package com.heaven.heavenhelp.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import com.heaven.heavenhelp.activity.LoginActivity;
import com.heaven.heavenhelp.activity.Personal_Setting_Activity;
import com.heaven.heavenhelp.activity.PublicAccompanyActivity;
import com.heaven.heavenhelp.model.UserInfo;
import com.heaven.heavenhelp.utils.BaseFragment;
import com.heaven.heavenhelp.utils.Constants;
import com.heaven.heavenhelp.utils.LoadProcessDialog;
import com.heaven.heavenhelp.utils.SharePrefUtil;
import com.heaven.heavenhelp.utils.StringRequestUtil;
import com.heaven.heavenhelp.utils.ToastUtils;

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
public class MyCenterFragment extends BaseFragment{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private OnFragmentInteractionListener mListener;
    private RequestQueue requestQueue;
    private ToastUtils toastUtils;
    private Button btn_login,btn_personal_setting,btn_public_accompany;
    private TextView tv_login_info;
    Dialog mDialog;

    Handler handler  = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what==0){
                mDialog = LoadProcessDialog.showRoundProcessDialog(getActivity(), R.layout.loading_process_dialog_color);
            }else if(msg.what==1){
                //结束加载动画
                mDialog.dismiss();
            }

        }
    };

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
        View activity_login = inflater.inflate(R.layout.fragment_my_center, container, false);

        toastUtils = new ToastUtils(getActivity());
        //requestQueue = Volley.newRequestQueue(getActivity());
        //bt_login = (Button)activity_login.findViewById(R.id.bt_login);
        btn_personal_setting = (Button)activity_login.findViewById(R.id.btn_personal_setting);
        btn_public_accompany = (Button)activity_login.findViewById(R.id.btn_public_accompany);
        tv_login_info = (TextView)activity_login.findViewById(R.id.tv_login_info);

        btn_login = (Button)activity_login.findViewById(R.id.btn_login);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity().getApplicationContext(), LoginActivity.class));
            }
        });
        btn_personal_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity().getApplicationContext(), Personal_Setting_Activity.class));
            }
        });
        btn_public_accompany.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity().getApplicationContext(), PublicAccompanyActivity.class));
            }
        });

        return activity_login;
    }

    @Override
    public void lazyLoad() {
        handler.sendEmptyMessage(0);
        initCenterInfo();
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
    public void onResume() {
        super.onResume();
    }



    public void initCenterInfo(){

        requestQueue = Volley.newRequestQueue(getActivity());

        StringRequest findUserByMobile = new StringRequestUtil(Request.Method.POST, Constants.host+Constants.findUserByMobile, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                try {
                    JSONObject json = new JSONObject(s);
                    String result = json.getString("result");
                    String mobile = json.getString("mobile");
                    if ("1".equals(result)) {
                        tv_login_info.setText("你好," + mobile + ",欢迎登录");
                        tv_login_info.setVisibility(View.VISIBLE);
                        btn_login.setVisibility(View.GONE);
                        btn_personal_setting.setVisibility(View.VISIBLE);
                        btn_public_accompany.setVisibility(View.VISIBLE);

                    }else {
                        tv_login_info.setText("您没有登录");
                        tv_login_info.setVisibility(View.VISIBLE);
                        btn_login.setVisibility(View.VISIBLE);
                        btn_public_accompany.setVisibility(View.GONE);
                        btn_personal_setting.setVisibility(View.GONE);
                    }
                    handler.sendEmptyMessage(1);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                mDialog.dismiss();
                Toast.makeText(getActivity().getApplicationContext(), "网络不太给力啊", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                UserInfo userInfo = SharePrefUtil.getUserInfo(getActivity().getApplicationContext());
                if(userInfo.getToken()!=null){
                    map.put("token",userInfo.getToken());
                }
                if(userInfo.getMobile()!=null){
                    map.put("mobile",userInfo.getMobile() );
                }
                return map;
            }
        };
        requestQueue.add(findUserByMobile);
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
