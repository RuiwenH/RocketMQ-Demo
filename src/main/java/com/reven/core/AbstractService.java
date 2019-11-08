package com.reven.core;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import tk.mybatis.mapper.entity.Condition;

/**
 * @ClassName:  AbstractService   
 * @Description:基于通用MyBatis Mapper插件的Service接口的实现
 * @author reven
 * @date   2018年8月28日
 * @param <T>
 */
public abstract class AbstractService<T> implements IBaseService<T> {

	@Autowired
	protected Mapper<T> mapper;

	private Class<T> modelClass; 

	@SuppressWarnings("unchecked")
	public AbstractService() {
		ParameterizedType pt = (ParameterizedType) this.getClass().getGenericSuperclass();
		modelClass = (Class<T>) pt.getActualTypeArguments()[0];
	}

	@Override
	public void save(T model) {
		mapper.insertSelective(model);
	}

	@Override
	public void save(List<T> models) {
		mapper.insertList(models);
	}

	@Override
	public void deleteById(Integer id) {
		mapper.deleteByPrimaryKey(id);
	}

	@Override
	public void deleteByIds(String ids) {
		mapper.deleteByIds(ids);
	}

	@Override
	public void update(T model) {
		mapper.updateByPrimaryKeySelective(model);
	}

	@Override
	public T findById(Integer id) {
		return mapper.selectByPrimaryKey(id);
	}

	@Override
	public T findBy(String fieldName, Object value) throws Exception {
		try {
			T model = modelClass.newInstance();
			Field field = modelClass.getDeclaredField(fieldName);
			field.setAccessible(true);
			field.set(model, value);
			return mapper.selectOne(model);
		} catch (ReflectiveOperationException e) {
			throw new Exception(e.getMessage(), e);
		}
	}

	@Override
	public List<T> findByIds(String ids) {
		return mapper.selectByIds(ids);
	}

	@Override
	public List<T> findByCondition(Condition condition) {
		return mapper.selectByCondition(condition);
	}

	@Override
	public List<T> findAll() {
		return mapper.selectAll();
	}

	@Override
	public List<T> find(Map<String, Object> paramMap) {
		return mapper.find(paramMap);
	}
}
