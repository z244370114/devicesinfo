import 'package:flutter_test/flutter_test.dart';
import 'package:devicesinfo/devicesinfo.dart';
import 'package:devicesinfo/devicesinfo_platform_interface.dart';
import 'package:devicesinfo/devicesinfo_method_channel.dart';
import 'package:plugin_platform_interface/plugin_platform_interface.dart';

class MockDevicesinfoPlatform
    with MockPlatformInterfaceMixin
    implements DevicesinfoPlatform {

  @override
  Future<String?> getPlatformVersion() => Future.value('42');
}

void main() {
  final DevicesinfoPlatform initialPlatform = DevicesinfoPlatform.instance;

  test('$MethodChannelDevicesinfo is the default instance', () {
    expect(initialPlatform, isInstanceOf<MethodChannelDevicesinfo>());
  });

  test('getPlatformVersion', () async {
    Devicesinfo devicesinfoPlugin = Devicesinfo();
    MockDevicesinfoPlatform fakePlatform = MockDevicesinfoPlatform();
    DevicesinfoPlatform.instance = fakePlatform;

    expect(await devicesinfoPlugin.getPlatformVersion(), '42');
  });
}
