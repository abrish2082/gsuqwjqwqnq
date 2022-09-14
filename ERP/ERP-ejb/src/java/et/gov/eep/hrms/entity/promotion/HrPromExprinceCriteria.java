/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.entity.promotion;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author user
 */
@Entity
@Table(name = "HR_PROM_EXPRINCE_CRITERIA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HrPromExprinceCriteria.findAll", query = "SELECT h FROM HrPromExprinceCriteria h"),
    @NamedQuery(name = "HrPromExprinceCriteria.findById", query = "SELECT h FROM HrPromExprinceCriteria h WHERE h.id = :id"),
    @NamedQuery(name = "HrPromExprinceCriteria.findByWeight", query = "SELECT h FROM HrPromExprinceCriteria h WHERE h.weight = :weight"),
    @NamedQuery(name = "HrPromExprinceCriteria.findByDescription", query = "SELECT h FROM HrPromExprinceCriteria h WHERE h.description = :description")})
public class HrPromExprinceCriteria implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HR_PROM_EXPRINCE_CRITERIA_SEQ")
    @SequenceGenerator(name = "HR_PROM_EXPRINCE_CRITERIA_SEQ", sequenceName = "HR_PROM_EXPRINCE_CRITERIA_SEQ", allocationSize = 1)
    private Integer id;
    @Column(name = "WEIGHT")
    private double weight;
    @Size(max = 200)
    @Column(name = "DESCRIPTION")
    private String description;
    @JoinColumn(name = "LU_PROM_EXPRIENCE_RELATION", referencedColumnName = "ID")
    @ManyToOne
    private HrLuPromExpRelation luPromExprienceRelation;

    public HrPromExprinceCriteria() {
    }

    public HrPromExprinceCriteria(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public HrLuPromExpRelation getLuPromExprienceRelation() {
        return luPromExprienceRelation;
    }

    public void setLuPromExprienceRelation(HrLuPromExpRelation luPromExprienceRelation) {
        this.luPromExprienceRelation = luPromExprienceRelation;
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
        if (!(object instanceof HrPromExprinceCriteria)) {
            return false;
        }
        HrPromExprinceCriteria other = (HrPromExprinceCriteria) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "et.gov.eep.hrms.entity.promotion.HrPromExprinceCriteria[ id=" + id + " ]";
    }
    @Transient
    double yearExprience;

    public double getYearExprience() {
        return yearExprience;
    }

    public void setYearExprience(double yearExprience) {
        this.yearExprience = yearExprience;
    }
    
}
