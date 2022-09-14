/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.entity.recruitment;

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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author user
 */
@Entity
@Table(name = "HR_CANDIDATE_EMP_HISTORIES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HrCandidateEmpHistories.findAll", query = "SELECT h FROM HrCandidateEmpHistories h"),
    @NamedQuery(name = "HrCandidateEmpHistories.findById", query = "SELECT h FROM HrCandidateEmpHistories h WHERE h.id = :id"),
    @NamedQuery(name = "HrCandidateEmpHistories.findByInstitution", query = "SELECT h FROM HrCandidateEmpHistories h WHERE h.institution = :institution"),
    @NamedQuery(name = "HrCandidateEmpHistories.findByAddressForEmployer", query = "SELECT h FROM HrCandidateEmpHistories h WHERE h.addressForEmployer = :addressForEmployer"),
    @NamedQuery(name = "HrCandidateEmpHistories.findByJobTitle", query = "SELECT h FROM HrCandidateEmpHistories h WHERE h.jobTitle = :jobTitle"),
    @NamedQuery(name = "HrCandidateEmpHistories.findBySalary", query = "SELECT h FROM HrCandidateEmpHistories h WHERE h.salary = :salary"),
    @NamedQuery(name = "HrCandidateEmpHistories.findByStartDate", query = "SELECT h FROM HrCandidateEmpHistories h WHERE h.startDate = :startDate"),
    @NamedQuery(name = "HrCandidateEmpHistories.findByEndDate", query = "SELECT h FROM HrCandidateEmpHistories h WHERE h.endDate = :endDate"),
    @NamedQuery(name = "HrCandidateEmpHistories.findByKeyDutyResponsibility", query = "SELECT h FROM HrCandidateEmpHistories h WHERE h.keyDutyResponsibility = :keyDutyResponsibility"),
    @NamedQuery(name = "HrCandidateEmpHistories.findByReasonForTermination", query = "SELECT h FROM HrCandidateEmpHistories h WHERE h.reasonForTermination = :reasonForTermination")})
public class HrCandidateEmpHistories implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HR_CANDIDATE_EMP_HISTORIES_SEQ")
    @SequenceGenerator(name = "HR_CANDIDATE_EMP_HISTORIES_SEQ", sequenceName = "HR_CANDIDATE_EMP_HISTORIES_SEQ", allocationSize = 1)
    @Column(name = "ID")
    private BigDecimal id;
    @Size(max = 20)
    @Column(name = "INSTITUTION")
    private String institution;
    @Size(max = 100)
    @Column(name = "ADDRESS_FOR_EMPLOYER")
    private String addressForEmployer;
    @Size(max = 50)
    @Column(name = "JOB_TITLE")
    private String jobTitle;
    @Column(name = "SALARY")
    private Float salary;
    @Size(max = 20)
    @Column(name = "START_DATE")
    private String startDate;
    @Size(max = 20)
    @Column(name = "END_DATE")
    private String endDate;
    @Size(max = 400)
    @Column(name = "KEY_DUTY_RESPONSIBILITY")
    private String keyDutyResponsibility;
    @Size(max = 400)
    @Column(name = "REASON_FOR_TERMINATION")
    private String reasonForTermination;
    @JoinColumn(name = "CANDIDATE_ID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private HrCandidiates candidateId;

    public HrCandidateEmpHistories() {
    }

    public HrCandidateEmpHistories(BigDecimal id) {
        this.id = id;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public String getInstitution() {
        return institution;
    }

    public void setInstitution(String institution) {
        this.institution = institution;
    }

    public String getAddressForEmployer() {
        return addressForEmployer;
    }

    public void setAddressForEmployer(String addressForEmployer) {
        this.addressForEmployer = addressForEmployer;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public Float getSalary() {
        return salary;
    }

    public void setSalary(Float salary) {
        this.salary = salary;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getKeyDutyResponsibility() {
        return keyDutyResponsibility;
    }

    public void setKeyDutyResponsibility(String keyDutyResponsibility) {
        this.keyDutyResponsibility = keyDutyResponsibility;
    }

    public String getReasonForTermination() {
        return reasonForTermination;
    }

    public void setReasonForTermination(String reasonForTermination) {
        this.reasonForTermination = reasonForTermination;
    }

    public HrCandidiates getCandidateId() {
        return candidateId;
    }

    public void setCandidateId(HrCandidiates candidateId) {
        this.candidateId = candidateId;
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
        if (!(object instanceof HrCandidateEmpHistories)) {
            return false;
        }
        HrCandidateEmpHistories other = (HrCandidateEmpHistories) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "et.gov.eep.hrms.entity.recruitment.HrCandidateEmpHistories[ id=" + id + " ]";
    }

}
