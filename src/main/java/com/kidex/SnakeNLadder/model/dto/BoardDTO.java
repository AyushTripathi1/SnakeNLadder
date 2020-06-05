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
    private String snakesArray;

    @Column(name = "ladders")
    private String laddersArray;

    @Column(name = "bombs")
    private String bombsArray;

    @OneToMany(mappedBy = "boardDto", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Fetch(FetchMode.SUBSELECT)
    @BatchSize(size = 10)
    private List<GameDTO> gameDTOList = new ArrayList<>();
}
