package ba.unsa.etf.dal;

public class Pet {
    private int id;
    private String name;
    private String sex;
    private String age;
    private String breed;
    private String imgSrc;
    private double dedicationHours;
    private int peopleTolerance;
    private int yardNeed;
    private int petTolerance;
    private int adopted;
    private int urgent;
    private String type;

    public Pet(int id,String name, String sex, String age, String breed, String imgSrc, double dedicationHours, int peopleTolerance, int yardNeed, int petTolerance, int adopted, int urgent, String type) {
        this.id = id;
        this.name = name;
        this.sex = sex;
        this.age = age;
        this.breed = breed;
        this.imgSrc = imgSrc;
        this.dedicationHours = dedicationHours;
        this.peopleTolerance = peopleTolerance;
        this.yardNeed = yardNeed;
        this.petTolerance = petTolerance;
        this.adopted = adopted;
        this.urgent = urgent;
        this.type = type;
    }

    protected Pet() {
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public String getImgSrc() {
        return imgSrc;
    }

    public void setImgSrc(String imgSrc) {
        this.imgSrc = imgSrc;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getDedicationHours() {
        return dedicationHours;
    }

    public void setDedicationHours(double dedicationHours) {
        this.dedicationHours = dedicationHours;
    }

    public int getPeopleTolerance() {
        return peopleTolerance;
    }

    public void setPeopleTolerance(int peopleTolerance) {
        this.peopleTolerance = peopleTolerance;
    }

    public int getYardNeed() {
        return yardNeed;
    }

    public void setYardNeed(int yardNeed) { this.yardNeed = yardNeed; }

    public int getPetTolerance() {
        return petTolerance;
    }

    public void setPetTolerance(int petTolerance) {
        this.petTolerance = petTolerance;
    }

    public int getAdopted() {
        return adopted;
    }

    public void setAdopted(int adopted) {
        this.adopted = adopted;
    }
    public int getUrgent() { return urgent; }

    public void setUrgent(int urgent) { this.urgent = urgent; }
}
