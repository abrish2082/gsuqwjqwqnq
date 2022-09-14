/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.entity.training;

import et.gov.eep.hrms.entity.employee.HrEmployees;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Benin
 */
@Entity
@Table(name = "HR_TD_ANNUAL_PLAN_PARTICIPANTS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HrTdAnnualPlanParticipants.findAll", query = "SELECT h FROM HrTdAnnualPlanParticipants h"),
    @NamedQuery(name = "HrTdAnnualPlanParticipants.findById", query = "SELECT h FROM HrTdAnnualPlanParticipants h WHERE h.id = :id"),
    @NamedQuery(name = "HrTdAnnualPlanParticipants.findByNewOld", query = "SELECT h FROM HrTdAnnualPlanParticipants h WHERE h.newOld = :newOld"),
    @NamedQuery(name = "HrTdAnnualPlanParticipants.findByStatus", query = "SELECT h FROM HrTdAnnualPlanParticipants h WHERE h.status = :status")})
public class HrTdAnnualPlanParticipants implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private BigDecimal id;
    @Size(max = 20)
    @Column(name = "NEW_OLD")
    private String newOld;
    @Column(name = "STATUS")
    private BigInteger status;
    @JoinColumn(name = "EMP_ID", referencedColumnName = "ID")
    @ManyToOne
    private HrEmployees empId;
    @JoinColumn(name = "GROUP_ID", referencedColumnName = "ID")
    @ManyToOne
    private HrTdAnnualPlanGroups groupId;

    public HrTdAnnualPlanParticipants() {
    }

    public HrTdAnnualPlanParticipants(BigDecimal id) {
        this.id = id;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public String getNewOld() {
        return newOld;
    }

    public void setNewOld(String newOld) {
        this.newOld = newOld;
    }

    public BigInteger getStatus() {
        return status;
    }

    public void setStatus(BigInteger status) {
        this.status = status;
    }

    public HrEmployees getEmpId() {
        return empId;
    }

    public void setEmpId(HrEmployees empId) {
        this.empId = empId;
    }

    public HrTdAnnualPlanGroups getGroupId() {
        return groupId;
    }

    public void setGroupId(HrTdAnnualPlanGroups groupId) {
        this.groupId = groupId;
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
        if (!(object instanceof HrTdAnnualPlanParticipants)) {
            return false;
        }
        HrTdAnnualPlanParticipants other = (HrTdAnnualPlanParticipants) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "et.gov.eep.hrms.entity.training.HrTdAnnualPlanParticipants[ id=" + id + " ]";
    }
    
}
