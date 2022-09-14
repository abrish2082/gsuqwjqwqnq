/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.entity.salarydelegation;

import et.gov.eep.hrms.entity.employee.HrEmpFamilies;
import et.gov.eep.hrms.entity.employee.HrEmployees;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author INSA
 */
@Entity
@Table(name = "HR_SALARY_DELEGATION_REQUEST")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HrSalaryDelegationRequest.findByEmpId", query = "SELECT DISTINCT(h) FROM HrSalaryDelegationRequest h join h.empId g where g.id = :id"),

    @NamedQuery(name = "HrSalaryDelegationRequest.findAll", query = "SELECT h FROM HrSalaryDelegationRequest h WHERE h.status =0"),
    @NamedQuery(name = "HrSalaryDelegationRequest.findByFirstName", query = "SELECT h FROM HrSalaryDelegationRequest h WHERE UPPER(h.empId.empId) LIKE :empId and h.status =0"),
    @NamedQuery(name = "HrSalaryDelegationRequest.findByName", query = "SELECT h FROM HrSalaryDelegationRequest h WHERE UPPER(h.empId.firstName) LIKE :firstName and h.status =0"),
    @NamedQuery(name = "HrSalaryDelegationRequest.findByTwo", query = "SELECT h FROM HrSalaryDelegationRequest h WHERE UPPER(h.empId.firstName) LIKE :firstName and UPPER(h.empId.empId) LIKE :empId and h.status =0"),
//    @NamedQuery(name = "HrSalaryDelegationRequest.findByFamilyName", query = "SELECT h FROM HrSalaryDelegationRequest   UPPER(h.firstName) LIKE :firstName      h WHERE h.familyId.firstName = :firstName and h.status =0"),
    @NamedQuery(name = "Families.findByFirstName", query = "SELECT h FROM HrEmpFamilies h WHERE h.firstName = :firstName"),
    @NamedQuery(name = "Families.findByFirstNameLike", query = "SELECT h FROM HrEmpFamilies h WHERE UPPER(h.firstName) LIKE :firstName"),
    @NamedQuery(name = "HrSalaryDelegationRequest.findByStatus", query = "SELECT h FROM HrSalaryDelegationRequest h WHERE h.status = :status"),
    @NamedQuery(name = "HrSalaryDelegationRequest.findByStatusApprove", query = "SELECT h FROM HrSalaryDelegationRequest h WHERE h.status != :status"),
    @NamedQuery(name = "HrSalaryDelegationRequest.findByFirstNameLike", query = "SELECT h FROM HrSalaryDelegationRequest h WHERE h.firstName LIKE :firstName"),
    @NamedQuery(name = "HrSalaryDelegationRequest.findByFName", query = "SELECT h FROM HrSalaryDelegationRequest h WHERE h.firstName = :firstName"),
    @NamedQuery(name = "HrSalaryDelegationRequest.findByMiddleName", query = "SELECT h FROM HrSalaryDelegationRequest h WHERE h.middleName = :middleName"),
    @NamedQuery(name = "HrSalaryDelegationRequest.findByLastName", query = "SELECT h FROM HrSalaryDelegationRequest h WHERE h.lastName = :lastName"),
    @NamedQuery(name = "HrSalaryDelegationRequest.findByOrganization", query = "SELECT h FROM HrSalaryDelegationRequest h WHERE h.organization = :organization"),
    @NamedQuery(name = "HrSalaryDelegationRequest.findByAddress", query = "SELECT h FROM HrSalaryDelegationRequest h WHERE h.address = :address"),
    @NamedQuery(name = "HrSalaryDelegationRequest.findByDateOfDelegated", query = "SELECT h FROM HrSalaryDelegationRequest h WHERE h.dateOfDelegated = :dateOfDelegated"),
    @NamedQuery(name = "HrSalaryDelegationRequest.findByEndOfDelegated", query = "SELECT h FROM HrSalaryDelegationRequest h WHERE h.endOfDelegated = :endOfDelegated"),
    @NamedQuery(name = "HrSalaryDelegationRequest.findByAmount", query = "SELECT h FROM HrSalaryDelegationRequest h WHERE h.amount = :amount"),
    @NamedQuery(name = "HrSalaryDelegationRequest.findByTelPhone", query = "SELECT h FROM HrSalaryDelegationRequest h WHERE h.telPhone = :telPhone"),
    @NamedQuery(name = "HrSalaryDelegationRequest.findById", query = "SELECT h FROM HrSalaryDelegationRequest h WHERE h.id = :id"),
    @NamedQuery(name = "HrSalaryDelegationRequest.findByFax", query = "SELECT h FROM HrSalaryDelegationRequest h WHERE h.fax = :fax"),
    @NamedQuery(name = "HrSalaryDelegationRequest.findByRemark", query = "SELECT h FROM HrSalaryDelegationRequest h WHERE h.remark = :remark"),
    @NamedQuery(name = "HrSalaryDelegationRequest.findByIdentificationType", query = "SELECT h FROM HrSalaryDelegationRequest h WHERE h.identificationType = :identificationType"),
    @NamedQuery(name = "HrSalaryDelegationRequest.findBySex", query = "SELECT h FROM HrSalaryDelegationRequest h WHERE h.sex = :sex"),
//    @NamedQuery(name = "HrSalaryDelegationRequest.findByName", query = "SELECT h FROM HrSalaryDelegationRequest h WHERE h.empId.firstName LIKE :firstName"),
    @NamedQuery(name = "HrSalaryDelegationRequest.findByDelegateFrom", query = "SELECT h FROM HrSalaryDelegationRequest h WHERE h.delegateFrom = :delegateFrom")})
public class HrSalaryDelegationRequest implements Serializable {

    private static final long serialVersionUID = 1L;
    @Size(max = 20)
    @Column(name = "FIRST_NAME")
    private String firstName;
    @Size(max = 20)
    @Column(name = "MIDDLE_NAME")
    private String middleName;
    @Size(max = 20)
    @Column(name = "LAST_NAME")
    private String lastName;
    @Size(max = 20)
    @Column(name = "ORGANIZATION")
    private String organization;
    @Size(max = 20)
    @Column(name = "ADDRESS")
    private String address;
    @Column(name = "DATE_OF_DELEGATED")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateOfDelegated;
    @Column(name = "END_OF_DELEGATED")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endOfDelegated;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "AMOUNT")
    private Double amount;
    @Column(name = "TEL_PHONE")
    private BigInteger telPhone;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HR_SALARY_DELEGATION_SEQ")
    @SequenceGenerator(name = "HR_SALARY_DELEGATION_SEQ", sequenceName = "HR_SALARY_DELEGATION_SEQ", allocationSize = 1)
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private Integer id;
    @Column(name = "IDENTIFICATION_NUM")
    private String identificationNum;
    @Column(name = "FAX")
    private BigInteger fax;
    @Size(max = 100)
    @Column(name = "REMARK")
    private String remark;
    @Size(max = 20)
    @Column(name = "IDENTIFICATION_TYPE")
    private String identificationType;
    @Size(max = 20)
    @Column(name = "SEX")
    private String sex;
    @Size(max = 100)
    @Column(name = "DELEGATE_TYPE")
    private String delegateType;
    @Size(max = 100)
    @Column(name = "DELEGATE_FROM")
    private String delegateFrom;
    @Lob
    @Column(name = "DATA_ATTACHED")
    private Serializable dataAttached;
    @Column(name = "STATUS")
    private Integer status;
    @Size(max = 100)
    @Column(name = "REASON")
    private String reason;
    @Size(max = 20)
    @Column(name = "APPROVED_BY")
    private String approvedBy;
    @Size(max = 20)
    @Column(name = "DECISION")
    private String decision;
    @JoinColumn(name = "EMP_ID", referencedColumnName = "ID")
    @ManyToOne(cascade = CascadeType.PERSIST)
    private HrEmployees empId;
    @JoinColumn(name = "DELEGATE_ID", referencedColumnName = "ID")
    @ManyToOne(cascade = CascadeType.PERSIST)
    private HrEmployees delegateId;
    @JoinColumn(name = "FAMILY_ID", referencedColumnName = "ID")
    @ManyToOne(cascade = CascadeType.PERSIST)
    private HrEmpFamilies familyId;

    public HrSalaryDelegationRequest() {
    }

    public HrSalaryDelegationRequest(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getDateOfDelegated() {
        return dateOfDelegated;
    }

    public void setDateOfDelegated(Date dateOfDelegated) {
        this.dateOfDelegated = dateOfDelegated;
    }

    public Date getEndOfDelegated() {
        return endOfDelegated;
    }

    public void setEndOfDelegated(Date endOfDelegated) {
        this.endOfDelegated = endOfDelegated;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public BigInteger getTelPhone() {
        return telPhone;
    }

    public void setTelPhone(BigInteger telPhone) {
        this.telPhone = telPhone;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigInteger getFax() {
        return fax;
    }

    public void setFax(BigInteger fax) {
        this.fax = fax;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(String approvedBy) {
        this.approvedBy = approvedBy;
    }

    public String getDecision() {
        return decision;
    }

    public void setDecision(String decision) {
        this.decision = decision;
    }

    public String getIdentificationType() {
        return identificationType;
    }

    public void setIdentificationType(String identificationType) {
        this.identificationType = identificationType;
    }

    public String getIdentificationNum() {
        return identificationNum;
    }

    public void setIdentificationNum(String identificationNum) {
        this.identificationNum = identificationNum;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getDelegateType() {
        return delegateType;
    }

    public void setDelegateType(String delegateType) {
        this.delegateType = delegateType;
    }

    public String getDelegateFrom() {
        return delegateFrom;
    }

    public void setDelegateFrom(String delegateFrom) {
        this.delegateFrom = delegateFrom;
    }

    public Serializable getDataAttached() {
        return dataAttached;
    }

    public void setDataAttached(Serializable dataAttached) {
        this.dataAttached = dataAttached;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public HrEmployees getEmpId() {
        return empId;
    }

    public void setEmpId(HrEmployees empId) {
        this.empId = empId;
    }

    public HrEmployees getDelegateId() {
        return delegateId;
    }

    public void setDelegateId(HrEmployees delegateId) {
        this.delegateId = delegateId;
    }

    public HrEmpFamilies getFamilyId() {
        return familyId;
    }

    public void setFamilyId(HrEmpFamilies familyId) {
        this.familyId = familyId;
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
        if (!(object instanceof HrSalaryDelegationRequest)) {
            return false;
        }
        HrSalaryDelegationRequest other = (HrSalaryDelegationRequest) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return id + "--" + empId.getFirstName() + " " + empId.getMiddleName();
    }

    public void setDateOfDelegated(String toendOfDel) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
