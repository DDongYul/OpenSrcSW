import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;

public class Kuir {
    public static void main(String args[]) throws IOException, ParserConfigurationException, TransformerException, ClassNotFoundException {

        String command = args[0];

        switch (command) {
            case "-c":
                makeCollection makeCollection = new makeCollection();
                makeCollection.collection();
                break;
            case "-k":
                makeKeyword makeKeyword = new makeKeyword();
                makeKeyword.makeKeyword();
                break;
            case "-i":
                makeIndexPost indexPost = new makeIndexPost();
                indexPost.makeHashMap();
                indexPost.printHashMap();
                break;
            case  "-s":
                String data = args[1];
                searcher searcher = new searcher();
                searcher.searchDoc(data);
        }
            }
        } 


