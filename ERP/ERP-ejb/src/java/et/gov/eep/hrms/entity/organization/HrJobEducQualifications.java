/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.entity.organization;

import et.gov.eep.hrms.entity.lookup.HrLuEducLevels;
import et.gov.eep.hrms.entity.lookup.HrLuEducTypes;
import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
 * @author Baya
 */
@Entity
@Table(name = "HR_JOB_EDUC_QUALIFICATIONS")
@XmlRootElement
@NamedQueries//<editor-fold defaultstate="collapsed" desc="">
        ({
            @NamedQuery(name = "HrJobEducQualifications.findAll", query = "SELECT h FROM HrJobEducQualifications h"),
            @NamedQuery(name = "HrJobEducQualifications.findById", query = "SELECT h FROM HrJobEducQualifications h WHERE h.id = :id"),
            @NamedQuery(name = "HrJobEducQualifications.findByJobID", query = "SELECT h FROM HrJobEducQualifications h WHERE h.jobId = :jobId"),
            @NamedQuery(name = "HrJobEducQualifications.findByJobId", query = "SELECT h FROM HrJobEducQualifications h WHERE h.jobId.id = :jobId"),
            @NamedQuery(name = "HrJobEducQualifications.findByMinExperiance", query = "SELECT h FROM HrJobEducQualifications h WHERE h.minExperiance = :minExperiance")
        })
//</editor-fold>

public class HrJobEducQualifications implements Serializable {

    //<editor-fold defaultstate="collapsed" desc="Entity Attributes">
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)//
    @NotNull
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HR_JOB_EDUC_QUALIFICATIONS_SEQ")
    @SequenceGenerator(name = "HR_JOB_EDUC_QUALIFICATIONS_SEQ", sequenceName = "HR_JOB_EDUC_QUALIFICATIONS_SEQ", allocationSize = 1)
    private BigDecimal id;
    @Column(name = "MIN_EXPERIANCE")
    private Integer minExperiance;
    @JoinColumn(name = "JOB_ID", referencedColumnName = "ID")
    @ManyToOne(cascade = CascadeType.ALL)
    private HrJobTypes jobId;
    @JoinColumn(name = "EDUC_LEVEL_ID", referencedColumnName = "ID")
    @ManyToOne
    private HrLuEducLevels educLevelId;
    @JoinColumn(name = "EDUC_QUAL_ID", referencedColumnName = "ID")
    @ManyToOne
    private HrLuEducTypes educQualId;
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Getter and setter">
    public HrJobEducQualifications() {
    }
    
    /**
     *
     * @param id
     */
    public HrJobEducQualifications(BigDecimal id) {
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
    public Integer getMinExperiance() {
        return minExperiance;
    }
    
    /**
     *
     * @param minExperiance
     */
    public void setMinExperiance(Integer minExperiance) {
        this.minExperiance = minExperiance;
    }
    
    /**
     *
     * @return
     */
    public HrJobTypes getJobId() {
        return jobId;
    }
    
    /**
     *
     * @param jobId
     */
    public void setJobId(HrJobTypes jobId) {
        this.jobId = jobId;
    }
    
    /**
     *
     * @return
     */
    public HrLuEducLevels getEducLevelId() {
        return educLevelId;
    }
    
    /**
     *
     * @param educLevelId
     */
    public void setEducLevelId(HrLuEducLevels educLevelId) {
        this.educLevelId = educLevelId;
    }
    
    /**
     *
     * @return
     */
    public HrLuEducTypes getEducQualId() {
        return educQualId;
    }
    
    /**
     *
     * @param educQualId
     */
    public void setEducQualId(HrLuEducTypes educQualId) {
        this.educQualId = educQualId;
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Other Methods">
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }
    
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HrJobEducQualifications)) {
            return false;
        }
        HrJobEducQualifications other = (HrJobEducQualifications) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }
    
    @Override
    public String toString() {
        return "et.gov.eep.hrms.entity.HrJobEducQualifications[ id=" + id + " ]";
    }
//</editor-fold>

}
