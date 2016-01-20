package com.zhihu.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import com.zhihu.R;
import com.zhihu.activity.NewsDetailActivity;
import com.zhihu.adapter.CarouselAdapter;
import com.zhihu.adapter.NewsAdapter;
import com.zhihu.bean.News;
import com.zhihu.request.cache.ZhihuCache;
import com.zhihu.task.SelectNewsTask;
import com.zhihu.tool.CustomeProgressDialog;
import com.zhihu.tool.NetTools;
import com.zhihu.view.CarouselViewPage;
import com.zhihu.view.IndicatorLayout;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

/**
 * Created by gameness1 on 15-11-10.
 */
public class MainFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, AdapterView.OnItemClickListener {
    private View view;
    private TextView timeText;
    private ListView listView;
    private CarouselViewPage viewPager;
    private SwipeRefreshLayout refreshLayout;
    private IndicatorLayout indicatorLayout;
    private NewsAdapter newsAdapter;
    private boolean isNetworkAvailable;
    private CustomeProgressDialog dialog;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_main, container, false);
        isNetworkAvailable = new NetTools(getActivity()).checkNetworkState(true);
        initPageView();
        initData();

        /*ViewGroup parent = (ViewGroup) view.getParent();
        if (parent != null)
            parent.removeView(view);*/
		return view;
	}

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    public void initData() {
        List<News> newsList = ZhihuCache.getNewList();
        if (newsList != null && newsList.size() > 0)
            newsAdapter.refreshNewsList(newsList);
        if (isNetworkAvailable) {
            SelectNewsTask task = new SelectNewsTask(newsAdapter);
            task.execute();
            /*dialog = new CustomeProgressDialog();
            dialog.createLoadingDialog(getActivity(), "请稍等").show();
            task.setDialog(dialog);*/
        }

    }

    public void initPageView() {
        View pageView = LayoutInflater.from(getActivity()).inflate(R.layout.carousel_viewpager, null);
        timeText = (TextView) pageView.findViewById(R.id.time_text);
        timeText.setText(getTime() + "   今日热点");
        viewPager = (CarouselViewPage) pageView.findViewById(R.id.vp_main);
        viewPager.setAdapter(new CarouselAdapter(getActivity()));
        indicatorLayout = (IndicatorLayout) pageView.findViewById(R.id.indicate_main);
        indicatorLayout.setViewPage(viewPager);
        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refresh_layout);
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setColorScheme(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        listView = (ListView) view.findViewById(R.id.news_listview);
        listView.addHeaderView(pageView, null, false);
        newsAdapter = new NewsAdapter(getActivity());
        listView.setAdapter(newsAdapter);
        listView.setOnItemClickListener(this);
    }

    public String getTime() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat(getString(R.string.date_format));
        return format.format(c.getTime());
    }

    @Override
    public void onRefresh() {
        if (isNetworkAvailable) {
            new SelectNewsTask(newsAdapter, new SelectNewsTask.onFinishListener() {
                @Override
                public void afterTaskFinish() {
                    refreshLayout.setRefreshing(false);
                }
            }).execute();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(getActivity(), NewsDetailActivity.class);
        HashMap<String, String> newsDetailsMap = new HashMap<String, String>();
        News newItem = newsAdapter.getItem(position - 1);
        newsDetailsMap.put("title", newItem.getTitle());
        newsDetailsMap.put("url", newItem.getUrl());
        newsDetailsMap.put("date", newItem.getPubDate());
        newsDetailsMap.put("imgUrl", newItem.getImgUrl());
        intent.putExtra("newsDetails", newsDetailsMap);
        startActivity(intent);
    }
}
