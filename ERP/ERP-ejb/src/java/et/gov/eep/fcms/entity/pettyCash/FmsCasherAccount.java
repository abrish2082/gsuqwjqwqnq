/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.entity.pettyCash;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import et.gov.eep.fcms.entity.admin.FmsSubsidiaryLedger;
import et.gov.eep.hrms.entity.employee.HrEmployees;

/**
 *
 * @author AB
 */
@Entity
@Table(name = "FMS_CASHER_ACCOUNT")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FmsCasherAccount.findAll", query = "SELECT f FROM FmsCasherAccount f"),
    @NamedQuery(name = "FmsCasherAccount.findById", query = "SELECT f FROM FmsCasherAccount f WHERE f.id = :id"),
    @NamedQuery(name = "FmsCasherAccount.findByAmount", query = "SELECT f FROM FmsCasherAccount f WHERE f.amount = :amount"),
    @NamedQuery(name = "FmsCasherAccount.findByEmpCode", query = "SELECT f FROM FmsCasherAccount f WHERE f.empCode LIKE :empCode"),
    @NamedQuery(name = "FmsCasherAccount.findByStatus", query = "SELECT f FROM FmsCasherAccount f WHERE f.status LIKE :status"),
    @NamedQuery(name = "FmsCasherAccount.findByEmpCodeOBJ", query = "SELECT f FROM FmsCasherAccount f WHERE f.empCode = :empCode"),
    @NamedQuery(name = "FmsCasherAccount.findBySubsidiaryID", query = "SELECT f FROM FmsCasherAccount f WHERE f.subsidiaryId = :subsidiaryId")

})
public class FmsCasherAccount implements Serializable {

    private static final long serialVersionUID = 1L;
    //<editor-fold defaultstate="collapsed" desc="variable declaration">
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FMS_CASHER_ACCOUNT_SEQ")
    @SequenceGenerator(name = "FMS_CASHER_ACCOUNT_SEQ", sequenceName = "FMS_CASHER_ACCOUNT_SEQ", allocationSize = 1)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "AMOUNT")
    private Double amount;
    @Size(max = 55)
    @Column(name = "ACCOUNT_CODE", length = 55)
    private String accountCode;
    @Size(max = 45)
    @Column(name = "STATUS", length = 45)
    private String status;
    @JoinColumn(name = "SUBSIDIARY_ID", referencedColumnName = "SUBSIDIARY_ID")
    @OneToOne
    private FmsSubsidiaryLedger subsidiaryId;
    @JoinColumn(name = "EMP_CODE", referencedColumnName = "ID")
    @ManyToOne
    private HrEmployees empCode;
    @Transient
    List<FmsCasherAccount> casherAccountsList = new ArrayList<>();
    @OneToMany(mappedBy = "chasherId")
    private List<FmsDailyCashRegister> fmsDailyCashRegisterList;
    @OneToMany(mappedBy = "cashierId")
    private List<FmsPettyCashReplenishment> fmsPettyCashReplenishmentList;
    @Transient
    String display_conn;
//</editor-fold>

    public FmsCasherAccount() {
    }
//<editor-fold defaultstate="collapsed" desc="getter and setter">

    public String getAccountCode() {
        return accountCode;
    }

    public void setAccountCode(String accountCode) {
        this.accountCode = accountCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setCasherAccountsList(List<FmsCasherAccount> casherAccountsList) {
        this.casherAccountsList = casherAccountsList;
    }

    public FmsCasherAccount(int id) {
        this.id = id;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public HrEmployees getEmpCode() {
        return empCode;
    }

    public void setEmpCode(HrEmployees empCode) {
        this.empCode = empCode;
    }

    public FmsSubsidiaryLedger getSubsidiaryId() {
        return subsidiaryId;
    }

    public void setSubsidiaryId(FmsSubsidiaryLedger subsidiaryId) {
        this.subsidiaryId = subsidiaryId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDisplay_conn() {
        return display_conn;
    }

    public void setDisplay_conn(String display_conn) {
        this.display_conn = display_conn;
    }

    public List<FmsPettyCashReplenishment> getFmsPettyCashReplenishmentList() {
        return fmsPettyCashReplenishmentList;
    }

    public void setFmsPettyCashReplenishmentList(List<FmsPettyCashReplenishment> fmsPettyCashReplenishmentList) {
        this.fmsPettyCashReplenishmentList = fmsPettyCashReplenishmentList;
    }
//</editor-fold>

    @XmlTransient
    public List<FmsDailyCashRegister> getFmsDailyCashRegisterList() {
        return fmsDailyCashRegisterList;
    }

    public void setFmsDailyCashRegisterList(List<FmsDailyCashRegister> fmsDailyCashRegisterList) {
        this.fmsDailyCashRegisterList = fmsDailyCashRegisterList;
    }

    @XmlTransient
    public List<FmsCasherAccount> getCasherAccountsList() {
        if (casherAccountsList == null) {
            casherAccountsList = new ArrayList<>();
        }
        return casherAccountsList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof FmsCasherAccount)) {
            return false;
        }
        FmsCasherAccount other = (FmsCasherAccount) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return id.toString();
    }

    public void addToFmsCasherAccountDetail(FmsCasherAccount casherAccountDetail) {

        this.getCasherAccountsList().add(casherAccountDetail);

    }
}
