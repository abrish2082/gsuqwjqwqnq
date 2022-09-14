/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.entity.allowance;

import et.gov.eep.hrms.entity.organization.HrJobTypes;
import et.gov.eep.hrms.entity.payroll.HrPayrollEarningDeductions;
import et.gov.eep.hrms.entity.payroll.HrPayrollPlPg;
import java.io.Serializable;
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
@Table(name = "HR_ALLOWANCE_IN_JOB_TITLE")
@XmlRootElement
@NamedQueries({
    
//        @NamedQuery(name = "HrAllowanceInJobTitle.findAllAllowanceInJobTitle", query = "SELECT DISTINCT(jt) FROM HrAllowanceInJobTitle h join h.earningDeductionId jt"),
//    @NamedQuery(name = "HrAllowanceInJobTitle.findAll", query = "SELECT h FROM HrAllowanceInJobTitle h"),
//    @NamedQuery(name = "HrAllowanceInJobTitle.findAllownceInJotTitlePaymentTypes", query = "SELECT h FROM HrAllowanceInJobTitle h where h.earningDeductionId.code=:code"),
//    
////    @NamedQuery(name = "HrAllowanceInJobTitle.cheackRepeatedJobTitle", query = "SELECT h FROM HrAllowanceInJobTitle h WHERE  (h.paymentIn='Birr' or h.paymentIn='Percent' or h.paymentIn='kind In Birr/Amount') and h.jobTitleId.id=?1 and h.earningDeductionId.code=?2 and h.locationId.addressId=?3"),
//////      @NamedQuery(name = "HrAllowanceInJobTitle.cheackRepeatedJobTitle", query = "SELECT h FROM HrAllowanceInJobTitle h WHERE  (h.allwanceIn='Birr' or h.allwanceIn='Percent' or h.allwanceIn='kind In Birr/Amount')"),
////    
////    
////    @NamedQuery(name = "HrAllowanceInJobTitle.cheackRepeatedJobTitleForUpdate", query = "SELECT h FROM HrAllowanceInJobTitle h WHERE  (h.paymentIn='Birr' or h.paymentIn='Percent' or h.paymentIn='kind In Birr/Amount') and h.jobTitleId.id=?1 and h.earningDeductionId.code=?2 and h.locationId.addressId=?3 and (h.id !=?4)"),
//
//    
//      @NamedQuery(name = "HrAllowanceInJobTitle.cheackRepeatedJobTitle", query = "SELECT h FROM HrAllowanceInJobTitle h WHERE  (h.paymentIn='Birr' or h.paymentIn='Percent' or h.paymentIn='kind In Birr/Amount') and h.jobTitleId.id=?1 and h.earningDeductionId.code=?2 "),
////      @NamedQuery(name = "HrAllowanceInJobTitle.cheackRepeatedJobTitle", query = "SELECT h FROM HrAllowanceInJobTitle h WHERE  (h.allwanceIn='Birr' or h.allwanceIn='Percent' or h.allwanceIn='kind In Birr/Amount')"),
//    
//    
//    @NamedQuery(name = "HrAllowanceInJobTitle.cheackRepeatedJobTitleForUpdate", query = "SELECT h FROM HrAllowanceInJobTitle h WHERE  (h.paymentIn='Birr' or h.paymentIn='Percent' or h.paymentIn='kind In Birr/Amount') and h.jobTitleId.id=?1 and h.earningDeductionId.code=?2"),
//
//    
//    
//    
//    
//    
//    
//    
//    
//    
//    
//    @NamedQuery(name = "HrAllowanceInJobTitle.findAll", query = "SELECT h FROM HrAllowanceInJobTitle h"),
//    @NamedQuery(name = "HrAllowanceInJobTitle.findById", query = "SELECT h FROM HrAllowanceInJobTitle h WHERE h.id = :id"),
//    @NamedQuery(name = "HrAllowanceInJobTitle.findByAllwanceIn", query = "SELECT h FROM HrAllowanceInJobTitle h WHERE h.allwanceIn = :allwanceIn"),
//    @NamedQuery(name = "HrAllowanceInJobTitle.findByRegion", query = "SELECT h FROM HrAllowanceInJobTitle h WHERE h.region = :region"),
//    @NamedQuery(name = "HrAllowanceInJobTitle.findByAmount", query = "SELECT h FROM HrAllowanceInJobTitle h WHERE h.amount = :amount"),
//    @NamedQuery(name = "HrAllowanceInJobTitle.findByRegionId", query = "SELECT h FROM HrAllowanceInJobTitle h WHERE h.regionId = :regionId"),
//    @NamedQuery(name = "HrAllowanceInJobTitle.findByDescription", query = "SELECT h FROM HrAllowanceInJobTitle h WHERE h.description = :description"),
//    @NamedQuery(name = "HrAllowanceInJobTitle.findByPaymentIn", query = "SELECT h FROM HrAllowanceInJobTitle h WHERE h.paymentIn = :paymentIn"),
//    @NamedQuery(name = "HrAllowanceInJobTitle.findByKindName", query = "SELECT h FROM HrAllowanceInJobTitle h WHERE h.kindName = :kindName"),
//    @NamedQuery(name = "HrAllowanceInJobTitle.findByKindAmount", query = "SELECT h FROM HrAllowanceInJobTitle h WHERE h.kindAmount = :kindAmount"),
//    @NamedQuery(name = "HrAllowanceInJobTitle.findByLocationId", query = "SELECT h FROM HrAllowanceInJobTitle h WHERE h.locationId = :locationId")})
//



 @NamedQuery(name = "HrAllowanceInJobTitle.findAllAllowanceInJobTitle", query = "SELECT DISTINCT(jt) FROM HrAllowanceInJobTitle h join h.earningDeductionId jt"),
    @NamedQuery(name = "HrAllowanceInJobTitle.findAll", query = "SELECT h FROM HrAllowanceInJobTitle h"),
    @NamedQuery(name = "HrAllowanceInJobTitle.findAllownceInJotTitlePaymentTypes", query = "SELECT h FROM HrAllowanceInJobTitle h where h.earningDeductionId.code=:code"),
    
   // @NamedQuery(name = "HrAllowanceInJobTitle.cheackRepeatedJobTitle", query = "SELECT h FROM HrAllowanceInJobTitle h WHERE  (h.paymentIn='Birr' or h.paymentIn='Percent' or h.paymentIn='kind In Birr/Amount') and h.jobTitleId.id=?1 and h.earningDeductionId.code=?2 and h.locationId.addressId=?3"),
     @NamedQuery(name = "HrAllowanceInJobTitle.cheackRepeatedJobTitle", query = "SELECT h FROM HrAllowanceInJobTitle h WHERE  (h.allwanceIn='Birr' or h.allwanceIn='Percent' or h.allwanceIn='kind In Birr/Amount')"),
    
    
   // @NamedQuery(name = "HrAllowanceInJobTitle.cheackRepeatedJobTitleForUpdate", query = "SELECT h FROM HrAllowanceInJobTitle h WHERE  (h.paymentIn='Birr' or h.paymentIn='Percent' or h.paymentIn='kind In Birr/Amount') and h.jobTitleId.id=?1 and h.earningDeductionId.code=?2 and h.locationId.addressId=?3 and (h.id !=?4)"),
     @NamedQuery(name = "HrAllowanceInJobTitle.cheackRepeatedJobTitleForUpdate", query = "SELECT h FROM HrAllowanceInJobTitle h WHERE  (h.paymentIn='Birr' or h.paymentIn='Percent' or h.paymentIn='kind In Birr/Amount') and h.jobTitleId.id=?1 and h.earningDeductionId.code=?2  and (h.id !=?3)"),

    
    @NamedQuery(name = "HrAllowanceInJobTitle.findByEdId", query = "SELECT h FROM HrAllowanceInJobTitle h WHERE h.earningDeductionId.code=:code"),
    @NamedQuery(name = "HrAllowanceInJobTitle.findAll", query = "SELECT h FROM HrAllowanceInJobTitle h"),
    @NamedQuery(name = "HrAllowanceInJobTitle.findById", query = "SELECT h FROM HrAllowanceInJobTitle h WHERE h.id = :id"),
    @NamedQuery(name = "HrAllowanceInJobTitle.findByAllwanceIn", query = "SELECT h FROM HrAllowanceInJobTitle h WHERE h.allwanceIn = :allwanceIn"),
    @NamedQuery(name = "HrAllowanceInJobTitle.findByRegion", query = "SELECT h FROM HrAllowanceInJobTitle h WHERE h.region = :region"),
    @NamedQuery(name = "HrAllowanceInJobTitle.findByAmount", query = "SELECT h FROM HrAllowanceInJobTitle h WHERE h.amount = :amount"),
    @NamedQuery(name = "HrAllowanceInJobTitle.findByRegionId", query = "SELECT h FROM HrAllowanceInJobTitle h WHERE h.regionId = :regionId"),
    @NamedQuery(name = "HrAllowanceInJobTitle.findByDescription", query = "SELECT h FROM HrAllowanceInJobTitle h WHERE h.description = :description"),
    @NamedQuery(name = "HrAllowanceInJobTitle.findByPaymentIn", query = "SELECT h FROM HrAllowanceInJobTitle h WHERE h.paymentIn = :paymentIn"),
    @NamedQuery(name = "HrAllowanceInJobTitle.findByKindName", query = "SELECT h FROM HrAllowanceInJobTitle h WHERE h.kindName = :kindName"),
    @NamedQuery(name = "HrAllowanceInJobTitle.findByKindAmount", query = "SELECT h FROM HrAllowanceInJobTitle h WHERE h.kindAmount = :kindAmount")})















public class HrAllowanceInJobTitle implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HR_ALLOWANCE_IN_JOB_TITLE_SEQ")
    @SequenceGenerator(name = "HR_ALLOWANCE_IN_JOB_TITLE_SEQ", sequenceName = "HR_ALLOWANCE_IN_JOB_TITLE_SEQ", allocationSize = 1)
    
    private String id;
    @Size(max = 20)
    @Column(name = "ALLWANCE_IN")
    private String allwanceIn;
    @Size(max = 20)
    @Column(name = "REGION")
    private String region;
    @Size(max = 20)
    @Column(name = "AMOUNT")
    private String amount;
    @Size(max = 20)
    @Column(name = "REGION_ID")
    private String regionId;
    @Size(max = 150)
    @Column(name = "DESCRIPTION")
    private String description;
    @Size(max = 20)
    @Column(name = "PAYMENT_IN")
    private String paymentIn;
    @Size(max = 20)
    @Column(name = "KIND_NAME")
    private String kindName;
    @Size(max = 20)
    @Column(name = "KIND_AMOUNT")
    private String kindAmount;
    @Column(name = "LOCATION_ID")
    private BigInteger locationId;
    @JoinColumn(name = "JOB_TITLE_ID", referencedColumnName = "ID")
    @ManyToOne
    private HrJobTypes jobTitleId;
    @JoinColumn(name = "EARNING_DEDUCTION_ID", referencedColumnName = "CODE")
    @ManyToOne
    private HrPayrollEarningDeductions earningDeductionId;
    
    @JoinColumn(name = "JP_JL", referencedColumnName = "ID")
    @ManyToOne
    private HrPayrollPlPg jpJl;

    public HrAllowanceInJobTitle() {
    }

    public HrAllowanceInJobTitle(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAllwanceIn() {
        return allwanceIn;
    }

    public void setAllwanceIn(String allwanceIn) {
        this.allwanceIn = allwanceIn;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getRegionId() {
        return regionId;
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPaymentIn() {
        return paymentIn;
    }

    public void setPaymentIn(String paymentIn) {
        this.paymentIn = paymentIn;
    }

    public String getKindName() {
        return kindName;
    }

    public void setKindName(String kindName) {
        this.kindName = kindName;
    }

    public String getKindAmount() {
        return kindAmount;
    }

    public void setKindAmount(String kindAmount) {
        this.kindAmount = kindAmount;
    }

    public BigInteger getLocationId() {
        return locationId;
    }

    public void setLocationId(BigInteger locationId) {
        this.locationId = locationId;
    }

    public HrJobTypes getJobTitleId() {
        return jobTitleId;
    }

    public void setJobTitleId(HrJobTypes jobTitleId) {
        this.jobTitleId = jobTitleId;
    }

    public HrPayrollEarningDeductions getEarningDeductionId() {
        return earningDeductionId;
    }

    public void setEarningDeductionId(HrPayrollEarningDeductions earningDeductionId) {
        this.earningDeductionId = earningDeductionId;
    }

    public HrPayrollPlPg getJpJl() {
        return jpJl;
    }

    public void setJpJl(HrPayrollPlPg jpJl) {
        this.jpJl = jpJl;
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
        if (!(object instanceof HrAllowanceInJobTitle)) {
            return false;
        }
        HrAllowanceInJobTitle other = (HrAllowanceInJobTitle) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "et.gov.eep.hrms.entity.allowance.HrAllowanceInJobTitle[ id=" + id + " ]";
    }
    
}
