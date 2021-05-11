package com.embraces.hive.test;

import com.embraces.hive.util.HBaseServiceUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author Lijl
 * @ClassName HBaseUtilsTest
 * @Description TODO
 * @Date 2020/11/26 14:58
 * @Version 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class HBaseUtilsTest {

    @Autowired
    private HBaseServiceUtil hBaseServiceUtil;

    @Test
    public void initTable(){
        Map<String, List<String>> tableMap = new HashMap<>();
        tableMap.put("david_topic:actionFlow", Arrays.asList("info","logs"));
        tableMap.put("david_topic:recording",Arrays.asList("info","logs"));
        tableMap.put("david_topic:syncLog",Arrays.asList("info","logs"));
        tableMap.put("david_topic:uploadVersion",Arrays.asList("info","logs"));
        hBaseServiceUtil.createManyTable(tableMap);
    }

    @Test
    public void createOneTable(){
        //在指定命名空间创建表
        hBaseServiceUtil.createOneTable("david_topic:topictest","info","log");
    }
    @Test
    public void deleteTable(){
        hBaseServiceUtil.deleteTable("david_topic:topictest");
    }
    @Test
    public void insertManyColumnRecords(){
        String tableName = "david_topic:topictest";
        String rowNumber  = "105155";
        String columnFamily = "info";
        List<String> columns = Arrays.asList("id", "title", "poform", "dept", "level", "createUser");
        List<String> values = Arrays.asList("105155", "瑞风S4品牌临时页面", "Mobile", "营销中心", "普通", "王丽");
        hBaseServiceUtil.insertManyColumnRecords(tableName, rowNumber, columnFamily, columns, values);
    }
    @Test
    public  void selectTableByRowNumberAndColumnFamily(){
        String tableName = "david_topic:topictest";
        String rowNumber  = "105155";
        String columnFamily = "info";
        hBaseServiceUtil.selectTableByRowNumberAndColumnFamily(tableName,rowNumber,columnFamily);
    }

    @Test
    public void getAllTableNames(){
        hBaseServiceUtil.getAllTableNames();
    }

    @Test
    public void insertOneColumnRecords(){
        String tableName = "david_topic:topictest";
        String rowNumber  = "105155";
        String columnFamily = "info";
        hBaseServiceUtil.insertOneColumnRecords(tableName, rowNumber, columnFamily, "delete", "否");
    }
    @Test
    public void deleteDataBycolumn(){
        String tableName = "david_topic:topictest";
        String rowNumber  = "105155";
        String columnFamily = "info";
        String column = "delete";
        hBaseServiceUtil.deleteDataByColumn(tableName,rowNumber,columnFamily,column);
    }


    @Test
    public void deleteDataByRowNumber(){
        String tableName = "david_topic:topictest";
        String rowNumber  = "105155";
        hBaseServiceUtil.deleteDataByRowNumber(tableName, rowNumber);
    }


    @Test
    public void selectTableAllDataMap(){
        String tableName = "david_topic:topictest";
        hBaseServiceUtil.selectTableAllDataMap(tableName);
    }

    @Test
    public void selectTableAllDataMapColumnFamily(){
        String tableName = "david_topic:topictest";
        String columnFamily = "log";
        hBaseServiceUtil.selectTableAllDataMap(tableName,columnFamily);
    }

    @Test
    public void selectOneRowDataMap(){
        String tableName = "david_topic:topictest";
        String rowNumber  = "105155";
        hBaseServiceUtil.selectOneRowDataMap(tableName,rowNumber);
    }

    @Test
    public void selectColumnValue(){
        String tableName = "david_topic:topictest";
        String rowNumber  = "105155";
        String columnFamily = "info";
        String column = "title";
        hBaseServiceUtil.selectColumnValue(tableName,rowNumber,columnFamily,column);
    }


    @Test
    public void deleteDataByRowNumberAndColumnFamily(){
        String tableName = "david_topic:topictest";
        String columnFamily = "info";
        hBaseServiceUtil.deleteDataByColumnFamily(tableName,columnFamily);
    }

    @Test
    public void selectTableDataByFilter(){
        String tableName = "david_topic:topictest";
        List<String> queryParam = Arrays.asList("id,105155","dept,营销中心");
        //hBaseServiceUtil.selectTableDataByFilter(tableName, "info", queryParam, ",",true);
    }

    @Test
    public void selectColumnValueDataByFilter(){
        String tableName = "david_topic:topictest";
        List<String> queryParam = Arrays.asList("topicFileId,6282");
        hBaseServiceUtil.selectColumnValueDataByFilter(tableName, "info", queryParam, ",","id",true);
    }

}