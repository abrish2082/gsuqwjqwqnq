/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.prms.entity;

//import et.gov.eep.fcms.entity.budget.FmsOperatingBudget;
import et.gov.eep.mms.entity.MmsItemRegistration;
import et.gov.eep.mms.entity.MmsServiceReg;
import et.gov.eep.mms.entity.MmsStorereq;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@Table(name = "PRMS_PURCHASE_REQUEST_DET")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PrmsPurchaseRequestDet.findAll", query = "SELECT p FROM PrmsPurchaseRequestDet p"),
    @NamedQuery(name = "PrmsPurchaseRequestDet.findById", query = "SELECT p FROM PrmsPurchaseRequestDet p WHERE p.id = :id"),
    @NamedQuery(name = "PrmsPurchaseRequestDet.findByAccountCode", query = "SELECT p FROM PrmsPurchaseRequestDet p WHERE p.accountCode = :accountCode"),
    @NamedQuery(name = "PrmsPurchaseRequestDet.findByPartNo", query = "SELECT p FROM PrmsPurchaseRequestDet p WHERE p.partNo = :partNo"),
    @NamedQuery(name = "PrmsPurchaseRequestDet.findByItemName", query = "SELECT p FROM PrmsPurchaseRequestDet p WHERE p.purchaseReqId.prNumber=:purchaseReqId"),
    @NamedQuery(name = "PrmsPurchaseRequestDet.findByItemNameItem", query = "SELECT p FROM PrmsPurchaseRequestDet p WHERE p.materialCodeId.matCode IS NOT NULL"),
    @NamedQuery(name = "PrmsPurchaseRequestDet.findByItemNameService", query = "SELECT p FROM PrmsPurchaseRequestDet p WHERE p.serviceId.serviceId IS NOT NULL"),
    @NamedQuery(name = "PrmsPurchaseRequestDet.findByItemCod", query = "SELECT p FROM PrmsPurchaseRequestDet p WHERE p.materialCodeId IS NOT NULL"),
    @NamedQuery(name = "PrmsPurchaseRequestDet.findByprNumber", query = "SELECT p FROM PrmsPurchaseRequestDet p WHERE p.purchaseReqId = :purchaseReqId"),
    @NamedQuery(name = "PrmsPurchaseRequestDet.findByprNumb", query = "SELECT p FROM PrmsPurchaseRequestDet p WHERE p.id = :prNumber"),
    @NamedQuery(name = "PrmsPurchaseRequestDet.findByprNumbers", query = "SELECT p FROM PrmsPurchaseRequestDet p WHERE p.purchaseReqId.prNumber = :prNumber"),
    @NamedQuery(name = "PrmsPurchaseRequestDet.findByItemCode", query = "SELECT p FROM PrmsPurchaseRequestDet p WHERE p.materialCodeId.matCode = :materialCodeId"),
    @NamedQuery(name = "PrmsPurchaseRequestDet.findByItem", query = "SELECT p FROM PrmsPurchaseRequestDet p WHERE p.id = :materialCodeId"),
    @NamedQuery(name = "PrmsPurchaseRequestDet.findByPrNo", query = "SELECT p FROM PrmsPurchaseRequestDet p WHERE p.purchaseReqId IS NOT NULL AND P.materialCodeId IS NOT NULL"),
    @NamedQuery(name = "PrmsPurchaseRequestDet.findByReqQuantity", query = "SELECT p FROM PrmsPurchaseRequestDet p WHERE p.reqQuantity = :reqQuantity"),
    @NamedQuery(name = "PrmsPurchaseRequestDet.findByUnitPrice", query = "SELECT p FROM PrmsPurchaseRequestDet p WHERE p.unitPrice = :unitPrice"),
    @NamedQuery(name = "PrmsPurchaseRequestDet.findByVat", query = "SELECT p FROM PrmsPurchaseRequestDet p WHERE p.vat = :vat")})
public class PrmsPurchaseRequestDet implements Serializable {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "purchaseRquestDetId", fetch = FetchType.LAZY)
    private List<PrmsQuotationDetail> prmsQuotationDetailList;

    @JoinColumn(name = "MARKT_ASS_DTL_ID", referencedColumnName = "ID")
    @ManyToOne
    private PrmsMarketAssessmentDetail marktAssDtlId;
    @JoinColumn(name = "SERVICE_ID", referencedColumnName = "SERVICE_ID")
    @ManyToOne
    private MmsServiceReg serviceId;
    @JoinColumn(name = "SR_DETAIL_ID", referencedColumnName = "STORE_REQ_ID")
    @ManyToOne
    private MmsStorereq stDetailId;
    @Size(max = 20)
    @Column(name = "MATNAME", length = 20)
    private String matname;
    @Size(max = 20)
    @Column(name = "MATCODE", length = 20)
    private String matcode;
//    @Column(name = "FILE_REF_NO_GOODS")
//    private Integer fileRefNoGoods;

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(max = 20)
    @Column(name = "ID", nullable = false, length = 20)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "EEP_PURCHAS_REQ_DE_SEQ")
    @SequenceGenerator(name = "EEP_PURCHAS_REQ_DE_SEQ", sequenceName = "EEP_PURCHAS_REQ_DE_SEQ", allocationSize = 1)
    private String id;
    @Size(max = 255)
    @Column(name = "ACCOUNT_CODE", length = 255)
    private String accountCode;
    @Column(name = "PART_NO")
    private Long partNo;

    @Column(name = "REQ_QUANTITY")
    private Integer reqQuantity;
    @Column(name = "UNIT_PRICE")
    private BigDecimal unitPrice;
    @Size(max = 50)
    @Column(name = "VAT", length = 50)
    private String vat;

    @Column(name = "UNIT_MEASURE", length = 20)
    private String unitMeasre;

    @JoinColumn(name = "MATERIAL_CODE_ID", referencedColumnName = "MATERIAL_ID")
    @ManyToOne
    private MmsItemRegistration materialCodeId;
    @JoinColumn(name = "PURCHASE_REQ_ID", referencedColumnName = "ID")
    @ManyToOne
    private PrmsPurchaseRequest purchaseReqId;

//    @JoinColumn(name = "BUDGET_CODE", referencedColumnName = "O_B_DETAIL_ID")
//    @ManyToOne(fetch = FetchType.LAZY)
//    private FmsOperatingBudgetDetail budgetCode;

    /**
     *
     */
    public PrmsPurchaseRequestDet() {
    }

    /**
     *
     * @param id
     */
    public PrmsPurchaseRequestDet(String id) {
        this.id = id;
    }

    /**
     *
     * @return
     */
    public String getId() {
        return id;
    }

    /**
     *
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     *
     * @return
     */
    public String getAccountCode() {
        return accountCode;
    }

    /**
     *
     * @param accountCode
     */
    public void setAccountCode(String accountCode) {
        this.accountCode = accountCode;
    }

    /**
     *
     * @return
     */
    public Long getPartNo() {
        return partNo;
    }

    /**
     *
     * @param partNo
     */
    public void setPartNo(Long partNo) {
        this.partNo = partNo;
    }

    public Integer getReqQuantity() {
        return reqQuantity;
    }

    /**
     *
     * @param reqQuantity
     */
    public void setReqQuantity(Integer reqQuantity) {
        this.reqQuantity = reqQuantity;
    }

    /**
     *
     * @return
     */
    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    /**
     *
     * @param unitPrice
     */
    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getVat() {
        return vat;
    }

    public void setVat(String vat) {
        this.vat = vat;
    }

    public MmsItemRegistration getMaterialCodeId() {
        return materialCodeId;
    }

    /**
     *
     * @param materialCodeId
     */
    public void setMaterialCodeId(MmsItemRegistration materialCodeId) {
        this.materialCodeId = materialCodeId;
    }

    /**
     *
     * @return
     */
    public PrmsPurchaseRequest getPurchaseReqId() {
        return purchaseReqId;
    }

    /**
     *
     * @param purchaseReqId
     */
    public void setPurchaseReqId(PrmsPurchaseRequest purchaseReqId) {
        this.purchaseReqId = purchaseReqId;
    }

//    public FmsOperatingBudgetDetail getBudgetCode() {
//        return budgetCode;
//    }
//
//    public void setBudgetCode(FmsOperatingBudgetDetail budgetCode) {
//        this.budgetCode = budgetCode;
//    }

//    public Integer getFileRefNoGoods() {
//        return fileRefNoGoods;
//    }
//
//    public void setFileRefNoGoods(Integer fileRefNoGoods) {
//        this.fileRefNoGoods = fileRefNoGoods;
//    }
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PrmsPurchaseRequestDet)) {
            return false;
        }
        PrmsPurchaseRequestDet other = (PrmsPurchaseRequestDet) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return id;
    }

    public String getUnitMeasre() {
        return unitMeasre;
    }

    public void setUnitMeasre(String unitMeasre) {
        this.unitMeasre = unitMeasre;
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

    public MmsStorereq getStDetailId() {
        return stDetailId;
    }

    public void setStDetailId(MmsStorereq stDetailId) {
        this.stDetailId = stDetailId;
    }

    
   

    public MmsServiceReg getServiceId() {
        return serviceId;
    }

    public void setServiceId(MmsServiceReg serviceId) {
        this.serviceId = serviceId;
    }

    public PrmsMarketAssessmentDetail getMarktAssDtlId() {
        return marktAssDtlId;
    }

    public void setMarktAssDtlId(PrmsMarketAssessmentDetail marktAssDtlId) {
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
