/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.entity.lookup;

import et.gov.eep.fcms.entity.perDiem.FmsLuPerdimeRate;
import et.gov.eep.hrms.entity.allowance.HrAllowanceInLevels;
import et.gov.eep.hrms.entity.allowance.HrAllowanceInLocations;
import et.gov.eep.hrms.entity.organization.HrJobTypes;
import et.gov.eep.hrms.entity.organization.HrSalaryScaleRanges;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
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
@Table(name = "HR_LU_JOB_LEVELS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HrLuJobLevels.findAll", query = "SELECT h FROM HrLuJobLevels h"),
    @NamedQuery(name = "HrLuJobLevels.findById", query = "SELECT h FROM HrLuJobLevels h WHERE h.id = :id"),
    @NamedQuery(name = "HrLuJobLevels.findByJobLevel", query = "SELECT h FROM HrLuJobLevels h WHERE h.jobLevel = :jobLevel"),
    @NamedQuery(name = "HrLuJobLevels.findByDescription", query = "SELECT h FROM HrLuJobLevels h WHERE h.description = :description")})
public class HrLuJobLevels implements Serializable {

    @OneToMany(mappedBy = "levelId")
    private List<HrAllowanceInLocations> hrAllowanceInLocationsList;
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HR_LU_JOB_LEVELS_SEQ")
    @SequenceGenerator(name = "HR_LU_JOB_LEVELS_SEQ", sequenceName = "HR_LU_JOB_LEVELS_SEQ", allocationSize = 1)
    @Basic(optional = false)
    @Column(name = "ID")
    private BigDecimal id;
    @Column(name = "JOB_LEVEL")
    private String jobLevel;
    @Column(name = "DESCRIPTION")
    private String description;
//    @JoinColumn(name = "JOB_CATEGORY_ID", referencedColumnName = "ID")
//    @ManyToOne
//    private HrLuJobCategories jobCategoryId;
    @OneToMany(mappedBy = "levelId")
    private List<HrSalaryScaleRanges> hrSalaryScaleRangesList;
    @OneToMany(mappedBy = "levelId")
    private List<HrAllowanceInLevels> hrAllowanceInLevelsList;
    @OneToMany(mappedBy = "jobLevelId")
    private List<HrJobTypes> hrJobTypesList;
    @OneToOne(mappedBy = "jobLevelId", fetch = FetchType.LAZY)
    private List<FmsLuPerdimeRate> fmsLuPerdiemeList;

    /**
     *
     */
    public HrLuJobLevels() {
    }

    /**
     *
     * @param id
     */
    public HrLuJobLevels(BigDecimal id) {
        this.id = id;
    }

    /**
     *
     * @return
     */
    public BigDecimal getId() {
        return id;
    }

    /**
     *
     * @param id
     */
    public void setId(BigDecimal id) {
        this.id = id;
    }

    /**
     *
     * @return
     */
    public String getJobLevel() {
        return jobLevel;
    }

    /**
     *
     * @param jobLevel
     */
    public void setJobLevel(String jobLevel) {
        this.jobLevel = jobLevel;
    }

    /**
     *
     * @return
     */
    public String getDescription() {
        return description;
    }

    /**
     *
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     *
     * @return
     */
    /**
     *
     * @return
     */
    @XmlTransient
    public List<HrSalaryScaleRanges> getHrSalaryScaleRangesList() {
        return hrSalaryScaleRangesList;
    }

    /**
     *
     * @param hrSalaryScaleRangesList
     */
    public void setHrSalaryScaleRangesList(List<HrSalaryScaleRanges> hrSalaryScaleRangesList) {
        this.hrSalaryScaleRangesList = hrSalaryScaleRangesList;
    }

    /**
     *
     * @return
     */
    @XmlTransient
    public List<HrAllowanceInLevels> getHrAllowanceInLevelsList() {
        return hrAllowanceInLevelsList;
    }

    /**
     *
     * @param hrAllowanceInLevelsList
     */
    public void setHrAllowanceInLevelsList(List<HrAllowanceInLevels> hrAllowanceInLevelsList) {
        this.hrAllowanceInLevelsList = hrAllowanceInLevelsList;
    }

    /**
     *
     * @return
     */
    @XmlTransient
    public List<HrJobTypes> getHrJobTypesList() {
        return hrJobTypesList;
    }

    /**
     *
     * @param hrJobTypesList
     */
    public void setHrJobTypesList(List<HrJobTypes> hrJobTypesList) {
        this.hrJobTypesList = hrJobTypesList;
    }

    @XmlTransient
    public List<FmsLuPerdimeRate> getFmsLuPerdiemeList() {
        return fmsLuPerdiemeList;
    }

    public void setFmsLuPerdiemeList(List<FmsLuPerdimeRate> fmsLuPerdiemeList) {
        this.fmsLuPerdiemeList = fmsLuPerdiemeList;
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
        if (!(object instanceof HrLuJobLevels)) {
            return false;
        }
        HrLuJobLevels other = (HrLuJobLevels) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return jobLevel;
    }

    @XmlTransient
    public List<HrAllowanceInLocations> getHrAllowanceInLocationsList() {
        return hrAllowanceInLocationsList;
    }

    public void setHrAllowanceInLocationsList(List<HrAllowanceInLocations> hrAllowanceInLocationsList) {
        this.hrAllowanceInLocationsList = hrAllowanceInLocationsList;
    }

}
