package com.example.semestralni_prace_a;

import java.net.URL;
import java.util.Iterator;
import java.util.Optional;
import java.util.ResourceBundle;

import VyrobniProces.VyrobniProces;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
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

    VyrobniProces vyrobniProces = new VyrobniProces();
    Generator procesGenerator = new Generator();

    String soubor ="D:\\учёба\\BDATS Datove struktury\\2022-2023\\vypracovane\\Semestralni_prace_A\\Semestralni_prace_A\\src\\main\\java\\com\\example\\semestralni_prace_a\\import.csv";
    @FXML
    void OnActionGenerovatBTN(ActionEvent event) {
        for (int i = 0; i < pocetZaznamuSlider.getValue(); i++)
            vyrobniProces.vlozProces(procesGenerator.generateRandomProcess(), enumPozice.POSLEDNI);
        draw();


    }

    @FXML
    void OnActionKonecBTN(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("Opravdu chcete ukoncit?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) System.exit(0);
    }

    @FXML
    void OnActionOdeberProcesBTN(ActionEvent event) {
        if (poziceChBox.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Zadejte pozice");

            alert.showAndWait();
        } else {
            vyrobniProces.odeberProces(poziceChBox.getValue());
            draw();
        }
    }


    @FXML
    void OnActionPridatProcesBTN(ActionEvent event) {
        int pocetOsob = pocetOsobSlider.getValue();
        int cas = casSlider.getValue();
        enumPozice pozice = pozicePridatDataChBox.getValue();

        if (kontrolaHodnot()) {
            switch (typProcesuChBox.getValue()) {
                case MANUAL -> vyrobniProces.vlozProces(procesGenerator.generateManualProces(pocetOsob, cas), pozice);
                case ROBOT -> vyrobniProces.vlozProces(procesGenerator.generateRobotickyProces(cas), pozice);
            }
            draw();
        }
    }

    public boolean kontrolaHodnot() {
        if (pozicePridatDataChBox.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Zadejte pozice");

            alert.showAndWait();
            return false;
        }
        if (typProcesuChBox.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Zadejte typ procesu");

            alert.showAndWait();
            return false;
        }
        if (typProcesuChBox.getValue() == enumTypProcesu.ROBOT && pocetOsobSlider.getValue() != 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Roboticky proces nemuze mit lidi");

            alert.showAndWait();
            return false;
        }
        if (typProcesuChBox.getValue() == enumTypProcesu.MANUAL && pocetOsobSlider.getValue() == 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Manualni proces musi mit lidi");

            alert.showAndWait();
            return false;
        }

        return true;
    }

    @FXML
    void OnActionReorganizovatBTN(ActionEvent event) {

    }

    @FXML
    void OnActionZpristupniProcesBTN(ActionEvent event) {
        if (poziceChBox.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Zadejte pozice");

            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(poziceChBox.getValue().toString() + " PRVEK");
            alert.setContentText(vyrobniProces.zpristupniProces(poziceChBox.getValue()).toString());

            alert.showAndWait();
        }
    }

    @FXML
    void OnActionacistSouborBTN(ActionEvent event) {
        try {
            vyrobniProces.importDat(soubor);
            draw();
        }catch (Exception ex){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText(ex.getLocalizedMessage());

            alert.showAndWait();
        }
    }

    @FXML
    void OnActionUlozitDataBTN(ActionEvent event) {

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

    public void draw() {
        mainListView.getItems().clear();
        Iterator iterator = vyrobniProces.iterator();
        while (iterator.hasNext()) {
            mainListView.getItems().add((Proces) iterator.next());
        }

    }


}
