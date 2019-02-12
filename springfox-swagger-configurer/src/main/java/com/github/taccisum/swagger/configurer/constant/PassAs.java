package com.github.taccisum.swagger.configurer.constant;

/**
 * @author tac - liaojf@cheegu.com
 * @since 2019/2/12
 */
public enum PassAs {
    HEADER("header");

    private String val;

    public String val() {
        return val;
    }

    PassAs(String val) {
        this.val = val;
    }
}
