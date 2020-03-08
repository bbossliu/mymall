package com.atguigu.gmall.bean;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

public class PmsProductSaleAttr implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    @Column
    private String productId;
    @Column
    private String saleAttrId;
    @Column
    private String saleAttrName;
    @Transient
    private List<PmsProductSaleAttrValue> spuSaleAttrValueList ;

    public PmsProductSaleAttr() {
    }

    public PmsProductSaleAttr(String productId, String saleAttrId, String saleAttrName, List<PmsProductSaleAttrValue> spuSaleAttrValueList) {
        this.productId = productId;
        this.saleAttrId = saleAttrId;
        this.saleAttrName = saleAttrName;
        this.spuSaleAttrValueList = spuSaleAttrValueList;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getSaleAttrId() {
        return saleAttrId;
    }

    public void setSaleAttrId(String saleAttrId) {
        this.saleAttrId = saleAttrId;
    }

    public String getSaleAttrName() {
        return saleAttrName;
    }

    public void setSaleAttrName(String saleAttrName) {
        this.saleAttrName = saleAttrName;
    }

    public List<PmsProductSaleAttrValue> getSpuSaleAttrValueList() {
        return spuSaleAttrValueList;
    }

    public void setSpuSaleAttrValueList(List<PmsProductSaleAttrValue> spuSaleAttrValueList) {
        this.spuSaleAttrValueList = spuSaleAttrValueList;
    }
}
