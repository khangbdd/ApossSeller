package com.example.apossseller.uicontroler.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.apossseller.R
import com.example.apossseller.databinding.FragmentProductBinding
import com.example.apossseller.uicontroler.adapter.HomeProductAdapter
import com.example.apossseller.viewmodel.ProductViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductFragment : Fragment(), ViewTreeObserver.OnScrollChangedListener{

    private lateinit var binding: FragmentProductBinding
    private lateinit var productAdapter: HomeProductAdapter
    private val viewModel: ProductViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_product, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        productAdapter = HomeProductAdapter(HomeProductAdapter.OnClickListener{

        })
        binding.imageBack.setOnClickListener {
            requireActivity().onBackPressed()
        }
        binding.products.adapter = productAdapter
        setUpNestedScrollView()
        onSearchKeyChange()
        return binding.root
    }
    private fun onSearchKeyChange(){
        viewModel.searchKey.observe(this.viewLifecycleOwner, Observer {
            viewModel.currentPage =1;
            viewModel.isLastPage = false;
            viewModel._products.value = mutableListOf()
            viewModel.loadProduct();
        })
    }

    override fun onScrollChanged() {
        val view: View = binding.scrollView.getChildAt(binding.scrollView.childCount -1)
        val bottomDetector = view.bottom -  (binding.scrollView.height + binding.scrollView.scrollY)
        if(bottomDetector <=0){
            viewModel.loadProduct()
        }
    }
    private fun setUpNestedScrollView() {
        binding.scrollView.viewTreeObserver.addOnScrollChangedListener(this)
    }

}