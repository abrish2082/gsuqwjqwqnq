/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.prms.entity;

import et.gov.eep.commonApplications.entity.WfPrmsProcessed;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import et.gov.eep.prms.entity.PrmsQuotation;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.CascadeType;
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
@Table(name = "PRMS_SUPPLIER_PERFORMANCE_INFO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PrmsSupplierPerformanceInfo.findAll", query = "SELECT p FROM PrmsSupplierPerformanceInfo p"),
    @NamedQuery(name = "PrmsSupplierPerformanceInfo.findBySuppInfoId", query = "SELECT p FROM PrmsSupplierPerformanceInfo p WHERE p.suppInfoId = :suppInfoId"),
    @NamedQuery(name = "PrmsSupplierPerformanceInfo.findByReqForApproval", query = "SELECT p FROM PrmsSupplierPerformanceInfo p WHERE p.status =0"),
    @NamedQuery(name = "PrmsSupplierPerformanceInfo.findBySupPerfNos", query = "SELECT p FROM PrmsSupplierPerformanceInfo p WHERE p.supplierPerfoNo = :supplierPerfoNo"),
    @NamedQuery(name = "PrmsSupplierPerformanceInfo.findByDateRigistered", query = "SELECT p FROM PrmsSupplierPerformanceInfo p WHERE p.dateRigistered = :dateRigistered"),
    @NamedQuery(name = "PrmsPaymentRequisition.SearchById", query = "SELECT p FROM PrmsSupplierPerformanceInfo p WHERE p.supplierPerfoNo LIKE :supplierPerfoNo ORDER BY P.suppInfoId ASC"),
    @NamedQuery(name = "PrmsSupplierPerformanceInfo.findByDeliveryWrtTime", query = "SELECT p FROM PrmsSupplierPerformanceInfo p WHERE p.deliveryWrtTime = :deliveryWrtTime"),
    @NamedQuery(name = "PrmsSupplierPerformanceInfo.findByRemarkWrtCondition", query = "SELECT p FROM PrmsSupplierPerformanceInfo p WHERE p.remarkWrtCondition = :remarkWrtCondition"),
    @NamedQuery(name = "PrmsSupplierPerformanceInfo.findByPreparedBy", query = "SELECT p FROM PrmsSupplierPerformanceInfo p WHERE p.preparedBy = :preparedBy"),
    @NamedQuery(name = "PrmsSupplierPerformanceInfo.findByUserDeptComment", query = "SELECT p FROM PrmsSupplierPerformanceInfo p WHERE p.userDeptComment = :userDeptComment"),
    @NamedQuery(name = "PrmsSupplierPerformanceInfo.findByClaimAnswering", query = "SELECT p FROM PrmsSupplierPerformanceInfo p WHERE p.claimAnswering = :claimAnswering"),
    @NamedQuery(name = "PrmsSupplierPerformanceInfo.findByContractSignedDate", query = "SELECT p FROM PrmsSupplierPerformanceInfo p WHERE p.contractSignedDate = :contractSignedDate"),
    @NamedQuery(name = "PrmsSupplierPerformanceInfo.findByMaxSupplierNum", query = "SELECT p FROM PrmsSupplierPerformanceInfo p WHERE p.suppInfoId = (SELECT Max(c.suppInfoId)  from PrmsSupplierPerformanceInfo c)"),
    @NamedQuery(name = "PrmsSupplierPerformanceInfo.findByLcOpeningDate", query = "SELECT p FROM PrmsSupplierPerformanceInfo p WHERE p.lcOpeningDate = :lcOpeningDate"),
    @NamedQuery(name = "PrmsSupplierPerformanceInfo.findByPbExpiryDate", query = "SELECT p FROM PrmsSupplierPerformanceInfo p WHERE p.pbExpiryDate = :pbExpiryDate"),
    @NamedQuery(name = "PrmsSupplierPerformanceInfo.findByLcExpiryDate", query = "SELECT p FROM PrmsSupplierPerformanceInfo p WHERE p.lcExpiryDate = :lcExpiryDate"),
    @NamedQuery(name = "PrmsSupplierPerformanceInfo.findByPbRefNo", query = "SELECT p FROM PrmsSupplierPerformanceInfo p WHERE p.pbRefNo = :pbRefNo"),
    @NamedQuery(name = "PrmsSupplierPerformanceInfo.findByNoofLcExtention", query = "SELECT p FROM PrmsSupplierPerformanceInfo p WHERE p.noofLcExtention = :noofLcExtention"),
    @NamedQuery(name = "PrmsSupplierPerformanceInfo.findByLcExtCondition", query = "SELECT p FROM PrmsSupplierPerformanceInfo p WHERE p.lcExtCondition = :lcExtCondition"),
    @NamedQuery(name = "PrmsSupplierPerformanceInfo.findByPurchaseItemDescribiton", query = "SELECT p FROM PrmsSupplierPerformanceInfo p WHERE p.purchaseItemDescribiton = :purchaseItemDescribiton")})
public class PrmsSupplierPerformanceInfo implements Serializable {

    @Column(name = "PROCESSED_ON")
    @Temporal(TemporalType.TIMESTAMP)
    private Date processedOn;

    @OneToMany(mappedBy = "suppPerformanceId", cascade = CascadeType.ALL)
    private List<WfPrmsProcessed> prmsWorkFlowProccedList;

    @JoinColumn(name = "CONTRACT_ID", referencedColumnName = "CONTRACT_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private PrmsContract contractId;
    @JoinColumn(name = "BID_ID", referencedColumnName = "ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private PrmsBid bidId;
    @JoinColumn(name = "QUOTATION_ID", referencedColumnName = "QUATATION_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private PrmsQuotation quotationId;

    @JoinColumn(name = "CLAIM_ID", referencedColumnName = "CLAIM_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private PrmsClaimRecordingForm claimId;

    @JoinColumn(name = "LC_ID", referencedColumnName = "LC_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private PrmsLcRigistration lcId;

    @JoinColumn(name = "GOODS_ID", referencedColumnName = "ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private PrmsGoodsEntrance goodsId;

    @JoinColumn(name = "PO_ID", referencedColumnName = "PO_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private PrmsPurchaseOrder poId;

    @JoinColumn(name = "SUPP_ID", referencedColumnName = "ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private PrmsSupplyProfile suppId;

    @Size(max = 100)
    @Column(name = "SUPPLIER_PERFO_NO")
    private String supplierPerfoNo;

    @Size(max = 100)
    @Column(name = "CONTRACT_AMOUNT")
    private String contractAmount;

    @Size(max = 20)
    @Column(name = "TOTAL_AMOUNT")
    private String totalAmount;

    @Size(max = 30)
    @Column(name = "TYPE")
    private String type;

    @Size(max = 30)
    @Column(name = "CLAIM_DESCRIPTION")
    private String claimDescription;
    @Size(max = 30)
    @Column(name = "UNDELIVERED_BALANCE")
    private String undeliveredBalance;
    @Size(max = 30)
    @Column(name = "MARKET_APPROCH")
    private String marketApproch;
    @Size(max = 30)
    @Column(name = "GE_DESCRIPTION")
    private String geDescription;
    @Size(max = 30)
    @Column(name = "GE_AMOUNT")
    private String geAmount;

    @Column(name = "GOODS_AMOUNT")
    private BigInteger goodsAmount;
    @Size(max = 30)
    @Column(name = "GE_NO")
    private String geNo;

    @Column(name = "STATUS")
    private Integer status;

//    @Size(max = 30)
//    @Column(name = "CLAIM_NO")
//    private String claimNo;
    @Size(max = 30)
    @Column(name = "CONTRACT_NO")
    private String contractNo;

    @Size(max = 30)
    @Column(name = "BID_NO")
    private String bidNo;
    @Size(max = 30)
    @Column(name = "LC_NO")
    private String lcNo;

    @Size(max = 30)
    @Column(name = "QUOTATION_NO")
    private String quotationNo;

    @Size(max = 30)
    @Column(name = "TYPE2")
    private String type2;
    @Size(max = 20)
    @Column(name = "DELIVERY_WRT_TIME")
    private String deliveryWrtTime;
    @Size(max = 20)
    @Column(name = "DELIVERY_AMOUNT_INPERCENT")
    private String deliveryAmountInpercent;
    @Size(max = 20)
    @Column(name = "ITEM_QUALITY")
    private String itemQuality;
    @Size(max = 20)
    @Column(name = "WRT_PROCURMENT_DEPT")
    private String wrtProcurmentDept;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PRMS_SUPPLIER_PERFORMANCE_SEQ")
    @SequenceGenerator(name = "PRMS_SUPPLIER_PERFORMANCE_SEQ", sequenceName = "PRMS_SUPPLIER_PERFORMANCE_SEQ", allocationSize = 1)
    @Size(min = 1, max = 50)
    @Column(name = "SUPP_INFO_ID")
    private String suppInfoId;
    @Column(name = "DATE_RIGISTERED")
    @Temporal(TemporalType.DATE)
    private Date dateRigistered;
    @Size(max = 20)
    @Column(name = "REMARK_WRT_CONDITION")
    private String remarkWrtCondition;
    @Size(max = 20)
    @Column(name = "PREPARED_BY")
    private String preparedBy;
    @Size(max = 20)
    @Column(name = "USER_DEPT_COMMENT")
    private String userDeptComment;
    @Size(max = 20)
    @Column(name = "CLAIM_ANSWERING")
    private String claimAnswering;
    @Column(name = "CONTRACT_SIGNED_DATE")
    @Temporal(TemporalType.DATE)
    private Date contractSignedDate;
    @Column(name = "LC_OPENING_DATE")
    @Temporal(TemporalType.DATE)
    private Date lcOpeningDate;
    @Column(name = "PB_EXPIRY_DATE")
    @Temporal(TemporalType.DATE)
    private Date pbExpiryDate;
    @Column(name = "LC_EXPIRY_DATE")
    @Temporal(TemporalType.DATE)
    private Date lcExpiryDate;
    @Column(name = "PB_REF_NO")
    private Long pbRefNo;
    @Size(max = 20)
    @Column(name = "NOOF_LC_EXTENTION")
    private String noofLcExtention;
    @Column(name = "SELECT_OPT")
    private Integer selectOpt;
    @Size(max = 20)
    @Column(name = "LC_EXT_CONDITION")
    private String lcExtCondition;
    @Size(max = 50)
    @Column(name = "PURCHASE_ITEM_DESCRIBITON")
    private String purchaseItemDescribiton;
    @Transient
    private String columnName;
    @Transient
    private String columnValue;
    @Column(name = "LIQUIDITY_DAMAGE_AMOUNT")
    private Double liquidityDamageAmount;

    /**
     *
     */
    public PrmsSupplierPerformanceInfo() {
    }

    /**
     *
     * @param suppInfoId
     */
    public PrmsSupplierPerformanceInfo(String suppInfoId) {
        this.suppInfoId = suppInfoId;
    }

    /**
     *
     * @return
     */
    public String getSuppInfoId() {
        return suppInfoId;
    }

    /**
     *
     * @param suppInfoId
     */
    public void setSuppInfoId(String suppInfoId) {
        this.suppInfoId = suppInfoId;
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

    /**
     *
     * @return
     */
    
    
    public Date getDateRigistered() {
        return dateRigistered;
    }

    /**
     *
     * @param dateRigistered
     */
    public void setDateRigistered(Date dateRigistered) {
        this.dateRigistered = dateRigistered;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     *
     * @return
     */
    public String getRemarkWrtCondition() {
        return remarkWrtCondition;
    }

    /**
     *
     * @param remarkWrtCondition
     */
    public void setRemarkWrtCondition(String remarkWrtCondition) {
        this.remarkWrtCondition = remarkWrtCondition;
    }

    public String getQuotationNo() {
        return quotationNo;
    }

    public void setQuotationNo(String quotationNo) {
        this.quotationNo = quotationNo;
    }

    public String getBidNo() {
        return bidNo;
    }

    public void setBidNo(String bidNo) {
        this.bidNo = bidNo;
    }

    public Integer getSelectOpt() {
        return selectOpt;
    }

    public void setSelectOpt(Integer selectOpt) {
        this.selectOpt = selectOpt;
    }

    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }

    public String getGeNo() {
        return geNo;
    }

    public void setGeNo(String geNo) {
        this.geNo = geNo;
    }

    public String getLcNo() {
        return lcNo;
    }

    public void setLcNo(String lcNo) {
        this.lcNo = lcNo;
    }

    /**
     *
     * @return
     */
    public String getPreparedBy() {
        return preparedBy;
    }

    /**
     *
     * @param preparedBy
     */
    public void setPreparedBy(String preparedBy) {
        this.preparedBy = preparedBy;
    }

    /**
     *
     * @return
     */
    public String getUserDeptComment() {
        return userDeptComment;
    }

    /**
     *
     * @param userDeptComment
     */
    public void setUserDeptComment(String userDeptComment) {
        this.userDeptComment = userDeptComment;
    }

    public BigInteger getGoodsAmount() {
        return goodsAmount;
    }

    public void setGoodsAmount(BigInteger goodsAmount) {
        this.goodsAmount = goodsAmount;
    }

    /**
     *
     * @return
     */
    public String getClaimAnswering() {
        return claimAnswering;
    }

    /**
     *
     * @param claimAnswering
     */
    public void setClaimAnswering(String claimAnswering) {
        this.claimAnswering = claimAnswering;
    }

    /**
     *
     * @return
     */
    public Date getContractSignedDate() {
        return contractSignedDate;
    }

    /**
     *
     * @param contractSignedDate
     */
    public void setContractSignedDate(Date contractSignedDate) {
        this.contractSignedDate = contractSignedDate;
    }

    /**
     *
     * @return
     */
    public Date getLcOpeningDate() {
        return lcOpeningDate;
    }

    /**
     *
     * @param lcOpeningDate
     */
    public void setLcOpeningDate(Date lcOpeningDate) {
        this.lcOpeningDate = lcOpeningDate;
    }

    /**
     *
     * @return
     */
    public Date getPbExpiryDate() {
        return pbExpiryDate;
    }

    /**
     *
     * @param pbExpiryDate
     */
    public void setPbExpiryDate(Date pbExpiryDate) {
        this.pbExpiryDate = pbExpiryDate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getClaimDescription() {
        return claimDescription;
    }

    public void setClaimDescription(String claimDescription) {
        this.claimDescription = claimDescription;
    }

    public String getUndeliveredBalance() {
        return undeliveredBalance;
    }

    public void setUndeliveredBalance(String undeliveredBalance) {
        this.undeliveredBalance = undeliveredBalance;
    }

    public String getMarketApproch() {
        return marketApproch;
    }

    public void setMarketApproch(String marketApproch) {
        this.marketApproch = marketApproch;
    }

    public String getGeDescription() {
        return geDescription;
    }

    public void setGeDescription(String geDescription) {
        this.geDescription = geDescription;
    }

    public String getGeAmount() {
        return geAmount;
    }

    public void setGeAmount(String geAmount) {
        this.geAmount = geAmount;
    }

    /**
     *
     * @return
     */
    public Date getLcExpiryDate() {
        return lcExpiryDate;
    }

    /**
     *
     * @param lcExpiryDate
     */
    public void setLcExpiryDate(Date lcExpiryDate) {
        this.lcExpiryDate = lcExpiryDate;
    }

    /**
     *
     * @return
     */
    public Long getPbRefNo() {
        return pbRefNo;
    }

    /**
     *
     * @param pbRefNo
     */
    public void setPbRefNo(Long pbRefNo) {
        this.pbRefNo = pbRefNo;
    }

    /**
     *
     * @return
     */
    public String getNoofLcExtention() {
        return noofLcExtention;
    }

    /**
     *
     * @param noofLcExtention
     */
    public void setNoofLcExtention(String noofLcExtention) {
        this.noofLcExtention = noofLcExtention;
    }

    /**
     *
     * @return
     */
    public String getLcExtCondition() {
        return lcExtCondition;
    }

    /**
     *
     * @param lcExtCondition
     */
    public void setLcExtCondition(String lcExtCondition) {
        this.lcExtCondition = lcExtCondition;
    }

    /**
     *
     * @return
     */
    public String getPurchaseItemDescribiton() {
        return purchaseItemDescribiton;
    }

    public PrmsClaimRecordingForm getClaimId() {
        return claimId;
    }

    public void setClaimId(PrmsClaimRecordingForm claimId) {
        this.claimId = claimId;
    }

    public PrmsLcRigistration getLcId() {
        return lcId;
    }

    public void setLcId(PrmsLcRigistration lcId) {
        this.lcId = lcId;
    }

    /**
     *
     * @param purchaseItemDescribiton
     */
    public void setPurchaseItemDescribiton(String purchaseItemDescribiton) {
        this.purchaseItemDescribiton = purchaseItemDescribiton;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (suppInfoId != null ? suppInfoId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PrmsSupplierPerformanceInfo)) {
            return false;
        }
        PrmsSupplierPerformanceInfo other = (PrmsSupplierPerformanceInfo) object;
        if ((this.suppInfoId == null && other.suppInfoId != null) || (this.suppInfoId != null && !this.suppInfoId.equals(other.suppInfoId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return suppInfoId;
    }

    /**
     *
     * @return
     */
    public String getDeliveryAmountInpercent() {
        return deliveryAmountInpercent;
    }

    /**
     *
     * @param deliveryAmountInpercent
     */
    public void setDeliveryAmountInpercent(String deliveryAmountInpercent) {
        this.deliveryAmountInpercent = deliveryAmountInpercent;
    }

    /**
     *
     * @return
     */
    public String getItemQuality() {
        return itemQuality;
    }

    /**
     *
     * @param itemQuality
     */
    public void setItemQuality(String itemQuality) {
        this.itemQuality = itemQuality;
    }

    /**
     *
     * @return
     */
    public String getWrtProcurmentDept() {
        return wrtProcurmentDept;
    }

    /**
     *
     * @param wrtProcurmentDept
     */
    public void setWrtProcurmentDept(String wrtProcurmentDept) {
        this.wrtProcurmentDept = wrtProcurmentDept;
    }

    /**
     *
     * @return
     */
    public String getDeliveryWrtTime() {
        return deliveryWrtTime;
    }

    /**
     *
     * @param deliveryWrtTime
     */
    public void setDeliveryWrtTime(String deliveryWrtTime) {
        this.deliveryWrtTime = deliveryWrtTime;
    }

    public String getType2() {
        return type2;
    }

    public void setType2(String type2) {
        this.type2 = type2;
    }

    /**
     *
     * @return
     */
    public String getTotalAmount() {
        return totalAmount;
    }

    /**
     *
     * @param totalAmount
     */
    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public PrmsPurchaseOrder getPoId() {
        return poId;
    }

    public void setPoId(PrmsPurchaseOrder poId) {
        this.poId = poId;
    }

    public PrmsSupplyProfile getSuppId() {
        return suppId;
    }

    public void setSuppId(PrmsSupplyProfile suppId) {
        this.suppId = suppId;
    }

    public String getSupplierPerfoNo() {
        return supplierPerfoNo;
    }

    public void setSupplierPerfoNo(String supplierPerfoNo) {
        this.supplierPerfoNo = supplierPerfoNo;
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

    public PrmsQuotation getQuotationId() {
        return quotationId;
    }

    public void setQuotationId(PrmsQuotation quotationId) {
        this.quotationId = quotationId;
    }

    public Double getLiquidityDamageAmount() {
        return liquidityDamageAmount;
    }

    public void setLiquidityDamageAmount(Double liquidityDamageAmount) {
        this.liquidityDamageAmount = liquidityDamageAmount;
    }

    /**
     * @return the prmsWorkFlowProccedList
     */
    public List<WfPrmsProcessed> getPrmsWorkFlowProccedList() {
        if (prmsWorkFlowProccedList == null) {
            prmsWorkFlowProccedList = new ArrayList<>();
        }
        return prmsWorkFlowProccedList;
    }

    /**
     * @param prmsWorkFlowProccedList the prmsWorkFlowProccedList to set
     */
    public void setPrmsWorkFlowProccedList(List<WfPrmsProcessed> prmsWorkFlowProccedList) {
        this.prmsWorkFlowProccedList = prmsWorkFlowProccedList;
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
     * @return the goodsId
     */
    public PrmsGoodsEntrance getGoodsId() {
        return goodsId;
    }

    /**
     * @param goodsId the goodsId to set
     */
    public void setGoodsId(PrmsGoodsEntrance goodsId) {
        this.goodsId = goodsId;
    }

    /**
     * @return the contractAmount
     */
    public String getContractAmount() {
        return contractAmount;
    }

    /**
     * @param contractAmount the contractAmount to set
     */
    public void setContractAmount(String contractAmount) {
        this.contractAmount = contractAmount;
    }

}
