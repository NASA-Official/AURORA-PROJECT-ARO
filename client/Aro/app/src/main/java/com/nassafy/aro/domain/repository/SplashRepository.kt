package com.nassafy.aro.domain.repository

import com.nassafy.aro.domain.api.SplashApi
import com.nassafy.aro.util.di.HeaderInterceptorApi
import com.nassafy.aro.util.di.WithoutHeaderInterceptorApi
import javax.inject.Inject

class SplashRepository @Inject constructor(
    @WithoutHeaderInterceptorApi private val splashApi: SplashApi,
    @HeaderInterceptorApi private val splashHeaderApi: SplashApi
) {




} // End of SplashRepository
