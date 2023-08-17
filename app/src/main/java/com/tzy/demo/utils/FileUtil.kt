package com.tzy.demo.utils

import android.content.Context
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

/**
 * @Author tangzhaoyang
 * @Date 2023/8/17
 *
 */
object FileUtil {
    fun assetsToFile(context: Context, assetsPath: String, filePath: String) {
        var inputStream: InputStream? = null
        var fos: FileOutputStream? = null
        try {
            inputStream = context.assets.open(assetsPath)
            fos = FileOutputStream(File(filePath))
            val data = ByteArray(2048)
            var read: Int
            while (inputStream.read(data).also { read = it } > -1) {
                fos.write(data, 0, read)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            try {
                inputStream?.close()
                fos?.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}