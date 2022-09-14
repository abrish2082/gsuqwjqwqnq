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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author user
 */
@Entity
@Table(name = "PRMS_LC_RIGISTRATION_AMEND")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PrmsLcRigistrationAmend.findAll", query = "SELECT p FROM PrmsLcRigistrationAmend p"),
    @NamedQuery(name = "PrmsLcRigistrationAmend.findByAmendLike", query = "SELECT p FROM PrmsLcRigistrationAmend p WHERE p.lcAmendNo LIKE :lcAmendNo"),
    @NamedQuery(name = "PrmsLcRigistrationAmend.findByLcNo", query = "SELECT p FROM PrmsLcRigistrationAmend p WHERE p.lcNo = :lcNo"),
    @NamedQuery(name = "PrmsLcRigistrationAmend.findByReqForApproval", query = "SELECT p FROM PrmsLcRigistrationAmend  p WHERE p.status=0"),
    @NamedQuery(name = "PrmsLcRigistrationAmend.findByReqForApprovalss", query = "SELECT p FROM PrmsLcRigistrationAmend  p WHERE p.status= :status"),
    @NamedQuery(name = "PrmsLcRigistrationAmend.findByLCAmendNos", query = "SELECT p FROM PrmsLcRigistrationAmend p WHERE p.amendedNo = :amendedNo"),
    @NamedQuery(name = "PrmsLcRigistrationAmend.findByLcType", query = "SELECT p FROM PrmsLcRigistrationAmend p WHERE p.lcType = :lcType"),
    @NamedQuery(name = "PrmsLcRigistrationAmend.findByLcAmount", query = "SELECT p FROM PrmsLcRigistrationAmend p WHERE p.lcAmount = :lcAmount"),
    @NamedQuery(name = "PrmsLcRigistrationAmend.findByExchangeRate", query = "SELECT p FROM PrmsLcRigistrationAmend p WHERE p.exchangeRate = :exchangeRate"),
//    @NamedQuery(name = "PrmsLcRigistrationAmend.findByCurrency", query = "SELECT p FROM PrmsLcRigistrationAmend p WHERE p.currency = :currency"),
    @NamedQuery(name = "PrmsLcRigistrationAmend.findByRigistrationDate", query = "SELECT p FROM PrmsLcRigistrationAmend p WHERE p.rigistrationDate = :rigistrationDate"),
    @NamedQuery(name = "PrmsLcRigistrationAmend.findByLcAmendRef", query = "SELECT p FROM PrmsLcRigistrationAmend p WHERE p.lcNo = :lcNo"),
    @NamedQuery(name = "PrmsLcRigistrationAmend.findByDescribition", query = "SELECT p FROM PrmsLcRigistrationAmend p WHERE p.describition = :describition"),
    @NamedQuery(name = "PrmsLcRigistrationAmend.findByShipmentDate", query = "SELECT p FROM PrmsLcRigistrationAmend p WHERE p.shipmentDate = :shipmentDate"),
    @NamedQuery(name = "PrmsLcRigistrationAmend.findByNoOfLcExtention", query = "SELECT p FROM PrmsLcRigistrationAmend p WHERE p.noOfLcExtention = :noOfLcExtention"),
    @NamedQuery(name = "PrmsLcRigistrationAmend.findByPortOfLoding", query = "SELECT p FROM PrmsLcRigistrationAmend p WHERE p.portOfLoding = :portOfLoding"),
    @NamedQuery(name = "PrmsLcRigistrationAmend.findByPortOfDischarge", query = "SELECT p FROM PrmsLcRigistrationAmend p WHERE p.portOfDischarge = :portOfDischarge"),
    @NamedQuery(name = "PrmsLcRigistrationAmend.findByLcExtCodition", query = "SELECT p FROM PrmsLcRigistrationAmend p WHERE p.lcExtCodition = :lcExtCodition"),
    @NamedQuery(name = "PrmsLcRigistrationAmend.findByLcOpeningDate", query = "SELECT p FROM PrmsLcRigistrationAmend p WHERE p.lcOpeningDate = :lcOpeningDate"),
    @NamedQuery(name = "PrmsLcRigistrationAmend.findByLcExpireDate", query = "SELECT p FROM PrmsLcRigistrationAmend p WHERE p.lcExpireDate = :lcExpireDate"),
    @NamedQuery(name = "PrmsLcRigistrationAmend.findByLcId", query = "SELECT p FROM PrmsLcRigistrationAmend p WHERE p.lcId = :lcId"),
    @NamedQuery(name = "PrmsLcRigistrationAmend.findByLcIds", query = "SELECT p FROM PrmsLcRigistrationAmend p WHERE p.lcId.lcId = :lcId"),
    @NamedQuery(name = "PrmsLcRigistrationAmend.findBySwitchCode", query = "SELECT p FROM PrmsLcRigistrationAmend p WHERE p.switchCode = :switchCode"),
    @NamedQuery(name = "PrmsLcRigistrationAmend.findByAmendmentCostCovered", query = "SELECT p FROM PrmsLcRigistrationAmend p WHERE p.amendmentCostCovered = :amendmentCostCovered"),
    @NamedQuery(name = "PrmsLcRigistrationAmend.findByPreparedBy", query = "SELECT p FROM PrmsLcRigistrationAmend p WHERE p.preparedBy = :preparedBy"),
    @NamedQuery(name = "PrmsLcRigistrationAmend.findByRemark", query = "SELECT p FROM PrmsLcRigistrationAmend p WHERE p.remark = :remark"),
    @NamedQuery(name = "PrmsLcRigistrationAmend.findByAccountCode", query = "SELECT p FROM PrmsLcRigistrationAmend p WHERE p.accountCode = :accountCode"),
    @NamedQuery(name = "PrmsLcRigistrationAmend.findByServiceProId", query = "SELECT p FROM PrmsLcRigistrationAmend p WHERE p.serviceProId = :serviceProId"),
    @NamedQuery(name = "PrmsLcRigistrationAmend.findByContractId", query = "SELECT p FROM PrmsLcRigistrationAmend p WHERE p.contractId = :contractId"),
    @NamedQuery(name = "PrmsLcRigistrationAmend.findByEndUser", query = "SELECT p FROM PrmsLcRigistrationAmend p WHERE p.endUser = :endUser"),
    @NamedQuery(name = "PrmsLcRigistrationAmend.findBySupplier", query = "SELECT p FROM PrmsLcRigistrationAmend p WHERE p.supplier = :supplier"),
    @NamedQuery(name = "PrmsLcRigistrationAmend.findByGeneratedAmdNm", query = "SELECT p FROM PrmsLcRigistrationAmend p WHERE p.id = (SELECT Max(p.id)  from PrmsLcRigistrationAmend p)"),
    @NamedQuery(name = "PrmsLcRigistrationAmend.findByLcAmendNos", query = "SELECT p FROM PrmsLcRigistrationAmend p WHERE p.amendedNo LIKE :amendedNo"),
    @NamedQuery(name = "PrmsLcRigistrationAmend.findByAmendedNo", query = "SELECT p FROM PrmsLcRigistrationAmend p WHERE p.amendedNo = :amendedNo"),
    @NamedQuery(name = "PrmsLcRigistrationAmend.findById", query = "SELECT p FROM PrmsLcRigistrationAmend p WHERE p.id = :id")})
public class PrmsLcRigistrationAmend implements Serializable {

    @Column(name = "PROCESSED_ON")
    @Temporal(TemporalType.TIMESTAMP)
    private Date processedOn;

    @JoinColumn(name = "DOCUMENT_ID", referencedColumnName = "DOCUMENT_ID")
    @ManyToOne
    private PrmsLuDmArchive documentId;

    @Column(name = "REMAINING_LC_PAYMENT_AMOUNT")
    private Double remainingLcPaymentAmount;

    @Column(name = "LC_AMOUNT")
    private Double lcAmount;
    @Transient
    private String columnName;
    @Transient
    private String columnValue;
    @OneToMany(mappedBy = "lcAmendId")
    private List<PrmsFileUpload> prmsFileUploadList;

    @OneToMany(mappedBy = "lcAmendmentId", cascade = CascadeType.ALL)
    private List<WfPrmsProcessed> wfPrmsProcessedLists;
    @JoinColumn(name = "PORT_OF_DISCHARGE", referencedColumnName = "PORT_ID")
    @ManyToOne
    private PrmsPortEntry portOfDischarge;
    @JoinColumn(name = "PORT_OF_LODING", referencedColumnName = "PORT_ID")
    @ManyToOne
    private PrmsPortEntry portOfLoding;
    @JoinColumn(name = "COUNTRY_ID", referencedColumnName = "ID")
    @ManyToOne
    private ComLuCountry countryId;

    @JoinColumn(name = "CURRENCY_ID", referencedColumnName = "CURRENCY_ID")
    @ManyToOne
    private FmsLuCurrency currencyId;

    private static final long serialVersionUID = 1L;

    @Column(name = "LC_NO")
    private String lcNo;

    @Column(name = "LC_TYPE")
    private String lcType;

    @Column(name = "STATUS")
    private Integer status;

//    @Column(name = "LC_AMOUNT")
//    private String lcAmount;
    @Column(name = "EXCHANGE_RATE")
    private BigInteger exchangeRate;

    @Column(name = "RIGISTRATION_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date rigistrationDate;

    @Column(name = "LC_AMEND_REF")
    private String lcAmendRef;

    @Column(name = "DESCRIBITION")
    private String describition;
    @Column(name = "SHIPMENT_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date shipmentDate;

    @Column(name = "NO_OF_LC_EXTENTION")
    private String noOfLcExtention;

    @Column(name = "LC_EXT_CODITION")
    private String lcExtCodition;
    @Column(name = "LC_OPENING_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lcOpeningDate;
    @Column(name = "LC_EXPIRE_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lcExpireDate;

    @Column(name = "SWITCH_CODE")
    private String switchCode;

    @Column(name = "AMENDMENT_COST_COVERED")
    private String amendmentCostCovered;

    @Column(name = "PREPARED_BY")
    private Integer preparedBy;
    @Size(max = 100)
    @Column(name = "REMARK")
    private String remark;

    @Column(name = "ACCOUNT_CODE")
    private String accountCode;

    @Column(name = "END_USER")
    private String endUser;

//    @JoinColumn(name = "PERMIT_NO", referencedColumnName = "ID")
//    @ManyToOne
//    private PrmsForeignExchange permitNo;
    @Size(max = 100)
    @Column(name = "SUPPLIER_")
    private String supplier;

    @Column(name = "LC_AMEND_NO")
    private String lcAmendNo;

    @Column(name = "AMENDED_NO")
    private String amendedNo;

    @Column(name = "SOURCE_OF_FUND")
    private String sourchOfFund;
    @Column(name = "DESCRIPTION_OF_GOODS")
    private String descriptionOfGoods;
    @Column(name = "DELIVERY_TERM_AFTER_LC_AMOUNT")
    private Double deliveryTermAfterLcAmount;
    @Column(name = "LC_PAYMENT_AFTER_ACCEPTANCE")
    private Double lcPaymentAfterAcceptance;

    @JoinColumn(name = "CONTRACT_ID", referencedColumnName = "CONTRACT_ID")
    @ManyToOne
    private PrmsContract contractId;

    @JoinColumn(name = "LC_ID", referencedColumnName = "LC_ID")
    @ManyToOne
    private PrmsLcRigistration lcId;

    @JoinColumn(name = "SERVICE_PRO_ID", referencedColumnName = "SERVICE_PRO_ID")
    @ManyToOne
    private PrmsServiceProvider serviceProId;
    @JoinColumn(name = "FOREIGN_ID", referencedColumnName = "ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private PrmsForeignExchange foreignId;

    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PRMS_LC_REG_AMEND_SEQ")
    @SequenceGenerator(name = "PRMS_LC_REG_AMEND_SEQ", sequenceName = "PRMS_LC_REG_AMEND_SEQ", allocationSize = 1)

    @Column(name = "ID", precision = 0, scale = -127)
    private Integer id;
    @Column(name = "PERMIT_AMOUNT")
    private Double permitAmount;
    @Column(name = "NMBER_OF_DAYS")
    private Integer numberOfDays;
    @Column(name = "PARTIAL_SHIPMENT")
    private String partialShipment;

//    @Column(name = "COUNTRY")
//    private String country;
    public PrmsLcRigistrationAmend() {
    }

    public PrmsLcRigistrationAmend(Integer id) {
        this.id = id;
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

    public PrmsServiceProvider getServiceProId() {
        return serviceProId;
    }

    public void setServiceProId(PrmsServiceProvider serviceProId) {
        this.serviceProId = serviceProId;
    }

    public PrmsLuDmArchive getDocumentId() {
        return documentId;
    }

    public void setDocumentId(PrmsLuDmArchive documentId) {
        this.documentId = documentId;
    }
//    public List<PrmsLcRegDetailAmendment> getPrmsLcRegDetailAmendmentList() {
//        return prmsLcRegDetailAmendmentList;
//    }
//
//    public void setPrmsLcRegDetailAmendmentList(List<PrmsLcRegDetailAmendment> prmsLcRegDetailAmendmentList) {
//        this.prmsLcRegDetailAmendmentList = prmsLcRegDetailAmendmentList;
//    }

    public String getLcNo() {
        return lcNo;
    }

    public void setLcNo(String lcNo) {
        this.lcNo = lcNo;
    }

    public String getLcType() {
        return lcType;
    }

    public void setLcType(String lcType) {
        this.lcType = lcType;
    }

//    public Integer getLcAmount() {
//        return lcAmount;
//    }
//
//    public void setLcAmount(Integer lcAmount) {
//        this.lcAmount = lcAmount;
//    }
    public BigInteger getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(BigInteger exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    public FmsLuCurrency getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(FmsLuCurrency currencyId) {
        this.currencyId = currencyId;
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

    public String getLcAmendNo() {
        return lcAmendNo;
    }

    public void setLcAmendNo(String lcAmendNo) {
        this.lcAmendNo = lcAmendNo;
    }

    public String getAmendedNo() {
        return amendedNo;
    }

    public void setAmendedNo(String amendedNo) {
        this.amendedNo = amendedNo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getPermitAmount() {
        return permitAmount;
    }

    public void setPermitAmount(Double permitAmount) {
        this.permitAmount = permitAmount;
    }

//    private List<PrmsLcRegDetailAmendment> prmsLcRegDetailAmendmentList = new ArrayList<>();
//\
//    @XmlTransient
//    public List<PrmsLcRegDetailAmendment> getprmsLcRegDetailAmendmentList() {
//        return prmsLcRegDetailAmendmentList;
//    }
//
//    public void setprmsLcRegDetailAmendmentList(List<PrmsLcRegDetailAmendment> prmsLcRegDetailAmendmentList) {
//        this.prmsLcRegDetailAmendmentList = prmsLcRegDetailAmendmentList;
//    }
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
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PrmsLcRigistrationAmend)) {
            return false;
        }
        PrmsLcRigistrationAmend other = (PrmsLcRigistrationAmend) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return amendedNo;
    }

    public Double getLcAmount() {
        return lcAmount;
    }

    public void setLcAmount(Double lcAmount) {
        this.lcAmount = lcAmount;
    }

    public ComLuCountry getCountryId() {
        return countryId;
    }

    public void setCountryId(ComLuCountry countryId) {
        this.countryId = countryId;
    }

//    public BigInteger getLcAmount() {
//        return lcAmount;
//    }
//
//    public void setLcAmount(BigInteger lcAmount) {
//        this.lcAmount = lcAmount;
//    }
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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
     * @return the prmsWorkFlowProccedList
     */
}
