package com.zhihu.task;

import android.os.AsyncTask;
import com.zhihu.adapter.NewsAdapter;
import com.zhihu.entity.News;
import com.zhihu.http.Http;
import com.zhihu.http.NewsParser;
import com.zhihu.tool.CustomeProgressDialog;

import java.io.IOException;
import java.util.List;

import javax.xml.xpath.XPathExpressionException;

/**
 * Created by gameness1 on 15-11-11.
 */
public class SelectNewsTask extends AsyncTask<Void, Void, List<News>>{
    private NewsAdapter adapter;
    private onFinishListener finishListener;
    public CustomeProgressDialog dialog;
    public static String URL = "http://data.3g.sina.com.cn/api/combine.php?" +
            "wm=b207&version=1&cid=1&channel=gj&from=6028195012&chwm=5062_0001&uid=";

    public SelectNewsTask(NewsAdapter adapter){
        this.adapter = adapter;
    }

    public SelectNewsTask(NewsAdapter adapter, onFinishListener finishListener){
        this.adapter = adapter;
        this.finishListener = finishListener;
    }

    public void setDialog(CustomeProgressDialog dialog) {
        this.dialog = dialog;
    }

    @Override
    protected List<News> doInBackground(Void... params) {
        List<News> newsList = null;
        try {
            String response = Http.get(URL);
            NewsParser parser = new NewsParser();
            newsList = parser.getNewsList(1, response);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }
        return newsList;
    }

    @Override
    protected void onPostExecute(List<News> newsList) {
        adapter.refreshNewsList(newsList);
        if (finishListener != null)
            finishListener.afterTaskFinish();
        if (dialog != null){
            dialog.dismiss();
            dialog = null;
        }
    }

    public interface onFinishListener {
        public void afterTaskFinish();
    }
}
