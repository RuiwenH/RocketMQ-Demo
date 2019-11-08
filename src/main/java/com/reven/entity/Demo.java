package com.reven.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

/**
 * @author reven
 */
@Table(name = "t_demo")
@Data
public class Demo implements Serializable {
    
    private static final long serialVersionUID = 1L;
    /**
     * @Fields id 自增主键id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * @Fields name 名称
     */
    private String name;

    /**
     * @Fields date 数据日期
     */
    private Date date;

    /**
     * @Fields timestamp 更新记录时刷新当前时间戳记时
     */
    private Date timestamp;

    /**
     * @Fields key 测试关键字
     */
    @Column(name = "`KEY`")
    private String key;

    @Column(name = "`ac dd`")
    private String acDd;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", name=").append(name);
        sb.append(", date=").append(date);
        sb.append(", timestamp=").append(timestamp);
        sb.append(", key=").append(key);
        sb.append(", acDd=").append(acDd);
        sb.append("]");
        return sb.toString();
    }
}