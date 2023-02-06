package com.example.MList.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.MList.Model.MovieList;
import com.example.MList.Model.Movies;
import com.example.MList.Model.User;
import com.example.MList.Service.MovieService;

@RestController
@RequestMapping("/movie")
@CrossOrigin
public class MovieController {

    // movieservice
    @Autowired
    MovieService service;

    @GetMapping("/getprofile")
    public List<MovieList> getProfile(@RequestParam String username){
        return service.getProfile(username);
    }

    @GetMapping("getlist/{username}/{listName}")
    public List<Movies> getList(@PathVariable String username, @PathVariable String listName){
        return service.getList(username, listName);
    }

    @PostMapping("/addmovie")
    public void addMovieToList(@RequestParam String movieTitle, @RequestParam String username, @RequestParam String list){
        service.addMovieToListService(movieTitle,username,list);
    }

    @PostMapping("/createlist")
    public void createList(@RequestParam String listName, @RequestBody User user){
        service.createList(listName, user);
    }

    @GetMapping("/searchbar/{search}")
    public String searchBar(@PathVariable String search){
        try{
            return service.searchBar(search);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return "searchBar from Controller";
    }

    @PostMapping("/deletemovie")
    public void deleteMovieFromList(@RequestParam int movieId,@RequestParam String movieTitle, @RequestParam String listName, @RequestParam String username){
        try{
            service.deleteMovieFromList(movieId,movieTitle,listName,username);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    @PostMapping("/deletelist")
    public void deleteList(@RequestParam String listName, @RequestParam String username){
        try{
            service.deleteList(listName,username);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}
