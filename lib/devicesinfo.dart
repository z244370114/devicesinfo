
import 'devicesinfo_platform_interface.dart';

class Devicesinfo {
  Future<String?> getPlatformVersion() {
    return DevicesinfoPlatform.instance.getPlatformVersion();
  }
}
