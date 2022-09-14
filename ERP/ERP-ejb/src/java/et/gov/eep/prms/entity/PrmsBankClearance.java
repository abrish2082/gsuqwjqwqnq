/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.prms.entity;

import et.gov.eep.commonApplications.entity.PrmsLuDmArchive;
import et.gov.eep.commonApplications.entity.WfPrmsProcessed;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Date;
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
import java.util.ArrayList;
import javax.persistence.Transient;

/**
 *
 * @author user
 */
@Entity
@Table(name = "PRMS_BANK_CLEARANCE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PrmsBankClearance.findAll", query = "SELECT p FROM PrmsBankClearance p"),
    @NamedQuery(name = "PrmsBankClearance.findByClearanceNo", query = "SELECT p FROM PrmsBankClearance p WHERE p.clearanceNo = :clearanceNo"),
    @NamedQuery(name = "PrmsBankClearance.findByBankReqForApproval", query = "SELECT p FROM PrmsBankClearance p WHERE p.status=0"),
    @NamedQuery(name = "PrmsBankClearance.searchByClearanceNo", query = "SELECT p FROM PrmsBankClearance p WHERE p.clearanceNo LIKE :clearanceNo and p.preparedBy=:preparedBy"),
    @NamedQuery(name = "PrmsBankClearance.SearchByClearanceIdSelection", query = "SELECT p FROM PrmsBankClearance p WHERE p.clearanceNo = :clearanceNo"),
    @NamedQuery(name = "PrmsBankClearance.generateByMaxClearanceNo", query = "SELECT p FROM PrmsBankClearance p WHERE p.clearanceId = (SELECT Max(p.clearanceId) from PrmsBankClearance p)"),
    @NamedQuery(name = "PrmsBankClearance.findByGoodsName", query = "SELECT p FROM PrmsBankClearance p WHERE p.goodsName = :goodsName"),
    @NamedQuery(name = "PrmsBankClearance.findByLetterOfCreditAmount", query = "SELECT p FROM PrmsBankClearance p WHERE p.letterOfCreditAmount = :letterOfCreditAmount"),
    @NamedQuery(name = "PrmsBankClearance.findByBankPermitNo", query = "SELECT p FROM PrmsBankClearance p WHERE p.bankPermitNo = :bankPermitNo"),
    @NamedQuery(name = "PrmsBankClearance.findByDeliveredBy", query = "SELECT p FROM PrmsBankClearance p WHERE p.deliveredBy = :deliveredBy"),
    @NamedQuery(name = "PrmsBankClearance.findByPreparedBy", query = "SELECT p FROM PrmsBankClearance p WHERE p.preparedBy = :preparedBy"),
    @NamedQuery(name = "PrmsBankClearance.findByRegisteredDate", query = "SELECT p FROM PrmsBankClearance p WHERE p.registeredDate = :registeredDate"),
    @NamedQuery(name = "PrmsBankClearance.findByBillType", query = "SELECT p FROM PrmsBankClearance p WHERE p.billType = :billType"),
    @NamedQuery(name = "PrmsBankClearance.findByBillNo", query = "SELECT p FROM PrmsBankClearance p WHERE p.billNo = :billNo"),
    @NamedQuery(name = "PrmsBankClearance.findByDeliveredAmount", query = "SELECT p FROM PrmsBankClearance p WHERE p.deliveredAmount = :deliveredAmount"),
    @NamedQuery(name = "PrmsBankClearance.findByRemark", query = "SELECT p FROM PrmsBankClearance p WHERE p.remark = :remark"),
    @NamedQuery(name = "PrmsBankClearance.findByClearanceId", query = "SELECT p FROM PrmsBankClearance p WHERE p.clearanceId = :clearanceId"),
    @NamedQuery(name = "PrmsBankClearance.findByInvoiceNo", query = "SELECT p FROM PrmsBankClearance p WHERE p.invoiceNo = :invoiceNo"),
    @NamedQuery(name = "PrmsBankClearance.findByDeclarationNo", query = "SELECT p FROM PrmsBankClearance p WHERE p.declarationNo = :declarationNo"),
    @NamedQuery(name = "PrmsBankClearance.findByInvoiceAmount", query = "SELECT p FROM PrmsBankClearance p WHERE p.invoiceAmount = :invoiceAmount"),
    @NamedQuery(name = "PrmsBankClearance.findByPermitAmount", query = "SELECT p FROM PrmsBankClearance p WHERE p.permitAmount = :permitAmount"),
    @NamedQuery(name = "PrmsBankClearance.findByProcessedDate", query = "SELECT p FROM PrmsBankClearance p WHERE p.processedDate=:processedDate"),
    @NamedQuery(name = "PrmsBankClearance.findByClearedAmount", query = "SELECT p FROM PrmsBankClearance p WHERE p.clearedAmount = :clearedAmount"),
    @NamedQuery(name = "PrmsBankClearance.findByAttachmentRefNumber", query = "SELECT p FROM PrmsBankClearance p WHERE p.attachmentRefNumber=:attachmentRefNumber"),
    @NamedQuery(name = "PrmsBankClearance.findByStatus", query = "SELECT p FROM PrmsBankClearance p WHERE p.status=:status")})
public class PrmsBankClearance implements Serializable {

    @OneToMany(mappedBy = "clearanceId", cascade = CascadeType.ALL)
    private List<WfPrmsProcessed> wfPrmsProcessedLists;

    private static final long serialVersionUID = 1L;
    @Size(max = 30)
    @Column(name = "CLEARANCE_NO", length = 30)
    private String clearanceNo;
    @Size(max = 200)
    @Column(name = "GOODS_NAME", length = 200)
    private String goodsName;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "LETTER_OF_CREDIT_AMOUNT", precision = 126)
    private Double letterOfCreditAmount;
    @Size(max = 30)
    @Column(name = "BANK_PERMIT_NO", length = 30)
    private String bankPermitNo;
    @Size(max = 30)
    @Column(name = "DELIVERED_BY", length = 30)
    private String deliveredBy;

    @Column(name = "PREPARED_BY")
    private Integer preparedBy;
    @Column(name = "REGISTERED_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date registeredDate;
    @Column(name = "PROCESSED_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date processedDate;
    @Size(max = 30)
    @Column(name = "BILL_TYPE", length = 30)
    private String billType;
    @Size(max = 30)
    @Column(name = "BILL_NO", length = 30)
    private String billNo;
    @Column(name = "DELIVERED_AMOUNT", precision = 126)
    private Double deliveredAmount;
    @Size(max = 75)
    @Column(name = "REMARK", length = 75)
    private String remark;
    @JoinColumn(name = "ATTACHMENT_REF_NUMBER", referencedColumnName = "DOCUMENT_ID")
    @ManyToOne
    private PrmsLuDmArchive attachmentRefNumber;
    @Column(name = "STATUS")
    private Integer status;

    // <editor-fold defaultstate="collapsed" desc="Declare Variable for Dynamic Searching">
    @Transient
    private String columnName;

    @Transient
    private String columnValue;
    // </editor-fold>

    @Id
    @Basic(optional = false)
    @NotNull
    @GeneratedValue(generator = "PRMS_BANK_CLEARANCE_SEQ", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "PRMS_BANK_CLEARANCE_SEQ", sequenceName = "PRMS_BANK_CLEARANCE_SEQ", allocationSize = 1)
    @Column(name = "CLEARANCE_ID", nullable = false, precision = 0, scale = -127)
    private BigDecimal clearanceId;
    @Size(max = 35)
    @Column(name = "INVOICE_NO", length = 35)
    private String invoiceNo;
    @Size(max = 35)
    @Column(name = "DECLARATION_NO", length = 35)
    private String declarationNo;
    @Column(name = "INVOICE_AMOUNT", precision = 126)
    private Double invoiceAmount;
    @Column(name = "PERMIT_AMOUNT", precision = 126)
    private Double permitAmount;
    @Column(name = "CLEARED_AMOUNT", precision = 126)
    private Double clearedAmount;
    @JoinColumn(name = "CONTRACT_ID", referencedColumnName = "CONTRACT_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private PrmsContract contractId;
    @JoinColumn(name = "LETTER_OF_CREDIT_NO", referencedColumnName = "LC_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private PrmsLcRigistration letterOfCreditNo;
    @JoinColumn(name = "SUPPLIER_ID", referencedColumnName = "ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private PrmsSupplyProfile supplierId;

    public PrmsBankClearance() {
    }

    public PrmsBankClearance(BigDecimal clearanceId) {
        this.clearanceId = clearanceId;
    }

    public String getClearanceNo() {
        return clearanceNo;
    }

    public void setClearanceNo(String clearanceNo) {
        this.clearanceNo = clearanceNo;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public Double getLetterOfCreditAmount() {
        return letterOfCreditAmount;
    }

    public void setLetterOfCreditAmount(Double letterOfCreditAmount) {
        this.letterOfCreditAmount = letterOfCreditAmount;
    }

    public String getBankPermitNo() {
        return bankPermitNo;
    }

    public void setBankPermitNo(String bankPermitNo) {
        this.bankPermitNo = bankPermitNo;
    }

    public String getDeliveredBy() {
        return deliveredBy;
    }

    public void setDeliveredBy(String deliveredBy) {
        this.deliveredBy = deliveredBy;
    }

    public Integer getPreparedBy() {
        return preparedBy;
    }

    public void setPreparedBy(Integer preparedBy) {
        this.preparedBy = preparedBy;
    }

    public Date getRegisteredDate() {
        return registeredDate;
    }

    public void setRegisteredDate(Date registeredDate) {
        this.registeredDate = registeredDate;
    }

    public Date getProcessedDate() {
        return processedDate;
    }

    public void setProcessedDate(Date processedDate) {
        this.processedDate = processedDate;
    }

    public String getBillType() {
        return billType;
    }

    public void setBillType(String billType) {
        this.billType = billType;
    }

    public String getBillNo() {
        return billNo;
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

    public Double getDeliveredAmount() {
        return deliveredAmount;
    }

    public void setDeliveredAmount(Double deliveredAmount) {
        this.deliveredAmount = deliveredAmount;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public PrmsLuDmArchive getAttachmentRefNumber() {
        return attachmentRefNumber;
    }

    public void setAttachmentRefNumber(PrmsLuDmArchive attachmentRefNumber) {
        this.attachmentRefNumber = attachmentRefNumber;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

     // <editor-fold defaultstate="collapsed" desc="getter and setter declared Variables for Dynamic Searching">
    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getColumnValue() {
        return columnValue;
    }

    public void setColumnValue(String columnValue) {
        this.columnValue = columnValue;
    }
// </editor-fold>

    public BigDecimal getClearanceId() {
        return clearanceId;
    }

    public void setClearanceId(BigDecimal clearanceId) {
        this.clearanceId = clearanceId;
    }

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public String getDeclarationNo() {
        return declarationNo;
    }

    public void setDeclarationNo(String declarationNo) {
        this.declarationNo = declarationNo;
    }

    public Double getInvoiceAmount() {
        return invoiceAmount;
    }

    public void setInvoiceAmount(Double invoiceAmount) {
        this.invoiceAmount = invoiceAmount;
    }

    public Double getPermitAmount() {
        return permitAmount;
    }

    public void setPermitAmount(Double permitAmount) {
        this.permitAmount = permitAmount;
    }

    public Double getClearedAmount() {
        return clearedAmount;
    }

    public void setClearedAmount(Double clearedAmount) {
        this.clearedAmount = clearedAmount;
    }

    public PrmsContract getContractId() {
        return contractId;
    }

    public void setContractId(PrmsContract contractId) {
        this.contractId = contractId;
    }

    public PrmsLcRigistration getLetterOfCreditNo() {
        return letterOfCreditNo;
    }

    public void setLetterOfCreditNo(PrmsLcRigistration letterOfCreditNo) {
        this.letterOfCreditNo = letterOfCreditNo;
    }

    public PrmsSupplyProfile getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(PrmsSupplyProfile supplierId) {
        this.supplierId = supplierId;
    }

    @XmlTransient
    public List<WfPrmsProcessed> getWfPrmsProcessedLists() {
        if (wfPrmsProcessedLists == null) {
            wfPrmsProcessedLists = new ArrayList<>();
        }
        return wfPrmsProcessedLists;
    }

    public void setWfPrmsProcessedLists(List<WfPrmsProcessed> wfPrmsProcessedLists) {
        this.wfPrmsProcessedLists = wfPrmsProcessedLists;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (clearanceId != null ? clearanceId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PrmsBankClearance)) {
            return false;
        }
        PrmsBankClearance other = (PrmsBankClearance) object;
        if ((this.clearanceId == null && other.clearanceId != null) || (this.clearanceId != null && !this.clearanceId.equals(other.clearanceId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "et.gov.eep.prms.entity.PrmsBankClearance[ clearanceId=" + clearanceId + " ]";
    }

}
