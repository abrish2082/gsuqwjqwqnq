/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.mms.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
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

/**
 *
 * @author w_station
 */
@Entity
@Table(name = "LOST_DISPOSAL_DMS_UPLOD")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "LostDisposalDmsUplod.findAll", query = "SELECT l FROM LostDisposalDmsUplod l"),
    @NamedQuery(name = "LostDisposalDmsUplod.findById", query = "SELECT l FROM LostDisposalDmsUplod l WHERE l.id = :id"),
    @NamedQuery(name = "LostDisposalDmsUplod.findByDocId", query = "SELECT l FROM LostDisposalDmsUplod l WHERE l.docId = :docId")})
public class LostDisposalDmsUplod implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id

    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "LOST_DISPOSAL_DMS_UPLOAD_SEQ")
    @SequenceGenerator(name = "LOST_DISPOSAL_DMS_UPLOAD_SEQ", sequenceName = "LOST_DISPOSAL_DMS_UPLOAD_SEQ", allocationSize = 1)

    @Column(name = "ID", nullable = false, precision = 0, scale = -127)
    private BigDecimal id;
    @Column(name = "DOC_ID")
    private Integer docId;
    @JoinColumn(name = "DISP_ID", referencedColumnName = "DISP_ID")
    @ManyToOne
    private MmsDisposal dispId;

    public LostDisposalDmsUplod() {
    }

    public LostDisposalDmsUplod(BigDecimal id) {
        this.id = id;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public Integer getDocId() {
        return docId;
    }

    public void setDocId(Integer docId) {
        this.docId = docId;
    }

    public MmsDisposal getDispId() {
        return dispId;
    }

    public void setDispId(MmsDisposal dispId) {
        this.dispId = dispId;
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
        if (!(object instanceof LostDisposalDmsUplod)) {
            return false;
        }
        LostDisposalDmsUplod other = (LostDisposalDmsUplod) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entity.LostDisposalDmsUplod[ id=" + id + " ]";
    }

}
