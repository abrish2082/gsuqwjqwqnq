/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.entity.Bond;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
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
import et.gov.eep.commonApplications.entity.ComLuCountry;
import et.gov.eep.fcms.entity.FmsLuCurrency;

/**
 *
 * @author mora1
 */
@Entity
@Table(name = "FMS_BOND_FOREIGN")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FmsBondForeign.findAll", query = "SELECT f FROM FmsBondForeign f"),
    @NamedQuery(name = "FmsBondForeign.findBySerialNo", query = "SELECT f FROM FmsBondForeign f WHERE f.serialNo LIKE :serialNo"),
    @NamedQuery(name = "FmsBondForeign.findBySerialNoAndName", query = "SELECT f FROM FmsBondForeign f WHERE f.serialNo LIKE :serialNo and f.buyerFullName LIKE :buyerFullName"),
    @NamedQuery(name = "FmsBondForeign.findByBuyerFullName", query = "SELECT f FROM FmsBondForeign f WHERE f.buyerFullName = :buyerFullName"),
    @NamedQuery(name = "FmsBondForeign.findByEquivalentInEthiopianBirr", query = "SELECT f FROM FmsBondForeign f WHERE f.equivalentInEthiopianBirr = :equivalentInEthiopianBirr"),
    @NamedQuery(name = "FmsBondForeign.findByStatus", query = "SELECT f FROM FmsBondForeign f WHERE f.status = :status"),
    @NamedQuery(name = "FmsBondForeign.findBySerialNoLike", query = "SELECT f FROM FmsBondForeign f WHERE f.serialNo LIKE :serialNo"),
    @NamedQuery(name = "FmsBondForeign.findByBuyerFullNamelike", query = "SELECT f FROM FmsBondForeign f WHERE f.buyerFullName LIKE :buyerFullName"),
    @NamedQuery(name = "FmsBondForeign.findByApplicationDate", query = "SELECT f FROM FmsBondForeign f WHERE f.applicationDate = :applicationDate"),
    @NamedQuery(name = "FmsBondForeign.findByApplicationDateLike", query = "SELECT f FROM FmsBondForeign f WHERE f.applicationDate = :applicationDate"),
    @NamedQuery(name = "FmsBondForeign.findByDueDate", query = "SELECT f FROM FmsBondForeign f WHERE f.dueDate = :dueDate"),
    @NamedQuery(name = "FmsBondForeign.findByInterestBearing", query = "SELECT f FROM FmsBondForeign f WHERE f.interestBearing = :interestBearing"),
    @NamedQuery(name = "FmsBondForeign.findByAmount", query = "SELECT f FROM FmsBondForeign f WHERE f.principalamount = :principalamount"),
    @NamedQuery(name = "FmsBondForeign.findByBondValueYear", query = "SELECT f FROM FmsBondForeign f WHERE f.BondValueYear = :BondValueYear"),
    @NamedQuery(name = "FmsBondForeign.findByCountryBondIssued", query = "SELECT f FROM FmsBondForeign f WHERE f.countryBondIssued = :countryBondIssued"),
    @NamedQuery(name = "FmsBondForeign.findByCountryBondIssuedlike", query = "SELECT f FROM FmsBondForeign f WHERE f.countryBondIssued.country LIKE :countryBondIssued"),
    @NamedQuery(name = "FmsBondForeign.findByLastRepaymentSchedule", query = "SELECT f FROM FmsBondForeign f WHERE f.lastRepaymentSchedule = :lastRepaymentSchedule"),
    @NamedQuery(name = "FmsBondForeign.findByForeignBondId", query = "SELECT f FROM FmsBondForeign f WHERE f.foreignBondId = :foreignBondId"),
    @NamedQuery(name = "FmsBondForeign.findByBondForeignId", query = "SELECT f FROM FmsBondForeign f WHERE f.BondForeignId = :BondForeignId"),
    @NamedQuery(name = "FmsBondForeign.findByValueDate", query = "SELECT f FROM FmsBondForeign f WHERE f.valueDate = :valueDate"),
    @NamedQuery(name = "FmsBondForeign.findByBondMaturityDate", query = "SELECT f FROM FmsBondForeign f WHERE f.BondMaturityDate = :BondMaturityDate"),
    @NamedQuery(name = "FmsBondForeign.findByReferenceNumber", query = "SELECT f FROM FmsBondForeign f WHERE f.referenceNumber = :referenceNumber"),
    @NamedQuery(name = "FmsBondForeign.findByfindBySerialNameAmountAndCurency", query = "SELECT f FROM FmsBondForeign f WHERE f.serialNo = :serialNo and f.buyerFullName = :buyerFullName and f.fmsLuCurrency = :BondCurrency and f.principalamount = :principalamount"),
    @NamedQuery(name = "FmsBondForeign.findBySerialAmountAndCurency", query = "SELECT f FROM FmsBondForeign f WHERE f.serialNo = :serialNo and f.fmsLuCurrency = :BondCurrency and f.principalamount = :principalamount"),
    @NamedQuery(name = "FmsBondForeign.findByNameAmountAndCurency", query = "SELECT f FROM FmsBondForeign f WHERE f.buyerFullName = :buyerFullName and f.fmsLuCurrency = :BondCurrency and f.principalamount = :principalamount"),
    @NamedQuery(name = "FmsBondForeign.findByAmountAndCurency", query = "SELECT f FROM FmsBondForeign f WHERE f.fmsLuCurrency = :BondCurrency and f.principalamount = :principalamount"),
    @NamedQuery(name = "FmsBondForeign.findBySerialAndAmount", query = "SELECT f FROM FmsBondForeign f WHERE f.serialNo = :serialNo and f.principalamount = :principalamount"),
    @NamedQuery(name = "FmsBondForeign.findByNameAndCurrency", query = "SELECT f FROM FmsBondForeign f WHERE f.buyerFullName = :buyerFullName and f.fmsLuCurrency = :BondCurrency"),
    @NamedQuery(name = "FmsBondForeign.findBySerialAndCurrency", query = "SELECT f FROM FmsBondForeign f WHERE f.serialNo = :serialNo and f.fmsLuCurrency = :BondCurrency"),
    @NamedQuery(name = "FmsBondForeign.findByBondCurrency", query = "SELECT f FROM FmsBondForeign f WHERE f.fmsLuCurrency = :BondCurrency"),
    @NamedQuery(name = "FmsBondForeign.findByExtend", query = "SELECT f FROM FmsBondForeign f WHERE f.extend = :extend")})
public class FmsBondForeign implements Serializable {

    private static final long serialVersionUID = 1L;
    //<editor-fold defaultstate="collapsed" desc="variable declaration">
    @Id
    @Basic(optional = false)
    @Column(name = "BOND_FOREIGN_ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FMS_BOND_FOREIGN_SEQ")
    @SequenceGenerator(name = "FMS_BOND_FOREIGN_SEQ", sequenceName = "FMS_BOND_FOREIGN_SEQ", allocationSize = 1)
    private Integer BondForeignId;
    @Basic(optional = false)
    @NotNull
    @Size(max = 20)
    @Column(name = "SERIAL_NO")
    private String serialNo;
    @Column(name = "BUYER_FULL_NAME")
    private String buyerFullName;
    @Column(name = "APPLICATION_DATE")
    @Temporal(TemporalType.DATE)
    private Date applicationDate;
    @Column(name = "DUE_DATE")
    @Temporal(TemporalType.DATE)
    private Date dueDate;
    @Column(name = "BOND_MATURITY_DATE")
    @Temporal(TemporalType.DATE)
    private Date BondMaturityDate;
    @Column(name = "INTEREST_BEARING")
    private Double interestBearing;
    @Column(name = "PRINCIPAL_AMOUNT")
    private Double principalamount;
    @Column(name = "BOND_VALUE_YEAR")
    private Integer BondValueYear;
    @Column(name = "EQUIVALENT_IN_ETHIOPIAN_BIRR")
    private Double equivalentInEthiopianBirr;
    @Column(name = "LAST_REPAYMENT_SCHEDULE")
    @Temporal(TemporalType.DATE)
    private Date lastRepaymentSchedule;
    @Column(name = "STATUS")
    private String status;
    @Column(name = "FOREIGN_BOND_ID")
    private BigInteger foreignBondId;
    @Size(max = 20)
    @Column(name = "EXTEND")
    private String extend;
    @Column(name = "VALUE_DATE")
    @Temporal(TemporalType.DATE)
    private Date valueDate;
    @Column(name = "PAYMENT_DOCUMENT_DATE")
    @Temporal(TemporalType.DATE)
    private Date paymentDocumentDate;
    @Column(name = "PAYMENT_DOCUMENT_REFERENCE")
    private String paymentDocumentReference;
    @Column(name = "REMARK")
    private String remark;
    @Column(name = "REFERENCE_NUMBER")
    private String referenceNumber;
    @JoinColumn(name = "COUNTRY_BOND_ISSUED", referencedColumnName = "ID")
    @ManyToOne
    private ComLuCountry countryBondIssued;
    @JoinColumn(name = "BOND_NAME", referencedColumnName = "NAME")
    @ManyToOne
    private FmsLuBondType BondName;
    @JoinColumn(name = "BOND_CURRENCY", referencedColumnName = "CURRENCY_ID")
    @ManyToOne
    private FmsLuCurrency fmsLuCurrency;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "BondForeignId")
    private List<FmsBondForeignSchedule> fmsBondForeignScheduleList = new ArrayList<>();
    @Transient
    Double bondAmount;
    @Transient
    Double interest;
    @Transient
    int countppople;
    @Transient
    Double sumofamount;
    @Transient
    String ChangedStatus;
    @Transient
    Date paymentDate;
//</editor-fold>

    public FmsBondForeign() {
    }

    //<editor-fold defaultstate="collapsed" desc="Getter and Setter Methods">
    public Integer getBondForeignId() {
        return BondForeignId;
    }

    public void setBondForeignId(Integer BondForeignId) {
        this.BondForeignId = BondForeignId;
    }

    public FmsBondForeign(Integer BondForeignId) {
        this.BondForeignId = BondForeignId;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public String getReferenceNumber() {
        return referenceNumber;
    }

    public void setReferenceNumber(String referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

    public Date getPaymentDocumentDate() {
        return paymentDocumentDate;
    }

    public void setPaymentDocumentDate(Date paymentDocumentDate) {
        this.paymentDocumentDate = paymentDocumentDate;
    }

    public String getPaymentDocumentReference() {
        return paymentDocumentReference;
    }

    public void setPaymentDocumentReference(String paymentDocumentReference) {
        this.paymentDocumentReference = paymentDocumentReference;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getBuyerFullName() {
        return buyerFullName;
    }

    public void setBuyerFullName(String buyerFullName) {
        this.buyerFullName = buyerFullName;
    }

    public Date getApplicationDate() {
        return applicationDate;
    }

    public void setApplicationDate(Date applicationDate) {
        this.applicationDate = applicationDate;
    }

    public Date getValueDate() {
        return valueDate;
    }

    public void setValueDate(Date valueDate) {
        this.valueDate = valueDate;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Date getBondMaturityDate() {
        return BondMaturityDate;
    }

    public void setBondMaturityDate(Date BondMaturityDate) {
        this.BondMaturityDate = BondMaturityDate;
    }

    public Double getInterestBearing() {
        return interestBearing;
    }

    public void setInterestBearing(Double interestBearing) {
        this.interestBearing = interestBearing;
    }

    public Double getPrincipalamount() {
        return principalamount;
    }

    public void setPrincipalamount(Double principalamount) {
        this.principalamount = principalamount;
    }

    public Integer getBondValueYear() {
        return BondValueYear;
    }

    public void setBondValueYear(Integer BondValueYear) {
        this.BondValueYear = BondValueYear;
    }

    public Double getEquivalentInEthiopianBirr() {
        return equivalentInEthiopianBirr;
    }

    public void setEquivalentInEthiopianBirr(Double equivalentInEthiopianBirr) {
        this.equivalentInEthiopianBirr = equivalentInEthiopianBirr;
    }

    public Date getLastRepaymentSchedule() {
        return lastRepaymentSchedule;
    }

    public void setLastRepaymentSchedule(Date lastRepaymentSchedule) {
        this.lastRepaymentSchedule = lastRepaymentSchedule;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public BigInteger getForeignBondId() {
        return foreignBondId;
    }

    public void setForeignBondId(BigInteger foreignBondId) {
        this.foreignBondId = foreignBondId;
    }

    public String getExtend() {
        return extend;
    }

    public void setExtend(String extend) {
        this.extend = extend;
    }

    public ComLuCountry getCountryBondIssued() {
        return countryBondIssued;
    }

    public void setCountryBondIssued(ComLuCountry countryBondIssued) {
        this.countryBondIssued = countryBondIssued;
    }

    public FmsLuBondType getBondName() {
        return BondName;
    }

    public void setBondName(FmsLuBondType BondName) {
        this.BondName = BondName;
    }

    public FmsLuCurrency getFmsLuCurrency() {
        return fmsLuCurrency;
    }

    public void setFmsLuCurrency(FmsLuCurrency fmsLuCurrency) {
        this.fmsLuCurrency = fmsLuCurrency;
    }

    public Double getBondAmount() {
        return bondAmount;
    }

    public void setBondAmount(Double bondAmount) {
        this.bondAmount = bondAmount;
    }

    public Double getInterest() {
        return interest;
    }

    public void setInterest(Double interest) {
        this.interest = interest;
    }

    public int getCountpeople() {
        return countppople;
    }

    public void setCountpeople(int countppople) {
        this.countppople = countppople;
    }

    public Double getSumofamount() {
        return sumofamount;
    }

    public void setSumofamount(Double sumofamount) {
        this.sumofamount = sumofamount;
    }

    public String getChangedStatus() {
        return ChangedStatus;
    }

    public void setChangedStatus(String ChangedStatus) {
        this.ChangedStatus = ChangedStatus;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }
//</editor-fold>

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (serialNo != null ? serialNo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof FmsBondForeign)) {
            return false;
        }
        FmsBondForeign other = (FmsBondForeign) object;
        if ((this.serialNo == null && other.serialNo != null) || (this.serialNo != null && !this.serialNo.equals(other.serialNo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return serialNo;
    }

    @XmlTransient
    public List<FmsBondForeignSchedule> getFmsBondForeignScheduleList() {
        if (fmsBondForeignScheduleList == null) {
            fmsBondForeignScheduleList = new ArrayList<>();
        }
        return fmsBondForeignScheduleList;
    }

    public void setFmsBondForeignScheduleList(List<FmsBondForeignSchedule> fmsBondForeignScheduleList) {
        this.fmsBondForeignScheduleList = fmsBondForeignScheduleList;
    }

    public void addToFmsBondForeignScheduleDetail(FmsBondForeignSchedule fmsBondForeignScheduleDetail) {
        this.getFmsBondForeignScheduleList().add(fmsBondForeignScheduleDetail);
        fmsBondForeignScheduleDetail.setBondForeignId(this);

    }

}
