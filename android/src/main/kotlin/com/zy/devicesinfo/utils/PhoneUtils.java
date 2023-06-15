package com.zy.devicesinfo.utils;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.zy.devicesinfo.UtilsApp;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PhoneUtils {
    /**
     * 构造类
     */
    private PhoneUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 判断设备是否是手机
     *
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public static boolean isPhone() {
        TelephonyManager tm = (TelephonyManager) UtilsApp.getApp().getSystemService(Context.TELEPHONY_SERVICE);
        return tm != null && tm.getPhoneType() != TelephonyManager.PHONE_TYPE_NONE;
    }

    /**
     * 获取IMEI码
     * <p>需添加权限 {@code <uses-permission android:name="android.permission.READ_PHONE_STATE"/>}</p>
     *
     * @return IMEI码
     */
    @SuppressLint("HardwareIds")
    public static String getIMEI() {
        TelephonyManager tm = (TelephonyManager) UtilsApp.getApp().getSystemService(Context.TELEPHONY_SERVICE);
        try {
            return tm != null ? tm.getDeviceId() : null;
        } catch (Exception ignored) {

        }
        return getUniquePsuedoID();
    }

    /**
     * 通过读取设备的ROM版本号、厂商名、CPU型号和其他硬件信息来组合出一串15位的号码
     * 其中“Build.SERIAL”这个属性来保证ID的独一无二，当API < 9 无法读取时，使用AndroidId
     *
     * @return 伪唯一ID
     */
    public static String getUniquePsuedoID() {
        String m_szDevIDShort = "35" +
                Build.BOARD.length() % 10 + Build.BRAND.length() % 10 +
                Build.CPU_ABI.length() % 10 + Build.DEVICE.length() % 10 +
                Build.DISPLAY.length() % 10 + Build.HOST.length() % 10 +
                Build.ID.length() % 10 + Build.MANUFACTURER.length() % 10 +
                Build.MODEL.length() % 10 + Build.PRODUCT.length() % 10 +
                Build.TAGS.length() % 10 + Build.TYPE.length() % 10 +
                Build.USER.length() % 10;

        String serial;
        try {
            serial = Build.class.getField("SERIAL").get(null).toString();
            return new UUID(m_szDevIDShort.hashCode(), serial.hashCode()).toString();
        } catch (Exception e) {
            //获取失败，使用AndroidId
            serial = GeneralUtils.getAndroidID();
            if (TextUtils.isEmpty(serial)) {
                serial = "serial";
            }
        }

        return new UUID(m_szDevIDShort.hashCode(), serial.hashCode()).toString();
    }

    /**
     * 获取IMSI码
     * <p>需添加权限 {@code <uses-permission android:name="android.permission.READ_PHONE_STATE"/>}</p>
     *
     * @return IMSI码
     */
    @SuppressLint("HardwareIds")
    public static String getIMSI() {
        TelephonyManager tm = (TelephonyManager) UtilsApp.getApp().getSystemService(Context.TELEPHONY_SERVICE);
        try {
            return tm != null ? tm.getSubscriberId() : null;
        } catch (Exception ignored) {
        }
        return null;
    }

    /**
     * 判断sim卡是否准备好
     *
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public static boolean isSimCardReady() {
        TelephonyManager tm = (TelephonyManager) UtilsApp.getApp().getSystemService(Context.TELEPHONY_SERVICE);
        return tm != null && tm.getSimState() == TelephonyManager.SIM_STATE_READY;
    }

    /**
     * 获取Sim卡运营商名称
     * <p>中国移动、如中国联通、中国电信</p>
     *
     * @return sim卡运营商名称
     */
    public static String getSimOperatorName() {
        TelephonyManager tm = (TelephonyManager) UtilsApp.getApp().getSystemService(Context.TELEPHONY_SERVICE);
        return tm != null ? tm.getSimOperatorName() : null;
    }

    /**
     * 获取Sim卡运营商名称
     * <p>中国移动、如中国联通、中国电信</p>
     *
     * @return 移动网络运营商名称
     */
    public static String getSimOperatorByMnc() {
        TelephonyManager tm = (TelephonyManager) UtilsApp.getApp().getSystemService(Context.TELEPHONY_SERVICE);
        String operator = tm != null ? tm.getSimOperator() : null;
        if (operator == null) {
            return null;
        }
        switch (operator) {
            case "46000":
            case "46002":
            case "46007":
                return "中国移动";
            case "46001":
                return "中国联通";
            case "46003":
                return "中国电信";
            default:
                return operator;
        }
    }

    /**
     * 获取Sim卡序列号
     * <p>
     * Requires Permission:
     * {@link android.Manifest.permission#READ_PHONE_STATE READ_PHONE_STATE}
     *
     * @return 序列号
     */
    public static String getSimSerialNumber() {
        try {
            TelephonyManager tm = (TelephonyManager) UtilsApp.getApp().getSystemService(Context.TELEPHONY_SERVICE);
            String serialNumber = tm != null ? tm.getSimSerialNumber() : null;

            return serialNumber != null ? serialNumber : "";
        } catch (Exception e) {
        }

        return "";
    }

    /**
     * 获取Sim卡的国家代码
     *
     * @return 国家代码
     */
    public static String getSimCountryIso() {
        TelephonyManager tm = (TelephonyManager) UtilsApp.getApp().getSystemService(Context.TELEPHONY_SERVICE);
        return tm != null ? tm.getSimCountryIso() : null;
    }

    /**
     * 获得卡槽数，默认为1
     *
     * @return 返回卡槽数
     */
    public static int getSimCount() {
        int count = 1;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            try {
                SubscriptionManager mSubscriptionManager = (SubscriptionManager) UtilsApp.getApp().getSystemService(Context.TELEPHONY_SUBSCRIPTION_SERVICE);
                if (mSubscriptionManager != null) {
                    count = mSubscriptionManager.getActiveSubscriptionInfoCountMax();
                    return count;
                }
            } catch (Exception ignored) {
            }
        }
        try {
            count = Integer.parseInt(getReflexMethod("getPhoneCount"));
        } catch (MethodNotFoundException ignored) {
        }
        return count;
    }

    /**
     * 获取Sim卡使用的数量
     *
     * @return 0, 1, 2
     */
    @SuppressLint("MissingPermission")
    public static int getSimUsedCount() {
        int count = 0;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            try {
                SubscriptionManager mSubscriptionManager = (SubscriptionManager) UtilsApp.getApp().getSystemService(Context.TELEPHONY_SUBSCRIPTION_SERVICE);
                count = mSubscriptionManager.getActiveSubscriptionInfoCount();
                return count;
            } catch (Exception ignored) {
            }
        }

        TelephonyManager tm = (TelephonyManager) UtilsApp.getApp().getSystemService(Context.TELEPHONY_SERVICE);
        if (tm.getSimState() == TelephonyManager.SIM_STATE_READY) {
            count = 1;
        }
        try {
            if (Integer.parseInt(getReflexMethodWithId("getSimState", "1")) == TelephonyManager.SIM_STATE_READY) {
                count = 2;
            }
        } catch (MethodNotFoundException ignored) {
        }
        return count;
    }

    /**
     * 获取多卡信息
     *
     * @return 多Sim卡的具体信息
     */
    @SuppressLint("MissingPermission")
    public static List<SimInfo> getSimMultiInfo() {
        List<SimInfo> infos = new ArrayList<>();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            //1.版本超过5.1，调用系统方法
            SubscriptionManager mSubscriptionManager = (SubscriptionManager) UtilsApp.getApp().getSystemService(Context.TELEPHONY_SUBSCRIPTION_SERVICE);
            List<SubscriptionInfo> activeSubscriptionInfoList = null;
            if (mSubscriptionManager != null) {
                try {
                    activeSubscriptionInfoList = mSubscriptionManager.getActiveSubscriptionInfoList();
                } catch (Exception ignored) {
                }
            }
            if (activeSubscriptionInfoList != null && activeSubscriptionInfoList.size() > 0) {
                //1.1.1 有使用的卡，就遍历所有卡
                for (SubscriptionInfo subscriptionInfo : activeSubscriptionInfoList) {
                    SimInfo simInfo = new SimInfo();
                    simInfo.mCarrierName = subscriptionInfo.getCarrierName();
                    simInfo.mIccId = subscriptionInfo.getIccId();
                    simInfo.mSimSlotIndex = subscriptionInfo.getSimSlotIndex();
                    simInfo.mNumber = subscriptionInfo.getNumber();
                    simInfo.mCountryIso = subscriptionInfo.getCountryIso();
                    try {
                        simInfo.mImei = getReflexMethodWithId("getDeviceId", String.valueOf(simInfo.mSimSlotIndex));
                        simInfo.mImsi = getReflexMethodWithId("getSubscriberId", String.valueOf(subscriptionInfo.getSubscriptionId()));
                    } catch (MethodNotFoundException ignored) {
                    }
                    infos.add(simInfo);
                }
            }
        } else {
            //2.版本低于5.1的系统，首先调用数据库，看能不能访问到
            Uri uri = Uri.parse("content://telephony/siminfo"); //访问raw_contacts表
            ContentResolver resolver = UtilsApp.getApp().getContentResolver();
            Cursor cursor = resolver.query(uri, new String[]{"_id", "icc_id", "sim_id", "display_name", "carrier_name", "name_source", "color", "number", "display_number_format", "data_roaming", "mcc", "mnc"}, null, null, null);
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    SimInfo simInfo = new SimInfo();
                    simInfo.mCarrierName = cursor.getString(cursor.getColumnIndex("carrier_name"));
                    simInfo.mIccId = cursor.getString(cursor.getColumnIndex("icc_id"));
                    simInfo.mSimSlotIndex = cursor.getInt(cursor.getColumnIndex("sim_id"));
                    simInfo.mNumber = cursor.getString(cursor.getColumnIndex("number"));
                    simInfo.mCountryIso = cursor.getString(cursor.getColumnIndex("mcc"));
                    String id = cursor.getString(cursor.getColumnIndex("_id"));

                    try {
                        simInfo.mImei = getReflexMethodWithId("getDeviceId", String.valueOf(simInfo.mSimSlotIndex));
                        simInfo.mImsi = getReflexMethodWithId("getSubscriberId", String.valueOf(id));
                    } catch (MethodNotFoundException ignored) {
                    }
                    infos.add(simInfo);
                }
                cursor.close();
            }
        }
        return infos;
    }

    @Nullable
    public static String getSecondIMSI() {
        int maxCount = 20;
        if (TextUtils.isEmpty(getIMSI())) {
            return null;
        }
        for (int i = 0; i < maxCount; i++) {
            String imsi = null;
            try {
                imsi = getReflexMethodWithId("getSubscriberId", String.valueOf(i));
            } catch (MethodNotFoundException ignored) {

            }
            if (!TextUtils.isEmpty(imsi) && !imsi.equals(getIMSI())) {
                return imsi;
            }
        }
        return null;
    }

    /**
     * 通过反射获得SimInfo的信息
     * 当index为0时，读取默认信息
     *
     * @param index 位置,用来当subId和phoneId
     * @return {@link SimInfo} sim信息
     */
    @NonNull
    private static SimInfo getReflexSimInfo(int index) {
        SimInfo simInfo = new SimInfo();
        simInfo.mSimSlotIndex = index;
        try {
            simInfo.mImei = getReflexMethodWithId("getDeviceId", String.valueOf(simInfo.mSimSlotIndex));
            //slotId,比较准确
            simInfo.mImsi = getReflexMethodWithId("getSubscriberId", String.valueOf(simInfo.mSimSlotIndex));
            //subId,很不准确
            simInfo.mCarrierName = getReflexMethodWithId("getSimOperatorNameForPhone", String.valueOf(simInfo.mSimSlotIndex));
            //PhoneId，基本准确
            simInfo.mCountryIso = getReflexMethodWithId("getSimCountryIso", String.valueOf(simInfo.mSimSlotIndex));
            //subId，很不准确
            simInfo.mIccId = getReflexMethodWithId("getSimSerialNumber", String.valueOf(simInfo.mSimSlotIndex));
            //subId，很不准确
            simInfo.mNumber = getReflexMethodWithId("getLine1Number", String.valueOf(simInfo.mSimSlotIndex));
            //subId，很不准确
        } catch (MethodNotFoundException ignored) {
        }
        return simInfo;
    }

    /**
     * 通过反射调取@hide的方法
     *
     * @param predictedMethodName 方法名
     * @return 返回方法调用的结果
     * @throws MethodNotFoundException 方法没有找到
     */
    private static String getReflexMethod(String predictedMethodName) throws MethodNotFoundException {
        String result = null;
        TelephonyManager telephony = (TelephonyManager) UtilsApp.getApp().getSystemService(Context.TELEPHONY_SERVICE);
        try {
            Class<?> telephonyClass = Class.forName(telephony.getClass().getName());
            Method getSimID = telephonyClass.getMethod(predictedMethodName);
            Object ob_phone = getSimID.invoke(telephony);
            if (ob_phone != null) {
                result = ob_phone.toString();
            }
        } catch (Exception e) {
            throw new MethodNotFoundException(predictedMethodName);
        }
        return result;
    }

    /**
     * 通过反射调取@hide的方法
     *
     * @param predictedMethodName 方法名
     * @param id                  参数
     * @return 返回方法调用的结果
     * @throws MethodNotFoundException 方法没有找到
     */
    private static String getReflexMethodWithId(String predictedMethodName, String id) throws MethodNotFoundException {
        String result = null;
        TelephonyManager telephony = (TelephonyManager) UtilsApp.getApp().getSystemService(Context.TELEPHONY_SERVICE);
        try {
            Class<?> telephonyClass = Class.forName(telephony.getClass().getName());
            Class<?>[] parameter = new Class[1];
            parameter[0] = int.class;
            Method getSimID = telephonyClass.getMethod(predictedMethodName, parameter);
            Class<?>[] parameterTypes = getSimID.getParameterTypes();
            Object[] obParameter = new Object[parameterTypes.length];
            if (parameterTypes[0].getSimpleName().equals("int")) {
                obParameter[0] = Integer.valueOf(id);
            } else if (parameterTypes[0].getSimpleName().equals("long")) {
                obParameter[0] = Long.valueOf(id);
            } else {
                obParameter[0] = id;
            }
            Object ob_phone = getSimID.invoke(telephony, obParameter);
            if (ob_phone != null) {
                result = ob_phone.toString();
            }
        } catch (Exception e) {
            throw new MethodNotFoundException(predictedMethodName);
        }
        return result;
    }

    /**
     * SIM 卡信息
     */
    public static class SimInfo {
        /**
         * 运营商信息：中国移动 中国联通 中国电信
         */
        public CharSequence mCarrierName;
        /**
         * 卡槽ID，SimSerialNumber
         */
        public CharSequence mIccId;
        /**
         * 卡槽id， -1 - 没插入、 0 - 卡槽1 、1 - 卡槽2
         */
        public int mSimSlotIndex;
        /**
         * 号码
         */
        public CharSequence mNumber;
        /**
         * 城市
         */
        public CharSequence mCountryIso;
        /**
         * 设备唯一识别码
         */
        public CharSequence mImei = getIMEI();
        /**
         * SIM的编号
         */
        public CharSequence mImsi;

        /**
         * 通过 IMEI 判断是否相等
         *
         * @param obj
         * @return
         */
        @Override
        public boolean equals(Object obj) {
            return obj != null && obj instanceof SimInfo && (TextUtils.isEmpty(((SimInfo) obj).mImei) || ((SimInfo) obj).mImei.equals(mImei));
        }

        @Override
        public String toString() {
            return "SimInfo{" +
                    "mCarrierName=" + mCarrierName +
                    ", mIccId=" + mIccId +
                    ", mSimSlotIndex=" + mSimSlotIndex +
                    ", mNumber=" + mNumber +
                    ", mCountryIso=" + mCountryIso +
                    ", mImei=" + mImei +
                    ", mImsi=" + mImsi +
                    '}';
        }
    }

    /**
     * 反射未找到方法
     */
    private static class MethodNotFoundException extends Exception {

        public static final long serialVersionUID = -3241033488141442594L;

        MethodNotFoundException(String info) {
            super(info);
        }
    }
}