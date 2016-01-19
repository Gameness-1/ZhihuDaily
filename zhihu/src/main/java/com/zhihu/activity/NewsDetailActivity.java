package com.zhihu.activity;


import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.zhihu.R;
import com.zhihu.tool.CustomeProgressDialog;
import com.zhihu.tool.NetUtil;

import java.util.HashMap;

/**
 * Created by gameness1 on 15-11-12.
 */
public class NewsDetailActivity extends Activity implements View.OnClickListener {
    private ImageView detailImage;
    private TextView newsTitle, newsContent, publicDate;
    private SelectNewsDetailTask newsDetailTask;
    private ImageButton btnBack;
    private HashMap<String, String> newsDetailsMap = new HashMap<String, String>();
    private ImageLoader imageLoader;
    private CustomeProgressDialog dialog;
    //////
    private DisplayImageOptions options = new DisplayImageOptions.Builder()
            .showImageOnLoading(R.drawable.news_noresource)
            .showImageOnFail(R.drawable.news_noresource)
            .showImageForEmptyUri(R.drawable.news_noresource)
            .cacheInMemory(true)
            .cacheOnDisk(true)
            .considerExifParams(true)
            .build();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(this));
        newsDetailsMap = (HashMap<String, String>) getIntent().getSerializableExtra("newsDetails");
        init();
        dialog = new CustomeProgressDialog();
        dialog.createLoadingDialog(this, "请稍等").show();
        newsDetailTask = new SelectNewsDetailTask();
        newsDetailTask.execute(newsDetailsMap.get("url"));
    }

    public void init(){
        btnBack = (ImageButton) findViewById(R.id.btn_back);
        btnBack.setOnClickListener(this);
        detailImage = (ImageView) findViewById(R.id.news_image);
        publicDate = (TextView) findViewById(R.id.pub_date);
        newsTitle = (TextView) findViewById(R.id.news_title);
        newsContent = (TextView) findViewById(R.id.news_content);
        newsTitle.setText(newsDetailsMap.get("title"));
        publicDate.setText(newsDetailsMap.get("date"));
        imageLoader.displayImage(newsDetailsMap.get("imgUrl"), detailImage, options);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_back :
                this.finish();
                break;
        }
    }

    public class SelectNewsDetailTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            String result = NetUtil.getNewsContentByUrl(params[0]);
            return result;
        }

        @Override
        protected void onPostExecute(String contentText) {
            newsContent.setText(contentText);
            if (dialog != null){
                dialog.dismiss();
                dialog = null;
            }
        }
    }
}
