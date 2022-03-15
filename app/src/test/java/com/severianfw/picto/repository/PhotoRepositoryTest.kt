package com.severianfw.picto.repository

import com.severianfw.picto.data.local.PhotoDao
import com.severianfw.picto.data.remote.ApiService
import com.severianfw.picto.data.remote.PhotoResponse
import com.severianfw.picto.data.remote.SearchPhotoResponse
import com.severianfw.picto.data.repository.PhotoRepositoryImpl
import com.severianfw.picto.domain.model.PhotoItemModel
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

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }

    @After
    fun tearDown() {
        Mockito.verifyNoMoreInteractions(photoDao)
        Mockito.verifyNoMoreInteractions(apiService)
    }

    @Test
    fun `when getLocalPhotos then result must be valid`() {
        // Given
        val photoItemModel = PhotoItemModel("ID_1")
        Mockito.`when`(photoDao.getPhotos()).thenReturn(Single.just(listOf(photoItemModel)))

        // When
        val result = photoRepositoryImpl.getLocalPhotos().blockingGet()

        // Then
        Assert.assertEquals(result, listOf(photoItemModel))
        Mockito.verify(photoDao).getPhotos()
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
    fun `when getPhotos then result must be valid`() {
        // Given
        val dummyPage = 1
        val dummyPerPage = 10
        val dummyClientId = "eH_2mMyrCefjXtAmoudvsdfA6qdHD4ju6jF3yFkY5UU"
        val dummyPhotoResponse = PhotoResponse(id = "ID_1")
        val dummyPhotoItemModel = PhotoItemModel(id = "ID_1")
        Mockito.`when`(apiService.getPhotos(dummyClientId, dummyPerPage, dummyPage))
            .thenReturn(
                Single.just(listOf(dummyPhotoResponse))
            )
        Mockito.doNothing().`when`(photoDao).insertPhotos(listOf(dummyPhotoItemModel))

        // When
        val result = photoRepositoryImpl.getPhotos(dummyPage).blockingGet()

        // Then
        Assert.assertEquals(result, listOf(dummyPhotoResponse))
        Mockito.verify(photoDao).insertPhotos(listOf(dummyPhotoItemModel))
        Mockito.verify(apiService).getPhotos(
            dummyClientId,
            dummyPerPage,
            dummyPage
        )
    }


}