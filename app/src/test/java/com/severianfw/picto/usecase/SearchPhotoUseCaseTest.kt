package com.severianfw.picto.usecase

import com.severianfw.picto.data.remote.PhotoResponse
import com.severianfw.picto.data.remote.SearchPhotoResponse
import com.severianfw.picto.data.repository.PhotoRepository
import com.severianfw.picto.domain.model.PhotoItemModel
import com.severianfw.picto.domain.usecase.impl.SearchPhotoUseCaseImpl
import io.reactivex.rxjava3.core.Single
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class SearchPhotoUseCaseTest {

    @InjectMocks
    private lateinit var searchPhotoUseCaseImpl: SearchPhotoUseCaseImpl

    @Mock
    private lateinit var photoRepository: PhotoRepository

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }

    @After
    fun tearDown() {
        Mockito.verifyNoMoreInteractions(photoRepository)
    }

    @Test
    fun `when invoke then result should be valid`() {
        val dummyPage = 1
        val dummyPhotoName = "photo_name"
        val dummySearchPhotoResponse =
            SearchPhotoResponse(results = listOf(PhotoResponse(id = "ID_1")))

        Mockito.`when`(photoRepository.searchPhotos(dummyPage, dummyPhotoName))
            .thenReturn(Single.just(dummySearchPhotoResponse))
        val dummyListPhotoItem = listOf(PhotoItemModel(id = "ID_1"))

        val result = searchPhotoUseCaseImpl.invoke(dummyPage, dummyPhotoName).blockingGet()
        Assert.assertEquals(result, dummyListPhotoItem)

        Mockito.verify(photoRepository).searchPhotos(dummyPage, dummyPhotoName)
    }
}