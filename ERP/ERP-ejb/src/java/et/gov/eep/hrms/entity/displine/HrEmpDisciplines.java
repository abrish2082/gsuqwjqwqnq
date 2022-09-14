/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.entity.displine;

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
 * @author Abdi
 */
@Entity
@Table(name = "HR_EMP_DISCIPLINES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HrEmpDisciplines.findAll", query = "SELECT h FROM HrEmpDisciplines h"),
    @NamedQuery(name = "HrEmpDisciplines.findById", query = "SELECT h FROM HrEmpDisciplines h WHERE h.id = :id"),
    @NamedQuery(name = "HrEmpDisciplines.findByDescriptionOfOffence", query = "SELECT h FROM HrEmpDisciplines h WHERE h.descriptionOfOffence = :descriptionOfOffence"),
    @NamedQuery(name = "HrEmpDisciplines.findByRepititionOfOffence", query = "SELECT h FROM HrEmpDisciplines h WHERE h.repititionOfOffence = :repititionOfOffence"),
    @NamedQuery(name = "HrEmpDisciplines.findByDecisionOnPenality", query = "SELECT h FROM HrEmpDisciplines h WHERE h.decisionOnPenality = :decisionOnPenality"),
    @NamedQuery(name = "HrEmpDisciplines.findByPenaltyClassfication", query = "SELECT h FROM HrEmpDisciplines h WHERE h.penaltyClassfication = :penaltyClassfication"),
    @NamedQuery(name = "HrEmpDisciplines.findByRequestId", query = "SELECT h FROM HrEmpDisciplines h WHERE h.requestId = :requestId"),
    @NamedQuery(name = "HrEmpDisciplines.findByStatus", query = "SELECT h FROM HrEmpDisciplines h WHERE h.status = :status"),
    @NamedQuery(name = "HrEmpDisciplines.findByDecisionDate", query = "SELECT h FROM HrEmpDisciplines h WHERE h.decisionDate = :decisionDate")})
public class HrEmpDisciplines implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HR_EMP_DISCIPLINES_SEQ")
    @SequenceGenerator(name = "HR_EMP_DISCIPLINES_SEQ", sequenceName = "HR_EMP_DISCIPLINES_SEQ", allocationSize = 1)
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private BigDecimal id;
    @Size(max = 400)
    @Column(name = "DESCRIPTION_OF_OFFENCE")
    private String descriptionOfOffence;
    @Column(name = "PENALTY_CLASSFICATION")
    private String penaltyClassfication;
    @Column(name = "REPITITION_OF_OFFENCE")
    private BigInteger repititionOfOffence;
    @Column(name = "STATUS")
    private Integer status;
    @Size(max = 400)
    @Column(name = "DECISION_ON_PENALITY")
    private String decisionOnPenality;
    @Size(max = 20)
    @Column(name = "DECISION_DATE")
    private String decisionDate;
    @JoinColumn(name = "OFFENCE_TYPE_ID", referencedColumnName = "ID")
    @ManyToOne
    private HrDisciplineOffenceTypes offenceTypeId;
    @JoinColumn(name = "REQUEST_ID", referencedColumnName = "ID")
    @ManyToOne
    private HrDisciplineRequests requestId;

    public HrEmpDisciplines() {
    }

    public HrEmpDisciplines(BigDecimal id) {
        this.id = id;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public String getDescriptionOfOffence() {
        return descriptionOfOffence;
    }

    public void setDescriptionOfOffence(String descriptionOfOffence) {
        this.descriptionOfOffence = descriptionOfOffence;
    }

    public BigInteger getRepititionOfOffence() {
        return repititionOfOffence;
    }

    public void setRepititionOfOffence(BigInteger repititionOfOffence) {
        this.repititionOfOffence = repititionOfOffence;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getDecisionOnPenality() {
        return decisionOnPenality;
    }

    public void setDecisionOnPenality(String decisionOnPenality) {
        this.decisionOnPenality = decisionOnPenality;
    }

    public String getPenaltyClassfication() {
        return penaltyClassfication;
    }

    public void setPenaltyClassfication(String penaltyClassfication) {
        this.penaltyClassfication = penaltyClassfication;
    }

    public String getDecisionDate() {
        return decisionDate;
    }

    public void setDecisionDate(String decisionDate) {
        this.decisionDate = decisionDate;
    }

    public HrDisciplineOffenceTypes getOffenceTypeId() {
        return offenceTypeId;
    }

    public void setOffenceTypeId(HrDisciplineOffenceTypes offenceTypeId) {
        this.offenceTypeId = offenceTypeId;
    }

    public HrDisciplineRequests getRequestId() {
        return requestId;
    }

    public void setRequestId(HrDisciplineRequests requestId) {
        this.requestId = requestId;
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
        if (!(object instanceof HrEmpDisciplines)) {
            return false;
        }
        HrEmpDisciplines other = (HrEmpDisciplines) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "et.gov.eep.hrms.entity.displine.HrEmpDisciplines[ id=" + id + " ]";
    }

}
