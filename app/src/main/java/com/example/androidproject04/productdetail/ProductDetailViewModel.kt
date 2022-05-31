package com.example.androidproject04.productdetail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.androidproject04.persistence.Product
import com.example.androidproject04.persistence.ProductRepository

private const val TAG = "ProductDetailViewModel"

class ProductDetailViewModel(code: String?) : ViewModel() {
    lateinit var product: MutableLiveData<Product?>

    init {
        if (code != null) {
            getProduct(code)
        } else {
            product = MutableLiveData<Product?>()
            product.value = Product()
        }
    }

    private fun getProduct(productCode: String) {
        product = ProductRepository.getProductByCode(productCode)
    }

    fun deleteProduct() {
        if (product.value?.id != null) {
            ProductRepository.deleteProduct(product.value!!.id!!)
            product.value = null
        }
    }

    override fun onCleared() {
        if (product.value != null
            && product.value!!.code != null
            && product.value!!.code!!.isNotBlank()
        ) {
            ProductRepository.saveProduct(product.value!!)
        }
        super.onCleared()
    }
}