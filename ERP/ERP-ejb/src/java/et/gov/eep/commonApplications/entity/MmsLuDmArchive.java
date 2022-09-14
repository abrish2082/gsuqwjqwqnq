/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.commonApplications.entity;

import et.gov.eep.mms.entity.IfrsLease;
import et.gov.eep.mms.entity.MmsDisposal;
import et.gov.eep.mms.entity.MmsStockDisposal;
import et.gov.eep.mms.entity.MmsStockItemLost;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Basic;
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
import org.codehaus.groovy.ast.GenericsType;

/**
 *
 * @author insa
 */
@Entity
@Table(name = "MMS_LU_DM_ARCHIVE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MmsLuDmArchive.findAll", query = "SELECT m FROM MmsLuDmArchive m"),
    @NamedQuery(name = "MmsLuDmArchive.findByDocumentId", query = "SELECT m FROM MmsLuDmArchive m WHERE m.documentId = :documentId"),
    @NamedQuery(name = "MmsLuDmArchive.findByFileName", query = "SELECT m FROM MmsLuDmArchive m WHERE m.fileName = :fileName"),
    @NamedQuery(name = "MmsLuDmArchive.findByFileType", query = "SELECT m FROM MmsLuDmArchive m WHERE m.fileType = :fileType")})
public class MmsLuDmArchive implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @GeneratedValue(generator = "MMS_LU_DM_ARCHIVE_SEQ", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "MMS_LU_DM_ARCHIVE_SEQ", sequenceName = "MMS_LU_DM_ARCHIVE_SEQ", allocationSize = 1)
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

    @OneToMany(mappedBy = "referenceNumber")
    private List<MmsDisposal> mmsDisposalList;

    @OneToMany(mappedBy = "referenceNumber")
    private List<MmsStockDisposal> mmsDispList;
    @OneToMany(mappedBy = "referenceNumber"   )
    private List<MmsStockItemLost> mmsStockItemLostlists;
    @OneToMany(mappedBy = "referenceNumber")
    private List<IfrsLease> IfrsLeaseslists;

    public MmsLuDmArchive() {
    }

    public MmsLuDmArchive(BigDecimal documentId) {
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
    public List<MmsDisposal> getMmsDisposalList() {
        if (mmsDisposalList == null) {
            mmsDisposalList = new ArrayList<>();
        }
        return mmsDisposalList;
    }

    public void setMmsDisposalList(List<MmsDisposal> mmsDisposalList) {
        this.mmsDisposalList = mmsDisposalList;
    }

    @XmlTransient
    public List<MmsStockDisposal> getMmsStockDisposalsList() {
        if (mmsDispList == null) {
            mmsDispList = new ArrayList<>();

        }
        return mmsDispList;
    }

    public void setMmsStockDisposalsList(List<MmsStockDisposal> mmsDispList) {
        this.mmsDispList = mmsDispList;
    }

    @XmlTransient
    public List<MmsStockItemLost> getMmsStockItemLostlists() {
        if (mmsStockItemLostlists == null) {
            mmsStockItemLostlists = new ArrayList<>();
        }
        return mmsStockItemLostlists;
    }

    public void setMmsStockItemLostlists(List<MmsStockItemLost> mmsStockItemLostlists) {
        this.mmsStockItemLostlists = mmsStockItemLostlists;
    }

    @XmlTransient
    public List<IfrsLease> getIfrsLeaseslists() {
        if (IfrsLeaseslists == null) {
            IfrsLeaseslists = new ArrayList<>();           
        }
        return IfrsLeaseslists;
    }

    public void setIfrsLeaseslists(List<IfrsLease> IfrsLeaseslists) {
        this.IfrsLeaseslists = IfrsLeaseslists;
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
        if (!(object instanceof MmsLuDmArchive)) {
            return false;
        }
        MmsLuDmArchive other = (MmsLuDmArchive) object;
        if ((this.documentId == null && other.documentId != null) || (this.documentId != null && !this.documentId.equals(other.documentId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "et.gov.eep.commonApplications.entity.MmsLuDmArchive[ documentId=" + documentId + " ]";
    }

}
