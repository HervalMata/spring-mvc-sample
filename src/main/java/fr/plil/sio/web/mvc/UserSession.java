package fr.plil.sio.web.mvc;

import java.io.Serializable;

public class UserSession implements Serializable {

    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
