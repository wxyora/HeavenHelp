package com.heaven.heavenhelp.activity;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.heaven.heavenhelp.R;
import com.heaven.heavenhelp.util.StringRequestUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends ActionBarActivity implements View.OnClickListener {

    Button bt_login_submit, bt_register_info;
    RequestQueue requestQueue;
    EditText id_login_mobile, id_login_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        requestQueue = Volley.newRequestQueue(this);
        bt_login_submit = (Button) findViewById(R.id.bt_login_submit);
        bt_register_info = (Button) findViewById(R.id.bt_register_info);
        id_login_mobile = (EditText) findViewById(R.id.id_login_mobile);
        id_login_password = (EditText) findViewById(R.id.id_login_password);
        bt_login_submit.setOnClickListener(this);
        bt_register_info.setOnClickListener(this);
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

                StringRequest sr = new StringRequestUtil(Request.Method.POST, "http://waylonsir.imwork.net/celechem/loginValidate.action", new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
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
                            }else{

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        System.out.print("error");
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> map = new HashMap<String, String>();
                        map.put("mobile", id_login_mobile.getText().toString().trim());
                        map.put("password", id_login_password.getText().toString().trim());
                        return map;
                    }
                };
                requestQueue.add(sr);
                break;
            case R.id.bt_register_info:
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}
