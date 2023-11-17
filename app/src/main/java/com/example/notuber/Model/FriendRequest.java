package com.example.notuber.Model;

public class FriendRequest {
    private String email;
    private String friend;

    // Constructor, getters, and setters
    public FriendRequest(String email, String friend) {
        this.email = email;
        this.friend = friend;
    }

    public String getEmail(){ return email;}
    public String getFriend(){ return friend;}
    public void setEmail(String newEmail){ this.email = newEmail;}
    public void setFriend(String newFriend){ this.friend = newFriend;}
}
