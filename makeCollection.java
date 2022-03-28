import java.io.FileOutputStream;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.w3c.dom.Document;
import org.w3c.dom.Element;



import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class  makeCollection{
    void collection() throws IOException {
        try {
            Integer count = 0;
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

            Document doc = docBuilder.newDocument();
            Element head = doc.createElement("docs" );
            doc.appendChild(head);

            String path = "/Users/dongyullee/SimpleIR/html";
            File dir = new File(path);
            File[] fileList = new File[5];
            fileList = dir.listFiles();


            for(int i=0; i<5; i++) {
                Element doc_Id = doc.createElement("doc");
                doc_Id.setAttribute("id" , count.toString());
                head.appendChild(doc_Id);
                count++;

                org.jsoup.nodes.Document html = Jsoup.parse(fileList[i], "UTF-8");
                String titleData = html.title();
                String bodyData = html.body().text();
                Element title = doc.createElement("title");
                title.appendChild(doc.createTextNode(titleData));
                doc_Id.appendChild(title);
                Element body = doc.createElement("body");
                body.appendChild(doc.createTextNode(bodyData));
                doc_Id.appendChild(body);
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new FileOutputStream((new File("/Users/dongyullee/SimpleIR/html/collection.xml"))));

         transformer.transform(source,result);



        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }

    }
}
