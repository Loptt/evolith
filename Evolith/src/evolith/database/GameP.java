/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package evolith.database;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author ErickFrank
 */
@Entity
@Table(name = "game", catalog = "Evolith", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "GameP.findAll", query = "SELECT g FROM GameP g")
    , @NamedQuery(name = "GameP.findByGameId", query = "SELECT g FROM GameP g WHERE g.gameId = :gameId")
    , @NamedQuery(name = "GameP.findByGameDuration", query = "SELECT g FROM GameP g WHERE g.gameDuration = :gameDuration")
    , @NamedQuery(name = "GameP.findByGameScore", query = "SELECT g FROM GameP g WHERE g.gameScore = :gameScore")})
public class GameP implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "game_id", nullable = false)
    private Integer gameId;
    @Column(name = "game_duration")
    private Integer gameDuration;
    @Basic(optional = false)
    @Column(name = "game_score", nullable = false)
    private int gameScore;
    @JoinColumn(name = "player_id", referencedColumnName = "player_id", nullable = false)
    @ManyToOne(optional = false)
    private PlayerP playerId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "gameId")
    private Collection<SpeciesP> speciesPCollection;

    public GameP() {
    }

    public GameP(Integer gameId) {
        this.gameId = gameId;
    }

    public GameP(Integer gameId, int gameScore) {
        this.gameId = gameId;
        this.gameScore = gameScore;
    }

    public Integer getGameId() {
        return gameId;
    }

    public void setGameId(Integer gameId) {
        this.gameId = gameId;
    }

    public Integer getGameDuration() {
        return gameDuration;
    }

    public void setGameDuration(Integer gameDuration) {
        this.gameDuration = gameDuration;
    }

    public int getGameScore() {
        return gameScore;
    }

    public void setGameScore(int gameScore) {
        this.gameScore = gameScore;
    }

    public PlayerP getPlayerId() {
        return playerId;
    }

    public void setPlayerId(PlayerP playerId) {
        this.playerId = playerId;
    }

    @XmlTransient
    public Collection<SpeciesP> getSpeciesPCollection() {
        return speciesPCollection;
    }

    public void setSpeciesPCollection(Collection<SpeciesP> speciesPCollection) {
        this.speciesPCollection = speciesPCollection;
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
        if (!(object instanceof GameP)) {
            return false;
        }
        GameP other = (GameP) object;
        if ((this.gameId == null && other.gameId != null) || (this.gameId != null && !this.gameId.equals(other.gameId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "evolith.database.GameP[ gameId=" + gameId + " ]";
    }
    
}
