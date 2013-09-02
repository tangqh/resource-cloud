package cn.edu.sdu.drs.cluster;

import java.io.*;


public class ReadIPlist { 
	
	public static void go(){
	
		BufferedReader bufread; 
        String read, readStr; 
        readStr = ""; 
        try { 
        	
            File file = new File(RealPath.Path+"SQL\\iplist.data"); 
            FileReader fileread = new FileReader(file); 
            bufread = new BufferedReader(fileread); 
            while ((read = bufread.readLine()) != null) { 
                Selectlist.Select.add(read); 
            } 
           
        } catch (Exception d) { 
            System.out.println(d.getMessage()); 
        } 
    }
}
