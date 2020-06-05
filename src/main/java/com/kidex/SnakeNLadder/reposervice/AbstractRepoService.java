package com.kidex.SnakeNLadder.reposervice;

import com.kidex.SnakeNLadder.exception.DataAccessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public abstract class AbstractRepoService<T, U> {

    @Autowired
    public abstract JpaRepository<T, U> getRepo();

    public List<T> getAllEntities() {
        List<T> entities;
        try {
            entities = getRepo().findAll();
        } catch (Exception e) {
            throw new DataAccessException(
                    "Error while retrieving entities from DB:" + e.getMessage());
        }
        return entities;
    }
}
