package com.heaven.heavenhelp.activity;

import android.app.Activity;
import android.app.Dialog;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
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

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MyCenterFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MyCenterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyCenterFragment extends Fragment implements View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;


    private Button bt_login_submit, bt_register_info;
    private RequestQueue requestQueue;
    private EditText id_login_mobile, id_login_password;
    private ToastUtils toastUtils;
    private CheckBox remember_user_info;
    private ProgressBar mProgressBar;
    private Dialog mDialog;
    private TextView tv_forget_password;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MyCenterFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MyCenterFragment newInstance(String param1, String param2) {
        MyCenterFragment fragment = new MyCenterFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public MyCenterFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View activity_login = inflater.inflate(R.layout.activity_login, container, false);

        toastUtils = new ToastUtils(getActivity());
        requestQueue = Volley.newRequestQueue(getActivity());
        bt_login_submit = (Button) activity_login.findViewById(R.id.bt_login_submit);
        bt_register_info = (Button) activity_login.findViewById(R.id.bt_register_info);
        id_login_mobile = (EditText) activity_login.findViewById(R.id.id_login_mobile);
        id_login_password = (EditText) activity_login.findViewById(R.id.id_login_password);
        remember_user_info = (CheckBox)activity_login.findViewById(R.id.remember_user_info);
        tv_forget_password = (TextView) activity_login.findViewById(R.id.tv_forget_password);
        bt_login_submit.setOnClickListener(this);
        bt_register_info.setOnClickListener(this);
        tv_forget_password.setOnClickListener(this);
        SharedPreferences sharedPreferences =
               getActivity().getSharedPreferences("loginInfo", 0);
        id_login_mobile.setText(sharedPreferences.getString("loginName", null));
        id_login_password.setText(sharedPreferences.getString("loginPwd", null));

        return activity_login;
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
                        getActivity().getSharedPreferences("loginInfo", 0);

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

                            mDialog = LoadProcessDialog.showRoundProcessDialog(getActivity(), R.layout.loading_process_dialog_color);
                            StringRequest sr = new StringRequestUtil(Request.Method.POST, "http://waylonsir.imwork.net/celechem/loginValidate.action", new Response.Listener<String>() {
                                @Override
                                public void onResponse(String s) {
                                    mDialog.dismiss();
                                    try {
                                        JSONObject jsonObject = new JSONObject(s);
                                        String result = jsonObject.getString("result");
                                        if(result.equals("0")){
                                            Toast.makeText(getActivity(), "该用户不存在，请注册", Toast.LENGTH_SHORT).show();
                                        }else if(result.equals("3")){
                                            Toast.makeText(getActivity(), "请核对用户名和密码", Toast.LENGTH_SHORT).show();
                                        }else if(result.equals("1")){
                                            Intent intent = new Intent(getActivity(), LoginSuccessActivity.class);
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
                                    Toast.makeText(getActivity(),"网络异常，请稍后再试。",Toast.LENGTH_SHORT).show();
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
                Intent raIntent = new Intent(getActivity(), RegisterActivity.class);
                startActivity(raIntent);
                break;
            case R.id.tv_forget_password:
                Intent fdIntent = new Intent(getActivity(), FindPasswordActivity.class);
                startActivity(fdIntent);
                break;
            default:
                break;
        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
