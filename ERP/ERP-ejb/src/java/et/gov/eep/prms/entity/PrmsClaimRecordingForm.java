/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.prms.entity;

import com.sun.javafx.scene.control.skin.VirtualFlow;
import et.gov.eep.commonApplications.entity.WfPrmsProcessed;
import et.gov.eep.fcms.entity.FmsLuCurrency;
import et.gov.eep.hrms.entity.organization.HrDepartments;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
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
@Table(name = "PRMS_CLAIM_RECORDING_FORM")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PrmsClaimRecordingForm.findAll", query = "SELECT p FROM PrmsClaimRecordingForm p"),
    @NamedQuery(name = "PrmsClaimRecordingForm.findByClaimNo", query = "SELECT p FROM PrmsClaimRecordingForm p WHERE p.claimNo = :claimNo"),
    @NamedQuery(name = "PrmsClaimRecordingForm.findByClaimNos", query = "SELECT p FROM PrmsClaimRecordingForm p WHERE p.claimNo Like :claimNo"),
    @NamedQuery(name = "PrmsClaimRecordingForm.findByLcNo", query = "SELECT p FROM PrmsClaimRecordingForm p WHERE p.lcNo = :lcNo"),
    @NamedQuery(name = "PrmsClaimRecordingForm.findByReqForApproval", query = "SELECT p FROM PrmsClaimRecordingForm  p WHERE p.status=0"),
    @NamedQuery(name = "PrmsClaimRecordingForm.findByClaimNossss", query = "SELECT p FROM PrmsClaimRecordingForm p WHERE p.claimNo = :claimNo"),
    @NamedQuery(name = "PrmsClaimRecordingForm.findByContractNo", query = "SELECT p FROM PrmsClaimRecordingForm p WHERE p.contractNo = :contractNo"),
    @NamedQuery(name = "PrmsClaimRecordingForm.findByCommecialInvoice", query = "SELECT p FROM PrmsClaimRecordingForm p WHERE p.commecialInvoice = :commecialInvoice"),
    @NamedQuery(name = "PrmsClaimRecordingForm.findByBankPermitNo", query = "SELECT p FROM PrmsClaimRecordingForm p WHERE p.bankPermitNo = :bankPermitNo"),
    @NamedQuery(name = "PrmsClaimRecordingForm.findByBillNo", query = "SELECT p FROM PrmsClaimRecordingForm p WHERE p.billNo = :billNo"),
    @NamedQuery(name = "PrmsClaimRecordingForm.findByPreparedBy", query = "SELECT p FROM PrmsClaimRecordingForm p WHERE p.preparedBy = :preparedBy"),
    @NamedQuery(name = "PrmsClaimRecordingForm.findByClaimDescription", query = "SELECT p FROM PrmsClaimRecordingForm p WHERE p.claimDescription = :claimDescription"),
    @NamedQuery(name = "PrmsClaimRecordingForm.findBySupplier", query = "SELECT p FROM PrmsClaimRecordingForm p WHERE p.supplier = :supplier"),
    @NamedQuery(name = "PrmsClaimRecordingForm.findByDepId", query = "SELECT p FROM PrmsClaimRecordingForm p WHERE p.depId = :depId"),
    @NamedQuery(name = "PrmsClaimRecordingForm.findByDateRegistered", query = "SELECT p FROM PrmsClaimRecordingForm p WHERE p.dateRegistered = :dateRegistered"),
    @NamedQuery(name = "PrmsClaimRecordingForm.findByDateRequested", query = "SELECT p FROM PrmsClaimRecordingForm p WHERE p.dateRequested = :dateRequested"),
    @NamedQuery(name = "PrmsClaimRecordingForm.findByDateReturned", query = "SELECT p FROM PrmsClaimRecordingForm p WHERE p.dateReturned = :dateReturned"),
    @NamedQuery(name = "PrmsClaimRecordingForm.findByContractAmount", query = "SELECT p FROM PrmsClaimRecordingForm p WHERE p.contractAmount = :contractAmount"),
    @NamedQuery(name = "PrmsClaimRecordingForm.findByDeliveredAmount", query = "SELECT p FROM PrmsClaimRecordingForm p WHERE p.deliveredAmount = :deliveredAmount"),
    @NamedQuery(name = "PrmsClaimRecordingForm.findByClaimRequestAmount", query = "SELECT p FROM PrmsClaimRecordingForm p WHERE p.claimRequestAmount = :claimRequestAmount"),
    @NamedQuery(name = "PrmsClaimRecordingForm.findByReturnedClaimAmount", query = "SELECT p FROM PrmsClaimRecordingForm p WHERE p.returnedClaimAmount = :returnedClaimAmount"),
    @NamedQuery(name = "PrmsClaimRecordingForm.findByMaxclaimId", query = "SELECT p FROM PrmsClaimRecordingForm p WHERE p.claimId =(SELECT Max(p.claimId)  from PrmsClaimRecordingForm p)"),
    @NamedQuery(name = "PrmsClaimRecordingForm.findByclaimId", query = "SELECT p FROM PrmsClaimRecordingForm p WHERE p.claimId = :claimId"),
    @NamedQuery(name = "PrmsClaimRecordingForm.findByUndeliveredBalance", query = "SELECT p FROM PrmsClaimRecordingForm p WHERE p.undeliveredBalance = :undeliveredBalance")})
public class PrmsClaimRecordingForm implements Serializable {

    @OneToMany(mappedBy = "claimId", cascade = CascadeType.ALL)
    private List<PrmsSupplierPerformanceInfo> prmsSupplierPerformanceInfoCollection;

    @OneToMany(mappedBy = "claimId", cascade = CascadeType.ALL)
    private List<WfPrmsProcessed> WfPrmsProcessedLists;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "claimId", fetch = FetchType.LAZY)
    private List<PrmsClaimDetail> prmsClaimDetailList;
    private static final long serialVersionUID = 1L;

    @Column(name = "CLAIM_NO")
    private String claimNo;

    @Column(name = "LC_NO")
    private String lcNo;

    @Column(name = "CONTRACT_NO")
    private String contractNo;

    @Column(name = "COMMECIAL_INVOICE")
    private String commecialInvoice;

    @Column(name = "BANK_PERMIT_NO")
    private String bankPermitNo;

    @Column(name = "BILL_NO")
    private String billNo;

    @Column(name = "CLAIM_DESCRIPTION")
    private String claimDescription;

    @Column(name = "SUPPLIER")
    private String supplier;

    @Column(name = "STATUS")
    private Integer status;

    @Column(name = "DATE_REGISTERED")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateRegistered;
    @Column(name = "DATE_REQUESTED")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateRequested;
    @Column(name = "DATE_RETURNED")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateReturned;

    @Column(name = "PROCESSED_ON")
    @Temporal(TemporalType.TIMESTAMP)
    private Date processedOn;

    @Column(name = "CONTRACT_AMOUNT", length = 100)
    private String contractAmount;

    @Column(name = "DELIVERED_AMOUNT")
    private Double deliveredAmount;

    @Column(name = "CLAIM_REQUEST_AMOUNT")
    private String claimRequestAmount;

    @Column(name = "RETURNED_CLAIM_AMOUNT")
    private Double returnedClaimAmount;

     //<editor-fold defaultstate="collapsed" desc="Dynamic Searching Transient Parameters">
    @Transient
    private String columnName;
    @Transient
    private String columnValue;
    //</editor-fold >
    @Column(name = "UNDELIVERED_BALANCE")
    private String undeliveredBalance;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PRMS_CLAIM_SEQ")
    @SequenceGenerator(name = "PRMS_CLAIM_SEQ", sequenceName = "PRMS_CLAIM_SEQ", allocationSize = 1)
    @Column(name = "CLAIM_ID", nullable = false, precision = 0, scale = -127)
    private BigDecimal claimId;
    @Size(max = 100)
    @Column(name = "REMARK", length = 100)
    private String remark;

    @Column(name = "PREPARED_BY")
    private Integer preparedBy;
    @JoinColumn(name = "CURRENCY_ID", referencedColumnName = "CURRENCY_ID")
    @ManyToOne
    private FmsLuCurrency currencyId;
    @JoinColumn(name = "AWARD_ID", referencedColumnName = "AWARD_ID")
    @ManyToOne
    private PrmsAward awardId;
    @JoinColumn(name = "CONTRACT_ID", referencedColumnName = "CONTRACT_ID")
    @ManyToOne
    private PrmsContract contractId;
    @JoinColumn(name = "DEP_ID", referencedColumnName = "DEP_ID")
    @ManyToOne
    private HrDepartments depId;

    @JoinColumn(name = "LC_ID", referencedColumnName = "LC_ID")
    @ManyToOne
    private PrmsLcRigistration lcId;

    public PrmsClaimRecordingForm() {
    }

    public PrmsClaimRecordingForm(BigDecimal claimId) {
        this.claimId = claimId;
    }

    public String getClaimNo() {
        return claimNo;
    }

    public void setClaimNo(String claimNo) {
        this.claimNo = claimNo;
    }

    public String getLcNo() {
        return lcNo;
    }

    public void setLcNo(String lcNo) {
        this.lcNo = lcNo;
    }

    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }

    public String getCommecialInvoice() {
        return commecialInvoice;
    }

    public void setCommecialInvoice(String commecialInvoice) {
        this.commecialInvoice = commecialInvoice;
    }

    public String getBankPermitNo() {
        return bankPermitNo;
    }

    public void setBankPermitNo(String bankPermitNo) {
        this.bankPermitNo = bankPermitNo;
    }

    public String getBillNo() {
        return billNo;
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

    public String getClaimDescription() {
        return claimDescription;
    }

    public void setClaimDescription(String claimDescription) {
        this.claimDescription = claimDescription;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public HrDepartments getDepId() {
        return depId;
    }

    public void setDepId(HrDepartments depId) {
        this.depId = depId;
    }

    public Date getDateRegistered() {
        return dateRegistered;
    }

    public void setDateRegistered(Date dateRegistered) {
        this.dateRegistered = dateRegistered;
    }

    public Date getDateRequested() {
        return dateRequested;
    }

    public void setDateRequested(Date dateRequested) {
        this.dateRequested = dateRequested;
    }

    public Date getDateReturned() {
        return dateReturned;
    }

    public void setDateReturned(Date dateReturned) {
        this.dateReturned = dateReturned;
    }

    public String getContractAmount() {
        return contractAmount;
    }

    public void setContractAmount(String contractAmount) {
        this.contractAmount = contractAmount;
    }

    public Double getDeliveredAmount() {
        return deliveredAmount;
    }

    public void setDeliveredAmount(Double deliveredAmount) {
        this.deliveredAmount = deliveredAmount;
    }

    public Double getReturnedClaimAmount() {
        return returnedClaimAmount;
    }

    public void setReturnedClaimAmount(Double returnedClaimAmount) {
        this.returnedClaimAmount = returnedClaimAmount;
    }

    public String getClaimRequestAmount() {
        return claimRequestAmount;
    }

    public void setClaimRequestAmount(String claimRequestAmount) {
        this.claimRequestAmount = claimRequestAmount;
    }

    public String getUndeliveredBalance() {
        return undeliveredBalance;
    }

    public void setUndeliveredBalance(String undeliveredBalance) {
        this.undeliveredBalance = undeliveredBalance;
    }

    public BigDecimal getClaimId() {
        return claimId;
    }

    public void setClaimId(BigDecimal claimId) {
        this.claimId = claimId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getPreparedBy() {
        return preparedBy;
    }

    public void setPreparedBy(Integer preparedBy) {
        this.preparedBy = preparedBy;
    }

    public FmsLuCurrency getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(FmsLuCurrency currencyId) {
        this.currencyId = currencyId;
    }

    public PrmsAward getAwardId() {
        return awardId;
    }

    public void setAwardId(PrmsAward awardId) {
        this.awardId = awardId;
    }

    public PrmsContract getContractId() {
        return contractId;
    }

    public void setContractId(PrmsContract contractId) {
        this.contractId = contractId;
    }

    public PrmsLcRigistration getLcId() {
        return lcId;
    }

    public void setLcId(PrmsLcRigistration lcId) {
        this.lcId = lcId;
    }

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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (claimId != null ? claimId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
       
        if (!(object instanceof PrmsClaimRecordingForm)) {
            return false;
        }
        PrmsClaimRecordingForm other = (PrmsClaimRecordingForm) object;
        if ((this.claimId == null && other.claimId != null) || (this.claimId != null && !this.claimId.equals(other.claimId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return claimNo;
    }

    @XmlTransient
    public List<PrmsClaimDetail> getPrmsClaimDetailList() {
        if (prmsClaimDetailList == null) {
            prmsClaimDetailList = new ArrayList<>();
        }
        return prmsClaimDetailList;
    }

    public void setPrmsClaimDetailList(List<PrmsClaimDetail> prmsClaimDetailList) {
        this.prmsClaimDetailList = prmsClaimDetailList;
    }

    @XmlTransient
    public List<PrmsSupplierPerformanceInfo> getPrmsSupplierPerformanceInfoCollection() {
        if (prmsSupplierPerformanceInfoCollection == null) {
            prmsSupplierPerformanceInfoCollection = new ArrayList<>();
        }
        return prmsSupplierPerformanceInfoCollection;
    }

    public void setPrmsSupplierPerformanceInfoCollection(List<PrmsSupplierPerformanceInfo> prmsSupplierPerformanceInfoCollection) {
        this.prmsSupplierPerformanceInfoCollection = prmsSupplierPerformanceInfoCollection;
    }

    /**
     * @return the status
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * @return the WfPrmsProcessedLists
     */
    public List<WfPrmsProcessed> getWfPrmsProcessedLists() {
        if (WfPrmsProcessedLists == null) {
            WfPrmsProcessedLists = new ArrayList<>();
        }
        return WfPrmsProcessedLists;
    }

    /**
     * @param WfPrmsProcessedLists the WfPrmsProcessedLists to set
     */
    public void setWfPrmsProcessedLists(List<WfPrmsProcessed> WfPrmsProcessedLists) {
        this.WfPrmsProcessedLists = WfPrmsProcessedLists;
    }

    public Date getProcessedOn() {
        return processedOn;
    }

    public void setProcessedOn(Date processedOn) {
        this.processedOn = processedOn;
    }

}
