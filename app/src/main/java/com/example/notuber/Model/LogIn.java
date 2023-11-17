package com.example.notuber.Model;

/**
 * La clase LogIn representa la información de inicio de sesión con un correo electrónico y una contraseña.
 */
public class LogIn {
    private String email;
    private String password;

    /**
     * Constructor que inicializa la clase LogIn con un correo electrónico y una contraseña.
     *
     * @param email    El correo electrónico asociado al inicio de sesión.
     * @param password La contraseña asociada al inicio de sesión.
     */
    public LogIn(String email, String password) {
        this.email = email;
        this.password = password;

    }

    public String getEmail() {

        return email;
    }

    public void setEmail(String email) {

        this.email = email;
    }

    public String getPassword() {

        return password;
    }

    public void setPassword(String password) {

        this.password = password;
    }

}

