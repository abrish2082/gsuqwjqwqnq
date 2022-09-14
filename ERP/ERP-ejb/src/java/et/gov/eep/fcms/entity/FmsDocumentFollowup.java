/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.entity;

import et.gov.eep.fcms.entity.Vocher.FmsVoucher;
import java.io.Serializable;
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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author AB
 */
@Entity
@Table(name = "fms_document_followup" )
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FmsDocumentFollowup.findAll", query = "SELECT f FROM FmsDocumentFollowup f"),
    @NamedQuery(name = "FmsDocumentFollowup.findByDocumentReference", query = "SELECT f FROM FmsDocumentFollowup f WHERE f.documentReference = :documentReference"),
    @NamedQuery(name = "FmsDocumentFollowup.findByType", query = "SELECT f FROM FmsDocumentFollowup f WHERE f.type = :type"),
    @NamedQuery(name = "FmsDocumentFollowup.findByLastStatus", query = "SELECT f FROM FmsDocumentFollowup f WHERE f.lastStatus = :lastStatus"),
    @NamedQuery(name = "FmsDocumentFollowup.findByRemark", query = "SELECT f FROM FmsDocumentFollowup f WHERE f.remark = :remark"),
    @NamedQuery(name = "FmsDocumentFollowup.findByFmsDocumentFollowup", query = "SELECT f FROM FmsDocumentFollowup f WHERE f.fmsDocumentFollowup = :fmsDocumentFollowup"),
    @NamedQuery(name = "FmsDocumentFollowup.findByVoucherId", query = "SELECT f FROM FmsDocumentFollowup f WHERE f.voucherId = :voucherId")})
public class FmsDocumentFollowup implements Serializable {
    private static final long serialVersionUID = 1L;
    @Size(max = 45)
    @Column(name = "DOCUMENT_REFERENCE", length = 45)
    private String documentReference;
    @Size(max = 45)
    @Column(length = 45,name="TYPE")
    
    private String type;
    @Column(name = "LAST_STATUS")
    private Integer lastStatus;
    @Size(max = 45)
    
    @Column(length = 45 ,name = "REMARK")
      private String remark;
    @Id
   @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FMS_DOCUMENT_FOLLOWUP_FMS__SEQ")
    @SequenceGenerator(name = "FMS_DOCUMENT_FOLLOWUP_FMS__SEQ", sequenceName = "FMS_DOCUMENT_FOLLOWUP_FMS__SEQ", allocationSize = 1)
    @Basic(optional = false)
    @Column(name = "fms_document_followup", nullable = false)
    private Integer fmsDocumentFollowup;
    @JoinColumn(name = "VOUCHER_ID", referencedColumnName = "VOUCHER_ID", nullable = false)
    @ManyToOne(optional = false)
    private FmsVoucher voucherId;

    /**
     *
     */
    public FmsDocumentFollowup() {
    }

    /**
     *
     * @param fmsDocumentFollowup
     */
    public FmsDocumentFollowup(Integer fmsDocumentFollowup) {
        this.fmsDocumentFollowup = fmsDocumentFollowup;
    }

    /**
     *
     * @return
     */
    public FmsVoucher getVoucherId() {
        if(voucherId==null)
        {
            voucherId=new FmsVoucher();
        }
        return voucherId;
    }

    /**
     *
     * @param voucherId
     */
    public void setVoucherId(FmsVoucher voucherId) {
        this.voucherId = voucherId;
    }

    /**
     *
     * @return
     */
    public String getDocumentReference() {
        return documentReference;
    }

    /**
     *
     * @param documentReference
     */
    public void setDocumentReference(String documentReference) {
        this.documentReference = documentReference;
    }

    /**
     *
     * @return
     */
    public String getType() {
        return type;
    }

    /**
     *
     * @param type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     *
     * @return
     */
    public Integer getLastStatus() {
        return lastStatus;
    }

    /**
     *
     * @param lastStatus
     */
    public void setLastStatus(Integer lastStatus) {
        this.lastStatus = lastStatus;
    }

    /**
     *
     * @return
     */
    public String getRemark() {
        return remark;
    }

    /**
     *
     * @param remark
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     *
     * @return
     */
    public Integer getFmsDocumentFollowup() {
        return fmsDocumentFollowup;
    }

    /**
     *
     * @param fmsDocumentFollowup
     */
    public void setFmsDocumentFollowup(Integer fmsDocumentFollowup) {
        this.fmsDocumentFollowup = fmsDocumentFollowup;
    }

   

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (fmsDocumentFollowup != null ? fmsDocumentFollowup.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FmsDocumentFollowup)) {
            return false;
        }
        FmsDocumentFollowup other = (FmsDocumentFollowup) object;
        if ((this.fmsDocumentFollowup == null && other.fmsDocumentFollowup != null) || (this.fmsDocumentFollowup != null && !this.fmsDocumentFollowup.equals(other.fmsDocumentFollowup))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "et.gov.insa.erp.ibfms.entity.FmsDocumentFollowup[ fmsDocumentFollowup=" + fmsDocumentFollowup + " ]";
    }
    
}
