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
@Table(name = "Mutation")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Mutation.findAll", query = "SELECT m FROM Mutation m")
    , @NamedQuery(name = "Mutation.findByMutationId", query = "SELECT m FROM Mutation m WHERE m.mutationId = :mutationId")
    , @NamedQuery(name = "Mutation.findByStrength", query = "SELECT m FROM Mutation m WHERE m.strength = :strength")
    , @NamedQuery(name = "Mutation.findBySize", query = "SELECT m FROM Mutation m WHERE m.size = :size")
    , @NamedQuery(name = "Mutation.findByStealth", query = "SELECT m FROM Mutation m WHERE m.stealth = :stealth")
    , @NamedQuery(name = "Mutation.findBySpeed", query = "SELECT m FROM Mutation m WHERE m.speed = :speed")
    , @NamedQuery(name = "Mutation.findByMutationName", query = "SELECT m FROM Mutation m WHERE m.mutationName = :mutationName")})
public class Mutation implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "mutation_id")
    private Integer mutationId;
    @Column(name = "strength")
    private Integer strength;
    @Column(name = "size")
    private Integer size;
    @Column(name = "stealth")
    private Integer stealth;
    @Column(name = "speed")
    private Integer speed;
    @Column(name = "mutation_name")
    private String mutationName;

    public Mutation() {
    }

    public Mutation(Integer mutationId) {
        this.mutationId = mutationId;
    }

    public Integer getMutationId() {
        return mutationId;
    }

    public void setMutationId(Integer mutationId) {
        this.mutationId = mutationId;
    }

    public Integer getStrength() {
        return strength;
    }

    public void setStrength(Integer strength) {
        this.strength = strength;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Integer getStealth() {
        return stealth;
    }

    public void setStealth(Integer stealth) {
        this.stealth = stealth;
    }

    public Integer getSpeed() {
        return speed;
    }

    public void setSpeed(Integer speed) {
        this.speed = speed;
    }

    public String getMutationName() {
        return mutationName;
    }

    public void setMutationName(String mutationName) {
        this.mutationName = mutationName;
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
        if (!(object instanceof Mutation)) {
            return false;
        }
        Mutation other = (Mutation) object;
        if ((this.mutationId == null && other.mutationId != null) || (this.mutationId != null && !this.mutationId.equals(other.mutationId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "evolith.database.Mutation[ mutationId=" + mutationId + " ]";
    }
    
}
