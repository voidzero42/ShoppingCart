package com.zerovoid.shoppingcart.activity;

import java.util.List;

import android.app.Activity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.zerovoid.shoppingcart.R;

public class InfoDetailsAdapter extends BaseExpandableListAdapter {
    Activity activity;
    List<String> group;
    List<List<String>> child;

    public InfoDetailsAdapter(Activity a, List<String> group,
                              List<List<String>> child) {
        activity = a;
        this.group = group;
        this.child = child;
    }


    public void setGroupList(List<String> list) {
        this.group = list;
    }

    public void setChildList(List<List<String>> list) {
        this.child = list;
    }


    @Override
    public int getGroupCount() {
        return group.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        Log.e("Adapter", "child count = " + child.get(groupPosition).size());
        return child.get(groupPosition).size();
    }

    // group method stub
    @Override
    public Object getGroup(int groupPosition) {
        return group.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return child.get(groupPosition).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }


    @Override
    public View getChildView(final int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        String string = child.get(groupPosition).get(childPosition);
        ViewHolderChild holder = null;
        if (convertView == null) {
            holder = new ViewHolderChild();
            convertView = LayoutInflater.from(activity).inflate(
                    R.layout.item_elv_child_test, null);
            holder.tvDel = (TextView) convertView.findViewById(R.id.tvDel);
            holder.tvChild = (TextView) convertView.findViewById(R.id.tvItemChild);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolderChild) convertView.getTag();
        }
        holder.tvChild.setText(string);
        holder.tvDel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(activity, "删除zi", Toast.LENGTH_SHORT).show();
                child.get(groupPosition).remove(childPosition);
                if (child.get(groupPosition).size() == 0) {
                    child.remove(groupPosition);
                    group.remove(groupPosition);
                }
                notifyDataSetChanged();
            }
        });
        return convertView;
    }


    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {

        String string = group.get(groupPosition);
        ViewHolderGroup holder = null;
        if (convertView == null) {
            holder = new ViewHolderGroup();
            convertView = LayoutInflater.from(activity).inflate(
                    R.layout.item_elv_group_test, null);
            holder.tvGroup = (TextView) convertView.findViewById(R.id.tvShopNameGroup);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolderGroup) convertView.getTag();
        }
        holder.tvGroup.setText(string);
        return convertView;
    }


    @Override
    public boolean hasStableIds() {

        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {

        return true;
    }

    class ViewHolderGroup {
        TextView tvGroup;
    }

    class ViewHolderChild {
        TextView tvChild;
        TextView tvDel;
    }
}
