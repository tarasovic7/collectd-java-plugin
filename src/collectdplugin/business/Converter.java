/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package collectdplugin.business;

import collectdplugin.database.Data;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import org.collectd.api.DataSource;

/**
 *
 * @author martin
 */
public class Converter {

    private List<Data> entries;
    private final int typeOfEntries;
    private final String source;

    public Converter(List<Data> entries, int typeOfData, String source) {
        Objects.requireNonNull(entries, "list with entries can't be null");
        Objects.requireNonNull(source, "source can't be null");
        this.typeOfEntries = typeOfData;
        for (Data item : entries) {
            requireTypeAndNotNull(item, typeOfData);
        }
        this.entries = entries;
        this.source = source;
    }

    private static void requireTypeAndNotNull(Data entry, int requiredType) {
        Objects.requireNonNull(entry, "entry can't be null");
        if (entry.valueType != requiredType) {
            throw new IllegalArgumentException("Incosistent type of data! reqquired:" + requiredType + "but was:" + entry.valueType);
        }
    }

    public void addEntry(Data entry) {
        requireTypeAndNotNull(entry, typeOfEntries);
        entries.add(entry);
    }

    public int getTypeOfEntries() {
        return typeOfEntries;
    }

    public String getSource() {
        return source;
    }

    public List<Data> getEntries() {
        return Collections.unmodifiableList(entries);
    }

    public List<ConvertedDataEntry> getDifferencedValues() {
               
        switch (typeOfEntries) {
            case DataSource.TYPE_ABSOLUTE:
                return ConversionAlgorithms.convertAbsolute(entries);
            case DataSource.TYPE_COUNTER:
                return ConversionAlgorithms.convertCounter(entries);
            case DataSource.TYPE_DERIVE:
               //return ConversionAlgorithms.convertDerived(entries);
               return ConversionAlgorithms.convertGauge(entries);
            case DataSource.TYPE_GAUGE:
              //  return ConversionAlgorithms.convertGauge(entries);
              //  return ConversionAlgorithms.convertGauge(entries);
                return ConversionAlgorithms.convertDerived(entries);
            default:
                throw new IllegalStateException("Unknown type of entry");
        }
    }
}
