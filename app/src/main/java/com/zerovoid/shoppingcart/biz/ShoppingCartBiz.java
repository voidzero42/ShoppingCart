package com.zerovoid.shoppingcart.biz;

import android.widget.ImageView;

import com.zerovoid.shoppingcart.R;
import com.zerovoid.shoppingcart.model.ShoppingCartBean;

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

    public static List<ShoppingCartBean> selectOne(List<ShoppingCartBean> list, int groudPosition, int childPosition, ImageView ivCheck) {
        boolean isSelected = !(list.get(groudPosition).getGoods().get(childPosition).isChildSelected());
        checkItem(isSelected, ivCheck);
        list.get(groudPosition).getGoods().get(childPosition).setIsChildSelected(isSelected);
        return list;
    }

    public static List<ShoppingCartBean> selectGroup(List<ShoppingCartBean> list, int groudPosition) {
        boolean isSelected = !(list.get(groudPosition).isGroupSelected());
        list.get(groudPosition).setIsGroupSelected(isSelected);
        for (int i = 0; i < list.get(groudPosition).getGoods().size(); i++) {
            list.get(groudPosition).getGoods().get(i).setIsChildSelected(isSelected);
        }
        return list;
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
            ivCheck.setImageResource(R.drawable.ic_checked);
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

        return false;
    }

}
