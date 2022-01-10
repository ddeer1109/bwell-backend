package com.bwell.utils;

public class IdGenerator {
    private static Long id = 1L;

    public static synchronized Long nextId(){
        id += 1;
        return id;
    }
}
