package org.fx.utils;
import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.reflect.TypeToken;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.fx.model.Task;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileUtils {
    private static HashMap<String, Object> dataMap;
    private static Gson gson = new Gson();

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
            dataMap.put("data", gson.toJson(data,new TypeToken<ObservableList<Task>>(){}.getType()));
            os.writeObject(dataMap);
            os.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ObservableList<Task> getData() {
        String gsonStr = (String) dataMap.get("data");
        ArrayList list =gson.fromJson(gsonStr, new TypeToken<ArrayList>(){}.getType());
        ObservableList<Task> tasks = FXCollections.observableArrayList();
        for (Object obj: list) {
            LinkedTreeMap task = (LinkedTreeMap) obj;
            tasks.add(new Task(task));
        }
        return  tasks;

    }
}
