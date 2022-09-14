/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.entity.Bond;

import java.io.Serializable;
import java.math.BigInteger;
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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author mora
 */
@Entity
@Table(name = "FMS_BOND_LOCAL_EXTEND")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FmsBondLocalExtend.findAll", query = "SELECT f FROM FmsBondLocalExtend f"),
    @NamedQuery(name = "FmsBondLocalExtend.findByNewSerialNo", query = "SELECT f FROM FmsBondLocalExtend f WHERE f.newSerialNo = :newSerialNo"),
    @NamedQuery(name = "FmsBondLocalExtend.findByExtendDate", query = "SELECT f FROM FmsBondLocalExtend f WHERE f.extendDate = :extendDate"),
    @NamedQuery(name = "FmsBondLocalExtend.findByInterestBearing", query = "SELECT f FROM FmsBondLocalExtend f WHERE f.interestBearing = :interestBearing"),
    @NamedQuery(name = "FmsBondLocalExtend.findByRemainBirr", query = "SELECT f FROM FmsBondLocalExtend f WHERE f.remainBirr = :remainBirr"),
    @NamedQuery(name = "FmsBondLocalExtend.findByRePaid", query = "SELECT f FROM FmsBondLocalExtend f WHERE f.rePaid = :rePaid"),
    @NamedQuery(name = "FmsBondLocalExtend.findByGracePeriod", query = "SELECT f FROM FmsBondLocalExtend f WHERE f.gracePeriod = :gracePeriod"),
    @NamedQuery(name = "FmsBondLocalExtend.findByStatus", query = "SELECT f FROM FmsBondLocalExtend f WHERE f.status = :status"),
    @NamedQuery(name = "FmsBondLocalExtend.findByLocalBondId", query = "SELECT f FROM FmsBondLocalExtend f WHERE f.localBondId = :localBondId")})
public class FmsBondLocalExtend implements Serializable {

    private static final long serialVersionUID = 1L;
//<editor-fold defaultstate="collapsed" desc="variable declaration">
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FMS_BOND_LOCAL_EXTEND_SEQ")
    @SequenceGenerator(name = "FMS_BOND_LOCAL_EXTEND_SEQ", sequenceName = "FMS_BOND_LOCAL_EXTEND_SEQ", allocationSize = 1)
    @Basic(optional = false)
    @NotNull
    @Column(name = "NEW_SERIAL_NO")
    private String newSerialNo;
    @Column(name = "EXTEND_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date extendDate;
    @Column(name = "INTEREST_BEARING")
    private Double interestBearing;
    @Column(name = "REMAIN_BIRR")
    private Double remainBirr;
    @Column(name = "RE_PAID")
    private BigInteger rePaid;
    @Column(name = "GRACE_PERIOD")
    private BigInteger gracePeriod;
    @Column(name = "SERIAL_NO")
    private String serialNo;
    @Column(name = "STATUS")
    private String status;
    @JoinColumn(name = "NEW_SERIAL_NO", referencedColumnName = "LOCAL_BOND_ID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private FmsBondLocal localBondId;
//</editor-fold>
    public FmsBondLocalExtend() {
    }
//<editor-fold defaultstate="collapsed" desc="Getter and Settter">
    public FmsBondLocalExtend(String newSerialNo) {
        this.newSerialNo = newSerialNo;
    }

    public FmsBondLocalExtend(String newSerialNo, FmsBondLocal localBondId) {
        this.newSerialNo = newSerialNo;
        this.localBondId = localBondId;
    }

    public String getNewSerialNo() {
        return newSerialNo;
    }

    public void setNewSerialNo(String newSerialNo) {
        this.newSerialNo = newSerialNo;
    }

    public Date getExtendDate() {
        return extendDate;
    }

    public void setExtendDate(Date extendDate) {
        this.extendDate = extendDate;
    }

    public Double getInterestBearing() {
        return interestBearing;
    }

    public void setInterestBearing(Double interestBearing) {
        this.interestBearing = interestBearing;
    }

    public Double getRemainBirr() {
        return remainBirr;
    }

    public void setRemainBirr(Double remainBirr) {
        this.remainBirr = remainBirr;
    }

    public BigInteger getRePaid() {
        return rePaid;
    }

    public void setRePaid(BigInteger rePaid) {
        this.rePaid = rePaid;
    }

    public BigInteger getGracePeriod() {
        return gracePeriod;
    }

    public void setGracePeriod(BigInteger gracePeriod) {
        this.gracePeriod = gracePeriod;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public FmsBondLocal getLocalBondId() {
        return localBondId;
    }

    public void setLocalBondId(FmsBondLocal localBondId) {
        this.localBondId = localBondId;
    }
//</editor-fold>
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (newSerialNo != null ? newSerialNo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof FmsBondLocalExtend)) {
            return false;
        }
        FmsBondLocalExtend other = (FmsBondLocalExtend) object;
        if ((this.newSerialNo == null && other.newSerialNo != null) || (this.newSerialNo != null && !this.newSerialNo.equals(other.newSerialNo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return newSerialNo;
    }

}
