package com.zerovoid.shoppingcart.adapter;

import android.content.Context;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zerovoid.shoppingcart.R;
import com.zerovoid.shoppingcart.biz.ShoppingCartBiz;
import com.zerovoid.shoppingcart.biz.ShoppingCartHttpBiz;
import com.zerovoid.shoppingcart.model.ShoppingCartBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 绯若虚无 on 2015/10/10.
 */
public class MyExpandableListAdapter extends BaseExpandableListAdapter {
    private Context mContext;
    private List<ShoppingCartBean> mListChild;
    private ImageView ivSelectAll;
    boolean isSelectAll = false;

    /**
     * 将选择全部的图片视图传入Adapter（这种写法非常不好，增加了几个类之间的耦合度，应该写一个独立的UI组件）
     *
     * @param ivSelectAll
     */
    public void setImageViewSelectAll(ImageView ivSelectAll) {
        this.ivSelectAll = ivSelectAll;
    }

    public MyExpandableListAdapter(Context context, List<ShoppingCartBean> listChild) {
        mContext = context;
        mListChild = listChild;
    }

    public void setList(List<ShoppingCartBean> mListChildGoods) {
        mListChild.clear();
        mListChild.addAll(mListChildGoods);
    }

    @Override
    public int getGroupCount() {
        return mListChild.size();
    }

    //mListChild.get(i).getGoods().size()
    @Override
    public int getChildrenCount(int i) {
        return mListChild.get(i).getGoods().size();
    }

    @Override
    public Object getGroup(int i) {
        return null;
    }

    @Override
    public Object getChild(int i, int i1) {
        return mListChild.get(i).getGoods();
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
            holder.tvGroup = (TextView) convertView.findViewById(R.id.tvShopNameGroup);
            holder.tvEdit = (TextView) convertView.findViewById(R.id.tvEdit);
            holder.ivCheckGroup = (ImageView) convertView.findViewById(R.id.ivCheckGroup);
            convertView.setTag(holder);
        } else {
            holder = (GroupViewHolder) convertView.getTag();
        }
        ShoppingCartBiz.checkItem(mListChild.get(groupPosition).isGroupSelected(), holder.ivCheckGroup);
        holder.ivCheckGroup.setTag(groupPosition);
        holder.ivCheckGroup.setOnClickListener(listener);
        holder.tvEdit.setOnClickListener(listener);
        holder.tvGroup.setText(mListChild.get(groupPosition).getMerchantName());
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildViewHolder holder = null;
        if (convertView == null) {
            holder = new ChildViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_elv_child_test, parent, false);
            holder.tvChild = (TextView) convertView.findViewById(R.id.tvItemChild);
            holder.ivCheckGood = (ImageView) convertView.findViewById(R.id.ivCheckGood);
            holder.rlEditStatus = (RelativeLayout) convertView.findViewById(R.id.rlEditStatus);
            holder.llGoodInfo = (LinearLayout) convertView.findViewById(R.id.llGoodInfo);
            convertView.setTag(holder);
        } else {
            holder = (ChildViewHolder) convertView.getTag();
        }
        ShoppingCartBean.Goods goods = mListChild.get(groupPosition).getGoods().get(childPosition);
        String text = goods.getGoodsName();
        String itemStat = goods.getItemStat();
        ShoppingCartBiz.checkItem(mListChild.get(groupPosition).getGoods().get(childPosition).isChildSelected(), holder.ivCheckGood);
        holder.ivCheckGood.setTag(groupPosition + "," + childPosition);
        holder.ivCheckGood.setOnClickListener(listener);
        Log.e("MyExpandableAdapter", "child text=" + text);
        holder.tvChild.setText(text);
        return convertView;
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.ivCheckGood:
                    String tag = String.valueOf(view.getTag());
                    if (tag.contains(",")) {
                        String s[] = tag.split(",");
                        int groupPosition = Integer.parseInt(s[0]);
                        int childPosition = Integer.parseInt(s[1]);
                        isSelectAll = ShoppingCartBiz.selectOne(mListChild, groupPosition, childPosition);
                        ShoppingCartBiz.checkItem(isSelectAll, ivSelectAll);
                        notifyDataSetChanged();
                    }
                    break;
                case R.id.tvEdit:

                    break;
                case R.id.ivCheckGroup:
                    int groupPosition = Integer.parseInt(String.valueOf(view.getTag()));
                    isSelectAll = ShoppingCartBiz.selectGroup(mListChild, groupPosition);
                    ShoppingCartBiz.checkItem(isSelectAll, ivSelectAll);
                    notifyDataSetChanged();
                    break;
                case R.id.ivSelectAll:
                    isSelectAll = ShoppingCartBiz.selectAll(mListChild, isSelectAll, (ImageView) view);
                    notifyDataSetChanged();
                    break;
                case R.id.tvEditAll:
                    Toast.makeText(mContext, "编辑全部", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }

    class GroupViewHolder {
        TextView tvGroup;
        TextView tvEdit;
        ImageView ivCheckGroup;
    }

    class ChildViewHolder {
        TextView tvChild;
        ImageView ivCheckGood;
        LinearLayout llGoodInfo;
        RelativeLayout rlEditStatus;
    }
}
