package com.cictec.ibd.base.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import com.cictec.ibd.base.fragment.BigPhotoFragment
import com.cictec.ibd.base.base.EasyMainActivity
import com.cictec.ibd.base.cache.ActiveCache
import com.cictec.ibd.base.cache.UserLoginCache
import com.cictec.ibd.base.config.CLASSNAME
import com.cictec.ibd.base.config.TITLE

/**
 *
 * 所有跳转的集合处理方式
 *
 * CopyRight (c)2018: <北京中航讯科技股份有限公司>
 * @author    HanKun
 * @date      2018-09-12
 * @version   1.0
 */


/**
 * 打开指定Activity
 *
 * @param context 上下文
 * @param bundle 参数
 *
 */
inline fun <reified T> startOpenActivity(
    context: Context,
    bundle: Bundle = Bundle(),
    needFilter: Boolean = true
) {
    if (needFilter) {
        if (BtnClickUtils.isFastDoubleClick()) {
            return
        }
    }
    //在这里拦截判断是否需要进行登录
    if (ActiveCache.needLogin(T::class.java.name)) {
        //拦截跳转，需要进行跳转登录
        UserLoginCache.openLogin(context)
    } else {
        val intent = Intent(context, T::class.java)
        intent.putExtras(bundle)
        context.startActivity(intent)
    }

}

/**
 * 打开大图的加载页面
 * @param context
 * @param url 图片url地址
 *
 * */
fun startOpenBigPhotoPage(context: Context, url: String) {
    val bundle = Bundle()
    bundle.putString("url", url)
    openFragment<BigPhotoFragment>(context, "", bundle)
}


/**
 * 启动指定的服务
 *
 * @param context 上下文
 * @param bundle 参数
 *
 */
inline fun <reified T> startOpenService(context: Context, bundle: Bundle = Bundle()) {
    val intent = Intent(context, T::class.java)
    intent.putExtras(bundle)
    context.startService(intent)
}

/**
 * 关闭服务
 */
inline fun <reified T> stopCloseService(context: Context) {
    val intent = Intent(context, T::class.java)
    context.stopService(intent)
}

/**
 * 启动一个Fragment，默认使用的是管理类进行的启动
 *
 * @param context 上下文
 * @param title 标题名称
 * @param bundle 数据传递类Bundle
 * @param activity 必须是一个可以直接加载fragment处理的管理Activity
 *
 */
inline fun <reified T> openFragment(
    context: Context, title: String = "", bundle: Bundle = Bundle()
) {
    openFragment(context, T::class.java.name, title, bundle)
}

/**
 * 根据fragment全路径名称启动一个页面
 *
 *
 * @param context 上下文
 * @param fragment fragment的全路径
 * @param title 标题名称
 * @param bundle 数据传递类Bundle
 * @param activity 必须是一个可以直接加载fragment处理的管理Activity
 */
inline fun openFragment(
    context: Context, fragment: String, title: String = "", bundle: Bundle = Bundle()
) {
    if (BtnClickUtils.isFastDoubleClick()) {
        return
    }

    if (ActiveCache.needLogin(fragment)) {
        //跳转登录界面
        UserLoginCache.openLogin(context)
    } else {
        bundle.putString(CLASSNAME, fragment)
        bundle.putString(TITLE, title)
        val intent = Intent(context, EasyMainActivity::class.java)
        intent.putExtras(bundle)
        context.startActivity(intent)
    }
}


/**
 *
 * 打开拨号键盘
 *
 * @param context 上下文
 * @param phoneNumber 电话号码
 *
 */
fun callPhone(context: Context, phoneNumber: String) {
    try {
        val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phoneNumber"))
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        context.startActivity(intent)
    } catch (e: Exception) {
        e.printStackTrace()
    }

}


