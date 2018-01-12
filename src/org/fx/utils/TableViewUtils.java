package org.fx.utils;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.fx.model.Task;
import org.fx.thread.DealyStartThread;

import java.util.Date;

public class TableViewUtils {
    private static boolean startFlag = true;
    private static final ObservableList<Task> data
            = FXCollections.observableArrayList();

    public static ObservableList<Task> addTask(int id) {
        Task task = new Task(id);
        data.add(id,task);
        sort(data);
        return data;
    }

    public static ObservableList<Task> delTask(int id) {
        data.remove(id -1);
        sort(data);
        return data;
    }

    public static void getTask() {

    }

    public static ObservableList<Task> getDefaultTask() {
        Task task = new Task(1);
        data.add(task);
        return data;
    }

    public static ObservableList<Task> getData() {
        return data;
    }

    private static void sort(ObservableList<Task> data) {
        for (int i = 0; i < data.size(); i++) {
//            System.out.println("新增 "+data.get(i).getId()+","+ data.get(i).getName() + "," + data.get(i).getEnable() + " 的记录");
            data.get(i).setId(i+1);
        }
    }

    private static void test(ObservableList<Task> data) {
        System.out.println("**********");
        for (int i = 0; i < data.size(); i++) {
            System.out.println("新增 "+"i="+i+"id="+data.get(i).getId()+","+ data.get(i).getName() + "," + data.get(i).getEnable() + " 的记录");
        }
        System.out.println("&&&&&&&&&&");
    }
    public static void startClickJob(){
        startFlag =true;
        DealyStartThread startJob = new DealyStartThread(new Date());
//        Date start = new Date();
//        while (startFlag){
//            for (int i = 0; i < data.size(); i++) {
//                Task task = data.get(i);
//                if(task.getEnable()&&startFlag){
//                    Date now = new Date();
//                    if((now.getTime()-start.getTime())<5*60*1000){
//                        MouseUtils.clikTarget(task.getX(),task.getY(),task.getClickNumber());
//                    }else{
//                        cancleClickJob();
//                    }
//                    try {
//                        Thread.sleep(task.getDealy());
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        }
    }

    public static void dealyStartClickJob(Date startDate){

    }
    public static  boolean isClick(){
        return startFlag;
    }
    public static void cancleClickJob(){
        startFlag = false;
    }


}
