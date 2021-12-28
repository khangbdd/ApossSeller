package com.example.apossseller.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.apossseller.model.HomeProduct
import com.example.apossseller.model.Image
import com.example.apossseller.model.dto.ProductDTO
import com.example.apossseller.repository.ProductRepository
import com.example.apossseller.utils.LoadingStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.util.stream.Collector
import java.util.stream.Collectors
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val productRepository: ProductRepository
):ViewModel() {

    var _products = MutableLiveData<MutableList<HomeProduct>>()
    val products: LiveData<MutableList<HomeProduct>> get() = _products

    var isLastPage = false
    var currentPage = 1;
    val searchKey = MutableLiveData<String>()

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)
    var toastMessage: MutableLiveData<String> = MutableLiveData()
    private var _status = MutableLiveData<LoadingStatus>()
    val status: LiveData<LoadingStatus> get()= _status
    init {
        searchKey.value =""
        _products.value= mutableListOf();
        loadProduct()
    }
    fun loadProduct(){
        if(!isLastPage && _status.value != LoadingStatus.Loading){
            _status.value = LoadingStatus.Loading;
            coroutineScope.launch {
                val productResponseDTO = productRepository.loadProductByKeywordAndSort( currentPage,searchKey.value!!)
                if(productResponseDTO.isSuccessful){
                    val productResponse = productResponseDTO.body()
                    val resultProduct = productResponse!!.content.stream().map {
                        convertToHomeProduct(it)
                    }.collect(Collectors.toList())
                    _products.value = concatenateMutable(_products.value!!,resultProduct)
                    _status.value = LoadingStatus.Success
                    if (productResponse.last) {
                        isLastPage = true
                    } else {
                        currentPage++
                    }
                }else{
                    val jsonError: JSONObject = JSONObject(productResponseDTO.errorBody()!!.string())
                    toastMessage.value = jsonError.getString("message")
                }
            }
        }
    }
    private fun convertToHomeProduct(productDTO: ProductDTO): HomeProduct {
        return HomeProduct(
            id = productDTO.id,
            image = Image(productDTO.image),
            name = productDTO.name,
            rating = productDTO.rating.toFloat(),
            price = productDTO.price,
            purchased = productDTO.purchased
        )
    }
    private fun <T> concatenateMutable(vararg lists: MutableList<T>): MutableList<T> {
        val result: MutableList<T> = ArrayList()
        for (list in lists) {
            result.addAll(list)
        }
        return result
    }


    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

}