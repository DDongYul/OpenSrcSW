import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;


public class Kuir {
    public static void main(String args[]) throws IOException, ParserConfigurationException, TransformerException, ClassNotFoundException {

        String command = args[0];

        if(command.equals("-c")) {
            makeCollection makeCollection = new makeCollection();
            makeCollection.collection();
        }
        else if(command.equals("-k")) {
            makeKeyword makeKeyword = new makeKeyword();
            makeKeyword.makeKeyword();
        }
        else if(command.equals("-i")) {
            makeIndexPost indexPost = new makeIndexPost();
            indexPost.makeHashMap();
            indexPost.printHashMap();
        }
        }
    }

