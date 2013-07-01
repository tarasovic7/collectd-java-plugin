/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package collectdplugin;

import collectdplugin.database.Database;
import collectdplugin.view.CollectdApp;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.collectd.api.Collectd;
import org.collectd.api.CollectdConfigInterface;
import org.collectd.api.CollectdReadInterface;
import org.collectd.api.ValueList;

import org.collectd.api.CollectdWriteInterface;
import org.collectd.api.DataSource;
import org.collectd.api.OConfigItem;

/**
 *
 * @author martin
 */
public class FooBar/* implements CollectdWriteInterface, CollectdReadInterface, CollectdConfigInterface */{

    /*private BufferedWriter writter;
    private CollectiongApp gui = new CollectiongApp();
    private static Database db = new Database();

    public FooBar() {


        File file = new File("/home/martin/Desktop/text.txt");
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            if (!file.canWrite()) {
                throw new Error("cant write to file");
            }
            writter = new BufferedWriter(new FileWriter(file));
        } catch (IOException ex) {
            Logger.getLogger(FooBar.class.getName()).log(Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                gui.setVisible(true);
            }
        });

        Collectd.registerWrite("foobar", this);
        Collectd.registerConfig("foobar", this);
    }

    @Override
    public int read() {
        ValueList vl = new ValueList();

        Collectd.dispatchValues(vl);
        return 0;
    }

    public static void main(String... args) {
        System.out.println("Fools1");
        FooBar foo = new FooBar();
        System.out.println("Fools2");
    }

    @Override
    public int write(ValueList vl) {
        //return writeToFile("write:" + valuesToString(vl));
        // String values = valuesToString(vl);
        toDb(vl);
        plainToGui();
        return 0;
    }

    private int writeToGui(String data) {
        gui.printNewData(data);
        return 0;
    }

    public String valuesToString(ValueList vl) {
        StringBuilder result = new StringBuilder();
        result.append("------------Data set--------------- \n");
        for (DataSource ds : vl.getDataSet().getDataSources()) {
            String[] data = new String[]{
                "---Datasource \n",
                "name:", ds.getName(), "\n",
                "type:", String.valueOf(ds.getType()), "\n",
                "max:", String.valueOf(ds.getMax()), "\n",
                "min", String.valueOf(ds.getMin()), "\n",};
            appendArray(result, data);
        }

        result.append("---Time \n");
        String data[] = new String[]{
            "recived:", new Date(vl.getTime()).toString(), "\n",
            "expected:", new Date(vl.getInterval() + vl.getTime()).toString(), "\n",};
        appendArray(result, data);

        data = new String[]{
            "-Host:", vl.getHost(), "\n",
            "-Plugin:", vl.getPlugin(), "\n",
            "-PluginInstance:", vl.getPluginInstance(), "\n",
            "-Type:", vl.getType(), "\n",
            "-TypeInstance:", vl.getTypeInstance(), "\n",};
        appendArray(result, data);
        result.append("-Datasource:");
        result.append(vl.getSource());
        result.append('\n');
        result.append("numbers");
        Number[] numbers = new Number[10];

        numbers = vl.getValues().toArray(numbers);

        for (Number number : numbers) {
            result.append(number);
            result.append(";");
        }

        result.append('\n');
        result.append("-----------------------------------\n");
        return result.toString();
    }

    //obsolete
  /*  private void toDb(ValueList vl) {

        int itemsCount = vl.getDataSet().getDataSources().size();
        for (int i = 0; i < itemsCount; i++) {
            DataSource ds = vl.getDataSet().getDataSources().get(i);
            Number number = vl.getValues().get(i);
            db.registerNewValue(vl.getSource(), ds.getType(), number);
        }

    }

    private int plainToGui() {
        StringBuilder text = new StringBuilder();
        text.append("-----Measured values-----\n");
        text.append("Absolute:");
        printMap(text, db.getAbsolute());
        text.append("Counter:");
        printMap(text, db.getCounter());
        text.append("Derived:");
        printMap(text, db.getDerived());
        text.append("Gauge:");
        printMap(text, db.getGauge());
        gui.printNewData(text.toString());
        return 0;
    }

    private int convertedToGui() {
        StringBuilder text = new StringBuilder();
        text.append("-----Measured converted values-----\n");
        text.append("Absolute:");
        printMap(text, db.getAbsolute());
        text.append("Counter:");
        printMap(text, db.getCounter());
        text.append("Derived:");
        printMap(text, db.getDerived());
        text.append("Gauge:");
        printMap(text, db.getGauge());
        gui.printNewData(text.toString());
    }

    private void appendArray(StringBuilder builder, String[] array) {
        if (array == null) {
            return;
        }

        for (int i = 0; i < array.length; i++) {
            builder.append(array[i]);
        }
    }

    @Override
    public int config(OConfigItem oci) {
        return writeToFile(oci.toString());

    }

    private int writeToFile(String message) {
        try {
            writter.append(message);
        } catch (IOException ex) {
            return 1;
        }
        return 0;
    }
*/
    private void printMap(StringBuilder text, Map<String, List<Number>> map) {
        for (String data : map.keySet()) {
            text.append(data);
            text.append(":");
            for (Number number : map.get(data)) {
                text.append(number);
                text.append("-");
            }
            text.append("\n");
        }
        text.append("\n");
    }
}
