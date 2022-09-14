/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.entity.leave;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "HR_LU_HOLIDAY_NAMES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HrLuHolidayNames.findAll", query = "SELECT h FROM HrLuHolidayNames h"),
    @NamedQuery(name = "HrLuHolidayNames.findById", query = "SELECT h FROM HrLuHolidayNames h WHERE h.id = :id"),
    @NamedQuery(name = "HrLuHolidayNames.findByHolidayNames", query = "SELECT h FROM HrLuHolidayNames h WHERE h.holidayNames = :holidayNames")})
public class HrLuHolidayNames implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HR_LU_HOLIDAY_NAMES_SEQ")
    @SequenceGenerator(name = "HR_LU_HOLIDAY_NAMES_SEQ", sequenceName = "HR_LU_HOLIDAY_NAMES_SEQ", allocationSize = 1)
    private BigDecimal id;
    
    @Size(max = 100)
    @Column(name = "HOLIDAY_NAMES")
    private String holidayNames;
    
    @OneToMany(mappedBy = "luHolidayNameId",cascade = CascadeType.ALL)
    private List<HrLeaveHolidaySetup> hrLeaveHolidaySetupList;

    public HrLuHolidayNames() {
    }

    public HrLuHolidayNames(BigDecimal id) {
        this.id = id;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public String getHolidayNames() {
        return holidayNames;
    }

    public void setHolidayNames(String holidayNames) {
        this.holidayNames = holidayNames;
    }

    @XmlTransient
    public List<HrLeaveHolidaySetup> getHrLeaveHolidaySetupList() {
        return hrLeaveHolidaySetupList;
    }

    public void setHrLeaveHolidaySetupList(List<HrLeaveHolidaySetup> hrLeaveHolidaySetupList) {
        this.hrLeaveHolidaySetupList = hrLeaveHolidaySetupList;
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
        if (!(object instanceof HrLuHolidayNames)) {
            return false;
        }
        HrLuHolidayNames other = (HrLuHolidayNames) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return holidayNames;
    }
    
}
