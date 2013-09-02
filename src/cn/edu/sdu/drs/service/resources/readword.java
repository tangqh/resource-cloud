package cn.edu.sdu.drs.service.resources;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import org.apache.poi.hwpf.extractor.WordExtractor;

public class readword {

  public  static String getWordStr(String file ) throws Exception{
	  
	 String pathw=new String(file.getBytes("UTF-8"),"gbk");

	    String result = null;
		WordExtractor wordExtractor = null;
		try {
			URL url = new URL(pathw);
			HttpURLConnection urlCon = (HttpURLConnection) url.openConnection();


			wordExtractor = new WordExtractor(urlCon.getInputStream());
			
			result = wordExtractor.getText();
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		
		return(result); 
	    
	    
  }
}   
