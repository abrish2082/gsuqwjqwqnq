/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.entity.Bond;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author mora
 */
@Entity
@Table(name = "FMS_BOND_LOCAL")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FmsBondLocal.findAll", query = "SELECT f FROM FmsBondLocal f"),
    @NamedQuery(name = "FmsBondLocal.findBySerialNo", query = "SELECT f FROM FmsBondLocal f WHERE f.serialNo = :serialNo"),
    @NamedQuery(name = "FmsBondLocal.findBySerialNoLike", query = "SELECT f FROM FmsBondLocal f WHERE f.serialNo LIKE :serialNo"),
    @NamedQuery(name = "FmsBondLocal.findBondLike", query = "SELECT f FROM FmsBondLocal f WHERE f.serialNo LIKE :serialNo and f.buyerFullName LIKE :buyerFullName"),
    @NamedQuery(name = "FmsBondLocal.findByBuyerFullName", query = "SELECT f FROM FmsBondLocal f WHERE f.buyerFullName = :buyerFullName"),
    @NamedQuery(name = "FmsBondLocal.findByBuyerFullNameLike", query = "SELECT f FROM FmsBondLocal f WHERE f.buyerFullName LIKE :buyerFullName"),
    @NamedQuery(name = "FmsBondLocal.findByApplicationDate", query = "SELECT f FROM FmsBondLocal f WHERE f.applicationDate = :applicationDate"),
    @NamedQuery(name = "FmsBondLocal.findByApplicationDateLike", query = "SELECT f FROM FmsBondLocal f WHERE f.applicationDate LIKE :applicationDate"),
    @NamedQuery(name = "FmsBondLocal.findByDueDate", query = "SELECT f FROM FmsBondLocal f WHERE f.dueDate = :dueDate"),
    @NamedQuery(name = "FmsBondLocal.findByInterestBearing", query = "SELECT f FROM FmsBondLocal f WHERE f.interestBearing = :interestBearing"),
    @NamedQuery(name = "FmsBondLocal.findByBondType", query = "SELECT f FROM FmsBondLocal f WHERE f.BondType = :BondType"),
    @NamedQuery(name = "FmsBondLocal.findByValueBirr", query = "SELECT f FROM FmsBondLocal f WHERE f.valueBirr = :valueBirr"),
    @NamedQuery(name = "FmsBondLocal.findByRePaid", query = "SELECT f FROM FmsBondLocal f WHERE f.rePaid = :rePaid"),
    @NamedQuery(name = "FmsBondLocal.findByfindBySerialNamePrincipal", query = "SELECT f FROM FmsBondLocal f WHERE f.serialNo = :serialNo and f.buyerFullName = :buyerFullName and f.valueBirr = :valueBirr"),
    @NamedQuery(name = "FmsBondLocal.findBySerialAndPrincipal", query = "SELECT f FROM FmsBondLocal f WHERE f.serialNo = :serialNo and f.valueBirr = :valueBirr"),
    @NamedQuery(name = "FmsBondLocal.findByPrincipalAndName", query = "SELECT f FROM FmsBondLocal f WHERE f.buyerFullName = :buyerFullName and f.valueBirr = :valueBirr"),
    @NamedQuery(name = "FmsBondLocal.findByGracePeriod", query = "SELECT f FROM FmsBondLocal f WHERE f.gracePeriod = :gracePeriod"),
    @NamedQuery(name = "FmsBondLocal.findByLastRepaymentSchedule", query = "SELECT f FROM FmsBondLocal f WHERE f.lastRepaymentSchedule = :lastRepaymentSchedule"),
    @NamedQuery(name = "FmsBondLocal.findByStatus", query = "SELECT f FROM FmsBondLocal f WHERE f.status = :status"),
    @NamedQuery(name = "FmsBondLocal.findByRegisteredDate", query = "SELECT f FROM FmsBondLocal f WHERE f.registeredDate = :registeredDate"),
    @NamedQuery(name = "FmsBondLocal.findByBondMaturity", query = "SELECT f FROM FmsBondLocal f WHERE f.BondMaturity = :BondMaturity"),
    @NamedQuery(name = "FmsBondLocal.findByExtend_no", query = "SELECT f FROM FmsBondLocal f WHERE f.extend_no = :extend_no"),
    @NamedQuery(name = "FmsBondLocal.findByLocalBondId", query = "SELECT f FROM FmsBondLocal f WHERE f.localBondId = :localBondId")})
public class FmsBondLocal implements Serializable {

    private static final long serialVersionUID = 1L;
    //<editor-fold defaultstate="collapsed" desc="variable declaration">
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "SERIAL_NO")
    private String serialNo;
    @Size(max = 20)
    @Column(name = "BUYER_FULL_NAME")
    private String buyerFullName;
    @Column(name = "APPLICATION_DATE")
    @Temporal(TemporalType.DATE)
    private Date applicationDate;
    @Column(name = "REGISTERED_DATE")
    @Temporal(TemporalType.DATE)
    private Date registeredDate;
    @Column(name = "DUE_DATE")
    @Temporal(TemporalType.DATE)
    private Date dueDate;
    @Column(name = "BOND_MATURITY_DATE")
    @Temporal(TemporalType.DATE)
    private Date BondMaturityDate;
    @Column(name = "DOCUMENT_REFERENCE_NO")
    private String documentReferenceNumber;
    @Column(name = "INTEREST_BEARING")
    private Double interestBearing;
    @Size(max = 20)
    @Column(name = "BOND_TYPE")
    private String BondType;
    @Column(name = "VALUE_BIRR")
    private Double valueBirr;
    @Column(name = "LAST_REPAYMENT_SCHEDULE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastRepaymentSchedule;
    @Size(max = 20)
    @Column(name = "STATUS")
    private String status;
    @Column(name = "RE_PAID")
    private Integer rePaid;
    @Column(name = "GRACE_PERIOD")
    private Integer gracePeriod;
    @Column(name = "BOND_MATURITY")
    private Integer BondMaturity;
    @Column(name = "REMARK")
    private String remark;
    @Size(max = 20)
    @Column(name = "EXTEND_NO")
    private String extend_no;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "LOCAL_BOND_ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FMS_BOND_LOCAL_SEQ")
    @SequenceGenerator(name = "FMS_BOND_LOCAL_SEQ", sequenceName = "FMS_BOND_LOCAL_SEQ", allocationSize = 1)
    private Integer localBondId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "localBondId")
    private Collection<FmsBondLocalExtend> fmsBondLocalExtendCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "localBondId", fetch = FetchType.LAZY)
    private List<FmsBondLocalSchedule> fmsBondLocalScheduleList = new ArrayList<>();
//</editor-fold>

    public FmsBondLocal() {
    }

//<editor-fold defaultstate="collapsed" desc="Getter and Setter">
    public FmsBondLocal(Integer localBondId) {
        this.localBondId = localBondId;
    }

    public FmsBondLocal(Integer localBondId, String serialNo) {
        this.localBondId = localBondId;
        this.serialNo = serialNo;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public String getBuyerFullName() {
        return buyerFullName;
    }

    public void setBuyerFullName(String buyerFullName) {
        this.buyerFullName = buyerFullName;
    }

    public Date getApplicationDate() {
        return applicationDate;
    }

    public void setApplicationDate(Date applicationDate) {
        this.applicationDate = applicationDate;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Date getBondMaturityDate() {
        return BondMaturityDate;
    }

    public void setBondMaturityDate(Date BondMaturityDate) {
        this.BondMaturityDate = BondMaturityDate;
    }

    public String getDocumentReferenceNumber() {
        return documentReferenceNumber;
    }

    public void setDocumentReferenceNumber(String documentReferenceNumber) {
        this.documentReferenceNumber = documentReferenceNumber;
    }

    public Double getInterestBearing() {
        return interestBearing;
    }

    public void setInterestBearing(Double interestBearing) {
        this.interestBearing = interestBearing;
    }

    public String getBondType() {
        return BondType;
    }

    public void setBondType(String BondType) {
        this.BondType = BondType;
    }

    public Double getValueBirr() {
        return valueBirr;
    }

    public void setValueBirr(Double valueBirr) {
        this.valueBirr = valueBirr;
    }

    public Integer getRePaid() {
        return rePaid;
    }

    public void setRePaid(Integer rePaid) {
        this.rePaid = rePaid;
    }

    public Integer getGracePeriod() {
        return gracePeriod;
    }

    public void setGracePeriod(Integer gracePeriod) {
        this.gracePeriod = gracePeriod;
    }

    public Integer getBondMaturity() {
        return BondMaturity;
    }

    public void setBondMaturity(Integer BondMaturity) {
        this.BondMaturity = BondMaturity;
    }

    public Date getLastRepaymentSchedule() {
        return lastRepaymentSchedule;
    }

    public void setLastRepaymentSchedule(Date lastRepaymentSchedule) {
        this.lastRepaymentSchedule = lastRepaymentSchedule;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getLocalBondId() {
        return localBondId;
    }

    public void setLocalBondId(Integer localBondId) {
        this.localBondId = localBondId;
    }

    public String getExtend_no() {
        return extend_no;
    }

    public void setExtend_no(String extend_no) {
        this.extend_no = extend_no;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getRegisteredDate() {
        return registeredDate;
    }

    public void setRegisteredDate(Date registeredDate) {
        this.registeredDate = registeredDate;
    }
//</editor-fold>

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (serialNo != null ? serialNo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof FmsBondLocal)) {
            return false;
        }
        FmsBondLocal other = (FmsBondLocal) object;
        if ((this.serialNo == null && other.serialNo != null) || (this.serialNo != null && !this.serialNo.equals(other.serialNo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return serialNo + "";
    }

    @XmlTransient
    public List<FmsBondLocalSchedule> getFmsBondLocalScheduleList() {
        if (fmsBondLocalScheduleList == null) {
            fmsBondLocalScheduleList = new ArrayList<>();
        }
        return fmsBondLocalScheduleList;
    }

    public void setFmsBondLocalScheduleList(List<FmsBondLocalSchedule> fmsBondLocalScheduleList) {
        this.fmsBondLocalScheduleList = fmsBondLocalScheduleList;
    }

    @XmlTransient
    public Collection<FmsBondLocalExtend> getFmsBondLocalExtendCollection() {
        return fmsBondLocalExtendCollection;
    }

    public void setFmsBondLocalExtendCollection(Collection<FmsBondLocalExtend> fmsBondLocalExtendCollection) {
        this.fmsBondLocalExtendCollection = fmsBondLocalExtendCollection;
    }

    public void addToFmsBFmsBondLocalandForeignScheduleDetail(FmsBondLocalSchedule fmsBondLocalScheduleDetail) {

        this.getFmsBondLocalScheduleList().add(fmsBondLocalScheduleDetail);
        fmsBondLocalScheduleDetail.setLocalBondId(this);

    }

}
