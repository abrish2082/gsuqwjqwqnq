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
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author user
 */
@Entity
@Table(name = "FMS_FA_CODED_DESPOSAL")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FmsFaCodedDesposal.findAll", query = "SELECT f FROM FmsFaCodedDesposal f"),
    @NamedQuery(name = "FmsFaCodedDesposal.findByIdNo", query = "SELECT f FROM FmsFaCodedDesposal f WHERE f.idNo = :idNo"),
    @NamedQuery(name = "FmsFaCodedDesposal.findByActionDate", query = "SELECT f FROM FmsFaCodedDesposal f WHERE f.actionDate = :actionDate"),
    @NamedQuery(name = "FmsFaCodedDesposal.findByAccountId", query = "SELECT f FROM FmsFaCodedDesposal f WHERE f.accountId = :accountId"),
    @NamedQuery(name = "FmsFaCodedDesposal.findByDebit", query = "SELECT f FROM FmsFaCodedDesposal f WHERE f.debit = :debit"),
    @NamedQuery(name = "FmsFaCodedDesposal.findByCredit", query = "SELECT f FROM FmsFaCodedDesposal f WHERE f.credit = :credit"),
    @NamedQuery(name = "FmsFaCodedDesposal.findByFaCode", query = "SELECT f FROM FmsFaCodedDesposal f WHERE f.faCode = :faCode")})
public class FmsFaCodedDesposal implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_NO")
    private Integer idNo;
    @Size(max = 20)
    @Column(name = "ACTION_DATE")
    private String actionDate;
    @Size(max = 20)
    @Column(name = "ACCOUNT_ID")
    private String accountId;
    @Column(name = "DEBIT")
    private Integer debit;
    @Column(name = "CREDIT")
    private Integer credit;
    @Column(name = "FA_CODE")
    private BigInteger faCode;

    /**
     *
     */
    public FmsFaCodedDesposal() {
    }

    /**
     *
     * @param idNo
     */
    public FmsFaCodedDesposal(Integer idNo) {
        this.idNo = idNo;
    }

    /**
     *
     * @return
     */
    public Integer getIdNo() {
        return idNo;
    }

    /**
     *
     * @param idNo
     */
    public void setIdNo(Integer idNo) {
        this.idNo = idNo;
    }

    /**
     *
     * @return
     */
    public String getActionDate() {
        return actionDate;
    }

    /**
     *
     * @param actionDate
     */
    public void setActionDate(String actionDate) {
        this.actionDate = actionDate;
    }

    /**
     *
     * @return
     */
    public String getAccountId() {
        return accountId;
    }

    /**
     *
     * @param accountId
     */
    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    /**
     *
     * @return
     */
    public Integer getDebit() {
        return debit;
    }

    /**
     *
     * @param debit
     */
    public void setDebit(Integer debit) {
        this.debit = debit;
    }

    /**
     *
     * @return
     */
    public Integer getCredit() {
        return credit;
    }

    /**
     *
     * @param credit
     */
    public void setCredit(Integer credit) {
        this.credit = credit;
    }

    /**
     *
     * @return
     */
    public BigInteger getFaCode() {
        return faCode;
    }

    /**
     *
     * @param faCode
     */
    public void setFaCode(BigInteger faCode) {
        this.faCode = faCode;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idNo != null ? idNo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FmsFaCodedDesposal)) {
            return false;
        }
        FmsFaCodedDesposal other = (FmsFaCodedDesposal) object;
        if ((this.idNo == null && other.idNo != null) || (this.idNo != null && !this.idNo.equals(other.idNo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "et.gov.insa.erp.ibfms.entity.FmsFaCodedDesposal[ idNo=" + idNo + " ]";
    }
    
}
