/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.entity;

//import et.gov.eep.mms.entity.PapmsSivFixedMat;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
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
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Heni
 */
@Entity
@Table(name = "FMS_FIXED_ASSET")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FmsFixedAsset.findAll", query = "SELECT f FROM FmsFixedAsset f"),
    @NamedQuery(name = "FmsFixedAsset.findByFixedAssetId", query = "SELECT f FROM FmsFixedAsset f WHERE f.fixedAssetId = :fixedAssetId"),
    @NamedQuery(name = "FmsFixedAsset.findByFixedAssetGroup", query = "SELECT f FROM FmsFixedAsset f WHERE f.fixedAssetGroup = :fixedAssetGroup"),
    @NamedQuery(name = "FmsFixedAsset.findByFixedAssetCode", query = "SELECT f FROM FmsFixedAsset f WHERE f.fixedAssetCode = :fixedAssetCode"),
    @NamedQuery(name = "FmsFixedAsset.findByFixedAssetActivation", query = "SELECT f FROM FmsFixedAsset f WHERE f.fixedAssetActivation = :fixedAssetActivation"),
    @NamedQuery(name = "FmsFixedAsset.findByAccumulatedDepreciation", query = "SELECT f FROM FmsFixedAsset f WHERE f.accumulatedDepreciation = :accumulatedDepreciation"),
    @NamedQuery(name = "FmsFixedAsset.findByCurrentStatus", query = "SELECT f FROM FmsFixedAsset f WHERE f.currentStatus = :currentStatus"),
    @NamedQuery(name = "FmsFixedAsset.findByCost", query = "SELECT f FROM FmsFixedAsset f WHERE f.cost = :cost"),
    @NamedQuery(name = "FmsFixedAsset.findBySideNo", query = "SELECT f FROM FmsFixedAsset f WHERE f.sideNo = :sideNo"),
    @NamedQuery(name = "FmsFixedAsset.findByReferenceType", query = "SELECT f FROM FmsFixedAsset f WHERE f.referenceType = :referenceType"),
    @NamedQuery(name = "FmsFixedAsset.findByReferenceCode", query = "SELECT f FROM FmsFixedAsset f WHERE f.referenceCode = :referenceCode"),
    @NamedQuery(name = "FmsFixedAsset.findBySubsidiaryId", query = "SELECT f FROM FmsFixedAsset f WHERE f.subsidiaryId = :subsidiaryId"),
    @NamedQuery(name = "FmsFixedAsset.findByTaxGroup", query = "SELECT f FROM FmsFixedAsset f WHERE f.taxGroup = :taxGroup"),
    @NamedQuery(name = "FmsFixedAsset.findByDescrpition", query = "SELECT f FROM FmsFixedAsset f WHERE f.descrpition = :descrpition"),
    @NamedQuery(name = "FmsFixedAsset.findByDateIssued", query = "SELECT f FROM FmsFixedAsset f WHERE f.dateIssued = :dateIssued"),
    @NamedQuery(name = "FmsFixedAsset.findByLocation", query = "SELECT f FROM FmsFixedAsset f WHERE f.location = :location"),
    @NamedQuery(name = "FmsFixedAsset.findByAdjustmentAmmaunt", query = "SELECT f FROM FmsFixedAsset f WHERE f.adjustmentAmmaunt = :adjustmentAmmaunt"),
    @NamedQuery(name = "FmsFixedAsset.findByAdditionsAmmaunt", query = "SELECT f FROM FmsFixedAsset f WHERE f.additionsAmmaunt = :additionsAmmaunt")})
public class FmsFixedAsset implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "FIXED_ASSET_ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FMS_FIXED_ASSET_REG_ID_SEQ")
    @SequenceGenerator(name = "FMS_FIXED_ASSET_REG_ID_SEQ", sequenceName = "FMS_FIXED_ASSET_REG_ID_SEQ", allocationSize = 1)
    private Long fixedAssetId;
    @Size(max = 255)
    @Column(name = "FIXED_ASSET_GROUP")
    private String fixedAssetGroup;
    @Size(max = 20)
    @Column(name = "FIXED_ASSET_CODE")
    private String fixedAssetCode;
    @Size(max = 20)
    @Column(name = "FIXED_ASSET_ACTIVATION")
    private String fixedAssetActivation;
    @Column(name = "ACCUMULATED_DEPRECIATION")
    private BigInteger accumulatedDepreciation;
    @Column(name = "CURRENT_STATUS")
    private Integer currentStatus;
    @Column(name = "COST")
    private BigInteger cost;
    @Size(max = 50)
    @Column(name = "SIDE_NO")
    private String sideNo;
    @Size(max = 20)
    @Column(name = "REFERENCE_TYPE")
    private String referenceType;
    @Size(max = 50)
    @Column(name = "REFERENCE_CODE")
    private String referenceCode;
    @Column(name = "SUBSIDIARY_ID")
    private BigInteger subsidiaryId;
    @Column(name = "TAX_GROUP")
    private BigInteger taxGroup;
    @Size(max = 20)
    @Column(name = "DESCRPITION")
    private String descrpition;
    @Column(name = "DATE_ISSUED")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateIssued;
    @Size(max = 20)
    @Column(name = "LOCATION")
    private String location;
    @Column(name = "ADJUSTMENT_AMMAUNT")
    private BigInteger adjustmentAmmaunt;
    @Column(name = "ADDITIONS_AMMAUNT")
    private BigInteger additionsAmmaunt;
    @OneToMany(mappedBy = "fixedAssetId")
    private List<FmsFaDepreciation> fmsFaDepreciationList;
//    @JoinColumn(name = "SIV_FIX_MAT_ID", referencedColumnName = "SIV_FIX_MAT_ID")
//    @ManyToOne
//    private PapmsSivFixedMat sivFixMatId;

    /**
     *
     */
    public FmsFixedAsset() {
    }

    /**
     *
     * @param fixedAssetId
     */
    public FmsFixedAsset(Long fixedAssetId) {
        this.fixedAssetId = fixedAssetId;
    }

    /**
     *
     * @return
     */
    public Long getFixedAssetId() {
        return fixedAssetId;
    }

    /**
     *
     * @param fixedAssetId
     */
    public void setFixedAssetId(Long fixedAssetId) {
        this.fixedAssetId = fixedAssetId;
    }

    /**
     *
     * @return
     */
    public String getFixedAssetGroup() {
        return fixedAssetGroup;
    }

    /**
     *
     * @param fixedAssetGroup
     */
    public void setFixedAssetGroup(String fixedAssetGroup) {
        this.fixedAssetGroup = fixedAssetGroup;
    }

    /**
     *
     * @return
     */
    public String getFixedAssetCode() {
        return fixedAssetCode;
    }

    /**
     *
     * @param fixedAssetCode
     */
    public void setFixedAssetCode(String fixedAssetCode) {
        this.fixedAssetCode = fixedAssetCode;
    }

    /**
     *
     * @return
     */
    public String getFixedAssetActivation() {
        return fixedAssetActivation;
    }

    /**
     *
     * @param fixedAssetActivation
     */
    public void setFixedAssetActivation(String fixedAssetActivation) {
        this.fixedAssetActivation = fixedAssetActivation;
    }

    /**
     *
     * @return
     */
    public BigInteger getAccumulatedDepreciation() {
        return accumulatedDepreciation;
    }

    /**
     *
     * @param accumulatedDepreciation
     */
    public void setAccumulatedDepreciation(BigInteger accumulatedDepreciation) {
        this.accumulatedDepreciation = accumulatedDepreciation;
    }

    /**
     *
     * @return
     */
    public Integer getCurrentStatus() {
        return currentStatus;
    }

    /**
     *
     * @param currentStatus
     */
    public void setCurrentStatus(Integer currentStatus) {
        this.currentStatus = currentStatus;
    }

    /**
     *
     * @return
     */
    public BigInteger getCost() {
        return cost;
    }

    /**
     *
     * @param cost
     */
    public void setCost(BigInteger cost) {
        this.cost = cost;
    }

    /**
     *
     * @return
     */
    public String getSideNo() {
        return sideNo;
    }

    /**
     *
     * @param sideNo
     */
    public void setSideNo(String sideNo) {
        this.sideNo = sideNo;
    }

    /**
     *
     * @return
     */
    public String getReferenceType() {
        return referenceType;
    }

    /**
     *
     * @param referenceType
     */
    public void setReferenceType(String referenceType) {
        this.referenceType = referenceType;
    }

    /**
     *
     * @return
     */
    public String getReferenceCode() {
        return referenceCode;
    }

    /**
     *
     * @param referenceCode
     */
    public void setReferenceCode(String referenceCode) {
        this.referenceCode = referenceCode;
    }

    /**
     *
     * @return
     */
    public BigInteger getSubsidiaryId() {
        return subsidiaryId;
    }

    /**
     *
     * @param subsidiaryId
     */
    public void setSubsidiaryId(BigInteger subsidiaryId) {
        this.subsidiaryId = subsidiaryId;
    }

    /**
     *
     * @return
     */
    public BigInteger getTaxGroup() {
        return taxGroup;
    }

    /**
     *
     * @param taxGroup
     */
    public void setTaxGroup(BigInteger taxGroup) {
        this.taxGroup = taxGroup;
    }

    /**
     *
     * @return
     */
    public String getDescrpition() {
        return descrpition;
    }

    /**
     *
     * @param descrpition
     */
    public void setDescrpition(String descrpition) {
        this.descrpition = descrpition;
    }

    /**
     *
     * @return
     */
    public Date getDateIssued() {
        return dateIssued;
    }

    /**
     *
     * @param dateIssued
     */
    public void setDateIssued(Date dateIssued) {
        this.dateIssued = dateIssued;
    }

    /**
     *
     * @return
     */
    public String getLocation() {
        return location;
    }

    /**
     *
     * @param location
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     *
     * @return
     */
    public BigInteger getAdjustmentAmmaunt() {
        return adjustmentAmmaunt;
    }

    /**
     *
     * @param adjustmentAmmaunt
     */
    public void setAdjustmentAmmaunt(BigInteger adjustmentAmmaunt) {
        this.adjustmentAmmaunt = adjustmentAmmaunt;
    }

    /**
     *
     * @return
     */
    public BigInteger getAdditionsAmmaunt() {
        return additionsAmmaunt;
    }

    /**
     *
     * @param additionsAmmaunt
     */
    public void setAdditionsAmmaunt(BigInteger additionsAmmaunt) {
        this.additionsAmmaunt = additionsAmmaunt;
    }
//
//    public PapmsSivFixedMat getSivFixMatId() {
//        return sivFixMatId;
//    }
//
//    public void setSivFixMatId(PapmsSivFixedMat sivFixMatId) {
//        this.sivFixMatId = sivFixMatId;
//    }

    /**
     *
     * @return
     */
    public List<FmsFaDepreciation> getFmsFaDepreciationList() {
        return fmsFaDepreciationList;
    }

    /**
     *
     * @param fmsFaDepreciationList
     */
    public void setFmsFaDepreciationList(List<FmsFaDepreciation> fmsFaDepreciationList) {
        this.fmsFaDepreciationList = fmsFaDepreciationList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (fixedAssetId != null ? fixedAssetId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FmsFixedAsset)) {
            return false;
        }
        FmsFixedAsset other = (FmsFixedAsset) object;
        if ((this.fixedAssetId == null && other.fixedAssetId != null) || (this.fixedAssetId != null && !this.fixedAssetId.equals(other.fixedAssetId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "et.gov.insa.erp.ibfms.entity.FmsFixedAsset[ fixedAssetId=" + fixedAssetId + " ]";
    }

}
