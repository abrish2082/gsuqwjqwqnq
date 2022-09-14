/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.commonApplications.entity;

import et.gov.eep.mms.entity.MmsBinCard;
import et.gov.eep.mms.entity.MmsDisposal;
import et.gov.eep.mms.entity.MmsDisposalItems;
import et.gov.eep.mms.entity.MmsFaBuilding;
import et.gov.eep.mms.entity.MmsFaGeothermal;
import et.gov.eep.mms.entity.MmsFaHydropower;
import et.gov.eep.mms.entity.MmsFaLand;
import et.gov.eep.mms.entity.MmsFaSubstation;
import et.gov.eep.mms.entity.MmsFaTransmission;
import et.gov.eep.mms.entity.MmsFaTransport;
import et.gov.eep.mms.entity.MmsFaWind;
import et.gov.eep.mms.entity.MmsFixedAssetReturn;
import et.gov.eep.mms.entity.MmsFixedAssetTransfer;
import et.gov.eep.mms.entity.MmsFixedassetRegstration;
import et.gov.eep.mms.entity.MmsGatePassInformation;
import et.gov.eep.mms.entity.MmsGrn;
import et.gov.eep.mms.entity.MmsInspection;
import et.gov.eep.mms.entity.MmsInsurance;
import et.gov.eep.mms.entity.MmsInventoryBalanceSheet;
import et.gov.eep.mms.entity.MmsInventoryCounting;
import et.gov.eep.mms.entity.MmsIsiv;
import et.gov.eep.mms.entity.MmsIsivReceived;
import et.gov.eep.mms.entity.MmsItemRegistration;
import et.gov.eep.mms.entity.MmsLocationInfo;
import et.gov.eep.mms.entity.MmsLostFixedAsset;
import et.gov.eep.mms.entity.MmsManageLocation;
import et.gov.eep.mms.entity.MmsNeedAssessment;
import et.gov.eep.mms.entity.MmsNeedAssessmentService;
import et.gov.eep.mms.entity.MmsNonFixedAssetReturn;
import et.gov.eep.mms.entity.MmsRmg;
import et.gov.eep.mms.entity.MmsShelfInfo;
import et.gov.eep.mms.entity.MmsSiv;
import et.gov.eep.mms.entity.MmsStoreInformation;
import et.gov.eep.mms.entity.MmsStoreToHrDepMapper;
import et.gov.eep.mms.entity.MmsStorereq;
import et.gov.eep.prms.entity.PrmsContainerAgreement;
import et.gov.eep.prms.entity.PrmsImportShippingInstruct;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import et.gov.eep.mms.entity.MmsStockDisposal;
import et.gov.eep.mms.entity.MmsStockItemLost;

/**
 *
 * @author minab
 */
@Entity
@Table(name = "WF_MMS_PROCESSED")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "WfMmsProcessed.findAll", query = "SELECT w FROM WfMmsProcessed w"),
    @NamedQuery(name = "WfMmsProcessed.findById", query = "SELECT w FROM WfMmsProcessed w WHERE w.processedId = :processedId"),
    @NamedQuery(name = "WfMmsProcessed.findByProcessedBy", query = "SELECT w FROM WfMmsProcessed w WHERE w.processedBy = :processedBy"),
    @NamedQuery(name = "WfMmsProcessed.findByDecision", query = "SELECT w FROM WfMmsProcessed w WHERE w.decision = :decision"),
    @NamedQuery(name = "WfMmsProcessed.findByCommentGiven", query = "SELECT w FROM WfMmsProcessed w WHERE w.commentGiven = :commentGiven"),
    // @NamedQuery(name = "WfMmsProcessed.findByRecordedBy", query = "SELECT w FROM WfMmsProcessed w WHERE w.recordedBy = :recordedBy"),
    @NamedQuery(name = "WfMmsProcessed.findByprocessedOn", query = "SELECT w FROM WfMmsProcessed w WHERE w.processedOn = :processedOn"),
    @NamedQuery(name = "WfMmsProcessed.findByStoreReqId", query = "SELECT w FROM WfMmsProcessed w WHERE w.storeReqId = :storeReqId"),
    // @NamedQuery(name = "WfMmsProcessed.findByGrnId", query = "SELECT w FROM WfMmsProcessed w WHERE w.grnId = :grnId"),
    @NamedQuery(name = "WfMmsProcessed.findBySivId", query = "SELECT w FROM WfMmsProcessed w WHERE w.sivId = :sivId")})
public class WfMmsProcessed implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @GeneratedValue(generator = "WF_MMS_PROCESSED_SEQ", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "WF_MMS_PROCESSED_SEQ", sequenceName = "WF_MMS_PROCESSED_SEQ", allocationSize = 1)
    @Column(name = "PROCESSED_ID", nullable = false)
    private Integer processedId;
    @Column(name = "PROCESSED_BY")
    private Integer processedBy;
    @Column(name = "DECISION")
    private Integer decision;
    @Size(max = 50)
    @Column(name = "COMMENT_GIVEN")
    private String commentGiven;
//    @Size(max = 20)
//    @Column(name = "RECORDED_BY")
//    private String recordedBy;
    @Transient
    private String WfDecison;
    @Size(max = 50)
    @Column(name = "PROCESSED_ON")
    private String processedOn;
    @JoinColumn(name = "STORE_REQ_ID", referencedColumnName = "STORE_REQ_ID")
    @ManyToOne
    private MmsStorereq storeReqId;

    @JoinColumn(name = "GRN_ID", referencedColumnName = "GRN_ID")
    @ManyToOne
    private MmsGrn grnId;

    @JoinColumn(name = "TRANSFER_ID", referencedColumnName = "TRANSFER_ID")
    @ManyToOne
    private MmsIsiv transferId;

    @JoinColumn(name = "SIV_ID", referencedColumnName = "SIV_ID")
    @ManyToOne
    private MmsSiv sivId;
    @JoinColumn(name = "BUILDING_ID", referencedColumnName = "BUILDING_ID")
    @ManyToOne
    private MmsFaBuilding buildingId;
    @JoinColumn(name = "DISP_ID", referencedColumnName = "DISP_ID")
    @ManyToOne
    private MmsDisposal dispId;
    @JoinColumn(name = "CARD_NO", referencedColumnName = "CARD_NO")
    @ManyToOne
    private MmsBinCard cardNo;
    @JoinColumn(name = "DISPOSAL_ID", referencedColumnName = "DISPOSAL_ID")
    @ManyToOne
    private MmsDisposalItems disposalId;
    @JoinColumn(name = "GEOTHERMAL_ID", referencedColumnName = "GEOTHERMAL_ID")
    @ManyToOne
    private MmsFaGeothermal geothermalId;
    @JoinColumn(name = "LOCATION_ID", referencedColumnName = "LOCATION_ID")
    @ManyToOne
    private MmsLocationInfo locationId;
    @JoinColumn(name = "RMG_ID", referencedColumnName = "RMG_ID")
    @ManyToOne
    private MmsRmg rmgId;
    @JoinColumn(name = "LOST_ITEM_ID", referencedColumnName = "LOST_ITEM_ID")
    @ManyToOne
    private MmsLostFixedAsset lostItemId;
    @JoinColumn(name = "LOST_STOCK_ID", referencedColumnName = "LOST_STOCK_ID")
    @ManyToOne
    private MmsStockItemLost lostStockId;
    
    @JoinColumn(name = "HYDRO_POWER_ID", referencedColumnName = "HYDRO_POWER_ID")
    @ManyToOne
    private MmsFaHydropower hydroPowerId;
    @JoinColumn(name = "FA_TRANSFER_ID", referencedColumnName = "TRANSFER_ID")
    @ManyToOne
    private MmsFixedAssetTransfer faTransferId;
    @JoinColumn(name = "INVENTORY_COUNT_ID", referencedColumnName = "INVENTORY_COUNT_ID")
    @ManyToOne
    private MmsInventoryCounting inventoryCountId;
    @JoinColumn(name = "LAND_ID", referencedColumnName = "LAND_ID")
    @ManyToOne
    private MmsFaLand landId;
    @JoinColumn(name = "SUBSTATION_ID", referencedColumnName = "SUBSTATION_ID")
    @ManyToOne
    private MmsFaSubstation substationId;
    @JoinColumn(name = "ASSESSMETN_ID", referencedColumnName = "ASSESSMETN_ID")
    @ManyToOne
    private MmsNeedAssessment assessmetnId;
    @JoinColumn(name = "TRANSFER_FA_ID", referencedColumnName = "TRANSFER_ID")
    @ManyToOne
    private MmsFixedAssetTransfer transferFaId;
    @JoinColumn(name = "SHELF_ID", referencedColumnName = "SHELF_ID")
    @ManyToOne
    private MmsShelfInfo shelfId;
    @JoinColumn(name = "DEP_TO_STORE_ID", referencedColumnName = "ID")
    @ManyToOne
    private MmsStoreToHrDepMapper depToStoreId;
    @JoinColumn(name = "TRANSMISSION_ID", referencedColumnName = "TRANSMISSION_ID")
    @ManyToOne
    private MmsFaTransmission transmissionId;
//    @JoinColumn(name = "STORE_LOCATION_ID", referencedColumnName = "ID")
//    @ManyToOne
//    private MmsStoreLocation storeLocationId;
    @JoinColumn(name = "INS_ID", referencedColumnName = "INS_ID")
    @ManyToOne
    private MmsInsurance insId;
    @JoinColumn(name = "RECIEVING_ID", referencedColumnName = "RECIEVING_ID")
    @ManyToOne
    private MmsIsivReceived recievingId;
    @JoinColumn(name = "NFA_ID", referencedColumnName = "NFA_ID")
    @ManyToOne
    private MmsNonFixedAssetReturn nfaId;
    @JoinColumn(name = "IBS_ID", referencedColumnName = "IBS_ID")
    @ManyToOne
    private MmsInventoryBalanceSheet ibsId;
    @JoinColumn(name = "FA_REGSTRATION_ID", referencedColumnName = "ID")
    @ManyToOne
    private MmsFixedassetRegstration faRegstrationId;
    @JoinColumn(name = "MATERIAL_ID", referencedColumnName = "MATERIAL_ID")
    @ManyToOne
    private MmsItemRegistration materialId;
    @JoinColumn(name = "STORE_ID", referencedColumnName = "STORE_ID")
    @ManyToOne
    private MmsStoreInformation storeId;
    @JoinColumn(name = "STK_ID", referencedColumnName = "STOCK_ID")
    @ManyToOne
    private MmsStockDisposal stkId;
    // @JoinColumn(name = "TRANSFER_ID", referencedColumnName = "TRANSFER_ID")
    // @ManyToOne
//    private MmsIsiv transferId;
    @JoinColumn(name = "NAC_ID", referencedColumnName = "ASSESSMENT_DTL_ID")
    @ManyToOne
    private MmsNeedAssessmentService assessmentDtlId;
    @JoinColumn(name = "ML_ID", referencedColumnName = "ID")
    @ManyToOne
    private MmsManageLocation mlId;
    @JoinColumn(name = "GATE_PASS_ID", referencedColumnName = "GATE_PASS_ID")
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)

    private MmsGatePassInformation gatePassId;
    @JoinColumn(name = "TRANSPORT_ID", referencedColumnName = "TRANSPORT_ID")
    @ManyToOne
    private MmsFaTransport transportId;
    @JoinColumn(name = "FAR_ID", referencedColumnName = "FAR_ID")
    @ManyToOne
    private MmsFixedAssetReturn farId;

    @JoinColumn(name = "INSPECTION_ID", referencedColumnName = "INSPECTION_ID")
    @ManyToOne
    private MmsInspection inspectionId;
    @JoinColumn(name = "WIND_ID", referencedColumnName = "WIND_ID")
    @ManyToOne
    private MmsFaWind windId;
    @JoinColumn(name = "CONTAINERS_ID", referencedColumnName = "CONTAINER_ID")
    @ManyToOne
    private PrmsContainerAgreement containerId;
    @JoinColumn(name = "IS_ID", referencedColumnName = "ID")
    @ManyToOne
    private PrmsImportShippingInstruct isId;
    
//    @JoinColumn(name = "SHELF_ID", referencedColumnName = "SHELF_ID")
//    @ManyToOne
//    private MmsShelfInfo ShelfId;

    public MmsFixedAssetReturn getFarId() {
        return farId;
    }

    public void setFarId(MmsFixedAssetReturn farId) {
        this.farId = farId;
    }

    public MmsStockItemLost getLostStockId() {
        return lostStockId;
    }

    public void setLostStockId(MmsStockItemLost lostStockId) {
        this.lostStockId = lostStockId;
    }

    public MmsNeedAssessmentService getAssessmentDtlId() {
        return assessmentDtlId;
    }

    public void setAssessmentDtlId(MmsNeedAssessmentService assessmentDtlId) {
        this.assessmentDtlId = assessmentDtlId;
    }

    public MmsGrn getGrnId() {
        return grnId;
    }

    public void setGrnId(MmsGrn grnId) {
        this.grnId = grnId;
    }

    public WfMmsProcessed() {
    }

    public WfMmsProcessed(Integer id) {
        this.processedId = id;
    }

    public Integer getProcessedId() {
        return processedId;
    }

    public void setProcessedId(Integer processedId) {
        this.processedId = processedId;
    }

    public Integer getProcessedBy() {
        return processedBy;
    }

    public void setProcessedBy(Integer processedBy) {
        this.processedBy = processedBy;
    }

    public Integer getDecision() {
        return decision;
    }

    public void setDecision(Integer decision) {
        this.decision = decision;
    }

    public String getCommentGiven() {
        return commentGiven;
    }

    public void setCommentGiven(String commentGiven) {
        this.commentGiven = commentGiven;
    }

//    public String getRecordedBy() {
//        return recordedBy;
//    }
//
//    public void setRecordedBy(String recordedBy) {
//        this.recordedBy = recordedBy;
//    }
    public String getProcessedOn() {
        return processedOn;
    }

    public void setProcessedOn(String processedOn) {
        this.processedOn = processedOn;
    }

    public MmsStorereq getStoreReqId() {
        return storeReqId;
    }

    public void setStoreReqId(MmsStorereq storeReqId) {
        this.storeReqId = storeReqId;
    }

//    public BigInteger getGrnId() {
//        return grnId;
//    }
//
//    public void setGrnId(BigInteger grnId) {
//        this.grnId = grnId;
//    }
    public MmsSiv getSivId() {
        return sivId;
    }

    public void setSivId(MmsSiv sivId) {
        this.sivId = sivId;
    }

    public MmsFaBuilding getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(MmsFaBuilding buildingId) {
        this.buildingId = buildingId;
    }

    public MmsDisposal getDispId() {
        return dispId;
    }

    public void setDispId(MmsDisposal dispId) {
        this.dispId = dispId;
    }

    public MmsBinCard getCardNo() {
        return cardNo;
    }

    public void setCardNo(MmsBinCard cardNo) {
        this.cardNo = cardNo;
    }

    public MmsDisposalItems getDisposalId() {
        return disposalId;
    }

    public void setDisposalId(MmsDisposalItems disposalId) {
        this.disposalId = disposalId;
    }

    public MmsFaGeothermal getGeothermalId() {
        return geothermalId;
    }

    public void setGeothermalId(MmsFaGeothermal geothermalId) {
        this.geothermalId = geothermalId;
    }

    public MmsLocationInfo getLocationId() {
        return locationId;
    }

    public void setLocationId(MmsLocationInfo locationId) {
        this.locationId = locationId;
    }

    public MmsRmg getRmgId() {
        return rmgId;
    }

    public void setRmgId(MmsRmg rmgId) {
        this.rmgId = rmgId;
    }

    public MmsLostFixedAsset getLostItemId() {
        return lostItemId;
    }

    public void setLostItemId(MmsLostFixedAsset lostItemId) {
        this.lostItemId = lostItemId;
    }

    public MmsFaHydropower getHydroPowerId() {
        return hydroPowerId;
    }

    public void setHydroPowerId(MmsFaHydropower hydroPowerId) {
        this.hydroPowerId = hydroPowerId;
    }

    public MmsFixedAssetTransfer getFaTransferId() {
        return faTransferId;
    }

    public void setFaTransferId(MmsFixedAssetTransfer faTransferId) {
        this.faTransferId = faTransferId;
    }

    public MmsInventoryCounting getInventoryCountId() {
        return inventoryCountId;
    }

    public void setInventoryCountId(MmsInventoryCounting inventoryCountId) {
        this.inventoryCountId = inventoryCountId;
    }

    public MmsFaLand getLandId() {
        return landId;
    }

    public void setLandId(MmsFaLand landId) {
        this.landId = landId;
    }

    public MmsFaSubstation getSubstationId() {
        return substationId;
    }

    public void setSubstationId(MmsFaSubstation substationId) {
        this.substationId = substationId;
    }

    public MmsNeedAssessment getAssessmetnId() {
        return assessmetnId;
    }

    public void setAssessmetnId(MmsNeedAssessment assessmetnId) {
        this.assessmetnId = assessmetnId;
    }

    public MmsFixedAssetTransfer getTransferFaId() {
        return transferFaId;
    }

    public void setTransferFaId(MmsFixedAssetTransfer transferFaId) {
        this.transferFaId = transferFaId;
    }

//    public MmsShelfInfo getShelfId() {
//        return shelfId;
//    }
//
//    public void setShelfId(MmsShelfInfo shelfId) {
//        this.shelfId = shelfId;
//    }

    public MmsStoreToHrDepMapper getDepToStoreId() {
        return depToStoreId;
    }

    public void setDepToStoreId(MmsStoreToHrDepMapper depToStoreId) {
        this.depToStoreId = depToStoreId;
    }

    public MmsFaTransmission getTransmissionId() {
        return transmissionId;
    }

    public void setTransmissionId(MmsFaTransmission transmissionId) {
        this.transmissionId = transmissionId;
    }

//    public MmsStoreLocation getStoreLocationId() {
//        return storeLocationId;
//    }
//
//    public void setStoreLocationId(MmsStoreLocation storeLocationId) {
//        this.storeLocationId = storeLocationId;
//    }
    public MmsInsurance getInsId() {
        return insId;
    }

    public void setInsId(MmsInsurance insId) {
        this.insId = insId;
    }

    public MmsIsivReceived getRecievingId() {
        return recievingId;
    }

    public void setRecievingId(MmsIsivReceived recievingId) {
        this.recievingId = recievingId;
    }

    public MmsNonFixedAssetReturn getNfaId() {
        return nfaId;
    }

    public void setNfaId(MmsNonFixedAssetReturn nfaId) {
        this.nfaId = nfaId;
    }

    public MmsInventoryBalanceSheet getIbsId() {
        return ibsId;
    }

    public void setIbsId(MmsInventoryBalanceSheet ibsId) {
        this.ibsId = ibsId;
    }

    public MmsFixedassetRegstration getFaRegstrationId() {
        return faRegstrationId;
    }

    public void setFaRegstrationId(MmsFixedassetRegstration faRegstrationId) {
        this.faRegstrationId = faRegstrationId;
    }

    public MmsItemRegistration getMaterialId() {
        return materialId;
    }

    public void setMaterialId(MmsItemRegistration materialId) {
        this.materialId = materialId;
    }

    public MmsStoreInformation getStoreId() {
        return storeId;
    }

    public void setStoreId(MmsStoreInformation storeId) {
        this.storeId = storeId;
    }

    public MmsIsiv getTransferId() {
        return transferId;
    }

    public void setTransferId(MmsIsiv transferId) {
        this.transferId = transferId;
    }

    public MmsManageLocation getMlId() {
        return mlId;
    }

    public void setMlId(MmsManageLocation mlId) {
        this.mlId = mlId;
    }

    public MmsGatePassInformation getGatePassId() {
        return gatePassId;
    }

    public void setGatePassId(MmsGatePassInformation gatePassId) {
        this.gatePassId = gatePassId;
    }

    public MmsFaTransport getTransportId() {
        return transportId;
    }

    public void setTransportId(MmsFaTransport transportId) {
        this.transportId = transportId;
    }

    public MmsInspection getInspectionId() {
        return inspectionId;
    }

    public void setInspectionId(MmsInspection inspectionId) {
        this.inspectionId = inspectionId;
    }

    public MmsFaWind getWindId() {
        return windId;
    }

    public void setWindId(MmsFaWind windId) {
        this.windId = windId;
    }

    public PrmsContainerAgreement getContainerId() {
        return containerId;
    }

    public void setContainerId(PrmsContainerAgreement containerId) {
        this.containerId = containerId;
    }

    public PrmsImportShippingInstruct getIsId() {
        return isId;
    }

    public void setIsId(PrmsImportShippingInstruct isId) {
        this.isId = isId;
    }

    public MmsStockDisposal getStkId() {
        return stkId;
    }

    public void setStkId(MmsStockDisposal stkId) {
        this.stkId = stkId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (processedId != null ? processedId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof WfMmsProcessed)) {
            return false;
        }
        WfMmsProcessed other = (WfMmsProcessed) object;
        if ((this.processedId == null && other.processedId != null) || (this.processedId != null && !this.processedId.equals(other.processedId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "et.gov.eep.mms.entity.WfMmsProcessed[ processedId=" + processedId + " ]";
    }

    public String getWfDecison() {
        return WfDecison;
    }

    public void setWfDecison(String WfDecison) {
        this.WfDecison = WfDecison;

    }

    public MmsShelfInfo getShelfId() {
        return shelfId;
    }

    public void setShelfId(MmsShelfInfo shelfId) {
        this.shelfId = shelfId;
    }
    
}
