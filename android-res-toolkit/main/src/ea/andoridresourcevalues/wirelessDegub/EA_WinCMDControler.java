package ea.andoridresourcevalues.wirelessDegub;

import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.prefs.Preferences;

import javax.swing.JOptionPane;

import ea.andoridresourcevalues.listners.EA_IPListener;
import ea.andoridresourcevalues.models.EA_DeviceInfoModel;
import ea.andoridresourcevalues.utils.EA_Constants;
import ea.andoridresourcevalues.wirelessDegub.stringParser.EA_DeviceInfo;
import ea.andoridresourcevalues.wirelessDegub.stringParser.IPAddress;
import ea.andoridresourcevalues.wirelessDegub.ui.EA_WinAskIP;

public class EA_WinCMDControler implements EA_IPListener {

	private boolean eaBool_isSDKEvrmntVarSet = false;
	private EA_WinCMDCmd ea_WinCMD;
	private Preferences prefs;
	private String strIp;

	public EA_WinCMDControler(boolean eaBool_isSDKEvrmntVarSet, Preferences prefs) {
		super();
		this.eaBool_isSDKEvrmntVarSet = eaBool_isSDKEvrmntVarSet;
		this.prefs = prefs;
		ea_WinCMD = new EA_WinCMDCmd();
	}

	public EA_WinCMDControler() {
	}

	public ArrayList<EA_DeviceInfoModel> getConnectedDevicesList() {

		ArrayList<EA_DeviceInfoModel> eaArrayList_DeviceInfo = null;
		if (eaBool_isSDKEvrmntVarSet) {

			eaArrayList_DeviceInfo = new EA_DeviceInfo(ea_WinCMD.ea_executeCMD(EA_Constants.WinCMDs.ADB_DEVICES))
					.getDevicesName();

			if (eaArrayList_DeviceInfo.size() == 0) {
				eaArrayList_DeviceInfo.add(new EA_DeviceInfoModel(null, null, null, null, "No device connected", null));
			}

		}
		return eaArrayList_DeviceInfo;
	}

	public boolean ea_checkSDK() {

		EA_Constants.Paths.staticString_SDKpath = prefs.get(EA_Constants.Prefs.SDK_Path, "");
		StringBuilder strBuildr_result = ea_WinCMD.ea_executeCMD(EA_Constants.WinCMDs.ADB_DEVICES);

		eaBool_isSDKEvrmntVarSet = strBuildr_result.equals(null) ? false : true;

		if (!(!EA_Constants.Paths.staticString_SDKpath.equals("") || eaBool_isSDKEvrmntVarSet)) {
			return false;
		}
		return true;
	}

	public boolean wirelessConct(EA_DeviceInfoModel deviceInfo) {

		// execute "tcpip" cmd
		// String cmd = EA_Constants.WinCMDs.ADB_PREFIX +
		// deviceInfo.getStrdeviceSerial() + EA_Constants.WinCMDs.TCPIP;
		// StringBuilder res = ea_WinCMD.ea_executeCMD(cmd);
		// System.out.println(res);

		// execute "adb connect #.#.#.#" cmd to get IP of device
		if (deviceInfo.getStrdeviceSerial().matches(EA_Constants.ipPatternWithPort)) {
			System.out.println("Already connected");
			return false;
		} else {
			getIP(deviceInfo);
		}

		// try to connect to device wirelessly
		// String cmd = EA_Constants.WinCMDs.ADB_PREFIX +
		// deviceInfo.getStrdeviceSerial() + EA_Constants.WinCMDs.TCPIP;
		// StringBuilder res = ea_WinCMD.ea_executeCMD(cmd);
		// System.out.println(res);
		int eaInt_tryNo = 0;
		do {
			String cmd = EA_Constants.WinCMDs.ADB_PREFIX + deviceInfo.getStrdeviceSerial()
					+ EA_Constants.WinCMDs.WIRELESS_CONNECT + strIp + EA_Constants.WinCMDs.PORT1;
			StringBuilder res = ea_WinCMD.ea_executeCMD(cmd);
			System.out.println(res);
			if (res.indexOf(EA_Constants.WinCMDs.WIRELESS_SUCCESSFULLY_CONNECTED_RESPONSE) >= 0) {
				System.out.println("Successfully connected");
				EA_Constants.arrayList_WirelessConnectedDevicesIP.add(strIp);
				return true;
			} else {
				cmd = EA_Constants.WinCMDs.ADB_PREFIX + deviceInfo.getStrdeviceSerial() + EA_Constants.WinCMDs.TCPIP;
				res = ea_WinCMD.ea_executeCMD(cmd);
				System.out.println(res);
			}
			eaInt_tryNo++;
			System.out.println("Tried " + eaInt_tryNo + " time(s).");
		} while (eaInt_tryNo <= 5);

		return false;

	}

	private void getIP(EA_DeviceInfoModel deviceInfo) {

		String cmd = EA_Constants.WinCMDs.ADB_PREFIX + deviceInfo.getStrdeviceSerial() + EA_Constants.WinCMDs.GET_IP;
		StringBuilder res = ea_WinCMD.ea_executeCMD(cmd);
		strIp = IPAddress.getIP(res);
		// System.out.println("IP-->"+IPAddress.getIP(res));

		if (res.length() <= 0 || strIp.length() == 0)
			askIP();

	}

	private void askIP() {

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EA_WinAskIP window = new EA_WinAskIP(EA_WinCMDControler.this);
					window.frmEnterIp.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

	}

	@Override
	public void setIP(String IP) {
		strIp = IP;
	}

	/**
	 * To connect to devices after unplugging USB
	 * @param eaArraylst_ConnectedDevices connected devices list
	 * @return true if connected to devices.
	 */
	public boolean tryConnectToWirelessDevices(ArrayList<EA_DeviceInfoModel> eaArraylst_ConnectedDevices) {
	
		//TODO connect to removed devices
	    ArrayList<String> aryLst_wirelessDevices = EA_Constants.arrayList_WirelessConnectedDevicesIP;
	    
	    for(int eaTemp = 0; eaTemp < eaArraylst_ConnectedDevices.size() ; eaTemp++){
	    	EA_DeviceInfoModel deviceInfo = eaArraylst_ConnectedDevices.get(eaTemp);
	    	//remove ip address of connected devices
	    	if(deviceInfo.getStrDeviceConnectionType().equals(EA_Constants.DeviceInfo.WIRELESS)){
	    		int pos = aryLst_wirelessDevices.indexOf(deviceInfo.getStrdeviceSerial());
	    		if(pos>=0)
	    		aryLst_wirelessDevices.remove(pos);
	    	}
	    }
	    
	    if(aryLst_wirelessDevices.size()<=0)
	    	return false;
	    
		for(int eaTemp = 0; eaTemp < aryLst_wirelessDevices.size() ; eaTemp++){
			
			String cmd = EA_Constants.WinCMDs.ADB_PREFIX_SIMPLE + EA_Constants.WinCMDs.WIRELESS_CONNECT + 
					aryLst_wirelessDevices.get(eaTemp) + EA_Constants.WinCMDs.PORT1;
			StringBuilder res = ea_WinCMD.ea_executeCMD(cmd);
			System.out.println(res);
		}
		return true;
		
		
	}
	
	
}
