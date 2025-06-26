package com.example.cleancodeproducts.domain.repository

import com.example.cleancodeproducts.data.dto.toDomain
import com.example.cleancodeproducts.data.remote.ProductApiService
import com.example.cleancodeproducts.data.repository.ApiState
import com.example.cleancodeproducts.data.repository.ProductRepository
import com.example.cleancodeproducts.domain.model.Product
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(private val apiService: ProductApiService): ProductRepository {
    override fun getProducts(): Flow<ApiState<List<Product>>> = flow {

        emit(ApiState.Loading)
        try {
            val response = apiService.getProducts()
            if (response.isSuccessful){
                response.body().let {
                    val product = response.body()?.map { it.toDomain() } ?:emptyList()
                    emit(ApiState.Success(product))
                }
            }else{
                emit(ApiState.Error("Error: ${response.code()}"))
            }

        }catch (e:Exception){
            emit(ApiState.Error("Error: ${e.localizedMessage}"))
        }
    }
}