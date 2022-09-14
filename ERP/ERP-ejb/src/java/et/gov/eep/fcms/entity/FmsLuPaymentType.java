/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.entity;

import et.gov.eep.fcms.entity.Vocher.FmsCashReceiptVoucher;
import et.gov.eep.fcms.entity.Vocher.FmsChequePaymentVoucher;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
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
 * @author mora1
 */
@Entity
@Table(name = "FMS_LU_PAYMENT_TYPE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FmsLuPaymentType.findAll", query = "SELECT f FROM FmsLuPaymentType f"),
    @NamedQuery(name = "FmsLuPaymentType.findByLuPaymentTypeId", query = "SELECT f FROM FmsLuPaymentType f WHERE f.luPaymentTypeId = :luPaymentTypeId"),
    @NamedQuery(name = "FmsLuPaymentType.findByPaymentType", query = "SELECT f FROM FmsLuPaymentType f WHERE f.paymentType = :paymentType"),
    @NamedQuery(name = "FmsLuPaymentType.findByPayment", query = "SELECT f FROM FmsLuPaymentType f WHERE f.payment = :payment")})
public class FmsLuPaymentType implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "LU_PAYMENT_TYPE_ID")
    private Long luPaymentTypeId;
    @Size(max = 50)
    @Column(name = "PAYMENT_TYPE")
    private String paymentType;
    @Size(max = 20)
    @Column(name = "PAYMENT")
    private String payment;
    @OneToMany(mappedBy = "luPaymentTypeId")
    private List<FmsChequePaymentVoucher> fmsChequePaymentVoucherList;

    @OneToMany(mappedBy = "paymentType")
    private List<FmsCashReceiptVoucher> fmsCashReceiptVoucherList;

    public FmsLuPaymentType() {
    }

    public FmsLuPaymentType(Long luPaymentTypeId) {
        this.luPaymentTypeId = luPaymentTypeId;
    }

    public Long getLuPaymentTypeId() {
        return luPaymentTypeId;
    }

    public void setLuPaymentTypeId(Long luPaymentTypeId) {
        this.luPaymentTypeId = luPaymentTypeId;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

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
    @XmlTransient
    
    public List<FmsCashReceiptVoucher> getFmsCashReceiptVoucherList() {
        if(fmsCashReceiptVoucherList==null){
            fmsCashReceiptVoucherList=new ArrayList<>();
        }
        return fmsCashReceiptVoucherList;
    }

    public void setFmsCashReceiptVoucherList(List<FmsCashReceiptVoucher> fmsCashReceiptVoucherList) {
        this.fmsCashReceiptVoucherList = fmsCashReceiptVoucherList;
    }
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (luPaymentTypeId != null ? luPaymentTypeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FmsLuPaymentType)) {
            return false;
        }
        FmsLuPaymentType other = (FmsLuPaymentType) object;
        if ((this.luPaymentTypeId == null && other.luPaymentTypeId != null) || (this.luPaymentTypeId != null && !this.luPaymentTypeId.equals(other.luPaymentTypeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return paymentType;
    }

}
