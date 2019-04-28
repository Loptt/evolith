/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package evolith.database;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author ErickFrank
 */
@Entity
@Table(name = "Game")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Game.findAll", query = "SELECT g FROM Game g")
    , @NamedQuery(name = "Game.findByGameId", query = "SELECT g FROM Game g WHERE g.gameId = :gameId")
    , @NamedQuery(name = "Game.findByGameDateCreated", query = "SELECT g FROM Game g WHERE g.gameDateCreated = :gameDateCreated")
    , @NamedQuery(name = "Game.findByGameDuration", query = "SELECT g FROM Game g WHERE g.gameDuration = :gameDuration")
    , @NamedQuery(name = "Game.findByGameScore", query = "SELECT g FROM Game g WHERE g.gameScore = :gameScore")})
public class Game implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "game_id")
    private Integer gameId;
    @Basic(optional = false)
    @Column(name = "game_date_created")
    @Temporal(TemporalType.DATE)
    private Date gameDateCreated;
    @Basic(optional = false)
    @Column(name = "game_duration")
    private int gameDuration;
    @Basic(optional = false)
    @Column(name = "game_score")
    private int gameScore;

    public Game() {
    }

    public Game(Integer gameId) {
        this.gameId = gameId;
    }

    public Game(Integer gameId, Date gameDateCreated, int gameDuration, int gameScore) {
        this.gameId = gameId;
        this.gameDateCreated = gameDateCreated;
        this.gameDuration = gameDuration;
        this.gameScore = gameScore;
    }

    public Integer getGameId() {
        return gameId;
    }

    public void setGameId(Integer gameId) {
        this.gameId = gameId;
    }

    public Date getGameDateCreated() {
        return gameDateCreated;
    }

    public void setGameDateCreated(Date gameDateCreated) {
        this.gameDateCreated = gameDateCreated;
    }

    public int getGameDuration() {
        return gameDuration;
    }

    public void setGameDuration(int gameDuration) {
        this.gameDuration = gameDuration;
    }

    public int getGameScore() {
        return gameScore;
    }

    public void setGameScore(int gameScore) {
        this.gameScore = gameScore;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (gameId != null ? gameId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Game)) {
            return false;
        }
        Game other = (Game) object;
        if ((this.gameId == null && other.gameId != null) || (this.gameId != null && !this.gameId.equals(other.gameId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "evolith.database.Game[ gameId=" + gameId + " ]";
    }
    
}
