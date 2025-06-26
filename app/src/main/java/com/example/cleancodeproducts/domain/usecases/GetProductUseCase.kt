package com.example.cleancodeproducts.domain.usecases

import com.example.cleancodeproducts.data.repository.ProductRepository
import javax.inject.Inject

class GetProductUseCase @Inject constructor(private val repository: ProductRepository) {

    operator fun invoke() = repository.getProducts()

}