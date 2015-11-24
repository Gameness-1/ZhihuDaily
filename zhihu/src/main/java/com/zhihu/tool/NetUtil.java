package com.zhihu.tool;

import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.util.NodeList;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * Created by gameness1 on 15-11-12.
 */
public class NetUtil {

    static String[] ignoreKeys=new String[]{
            "浏览原图",
            "视频下载",
    };


    public static String getNewsContentByUrl(String url) {
        String result = "";
        try {
            Document doc = Jsoup.connect(url).get();
            Elements ListDiv = doc.getElementsByAttributeValue("class",
                    "articleContent");
            result = ListDiv.html().toString();
            String contentString =parserContent(result);
            result = contentString;
            if(contentString.trim().length()==0){
                result = "noting";
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String parserContent(String content) {
        String reslut = "";
        //过滤正文之前的内容
        if(content.contains("<span class=\"imgMessage\">")){
            int index = content.lastIndexOf("<span class=\"imgMessage\">");
            content = content.substring(index, content.length());
        }

        content = "<html>"+content+"</html>";
        NodeFilter contentFilter = new TagNameFilter("html");
        try {
            Parser imgParser = new Parser();
            imgParser.setResource(content);
            NodeList imgList = imgParser.extractAllNodesThatMatch(contentFilter);

            reslut = imgList.asString();
            for (int i = 0; i < ignoreKeys.length; i++) {
                if (reslut.contains(ignoreKeys[i])) {
                    int index = reslut.indexOf(ignoreKeys[i]);
                    reslut = reslut.substring(index + 8, reslut.length());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return reslut;
    }
}
