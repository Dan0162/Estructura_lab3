package com.jsonparsing;

import java.io.IOException;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class json {

    private static ObjectMapper ObjectMapper = getDefaulObjectMapper();

    private static ObjectMapper getDefaulObjectMapper(){
        ObjectMapper defaultObjectMapper = new ObjectMapper();
        //...
        return defaultObjectMapper;
    }

    public static JsonNode parse(String src) throws IOException{
        return ObjectMapper.readTree(src);
    }
}


