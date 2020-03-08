package com.atguigu.gmall.bean;

import java.io.Serializable;

/**
 *
 * @author liux
 * @since 2020-03-07 22:19:39
 */
public class PmsSkuImage implements Serializable {
    private static final long serialVersionUID = 303725598286085253L;
    /**
     * 编号
     */
    private String id;
    /**
     * 商品id
     */
    private String skuId;
    /**
     * 图片名称（冗余）
     */
    private String imgName;
    /**
     * 图片路径(冗余)
     */
    private String imgUrl;
    /**
     * 商品图片id
     */
    private String spuImgId;
    /**
     * 是否默认
     */
    private String isDefault;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }

    public String getImgName() {
        return imgName;
    }

    public void setImgName(String imgName) {
        this.imgName = imgName;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getSpuImgId() {
        return spuImgId;
    }

    public void setSpuImgId(String spuImgId) {
        this.spuImgId = spuImgId;
    }

    public String getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(String isDefault) {
        this.isDefault = isDefault;
    }

}