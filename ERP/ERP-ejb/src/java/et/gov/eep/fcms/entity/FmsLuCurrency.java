/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.entity;

import et.gov.eep.fcms.entity.Bond.FmsBondForeign;
import et.gov.eep.fcms.entity.Bond.FmsBondLibor;
import et.gov.eep.fcms.entity.Ifrs.FinancialInstrumentRegister;
import et.gov.eep.fcms.entity.loan.FmsLoan;
import et.gov.eep.prms.entity.PrmsAward;
import et.gov.eep.prms.entity.PrmsBidOpeningChecklstDt;
import et.gov.eep.prms.entity.PrmsForeignExchange;
import et.gov.eep.prms.entity.PrmsMarketAssessmentDetail;
import et.gov.eep.prms.entity.PrmsInsuranceRequisition;
import et.gov.eep.prms.entity.PrmsBid;
import et.gov.eep.prms.entity.PrmsCheckListDetailforlot;
import et.gov.eep.prms.entity.PrmsContractAmendment;
import et.gov.eep.prms.entity.PrmsFinancialEvaluaDetail;
import et.gov.eep.prms.entity.PrmsPoCurrency;
import et.gov.eep.prms.entity.PrmsRevenueContarct;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author user
 */
@Entity
@Table(name = "FMS_LU_CURRENCY", catalog = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FmsLuCurrency.findAll", query = "SELECT f FROM FmsLuCurrency f"),
    @NamedQuery(name = "FmsLuCurrency.findByCurrencyId", query = "SELECT f FROM FmsLuCurrency f WHERE f.currencyId = :currencyId"),
    @NamedQuery(name = "FmsLuCurrency.findByCurrency", query = "SELECT f FROM FmsLuCurrency f WHERE f.currency = :currency ORDER BY f.currency ASC"),
    @NamedQuery(name = "FmsLuCurrency.searchByCurrencyNameOrder", query = "SELECT f FROM FmsLuCurrency f ORDER BY f.currency ASC"),
    @NamedQuery(name = "FmsLuCurrency.findByNameLike", query = "SELECT f FROM FmsLuCurrency f WHERE f.currency LIKE :currency"),

    @NamedQuery(name = "FmsLuCurrency.findByCountry", query = "SELECT f FROM FmsLuCurrency f WHERE f.country = :country"),
    @NamedQuery(name = "FmsLuCurrency.findByXrate", query = "SELECT f FROM FmsLuCurrency f WHERE f.xrate = :xrate"),
    @NamedQuery(name = "FmsLuCurrency.findByDescription", query = "SELECT f FROM FmsLuCurrency f WHERE f.description = :description")})
public class FmsLuCurrency implements Serializable {

//    private List<FmsBondLibor> pmsbondLuborsList;
//    @OneToMany(mappedBy = "curencyId", cascade = CascadeType.ALL)
    @Column(name = "XRATE")
    private BigDecimal xrate;
    @OneToMany(mappedBy = "currencyId", fetch = FetchType.LAZY)
    private List<PrmsPoCurrency> prmsPoCurrencyList;
//    @OneToMany(mappedBy = "currencyId")
//    private List<PmsCreateProjects> pmsCreateProjectsList;
    @OneToMany(mappedBy = "curencyId")
    private Collection<FmsDailyExchangeRateReg> fmsDailyExchangeRateRegCollection;
    @OneToMany(mappedBy = "currencyId")
    private List<PrmsFinancialEvaluaDetail> prmsFinancialEvaluaDetailList;
    @OneToMany(mappedBy = "frightCurrency", fetch = FetchType.LAZY)
    private List<PrmsForeignExchange> prmsForeignExchangeList;
    @OneToMany(mappedBy = "fobCurrency", fetch = FetchType.LAZY)
    private List<PrmsForeignExchange> prmsForeignExchangeList1;
    @OneToMany(mappedBy = "candfCurrency", fetch = FetchType.LAZY)
    private List<PrmsForeignExchange> prmsForeignExchangeList2;
    @OneToMany(mappedBy = "currency", fetch = FetchType.LAZY)
    private List<PrmsForeignExchange> prmsForeignExchangeList3;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fmsLuCurrency")
    private List<FmsBondForeign> fmsBondForeignCollection;

    @OneToMany(mappedBy = "currencyId")
    private List<PrmsMarketAssessmentDetail> prmsMarketAssessmentDetailList;
    @OneToMany(mappedBy = "currencyName", fetch = FetchType.LAZY)
    private List<PrmsInsuranceRequisition> prmsInsuranceRequisitionList;
    @OneToMany(mappedBy = "currencyId", fetch = FetchType.LAZY)
    private List<PrmsBid> prmsBidList;
    @OneToMany(mappedBy = "currencyId", fetch = FetchType.LAZY)
    private List<PrmsBidOpeningChecklstDt> prmsBidOpeningChecklstDts;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "discCurrencyId", fetch = FetchType.LAZY)
    private List<PrmsBidOpeningChecklstDt> prmsBidOpeningChecklstDtsForDS;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "bidSecCurrency", fetch = FetchType.LAZY)
    private List<PrmsBidOpeningChecklstDt> prmsBidOpeningChecklstDtsForBIdSec;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "currencyId", fetch = FetchType.LAZY)
    private List<PrmsCheckListDetailforlot> checkListDetailforlots;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "currencyId", fetch = FetchType.LAZY)
    private List<PrmsAward> prmsAwardsList;
    @OneToMany(mappedBy = "currencyId", fetch = FetchType.LAZY)
    private List<PrmsRevenueContarct> prmsRevenueContarctList;
    @OneToMany(mappedBy = "fmsLuCurrency")
    private List<FmsBondLibor> fmsBondLiborList;
    @OneToMany(mappedBy = "currency")
    private List<FinancialInstrumentRegister> FinancialInstrumentRegisterList;
    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @NotNull
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FMS_LU_CURRENCY_SEQ")
    @SequenceGenerator(name = "FMS_LU_CURRENCY_SEQ", sequenceName = "FMS_LU_CURRENCY_SEQ", allocationSize = 1)
    @Size(min = 1, max = 20)
    @Column(name = "CURRENCY_ID", nullable = false, length = 20)
    private String currencyId;
    @Size(max = 20)
    @Column(name = "CURRENCY", length = 20)
    private String currency;
    @Size(max = 20)
    @Column(name = "COUNTRY", length = 20)
    private String country;
    @Size(max = 50)
    @Column(name = "DESCRIPTION", length = 50)
    private String description;
    @OneToMany(mappedBy = "currency")
    private List<PrmsContractAmendment> prmsContractAmendmentList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "currencyId", fetch = FetchType.LAZY)
    private List<FmsLoan> FmsLoanList;

    public FmsLuCurrency() {
    }

    public FmsLuCurrency(String currencyId) {
        this.currencyId = currencyId;
    }

    public String getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(String currencyId) {
        this.currencyId = currencyId;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (currencyId != null ? currencyId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FmsLuCurrency)) {
            return false;
        }
        FmsLuCurrency other = (FmsLuCurrency) object;
        if ((this.currencyId == null && other.currencyId != null) || (this.currencyId != null && !this.currencyId.equals(other.currencyId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return currency;
    }

    @XmlTransient
    public List<PrmsInsuranceRequisition> getPrmsInsuranceRequisitionList() {
        if (prmsInsuranceRequisitionList == null) {
            prmsInsuranceRequisitionList = new ArrayList<>();
        }
        return prmsInsuranceRequisitionList;
    }

    public void setPrmsInsuranceRequisitionList(List<PrmsInsuranceRequisition> prmsInsuranceRequisitionList) {
        this.prmsInsuranceRequisitionList = prmsInsuranceRequisitionList;
    }

    @XmlTransient
    public List<FmsBondForeign> getFmsBondForeignCollection() {
        return fmsBondForeignCollection;
    }

    @XmlTransient
    public void setFmsBondForeignCollection(List<FmsBondForeign> fmsBondForeignCollection) {
        this.fmsBondForeignCollection = fmsBondForeignCollection;
    }

    @XmlTransient
    public List<FinancialInstrumentRegister> getFinancialInstrumentRegisterList() {
        if (FinancialInstrumentRegisterList == null) {
            FinancialInstrumentRegisterList = new ArrayList<>();
        }
        return FinancialInstrumentRegisterList;
    }

    @XmlTransient
    public List<FmsBondLibor> getFmsBondLiborList() {
        if (fmsBondLiborList == null) {
            fmsBondLiborList = new ArrayList<>();
        }
        return fmsBondLiborList;
    }

    public void setFmsBondLiborList(List<FmsBondLibor> fmsBondLiborList) {
        this.fmsBondLiborList = fmsBondLiborList;
    }

    @XmlTransient

    public List<PrmsMarketAssessmentDetail> getPrmsMarketAssessmentDetailList() {
        return prmsMarketAssessmentDetailList;
    }

    public void setPrmsMarketAssessmentDetailList(List<PrmsMarketAssessmentDetail> prmsMarketAssessmentDetailList) {
        this.prmsMarketAssessmentDetailList = prmsMarketAssessmentDetailList;
    }

    @XmlTransient
    public List<PrmsForeignExchange> getPrmsForeignExchangeList() {
        return prmsForeignExchangeList;
    }

    public void setPrmsForeignExchangeList(List<PrmsForeignExchange> prmsForeignExchangeList) {
        this.prmsForeignExchangeList = prmsForeignExchangeList;
    }

    @XmlTransient
    public List<PrmsForeignExchange> getPrmsForeignExchangeList1() {
        return prmsForeignExchangeList1;
    }

    public void setPrmsForeignExchangeList1(List<PrmsForeignExchange> prmsForeignExchangeList1) {
        this.prmsForeignExchangeList1 = prmsForeignExchangeList1;
    }

    @XmlTransient
    public List<PrmsForeignExchange> getPrmsForeignExchangeList2() {
        return prmsForeignExchangeList2;
    }

    public void setPrmsForeignExchangeList2(List<PrmsForeignExchange> prmsForeignExchangeList2) {
        this.prmsForeignExchangeList2 = prmsForeignExchangeList2;
    }

    @XmlTransient
    public List<FmsLoan> getFmsLoanList() {
        return FmsLoanList;
    }

    public void setFmsLoanList(List<FmsLoan> FmsLoanList) {
        this.FmsLoanList = FmsLoanList;
    }

    @XmlTransient
    public List<PrmsForeignExchange> getPrmsForeignExchangeList3() {
        return prmsForeignExchangeList3;
    }

    public void setPrmsForeignExchangeList3(List<PrmsForeignExchange> prmsForeignExchangeList3) {
        this.prmsForeignExchangeList3 = prmsForeignExchangeList3;
    }

    @XmlTransient

    public List<PrmsBid> getPrmsBidList() {
        return prmsBidList;
    }

    public void setPrmsBidList(List<PrmsBid> prmsBidList) {
        this.prmsBidList = prmsBidList;
    }

    @XmlTransient
    public List<PrmsBidOpeningChecklstDt> getPrmsBidOpeningChecklstDts() {
        return prmsBidOpeningChecklstDts;
    }

    public void setPrmsBidOpeningChecklstDts(List<PrmsBidOpeningChecklstDt> prmsBidOpeningChecklstDts) {
        this.prmsBidOpeningChecklstDts = prmsBidOpeningChecklstDts;
    }

    @XmlTransient

    public List<PrmsCheckListDetailforlot> getCheckListDetailforlots() {
        return checkListDetailforlots;
    }

    public void setCheckListDetailforlots(List<PrmsCheckListDetailforlot> checkListDetailforlots) {
        this.checkListDetailforlots = checkListDetailforlots;
    }

    @XmlTransient

    public List<PrmsBidOpeningChecklstDt> getPrmsBidOpeningChecklstDtsForDS() {
        if (prmsBidOpeningChecklstDtsForDS == null) {
            prmsBidOpeningChecklstDtsForDS = new ArrayList<>();
        }

        return prmsBidOpeningChecklstDtsForDS;
    }

    public void setPrmsBidOpeningChecklstDtsForDS(List<PrmsBidOpeningChecklstDt> prmsBidOpeningChecklstDtsForDS) {
        this.prmsBidOpeningChecklstDtsForDS = prmsBidOpeningChecklstDtsForDS;
    }

    @XmlTransient

    public List<PrmsBidOpeningChecklstDt> getPrmsBidOpeningChecklstDtsForBIdSec() {
        if (prmsBidOpeningChecklstDtsForBIdSec == null) {
            prmsBidOpeningChecklstDtsForBIdSec = new ArrayList<>();
        }
        return prmsBidOpeningChecklstDtsForBIdSec;
    }

    public void setPrmsBidOpeningChecklstDtsForBIdSec(List<PrmsBidOpeningChecklstDt> prmsBidOpeningChecklstDtsForBIdSec) {
        this.prmsBidOpeningChecklstDtsForBIdSec = prmsBidOpeningChecklstDtsForBIdSec;
    }

    @XmlTransient

    public List<PrmsAward> getPrmsAwardsList() {
        if (prmsAwardsList == null) {
            prmsAwardsList = new ArrayList<>();
        }
        return prmsAwardsList;
    }

    public void setPrmsAwardsList(List<PrmsAward> prmsAwardsList) {
        this.prmsAwardsList = prmsAwardsList;
    }

    @XmlTransient
    public Collection<FmsDailyExchangeRateReg> getFmsDailyExchangeRateRegCollection() {
        return fmsDailyExchangeRateRegCollection;
    }

    @XmlTransient

    public List<PrmsContractAmendment> getPrmsContractAmendmentList() {
        return prmsContractAmendmentList;
    }

    public void setPrmsContractAmendmentList(List<PrmsContractAmendment> prmsContractAmendmentList) {
        this.prmsContractAmendmentList = prmsContractAmendmentList;
    }

    public void setFmsDailyExchangeRateRegCollection(Collection<FmsDailyExchangeRateReg> fmsDailyExchangeRateRegCollection) {
        this.fmsDailyExchangeRateRegCollection = fmsDailyExchangeRateRegCollection;
    }

//    @XmlTransient
//    public List<PmsCreateProjects> getPmsCreateProjectsList() {
//        return pmsCreateProjectsList;
//    }
//
//    public void setPmsCreateProjectsList(List<PmsCreateProjects> pmsCreateProjectsList) {
//        this.pmsCreateProjectsList = pmsCreateProjectsList;
//    }
    @XmlTransient
    public List<PrmsRevenueContarct> getPrmsRevenueContarctList() {
        if (prmsRevenueContarctList == null) {
            prmsRevenueContarctList = new ArrayList<>();
        }
        return prmsRevenueContarctList;
    }

    public void setPrmsRevenueContarctList(List<PrmsRevenueContarct> prmsRevenueContarctList) {
        this.prmsRevenueContarctList = prmsRevenueContarctList;
    }

    @XmlTransient

    public List<PrmsFinancialEvaluaDetail> getPrmsFinancialEvaluaDetailList() {
        return prmsFinancialEvaluaDetailList;
    }

    public void setPrmsFinancialEvaluaDetailList(List<PrmsFinancialEvaluaDetail> prmsFinancialEvaluaDetailList) {
        this.prmsFinancialEvaluaDetailList = prmsFinancialEvaluaDetailList;
    }

    @XmlTransient
    public List<PrmsPoCurrency> getPrmsPoCurrencyList() {
        if (prmsPoCurrencyList == null) {
            prmsPoCurrencyList = new ArrayList<>();
        }
        return prmsPoCurrencyList;
    }

    public void setPrmsPoCurrencyList(List<PrmsPoCurrency> prmsPoCurrencyList) {
        this.prmsPoCurrencyList = prmsPoCurrencyList;
    }

    public BigDecimal getXrate() {
        return xrate;
    }

    public void setXrate(BigDecimal xrate) {
        this.xrate = xrate;
    }

}
