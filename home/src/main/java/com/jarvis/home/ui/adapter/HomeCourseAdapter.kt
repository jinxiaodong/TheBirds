package com.jarvis.home.ui.adapter

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jarvis.common.webview.WebActivity
import com.jarvis.home.databinding.ItemHomeCourseBinding
import com.jarvis.home.net.HomeCourseItem

/**
 * @author jinxiaodong
 * @description：
 * @date 2021/9/15
 */
class HomeCourseAdapter(private val mList: List<HomeCourseItem>) :
    RecyclerView.Adapter<HomeCourseAdapter.NewClassVH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = NewClassVH.create(parent)

    override fun onBindViewHolder(holder: NewClassVH, position: Int) {
        holder.bind(mList[position])
    }

    override fun getItemCount() = mList.size


    class NewClassVH(private val binding: ItemHomeCourseBinding) :
        RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun create(parent: ViewGroup): NewClassVH {
                return NewClassVH(
                    ItemHomeCourseBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
        }

        fun bind(info: HomeCourseItem) {
            binding.info = info
            binding.tvOldPriceItemCourse.paintFlags += Paint.STRIKE_THRU_TEXT_FLAG
            itemView.setOnClickListener { v ->
                WebActivity.openUrl(v.context, "https://m.cniao5.com/course/${info.id}")
            }
            binding.executePendingBindings()
        }
    }

}