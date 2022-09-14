/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.entity.insurance;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author insa
 */
@Entity
@Table(name = "HR_INSURANCE_DIAGNOSIS_RESULT")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HrInsuranceDiagnosisResult.findAll", query = "SELECT h FROM HrInsuranceDiagnosisResult h"),
    @NamedQuery(name = "HrInsuranceDiagnosisResult.findById", query = "SELECT h FROM HrInsuranceDiagnosisResult h WHERE h.id = :id"),
    @NamedQuery(name = "HrInsuranceDiagnosisResult.findbystatus2", query = "SELECT h FROM HrInsuranceDiagnosisResult h WHERE h.hrInsuranceInjuredEmployee.type = :fname AND h.status = :status2"),
    @NamedQuery(name = "HrInsuranceDiagnosisResult.findBystatus", query = "SELECT h FROM HrInsuranceInjuredEmployee h WHERE h.status = :status"),
    @NamedQuery(name = "HrInsuranceDiagnosisResult.findbyfullname", query = "SELECT h FROM HrInsuranceDiagnosisResult h WHERE (h.hrInsuranceInjuredEmployee.type = :fname OR h.hrInsuranceInjuredEmployee.type =:dname) AND h.status = :status"),
    @NamedQuery(name = "HrInsuranceDiagnosisResult.findbydaily", query = "SELECT h FROM HrInsuranceDiagnosisResult h WHERE h.hrInsuranceInjuredEmployee.type = :dname AND h.status = :status"),
    @NamedQuery(name = "HrInsuranceDiagnosisResult.findByHospital", query = "SELECT h FROM HrInsuranceDiagnosisResult h WHERE h.hospital = :hospital"),
    @NamedQuery(name = "HrInsuranceDiagnosisResult.findByInsuranceEmpId", query = "SELECT h FROM HrInsurance h WHERE h.insuranceId.insuranceName = :insuranceName"),
    @NamedQuery(name = "HrInsuranceDiagnosisResult.findByInsuranceInjuredEmpId", query = "SELECT h FROM HrInsuranceDiagnosisResult h WHERE h.hrInsuranceInjuredEmployee = :hrInsuranceInjuredEmployee"),
    @NamedQuery(name = "HrInsuranceDiagnosisResult.findByPhysician", query = "SELECT h FROM HrInsuranceDiagnosisResult h WHERE h.physician = :physician"),
    @NamedQuery(name = "HrInsuranceDiagnosisResult.findByIsDisease", query = "SELECT h FROM HrInsuranceDiagnosisResult h WHERE h.isDisease = :isDisease"),
    @NamedQuery(name = "HrInsuranceDiagnosisResult.findByInsuranceName", query = "SELECT h FROM HrInsuranceDiagnosisResult h WHERE h.hrInsuranceInjuredEmployee.insuranceId.insuranceId.insuranceName = :insuranceName"),
    @NamedQuery(name = "HrInsuranceDiagnosisResult.findByInjuryDisease", query = "SELECT h FROM HrInsuranceDiagnosisResult h WHERE h.injuryDisease = :injuryDisease"),
    @NamedQuery(name = "HrInsuranceDiagnosisResult.findByTreatmentPrescribed", query = "SELECT h FROM HrInsuranceDiagnosisResult h WHERE h.treatmentPrescribed = :treatmentPrescribed"),
    @NamedQuery(name = "HrInsuranceDiagnosisResult.findByOtherDefectDisease", query = "SELECT h FROM HrInsuranceDiagnosisResult h WHERE h.otherDefectDisease = :otherDefectDisease"),
    @NamedQuery(name = "HrInsuranceDiagnosisResult.findByTreatmentDate", query = "SELECT h FROM HrInsuranceDiagnosisResult h WHERE h.treatmentDate = :treatmentDate"),
    @NamedQuery(name = "HrInsuranceDiagnosisResult.findByTime", query = "SELECT h FROM HrInsuranceDiagnosisResult h WHERE h.time = :time"),
    @NamedQuery(name = "HrInsuranceDiagnosisResult.findByHourMinute", query = "SELECT h FROM HrInsuranceDiagnosisResult h WHERE h.type = :type"),
    @NamedQuery(name = "HrInsuranceDiagnosisResult.findBySickLeave", query = "SELECT h FROM HrInsuranceDiagnosisResult h WHERE h.sickLeave = :sickLeave")})
public class HrInsuranceDiagnosisResult implements Serializable {

    @Column(name = "IS_DISEASE")
    private BigDecimal isDisease;
    @Column(name = "SICK_LEAVE")
    private BigDecimal sickLeave;
    @OneToMany(mappedBy = "diagnosisId",cascade = CascadeType.ALL)
    private List<HrInsuranceDiagnosisUpload> hrInsuranceDiagnosisUploadList;

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HR_INSURANCE_DIAG_RESULT_SEQ")
    @SequenceGenerator(name = "HR_INSURANCE_DIAG_RESULT_SEQ", sequenceName = "HR_INSURANCE_DIAG_RESULT_SEQ", allocationSize = 1)
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private Integer id;
    @Column(name = "STATUS")
    private Integer status;
    @Size(max = 100)
    @Column(name = "HOSPITAL")
    private String hospital;
    @Size(max = 100)
    @Column(name = "PHYSICIAN")
    private String physician;
    @Size(max = 200)
    @Column(name = "INJURY_DISEASE")
    private String injuryDisease;
    @Size(max = 200)
    @Column(name = "TREATMENT_PRESCRIBED")
    private String treatmentPrescribed;
    @Size(max = 200)
    @Column(name = "OTHER_DEFECT_DISEASE")
    private String otherDefectDisease;
    @Size(max = 20)
    @Column(name = "TREATMENT_DATE")
    private String treatmentDate;
    @Column(name = "TIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date time;
    @Size(max = 20)
    @Column(name = "TYPE")
    private String type;
    @JoinColumn(name = "INSURANCE_INJURED_EMP_ID", referencedColumnName = "ID")
    @ManyToOne
    private HrInsuranceInjuredEmployee hrInsuranceInjuredEmployee;
    @OneToMany(mappedBy = "diagonasisResultId")
    private List<HrInsurancePayment> hrInsurancePaymentList;

    public HrInsuranceDiagnosisResult() {
    }

    public HrInsuranceDiagnosisResult(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getHospital() {
        return hospital;
    }

    public void setHospital(String hospital) {
        this.hospital = hospital;
    }

    public String getPhysician() {
        return physician;
    }

    public void setPhysician(String physician) {
        this.physician = physician;
    }

    public BigDecimal getIsDisease() {
        return isDisease;
    }

    public void setIsDisease(BigDecimal isDisease) {
        this.isDisease = isDisease;
    }

    public String getInjuryDisease() {
        return injuryDisease;
    }

    public void setInjuryDisease(String injuryDisease) {
        this.injuryDisease = injuryDisease;
    }

    public String getTreatmentPrescribed() {
        return treatmentPrescribed;
    }

    public void setTreatmentPrescribed(String treatmentPrescribed) {
        this.treatmentPrescribed = treatmentPrescribed;
    }

    public String getOtherDefectDisease() {
        return otherDefectDisease;
    }

    public void setOtherDefectDisease(String otherDefectDisease) {
        this.otherDefectDisease = otherDefectDisease;
    }

    public String getTreatmentDate() {
        return treatmentDate;
    }

    public void setTreatmentDate(String treatmentDate) {
        this.treatmentDate = treatmentDate;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public BigDecimal getSickLeave() {
        return sickLeave;
    }

    public void setSickLeave(BigDecimal sickLeave) {
        this.sickLeave = sickLeave;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public HrInsuranceInjuredEmployee getHrInsuranceInjuredEmployee() {
        return hrInsuranceInjuredEmployee;
    }

    public void setHrInsuranceInjuredEmployee(HrInsuranceInjuredEmployee hrInsuranceInjuredEmployee) {
        this.hrInsuranceInjuredEmployee = hrInsuranceInjuredEmployee;
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
        if (!(object instanceof HrInsuranceDiagnosisResult)) {
            return false;
        }
        HrInsuranceDiagnosisResult other = (HrInsuranceDiagnosisResult) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.insurance.HrInsuranceDiagnosisResult[ id=" + id + " ]";
    }

    @XmlTransient

    public List<HrInsurancePayment> getHrInsurancePaymentList() {
        return hrInsurancePaymentList;
    }

    public void setHrInsurancePaymentList(List<HrInsurancePayment> hrInsurancePaymentList) {
        this.hrInsurancePaymentList = hrInsurancePaymentList;
    }

    @XmlTransient
    public List<HrInsuranceDiagnosisUpload> getHrInsuranceDiagnosisUploadList() {
        if (hrInsuranceDiagnosisUploadList == null) {
            hrInsuranceDiagnosisUploadList = new ArrayList<>();
        }
        return hrInsuranceDiagnosisUploadList;
    }

    public void setHrInsuranceDiagnosisUploadList(List<HrInsuranceDiagnosisUpload> hrInsuranceDiagnosisUploadList) {
        this.hrInsuranceDiagnosisUploadList = hrInsuranceDiagnosisUploadList;
    }

    public void Add(HrInsuranceDiagnosisUpload hrDiagnosisUpload) {
        if (hrDiagnosisUpload.getDiagnosisId() != this) {
            this.getHrInsuranceDiagnosisUploadList().add(hrDiagnosisUpload);
            hrDiagnosisUpload.setDiagnosisId(this);
        }
    }
    }
