package com.github.taccisum.swagger.configurer.config;

import com.github.taccisum.swagger.configurer.constant.PassAs;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import springfox.documentation.swagger.web.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author tac - liaojf@cheegu.com
 * @since 2019/2/1
 */
@Getter
@Setter
@ConfigurationProperties(SwaggerProperties.PREFIX)
public class SwaggerProperties {
    /**
     * spring boot properties prefix constant
     */
    public static final String PREFIX = "swagger";

    /**
     * indicates if enable swagger auto configuration
     */
    private Boolean enabled = true;
    /**
     * base package where to scan API controllers
     */
    private String basePackage = null;
    /**
     * include api paths
     */
    private List<String> includePaths = new ArrayList<>();
    /**
     * exclude api paths
     */
    private List<String> excludePaths = new ArrayList<>();
    /**
     * path that acts as a prefix to the api base path
     */
    private String pathMapping = "/";
    /**
     * generic classes on which to apply generic model substitution
     */
    private List<Class> genericModelSubstitutes = new ArrayList<>();
    /**
     * when true it enables rfc6570 url templates
     */
    private Boolean enableUrlTemplating = false;
    /**
     * how to access apis of you app
     */
    private AuthProperties auth = new AuthProperties();
    /**
     * API document info
     */
    private Info info = new Info();
    /**
     * global operation parameters
     */
    private Map<String, ParameterProperties> globalParameters = new HashMap<>();
    /**
     * swagger ui configuration
     */
    private UIProperties ui = new UIProperties();

    @Getter
    @Setter
    public static class Info {
        /**
         * title of this document
         */
        private String title;
        /**
         * version of this document
         */
        private String version;
        /**
         * contact info
         */
        private Contact contact = new Contact();
        /**
         * terms of service url
         */
        private String termsOfServiceUrl;
        /**
         * license
         */
        private String license;
        /**
         * license url
         */
        private String licenseUrl;
        /**
         * description for this document
         */
        private Description description = new Description();

        @Getter
        @Setter
        public static class Description {
            /**
             * custom description html. if not null, will override all other configs
             */
            private String html;
            /**
             * indicates if show active spring profiles of you project
             */
            private Boolean showProfiles = true;
            /**
             * indicates if show start date of you project
             */
            private Boolean showStartDate = true;
            /**
             * indicates if show hostname of the device you app running
             */
            private Boolean showHostname = true;
        }

        @Getter
        @Setter
        public static class Contact {
            /**
             * contact name
             */
            private String name;
            /**
             * contact url
             */
            private String url;
            /**
             * contact email
             */
            private String email;
        }
    }

    @Getter
    @Setter
    public static class ParameterProperties {
        private String modelRef = "string";
        private String parameterType = "query";
        private Boolean required = false;
        private String defaultValue;
        private String description = "global operation parameter";
        private Boolean hidden = false;
    }

    @Getter
    @Setter
    public static class AuthProperties {
        private Map<String, ApiKeySecurityProperties> apiKey = new HashMap<>();

        @Getter
        @Setter
        public static class ApiKeySecurityProperties {
            private String keyName;
            private PassAs passAs;
            private List<String> includePaths = new ArrayList<>();
            private List<String> excludePaths = new ArrayList<>();
        }
    }

    @Getter
    @Setter
    public static class UIProperties {
        /**
         * whether enable deep linking for tags and operation
         */
        private Boolean deepLinking = true;
        /**
         * whether display of operationId in operations list
         */
        private Boolean displayOperationId = false;
        /**
         * The default expansion depth for models (set to -1 completely hide the models)
         */
        private Integer defaultModelsExpandDepth = 1;
        /**
         * The default expansion depth for the model on the model-example section
         */
        private Integer defaultModelExpandDepth = 1;
        /**
         * how the model is shown when the API is first rendered
         */
        private ModelRendering defaultModelRendering = ModelRendering.EXAMPLE;
        /**
         * whether display of the request duration
         */
        private Boolean displayRequestDuration = false;
        /**
         * controls the default expansion setting for the operations and tags
         */
        private DocExpansion docExpansion = DocExpansion.NONE;
        /**
         * whether enable filtering
         */
        private Boolean filter = false;
        /**
         * if set, limits the number of tagged operations displayed to at most this many
         */
        private Integer maxDisplayedTags;
        /**
         * apply a sort to the operation list of each API
         */
        private OperationsSorter operationsSorter = OperationsSorter.ALPHA;
        /**
         * whether display of vendor extension (x-) fields and values for Operations, Parameters, and Schema
         */
        private Boolean showExtensions = false;
        /**
         * apply a sort to the tag list of each API
         */
        private TagsSorter tagsSorter = TagsSorter.ALPHA;
        /**
         * list of HTTP methods that have the Try it out feature enabled
         */
        private String[] supportedSubmitMethods = UiConfiguration.Constants.DEFAULT_SUBMIT_METHODS;
        /**
         * specs validator url. use swagger.io's online validator by default. if null, disable validation
         */
        private String validatorUrl;
        private Resources resources = new Resources();

        @Data
        public static class Resources {
            private Resource statics = new Resource("/**", "classpath:/static/");
            private Resource index = new Resource("swagger-ui.html", "classpath:/META-INF/resources/");
            private Resource webjars = new Resource("/webjars/**", "classpath:/META-INF/resources/webjars/");

            @Data
            public static class Resource {
                private String pathPattern;
                private String locations;

                public Resource(String pathPattern, String locations) {
                    this.pathPattern = pathPattern;
                    this.locations = locations;
                }
            }
        }
    }
}
