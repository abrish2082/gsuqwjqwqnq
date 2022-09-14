
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.prms.entity;

import et.gov.eep.commonApplications.entity.ComLuCountry;
import et.gov.eep.fcms.entity.Vocher.FmsCashReceiptVoucher;

import java.io.Serializable;
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
import javax.persistence.ManyToMany;
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
import et.gov.eep.fcms.entity.Vocher.FmsCashReceiptVoucher;

/**
 *
 * @author user
 */
@Entity
@Table(name = "PRMS_SUPPLY_PROFILE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PrmsSupplyProfile.findAll", query = "SELECT p FROM PrmsSupplyProfile p"),
    @NamedQuery(name = "PrmsSupplyProfile.findById", query = "SELECT p FROM PrmsSupplyProfile p WHERE p.id = :id"),
    @NamedQuery(name = "PrmsSupplyProfile.searchByVendorCode", query = "SELECT p FROM PrmsSupplyProfile p WHERE p.vendorCode LIKE :vendorCode"),
    @NamedQuery(name = "PrmsSupplyProfile.findByMaxId", query = "SELECT p FROM PrmsSupplyProfile p WHERE p.id =(SELECT Max(p.id)  from PrmsSupplyProfile p)"),
    @NamedQuery(name = "PrmsSupplyProfile.findByVendorCode", query = "SELECT p FROM PrmsSupplyProfile p WHERE p.vendorCode = :vendorCode"),
    @NamedQuery(name = "PrmsSupplyProfile.findByVendorName", query = "SELECT p FROM PrmsSupplyProfile p WHERE p.id = :vendorName"),
    @NamedQuery(name = "PrmsSupplyProfile.findByVendorType", query = "SELECT p FROM PrmsSupplyProfile p WHERE p.vendorType = :vendorType"),
    @NamedQuery(name = "PrmsSupplyProfile.findByVatTypeID", query = "SELECT p FROM PrmsSupplyProfile p WHERE p.vatTypeId = :vatTypeId"),

    @NamedQuery(name = "PrmsSupplyProfile.findByVatNo", query = "SELECT p FROM PrmsSupplyProfile p WHERE p.vatNo = :vatNo"),
    @NamedQuery(name = "PrmsSupplyProfile.findByAgentName", query = "SELECT p FROM PrmsSupplyProfile p WHERE p.agentName = :agentName"),
    @NamedQuery(name = "PrmsSupplyProfile.findByVendName", query = "SELECT p FROM PrmsSupplyProfile p WHERE p.vendorName = :vendorName"),
    @NamedQuery(name = "PrmsSupplyProfile.findByVendNam", query = "SELECT p FROM PrmsSupplyProfile p WHERE p.vendorName = :vendorName"),
    @NamedQuery(name = "PrmsSupplyProfile.findByAgentAddress", query = "SELECT p FROM PrmsSupplyProfile p WHERE p.agentAddress = :agentAddress"),
    @NamedQuery(name = "PrmsSupplyProfile.findByDateReg", query = "SELECT p FROM PrmsSupplyProfile p WHERE p.dateReg = :dateReg"),
    @NamedQuery(name = "PrmsSupplyProfile.findByTinNo", query = "SELECT p FROM PrmsSupplyProfile p WHERE p.tinNo = :tinNo"),
    @NamedQuery(name = "PrmsSupplyProfile.findByTelOffice", query = "SELECT p FROM PrmsSupplyProfile p WHERE p.telOffice = :telOffice"),
    @NamedQuery(name = "PrmsSupplyProfile.findByTelMobile", query = "SELECT p FROM PrmsSupplyProfile p WHERE p.telMobile = :telMobile"),
    @NamedQuery(name = "PrmsSupplyProfile.findByFax", query = "SELECT p FROM PrmsSupplyProfile p WHERE p.fax = :fax"),
    @NamedQuery(name = "PrmsSupplyProfile.findByPobox", query = "SELECT p FROM PrmsSupplyProfile p WHERE p.pobox = :pobox"),
    @NamedQuery(name = "PrmsSupplyProfile.findByEmail", query = "SELECT p FROM PrmsSupplyProfile p WHERE p.email = :email"),
    @NamedQuery(name = "PrmsSupplyProfile.findByWebsite", query = "SELECT p FROM PrmsSupplyProfile p WHERE p.website = :website"),
    @NamedQuery(name = "PrmsSupplyProfile.findByCountryId", query = "SELECT p FROM PrmsSupplyProfile p WHERE p.countryId = :countryId"),
    @NamedQuery(name = "PrmsSupplyProfile.findByHouseNo", query = "SELECT p FROM PrmsSupplyProfile p WHERE p.houseNo = :houseNo"),
    @NamedQuery(name = "PrmsSupplyProfile.findByFaniancialLevel", query = "SELECT p FROM PrmsSupplyProfile p WHERE p.faniancialLevel = :faniancialLevel"),
    @NamedQuery(name = "PrmsSupplyProfile.findByStatus", query = "SELECT p FROM PrmsSupplyProfile p WHERE p.status = :status"),
    @NamedQuery(name = "PrmsSupplyProfile.findByRemark", query = "SELECT p FROM PrmsSupplyProfile p WHERE p.remark = :remark"),
    @NamedQuery(name = "PrmsSupplyProfile.findByVendInfo", query = "SELECT p FROM PrmsSupplyProfile p WHERE p.vendInfo = :vendInfo"),
    @NamedQuery(name = "PrmsSupplyProfile.findByPreparedBy", query = "SELECT p FROM PrmsSupplyProfile p WHERE p.preparedBy = :preparedBy"),
    @NamedQuery(name = "PrmsSupplyProfile.findByProfileFors", query = "SELECT p FROM PrmsSupplyProfile p WHERE p.profileFor LIKE :profileNo"),
    @NamedQuery(name = "PrmsSupplyProfile.findByProfileFor", query = "SELECT p FROM PrmsSupplyProfile p WHERE p.profileFor = :profileNo")})
public class PrmsSupplyProfile implements Serializable {

    @OneToMany(mappedBy = "suppId", fetch = FetchType.LAZY)
    private List<PrmsPurchaseOrder> prmsPurchaseOrderList;

    @OneToMany(mappedBy = "supId", fetch = FetchType.LAZY)
    private List<PrmsCheckListDetailforlot> prmsCheckListDetailforlotList;

    @OneToMany(mappedBy = "suppId", fetch = FetchType.LAZY)
    private List<FmsCashReceiptVoucher> fmsCashReceiptVoucherList;
    @OneToMany(mappedBy = "suppId", fetch = FetchType.LAZY)
    private List<FmsCashReceiptVoucher> fmsBiFmsCashReceiptVouchers;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "suppId", fetch = FetchType.LAZY)
    private List<PrmsBidderRegDetail> prmsBidderRegDetails;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "suppId", fetch = FetchType.LAZY)
    private List<PrmsBidOpeningChecklstDt> prmsBidOpeningChecklstDtList;
    @OneToMany(mappedBy = "supplierId", fetch = FetchType.LAZY)
    private List<PrmsBankClearance> prmsBankClearanceList;

    @OneToMany(mappedBy = "suppId")
    private List<PrmsAward> prmsAwardList;

    @OneToMany(mappedBy = "suppId")
    private List<PrmsFailsupplerAward> prmsFailsupplerAwardsList;

    @OneToMany(mappedBy = "suppId")
    private List<PrmsContract> prmsContractList;

    @OneToMany(mappedBy = "suppId", fetch = FetchType.LAZY)
    private List<PrmsForeignExchange> prmsForeignExchangeList;

    @JoinColumn(name = "COUNTRY_ID", referencedColumnName = "ID")
    @ManyToOne
    private ComLuCountry countryId;

    @OneToMany(mappedBy = "suppId", cascade = CascadeType.ALL)
    private List<PrmsSupplyProfileDetail> prmsSupplyProfileDetailList = new ArrayList<>();
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PRMS_SUPPLY_SEQ_GENERATOR")
    @SequenceGenerator(name = "PRMS_SUPPLY_SEQ_GENERATOR", sequenceName = "PRMS_SUPPLY_SEQ_GENERATOR", allocationSize = 1)
    @Column(name = "ID", nullable = false, precision = 0, scale = -127)
    private String id;
    @Size(max = 100)
    @Column(name = "VENDOR_CODE", length = 100)
    private String vendorCode;
    @Size(max = 100)
    @Column(name = "VENDOR_NAME", length = 100)
    private String vendorName;
    @Size(max = 100)
    @Column(name = "VENDOR_TYPE", length = 100)
    private String vendorType;
    @Size(max = 20)
    @Column(name = "VAT_NO", length = 20)
    private String vatNo;
    @Size(max = 100)
    @Column(name = "AGENT_NAME", length = 100)
    private String agentName;
    @Size(max = 100)
    @Column(name = "AGENT_ADDRESS", length = 100)
    private String agentAddress;
    @Column(name = "DATE_REG")
    private String dateReg;
    @Column(name = "PERIODS_DATE")
    private String periodsDate;
    @Size(max = 100)
    @Column(name = "BUSINESS")
    private String business;
    @Size(max = 500)
    @Column(name = "BUSI_SECTOR")
    private String busiSector;
    @Size(max = 500)
    @Column(name = "BUS_DISCRIPTION", length = 500)
    private String busDiscription;
    @Size(max = 500)
    @Column(name = "OTHERS_REMARK", length = 500)
    private String othersRemark;
    @Size(max = 20)
    @Column(name = "TIN_NO", length = 10)
    private String tinNo;
    @Size(max = 20)
    @Column(name = "TEL_OFFICE", length = 10)
    private String telOffice;
    @Size(max = 20)
    @Column(name = "TEL_MOBILE", length = 20)
    private String telMobile;
    @Size(max = 100)
    @Column(name = "FAX", length = 100)
    private String fax;
    @Size(max = 100)
    @Column(name = "POBOX", length = 100)
    private String pobox;
    @Size(max = 100)
    @Column(name = "EMAIL", length = 100)
    private String email;
    @Size(max = 100)
    @Column(name = "WEBSITE", length = 100)
    private String website;
    @Size(max = 100)
    @Column(name = "CALLING_CODE")
    private String callingCode;

    @Column(name = "HOUSE_NO", length = 100)
    private String houseNo;
    @Size(max = 100)
    @Column(name = "FANIANCIAL_LEVEL", length = 100)
    private String faniancialLevel;
    @Size(max = 100)
    @Column(name = "STATUS", length = 100)
    private String status;
    @Size(max = 100)
    @Column(name = "REMARK", length = 100)
    private String remark;
    @Size(max = 100)
    @Column(name = "VEND_INFO", length = 100)
    private String vendInfo;
    @Size(max = 100)
    @Column(name = "PREPARED_BY", length = 100)
    private String preparedBy;
    @Size(max = 50)
    @Column(name = "PROFILE_FOR", length = 50)
    private String profileFor;
    @JoinColumn(name = "VAT_TYPE_ID", referencedColumnName = "VAT_TYPE_ID")
    @ManyToOne
    private PrmsLuVatTypeLookup vatTypeId;
    @OneToMany(mappedBy = "supplierId")
    private List<PrmsFinancialEvlResultyDtl> prmsFinancialEvlResultyDtlList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "suppId", fetch = FetchType.LAZY)
    private List<PrmsPaymentRequisition> prmsPaymentRequisitionList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "suppId", fetch = FetchType.LAZY)
    private List<PrmsSuppSpecification> prmsSuppSpecificationsList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "supplierId", fetch = FetchType.LAZY)
    private List<PrmsThechincalEvaluationDet> prmsThechincalEvaluationDetList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "supplierId", fetch = FetchType.LAZY)
    private List<PrmsPreminilaryEvalutionDt> prmsPreminilaryEvalutionDtList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "bidderId")
    private List<PrmsPostDetail> prmsPostDetailList;

    @Transient
    private String failLocation;
    @Transient
    private String columnName;
    @Transient
    private String columnValue;

    public PrmsSupplyProfile() {
    }

    public PrmsSupplyProfile(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public String getVendorCode() {
        return vendorCode;
    }

    public void setVendorCode(String vendorCode) {
        this.vendorCode = vendorCode;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public String getVendorType() {
        return vendorType;
    }

    public void setVendorType(String vendorType) {
        this.vendorType = vendorType;
    }

    public String getVatNo() {
        return vatNo;
    }

    public void setVatNo(String vatNo) {
        this.vatNo = vatNo;
    }

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    public String getAgentAddress() {
        return agentAddress;
    }

    public void setAgentAddress(String agentAddress) {
        this.agentAddress = agentAddress;
    }

    public String getDateReg() {
        return dateReg;
    }

    public void setDateReg(String dateReg) {
        this.dateReg = dateReg;
    }

    public String getTinNo() {
        return tinNo;
    }

    public void setTinNo(String tinNo) {
        this.tinNo = tinNo;
    }

    public String getTelOffice() {
        return telOffice;
    }

    public void setTelOffice(String telOffice) {
        this.telOffice = telOffice;
    }

    public String getTelMobile() {
        return telMobile;
    }

    public void setTelMobile(String telMobile) {
        this.telMobile = telMobile;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getPobox() {
        return pobox;
    }

    public void setPobox(String pobox) {
        this.pobox = pobox;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBusiness() {
        return business;
    }

    public void setBusiness(String business) {
        this.business = business;
    }

    public String getOthersRemark() {
        return othersRemark;
    }

    public void setOthersRemark(String othersRemark) {
        this.othersRemark = othersRemark;
    }

    public String getBusDiscription() {
        return busDiscription;
    }

    public void setBusDiscription(String busDiscription) {
        this.busDiscription = busDiscription;
    }

    public String getBusiSector() {
        return busiSector;
    }

    public void setBusiSector(String busiSector) {
        this.busiSector = busiSector;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public ComLuCountry getCountryId() {
        return countryId;
    }

    public void setCountryId(ComLuCountry countryId) {
        this.countryId = countryId;
    }

    public String getHouseNo() {
        return houseNo;
    }

    public void setHouseNo(String houseNo) {
        this.houseNo = houseNo;
    }

    public String getFaniancialLevel() {
        return faniancialLevel;
    }

    public void setFaniancialLevel(String faniancialLevel) {
        this.faniancialLevel = faniancialLevel;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getVendInfo() {
        return vendInfo;
    }

    public void setVendInfo(String vendInfo) {
        this.vendInfo = vendInfo;
    }

    public String getPreparedBy() {
        return preparedBy;
    }

    public void setPreparedBy(String preparedBy) {
        this.preparedBy = preparedBy;
    }

    public String getProfileFor() {
        return profileFor;
    }

    public void setProfileFor(String profileFor) {
        this.profileFor = profileFor;
    }

    public PrmsLuVatTypeLookup getVatTypeId() {
        return vatTypeId;
    }

    public void setVatTypeId(PrmsLuVatTypeLookup vatTypeId) {
        this.vatTypeId = vatTypeId;
    }

    @XmlTransient
    public List<PrmsSupplyProfileDetail> getPrmsSupplyProfileDetailList() {
        return prmsSupplyProfileDetailList;
    }

    public void setPrmsSupplyProfileDetailList(List<PrmsSupplyProfileDetail> prmsSupplyProfileDetailList) {
        this.prmsSupplyProfileDetailList = prmsSupplyProfileDetailList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @XmlTransient
    public List<PrmsFinancialEvlResultyDtl> getPrmsFinancialEvlResultyDtlList() {
        if (prmsFinancialEvlResultyDtlList == null) {
            prmsFinancialEvlResultyDtlList = new ArrayList<>();
        }
        return prmsFinancialEvlResultyDtlList;
    }

    public void setPrmsFinancialEvlResultyDtlList(List<PrmsFinancialEvlResultyDtl> prmsFinancialEvlResultyDtlList) {
        this.prmsFinancialEvlResultyDtlList = prmsFinancialEvlResultyDtlList;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PrmsSupplyProfile)) {
            return false;
        }
        PrmsSupplyProfile other = (PrmsSupplyProfile) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return vendorName;
    }

    @XmlTransient
    public List<PrmsPaymentRequisition> getPrmsPaymentRequisitionList() {
        if (prmsPaymentRequisitionList == null) {
            prmsPaymentRequisitionList = new ArrayList<>();
        }
        return prmsPaymentRequisitionList;
    }

    public void setPrmsPaymentRequisitionList(List<PrmsPaymentRequisition> prmsPaymentRequisitionList) {
        this.prmsPaymentRequisitionList = prmsPaymentRequisitionList;
    }

    @XmlTransient
    public List<PrmsSuppSpecification> getPrmsSuppSpecificationsList() {
        if (prmsSuppSpecificationsList == null) {
            prmsSuppSpecificationsList = new ArrayList<>();
        }
        return prmsSuppSpecificationsList;
    }

    public void setPrmsSuppSpecificationsList(List<PrmsSuppSpecification> prmsSuppSpecificationsList) {
        this.prmsSuppSpecificationsList = prmsSuppSpecificationsList;
    }

    @XmlTransient
    public List<PrmsThechincalEvaluationDet> getPrmsThechincalEvaluationDetList() {
        return prmsThechincalEvaluationDetList;
    }

    public void setPrmsThechincalEvaluationDetList(List<PrmsThechincalEvaluationDet> prmsThechincalEvaluationDetList) {
        this.prmsThechincalEvaluationDetList = prmsThechincalEvaluationDetList;
    }

    @XmlTransient
    public List<PrmsAward> getPrmsAwardList() {
        return prmsAwardList;
    }

    public void setPrmsAwardList(List<PrmsAward> prmsAwardList) {
        this.prmsAwardList = prmsAwardList;
    }

    @XmlTransient
    public List<PrmsContract> getPrmsContractList() {
        return prmsContractList;
    }

    public void setPrmsContractList(List<PrmsContract> prmsContractList) {
        this.prmsContractList = prmsContractList;
    }

    @XmlTransient
    public List<PrmsForeignExchange> getPrmsForeignExchangeList() {
        if (prmsForeignExchangeList == null) {
            prmsForeignExchangeList = new ArrayList<>();
        }
        return prmsForeignExchangeList;
    }

    public void setPrmsForeignExchangeList(List<PrmsForeignExchange> prmsForeignExchangeList) {
        this.prmsForeignExchangeList = prmsForeignExchangeList;
    }

    @XmlTransient
    public List<PrmsBidderRegDetail> getPrmsBidderRegDetails() {
        if (prmsBidderRegDetails == null) {
            prmsBidderRegDetails = new ArrayList<>();
        }
        return prmsBidderRegDetails;
    }

    public void setPrmsBidderRegDetails(List<PrmsBidderRegDetail> prmsBidderRegDetails) {
        this.prmsBidderRegDetails = prmsBidderRegDetails;
    }

    @XmlTransient

    public List<FmsCashReceiptVoucher> getFmsBiFmsCashReceiptVouchers() {
        if (fmsBiFmsCashReceiptVouchers == null) {
            fmsBiFmsCashReceiptVouchers = new ArrayList<>();
        }
        return fmsBiFmsCashReceiptVouchers;
    }

    public void setFmsBiFmsCashReceiptVouchers(List<FmsCashReceiptVoucher> fmsBiFmsCashReceiptVouchers) {
        this.fmsBiFmsCashReceiptVouchers = fmsBiFmsCashReceiptVouchers;
    }

    @XmlTransient
    public List<FmsCashReceiptVoucher> getFmsCashReceiptVoucherList() {
        if (fmsCashReceiptVoucherList == null) {
            fmsCashReceiptVoucherList = new ArrayList<>();
        }
        return fmsCashReceiptVoucherList;
    }

    public void setFmsCashReceiptVoucherList(List<FmsCashReceiptVoucher> fmsCashReceiptVoucherList) {
        this.fmsCashReceiptVoucherList = fmsCashReceiptVoucherList;
    }

    @XmlTransient

    public List<PrmsFailsupplerAward> getPrmsFailsupplerAwardsList() {
        if (prmsFailsupplerAwardsList == null) {
            prmsFailsupplerAwardsList = new ArrayList<>();
        }
        return prmsFailsupplerAwardsList;
    }

    public void setPrmsFailsupplerAwardsList(List<PrmsFailsupplerAward> prmsFailsupplerAwardsList) {
        this.prmsFailsupplerAwardsList = prmsFailsupplerAwardsList;
    }

    @XmlTransient

    public List<PrmsBidOpeningChecklstDt> getPrmsBidOpeningChecklstDtList() {
        if (prmsBidOpeningChecklstDtList == null) {
            prmsBidOpeningChecklstDtList = new ArrayList<>();
        }
        return prmsBidOpeningChecklstDtList;
    }

    public void setPrmsBidOpeningChecklstDtList(List<PrmsBidOpeningChecklstDt> prmsBidOpeningChecklstDtList) {
        this.prmsBidOpeningChecklstDtList = prmsBidOpeningChecklstDtList;
    }

    @XmlTransient

    public List<PrmsBankClearance> getPrmsBankClearanceList() {
        if (prmsBankClearanceList == null) {
            prmsBankClearanceList = new ArrayList<>();
        }
        return prmsBankClearanceList;
    }

    public void setPrmsBankClearanceList(List<PrmsBankClearance> prmsBankClearanceList) {
        this.prmsBankClearanceList = prmsBankClearanceList;
    }

    @XmlTransient

    public List<PrmsPreminilaryEvalutionDt> getPrmsPreminilaryEvalutionDtList() {
        if (prmsPreminilaryEvalutionDtList == null) {
            prmsPreminilaryEvalutionDtList = new ArrayList<>();
        }
        return prmsPreminilaryEvalutionDtList;
    }

    public void setPrmsPreminilaryEvalutionDtList(List<PrmsPreminilaryEvalutionDt> prmsPreminilaryEvalutionDtList) {
        this.prmsPreminilaryEvalutionDtList = prmsPreminilaryEvalutionDtList;
    }

    public String getFailLocation() {
        return failLocation;
    }

    public void setFailLocation(String failLocation) {
        this.failLocation = failLocation;
    }

    @XmlTransient
    public List<PrmsCheckListDetailforlot> getPrmsCheckListDetailforlotList() {
        if (prmsCheckListDetailforlotList == null) {
            prmsCheckListDetailforlotList = new ArrayList<>();
        }
        return prmsCheckListDetailforlotList;
    }

    public void setPrmsCheckListDetailforlotList(List<PrmsCheckListDetailforlot> prmsCheckListDetailforlotList) {
        this.prmsCheckListDetailforlotList = prmsCheckListDetailforlotList;
    }

    /**
     * @return the prmsPostDetailList
     */
    @XmlTransient
    public List<PrmsPostDetail> getPrmsPostDetailList() {
        if (prmsPostDetailList == null) {
            prmsPostDetailList = new ArrayList<>();
        }
        return prmsPostDetailList;
    }

    /**
     * @param prmsPostDetailList the prmsPostDetailList to set
     */
    public void setPrmsPostDetailList(List<PrmsPostDetail> prmsPostDetailList) {
        this.prmsPostDetailList = prmsPostDetailList;
    }

    public String getCallingCode() {
        return callingCode;
    }

    public void setCallingCode(String callingCode) {
        this.callingCode = callingCode;
    }

    /**
     * @return the periodDate
     */
    public String getPeriodsDate() {
        return periodsDate;
    }

    /**
     * @param periodDate the periodDate to set
     */
    public void setPeriodDate(String periodsDate) {
        this.periodsDate = periodsDate;
    }

    @XmlTransient
    public List<PrmsPurchaseOrder> getPrmsPurchaseOrderList() {
        return prmsPurchaseOrderList;
    }

    public void setPrmsPurchaseOrderList(List<PrmsPurchaseOrder> prmsPurchaseOrderList) {
        this.prmsPurchaseOrderList = prmsPurchaseOrderList;
    }

}
