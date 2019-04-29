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
@Table(name = "species", catalog = "Evolith", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SpeciesDB.findAll", query = "SELECT s FROM SpeciesDB s")
    , @NamedQuery(name = "SpeciesDB.findBySpeciesId", query = "SELECT s FROM SpeciesDB s WHERE s.speciesId = :speciesId")
    , @NamedQuery(name = "SpeciesDB.findBySpeciesColor", query = "SELECT s FROM SpeciesDB s WHERE s.speciesColor = :speciesColor")
    , @NamedQuery(name = "SpeciesDB.findBySpeciesName", query = "SELECT s FROM SpeciesDB s WHERE s.speciesName = :speciesName")})
public class SpeciesDB implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "species_id", nullable = false)
    private Integer speciesId;
    @Column(name = "species_color", length = 20)
    private String speciesColor;
    @Column(name = "species_name", length = 20)
    private String speciesName;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "speciesId")
    private Collection<OrganismDB> organismDBCollection;
    @JoinColumn(name = "game_id", referencedColumnName = "game_id", nullable = false)
    @ManyToOne(optional = false)
    private GameDB gameId;

    public SpeciesDB() {
    }

    public SpeciesDB(Integer speciesId) {
        this.speciesId = speciesId;
    }

    public Integer getSpeciesId() {
        return speciesId;
    }

    public void setSpeciesId(Integer speciesId) {
        this.speciesId = speciesId;
    }

    public String getSpeciesColor() {
        return speciesColor;
    }

    public void setSpeciesColor(String speciesColor) {
        this.speciesColor = speciesColor;
    }

    public String getSpeciesName() {
        return speciesName;
    }

    public void setSpeciesName(String speciesName) {
        this.speciesName = speciesName;
    }

    @XmlTransient
    public Collection<OrganismDB> getOrganismDBCollection() {
        return organismDBCollection;
    }

    public void setOrganismDBCollection(Collection<OrganismDB> organismDBCollection) {
        this.organismDBCollection = organismDBCollection;
    }

    public GameDB getGameId() {
        return gameId;
    }

    public void setGameId(GameDB gameId) {
        this.gameId = gameId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (speciesId != null ? speciesId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SpeciesDB)) {
            return false;
        }
        SpeciesDB other = (SpeciesDB) object;
        if ((this.speciesId == null && other.speciesId != null) || (this.speciesId != null && !this.speciesId.equals(other.speciesId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "evolith.database.SpeciesDB[ speciesId=" + speciesId + " ]";
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
