package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		File xmlFile = new File("/home/kevin/eclipse_workspace/xmlDiffComparator/ressources/Origin.xml");
        
        // Let's get XML file as String using BufferedReader
        // FileReader uses platform's default character encoding
        // if you need to specify a different encoding, use InputStreamReader
        Reader fileReader = null;
		try {
			fileReader = new FileReader(xmlFile);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
        BufferedReader bufReader = new BufferedReader(fileReader);
        
        StringBuilder sb = new StringBuilder();
        String line;
        
        String xmlOrigin = "";
        String xmlTarget = "";
        
		try {
			line = bufReader.readLine();
			
			while( line != null){
	            sb.append(line);
	            line = bufReader.readLine();
	        }
	        String xml2String = sb.toString();
	        
	        xmlOrigin = xml2String;
	        
	        bufReader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
		String test ="<books>   <book>    	  <title>Basic XML</title>   	     <testa>   	        <testb>lol</testb>   	     </testa>      <price>100</price>      <qty>5</qty>   </book>   <book>      <title>Basic Java</title>      <price>200</price>      <qty>15</qty>	</book></books>";
		
		/*String xmlOrigin = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><books><book><testa><testb></testb></testa><title>Basic XML</title><price>100</price><qty>5</qty></book><book><title>Basic Java</title><price>200</price><qty>15</qty></book></books>";
		*/xmlTarget = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><books><book><title>Basic XML</title><price>100</price><qty>5</qty></book><book><title>Basic Java</title><price>200</price><qty>15</qty></book></books>";
		
		Comparator comparator = new Comparator(xmlOrigin, xmlTarget);
		comparator.compare();
	}

}
