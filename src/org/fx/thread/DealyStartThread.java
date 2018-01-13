package org.fx.thread;

import javafx.collections.ObservableList;
import org.fx.model.Task;
import org.fx.utils.MouseUtils;
import org.fx.utils.TableViewUtils;

import java.util.Date;

public class DealyStartThread implements Runnable {
    private Date startDate;

    public DealyStartThread(Date startDate) {
        this.startDate = startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    @Override
    public void run() {
        boolean flag = true;
        while (flag){
            if(new Date().getTime() >= startDate.getTime()){
                startClickJob();
                flag = false;
            }
        }
    }
    private void startClickJob(){
        ObservableList<Task> data = TableViewUtils.getData();
        Date start = new Date();
        while (TableViewUtils.isClick()){
            for (int i = 0; i < data.size(); i++) {
                Task task = data.get(i);
                if(task.getEnable()){
                    Date now = new Date();
                    if(TableViewUtils.isClick()) {
                        MouseUtils.clikTarget(task.getX(), task.getY(), task.getClickNumber());
                        try {
                            Thread.sleep(task.getDealy());
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

}
