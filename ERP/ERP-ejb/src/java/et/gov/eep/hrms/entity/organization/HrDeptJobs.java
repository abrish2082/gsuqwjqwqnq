/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.entity.organization;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author user
 */
@Entity
@Table(name = "HR_DEPT_JOBS")
@XmlRootElement
@NamedQueries//<editor-fold defaultstate="collapsed" desc="">
        ({
            @NamedQuery(name = "HrDeptJobs.count", query = "SELECT count(h) FROM HrDeptJobs h group by h.deptId.depId,h.jobId.id"),
            @NamedQuery(name = "HrDeptJobs.findAll", query = "SELECT h FROM HrDeptJobs h"),
            @NamedQuery(name = "HrDeptJobs.findById", query = "SELECT h FROM HrDeptJobs h WHERE h.id = :id"),
            @NamedQuery(name = "HrDeptJobs.findByDeptId", query = "SELECT h FROM HrDeptJobs h WHERE h.deptId.depName = :deptID"),
            @NamedQuery(name = "HrDeptJobs.findByJobId", query = "SELECT h FROM HrDeptJobs h WHERE h.jobId.id = :jobId"),
            @NamedQuery(name = "HrDeptJobs.findByDepartmentId", query = "SELECT h FROM HrDeptJobs h WHERE h.deptId.depId = :deptId"),
            @NamedQuery(name = "HrDeptJobs.findByDeptIdandJobId", query = "SELECT h FROM HrDeptJobs h WHERE h.deptId.depId = :deptId and h.jobId.id =:jobId"),
            @NamedQuery(name = "HrDeptJobs.findByDepartmentIdLike", query = "SELECT h FROM HrDeptJobs h WHERE h.deptId.depId = :deptId"),
            @NamedQuery(name = "HrDeptJobs.findByNoEmpNeeded", query = "SELECT h FROM HrDeptJobs h WHERE h.noEmpNeeded = :noEmpNeeded"),
            @NamedQuery(name = "HrDeptJobs.findByDeptIdJobId", query = "SELECT h FROM HrDeptJobs h WHERE h.deptId.depId = :depId and h.jobId.id = :jobId")})
//</editor-fold>
public class HrDeptJobs implements Serializable {

   
    //<editor-fold defaultstate="collapsed" desc="Entity Attributes">
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HR_DEPT_JOBS_SEQ")
    @SequenceGenerator(name = "HR_DEPT_JOBS_SEQ", sequenceName = "HR_DEPT_JOBS_SEQ", allocationSize = 1)
    private BigDecimal id;
    @Size(max = 400)
    @Column(name = "REMARK")
    private String remark;
    @Size(max = 20)
    @Column(name = "PREPARED_BY")
    private String preparedBy;
    @Size(max = 20)
    @Column(name = "PREPARED_ON")
    private String preparedOn;
    @Column(name = "NO_EMP_NEEDED")
    private BigInteger noEmpNeeded;
    @JoinColumn(name = "DEPT_ID", referencedColumnName = "DEP_ID")
    @ManyToOne
    private HrDepartments deptId;
    @JoinColumn(name = "JOB_ID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private HrJobTypes jobId;
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="getter and setter">
    public HrDeptJobs() {
    }
    
    /**
     *
     * @param id
     */
    public HrDeptJobs(BigDecimal id) {
        this.id = id;
    }
    
    /**
     *
     * @return
     */
    public BigDecimal getId() {
        return id;
    }
    
    /**
     *
     * @param id
     */
    public void setId(BigDecimal id) {
        this.id = id;
    }
    
    /**
     *
     * @return
     */
    public BigInteger getNoEmpNeeded() {
        return noEmpNeeded;
    }
    
    /**
     *
     * @param noEmpNeeded
     */
    public void setNoEmpNeeded(BigInteger noEmpNeeded) {
        this.noEmpNeeded = noEmpNeeded;
    }
    
    /**
     *
     * @return
     */
    public HrDepartments getDeptId() {
        return deptId;
    }
    
    /**
     *
     * @param deptId
     */
    public void setDeptId(HrDepartments deptId) {
        this.deptId = deptId;
    }
    
    /**
     *
     * @return
     */
    public HrJobTypes getJobId() {
        return jobId;
    }
    
    /**
     *
     * @param jobId
     */
    public void setJobId(HrJobTypes jobId) {
        this.jobId = jobId;
    }
    
    public String getRemark() {
        return remark;
    }
    
    public String getPreparedBy() {
        return preparedBy;
    }
    
    public void setPreparedBy(String preparedBy) {
        this.preparedBy = preparedBy;
    }
    
    public String getPreparedOn() {
        return preparedOn;
    }
    
    public void setPreparedOn(String preparedOn) {
        this.preparedOn = preparedOn;
    }
    
    public void setRemark(String remark) {
        this.remark = remark;
    }
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Other Methods">
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }
    
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HrDeptJobs)) {
            return false;
        }
        HrDeptJobs other = (HrDeptJobs) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }
    
    @Override
    public String toString() {
        return jobId.getJobTitle();//"et.gov.eep.hrms.entity.HrDeptJobs[ id=" + id + " ]";
    }
//</editor-fold>
  
}
