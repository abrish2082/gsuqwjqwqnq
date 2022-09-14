/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.entity.committee;

import et.gov.eep.hrms.entity.employee.HrEmployees;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author user
 */
@Entity
@Table(name = "HR_COMMITTEE_MEMBERS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HrCommitteeMembers.findAll", query = "SELECT h FROM HrCommitteeMembers h"),
    @NamedQuery(name = "HrCommitteeMembers.findById", query = "SELECT h FROM HrCommitteeMembers h WHERE h.id = :id"),
    @NamedQuery(name = "HrCommitteeMembers.findByResponsiblity", query = "SELECT h FROM HrCommitteeMembers h WHERE h.responsiblity = :responsiblity"),
    @NamedQuery(name = "HrCommitteeMembers.findByAssignedDate", query = "SELECT h FROM HrCommitteeMembers h WHERE h.assignedDate = :assignedDate"),
    @NamedQuery(name = "HrCommitteeMembers.findByRemark", query = "SELECT h FROM HrCommitteeMembers h WHERE h.remark = :remark"),
    @NamedQuery(name = "HrCommitteeMembers.findByCommittee", query = "SELECT h FROM HrCommitteeMembers h WHERE h.committeeId.id = :id")})
public class HrCommitteeMembers implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HR_COMMITTEE_MEMBERS_SEQ_GENERATOR")
    @SequenceGenerator(name = "HR_COMMITTEE_MEMBERS_SEQ_GENERATOR", sequenceName = "HR_COMMITTEE_MEMBERS_SEQ", allocationSize = 1)
    private BigDecimal id;

    @Size(max = 20)
    @Column(name = "RESPONSIBLITY")
    private String responsiblity;

    @Column(name = "END_DATE")
    private String endDate;
    
    @Size(max = 200)
    @Column(name = "REASON_FOR_END")
    private String reasonForEnd;
    
    
    @Column(name = "ASSIGNED_DATE")
    private String assignedDate;

    @Size(max = 200)
    @Column(name = "REMARK")
    private String remark;

   

    @JoinColumn(name = "COMMITTEE_ID", referencedColumnName = "ID")
    @ManyToOne
    private HrCommittees committeeId;

    @JoinColumn(name = "EMP_ID", referencedColumnName = "ID")
    @ManyToOne
    private HrEmployees empId;

    public HrCommitteeMembers() {
    }

    public HrCommitteeMembers(BigDecimal id) {
        this.id = id;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public String getResponsiblity() {
        return responsiblity;
    }

    public void setResponsiblity(String responsiblity) {
        this.responsiblity = responsiblity;
    }

    public String getAssignedDate() {
        return assignedDate;
    }

    public void setAssignedDate(String assignedDate) {
        this.assignedDate = assignedDate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getReasonForEnd() {
        return reasonForEnd;
    }

    public void setReasonForEnd(String reasonForEnd) {
        this.reasonForEnd = reasonForEnd;
    }


    public HrCommittees getCommitteeId() {
        return committeeId;
    }

    public void setCommitteeId(HrCommittees committeeId) {
        this.committeeId = committeeId;
    }

    public HrEmployees getEmpId() {
        return empId;
    }

    public void setEmpId(HrEmployees empId) {
        this.empId = empId;
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
        if (!(object instanceof HrCommitteeMembers)) {
            return false;
        }
        HrCommitteeMembers other = (HrCommitteeMembers) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return  id.toString();
    }

}
