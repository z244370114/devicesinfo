package com.zy.devicesinfo.data;

import static com.zy.devicesinfo.utils.GeneralUtils.getSimCardInfo;
import static com.zy.devicesinfo.utils.OtherUtils.isNetState;
import static com.zy.devicesinfo.utils.StorageQueryUtil.queryWithStorageManager;

import com.zy.devicesinfo.UtilsApp;
import com.zy.devicesinfo.utils.NetWorkUtils;
import com.zy.devicesinfo.utils.OtherUtils;

public class DeviceInfos {
    //    HardwareData dhoamrpdewtare = new HardwareData();
//    MediaFilesData dmoemdpieat = new MediaFilesData();
//    OtherData dootmhpeert = new OtherData();
//    SimCardData dsoimmpet = getSimCardInfo();
//    StorageData dsotmopreatge = new StorageData();
//    LocationAddressData dgopmspet = new LocationAddressData();
//    NetWorkData dnoemtpweotrk = NetWorkUtils.getNetWorkInfo(new NetWorkData());
//    SensorData dsoemnpseotr = OtherUtils.getSensorList(new SensorData());
//    BatteryStatusData dboamtpteetry = UtilsApp.batteryStatusData;
//    GeneralData dgoemnpeertal = new GeneralData();

    HardwareData hardwareData = new HardwareData();
    MediaFilesData mediaFilesData = new MediaFilesData();
    OtherData otherData = new OtherData();
    SimCardData simCardData = getSimCardInfo();
    StorageData storageData = queryWithStorageManager(new StorageData());
    LocationAddressData locationAddressData = new LocationAddressData();
    NetWorkData netWorkData = null;
    SensorData sensorDataList = OtherUtils.getSensorList(new SensorData());
    BatteryStatusData batteryStatusData = UtilsApp.batteryStatusData;
    GeneralData generalData = new GeneralData();

    //List<ContactData> contalist = new ContactData().getContactList(new ArrayList<ContactData>());
//    List<AppListData> appListData = new AppListData().getAppListData(new ArrayList<AppListData>());
    {
        if (isNetState() == 1) {
            netWorkData = NetWorkUtils.getNetWorkInfo(new NetWorkData());
        }

    }
}