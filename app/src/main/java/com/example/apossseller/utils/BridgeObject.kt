package com.example.apossseller.utils

import androidx.lifecycle.MutableLiveData

object BridgeObject
{
    var isNeedChangeListener = MutableLiveData<Boolean>(true)
    var statusOrder: String = "Pending"
}
