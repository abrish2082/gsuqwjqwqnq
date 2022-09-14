/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull; 
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author memube
 */
@Entity
@Table(name = "FMS_ACCOUNT_USE_TEMP_DATA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FmsAccountUseTempData.findAll", query = "SELECT f FROM FmsAccountUseTempData f"),
    @NamedQuery(name = "FmsAccountUseTempData.findById", query = "SELECT f FROM FmsAccountUseTempData f WHERE f.id = :id"), 
    @NamedQuery(name = "FmsAccountUseTempData.findByAccountCode", query = "SELECT f FROM FmsAccountUseTempData f WHERE f.accountCode = :accountCode"), 
    @NamedQuery(name = "FmsAccountUseTempData.findByDebit", query = "SELECT f FROM FmsAccountUseTempData f WHERE f.debit = :debit"),
    @NamedQuery(name = "FmsAccountUseTempData.findByCredit", query = "SELECT f FROM FmsAccountUseTempData f WHERE f.credit = :credit"), 
    @NamedQuery(name = "FmsAccountUseTempData.findByRefNo", query = "SELECT f FROM FmsAccountUseTempData f WHERE f.refNo = :refNo"), 
    @NamedQuery(name = "FmsAccountUseTempData.findByStatus", query = "SELECT f FROM FmsAccountUseTempData f WHERE f.status = :status"), 
    @NamedQuery(name = "FmsAccountUseTempData.findByTransactionDate", query = "SELECT f FROM FmsAccountUseTempData f WHERE f.transactionDate = :transactionDate")}) 
public class FmsAccountUseTempData implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private BigDecimal id;
    @Size(max = 45)
    @Column(name = "ACCOUNT_CODE")
    private String accountCode;
    @Column(name = "DEBIT")
    private BigDecimal debit;
    @Column(name = "CREDIT")
    private BigDecimal credit;
    @Size(max = 52)
    @Column(name = "REF_NO")
    private String refNo;
    @Column(name = "STATUS")
    private Integer status;
    @Column(name = "TRANSACTION_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date transactionDate;

    public FmsAccountUseTempData() {
    }

    public FmsAccountUseTempData(BigDecimal id) {
        this.id = id;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public String getAccountCode() {
        return accountCode;
    }

    public void setAccountCode(String accountCode) {
        this.accountCode = accountCode;
    }

    public BigDecimal getDebit() {
        return debit;
    }

    public void setDebit(BigDecimal debit) {
        this.debit = debit;
    }

    public BigDecimal getCredit() {
        return credit;
    }

    public void setCredit(BigDecimal credit) {
        this.credit = credit;
    }

    public String getRefNo() {
        return refNo;
    }

    public void setRefNo(String refNo) {
        this.refNo = refNo;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }
    @Transient
    String display_conn;

    public String getDisplay_conn() {
        return display_conn;
    }

    public void setDisplay_conn(String display_conn) {
        this.display_conn = display_conn;
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
        if (!(object instanceof FmsAccountUseTempData)) {
            return false;
        }
        FmsAccountUseTempData other = (FmsAccountUseTempData) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "et.gov.eep.fcms.entity.FmsAccountUseTempData[ id=" + id + " ]";
    }

}
