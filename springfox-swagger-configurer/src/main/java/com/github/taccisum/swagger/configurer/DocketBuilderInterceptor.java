package com.github.taccisum.swagger.configurer;

import com.github.taccisum.swagger.configurer.event.AfterInitializeDocketEvent;
import com.github.taccisum.swagger.configurer.event.BeforeInitializeDocketEvent;
import com.google.common.eventbus.Subscribe;
import org.springframework.core.Ordered;

/**
 * @author tac - liaojf@cheegu.com
 * @since 2019/2/12
 */
public interface DocketBuilderInterceptor extends Ordered {
    @Subscribe
    void before(BeforeInitializeDocketEvent event);

    @Subscribe
    void after(AfterInitializeDocketEvent event);
}
