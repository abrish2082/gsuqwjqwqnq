/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.entity.training;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Benin
 */
@Entity
@Table(name = "HR_TD_ANNUAL_PLAN_GROUPS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HrTdAnnualPlanGroups.findAll", query = "SELECT h FROM HrTdAnnualPlanGroups h"),
    @NamedQuery(name = "HrTdAnnualPlanGroups.findById", query = "SELECT h FROM HrTdAnnualPlanGroups h WHERE h.id = :id"),
    @NamedQuery(name = "HrTdAnnualPlanGroups.findByGroupName", query = "SELECT h FROM HrTdAnnualPlanGroups h WHERE h.groupName = :groupName"),
    @NamedQuery(name = "HrTdAnnualPlanGroups.findByParticipantNeeded", query = "SELECT h FROM HrTdAnnualPlanGroups h WHERE h.participantNeeded = :participantNeeded"),
    @NamedQuery(name = "HrTdAnnualPlanGroups.findByVenue", query = "SELECT h FROM HrTdAnnualPlanGroups h WHERE h.venue = :venue"),
    @NamedQuery(name = "HrTdAnnualPlanGroups.findByStartDate", query = "SELECT h FROM HrTdAnnualPlanGroups h WHERE h.startDate = :startDate"),
    @NamedQuery(name = "HrTdAnnualPlanGroups.findByEndDate", query = "SELECT h FROM HrTdAnnualPlanGroups h WHERE h.endDate = :endDate"),
    @NamedQuery(name = "HrTdAnnualPlanGroups.findByCostPerPerson", query = "SELECT h FROM HrTdAnnualPlanGroups h WHERE h.costPerPerson = :costPerPerson"),
    @NamedQuery(name = "HrTdAnnualPlanGroups.findByRemark", query = "SELECT h FROM HrTdAnnualPlanGroups h WHERE h.remark = :remark")})
public class HrTdAnnualPlanGroups implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HR_TD_ANN_PLAN_GROUP_SEQ")
    @SequenceGenerator(name = "HR_TD_ANN_PLAN_GROUP_SEQ", sequenceName = "HR_TD_ANN_PLAN_GROUP_SEQ", allocationSize = 1)
    @NotNull
    @Column(name = "ID")
    private BigDecimal id;
    @Size(max = 20)
    @Column(name = "GROUP_NAME")
    private String groupName;
    @Column(name = "PARTICIPANT_NEEDED")
    private Integer participantNeeded;
    @Size(max = 20)
    @Column(name = "VENUE")
    private String venue;
    @Size(max = 20)
    @Column(name = "START_DATE")
    private String startDate;
    @Size(max = 20)
    @Column(name = "END_DATE")
    private String endDate;
    @Column(name = "COST_PER_PERSON")
    private BigInteger costPerPerson;
    @Size(max = 200)
    @Column(name = "REMARK")
    private String remark;

    @JoinColumn(name = "ANN_PLAN_ID", referencedColumnName = "ID")
    @ManyToOne(cascade = CascadeType.ALL)
    private HrTdAnnualPlans annPlanId;

    @JoinColumn(name = "INS_ID", referencedColumnName = "ID")
    @ManyToOne
    private HrTdTrainerProfiles insId;

    public HrTdAnnualPlanGroups() {
    }

    public HrTdAnnualPlanGroups(BigDecimal id) {
        this.id = id;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Integer getParticipantNeeded() {
        return participantNeeded;
    }

    public void setParticipantNeeded(Integer participantNeeded) {
        this.participantNeeded = participantNeeded;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
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

    public BigInteger getCostPerPerson() {
        return costPerPerson;
    }

    public void setCostPerPerson(BigInteger costPerPerson) {
        this.costPerPerson = costPerPerson;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public HrTdAnnualPlans getAnnPlanId() {
        return annPlanId;
    }

    public void setAnnPlanId(HrTdAnnualPlans annPlanId) {
        this.annPlanId = annPlanId;
    }

    public HrTdTrainerProfiles getInsId() {
        return insId;
    }

    public void setInsId(HrTdTrainerProfiles insId) {
        this.insId = insId;
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
        if (!(object instanceof HrTdAnnualPlanGroups)) {
            return false;
        }
        HrTdAnnualPlanGroups other = (HrTdAnnualPlanGroups) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "et.gov.eep.hrms.entity.training.HrTdAnnualPlanGroups[ id=" + id + " ]";
    }

}
