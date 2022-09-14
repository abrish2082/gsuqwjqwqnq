/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.entity.leave;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
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
@Table(name = "HR_LU_HOLIDAYS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HrLuHolidays.findAll", query = "SELECT h FROM HrLuHolidays h"),
    @NamedQuery(name = "HrLuHolidays.findByDateGC", query = "SELECT h FROM HrLuHolidays h WHERE h.dateGc = :dateGc"),
    @NamedQuery(name = "HrLuHolidays.isbeteween", query = "SELECT h FROM HrLuHolidays h WHERE h.dateGc BETWEEN :x AND :y")})
public class HrLuHolidays implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private Integer id;
    @Size(max = 200)
    @Column(name = "HOLIDAY_NAME")
    private String holidayName;
    @Column(name = "DATE_GC")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateGc;
    @Column(name = "DATE_EC")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateEc;

    public HrLuHolidays() {
    }

    public HrLuHolidays(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getHolidayName() {
        return holidayName;
    }

    public void setHolidayName(String holidayName) {
        this.holidayName = holidayName;
    }

    public Date getDateGc() {
        return dateGc;
    }

    public void setDateGc(Date dateGc) {
        this.dateGc = dateGc;
    }

    public Date getDateEc() {
        return dateEc;
    }

    public void setDateEc(Date dateEc) {
        this.dateEc = dateEc;
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
        if (!(object instanceof HrLuHolidays)) {
            return false;
        }
        HrLuHolidays other = (HrLuHolidays) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.HrLuHolidays[ id=" + id + " ]";
    }

}
