package com.atguigu.gmall.bean;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

public class PmsSkuInfo implements Serializable {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    @Column
    private String spuId;
    @Column
    private String price;
    @Column
    private String skuName;
    @Column
    private String skuDesc;
    @Column
    private String weight;
    @Column
    private String tmId;
    @Column
    private String catalog3Id;
    @Transient
    private String skuDefaultImg;

    @Transient
    List<PmsSkuImage> skuImageList;

    @Transient
    List<PmsSkuAttrValue> skuAttrValueList;

    @Transient
    List<PmsSkuSaleAttrValue> skuSaleAttrValueList;

    public PmsSkuInfo() {
    }

    public PmsSkuInfo(String spuId, String price, String skuName, String skuDesc, String weight, String tmId, String catalog3Id, String skuDefaultImg, List<PmsSkuImage> skuImageList, List<PmsSkuAttrValue> skuAttrValueList, List<PmsSkuSaleAttrValue> skuSaleAttrValueList) {
        this.spuId = spuId;
        this.price = price;
        this.skuName = skuName;
        this.skuDesc = skuDesc;
        this.weight = weight;
        this.tmId = tmId;
        this.catalog3Id = catalog3Id;
        this.skuDefaultImg = skuDefaultImg;
        this.skuImageList = skuImageList;
        this.skuAttrValueList = skuAttrValueList;
        this.skuSaleAttrValueList = skuSaleAttrValueList;
    }

    public String getSpuId() {
        return spuId;
    }

    public void setSpuId(String spuId) {
        this.spuId = spuId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    public String getSkuDesc() {
        return skuDesc;
    }

    public void setSkuDesc(String skuDesc) {
        this.skuDesc = skuDesc;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getTmId() {
        return tmId;
    }

    public void setTmId(String tmId) {
        this.tmId = tmId;
    }

    public String getCatalog3Id() {
        return catalog3Id;
    }

    public void setCatalog3Id(String catalog3Id) {
        this.catalog3Id = catalog3Id;
    }

    public String getSkuDefaultImg() {
        return skuDefaultImg;
    }

    public void setSkuDefaultImg(String skuDefaultImg) {
        this.skuDefaultImg = skuDefaultImg;
    }

    public List<PmsSkuImage> getSkuImageList() {
        return skuImageList;
    }

    public void setSkuImageList(List<PmsSkuImage> skuImageList) {
        this.skuImageList = skuImageList;
    }

    public List<PmsSkuAttrValue> getSkuAttrValueList() {
        return skuAttrValueList;
    }

    public void setSkuAttrValueList(List<PmsSkuAttrValue> skuAttrValueList) {
        this.skuAttrValueList = skuAttrValueList;
    }

    public List<PmsSkuSaleAttrValue> getSkuSaleAttrValueList() {
        return skuSaleAttrValueList;
    }

    public void setSkuSaleAttrValueList(List<PmsSkuSaleAttrValue> skuSaleAttrValueList) {
        this.skuSaleAttrValueList = skuSaleAttrValueList;
    }
}
