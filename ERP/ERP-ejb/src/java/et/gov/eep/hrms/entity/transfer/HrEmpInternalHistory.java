/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.entity.transfer;

import et.gov.eep.hrms.entity.employee.HrEmployees;
import java.io.Serializable;
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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author INSA
 */
@Entity
@Table(name = "HR_EMP_INTERNAL_HISTORY")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HrEmpInternalHistory.findByEmpId", query = "SELECT DISTINCT(h) FROM HrEmpInternalHistory h join h.empId g where g.id = :id"),
    @NamedQuery(name = "HrEmpInternalHistory.findAllRequest", query = "SELECT h FROM HrEmpInternalHistory h WHERE h.status=0"),
    @NamedQuery(name = "HrEmpInternalHistory.findAllByEmpId", query = "SELECT h FROM HrEmpInternalHistory h WHERE h.empId.empId = :empId"),
    @NamedQuery(name = "HrEmpInternalHistory.findByName", query = "SELECT h FROM HrEmpInternalHistory h WHERE UPPER(h.empId.firstName) LIKE :firstName and h.status =0"),
    @NamedQuery(name = "HrEmpInternalHistory.findByTwo", query = "SELECT h FROM HrEmpInternalHistory h WHERE UPPER(h.empId.firstName) LIKE :firstName and UPPER(h.empId.empId) LIKE :empId and h.status =0"),

    @NamedQuery(name = "HrEmpInternalHistory.findAll", query = "SELECT h FROM HrEmpInternalHistory h"),
    @NamedQuery(name = "HrEmpInternalHistory.findAllAssignment", query = "SELECT h FROM HrEmpInternalHistory h WHERE h.processType = 'Assignment'"),
    @NamedQuery(name = "HrEmpInternalHistory.findAssignmentByNameLike", query = "SELECT h FROM HrEmpInternalHistory h WHERE UPPER(h.empId.firstName) LIKE :firstName and h.processType = 'Assignment'"),
    @NamedQuery(name = "HrEmpInternalHistory.findAssignmentByIdLike", query = "SELECT h FROM HrEmpInternalHistory h WHERE UPPER(h.empId.empId) LIKE :empId and h.processType = 'Assignment'"),
    @NamedQuery(name = "HrEmpInternalHistory.findByIdAndName", query = "SELECT h FROM HrEmpInternalHistory h WHERE UPPER(h.empId.firstName) LIKE :firstName and UPPER(h.empId.empId) LIKE :empId and h.processType ='Assignment'"),
    @NamedQuery(name = "HrEmpInternalHistory.findAssignmentByName", query = "SELECT h FROM HrEmpInternalHistory h WHERE h.empId.firstName = :firstName and h.processType = 'Assignment'"),
    @NamedQuery(name = "HrEmpInternalHistory.findAssignmentById", query = "SELECT h FROM HrEmpInternalHistory h WHERE h.empId.empId = :empId and h.processType = 'Assignment'"),
    @NamedQuery(name = "HrEmpInternalHistory.findById", query = "SELECT h FROM HrEmpInternalHistory h WHERE h.id = :id"),
    @NamedQuery(name = "HrEmpInternalHistory.findByProcessId", query = "SELECT h FROM HrEmpInternalHistory h WHERE h.processId = :processId"),
    @NamedQuery(name = "HrEmpInternalHistory.findByProcessType", query = "SELECT h FROM HrEmpInternalHistory h WHERE h.processType = :processType"),
    @NamedQuery(name = "HrEmpInternalHistory.findByOldDepName", query = "SELECT h FROM HrEmpInternalHistory h WHERE h.oldDepName = :oldDepName"),
    @NamedQuery(name = "HrEmpInternalHistory.findByOldJobName", query = "SELECT h FROM HrEmpInternalHistory h WHERE h.oldJobName = :oldJobName"),
    @NamedQuery(name = "HrEmpInternalHistory.findByEmployeeId", query = "SELECT h FROM HrEmpInternalHistory h WHERE  UPPER(h.empId.empId) LIKE :empId and h.status=0"),
    @NamedQuery(name = "HrEmpInternalHistory.findByReferenceNo", query = "SELECT h FROM HrEmpInternalHistory h WHERE h.referenceNo = :referenceNo"),
    @NamedQuery(name = "HrEmpInternalHistory.findByRemark", query = "SELECT h FROM HrEmpInternalHistory h WHERE h.remark = :remark"),
    @NamedQuery(name = "HrEmpInternalHistory.findByStatus", query = "SELECT h FROM HrEmpInternalHistory h WHERE h.status = :status")})
public class HrEmpInternalHistory implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HR_EMP_INTERNAL_HISTORY_SEQ")
    @SequenceGenerator(name = "HR_EMP_INTERNAL_HISTORY_SEQ", sequenceName = "HR_EMP_INTERNAL_HISTORY_SEQ", allocationSize = 1)
    private Integer id;

    @Size(max = 20)
    @Column(name = "PROCESS_ID")
    private String processId;

    @Size(max = 20)
    @Column(name = "PROCESS_TYPE")
    private String processType;

    @Size(max = 400)
    @Column(name = "OLD_DEP_NAME")
    private String oldDepName;

    @Size(max = 400)
    @Column(name = "OLD_JOB_NAME")
    private String oldJobName;

    @Column(name = "PROCESS_DATE")
    private String processDate;

    @Size(max = 100)
    @Column(name = "REFERENCE_NO")
    private String referenceNo;

    @Size(max = 400)
    @Column(name = "REMARK")
    private String remark;

    @Column(name = "OLD_DEP_ID")
    private Integer oldDepId;
    @Column(name = "OLD_JOB_ID")
    private Integer oldJobId;
    @Column(name = "OLD_GRADE")
    private String oldGrade;
    @Column(name = "OLD_SALARY_STEP")
    private String oldSalaryStep;
    @Column(name = "OLD_SALARY")
    private double oldSalary;
    @Column(name = "STATUS")
    private Integer status;

    @JoinColumn(name = "EMP_ID", referencedColumnName = "ID")
    @ManyToOne
    private HrEmployees empId;

    public HrEmpInternalHistory() {
    }

    public HrEmpInternalHistory(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProcessId() {
        return processId;
    }

    public void setProcessId(String processId) {
        this.processId = processId;
    }

    public String getProcessType() {
        return processType;
    }

    public void setProcessType(String processType) {
        this.processType = processType;
    }

    public String getOldDepName() {
        return oldDepName;
    }

    public void setOldDepName(String oldDepName) {
        this.oldDepName = oldDepName;
    }

    public String getOldJobName() {
        return oldJobName;
    }

    public void setOldJobName(String oldJobName) {
        this.oldJobName = oldJobName;
    }

    public String getProcessDate() {
        return processDate;
    }

    public void setProcessDate(String processDate) {
        this.processDate = processDate;
    }

    public String getReferenceNo() {
        return referenceNo;
    }

    public void setReferenceNo(String referenceNo) {
        this.referenceNo = referenceNo;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public HrEmployees getEmpId() {
        return empId;
    }

    public void setEmpId(HrEmployees empId) {
        this.empId = empId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HrEmpInternalHistory)) {
            return false;
        }
        HrEmpInternalHistory other = (HrEmpInternalHistory) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "et.gov.eep.hrms.entity.transfer.HrEmpInternalHistory[ id=" + id + " ]";
    }

    //<editor-fold defaultstate="collapsed" desc="static variable">
    public static int NEW_PLACEMENT = 1;
    public static int PROMOTION = 2;
    public static int TRANSFER = 3;
    public static int INCREMENT = 4;
    public static int ASSIGNMENT = 5;
    public static int ACTING = 6;
    public static int JOB_ROTATION = 7;
    public static int SALARY_SCALE_CHANGE = 8;
    public static int DISCIPLINARY_CASE = 9;
    public static int PROBATION = 10;
    public static int REVOKE_ACTING = 11;
    public static int OTHER = 12;
//</editor-fold>

    public Integer getOldDepId() {
        return oldDepId;
    }

    public void setOldDepId(Integer oldDepId) {
        this.oldDepId = oldDepId;
    }

    public Integer getOldJobId() {
        return oldJobId;
    }

    public void setOldJobId(Integer oldJobId) {
        this.oldJobId = oldJobId;
    }

    public String getOldGrade() {
        return oldGrade;
    }

    public void setOldGrade(String oldGrade) {
        this.oldGrade = oldGrade;
    }

    public String getOldSalaryStep() {
        return oldSalaryStep;
    }

    public void setOldSalaryStep(String oldSalaryStep) {
        this.oldSalaryStep = oldSalaryStep;
    }

    public double getOldSalary() {
        return oldSalary;
    }

    public void setOldSalary(double oldSalary) {
        this.oldSalary = oldSalary;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

}
