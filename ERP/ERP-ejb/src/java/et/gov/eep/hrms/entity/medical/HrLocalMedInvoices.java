/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.entity.medical;

import et.gov.eep.commonApplications.entity.WfHrProcessed;
import et.gov.eep.hrms.entity.employee.HrEmployees;
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
 * @author Ob
 */
@Entity
@Table(name = "HR_LOCAL_MED_INVOICES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HrLocalMedInvoices.findAll", query = "SELECT h FROM HrLocalMedInvoices h WHERE h.status = 0"),
    @NamedQuery(name = "HrLocalMedInvoices.findById", query = "SELECT h FROM HrLocalMedInvoices h WHERE h.id = :id"),

    @NamedQuery(name = "HrLocalMedInvoices.findByInstitutionName", query = "SELECT h FROM HrLocalMedInvoices h WHERE h.institutionId.name = :name and h.status = 0"),
    @NamedQuery(name = "HrLocalMedInvoices.findByInstitutionType", query = "SELECT DISTINCT (h.type) FROM HrLocalMedInstitutions h WHERE h.type = :type and h.status = 0"),
    @NamedQuery(name = "HrLocalMedInvoices.findByInvoiceOrRefNumber", query = "SELECT h FROM HrLocalMedInvoices h WHERE h.invoiceOrRefNumber = :invoiceOrRefNumber"),
    @NamedQuery(name = "HrLocalMedInvoices.findByInvoiceDate", query = "SELECT h FROM HrLocalMedInvoices h WHERE h.invoiceDate = :invoiceDate"),
    @NamedQuery(name = "HrLocalMedInvoices.findByPreparedOn", query = "SELECT h FROM HrLocalMedInvoices h WHERE h.preparedOn = :preparedOn"),
    @NamedQuery(name = "HrLocalMedInvoices.findByRemark", query = "SELECT h FROM HrLocalMedInvoices h WHERE h.remark = :remark"),
    @NamedQuery(name = "HrLocalMedInvoices.findByStatus", query = "SELECT h FROM HrLocalMedInvoices h WHERE h.status = :status"),
    @NamedQuery(name = "HrLocalMedInvoices.findPreparedList", query = "SELECT h FROM HrLocalMedInvoices h WHERE h.status = 0 ORDER BY h.preparedOn"),
    @NamedQuery(name = "HrLocalMedInvoices.findByApprovedDate", query = "SELECT h FROM HrLocalMedInvoices h WHERE h.approvedDate = :approvedDate")})
public class HrLocalMedInvoices implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HR_LOCAL_MED_INVOICES_SEQ")
    @SequenceGenerator(name = "HR_LOCAL_MED_INVOICES_SEQ", sequenceName = "HR_LOCAL_MED_INVOICES_SEQ", allocationSize = 1)
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private Integer id;
    @Size(max = 20)
    @Column(name = "INVOICE_OR_REF_NUMBER")
    private String invoiceOrRefNumber;
    @Size(max = 20)
    @Column(name = "INVOICE_DATE")
    private String invoiceDate;
    @Size(max = 20)
    @Column(name = "PREPARED_ON")
    private String preparedOn;
    @Size(max = 200)
    @Column(name = "REMARK")
    private String remark;
    @Column(name = "STATUS")
    private Integer status;
    @Size(max = 20)
    @Column(name = "REFERENCE_NO")
    private String referenceNo;
    @Column(name = "APPROVED_DATE")
    private String approvedDate;
    @Column(name = "PREPARED_BY")
    private int preparedBy;

    @OneToMany(mappedBy = "invoiceId", cascade = CascadeType.ALL)
    private List<HrLocalMedInvoiceDetails> hrLocalMedInvoiceDetailsList = new ArrayList<>();

//    @JoinColumn(name = "PREPARED_BY", referencedColumnName = "ID")
//    @ManyToOne
//    private HrEmployees preparedBy;
    @JoinColumn(name = "INSTITUTION_ID", referencedColumnName = "ID")
    @ManyToOne
    private HrLocalMedInstitutions institutionId;

    @OneToMany(mappedBy = "medicalCreditBillId", cascade = CascadeType.ALL)
    private List<WfHrProcessed> wfHrProcessedList = new ArrayList<>();

    public HrLocalMedInvoices() {
    }

    public HrLocalMedInvoices(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getInvoiceOrRefNumber() {
        return invoiceOrRefNumber;
    }

    public void setInvoiceOrRefNumber(String invoiceOrRefNumber) {
        this.invoiceOrRefNumber = invoiceOrRefNumber;
    }

    public String getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(String invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public String getPreparedOn() {
        return preparedOn;
    }

    public void setPreparedOn(String preparedOn) {
        this.preparedOn = preparedOn;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getApprovedDate() {
        return approvedDate;
    }

    public void setApprovedDate(String approvedDate) {
        this.approvedDate = approvedDate;
    }

    public String getReferenceNo() {
        return referenceNo;
    }

    public void setReferenceNo(String referenceNo) {
        this.referenceNo = referenceNo;
    }

    @XmlTransient
    public List<HrLocalMedInvoiceDetails> getHrLocalMedInvoiceDetailsList() {
        if (hrLocalMedInvoiceDetailsList == null) {
            hrLocalMedInvoiceDetailsList = new ArrayList<>();
        }
        return hrLocalMedInvoiceDetailsList;
    }

    public void setHrLocalMedInvoiceDetailsList(List<HrLocalMedInvoiceDetails> hrLocalMedInvoiceDetailsList) {
        this.hrLocalMedInvoiceDetailsList = hrLocalMedInvoiceDetailsList;
    }

    public void addInvoiceDetail(HrLocalMedInvoiceDetails hrLocalMedInvoiceDetails) {
        if (hrLocalMedInvoiceDetails.getInvoiceId() != this) {
            this.getHrLocalMedInvoiceDetailsList().add(hrLocalMedInvoiceDetails);
            hrLocalMedInvoiceDetails.setInvoiceId(this);
        }
    }

    public int getPreparedBy() {
        return preparedBy;
    }

    public void setPreparedBy(int preparedBy) {
        this.preparedBy = preparedBy;
    }

//    public HrEmployees getPreparedBy() {
//        return preparedBy;
//    }
//
//    public void setPreparedBy(HrEmployees preparedBy) {
//        this.preparedBy = preparedBy;
//    }
    public HrLocalMedInstitutions getInstitutionId() {
        return institutionId;
    }

    public void setInstitutionId(HrLocalMedInstitutions institutionId) {
        this.institutionId = institutionId;
    }

    @XmlTransient
    public List<WfHrProcessed> getWfHrProcessedList() {
        return wfHrProcessedList;
    }

    public void setWfHrProcessedList(List<WfHrProcessed> wfHrProcessedList) {
        this.wfHrProcessedList = wfHrProcessedList;
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
        if (!(object instanceof HrLocalMedInvoices)) {
            return false;
        }
        HrLocalMedInvoices other = (HrLocalMedInvoices) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return institutionId.getName();
    }

}
