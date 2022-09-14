/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.entity.training;

import java.io.Serializable;
import java.math.BigDecimal;
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
@Table(name = "HR_TD_ISP_PAYMENT_DETAILS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HrTdIspPaymentDetails.findAll", query = "SELECT h FROM HrTdIspPaymentDetails h"),
    @NamedQuery(name = "HrTdIspPaymentDetails.findById", query = "SELECT h FROM HrTdIspPaymentDetails h WHERE h.id = :id"),
    @NamedQuery(name = "HrTdIspPaymentDetails.findByAmountPaid", query = "SELECT h FROM HrTdIspPaymentDetails h WHERE h.amountPaid = :amountPaid"),
    @NamedQuery(name = "HrTdIspPaymentDetails.findByPaymentStatus", query = "SELECT h FROM HrTdIspPaymentDetails h WHERE h.paymentStatus = :paymentStatus"),
    @NamedQuery(name = "HrTdIspPaymentDetails.findByReferenceNo", query = "SELECT h FROM HrTdIspPaymentDetails h WHERE h.referenceNo = :referenceNo"),
    @NamedQuery(name = "HrTdIspPaymentDetails.findByPaymentDate", query = "SELECT h FROM HrTdIspPaymentDetails h WHERE h.paymentDate = :paymentDate")})
public class HrTdIspPaymentDetails implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HR_TD_ISP_PAYMENT_DETAILS_SEQ")
    @SequenceGenerator(name = "HR_TD_ISP_PAYMENT_DETAILS_SEQ", sequenceName = "HR_TD_ISP_PAYMENT_DETAILS_SEQ", allocationSize = 1)
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private BigDecimal id;
    @Column(name = "AMOUNT_PAID")
    private BigDecimal amountPaid;
    @Column(name = "PAYMENT_STATUS")
    private Integer paymentStatus;
    @Size(max = 20)
    @Column(name = "REFERNCE_NO")
    private String referenceNo;
    @Size(max = 20)
    @Column(name = "PAYMENT_DATE")
    private String paymentDate;
    @JoinColumn(name = "PAYEMENT_ID", referencedColumnName = "ID")
    @ManyToOne
    private HrTdIspPayments payementId;
    @JoinColumn(name = "STUDENT_ID", referencedColumnName = "ID")
    @ManyToOne
    private HrTdIspStudentDetails studentId;

    public HrTdIspPaymentDetails() {
    }

    public HrTdIspPaymentDetails(BigDecimal id) {
        this.id = id;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public BigDecimal getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(BigDecimal amountPaid) {
        this.amountPaid = amountPaid;
    }

    public Integer getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(Integer paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getReferenceNo() {
        return referenceNo;
    }

    public void setReferenceNo(String referenceNo) {
        this.referenceNo = referenceNo;
    }

    public String getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }

    public HrTdIspPayments getPayementId() {
        return payementId;
    }

    public void setPayementId(HrTdIspPayments payementId) {
        this.payementId = payementId;
    }

    public HrTdIspStudentDetails getStudentId() {
        return studentId;
    }

    public void setStudentId(HrTdIspStudentDetails studentId) {
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
        if (!(object instanceof HrTdIspPaymentDetails)) {
            return false;
        }
        HrTdIspPaymentDetails other = (HrTdIspPaymentDetails) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return referenceNo;
    }

}
