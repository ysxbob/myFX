package org.fx.controller;


import com.melloware.jintellitype.HotkeyListener;
import com.melloware.jintellitype.JIntellitype;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyEvent;
import javafx.stage.Screen;
import javafx.util.converter.IntegerStringConverter;
import org.fx.model.ScreenShotWindow;
import org.fx.model.Task;
import org.fx.utils.AuthUtils;
import org.fx.utils.KeyUtils;
import org.fx.utils.TableViewUtils;

import java.awt.*;
//import java.awt.*;
//import java.awt.event.KeyEvent;


public class Controller {
    public Label macLabel;
    public TextField keyText;
    private TableViewUtils viewUtils;
    public TableView<Task> taskView;
    public CheckBox dealyCheck;
    public Button startBtn;
    @FXML
    private TableColumn<Task, String> nameCol, locationCol, deleteCol, addCol;
    @FXML
    private TableColumn<Task, Integer> idCol, xCol, yCol, clickNumberCol, dealyCol;
    @FXML
    private TableColumn<Task, Boolean> enableCol;

    public void startJob(ActionEvent event) {
        if(!TableViewUtils.isClick()){
            if(AuthUtils.auth()){
                if(dealyCheck.isSelected()){

                }else{
                    taskView.setEditable(false);
                    TableViewUtils.startClickJob();
                    startBtn.setText("停止任务");
                    System.out.println("start job");
                }
            }
        }else {
            cancleJob();
        }
    }

    private void cancleJob() {
        TableViewUtils.cancleClickJob();
        taskView.setEditable(true);
        startBtn.setText("开始任务");
    }

    public void addTask(ActionEvent event) {
//        Task task = new Task("1", "test", 400, 500, 3, 500, true);
//        task = new Task();
//        data.add(task);
//        taskView.setItems(TableViewUtils.getDefaultTask());
        taskView.setItems(viewUtils.getDefaultTask());
    }

    public void getMouse(ActionEvent event) {
        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();

        double minX = bounds.getMinX();
        double minY = bounds.getMinY();
        double maxX = bounds.getMaxX();
        double maxY = bounds.getMaxY();
//        text.setText("minX:" + minX + ",minY+" + minY + ",maxX:" + maxX + ",maxY:" + maxY);
//        Group group = new Group();
//        group.getChildren().add(text);
//        Scene scene = new Scene(group,1400,900);
//        FXRobot robot = FXRobotFactory.createRobot(scene);
//        robot.mouseMove((int)minX+400,(int)minY+400);
//        robot.mouseClick();
//        try {
//            Robot robot = new Robot();//创建Robot对象
//            robot.mouseMove((int) minX + 400, (int) minY + 400);
//            robot.mousePress(KeyEvent.BUTTON1_MASK);
//            robot.mouseRelease(KeyEvent.BUTTON1_MASK);
//
//        } catch (AWTException e) {
//            e.printStackTrace();
//        }

    }

    //    public void testClicked(ActionEvent event){
//        text.setText("666666");
//    }
    @FXML
    private void initialize() {
        setHotKey();
        macLabel.setText(AuthUtils.getMacStr());
        if (!"".equals(AuthUtils.getKey())) {
            keyText.setText(AuthUtils.getKey());
        }
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

        xCol.setCellValueFactory(new PropertyValueFactory<>("x"));
        xCol.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));

        yCol.setCellValueFactory(new PropertyValueFactory<>("y"));
        yCol.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));


        clickNumberCol.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        clickNumberCol.setCellValueFactory(new PropertyValueFactory<>("clickNumber"));

        dealyCol.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        dealyCol.setCellValueFactory(new PropertyValueFactory<>("dealy"));

        enableCol.setCellFactory(CheckBoxTableCell.forTableColumn(enableCol));
        enableCol.setCellValueFactory(new PropertyValueFactory<>("enable"));

        locationCol.setCellFactory((col) -> {
            TableCell<Task, String> cell = new TableCell<Task, String>() {

                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    this.setText(null);
                    this.setGraphic(null);
                    if (!empty) {
//                        ImageView delICON = new ImageView(getClass().getResource("delete.png").toString());
//                        Button delBtn = new Button("删除", delICON);
                        Button delBtn = new Button("位置");
                        this.setGraphic(delBtn);
                        delBtn.setOnMouseClicked((me) -> {
                            Task task = this.getTableView().getItems().get(this.getIndex());

                            ScreenShotWindow ssw = null;
                            try {
                                ssw = new ScreenShotWindow(task);
                            } catch (AWTException e) {
                                e.printStackTrace();
                            }
                            ssw.setVisible(true);

                            System.out.println("获取的位置 " + "" + "," + "" + " 的记录");
                        });
                    }
                }

            };
            return cell;
        });

        deleteCol.setCellFactory((col) -> {
            TableCell<Task, String> cell = new TableCell<Task, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    this.setText(null);
                    this.setGraphic(null);
                    if (!empty) {
//                        ImageView delICON = new ImageView(getClass().getResource("delete.png").toString());
//                        Button delBtn = new Button("删除", delICON);
                        Button delBtn = new Button("删除");
                        this.setGraphic(delBtn);
                        delBtn.setOnMouseClicked((me) -> {
                            Task task = this.getTableView().getItems().get(this.getIndex());
                            System.out.println("删除 " + task.getName() + "," + task.getEnable() + " 的记录");
                            if (task.getId() != 1) {
                                TableViewUtils.delTask(task.getId());
                            }
                        });
                    }
                }

            };
            return cell;
        });
        addCol.setCellFactory((col) -> {
            TableCell<Task, String> cell = new TableCell<Task, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    this.setText(null);
                    this.setGraphic(null);
                    if (!empty) {
//                        ImageView delICON = new ImageView(getClass().getResource("delete.png").toString());
//                        Button delBtn = new Button("删除", delICON);
                        Button delBtn = new Button("新增");
                        this.setGraphic(delBtn);
                        delBtn.setOnMouseClicked((me) -> {
                            Task task = this.getTableView().getItems().get(this.getIndex());
                            System.out.println("新 增 " + task.getName() + "," + task.getEnable() + " 的记录");
                            TableViewUtils.addTask(task.getId());
                        });
                    }
                }

            };
            return cell;
        });
        taskView.setItems(viewUtils.getDefaultTask());
    }

    private void setHotKey() {
        JIntellitype.getInstance().registerHotKey(0, JIntellitype.MOD_ALT, (int) 'Q');
        JIntellitype.getInstance().registerHotKey(1, JIntellitype.MOD_ALT, (int) 'E');
        JIntellitype.getInstance().addHotKeyListener(new HotkeyListener() {
            @Override
            public void onHotKey(int markCode) {
                switch (markCode) {
                    case 0:
                        System.out.println("hot key enable");
                        cancleJob();
                        break;
                    case 1:
                        System.exit(0);
                        break;
                }
            }
        });
    }

    public void setMainApp(TableViewUtils viewUtils) {
        this.viewUtils = viewUtils;
        taskView.setItems(viewUtils.getData());
        System.out.println("新增的记录");
    }

    public void keyListener(KeyEvent keyEvent) {
        System.out.println("key code:" + keyEvent.getCharacter());
    }

    public void setKey(ActionEvent event) {
        System.out.println("setkeyEvent");
        AuthUtils.setKey(keyText.getText());
    }
    public void clickCopy(ActionEvent event){

    }

}