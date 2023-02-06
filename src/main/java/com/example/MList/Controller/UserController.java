package com.example.MList.Controller;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.MList.Model.User;
import com.example.MList.Service.UserService;

@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserController {
    
    @Autowired
    UserService service;

    @PostMapping("/login")
    public boolean login(@RequestBody User user){
        try{
            System.out.println(user.getUsername() + " ----------- " + user.getPassword());
            if(service.login(user)){
                return true;
            }
            else{
                return false;
            }
        }
        catch(Exception e){
            e.printStackTrace();
            return false;
        }
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
}
