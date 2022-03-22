package com.severianfw.picto.repository

import com.severianfw.picto.connectionlistener.InternetConnectionListener
import com.severianfw.picto.data.local.PhotoDao
import com.severianfw.picto.data.local.PhotoEntity
import com.severianfw.picto.data.remote.ApiService
import com.severianfw.picto.data.remote.PhotoResponse
import com.severianfw.picto.data.remote.SearchPhotoResponse
import com.severianfw.picto.data.repository.PhotoRepositoryImpl
import com.severianfw.picto.data.repository.PhotoState
import com.severianfw.picto.domain.model.PhotoItemModel
import com.severianfw.picto.utils.PhotoMapper
import io.reactivex.rxjava3.core.Single
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class PhotoRepositoryTest {

    @InjectMocks
    private lateinit var photoRepositoryImpl: PhotoRepositoryImpl

    @Mock
    private lateinit var apiService: ApiService

    @Mock
    private lateinit var photoDao: PhotoDao

    @Mock
    private lateinit var internetConnectionListener: InternetConnectionListener

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }

    @After
    fun tearDown() {
        Mockito.verifyNoMoreInteractions(photoDao)
        Mockito.verifyNoMoreInteractions(apiService)
        Mockito.verifyNoMoreInteractions(internetConnectionListener)
    }

    @Test
    fun `when searchPhotos then result must be valid`() {
        // Given
        val dummyPage = 1
        val dummyPerPage = 10
        val dummyPhotoName = "photo_name"
        val dummyClientId = "eH_2mMyrCefjXtAmoudvsdfA6qdHD4ju6jF3yFkY5UU"
        Mockito.`when`(
            apiService.searchPhotos(
                dummyClientId,
                dummyPerPage,
                dummyPage,
                dummyPhotoName
            )
        ).thenReturn(
            Single.just(SearchPhotoResponse())
        )

        // When
        val result = photoRepositoryImpl.searchPhotos(dummyPage, dummyPhotoName).blockingGet()

        // Then
        Assert.assertEquals(result, SearchPhotoResponse())
        Mockito.verify(apiService).searchPhotos(
            dummyClientId,
            dummyPerPage,
            dummyPage,
            dummyPhotoName
        )
    }

    @Test
    fun `when deleteLocalPhotos then deletePhotos must be executed`() {
        // Given
        Mockito.doNothing().`when`(photoDao).deletePhotos()

        // When
        photoRepositoryImpl.deleteLocalPhotos()

        // then
        Mockito.verify(photoDao).deletePhotos()
    }

    @Test
    fun `when getPhotos and has internet then result type must be valid`() {
        // Given
        val dummyPage = 1
        val dummyIsInitial = true
        val dummyPerPage = 10
        val dummyClientId = "eH_2mMyrCefjXtAmoudvsdfA6qdHD4ju6jF3yFkY5UU"
        val dummyPhotoResponse = PhotoResponse(id = "ID_1")
        val dummyPhotoEntity = PhotoEntity(id = "ID_1")
        Mockito.`when`(internetConnectionListener.isInternetAvailable()).thenReturn(true)
        Mockito.doNothing().`when`(photoDao).deletePhotos()
        Mockito.`when`(apiService.getPhotos(dummyClientId, dummyPerPage, dummyPage))
            .thenReturn(
                Single.just(listOf(dummyPhotoResponse))
            )
        Mockito.doNothing().`when`(photoDao).insertPhotos(listOf(dummyPhotoEntity))
        val dummyPhotoRemoteModel = PhotoState.PhotoRemoteModel(listOf(dummyPhotoResponse))

        // When
        val result = photoRepositoryImpl.getPhotos(dummyPage, dummyIsInitial).blockingGet()

        // Then
        Assert.assertEquals(result.javaClass, dummyPhotoRemoteModel.javaClass)
        Mockito.verify(photoDao).insertPhotos(listOf(dummyPhotoEntity))
        Mockito.verify(photoDao).deletePhotos()
        Mockito.verify(apiService).getPhotos(
            dummyClientId,
            dummyPerPage,
            dummyPage
        )
        Mockito.verify(internetConnectionListener).isInternetAvailable()
    }

    @Test
    fun `when getPhotos and no internet then result type must be valid`() {
        // Given
        val dummyPage = 1
        val dummyIsInitial = true
        val dummyPerPage = 10
        val dummyPhotoEntity = PhotoEntity(id = "ID_1")
        Mockito.`when`(internetConnectionListener.isInternetAvailable()).thenReturn(false)
        Mockito.`when`(photoDao.getPhotos())
            .thenReturn(
                Single.just(listOf(dummyPhotoEntity))
            )
        val dummyPhotoLocalModel = PhotoState.PhotoLocalModel(listOf(dummyPhotoEntity))

        // When
        val result = photoRepositoryImpl.getPhotos(dummyPage, dummyIsInitial).blockingGet()

        // Then
        Assert.assertEquals(result.javaClass, dummyPhotoLocalModel.javaClass)
        Mockito.verify(photoDao).getPhotos()
        Mockito.verify(internetConnectionListener).isInternetAvailable()
    }

    @Test
    fun `when getPhotos, no internet connection, and page is greater than 1 then result type must be empty list of PhotoState`() {
        // Given
        val dummyPage = 2
        val dummyIsInitial = true
        val dummyPhotoState = PhotoState.PhotoLocalModel(emptyList())
        Mockito.`when`(internetConnectionListener.isInternetAvailable()).thenReturn(false)

        // When
        val result = photoRepositoryImpl.getPhotos(dummyPage, dummyIsInitial).blockingGet()

        // Then
        Assert.assertEquals(result.javaClass, dummyPhotoState.javaClass)
        Mockito.verify(internetConnectionListener).isInternetAvailable()
    }

}