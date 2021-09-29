package ba.unsa.etf.dal;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.stream.Collectors;

public class PetDAO extends Pet{

    private Connection con = null;
    private static PetDAO instance = null;
    private PreparedStatement getTypePetsStatement,getUnadoptedTypePetStatement,getLikedPetsStatement,getNewIdLiked,getNewId,insertLikedPetStatement,
            insertDogStatement,insertCatStatement,getPetStatement,deleteLikedPetStatement,deletePetStatement,updateAdoptionStatement,updateUrgentStatement,getPetsStatement;
    private static UserDAO user;
    private PetDAO() {

        try {
            con = UserDAO.getConnection();
            getPetsStatement =  con.prepareStatement("SELECT * FROM pets");
            getTypePetsStatement =  con.prepareStatement("SELECT * FROM pets WHERE type = ?");
            getUnadoptedTypePetStatement =  con.prepareStatement("SELECT * FROM pets WHERE adopted = 0 AND type = ?");
            getLikedPetsStatement =  con.prepareStatement("SELECT petid FROM likedpets WHERE userid=?");
            getPetStatement = con.prepareStatement("SELECT type FROM pets WHERE id=?");
            getNewIdLiked = con.prepareStatement("SELECT MAX(id)+1 FROM likedpets");
            getNewId = con.prepareStatement("SELECT MAX(id)+1 FROM pets");
            insertLikedPetStatement = con.prepareStatement("INSERT INTO likedpets VALUES (?,?,?)");
            insertDogStatement = con.prepareStatement("INSERT INTO pets VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)");
            insertCatStatement = con.prepareStatement("INSERT INTO pets VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)");
            deleteLikedPetStatement = con.prepareStatement("DELETE FROM likedpets WHERE petid = ?");
            deletePetStatement = con.prepareStatement("DELETE FROM pets WHERE id = ?");
            updateAdoptionStatement = con.prepareStatement("UPDATE pets SET adopted = 1 WHERE id = ?");
            updateUrgentStatement = con.prepareStatement("UPDATE pets SET urgent = 1 WHERE id = ?");

        } catch (SQLException e) {
            regenerateDatabase();
            try {
                getPetsStatement =  con.prepareStatement("SELECT * FROM pets");
                getTypePetsStatement =  con.prepareStatement("SELECT * FROM pets WHERE type = ?");
                getUnadoptedTypePetStatement =  con.prepareStatement("SELECT * FROM pets WHERE adopted = 0 AND type = ?");
                getLikedPetsStatement =  con.prepareStatement("SELECT petid FROM likedpets WHERE userid = ?");
                getPetStatement = con.prepareStatement("SELECT type FROM pets WHERE id=?");
                getNewIdLiked = con.prepareStatement("SELECT MAX(id)+1 FROM likedpets");
                getNewId = con.prepareStatement("SELECT MAX(id)+1 FROM pets");
                insertLikedPetStatement = con.prepareStatement("INSERT INTO likedpets VALUES (?,?,?)");
                insertDogStatement = con.prepareStatement("INSERT INTO pets VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)");
                insertCatStatement = con.prepareStatement("INSERT INTO pets VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)");
                deleteLikedPetStatement = con.prepareStatement("DELETE FROM likedpets WHERE petid = ?");
                deletePetStatement = con.prepareStatement("DELETE FROM pets WHERE id = ?");
                updateAdoptionStatement = con.prepareStatement("UPDATE pets SET adopted = 1 WHERE id = ?");
                updateUrgentStatement = con.prepareStatement("UPDATE pets SET urgent = 1 WHERE id = ?");
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
    }

    public PetDAO(int id, String name, String sex, String age, String breed, String imgSrc, double dedicationHours, int peopleTolerance, int yardNeed, int petTolerance, int adopted, int urgent, String type) {
        super(id,name,sex,age,breed,imgSrc,dedicationHours,peopleTolerance,yardNeed,peopleTolerance,adopted,urgent,type);
    }

    private static void initialize() {
        instance = new PetDAO();

    }

    public static PetDAO getInstance() {
        user = UserDAO.getInstance();
        if (instance == null) initialize();
        return instance;
    }

    public ArrayList<PetDAO> getAllPets() {
        ArrayList<PetDAO> list = new ArrayList<>();

        try {

            ResultSet set = getPetsStatement.executeQuery();
            while (set.next()) {
                list.add(new PetDAO(set.getInt(1), set.getString(2), set.getString(3),
                        set.getString(4), set.getString(5), set.getString(6),
                        set.getDouble(7), set.getInt(8), set.getInt(9), set.getInt(10), set.getInt(11), set.getInt(12), set.getString(13)));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public ArrayList<PetDAO> getAllDogs() {
        ArrayList<PetDAO> list = new ArrayList<>();

        try {
            getTypePetsStatement.setString(1, "dog");
            ResultSet set = getTypePetsStatement.executeQuery();
            while (set.next()) {
                list.add(new PetDAO(set.getInt(1), set.getString(2), set.getString(3),
                        set.getString(4), set.getString(5), set.getString(6),
                        set.getDouble(7), set.getInt(8), set.getInt(9), set.getInt(10), set.getInt(11), set.getInt(12), set.getString(13)));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public boolean isPetDog(int id) {
        boolean type=false;
        try {
            getPetStatement.setInt(1, id);
            ResultSet set = getPetStatement.executeQuery();
            while (set.next()) {
                if (set.getString(1).equals("dog")) type=true;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return type;
    }

    public boolean isPetCat(int id) {
        boolean type=false;
        try {
            getPetStatement.setInt(1, id);
            ResultSet set = getPetStatement.executeQuery();
            while (set.next()) {
                if (set.getString(1).equals("cat")) type=true;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return type;
    }

    public ArrayList<PetDAO> getAllCats() {
        ArrayList<PetDAO> list = new ArrayList<>();

        try {
            getTypePetsStatement.setString(1,"cat");
            ResultSet set = getTypePetsStatement.executeQuery();
            while (set.next()) {
                list.add(new PetDAO(set.getInt(1), set.getString(2), set.getString(3),
                        set.getString(4), set.getString(5), set.getString(6),
                        set.getDouble(7), set.getInt(8), set.getInt(9), set.getInt(10), set.getInt(11), set.getInt(12), set.getString(13)));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public ArrayList<PetDAO> getAllUnadoptedDogs() {
        ArrayList<PetDAO> list = new ArrayList<>();

        try {
            getUnadoptedTypePetStatement.setString(1,"dog");
            ResultSet set = getUnadoptedTypePetStatement.executeQuery();
            while (set.next()) {
                    list.add(new PetDAO(set.getInt(1), set.getString(2), set.getString(3),
                            set.getString(4), set.getString(5), set.getString(6),
                            set.getDouble(7), set.getInt(8), set.getInt(9), set.getInt(10), set.getInt(11), set.getInt(12), set.getString(13)));

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public ArrayList<PetDAO> getAllUnadoptedCats() {
        ArrayList<PetDAO> list = new ArrayList<>();

        try {
            getUnadoptedTypePetStatement.setString(1,"cat");
            ResultSet set = getUnadoptedTypePetStatement.executeQuery();
            while (set.next()) {
                    list.add(new PetDAO(set.getInt(1), set.getString(2), set.getString(3),
                            set.getString(4), set.getString(5), set.getString(6),
                            set.getDouble(7), set.getInt(8), set.getInt(9), set.getInt(10), set.getInt(11), set.getInt(12), set.getString(13)));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public ArrayList<PetDAO> getLikedPets() {
        ArrayList<PetDAO> list = new ArrayList<>();

        try {
            getLikedPetsStatement.setInt(1, user.getCurrentUser().getId());
            ResultSet set = getLikedPetsStatement.executeQuery();
            while (set.next()) {
                int petId = set.getInt(1);
                //duplicate pets not allowed
                if (!list.stream().anyMatch(p -> p.getId()==petId)) {
                    list.addAll((ArrayList<PetDAO>) getAllPets().stream().filter(p -> p.getId() == petId).collect(Collectors.toList()));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public void insertLikedPet(PetDAO pet) {
        try {
            ResultSet result = getNewIdLiked.executeQuery();
            result.next();

            insertLikedPetStatement.setInt(1, result.getInt(1) );
            insertLikedPetStatement.setInt(2, pet.getId());
            insertLikedPetStatement.setInt(3, user.getCurrentUser().getId());
            insertLikedPetStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertDog(PetDAO pet) {
        try {
            ResultSet result = getNewId.executeQuery();
            result.next();

            insertDogStatement.setInt(1, result.getInt(1) );
            insertDogStatement.setString(2, pet.getName());
            insertDogStatement.setString(3, pet.getSex());
            insertDogStatement.setString(4, pet.getAge());
            insertDogStatement.setString(5, pet.getBreed());
            insertDogStatement.setString(6, pet.getImgSrc());
            insertDogStatement.setDouble(7, pet.getDedicationHours());
            insertDogStatement.setInt(8, pet.getPeopleTolerance());
            insertDogStatement.setInt(9, pet.getYardNeed());
            insertDogStatement.setInt(10, pet.getPetTolerance());
            insertDogStatement.setInt(11, pet.getAdopted());
            insertDogStatement.setInt(12, pet.getUrgent());
            insertDogStatement.setString(13, "dog");
            insertDogStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertCat(PetDAO pet) {
        try {
            ResultSet result = getNewId.executeQuery();
            result.next();

            insertCatStatement.setInt(1, result.getInt(1) );
            insertCatStatement.setString(2, pet.getName());
            insertCatStatement.setString(3, pet.getSex());
            insertCatStatement.setString(4, pet.getAge());
            insertCatStatement.setString(5, pet.getBreed());
            insertCatStatement.setString(6, pet.getImgSrc());
            insertCatStatement.setDouble(7, pet.getDedicationHours());
            insertCatStatement.setInt(8, pet.getPeopleTolerance());
            insertCatStatement.setInt(9, pet.getYardNeed());
            insertCatStatement.setInt(10, pet.getPetTolerance());
            insertCatStatement.setInt(11, pet.getAdopted());
            insertCatStatement.setInt(12, pet.getUrgent());
            insertCatStatement.setString(13, "cat");
            insertCatStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteLikedPet(PetDAO pet) {
        try {
            deleteLikedPetStatement.setInt(1, pet.getId());
            deleteLikedPetStatement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deletePet(PetDAO pet) {
        try {
            deletePetStatement.setInt(1, pet.getId());
            deletePetStatement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setAdopted (PetDAO pet) {
        try {
            updateAdoptionStatement.setInt(1, pet.getId());
            updateAdoptionStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setUrgent (PetDAO pet) {
        try {
            updateUrgentStatement.setInt(1, pet.getId());
            updateUrgentStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void removeInstance() {
        if (instance == null) return;
        instance.close();
        instance = null;
    }

    public void close() {
        try {
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void regenerateDatabase() {
        Scanner enter = null;
        try {
            enter = new Scanner(new FileInputStream("petshelterdb.db.sql"));
            String sqlQuery = "";
            while (enter.hasNext()) {
                sqlQuery += enter.nextLine();
                if ( sqlQuery.length() > 1 && sqlQuery.charAt( sqlQuery.length()-1 ) == ';') {
                    try {
                        Statement stmt = con.createStatement();
                        stmt.execute(sqlQuery);
                        sqlQuery = "";
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
            enter.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    // For tests, gets database to default state
    public void defaultDatabase() throws SQLException {
        Statement stmt = con.createStatement();
        stmt.executeUpdate("DELETE FROM dogs");
        regenerateDatabase();
    }

}
