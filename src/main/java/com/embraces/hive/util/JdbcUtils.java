package com.embraces.hive.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author Lijl
 * @ClassName JdbcUtils
 * @Description TODO
 * @Date 2020/10/26 15:45
 * @Version 1.0
 */
public class JdbcUtils {

    private static Logger log = LoggerFactory.getLogger(JdbcUtils.class);

    protected Connection conn;
    protected Statement stat;
    protected PreparedStatement ps;
    protected ResultSet rs;

    public Connection getConn(String url,String userName,String password) throws SQLException, ClassNotFoundException {
        Class.forName("org.apache.hive.jdbc.HiveDriver");
        conn = DriverManager.getConnection(url,userName,password);
        return conn;
    }

    public List executeQueryList(String url, String userName, String password, String sql, Class cls, List<Object> params)
            throws Exception {
        log.info("将要的执行SQL：{}",sql);
        conn = getConn(url,userName, password);
        ps = conn.prepareStatement(sql);
        if (params!=null){
            setParams(rs, params);
        }
        rs = ps.executeQuery();
        List list = new ArrayList();
        while (rs.next()) {
            Object data = invoteSetter(cls);
            list.add(data);
        }
        close();
        return list;
    }


    private Object invoteSetter(Class cls) throws InstantiationException, IllegalAccessException, NoSuchMethodException, SecurityException, SQLException, IllegalArgumentException, InvocationTargetException {
        Object obj = cls.newInstance();// 反射创建对象。
        // ① 当前cls所在的类，定义了哪些属性。才能知道setter方法的名字。
        // ② 每个属性是什么类型的。才能知道要调用rs.getInt还是rs.getString ...
        Field[] fs = cls.getDeclaredFields();// 获取cls类的所有定义的属性。
        for (Field f : fs) {
            String fieldname = f.getName();// 属性的名字。
            Class<?> type = f.getType();// 属性的类型的class对象。 int -- int.class
            String setter = "set" + fieldname.substring(0, 1).toUpperCase()
                    + fieldname.substring(1, fieldname.length());
            Method settermethod = cls.getMethod(setter, type);
            if (settermethod == null) {
                continue;// 如果方法不存在则跳过。
            }
            Object value = null;
            if (type == int.class || type == Integer.class) {
                value = rs.getInt(fieldname);
            } else if (type == String.class) {
                value = rs.getString(fieldname);
            } else if (type == Date.class) {
                value = rs.getDate(fieldname);
            }//此处还应该有更多的判断。
            // 反射调用方法。setXXX方法。进行赋值。
            settermethod.invoke(obj, value);
        }
        return obj;
    }


    private void setParams(ResultSet rs, List<Object> params) throws SQLException {
        for (int i = 0; i < params.size(); i++) {
            Object v = params.get(i);
            if(v == null){
                continue;
            }
            if (v instanceof Integer) {
                ps.setInt(i + 1, (int) v);
            } else if (v instanceof String) {
                ps.setString(i + 1, (String) v);
            }
        }
    }

    public void close() {
        close(conn, stat, rs);
    }

    public static void close(Connection conn, Statement stat, ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (stat != null) {
            try {
                stat.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
