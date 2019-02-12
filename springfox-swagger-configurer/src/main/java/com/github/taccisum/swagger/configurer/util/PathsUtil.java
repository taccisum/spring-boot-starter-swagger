package com.github.taccisum.swagger.configurer.util;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import springfox.documentation.builders.PathSelectors;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author tac - liaojf@cheegu.com
 * @since 2019/2/12
 */
public abstract class PathsUtil {
    public static Predicate<String> includeAndExclude(List<String> includePaths, List<String> excludePaths) {
        return Predicates.and(
                Predicates.or(includePaths.stream().map(PathSelectors::ant).collect(Collectors.toList())),
                Predicates.not(Predicates.or(excludePaths.stream().map(PathSelectors::ant).collect(Collectors.toList())))
        );
    }
}
