package com.example.apossseller.repository

import android.provider.SyncStateContract
import android.util.Log
import com.example.apossseller.utils.Constants
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.text.SimpleDateFormat
import java.util.*

object RetrofitInstance {
    lateinit var retrofit: Retrofit;

    init {
        try {
            val client = OkHttpClient.Builder().apply {
                addInterceptor(MyInterceptor())
            }.build()

            val moshi = Moshi.Builder()
                .add(KotlinJsonAdapterFactory())
                .add(Date::class.java, DateJsonAdapter())
                .build()
            retrofit = Retrofit.Builder()
                .addConverterFactory(MoshiConverterFactory.create(moshi).asLenient())
                .addCallAdapterFactory(CoroutineCallAdapterFactory()).baseUrl(
                    Constants.BASE_URL
                )
                .client(client)
                .build()
        } catch (e: Exception) {
            Log.d("Retrofit", "Server error!")
        }
    }

    class DateJsonAdapter : JsonAdapter<Date>() {
        private val dateFormat = "yyyy-MM-dd HH:mm:ss"

        private val sdFormat = SimpleDateFormat(dateFormat, Locale.JAPAN)


        @Synchronized
        @Throws(Exception::class)
        override fun fromJson(reader: com.squareup.moshi.JsonReader): Date? {
            val string = reader.nextString()

            return sdFormat.parse(string)
        }

        @Synchronized
        @Throws(Exception::class)
        override fun toJson(writer: com.squareup.moshi.JsonWriter, value: Date?) {
            writer.value(sdFormat.format(value!!))
        }
    }
}