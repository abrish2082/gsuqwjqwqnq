/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.entity.Vocher;

import et.gov.eep.fcms.entity.Vocher.FmsVoucher;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author beharu
 */
@Entity
@Table(name = "fms_journal_voucher")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FmsJournalVoucher.findAll", query = "SELECT f FROM FmsJournalVoucher f"),
    @NamedQuery(name = "FmsJournalVoucher.findBySourceJeId", query = "SELECT f FROM FmsJournalVoucher f WHERE f.sourceJeId = :sourceJeId"),
    @NamedQuery(name = "FmsJournalVoucher.findByAmountInFigure", query = "SELECT f FROM FmsJournalVoucher f WHERE f.amountInFigure = :amountInFigure"),
    @NamedQuery(name = "FmsJournalVoucher.findByAmountInWord", query = "SELECT f FROM FmsJournalVoucher f WHERE f.amountInWord = :amountInWord"),
    @NamedQuery(name = "FmsJournalVoucher.findByPreparedBy", query = "SELECT f FROM FmsJournalVoucher f WHERE f.preparedBy = :preparedBy"),
    @NamedQuery(name = "FmsJournalVoucher.findByCheckedBy", query = "SELECT f FROM FmsJournalVoucher f WHERE f.checkedBy = :checkedBy"),
    @NamedQuery(name = "FmsJournalVoucher.findByApprovedBy", query = "SELECT f FROM FmsJournalVoucher f WHERE f.approvedBy = :approvedBy"),
    @NamedQuery(name = "FmsJournalVoucher.findByPreparedDate", query = "SELECT f FROM FmsJournalVoucher f WHERE f.preparedDate = :preparedDate"),
    @NamedQuery(name = "FmsJournalVoucher.findByCheckedDate", query = "SELECT f FROM FmsJournalVoucher f WHERE f.checkedDate = :checkedDate"),
    @NamedQuery(name = "FmsJournalVoucher.findByApprovedDate", query = "SELECT f FROM FmsJournalVoucher f WHERE f.approvedDate = :approvedDate"),
    @NamedQuery(name = "FmsJournalVoucher.findByJvId", query = "SELECT f FROM FmsJournalVoucher f WHERE f.jvId = :jvId")})
public class FmsJournalVoucher implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FMS_JOURNAL_VOUCHER_JV_ID_SEQ")
    @SequenceGenerator(name = "FMS_JOURNAL_VOUCHER_JV_ID_SEQ", sequenceName = "FMS_JOURNAL_VOUCHER_JV_ID_SEQ", allocationSize = 1)
    @Basic(optional = false)
    @Column(name = "JV_ID", nullable = false)
    private Integer jvId;
    @Size(max = 50)
    @Column(name = "SOURCE_JE_ID", length = 50)
    private String sourceJeId;
    @Column(name = "AMOUNT_IN_FIGURE")
    private Long amountInFigure;
    @Size(max = 500)
    @Column(name = "AMOUNT_IN_WORD", length = 500)
    private String amountInWord;
    @Size(max = 50)
    @Column(name = "PREPARED_BY", length = 50)
    private String preparedBy;
    @Size(max = 50)
    @Column(name = "CHECKED_BY", length = 50)
    private String checkedBy;
    @Size(max = 50)
    @Column(name = "APPROVED_BY", length = 50)
    private String approvedBy;
    @Column(name = "PREPARED_DATE")
    @Temporal(TemporalType.DATE)
    private Date preparedDate;
    @Column(name = "CHECKED_DATE")
    @Temporal(TemporalType.DATE)
    private Date checkedDate;
    @Column(name = "APPROVED_DATE")
    @Temporal(TemporalType.DATE)
    private Date approvedDate;
    @JoinColumn(name = "VOUCHER_ID", referencedColumnName = "VOUCHER_ID")
    @OneToOne(optional = false)
    private FmsVoucher fmsVOUCHERID;
    
    @Column(name = "REFERENCE_NUMBER", length = 30)
    private String referenceNumber;
    @Column(name = "PREPARE_REMARK", length = 30)
    private String prepareRemark;
    @Size(max = 500)
    @Column(name = "APPROVED_REMARK", length = 500)
    private String approvedRemark;
    @Size(max = 500)
    @Column(name = "CHECK_REMARK", length = 500)
    private String checkRemark;

    /**
     *
     * @return
     */
    public String getPrepareRemark() {
        return prepareRemark;
    }

    /**
     *
     * @return
     */
    public String getApprovedRemark() {
        return approvedRemark;
    }

    /**
     *
     * @param approvedRemark
     */
    public void setApprovedRemark(String approvedRemark) {
        this.approvedRemark = approvedRemark;
    }

    /**
     *
     * @return
     */
    public String getCheckRemark() {
        return checkRemark;
    }

    /**
     *
     * @param checkRemark
     */
    public void setCheckRemark(String checkRemark) {
        this.checkRemark = checkRemark;
    }

    /**
     *
     * @param prepareRemark
     */
    public void setPrepareRemark(String prepareRemark) {
        this.prepareRemark = prepareRemark;
    }

    /**
     *
     * @return
     */
    public String getReferenceNumber() {
        return referenceNumber;
    }

    /**
     *
     * @param referenceNumber
     */
    public void setReferenceNumber(String referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

    /**
     *
     * @return
     */
    public FmsVoucher getFmsVOUCHERID() {
        if (fmsVOUCHERID == null) {
            fmsVOUCHERID = new FmsVoucher();
        }
        return fmsVOUCHERID;
    }

    /**
     *
     * @param fmsVOUCHERID
     */
    public void setFmsVOUCHERID(FmsVoucher fmsVOUCHERID) {
        this.fmsVOUCHERID = fmsVOUCHERID;
    }

    /**
     *
     */
    public FmsJournalVoucher() {
    }

    /**
     *
     * @param jvId
     */
    public FmsJournalVoucher(Integer jvId) {
        this.jvId = jvId;
    }

    /**
     *
     * @return
     */
    public String getSourceJeId() {
        return sourceJeId;
    }

    /**
     *
     * @param sourceJeId
     */
    public void setSourceJeId(String sourceJeId) {
        this.sourceJeId = sourceJeId;
    }

    /**
     *
     * @return
     */
    public Long getAmountInFigure() {
        return amountInFigure;
    }

    /**
     *
     * @param amountInFigure
     */
    public void setAmountInFigure(Long amountInFigure) {
        this.amountInFigure = amountInFigure;
    }

    /**
     *
     * @return
     */
    public String getAmountInWord() {
        return amountInWord;
    }

    /**
     *
     * @param amountInWord
     */
    public void setAmountInWord(String amountInWord) {
        this.amountInWord = amountInWord;
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
    public String getCheckedBy() {
        return checkedBy;
    }

    /**
     *
     * @param checkedBy
     */
    public void setCheckedBy(String checkedBy) {
        this.checkedBy = checkedBy;
    }

    /**
     *
     * @return
     */
    public String getApprovedBy() {
        return approvedBy;
    }

    /**
     *
     * @param approvedBy
     */
    public void setApprovedBy(String approvedBy) {
        this.approvedBy = approvedBy;
    }

    /**
     *
     * @return
     */
    public Date getPreparedDate() {
        return preparedDate;
    }

    /**
     *
     * @param preparedDate
     */
    public void setPreparedDate(Date preparedDate) {
        this.preparedDate = preparedDate;
    }

    /**
     *
     * @return
     */
    public Date getCheckedDate() {
        return checkedDate;
    }

    /**
     *
     * @param checkedDate
     */
    public void setCheckedDate(Date checkedDate) {
        this.checkedDate = checkedDate;
    }

    /**
     *
     * @return
     */
    public Date getApprovedDate() {
        return approvedDate;
    }

    /**
     *
     * @param approvedDate
     */
    public void setApprovedDate(Date approvedDate) {
        this.approvedDate = approvedDate;
    }

    /**
     *
     * @return
     */
    public Integer getJvId() {
        return jvId;
    }

    /**
     *
     * @param jvId
     */
    public void setJvId(Integer jvId) {
        this.jvId = jvId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (jvId != null ? jvId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FmsJournalVoucher)) {
            return false;
        }
        FmsJournalVoucher other = (FmsJournalVoucher) object;
        if ((this.jvId == null && other.jvId != null) || (this.jvId != null && !this.jvId.equals(other.jvId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "et.gov.insa.erp.ibfms.integration.FmsJournalVoucher[ jvId=" + jvId + " ]";
    }

}
