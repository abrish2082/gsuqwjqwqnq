/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.entity;

import et.gov.eep.fcms.entity.admin.FmsAccountingPeriod;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author AB
 */
@Entity
@Table(name = "fms_trial_balance" )
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FmsTrialBalance.findAll", query = "SELECT f FROM FmsTrialBalance f"),
    @NamedQuery(name = "FmsTrialBalance.findByTbId", query = "SELECT f FROM FmsTrialBalance f WHERE f.tbId = :tbId"),
    @NamedQuery(name = "FmsTrialBalance.findByAccountCode", query = "SELECT f FROM FmsTrialBalance f WHERE f.accountCode = :accountCode"),
    @NamedQuery(name = "FmsTrialBalance.findByGlCode", query = "SELECT f FROM FmsTrialBalance f WHERE f.glCode = :glCode"),
    @NamedQuery(name = "FmsTrialBalance.findByDebit", query = "SELECT f FROM FmsTrialBalance f WHERE f.debit = :debit"),
    @NamedQuery(name = "FmsTrialBalance.findByCredit", query = "SELECT f FROM FmsTrialBalance f WHERE f.credit = :credit"),
    @NamedQuery(name = "FmsTrialBalance.findByStatus", query = "SELECT f FROM FmsTrialBalance f WHERE f.status = :status"),
    @NamedQuery(name = "FmsTrialBalance.findByTrCode", query = "SELECT f FROM FmsTrialBalance f WHERE f.trCode = :trCode")})
public class FmsTrialBalance implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "TB_ID", nullable = false)
    private Integer tbId;
    @Size(max = 30)
    @Column(name = "ACCOUNT_CODE", length = 30)
    private String accountCode;
    @Size(max = 20)
    @Column(name = "GL_CODE", length = 20)
    private String glCode;
    @Column(name = "DEBIT")
    private double debit;
    @Column(name = "CREDIT")
    private double credit;
    @Column(name = "STATUS")
    private Long status;
    @Column(name = "TR_CODE")
    private Integer trCode;
    @JoinColumn(name = "ACOUNTIG_PERIOD_ID", referencedColumnName = "ACOUNTIG_PERIOD_ID")
    @ManyToOne
    private FmsAccountingPeriod acountigPeriodId;
    @JoinColumn(name = "VOUCHER_CODE", referencedColumnName = "VOUCHER_CODE")
    @ManyToOne
    private FmsVoucherReadyToTb voucherCode;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tbId")
    private List<FmsEndingBalance2> fmsEndingBalance2List;

    /**
     *
     */
    public FmsTrialBalance() {
    }

    /**
     *
     * @param tbId
     */
    public FmsTrialBalance(Integer tbId) {
        this.tbId = tbId;
    }

    /**
     *
     * @return
     */
    public Integer getTbId() {
        return tbId;
    }

    /**
     *
     * @param tbId
     */
    public void setTbId(Integer tbId) {
        this.tbId = tbId;
    }

    /**
     *
     * @return
     */
    public String getAccountCode() {
        return accountCode;
    }

    /**
     *
     * @param accountCode
     */
    public void setAccountCode(String accountCode) {
        this.accountCode = accountCode;
    }

    /**
     *
     * @return
     */
    public String getGlCode() {
        return glCode;
    }

    /**
     *
     * @param glCode
     */
    public void setGlCode(String glCode) {
        this.glCode = glCode;
    }

    /**
     *
     * @return
     */
    public double getDebit() {
        return debit;
    }

    /**
     *
     * @param debit
     */
    public void setDebit(double debit) {
        this.debit = debit;
    }

    /**
     *
     * @return
     */
    public double getCredit() {
        return credit;
    }

    /**
     *
     * @param credit
     */
    public void setCredit(double credit) {
        this.credit = credit;
    }

    /**
     *
     * @return
     */
    public Long getStatus() {
        return status;
    }

    /**
     *
     * @param status
     */
    public void setStatus(Long status) {
        this.status = status;
    }

    /**
     *
     * @return
     */
    public Integer getTrCode() {
        return trCode;
    }

    /**
     *
     * @param trCode
     */
    public void setTrCode(Integer trCode) {
        this.trCode = trCode;
    }

    /**
     *
     * @return
     */
    public FmsAccountingPeriod getAcountigPeriodId() {
        return acountigPeriodId;
    }

    /**
     *
     * @param acountigPeriodId
     */
    public void setAcountigPeriodId(FmsAccountingPeriod acountigPeriodId) {
        this.acountigPeriodId = acountigPeriodId;
    }

    /**
     *
     * @return
     */
    public FmsVoucherReadyToTb getVoucherCode() {
        return voucherCode;
    }

    /**
     *
     * @param voucherCode
     */
    public void setVoucherCode(FmsVoucherReadyToTb voucherCode) {
        this.voucherCode = voucherCode;
    }

    /**
     *
     * @return
     */
    @XmlTransient
    public List<FmsEndingBalance2> getFmsEndingBalance2List() {
        return fmsEndingBalance2List;
    }

    /**
     *
     * @param fmsEndingBalance2List
     */
    public void setFmsEndingBalance2List(List<FmsEndingBalance2> fmsEndingBalance2List) {
        this.fmsEndingBalance2List = fmsEndingBalance2List;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (tbId != null ? tbId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FmsTrialBalance)) {
            return false;
        }
        FmsTrialBalance other = (FmsTrialBalance) object;
        if ((this.tbId == null && other.tbId != null) || (this.tbId != null && !this.tbId.equals(other.tbId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "et.gov.insa.erp.ibfms.entity.FmsTrialBalance[ tbId=" + tbId + " ]";
    }

   
    
}
