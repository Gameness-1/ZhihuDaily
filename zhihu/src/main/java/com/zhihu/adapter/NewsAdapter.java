package com.zhihu.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.zhihu.R;
import com.zhihu.bean.News;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by gameness1 on 15-11-11.
 */
public class NewsAdapter extends BaseAdapter {
    private Context context;
    private List<News> newsList;
    private LayoutInflater inflater;
    private ImageLoader imageLoader;

    private DisplayImageOptions options = new DisplayImageOptions.Builder()
            .showImageOnLoading(R.drawable.ic_noresource)
            .showImageOnFail(R.drawable.ic_noresource)
            .showImageForEmptyUri(R.drawable.ic_noresource)
            .cacheInMemory(true)
            .cacheOnDisk(true)
            .considerExifParams(true)
            .build();

    public NewsAdapter(Context context){
        this.context = context;
        newsList = new ArrayList<News>();
        inflater = LayoutInflater.from(context);
        imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(context));
    }

    @Override
    public int getCount() {
        return newsList.size();
    }

    @Override
    public News getItem(int position) {
        return newsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.news_list_item, null);
            holder.image = (ImageView) convertView.findViewById(R.id.news_image);
            holder.title = (TextView) convertView.findViewById(R.id.news_title);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        News newItem = getItem(position);
        holder.title.setText(newItem.getTitle());
        imageLoader.displayImage(newItem.getImgUrl(), holder.image, options, new AnimateFirstDisplayListener());
        return convertView;
    }

    public void refreshNewsList(List<News> list) {
        newsList.clear();
        addAll(list);
        notifyDataSetChanged();
    }

    public void addAll(List<News> list) {
        for (int i = 0; i < list.size(); i++) {
            newsList.add(list.get(i));
        }

    }

    public class ViewHolder {
        private ImageView image = null;
        private TextView title = null;
    }

    private static class AnimateFirstDisplayListener extends SimpleImageLoadingListener {
        static final List<String> displayedImages = Collections.synchronizedList(new LinkedList<String>());
        @Override
        public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
            if (loadedImage != null) {
                ImageView imageView = (ImageView) view;
                // 是否第一次显示
                boolean firstDisplay = !displayedImages.contains(imageUri);
                if (firstDisplay) {
                    // 图片淡入效果
                    FadeInBitmapDisplayer.animate(imageView, 500);
                    displayedImages.add(imageUri);
                }
            }
        }
    }
}
