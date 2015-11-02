package com.zerovoid.shoppingcart.adapter;

import android.app.Activity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zerovoid.common.view.UIAlertView;
import com.zerovoid.shoppingcart.R;
import com.zerovoid.shoppingcart.biz.ShoppingCartBiz;
import com.zerovoid.shoppingcart.model.ShoppingCartBean;

import java.util.ArrayList;
import java.util.List;

/**
 * * ---------神兽保佑 !---------
 * <p/>
 * ... ┏┓        ┏┓
 * ..┏┛┻━━━━┛┻┓
 * .┃              ┃
 * ┃      ━       ┃
 * ┃  ┳┛  ┗┳   ┃
 * ┃              ┃
 * ┃      ┻      ┃
 * ┃              ┃
 * ┗━┓      ┏━┛
 * ... ┃      ┃
 * .. ┃      ┃
 * . ┃      ┗━━━┓
 * ┃              ┣┓
 * ┃             ┏┛
 * ┗┓┓┏━┳┓┏┛
 * . ┃┫┫  ┃┫┫
 * .┗┻┛  ┗┻┛
 * <p/>
 * Created by 绯若虚无 on 2015/10/10.
 */
public class MyExpandableListAdapter extends BaseExpandableListAdapter {
    private Activity mContext;
    private List<ShoppingCartBean> mListGoods = new ArrayList<>();

    public MyExpandableListAdapter(Activity context) {
        mContext = context;
    }

    public void setList(List<ShoppingCartBean> mListGoods) {
        this.mListGoods = mListGoods;
    }

    @Override
    public int getGroupCount() {
        return mListGoods.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return mListGoods.get(groupPosition).getGoods().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mListGoods.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return mListGoods.get(groupPosition).getGoods().get(childPosition);
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
        holder.tvGroup.setText(mListGoods.get(groupPosition).getMerchantName());
        return convertView;
    }

    /**
     * @param groupPosition
     * @param childPosition
     * @param isLastChild
     * @param convertView
     * @param parent
     * @return
     */
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
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
        holder.tvDel.setTag(groupPosition + "," + childPosition);
        holder.tvDel.setOnClickListener(listener);
        String goodName = mListGoods.get(groupPosition).getGoods().get(childPosition).getGoodsName();
        holder.tvChild.setText(goodName);
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.tvDel:
                    String tagPos = String.valueOf(v.getTag());
                    if (tagPos.contains(",")) {
                        String s[] = tagPos.split(",");
                        int groupPosition = Integer.parseInt(s[0]);
                        int childPosition = Integer.parseInt(s[1]);
                        showDelDialog(groupPosition, childPosition);
                    }
                    break;
            }
        }
    };

    private void showDelDialog(final int groupPosition, final int childPosition) {
        final UIAlertView dialog = new UIAlertView(mContext, "温馨提示", "确认删除该商品吗?",
                "取消", "确定");
        dialog.show();

        dialog.setClicklistener(new UIAlertView.ClickListenerInterface() {

                                    @Override
                                    public void doLeft() {
                                        dialog.dismiss();
                                    }

                                    @Override
                                    public void doRight() {
                                        ShoppingCartBiz.delGood(mListGoods.get(groupPosition).getGoods().get(childPosition).getProductID());
                                        delGoods(groupPosition, childPosition);
//                    setTitle();
//                    setSettle();
                                        notifyDataSetChanged();
                                        dialog.dismiss();
                                    }
                                }
        );
    }

    private void delGoods(int groupPosition, int childPosition) {
        mListGoods.get(groupPosition).getGoods().remove(childPosition);
        if (mListGoods.get(groupPosition).getGoods().size() == 0) {
            mListGoods.remove(groupPosition);
        }
        notifyDataSetChanged();
    }

    class GroupViewHolder {
        TextView tvGroup;
        TextView tvEdit;
        ImageView ivCheckGroup;
    }

    class ChildViewHolder {
        TextView tvChild;
        TextView tvGoodsParam;
        ImageView ivCheckGood;
        LinearLayout llGoodInfo;
        RelativeLayout rlEditStatus;
        ImageView ivAdd;
        ImageView ivReduce;
        TextView tvDel;
        TextView tvPriceNew;
        TextView tvPriceOld;
        TextView tvNum;
        TextView tvNum2;
    }
}
