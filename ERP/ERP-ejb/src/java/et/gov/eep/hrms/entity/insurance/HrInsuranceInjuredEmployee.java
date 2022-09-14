package et.gov.eep.hrms.entity.insurance;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import et.gov.eep.hrms.entity.employee.HrEmployees;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
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
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author meles
 */
@Entity
@Table(name = "HR_INSURANCE_INJURED_EMPLOYEE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HrInsuranceInjuredEmployee.findAll", query = "SELECT h FROM HrInsuranceInjuredEmployee h"),
    @NamedQuery(name = "HrInsuranceInjuredEmployee.findById", query = "SELECT h FROM HrInsuranceInjuredEmployee h WHERE h.id = :id"),
    @NamedQuery(name = "HrInsuranceInjuredEmployee.findByRegistrationNo", query = "SELECT h FROM HrInsuranceInjuredEmployee h WHERE h.registrationNo = :registrationNo"),
    @NamedQuery(name = "HrInsuranceInjuredEmployee.findByAccidentDate", query = "SELECT h FROM HrInsuranceInjuredEmployee h WHERE h.accidentDate = :accidentDate"),
    @NamedQuery(name = "HrInsuranceInjuredEmployee.findByAccidentPlace", query = "SELECT h FROM HrInsuranceInjuredEmployee h WHERE h.accidentPlace = :accidentPlace"),
    @NamedQuery(name = "HrInsuranceInjuredEmployee.empname", query = "SELECT h FROM HrInsuranceInjuredEmployee h WHERE UPPER(h.empId.firstName)LIKE :empname"),
    @NamedQuery(name = "HrInsuranceInjuredEmployee.findByAccidentInformedDate", query = "SELECT h FROM HrInsuranceInjuredEmployee h WHERE h.accidentInformedDate = :accidentInformedDate"),
    @NamedQuery(name = "HrInsuranceInjuredEmployee.findByAccidentDescription", query = "SELECT h FROM HrInsuranceInjuredEmployee h WHERE h.accidentDescription = :accidentDescription"),
    @NamedQuery(name = "HrInsuranceInjuredEmployee.findByDailyWage", query = "SELECT h FROM HrInsuranceInjuredEmployee h WHERE h.dailyWage = :dailyWage"),
    @NamedQuery(name = "HrInsuranceInjuredEmployee.findByWitness1", query = "SELECT h FROM HrInsuranceInjuredEmployee h WHERE h.witness1 = :witness1"),
    @NamedQuery(name = "HrInsuranceInjuredEmployee.findByWitness2", query = "SELECT h FROM HrInsuranceInjuredEmployee h WHERE h.witness2 = :witness2"),
    @NamedQuery(name = "HrInsuranceInjuredEmployee.findByWitness3", query = "SELECT h FROM HrInsuranceInjuredEmployee h WHERE h.witness3 = :witness3"),
    @NamedQuery(name = "HrInsuranceInjuredEmployee.findByType", query = "SELECT h FROM HrInsuranceInjuredEmployee h WHERE UPPER(h.type)LIKE :type"),
    @NamedQuery(name = "HrInsuranceInjuredEmployee.ttt", query = "SELECT  h FROM HrInsuranceInjuredEmployee h WHERE  h.type =:ttt"),
    @NamedQuery(name = "HrInsuranceInjuredEmployee.findByFullName", query = "SELECT h FROM HrInsuranceInjuredEmployee h WHERE UPPER(h.fullName)LIKE  :fullName"),
    @NamedQuery(name = "HrInsuranceInjuredEmployee.findByDateOfBirth", query = "SELECT h FROM HrInsuranceInjuredEmployee h WHERE h.dateOfBirth = :dateOfBirth"),
    @NamedQuery(name = "HrInsuranceInjuredEmployee.findByJobPosition", query = "SELECT h FROM HrInsuranceInjuredEmployee h WHERE h.jobPosition = :jobPosition"),
    @NamedQuery(name = "HrInsuranceInjuredEmployee.findByStatus", query = "SELECT h FROM HrInsuranceInjuredEmployee h WHERE h.status = :status")})
public class HrInsuranceInjuredEmployee implements Serializable {

    @Column(name = "DAILY_WAGE")
    private BigDecimal dailyWage;
    @OneToMany(mappedBy = "hrInsuranceInjuredEmployee")
    private List<HrInsuranceDiagnosisResult> hrInsuranceDiagnosisResultList;
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HR_INSURANCE_INJURED_EMP_SEQ")
    @SequenceGenerator(name = "HR_INSURANCE_INJURED_EMP_SEQ", sequenceName = "HR_INSURANCE_INJURED_EMP_SEQ", allocationSize = 1)
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private Integer id;
    @Size(max = 20)
    @Column(name = "REGISTRATION_NO")
    private String registrationNo;
    @Size(max = 20)
    @Column(name = "ACCIDENT_DATE")
    private String accidentDate;
    @Size(max = 20)
    @Column(name = "ACCIDENT_PLACE")
    private String accidentPlace;
    @Size(max = 20)
    @Column(name = "ACCIDENT_INFORMED_DATE")
    private String accidentInformedDate;
    @Size(max = 200)
    @Column(name = "ACCIDENT_DESCRIPTION")
    private String accidentDescription;
    @Size(max = 20)
    @Column(name = "WITNESS1")
    private String witness1;
    @Size(max = 20)
    @Column(name = "WITNESS2")
    private String witness2;
    @Size(max = 20)
    @Column(name = "WITNESS3")
    private String witness3;
    @Size(max = 20)
    @Column(name = "TYPE")
    private String type;
    @Size(max = 20)
    @Column(name = "ACCIDENTHAPPENED")
    private String accidentHappened;
    @Size(max = 20)
    @Column(name = "FULL_NAME")
    private String fullName;
    @Size(max = 20)
    @Column(name = "DATE_OF_BIRTH")
    private String dateOfBirth;
    @Size(max = 20)
    @Column(name = "JOB_POSITION")
    private String jobPosition;
    @Size(max = 20)
    @Column(name = "STATUS")
    private String status;
    @Size(max = 20)
    @Column(name = "PREPARED_ON")
    private String preparedon;
    @Size(max = 20)
    @Column(name = "PREPARED_BY")
    private String preparedby;
    @JoinColumn(name = "EMP_ID", referencedColumnName = "ID")
    @ManyToOne
    private HrEmployees empId;
    @JoinColumn(name = "INSURANCE_ID", referencedColumnName = "ID")
    @ManyToOne
    private HrInsurance insuranceId;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "hrInsuranceInjuredEmployee")
    private HrInsuranceDiagnosisResult hrInsuranceDiagnosisResult;

    public HrInsuranceInjuredEmployee() {
    }

    public HrInsuranceInjuredEmployee(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRegistrationNo() {
        return registrationNo;
    }

    public void setRegistrationNo(String registrationNo) {
        this.registrationNo = registrationNo;
    }

    public String getAccidentDate() {
        return accidentDate;
    }

    public void setAccidentDate(String accidentDate) {
        this.accidentDate = accidentDate;
    }

    public String getAccidentPlace() {
        return accidentPlace;
    }

    public void setAccidentPlace(String accidentPlace) {
        this.accidentPlace = accidentPlace;
    }

    public String getAccidentInformedDate() {
        return accidentInformedDate;
    }

    public void setAccidentInformedDate(String accidentInformedDate) {
        this.accidentInformedDate = accidentInformedDate;
    }

    public String getAccidentDescription() {
        return accidentDescription;
    }

    public void setAccidentDescription(String accidentDescription) {
        this.accidentDescription = accidentDescription;
    }

    public BigDecimal getDailyWage() {
        return dailyWage;
    }

    public void setDailyWage(BigDecimal dailyWage) {
        this.dailyWage = dailyWage;
    }

    public String getWitness1() {
        return witness1;
    }

    public void setWitness1(String witness1) {
        this.witness1 = witness1;
    }

    public String getWitness2() {
        return witness2;
    }

    public void setWitness2(String witness2) {
        this.witness2 = witness2;
    }

    public String getWitness3() {
        return witness3;
    }

    public void setWitness3(String witness3) {
        this.witness3 = witness3;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getJobPosition() {
        return jobPosition;
    }

    public void setJobPosition(String jobPosition) {
        this.jobPosition = jobPosition;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public HrEmployees getEmpId() {
        return empId;
    }

    public String getPreparedon() {
        return preparedon;
    }

    public void setPreparedon(String preparedon) {
        this.preparedon = preparedon;
    }

    public String getPreparedby() {
        return preparedby;
    }

    public void setPreparedby(String preparedby) {
        this.preparedby = preparedby;
    }

    public String getAccidentHappened() {
        return accidentHappened;
    }

    public void setAccidentHappened(String accidentHappened) {
        this.accidentHappened = accidentHappened;
    }

    public void setEmpId(HrEmployees empId) {
        this.empId = empId;
    }

    public HrInsurance getInsuranceId() {
        return insuranceId;
    }

    public void setInsuranceId(HrInsurance insuranceId) {
        this.insuranceId = insuranceId;
    }

    public HrInsuranceDiagnosisResult getHrInsuranceDiagnosisResult() {
        return hrInsuranceDiagnosisResult;
    }

    public void setHrInsuranceDiagnosisResult(HrInsuranceDiagnosisResult hrInsuranceDiagnosisResult) {
        this.hrInsuranceDiagnosisResult = hrInsuranceDiagnosisResult;
    }
    @Transient
    private String Col_Value;

    public String getCol_Value() {
        return Col_Value;
    }

    public void setCol_Value(String Col_Value) {
        this.Col_Value = Col_Value;
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
        if (!(object instanceof HrInsuranceInjuredEmployee)) {
            return false;
        }
        HrInsuranceInjuredEmployee other = (HrInsuranceInjuredEmployee) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "et.gov.eep.hrms.entity.address.HrInsuranceInjuredEmployee[ id=" + id + " ]";
    }

    @XmlTransient

    public List<HrInsuranceDiagnosisResult> getHrInsuranceDiagnosisResultList() {
        return hrInsuranceDiagnosisResultList;
    }

    public void setHrInsuranceDiagnosisResultList(List<HrInsuranceDiagnosisResult> hrInsuranceDiagnosisResultList) {
        this.hrInsuranceDiagnosisResultList = hrInsuranceDiagnosisResultList;
    }

}
