package com.kidex.SnakeNLadder.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity
@Table(name = "board")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BoardDTO extends AbstractEntity {

    @Column(name = "dimension")
    private Integer dimension;

    @Column(name = "snakes")
    private String snakes;

    @Column(name = "ladders")
    private String ladders;

    @Column(name = "bombs")
    private String bombs;

    @OneToMany(mappedBy = "boardDto", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Fetch(FetchMode.SUBSELECT)
    @BatchSize(size = 10)
    private List<GameDTO> gameDTOList = new ArrayList<>();

    public List<List<Integer>> getSnakes() {
        List<List<Integer>> listValues = new ArrayList<>();
        for (String field : this.snakes.substring(2, snakes.length() - 2).split("\\], \\[")) {
            List<Integer> nestedListValues = new ArrayList<>();
            for (String nestedField : field.split("\\s*,\\s*")) {
                nestedListValues.add(Integer.parseInt(nestedField));
            }
            listValues.add(nestedListValues);
        }
        return listValues;
    }

    public List<List<Integer>> getLadders() {
        List<List<Integer>> listValues = new ArrayList<>();
        for (String field : this.ladders.substring(2, ladders.length() - 2).split("\\], \\[")) {
            List<Integer> nestedListValues = new ArrayList<>();
            for (String nestedField : field.split("\\s*,\\s*")) {
                nestedListValues.add(Integer.parseInt(nestedField));
            }
            listValues.add(nestedListValues);
        }
        return listValues;
    }

    public List<Integer> getBombs() {
        List<Integer> listValues = new ArrayList<>();
        for (String field : this.bombs.substring(1, bombs.length() - 2).split("\\s*,\\s*")) {
            listValues.add(Integer.parseInt(field));
        }
        return listValues;
    }
}
