package omeronce.android.emptyproject.model.network

import android.content.SharedPreferences
import okhttp3.Interceptor
import okhttp3.Response
import omeronce.android.emptyproject.Const

class AuthInterceptor(val sharedPreferences: SharedPreferences): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = sharedPreferences.getString(Const.PREFS_TOKEN_KEY, "2d4d1ba6-3cc5-47d7-999d-b2f20306a2f5")
        var newRequest = chain.request()
        if(!token.isNullOrEmpty()) {
            newRequest = chain.request().newBuilder().addHeader("Authorization", token).build()
        }
        return chain.proceed(newRequest)
    }
}