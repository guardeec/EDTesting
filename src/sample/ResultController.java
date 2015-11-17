package sample;

import POJO.Result;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by root on 11/17/15.
 */
public class ResultController implements Initializable {

    @FXML
    TextFlow resultTF;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        List<Result> results = Controller.resultList;
        resultTF.getChildren().remove(0,resultTF.getChildren().size());
        for (Result r : results){
            String message = "ID: " + r.getAttackId() + "\n" + "Attack: " + r.getAttackTitle()+ "\n\n";
            resultTF.getChildren().add(new Text(message));
        }
    }
}
