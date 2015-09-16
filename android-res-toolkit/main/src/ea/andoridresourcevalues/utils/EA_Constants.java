package ea.andoridresourcevalues.utils;

import java.util.ArrayList;
import java.util.HashMap;

public class EA_Constants {

	public static String eaStaticString_ValeFolderName = "value";
	public static int eaStaticInteger_RowNumber = 0;
	public static HashMap<Integer, String> eaStaticMap_FileName; //stores the file name for value.
	public static ArrayList<String> arrayList_WirelessConnectedDevicesIP; //to store IP names of wirelessly connected devices
	public static final String ipPatternWithPort = "\\d{1,3}.\\d{1,3}.\\d{1,3}.\\d{1,3}:\\d{0,4}";
	public static final String ipPattern = "\\d{1,3}.\\d{1,3}.\\d{1,3}.\\d{1,3}";

	public static class Paths { 

		public static String eaStaticString_workingFolderPath = "";
		public static final String eaStaticString_relativePathToParentValuesFolder = "\\src\\main\\res";
		public static String staticString_SDKpath = "";
		public static final String staticStr_relPlatformToolsPath = "\\platform-tools";

	}
	
	public static class XMLAttributes {
		
		public static final String RootTag = "resources";
		public static final String AttributeName  = "name";
		public static final String dimensionTagNam = "dimen";
		
	}

	public static class Prefs {

		public static final String WorkingFolderPath = "proejectFolder";
		public static final String SDK_Path = "SDKpath";
		
	}

   public static class WinCMDs{
	   
	   public static final String ADB_DEVICES = "adb devices";
	   public static final String ADB_PREFIX_SIMPLE = "adb ";
	   public static final String ADB_PREFIX = "adb -s ";
	   public static final String MANUFATURE_NAME = " shell getprop ro.product.model";
	   public static final String VERSION = " shell getprop ro.build.version.release";
	   public static final String TCPIP = " tcpip 5555";
	   public static final String WIRELESS_CONNECT = " connect ";
	   public static final String GET_IP = " shell ifconfig wlan0";
	   public static final String PORT1 = ":5555";
	public static final String WIRELESS_SUCCESSFULLY_CONNECTED_RESPONSE = "connected to ";
	
   }
   
   public final static class DeviceInfo{
	   
	   public static final String ONLINE = "Online";
	public static final String OFFLINE = "Offline";
	public static final String REAL = "Real";
	public static final String EMULATOR = "emulator";
	public static final String USB = "USB";
	public static final String WIRELESS = "Wireless";
	
	
	   
   }
   
}
