/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.entity.recruitment;

import et.gov.eep.hrms.entity.address.HrAddresses;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author user
 */
@Entity
@Table(name = "HR_CANDIDATE_TRAININGS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HrCandidateTrainings.findAll", query = "SELECT h FROM HrCandidateTrainings h"),
    @NamedQuery(name = "HrCandidateTrainings.findById", query = "SELECT h FROM HrCandidateTrainings h WHERE h.id = :id"),
    @NamedQuery(name = "HrCandidateTrainings.findByTrainingTitle", query = "SELECT h FROM HrCandidateTrainings h WHERE h.trainingTitle = :trainingTitle"),
    @NamedQuery(name = "HrCandidateTrainings.findByInstitution", query = "SELECT h FROM HrCandidateTrainings h WHERE h.institution = :institution"),
    @NamedQuery(name = "HrCandidateTrainings.findByStartDate", query = "SELECT h FROM HrCandidateTrainings h WHERE h.startDate = :startDate"),
    @NamedQuery(name = "HrCandidateTrainings.findByEndDate", query = "SELECT h FROM HrCandidateTrainings h WHERE h.endDate = :endDate"),
    @NamedQuery(name = "HrCandidateTrainings.findBySponsoredBy", query = "SELECT h FROM HrCandidateTrainings h WHERE h.sponsoredBy = :sponsoredBy")})
public class HrCandidateTrainings implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HR_CANDIDATE_TRAININGS_SEQ")
    @SequenceGenerator(name = "HR_CANDIDATE_TRAININGS_SEQ", sequenceName = "HR_CANDIDATE_TRAININGS_SEQ", allocationSize = 1)
    @Column(name = "ID")
    private BigDecimal id;
    @Column(name = "TRAINING_TITLE")
    private String trainingTitle;
    @Column(name = "INSTITUTION")
    private String institution;
    @Column(name = "START_DATE")
    private String startDate;
    @Column(name = "END_DATE")
    private String endDate;
    @Column(name = "SPONSORED_BY")
    private String sponsoredBy;
    @JoinColumn(name = "ADDRESS_ID", referencedColumnName = "ADDRESS_ID")
    @ManyToOne
    private HrAddresses addressId;
    @JoinColumn(name = "CANDIDATE_ID", referencedColumnName = "ID")
    @ManyToOne
    private HrCandidiates candidateId;

    public HrCandidateTrainings() {
    }

    public HrCandidateTrainings(BigDecimal id) {
        this.id = id;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public String getTrainingTitle() {
        return trainingTitle;
    }

    public void setTrainingTitle(String trainingTitle) {
        this.trainingTitle = trainingTitle;
    }

    public String getInstitution() {
        return institution;
    }

    public void setInstitution(String institution) {
        this.institution = institution;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getSponsoredBy() {
        return sponsoredBy;
    }

    public void setSponsoredBy(String sponsoredBy) {
        this.sponsoredBy = sponsoredBy;
    }

    public HrAddresses getAddressId() {
        return addressId;
    }

    public void setAddressId(HrAddresses addressId) {
        this.addressId = addressId;
    }

    public HrCandidiates getCandidateId() {
        return candidateId;
    }

    public void setCandidateId(HrCandidiates candidateId) {
        this.candidateId = candidateId;
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
        if (!(object instanceof HrCandidateTrainings)) {
            return false;
        }
        HrCandidateTrainings other = (HrCandidateTrainings) object;
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
