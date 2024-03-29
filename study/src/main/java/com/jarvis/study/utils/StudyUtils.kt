package com.jarvis.study.utils

import androidx.databinding.BindingAdapter
import com.daimajia.numberprogressbar.NumberProgressBar
import com.jarvis.study.net.StudiedRsp

/**
 * @author jinxiaodong
 * @description：
 * @date 2021/9/8
 */
object StudyUtils {
    @JvmStatic
    fun rankStr(rank: Int): String {
        return if (rank > 0) "第${rank}名" else "千里之外"
    }

    @JvmStatic
    fun parseStudentComment(info: StudiedRsp.Data?): String {
        return "${info?.lessons_played_time} ${info?.comment_count}人评价"
    }

    @JvmStatic
    fun parseFree(info: StudiedRsp.Data?): String {
        return if (info?.is_free == 1) "免费" else "￥${info?.now_price}"
    }

}

@BindingAdapter("app:progress_current", requireAll = false)
fun setProgress(pb: NumberProgressBar, progress: Double?) {
    pb.progress = ((progress ?: 0.0) * 100).toInt()
}