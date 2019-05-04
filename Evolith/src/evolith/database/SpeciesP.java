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
@Table(name = "species", catalog = "Evolith", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SpeciesP.findAll", query = "SELECT s FROM SpeciesP s")
    , @NamedQuery(name = "SpeciesP.findBySpeciesId", query = "SELECT s FROM SpeciesP s WHERE s.speciesId = :speciesId")
    , @NamedQuery(name = "SpeciesP.findBySpeciesColor", query = "SELECT s FROM SpeciesP s WHERE s.speciesColor = :speciesColor")
    , @NamedQuery(name = "SpeciesP.findBySpeciesName", query = "SELECT s FROM SpeciesP s WHERE s.speciesName = :speciesName")})
public class SpeciesP implements Serializable {

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
    private Collection<OrganismP> organismPCollection;
    @JoinColumn(name = "game_id", referencedColumnName = "game_id", nullable = false)
    @ManyToOne(optional = false)
    private GameP gameId;

    public SpeciesP() {
    }

    public SpeciesP(Integer speciesId) {
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
    public Collection<OrganismP> getOrganismPCollection() {
        return organismPCollection;
    }

    public void setOrganismPCollection(Collection<OrganismP> organismPCollection) {
        this.organismPCollection = organismPCollection;
    }

    public GameP getGameId() {
        return gameId;
    }

    public void setGameId(GameP gameId) {
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
        if (!(object instanceof SpeciesP)) {
            return false;
        }
        SpeciesP other = (SpeciesP) object;
        if ((this.speciesId == null && other.speciesId != null) || (this.speciesId != null && !this.speciesId.equals(other.speciesId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "evolith.database.SpeciesP[ speciesId=" + speciesId + " ]";
    }
    
}
