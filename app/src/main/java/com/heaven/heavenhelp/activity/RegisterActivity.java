package com.heaven.heavenhelp.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.heaven.heavenhelp.R;
import com.heaven.heavenhelp.utils.StringRequestUtil;
import com.heaven.heavenhelp.utils.ToastUtils;
import com.heaven.heavenhelp.utils.ValidationUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;


public class RegisterActivity extends ActionBarActivity implements View.OnClickListener {


    EditText id_mobile_number, id_gotted_code, id_password;
    Button id_send_code, id_submit_info;
    EventHandler eventHandler;
    RequestQueue requestQueue;
    ToastUtils toastUtils;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        id_mobile_number = (EditText) findViewById(R.id.id_mobile_number);
        id_gotted_code = (EditText) findViewById(R.id.id_gotted_code);
        id_password = (EditText) findViewById(R.id.id_password);
        id_send_code = (Button) findViewById(R.id.id_send_code);
        id_submit_info = (Button) findViewById(R.id.id_submit_info);
        toastUtils = new ToastUtils(this);
        id_send_code.setOnClickListener(this);
        id_submit_info.setOnClickListener(this);
        SMSSDK.initSDK(this, "918205743ae2", "1ce19530b625e514ad9b8073e1117a5e");
        requestQueue = Volley.newRequestQueue(this);

        eventHandler = new EventHandler() {
            /**
             * 在操作之后被触发
             *
             * @param event
             *            参数1
             * @param result
             *            参数2 SMSSDK.RESULT_COMPLETE表示操作成功，为SMSSDK.
             *            RESULT_ERROR表示操作失败
             * @param data
             *            事件操作的结果
             */
            @Override
            public void afterEvent(int event, int result, Object data) {
                Message msg = new Message();
                msg.arg1 = event;
                msg.arg2 = result;
                msg.obj = data;
                handler.sendMessage(msg);
            }
        };
        // 注册回调监听接口
        SMSSDK.registerEventHandler(eventHandler);


    }

    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == -9) {
                id_send_code.setText("重新发送");
            } else if (msg.what == -8) {
                id_send_code.setText("获取验证码");
                id_send_code.setClickable(true);
            } else {
                int event = msg.arg1;
                int result = msg.arg2;
                Object data = msg.obj;
                Log.e("event", "event=" + event);
                if (result == SMSSDK.RESULT_COMPLETE) {

                    if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                        StringRequest sr = new StringRequestUtil(Request.Method.POST, "http://123.57.158.178:9090/celechem/register.action", new Response.Listener<String>() {
                            @Override
                            public void onResponse(String s) {
                                try {
                                    JSONObject json = new JSONObject(s);
                                    String result = json.getString("result");
                                    if ("1".equals(result)) {
                                        Intent intent = new Intent(RegisterActivity.this, LoginSuccessActivity.class);
                                        startActivity(intent);
                                        finish();
                                    } else if ("0".equals(result)) {
                                        Toast.makeText(RegisterActivity.this, "系统异常", Toast.LENGTH_SHORT).show();
                                    } else if ("2".equals(result)) {
                                        Toast.makeText(RegisterActivity.this, "该用户已存在", Toast.LENGTH_SHORT).show();
                                    }
                                    ;
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError volleyError) {
                                Toast.makeText(RegisterActivity.this, "网络异常", Toast.LENGTH_SHORT).show();
                            }
                        }) {
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String, String> map = new HashMap<String, String>();
                                map.put("mobile", id_mobile_number.getText().toString());
                                map.put("password", id_password.getText().toString());
                                return map;
                            }
                        };
                        sr.setTag("www");
                        requestQueue.add(sr);

                    } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                        Toast.makeText(getApplicationContext(), "验证码已经发送",
                                Toast.LENGTH_SHORT).show();
                    } else {
                        ((Throwable) data).printStackTrace();
                    }
                }else{
                    Toast.makeText(RegisterActivity.this, "验证码不正确", Toast.LENGTH_SHORT).show();
                }
            }
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.id_send_code:
                if(TextUtils.isEmpty(id_mobile_number.getText().toString())){
                    toastUtils.showToastShort("手机号码不能为空");
                }else if(!ValidationUtil.isMobileNO(id_mobile_number.getText().toString())){
                    toastUtils.showToastShort("请输入正确的手机号");
                }else{
                    StringRequest findUserByMobile = new StringRequestUtil(Request.Method.POST, "http://123.57.158.178:9090/celechem/findUserByMobile.action", new Response.Listener<String>() {
                        @Override
                        public void onResponse(String s) {
                            try {
                                JSONObject json = new JSONObject(s);
                                String result = json.getString("result");
                                if ("0".equals(result)) {
                                    SMSSDK.getVerificationCode("86", id_mobile_number.getText().toString().trim());
                                }else {
                                    Toast.makeText(RegisterActivity.this, "该用户已存在,可以直接登陆", Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {
                            Toast.makeText(RegisterActivity.this, "网络异常", Toast.LENGTH_SHORT).show();
                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> map = new HashMap<String, String>();
                            map.put("mobile", id_mobile_number.getText().toString());
                            return map;
                        }
                    };
                    requestQueue.add(findUserByMobile);
                }
                break;
            case R.id.id_submit_info:

                if(TextUtils.isEmpty(id_mobile_number.getText().toString())){
                    toastUtils.showToastShort("手机号码不能为空");
                }else if(!ValidationUtil.isMobileNO(id_mobile_number.getText().toString())){
                    toastUtils.showToastShort("请输入正确的手机号");
                }else{

                    if(TextUtils.isEmpty(id_gotted_code.getText().toString())){
                        toastUtils.showToastShort("请输入验证码");
                    }else{
                        if(TextUtils.isEmpty(id_password.getText().toString())){
                            toastUtils.showToastShort("密码不能为空");
                        }else if(id_password.getText().toString().length()<4){
                            toastUtils.showToastShort("密码长度不能小于4位");
                        }else {
                            SMSSDK.submitVerificationCode("86", id_mobile_number.getText().toString(), id_gotted_code.getText().toString());
                            //SMSSDK.unregisterEventHandler(eventHandler);
                        }
                    }
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        requestQueue.cancelAll("www");
        SMSSDK.unregisterEventHandler(eventHandler);
    }
}
