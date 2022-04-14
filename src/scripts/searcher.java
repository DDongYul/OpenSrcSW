import org.jsoup.Jsoup;
import org.jsoup.parser.Parser;
import org.snu.ids.kkma.index.Keyword;
import org.snu.ids.kkma.index.KeywordExtractor;
import org.snu.ids.kkma.index.KeywordList;

import java.io.*;
import java.util.*;

@SuppressWarnings("unchecked")
public class searcher {
double[] calcSim(HashMap<String , Integer> data) throws IOException, ClassNotFoundException {

    ArrayList tf = new ArrayList();           //tf 값을 담는 리스트 (query에 따라 tf의 개수가 달라지므로 리스트로 선언)
    double[] calcsim = {0.0,0.0,0.0,0.0,0.0};               // 5개의 id의 calcsim을 담을 배열
    double[] cos_calcsim = new double[5];                   // 벡터의 크기 담을 배열
;
    FileInputStream fileStream = new FileInputStream("SimpleIR/html/index.post");
    ObjectInputStream objectInputStream = new ObjectInputStream(fileStream);

    Object object = objectInputStream.readObject();
    objectInputStream.close();

    HashMap indexPost = (HashMap) object;                 //index.post에 저장된 hashmap
    Iterator<String> index = indexPost.keySet().iterator();

    Iterator<String> keyword = data.keySet().iterator();        //query에 담긴 keyword를 담을 iterator
    int i = 0;
    while (keyword.hasNext()) {                                 //tf값을 tf리스트에 담아줌
        String key = keyword.next();
        tf.add(i, data.get(key));
        i++;
    }
    double[] w = new double[i];                                 //indexpost의 <keyword <id,w>> 에서 w값을 담을 배열
    keyword = data.keySet().iterator();                         //keyword iterator 초가화
    while(keyword.hasNext()){                                   //keyword 별로 반복문 돔
        String key = keyword.next();
        while (index.hasNext()){
            String idx = index.next();                          //index.post 돌면서 keywor와 같은게 있는지 탐색
            if(key.equals(idx)){
                HashMap temp = (HashMap)indexPost.get(key);     //index.post <keyword <id,w>> 에서 <id,w> 정보 가져옴
                for(i=0;i<5;i++) {
                    for (int j = 0; j < tf.size(); j++) {
                        w[j] = Double.parseDouble(temp.get(i).toString());      //w[j] 는 id별 가중치를 담음
                        calcsim[i] += w[j] * Double.parseDouble(tf.get(j).toString());  //해당 keyword가 해당 문서에 있으면(w[j]값이 0이 아니면) 유사도 값을 올려준다!
                    }
                }
            }
        }
        index = indexPost.keySet().iterator();                  //index iterator 초기화
    }
    i=0;
    keyword = data.keySet().iterator();
    double a_size = 0.0;
    double b_size = 0.0;
    for(int j=0; j<5; j++) {
        for(int k=0; k<tf.size(); k++) {
            a_size += Math.pow(Double.parseDouble(tf.get(k).toString()) , Double.parseDouble(tf.get(k).toString()));
            b_size += w[k] * w[k];
            System.out.println(Double.parseDouble(tf.get(k).toString()));
            //System.out.println(a_size);
        }
        a_size = 0.0;
        b_size = 0.0;
        cos_calcsim[j] = Math.sqrt(a_size) * Math.sqrt((b_size));
    }
    for(i=0; i<5; i++) {
        calcsim[i] = calcsim[i] / cos_calcsim[i];
        calcsim[i] = (double) Math.round(calcsim[i] * 100) / 100.00;
        //System.out.println(calcsim[i]);
    }
return calcsim;
}



@SuppressWarnings("unchecked")
void searchDoc(String query) throws IOException, ClassNotFoundException {
    HashMap<String , Integer> data = new HashMap();
    StringBuffer query_Data = new StringBuffer();
    KeywordExtractor ke = new KeywordExtractor();
    KeywordList kl = ke.extractKeyword(query, true);
    for (int j = 0; j< kl.size(); j++){
        Keyword kwrd = kl.get(j);
        data.put(kwrd.getString(), kwrd.getCnt());  //<keyword , tf>
    }

    double[] calcSim = new double[5];       //calcsim값 담을 배열
    double[] calcSim_clone = new double[5]; //id 순서찾기위해 정렬할 배열
    calcSim = calcSim(data);
    calcSim_clone = calcSim(data);
    Arrays.sort(calcSim_clone);             //클론만 정렬

    Integer[] id = new Integer[3];          //상위 3개의 id 담을 배열
    int k=4;
    for(int i =0; i<3; i++){
        for(int j=0; j<5; j++){
            if((calcSim_clone[k] - calcSim[j])==0){
                id[i] = j;
            }
        }
        k--;
    }
    String path_collection = "SimpleIR/html/collection.xml";
    File dir2 = new File(path_collection);
    org.jsoup.nodes.Document html = Jsoup.parse(dir2, "UTF-8" , "SimpleIR/html/collection.xml" ,  Parser.xmlParser());

    for (int i=0; i<3; i++) {
        if(calcSim[id[i]] == 0.0) {
            System.out.println("유사도" +(i+1) + "위 문서의 title: "  + "검색된 문서가 없습니다.");
        }
        else {
            String titleData = html.getElementById((id[i].toString())).getElementsByTag("title").text();
            System.out.println("유사도" +(i+1) + "위 문서의 title: "+titleData);
        }
    }
}
}
