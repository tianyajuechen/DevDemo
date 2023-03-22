package com.tzy.demo.activity.packageinfo

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tzy.demo.bean.PackageInfoBean
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

class PackageInfoViewModel : ViewModel() {

    val packageList = MutableLiveData<List<PackageInfoBean>>()

    /**
     * 通过PackageManager.getInstalledPackages()查询
     */
    fun getList1(packageManager: PackageManager) {
        viewModelScope.launch(Dispatchers.IO) {
            val list = packageManager.getInstalledPackages(0)
            val newList = list.map {
                PackageInfoBean().apply {
                    this.icon = it.applicationInfo.loadIcon(packageManager)
                    this.name = it.applicationInfo.loadLabel(packageManager).toString()
                    this.packageName = it.packageName
                    this.launcher = it.activities?.get(0)?.targetActivity ?: ""
                }
            }.toList()
            packageList.postValue(newList)
        }
    }

    /**
     * 通过隐式意图查询
     */
    fun getList2(packageManager: PackageManager) {
        viewModelScope.launch(Dispatchers.IO) {
            val intent = Intent(Intent.ACTION_MAIN).addCategory(Intent.CATEGORY_LAUNCHER)
            val list = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                packageManager.queryIntentActivities(intent, PackageManager.MATCH_ALL)
            } else {
                packageManager.queryIntentActivities(intent, 0)
            }
            val newList = list.map {
                PackageInfoBean().apply {
                    this.icon = it.loadIcon(packageManager)
                    this.name = it.loadLabel(packageManager).toString()
                    this.packageName = it.activityInfo.packageName
                    this.launcher = it.activityInfo.name
                }
            }.toList()
            packageList.postValue(newList)
        }
    }

    /**
     * 通过adb命令查询
     */
    fun getList3(packageManager: PackageManager) {
        viewModelScope.launch(Dispatchers.IO) {
            val list = mutableListOf<PackageInfoBean>()
            try {
                val process = Runtime.getRuntime().exec("pm list package")
                val br = BufferedReader(InputStreamReader(process.inputStream))
                var line: String?
                while (br.readLine().also { line = it } != null) {
                    val packageName = line!!.split(":")[1]
//                    val packageInfo = packageManager.getPackageInfo(packageName, PackageManager.GET_GIDS)
                    val applicationInfo = packageManager.getApplicationInfo(packageName, 0)
                    val icon = applicationInfo.loadIcon(packageManager)
                    val name = applicationInfo.loadLabel(packageManager).toString()
                    list.add(PackageInfoBean(name, icon, packageName))
                }
            } catch (e: Exception) {
                Log.e("PackageInfo", e.toString())
            }
            packageList.postValue(list)
        }
    }

    /**
     * 通过包名反推
     */
    fun getList4(packageManager: PackageManager, packageArray: Array<String>) {
        viewModelScope.launch(Dispatchers.IO) {
            val list = mutableListOf<PackageInfoBean>()
            packageArray.forEach {
                try {
                    val packageInfo = packageManager.getPackageInfo(it, PackageManager.GET_GIDS)
                    val infoBean = PackageInfoBean().apply {
                        this.icon = packageInfo.applicationInfo.loadIcon(packageManager)
                        this.name = packageInfo.applicationInfo.loadLabel(packageManager).toString()
                        this.packageName = it
                        this.launcher = packageInfo.activities?.get(0)?.targetActivity ?: ""

                    }
                    list.add(infoBean)
                } catch (e: Exception) {
                    Log.e("PackageInfo", e.toString())
                }
            }
            packageList.postValue(list)
        }
    }
}