package ba.unsa.etf.controllers;

import ba.unsa.etf.ItemButtonListener;
import ba.unsa.etf.dal.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.io.FileInputStream;
import java.io.FileNotFoundException;


public class ItemController {


    @FXML
    private Label nameUserLabel;

    @FXML
    private Label usernameUserLabel;

    @FXML
    private Label passwordUserLabel;

    @FXML
    private Label emailUserLabel;

    @FXML
    private Label workingUserLabel;

    @FXML
    private Label roomatesUserLabel;

    @FXML
    private Label yardUserLabel;

    @FXML
    private Label homeworkUserLabel;

    @FXML
    private Label petsUserLabel;

    @FXML
    private Label dateLabel;

    @FXML
    private Label nameLabel;

    @FXML
    private Label sexLabel;

    @FXML
    private Label breedLabel;

    @FXML
    private Label ageLabel;

    @FXML
    private Label adviceLabel = new Label();

    @FXML
    private Label adoptionLabel = new Label();

    @FXML
    private Label urgentLabel = new Label();

    @FXML
    private ImageView img;

    @FXML
    private Button heartButton;

    @FXML
    private Button unHeartButton;

    @FXML
    private Button deleteButton;


    private PetDAO pet = PetDAO.getInstance();
    private UserDAO userDao = UserDAO.getInstance();
    private User user;
    private ItemButtonListener myListener;

    @FXML
    public void clickHeart(MouseEvent mouseEvent) {
        if (pet!=null) myListener.onClickListener(pet);
        Image heart=new Image("/img/fulllikeicon.png");
        ImageView iv=new ImageView(heart);
        iv.setFitHeight(30);
        iv.setFitWidth(30);
        heartButton.setGraphic(iv);
    }

    @FXML
    public void unclickHeart(MouseEvent mouseEvent) {
        if (pet!=null) myListener.onClickListener(pet);
    }

    @FXML
    public void deleteUser(MouseEvent mouseEvent) {
        myListener.onClickListener(user);
    }

    @FXML
    public void adoptedClick(MouseEvent mouseEvent) {
        if (pet!=null) {
            PetDAO selectedPet = PetDAO.getInstance();
            selectedPet.setAdopted(pet);
        }

        adoptionLabel.setText("Adoption status: Adopted");
    }

    @FXML
    public void urgentClick(MouseEvent mouseEvent) {
        if (pet!=null) {
            PetDAO selectedPet = PetDAO.getInstance();
            selectedPet.setUrgent(pet);
        }
        adoptionLabel.setText(adoptionLabel.getText()+" (urgent)");
    }

    public void setAllPetsData(PetDAO pet, ItemButtonListener myListener) {
        this.pet = pet;
        this.myListener = myListener;
        nameLabel.setText(pet.getName());
        sexLabel.setText("Sex: " + pet.getSex());
        ageLabel.setText("Age: " + pet.getAge());
        breedLabel.setText("Breed: " + pet.getBreed());
        Image image = new Image(getClass().getResourceAsStream(pet.getImgSrc()));
        img.setImage(image);
        if (pet.getUrgent()==1) urgentLabel.setText("URGENT");
    }

    public void setPetData(PetDAO pet, ItemButtonListener myListener) {
        this.pet = pet;
        this.myListener = myListener;
        nameLabel.setText(pet.getName());
        sexLabel.setText("Sex: " + pet.getSex());
        ageLabel.setText("Age: " + pet.getAge());
        breedLabel.setText("Breed: " + pet.getBreed());
        if (getClass().getResourceAsStream(pet.getImgSrc())!=null) {
            Image image = new Image(getClass().getResourceAsStream(pet.getImgSrc()));
            img.setImage(image);
        }
        else {
            try {
                FileInputStream inputstream = new FileInputStream(pet.getImgSrc());
                Image image = new Image(inputstream);
                img.setImage(image);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        if (pet.getAdopted()==1) adoptionLabel.setText("Adoption status: Adopted");
        else adoptionLabel.setText("Adoption status: Not Adopted");
        if (pet.getUrgent()==1) adoptionLabel.setText(adoptionLabel.getText()+ " (urgent)");

        if (userDao.getCurrentUser()!=null) {
            adviceLabel.setText(calculateAdvice(pet));
        }
    }

    private String calculateAdvice(PetDAO pet) {
        if (pet!=null) {
            return petAdvice(pet);
        }
        return "";
    }

    private String petAdvice(Pet pet) {
        String result = "";
        int hoursInADay = 24;
        int avgSleepHours = 8;

        if (userDao.getProfileSet(userDao.getCurrentUser().getUsername())==0) {
            result = "Please complete your profile to get an approval";
        }
        //If a pet needs more dedication than user can give it
        else if (pet.getDedicationHours()> hoursInADay - avgSleepHours - userDao.getCurrentUser().getWorkhours() && userDao.getCurrentUser().getHomework()==0) {
            result = "Disapproved: This pet demands more time in a day than you have freetime";
        }
        else if (pet.getPeopleTolerance()==0 && userDao.getCurrentUser().getRoomates()>1) {
            result = "Disapproved: This pet does not tolerate other people in a household";
        }
        else if (pet.getPetTolerance()==0 && userDao.getCurrentUser().getPets()==1) {
            result = "Disapproved: This pet does not tolerate other pets in a household";
        }
        else if (pet.getYardNeed()==1 && userDao.getCurrentUser().getYard()==0) {
            result = "Disapproved: This pet prefers having a yard";
        }
        else {
            result = "Approved";
            userDao.setApproved(userDao.getCurrentUser());
        }
        return result;
    }


    public String  interpretingData (int data) {
        if (data==1) return "Yes";
        else return "No";
    }

    public void setUserData (User user, ItemButtonListener myListener) {
        this.user = user;
        this.myListener = myListener;
        nameUserLabel.setText("name: " + user.getName());
        usernameUserLabel.setText("username: " + user.getUsername());
        passwordUserLabel.setText("pass: " + user.getPassword());
        emailUserLabel.setText("email: " + user.getEmail());
        workingUserLabel.setText("Working: " + user.getWorkhours() + "h");
        roomatesUserLabel.setText("Roomates: " + interpretingData(user.getRoomates()));
        yardUserLabel.setText("Yard: " + interpretingData(user.getYard()));
        homeworkUserLabel.setText("Work from home: " + interpretingData(user.getHomework()));
        petsUserLabel.setText("Other pets: " + interpretingData(user.getPets()));
        if (user.getAppointment()==null) dateLabel.setText("No appointments");
        else dateLabel.setText("Appointment date: " + user.getAppointment().toString());
    }

}
