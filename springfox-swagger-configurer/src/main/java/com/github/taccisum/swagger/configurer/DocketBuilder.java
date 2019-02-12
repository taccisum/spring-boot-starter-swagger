package com.github.taccisum.swagger.configurer;

import com.github.taccisum.swagger.configurer.concrete.DefaultDescriptionBuilder;
import com.github.taccisum.swagger.configurer.config.SwaggerProperties;
import com.github.taccisum.swagger.configurer.event.AfterInitializeDocketEvent;
import com.github.taccisum.swagger.configurer.event.BeforeInitializeDocketEvent;
import com.github.taccisum.swagger.configurer.util.PathsUtil;
import com.google.common.collect.Lists;
import com.google.common.eventbus.EventBus;
import org.springframework.core.Ordered;
import org.springframework.util.CollectionUtils;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.*;

/**
 * @author tac - liaojf@cheegu.com
 * @since 2019/2/1
 */
public class DocketBuilder {
    private EventBus eventBus = new EventBus();
    private List<DocketBuilderInterceptor> interceptors = new ArrayList<>();
    private Docket instance = null;

    private SwaggerProperties properties;
    private DescriptionBuilder descriptionBuilder;

    public DocketBuilder(SwaggerProperties properties) {
        this.properties = properties;
        this.descriptionBuilder = new DefaultDescriptionBuilder(null, properties.getInfo().getDescription());
    }

    public void setDescriptionBuilder(DescriptionBuilder descriptionBuilder) {
        this.descriptionBuilder = descriptionBuilder;
    }

    public void addInterceptor(DocketBuilderInterceptor interceptor) {
        interceptors.add(interceptor);
    }

    public Docket build() {
        if (instance != null) {
            return instance;
        }

        instance = new Docket(DocumentationType.SWAGGER_2);
        interceptors.stream()
                .sorted(Comparator.comparingInt(Ordered::getOrder))
                .forEach(i -> eventBus.register(i));

        eventBus.post(new BeforeInitializeDocketEvent());

        if (!properties.getEnabled()) {
            instance.enable(false);
            return instance;
        }

        if (CollectionUtils.isEmpty(properties.getIncludePaths())) {
            properties.setIncludePaths(Lists.newArrayList("/**"));
        }

        Map<String, SecurityScheme> schemeMap = new HashMap<>();
        Map<String, SecurityContext> contextMap = new HashMap<>();

        properties.getAuth().getApiKey().forEach((k, v) -> {
            schemeMap.put(k, new ApiKey(k, v.getKeyName(), v.getPassAs().val()));
            contextMap.put(k, SecurityContext.builder()
                    .securityReferences(Lists.newArrayList(
                            SecurityReference.builder().scopes(new AuthorizationScope[]{}).reference(k).build()
                    ))
                    .forPaths(PathsUtil.includeAndExclude(v.getIncludePaths(), v.getExcludePaths()))
                    .build()
            );
        });

        instance.apiInfo(apiInfo())
                .pathMapping(properties.getPathMapping())
                .genericModelSubstitutes(properties.getGenericModelSubstitutes().toArray(new Class[]{}))
                .enableUrlTemplating(properties.getEnableUrlTemplating())
                .select()
                .apis(RequestHandlerSelectors.basePackage(properties.getBasePackage()))
                .paths(PathsUtil.includeAndExclude(properties.getIncludePaths(), properties.getExcludePaths()))
                .build()
                .globalOperationParameters(gop())
                .securitySchemes(new ArrayList<>(schemeMap.values()))
                .securityContexts(new ArrayList<>(contextMap.values()))
        ;

        eventBus.post(new AfterInitializeDocketEvent());

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
