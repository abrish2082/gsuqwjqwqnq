/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.entity.insurance;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import javax.persistence.Basic;
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
 * @author meles
 */
@Entity
@Table(name = "HR_INSURANCE_PAYMENT_DETAIL")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HrInsurancePaymentDetail.findAll", query = "SELECT h FROM HrInsurancePaymentDetail h"),
    @NamedQuery(name = "HrInsurancePaymentDetail.findById", query = "SELECT h FROM HrInsurancePaymentDetail h WHERE h.id = :id"),
    @NamedQuery(name = "HrInsurancePaymentDetail.findByMaximumId", query = "SELECT h FROM HrInsurancePaymentDetail h WHERE h.referenceNo = (SELECT Max(p.referenceNo) FROM HrInsurancePaymentDetail p)"),
    @NamedQuery(name = "HrInsurancePaymentDetail.findByMedicalExpence", query = "SELECT h FROM HrInsurancePaymentDetail h WHERE h.medicalExpence = :medicalExpence"),
    @NamedQuery(name = "HrInsurancePaymentDetail.findByMaximumId", query = "SELECT h FROM HrInsurancePaymentDetail h WHERE h.referenceNo = (SELECT Max(p.referenceNo) FROM HrInsurancePaymentDetail p)"),
    @NamedQuery(name = "HrInsurancePaymentDetail.findByReferenceNo", query = "SELECT h FROM HrInsurancePaymentDetail h WHERE h.referenceNo = :referenceNo"),
    @NamedQuery(name = "HrInsurancePaymentDetail.findByRecieptNo", query = "SELECT h FROM HrInsurancePaymentDetail h WHERE h.recieptNo = :recieptNo")})
public class HrInsurancePaymentDetail implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HR_INSUR_PAYMENT_DETAIL_SEQ")
    @SequenceGenerator(name = "HR_INSUR_PAYMENT_DETAIL_SEQ", sequenceName = "HR_INSUR_PAYMENT_DETAIL_SEQ", allocationSize = 1)
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private Integer id;
    @Size(max = 20)
    @Column(name = "REFERENCE_NO")
    private String referenceNo;
    @Size(max = 20)
    @Column(name = "ACCIDENT_HAPPEND")
    private String accidenthappend;
    @Column(name = "MEDICAL_EXPENCE")
    private BigDecimal medicalExpence;
    @Column(name = "RECIEPT_NO")
    private BigInteger recieptNo;
    @JoinColumn(name = "PAYMENT_REQUEST_ID", referencedColumnName = "ID")
    @ManyToOne
    private HrInsurancePayment paymentRequestId;

    public HrInsurancePaymentDetail() {
    }

    public HrInsurancePaymentDetail(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getMedicalExpence() {
        return medicalExpence;
    }

    public void setMedicalExpence(BigDecimal medicalExpence) {
        this.medicalExpence = medicalExpence;
    }

    public BigInteger getRecieptNo() {
        return recieptNo;
    }

    public void setRecieptNo(BigInteger recieptNo) {
        this.recieptNo = recieptNo;
    }

    public HrInsurancePayment getPaymentRequestId() {
        return paymentRequestId;
    }

    public void setPaymentRequestId(HrInsurancePayment paymentRequestId) {
        this.paymentRequestId = paymentRequestId;
    }

    public String getAccidenthappend() {
        return accidenthappend;
    }

    public void setAccidenthappend(String accidenthappend) {
        this.accidenthappend = accidenthappend;
    }

    public String getReferenceNo() {
        return referenceNo;
    }

    public void setReferenceNo(String referenceNo) {
        this.referenceNo = referenceNo;
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
        if (!(object instanceof HrInsurancePaymentDetail)) {
            return false;
        }
        HrInsurancePaymentDetail other = (HrInsurancePaymentDetail) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "et.gov.eep.hrms.entity.insurance.HrInsurancePaymentDetail[ id=" + id + " ]";
    }

}
