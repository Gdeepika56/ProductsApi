package com.example.cleancodeproducts.presentation.view.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cleancodeproducts.databinding.ViewHolderProductBinding
import com.example.cleancodeproducts.domain.model.Product
import com.example.cleancodeproducts.presentation.viewholder.ProductViewHolder

class ProductAdapter(var productList: List<Product>): RecyclerView.Adapter<ProductViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProductViewHolder {
        val binding = ViewHolderProductBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ProductViewHolder,
        position: Int
    ) {
      holder.bind(productList[position])
    }

    override fun getItemCount(): Int = productList.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateList (newList :List<Product>){
        productList = newList
        notifyDataSetChanged()
    }
}