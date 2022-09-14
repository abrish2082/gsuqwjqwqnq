/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.entity.lookup;

import et.gov.eep.hrms.entity.employee.HrEmployees;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
 * @author Man
 */
@Entity
@Table(name = "HR_LU_RELIGIONS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HrLuReligions.findAll", query = "SELECT h FROM HrLuReligions h"),
    @NamedQuery(name = "HrLuReligions.findById", query = "SELECT h FROM HrLuReligions h WHERE h.id = :id"),
    @NamedQuery(name = "HrLuReligions.findByReligion", query = "SELECT h FROM HrLuReligions h WHERE h.religion = :religion"),
    @NamedQuery(name = "HrLuReligions.findByDescription", query = "SELECT h FROM HrLuReligions h WHERE h.description = :description")})
public class HrLuReligions implements Serializable {
    @OneToMany(mappedBy = "religionId")
    private List<HrEmployees> hrEmployeesList;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private Integer id;
    @Size(max = 50)
    @Column(name = "RELIGION")
    private String religion;
    @Size(max = 100)
    @Column(name = "DESCRIPTION")
    private String description;

    /**
     *
     */
    public HrLuReligions() {
    }

    /**
     *
     * @param id
     */
    public HrLuReligions(Integer id) {
        this.id = id;
    }

    /**
     *
     * @return
     */
    public Integer getId() {
        return id;
    }

    /**
     *
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     *
     * @return
     */
    public String getReligion() {
        return religion;
    }

    /**
     *
     * @param religion
     */
    public void setReligion(String religion) {
        this.religion = religion;
    }

    /**
     *
     * @return
     */
    public String getDescription() {
        return description;
    }

    /**
     *
     * @param description
     */
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
        if (!(object instanceof HrLuReligions)) {
            return false;
        }
        HrLuReligions other = (HrLuReligions) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return religion;
    }

    /**
     *
     * @return
     */
    @XmlTransient
    public List<HrEmployees> getHrEmployeesList() {
        return hrEmployeesList;
    }

    /**
     *
     * @param hrEmployeesList
     */
    public void setHrEmployeesList(List<HrEmployees> hrEmployeesList) {
        this.hrEmployeesList = hrEmployeesList;
    }
    
}
