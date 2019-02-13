package com.github.taccisum.swagger.configurer;

import com.github.taccisum.swagger.configurer.config.SwaggerProperties;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger.web.UiConfigurationBuilder;

/**
 * @author tac - liaojf@cheegu.com
 * @since 2019/2/13
 */
public class UIConfigurationBuilderAdapter {
    private SwaggerProperties properties;

    public UIConfigurationBuilderAdapter(SwaggerProperties properties) {
        this.properties = properties;
    }

    public UiConfiguration build() {
        SwaggerProperties.UIProperties uiProperties = properties.getUi();
        return UiConfigurationBuilder.builder()
                .deepLinking(uiProperties.getDeepLinking())
                .displayOperationId(uiProperties.getDisplayOperationId())
                .defaultModelsExpandDepth(uiProperties.getDefaultModelsExpandDepth())
                .defaultModelExpandDepth(uiProperties.getDefaultModelExpandDepth())
                .defaultModelRendering(uiProperties.getDefaultModelRendering())
                .displayRequestDuration(uiProperties.getDisplayRequestDuration())
                .docExpansion(uiProperties.getDocExpansion())
                .filter(uiProperties.getFilter())
                .maxDisplayedTags(uiProperties.getMaxDisplayedTags())
                .operationsSorter(uiProperties.getOperationsSorter())
                .showExtensions(uiProperties.getShowExtensions())
                .tagsSorter(uiProperties.getTagsSorter())
                .supportedSubmitMethods(uiProperties.getSupportedSubmitMethods())
                .validatorUrl(uiProperties.getValidatorUrl())
                .build();
    }
}
