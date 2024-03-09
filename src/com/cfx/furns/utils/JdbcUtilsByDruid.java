package com.cfx.furns.utils;


import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.sql.*;
import java.util.Properties;

/**
 * Project: Ajax
 * Create By: Chen.F.X
 * DateTime: 2024/1/7 15:13
 * <p>
 * 基于 druid 数据库连接池的工具类
 **/
public class JdbcUtilsByDruid {
    private static final String TAG = "JdbcUtilsByDruid";
    private static DataSource sDataSource;
    private static ThreadLocal<Connection> sThreadLocalConn = new ThreadLocal<>();

    // 在静态代码块中完成 sDataSource 的初始化
    static {
        Properties properties = new Properties();
        try {
            /**
             * java se 中可以这样写 properties.load(new FileInputStream("src\\druid.properties"))
             * 但是在 web 项目中路径不一样了，所有资源都在 out 下面，这样写找不到资源路径
             * 所以需要找到资源的真正路径
             * 方式 1
             */
            // String absolutePath = FilePath.getAbsolutePath("druid.properties");
            // properties.load(new FileInputStream(absolutePath));

            // 方式 2
            properties.load(JdbcUtilsByDruid.class.getClassLoader().getResourceAsStream("druid.properties"));
            sDataSource = DruidDataSourceFactory.createDataSource(properties);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // getConnection 方法
    // public static Connection getConnection() throws SQLException {
    //     return sDataSource.getConnection();
    // }

    /**
     * 一次请求是在一个线程中
     * 通过 ThreadLocal 保证一个线程中的 connection 是同一个对象
     * 这样才能通过事务控制数据的原子性
     *
     * @return
     * @throws SQLException
     */
    public static Connection getConnection() {
        // 1.先从当前线程中获取 connection 对象
        Connection connection = sThreadLocalConn.get();
        if (connection == null) {
            // 2.如果为空，表明还没获取过，那么从数据库连接池中获取一个 connection 对象
            try {
                connection = sDataSource.getConnection();
                // 3.关闭自动提交
                connection.setAutoCommit(false);
                // 4.并将这个 connection 对象放到 sThreadLocalConn 中
                sThreadLocalConn.set(connection);
            } catch (SQLException throwables) {
                Logit.d(TAG, "throwables: " + throwables);
            }
        }
        return connection;
    }

    /**
     * 提交事务
     */
    public static void commit() {
        Connection connection = sThreadLocalConn.get();
        if (connection != null) {
            // 如果当前线程中有 connection 就提交事务
            try {
                connection.commit();
            } catch (SQLException throwables) {
                Logit.d(TAG, "throwables: " + throwables);
            } finally {
                sThreadLocalConn.remove();
                try {
                    connection.close();
                } catch (SQLException throwables) {
                    Logit.d(TAG, "throwables: " + throwables);
                }
            }
        }
    }

    /**
     * 回滚事务
     */
    public static void rollback() {
        Connection connection = sThreadLocalConn.get();
        if (connection != null) {
            try {
                connection.rollback();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } finally {
                sThreadLocalConn.remove();
                try {
                    connection.close();
                } catch (SQLException throwables) {
                    Logit.d(TAG, "throwables: " + throwables);
                }
            }
        }
    }


    /**
     * 关闭连接, 在数据库连接池技术中，close 不是真的断掉连接
     * 而是把使用的 Connection 对象放回连接池
     *
     * @param resultSet
     * @param statement
     * @param connection
     */
    public static void close(ResultSet resultSet, Statement statement, Connection connection) {
        try {
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
