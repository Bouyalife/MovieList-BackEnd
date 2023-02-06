package com.example.MList.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.example.MList.Model.MovieList;

@Repository
@CrossOrigin
public interface MovieListRepository extends JpaRepository<MovieList,Integer>{
    
    @Query(value = "Select * FROM t_user_lists WHERE user_id=:id", nativeQuery = true)
    List<MovieList>  findProfileById(@Param("id")int id);

    @Query(value = "Select list_id FROM t_user_lists WHERE list_name=:list", nativeQuery = true)
    int findByListName(@Param("list")String list);

}
