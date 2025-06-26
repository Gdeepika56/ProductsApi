package com.example.cleancodeproducts.presentation.viewholder

import android.annotation.SuppressLint
import androidx.recyclerview.widget.RecyclerView
import com.example.cleancodeproducts.databinding.ViewHolderProductBinding
import com.example.cleancodeproducts.domain.model.Product

class ProductViewHolder (val binding: ViewHolderProductBinding): RecyclerView.ViewHolder(binding.root){
    @SuppressLint("SetTextI18n")
    fun bind(product:Product){
        with(binding){
            tvCategory.text = "Category: ${product.category}"
            tvDescription.text ="Description: ${product.description}"
            tvTitle.text = "Title: ${product.title}"
        }
    }
}