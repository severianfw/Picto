package com.severianfw.picto

import com.severianfw.picto.viewmodel.HomeViewModel
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock

class HomeViewModelTest {

    private lateinit var homeViewModel: HomeViewModel

    @Before
    fun setUp() {
        homeViewModel = mock(HomeViewModel::class.java)
    }

    @Test
    fun testLoadMorePage() {
    }

}