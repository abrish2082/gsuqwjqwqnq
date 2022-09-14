/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.entity.bank;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import et.gov.eep.prms.entity.PrmsPaymentRequisition;
import javax.persistence.Transient;

/**
 *
 * @author mubejbl
 */
@Entity
@Table(name = "FMS_BANK")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FmsBank.findAll", query = "SELECT f FROM FmsBank f"),
    @NamedQuery(name = "FmsBank.findByBankNameLike", query = "SELECT f FROM FmsBank f WHERE UPPER (f.bankName) LIKE :bankName"),
    @NamedQuery(name = "FmsBank.findByBankCode", query = "SELECT f FROM FmsBank f WHERE f.bankCode = :bankCode"),
    @NamedQuery(name = "FmsBank.findByBankName", query = "SELECT f FROM FmsBank f WHERE f.bankName = :bankName"),
    @NamedQuery(name = "FmsBank.findByBankId", query = "SELECT f FROM FmsBank f WHERE f.bankId = :bankId")})
public class FmsBank implements Serializable {

    private static final long serialVersionUID = 1L;
    //<editor-fold defaultstate="collapsed" desc="variable declaration">
    @Id
    @Basic(optional = false)
    @NotNull
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FMS_SEQ_BANK")
    @SequenceGenerator(name = "FMS_SEQ_BANK", sequenceName = "FMS_SEQ_BANK", allocationSize = 1)
    @Column(name = "BANK_ID")
    private Integer bankId;
    @Column(name = "BANK_CODE")
    private String bankCode;
    @Size(max = 45)
    @Column(name = "BANK_NAME")
    private String bankName;
    //<editor-fold defaultstate="collapsed" desc="Dynamic Searching Transient Parameters">
    @Transient
    private String columnName;
    @Transient
    private String columnValue;
    //</editor-fold >
    @OneToMany(mappedBy = "bankId")
    private List<FmsBankAccount> fmsBankAccountList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "bankId", fetch = FetchType.LAZY)
    private List<PrmsPaymentRequisition> prmsPaymentRequisitionList;
    @OneToMany(mappedBy = "bankId")
    private List<FmsBankCashDeposit> fmsBankCashDepositList;
//</editor-fold>

    public FmsBank() {
    }
//<editor-fold defaultstate="collapsed" desc="getter and setter">

    public FmsBank(Integer bankId) {
        this.bankId = bankId;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getColumnValue() {
        return columnValue;
    }

    public void setColumnValue(String columnValue) {
        this.columnValue = columnValue;
    }

    public Integer getBankId() {
        return bankId;
    }

    public void setBankId(Integer bankId) {
        this.bankId = bankId;
    }
//</editor-fold>

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (bankId != null ? bankId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof FmsBank)) {
            return false;
        }
        FmsBank other = (FmsBank) object;
        if ((this.bankId == null && other.bankId != null) || (this.bankId != null && !this.bankId.equals(other.bankId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return bankName;
    }

    @XmlTransient
    public List<FmsBankAccount> getFmsBankAccountList() {
        if (fmsBankAccountList == null) {
            fmsBankAccountList = new ArrayList<>();
        }
        return fmsBankAccountList;
    }

    public void setFmsBankAccountList(List<FmsBankAccount> fmsBankAccountList) {
        this.fmsBankAccountList = fmsBankAccountList;
    }

    @XmlTransient
    public List<FmsBankCashDeposit> getFmsBankCashDepositList() {
        return fmsBankCashDepositList;
    }

    public void setFmsBankCashDepositList(List<FmsBankCashDeposit> fmsBankCashDepositList) {
        this.fmsBankCashDepositList = fmsBankCashDepositList;
    }

    @XmlTransient
    public List<PrmsPaymentRequisition> getPrmsPaymentRequisitionList() {
        if (prmsPaymentRequisitionList == null) {
            prmsPaymentRequisitionList = new ArrayList<>();
        }
        return prmsPaymentRequisitionList;
    }

    public void setPrmsPaymentRequisitionList(List<PrmsPaymentRequisition> prmsPaymentRequisitionList) {
        this.prmsPaymentRequisitionList = prmsPaymentRequisitionList;
    }

}
