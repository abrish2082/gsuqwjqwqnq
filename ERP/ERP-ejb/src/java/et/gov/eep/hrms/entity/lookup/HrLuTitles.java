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
 * @author user
 */
@Entity
@Table(name = "HR_LU_TITLES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HrLuTitles.findAll", query = "SELECT h FROM HrLuTitles h order by h.id"),
    @NamedQuery(name = "HrLuTitles.findById", query = "SELECT h FROM HrLuTitles h WHERE h.id = :id"),
    @NamedQuery(name = "HrLuTitles.findByTitle", query = "SELECT h FROM HrLuTitles h WHERE h.title = :title")})
public class HrLuTitles implements Serializable {
    @OneToMany(mappedBy = "titleId")
    private List<HrEmployees> hrEmployeesList;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private Integer id;
    @Size(max = 20)
    @Column(name = "TITLE")
    private String title;

    /**
     *
     */
    public HrLuTitles() {
    }

    /**
     *
     * @param id
     */
    public HrLuTitles(Integer id) {
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
    public String getTitle() {
        return title;
    }

    /**
     *
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
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
        if (!(object instanceof HrLuTitles)) {
            return false;
        }
        HrLuTitles other = (HrLuTitles) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return title;
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
