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

public class AddBeverageController {
    @FXML
    private TextField volumeField, percentageField, beverageNameField;
    @FXML
    private ColorPicker colorPickerField;
    @FXML
    private Text volumeSuffix, timeBox;
    @FXML
    private Button addButton;

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
            addButton.setOpacity(0.5);
            return;
        }
        addButton.setOpacity(1);
    }

    private boolean hasValidInput() {
        return model.hasValidInput(beverageNameField.getText(), volumeField.getText(), percentageField.getText());
    }


    @FXML
    private void initialize(){
        unitChoiceBox.setItems(unitChoicesList);
        unitChoiceBox.setValue("l");
        unitChoiceBox.setOnAction(actionEvent -> updatePromptText());

        initTimeBox();

        setListeners();

        updatePromptText();
        validateInput();
    }

    private void initTimeBox() {
        LocalDateTime now = LocalDateTime.now();
        timeStamp = DateTimeHelper.timeStringFromDate(now);
        timeBox.setText(DateTimeHelper.getTime(now));
    }

    private void setListeners() {
        percentageField.textProperty().addListener(this::OnInputFieldsChanged);
        beverageNameField.textProperty().addListener(this::OnInputFieldsChanged);
        volumeField.textProperty().addListener(this::OnInputFieldsChanged);
    }

    private void updatePromptText() {
        volumeSuffix.setText((String) unitChoiceBox.getValue());
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
    }

    private Beverage GetBeverageFromFields() {
        return model.GetBeverageFromFields(beverageNameField.getText(),
                volumeField.getText(),
                (String)unitChoiceBox.getValue(),
                percentageField.getText(),
                colorPickerField.getValue().hashCode());
    }

    @FXML
    protected void onFillInFromPrefab(MouseEvent mouseEvent) {
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
