package com.example.cleancodeproducts.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cleancodeproducts.data.repository.ApiState
import com.example.cleancodeproducts.domain.model.Product
import com.example.cleancodeproducts.domain.usecases.GetProductUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(private val getProductUseCase: GetProductUseCase):
    ViewModel() {

    private val _product = MutableStateFlow<ApiState<List<Product>>>(ApiState.Loading)
    val product : StateFlow<ApiState<List<Product>>> = _product

    fun fetchProduct(){
        viewModelScope.launch{
            getProductUseCase().collect{ data->
                _product.value = data
            }
        }
    }
}