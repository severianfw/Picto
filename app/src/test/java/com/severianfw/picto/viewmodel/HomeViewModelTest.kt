package com.severianfw.picto.viewmodel

import com.severianfw.picto.domain.model.PhotoItemModel
import com.severianfw.picto.domain.usecase.GetPhotoUseCase
import com.severianfw.picto.domain.usecase.SearchPhotoUseCase
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.subscribers.TestSubscriber
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.doReturn
import org.mockito.MockitoAnnotations

class HomeViewModelTest {

    @InjectMocks
    private lateinit var homeViewModel: HomeViewModel

    @Mock
    private lateinit var getPhotoUseCase: GetPhotoUseCase

    @Mock
    private lateinit var searchPhotoUseCase: SearchPhotoUseCase

    private var subscriber = TestSubscriber<Single<PhotoItemModel>>()

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun testGetPhotos() {
        val dummyPageNumber = 2
        val dummyIsInitial = true
        val dummyPhotoItemModel = PhotoItemModel(id = "ID_1")

//        Mockito.`when`(getPhotoUseCase.invoke(dummyPageNumber, dummyIsInitial))
//            .thenReturn(Single.just(listOf(dummyPhotoItemModel)))

        doReturn(Single.just(listOf(dummyPhotoItemModel))).`when`(getPhotoUseCase)
            .invoke(dummyPageNumber, dummyIsInitial)
        homeViewModel.getPhotos()

        val result = homeViewModel.photos.value
        assertEquals(result, listOf(dummyPhotoItemModel))
    }

    @Test
    fun testSearchPhotos() {
        var dummyIsSearching = true

    }

    @Test
    fun testLoadMorePage() {

    }


}