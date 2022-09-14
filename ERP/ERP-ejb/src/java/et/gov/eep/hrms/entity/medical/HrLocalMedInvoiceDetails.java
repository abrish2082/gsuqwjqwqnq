/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.entity.medical;

import et.gov.eep.hrms.entity.employee.HrEmployees;
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
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author INSA
 */
@Entity
@Table(name = "HR_LOCAL_MED_INVOICE_DETAILS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HrLocalMedInvoiceDetails.findAll", query = "SELECT h FROM HrLocalMedInvoiceDetails h"),
    @NamedQuery(name = "HrLocalMedInvoiceDetails.findById", query = "SELECT h FROM HrLocalMedInvoiceDetails h WHERE h.id = :id"),
    @NamedQuery(name = "HrLocalMedInvoiceDetails.findByAmount", query = "SELECT h FROM HrLocalMedInvoiceDetails h WHERE h.amount = :amount"),
    @NamedQuery(name = "HrLocalMedInvoiceDetails.findByTreatmentDate", query = "SELECT h FROM HrLocalMedInvoiceDetails h WHERE h.treatmentDate = :treatmentDate")})
public class HrLocalMedInvoiceDetails implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HR_LOCAL_MED_INVOICEDETAIL_SEQ")
    @SequenceGenerator(name = "HR_LOCAL_MED_INVOICEDETAIL_SEQ", sequenceName = "HR_LOCAL_MED_INVOICEDETAIL_SEQ", allocationSize = 1)
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private BigDecimal id;
    @Column(name = "AMOUNT")
    private double amount;
    @Column(name = "DESCRIPTION")
    private String description;
    @Column(name = "TREATMENT_DATE")
    private String treatmentDate;

    @JoinColumn(name = "EMP_ID", referencedColumnName = "ID")
    @ManyToOne
    private HrEmployees empId;

    @JoinColumn(name = "INVOICE_ID", referencedColumnName = "ID")
    @ManyToOne
    private HrLocalMedInvoices invoiceId;

    @JoinColumn(name = "TREATMENT_TYPE_ID", referencedColumnName = "ID")
    @ManyToOne
    private HrLocalMedTreatmentType treatmentTypeId;

    public HrLocalMedInvoiceDetails() {
    }

    public HrLocalMedInvoiceDetails(BigDecimal id) {
        this.id = id;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTreatmentDate() {
        return treatmentDate;
    }

    public void setTreatmentDate(String treatmentDate) {
        this.treatmentDate = treatmentDate;
    }

    public HrEmployees getEmpId() {
        return empId;
    }

    public void setEmpId(HrEmployees empId) {
        this.empId = empId;
    }

    public HrLocalMedInvoices getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(HrLocalMedInvoices invoiceId) {
        this.invoiceId = invoiceId;
    }

    public HrLocalMedTreatmentType getTreatmentTypeId() {
        return treatmentTypeId;
    }

    public void setTreatmentTypeId(HrLocalMedTreatmentType treatmentTypeId) {
        this.treatmentTypeId = treatmentTypeId;
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
        if (!(object instanceof HrLocalMedInvoiceDetails)) {
            return false;
        }
        HrLocalMedInvoiceDetails other = (HrLocalMedInvoiceDetails) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.HrLocalMedInvoiceDetails[ id=" + id + " ]";
    }

    @Transient
    private double companyShare;
    @Transient
    private double employeeShare;

    public double getCompanyShare() {
        companyShare = amount * 90 / 100;
        return companyShare;
    }

    public void setCompanyShare(double companyShare) {
        this.companyShare = companyShare;
    }

    public double getEmployeeShare() {
        employeeShare = amount * 10 / 100;
        return employeeShare;
    }

    public void setEmployeeShare(double employeeShare) {
        this.employeeShare = employeeShare;
    }

}
