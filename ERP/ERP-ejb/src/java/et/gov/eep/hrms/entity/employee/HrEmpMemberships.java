/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.entity.employee;

import et.gov.eep.hrms.entity.lookup.HrLuMemberships;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author user
 */
@Entity
@Table(name = "HR_EMP_MEMBERSHIPS")
@XmlRootElement
@NamedQueries//<editor-fold defaultstate="collapsed" desc="">
        ({
            @NamedQuery(name = "HrEmpMemberships.findAll", query = "SELECT h FROM HrEmpMemberships h"),
            @NamedQuery(name = "HrEmpMemberships.findById", query = "SELECT h FROM HrEmpMemberships h WHERE h.id = :id"),
            @NamedQuery(name = "HrEmpMemberships.findBySubscriptionPaidBy", query = "SELECT h FROM HrEmpMemberships h WHERE h.subscriptionPaidBy = :subscriptionPaidBy"),
            @NamedQuery(name = "HrEmpMemberships.findBySubscriptionAmount", query = "SELECT h FROM HrEmpMemberships h WHERE h.subscriptionAmount = :subscriptionAmount"),
            @NamedQuery(name = "HrEmpMemberships.findBySubscriptionCommenceDate", query = "SELECT h FROM HrEmpMemberships h WHERE h.subscriptionCommenceDate = :subscriptionCommenceDate"),
            @NamedQuery(name = "HrEmpMemberships.findBySubscriptionRenewalDate", query = "SELECT h FROM HrEmpMemberships h WHERE h.subscriptionRenewalDate = :subscriptionRenewalDate")})
//</editor-fold>
public class HrEmpMemberships implements Serializable {
    //<editor-fold defaultstate="collapsed" desc="Entity Attributes">
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HR_EMP_MEMBERSHIPS_SEQ")
    @SequenceGenerator(name = "HR_EMP_MEMBERSHIPS_SEQ", sequenceName = "HR_EMP_MEMBERSHIPS_SEQ", allocationSize = 1)
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private BigDecimal id;
    @Column(name = "SUBSCRIPTION_PAID_BY")
    private String subscriptionPaidBy;
    @Column(name = "SUBSCRIPTION_AMOUNT")
    private BigInteger subscriptionAmount;
    @Size(max = 20)
    @Column(name = "SUBSCRIPTION_COMMENCE_DATE")
    private String subscriptionCommenceDate;
    @Size(max = 20)
    @Column(name = "SUBSCRIPTION_RENEWAL_DATE")
    private String subscriptionRenewalDate;
    @JoinColumn(name = "EMP_ID", referencedColumnName = "ID")
    @ManyToOne
    private HrEmployees empId;
    @JoinColumn(name = "MEMBERSHIP_ID", referencedColumnName = "ID")
    @ManyToOne
    private HrLuMemberships membershipId;
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="getter and setter">
    public HrEmpMemberships() {
    }
    
    public HrEmpMemberships(BigDecimal id) {
        this.id = id;
    }
    
    public BigDecimal getId() {
        return id;
    }
    
    public void setId(BigDecimal id) {
        this.id = id;
    }
    
    public String getSubscriptionPaidBy() {
        return subscriptionPaidBy;
    }
    
    public void setSubscriptionPaidBy(String subscriptionPaidBy) {
        this.subscriptionPaidBy = subscriptionPaidBy;
    }
    
    public BigInteger getSubscriptionAmount() {
        return subscriptionAmount;
    }
    
    public void setSubscriptionAmount(BigInteger subscriptionAmount) {
        this.subscriptionAmount = subscriptionAmount;
    }
    
    public String getSubscriptionCommenceDate() {
        return subscriptionCommenceDate;
    }
    
    public void setSubscriptionCommenceDate(String subscriptionCommenceDate) {
        this.subscriptionCommenceDate = subscriptionCommenceDate;
    }
    
    public String getSubscriptionRenewalDate() {
        return subscriptionRenewalDate;
    }
    
    public void setSubscriptionRenewalDate(String subscriptionRenewalDate) {
        this.subscriptionRenewalDate = subscriptionRenewalDate;
    }
    
    public HrEmployees getEmpId() {
        return empId;
    }
    
    public void setEmpId(HrEmployees empId) {
        this.empId = empId;
    }
    
    public HrLuMemberships getMembershipId() {
        return membershipId;
    }
    
    public void setMembershipId(HrLuMemberships membershipId) {
        this.membershipId = membershipId;
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
        if (!(object instanceof HrEmpMemberships)) {
            return false;
        }
        HrEmpMemberships other = (HrEmpMemberships) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }
    
    @Override
    public String toString() {
        return "et.gov.eep.hrms.entity.employee.HrEmpMemberships[ id=" + id + " ]";
    }
//</editor-fold>
    
}
