package com.nassafy.aro.util

import android.net.Uri
import com.nassafy.aro.BuildConfig

const val BASE_URL = ""
const val SERVER_URL = "https://j8d106.p.ssafy.io/"
val githubLoginUri = Uri.Builder().scheme("https").authority("github.com")
    .appendPath("login")
    .appendPath("oauth")
    .appendPath("authorize")
    .appendQueryParameter("client_id", BuildConfig.GITHUB_CLIENT_ID)
    .build()