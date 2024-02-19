package com.parg3v.domain.extensions

import android.content.Context
import androidx.datastore.dataStore
import com.parg3v.domain.config.LoginConfig
import com.parg3v.domain.serializer.LoginSerializer

val Context.dataStore by dataStore(LoginConfig.FILE_NAME, LoginSerializer)
