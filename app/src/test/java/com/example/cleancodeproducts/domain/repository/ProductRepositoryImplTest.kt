package com.example.cleancodeproducts.domain.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.cleancodeproducts.data.dto.ProductResponseDto
import com.example.cleancodeproducts.data.dto.toDomain
import com.example.cleancodeproducts.data.remote.ProductApiService
import com.example.cleancodeproducts.data.repository.ApiState
import com.example.cleancodeproducts.domain.model.Product
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import okhttp3.Response
import okhttp3.ResponseBody
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ProductRepositoryImplTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var repositoryImpl: ProductRepositoryImpl
    private lateinit var apiService: ProductApiService

    @Before
    fun setUp() {
        apiService = mockk()
        repositoryImpl = ProductRepositoryImpl(apiService)
    }

    @Test
    fun `getProducts return success when api returns the data`() = runTest {
        val productResponseDto = ProductResponseDto(
            category = "Test Product",
            description = "test description",
            title = "Test"
        )
        coEvery { apiService.getProducts() } returns retrofit2.Response.success(listOf(productResponseDto))

        val result = repositoryImpl.getProducts().toList()

        assertTrue(result[1] is ApiState.Success)
        assertEquals(productResponseDto.toDomain(),(result[1] as ApiState.Success).data[0])
    }

    @Test
    fun `getProducts return error when api fails to returns the data`() =  runTest {
        val error =retrofit2.Response.error<List<ProductResponseDto>>(
            400,
            ResponseBody.create(null, "No Response")
        )

        coEvery {apiService.getProducts()} returns error

        val result = repositoryImpl.getProducts().toList()

        assertTrue(result[1] is ApiState.Error)
    }

    @Test
    fun `getProducts return error when exception is thrown`() = runTest {
        coEvery { apiService.getProducts() } throws RuntimeException("Network Error")

        val result = repositoryImpl.getProducts().toList()

        assertTrue(result[1] is ApiState.Error)
        assertEquals("Error: Network Error", (result[1] as ApiState.Error).error)
    }

}