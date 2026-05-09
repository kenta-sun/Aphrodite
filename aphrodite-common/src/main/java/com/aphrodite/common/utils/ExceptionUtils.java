package com.aphrodite.common.utils;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.regex.Pattern;

/**
 * 异常处理相关工具
 */
public class ExceptionUtils {

    /**
     * 将异常的堆栈信息转换为字符串
     *
     * @param t 异常对象
     * @param regex 正则表达式，用于过滤堆栈信息
     * @return 字符串，包含异常message和过滤后的堆栈信息
     */
    public static String stackTraceStringFilter(Throwable t, String regex) {
        if (t == null || AphUtils.isBlank(regex)) {
            return null;
        }

        final StringWriter sw = new StringWriter();
        final PrintWriter pw = new PrintWriter(sw, true);
        t.printStackTrace(pw);
        String stackTraceString = sw.getBuffer().toString();
        if (AphUtils.isBlank(stackTraceString)) {
            return null;
        }

        String[] stackTraceStringArray = stackTraceString.split("\n");
        Pattern pattern = Pattern.compile(regex);
        stackTraceStringArray = Arrays.stream(stackTraceStringArray)
                .filter(s -> {
                    s = s.trim();
                    return AphUtils.isNotBlank(s) && (!s.startsWith("at") || pattern.matcher(s).matches());
                })
                .toArray(String[]::new);
        return String.join("\n", stackTraceStringArray);
    }
}
