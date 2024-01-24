package com.utilities;

import java.io.FileReader;
import java.util.Properties;

public class ReadPropertiesFile {

	
	public  Properties readpropertyfile(String fileName) throws Exception {
		
	//	FileReader filereader = new FileReader("./configfiles/config.properties");
		System.out.println(" The Path is"+System.getProperty("user.dir"));
		
		System.out.println(" The file path is"+System.getProperty("user.dir")+"/configfiles/"+fileName+".properties");
		FileReader filereader = new FileReader(System.getProperty("user.dir")+"/configfiles/"+fileName+".properties");
		Properties prop= new Properties();
		prop.clear();
		prop.load(filereader);
		filereader.close();
		return prop;
	}

}
