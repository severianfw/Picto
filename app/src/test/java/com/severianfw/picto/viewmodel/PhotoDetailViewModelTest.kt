package com.severianfw.picto.viewmodel

import com.severianfw.picto.domain.usecase.DownloadPhotoUseCase
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class PhotoDetailViewModelTest {

    @InjectMocks
    private lateinit var photoDetailViewModel: PhotoDetailViewModel

    @Mock
    private lateinit var downloadPhotoUseCase: DownloadPhotoUseCase

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
    }

    @After
    fun teardown() {
        Mockito.verifyNoMoreInteractions(downloadPhotoUseCase)
    }

    @Test
    fun `when downloadPhoto then use case must be executed`() {
        // Given
        val dummyPhotoUrl = "photo_url"
        Mockito.doNothing().`when`(downloadPhotoUseCase).invoke(dummyPhotoUrl)

        // When
        photoDetailViewModel.downloadPhoto(dummyPhotoUrl)

        // Then
        Mockito.verify(downloadPhotoUseCase).invoke(dummyPhotoUrl)
    }
}