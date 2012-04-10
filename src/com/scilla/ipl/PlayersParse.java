package com.scilla.ipl;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


public class PlayersParse {
	private static final String FILE_EXTENSION = ".jpg";
	private DocumentBuilder builder;
	private DocumentBuilderFactory factory;
	private final List<TeamDetails> list = new ArrayList();
	private final HashMap<String, String> map = new HashMap();

	private String getNodeValue(NamedNodeMap paramNamedNodeMap,
			String paramString) {
		String str = null;
		Node localNode = paramNamedNodeMap.getNamedItem(paramString);
		if (localNode != null)
			str = localNode.getNodeValue();
		return str;
	}

	public List<TeamDetails> getList() {
		return this.list;
	}

	public String getPlayerName(String paramString) {
		return (String) this.map.get(paramString);
	}

	public void parse(InputStream paramInputStream) {
		int i;
		int j;
		
			try {
				this.factory = DocumentBuilderFactory.newInstance();
				this.builder = this.factory.newDocumentBuilder();
				this.builder.isValidating();
				Document localDocument = this.builder.parse(paramInputStream,
						null);
				localDocument.getDocumentElement().normalize();
				NodeList localNodeList = localDocument
						.getElementsByTagName("player");
				i = localNodeList.getLength();
				j = 0;
				do
				{
				String str1 = localNodeList.item(j).getFirstChild()
						.getNodeValue();
				NamedNodeMap localNamedNodeMap = localNodeList.item(j)
						.getAttributes();
				String str2 = getNodeValue(localNamedNodeMap, "type");
				TeamDetails localTeamsDetails = new TeamDetails(str1, str2,
						getNodeValue(localNamedNodeMap, "country"), str1
								+ ".jpg");
				this.list.add(localTeamsDetails);
				this.map.put(str1, str2);
				j++;
				}
				while (j < i);
			} catch (SAXException localSAXException) {
				localSAXException.printStackTrace();
			
			} catch (IOException localIOException) {
				localIOException.printStackTrace();
			
			} catch (ParserConfigurationException localParserConfigurationException) {
				localParserConfigurationException.printStackTrace();
				
			}
		
	}
}