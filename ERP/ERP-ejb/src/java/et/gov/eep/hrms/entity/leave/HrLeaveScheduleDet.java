/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.entity.leave;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Desu
 */
@Entity
@Table(name = "HR_LEAVE_SCHEDULE_DET")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HrLeaveScheduleDet.findAll", query = "SELECT h FROM HrLeaveScheduleDet h"),
    @NamedQuery(name = "HrLeaveScheduleDet.findById", query = "SELECT h FROM HrLeaveScheduleDet h WHERE h.id = :id"),
    @NamedQuery(name = "HrLeaveScheduleDet.findByLeaveMonth", query = "SELECT h FROM HrLeaveScheduleDet h WHERE h.leaveMonth = :leaveMonth"),
    @NamedQuery(name = "HrLeaveScheduleDet.findByDescription", query = "SELECT h FROM HrLeaveScheduleDet h WHERE h.description = :description")})
public class HrLeaveScheduleDet implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HR_LEAVE_SCHEDULE_DET_SEQ")
    @SequenceGenerator(name = "HR_LEAVE_SCHEDULE_DET_SEQ", sequenceName = "HR_LEAVE_SCHEDULE_DET_SEQ", allocationSize = 1)
    private Integer id;

    @Size(max = 20)
    @Column(name = "LEAVE_MONTH")
    private String leaveMonth;

    @Size(max = 200)
    @Column(name = "DESCRIPTION")
    private String description;


    @JoinColumn(name = "SCHEDULE_ID", referencedColumnName = "ID")
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private HrLeaveSchedule scheduleId;

    public HrLeaveScheduleDet() {
    }

    public HrLeaveScheduleDet(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLeaveMonth() {
        return leaveMonth;
    }

    public void setLeaveMonth(String leaveMonth) {
        this.leaveMonth = leaveMonth;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public HrLeaveSchedule getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(HrLeaveSchedule scheduleId) {
        this.scheduleId = scheduleId;
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
        if (!(object instanceof HrLeaveScheduleDet)) {
            return false;
        }
        HrLeaveScheduleDet other = (HrLeaveScheduleDet) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.HrLeaveScheduleDet[ id=" + id + " ]";
    }

}
