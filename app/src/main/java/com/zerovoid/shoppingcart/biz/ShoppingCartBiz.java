package com.zerovoid.shoppingcart.biz;

import android.widget.ImageView;
import android.widget.TextView;

import com.zerovoid.common.config.SharePreferenceUtilNew;
import com.zerovoid.shoppingcart.R;
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


    //TODO 添加到购物车、查询总量、结算值传递；

    /**
     * 添加某商品的数量到数据库
     *
     * @param productID 此商品的规格ID
     * @param num       此商品的数量
     */
    public static void saveGoodNum(String productID, String num) {
        String goodsInfo = SharePreferenceUtilNew.getInstance().getGoodsInfo();
        StringBuilder sb = new StringBuilder();
        sb.append(goodsInfo);
        if (!"".equals(goodsInfo)) {
            sb.append(";");
        }
        String newInfo = sb.append(productID).append(",").append(num).toString();
        SharePreferenceUtilNew.getInstance().setGoodsInfo(newInfo);
    }

    /**
     * 删除某个商品,即删除其ProductID
     *
     * @param productID 规格ID
     */
    public static void delGood(String productID) {
        String goodsInfo = SharePreferenceUtilNew.getInstance().getGoodsInfo();
        if ("".equals(goodsInfo)) {
            return;
        }
        String[] s1 = goodsInfo.split(";");
        int delPosition = -1;
        for (int i = 0; i < s1.length; i++) {
            String s2[] = s1[i].split(",");
            if (s2[0].equals(productID)) {
                delPosition = i;
                break;
            }
        }
        if (delPosition != -1) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < s1.length; i++) {
                if (i == delPosition) {
                    continue;
                }
                sb.append(s1[i]);
                if (i != s1.length - 1) {
                    sb.append(";");
                }
            }
            SharePreferenceUtilNew.getInstance().setGoodsInfo(sb.toString());
        }
    }

    public static void calcuGoodsNum(boolean isPlus, ShoppingCartBean.Goods goods, TextView tvNum) {
        String currentNum = goods.getNumber().trim();
        if (currentNum.contains("X") || currentNum.contains("x")) {
            currentNum = currentNum.substring(1);
        }
        String num = "0";
        if (isPlus) {
            num = String.valueOf(Integer.parseInt(currentNum) + 1);
        } else {
            int i = Integer.parseInt(currentNum);
            if (i > 0) {
                num = String.valueOf(i - 1);
            }
        }
        String productID = goods.getProductID();
        tvNum.setText("X" + num);
        goods.setNumber("X" + num);
        //存到SP里的，没有X
        updateSP(productID, num);
    }

    /**
     * 更新购物车的单个商品数量
     *
     * @param productID
     * @param num
     */
    public static void updateSP(String productID, String num) {
        String goodsInfo = SharePreferenceUtilNew.getInstance().getGoodsInfo();
        if ("".equals(goodsInfo)) {
            return;
        }
        String[] s1 = goodsInfo.split(";");
        for (int i = 0; i < s1.length; i++) {
            String s2[] = s1[i].split(",");
            if (s2[0].equals(productID)) {
                s1[i] = s2[0] + "," + num;
                break;
            }
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s1.length; i++) {
            sb.append(s1[i]);
            if (i != s1.length - 1) {
                sb.append(";");
            }
        }
        SharePreferenceUtilNew.getInstance().setGoodsInfo(sb.toString());
    }

    /**
     * 查询购物车商品总数量
     *
     * @return
     */
    public static int getGoodsCount() {
        int count = 0;
        String goodsInfo = SharePreferenceUtilNew.getInstance().getGoodsInfo();
        if (!"".equals(goodsInfo)) {
            String[] s1 = goodsInfo.split(";");
            count = s1.length;
        }
        return count;
    }

    public static List<String> getAllProductID() {
        List<String> list = new ArrayList<>();
        String goodsInfo = SharePreferenceUtilNew.getInstance().getGoodsInfo();
        String[] s1 = goodsInfo.split(";");
        if (!"".equals(goodsInfo)) {
            for (int i = 0; i < s1.length; i++) {
                String s2[] = s1[i].split(",");
                list.add(s2[0]);
            }
        }
        return list;
    }

    public static void updateShopList(List<ShoppingCartBean> list) {
        String goodsInfo = SharePreferenceUtilNew.getInstance().getGoodsInfo();
        String[] s1 = goodsInfo.split(";");

        for (int i = 0; i < list.size(); i++) {
            ArrayList<ShoppingCartBean.Goods> list2 = list.get(i).getGoods();
            for (int j = 0; j < list2.size(); j++) {
                String productID = list2.get(j).getProductID();
                for (int m = 0; m < s1.length; m++) {
                    String s2[] = s1[m].split(",");
                    if (productID.equals(s2[0])) {
                        list.get(i).getGoods().get(j).setNumber("X" + s2[1]);
                        break;
                    }
                }
            }
        }
    }

}
