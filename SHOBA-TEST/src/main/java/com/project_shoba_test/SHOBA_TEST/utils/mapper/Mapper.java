package com.project_shoba_test.SHOBA_TEST.utils.mapper;

public interface Mapper<A, B> {
    public A mapTo(B b);

    public B mapFrom(A a);
}

