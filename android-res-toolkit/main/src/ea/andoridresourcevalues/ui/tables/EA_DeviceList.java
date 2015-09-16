package ea.andoridresourcevalues.ui.tables;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import ea.andoridresourcevalues.models.EA_DeviceInfoModel;

public class EA_DeviceList extends AbstractTableModel{

	
	ArrayList<EA_DeviceInfoModel> arrayList_deviceInfos;
	EA_DeviceInfoModel deviceInfo;
	
	
	public EA_DeviceList(ArrayList<EA_DeviceInfoModel> arrayList_deviceInfos) {
		super();
		this.arrayList_deviceInfos = arrayList_deviceInfos;
		deviceInfo = new EA_DeviceInfoModel();
	}

	@Override
	public int getColumnCount() {
		return 6;
	}

	@Override
	public int getRowCount() {
		return arrayList_deviceInfos.size();
	}

	@Override
	public String getValueAt(int row, int col) {
		
		deviceInfo = arrayList_deviceInfos.get(row);
		switch (col) {
		case 0:
			 return deviceInfo.getStrDeviceManufacturer();
		case 1:
			return deviceInfo.getStrdeviceSerial();
		case 2:
			return deviceInfo.getStrDeviceVersion();
		case 3:
			return deviceInfo.getStrdeviceStatus();
		case 4:
			return deviceInfo.getStrDeviceConnectionType();
		case 5:
			return deviceInfo.getStrdeviceType();
		
		}

		return null;
	}
	
	public boolean isCellEditable(int row, int col)
    { return false; }

	@Override
	public String getColumnName(int int_col) {
		switch (int_col) {
		case 0:
			 return "Device Manufacturer";
		case 1:
			return "Serial Number";
		case 2:
			return "Version";
		case 3:
			return "Status";
		case 4:
			return "Conn. Type";
		case 5:
			return "Device Type";
		
		}
		return null;
	}

}
