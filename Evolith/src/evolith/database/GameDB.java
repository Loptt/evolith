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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "game", catalog = "Evolith", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "GameDB.findAll", query = "SELECT g FROM GameDB g")
    , @NamedQuery(name = "GameDB.findByGameId", query = "SELECT g FROM GameDB g WHERE g.gameId = :gameId")
    , @NamedQuery(name = "GameDB.findByGameDuration", query = "SELECT g FROM GameDB g WHERE g.gameDuration = :gameDuration")
    , @NamedQuery(name = "GameDB.findByGameScore", query = "SELECT g FROM GameDB g WHERE g.gameScore = :gameScore")})
public class GameDB implements Serializable {

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
    private PlayerDB playerId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "gameId")
    private Collection<SpeciesDB> speciesDBCollection;

    public GameDB() {
    }

    public GameDB(Integer gameId) {
        this.gameId = gameId;
    }

    public GameDB(Integer gameId, int gameScore) {
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

    public PlayerDB getPlayerId() {
        return playerId;
    }

    public void setPlayerId(PlayerDB playerId) {
        this.playerId = playerId;
    }

    @XmlTransient
    public Collection<SpeciesDB> getSpeciesDBCollection() {
        return speciesDBCollection;
    }

    public void setSpeciesDBCollection(Collection<SpeciesDB> speciesDBCollection) {
        this.speciesDBCollection = speciesDBCollection;
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
        if (!(object instanceof GameDB)) {
            return false;
        }
        GameDB other = (GameDB) object;
        if ((this.gameId == null && other.gameId != null) || (this.gameId != null && !this.gameId.equals(other.gameId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "evolith.database.GameDB[ gameId=" + gameId + " ]";
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
