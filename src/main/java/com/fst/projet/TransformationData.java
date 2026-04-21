package com.fst.projet;

import java.util.HashMap;
import java.util.Map;

public class TransformationData {

    private String type;
    private Map<String, Object> params;

    public TransformationData() {
        this.params = new HashMap<>();
    }
    public TransformationData(String type) {
        this();
        this.type = type;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public Map<String, Object> getParams() {
        return params;
    }
    public void setParams(Map<String, Object> params) {
        this.params = params;
    }
}