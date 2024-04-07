package com.abit8.jdbc.mapper;

public interface Mapper <T, F>{
    public T mapFrom(F f);
}
