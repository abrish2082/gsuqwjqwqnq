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
@Table(name = "HR_ALLOWANCE_IN_LEVELS")
@XmlRootElement
@NamedQueries({
    //    @NamedQuery(name = "HrAllowanceInLevels.checkAllowance", query = "SELECT h FROM HrAllowanceInLevels h where h.allowanceIn=:allowanceIn and h.earningDeductionId.code=:code and h.levelId.id=:id"),

    //     @NamedQuery(name = "HrAllowanceInLevels.checkAllowance", query = "SELECT h FROM HrAllowanceInLevels h where (h.allowanceIn='Birr' or h.allowanceIn='Percent' or h.allowanceIn='kind In Birr/Amount') and h.earningDeductionId.code=:code and h.levelId.id=:id and h.locationId.addressId = :addressId"),

    @NamedQuery(name = "HrAllowanceInLevels.checkAllowance", query = "SELECT h FROM HrAllowanceInLevels h where (h.allowanceIn='Birr' or h.allowanceIn='Percent' or h.allowanceIn='kind In Birr/Amount') and h.earningDeductionId.code=:code and h.levelId.id=:id "),

    @NamedQuery(name = "HrPayrollEarningDeductions.searchByItemCodeForUpdate", query = "SELECT h FROM HrPayrollEarningDeductions h where h.itemCode = :itemCode and h.code <> :code"),

//     @NamedQuery(name = "HrAllowanceInLevels.checkAllowanceForUpdate", query = "SELECT h FROM HrAllowanceInLevels h where  h.id !=?1 and h.levelId.id =?2 and h.locationId.addressId =?3 and h.earningDeductionId.code =?4"),
    @NamedQuery(name = "HrAllowanceInLevels.checkAllowanceForUpdate", query = "SELECT h FROM HrAllowanceInLevels h where  h.id !=?1 and h.levelId.id =?2  and h.earningDeductionId.code =?3"),

    @NamedQuery(name = "HrAllowanceInLevels.findJobLevelCodes", query = "SELECT DISTINCT(c) FROM HrAllowanceInLevels h join h.earningDeductionId c"),
    @NamedQuery(name = "HrAllowanceInLevels.findJobLevelInCodeAndType", query = "SELECT h FROM HrAllowanceInLevels h where h.earningDeductionId.code=:code"),

    @NamedQuery(name = "HrAllowanceInLevels.findAll", query = "SELECT h FROM HrAllowanceInLevels h"),
    @NamedQuery(name = "HrAllowanceInLevels.findById", query = "SELECT h FROM HrAllowanceInLevels h WHERE h.id = :id"),
    @NamedQuery(name = "HrAllowanceInLevels.findByAllowanceIn", query = "SELECT h FROM HrAllowanceInLevels h WHERE h.allowanceIn = :allowanceIn"),
    @NamedQuery(name = "HrAllowanceInLevels.findByAmount", query = "SELECT h FROM HrAllowanceInLevels h WHERE h.amount = :amount"),
    @NamedQuery(name = "HrAllowanceInLevels.findByRegionId", query = "SELECT h FROM HrAllowanceInLevels h WHERE h.regionId = :regionId"),
    @NamedQuery(name = "HrAllowanceInLevels.findByDescription", query = "SELECT h FROM HrAllowanceInLevels h WHERE h.description = :description"),
    @NamedQuery(name = "HrAllowanceInLevels.findByPaymentIn", query = "SELECT h FROM HrAllowanceInLevels h WHERE h.paymentIn = :paymentIn"),
    @NamedQuery(name = "HrAllowanceInLevels.findByKindName", query = "SELECT h FROM HrAllowanceInLevels h WHERE h.kindName = :kindName"),
    @NamedQuery(name = "HrAllowanceInLevels.findByKindAmount", query = "SELECT h FROM HrAllowanceInLevels h WHERE h.kindAmount = :kindAmount"),
    @NamedQuery(name = "HrAllowanceInLevels.findByLocationId", query = "SELECT h FROM HrAllowanceInLevels h WHERE h.locationId = :locationId")})
public class HrAllowanceInLevels implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")

    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HR_ALLOWANCE_IN_LEVELS_SEQ")
    @SequenceGenerator(name = "HR_ALLOWANCE_IN_LEVELS_SEQ", sequenceName = "HR_ALLOWANCE_IN_LEVELS_SEQ", allocationSize = 1)

    private BigDecimal id;
    @Size(max = 20)
    @Column(name = "ALLOWANCE_IN")
    private String allowanceIn;
    @Column(name = "AMOUNT")
    private BigInteger amount;
    @Size(max = 20)
    @Column(name = "REGION_ID")
    private String regionId;
    @Size(max = 60)
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

    @JoinColumn(name = "LEVEL_ID", referencedColumnName = "ID")
    @ManyToOne
    private HrLuJobLevels levelId;
    @JoinColumn(name = "EARNING_DEDUCTION_ID", referencedColumnName = "CODE")
    @ManyToOne
    private HrPayrollEarningDeductions earningDeductionId;

    @JoinColumn(name = "PL_PG", referencedColumnName = "ID")
    @ManyToOne
    private HrPayrollPlPg plPg;

    public HrAllowanceInLevels() {
    }

    public HrAllowanceInLevels(BigDecimal id) {
        this.id = id;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public String getAllowanceIn() {
        return allowanceIn;
    }

    public void setAllowanceIn(String allowanceIn) {
        this.allowanceIn = allowanceIn;
    }

    public BigInteger getAmount() {
        return amount;
    }

    public void setAmount(BigInteger amount) {
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

    public HrLuJobLevels getLevelId() {
        return levelId;
    }

    public void setLevelId(HrLuJobLevels levelId) {
        this.levelId = levelId;
    }

    public HrPayrollEarningDeductions getEarningDeductionId() {
        return earningDeductionId;
    }

    public void setEarningDeductionId(HrPayrollEarningDeductions earningDeductionId) {
        this.earningDeductionId = earningDeductionId;
    }

    public HrPayrollPlPg getPlPg() {
        return plPg;
    }

    public void setPlPg(HrPayrollPlPg plPg) {
        this.plPg = plPg;
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
        if (!(object instanceof HrAllowanceInLevels)) {
            return false;
        }
        HrAllowanceInLevels other = (HrAllowanceInLevels) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "et.gov.eep.hrms.entity.allowance.HrAllowanceInLevels[ id=" + id + " ]";
    }

}
