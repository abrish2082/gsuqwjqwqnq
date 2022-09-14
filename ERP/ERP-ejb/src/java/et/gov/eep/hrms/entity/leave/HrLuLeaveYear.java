/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.entity.leave;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
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
@Table(name = "HR_LU_LEAVE_YEAR")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HrLuLeaveYear.findAll", query = "SELECT h FROM HrLuLeaveYear h"),
    @NamedQuery(name = "HrLuLeaveYear.findById", query = "SELECT h FROM HrLuLeaveYear h WHERE h.id = :id"),
    @NamedQuery(name = "HrLuLeaveYear.findByLyear", query = "SELECT h FROM HrLuLeaveYear h WHERE h.lyear = :lyear"),
    @NamedQuery(name = "HrLuLeaveYear.findActiveLyear", query = "SELECT h FROM HrLuLeaveYear h WHERE h.status = :stat"),
    @NamedQuery(name = "HrLuLeaveYear.findByStatus", query = "SELECT h FROM HrLuLeaveYear h WHERE h.status = :status")})
public class HrLuLeaveYear implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private Long id;
    @Size(max = 20)
    @Column(name = "LYEAR")
    private String lyear;
    @Size(max = 20)
    @Column(name = "STATUS")
    private String status;
    

    public HrLuLeaveYear() {
    }

    public HrLuLeaveYear(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLyear() {
        return lyear;
    }

    public void setLyear(String lyear) {
        this.lyear = lyear;
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
        if (!(object instanceof HrLuLeaveYear)) {
            return false;
        }
        HrLuLeaveYear other = (HrLuLeaveYear) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return lyear;
    }

}
