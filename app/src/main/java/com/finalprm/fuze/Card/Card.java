package com.finalprm.fuze.Card;

//import lombok.Builder;
//import lombok.Getter;
//import lombok.Setter;
//import lombok.ToString;
//
//@Getter
//@Setter
//@ToString
//@Builder
public class Card {
    private String userId;
    private String name;
    private String age;
    private String bio;
    private String profileImageUrl;
    private String gender;
    private String favorite;


    public Card() {
    }

    public Card(String userId, String name, String profileImageUrl) {
        this.userId = userId;
        this.name = name;
        this.profileImageUrl = profileImageUrl;
    }

    public Card(String userId, String name, String age, String bio, String profileImageUrl, String gender, String favorite) {
        this.userId = userId;
        this.name = name;
        this.age = age;
        this.bio = bio;
        this.profileImageUrl = profileImageUrl;
        this.gender = gender;
        this.favorite = favorite;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getFavorite() {
        return favorite;
    }

    public void setFavorite(String favorite) {
        this.favorite = favorite;
    }

    @Override
    public String toString() {
        return "Card{" +
                "userId='" + userId + '\'' +
                ", name='" + name + '\'' +
                ", age='" + age + '\'' +
                ", bio='" + bio + '\'' +
                ", profileImageUrl='" + profileImageUrl + '\'' +
                ", gender='" + gender + '\'' +
                ", favorite='" + favorite + '\'' +
                '}';
    }
}

