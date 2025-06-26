package com.example.cleancodeproducts.presentation.view

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cleancodeproducts.R
import com.example.cleancodeproducts.data.repository.ApiState
import com.example.cleancodeproducts.databinding.ActivityMainBinding
import com.example.cleancodeproducts.presentation.view.adapter.ProductAdapter
import com.example.cleancodeproducts.presentation.viewmodel.ProductViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: ProductViewModel by viewModels()
    private  lateinit var  adapter : ProductAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setRecyclerView()
        observeViewModel()

        viewModel.fetchProduct()

    }

    private fun setRecyclerView(){
        adapter = ProductAdapter(emptyList())
        binding.rvProduct.layoutManager = LinearLayoutManager(this)
        binding.rvProduct.adapter = adapter
    }

    private fun observeViewModel(){
        lifecycleScope.launch {
            viewModel.product.collectLatest { state ->
                when(state){
                    is ApiState.Loading -> {
                        binding.pbProduct.isVisible = true
                    }

                    is ApiState.Success ->{
                        binding.pbProduct.isVisible = false
                        adapter.updateList(state.data)
                    }

                    is ApiState.Error -> {
                        binding.pbProduct.isVisible = false
                        Toast.makeText(this@MainActivity, state.error,Toast.LENGTH_LONG).show()
                    }
                }

            }
        }
    }
}