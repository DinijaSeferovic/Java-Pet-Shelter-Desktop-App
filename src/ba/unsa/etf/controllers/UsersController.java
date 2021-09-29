package ba.unsa.etf.controllers;

import ba.unsa.etf.ItemButtonListener;
import ba.unsa.etf.dal.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class UsersController implements Initializable {
    @FXML
    private ScrollPane scrollUsers;

    @FXML
    private GridPane gridUsers;

    @FXML
    private BorderPane mainPane;

    private List<User> users = new ArrayList<>();
    private ItemButtonListener myListener;

    private UserDAO user = UserDAO.getInstance();

    private void UsersController(){ }

    @Override
    public void initialize(URL location, ResourceBundle resources)  {

        users.addAll(user.getAllUsers());

        if (users.size() > 0) {
            myListener = new ItemButtonListener() {
                @Override
                public void onClickListener(User u) {
                    user.deleteUser(u.getUsername());
                    gridUsers.getChildren().remove(users.indexOf(u));
                    users.remove(u);
                }
                @Override
                public void onClickListener(PetDAO dog) {}
            };
        }

        int column = 0;
        int row = 1;

        try {
            for (int i = 0; i < users.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/fxml/useritem.fxml"));
                VBox Pane = fxmlLoader.load();

                ItemController itemController = fxmlLoader.getController();
                itemController.setUserData(users.get(i), myListener);

                if (column == 1) {
                    column = 0;
                    row++;
                }

                gridUsers.add(Pane, column++, row); //(child,column,row)
                //set grid width
                gridUsers.setMinWidth(Region.USE_COMPUTED_SIZE);
                gridUsers.setPrefWidth(570);
                gridUsers.setMaxWidth(Region.USE_PREF_SIZE);

                //set grid height
                gridUsers.setMinHeight(Region.USE_COMPUTED_SIZE);
                gridUsers.setPrefHeight(Region.USE_COMPUTED_SIZE);
                gridUsers.setMaxHeight(Region.USE_PREF_SIZE);
                GridPane.setMargin(Pane, new Insets(10));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
