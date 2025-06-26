package com.example.cleancodeproducts.data.repository

import com.example.cleancodeproducts.domain.model.Product
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    fun getProducts(): Flow<ApiState<List<Product>>>
}