package omeronce.android.emptyproject.di.module

import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import omeronce.android.emptyproject.Const
import omeronce.android.emptyproject.model.network.ApiGateway
import omeronce.android.emptyproject.model.network.AuthInterceptor
import omeronce.android.emptyproject.model.network.WebApi
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {
    factory { provideLoggingInterceptor() }
    factory { AuthInterceptor(get()) }
    factory { provideOkHttpClient(get(), get()) }
    single { provideRetrofit(get()) }
    factory { provideApi(get()) }
    single { provideApiGateway() }
}

fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder().baseUrl(Const.BASE_URL).client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create()).build()
}

fun provideOkHttpClient(authInterceptor: AuthInterceptor, loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
    return OkHttpClient().newBuilder().addInterceptor(loggingInterceptor).addInterceptor(authInterceptor).build()
}

fun provideLoggingInterceptor(): HttpLoggingInterceptor {
    val logger = object : HttpLoggingInterceptor.Logger {
        override fun log(message: String) {
            Log.i("OkHttp", message);
        }
    }
    val interceptor = HttpLoggingInterceptor(logger)
    interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
    return interceptor
}

fun provideApi(retrofit: Retrofit): WebApi = retrofit.create(WebApi::class.java)

fun provideApiGateway() = ApiGateway()