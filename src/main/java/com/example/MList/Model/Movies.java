package com.example.MList.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "t_movies")
public class Movies{

    @Id
    private int movieId;

    @JoinColumn(name = "userId")
    private int userId; 

    @JoinColumn(name = "listId")
    private int listId;

    private String movieTitle;

    public Movies(int movieId, int userId, int listId, String movieTitle){
        this.movieTitle = movieTitle;
        this.movieId = movieId;
        this.userId = userId;
        this.listId = listId;
    }

    public Movies(){

    }

    public void setMovieTitle(String movieTitle){
        this.movieTitle = movieTitle;
    }

    public String getMovieTitle(){
        return this.movieTitle;
    }

    public void setMovieId(int movieId){
        this.movieId = movieId;
    }

    public int getMovieId(){
        return this.movieId;
    }

    public void setUserId(int userId){
        this.userId = userId;
    }

    public int getUserId(){
        return this.userId;
    }

    public void setListId(int listId){
        this.listId = listId;
    }

    public int getListId(){
        return this.listId;
    }
}