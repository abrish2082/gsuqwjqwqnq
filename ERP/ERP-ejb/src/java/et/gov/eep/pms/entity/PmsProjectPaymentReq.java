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
@Table(name = "PMS_PROJECT_PAYMENT_REQ")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PmsProjectPaymentReq.findAll", query = "SELECT p FROM PmsProjectPaymentReq p"),
    @NamedQuery(name = "PmsProjectPaymentReq.findByProjectPaymentId", query = "SELECT p FROM PmsProjectPaymentReq p WHERE p.projectPaymentId = :projectPaymentId"),
    @NamedQuery(name = "PmsProjectPaymentReq.findByApprovalForPaymentId", query = "SELECT p FROM PmsProjectPaymentReq p WHERE p.approvalForPaymentId = :approvalForPaymentId"),
    @NamedQuery(name = "PmsProjectPaymentReq.findByPayTo", query = "SELECT p FROM PmsProjectPaymentReq p WHERE p.payTo = :payTo"),
    @NamedQuery(name = "PmsProjectPaymentReq.findByPlaceOfPayment", query = "SELECT p FROM PmsProjectPaymentReq p WHERE p.placeOfPayment = :placeOfPayment"),
    @NamedQuery(name = "PmsProjectPaymentReq.findByDateOfPayment", query = "SELECT p FROM PmsProjectPaymentReq p WHERE p.dateOfPayment = :dateOfPayment"),
    @NamedQuery(name = "PmsProjectPaymentReq.findByPaymentNo", query = "SELECT p FROM PmsProjectPaymentReq p WHERE p.paymentNo = :paymentNo"),
    @NamedQuery(name = "PmsProjectPaymentReq.findByAccNo", query = "SELECT p FROM PmsProjectPaymentReq p WHERE p.accNo = :accNo"),
    @NamedQuery(name = "PmsProjectPaymentReq.findByJobNo", query = "SELECT p FROM PmsProjectPaymentReq p WHERE p.jobNo = :jobNo"),
    @NamedQuery(name = "PmsProjectPaymentReq.findByAmount", query = "SELECT p FROM PmsProjectPaymentReq p WHERE p.amount = :amount"),
    @NamedQuery(name = "PmsProjectPaymentReq.findByAmountInWord", query = "SELECT p FROM PmsProjectPaymentReq p WHERE p.amountInWord = :amountInWord"),
    @NamedQuery(name = "PmsProjectPaymentReq.findByPurpose", query = "SELECT p FROM PmsProjectPaymentReq p WHERE p.purpose = :purpose"),
    @NamedQuery(name = "PmsProjectPaymentReq.findByWorkAuthoCheckNo", query = "SELECT p FROM PmsProjectPaymentReq p WHERE p.workAuthoCheckNo = :workAuthoCheckNo"),
    @NamedQuery(name = "PmsProjectPaymentReq.findByWorkAuthoDate", query = "SELECT p FROM PmsProjectPaymentReq p WHERE p.workAuthoDate = :workAuthoDate"),
    @NamedQuery(name = "PmsProjectPaymentReq.findByMaterialId", query = "SELECT p FROM PmsProjectPaymentReq p WHERE p.materialId = :materialId")})
public class PmsProjectPaymentReq implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "PROJECT_PAYMENT_ID")
    private BigDecimal projectPaymentId;
    @Column(name = "APPROVAL_FOR_PAYMENT_ID")
    private BigInteger approvalForPaymentId;
    @Size(max = 200)
    @Column(name = "PAY_TO")
    private String payTo;
    @Size(max = 200)
    @Column(name = "PLACE_OF_PAYMENT")
    private String placeOfPayment;
    @Column(name = "DATE_OF_PAYMENT")
    @Temporal(TemporalType.DATE)
    private Date dateOfPayment;
    @Size(max = 40)
    @Column(name = "PAYMENT_NO")
    private String paymentNo;
    @Size(max = 50)
    @Column(name = "ACC_NO")
    private String accNo;
    @Size(max = 200)
    @Column(name = "JOB_NO")
    private String jobNo;
    @Size(max = 40)
    @Column(name = "AMOUNT")
    private String amount;
    @Size(max = 200)
    @Column(name = "AMOUNT_IN_WORD")
    private String amountInWord;
    @Size(max = 200)
    @Column(name = "PURPOSE")
    private String purpose;
    @Size(max = 40)
    @Column(name = "WORK_AUTHO_CHECK_NO")
    private String workAuthoCheckNo;
    @Column(name = "WORK_AUTHO_DATE")
    @Temporal(TemporalType.DATE)
    private Date workAuthoDate;
    @Column(name = "MATERIAL_ID")
    private BigInteger materialId;
    @JoinColumn(name = "PROJECT_ID", referencedColumnName = "PROJECT_ID")
    @ManyToOne
    private PmsCreateProjects projectId;
    @JoinColumn(name = "WORK_AUTHO_ID", referencedColumnName = "WORK_AUTHO_ID")
    @ManyToOne
    private PmsWorkAuthorization workAuthoId;

    public PmsProjectPaymentReq() {
    }

    public PmsProjectPaymentReq(BigDecimal projectPaymentId) {
        this.projectPaymentId = projectPaymentId;
    }

    public BigDecimal getProjectPaymentId() {
        return projectPaymentId;
    }

    public void setProjectPaymentId(BigDecimal projectPaymentId) {
        this.projectPaymentId = projectPaymentId;
    }

    public BigInteger getApprovalForPaymentId() {
        return approvalForPaymentId;
    }

    public void setApprovalForPaymentId(BigInteger approvalForPaymentId) {
        this.approvalForPaymentId = approvalForPaymentId;
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

    public String getAccNo() {
        return accNo;
    }

    public void setAccNo(String accNo) {
        this.accNo = accNo;
    }

    public String getJobNo() {
        return jobNo;
    }

    public void setJobNo(String jobNo) {
        this.jobNo = jobNo;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
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

    public BigInteger getMaterialId() {
        return materialId;
    }

    public void setMaterialId(BigInteger materialId) {
        this.materialId = materialId;
    }

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
        if (!(object instanceof PmsProjectPaymentReq)) {
            return false;
        }
        PmsProjectPaymentReq other = (PmsProjectPaymentReq) object;
        if ((this.projectPaymentId == null && other.projectPaymentId != null) || (this.projectPaymentId != null && !this.projectPaymentId.equals(other.projectPaymentId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "et.gov.eep.pms.entity.PmsProjectPaymentReq[ projectPaymentId=" + projectPaymentId + " ]";
    }
    
}
