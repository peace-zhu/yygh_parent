package com.atguigu.easyExcel;

import com.alibaba.excel.EasyExcel;

public class TestRead {
    public static void main(String[] args) {
        String fileName = "D:\\Excel\\01.xlsx";
        EasyExcel.read(fileName, UserData.class,new ExcelListener()).sheet().doRead();
    }
}
