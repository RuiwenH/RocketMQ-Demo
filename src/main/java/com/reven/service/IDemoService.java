package com.reven.service;
import com.github.pagehelper.PageInfo;
import com.reven.core.IBaseService;
import com.reven.entity.Demo;


/**
 * @author reven
 */
public interface IDemoService extends IBaseService<Demo> {

    /**   
     * 模拟下订单，保存至db，并发送至mq
     * @param demo      
     */
    void saveOrder(Demo demo);

	PageInfo<Demo> findAll(Integer page, Integer size);

}
