package com.kaishengit.dao;

import com.kaishengit.util.Page;
import com.kaishengit.util.RequestQuery;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * @author 刘帅
 */
public abstract class BaseDao<T,PK extends Serializable> {

    private Class<T> entityClazz;

    public BaseDao() {
        ParameterizedType parameterizedType = (ParameterizedType) getClass().getGenericSuperclass();
        Type[] types = parameterizedType.getActualTypeArguments();
        entityClazz = (Class<T>) types[0];
    }

    @Autowired
    private SessionFactory sessionFactory;

    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    public void save(T entity) {
        getSession().saveOrUpdate(entity);
    }

    public T findById(PK id) {
        return (T) getSession().get(entityClazz,id);
    }

    public void deleteById(PK id) {

        getSession().delete(findById(id));
    }

    public List<T> findAll() {

        Criteria criteria =  getSession().createCriteria(entityClazz);
        return criteria.list();
    }

    public List<T> findByPage(Integer start , Integer size) {
        Criteria criteria = getSession().createCriteria(entityClazz);
        criteria.setFirstResult(start);
        criteria.setMaxResults(size);
        return criteria.list();
    }

    public Long count() {
        Criteria criteria = getSession().createCriteria(entityClazz);
        long count = (long) criteria.setProjection(Projections.rowCount()).uniqueResult();
        return count;
    }

    public Long count(List<RequestQuery> requestQueryList) {

        Criteria criteria = getSession().createCriteria(entityClazz);
        buildRequestQuery(requestQueryList, criteria);
        long count = (long) criteria.setProjection(Projections.rowCount()).uniqueResult();
        return count;
    }


    public Page<T> findByQueryList(int pageNo, List<RequestQuery> requestQueryList) {

        //根据条件计算总页数
        Long count = count(requestQueryList);
        //计算总页数
        Page<T> page = new Page<T>(pageNo,count.intValue());
        Criteria criteria = getSession().createCriteria(entityClazz);
        criteria.addOrder(Order.desc("id"));

        criteria.setFirstResult(page.getStart());
        criteria.setMaxResults(page.getPageSize());

        buildRequestQuery(requestQueryList, criteria);
         List<T> list = criteria.list();
         page.setItems(list);
         return page;
    }


    private Criterion createCriteria(String paramName, String equalType, Object value) {

        if (paramName.contains("_or_")) {
            String[] params = paramName.split("_or_");
            Disjunction disjunction = Restrictions.disjunction();
            for (String name : params) {
                disjunction.add(getCriterion(name, equalType, value));
            }
            return disjunction;
        }else {
            return getCriterion(paramName, equalType, value);
        }
    }


    private Criterion getCriterion(String paramName, String equalType, Object value) {
        if ("eq".equalsIgnoreCase(equalType)) {
            return Restrictions.eq(paramName,value);
        }else if ("like".equalsIgnoreCase(equalType)) {
            return Restrictions.like(paramName,value.toString(), MatchMode.ANYWHERE);
        }
        return null;
    }


    private void buildRequestQuery(List<RequestQuery> requestQueryList, Criteria criteria) {
        for (RequestQuery requestQuery : requestQueryList) {
            String paramName = requestQuery.getParameterName();
            String equalType = requestQuery.getEqualType();
            Object value = requestQuery.getValue();

            criteria.add(createCriteria(paramName,equalType,value));
        }
    }

}
