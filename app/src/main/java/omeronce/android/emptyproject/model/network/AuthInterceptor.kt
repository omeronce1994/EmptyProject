package omeronce.android.emptyproject.model.network

import android.content.SharedPreferences
import okhttp3.Interceptor
import okhttp3.Response
import omeronce.android.emptyproject.Const

class AuthInterceptor(val sharedPreferences: SharedPreferences): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = sharedPreferences.getString(Const.PREFS_TOKEN_KEY, "")
        var newRequest = chain.request()
        if(!token.isNullOrEmpty()) {
            newRequest = chain.request().newBuilder().addHeader("Authorization", token).build()
        }
        return chain.proceed(newRequest)
    }
}