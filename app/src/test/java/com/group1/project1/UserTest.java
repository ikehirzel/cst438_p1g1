package com.group1.project1;

import junit.framework.TestCase;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class UserTest extends TestCase {

    public void testDefaultConstructor() {
        User test1 = new User();
        assertEquals("n/a", test1.getUsername());
        assertNotEquals("tester1", test1.getUsername());
        assertEquals("n/a", test1.getPassword());
        assertNotEquals("password", test1.getPassword());
    }

    public void testParameterConstructor() {
        User test1 = new User("tester1", "password");
        assertEquals("tester1", test1.getUsername());
        assertNotEquals("tester2", test1.getUsername());
        assertEquals("password", test1.getPassword());
        assertNotEquals("pass", test1.getPassword());
    }

    public void testGetUsername() {
        User test1 = new User("tester1", "password");
        String username = test1.getUsername();
        assertEquals("tester1", username);
        assertNotEquals("tester2", username);
    }

    public void testSetUsername() {
        User test1 = new User("tester1", "password");
        test1.setUsername("tester2");
        assertEquals("tester2", test1.getUsername());
        assertNotEquals("tester1", test1.getUsername());
    }

    public void testGetPassword() {
        User test1 = new User("tester1", "password");
        String password = test1.getPassword();
        assertEquals("password", password);
        assertNotEquals("tester2", password);
    }

    public void testSetPassword() {
        User test1 = new User("tester1", "password");
        test1.setPassword("pass");
        assertEquals("pass", test1.getPassword());
        assertNotEquals("password", test1.getPassword());
    }

    public void testGetOwnedPokemon() {
        User test1 = new User("tester1", "password");
        ArrayList<String> pokemon = new ArrayList<>();
        pokemon.add("Pikachu");
        pokemon.add("Charmander");
        test1.setOwnedPokemon(pokemon);
        assertEquals(pokemon, test1.getOwnedPokemon());
        assertNotEquals(new ArrayList<>(), test1.getOwnedPokemon());

    }

    public void testSetOwnedPokemon() {
        User test1 = new User("tester1", "password");
        assertEquals(new ArrayList<>(), test1.getOwnedPokemon());
        ArrayList<String> pokemon = new ArrayList<>();
        pokemon.add("Pikachu");
        pokemon.add("Charmander");
        test1.setOwnedPokemon(pokemon);
        assertEquals(pokemon, test1.getOwnedPokemon());
        assertNotEquals(new ArrayList<>(), test1.getOwnedPokemon());
    }

    public void testGetOwnedBerries() {
        User test1 = new User("tester1", "password");
        assertEquals(new ArrayList<>(), test1.getOwnedBerries());
        ArrayList<String> berries = new ArrayList<>();
        berries.add("Cheri");
        berries.add("Oran");
        test1.setOwnedBerries(berries);
        assertEquals(berries, test1.getOwnedBerries());
        assertNotEquals(new ArrayList<>(), test1.getOwnedBerries());
    }

    public void testSetOwnedBerries() {
        User test1 = new User("tester1", "password");
        assertEquals(new ArrayList<>(), test1.getOwnedBerries());
        ArrayList<String> berries = new ArrayList<>();
        berries.add("Cheri");
        berries.add("Oran");
        test1.setOwnedBerries(berries);
        assertEquals(berries, test1.getOwnedBerries());
        assertNotEquals(new ArrayList<>(), test1.getOwnedBerries());
    }
}