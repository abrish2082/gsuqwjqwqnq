/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.entity.medical;

import java.io.Serializable;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Ob
 */
@Entity
@Table(name = "HR_LOCAL_MED_TREATMENT_TYPE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HrLocalMedTreatmentType.findAll", query = "SELECT h FROM HrLocalMedTreatmentType h"),
    @NamedQuery(name = "HrLocalMedTreatmentType.findById", query = "SELECT h FROM HrLocalMedTreatmentType h WHERE h.id = :id"),
    @NamedQuery(name = "HrLocalMedTreatmentType.findByTreatmentType", query = "SELECT h FROM HrLocalMedTreatmentType h WHERE h.treatmentType = :treatmentType"),
    @NamedQuery(name = "HrLocalMedTreatmentType.findByCompanyShare", query = "SELECT h FROM HrLocalMedTreatmentType h WHERE h.coveredBy = :coveredBy"),
    @NamedQuery(name = "HrLocalMedTreatmentType.findByDescription", query = "SELECT h FROM HrLocalMedTreatmentType h WHERE h.description = :description")})
public class HrLocalMedTreatmentType implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HR_LOCAL_MED_TREATMENT_SEQ")
    @SequenceGenerator(name = "HR_LOCAL_MED_TREATMENT_SEQ", sequenceName = "HR_LOCAL_MED_TREATMENT_SEQ", allocationSize = 1)
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private Integer id;
    @Size(max = 100)
    @Column(name = "TREATMENT_TYPE")
    private String treatmentType;
    @Column(name = "COVERED_BY")
    private String coveredBy;
    @Size(max = 200)
    @Column(name = "DESCRIPTION")
    private String description;
//    @OneToMany(mappedBy = "treatmentTypeId")
//    private List<HrLocalMedInvoiceDetails> hrLocalMedInvoiceDetailsList;

    public HrLocalMedTreatmentType() {
    }

    public HrLocalMedTreatmentType(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTreatmentType() {
        return treatmentType;
    }

    public void setTreatmentType(String treatmentType) {
        this.treatmentType = treatmentType;
    }

    public String getCoveredBy() {
        return coveredBy;
    }

    public void setCoveredBy(String coveredBy) {
        this.coveredBy = coveredBy;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

//    @XmlTransient
//    public List<HrLocalMedInvoiceDetails> getHrLocalMedInvoiceDetailsList() {
//        return hrLocalMedInvoiceDetailsList;
//    }
//
//    public void setHrLocalMedInvoiceDetailsList(List<HrLocalMedInvoiceDetails> hrLocalMedInvoiceDetailsList) {
//        this.hrLocalMedInvoiceDetailsList = hrLocalMedInvoiceDetailsList;
//    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HrLocalMedTreatmentType)) {
            return false;
        }
        HrLocalMedTreatmentType other = (HrLocalMedTreatmentType) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return treatmentType;
    }

}
