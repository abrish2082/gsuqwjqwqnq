/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.mms.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Sadik
 */
@Entity
@Table(name = "MMS_LU_UNIT_MEASURE", catalog = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MmsLuUnitMeasure.findAll", query = "SELECT m FROM MmsLuUnitMeasure m"),
    @NamedQuery(name = "MmsLuUnitMeasure.findByName", query = "SELECT m FROM MmsLuUnitMeasure m WHERE m.name = :name"),
    @NamedQuery(name = "MmsLuUnitMeasure.findByDescription", query = "SELECT m FROM MmsLuUnitMeasure m WHERE m.description = :description"),
    @NamedQuery(name = "MmsLuUnitMeasure.findById", query = "SELECT m FROM MmsLuUnitMeasure m WHERE m.id = :id")})
public class MmsLuUnitMeasure implements Serializable {
    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "NAME", nullable = false, length = 45)
    private String name;
    @Size(max = 50)
    @Column(name = "DESCRIPTION", length = 50)
    private String description;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
   @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MMS_LU_UNIT_MEASURE_SEQ")
    @SequenceGenerator(name = "MMS_LU_UNIT_MEASURE_SEQ", sequenceName = "MMS_LU_UNIT_MEASURE_SEQ", allocationSize = 1)
    @Column(name = "ID", nullable = false, precision = 0, scale = -127)
    private Integer id;

    public MmsLuUnitMeasure() {
    }

    public MmsLuUnitMeasure(Integer id) {
        this.id = id;
    }

    public MmsLuUnitMeasure(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MmsLuUnitMeasure)) {
            return false;
        }
        MmsLuUnitMeasure other = (MmsLuUnitMeasure) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return name;
    }
    
}
