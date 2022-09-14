package et.gov.eep.fcms.entity.perDiem;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import et.gov.eep.commonApplications.entity.WfFcmsProcessed;
import et.gov.eep.hrms.entity.employee.HrEmployees;

/**
 *
 * @author muller
 */
@Entity
@Table(name = "FMS_FIELD_ALLOWANCE_FOREIGN")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FmsFieldAllowanceForeign.findAll", query = "SELECT f FROM FmsFieldAllowanceForeign f"),
    @NamedQuery(name = "FmsFieldAllowanceForeign.findById", query = "SELECT f FROM FmsFieldAllowanceForeign f WHERE f.id = :id"),
    @NamedQuery(name = "FmsFieldAllowanceForeign.findByFromPlace", query = "SELECT f FROM FmsFieldAllowanceForeign f WHERE f.fromPlace = :fromPlace"),
    @NamedQuery(name = "FmsFieldAllowanceForeign.findByFromDate", query = "SELECT f FROM FmsFieldAllowanceForeign f WHERE f.fromDate = :fromDate"),
    @NamedQuery(name = "FmsFieldAllowanceForeign.findByToPlace", query = "SELECT f FROM FmsFieldAllowanceForeign f WHERE f.toPlace = :toPlace"),
    @NamedQuery(name = "FmsFieldAllowanceForeign.findByToDate", query = "SELECT f FROM FmsFieldAllowanceForeign f WHERE f.toDate = :toDate"),
    @NamedQuery(name = "FmsFieldAllowanceForeign.findByModeOfTransport", query = "SELECT f FROM FmsFieldAllowanceForeign f WHERE f.modeOfTransport = :modeOfTransport"),
    @NamedQuery(name = "FmsFieldAllowanceForeign.findByPreparedDate", query = "SELECT f FROM FmsFieldAllowanceForeign f WHERE f.preparedDate = :preparedDate"),
    @NamedQuery(name = "FmsFieldAllowanceForeign.findByPurpose", query = "SELECT f FROM FmsFieldAllowanceForeign f WHERE f.purpose = :purpose"),
    @NamedQuery(name = "FmsFieldAllowanceForeign.findByRequestNo", query = "SELECT f FROM FmsFieldAllowanceForeign f WHERE f.requestNo = :requestNo"),
    @NamedQuery(name = "FmsFieldAllowanceForeign.findByNoOfDays", query = "SELECT f FROM FmsFieldAllowanceForeign f WHERE f.noOfDays = :noOfDays"),
    @NamedQuery(name = "FmsFieldAllowanceForeign.findByTravelExpense", query = "SELECT f FROM FmsFieldAllowanceForeign f WHERE f.travelExpense = :travelExpense"),
    @NamedQuery(name = "FmsFieldAllowanceForeign.findByBreakLodgeExpense", query = "SELECT f FROM FmsFieldAllowanceForeign f WHERE f.breakLodgeExpense = :breakLodgeExpense"),
    @NamedQuery(name = "FmsFieldAllowanceForeign.findByLunchDinnerExpense", query = "SELECT f FROM FmsFieldAllowanceForeign f WHERE f.lunchDinnerExpense = :lunchDinnerExpense"),
    @NamedQuery(name = "FmsFieldAllowanceForeign.findByMiscellaneousExpense", query = "SELECT f FROM FmsFieldAllowanceForeign f WHERE f.miscellaneousExpense = :miscellaneousExpense"),
    @NamedQuery(name = "FmsFieldAllowanceForeign.findByTotalExpense", query = "SELECT f FROM FmsFieldAllowanceForeign f WHERE f.totalExpense = :totalExpense"),
    @NamedQuery(name = "FmsFieldAllowanceForeign.findByTravelWay", query = "SELECT f FROM FmsFieldAllowanceForeign f WHERE f.travelWay = :travelWay"),
    @NamedQuery(name = "FmsFieldAllowanceForeign.findByGoodwillingPayment", query = "SELECT f FROM FmsFieldAllowanceForeign f WHERE f.goodwillingPayment = :goodwillingPayment"),
    @NamedQuery(name = "FmsFieldAllowanceForeign.findByEmpId", query = "SELECT f FROM FmsFieldAllowanceForeign f WhERE f.empId = :empId"),
    @NamedQuery(name = "FmsFieldAllowanceForeign.findFaByWfStatus", query = "SELECt f FROM FmsFieldAllowanceForeign f where f.wfStatus = :wfStatus"),
    @NamedQuery(name = "FmsFieldAllowanceForeign.findByReservePayment", query = "SELECT f FROM FmsFieldAllowanceForeign f WHERE f.reservePayment = :reservePayment")})
public class FmsFieldAllowanceForeign implements Serializable {

    private static final long serialVersionUID = 1L;
    //<editor-fold defaultstate="collapsed" desc="variable declaration">
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FMS_FAF_SEQ")
    @SequenceGenerator(name = "FMS_FAF_SEQ", sequenceName = "FMS_FAF_SEQ", allocationSize = 1)
    @NotNull
    @Column(name = "ID")
    private BigDecimal id;
    @Size(max = 20)
    @Column(name = "FROM_PLACE")
    private String fromPlace;
    @Column(name = "FROM_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fromDate;
    @Column(name = "TO_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date toDate;
    @Size(max = 20)
    @Column(name = "MODE_OF_TRANSPORT")
    private String modeOfTransport;
    @Column(name = "PREPARED_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date preparedDate;
    @Size(max = 45)
    @Column(name = "PREPARED_BY")
    private String preparedBy;
    @Size(max = 255)
    @Column(name = "PURPOSE")
    private String purpose;
    @Size(max = 255)
    @Column(name = "PREPARED_REMARK")
    private String preparedRemark;
    @Size(max = 50)
    @Column(name = "REQUEST_NO")
    private String requestNo;
    @Column(name = "NO_OF_DAYS")
    private Integer noOfDays;
    @Column(name = "TRAVEL_EXPENSE")
    private Double travelExpense;
    @Column(name = "BREAK_LODGE_EXPENSE")
    private Double breakLodgeExpense;
    @Column(name = "LUNCH_DINNER_EXPENSE")
    private Double lunchDinnerExpense;
    @Column(name = "MISCELLANEOUS_EXPENSE")
    private Double miscellaneousExpense;
    @Column(name = "TOTAL_EXPENSE")
    private Double totalExpense;
    @Size(max = 20)
    @Column(name = "TRAVEL_WAY")
    private String travelWay;
    @Column(name = "GOODWILLING_PAYMENT")
    private Double goodwillingPayment;
    @Column(name = "RESERVE_PAYMENT")
    private Double reservePayment;
    @Column(name = "WF_STATUS")
    private Integer wfStatus;
    @JoinColumn(name = "EMP_ID", referencedColumnName = "ID")
    @ManyToOne
    private HrEmployees empId;
    @JoinColumn(name = "TO_PLACE", referencedColumnName = "COUNTRY_ID")
    @ManyToOne
    private FmsLuCountry toPlace;
    @OneToMany(mappedBy = "perdiemRequestForeignId")
    private List<WfFcmsProcessed> wfFcmsProcessedList;
//</editor-fold>

    public FmsFieldAllowanceForeign() {
    }
//<editor-fold defaultstate="collapsed" desc="getter and setter">

    public FmsFieldAllowanceForeign(BigDecimal id) {
        this.id = id;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public String getFromPlace() {
        return fromPlace;
    }

    public void setFromPlace(String fromPlace) {
        this.fromPlace = fromPlace;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public FmsLuCountry getToPlace() {
        return toPlace;
    }

    public void setToPlace(FmsLuCountry toPlace) {
        this.toPlace = toPlace;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public String getModeOfTransport() {
        return modeOfTransport;
    }

    public void setModeOfTransport(String modeOfTransport) {
        this.modeOfTransport = modeOfTransport;
    }

    public Date getPreparedDate() {
        return preparedDate;
    }

    public void setPreparedDate(Date preparedDate) {
        this.preparedDate = preparedDate;
    }

    public String getPreparedBy() {
        return preparedBy;
    }

    public void setPreparedBy(String preparedBy) {
        this.preparedBy = preparedBy;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getPreparedRemark() {
        return preparedRemark;
    }

    public void setPreparedRemark(String preparedRemark) {
        this.preparedRemark = preparedRemark;
    }

    public String getRequestNo() {
        return requestNo;
    }

    public void setRequestNo(String requestNo) {
        this.requestNo = requestNo;
    }

    public Integer getNoOfDays() {
        return noOfDays;
    }

    public void setNoOfDays(Integer noOfDays) {
        this.noOfDays = noOfDays;
    }

    public Double getTravelExpense() {
        return travelExpense;
    }

    public void setTravelExpense(Double travelExpense) {
        this.travelExpense = travelExpense;
    }

    public Double getBreakLodgeExpense() {
        return breakLodgeExpense;
    }

    public void setBreakLodgeExpense(Double breakLodgeExpense) {
        this.breakLodgeExpense = breakLodgeExpense;
    }

    public Double getLunchDinnerExpense() {
        return lunchDinnerExpense;
    }

    public void setLunchDinnerExpense(Double lunchDinnerExpense) {
        this.lunchDinnerExpense = lunchDinnerExpense;
    }

    public Double getMiscellaneousExpense() {
        return miscellaneousExpense;
    }

    public void setMiscellaneousExpense(Double miscellaneousExpense) {
        this.miscellaneousExpense = miscellaneousExpense;
    }

    public Double getTotalExpense() {
        return totalExpense;
    }

    public void setTotalExpense(Double totalExpense) {
        this.totalExpense = totalExpense;
    }

    public String getTravelWay() {
        return travelWay;
    }

    public void setTravelWay(String travelWay) {
        this.travelWay = travelWay;
    }

    public Double getGoodwillingPayment() {
        return goodwillingPayment;
    }

    public void setGoodwillingPayment(Double goodwillingPayment) {
        this.goodwillingPayment = goodwillingPayment;
    }

    public Double getReservePayment() {
        return reservePayment;
    }

    public void setReservePayment(Double reservePayment) {
        this.reservePayment = reservePayment;
    }

    public Integer getWfStatus() {
        return wfStatus;
    }

    public void setWfStatus(Integer wfStatus) {
        this.wfStatus = wfStatus;
    }

    public HrEmployees getEmpId() {
        return empId;
    }

    public void setEmpId(HrEmployees empId) {
        this.empId = empId;
    }
//</editor-fold>

    @XmlTransient
    public List<WfFcmsProcessed> getWfFcmsProcessedList() {
        return wfFcmsProcessedList;
    }

    public void setWfFcmsProcessedList(List<WfFcmsProcessed> wfFcmsProcessedList) {
        this.wfFcmsProcessedList = wfFcmsProcessedList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof FmsFieldAllowanceForeign)) {
            return false;
        }
        FmsFieldAllowanceForeign other = (FmsFieldAllowanceForeign) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return id.toString();
    }
}
