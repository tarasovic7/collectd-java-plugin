/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package collectdplugin.business;

import collectdplugin.database.Data;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * This class is responsible for conversion between data types All methods in
 * this class are converting values from collectd into human readable format All
 * methods in this class returns difference in count or list of differences
 *
 * @author martin
 */
public class ConversionAlgorithms {

    // 2^32
    private static final long twoExp32 =  new BigDecimal(2).pow(32).longValue();
    // 2^64
    private static final BigDecimal twoExp64 =  new BigDecimal(2).pow(64);

    public static List<ConvertedDataEntry> convertGauge(List<Data> data) {
        Objects.requireNonNull(data, "data can't be null");
        requireSize(data, 2);
        List<ConvertedDataEntry> result = new ArrayList<>(data.size());
        for (int i = 1; i < data.size(); i++) {
            long timeSpan = data.get(i).time - data.get(i - 1).time;
            double valueDifferece = data.get(i).value - data.get(i - 1).value;
            result.add(new ConvertedDataEntry((long) valueDifferece, timeSpan));
        }
        return result;
    }

    /**
     *
     * @param oldData can't be null
     * @param newData can't be null
     * @return see class info
     */
    public static ConvertedDataEntry convertDerived(Data oldData, Data newData) {
        Objects.requireNonNull(oldData, "oldData can't be null");
        Objects.requireNonNull(newData, "newData can't be null");
        long timeDiff = newData.time - oldData.time;
        return new ConvertedDataEntry((long) (timeDiff * newData.value), timeDiff);     
    }

    /**
     *
     * @param data array with values, must have at least 2 items
     * @return see class info
     */
    public static List<ConvertedDataEntry> convertDerived(List<Data> data) {
        Objects.requireNonNull(data, "data can't be null");
        requireSize(data, 2);
        List<ConvertedDataEntry> result = new ArrayList<>(data.size());
        for (int i = 1; i < data.size(); i++) {
            result.add(convertDerived(data.get(i - 1), data.get(i)));
        }
        return result;
    }

    /**
     *
     * @param data cant be null and must have, at least 2 items
     * @return see class info
     */
    public static List<ConvertedDataEntry> convertCounter(List<Data> data) {
        Objects.requireNonNull(data, "data can't be null");
        requireSize(data, 2);
        List<ConvertedDataEntry> result = new ArrayList<>(data.size());
        for (int i = 1; i < data.size(); i++) {
            ConvertedDataEntry tmp = convertCounter(data.get(i - 1), data.get(i), i == 1 ? 0 : result.get(i - 1).getValueDifference());
            result.add(tmp);
        }
        return result;
    }

    /**
     *
     * @param oldData can't be null
     * @param newData can't be null
     * @param oldValue latest counted value
     * @return info is in class description
     */
    public static ConvertedDataEntry convertCounter(Data oldData, Data newData, long oldValue) {
        Objects.requireNonNull(oldData, "oldData can't be null");
        Objects.requireNonNull(newData, "newData can't be null");
        long timeDiff = newData.time - oldData.time;
        long valueDiff;
        if (oldValue > twoExp32) {
            valueDiff = new BigDecimal((long) (timeDiff * newData.value))
                    .add(twoExp64.negate())
                    .longValue();
        } else {
            valueDiff = ((long) (timeDiff * newData.value)) - twoExp32;
        }
        return new ConvertedDataEntry(valueDiff, timeDiff);
    }

    /**
     * Same as derivedDifferecne function
     *
     * @param data
     * @return
     */
    public static List<ConvertedDataEntry> convertAbsolute(List<Data> data) {
        return convertDerived(data);
    }

    private static <T> void requireSize(List<T> data, int size) {
        if (data.size() < size) {
            throw new IllegalArgumentException("It's impossible to compute derived value from less than 2 entries");
        }
    }
}
