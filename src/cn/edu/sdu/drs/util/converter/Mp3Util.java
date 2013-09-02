package cn.edu.sdu.drs.util.converter;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

public class Mp3Util {

	public static boolean playerXml(final String targetDir,String xmlName,String fileName){
		
		File file=new File(targetDir+xmlName);	
		if(file.exists()){
			file.delete();
		}
		
		String content="<player><playlist><track>" +
				"<file>" +fileName+"</file>" +
				"<title>"+fileName+"</title>" +
				"</track></playlist></player>";
		try {
			FileUtils.writeStringToFile(new File(targetDir+xmlName), content);
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
}
