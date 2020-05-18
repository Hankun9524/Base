@file:Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")

package com.cictec.ibd.base.utils

import android.content.Context
import android.graphics.Bitmap
import android.os.Environment
import android.util.Log
import com.cictec.ibd.base.app.BaseModelApplication
import java.io.*


/**
 *
 * 文件操作流
 *
 * CopyRight (c)2018: <北京中航讯科技股份有限公司>
 * @author    HanKun
 * @date      2018-09-12
 * @version   1.0
 */

fun getRootPath(): String =
    BaseModelApplication.getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES)!!.absolutePath


/**
 * 创建一个缓存的存储目录,默认缓存目录
 *
 * @param fileName 文件名称
 * @param catalog  写入目录，默认cache
 *
 */
fun getFilePath(fileName: String, catalog: String = "cache"): String {
    val dirPath = "${getRootPath()}${File.separator}cictecCustomBus${File.separator}$catalog"
    return "$dirPath${File.separator}$fileName"
}

fun getAppFilePath(): String {
    return "${getRootPath()}${File.separator}cictecCustomBus${File.separator}busApp${File.separator}"
}

/**
 * 关闭一个流
 *
 * @param closeable 流
 *
 */
fun closeStream(closeable: Closeable?) {
    try {
        closeable?.close()
    } catch (e: Exception) {
        e.printStackTrace()
    }
}


/**
 * 读取Assets目录下的文件
 *
 * @param context 上下文
 * @param fileName 文件全名
 *
 * @return 返回文件内容
 */
fun getAssetsContent(context: Context, fileName: String): String {
    var inputStream: InputStreamReader? = null
    return try {
        inputStream = InputStreamReader(context.assets.open(fileName))
        inputStream.readText()
    } catch (e: Exception) {
        e.printStackTrace()
        ""
    } finally {
        closeStream(inputStream)
    }
}


/**
 *
 * 读取本地文件内容
 *
 * @param filePath 文件路径
 *
 * @return 返回文件内容
 */
fun getFileContent(filePath: String): String {
    var inputStream: InputStreamReader? = null
    return try {
        inputStream = InputStreamReader(FileInputStream(filePath))
        inputStream.readText()
    } catch (e: Exception) {
        e.printStackTrace()
        ""
    } finally {
        closeStream(inputStream)
    }
}

/**
 * 写入本地文件。
 *
 * @param filePath 文件的绝对路径
 * @param content  写入内容
 * @param append  是否是追加的内容
 *
 */
fun write(filePath: String, content: String, append: Boolean = false) {
    var fw: FileWriter? = null
    var bw: BufferedWriter? = null
    try {
        createFile(filePath)
        fw = FileWriter(filePath, append)
        bw = BufferedWriter(fw)
        //写入相关Log到文件
        bw.run {
            append(content)
            newLine()
        }
    } catch (e: Exception) {
        e.printStackTrace()
    } finally {
        closeStream(bw)
        closeStream(fw)
    }
}

/**
 * 创建文件夹
 */
fun createDirFile(fileDirPath: String) {
    try {
        val file = File(fileDirPath)
        if (!file.exists()) {
            file.mkdirs()
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

/**
 *
 * 创建文件，如果没有的话会连同创建目录
 *
 * @param filePath 文件路径
 *
 */
fun createFile(filePath: String) {
    try {
        val file = File(filePath)
        if (!file.exists()) {
            val dirPath = file.parent
            val dir = File(dirPath)
            if (!dir.exists()) {
                dir.mkdirs()
            }
            file.createNewFile()
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }

}

/** 保存方法  */
fun saveBitmap(bm: Bitmap, name: String): String? {
    var path = getAppFilePath()
    File(path).mkdirs()
    val f = File(path, name)
    if (f.exists()) {
        f.delete()
    }
    try {
        val out = FileOutputStream(f)
        bm.compress(Bitmap.CompressFormat.PNG, 90, out)
        out.flush()
        out.close()
        Log.d("hutao", "保存bitmap完成 路径$path$name")
        return path + name
    } catch (e: FileNotFoundException) {
        e.printStackTrace()
    } catch (e: IOException) {
        e.printStackTrace()
    }
    Log.d("hutao", "保存bitmap失败$path$name")
    return ""
}


