package com.example.MList.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.example.MList.Model.MovieList;
import com.example.MList.Model.Movies;
import com.example.MList.Model.User;
import com.example.MList.Repository.MovieListRepository;
import com.example.MList.Repository.MoviesRepository;
import com.example.MList.Repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
@CrossOrigin
public class UserService {

    @Autowired
    UserRepository repository;
    @Autowired
    MovieListRepository mListRepository;
    @Autowired
    MoviesRepository mRepository;


    // Hämta en specifik användare baserat på användarnamn
    public boolean getUserService(String username){
        try{
            LoggerFactory.getLogger(getClass()).info(repository.findByUsername(username).getPassword());
            return true;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }

    // Lägg till en användare med specifikt användarnamn och lösenord. Användarnamn är unikt
    public boolean addUserService(String username, String password){
        System.out.println(username + " tdfsgsdfg :" + password);
        try
        {
            // Salt
            SecureRandom random = new SecureRandom();
            byte[] salt = new byte[16];
            random.nextBytes(salt);
            // Hash
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.update(salt);
            byte[] hashedPassword = md.digest(password.getBytes("UTF-8"));

            User user = new User();
            user.setPassword(new String(hashedPassword));
            user.setUsername(username);
            user.setSalt(new String(salt));
            repository.save(user);
            return true;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return false;
        }        
    }

    public String getCatFact(){
        try{
            URL url = new URL("https://catfact.ninja/fact");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            con.setRequestMethod("GET");
            con.setRequestProperty("Content-type", "application/json");
            con.setDoOutput(true);

            con.connect();
            int responseCode = con.getResponseCode();
            if(responseCode == HttpURLConnection.HTTP_OK){
                System.err.println(responseCode);
                BufferedReader br = new BufferedReader(
                    new InputStreamReader(con.getInputStream(),"UTF-8")
                );

                StringBuilder response = new StringBuilder();
                String responseLine = null;
                while((responseLine = br.readLine()) != null)
                {
                    response.append(responseLine);
                }

                return response.toString().substring(9,response.toString().indexOf("\",\"length\":"));
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return "CATFACT couldn't be called";
    }

    public boolean login(User user){
        try{
            System.out.println("SERVICE: " + user.getUsername() + " ----------- " + user.getPassword());
            System.out.println(repository.findById(1).get().getUsername());
            System.out.println(repository.findByUsername(user.getUsername()));
            // Validate password
            // Hämta salt och hämta hashed lösenord
            User foundUser = repository.findByUsername(user.getUsername());
            String salt = foundUser.getSalt();
            String password = foundUser.getPassword();
            
            // jämför hash -> user.password + salt == foundUser.password
            
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.update(salt.getBytes());
            String loginPassword = new String(md.digest(user.getPassword().getBytes(StandardCharsets.UTF_8)));
            System.out.println(loginPassword);
            System.out.println(password);
            if(loginPassword.equals(password)){
                return true;
            }
            else
            {
                return false;
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }
}
