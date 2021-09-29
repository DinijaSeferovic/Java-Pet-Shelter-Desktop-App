package ba.unsa.etf.controllers;

import ba.unsa.etf.ItemButtonListener;
import ba.unsa.etf.dal.PetDAO;
import ba.unsa.etf.dal.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class AdminDogsController implements Initializable {
    @FXML
    private ScrollPane scrollAdminDogs;

    @FXML
    private GridPane gridAdminDogs;

    @FXML
    private GridPane mainGridAdminDogs;

    public AdminDogsController(){ }

    private List<PetDAO> dogs = new ArrayList<>();
    private ItemButtonListener myListener;

    private PetDAO pet = PetDAO.getInstance();

    @Override
    public void initialize(URL location, ResourceBundle resources)  {

        dogs.addAll(pet.getAllDogs());

        if (dogs.size() > 0) {
            myListener = new ItemButtonListener() {
                @Override
                public void onClickListener(PetDAO d) {
                    pet.deletePet(d);
                    gridAdminDogs.getChildren().remove(dogs.indexOf(d));
                    dogs.remove(d);
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
                fxmlLoader.setLocation(getClass().getResource("/fxml/userpetitem.fxml"));
                VBox Pane = fxmlLoader.load();

                ItemController itemController = fxmlLoader.getController();
                itemController.setPetData(dogs.get(i), myListener);

                if (column == 1) {
                    column = 0;
                    row++;
                }

                gridAdminDogs.add(Pane, column++, row); //(child,column,row)
                //set grid width
                gridAdminDogs.setMinWidth(Region.USE_COMPUTED_SIZE);
                gridAdminDogs.setPrefWidth(570);
                gridAdminDogs.setMaxWidth(Region.USE_PREF_SIZE);

                //set grid height
                gridAdminDogs.setMinHeight(Region.USE_COMPUTED_SIZE);
                gridAdminDogs.setPrefHeight(Region.USE_COMPUTED_SIZE);
                gridAdminDogs.setMaxHeight(Region.USE_PREF_SIZE);

                GridPane.setMargin(Pane, new Insets(10));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addDogsAction(ActionEvent actionEvent) {
        try {
            Stage newStage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/addpetscreen.fxml"));
            loader.setController(new AddFormContoller("dog"));
            Parent root = loader.load();
            newStage.setTitle("Add a dog");
            newStage.setScene(new Scene(root, 300, 350));
            newStage.setResizable(false);
            newStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
