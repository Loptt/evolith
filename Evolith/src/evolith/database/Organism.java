/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package evolith.database;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
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
@Table(name = "Organism")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Organism.findAll", query = "SELECT o FROM Organism o")
    , @NamedQuery(name = "Organism.findByOrganismId", query = "SELECT o FROM Organism o WHERE o.organismId = :organismId")
    , @NamedQuery(name = "Organism.findByOrganismName", query = "SELECT o FROM Organism o WHERE o.organismName = :organismName")
    , @NamedQuery(name = "Organism.findByOrganismAlive", query = "SELECT o FROM Organism o WHERE o.organismAlive = :organismAlive")
    , @NamedQuery(name = "Organism.findByOrganismGeneration", query = "SELECT o FROM Organism o WHERE o.organismGeneration = :organismGeneration")
    , @NamedQuery(name = "Organism.findByOrganismPositionX", query = "SELECT o FROM Organism o WHERE o.organismPositionX = :organismPositionX")
    , @NamedQuery(name = "Organism.findByOrganismPositionY", query = "SELECT o FROM Organism o WHERE o.organismPositionY = :organismPositionY")})
public class Organism implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "organism_id")
    private Integer organismId;
    @Column(name = "organism_name")
    private String organismName;
    @Column(name = "organism_alive")
    private Boolean organismAlive;
    @Column(name = "organism_generation")
    private Integer organismGeneration;
    @Column(name = "organism_position_x")
    private Integer organismPositionX;
    @Column(name = "organism_position_y")
    private Integer organismPositionY;
    @OneToMany(mappedBy = "organismParentId")
    private Collection<Organism> organismCollection;
    @JoinColumn(name = "organism_parent_id", referencedColumnName = "organism_id")
    @ManyToOne
    private Organism organismParentId;

    public Organism() {
    }

    public Organism(Integer organismId) {
        this.organismId = organismId;
    }

    public Integer getOrganismId() {
        return organismId;
    }

    public void setOrganismId(Integer organismId) {
        this.organismId = organismId;
    }

    public String getOrganismName() {
        return organismName;
    }

    public void setOrganismName(String organismName) {
        this.organismName = organismName;
    }

    public Boolean getOrganismAlive() {
        return organismAlive;
    }

    public void setOrganismAlive(Boolean organismAlive) {
        this.organismAlive = organismAlive;
    }

    public Integer getOrganismGeneration() {
        return organismGeneration;
    }

    public void setOrganismGeneration(Integer organismGeneration) {
        this.organismGeneration = organismGeneration;
    }

    public Integer getOrganismPositionX() {
        return organismPositionX;
    }

    public void setOrganismPositionX(Integer organismPositionX) {
        this.organismPositionX = organismPositionX;
    }

    public Integer getOrganismPositionY() {
        return organismPositionY;
    }

    public void setOrganismPositionY(Integer organismPositionY) {
        this.organismPositionY = organismPositionY;
    }

    @XmlTransient
    public Collection<Organism> getOrganismCollection() {
        return organismCollection;
    }

    public void setOrganismCollection(Collection<Organism> organismCollection) {
        this.organismCollection = organismCollection;
    }

    public Organism getOrganismParentId() {
        return organismParentId;
    }

    public void setOrganismParentId(Organism organismParentId) {
        this.organismParentId = organismParentId;
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
        if (!(object instanceof Organism)) {
            return false;
        }
        Organism other = (Organism) object;
        if ((this.organismId == null && other.organismId != null) || (this.organismId != null && !this.organismId.equals(other.organismId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "evolith.database.Organism[ organismId=" + organismId + " ]";
    }
    
}
