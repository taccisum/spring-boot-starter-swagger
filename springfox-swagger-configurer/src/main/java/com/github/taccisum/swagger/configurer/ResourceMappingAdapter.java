package com.github.taccisum.swagger.configurer;

import com.github.taccisum.swagger.configurer.config.SwaggerProperties;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

/**
 * 处理 swagger 相关的 resources mapping，解决诸如某些情况下访问 swagger-ui.html 404 的问题
 *
 * @author taccisum - liaojinfeng6938@dingtalk.com
 * @since 2021-11-04
 */
public class ResourceMappingAdapter {
    private SwaggerProperties properties;

    public ResourceMappingAdapter(SwaggerProperties properties) {
        this.properties = properties;
    }

    public void doMapping(ResourceHandlerRegistry registry) {
        SwaggerProperties.UIProperties.Resources.Resource statics = properties.getUi().getResources().getStatics();
        SwaggerProperties.UIProperties.Resources.Resource index = properties.getUi().getResources().getIndex();
        SwaggerProperties.UIProperties.Resources.Resource webjar = properties.getUi().getResources().getWebjars();

        registry.addResourceHandler(statics.getPathPattern()).addResourceLocations(statics.getLocations());
        registry.addResourceHandler(index.getPathPattern()).addResourceLocations(index.getLocations());
        registry.addResourceHandler(webjar.getPathPattern()).addResourceLocations(webjar.getLocations());
    }
}
