/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.entity.lookup;

import et.gov.eep.hrms.entity.promotion.HrPromotionEducCriterias;
import et.gov.eep.hrms.entity.recruitment.HrCandidiateEducations;
import et.gov.eep.hrms.entity.training.HrTdPsvcTraineeDetails;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
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
@Table(name = "HR_LU_EDUC_LEVELS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HrLuEducLevels.findAll", query = "SELECT h FROM HrLuEducLevels h"),
    @NamedQuery(name = "HrLuEducLevels.findById", query = "SELECT h FROM HrLuEducLevels h WHERE h.id = :id"),
    @NamedQuery(name = "HrLuEducLevels.findByEducLevel", query = "SELECT h FROM HrLuEducLevels h WHERE h.educLevel = :educLevel"),
    @NamedQuery(name = "HrLuEducLevels.findByRank", query = "SELECT h FROM HrLuEducLevels h WHERE h.rank = :rank")})
public class HrLuEducLevels implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "EDUC_LEVEL")
    private String educLevel;
    @Column(name = "RANK")
    private String rank;
    @OneToMany(mappedBy = "educLevelId", cascade = CascadeType.ALL)
    private List<HrTdPsvcTraineeDetails> hrTdPsvcTraineeDetailsList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "educLevelId")
    private List<HrPromotionEducCriterias> hrPromotionEducCriteriasList;
    @OneToMany(mappedBy = "educLevelId")
    private List<HrCandidiateEducations> hrCandidiateEducationsList;

    /**
     *
     */
    public HrLuEducLevels() {
    }

    /**
     *
     * @param id
     */
    public HrLuEducLevels(Integer id) {
        this.id = id;
    }

    /**
     *
     * @return
     */
    public Integer getId() {
        return id;
    }

    /**
     *
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     *
     * @return
     */
    public String getEducLevel() {
        return educLevel;
    }

    /**
     *
     * @param educLevel
     */
    public void setEducLevel(String educLevel) {
        this.educLevel = educLevel;
    }

    /**
     *
     * @return
     */
    public String getRank() {
        return rank;
    }

    /**
     *
     * @param rank
     */
    public void setRank(String rank) {
        this.rank = rank;
    }

    @XmlTransient
    public List<HrTdPsvcTraineeDetails> getHrTdPsvcTraineeDetailsList() {
        if (hrTdPsvcTraineeDetailsList == null) {
            hrTdPsvcTraineeDetailsList = new ArrayList<>();
        }
        return hrTdPsvcTraineeDetailsList;
    }

    public void setHrTdPsvcTraineeDetailsList(List<HrTdPsvcTraineeDetails> hrTdPsvcTraineeDetailsList) {
        this.hrTdPsvcTraineeDetailsList = hrTdPsvcTraineeDetailsList;
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
        if (!(object instanceof HrLuEducLevels)) {
            return false;
        }
        HrLuEducLevels other = (HrLuEducLevels) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return educLevel;
    }

    @XmlTransient
    public List<HrCandidiateEducations> getHrCandidiateEducationsList() {
        return hrCandidiateEducationsList;
    }

    public void setHrCandidiateEducationsList(List<HrCandidiateEducations> hrCandidiateEducationsList) {
        this.hrCandidiateEducationsList = hrCandidiateEducationsList;
    }

    @XmlTransient
    public List<HrPromotionEducCriterias> getHrPromotionEducCriteriasList() {
        return hrPromotionEducCriteriasList;
    }

    public void setHrPromotionEducCriteriasList(List<HrPromotionEducCriterias> hrPromotionEducCriteriasList) {
        this.hrPromotionEducCriteriasList = hrPromotionEducCriteriasList;
    }

}
