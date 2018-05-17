package com.wwwarehouse.xdw.openapi.dao.model;

import java.io.Serializable;
import java.util.Date;

public class SyParamDO implements Serializable {

    public static final String L_NEED_DOWN_TRADE = "NEED_DOWN_TRADE";			//是否需要下载订单
    public static final String L_NEED_DOWN_TRADE_HIS = "NEED_DOWN_TRADE_HIS";	//是否需要下载订单
    public static final String L_NEED_DOWN_PRODUCT = "NEED_DOWN_RPODUCT";		//是否需要下载商品
    public static final String S_DOWN_TRADE_STATUS = "DOWN_TRADE_STATUS";		//下载订单的状态
    public static final String S_DOWN_TRADE_STATUS_HIS = "DOWN_TRADE_STATUS_HIS";//下载历史订单的状态
    public static final String D_LAST_TRADE = "LAST_TRADE";						//上次下载订单时间
    public static final String D_LAST_TRADE_HIS = "LAST_TRADE_HIS";				//历史下载订单时间
    public static final String L_HIS_TRADE_TIME_RANGE = "HIS_TRADE_TIME_RANGE";	//下载历史订单的往前推时间长度（天）
    public static final String L_PRODUCT_TIME_RANGE = "PRODUCT_TIME_RANGE";		//下载商品的往前推时间长度（天）
    public static final String D_LAST_TRADE_INC = "LAST_TRADE_INC";				//上次增量下载订单的时间
    public static final String D_LAST_TRADERATE = "LAST_TRADERATE";
    public static final String D_LAST_PURCHASE = "LAST_PURCHASE";
    public static final String D_LAST_REFUND = "LAST_REFUND";
    public static final String S_SHOP_SCORE_URL = "SHOP_SCORE_URL";
    public static final String L_HAS_PRESALE = "HAS_PRESALE";
    public static final String L_NOT_SHIP_NOTICE = "NOT_SHIP_NOTICE";
    public static final String L_REQUIRE_SHIP_DAY = "REQUIRE_SHIP_DAY";
    public static final String L_AUTO_ADD_RETURN = "AUTO_ADD_RETURN";
    public static final String L_FLOOR_COD_FEE = "FLOOR_COD_FEE";
    public static final String L_AUTO_ALLOCATION_RETURN = "AUTO_ALLOCATION_RETURN";
    public static final String L_DOWN_RATE_FLAG = "DOWN_RATE_FLAG";
    public static final String L_PUSH_TMC = "PUSH_TMC";
    public static final String L_END_REMOVE_ORDER_STATUS = "END_REMOVE_ORDER_STATUS";
    public static final String D_LAST_SYN_INVENTORY = "LAST_SYN_INVENTORY";
    public static final String L_SHIP_TRACK_TYPE = "SHIP_TRACK_TYPE";
    public static final String S_API_URL = "API_URL";

    private Long relatedId;

    private String paramName;

    private String paramValue;

    private String remark;

    private Date updateTime;

    private Long updateUserId;

    private Long createUserId;

    private Date createTime;

    private static final long serialVersionUID = 1L;

    public Long getRelatedId() {
        return relatedId;
    }

    public void setRelatedId(Long relatedId) {
        this.relatedId = relatedId;
    }

    public String getParamName() {
        return paramName;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    public String getParamValue() {
        return paramValue;
    }

    public void setParamValue(String paramValue) {
        this.paramValue = paramValue;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Long getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(Long updateUserId) {
        this.updateUserId = updateUserId;
    }

    public Long getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Long createUserId) {
        this.createUserId = createUserId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", relatedId=").append(relatedId);
        sb.append(", paramName=").append(paramName);
        sb.append(", paramValue=").append(paramValue);
        sb.append(", remark=").append(remark);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", updateUserId=").append(updateUserId);
        sb.append(", createUserId=").append(createUserId);
        sb.append(", createTime=").append(createTime);
        sb.append("]");
        return sb.toString();
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        SyParamDO other = (SyParamDO) that;
        return (this.getRelatedId() == null ? other.getRelatedId() == null : this.getRelatedId().equals(other.getRelatedId()))
            && (this.getParamName() == null ? other.getParamName() == null : this.getParamName().equals(other.getParamName()))
            && (this.getParamValue() == null ? other.getParamValue() == null : this.getParamValue().equals(other.getParamValue()))
            && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
            && (this.getUpdateUserId() == null ? other.getUpdateUserId() == null : this.getUpdateUserId().equals(other.getUpdateUserId()))
            && (this.getCreateUserId() == null ? other.getCreateUserId() == null : this.getCreateUserId().equals(other.getCreateUserId()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getRelatedId() == null) ? 0 : getRelatedId().hashCode());
        result = prime * result + ((getParamName() == null) ? 0 : getParamName().hashCode());
        result = prime * result + ((getParamValue() == null) ? 0 : getParamValue().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        result = prime * result + ((getUpdateUserId() == null) ? 0 : getUpdateUserId().hashCode());
        result = prime * result + ((getCreateUserId() == null) ? 0 : getCreateUserId().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        return result;
    }
}