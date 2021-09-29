package ba.unsa.etf.controllers;

import ba.unsa.etf.ItemButtonListener;
import ba.unsa.etf.dal.PetDAO;
import ba.unsa.etf.dal.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class BothController implements Initializable {
    private PetDAO pet = PetDAO.getInstance();

    @FXML
    private ScrollPane scrollBoth;

    @FXML
    private GridPane gridBoth;

    @FXML
    private GridPane mainGridBoth;

    private List<PetDAO> pets = new ArrayList<>();
    private ItemButtonListener myListener;

    private void setLikedPet(PetDAO p) {
        pet.insertLikedPet(pets.stream().filter(d -> d.getId()==p.getId()).findFirst().get());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources)  {

        pets.addAll(pet.getAllPets());

        if (pets.size() > 0) {
            myListener = new ItemButtonListener() {
                @Override
                public void onClickListener(PetDAO pet) {
                    setLikedPet(pet);
                }
                @Override
                public void onClickListener(User user) {}
            };
        }

        int column = 0;
        int row = 1;
        try {
            for (int i = 0; i < pets.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/fxml/griditem.fxml"));
                AnchorPane Pane = fxmlLoader.load();

                ItemController itemController = fxmlLoader.getController();
                itemController.setAllPetsData(pets.get(i), myListener);

                if (column == 3) {
                    column = 0;
                    row++;
                }

                gridBoth.add(Pane, column++, row); //(child,column,row)
                //set grid width
                gridBoth.setMinWidth(Region.USE_COMPUTED_SIZE);
                gridBoth.setPrefWidth(833);
                gridBoth.setMaxWidth(Region.USE_PREF_SIZE);

                //set grid height
                gridBoth.setMinHeight(Region.USE_COMPUTED_SIZE);
                gridBoth.setPrefHeight(Region.USE_COMPUTED_SIZE);
                gridBoth.setMaxHeight(Region.USE_PREF_SIZE);

                GridPane.setMargin(Pane, new Insets(10));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void goBack(ActionEvent actionEvent) {
        Node n = (Node) actionEvent.getSource();
        Stage stage = (Stage) n.getScene().getWindow();
        stage.close();
    }
}
