package com.reven.core;

import java.util.List;
import java.util.Map;

import tk.mybatis.mapper.entity.Condition;

/**
 * @ClassName:  IBaseService   
 * @Description: Service 层 基础接口，其他Service 接口 请继承该接口
 * @author reven
 * @date   2018年8月28日
 * @param <T>
 */
public interface IBaseService<T> {
	void save(T model);// 持久化

	void save(List<T> models);// 批量持久化

	void deleteById(Integer id);// 通过主鍵刪除

	void deleteByIds(String ids);// 批量刪除 eg：ids -> “1,2,3,4”

	void update(T model);// 更新

	T findById(Integer id);// 通过ID查找

	T findBy(String fieldName, Object value) throws Exception; // 通过Model中某个成员变量名称（非数据表中column的名称）查找,value需符合unique约束

	List<T> findByIds(String ids);// 通过多个ID查找//eg：ids -> “1,2,3,4”

	List<T> findByCondition(Condition condition);// 根据条件查找

	List<T> findAll();// 获取所有

	List<T> find(Map<String, Object> paramMap);// 根据条件获取
}
