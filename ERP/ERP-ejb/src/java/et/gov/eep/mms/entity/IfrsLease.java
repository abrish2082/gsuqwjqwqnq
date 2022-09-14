/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.mms.entity;

import et.gov.eep.commonApplications.entity.MmsLuDmArchive;
import et.gov.eep.fcms.entity.FmsLuCurrency;
import et.gov.eep.ifrs.entity.IfrsDepreciationSetup;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
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
 * @author insa
 */
@Entity
@Table(name = "IFRS_LEASE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "IfrsLease.findAll", query = "SELECT i FROM IfrsLease i"),
    @NamedQuery(name = "IfrsLease.findByLeaseId", query = "SELECT i FROM IfrsLease i WHERE i.leaseId = :leaseId"),
    @NamedQuery(name = "IfrsLease.findByCostModel", query = "SELECT i FROM IfrsLease i WHERE i.costModel = :costModel"),
    @NamedQuery(name = "IfrsLease.findByLeaseCode", query = "SELECT i FROM IfrsLease i WHERE i.leaseCode = :leaseCode"),
    @NamedQuery(name = "IfrsLease.findByInceptionYear", query = "SELECT i FROM IfrsLease i WHERE i.inceptionYear = :inceptionYear"),
    @NamedQuery(name = "IfrsLease.findByLeaseType", query = "SELECT i FROM IfrsLease i WHERE i.leaseType = :leaseType"),
    @NamedQuery(name = "IfrsLease.findByLeasePeried", query = "SELECT i FROM IfrsLease i WHERE i.leasePeried = :leasePeried"),
    @NamedQuery(name = "IfrsLease.findByLeaseAmount", query = "SELECT i FROM IfrsLease i WHERE i.leaseAmount = :leaseAmount"),
    @NamedQuery(name = "IfrsLease.findByDescription", query = "SELECT i FROM IfrsLease i WHERE i.description = :description"),
    @NamedQuery(name = "IfrsLease.findByLeaseCurrency", query = "SELECT i FROM IfrsLease i WHERE i.leaseCurrency = :leaseCurrency"),
    @NamedQuery(name = "IfrsLease.findByLeasePayementType", query = "SELECT i FROM IfrsLease i WHERE i.leasePayementType = :leasePayementType"),
    @NamedQuery(name = "IfrsLease.findByImpairmentCost", query = "SELECT i FROM IfrsLease i WHERE i.impairmentCost = :impairmentCost"),
    @NamedQuery(name = "IfrsLease.findByDiscountRate", query = "SELECT i FROM IfrsLease i WHERE i.discountRate = :discountRate"),
    @NamedQuery(name = "IfrsLease.findByAssetName", query = "SELECT i FROM IfrsLease i WHERE i.assetName = :assetName"),
    @NamedQuery(name = "IfrsLease.findByInterestRate", query = "SELECT i FROM IfrsLease i WHERE i.interestRate = :interestRate"),
    @NamedQuery(name = "IfrsLease.findByRentalPayement", query = "SELECT i FROM IfrsLease i WHERE i.rentalPayement = :rentalPayement"),
    @NamedQuery(name = "IfrsLease.findByLeaseTerm", query = "SELECT i FROM IfrsLease i WHERE i.leaseTerm = :leaseTerm"),
    @NamedQuery(name = "IfrsLease.findByFairValu", query = "SELECT i FROM IfrsLease i WHERE i.fairValu = :fairValu"),
    @NamedQuery(name = "IfrsLease.findByActPolicy", query = "SELECT i FROM IfrsLease i WHERE i.actPolicy = :actPolicy"),
    @NamedQuery(name = "IfrsLease.findByLeaseStatus", query = "SELECT i FROM IfrsLease i WHERE i.leaseStatus = :leaseStatus"),
    @NamedQuery(name = "IfrsLease.findByLeaseTermination", query = "SELECT i FROM IfrsLease i WHERE i.leaseTermination = :leaseTermination"),
    @NamedQuery(name = "IfrsLease.findByAssetType", query = "SELECT i FROM IfrsLease i WHERE i.assetType = :assetType"),
    @NamedQuery(name = "IfrsLease.findByLeaseLocation", query = "SELECT i FROM IfrsLease i WHERE i.leaseLocation = :leaseLocation"),
    @NamedQuery(name = "IfrsLease.findByClientId", query = "SELECT i FROM IfrsLease i WHERE i.clientId = :clientId"),
    @NamedQuery(name = "IfrsLease.findByLeaseTermInfo", query = "SELECT i FROM IfrsLease i WHERE i.leaseTermInfo = :leaseTermInfo"),
    @NamedQuery(name = "IfrsLease.findByAgreementDate", query = "SELECT i FROM IfrsLease i WHERE i.agreementDate = :agreementDate"),
    @NamedQuery(name = "IfrsLease.findByOriginalTerm", query = "SELECT i FROM IfrsLease i WHERE i.originalTerm = :originalTerm"),
    @NamedQuery(name = "IfrsLease.findByLeaseLifeRemaining", query = "SELECT i FROM IfrsLease i WHERE i.leaseLifeRemaining = :leaseLifeRemaining"),
    @NamedQuery(name = "IfrsLease.findByCommencementDate", query = "SELECT i FROM IfrsLease i WHERE i.commencementDate = :commencementDate"),
    @NamedQuery(name = "IfrsLease.findByPaymentFrequency", query = "SELECT i FROM IfrsLease i WHERE i.paymentFrequency = :paymentFrequency"),
    @NamedQuery(name = "IfrsLease.findByDepreciationType", query = "SELECT i FROM IfrsLease i WHERE i.fagId = :depreciationSetup"),
    @NamedQuery(name = "IfrsLease.findByPurchaseOption", query = "SELECT i FROM IfrsLease i WHERE i.purchaseOption = :purchaseOption")})
public class IfrsLease implements Serializable {

    private static final long serialVersionUID = 1L;

    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
//    @Basic(optional = false)
//    @NotNull
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "IFRS_LEASE_SETUP_SEQ")
    @SequenceGenerator(name = "IFRS_LEASE_SETUP_SEQ", sequenceName = "IFRS_LEASE_SETUP_SEQ", allocationSize = 1)
    @Column(name = "LEASE_ID")
    private BigDecimal leaseId;

    @Size(max = 20)
    @Column(name = "COST_MODEL")
    private String costModel;
    @Column(name = "LEASE_CODE")
    private BigInteger leaseCode;
    @Column(name = "INCEPTION_YEAR")
    private BigInteger inceptionYear;
    @Column(name = "REDUCE_RENT_PERIOD")
    private BigInteger reduceRentPeriod;

    @Size(max = 20)
    @Column(name = "LEASE_AREA")
    private String leaseArea;

    @Column(name = "IMPAIRMENT_COST")
    private BigInteger impairmentCost;

    @Size(max = 20)
    @Column(name = "LEASE_PERIED")
    private String leasePeried;

    @Column(name = "DISCOUNT_RATE")
    private BigInteger discountRate;

    @Size(max = 20)
    @Column(name = "LEASE_TYPE")
    private String leaseType;

    @Column(name = "LEASE_AMOUNT")
    private BigInteger leaseAmount;

    @Column(name = "FAIR_VALU")
    private BigInteger fairValu;

    @Size(max = 20)
    @Column(name = "DESCRIPTION")
    private String description;

//    @Size(max = 20)
//    @Column(name = "LEASE_CURRENCY")
//    private String leaseCurrency;

    @Size(max = 20)
    @Column(name = "LEASE_PAYEMENT_TYPE")
    private String leasePayementType;

    @Size(max = 20)
    @Column(name = "ASSET_NAME")
    private String assetName;

    @Column(name = "INTEREST_RATE")
    private BigInteger interestRate;

    @Column(name = "RENTAL_PAYEMENT")
    private BigInteger rentalPayement;

    @Size(max = 20)
    @Column(name = "LEASE_TERM")
    private String leaseTerm;

    @Size(max = 20)
    @Column(name = "ACT_POLICY")
    private String actPolicy;

    @Size(max = 20)
    @Column(name = "LEASE_STATUS")
    private String leaseStatus;

    @Size(max = 20)
    @Column(name = "LEASE_TERMINATION")
    private String leaseTermination;

    @Size(max = 20)
    @Column(name = "ASSET_TYPE")
    private String assetType;

    @Size(max = 20)
    @Column(name = "LEASE_LOCATION")
    private String leaseLocation;

    @Size(max = 20)
    @Column(name = "CLIENT_ID")
    private String clientId;

    @Size(max = 20)
    @Column(name = "LEASE_TERM_INFO")
    private String leaseTermInfo;

    @Size(max = 20)
    @Column(name = "AGREEMENT_DATE")
    private String agreementDate;

    @Size(max = 20)
    @Column(name = "ORIGINAL_TERM")
    private String originalTerm;

    @Size(max = 20)
    @Column(name = "LEASE_LIFE_REMAINING")
    private String leaseLifeRemaining;

    @Size(max = 20)
    @Column(name = "COMMENCEMENT_DATE")
    private String commencementDate;

    @Size(max = 20)
    @Column(name = "PAYMENT_FREQUENCY")
    private String paymentFrequency;

    @Size(max = 20)
    @Column(name = "PURCHASE_OPTION")
    private String purchaseOption;

    @Size(max = 20)
    @Column(name = "RENT_FREE")
    private String rentFree;

    @Size(max = 20)
    @Column(name = "LEASOR")
    private String leasor;

    @Size(max = 20)
    @Column(name = "POSSESSION_DATE")
    private String possessionDate;

    @Size(max = 20)
    @Column(name = "ORIGINAL_COMMENCEMENT_DATE")
    private String originalCommencementDate;
    @Size(max = 20)
    @Column(name = "COMPANY_NAME")
    private String companyName;

    @Size(max = 20)
    @Column(name = "DEPARTMENT")
    private String department;

    @Size(max = 100)
    @Column(name = "CONTRAT_NAME") 
    private String contratName;
    
      @Size(max = 70)
    @Column(name = "LEASE_PERIED_TYPE") 
    private String leasePeriodType;
      
    @Size(max = 20)
    @Column(name = "CONTRAT_ID")
    private String contratId;
    @Size(max = 20)
    @Column(name = "ORIGINAL_EXPIRATION")
    private String originalExpiration;
    
    @JoinColumn(name = "FAG_ID", referencedColumnName = "ID")
    @ManyToOne
    private IfrsDepreciationSetup fagId;
    
    @JoinColumn(name = "REFERENCE_NUMBER", referencedColumnName = "DOCUMENT_ID")
    @ManyToOne
    private MmsLuDmArchive referenceNumber;
    @JoinColumn(name = "LEASE_CURRENCY", referencedColumnName = "CURRENCY_ID")
    @ManyToOne
    private FmsLuCurrency leaseCurrency;

    public IfrsLease() {
    }

    public IfrsDepreciationSetup getFagId() {
        return fagId;
    }

    public void setFagId(IfrsDepreciationSetup fagId) {
        this.fagId = fagId;
    }

    public IfrsLease(BigDecimal leaseId) {
        this.leaseId = leaseId;
    }

    public BigDecimal getLeaseId() {
        return leaseId;
    }

    public void setLeaseId(BigDecimal leaseId) {
        this.leaseId = leaseId;
    }

    public String getCostModel() {
        return costModel;
    }

    public void setCostModel(String costModel) {
        this.costModel = costModel;
    }

    public BigInteger getLeaseCode() {
        return leaseCode;
    }

    public void setLeaseCode(BigInteger leaseCode) {
        this.leaseCode = leaseCode;
    }

    public BigInteger getInceptionYear() {
        return inceptionYear;
    }

    public void setInceptionYear(BigInteger inceptionYear) {
        this.inceptionYear = inceptionYear;
    }

    public String getLeaseType() {
        return leaseType;
    }

    public void setLeaseType(String leaseType) {
        this.leaseType = leaseType;
    }

    public String getLeasePeried() {
        return leasePeried;
    }

    public void setLeasePeried(String leasePeried) {
        this.leasePeried = leasePeried;
    }

    public BigInteger getLeaseAmount() {
        return leaseAmount;
    }

    public void setLeaseAmount(BigInteger leaseAmount) {
        this.leaseAmount = leaseAmount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public FmsLuCurrency getLeaseCurrency() {
        return leaseCurrency;
    }

    public void setLeaseCurrency(FmsLuCurrency leaseCurrency) {
        this.leaseCurrency = leaseCurrency;
    }

//    public String getLeaseCurrency() {
//        return leaseCurrency;
//    }
//
//    public void setLeaseCurrency(String leaseCurrency) {
//        this.leaseCurrency = leaseCurrency;
//    }

    public String getLeasePayementType() {
        return leasePayementType;
    }

    public void setLeasePayementType(String leasePayementType) {
        this.leasePayementType = leasePayementType;
    }

    public BigInteger getImpairmentCost() {
        return impairmentCost;
    }

    public void setImpairmentCost(BigInteger impairmentCost) {
        this.impairmentCost = impairmentCost;
    }

    public BigInteger getDiscountRate() {
        return discountRate;
    }

    public void setDiscountRate(BigInteger discountRate) {
        this.discountRate = discountRate;
    }

    public String getAssetName() {
        return assetName;
    }

    public void setAssetName(String assetName) {
        this.assetName = assetName;
    }

    public BigInteger getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(BigInteger interestRate) {
        this.interestRate = interestRate;
    }

    public BigInteger getRentalPayement() {
        return rentalPayement;
    }

    public void setRentalPayement(BigInteger rentalPayement) {
        this.rentalPayement = rentalPayement;
    }

    public String getLeaseTerm() {
        return leaseTerm;
    }

    public void setLeaseTerm(String leaseTerm) {
        this.leaseTerm = leaseTerm;
    }

    public BigInteger getFairValu() {
        return fairValu;
    }

    public void setFairValu(BigInteger fairValu) {
        this.fairValu = fairValu;
    }

    public String getActPolicy() {
        return actPolicy;
    }

    public void setActPolicy(String actPolicy) {
        this.actPolicy = actPolicy;
    }

    public String getLeaseStatus() {
        return leaseStatus;
    }

    public void setLeaseStatus(String leaseStatus) {
        this.leaseStatus = leaseStatus;
    }

    public String getLeaseTermination() {
        return leaseTermination;
    }

    public void setLeaseTermination(String leaseTermination) {
        this.leaseTermination = leaseTermination;
    }

    public String getAssetType() {
        return assetType;
    }

    public void setAssetType(String assetType) {
        this.assetType = assetType;
    }

    public String getLeaseLocation() {
        return leaseLocation;
    }

    public void setLeaseLocation(String leaseLocation) {
        this.leaseLocation = leaseLocation;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getLeaseTermInfo() {
        return leaseTermInfo;
    }

    public void setLeaseTermInfo(String leaseTermInfo) {
        this.leaseTermInfo = leaseTermInfo;
    }

    public String getAgreementDate() {
        return agreementDate;
    }

    public void setAgreementDate(String agreementDate) {
        this.agreementDate = agreementDate;
    }

    public String getOriginalTerm() {
        return originalTerm;
    }

    public void setOriginalTerm(String originalTerm) {
        this.originalTerm = originalTerm;
    }

    public String getLeaseLifeRemaining() {
        return leaseLifeRemaining;
    }

    public void setLeaseLifeRemaining(String leaseLifeRemaining) {
        this.leaseLifeRemaining = leaseLifeRemaining;
    }

    public String getCommencementDate() {
        return commencementDate;
    }

    public void setCommencementDate(String commencementDate) {
        this.commencementDate = commencementDate;
    }

    public String getPaymentFrequency() {
        return paymentFrequency;
    }

    public void setPaymentFrequency(String paymentFrequency) {
        this.paymentFrequency = paymentFrequency;
    }

    public String getPurchaseOption() {
        return purchaseOption;
    }

    public void setPurchaseOption(String purchaseOption) {
        this.purchaseOption = purchaseOption;
    }

    public BigInteger getReduceRentPeriod() {
        return reduceRentPeriod;
    }

    public void setReduceRentPeriod(BigInteger reduceRentPeriod) {
        this.reduceRentPeriod = reduceRentPeriod;
    }

    public String getLeaseArea() {
        return leaseArea;
    }

    public void setLeaseArea(String leaseArea) {
        this.leaseArea = leaseArea;
    }

    public String getRentFree() {
        return rentFree;
    }

    public void setRentFree(String rentFree) {
        this.rentFree = rentFree;
    }

    public String getLeasor() {
        return leasor;
    }

    public void setLeasor(String leasor) {
        this.leasor = leasor;
    }

    public String getPossessionDate() {
        return possessionDate;
    }

    public void setPossessionDate(String possessionDate) {
        this.possessionDate = possessionDate;
    }

    public String getOriginalCommencementDate() {
        return originalCommencementDate;
    }

    public void setOriginalCommencementDate(String originalCommencementDate) {
        this.originalCommencementDate = originalCommencementDate;
    }

    public String getOriginalExpiration() {
        return originalExpiration;
    }

    public void setOriginalExpiration(String originalExpiration) {
        this.originalExpiration = originalExpiration;
    }

    public MmsLuDmArchive getReferenceNumber() {
        return referenceNumber;
    }

    public void setReferenceNumber(MmsLuDmArchive referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getContratName() {
        return contratName;
    }

    public void setContratName(String contractName) {
        this.contratName = contratName;
    }

    public String getLeasePeriodType() {
        return leasePeriodType;
    }

    public void setLeasePeriodType(String leasePeriodType) {
        this.leasePeriodType = leasePeriodType;
    }

    public String getContratId() {
        return contratId;
    }

    public void setContratId(String contratId) {
        this.contratId = contratId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (leaseId != null ? leaseId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof IfrsLease)) {
            return false;
        }
        IfrsLease other = (IfrsLease) object;
        if ((this.leaseId == null && other.leaseId != null) || (this.leaseId != null && !this.leaseId.equals(other.leaseId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "et.gov.eep.mms.entity.IfrsLease[ leaseId=" + leaseId + " ]";
    }

}
