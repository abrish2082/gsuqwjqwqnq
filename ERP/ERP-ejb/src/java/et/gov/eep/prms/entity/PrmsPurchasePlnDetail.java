/******************************************************************************
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 ******************************************************************************/
package et.gov.eep.prms.entity;

import et.gov.eep.mms.entity.MmsItemRegistration;
import java.io.Serializable;
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
@Table(name = "PRMS_PURCHASE_PLN_DETAIL")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PrmsPurchasePlnDetail.findAll", query = "SELECT p FROM PrmsPurchasePlnDetail p"),
    @NamedQuery(name = "PrmsPurchasePlnDetail.findById", query = "SELECT p FROM PrmsPurchasePlnDetail p WHERE p.id = :id"),
    @NamedQuery(name = "PrmsPurchasePlnDetail.findByMoneySource", query = "SELECT p FROM PrmsPurchasePlnDetail p WHERE p.moneySource = :moneySource"),
    @NamedQuery(name = "PrmsPurchasePlnDetail.findByPlanNum", query = "SELECT p FROM PrmsPurchasePlnDetail p WHERE p.purchsePlanId.planNo = :planNum"),
    @NamedQuery(name = "PrmsPurchasePlnDetail.findByplnNum", query = "SELECT p FROM PrmsPurchasePlnDetail p WHERE p.purchsePlanId.planNo IS NOT NULL"),
    @NamedQuery(name = "PrmsPurchasePlnDetail.findByProcuremntCat", query = "SELECT p FROM PrmsPurchasePlnDetail p WHERE p.procuremntCat = :procuremntCat"),
    @NamedQuery(name = "PrmsPurchasePlnDetail.findByBudgetType", query = "SELECT p FROM PrmsPurchasePlnDetail p WHERE p.budgetType = :budgetType"),
    @NamedQuery(name = "PrmsPurchasePlnDetail.findByProcuremntMethod", query = "SELECT p FROM PrmsPurchasePlnDetail p WHERE p.procuremntMethod = :procuremntMethod"),
    @NamedQuery(name = "PrmsPurchasePlnDetail.findByBidOpeningDate", query = "SELECT p FROM PrmsPurchasePlnDetail p WHERE p.bidOpeningDate = :bidOpeningDate"),
    @NamedQuery(name = "PrmsPurchasePlnDetail.findByDeliveryDate", query = "SELECT p FROM PrmsPurchasePlnDetail p WHERE p.deliveryDate = :deliveryDate"),
    @NamedQuery(name = "PrmsPurchasePlnDetail.findByRemark", query = "SELECT p FROM PrmsPurchasePlnDetail p WHERE p.remark = :remark"),
    @NamedQuery(name = "PrmsPurchasePlnDetail.findByPriceUnit", query = "SELECT p FROM PrmsPurchasePlnDetail p WHERE p.priceUnit = :priceUnit")})
public class PrmsPurchasePlnDetail implements Serializable {

    @Column(name = "QUANTITY")
    private Integer quantity;
   
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "ID", nullable = false, length = 20)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "EEP_PURCH_PNL_DTL_SEQ")
    @SequenceGenerator(name = "EEP_PURCH_PNL_DTL_SEQ", sequenceName = "EEP_PURCH_PNL_DTL_SEQ", allocationSize = 1)
    private String id;
    @Size(max = 20)
    @Column(name = "MONEY_SOURCE", length = 20)
    private String moneySource;
    @Size(max = 20)
    @Column(name = "PROCUREMNT_CAT", length = 20)
    private String procuremntCat;
    @Size(max = 20)
    @Column(name = "BUDGET_TYPE", length = 20)
    private String budgetType;
    @Size(max = 20)
    @Column(name = "PROCUREMNT_METHOD", length = 20)
    private String procuremntMethod;
    @Column(name = "BID_OPENING_DATE")
    @Temporal(TemporalType.DATE)
    private Date bidOpeningDate;
    @Column(name = "DELIVERY_DATE")
    @Temporal(TemporalType.DATE)
    private Date deliveryDate;
    @Size(max = 20)
    @Column(name = "REMARK", length = 20)
    private String remark;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "PRICE_UNIT", precision = 126)
    private Double priceUnit;
    @JoinColumn(name = "ITEM_CODE_ID", referencedColumnName = "MATERIAL_ID")
    @ManyToOne()
    private MmsItemRegistration itemCodeId;
    @JoinColumn(name = "PURCHSE_PLAN_ID", referencedColumnName = "ID")
    @ManyToOne()
    private PrmsPurchasePlan purchsePlanId;

    public PrmsPurchasePlnDetail() {
    }

    public PrmsPurchasePlnDetail(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMoneySource() {
        return moneySource;
    }

    public void setMoneySource(String moneySource) {
        this.moneySource = moneySource;
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

    public Double getPriceUnit() {
        return priceUnit;
    }

    public void setPriceUnit(Double priceUnit) {
        this.priceUnit = priceUnit;
    }

    public MmsItemRegistration getItemCodeId() {
        return itemCodeId;
    }

    public void setItemCodeId(MmsItemRegistration itemCodeId) {
        this.itemCodeId = itemCodeId;
    }

    public PrmsPurchasePlan getPurchsePlanId() {
        return purchsePlanId;
    }

    public void setPurchsePlanId(PrmsPurchasePlan purchsePlanId) {
        this.purchsePlanId = purchsePlanId;
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
        if (!(object instanceof PrmsPurchasePlnDetail)) {
            return false;
        }
        PrmsPurchasePlnDetail other = (PrmsPurchasePlnDetail) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

}
