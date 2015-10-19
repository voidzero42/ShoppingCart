package com.zerovoid.shoppingcart.biz;

import android.util.Log;
import android.widget.ImageView;

import com.zerovoid.shoppingcart.R;
import com.zerovoid.shoppingcart.model.LocalGoods;
import com.zerovoid.shoppingcart.model.ShoppingCartBean;

import org.kymjs.kjframe.KJDB;

import java.util.List;

/**
 * Created by 绯若虚无（https://github.com/joefei） on 2015/10/12.
 */
public class ShoppingCartBiz {

    /**
     * 选择全部，点下全部按钮，改变所有商品选中状态
     */
    public static boolean selectAll(List<ShoppingCartBean> list, boolean isSelectAll, ImageView ivCheck) {
        isSelectAll = !isSelectAll;
        ShoppingCartBiz.checkItem(isSelectAll, ivCheck);
        for (int i = 0; i < list.size(); i++) {
            list.get(i).setIsGroupSelected(isSelectAll);
            for (int j = 0; j < list.get(i).getGoods().size(); j++) {
                list.get(i).getGoods().get(j).setIsChildSelected(isSelectAll);
            }
        }
        return isSelectAll;
    }

    /**
     * 族内的所有组，是否都被选中，即全选
     *
     * @param list
     * @return
     */
    private static boolean isSelectAllGroup(List<ShoppingCartBean> list) {
        for (int i = 0; i < list.size(); i++) {
            boolean isSelectGroup = list.get(i).isGroupSelected();
            if (!isSelectGroup) {
                return false;
            }
        }
        return true;
    }

    /**
     * 组内所有子选项是否全部被选中
     *
     * @param list
     * @return
     */
    private static boolean isSelectAllChild(List<ShoppingCartBean.Goods> list) {
        for (int i = 0; i < list.size(); i++) {
            boolean isSelectGroup = list.get(i).isChildSelected();
            if (!isSelectGroup) {
                return false;
            }
        }
        return true;
    }

    /**
     * 单选一个，需要判断整个组的标志，整个族的标志，是否被全选，取消，则
     * 除了选择全部和选择单个可以单独设置背景色，其他都是通过改变值，然后notify；
     *
     * @param list
     * @param groudPosition
     * @param childPosition
     * @return 是否选择全部
     */
    public static boolean selectOne(List<ShoppingCartBean> list, int groudPosition, int childPosition) {
        boolean isSelectAll;
        boolean isSelectedOne = !(list.get(groudPosition).getGoods().get(childPosition).isChildSelected());
        list.get(groudPosition).getGoods().get(childPosition).setIsChildSelected(isSelectedOne);//单个图标的处理
        boolean isSelectCurrentGroup = isSelectAllChild(list.get(groudPosition).getGoods());
        list.get(groudPosition).setIsGroupSelected(isSelectCurrentGroup);//组图标的处理
        isSelectAll = isSelectAllGroup(list);
        return isSelectAll;
    }

    public static boolean selectGroup(List<ShoppingCartBean> list, int groudPosition) {
        boolean isSelectAll;
        boolean isSelected = !(list.get(groudPosition).isGroupSelected());
        list.get(groudPosition).setIsGroupSelected(isSelected);
        for (int i = 0; i < list.get(groudPosition).getGoods().size(); i++) {
            list.get(groudPosition).getGoods().get(i).setIsChildSelected(isSelected);
        }
        isSelectAll = isSelectAllGroup(list);
        return isSelectAll;
    }

    /**
     * 勾与不勾选中选项
     *
     * @param isSelect 原先状态
     * @param ivCheck
     * @return 是否勾上，之后状态
     */
    public static boolean checkItem(boolean isSelect, ImageView ivCheck) {
        if (isSelect) {
            ivCheck.setImageResource(R.drawable.ic_shop);
        } else {
            ivCheck.setImageResource(R.drawable.ic_uncheck);
        }
        return isSelect;
    }

    /**
     * 添加到购物车
     *
     * @return 是否添加成功
     */
    public static boolean addGoodToShoppingCart(String productID, String number) {
        productID = "279457f3-4692-43bf-9676-fa9ab9155c38";
        number = "3";
//        KJDB db = KJDB.create();
//        LocalGoods goods = new LocalGoods();
//        goods.setGoodsNum(number);
//        goods.setProductID(productID);
//        db.save(goods);

        return false;
    }

    //优先完成两件事；
    //添加到购物车、查询总量、结算值传递；

    //使用SP来实现，增删改查，转List<Map>
    private static void addSP(){
        //format: v11,v12;v21,v22
        //split , ListMap put;
        //list.add
        //for stringbuilder sp (common)
    }
    private static void delSP(){}
    private static void updateSP(){}
    private static void querySP(){}

    public static void getGoodsCount() {
//        KJDB db = KJDB.create();
        // 这里是查找全部数据的
//        List<LocalGoods> list = db.findAll(LocalGoods.class);
//        StringBuilder str = new StringBuilder();
//        for (LocalGoods u : list) {
//            str.append(u.getProductID()).append("----");
//        }
//        Log.e("TAG", str.toString());
    }

}
