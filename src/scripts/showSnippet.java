import org.jsoup.Jsoup;
import org.jsoup.parser.Parser;
import org.snu.ids.kkma.index.Keyword;
import org.snu.ids.kkma.index.KeywordExtractor;
import org.snu.ids.kkma.index.KeywordList;

import java.io.*;
import java.util.ArrayList;

public class showSnippet {

    void matchingCount(String query) throws IOException {
        Integer id = 0;
        String path = "SimpleIR/html/coleection.xml";
        File file = new File(path);
        org.jsoup.nodes.Document html = Jsoup.parse(file, "UTF-8", path, Parser.xmlParser());

        StringBuffer query_Data = new StringBuffer();
        KeywordExtractor ke = new KeywordExtractor();
        KeywordList kl = ke.extractKeyword(query, true);
        for (int j = 0; j< kl.size(); j++){
            Keyword kwrd = kl.get(j);
            query_Data.append(kwrd.getString());
        }

        int matching_Count=0;
        String title ;
        String snippet;
        ArrayList<Integer> snapcount = new ArrayList();
        for (int i = 0; i < 5; i++) {
            int count = 0;
            StringBuffer temp = new StringBuffer();
            String titleData = html.getElementById(id.toString()).getElementsByTag("title").text();
            String bodyData = html.getElementById(id.toString()).getElementsByTag("body").text();
            title = titleData.toString();
            while (bodyData.length() >= count + 30) {
                for (int j = count; j < count + 30; j++) {
                    temp.append(bodyData.charAt(j));
                    for(int k=0; k<query_Data.length(); k++){
                        if(query_Data.charAt(k) == temp.charAt(k)){

                        }

                    }
                    count++;
                    temp = new StringBuffer();
                }
            }

        }
        System.out.println();
    }


    public showSnippet() throws IOException {
    }
}
