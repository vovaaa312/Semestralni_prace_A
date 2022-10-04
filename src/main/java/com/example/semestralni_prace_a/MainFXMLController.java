package com.example.semestralni_prace_a;

import java.net.URL;
import java.util.ResourceBundle;

import VyrobniProces.VyrobniProces;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import Proces.Proces;
import Proces.ProcesManualni;
import Proces.ProcesRoboticky;

import Enum.enumReorg;
import Enum.enumTypProcesu;
import Enum.enumPozice;

public class MainFXMLController {


    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Spinner<Integer> casReorgSlider;

    @FXML
    private Spinner<Integer> casSlider;

    @FXML
    private ListView<Proces> mainListView;


    @FXML
    private Spinner<Integer> pocetOsobSlider;

    @FXML
    private Spinner<Integer> pocetZaznamuSlider;

    @FXML
    private ChoiceBox<enumPozice> poziceChBox;

    @FXML
    private ChoiceBox<enumPozice> pozicePridatDataChBox;


    @FXML
    private ChoiceBox<enumTypProcesu> typProcesuChBox;

    @FXML
    private ChoiceBox<enumReorg> typReorgSlider;

    @FXML
    private Button ulozitDataBTN;

    @FXML
    private Button zpristupniProcesBTN;

    @FXML
    private Button pridatProcesBTN;

    @FXML
    private Button reorganizovatBTN;

    @FXML
    private Button nacistSouborBTN;

    @FXML
    private Button odeberProcesBTN;

    @FXML
    private Button generovatBTN;

    @FXML
    private Button konecBTN;

    VyrobniProces<Proces> vyrobniProces = new VyrobniProces<Proces>();
    Generator<Proces> procesGenerator = new Generator<Proces>();

    private int pocetZaznamu;

    @FXML
    void OnActionGenerovatBTN(ActionEvent event) {
        for (int i = 0; i < pocetZaznamuSlider.getValue(); i++) {
            vyrobniProces.vlozProces(procesGenerator.generateRandomProcess(), enumPozice.POSLEDNI);
        }
    }

    @FXML
    void OnActionKonecBTN(ActionEvent event) {

    }

    @FXML
    void OnActionOdeberProcesBTN(ActionEvent event) {

    }

    @FXML
    void OnActionPridatProcesBTN(ActionEvent event) {

    }

    @FXML
    void OnActionReorganizovatBTN(ActionEvent event) {

    }

    @FXML
    void OnActionUlozitDataBTN(ActionEvent event) {

    }

    @FXML
    void OnActionZpristupniProcesBTN(ActionEvent event) {

    }

    @FXML
    void OnActionacistSouborBTN(ActionEvent event) {

    }

    @FXML
    void initialize() {
        casReorgSlider.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 1));
        casSlider.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 1));
        pocetOsobSlider.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 0));
        pocetZaznamuSlider.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 0));

        poziceChBox.getItems().addAll(enumPozice.values());
        pozicePridatDataChBox.getItems().addAll(enumPozice.values());
        typProcesuChBox.getItems().addAll(enumTypProcesu.values());
        typReorgSlider.getItems().addAll(enumReorg.values());
    }

    public void draw(){
        mainListView.getItems().clear();
       // mainListView.getItems().addAll((String)vyrobniProces);
    }


}
