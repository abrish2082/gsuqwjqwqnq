/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.pms.entity;

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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Me
 */
@Entity
@Table(name = "PMS_STATUS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PmsStatus.findAll", query = "SELECT p FROM PmsStatus p"),
    @NamedQuery(name = "PmsStatus.findByStatusId", query = "SELECT p FROM PmsStatus p WHERE p.statusId = :statusId"),
    @NamedQuery(name = "PmsStatus.findByStatus", query = "SELECT p FROM PmsStatus p WHERE p.status = :status")
//    @NamedQuery(name = "PmsStatus.findByOverAllPerformanceIn", query = "SELECT p FROM PmsStatus p WHERE p.overAllPerformanceIn = :overAllPerformanceIn"),
//    @NamedQuery(name = "PmsStatus.findByDescription", query = "SELECT p FROM PmsStatus p WHERE p.description = :description"),
//    @NamedQuery(name = "PmsStatus.findByCheckedBy", query = "SELECT p FROM PmsStatus p WHERE p.checkedBy = :checkedBy"),
//    @NamedQuery(name = "PmsStatus.findByApprovedBy", query = "SELECT p FROM PmsStatus p WHERE p.approvedBy = :approvedBy"),
//    @NamedQuery(name = "PmsStatus.findByRemark", query = "SELECT p FROM PmsStatus p WHERE p.remark = :remark"
})
public class PmsStatus implements Serializable {
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "PERFORMANCE_IN_PERCENT", precision = 126)
    private Double performanceInPercent;
//    @Column(name = "over_all_performance_in_%")
//    private Serializable overAllPerformanceIn;
//    @Column(name = "over_all_performance_in_%")
//    private Serializable overAllPerformanceIn;
//    @Column(name = "over_all_performance_in_%")
//    private Serializable overAllPerformanceIn;

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "STATUS_ID")
    private Integer statusId;
    @Column(name = "STATUS")
    private String status;
//    @Column(name = "over_all_performance_in_%")
//    private Serializable overAllPerformanceIn;
//    @Column(name = "DESCRIPTION")
//    private String description;
//    @Column(name = "CHECKED_BY")
//    private String checkedBy;
//    @Column(name = "APPROVED_BY")
//    private String approvedBy;
//    @Column(name = "REMARK")
//    private String remark;
    @OneToMany(mappedBy = "statusId")
    private List<PmsCreateProjects> pmsCreateProjectsList;

    public PmsStatus() {
    }

    public PmsStatus(Integer statusId) {
        this.statusId = statusId;
    }

    public Integer getStatusId() {
        return statusId;
    }

    public void setStatusId(Integer statusId) {
        this.statusId = statusId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

//    public Serializable getOverAllPerformanceIn() {
//        return overAllPerformanceIn;
//    }
//
//    public void setOverAllPerformanceIn(Serializable overAllPerformanceIn) {
//        this.overAllPerformanceIn = overAllPerformanceIn;
//    }
//    public String getDescription() {
//        return description;
//    }
//
//    public void setDescription(String description) {
//        this.description = description;
//    }
//
//    public String getCheckedBy() {
//        return checkedBy;
//    }
//
//    public void setCheckedBy(String checkedBy) {
//        this.checkedBy = checkedBy;
//    }
//
//    public String getApprovedBy() {
//        return approvedBy;
//    }
//
//    public void setApprovedBy(String approvedBy) {
//        this.approvedBy = approvedBy;
//    }
//    public String getDescription() {
//        return description;
//    }
//
//    public void setDescription(String description) {
//        this.description = description;
//    }
//
//    public String getCheckedBy() {
//        return checkedBy;
//    }
//
//    public void setCheckedBy(String checkedBy) {
//        this.checkedBy = checkedBy;
//    }
//
//    public String getApprovedBy() {
//        return approvedBy;
//    }
//
//    public void setApprovedBy(String approvedBy) {
//        this.approvedBy = approvedBy;
//    }
//
//    public String getRemark() {
//        return remark;
//    }
//
//    public void setRemark(String remark) {
//        this.remark = remark;
//    }
    @XmlTransient
    public List<PmsCreateProjects> getPmsCreateProjectsList() {
        return pmsCreateProjectsList;
    }

    public void setPmsCreateProjectsList(List<PmsCreateProjects> pmsCreateProjectsList) {
        this.pmsCreateProjectsList = pmsCreateProjectsList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (statusId != null ? statusId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PmsStatus)) {
            return false;
        }
        PmsStatus other = (PmsStatus) object;
        if ((this.statusId == null && other.statusId != null) || (this.statusId != null && !this.statusId.equals(other.statusId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return status;
    }

//    public double getOverAllPerformanceIn() {
//        return overAllPerformanceIn;
//    }
//
//    public void setOverAllPerformanceIn(double overAllPerformanceIn) {
//        this.overAllPerformanceIn = overAllPerformanceIn;
//    }

    public Double getPerformanceInPercent() {
        return performanceInPercent;
    }

    public void setPerformanceInPercent(Double performanceInPercent) {
        this.performanceInPercent = performanceInPercent;
    }

}
