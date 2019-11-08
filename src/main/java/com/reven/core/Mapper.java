package com.reven.core;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import tk.mybatis.mapper.common.BaseMapper;
import tk.mybatis.mapper.common.ConditionMapper;
import tk.mybatis.mapper.common.IdsMapper;
import tk.mybatis.mapper.common.special.InsertListMapper;

/**
 * @ClassName:  Mapper   
 * @Description:定制MyBatis Mapper插件接口
 * @author reven
 * @date   2018年8月28日
 * @param <T>
 */
public interface Mapper<T> extends BaseMapper<T>, ConditionMapper<T>, IdsMapper<T>, InsertListMapper<T> {
	List<T> find(@Param("paramMap") Map<String, Object> paramMap);
}
