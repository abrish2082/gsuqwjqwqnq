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
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author AB
 */
@Entity
@Table(name = "fms_voucher_ready_to_tb" )
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FmsVoucherReadyToTb.findAll", query = "SELECT f FROM FmsVoucherReadyToTb f"),
    @NamedQuery(name = "FmsVoucherReadyToTb.findByVoucherCode", query = "SELECT f FROM FmsVoucherReadyToTb f WHERE f.voucherCode = :voucherCode"),
    @NamedQuery(name = "FmsVoucherReadyToTb.findByDebit", query = "SELECT f FROM FmsVoucherReadyToTb f WHERE f.debit = :debit"),
    @NamedQuery(name = "FmsVoucherReadyToTb.findByCredit", query = "SELECT f FROM FmsVoucherReadyToTb f WHERE f.credit = :credit"),
    @NamedQuery(name = "FmsVoucherReadyToTb.findByGlCode", query = "SELECT f FROM FmsVoucherReadyToTb f WHERE f.glCode = :glCode"),
    @NamedQuery(name = "FmsVoucherReadyToTb.findByType", query = "SELECT f FROM FmsVoucherReadyToTb f WHERE f.type = :type"),
    @NamedQuery(name = "FmsVoucherReadyToTb.findByStatus", query = "SELECT f FROM FmsVoucherReadyToTb f WHERE f.status = :status"),
    @NamedQuery(name = "FmsVoucherReadyToTb.findByPurpose", query = "SELECT f FROM FmsVoucherReadyToTb f WHERE f.purpose = :purpose")})
public class FmsVoucherReadyToTb implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "VOUCHER_CODE", nullable = false, length = 50)
    private String voucherCode;
    private Long debit;
    private Long credit;
    @Size(max = 30)
    @Column(name = "GL_CODE", length = 30)
    private String glCode;
    @Size(max = 20)
    @Column(length = 20)
    private String type;
    @Size(max = 10)
    @Column(length = 10)
    private String status;
    @Size(max = 200)
    @Column(length = 200)
    private String purpose;

    /**
     *
     */
    public FmsVoucherReadyToTb() {
    }

    /**
     *
     * @param voucherCode
     */
    public FmsVoucherReadyToTb(String voucherCode) {
        this.voucherCode = voucherCode;
    }

    /**
     *
     * @return
     */
    public String getVoucherCode() {
        return voucherCode;
    }

    /**
     *
     * @param voucherCode
     */
    public void setVoucherCode(String voucherCode) {
        this.voucherCode = voucherCode;
    }

    /**
     *
     * @return
     */
    public Long getDebit() {
        return debit;
    }

    /**
     *
     * @param debit
     */
    public void setDebit(Long debit) {
        this.debit = debit;
    }

    /**
     *
     * @return
     */
    public Long getCredit() {
        return credit;
    }

    /**
     *
     * @param credit
     */
    public void setCredit(Long credit) {
        this.credit = credit;
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
    public String getType() {
        return type;
    }

    /**
     *
     * @param type
     */
    public void setType(String type) {
        this.type = type;
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
    public String getPurpose() {
        return purpose;
    }

    /**
     *
     * @param purpose
     */
    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (voucherCode != null ? voucherCode.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FmsVoucherReadyToTb)) {
            return false;
        }
        FmsVoucherReadyToTb other = (FmsVoucherReadyToTb) object;
        if ((this.voucherCode == null && other.voucherCode != null) || (this.voucherCode != null && !this.voucherCode.equals(other.voucherCode))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "et.gov.insa.erp.ibfms.entity.FmsVoucherReadyToTb[ voucherCode=" + voucherCode + " ]";
    }
    
}
