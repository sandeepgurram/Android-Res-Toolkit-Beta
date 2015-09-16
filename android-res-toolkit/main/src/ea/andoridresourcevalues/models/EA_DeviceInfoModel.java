package ea.andoridresourcevalues.models;

public class EA_DeviceInfoModel {

	String strdeviceSerial;
	String strdeviceStatus;
	String strdeviceType;
	String strDeviceConnectionType;
	String strDeviceManufacturer;
	String strDeviceVersion;
	
	public EA_DeviceInfoModel(String strdeviceSerial, String strdeviceStatus, String strdeviceType,
			String strDeviceConnectionType, String strDeviceManufacturer, String strDeviceVersion) {
		super();
		this.strdeviceSerial = strdeviceSerial;
		this.strdeviceStatus = strdeviceStatus;
		this.strdeviceType = strdeviceType;
		this.strDeviceConnectionType = strDeviceConnectionType;
		this.strDeviceManufacturer = strDeviceManufacturer;
		this.strDeviceVersion = strDeviceVersion;
	}
	
	public EA_DeviceInfoModel() {
		super();
		
	}

	public String getStrdeviceSerial() {
		return strdeviceSerial;
	}
	
	public void setStrdeviceSerial(String strdeviceSerial) {
		this.strdeviceSerial = strdeviceSerial;
	}
	
	public String getStrdeviceStatus() {
		return strdeviceStatus;
	}
	
	public void setStrdeviceStatus(String strdeviceStatus) {
		this.strdeviceStatus = strdeviceStatus;
	}
	
	public String getStrdeviceType() {
		return strdeviceType;
	}
	
	public void setStrdeviceType(String strdeviceType) {
		this.strdeviceType = strdeviceType;
	}

	public String getStrDeviceConnectionType() {
		return strDeviceConnectionType;
	}

	public void setStrDeviceConnectionType(String strDeviceConnectionType) {
		this.strDeviceConnectionType = strDeviceConnectionType;
	}

	public String getStrDeviceManufacturer() {
		return strDeviceManufacturer;
	}

	public void setStrDeviceManufacturer(String strDeviceManufacturer) {
		this.strDeviceManufacturer = strDeviceManufacturer;
	}

	public String getStrDeviceVersion() {
		return strDeviceVersion;
	}

	public void setStrDeviceVersion(String strDeviceVersion) {
		this.strDeviceVersion = strDeviceVersion;
	}
	
	
	
}
