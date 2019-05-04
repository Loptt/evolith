/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package evolith.database;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author ErickFrank
 */
@Entity
@Table(name = "ranking2", catalog = "Evolith", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Ranking2P.findAll", query = "SELECT r FROM Ranking2P r")
    , @NamedQuery(name = "Ranking2P.findByPlayerName", query = "SELECT r FROM Ranking2P r WHERE r.playerName = :playerName")
    , @NamedQuery(name = "Ranking2P.findBySpeciesName", query = "SELECT r FROM Ranking2P r WHERE r.speciesName = :speciesName")
    , @NamedQuery(name = "Ranking2P.findByGameDuration", query = "SELECT r FROM Ranking2P r WHERE r.gameDuration = :gameDuration")
    , @NamedQuery(name = "Ranking2P.findByGameScore", query = "SELECT r FROM Ranking2P r WHERE r.gameScore = :gameScore")})
public class Ranking2P implements Serializable {

    private static final long serialVersionUID = 1L;
    @Column(name = "player_name", length = 20)
    private String playerName;
    @Column(name = "species_name", length = 20)
    @Id
    private String speciesName;
    @Column(name = "game_duration")
    private Integer gameDuration;
    @Basic(optional = false)
    @Column(name = "game_score", nullable = false)
    private int gameScore;

    public Ranking2P() {
    }
    public List<Ranking2P> getRanking2P()
    {
        List<Ranking2P> res;
           EntityManagerFactory emf = Persistence.createEntityManagerFactory("EvolithPU");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        try {
            Query qranking = em.createNamedQuery("Ranking2P.findAll");
            res  =  (List<Ranking2P>) qranking.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
            return null;
        } finally {
            em.close();
        }
        return res;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getSpeciesName() {
        return speciesName;
    }

    public void setSpeciesName(String speciesName) {
        this.speciesName = speciesName;
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
    
}
