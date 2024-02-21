package com.parg3v.domain.use_cases

import android.content.Context
import com.parg3v.domain.extensions.dataStore
import com.parg3v.domain.model.LoginInfo

class DeleteLoginInfoUseCase {
    suspend operator fun invoke(context: Context) {
        context.dataStore.updateData { LoginInfo() }
    }
}