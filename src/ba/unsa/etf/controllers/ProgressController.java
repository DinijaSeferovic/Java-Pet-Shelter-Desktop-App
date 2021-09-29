package ba.unsa.etf.controllers;

import ba.unsa.etf.NoLikedException;
import ba.unsa.etf.SendMail;
import ba.unsa.etf.dal.PetDAO;
import ba.unsa.etf.dal.UserDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class ProgressController implements Initializable {

    private PetDAO pet;
    private UserDAO user;

    @FXML
    private DatePicker appointmentDate;

    @FXML
    private Label firstLabel = new Label();

    private LocalDate pickedDate;


    private SendMail mail;

    @FXML
    public void getDate(ActionEvent event) {
        pickedDate = appointmentDate.getValue();
        user.setDate(pickedDate.toString());
        mail = new SendMail(user.getEmail(user.getCurrentUser().getUsername()), pickedDate.toString());
        Thread thread = new Thread(mail);
        thread.start();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        pet = PetDAO.getInstance();
        user = UserDAO.getInstance();
        if (user.getApproved(user.getCurrentUser().getUsername())==1) {
            firstLabel.setText("You have been approved. Please choose the date to visit us and meet the desired pets");
        }
        else {
            firstLabel.setText("You have not been approved since your lifestyle does not match any of the liked pets");
        }

        try {
            if (pet.getLikedPets().isEmpty()) {
                appointmentDate.setDisable(true);
                throw new NoLikedException("You don't have any liked pets");
            }
            if (user.getApproved(user.getCurrentUser().getUsername())==0) {
                appointmentDate.setDisable(true);
            }
        } catch (NoLikedException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("No Liked Pets Error");
            alert.setHeaderText("You have not liked any pets");
            alert.setContentText("Please like the pets that you want to meet and then pick a date.");

            alert.showAndWait();
        }
    }

}



