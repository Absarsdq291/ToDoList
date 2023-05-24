/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package todolist;

import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

/**
 *
 * @author Pavilion
 */
public class ToDoListController implements Initializable{
    
    @Override
    public void initialize(URL url, ResourceBundle rb){
        System.out.println("HI");
    }
    @FXML
    private Button addButton;

    @FXML
    private TextField descriptionTextField;

    @FXML
    private ListView<String> eventList;
    
    @FXML
    private void addEvent(ActionEvent event) {
//        list.add(new Event(descriptionTextField.getText()));
//        eventList.setItems(list);
        eventList.getItems().add("-> "+descriptionTextField.getText());
    }
    
    @FXML
    private void removeEvent(ActionEvent event) {
        int selectedID = eventList.getSelectionModel().getSelectedIndex();
        eventList.getItems().remove(selectedID);
    }
    
    @FXML
    private void updateEvent(ActionEvent event){
        int selectedID = eventList.getSelectionModel().getSelectedIndex();
        eventList.getItems().remove(selectedID);
        eventList.getItems().add(selectedID, "-> "+descriptionTextField.getText());
    }
    
//    @FXML
//    private void updateTextField(MouseEvent event){
//        String selectedItem = eventList.getSelectionModel().getSelectedItem().toString();
//        descriptionTextField.setText(selectedItem);
//    }
    
    @FXML
    private void saveListViewDataToFile(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save List");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
        File file = fileChooser.showSaveDialog(eventList.getScene().getWindow());

        if (file != null) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                ObservableList<String> items = eventList.getItems();
                for (String item : items) {
                    writer.write(item);
                    writer.newLine();
                }
            } catch (IOException e) {
                System.out.println("Exception");
            }
        }
    }
    
    @FXML
    private void openEvent(ActionEvent event){
      FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
        File file = fileChooser.showOpenDialog(eventList.getScene().getWindow());

        if (file != null) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                ObservableList<String> lines = FXCollections.observableArrayList();
                String line;
                while ((line = reader.readLine()) != null) {
                    lines.add(line);
                }
                eventList.setItems(lines);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

