/**
 * *****************************************************************************
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates and open the template
 * in the editor.
 * ****************************************************************************
 */
package et.gov.eep.prms.entity;

import et.gov.eep.commonApplications.entity.PrmsLuDmArchive;
import et.gov.eep.commonApplications.entity.WfPrmsProcessed;
import et.gov.eep.fcms.entity.FmsLuCurrency;
import et.gov.eep.pms.entity.PmsCreateProjects;
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
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * *********************************************************************************************************************************************************
 *
 * @author User
 * ********************************************************************************************************************************************************
 */
@Entity
@Table(name = "PRMS_CONTRACT_AMENDMENT")//, catalog = "", schema = "EEP")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PrmsContractAmendment.findAll", query = "SELECT p FROM PrmsContractAmendment p"),
    @NamedQuery(name = "PrmsContractAmendment.findByMaxCheckListNum", query = "SELECT p FROM PrmsContractAmendment p WHERE p.contractAmendId = (SELECT Max(c.contractAmendId)  from PrmsContractAmendment c)"),
    @NamedQuery(name = "PrmsContractAmendment.listByContId", query = "SELECT p FROM PrmsContractAmendment p WHERE p.contractId=:contractId"),
    @NamedQuery(name = "PrmsContractAmendment.listByContIdStatus", query = "SELECT p FROM PrmsContractAmendment p WHERE p.contractId=:contractId and p.status=0"),
    @NamedQuery(name = "PrmsContractAmendment.listBytatus", query = "SELECT p FROM PrmsContractAmendment p WHERE p.status=0 and p.contractAmendNo is not null"),

    @NamedQuery(name = "PrmsContractAmendment.findByContractAmendId", query = "SELECT p FROM PrmsContractAmendment p WHERE p.contractAmendId = :contractAmendId"),
    @NamedQuery(name = "PrmsContractAmendment.findByDocumentId", query = "SELECT p FROM PrmsContractAmendment p WHERE p.documentId = :documentId"),
    @NamedQuery(name = "PrmsContractAmendment.findByContractPeriodTo", query = "SELECT p FROM PrmsContractAmendment p WHERE p.contractPeriodTo = :contractPeriodTo"),
    @NamedQuery(name = "PrmsContractAmendment.findByDeliveryPlace", query = "SELECT p FROM PrmsContractAmendment p WHERE p.deliveryPlace = :deliveryPlace"),
    @NamedQuery(name = "PrmsContractAmendment.findByPreparedBy", query = "SELECT p FROM PrmsContractAmendment p WHERE p.preparedBy = :preparedBy"),
    @NamedQuery(name = "PrmsContractAmendment.findByPreparedDate", query = "SELECT p FROM PrmsContractAmendment p WHERE p.preparedDate = :preparedDate"),
    @NamedQuery(name = "PrmsContractAmendment.findByStatus", query = "SELECT p FROM PrmsContractAmendment p WHERE p.status = :status"),
    @NamedQuery(name = "PrmsContractAmendment.findByRemark", query = "SELECT p FROM PrmsContractAmendment p WHERE p.remark = :remark"),
    @NamedQuery(name = "PrmsContractAmendment.findById", query = "SELECT p FROM PrmsContractAmendment p WHERE p.id = :id"),
    @NamedQuery(name = "PrmsContractAmendment.findByContractAmendNo", query = "SELECT p FROM PrmsContractAmendment p WHERE p.contractAmendNo = :contractAmendNo"),
    @NamedQuery(name = "PrmsContractAmendment.findByContractAmendNoS", query = "SELECT p FROM PrmsContractAmendment p WHERE p.contractAmendNo LIKE :contractAmendNo and p.preparedBy=:preparedBy"),

    @NamedQuery(name = "PrmsContractAmendment.findByIncomTerm", query = "SELECT p FROM PrmsContractAmendment p WHERE p.incomTerm = :incomTerm"),
    @NamedQuery(name = "PrmsContractAmendment.findByAmendementDate", query = "SELECT p FROM PrmsContractAmendment p WHERE p.amendementDate = :amendementDate"),
    @NamedQuery(name = "PrmsContractAmendment.findByPreferenceBondAmount", query = "SELECT p FROM PrmsContractAmendment p WHERE p.preferenceBondAmount = :preferenceBondAmount"),
    @NamedQuery(name = "PrmsContractAmendment.findByCommencmentDate", query = "SELECT p FROM PrmsContractAmendment p WHERE p.commencmentDate = :commencmentDate"),
    @NamedQuery(name = "PrmsContractAmendment.findByAmendmentNumber", query = "SELECT p FROM PrmsContractAmendment p WHERE p.amendmentNumber = :amendmentNumber"),
    @NamedQuery(name = "PrmsContractAmendment.findByContracttype", query = "SELECT p FROM PrmsContractAmendment p WHERE p.contracttype = :contracttype"),
    @NamedQuery(name = "PrmsContractAmendment.findByPaymenttype", query = "SELECT p FROM PrmsContractAmendment p WHERE p.paymenttype = :paymenttype"),
    @NamedQuery(name = "PrmsContractAmendment.findByPerformanceGarante", query = "SELECT p FROM PrmsContractAmendment p WHERE p.performanceGarante = :performanceGarante"),
    @NamedQuery(name = "PrmsContractAmendment.findByContractsigningDate", query = "SELECT p FROM PrmsContractAmendment p WHERE p.contractsigningDate = :contractsigningDate"),
    @NamedQuery(name = "PrmsContractAmendment.findByContractName", query = "SELECT p FROM PrmsContractAmendment p WHERE p.contractName = :contractName"),
    @NamedQuery(name = "PrmsContractAmendment.findByContractamount", query = "SELECT p FROM PrmsContractAmendment p WHERE p.contractamount = :contractamount"),
    @NamedQuery(name = "PrmsContract.findByContractendDate", query = "SELECT p FROM PrmsContract p WHERE p.contractendDate = :contractendDate"),
    @NamedQuery(name = "PrmsContractAmendment.findByCommencementdateam", query = "SELECT p FROM PrmsContractAmendment p WHERE p.commencementdateam = :commencementdateam"),
    @NamedQuery(name = "PrmsContractAmendment.findByContractsigningdateam", query = "SELECT p FROM PrmsContractAmendment p WHERE p.contractsigningdateam = :contractsigningdateam"),
    @NamedQuery(name = "PrmsContractAmendment.findByRigistrationDate", query = "SELECT p FROM PrmsContractAmendment p WHERE p.rigistrationDate = :rigistrationDate"),// listByContId
    @NamedQuery(name = "PrmsContractAmendment.findByContractenddateam", query = "SELECT p FROM PrmsContractAmendment p WHERE p.contractenddateam = :contractenddateam")})

public class PrmsContractAmendment implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @GeneratedValue(generator = "PRMS_CONTRACT_AMEND_SEQ", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "PRMS_CONTRACT_AMEND_SEQ", sequenceName = "PRMS_CONTRACT_AMEND_SEQ", allocationSize = 1)
    @Column(name = "CONTRACT_AMEND_ID")
    private BigDecimal contractAmendId;
    @Column(name = "DOCUMENT_ID")
    private String documentId;
    @Column(name = "CONTRACT_PERIOD_TO")
    private Integer contractPeriodTo;
    @Size(max = 100)
    @Column(name = "DELIVERY_PLACE", length = 100)
    private String deliveryPlace;
    @Size(max = 50)
    @Column(name = "PREPARED_BY", length = 50)
    private String preparedBy;
    @Column(name = "PREPARED_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date preparedDate;
    @Column(name = "STATUS")
    private Integer status;
    @Size(max = 100)
    @Column(name = "REMARK", length = 100)
    private String remark;
    @Size(max = 20)
    @Column(name = "ID", length = 20)
    private String id;
    @Size(max = 100)
    @Column(name = "CONTRACT_AMEND_NO", length = 100)
    private String contractAmendNo;
    @Size(max = 50)
    @Column(name = "INCOM_TERM", length = 50)
    private String incomTerm;

    @Column(name = "RIGISTRATION_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date rigistrationDate;

    @Column(name = "AMENDEMENT_DATE")
    @Temporal(TemporalType.DATE)
    private Date amendementDate;
    @Column(name = "PREFERENCE_BOND_AMOUNT")
    private Integer preferenceBondAmount;
    @Column(name = "COMMENCMENT_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date commencmentDate;
    @Size(max = 100)
    @Column(name = "AMENDMENT_NUMBER", length = 100)
    private String amendmentNumber;
    @Size(max = 20)
    @Column(name = "CONTRACTTYPE", length = 20)
    private String contracttype;
    @Size(max = 20)
    @Column(name = "PAYMENTTYPE", length = 20)
    private String paymenttype;
    @Column(name = "PERFORMANCE_GARANTE")
    private Double performanceGarante;
    @Column(name = "CONTRACTSIGNING_DATE")
    @Temporal(TemporalType.DATE)
    private Date contractsigningDate;
    @Size(max = 20)
    @Column(name = "CONTRACT_NAME", length = 20)
    private String contractName;
    @Column(name = "CONTRACTAMOUNT", precision = 126)
    private Double contractamount;
    @Column(name = "CONTRACTEND_DATE")
    @Temporal(TemporalType.DATE)
    private Date contractendDate;
    @Size(max = 20)
    @Column(name = "COMMENCEMENTDATEAM")
    private String commencementdateam;
    @Size(max = 20)
    @Column(name = "CONTRACTSIGNINGDATEAM")
    private String contractsigningdateam;
    @Size(max = 20)
    @Column(name = "CONTRACTENDDATEAM")
    private String contractenddateam;
    @Transient
    private String columnName;
    @Transient
    private String columnValue;
    @JoinColumn(name = "CURRENCY", referencedColumnName = "CURRENCY_ID")
    @ManyToOne
    private FmsLuCurrency currency;
    @JoinColumn(name = "PROJECT_ID", referencedColumnName = "PROJECT_ID")
    @ManyToOne
    private PmsCreateProjects projectId;
    @JoinColumn(name = "AWARD_ID", referencedColumnName = "AWARD_ID")
    @ManyToOne
    private PrmsAward awardId;
    @JoinColumn(name = "BID_ID", referencedColumnName = "ID")
    @ManyToOne
    private PrmsBid bidId;
    @JoinColumn(name = "CONTRACT_ID", referencedColumnName = "CONTRACT_ID")
    @ManyToOne
    private PrmsContract contractId;
    @JoinColumn(name = "SUPP_ID", referencedColumnName = "ID")
    @ManyToOne
    private PrmsSupplyProfile suppId;
    @OneToMany(mappedBy = "contractAmendId", cascade = CascadeType.ALL)
    private List<WfPrmsProcessed> prmsWorkFlowProccedList;

    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "contractAmendId", fetch = FetchType.LAZY)
    private List<PrmsContractAmendmentDt> prmsContractAmendmentDtList;

    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "contractId", fetch = FetchType.LAZY)
    private List<PrmsContractamendCurrency> prmsContractACurrencyDetailList;
    @JoinColumn(name = "DOCUMENTUP_ID", referencedColumnName = "DOCUMENT_ID")
    @ManyToOne
    private PrmsLuDmArchive documentupId;

    public PrmsContractAmendment() {
    }

    public PrmsContractAmendment(BigDecimal contractAmendId) {

        this.contractAmendId = contractAmendId;
    }

    @Override
    public int hashCode() {

        int hash = 0;
        hash += (contractAmendId != null ? contractAmendId.hashCode() : 0);

        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PrmsContractAmendment)) {
            return false;
        }

        PrmsContractAmendment other = (PrmsContractAmendment) object;

        if ((this.contractAmendId == null && other.contractAmendId != null)
                || (this.contractAmendId != null && !this.contractAmendId.equals(other.contractAmendId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return contractAmendNo;
    }

    public BigDecimal getContractAmendId() {
        return contractAmendId;
    }

    public void setContractAmendId(BigDecimal contractAmendId) {
        this.contractAmendId = contractAmendId;
    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public Date getRigistrationDate() {
        return rigistrationDate;
    }

    public void setRigistrationDate(Date rigistrationDate) {
        this.rigistrationDate = rigistrationDate;
    }

    public Integer getContractPeriodTo() {
        return contractPeriodTo;
    }

    public void setContractPeriodTo(Integer contractPeriodTo) {
        this.contractPeriodTo = contractPeriodTo;
    }

    public String getDeliveryPlace() {
        return deliveryPlace;
    }

    public void setDeliveryPlace(String deliveryPlace) {
        this.deliveryPlace = deliveryPlace;
    }

    public String getPreparedBy() {
        return preparedBy;
    }

    public void setPreparedBy(String preparedBy) {
        this.preparedBy = preparedBy;
    }

    public Date getPreparedDate() {
        return preparedDate;
    }

    public void setPreparedDate(Date preparedDate) {
        this.preparedDate = preparedDate;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContractAmendNo() {
        return contractAmendNo;
    }

    public void setContractAmendNo(String contractAmendNo) {
        this.contractAmendNo = contractAmendNo;
    }

    public String getIncomTerm() {
        return incomTerm;
    }

    public void setIncomTerm(String incomTerm) {
        this.incomTerm = incomTerm;
    }

    public Date getAmendementDate() {
        return amendementDate;
    }

    public void setAmendementDate(Date amendementDate) {
        this.amendementDate = amendementDate;
    }

    public Date getContractendDate() {
        return contractendDate;
    }

    public void setContractendDate(Date contractendDate) {
        this.contractendDate = contractendDate;
    }

    public Integer getPreferenceBondAmount() {
        return preferenceBondAmount;
    }

    public void setPreferenceBondAmount(Integer preferenceBondAmount) {
        this.preferenceBondAmount = preferenceBondAmount;
    }

    public Date getCommencmentDate() {
        return commencmentDate;
    }

    public void setCommencmentDate(Date commencmentDate) {
        this.commencmentDate = commencmentDate;
    }

    public String getAmendmentNumber() {
        return amendmentNumber;
    }

    public void setAmendmentNumber(String amendmentNumber) {
        this.amendmentNumber = amendmentNumber;
    }

    public String getContracttype() {
        return contracttype;
    }

    public void setContracttype(String contracttype) {
        this.contracttype = contracttype;
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

    public String getPaymenttype() {
        return paymenttype;
    }

    public void setPaymenttype(String paymenttype) {
        this.paymenttype = paymenttype;
    }

    public Double getPerformanceGarante() {
        return performanceGarante;
    }

    public void setPerformanceGarante(Double performanceGarante) {
        this.performanceGarante = performanceGarante;
    }

    public Date getContractsigningDate() {
        return contractsigningDate;
    }

    public void setContractsigningDate(Date contractsigningDate) {
        this.contractsigningDate = contractsigningDate;
    }

    public String getContractName() {
        return contractName;
    }

    public void setContractName(String contractName) {
        this.contractName = contractName;
    }

    public Double getContractamount() {
        return contractamount;
    }

    public void setContractamount(Double contractamount) {
        this.contractamount = contractamount;
    }

    public String getCommencementdateam() {
        return commencementdateam;
    }

    public void setCommencementdateam(String commencementdateam) {
        this.commencementdateam = commencementdateam;
    }

    public String getContractsigningdateam() {
        return contractsigningdateam;
    }

    public void setContractsigningdateam(String contractsigningdateam) {
        this.contractsigningdateam = contractsigningdateam;
    }

    public String getContractenddateam() {
        return contractenddateam;
    }

    public void setContractenddateam(String contractenddateam) {
        this.contractenddateam = contractenddateam;
    }

    public FmsLuCurrency getCurrency() {
        return currency;
    }

    public void setCurrency(FmsLuCurrency currency) {
        this.currency = currency;
    }

    public PmsCreateProjects getProjectId() {
        return projectId;
    }

    public void setProjectId(PmsCreateProjects projectId) {
        this.projectId = projectId;
    }

    public PrmsAward getAwardId() {
        return awardId;
    }

    public void setAwardId(PrmsAward awardId) {
        this.awardId = awardId;
    }

    public PrmsBid getBidId() {
        return bidId;
    }

    public void setBidId(PrmsBid bidId) {
        this.bidId = bidId;
    }

    public PrmsContract getContractId() {
        return contractId;
    }

    public void setContractId(PrmsContract contractId) {
        this.contractId = contractId;
    }

    public PrmsSupplyProfile getSuppId() {
        return suppId;
    }

    public void setSuppId(PrmsSupplyProfile suppId) {
        this.suppId = suppId;
    }

    public PrmsLuDmArchive getDocumentupId() {
        return documentupId;
    }

    public void setDocumentupId(PrmsLuDmArchive documentupId) {
        this.documentupId = documentupId;
    }

    @XmlTransient
    public List<PrmsContractAmendmentDt> getPrmsContractAmendmentDtList() {
        if (prmsContractAmendmentDtList == null) {
            prmsContractAmendmentDtList = new ArrayList<>();
        }
        return prmsContractAmendmentDtList;
    }

    public void setPrmsContractAmendmentDtList(
            List<PrmsContractAmendmentDt> prmsContractAmendmentDtList) {
        this.prmsContractAmendmentDtList = prmsContractAmendmentDtList;
    }

    public List<PrmsContractamendCurrency> getPrmsContractACurrencyDetailList() {

        if (prmsContractACurrencyDetailList == null) {
            prmsContractACurrencyDetailList = new ArrayList<>();
        }
        return prmsContractACurrencyDetailList;
    }

    public void setPrmsContractACurrencyDetailList(
            List<PrmsContractamendCurrency> prmsContractACurrencyDetailList) {
        this.prmsContractACurrencyDetailList = prmsContractACurrencyDetailList;
    }

    @XmlTransient
    public List<WfPrmsProcessed> getPrmsWorkFlowProccedList() {
        if (prmsWorkFlowProccedList == null) {
            prmsWorkFlowProccedList = new ArrayList<>();
        }
        return prmsWorkFlowProccedList;
    }

    public void setHrWorkFlowProccedList(List<WfPrmsProcessed> prmsWorkFlowProccedList) {
        this.prmsWorkFlowProccedList = prmsWorkFlowProccedList;
    }

    public void addCOntractAmeCurrInfo(PrmsContractamendCurrency papmscurrobj) {
        if (papmscurrobj.getContractId() != this) {
            this.getPrmsContractACurrencyDetailList().add(papmscurrobj);
            papmscurrobj.setContractId(this);
        }
    }

    public void addCOntractCurrInfo(PrmsContractamendCurrency papmscurrobj) {

        if (papmscurrobj.getContractId() != this) {
            this.getPrmsContractACurrencyDetailList().add(papmscurrobj);
            papmscurrobj.setContractId(this);
            System.out.println("===add---" + papmscurrobj.getContractId());
        }
    }
}
