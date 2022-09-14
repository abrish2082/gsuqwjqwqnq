/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.mms.entity;



import java.io.Serializable;



import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Binyam
 */
@Entity
@Table(name = "MMS_FA_ASSET_TYPE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MmsFaAssetType.findAll", query = "SELECT m FROM MmsFaAssetType m"),
    @NamedQuery(name = "MmsFaAssetType.findByAssetId", query = "SELECT m FROM MmsFaAssetType m WHERE m.assetId = :assetId"),
    @NamedQuery(name = "MmsFaAssetType.findByAssetNo", query = "SELECT m FROM MmsFaAssetType m WHERE m.assetNo = :assetNo"),
    @NamedQuery(name = "MmsFaAssetType.findByAssetType", query = "SELECT m FROM MmsFaAssetType m WHERE m.assetType = :assetType")})
public class MmsFaAssetType implements Serializable {
    @OneToMany(mappedBy = "assetType")
    private List<MmsFixedassetRegstration> mmsFixedassetRegstrationList;
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ASSET_ID")
    private Integer assetId;
    @Column(name = "ASSET_NO")
    private BigInteger assetNo;
    @Size(max = 100)
    @Column(name = "ASSET_TYPE")
    private String assetType;
    @OneToMany(mappedBy = "gtAssetType")
    private List<MmsFaGeothermal> mmsFaGeothermalList;
    @OneToMany(mappedBy = "buAssetType")
    private List<MmsFaBuilding> mmsFaBuildingList;
    @OneToMany(mappedBy = "wdAssetType")
    private List<MmsFaWind> mmsFaWindList;
    @OneToMany(mappedBy = "assetTypeNo")
    private List<MmsFaTransmission> mmsFaTransmissionList;
    @OneToMany(mappedBy = "hpAssetType")
    private List<MmsFaHydropower> mmsFaHydropowerList;
    @OneToMany(mappedBy = "ssAssetType")
    private List<MmsFaSubstation> mmsFaSubstationList;

    /**
     *
     */
    public MmsFaAssetType() {
    }

    /**
     *
     * @param assetId
     */
    public MmsFaAssetType(Integer assetId) {
        this.assetId = assetId;
    }

    /**
     *
     * @return
     */
    public Integer getAssetId() {
        return assetId;
    }

    /**
     *
     * @param assetId
     */
    public void setAssetId(Integer assetId) {
        this.assetId = assetId;
    }

    /**
     *
     * @return
     */
    public BigInteger getAssetNo() {
        return assetNo;
    }

    /**
     *
     * @param assetNo
     */
    public void setAssetNo(BigInteger assetNo) {
        this.assetNo = assetNo;
    }

    /**
     *
     * @return
     */
    public String getAssetType() {
        return assetType;
    }

    /**
     *
     * @param assetType
     */
    public void setAssetType(String assetType) {
        this.assetType = assetType;
    }

    /**
     *
     * @return
     */
    @XmlTransient
    public List<MmsFaGeothermal> getMmsFaGeothermalList() {
        return mmsFaGeothermalList;
    }

    /**
     *
     * @param mmsFaGeothermalList
     */
    public void setMmsFaGeothermalList(List<MmsFaGeothermal> mmsFaGeothermalList) {
        this.mmsFaGeothermalList = mmsFaGeothermalList;
    }

    /**
     *
     * @return
     */
    @XmlTransient
    public List<MmsFaBuilding> getMmsFaBuildingList() {
        return mmsFaBuildingList;
    }

    /**
     *
     * @param mmsFaBuildingList
     */
    public void setMmsFaBuildingList(List<MmsFaBuilding> mmsFaBuildingList) {
        this.mmsFaBuildingList = mmsFaBuildingList;
    }

    /**
     *
     * @return
     */
    @XmlTransient
    public List<MmsFaWind> getMmsFaWindList() {
        return mmsFaWindList;
    }

    /**
     *
     * @param mmsFaWindList
     */
    public void setMmsFaWindList(List<MmsFaWind> mmsFaWindList) {
        this.mmsFaWindList = mmsFaWindList;
    }

    /**
     *
     * @return
     */
    @XmlTransient
    public List<MmsFaTransmission> getMmsFaTransmissionList() {
        return mmsFaTransmissionList;
    }

    /**
     *
     * @param mmsFaTransmissionList
     */
    public void setMmsFaTransmissionList(List<MmsFaTransmission> mmsFaTransmissionList) {
        this.mmsFaTransmissionList = mmsFaTransmissionList;
    }

    /**
     *
     * @return
     */
    @XmlTransient
    public List<MmsFaHydropower> getMmsFaHydropowerList() {
        return mmsFaHydropowerList;
    }

    /**
     *
     * @param mmsFaHydropowerList
     */
    public void setMmsFaHydropowerList(List<MmsFaHydropower> mmsFaHydropowerList) {
        this.mmsFaHydropowerList = mmsFaHydropowerList;
    }

    /**
     *
     * @return
     */
    @XmlTransient
    public List<MmsFaSubstation> getMmsFaSubstationList() {
        return mmsFaSubstationList;
    }

    /**
     *
     * @param mmsFaSubstationList
     */
    public void setMmsFaSubstationList(List<MmsFaSubstation> mmsFaSubstationList) {
        this.mmsFaSubstationList = mmsFaSubstationList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (assetId != null ? assetId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MmsFaAssetType)) {
            return false;
        }
        MmsFaAssetType other = (MmsFaAssetType) object;
        if ((this.assetId == null && other.assetId != null) || (this.assetId != null && !this.assetId.equals(other.assetId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return assetType;
    }

    @XmlTransient
    public List<MmsFixedassetRegstration> getMmsFixedassetRegstrationList() {
        return mmsFixedassetRegstrationList;
    }

    public void setMmsFixedassetRegstrationList(List<MmsFixedassetRegstration> mmsFixedassetRegstrationList) {
        this.mmsFixedassetRegstrationList = mmsFixedassetRegstrationList;
    }
    
}
