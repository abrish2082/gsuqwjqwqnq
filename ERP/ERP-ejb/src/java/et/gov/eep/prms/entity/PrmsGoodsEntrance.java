/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.prms.entity;

import et.gov.eep.commonApplications.entity.ComLuCountry;
import et.gov.eep.commonApplications.entity.PrmsLuDmArchive;
import et.gov.eep.commonApplications.entity.WfPrmsProcessed;
import et.gov.eep.fcms.entity.FmsLuCurrency;
import et.gov.eep.hrms.entity.organization.HrDepartments;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
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
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlTransient;
import org.eclipse.persistence.oxm.annotations.XmlTransformation;

@Entity
@Table(name = "PRMS_GOODS_ENTRANCE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PrmsGoodsEntrance.findAll", query = "SELECT p FROM PrmsGoodsEntrance p"),
    @NamedQuery(name = "PrmsGoodsEntrance.findByRegistrationNo", query = "SELECT p FROM PrmsGoodsEntrance p WHERE p.registrationNo = :registrationNo"),
    @NamedQuery(name = "PrmsGoodsEntrance.findByMaxId", query = "SELECT p FROM PrmsGoodsEntrance p WHERE p.id =(SELECT Max(p.id)  from PrmsGoodsEntrance p)"),
    @NamedQuery(name = "PrmsGoodsEntrance.findById", query = "SELECT p FROM PrmsGoodsEntrance p WHERE p.id = :id"),
    @NamedQuery(name = "PrmsGoodsEntrance.findByReqForApproval", query = "SELECT p FROM PrmsGoodsEntrance  p WHERE p.status=0"),
    @NamedQuery(name="PrmsGoodsEntrance.findByApprovedStatus",query = "SELECT p FROM PrmsGoodsEntrance p WHERE p.status=:status"),
    @NamedQuery(name = "PrmsGoodsEntrance.findByGoodsNoos", query = "SELECT p FROM PrmsGoodsEntrance p WHERE p.registrationNo = :registrationNo"),
    @NamedQuery(name = "PrmsGoodsEntrance.findByRegistrationNos", query = "SELECT p FROM PrmsGoodsEntrance p WHERE p.registrationNo LIKE :registrationNo"),
//    @NamedQuery(name = "PrmsGoodsEntrance.findByRegistrationType", query = "SELECT p FROM PrmsGoodsEntrance p WHERE p.registrationType = :registrationType"),
    @NamedQuery(name = "PrmsGoodsEntrance.findByDescription", query = "SELECT p FROM PrmsGoodsEntrance p WHERE p.description = :description"),
//    @NamedQuery(name = "PrmsGoodsEntrance.findByCountryName", query = "SELECT p FROM PrmsGoodsEntrance p WHERE p.countryName = :countryName"),
    @NamedQuery(name = "PrmsGoodsEntrance.findByGoodsAmount", query = "SELECT p FROM PrmsGoodsEntrance p WHERE p.goodsAmount = :goodsAmount"),
    @NamedQuery(name = "PrmsGoodsEntrance.findByUnitMeasure", query = "SELECT p FROM PrmsGoodsEntrance p WHERE p.unitMeasure = :unitMeasure"),
//    @NamedQuery(name = "PrmsGoodsEntrance.findByLcNo", query = "SELECT p FROM PrmsGoodsEntrance p WHERE p.lcNo = :lcNo"),
//    @NamedQuery(name = "PrmsGoodsEntrance.findByContractNo", query = "SELECT p FROM PrmsGoodsEntrance p WHERE p.contractNo = :contractNo"),
    @NamedQuery(name = "PrmsGoodsEntrance.findByBankPermit", query = "SELECT p FROM PrmsGoodsEntrance p WHERE p.bankPermit = :bankPermit"),
    @NamedQuery(name = "PrmsGoodsEntrance.findBySupplier", query = "SELECT p FROM PrmsGoodsEntrance p WHERE p.supplier = :supplier"),
//    @NamedQuery(name = "PrmsGoodsEntrance.findByDepartment", query = "SELECT p FROM PrmsGoodsEntrance p WHERE p.workUnit = :workUnit"),
//    @NamedQuery(name = "PrmsGoodsEntrance.findByCurrency", query = "SELECT p FROM PrmsGoodsEntrance p WHERE p.currency = :currency"),
    @NamedQuery(name = "PrmsGoodsEntrance.findByContractAmount", query = "SELECT p FROM PrmsGoodsEntrance p WHERE p.contractAmount = :contractAmount"),
    @NamedQuery(name = "PrmsGoodsEntrance.findByPrepatedBy", query = "SELECT p FROM PrmsGoodsEntrance p WHERE p.prepatedBy = :prepatedBy"),
//    @NamedQuery(name = "PrmsGoodsEntrance.findByBillType", query = "SELECT p FROM PrmsGoodsEntrance p WHERE p.billType = :billType"),
    @NamedQuery(name = "PrmsGoodsEntrance.findByBillNo", query = "SELECT p FROM PrmsGoodsEntrance p WHERE p.billNo = :billNo"),
    @NamedQuery(name = "PrmsGoodsEntrance.findByDateRegistered", query = "SELECT p FROM PrmsGoodsEntrance p WHERE p.dateRegistered = :dateRegistered"),
    @NamedQuery(name = "PrmsGoodsEntrance.findByOperationNo", query = "SELECT p FROM PrmsGoodsEntrance p WHERE p.operationNo = :operationNo"),
    @NamedQuery(name = "PrmsGoodsEntrance.findByEslseNo", query = "SELECT p FROM PrmsGoodsEntrance p WHERE p.eslseNo = :eslseNo"),
    @NamedQuery(name = "PrmsGoodsEntrance.findByIcasNo", query = "SELECT p FROM PrmsGoodsEntrance p WHERE p.icasNo = :icasNo"),
    @NamedQuery(name = "PrmsGoodsEntrance.findByAirport", query = "SELECT p FROM PrmsGoodsEntrance p WHERE p.airport = :airport"),
    @NamedQuery(name = "PrmsGoodsEntrance.findByRailway", query = "SELECT p FROM PrmsGoodsEntrance p WHERE p.railway = :railway"),
    @NamedQuery(name = "PrmsGoodsEntrance.findByProject", query = "SELECT p FROM PrmsGoodsEntrance p WHERE p.project = :project"),
    @NamedQuery(name = "PrmsGoodsEntrance.findByCustomAuthority", query = "SELECT p FROM PrmsGoodsEntrance p WHERE p.customAuthority = :customAuthority"),
    @NamedQuery(name = "PrmsGoodsEntrance.findByCustomCost", query = "SELECT p FROM PrmsGoodsEntrance p WHERE p.customCost = :customCost"),
    @NamedQuery(name = "PrmsGoodsEntrance.findByServiceCost", query = "SELECT p FROM PrmsGoodsEntrance p WHERE p.serviceCost = :serviceCost"),
    @NamedQuery(name = "PrmsGoodsEntrance.findByMiscellaneousCost", query = "SELECT p FROM PrmsGoodsEntrance p WHERE p.miscellaneousCost = :miscellaneousCost"),
    @NamedQuery(name = "PrmsGoodsEntrance.findByDepositeCost", query = "SELECT p FROM PrmsGoodsEntrance p WHERE p.depositeCost = :depositeCost"),
    @NamedQuery(name = "PrmsGoodsEntrance.findByCashRecievable", query = "SELECT p FROM PrmsGoodsEntrance p WHERE p.cashReceivable = :cashReceivable"),
    @NamedQuery(name = "PrmsGoodsEntrance.findByPayable", query = "SELECT p FROM PrmsGoodsEntrance p WHERE p.cashPayable = :cashPayable"),
    @NamedQuery(name = "PrmsGoodsEntrance.findByTotalCost", query = "SELECT p FROM PrmsGoodsEntrance p WHERE p.totalCost = :totalCost"),
    @NamedQuery(name = "PrmsGoodsEntrance.findByRemark", query = "SELECT p FROM PrmsGoodsEntrance p WHERE p.remark = :remark")})
public class PrmsGoodsEntrance implements Serializable {

    @JoinColumn(name = "DOCUMENT_ID", referencedColumnName = "DOCUMENT_ID")
    @ManyToOne
    private PrmsLuDmArchive documentId;
    
    
    @Column(name = "PROCESSED_ON")
    @Temporal(TemporalType.TIMESTAMP)
    private Date processedOn;

    @OneToMany(mappedBy = "goodsId")
    private List<PrmsFileUpload> prmsFileUploadList;

    @JoinColumn(name = "DEP_ID", referencedColumnName = "DEP_ID")
    @ManyToOne
    private HrDepartments depId;

    @JoinColumn(name = "LC_ID", referencedColumnName = "LC_ID")
    @ManyToOne
    private PrmsLcRigistration lcId;

    @JoinColumn(name = "COTRACT_ID", referencedColumnName = "CONTRACT_ID")
    @ManyToOne
    private PrmsContract cotractId;

    @JoinColumn(name = "PORT_OF_DISCHARGE", referencedColumnName = "PORT_ID")
    @ManyToOne
    private PrmsPortEntry portOfDischarge;
    @JoinColumn(name = "CURRENCY_ID", referencedColumnName = "CURRENCY_ID")
    @ManyToOne
    private FmsLuCurrency currencyId;

    @JoinColumn(name = "COUNTRY_ID", referencedColumnName = "ID")
    @ManyToOne
    private ComLuCountry countryId;

    @OneToMany(mappedBy = "goodsId", cascade = CascadeType.ALL)
    private List<WfPrmsProcessed> WfPrmsProcessedLists;
    
//    @OneToMany(mappedBy = "suppInfoId", cascade = CascadeType.ALL)
//    private List<PrmsSupplierPerformanceInfo> prmsSuppPerformanceList;
    
     @OneToMany(mappedBy = "goodsId", cascade = CascadeType.ALL)
    private List<PrmsSupplierPerformanceInfo> prmsSuppPerformanceList;
   
    @Column(name = "DEPOSITE_COST", precision = 126)
    private Double depositeCost;
    @Column(name = "CASH_RECEIVABLE", precision = 126)
    private Double cashReceivable;
    @Column(name = "CASH_PAYABLE", precision = 126)
    private Double cashPayable;
    @Column(name = "CUSTOM_COST", precision = 126)
    private Double customCost;
    @Column(name = "SERVICE_COST", precision = 126)
    private Double serviceCost;
    @Column(name = "MISCELLANEOUS_COST", precision = 126)
    private Double miscellaneousCost;
    @Column(name = "TOTAL_COST", precision = 126)
    private Double totalCost;

    @Column(name = "STATUS")
    private Integer status;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Column(name = "GOODS_AMOUNT")
    private BigInteger goodsAmount;
    @Column(name = "DATE_REGISTERED")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateRegistered;
    @OneToMany(mappedBy = "goodEntranceId")
    private List<PrmsContainerAgreement> prmsContainerAgreementList;

    @Id
    @Basic(optional = false)
    @NotNull
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PRMS_GOODS_ENTRANCE_SEQ")
    @SequenceGenerator(name = "PRMS_GOODS_ENTRANCE_SEQ", sequenceName = "PRMS_GOODS_ENTRANCE_SEQ", allocationSize = 1)
    private String id;
    private static final long serialVersionUID = 1L;

    @Column(name = "REGISTRATION_NO", nullable = false)
    private String registrationNo;

    @Column(name = "PROJECT")
    private String project;

    @Column(name = "DESCRIPTION")
    private String description;
     @Transient
    private String columnName;
    @Transient
    private String columnValue;

    public PrmsLcRigistration getLcId() {
        return lcId;
    }

    public void setLcId(PrmsLcRigistration lcId) {
        this.lcId = lcId;
    }

    @Column(name = "COMMERCIAL_INVOICE")
    private String commecialInvoice;

    @Column(name = "UNIT_MEASURE")
    private String unitMeasure;

    @Column(name = "BANK_PERMIT")
    private String bankPermit;

    @Column(name = "SUPPLIER")
    private String supplier;

    @Column(name = "CONTRACT_AMOUNT", length = 100)
    private String  contractAmount;

    @Column(name = "PREPATED_BY")
    private Integer prepatedBy;

    @Column(name = "BILL_NO")
    private String billNo;

    @Column(name = "OPERATION_NO")
    private String operationNo;

    @Column(name = "ESLSE_NO")
    private String eslseNo;

    @Column(name = "ICAS_NO")
    private String icasNo;

    @Column(name = "AIRPORT")
    private String airport;

    @Column(name = "RAILWAY")
    private String railway;

    @Column(name = "CUSTOM_AUTHORITY")
    private String customAuthority;

    @Column(name = "REMARK")
    private String remark;

    @Column(name = "DECLARATION_NO")
    private String declarationNo;

    @Column(name = "INITIAL_DEPOSITE")
    private Double initialDepostie;

    @Column(name = "ACTUAL_PAID")
    private Double actualPaid;

    @Column(name = "REMAINING_AMOUNT")
    private Double remainingAmount;

    

    public PrmsGoodsEntrance() {
    }

    public PrmsGoodsEntrance(String registrationNo) {
        this.registrationNo = registrationNo;
    }

    public String getRegistrationNo() {
        return registrationNo;
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

    public void setRegistrationNo(String registrationNo) {
        this.registrationNo = registrationNo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ComLuCountry getCountryId() {
        return countryId;
    }

    public void setCountryId(ComLuCountry countryId) {
        this.countryId = countryId;
    }

    public BigInteger getGoodsAmount() {
        return goodsAmount;
    }

    public void setGoodsAmount(BigInteger goodsAmount) {
        this.goodsAmount = goodsAmount;
    }

    public String getUnitMeasure() {
        return unitMeasure;
    }

    public void setUnitMeasure(String unitMeasure) {
        this.unitMeasure = unitMeasure;
    }

    public String getBankPermit() {
        return bankPermit;
    }

    public void setBankPermit(String bankPermit) {
        this.bankPermit = bankPermit;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public Double getDepositeCost() {
        return depositeCost;
    }

    public void setDepositeCost(Double depositeCost) {
        this.depositeCost = depositeCost;
    }

    public Double getCashReceivable() {
        return cashReceivable;
    }

    public void setCashReceivable(Double cashReceivable) {
        this.cashReceivable = cashReceivable;
    }

    public Double getCashPayable() {
        return cashPayable;
    }

    public void setCashPayable(Double cashPayable) {
        this.cashPayable = cashPayable;
    }

    public FmsLuCurrency getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(FmsLuCurrency currencyId) {
        this.currencyId = currencyId;
    }

    public String getContractAmount() {
        return contractAmount;
    }

    public void setContractAmount(String contractAmount) {
        this.contractAmount = contractAmount;
    }

    public Integer getPrepatedBy() {
        return prepatedBy;
    }

    public void setPrepatedBy(Integer prepatedBy) {
        this.prepatedBy = prepatedBy;
    }

    public String getBillNo() {
        return billNo;
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

    public Date getDateRegistered() {
        return dateRegistered;
    }

    public void setDateRegistered(Date dateRegistered) {
        this.dateRegistered = dateRegistered;
    }

    public String getOperationNo() {
        return operationNo;
    }

    public void setOperationNo(String operationNo) {
        this.operationNo = operationNo;
    }

    public String getEslseNo() {
        return eslseNo;
    }

    public void setEslseNo(String eslseNo) {
        this.eslseNo = eslseNo;
    }

    public String getIcasNo() {
        return icasNo;
    }

    public void setIcasNo(String icasNo) {
        this.icasNo = icasNo;
    }

    public String getAirport() {
        return airport;
    }

    public void setAirport(String airport) {
        this.airport = airport;
    }

    public String getRailway() {
        return railway;
    }

    public void setRailway(String railway) {
        this.railway = railway;
    }

    public String getCustomAuthority() {
        return customAuthority;
    }

    public void setCustomAuthority(String customAuthority) {
        this.customAuthority = customAuthority;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCommecialInvoice() {
        return commecialInvoice;
    }

    public void setCommecialInvoice(String commecialInvoice) {
        this.commecialInvoice = commecialInvoice;
    }

    @XmlTransient
    public List<PrmsContainerAgreement> getPrmsContainerAgreementList() {
        if (prmsContainerAgreementList == null) {
            prmsContainerAgreementList = new ArrayList<>();
        }
        return prmsContainerAgreementList;
    }

    public void setPrmsContainerAgreementList(List<PrmsContainerAgreement> prmsContainerAgreementList) {
        this.prmsContainerAgreementList = prmsContainerAgreementList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (registrationNo != null ? registrationNo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {

        if (!(object instanceof PrmsGoodsEntrance)) {
            return false;
        }
        PrmsGoodsEntrance other = (PrmsGoodsEntrance) object;
        if ((this.registrationNo == null && other.registrationNo != null) || (this.registrationNo != null && !this.registrationNo.equals(other.registrationNo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return registrationNo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Double getCustomCost() {
        return customCost;
    }

    public void setCustomCost(Double customCost) {
        this.customCost = customCost;
    }

    public Double getServiceCost() {
        return serviceCost;
    }

    public void setServiceCost(Double serviceCost) {
        this.serviceCost = serviceCost;
    }

    public Double getMiscellaneousCost() {
        return miscellaneousCost;
    }

    public void setMiscellaneousCost(Double miscellaneousCost) {
        this.miscellaneousCost = miscellaneousCost;
    }

    public Double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(Double totalCost) {
        this.totalCost = totalCost;
    }

    public PrmsPortEntry getPortOfDischarge() {
        return portOfDischarge;
    }

    public void setPortOfDischarge(PrmsPortEntry portOfDischarge) {
        this.portOfDischarge = portOfDischarge;
    }

    public PrmsContract getCotractId() {
        return cotractId;
    }

    public void setCotractId(PrmsContract cotractId) {
        this.cotractId = cotractId;
    }

    public HrDepartments getDepId() {
        return depId;
    }

    public void setDepId(HrDepartments depId) {
        this.depId = depId;
    }

    public String getDeclarationNo() {
        return declarationNo;
    }

    public void setDeclarationNo(String declarationNo) {
        this.declarationNo = declarationNo;
    }

    public Double getInitialDepostie() {
        return initialDepostie;
    }

    public void setInitialDepostie(Double initialDepostie) {
        this.initialDepostie = initialDepostie;
    }

    public Double getActualPaid() {
        return actualPaid;
    }

    public void setActualPaid(Double actualPaid) {
        this.actualPaid = actualPaid;
    }

    public Double getRemainingAmount() {
        return remainingAmount;
    }

    public void setRemainingAmount(Double remainingAmount) {
        this.remainingAmount = remainingAmount;
    }

    public PrmsLuDmArchive getDocumentId() {
        return documentId;
    }

    public void setDocumentId(PrmsLuDmArchive documentId) {
        this.documentId = documentId;
    }

    /**
     * @return the WfPrmsProcessedLists
     */
    @XmlTransient
    public List<WfPrmsProcessed> getWfPrmsProcessedLists() {
        if (WfPrmsProcessedLists == null) {
            WfPrmsProcessedLists = new ArrayList<>();
        }
        return WfPrmsProcessedLists;
    }

    /**
     * @param WfPrmsProcessedLists the WfPrmsProcessedLists to set
     */
    public void setWfPrmsProcessedLists(List<WfPrmsProcessed> WfPrmsProcessedLists) {
        this.WfPrmsProcessedLists = WfPrmsProcessedLists;
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
     * @return the prmsSuppPerformanceList
     */
    public List<PrmsSupplierPerformanceInfo> getPrmsSuppPerformanceList() {
        if(prmsSuppPerformanceList == null){
            prmsSuppPerformanceList = new ArrayList<>();
        }
        return prmsSuppPerformanceList;
    }

    /**
     * @param prmsSuppPerformanceList the prmsSuppPerformanceList to set
     */
    public void setPrmsSuppPerformanceList(List<PrmsSupplierPerformanceInfo> prmsSuppPerformanceList) {
        this.prmsSuppPerformanceList = prmsSuppPerformanceList;
    }

}
