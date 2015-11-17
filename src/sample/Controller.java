package sample;

import POJO.Functions;
import POJO.Hacker;
import POJO.Result;
import POJO.Rules;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    public static List<Result> resultList = new LinkedList<>();

    ObservableList<Functions> functionsList = FXCollections.observableArrayList();
    ObservableList<Hacker> hackerList = FXCollections.observableArrayList();

    @FXML
    ChoiceBox functionalCB;

    @FXML
    ChoiceBox hackerTypeCB;
    @FXML
    ChoiceBox hackerLevelCB;

    @FXML
    TableView functionalTable;
    @FXML
    TableView hackerTable;

    @FXML
    private void functionalAdd(){
        //получаем номер функционала и его название
        String number = functionalCB.getValue().toString().substring(0, functionalCB.getValue().toString().indexOf(" "));
        String func = functionalCB.getValue().toString().substring(functionalCB.getValue().toString().indexOf(" ")+1);

        //смотрим не заносили ли мы его уже в список
        Boolean dublicate = false;
        Iterator iterator = functionsList.iterator();
        while (iterator.hasNext()){
            Functions f = (Functions) iterator.next();
            //и если нашли - ставим флаг
            if (f.getNumber().compareTo(number)==0){
                dublicate = true;
            }
        }

        //добавляем в таблицу
        if (!dublicate){
            functionsList.add(new Functions(number, func));
            functionalTable.setItems(functionsList);
        }
    }

    @FXML
    private void functionalDel(){
        //получаем номер функционала и его название
        String number = functionalCB.getValue().toString().substring(0, functionalCB.getValue().toString().indexOf(" "));

        //ищем его в таблице
        Iterator iterator = functionsList.iterator();
        while (iterator.hasNext()){
            Functions f = (Functions) iterator.next();
            //и если нашли - удаляем
            if (f.getNumber().compareTo(number)==0){
                functionsList.remove(f);
                break;
            }
        }
        functionalTable.setItems(functionsList);
    }

    @FXML
    private void hackerAdd(){
        try {
            //получаем тип, уровень и номер злоумышленника
            String hackerType = hackerTypeCB.getValue().toString();
            String hackerLevel = hackerLevelCB.getValue().toString().substring(hackerLevelCB.getValue().toString().indexOf(" ")+1);
            String hackerNumber = hackerLevelCB.getValue().toString().substring(0, hackerLevelCB.getValue().toString().indexOf(" "));

            //проверяем добавлен ли такой злоумышленник уже в список
            Boolean dublicate = false;
            Iterator iterator = hackerList.iterator();
            while (iterator.hasNext()){
                Hacker h = (Hacker) iterator.next();
                //и если нашли - ставим флаг
                if (h.getNumber().compareTo(hackerNumber)==0){
                    dublicate = true;
                }
            }

            //если нет, то добавляем
            if (!dublicate){
                hackerList.add(new Hacker(hackerNumber, hackerType, hackerLevel));
                hackerTable.setItems(hackerList);
            }
        } catch (NullPointerException ex){
        }
    }

    @FXML
    private void hackerDel(){
        try {
            String hackerNumber = hackerLevelCB.getValue().toString().substring(0, hackerLevelCB.getValue().toString().indexOf(" "));
            Iterator iterator = hackerList.iterator();
            while (iterator.hasNext()){
                Hacker h = (Hacker) iterator.next();
                if (h.getNumber().compareTo(hackerNumber)==0){
                    hackerList.remove(h);
                    break;
                }
            }
            hackerTable.setItems(hackerList);
        }catch (NullPointerException ex){
        }
    }


    /*
    вычисляет атаки
    открывает списко возможных атак в новом окне
     */
    @FXML
    private void calculate(){
        List<Functions> functionsLinkedList = new LinkedList<>();
        List<Hacker>    hackerLinkedList    = new LinkedList<>();
        List<Rules>     rulesLinkedList     = new LinkedList<>();

        //формируем список функционала
        for (Functions f : functionsList){
            functionsLinkedList.add(f);
        }
        //формируем список атакующих
        for (Hacker h : hackerList){
            hackerLinkedList.add(h);
        }
        //формируем список правил
        try {
            BufferedReader functional = new BufferedReader (new FileReader("test_rules.txt"));
            String line;
            while ((line=functional.readLine())!=null){
                line = line.replaceAll(" ", "");
                line = line.replaceAll("_", " ");
                String id = line.substring(0, line.indexOf("&"));
                line = line.replaceFirst("&", "");
                String functionalId = line.substring(line.indexOf("functionalId=")+13, line.indexOf("&"));
                line = line.replaceFirst("&", "");
                String attackerId = line.substring(line.indexOf("attackerId=")+11, line.indexOf("&"));
                line = line.replaceFirst("&", "");
                String attackId = line.substring(line.indexOf("attackId=")+9, line.indexOf("&"));
                line = line.replaceFirst("&", "");

                List<String> hackerId = new LinkedList<>();
                while (attackerId.length()>0){
                    if (attackerId.length()>0 && !attackerId.contains(",")){
                        hackerId.add(attackerId);
                        break;
                    }
                    String oneId = attackerId.substring(0,attackerId.indexOf(","));
                    attackerId = attackerId.replaceFirst(oneId+",","");
                    hackerId.add(oneId);
                }
                rulesLinkedList.add(new Rules(id,functionalId,hackerId,attackId));
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(0);
        }

        //отсеиваем по типу атакующего
        Iterator<Rules> iterator = rulesLinkedList.iterator();
        while (iterator.hasNext()){
            Rules rule = iterator.next();
            boolean containsHackerType = false;
            for (Hacker h : hackerLinkedList){
                List<String> hackerIdInRule = rule.getHackerId();
                String hackerIdInHackerList = h.getNumber();
                for (String id : hackerIdInRule){
                    if (id.compareTo(hackerIdInHackerList)==0){
                        containsHackerType = true;
                    }
                }
            }
            if (!containsHackerType){
                iterator.remove();
            }
        }

        //отсеиваем по функционалу
        iterator = rulesLinkedList.iterator();
        while (iterator.hasNext()){
            Rules rules = iterator.next();
            boolean containsFunctionType = false;
            for (Functions f : functionsLinkedList){
                if(f.getNumber().compareTo(rules.getFunctionalId())==0){
                    containsFunctionType = true;
                }
            }
            if (!containsFunctionType && rules.getFunctionalId().compareTo("null")!=0){
                iterator.remove();
            }
        }

        //получаем список возможных атак
        try {
            BufferedReader functional = new BufferedReader (new FileReader("list_of_attacks.txt"));
            String line;
            while ((line=functional.readLine())!=null){
                String number = line.substring(0, line.indexOf("&")).replaceAll(" ", "");
                String attack = line.substring(line.indexOf("&")+1);
                for (Rules r : rulesLinkedList){
                    if (r.getAttackId().compareTo(number)==0){
                        r.setAttackName(attack);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(0);
        }

        //добавляем фозможные атаки в список вывода
        for (Rules r : rulesLinkedList){
            resultList.add(new Result(r.getAttackId(), r.getAttackName()));
        }

        //загружаем окно вывода
        FXMLLoader loader = new FXMLLoader();
        Parent root = null;
        try {
            root = (Parent) loader.load(getClass().getResourceAsStream("/sample/result.fxml"));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Result");
            stage.setResizable(false);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //обнуляем список
        resultList = new LinkedList<>();
    }


    /*
    при инициализации подгружаются файлики с атакующими и функционалом
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //инициализируем функционал
        try {
            BufferedReader functional = new BufferedReader (new FileReader("functional_features.txt"));
            String line;
            while ((line=functional.readLine())!=null){
                line = line.replaceAll(" ","");
                line = line.replaceAll("_"," ");
                String number = line.substring(0, line.indexOf("&"));
                line = line.replaceFirst("&", "");
                String func = line.substring(line.indexOf("functional=")+11, line.indexOf("&"));
                functionalCB.getItems().add(number+" "+func);
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(0);
        }

        //инициализируем категории атакующих
        try {
            BufferedReader hacker = new BufferedReader (new FileReader("categories_of_attacker.txt"));
            String line;
            while ((line=hacker.readLine())!=null){
                line = line.replaceAll(" ", "");
                line = line.replaceAll("_", " ");
                line = line.replaceFirst("&", "");
                String type = line.substring(line.indexOf("type=")+5, line.indexOf("&"));
                boolean dublicate = false;
                for (int i=0; i<hackerTypeCB.getItems().size(); i++){
                    String item = (String) hackerTypeCB.getItems().get(i);
                    if (item.compareTo(type)==0) {
                        dublicate = true;
                    }
                }
                if (!dublicate){
                    hackerTypeCB.getItems().add(type);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(0);
        }

        //при выборе типа атакующего подгружаем его уровни
        hackerTypeCB.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                hackerLevelCB.getItems().remove(0,hackerLevelCB.getItems().size());
                try {
                    BufferedReader hacker = new BufferedReader (new FileReader("categories_of_attacker.txt"));
                    String line;
                    while ((line=hacker.readLine())!=null){
                        line = line.replaceAll(" ", "");
                        line = line.replaceAll("_", " ");
                        String id = line.substring(0, line.indexOf("&"));
                        line = line.replaceFirst("&", "");
                        String type = line.substring(line.indexOf("type=") + 5, line.indexOf("&"));
                        line = line.replaceFirst("&", "");
                        String selectedType = hackerTypeCB.getValue().toString();
                        if (type.compareTo(selectedType)==0){
                            String level = line.substring(line.indexOf("level=")+6,line.indexOf("&"));
                            hackerLevelCB.getItems().add(id+" "+level);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    System.exit(0);
                }
            }
        });
    }

}
