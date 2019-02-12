package com.github.taccisum.swagger.configurer.concrete;

import com.github.taccisum.swagger.configurer.DescriptionBuilder;
import com.github.taccisum.swagger.configurer.config.SwaggerProperties;
import com.github.taccisum.swagger.configurer.util.NetUtils;
import org.springframework.core.env.Environment;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

/**
 * @author tac - liaojf@cheegu.com
 * @since 2019/2/12
 */
public class DefaultDescriptionBuilder implements DescriptionBuilder {
    private Environment env;
    private SwaggerProperties.Info.Description descConf;

    public DefaultDescriptionBuilder(Environment env, SwaggerProperties.Info.Description descConf) {
        this.env = env;
        this.descConf = descConf;
    }

    @Override
    public String build() {
        StringBuilder sb = new StringBuilder();
        if (descConf.getShowProfiles() && env != null) {
            sb.append("<br/>");
            sb.append("<span style='font-weight:bold'>Active profiles: </span>");
            String[] activeProfiles = env.getActiveProfiles();
            if (activeProfiles.length == 0) {
                activeProfiles = new String[]{"default"};
            }
            sb.append(Arrays.toString(activeProfiles));
        }
        if (descConf.getShowStartDate()) {
            sb.append("<br/>");
            sb.append("<span style='font-weight:bold'>Start date: </span>");
            sb.append(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        }
        if (descConf.getShowHostname()) {
            sb.append("<br/>");
            sb.append("<span style='font-weight:bold'>Hostname: </span>");
            sb.append(NetUtils.getHostName());
        }

        return sb.toString();
    }
}
