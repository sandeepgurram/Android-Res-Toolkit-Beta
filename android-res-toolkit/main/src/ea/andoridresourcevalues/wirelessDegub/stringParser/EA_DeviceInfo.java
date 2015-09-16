package ea.andoridresourcevalues.wirelessDegub.stringParser;

import java.util.ArrayList;
import java.util.regex.Pattern;

import ea.andoridresourcevalues.models.EA_DeviceInfoModel;
import ea.andoridresourcevalues.utils.EA_Constants;
import ea.andoridresourcevalues.wirelessDegub.EA_WinCMDCmd;

public class EA_DeviceInfo {

	private StringBuilder eaStr_CMDmsg;
	private ArrayList<EA_DeviceInfoModel> arrayList_DeviceInfos;
	private int int_currentPosition;
	private EA_DeviceInfoModel deviceInfoModel;
	private EA_WinCMDCmd ea_WinCMD;
	
	public EA_DeviceInfo(StringBuilder stringBuilder) {
		
		eaStr_CMDmsg = stringBuilder;
		arrayList_DeviceInfos = new ArrayList<>();
		int_currentPosition = 0;
		ea_WinCMD = new EA_WinCMDCmd();

	}
	
	public ArrayList<EA_DeviceInfoModel> getDevicesName(){
		
		int int_lineEndPos = eaStr_CMDmsg.indexOf("\n", int_currentPosition);
		int_currentPosition = int_lineEndPos;  //updating current position
//		int_firstLineEndPos++;
		boolean hasNextDevice = false;
	    do{
	    	
//	    	System.out.println("-->\t<--"); 
	    	
	    	int int_posOfSpaceChar = eaStr_CMDmsg.indexOf("\t", int_currentPosition);
	    	int_currentPosition = int_posOfSpaceChar;  
	    	if(int_currentPosition > 1 )
	    		hasNextDevice = true;
	    	else
	    		hasNextDevice = false;
	    	
	    	if(hasNextDevice){
	    		String str_deviceName = eaStr_CMDmsg.subSequence(++int_lineEndPos, int_currentPosition).toString();
	    		int_lineEndPos = eaStr_CMDmsg.indexOf("\n", int_currentPosition);
	    		int_currentPosition = int_lineEndPos;
	    		String str_deviceStatus = eaStr_CMDmsg.subSequence(++int_posOfSpaceChar, int_currentPosition).toString();
	    		
	    		String str_connectionType = getConnectionType(str_deviceName);
	    	
	    		String str_deviceType = getDecviceType(str_deviceName);
	    		
	    		//saving device info
	    		deviceInfoModel = new EA_DeviceInfoModel();
	    		deviceInfoModel.setStrdeviceSerial(str_deviceName);
	    		deviceInfoModel.setStrdeviceType(str_deviceType);
	    		deviceInfoModel.setStrDeviceConnectionType(str_connectionType);
	    		deviceInfoModel.setStrDeviceVersion(ea_WinCMD.ea_executeCMD(EA_Constants.WinCMDs.ADB_PREFIX
	    				+str_deviceName
	    				+EA_Constants.WinCMDs.VERSION).toString());
	    		deviceInfoModel.setStrDeviceManufacturer(ea_WinCMD.ea_executeCMD(EA_Constants.WinCMDs.ADB_PREFIX
	    				+str_deviceName
	    				+EA_Constants.WinCMDs.MANUFATURE_NAME).toString());
	    		if(str_deviceStatus.equals("device"))
	    			deviceInfoModel.setStrdeviceStatus(EA_Constants.DeviceInfo.ONLINE);
	    		else
	    			deviceInfoModel.setStrdeviceStatus(EA_Constants.DeviceInfo.OFFLINE);
	    		
	    		//adding to array list
	    		arrayList_DeviceInfos.add(deviceInfoModel);
	    		
//	    		System.out.println("device manufacturer-->"+deviceInfoModel.getStrDeviceManufacturer());
//	    		System.out.println("device VERSION-->"+deviceInfoModel.getStrDeviceVersion());
	    		
	    	}
	    	
	    }while(hasNextDevice);
		
		return arrayList_DeviceInfos;
	}

	private String getDecviceType(String str_deviceName) {

		String deviceType =EA_Constants.DeviceInfo.REAL ;
		deviceType.contains(EA_Constants.DeviceInfo.EMULATOR.toLowerCase());
		if(deviceType.contains(EA_Constants.DeviceInfo.EMULATOR.toLowerCase()))
			deviceType = EA_Constants.DeviceInfo.EMULATOR;
		

		return deviceType;
		
	}

	private String getConnectionType(String deviceName) {
			
		String connectionType = EA_Constants.DeviceInfo.USB;
//		String ipPattern = "\\d{0,3}.\\d{0,3}.\\d{0,3}.\\d{0,3}:\\d{0,4}";
		if(deviceName.matches(EA_Constants.ipPatternWithPort))
			connectionType = EA_Constants.DeviceInfo.WIRELESS;
		

		return connectionType;
	}

	
}
