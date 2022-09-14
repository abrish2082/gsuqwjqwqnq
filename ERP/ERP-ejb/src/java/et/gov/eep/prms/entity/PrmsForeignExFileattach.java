/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.prms.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.annotation.Generated;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
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
@Table(name = "PRMS_FOREIGN_EX_FILEATTACH")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PrmsForeignExFileattach.findAll", query = "SELECT p FROM PrmsForeignExFileattach p"),
    @NamedQuery(name = "PrmsForeignExFileattach.findByFileId", query = "SELECT p FROM PrmsForeignExFileattach p WHERE p.fileId = :fileId"),
    @NamedQuery(name = "PrmsForeignExFileattach.findByReferenceNo", query = "SELECT p FROM PrmsForeignExFileattach p WHERE p.referenceNo = :referenceNo")})
public class PrmsForeignExFileattach implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
//    @GeneratedValue(generator = "", strategy = GenerationType.SEQUENCE)
//    @SequenceGenerator(name = "", sequenceName = "", allocationSize = 1)
    @Column(name = "FILE_ID", nullable = false, precision = 0, scale = -127)
    private BigDecimal fileId;
    @Size(max = 100)
    @Column(name = "REFERENCE_NO", length = 100)
    private String referenceNo;
    @Lob
    @Column(name = "FILES")
    private Serializable files;
    @JoinColumn(name = "FOREIGN_EXCHANGE_ID", referencedColumnName = "ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private PrmsForeignExchange foreignExchangeId;

    public PrmsForeignExFileattach() {
    }

    public PrmsForeignExFileattach(BigDecimal fileId) {
        this.fileId = fileId;
    }

    public BigDecimal getFileId() {
        return fileId;
    }

    public void setFileId(BigDecimal fileId) {
        this.fileId = fileId;
    }

    public String getReferenceNo() {
        return referenceNo;
    }

    public void setReferenceNo(String referenceNo) {
        this.referenceNo = referenceNo;
    }

    public Serializable getFiles() {
        return files;
    }

    public void setFiles(Serializable files) {
        this.files = files;
    }

    public PrmsForeignExchange getForeignExchangeId() {
        return foreignExchangeId;
    }

    public void setForeignExchangeId(PrmsForeignExchange foreignExchangeId) {
        this.foreignExchangeId = foreignExchangeId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (fileId != null ? fileId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PrmsForeignExFileattach)) {
            return false;
        }
        PrmsForeignExFileattach other = (PrmsForeignExFileattach) object;
        if ((this.fileId == null && other.fileId != null) || (this.fileId != null && !this.fileId.equals(other.fileId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "et.gov.eep.prms.entity.PrmsForeignExFileattach[ fileId=" + fileId + " ]";
    }

}
