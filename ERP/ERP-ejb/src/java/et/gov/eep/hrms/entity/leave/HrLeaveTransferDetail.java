/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.entity.leave;

import et.gov.eep.hrms.entity.employee.HrEmployees;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author user
 */
@Entity
@Table(name = "HR_LEAVE_TRANSFER_DETAIL")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HrLeaveTransferDetail.findAll", query = "SELECT h FROM HrLeaveTransferDetail h"),
    @NamedQuery(name = "HrLeaveTransferDetail.findByDetialId", query = "SELECT h FROM HrLeaveTransferDetail h WHERE h.detialId = :detialId"),
    
    @NamedQuery(name = "HrLeaveTransferDetail.findByParentId", query = "SELECT h FROM HrLeaveTransferDetail h WHERE h.requestMasterId = :parentID"),
    @NamedQuery(name = "HrLeaveTransferDetail.findByStatus", query = "SELECT h FROM HrLeaveTransferDetail h WHERE h.status = :status")})
public class HrLeaveTransferDetail implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @NotNull
    @Column(name = "DETIAL_ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HR_LEAVE_TRANSFER_DETAIL_SEQ")
    @SequenceGenerator(name = "HR_LEAVE_TRANSFER_DETAIL_SEQ", sequenceName = "HR_LEAVE_TRANSFER_DETAIL_SEQ", allocationSize = 1)
    private BigDecimal detialId;

    @Column(name = "STATUS")
    private boolean status;

    @JoinColumn(name = "EMP_ID", referencedColumnName = "ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private HrEmployees empId;

    @JoinColumn(name = "REQUEST_MASTER_ID", referencedColumnName = "ID")
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private HrLeaveTransfer requestMasterId;

    public HrLeaveTransferDetail() {
    }

    public HrLeaveTransferDetail(BigDecimal detialId) {
        this.detialId = detialId;
    }

    public BigDecimal getDetialId() {
        return detialId;
    }

    public void setDetialId(BigDecimal detialId) {
        this.detialId = detialId;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public HrEmployees getEmpId() {
        return empId;
    }

    public void setEmpId(HrEmployees empId) {
        this.empId = empId;
    }

    public HrLeaveTransfer getRequestMasterId() {
        return requestMasterId;
    }

    public void setRequestMasterId(HrLeaveTransfer requestMasterId) {
        this.requestMasterId = requestMasterId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (detialId != null ? detialId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HrLeaveTransferDetail)) {
            return false;
        }
        HrLeaveTransferDetail other = (HrLeaveTransferDetail) object;
        if ((this.detialId == null && other.detialId != null) || (this.detialId != null && !this.detialId.equals(other.detialId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return empId.getEmpId();
    }

}
