package com.zerovoid.shoppingcart.model;

import java.util.ArrayList;

/**
 * Created by 绯若虚无（@link https://github.com/joefei） on 2015/10/12.
 */
public class ShoppingCartBean {

    public static final String EDIT_STATUS_TRUE = "0";

    public static final String EDIT_STATUS_FALSE = "1";

    /** 商品ID */
    private String goodsID;

    /** 是否处于编辑状态 */
    private String isEditStatus = EDIT_STATUS_TRUE;

    /** 商品名称 */
    private String goodsName;

    /** 店铺名称 */
    private String merchantName;

    /** 商品规格 */
    private ArrayList<Dispatch> pdtDesc;

    /** 现价 price */
    private String price;

    /** 原价 */
    private String mkPrice;

    /** 商品图片地址 */
    private String goodsLogo;


    /**商品类*/
    static class Goods{
        private String goodsID;
        private String goodsName;
        private String goodsLogo;
        private String pdtDesc;
        private String mkPrice;
        private String price;
        private String itemStat;
    }

    static class Dispatch {
        private String dispatchID;
        private String dispatchName;
        private String dispatchType;
        private String fee;
        private String limitFee;
    }

}
