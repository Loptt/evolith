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
@Table(name = "organism", catalog = "Evolith", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "OrganismDB.findAll", query = "SELECT o FROM OrganismDB o")
    , @NamedQuery(name = "OrganismDB.findByOrganismId", query = "SELECT o FROM OrganismDB o WHERE o.organismId = :organismId")
    , @NamedQuery(name = "OrganismDB.findByOrganismAlive", query = "SELECT o FROM OrganismDB o WHERE o.organismAlive = :organismAlive")
    , @NamedQuery(name = "OrganismDB.findByOrganismGeneration", query = "SELECT o FROM OrganismDB o WHERE o.organismGeneration = :organismGeneration")
    , @NamedQuery(name = "OrganismDB.findByOrganismKills", query = "SELECT o FROM OrganismDB o WHERE o.organismKills = :organismKills")
    , @NamedQuery(name = "OrganismDB.findByOrganismLifespan", query = "SELECT o FROM OrganismDB o WHERE o.organismLifespan = :organismLifespan")})
public class OrganismDB implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "organism_id", nullable = false)
    private Integer organismId;
    @Column(name = "organism_alive")
    private Integer organismAlive;
    @Column(name = "organism_generation")
    private Integer organismGeneration;
    @Column(name = "organism_kills")
    private Integer organismKills;
    @Column(name = "organism_lifespan")
    private Integer organismLifespan;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "organismId")
    private Collection<MutationDB> mutationDBCollection;
    @JoinColumn(name = "species_id", referencedColumnName = "species_id", nullable = false)
    @ManyToOne(optional = false)
    private SpeciesDB speciesId;

    public OrganismDB() {
    }

    public OrganismDB(Integer organismId) {
        this.organismId = organismId;
    }

    public Integer getOrganismId() {
        return organismId;
    }

    public void setOrganismId(Integer organismId) {
        this.organismId = organismId;
    }

    public Integer getOrganismAlive() {
        return organismAlive;
    }

    public void setOrganismAlive(Integer organismAlive) {
        this.organismAlive = organismAlive;
    }

    public Integer getOrganismGeneration() {
        return organismGeneration;
    }

    public void setOrganismGeneration(Integer organismGeneration) {
        this.organismGeneration = organismGeneration;
    }

    public Integer getOrganismKills() {
        return organismKills;
    }

    public void setOrganismKills(Integer organismKills) {
        this.organismKills = organismKills;
    }

    public Integer getOrganismLifespan() {
        return organismLifespan;
    }

    public void setOrganismLifespan(Integer organismLifespan) {
        this.organismLifespan = organismLifespan;
    }

    @XmlTransient
    public Collection<MutationDB> getMutationDBCollection() {
        return mutationDBCollection;
    }

    public void setMutationDBCollection(Collection<MutationDB> mutationDBCollection) {
        this.mutationDBCollection = mutationDBCollection;
    }

    public SpeciesDB getSpeciesId() {
        return speciesId;
    }

    public void setSpeciesId(SpeciesDB speciesId) {
        this.speciesId = speciesId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (organismId != null ? organismId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof OrganismDB)) {
            return false;
        }
        OrganismDB other = (OrganismDB) object;
        if ((this.organismId == null && other.organismId != null) || (this.organismId != null && !this.organismId.equals(other.organismId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "evolith.database.OrganismDB[ organismId=" + organismId + " ]";
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
