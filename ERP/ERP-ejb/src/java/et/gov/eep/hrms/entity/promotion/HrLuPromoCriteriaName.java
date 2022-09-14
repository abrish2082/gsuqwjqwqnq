/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.entity.promotion;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author user
 */
@Entity
@Table(name = "HR_LU_PROMO_CRITERIA_NAME")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HrLuPromoCriteriaName.findAll", query = "SELECT h FROM HrLuPromoCriteriaName h"),
    @NamedQuery(name = "HrLuPromoCriteriaName.findById", query = "SELECT h FROM HrLuPromoCriteriaName h WHERE h.id = :id"),
    @NamedQuery(name = "HrLuPromoCriteriaName.findByDescription", query = "SELECT h FROM HrLuPromoCriteriaName h WHERE h.description = :description")})
public class HrLuPromoCriteriaName implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HR_LU_PROMO_CRITERIA_NAME_SEQ")
    @SequenceGenerator(name = "HR_LU_PROMO_CRITERIA_NAME_SEQ", sequenceName = "HR_LU_PROMO_CRITERIA_NAME_SEQ", allocationSize = 1)
    private Integer id;
    
    @Size(max = 60)
    @Column(name = "DESCRIPTION")
    private String description;
    
    @OneToMany(mappedBy = "luPromoCriteriaNameId",cascade = CascadeType.ALL)
    private List<HrPromotionCriterias> hrPromotionCriteriasList = new ArrayList<>();

    public HrLuPromoCriteriaName() {
    }

    public HrLuPromoCriteriaName(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @XmlTransient
    public List<HrPromotionCriterias> getHrPromotionCriteriasList() {
        return hrPromotionCriteriasList;
    }

    public void setHrPromotionCriteriasList(List<HrPromotionCriterias> hrPromotionCriteriasList) {
        this.hrPromotionCriteriasList = hrPromotionCriteriasList;
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
        if (!(object instanceof HrLuPromoCriteriaName)) {
            return false;
        }
        HrLuPromoCriteriaName other = (HrLuPromoCriteriaName) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return description;//"et.gov.eep.hrms.entity.promotion.HrLuPromoCriteriaName[ id=" + id + " ]";
    }
    
}
