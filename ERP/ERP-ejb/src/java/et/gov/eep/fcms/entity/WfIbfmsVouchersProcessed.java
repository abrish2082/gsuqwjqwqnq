package et.gov.eep.fcms.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
 * @author insa
 */
@Entity
@Table(name = "WF_IBFMS_VOUCHERS_PROCESSED")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "WfIbfmsVouchersProcessed.findAll", query = "SELECT w FROM WfIbfmsVouchersProcessed w"),
    @NamedQuery(name = "WfIbfmsVouchersProcessed.findByProcessedId", query = "SELECT w FROM WfIbfmsVouchersProcessed w WHERE w.processedId = :processedId"),
    @NamedQuery(name = "WfIbfmsVouchersProcessed.findByRequestId", query = "SELECT w FROM WfIbfmsVouchersProcessed w WHERE w.requestId = :requestId"),
    @NamedQuery(name = "WfIbfmsVouchersProcessed.findByProcessedBy", query = "SELECT w FROM WfIbfmsVouchersProcessed w WHERE w.processedBy = :processedBy"),
    @NamedQuery(name = "WfIbfmsVouchersProcessed.findByDecision", query = "SELECT w FROM WfIbfmsVouchersProcessed w WHERE w.decision = :decision"),
    @NamedQuery(name = "WfIbfmsVouchersProcessed.findByCommentGiven", query = "SELECT w FROM WfIbfmsVouchersProcessed w WHERE w.commentGiven = :commentGiven"),
    @NamedQuery(name = "WfIbfmsVouchersProcessed.findByRecordedBy", query = "SELECT w FROM WfIbfmsVouchersProcessed w WHERE w.recordedBy = :recordedBy"),
    @NamedQuery(name = "WfIbfmsVouchersProcessed.findByTimeStamp", query = "SELECT w FROM WfIbfmsVouchersProcessed w WHERE w.timeStamp = :timeStamp")})
public class WfIbfmsVouchersProcessed implements Serializable {
        private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "WF_IBFMS_PROCESSED_SEQ")
    @SequenceGenerator( name = "WF_IBFMS_PROCESSED_SEQ", sequenceName = "WF_IBFMS_PROCESSED_SEQ", allocationSize = 1)
    @Basic(optional = false)
    @Column(name = "PROCESSED_ID", nullable = false)
    private Integer processedId;
    @Column(name = "REQUEST_ID")
    private Integer requestId;
    @Column(name = "PROCESSED_BY", length = 20)
    private String processedBy;
    @Column(length = 20)
    private String decision;
    @Column(name = "COMMENT_GIVEN", length = 50)
    private String commentGiven;
    @Column(name = "RECORDED_BY", length = 20)
    private String recordedBy;
    @Column(name = "TIME_STAMP")
    @Temporal(TemporalType.TIMESTAMP)
    private Date timeStamp;

    /**
     *
     */
    public WfIbfmsVouchersProcessed() {
    }

    /**
     *
     * @param processedId
     */
    public WfIbfmsVouchersProcessed(Integer processedId) {
        this.processedId = processedId;
    }

    /**
     *
     * @return
     */
    public Integer getProcessedId() {
        return processedId;
    }

    /**
     *
     * @param processedId
     */
    public void setProcessedId(Integer processedId) {
        this.processedId = processedId;
    }

    /**
     *
     * @return
     */
    public Integer getRequestId() {
        return requestId;
    }

    /**
     *
     * @param requestId
     */
    public void setRequestId(Integer requestId) {
        this.requestId = requestId;
    }

    /**
     *
     * @return
     */
    public String getProcessedBy() {
        return processedBy;
    }

    /**
     *
     * @param processedBy
     */
    public void setProcessedBy(String processedBy) {
        this.processedBy = processedBy;
    }

    /**
     *
     * @return
     */
    public String getDecision() {
        return decision;
    }

    /**
     *
     * @param decision
     */
    public void setDecision(String decision) {
        this.decision = decision;
    }

    /**
     *
     * @return
     */
    public String getCommentGiven() {
        return commentGiven;
    }

    /**
     *
     * @param commentGiven
     */
    public void setCommentGiven(String commentGiven) {
        this.commentGiven = commentGiven;
    }

    /**
     *
     * @return
     */
    public String getRecordedBy() {
        return recordedBy;
    }

    /**
     *
     * @param recordedBy
     */
    public void setRecordedBy(String recordedBy) {
        this.recordedBy = recordedBy;
    }

    /**
     *
     * @return
     */
    public Date getTimeStamp() {
        return timeStamp;
    }

    /**
     *
     * @param timeStamp
     */
    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (processedId != null ? processedId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof WfIbfmsVouchersProcessed)) {
            return false;
        }
        WfIbfmsVouchersProcessed other = (WfIbfmsVouchersProcessed) object;
        if ((this.processedId == null && other.processedId != null) || (this.processedId != null && !this.processedId.equals(other.processedId))) {
            return false;
        }
        return true;
    }

   

    @Override
    public String toString() {
        return "et.gov.insa.epse.lims.entity.WfLimsProcessed[ processedId=" + processedId + " ]";
    }
    
}
