/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.entity;

import et.gov.eep.fcms.entity.Vocher.FmsChequePaymentVoucher;
import et.gov.eep.pms.entity.PmsWorkAuthorization;
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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author user
 */
@Entity
@Table(name = "FMS_PETTY_CHEQ_PAYM_CONTROL")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FmsPettyCheqPaymControl.findAll", query = "SELECT f FROM FmsPettyCheqPaymControl f"),
    @NamedQuery(name = "FmsPettyCheqPaymControl.findByControlId", query = "SELECT f FROM FmsPettyCheqPaymControl f WHERE f.controlId = :controlId"),
//    @NamedQuery(name = "FmsPettyCheqPaymControl.findByCapitalBgtControlList", query = "SELECT f FROM FmsPettyCheqPaymControl f inner join f.capitalBgtId c"),
//    @NamedQuery(name = "FmsPettyCheqPaymControl.findByOperatingBgtControlList", query = "SELECT f FROM FmsPettyCheqPaymControl f inner join f.operatingBgtId op"),
    @NamedQuery(name = "FmsPettyCheqPaymControl.findByBalance", query = "SELECT f FROM FmsPettyCheqPaymControl f WHERE f.balance = :balance"),
    @NamedQuery(name = "FmsPettyCheqPaymControl.findByPettyChequeId", query = "SELECT f FROM FmsPettyCheqPaymControl f WHERE f.pettyChequeId = :pettyChequeId")})
public class FmsPettyCheqPaymControl implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    
    @Column(name = "CONTROL_ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BUDGET_CONTROL")
    @SequenceGenerator(name = "BUDGET_CONTROL", sequenceName = "BUDGET_CONTROL_ID_SEQ", allocationSize = 1)
    private Integer controlId;
    @Column(name = "BALANCE")
    private Double balance;
    @JoinColumn(name = "PETTY_CHEQUE_ID", referencedColumnName = "PETTY_CASH_PAYMENT_VOUCHER_ID")
    @ManyToOne
    private FmsPettyCashPaymentVoucher pettyChequeId;      
    @JoinColumn(name = "CHEQUE_PAYMENT_VOUCHER_ID", referencedColumnName = "CHEQUE_PAYMENT_VOUCHER_ID")
    @ManyToOne
    private FmsChequePaymentVoucher chequePaymentVoucherId;    
    @JoinColumn(name = "WORK_AUTHORIZATION_ID", referencedColumnName = "WORK_AUTHO_ID")
    @ManyToOne
    private PmsWorkAuthorization workAuthorizationId;

    public PmsWorkAuthorization getWorkAuthorizationId() {
        return workAuthorizationId;
    }

    public void setWorkAuthorizationId(PmsWorkAuthorization workAuthorizationId) {
        this.workAuthorizationId = workAuthorizationId;
    }
    /**
     *
     * @return
     */
    public FmsPettyCashPaymentVoucher getPettyChequeId() {
        return pettyChequeId;
    }

    /**
     *
     * @param pettyChequeId
     */
    public void setPettyChequeId(FmsPettyCashPaymentVoucher pettyChequeId) {
        this.pettyChequeId = pettyChequeId;
    }

    /**
     *
     */
    public FmsPettyCheqPaymControl() {
    }

    /**
     *
     * @param controlId
     */
    public FmsPettyCheqPaymControl(Integer controlId) {
        this.controlId = controlId;
    }

    /**
     *
     * @return
     */
    public Integer getControlId() {
        return controlId;
    }

    /**
     *
     * @param controlId
     */
    public void setControlId(Integer controlId) {
        this.controlId = controlId;
    }

    /**
     *
     * @return
     */
    public Double getBalance() {
        return balance;
    }

    /**
     *
     * @param balance
     */
    public void setBalance(Double balance) {
        this.balance = balance;
    }


    /**
     *
     * @return
     */
    public FmsChequePaymentVoucher getChequePaymentVoucherId() {
        return chequePaymentVoucherId;
    }

    /**
     *
     * @param chequePaymentVoucherId
     */
    public void setChequePaymentVoucherId(FmsChequePaymentVoucher chequePaymentVoucherId) {
        this.chequePaymentVoucherId = chequePaymentVoucherId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (controlId != null ? controlId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FmsPettyCheqPaymControl)) {
            return false;
        }
        FmsPettyCheqPaymControl other = (FmsPettyCheqPaymControl) object;
        if ((this.controlId == null && other.controlId != null) || (this.controlId != null && !this.controlId.equals(other.controlId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "et.gov.eep.fcms.entity.FmsPettyCheqPaymControl[ controlId=" + controlId + " ]";
    }

}
