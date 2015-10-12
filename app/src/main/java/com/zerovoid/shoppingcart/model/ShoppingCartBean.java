package com.zerovoid.shoppingcart.model;

/**
 * Created by 绯若虚无 on 2015/10/12.
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



}
