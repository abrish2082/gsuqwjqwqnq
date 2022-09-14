/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.prms.entity;

import et.gov.eep.commonApplications.entity.ComLuCountry;
import et.gov.eep.fcms.entity.FmsLuCurrency;
import et.gov.eep.hrms.entity.lookup.HrLuBankBranches;
import et.gov.eep.hrms.entity.lookup.HrLuBanks;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author user
 */
@Entity
@Table(name = "PRMS_BID_OPENING_CHECKLST_DT")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PrmsBidOpeningChecklstDt.findAll", query = "SELECT p FROM PrmsBidOpeningChecklstDt p"),
    @NamedQuery(name = "PrmsBidOpeningChecklstDt.findByBidCheckDtId", query = "SELECT p FROM PrmsBidOpeningChecklstDt p WHERE p.bidCheckDtId = :bidCheckDtId"),
    @NamedQuery(name = "PrmsBidOpeningChecklstDt.findByBidFormStatus", query = "SELECT p FROM PrmsBidOpeningChecklstDt p WHERE p.bidFormStatus = :bidFormStatus"),
    @NamedQuery(name = "PrmsBidOpeningChecklstDt.findByDocumentAuthorityForSigning", query = "SELECT p FROM PrmsBidOpeningChecklstDt p WHERE p.documentAuthorityForSigning = :documentAuthorityForSigning"),
    @NamedQuery(name = "PrmsBidOpeningChecklstDt.findByOuterEnvolopeOfBid", query = "SELECT p FROM PrmsBidOpeningChecklstDt p WHERE p.outerEnvolopeOfBid = :outerEnvolopeOfBid"),
    @NamedQuery(name = "PrmsBidOpeningChecklstDt.findByDiscriptionOfAlternateBid", query = "SELECT p FROM PrmsBidOpeningChecklstDt p WHERE p.discriptionOfAlternateBid = :discriptionOfAlternateBid"),
    @NamedQuery(name = "PrmsBidOpeningChecklstDt.findByDiscriptionOfModification", query = "SELECT p FROM PrmsBidOpeningChecklstDt p WHERE p.discriptionOfModification = :discriptionOfModification"),
    @NamedQuery(name = "PrmsBidOpeningChecklstDt.findByAdditionalComent", query = "SELECT p FROM PrmsBidOpeningChecklstDt p WHERE p.additionalComent = :additionalComent"),
    @NamedQuery(name = "PrmsBidOpeningChecklstDt.findByNameOfBidderPresent", query = "SELECT p FROM PrmsBidOpeningChecklstDt p WHERE p.nameOfBidderPresent = :nameOfBidderPresent"),
    @NamedQuery(name = "PrmsBidOpeningChecklstDt.findByDiscriptionOfSubmission", query = "SELECT p FROM PrmsBidOpeningChecklstDt p WHERE p.discriptionOfSubmission = :discriptionOfSubmission"),
    @NamedQuery(name = "PrmsBidOpeningChecklstDt.findByTotalBidPrice", query = "SELECT p FROM PrmsBidOpeningChecklstDt p WHERE p.totalBidPrice = :totalBidPrice"),
    @NamedQuery(name = "PrmsBidOpeningChecklstDt.findByBidSecurityAmount", query = "SELECT p FROM PrmsBidOpeningChecklstDt p WHERE p.bidSecurityAmount = :bidSecurityAmount"),
    @NamedQuery(name = "PrmsBidOpeningChecklstDt.findByExpirationDatBidsec", query = "SELECT p FROM PrmsBidOpeningChecklstDt p WHERE p.expirationDatBidsec = :expirationDatBidsec"),
    @NamedQuery(name = "PrmsBidOpeningChecklstDt.findByMaxCheckListNum", query = "SELECT p FROM PrmsBidOpeningChecklstDt p WHERE p.bidCheckDtId = (SELECT Max(c.bidCheckDtId)  from PrmsBidOpeningChecklstDt c)"),
    @NamedQuery(name = "PrmsBidOpeningChecklstDt.findByPercent", query = "SELECT p FROM PrmsBidOpeningChecklstDt p WHERE p.percent = :percent"),
    @NamedQuery(name = "PrmsBidOpeningChecklstDt.findByLotName", query = "SELECT p FROM PrmsBidOpeningChecklstDt p WHERE p.lotName = :lotName"),
    @NamedQuery(name = "PrmsBidOpeningChecklstDt.findByDiscountType", query = "SELECT p FROM PrmsBidOpeningChecklstDt p WHERE p.discountType = :discountType")})
public class PrmsBidOpeningChecklstDt implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PRMS_bID_OPENING_DT_SEQ")
    @SequenceGenerator(name = "PRMS_bID_OPENING_DT_SEQ", sequenceName = "PRMS_bID_OPENING_DT_SEQ", allocationSize = 1)

    @Column(name = "BID_CHECK_DT_ID")
    private BigDecimal bidCheckDtId;
    @Size(max = 100)
    @Column(name = "BID_FORM_STATUS")
    private String bidFormStatus;
    @Size(max = 100)
    @Column(name = "DOCUMENT_AUTHORITY_FOR_SIGNING")
    private String documentAuthorityForSigning;
    @Size(max = 100)
    @Column(name = "OUTER_ENVOLOPE_OF_BID")
    private String outerEnvolopeOfBid;
    @Size(max = 100)
    @Column(name = "DISCRIPTION_OF_ALTERNATE_BID")
    private String discriptionOfAlternateBid;
    @Size(max = 100)
    @Column(name = "DISCRIPTION_OF_MODIFICATION")
    private String discriptionOfModification;
    @Size(max = 500)
    @Column(name = "ADDITIONAL_COMENT")
    private String additionalComent;
    @Size(max = 100)
    @Column(name = "NAME_OF_BIDDER_PRESENT")
    private String nameOfBidderPresent;
    @Size(max = 100)
    @Column(name = "DISCRIPTION_OF_SUBMISSION")
    private String discriptionOfSubmission;
    @Column(name = "TOTAL_BID_PRICE")
    private Double totalBidPrice;
    @Column(name = "BID_SECURITY_AMOUNT")
    private Double bidSecurityAmount;
    @Column(name = "EXPIRATION_DAT_BIDSEC")
    @Temporal(TemporalType.TIMESTAMP)
    private Date expirationDatBidsec;
    @Size(max = 100)
    @Column(name = "PERCENT")
    private String percent;
    @Size(max = 50)
    @Column(name = "LOT_NAME")
    private String lotName;
    @Size(max = 100)
    @Column(name = "DISCOUNT_TYPE")
    private String discountType;
    @JoinColumn(name = "COUNTRY_ID", referencedColumnName = "ID")
    @ManyToOne
    private ComLuCountry countryId;
    @JoinColumn(name = "CURRENCY_ID", referencedColumnName = "CURRENCY_ID")
    @ManyToOne
    private FmsLuCurrency currencyId;
    @JoinColumn(name = "DISC_CURRENCY_ID", referencedColumnName = "CURRENCY_ID")
    @ManyToOne
    private FmsLuCurrency discCurrencyId;
    @JoinColumn(name = "BID_SEC_CURRENCY", referencedColumnName = "CURRENCY_ID")
    @ManyToOne
    private FmsLuCurrency bidSecCurrency;
    @JoinColumn(name = "BANK_ID", referencedColumnName = "ID")
    @ManyToOne
    private HrLuBanks bankId;
    @JoinColumn(name = "BANK_BRANCH_ID", referencedColumnName = "ID")
    @ManyToOne
    private HrLuBankBranches bankBranchId;
    @JoinColumn(name = "BID_CHECK_LIST_ID", referencedColumnName = "BID_OPEN_CHECK_LIST_ID")
    @ManyToOne
    private PrmsBidOpeningCheckList bidCheckListId;
    @JoinColumn(name = "SUPP_ID", referencedColumnName = "ID")
    @ManyToOne
    private PrmsSupplyProfile suppId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "checkListId", fetch = FetchType.LAZY)
    private List<PrmsCheckListDetailforlot> checkListDetailforlots;

    public PrmsBidOpeningChecklstDt() {
    }

    public PrmsBidOpeningChecklstDt(BigDecimal bidCheckDtId) {
        this.bidCheckDtId = bidCheckDtId;
    }

    public BigDecimal getBidCheckDtId() {
        return bidCheckDtId;
    }

    public void setBidCheckDtId(BigDecimal bidCheckDtId) {
        this.bidCheckDtId = bidCheckDtId;
    }

    public String getBidFormStatus() {
        return bidFormStatus;
    }

    public void setBidFormStatus(String bidFormStatus) {
        this.bidFormStatus = bidFormStatus;
    }

    public String getDocumentAuthorityForSigning() {
        return documentAuthorityForSigning;
    }

    public void setDocumentAuthorityForSigning(String documentAuthorityForSigning) {
        this.documentAuthorityForSigning = documentAuthorityForSigning;
    }

    public String getOuterEnvolopeOfBid() {
        return outerEnvolopeOfBid;
    }

    public void setOuterEnvolopeOfBid(String outerEnvolopeOfBid) {
        this.outerEnvolopeOfBid = outerEnvolopeOfBid;
    }

    public String getDiscriptionOfAlternateBid() {
        return discriptionOfAlternateBid;
    }

    public void setDiscriptionOfAlternateBid(String discriptionOfAlternateBid) {
        this.discriptionOfAlternateBid = discriptionOfAlternateBid;
    }

    public String getDiscriptionOfModification() {
        return discriptionOfModification;
    }

    public void setDiscriptionOfModification(String discriptionOfModification) {
        this.discriptionOfModification = discriptionOfModification;
    }

    public String getAdditionalComent() {
        return additionalComent;
    }

    public void setAdditionalComent(String additionalComent) {
        this.additionalComent = additionalComent;
    }

    public String getNameOfBidderPresent() {
        return nameOfBidderPresent;
    }

    public void setNameOfBidderPresent(String nameOfBidderPresent) {
        this.nameOfBidderPresent = nameOfBidderPresent;
    }

    public String getDiscriptionOfSubmission() {
        return discriptionOfSubmission;
    }

    public void setDiscriptionOfSubmission(String discriptionOfSubmission) {
        this.discriptionOfSubmission = discriptionOfSubmission;
    }

    public Double getTotalBidPrice() {
        return totalBidPrice;
    }

    public void setTotalBidPrice(Double totalBidPrice) {
        this.totalBidPrice = totalBidPrice;
    }

    public Double getBidSecurityAmount() {
        return bidSecurityAmount;
    }

    public void setBidSecurityAmount(Double bidSecurityAmount) {
        this.bidSecurityAmount = bidSecurityAmount;
    }

    public Date getExpirationDatBidsec() {
        return expirationDatBidsec;
    }

    public void setExpirationDatBidsec(Date expirationDatBidsec) {
        this.expirationDatBidsec = expirationDatBidsec;
    }

    public String getPercent() {
        return percent;
    }

    public void setPercent(String percent) {
        this.percent = percent;
    }

    public String getLotName() {
        return lotName;
    }

    public void setLotName(String lotName) {
        this.lotName = lotName;
    }

    public String getDiscountType() {
        return discountType;
    }

    public void setDiscountType(String discountType) {
        this.discountType = discountType;
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

    public FmsLuCurrency getDiscCurrencyId() {
        return discCurrencyId;
    }

    public void setDiscCurrencyId(FmsLuCurrency discCurrencyId) {
        this.discCurrencyId = discCurrencyId;
    }

    public HrLuBanks getBankId() {
        return bankId;
    }

    public void setBankId(HrLuBanks bankId) {
        this.bankId = bankId;
    }

    public HrLuBankBranches getBankBranchId() {
        return bankBranchId;
    }

    public void setBankBranchId(HrLuBankBranches bankBranchId) {
        this.bankBranchId = bankBranchId;
    }

    public PrmsBidOpeningCheckList getBidCheckListId() {
        return bidCheckListId;
    }

    public void setBidCheckListId(PrmsBidOpeningCheckList bidCheckListId) {
        this.bidCheckListId = bidCheckListId;
    }

    public PrmsSupplyProfile getSuppId() {
        return suppId;
    }

    public void setSuppId(PrmsSupplyProfile suppId) {
        this.suppId = suppId;
    }

    public FmsLuCurrency getBidSecCurrency() {
        return bidSecCurrency;
    }

    public void setBidSecCurrency(FmsLuCurrency bidSecCurrency) {
        this.bidSecCurrency = bidSecCurrency;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (bidCheckDtId != null ? bidCheckDtId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PrmsBidOpeningChecklstDt)) {
            return false;
        }
        PrmsBidOpeningChecklstDt other = (PrmsBidOpeningChecklstDt) object;
        if ((this.bidCheckDtId == null && other.bidCheckDtId != null) || (this.bidCheckDtId != null && !this.bidCheckDtId.equals(other.bidCheckDtId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "et.gov.eep.prms.entity.PrmsBidOpeningChecklstDt[ bidCheckDtId=" + bidCheckDtId + " ]";
    }

    @XmlTransient
    public List<PrmsCheckListDetailforlot> getCheckListDetailforlots() {
        if (checkListDetailforlots == null) {
            checkListDetailforlots = new ArrayList<>();
        }
        return checkListDetailforlots;
    }

    public void setCheckListDetailforlots(List<PrmsCheckListDetailforlot> checkListDetailforlots) {
        this.checkListDetailforlots = checkListDetailforlots;
    }

    public void addBidCheckListDetail(PrmsCheckListDetailforlot checkListDetailforlot) {
        if (checkListDetailforlot.getCheckListId() != this) {
            this.getCheckListDetailforlots().add(checkListDetailforlot);
            checkListDetailforlot.setCheckListId(this);
        }

    }
}
