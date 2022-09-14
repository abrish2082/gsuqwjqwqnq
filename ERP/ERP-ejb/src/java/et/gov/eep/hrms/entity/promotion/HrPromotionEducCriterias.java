/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.entity.promotion;

import et.gov.eep.hrms.entity.lookup.HrLuEducLevels;
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
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author user
 */
@Entity
@Table(name = "HR_PROMOTION_EDUC_CRITERIAS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HrPromotionEducCriterias.findAll", query = "SELECT h FROM HrPromotionEducCriterias h"),
    @NamedQuery(name = "HrPromotionEducCriterias.findById", query = "SELECT h FROM HrPromotionEducCriterias h WHERE h.id = :id"),
    @NamedQuery(name = "HrPromotionEducCriterias.findByWeghit", query = "SELECT h FROM HrPromotionEducCriterias h WHERE h.weghit = :weghit")})
public class HrPromotionEducCriterias implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HR_PROMOTION_EDUC_CRITERIA_SEQ")
    @SequenceGenerator( name = "HR_PROMOTION_EDUC_CRITERIA_SEQ", sequenceName = "HR_PROMOTION_EDUC_CRITERIA_SEQ", allocationSize = 1)
    private Integer id;
    
    @Column(name = "WEGHIT")
    private Double weghit;
    
    @JoinColumn(name = "EDUC_LEVEL_ID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private HrLuEducLevels educLevelId;

    public HrPromotionEducCriterias() {
    }

    public HrPromotionEducCriterias(Integer id) {
        this.id = id;
    }

    public HrPromotionEducCriterias(Integer id, Double weghit) {
        this.id = id;
        this.weghit = weghit;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getWeghit() {
        return weghit;
    }

    public void setWeghit(Double weghit) {
        this.weghit = weghit;
    }

    public HrLuEducLevels getEducLevelId() {
        return educLevelId;
    }

    public void setEducLevelId(HrLuEducLevels educLevelId) {
        this.educLevelId = educLevelId;
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
        if (!(object instanceof HrPromotionEducCriterias)) {
            return false;
        }
        HrPromotionEducCriterias other = (HrPromotionEducCriterias) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "et.gov.eep.hrms.entity.promotion.HrPromotionEducCriterias[ id=" + id + " ]";
    }
    
}
