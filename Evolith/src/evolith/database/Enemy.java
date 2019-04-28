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
@Table(name = "Enemy")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Enemy.findAll", query = "SELECT e FROM Enemy e")
    , @NamedQuery(name = "Enemy.findByEnemyId", query = "SELECT e FROM Enemy e WHERE e.enemyId = :enemyId")
    , @NamedQuery(name = "Enemy.findByEnemyPositionX", query = "SELECT e FROM Enemy e WHERE e.enemyPositionX = :enemyPositionX")
    , @NamedQuery(name = "Enemy.findByEnemyPositionY", query = "SELECT e FROM Enemy e WHERE e.enemyPositionY = :enemyPositionY")
    , @NamedQuery(name = "Enemy.findByEnemySize", query = "SELECT e FROM Enemy e WHERE e.enemySize = :enemySize")})
public class Enemy implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "enemy_id")
    private Integer enemyId;
    @Basic(optional = false)
    @Column(name = "enemy_position_x")
    private int enemyPositionX;
    @Basic(optional = false)
    @Column(name = "enemy_position_y")
    private int enemyPositionY;
    @Column(name = "enemy_size")
    private Integer enemySize;

    public Enemy() {
    }

    public Enemy(Integer enemyId) {
        this.enemyId = enemyId;
    }

    public Enemy(Integer enemyId, int enemyPositionX, int enemyPositionY) {
        this.enemyId = enemyId;
        this.enemyPositionX = enemyPositionX;
        this.enemyPositionY = enemyPositionY;
    }

    public Integer getEnemyId() {
        return enemyId;
    }

    public void setEnemyId(Integer enemyId) {
        this.enemyId = enemyId;
    }

    public int getEnemyPositionX() {
        return enemyPositionX;
    }

    public void setEnemyPositionX(int enemyPositionX) {
        this.enemyPositionX = enemyPositionX;
    }

    public int getEnemyPositionY() {
        return enemyPositionY;
    }

    public void setEnemyPositionY(int enemyPositionY) {
        this.enemyPositionY = enemyPositionY;
    }

    public Integer getEnemySize() {
        return enemySize;
    }

    public void setEnemySize(Integer enemySize) {
        this.enemySize = enemySize;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (enemyId != null ? enemyId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Enemy)) {
            return false;
        }
        Enemy other = (Enemy) object;
        if ((this.enemyId == null && other.enemyId != null) || (this.enemyId != null && !this.enemyId.equals(other.enemyId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "evolith.database.Enemy[ enemyId=" + enemyId + " ]";
    }
    
}
