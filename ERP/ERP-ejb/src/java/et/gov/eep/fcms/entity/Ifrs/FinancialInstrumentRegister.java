/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.entity.Ifrs;

import java.io.Serializable;
import java.math.BigDecimal;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import et.gov.eep.fcms.entity.FmsLuCurrency;
import et.gov.eep.fcms.entity.admin.FmsGeneralLedger;
import et.gov.eep.fcms.entity.admin.FmsSubsidiaryLedger;
import javax.persistence.Transient;

/**
 *
 * @author mora1
 */
@Entity
@Table(name = "FINANCIAL_INSTRUMENT_REGISTER")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FinancialInstrumentRegister.findAll", query = "SELECT f FROM FinancialInstrumentRegister f"),
    @NamedQuery(name = "FinancialInstrumentRegister.findByRegisterId", query = "SELECT f FROM FinancialInstrumentRegister f WHERE f.registerId = :registerId"),
    @NamedQuery(name = "FinancialInstrumentRegister.findByAssetDescription", query = "SELECT f FROM FinancialInstrumentRegister f WHERE f.assetDescription = :assetDescription"),
    @NamedQuery(name = "FinancialInstrumentRegister.findByIssuedDate", query = "SELECT f FROM FinancialInstrumentRegister f WHERE f.issuedDate = :issuedDate"),
    @NamedQuery(name = "FinancialInstrumentRegister.findByCarryngAmount", query = "SELECT f FROM FinancialInstrumentRegister f WHERE f.carryngAmount = :carryngAmount"),
    @NamedQuery(name = "FinancialInstrumentRegister.findByInterestRate", query = "SELECT f FROM FinancialInstrumentRegister f WHERE f.interestRate = :interestRate"),
    @NamedQuery(name = "FinancialInstrumentRegister.findByAssetStatus", query = "SELECT f FROM FinancialInstrumentRegister f WHERE f.assetStatus = :assetStatus"),
    @NamedQuery(name = "FinancialInstrumentRegister.findByImpairmentCost", query = "SELECT f FROM FinancialInstrumentRegister f WHERE f.impairmentCost = :impairmentCost"),
    @NamedQuery(name = "FinancialInstrumentRegister.findByReportingDate", query = "SELECT f FROM FinancialInstrumentRegister f WHERE f.reportingDate = :reportingDate")})
public class FinancialInstrumentRegister implements Serializable {

    private static final long serialVersionUID = 1L;
    //<editor-fold defaultstate="collapsed" desc="variable declaration">
    @Id
    @Basic(optional = false)
    @NotNull
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FINANCIAL_INSTRUMENT_REG_SEQ")
    @SequenceGenerator(name = "FINANCIAL_INSTRUMENT_REG_SEQ", sequenceName = "FINANCIAL_INSTRUMENT_REG_SEQ", allocationSize = 1)
    @Column(name = "REGISTER_ID", nullable = false, precision = 0, scale = -127)
    private BigDecimal registerId;
    @Size(max = 100)
    @Column(name = "ASSET_DESCRIPTION", length = 100)
    private String assetDescription;
    @Column(name = "ISSUED_DATE")
    @Temporal(TemporalType.DATE)
    private Date issuedDate;
    @Column(name = "CARRYNG_AMOUNT")
    private Double carryngAmount;
    @Column(name = "INTEREST_RATE")
    private Double interestRate;
    @Size(max = 20)
    @Column(name = "ASSET_STATUS", length = 20)
    private String assetStatus;
    @Column(name = "BOND_TYPE")
    private String BondType;
    @Column(name = "IMPAIRMENT_COST")
    private Double impairmentCost;
    @Column(name = "REPORTING_DATE")
    @Temporal(TemporalType.DATE)
    private Date reportingDate;
    @JoinColumn(name = "FMS_LU_FINANCIAL_ASSET_TYPE", referencedColumnName = "ASSET_TYPE_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private FmsLuFinancialAssetType financialAssetType;
    @JoinColumn(name = "FMS_LU_FINA_INSTRUMENT_MEASURE", referencedColumnName = "INST_MEASURE_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private FmsLuFinaInstrumentMeasure financialMeasurement;
    @JoinColumn(name = "FMS_LU_FINAN_INSTRUMENT_TYPE", referencedColumnName = "INST_TYPE_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private FmsLuFinanInstrumentType financialInstrumentType;
    @JoinColumn(name = "GENERAL_LEDGER", referencedColumnName = "GENERAL_LEDGER_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private FmsGeneralLedger generalLedger;
    @JoinColumn(name = "CURRENCY", referencedColumnName = "CURRENCY_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private FmsLuCurrency currency;
    @JoinColumn(name = "SUBSIDIARY_LEDGER", referencedColumnName = "SUBSIDIARY_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private FmsSubsidiaryLedger subsidiaryLedger;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "financialInstrumentId")
    private List<FinancialInstrumentDetail> financialInstrumentDetailList;
//</editor-fold>

    public FinancialInstrumentRegister() {
    }
//<editor-fold defaultstate="collapsed" desc="getter and setter">

    public FinancialInstrumentRegister(BigDecimal registerId) {
        this.registerId = registerId;
    }

    public BigDecimal getRegisterId() {
        return registerId;
    }

    public void setRegisterId(BigDecimal registerId) {
        this.registerId = registerId;
    }

    public String getAssetDescription() {
        return assetDescription;
    }

    public void setAssetDescription(String assetDescription) {
        this.assetDescription = assetDescription;
    }

    public Date getIssuedDate() {
        return issuedDate;
    }

    public void setIssuedDate(Date issuedDate) {
        this.issuedDate = issuedDate;
    }

    public Double getCarryngAmount() {
        return carryngAmount;
    }

    public void setCarryngAmount(Double carryngAmount) {
        this.carryngAmount = carryngAmount;
    }

    public Double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(Double interestRate) {
        this.interestRate = interestRate;
    }

    public Double getImpairmentCost() {
        return impairmentCost;
    }

    public void setImpairmentCost(Double impairmentCost) {
        this.impairmentCost = impairmentCost;
    }

    public String getAssetStatus() {
        return assetStatus;
    }

    public void setAssetStatus(String assetStatus) {
        this.assetStatus = assetStatus;
    }

    public String getBondType() {
        return BondType;
    }

    public void setBondType(String BondType) {
        this.BondType = BondType;
    }

    public Date getReportingDate() {
        return reportingDate;
    }

    public void setReportingDate(Date reportingDate) {
        this.reportingDate = reportingDate;
    }

    public FmsLuFinancialAssetType getFmsLuFinancialAssetType() {
        return financialAssetType;
    }

    public void setFmsLuFinancialAssetType(FmsLuFinancialAssetType financialAssetType) {
        this.financialAssetType = financialAssetType;
    }

    public FmsLuFinaInstrumentMeasure getFinancialMeasurement() {
        return financialMeasurement;
    }

    public void setFinancialMeasurement(FmsLuFinaInstrumentMeasure financialMeasurement) {
        this.financialMeasurement = financialMeasurement;
    }

    public FmsLuFinanInstrumentType getFmsLuFinanInstrumentType() {
        return financialInstrumentType;
    }

    public void setFmsLuFinanInstrumentType(FmsLuFinanInstrumentType financialInstrumentType) {
        this.financialInstrumentType = financialInstrumentType;
    }

    public FmsGeneralLedger getGeneralLedger() {
        return generalLedger;
    }

    public void setGeneralLedger(FmsGeneralLedger generalLedger) {
        this.generalLedger = generalLedger;
    }

    public FmsLuCurrency getCurrency() {
        return currency;
    }

    public void setCurrency(FmsLuCurrency currency) {
        this.currency = currency;
    }

    public FmsSubsidiaryLedger getSubsidiaryLedger() {
        return subsidiaryLedger;
    }

    public void setSubsidiaryLedger(FmsSubsidiaryLedger subsidiaryLedger) {
        this.subsidiaryLedger = subsidiaryLedger;
    }
//</editor-fold>

    @Transient
    private String columnValue;

    public String getColumnValue() {
        return columnValue;
    }

    public void setColumnValue(String columnValue) {
        this.columnValue = columnValue;
    }

    @XmlTransient
    public List<FinancialInstrumentDetail> getFinancialInstrumentDetailList() {
        if (financialInstrumentDetailList == null) {
            financialInstrumentDetailList = new ArrayList<>();
        }
        return financialInstrumentDetailList;
    }

    public void setFinancialInstrumentDetailList(List<FinancialInstrumentDetail> financialInstrumentDetailList) {
        this.financialInstrumentDetailList = financialInstrumentDetailList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (registerId != null ? registerId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof FinancialInstrumentRegister)) {
            return false;
        }
        FinancialInstrumentRegister other = (FinancialInstrumentRegister) object;
        if ((this.registerId == null && other.registerId != null) || (this.registerId != null && !this.registerId.equals(other.registerId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "et.gov.eep.fcms.entity.Ifrs.FinancialInstrumentRegister[ registerId=" + registerId + " ]";
    }

    public void addToFinanceInstrumentDetail(FinancialInstrumentDetail financialInstrumentDetail) {
        if (financialInstrumentDetail.getFinancialInstrumentId() != this) {
            this.getFinancialInstrumentDetailList().add(financialInstrumentDetail);
            financialInstrumentDetail.setFinancialInstrumentId(this);
        }
    }

}
