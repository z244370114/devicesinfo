package com.zy.devicesinfo

import android.app.Application
import android.content.Context
import com.google.gson.Gson
import com.zy.devicesinfo.data.AppListDataArmour
import com.zy.devicesinfo.data.BatteryStatusData
import com.zy.devicesinfo.data.DeviceInfos
import com.zy.devicesinfo.data.GeneralData
import com.zy.devicesinfo.data.HardwareData
import com.zy.devicesinfo.data.LocationAddressData
import com.zy.devicesinfo.data.MediaFilesData
import com.zy.devicesinfo.data.NetWorkData
import com.zy.devicesinfo.data.OtherData
import com.zy.devicesinfo.data.SimCardData
import com.zy.devicesinfo.data.StorageData
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler

class MethodCallHandlerImpl(private var context: Context) : MethodCallHandler {

    override fun onMethodCall(call: MethodCall, result: MethodChannel.Result) {
        when (call.method) {
            "initApp" -> {
                UtilsApp.init(context.applicationContext as Application)
            }

            "getDeviceInfos" -> {
                //获取全部设备信息
                val deviceInfo = DeviceInfos()
                result.success(deviceInfo.toString())
            }

            "getGeneralData" -> {
                //获取一般手机信息
                val deviceInfo = GeneralData()
                result.success(Gson().toJson(deviceInfo))
            }

            "getAppListDataArmour" -> {
                //获取手机安装的APP信息
                val appListData = AppListDataArmour.getAppListData()
                result.success(Gson().toJson(appListData))
            }

            "getBatteryStatusData" -> {
                //获取电池信息
                val batteryStatusData = BatteryStatusData()
                result.success(Gson().toJson(batteryStatusData))
            }

            "getContactDataArmour" -> {
                //获取联系人信息
            }

            "getHardwareData" -> {
                //获取手机硬件信息
                val hardwareData = HardwareData()
                result.success(Gson().toJson(hardwareData))
            }

            "getLocationAddressData" -> {
                //获取定位信息
                val locationAddressData = LocationAddressData()
                result.success(Gson().toJson(locationAddressData))

            }

            "getMediaFilesData" -> {
                //获取手机文件个数
                val mediaFilesData = MediaFilesData()
                result.success(Gson().toJson(mediaFilesData))
            }

            "getNetWorkData" -> {
                //获取网络信息
                val netWorkData = NetWorkData()
                result.success(Gson().toJson(netWorkData))
            }

            "getSimCardData" -> {
                //获取手机卡信息
                val simCardData = SimCardData()
                result.success(Gson().toJson(simCardData))

            }

            "getOtherData" -> {
                //获取其他信息
                val otherData = OtherData()
                result.success(Gson().toJson(otherData))

            }

            "getStorageData" -> {
                //获取手机缓存信息，内存卡信息
                val storageData = StorageData()
                result.success(Gson().toJson(storageData))
            }

            else -> {
                result.notImplemented()
            }
        }
    }
}