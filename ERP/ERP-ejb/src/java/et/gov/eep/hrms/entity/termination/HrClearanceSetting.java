/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.entity.termination;

import et.gov.eep.hrms.entity.organization.HrDepartments;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Ob
 */
@Entity
@Table(name = "HR_CLEARANCE_SETTING")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HrClearanceSetting.findAll", query = "SELECT h FROM HrClearanceSetting h"),
    @NamedQuery(name = "HrClearanceSetting.findById", query = "SELECT h FROM HrClearanceSetting h WHERE h.id = :id"),
    @NamedQuery(name = "HrClearanceSetting.findByStatus", query = "SELECT h FROM HrClearanceSetting h WHERE h.status = true ORDER BY h.priority ASC")})
public class HrClearanceSetting implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "STATUS")
    private boolean status = false;
    @Column(name = "PRIORITY")
    private Integer priority;
    @JoinColumn(name = "DEPT_ID", referencedColumnName = "DEP_ID")
    @OneToOne(fetch = FetchType.LAZY)
    private HrDepartments deptId;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "clearanceSettingId", fetch = FetchType.LAZY)
    private List<HrClearance> hrClearanceList;

    public HrClearanceSetting() {
    }

    public HrClearanceSetting(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Integer getPriority() {
        if (priority == null) {
            priority = 1;
        }
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public HrDepartments getDeptId() {
        return deptId;
    }

    public void setDeptId(HrDepartments deptId) {
        this.deptId = deptId;
    }

    @XmlTransient
    public List<HrClearance> getHrClearanceList() {
        return hrClearanceList;
    }

    public void setHrClearanceList(List<HrClearance> hrClearanceList) {
        this.hrClearanceList = hrClearanceList;
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
        if (!(object instanceof HrClearanceSetting)) {
            return false;
        }
        HrClearanceSetting other = (HrClearanceSetting) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.HrClearanceSetting[ id=" + id + " ]";
    }

}
