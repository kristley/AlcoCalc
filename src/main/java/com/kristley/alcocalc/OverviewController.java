package com.kristley.alcocalc;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import java.util.ArrayList;
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
        NightsManager.updateCurrentNightToTonight();
        fillInData();
    }

    private void fillInData() {
        fillTable();
        fillOverview();
        hideAddButton(!NightsManager.currentNightIsToday());
    }

    private void hideAddButton(boolean b) {
        addButton.setOpacity(b ? 0 : 1);
    }

    private void fillOverview() {
        List<SerializableDrink> drinks = NightsManager.getNight().getSerializableDrinks();
        absoluteVolBox.setText(model.calculateAbsoluteVolumeString(drinks));
        drinkingTimeBox.setText(model.calculateDrinkingTime(drinks, NightsManager.getNight()));
    }

    private void fillTable() {
        historyTable.setItems(getDrinks());
    }

    private ObservableList<Drink> getDrinks() {
        List<Drink> drinks = new ArrayList<>();
        for (SerializableDrink serializableDrink : NightsManager.getNight().getSerializableDrinks()) {
            drinks.add(new Drink(serializableDrink));
        }
        return FXCollections.observableArrayList(drinks);
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
        if (NightsManager.currentNightIsToday())
            SceneManager.loadSceneFromResource("addBeverage-view.fxml");
    }


    @FXML
    protected void onBackButtonPressed(MouseEvent mouseEvent) {
        if(!model.canGoBack()){
            return;
        }
        model.goBack();
        fillInData();
    }

    @FXML
    protected void onForwardButtonPressed(MouseEvent mouseEvent) {
        if(!model.canGoForward()){
            //todo hide
            return;
        }
        model.goForward();
        fillInData();
    }
}

