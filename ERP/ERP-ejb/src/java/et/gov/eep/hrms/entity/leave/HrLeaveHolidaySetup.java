/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.entity.leave;

import et.gov.eep.fcms.entity.FmsLuBudgetYear;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author user
 */
@Entity
@Table(name = "HR_LEAVE_HOLIDAY_SETUP")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HrLeaveHolidaySetup.findAll", query = "SELECT h FROM HrLeaveHolidaySetup h"),
    @NamedQuery(name = "HrLeaveHolidaySetup.findById", query = "SELECT h FROM HrLeaveHolidaySetup h WHERE h.id = :id"),    
    @NamedQuery(name = "HrLeaveHolidaySetup.isbeteween", query = "SELECT h FROM HrLeaveHolidaySetup h WHERE h.holidayDateInGc BETWEEN :x AND :y"),
    
    @NamedQuery(name = "HrLeaveHolidaySetup.findByBudgetYear", query = "SELECT h FROM HrLeaveHolidaySetup h WHERE h.fmsLuBudgetYear.budgetYear = :budgetYear"),
    @NamedQuery(name = "HrLeaveHolidaySetup.findByHolidayDate", query = "SELECT h FROM HrLeaveHolidaySetup h WHERE h.holidayDateInEc = :holidayDateInEc")})
public class HrLeaveHolidaySetup implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HR_LEAVE_HOLIDAY_SETUP_SEQ")
    @SequenceGenerator(name = "HR_LEAVE_HOLIDAY_SETUP_SEQ", sequenceName = "HR_LEAVE_HOLIDAY_SETUP_SEQ", allocationSize = 1)
    private BigDecimal id;
    
    @Size(max = 20)
    @Column(name = "HOLIDAY_DATE_IN_EC")
    private String holidayDateInEc;
    
    @Column(name = "HOLIDAY_DATE_IN_GC")     
    @Temporal(TemporalType.TIMESTAMP)
    private Date holidayDateInGc;
    
    @JoinColumn(name = "FMS_LU_BUDGET_YEAR", referencedColumnName = "LU_BUDGET_YEAR_ID")
    @ManyToOne
    private FmsLuBudgetYear fmsLuBudgetYear;
    @JoinColumn(name = "LU_HOLIDAY_NAME_ID", referencedColumnName = "ID")
    @ManyToOne
    private HrLuHolidayNames luHolidayNameId;

    public HrLeaveHolidaySetup() {
    }

    public HrLeaveHolidaySetup(BigDecimal id) {
        this.id = id;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public String getHolidayDateInEc() {
        return holidayDateInEc;
    }

    public void setHolidayDateInEc(String holidayDateInEc) {
        this.holidayDateInEc = holidayDateInEc;
    }

    public Date getHolidayDateInGc() {
        return holidayDateInGc;
    }

    public void setHolidayDateInGc(Date holidayDateInGc) {
        this.holidayDateInGc = holidayDateInGc;
    }


    public FmsLuBudgetYear getFmsLuBudgetYear() {
        return fmsLuBudgetYear;
    }

    public void setFmsLuBudgetYear(FmsLuBudgetYear fmsLuBudgetYear) {
        this.fmsLuBudgetYear = fmsLuBudgetYear;
    }

    public HrLuHolidayNames getLuHolidayNameId() {
        return luHolidayNameId;
    }

    public void setLuHolidayNameId(HrLuHolidayNames luHolidayNameId) {
        this.luHolidayNameId = luHolidayNameId;
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
        if (!(object instanceof HrLeaveHolidaySetup)) {
            return false;
        }
        HrLeaveHolidaySetup other = (HrLeaveHolidaySetup) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "et.gov.eep.hrms.entity.leave.HrLeaveHolidaySetup[ id=" + id + " ]";
    }

}
