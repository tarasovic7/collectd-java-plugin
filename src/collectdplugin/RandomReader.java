/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package collectdplugin;

import collectdplugin.business.ConvertedDataEntry;
import collectdplugin.business.Converter;
import collectdplugin.database.Data;
import collectdplugin.database.Database;
import collectdplugin.view.CollectdApp;
import java.util.Map;
import org.collectd.api.Collectd;
import org.collectd.api.CollectdWriteInterface;
import org.collectd.api.DataSource;
import org.collectd.api.ValueList;

/**
 *
 * @author martin
 */
public class RandomReader implements CollectdWriteInterface {

    private CollectdApp gui = new CollectdApp();
    private static Database db = new Database();

    public RandomReader() {
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                gui.setVisible(true);
            }
        });
        Collectd.registerWrite("randomReader", this);
    }

    @Override
    public int write(ValueList vl) {
        toDb(vl);
        final String forUser = compileDataForGui();
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                gui.printCountedData(forUser);
            }
        });

        return 0;
    }

    private synchronized void toDb(ValueList vl) {

        int itemsCount = vl.getDataSet().getDataSources().size();
        for (int i = 0; i < itemsCount; i++) {
            DataSource ds = vl.getDataSet().getDataSources().get(i);
            Number number = vl.getValues().get(i);
            db.registerNewValue(vl.getSource(), new Data(number.longValue(), vl.getTime(), ds.getType()));
        }
    }

    private String compileDataForGui() {
        StringBuilder result = new StringBuilder(2000);

        result.append(printMap(db.getAbsolute()));
        result.append(printMap(db.getCounter()));
        result.append(printMap(db.getDerived()));
        result.append(printMap(db.getGauge()));


        return result.toString();
    }

    private String printMap(Map<String, Converter> map) {
        StringBuilder result = new StringBuilder(500);
        result.append("-----------------begin--------------\n");
        for (String key : map.keySet()) {
            result.append("Source:");
            result.append(key);
            result.append("\nValues: ");
            try {
                for (ConvertedDataEntry entry : map.get(key).getDifferencedValues()) {
                    result.append(" ");
                    result.append(entry.getValueDifference());
                }
            } catch (IllegalArgumentException | IllegalStateException ex) {
                result.append(ex.getMessage());
            }
            result.append("\n");
        }
        result.append("-----------------end--------------\n");
        return result.toString();
    }
}
