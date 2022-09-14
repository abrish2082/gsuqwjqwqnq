/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.entity.loan;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
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
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Binyam
 */
@Entity
@Table(name = "FMS_LOAN_COMMITMENT")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FmsLoanCommitment.findAll", query = "SELECT f FROM FmsLoanCommitment f"),
    @NamedQuery(name = "FmsLoanCommitment.findByCommitmentId", query = "SELECT f FROM FmsLoanCommitment f WHERE f.commitmentId = :commitmentId"),
    @NamedQuery(name = "FmsLoanCommitment.findByLoanId", query = "SELECT f FROM FmsLoanCommitment f WHERE f.loanId = :loanId ORDER BY f.commitmentId"),
    @NamedQuery(name = "FmsLoanCommitment.findByPaymentDate", query = "SELECT f FROM FmsLoanCommitment f WHERE f.paymentDate = :paymentDate"),
    @NamedQuery(name = "FmsLoanCommitment.findByAmount", query = "SELECT f FROM FmsLoanCommitment f WHERE f.amount = :amount"),
    @NamedQuery(name = "FmsLoanCommitment.findByStatus", query = "SELECT f FROM FmsLoanCommitment f WHERE f.status = :status")})
public class FmsLoanCommitment implements Serializable {
    @Column(name = "AMOUNT")
    private BigDecimal amount;
    @OneToMany(mappedBy = "loanCommFk")
    private List<FmsLoanCommAccounts> fmsLoanCommAccountsList;
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "COMMITMENT_ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FMS_SEQ_LOAN_COMMITMENT")
    @SequenceGenerator(schema="ERP", name = "FMS_SEQ_LOAN_COMMITMENT", sequenceName = "FMS_SEQ_LOAN_COMMITMENT", allocationSize = 1)
    private Integer commitmentId;
    @Column(name = "PAYMENT_DATE")
    @Temporal(TemporalType.DATE)
    private Date paymentDate;
    @Column(name = "STATUS")
    private String status;
    @JoinColumn(name = "LOAN_ID", referencedColumnName = "LOAN_ID")
    @ManyToOne
    private FmsLoan loanId;

    /**
     *
     */
    public FmsLoanCommitment() {
    }

    /**
     *
     * @param commitmentId
     */
    public FmsLoanCommitment(Integer commitmentId) {
        this.commitmentId = commitmentId;
    }

    /**
     *
     * @return
     */
    public Integer getCommitmentId() {
        return commitmentId;
    }

    /**
     *
     * @param commitmentId
     */
    public void setCommitmentId(Integer commitmentId) {
        this.commitmentId = commitmentId;
    }

    /**
     *
     * @return
     */
    public Date getPaymentDate() {
        return paymentDate;
    }

    /**
     *
     * @param paymentDate
     */
    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }


    /**
     *
     * @return
     */
    public String getStatus() {
        return status;
    }

    /**
     *
     * @param status
     */
    public void setStatus(String status) {
        this.status = status;
    }
    
    /**
     *
     * @return
     */
    public FmsLoan getLoanId() {
        return loanId;
    }

    /**
     *
     * @param loanId
     */
    public void setLoanId(FmsLoan loanId) {
        this.loanId = loanId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (commitmentId != null ? commitmentId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FmsLoanCommitment)) {
            return false;
        }
        FmsLoanCommitment other = (FmsLoanCommitment) object;
        if ((this.commitmentId == null && other.commitmentId != null) || (this.commitmentId != null && !this.commitmentId.equals(other.commitmentId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "et.gov.eep.fcms.entity.FmsLoanCommitment[ commitmentId=" + commitmentId + " ]";
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    @XmlTransient
    public List<FmsLoanCommAccounts> getFmsLoanCommAccountsList() {
        return fmsLoanCommAccountsList;
    }

    public void setFmsLoanCommAccountsList(List<FmsLoanCommAccounts> fmsLoanCommAccountsList) {
        this.fmsLoanCommAccountsList = fmsLoanCommAccountsList;
    }
    
}
