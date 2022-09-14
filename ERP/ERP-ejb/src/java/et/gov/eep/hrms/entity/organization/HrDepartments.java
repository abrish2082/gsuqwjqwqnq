/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.entity.organization;

import et.gov.eep.fcms.entity.admin.FmsCostcSystemJunction;
import et.gov.eep.hrms.entity.employee.HrEmployees;
import et.gov.eep.hrms.entity.lookup.HrLuRegions;
import et.gov.eep.hrms.entity.payroll.HrPayrollPlPgDept;
import et.gov.eep.hrms.entity.recruitment.HrRecruitmentRequests;
import et.gov.eep.hrms.entity.training.HrTdAnnualNeedRequests;
import et.gov.eep.hrms.entity.training.HrTdPsvcTraineeDetails;
import et.gov.eep.mms.entity.MmsDisposalItems;
import et.gov.eep.mms.entity.MmsFixedAssetReturn;
import et.gov.eep.mms.entity.MmsFixedAssetTransfer;
import et.gov.eep.prms.entity.PrmsPurchaseRequest;
import et.gov.eep.mms.entity.MmsFixedassetRegstration;
import et.gov.eep.mms.entity.MmsGatePassInformation;
import et.gov.eep.mms.entity.MmsGrn;
import et.gov.eep.mms.entity.MmsInsurance;
import et.gov.eep.mms.entity.MmsLostFixedAsset;
import et.gov.eep.mms.entity.MmsNeedAssessment;
import et.gov.eep.mms.entity.MmsStockItemLost;
import et.gov.eep.mms.entity.MmsStoreToHrDepMapper;
import et.gov.eep.prms.entity.PrmsGoodsEntrance;
import et.gov.eep.prms.entity.PrmsImportShippingInstruct;
import et.gov.eep.prms.entity.PrmsInsuranceRequisition;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Administrator
 */
@Entity
@Table(name = "HR_DEPARTMENTS")
@XmlRootElement
@NamedQueries//<editor-fold defaultstate="collapsed" desc="">
        ({
            @NamedQuery(name = "HrDepartments.findAllBySorted", query = "SELECT h FROM HrDepartments h order by h.depName desc"),
            @NamedQuery(name = "HrDepartments.findAll", query = "SELECT h FROM HrDepartments h"),
            @NamedQuery(name = "HrDepartments.findByDepId", query = "SELECT h FROM HrDepartments h WHERE h.depId = :depId"),
            @NamedQuery(name = "HrDepartments.findByDepName", query = "SELECT h FROM HrDepartments h WHERE h.depName = :depName"),
            @NamedQuery(name = "HrDepartments.findByEstDate", query = "SELECT h FROM HrDepartments h WHERE h.estDate = :estDate"),
            @NamedQuery(name = "HrDepartments.findByMission", query = "SELECT h FROM HrDepartments h WHERE h.mission = :mission"),
            @NamedQuery(name = "HrDepartments.findByVision", query = "SELECT h FROM HrDepartments h WHERE h.vision = :vision"),
            @NamedQuery(name = "HrDepartments.findByDepValue", query = "SELECT h FROM HrDepartments h WHERE h.depValue = :depValue"),
            @NamedQuery(name = "HrDepartments.deleteDep", query = "DELETE FROM HrDepartments h WHERE h.parentId = :depId or h.depId = :depId"),
            //FOR MMS USE DON"T DELETE.
            @NamedQuery(name = "HrDepartments.findByDepNameLike", query = "SELECT h FROM HrDepartments h WHERE UPPER(h.depName) LIKE :depName"),
            @NamedQuery(name = "HrDepartments.findByParentId", query = "SELECT h FROM HrDepartments h WHERE h.parentId = :parentId order by h.depName asc")})
//</editor-fold>

public class HrDepartments implements Serializable {

    //<editor-fold defaultstate="collapsed" desc="Entity Attributes and Relations">
    @Column(name = "PARENT_ID")
    private Integer parentId;
    @Column(name = "TYPE")
    private Integer type;
    @OneToOne(mappedBy = "depId")
    private FmsCostcSystemJunction fmsCostcSystemJunction;
    @OneToMany(mappedBy = "departmentId")
    private List<MmsStoreToHrDepMapper> mmsStoreToHrDepMapperList;
    @OneToMany(mappedBy = "fromDepId")
    private List<PrmsImportShippingInstruct> prmsImportShippingInstructList;
    @OneToMany(mappedBy = "depId")
    private List<PrmsGoodsEntrance> prmsGoodsEntranceList;
    @OneToMany(mappedBy = "hrDeptId", fetch = FetchType.LAZY)
    private List<PrmsInsuranceRequisition> prmsInsuranceRequisitionList;
    @Basic(optional = false)
    @Column(name = "DEP_LEVEL")
    private BigInteger depLevel;
    @OneToMany(mappedBy = "deptId")
    private List<HrRecruitmentRequests> hrRecruitmentRequestsList;
    @OneToOne(optional = false, mappedBy = "depId")
    private List<FmsCostcSystemJunction> fmsCostcSystemJunctionList;
    @OneToMany(mappedBy = "depId")
    private List<HrPayrollPlPgDept> hrPayrollPlPgDeptList;
    @Column(name = "EST_DATE")
    private String estDate;
    @OneToMany(mappedBy = "department")
    private List<MmsFixedassetRegstration> mmsFixedassetRegstrationList;
    @OneToMany(mappedBy = "department")
    private List<MmsGatePassInformation> mmsGatePassInformationList;
    @OneToMany(mappedBy = "deptId", fetch = FetchType.LAZY)
    private List<HrEmployees> hrEmployeesList;
    @OneToMany(mappedBy = "depId")
    private List<HrJobTypes> hrJobTypesList;
    @OneToMany(mappedBy = "department")
    private List<MmsLostFixedAsset> mmsLostFixedAssetList;
    @OneToMany(mappedBy = "department")
    private List<MmsFixedAssetReturn> mmsLostFixedAssetReturnList;
    @OneToMany(mappedBy = "department")
    private List<MmsInsurance> mmsFixedAssetInsuranceList;
    @OneToMany(mappedBy = "department")
    private List<MmsDisposalItems> mmsDisposalItemsList;
    @OneToMany(mappedBy = "transferDepartment")
    private List<MmsFixedAssetTransfer> mmsFixedAssetTransferDeptList;
    @OneToMany(mappedBy = "receivingDepartment")
    private List<MmsFixedAssetTransfer> mmsFixedAssetTransferDeptRecieveList;
    @OneToMany(mappedBy = "deptId", cascade = CascadeType.ALL)
    private List<HrDeptJobs> hrDeptJobsList;
    private static final long serialVersionUID = 1L;
    @OneToMany(mappedBy = "deptId", cascade = CascadeType.ALL)
    private List<HrTdPsvcTraineeDetails> hrTdPsvcTraineeDetailsList;

    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL)
    private List<MmsStockItemLost> mmsStockLostList;
    @Id
    @Basic(optional = false)
    @Column(name = "DEP_ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HR_DEPARTMENTS_SEQ")
    @SequenceGenerator(name = "HR_DEPARTMENTS_SEQ", sequenceName = "HR_DEPARTMENTS_SEQ", allocationSize = 1)
    private Integer depId;
    @Column(name = "DEP_NAME")
    private String depName;
    @Column(name = "MISSION")
    private String mission;
    @Column(name = "VISION")
    private String vision;
    @Column(name = "DEP_VALUE")
    private String depValue;
    @Column(name = "PREPARED_BY")
    private String preparedBy;
    @Column(name = "PREPARED_ON")
    private String preparedOn;
    @OneToMany(mappedBy = "depId", cascade = CascadeType.ALL)
    private List<HrDepAddresses> hrDepAddressesList = new ArrayList<>();
    @OneToMany(mappedBy = "reqstrDepId")
    private List<PrmsPurchaseRequest> prmsPurchaseRequestList;
    @OneToMany(mappedBy = "departmentId")
    private List<MmsNeedAssessment> mmsNeedAssessmentList;
    @OneToMany(mappedBy = "deptId")
    private List<HrTdAnnualNeedRequests> hrTdAnnualNeedRequestsList;
    @OneToMany(mappedBy = "depId")
    private List<MmsGrn> mmsGrnsList;
    @JoinColumn(name = "REGION_ID", referencedColumnName = "ID")
    @ManyToOne
    private HrLuRegions regionId;
    @Transient
    private String columnName;
    @Transient
    private String columnValue;
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Getter and setters">
    public HrDepartments() {
    }

    /**
     *
     * @param depId
     */
    public HrDepartments(Integer depId) {
        this.depId = depId;
    }

    /**
     *
     * @return
     */
    public Integer getDepId() {
        return depId;
    }

    /**
     *
     * @param depId
     */
    public void setDepId(Integer depId) {
        this.depId = depId;
    }

    /**
     *
     * @return
     */
    public String getDepName() {
        return depName;
    }

    /**
     *
     * @param depName
     */
    public void setDepName(String depName) {
        this.depName = depName;
    }

    /**
     *
     * @return
     */
    public String getMission() {
        return mission;
    }

    public HrLuRegions getRegionId() {
        return regionId;
    }

    public void setRegionId(HrLuRegions regionId) {
        this.regionId = regionId;
    }

    /**
     *
     * @param mission
     */
    public void setMission(String mission) {
        this.mission = mission;
    }

    /**
     *
     * @return
     */
    public String getVision() {
        return vision;
    }

    /**
     *
     * @param vision
     */
    public void setVision(String vision) {
        this.vision = vision;
    }

    /**
     *
     * @return
     */
    public String getDepValue() {
        return depValue;
    }

    /**
     *
     * @param depValue
     */
    public void setDepValue(String depValue) {
        this.depValue = depValue;
    }

    /**
     *
     * @return
     */
    public Integer getParentId() {
        return parentId;
    }

    /**
     *
     * @param parentId
     */
    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getPreparedBy() {
        return preparedBy;
    }

    public void setPreparedBy(String preparedBy) {
        this.preparedBy = preparedBy;
    }

    public String getPreparedOn() {
        return preparedOn;
    }

    public void setPreparedOn(String preparedOn) {
        this.preparedOn = preparedOn;
    }

    /**
     *
     * @return
     */
    @XmlTransient
    public List<HrDepAddresses> getHrDepAddressesList() {
        return hrDepAddressesList;
    }

    /**
     *
     * @param hrDepAddressesList
     */
    public void setHrDepAddressesList(List<HrDepAddresses> hrDepAddressesList) {
        this.hrDepAddressesList = hrDepAddressesList;
    }

    public List<MmsDisposalItems> getMmsDisposalItemsList() {
        return mmsDisposalItemsList;
    }

    public void setMmsDisposalItemsList(List<MmsDisposalItems> mmsDisposalItemsList) {
        this.mmsDisposalItemsList = mmsDisposalItemsList;
    }

    public List<MmsFixedAssetTransfer> getMmsFixedAssetTransferDeptList() {
        return mmsFixedAssetTransferDeptList;
    }

    public void setMmsFixedAssetTransferDeptList(List<MmsFixedAssetTransfer> mmsFixedAssetTransferDeptList) {
        this.mmsFixedAssetTransferDeptList = mmsFixedAssetTransferDeptList;
    }

    public List<MmsFixedAssetTransfer> getMmsFixedAssetTransferDeptRecieveList() {
        return mmsFixedAssetTransferDeptRecieveList;
    }

    public void setMmsFixedAssetTransferDeptRecieveList(List<MmsFixedAssetTransfer> mmsFixedAssetTransferDeptRecieveList) {
        this.mmsFixedAssetTransferDeptRecieveList = mmsFixedAssetTransferDeptRecieveList;
    }

    public List<MmsFixedAssetReturn> getMmsLostFixedAssetReturnList() {
        return mmsLostFixedAssetReturnList;
    }

    public void setMmsLostFixedAssetReturnList(List<MmsFixedAssetReturn> mmsLostFixedAssetReturnList) {
        this.mmsLostFixedAssetReturnList = mmsLostFixedAssetReturnList;
    }

    @XmlTransient
    public List<MmsStockItemLost> getMmsStockLostList() {
        if (mmsStockLostList == null) {
            mmsStockLostList = new ArrayList<>();
        }
        return mmsStockLostList;
    }

    public void setMmsStockLostList(List<MmsStockItemLost> mmsStockLostList) {
        this.mmsStockLostList = mmsStockLostList;
    }

    public List<MmsInsurance> getMmsFixedAssetInsuranceList() {
        return mmsFixedAssetInsuranceList;
    }

    public void setMmsFixedAssetInsuranceList(List<MmsInsurance> mmsFixedAssetInsuranceList) {
        this.mmsFixedAssetInsuranceList = mmsFixedAssetInsuranceList;
    }

    @XmlTransient
    public List<HrTdPsvcTraineeDetails> getHrTdPsvcTraineeDetailsList() {
        return hrTdPsvcTraineeDetailsList;
    }

    public void setHrTdPsvcTraineeDetailsList(List<HrTdPsvcTraineeDetails> hrTdPsvcTraineeDetailsList) {
        this.hrTdPsvcTraineeDetailsList = hrTdPsvcTraineeDetailsList;
    }

    /**
     *
     * @return
     */
    @XmlTransient
    public List<MmsNeedAssessment> getMmsNeedAssessmentList() {
        return mmsNeedAssessmentList;
    }

    /**
     *
     * @param mmsNeedAssessmentList
     */
    public void setMmsNeedAssessmentList(List<MmsNeedAssessment> mmsNeedAssessmentList) {
        this.mmsNeedAssessmentList = mmsNeedAssessmentList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (depId != null ? depId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HrDepartments)) {
            return false;
        }
        HrDepartments other = (HrDepartments) object;
        if ((this.depId == null && other.depId != null) || (this.depId != null && !this.depId.equals(other.depId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return depName;
    }

    /**
     *
     * @return
     */
    @XmlTransient
    public List<HrJobTypes> getHrJobTypesList() {
        return hrJobTypesList;
    }

    /**
     *
     * @param hrJobTypesList
     */
    public void setHrJobTypesList(List<HrJobTypes> hrJobTypesList) {
        this.hrJobTypesList = hrJobTypesList;
    }

    /**
     *
     * @return
     */
    @XmlTransient
    public List<HrDeptJobs> getHrDeptJobsList() {
        return hrDeptJobsList;
    }

    /**
     *
     * @param hrDeptJobsList
     */
    public void setHrDeptJobsList(List<HrDeptJobs> hrDeptJobsList) {
        this.hrDeptJobsList = hrDeptJobsList;
    }

//    @XmlTransient
//    public List<PrmsMarketAssessment> getPrmsMarketAssessmentList() {
//        return prmsMarketAssessmentList;
//    }
//
//    public void setPrmsMarketAssessmentList(List<PrmsMarketAssessment> prmsMarketAssessmentList) {
//        this.prmsMarketAssessmentList = prmsMarketAssessmentList;
//    }
    /**
     *
     * @return
     */
    @XmlTransient
    public List<PrmsPurchaseRequest> getPrmsPurchaseRequestList() {
        return prmsPurchaseRequestList;
    }

    /**
     *
     * @param prmsPurchaseRequestList
     */
    public void setPrmsPurchaseRequestList(List<PrmsPurchaseRequest> prmsPurchaseRequestList) {
        this.prmsPurchaseRequestList = prmsPurchaseRequestList;
    }

    /**
     *
     * @return
     */
    @XmlTransient
//    public List<PrmsContract> getPrmsContractList() {
//        return prmsContractList;
//    }
//
//    /**
//     *
//     * @param prmsContractList
//     */
//    public void setPrmsContractList(List<PrmsContract> prmsContractList) {
//        this.prmsContractList = prmsContractList;
//    }

    public List<MmsLostFixedAsset> getMmsLostFixedAssetList() {
        return mmsLostFixedAssetList;
    }

    public void setMmsLostFixedAssetList(List<MmsLostFixedAsset> mmsLostFixedAssetList) {
        this.mmsLostFixedAssetList = mmsLostFixedAssetList;
    }

    /**
     *
     * @return
     */
    @XmlTransient
    public List<MmsGatePassInformation> getMmsGatePassInformationList() {
        return mmsGatePassInformationList;
    }

    /**
     *
     * @param mmsGatePassInformationList
     */
    public void setMmsGatePassInformationList(List<MmsGatePassInformation> mmsGatePassInformationList) {
        this.mmsGatePassInformationList = mmsGatePassInformationList;
    }

    /**
     *
     * @return
     */
    @XmlTransient
    public List<HrEmployees> getHrEmployeesList() {
        return hrEmployeesList;
    }

    /**
     *
     * @param hrEmployeesList
     */
    public void setHrEmployeesList(List<HrEmployees> hrEmployeesList) {
        this.hrEmployeesList = hrEmployeesList;
    }

//    public String getValue() {
//        return value;
//    }
//
//    public void setValue(String value) {
//        this.value = value;
//    }
    /**
     *
     * @return
     */
    @XmlTransient
    public List<MmsFixedassetRegstration> getMmsFixedassetRegstrationList() {
        if (mmsFixedassetRegstrationList == null) {
            mmsFixedassetRegstrationList = new ArrayList<>();
        }
        return mmsFixedassetRegstrationList;
    }

    /**
     *
     * @param mmsFixedassetRegstrationList
     */
    public void setMmsFixedassetRegstrationList(List<MmsFixedassetRegstration> mmsFixedassetRegstrationList) {
        this.mmsFixedassetRegstrationList = mmsFixedassetRegstrationList;
    }

    public String getEstDate() {
        return estDate;
    }

    public void setEstDate(String estDate) {
        this.estDate = estDate;
    }

    @XmlTransient

    public List<HrPayrollPlPgDept> getHrPayrollPlPgDeptList() {
        return hrPayrollPlPgDeptList;
    }

    public void setHrPayrollPlPgDeptList(List<HrPayrollPlPgDept> hrPayrollPlPgDeptList) {
        this.hrPayrollPlPgDeptList = hrPayrollPlPgDeptList;
    }

    @XmlTransient
    public List<FmsCostcSystemJunction> getFmsCostcSystemJunctionList() {
        return fmsCostcSystemJunctionList;
    }

    public void setFmsCostcSystemJunctionList(List<FmsCostcSystemJunction> fmsCostcSystemJunctionList) {
        this.fmsCostcSystemJunctionList = fmsCostcSystemJunctionList;
    }

    @XmlTransient
    public List<HrRecruitmentRequests> getHrRecruitmentRequestsList() {
        return hrRecruitmentRequestsList;
    }

    public void setHrRecruitmentRequestsList(List<HrRecruitmentRequests> hrRecruitmentRequestsList) {
        this.hrRecruitmentRequestsList = hrRecruitmentRequestsList;
    }

//    @XmlTransient
//    public List<FmsOperatingBudget> getFmsOperatingBudgetList() {
//        return fmsOperatingBudgetList;
//    }
//
//    public void setFmsOperatingBudgetList(List<FmsOperatingBudget> fmsOperatingBudgetList) {
//        this.fmsOperatingBudgetList = fmsOperatingBudgetList;
//    }
//    public BigInteger getParentId() {
//        return parentId;
//    }
//
//    public void setParentId(BigInteger parentId) {
//        this.parentId = parentId;
//    }
    public BigInteger getDepLevel() {
        return depLevel;
    }

    public void setDepLevel(BigInteger depLevel) {
        this.depLevel = depLevel;
    }

    @XmlTransient

    public List<HrTdAnnualNeedRequests> getHrTdAnnualNeedRequestsList() {
        return hrTdAnnualNeedRequestsList;
    }

    public void setHrTdAnnualNeedRequestsList(List<HrTdAnnualNeedRequests> hrTdAnnualNeedRequestsList) {
        this.hrTdAnnualNeedRequestsList = hrTdAnnualNeedRequestsList;
    }
//    @XmlTransient
//
//    public List<PrmsPaymentRequisition> getPrmsPaymentRequisitionList() {
//        if(prmsPaymentRequisitionList==null){prmsPaymentRequisitionList=new ArrayList<>();}
//        return prmsPaymentRequisitionList;
//    }
//
//    public void setPrmsPaymentRequisitionList(List<PrmsPaymentRequisition> prmsPaymentRequisitionList) {
//        this.prmsPaymentRequisitionList = prmsPaymentRequisitionList;
//    }
//

    public void addToDebJob(HrDeptJobs hrDeptJobs) {
        if (hrDeptJobs.getDeptId() != this) {
            this.getHrDeptJobsList().add(hrDeptJobs);
            hrDeptJobs.setDeptId(this);
        }
    }

    @XmlTransient
    public List<PrmsInsuranceRequisition> getPrmsInsuranceRequisitionList() {
        if (prmsInsuranceRequisitionList == null) {
            prmsInsuranceRequisitionList = new ArrayList<>();
        }
        return prmsInsuranceRequisitionList;
    }

    public void setPrmsInsuranceRequisitionList(List<PrmsInsuranceRequisition> prmsInsuranceRequisitionList) {
        this.prmsInsuranceRequisitionList = prmsInsuranceRequisitionList;
    }

//    @XmlTransient
//    public List<FmsOperatingBudget> getFmsOperatingBudgetList() {
//        return fmsOperatingBudgetList;
//    }
//
//    public void setFmsOperatingBudgetList(List<FmsOperatingBudget> fmsOperatingBudgetList) {
//        this.fmsOperatingBudgetList = fmsOperatingBudgetList;
//    }
    @XmlTransient
    public List<PrmsGoodsEntrance> getPrmsGoodsEntranceList() {
        return prmsGoodsEntranceList;
    }

    public void setPrmsGoodsEntranceList(List<PrmsGoodsEntrance> prmsGoodsEntranceList) {
        this.prmsGoodsEntranceList = prmsGoodsEntranceList;
    }

    @XmlTransient
    public List<PrmsImportShippingInstruct> getPrmsImportShippingInstructList() {
        if (prmsImportShippingInstructList == null) {
            prmsImportShippingInstructList = new ArrayList<>();
        }
        return prmsImportShippingInstructList;
    }

    public void setPrmsImportShippingInstructList(List<PrmsImportShippingInstruct> prmsImportShippingInstructList) {
        this.prmsImportShippingInstructList = prmsImportShippingInstructList;
    }

//    public List<PrmsClaimRecordingForm> getPrmsClaimRecordingFormList() {
//        return prmsClaimRecordingFormList;
//    }
//
//    public void setPrmsClaimRecordingFormList(List<PrmsClaimRecordingForm> prmsClaimRecordingFormList) {
//        this.prmsClaimRecordingFormList = prmsClaimRecordingFormList;
//    }
//    public BigInteger getParentId() {
//        return parentId;
//    }
//
//    public void setParentId(BigInteger parentId) {
//        this.parentId = parentId;
//    }
    @XmlTransient
    public List<MmsStoreToHrDepMapper> getMmsStoreToHrDepMapperList() {
        return mmsStoreToHrDepMapperList;
    }

    public void setMmsStoreToHrDepMapperList(List<MmsStoreToHrDepMapper> mmsStoreToHrDepMapperList) {
        this.mmsStoreToHrDepMapperList = mmsStoreToHrDepMapperList;
    }

    @XmlTransient
    public List<MmsGrn> getMmsGrnsList() {
        return mmsGrnsList;
    }

    public void setMmsGrnsList(List<MmsGrn> mmsGrnsList) {
        this.mmsGrnsList = mmsGrnsList;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public FmsCostcSystemJunction getFmsCostcSystemJunction() {
        return fmsCostcSystemJunction;
    }

    public void setFmsCostcSystemJunction(FmsCostcSystemJunction fmsCostcSystemJunction) {
        this.fmsCostcSystemJunction = fmsCostcSystemJunction;
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
//</editor-fold>

}
