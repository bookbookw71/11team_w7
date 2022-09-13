package com.example.bookbookw71.API;

import lombok.SneakyThrows;

import java.net.URLEncoder;
import java.util.*;

import lombok.Synchronized;
import org.json.*;
import org.xml.sax.ContentHandler;
import org.xml.sax.helpers.ParserAdapter;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;



public class SearchService {
    public List<Item> Items;
    private Item currentItem;
    private boolean inItemElement = false;
    private String tempValue;
    private static final String BASE_SEARCH_URL = "http://www.aladdin.co.kr/ttb/api/ItemSearch.aspx?";
    private static final String BASE_ITEM_URL = "http://www.aladin.co.kr/ttb/api/ItemList.aspx?";

    SearchService( ){
        Items = new ArrayList<Item>();
    }

    @SneakyThrows
    public static String getUrl(String searchTitle){

        Map<String,String> hm = new HashMap<String,String>();
        hm.put("ttbkey", "ttbhaga6051538001");
        hm.put("Query", URLEncoder.encode(searchTitle, "UTF-8"));
        hm.put("QueryType", "Title");
        hm.put("MaxResults", "5");
        hm.put("start", "1");
        hm.put("SearchTarget", "Book");
        hm.put("output", "xml");

        StringBuffer sb = new StringBuffer();
        //iterator 객체 안을 리스트 순서대로 돌면서 반환?
        Iterator<String> iter = hm.keySet().iterator();
        while (iter.hasNext()) {
            String key = iter.next();
            String val  = hm.get(key);
            sb.append(key).append("=").append(val).append("&");
        }

        return BASE_SEARCH_URL + sb.toString();
    }

    public static void printSearch(String search) throws Exception {
        String url = getUrl(search); //args를 이 부분을 request로 받도록 수정

        SearchService api = new SearchService();
        api.parseXml(url);

        for(Item item : api.Items){
            System.out.println(item.title + " : " + item.link);
            System.out.println("상품설명 : "+item.description);
            System.out.println();
        }

    }



    public String xmlToJSON(String xml) {
        try{

            JSONObject json = XML.toJSONObject(xml);
            String jsonString = json.toString(4);

            return jsonString;

        }catch(Exception e){return "xmlToJSON fail";}

    }



    public void parseXml(String xmlUrl) throws Exception {
        SAXParserFactory spf = SAXParserFactory.newInstance();
        SAXParser sp = spf.newSAXParser();
        ParserAdapter pa = new ParserAdapter(sp.getParser());
        pa.setContentHandler((ContentHandler) this);
        pa.parse(xmlUrl);
    }

}
