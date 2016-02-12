package POJO;

import javafx.beans.property.SimpleStringProperty;

/**
 * Created by root on 11/13/15.
 */
public class Hacker {
    private final SimpleStringProperty number;
    private final SimpleStringProperty type;
    private final SimpleStringProperty level;

    public Hacker(String number, String type, String level) {
        this.number = new SimpleStringProperty(number);
        this.type = new SimpleStringProperty(type);
        this.level = new SimpleStringProperty(level);
    }

    public String getNumber() {
        return number.get();
    }

    public SimpleStringProperty numberProperty() {
        return number;
    }

    public void setNumber(String number) {
        this.number.set(number);
    }

    public String getType() {
        return type.get();
    }

    public SimpleStringProperty typeProperty() {
        return type;
    }

    public void setType(String type) {
        this.type.set(type);
    }

    public String getLevel() {
        return level.get();
    }

    public SimpleStringProperty levelProperty() {
        return level;
    }

    public void setLevel(String level) {
        this.level.set(level);
    }
}
