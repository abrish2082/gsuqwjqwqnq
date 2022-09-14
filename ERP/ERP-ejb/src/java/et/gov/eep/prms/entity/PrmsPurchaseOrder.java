/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.prms.entity;

import et.gov.eep.commonApplications.entity.WfPrmsProcessed;
import et.gov.eep.hrms.entity.organization.HrDepartments;
import et.gov.eep.mms.entity.MmsInspection;
import java.io.Serializable;
import java.math.BigInteger;
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
import javax.persistence.Transient;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author user
 */
@Entity
@Table(name = "PRMS_PURCHASE_ORDER")//, catalog = "", schema = "EEP")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PrmsPurchaseOrder.findAll", query = "SELECT p FROM PrmsPurchaseOrder p "),
    @NamedQuery(name = "PrmsPurchaseOrder.findAlls", query = "SELECT p FROM PrmsPurchaseOrder p WHERE p.status=3"),
    @NamedQuery(name = "PrmsPurchaseOrder.findAllbyStatus", query = "SELECT p FROM PrmsPurchaseOrder p WHERE p.status=0"),
    @NamedQuery(name = "PrmsPurchaseOrder.findByPoId", query = "SELECT p FROM PrmsPurchaseOrder p WHERE p.requestedBy=:requestedBy AND p.poId LIKE :poId"),
//    @NamedQuery(name = "PrmsPurchaseOrder.findByPORequeted", query = "SELECT p FROM PrmsPurchaseOrder p WHERE p.status=0"),
    @NamedQuery(name = "PrmsPurchaseOrder.SearchPoId", query = "SELECT p FROM PrmsPurchaseOrder p WHERE p.poId LIKE :poId"),
    @NamedQuery(name = "PrmsPurchaseOrder.findByPacNo", query = "SELECT p FROM PrmsPurchaseOrder p WHERE p.pacNo = :pacNo"),
    @NamedQuery(name = "PrmsPurchaseOrder.SearchfindByPacNo", query = "SELECT p FROM PrmsPurchaseOrder p WHERE p.pacNo LIKE :pacNo and p.requestedBy=:requestedBy ORDER BY p.pacNo ASC"),
    @NamedQuery(name = "PrmsPurchaseOrder.findByRigistrationDate", query = "SELECT p FROM PrmsPurchaseOrder p WHERE p.rigistrationDate = :rigistrationDate"),
    @NamedQuery(name = "PrmsPurchaseOrder.findByMaxBidOfferNum", query = "SELECT p FROM PrmsPurchaseOrder p WHERE p.poId = (SELECT Max(c.poId)  from PrmsPurchaseOrder c)"),
    @NamedQuery(name = "PrmsPurchaseOrder.finAllWithzero", query = "SELECT p FROM PrmsPurchaseOrder p WHERE p.approvedStatus = :approvedStatus"),
    @NamedQuery(name = "PrmsPurchaseOrder.findByQuotationId", query = "SELECT p FROM PrmsPurchaseOrder p WHERE p.quotationId=:quotationId"),
    @NamedQuery(name = "PrmsPurchaseOrder.findByQuotationIdAndStatus", query = "SELECT p FROM PrmsPurchaseOrder p WHERE p.quotationId=:quotationId AND p.status=:status"),
    @NamedQuery(name = "PrmsPurchaseOrder.findByContactId", query = "SELECT p FROM PrmsPurchaseOrder p WHERE p.contractId = :contractId"),
    @NamedQuery(name = "PrmsPurchaseOrder.findByQuatationId", query = "SELECT p FROM PrmsPurchaseOrder p WHERE p.quotationId = :quotationId"),
    @NamedQuery(name = "PrmsPurchaseOrder.findByRequestedBy", query = "SELECT p FROM PrmsPurchaseOrder p WHERE p.requestedBy = :requestedBy"),
    @NamedQuery(name = "PrmsPurchaseOrder.findByApprovedBy", query = "SELECT p FROM PrmsPurchaseOrder p WHERE p.approvedBy = :approvedBy"),
    @NamedQuery(name = "PrmsPurchaseOrder.findByRemark", query = "SELECT p FROM PrmsPurchaseOrder p WHERE p.remark = :remark"),
    @NamedQuery(name = "PrmsPurchaseOrder.findByApprovedDate", query = "SELECT p FROM PrmsPurchaseOrder p WHERE p.approvedDate = :approvedDate"),
    @NamedQuery(name = "PrmsPurchaseOrder.findByWithhold", query = "SELECT p FROM PrmsPurchaseOrder p WHERE p.withhold = :withhold"),
    @NamedQuery(name = "PrmsPurchaseOrder.findByStatus", query = "SELECT p FROM PrmsPurchaseOrder p WHERE p.status = :status"),
    @NamedQuery(name = "PrmsPurchaseOrder.findByBidId", query = "SELECT p FROM PrmsPurchaseOrder p WHERE p.bidId = :bidId")})
public class PrmsPurchaseOrder implements Serializable {

    @Column(name = "APPROVED_STATUS")
    private Integer approvedStatus;
    @Column(name = "WITHHOLD")
    private double withhold;
    @Column(name = "STATUS")
    private Integer status;
    @OneToMany(mappedBy = "poId", fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private List<PrmsPoCurrency> prmsPoCurrencyList;
    @OneToMany(mappedBy = "purchaseOId")
    private List<MmsInspection> mmsInspectionList;
    @JoinColumn(name = "PURCHASE_REQUEST_ID", referencedColumnName = "ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private PrmsPurchaseRequest purchaseRequestId;
    @JoinColumn(name = "QUOTATION_ID", referencedColumnName = "QUATATION_ID")
    @ManyToOne
    private PrmsQuotation quotationId;
    @JoinColumn(name = "PAYMENT_REQ_ID", referencedColumnName = "PAYMENT_REQ_ID")
    @ManyToOne
    private PrmsPaymentRequisition paymentReqId;
    @JoinColumn(name = "CONTRACT_ID", referencedColumnName = "CONTRACT_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private PrmsContract contractId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "poId", fetch = FetchType.LAZY)
    private List<PrmsPaymentRequisition> prmsPaymentRequisitionList;
    @OneToMany(mappedBy = "poId", cascade = CascadeType.ALL)
    private List<PrmsSupplierPerformanceInfo> prmsSupplierPerformanceInfoList;
    @JoinColumn(name = "BID_ID", referencedColumnName = "ID")
    @ManyToOne
    private PrmsBid bidId;
    @Column(name = "MINUIT_NO")
    private Long minuitNo;
    @JoinColumn(name = "SUPP_ID", referencedColumnName = "ID")
    @ManyToOne
    private PrmsSupplyProfile suppId;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PRMS_PURCHASE_ORDER_SEQ")
    @SequenceGenerator(name = "PRMS_PURCHASE_ORDER_SEQ", sequenceName = "PRMS_PURCHASE_ORDER_SEQ", allocationSize = 1)
    @Size(min = 1, max = 100)
    @Column(name = "PO_ID")
    private String poId;
    @Size(max = 100)
    @Column(name = "PAC_NO")
    private String pacNo;
    @Size(max = 20)
    @Column(name = "TYPE")
    private String type;
    @Column(name = "RIGISTRATION_DATE")
    @Temporal(TemporalType.DATE)
    private Date rigistrationDate;
    @Size(max = 45)
    @Column(name = "REQUESTED_BY")
    private String requestedBy;
    @Size(max = 45)
    @Column(name = "APPROVED_BY")
    private String approvedBy;
    @Size(max = 45)
    @Column(name = "REMARK")
    private String remark;
    @Column(name = "APPROVED_DATE")
    @Temporal(TemporalType.DATE)
    private Date approvedDate;
    @Transient
    private String columnName;
    @Transient
    private String columnValue;
    @JoinColumn(name = "DEP_ID", referencedColumnName = "DEP_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private HrDepartments depId;
    @JoinColumn(name = "AWARD_ID", referencedColumnName = "AWARD_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private PrmsAward awardId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "poId", fetch = FetchType.LAZY)
    private List<PrmsPurOrderDetail> prmsPurOrderDetailList;
//    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "contractId", fetch = FetchType.LAZY)
//    private List<PrmsContractCurrencyDetail> prmsContractCurrencyDetailList;
    @OneToMany(mappedBy = "purchaseOrderId", cascade = CascadeType.ALL)
    private List<WfPrmsProcessed> prmsWorkFlowProccedList;

    public PrmsPurchaseOrder() {
    }

    public PrmsAward getAwardId() {
        return awardId;
    }

    public void setAwardId(PrmsAward awardId) {
        this.awardId = awardId;
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

    public PrmsPurchaseOrder(String poId) {
        this.poId = poId;
    }

    public String getPoId() {
        return poId;
    }

    public void setPoId(String poId) {
        this.poId = poId;
    }

    public String getPacNo() {
        return pacNo;
    }

    public void setPacNo(String pacNo) {
        this.pacNo = pacNo;
    }

    public Date getRigistrationDate() {
        return rigistrationDate;
    }

    public void setRigistrationDate(Date rigistrationDate) {
        this.rigistrationDate = rigistrationDate;
    }

    public String getRequestedBy() {
        return requestedBy;
    }

    public void setRequestedBy(String requestedBy) {
        this.requestedBy = requestedBy;
    }

    public String getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(String approvedBy) {
        this.approvedBy = approvedBy;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getApprovedDate() {
        return approvedDate;
    }

    public void setApprovedDate(Date approvedDate) {
        this.approvedDate = approvedDate;
    }

    public HrDepartments getDepId() {
        return depId;
    }

    public void setDepId(HrDepartments depId) {
        this.depId = depId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getWithhold() {
        return withhold;
    }

    public void setWithhold(double withhold) {
        this.withhold = withhold;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @XmlTransient
    public List<PrmsPurOrderDetail> getPrmsPurOrderDetailList() {
        if (prmsPurOrderDetailList == null) {
            prmsPurOrderDetailList = new ArrayList<>();
        }
        return prmsPurOrderDetailList;
    }

    public void setPrmsPurOrderDetailList(List<PrmsPurOrderDetail> prmsPurOrderDetailList) {
        this.prmsPurOrderDetailList = prmsPurOrderDetailList;
    }

//    @XmlTransient
//    public List<PrmsContractCurrencyDetail> getPrmsContractCurrencyDetailList() {
//
//        if (prmsContractCurrencyDetailList == null) {
//            prmsContractCurrencyDetailList = new ArrayList<>();
//        }
//
//        return prmsContractCurrencyDetailList;
//    }
//
//    public void setPrmsContractCurrencyDetailList(List<PrmsContractCurrencyDetail> prmsContractCurrencyDetailList) {
//        this.prmsContractCurrencyDetailList = prmsContractCurrencyDetailList;
//    }
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (poId != null ? poId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PrmsPurchaseOrder)) {
            return false;
        }
        PrmsPurchaseOrder other = (PrmsPurchaseOrder) object;
        if ((this.poId == null && other.poId != null) || (this.poId != null && !this.poId.equals(other.poId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return pacNo;
    }

    public void addPurchaseOrderDetialInfo(PrmsPurOrderDetail papmsAwardDetailobj) {
        if (papmsAwardDetailobj.getPoId() != this) {
            this.getPrmsPurOrderDetailList().add(papmsAwardDetailobj);

            papmsAwardDetailobj.setPoId(this);
        }

    }

    public PrmsSupplyProfile getSuppId() {
        return suppId;
    }

    public void setSuppId(PrmsSupplyProfile suppId) {
        this.suppId = suppId;
    }

    public Long getMinuitNo() {
        return minuitNo;
    }

    public void setMinuitNo(Long minuitNo) {
        this.minuitNo = minuitNo;
    }

    public PrmsBid getBidId() {
        return bidId;
    }

    public void setBidId(PrmsBid bidId) {
        this.bidId = bidId;
    }

    @XmlTransient
    public List<PrmsSupplierPerformanceInfo> getPrmsSupplierPerformanceInfoList() {
        if (prmsSupplierPerformanceInfoList == null) {
            prmsSupplierPerformanceInfoList = new ArrayList<>();
        }
        return prmsSupplierPerformanceInfoList;
    }

    public void setPrmsSupplierPerformanceInfoList(List<PrmsSupplierPerformanceInfo> prmsSupplierPerformanceInfoList) {
        this.prmsSupplierPerformanceInfoList = prmsSupplierPerformanceInfoList;
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

    public PrmsContract getContractId() {
        return contractId;
    }

    public void setContractId(PrmsContract contractId) {
        this.contractId = contractId;
    }

    public PrmsPaymentRequisition getPaymentReqId() {
        return paymentReqId;
    }

    public void setPaymentReqId(PrmsPaymentRequisition paymentReqId) {
        this.paymentReqId = paymentReqId;
    }

    public PrmsPurchaseRequest getPurchaseRequestId() {
        return purchaseRequestId;
    }

    public void setPurchaseRequestId(PrmsPurchaseRequest purchaseRequestId) {
        this.purchaseRequestId = purchaseRequestId;
    }

    public Integer getApprovedStatus() {
        return approvedStatus;
    }

    public void setApprovedStatus(Integer approvedStatus) {
        this.approvedStatus = approvedStatus;
    }

    @XmlTransient
    public List<MmsInspection> getMmsInspectionList() {
        return mmsInspectionList;
    }

    public void setMmsInspectionList(List<MmsInspection> mmsInspectionList) {
        this.mmsInspectionList = mmsInspectionList;
    }

    public PrmsQuotation getQuotationId() {
        return quotationId;
    }

    public void setQuotationId(PrmsQuotation quotationId) {
        this.quotationId = quotationId;
    }

    public void add(PrmsPurOrderDetail papmsPurchaseOrderDetail) {
        if (papmsPurchaseOrderDetail.getPoId() != this) {
            this.getPrmsPurOrderDetailList().add(papmsPurchaseOrderDetail);
            papmsPurchaseOrderDetail.setPoId(this);
        }
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
}
