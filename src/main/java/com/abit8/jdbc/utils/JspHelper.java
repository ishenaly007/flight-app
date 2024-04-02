package com.abit8.jdbc.utils;

import lombok.experimental.UtilityClass;

import java.awt.*;

@UtilityClass
public class JspHelper {
    private final static String JSP_FORMAT = "/%s.jsp";
    public String getPath(String jsp){
        return JSP_FORMAT.formatted(jsp);
    }
}
