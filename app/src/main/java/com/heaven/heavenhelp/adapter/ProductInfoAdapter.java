package com.heaven.heavenhelp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.heaven.heavenhelp.R;
import com.heaven.heavenhelp.model.ProductInfo;
import com.heaven.heavenhelp.model.UserInfo;

import java.util.List;

/**
 * Created by Acer-002 on 2015/7/15.
 */
public class ProductInfoAdapter extends BaseAdapter {

    private List<ProductInfo> products;
    Context context;

    public ProductInfoAdapter(Context context, List<ProductInfo> products) {
        this.products = products;
        this.context = context;
    }

    @Override
    public int getCount() {
        return (products == null) ? 0 : products.size();
    }

    @Override
    public Object getItem(int i) {
        return products.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public class ViewHolder {
        TextView tv_product_name;
        TextView tv_product_price;
    }


    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        ProductInfo productInfo = (ProductInfo) getItem(i);
        ViewHolder viewHolder = null;

        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.news_list_item, null);
            viewHolder.tv_product_name = (TextView) convertView.findViewById(
                    R.id.user_name);
            viewHolder.tv_product_price = (TextView) convertView.findViewById(
                    R.id.user_age);
            convertView.setTag(viewHolder);
        }
        else{
            viewHolder= (ViewHolder)convertView.getTag();
        }

        viewHolder.tv_product_name.setText(productInfo.getProductName());
        viewHolder.tv_product_price.setText(productInfo.getProductPrice());

        return convertView;
    }
}
