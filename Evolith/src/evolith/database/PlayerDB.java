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
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Persistence;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author ErickFrank
 */
@Entity
@Table(name = "player", catalog = "Evolith", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PlayerDB.findAll", query = "SELECT p FROM PlayerDB p")
    , @NamedQuery(name = "PlayerDB.findByPlayerId", query = "SELECT p FROM PlayerDB p WHERE p.playerId = :playerId")
    , @NamedQuery(name = "PlayerDB.findByPlayerName", query = "SELECT p FROM PlayerDB p WHERE p.playerName = :playerName")})
public class PlayerDB implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "player_id", nullable = false)
    private Integer playerId;
    @Column(name = "player_name", length = 20)
    private String playerName;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "playerId")
    private Collection<GameDB> gameDBCollection;

    public PlayerDB() {
    }

    public PlayerDB(Integer playerId) {
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

    @XmlTransient
    public Collection<GameDB> getGameDBCollection() {
        return gameDBCollection;
    }

    public void setGameDBCollection(Collection<GameDB> gameDBCollection) {
        this.gameDBCollection = gameDBCollection;
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
        if (!(object instanceof PlayerDB)) {
            return false;
        }
        PlayerDB other = (PlayerDB) object;
        if ((this.playerId == null && other.playerId != null) || (this.playerId != null && !this.playerId.equals(other.playerId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "evolith.database.PlayerDB[ playerId=" + playerId + " ]";
    }

    public void persist(Object object) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("EvolithPU");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        try {
            em.persist(object);
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }
    
    
}
