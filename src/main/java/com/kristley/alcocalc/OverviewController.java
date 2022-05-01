package com.kristley.alcocalc;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import java.util.List;

public class OverviewController {

    @FXML
    protected Pane backButton;
    @FXML
    protected Pane forwardButton;
    @FXML
    protected Button addButton;
    @FXML
    protected TableView<Drink> historyTable;
    @FXML
    protected TableColumn<Drink, String> colTime;
    @FXML
    protected TableColumn<Drink, String>colBeverage;
    @FXML
    protected TableColumn<Drink, Double> colVolume;
    @FXML
    protected TableColumn<Drink, Double> colPercentage;
    @FXML
    protected TableColumn<Drink, String> colColor;
    @FXML
    protected Text absoluteVolBox, drinkingTimeBox;

    @FXML
    protected Text dateText;

    private OverviewModel model;


    @FXML
    private void initialize(){
        model = new OverviewModel();
        setUpTableFactories();
        model.setUpNight();
        fillInData();
    }


    private void fillInData() {
        fillTable();
        fillOverview();
        toggleAddButtonVisibility(!model.nightIsTonight());
        toggleBackAndForwardVisibility();
        updateDateText();
    }

    private void toggleBackAndForwardVisibility() {
        backButton.setOpacity(model.canGoBack() ? 1 : 0);
        forwardButton.setOpacity(model.canGoForward() ? 1 : 0);
    }

    private void updateDateText() {
        dateText.setText(model.getCurrentDate());
    }

    private void toggleAddButtonVisibility(boolean b) {
        addButton.setOpacity(b ? 0 : 1);
    }

    private void fillOverview() {
        Night night = model.getNight();
        List<SerializableDrink> drinks = night.getSerializableDrinks();
        absoluteVolBox.setText(model.calculateAbsoluteVolumeString(drinks));
        drinkingTimeBox.setText(model.calculateDrinkingTime(drinks, night));
    }

    private void fillTable() {
        ObservableList<Drink> drinks = FXCollections.observableArrayList(model.getDrinks());
        historyTable.setItems(drinks);
    }

    private void setUpTableFactories() {
        colTime.setCellValueFactory(new PropertyValueFactory<>("Time"));
        colBeverage.setCellValueFactory(new PropertyValueFactory<>("BeverageName"));
        colVolume.setCellValueFactory(new PropertyValueFactory<>("Volume"));
        colPercentage.setCellValueFactory(new PropertyValueFactory<>("Percentage"));
        colColor.setCellValueFactory(new PropertyValueFactory<>("Color"));
        colColor.setCellFactory(param -> new ColoredCell());
    }
    @FXML
    protected void onAddButtonClicked(){
        model.tryLoadAddMenu();
    }


    @FXML
    protected void onBackButtonPressed() {
        if(!model.canGoBack()){
            return;
        }
        model.goBack();
        fillInData();
    }

    @FXML
    protected void onForwardButtonPressed() {
        if(!model.canGoForward()){
            return;
        }
        model.goForward();
        fillInData();
    }
}

