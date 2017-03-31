package com.kaige.mobilesafe74.activity;

import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.kaige.mobilesafe74.R;
import com.kaige.mobilesafe74.dbDao.BlackNumberDao;
import com.kaige.mobilesafe74.db_domin.BlackNumberInfo;

import java.util.List;

public class BlackNumberActivity extends AppCompatActivity {
    private ListView lv_blacknumber;
    private Button bt_add;
    private BlackNumberDao dao;
    private List<BlackNumberInfo> mNumberList;
    private MyAdapter mAdapter;
    private int mode = 1;
    private int mCount;
    private boolean isload = false;

    private Handler mHandler = new Handler(){
        public void handleMessage(android.os.Message msg) {
            if(mAdapter == null){
                mAdapter = new MyAdapter();
                lv_blacknumber.setAdapter(mAdapter);
            }else{
                mAdapter.notifyDataSetChanged();
                isload = false;
            }
        };
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_black_number);

        initUI();
        initData();
    }

    private void initData() {
        dao = BlackNumberDao.getInstance(this);
        new Thread(){
            public void run() {
                mNumberList = dao.find(0);
                mCount = dao.getCount();
                mHandler.sendEmptyMessage(0);
            };
        }.start();
    }

    private void initUI() {
        lv_blacknumber = (ListView) findViewById(R.id.lv_blacknumber);
        bt_add = (Button) findViewById(R.id.bt_add);
        bt_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddDialog();
            }
        });
        lv_blacknumber.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                //OnScrollListener.SCROLL_STATE_FLING
                //OnScrollListener.SCROLL_STATE_IDLE
                //OnScrollListener.SCROLL_STATE_TOUCH_SCROLL
                if(mNumberList!=null){
                    if(AbsListView.OnScrollListener.SCROLL_STATE_IDLE == scrollState && mNumberList.size()<mCount
                            && lv_blacknumber.getLastVisiblePosition()>=mNumberList.size()-1 && !isload){
                        new Thread(){
                            public void run() {
                                isload = true;
                                List<BlackNumberInfo> moreData = dao.find(mNumberList.size());
                                if(moreData!=null){
                                    mNumberList.addAll(moreData);
                                    mHandler.sendEmptyMessage(0);
                                }
                            };
                        }.start();
                    }
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {

            }
        });
    }

    protected void showAddDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final AlertDialog dialog = builder.create();
        View view = View.inflate(getApplicationContext(), R.layout.dialog_add_blacknumber, null);
        dialog.setView(view,0,0,0,0);

        Button bt_submit = (Button) view.findViewById(R.id.bt_submit);
        Button bt_cancel = (Button) view.findViewById(R.id.bt_cancel);
        final EditText et_phone = (EditText) view.findViewById(R.id.et_phone);

        RadioGroup rg_group = (RadioGroup) view.findViewById(R.id.rg_group);
        rg_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_sms:
                        mode = 1;
                        break;
                    case R.id.rb_phone:
                        mode = 2;
                        break;
                    case R.id.rb_all:
                        mode = 3;
                        break;
                }
            }
        });
        bt_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = et_phone.getText().toString();
                //1,将数据添加到数据库中
                dao.insert(mode+"", phone);
                //2,封装javabean更新数据适配器
                BlackNumberInfo blackNumberInfo = new BlackNumberInfo();
                blackNumberInfo.phone = phone;
                blackNumberInfo.mode = mode+"";
                //3,通知数据适配器刷新
                mNumberList.add(0, blackNumberInfo);
                //4,通知数据适配器刷新
                if(mAdapter!=null){
                    mAdapter.notifyDataSetChanged();
                }
                //5,关闭对话框
                dialog.dismiss();
            }
        });

        bt_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    class MyAdapter extends BaseAdapter{
        @Override
        public int getCount() {
            return mNumberList.size();
        }

        @Override
        public BlackNumberInfo getItem(int position) {
            return mNumberList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder = null;
            if(convertView == null){
                viewHolder = new ViewHolder();
                convertView = View.inflate(getApplicationContext(), R.layout.list_blacknumber_item, null);
                convertView.setTag(viewHolder);
            }else{
                viewHolder = (ViewHolder) convertView.getTag();
            }

            viewHolder.tv_phone = (TextView) convertView.findViewById(R.id.tv_phone);
            viewHolder.tv_mode = (TextView) convertView.findViewById(R.id.tv_mode);
            viewHolder.iv_delete = (ImageView) convertView.findViewById(R.id.iv_delete);

            viewHolder.tv_phone.setText(mNumberList.get(position).phone);
            int mode = Integer.parseInt(mNumberList.get(position).mode);

            viewHolder.iv_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dao.delete(mNumberList.get(position).phone);
                    mNumberList.remove(position);
                    if(mAdapter!=null){
                        mAdapter.notifyDataSetChanged();
                    }
                }
            });

            switch (mode) {
                case 1:
                    viewHolder.tv_mode.setText("拦截短信");
                    break;
                case 2:
                    viewHolder.tv_mode.setText("拦截电话");
                    break;
                case 3:
                    viewHolder.tv_mode.setText("拦截所有");
                    break;
            }
            return convertView;
        }
    }

    static class ViewHolder{
        TextView tv_phone;
        TextView tv_mode;
        ImageView iv_delete;
    }
}
