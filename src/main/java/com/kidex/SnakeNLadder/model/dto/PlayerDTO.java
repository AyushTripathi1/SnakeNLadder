package com.kidex.SnakeNLadder.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "player")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PlayerDTO extends AbstractEntity {

    @Column(name = "player_number")
    private Integer playerNumber;

    @Column(name = "next_move")
    @Builder.Default
    private Boolean nextMove = Boolean.TRUE;

    @Column(name = "is_active")
    @Builder.Default
    private Boolean active = Boolean.FALSE;

    @Column(name = "current_position")
    @Builder.Default
    private Integer currentPosition = 0;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "game_id")
    private GameDTO gameDto;

}
