package com.github.taccisum.swagger.configurer.event;

import springfox.documentation.spring.web.plugins.Docket;

/**
 * @author tac - liaojf@cheegu.com
 * @since 2019/2/12
 */
public class AfterInitializeDocketEvent extends DocketBuilderEvent {
    public AfterInitializeDocketEvent(Docket instance) {
        super(instance);
    }
}
