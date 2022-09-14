/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.entity.bank;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityResult;
import javax.persistence.FieldResult;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author mubejbl
 */
@Entity
@Table(name = "FMS_CODED_TRANSACTION")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FmsCodedTransaction.findAll", query = "SELECT f FROM FmsCodedTransaction f"),
    @NamedQuery(name = "FmsCodedTransaction.findByCodedTransactonId", query = "SELECT f FROM FmsCodedTransaction f WHERE f.codedTransactonId = :codedTransactonId"),
    @NamedQuery(name = "FmsCodedTransaction.findByStatusANDtranRef", query = "SELECT f FROM FmsCodedTransaction f WHERE f.status = :status AND f.tranRef = :tranRef"),
    @NamedQuery(name = "FmsCodedTransaction.findByCredit", query = "SELECT f FROM FmsCodedTransaction f WHERE f.credit = :credit"),
    @NamedQuery(name = "FmsCodedTransaction.findByDebit", query = "SELECT f FROM FmsCodedTransaction f WHERE f.debit = :debit"),
    @NamedQuery(name = "FmsCodedTransaction.findByTranRef", query = "SELECT f FROM FmsCodedTransaction f WHERE f.tranRef = :tranRef"),
    @NamedQuery(name = "FmsCodedTransaction.findByStatus", query = "SELECT f FROM FmsCodedTransaction f WHERE f.status = :status"),
    @NamedQuery(name = "FmsCodedTransaction.findByStatusAndSystemCode", query = "SELECT f FROM FmsCodedTransaction f WHERE f.status = :status AND f.subsidiaryId LIKE :subsidiaryId"),
    @NamedQuery(name = "FmsCodedTransaction.findByReconStatus", query = "SELECT f FROM FmsCodedTransaction f WHERE f.recon_status = :recon_status"),
    @NamedQuery(name = "FmsCodedTransaction.findByType", query = "SELECT f FROM FmsCodedTransaction f WHERE f.type = :type")})

//OrderResultsAcu
@SqlResultSetMapping(name = "OrderResultsAcu",
        entities = {
            @EntityResult(entityClass = FmsCodedTransaction.class, fields = {
                @FieldResult(name = "codedTransactonId", column = "ctId"),
                @FieldResult(name = "tranRef", column = "Ref_No"),
                @FieldResult(name = "tranRef", column = "Ref_No"),
                @FieldResult(name = "type", column = "dates"),
                @FieldResult(name = "type", column = "Dates"),
                @FieldResult(name = "debit", column = "Amounts"),
                @FieldResult(name = "credit", column = "Amount")})})

public class FmsCodedTransaction implements Serializable {

    private static final long serialVersionUID = 1L;
    //<editor-fold defaultstate="collapsed" desc="variable declaration">
    @Id
    @Basic(optional = false)
    @NotNull
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FMS_CODED_TRANSACTION_SEQ")
    @SequenceGenerator(name = "FMS_CODED_TRANSACTION_SEQ", sequenceName = "FMS_CODED_TRANSACTION_SEQ", allocationSize = 1)
    @Column(name = "CODED_TRANSACTON_ID")
    private Integer codedTransactonId;
    @Column(name = "CREDIT")
    private Double credit;
    @Column(name = "DEBIT")
    private Double debit;
    @Column(name = "TRAN_REF")
    private String tranRef;
    @Column(name = "STATUS")
    private Integer status;
    @Column(name = "RECON_STATUS")
    private Integer recon_status;
    @Column(name = "TYPE")
    private String type;
    @Size(max = 50)
    @Column(name = "SUBSIDIARY_ID")
    private String subsidiaryId;
    @Transient
    String display_conn;
//</editor-fold>

    public FmsCodedTransaction() {
    }

    //<editor-fold defaultstate="collapsed" desc="getter and setter">

    public FmsCodedTransaction(Integer codedTransactonId) {
        this.codedTransactonId = codedTransactonId;
    }

    public Integer getCodedTransactonId() {
        return codedTransactonId;
    }

    public void setCodedTransactonId(Integer codedTransactonId) {
        this.codedTransactonId = codedTransactonId;
    }

    public Double getCredit() {
        return credit;
    }

    public void setCredit(Double credit) {
        this.credit = credit;
    }

    public Double getDebit() {
        return debit;
    }

    public void setDebit(Double debit) {
        this.debit = debit;
    }

    public String getTranRef() {
        return tranRef;
    }

    public void setTranRef(String tranRef) {
        this.tranRef = tranRef;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getRecon_status() {
        return recon_status;
    }

    public void setRecon_status(Integer recon_status) {
        this.recon_status = recon_status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSubsidiaryId() {
        return subsidiaryId;
    }

    public void setSubsidiaryId(String subsidiaryId) {
        this.subsidiaryId = subsidiaryId;
    }

    public String getDisplay_conn() {
        return display_conn;
    }

    public void setDisplay_conn(String display_conn) {
        this.display_conn = display_conn;
    }
//</editor-fold>

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codedTransactonId != null ? codedTransactonId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof FmsCodedTransaction)) {
            return false;
        }
        FmsCodedTransaction other = (FmsCodedTransaction) object;
        if ((this.codedTransactonId == null && other.codedTransactonId != null) || (this.codedTransactonId != null && !this.codedTransactonId.equals(other.codedTransactonId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return codedTransactonId.toString();
    }

}
