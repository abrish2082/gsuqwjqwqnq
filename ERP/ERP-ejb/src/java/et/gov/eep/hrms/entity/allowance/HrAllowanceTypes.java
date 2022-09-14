/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.entity.allowance;

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
@Table(name = "HR_ALLOWANCE_TYPES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HrAllowanceTypes.findAll", query = "SELECT h FROM HrAllowanceTypes h"),
    @NamedQuery(name = "HrAllowanceTypes.findById", query = "SELECT h FROM HrAllowanceTypes h WHERE h.id = :id"),
    @NamedQuery(name = "HrAllowanceTypes.findByAllowanceName", query = "SELECT h FROM HrAllowanceTypes h WHERE h.allowanceName = :allowanceName"),
    @NamedQuery(name = "HrAllowanceTypes.findByType", query = "SELECT h FROM HrAllowanceTypes h WHERE h.type = :type")})
public class HrAllowanceTypes implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "ID")
    private String id;
    @Size(max = 20)
    @Column(name = "ALLOWANCE_NAME")
    private String allowanceName;
    @Size(max = 20)
    @Column(name = "TYPE")
    private String type;

    /**
     *
     */
    public HrAllowanceTypes() {
    }

    /**
     *
     * @param id
     */
    public HrAllowanceTypes(String id) {
        this.id = id;
    }

    /**
     *
     * @return
     */
    public String getId() {
        return id;
    }

    /**
     *
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     *
     * @return
     */
    public String getAllowanceName() {
        return allowanceName;
    }

    /**
     *
     * @param allowanceName
     */
    public void setAllowanceName(String allowanceName) {
        this.allowanceName = allowanceName;
    }

    /**
     *
     * @return
     */
    public String getType() {
        return type;
    }

    /**
     *
     * @param type
     */
    public void setType(String type) {
        this.type = type;
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
        if (!(object instanceof HrAllowanceTypes)) {
            return false;
        }
        HrAllowanceTypes other = (HrAllowanceTypes) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "et.gov.eep.hrms.entity.allowance.HrAllowanceTypes[ id=" + id + " ]";
    }

    
}
