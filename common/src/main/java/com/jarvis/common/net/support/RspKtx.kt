package com.jarvis.common.net.support

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.jarvis.common.net.model.ApiResponse
import com.jarvis.common.net.model.DataResult
import com.jarvis.common.net.model.UNKNOWN_ERROR_CODE
import okhttp3.Response
import retrofit2.Call
import retrofit2.Callback
import retrofit2.await
import retrofit2.awaitResponse
import java.io.IOException

 /**
  * @author jinxiaodong
  * @description： OkHttp3 的 Response的扩展函数，属性
  * @date 2021/8/12
  */


/**
 * okHttp的Call执行异步，并转化为liveData可观察结果
 */
inline fun <reified T> okhttp3.Call.toLiveData(): LiveData<T?> {
    val live = MutableLiveData<T?>()
    this.enqueue(object : okhttp3.Callback {
        override fun onFailure(call: okhttp3.Call, e: IOException) {
            live.postValue(null)
        }

        override fun onResponse(call: okhttp3.Call, response: Response) {
            if (response.isSuccessful) {
                response.toEntity<T>()
            }
        }

    })
    return live
}


/**
 * 将Response的对象，转化为需要的对象类型，也就是将body.string转为entity
 * @return 返回需要的类型对象，可能为null，如果json解析失败的话
 */
inline fun <reified T> Response.toEntity(): T? {
    if (!isSuccessful) return null
    //gson不允许我们将json对象采用String,所以单独处理
    if (T::class.java.isAssignableFrom(String::class.java)) {
        return kotlin.runCatching {
            this.body?.string()
        }.getOrNull() as? T
    }
    return kotlin.runCatching {
        Gson().fromJson(this.body?.string(), T::class.java)
    }.onFailure { e ->
        e.printStackTrace()
    }.getOrNull()
}


//endregion


//region retrofit 相关扩展


/**
 * Retrofit的Call执行异步，并转化为liveData可观察结果
 */
fun <T : Any> Call<T>.toLiveData(): LiveData<T?> {
    val live = MutableLiveData<T?>()
    this.enqueue(object : Callback<T> {
        override fun onFailure(call: Call<T>, t: Throwable) {
            live.postValue(null)
        }

        override fun onResponse(call: Call<T>, response: retrofit2.Response<T>) {
            val value = if (response.isSuccessful) {
                response.body()
            } else {
                null
            }
            live.postValue(value)
        }
    })
    return live
}


/**
 * 扩展retrofit的返回数据，调用await，并catch超时等异常
 * @return DataResult 返回格式为ApiResponse封装
 */
suspend fun <T : Any> Call<T>.serverData(): DataResult<T> {
    var result: DataResult<T> = DataResult.Loading
    kotlin.runCatching {
        this.await()
    }.onFailure {
        result = DataResult.Error(RuntimeException(it))
        it.printStackTrace()
    }.onSuccess {
        result = DataResult.Success(it)
    }
    return result
}


/**
 * 扩展retrofit的返回数据，调用await，并catch超时等异常
 * @return ApiResponse 返回格式为ApiResponse封装
 */
suspend fun <T : Any> Call<T>.serverRsp(): ApiResponse<T> {
    var result: ApiResponse<T>
    val response = kotlin.runCatching {
        this.awaitResponse()
    }.onFailure {
        result = ApiResponse.create(UNKNOWN_ERROR_CODE, it)
        it.printStackTrace()
    }.getOrThrow()
    result = ApiResponse.create(response)
    return result
}

//endregion
