package com.kaishengit.util;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * @author 刘帅
 */
public class RequestQuery {

    /**
     * 要查询的参数名
     */
    private String parameterName;
    /**
     * 通配符
     */
    private String equalType;
    /**
     *要查询的值
     */
    private Object value;

    public String getParameterName() {
        return parameterName;
    }

    public void setParameterName(String parameterName) {
        this.parameterName = parameterName;
    }

    public String getEqualType() {
        return equalType;
    }

    public void setEqualType(String equalType) {
        this.equalType = equalType;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public static List<RequestQuery> getRequestQueryList(HttpServletRequest request) {

        List<RequestQuery> requestQueryList = new ArrayList<>();
        //获得请求的所有Key
        Enumeration<String> enumeration = request.getParameterNames();

        while (enumeration.hasMoreElements()) {
            String key = enumeration.nextElement();
            String value = request.getParameter(key);

            if (key.startsWith("q_") && !"".equals(value) && value != null) {
                //q_eq_bd_price_or_marketPrice
                String[] array = key.split("_",4);

                if (array == null || array.length != 4) {
                    throw new IllegalArgumentException("查询条件异常"+key);
                }

                RequestQuery requestQuery = new RequestQuery();
                requestQuery.setParameterName(array[3]);
                requestQuery.setEqualType(array[1]);
                requestQuery.setValue(editValueType(array[2],value));

                requestQueryList.add(requestQuery);
            }
        }
        return requestQueryList;
    }

    private static Object editValueType(String valueType, String value) {

        if ("s".equalsIgnoreCase(valueType)) {
            return value;
        }else if ("bd".equalsIgnoreCase(valueType)) {
            return new BigDecimal(value);
        }
        return null;
    }

}
