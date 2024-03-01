package com.easysoftware.application.epkgpackage;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.easysoftware.application.epkgpackage.dto.EPKGPackageSearchCondition;
import com.easysoftware.application.epkgpackage.dto.InputEPKGPackage;
import com.easysoftware.common.utils.ResultUtil;
import com.easysoftware.domain.epkgpackage.gateway.EPKGPackageGateway;
import com.easysoftware.domain.rpmpackage.gateway.RPMPackageGateway;

import jakarta.annotation.Resource;

@Service
public class EPKGPackageServiceImpl implements EPKGPackageService {
    @Resource
    EPKGPackageGateway ePKGPackageGateway;
    
    @Override
    public ResponseEntity<Object> deleteEPKGPkg(List<String> names) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ResponseEntity<Object> insertEPKGPkg(InputEPKGPackage input) {
       return null;
    }

    @Override
    public Map<String, Object> queryAllEPKGPkgMenu(EPKGPackageSearchCondition condition) {
        Map<String, Object> ePKGMenu = ePKGPackageGateway.queryMenuByName(condition);
        return ePKGMenu;
    }

    @Override
    public ResponseEntity<Object> searchEPKGPkg(EPKGPackageSearchCondition condition) {
        Map<String, Object> res = ePKGPackageGateway.queryDetailByName(condition);
        return ResultUtil.success(HttpStatus.OK, res);
    }

    @Override
    public ResponseEntity<Object> updateEPKGPkg(InputEPKGPackage inputrPMPackage) {
        // TODO Auto-generated method stub
        return null;
    }
    
}
