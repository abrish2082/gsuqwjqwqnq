/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.entity;

import java.io.Serializable;
import java.math.BigInteger;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author mora1
 */
@Entity
@Table(name = "FMS_ACCOUNTS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FmsAccounts.findAll", query = "SELECT f FROM FmsAccounts f"),
    @NamedQuery(name = "FmsAccounts.findByAccountsId", query = "SELECT f FROM FmsAccounts f WHERE f.accountsId = :accountsId"),
    @NamedQuery(name = "FmsAccounts.findByAccountsCode", query = "SELECT f FROM FmsAccounts f WHERE f.accountsCode = :accountsCode"),
    @NamedQuery(name = "FmsAccounts.findByAccountsTitle", query = "SELECT f FROM FmsAccounts f WHERE f.accountsTitle = :accountsTitle"),
    @NamedQuery(name = "FmsAccounts.findByParentId", query = "SELECT f FROM FmsAccounts f WHERE f.parentId = :parentId")})
public class FmsAccounts implements Serializable {

    private static final long serialVersionUID = 1L;
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FMS_ACCOUNTS_SEQ")
    @SequenceGenerator(name = "FMS_ACCOUNTS_SEQ", sequenceName = "FMS_ACCOUNTS_SEQ", allocationSize = 1)
    @Column(name = "ACCOUNTS_ID")
    private Integer accountsId;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "ACCOUNTS_CODE")
    private String accountsCode;
    @Size(max = 200)
    @Column(name = "ACCOUNTS_TITLE")
    private String accountsTitle;
    @Size(max = 20)
    @Column(name = "PARENT_ID")
    private String parentId;

    public FmsAccounts() {
    }

    public FmsAccounts(String accountsCode) {
        this.accountsCode = accountsCode;
    }

    public Integer getAccountsId() {
        return accountsId;
    }

    public void setAccountsId(Integer accountsId) {
        this.accountsId = accountsId;
    }

    public String getAccountsCode() {
        return accountsCode;
    }

    public void setAccountsCode(String accountsCode) {
        this.accountsCode = accountsCode;
    }

    public String getAccountsTitle() {
        return accountsTitle;
    }

    public void setAccountsTitle(String accountsTitle) {
        this.accountsTitle = accountsTitle;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (accountsCode != null ? accountsCode.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FmsAccounts)) {
            return false;
        }
        FmsAccounts other = (FmsAccounts) object;
        if ((this.accountsCode == null && other.accountsCode != null) || (this.accountsCode != null && !this.accountsCode.equals(other.accountsCode))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return accountsCode;
    }

}
