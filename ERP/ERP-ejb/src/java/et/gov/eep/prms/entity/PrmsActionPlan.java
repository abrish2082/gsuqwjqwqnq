/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.prms.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author user
 */
@Entity
@Table(name = "PRMS_ACTION_PLAN")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PrmsActionPlan.findAll", query = "SELECT p FROM PrmsActionPlan p"),
    @NamedQuery(name = "PrmsActionPlan.findById", query = "SELECT p FROM PrmsActionPlan p WHERE p.id = :id"),
    @NamedQuery(name = "PrmsActionPlan.findByActionPlanNo", query = "SELECT p FROM PrmsActionPlan p WHERE p.actionPlanNo = :actionPlanNo"),
    @NamedQuery(name = "PrmsActionPlan.findByActionPlanNum", query = "SELECT p FROM PrmsActionPlan p WHERE p.actionPlanNo LIKE :actionPlanNo"),
    @NamedQuery(name = "PrmsActionPlan.findByPreparedDate", query = "SELECT p FROM PrmsActionPlan p WHERE p.preparedDate = :preparedDate"),
    @NamedQuery(name = "PrmsActionPlan.findByStartDate", query = "SELECT p FROM PrmsActionPlan p WHERE p.startDate = :startDate"),
    @NamedQuery(name = "PrmsActionPlan.findByAutoGenetNo", query = "SELECT p FROM PrmsActionPlan p WHERE p.id = (SELECT Max(p.id)  from PrmsActionPlan p)"),
    @NamedQuery(name = "PrmsActionPlan.findByEndDate", query = "SELECT p FROM PrmsActionPlan p WHERE p.endDate = :endDate"),
    @NamedQuery(name = "PrmsActionPlan.findByDesrciption", query = "SELECT p FROM PrmsActionPlan p WHERE p.desrciption = :desrciption")})
public class PrmsActionPlan implements Serializable {

    @JoinColumn(name = "PROJECT_PLAN_ID", referencedColumnName = "ID")
    @ManyToOne
    private PrmsProjectPlan projectPlanId;
    @JoinColumn(name = "PURCHASE_PLAN_ID", referencedColumnName = "ID")
    @ManyToOne
    private PrmsPurchasePlan purchasePlanId;

    @Size(max = 20)
    @Column(name = "PREPARED_BY", length = 20)
    private String preparedBy;
     @Size(max = 20)
    @Column(name = "PLAN_TYPE", length = 20)
    private String planType;
    @Size(max = 20)
    @Column(name = "REMARK", length = 20)
    private String remark;

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PRMS_ACTION_PLN_SEQ_GENERATOR")
    @SequenceGenerator(name = "PRMS_ACTION_PLN_SEQ_GENERATOR", sequenceName = "PRMS_ACTION_PLN_SEQ", allocationSize = 1)
    private String id;
    @Size(max = 20)
    @Column(name = "ACTION_PLAN_NO")
    private String actionPlanNo;
    @Size(max = 20)
    @Column(name = "PROCUREMNT_METHOD")
    private String procuremntMethod;
    @Column(name = "PREPARED_DATE")
    @Temporal(TemporalType.DATE)
    private Date preparedDate;
    @Column(name = "START_DATE")
    @Temporal(TemporalType.DATE)
    private Date startDate;
    @Column(name = "END_DATE")
    @Temporal(TemporalType.DATE)
    private Date endDate;
    @Size(max = 500)
    @Column(name = "DESRCIPTION")
    private String desrciption;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "actionPlanId", fetch = FetchType.LAZY)
    private List<PrmsActionPlanDetail> prmsActionPlanDetailList;

    public PrmsActionPlan() {
    }

    public PrmsActionPlan(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getActionPlanNo() {
        return actionPlanNo;
    }

    public void setActionPlanNo(String actionPlanNo) {
        this.actionPlanNo = actionPlanNo;
    }

    public String getProcuremntMethod() {
        return procuremntMethod;
    }

    public void setProcuremntMethod(String procuremntMethod) {
        this.procuremntMethod = procuremntMethod;
    }

    public Date getPreparedDate() {
        return preparedDate;
    }

    public void setPreparedDate(Date preparedDate) {
        this.preparedDate = preparedDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getDesrciption() {
        return desrciption;
    }

    public void setDesrciption(String desrciption) {
        this.desrciption = desrciption;
    }

    @XmlTransient
    public List<PrmsActionPlanDetail> getPrmsActionPlanDetailList() {
        if (prmsActionPlanDetailList == null) {
            prmsActionPlanDetailList = new ArrayList<>();
        }
        return prmsActionPlanDetailList;
    }

    public void setPrmsActionPlanDetailList(List<PrmsActionPlanDetail> prmsActionPlanDetailList) {
        this.prmsActionPlanDetailList = prmsActionPlanDetailList;
    }

    public void add(PrmsActionPlanDetail prmsActionPlanDetail) {

        if (prmsActionPlanDetail.getActionPlanId() != this) {
            this.getPrmsActionPlanDetailList().add(prmsActionPlanDetail);
            prmsActionPlanDetail.setActionPlanId(this);

        }
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
        if (!(object instanceof PrmsActionPlan)) {
            return false;
        }
        PrmsActionPlan other = (PrmsActionPlan) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return actionPlanNo;
    }

    public String getPreparedBy() {
        return preparedBy;
    }

    public void setPreparedBy(String preparedBy) {
        this.preparedBy = preparedBy;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public PrmsProjectPlan getProjectPlanId() {
        return projectPlanId;
    }

    public void setProjectPlanId(PrmsProjectPlan projectPlanId) {
        this.projectPlanId = projectPlanId;
    }

    public PrmsPurchasePlan getPurchasePlanId() {
        return purchasePlanId;
    }

    public void setPurchasePlanId(PrmsPurchasePlan purchasePlanId) {
        this.purchasePlanId = purchasePlanId;
    }

    public String getPlanType() {
        return planType;
    }

    public void setPlanType(String planType) {
        this.planType = planType;
    }

    

}
