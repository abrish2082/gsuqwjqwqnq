/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.prms.entity;

import java.io.Serializable;
import java.math.BigDecimal;
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
 * @author user
 */
@Entity
@Table(name = "PRMS_LC_APPLICATION")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PrmsLcApplication.findAll", query = "SELECT p FROM PrmsLcApplication p"),
    @NamedQuery(name = "PrmsLcApplication.findByLcAppNo", query = "SELECT p FROM PrmsLcApplication p WHERE p.lcAppNo = :lcAppNo"),
    @NamedQuery(name = "PrmsLcApplication.findByLcAppNos", query = "SELECT p FROM PrmsLcApplication p WHERE p.lcAppNo LIKE :lcAppNo"),
    @NamedQuery(name = "PrmsLcApplication.findByTo", query = "SELECT p FROM PrmsLcApplication p WHERE p.to = :to"),
    @NamedQuery(name = "PrmsLcApplication.findByApplicantName", query = "SELECT p FROM PrmsLcApplication p WHERE p.applicantName = :applicantName"),
    @NamedQuery(name = "PrmsLcApplication.findByApplicantAddress", query = "SELECT p FROM PrmsLcApplication p WHERE p.applicantAddress = :applicantAddress"),
    @NamedQuery(name = "PrmsLcApplication.findByLicenseNo", query = "SELECT p FROM PrmsLcApplication p WHERE p.licenseNo = :licenseNo"),
    @NamedQuery(name = "PrmsLcApplication.findByLicenseType", query = "SELECT p FROM PrmsLcApplication p WHERE p.licenseType = :licenseType"),
    @NamedQuery(name = "PrmsLcApplication.findByTinNo", query = "SELECT p FROM PrmsLcApplication p WHERE p.tinNo = :tinNo"),
    @NamedQuery(name = "PrmsLcApplication.findBySourceOfFund", query = "SELECT p FROM PrmsLcApplication p WHERE p.sourceOfFund = :sourceOfFund"),
    @NamedQuery(name = "PrmsLcApplication.findByLoanNo", query = "SELECT p FROM PrmsLcApplication p WHERE p.loanNo = :loanNo"),
    @NamedQuery(name = "PrmsLcApplication.findByNbeAccountNo", query = "SELECT p FROM PrmsLcApplication p WHERE p.nbeAccountNo = :nbeAccountNo"),
    @NamedQuery(name = "PrmsLcApplication.findByLcType", query = "SELECT p FROM PrmsLcApplication p WHERE p.lcType = :lcType"),
    @NamedQuery(name = "PrmsLcApplication.findByAmountInFigure", query = "SELECT p FROM PrmsLcApplication p WHERE p.amountInFigure = :amountInFigure"),
    @NamedQuery(name = "PrmsLcApplication.findByAmountInWords", query = "SELECT p FROM PrmsLcApplication p WHERE p.amountInWords = :amountInWords"),
    @NamedQuery(name = "PrmsLcApplication.findByDeliveryTerm", query = "SELECT p FROM PrmsLcApplication p WHERE p.deliveryTerm = :deliveryTerm"),
    @NamedQuery(name = "PrmsLcApplication.findByShipmentDate", query = "SELECT p FROM PrmsLcApplication p WHERE p.shipmentDate = :shipmentDate"),
    @NamedQuery(name = "PrmsLcApplication.findByExpiryDate", query = "SELECT p FROM PrmsLcApplication p WHERE p.expiryDate = :expiryDate"),
    @NamedQuery(name = "PrmsLcApplication.findByApplicationDate", query = "SELECT p FROM PrmsLcApplication p WHERE p.applicationDate = :applicationDate"),
    @NamedQuery(name = "PrmsLcApplication.findBySupplierName", query = "SELECT p FROM PrmsLcApplication p WHERE p.supplierName = :supplierName"),
    @NamedQuery(name = "PrmsLcApplication.findByAdvisingBank", query = "SELECT p FROM PrmsLcApplication p WHERE p.advisingBank = :advisingBank"),
    @NamedQuery(name = "PrmsLcApplication.findByShipmentFrom", query = "SELECT p FROM PrmsLcApplication p WHERE p.shipmentFrom = :shipmentFrom"),
    @NamedQuery(name = "PrmsLcApplication.findByShipmentTo", query = "SELECT p FROM PrmsLcApplication p WHERE p.shipmentTo = :shipmentTo"),
    @NamedQuery(name = "PrmsLcApplication.findByPartialShipment", query = "SELECT p FROM PrmsLcApplication p WHERE p.partialShipment = :partialShipment"),
    @NamedQuery(name = "PrmsLcApplication.findByTransShipment", query = "SELECT p FROM PrmsLcApplication p WHERE p.transShipment = :transShipment"),
    @NamedQuery(name = "PrmsLcApplication.findByShipmentToBeEffecBy", query = "SELECT p FROM PrmsLcApplication p WHERE p.shipmentToBeEffecBy = :shipmentToBeEffecBy"),
    @NamedQuery(name = "PrmsLcApplication.findByCountryOrigineOfGoods", query = "SELECT p FROM PrmsLcApplication p WHERE p.countryOrigineOfGoods = :countryOrigineOfGoods"),
    @NamedQuery(name = "PrmsLcApplication.findByPackageToBeMarked", query = "SELECT p FROM PrmsLcApplication p WHERE p.packageToBeMarked = :packageToBeMarked"),
    @NamedQuery(name = "PrmsLcApplication.findByBillOfExchange", query = "SELECT p FROM PrmsLcApplication p WHERE p.billOfExchange = :billOfExchange"),
    @NamedQuery(name = "PrmsLcApplication.findByFreightInvoice", query = "SELECT p FROM PrmsLcApplication p WHERE p.freightInvoice = :freightInvoice"),
    @NamedQuery(name = "PrmsLcApplication.findByLcappid", query = "SELECT p FROM PrmsLcApplication p WHERE p.lcappid = :lcappid"),
    @NamedQuery(name = "PrmsLcApplication.findByMaxLCAppId", query = "SELECT p FROM PrmsLcApplication p WHERE p.lcappid =(SELECT Max(p.lcappid)  from PrmsLcApplication p)"),
    @NamedQuery(name = "PrmsLcApplication.findByAvailableBy", query = "SELECT p FROM PrmsLcApplication p WHERE p.availableBy = :availableBy"),
    @NamedQuery(name = "PrmsLcApplication.findByCifOfShip", query = "SELECT p FROM PrmsLcApplication p WHERE p.cifOfShip = :cifOfShip"),
    @NamedQuery(name = "PrmsLcApplication.findByInsurPolicy", query = "SELECT p FROM PrmsLcApplication p WHERE p.insurPolicy = :insurPolicy"),
    @NamedQuery(name = "PrmsLcApplication.findByGoodsServices", query = "SELECT p FROM PrmsLcApplication p WHERE p.goodsServices = :goodsServices")})
public class PrmsLcApplication implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Column(name = "LC_APP_NO")
    private String lcAppNo;
    
    @Column(name = "TO_")
    private String to;
    
    @Column(name = "APPLICANT_NAME")
    private String applicantName;
    
    @Column(name = "APPLICANT_ADDRESS")
    private String applicantAddress;
    
    @Column(name = "LICENSE_NO")
    private String licenseNo;
    
    @Column(name = "LICENSE_TYPE")
    private String licenseType;
    
    @Column(name = "TIN_NO")
    private String tinNo;
    
    @Column(name = "SOURCE_OF_FUND")
    private String sourceOfFund;
    
    @Column(name = "LOAN_NO")
    private String loanNo;
    
    @Column(name = "NBE_ACCOUNT_NO")
    private String nbeAccountNo;
    
    @Column(name = "LC_TYPE")
    private String lcType;
    
    @Column(name = "AMOUNT_IN_FIGURE")
    private String amountInFigure;
    
    @Column(name = "AMOUNT_IN_WORDS")
    private String amountInWords;
    
    @Column(name = "DELIVERY_TERM")
    private String deliveryTerm;
    @Column(name = "SHIPMENT_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date shipmentDate;
    @Column(name = "EXPIRY_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date expiryDate;
    @Column(name = "APPLICATION_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date applicationDate;
    
    @Column(name = "SUPPLIER_NAME")
    private String supplierName;
    
    @Column(name = "ADVISING_BANK")
    private String advisingBank;
    
    @Column(name = "SHIPMENT_FROM")
    private String shipmentFrom;
    
    @Column(name = "SHIPMENT_TO")
    private String shipmentTo;
    
    @Column(name = "PARTIAL_SHIPMENT")
    private String partialShipment;
    
    @Column(name = "TRANS_SHIPMENT")
    private String transShipment;
    
    @Column(name = "SHIPMENT_TO_BE_EFFEC_BY")
    private String shipmentToBeEffecBy;
    
    @Column(name = "COUNTRY_ORIGINE_OF_GOODS")
    private String countryOrigineOfGoods;
    
    @Column(name = "PACKAGE_TO_BE_MARKED")
    private String packageToBeMarked;
    
    @Column(name = "BILL_OF_EXCHANGE")
    private String billOfExchange;
    
    @Column(name = "FREIGHT_INVOICE")
    private String freightInvoice;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    
     @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PRMS_LC_APPLICATION_SEQ")
    @SequenceGenerator(name = "PRMS_LC_APPLICATION_SEQ", sequenceName = "PRMS_LC_APPLICATION_SEQ", allocationSize = 1)
    
    @Column(name = "LCAPPID")
    private BigDecimal lcappid;
    
    @Column(name = "AVAILABLE_BY")
    private String availableBy;
    
    @Column(name = "CIF_OF_SHIP")
    private String cifOfShip;
    
    @Column(name = "INSUR_POLICY")
    private String insurPolicy;
    
    @Column(name = "GOODS_SERVICES")
    private String goodsServices;
    @JoinColumn(name = "CONTRACT_ID", referencedColumnName = "CONTRACT_ID")
    @ManyToOne
    private PrmsContract contractId;

    public PrmsLcApplication() {
    }

    public PrmsLcApplication(BigDecimal lcappid) {
        this.lcappid = lcappid;
    }

    public String getLcAppNo() {
        return lcAppNo;
    }

    public void setLcAppNo(String lcAppNo) {
        this.lcAppNo = lcAppNo;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getApplicantName() {
        return applicantName;
    }

    public void setApplicantName(String applicantName) {
        this.applicantName = applicantName;
    }

    public String getApplicantAddress() {
        return applicantAddress;
    }

    public void setApplicantAddress(String applicantAddress) {
        this.applicantAddress = applicantAddress;
    }

    public String getLicenseNo() {
        return licenseNo;
    }

    public void setLicenseNo(String licenseNo) {
        this.licenseNo = licenseNo;
    }

    public String getLicenseType() {
        return licenseType;
    }

    public void setLicenseType(String licenseType) {
        this.licenseType = licenseType;
    }

    public String getTinNo() {
        return tinNo;
    }

    public void setTinNo(String tinNo) {
        this.tinNo = tinNo;
    }

    public String getSourceOfFund() {
        return sourceOfFund;
    }

    public void setSourceOfFund(String sourceOfFund) {
        this.sourceOfFund = sourceOfFund;
    }

    public String getLoanNo() {
        return loanNo;
    }

    public void setLoanNo(String loanNo) {
        this.loanNo = loanNo;
    }

    public String getNbeAccountNo() {
        return nbeAccountNo;
    }

    public void setNbeAccountNo(String nbeAccountNo) {
        this.nbeAccountNo = nbeAccountNo;
    }

    public String getLcType() {
        return lcType;
    }

    public void setLcType(String lcType) {
        this.lcType = lcType;
    }

    public String getAmountInFigure() {
        return amountInFigure;
    }

    public void setAmountInFigure(String amountInFigure) {
        this.amountInFigure = amountInFigure;
    }

    public String getAmountInWords() {
        return amountInWords;
    }

    public void setAmountInWords(String amountInWords) {
        this.amountInWords = amountInWords;
    }

    public String getDeliveryTerm() {
        return deliveryTerm;
    }

    public void setDeliveryTerm(String deliveryTerm) {
        this.deliveryTerm = deliveryTerm;
    }

    public Date getShipmentDate() {
        return shipmentDate;
    }

    public void setShipmentDate(Date shipmentDate) {
        this.shipmentDate = shipmentDate;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    public Date getApplicationDate() {
        return applicationDate;
    }

    public void setApplicationDate(Date applicationDate) {
        this.applicationDate = applicationDate;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getAdvisingBank() {
        return advisingBank;
    }

    public void setAdvisingBank(String advisingBank) {
        this.advisingBank = advisingBank;
    }

    public String getShipmentFrom() {
        return shipmentFrom;
    }

    public void setShipmentFrom(String shipmentFrom) {
        this.shipmentFrom = shipmentFrom;
    }

    public String getShipmentTo() {
        return shipmentTo;
    }

    public void setShipmentTo(String shipmentTo) {
        this.shipmentTo = shipmentTo;
    }

    public String getPartialShipment() {
        return partialShipment;
    }

    public void setPartialShipment(String partialShipment) {
        this.partialShipment = partialShipment;
    }

    public String getTransShipment() {
        return transShipment;
    }

    public void setTransShipment(String transShipment) {
        this.transShipment = transShipment;
    }

    public String getShipmentToBeEffecBy() {
        return shipmentToBeEffecBy;
    }

    public void setShipmentToBeEffecBy(String shipmentToBeEffecBy) {
        this.shipmentToBeEffecBy = shipmentToBeEffecBy;
    }

    public String getCountryOrigineOfGoods() {
        return countryOrigineOfGoods;
    }

    public void setCountryOrigineOfGoods(String countryOrigineOfGoods) {
        this.countryOrigineOfGoods = countryOrigineOfGoods;
    }

    public String getPackageToBeMarked() {
        return packageToBeMarked;
    }

    public void setPackageToBeMarked(String packageToBeMarked) {
        this.packageToBeMarked = packageToBeMarked;
    }

    public String getBillOfExchange() {
        return billOfExchange;
    }

    public void setBillOfExchange(String billOfExchange) {
        this.billOfExchange = billOfExchange;
    }

    public String getFreightInvoice() {
        return freightInvoice;
    }

    public void setFreightInvoice(String freightInvoice) {
        this.freightInvoice = freightInvoice;
    }

    public BigDecimal getLcappid() {
        return lcappid;
    }

    public void setLcappid(BigDecimal lcappid) {
        this.lcappid = lcappid;
    }

    public String getAvailableBy() {
        return availableBy;
    }

    public void setAvailableBy(String availableBy) {
        this.availableBy = availableBy;
    }

    public String getCifOfShip() {
        return cifOfShip;
    }

    public void setCifOfShip(String cifOfShip) {
        this.cifOfShip = cifOfShip;
    }

    public String getInsurPolicy() {
        return insurPolicy;
    }

    public void setInsurPolicy(String insurPolicy) {
        this.insurPolicy = insurPolicy;
    }

    public String getGoodsServices() {
        return goodsServices;
    }

    public void setGoodsServices(String goodsServices) {
        this.goodsServices = goodsServices;
    }

    public PrmsContract getContractId() {
        return contractId;
    }

    public void setContractId(PrmsContract contractId) {
        this.contractId = contractId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (lcappid != null ? lcappid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PrmsLcApplication)) {
            return false;
        }
        PrmsLcApplication other = (PrmsLcApplication) object;
        if ((this.lcappid == null && other.lcappid != null) || (this.lcappid != null && !this.lcappid.equals(other.lcappid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "et.gov.eep.prms.entity.PrmsLcApplication[ lcappid=" + lcappid + " ]";
    }
    
}
