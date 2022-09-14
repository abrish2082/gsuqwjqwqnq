/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.prms.entity;

import et.gov.eep.fcms.entity.budget.FmsOperatingBudgetDetail;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author user
 */
@Entity
@Table(name = "PRMS_PURCHASE_REQ_SERVICE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PrmsPurchaseReqService.findAll", query = "SELECT p FROM PrmsPurchaseReqService p"),
    @NamedQuery(name = "PrmsPurchaseReqService.findById", query = "SELECT p FROM PrmsPurchaseReqService p WHERE p.id = :id"),
    @NamedQuery(name = "PrmsPurchaseReqService.findByAccountCode", query = "SELECT p FROM PrmsPurchaseReqService p WHERE p.accountCode = :accountCode"),
    @NamedQuery(name = "PrmsPurchaseReqService.findByPrNo", query = "SELECT p FROM PrmsPurchaseReqService p WHERE p.purchaseReqId.prNumber = :prNum"),

    @NamedQuery(name = "PrmsPurchaseReqService.findByPrNumber", query = "SELECT p FROM PrmsPurchaseReqService p WHERE p.purchaseReqId.prNumber IS NOT NULL"),
    @NamedQuery(name = "PrmsPurchaseReqService.findByMaterialCodeId", query = "SELECT p FROM PrmsPurchaseReqService p WHERE p.materialCodeId = :materialCodeId"),
    @NamedQuery(name = "PrmsPurchaseReqService.findByReqQuantity", query = "SELECT p FROM PrmsPurchaseReqService p WHERE p.reqQuantity = :reqQuantity"),
    @NamedQuery(name = "PrmsPurchaseReqService.findByUnitPrice", query = "SELECT p FROM PrmsPurchaseReqService p WHERE p.unitPrice = :unitPrice"),
    @NamedQuery(name = "PrmsPurchaseReqService.findByServiceType", query = "SELECT p FROM PrmsPurchaseReqService p WHERE p.serviceType = :serviceType"),
    @NamedQuery(name = "PrmsPurchaseReqService.findByVat", query = "SELECT p FROM PrmsPurchaseReqService p WHERE p.vat = :vat"),
    @NamedQuery(name = "PrmsPurchaseReqService.findOBDetailIdAndPrId", query = "SELECT p FROM PrmsPurchaseReqService p WHERE p.budgetCode.oBDetailId = :oBDetailId AND p.purchaseReqId.id = :prId"),
    @NamedQuery(name = "PrmsPurchaseReqService.findByUnitMeasure", query = "SELECT p FROM PrmsPurchaseReqService p WHERE p.unitMeasure = :unitMeasure"),
    @NamedQuery(name = "PrmsPurchaseReqService.findByMatname", query = "SELECT p FROM PrmsPurchaseReqService p WHERE p.matname = :matname"),
    @NamedQuery(name = "PrmsPurchaseReqService.findByMatcode", query = "SELECT p FROM PrmsPurchaseReqService p WHERE p.matcode = :matcode"),
    @NamedQuery(name = "PrmsPurchaseReqService.findByBriefDescription", query = "SELECT p FROM PrmsPurchaseReqService p WHERE p.briefDescription = :briefDescription"),
    @NamedQuery(name = "PrmsPurchaseReqService.findByBudgetCode", query = "SELECT p FROM PrmsPurchaseReqService p WHERE p.budgetCode =:budgetCode")})
public class PrmsPurchaseReqService implements Serializable {

    @Column(name = "SR_DETAIL_ID")
    private BigInteger srDetailId;
    @Size(max = 20)
    @Column(name = "MARKT_ASS_DTL_ID")
    private String marktAssDtlId;
    @Size(max = 35)
    @Column(name = "SERVICE_TYPE")
    private String serviceType;
//    @Size(max = 20)
//    @Column(name = "SERVICE_TYPE")
//    private String serviceType;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "purchaseReqServiceId", fetch = FetchType.LAZY)
    private List<PrmsQuotationDetail> prmsQuotationDetailList;
    private static final long serialVersionUID = 1L;
    @Id

    @Size(min = 1, max = 20)
    @Column(name = "ID", nullable = false, length = 20)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PRMS_PURCHASE_REQ_SERVICE_SEQ")
    @SequenceGenerator(name = "PRMS_PURCHASE_REQ_SERVICE_SEQ", sequenceName = "PRMS_PURCHASE_REQ_SERVICE_SEQ", allocationSize = 1)
    private String id;
    @Size(max = 255)
    @Column(name = "ACCOUNT_CODE", length = 255)
    private String accountCode;
    @Column(name = "PART_NO")
    private Long partNo;
    @Column(name = "MATERIAL_CODE_ID")
    private BigInteger materialCodeId;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "REQ_QUANTITY")
    private Integer reqQuantity;
    @Column(name = "UNIT_PRICE", precision = 126)
    private BigDecimal unitPrice;
    @Size(max = 50)
    @Column(name = "VAT", length = 50)
    private String vat;
//    @Column(name = "SR_ID")
//    private BigInteger srId;
//    @Size(max = 20)
    @Column(name = "UNIT_MEASURE",
            length = 20)
    private String unitMeasure;
    @Size(max = 20)
    @Column(name = "MATNAME", length = 20)
    private String matname;
    @Column(name = "BRIEF_DESCRIPTION", length = 300)
    private String briefDescription;
    @Size(max = 20)
    @Column(name = "MATCODE", length = 20)
    private String matcode;
    @JoinColumn(name = "SERVICE_ID", referencedColumnName = "SERV_AND_WORK_ID")
    @ManyToOne
    private PrmsServiceAndWorkReg serviceId;
    @Column(name = "FILE_REF_NO_SERVORWORK")
    private Integer fileRefNoServorwork;
//    @JoinColumn(name = "MARKT_ASS_SERVICE_ID", referencedColumnName = "ID")
//    @ManyToOne
//    private PrmsMarketAssmntService marktAssServiceId;
    @JoinColumn(name = "PURCHASE_REQ_ID", referencedColumnName = "ID")
    @ManyToOne
    private PrmsPurchaseRequest purchaseReqId;
    @JoinColumn(name = "O_B_DETAIL_ID", referencedColumnName = "O_B_DETAIL_ID")
    @ManyToOne
    private FmsOperatingBudgetDetail budgetCode;

    public PrmsPurchaseReqService() {
    }

    public PrmsPurchaseReqService(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAccountCode() {
        return accountCode;
    }

    public void setAccountCode(String accountCode) {
        this.accountCode = accountCode;
    }

    public Long getPartNo() {
        return partNo;
    }

    public void setPartNo(Long partNo) {
        this.partNo = partNo;
    }

    public BigInteger getMaterialCodeId() {
        return materialCodeId;
    }

    public void setMaterialCodeId(BigInteger materialCodeId) {
        this.materialCodeId = materialCodeId;
    }

    public Integer getReqQuantity() {
        return reqQuantity;
    }

    public void setReqQuantity(Integer reqQuantity) {
        this.reqQuantity = reqQuantity;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getVat() {
        return vat;
    }

    public void setVat(String vat) {
        this.vat = vat;
    }

    public String getBriefDescription() {
        return briefDescription;
    }

    public void setBriefDescription(String briefDescription) {
        this.briefDescription = briefDescription;
    }

    public String getUnitMeasure() {
        return unitMeasure;
    }

    public void setUnitMeasure(String unitMeasure) {
        this.unitMeasure = unitMeasure;
    }

    public String getMatname() {
        return matname;
    }

    public void setMatname(String matname) {
        this.matname = matname;
    }

    public String getMatcode() {
        return matcode;
    }

    public void setMatcode(String matcode) {
        this.matcode = matcode;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public PrmsServiceAndWorkReg getServiceId() {
        return serviceId;
    }

    public void setServiceId(PrmsServiceAndWorkReg serviceId) {
        this.serviceId = serviceId;
    }

    public PrmsPurchaseRequest getPurchaseReqId() {
        return purchaseReqId;
    }

    public void setPurchaseReqId(PrmsPurchaseRequest purchaseReqId) {
        this.purchaseReqId = purchaseReqId;
    }

    public FmsOperatingBudgetDetail getBudgetCode() {
        return budgetCode;
    }

    public void setBudgetCode(FmsOperatingBudgetDetail budgetCode) {
        this.budgetCode = budgetCode;
    }

    public Integer getFileRefNoServorwork() {
        return fileRefNoServorwork;
    }

    public void setFileRefNoServorwork(Integer fileRefNoServorwork) {
        this.fileRefNoServorwork = fileRefNoServorwork;
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
        if (!(object instanceof PrmsPurchaseReqService)) {
            return false;
        }
        PrmsPurchaseReqService other = (PrmsPurchaseReqService) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return id;
    }

    public BigInteger getSrDetailId() {
        return srDetailId;
    }

    public void setSrDetailId(BigInteger srDetailId) {
        this.srDetailId = srDetailId;
    }

    public String getMarktAssDtlId() {
        return marktAssDtlId;
    }

    public void setMarktAssDtlId(String marktAssDtlId) {
        this.marktAssDtlId = marktAssDtlId;
    }

    @XmlTransient
    public List<PrmsQuotationDetail> getPrmsQuotationDetailList() {
        if (prmsQuotationDetailList == null) {
            prmsQuotationDetailList = new ArrayList<>();
        }
        return prmsQuotationDetailList;
    }

    public void setPrmsQuotationDetailList(List<PrmsQuotationDetail> prmsQuotationDetailList) {
        this.prmsQuotationDetailList = prmsQuotationDetailList;
    }

}
