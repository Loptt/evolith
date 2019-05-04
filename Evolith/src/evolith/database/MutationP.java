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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author ErickFrank
 */
@Entity
@Table(name = "mutation", catalog = "Evolith", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MutationP.findAll", query = "SELECT m FROM MutationP m")
    , @NamedQuery(name = "MutationP.findByMutationId", query = "SELECT m FROM MutationP m WHERE m.mutationId = :mutationId")
    , @NamedQuery(name = "MutationP.findByMutationName", query = "SELECT m FROM MutationP m WHERE m.mutationName = :mutationName")
    , @NamedQuery(name = "MutationP.findByMutationValue", query = "SELECT m FROM MutationP m WHERE m.mutationValue = :mutationValue")})
public class MutationP implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "mutation_id", nullable = false)
    private Integer mutationId;
    @Column(name = "mutation_name", length = 20)
    private String mutationName;
    @Column(name = "mutation_value")
    private Integer mutationValue;
    @JoinColumn(name = "organism_id", referencedColumnName = "organism_id", nullable = false)
    @ManyToOne(optional = false)
    private OrganismP organismId;

    public MutationP() {
    }

    public MutationP(Integer mutationId) {
        this.mutationId = mutationId;
    }

    public Integer getMutationId() {
        return mutationId;
    }

    public void setMutationId(Integer mutationId) {
        this.mutationId = mutationId;
    }

    public String getMutationName() {
        return mutationName;
    }

    public void setMutationName(String mutationName) {
        this.mutationName = mutationName;
    }

    public Integer getMutationValue() {
        return mutationValue;
    }

    public void setMutationValue(Integer mutationValue) {
        this.mutationValue = mutationValue;
    }

    public OrganismP getOrganismId() {
        return organismId;
    }

    public void setOrganismId(OrganismP organismId) {
        this.organismId = organismId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (mutationId != null ? mutationId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MutationP)) {
            return false;
        }
        MutationP other = (MutationP) object;
        if ((this.mutationId == null && other.mutationId != null) || (this.mutationId != null && !this.mutationId.equals(other.mutationId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "evolith.database.MutationP[ mutationId=" + mutationId + " ]";
    }
    
}
