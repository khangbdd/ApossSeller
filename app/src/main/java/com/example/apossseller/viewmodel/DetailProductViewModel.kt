package com.example.apossseller.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.apossseller.model.*
import com.example.apossseller.model.dto.ProductDetailDTO
import com.example.apossseller.model.dto.ProductPropertyDTO
import com.example.apossseller.model.dto.ProductPropertyValueDTO
import com.example.apossseller.repository.ProductRepository
import com.example.apossseller.utils.LoadingStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.util.stream.Collectors
import javax.inject.Inject

@HiltViewModel
class DetailProductViewModel @Inject constructor(
    private val productRepository: ProductRepository,
) : ViewModel(){

    private var selectedProductId: Long = 0

    private var _selectedProduct = MutableLiveData<ProductDetail>()
    val selectedProduct: LiveData<ProductDetail> get() = _selectedProduct

    private var _selectedProductImages = MutableLiveData<List<Image>>()
    val selectedProductImages: LiveData<List<Image>> get() = _selectedProductImages

    private var _selectedProductStringProperty = MutableLiveData<ArrayList<ProductDetailProperty>>()
    val selectedProductStringProperty: LiveData<ArrayList<ProductDetailProperty>> get() = _selectedProductStringProperty

    private var _selectedProductColorProperty = MutableLiveData<ArrayList<ProductDetailProperty>>()
    val selectedProductColorProperty: LiveData<ArrayList<ProductDetailProperty>> get() = _selectedProductColorProperty

    private val _selectedProductQuantities = MutableLiveData<Int>()
    val selectedProductQuantities: MutableLiveData<Int> get() = _selectedProductQuantities

    val productDetailLoadingState = MutableLiveData<LoadingStatus>()

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    fun setSelectedProductId(id: Long) {
        selectedProductId = id
        if (selectedProductId != (-1).toLong()) {
            loadSelectedProductById(selectedProductId)
            loadListImageByID(selectedProductId)
        }
    }
    private fun loadSelectedProductById(id: Long) {
        productDetailLoadingState.value = LoadingStatus.Loading
        coroutineScope.launch {
            val productResponse = productRepository.loadProductById(id)
            if (productResponse.isSuccessful) {
                _selectedProduct.value = mapToProductDetail(productResponse.body()!!, id)
                _selectedProductQuantities.value = _selectedProduct.value!!.availableQuantities
                loadSelectedProductStringPropertyById(selectedProductId, _selectedProduct.value!!.availableQuantities)
                loadSelectedProductColorPropertyById(selectedProductId, _selectedProduct.value!!.availableQuantities)
                productDetailLoadingState.value = LoadingStatus.Success
            } else {
                productDetailLoadingState.value = LoadingStatus.Fail
            }
        }
    }
    private fun loadSelectedProductColorPropertyById(id: Long, productQuantity: Int)  {
        coroutineScope.launch {
            val productStringResponseDTO = productRepository.loadProductColorPropertyById(id)
            if(productStringResponseDTO.isSuccessful){
                _selectedProductColorProperty.value =  productStringResponseDTO.body()!!.stream().map {
                    mapToProperty(it!!, productQuantity)
                }.collect(Collectors.toList()).toCollection(ArrayList())
            }
        }
    }
    private fun loadSelectedProductStringPropertyById(id: Long, productQuantity: Int) {
        coroutineScope.launch {
            val productStringResponseDTO = productRepository.loadProductStringPropertyById(id)
            if(productStringResponseDTO.isSuccessful){
                _selectedProductStringProperty.value =  productStringResponseDTO.body()!!.stream().map {
                    mapToProperty(it!!, productQuantity)
                }.collect(Collectors.toList()).toCollection(ArrayList())
            }
        }
    }
    private fun mapToProperty(productPropertyDTO: ProductPropertyDTO, productQuantity: Int): ProductDetailProperty{
        val productPropertyValues: List<PropertyValue> = productPropertyDTO.valueDTOS.stream().map {
            mapToPropertyValue(it, productPropertyDTO.id)
        }.collect(Collectors.toList())
        return ProductDetailProperty(
            productPropertyDTO.id,
            productPropertyDTO.name,
            productPropertyValues,
            productQuantity
        )
    }
    private fun mapToPropertyValue(productPropertyValueDTO: ProductPropertyValueDTO, propertyId: Long): PropertyValue{
        return PropertyValue(
            productPropertyValueDTO.id,
            productPropertyValueDTO.name,
            propertyId,
            productPropertyValueDTO.value,
            productPropertyValueDTO.additionalPrice,
            productPropertyValueDTO.quantity,
            false
        )
    }
    private fun mapToProductDetail(productDetailDTO: ProductDetailDTO, id: Long): ProductDetail {
        return ProductDetail(
            id,
            productDetailDTO.name,
            productDetailDTO.price,
            productDetailDTO.purchase,
            productDetailDTO.rating,
            true,
            productDetailDTO.description,
            productDetailDTO.quantity,
            productDetailDTO.kindName,
            productDetailDTO.totalReview
        )
    }
    private fun mapToImage(productImageDTO: ProductImageDTO): Image {
        return Image(productImageDTO.imageUrl)
    }

    private fun loadListImageByID(id: Long) {
        coroutineScope.launch {
            val productImageResponse = productRepository.loadProductImageByProductId(id)
            if (productImageResponse.isSuccessful) {
                _selectedProductImages.value = productImageResponse.body()!!.stream().map{
                    mapToImage(it)
                }.collect(Collectors.toList())
            }
        }
    }
    fun notifySelectedStringPropertyChange(propertyId: Long) {
        var newQuantities = 0
        for (property in _selectedProductStringProperty.value!!) {
            if (property.id == propertyId) {
                for (value in property.values) {
                    if (value.isChosen) {
                        newQuantities += value.count
                    }
                }
                if (newQuantities == 0) {
                    property.valueCountSummarize = 11
                } else {
                    property.valueCountSummarize = newQuantities
                }
            }
        }
        setSelectedProductMinValue()
    }

    fun notifySelectedColorPropertyChange(propertyId: Long) {
        var newQuantities = 0
        for (property in _selectedProductColorProperty.value!!) {
            if (property.id == propertyId) {
                for (value in property.values) {
                    if (value.isChosen) {
                        newQuantities += value.count
                    }
                }
                if (newQuantities == 0) {
                    property.valueCountSummarize = 11
                } else {
                    property.valueCountSummarize = newQuantities
                }
            }
        }
        setSelectedProductMinValue()
    }
    private fun setSelectedProductMinValue() {
        var minSelect = selectedProductStringProperty.value!![0].valueCountSummarize
        for (property in _selectedProductStringProperty.value!!) {
            if (property.valueCountSummarize < minSelect) {
                minSelect = property.valueCountSummarize
            }
        }
        for (property in _selectedProductColorProperty.value!!) {
            if (property.valueCountSummarize < minSelect) {
                minSelect = property.valueCountSummarize
            }
        }

        _selectedProductQuantities.value = minSelect
    }

}
