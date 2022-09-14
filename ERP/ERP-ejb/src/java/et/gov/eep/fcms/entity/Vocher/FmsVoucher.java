/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.entity.Vocher;

import et.gov.eep.commonApplications.entity.WfFcmsProcessed;
import et.gov.eep.fcms.entity.FmsAccountUse;
import et.gov.eep.fcms.entity.FmsDocumentFollowup;
import et.gov.eep.fcms.entity.FmsPettyCashPaymentVoucher;
import et.gov.eep.fcms.entity.FmsSuspensePaymentVocher;
import et.gov.eep.fcms.entity.FmsVatRecieptVoucher;
import et.gov.eep.fcms.entity.FmsWithHoldingVoucher;
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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
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
 *
 * @author AB
 */
@Entity
@Table(name = "FMS_VOUCHER")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FmsVoucher.findAll", query = "SELECT f FROM FmsVoucher f"),
    @NamedQuery(name = "FmsVoucher.findByVoucherId", query = "SELECT f FROM FmsVoucher f WHERE f.voucherId = :voucherId "),
    @NamedQuery(name = "FmsVoucher.findByVoucherByType", query = "SELECT f FROM FmsVoucher f WHERE f.voucherId LIKE :voucherId AND  f.type= :type"),
    @NamedQuery(name = "FmsVoucher.findByType", query = "SELECT f FROM FmsVoucher f WHERE f.type = :type"),
    @NamedQuery(name = "FmsVoucher.findByStatus", query = "SELECT f FROM FmsVoucher f WHERE f.status = :status"),
    @NamedQuery(name = "FmsVoucher.findByStatusUPDATE", query = " UPDATE     FmsVoucher f SET f.status = 3 WHERE f.status = 2 "),
    @NamedQuery(name = "FmsVoucher.findByStatusCpo", query = " SELECT f FROM FmsVoucher f WHERE f.status = 0 AND F.type='CPO' "),
    @NamedQuery(name = "FmsVoucher.findByStatusCrv", query = " SELECT f FROM FmsVoucher f WHERE f.status = 0 AND F.type='CRV' "),
    @NamedQuery(name = "FmsVoucher.findByStatusChpv", query = " SELECT f FROM FmsVoucher f WHERE f.status = 0 and  f.type='CHPV'"),
    @NamedQuery(name = "FmsVoucher.findByStatusJV", query = " SELECT f FROM FmsVoucher f WHERE f.status = 0 AND f.type = 'JV'"),
    @NamedQuery(name = "FmsVoucher.findVoucheIdDup", query = "SELECT f FROM FmsVoucher f WHERE f.voucherId = :voucherId"),
    @NamedQuery(name = "FmsVoucher.findByVoucherJV", query = "SELECT f FROM FmsVoucher f WHERE f.voucherId LIKE :voucherId AND  f.type= :type"),
    @NamedQuery(name = "FmsVoucher.findByVoucherPCPVDailyCash", query = "SELECT f FROM FmsVoucher f WHERE f.voucherId LIKE :voucherId AND  f.type= 'PCPV'"),
    @NamedQuery(name = "FmsVoucher.findByVoucherCPODailyCash", query = "SELECT f FROM FmsVoucher f WHERE f.voucherId LIKE :voucherId AND  f.type= 'CPO'"),
    @NamedQuery(name = "FmsVoucher.findByReason", query = "SELECT f FROM FmsVoucher f WHERE f.reason = :reason"),
    @NamedQuery(name = "FmsVoucher.findByProcessedDate", query = "SELECT f FROM FmsVoucher f WHERE f.processedDate =:processedDate"),
    @NamedQuery(name = "FmsVoucher.findByPreparedBy", query = "SELECT f FROM FmsVoucher f WHERE f.preparedBy = :preparedBy"),
    @NamedQuery(name = "FmsVoucher.findByPreparedDate", query = "SELECT f FROM FmsVoucher f WHERE f.preparedDate = :preparedDate"),
    @NamedQuery(name = "FmsVoucher.findByPreparedRemark", query = "SELECT f FROM FmsVoucher f WHERE f.preparedRemark = :preparedRemark"),
    @NamedQuery(name = "FmsVoucher.findByvatWithholdStatus", query = "SELECT f FROM FmsVoucher f WHERE f.vatWithholdStatus = :vatWithholdStatus"),
    @NamedQuery(name = "FmsVoucher.findByProcessedBy", query = "SELECT f FROM FmsVoucher f WHERE f.ProcessedBy =:ProcessedBy"),
    @NamedQuery(name = "FmsVoucher.findByPurpose", query = "SELECT f FROM FmsVoucher f WHERE f.purpose = :purpose")})
public class FmsVoucher implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FMS_VOUCHER_SEQ")
    @SequenceGenerator(name = "FMS_VOUCHER_SEQ", sequenceName = "FMS_VOUCHER_SEQ", allocationSize = 1)
    @Column(name = "VOUCHER_ID", nullable = false, length = 50)

    private String voucherId;
    @Size(max = 10)
    @Column(length = 200, name = "VOUCHER_NO")
    private String voucherNo;
    @Size(max = 10)
    @Column(length = 50, name = "SYSTEM")
    private String SystemNo;

    @Size(max = 200)
    @Column(length = 200, name = "PURPOSE")
    private String purpose;
    @Size(max = 20)
    @Column(name = "TYPE_", length = 20)
    private String type;
    @Size(max = 200)
    @Column(length = 200, name = "REASON")
    private String reason;
    @Column(name = "STATUS")
    private Integer status;
    @Column(name = "PROCESSED_BY", length = 35)
    private String ProcessedBy;
    @Column(name = "PROCESSED_DATE")
    @Temporal(TemporalType.DATE)
    private Date processedDate;

    @Column(name = "PREPARED_BY")
    private String preparedBy;
    @Column(name = "PREPARED_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date preparedDate;
    @Size(max = 50)
    @Column(name = "PREPARED_REMARK")
    private String preparedRemark;
    @Size(max = 50)
    @Column(name = "VAT_WITHOLD_STATUS")
    private String vatWithholdStatus;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "VOUCHEID", fetch = FetchType.LAZY)
    private List<FmsAccountUse> fmsAccountUseList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "voucherId", fetch = FetchType.LAZY)
    private List<FmsDocumentFollowup> fmsDocumentFollowupList = new ArrayList<>();
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "fmsVOUCHERID")
    private FmsJournalVoucher fmsJournalVoucher;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "fmsVOUCHERID")
    private FmsPettyCashPaymentVoucher fmsPettyCashPaymentVoucher;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "fmsVOUCHERID")
    private FmsChequePaymentVoucher fmsChequePaymentVoucher;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "fmsVOUCHERID")
    public FmsCashReceiptVoucher fmsCashReceiptVoucher;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "fmsVOUCHERID")
    private FmsSuspensePaymentVocher fmsSuspensePaymentVocher;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "fmsVOUCHERID")
    private FmsCashPaymentOrder fmsCashPaymentOrder;
    @OneToOne(cascade = CascadeType.PERSIST, mappedBy = "fmsVOUCHERID")
    private FmsWithHoldingVoucher withHoldingVoucher;
    @OneToOne(cascade = CascadeType.PERSIST, mappedBy = "fmsVOUCHERID")
    private FmsVatRecieptVoucher fmsVatRecieptVoucher;
    @OneToMany(mappedBy = "fmsVoucherId")
    private List<WfFcmsProcessed> wfFcmsProcessedList;

    

    public FmsVoucher(){
        
    }
  
@Transient
    private String columnName;

    @Transient
    private String columnValue;
    
      public String getColumnValue() {
        return columnValue;
    }

    public void setColumnValue(String columnValue) {
        this.columnValue = columnValue;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }
    
    
    public FmsWithHoldingVoucher getWithHoldingVoucher() {
        return withHoldingVoucher;
    }

    public void setWithHoldingVoucher(FmsWithHoldingVoucher withHoldingVoucher) {
        this.withHoldingVoucher = withHoldingVoucher;
    }

    public String getVoucherNo() {
        return voucherNo;
    }

    public String getVatWithholdStatus() {
        return vatWithholdStatus;
    }

    public void setVatWithholdStatus(String vatWithholdStatus) {
        this.vatWithholdStatus = vatWithholdStatus;
    }

    public void setVoucherNo(String voucherNo) {
        this.voucherNo = voucherNo;
    }

    public String getSystemNo() {
        return SystemNo;
    }

    public void setSystemNo(String SystemNo) {
        this.SystemNo = SystemNo;
    }

    

    public String getPreparedRemark() {
        return preparedRemark;
    }

    public void setPreparedRemark(String preparedRemark) {
        this.preparedRemark = preparedRemark;
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

    public String getReason() {
        return reason;
    }

    /**
     *
     * @param reason
     */
    public void setReason(String reason) {
        this.reason = reason;
    }

    /**
     *
     * @param voucherId
     */
    public FmsVoucher(String voucherId) {
        this.voucherId = voucherId;
    }

    /**
     *
     * @return
     */
    public String getVoucherId() {
        return voucherId;
    }

    /**
     *
     * @param voucherId
     */
    public void setVoucherId(String voucherId) {
        this.voucherId = voucherId;
    }

    /**
     *
     * @return
     */
    public String getType() {
        return type;
    }

    /**
     *
     * @param type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     *
     * @return
     */
    public Integer getStatus() {
        return status;
    }

    /**
     *
     * @param status
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     *
     * @return
     */
    public String getPurpose() {
        return purpose;
    }

    public String getProcessedBy() {
        return ProcessedBy;
    }

    public void setProcessedBy(String ProcessedBy) {
        this.ProcessedBy = ProcessedBy;
    }

    public Date getProcessedDate() {
        return processedDate;
    }

    public void setProcessedDate(Date processedDate) {
        this.processedDate = processedDate;
    }

    /**
     *
     * @param purpose
     */
    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public FmsJournalVoucher getFmsJournalVoucher() {
        return fmsJournalVoucher;
    }

    /**
     *
     * @param fmsJournalVoucher
     */
    public void setFmsJournalVoucher(FmsJournalVoucher fmsJournalVoucher) {
        this.fmsJournalVoucher = fmsJournalVoucher;
    }

    public FmsCashPaymentOrder getFmsCashPaymentOrder() {
        if (fmsCashPaymentOrder == null) {
            fmsCashPaymentOrder = new FmsCashPaymentOrder();
        }
        return fmsCashPaymentOrder;
    }

    public void setFmsCashPaymentOrder(FmsCashPaymentOrder fmsCashPaymentOrder) {
        this.fmsCashPaymentOrder = fmsCashPaymentOrder;
    }

    public FmsVatRecieptVoucher getFmsVatRecieptVoucher() {
        if (fmsVatRecieptVoucher == null) {
            fmsVatRecieptVoucher = new FmsVatRecieptVoucher();
        }
        return fmsVatRecieptVoucher;
    }

    public void setFmsVatRecieptVoucher(FmsVatRecieptVoucher fmsVatRecieptVoucher) {
        this.fmsVatRecieptVoucher = fmsVatRecieptVoucher;
    }

    /**
     *
     * @return
     */
    @XmlTransient
    public List<FmsDocumentFollowup> getFmsDocumentFollowupList() {
        if (fmsDocumentFollowupList == null) {
            fmsDocumentFollowupList = new ArrayList<>();
        }
        return fmsDocumentFollowupList;
    }

    /**
     *
     * @param fmsDocumentFollowupList
     */
    public void setFmsDocumentFollowupList(List<FmsDocumentFollowup> fmsDocumentFollowupList) {
        this.fmsDocumentFollowupList = fmsDocumentFollowupList;
    }

    /**
     *
     * @return
     */
    @XmlTransient
    public List<FmsAccountUse> getFmsAccountUseList() {
        if (fmsAccountUseList == null) {
            fmsAccountUseList = new ArrayList<>();
        }
        return fmsAccountUseList;
    }

    /**
     *
     * @param fmsAccountUseList
     */
    public void setFmsAccountUseList(List<FmsAccountUse> fmsAccountUseList) {
        this.fmsAccountUseList = fmsAccountUseList;
    }

    public FmsPettyCashPaymentVoucher getFmsPettyCashPaymentVoucher() {
        if (fmsPettyCashPaymentVoucher == null) {
            fmsPettyCashPaymentVoucher = new FmsPettyCashPaymentVoucher();
        }
        return fmsPettyCashPaymentVoucher;
    }

    public void setFmsPettyCashPaymentVoucher(FmsPettyCashPaymentVoucher fmsPettyCashPaymentVoucher) {
        this.fmsPettyCashPaymentVoucher = fmsPettyCashPaymentVoucher;
    }

    /**
     *
     * @return
     */
    public FmsChequePaymentVoucher getFmsChequePaymentVoucher() {
        return fmsChequePaymentVoucher;
    }

    /**
     *
     * @param fmsChequePaymentVoucher
     */
    public void setFmsChequePaymentVoucher(FmsChequePaymentVoucher fmsChequePaymentVoucher) {
        this.fmsChequePaymentVoucher = fmsChequePaymentVoucher;
    }

    /**
     *
     * @return
     */
    public FmsCashReceiptVoucher getFmsCashReceiptVoucher() {
        return fmsCashReceiptVoucher;
    }

    public void setFmsCashReceiptVoucher(FmsCashReceiptVoucher fmsCashReceiptVoucher) {
        this.fmsCashReceiptVoucher = fmsCashReceiptVoucher;
    }

    public FmsSuspensePaymentVocher getFmsSuspensePaymentVocher() {
        return fmsSuspensePaymentVocher;
    }

    public void setFmsSuspensePaymentVocher(FmsSuspensePaymentVocher fmsSuspensePaymentVocher) {
        this.fmsSuspensePaymentVocher = fmsSuspensePaymentVocher;
    }

    @XmlTransient
    public List<WfFcmsProcessed> getWfFcmsProcessedList() {
        if (wfFcmsProcessedList == null) {
            wfFcmsProcessedList = new ArrayList<>();
        }

        return wfFcmsProcessedList;
    }

    public void setWfFcmsProcessedList(List<WfFcmsProcessed> wfFcmsProcessedList) {
        this.wfFcmsProcessedList = wfFcmsProcessedList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (voucherId != null ? voucherId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FmsVoucher)) {
            return false;
        }
        FmsVoucher other = (FmsVoucher) object;
        if ((this.voucherId == null && other.voucherId != null) || (this.voucherId != null && !this.voucherId.equals(other.voucherId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return voucherId;
    }

    /**
     *
     * @param fmsAccountUseDetail
     *
     */
    public void addToFmsSubsidiaryLSrDetail(FmsAccountUse fmsAccountUseDetail) {
        if (fmsAccountUseDetail.getVOUCHEID() != this) {
            this.getFmsAccountUseList().add(fmsAccountUseDetail);
            fmsAccountUseDetail.setVOUCHEID(this);
        }
    }

    /**
     *
     * @param FmsChequePaymentVoucherDetail
     */
    public void addToFmsCheckPaymentDetail(FmsChequePaymentVoucher FmsChequePaymentVoucherDetail) {
        System.out.println("====== hi");
        if (FmsChequePaymentVoucherDetail.getVoucherId() != this) {
            System.out.println("hello");
//                                    this.getFmsChequePaymentVoucherList().add(FmsChequePaymentVoucherDetail);
            this.fmsChequePaymentVoucher = FmsChequePaymentVoucherDetail;
            FmsChequePaymentVoucherDetail.setVoucherId(this);
            System.out.println("FmsChequePaymentVoucherDetail.setFmsVOUCHERID(this)=====" + FmsChequePaymentVoucherDetail.getFmsVOUCHERID());

        }
    }

    /**
     *
     * @param journalVoucherDetail
     */
    public void addToFmsJournalVoucherDetail(FmsJournalVoucher journalVoucherDetail) {
        if (journalVoucherDetail.getFmsVOUCHERID() != this) {
            this.fmsJournalVoucher = journalVoucherDetail;
            // this.getFmsJournalVoucherList().add(journalVoucherDetail);
            journalVoucherDetail.setFmsVOUCHERID(this);
            System.out.println("journalVoucherDetail.setFmsVOUCHERID(this)=====" + journalVoucherDetail.getFmsVOUCHERID());
        }
    }

    public void addWithholdingDetail(FmsWithHoldingVoucher FmsWithHoldingVoucherDetail) {
        if (FmsWithHoldingVoucherDetail.getFmsVOUCHERID() != this) {
//                                    this.getFmsChequePaymentVoucherList().add(FmsChequePaymentVoucherDetail);
            this.withHoldingVoucher = FmsWithHoldingVoucherDetail;
            FmsWithHoldingVoucherDetail.setFmsVOUCHERID(this);
        }
    }

    public void addVatDetail(FmsVatRecieptVoucher fmsVatRecieptVoucherDetail) {
        System.out.println("====== hiiiiii");
        if (fmsVatRecieptVoucherDetail.getFmsVOUCHERID() != this) {
//                                    this.getFmsChequePaymentVoucherList().add(FmsChequePaymentVoucherDetail);
            this.fmsVatRecieptVoucher = fmsVatRecieptVoucherDetail;
            fmsVatRecieptVoucherDetail.setFmsVOUCHERID(this);
        }
    }

    /**
     *
     * @param fmsCashReceiptVoucherDetail
     */
    public void addToFmscashReceiptDetail(FmsCashReceiptVoucher fmsCashReceiptVoucherDetail) {
        if (fmsCashReceiptVoucherDetail.getFmsvoucherVOUCHERID() != this) {
            this.fmsCashReceiptVoucher = fmsCashReceiptVoucherDetail;
            //this.getFmsCashReceiptVouchersList().add(fmsCashReceiptVoucherDetail);
            fmsCashReceiptVoucherDetail.setFmsvoucherVOUCHERID(this);
        }
    }

    /**
     *
     * @param fmsPettyCashPaymentDetail
     */
    public void addToFmsPettyCashPaymentDetail(FmsPettyCashPaymentVoucher fmsPettyCashPaymentDetail) {
        if (fmsPettyCashPaymentDetail.getFmsvoucherVOUCHERID() != this) {
//            this.getFmsPettyCashPaymentList().add(fmsPettyCashPaymentDetail);
            fmsPettyCashPaymentVoucher = fmsPettyCashPaymentDetail;
            fmsPettyCashPaymentDetail.setFmsvoucherVOUCHERID(this);
        }
    }

    /**
     *
     * @param fmsDocumentFollowupDetail
     */
    public void addToFmsDocumentFollowupDetail(FmsDocumentFollowup fmsDocumentFollowupDetail) {
        System.err.println("-----" + fmsDocumentFollowupDetail.getVoucherId());
        if (fmsDocumentFollowupDetail.getVoucherId() != this) {
            this.getFmsDocumentFollowupList().add(fmsDocumentFollowupDetail);
            fmsDocumentFollowupDetail.setVoucherId(this);
        }
    }

    public void addToFmsSuspensePaymentVoucherDetail(FmsSuspensePaymentVocher suspensePymentVoucherDetail) {
        if (suspensePymentVoucherDetail.getFmsVOUCHERID() != this) {
            fmsSuspensePaymentVocher = suspensePymentVoucherDetail;
//            this.getFmsSuspensePymtVoucherList().add(suspensePymentVoucherDetail);
            suspensePymentVoucherDetail.setFmsVOUCHERID(this);
        }
    }

    public void addToFmsCashPaymentOrderDetail(FmsCashPaymentOrder cashPaymentOrderDetail) {
        if (cashPaymentOrderDetail.getFmsVOUCHERID() != this) {
            this.fmsCashPaymentOrder = cashPaymentOrderDetail;
            cashPaymentOrderDetail.setFmsVOUCHERID(this);
        }
    }

    public void removedata(FmsAccountUse rowData) {
        getFmsAccountUseList().remove(rowData);
    }
    @Transient
    String Decision2;

    public String getDecision2() {
        return Decision2;
    }

    public void setDecision2(String Decision2) {
        this.Decision2 = Decision2;
    }

}
