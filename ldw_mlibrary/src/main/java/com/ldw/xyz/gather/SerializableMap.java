package com.ldw.xyz.gather;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by LDW10000000 on 07/03/2017.
 */

public class SerializableMap implements Serializable {
    private Map<Object,Object> map;
    public Map<Object,Object> getMap()
    {
        return map;
    }
    public void setMap(Map<Object,Object> map)
    {
        this.map=map;
    }
}
