/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package collectdplugin.business;

/**
 *
 * @author martin
 */
public class ConvertedDataEntry {
    
    private final long valueDifference;
    private final long timeSpan;

    public ConvertedDataEntry(long valueDifference, long timeSpan) {
        this.valueDifference = valueDifference;
        this.timeSpan = timeSpan;
    }
    
    

    public long getValueDifference(){
        return valueDifference;
    }

    public long getTimeSpan(){
        return timeSpan;
    }
}
