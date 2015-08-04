package com.heaven.heavenhelp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.heaven.heavenhelp.R;
import com.heaven.heavenhelp.model.UserInfo;

import java.util.List;

/**
 * Created by Acer-002 on 2015/7/15.
 */
public class UserInfoAdapter extends BaseAdapter {

    private List<UserInfo> persons;
    Context context;

    public UserInfoAdapter(Context context, List<UserInfo> persons) {
        this.persons = persons;
        this.context = context;
    }

    @Override
    public int getCount() {
        return (persons == null) ? 0 : persons.size();
    }

    @Override
    public Object getItem(int i) {
        return persons.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public class ViewHolder {
        TextView tv_user_name;
        TextView tv_user_age;
    }


    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        UserInfo userInfo = (UserInfo) getItem(i);
        ViewHolder viewHolder = null;

        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.news_list_item, null);
            viewHolder.tv_user_name = (TextView) convertView.findViewById(
                    R.id.user_name);
            viewHolder.tv_user_age = (TextView) convertView.findViewById(
                    R.id.user_age);
            convertView.setTag(viewHolder);
        }
        else{
            viewHolder= (ViewHolder)convertView.getTag();
        }

        viewHolder.tv_user_name.setText(userInfo.getUserName());
        viewHolder.tv_user_age.setText(userInfo.getUserAge());

        return convertView;
    }
}
