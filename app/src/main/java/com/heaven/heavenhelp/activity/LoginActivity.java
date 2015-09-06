package com.heaven.heavenhelp.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.heaven.heavenhelp.R;
import com.heaven.heavenhelp.utils.LoadProcessDialog;
import com.heaven.heavenhelp.utils.StringRequestUtil;
import com.heaven.heavenhelp.utils.ToastUtils;
import com.heaven.heavenhelp.utils.ValidationUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends ActionBarActivity implements View.OnClickListener {

    private Button bt_login_submit, bt_register_info;
    private RequestQueue requestQueue;
    private EditText id_login_mobile, id_login_password;
    private ToastUtils toastUtils;
    private CheckBox remember_user_info;
    private ProgressBar mProgressBar;
    private Dialog mDialog;
    private TextView tv_forget_password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.setTitle("登录");
        toastUtils = new ToastUtils(this);
        requestQueue = Volley.newRequestQueue(this);
        bt_login_submit = (Button) findViewById(R.id.bt_login_submit);
        bt_register_info = (Button) findViewById(R.id.bt_register_info);
        id_login_mobile = (EditText) findViewById(R.id.id_login_mobile);
        id_login_password = (EditText) findViewById(R.id.id_login_password);
        remember_user_info = (CheckBox)findViewById(R.id.remember_user_info);
        tv_forget_password = (TextView) findViewById(R.id.tv_forget_password);
        bt_login_submit.setOnClickListener(this);
        bt_register_info.setOnClickListener(this);
        tv_forget_password.setOnClickListener(this);
        SharedPreferences sharedPreferences =
                getSharedPreferences("loginInfo", MODE_PRIVATE);
        id_login_mobile.setText(sharedPreferences.getString("loginName", null));
        id_login_password.setText(sharedPreferences.getString("loginPwd", null));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
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
            case R.id.bt_login_submit:

                SharedPreferences sharedPreferences =
                        getSharedPreferences("loginInfo", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                String loginName = id_login_mobile.getText().toString();
                String loginPwd = id_login_password.getText().toString();
                boolean checked = remember_user_info.isChecked();


                if(TextUtils.isEmpty(id_login_mobile.getText().toString())){
                    toastUtils.showToastShort("手机号码不能为空");
                }else{
                    if(!ValidationUtil.isMobileNO(id_login_mobile.getText().toString())){
                        toastUtils.showToastShort("请输入正确的手机号");
                    }else{
                        if(TextUtils.isEmpty(id_login_password.getText().toString())){
                            toastUtils.showToastShort("密码不能为空");
                        }else if(id_login_password.getText().toString().length()<4){
                            toastUtils.showToastShort("密码长度不能小于4位");
                        }else{

                            if (checked) {
                                if (checked) {
                                    editor.putString("loginName", loginName);
                                    editor.putString("loginPwd", loginPwd);
                                    editor.commit();
                                }
                            } else {
                                editor.clear();
                                editor.commit();
                            }

                            mDialog = LoadProcessDialog.showRoundProcessDialog(this,R.layout.loading_process_dialog_color);
                            StringRequest sr = new StringRequestUtil(Request.Method.POST, "http://waylonsir.imwork.net/celechem/loginValidate.action", new Response.Listener<String>() {
                                @Override
                                public void onResponse(String s) {
                                    mDialog.dismiss();
                                    try {
                                        JSONObject jsonObject = new JSONObject(s);
                                        String result = jsonObject.getString("result");
                                        if(result.equals("0")){
                                            Toast.makeText(LoginActivity.this, "该用户不存在，请注册", Toast.LENGTH_SHORT).show();
                                        }else if(result.equals("3")){
                                            Toast.makeText(LoginActivity.this, "请核对用户名和密码", Toast.LENGTH_SHORT).show();
                                        }else if(result.equals("1")){
                                            Intent intent = new Intent(LoginActivity.this, LoginSuccessActivity.class);
                                            startActivity(intent);
                                            finish();
                                        }else{

                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError volleyError) {
                                    Toast.makeText(LoginActivity.this,"网络异常，请稍后再试。",Toast.LENGTH_SHORT).show();
                                    mDialog.dismiss();
                                }
                            }) {
                                @Override
                                protected Map<String, String> getParams() throws AuthFailureError {
                                    Map<String, String> map = new HashMap<String, String>();
                                    map.put("mobile",id_login_mobile.getText().toString().trim());
                                    map.put("password",id_login_password.getText().toString().trim());
                                    return map;
                                }
                            };
                            requestQueue.add(sr);
                        }
                    }
                }
                break;
            case R.id.bt_register_info:
                Intent raIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(raIntent);
                break;
            case R.id.tv_forget_password:
                Intent fdIntent = new Intent(LoginActivity.this, FindPasswordActivity.class);
                startActivity(fdIntent);
                break;
            default:
                break;
        }
    }

}
