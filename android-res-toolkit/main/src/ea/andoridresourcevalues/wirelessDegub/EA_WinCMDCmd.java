package ea.andoridresourcevalues.wirelessDegub;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import ea.andoridresourcevalues.ui.EA_MainWindow;

public class EA_WinCMDCmd {

	public StringBuilder ea_executeCMD(String cmd){
		
//		System.out.println("Cmd: "+ cmd );
		 StringBuilder strBuildr_cmdOut = new StringBuilder();
		
		  try {
              Runtime rt = Runtime.getRuntime();
              Process pr = rt.exec(cmd);
//              BufferedReader error = new BufferedReader(new InputStreamReader(pr.getErrorStream()));
              BufferedReader input = new BufferedReader(new InputStreamReader(pr.getInputStream()));
              
//              Thread.sleep(1000);
          	
    			String line=null;

                 while((line=input.readLine()) != null) {
//                     System.out.println(line);
                     strBuildr_cmdOut.append(line);
                     strBuildr_cmdOut.append("\n");
                     
                 }
                 
//                 String eline=null;
//
//                 System.out.println("Error-->");
//                 while((eline=error.readLine()) != null) {
//                     System.out.println(eline);
//                     strBuildr_cmdOut.append(eline);
//                     strBuildr_cmdOut.append("\n");
//                     
//                 }
    			
              
              
             

          } catch(Exception e) {
        	  System.out.println("Error-->");
              System.out.println(e.toString());
              e.printStackTrace();
              return null;
          }
		
		return strBuildr_cmdOut;
	}

	
		
	}
