package com.zerovoid.shoppingcart.biz;

import android.widget.ImageView;

import com.zerovoid.shoppingcart.R;

/**
 * Created by 绯若虚无（https://github.com/joefei） on 2015/10/12.
 */
public class ShoppingCartBiz {

    /**
     * 选择全部，点下全部按钮，改变所有商品选中状态
     */
    public static void selectAll() {

    }

    /**
     * 勾与不勾选中选项
     *
     * @param isSelect 原先状态
     * @param ivCheck
     * @return 是否勾上，之后状态
     */
    public static boolean checkItem(boolean isSelect, ImageView ivCheck) {
        isSelect = !isSelect;
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
    public static boolean addGoodToShoppingCart(String productID,String number) {

        return false;
    }

}
