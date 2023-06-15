import 'dart:ffi';

import 'package:flutter/foundation.dart';
import 'package:flutter/services.dart';

import 'devicesinfo_platform_interface.dart';

/// An implementation of [DevicesinfoPlatform] that uses method channels.
class MethodChannelDevicesinfo extends DevicesinfoPlatform {
  /// The method channel used to interact with the native platform.
  @visibleForTesting
  final methodChannel = const MethodChannel('devicesinfo');

  @override
  static Future<void> initApp() async {
    await methodChannel.invokeMethod('initApp');
  }

  @override
  static Future<String?> getDeviceInfos() async {
    var data = await methodChannel.invokeMethod('getDeviceInfos');
    return data;
  }

  @override
  static Future<String?> getGeneralData() async {
    var data = await methodChannel.invokeMethod('getGeneralData');
    return data;
  }

  @override
  static Future<String?> getAppListDataArmour() async {
    var data = await methodChannel.invokeMethod('getAppListDataArmour');
    return data;
  }

  @override
  static Future<String?> getBatteryStatusData() async {
    var data = await methodChannel.invokeMethod('getBatteryStatusData');
    return data;
  }

  @override
  static Future<String?> getContactDataArmour() async {
    var data = await methodChannel.invokeMethod('getContactDataArmour');
    return data;
  }

  @override
  static Future<String?> getHardwareData() async {
    var data = await methodChannel.invokeMethod('getHardwareData');
    return data;
  }

  @override
  static Future<String?> getLocationAddressData() async {
    var data = await methodChannel.invokeMethod('getLocationAddressData');
    return data;
  }

  @override
  static Future<String?> getMediaFilesData() async {
    var data = await methodChannel.invokeMethod('getMediaFilesData');
    return data;
  }

  @override
  static Future<String?> getNetWorkData() async {
    var data = await methodChannel.invokeMethod('getNetWorkData');
    return data;
  }

  @override
  static Future<String?> getSimCardData() async {
    var data = await methodChannel.invokeMethod('getSimCardData');
    return data;
  }

  @override
  static Future<String?> getOtherData() async {
    var data = await methodChannel.invokeMethod('getOtherData');
    return data;
  }

  @override
  static Future<String?> getStorageData() async {
    var data = await methodChannel.invokeMethod('getStorageData');
    return data;
  }

}
