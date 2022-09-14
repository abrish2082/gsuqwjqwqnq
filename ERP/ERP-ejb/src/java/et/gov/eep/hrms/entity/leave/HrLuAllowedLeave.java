/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.entity.leave;

import java.io.Serializable;
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
@Table(name = "HR_LU_ALLOWED_LEAVE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HrLuAllowedLeave.findAll", query = "SELECT h FROM HrLuAllowedLeave h"),
    @NamedQuery(name = "HrLuAllowedLeave.findById", query = "SELECT h FROM HrLuAllowedLeave h WHERE h.id = :id"),
    @NamedQuery(name = "HrLuAllowedLeave.findByNoOfYr", query = "SELECT h FROM HrLuAllowedLeave h WHERE h.noOfYr =:noOfYr"),
    @NamedQuery(name = "HrLuAllowedLeave.findByAllowedDays", query = "SELECT h FROM HrLuAllowedLeave h WHERE h.allowedDays = :allowedDays"),
    @NamedQuery(name = "HrLuAllowedLeave.findByDescription", query = "SELECT h FROM HrLuAllowedLeave h WHERE h.description = :description")})
public class HrLuAllowedLeave implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private Integer id;
    @Column(name = "NO_OF_YR")
    private Integer noOfYr;
    @Column(name = "ALLOWED_DAYS")
    private Integer allowedDays;
    @Size(max = 20)
    @Column(name = "DESCRIPTION")
    private String description;

    public HrLuAllowedLeave() {
    }

    public HrLuAllowedLeave(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getNoOfYr() {
        return noOfYr;
    }

    public void setNoOfYr(Integer noOfYr) {
        this.noOfYr = noOfYr;
    }

    public Integer getAllowedDays() {
        return allowedDays;
    }

    public void setAllowedDays(Integer allowedDays) {
        this.allowedDays = allowedDays;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
        if (!(object instanceof HrLuAllowedLeave)) {
            return false;
        }
        HrLuAllowedLeave other = (HrLuAllowedLeave) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.HrLuAllowedLeave[ id=" + id + " ]";
    }

}
