package com.jarvis.home.net

import com.jarvis.service.network.BaseCniaoRsp
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


/**
 * @author jinxiaodong
 * @description：
 * @date 2021/9/15
 */
interface HomeService {

    //region 首页用的 页面配置
    /**
     * 获取模块组件列表
     */
    @GET("/allocation/component/list")
    fun getModuleItems(@Query("module_id") moduleId: Int): Call<BaseCniaoRsp>


    /**
     * 根据页面id 获取页面的模块列表
     * 首页用，id就是1
     */
    @GET("/allocation/module/list")
    fun getPageModuleList(@Query("page_id") pageId: Int = 1): Call<BaseCniaoRsp>

    //endregion



    /**
     * banner列表
     * [type]类型 1:小程序 2:web 3:h5 4:ios 5:android 如: 2表示web 默认2
     * [show]页面显示 1 首页 2 课程 3 大数据学院 4 机器人学院 5 人工智能学院 6 推广员 默认1
     * @Query("type") type: Int=5, @Query("page_show") show: Int 不传参了，现在据首页有数据，且type = web
     */
    @GET("/ad/new/banner/list")
    fun getBannerList(): Call<BaseCniaoRsp>

}