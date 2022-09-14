/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.entity.bank;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author mubejbl
 */
@Entity
@Table(name = "FMS_BANK_ACCOUNT")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FmsBankAccount.findAll", query = "SELECT f FROM FmsBankAccount f"),
    @NamedQuery(name = "FmsBankAccount.findByBranchName", query = "SELECT f FROM FmsBankAccount f WHERE f.branchName = :branchName"),
    @NamedQuery(name = "FmsBankAccount.findByBankNameAndBranchName", query = "SELECT f FROM FmsBankAccount f WHERE f.bankId = :bankId AND f.branchName = :branchName"),
    @NamedQuery(name = "FmsBankAccount.findByBankID", query = "SELECT f FROM FmsBankAccount f WHERE f.bankId = :bankId"),
    @NamedQuery(name = "FmsBankAccount.findByLocation", query = "SELECT f FROM FmsBankAccount f WHERE f.location = :location"),
    @NamedQuery(name = "FmsBankAccount.findByAccCreatedDate", query = "SELECT f FROM FmsBankAccount f WHERE f.accCreatedDate = :accCreatedDate"),
    @NamedQuery(name = "FmsBankAccount.findByBankAccountId", query = "SELECT f FROM FmsBankAccount f WHERE f.bankAccountId = :bankAccountId")})
public class FmsBankAccount implements Serializable {

    private static final long serialVersionUID = 1L;
    //<editor-fold defaultstate="collapsed" desc="variable declaration">
    @Size(max = 50)
    @Column(name = "BRANCH_NAME")
    private String branchName;
    @Size(max = 50)
    @Column(name = "LOCATION")
    private String location;
    @Column(name = "ACC_CREATED_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date accCreatedDate;
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FMS_BANK_ACCOUNT_SEQ")
    @SequenceGenerator(name = "FMS_BANK_ACCOUNT_SEQ", sequenceName = "FMS_BANK_ACCOUNT_SEQ", allocationSize = 1)
    @Column(name = "BANK_ACCOUNT_ID")
    private Integer bankAccountId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "bankAccountId")
    private List<FmsBankBranchAccounts> fmsBankBranchAccountsList = new ArrayList<>();
    @JoinColumn(name = "BANK_ID", referencedColumnName = "BANK_ID")
    @ManyToOne
    private FmsBank bankId;
//</editor-fold>

    public FmsBankAccount() {
    }
//<editor-fold defaultstate="collapsed" desc="getter and setter">

    public FmsBankAccount(Integer bankAccountId) {
        this.bankAccountId = bankAccountId;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public FmsBank getBankId() {
        return bankId;
    }

    public void setBankId(FmsBank bankId) {
        this.bankId = bankId;
    }

    public Date getAccCreatedDate() {
        return accCreatedDate;
    }

    public void setAccCreatedDate(Date accCreatedDate) {
        this.accCreatedDate = accCreatedDate;
    }

    public Integer getBankAccountId() {
        return bankAccountId;
    }

    public void setBankAccountId(Integer bankAccountId) {
        this.bankAccountId = bankAccountId;
    }
//</editor-fold>

    @XmlTransient
    public List<FmsBankBranchAccounts> getFmsBankBranchAccountsList() {
        if (fmsBankBranchAccountsList == null) {
            fmsBankBranchAccountsList = new ArrayList<>();
        }
        return fmsBankBranchAccountsList;

    }

    public void setFmsBankBranchAccountsList(List<FmsBankBranchAccounts> fmsBankBranchAccountsList) {
        this.fmsBankBranchAccountsList = fmsBankBranchAccountsList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (bankAccountId != null ? bankAccountId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof FmsBankAccount)) {
            return false;
        }
        FmsBankAccount other = (FmsBankAccount) object;
        if ((this.bankAccountId == null && other.bankAccountId != null) || (this.bankAccountId != null && !this.bankAccountId.equals(other.bankAccountId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return bankAccountId.toString();
    }

    public void addAccountInfoDetail(FmsBankBranchAccounts fmsBankBranchAccounts) {
        if (fmsBankBranchAccounts.getBankAccountId() != this) {
            this.getFmsBankBranchAccountsList().add(fmsBankBranchAccounts);
            fmsBankBranchAccounts.setBankAccountId(this);

        }
    }

}
