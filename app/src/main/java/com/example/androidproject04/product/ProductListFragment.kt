package com.example.androidproject04.product

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import com.example.androidproject04.databinding.FragmentProductsListBinding
import com.example.androidproject04.persistence.Product

private const val TAG = "ProductsListFragment"

class ProductListFragment : Fragment() {

    private val productListViewModel: ProductListViewModel by lazy {
        ViewModelProvider(this)[ProductListViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentProductsListBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.productListViewModel = productListViewModel

        val itemDecor = DividerItemDecoration(context, VERTICAL)
        binding.rcvProducts.addItemDecoration(itemDecor);

        binding.rcvProducts.adapter =
            ProductAdapter(ProductAdapter.ProductClickListener { product ->
                Log.i(TAG, "Product selected: ${product.name}")
                this.findNavController()
                    .navigate(ProductListFragmentDirections.actionShowProductDetail(product.code!!))
            })

        binding.fab.setOnClickListener { view ->
            this.findNavController()
                .navigate(ProductListFragmentDirections.actionShowProductDetail(null))
        }

        binding.productsRefresh.setOnRefreshListener {
            Log.i(TAG, "Refreshing products list")
            productListViewModel.refreshProducts()
            binding.productsRefresh.isRefreshing = false
        }

        return binding.root
    }
}