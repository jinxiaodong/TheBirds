package com.jarvis.mine.widget

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import androidx.annotation.Keep
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.ObservableField
import com.jarvis.mine.R
import com.jarvis.mine.databinding.VItemSettingsBinding
import java.util.jar.Attributes

/**
 * @author jinxiaodong
 * @description：
 * @date 2021/8/31
 */
class ItemSettingsView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) :
    ConstraintLayout(context, attrs, defStyleAttr) {


    private var itemBean = ItemSettingsBean()

    private val obItemInfo = ObservableField(itemBean)

    init {
        //管理布局
        VItemSettingsBinding.inflate(LayoutInflater.from(context), this, true).apply {
            info = obItemInfo
        }
        setBackgroundColor(Color.WHITE)

        val attributeSet =
            context.obtainStyledAttributes(attrs, R.styleable.ItemSettingsView).apply {

                //icon 设置
                itemBean.iconRes =
                    getResourceId(R.styleable.ItemSettingsView_icon, R.drawable.ic_gift_card)
                val iconRGB = getColor(R.styleable.ItemSettingsView_iconColor, 0)
                itemBean.iconColor = iconRGB
                //title设置
                itemBean.title = getString(R.styleable.ItemSettingsView_title) ?: ""
                val titleRGB = getColor(
                    R.styleable.ItemSettingsView_titleColor,
                    resources.getColor(R.color.colorPrimaryText)
                )
                itemBean.titleColor = titleRGB

                //desc设置
                itemBean.desc = getString(R.styleable.ItemSettingsView_desc) ?: ""
                val descRGB = getColor(R.styleable.ItemSettingsView_descColor, 0)
                itemBean.descColor = descRGB
                //arrow设置
                itemBean.arrowRes =
                    getResourceId(R.styleable.ItemSettingsView_arrow, R.drawable.ic_right)
                val arrowRGB = getColor(
                    R.styleable.ItemSettingsView_arrowColor,
                    resources.getColor(R.color.colorSecondaryText)
                )
                itemBean.arrowColor = arrowRGB

            }

        attributeSet.recycle()

    }


        //region 设置资源

    /**
     * 设置整个item的对象info
     */
    fun setInfo(info: ItemSettingsBean) {
        itemBean = info
        obItemInfo.set(info)
    }

    /**
     * 设置title
     */
    fun setTitle(title: String) {
        itemBean.title = title
    }

    /**
     * 设置内容描述
     */
    fun setDesc(desc: String) {
        itemBean.desc = desc
    }

    /**
     * 设置icon图标
     */
    fun setIcon(iconRes: Any) {
        itemBean.iconRes = iconRes
    }

    /**
     * 设置icon图标
     */
    fun setArrow(arrowRes: Any) {
        itemBean.arrowRes = arrowRes
    }


    //endregion

    //region 点击事件

    /**
     * 单独点击图标
     */
    fun onClickIcon(listener: OnClickListener) {
        itemBean.iconListener = listener
    }

    /**
     * 单独点击title
     */
    fun onClickTitle(listener: OnClickListener) {
        itemBean.titleListener = listener
    }

    /**
     * 单独点击desc
     */
    fun onClickDesc(listener: OnClickListener) {
        itemBean.descListener = listener
    }

    /**
     * 单独点击箭头
     */
    fun onClickArrow(listener: OnClickListener) {
        itemBean.arrowListener = listener
    }

    //endregion

    //region 设置颜色

    /**
     * 设置标题title颜色
     */
    fun setIconColor(colorRes: Int) {
        itemBean.iconColor = colorRes
    }

    /**
     * 设置标题title颜色
     */
    fun setTitleColor(colorRes: Int) {
        itemBean.titleColor = colorRes
    }

    /**
     * 设置标题title颜色
     */
    fun setDescColor(colorRes: Int) {
        itemBean.descColor = colorRes
    }

    /**
     * 设置标题title颜色
     */
    fun setArrowColor(colorRes: Int) {
        itemBean.arrowColor = colorRes
    }

    //endregion

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        return hasOnClickListeners()
    }

}


@Keep
data class ItemSettingsBean(
    var iconRes: Any = R.drawable.ic_gift_card,
    var title: String = "学习卡 免费领",
    var desc: String = "可以免费领取学习卡",
    var titleColor: Int = R.color.colorPrimaryText,
    var descColor: Int = R.color.colorSecondaryText,
    var iconColor: Int = R.color.colorPrimary,
    var arrowColor: Int = R.color.colorSecondaryText,
    var arrowRes: Any = R.drawable.ic_right
) {
    //item的子View的点击listener
    var iconListener: View.OnClickListener? = null
    var titleListener: View.OnClickListener? = null
    var descListener: View.OnClickListener? = null
    var arrowListener: View.OnClickListener? = null
}