package com.zerovoid.shoppingcart.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Paint;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zerovoid.common.util.DecimalUtil;
import com.zerovoid.common.util.ToastHelper;
import com.zerovoid.shoppingcart.R;
import com.zerovoid.shoppingcart.biz.ShoppingCartBiz;
import com.zerovoid.shoppingcart.biz.ShoppingCartHttpBiz;
import com.zerovoid.shoppingcart.model.ShoppingCartBean;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 绯若虚无 on 2015/10/10.
 */
public class MyExpandableListAdapter extends BaseExpandableListAdapter {
    private Activity mContext;
    private List<String> listGroup;
    private List<List<String>> listChild;

    public MyExpandableListAdapter(Activity context, List<String> listGroup, List<List<String>> listChild2) {
        mContext = context;
        this.listGroup = listGroup;
        this.listChild = listChild2;
    }


    public void setGroupList(List<String> list) {
        this.listGroup = list;
    }

    public void setChildList(List<List<String>> list) {
        this.listChild = list;
    }

    @Override
    public int getGroupCount() {
        return listGroup.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        Log.e("Adapter", "child count = " + listChild.get(groupPosition).size());
        return listChild.get(groupPosition).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return listGroup.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return listChild.get(groupPosition).get(childPosition);
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
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupViewHolder holder = null;
        if (convertView == null) {
            holder = new GroupViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_elv_group_test, parent, false);
            holder.tvGroup = (TextView) convertView.findViewById(R.id.tvShopNameGroup);
            convertView.setTag(holder);
        } else {
            holder = (GroupViewHolder) convertView.getTag();
        }
        holder.tvGroup.setText(listGroup.get(groupPosition));
        return convertView;
    }

    /**
     * ---------Dragon be here !-------------------------
     * <p/>
     * .. ┏┓        ┏┓
     * ┏┛┻━━━━┛┻┓
     * ┃              ┃
     * ┃      ━       ┃
     * ┃  ┳┛  ┗┳   ┃
     * ┃              ┃
     * ┃      ┻      ┃
     * ┃              ┃
     * ┗━┓      ┏━┛
     * ┃      ┃
     * ┃      ┃
     * ┃      ┗━━━┓
     * ┃              ┣┓
     * ┃             ┏┛
     * ┗┓┓┏━┳┓┏┛
     * ┃┫┫  ┃┫┫
     * ┗┻┛  ┗┻┛
     *
     * @param groupPosition
     * @param childPosition
     * @param isLastChild
     * @param convertView
     * @param parent
     * @return
     */
    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildViewHolder holder = null;
        if (convertView == null) {
            holder = new ChildViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_elv_child_test, parent, false);
            holder.tvChild = (TextView) convertView.findViewById(R.id.tvItemChild);
            holder.tvDel = (TextView) convertView.findViewById(R.id.tvDel);
            convertView.setTag(holder);
        } else {
            holder = (ChildViewHolder) convertView.getTag();
        }
        holder.tvDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listChild.get(groupPosition).remove(childPosition);
                if (listChild.get(groupPosition).size() == 0) {
                    listChild.remove(groupPosition);
                    listGroup.remove(groupPosition);
                }
                notifyDataSetChanged();
            }
        });

        Log.e("MyExpandableAdapter", "child text=" + listChild.get(groupPosition).get(childPosition));
        holder.tvChild.setText(listChild.get(groupPosition).get(childPosition));
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
        TextView tvDel;

    }
}
