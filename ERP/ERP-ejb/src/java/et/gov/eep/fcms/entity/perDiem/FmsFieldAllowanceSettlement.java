package et.gov.eep.fcms.entity.perDiem;

import java.io.Serializable;
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
@Table(name = "FMS_FIELD_ALLOWANCE_SETTLEMENT")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FmsFieldAllowanceSettlement.findAll", query = "SELECT f FROM FmsFieldAllowanceSettlement f"),
    @NamedQuery(name = "FmsFieldAllowanceSettlement.findById", query = "SELECT f FROM FmsFieldAllowanceSettlement f WHERE f.id = :id"),
    @NamedQuery(name = "FmsFieldAllowanceSettlement.findByEmpId", query = "SELECT f FROM FmsFieldAllowanceSettlement f WHERE f.empId = :empId"),
    @NamedQuery(name = "FmsFieldAllowanceSettlement.findByAllParameters", query = "SELECT f FROM FmsFieldAllowanceSettlement f WHERE f.empId LIKE :empId"),
    @NamedQuery(name = "FmsFieldAllowanceSettlement.findByDepartureDate", query = "SELECT f FROM FmsFieldAllowanceSettlement f WHERE f.departureDate = :departureDate"),
    @NamedQuery(name = "FmsFieldAllowanceSettlement.findByReturnedDate", query = "SELECT f FROM FmsFieldAllowanceSettlement f WHERE f.returnedDate = :returnedDate"),
    @NamedQuery(name = "FmsFieldAllowanceSettlement.findByDepartureTime", query = "SELECT f FROM FmsFieldAllowanceSettlement f WHERE f.departureTime = :departureTime"),
    @NamedQuery(name = "FmsFieldAllowanceSettlement.findByReturnedTime", query = "SELECT f FROM FmsFieldAllowanceSettlement f WHERE f.returnedTime = :returnedTime"),
    @NamedQuery(name = "FmsFieldAllowanceSettlement.findByNoOfDays", query = "SELECT f FROM FmsFieldAllowanceSettlement f WHERE f.noOfDays = :noOfDays"),
    @NamedQuery(name = "FmsFieldAllowanceSettlement.findByModeOfTransport", query = "SELECT f FROM FmsFieldAllowanceSettlement f WHERE f.modeOfTransport = :modeOfTransport"),
    @NamedQuery(name = "FmsFieldAllowanceSettlement.findByTravelExpense", query = "SELECT f FROM FmsFieldAllowanceSettlement f WHERE f.travelExpense = :travelExpense"),
    @NamedQuery(name = "FmsFieldAllowanceSettlement.findByFoodExpense", query = "SELECT f FROM FmsFieldAllowanceSettlement f WHERE f.foodExpense = :foodExpense"),
    @NamedQuery(name = "FmsFieldAllowanceSettlement.findByLodgingExpense", query = "SELECT f FROM FmsFieldAllowanceSettlement f WHERE f.lodgingExpense = :lodgingExpense"),
    @NamedQuery(name = "FmsFieldAllowanceSettlement.findByMiscellaneousExpense", query = "SELECT f FROM FmsFieldAllowanceSettlement f WHERE f.miscellaneousExpense = :miscellaneousExpense"),
    @NamedQuery(name = "FmsFieldAllowanceSettlement.findByTotalExpense", query = "SELECT f FROM FmsFieldAllowanceSettlement f WHERE f.totalExpense = :totalExpense"),
    @NamedQuery(name = "FmsFieldAllowanceSettlement.findByReturnableAmount", query = "SELECT f FROM FmsFieldAllowanceSettlement f WHERE f.returnableAmount = :returnableAmount"),
    @NamedQuery(name = "FmsFieldAllowanceSettlement.findByPreparedBy", query = "SELECT f FROM FmsFieldAllowanceSettlement f WHERE f.preparedBy = :preparedBy"),
    @NamedQuery(name = "FmsFieldAllowanceSettlement.findFaByWfStatus", query = "SELECt f FROM FmsFieldAllowanceSettlement f where f.wfStatus = :wfStatus"),
    @NamedQuery(name = "FmsFieldAllowanceSettlement.findByPreparedRemark", query = "SELECT f FROM FmsFieldAllowanceSettlement f WHERE f.preparedRemark = :preparedRemark")})
public class FmsFieldAllowanceSettlement implements Serializable {

    private static final long serialVersionUID = 1L;
    //<editor-fold defaultstate="collapsed" desc="variable declaration">
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FMS_FIELD_ALLOWANCE_SETTLE_SEQ")
    @SequenceGenerator(name = "FMS_FIELD_ALLOWANCE_SETTLE_SEQ", sequenceName = "FMS_FIELD_ALLOWANCE_SETTLE_SEQ", allocationSize = 1)
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private Integer id;
    @Column(name = "DEPARTURE_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date departureDate;
    @Column(name = "RETURNED_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date returnedDate;
    @Column(name = "PREPARED_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date preparedDate;
    @Size(max = 20)
    @Column(name = "DEPARTURE_TIME")
    private String departureTime;
    @Size(max = 20)
    @Column(name = "RETURNED_TIME")
    private String returnedTime;
    @Column(name = "NO_OF_DAYS")
    private Integer noOfDays;
    @Size(max = 20)
    @Column(name = "MODE_OF_TRANSPORT")
    private String modeOfTransport;
    @Column(name = "TRAVEL_EXPENSE")
    private Double travelExpense;
    @Column(name = "FOOD_EXPENSE")
    private Double foodExpense;
    @Column(name = "LODGING_EXPENSE")
    private Double lodgingExpense;
    @Column(name = "MISCELLANEOUS_EXPENSE")
    private Double miscellaneousExpense;
    @Column(name = "TOTAL_EXPENSE")
    private Double totalExpense;
    @Column(name = "RETURNABLE_AMOUNT")
    private Double returnableAmount;
    @Size(max = 20)
    @Column(name = "PREPARED_BY")
    private String preparedBy;
    @Size(max = 200)
    @Column(name = "PREPARED_REMARK")
    private String preparedRemark;
    @Column(name = "WF_STATUS")
    private Integer wfStatus;
    @JoinColumn(name = "EMP_ID", referencedColumnName = "ID")
    @ManyToOne
    private HrEmployees empId;
    @JoinColumn(name = "ALLOWANCE_REQ_ID", referencedColumnName = "ID")
    @ManyToOne
    private FmsFieldAllowance allowanceReqId;
    @OneToMany(mappedBy = "perdiemLocalSettlementId")
    private List<WfFcmsProcessed> wfFcmsProcessedList;
//</editor-fold>

    public FmsFieldAllowanceSettlement() {
    }

    //<editor-fold defaultstate="collapsed" desc="getter and setter">
    public FmsFieldAllowanceSettlement(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public FmsFieldAllowance getAllowanceReqId() {
        return allowanceReqId;
    }

    public void setAllowanceReqId(FmsFieldAllowance allowanceReqId) {
        this.allowanceReqId = allowanceReqId;
    }

    public HrEmployees getEmpId() {
        return empId;
    }

    public void setEmpId(HrEmployees empId) {
        this.empId = empId;
    }

    public Date getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(Date departureDate) {
        this.departureDate = departureDate;
    }

    public Date getReturnedDate() {
        return returnedDate;
    }

    public void setReturnedDate(Date returnedDate) {
        this.returnedDate = returnedDate;
    }

    public Date getPreparedDate() {
        return preparedDate;
    }

    public void setPreparedDate(Date preparedDate) {
        this.preparedDate = preparedDate;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public String getReturnedTime() {
        return returnedTime;
    }

    public void setReturnedTime(String returnedTime) {
        this.returnedTime = returnedTime;
    }

    public Integer getNoOfDays() {
        return noOfDays;
    }

    public void setNoOfDays(Integer noOfDays) {
        this.noOfDays = noOfDays;
    }

    public String getModeOfTransport() {
        return modeOfTransport;
    }

    public void setModeOfTransport(String modeOfTransport) {
        this.modeOfTransport = modeOfTransport;
    }

    public Double getTravelExpense() {
        return travelExpense;
    }

    public void setTravelExpense(Double travelExpense) {
        this.travelExpense = travelExpense;
    }

    public Double getFoodExpense() {
        return foodExpense;
    }

    public void setFoodExpense(Double foodExpense) {
        this.foodExpense = foodExpense;
    }

    public Double getLodgingExpense() {
        return lodgingExpense;
    }

    public void setLodgingExpense(Double lodgingExpense) {
        this.lodgingExpense = lodgingExpense;
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

    public Double getReturnableAmount() {
        return returnableAmount;
    }

    public void setReturnableAmount(Double returnableAmount) {
        this.returnableAmount = returnableAmount;
    }

    public String getPreparedBy() {
        return preparedBy;
    }

    public void setPreparedBy(String preparedBy) {
        this.preparedBy = preparedBy;
    }

    public String getPreparedRemark() {
        return preparedRemark;
    }

    public void setPreparedRemark(String preparedRemark) {
        this.preparedRemark = preparedRemark;
    }

    public Integer getWfStatus() {
        return wfStatus;
    }

    public void setWfStatus(Integer wfStatus) {
        this.wfStatus = wfStatus;
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
        if (!(object instanceof FmsFieldAllowanceSettlement)) {
            return false;
        }
        FmsFieldAllowanceSettlement other = (FmsFieldAllowanceSettlement) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.FmsFieldAllowanceSettlement[ id=" + id + " ]";
    }
}
