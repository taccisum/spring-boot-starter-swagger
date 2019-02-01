package com.github.taccisum.swagger.configurer;

import com.github.taccisum.swagger.configurer.config.SwaggerProperties;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * @author tac - liaojf@cheegu.com
 * @since 2019/2/1
 */
public class DocketBuilder {
    private Docket instance = new Docket(DocumentationType.SWAGGER_2);

    private SwaggerProperties properties;

    public DocketBuilder(SwaggerProperties properties) {
        this.properties = properties;
    }

    public Docket build() {
        if (!properties.getEnabled()) {
            instance.enable(false);
            return instance;
        }


        return instance;
    }
}
