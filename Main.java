import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.FileNotFoundException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.snu.ids.kkma.index.Keyword;
import org.snu.ids.kkma.index.KeywordExtractor;
import org.snu.ids.kkma.index.KeywordList;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class Main {
	public static void main(String[] args) {
		
		File path = new File("C:\\Users\\totog\\SimpleIR"); // dir
        File[] fileList = path.listFiles();

		if(fileList.length>0) { //empty;
			int id = 0;
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = null;
            try {
            	docBuilder = docFactory.newDocumentBuilder();
            }catch (ParserConfigurationException e) {
            	e.printStackTrace();
            }
            org.w3c.dom.Document doc_2 = docBuilder.newDocument();
            
            Element docs = doc_2.createElement("docs");
            doc_2.appendChild(docs);
            
            for (int i = 0; i<fileList.length; i++) {
            	try {
            		File input = new File(String.valueOf(fileList[i]));
            		String name = String.valueOf(input);
            		
            		if(name.contains(".html")){
            			Document doc = Jsoup.parse(input,"UTF-8");//라이브러리
            			
            			Element doc_1= doc_2.createElement("doc");
            			Element title = doc_2.createElement("title");
            			Element body = doc_2.createElement("body");
            			
            			doc_1.setAttribute("id", String.valueOf(id));
            			title.appendChild(doc_2.createTextNode(doc.title()));
            			title.appendChild(doc_2.createTextNode(doc.text()));
            			
            			doc_1.appendChild(title);
            			doc_1.appendChild(body);
            			docs.appendChild(doc_1);
            			
            			id++;
            		}
            	}catch (IOException e) {
            		e.printStackTrace();
            	}
            }
            try {
            	TransformerFactory transformerFactory = TransformerFactory.newInstance();
    			Transformer transformer = transformerFactory.newTransformer();
    			transformer.setOutputProperty(OutputKeys.ENCODING,"UTF-8");
    			
    			DOMSource source = new DOMSource(doc_2);
    			StreamResult result = new StreamResult(new FileOutputStream(new File("C:\\Users\\totog\\SimpleIR\\collection.xml")));
    			
    			transformer.transform(source,result);

            }
    	catch (Exception e) {
    		e.printStackTrace();
    	}
	
	}
}
}//class Main
