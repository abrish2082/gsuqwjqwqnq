/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.entity.transfer;

import et.gov.eep.hrms.entity.employee.HrEmployees;
import et.gov.eep.hrms.entity.lookup.HrLuSalarySteps;
import et.gov.eep.hrms.entity.organization.HrDepartments;
import et.gov.eep.hrms.entity.organization.HrJobTypes;
import et.gov.eep.hrms.entity.organization.HrSalaryScaleRanges;
import et.gov.eep.hrms.entity.organization.HrSalaryScales;
import java.io.Serializable;
import java.math.BigDecimal;
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
 * @author Ob
 */
@Entity
@Table(name = "HR_INTERNAL_MOVMENT")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HrInternalMovment.findAll", query = "SELECT h FROM HrInternalMovment h"),
    @NamedQuery(name = "HrInternalMovment.findAllRequest", query = "SELECT h FROM HrInternalMovment h WHERE h.status=0"),
    @NamedQuery(name = "HrInternalMovment.findByEmpId", query = "SELECT DISTINCT(h) FROM HrInternalMovment h join h.empId g where g.id = :id"),
    @NamedQuery(name = "HrInternalMovment.findByName", query = "SELECT h FROM HrInternalMovment h WHERE UPPER(h.empId.firstName) LIKE :firstName and h.status =0"),
    @NamedQuery(name = "HrInternalMovment.findByTwo", query = "SELECT h FROM HrInternalMovment h WHERE UPPER(h.empId.firstName) LIKE :firstName and UPPER(h.empId.empId) LIKE :empId and h.status =0"),
    @NamedQuery(name = "HrInternalMovment.findByEmployeeId", query = "SELECT h FROM HrInternalMovment h WHERE  UPPER(h.empId.empId) LIKE :empId and h.status=0"),
    @NamedQuery(name = "HrInternalMovment.findById", query = "SELECT h FROM HrInternalMovment h WHERE h.id = :id"),
    @NamedQuery(name = "HrInternalMovment.findByProcessType", query = "SELECT h FROM HrInternalMovment h WHERE h.processType = :processType"),
    @NamedQuery(name = "HrInternalMovment.findByProcessDate", query = "SELECT h FROM HrInternalMovment h WHERE h.processDate = :processDate"),
    @NamedQuery(name = "HrInternalMovment.findByReferenceNo", query = "SELECT h FROM HrInternalMovment h WHERE h.referenceNo = :referenceNo"),
    @NamedQuery(name = "HrInternalMovment.findByStatus", query = "SELECT h FROM HrInternalMovment h WHERE h.status = :status"),
    @NamedQuery(name = "HrInternalMovment.findByRemark", query = "SELECT h FROM HrInternalMovment h WHERE h.remark = :remark")})
public class HrInternalMovment implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HR_INTERNAL_MOVMENT_SEQ")
    @SequenceGenerator(name = "HR_INTERNAL_MOVMENT_SEQ", sequenceName = "HR_INTERNAL_MOVMENT_SEQ", allocationSize = 1)
    private BigDecimal id;
    @Size(max = 20)
    @Column(name = "PROCESS_TYPE")
    private String processType;
    @Size(max = 20)
    @Column(name = "PROCESS_DATE")
    private String processDate;
    @Size(max = 20)
    @Column(name = "REFERENCE_NO")
    private String referenceNo;
    @Column(name = "STATUS")
    private Integer status;
    @Size(max = 20)
    @Column(name = "REMARK")
    private String remark;

    @JoinColumn(name = "DEP_ID", referencedColumnName = "DEP_ID")
    @ManyToOne
    private HrDepartments depId;

    @JoinColumn(name = "EMP_ID", referencedColumnName = "ID")
    @ManyToOne
    private HrEmployees empId;

    @JoinColumn(name = "JOB_ID", referencedColumnName = "ID")
    @ManyToOne
    private HrJobTypes jobId;

    @JoinColumn(name = "SALARY_STEP_ID", referencedColumnName = "ID")
    @ManyToOne
    private HrLuSalarySteps salaryStepId;

    @JoinColumn(name = "SALARY_ID", referencedColumnName = "ID")
    @ManyToOne
    private HrSalaryScales salaryId;

    @JoinColumn(name = "GRADE_ID", referencedColumnName = "ID")
    @ManyToOne
    private HrSalaryScaleRanges gradeId;

    public HrInternalMovment() {
    }

    public HrInternalMovment(BigDecimal id) {
        this.id = id;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public String getProcessType() {
        return processType;
    }

    public void setProcessType(String processType) {
        this.processType = processType;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public HrDepartments getDepId() {
        return depId;
    }

    public void setDepId(HrDepartments depId) {
        this.depId = depId;
    }

    public HrEmployees getEmpId() {
        return empId;
    }

    public void setEmpId(HrEmployees empId) {
        this.empId = empId;
    }

    public HrJobTypes getJobId() {
        return jobId;
    }

    public void setJobId(HrJobTypes jobId) {
        this.jobId = jobId;
    }

    public HrLuSalarySteps getSalaryStepId() {
        return salaryStepId;
    }

    public void setSalaryStepId(HrLuSalarySteps salaryStepId) {
        this.salaryStepId = salaryStepId;
    }

    public HrSalaryScales getSalaryId() {
        return salaryId;
    }

    public void setSalaryId(HrSalaryScales salaryId) {
        this.salaryId = salaryId;
    }

    public HrSalaryScaleRanges getGradeId() {
        return gradeId;
    }

    public void setGradeId(HrSalaryScaleRanges gradeId) {
        this.gradeId = gradeId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
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

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HrInternalMovment)) {
            return false;
        }
        HrInternalMovment other = (HrInternalMovment) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.HrInternalMovment[ id=" + id + " ]";
    }

}
