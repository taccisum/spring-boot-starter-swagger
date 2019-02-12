package com.github.taccisum.swagger.configurer;

import com.github.taccisum.swagger.configurer.concrete.DefaultDescriptionBuilder;
import com.github.taccisum.swagger.configurer.config.SwaggerProperties;
import com.google.common.base.Predicates;
import com.google.common.collect.Lists;
import org.springframework.util.CollectionUtils;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author tac - liaojf@cheegu.com
 * @since 2019/2/1
 */
public class DocketBuilder {
    private Docket instance = new Docket(DocumentationType.SWAGGER_2);

    private SwaggerProperties properties;
    private DescriptionBuilder descriptionBuilder;

    public DocketBuilder(SwaggerProperties properties) {
        this.properties = properties;
        this.descriptionBuilder = new DefaultDescriptionBuilder(null, properties.getInfo().getDescription());
    }

    public void setDescriptionBuilder(DescriptionBuilder descriptionBuilder) {
        this.descriptionBuilder = descriptionBuilder;
    }

    public Docket build() {
        if (!properties.getEnabled()) {
            instance.enable(false);
            return instance;
        }

        if (CollectionUtils.isEmpty(properties.getIncludePaths())) {
            properties.setIncludePaths(Lists.newArrayList("/**"));
        }

        instance.apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage(properties.getBasePackage()))
                .paths(
                        Predicates.and(
                                Predicates.or(properties.getIncludePaths().stream().map(PathSelectors::ant).collect(Collectors.toList())),
                                Predicates.not(Predicates.or(properties.getExcludePaths().stream().map(PathSelectors::ant).collect(Collectors.toList())))
                        )
                )
                .build()
                .globalOperationParameters(gop())
        ;

        return instance;
    }

    private List<Parameter> gop() {
        Map<String, SwaggerProperties.ParameterProperties> globalParameters = properties.getGlobalParameters();
        if (globalParameters != null && globalParameters.size() > 0) {
            List<Parameter> parameters = new ArrayList<>();
            for (String name : globalParameters.keySet()) {
                SwaggerProperties.ParameterProperties parameterProperties = globalParameters.get(name);
                Parameter parameter = new ParameterBuilder()
                        .name(name)
                        .modelRef(new ModelRef(parameterProperties.getModelRef()))
                        .parameterType(parameterProperties.getParameterType())
                        .required(parameterProperties.getRequired())
                        .defaultValue(parameterProperties.getDefaultValue())
                        .description(parameterProperties.getDescription())
                        .hidden(parameterProperties.getHidden())
                        .build();
                parameters.add(parameter);
            }
            return parameters;
        }
        return null;
    }

    private ApiInfo apiInfo() {
        SwaggerProperties.Info conf = properties.getInfo();

        return new ApiInfoBuilder()
                .title(conf.getTitle())
                .description(descriptionBuilder.build())
                .version(conf.getVersion())
                .termsOfServiceUrl(conf.getTermsOfServiceUrl())
                .contact(new Contact(conf.getContact().getName(), conf.getContact().getUrl(), conf.getContact().getEmail()))
                .license(conf.getLicense())
                .licenseUrl(conf.getLicenseUrl())
                .build();
    }
}
