/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.entity.admin;

import java.io.Serializable;
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
 * @author userPCAdmin
 */
@Entity
@Table(name = "FMS_VOUCHERS_NO_RANGE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FmsVouchersNoRange.findAll", query = "SELECT f FROM FmsVouchersNoRange f "),
    @NamedQuery(name = "FmsVouchersNoRange.findById", query = "SELECT f FROM FmsVouchersNoRange f WHERE f.id = :id"),
    @NamedQuery(name = "FmsVouchersNoRange.findByStartingNo", query = "SELECT f FROM FmsVouchersNoRange f WHERE f.startingNo = :startingNo"),
    @NamedQuery(name = "FmsVouchersNoRange.findByEndingNo", query = "SELECT f FROM FmsVouchersNoRange f WHERE f.endingNo = :endingNo"),
    @NamedQuery(name = "FmsVouchersNoRange.findByCurrentNo", query = "SELECT f FROM FmsVouchersNoRange f WHERE f.currentNo = :currentNo"),
    @NamedQuery(name = "FmsVouchersNoRange.findByStatus", query = "SELECT f FROM FmsVouchersNoRange f WHERE f.status = :status AND f.typeId.id = :typeId"),
    @NamedQuery(name = "FmsVouchersNoRange.findByActiveStatus", query = "SELECT f FROM FmsVouchersNoRange f WHERE f.status = 1"),
    @NamedQuery(name = "FmsVouchersNoRange.findByStatusUPDATE", query = " UPDATE     FmsVouchersNoRange f SET f.status = 0 WHERE f.typeId.id = :typeId AND f.status = 1 "),
    @NamedQuery(name = "FmsVouchersNoRange.findByVoucherType", query = "SELECT f FROM FmsVouchersNoRange f WHERE f.typeId.id = :typeId AND f.status = 1"),
    @NamedQuery(name = "FmsVouchersNoRange.findByPreparedDate", query = "SELECT f FROM FmsVouchersNoRange f WHERE f.preparedDate = :preparedDate"),
    @NamedQuery(name = "FmsVouchersNoRange.findByPreparedBy", query = "SELECT f FROM FmsVouchersNoRange f WHERE f.preparedBy = :preparedBy"),
    @NamedQuery(name = "FmsVouchersNoRange.findBySystemId", query = "SELECT f FROM FmsVouchersNoRange f WHERE f.systemId.systemId=:systemId")})
public class FmsVouchersNoRange implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FMS_VOUCHERS_NO_RANGE_SEQ")
    @SequenceGenerator(name = "FMS_VOUCHERS_NO_RANGE_SEQ", sequenceName = "FMS_VOUCHERS_NO_RANGE_SEQ", allocationSize = 1)
    @NotNull
    @Column(nullable = false, precision = 0, scale = -127)
    private Integer id;
    @Size(max = 20)
    @Column(name = "STARTING_NO", length = 20)
    private String startingNo;
    @Size(max = 20)
    @Column(name = "ENDING_NO", length = 20)
    private String endingNo;
    @Size(max = 20)
    @Column(name = "CURRENT_NO", length = 20)
    private String currentNo;
    private Integer status;
    @Column(name = "PREPARED_DATE")
    @Temporal(TemporalType.DATE)
    private Date preparedDate;
    @Size(max = 50)
    @Column(name = "PREPARED_BY", length = 50)
    private String preparedBy;
    @JoinColumn(name = "FISCAL_YEAR_ID", referencedColumnName = "ACOUNTIG_PERIOD_ID")
    @ManyToOne
    private FmsAccountingPeriod fiscalYearId;
    @JoinColumn(name = "SYSTEM_ID", referencedColumnName = "SYSTEM_ID")
    @ManyToOne
    private FmsLuSystem systemId;
    @JoinColumn(name = "TYPE_ID", referencedColumnName = "ID")
    @ManyToOne
    private FmsLuVouchersType typeId;

    public FmsVouchersNoRange() {
    }

    public FmsVouchersNoRange(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStartingNo() {
        return startingNo;
    }

    public void setStartingNo(String startingNo) {
        this.startingNo = startingNo;
    }

    public String getEndingNo() {
        return endingNo;
    }

    public void setEndingNo(String endingNo) {
        this.endingNo = endingNo;
    }

    public String getCurrentNo() {
        return currentNo;
    }

    public void setCurrentNo(String currentNo) {
        this.currentNo = currentNo;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getPreparedDate() {
        return preparedDate;
    }

    public void setPreparedDate(Date preparedDate) {
        this.preparedDate = preparedDate;
    }

    public String getPreparedBy() {
        return preparedBy;
    }

    public void setPreparedBy(String preparedBy) {
        this.preparedBy = preparedBy;
    }

    public FmsAccountingPeriod getFiscalYearId() {

        return fiscalYearId;
    }

    public void setFiscalYearId(FmsAccountingPeriod fiscalYearId) {

        this.fiscalYearId = fiscalYearId;
    }

    public FmsLuSystem getSystemId() {
        return systemId;
    }

    public void setSystemId(FmsLuSystem systemId) {
        this.systemId = systemId;
    }

    public FmsLuVouchersType getTypeId() {
        if (typeId == null) {
            typeId = new FmsLuVouchersType();
        }
        return typeId;
    }

    public void setTypeId(FmsLuVouchersType typeId) {
        this.typeId = typeId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof FmsVouchersNoRange)) {
            return false;
        }
        FmsVouchersNoRange other = (FmsVouchersNoRange) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return id.toString();
    }

}
