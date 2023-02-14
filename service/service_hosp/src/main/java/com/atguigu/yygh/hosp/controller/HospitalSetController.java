package com.atguigu.yygh.hosp.controller;

import com.alibaba.excel.util.StringUtils;
import com.atgui.yygh.common.exception.YyghException;
import com.atgui.yygh.common.result.Result;
import com.atguigu.yygh.common.utils.MD5;
import com.atguigu.yygh.hosp.service.HospitalSetService;
import com.atguigu.yygh.model.hosp.HospitalSet;
import com.atguigu.yygh.vo.hosp.HospitalSetQueryVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import javafx.geometry.Pos;
import lombok.Getter;
import org.apache.xmlbeans.impl.xb.xsdschema.Public;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Random;

@CrossOrigin
@RestController
@RequestMapping("/admin/hosp/hospitalSet")
public class HospitalSetController {

    @Autowired
    private HospitalSetService hospitalSetService;

    @GetMapping("/findall")
    public Result findAllSet(){
        List<HospitalSet> list = hospitalSetService.list();
        return Result.ok(list);
    }
    @DeleteMapping("{id}")
    public Result removeHospSet(@PathVariable long id){
//        int a=1/0;
        boolean b = hospitalSetService.removeById(id);
        if (b) {
            return Result.ok();
        }else{
            return Result.fail();
        }
    }
    @PostMapping("findPageHospSet/{cur}/{limit}")
    public Result findPageHospSet(@PathVariable long cur,
                                  @PathVariable long limit,
                                  @RequestBody(required=false) HospitalSetQueryVo hospitalSetQueryVo){
        Page<HospitalSet> page = new Page<>(cur,limit);
        QueryWrapper<HospitalSet> queryWrapper = new QueryWrapper<>();
        if(hospitalSetQueryVo!=null){
            String hosname = hospitalSetQueryVo.getHosname();//医院名称
            String hoscode = hospitalSetQueryVo.getHoscode();//医院编号
            if(!StringUtils.isEmpty(hosname)){
                queryWrapper.like("hosname",hospitalSetQueryVo.getHosname());
            }
            if(!StringUtils.isEmpty(hoscode)){
                queryWrapper.eq("hoscode",hospitalSetQueryVo.getHoscode());
            }
        }

        Page<HospitalSet> page1 = hospitalSetService.page(page, queryWrapper);
        return Result.ok(page1);
    }
    //添加医院
    @PostMapping("saveHospitalSet")
    public Result save(@RequestBody HospitalSet hospitalSet){
        //设置状态
        hospitalSet.setStatus(1);
        //设置密钥
        Random random = new Random();
        hospitalSet.setSignKey(MD5.encrypt(System.currentTimeMillis()+""+random.nextInt(1000)));
        boolean save = hospitalSetService.save(hospitalSet);
        if(save){
            return Result.ok();
        }else{
            return Result.fail();
        }
    }
    //根据id获取信息
    @GetMapping("getHospSet/{id}")
    public Result getHospSet(@PathVariable long id){
        try{
//            int a = 1/0;
        }catch (Exception e){
            throw new YyghException("失败",201);
        }
        HospitalSet hospitalSet = hospitalSetService.getById(id);
        return Result.ok(hospitalSet);
    }
    //修改医院操作
    @PostMapping("updateHospitalSet")
    public Result updateHospitalSet(@RequestBody HospitalSet hospitalSet){
        boolean b = hospitalSetService.updateById(hospitalSet);
        if(b){
            return Result.ok();
        }else {
            return Result.fail();
        }
    }
    @DeleteMapping("banchRemove")
    public Result banchRemove(@RequestBody List<Long> idList){
        hospitalSetService.removeByIds(idList);
        return Result.ok();
    }
    //医院设置锁定和解锁
    @PutMapping("lockHospitalSet/{id}/{status}")
    public Result lockHospitalSet(@PathVariable Long id,
                                  @PathVariable Integer status){
        HospitalSet hospitalSet = hospitalSetService.getById(id);
        hospitalSet.setStatus(status);
        hospitalSetService.updateById(hospitalSet);
        return Result.ok();
    }
    //发送签名密钥
    @PutMapping("sendKey/{id}")
    public Result lockHospitalSet(@PathVariable Long id){
        HospitalSet hospitalSet = hospitalSetService.getById(id);
        String signKey = hospitalSet.getSignKey();
        String hoscode = hospitalSet.getHoscode();
        //TODO 发送签名
        return Result.ok();

    }
}
