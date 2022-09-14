/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.entity.succession;

import et.gov.eep.hrms.entity.organization.HrDepartments;
import et.gov.eep.hrms.entity.organization.HrJobTypes;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
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
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author meles
 */
@Entity
@Table(name = "HR_SM_KMP")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HrSmKmp.findAll", query = "SELECT h FROM HrSmKmp h"),
    @NamedQuery(name = "HrSmKmp.findById", query = "SELECT h FROM HrSmKmp h WHERE h.id = :id"),
    @NamedQuery(name = "HrSmKmp.findByJobId", query = "SELECT h FROM HrSmKmp h WHERE h.jobId.id= :jobId"),
    @NamedQuery(name = "HrSmKmp.findByDeptId", query = "SELECT h FROM HrSmKmp h WHERE h.deptId = :deptId "),
    @NamedQuery(name = "HrSmKmp.findByDeptIdAndKmpId", query = "SELECT h FROM HrSmKmp h WHERE h.deptId = :depId AND h.id = :kmpid "),
    @NamedQuery(name = "HrSmKmp.findByDeptIdJobId", query = "SELECT h FROM HrSmKmp h WHERE h.jobId.id= :jobId and h.deptId.depId = :deptId"),
    @NamedQuery(name = "HrSmKmp.findByDeptName", query = "SELECT h FROM HrSmKmp h WHERE UPPER(h.deptId.depName)LIKE :detName"),
    @NamedQuery(name = "HrSmKmp.findBykmpId", query = "SELECT h FROM HrSmKmp h WHERE UPPER(h.jobId.jobTitle) LIKE :jobTitle")
   })
    
   
public class HrSmKmp implements Serializable {
    @OneToMany(mappedBy = "kmpId")
    private Collection<HrSmSuccessorEvaluation> hrSmSuccessorEvaluationCollection;
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HR_SM_KMP_SEQ")
    @SequenceGenerator(name = "HR_SM_KMP_SEQ", sequenceName = "HR_SM_KMP_SEQ", allocationSize = 1)
    private Integer id;
    
    @JoinColumn(name = "DEPT_ID", referencedColumnName = "DEP_ID")
    @ManyToOne
    private HrDepartments deptId;
    @JoinColumn(name = "JOB_ID", referencedColumnName = "ID")
    @ManyToOne
    private HrJobTypes jobId;
   
  

    public HrSmKmp() {
    }

    public HrSmKmp(Integer id) {
        this.id = id;
    }

    public HrDepartments getDeptId() {
        return deptId;
    }

    public void setDeptId(HrDepartments deptId) {
        this.deptId = deptId;
    }

    public HrJobTypes getJobId() {
        return jobId;
    }

    public void setJobId(HrJobTypes jobId) {
        this.jobId = jobId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
        if (!(object instanceof HrSmKmp)) {
            return false;
        }
        HrSmKmp other = (HrSmKmp) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return id.toString();
    }

    @XmlTransient
    public Collection<HrSmSuccessorEvaluation> getHrSmSuccessorEvaluationCollection() {
        return hrSmSuccessorEvaluationCollection;
    }

    public void setHrSmSuccessorEvaluationCollection(Collection<HrSmSuccessorEvaluation> hrSmSuccessorEvaluationCollection) {
        this.hrSmSuccessorEvaluationCollection = hrSmSuccessorEvaluationCollection;
    }

    }
