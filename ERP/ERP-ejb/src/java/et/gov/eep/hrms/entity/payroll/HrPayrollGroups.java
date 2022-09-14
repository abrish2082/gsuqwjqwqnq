/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.entity.payroll;

import java.io.Serializable;
import java.math.BigDecimal;
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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author user
 */
@Entity
@Table(name = "HR_PAYROLL_GROUPS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HrPayrollGroups.findAll", query = "SELECT h FROM HrPayrollGroups h"),
    @NamedQuery(name = "HrPayrollGroups.findById", query = "SELECT h FROM HrPayrollGroups h WHERE h.id = :id"),
    @NamedQuery(name = "HrPayrollGroups.findByGroupAnme", query = "SELECT h FROM HrPayrollGroups h WHERE h.groupAnme = :groupAnme"),
    @NamedQuery(name = "HrPayrollGroups.findByStatus", query = "SELECT h FROM HrPayrollGroups h WHERE h.status = :status")})
public class HrPayrollGroups implements Serializable {
    @Size(max = 20)
    @Column(name = "START_DATE")
    private String startDate;
    @Size(max = 20)
    @Column(name = "END_DATE")
    private String endDate;
    @OneToMany(mappedBy = "groupId")
    private List<HrPayrollFilterBp> hrPayrollFilterBpList;
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    
     @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HR_PAYROLL_GROUPS_SEQ") //
    @SequenceGenerator(name = "HR_PAYROLL_GROUPS_SEQ", sequenceName = "HR_PAYROLL_GROUPS_SEQ", allocationSize = 1)
    
    private BigDecimal id;
    @Size(max = 60)
    @Column(name = "GROUP_ANME")
    private String groupAnme;
    @Size(max = 20)
    @Column(name = "STATUS")
    private String status;
      @Size(max = 20)
    @Column(name = "GROUP_FOR")
    private String groupFor;
   
    public HrPayrollGroups() {
    }

    public HrPayrollGroups(BigDecimal id) {
        this.id = id;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public String getGroupAnme() {
        return groupAnme;
    }

    public void setGroupAnme(String groupAnme) {
        this.groupAnme = groupAnme;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
        if (!(object instanceof HrPayrollGroups)) {
            return false;
        }
        HrPayrollGroups other = (HrPayrollGroups) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "et.gov.eep.hrms.entity.payroll.HrPayrollGroups[ id=" + id + " ]";
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    @XmlTransient
    public List<HrPayrollFilterBp> getHrPayrollFilterBpList() {
        return hrPayrollFilterBpList;
    }

    public void setHrPayrollFilterBpList(List<HrPayrollFilterBp> hrPayrollFilterBpList) {
        this.hrPayrollFilterBpList = hrPayrollFilterBpList;
    }
    
}
