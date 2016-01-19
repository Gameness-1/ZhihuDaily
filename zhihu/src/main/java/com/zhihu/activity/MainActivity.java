package com.zhihu.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.zhihu.R;
import com.zhihu.adapter.NavigationDrawerAdapter;
import com.zhihu.bean.NavigationItem;
import com.zhihu.fragment.AsyncTaskPoolTest;
import com.zhihu.fragment.MainFragment;
import com.zhihu.listener.NavigationDrawerCallbacks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by gameness1 on 15-11-10.
 */
public class MainActivity extends FragmentActivity {
    private DrawerLayout mDrawerLayout;
    private RecyclerView recyclerView;
    private Fragment curFragment;
    private NavigationDrawerAdapter drawerAdapter;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        initViews();
        curFragment = new MainFragment();
        switchFragment(curFragment);
    }

    private void findViews() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerlayout);
        recyclerView = (RecyclerView) findViewById(R.id.left_drawer);
    }

    private void initViews() {
        drawerAdapter = new NavigationDrawerAdapter(getMenuData());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(drawerAdapter);
        drawerAdapter.setNavigationDrawerCallbacks(new NavigationDrawerCallbacks() {
            @Override
            public void onNavigationDrawerItemSelected(int position) {
               // drawerAdapter.notifyItemChanged(position);
                onItemClick(position);
                mDrawerLayout.closeDrawers();
            }
        });
        fragmentManager = getSupportFragmentManager();
    }

    private List<NavigationItem> getMenuData() {
        List<NavigationItem> itemList = new ArrayList<NavigationItem>();
        String[] menuText = getResources().getStringArray(R.array.menuList);
        itemList.add(new NavigationItem(menuText[0], getResources().getDrawable(R.drawable.left_home)));
        itemList.add(new NavigationItem(menuText[1], getResources().getDrawable(R.drawable.left_categories)));
        itemList.add(new NavigationItem(menuText[2], getResources().getDrawable(R.drawable.left_favorite)));
        itemList.add(new NavigationItem(menuText[3], getResources().getDrawable(R.drawable.left_feedback)));
        itemList.add(new NavigationItem(menuText[4], getResources().getDrawable(R.drawable.left_setting)));
        return itemList;
    }


    public void onItemClick(int position) {
        //切换菜单点击的fragment
        Fragment contentFragment = null;
        switch (position) {
            case 0:
                contentFragment = new MainFragment();
                mDrawerLayout.closeDrawers();
                break;
            case 1:
                contentFragment = new AsyncTaskPoolTest();
                mDrawerLayout.closeDrawers();
                break;
            case 2:
                return;
                //break;
            default:
                return;
               // break;
        }
        switchFragment(contentFragment);
    }

    public Fragment getFragment() {
        return curFragment;
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
