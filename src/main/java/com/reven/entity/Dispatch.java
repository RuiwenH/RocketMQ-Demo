package com.reven.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "t_dispatch")
public class Dispatch   implements Serializable {
    /**
     * @Fields dispatchId 发货单id
     */
    @Id
    @Column(name = "`dispatch_id`")
    private Integer dispatchId;

    /**
     * @Fields orderId 订单id
     */
    @Column(name = "`order_id`")
    private Integer orderId;

    /**
     * @Fields orderNo 订单编号
     */
    @Column(name = "`order_no`")
    private String orderNo;

    /**
     * @Fields dispatchDate  发货日期
     */
    @Column(name = "`dispatch_date`")
    private Date dispatchDate;

    /**
     * @Fields createTime 创建时间
     */
    @Column(name = "`create_time`")
    private Date createTime;

    private static final long serialVersionUID = 1L;

    public Integer getDispatchId() {
        return dispatchId;
    }

    public void setDispatchId(Integer dispatchId) {
        this.dispatchId = dispatchId;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo == null ? null : orderNo.trim();
    }

    public Date getDispatchDate() {
        return dispatchDate;
    }

    public void setDispatchDate(Date dispatchDate) {
        this.dispatchDate = dispatchDate;
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
        sb.append(", dispatchId=").append(dispatchId);
        sb.append(", orderId=").append(orderId);
        sb.append(", orderNo=").append(orderNo);
        sb.append(", dispatchDate=").append(dispatchDate);
        sb.append(", createTime=").append(createTime);
        sb.append("]");
        return sb.toString();
    }
}