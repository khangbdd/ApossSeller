package com.example.apossseller.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.apossseller.model.dto.TokenDTO
import com.example.apossseller.repository.AuthRepository
import com.example.apossseller.repository.OrderRepository
import com.example.apossseller.repository.database.AccountDatabase
import com.example.apossseller.utils.LoadingStatus
import com.example.apossseller.utils.OrderStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.util.stream.Collectors
import javax.inject.Inject

@HiltViewModel
class OrderGeneralViewModel @Inject constructor(
    val orderRepository: OrderRepository,
    val authRepository: AuthRepository,
    @ApplicationContext val context: Context
): ViewModel() {

    val totalOrderOnPlace = MutableLiveData<Int>()
    val totalOrderString = MutableLiveData<String>()

    private var _loadStatus = MutableLiveData<LoadingStatus>()
    val loadStatus: LiveData<LoadingStatus> get() = _loadStatus

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    init {
        loadTotal()
    }

    fun loadTotal()
    {
        val account = AccountDatabase.getInstance(context).accountDao.getAccount()
        val token  = TokenDTO(accessToken = account!!.accessToken, account.tokenType, account.refreshToken)
        _loadStatus.value = LoadingStatus.Loading
        coroutineScope.launch {
            var respond = orderRepository.orderService.countAllOrderOnPlace(token.getFullAccessToken())
            if (respond.code() == 200)
            {
                totalOrderOnPlace.value = respond.body()
                totalOrderString.value = "Total: ${totalOrderOnPlace.value}"
                _loadStatus.value = LoadingStatus.Success
            }
            if (respond.code() == 401)
            {
                val accessTokenResponse =
                    authRepository.getAccessToken(token.refreshToken)
                if (accessTokenResponse.code() == 200) {
                    token.accessToken = accessTokenResponse.body()!!
                    AccountDatabase.getInstance(context).accountDao.updateAccessToken(
                        token.accessToken
                    )
                    loadTotal()
                }
            }
            else {
                _loadStatus.value = LoadingStatus.Fail
            }
        }
    }
}