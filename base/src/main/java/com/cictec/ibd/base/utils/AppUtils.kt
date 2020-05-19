package com.cictec.ibd.base.utils

import android.app.Activity
import android.content.*
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.text.TextUtils
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.widget.EditText
import androidx.core.content.FileProvider
import constacne.UiType
import dalvik.system.DexFile
import model.UiConfig
import model.UpdateConfig
import update.UpdateAppUtils
import java.io.File
import java.util.*


/**

 *
 * CopyRight (c)2018: <北京中航讯科技股份有限公司>
 * @author    HanKun
 * @date      2018-08-21
 * @version   1.0
 */


/**
 * 将Dp转化为Px
 *
 * @param context 上下文
 * @param dpVal dp值
 *
 * @return 返回dp转化后的px值
 *
 */
fun dp2px(context: Context, dpVal: Float): Float =
    TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpVal, context.resources.displayMetrics)


/**
 * 获取当前app的名字
 *
 * @param context 上下文
 *
 * @return 返回app的Name
 */
fun getAppName(context: Context): String =
    try {
        context.resources.getString(
            context.packageManager.getPackageInfo(
                context.packageName,
                0
            ).applicationInfo.labelRes
        )
    } catch (e: Exception) {
        ""
    }


/**
 * 获取当前app的版本名称
 *
 * @param context 上下文
 *
 * @return 返回app的版本名称
 */
fun getVersionName(context: Context): String =
    try {
        context.packageManager.getPackageInfo(context.packageName, 0).versionName
    } catch (e: Exception) {
        val s = "123"
        ""
    }


/**
 * 获取当前app的版本号
 *
 * @param context 上下文
 *
 * @return 返回当前app的版本版本号
 */
fun getVersionCode(context: Context): Int =
    try {
        context.packageManager.getPackageInfo(context.packageName, 0).versionCode
    } catch (e: Exception) {
        -1
    }

/**
 * 获取apk文件信息
 *
 * @param filePath 文件路径
 */
private fun apkFileInfo(context: Context, filePath: String): Int {
    return try {
        val packageManager = context.packageManager
        val packageInfo =
            packageManager.getPackageArchiveInfo(filePath, PackageManager.GET_ACTIVITIES)
        packageInfo?.versionCode ?: -1
    } catch (e: Exception) {
        -1
    }
}

/**
 * 获取对应版本apk路径
 */
fun getApkVersionCode(context: Context, versionCode: Int, path: String): String {
    val file = File(path)
    if (file.exists()) {
        val files = file.listFiles()
        if (!files.isNullOrEmpty()) {
            for (file in files) {
                val code = apkFileInfo(context, file.path)
                if (versionCode == code) {
                    return file.path
                }
            }
        }
    }
    return ""
}

fun installApk(context: Context, fileSavePath: String) {
    Log.d("UpdateVersionService", "开始安装新版本")
    val file = File(Uri.parse(fileSavePath).path!!)
    val intent = Intent(Intent.ACTION_VIEW)
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
        val uri = FileProvider.getUriForFile(context, context.packageName + ".fileprovider", file)
        intent.setDataAndType(uri, "application/vnd.android.package-archive")
    } else {
        intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive")
    }
    context.startActivity(intent)
}

/**
 * 获取当前手机系统版本号
 *
 * @return 系统版本号
 */
fun getSystemVersion(): String {
    return android.os.Build.VERSION.RELEASE
}

/**
 * 获取手机型号
 *
 * @return 手机型号
 */
fun getSystemModel(): String {
    return android.os.Build.MODEL
}

/**
 * 获取手机厂商
 *
 * @return 手机厂商
 */
fun getDeviceBrand(): String {
    return android.os.Build.BRAND
}


/**
 * 获取view所持有的context，在某种情况下，view不能正常的获取context直接使用
 *
 * @param view  View
 *
 * @return Context  当没有成功获取则返回null
 */
fun getContext(view: View): Context {
    val context = view.context
    if (context is ContextWrapper) {
        if (context is Activity) {
            return context
        }
        return context.baseContext
    }
    return context
}


/**
 * 获取application标签下的metaData值
 *
 * @param context 上下文
 * @param key     需要获取值得key
 * @return value 取到的值，如果未取到返回null
 */
fun getAppMetaDataValue(context: Context, key: String): String? {
    try {
        val appInfo = context.packageManager
            .getApplicationInfo(context.packageName, PackageManager.GET_META_DATA)
        return appInfo.metaData.getString(key)
    } catch (e: Exception) {
    }

    return null
}


/**
 * 复制内容到剪切板
 *
 * @param copyStr
 * @return
 */
fun copyString(context: Context, copyStr: String): Boolean {
    try {
        val cm = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        cm.setPrimaryClip(ClipData.newPlainText(null, copyStr))
        return true
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return false
}


/**
 *
 * 如果给定参数是空，可以用默认的参数进行替换
 *
 * @param default 默认参数
 */
fun String.stringReplaceNull(default: String = ""): String {
    if (this.isEmpty()) {
        return default
    }
    return this
}


/**
 *
 * 获取一个 uuid
 *
 */
fun getUuid(): String {
    return UUID.randomUUID().toString().replace("-", "")
}


/**
 * 当前窗口亮度
 * 范围为0~1.0,1.0时为最亮，-1为系统默认设置
 */
var Activity.windowBrightness
    get() = window.attributes.screenBrightness
    set(brightness) {
        //小于0或大于1.0默认为系统亮度
        window.attributes = window.attributes.apply {
            screenBrightness = if (brightness > 1.0 || brightness < 0) -1.0F else brightness
        }
    }

/**
 *
 * 获取EditText的值，如果为空，直接返回“”
 *
 */
fun EditText.getTextNotNull(): String {
    val text = this.text.toString()
    return if (TextUtils.isEmpty(text)) "" else text
}


/**
 *
 * 版本更新下载的提示
 *
 * @param versionName 版本名称
 * @param versionCode 版本号
 * @param downloadUrl 下载地址
 * @param updateContent 更新说明
 * @param appLogoResId 显示图标
 * @param apkPath  apk存储路径
 *
 */
fun appUpdate(
    versionName: String,
    versionCode: Int,
    downloadUrl: String,
    updateContent: String,
    appLogoResId: Int,
    apkPath: String
) {
    appUpdate(
        downloadUrl = downloadUrl,
        updateTitle = "发现新版本V${versionName}",
        updateContent = updateContent,
        uiConfig = UiConfig(uiType = UiType.PLENTIFUL),
        updateConfig = UpdateConfig(
            notifyImgRes = appLogoResId,
            isShowNotification = true,
            apkSavePath = apkPath,
            serverVersionName = versionName,
            serverVersionCode = versionCode
        )
    )

}


fun appUpdate(
    downloadUrl: String,
    updateTitle: String,
    updateContent: String,
    uiConfig: UiConfig,
    updateConfig: UpdateConfig
) {
    UpdateAppUtils
        .getInstance()
        .apkUrl(downloadUrl)
        .updateTitle(updateTitle)
        .updateContent(updateContent)
        .uiConfig(uiConfig)
        .updateConfig(updateConfig)
        .update()
}

