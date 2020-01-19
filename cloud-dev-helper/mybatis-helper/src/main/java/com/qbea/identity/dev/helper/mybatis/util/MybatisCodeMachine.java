/*
 * CodeMachine.java Created on 2019年3月22日 下午6:13:25
 * Copyright (c) 2019 HeWei Technology Co.Ltd. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.qbea.identity.dev.helper.mybatis.util;

import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 用于生成<table><table/> 字符串，用于全库生成场景,使用前检查GeneratorConfig.properties是否配置正确
 * @author radishT
 */
public class MybatisCodeMachine {
    public static Connection conn = null;
    public static String schema;
    public static String driver;
    public static String url;
    public static String username;
    public static String password;
    static{
        Properties prop = loadProperties();
        schema = prop.getProperty("schema");
        driver = prop.getProperty("jdbc.driverClassName");
        url = prop.getProperty("jdbc.url");
        username = prop.getProperty("jdbc.username");
        password = prop.getProperty("jdbc.password");
        try {
            Class.forName(driver);
            conn = (Connection) DriverManager.getConnection(url, username, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
    public static void main(String[] args) {
        // 生成table 字符串
        generateTableString();
    }
    
    
    /**
     * 根据数据库中的表,生成mybatis generator 中原始的<table></table> 字符串,用于黏贴
     */
    public static void generateTableString(){
        try {
            String sql = "select table_name,table_comment from information_schema.tables where table_schema = '"
                    + schema + "' and table_type = 'base table'";
            PreparedStatement pstmt = (PreparedStatement) conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                String tableName = rs.getString("table_name");
                String tableComment = rs.getString("table_comment");
                System.out.println("<!-- " + tableName + ":(" + tableComment + ") -->");
                System.out.println("<table schema=\"" + schema + "\" tableName=\"" + tableName
                          + "\">");
                System.out.println("    <property name=\"constructorBased\" value=\"true\" />");
                System.out.println("    <property name=\"ignoreQualifiersAtRuntime\" value=\"true\" />");
                System.out.println(
                        "    <generatedKey column=\"BSM\" sqlStatement=\"SELECT LAST_INSERT_ID()\" identity=\"true\" />");
                System.out.println("</table>");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Properties loadProperties(){
        InputStream inputStream = null;
        try {
            inputStream = new ClassPathResource("GeneratorConfig.properties").getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Properties prop = new Properties();
        try {
            prop.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return prop;
    }
}
