package com.nassafy.aro

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.viewbinding.BuildConfig
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.nassafy.aro.util.SERVER_URL
import com.nassafy.aro.util.SharedPreferencesUtil
import dagger.hilt.android.HiltAndroidApp
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit

private const val TAG = "Application_싸피"

@HiltAndroidApp
class Application : Application() {
    override fun onCreate() {
        super.onCreate()

        sharedPreferencesUtil = SharedPreferencesUtil(applicationContext)


    } // End of onCreate

    companion object {
        lateinit var retrofit: Retrofit
        lateinit var headerRetrofit: Retrofit


        lateinit var sharedPreferencesUtil: SharedPreferencesUtil
    } // End of companion object
}  // End of Application class
