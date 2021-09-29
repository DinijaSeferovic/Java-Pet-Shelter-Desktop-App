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

public class AdminCatsController implements Initializable {
    @FXML
    private ScrollPane scrollAdminCats;

    @FXML
    private GridPane gridAdminCats;

    @FXML
    private GridPane mainGridAdminCats;

    public AdminCatsController(){ }

    private List<PetDAO> cats = new ArrayList<>();
    private ItemButtonListener myListener;

    private PetDAO pet = PetDAO.getInstance();

    @Override
    public void initialize(URL location, ResourceBundle resources)  {

        cats.addAll(pet.getAllCats());

        if (cats.size() > 0) {
            myListener = new ItemButtonListener() {

                @Override
                public void onClickListener(PetDAO c) {
                    pet.deletePet(c);
                    gridAdminCats.getChildren().remove(cats.indexOf(c));
                    cats.remove(c);
                }
                @Override
                public void onClickListener(User user) {}
            };
        }

        int column = 0;
        int row = 1;

        try {
            for (int i = 0; i < cats.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/fxml/userpetitem.fxml"));
                VBox Pane = fxmlLoader.load();

                ItemController itemController = fxmlLoader.getController();
                itemController.setPetData(cats.get(i), myListener);

                if (column == 1) {
                    column = 0;
                    row++;
                }

                gridAdminCats.add(Pane, column++, row); //(child,column,row)
                //set grid width
                gridAdminCats.setMinWidth(Region.USE_COMPUTED_SIZE);
                gridAdminCats.setPrefWidth(570);
                gridAdminCats.setMaxWidth(Region.USE_PREF_SIZE);

                //set grid height
                gridAdminCats.setMinHeight(Region.USE_COMPUTED_SIZE);
                gridAdminCats.setPrefHeight(Region.USE_COMPUTED_SIZE);
                gridAdminCats.setMaxHeight(Region.USE_PREF_SIZE);

                GridPane.setMargin(Pane, new Insets(10));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addCatsAction(ActionEvent actionEvent) {
        try {
            Stage newStage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/addpetscreen.fxml"));
            loader.setController(new AddFormContoller("cat"));
            Parent root = loader.load();
            newStage.setTitle("Add a cat");
            newStage.setScene(new Scene(root, 300, 350));
            newStage.setResizable(false);
            newStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
