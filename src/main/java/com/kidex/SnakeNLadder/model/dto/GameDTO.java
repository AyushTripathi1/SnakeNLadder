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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "game")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GameDTO extends AbstractEntity {

    @Column(name = "is_active")
    @Builder.Default
    private Boolean active = Boolean.TRUE;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "board_id")
    private BoardDTO boardDto;

    @OneToMany(mappedBy = "gameDto", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Fetch(FetchMode.SUBSELECT)
    @BatchSize(size = 10)
    private List<PlayerDTO> playerDTOList = new ArrayList<>();

}
