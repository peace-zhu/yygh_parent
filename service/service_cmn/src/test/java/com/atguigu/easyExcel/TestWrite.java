package com.atguigu.easyExcel;

import com.alibaba.excel.EasyExcel;

import java.util.ArrayList;
import java.util.List;

public class TestWrite {
    public static void main(String[] args) {
        //构建数据的集合
        List<UserData> list = new ArrayList<>();
        for(int i=0;i<10;i++){
            UserData data = new UserData();
            data.setUid(i);
            data.setUsername("zyc"+i);
            list.add(data);
        }
        //设置excel文件路径and名称
        String fileName = "D:\\Excel\\01.xlsx";
        EasyExcel.write(fileName, UserData.class).sheet("用户信息")
                .doWrite(list);
    }
}
