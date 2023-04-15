package com.giacomoparisi.data.utils

import com.giacomoparisi.data.BuildConfig


fun isDebuggable(): Boolean = BuildConfig.BUILD_TYPE == "debug"