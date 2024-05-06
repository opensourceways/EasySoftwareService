package com.easysoftware.adapter.execute;

import com.easysoftware.application.operationconfig.OperationConfigService;
import com.easysoftware.application.operationconfig.dto.InputOperationConfig;
import com.easysoftware.common.entity.MessageCode;
import com.easysoftware.common.utils.ResultUtil;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/operationconfig")
public class OperationConfigExecuteAdapter {


    /**
     * Autowired service for handling operation configurations.
     */
    @Autowired
    private OperationConfigService service;


    /**
     * Endpoint to insert a new operation configuration.
     *
     * @param input The input data for creating a new operation configuration.
     * @return ResponseEntity<Object>.
     */
    @PostMapping("")
    public ResponseEntity<Object> insertOperationConfig(@Valid @RequestBody final InputOperationConfig input) {
        boolean succeed = service.insertOperationConfig(input);
        if (succeed) {
            return ResultUtil.success(HttpStatus.OK);
        }
        return ResultUtil.fail(HttpStatus.BAD_REQUEST, MessageCode.EC0006);
    }

    /**
     * Endpoint to delete operation configurations by type.
     *
     * @param type Type of operation configuration to be deleted.
     * @return ResponseEntity<Object>.
     */
    @DeleteMapping("/{type}")
    public ResponseEntity<Object> deleteByType(@Size(max = 255) @PathVariable final String type) {
        boolean succeed = service.deleteByType(type);
        if (succeed) {
            return ResultUtil.success(HttpStatus.OK);
        }
        return ResultUtil.fail(HttpStatus.BAD_REQUEST, MessageCode.EC0005);
    }
}
