import org.jsoup.Jsoup;
import org.jsoup.parser.Parser;

import java.io.*;
import java.util.HashMap;
import java.util.Iterator;


public class makeIndexPost {
    double calw(int tf , int df){
        double n =5;
        double temp = n/ df;
        double result = tf * Math.log(temp);
        result = (double)Math.round(result*100) /100.00;
        return result;
    }

    void makeHashMap() throws IOException {
        Integer id=0;
        int count = 0;
        String index_file_path = "/Users/dongyullee/SimpleIR/html/index.xml";
        FileOutputStream fileStream = new FileOutputStream("/Users/dongyullee/SimpleIR/html/index.post");
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileStream);

        File index_file = new File(index_file_path);
        org.jsoup.nodes.Document html = Jsoup.parse(index_file, "UTF-8" , index_file_path,  Parser.xmlParser());
        HashMap<Integer , HashMap<String, String>> all_word = new HashMap<>();      //<id , <key , 빈도>> id별 문자 빈도 수 저장
        HashMap temp= new HashMap<>();      //값 임시로 담을 temp  해쉬맵
        HashMap temp_clone= new HashMap<>();

        HashMap id_word = new HashMap<>();   //<id , w(가중치)> result에 넣을 hashmap
        HashMap result= new HashMap<>();     //<key <id , w(가중치)>> 키워드 id 별 가중치

        for (int j=0; j<5; j++) {
            String bodyData = html.getElementById(id.toString()).getElementsByTag("body").text();
            String[] split1 = bodyData.split("#");
            for (int i = 0; i < split1.length; i++) {
                String[] split2 = split1[i].split(":");
                temp.put(split2[0], split2[1]);
                id_word.put(id,split2[0]);
            }
            temp_clone = (HashMap<String, String>) temp.clone();
            all_word.put(id,temp_clone);
            temp.clear();
            id++;
        }   //all_word<id <key,tf>> 저장 완료

        for(Integer i=0; i<5; i++){
            Iterator<String> keys = all_word.get(i).keySet().iterator();
            while (keys.hasNext()){
                String key = keys.next();
                for(Integer j=0; j<5; j++){
                    if(all_word.get(j).containsKey(key))
                        count++;
                }
                for(Integer j=0; j<5; j++) {
                    if (all_word.get(j).containsKey(key)) {
                        int tf = Integer.parseInt(all_word.get(i).get(key));
                        double w = calw(tf,count);
                        id_word.put(j,w);
                    }
                    else
                        id_word.put(j,0.0);
                }
                 temp_clone = (HashMap) id_word.clone();
                result.put(key,temp_clone);
                count = 0;
            }
        }               //result<key , <id,w> > 저장완료

        objectOutputStream.writeObject(result);
        objectOutputStream.close();
    }

    void printHashMap() throws IOException, ClassNotFoundException {
        FileInputStream fileStream = new FileInputStream("/Users/dongyullee/SimpleIR/html/index.post");
        ObjectInputStream objectInputStream = new ObjectInputStream(fileStream);

        Object object = objectInputStream.readObject();
        objectInputStream.close();

        HashMap hashMap = (HashMap) object;
        Iterator<String> it = hashMap.keySet().iterator();

        while (it.hasNext()) {
            String key = it.next();
            HashMap value = (HashMap) hashMap.get(key);
            System.out.println(key + "->" + value.entrySet());
        }
    }
}

