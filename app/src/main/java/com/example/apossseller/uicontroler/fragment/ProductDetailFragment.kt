package com.example.apossseller.uicontroler.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.apossseller.R
import com.example.apossseller.databinding.FragmentProductDetailBinding
import com.example.apossseller.uicontroler.adapter.*
import com.example.apossseller.utils.LoadingStatus
import com.example.apossseller.viewmodel.DetailProductViewModel

class ProductDetailFragment : Fragment() , StringDetailPropertyAdapter.PropertyStringValueSelected,
    ColorDetailPropertyAdapter.PropertyColorValueSelected{


    private val args: ProductDetailFragmentArgs by navArgs()
    private lateinit var binding: FragmentProductDetailBinding
    private val viewModel: DetailProductViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_product_detail, container, false)
        val selectedProductId: Long = args.id
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        if (selectedProductId != -1L) {
            viewModel.setSelectedProductId(selectedProductId)
        }
        setBackButton()
        setUpProductProperty()
        onDetailProductChange()
        return binding.root
    }
    private fun onDetailProductChange(){
        viewModel.productDetailLoadingState.observe(viewLifecycleOwner, {
            if(it == LoadingStatus.Loading){
                binding.detailProgress.visibility = View.VISIBLE
            }else{
                binding.detailProgress.visibility = View.GONE
                if(it == LoadingStatus.Fail){
                    Toast.makeText(this.requireContext(),"Loading fail", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
    private fun setBackButton() {
        binding.back.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }
    private fun setUpProductProperty(){
        setShowAll()
        setUpViewPager()
        setUpIndicator()
        val stringPropertyAdapter = StringPropertyAdapter(this)
        val colorPropertyAdapter = ColorPropertyAdapter(this)
        binding.stringProperty.adapter = stringPropertyAdapter
        binding.colorProperty.adapter = colorPropertyAdapter
    }
    private fun setShowAll() {
        binding.showAll.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                binding.description.maxLines = 1000
            } else {
                binding.description.maxLines = 10
            }
        }
    }
    private fun setUpViewPager() {
        val imagesAdapter = DetailProductImageViewPagerAdapter(DetailProductImageViewPagerAdapter.OnClickListener{

        })
        binding.images.adapter = imagesAdapter
    }
    private fun setUpIndicator() {
        binding.indicator.setViewPager(binding.images)
    }

    override fun notifySelectedStringValueChange(propertyId: Long) {
        viewModel.notifySelectedStringPropertyChange(propertyId)
    }

    override fun notifySelectedColorValueChange(propertyId: Long) {
        viewModel.notifySelectedColorPropertyChange(propertyId)
    }
}