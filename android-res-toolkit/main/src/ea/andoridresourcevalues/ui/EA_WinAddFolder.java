package ea.andoridresourcevalues.ui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;

import ea.andoridresourcevalues.EA_ValuesFolderManager;
import ea.andoridresourcevalues.utils.EA_Constants;
import ea.andoridresourcevalues.utils.EA_Utils;

import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.awt.event.ActionEvent;

public class EA_WinAddFolder {

	private JFrame frmAddResFolder;
	private JTextField txtValues;
	private JLabel label;

	public JFrame start(){
		final EA_WinAddFolder window = new EA_WinAddFolder();;
		EventQueue.invokeLater(new Runnable() {
			

			public void run() {
				try {
					window.frmAddResFolder.setVisible(true);
					window.frmAddResFolder.addWindowListener(new WindowListener() {
						
						@Override
						public void windowOpened(WindowEvent e) {
							// TODO Auto-generated method stub
							
						}
						
						@Override
						public void windowIconified(WindowEvent e) {
							// TODO Auto-generated method stub
							
						}
						
						@Override
						public void windowDeiconified(WindowEvent e) {
							// TODO Auto-generated method stub
							
						}
						
						@Override
						public void windowDeactivated(WindowEvent e) {
							// TODO Auto-generated method stub
							
						}
						
						@Override
						public void windowClosing(WindowEvent e) {
								
							

						}
						
						@Override
						public void windowClosed(WindowEvent e) {
							// TODO Auto-generated method stub
							
						}
						
						@Override
						public void windowActivated(WindowEvent e) {
							// TODO Auto-generated method stub
							
						}
					});
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		return window.frmAddResFolder;
		
	
		
	}
	
	/**
	 * Create the application.
	 */
	public EA_WinAddFolder() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmAddResFolder = new JFrame();
		frmAddResFolder.setTitle("Add Res Folder");
		frmAddResFolder.setBounds(100, 100, 268, 153);
		frmAddResFolder.getContentPane().setLayout(null);
		
		txtValues = new JTextField();
		txtValues.setText("values-");
		txtValues.setBounds(10, 25, 232, 20);
		frmAddResFolder.getContentPane().add(txtValues);
		txtValues.setColumns(10);
		
		JButton btnCreate = new JButton("Create");
		btnCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				label.setText("");
				String eaStr_FolderName = txtValues.getText();
				File eaDir = new File( EA_Constants.Paths.eaStaticString_workingFolderPath
						+ EA_Constants.Paths.eaStaticString_relativePathToParentValuesFolder
						+"\\"
						+ eaStr_FolderName);
				if(eaDir.exists()){
					label.setText("Folder Already present");
				}else{
					eaDir.mkdir();
					frmAddResFolder.dispose();
					EA_Utils.clearLists();
					new EA_ValuesFolderManager();
				}
			}
		});
		btnCreate.setBounds(75, 56, 89, 23);
		frmAddResFolder.getContentPane().add(btnCreate);
		
		JLabel lblResFolderName = new JLabel("Res folder name");
		lblResFolderName.setBounds(10, 11, 232, 14);
		frmAddResFolder.getContentPane().add(lblResFolderName);
		
		label = new JLabel("");
		label.setBounds(10, 90, 232, 14);
		frmAddResFolder.getContentPane().add(label);
	}
}
