package com.reven.web.controller;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.reven.core.web.BaseController;
import com.reven.core.web.ResResult;
import com.reven.entity.Order;
import com.reven.service.IOrderService;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by CodeGenerator on 2019/11/11.
 */
@Slf4j
@RestController
@RequestMapping("/order")
public class OrderController extends BaseController {
    @Resource
    private IOrderService orderService;

    /**
     * 下订单并发送消息
     * 
     * @param order
     * @return
     */
    @GetMapping("/add")
    public ResResult add(Order order) {
        try {
            orderService.saveWithMQ(order);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResResult.fail(e.getMessage());
        }
        return ResResult.success();
    }

    /**
     * 模拟其他子系统消费事务消息
     * 
     * @return
     */
    @GetMapping("/consumerMsg")
    public ResResult consumerMsg() {
        try {
            orderService.consumerMsg();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResResult.fail(e.getMessage());
        }
        return ResResult.success();
    }

    @GetMapping("/add2")
    public ResResult add2(Order order) {
        try {
            orderService.save(order);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResResult.fail(e.getMessage());
        }
        return ResResult.success();
    }

    @PostMapping("/delete")
    public ResResult delete(@RequestParam Integer id) {
        orderService.deleteById(id);
        return ResResult.success();
    }

    @PostMapping("/update")
    public ResResult update(Order order) {
        orderService.update(order);
        return ResResult.success();
    }

    @RequestMapping("/detail")
    public ResResult detail(@RequestParam Integer id) {
        Order order = orderService.findById(id);
        return ResResult.success(order);
    }

    @RequestMapping("/list")
    public ResResult list(@RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "0") Integer size) {
        PageHelper.startPage(page, size);
        List<Order> list = orderService.findAll();
        PageInfo<Order> pageInfo = new PageInfo<Order>(list);
        return ResResult.success(pageInfo);
    }
}
