/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.entity.recruitment;

import et.gov.eep.hrms.entity.address.HrAddresses;
import et.gov.eep.hrms.entity.lookup.HrLuEducLevels;
import et.gov.eep.hrms.entity.lookup.HrLuEducTypes;
import java.io.Serializable;
import java.math.BigDecimal;
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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author user
 */
@Entity
@Table(name = "HR_CANDIDIATE_EDUCATIONS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HrCandidiateEducations.findAll", query = "SELECT h FROM HrCandidiateEducations h"),
    @NamedQuery(name = "HrCandidiateEducations.findById", query = "SELECT h FROM HrCandidiateEducations h WHERE h.id = :id"),
    @NamedQuery(name = "HrCandidiateEducations.findByInstitution", query = "SELECT h FROM HrCandidiateEducations h WHERE h.institution = :institution"),
    @NamedQuery(name = "HrCandidiateEducations.findByStartDate", query = "SELECT h FROM HrCandidiateEducations h WHERE h.startDate = :startDate"),
    @NamedQuery(name = "HrCandidiateEducations.findByEndDate", query = "SELECT h FROM HrCandidiateEducations h WHERE h.endDate = :endDate"),
    @NamedQuery(name = "HrCandidiateEducations.findByAward", query = "SELECT h FROM HrCandidiateEducations h WHERE h.award = :award"),
    @NamedQuery(name = "HrCandidiateEducations.findByEducResult", query = "SELECT h FROM HrCandidiateEducations h WHERE h.educResult = :educResult"),
    @NamedQuery(name = "HrCandidiateEducations.findBySponsoredBy", query = "SELECT h FROM HrCandidiateEducations h WHERE h.sponsoredBy = :sponsoredBy")})
public class HrCandidiateEducations implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HR_CANDIDIATE_EDUCATIONS_SEQ")
    @SequenceGenerator(name = "HR_CANDIDIATE_EDUCATIONS_SEQ", sequenceName = "HR_CANDIDIATE_EDUCATIONS_SEQ", allocationSize = 1)
    @Column(name = "ID")
    private BigDecimal id;
    @Column(name = "INSTITUTION")
    private String institution;
    @Column(name = "START_DATE")
    private String startDate;
    @Column(name = "END_DATE")
    private String endDate;
    @Column(name = "AWARD")
    private String award;
    @Column(name = "RESULT_TYPE")
    private String resultType;
    @Column(name = "EDUC_RESULT")
    private Double educResult;
    @Column(name = "SPONSORED_BY")
    private String sponsoredBy;
    @JoinColumn(name = "ADDRESS_ID", referencedColumnName = "ADDRESS_ID")
    @ManyToOne
    private HrAddresses addressId;
    @JoinColumn(name = "CANDIDIATE_ID", referencedColumnName = "ID")
    @ManyToOne
    private HrCandidiates candidiateId;    
    @JoinColumn(name = "EDUC_TYPE_ID", referencedColumnName = "ID")
    @ManyToOne
    private HrLuEducTypes educTypeId;
    @JoinColumn(name = "EDUC_LEVEL_ID", referencedColumnName = "ID")
    @ManyToOne
    private HrLuEducLevels educLevelId;

    public HrCandidiateEducations() {
    }

    public HrCandidiateEducations(BigDecimal id) {
        this.id = id;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
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

    public String getAward() {
        return award;
    }

    public void setAward(String award) {
        this.award = award;
    }

    public String getResultType() {
        return resultType;
    }

    public void setResultType(String resultType) {
        this.resultType = resultType;
    }

    public Double getEducResult() {
        return educResult;
    }

    public void setEducResult(Double educResult) {
        this.educResult = educResult;
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

    public HrCandidiates getCandidiateId() {
        return candidiateId;
    }

    public void setCandidiateId(HrCandidiates candidiateId) {
        this.candidiateId = candidiateId;
    }

    public HrLuEducLevels getEducLevelId() {
        return educLevelId;
    }

    public void setEducLevelId(HrLuEducLevels educLevelId) {
        this.educLevelId = educLevelId;
    }

    public HrLuEducTypes getEducTypeId() {
        return educTypeId;
    }

    public void setEducTypeId(HrLuEducTypes educTypeId) {
        this.educTypeId = educTypeId;
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
        if (!(object instanceof HrCandidiateEducations)) {
            return false;
        }
        HrCandidiateEducations other = (HrCandidiateEducations) object;
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
