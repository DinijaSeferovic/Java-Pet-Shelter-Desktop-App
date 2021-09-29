package ba.unsa.etf.controllers;

import ba.unsa.etf.ItemButtonListener;
import ba.unsa.etf.dal.Pet;
import ba.unsa.etf.dal.PetDAO;
import ba.unsa.etf.dal.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class LikedController implements Initializable {
    @FXML
    private ScrollPane scrollLiked;

    @FXML
    private GridPane gridLiked;

    @FXML
    private GridPane mainGridLiked;

    private List<PetDAO> pets = new ArrayList<>();
    private ItemButtonListener myListener;

    private PetDAO pet = PetDAO.getInstance();

    private void deleteLikedPet(Pet pet) {
        this.pet.deleteLikedPet(pets.stream().filter(d -> d.getId()==pet.getId()).findFirst().get());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources)  {

        pets.addAll(pet.getLikedPets());

        if (pets.size() > 0) {
            myListener = new ItemButtonListener() {
                @Override
                public void onClickListener(PetDAO pet) {
                    deleteLikedPet(pet);
                    gridLiked.getChildren().remove(pets.indexOf(pet));
                    pets.remove(pet);
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
                fxmlLoader.setLocation(getClass().getResource("/fxml/likeditem.fxml"));
                VBox Pane = fxmlLoader.load();

                ItemController itemController = fxmlLoader.getController();
                itemController.setPetData(pets.get(i), myListener);

                if (column == 1) {
                    column = 0;
                    row++;
                }

                gridLiked.add(Pane, column++, row); //(child,column,row)
                //set grid width
                gridLiked.setMinWidth(Region.USE_COMPUTED_SIZE);
                gridLiked.setPrefWidth(570);
                gridLiked.setMaxWidth(Region.USE_PREF_SIZE);

                //set grid height
                gridLiked.setMinHeight(Region.USE_COMPUTED_SIZE);
                gridLiked.setPrefHeight(Region.USE_COMPUTED_SIZE);
                gridLiked.setMaxHeight(Region.USE_PREF_SIZE);

                GridPane.setMargin(Pane, new Insets(10));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
