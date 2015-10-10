package com.zerovoid.shoppingcart.adapter;

import android.content.Context;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.zerovoid.shoppingcart.R;

import java.util.ArrayList;

/**
 * Created by 绯若虚无 on 2015/10/10.
 */
public class MyExpandableListAdapter extends BaseExpandableListAdapter {
    private Context mContext;
    private ArrayList<String> mListGroup;
    private ArrayList<ArrayList<String>> mListChild;

    public MyExpandableListAdapter(Context context, ArrayList<String> listGroup, ArrayList<ArrayList<String>> listChild) {
        mContext = context;
        mListChild = listChild;
        mListGroup = listGroup;
    }

    @Override
    public int getGroupCount() {
        return mListGroup.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return mListChild.size();
    }

    @Override
    public Object getGroup(int i) {
        return mListGroup.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return mListChild.get(i).get(i1);
    }

    @Override
    public long getGroupId(int i) {
        return 0;
    }

    @Override
    public long getChildId(int i, int i1) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupViewHolder holder = null;
        if (convertView == null) {
            holder = new GroupViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_elv_group_test, parent, false);
            holder.tvGroup = (TextView) convertView.findViewById(R.id.tvItemGroup);
            convertView.setTag(holder);
        } else {
            holder = (GroupViewHolder) convertView.getTag();
        }
        holder.tvGroup.setText(mListGroup.get(groupPosition));
        Log.e("MyExpandableAdapter", "group text=" + mListGroup.get(groupPosition));
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildViewHolder holder = null;
        if (convertView == null) {
            holder = new ChildViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_elv_child_test, parent, false);
            holder.tvChild = (TextView) convertView.findViewById(R.id.tvItemChild);
            convertView.setTag(holder);
        } else {
            holder = (ChildViewHolder) convertView.getTag();
        }
        String text = mListChild.get(groupPosition).get(childPosition);
        Log.e("MyExpandableAdapter", "child text=" + text);
        holder.tvChild.setText(text);
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }

    class GroupViewHolder {
        TextView tvGroup;
    }

    class ChildViewHolder {
        TextView tvChild;
    }
}
