package com.example.cleancodeproducts.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.cleancodeproducts.data.repository.ApiState
import com.example.cleancodeproducts.data.repository.ProductRepository
import com.example.cleancodeproducts.domain.model.Product
import com.example.cleancodeproducts.domain.repository.ProductRepositoryImpl
import com.example.cleancodeproducts.domain.usecases.GetProductUseCase
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ProductViewModelTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()
    private lateinit var viewModel: ProductViewModel
    private lateinit var getProductUseCase: GetProductUseCase
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        getProductUseCase = mockk()
        viewModel = ProductViewModel(getProductUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `fetchProducts emits success`() = runTest {
        val fakeProducts = loadFakeProducts()

        coEvery { getProductUseCase() } returns flowOf(ApiState.Success(fakeProducts))

        viewModel.fetchProduct()
        testScheduler.advanceUntilIdle()

        assertEquals(ApiState.Success(fakeProducts),viewModel.product.value)
    }

    @Test
    fun `fetchProduct emits error`() = runTest {
        val error = "Something went wrong"

        coEvery { getProductUseCase() } returns flowOf(ApiState.Error(error))

        viewModel.fetchProduct()
        testScheduler.advanceUntilIdle()

        assertEquals(ApiState.Error(error),viewModel.product.value)
    }

    private fun loadFakeProducts(): List<Product>{
        val stream = javaClass.classLoader?.getResourceAsStream("ProductResponse.json")
        val reader = stream?.bufferedReader().use {it?.readText()?: "NA"}
        val listType = object :TypeToken<List<Product>>() {}.type
        return Gson().fromJson(reader, listType)
    }

}