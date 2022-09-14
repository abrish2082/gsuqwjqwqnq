/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.entity.leave;

import java.io.Serializable;
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
@Table(name = "HR_LU_LEAVE_TYPES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HrLuLeaveTypes.findAll", query = "SELECT h FROM HrLuLeaveTypes h"),
    @NamedQuery(name = "HrLuLeaveTypes.findById", query = "SELECT h FROM HrLuLeaveTypes h WHERE h.id = :id"),
    @NamedQuery(name = "HrLuLeaveTypes.findByLeaveName", query = "SELECT h FROM HrLuLeaveTypes h WHERE h.leaveName = :leaveName"),
    @NamedQuery(name = "HrLuLeaveTypes.findByLeaveCode", query = "SELECT h FROM HrLuLeaveTypes h WHERE h.leaveCode = :leaveCode"),
    @NamedQuery(name = "HrLuLeaveTypes.findByDescription", query = "SELECT h FROM HrLuLeaveTypes h WHERE h.description = :description"),
    @NamedQuery(name = "HrLuLeaveTypes.findByStatus", query = "SELECT h FROM HrLuLeaveTypes h WHERE h.status = :status")})
public class HrLuLeaveTypes implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HR_LU_LEAVE_TYPES_SEQ")
    @SequenceGenerator(name = "HR_LU_LEAVE_TYPES_SEQ", sequenceName = "HR_LU_LEAVE_TYPES_SEQ", allocationSize = 1)
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private Integer id;
    @Size(max = 20)
    @Column(name = "LEAVE_NAME")
    private String leaveName;
    @Size(max = 20)
    @Column(name = "LEAVE_CODE")
    private String leaveCode;
    @Size(max = 20)
    @Column(name = "DESCRIPTION")
    private String description;
    @Size(max = 20)
    @Column(name = "STATUS")
    private String status;
    @OneToMany(mappedBy = "leaveTypeId")
    private List<HrLeaveSetting> hrLeaveSettingList;
    @OneToMany(mappedBy = "leaveTypesId")
    private List<HrLeaveRequest> hrLeaveRequestList;
//    @OneToMany(mappedBy = "leaveTypeId", cascade = CascadeType.ALL)
//    private List<HrLeaveBalance> hrLeaveBalanceList;
//   

    public HrLuLeaveTypes() {
    }

    public HrLuLeaveTypes(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLeaveName() {
        return leaveName;
    }

    public void setLeaveName(String leaveName) {
        this.leaveName = leaveName;
    }

    public String getLeaveCode() {
        return leaveCode;
    }

    public void setLeaveCode(String leaveCode) {
        this.leaveCode = leaveCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

//    @XmlTransient
//    public List<HrLeaveBalance> getHrLeaveBalanceList() {
//        return hrLeaveBalanceList;
//    }
//
//    public void setHrLeaveBalanceList(List<HrLeaveBalance> hrLeaveBalanceList) {
//        this.hrLeaveBalanceList = hrLeaveBalanceList;
//    }
    @XmlTransient
    public List<HrLeaveRequest> getHrLeaveRequestList() {
        return hrLeaveRequestList;
    }

    @XmlTransient
    public List<HrLeaveSetting> getHrLeaveSettingList() {
        return hrLeaveSettingList;
    }

    public void setHrLeaveSettingList(List<HrLeaveSetting> hrLeaveSettingList) {
        this.hrLeaveSettingList = hrLeaveSettingList;
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
        if (!(object instanceof HrLuLeaveTypes)) {
            return false;
        }
        HrLuLeaveTypes other = (HrLuLeaveTypes) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return leaveName;
    }

}
