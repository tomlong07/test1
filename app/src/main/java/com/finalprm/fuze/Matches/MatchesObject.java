package com.finalprm.fuze.Matches;



import com.finalprm.fuze.Util.User;

import java.util.ArrayList;

public class MatchesObject {
    private String userId;
    private String name;
    private String profileImageUrl;
    private String gender;
    private String favorite;

    private String lastMessage;
    private String lastTimeStamp;
    private String lastSeen;

    private String chatId;
    private ArrayList<User> userObjectArrayList = new ArrayList<>();
    public MatchesObject(String userId, String name, String profileImageUrl, String gender, String favorite, String lastMessage, String lastTimeStamp, String chatId, String lastSeen){

        this.userId = userId;
        this.name = name;
        this.profileImageUrl = profileImageUrl;
        this.gender = gender;
        this.favorite = favorite;
        this.lastMessage = lastMessage;
        this.lastTimeStamp = lastTimeStamp;
        this.chatId = chatId;
        this.lastSeen = lastSeen;
    }

    public ArrayList<User> getUserObjectArrayList() {
        return userObjectArrayList;
    }




    public void addUserToArrayList(User mUser){
        userObjectArrayList.add(mUser);
    }

    public String getLastSeen(){
        return lastSeen;
    }
    public void setLastSeen(String lastSeen){
        this.lastSeen = lastSeen;
    }


    public String getUserId(){
        return userId;
    }
    public String getChatId(){
        return chatId;
    }
    public void setChatId(String chatId){ this.chatId = chatId;}
    public String getGender(){
        return gender;
    }
    public String getFavorite(){
        return favorite;
    }
    public String getLastMessage() {return this.lastMessage;}
    public String getLastTimestamp() {return this.lastTimeStamp;}

    public void setUserID(String userID){
        this.userId = userId;
    }
    public void setFavorite(String need){
        this.favorite = favorite;
    }
    public void setGender(String give){
        this.gender = gender;
    }

    public void setLastMessage(String lastMessage) {this.lastMessage = lastMessage;}
    public void setLastTimeStamp(String lastTimeStamp) {this.lastTimeStamp = lastTimeStamp;}


    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }

    public String getProfileImageUrl(){
        return profileImageUrl;
    }
    public void setProfileImageUrl(String profileImageUrl){
        this.profileImageUrl = profileImageUrl;
    }
}
