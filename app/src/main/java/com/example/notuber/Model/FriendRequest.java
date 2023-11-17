package com.example.notuber.Model;


/**
 * Clase que representa una solicitud de amistad.
 */
public class FriendRequest {
    private String email;
    private String friend;

    /**
     * Constructor de la clase FriendRequest.
     *
     * @param email  Dirección de correo electrónico del usuario que envía la solicitud.
     * @param friend Dirección de correo electrónico del usuario al que se envía la solicitud.
     */
    public FriendRequest(String email, String friend) {
        this.email = email;
        this.friend = friend;
    }

    public String getEmail(){ return email;}
    public String getFriend(){ return friend;}
    public void setEmail(String newEmail){ this.email = newEmail;}
    public void setFriend(String newFriend){ this.friend = newFriend;}
}
