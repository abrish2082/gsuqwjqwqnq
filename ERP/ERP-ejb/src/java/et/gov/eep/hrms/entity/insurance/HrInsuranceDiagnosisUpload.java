/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.entity.insurance;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
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
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author insa
 */
@Entity
@Table(name = "HR_INSURANCE_DIAGNOSIS_UPLOAD")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HrInsuranceDiagnosisUpload.findAll", query = "SELECT h FROM HrInsuranceDiagnosisUpload h"),
    @NamedQuery(name = "HrInsuranceDiagnosisUpload.findById", query = "SELECT h FROM HrInsuranceDiagnosisUpload h WHERE h.id = :id"),
    @NamedQuery(name = "HrInsuranceDiagnosisUpload.findByDocId", query = "SELECT h FROM HrInsuranceDiagnosisUpload h WHERE h.docId = :docId")})
public class HrInsuranceDiagnosisUpload implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HR_DIAGNOSIS_UPLOAD_SEQ")
    @SequenceGenerator(name = "HR_DIAGNOSIS_UPLOAD_SEQ", sequenceName = "HR_DIAGNOSIS_UPLOAD_SEQ", allocationSize = 1)
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private BigDecimal id;
    @Column(name = "DOC_ID")
    private Integer docId;
    @JoinColumn(name = "DIAGNOSIS_ID", referencedColumnName = "ID")
    @ManyToOne
    private HrInsuranceDiagnosisResult diagnosisId;

    public HrInsuranceDiagnosisUpload() {
    }

    public HrInsuranceDiagnosisUpload(BigDecimal id) {
        this.id = id;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public Integer getDocId() {
        return docId;
    }

    public void setDocId(Integer docId) {
        this.docId = docId;
    }

    public HrInsuranceDiagnosisResult getDiagnosisId() {
        return diagnosisId;
    }

    public void setDiagnosisId(HrInsuranceDiagnosisResult diagnosisId) {
        this.diagnosisId = diagnosisId;
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
        if (!(object instanceof HrInsuranceDiagnosisUpload)) {
            return false;
        }
        HrInsuranceDiagnosisUpload other = (HrInsuranceDiagnosisUpload) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "et.gov.eep.hrms.entity.insurance.HrInsuranceDiagnosisUpload[ id=" + id + " ]";
    }

}
