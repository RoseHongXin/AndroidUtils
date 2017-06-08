package hx.toolkit;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.RandomAccessFile;
import java.util.List;
import java.util.UUID;

/**
 * Created by Rose on 3/29/2017.
 */

public class OSUtil {

    public static int getVersionCode(Context ctx){
        try {
            PackageInfo pi = ctx.getPackageManager().getPackageInfo(ctx.getPackageName(), 0);
            return pi.versionCode;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public static String getVersionName(Context ctx){
        try {
            PackageInfo pi= ctx.getPackageManager().getPackageInfo(ctx.getPackageName(), 0);
            return pi.versionName;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
            return "unknown";
        }
    }

    public static String getMac(Context ctx){
        String macAddress =null;
        WifiManager wifiManager = (WifiManager)ctx.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = (null== wifiManager ? null: wifiManager.getConnectionInfo());
        macAddress = info.getMacAddress();
        return macAddress;
    }
    public static String getMac(){
        String macSerial = null;
        String str = "";
        try {
            Process pp = Runtime.getRuntime().exec("cat /sys/class/net/wlan0/address ");
            InputStreamReader ir = new InputStreamReader(pp.getInputStream());
            LineNumberReader input = new LineNumberReader(ir);
            for (; null != str;) {
                str = input.readLine();
                if (str != null) {
                    macSerial = str.trim();// 去空格
                    break;
                }
            }
        } catch (IOException ex) {
            // 赋予默认值
            ex.printStackTrace();
        }
        return macSerial;
    }
    public static String getDeviceId(Context ctx){
        TelephonyManager tm = (TelephonyManager)ctx.getSystemService(Context.TELEPHONY_SERVICE);
        String deviceId = tm.getDeviceId();
        return deviceId;
    }
    public static String getSimSerialNumber(Context ctx){
        TelephonyManager tm = (TelephonyManager)ctx.getSystemService(Context.TELEPHONY_SERVICE);
        String serialNumber = tm.getSimSerialNumber();
        return serialNumber;
    }
    public static String getAndroidId(Context ctx){
        String androidId = Settings.System.getString(ctx.getContentResolver(), Settings.Secure.ANDROID_ID);
        return androidId;
    }
    public static String getSerialNumber(){
        return Build.SERIAL;
    }
    public static String getInstallationId(Context ctx){
        return new Installation().id(ctx);
    }


    private static class Installation {
        private String sID =null;
        private static final String INSTALLATION = "INSTALLATION";
        public synchronized String id(Context context) {
            if (sID == null) {
                File installation = new File(context.getFilesDir(), INSTALLATION);
                try {
                    if (!installation.exists()) writeInstallationFile(installation);
                    sID = readInstallationFile(installation);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
            return sID;
        }
        private String readInstallationFile(File installation)throws IOException {
            RandomAccessFile f = new RandomAccessFile(installation,"r");
            byte[] bytes = new byte[(int) f.length()];
            f.readFully(bytes);
            f.close();
            return new String(bytes);
        }
        private void writeInstallationFile(File installation)throws IOException {
            FileOutputStream out = new FileOutputStream(installation);
            String id = UUID.randomUUID().toString();
            out.write(id.getBytes());
            out.close();
        }
    }

    public static boolean isActForeground(Activity act) {
        if (act == null) return false;
        ActivityManager am = (ActivityManager) act.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> list = am.getRunningTasks(1);
        String actName = act.getClass().getName();
        if (list != null && list.size() > 0) {
            ComponentName cpn = list.get(0).topActivity;
            if (actName.equals(cpn.getClassName())) return true;
        }
        return false;
    }

}
