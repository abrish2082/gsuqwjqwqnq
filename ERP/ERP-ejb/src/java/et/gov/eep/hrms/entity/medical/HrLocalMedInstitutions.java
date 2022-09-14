/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.entity.medical;

import et.gov.eep.hrms.entity.lookup.HrLuBankBranches;
import et.gov.eep.hrms.entity.lookup.HrLuBanks;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author INSA
 */
@Entity
@Table(name = "HR_LOCAL_MED_INSTITUTIONS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HrLocalMedInstitutions.findAll", query = "SELECT h FROM HrLocalMedInstitutions h"),
    @NamedQuery(name = "HrLocalMedInstitutions.findById", query = "SELECT h FROM HrLocalMedInstitutions h WHERE h.id = :id"),
    @NamedQuery(name = "HrLocalMedInstitutions.findByName", query = "SELECT h FROM HrLocalMedInstitutions h WHERE h.name = :name"),

    @NamedQuery(name = "HrLocalMedInstitutions.findHospital", query = "SELECT h FROM HrLocalMedInstitutions h WHERE h.type = 'Hospital' AND h.status = 1"),
    @NamedQuery(name = "HrLocalMedInstitutions.findPharmacy", query = "SELECT h FROM HrLocalMedInstitutions h WHERE h.type = 'Pharmacy' AND h.status = 1"),
    @NamedQuery(name = "HrLocalMedInstitutions.checkDuplicate", query = "SELECT h FROM HrLocalMedInstitutions h WHERE h.name = :name and h.type = :type"),
    @NamedQuery(name = "HrLocalMedInstitutions.findByType", query = "SELECT h FROM HrLocalMedInstitutions h WHERE h.type = :type"),
    @NamedQuery(name = "HrLocalMedInstitutions.findByPartner", query = "SELECT h FROM HrLocalMedInstitutions h WHERE h.partner = :partner"),
    @NamedQuery(name = "HrLocalMedInstitutions.findByAccountNumber", query = "SELECT h FROM HrLocalMedInstitutions h WHERE h.accountNumber = :accountNumber"),
    @NamedQuery(name = "HrLocalMedInstitutions.findByTinNumber", query = "SELECT h FROM HrLocalMedInstitutions h WHERE h.tinNumber = :tinNumber"),
    @NamedQuery(name = "HrLocalMedInstitutions.findByAddress", query = "SELECT h FROM HrLocalMedInstitutions h WHERE h.address = :address"),
    @NamedQuery(name = "HrLocalMedInstitutions.findBySpecialization", query = "SELECT h FROM HrLocalMedInstitutions h WHERE h.specialization = :specialization"),
    @NamedQuery(name = "HrLocalMedInstitutions.findByStatus", query = "SELECT h FROM HrLocalMedInstitutions h WHERE h.status = :status")})
public class HrLocalMedInstitutions implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HR_LOCAL_MED_INSTITUTIONS_SEQ")
    @SequenceGenerator(name = "HR_LOCAL_MED_INSTITUTIONS_SEQ", sequenceName = "HR_LOCAL_MED_INSTITUTIONS_SEQ", allocationSize = 1)
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private Integer id;
    @Size(max = 50)
    @Column(name = "NAME")
    private String name;
    @Size(max = 20)
    @Column(name = "TYPE")
    private String type;
    @Size(max = 20)
    @Column(name = "PARTNER")
    private String partner;
    @Size(max = 20)
    @Column(name = "ACCOUNT_NUMBER")
    private String accountNumber;
    @Size(max = 100)
    @Column(name = "TIN_NUMBER")
    private String tinNumber;
    @Size(max = 200)
    @Column(name = "ADDRESS")
    private String address;
    @Size(max = 200)
    @Column(name = "SPECIALIZATION")
    private String specialization;
    @Column(name = "STATUS")
    private Integer status;

    @JoinColumn(name = "BANK_ID", referencedColumnName = "ID")
    @ManyToOne
    private HrLuBanks bankId;

    @JoinColumn(name = "BRANCH_ID", referencedColumnName = "ID")
    @ManyToOne
    private HrLuBankBranches branchId;

    @OneToMany(mappedBy = "institutionId", cascade = CascadeType.ALL)
    private List<HrLocalMedInvoices> hrLocalMedInvoicesList;

    public HrLocalMedInstitutions() {
    }

    public HrLocalMedInstitutions(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPartner() {
        return partner;
    }

    public void setPartner(String partner) {
        this.partner = partner;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getTinNumber() {
        return tinNumber;
    }

    public void setTinNumber(String tinNumber) {
        this.tinNumber = tinNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public HrLuBanks getBankId() {
        return bankId;
    }

    public void setBankId(HrLuBanks bankId) {
        this.bankId = bankId;
    }

    public HrLuBankBranches getBranchId() {
        return branchId;
    }

    public void setBranchId(HrLuBankBranches branchId) {
        this.branchId = branchId;
    }

    @XmlTransient
    public List<HrLocalMedInvoices> getHrLocalMedInvoicesList() {
        if (hrLocalMedInvoicesList == null) {
            hrLocalMedInvoicesList = new ArrayList<>();
        }
        return hrLocalMedInvoicesList;
    }

    public void setHrLocalMedInvoicesList(List<HrLocalMedInvoices> hrLocalMedInvoicesList) {
        this.hrLocalMedInvoicesList = hrLocalMedInvoicesList;
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
        if (!(object instanceof HrLocalMedInstitutions)) {
            return false;
        }
        HrLocalMedInstitutions other = (HrLocalMedInstitutions) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return name;
    }

    }
