/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package collectdplugin.database;

/**
 *
 * @author martin
 */
public class Data {

    public Data(double value, long time, int valueType) {
        this.value = value;
        this.time = time;
        this.valueType = valueType;
    }

    
    public final double value;
    public final long time;
    public final int valueType;
    
}
