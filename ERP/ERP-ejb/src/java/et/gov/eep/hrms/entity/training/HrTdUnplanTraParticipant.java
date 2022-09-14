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
 * @author Abdi_Pc
 */
@Entity
@Table(name = "HR_TD_UNPLAN_TRA_PARTICIPANT")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HrTdUnplanTraParticipant.findAll", query = "SELECT h FROM HrTdUnplanTraParticipant h"),
    @NamedQuery(name = "HrTdUnplanTraParticipant.findById", query = "SELECT h FROM HrTdUnplanTraParticipant h WHERE h.id = :id"),
    @NamedQuery(name = "HrTdUnplanTraParticipant.findByReason", query = "SELECT h FROM HrTdUnplanTraParticipant h WHERE h.reason = :reason"),

    @NamedQuery(name = "HrTdUnplanTraParticipant.findByAnnTraId", query = "SELECT h FROM HrTdAnnualTraParticipants h WHERE h.annTraNeedId = :annualNeedRequestId and h.status =0"),

    @NamedQuery(name = "HrTdUnplanTraParticipant.findByATrainId", query = "SELECT h FROM HrTdAnnualTrainingNeeds h WHERE h.annualNeedRequestId = :course"),

    @NamedQuery(name = "HrTdUnplanTraParticipant.findByAnnTraNeedId", query = "SELECT h FROM HrTdAnnualTraParticipants h WHERE h.annTraNeedId = :id"),
    @NamedQuery(name = "HrTdUnplanTraParticipant.findByStatus", query = "SELECT h FROM HrTdUnplanTraParticipant h WHERE h.status = :status")})
public class HrTdUnplanTraParticipant implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HR_TD_UNPLAN_TRA_PART_SEQ")
    @SequenceGenerator(name = "HR_TD_UNPLAN_TRA_PART_SEQ", sequenceName = "HR_TD_UNPLAN_TRA_PART_SEQ", allocationSize = 1)
    @NotNull
    @Column(name = "ID")
    private BigDecimal id;
    @Size(max = 200)
    @Column(name = "REASON")
    private String reason;
    @Column(name = "STATUS")
    private Integer status;
    @JoinColumn(name = "EMP_ID", referencedColumnName = "ID")
    @ManyToOne
    private HrEmployees empId;
    @JoinColumn(name = "UNP_TRA_REQ_ID", referencedColumnName = "ID")
    @ManyToOne
    private HrTdUnplanTrainingRequest unpTraReqId;

    public HrTdUnplanTraParticipant() {
    }

    public HrTdUnplanTraParticipant(BigDecimal id) {
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public HrEmployees getEmpId() {
        return empId;
    }

    public void setEmpId(HrEmployees empId) {
        this.empId = empId;
    }

    public HrTdUnplanTrainingRequest getUnpTraReqId() {
        return unpTraReqId;
    }

    public void setUnpTraReqId(HrTdUnplanTrainingRequest unpTraReqId) {
        this.unpTraReqId = unpTraReqId;
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
        if (!(object instanceof HrTdUnplanTraParticipant)) {
            return false;
        }
        HrTdUnplanTraParticipant other = (HrTdUnplanTraParticipant) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entit.HrTdUnplanTraParticipant[ id=" + id + " ]";
    }

}
