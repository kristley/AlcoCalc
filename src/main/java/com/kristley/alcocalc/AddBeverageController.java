package com.kristley.alcocalc;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AddBeverageController {
    @FXML
    public TextField volumeField, percentageField, beverageNameField;
    @FXML
    public ColorPicker colorPickerField;
    @FXML
    public Text volumeSuffix, timeBox;
    @FXML
    public Button addButton;

    ObservableList<String> unitChoicesList = FXCollections.observableArrayList("ml", "cl", "dl", "l");
    @FXML
    public ChoiceBox unitChoiceBox;
    private AddBeverageModel model = new AddBeverageModel();
    private String timeStamp;

    private void OnInputFieldsChanged(ObservableValue<? extends String> observable, String oldValue, String newValue) {
        validateInput();
    }

    private void validateInput() {
        if (!hasValidInput()){
            //Todo add button dysfunctional
            addButton.setOpacity(0.5);
            return;
        }
        addButton.setOpacity(1);
    }

    private boolean hasValidInput() {
        try {
            double d = Double.parseDouble(volumeField.getText());
            double d2 = Double.parseDouble(percentageField.getText());

        } catch (NumberFormatException e) {
            return false;
        }

        if (beverageNameField.getText().length() < 1)
            return false;
        if (colorPickerField.getValue() == Color.BLACK)
            return false;
        return true;
    }

    @FXML
    private void initialize(){
        unitChoiceBox.setItems(unitChoicesList);
        unitChoiceBox.setValue("l");
        unitChoiceBox.setOnAction(actionEvent -> updatePromptText());



        timeBox.setText(getCurrentTime());

        percentageField.textProperty().addListener(this::OnInputFieldsChanged);
        beverageNameField.textProperty().addListener(this::OnInputFieldsChanged);
        volumeField.textProperty().addListener(this::OnInputFieldsChanged);

        updatePromptText();
        validateInput();
    }

    private void updatePromptText() {
        volumeSuffix.setText((String) unitChoiceBox.getValue());
    }

    private String getCurrentTime() {

        LocalDateTime now = LocalDateTime.now();
        timeStamp = DateTimeHelper.timeStringFromDate(now);
        return now.format(DateTimeFormatter.ofPattern("HH:mm"));
    }

    @FXML
    protected void onBackButtonClicked(){
        SceneManager.loadSceneFromResource("alcoCalc-view.fxml");
    }

    @FXML
    public void onAddBeverage() {
        if(!hasValidInput()){
            return;
        }
        Beverage beverage = GetBeverageFromFields();
        model.add(beverage, timeStamp);
        SceneManager.loadSceneFromResource("alcoCalc-view.fxml");
    }

    private Beverage GetBeverageFromFields() {
        String hex8 = Integer.toHexString(colorPickerField.getValue().hashCode());
        String color = "#" + hex8.substring(0, 6);

        String beverageName = beverageNameField.getText();
        Double volume = Double.parseDouble(volumeField.getText());
        Double percentage = Double.parseDouble(percentageField.getText());
        String suffix = volumeSuffix.getText();
        return new Beverage(beverageName, volume, suffix, percentage, color);
    }

    public void onFillInFromPrefab(MouseEvent mouseEvent) {
        Pane pane = (Pane) mouseEvent.getSource();
        Text bevName = (Text) getByUserData(pane, "0");
        Text volume = (Text) getByUserData(pane, "1");
        Text percentage = (Text) getByUserData(pane, "2");
        Circle color = (Circle)getByUserData(pane, "3");
        Text suffix = (Text) getByUserData(pane, "4");

        beverageNameField.setText(bevName.getText());
        volumeField.setText(volume.getText());
        percentageField.setText(percentage.getText());
        colorPickerField.setValue((Color) color.getFill());
        unitChoiceBox.setValue(suffix.getText());
    }

    private Node getByUserData(Pane parent, String data) {
        for (Node n : parent.getChildren()) {
            if (data.equals(n.getUserData())) {
                return n;
            }
        }
        return null;
    }
}
