/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.mms.entity;

import java.io.Serializable;
import java.math.BigDecimal;
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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Sadik
 */
@Entity
@Table(name = "MMS_LOCATION_INFO_DTL")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MmsLocationInfoDtl.findAll", query = "SELECT m FROM MmsLocationInfoDtl m"),
    @NamedQuery(name = "MmsLocationInfoDtl.findByLocInfoDtlId", query = "SELECT m FROM MmsLocationInfoDtl m WHERE m.locInfoDtlId = :locInfoDtlId"),
    @NamedQuery(name = "MmsLocationInfoDtl.findByCellName", query = "SELECT m FROM MmsLocationInfoDtl m WHERE m.cellName = :cellName"),
    @NamedQuery(name = "MmsLocationInfoDtl.findByCellCode", query = "SELECT m FROM MmsLocationInfoDtl m WHERE m.cellCode = :cellCode"),
    @NamedQuery(name = "MmsLocationInfoDtl.findByCellRow", query = "SELECT m FROM MmsLocationInfoDtl m WHERE m.cellRow = :cellRow")})
public class MmsLocationInfoDtl implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
     @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MMS_LOCATION_DTL_SEQ")
    @SequenceGenerator(name = "MMS_LOCATION_DTL_SEQ", sequenceName = "MMS_LOCATION_DTL_SEQ", allocationSize = 1)
    @Column(name = "LOC_INFO_DTL_ID", nullable = false, precision = 0, scale = -127)
    private BigDecimal locInfoDtlId;
    @Size(max = 45)
    @Column(name = "CELL_NAME", length = 45)
    private String cellName;
    @Size(max = 45)
    @Column(name = "CELL_CODE", length = 45)
    private String cellCode;
    @Size(max = 45)
    @Column(name = "CELL_ROW", length = 45)
    private String cellRow;
    @JoinColumn(name = "LOCATION_INFO_ID", referencedColumnName = "LOCATION_ID")
    @ManyToOne
    private MmsLocationInfo locationInfoId;

    /**
     *
     */
    public MmsLocationInfoDtl() {
    }

    /**
     *
     * @param locInfoDtlId
     */
    public MmsLocationInfoDtl(BigDecimal locInfoDtlId) {
        this.locInfoDtlId = locInfoDtlId;
    }

    /**
     *
     * @return
     */
    public BigDecimal getLocInfoDtlId() {
        return locInfoDtlId;
    }

    /**
     *
     * @param locInfoDtlId
     */
    public void setLocInfoDtlId(BigDecimal locInfoDtlId) {
        this.locInfoDtlId = locInfoDtlId;
    }

    /**
     *
     * @return
     */
    public String getCellName() {
        return cellName;
    }

    /**
     *
     * @param cellName
     */
    public void setCellName(String cellName) {
        this.cellName = cellName;
    }

    /**
     *
     * @return
     */
    public String getCellCode() {
        return cellCode;
    }

    /**
     *
     * @param cellCode
     */
    public void setCellCode(String cellCode) {
        this.cellCode = cellCode;
    }

    /**
     *
     * @return
     */
    public String getCellRow() {
        return cellRow;
    }

    /**
     *
     * @param cellRow
     */
    public void setCellRow(String cellRow) {
        this.cellRow = cellRow;
    }

    /**
     *
     * @return
     */
    public MmsLocationInfo getLocationInfoId() {
        return locationInfoId;
    }

    /**
     *
     * @param locationInfoId
     */
    public void setLocationInfoId(MmsLocationInfo locationInfoId) {
        this.locationInfoId = locationInfoId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (locInfoDtlId != null ? locInfoDtlId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MmsLocationInfoDtl)) {
            return false;
        }
        MmsLocationInfoDtl other = (MmsLocationInfoDtl) object;
        if ((this.locInfoDtlId == null && other.locInfoDtlId != null) || (this.locInfoDtlId != null && !this.locInfoDtlId.equals(other.locInfoDtlId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "et.gov.eep.mms.entity.MmsLocationInfoDtl[ locInfoDtlId=" + locInfoDtlId + " ]";
    }
    
}
