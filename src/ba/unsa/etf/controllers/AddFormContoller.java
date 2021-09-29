package ba.unsa.etf.controllers;

import ba.unsa.etf.dal.Pet;
import ba.unsa.etf.dal.PetDAO;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class AddFormContoller implements Initializable {

    @FXML
    private TextField addNameField;
    SimpleStringProperty nameProperty = new SimpleStringProperty("");
    @FXML
    private TextField addBreedField;
    SimpleStringProperty breedProperty = new SimpleStringProperty("");
    @FXML
    private TextField addImageField;
    SimpleStringProperty imageProperty = new SimpleStringProperty("");
    @FXML
    private ChoiceBox<String> addSexChoice;
    @FXML
    private ChoiceBox<String> addPeopleChoice;
    @FXML
    private ChoiceBox<String> addPetsChoice;
    @FXML
    private ChoiceBox<String> addYardChoice;
    @FXML
    private Spinner<Double> addDedicationSpinner;
    @FXML
    private Spinner<Integer> addAgeSpinner;
    @FXML
    private Button browseImage;
    @FXML
    private Label infoLabel;

    private PetDAO pet;
    private String type;


    public AddFormContoller(String type) {
        pet = PetDAO.getInstance();
        this.type = type;

    }

    public void addAction(ActionEvent actionEvent) {

        if (addImageField.getText().length() > 0 && addBreedField.getText().length() > 0 && addNameField.getText().length() > 0) {
            if (pet!=null && type.equals("dog")) {
                PetDAO newDog = new PetDAO(0,nameProperty.get(), sexInterpretation(addSexChoice), addAgeSpinner.getValue().toString(), breedProperty.get(), imageProperty.get(), addDedicationSpinner.getValue(), choiceInterpretation(addPeopleChoice), choiceInterpretation(addYardChoice), choiceInterpretation(addPetsChoice), 0, 0, "dog");
                pet.insertDog(newDog);
                infoLabel.setText("The new dog is added");
            }
            else if (pet!=null && type.equals("dog")) {
                PetDAO newCat = new PetDAO(0,nameProperty.get(), sexInterpretation(addSexChoice), addAgeSpinner.getValue().toString(), breedProperty.get(), imageProperty.get(), addDedicationSpinner.getValue(), choiceInterpretation(addPeopleChoice), choiceInterpretation(addYardChoice), choiceInterpretation(addPetsChoice), 0, 0, "cat");
                pet.insertCat(newCat);
                infoLabel.setText("The new cat is added");
            }
        }
        else {
            infoLabel.setText("Please enter the remaining information");
        }
    }

    private int choiceInterpretation(ChoiceBox<String> choice) {
        if (choice.getValue().equals("Yes")) return 1;
        else return 0;
    }

    private String sexInterpretation(ChoiceBox<String> choice) {
        if (choice.getValue().equals("Male")) return "M";
        else return "F";
    }

    public void exitAction(ActionEvent actionEvent) {
        Node n = (Node) actionEvent.getSource();
        Stage stage = (Stage) n.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addSexChoice.getItems().add("Male");
        addSexChoice.getItems().add("Female");
        addPeopleChoice.getItems().add("Yes");
        addPeopleChoice.getItems().add("No");
        addPetsChoice.getItems().add("Yes");
        addPetsChoice.getItems().add("No");
        addYardChoice.getItems().add("Yes");
        addYardChoice.getItems().add("No");
        addNameField.textProperty().bindBidirectional(nameProperty);
        addBreedField.textProperty().bindBidirectional(breedProperty);
        addImageField.textProperty().bindBidirectional(imageProperty);
    }

    public void browseImageAction(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose an image of the pet");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("PNG image", "*.png"),
                new FileChooser.ExtensionFilter("JPEG image", "*.jpg", "*.jpeg")
        );
        File selectedFile = fileChooser.showOpenDialog((Stage) browseImage.getScene().getWindow());
        if (selectedFile != null) {
            String path = selectedFile.getAbsolutePath();
            addImageField.setText(path);
        }
    }
}
