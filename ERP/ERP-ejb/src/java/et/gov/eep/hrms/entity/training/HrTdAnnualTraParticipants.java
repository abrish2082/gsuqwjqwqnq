/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.entity.training;

import et.gov.eep.hrms.entity.employee.HrEmployees;
import java.io.Serializable;
import java.math.BigDecimal;
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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Ob
 */
@Entity
@Table(name = "HR_TD_ANNUAL_TRA_PARTICIPANTS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HrTdAnnualTraParticipants.findAll", query = "SELECT h FROM HrTdAnnualTraParticipants h"),
    @NamedQuery(name = "HrTdAnnualTraParticipants.findById", query = "SELECT h FROM HrTdAnnualTraParticipants h WHERE h.id = :id"),

    @NamedQuery(name = "HrTdAnnualTraParticipants.findByEmpId", query = "SELECT h FROM HrEmployees h WHERE UPPER(h.empId) LIKE :empId ORDER BY h.firstName"),
    @NamedQuery(name = "HrTdAnnualTraParticipants.findByName", query = "SELECT h FROM HrEmployees h WHERE UPPER(h.firstName) LIKE :firstName ORDER BY h.firstName"),
    @NamedQuery(name = "HrTdAnnualTraParticipants.findByIdOrName", query = "SELECT h FROM HrEmployees h WHERE UPPER(h.firstName) LIKE :firstName OR UPPER(h.empId) LIKE :empId"),

    @NamedQuery(name = "HrTdAnnualTraParticipants.findByCourse", query = "SELECT h FROM HrTdAnnualTrainingNeeds h WHERE h.trainingId = :trainingId"),
    @NamedQuery(name = "HrTdAnnualTraParticipants.findByAnnTraNeedId", query = "SELECT h FROM HrTdAnnualTraParticipants h WHERE h.annTraNeedId.id = :annTraNeedId"),
    @NamedQuery(name = "HrTdAnnualTraParticipants.findByTraNeedIdToBeApproved", query = "SELECT h FROM HrTdAnnualTraParticipants h WHERE h.annTraNeedId.id = :annTraNeedId and h.status = 0 "),
    @NamedQuery(name = "HrTdAnnualTraParticipants.findByReason", query = "SELECT h FROM HrTdAnnualTraParticipants h WHERE h.reason = :reason"),
    @NamedQuery(name = "HrTdAnnualTraParticipants.findByStatus", query = "SELECT h FROM HrTdAnnualTraParticipants h WHERE h.status = :status")})
public class HrTdAnnualTraParticipants implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HR_TD_ANNUAL_TRA_PART_SEQ")
    @SequenceGenerator(name = "HR_TD_ANNUAL_TRA_PART_SEQ", sequenceName = "HR_TD_ANNUAL_TRA_PART_SEQ", allocationSize = 1)
    @Column(name = "ID")
    private BigDecimal id;
    @Size(max = 200)
    @Column(name = "REASON")
    private String reason;
    @Size(max = 20)
    @Column(name = "STATUS")
    private String status;
    @JoinColumn(name = "EMP_ID", referencedColumnName = "ID")
    @ManyToOne
    private HrEmployees empId;
    @JoinColumn(name = "ANN_TRA_NEED_ID", referencedColumnName = "ID")
    @ManyToOne
    private HrTdAnnualTrainingNeeds annTraNeedId;

    public HrTdAnnualTraParticipants() {
    }

    public HrTdAnnualTraParticipants(BigDecimal id) {
        this.id = id;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public HrEmployees getEmpId() {
        return empId;
    }

    public void setEmpId(HrEmployees empId) {
        this.empId = empId;
    }

    public HrTdAnnualTrainingNeeds getAnnTraNeedId() {
        return annTraNeedId;
    }

    public void setAnnTraNeedId(HrTdAnnualTrainingNeeds annTraNeedId) {
        this.annTraNeedId = annTraNeedId;
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
        if (!(object instanceof HrTdAnnualTraParticipants)) {
            return false;
        }
        HrTdAnnualTraParticipants other = (HrTdAnnualTraParticipants) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.HrTdAnnualTraParticipants[ id=" + id + " ]";
    }

}
