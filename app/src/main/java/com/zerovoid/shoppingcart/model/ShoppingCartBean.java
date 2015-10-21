package com.zerovoid.shoppingcart.model;

import java.util.ArrayList;

/**
 * Created by 绯若虚无（@link https://github.com/joefei） on 2015/10/12.
 */
public class ShoppingCartBean {

    public static final String EDIT_STATUS_TRUE = "0";

    public static final String EDIT_STATUS_FALSE = "1";


    /** 是否处于编辑状态 */
    private boolean isEditing;
    /** 组是否被选中 */
    private boolean isGroupSelected;

    /** 店铺名称 */
    private String merchantName;

    /** 店铺ID */
    private String merchantID;

    private ArrayList<Goods> goods;

    private ArrayList<Dispatch> dispatch;

    public boolean isEditing() {
        return isEditing;
    }

    public boolean isGroupSelected() {
        return isGroupSelected;
    }

    public void setIsGroupSelected(boolean isGroupSelected) {
        this.isGroupSelected = isGroupSelected;
    }

    public ArrayList<Goods> getGoods() {
        return goods;
    }

    public void setGoods(ArrayList<Goods> goods) {
        this.goods = goods;
    }

    public ArrayList<Dispatch> getDispatch() {
        return dispatch;
    }

    public void setDispatch(ArrayList<Dispatch> dispatch) {
        this.dispatch = dispatch;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getMerchantID() {
        return merchantID;
    }

    public void setMerchantID(String merchantID) {
        this.merchantID = merchantID;
    }

    public void setIsEditing(boolean isEditing) {
        this.isEditing = isEditing;
    }

    /** 商品类 */
    public static class Goods {
        /** 数量 */
        private String number;
        /** 商品ID */
        private String goodsID;
        /** 商品名称 */
        private String goodsName;
        /** 商品宣传图片 */
        private String goodsLogo;
        /** 商品规格 */
        private String pdtDesc;
        /** 市场价，原价 */
        private String mkPrice;
        /** 价格，当前价格 */
        private String price;
        /** 是否失效 */
        private String itemStat;
        /** 是否是编辑状态 */
        private boolean isEditing;
        /** 是否被选中 */
        private boolean isChildSelected;
        /** 规格ID */
        private String productID;

        public String getProductID() {
            return productID;
        }

        public void setProductID(String productID) {
            this.productID = productID;
        }

        public boolean isEditing() {
            return isEditing;
        }

        public void setIsEditing(boolean isEditing) {
            this.isEditing = isEditing;
        }

        public boolean isChildSelected() {
            return isChildSelected;
        }

        public void setIsChildSelected(boolean isChildSelected) {
            this.isChildSelected = isChildSelected;
        }

        public String getGoodsID() {
            return goodsID;
        }

        public void setGoodsID(String goodsID) {
            this.goodsID = goodsID;
        }

        public String getGoodsName() {
            return goodsName;
        }

        public void setGoodsName(String goodsName) {
            this.goodsName = goodsName;
        }

        public String getGoodsLogo() {
            return goodsLogo;
        }

        public void setGoodsLogo(String goodsLogo) {
            this.goodsLogo = goodsLogo;
        }

        public String getPdtDesc() {
            return pdtDesc;
        }

        public void setPdtDesc(String pdtDesc) {
            this.pdtDesc = pdtDesc;
        }

        public String getMkPrice() {
            return mkPrice;
        }

        public void setMkPrice(String mkPrice) {
            this.mkPrice = mkPrice;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getItemStat() {
            return itemStat;
        }

        public void setItemStat(String itemStat) {
            this.itemStat = itemStat;
        }

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }
    }

    public static class Dispatch {

        /** 配送ID */
        private String dispatchID;
        /** 配送方式名称 */
        private String dispatchName;
        /** 配送方式（1邮寄，2到店提货，3送货上门） */
        private String dispatchType;
        /** 配送费用 */
        private String fee;
        /** 满多少免运费 */
        private String limitFee;


        public String getDispatchID() {
            return dispatchID;
        }

        public void setDispatchID(String dispatchID) {
            this.dispatchID = dispatchID;
        }

        public String getDispatchName() {
            return dispatchName;
        }

        public void setDispatchName(String dispatchName) {
            this.dispatchName = dispatchName;
        }

        public String getDispatchType() {
            return dispatchType;
        }

        public void setDispatchType(String dispatchType) {
            this.dispatchType = dispatchType;
        }

        public String getFee() {
            return fee;
        }

        public void setFee(String fee) {
            this.fee = fee;
        }

        public String getLimitFee() {
            return limitFee;
        }

        public void setLimitFee(String limitFee) {
            this.limitFee = limitFee;
        }
    }

}
