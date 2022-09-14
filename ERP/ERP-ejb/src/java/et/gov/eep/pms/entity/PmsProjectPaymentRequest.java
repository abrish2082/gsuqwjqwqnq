/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.pms.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Me
 */
@Entity
@Table(name = "PMS_PROJECT_PAYMENT_REQUEST")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PmsProjectPaymentRequest.findAll", query = "SELECT p FROM PmsProjectPaymentRequest p"),
    @NamedQuery(name = "PmsProjectPaymentRequest.findByProjectPaymentId", query = "SELECT p FROM PmsProjectPaymentRequest p WHERE p.projectPaymentId = :projectPaymentId"),
    @NamedQuery(name = "PmsProjectPaymentRequest.findByPayTo", query = "SELECT p FROM PmsProjectPaymentRequest p WHERE p.payTo = :payTo"),
    @NamedQuery(name = "PmsProjectPaymentRequest.findByPlaceOfPayment", query = "SELECT p FROM PmsProjectPaymentRequest p WHERE p.placeOfPayment = :placeOfPayment"),
    @NamedQuery(name = "PmsProjectPaymentRequest.findByDateOfPayment", query = "SELECT p FROM PmsProjectPaymentRequest p WHERE p.dateOfPayment = :dateOfPayment"),
    @NamedQuery(name = "PmsProjectPaymentRequest.findByPaymentNo", query = "SELECT p FROM PmsProjectPaymentRequest p WHERE p.paymentNo = :paymentNo"),
    @NamedQuery(name = "PmsProjectPaymentRequest.findByAccountNo", query = "SELECT p FROM PmsProjectPaymentRequest p WHERE p.accountNo = :accountNo"),
    @NamedQuery(name = "PmsProjectPaymentRequest.findByJobNo", query = "SELECT p FROM PmsProjectPaymentRequest p WHERE p.jobNo = :jobNo"),
    @NamedQuery(name = "PmsProjectPaymentRequest.findByAmount", query = "SELECT p FROM PmsProjectPaymentRequest p WHERE p.amount = :amount"),
    @NamedQuery(name = "PmsProjectPaymentRequest.findByAmountInWord", query = "SELECT p FROM PmsProjectPaymentRequest p WHERE p.amountInWord = :amountInWord"),
    @NamedQuery(name = "PmsProjectPaymentRequest.findByPurpose", query = "SELECT p FROM PmsProjectPaymentRequest p WHERE p.purpose = :purpose"),
    @NamedQuery(name = "PmsProjectPaymentRequest.findByWorkAuthoCheckNo", query = "SELECT p FROM PmsProjectPaymentRequest p WHERE p.workAuthoCheckNo = :workAuthoCheckNo"),
    @NamedQuery(name = "PmsProjectPaymentRequest.findByWorkAuthoDate", query = "SELECT p FROM PmsProjectPaymentRequest p WHERE p.workAuthoDate = :workAuthoDate"),
    @NamedQuery(name = "PmsProjectPaymentRequest.findByPaymentDue", query = "SELECT p FROM PmsProjectPaymentRequest p WHERE p.paymentDue = :paymentDue")})
//    @NamedQuery(name = "PmsProjectPaymentRequest.findByMaterialOrserviece", query = "SELECT p FROM PmsProjectPaymentRequest p WHERE p.materialOrserviece = :materialOrserviece")})
public class PmsProjectPaymentRequest implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "PROJECT_PAYMENT_ID")
    private BigDecimal projectPaymentId;
    @Size(max = 400)
    @Column(name = "PAY_TO")
    private String payTo;
    @Size(max = 400)
    @Column(name = "PLACE_OF_PAYMENT")
    private String placeOfPayment;
    @Column(name = "DATE_OF_PAYMENT")
    @Temporal(TemporalType.DATE)
    private Date dateOfPayment;
    @Size(max = 20)
    @Column(name = "PAYMENT_NO")
    private String paymentNo;
    @Size(max = 100)
    @Column(name = "ACCOUNT_NO")
    private String accountNo;
    @Size(max = 100)
    @Column(name = "JOB_NO")
    private String jobNo;
    @Column(name = "AMOUNT")
    private BigInteger amount;
    @Size(max = 400)
    @Column(name = "AMOUNT_IN_WORD")
    private String amountInWord;
    @Size(max = 400)
    @Column(name = "PURPOSE")
    private String purpose;
    @Size(max = 200)
    @Column(name = "WORK_AUTHO_CHECK_NO")
    private String workAuthoCheckNo;
    @Column(name = "WORK_AUTHO_DATE")
    @Temporal(TemporalType.DATE)
    private Date workAuthoDate;
    @Size(max = 400)
    @Column(name = "PAYMENT_DUE")
    private String paymentDue;
//    @Size(max = 400)
//    @Column(name = "MATERIAL_ORSERVIECE")
//    private String materialOrserviece;
    @JoinColumn(name = "PROJECT_ID", referencedColumnName = "PROJECT_ID")
    @ManyToOne
    private PmsCreateProjects projectId;
    @JoinColumn(name = "WORK_AUTHO_ID", referencedColumnName = "WORK_AUTHO_ID")
    @ManyToOne
    private PmsWorkAuthorization workAuthoId;


    public PmsProjectPaymentRequest() {
    }

    public PmsProjectPaymentRequest(BigDecimal projectPaymentId) {
        this.projectPaymentId = projectPaymentId;
    }

    public BigDecimal getProjectPaymentId() {
        return projectPaymentId;
    }

    public void setProjectPaymentId(BigDecimal projectPaymentId) {
        this.projectPaymentId = projectPaymentId;
    }

    public String getPayTo() {
        return payTo;
    }

    public void setPayTo(String payTo) {
        this.payTo = payTo;
    }

    public String getPlaceOfPayment() {
        return placeOfPayment;
    }

    public void setPlaceOfPayment(String placeOfPayment) {
        this.placeOfPayment = placeOfPayment;
    }

    public Date getDateOfPayment() {
        return dateOfPayment;
    }

    public void setDateOfPayment(Date dateOfPayment) {
        this.dateOfPayment = dateOfPayment;
    }

    public String getPaymentNo() {
        return paymentNo;
    }

    public void setPaymentNo(String paymentNo) {
        this.paymentNo = paymentNo;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getJobNo() {
        return jobNo;
    }

    public void setJobNo(String jobNo) {
        this.jobNo = jobNo;
    }

    public BigInteger getAmount() {
        return amount;
    }

    public void setAmount(BigInteger amount) {
        this.amount = amount;
    }

    public String getAmountInWord() {
        return amountInWord;
    }

    public void setAmountInWord(String amountInWord) {
        this.amountInWord = amountInWord;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getWorkAuthoCheckNo() {
        return workAuthoCheckNo;
    }

    public void setWorkAuthoCheckNo(String workAuthoCheckNo) {
        this.workAuthoCheckNo = workAuthoCheckNo;
    }

    public Date getWorkAuthoDate() {
        return workAuthoDate;
    }

    public void setWorkAuthoDate(Date workAuthoDate) {
        this.workAuthoDate = workAuthoDate;
    }

    public String getPaymentDue() {
        return paymentDue;
    }

    public void setPaymentDue(String paymentDue) {
        this.paymentDue = paymentDue;
    }

//    public String getMaterialOrserviece() {
//        return materialOrserviece;
//    }
//
//    public void setMaterialOrserviece(String materialOrserviece) {
//        this.materialOrserviece = materialOrserviece;
//    }

    public PmsCreateProjects getProjectId() {
        return projectId;
    }

    public void setProjectId(PmsCreateProjects projectId) {
        this.projectId = projectId;
    }

    public PmsWorkAuthorization getWorkAuthoId() {
        return workAuthoId;
    }

    public void setWorkAuthoId(PmsWorkAuthorization workAuthoId) {
        this.workAuthoId = workAuthoId;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (projectPaymentId != null ? projectPaymentId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PmsProjectPaymentRequest)) {
            return false;
        }
        PmsProjectPaymentRequest other = (PmsProjectPaymentRequest) object;
        if ((this.projectPaymentId == null && other.projectPaymentId != null) || (this.projectPaymentId != null && !this.projectPaymentId.equals(other.projectPaymentId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "et.gov.eep.pms.entity.PmsProjectPaymentRequest[ projectPaymentId=" + projectPaymentId + " ]";
    }
    
}
