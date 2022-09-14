/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.prms.entity;

import et.gov.eep.commonApplications.entity.PrmsLuDmArchive;
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
@Table(name = "PRMS_INSURANCE_REQUISITION")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PrmsInsuranceRequisition.findAll", query = "SELECT p FROM PrmsInsuranceRequisition p"),
    @NamedQuery(name = "PrmsInsuranceRequisition.findByInsuranceRegId", query = "SELECT p FROM PrmsInsuranceRequisition p WHERE p.insuranceRegId = :insuranceRegId"),
    @NamedQuery(name = "PrmsInsuranceRequisition.findByInsuranceNo", query = "SELECT p FROM PrmsInsuranceRequisition p WHERE p.insuranceNo = :insuranceNo"),
    @NamedQuery(name = "PrmsInsuranceRequisition.findForRowSelect", query = "SELECT p FROM PrmsInsuranceRequisition p WHERE p.insuranceNo = :insuranceNo"),
    @NamedQuery(name = "PrmsInsuranceRequisition.findByInsuranceNos", query = "SELECT p FROM PrmsInsuranceRequisition p WHERE p.insuranceNo LIKE :insuranceNo"),
    @NamedQuery(name = "PrmsInsuranceRequisition.searchByInsuranceNo", query = "SELECT p FROM PrmsInsuranceRequisition p WHERE p.insuranceNo LIKE :insuranceNo and p.preparedBy=:preparedBy"),
    @NamedQuery(name = "PrmsInsuranceRequisition.InsertNextInsuranceNo", query = "SELECT p FROM PrmsInsuranceRequisition p WHERE p.insuranceNo = (SELECT MAX(p.insuranceNo) FROM PrmsInsuranceRequisition p)"),
    @NamedQuery(name = "PrmsInsuranceRequisition.findByInsuranceForApproval", query = "SELECT p FROM PrmsInsuranceRequisition p WHERE p.status=0"),
    @NamedQuery(name = "PrmsInsuranceRequisition.findByNameOfInsured", query = "SELECT p FROM PrmsInsuranceRequisition p WHERE p.nameOfInsured = :nameOfInsured"),
    @NamedQuery(name = "PrmsInsuranceRequisition.findByAmountOfInsuranceFc", query = "SELECT p FROM PrmsInsuranceRequisition p WHERE p.amountOfInsuranceFc = :amountOfInsuranceFc"),
    @NamedQuery(name = "PrmsInsuranceRequisition.findByExchangeRate", query = "SELECT p FROM PrmsInsuranceRequisition p WHERE p.exchangeRate = :exchangeRate"),
    @NamedQuery(name = "PrmsInsuranceRequisition.findByLocalCurrencyInword", query = "SELECT p FROM PrmsInsuranceRequisition p WHERE p.localCurrencyInword = :localCurrencyInword"),
    @NamedQuery(name = "PrmsInsuranceRequisition.findByQuantity", query = "SELECT p FROM PrmsInsuranceRequisition p WHERE p.quantity = :quantity"),
    @NamedQuery(name = "PrmsInsuranceRequisition.findByDescriptionOfGood", query = "SELECT p FROM PrmsInsuranceRequisition p WHERE p.descriptionOfGood = :descriptionOfGood"),
    @NamedQuery(name = "PrmsInsuranceRequisition.findByMarksAndNos", query = "SELECT p FROM PrmsInsuranceRequisition p WHERE p.marksAndNos = :marksAndNos"),
    @NamedQuery(name = "PrmsInsuranceRequisition.findByDateIssued", query = "SELECT p FROM PrmsInsuranceRequisition p WHERE p.dateIssued = :dateIssued"),
    @NamedQuery(name = "PrmsInsuranceRequisition.findByReferenceNo", query = "SELECT p FROM PrmsInsuranceRequisition p WHERE p.referenceNo = :referenceNo"),
    @NamedQuery(name = "PrmsInsuranceRequisition.findByCoverRequired", query = "SELECT p FROM PrmsInsuranceRequisition p WHERE p.coverRequired = :coverRequired"),
    @NamedQuery(name = "PrmsInsuranceRequisition.findByPolicyNo", query = "SELECT p FROM PrmsInsuranceRequisition p WHERE p.policyNo = :policyNo"),
//    @NamedQuery(name = "PrmsInsuranceRequisition.findByPerSS", query = "SELECT p FROM PrmsInsuranceRequisition p WHERE p.perSS = :perSS"),
    @NamedQuery(name = "PrmsInsuranceRequisition.findByServiceProName", query = "SELECT p FROM PrmsInsuranceRequisition p WHERE p.serviceProName=:serviceProName"),
    @NamedQuery(name = "PrmsInsuranceRequisition.findByStartFrom", query = "SELECT p FROM PrmsInsuranceRequisition p WHERE p.startFrom = :startFrom"),
    @NamedQuery(name = "PrmsInsuranceRequisition.findByArriveTo", query = "SELECT p FROM PrmsInsuranceRequisition p WHERE p.arriveTo = :arriveTo"),
    @NamedQuery(name = "PrmsInsuranceRequisition.findByRemark", query = "SELECT p FROM PrmsInsuranceRequisition p WHERE p.remark = :remark"),
    @NamedQuery(name = "PrmsInsuranceRequisition.findByPreparerPosition", query = "SELECT p FROM PrmsInsuranceRequisition p WHERE p.preparerPosition = :preparerPosition"),
    @NamedQuery(name = "PrmsInsuranceRequisition.findByPackaging", query = "SELECT p FROM PrmsInsuranceRequisition p WHERE p.packaging = :packaging"),
    @NamedQuery(name = "PrmsInsuranceRequisition.findByPreparedBy", query = "SELECT p FROM PrmsInsuranceRequisition p WHERE p.preparedBy = :preparedBy"),
    @NamedQuery(name = "PrmsInsuranceRequisition.findByTotalFc", query = "SELECT p FROM PrmsInsuranceRequisition p WHERE p.totalFc = :totalFc"),
    @NamedQuery(name = "PrmsInsuranceRequisition.findByLcAmount", query = "SELECT p FROM PrmsInsuranceRequisition p WHERE p.lcAmount = :lcAmount"),
    @NamedQuery(name = "PrmsInsuranceRequisition.findByFileRefNumber", query = "SELECT p FROM PrmsInsuranceRequisition p WHERE p.fileRefNumber=:fileRefNumber"),
    @NamedQuery(name = "PrmsInsuranceRequisition.findByPremiumDue", query = "SELECT p FROM PrmsInsuranceRequisition p WHERE p.premiumDue=:premiumDue"),
    @NamedQuery(name = "PrmsInsuranceRequisition.findByStatus", query = "SELECT p FROM PrmsInsuranceRequisition p WHERE p.status=:status"),
    @NamedQuery(name = "PrmsInsuranceRequisition.findByProcessedOn", query = "SELECT p FROM PrmsInsuranceRequisition p WHERE p.processedOn=:processedOn")})
public class PrmsInsuranceRequisition implements Serializable {

    @OneToMany(mappedBy = "insuranceRegId", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<WfPrmsProcessed> wfPrmsProcessedLists;

    private static final long serialVersionUID = 1L;

    @OneToMany(mappedBy = "insuranceRegId", fetch = FetchType.LAZY)
    private List<PrmsInsuranceNotToBank> prmsInsuranceNotToBankList;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PRMS_INSURANCE_REQUISITION_SEQ")
    @SequenceGenerator(name = "PRMS_INSURANCE_REQUISITION_SEQ", sequenceName = "PRMS_INSURANCE_REQUISITION_SEQ", allocationSize = 1)
    @Column(name = "INSURANCE_REG_ID", nullable = false, precision = 0, scale = -127)
    private BigDecimal insuranceRegId;
    @Size(max = 35)
    @Column(name = "INSURANCE_NO", length = 35)
    private String insuranceNo;
    @Size(max = 50)
    @Column(name = "NAME_OF_INSURED", length = 50)
    private String nameOfInsured;
    @Column(name = "AMOUNT_OF_INSURANCE_FC")
    private BigDecimal amountOfInsuranceFc;
    @Column(name = "EXCHANGE_RATE")
    private BigDecimal exchangeRate;
    @Column(name = "TOTAL_FC")
    private BigDecimal totalFc;
    @Column(name = "LC_AMOUNT")
    private BigDecimal lcAmount;
    @Size(max = 200)
    @Column(name = "LOCAL_CURRENCY_INWORD", length = 200)
    private String localCurrencyInword;
    @Column(name = "QUANTITY")
    private BigInteger quantity;
    @Size(max = 200)
    @Column(name = "DESCRIPTION_OF_GOOD", length = 200)
    private String descriptionOfGood;
    @Size(max = 200)
    @Column(name = "MARKS_AND_NOS", length = 200)
    private String marksAndNos;
    @Column(name = "DATE_ISSUED")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateIssued;
    @Size(max = 35)
    @Column(name = "REFERENCE_NO", length = 35)
    private String referenceNo;
    @Size(max = 50)
    @Column(name = "COVER_REQUIRED", length = 50)
    private String coverRequired;
    @Size(max = 35)
    @Column(name = "POLICY_NO", length = 35)
    private String policyNo;
//    @Size(max = 50)
//    @Column(name = "PER_S_S", length = 50)
//    private String perSS;
    @Size(max = 100)
    @Column(name = "START_FROM", length = 100)
    private String startFrom;
    @Size(max = 100)
    @Column(name = "ARRIVE_TO", length = 100)
    private String arriveTo;
    @Size(max = 200)
    @Column(name = "REMARK", length = 200)
    private String remark;
    @JoinColumn(name = "FILE_REF_NUMBER", referencedColumnName = "DOCUMENT_ID")
    @ManyToOne(cascade = CascadeType.PERSIST)
    private PrmsLuDmArchive fileRefNumber;
    @Column(name = "PREMIUM_DUE")
    private Float premiumDue;
    @Size(max = 50)
    @Column(name = "PREPARER_POSITION", length = 50)
    private String preparerPosition;
    @Size(max = 50)
    @Column(name = "PACKAGING", length = 50)
    private String packaging;
    @Transient
    private String columnName;
    @Transient
    private String columnValue;
    @Column(name = "PREPARED_BY")
    private Integer preparedBy;
    @Column(name = "STATUS")
    private Integer status;
    @Size(max = 50)
    @Column(name = "PROCESSED_ON")
    private String processedOn;
    @JoinColumn(name = "CURRENCY_NAME", referencedColumnName = "CURRENCY_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private FmsLuCurrency currencyName;
    @JoinColumn(name = "HR_DEPT_ID", referencedColumnName = "DEP_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private HrDepartments hrDeptId;
    @JoinColumn(name = "CONTRACT_NO", referencedColumnName = "CONTRACT_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private PrmsContract contractNo;
    @JoinColumn(name = "SERVICE_PRO_ID", referencedColumnName = "SERVICE_PRO_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private PrmsServiceProvider serviceProId;
    @JoinColumn(name = "SERVICE_DT_ID", referencedColumnName = "SERVICE_DT_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private PrmsServiceProviderDetail serviceDtId;
    @JoinColumn(name = "SERVICE_PRO_NAME", referencedColumnName = "SERVICE_PRO_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private PrmsServiceProvider serviceProName;

    public PrmsInsuranceRequisition() {
    }

    public PrmsInsuranceRequisition(BigDecimal insuranceRegId) {
        this.insuranceRegId = insuranceRegId;
    }

    public BigDecimal getInsuranceRegId() {
        return insuranceRegId;
    }

    public void setInsuranceRegId(BigDecimal insuranceRegId) {
        this.insuranceRegId = insuranceRegId;
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

    public String getInsuranceNo() {
        return insuranceNo;
    }

    public void setInsuranceNo(String insuranceNo) {
        this.insuranceNo = insuranceNo;
    }

    public String getNameOfInsured() {
        return nameOfInsured;
    }

    public void setNameOfInsured(String nameOfInsured) {
        this.nameOfInsured = nameOfInsured;
    }

    public BigDecimal getAmountOfInsuranceFc() {
        return amountOfInsuranceFc;
    }

    public void setAmountOfInsuranceFc(BigDecimal amountOfInsuranceFc) {
        this.amountOfInsuranceFc = amountOfInsuranceFc;
    }

    public BigDecimal getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(BigDecimal exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    public String getLocalCurrencyInword() {
        return localCurrencyInword;
    }

    public void setLocalCurrencyInword(String localCurrencyInword) {
        this.localCurrencyInword = localCurrencyInword;
    }

    public BigInteger getQuantity() {
        return quantity;
    }

    public void setQuantity(BigInteger quantity) {
        this.quantity = quantity;
    }

    public String getDescriptionOfGood() {
        return descriptionOfGood;
    }

    public void setDescriptionOfGood(String descriptionOfGood) {
        this.descriptionOfGood = descriptionOfGood;
    }

    public String getMarksAndNos() {
        return marksAndNos;
    }

    public void setMarksAndNos(String marksAndNos) {
        this.marksAndNos = marksAndNos;
    }

    public Date getDateIssued() {
        return dateIssued;
    }

    public void setDateIssued(Date dateIssued) {
        this.dateIssued = dateIssued;
    }

    public String getReferenceNo() {
        return referenceNo;
    }

    public void setReferenceNo(String referenceNo) {
        this.referenceNo = referenceNo;
    }

    public String getCoverRequired() {
        return coverRequired;
    }

    public void setCoverRequired(String coverRequired) {
        this.coverRequired = coverRequired;
    }

    public String getPolicyNo() {
        return policyNo;
    }

    public void setPolicyNo(String policyNo) {
        this.policyNo = policyNo;
    }

//    public String getPerSS() {
//        return perSS;
//    }
//
//    public void setPerSS(String perSS) {
//        this.perSS = perSS;
//    }
    public String getStartFrom() {
        return startFrom;
    }

    public void setStartFrom(String startFrom) {
        this.startFrom = startFrom;
    }

    public String getArriveTo() {
        return arriveTo;
    }

    public void setArriveTo(String arriveTo) {
        this.arriveTo = arriveTo;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public PrmsLuDmArchive getFileRefNumber() {
        return fileRefNumber;
    }

    public void setFileRefNumber(PrmsLuDmArchive fileRefNumber) {
        this.fileRefNumber = fileRefNumber;
    }

    public Float getPremiumDue() {
        return premiumDue;
    }

    public void setPremiumDue(Float premiumDue) {
        this.premiumDue = premiumDue;
    }

    public String getPreparerPosition() {
        return preparerPosition;
    }

    public void setPreparerPosition(String preparerPosition) {
        this.preparerPosition = preparerPosition;
    }

    public String getPackaging() {
        return packaging;
    }

    public void setPackaging(String packaging) {
        this.packaging = packaging;
    }

    public Integer getPreparedBy() {
        return preparedBy;
    }

    public void setPreparedBy(Integer preparedBy) {
        this.preparedBy = preparedBy;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getProcessedOn() {
        return processedOn;
    }

    public void setProcessedOn(String processedOn) {
        this.processedOn = processedOn;
    }

    public BigDecimal getTotalFc() {
        return totalFc;
    }

    public void setTotalFc(BigDecimal totalFc) {
        this.totalFc = totalFc;
    }

    public BigDecimal getLcAmount() {
        return lcAmount;
    }

    public void setLcAmount(BigDecimal lcAmount) {
        this.lcAmount = lcAmount;
    }

    public FmsLuCurrency getCurrencyName() {
        return currencyName;
    }

    public void setCurrencyName(FmsLuCurrency currencyName) {
        this.currencyName = currencyName;
    }

    public HrDepartments getHrDeptId() {
        return hrDeptId;
    }

    public void setHrDeptId(HrDepartments hrDeptId) {
        this.hrDeptId = hrDeptId;
    }

    public PrmsContract getContractNo() {
        return contractNo;
    }

    public void setContractNo(PrmsContract contractNo) {
        this.contractNo = contractNo;
    }

    public PrmsServiceProvider getServiceProId() {
        return serviceProId;
    }

    public void setServiceProId(PrmsServiceProvider serviceProId) {
        this.serviceProId = serviceProId;
    }

    public PrmsServiceProviderDetail getServiceDtId() {
        return serviceDtId;
    }

    public void setServiceDtId(PrmsServiceProviderDetail serviceDtId) {
        this.serviceDtId = serviceDtId;
    }

    public PrmsServiceProvider getServiceProName() {
        return serviceProName;
    }

    public void setServiceProName(PrmsServiceProvider serviceProName) {
        this.serviceProName = serviceProName;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (insuranceRegId != null ? insuranceRegId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PrmsInsuranceRequisition)) {
            return false;
        }
        PrmsInsuranceRequisition other = (PrmsInsuranceRequisition) object;
        if ((this.insuranceRegId == null && other.insuranceRegId != null) || (this.insuranceRegId != null && !this.insuranceRegId.equals(other.insuranceRegId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return insuranceNo;
        //  return "et.gov.eep.prms.entity.PrmsInsuranceRequisition[ insuranceRegId=" + insuranceRegId .toString()+ "]";
        //return "et.gov.eep.prms.entity.PrmsInsuranceRequisition[ insuranceRegId=" + insuranceRegId + "]";
    }

    @XmlTransient
    public List<PrmsInsuranceNotToBank> getPrmsInsuranceNotToBankList() {
        if (prmsInsuranceNotToBankList == null) {
            prmsInsuranceNotToBankList = new ArrayList<>();
        }
        return prmsInsuranceNotToBankList;
    }

    public void setPrmsInsuranceNotToBankList(List<PrmsInsuranceNotToBank> prmsInsuranceNotToBankList) {
        this.prmsInsuranceNotToBankList = prmsInsuranceNotToBankList;
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

}
