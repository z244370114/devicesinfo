import 'package:plugin_platform_interface/plugin_platform_interface.dart';

import 'devicesinfo_method_channel.dart';

abstract class DevicesinfoPlatform extends PlatformInterface {
  /// Constructs a DevicesinfoPlatform.
  DevicesinfoPlatform() : super(token: _token);

  static final Object _token = Object();

  static DevicesinfoPlatform _instance = MethodChannelDevicesinfo();

  /// The default instance of [DevicesinfoPlatform] to use.
  ///
  /// Defaults to [MethodChannelDevicesinfo].
  static DevicesinfoPlatform get instance => _instance;

  /// Platform-specific implementations should set this with their own
  /// platform-specific class that extends [DevicesinfoPlatform] when
  /// they register themselves.
  static set instance(DevicesinfoPlatform instance) {
    PlatformInterface.verifyToken(instance, _token);
    _instance = instance;
  }

  Future<String?> getPlatformVersion() {
    throw UnimplementedError('platformVersion() has not been implemented.');
  }
}
