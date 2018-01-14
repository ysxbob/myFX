package org.fx.utils;

import com.sun.tools.internal.xjc.reader.xmlschema.bindinfo.BIConversion;
import javafx.collections.ObservableList;
import org.fx.model.Task;

import java.io.*;
import java.util.HashMap;

public class FileUtils {
    private static HashMap<String, Object> dataMap;

    static {
        try {
            ObjectInputStream is = new ObjectInputStream(new FileInputStream(
                    "job.data"));
            dataMap = (HashMap<String, Object>) is.readObject();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        if (null == dataMap) {
            dataMap = new HashMap<String, Object>();
        }
    }

    public static void saveData(ObservableList<Task> data) {
        try {
            ObjectOutputStream os = new ObjectOutputStream(
                    new FileOutputStream("job.data"));
            dataMap.put("data", data);
            os.writeObject(dataMap);
            os.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ObservableList<Task> getData() {
        return (ObservableList<Task>) dataMap.get("data");
    }
}
