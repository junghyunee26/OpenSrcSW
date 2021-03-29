import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class indexer {
	public void Hashmap(String dir) throws ParserConfigurationException, IOException, SAXException {
		// TODO Auto-generated method stub
		File index = new File(dir);
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = docFactory.newDocumentBuilder();
		Document document = builder.parse(index);
		
		NodeList nodeList = document.getElementsByTagName("doc");
		String[] set;
		HashMap<String, String> hashMap = new HashMap<String, String>();
		ArrayList<String> arrayList = new ArrayList<>();
		
		for(int i = 0; i<nodeList.getLength();i++) {
			Node node = nodeList.item(i);
			set = (node.getLastChild().getTextContent().split("#"));
			for(String wordset : set) {
				String[] word = wordset.split(":");
				for(int k = 0; k<word.length; k+=2) {
					//System.out.println(word[k] + ";" + i + ";" + word[k+1]);
					arrayList.add(word[k] + ";" + i + ";" + word[k+1]);
				}
			}
		}
		for(String item : arrayList) {
			String[] s = item.split(";");
			if(hashMap.containsKey(s[0])) {
				String origin = hashMap.get(s[0]).toString();
				String new_value = origin + ";" + s[1] + " " + s[2];
				hashMap.put(s[0], new_value);
			}
			else {
				hashMap.put(s[0], s[1] + " " + s[2]);
			}
		}
		HashMap<String, ArrayList<String>> map = new HashMap<String, ArrayList<String>>();
		
		for (String key : hashMap.keySet() ) {
			String value = hashMap.get(key);
			System.out.println(key + ":"+value);
			
			String[] valueSet = value.split(";");
			
			for (String s: valueSet) {
				double id = Double.parseDouble(s.substring(0,1));
				double tf = Double.parseDouble(s.substring(2));
				double df = (double) valueSet.length;
				double n = 5.0;
				double weight = tf*(Math.log(n/df));
				System.out.println("key: "+key+"id: "+id+"tf: "+tf+"n: "+ n + "df: "+df+"weight: "+weight+"n/df: "+n/df);
				
				if(map.containsKey(key)) {
					String s1 = map.get(key).toString().replace("["," ").replace("]", " ");
					String s2 = s1 + " " + Math.round(id) + " " + String.format("%.2f", weight);
					ArrayList<String> list = new ArrayList<>();
					list.add(s2);
					map.put(key, list);
				}
				else {
					String s1 = Math.round(id) + " " + String.format("%.2f",  weight);
					ArrayList<String> list = new ArrayList<>();
					list.add(s1);
					map.put(key,list);
				}
			}
			
		}
		FileOutputStream fileOutputStream = new FileOutputStream("C:/Users/totog/SimpleIR/index.post");
		ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
		
		objectOutputStream.writeObject(map);
		objectOutputStream.close();
	}
	
	public void read() throws IOException, ClassNotFoundException{
		FileInputStream fileInputStream = new FileInputStream("C:/Users/totog/SimpleIR/index.post");
		ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
		
		Object object = objectInputStream.readObject();
		objectInputStream.close();
		
		HashMap<String, ArrayList<String>>hashMap = (HashMap) object;
		
		for(String key : hashMap.keySet()) {
			ArrayList<String>value = hashMap.get(key);
			System.out.println(key+"->"+value);
		}
	}

}
