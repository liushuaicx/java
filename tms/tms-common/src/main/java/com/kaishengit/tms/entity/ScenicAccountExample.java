package com.kaishengit.tms.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ScenicAccountExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public ScenicAccountExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andScenicAccountIsNull() {
            addCriterion("scenic_account is null");
            return (Criteria) this;
        }

        public Criteria andScenicAccountIsNotNull() {
            addCriterion("scenic_account is not null");
            return (Criteria) this;
        }

        public Criteria andScenicAccountEqualTo(String value) {
            addCriterion("scenic_account =", value, "scenicAccount");
            return (Criteria) this;
        }

        public Criteria andScenicAccountNotEqualTo(String value) {
            addCriterion("scenic_account <>", value, "scenicAccount");
            return (Criteria) this;
        }

        public Criteria andScenicAccountGreaterThan(String value) {
            addCriterion("scenic_account >", value, "scenicAccount");
            return (Criteria) this;
        }

        public Criteria andScenicAccountGreaterThanOrEqualTo(String value) {
            addCriterion("scenic_account >=", value, "scenicAccount");
            return (Criteria) this;
        }

        public Criteria andScenicAccountLessThan(String value) {
            addCriterion("scenic_account <", value, "scenicAccount");
            return (Criteria) this;
        }

        public Criteria andScenicAccountLessThanOrEqualTo(String value) {
            addCriterion("scenic_account <=", value, "scenicAccount");
            return (Criteria) this;
        }

        public Criteria andScenicAccountLike(String value) {
            addCriterion("scenic_account like", value, "scenicAccount");
            return (Criteria) this;
        }

        public Criteria andScenicAccountNotLike(String value) {
            addCriterion("scenic_account not like", value, "scenicAccount");
            return (Criteria) this;
        }

        public Criteria andScenicAccountIn(List<String> values) {
            addCriterion("scenic_account in", values, "scenicAccount");
            return (Criteria) this;
        }

        public Criteria andScenicAccountNotIn(List<String> values) {
            addCriterion("scenic_account not in", values, "scenicAccount");
            return (Criteria) this;
        }

        public Criteria andScenicAccountBetween(String value1, String value2) {
            addCriterion("scenic_account between", value1, value2, "scenicAccount");
            return (Criteria) this;
        }

        public Criteria andScenicAccountNotBetween(String value1, String value2) {
            addCriterion("scenic_account not between", value1, value2, "scenicAccount");
            return (Criteria) this;
        }

        public Criteria andScenicPasswordIsNull() {
            addCriterion("scenic_password is null");
            return (Criteria) this;
        }

        public Criteria andScenicPasswordIsNotNull() {
            addCriterion("scenic_password is not null");
            return (Criteria) this;
        }

        public Criteria andScenicPasswordEqualTo(String value) {
            addCriterion("scenic_password =", value, "scenicPassword");
            return (Criteria) this;
        }

        public Criteria andScenicPasswordNotEqualTo(String value) {
            addCriterion("scenic_password <>", value, "scenicPassword");
            return (Criteria) this;
        }

        public Criteria andScenicPasswordGreaterThan(String value) {
            addCriterion("scenic_password >", value, "scenicPassword");
            return (Criteria) this;
        }

        public Criteria andScenicPasswordGreaterThanOrEqualTo(String value) {
            addCriterion("scenic_password >=", value, "scenicPassword");
            return (Criteria) this;
        }

        public Criteria andScenicPasswordLessThan(String value) {
            addCriterion("scenic_password <", value, "scenicPassword");
            return (Criteria) this;
        }

        public Criteria andScenicPasswordLessThanOrEqualTo(String value) {
            addCriterion("scenic_password <=", value, "scenicPassword");
            return (Criteria) this;
        }

        public Criteria andScenicPasswordLike(String value) {
            addCriterion("scenic_password like", value, "scenicPassword");
            return (Criteria) this;
        }

        public Criteria andScenicPasswordNotLike(String value) {
            addCriterion("scenic_password not like", value, "scenicPassword");
            return (Criteria) this;
        }

        public Criteria andScenicPasswordIn(List<String> values) {
            addCriterion("scenic_password in", values, "scenicPassword");
            return (Criteria) this;
        }

        public Criteria andScenicPasswordNotIn(List<String> values) {
            addCriterion("scenic_password not in", values, "scenicPassword");
            return (Criteria) this;
        }

        public Criteria andScenicPasswordBetween(String value1, String value2) {
            addCriterion("scenic_password between", value1, value2, "scenicPassword");
            return (Criteria) this;
        }

        public Criteria andScenicPasswordNotBetween(String value1, String value2) {
            addCriterion("scenic_password not between", value1, value2, "scenicPassword");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNull() {
            addCriterion("create_time is null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNotNull() {
            addCriterion("create_time is not null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeEqualTo(Date value) {
            addCriterion("create_time =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(Date value) {
            addCriterion("create_time <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(Date value) {
            addCriterion("create_time >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("create_time >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(Date value) {
            addCriterion("create_time <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(Date value) {
            addCriterion("create_time <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<Date> values) {
            addCriterion("create_time in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(List<Date> values) {
            addCriterion("create_time not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(Date value1, Date value2) {
            addCriterion("create_time between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(Date value1, Date value2) {
            addCriterion("create_time not between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNull() {
            addCriterion("update_time is null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNotNull() {
            addCriterion("update_time is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeEqualTo(Date value) {
            addCriterion("update_time =", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotEqualTo(Date value) {
            addCriterion("update_time <>", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThan(Date value) {
            addCriterion("update_time >", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("update_time >=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThan(Date value) {
            addCriterion("update_time <", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThanOrEqualTo(Date value) {
            addCriterion("update_time <=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIn(List<Date> values) {
            addCriterion("update_time in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotIn(List<Date> values) {
            addCriterion("update_time not in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeBetween(Date value1, Date value2) {
            addCriterion("update_time between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotBetween(Date value1, Date value2) {
            addCriterion("update_time not between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andScenicStateIsNull() {
            addCriterion("scenic_state is null");
            return (Criteria) this;
        }

        public Criteria andScenicStateIsNotNull() {
            addCriterion("scenic_state is not null");
            return (Criteria) this;
        }

        public Criteria andScenicStateEqualTo(String value) {
            addCriterion("scenic_state =", value, "scenicState");
            return (Criteria) this;
        }

        public Criteria andScenicStateNotEqualTo(String value) {
            addCriterion("scenic_state <>", value, "scenicState");
            return (Criteria) this;
        }

        public Criteria andScenicStateGreaterThan(String value) {
            addCriterion("scenic_state >", value, "scenicState");
            return (Criteria) this;
        }

        public Criteria andScenicStateGreaterThanOrEqualTo(String value) {
            addCriterion("scenic_state >=", value, "scenicState");
            return (Criteria) this;
        }

        public Criteria andScenicStateLessThan(String value) {
            addCriterion("scenic_state <", value, "scenicState");
            return (Criteria) this;
        }

        public Criteria andScenicStateLessThanOrEqualTo(String value) {
            addCriterion("scenic_state <=", value, "scenicState");
            return (Criteria) this;
        }

        public Criteria andScenicStateLike(String value) {
            addCriterion("scenic_state like", value, "scenicState");
            return (Criteria) this;
        }

        public Criteria andScenicStateNotLike(String value) {
            addCriterion("scenic_state not like", value, "scenicState");
            return (Criteria) this;
        }

        public Criteria andScenicStateIn(List<String> values) {
            addCriterion("scenic_state in", values, "scenicState");
            return (Criteria) this;
        }

        public Criteria andScenicStateNotIn(List<String> values) {
            addCriterion("scenic_state not in", values, "scenicState");
            return (Criteria) this;
        }

        public Criteria andScenicStateBetween(String value1, String value2) {
            addCriterion("scenic_state between", value1, value2, "scenicState");
            return (Criteria) this;
        }

        public Criteria andScenicStateNotBetween(String value1, String value2) {
            addCriterion("scenic_state not between", value1, value2, "scenicState");
            return (Criteria) this;
        }

        public Criteria andScenicIdIsNull() {
            addCriterion("scenic_id is null");
            return (Criteria) this;
        }

        public Criteria andScenicIdIsNotNull() {
            addCriterion("scenic_id is not null");
            return (Criteria) this;
        }

        public Criteria andScenicIdEqualTo(Integer value) {
            addCriterion("scenic_id =", value, "scenicId");
            return (Criteria) this;
        }

        public Criteria andScenicIdNotEqualTo(Integer value) {
            addCriterion("scenic_id <>", value, "scenicId");
            return (Criteria) this;
        }

        public Criteria andScenicIdGreaterThan(Integer value) {
            addCriterion("scenic_id >", value, "scenicId");
            return (Criteria) this;
        }

        public Criteria andScenicIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("scenic_id >=", value, "scenicId");
            return (Criteria) this;
        }

        public Criteria andScenicIdLessThan(Integer value) {
            addCriterion("scenic_id <", value, "scenicId");
            return (Criteria) this;
        }

        public Criteria andScenicIdLessThanOrEqualTo(Integer value) {
            addCriterion("scenic_id <=", value, "scenicId");
            return (Criteria) this;
        }

        public Criteria andScenicIdIn(List<Integer> values) {
            addCriterion("scenic_id in", values, "scenicId");
            return (Criteria) this;
        }

        public Criteria andScenicIdNotIn(List<Integer> values) {
            addCriterion("scenic_id not in", values, "scenicId");
            return (Criteria) this;
        }

        public Criteria andScenicIdBetween(Integer value1, Integer value2) {
            addCriterion("scenic_id between", value1, value2, "scenicId");
            return (Criteria) this;
        }

        public Criteria andScenicIdNotBetween(Integer value1, Integer value2) {
            addCriterion("scenic_id not between", value1, value2, "scenicId");
            return (Criteria) this;
        }
    }

    /**
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}