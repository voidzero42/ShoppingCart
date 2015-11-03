package com.zerovoid.shoppingcart.biz;

import android.widget.ImageView;
import android.widget.TextView;

import com.zerovoid.common.config.SharePreferenceUtilNew;
import com.zerovoid.common.util.DecimalUtil;
import com.zerovoid.shoppingcart.R;
import com.zerovoid.shoppingcart.dao.ShoppingCartDao;
import com.zerovoid.shoppingcart.model.ShoppingCartBean;

import java.util.ArrayList;
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
            ivCheck.setImageResource(R.drawable.ic_checked);
        } else {
            ivCheck.setImageResource(R.drawable.ic_uncheck);
        }
        return isSelect;
    }

    /**=====================上面是界面改动部分，下面是数据变化部分=========================*/

    /**
     * 获取结算信息，肯定需要获取总价和数量，但是数据结构改变了，这里处理也要变；
     *
     * @return 0=选中的商品数量；1=选中的商品总价
     */
    public static String[] getShoppingCount(List<ShoppingCartBean> listGoods) {
        String[] infos = new String[2];
        String selectedCount = "0";
        String selectedMoney = "0";
        for (int i = 0; i < listGoods.size(); i++) {
            for (int j = 0; j < listGoods.get(i).getGoods().size(); j++) {
                boolean isSelectd = listGoods.get(i).getGoods().get(j).isChildSelected();
                if (isSelectd) {
                    String price = listGoods.get(i).getGoods().get(j).getPrice();
                    String num = listGoods.get(i).getGoods().get(j).getNumber();
                    String countMoney = DecimalUtil.multiply(price, num);
                    selectedMoney = DecimalUtil.add(selectedMoney, countMoney);
                    selectedCount = DecimalUtil.add(selectedCount, "1");
                }
            }
        }
        infos[0] = selectedCount;
        infos[1] = selectedMoney;
        return infos;
    }


    public static boolean hasSelectedGoods(List<ShoppingCartBean> listGoods) {
        String count = getShoppingCount(listGoods)[0];
        if ("0".equals(count)) {
            return false;
        }
        return true;
    }

    /**
     * 添加某商品的数量到数据库（非通用部分，都有这个动作，但是到底存什么，未可知）
     *
     * @param productID 此商品的规格ID
     * @param num       此商品的数量
     */
    public static void addGoodToCart(String productID, String num) {
        ShoppingCartDao.getInstance().saveShoppingInfo(productID, num);
    }

    /**
     * 删除某个商品,即删除其ProductID
     *
     * @param productID 规格ID
     */
    public static void delGood(String productID) {
        ShoppingCartDao.getInstance().deleteShoppingInfo(productID);
    }

    /** 删除全部商品 */
    public static void delAllGoods() {
        ShoppingCartDao.getInstance().delAllGoods();
    }

    /** 增减数量，操作通用，数据不通用 */
    public static void addOrReduceGoodsNum(boolean isPlus, ShoppingCartBean.Goods goods, TextView tvNum) {
        String currentNum = goods.getNumber().trim();
        String num = "1";
        if (isPlus) {
            num = String.valueOf(Integer.parseInt(currentNum) + 1);
        } else {
            int i = Integer.parseInt(currentNum);
            if (i > 1) {
                num = String.valueOf(i - 1);
            } else {
                num = "1";
            }
        }
        String productID = goods.getProductID();
        tvNum.setText(num);
        goods.setNumber(num);
        updateGoodsNumber(productID, num);
    }

    /**
     * 更新购物车的单个商品数量
     *
     * @param productID
     * @param num
     */
    public static void updateGoodsNumber(String productID, String num) {
        ShoppingCartDao.getInstance().updateGoodsNum(productID, num);
    }

    /**
     * 查询购物车商品总数量
     * <p/>
     * 统一使用该接口，而就行是通过何种方式获取数据，数据库、SP、文件、网络，都可以
     *
     * @return
     */
    public static int getGoodsCount() {
        return ShoppingCartDao.getInstance().getGoodsCount();
    }

    /**
     * 获取所有商品ID，用于向服务器请求数据（非通用部分）
     *
     * @return
     */
    public static List<String> getAllProductID() {
        return ShoppingCartDao.getInstance().getProductList();
    }

    /** 由于这次服务端没有保存商品数量，需要此步骤来处理数量（非通用部分） */
    public static void updateShopList(List<ShoppingCartBean> list) {
        if (list == null) {
            return;
        }
        for (int i = 0; i < list.size(); i++) {
            ShoppingCartBean scb = list.get(i);
            if (scb == null) {
                continue;
            }
            ArrayList<ShoppingCartBean.Goods> list2 = scb.getGoods();
            if (list2 == null) {
                continue;
            }
            for (int j = 0; j < list2.size(); j++) {
                ShoppingCartBean.Goods goods = list2.get(j);
                if (goods == null) {
                    continue;
                }
                String productID = goods.getProductID();
                String num = ShoppingCartDao.getInstance().getNumByProductID(productID);
                list.get(i).getGoods().get(j).setNumber(num);
            }
        }
    }

}
