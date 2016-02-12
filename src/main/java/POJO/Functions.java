package POJO;

import javafx.beans.property.SimpleStringProperty;

/**
 * Created by root on 11/13/15.
 */
public class Functions {
    private final SimpleStringProperty number;
    private final SimpleStringProperty function;

    public Functions(String number, String function) {
        this.number = new SimpleStringProperty(number);
        this.function = new SimpleStringProperty(function);
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

    public String getFunction() {
        return function.get();
    }

    public SimpleStringProperty functionProperty() {
        return function;
    }

    public void setFunction(String function) {
        this.function.set(function);
    }
}
