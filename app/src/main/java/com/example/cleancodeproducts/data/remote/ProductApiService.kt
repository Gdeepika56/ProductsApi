package com.example.cleancodeproducts.data.remote

import com.example.cleancodeproducts.commons.Constants.ENDPOINTS
import com.example.cleancodeproducts.data.dto.ProductResponseDto
import com.example.cleancodeproducts.domain.model.Product
import retrofit2.Response
import retrofit2.http.GET

interface ProductApiService {

    @GET(ENDPOINTS)
    suspend fun getProducts(): Response<List<ProductResponseDto>>
}