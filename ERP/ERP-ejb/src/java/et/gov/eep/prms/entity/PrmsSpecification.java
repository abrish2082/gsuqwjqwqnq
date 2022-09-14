/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.prms.entity;

import et.gov.eep.mms.entity.MmsItemRegistration;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author pc
 */
@Entity
@Table(name = "PRMS_SPECIFICATION")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PrmsSpecification.findAll", query = "SELECT p FROM PrmsSpecification p"),
    @NamedQuery(name = "PrmsSpecification.findBySpecificationId", query = "SELECT p FROM PrmsSpecification p WHERE p.specificationId = :specificationId"),
    @NamedQuery(name = "PrmsSpecification.findByRegistrationDate", query = "SELECT p FROM PrmsSpecification p WHERE p.registrationDate = :registrationDate"),
    @NamedQuery(name = "PrmsSpecification.searchBySpecificationNo", query = "SELECT p FROM PrmsSpecification p WHERE p.specficationNo LIKE :specficationNo ORDER BY P.specificationId ASC"),
    @NamedQuery(name = "PrmsSpecification.searchBySpecificationbid", query = "SELECT p FROM PrmsSpecification p WHERE p.bidDetId.itemRegId.matName=:bidDetId"),
    @NamedQuery(name = "PrmsSpecification.searchByMatName", query = "SELECT p FROM PrmsSpecification p WHERE p.bidDetId.itemRegId.matName=:matName"),
    @NamedQuery(name = "PrmsSpecification.findByPreparedBy", query = "SELECT p FROM PrmsSpecification p WHERE p.preparedBy = :preparedBy"),
    @NamedQuery(name = "PrmsSpecification.findByBidDetIdBy", query = "SELECT p FROM PrmsSpecification p WHERE p.bidDetId.itemRegId.matCode = :bidDetId"),
    @NamedQuery(name = "PrmsSpecification.findByBidDetId", query = "SELECT p FROM PrmsSpecification p WHERE p.bidDetId.itemRegId.matName = :matName"),
    @NamedQuery(name = "PrmsSpecification.findBySpecficationNo", query = "SELECT p FROM PrmsSpecification p WHERE p.specficationNo = :specficationNo"),
    @NamedQuery(name = "PrmsSpecification.findByMaxCheckListNum", query = "SELECT p FROM PrmsSpecification p WHERE p.specificationId = (SELECT Max(c.specificationId)  from PrmsSpecification c)"),

    @NamedQuery(name = "PrmsSpecification.findByRemark", query = "SELECT p FROM PrmsSpecification p WHERE p.remark = :remark"),

    @NamedQuery(name = "PrmsSpecification.findBySpecification", query = "SELECT p FROM PrmsSpecification p WHERE p.specification = :specification")})
public class PrmsSpecification implements Serializable {

    @Size(max = 100)
    @Column(name = "SPECFICATION_NO")
    private String specficationNo;

    @JoinColumn(name = "BID_DET_ID", referencedColumnName = "ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private PrmsBidDetail bidDetId;

    @JoinColumn(name = "BID_ID", referencedColumnName = "ID")
    @ManyToOne
    private PrmsBid bidId;
    @JoinColumn(name = "MATERIAL_ID", referencedColumnName = "MATERIAL_ID")
    @ManyToOne
    private MmsItemRegistration materialId;
    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PRMS_SPECFICATION_SEQUENCE")
    @SequenceGenerator(name = "PRMS_SPECFICATION_SEQUENCE", sequenceName = "PRMS_SPECFICATION_SEQUENCE", allocationSize = 1)
    @Column(name = "SPECIFICATION_ID")
    private BigDecimal specificationId;
    @Column(name = "REGISTRATION_DATE")
    @Temporal(TemporalType.DATE)
    private Date registrationDate;
    @Size(max = 100)
    @Column(name = "PREPARED_BY")
    private String preparedBy;
    @Size(max = 100)
    @Column(name = "REMARK")
    private String remark;
    @Size(max = 500)
    @Column(name = "SPECIFICATION")
    private String specification;

    public PrmsSpecification() {
    }

    /**
     *
     * @param specificationId
     */
    public PrmsSpecification(BigDecimal specificationId) {
        this.specificationId = specificationId;
    }

    /**
     *
     * @param specificationId
     * @param registrationDate
     */
    public PrmsSpecification(BigDecimal specificationId, Date registrationDate) {
        this.specificationId = specificationId;
        this.registrationDate = registrationDate;
    }

    /**
     *
     * @return
     */
    public BigDecimal getSpecificationId() {
        return specificationId;
    }

    /**
     *
     * @param specificationId
     */
    public void setSpecificationId(BigDecimal specificationId) {
        this.specificationId = specificationId;
    }

    /**
     *
     * @return
     */
    public Date getRegistrationDate() {
        return registrationDate;
    }

    public PrmsBid getBidId() {
        return bidId;
    }

    public void setBidId(PrmsBid bidId) {
        this.bidId = bidId;
    }

    /**
     *
     * @param registrationDate
     */
    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    /**
     *
     * @return
     */
    public String getPreparedBy() {
        return preparedBy;
    }

    /**
     *
     * @param preparedBy
     */
    public void setPreparedBy(String preparedBy) {
        this.preparedBy = preparedBy;
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
    public String getSpecification() {
        return specification;
    }

    /**
     *
     * @param specification
     */
    public void setSpecification(String specification) {
        this.specification = specification;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (specificationId != null ? specificationId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PrmsSpecification)) {
            return false;
        }
        PrmsSpecification other = (PrmsSpecification) object;
        if ((this.specificationId == null && other.specificationId != null) || (this.specificationId != null && !this.specificationId.equals(other.specificationId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return specificationId.toString();
    }

    /**
     *
     * @return
     */
    /**
     *
     * @return
     */
    public PrmsBidDetail getBidDetId() {
        return bidDetId;
    }

    /**
     *
     * @param bidDetId
     */
    public void setBidDetId(PrmsBidDetail bidDetId) {
        this.bidDetId = bidDetId;
    }

    public String getSpecficationNo() {
        return specficationNo;
    }

    public void setSpecficationNo(String specficationNo) {
        this.specficationNo = specficationNo;
    }

    public MmsItemRegistration getMaterialId() {
        return materialId;
    }

    public void setMaterialId(MmsItemRegistration materialId) {
        this.materialId = materialId;
    }
   
}
