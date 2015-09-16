package ea.andoridresourcevalues.wirelessDegub.ui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JLabel;
import com.jgoodies.forms.factories.DefaultComponentFactory;

import ea.andoridresourcevalues.listners.EA_IPListener;
import ea.andoridresourcevalues.utils.EA_Constants;
import ea.andoridresourcevalues.wirelessDegub.EA_WinCMDControler;

import javax.swing.JTextArea;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class EA_WinAskIP {

	public JFrame frmEnterIp;
	private JTextField eatxtfld_ipAddress;
	private EA_IPListener ea_IPListener;

	/**
	 * Launch the application.
//	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					AskIP window = new AskIP();
//					window.frmEnterIp.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	/**
	 * Create the application.
	 * @param ea_IPListener listener object 
	 */
	public EA_WinAskIP(EA_IPListener ea_IPListener) {
		this.ea_IPListener = ea_IPListener;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmEnterIp = new JFrame();
		frmEnterIp.setTitle("Enter IP");
		frmEnterIp.setBounds(100, 100, 355, 299);
//		frmEnterIp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmEnterIp.getContentPane().setLayout(null);
		
		eatxtfld_ipAddress = new JTextField();
		eatxtfld_ipAddress.setBounds(10, 61, 319, 20);
		frmEnterIp.getContentPane().add(eatxtfld_ipAddress);
		eatxtfld_ipAddress.setColumns(10);
		
		JLabel lblEnterYourDevice = DefaultComponentFactory.getInstance().createLabel("Enter it in the box below");
		lblEnterYourDevice.setBounds(10, 36, 209, 14);
		frmEnterIp.getContentPane().add(lblEnterYourDevice);
		
		String str_StepsToFindIP = "Steps to find device IP address:".toUpperCase()
				+ "\n"
				+ "\n1. Check if device is connected to same network as that of your development system"
				+ "\n2. open setting >> About Phone >> status"
				+ "\n3. under IP address section you will find IP address of device";
		JTextArea txtrD = new JTextArea();
		txtrD.setText(str_StepsToFindIP);
		txtrD.setEditable(false);
		txtrD.setEnabled(false);
		txtrD.setLineWrap(true);
		txtrD.setBounds(10, 132, 319, 118);
		frmEnterIp.getContentPane().add(txtrD);
		
		JButton btnOk = new JButton("OK");
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String enterTxt = eatxtfld_ipAddress.getText(); 
				if(validateIP(enterTxt)){
					frmEnterIp.dispose();
					ea_IPListener.setIP(enterTxt);
				}
			}

			
		});
		btnOk.setBounds(123, 92, 89, 23);
		frmEnterIp.getContentPane().add(btnOk);
		
		JLabel lblWeCouldNot = DefaultComponentFactory.getInstance().createLabel("We could not find your device IP address");
		lblWeCouldNot.setBounds(10, 11, 319, 14);
		frmEnterIp.getContentPane().add(lblWeCouldNot);
	}
	
	private boolean validateIP(String enterTxt) {
		
		
		if(enterTxt.matches(EA_Constants.ipPattern))
			return true;
		
		return false;
	}
}
