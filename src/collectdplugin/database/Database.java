/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package collectdplugin.database;

import collectdplugin.business.Converter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import org.collectd.api.DataSource;

/**
 *
 * @author martin
 */
public class Database {

    private Map<String, Converter> absolute = new HashMap<>();
    private Map<String, Converter> counter = new HashMap<>();
    private Map<String, Converter> derived = new HashMap<>();
    private Map<String, Converter> gauge = new HashMap<>();

   
    public void registerNewValue(String id, Data data) {
        Objects.requireNonNull(id, "id cant be null");
        Objects.requireNonNull(data, "data cant be null");
        switch (data.valueType) {
            case DataSource.TYPE_ABSOLUTE:
                addToMap(absolute, id, data);
                break;
            case DataSource.TYPE_COUNTER:
                addToMap(counter, id, data);
                break;
            case DataSource.TYPE_DERIVE:
                addToMap(derived, id, data);
                break;
            case DataSource.TYPE_GAUGE:
                addToMap(gauge, id, data);
                break;
        }
    }

    private synchronized void addToMap(Map<String, Converter> map, String key, Data value) {
        if (!map.containsKey(key)) {       
            map.put(key,new Converter(new ArrayList<Data>(), value.valueType, key));
        }
        map.get(key).addEntry(value);
    }
    

    public Map<String, Converter> getAbsolute() {
        return absolute;
    }

    public Map<String, Converter> getCounter() {
        return counter;
    }

    public Map<String, Converter> getDerived() {
        return derived;
    }

    public Map<String, Converter> getGauge() {
        return gauge;
    }


}
