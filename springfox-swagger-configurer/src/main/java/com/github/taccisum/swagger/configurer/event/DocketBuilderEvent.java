package com.github.taccisum.swagger.configurer.event;

import com.github.taccisum.swagger.configurer.Event;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * @author tac - liaojf@cheegu.com
 * @since 2019/2/12
 */
public abstract class DocketBuilderEvent implements Event {
    private Docket instance;

    public Docket getInstance() {
        return instance;
    }

    public DocketBuilderEvent(Docket instance) {
        this.instance = instance;
    }
}
