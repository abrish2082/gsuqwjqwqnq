/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.prms.entity;

import et.gov.eep.fcms.entity.FmsLuCurrency;
import et.gov.eep.mms.entity.MmsItemRegistration;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityResult;
import javax.persistence.FieldResult;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author user
 */
@Entity
@Table(name = "PRMS_FINANCIAL_EVALUA_DETAIL")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PrmsFinancialEvaluaDetail.findAll", query = "SELECT p FROM PrmsFinancialEvaluaDetail p"),
    @NamedQuery(name = "PrmsFinancialEvaluaDetail.findById", query = "SELECT p FROM PrmsFinancialEvaluaDetail p WHERE p.id = :id")})
@SqlResultSetMapping(name = "OrderResultsTechnicalAndFinancal",
        entities = {
            @EntityResult(entityClass = PrmsFinancialEvaluaDetail.class, fields = {
                @FieldResult(name = "unitPrice", column = "Unit_Price"),
                @FieldResult(name = "score", column = "TechScore"),
                @FieldResult(name = "supplierCode", column = "TEchSupplier")})})

public class PrmsFinancialEvaluaDetail implements Serializable {

    @Transient
    private double score;
    @Transient
    private String itemName;
    @Transient
    private double total;
//    @Column(name = "BIDDTL_ID")
//    private String bidDtlId;
    @JoinColumn(name = "BID_DETAIL_ID", referencedColumnName = "ID")
    @ManyToOne
    private PrmsBidDetail BidDetailId;
    @Column(name = "VAT")
    private double vat;
    @JoinColumn(name = "ITEM_REGISTRATION_ID", referencedColumnName = "MATERIAL_ID")
    @ManyToOne
    private MmsItemRegistration itemRegistrationId;
    @JoinColumn(name = "SERVICE_ID", referencedColumnName = "SERV_AND_WORK_ID")
    @ManyToOne
    private PrmsServiceAndWorkReg serviceId;
    @JoinColumn(name = "SUPPLIER_ID", referencedColumnName = "ID")
    @ManyToOne
    private PrmsSupplyProfile supplierId;

    @Size(max = 20)
    @Column(name = "SUPPLIER_CODE", length = 20)
    private String supplierCode;
    @Size(max = 20)
    @Column(name = "AWARD_TYPE", length = 20)
    private String awardType;
    @Size(max = 20)
    @Column(name = "LOT_NO", length = 20)
    private String lotNo;
    @Column(name = "QUANTITY")
    private Integer quantity;
    @Column(name = "TOTAL_PRICE")
    private double totalPrice;
    @Column(name = "UNIT_PRICE")
    private double unitPrice;
    @Column(name = "DISCOUNT_PRICE")
    private double discountPrice;
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PRMS_FINANCIAL_EVAL_DTL_SEQ_GENERATOR")
    @SequenceGenerator(name = "PRMS_FINANCIAL_EVAL_DTL_SEQ_GENERATOR", sequenceName = "PRMS_FINANCIAL_EVAL_DTL_SEQ", allocationSize = 1)
    private String id;
    @JoinColumn(name = "FINANCIAL_EVALUATION_ID", referencedColumnName = "ID")
    @ManyToOne
    private PrmsFinancialEvaluation financialEvaluationId;
    @JoinColumn(name = "CURRENCY_ID", referencedColumnName = "CURRENCY_ID")
    @ManyToOne
    private FmsLuCurrency currencyId;
    @OneToMany(mappedBy = "financialEvaluaDetailId")
    private List<PrmsFinancialEvlResultyDtl> prmsFinancialEvlResultyDtlLists;
    @Transient
    private double sum;
    @Transient
    String currency;

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public double getSum() {
        return sum;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }

    public PrmsFinancialEvaluaDetail() {
    }

    public PrmsFinancialEvaluaDetail(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public PrmsFinancialEvaluation getFinancialEvaluationId() {
        return financialEvaluationId;
    }

    public void setFinancialEvaluationId(PrmsFinancialEvaluation financialEvaluationId) {
        this.financialEvaluationId = financialEvaluationId;
    }

//    public String getBidDtlId() {
//        return bidDtlId;
//    }
//
//    public void setBidDtlId(String bidDtlId) {
//        this.bidDtlId = bidDtlId;
//    }
    public String getLotNo() {
        return lotNo;
    }

    public void setLotNo(String lotNo) {
        this.lotNo = lotNo;
    }

    public double getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(double discountPrice) {
        this.discountPrice = discountPrice;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    @XmlTransient
    public List<PrmsFinancialEvlResultyDtl> getPrmsFinancialEvlResultyDtlLists() {
        if (prmsFinancialEvlResultyDtlLists == null) {
            prmsFinancialEvlResultyDtlLists = new ArrayList<>();
        }
        return prmsFinancialEvlResultyDtlLists;
    }

    public void setPrmsFinancialEvlResultyDtlLists(List<PrmsFinancialEvlResultyDtl> prmsFinancialEvlResultyDtlLists) {
        this.prmsFinancialEvlResultyDtlLists = prmsFinancialEvlResultyDtlLists;
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
        if (!(object instanceof PrmsFinancialEvaluaDetail)) {
            return false;
        }
        PrmsFinancialEvaluaDetail other = (PrmsFinancialEvaluaDetail) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "et.gov.eep.prms.entity.PrmsFinancialEvaluaDetail[ id=" + id + " ]";
    }

    public String getSupplierCode() {
        return supplierCode;
    }

    public void setSupplierCode(String supplierCode) {
        this.supplierCode = supplierCode;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public double getVat() {
        return vat;
    }

    public void setVat(double vat) {
        this.vat = vat;
    }

    public MmsItemRegistration getItemRegistrationId() {
        return itemRegistrationId;
    }

    public void setItemRegistrationId(MmsItemRegistration itemRegistrationId) {
        this.itemRegistrationId = itemRegistrationId;
    }

    public PrmsSupplyProfile getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(PrmsSupplyProfile supplierId) {
        this.supplierId = supplierId;
    }

    public String getAwardType() {
        return awardType;
    }

    public void setAwardType(String awardType) {
        this.awardType = awardType;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public FmsLuCurrency getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(FmsLuCurrency currencyId) {
        this.currencyId = currencyId;
    }

    public PrmsBidDetail getBidDetailId() {
        return BidDetailId;
    }

    public void setBidDetailId(PrmsBidDetail BidDetailId) {
        this.BidDetailId = BidDetailId;
    }

    public PrmsServiceAndWorkReg getServiceId() {
        return serviceId;
    }

    public void setServiceId(PrmsServiceAndWorkReg serviceId) {
        this.serviceId = serviceId;
    }

}
