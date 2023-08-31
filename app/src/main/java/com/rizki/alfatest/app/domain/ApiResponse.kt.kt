package com.rizki.alfatest.app.domain

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.rizki.alfatest.ext.exception.ForbiddenException
import com.skollaedutech.skolla.ext.constant.NetworkCodes
import com.skollaedutech.skolla.ext.exeption.NoNetworkException
import com.rizki.alfatest.ext.other.ErrorCodesMapper
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject
import org.json.JSONTokener
import retrofit2.HttpException
import java.io.IOException
import java.net.ConnectException
import java.net.SocketTimeoutException

sealed class ResultWrapper<out T> {
    data class Success<out T>(val value: T) : ResultWrapper<T>()
    data class GenericError(val code: Int, val message: String? = null) : ResultWrapper<Nothing>()
}

suspend fun <T> safeApiCall(
    dispatcher: CoroutineDispatcher, // to define coroutine you want to use
    errorCodesMapper: ErrorCodesMapper, // mapping the error code with message you already define eg: if NO_CONNECTION the return message is "No Internet Connection, make sure your device connected to Internet"
    apiCall: suspend () -> T?
): ResultWrapper<T?> {
    return withContext(dispatcher) { // Calls the specified suspending block with a given coroutine context, suspends until it completes, and returns the result.
        try {
            val call = apiCall.invoke() // call api
            ResultWrapper.Success(call) // if succes it will return the value
        } catch (throwable: Throwable) {
            throwable.printStackTrace() // if not success
            when (throwable) {
                is NoNetworkException -> { // this when value is actually from exception list. but we create a class to make the code more readable
                    ResultWrapper.GenericError(
                        code = NetworkCodes.NO_CONNECTION, // we can create custom network codes in this object, so this object can be our constant list
                        message = errorCodesMapper.getMessage(NetworkCodes.NO_CONNECTION)
                    )
                }
                is SocketTimeoutException -> {
                    ResultWrapper.GenericError(
                        code = NetworkCodes.TIMEOUT_ERROR,
                        message = errorCodesMapper.getMessage(NetworkCodes.CONNECTION_ERROR)
                    )
                }
                is ConnectException -> {
                    ResultWrapper.GenericError(
                        code = NetworkCodes.TIMEOUT_ERROR,
                        message = errorCodesMapper.getMessage(NetworkCodes.CONNECTION_ERROR)
                    )
                }
                is TimeoutCancellationException -> {
                    ResultWrapper.GenericError(
                        code = NetworkCodes.TIMEOUT_ERROR,
                        message = errorCodesMapper.getMessage(NetworkCodes.CONNECTION_ERROR)
                    )
                }
                is IOException -> {
                    ResultWrapper.GenericError(
                        code = NetworkCodes.CONNECTION_ERROR,
                        message = errorCodesMapper.getMessage(NetworkCodes.CONNECTION_ERROR)
                    )
                }
                is ForbiddenException -> {
                    ResultWrapper.GenericError(
                        code = NetworkCodes.FORBIDDEN,
                        message = errorCodesMapper.getMessage(NetworkCodes.FORBIDDEN)
                    )
                }
                is HttpException -> {
                    val code = throwable.code()
                    ResultWrapper.GenericError(
                        code = code, message = convertErrorBody(throwable)
                    )
                }
                is NullPointerException -> {
                    val code = NetworkCodes.HTTP_NO_CONTENT
                    ResultWrapper.GenericError(
                        code = code, message = errorCodesMapper.getMessage(NetworkCodes.GENERIC_ERROR)
                    )
                }
                else -> {
                    ResultWrapper.GenericError(
                        code = NetworkCodes.GENERIC_ERROR,
                        message = errorCodesMapper.getMessage(NetworkCodes.GENERIC_ERROR)
                    )
                }
            }
        }
    }
}

// for custom error body
private fun convertErrorBody(throwable: HttpException): String? {
    try {
        val json = JSONTokener(throwable.response()?.errorBody()?.string()).nextValue()
        if (json is JSONObject || json is JSONArray) {
            val gson = Gson()
            val typeCustom = object : TypeToken<ErrorResponse>() {}.type
            val errorResponse = gson.fromJson<ErrorResponse>(json.toString(), typeCustom)

            errorResponse?.let { return it.message }
        }
        return null
    } catch (exception: Exception) {
        return null
    }
}

class ErrorResponse(val message: String? = "")