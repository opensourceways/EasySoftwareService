package com.easysoftware.adapter.execute;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.easysoftware.application.operationconfig.OperationConfigService;
import com.easysoftware.application.operationconfig.dto.InputOperationConfig;
import com.easysoftware.application.rpmpackage.dto.InputRPMPackage;
import com.easysoftware.common.entity.MessageCode;
import com.easysoftware.common.utils.ResultUtil;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;

@RestController
@RequestMapping("/operationconfig")
public class OperationConfigExecuteAdapter {

    @Autowired
    OperationConfigService service;

    @PostMapping("")
    public ResponseEntity<Object> insertOperationConfig(@Valid @RequestBody InputOperationConfig input) {
        boolean succeed = service.insertOperationConfig(input);
        if (succeed) {
            return ResultUtil.success(HttpStatus.OK);
        }
        return ResultUtil.fail(HttpStatus.BAD_REQUEST, MessageCode.EC0006);
    }

    @DeleteMapping("/{type}")
    public ResponseEntity<Object> deleteByType (@Size(max = 255) @PathVariable String type) {
        boolean succeed = service.deleteByType(type);
        if (succeed) {
            return ResultUtil.success(HttpStatus.OK);
        }
        return ResultUtil.fail(HttpStatus.BAD_REQUEST, MessageCode.EC0005);
    }
}
