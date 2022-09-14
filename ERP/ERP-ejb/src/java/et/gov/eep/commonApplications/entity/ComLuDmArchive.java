/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.commonApplications.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.annotation.Generated;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author mora1
 */
@Entity
@Table(name = "COM_LU_DM_ARCHIVE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ComLuDmArchive.findAll", query = "SELECT c FROM ComLuDmArchive c"),
    @NamedQuery(name = "ComLuDmArchive.findByDocumentId", query = "SELECT c FROM ComLuDmArchive c WHERE c.documentId = :documentId"),
    @NamedQuery(name = "ComLuDmArchive.findByFileName", query = "SELECT c FROM ComLuDmArchive c WHERE c.fileName = :fileName"),
    @NamedQuery(name = "ComLuDmArchive.findByFileType", query = "SELECT c FROM ComLuDmArchive c WHERE c.fileType = :fileType")})
public class ComLuDmArchive implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @NotNull
    @Id
    @Basic(optional = false)
    @GeneratedValue(generator = "COM_LU_DM_ARCHIVE_SEQ", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "COM_LU_DM_ARCHIVE_SEQ", sequenceName = "COM_LU_DM_ARCHIVE_SEQ", allocationSize = 1)
    @Column(name = "DOCUMENT_ID", nullable = false, precision = 0, scale = -127)
    private Integer documentId;
    @Size(max = 200)
    @Column(name = "FILE_NAME", length = 35)
    private String fileName;
    @Size(max = 35)
    @Column(name = "FILE_TYPE", length = 35)
    private String fileType;
    @Lob
    @Column(name = "UPLOAD_FILE")
    private byte[] uploadFile;

    public ComLuDmArchive() {
    }

    public ComLuDmArchive(Integer documentId) {
        this.documentId = documentId;
    }

    public Integer getDocumentId() {
        return documentId;
    }

    public void setDocumentId(Integer documentId) {
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (documentId != null ? documentId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ComLuDmArchive)) {
            return false;
        }
        ComLuDmArchive other = (ComLuDmArchive) object;
        if ((this.documentId == null && other.documentId != null) || (this.documentId != null && !this.documentId.equals(other.documentId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "et.gov.eep.commonApplications.entity.ComLuDmArchive[ documentId=" + documentId + " ]";
    }

}
