package com.example.licentafii2022;

public class AllUsers {
    String name,userId,phone,email,bio,uri;

    /**
     * Clasă folosită pentru a putea salva metadatele unui utilizator în baza de date
     * */
    public AllUsers(String name, String userId, String phone, String email, String bio) {
        this.name = name;
        this.userId = userId;
        this.phone = phone;
        this.email = email;
        this.bio = bio;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
