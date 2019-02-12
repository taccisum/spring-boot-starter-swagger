package com.github.taccisum.swagger.autoconfigure;

import com.github.taccisum.swagger.configurer.DocketBuilder;
import com.github.taccisum.swagger.configurer.DocketBuilderInterceptor;
import com.github.taccisum.swagger.configurer.concrete.DefaultDescriptionBuilder;
import com.github.taccisum.swagger.configurer.config.SwaggerProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.util.CollectionUtils;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;

/**
 * @author tac - liaojf@cheegu.com
 * @since 2019/2/12
 */
@EnableSwagger2
public class SwaggerAutoConfiguration {
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
}
