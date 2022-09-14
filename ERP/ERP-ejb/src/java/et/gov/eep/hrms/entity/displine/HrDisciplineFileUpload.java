/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.entity.displine;

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
import javax.xml.bind.annotation.XmlRootElement;
import et.gov.eep.commonApplications.entity.HrLuDmArchive;

/**
 *
 * @author Abdi_Pc
 */
@Entity
@Table(name = "HR_DISCIPLINE_FILE_UPLOAD")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HrDisciplineFileUpload.findAll", query = "SELECT h FROM HrDisciplineFileUpload h"),
    @NamedQuery(name = "HrDisciplineFileUpload.findById", query = "SELECT h FROM HrDisciplineFileUpload h WHERE h.id = :id"),
    @NamedQuery(name = "HrDisciplineFileUpload.findByDisciplineCaseId", query = "SELECT h FROM HrDisciplineFileUpload h WHERE h.disciplineCaseId = :disciplineCaseId")})
public class HrDisciplineFileUpload implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @NotNull
    @Basic(optional = false)
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HR_DISCIPLINE_FILE_UPLOAD_SEQ")
    @SequenceGenerator(name = "HR_DISCIPLINE_FILE_UPLOAD_SEQ", sequenceName = "HR_DISCIPLINE_FILE_UPLOAD_SEQ", allocationSize = 1)
    @Column(name = "ID")
    private BigDecimal id;
    @JoinColumn(name = "DOCUMENT_ID", referencedColumnName = "DOCUMENT_ID")
    @ManyToOne
    private HrLuDmArchive documentId;
    @JoinColumn(name = "DISCIPLINE_CASE_ID", referencedColumnName = "ID")
    @ManyToOne
    private HrDisciplineRequests disciplineCaseId;

    public HrDisciplineFileUpload() {
    }

    public HrDisciplineFileUpload(BigDecimal id) {
        this.id = id;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public HrLuDmArchive getDocumentId() {
        return documentId;
    }

    public void setDocumentId(HrLuDmArchive documentId) {
        this.documentId = documentId;
    }

    public HrDisciplineRequests getDisciplineCaseId() {
        return disciplineCaseId;
    }

    public void setDisciplineCaseId(HrDisciplineRequests disciplineCaseId) {
        this.disciplineCaseId = disciplineCaseId;
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
        if (!(object instanceof HrDisciplineFileUpload)) {
            return false;
        }
        HrDisciplineFileUpload other = (HrDisciplineFileUpload) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "et.gov.eep.hrms.entity.displine.HrDisciplineFileUpload[ id=" + id + " ]";
//        return id.toString();
    }

}
