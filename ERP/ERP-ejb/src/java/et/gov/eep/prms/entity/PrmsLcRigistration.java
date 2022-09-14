/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.prms.entity;

import et.gov.eep.commonApplications.entity.WfPrmsProcessed;
import et.gov.eep.commonApplications.entity.ComLuCountry;
import et.gov.eep.commonApplications.entity.PrmsLuDmArchive;
import et.gov.eep.fcms.entity.FmsLuCurrency;
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
@Table(name = "PRMS_LC_RIGISTRATION")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PrmsLcRigistration.findAll", query = "SELECT p FROM PrmsLcRigistration p"),
    @NamedQuery(name = "PrmsLcRigistration.findAlls", query = "SELECT p FROM PrmsLcRigistration p WHERE p.status=3"),
    @NamedQuery(name = "PrmsLcRigistration.findByLcNo", query = "SELECT p FROM PrmsLcRigistration p WHERE p.lcNo = :lcNo"),
    @NamedQuery(name = "PrmsLcRigistration.searchByLcNoForAmount", query = "SELECT p FROM PrmsLcRigistration p WHERE p.lcNo = :lcNo"),
    @NamedQuery(name = "PrmsLcRigistration.findByReqForApproval", query = "SELECT p FROM PrmsLcRigistration  p WHERE p.status=0"),
    @NamedQuery(name = "PrmsLcRigistration.searchfindByLcNo", query = "SELECT p FROM PrmsLcRigistration p WHERE p.lcNo = :lcNo"),
    @NamedQuery(name = "PrmsLcRigistration.findByLCNos", query = "SELECT p FROM PrmsLcRigistration p WHERE p.lcNo = :lcNo"),
    @NamedQuery(name = "PrmsLcRigistration.findByLcNoLike", query = "SELECT p FROM PrmsLcRigistration p WHERE p.lcNo LIKE :lcNo"),
    @NamedQuery(name = "PrmsLcRigistration.findByLcType", query = "SELECT p FROM PrmsLcRigistration p WHERE p.lcType = :lcType"),
    @NamedQuery(name = "PrmsLcRigistration.findByLcAmount", query = "SELECT p FROM PrmsLcRigistration p WHERE p.lcAmount = :lcAmount"),
    @NamedQuery(name = "PrmsLcRigistration.findByExchangeRate", query = "SELECT p FROM PrmsLcRigistration p WHERE p.exchangeRate = :exchangeRate"),
    @NamedQuery(name = "PrmsLcRigistration.findByRigistrationDate", query = "SELECT p FROM PrmsLcRigistration p WHERE p.rigistrationDate = :rigistrationDate"),
    @NamedQuery(name = "PrmsLcRigistration.findByLcAmendRef", query = "SELECT p FROM PrmsLcRigistration p WHERE p.lcAmendRef = :lcAmendRef"),
    @NamedQuery(name = "PrmsLcRigistration.findByDescribition", query = "SELECT p FROM PrmsLcRigistration p WHERE p.describition = :describition"),
    @NamedQuery(name = "PrmsLcRigistration.findByShipmentDate", query = "SELECT p FROM PrmsLcRigistration p WHERE p.shipmentDate = :shipmentDate"),
    @NamedQuery(name = "PrmsLcRigistration.findByNoOfLcExtention", query = "SELECT p FROM PrmsLcRigistration p WHERE p.noOfLcExtention = :noOfLcExtention"),
    @NamedQuery(name = "PrmsLcRigistration.findByPortOfLoding", query = "SELECT p FROM PrmsLcRigistration p WHERE p.portOfLoding = :portOfLoding"),
    @NamedQuery(name = "PrmsLcRigistration.findByPortOfDischarge", query = "SELECT p FROM PrmsLcRigistration p WHERE p.portOfDischarge = :portOfDischarge"),
    @NamedQuery(name = "PrmsLcRigistration.findByLcExtCodition", query = "SELECT p FROM PrmsLcRigistration p WHERE p.lcExtCodition = :lcExtCodition"),
    @NamedQuery(name = "PrmsLcRigistration.findByLcOpeningDate", query = "SELECT p FROM PrmsLcRigistration p WHERE p.lcOpeningDate = :lcOpeningDate"),
    @NamedQuery(name = "PrmsLcRigistration.findByLcExpireDate", query = "SELECT p FROM PrmsLcRigistration p WHERE p.lcExpireDate = :lcExpireDate"),
    @NamedQuery(name = "PrmsLcRigistration.findByLcId", query = "SELECT p FROM PrmsLcRigistration p WHERE p.lcId = :lcId"),
    @NamedQuery(name = "PrmsLcRigistration.findByMaxLcId", query = "SELECT p FROM PrmsLcRigistration p WHERE p.lcId =(SELECT Max(p.lcId)  from PrmsLcRigistration p)"),
    @NamedQuery(name = "PrmsLcRigistration.findBySwitchCode", query = "SELECT p FROM PrmsLcRigistration p WHERE p.switchCode = :switchCode"),
    @NamedQuery(name = "PrmsLcRigistration.findByAmendmentCostCovered", query = "SELECT p FROM PrmsLcRigistration p WHERE p.amendmentCostCovered = :amendmentCostCovered"),
    @NamedQuery(name = "PrmsLcRigistration.findByPreparedBy", query = "SELECT p FROM PrmsLcRigistration p WHERE p.preparedBy = :preparedBy"),
    @NamedQuery(name = "PrmsLcRigistration.findByApprovedStatus", query = "SELECT p FROM PrmsLcRigistration p WHERE p.status=:status"),
    @NamedQuery(name = "PrmsLcRigistration.findByRemark", query = "SELECT p FROM PrmsLcRigistration p WHERE p.remark = :remark"),
    @NamedQuery(name = "PrmsLcRigistration.findByAccountCode", query = "SELECT p FROM PrmsLcRigistration p WHERE p.accountCode = :accountCode"),
    @NamedQuery(name = "PrmsLcRigistration.findByEndUser", query = "SELECT p FROM PrmsLcRigistration p WHERE p.endUser = :endUser"),
//    @NamedQuery(name = "PrmsLcRigistration.findByPermitNo", query = "SELECT p FROM PrmsLcRigistration p WHERE p.permitNo = :permitNo"),
    @NamedQuery(name = "PrmsLcRigistration.findBySupplier", query = "SELECT p FROM PrmsLcRigistration p WHERE p.supplier = :supplier"),
    @NamedQuery(name = "PrmsLcRigistration.findByPermitAmount", query = "SELECT p FROM PrmsLcRigistration p WHERE p.permitAmount = :permitAmount")})
public class PrmsLcRigistration implements Serializable {

    @Column(name = "PROCESSED_ON")
    @Temporal(TemporalType.TIMESTAMP)
    private Date processedOn;

    @Column(name = "PARTIAL_SHIPMENT")
    private String partialShipment;

    @JoinColumn(name = "FOREIGN_ID", referencedColumnName = "ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private PrmsForeignExchange foreignId;

    @OneToMany(mappedBy = "lcId", cascade = CascadeType.ALL)
    private List<PrmsSupplierPerformanceInfo> prmsSupplierPerformanceInfoCollection;
    @OneToMany(mappedBy = "lcId", cascade = CascadeType.ALL)
    private List<WfPrmsProcessed> wfPrmsProcessedLists;
    @OneToMany(mappedBy = "letterOfCreditNo", fetch = FetchType.LAZY)
    private List<PrmsBankClearance> prmsBankClearanceCollection;
    @OneToMany(mappedBy = "lcId", fetch = FetchType.LAZY)
    private List<PrmsLcRigistrationAmend> prmsLcRigistrationAmendCollection;
    @OneToMany(mappedBy = "lcId", fetch = FetchType.LAZY)
    private List<PrmsClaimRecordingForm> prmsClaimRecordingFormCollection;
    @OneToMany(mappedBy = "lcId")
    private List<PrmsFileUpload> prmsFileUploadList;

    private static final long serialVersionUID = 1L;
    @Size(max = 25)
    @Column(name = "LC_NO")
    private String lcNo;
    @Size(max = 50)
    @Column(name = "LC_TYPE")
    private String lcType;
    @JoinColumn(name = "DOCUMENT_ID", referencedColumnName = "DOCUMENT_ID")
    @ManyToOne
    private PrmsLuDmArchive documentId;
    @Column(name = "LC_AMOUNT")
    private Double lcAmount;
    @Column(name = "EXCHANGE_RATE")
    private BigInteger exchangeRate;
    @Column(name = "RIGISTRATION_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date rigistrationDate;
    @Size(max = 20)
    @Column(name = "LC_AMEND_REF")
    private String lcAmendRef;
    @Size(max = 50)
    @Column(name = "DESCRIBITION")
    private String describition;
    @Column(name = "SHIPMENT_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date shipmentDate;
    @Size(max = 20)
    @Column(name = "NO_OF_LC_EXTENTION")
    private String noOfLcExtention;
    @Size(max = 20)
    @Column(name = "LC_EXT_CODITION")
    private String lcExtCodition;
    @Column(name = "LC_OPENING_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lcOpeningDate;
    @Column(name = "LC_EXPIRE_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lcExpireDate;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PRMS_LC_REGISTRATION_SEQ")
    @SequenceGenerator(name = "PRMS_LC_REGISTRATION_SEQ", sequenceName = "PRMS_LC_REGISTRATION_SEQ", allocationSize = 1)
    @Column(name = "LC_ID")
    private BigDecimal lcId;
    @Size(max = 50)
    @Column(name = "SWITCH_CODE")
    private String switchCode;
    @Size(max = 50)
    @Column(name = "AMENDMENT_COST_COVERED")
    private String amendmentCostCovered;
//    @Size(max = 50)
    @Column(name = "PREPARED_BY")
    private Integer preparedBy;
    @Size(max = 100)
    @Column(name = "REMARK")
    private String remark;
    @Size(max = 20)
    @Column(name = "ACCOUNT_CODE")
    private String accountCode;
    @Size(max = 20)
    @Column(name = "END_USER")
    private String endUser;

//    @JoinColumn(name = "PERMIT_NO", referencedColumnName = "ID")
//    @ManyToOne
//    private PrmsForeignExchange permitNo;
    @Size(max = 100)
    @Column(name = "SUPPLIER_")
    private String supplier;
    @Column(name = "PERMIT_AMOUNT")
    private Double permitAmount;

    @Column(name = "SOURCE_OF_FUND")
    private String sourchOfFund;
    @Column(name = "DESCRIPTION_OF_GOODS")
    private String descriptionOfGoods;
    @Column(name = "DELIVERY_TERM_AFTER_LC_AMOUNT")
    private Double deliveryTermAfterLcAmount;
    @Column(name = "LC_PAYMENT_AFTER_ACCEPTANCE")
    private Double lcPaymentAfterAcceptance;
    @Column(name = "REMAINING_LC_PAYMENT_AMOUNT")
    private Double remainingLcPaymentAmount;
    @Column(name = "STATUS")
    private Integer status;
    @Column(name = "NMBER_OF_DAYS")
    private Integer numberOfDays;

    @JoinColumn(name = "COUNTRY_ID", referencedColumnName = "ID")
    @ManyToOne
    private ComLuCountry countryId;
    @JoinColumn(name = "CURRENCY_ID", referencedColumnName = "CURRENCY_ID")
    @ManyToOne
    private FmsLuCurrency currencyId;
    @JoinColumn(name = "CONTRACT_ID", referencedColumnName = "CONTRACT_ID")
    @ManyToOne
    private PrmsContract contractId;
    @JoinColumn(name = "PORT_OF_DISCHARGE", referencedColumnName = "PORT_ID")
    @ManyToOne
    private PrmsPortEntry portOfDischarge;
    @JoinColumn(name = "PORT_OF_LODING", referencedColumnName = "PORT_ID")
    @ManyToOne
    private PrmsPortEntry portOfLoding;
    @JoinColumn(name = "SERVICE_PRO_ID", referencedColumnName = "SERVICE_PRO_ID")
    @ManyToOne
    private PrmsServiceProvider serviceProId;
    @Transient
    private String columnName;
    @Transient
    private String columnValue;

    public PrmsLcRigistration() {
    }

    public PrmsLcRigistration(BigDecimal lcId) {
        this.lcId = lcId;
    }

    public String getLcNo() {
        return lcNo;
    }

    public void setLcNo(String lcNo) {
        this.lcNo = lcNo;
    }

    public PrmsLuDmArchive getDocumentId() {
        return documentId;
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

    public void setDocumentId(PrmsLuDmArchive documentId) {
        this.documentId = documentId;
    }

    public String getLcType() {
        return lcType;
    }

    public void setLcType(String lcType) {
        this.lcType = lcType;
    }

    public Double getLcAmount() {
        return lcAmount;
    }

    public void setLcAmount(Double lcAmount) {
        this.lcAmount = lcAmount;
    }

    public BigInteger getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(BigInteger exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    public Date getRigistrationDate() {
        return rigistrationDate;
    }

    public void setRigistrationDate(Date rigistrationDate) {
        this.rigistrationDate = rigistrationDate;
    }

    public String getLcAmendRef() {
        return lcAmendRef;
    }

    public void setLcAmendRef(String lcAmendRef) {
        this.lcAmendRef = lcAmendRef;
    }

    public String getDescribition() {
        return describition;
    }

    public void setDescribition(String describition) {
        this.describition = describition;
    }

    public Date getShipmentDate() {
        return shipmentDate;
    }

    public void setShipmentDate(Date shipmentDate) {
        this.shipmentDate = shipmentDate;
    }

    public String getNoOfLcExtention() {
        return noOfLcExtention;
    }

    public void setNoOfLcExtention(String noOfLcExtention) {
        this.noOfLcExtention = noOfLcExtention;
    }

    public String getLcExtCodition() {
        return lcExtCodition;
    }

    public void setLcExtCodition(String lcExtCodition) {
        this.lcExtCodition = lcExtCodition;
    }

    public Date getLcOpeningDate() {
        return lcOpeningDate;
    }

    public void setLcOpeningDate(Date lcOpeningDate) {
        this.lcOpeningDate = lcOpeningDate;
    }

    public Date getLcExpireDate() {
        return lcExpireDate;
    }

    public void setLcExpireDate(Date lcExpireDate) {
        this.lcExpireDate = lcExpireDate;
    }

    public BigDecimal getLcId() {
        return lcId;
    }

    public void setLcId(BigDecimal lcId) {
        this.lcId = lcId;
    }

    public String getSwitchCode() {
        return switchCode;
    }

    public void setSwitchCode(String switchCode) {
        this.switchCode = switchCode;
    }

    public String getAmendmentCostCovered() {
        return amendmentCostCovered;
    }

    public void setAmendmentCostCovered(String amendmentCostCovered) {
        this.amendmentCostCovered = amendmentCostCovered;
    }

    public Integer getPreparedBy() {
        return preparedBy;
    }

    public void setPreparedBy(Integer preparedBy) {
        this.preparedBy = preparedBy;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getAccountCode() {
        return accountCode;
    }

    public void setAccountCode(String accountCode) {
        this.accountCode = accountCode;
    }

    public String getEndUser() {
        return endUser;
    }

    public void setEndUser(String endUser) {
        this.endUser = endUser;
    }

//    public PrmsForeignExchange getPermitNo() {
//        return permitNo;
//    }
//
//    public void setPermitNo(PrmsForeignExchange permitNo) {
//        this.permitNo = permitNo;
//    }
    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public Double getPermitAmount() {
        return permitAmount;
    }

    public void setPermitAmount(Double permitAmount) {
        this.permitAmount = permitAmount;
    }

    public ComLuCountry getCountryId() {
        return countryId;
    }

    public void setCountryId(ComLuCountry countryId) {
        this.countryId = countryId;
    }

    public FmsLuCurrency getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(FmsLuCurrency currencyId) {
        this.currencyId = currencyId;
    }

    public PrmsContract getContractId() {
//        if (contractId==null) {
//            contractId=new PrmsContract();
//        }
        return contractId;
    }

    public void setContractId(PrmsContract contractId) {
        this.contractId = contractId;
    }

    public PrmsPortEntry getPortOfDischarge() {
        return portOfDischarge;
    }

    public void setPortOfDischarge(PrmsPortEntry portOfDischarge) {
        this.portOfDischarge = portOfDischarge;
    }

    public PrmsPortEntry getPortOfLoding() {
        return portOfLoding;
    }

    public void setPortOfLoding(PrmsPortEntry portOfLoding) {
        this.portOfLoding = portOfLoding;
    }

    public PrmsServiceProvider getServiceProId() {
        return serviceProId;
    }

    public void setServiceProId(PrmsServiceProvider serviceProId) {
        this.serviceProId = serviceProId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (lcId != null ? lcId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PrmsLcRigistration)) {
            return false;
        }
        PrmsLcRigistration other = (PrmsLcRigistration) object;
        if ((this.lcId == null && other.lcId != null) || (this.lcId != null && !this.lcId.equals(other.lcId))) {
            return false;
        }
        return true;
    }

    public String getSourchOfFund() {
        return sourchOfFund;
    }

    public void setSourchOfFund(String sourchOfFund) {
        this.sourchOfFund = sourchOfFund;
    }

    public String getDescriptionOfGoods() {
        return descriptionOfGoods;
    }

    public void setDescriptionOfGoods(String descriptionOfGoods) {
        this.descriptionOfGoods = descriptionOfGoods;
    }

    public Double getDeliveryTermAfterLcAmount() {
        return deliveryTermAfterLcAmount;
    }

    public void setDeliveryTermAfterLcAmount(Double deliveryTermAfterLcAmount) {
        this.deliveryTermAfterLcAmount = deliveryTermAfterLcAmount;
    }

    public Double getLcPaymentAfterAcceptance() {
        return lcPaymentAfterAcceptance;
    }

    public void setLcPaymentAfterAcceptance(Double lcPaymentAfterAcceptance) {
        this.lcPaymentAfterAcceptance = lcPaymentAfterAcceptance;
    }

    public Double getRemainingLcPaymentAmount() {
        return remainingLcPaymentAmount;
    }

    public void setRemainingLcPaymentAmount(Double remainingLcPaymentAmount) {
        this.remainingLcPaymentAmount = remainingLcPaymentAmount;
    }

    @Override
    public String toString() {
        return lcNo;
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

    @XmlTransient
    public List<PrmsBankClearance> getPrmsBankClearanceCollection() {
        return prmsBankClearanceCollection;
    }

    public void setPrmsBankClearanceCollection(List<PrmsBankClearance> prmsBankClearanceCollection) {
        this.prmsBankClearanceCollection = prmsBankClearanceCollection;
    }

    @XmlTransient
    public List<PrmsLcRigistrationAmend> getPrmsLcRigistrationAmendCollection() {
        return prmsLcRigistrationAmendCollection;
    }

    public void setPrmsLcRigistrationAmendCollection(List<PrmsLcRigistrationAmend> prmsLcRigistrationAmendCollection) {
        this.prmsLcRigistrationAmendCollection = prmsLcRigistrationAmendCollection;
    }

    @XmlTransient
    public List<PrmsClaimRecordingForm> getPrmsClaimRecordingFormCollection() {
        return prmsClaimRecordingFormCollection;
    }

    public void setPrmsClaimRecordingFormCollection(List<PrmsClaimRecordingForm> prmsClaimRecordingFormCollection) {
        this.prmsClaimRecordingFormCollection = prmsClaimRecordingFormCollection;
    }

    /**
     * @return the processedOn
     */
    public Date getProcessedOn() {
        return processedOn;
    }

    /**
     * @param processedOn the processedOn to set
     */
    public void setProcessedOn(Date processedOn) {
        this.processedOn = processedOn;
    }

    /**
     * @return the prmsFileUploadList
     */
    @XmlTransient
    public List<PrmsFileUpload> getPrmsFileUploadList() {
        if (prmsFileUploadList == null) {
            prmsFileUploadList = new ArrayList<>();
        }
        return prmsFileUploadList;
    }

    /**
     * @param prmsFileUploadList the prmsFileUploadList to set
     */
    public void setPrmsFileUploadList(List<PrmsFileUpload> prmsFileUploadList) {
        this.prmsFileUploadList = prmsFileUploadList;
    }

    public void add(PrmsFileUpload lcregLocal) {
        if (lcregLocal.getLcId() != this) {
            this.getPrmsFileUploadList().add(lcregLocal);
            lcregLocal.setLcId(this);
            System.out.println("===add---" + lcregLocal.getLcId());
        }
    }

    /**
     * @return the foreignId
     */
    public PrmsForeignExchange getForeignId() {
        return foreignId;
    }

    /**
     * @param foreignId the foreignId to set
     */
    public void setForeignId(PrmsForeignExchange foreignId) {
        this.foreignId = foreignId;
    }

    /**
     * @return the partialShipment
     */
    public String getPartialShipment() {
        return partialShipment;
    }

    /**
     * @param partialShipment the partialShipment to set
     */
    public void setPartialShipment(String partialShipment) {
        this.partialShipment = partialShipment;
    }

    /**
     * @return the numberOfDays
     */
    public Integer getNumberOfDays() {
        return numberOfDays;
    }

    /**
     * @param numberOfDays the numberOfDays to set
     */
    public void setNumberOfDays(Integer numberOfDays) {
        this.numberOfDays = numberOfDays;
    }

    /**
     * @return the prmsWorkFlowProccedList
     */
}
