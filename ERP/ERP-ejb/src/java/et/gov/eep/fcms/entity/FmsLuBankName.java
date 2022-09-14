/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.entity;

import et.gov.eep.fcms.entity.Vocher.FmsChequePaymentVoucher;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author AB
 */
@Entity
@Table(name = "fms_lu_bank_name" )
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FmsLuBankName.findAll", query = "SELECT f FROM FmsLuBankName f"),
    @NamedQuery(name = "FmsLuBankName.findByLuBankNameId", query = "SELECT f FROM FmsLuBankName f WHERE f.luBankNameId = :luBankNameId"),
    @NamedQuery(name = "FmsLuBankName.findByBankName", query = "SELECT f FROM FmsLuBankName f WHERE f.bankName = :bankName")})
public class FmsLuBankName implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FMS_LU_BANK_NAME_LU_BANK_N_SEQ")
    @SequenceGenerator(name = "FMS_LU_BANK_NAME_LU_BANK_N_SEQ", sequenceName = "FMS_LU_BANK_NAME_LU_BANK_N_SEQ", allocationSize = 1)
    @Basic(optional = false)
    @Column(name = "LU_BANK_NAME_ID", nullable = false)
    private Integer luBankNameId;
    @Size(max = 50)
    @Column(name = "BANK_NAME", length = 50)
    private String bankName;
    @OneToMany(mappedBy = "luBankNameId")
    private List<FmsChequePaymentVoucher> fmsChequePaymentVoucherList;

    /**
     *
     */
    public FmsLuBankName() {
    }

    /**
     *
     * @param luBankNameId
     */
    public FmsLuBankName(Integer luBankNameId) {
        this.luBankNameId = luBankNameId;
    }

    /**
     *
     * @return
     */
    public Integer getLuBankNameId() {
        return luBankNameId;
    }

    /**
     *
     * @param luBankNameId
     */
    public void setLuBankNameId(Integer luBankNameId) {
        this.luBankNameId = luBankNameId;
    }

    /**
     *
     * @return
     */
    public String getBankName() {
        return bankName;
    }

    /**
     *
     * @param bankName
     */
    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    /**
     *
     * @return
     */
    @XmlTransient
    public List<FmsChequePaymentVoucher> getFmsChequePaymentVoucherList() {
        return fmsChequePaymentVoucherList;
    }

    /**
     *
     * @param fmsChequePaymentVoucherList
     */
    public void setFmsChequePaymentVoucherList(List<FmsChequePaymentVoucher> fmsChequePaymentVoucherList) {
        this.fmsChequePaymentVoucherList = fmsChequePaymentVoucherList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (luBankNameId != null ? luBankNameId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FmsLuBankName)) {
            return false;
        }
        FmsLuBankName other = (FmsLuBankName) object;
        if ((this.luBankNameId == null && other.luBankNameId != null) || (this.luBankNameId != null && !this.luBankNameId.equals(other.luBankNameId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return bankName;
    }
    
}
