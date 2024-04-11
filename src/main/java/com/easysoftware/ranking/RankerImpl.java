package com.easysoftware.ranking;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.easysoftware.application.domainpackage.vo.DomainPackageMenuVo;
import com.easysoftware.application.operationconfig.vo.OperationConfigVo;
import com.easysoftware.domain.operationconfig.gateway.OperationConfigGateway;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;



@Component
public class RankerImpl implements Ranker{


    @Autowired
    private OperationConfigGateway configGateway;

    public List<Map<String, Object>> rankingDomainPageByAlgorithm(List<Map<String, Object>> domainPage){
        return null;
    }


    public List<Map<String, Object>> rankingDomainPageByOperationConfig(List<Map<String, Object>> domainPage){

        List<OperationConfigVo> rankingConfig = configGateway.selectAll();

        // 再排序，避免数据库写入顺序错乱
        Collections.sort(rankingConfig, new Comparator<OperationConfigVo>() {
            @Override
            public int compare(OperationConfigVo o1, OperationConfigVo o2) {
                int a = Integer.parseInt(o1.getOrderIndex());
                int b = Integer.parseInt(o2.getOrderIndex());
                return  a - b;
            }
        });

        List<Map<String, Object>> rankingList = new ArrayList<>();

        for (OperationConfigVo config : rankingConfig){
            String rankingCatego = config.getCategorys();
            
            // 排序边栏
            Iterator<Map<String, Object>> iterator = domainPage.iterator();  
            while (iterator.hasNext()) {  
                Map<String, Object> item = iterator.next();  
                String catego = (String)item.get("name");
                if (catego.equals(rankingCatego)) { 
                    // 排序内容 
                    Map<String, Object> rankedItem = rankContent(item,config.getRecommend());
                    rankingList.add(rankedItem);
                    iterator.remove();  
                }  
            }
        }
       
        for(int i = rankingList.size() - 1 ; i >=0 ; i --){
            domainPage.add(0,rankingList.get(i));
        }


        return domainPage;
    }

    private Map<String, Object> rankContent(Map<String, Object> item,String recommendOrder){
      
        List<Object> menuVoList = (List<Object>) item.get("children");

        if(menuVoList == null){
            return item;
        }

        String[] recommendNames = recommendOrder.replace(" ", "").split(",");
        List<Object> rankingList = new ArrayList<>();

        System.out.println("start ranking");
        for(String name : recommendNames){
            Iterator<Object> iterator = menuVoList.iterator();
            while (iterator.hasNext()) {  
                Object menuVoObj = iterator.next(); 
                DomainPackageMenuVo menuVo = (DomainPackageMenuVo) menuVoObj;
                String softWareName = (String)menuVo.getName();
                if(softWareName.equals(name)){
                    rankingList.add(menuVo);
                    iterator.remove();
                }
            }
        }


        for(int i = rankingList.size() - 1 ; i >= 0; i --){
            menuVoList.add(0,rankingList.get(i));
        }

        for(Object obj : menuVoList){
            DomainPackageMenuVo menuVo = (DomainPackageMenuVo )obj;
            System.out.println(menuVo.getName());
        }
        
        // 避免设置空value
        if(menuVoList != null) {
            item.put("children", menuVoList);
        }
      
        return item;
    }
}
