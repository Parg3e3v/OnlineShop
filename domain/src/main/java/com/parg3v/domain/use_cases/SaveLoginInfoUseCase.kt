package com.parg3v.domain.use_cases

import android.content.Context
import com.parg3v.domain.common.ResultOf
import com.parg3v.domain.extensions.dataStore
import com.parg3v.domain.model.LoginInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException

class SaveLoginInfoUseCase {
    operator fun invoke(context: Context, loginInfo: LoginInfo): Flow<ResultOf<LoginInfo>> = flow {
        try {
            emit(ResultOf.Loading())
            val products = context.dataStore.updateData { loginInfo }
            emit(ResultOf.Success(products))
        } catch (e: IOException) {
            emit(ResultOf.Failure("Couldn't save data"))
        }
    }
}