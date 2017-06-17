package cn.zhao.bos.vo;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;


public class Function implements Serializable {

    private String id;

    private Function function;

    private String name;

    private String code;

    private String description;

    private String page;

    private String generatemenu;

    private Integer zindex;

    public Function() {
    }

    public Function(String id) {
        super();
        this.id = id;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Function getFunction() {
        return this.function;
    }

    public void setFunction(Function function) {
        this.function = function;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getGeneratemenu() {
        return this.generatemenu;
    }

    public void setGeneratemenu(String generatemenu) {
        this.generatemenu = generatemenu;
    }

    public Integer getZindex() {
        return this.zindex;
    }

    public void setZindex(Integer zindex) {
        this.zindex = zindex;
    }
    
    public String getpId() {
        if(function != null) {
            return function.getId();
        }
        return "0";
    }

}