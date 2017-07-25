package com.shensheng.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/7/24.
 */
public class MapperUtil {
    private final String TYPE_CHAR = "char";

    private final String TYPE_VARCHAR = "varchar";

    private final String TYPE_DATE = "date";

    private final String TYPE_TIMESTAMP = "timestamp";

    private final String TYPE_INT = "int";

    private final String TYPE_TINYINT = "tinyint";

    private final String TYPE_SMALLINT = "smallint";

    private final String TYPE_BIGINT = "bigint";

    private final String TYPE_TEXT = "text";

    private final String TYPE_BIT = "bit";

    private final String TYPE_DECIMAL = "decimal";

    private final String TYPE_BLOB = "blob";

    private final String BATH_PATH = "D:/test1/src/main/";

    private final String BEAN_PATH = BATH_PATH + getPath(this.BEAN_PACKAGE);

    private final String MAPPER_PATH = BATH_PATH + getPath(this.MAPPER_PACKAGE);

    private final String XML_PATH = BATH_PATH + getPath(this.XML_PACKAGE);

    private final String BASE_PACKAGE = "com.shensheng.";

    private final String BEAN_PACKAGE = BASE_PACKAGE + "persistence.beans";

    private final String MAPPER_PACKAGE = BASE_PACKAGE + "persistence.mapper";

    private final String XML_PACKAGE = "resources.mapper";

    private final String DRIVER_NAME = "com.mysql.jdbc.Driver";

    private final String USER = "root";

    private final String PASSWORD = "root";

    private final String URL = "jdbc:mysql://localhost:3306/test?characterEncoding=utf8";

    private String tableName = null;

    private String beanName = null;

    private String mapperName = null;

    private Connection conn = null;


    private void init() throws ClassNotFoundException, SQLException {
        Class.forName(DRIVER_NAME);
        conn = DriverManager.getConnection(URL, USER, PASSWORD);
    }

    /**
     *  获取所有的表
     *
     * @return
     * @throws SQLException
     */
    private List<String> getTables() throws SQLException {
        List<String> tables = new ArrayList<>();
        PreparedStatement pstate = conn.prepareStatement("show tables");
        ResultSet results = pstate.executeQuery();
        while (results.next()) {
            String tableName = results.getString(1);
            tables.add(tableName);
        }
        return tables;
    }

    private static String getPath(String path) {
        if (path != null && !path.equals("")) {
            path = path.replaceAll("\\.", "/");
        }
        return path;
    }

    private void processTable(String table) {
        StringBuffer sb = new StringBuffer(table.length());
        String tableNew = table.toLowerCase();
        String[] tables = tableNew.split("_");
        String temp = null;
        for (int i = 0; i < tables.length; i++) {
            temp = tables[i].trim();
            sb.append(temp.substring(0, 1).toUpperCase()).append(temp.substring(1));
        }
        beanName = sb.toString();
        mapperName = beanName + "Mapper";
    }


    private String processType(String type) {
        if (type.indexOf(TYPE_CHAR) > -1) {
            return "String";
        } else if (type.indexOf(TYPE_VARCHAR) > -1) {
            return "String";
        } else if (type.indexOf(TYPE_BIGINT) > -1) {
            return "Long";
        } else if (type.indexOf(TYPE_INT) > -1) {
            return "Integer";
        } else if (type.indexOf(TYPE_TINYINT) > -1) {
            return "Integer";
        } else if (type.indexOf(TYPE_SMALLINT) > -1) {
            return "Integer";
        } else if (type.indexOf(TYPE_DATE) > -1) {
            return "Date";
        } else if (type.indexOf(TYPE_TEXT) > -1) {
            return "String";
        } else if (type.indexOf(TYPE_TIMESTAMP) > -1) {
            return "Date";
        } else if (type.indexOf(TYPE_BIT) > -1) {
            return "Boolean";
        } else if (type.indexOf(TYPE_DECIMAL) > -1) {
            return "BigDecimal";
        } else if (type.indexOf(TYPE_BLOB) > -1) {
            return "byte[]";
        }
        return null;
    }

    private String processJdbcType(String type) {
        if (type.indexOf(TYPE_CHAR) > -1) {
            return "VARCHAR";
        } else if (type.indexOf(TYPE_VARCHAR) > -1) {
            return "VARCHAR";
        } else if (type.indexOf(TYPE_BIGINT) > -1) {
            return "BIGINT";
        } else if (type.indexOf(TYPE_INT) > -1) {
            return "INTEGER";
        } else if (type.indexOf(TYPE_TINYINT) > -1) {
            return "INTEGER";
        } else if (type.indexOf(TYPE_SMALLINT) > -1) {
            return "INTEGER";
        } else if (type.indexOf(TYPE_DATE) > -1) {
            return "TIMESTAMP";
        } else if (type.indexOf(TYPE_TEXT) > -1) {
            return "VARCHAR";
        } else if (type.indexOf(TYPE_TIMESTAMP) > -1) {
            return "TIMESTAMP";
        } else if (type.indexOf(TYPE_BIT) > -1) {
            return "INTEGER";
        } else if (type.indexOf(TYPE_DECIMAL) > -1) {
            return "DECIMAL";
        } else if (type.indexOf(TYPE_BLOB) > -1) {
            return "BLOB";
        }
        return null;
    }

    private boolean isType(List<String> types, String type) {
        if (type != null) {
            for (int i = 0; i < types.size(); i++) {
                if (type.equals(types.get(i))) {
                    return true;
                }
            }
        }
        return false;
    }

    private String processField(String field) {
        StringBuffer sb = new StringBuffer(field.length());
        field = field.toLowerCase();
        String[] fields = field.split("_");
        String temp = null;
        sb.append(fields[0]);
        for (int i = 1; i < fields.length; i++) {
            temp = fields[i].trim();
            sb.append(temp.substring(0, 1).toUpperCase()).append(temp.substring(1));
        }
        return sb.toString();
    }


    /**
     *  将实体类名首字母改为小写
     *
     * @param beanName
     * @return
     */
    private String processResultMapId(String beanName) {
        return beanName.substring(0, 1).toLowerCase() + beanName.substring(1);
    }


    /**
     *  构建类上面的注释
     *
     * @param bw
     * @param text
     * @return
     * @throws IOException
     */
    private BufferedWriter buildClassComment(BufferedWriter bw, String text) throws IOException {
        bw.newLine();
        bw.newLine();
        bw.write("/**");
        bw.newLine();
        bw.write(" * " + text);
        bw.newLine();
        bw.write(" */");
        return bw;
    }

    /**
     *  生成实体类
     *
     * @param columns
     * @param types
     * @param comments
     * @throws IOException
     */
    private void buildEntityBean(List<String> columns, List<String> types, List<String> comments, String tableComment)
            throws IOException {
        File folder = new File(BEAN_PATH);
        if (!folder.exists()) {
            folder.mkdirs();
        }

        File beanFile = new File(BEAN_PATH, beanName + ".java");
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(beanFile)));
        bw.write("package " + BEAN_PACKAGE + ";");
        bw.newLine();
        bw.newLine();
        bw.write("import java.io.Serializable;");
        if (isType(types, TYPE_DATE) || isType(types, TYPE_TIMESTAMP)) {
            bw.newLine();
            bw.write("import java.util.Date;");
        }
        if (isType(types, TYPE_DECIMAL)) {
            bw.newLine();
            bw.write("import java.math.BigDecimal;");
        }
        bw = buildClassComment(bw, tableComment);
        bw.newLine();
        bw.write("public class " + beanName + " implements Serializable {");
        bw.newLine();
        int size = columns.size();
        for (int i = 0; i < size; i++) {
            bw.write("    /**");
            bw.newLine();
            bw.write("     * " + comments.get(i));
            bw.newLine();
            bw.write("     */");
            bw.newLine();
            bw.write("    private " + processType(types.get(i)) + " " + processField(columns.get(i)) + ";");
            bw.newLine();
            bw.newLine();
        }
        // 生成get 和 set方法
        String tempField = null;
        String _tempField = null;
        String tempType = null;
        for (int i = 0; i < size; i++) {
            tempType = processType(types.get(i));
            _tempField = processField(columns.get(i));
            tempField = _tempField.substring(0, 1).toUpperCase() + _tempField.substring(1);
            bw.write("    public void set" + tempField + "(" + tempType + " " + _tempField + ") {");
            bw.newLine();
            bw.write("        this." + _tempField + " = " + _tempField + ";");
            bw.newLine();
            bw.write("    }");
            bw.newLine();
            bw.newLine();
            bw.write("    public " + tempType + " get" + tempField + "() {");
            bw.newLine();
            bw.write("        return this." + _tempField + ";");
            bw.newLine();
            bw.write("    }");
            bw.newLine();
            bw.newLine();
        }
        bw.write("}");
        bw.newLine();
        bw.flush();
        bw.close();
    }


    /**
     *  构建Mapper文件
     *
     * @throws IOException
     */
    private void buildMapper() throws IOException {
        File folder = new File(MAPPER_PATH);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        File mapperFile = new File(MAPPER_PATH, mapperName + ".java");
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(mapperFile), "utf-8"));
        bw.write("package " + MAPPER_PACKAGE + ";");
        bw.newLine();
        bw.newLine();
        bw.write("import " + BEAN_PACKAGE + "." + beanName + ";");
        bw.newLine();
        bw = buildClassComment(bw, mapperName + "数据库操作接口类");
        bw.newLine();
        bw.write("public interface " + mapperName + " {");
        bw.newLine();
        bw.newLine();
        bw.write("}");
        bw.flush();
        bw.close();
    }


    /**
     *  构建实体类映射XML文件
     *
     * @param columns
     * @param types
     * @param comments
     * @throws IOException
     */
    private void buildMapperXml(List<String> columns, List<String> types, List<String> comments) throws IOException {
        File folder = new File(XML_PATH);
        if (!folder.exists()) {
            folder.mkdirs();
        }

        File mapperXmlFile = new File(XML_PATH, mapperName + ".xml");
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(mapperXmlFile)));
        bw.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        bw.newLine();
        bw.write("<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\">");
        bw.newLine();
        bw.write("<mapper namespace=\"" + MAPPER_PACKAGE + "." + mapperName + "\">");
        bw.newLine();
        bw.newLine();

        bw.write("    <!--实体映射-->");
        bw.newLine();
        bw.write("    <resultMap id=\"rm\" type=\"" + BEAN_PACKAGE + "." + beanName + "\">");
        bw.newLine();
        int size = columns.size();
        for (int i = 0; i < size; i++) {
            bw.write("        <result property=\"" + this.processField(columns.get(i)) + "\" column=\"" + columns.get(i) + "\" jdbcType=\"" + processJdbcType(types.get(i)) + "\" />");
            bw.newLine();
        }
        bw.write("    </resultMap>");

        bw.newLine();
        bw.newLine();

        // 下面开始写SqlMapper中的方法
        buildSQL(bw, columns, types);

        bw.write("</mapper>");
        bw.flush();
        bw.close();
    }


    private void buildSQL(BufferedWriter bw, List<String> columns, List<String> types) throws IOException {
        int size = columns.size();
        // 通用结果列
        bw.write("    <!-- 通用查询结果列-->");
        bw.newLine();
        bw.write("    <sql id=\"Base_Column_List\">");
        bw.newLine();

        bw.write("         ");
        for (int i = 0; i < size; i++) {
            bw.write(" " + columns.get(i));
            if (i != size - 1) {
                bw.write(",");
            }
        }

        bw.newLine();
        bw.write("    </sql>");
        bw.newLine();
        bw.newLine();


        // 查询(根据主键ID查询(表的第一个字段))
        bw.write("    <!-- 查询（根据主键ID查询） -->");
        bw.newLine();
        bw.write("    <select id=\"selectByPrimaryKey\" resultType=\""
                + processResultMapId(beanName) + "\" parameterType=\"java.lang." + processType(types.get(0)) + "\">");
        bw.newLine();
        bw.write("         SELECT");
        bw.newLine();
        bw.write("         <include refid=\"Base_Column_List\" />");
        bw.newLine();
        bw.write("         FROM " + tableName);
        bw.newLine();
        bw.write("         WHERE " + columns.get(0) + " = #{" + processField(columns.get(0)) + "}");
        bw.newLine();
        bw.write("    </select>");
        bw.newLine();
        bw.newLine();
        // 查询完


        // 删除(根据主键ID删除(表的第一个字段))
        bw.write("    <!--删除：根据主键ID删除-->");
        bw.newLine();
        bw.write("    <delete id=\"deleteByPrimaryKey\" parameterType=\"java.lang." + processType(types.get(0)) + "\">");
        bw.newLine();
        bw.write("         DELETE FROM " + tableName);
        bw.newLine();
        bw.write("         WHERE " + columns.get(0) + " = #{" + processField(columns.get(0)) + "}");
        bw.newLine();
        bw.write("    </delete>");
        bw.newLine();
        bw.newLine();
        // 删除完


        // 添加insert方法
/*        bw.write("    <!-- 添加 -->");
        bw.newLine();
        bw.write("    <insert id=\"insert\" parameterType=\"" + processResultMapId(beanName) + "\">");
        bw.newLine();
        bw.write("         INSERT INTO " + tableName);
        bw.newLine();
        bw.write("         (");
        for (int i = 0; i < size; i++) {
            bw.write(columns.get(i));
            if (i != size - 1) {
                bw.write(",");
            }
        }
        bw.write(") ");
        bw.newLine();
        bw.write("         VALUES ");
        bw.newLine();
        bw.write("         (");
        for (int i = 0; i < size; i++) {
            bw.write("#{" + processField(columns.get(i)) + "}");
            if (i != size - 1) {
                bw.write(",");
            }
        }
        bw.write(") ");
        bw.newLine();
        bw.write("    </insert>");
        bw.newLine();
        bw.newLine();*/
        // 添加insert完


        //---------------  insert方法（匹配有值的字段）
        bw.write("    <!-- 添加 （匹配有值的字段）-->");
        bw.newLine();
        bw.write("    <insert id=\"insertSelective\" parameterType=\"" + processResultMapId(beanName) + "\">");
        bw.newLine();
        bw.write("         INSERT INTO " + tableName);
        bw.newLine();
        bw.write("         <trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\" >");
        bw.newLine();

        String tempField = null;
        for (int i = 0; i < size; i++ ) {
            tempField = processField(columns.get(i));
            if ("String".equals(processType(types.get(i)))) {
                bw.write("            <if test=\"" + tempField + " != null and " + tempField + " != ''\">");
            } else {
                bw.write("            <if test=\"" + tempField + " != null\">");
            }
            bw.newLine();
            bw.write("                 " + columns.get(i) + ",");
            bw.newLine();
            bw.write("            </if>");
            bw.newLine();
        }

        bw.write("         </trim>");
        bw.newLine();

        bw.write("         <trim prefix=\"values (\" suffix=\")\" suffixOverrides=\",\" >");
        bw.newLine();

        for (int i = 0; i < size; i++ ) {
            tempField = processField(columns.get(i));
            bw.write("            <if test=\"" + tempField + "!=null\">");
            bw.newLine();
            bw.write("                 #{" + tempField + "},");
            bw.newLine();
            bw.write("            </if>");
            bw.newLine();
        }

        bw.write("         </trim>");
        bw.newLine();
        bw.write("    </insert>");
        bw.newLine();
        bw.newLine();
        //---------------  完毕


        // 修改update方法（匹配有值的字段）
        bw.write("    <!-- 修 改-->");
        bw.newLine();
        bw.write("    <update id=\"updateByPrimaryKeySelective\" parameterType=\"" + processResultMapId(beanName) + "\">");
        bw.newLine();
        bw.write("         UPDATE " + tableName);
        bw.newLine();
        bw.write("          <set> ");
        bw.newLine();

        for (int i = 1; i < size; i++) {
            tempField = processField(columns.get(i));
            if ("String".equals(processType(types.get(i)))) {
                bw.write("            <if test=\"" + tempField + " != null and " + tempField + " != ''\">");
            } else {
                bw.write("            <if test=\"" + tempField + " != null\">");
            }
//            bw.write("            <if test=\"" + tempField + " != null\">");
            bw.newLine();
            bw.write("                 " + columns.get(i) + " = #{" + tempField + "},");
            bw.newLine();
            bw.write("            </if>");
            bw.newLine();
        }

        bw.write("          </set>");
        bw.newLine();
        bw.write("         WHERE " + columns.get(0) + " = #{" + processField(columns.get(0)) + "}");
        bw.newLine();
        bw.write("    </update>");
        bw.newLine();
        bw.newLine();
        // update方法完毕

        // ----- 修改
/*        bw.write("    <!-- 修 改-->");
        bw.newLine();
        bw.write("    <update id=\"updateByPrimaryKey\" parameterType=\"" + processResultMapId(beanName) + "\">");
        bw.newLine();
        bw.write("         UPDATE " + tableName);
        bw.newLine();
        bw.write("         SET ");

        bw.newLine();
        for (int i = 1; i < size; i++) {
            tempField = processField(columns.get(i));
            bw.write("             " + columns.get(i) + " = #{" + tempField + "}");
            if (i != size - 1) {
                bw.write(",");
            }
            bw.newLine();
        }

        bw.write("         WHERE " + columns.get(0) + " = #{" + processField(columns.get(0)) + "}");
        bw.newLine();
        bw.write("    </update>");
        bw.newLine();
        bw.newLine();*/
    }


    /**
     *  获取所有的数据库表注释
     *
     * @return
     * @throws SQLException
     */
    private Map<String, String> getTableComment() throws SQLException {
        Map<String, String> maps = new HashMap<>();
        PreparedStatement pstate = conn.prepareStatement("show table status");
        ResultSet results = pstate.executeQuery();
        while (results.next()) {
            String tableName = results.getString("NAME");
            String comment = results.getString("COMMENT");
            maps.put(tableName, comment);
        }
        return maps;
    }


    public void generate() throws ClassNotFoundException, SQLException, IOException {
        init();
        String prefix = "show full fields from ";
        List<String> columns = null;
        List<String> types = null;
        List<String> comments = null;
        PreparedStatement pstate = null;
        List<String> tables = getTables();
        Map<String, String> tableComments = getTableComment();
        for (String table : tables) {
            columns = new ArrayList<>();
            types = new ArrayList<>();
            comments = new ArrayList<>();
            pstate = conn.prepareStatement(prefix + table);
            ResultSet results = pstate.executeQuery();
            while (results.next()) {
                columns.add(results.getString("FIELD"));
                types.add(results.getString("TYPE"));
                comments.add(results.getString("COMMENT"));
            }
            tableName = table;
            processTable(table);
            String tableComment = tableComments.get(tableName);
            buildEntityBean(columns, types, comments, tableComment);
            buildMapper();
            buildMapperXml(columns, types, comments);
        }
        conn.close();
    }


    public static void main(String[] args) {
        try {
            new MapperUtil().generate();
            // 自动打开生成文件的目录
//            Runtime.getRuntime().exec("cmd /c start explorer D:\\test1\\src\\main");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

