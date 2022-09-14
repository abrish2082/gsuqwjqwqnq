package et.gov.eep.fcms.entity.perDiem;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
import javax.persistence.Transient;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import et.gov.eep.commonApplications.entity.WfFcmsProcessed;
import et.gov.eep.hrms.entity.employee.HrEmployees;
import et.gov.eep.hrms.entity.address.HrAddresses;

/**
 *
 * @author muller
 */
@Entity
@Table(name = "FMS_FIELD_ALLOWANCE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FmsFieldAllowance.findAll", query = "SELECT f FROM FmsFieldAllowance f"),
    @NamedQuery(name = "FmsFieldAllowance.findById", query = "SELECT f FROM FmsFieldAllowance f WHERE f.id = :id"),
    @NamedQuery(name = "FmsFieldAllowance.findByFromDate", query = "SELECT f FROM FmsFieldAllowance f WHERE f.fromDate = :fromDate"),
    @NamedQuery(name = "FmsFieldAllowance.findByFromTime", query = "SELECT f FROM FmsFieldAllowance f WHERE f.fromTime = :fromTime"),
    @NamedQuery(name = "FmsFieldAllowance.findByToDate", query = "SELECT f FROM FmsFieldAllowance f WHERE f.toDate = :toDate"),
    @NamedQuery(name = "FmsFieldAllowance.findByToTime", query = "SELECT f FROM FmsFieldAllowance f WHERE f.toTime = :toTime"),
    @NamedQuery(name = "FmsFieldAllowance.findByModeOfTransport", query = "SELECT f FROM FmsFieldAllowance f WHERE f.modeOfTransport = :modeOfTransport"),
    @NamedQuery(name = "FmsFieldAllowance.findByPreparedDate", query = "SELECT f FROM FmsFieldAllowance f WHERE f.preparedDate = :preparedDate"),
    @NamedQuery(name = "FmsFieldAllowance.findByPurpose", query = "SELECT f FROM FmsFieldAllowance f WHERE f.purpose = :purpose"),
    @NamedQuery(name = "FmsFieldAllowance.findByPreparedBy", query = "SELECT f FROM FmsFieldAllowance f WHERE f.preparedBy = :preparedBy"),
    @NamedQuery(name = "FmsFieldAllowance.findByRequestNo", query = "SELECT f FROM FmsFieldAllowance f WHERE f.requestNo = :requestNo"),
    @NamedQuery(name = "FmsFieldAllowance.findByNoOfDays", query = "SELECT f FROM FmsFieldAllowance f WHERE f.noOfDays = :noOfDays"),
    @NamedQuery(name = "FmsFieldAllowance.findByTravelExpense", query = "SELECT f FROM FmsFieldAllowance f WHERE f.travelExpense = :travelExpense"),
    @NamedQuery(name = "FmsFieldAllowance.findByHotelExpense", query = "SELECT f FROM FmsFieldAllowance f WHERE f.hotelExpense = :hotelExpense"),
    @NamedQuery(name = "FmsFieldAllowance.findByMealsExpense", query = "SELECT f FROM FmsFieldAllowance f WHERE f.mealsExpense = :mealsExpense"),
    @NamedQuery(name = "FmsFieldAllowance.findByMiscellaneousExpense", query = "SELECT f FROM FmsFieldAllowance f WHERE f.miscellaneousExpense = :miscellaneousExpense"),
    @NamedQuery(name = "FmsFieldAllowance.findByTotalExpense", query = "SELECT f FROM FmsFieldAllowance f WHERE f.totalExpense = :totalExpense"),
    @NamedQuery(name = "FmsFieldAllowance.findByDepartureAddressID", query = "SELECT f FROM FmsFieldAllowance f WHERE f.departureAddressId = :departureAddressId"),
    @NamedQuery(name = "FmsFieldAllowance.findByReturnedAddressID", query = "SELECT f FROM FmsFieldAllowance f WHERE f.returnedAddressId = :returnedAddressId"),
    @NamedQuery(name = "FmsFieldAllowance.findByStatus", query = "SELECT f FROM FmsFieldAllowance f WHERE f.status = :status"),
    @NamedQuery(name = "FmsFieldAllowance.findByWfStatusApproved", query = "SELECT f FROM FmsFieldAllowance f WHERE f.status = :status AND f.wfStatus=:wfStatus"),
    @NamedQuery(name = "FmsFieldAllowance.findByempIdAndStatus", query = "SELECt f FROM FmsFieldAllowance f where f.id = :id AND f.status='Requested'"),
    @NamedQuery(name = "FmsFieldAllowance.findFaByWfStatus", query = "SELECt f FROM FmsFieldAllowance f where f.wfStatus = :wfStatus"),
    @NamedQuery(name = "FmsFieldAllowance.findByempId", query = "SELECt f FROM FmsFieldAllowance f where f.empId = :empId")})

public class FmsFieldAllowance implements Serializable {

    private static final long serialVersionUID = 1L;
    //<editor-fold defaultstate="collapsed" desc="variable declaration">
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FMS_FIELD_ALLOWANCE_SEQ")
    @SequenceGenerator(name = "FMS_FIELD_ALLOWANCE_SEQ", sequenceName = "FMS_FIELD_ALLOWANCE_SEQ", allocationSize = 1)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "FROM_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fromDate;
    @Size(max = 20)
    @Column(name = "FROM_TIME")
    private String fromTime;
    @Column(name = "TO_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date toDate;
    @Size(max = 20)
    @Column(name = "TO_TIME")
    private String toTime;
    @Size(max = 20)
    @Column(name = "MODE_OF_TRANSPORT")
    private String modeOfTransport;
    @Column(name = "PREPARED_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date preparedDate;
    @Size(max = 255)
    @Column(name = "PURPOSE")
    private String purpose;
    @Size(max = 255)
    @Column(name = "PREPARED_REMARK")
    private String preparedRemark;
    @Size(max = 45)
    @Column(name = "ACCOUNT_CODE")
    private String accountCode;
    @Size(max = 45)
    @Column(name = "PREPARED_BY")
    private String preparedBy;
    @Column(name = "STATUS")
    private String status;
    @Column(name = "WF_STATUS")
    private Integer wfStatus;
    @Size(max = 50)
    @Column(name = "REQUEST_NO")
    private String requestNo;
    @Column(name = "NO_OF_DAYS")
    private Integer noOfDays;
    @Column(name = "TRAVEL_EXPENSE")
    private Double travelExpense;
    @Column(name = "HOTEL_EXPENSE")
    private Double hotelExpense;
    @Column(name = "MEALS_EXPENSE")
    private Double mealsExpense;
    @Column(name = "MISCELLANEOUS_EXPENSE")
    private Double miscellaneousExpense;
    @Column(name = "TOTAL_EXPENSE")
    private Double totalExpense;
    @JoinColumn(name = "RETURNED_ADDRESS_ID", referencedColumnName = "ADDRESS_ID")
    @ManyToOne
    private HrAddresses returnedAddressId;
    @JoinColumn(name = "DEPARTURE_ADDRESS_ID", referencedColumnName = "ADDRESS_ID")
    @ManyToOne
    private HrAddresses departureAddressId;
    @OneToMany(mappedBy = "perdiemRequestLocalId")
    private List<WfFcmsProcessed> wfFcmsProcessedList;
    @JoinColumn(name = "EMP_ID", referencedColumnName = "ID")
    @ManyToOne
    private HrEmployees empId;

    @OneToMany(mappedBy = "allowanceReqId")
    private List<FmsFieldAllowanceSettlement> fmsFieldAllowanceSettlementList;
    @Transient
    String display_conn;
//</editor-fold>

    public FmsFieldAllowance() {
    }
//<editor-fold defaultstate="collapsed" desc="getter and setter">

    public List<FmsFieldAllowanceSettlement> getFmsFieldAllowanceSettlementList() {
        if (fmsFieldAllowanceSettlementList == null) {
            fmsFieldAllowanceSettlementList = new ArrayList<>();
        }
        return fmsFieldAllowanceSettlementList;
    }

    public void setFmsFieldAllowanceSettlementList(List<FmsFieldAllowanceSettlement> fmsFieldAllowanceSettlementList) {
        this.fmsFieldAllowanceSettlementList = fmsFieldAllowanceSettlementList;
    }

    public FmsFieldAllowance(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public String getFromTime() {
        return fromTime;
    }

    public void setFromTime(String fromTime) {
        this.fromTime = fromTime;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public String getToTime() {
        return toTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getWfStatus() {
        return wfStatus;
    }

    public void setWfStatus(Integer wfStatus) {
        this.wfStatus = wfStatus;
    }

    public void setToTime(String toTime) {
        this.toTime = toTime;
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

    public String getAccountCode() {
        return accountCode;
    }

    public void setAccountCode(String accountCode) {
        this.accountCode = accountCode;
    }

    public String getPreparedBy() {
        return preparedBy;
    }

    public void setPreparedBy(String preparedBy) {
        this.preparedBy = preparedBy;
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

    public Double getHotelExpense() {
        return hotelExpense;
    }

    public void setHotelExpense(Double hotelExpense) {
        this.hotelExpense = hotelExpense;
    }

    public Double getMealsExpense() {
        return mealsExpense;
    }

    public void setMealsExpense(Double mealsExpense) {
        this.mealsExpense = mealsExpense;
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

    public HrEmployees getEmpId() {
        return empId;
    }

    public void setEmpId(HrEmployees empId) {
        this.empId = empId;
    }

    public HrAddresses getReturnedAddressId() {
        return returnedAddressId;
    }

    public void setReturnedAddressId(HrAddresses returnedAddressId) {
        this.returnedAddressId = returnedAddressId;
    }

    public HrAddresses getDepartureAddressId() {
        return departureAddressId;
    }

    public void setDepartureAddressId(HrAddresses departureAddressId) {
        this.departureAddressId = departureAddressId;
    }

    public String getDisplay_conn() {
        return display_conn;
    }

    public void setDisplay_conn(String display_conn) {
        this.display_conn = display_conn;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof FmsFieldAllowance)) {
            return false;
        }
        FmsFieldAllowance other = (FmsFieldAllowance) object;
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
