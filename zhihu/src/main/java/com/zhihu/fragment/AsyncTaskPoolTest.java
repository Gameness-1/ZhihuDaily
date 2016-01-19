package com.zhihu.fragment;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.zhihu.R;
import com.zhihu.activity.MainActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by bjweilingzi on 2016/1/14.
 */
public class AsyncTaskPoolTest extends Fragment {
    private static int ID = 0;
    private static final int TASK_COUNT = 9;
    private static ExecutorService SINGLE_TASK_EXECUTOR;
    private static ExecutorService LIMITED_TASK_EXECUTOR;
    private static ExecutorService FULL_TASK_EXECUTOR;

    private View view;
    private Button full, fixed, single;
    private AsyncTaskAdapter taskAdapter;
    private OnChangeListener mChangeListener;
    static {
        SINGLE_TASK_EXECUTOR = (ExecutorService) Executors.newSingleThreadExecutor();
        LIMITED_TASK_EXECUTOR = (ExecutorService) Executors.newFixedThreadPool(3);
        FULL_TASK_EXECUTOR = (ExecutorService) Executors.newCachedThreadPool();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.asynctask_test, container, false);
        full = (Button) view.findViewById(R.id.full);
        fixed = (Button) view.findViewById(R.id.fixed);
        single = (Button) view.findViewById(R.id.single);
        taskAdapter = new AsyncTaskAdapter(getActivity(), 7);
        taskAdapter.setmIdentify(2);
        fixed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                taskAdapter.setmIdentify(0);
                if (mChangeListener != null)
                    mChangeListener.onChange();
                //taskAdapter.notifyDataSetChanged();
            }
        });
        single.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                taskAdapter.setmIdentify(1);
                if (mChangeListener != null)
                    mChangeListener.onChange();
                //taskAdapter.notifyDataSetChanged();
            }
        });
        String title = "AsyncTask of API " + Build.VERSION.SDK_INT;
        Log.d("Asyntask", title);
        final ListView taskList = (ListView) view.findViewById(R.id.task_list);
        taskList.setAdapter(taskAdapter);

        ViewGroup parent = (ViewGroup) view.getParent();
        if (parent != null)
            parent.removeView(view);
        return view;
    }

    public void setOnChangeListener(OnChangeListener changgeListener){
        mChangeListener = changgeListener;
    }

    private class AsyncTaskAdapter extends BaseAdapter {
        private Context mContext;
        private LayoutInflater mFactory;
        private int mTaskCount;
        private int mIdentify;
        private Fragment testFragment;
        List<SimpleAsyncTask> mTaskList;

        public AsyncTaskAdapter(Context context, int taskCount) {
            mContext = context;
            mFactory = LayoutInflater.from(mContext);
           // mTaskCount = taskCount;
            mTaskList = new ArrayList<SimpleAsyncTask>(taskCount);
            testFragment = ((MainActivity) mContext).getFragment();

        }

        public void setmIdentify(int mIdentify) {
            this.mIdentify = mIdentify;
        }

        @Override
        public int getCount() {
            return 9;
        }

        @Override
        public Object getItem(int position) {
            return mTaskList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = mFactory.inflate(R.layout.asynctask_demo_item, null);
                SimpleAsyncTask task = new SimpleAsyncTask((TaskItem) convertView, mContext);
                switch (mIdentify) {
                    case 0 :
                        task.executeOnExecutor(LIMITED_TASK_EXECUTOR);
                        break;
                    case 1 :
                        task.executeOnExecutor(SINGLE_TASK_EXECUTOR);
                        break;
                    case 2:
                        task.executeOnExecutor(FULL_TASK_EXECUTOR);
                }
                mTaskList.add(task);
            }
            final ProgressBar mProgress = (ProgressBar) convertView.findViewById(R.id.task_progress);
            Fragment testFragment = ((MainActivity) mContext).getFragment();
            if (testFragment instanceof AsyncTaskPoolTest) {
                ((AsyncTaskPoolTest) testFragment).setOnChangeListener(new OnChangeListener() {
                    @Override
                    public void onChange() {
                        mProgress.setProgress(0);
                    }
                });
            }
            return convertView;
        }
    }
    private class SimpleAsyncTask extends AsyncTask<Void, Integer, Void> {
        private TaskItem mTaskItem;
        private String mName;
        private Context mContext;

        public SimpleAsyncTask(TaskItem item) {
            mTaskItem = item;
            mName = "Task #" + String.valueOf(++ID);
        }

        public SimpleAsyncTask(TaskItem item, Context context) {
            mTaskItem = item;
            mName = "Task #" + String.valueOf(++ID);
            mContext = context;
        }

        @Override
        protected Void doInBackground(Void... params) {
            int prog = 1;
            while (prog < 11) {
                SystemClock.sleep(100);
                publishProgress(prog*10);
                prog++;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
        }

        @Override
        protected void onPreExecute() {
            mTaskItem.setTitle(mName);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            mTaskItem.setProgress(values[0]);
            Fragment testFragment = ((MainActivity) mContext).getFragment();
            /*if (mContext != null) {
                if (testFragment instanceof AsyncTaskPoolTest) {
                    ((AsyncTaskPoolTest) testFragment).setOnChangeListener(new OnChangeListener() {
                        @Override
                        public void onChange() {
                            mTaskItem.setProgress(0);
                        }
                    });
                }

            }*/
        }
    }

    public interface OnChangeListener { void onChange();}

}

class TaskItem extends LinearLayout {
    private TextView mTitle;
    private ProgressBar mProgress;

    public TaskItem(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TaskItem(Context context) {
        super(context);
    }

    public void setTitle(String title) {
        if (mTitle == null) {
            mTitle = (TextView) findViewById(R.id.task_name);
        }
        mTitle.setText(title);
    }

    public void setProgress(int prog) {
        if (mProgress == null) {
            mProgress = (ProgressBar) findViewById(R.id.task_progress);
        }
        mProgress.setProgress(prog);
    }


}
