package com.severianfw.picto.usecase

import com.severianfw.picto.data.local.entity.PhotoEntity
import com.severianfw.picto.data.remote.PhotoResponse
import com.severianfw.picto.data.repository.PhotoRepository
import com.severianfw.picto.data.repository.PhotoState
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
import org.mockito.MockitoAnnotations

class GetPhotoUseCaseTest {

    @InjectMocks
    private lateinit var getPhotoUseCaseImpl: GetPhotoUseCaseImpl

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
    fun `when invoke and get PhotoRemoteModel then result must be valid`() {
        // Given
        val dummyPage = 1
        val dummyIsInitial = true
        val dummyPhotoResponse = PhotoResponse(id = "ID_1")
        val dummyPhotoState = PhotoState.PhotoRemoteModel(listOf(dummyPhotoResponse))
        Mockito.`when`(photoRepository.getPhotos(dummyPage, dummyIsInitial))
            .thenReturn(Single.just(dummyPhotoState))
        val dummyPhotoItemModel = PhotoMapper.mapResponseToPhotoItemModel(dummyPhotoState.photos)

        // When
        val result = getPhotoUseCaseImpl.invoke(dummyPage, dummyIsInitial).blockingGet()

        // Then
        Assert.assertEquals(result, dummyPhotoItemModel)

        Mockito.verify(photoRepository).getPhotos(dummyPage, dummyIsInitial)
    }

    @Test
    fun `when invoke and get PhotoLocalModel then result must be valid`() {
        // Given
        val dummyPage = 1
        val dummyIsInitial = true
        val dummyPhotoEntity = PhotoEntity(id = "ID_1")
        val dummyPhotoState = PhotoState.PhotoLocalModel(listOf(dummyPhotoEntity))
        Mockito.`when`(photoRepository.getPhotos(dummyPage, dummyIsInitial))
            .thenReturn(Single.just(dummyPhotoState))
        val dummyPhotoItemModel = PhotoMapper.mapPhotoEntityToPhotoItemModel(dummyPhotoState.photos)

        // When
        val result = getPhotoUseCaseImpl.invoke(dummyPage, dummyIsInitial).blockingGet()

        // Then
        Assert.assertEquals(result, dummyPhotoItemModel)

        Mockito.verify(photoRepository).getPhotos(dummyPage, dummyIsInitial)
    }

}