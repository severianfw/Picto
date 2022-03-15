package com.severianfw.picto.usecase

import com.severianfw.picto.domain.usecase.impl.DownloadPhotoUseCaseImpl
import com.severianfw.picto.downloadmanager.DownloadManagerHelper
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class DownloadPhotoUseCaseTest {

    @InjectMocks
    private lateinit var downloadPhotoUseCaseImpl: DownloadPhotoUseCaseImpl

    @Mock
    private lateinit var downloadManagerHelper: DownloadManagerHelper

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }

    @After
    fun tearDown() {
        Mockito.verifyNoMoreInteractions(downloadManagerHelper)
    }

    @Test
    fun `when invoke then downloadPhoto must be executed`() {
        // Given
        val dummyPhotoUrl = "test_url"
        Mockito.doNothing().`when`(downloadManagerHelper).downloadPhoto(dummyPhotoUrl)

        // When
        downloadPhotoUseCaseImpl.invoke(dummyPhotoUrl)

        // Then
        Mockito.verify(downloadManagerHelper).downloadPhoto(dummyPhotoUrl)
    }
}