/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.entity.bank;

import java.io.Serializable;
import java.math.BigDecimal;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import et.gov.eep.commonApplications.entity.ComParty;

/**
 *
 * @author Hanoc
 */
@Entity
@Table(name = "FMS_DEPOSIT_SLIP")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FmsDepositSlip.findAll", query = "SELECT f FROM FmsDepositSlip f"),
    @NamedQuery(name = "FmsDepositSlip.findBySeqId", query = "SELECT f FROM FmsDepositSlip f WHERE f.seqId = :seqId"),
    @NamedQuery(name = "FmsDepositSlip.findByCompanyName", query = "SELECT f FROM FmsDepositSlip f WHERE f.companyName = :companyName"),
    @NamedQuery(name = "FmsDepositSlip.findByAmount", query = "SELECT f FROM FmsDepositSlip f WHERE f.amount = :amount"),
    @NamedQuery(name = "FmsDepositSlip.findByDueDate", query = "SELECT f FROM FmsDepositSlip f WHERE f.dueDate = :dueDate AND f.companyName = :companyName"),
    @NamedQuery(name = "FmsDepositSlip.findByCollectionDate", query = "SELECT f FROM FmsDepositSlip f WHERE f.collectionDate = :collectionDate")})
public class FmsDepositSlip implements Serializable {

    private static final long serialVersionUID = 1L;
    //<editor-fold defaultstate="collapsed" desc="variable declaration">
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FMS_SEQ_DEPOSIT_SLIP")
    @SequenceGenerator(name = "FMS_SEQ_DEPOSIT_SLIP", sequenceName = "FMS_SEQ_DEPOSIT_SLIP", allocationSize = 1)
    @NotNull
    @Column(name = "SEQ_ID")
    private BigDecimal seqId;
    @Size(max = 100)
    @Column(name = "COMPANY_NAME")
    private String companyName;
    @Column(name = "AMOUNT")
    private BigDecimal amount;
    @Size(max = 20)
    @Column(name = "DUE_DATE")
    private String dueDate;
    @Size(max = 20)
    @Column(name = "COLLECTION_DATE")
    private String collectionDate;
    @JoinColumn(name = "COM_ID", referencedColumnName = "PARTY_ID")
    @ManyToOne
    private ComParty comId;
    @Size(max = 20)
    @Column(name = "VOUCHER_NO")
    private String voucherNo;
//</editor-fold>

    public FmsDepositSlip() {
    }

    //<editor-fold defaultstate="collapsed" desc="getter and setter">
    public FmsDepositSlip(BigDecimal seqId) {
        this.seqId = seqId;
    }

    public BigDecimal getSeqId() {
        return seqId;
    }

    public void setSeqId(BigDecimal seqId) {
        this.seqId = seqId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getCollectionDate() {
        return collectionDate;
    }

    public void setCollectionDate(String collectionDate) {
        this.collectionDate = collectionDate;
    }

    public ComParty getComId() {
        return comId;
    }

    public void setComId(ComParty comId) {
        this.comId = comId;
    }

    public String getVoucherNo() {
        return voucherNo;
    }

    public void setVoucherNo(String voucherNo) {
        this.voucherNo = voucherNo;
    }
//</editor-fold>

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (seqId != null ? seqId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof FmsDepositSlip)) {
            return false;
        }
        FmsDepositSlip other = (FmsDepositSlip) object;
        if ((this.seqId == null && other.seqId != null) || (this.seqId != null && !this.seqId.equals(other.seqId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "folder.FmsDepositSlip[ seqId=" + seqId + " ]";
    }

}
