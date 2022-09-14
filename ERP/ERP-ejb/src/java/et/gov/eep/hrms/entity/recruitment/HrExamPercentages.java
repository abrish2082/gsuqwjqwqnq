/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.entity.recruitment;

import java.io.Serializable;
import java.math.BigDecimal;
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
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Benin
 */
@Entity
@Table(name = "HR_EXAM_PERCENTAGES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HrExamPercentages.findAll", query = "SELECT h FROM HrExamPercentages h"),
    @NamedQuery(name = "HrExamPercentages.findById", query = "SELECT h FROM HrExamPercentages h WHERE h.id = :id"),
    @NamedQuery(name = "HrExamPercentages.findDuplicateEntry", query = "SELECT h FROM HrExamPercentages h WHERE h.batchCode = :batchCode"),
    @NamedQuery(name = "HrExamPercentages.findByBatchCode", query = "SELECT h FROM HrExamPercentages h WHERE h.batchCode = :batchCode"),
    @NamedQuery(name = "HrExamPercentages.findByBatchCodeLike", query = "SELECT h FROM HrExamPercentages h WHERE UPPER(h.batchCode) Like :batchCode"),
    @NamedQuery(name = "HrExamPercentages.findByWrittenPercentage", query = "SELECT h FROM HrExamPercentages h WHERE h.writtenPercentage = :writtenPercentage"),
    @NamedQuery(name = "HrExamPercentages.findByInterviewPercentage", query = "SELECT h FROM HrExamPercentages h WHERE h.interviewPercentage = :interviewPercentage"),
    @NamedQuery(name = "HrExamPercentages.findByPracticalPercentage", query = "SELECT h FROM HrExamPercentages h WHERE h.practicalPercentage = :practicalPercentage"),
    @NamedQuery(name = "HrExamPercentages.findByOtherPercentage", query = "SELECT h FROM HrExamPercentages h WHERE h.otherPercentage = :otherPercentage"),
    @NamedQuery(name = "HrExamPercentages.findByCgpaPercentage", query = "SELECT h FROM HrExamPercentages h WHERE h.cgpaPercentage = :cgpaPercentage")})
public class HrExamPercentages implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HR_EXAM_PERCENTAGES_SEQ")
    @SequenceGenerator(name = "HR_EXAM_PERCENTAGES_SEQ", sequenceName = "HR_EXAM_PERCENTAGES_SEQ", allocationSize = 1)
    @Column(name = "ID")
    private BigDecimal id;
    @Column(name = "BATCH_CODE")
    private Integer batchCode;
    @Column(name = "WRITTEN_PERCENTAGE")
    private String writtenPercentage;
    @Column(name = "INTERVIEW_PERCENTAGE")
    private String interviewPercentage;
    @Column(name = "PRACTICAL_PERCENTAGE")
    private String practicalPercentage;
    @Column(name = "OTHER_PERCENTAGE")
    private String otherPercentage;
    @Column(name = "CGPA_PERCENTAGE")
    private String cgpaPercentage;
    @Column(name = "EXPERIENCE_PERCENTAGE")
    private String experiencePercentage;
    @Column(name = "PREPARED_BY")
    private Integer preparedBy;
    @Column(name = "PREPARED_ON")
    private String preparedOn;
    
    public HrExamPercentages() {
    }

    //<editor-fold defaultstate="collapsed" desc="getters & setters">
    public HrExamPercentages(BigDecimal id) {
        this.id = id;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public Integer getBatchCode() {
        return batchCode;
    }

    public void setBatchCode(Integer batchCode) {
        this.batchCode = batchCode;
    }

    public String getWrittenPercentage() {
        return writtenPercentage;
    }

    public void setWrittenPercentage(String writtenPercentage) {
        this.writtenPercentage = writtenPercentage;
    }

    public String getInterviewPercentage() {
        return interviewPercentage;
    }

    public void setInterviewPercentage(String interviewPercentage) {
        this.interviewPercentage = interviewPercentage;
    }

    public String getPracticalPercentage() {
        return practicalPercentage;
    }

    public void setPracticalPercentage(String practicalPercentage) {
        this.practicalPercentage = practicalPercentage;
    }

    public String getOtherPercentage() {
        return otherPercentage;
    }

    public void setOtherPercentage(String otherPercentage) {
        this.otherPercentage = otherPercentage;
    }

    public String getCgpaPercentage() {
        return cgpaPercentage;
    }

    public void setCgpaPercentage(String cgpaPercentage) {
        this.cgpaPercentage = cgpaPercentage;
    }

    public String getExperiencePercentage() {
        return experiencePercentage;
    }

    public void setExperiencePercentage(String experiencePercentage) {
        this.experiencePercentage = experiencePercentage;
    }

    public Integer getPreparedBy() {
        return preparedBy;
    }

    public void setPreparedBy(Integer preparedBy) {
        this.preparedBy = preparedBy;
    }

    public String getPreparedOn() {
        return preparedOn;
    }

    public void setPreparedOn(String preparedOn) {
        this.preparedOn = preparedOn;
    }
    //</editor-fold>

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HrExamPercentages)) {
            return false;
        }
        HrExamPercentages other = (HrExamPercentages) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "et.gov.eep.hrms.entity.recruitment.HrExamPercentages[ id=" + id + " ]";
    }

    //<editor-fold defaultstate="collapsed" desc="transient">
    @Transient
    Float totalPercentage;

    //<editor-fold defaultstate="collapsed" desc="getters & setters">
    public Float getTotalPercentage() {
        return totalPercentage;
    }

    public void setTotalPercentage(Float totalPercentage) {
        this.totalPercentage = totalPercentage;
    }
//</editor-fold>
//</editor-fold>

}
