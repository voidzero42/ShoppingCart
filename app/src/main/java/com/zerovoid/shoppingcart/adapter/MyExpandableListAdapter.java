package com.zerovoid.shoppingcart.adapter;

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
    private Context mContext;
    private List<ShoppingCartBean> mListChild;
    private ImageView ivSelectAll;
    private TextView btnSettle;
    private TextView tvCountMoney;
    private TextView tvTitle;
    private boolean isSelectAll = false;
    private int goodsCount = 0;
    private String selectedCount = "0";
    private String selectedMoney = "0";

    /**
     * 将选择全部的图片视图传入Adapter（这种写法非常不好，增加了几个类之间的耦合度，应该写一个独立的UI组件）
     *
     * @param ivSelectAll
     */
    public void setImageViewSelectAll(ImageView ivSelectAll, TextView btnSettle, TextView tvCountMoney, TextView tvTitle) {
        this.ivSelectAll = ivSelectAll;
        this.btnSettle = btnSettle;
        this.tvCountMoney = tvCountMoney;
        this.tvTitle = tvTitle;
        this.ivSelectAll.setOnClickListener(listener);
        this.tvCountMoney.setOnClickListener(listener);
        this.btnSettle.setOnClickListener(listener);
        setTitle();
        setSettle();
    }

    private void setSettle() {
        selectedCount = "0";
        selectedMoney = "0";
        for (int i = 0; i < mListChild.size(); i++) {
            for (int j = 0; j < mListChild.get(i).getGoods().size(); j++) {
                boolean isSelectd = mListChild.get(i).getGoods().get(j).isChildSelected();
                if (isSelectd) {
                    String price = mListChild.get(i).getGoods().get(j).getPrice();
                    String num = mListChild.get(i).getGoods().get(j).getNumber();
                    if (num.contains("X") || num.contains("x")) {
                        num = num.substring(1);
                    }
                    String countMoney = DecimalUtil.multiply(price, num);
                    selectedMoney = DecimalUtil.add(selectedMoney, countMoney);
                    selectedCount = DecimalUtil.add(selectedCount, "1");
                }
            }
        }
        String countMoney = String.format(mContext.getResources().getString(R.string.count_money), selectedMoney + "");
        String countGoods = String.format(mContext.getResources().getString(R.string.count_goods), selectedCount + "");
        tvCountMoney.setText(countMoney);
        btnSettle.setText(countGoods);
    }

    private void setTitle() {
        goodsCount = ShoppingCartBiz.getGoodsCount();
        String title = String.format(mContext.getResources().getString(R.string.shop_title), goodsCount + "");
        tvTitle.setText(title);
    }

    public MyExpandableListAdapter(Context context, List<ShoppingCartBean> listChild) {
        mContext = context;
        mListChild = listChild;
    }

    public void setList(List<ShoppingCartBean> mListChildGoods) {
        mListChild.clear();
        mListChild.addAll(mListChildGoods);
    }

    private List<String> listGroup = new ArrayList<>();

    public void setGroupList(List<String> list) {
        this.listGroup = list;
    }

    @Override
    public int getGroupCount() {
        return listGroup.size();
    }

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
        boolean isEditing = mListChild.get(groupPosition).isEditing();
        if (isEditing) {
            holder.tvEdit.setText("完成");
        } else {
            holder.tvEdit.setText("编辑");
        }
//        holder.ivCheckGroup.setTag(groupPosition);
//        holder.ivCheckGroup.setOnClickListener(listener);
        holder.tvEdit.setTag(groupPosition);
        holder.tvEdit.setOnClickListener(listener);
//        holder.tvGroup.setText(mListChild.get(groupPosition).getMerchantName());
        holder.tvGroup.setText(listGroup.get(groupPosition));
        return convertView;
    }

    /**
     * ---------Dragon be here !-------------------------
     * <p/>
     * —— ——
     *
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
            holder.ivCheckGood = (ImageView) convertView.findViewById(R.id.ivCheckGood);
            holder.rlEditStatus = (RelativeLayout) convertView.findViewById(R.id.rlEditStatus);
            holder.llGoodInfo = (LinearLayout) convertView.findViewById(R.id.llGoodInfo);
            holder.ivAdd = (ImageView) convertView.findViewById(R.id.ivAdd);
            holder.ivReduce = (ImageView) convertView.findViewById(R.id.ivReduce);
            holder.tvDel = (TextView) convertView.findViewById(R.id.tvDel);
            holder.tvGoodsParam = (TextView) convertView.findViewById(R.id.tvGoodsParam);
            holder.tvPriceNew = (TextView) convertView.findViewById(R.id.tvPriceNew);
            holder.tvPriceOld = (TextView) convertView.findViewById(R.id.tvPriceOld);
            holder.tvPriceOld.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            holder.tvNum = (TextView) convertView.findViewById(R.id.tvNum);
            holder.tvNum2 = (TextView) convertView.findViewById(R.id.tvNum2);
            convertView.setTag(holder);
        } else {
            holder = (ChildViewHolder) convertView.getTag();
        }
        ShoppingCartBean.Goods goods = mListChild.get(groupPosition).getGoods().get(childPosition);
        String text = goods.getGoodsName();
        boolean isEditing = goods.isEditing();
        String priceNew = "¥" + goods.getPrice();
        String priceOld = "¥" + goods.getMkPrice();
        String num = goods.getNumber();
        String pdtDesc = goods.getPdtDesc();
        ShoppingCartBiz.checkItem(mListChild.get(groupPosition).getGoods().get(childPosition).isChildSelected(), holder.ivCheckGood);
        holder.ivCheckGood.setTag(groupPosition + "," + childPosition);
        holder.ivCheckGood.setOnClickListener(listener);
        holder.ivAdd.setTag(goods);
        holder.ivReduce.setTag(goods);
        holder.tvDel.setTag(groupPosition + "," + childPosition);
        holder.tvDel.setOnClickListener(listener);
        holder.ivAdd.setOnClickListener(listener);
        holder.ivReduce.setOnClickListener(listener);
        holder.tvPriceNew.setText(priceNew);
        holder.tvPriceOld.setText(priceOld);
        holder.tvNum.setText(num);
        holder.tvNum2.setText(num);
        holder.tvGoodsParam.setText(pdtDesc);
        if (isEditing) {
            holder.llGoodInfo.setVisibility(View.GONE);
            holder.rlEditStatus.setVisibility(View.VISIBLE);
        } else {
            holder.llGoodInfo.setVisibility(View.VISIBLE);
            holder.rlEditStatus.setVisibility(View.GONE);
        }
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
                        setSettle();
                        notifyDataSetChanged();
                    }
                    break;
                case R.id.tvEdit://切换界面
                    ToastHelper.getInstance()._toast("编辑");
                    int groupPosition2 = Integer.parseInt(String.valueOf(view.getTag()));

                    boolean isEditing = !(mListChild.get(groupPosition2).isEditing());
                    mListChild.get(groupPosition2).setIsEditing(isEditing);
                    if (!isEditing) {

                    }
                    for (int i = 0; i < mListChild.get(groupPosition2).getGoods().size(); i++) {
                        mListChild.get(groupPosition2).getGoods().get(i).setIsEditing(isEditing);
                    }
                    notifyDataSetChanged();
                    break;
                case R.id.ivCheckGroup:
                    int groupPosition = Integer.parseInt(String.valueOf(view.getTag()));
                    isSelectAll = ShoppingCartBiz.selectGroup(mListChild, groupPosition);
                    ShoppingCartBiz.checkItem(isSelectAll, ivSelectAll);
                    setSettle();
                    notifyDataSetChanged();
                    break;
                case R.id.ivSelectAll:
                    isSelectAll = ShoppingCartBiz.selectAll(mListChild, isSelectAll, (ImageView) view);
                    setSettle();
                    notifyDataSetChanged();
                    break;
                case R.id.tvEditAll:
                    Toast.makeText(mContext, "编辑全部", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.tvDel:
                    String tag2 = String.valueOf(view.getTag());
                    if (tag2.contains(",")) {
                        String s[] = tag2.split(",");
                        int groupPosition3 = Integer.parseInt(s[0]);
                        int childPosition = Integer.parseInt(s[1]);
                        ShoppingCartBiz.delGood(mListChild.get(groupPosition3).getGoods().get(childPosition).getProductID());
                        mListChild.get(groupPosition3).getGoods().remove(childPosition);
                        if (mListChild.get(groupPosition3).getGoods().size() == 0) {
                            mListChild.remove(groupPosition3);
                            listGroup.remove(groupPosition3);
                        }
                        notifyDataSetChanged();
                        setTitle();
                        setSettle();
                    }
                    break;
                case R.id.ivAdd:
                    ShoppingCartBiz.calcuGoodsNum(true, (ShoppingCartBean.Goods) view.getTag(), ((TextView) (((View) (view.getParent())).findViewById(R.id.tvNum2))));
                    setSettle();
                    break;
                case R.id.ivReduce:
                    ShoppingCartBiz.calcuGoodsNum(false, (ShoppingCartBean.Goods) view.getTag(), ((TextView) (((View) (view.getParent())).findViewById(R.id.tvNum2))));
                    setSettle();
                    break;
                case R.id.btnSettle:
                    ToastHelper.getInstance()._toast("结算跳转");
                    break;
                case R.id.tvCountMoney:
                    ToastHelper.getInstance()._toast("总价");
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
