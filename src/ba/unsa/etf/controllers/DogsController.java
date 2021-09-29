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

public class DogsController implements Initializable {
    private PetDAO dao = PetDAO.getInstance();

    @FXML
    private ScrollPane scrollDogs;

    @FXML
    private GridPane gridDogs;

    @FXML
    private GridPane mainGridDogs;

    private List<PetDAO> dogs = new ArrayList<>();
    private ItemButtonListener myListener;

    private void setLikedDog(PetDAO dog) {
        dao.insertLikedPet(dogs.stream().filter(d -> d.getId()==dog.getId()).findFirst().get());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources)  {

        dogs.addAll(dao.getAllUnadoptedDogs());

        if (dogs.size() > 0) {
            myListener = new ItemButtonListener() {
                @Override
                public void onClickListener(PetDAO dog) {
                    setLikedDog(dog);
                }
                @Override
                public void onClickListener(User user) {}
            };
        }

        int column = 0;
        int row = 1;
        try {
            for (int i = 0; i < dogs.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/fxml/griditem.fxml"));
                AnchorPane Pane = fxmlLoader.load();

                ItemController itemController = fxmlLoader.getController();
                itemController.setAllPetsData(dogs.get(i), myListener);

                if (column == 3) {
                    column = 0;
                    row++;
                }

                gridDogs.add(Pane, column++, row); //(child,column,row)
                //set grid width
                gridDogs.setMinWidth(Region.USE_COMPUTED_SIZE);
                gridDogs.setPrefWidth(833);
                gridDogs.setMaxWidth(Region.USE_PREF_SIZE);

                //set grid height
                gridDogs.setMinHeight(Region.USE_COMPUTED_SIZE);
                gridDogs.setPrefHeight(Region.USE_COMPUTED_SIZE);
                gridDogs.setMaxHeight(Region.USE_PREF_SIZE);

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
