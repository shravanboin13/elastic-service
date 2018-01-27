package com.demo.utils;

import com.demo.com.demo.dto.SearchQueryDTO;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
@Component
public class CommonUtils {
    public SearchQueryDTO createSearchQueryDTO(Map queryMap) {
        SearchQueryDTO searchDTO = new SearchQueryDTO();
       /* if(!StringUtils.isEmpty(queryMap.get("content"))){
            searchDTO.setContent((String) queryMap.get("content"));
        }
        if(!StringUtils.isEmpty(queryMap.get("color"))){
            searchDTO.setColor((String) queryMap.get("color"));
        }
        if(!StringUtils.isEmpty(queryMap.get("brand"))){
            searchDTO.setBrand((String) queryMap.get("brand"));
        }
        if(!StringUtils.isEmpty(queryMap.get("size"))){
            searchDTO.setSize((String) queryMap.get("size"));
        }
*/
        /*if(!StringUtils.isEmpty(queryMap.get("color"))){
           String[] aColor =  (String [])queryMap.get("color");
           List<String> alColor = new ArrayList<String>();
           if(null!=aColor && aColor.length>0){
               for(String color:aColor) {
                   alColor.add(color);
               }
           }
            searchDTO.setColor(alColor);
        }
        if(!StringUtils.isEmpty(queryMap.get("brand"))){
            String[] aColor =  (String [])queryMap.get("brand");
            List<String> alColor = new ArrayList<String>();
            if(null!=aColor && aColor.length>0){
                for(String color:aColor) {
                    alColor.add(color);
                }
            }
            searchDTO.setColor(alColor);

        }*/

return searchDTO;
            }

}
