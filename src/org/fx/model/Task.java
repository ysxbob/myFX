package org.fx.model;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public final class Task implements java.io.Serializable{
    private final SimpleIntegerProperty id = new SimpleIntegerProperty();
    private final SimpleStringProperty name = new SimpleStringProperty();
    private final SimpleIntegerProperty x = new SimpleIntegerProperty();
    private final SimpleIntegerProperty y = new SimpleIntegerProperty();
    private final SimpleIntegerProperty clickNumber = new SimpleIntegerProperty();
    private final SimpleIntegerProperty dealy = new SimpleIntegerProperty();
    private final SimpleBooleanProperty enable = new SimpleBooleanProperty();

    public Task() {
        setClickNumber(1);
        setDealy(500);
        setEnable(true);
    }

    public Task(int id){
        this();
        setId(id);
    }
    public Task(int id, String name, int x, int y, int clickNumber, int dealy, boolean enable) {
        setId(id);
        setName(name);
        setX(x);
        setY(y);
        setClickNumber(clickNumber);
        setDealy(dealy);
        setEnable(enable);
    }

    public int getId() {
        return id.get();
    }

    public SimpleIntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public int getX() {
        return x.get();
    }

    public SimpleIntegerProperty xProperty() {
        return x;
    }

    public void setX(int x) {
        this.x.set(x);
    }

    public int getY() {
        return y.get();
    }

    public SimpleIntegerProperty yProperty() {
        return y;
    }

    public void setY(int y) {
        this.y.set(y);
    }

    public int getClickNumber() {
        return clickNumber.get();
    }

    public SimpleIntegerProperty clickNumberProperty() {
        return clickNumber;
    }

    public void setClickNumber(int clickNumber) {
        this.clickNumber.set(clickNumber);
    }

    public int getDealy() {
        return dealy.get();
    }

    public SimpleIntegerProperty dealyProperty() {
        return dealy;
    }

    public void setDealy(int dealy) {
        this.dealy.set(dealy);
    }

    public boolean getEnable() {
        return enable.get();
    }

    public SimpleBooleanProperty enableProperty() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable.set(enable);
    }
}
