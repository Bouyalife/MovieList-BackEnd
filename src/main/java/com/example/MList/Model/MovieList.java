package com.example.MList.Model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name ="t_user_lists")
public class MovieList{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int listId;
    @JoinColumn(name = "user")
    private int userId;
    private String listName;

    @OneToMany(mappedBy = "listId")
    private List<Movies> list = new ArrayList<Movies>();

    public MovieList(int listId, int userId, String listName){
        this.listId = listId;
        this.userId = userId;
        this.listName = listName;
    }

    public MovieList(){

    }

    public void setListId(int listId){
        this.listId = listId;
    }

    public int getListId(){
        return this.listId;
    }

    public void setUserId(int userId){
        this.userId = userId;
    }

    public int getUserId(){
        return this.userId;
    }

    public void setlistName(String listName){
        this.listName = listName;
    }

    public String getlistName(){
        return this.listName;
    }
}