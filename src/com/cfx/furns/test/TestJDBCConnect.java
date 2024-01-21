package com.cfx.furns.test;

import com.cfx.furns.utils.JdbcUtilsByDruid;
import com.cfx.furns.utils.Logit;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Project: FurnMall
 * Create By: Chen.F.X
 * DateTime: 2024/1/20 13:19
 **/
public class TestJDBCConnect {
    private static final String TAG = "TestJDBCConnect";

    // 测试 JdbcUtilsByDruid 获取连接是否正常
    @Test
    public void testJDBCConnect() throws SQLException {
        Connection connection = JdbcUtilsByDruid.getConnection();
        Logit.d(TAG, "testJDBCConnect connection: " + connection);
    }
}
