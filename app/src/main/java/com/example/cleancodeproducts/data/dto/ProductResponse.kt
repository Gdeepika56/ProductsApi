package com.example.cleancodeproducts.data.dto

import com.example.cleancodeproducts.domain.model.Product

data class ProductResponseDto (
    val category: String?,
    val description: String?,
    val title: String?
)

fun ProductResponseDto.toDomain(): Product{
    return Product(
            category = this.category ?: "",
            description = this.description ?: "",
            title = this.title ?: ""
    )
}