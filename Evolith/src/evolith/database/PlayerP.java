/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package evolith.database;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author ErickFrank
 */
@Entity
@Table(name = "player", catalog = "Evolith", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PlayerP.findAll", query = "SELECT p FROM PlayerP p")
    , @NamedQuery(name = "PlayerP.findByPlayerId", query = "SELECT p FROM PlayerP p WHERE p.playerId = :playerId")
    , @NamedQuery(name = "PlayerP.findByPlayerName", query = "SELECT p FROM PlayerP p WHERE p.playerName = :playerName")})
public class PlayerP implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "player_id", nullable = false)
    private Integer playerId;
    @Column(name = "player_name", length = 20)
    private String playerName;

    public PlayerP() {
    }

    public PlayerP(Integer playerId) {
        this.playerId = playerId;
    }

    public Integer getPlayerId() {
        return playerId;
    }

    public void setPlayerId(Integer playerId) {
        this.playerId = playerId;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (playerId != null ? playerId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PlayerP)) {
            return false;
        }
        PlayerP other = (PlayerP) object;
        if ((this.playerId == null && other.playerId != null) || (this.playerId != null && !this.playerId.equals(other.playerId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "evolith.database.PlayerP[ playerId=" + playerId + " ]";
    }
    
}
