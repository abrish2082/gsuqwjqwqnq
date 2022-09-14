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
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author user
 */
@Entity
@Table(name = "PRMS_INSURANCE_NOT_TO_BANK")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PrmsInsuranceNotToBank.findAll", query = "SELECT p FROM PrmsInsuranceNotToBank p"),
    @NamedQuery(name = "PrmsInsuranceNotToBank.findByInsuranceNotificationId", query = "SELECT p FROM PrmsInsuranceNotToBank p WHERE p.insuranceNotificationId = :insuranceNotificationId"),
    @NamedQuery(name = "PrmsInsuranceNotToBank.findByInsuranceNotificationNo", query = "SELECT p FROM PrmsInsuranceNotToBank p WHERE p.insuranceNotificationNo = :insuranceNotificationNo"),

    @NamedQuery(name = "PrmsInsuranceNotToBank.findForRowSelect", query = "SELECT p FROM PrmsInsuranceNotToBank p WHERE p.insuranceNotificationNo = :insuranceNotificationNo"),
    @NamedQuery(name = "PrmsInsuranceNotToBank.searchByNoificationNo", query = "SELECT p FROM PrmsInsuranceNotToBank p WHERE p.insuranceNotificationNo LIKE :insuranceNotificationNo"),
    @NamedQuery(name = "PrmsInsuranceNotToBank.seqBankNotificationNo", query = "SELECT p FROM PrmsInsuranceNotToBank p WHERE p.insuranceNotificationId = (SELECT MAX(p.insuranceNotificationId)FROM PrmsInsuranceNotToBank p)"),

    @NamedQuery(name = "PrmsInsuranceNotToBank.findByNotifiedDate", query = "SELECT p FROM PrmsInsuranceNotToBank p WHERE p.notifiedDate = :notifiedDate"),
    @NamedQuery(name = "PrmsInsuranceNotToBank.findByMarineCertificateNo", query = "SELECT p FROM PrmsInsuranceNotToBank p WHERE p.marineCertificateNo = :marineCertificateNo"),
    @NamedQuery(name = "PrmsInsuranceNotToBank.findByAddress", query = "SELECT p FROM PrmsInsuranceNotToBank p WHERE p.address = :address"),
    @NamedQuery(name = "PrmsInsuranceNotToBank.findByRisks", query = "SELECT p FROM PrmsInsuranceNotToBank p WHERE p.risks = :risks"),
    @NamedQuery(name = "PrmsInsuranceNotToBank.findByCargoClouses", query = "SELECT p FROM PrmsInsuranceNotToBank p WHERE p.cargoClouses = :cargoClouses"),
    @NamedQuery(name = "PrmsInsuranceNotToBank.findByWarClouses", query = "SELECT p FROM PrmsInsuranceNotToBank p WHERE p.warClouses = :warClouses"),
    @NamedQuery(name = "PrmsInsuranceNotToBank.findByPrepearedBy", query = "SELECT p FROM PrmsInsuranceNotToBank p WHERE p.prepearedBy = :prepearedBy"),
    @NamedQuery(name = "PrmsInsuranceNotToBank.findByRemark", query = "SELECT p FROM PrmsInsuranceNotToBank p WHERE p.remark = :remark")})
public class PrmsInsuranceNotToBank implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PRMS_BANK_NOTIFICATICATION_SEQ")
    @SequenceGenerator(name = "PRMS_BANK_NOTIFICATICATION_SEQ", sequenceName = "PRMS_BANK_NOTIFICATICATION_SEQ", allocationSize = 1)
    @Column(name = "INSURANCE_NOTIFICATION_ID", nullable = false, precision = 0, scale = -127)
    private BigDecimal insuranceNotificationId;
    @Size(max = 20)
    @Column(name = "INSURANCE_NOTIFICATION_NO", length = 20)
    private String insuranceNotificationNo;
    @Column(name = "NOTIFIED_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date notifiedDate;
    @Size(max = 50)
    @Column(name = "MARINE_CERTIFICATE_NO", length = 50)
    private String marineCertificateNo;
    @Size(max = 50)
    @Column(name = "ADDRESS", length = 50)
    private String address;
    @Size(max = 50)
    @Column(name = "RISKS", length = 50)
    private String risks;
    @Size(max = 50)
    @Column(name = "CARGO_CLOUSES", length = 50)
    private String cargoClouses;
    @Size(max = 50)
    @Column(name = "WAR_CLOUSES", length = 50)
    private String warClouses;
    @Size(max = 50)
    @Column(name = "PREPEARED_BY", length = 50)
    private String prepearedBy;
    @Size(max = 200)
    @Column(name = "REMARK", length = 200)
    private String remark;
    @JoinColumn(name = "INSURANCE_REG_ID", referencedColumnName = "INSURANCE_REG_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private PrmsInsuranceRequisition insuranceRegId;
    @JoinColumn(name = "PORT_ID", referencedColumnName = "PORT_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private PrmsPortEntry portId;
    @JoinColumn(name = "PORT_ID_VIA", referencedColumnName = "PORT_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private PrmsPortEntry portIdVia;
    @JoinColumn(name = "PORT_ID_TO", referencedColumnName = "PORT_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private PrmsPortEntry portIdTo;
    @JoinColumn(name = "FOR_BEHALF", referencedColumnName = "SERVICE_PRO_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private PrmsServiceProvider forBehalf;
    @JoinColumn(name = "SERVICE_PRO_ID", referencedColumnName = "SERVICE_PRO_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private PrmsServiceProvider serviceProId;
    @JoinColumn(name = "ON_BEHALF", referencedColumnName = "SERVICE_DT_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private PrmsServiceProviderDetail onBehalf;
    @JoinColumn(name = "SERVICE_DT_ID", referencedColumnName = "SERVICE_DT_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private PrmsServiceProviderDetail serviceDtId;
    @Transient
    private String columnName;
    @Transient
    private String columnValue;

    public PrmsInsuranceNotToBank() {
    }

    public PrmsInsuranceNotToBank(BigDecimal insuranceNotificationId) {
        this.insuranceNotificationId = insuranceNotificationId;
    }

    public BigDecimal getInsuranceNotificationId() {
        return insuranceNotificationId;
    }

    public void setInsuranceNotificationId(BigDecimal insuranceNotificationId) {
        this.insuranceNotificationId = insuranceNotificationId;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getColumnValue() {
        return columnValue;
    }

    public void setColumnValue(String columnValue) {
        this.columnValue = columnValue;
    }

    public String getInsuranceNotificationNo() {
        return insuranceNotificationNo;
    }

    public void setInsuranceNotificationNo(String insuranceNotificationNo) {
        this.insuranceNotificationNo = insuranceNotificationNo;
    }

    public Date getNotifiedDate() {
        return notifiedDate;
    }

    public void setNotifiedDate(Date notifiedDate) {
        this.notifiedDate = notifiedDate;
    }

    public String getMarineCertificateNo() {
        return marineCertificateNo;
    }

    public void setMarineCertificateNo(String marineCertificateNo) {
        this.marineCertificateNo = marineCertificateNo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRisks() {
        return risks;
    }

    public void setRisks(String risks) {
        this.risks = risks;
    }

    public String getCargoClouses() {
        return cargoClouses;
    }

    public void setCargoClouses(String cargoClouses) {
        this.cargoClouses = cargoClouses;
    }

    public String getWarClouses() {
        return warClouses;
    }

    public void setWarClouses(String warClouses) {
        this.warClouses = warClouses;
    }

    public String getPrepearedBy() {
        return prepearedBy;
    }

    public void setPrepearedBy(String prepearedBy) {
        this.prepearedBy = prepearedBy;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public PrmsInsuranceRequisition getInsuranceRegId() {
        return insuranceRegId;
    }

    public void setInsuranceRegId(PrmsInsuranceRequisition insuranceRegId) {
        this.insuranceRegId = insuranceRegId;
    }

    public PrmsPortEntry getPortId() {
        return portId;
    }

    public void setPortId(PrmsPortEntry portId) {
        this.portId = portId;
    }

    public PrmsPortEntry getPortIdVia() {
        return portIdVia;
    }

    public void setPortIdVia(PrmsPortEntry portIdVia) {
        this.portIdVia = portIdVia;
    }

    public PrmsPortEntry getPortIdTo() {
        return portIdTo;
    }

    public void setPortIdTo(PrmsPortEntry portIdTo) {
        this.portIdTo = portIdTo;
    }

    public PrmsServiceProvider getForBehalf() {
        return forBehalf;
    }

    public void setForBehalf(PrmsServiceProvider forBehalf) {
        this.forBehalf = forBehalf;
    }

    public PrmsServiceProvider getServiceProId() {
        return serviceProId;
    }

    public void setServiceProId(PrmsServiceProvider serviceProId) {
        this.serviceProId = serviceProId;
    }

    public PrmsServiceProviderDetail getOnBehalf() {
        return onBehalf;
    }

    public void setOnBehalf(PrmsServiceProviderDetail onBehalf) {
        this.onBehalf = onBehalf;
    }

    public PrmsServiceProviderDetail getServiceDtId() {
        return serviceDtId;
    }

    public void setServiceDtId(PrmsServiceProviderDetail serviceDtId) {
        this.serviceDtId = serviceDtId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (insuranceNotificationId != null ? insuranceNotificationId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PrmsInsuranceNotToBank)) {
            return false;
        }
        PrmsInsuranceNotToBank other = (PrmsInsuranceNotToBank) object;
        if ((this.insuranceNotificationId == null && other.insuranceNotificationId != null) || (this.insuranceNotificationId != null && !this.insuranceNotificationId.equals(other.insuranceNotificationId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "et.gov.eep.prms.entity.PrmsInsuranceNotToBank[ insuranceNotificationId=" + insuranceNotificationId + " ]";
    }

}
