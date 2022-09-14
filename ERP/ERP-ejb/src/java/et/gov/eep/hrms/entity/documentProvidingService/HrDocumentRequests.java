/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.entity.documentProvidingService;

import et.gov.eep.commonApplications.entity.WfHrProcessed;
import et.gov.eep.hrms.entity.address.HrAddresses;
import et.gov.eep.hrms.entity.employee.HrEmployees;
import java.io.Serializable;
import java.math.BigDecimal;
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
 * @author Baya
 */
@Entity
@Table(name = "HR_DOCUMENT_REQUESTS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HrDocumentRequests.findAll", query = "SELECT h FROM HrDocumentRequests h"),
    @NamedQuery(name = "HrDocumentRequests.findById", query = "SELECT h FROM HrDocumentRequests h WHERE h.id = :id"),
    @NamedQuery(name = "HrDocumentRequests.findByNoOfCopies", query = "SELECT h FROM HrDocumentRequests h WHERE h.noOfCopies = :noOfCopies"),
    @NamedQuery(name = "HrDocumentRequests.findByRequesterId", query = "SELECT h FROM HrDocumentRequests h WHERE h.requesterId.id = :reqId"),
    @NamedQuery(name = "HrDocumentRequests.findByRequestDate", query = "SELECT h FROM HrDocumentRequests h WHERE h.requestDate = :requestDate"),
    @NamedQuery(name = "HrDocumentRequests.findByEmpIdDocType", query = "SELECT h FROM HrDocumentRequests h WHERE h.requesterId.id = :requesterId and h.documentType.id = :docTypeId "),
    @NamedQuery(name = "HrDocumentRequests.findByReason", query = "SELECT h FROM HrDocumentRequests h WHERE h.reason = :reason"),
    @NamedQuery(name = "HrDocumentRequests.findByRequestorIdAndReqestDate", query = "SELECT h FROM HrDocumentRequests h WHERE h.requesterId = :reqId and h.requestDate=:reqDate"),
    @NamedQuery(name = "HrDocumentRequests.findByStatus", query = "SELECT h FROM HrDocumentRequests h WHERE h.status = :status"),
 @NamedQuery(name = "HrDocumentRequests.findByStatusAndUserID", query = "SELECT h FROM HrDocumentRequests h WHERE h.status = :status and h.ProcessedBy= :UserId")})
public class HrDocumentRequests implements Serializable {

    @OneToMany(mappedBy = "documentId", cascade = CascadeType.ALL)
    private List<WfHrProcessed> WfHrProcessedList;

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HR_DOCUMENT_REQUESTS_SEQ")
    @SequenceGenerator(name = "HR_DOCUMENT_REQUESTS_SEQ", sequenceName = "HR_DOCUMENT_REQUESTS_SEQ", allocationSize = 1)
    private BigDecimal id;
    @Column(name = "NO_OF_COPIES")
    private Integer noOfCopies;
    @Size(max = 20)
    @Column(name = "REQUEST_DATE")
    private String requestDate;
    @Size(max = 20)
    @Column(name = "APPROVE_DATE")
    private String approveDate;
    @Size(max = 200)
    @Column(name = "REASON")
    private String reason;
    @Size(max = 300)
    @Column(name = "APPROVER_REMARK")
    private String remark;
    @Column(name = "STATUS")
    private Integer status;
    @Column(name = "PROCESSED_BY")
    private String ProcessedBy;
    @Column(name = "GUARANTEE_PERSON")
    private String guarantee_person;
    @Column(name = "GUARANTEE_PERSON_ORGANIZATION")
    private String guarantee_person_organization;
    @JoinColumn(name = "ADDRESS_ID", referencedColumnName = "ADDRESS_ID")
    @ManyToOne
    private HrAddresses AddressesId;

    @JoinColumn(name = "DOCUMENT_TYPE", referencedColumnName = "ID")
    @ManyToOne
    private HrDocumentTypes documentType;
    @JoinColumn(name = "REQUESTER_ID", referencedColumnName = "ID")
    @ManyToOne
    private HrEmployees requesterId;

    //<editor-fold defaultstate="collapsed" desc="getter and setter">
    public HrDocumentRequests() {
    }

    public HrDocumentRequests(BigDecimal id) {
        this.id = id;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public Integer getNoOfCopies() {
        return noOfCopies;
    }

    public void setNoOfCopies(Integer noOfCopies) {
        this.noOfCopies = noOfCopies;
    }

    public String getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(String requestDate) {
        this.requestDate = requestDate;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getProcessedBy() {
        return ProcessedBy;
    }

    public void setProcessedBy(String ProcessedBy) {
        this.ProcessedBy = ProcessedBy;
    }

    public HrDocumentTypes getDocumentType() {
        return documentType;
    }

    public void setDocumentType(HrDocumentTypes documentType) {
        this.documentType = documentType;
    }

    public HrEmployees getRequesterId() {
        return requesterId;
    }

    public void setRequesterId(HrEmployees requesterId) {
        this.requesterId = requesterId;
    }

    public String getApproveDate() {
        return approveDate;
    }

    public void setApproveDate(String approveDate) {
        this.approveDate = approveDate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getGuarantee_person() {
        return guarantee_person;
    }

    public void setGuarantee_person(String guarantee_person) {
        this.guarantee_person = guarantee_person;
    }

    public String getGuarantee_person_organization() {
        return guarantee_person_organization;
    }

    public void setGuarantee_person_organization(String guarantee_person_organization) {
        this.guarantee_person_organization = guarantee_person_organization;
    }

    public HrAddresses getAddressesId() {
        return AddressesId;
    }

    public void setAddressesId(HrAddresses AddressesId) {
        this.AddressesId = AddressesId;
    }

//</editor-fold>
    @XmlTransient

    public List<WfHrProcessed> getWfHrProcessedList() {
        return WfHrProcessedList;
    }

    public void setWfHrProcessedList(List<WfHrProcessed> WfHrProcessedList) {
        this.WfHrProcessedList = WfHrProcessedList;
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
        if (!(object instanceof HrDocumentRequests)) {
            return false;
        }
        HrDocumentRequests other = (HrDocumentRequests) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return id.toString();
    }

}
