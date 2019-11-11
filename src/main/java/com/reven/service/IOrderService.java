package com.reven.service;

import com.reven.entity.Order;

import java.io.UnsupportedEncodingException;

import com.reven.core.IBaseService;

/**
 * Created by CodeGenerator on 2019/11/11.
 */
public interface IOrderService extends IBaseService<Order> {

    /**
     * @param order
     * @throws UnsupportedEncodingException
     */
    void saveWithMQ(Order order) throws UnsupportedEncodingException;


}
