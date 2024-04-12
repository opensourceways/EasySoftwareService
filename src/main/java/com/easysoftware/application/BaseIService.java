package com.easysoftware.application;

import java.util.ArrayList;

import com.baomidou.mybatisplus.extension.service.IService;

public interface BaseIService<T> extends IService<T>{
    void saveDataObjectBatch(ArrayList<String> dataObject);
}
