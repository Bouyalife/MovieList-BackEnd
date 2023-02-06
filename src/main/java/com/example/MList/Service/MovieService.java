package com.example.MList.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

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
public class MovieService {
    @Autowired
    MoviesRepository mRepository;

    @Autowired
    MovieListRepository mListRepository;

    @Autowired
    UserRepository repository;

    // Hittar alla listor tillhörande en profil med username
    public List<MovieList> getProfile(String username){
        try{
            return mListRepository.findProfileById(repository.findByUsername(username).getId());
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    public List<Movies> getList(String username, String listName){
        try{
            return mRepository.findAllMoviesOfListById(repository.findByUsername(username).getId(),mListRepository.findByListName(listName));
        }
        catch(Exception e)
        {
            return null;
        }
    }

    // Lägg till en filmtitel till en lista tillhörande id
    public boolean addMovieToListService(String movieTitle, String username, String list){
        try{
            Movies movie = new Movies();
            System.out.println(movieTitle);
            int id = 0;
            String title = "";
            String urlPath = String.format("https://api.themoviedb.org/3/search/movie?api_key=ba75fbe7d6887d7189c56dab58663b07&language=en-US&query=%s&page=1&include_adult=false", movieTitle);
            URL url = new URL(urlPath);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Content-type","application/json");
            con.setDoOutput(true);
            
            con.connect();
            int responseCode = con.getResponseCode();
            if(responseCode == HttpURLConnection.HTTP_OK)
            {
                BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(),"UTF-8"));
                StringBuilder response = new StringBuilder();
                String responseLine = "";

                while((responseLine = br.readLine()) != null){
                    response.append(responseLine);
                }
                System.out.println(response);
                id = Integer.valueOf(response.substring(response.indexOf(",\"id\":")+6,response.indexOf(",\"original_language\":\"")));
                title = response.substring(response.indexOf(",\"original_title\":")+19, response.indexOf("\",\"overview\":"));
            }
            if(id != 0)
            {
                movie.setListId(mListRepository.findByListName(list));
                movie.setMovieId(id);
                movie.setUserId(repository.findByUsername(username).getId());
                movie.setMovieTitle(title);
                System.out.println(movie.getListId() + " " + movie.getUserId() + "   " + movie.getMovieId() + "    " + movieTitle);
                mRepository.save(movie);
            }
            return true;
        }
        catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

    // Skapar en filmlista med listName tillhörande user.username
    public void createList(String listName, User user){
        try{
            List<MovieList> profileList = mListRepository.findProfileById(repository.findByUsername(user.getUsername()).getId());
            for(int i = 0; i < profileList.size(); i++)
            {
                if(profileList.get(i).getlistName().equals(listName))
                {
                    return; // lägg till returna en sträng som säger failed.
                }            
            }
            MovieList mList = new MovieList();
            mList.setUserId(repository.findByUsername(user.getUsername()).getId());
            mList.setlistName(listName.substring(1,listName.length()-1));
            mListRepository.save(mList);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    // Hämmtar titlar från tmdb api baserat på sök
    public String searchBar(String search){
        try{

            String urlPath = String.format("https://api.themoviedb.org/3/search/movie?api_key=ba75fbe7d6887d7189c56dab58663b07&language=en-US&query=%s&page=1&include_adult=false",search);
            System.out.println(urlPath);
            URL url = new URL(urlPath);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Content-type", "application/json");
            con.setDoOutput(true);

            con.connect();
            
            int responseCode = con.getResponseCode();

            if(responseCode == HttpURLConnection.HTTP_OK)
            {
                BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(),"UTF-8"));

                StringBuilder response = new StringBuilder();
                String responseLine = "";

                while((responseLine = br.readLine()) != null){
                    response.append(responseLine);
                }
                return response.toString();
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return "searchBar function returned, Api request failed.";
    } 

    public void deleteMovieFromList(int movieId, String movieTitle, String listName, String username){
        try{
            Movies movie = new Movies(movieId,repository.findByUsername(username).getId(),mListRepository.findByListName(listName),movieTitle);
            mRepository.delete(movie);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public void deleteList(String listName, String username){
        try{
            MovieList mList = new MovieList();
            mList.setListId(mListRepository.findByListName(listName));
            mList.setUserId(repository.findByUsername(username).getId());
            mListRepository.delete(mList);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}
