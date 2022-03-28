import org.jsoup.Jsoup;
import org.jsoup.parser.Parser;
import org.snu.ids.kkma.index.Keyword;
import org.snu.ids.kkma.index.KeywordExtractor;
import org.snu.ids.kkma.index.KeywordList;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class makeKeyword {
    void makeKeyword() throws ParserConfigurationException, IOException, TransformerException {
        Integer id = 0;
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

        Document doc = docBuilder.newDocument();
        Element head = doc.createElement("docs" );
        doc.appendChild(head);

        String path_collection = "/Users/dongyullee/SimpleIR/html/collection.xml";
        File dir2 = new File(path_collection);
        org.jsoup.nodes.Document html = Jsoup.parse(dir2, "UTF-8" , "/Users/dongyullee/SimpleIR/html/collection.xml" ,  Parser.xmlParser());

        for(int i=0; i<5; i++) {
            Element doc_Id = doc.createElement("doc");
            doc_Id.setAttribute("id" , id.toString());
            head.appendChild(doc_Id);

            String titleData = html.getElementById(id.toString()).getElementsByTag("title").text();
            String bodyData = html.getElementById(id.toString()).getElementsByTag("body").text();

            Element title = doc.createElement("title");
            title.appendChild(doc.createTextNode(titleData));
            doc_Id.appendChild(title);

            StringBuffer new_bodyData = new StringBuffer();
            KeywordExtractor ke = new KeywordExtractor();
            KeywordList kl = ke.extractKeyword(bodyData, true);
            for (int j = 0; j< kl.size(); j++){
                Keyword kwrd = kl.get(j);
                new_bodyData.append(kwrd.getString() + ":" + kwrd.getCnt() + "#");
            }
            Element body = doc.createElement("body");
            body.appendChild(doc.createTextNode(new_bodyData.toString()));
            doc_Id.appendChild(body);
            id++;
        }

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(new FileOutputStream((new File("/Users/dongyullee/SimpleIR/html/index.xml"))));

        transformer.transform(source,result);
    }
}
