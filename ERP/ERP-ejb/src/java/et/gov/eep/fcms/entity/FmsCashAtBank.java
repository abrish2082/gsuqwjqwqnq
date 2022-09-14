/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.entity;

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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author AB
 */
@Entity
@Table(name = "fms_cash_at_bank")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FmsCashAtBank.findAll", query = "SELECT f FROM FmsCashAtBank f"),
    @NamedQuery(name = "FmsCashAtBank.findByBankId", query = "SELECT f FROM FmsCashAtBank f WHERE f.bankId = :bankId"),
    @NamedQuery(name = "FmsCashAtBank.findByBankAccountNo", query = "SELECT f FROM FmsCashAtBank f WHERE f.bankAccountNo = :bankAccountNo")})
public class FmsCashAtBank implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
   @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FMS_CASH_AT_BANK_BANK_ID_SEQ")
    @SequenceGenerator(name = "FMS_CASH_AT_BANK_BANK_ID_SEQ", sequenceName = "FMS_CASH_AT_BANK_BANK_ID_SEQ", allocationSize = 1)
    @Basic(optional = false)
    @Column(name = "BANK_ID")
    private Integer bankId;
    @Size(max = 20)
    @Column(name = "BANK_ACCOUNT_NO")
    private String bankAccountNo;
//    @JoinColumn(name = "CHART_OF_ACCT_ID", referencedColumnName = "CHART_OF_ACCT_ID")
//    @ManyToOne
//    private FmsChartOfAccount chartOfAcctId;

    /**
     *
     */
    public FmsCashAtBank() {
    }

    /**
     *
     * @param bankId
     */
    public FmsCashAtBank(Integer bankId) {
        this.bankId = bankId;
    }

    /**
     *
     * @return
     */
    public Integer getBankId() {
        return bankId;
    }

    /**
     *
     * @param bankId
     */
    public void setBankId(Integer bankId) {
        this.bankId = bankId;
    }

    /**
     *
     * @return
     */
    public String getBankAccountNo() {
        return bankAccountNo;
    }

    /**
     *
     * @param bankAccountNo
     */
    public void setBankAccountNo(String bankAccountNo) {
        this.bankAccountNo = bankAccountNo;
    }

//    public FmsChartOfAccount getChartOfAcctId() {
//        return chartOfAcctId;
//    }
//
//    public void setChartOfAcctId(FmsChartOfAccount chartOfAcctId) {
//        this.chartOfAcctId = chartOfAcctId;
//    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (bankId != null ? bankId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FmsCashAtBank)) {
            return false;
        }
        FmsCashAtBank other = (FmsCashAtBank) object;
        if ((this.bankId == null && other.bankId != null) || (this.bankId != null && !this.bankId.equals(other.bankId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "et.gov.insa.erp.ibfms.entity.FmsCashAtBank[ bankId=" + bankId + " ]";
    }
    
}
