package com.example.MList.Model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "t_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String username;
    private String password;

    @ElementCollection
    private List<String> movieList = new ArrayList<String>();

    public User(int id, String username, String password){
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public User(){

    }

    public void setId(int id){
        this.id = id;
    }

    public int getId(){
        return this.id;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public String getUsername(){
        return this.username;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public String getPassword(){
        return this.password;
    }

    public List<String> getList(){
        return this.movieList;
    }
    
    public void setList(List<String> movieList){
        this.movieList = movieList;
    }

}
