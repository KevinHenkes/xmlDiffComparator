package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;

public class Comparator {
	
	private ArrayList<String> contentLineListOrigin = new ArrayList<String>(), contentLineListTarget = new ArrayList<String>();
	
	private int nbLine = 0;
	
	private Integer firstLineOrigin, firstLineTarget;
	
	public Comparator(String xmlOrigin, String xmlTarget) {
		System.out.println(formatXML(xmlOrigin));
		splitContent(formatXML(xmlOrigin), contentLineListOrigin);
		splitContent(formatXML(xmlTarget), contentLineListTarget);
	}
	
	/**
	 * @return the contentLineListOrigin
	 */
	public ArrayList<String> getContentLineListOrigin() {
		return contentLineListOrigin;
	}

	/**
	 * @param contentLineListOrigin the contentLineListOrigin to set
	 */
	public void setContentLineListOrigin(ArrayList<String> contentLineListOrigin) {
		this.contentLineListOrigin = contentLineListOrigin;
	}

	/**
	 * @return the contentLineListTarget
	 */
	public ArrayList<String> getContentLineListTarget() {
		return contentLineListTarget;
	}

	/**
	 * @param contentLineListTarget the contentLineListTarget to set
	 */
	public void setContentLineListTarget(ArrayList<String> contentLineListTarget) {
		this.contentLineListTarget = contentLineListTarget;
	}

	/**
	 * @return the nbLine
	 */
	public int getNbLine() {
		return nbLine;
	}

	/**
	 * @param nbLine the nbLine to set
	 */
	public void setNbLine(int nbLine) {
		this.nbLine = nbLine;
	}
	
	public void compare() {
		ArrayList<String> parentsLineListOrigin = new ArrayList<String>(), blockContentListOrigin = new ArrayList<String>(), blockContentListTarget = new ArrayList<String>(), parentsLineListTarget = new ArrayList<String>();
		
		int actualLevelDeep = 2;
		
		blockContentListOrigin = getBlock(actualLevelDeep, contentLineListOrigin, firstLineOrigin);
		
		/*if (blockContentListOrigin.size() > 0) {
			parentsLineListOrigin = getParentsLines(blockContentListOrigin.get(0), firstLineOrigin);
		}
		
		blockContentListTarget = getBlock(actualLevelDeep, contentLineListTarget);
		
		if (blockContentListTarget.size() > 0) {
			parentsLineListTarget = getParentsLines(blockContentListTarget.get(0), firstLineTarget);
		}
		
		for (String output : blockContentListOrigin) {
			System.out.println(output);
		}
		
		System.out.println();
		
		for (String output : blockContentListTarget) {
			System.out.println(output);
		}*/
	}
	
	public ArrayList<String> getBlock(int actualLevelDeep, ArrayList<String> contentLineList, Integer firstLine) {
		ArrayList<String> result = new ArrayList<String>();
		
		firstLine = 0;
		
		for (int i = 0;i < contentLineList.size(); i++) {
			String line = contentLineList.get(i);

			if (countDeepLevel(line) >= actualLevelDeep) {
				if (firstLine == 0) {
					firstLine = i;
				}
				
				result.add(line);
			}
		}
		
		return result;
	}
	
	public ArrayList<String> getParentsLines(String line, int i) {
		ArrayList<String> result = new ArrayList<String>();
		//System.out.println(i);
		//System.out.println(line);
		int parentDeepLevel = countDeepLevel(line);
		
		for (int j = i; j >= 0; j--) {
			String lineContentOrigin = contentLineListOrigin.get(j);
			//System.out.println(lineContentOrigin);
			if(countDeepLevel(lineContentOrigin) < parentDeepLevel) {
				result.add(lineContentOrigin);
				parentDeepLevel--;
			}
		}
		
		return result;
	}

	public int countDeepLevel(String line) {
		Pattern pattern = Pattern.compile("   ");
        Matcher  matcher = pattern.matcher(line);

        int count = 0;
        while (matcher.find())
            count++;
        
       return count;
	}
	
	public void splitContent(String xml, ArrayList<String> contentList) {	
		BufferedReader rdr = new BufferedReader(new StringReader(xml));
		
		try {
			for (String line = rdr.readLine(); line != null; line = rdr.readLine()) {
				contentList.add(line);
			}
			rdr.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public String grepNbLine(String line) {
		 return "";
	}
 	
	 public String formatXML(String input)
	    {
	        try
	        {
	            Transformer transformer = TransformerFactory.newInstance()
	                    .newTransformer();
	            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
	            transformer.setOutputProperty(
	                    "{http://xml.apache.org/xslt}indent-amount", "3");

	            StreamResult result = new StreamResult(new StringWriter());
	            DOMSource source = new DOMSource(parseXml(input));
	            transformer.transform(source, result);
	            return result.getWriter().toString();
	        } catch (Exception e)
	        {
	            e.printStackTrace();
	            return input;
	        }
	    }

	    private Document parseXml(String in)
	    {
	        try
	        {
	            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
	            DocumentBuilder db = dbf.newDocumentBuilder();
	            InputSource is = new InputSource(new StringReader(in));
	            return db.parse(is);
	        } catch (Exception e)
	        {
	            throw new RuntimeException(e);
	        }
	    }
}
