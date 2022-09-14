/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.entity.allowance;

import et.gov.eep.hrms.entity.lookup.HrLuJobLevels;
import et.gov.eep.hrms.entity.payroll.HrPayrollEarningDeductions;
import et.gov.eep.hrms.entity.payroll.HrPayrollPlPg;
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
 * @author user
 */
@Entity
@Table(name = "HR_ALLOWANCE_IN_LOCATIONS")
@XmlRootElement
@NamedQueries({
    
//         @NamedQuery(name = "HrAllowanceInLocations.checkAllInLoca", query = "SELECT h FROM HrAllowanceInLocations h where (h.paymentIn='Birr' or h.paymentIn='Percent' or h.paymentIn='kind In Birr/Amount') and h.earningDeductionCode.code=:code and h.locationId.addressId=:addressId and h.levelId.id=:id"),
//    
//    @NamedQuery(name = "HrAllowanceInLocations.checkAllInLocaForUpdate", query = "SELECT h FROM HrAllowanceInLocations h where (h.paymentIn='Birr' or h.paymentIn='Percent' or h.paymentIn='kind In Birr/Amount') and h.earningDeductionCode.code=?1 and h.locationId.addressId=?2 and h.levelId.id=?3 and h.id!=?4"),
//    
    
     @NamedQuery(name = "HrAllowanceInLocations.checkAllInLoca", query = "SELECT h FROM HrAllowanceInLocations h where (h.paymentIn='Birr' or h.paymentIn='Percent' or h.paymentIn='kind In Birr/Amount') and h.earningDeductionCode.code=:code  and h.levelId.id=:id"),
    
    @NamedQuery(name = "HrAllowanceInLocations.checkAllInLocaForUpdate", query = "SELECT h FROM HrAllowanceInLocations h where (h.paymentIn='Birr' or h.paymentIn='Percent' or h.paymentIn='kind In Birr/Amount') and h.earningDeductionCode.code=?1  and h.levelId.id=?3 and h.id!=?4"),
    
    
    @NamedQuery(name = "HrAllowanceInLocations.findAllAllowanceInJobTitle", query = "SELECT DISTINCT(al) FROM HrAllowanceInLocations h join h.earningDeductionCode al"),
    @NamedQuery(name = "HrAllowanceInLocations.findAll", query = "SELECT h FROM HrAllowanceInLocations h"),
    @NamedQuery(name = "HrAllowanceInLocations.findPaymentTypes", query = "SELECT h FROM HrAllowanceInLocations h where h.earningDeductionCode.code=:code"),

    @NamedQuery(name = "HrAllowanceInLocations.findAll", query = "SELECT h FROM HrAllowanceInLocations h"),
    @NamedQuery(name = "HrAllowanceInLocations.findById", query = "SELECT h FROM HrAllowanceInLocations h WHERE h.id = :id"),
//    @NamedQuery(name = "HrAllowanceInLocations.findByLocationId", query = "SELECT h FROM HrAllowanceInLocations h WHERE h.locationId = :locationId"),
    @NamedQuery(name = "HrAllowanceInLocations.findByAmount", query = "SELECT h FROM HrAllowanceInLocations h WHERE h.amount = :amount"),
    @NamedQuery(name = "HrAllowanceInLocations.findByPaymentIn", query = "SELECT h FROM HrAllowanceInLocations h WHERE h.paymentIn = :paymentIn"),
    @NamedQuery(name = "HrAllowanceInLocations.findByOfSalary", query = "SELECT h FROM HrAllowanceInLocations h WHERE h.ofSalary = :ofSalary"),
    @NamedQuery(name = "HrAllowanceInLocations.findByAnyRelatedPrice", query = "SELECT h FROM HrAllowanceInLocations h WHERE h.anyRelatedPrice = :anyRelatedPrice"),
    @NamedQuery(name = "HrAllowanceInLocations.findByDescription", query = "SELECT h FROM HrAllowanceInLocations h WHERE h.description = :description")})
public class HrAllowanceInLocations implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "ID")
    
     @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HR_ALLOWANCE_IN_LOCATIONS_SEQ")
    @SequenceGenerator(name = "HR_ALLOWANCE_IN_LOCATIONS_SEQ", sequenceName = "HR_ALLOWANCE_IN_LOCATIONS_SEQ", allocationSize = 1)
    private String id;
//    @Column(name = "LOCATION_ID")
//    private BigInteger locationId;
//    @Size(max = 20)
    @Column(name = "AMOUNT")
    private String amount;
    @Size(max = 20)
    @Column(name = "PAYMENT_IN")
    private String paymentIn;
    @Size(max = 20)
    @Column(name = "OF_SALARY")
    private String ofSalary;
    @Size(max = 20)
    @Column(name = "ANY_RELATED_PRICE")
    private String anyRelatedPrice;
    @Size(max = 60)
    @Column(name = "DESCRIPTION")
    private String description;
    @JoinColumn(name = "LEVEL_ID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private HrLuJobLevels levelId;
    @JoinColumn(name = "EARNING_DEDUCTION_CODE", referencedColumnName = "CODE")
    @ManyToOne
    private HrPayrollEarningDeductions earningDeductionCode;
    
    @JoinColumn(name = "JL_JL", referencedColumnName = "ID")
    @ManyToOne
    private HrPayrollPlPg jlJl;

    public HrAllowanceInLocations() {
    }

    public HrAllowanceInLocations(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

//    public BigInteger getLocationId() {
//        return locationId;
//    }
//
//    public void setLocationId(BigInteger locationId) {
//        this.locationId = locationId;
//    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getPaymentIn() {
        return paymentIn;
    }

    public void setPaymentIn(String paymentIn) {
        this.paymentIn = paymentIn;
    }

    public String getOfSalary() {
        return ofSalary;
    }

    public void setOfSalary(String ofSalary) {
        this.ofSalary = ofSalary;
    }

    public String getAnyRelatedPrice() {
        return anyRelatedPrice;
    }

    public void setAnyRelatedPrice(String anyRelatedPrice) {
        this.anyRelatedPrice = anyRelatedPrice;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public HrLuJobLevels getLevelId() {
        return levelId;
    }

    public void setLevelId(HrLuJobLevels levelId) {
        this.levelId = levelId;
    }

    public HrPayrollEarningDeductions getEarningDeductionCode() {
        return earningDeductionCode;
    }

    public void setEarningDeductionCode(HrPayrollEarningDeductions earningDeductionCode) {
        this.earningDeductionCode = earningDeductionCode;
    }

    public HrPayrollPlPg getJlJl() {
        return jlJl;
    }

    public void setJlJl(HrPayrollPlPg jlJl) {
        this.jlJl = jlJl;
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
        if (!(object instanceof HrAllowanceInLocations)) {
            return false;
        }
        HrAllowanceInLocations other = (HrAllowanceInLocations) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "et.gov.eep.hrms.entity.allowance.HrAllowanceInLocations[ id=" + id + " ]";
    }
    
}
