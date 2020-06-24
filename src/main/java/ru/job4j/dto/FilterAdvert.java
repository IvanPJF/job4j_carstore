package ru.job4j.dto;

import java.util.Objects;

public class FilterAdvert {

    private String name;
    private String paramName;
    private Object paramValue;

    public String getName() {
        return name;
    }

    public String getParamName() {
        return paramName;
    }

    public Object getParamValue() {
        return paramValue;
    }

    public boolean isWithParameter() {
        return Objects.nonNull(paramName);
    }
}
