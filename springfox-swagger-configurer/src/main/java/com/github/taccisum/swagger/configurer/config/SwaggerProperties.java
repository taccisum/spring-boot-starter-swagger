package com.github.taccisum.swagger.configurer.config;

import lombok.Getter;
import lombok.Setter;

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
    private Boolean enableUrlTemplating;
    /**
     * API document info
     */
    private Info info = new Info();
    /**
     * global operation parameters
     */
    private Map<String, ParameterProperties> globalParameters = new HashMap<>();

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
             * custom description html. will override all other configs if not null
             */
            private String html;
            /**
             * indicates if show active spring profiles of you project
             */
            private Boolean showProfiles;
            /**
             * indicates if show start date of you project
             */
            private Boolean showStartDate;
            /**
             * indicates if show hostname of the device you app running
             */
            private Boolean showHostname;
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
}
