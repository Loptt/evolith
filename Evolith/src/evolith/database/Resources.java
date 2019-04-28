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
@Table(name = "Resources")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Resources.findAll", query = "SELECT r FROM Resources r")
    , @NamedQuery(name = "Resources.findByResourcesId", query = "SELECT r FROM Resources r WHERE r.resourcesId = :resourcesId")
    , @NamedQuery(name = "Resources.findByResourcesPositionX", query = "SELECT r FROM Resources r WHERE r.resourcesPositionX = :resourcesPositionX")
    , @NamedQuery(name = "Resources.findByResourcesPositionY", query = "SELECT r FROM Resources r WHERE r.resourcesPositionY = :resourcesPositionY")
    , @NamedQuery(name = "Resources.findByResourcesQuantity", query = "SELECT r FROM Resources r WHERE r.resourcesQuantity = :resourcesQuantity")})
public class Resources implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "resources_id")
    private Integer resourcesId;
    @Basic(optional = false)
    @Column(name = "resources_position_x")
    private int resourcesPositionX;
    @Basic(optional = false)
    @Column(name = "resources_position_y")
    private int resourcesPositionY;
    @Column(name = "resources_quantity")
    private Integer resourcesQuantity;

    public Resources() {
    }

    public Resources(Integer resourcesId) {
        this.resourcesId = resourcesId;
    }

    public Resources(Integer resourcesId, int resourcesPositionX, int resourcesPositionY) {
        this.resourcesId = resourcesId;
        this.resourcesPositionX = resourcesPositionX;
        this.resourcesPositionY = resourcesPositionY;
    }

    public Integer getResourcesId() {
        return resourcesId;
    }

    public void setResourcesId(Integer resourcesId) {
        this.resourcesId = resourcesId;
    }

    public int getResourcesPositionX() {
        return resourcesPositionX;
    }

    public void setResourcesPositionX(int resourcesPositionX) {
        this.resourcesPositionX = resourcesPositionX;
    }

    public int getResourcesPositionY() {
        return resourcesPositionY;
    }

    public void setResourcesPositionY(int resourcesPositionY) {
        this.resourcesPositionY = resourcesPositionY;
    }

    public Integer getResourcesQuantity() {
        return resourcesQuantity;
    }

    public void setResourcesQuantity(Integer resourcesQuantity) {
        this.resourcesQuantity = resourcesQuantity;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (resourcesId != null ? resourcesId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Resources)) {
            return false;
        }
        Resources other = (Resources) object;
        if ((this.resourcesId == null && other.resourcesId != null) || (this.resourcesId != null && !this.resourcesId.equals(other.resourcesId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "evolith.database.Resources[ resourcesId=" + resourcesId + " ]";
    }
    
}
