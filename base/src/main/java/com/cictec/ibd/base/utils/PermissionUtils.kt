package com.cictec.ibd.base.utils

import android.Manifest
import android.content.ActivityNotFoundException
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.ftd.livepermissions.LivePermissions
import com.ftd.livepermissions.PermissionResult
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

/**
 *
 * 权限分配申请
 *
 * CopyRight (c)2018: <北京中航讯科技股份有限公司>
 * @author    HanKun
 * @date      2018-09-17
 * @version   1.0
 */


class PermissionUtils {
    /**
     * 询问权限是否已经完成
     */
    interface PermissionSuccessCallback {

        /**
         * 全部权限已经申请获得
         */
        fun onSuccess()
    }

    companion object {

        /**
         *
         * 申请系统敏感权限的列表
         *
         */
        fun getPermissionArrays(): Array<String> {
            return arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
        }

        /**
         * 摄像头权限
         */
        fun getPermissionCamera(): Array<String> {
            return arrayOf(Manifest.permission.CAMERA)
        }


        /**
         *
         * 权限的获取
         *
         *  @param activity
         *  @param permission  权限
         *  @param callback  权限询问的回调，成功回调 ，失败会自行处理，是继续询问还是直接默认会弹到权限允许
         *
         */
        fun getPermission(
            activity: AppCompatActivity,
            permission: Array<String>,
            callback: PermissionSuccessCallback
        ) {
            //先检查权限，如果权限不分配，直接退出app
            LivePermissions(activity).requestArray(permission)
                .observe(activity, Observer { result ->
                    when (result) {
                        is PermissionResult.Grant -> {
                            callback.onSuccess()
                        }
                        is PermissionResult.Rationale -> {
                            getPermission(activity, result.permissions, callback)
                        }
                        is PermissionResult.Deny -> {
                            ToastUtil.showLongToast(activity, "请允许权限后再使用")
                            jumpPermissionPage(activity)
                            activity.finish()
                        }
                    }
                })
        }


        /**
         *
         * 权限的获取
         *
         *  @param fragment
         *  @param permission  权限
         *  @param callback  权限询问的回调，成功回调 ，失败会自行处理，是继续询问还是直接默认会弹到权限允许
         *
         */
        fun getPermission(
            fragment: Fragment,
            permission: Array<String>,
            callback: PermissionSuccessCallback
        ) {
            //先检查权限，如果权限不分配，直接退出app
            LivePermissions(fragment).requestArray(permission)
                .observe(fragment, Observer { result ->
                    when (result) {
                        is PermissionResult.Grant -> {
                            callback.onSuccess()
                        }
                        is PermissionResult.Rationale -> {
                            getPermission(fragment, result.permissions, callback)
                        }
                        is PermissionResult.Deny -> {
                            ToastUtil.showLongToast(fragment.context, "请允许权限后再使用")
                            jumpPermissionPage(fragment.context!!)
                        }
                    }
                })
        }


        /**
         *
         * 根据不同的手机进行跳转设置界面的操作
         *
         */
        private fun jumpPermissionPage(context: Context) {
            try {
                val name = Build.MANUFACTURER
                when (name) {
                    "HUAWEI" -> goHuaWeiManager(context)
                    "vivo" -> goVivoManager(context)
                    "OPPO" -> goOppoManager(context)
                    "Coolpad" -> goCoolPadManager(context)
                    "Meizu" -> goMeiZuManager(context)
                    "Xiaomi" -> goMiManager(context)
                    "samsung" -> goSamSungManager(context)
                    "Sony" -> goSonyManager(context)
                    "LG" -> goLGManager(context)
                    else -> goIntentSetting(context)
                }
            } catch (e: Exception) {
                goIntentSetting(context)
            }
        }

        /**
         *
         * LG手机跳转
         *
         */
        private fun goLGManager(mContext: Context) {
            try {
                val packageName = mContext.packageName
                val intent = Intent(packageName)
                val comp = ComponentName(
                    "com.android.settings",
                    "com.android.settings.Settings\$AccessLockSummaryActivity"
                )
                intent.component = comp
                mContext.startActivity(intent)
            } catch (e: Exception) {
                goIntentSetting(mContext)
            }

        }

        /**
         *
         * 索尼手机跳转
         *
         */
        private fun goSonyManager(mContext: Context) {
            try {
                val packageName = mContext.packageName
                val intent = Intent(packageName)
                val comp =
                    ComponentName("com.sonymobile.cta", "com.sonymobile.cta.SomcCTAMainActivity")
                intent.component = comp
                mContext.startActivity(intent)
            } catch (e: Exception) {
                goIntentSetting(mContext)
            }

        }

        /**
         *
         * 华为手机跳转
         *
         */
        private fun goHuaWeiManager(mContext: Context) {
            try {
                val packageName = mContext.packageName
                val intent = Intent(packageName)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                val comp =
                    ComponentName(
                        "com.huawei.systemmanager",
                        "com.huawei.permissionmanager.ui.CustomBusMainActivity"
                    )
                intent.component = comp
                mContext.startActivity(intent)
            } catch (e: Exception) {
                goIntentSetting(mContext)
            }

        }

        /**
         *
         * 获取小米手机的版本
         *
         */
        private fun getMiUiVersion(): String? {
            val propName = "ro.miui.ui.version.name"
            val line: String
            var input: BufferedReader? = null
            try {
                val p = Runtime.getRuntime().exec("getprop $propName")
                input = BufferedReader(InputStreamReader(p.inputStream), 1024)
                line = input.readLine()
                input.close()
            } catch (ex: IOException) {
                ex.printStackTrace()
                return null
            } finally {
                closeStream(input)
            }
            return line
        }


        /**
         *
         * 小米手机的跳转
         *
         */
        private fun goMiManager(mContext: Context) {
            val rom = getMiUiVersion()
            val packageName = mContext.packageName
            val intent = Intent()
            if ("V6" == rom || "V7" == rom) {
                intent.action = "miui.intent.action.APP_PERM_EDITOR"
                intent.setClassName(
                    "com.miui.securitycenter",
                    "com.miui.permcenter.permissions.AppPermissionsEditorActivity"
                )
                intent.putExtra("extra_pkgname", packageName)
            } else if ("V8" == rom || "V9" == rom) {
                intent.action = "miui.intent.action.APP_PERM_EDITOR"
                intent.setClassName(
                    "com.miui.securitycenter",
                    "com.miui.permcenter.permissions.PermissionsEditorActivity"
                )
                intent.putExtra("extra_pkgname", packageName)
            } else {
                goIntentSetting(mContext)
            }
            mContext.startActivity(intent)
        }


        /**
         *
         * 魅族手机的跳转
         *
         */
        private fun goMeiZuManager(mContext: Context) {
            try {
                val packageName = mContext.packageName
                val intent = Intent("com.meizu.safe.security.SHOW_APPSEC")
                intent.addCategory(Intent.CATEGORY_DEFAULT)
                intent.putExtra("packageName", packageName)
                mContext.startActivity(intent)
            } catch (localActivityNotFoundException: ActivityNotFoundException) {
                goIntentSetting(mContext)
            }

        }

        /**
         *
         * 三星手机的跳转
         *
         */
        private fun goSamSungManager(context: Context) {
            goIntentSetting(context)
        }


        /**
         *
         * Oppo手机跳转
         *
         */
        private fun goOppoManager(mContext: Context) {
            doStartApplicationWithPackageName(mContext, "com.coloros.safecenter")
        }


        /**
         *
         * 酷派手机跳转
         *
         */
        private fun goCoolPadManager(context: Context) {
            doStartApplicationWithPackageName(context, "com.yulong.android.security:remote")
        }


        /**
         * vivo手机跳转
         *
         */
        private fun goVivoManager(context: Context) {
            doStartApplicationWithPackageName(context, "com.bairenkeji.icaller")
        }


        /**
         *
         * 根据路径包名开启设置的跳转
         *
         */
        private fun doStartApplicationWithPackageName(mContext: Context, packageName: String) {
            // 通过包名获取此APP详细信息，包括Activities、services、versioncode、name等等
            var packageInfo: PackageInfo? = null
            try {
                packageInfo = mContext.packageManager.getPackageInfo(packageName, 0)
            } catch (e: PackageManager.NameNotFoundException) {
                e.printStackTrace()
            }
            if (packageInfo == null) {
                return
            }
            // 创建一个类别为CATEGORY_LAUNCHER的该包名的Intent
            val resolveIntent = Intent(Intent.ACTION_MAIN, null)
            resolveIntent.addCategory(Intent.CATEGORY_LAUNCHER)
            resolveIntent.setPackage(packageInfo.packageName)
            // 通过getPackageManager()的queryIntentActivities方法遍历
            val resolventList = mContext.packageManager.queryIntentActivities(resolveIntent, 0)
            LogUtil.e("resolventList" + resolventList.size)
            for (i in resolventList.indices) {
                LogUtil.e(resolventList.get(i).activityInfo.packageName + resolventList[i].activityInfo.name)
            }
            val resolveInfo = resolventList.iterator().next()
            if (resolveInfo != null) {
                // packageName参数2 = 参数 packageName参数2
                val packageName = resolveInfo.activityInfo.packageName
                // 这个就是我们要找的该APP的LAUNCHER的Activity[组织形式：packageName参数2.mainActivityName]
                val className = resolveInfo.activityInfo.name
                // LAUNCHER Intent
                val intent = Intent(Intent.ACTION_MAIN)
                intent.addCategory(Intent.CATEGORY_LAUNCHER)
                // 设置ComponentName参数1:packageName参数2:MainActivity路径
                val cn = ComponentName(packageName, className)
                intent.component = cn
                try {
                    mContext.startActivity(intent)
                } catch (e: Exception) {
                    goIntentSetting(mContext)
                }
            }
        }


        /**
         * 跳转至设置界面
         */
        private fun goIntentSetting(mContext: Context) {
            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
            val uri = Uri.fromParts("package", mContext.packageName, null)
            intent.data = uri
            try {
                mContext.startActivity(intent)
            } catch (e: Exception) {
            }
        }
    }

}
