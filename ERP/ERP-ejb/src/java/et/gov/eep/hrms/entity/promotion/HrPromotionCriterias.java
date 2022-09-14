/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.entity.promotion;

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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author user
 */
@Entity
@Table(name = "HR_PROMOTION_CRITERIAS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HrPromotionCriterias.findAll", query = "SELECT h FROM HrPromotionCriterias h"),
    @NamedQuery(name = "HrPromotionCriterias.findById", query = "SELECT h FROM HrPromotionCriterias h WHERE h.id = :id"),
    @NamedQuery(name = "HrPromotionCriterias.findByWeight", query = "SELECT h FROM HrPromotionCriterias h WHERE h.weight = :weight"),
    @NamedQuery(name = "HrPromotionCriterias.findByIncrementRate", query = "SELECT h FROM HrPromotionCriterias h WHERE h.incrementRate = :incrementRate")})
public class HrPromotionCriterias implements Serializable {
  
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HR_PROMOTION_CRITERIAS_SEQ")
    @SequenceGenerator(name = "HR_PROMOTION_CRITERIAS_SEQ", sequenceName = "HR_PROMOTION_CRITERIAS_SEQ", allocationSize = 1)
    private Integer id;

   
    @Column(name = "INCREMENT_RATE")
    private double incrementRate;

    @Column(name = "WEIGHT")
    private Double weight;
    
    @JoinColumn(name = "LU_PROMO_CRITERIA_NAME_ID", referencedColumnName = "ID")
    @ManyToOne
    private HrLuPromoCriteriaName luPromoCriteriaNameId;

    public HrPromotionCriterias() {
    }

    public HrPromotionCriterias(Integer id) {
        this.id = id;
    }

    public HrPromotionCriterias(Integer id, double weight, double incrementRate) {
        this.id = id;
        this.weight = weight;
        this.incrementRate = incrementRate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public double getIncrementRate() {
        return incrementRate;
    }

    public void setIncrementRate(double incrementRate) {
        this.incrementRate = incrementRate;
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
        if (!(object instanceof HrPromotionCriterias)) {
            return false;
        }
        HrPromotionCriterias other = (HrPromotionCriterias) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "et.gov.eep.hrms.entity.promotion.HrPromotionCriterias[ id=" + id + " ]";
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public HrLuPromoCriteriaName getLuPromoCriteriaNameId() {
        return luPromoCriteriaNameId;
    }

    public void setLuPromoCriteriaNameId(HrLuPromoCriteriaName luPromoCriteriaNameId) {
        this.luPromoCriteriaNameId = luPromoCriteriaNameId;
    }

}
