package com.severianfw.picto.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.severianfw.picto.domain.model.PhotoItemModel
import com.severianfw.picto.domain.usecase.GetPhotoUseCase
import com.severianfw.picto.domain.usecase.SearchPhotoUseCase
import io.reactivex.rxjava3.android.plugins.RxAndroidPlugins
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.plugins.RxJavaPlugins
import io.reactivex.rxjava3.schedulers.Schedulers
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class HomeViewModelTest : BaseViewModelTest() {

    @InjectMocks
    private lateinit var homeViewModel: HomeViewModel

    @Mock
    private lateinit var getPhotoUseCase: GetPhotoUseCase

    @Mock
    private lateinit var searchPhotoUseCase: SearchPhotoUseCase

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    override fun setup() {
        MockitoAnnotations.openMocks(this)
        RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
    }

    override fun tearDown() {
        Mockito.verifyNoMoreInteractions(getPhotoUseCase)
        Mockito.verifyNoMoreInteractions(searchPhotoUseCase)
    }

    @Test
    fun `when getPhotos then result should be valid`() {
        // Given
        val dummyPageNumber = 1
        val dummyIsInitial = true
        val dummyPhotoItemModel = PhotoItemModel(id = "ID_1")
        Mockito.`when`(getPhotoUseCase.invoke(dummyPageNumber, dummyIsInitial))
            .thenReturn(Single.just(listOf(dummyPhotoItemModel)))

        // When
        homeViewModel.getPhotos()

        // Then
        homeViewModel.photos.observeForTesting {
            val actual = homeViewModel.photos.value
            Assert.assertEquals(actual, listOf(dummyPhotoItemModel))
        }
        Mockito.verify(getPhotoUseCase).invoke(dummyPageNumber, dummyIsInitial)
    }

    @Test
    fun `when searchPhotos then result should be valid`() {
        // Given
        val dummyPageNumber = 1
        val dummyPhotoName = "photo_name"
        val dummyPhotoItemModel = PhotoItemModel(id = "ID_1")
        Mockito.`when`(searchPhotoUseCase.invoke(dummyPageNumber, dummyPhotoName))
            .thenReturn(Single.just(listOf(dummyPhotoItemModel)))

        // When
        homeViewModel.searchPhotos(dummyPhotoName)

        // Then
        homeViewModel.photos.observeForTesting {
            val actual = homeViewModel.photos.value
            Assert.assertEquals(actual, listOf(dummyPhotoItemModel))
        }
        Mockito.verify(searchPhotoUseCase).invoke(dummyPageNumber, dummyPhotoName)
    }

    @Test
    fun `when load more page then pageNumber must be added by 1 and result must be valid`() {
        // Given
        val dummyPageNumber = 2
        val dummyIsInitial = false
        val dummyPhotoItemModel = PhotoItemModel(id = "ID_1")
        Mockito.`when`(getPhotoUseCase.invoke(dummyPageNumber, dummyIsInitial))
            .thenReturn(Single.just(listOf(dummyPhotoItemModel)))

        // When
        homeViewModel.setIsInitial(false)
        homeViewModel.loadMorePage()

        // Then
        homeViewModel.photos.observeForTesting {
            val actual = homeViewModel.photos.value
            Assert.assertEquals(actual, listOf(dummyPhotoItemModel))
        }
        Mockito.verify(getPhotoUseCase).invoke(dummyPageNumber, dummyIsInitial)

    }

    @Test
    fun `when load more page and is searching then pageNumber must be added by 1 and result must be valid`() {
        // Given
        val dummyPageNumber = 2
        val dummyPhotoName = "photo_name"
        val dummyPhotoItemModel = PhotoItemModel(id = "ID_1")
        Mockito.`when`(searchPhotoUseCase.invoke(dummyPageNumber, dummyPhotoName))
            .thenReturn(Single.just(listOf(dummyPhotoItemModel)))

        // When
        homeViewModel.setPhotoName(dummyPhotoName)
        homeViewModel.setIsSearching(true)
        homeViewModel.loadMorePage()

        // Then
        homeViewModel.photos.observeForTesting {
            val actual = homeViewModel.photos.value
            Assert.assertEquals(actual, listOf(dummyPhotoItemModel))
        }
        Mockito.verify(searchPhotoUseCase).invoke(dummyPageNumber, dummyPhotoName)
    }


}