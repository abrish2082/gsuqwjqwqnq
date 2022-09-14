/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.entity.recruitment;

import et.gov.eep.hrms.entity.lookup.HrLuLanguages;
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
@Table(name = "HR_CANDIDATE_LANGUAGES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HrCandidateLanguages.findAll", query = "SELECT h FROM HrCandidateLanguages h"),
    @NamedQuery(name = "HrCandidateLanguages.findById", query = "SELECT h FROM HrCandidateLanguages h WHERE h.id = :id"),
    @NamedQuery(name = "HrCandidateLanguages.findBySpeaking", query = "SELECT h FROM HrCandidateLanguages h WHERE h.speaking = :speaking"),
    @NamedQuery(name = "HrCandidateLanguages.findByReading", query = "SELECT h FROM HrCandidateLanguages h WHERE h.reading = :reading"),
    @NamedQuery(name = "HrCandidateLanguages.findByWriting", query = "SELECT h FROM HrCandidateLanguages h WHERE h.writing = :writing"),
    @NamedQuery(name = "HrCandidateLanguages.findByListening", query = "SELECT h FROM HrCandidateLanguages h WHERE h.listening = :listening")})
public class HrCandidateLanguages implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HR_CANDIDATE_LANGUAGES_SEQ")
    @SequenceGenerator(name = "HR_CANDIDATE_LANGUAGES_SEQ", sequenceName = "HR_CANDIDATE_LANGUAGES_SEQ", allocationSize = 1)
    @Column(name = "ID")
    private BigDecimal id;
    @Size(max = 20)
    @Column(name = "SPEAKING")
    private String speaking;
    @Size(max = 20)
    @Column(name = "READING")
    private String reading;
    @Size(max = 20)
    @Column(name = "WRITING")
    private String writing;
    @Size(max = 20)
    @Column(name = "LISTENING")
    private String listening;
    @JoinColumn(name = "CANDIDATE_ID", referencedColumnName = "ID")
    @ManyToOne
    private HrCandidiates candidateId;
    @JoinColumn(name = "LANGUAGE_ID", referencedColumnName = "ID")
    @ManyToOne
    private HrLuLanguages languageId;

    public HrCandidateLanguages() {
    }

    public HrCandidateLanguages(BigDecimal id) {
        this.id = id;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public String getSpeaking() {
        return speaking;
    }

    public void setSpeaking(String speaking) {
        this.speaking = speaking;
    }

    public String getReading() {
        return reading;
    }

    public void setReading(String reading) {
        this.reading = reading;
    }

    public String getWriting() {
        return writing;
    }

    public void setWriting(String writing) {
        this.writing = writing;
    }

    public String getListening() {
        return listening;
    }

    public void setListening(String listening) {
        this.listening = listening;
    }

    public HrCandidiates getCandidateId() {
        return candidateId;
    }

    public void setCandidateId(HrCandidiates candidateId) {
        this.candidateId = candidateId;
    }

    public HrLuLanguages getLanguageId() {
        return languageId;
    }

    public void setLanguageId(HrLuLanguages languageId) {
        this.languageId = languageId;
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
        if (!(object instanceof HrCandidateLanguages)) {
            return false;
        }
        HrCandidateLanguages other = (HrCandidateLanguages) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "et.gov.eep.hrms.entity.recruitment.HrCandidateLanguages[ id=" + id + " ]";
    }

}
