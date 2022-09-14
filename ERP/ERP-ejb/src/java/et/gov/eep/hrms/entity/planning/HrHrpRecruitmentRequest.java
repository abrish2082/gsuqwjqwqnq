/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.entity.planning;

import et.gov.eep.hrms.entity.employee.HrEmployees;
import et.gov.eep.hrms.entity.organization.HrJobTypes;
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
 * @author Ob
 */
@Entity
@Table(name = "HR_HRP_RECRUITMENT_REQUEST")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HrHrpRecruitmentRequest.findAll", query = "SELECT h FROM HrHrpRecruitmentRequest h"),
    @NamedQuery(name = "HrHrpRecruitmentRequest.findById", query = "SELECT h FROM HrHrpRecruitmentRequest h WHERE h.id = :id"),
    @NamedQuery(name = "HrHrpRecruitmentRequest.findByRecruitmentId", query = "SELECT h FROM HrHrpRecruitmentRequest h WHERE h.recruitmentId.id = :recruitmentId"),
    @NamedQuery(name = "HrHrpRecruitmentRequest.findByNumberRequestedPosition", query = "SELECT h FROM HrHrpRecruitmentRequest h WHERE h.numberRequestedPosition = :numberRequestedPosition"),
    @NamedQuery(name = "HrHrpRecruitmentRequest.findByVacancySource", query = "SELECT h FROM HrHrpRecruitmentRequest h WHERE h.vacancySource = :vacancySource"),
    @NamedQuery(name = "HrHrpRecruitmentRequest.findByRemark", query = "SELECT h FROM HrHrpRecruitmentRequest h WHERE h.remark = :remark"),
    @NamedQuery(name = "HrHrpRecruitmentRequest.findByAnalysedOn", query = "SELECT h FROM HrHrpRecruitmentRequest h WHERE h.analysedOn = :analysedOn"),
    @NamedQuery(name = "HrHrpRecruitmentRequest.findByHowToBeFilled", query = "SELECT h FROM HrHrpRecruitmentRequest h WHERE UPPER(h.howToBeFilled) LIKE :howToBeFilled"),
    @NamedQuery(name = "HrHrpRecruitmentRequest.findByStatus", query = "SELECT h FROM HrHrpRecruitmentRequest h WHERE h.status = :status")})
public class HrHrpRecruitmentRequest implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
     @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HR_HRP_RECRUITMENT_REQUEST_SEQ")
    @SequenceGenerator(name = "HR_HRP_RECRUITMENT_REQUEST_SEQ", sequenceName = "HR_HRP_RECRUITMENT_REQUEST_SEQ", allocationSize = 1)
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private BigDecimal id;
    @Column(name = "NUMBER_REQUESTED_POSITION")
    private Integer numberRequestedPosition;
    @Column(name = "VACANCY_SOURCE")
    private String vacancySource;
    @Column(name = "REMARK")
    private String remark;
    @Column(name = "MONTHS")
    private String months;
    @Column(name = "HOW_TO_BE_FILLED")
    private String howToBeFilled;
    @Column(name = "ANALYSED_ON")
    private String analysedOn;
    @Column(name = "STATUS")
    private Integer status;
    
    @JoinColumn(name = "ANALYSED_BY", referencedColumnName = "ID")
    @ManyToOne
    private HrEmployees analysedBy;
    
    @JoinColumn(name = "RECRUITMENT_ID", referencedColumnName = "ID")
    @ManyToOne
    private HrHrpRecruitments recruitmentId;
    
    @JoinColumn(name = "JOB_ID", referencedColumnName = "ID")
    @ManyToOne
    private HrJobTypes jobId;

    public HrHrpRecruitmentRequest() {
    }

    public HrHrpRecruitmentRequest(BigDecimal id) {
        this.id = id;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public Integer getNumberRequestedPosition() {
        return numberRequestedPosition;
    }

    public void setNumberRequestedPosition(Integer numberRequestedPosition) {
        this.numberRequestedPosition = numberRequestedPosition;
    }

    public String getVacancySource() {
        return vacancySource;
    }

    public void setVacancySource(String vacancySource) {
        this.vacancySource = vacancySource;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getMonths() {
        return months;
    }

    public void setMonths(String months) {
        this.months = months;
    }  
    
    public String getAnalysedOn() {
        return analysedOn;
    }

    public void setAnalysedOn(String analysedOn) {
        this.analysedOn = analysedOn;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public HrEmployees getAnalysedBy() {
        return analysedBy;
    }

    public void setAnalysedBy(HrEmployees analysedBy) {
        this.analysedBy = analysedBy;
    }

    public HrHrpRecruitments getRecruitmentId() {
        return recruitmentId;
    }

    public void setRecruitmentId(HrHrpRecruitments recruitmentId) {
        this.recruitmentId = recruitmentId;
    }

    public HrJobTypes getJobId() {
        return jobId;
    }

    public void setJobId(HrJobTypes jobId) {
        this.jobId = jobId;
    }

    public String getHowToBeFilled() {
        return howToBeFilled;
    }

    public void setHowToBeFilled(String howToBeFilled) {
        this.howToBeFilled = howToBeFilled;
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
        if (!(object instanceof HrHrpRecruitmentRequest)) {
            return false;
        }
        HrHrpRecruitmentRequest other = (HrHrpRecruitmentRequest) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.HrHrpRecruitmentRequest[ id=" + id + " ]";
    }
    
}
