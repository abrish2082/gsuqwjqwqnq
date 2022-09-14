/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.prms.entity;

import et.gov.eep.mms.entity.MmsItemRegistration;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
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
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@Table(name = "PRMS_QUOTATION_DETAIL")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PrmsQuotationDetail.findAll", query = "SELECT p FROM PrmsQuotationDetail p"),
    @NamedQuery(name = "PrmsQuotationDetail.findByQuatDetId", query = "SELECT p FROM PrmsQuotationDetail p WHERE p.quatDetId = :quatDetId"),
    @NamedQuery(name = "PrmsQuotationDetail.FindByForienID", query = "SELECT p FROM PrmsQuotationDetail p WHERE p.supId.vendorName = :supplyId"),

    @NamedQuery(name = "PrmsQuotationDetail.findByUnitPrice", query = "SELECT p FROM PrmsQuotationDetail p WHERE p.unitPrice = :unitPrice"),
    @NamedQuery(name = "PrmsQuotationDetail.findByQuotationNo", query = "SELECT p FROM PrmsQuotationDetail p WHERE p.quotationId.quotationNo = :quotationNo"),
    @NamedQuery(name = "PrmsQuotationDetail.findByQuantity", query = "SELECT p FROM PrmsQuotationDetail p WHERE p.quantity = :quantity")})
public class PrmsQuotationDetail implements Serializable {

    @JoinColumn(name = "PURCHASE_TYPE_ID", referencedColumnName = "PURCHASE_TYPE_ID")
    @ManyToOne
    private PrmsPurchaseTypeLookup purchaseTypeId;
    @JoinColumn(name = "SUP_ID", referencedColumnName = "ID")
    @ManyToOne
    private PrmsSupplyProfile supId;
    @JoinColumn(name = "VAT_TYPE_ID", referencedColumnName = "VAT_TYPE_ID")
    @ManyToOne
    private PrmsLuVatTypeLookup vatTypeId;

    @Size(max = 100)
    @Column(name = "SERVICE_DESCRIPTION")
    private String serviceDescription;
    @Column(name = "UNIT_PRICE")
    private double unitPrice;
    @Column(name = "VAT_TYPE")
    private String vatType;
    @Column(name = "QUANTITY")
    private Integer quantity;

    @JoinColumn(name = "PURCHASE_RQUEST_DET_ID", referencedColumnName = "ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private PrmsPurchaseRequestDet purchaseRquestDetId;
    @JoinColumn(name = "PR_SERVICE_ID", referencedColumnName = "ID")
    @OneToOne(fetch = FetchType.LAZY)
    private PrmsPurchaseReqService purchaseReqServiceId;
    @JoinColumn(name = "SERV_AND_WORK_ID", referencedColumnName = "SERV_AND_WORK_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private PrmsServiceAndWorkReg servAndWorkId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "quotationDetailId", fetch = FetchType.LAZY)
    private List<PrmsThechincalEvaluationDet> prmsThechnicalEvaluationList;

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PRMS_QUOTATION_DETAIL_SEQ")
    @SequenceGenerator(name = "PRMS_QUOTATION_DETAIL_SEQ", sequenceName = "PRMS_QUOTATION_DETAIL_SEQ", allocationSize = 1)
    @Column(name = "QUAT_DET_ID")
    private BigDecimal quatDetId;
    @JoinColumn(name = "QUOTATION_ID", referencedColumnName = "QUATATION_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private PrmsQuotation quotationId;
    @JoinColumn(name = "ITEM_REG_ID", referencedColumnName = "MATERIAL_ID")
    @ManyToOne
    private MmsItemRegistration materialCodeId;
    @Transient
    Double totals;

    public PrmsQuotationDetail() {
    }

    /**
     *
     * @param quatDetId
     */
    public PrmsQuotationDetail(BigDecimal quatDetId) {
        this.quatDetId = quatDetId;
    }

    /**
     *
     * @return
     */
    public BigDecimal getQuatDetId() {
        return quatDetId;
    }

    /**
     *
     * @param quatDetId
     */
    public void setQuatDetId(BigDecimal quatDetId) {
        this.quatDetId = quatDetId;
    }

    public PrmsQuotation getQuotationId() {
        return quotationId;
    }

    public void setQuotationId(PrmsQuotation quotationId) {
        this.quotationId = quotationId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (quatDetId != null ? quatDetId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PrmsQuotationDetail)) {
            return false;
        }
        PrmsQuotationDetail other = (PrmsQuotationDetail) object;
        if ((this.quatDetId == null && other.quatDetId != null) || (this.quatDetId != null && !this.quatDetId.equals(other.quatDetId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return quatDetId.toString();
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getVatType() {
        return vatType;
    }

    public void setVatType(String vatType) {
        this.vatType = vatType;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public PrmsPurchaseRequestDet getPurchaseRquestDetId() {
        return purchaseRquestDetId;
    }

    public void setPurchaseRquestDetId(PrmsPurchaseRequestDet purchaseRquestDetId) {
        this.purchaseRquestDetId = purchaseRquestDetId;
    }

    public PrmsPurchaseReqService getPurchaseReqServiceId() {
        if (purchaseReqServiceId == null) {
            purchaseReqServiceId = new PrmsPurchaseReqService();
        }
        return purchaseReqServiceId;
    }

    public void setPurchaseReqServiceId(PrmsPurchaseReqService purchaseReqServiceId) {
        this.purchaseReqServiceId = purchaseReqServiceId;
    }

    public String getServiceDescription() {
        return serviceDescription;
    }

    public void setServiceDescription(String serviceDescription) {
        this.serviceDescription = serviceDescription;
    }

    public MmsItemRegistration getMaterialCodeId() {
        return materialCodeId;
    }

    public void setMaterialCodeId(MmsItemRegistration materialCodeId) {
        this.materialCodeId = materialCodeId;
    }

    @XmlTransient
    public List<PrmsThechincalEvaluationDet> getPrmsThechnicalEvaluationList() {
        return prmsThechnicalEvaluationList;
    }

    public void setPrmsThechnicalEvaluationList(List<PrmsThechincalEvaluationDet> prmsThechnicalEvaluationList) {
        this.prmsThechnicalEvaluationList = prmsThechnicalEvaluationList;
    }

    public PrmsPurchaseTypeLookup getPurchaseTypeId() {
        return purchaseTypeId;
    }

    public void setPurchaseTypeId(PrmsPurchaseTypeLookup purchaseTypeId) {
        this.purchaseTypeId = purchaseTypeId;
    }

    public PrmsSupplyProfile getSupId() {
        return supId;
    }

    public void setSupId(PrmsSupplyProfile supId) {
        this.supId = supId;
    }

    public PrmsLuVatTypeLookup getVatTypeId() {
        return vatTypeId;
    }

    public void setVatTypeId(PrmsLuVatTypeLookup vatTypeId) {
        this.vatTypeId = vatTypeId;
    }

    public PrmsServiceAndWorkReg getServAndWorkId() {
        return servAndWorkId;
    }

    public void setServAndWorkId(PrmsServiceAndWorkReg servAndWorkId) {
        this.servAndWorkId = servAndWorkId;
    }

    public Double getTotals() {
        return totals;
    }

    public void setTotals(Double totals) {
        this.totals = totals;
    }

    @Transient
    private double totalVat;
    @Transient
    private double totalPrice;

    public double getTotalVat() {
        return totalVat;
    }

    public void setTotalVat(double totalVat) {
        this.totalVat = totalVat;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
