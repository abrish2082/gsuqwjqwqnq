/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.commonApplications.entity;

import et.gov.eep.hrms.entity.compliantHandling.HrAppealRequestFileUpload;
import et.gov.eep.hrms.entity.displine.HrDisciplineFileUpload;
import et.gov.eep.hrms.entity.termination.HrTerminationRequestUpload;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Abdi
 */
@Entity
@Table(name = "HR_LU_DM_ARCHIVE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HrLuDmArchive.findAll", query = "SELECT h FROM HrLuDmArchive h"),
    @NamedQuery(name = "HrLuDmArchive.findByDocumentId", query = "SELECT h FROM HrLuDmArchive h WHERE h.documentId = :documentId"),
    @NamedQuery(name = "HrLuDmArchive.findByFileName", query = "SELECT h FROM HrLuDmArchive h WHERE h.fileName = :fileName"),
    @NamedQuery(name = "HrLuDmArchive.findByFileType", query = "SELECT h FROM HrLuDmArchive h WHERE h.fileType = :fileType")})
public class HrLuDmArchive implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @GeneratedValue(generator = "HR_LU_DM_ARCHIVE_SEQ", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "HR_LU_DM_ARCHIVE_SEQ", sequenceName = "HR_LU_DM_ARCHIVE_SEQ", allocationSize = 1)
    @Column(name = "DOCUMENT_ID")
    private BigDecimal documentId;
    @Size(max = 200)
    @Column(name = "FILE_NAME")
    private String fileName;
    @Size(max = 35)
    @Column(name = "FILE_TYPE")
    private String fileType;
    @Lob
    @Column(name = "UPLOAD_FILE")
    private byte[] uploadFile;
    @OneToMany(mappedBy = "documentId", cascade = CascadeType.ALL)
    private List<HrAppealRequestFileUpload> hrAppealFileUploadsList;
    @OneToMany(mappedBy = "documentId", cascade = CascadeType.ALL)
    private List<HrDisciplineFileUpload> hrDisciplineFileUploadsList;
    @OneToMany(mappedBy = "documentId")
    private List<HrTerminationRequestUpload> hrTerminationRequestUploadsList;

    public HrLuDmArchive() {
    }

    public HrLuDmArchive(BigDecimal documentId) {
        this.documentId = documentId;
    }

    public BigDecimal getDocumentId() {
        return documentId;
    }

    public void setDocumentId(BigDecimal documentId) {
        this.documentId = documentId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public byte[] getUploadFile() {
        return uploadFile;
    }

    public void setUploadFile(byte[] uploadFile) {
        this.uploadFile = uploadFile;
    }

    @XmlTransient
    public List<HrDisciplineFileUpload> getHrDisciplineFileUploadsList() {
        if (hrDisciplineFileUploadsList == null) {
            hrDisciplineFileUploadsList = new ArrayList<>();
        }
        return hrDisciplineFileUploadsList;
    }

    public void setHrDisciplineFileUploadsList(List<HrDisciplineFileUpload> hrDisciplineFileUploadsList) {
        this.hrDisciplineFileUploadsList = hrDisciplineFileUploadsList;
    }

    @XmlTransient
    public List<HrAppealRequestFileUpload> getHrAppealFileUploadsList() {
        if (hrAppealFileUploadsList == null) {
            hrAppealFileUploadsList = new ArrayList<>();
        }
        return hrAppealFileUploadsList;
    }

    public void setHrAppealFileUploadsList(List<HrAppealRequestFileUpload> hrAppealFileUploadsList) {
        this.hrAppealFileUploadsList = hrAppealFileUploadsList;
    }
    public List<HrTerminationRequestUpload> getHrTerminationRequestUploadsList() {
        if (hrTerminationRequestUploadsList == null) {
            hrTerminationRequestUploadsList = new ArrayList<>();
        }
        return hrTerminationRequestUploadsList;
    }

    public void setHrTerminationRequestUploadsList(List<HrTerminationRequestUpload> hrTerminationRequestUploadsList) {
        this.hrTerminationRequestUploadsList = hrTerminationRequestUploadsList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (documentId != null ? documentId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HrLuDmArchive)) {
            return false;
        }
        HrLuDmArchive other = (HrLuDmArchive) object;
        if ((this.documentId == null && other.documentId != null) || (this.documentId != null && !this.documentId.equals(other.documentId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "et.gov.eep.commonApplications.entity.HrLuDmArchive[ documentId=" + documentId + " ]";
    }

}
