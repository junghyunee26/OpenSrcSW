package genSnippet;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.xml.sax.SAXException;

public class midterm {
public static void main(String[] args) throws ParserConfigurationException, IOException, TransformerException, SAXException, ClassNotFoundException {
	String arg = args[0];
	if (arg.equals("-i")) {
			String index_dir = args[1];
			indexer2 indexer = new indexer2();
			indexer.Hashmap(index_dir);
			indexer.read();
		
		}
	}

}
