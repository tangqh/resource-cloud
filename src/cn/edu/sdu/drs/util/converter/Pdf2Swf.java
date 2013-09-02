package cn.edu.sdu.drs.util.converter;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

import cn.edu.sdu.drs.util.AppUtil;

public class Pdf2Swf {
	
	
	public static boolean convert(final String pdfFilePath,
			final String swfFileDir,final String swfFileName) {
		
		//源文件不存在则返回
		File pdf=new File(pdfFilePath);
		if(!pdf.exists()){
			return false;
		}
		
		//目标路径不存在则建立目标路径  
        File swfDir = new File(swfFileDir);  
        if (!swfDir.exists()) {  
        	swfDir.mkdirs();  
        } 
		
        
        
		final String PdfToSwfPath = AppUtil.getPath() + "/resources/pdf2swf.exe ";//swftools工具地址
		String command = PdfToSwfPath + pdfFilePath + " -o " + swfFileDir
				+ swfFileName+".swf" + " -T 9 -f";

		try {
			System.out.println("start....");
			Process pro = Runtime.getRuntime().exec(command);
			
			if(pro==null){
				return false;
			}

			BufferedReader bufferedReader = new BufferedReader(
					new InputStreamReader(pro.getInputStream()));
			
			while (bufferedReader.readLine() != null) {
				String text = bufferedReader.readLine();
				System.out.println("正在转换 "+text);
			}
			
			pro.waitFor();//等待子进程转换完毕
			System.out.println("end!");
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} 
		
		return true;
	}
	
}
