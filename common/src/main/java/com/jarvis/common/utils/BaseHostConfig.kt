package com.jarvis.common.utils

import com.jarvis.common.BuildConfig

/**
 * @author jinxiaodong
 * @description：基础baseUrl的配置，可用于dokit的serverHost
 * @date 2021/9/6
 */


/**
 * 获取当前配置的baseHost
 */

fun getBaseHost(): String {

    return if (BuildConfig.DEBUG) {

        CniaoSpUtils.getString(SP_KEY_BASE_HOST) ?: HOST_PRODUCT
    } else {
        HOST_PRODUCT
    }
}

/**
 * 更新配置host
 */
fun setBaseHost(host: String) {
    CniaoSpUtils.put(SP_KEY_BASE_HOST, host)
}


//配置host的key
private const val SP_KEY_BASE_HOST = "sp_key_base_host"

const val HOST_DEV = "https://course.api.cniao5.com/"//开发环境下的host配置
const val HOST_QA = "https://qa.course.api.cniao5.com/"//qa环境的host配置
const val HOST_PRODUCT = "https://release.course.api.cniao5.com/"//正式配置host
