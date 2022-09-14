/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.prms.entity;

import et.gov.eep.hrms.entity.employee.HrEmployees;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "PRMS_BID_SUBMISSION_DETAIL")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PrmsBidSubmissionDetail.findAll", query = "SELECT p FROM PrmsBidSubmissionDetail p"),
    @NamedQuery(name = "PrmsBidSubmissionDetail.findByBidSubDtId", query = "SELECT p FROM PrmsBidSubmissionDetail p WHERE p.bidSubDtId = :bidSubDtId"),
    @NamedQuery(name = "PrmsBidSubmissionDetail.findBySubmittedBy", query = "SELECT p FROM PrmsBidSubmissionDetail p WHERE p.submittedBy = :submittedBy"),
    @NamedQuery(name = "PrmsBidSubmissionDetail.findByDateSub", query = "SELECT p FROM PrmsBidSubmissionDetail p WHERE p.dateSub = :dateSub"),
    @NamedQuery(name = "PrmsBidSubmissionDetail.findByTimeSub", query = "SELECT p FROM PrmsBidSubmissionDetail p WHERE p.timeSub = :timeSub"),
    @NamedQuery(name = "PrmsBidSubmissionDetail.findByRemark", query = "SELECT p FROM PrmsBidSubmissionDetail p WHERE p.remark = :remark")})
public class PrmsBidSubmissionDetail implements Serializable {

    @Column(name = "NUMBER_OF_COPY")
    private BigInteger numberOfCopy;
    @Column(name = "NUMBER_OF_ORIGINAL")
    private BigInteger numberOfOriginal;
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BID_SUBMISSION_DT_SEQ")
    @SequenceGenerator(name = "BID_SUBMISSION_DT_SEQ", sequenceName = "BID_SUBMISSION_DT_SEQ", allocationSize = 1)
    @Column(name = "BID_SUB_DT_ID")
    private BigDecimal bidSubDtId;
    @Size(max = 100)
    @Column(name = "SUBMITTED_BY")
    private String submittedBy;
    @Size(max = 100)
    @Column(name = "STATUS")
    private String status;
    @Column(name = "NO_PROPOSAL")
    private Integer noProposal;
    @Column(name = "DATE_SUB")
    @Temporal(TemporalType.DATE)
    private Date dateSub;
    @Column(name = "TIME_SUB")
    @Temporal(TemporalType.DATE)
    private Date timeSub;
    @Size(max = 500)
    @Column(name = "REMARK")
    private String remark;
    @JoinColumn(name = "SUPPLIER_ID", referencedColumnName = "ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private PrmsSupplyProfile supplierId;

    @JoinColumn(name = "BID_SUB_FID", referencedColumnName = "BID_SUB_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private PrmsBidSubmission bidSubFid;

    @JoinColumn(name = "EMPLOYEE_ID", referencedColumnName = "ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private HrEmployees employeeId;

    /**
     *
     */
    public PrmsBidSubmissionDetail() {
    }

    /**
     *
     * @param bidSubDtId
     */
    public PrmsBidSubmissionDetail(BigDecimal bidSubDtId) {
        this.bidSubDtId = bidSubDtId;
    }

    /**
     *
     * @return
     */
    public BigDecimal getBidSubDtId() {
        return bidSubDtId;
    }

    /**
     *
     * @param bidSubDtId
     */
    public void setBidSubDtId(BigDecimal bidSubDtId) {
        this.bidSubDtId = bidSubDtId;
    }

  
    public String getSubmittedBy() {
        return submittedBy;
    }

    public void setSubmittedBy(String submittedBy) {
        this.submittedBy = submittedBy;
    }

    public Date getDateSub() {
        return dateSub;
    }

    public void setDateSub(Date dateSub) {
        this.dateSub = dateSub;
    }

    public Date getTimeSub() {
        return timeSub;
    }

    public void setTimeSub(Date timeSub) {
        this.timeSub = timeSub;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public PrmsBidSubmission getBidSubFid() {
        return bidSubFid;
    }

    public void setBidSubFid(PrmsBidSubmission bidSubFid) {
        this.bidSubFid = bidSubFid;
    }

    public Integer getNoProposal() {
        return noProposal;
    }

    public void setNoProposal(Integer noProposal) {
        this.noProposal = noProposal;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (bidSubDtId != null ? bidSubDtId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PrmsBidSubmissionDetail)) {
            return false;
        }
        PrmsBidSubmissionDetail other = (PrmsBidSubmissionDetail) object;
        if ((this.bidSubDtId == null && other.bidSubDtId != null) || (this.bidSubDtId != null && !this.bidSubDtId.equals(other.bidSubDtId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "et.gov.eep.prms.entity.PrmsBidSubmissionDetail[ bidSubDtId=" + bidSubDtId + " ]";
    }

    public PrmsSupplyProfile getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(PrmsSupplyProfile supplierId) {
        this.supplierId = supplierId;
    }

    public HrEmployees getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(HrEmployees employeeId) {
        this.employeeId = employeeId;
    }

    public BigInteger getNumberOfCopy() {
        return numberOfCopy;
    }

    public void setNumberOfCopy(BigInteger numberOfCopy) {
        this.numberOfCopy = numberOfCopy;
    }

    public BigInteger getNumberOfOriginal() {
        return numberOfOriginal;
    }

    public void setNumberOfOriginal(BigInteger numberOfOriginal) {
        this.numberOfOriginal = numberOfOriginal;
    }

}
