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
@Table(name = "Species")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Species.findAll", query = "SELECT s FROM Species s")
    , @NamedQuery(name = "Species.findBySpeciesId", query = "SELECT s FROM Species s WHERE s.speciesId = :speciesId")
    , @NamedQuery(name = "Species.findBySpeciesColor", query = "SELECT s FROM Species s WHERE s.speciesColor = :speciesColor")
    , @NamedQuery(name = "Species.findBySpeciesName", query = "SELECT s FROM Species s WHERE s.speciesName = :speciesName")})
public class Species implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "species_id")
    private Integer speciesId;
    @Column(name = "species_color")
    private String speciesColor;
    @Column(name = "species_name")
    private String speciesName;

    public Species() {
    }

    public Species(Integer speciesId) {
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (speciesId != null ? speciesId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Species)) {
            return false;
        }
        Species other = (Species) object;
        if ((this.speciesId == null && other.speciesId != null) || (this.speciesId != null && !this.speciesId.equals(other.speciesId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "evolith.database.Species[ speciesId=" + speciesId + " ]";
    }
    
}
