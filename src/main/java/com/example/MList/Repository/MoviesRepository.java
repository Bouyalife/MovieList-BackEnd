package com.example.MList.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.example.MList.Model.Movies;

@Repository
@CrossOrigin
public interface MoviesRepository extends JpaRepository<Movies,Integer>{

    @Query(value="SELECT * FROM t_movies WHERE list_id=:listId AND user_id=:username",nativeQuery = true)
    List<Movies> findAllMoviesOfListById(@Param("username") int username,@Param("listId") int listId);

    @Query(value="DELETE FROM t_movies WHERE movie_id=:movieId",nativeQuery = true)
    boolean deleteMovie(@Param("movieId") int movieId);
}
