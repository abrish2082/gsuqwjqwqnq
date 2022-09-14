/**
 * *****************************************************************************
 *
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates and open the template
 * in the editor.
 *
 *****************************************************************************
 */
package et.gov.eep.prms.entity;

import et.gov.eep.commonApplications.entity.PrmsLuDmArchive;
import et.gov.eep.commonApplications.entity.WfPrmsProcessed;
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
 * *****************************************************************************
 *
 * @author User
 * ***************************************************************************
 */
@Entity
@Table(name = "PRMS_CONTRACT")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PrmsContract.findAll", query = "SELECT p FROM PrmsContract p"),
    @NamedQuery(name = "PrmsContract.findAlls", query = "SELECT p FROM PrmsContract p WHERE p.status=3"),
    @NamedQuery(name = "PrmsContract.findAllbyStatus", query = "SELECT p FROM PrmsContract p WHERE p.status=0"),
    @NamedQuery(name = "PrmsContract.findByContractId", query = "SELECT p FROM PrmsContract p WHERE p.contractId = :contractId"),
    @NamedQuery(name = "PrmsContract.findByBidId", query = "SELECT p FROM PrmsContract p WHERE p.bidId = :bidId"),
    @NamedQuery(name = "PrmsContract.findByBidIdAndStatus", query = "SELECT p FROM PrmsContract p WHERE p.bidId = :bidId AND p.status=:status"),
    @NamedQuery(name = "PrmsContract.searchfindByContractNo", query = "SELECT p FROM PrmsContract p WHERE p.contractNo LIKE :contractNo and p.preparedBy=:preparedBy"),
    @NamedQuery(name = "PrmsContract.findByMaxcontractId", query = "SELECT p FROM PrmsContract p WHERE p.contractId =(SELECT Max(p.contractId)  from PrmsContract p)"),
    @NamedQuery(name = "PrmsContract.findByContractsigningDate", query = "SELECT p FROM PrmsContract p WHERE p.contractsigningDate = :contractsigningDate"),
    @NamedQuery(name = "PrmsContract.findByDocumentId", query = "SELECT p FROM PrmsContract p WHERE p.documentId = :documentId"),
    @NamedQuery(name = "PrmsContract.findByContractPeriodForm", query = "SELECT p FROM PrmsContract p WHERE p.contractPeriodForm = :contractPeriodForm"),
    @NamedQuery(name = "PrmsContract.findByContractPeriodTo", query = "SELECT p FROM PrmsContract p WHERE p.contractPeriodTo = :contractPeriodTo"),
    @NamedQuery(name = "PrmsContract.findByDeliveryPlace", query = "SELECT p FROM PrmsContract p WHERE p.deliveryPlace = :deliveryPlace"),
    @NamedQuery(name = "PrmsContract.findByPreparedBy", query = "SELECT p FROM PrmsContract p WHERE p.preparedBy = :preparedBy"),
    @NamedQuery(name = "PrmsContract.findByPreparedByTwo", query = "SELECT p FROM PrmsContract p WHERE p.preparedBy = :preparedBy OR p.contractNo = :contractNo"),
    @NamedQuery(name = "PrmsContract.findByPreparedDate", query = "SELECT p FROM PrmsContract p WHERE p.preparedDate = :preparedDate"),
    @NamedQuery(name = "PrmsContract.findByRemark", query = "SELECT p FROM PrmsContract p WHERE p.remark = :remark"),
    @NamedQuery(name = "PrmsContract.findByContractName", query = "SELECT p FROM PrmsContract p WHERE p.contractName = :contractName"),
    @NamedQuery(name = "PrmsContract.findBySuppId", query = "SELECT p FROM PrmsContract p WHERE p.suppId = :suppId"),
    @NamedQuery(name = "PrmsContract.findByContractNo", query = "SELECT p FROM PrmsContract p WHERE p.contractNo = :contractNo"),
    @NamedQuery(name = "PrmsContract.findByCommencmentDate", query = "SELECT p FROM PrmsContract p WHERE p.commencmentDate = :commencmentDate"),
    @NamedQuery(name = "PrmsContract.findByPerformanceGarante", query = "SELECT p FROM PrmsContract p WHERE p.performanceGarante = :performanceGarante"),
    @NamedQuery(name = "PrmsContract.findByIncoTerm", query = "SELECT p FROM PrmsContract p WHERE p.incoTerm = :incoTerm"),
    @NamedQuery(name = "PrmsContract.findByPaymenttype", query = "SELECT p FROM PrmsContract p WHERE p.paymenttype = :paymenttype"),
    @NamedQuery(name = "PrmsContract.findByContractperiodtype", query = "SELECT p FROM PrmsContract p WHERE p.contractperiodtype = :contractperiodtype"),
    @NamedQuery(name = "PrmsContract.findByContractamount", query = "SELECT p FROM PrmsContract p WHERE p.contractamount = :contractamount"),
    @NamedQuery(name = "PrmsContract.findByContractendDate", query = "SELECT p FROM PrmsContract p WHERE p.contractendDate = :contractendDate"),
    @NamedQuery(name = "PrmsContract.findByCommencementdateam", query = "SELECT p FROM PrmsContract p WHERE p.commencementdateam = :commencementdateam"),
    @NamedQuery(name = "PrmsContract.findByContractdateam", query = "SELECT p FROM PrmsContract p WHERE p.contractdateam = :contractdateam"),
    @NamedQuery(name = "PrmsContract.findByStatus", query = "SELECT p FROM PrmsContract p WHERE p.status = :status"),
    @NamedQuery(name = "PrmsContract.findByContractenddateam", query = "SELECT p FROM PrmsContract p WHERE p.contractenddateam = :contractenddateam")})

public class PrmsContract implements Serializable {

    @Id
    @Basic(optional = false)
    @NotNull
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PRMS_CONTRACT_SEQ")
    @SequenceGenerator(name = "PRMS_CONTRACT_SEQ", sequenceName = "PRMS_CONTRACT_SEQ", allocationSize = 1)
    @Column(name = "CONTRACT_ID", precision = 22)
    private BigDecimal contractId;
    //***********************Manuall*********************************************************************
    @OneToMany(mappedBy = "contractNo", fetch = FetchType.LAZY)
    private List<PrmsInsuranceRequisition> prmsInsuranceRequisitionList;
    @OneToMany(mappedBy = "contractId", fetch = FetchType.LAZY)
    private List<PrmsContractAmendment> prmsContractAmendmentList;
    @OneToMany(mappedBy = "contractId", cascade = CascadeType.ALL)
    private List<PrmsContractFileUpload> prmsContractFileUploadList;
    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "contractId", fetch = FetchType.LAZY)
    private List<PrmsSupplierPerformanceInfo> prmsSupplierPerformanceInfoList;
    @OneToMany(mappedBy = "contractId", fetch = FetchType.LAZY)
    private List<PrmsBankClearance> prmsBankClearanceList;
    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "contractId", fetch = FetchType.LAZY)
    private List<PrmsPurchaseOrder> prmsPurchaseOrderList;
    @OneToMany(mappedBy = "contractId", fetch = FetchType.LAZY)
    private List<PrmsPaymentRequisition> prmsPaymentRequisitionList;
    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "contractId", fetch = FetchType.LAZY)
    private List<PrmsContractDetail> prmsContractDetailList;
    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "contractId", fetch = FetchType.LAZY)
    private List<PrmsContractCurrencyDetail> prmsContractCurrencyDetailList;
    @OneToMany(mappedBy = "contractId", cascade = CascadeType.ALL)
    private List<WfPrmsProcessed> prmsWorkFlowProccedList;
    @Column(name = "CONTRACTSIGNING_DATE")
    @Temporal(TemporalType.DATE)
    private Date contractsigningDate;
    @Column(name = "DOCUMENT_ID")
    private String documentId;
    @Column(name = "CONTRACT_PERIOD_FORM")
    @Temporal(TemporalType.DATE)
    private Date contractPeriodForm;
    @Column(name = "CONTRACT_PERIOD_TO")
    private Integer contractPeriodTo;
    @Size(max = 100)
    @Column(name = "DELIVERY_PLACE")
    private String deliveryPlace;
    @Size(max = 50)
    @Column(name = "PREPARED_BY")
    private String preparedBy;
    @Column(name = "PREPARED_DATE")
    @Temporal(TemporalType.DATE)
    private Date preparedDate;
    @Size(max = 100)
    @Column(name = "REMARK")
    private String remark;
    @Size(max = 50)
    @Column(name = "CONTRACT_NAME")
    private String contractName;
    @Column(name = "FILENAME")
    private Integer filename;
    @Size(max = 100)
    @Column(name = "CONTRACT_NO")
    private String contractNo;
    @Column(name = "COMMENCMENT_DATE")
    @Temporal(TemporalType.DATE)
    private Date commencmentDate;
    @Column(name = "PERFORMANCE_GARANTE")
    private Double performanceGarante;
    @Size(max = 50)
    @Column(name = "INCO_TERM")
    private String incoTerm;
    @Size(max = 20)
    @Column(name = "PAYMENTTYPE")
    private String paymenttype;
    @Size(max = 20)
    @Column(name = "CONTRACTPERIODTYPE")
    private String contractperiodtype;
    @Column(name = "CONTRACTAMOUNT")
    private Double contractamount;
    @Column(name = "CONTRACTEND_DATE")
    @Temporal(TemporalType.DATE)
    private Date contractendDate;
    @Column(name = "COMMENCEMENTDATEAM")
    private String commencementdateam;
    @Size(max = 20)
    @Column(name = "CONTRACTDATEAM")
    private String contractdateam;
    @Size(max = 20)
    @Column(name = "CONTRACTENDDATEAM")
    private String contractenddateam;
    @Column(name = "STATUS")
    private Integer status;
    @Column(name = "RIGISTERED_DATE")
    @Temporal(TemporalType.DATE)
    private Date rigisteredDate;
    @Transient
    private String columnName;
    @Transient
    private String columnValue;
    @JoinColumn(name = "PROJECT_ID", referencedColumnName = "PROJECT_ID")
    @ManyToOne
    private PmsCreateProjects projectId;
    @JoinColumn(name = "AWARD_ID", referencedColumnName = "AWARD_ID")
    @ManyToOne
    private PrmsAward awardId;
    @JoinColumn(name = "BID_ID", referencedColumnName = "ID")
    @ManyToOne
    private PrmsBid bidId;
    @JoinColumn(name = "QUOTATION_ID", referencedColumnName = "QUATATION_ID")
    @ManyToOne
    private PrmsQuotation quotationId;
    @JoinColumn(name = "SUPP_ID", referencedColumnName = "ID")
    @ManyToOne
    private PrmsSupplyProfile suppId;
    @JoinColumn(name = "DOCUMENTUP_ID", referencedColumnName = "DOCUMENT_ID")
    @ManyToOne
    private PrmsLuDmArchive documentupId;

    public BigDecimal getContractId() {
        return contractId;
    }

    public void setContractId(BigDecimal contractId) {
        this.contractId = contractId;
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

    public Date getContractsigningDate() {
        return contractsigningDate;
    }

    public void setContractsigningDate(Date contractsigningDate) {
        this.contractsigningDate = contractsigningDate;
    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public Date getContractPeriodForm() {
        return contractPeriodForm;
    }

    public void setContractPeriodForm(Date contractPeriodForm) {
        this.contractPeriodForm = contractPeriodForm;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getFilename() {
        return filename;
    }

    public void setFilename(Integer filename) {
        this.filename = filename;
    }

    public String getContractName() {
        return contractName;
    }

    public void setContractName(String contractName) {
        this.contractName = contractName;
    }

    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }

    public Date getCommencmentDate() {
        return commencmentDate;
    }

    public void setCommencmentDate(Date commencmentDate) {
        this.commencmentDate = commencmentDate;
    }

    public Double getPerformanceGarante() {
        return performanceGarante;
    }

    public void setPerformanceGarante(Double performanceGarante) {
        this.performanceGarante = performanceGarante;
    }

    public String getIncoTerm() {
        return incoTerm;
    }

    public void setIncoTerm(String incoTerm) {
        this.incoTerm = incoTerm;
    }

    public String getPaymenttype() {
        return paymenttype;
    }

    public void setPaymenttype(String paymenttype) {
        this.paymenttype = paymenttype;
    }

    public String getContractperiodtype() {
        return contractperiodtype;
    }

    public void setContractperiodtype(String contractperiodtype) {
        this.contractperiodtype = contractperiodtype;
    }

    public Double getContractamount() {
        return contractamount;
    }

    public void setContractamount(Double contractamount) {
        this.contractamount = contractamount;
    }

    public Date getContractendDate() {
        return contractendDate;
    }

    public void setContractendDate(Date contractendDate) {
        this.contractendDate = contractendDate;
    }

    public String getCommencementdateam() {
        return commencementdateam;
    }

    public void setCommencementdateam(String commencementdateam) {
        this.commencementdateam = commencementdateam;
    }

    public String getContractdateam() {
        return contractdateam;
    }

    public void setContractdateam(String contractdateam) {
        this.contractdateam = contractdateam;
    }

    public String getContractenddateam() {
        return contractenddateam;
    }

    public void setContractenddateam(String contractenddateam) {
        this.contractenddateam = contractenddateam;
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

    public PrmsQuotation getQuotationId() {
        return quotationId;
    }

    public void setQuotationId(PrmsQuotation quotationId) {
        this.quotationId = quotationId;
    }

    public PrmsSupplyProfile getSuppId() {
        return suppId;
    }

    public void setSuppId(PrmsSupplyProfile suppId) {
        this.suppId = suppId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getRigisteredDate() {
        return rigisteredDate;
    }

    public void setRigisteredDate(Date rigisteredDate) {
        this.rigisteredDate = rigisteredDate;
    }

    public PrmsLuDmArchive getDocumentupId() {
        return documentupId;
    }

    public void setDocumentupId(PrmsLuDmArchive documentupId) {
        this.documentupId = documentupId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (contractId != null ? contractId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PrmsContract)) {
            return false;
        }

        PrmsContract other = (PrmsContract) object;

        if ((this.contractId == null && other.contractId != null)
                || (this.contractId != null && !this.contractId.equals(
                        other.contractId))) {
            return false;
        }

        return true;
    }

    @Override
    public String toString() {
        return "et.gov.eep.prms.entity.PrmsContract[ contractId=" + contractId + " ]";
    }

    @XmlTransient
    public List<PrmsContractDetail> getPrmsContractDetailList() {

        if (prmsContractDetailList == null) {
            prmsContractDetailList = new ArrayList<>();
        }

        return prmsContractDetailList;
    }

    public void setPrmsContractDetailList(
            List<PrmsContractDetail> prmsContractDetailList) {
        this.prmsContractDetailList = prmsContractDetailList;
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

    public void addCOntractDetialInfo(PrmsContractDetail papmsAwardDetailobj) {

        if (papmsAwardDetailobj.getContractId() != this) {
            this.getPrmsContractDetailList().add(papmsAwardDetailobj);

            papmsAwardDetailobj.setContractId(this);
        }
    }

    @XmlTransient
    public List<PrmsPaymentRequisition> getPrmsPaymentRequisitionList() {
        if (prmsPaymentRequisitionList == null) {
            prmsPaymentRequisitionList = new ArrayList<>();
        }
        return prmsPaymentRequisitionList;
    }

    public void setPrmsPaymentRequisitionList(
            List<PrmsPaymentRequisition> prmsPaymentRequisitionList) {
        this.prmsPaymentRequisitionList = prmsPaymentRequisitionList;
    }

    @XmlTransient
    public List<PrmsPurchaseOrder> getPrmsPurchaseOrderList() {
        if (prmsPurchaseOrderList == null) {
            prmsPurchaseOrderList = new ArrayList<>();
        }
        return prmsPurchaseOrderList;
    }

    public void setPrmsPurchaseOrderList(List<PrmsPurchaseOrder> prmsPurchaseOrderList) {
        this.prmsPurchaseOrderList = prmsPurchaseOrderList;
    }

    @XmlTransient
    public List<PrmsSupplierPerformanceInfo> getPrmsSupplierPerformanceInfoList() {
        if (prmsSupplierPerformanceInfoList == null) {
            prmsSupplierPerformanceInfoList = new ArrayList<>();
        }
        return prmsSupplierPerformanceInfoList;
    }

    public void setPrmsSupplierPerformanceInfoList(
            List<PrmsSupplierPerformanceInfo> prmsSupplierPerformanceInfoList) {
        this.prmsSupplierPerformanceInfoList = prmsSupplierPerformanceInfoList;
    }

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
    public List<PrmsInsuranceRequisition> getPrmsInsuranceRequisitionList() {

        if (prmsInsuranceRequisitionList == null) {
            prmsInsuranceRequisitionList = new ArrayList<>();
        }
        return prmsInsuranceRequisitionList;
    }

    public void setPrmsInsuranceRequisitionList(
            List<PrmsInsuranceRequisition> prmsInsuranceRequisitionList) {
        this.prmsInsuranceRequisitionList = prmsInsuranceRequisitionList;
    }

    @XmlTransient
    public List<PrmsContractAmendment> getPrmsContractAmendmentList() {

        if (prmsContractAmendmentList == null) {
            prmsContractAmendmentList = new ArrayList<>();
        }
        return prmsContractAmendmentList;
    }

    public void setPrmsContractAmendmentList(
            List<PrmsContractAmendment> prmsContractAmendmentList) {
        this.prmsContractAmendmentList = prmsContractAmendmentList;
    }

    @XmlTransient
    public List<PrmsContractFileUpload> getPrmsContractFileUploadList() {
        if (prmsContractFileUploadList == null) {
            prmsContractFileUploadList = new ArrayList<>();
        }
        return prmsContractFileUploadList;
    }

    public void setPrmsContractFileUploadList(
            List<PrmsContractFileUpload> prmsContractFileUploadList) {
        this.prmsContractFileUploadList = prmsContractFileUploadList;
    }

    public void add(PrmsContractFileUpload hrDisciplineLocal) {
        if (hrDisciplineLocal.getContractId() != this) {
            this.getPrmsContractFileUploadList().add(hrDisciplineLocal);
            hrDisciplineLocal.setContractId(this);
            System.out.println("===add---" + hrDisciplineLocal.getContractId());
        }
    }

    /**
     * @return the prmsContractCurrencyDetailList
     */
    public List<PrmsContractCurrencyDetail> getPrmsContractCurrencyDetailList() {
        if (prmsContractCurrencyDetailList == null) {
            prmsContractCurrencyDetailList = new ArrayList<>();
        }
        return prmsContractCurrencyDetailList;
    }

    /**
     * ************************************************************************
     * @param prmsContractCurrencyDetailList the prmsContractCurrencyDetailList
     * to set
     *************************************************************************
     */
    public void setPrmsContractCurrencyDetailList(
            List<PrmsContractCurrencyDetail> prmsContractCurrencyDetailList) {
        this.prmsContractCurrencyDetailList = prmsContractCurrencyDetailList;
    }

    public void addCOntractCurrInfo(PrmsContractCurrencyDetail papmscurrobj) {
        if (papmscurrobj.getContractId() != this) {
            this.getPrmsContractCurrencyDetailList().add(papmscurrobj);
            papmscurrobj.setContractId(this);
            System.out.println("===add---" + papmscurrobj.getContractId());
        }
    }
}
