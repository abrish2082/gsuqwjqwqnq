package et.gov.eep.fcms.entity.pettyCash;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
import et.gov.eep.commonApplications.entity.WfFcmsProcessed;

/**
 *
 * @author memube
 */
@Entity
@Table(name = "FMS_PETTY_CASH_REPLENISHMENT")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FmsPettyCashReplenishment.findAll", query = "SELECT f FROM FmsPettyCashReplenishment f"),
    @NamedQuery(name = "FmsPettyCashReplenishment.findById", query = "SELECT f FROM FmsPettyCashReplenishment f WHERE f.id = :id"),
    @NamedQuery(name = "FmsPettyCashReplenishment.findByAmount", query = "SELECT f FROM FmsPettyCashReplenishment f WHERE f.amount = :amount"),
    @NamedQuery(name = "FmsPettyCashReplenishment.findByWfStatus", query = "SELECT f FROM FmsPettyCashReplenishment f WHERE f.wfStatus = :wfStatus"),
    @NamedQuery(name = "FmsPettyCashReplenishment.findByPreparedBy", query = "SELECT f FROM FmsPettyCashReplenishment f WHERE f.wfStatus = :wfStatus"),
    @NamedQuery(name = "FmsPettyCashReplenishment.findByCashierId", query = "SELECT f FROM FmsPettyCashReplenishment f WHERE f.cashierId = :cashierId"),
    @NamedQuery(name = "FmsPettyCashReplenishment.findBypreparedDate", query = "SELECT f FROM FmsPettyCashReplenishment f WHERE f.preparedDate = :preparedDate"),
    @NamedQuery(name = "FmsPettyCashReplenishment.findPCRByWfStatus", query = "SELECt f FROM FmsPettyCashReplenishment f where f.wfStatus = :wfStatus"),
    @NamedQuery(name = "FmsPettyCashReplenishment.findPCRByCashierIdAndWfCheckRejectValue", query = "SELECt f FROM FmsPettyCashReplenishment f where f.cashierId = :cashierId AND f.wfStatus = :wfStatus"),
    @NamedQuery(name = "FmsPettyCashReplenishment.findByPreparedRemark", query = "SELECT f FROM FmsPettyCashReplenishment f WHERE f.preparedRemark = :preparedRemark")})
public class FmsPettyCashReplenishment implements Serializable {

    private static final long serialVersionUID = 1L;
    //<editor-fold defaultstate="collapsed" desc="variable declaration">
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FMS_PETTY_CASH_REPLENISH_SEQ")
    @SequenceGenerator(name = "FMS_PETTY_CASH_REPLENISH_SEQ", sequenceName = "FMS_PETTY_CASH_REPLENISH_SEQ", allocationSize = 1)
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private Long id;
    @Column(name = "AMOUNT")
    private BigDecimal amount;
    @Column(name = "WF_STATUS")
    private Integer wfStatus;
    @Size(max = 45)
    @Column(name = "PREPARED_BY")
    private String preparedBy;
    @Column(name = "PREPARED_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date preparedDate;
    @Size(max = 255)
    @Column(name = "PREPARED_REMARK")
    private String preparedRemark;
    @JoinColumn(name = "CASHIER_ID", referencedColumnName = "ID")
    @ManyToOne
    private FmsCasherAccount cashierId;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "fmsPettyCashReplenishment")
    private WfFcmsProcessed wfFcmsProcessed;
    @OneToMany(mappedBy = "fmsPettyCashReplenishId")
    private List<WfFcmsProcessed> wfFcmsProcessedList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pettyCashReplenishmentId")
    private List<FmsPettyCashReplenishDtl> fmsPettyCashReplenishDtlList;
    @Transient
    String changedDecision;
//</editor-fold>

    public FmsPettyCashReplenishment() {
    }

    //<editor-fold defaultstate="collapsed" desc="getter and setter">
    public FmsPettyCashReplenishment(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Integer getWfStatus() {
        return wfStatus;
    }

    public void setWfStatus(Integer wfStatus) {
        this.wfStatus = wfStatus;
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

    public String getPreparedRemark() {
        return preparedRemark;
    }

    public void setPreparedRemark(String preparedRemark) {
        this.preparedRemark = preparedRemark;
    }

    public FmsCasherAccount getCashierId() {
        return cashierId;
    }

    public void setCashierId(FmsCasherAccount cashierId) {
        this.cashierId = cashierId;
    }

    public WfFcmsProcessed getWfFcmsProcessed() {
        return wfFcmsProcessed;
    }

    public void setWfFcmsProcessed(WfFcmsProcessed wfFcmsProcessed) {
        this.wfFcmsProcessed = wfFcmsProcessed;
    }

    public String getChangedDecision() {
        return changedDecision;
    }

    public void setChangedDecision(String changedDecision) {
        this.changedDecision = changedDecision;
    }
//</editor-fold>

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

    @XmlTransient
    public List<FmsPettyCashReplenishDtl> getFmsPettyCashReplenishDtlList() {
        if (fmsPettyCashReplenishDtlList == null) {
            fmsPettyCashReplenishDtlList = new ArrayList<>();
        }
        return fmsPettyCashReplenishDtlList;
    }

    public void setFmsPettyCashReplenishDtlList(List<FmsPettyCashReplenishDtl> fmsPettyCashReplenishDtlList) {
        this.fmsPettyCashReplenishDtlList = fmsPettyCashReplenishDtlList;
    }

    public void addPettyCashReplenishmentDetail(FmsPettyCashReplenishDtl fmsPettyCashReplenishDtl) {
        try {
            if (fmsPettyCashReplenishDtl.getPettyCashReplenishmentId() != this) {
                this.getFmsPettyCashReplenishDtlList().add(fmsPettyCashReplenishDtl);
                fmsPettyCashReplenishDtl.setPettyCashReplenishmentId(this);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof FmsPettyCashReplenishment)) {
            return false;
        }
        FmsPettyCashReplenishment other = (FmsPettyCashReplenishment) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "et.gov.eep.fcms.entity.workFlow.FmsPettyCashReplenishment[ id=" + id + " ]";
    }

}
