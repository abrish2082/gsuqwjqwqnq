/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.prms.entity;

import et.gov.eep.commonApplications.entity.PrmsLuDmArchive;
import et.gov.eep.commonApplications.entity.WfPrmsProcessed;
import et.gov.eep.fcms.entity.loan.FmsLoan;
import et.gov.eep.fcms.entity.FmsLuCurrency;
import et.gov.eep.hrms.entity.address.HrAddresses;
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
import javax.persistence.SequenceGenerator;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
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
@Table(name = "PRMS_FOREIGN_EXCHANGE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PrmsForeignExchange.findAll", query = "SELECT p FROM PrmsForeignExchange p"),
    @NamedQuery(name = "PrmsForeignExchange.findById", query = "SELECT p FROM PrmsForeignExchange p WHERE p.id = :id"),
    @NamedQuery(name = "PrmsForeignExchange.findByFeNumber", query = "SELECT p FROM PrmsForeignExchange p WHERE p.feNumber = :feNumber"),
    @NamedQuery(name = "PrmsForeignExchange.findByForeignExchReqForApproval", query = "SELECT p FROM PrmsForeignExchange p WHERE p.status=0"),
    @NamedQuery(name = "PrmsForeignExchange.findByFeNumberLike", query = "SELECT p FROM PrmsForeignExchange p WHERE p.feNumber LIKE :feNumber and p.preparedBy =:preparedBy"),
    @NamedQuery(name = "PrmsForeignExchange.InsertNextFE_No", query = "SELECT p FROM PrmsForeignExchange p WHERE p.id =(SELECT MAX(p.id)FROM PrmsForeignExchange p)"),

    @NamedQuery(name = "PrmsForeignExchange.findByRegion", query = "SELECT p FROM PrmsForeignExchange p WHERE p.region = :region"),
    @NamedQuery(name = "PrmsForeignExchange.findByKK", query = "SELECT p FROM PrmsForeignExchange p WHERE p.kK = :kK"),
    @NamedQuery(name = "PrmsForeignExchange.findByKebele", query = "SELECT p FROM PrmsForeignExchange p WHERE p.kebele = :kebele"),
    @NamedQuery(name = "PrmsForeignExchange.findByHNo", query = "SELECT p FROM PrmsForeignExchange p WHERE p.hNo = :hNo"),
    @NamedQuery(name = "PrmsForeignExchange.findByCity", query = "SELECT p FROM PrmsForeignExchange p WHERE p.city = :city"),
    @NamedQuery(name = "PrmsForeignExchange.findByTel", query = "SELECT p FROM PrmsForeignExchange p WHERE p.tel = :tel"),
    @NamedQuery(name = "PrmsForeignExchange.findByFax", query = "SELECT p FROM PrmsForeignExchange p WHERE p.fax = :fax"),
    @NamedQuery(name = "PrmsForeignExchange.findByPoBox", query = "SELECT p FROM PrmsForeignExchange p WHERE p.poBox = :poBox"),
    @NamedQuery(name = "PrmsForeignExchange.findByTradingLicenceNo", query = "SELECT p FROM PrmsForeignExchange p WHERE p.tradingLicenceNo = :tradingLicenceNo"),
    @NamedQuery(name = "PrmsForeignExchange.findByTinNo", query = "SELECT p FROM PrmsForeignExchange p WHERE p.tinNo = :tinNo"),
    @NamedQuery(name = "PrmsForeignExchange.findByAmount", query = "SELECT p FROM PrmsForeignExchange p WHERE p.amount = :amount"),
    @NamedQuery(name = "PrmsForeignExchange.findByFrightAmount", query = "SELECT p FROM PrmsForeignExchange p WHERE p.frightAmount = :frightAmount"),
    @NamedQuery(name = "PrmsForeignExchange.findByCandfAmount", query = "SELECT p FROM PrmsForeignExchange p WHERE p.candfAmount = :candfAmount"),
    @NamedQuery(name = "PrmsForeignExchange.findByFobBirr", query = "SELECT p FROM PrmsForeignExchange p WHERE p.fobBirr = :fobBirr"),
    @NamedQuery(name = "PrmsForeignExchange.findByFrightBirr", query = "SELECT p FROM PrmsForeignExchange p WHERE p.frightBirr = :frightBirr"),
    @NamedQuery(name = "PrmsForeignExchange.findByCandfBirr", query = "SELECT p FROM PrmsForeignExchange p WHERE p.candfBirr = :candfBirr"),
    @NamedQuery(name = "PrmsForeignExchange.findByClassSits", query = "SELECT p FROM PrmsForeignExchange p WHERE p.classSits = :classSits"),
    @NamedQuery(name = "PrmsForeignExchange.findByClassEu", query = "SELECT p FROM PrmsForeignExchange p WHERE p.classEu = :classEu"),
    @NamedQuery(name = "PrmsForeignExchange.findByClassEs", query = "SELECT p FROM PrmsForeignExchange p WHERE p.classEs = :classEs"),
    @NamedQuery(name = "PrmsForeignExchange.findByClassAs", query = "SELECT p FROM PrmsForeignExchange p WHERE p.classAs = :classAs"),
    @NamedQuery(name = "PrmsForeignExchange.findByRemark", query = "SELECT p FROM PrmsForeignExchange p WHERE p.remark = :remark"),
    @NamedQuery(name = "PrmsForeignExchange.findByAcNo", query = "SELECT p FROM PrmsForeignExchange p WHERE p.acNo = :acNo"),
    @NamedQuery(name = "PrmsForeignExchange.findByApplicationNo", query = "SELECT p FROM PrmsForeignExchange p WHERE p.applicationNo = :applicationNo"),
    @NamedQuery(name = "PrmsForeignExchange.findByShipmentAllowed", query = "SELECT p FROM PrmsForeignExchange p WHERE p.shipmentAllowed = :shipmentAllowed"),
    @NamedQuery(name = "PrmsForeignExchange.findByManufacturerInfo", query = "SELECT p FROM PrmsForeignExchange p WHERE p.manufacturerInfo = :manufacturerInfo"),
    @NamedQuery(name = "PrmsForeignExchange.findByCommodityBs", query = "SELECT p FROM PrmsForeignExchange p WHERE p.commodityBs = :commodityBs"),
    @NamedQuery(name = "PrmsForeignExchange.findByBp", query = "SELECT p FROM PrmsForeignExchange p WHERE p.bp = :bp"),
    @NamedQuery(name = "PrmsForeignExchange.findByPreparedBy", query = "SELECT p FROM PrmsForeignExchange p WHERE p.preparedBy = :preparedBy"),
    @NamedQuery(name = "PrmsForeignExchange.findByAudBy", query = "SELECT p FROM PrmsForeignExchange p WHERE p.audBy = :audBy"),
    @NamedQuery(name = "PrmsForeignExchange.findByPosBy", query = "SELECT p FROM PrmsForeignExchange p WHERE p.posBy = :posBy"),
    @NamedQuery(name = "PrmsForeignExchange.findByCommission", query = "SELECT p FROM PrmsForeignExchange p WHERE p.commission = :commission"),
    @NamedQuery(name = "PrmsForeignExchange.findByPercentage", query = "SELECT p FROM PrmsForeignExchange p WHERE p.percentage = :percentage"),
    @NamedQuery(name = "PrmsForeignExchange.findByBenefit", query = "SELECT p FROM PrmsForeignExchange p WHERE p.benefit = :benefit"),
    @NamedQuery(name = "PrmsForeignExchange.findByAgentName", query = "SELECT p FROM PrmsForeignExchange p WHERE p.agentName = :agentName"),
    @NamedQuery(name = "PrmsForeignExchange.findByLaonSource", query = "SELECT p FROM PrmsForeignExchange p WHERE p.laonSource = :laonSource"),
    @NamedQuery(name = "PrmsForeignExchange.findByMpandregNo", query = "SELECT p FROM PrmsForeignExchange p WHERE p.mpandregNo = :mpandregNo"),
    @NamedQuery(name = "PrmsForeignExchange.findByAutorizedOn", query = "SELECT p FROM PrmsForeignExchange p WHERE p.autorizedOn = :autorizedOn"),
    @NamedQuery(name = "PrmsForeignExchange.findByLoanDate", query = "SELECT p FROM PrmsForeignExchange p WHERE p.loanDate = :loanDate"),
    @NamedQuery(name = "PrmsForeignExchange.findByValidDate", query = "SELECT p FROM PrmsForeignExchange p WHERE p.validDate = :validDate"),
    @NamedQuery(name = "PrmsForeignExchange.findByApplicantName", query = "SELECT p FROM PrmsForeignExchange p WHERE p.applicantName = :applicantName"),
    @NamedQuery(name = "PrmsForeignExchange.findByFexFileRefnumber", query = "SELECT p FROM PrmsForeignExchange p WHERE p.fexFileRefnumber = :fexFileRefnumber"),
    @NamedQuery(name = "PrmsForeignExchange.findByPermitExpireDate", query = "SELECT p FROM PrmsForeignExchange p WHERE p.permitExpireDate = :permitExpireDate"),
    @NamedQuery(name = "PrmsForeignExchange.findByPermitAmount", query = "SELECT p FROM PrmsForeignExchange p WHERE p.permitAmount = :permitAmount"),
    @NamedQuery(name = "PrmsForeignExchange.findByIncoterm", query = "SELECT p FROM PrmsForeignExchange p WHERE p.incoterm=:incoterm"),
    @NamedQuery(name = "PrmsForeignExchange.findByStatus", query = "SELECT p FROM PrmsForeignExchange p WHERE p.status=:status")})
public class PrmsForeignExchange implements Serializable {
//    @Lob

    @Column(name = "FEX_FILE_REFNUMBER")
    private Integer fexFileRefnumber;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation

    @OneToMany(mappedBy = "forienId", cascade = CascadeType.ALL)
    private List<WfPrmsProcessed> wfPrmsProcessedLists;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "foreignAppId", fetch = FetchType.LAZY)
    private List<PrmsForeignExDetail2> prmsForeignExDetail2List;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "foreignAppId", fetch = FetchType.LAZY)
    private List<PrmsForeignExDetail1> prmsForeignExDetail1List;
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PRMS_FOREIGN_EXCHANGE_SEQ")
    @SequenceGenerator(sequenceName = "PRMS_FOREIGN_EXCHANGE_SEQ", name = "PRMS_FOREIGN_EXCHANGE_SEQ", allocationSize = 1)
    @Column(name = "ID", nullable = false, precision = 0, scale = -127)
    private BigDecimal id;
    @Size(max = 35)
    @Column(name = "FE_NUMBER", length = 35)
    private String feNumber;
    @Size(max = 35)
    @Column(name = "REGION", length = 35)
    private String region;
    @Size(max = 100)
    @Column(name = "K_K", length = 100)
    private String kK;
    @Size(max = 70)
    @Column(name = "KEBELE", length = 70)
    private String kebele;
    @Size(max = 50)
    @Column(name = "H_NO", length = 50)
    private String hNo;
    @Size(max = 50)
    @Column(name = "INCOTERM", length = 50)
    private String incoterm;
    @Size(max = 50)
    @Column(name = "CITY", length = 50)
    private String city;
    @Column(name = "TEL")
    private BigInteger tel;
    @Column(name = "FAX")
    private BigInteger fax;
    @Column(name = "PO_BOX")
    private BigInteger poBox;
    @Size(max = 60)
    @Column(name = "TRADING_LICENCE_NO", length = 60)
    private String tradingLicenceNo;
    @Size(max = 20)
    @Column(name = "TIN_NO", length = 20)
    private String tinNo;
//    @Size(max = 100)
    @Column(name = "AMOUNT", length = 100)
    private Float amount;
    @Size(max = 50)
    @Column(name = "FRIGHT_AMOUNT", length = 50)
    private String frightAmount;
    @Size(max = 100)
    @Column(name = "CANDF_AMOUNT", length = 100)
    private String candfAmount;
    @Size(max = 100)
    @Column(name = "FOB_BIRR", length = 100)
    private String fobBirr;
    @Size(max = 100)
    @Column(name = "FRIGHT_BIRR", length = 100)
    private String frightBirr;
    @Size(max = 35)
    @Column(name = "CANDF_BIRR", length = 35)
    private String candfBirr;
    @Size(max = 50)
    @Column(name = "CLASS_SITS", length = 50)
    private String classSits;
    @Size(max = 50)
    @Column(name = "CLASS_EU", length = 50)
    private String classEu;
    @Size(max = 50)
    @Column(name = "CLASS_ES", length = 50)
    private String classEs;
    @Size(max = 50)
    @Column(name = "CLASS_AS", length = 50)
    private String classAs;
    @Size(max = 200)
    @Column(name = "REMARK", length = 200)
    private String remark;
    @Size(max = 50)
    @Column(name = "AC_NO", length = 50)
    private String acNo;
    @Size(max = 50)
    @Column(name = "APPLICATION_NO", length = 50)
    private String applicationNo;
    @Size(max = 100)
    @Column(name = "SHIPMENT_ALLOWED", length = 100)
    private String shipmentAllowed;
    @Size(max = 100)
    @Column(name = "MANUFACTURER_INFO", length = 100)
    private String manufacturerInfo;
    @Size(max = 100)
    @Column(name = "COMMODITY_BS", length = 100)
    private String commodityBs;
    @Size(max = 50)
    @Column(name = "BP", length = 50)
    private String bp;

    @Column(name = "PREPARED_BY")
    private Integer preparedBy;
    @Size(max = 50)
    @Column(name = "AUD_BY", length = 50)
    private String audBy;
    @Size(max = 50)
    @Column(name = "POS_BY", length = 50)
    private String posBy;
    @Size(max = 100)
    @Column(name = "COMMISSION", length = 100)
    private String commission;
    @Size(max = 50)
    @Column(name = "PERCENTAGE", length = 50)
    private String percentage;
    @Size(max = 100)
    @Column(name = "BENEFIT", length = 100)
    private String benefit;
    @Size(max = 50)
    @Column(name = "AGENT_NAME", length = 50)
    private String agentName;
    @Size(max = 50)
    @Column(name = "LAON_SOURCE", length = 50)
    private String laonSource;
    @Size(max = 20)
    @Column(name = "MPANDREG_NO", length = 20)
    private String mpandregNo;
    @Column(name = "AUTORIZED_ON")
    @Temporal(TemporalType.TIMESTAMP)
    private Date autorizedOn;
    @Column(name = "LOAN_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date loanDate;
    @Column(name = "VALID_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date validDate;
    @Size(max = 50)
    @Column(name = "APPLICANT_NAME", length = 50)
    private String applicantName;

    @Column(name = "PERMIT_EXPIRE_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date permitExpireDate;
    @Column(name = "PERMIT_AMOUNT")
    private Double permitAmount;
    @Column(name = "STATUS")
    private Integer status;
    @JoinColumn(name = "FRIGHT_CURRENCY", referencedColumnName = "CURRENCY_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private FmsLuCurrency frightCurrency;
    @JoinColumn(name = "METHOD_OF_PAYMENT", referencedColumnName = "LOAN_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private FmsLoan methodOfPayment;
    @JoinColumn(name = "LOAN_NO", referencedColumnName = "LOAN_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private FmsLoan loanNo;
    @JoinColumn(name = "FOB_CURRENCY", referencedColumnName = "CURRENCY_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private FmsLuCurrency fobCurrency;
    @JoinColumn(name = "CANDF_CURRENCY", referencedColumnName = "CURRENCY_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private FmsLuCurrency candfCurrency;
    @JoinColumn(name = "CURRENCY", referencedColumnName = "CURRENCY_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private FmsLuCurrency currency;
    @JoinColumn(name = "COUNTRY", referencedColumnName = "ADDRESS_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private HrAddresses country;
    @JoinColumn(name = "COUNTRYOF_ORIGIN", referencedColumnName = "ADDRESS_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private HrAddresses countryofOrigin;
    @JoinColumn(name = "PORT_ID", referencedColumnName = "PORT_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private PrmsPortEntry portId;
    @JoinColumn(name = "BANK_ID", referencedColumnName = "SERVICE_DT_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private PrmsServiceProviderDetail bankId;
    @JoinColumn(name = "SUPP_ID", referencedColumnName = "ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private PrmsSupplyProfile suppId;
    @JoinColumn(name = "DOCUMENT_ID", referencedColumnName = "DOCUMENT_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private PrmsLuDmArchive documentId;
    @Transient
    private String columnName;
    @Transient
    private String columnValue;

    public PrmsForeignExchange() {
    }

    public PrmsForeignExchange(BigDecimal id) {
        this.id = id;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
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

    public String getFeNumber() {
        return feNumber;
    }

    public void setFeNumber(String feNumber) {
        this.feNumber = feNumber;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getKK() {
        return kK;
    }

    public void setKK(String kK) {
        this.kK = kK;
    }

    public String getKebele() {
        return kebele;
    }

    public void setKebele(String kebele) {
        this.kebele = kebele;
    }

    public String getHNo() {
        return hNo;
    }

    public void setHNo(String hNo) {
        this.hNo = hNo;
    }

    public String getIncoterm() {
        return incoterm;
    }

    public void setIncoterm(String incoterm) {
        this.incoterm = incoterm;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public BigInteger getTel() {
        return tel;
    }

    public void setTel(BigInteger tel) {
        this.tel = tel;
    }

    public BigInteger getFax() {
        return fax;
    }

    public void setFax(BigInteger fax) {
        this.fax = fax;
    }

    public BigInteger getPoBox() {
        return poBox;
    }

    public void setPoBox(BigInteger poBox) {
        this.poBox = poBox;
    }

    public String getTradingLicenceNo() {
        return tradingLicenceNo;
    }

    public void setTradingLicenceNo(String tradingLicenceNo) {
        this.tradingLicenceNo = tradingLicenceNo;
    }

    public String getTinNo() {
        return tinNo;
    }

    public void setTinNo(String tinNo) {
        this.tinNo = tinNo;
    }

    public Float getAmount() {
        return amount;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    public String getFrightAmount() {
        return frightAmount;
    }

    public void setFrightAmount(String frightAmount) {
        this.frightAmount = frightAmount;
    }

    public String getCandfAmount() {
        return candfAmount;
    }

    public void setCandfAmount(String candfAmount) {
        this.candfAmount = candfAmount;
    }

    public String getFobBirr() {
        return fobBirr;
    }

    public void setFobBirr(String fobBirr) {
        this.fobBirr = fobBirr;
    }

    public String getFrightBirr() {
        return frightBirr;
    }

    public void setFrightBirr(String frightBirr) {
        this.frightBirr = frightBirr;
    }

    public String getCandfBirr() {
        return candfBirr;
    }

    public void setCandfBirr(String candfBirr) {
        this.candfBirr = candfBirr;
    }

    public String getClassSits() {
        return classSits;
    }

    public void setClassSits(String classSits) {
        this.classSits = classSits;
    }

    public String getClassEu() {
        return classEu;
    }

    public void setClassEu(String classEu) {
        this.classEu = classEu;
    }

    public String getClassEs() {
        return classEs;
    }

    public void setClassEs(String classEs) {
        this.classEs = classEs;
    }

    public String getClassAs() {
        return classAs;
    }

    public void setClassAs(String classAs) {
        this.classAs = classAs;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getAcNo() {
        return acNo;
    }

    public void setAcNo(String acNo) {
        this.acNo = acNo;
    }

    public String getApplicationNo() {
        return applicationNo;
    }

    public void setApplicationNo(String applicationNo) {
        this.applicationNo = applicationNo;
    }

    public String getShipmentAllowed() {
        return shipmentAllowed;
    }

    public void setShipmentAllowed(String shipmentAllowed) {
        this.shipmentAllowed = shipmentAllowed;
    }

    public String getManufacturerInfo() {
        return manufacturerInfo;
    }

    public void setManufacturerInfo(String manufacturerInfo) {
        this.manufacturerInfo = manufacturerInfo;
    }

    public String getCommodityBs() {
        return commodityBs;
    }

    public void setCommodityBs(String commodityBs) {
        this.commodityBs = commodityBs;
    }

    public String getBp() {
        return bp;
    }

    public void setBp(String bp) {
        this.bp = bp;
    }

    public Integer getPreparedBy() {
        return preparedBy;
    }

    public void setPreparedBy(Integer preparedBy) {
        this.preparedBy = preparedBy;
    }

    public String getAudBy() {
        return audBy;
    }

    public void setAudBy(String audBy) {
        this.audBy = audBy;
    }

    public String getPosBy() {
        return posBy;
    }

    public void setPosBy(String posBy) {
        this.posBy = posBy;
    }

    public String getCommission() {
        return commission;
    }

    public void setCommission(String commission) {
        this.commission = commission;
    }

    public String getPercentage() {
        return percentage;
    }

    public void setPercentage(String percentage) {
        this.percentage = percentage;
    }

    public String getBenefit() {
        return benefit;
    }

    public void setBenefit(String benefit) {
        this.benefit = benefit;
    }

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    public String getLaonSource() {
        return laonSource;
    }

    public void setLaonSource(String laonSource) {
        this.laonSource = laonSource;
    }

    public String getMpandregNo() {
        return mpandregNo;
    }

    public void setMpandregNo(String mpandregNo) {
        this.mpandregNo = mpandregNo;
    }

    public Date getAutorizedOn() {
        return autorizedOn;
    }

    public void setAutorizedOn(Date autorizedOn) {
        this.autorizedOn = autorizedOn;
    }

    public Date getLoanDate() {
        return loanDate;
    }

    public void setLoanDate(Date loanDate) {
        this.loanDate = loanDate;
    }

    public Date getValidDate() {
        return validDate;
    }

    public void setValidDate(Date validDate) {
        this.validDate = validDate;
    }

    public String getApplicantName() {
        return applicantName;
    }

    public void setApplicantName(String applicantName) {
        this.applicantName = applicantName;
    }

    public Date getPermitExpireDate() {
        return permitExpireDate;
    }

    public void setPermitExpireDate(Date permitExpireDate) {
        this.permitExpireDate = permitExpireDate;
    }

    public Double getPermitAmount() {
        return permitAmount;
    }

    public void setPermitAmount(Double permitAmount) {
        this.permitAmount = permitAmount;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public FmsLuCurrency getFrightCurrency() {
        return frightCurrency;
    }

    public void setFrightCurrency(FmsLuCurrency frightCurrency) {
        this.frightCurrency = frightCurrency;
    }

    public FmsLoan getMethodOfPayment() {
        return methodOfPayment;
    }

    public void setMethodOfPayment(FmsLoan methodOfPayment) {
        this.methodOfPayment = methodOfPayment;
    }

    public FmsLoan getLoanNo() {
        return loanNo;
    }

    public void setLoanNo(FmsLoan loanNo) {
        this.loanNo = loanNo;
    }

    public FmsLuCurrency getCandfCurrency() {
        return candfCurrency;
    }

    public void setCandfCurrency(FmsLuCurrency candfCurrency) {
        this.candfCurrency = candfCurrency;
    }

    public FmsLuCurrency getCurrency() {
        return currency;
    }

    public void setCurrency(FmsLuCurrency currency) {
        this.currency = currency;
    }

    public HrAddresses getCountry() {
        return country;
    }

    public void setCountry(HrAddresses country) {
        this.country = country;
    }

    public HrAddresses getCountryofOrigin() {
        return countryofOrigin;
    }

    public void setCountryofOrigin(HrAddresses countryofOrigin) {
        this.countryofOrigin = countryofOrigin;
    }

    public PrmsPortEntry getPortId() {
        return portId;
    }

    public void setPortId(PrmsPortEntry portId) {
        this.portId = portId;
    }

    public PrmsServiceProviderDetail getBankId() {
        return bankId;
    }

    public void setBankId(PrmsServiceProviderDetail bankId) {
        this.bankId = bankId;
    }

    public PrmsSupplyProfile getSuppId() {
        return suppId;
    }

    public void setSuppId(PrmsSupplyProfile suppId) {
        this.suppId = suppId;
    }

    public PrmsLuDmArchive getDocumentId() {
        return documentId;
    }

    public void setDocumentId(PrmsLuDmArchive documentId) {
        this.documentId = documentId;
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
        if (!(object instanceof PrmsForeignExchange)) {
            return false;
        }
        PrmsForeignExchange other = (PrmsForeignExchange) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "et.gov.eep.prms.entity.PrmsForeignExchange[ id=" + id + " ]";
    }

    @XmlTransient
    public List<PrmsForeignExDetail1> getPrmsForeignExDetail1List() {
        if (prmsForeignExDetail1List == null) {
            prmsForeignExDetail1List = new ArrayList<>();
        }
        return prmsForeignExDetail1List;
    }

    public void setPrmsForeignExDetail1List(List<PrmsForeignExDetail1> prmsForeignExDetail1List) {
        this.prmsForeignExDetail1List = prmsForeignExDetail1List;
    }

    @XmlTransient
    public List<PrmsForeignExDetail2> getPrmsForeignExDetail2List() {
        if (prmsForeignExDetail2List == null) {
            prmsForeignExDetail2List = new ArrayList<>();
        }
        return prmsForeignExDetail2List;
    }

    public void setPrmsForeignExDetail2List(List<PrmsForeignExDetail2> prmsForeignExDetail2List) {
        this.prmsForeignExDetail2List = prmsForeignExDetail2List;
    }

    public void addForeignExDetail1(PrmsForeignExDetail1 prmsForeignExDetail1) {
        if (prmsForeignExDetail1.getForeignAppId() != this) {
            this.getPrmsForeignExDetail1List().add(prmsForeignExDetail1);
            prmsForeignExDetail1.setForeignAppId(this);

        }
    }

    public void addForeignExDetail2(PrmsForeignExDetail2 prmsForeignExDetail2) {
        if (prmsForeignExDetail2.getForeignAppId() != this) {
            this.getPrmsForeignExDetail2List().add(prmsForeignExDetail2);
            prmsForeignExDetail2.setForeignAppId(this);
        }
    }

    public Integer getFexFileRefnumber() {
        return fexFileRefnumber;
    }

    public void setFexFileRefnumber(Integer fexFileRefnumber) {
        this.fexFileRefnumber = fexFileRefnumber;
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
