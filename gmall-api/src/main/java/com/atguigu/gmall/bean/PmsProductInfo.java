package com.atguigu.gmall.bean;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

public class PmsProductInfo implements Serializable {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    @Column
    private String productName;
    @Column
    private String description;
    @Column
    private String catalog3Id;
    @Column
    private String tmId;
    @Transient
    private List<PmsProductImage> spuImageList ;
    @Transient
    private  List<PmsProductSaleAttr> spuSaleAttrList ;

    public PmsProductInfo() {
    }

    public PmsProductInfo(String productName, String description, String catalog3Id, String tmId, List<PmsProductImage> spuImageList, List<PmsProductSaleAttr> spuSaleAttrList) {
        this.productName = productName;
        this.description = description;
        this.catalog3Id = catalog3Id;
        this.tmId = tmId;
        this.spuImageList = spuImageList;
        this.spuSaleAttrList = spuSaleAttrList;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCatalog3Id() {
        return catalog3Id;
    }

    public void setCatalog3Id(String catalog3Id) {
        this.catalog3Id = catalog3Id;
    }

    public String getTmId() {
        return tmId;
    }

    public void setTmId(String tmId) {
        this.tmId = tmId;
    }

    public List<PmsProductImage> getSpuImageList() {
        return spuImageList;
    }

    public void setSpuImageList(List<PmsProductImage> spuImageList) {
        this.spuImageList = spuImageList;
    }

    public List<PmsProductSaleAttr> getSpuSaleAttrList() {
        return spuSaleAttrList;
    }

    public void setSpuSaleAttrList(List<PmsProductSaleAttr> spuSaleAttrList) {
        this.spuSaleAttrList = spuSaleAttrList;
    }
}
