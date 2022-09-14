/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.entity.training;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
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
 * @author insa
 */
@Entity
@Table(name = "HR_TD_ISP_STATUS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HrTdIspStatus.findAll", query = "SELECT h FROM HrTdIspStatus h"),
    @NamedQuery(name = "HrTdIspStatus.findById", query = "SELECT h FROM HrTdIspStatus h WHERE h.id = :id"),
    @NamedQuery(name = "HrTdIspStatus.findByStatus", query = "SELECT h FROM HrTdIspStatus h WHERE h.status = :status")})
public class HrTdIspStatus implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HR_TD_ISP_STATUS_SEQ")
    @SequenceGenerator(name = "HR_TD_ISP_STATUS_SEQ", sequenceName = "HR_TD_ISP_STATUS_SEQ", allocationSize = 1)
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private Integer id;
    @Size(max = 20)
    @Column(name = "STATUS")
    private Number status;
    @JoinColumn(name = "STUDENT_ID", referencedColumnName = "ID")
    @ManyToOne
    private HrTdIspStudents studentId;

    public HrTdIspStatus() {
    }

    public HrTdIspStatus(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Number getStatus() {
        return status;
    }

    public void setStatus(Number status) {
        this.status = status;
    }

    public HrTdIspStudents getStudentId() {
        return studentId;
    }

    public void setStudentId(HrTdIspStudents studentId) {
        this.studentId = studentId;
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
        if (!(object instanceof HrTdIspStatus)) {
            return false;
        }
        HrTdIspStatus other = (HrTdIspStatus) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.insurance.HrTdIspStatus[ id=" + id + " ]";
    }

    public void add(HrTdIspStudentDetails hrTdIspStudentDetails) {
//          if (hrTdIspPaymentDetails.getPayementId() != this) {
//            this.getHrTdIspPaymentDetailsList().add(hrTdIspPaymentDetails);
//            hrTdIspPaymentDetails.setPayementId(this);
    }
}
