package com.github.taccisum.swagger.configurer.config;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
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
     * indicates if enabled swagger2
     */
    private Boolean enabled = true;
    /**
     * base package where to scan API controllers
     */
    private String basePackage = null;
    /**
     * API document info
     */
    private Info info = new Info();
    /**
     * global operation parameters
     */
    private Map<String, ParameterProperties> globalOperationParameters = new HashMap<>();

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
         *
         */
        private String contractName;
        private String license;
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
