package com.heaven.heavenhelp.activity;

import android.app.Activity;
import android.app.Dialog;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.heaven.heavenhelp.R;
import com.heaven.heavenhelp.adapter.ProductInfoAdapter;
import com.heaven.heavenhelp.model.ProductInfo;
import com.heaven.heavenhelp.model.UserInfo;
import com.heaven.heavenhelp.pulltorefresh.PullToRefreshBase;
import com.heaven.heavenhelp.pulltorefresh.PullToRefreshListView;
import com.heaven.heavenhelp.utils.Constants;
import com.heaven.heavenhelp.utils.LoadProcessDialog;
import com.heaven.heavenhelp.utils.SharePrefUtil;
import com.heaven.heavenhelp.utils.StringRequestUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.spec.ECField;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductInfoFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    PullToRefreshListView newsListView;
    RequestQueue requestQueue;
    Dialog mDialog;
    List<ProductInfo> productInfos ;
    List<ProductInfo> productInfoList ;
    ProductInfoAdapter productInfoAdapter;
    private int pageNo = 0;
    Handler handler  = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what==0){
                mDialog = LoadProcessDialog.showRoundProcessDialog(getActivity(), R.layout.loading_process_dialog_color);
            }else if(msg.what==1){
               /* if(productInfoAdapter==null){
                    productInfoAdapter = new ProductInfoAdapter(getActivity(), productInfos);
                    newsListView.setAdapter(productInfoAdapter);
                    productInfoAdapter.notifyDataSetChanged();
                }else{
                    productInfoAdapter.notifyDataSetChanged();
                }
*/
                productInfoList.clear();
                productInfoList.addAll(productInfos);
                productInfoAdapter.notifyDataSetChanged();
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
     * @return A new instance of fragment ProductInfoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProductInfoFragment newInstance(String param1, String param2) {
        ProductInfoFragment fragment = new ProductInfoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public ProductInfoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onResume() {
        super.onResume();
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
        View product_info = inflater.inflate(R.layout.fragment_product_info, container, false);
        newsListView = (PullToRefreshListView) product_info.findViewById(R.id.pull_to_refresh_text);

        productInfos = new ArrayList<ProductInfo>();
        productInfoList  =new ArrayList<ProductInfo>();
        productInfoAdapter = new ProductInfoAdapter(getActivity(), productInfoList);

        newsListView.setAdapter(productInfoAdapter);

        newsListView.setMode(PullToRefreshBase.Mode.BOTH);
        newsListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                new AsyncTask<Void, Void, Void>() {

                    @Override
                    protected Void doInBackground(Void... params) {
                        try {
                            handler.sendEmptyMessage(0);
                            pageNo = 0;
                            productInfos.clear();
                            getData();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return null;
                    }

                    @Override
                    protected void onPostExecute(Void aVoid) {
                        newsListView.onRefreshComplete();
                        super.onPostExecute(aVoid);
                    }
                }.execute();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                new AsyncTask<Void, Void, Void>() {

                    @Override
                    protected Void doInBackground(Void... params) {
                        try {
                            handler.sendEmptyMessage(0);
                            pageNo++;
                            getData();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return null;
                    }

                    @Override
                    protected void onPostExecute(Void aVoid) {
                        newsListView.onRefreshComplete();
                        super.onPostExecute(aVoid);
                    }
                }.execute();
            }
        });

        return product_info;
    }

    private void getData() {
        requestQueue = Volley.newRequestQueue(getActivity());
        final StringRequestUtil request = new StringRequestUtil(Request.Method.POST, Constants.host+Constants.getProductInfo, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {

                try {
                    JSONArray jsonArray = new JSONArray(s.toString());
                    for (int i = 0; i < jsonArray.length(); i++) {
                        ProductInfo productInfo = new ProductInfo();
                        final Object o = jsonArray.get(i);
                        final JSONObject jsonObject = new JSONObject(o.toString());
                        final String productName = jsonObject.getString("productName");
                        final String productPrice = jsonObject.getString("productPrice");
                        productInfo.setProductName(productName);
                        productInfo.setProductPrice(productPrice + " RMB");
                        productInfos.add(productInfo);
                    }
                    handler.sendEmptyMessage(1);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
               // mDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                //mDialog.dismiss();
                Toast.makeText(getActivity(), "网络异常，请稍后再试。", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("pageNo",String.valueOf(pageNo));
                return map;
            }
        };
        requestQueue.add(request);

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
