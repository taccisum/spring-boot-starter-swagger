package com.github.taccisum.swagger.integration;

import com.github.taccisum.swagger.configurer.config.SwaggerProperties;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author tac - liaojf@cheegu.com
 * @since 2019/2/14
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("min")
public class MinConfigurationTest {
    @Autowired
    private SwaggerProperties swaggerProperties;

    @Test
    public void testSimply() throws Exception {
        assertThat(swaggerProperties).isNotNull();
    }
}
