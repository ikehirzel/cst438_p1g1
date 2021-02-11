package com.group1.project1.data;

import java.util.ArrayList;

public class User {

    // Username and Password Information
    private String username;
    private String password;

    // List of User's owned Pokemon and berries
    private ArrayList<String> ownedPokemon = new ArrayList<>();
    private ArrayList<String> ownedBerries = new ArrayList<>();

    // ^^^ NOTE: Change <String> later when classes have all been made.

    // Default Constructor
    public User() {
        this.username = "n/a";
        this.password = "n/a";
    }

    // Constructor
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // Setters and Getters

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ArrayList<String> getOwnedPokemon() {
        return ownedPokemon;
    }

    public void setOwnedPokemon(ArrayList<String> ownedPokemon) {
        this.ownedPokemon = ownedPokemon;
    }

    public ArrayList<String> getOwnedBerries() {
        return ownedBerries;
    }

    public void setOwnedBerries(ArrayList<String> ownedBerries) {
        this.ownedBerries = ownedBerries;
    }

}
