/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.entity.organization;

import et.gov.eep.hrms.entity.lookup.HrLuEducLevels;
import et.gov.eep.hrms.entity.lookup.HrLuEducTypes;
import java.io.Serializable;
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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author prg
 */
@Entity
@Table(name = "hr_job_qualification")
@XmlRootElement
@NamedQueries//<editor-fold defaultstate="collapsed" desc="">
        ({
            @NamedQuery(name = "HrJobQualification.findAll", query = "SELECT h FROM HrJobQualification h"),
            @NamedQuery(name = "HrJobQualification.findById", query = "SELECT h FROM HrJobQualification h WHERE h.id = :id"),
            @NamedQuery(name = "HrJobQualification.findByQualification", query = "SELECT h FROM HrJobQualification h WHERE h.qualification = :qualification"),
            @NamedQuery(name = "HrJobQualification.findByMinExperience", query = "SELECT h FROM HrJobQualification h WHERE h.minExperience = :minExperience")})
//</editor-fold>

public class HrJobQualification implements Serializable {
//<editor-fold defaultstate="collapsed" desc="Entity Attributes">
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HR_JOB_QUALIFICATION_ID_SEQ")
    @SequenceGenerator( name = "HR_JOB_QUALIFICATION_ID_SEQ", sequenceName = "HR_JOB_QUALIFICATION_ID_SEQ", allocationSize = 1)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "qualification")
    private String qualification;
    @Basic(optional = false)
    @NotNull
    @Column(name = "min_experience")
    private int minExperience;
    @JoinColumn(name = "lu_edu_lvl_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private HrLuEducLevels luEduLvlId;
    @JoinColumn(name = "lu_field_of_study_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private HrLuEducTypes luFieldOfStudyId;
    @JoinColumn(name = "job_type_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private HrJobTypes jobTypeId;
//</editor-fold>

 //<editor-fold defaultstate="collapsed" desc="Getter and setter">
    
    public HrJobQualification() {
    }
    
    /**
     *
     * @param id
     */
    public HrJobQualification(Integer id) {
        this.id = id;
    }
    
    /**
     *
     * @param id
     * @param qualification
     * @param minExperience
     */
    public HrJobQualification(Integer id, String qualification, int minExperience) {
        this.id = id;
        this.qualification = qualification;
        this.minExperience = minExperience;
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
    public String getQualification() {
        return qualification;
    }
    
    /**
     *
     * @param qualification
     */
    public void setQualification(String qualification) {
        this.qualification = qualification;
    }
    
    /**
     *
     * @return
     */
    public int getMinExperience() {
        return minExperience;
    }
    
    /**
     *
     * @param minExperience
     */
    public void setMinExperience(int minExperience) {
        this.minExperience = minExperience;
    }
    
    /**
     *
     * @return
     */
    public HrLuEducLevels getLuEduLvlId() {
        return luEduLvlId;
    }
    
    /**
     *
     * @param luEduLvlId
     */
    public void setLuEduLvlId(HrLuEducLevels luEduLvlId) {
        this.luEduLvlId = luEduLvlId;
    }
    
    /**
     *
     * @return
     */
    public HrLuEducTypes getLuFieldOfStudyId() {
        return luFieldOfStudyId;
    }
    
    /**
     *
     * @param luFieldOfStudyId
     */
    public void setLuFieldOfStudyId(HrLuEducTypes luFieldOfStudyId) {
        this.luFieldOfStudyId = luFieldOfStudyId;
    }
    
    /**
     *
     * @return
     */
    public HrJobTypes getJobTypeId() {
        return jobTypeId;
    }
    
    /**
     *
     * @param jobTypeId
     */
    public void setJobTypeId(HrJobTypes jobTypeId) {
        this.jobTypeId = jobTypeId;
    }
//</editor-fold>
    
 //<editor-fold defaultstate="collapsed" desc="other Methods">
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }
    
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HrJobQualification)) {
            return false;
        }
        HrJobQualification other = (HrJobQualification) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }
    
    @Override
    public String toString() {
        return "et.gov.insa.erp.hrms.entity.HrJobQualification[ id=" + id + " ]";
    }
    
//</editor-fold>
}
