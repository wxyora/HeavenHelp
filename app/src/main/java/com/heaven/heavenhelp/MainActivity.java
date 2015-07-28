package com.heaven.heavenhelp;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;


public class MainActivity extends ActionBarActivity implements View.OnClickListener {


    EditText id_mobile_number,id_gotted_code;
    Button id_send_code,id_submit_info;
    EventHandler eventHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        id_mobile_number = (EditText) findViewById(R.id.id_mobile_number);
        id_gotted_code = (EditText) findViewById(R.id.id_gotted_code);
        id_send_code = (Button) findViewById(R.id.id_send_code);
        id_submit_info = (Button) findViewById(R.id.id_submit_info);

        id_send_code.setOnClickListener(this);
        id_submit_info.setOnClickListener(this);
        SMSSDK.initSDK(this, "918205743ae2", "1ce19530b625e514ad9b8073e1117a5e");

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
                    // 短信注册成功后，返回MainActivity,然后提示新好友
                    if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {// 提交验证码成功
                        Toast.makeText(getApplicationContext(), "提交验证码成功",
                                Toast.LENGTH_SHORT).show();
                    } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                        Toast.makeText(getApplicationContext(), "验证码已经发送",
                                Toast.LENGTH_SHORT).show();
                    } else {
                        ((Throwable) data).printStackTrace();
                    }
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
                id_send_code.setText("正在发送......");
                SMSSDK.getVerificationCode("86", id_mobile_number.getText().toString().trim());
                id_send_code.setText("再次发送");
                break;

            case R.id.id_submit_info:
                // judgePhoneNums(phoneNums);
                SMSSDK.submitVerificationCode("86", id_mobile_number.getText().toString(), id_gotted_code
                        .getText().toString());
                // 验证通过之后，将smssdk注册代码注销
                //SMSSDK.unregisterEventHandler(eventHandler);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SMSSDK.unregisterEventHandler(eventHandler);
    }
}
