package com.github.taccisum.swagger.autoconfigure;

import com.github.taccisum.swagger.configurer.DocketBuilder;
import com.github.taccisum.swagger.configurer.DocketBuilderInterceptor;
import com.github.taccisum.swagger.configurer.ResourceMappingAdapter;
import com.github.taccisum.swagger.configurer.UIConfigurationBuilderAdapter;
import com.github.taccisum.swagger.configurer.concrete.DefaultDescriptionBuilder;
import com.github.taccisum.swagger.configurer.config.SwaggerProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.util.CollectionUtils;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;

/**
 * @author tac - liaojf@cheegu.com
 * @since 2019/2/12
 */
@EnableSwagger2
public class SwaggerAutoConfiguration implements WebMvcConfigurer {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SwaggerProperties properties;
    @Autowired
    private Environment environment;
    @Autowired(required = false)
    private List<DocketBuilderInterceptor> interceptors;

    @Bean
    @ConditionalOnMissingBean
    @ConfigurationProperties(SwaggerProperties.PREFIX)
    public SwaggerProperties swaggerProperties() {
        return new SwaggerProperties();
    }

    @Bean
    @ConditionalOnMissingBean
    public Docket docket() {
        DocketBuilder builder = new DocketBuilder(properties);
        builder.setDescriptionBuilder(new DefaultDescriptionBuilder(environment, properties.getInfo().getDescription()));

        if (!CollectionUtils.isEmpty(interceptors)) {
            interceptors.forEach(builder::addInterceptor);
        }

        return builder.build();
    }

    @Bean
    @ConditionalOnMissingBean
    public UiConfiguration uiConfiguration() {
        return new UIConfigurationBuilderAdapter(properties).build();
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        logger.info("Handle resource mapping for Swagger UI.");
        new ResourceMappingAdapter(properties).doMapping(registry);
    }
}
