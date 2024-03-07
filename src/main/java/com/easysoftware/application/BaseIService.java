package com.easysoftware.application;

import java.util.ArrayList;

import com.baomidou.mybatisplus.extension.service.IService;

public interface BaseIService<T> extends IService<T>{
    boolean existApp(String name);
    void saveDataObject(String dataObject);
    void saveDataObjectBatch(ArrayList<String> dataObject);
}
