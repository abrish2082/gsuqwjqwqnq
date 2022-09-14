/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.entity.bank;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author sura
 */
@Entity
@Table(name = "FMS_BANK_BOOK_ERROR")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FmsBankBookError.findAll", query = "SELECT f FROM FmsBankBookError f"),
    @NamedQuery(name = "FmsBankBookError.findById", query = "SELECT f FROM FmsBankBookError f WHERE f.id = :id"),
    @NamedQuery(name = "FmsBankBookError.findByDates", query = "SELECT f FROM FmsBankBookError f WHERE f.dates = :dates"),
    @NamedQuery(name = "FmsBankBookError.findByAmount", query = "SELECT f FROM FmsBankBookError f WHERE f.amount = :amount"),
    @NamedQuery(name = "FmsBankBookError.findByDiscription", query = "SELECT f FROM FmsBankBookError f WHERE f.discription = :discription")})
public class FmsBankBookError implements Serializable {

    private static final long serialVersionUID = 1L;
    //<editor-fold defaultstate="collapsed" desc="variable declaration">
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FMS_BANK_BOOK_SEQ")
    @SequenceGenerator(name = "FMS_BANK_BOOK_SEQ", sequenceName = "FMS_BANK_BOOK_SEQ", allocationSize = 1)
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "ID")
    private String id;
    @Size(max = 20)
    @Column(name = "DATES")
    private String dates;
    @Column(name = "AMOUNT")
    private Double amount;
    @Size(max = 300)
    @Column(name = "DISCRIPTION")
    private String discription;
    @Size(max = 20)
    @Column(name = "TYPE")
    private String type;
    @Column(name = "BANK_ACCOUNT_NO")
    private String bankAccountNo;
    @Transient
    private Date dateval;
//</editor-fold>

    public FmsBankBookError() {
    }
//<editor-fold defaultstate="collapsed" desc="getter and setter">

    public FmsBankBookError(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDates() {
        return dates;
    }

    public void setDates(String dates) {
        this.dates = dates;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getDiscription() {
        return discription;
    }

    public void setDiscription(String discription) {
        this.discription = discription;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBankAccountNo() {
        return bankAccountNo;
    }

    public void setBankAccountNo(String bankAccountNo) {
        this.bankAccountNo = bankAccountNo;
    }

    public Date getDateval() throws ParseException {
        if (dates != null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
            dateval = dateFormat.parse(dates);
        }
        return dateval;
    }

    public void setDateval(Date dateval) {
        this.dateval = dateval;
    }
//</editor-fold>

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof FmsBankBookError)) {
            return false;
        }
        FmsBankBookError other = (FmsBankBookError) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "et.gov.insa.erp.ibfms.entity.FmsBankBookError[ id=" + id + " ]";
    }

}
