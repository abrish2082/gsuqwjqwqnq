/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.prms.entity;

import et.gov.eep.commonApplications.entity.WfPrmsProcessed;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author user
 */
@Entity
@Table(name = "PRMS_PORT_ENTRY")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PrmsPortEntry.findAll", query = "SELECT p FROM PrmsPortEntry p"),
    @NamedQuery(name = "PrmsPortEntry.findAllByOrder", query = "SELECT p FROM PrmsPortEntry p ORDER BY p.portName ASC"),
    @NamedQuery(name = "PrmsPortEntry.findByPortId", query = "SELECT p FROM PrmsPortEntry p WHERE p.portId = :portId"),
    @NamedQuery(name = "PrmsPortEntry.findAllByStatus", query = "SELECT p FROM PrmsPortEntry p WHERE p.status=0"),
    @NamedQuery(name = "PrmsPortEntry.searchByPortTo", query = "SELECT p FROM PrmsPortEntry p WHERE p.portId NOT IN :p.portId"),
    @NamedQuery(name = "PrmsPortEntry.findByPortNoLike", query = "SELECT p FROM PrmsPortEntry p WHERE p.portNo LIKE :portNo"),
    @NamedQuery(name = "PrmsPortEntry.findByPortName", query = "SELECT p FROM PrmsPortEntry p WHERE p.portName = :portName"),
    @NamedQuery(name = "PrmsPortEntry.findByPortType", query = "SELECT p FROM PrmsPortEntry p WHERE p.portType = :portType"),
    @NamedQuery(name = "PrmsPortEntry.findByPortTypee", query = "SELECT p FROM PrmsPortEntry p WHERE p.portType ='AirPort'"),
    @NamedQuery(name = "PrmsPortEntry.findByDryPortNameList", query = "SELECT p FROM PrmsPortEntry p WHERE p.portType = 'Dry Port' ORDER BY p.portName ASC"),
    @NamedQuery(name = "PrmsPortEntry.findProjectName", query = "SELECT p FROM PrmsPortEntry p WHERE p.portName LIKE :portName AND  p.portType ='SeaPort'"),
    @NamedQuery(name = "PrmsPortEntry.SearchPortName", query = "SELECT p FROM PrmsPortEntry p WHERE p.portName LIKE :portName ORDER BY P.portName"),
    @NamedQuery(name = "PrmsPortEntry.findByMaxPortNo", query = "SELECT p FROM PrmsPortEntry p WHERE p.portId = (SELECT Max(c.portId)  from PrmsPortEntry c)"),
    @NamedQuery(name = "PrmsPortEntry.findByDateRigistered", query = "SELECT p FROM PrmsPortEntry p WHERE p.dateRigistered = :dateRigistered"),
    @NamedQuery(name = "PrmsPortEntry.findByPortKind", query = "SELECT p FROM PrmsPortEntry p WHERE p.portKind = :portKind"),
    @NamedQuery(name = "PrmsPortEntry.findByPreparedBy", query = "SELECT p FROM PrmsPortEntry p WHERE p.preparedBy = :preparedBy"),
    @NamedQuery(name = "PrmsPortEntry.findByRemark", query = "SELECT p FROM PrmsPortEntry p WHERE p.remark = :remark"),
    @NamedQuery(name = "PrmsPortEntry.findByPortNo", query = "SELECT p FROM PrmsPortEntry p WHERE p.portNo = :portNo")})
public class PrmsPortEntry implements Serializable {

    @Column(name = "STATUS")
    private Integer status;
    @Column(name = "CURRENT_STATUS")
    private Integer currentStatus;

    @OneToMany(mappedBy = "portOfDischarge")
    private List<PrmsGoodsEntrance> prmsGoodsEntranceList;
    @OneToMany(mappedBy = "portOfLoding")
    private List<PrmsLcRigistration> prmsLcRigistLodingList;
    @OneToMany(mappedBy = "portOfDischarge")
    private List<PrmsLcRigistration> prmsLcRigistrationDischargeList;

    @OneToMany(mappedBy = "portOfLoding")
    private List<PrmsLcRigistrationAmend> prmsLcRigistAmendLodingList;
    @OneToMany(mappedBy = "portOfDischarge")
    private List<PrmsLcRigistrationAmend> prmsLcRigistAmendDischargeList;

    @OneToMany(mappedBy = "portfkId", fetch = FetchType.LAZY)
    private List<PrmsContainerAgreement> prmsContainerAgreementList;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "portId", fetch = FetchType.LAZY)
    private List<PrmsInsuranceNotToBank> prmsInsuranceNotToBankList;

    @OneToMany(mappedBy = "portId", fetch = FetchType.LAZY)
    private List<PrmsForeignExchange> prmsForeignExchangeList;
//    @OneToMany(mappedBy = "portId")
//    private List<PrmsContractAmendment> prmsContractAmendmentList;
//    @OneToMany(mappedBy = "portId")
//    private List<PrmsContract> prmsContractList;

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "PORT_ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PRMS_PORT_SEQ")
    @SequenceGenerator(name = "PRMS_PORT_SEQ", sequenceName = "PRMS_PORT_SEQ", allocationSize = 1)
    private BigDecimal portId;
    @Size(max = 50)
    @Column(name = "PORT_NAME")
    private String portName;
    @Size(max = 100)
    @Column(name = "PORT_TYPE")
    private String portType;
    @Size(max = 35)
    @Column(name = "DATE_RIGISTERED")
//    @Temporal(TemporalType.DATE)
    private String dateRigistered;
    @Size(max = 50)
    @Column(name = "PORT_KIND")
    private String portKind;
    @Column(name = "PREPARED_BY")
    private Integer preparedBy;
    @Size(max = 100)
    @Column(name = "REMARK")
    private String remark;
    @Size(max = 100)
    @Column(name = "PORT_NO")
    private String portNo;
    @OneToMany(mappedBy = "portId", cascade = CascadeType.ALL)
    private List<WfPrmsProcessed> prmsWorkFlowProccedList;
    @Transient
    private String actorName;
    @Transient
    private String columnName;
    @Transient
    private String columnValue;

    /**
     *
     */
    public PrmsPortEntry() {
    }

    /**
     *
     * @param portId
     */
    public PrmsPortEntry(BigDecimal portId) {
        this.portId = portId;
    }

    /**
     *
     * @return
     */
    public BigDecimal getPortId() {
        return portId;
    }

    /**
     *
     * @param portId
     */
    public void setPortId(BigDecimal portId) {
        this.portId = portId;
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

    /**
     *
     * @return
     */
    
    
    public String getPortName() {
        return portName;
    }

    /**
     *
     * @param portName
     */
    public void setPortName(String portName) {
        this.portName = portName;
    }

    /**
     *
     * @return
     */
    public String getPortType() {
        return portType;
    }

    /**
     *
     * @param portType
     */
    public void setPortType(String portType) {
        this.portType = portType;
    }

    /**
     *
     * @return
     */
    public String getDateRigistered() {
        return dateRigistered;
    }

    /**
     *
     * @param dateRigistered
     */
    public void setDateRigistered(String dateRigistered) {
        this.dateRigistered = dateRigistered;
    }

    /**
     *
     * @return
     */
    public String getPortKind() {
        return portKind;
    }

    /**
     *
     * @param portKind
     */
    public void setPortKind(String portKind) {
        this.portKind = portKind;
    }

    /**
     *
     * @return
     */
    public Integer getPreparedBy() {
        return preparedBy;
    }

    public void setPreparedBy(Integer preparedBy) {
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
    public String getPortNo() {
        return portNo;
    }

    /**
     *
     * @param portNo
     */
    public void setPortNo(String portNo) {
        this.portNo = portNo;
    }

    public String getActorName() {
        return actorName;
    }

    public void setActorName(String actorName) {
        this.actorName = actorName;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (portId != null ? portId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PrmsPortEntry)) {
            return false;
        }
        PrmsPortEntry other = (PrmsPortEntry) object;
        if ((this.portId == null && other.portId != null) || (this.portId != null && !this.portId.equals(other.portId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return portName;
    }

    @XmlTransient
    public List<PrmsForeignExchange> getPrmsForeignExchangeList() {
        if (prmsForeignExchangeList == null) {
            prmsForeignExchangeList = new ArrayList<>();
        }
        return prmsForeignExchangeList;
    }

    public void setPrmsForeignExchangeList(List<PrmsForeignExchange> prmsForeignExchangeList) {
        this.prmsForeignExchangeList = prmsForeignExchangeList;
    }

    @XmlTransient
    public List<PrmsInsuranceNotToBank> getPrmsInsuranceNotToBankList() {
        if (prmsInsuranceNotToBankList == null) {
            prmsInsuranceNotToBankList = new ArrayList<>();
        }
        return prmsInsuranceNotToBankList;
    }

    public void setPrmsInsuranceNotToBankList(List<PrmsInsuranceNotToBank> prmsInsuranceNotToBankList) {
        this.prmsInsuranceNotToBankList = prmsInsuranceNotToBankList;
    }

    @XmlTransient
    public List<PrmsContainerAgreement> getPrmsContainerAgreementList() {
        if (prmsContainerAgreementList == null) {
            prmsContainerAgreementList = new ArrayList<>();
        }
        return prmsContainerAgreementList;
    }

    public void setPrmsContainerAgreementList(List<PrmsContainerAgreement> prmsContainerAgreementList) {
        this.prmsContainerAgreementList = prmsContainerAgreementList;
    }

    @XmlTransient
    public List<PrmsLcRigistrationAmend> getPrmsLcRigistAmendDischargeList() {
        if (prmsLcRigistAmendDischargeList == null) {
            prmsLcRigistAmendDischargeList = new ArrayList<>();
        }
        return prmsLcRigistAmendDischargeList;
    }

    public void setPrmsLcRigistAmendDischargeList(List<PrmsLcRigistrationAmend> prmsLcRigistAmendDischargeList) {
        this.prmsLcRigistAmendDischargeList = prmsLcRigistAmendDischargeList;
    }

    @XmlTransient
    public List<PrmsLcRigistrationAmend> getPrmsLcRigistAmendLodingList() {
        if (prmsLcRigistAmendLodingList == null) {
            prmsLcRigistAmendLodingList = new ArrayList<>();
        }
        return prmsLcRigistAmendLodingList;
    }

    public void setPrmsLcRigistAmendLodingList(List<PrmsLcRigistrationAmend> prmsLcRigistAmendLodingList) {
        this.prmsLcRigistAmendLodingList = prmsLcRigistAmendLodingList;
    }

    @XmlTransient
    public List<PrmsLcRigistration> getPrmsLcRigistLodingList() {
        if (prmsLcRigistLodingList == null) {
            prmsLcRigistLodingList = new ArrayList<>();
        }
        return prmsLcRigistLodingList;
    }

    public void setPrmsLcRigistLodingList(List<PrmsLcRigistration> prmsLcRigistLodingList) {
        this.prmsLcRigistLodingList = prmsLcRigistLodingList;
    }

    @XmlTransient
    public List<PrmsLcRigistration> getPrmsLcRigistrationDischargeList() {
        if (prmsLcRigistrationDischargeList == null) {
            prmsLcRigistrationDischargeList = new ArrayList<>();
        }
        return prmsLcRigistrationDischargeList;
    }

    public void setPrmsLcRigistrationDischargeList(List<PrmsLcRigistration> prmsLcRigistrationDischargeList) {
        this.prmsLcRigistrationDischargeList = prmsLcRigistrationDischargeList;
    }

    @XmlTransient
    public List<PrmsGoodsEntrance> getPrmsGoodsEntranceList() {
        if (prmsGoodsEntranceList == null) {
            prmsGoodsEntranceList = new ArrayList<>();
        }
        return prmsGoodsEntranceList;
    }

    public void setPrmsGoodsEntranceList(List<PrmsGoodsEntrance> prmsGoodsEntranceList) {
        this.prmsGoodsEntranceList = prmsGoodsEntranceList;
    }

//    @XmlTransient
//
//    public List<PrmsContractAmendment> getPrmsContractAmendmentList() {
//        return prmsContractAmendmentList;
//    }
//
//    public void setPrmsContractAmendmentList(List<PrmsContractAmendment> prmsContractAmendmentList) {
//        this.prmsContractAmendmentList = prmsContractAmendmentList;
//    }
//    @XmlTransient
//
//    public List<PrmsContract> getPrmsContractList() {
//        return prmsContractList;
//    }
//
//    public void setPrmsContractList(List<PrmsContract> prmsContractList) {
//        this.prmsContractList = prmsContractList;
//    }
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(Integer currentStatus) {
        this.currentStatus = currentStatus;
    }

    public List<WfPrmsProcessed> getPrmsWorkFlowProccedList() {
        if (prmsWorkFlowProccedList == null) {
            prmsWorkFlowProccedList = new ArrayList<>();
        }
        return prmsWorkFlowProccedList;
    }

    public void setPrmsWorkFlowProccedList(List<WfPrmsProcessed> prmsWorkFlowProccedList) {
        this.prmsWorkFlowProccedList = prmsWorkFlowProccedList;
    }

}
