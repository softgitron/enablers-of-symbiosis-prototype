package com.example.enablersofsymbiosisprototype.data;

import java.util.ArrayList;

public class Users {
    private static Users instance = new Users();
    public ArrayList<User> list = new ArrayList<>();

    private Users() {

    }

    public static Users getInstance() {
        return instance;
    }
}
