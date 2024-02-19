package com.parg3v.domain.use_cases

import android.content.Context
import com.parg3v.domain.extensions.dataStore
import com.parg3v.domain.model.LoginInfo
import kotlinx.coroutines.flow.first

class GetLoginInfoUseCase {
    suspend operator fun invoke(context: Context): LoginInfo {
        return context.dataStore.data.first()
    }
}