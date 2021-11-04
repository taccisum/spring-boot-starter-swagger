package com.github.taccisum.swagger.configurer;

import com.github.taccisum.swagger.configurer.config.SwaggerProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

/**
 * 处理 swagger 相关的 resources mapping，解决诸如某些情况下访问 swagger-ui.html 404 的问题
 *
 * @author taccisum - liaojinfeng6938@dingtalk.com
 * @since 2021-11-04
 */
@Slf4j
public class ResourceMappingAdapter {
    private SwaggerProperties properties;

    public ResourceMappingAdapter(SwaggerProperties properties) {
        this.properties = properties;
    }

    public void doMapping(ResourceHandlerRegistry registry) {
        SwaggerProperties.UIProperties.Resources.Resource statics = properties.getUi().getResources().getStatics();
        SwaggerProperties.UIProperties.Resources.Resource index = properties.getUi().getResources().getIndex();
        SwaggerProperties.UIProperties.Resources.Resource webjar = properties.getUi().getResources().getWebjars();

        log.info("Mapping Swagger UI static files resource \"{}\" to \"{}\".", statics.getPathPattern(), statics.getLocations());
        registry.addResourceHandler(statics.getPathPattern()).addResourceLocations(statics.getLocations());
        log.info("Mapping Swagger UI index resource \"{}\" to \"{}\".", index.getPathPattern(), index.getLocations());
        registry.addResourceHandler(index.getPathPattern()).addResourceLocations(index.getLocations());
        log.info("Mapping Swagger UI webjars resource \"{}\" to \"{}\".", webjar.getPathPattern(), webjar.getLocations());
        registry.addResourceHandler(webjar.getPathPattern()).addResourceLocations(webjar.getLocations());
    }
}
