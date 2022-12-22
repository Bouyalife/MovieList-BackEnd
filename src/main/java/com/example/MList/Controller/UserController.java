package com.example.MList.Controller;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.MList.Service.UserService;

@RestController
@CrossOrigin
public class UserController {
    
    @Autowired
    UserService service;

    @PostMapping("/login")
    public void login(@RequestParam String username, @RequestParam String password){

    }

    @GetMapping("/getuser")
    public void getUser(@RequestParam String username){
        LoggerFactory.getLogger(getClass()).info(username);
        service.getUserService(username);
    }

    @PostMapping("/adduser")
    public void addUser(@RequestParam String username, @RequestParam String password){
        service.addUserService(username,password);
    }

    @GetMapping("/catfact")
    public String getCatFact(){
        return service.getCatFact();
    }

    // flytta till movie controller
    @PostMapping("/addmovie")
    public void addMovieToList(@RequestParam String movieTitle, @RequestParam int id){
        service.addMovieToListService(movieTitle,id);
    }

}
