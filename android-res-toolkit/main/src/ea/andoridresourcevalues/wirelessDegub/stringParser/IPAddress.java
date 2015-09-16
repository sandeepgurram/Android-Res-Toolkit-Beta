package ea.andoridresourcevalues.wirelessDegub.stringParser;

public class IPAddress {
	
	static String strIP = "ip";
	
	public static String getIP(StringBuilder strBldr){
		
		
		int intCurrenttPos = strBldr.indexOf(strIP);
		
		intCurrenttPos = intCurrenttPos + strIP.length() ;	
		int intNextSpacePos = strBldr.indexOf(" ",++intCurrenttPos);
		String str_ip = strBldr.subSequence(intCurrenttPos,intNextSpacePos).toString(); 
		
		return str_ip;
		
	}

}
