package com.zhihu.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.zhihu.R;
import com.zhihu.view.MainFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by gameness1 on 15-11-10.
 */
public class MainActivity extends FragmentActivity implements AdapterView.OnItemClickListener {
    private DrawerLayout mDrawerLayout;
    private ListView drawerList;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        switchFragment(new MainFragment());
    }

    private void init() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerlayout);
        drawerList = (ListView) findViewById(R.id.left_drawer);
        SimpleAdapter adapter = new SimpleAdapter(
                this,
                this.getData(),
                R.layout.menu_left_list,
                new String[]{"image","text"},
                new int[]{R.id.menu_list_image, R.id.menu_list_text});
        drawerList.setAdapter(adapter);
        drawerList.setOnItemClickListener(this);
        mDrawerLayout.setDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        });
        fragmentManager = getSupportFragmentManager();
    }

    private List<Map<String, Object>> getData() {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        int[] images = {
                R.drawable.left_home,
                R.drawable.left_categories,
                R.drawable.left_favorite,
                R.drawable.left_feedback,
                R.drawable.left_setting
        };
        String[] textStrings = getResources().getStringArray(R.array.menuList);
        for (int i = 0; i < textStrings.length; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("image", images[i]);
            map.put("text", textStrings[i]);
            list.add(map);
        }
        return list;
    }

    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //切换菜单点击的fragment
        Fragment contentFragment = null;
        switch (position) {
            case 0:

                break;
            case 1:

                break;
            case 2:

                break;
            default:

                break;
        }
        //switchFragment(contentFragment);
    }

    public void switchFragment(Fragment fragment) {
        fragmentManager.beginTransaction()
                .replace(R.id.content_fragment, fragment).commit();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exitBy2Click();
        }
        return false;
    }

    /**
     * 双击退出
     */
    private static Boolean isExit = false;
    private void exitBy2Click() {
        Timer tExit = null;
        if (!isExit) {
            isExit = true; // 准备退出
            Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            tExit = new Timer();
            tExit.schedule(new TimerTask() {
                @Override
                public void run() {
                    isExit = false; // 取消退出
                }
            }, 2000); // 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务

        } else {
            finish();
            System.exit(0);
        }
    }
}
