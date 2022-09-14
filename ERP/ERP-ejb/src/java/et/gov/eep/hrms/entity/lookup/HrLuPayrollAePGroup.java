/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.entity.lookup;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author user
 */
@Entity
@Table(name = "HR_LU_PAYROLL_AE_P_GROUP")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HrLuPayrollAePGroup.findAll", query = "SELECT h FROM HrLuPayrollAePGroup h"),
    @NamedQuery(name = "HrLuPayrollAePGroup.findById", query = "SELECT h FROM HrLuPayrollAePGroup h WHERE h.id = :id"),
    @NamedQuery(name = "HrLuPayrollAePGroup.findByGroupName", query = "SELECT h FROM HrLuPayrollAePGroup h WHERE h.groupName = :groupName")})
public class HrLuPayrollAePGroup implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private BigDecimal id;
    @Size(max = 20)
    @Column(name = "GROUP_NAME")
    private String groupName;

    public HrLuPayrollAePGroup() {
    }

    public HrLuPayrollAePGroup(BigDecimal id) {
        this.id = id;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
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
        if (!(object instanceof HrLuPayrollAePGroup)) {
            return false;
        }
        HrLuPayrollAePGroup other = (HrLuPayrollAePGroup) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "et.gov.eep.hrms.entity.lookup.HrLuPayrollAePGroup[ id=" + id + " ]";
    }
    
}
