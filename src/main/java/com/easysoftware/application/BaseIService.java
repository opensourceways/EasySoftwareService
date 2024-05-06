package com.easysoftware.application;

import com.baomidou.mybatisplus.extension.service.IService;

import java.util.ArrayList;

public interface BaseIService<T> extends IService<T> {
    /**
     * Saves a batch of data objects.
     *
     * @param dataObject An ArrayList containing the data objects to be saved.
     */
    void saveDataObjectBatch(ArrayList<String> dataObject);
}
