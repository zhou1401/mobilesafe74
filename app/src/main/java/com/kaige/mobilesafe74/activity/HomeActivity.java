package com.kaige.mobilesafe74.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.kaige.mobilesafe74.R;
import com.kaige.mobilesafe74.utils.ConstantValue;
import com.kaige.mobilesafe74.utils.Md5Util;
import com.kaige.mobilesafe74.utils.SpUtil;
import com.kaige.mobilesafe74.utils.ToastUtils;

/**
 * Created by dell on 2017/3/13.
 */

public class HomeActivity extends Activity{
    private GridView gv_home;
    private String[] mTitleStrs;
    private int[] mDrawableIds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initUI();
        initData();

    }

    private void initData() {
       mTitleStrs= new String[]{"手机防盗","通信卫士","软件管理","进程管理","流量统计",
                "手机杀毒","缓存清理","高级工具","设置中心"};
        mDrawableIds=new int[]{
                R.drawable.home_safe,R.drawable.home_callmsgsafe,
                R.drawable.home_apps,R.drawable.home_taskmanager,
                R.drawable.home_netmanager,R.drawable.home_trojan,
                R.drawable.home_sysoptimize,R.drawable.home_tools,R.drawable.home_settings
        };
        gv_home.setAdapter(new MyAdapter());
        gv_home.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        showDialong();
                        break;
                    case 1:
                        startActivity(new Intent(getApplicationContext(), BlackNumberActivity.class));
                        break;
                    case 7:
                        startActivity(new Intent(getApplicationContext(), AToolActivity.class));
                        break;
                    case 8:
                        Intent intent = new Intent(getApplicationContext(), SettingActivity.class);
                        startActivity(intent);
                        break;
                }
            }
        });
    }

    /**
     * 设置密码22
     */
    private void showDialong() {
        String psd = SpUtil.getString(this, ConstantValue.MOBILE_SAFE_PSD, "");
        if(TextUtils.isEmpty(psd)){
            //1,初始设置密码对话框
            showSetPsdDialog();
        }else{
            //2,确认密码对话框
            showConfirmPsdDialog();
        }
    }

    /**
     * 确认密码对话框
     */
    private void showConfirmPsdDialog() {

        //因为需要去自己定义对话框的展示样式,所以需要调用dialog.setView(view);
        //view是由自己编写的xml转换成的view对象xml----->view
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final AlertDialog dialog = builder.create();

        final View view = View.inflate(this, R.layout.dialog_confirm_psd, null);
        //让对话框显示一个自己定义的对话框界面效果
//		dialog.setView(view);
        dialog.setView(view, 0, 0, 0, 0);
        dialog.show();

        Button bt_submit = (Button) view.findViewById(R.id.bt_submit);
        Button bt_cancel = (Button) view.findViewById(R.id.bt_cancel);

        bt_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText et_confirm_psd = (EditText)view.findViewById(R.id.et_confirm_psd);

                String confirmPsd = et_confirm_psd.getText().toString();

                if(!TextUtils.isEmpty(confirmPsd)){
                    //将存储在sp中32位的密码,获取出来,然后将输入的密码同样进行md5,然后与sp中存储密码比对
                    String psd = SpUtil.getString(getApplicationContext(), ConstantValue.MOBILE_SAFE_PSD, "");

                    if(psd.equals(Md5Util.encoder(confirmPsd))){
                        //进入应用手机防盗模块,开启一个新的activity
//						Intent intent = new Intent(getApplicationContext(), TestActivity.class);
                        Intent intent = new Intent(getApplicationContext(), SetupOverActivity.class);
                        startActivity(intent);
                        //跳转到新的界面以后需要去隐藏对话框
                        dialog.dismiss();
                    }else{
                        ToastUtils.show(getApplicationContext(),"确认密码错误");
                    }
                }else{
                    //提示用户密码输入有为空的情况
                    ToastUtils.show(getApplicationContext(), "请输入密码");
                }
            }
        });

        bt_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

    }
    /**
     * 设置密码对话框
     */
    private void showSetPsdDialog() {
        //因为需要去自己定义对话框的展示样式,所以需要调用dialog.setView(view);
        //view是由自己编写的xml转换成的view对象xml----->view
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final AlertDialog dialog = builder.create();

        final View view = View.inflate(this, R.layout.dialog_set_psd, null);
        //让对话框显示一个自己定义的对话框界面效果
//		dialog.setView(view);

        //为了兼容低版本,给对话框设置布局的时候,让其没有内边距(android系统默认提供出来的)
        dialog.setView(view, 0, 0, 0, 0);
        dialog.show();

        Button bt_submit = (Button) view.findViewById(R.id.bt_submit);
        Button bt_cancel = (Button) view.findViewById(R.id.bt_cancel);

        bt_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText et_set_psd = (EditText) view.findViewById(R.id.et_set_psd);
                EditText et_confirm_psd = (EditText)view.findViewById(R.id.et_confirm_psd);

                String psd = et_set_psd.getText().toString();
                String confirmPsd = et_confirm_psd.getText().toString();

                if(!TextUtils.isEmpty(psd) && !TextUtils.isEmpty(confirmPsd)){
                    if(psd.equals(confirmPsd)){
                        //进入应用手机防盗模块,开启一个新的activity
//						Intent intent = new Intent(getApplicationContext(), TestActivity.class);
                        Intent intent = new Intent(getApplicationContext(), SetupOverActivity.class);
                        startActivity(intent);
                        //跳转到新的界面以后需要去隐藏对话框
                        dialog.dismiss();

                        SpUtil.putSting(getApplicationContext(),
                                ConstantValue.MOBILE_SAFE_PSD, Md5Util.encoder(confirmPsd));
                    }else{
                        ToastUtils.show(getApplicationContext(),"确认密码错误");
                    }
                }else{
                    //提示用户密码输入有为空的情况
                    ToastUtils.show(getApplicationContext(), "请输入密码");
                }
            }
        });

        bt_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }



    class MyAdapter extends BaseAdapter{
        @Override
        public int getCount() {
            //条目的总数	文字组数 == 图片张数
            return mTitleStrs.length;
        }

        @Override
        public Object getItem(int position) {
            return mTitleStrs[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = View.inflate(getApplicationContext(), R.layout.gridview, null);
            TextView tv_title = (TextView) view.findViewById(R.id.tv_title0);
            ImageView iv_icon = (ImageView) view.findViewById(R.id.iv_icon);

            tv_title.setText(mTitleStrs[position]);
            iv_icon.setBackgroundResource(mDrawableIds[position]);

            return view;
        }
    }


    private void initUI() {
        gv_home = (GridView) findViewById(R.id.gv_home);
    }
}
