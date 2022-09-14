/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.entity.employee;

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
 * @author user
 */
@Entity
@Table(name = "HR_EMP_CONTRACTS")
@XmlRootElement
@NamedQueries//<editor-fold defaultstate="collapsed" desc="">
        ({
            @NamedQuery(name = "HrEmpContracts.findAll", query = "SELECT h FROM HrEmpContracts h"),
            @NamedQuery(name = "HrEmpContracts.findById", query = "SELECT h FROM HrEmpContracts h WHERE h.id = :id"),
            @NamedQuery(name = "HrEmpContracts.findByFromDate", query = "SELECT h FROM HrEmpContracts h WHERE h.fromDate = :fromDate"),
            @NamedQuery(name = "HrEmpContracts.findByToDate", query = "SELECT h FROM HrEmpContracts h WHERE h.toDate = :toDate"),
            @NamedQuery(name = "HrEmpContracts.findByempIdforarray", query = "SELECT h FROM HrEmpContracts h WHERE  h.empId= :empId"),
            @NamedQuery(name = "HrEmpContracts.searchByEmpSt", query = "SELECT h FROM HrEmpContracts h WHERE  h.empId= :empId AND h.status = :status"),
            @NamedQuery(name = "HrEmpContracts.stateUpdate", query = "UPDATE HrEmpContracts H SET H.status = 'Inactive' WHERE  h.empId= :empId"),
            @NamedQuery(name = "HrEmpContracts.findByReason", query = "SELECT h FROM HrEmpContracts h WHERE h.reason = :reason")})
//</editor-fold>

public class HrEmpContracts implements Serializable {

//<editor-fold defaultstate="collapsed" desc="Entity Attributes">
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HR_EMP_CONTRACTS_SEQ")
    @SequenceGenerator(name = "HR_EMP_CONTRACTS_SEQ", sequenceName = "HR_EMP_CONTRACTS_SEQ", allocationSize = 1)
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private BigDecimal id;
    @Size(max = 20)
    @Column(name = "FROM_DATE")
    private String fromDate;
    @Size(max = 20)
    @Column(name = "TO_DATE")
    private String toDate;
    @Column(name = "REASON")
    private String reason;
    @Column(name = "STATUS")
    private String status;
    @JoinColumn(name = "EMP_ID", referencedColumnName = "ID")
    @ManyToOne
    private HrEmployees empId;
//</editor-fold>
     
//<editor-fold defaultstate="collapsed" desc="getter and setter">
    public HrEmpContracts() {
    }
    
    
    public HrEmpContracts(BigDecimal id) {
        this.id = id;
    }
    
    public BigDecimal getId() {
        return id;
    }
    
    public void setId(BigDecimal id) {
        this.id = id;
    }
    
    public String getFromDate() {
        return fromDate;
    }
    
    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }
    
    public String getToDate() {
        return toDate;
    }
    
    public void setToDate(String toDate) {
        this.toDate = toDate;
    }
    
    public String getReason() {
        return reason;
    }
    
    public void setReason(String reason) {
        this.reason = reason;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public HrEmployees getEmpId() {
        return empId;
    }
    
    public void setEmpId(HrEmployees empId) {
        this.empId = empId;
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
        if (!(object instanceof HrEmpContracts)) {
            return false;
        }
        HrEmpContracts other = (HrEmpContracts) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }
    
    @Override
    public String toString() {
        return "et.gov.eep.hrms.entity.employee.HrEmpContracts[ id=" + id + " ]";
    }
//</editor-fold>
}
