package ba.unsa.etf.controllers;

import ba.unsa.etf.dal.PetDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class AddController {

    private PetDAO pet = PetDAO.getInstance();

    public void dogsAdd(ActionEvent actionEvent) {
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

    public void catsAdd(ActionEvent actionEvent) {
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
