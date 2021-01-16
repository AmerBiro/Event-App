package dk.events.a6.mvvm.model;

import android.widget.EditText;

public class UserModel {

    @Deprecated
    private String userId;
    private String first_name, last_name, date_of_birth, email, gender;
    private String  address, education,  job, description;
    private String image_url_account, image_url_images;


    public UserModel(String userId, String first_name, String last_name, String date_of_birth, String email, String gender, String address, String education, String job, String description, String image_url_account, String image_url_images) {
        this.userId = userId;
        this.first_name = first_name;
        this.last_name = last_name;
        this.date_of_birth = date_of_birth;
        this.email = email;
        this.gender = gender;
        this.address = address;
        this.education = education;
        this.job = job;
        this.description = description;
        this.image_url_account = image_url_account;
        this.image_url_images = image_url_images;
    }

    public UserModel() {

    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getDate_of_birth() {
        return date_of_birth;
    }

    public void setDate_of_birth(String date_of_birth) {
        this.date_of_birth = date_of_birth;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage_url_account() {
        return image_url_account;
    }

    public void setImage_url_account(String image_url_account) {
        this.image_url_account = image_url_account;
    }

    public String getImage_url_images() {
        return image_url_images;
    }

    public void setImage_url_images(String image_url_images) {
        this.image_url_images = image_url_images;
    }
}
