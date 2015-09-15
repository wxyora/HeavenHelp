package com.heaven.heavenhelp.activity;

import android.app.Activity;
import android.app.Dialog;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.heaven.heavenhelp.R;
import com.heaven.heavenhelp.adapter.ProductInfoAdapter;
import com.heaven.heavenhelp.model.ProductInfo;
import com.heaven.heavenhelp.pulltorefresh.PullToRefreshBase;
import com.heaven.heavenhelp.pulltorefresh.PullToRefreshListView;
import com.heaven.heavenhelp.utils.LoadProcessDialog;
import com.heaven.heavenhelp.utils.StringRequestUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

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
        // Inflate the layout for this fragment
        View product_info = inflater.inflate(R.layout.activity_login_success, container, false);


        newsListView = (PullToRefreshListView) product_info.findViewById(R.id.pull_to_refresh_text);
        newsListView.setMode(PullToRefreshBase.Mode.BOTH);
        requestQueue = Volley.newRequestQueue(getActivity());


        mDialog = LoadProcessDialog.showRoundProcessDialog(getActivity(), R.layout.loading_process_dialog_anim);
        final StringRequestUtil request = new StringRequestUtil(Request.Method.POST, "http://123.57.158.178:9090/celechem/getProductInfo.action", new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                List<ProductInfo> productInfos = new ArrayList<ProductInfo>();
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

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                ProductInfoAdapter productInfoAdapter = new ProductInfoAdapter(getActivity(), productInfos);
                newsListView.setAdapter(productInfoAdapter);
                mDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                mDialog.dismiss();
                Toast.makeText(getActivity(), "网络异常，请稍后再试。", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(request);


        newsListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                new AsyncTask<Void, Void, Void>() {

                    @Override
                    protected Void doInBackground(Void... params) {
                        try {
                            requestQueue.add(request);
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
                            Thread.sleep(0);
                        } catch (InterruptedException e) {
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
