/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.prms.entity;

//import et.gov.eep.fcms.entity.budget.FmsOperatingBudget;
import java.io.Serializable;
import java.math.BigDecimal;
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
@Table(name = "PRMS_ANNUAL_PLAN_SERVICE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PrmsAnnualPlanService.findAll", query = "SELECT p FROM PrmsAnnualPlanService p"),
    @NamedQuery(name = "PrmsAnnualPlanService.findById", query = "SELECT p FROM PrmsAnnualPlanService p WHERE p.id = :id"),
    @NamedQuery(name = "PrmsAnnualPlanService.findByMoneySource", query = "SELECT p FROM PrmsAnnualPlanService p WHERE p.moneySource = :moneySource"),
    @NamedQuery(name = "PrmsAnnualPlanService.findByQuantity", query = "SELECT p FROM PrmsAnnualPlanService p WHERE p.quantity = :quantity"),
    @NamedQuery(name = "PrmsAnnualPlanService.findByProcuremntCat", query = "SELECT p FROM PrmsAnnualPlanService p WHERE p.procuremntCat = :procuremntCat"),
    @NamedQuery(name = "PrmsAnnualPlanService.findByBudgetType", query = "SELECT p FROM PrmsAnnualPlanService p WHERE p.budgetType = :budgetType"),
    @NamedQuery(name = "PrmsAnnualPlanService.findByProcuremntMethod", query = "SELECT p FROM PrmsAnnualPlanService p WHERE p.procuremntMethod = :procuremntMethod"),
    @NamedQuery(name = "PrmsAnnualPlanService.findByBidOpeningDate", query = "SELECT p FROM PrmsAnnualPlanService p WHERE p.bidOpeningDate = :bidOpeningDate"),
    @NamedQuery(name = "PrmsAnnualPlanService.findByDeliveryDate", query = "SELECT p FROM PrmsAnnualPlanService p WHERE p.deliveryDate = :deliveryDate"),
    @NamedQuery(name = "PrmsAnnualPlanService.findByRemark", query = "SELECT p FROM PrmsAnnualPlanService p WHERE p.remark = :remark"),
    @NamedQuery(name = "PrmsAnnualPlanService.findByPriceUnit", query = "SELECT p FROM PrmsAnnualPlanService p WHERE p.priceUnit = :priceUnit")})
public class PrmsAnnualPlanService implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id

    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PRMS_ANNUAL_PLAN_SERVIC_SEQ")
    @SequenceGenerator(name = "PRMS_ANNUAL_PLAN_SERVIC_SEQ", sequenceName = "PRMS_ANNUAL_PLAN_SERVIC_SEQ", allocationSize = 1)
    private BigDecimal id;
    @Size(max = 20)
    @Column(name = "MONEY_SOURCE")
    private String moneySource;
    @Column(name = "QUANTITY")
    private Integer quantity;
    @Size(max = 20)
    @Column(name = "PROCUREMNT_CAT")
    private String procuremntCat;
    @Size(max = 20)
    @Column(name = "BUDGET_TYPE")
    private String budgetType;
    @Size(max = 20)
    @Column(name = "PROCUREMNT_METHOD")
    private String procuremntMethod;
    @Column(name = "BID_OPENING_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date bidOpeningDate;
    @Column(name = "DELIVERY_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date deliveryDate;
    @Size(max = 200)
    @Column(name = "REMARK")
    private String remark;
    @Column(name = "PRICE_UNIT")
    private BigDecimal priceUnit;
    @JoinColumn(name = "PURCHSE_PLAN_ID", referencedColumnName = "ID")
    @ManyToOne()
    private PrmsPurchasePlan purchsePlanId;
    @JoinColumn(name = "SERVICE_ID", referencedColumnName = "SERV_AND_WORK_ID")
    @ManyToOne
    private PrmsServiceAndWorkReg serviceId;

    public PrmsAnnualPlanService() {
    }

    public PrmsAnnualPlanService(BigDecimal id) {
        this.id = id;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public String getMoneySource() {
        return moneySource;
    }

    public void setMoneySource(String moneySource) {
        this.moneySource = moneySource;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getProcuremntCat() {
        return procuremntCat;
    }

    public void setProcuremntCat(String procuremntCat) {
        this.procuremntCat = procuremntCat;
    }

    public String getBudgetType() {
        return budgetType;
    }

    public void setBudgetType(String budgetType) {
        this.budgetType = budgetType;
    }

    public String getProcuremntMethod() {
        return procuremntMethod;
    }

    public void setProcuremntMethod(String procuremntMethod) {
        this.procuremntMethod = procuremntMethod;
    }

    public Date getBidOpeningDate() {
        return bidOpeningDate;
    }

    public void setBidOpeningDate(Date bidOpeningDate) {
        this.bidOpeningDate = bidOpeningDate;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public BigDecimal getPriceUnit() {
        return priceUnit;
    }

    public void setPriceUnit(BigDecimal priceUnit) {
        this.priceUnit = priceUnit;
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
        if (!(object instanceof PrmsAnnualPlanService)) {
            return false;
        }
        PrmsAnnualPlanService other = (PrmsAnnualPlanService) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "et.gov.eep.prms.entity.PrmsAnnualPlanService[ id=" + id + " ]";
    }

    public PrmsPurchasePlan getPurchsePlanId() {
        return purchsePlanId;
    }

    public void setPurchsePlanId(PrmsPurchasePlan purchsePlanId) {
        this.purchsePlanId = purchsePlanId;
    }

    public PrmsServiceAndWorkReg getServiceId() {
        return serviceId;
    }

    public void setServiceId(PrmsServiceAndWorkReg serviceId) {
        this.serviceId = serviceId;
    }
}
