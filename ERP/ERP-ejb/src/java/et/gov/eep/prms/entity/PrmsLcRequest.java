 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.prms.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author user
 */
@Entity
@Table(name = "PRMS_LC_REQUEST")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PrmsLcRequest.findAll", query = "SELECT p FROM PrmsLcRequest p"),
    @NamedQuery(name = "PrmsLcRequest.findByRequestid", query = "SELECT p FROM PrmsLcRequest p WHERE p.requestid = :requestid"),
    @NamedQuery(name = "PrmsLcRequest.findByRequestIdLike", query = "SELECT p FROM PrmsLcRequest p WHERE p.requestid LIKE :requestid"),
    @NamedQuery(name = "PrmsLcRequest.findByRequestReason", query = "SELECT p FROM PrmsLcRequest p WHERE p.requestReason = :requestReason"),
    @NamedQuery(name = "PrmsLcRequest.findByItemservicedescription", query = "SELECT p FROM PrmsLcRequest p WHERE p.itemservicedescription = :itemservicedescription"),
    @NamedQuery(name = "PrmsLcRequest.findByContractno", query = "SELECT p FROM PrmsLcRequest p WHERE p.contractno = :contractno"),
    @NamedQuery(name = "PrmsLcRequest.findBySuppliername", query = "SELECT p FROM PrmsLcRequest p WHERE p.suppliername = :suppliername"),
    @NamedQuery(name = "PrmsLcRequest.findByEnduser", query = "SELECT p FROM PrmsLcRequest p WHERE p.enduser = :enduser"),
    @NamedQuery(name = "PrmsLcRequest.findByPreparedby", query = "SELECT p FROM PrmsLcRequest p WHERE p.preparedby = :preparedby"),
    @NamedQuery(name = "PrmsLcRequest.findByPreparedremark", query = "SELECT p FROM PrmsLcRequest p WHERE p.preparedremark = :preparedremark"),
    @NamedQuery(name = "PrmsLcRequest.findByRefno", query = "SELECT p FROM PrmsLcRequest p WHERE p.refno = :refno"),
    @NamedQuery(name = "PrmsLcRequest.findByDateprepared", query = "SELECT p FROM PrmsLcRequest p WHERE p.dateprepared = :dateprepared"),
    @NamedQuery(name = "PrmsLcRequest.findByDateapproved", query = "SELECT p FROM PrmsLcRequest p WHERE p.dateapproved = :dateapproved"),
    @NamedQuery(name = "PrmsLcRequest.findByCopyto", query = "SELECT p FROM PrmsLcRequest p WHERE p.copyto = :copyto"),
    @NamedQuery(name = "PrmsLcRequest.findByCarboncopyto", query = "SELECT p FROM PrmsLcRequest p WHERE p.carboncopyto = :carboncopyto"),
    @NamedQuery(name = "PrmsLcRequest.findByContractamount", query = "SELECT p FROM PrmsLcRequest p WHERE p.contractamount = :contractamount"),
    @NamedQuery(name = "PrmsLcRequest.findByAmouninword", query = "SELECT p FROM PrmsLcRequest p WHERE p.amouninword = :amouninword"),
    @NamedQuery(name = "PrmsLcRequest.findByApprovedby", query = "SELECT p FROM PrmsLcRequest p WHERE p.approvedby = :approvedby"),
    @NamedQuery(name = "PrmsLcRequest.findByApproverremark", query = "SELECT p FROM PrmsLcRequest p WHERE p.approverremark = :approverremark"),
    @NamedQuery(name = "PrmsLcRequest.findByMaxId", query = "SELECT p FROM PrmsLcRequest p WHERE p.requestid =(SELECT Max(p.requestid)  from PrmsLcRequest p)"),
    @NamedQuery(name = "PrmsLcRequest.findById", query = "SELECT p FROM PrmsLcRequest p WHERE p.id = :id")})
public class PrmsLcRequest implements Serializable {

    private static final long serialVersionUID = 1L;
    @Size(max = 50)
    @Column(name = "REQUESTID")
    private String requestid;
    @Size(max = 20)
    @Column(name = "REQUEST_REASON")
    private String requestReason;
    @Size(max = 20)
    @Column(name = "ITEMSERVICEDESCRIPTION")
    private String itemservicedescription;
    @Size(max = 20)
    @Column(name = "CONTRACTNO")
    private String contractno;
    @Size(max = 20)
    @Column(name = "SUPPLIERNAME")
    private String suppliername;
    @Size(max = 20)
    @Column(name = "ENDUSER")
    private String enduser;
    @Size(max = 20)
    @Column(name = "PREPAREDBY")
    private String preparedby;
    @Size(max = 20)
    @Column(name = "PREPAREDREMARK")
    private String preparedremark;
    @Size(max = 20)
    @Column(name = "REFNO")
    private String refno;
    @Column(name = "DATEPREPARED")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateprepared;
    @Column(name = "DATEAPPROVED")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateapproved;
    @Size(max = 20)
    @Column(name = "COPYTO")
    private String copyto;
    @Size(max = 20)
    @Column(name = "CARBONCOPYTO")
    private String carboncopyto;
    @Size(max = 20)
    @Column(name = "CONTRACTAMOUNT")
    private String contractamount;
    @Size(max = 20)
    @Column(name = "AMOUNINWORD")
    private String amouninword;
    @Size(max = 20)
    @Column(name = "APPROVEDBY")
    private String approvedby;
    @Size(max = 20)
    @Column(name = "APPROVERREMARK")
    private String approverremark;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)

    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PRMS_LC_REQUEST_SEQ")
    @SequenceGenerator(name = "PRMS_LC_REQUEST_SEQ", sequenceName = "PRMS_LC_REQUEST_SEQ", allocationSize = 1)
    private BigDecimal id;
    @JoinColumn(name = "CONTRACT_ID", referencedColumnName = "CONTRACT_ID")
    @ManyToOne
    private PrmsContract contractId;
    @JoinColumn(name = "SERVICE_PRO_ID", referencedColumnName = "SERVICE_PRO_ID")
    @ManyToOne
    private PrmsServiceProvider serviceProId;

    public PrmsLcRequest() {
    }

    public PrmsLcRequest(BigDecimal id) {
        this.id = id;
    }

    public String getRequestid() {
        return requestid;
    }

    public void setRequestid(String requestid) {
        this.requestid = requestid;
    }

    public String getRequestReason() {
        return requestReason;
    }

    public void setRequestReason(String requestReason) {
        this.requestReason = requestReason;
    }

    public String getItemservicedescription() {
        return itemservicedescription;
    }

    public void setItemservicedescription(String itemservicedescription) {
        this.itemservicedescription = itemservicedescription;
    }

    public String getContractno() {
        return contractno;
    }

    public void setContractno(String contractno) {
        this.contractno = contractno;
    }

    public String getSuppliername() {
        return suppliername;
    }

    public void setSuppliername(String suppliername) {
        this.suppliername = suppliername;
    }

    public String getEnduser() {
        return enduser;
    }

    public void setEnduser(String enduser) {
        this.enduser = enduser;
    }

    public String getPreparedby() {
        return preparedby;
    }

    public void setPreparedby(String preparedby) {
        this.preparedby = preparedby;
    }

    public String getPreparedremark() {
        return preparedremark;
    }

    public void setPreparedremark(String preparedremark) {
        this.preparedremark = preparedremark;
    }

    public String getRefno() {
        return refno;
    }

    public void setRefno(String refno) {
        this.refno = refno;
    }

    public Date getDateprepared() {
        return dateprepared;
    }

    public void setDateprepared(Date dateprepared) {
        this.dateprepared = dateprepared;
    }

    public Date getDateapproved() {
        return dateapproved;
    }

    public void setDateapproved(Date dateapproved) {
        this.dateapproved = dateapproved;
    }

    public String getCopyto() {
        return copyto;
    }

    public void setCopyto(String copyto) {
        this.copyto = copyto;
    }

    public String getCarboncopyto() {
        return carboncopyto;
    }

    public void setCarboncopyto(String carboncopyto) {
        this.carboncopyto = carboncopyto;
    }

    public String getContractamount() {
        return contractamount;
    }

    public void setContractamount(String contractamount) {
        this.contractamount = contractamount;
    }

    public String getAmouninword() {
        return amouninword;
    }

    public void setAmouninword(String amouninword) {
        this.amouninword = amouninword;
    }

    public String getApprovedby() {
        return approvedby;
    }

    public void setApprovedby(String approvedby) {
        this.approvedby = approvedby;
    }

    public String getApproverremark() {
        return approverremark;
    }

    public void setApproverremark(String approverremark) {
        this.approverremark = approverremark;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public PrmsContract getContractId() {
        return contractId;
    }

    public void setContractId(PrmsContract contractId) {
        this.contractId = contractId;
    }

    public PrmsServiceProvider getServiceProId() {
        return serviceProId;
    }

    public void setServiceProId(PrmsServiceProvider serviceProId) {
        this.serviceProId = serviceProId;
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
        if (!(object instanceof PrmsLcRequest)) {
            return false;
        }
        PrmsLcRequest other = (PrmsLcRequest) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return requestid;
    }

}
