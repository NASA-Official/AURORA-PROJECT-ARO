package com.nassafy.aro.util.di

import com.nassafy.aro.BuildConfig
import com.nassafy.aro.domain.repository.api.TestApi
import com.nassafy.aro.util.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AuthInterceptorOkHttpClient

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class OtherInterceptorOkHttpClient

@Module
@InstallIn(SingletonComponent::class)
class ApiModule {

    //BASE_URL 제공
    @Provides
    fun provideBaseUrl() = BASE_URL

    //HttpClient 제공
    @Singleton
    @Provides
    @OtherInterceptorOkHttpClient
    fun provideOkHttpClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        if (BuildConfig.DEBUG) {
            logging.level = HttpLoggingInterceptor.Level.BODY
        } else {
            logging.level = HttpLoggingInterceptor.Level.NONE
        }

        return OkHttpClient.Builder()
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .addNetworkInterceptor(HttpLoggingInterceptor())
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .connectTimeout(1, TimeUnit.MINUTES)
            .build()
    } // End of provideOkHttpClient

    //헤더 포함 HttpClient 제공
    @Singleton
    @Provides
    @AuthInterceptorOkHttpClient
    fun provideHeaderOkHttpClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        if (BuildConfig.DEBUG) {
            logging.level = HttpLoggingInterceptor.Level.BODY
        } else {
            logging.level = HttpLoggingInterceptor.Level.NONE
        }

        return OkHttpClient.Builder()
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .addNetworkInterceptor(HttpLoggingInterceptor())
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .connectTimeout(1, TimeUnit.MINUTES)
            .addInterceptor(AppInterceptor())
            .build()
    } // End of provideHeaderOkHttpClient

    @Singleton
    @Provides
    fun provideTestApi(@OtherInterceptorOkHttpClient okHttpClient: OkHttpClient): TestApi {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(provideBaseUrl())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TestApi::class.java)
    }

    @Singleton
    @Provides
    fun provideHeaderTestApi(@AuthInterceptorOkHttpClient okHeaderOkHttpClient: OkHttpClient): TestApi {
        return Retrofit.Builder()
            .client(okHeaderOkHttpClient)
            .baseUrl(provideBaseUrl())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TestApi::class.java)
    }

    inner class AppInterceptor : Interceptor {
        @Throws(IOException::class)
        override fun intercept(chain: Interceptor.Chain): Response = with(chain) {
            val newRequest = request().newBuilder()
                    //todo
                //.addHeader("Authorization", sharedPreferencesUtil.getUserAccessToken())
                .build()
            proceed(newRequest)
        }
    } // End of AppInterceptor inner class

} // End of ApiModule