package com.severianfw.picto.usecase

import com.severianfw.picto.connectionlistener.InternetConnectionListener
import com.severianfw.picto.data.remote.PhotoResponse
import com.severianfw.picto.data.repository.PhotoRepository
import com.severianfw.picto.domain.model.PhotoItemModel
import com.severianfw.picto.domain.usecase.impl.GetPhotoUseCaseImpl
import com.severianfw.picto.utils.PhotoMapper
import io.reactivex.rxjava3.core.Single
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.doNothing
import org.mockito.MockitoAnnotations

class GetPhotoUseCaseTest {

    @InjectMocks
    private lateinit var getPhotoUseCaseImpl: GetPhotoUseCaseImpl

    @Mock
    private lateinit var photoRepository: PhotoRepository

    @Mock
    private lateinit var internetConnectionListener: InternetConnectionListener

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }

    @After
    fun tearDown() {
        Mockito.verifyNoMoreInteractions(photoRepository)
        Mockito.verifyNoMoreInteractions(internetConnectionListener)
    }

    @Test
    fun testOnlineInvoke() {
        val dummyPage = 1
        val dummyIsInitial = true
        val dummyPhotoResponse = PhotoResponse(id = "ID_1")

        Mockito.`when`(internetConnectionListener.isInternetAvailable()).thenReturn(true)
        doNothing().`when`(photoRepository).deleteLocalPhotos()

        Mockito.`when`(photoRepository.getPhotos(dummyPage))
            .thenReturn(Single.just(listOf(dummyPhotoResponse)))
        val dummyListPhotoItemModel = PhotoMapper.mapToPhotoItemModel(listOf(dummyPhotoResponse))

        val result = getPhotoUseCaseImpl.invoke(dummyPage, dummyIsInitial).blockingGet()
        Assert.assertEquals(result, dummyListPhotoItemModel)

        Mockito.verify(internetConnectionListener).isInternetAvailable()
        Mockito.verify(photoRepository).deleteLocalPhotos()
        Mockito.verify(photoRepository).getPhotos(dummyPage)
    }

    @Test
    fun testOfflineInvoke() {
        val dummyPage = 1
        val dummyIsInitial = true
        val dummyPhotoItemModel = PhotoItemModel(id = "ID_1")

        Mockito.`when`(internetConnectionListener.isInternetAvailable()).thenReturn(false)
        Mockito.`when`(photoRepository.getLocalPhotos())
            .thenReturn(Single.just(listOf(dummyPhotoItemModel)))
        val result = getPhotoUseCaseImpl.invoke(dummyPage, dummyIsInitial).blockingGet()

        Assert.assertEquals(result, listOf(dummyPhotoItemModel))

        Mockito.verify(internetConnectionListener).isInternetAvailable()
        Mockito.verify(photoRepository).getLocalPhotos()
    }

    @Test
    fun testOfflineInvokeIgnoreLoadMore() {
        val dummyPage = 2
        val dummyIsInitial = true

        Mockito.`when`(internetConnectionListener.isInternetAvailable()).thenReturn(false)

        val result = getPhotoUseCaseImpl.invoke(dummyPage, dummyIsInitial).blockingGet()
        Assert.assertEquals(result, emptyList<PhotoItemModel>())

        Mockito.verify(internetConnectionListener).isInternetAvailable()
    }


}