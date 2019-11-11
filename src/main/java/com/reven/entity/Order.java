package com.reven.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Table(name = "t_order")
public class Order implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * @Fields orderId 自增主键
     */
    @Id
    @Column(name = "`order_id`")
    private Integer orderId;

    /**
     * @Fields orderNo 订单编号
     */
    @Column(name = "`order_no`")
    private String orderNo;

    /**
     * @Fields userId 用户id
     */
    @Column(name = "`user_id`")
    private Integer userId;

    /**
     * @Fields date 订单日期
     */
    private Date date;

    /**
     * @Fields createTime 创建时间
     */
    @Column(name = "`create_time`")
    private Date createTime;

    /**
     * @Fields updateTime 更新时间
     */
    @Column(name = "`update_time`")
    private Date updateTime;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", orderId=").append(orderId);
        sb.append(", orderNo=").append(orderNo);
        sb.append(", userId=").append(userId);
        sb.append(", date=").append(date);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append("]");
        return sb.toString();
    }
}