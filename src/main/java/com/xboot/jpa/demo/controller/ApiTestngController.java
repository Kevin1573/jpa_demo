package com.xboot.jpa.demo.controller;

import com.xboot.jpa.demo.common.resp.Result;
import com.xboot.jpa.demo.dal.dao.ProjectApiInfoRepository;
import com.xboot.jpa.demo.dal.dataobject.ProjectApiInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.condition.RequestMethodsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.springframework.web.util.pattern.PathPattern;

import java.lang.reflect.Field;
import java.util.*;

/**
 * 注释
 *
 * @author xboot
 **/
@Slf4j
@RestController
@RequestMapping("/testng")
public class ApiTestngController {
    private final WebApplicationContext applicationContext;
    private final String[] excludedUrl = {"/error"};
    private final String[] excludedParamTypes = {"java.lang.Long", "java.lang.String"};
    private static final String PARAMS = "params";
    private final ProjectApiInfoRepository projectApiInfoRepository;

    @Autowired
    public ApiTestngController(WebApplicationContext applicationContext,
                               ProjectApiInfoRepository projectApiInfoRepository) {
        this.applicationContext = applicationContext;
        this.projectApiInfoRepository = projectApiInfoRepository;
    }

    @RequestMapping("/getApiInvokeList")
    public Result getApiInvokeList() {
        RequestMappingHandlerMapping mapping = applicationContext.getBean(RequestMappingHandlerMapping.class);
        // 拿到Handler适配器中的全部方法
        Map<RequestMappingInfo, HandlerMethod> methodMap = mapping.getHandlerMethods();
        List<Map<String, Object>> urlList = new ArrayList<>();

        for (Map.Entry<RequestMappingInfo, HandlerMethod> methodEntry : methodMap.entrySet()) {
            RequestMappingInfo key = methodEntry.getKey();
            HandlerMethod value = methodEntry.getValue();
            Map<String, Object> urlMap = new HashMap<>();
            Set<PathPattern> patterns = key.getPathPatternsCondition() == null ?
                    Collections.emptySet() : key.getPathPatternsCondition().getPatterns();
            patterns.stream().findFirst()
                    .ifPresentOrElse(pattern -> urlMap.put("url", pattern.getPatternString()),
                            () -> urlMap.put("url", "not found"));
            boolean anyMatch = Arrays.stream(excludedUrl).anyMatch(url -> url.equals(urlMap.get("url")));
            if (urlMap.get("url") == null || anyMatch) {
                continue;
            }
            RequestMethodsRequestCondition method = key.getMethodsCondition();
            urlMap.put("method", method.getMethods());
            MethodParameter[] methodParameters = value.getMethodParameters();
            int paramCount = 0;
            List<String> params = new ArrayList<>();
            List<Map<String, Object>> paramsType = new ArrayList<>();
            for (MethodParameter methodParameter : methodParameters) {
                paramCount++;
                log.info("{}", methodParameter.getParameterType());
                urlMap.put(PARAMS + (paramCount), methodParameter.getParameterType().getTypeName());
                params.add(methodParameter.getParameterType().getTypeName());
                boolean paramTypeMatch = false;
                for (String paramType : excludedParamTypes) {
                    if (paramType.equals(methodParameter.getParameterType().getName())) {
                        paramTypeMatch = true;
                    }
                }
                if (!paramTypeMatch) {
                    // 反射获取属性名
                    log.info("{}", methodParameter.getParameterName());
                    Map<String, Object> fieldMapping = reflectMethod(methodParameter.getParameterType());
                    urlMap.put("fieldMapping" + paramCount, fieldMapping);
                    paramsType.add(fieldMapping);
                }
            }
            urlMap.put("paramsType", paramsType);
            urlMap.put(PARAMS, params);
            urlList.add(urlMap);
        }

        buildAndSaveProjectApiInfo(urlList);
        return Result.ok().data("list", urlList);
    }

    private void buildAndSaveProjectApiInfo(List<Map<String, Object>> urlList) {
        projectApiInfoRepository.deleteAll();
        for (Map<String, Object> urlMap : urlList) {
            projectApiInfoRepository.save(new ProjectApiInfo(
                    (String) urlMap.get("url"),
                    urlMap.get("method"),
                    urlMap.get(PARAMS),
                    urlMap.get("paramsType")
            ));
        }
    }

    /**
     * 反射方法获取私有属性及方法
     */
    public static Map<String, Object> reflectMethod(Class<?> clazz) {
        try {
            // 获得Bean所有属性
            Field[] fields = clazz.getDeclaredFields();
            Map<String, Object> fieldMap = new HashMap<>();
            for (Field field : fields) {
                // 将目标属性设置为可以访问
                fieldMap.put(field.getName(), field.getType());
            }
            return fieldMap;
        } catch (Exception e) {
            log.error("反射异常", e);
        }

        return Collections.emptyMap();
    }

}
