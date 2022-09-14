/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.prms.entity;

import et.gov.eep.commonApplications.entity.WfPrmsProcessed;
import et.gov.eep.pms.entity.PmsCreateProjects;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import oracle.sql.DATE;

/**
 *
 * @author user
 */
@Entity
@Table(name = "PRMS_PROJECT_PLAN")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PrmsProjectPlan.findAll", query = "SELECT p FROM PrmsProjectPlan p"),
    @NamedQuery(name = "PrmsProjectPlan.findById", query = "SELECT p FROM PrmsProjectPlan p WHERE p.id = :id"),
    @NamedQuery(name = "PrmsProjectPlan.findAutoGenerat", query = "SELECT p FROM PrmsProjectPlan p WHERE p.id = (SELECT Max(p.id)  from PrmsProjectPlan p)"),
    @NamedQuery(name = "PrmsProjectPlan.findByLotno", query = "SELECT p FROM PrmsProjectPlan p WHERE p.projectName LIKE :lotno"),
    @NamedQuery(name = "PrmsProjectPlan.findByProjectPlanNoLike", query = "SELECT p FROM PrmsProjectPlan p WHERE p.projectPlanNo LIKE :projectPlanNo"),
    @NamedQuery(name = "PrmsProjectPlan.searchByProjectPlanNo", query = "SELECT p FROM PrmsProjectPlan p WHERE p.projectPlanNo LIKE :projectPlanNo and p.preparedBy=:preparedBy"),

    @NamedQuery(name = "PrmsProjectPlan.searchByProjectPlanNoStatus", query = "SELECT p FROM PrmsProjectPlan p WHERE p.status=0 and p.projectPlanNo is not null"),

    @NamedQuery(name = "PrmsProjectPlan.findByPreparedBy", query = "SELECT p FROM PrmsProjectPlan p WHERE p.preparedBy = :preparedBy"),
    @NamedQuery(name = "PrmsProjectPlan.findByDateRigistered", query = "SELECT p FROM PrmsProjectPlan p WHERE p.dateRigistered = :dateRigistered"),

    @NamedQuery(name = "PrmsProjectPlan.findByFullProcurementDescription", query = "SELECT p FROM PrmsProjectPlan p WHERE p.fullProcurementDescription = :fullProcurementDescription"),
    @NamedQuery(name = "PrmsProjectPlan.findByMarketApproach", query = "SELECT p FROM PrmsProjectPlan p WHERE p.marketApproach = :marketApproach"),
    @NamedQuery(name = "PrmsProjectPlan.findByPreQualification", query = "SELECT p FROM PrmsProjectPlan p WHERE p.preQualification = :preQualification"),
    @NamedQuery(name = "PrmsProjectPlan.findByProcurementProcess", query = "SELECT p FROM PrmsProjectPlan p WHERE p.procurementProcess = :procurementProcess"),
    @NamedQuery(name = "PrmsProjectPlan.findBySourceOfFinance", query = "SELECT p FROM PrmsProjectPlan p WHERE p.sourceOfFinance = :sourceOfFinance"),
    @NamedQuery(name = "PrmsProjectPlan.findByLoan", query = "SELECT p FROM PrmsProjectPlan p WHERE p.loan = :loan"),
    @NamedQuery(name = "PrmsProjectPlan.findByCredit", query = "SELECT p FROM PrmsProjectPlan p WHERE p.credit = :credit"),
    @NamedQuery(name = "PrmsProjectPlan.findByEstimatedAmount", query = "SELECT p FROM PrmsProjectPlan p WHERE p.estimatedAmount = :estimatedAmount"),
    @NamedQuery(name = "PrmsProjectPlan.findByPrepareDocumntStrtDate", query = "SELECT p FROM PrmsProjectPlan p WHERE p.prepareDocumntStrtDate = :prepareDocumntStrtDate"),
    @NamedQuery(name = "PrmsProjectPlan.findByPrepareDocumntEndingDate", query = "SELECT p FROM PrmsProjectPlan p WHERE p.prepareDocumntEndingDate = :prepareDocumntEndingDate"),
    @NamedQuery(name = "PrmsProjectPlan.findByAdvrtiseSpcificNticeStrtDt", query = "SELECT p FROM PrmsProjectPlan p WHERE p.advrtiseSpcificNticeStrtDt = :advrtiseSpcificNticeStrtDt"),
    @NamedQuery(name = "PrmsProjectPlan.findByAdvrtiseSpcificNticeEndDt", query = "SELECT p FROM PrmsProjectPlan p WHERE p.advrtiseSpcificNticeEndDt = :advrtiseSpcificNticeEndDt"),
    @NamedQuery(name = "PrmsProjectPlan.findByOpenPreQulifictionStrtDat", query = "SELECT p FROM PrmsProjectPlan p WHERE p.openPreQulifictionStrtDat = :openPreQulifictionStrtDat"),
    @NamedQuery(name = "PrmsProjectPlan.findByOpenPreQulifictionEndDat", query = "SELECT p FROM PrmsProjectPlan p WHERE p.openPreQulifictionEndDat = :openPreQulifictionEndDat"),
    @NamedQuery(name = "PrmsProjectPlan.findByPreQlfPrplEvlRprtStrtDt", query = "SELECT p FROM PrmsProjectPlan p WHERE p.preQlfPrplEvlRprtStrtDt = :preQlfPrplEvlRprtStrtDt"),
    @NamedQuery(name = "PrmsProjectPlan.findByPreQlfPrplEvlRprtEndDt", query = "SELECT p FROM PrmsProjectPlan p WHERE p.preQlfPrplEvlRprtEndDt = :preQlfPrplEvlRprtEndDt"),
    @NamedQuery(name = "PrmsProjectPlan.findBySubmisionEvalRecommEndDte", query = "SELECT p FROM PrmsProjectPlan p WHERE p.submisionEvalRecommEndDte = :submisionEvalRecommEndDte"),
    @NamedQuery(name = "PrmsProjectPlan.findBySubmApprlEvalRprtStrtDt", query = "SELECT p FROM PrmsProjectPlan p WHERE p.submApprlEvalRprtStrtDt = :submApprlEvalRprtStrtDt"),
    @NamedQuery(name = "PrmsProjectPlan.findBySubmApprvlEvalRprtEndDt", query = "SELECT p FROM PrmsProjectPlan p WHERE p.submApprvlEvalRprtEndDt = :submApprvlEvalRprtEndDt"),
    @NamedQuery(name = "PrmsProjectPlan.findByPrpreBidDocmentStrtDte", query = "SELECT p FROM PrmsProjectPlan p WHERE p.prpreBidDocmentStrtDte = :prpreBidDocmentStrtDte"),
    @NamedQuery(name = "PrmsProjectPlan.findByPrpreBidDocmentEndDte", query = "SELECT p FROM PrmsProjectPlan p WHERE p.prpreBidDocmentEndDte = :prpreBidDocmentEndDte"),
    @NamedQuery(name = "PrmsProjectPlan.findByInvtionShrtLstBiderStrtDt", query = "SELECT p FROM PrmsProjectPlan p WHERE p.invtionShrtLstBiderStrtDt = :invtionShrtLstBiderStrtDt"),
    @NamedQuery(name = "PrmsProjectPlan.findByInvtionShrtLstBiderEndDt", query = "SELECT p FROM PrmsProjectPlan p WHERE p.invtionShrtLstBiderEndDt = :invtionShrtLstBiderEndDt"),
    @NamedQuery(name = "PrmsProjectPlan.findByProjectPlanNo", query = "SELECT p FROM PrmsProjectPlan p WHERE p.projectPlanNo = :projectPlanNo"),
    @NamedQuery(name = "PrmsProjectPlan.findBySubmisionEvalRecommStrtDte", query = "SELECT p FROM PrmsProjectPlan p WHERE p.submisionEvalRecommStrtDte = :submisionEvalRecommStrtDte"),
    @NamedQuery(name = "PrmsProjectPlan.findByBidOpeningDate", query = "SELECT p FROM PrmsProjectPlan p WHERE p.bidOpeningDate = :bidOpeningDate"),
    @NamedQuery(name = "PrmsProjectPlan.findByBidClosingDate", query = "SELECT p FROM PrmsProjectPlan p WHERE p.bidClosingDate = :bidClosingDate"),
    @NamedQuery(name = "PrmsProjectPlan.findByEvltTchniclFinanStrtDte", query = "SELECT p FROM PrmsProjectPlan p WHERE p.evltTchniclFinanStrtDte = :evltTchniclFinanStrtDte"),
    @NamedQuery(name = "PrmsProjectPlan.findByEvltTchniclFinanEndDte", query = "SELECT p FROM PrmsProjectPlan p WHERE p.evltTchniclFinanEndDte = :evltTchniclFinanEndDte"),
    @NamedQuery(name = "PrmsProjectPlan.findBySbmtTchnclFinanComiteStrt", query = "SELECT p FROM PrmsProjectPlan p WHERE p.sbmtTchnclFinanComiteStrt = :sbmtTchnclFinanComiteStrt"),
    @NamedQuery(name = "PrmsProjectPlan.findBySbmtTchnclFinanComiteEnd", query = "SELECT p FROM PrmsProjectPlan p WHERE p.sbmtTchnclFinanComiteEnd = :sbmtTchnclFinanComiteEnd"),
    @NamedQuery(name = "PrmsProjectPlan.findBySbmtTchnclFinanMgmtStrtDt", query = "SELECT p FROM PrmsProjectPlan p WHERE p.sbmtTchnclFinanMgmtStrtDt = :sbmtTchnclFinanMgmtStrtDt"),
    @NamedQuery(name = "PrmsProjectPlan.findBySbmtTchnclFinanMgmtEndDte", query = "SELECT p FROM PrmsProjectPlan p WHERE p.sbmtTchnclFinanMgmtEndDte = :sbmtTchnclFinanMgmtEndDte"),
    @NamedQuery(name = "PrmsProjectPlan.findBySbmtTchnclFinanBrordEndDt", query = "SELECT p FROM PrmsProjectPlan p WHERE p.sbmtTchnclFinanBrordEndDt = :sbmtTchnclFinanBrordEndDt"),
    @NamedQuery(name = "PrmsProjectPlan.findBySbmtTchnclFinanBrordStrtD", query = "SELECT p FROM PrmsProjectPlan p WHERE p.sbmtTchnclFinanBrordStrtD = :sbmtTchnclFinanBrordStrtD"),
    @NamedQuery(name = "PrmsProjectPlan.findByNotificationAwardStrtDate", query = "SELECT p FROM PrmsProjectPlan p WHERE p.notificationAwardStrtDate = :notificationAwardStrtDate"),
    @NamedQuery(name = "PrmsProjectPlan.findByNotificationAwardEndDate", query = "SELECT p FROM PrmsProjectPlan p WHERE p.notificationAwardEndDate = :notificationAwardEndDate"),
    @NamedQuery(name = "PrmsProjectPlan.findByContrctDocmntPrprtioStrtDt", query = "SELECT p FROM PrmsProjectPlan p WHERE p.contrctDocmntPrprtioStrtDt = :contrctDocmntPrprtioStrtDt"),
    @NamedQuery(name = "PrmsProjectPlan.findByContrctDocmntPrprtionEndDt", query = "SELECT p FROM PrmsProjectPlan p WHERE p.contrctDocmntPrprtionEndDt = :contrctDocmntPrprtionEndDt"),
    @NamedQuery(name = "PrmsProjectPlan.findByServicePeriodStrtDt", query = "SELECT p FROM PrmsProjectPlan p WHERE p.servicePeriodStrtDt = :servicePeriodStrtDt"),
    @NamedQuery(name = "PrmsProjectPlan.findByServicePeriodEndDt", query = "SELECT p FROM PrmsProjectPlan p WHERE p.servicePeriodEndDt = :servicePeriodEndDt"),
    @NamedQuery(name = "PrmsProjectPlan.findByContractTerminationStrtDt", query = "SELECT p FROM PrmsProjectPlan p WHERE p.contractTerminationStrtDt = :contractTerminationStrtDt"),
    @NamedQuery(name = "PrmsProjectPlan.findByContractTerminationEndDt", query = "SELECT p FROM PrmsProjectPlan p WHERE p.contractTerminationEndDt = :contractTerminationEndDt"),
    @NamedQuery(name = "PrmsProjectPlan.findByStatus", query = "SELECT p FROM PrmsProjectPlan p WHERE p.status = :status"),
    @NamedQuery(name = "PrmsProjectPlan.findByPurchaseType", query = "SELECT p FROM PrmsProjectPlan p WHERE p.purchaseType = :purchaseType")})
public class PrmsProjectPlan implements Serializable {

    @Column(name = "TERM_OF_REF_STRT_DT")
    @Temporal(TemporalType.TIMESTAMP)
    private Date termOfRefStrtDt;
    @Column(name = "TERM_OF_REF_END_DT")
    @Temporal(TemporalType.TIMESTAMP)
    private Date termOfRefEndDt;
    @Column(name = "ADVR_EXP_INTER_FLOAT_STRT_DT")
    @Temporal(TemporalType.TIMESTAMP)
    private Date advrExpInterFloatStrtDt;
    @Column(name = "ADVR_EXP_INTER_FLOAT_END_DT")
    @Temporal(TemporalType.TIMESTAMP)
    private Date advrExpInterFloatEndDt;
    @Column(name = "EV_EXP_INT_SHORT_LIS_STRT_DT")
    @Temporal(TemporalType.TIMESTAMP)
    private Date evExpIntShortLisStrtDt;
    @Column(name = "EV_EXP_INT_SHORT_LIS_END_DT")
    @Temporal(TemporalType.TIMESTAMP)
    private Date evExpIntShortLisEndDt;
    @Column(name = "SUB_SHRT_REP_REC_STRT_DT")
    @Temporal(TemporalType.TIMESTAMP)
    private Date subShrtRepRecStrtDt;
    @Column(name = "SUB_SHRT_REP_REC_END_DT")
    @Temporal(TemporalType.TIMESTAMP)
    private Date subShrtRepRecEndDt;
    @Column(name = "SUB_SHRT_REP_APP_MGT_STRT_DT")
    @Temporal(TemporalType.TIMESTAMP)
    private Date subShrtRepAppMgtStrtDt;
    @Column(name = "SUB_SHRT_REP_APP_MGT_END_DT")
    @Temporal(TemporalType.TIMESTAMP)
    private Date subShrtRepAppMgtEndDt;
    @Column(name = "ISSUE_RQT_RFP_FLT_PRD_STRT_DT")
    @Temporal(TemporalType.TIMESTAMP)
    private Date issueRqtRfpFltPrdStrtDt;
    @Column(name = "ISSUE_RQT_RFP_FLT_PRD_END_DT")
    @Temporal(TemporalType.TIMESTAMP)
    private Date issueRqtRfpFltPrdEndDt;
    @Column(name = "OPEN_TECH_PROP_STRT_DT")
    @Temporal(TemporalType.TIMESTAMP)
    private Date openTechPropStrtDt;
    @Column(name = "OPEN_TECH_PROP_END_DT")
    @Temporal(TemporalType.TIMESTAMP)
    private Date openTechPropEndDt;
    @Column(name = "EVAL_TECH_STRT_DT")
    @Temporal(TemporalType.TIMESTAMP)
    private Date evalTechStrtDt;
    @Column(name = "EVAL_TECH_END_DT")
    @Temporal(TemporalType.TIMESTAMP)
    private Date evalTechEndDt;
    @Column(name = "CONT_EXEC_COMP_STRT_DT")
    @Temporal(TemporalType.TIMESTAMP)
    private Date contExecCompStrtDt;
    @Column(name = "CONT_EXEC_COMP_END_DT")
    @Temporal(TemporalType.TIMESTAMP)
    private Date contExecCompEndDt;
    @Column(name = "SBMT_TCHNC_MGMT_STRT_DT")
    @Temporal(TemporalType.TIMESTAMP)
    private Date sbmtTchncMgmtStrtDt;
    @Column(name = "SBMT_TCHNC_MGMT_END_DT")
    @Temporal(TemporalType.TIMESTAMP)
    private Date sbmtTchncMgmtEndDt;
    @Column(name = "SBMT_TCHNC_BOARD_STRT_DT")
    @Temporal(TemporalType.TIMESTAMP)
    private Date sbmtTchncBoardStrtDt;
    @Column(name = "SBMT_TCHNC_BOARD_END_DT")
    @Temporal(TemporalType.TIMESTAMP)
    private Date sbmtTchncBoardEndDt;
    @Column(name = "SBMT_TCHNCL_REP_COMITE_STRT")
    @Temporal(TemporalType.TIMESTAMP)
    private Date sbmtTchnclRepComiteStrt;

    @Size(max = 50)
    @Column(name = "PREPARED_BY")
    private String preparedBy;

    @Column(name = "DATE_RIGISTERED")
    @Temporal(TemporalType.DATE)
    private Date dateRigistered;

    @Column(name = "SBMT_TCHNCL_REP_COMITE_END")
    @Temporal(TemporalType.TIMESTAMP)
    private Date sbmtTchnclRepComiteEnd;

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "ID", nullable = false, length = 20)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PRMS_PROJECT_PLAN_SEQ")
    @SequenceGenerator(name = "PRMS_PROJECT_PLAN_SEQ", sequenceName = "PRMS_PROJECT_PLAN_SEQ", allocationSize = 1)

    private String id;
    @Size(max = 20)
    @Column(name = "SERVICE_TYPE")
    private String serviceType;
    @Size(max = 20)
    @Column(name = "FULL_PROCUREMENT_DESCRIPTION")
    private String fullProcurementDescription;
    @Size(max = 20)
    @Column(name = "MARKET_APPROACH")
    private String marketApproach;
    @Size(max = 20)
    @Column(name = "PRE_QUALIFICATION", length = 20)
    private String preQualification;
    @Size(max = 20)
    @Column(name = "PROCUREMENT_PROCESS", length = 20)
    private String procurementProcess;
    @Size(max = 20)
    @Column(name = "SOURCE_OF_FINANCE", length = 20)
    private String sourceOfFinance;
    @Column(name = "LOAN")
    private BigInteger loan;
    @Column(name = "CREDIT")
    private BigInteger credit;
    @Column(name = "ESTIMATED_AMOUNT")
    private BigInteger estimatedAmount;

    @Column(name = "STATUS")
    private Integer status;

    @Column(name = "PREPARE_DOCUMNT_STRT_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date prepareDocumntStrtDate;
    @Column(name = "PREPARE_DOCUMNT_ENDING_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date prepareDocumntEndingDate;
    @Column(name = "ADVRTISE_SPCIFIC_NTICE_STRT_DT")
    @Temporal(TemporalType.TIMESTAMP)
    private Date advrtiseSpcificNticeStrtDt;
    @Column(name = "ADVRTISE_SPCIFIC_NTICE_END_DT")
    @Temporal(TemporalType.TIMESTAMP)
    private Date advrtiseSpcificNticeEndDt;
    @Column(name = "OPEN_PRE_QULIFICTION_STRT_DAT")
    @Temporal(TemporalType.TIMESTAMP)
    private Date openPreQulifictionStrtDat;
    @Column(name = "OPEN_PRE_QULIFICTION_END_DAT")
    @Temporal(TemporalType.TIMESTAMP)
    private Date openPreQulifictionEndDat;
    @Column(name = "PRE_QLF_PRPL_EVL_RPRT_STRT_DT")
    @Temporal(TemporalType.TIMESTAMP)
    private Date preQlfPrplEvlRprtStrtDt;
    @Column(name = "PRE_QLF_PRPL_EVL_RPRT_END_DT")
    @Temporal(TemporalType.TIMESTAMP)
    private Date preQlfPrplEvlRprtEndDt;
    @Column(name = "SUBMISION_EVAL_RECOMM_END_DTE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date submisionEvalRecommEndDte;
    @Column(name = "SUBM_APPRL_EVAL_RPRT_STRT_DT")
    @Temporal(TemporalType.TIMESTAMP)
    private Date submApprlEvalRprtStrtDt;
    @Column(name = "SUBM_APPRVL_EVAL_RPRT_END_DT")
    @Temporal(TemporalType.TIMESTAMP)
    private Date submApprvlEvalRprtEndDt;
    @Column(name = "PRPRE_BID_DOCMENT_STRT_DTE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date prpreBidDocmentStrtDte;
    @Column(name = "PRPRE_BID_DOCMENT_END_DTE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date prpreBidDocmentEndDte;
    @Column(name = "INVTION_SHRT_LST_BIDER_STRT_DT")
    @Temporal(TemporalType.TIMESTAMP)
    private Date invtionShrtLstBiderStrtDt;
    @Column(name = "INVTION_SHRT_LST_BIDER_END_DT")
    @Temporal(TemporalType.TIMESTAMP)
    private Date invtionShrtLstBiderEndDt;
    @Size(max = 50)
    @Column(name = "PROJECT_PLAN_NO", length = 50)
    private String projectPlanNo;

    @Column(name = "SUBMISION_EVAL_RECOMM_STRT_DTE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date submisionEvalRecommStrtDte;
    @Column(name = "BID_OPENING_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date bidOpeningDate;
    @Column(name = "BID_CLOSING_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date bidClosingDate;
    @Column(name = "EVLT_TCHNICL_FINAN_STRT_DTE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date evltTchniclFinanStrtDte;
    @Column(name = "EVLT_TCHNICL_FINAN_END_DTE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date evltTchniclFinanEndDte;
    @Column(name = "SBMT_TCHNCL_FINAN_COMITE_STRT")
    @Temporal(TemporalType.TIMESTAMP)
    private Date sbmtTchnclFinanComiteStrt;
    @Column(name = "SBMT_TCHNCL_FINAN_COMITE_END")
    @Temporal(TemporalType.TIMESTAMP)
    private Date sbmtTchnclFinanComiteEnd;
    @Column(name = "SBMT_TCHNCL_FINAN_MGMT_STRT_DT")
    @Temporal(TemporalType.TIMESTAMP)
    private Date sbmtTchnclFinanMgmtStrtDt;
    @Column(name = "SBMT_TCHNCL_FINAN_MGMT_END_DTE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date sbmtTchnclFinanMgmtEndDte;
    @Column(name = "SBMT_TCHNCL_FINAN_BRORD_END_DT")
    @Temporal(TemporalType.TIMESTAMP)
    private Date sbmtTchnclFinanBrordEndDt;
    @Column(name = "SBMT_TCHNCL_FINAN_BRORD_STRT_D")
    @Temporal(TemporalType.TIMESTAMP)
    private Date sbmtTchnclFinanBrordStrtD;
    @Column(name = "NOTIFICATION_AWARD_STRT_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date notificationAwardStrtDate;
    @Column(name = "NOTIFICATION_AWARD_END_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date notificationAwardEndDate;
    @Column(name = "CONTRCT_DOCMNT_PRPRTIO_STRT_DT")
    @Temporal(TemporalType.TIMESTAMP)
    private Date contrctDocmntPrprtioStrtDt;
    @Column(name = "CONTRCT_DOCMNT_PRPRTION_END_DT")
    @Temporal(TemporalType.TIMESTAMP)
    private Date contrctDocmntPrprtionEndDt;
    @Column(name = "SERVICE_PERIOD_STRT_DT")
    @Temporal(TemporalType.TIMESTAMP)
    private Date servicePeriodStrtDt;
    @Column(name = "SERVICE_PERIOD_END_DT")
    @Temporal(TemporalType.TIMESTAMP)
    private Date servicePeriodEndDt;
    @Column(name = "CONTRACT_TERMINATION_STRT_DT")
    @Temporal(TemporalType.TIMESTAMP)
    private Date contractTerminationStrtDt;
    @Column(name = "CONTRACT_TERMINATION_END_DT")
    @Temporal(TemporalType.TIMESTAMP)
    private Date contractTerminationEndDt;
    @Column(name = "CONSTPRDSTRTDT")
    @Temporal(TemporalType.TIMESTAMP)
    private Date consttrprdsrtdt;
    @Column(name = "CONSTPRDENDTDT")
    @Temporal(TemporalType.TIMESTAMP)
    private Date constprdendtdt;
    @Size(max = 20)
    @Column(name = "PURCHASE_TYPE", length = 20)
    private String purchaseType;
    @OneToMany(mappedBy = "projectPlanId")
    private List<PrmsActionPlan> prmsActionPlanList;
    @OneToMany(mappedBy = "projectProcurementPlanId", fetch = FetchType.LAZY)
    private List<WfPrmsProcessed> wfPrmsProcessedLists;

    @OneToMany(mappedBy = "projectProcurementPlanId", cascade = CascadeType.ALL)
    private List<WfPrmsProcessed> prmsWorkFlowProccedList;
    @JoinColumn(name = "PROJECT_NAME", referencedColumnName = "PROJECT_ID")
    @ManyToOne
    private PmsCreateProjects projectName;
    @Transient
    private String columnName;
    @Transient
    private String columnValue;

    public PrmsProjectPlan() {
    }

    public PrmsProjectPlan(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public PmsCreateProjects getProjectName() {
        return projectName;
    }

    public void setProjectName(PmsCreateProjects projectName) {
        this.projectName = projectName;
    }

    public String getFullProcurementDescription() {
        return fullProcurementDescription;
    }

    public void setFullProcurementDescription(String fullProcurementDescription) {
        this.fullProcurementDescription = fullProcurementDescription;
    }

    public String getMarketApproach() {
        return marketApproach;
    }

    public void setMarketApproach(String marketApproach) {
        this.marketApproach = marketApproach;
    }

    public String getPreQualification() {
        return preQualification;
    }

    public void setPreQualification(String preQualification) {
        this.preQualification = preQualification;
    }

    public String getProcurementProcess() {
        return procurementProcess;
    }

    public void setProcurementProcess(String procurementProcess) {
        this.procurementProcess = procurementProcess;
    }

    public String getPreparedBy() {
        return preparedBy;
    }

    public void setPreparedBy(String preparedBy) {
        this.preparedBy = preparedBy;
    }

    public Date getDateRigistered() {
        return dateRigistered;
    }

    public void setDateRigistered(Date dateRigistered) {
        this.dateRigistered = dateRigistered;
    }

    public String getSourceOfFinance() {
        return sourceOfFinance;
    }

    public void setSourceOfFinance(String sourceOfFinance) {
        this.sourceOfFinance = sourceOfFinance;
    }

    public BigInteger getLoan() {
        return loan;
    }

    public void setLoan(BigInteger loan) {
        this.loan = loan;
    }

    public BigInteger getCredit() {
        return credit;
    }

    public void setCredit(BigInteger credit) {
        this.credit = credit;
    }

    public BigInteger getEstimatedAmount() {
        return estimatedAmount;
    }

    public void setEstimatedAmount(BigInteger estimatedAmount) {
        this.estimatedAmount = estimatedAmount;
    }

    public Date getPrepareDocumntStrtDate() {
        return prepareDocumntStrtDate;
    }

    public void setPrepareDocumntStrtDate(Date prepareDocumntStrtDate) {
        this.prepareDocumntStrtDate = prepareDocumntStrtDate;
    }

    public Date getPrepareDocumntEndingDate() {
        return prepareDocumntEndingDate;
    }

    public void setPrepareDocumntEndingDate(Date prepareDocumntEndingDate) {
        this.prepareDocumntEndingDate = prepareDocumntEndingDate;
    }

    public Date getAdvrtiseSpcificNticeStrtDt() {
        return advrtiseSpcificNticeStrtDt;
    }

    public void setAdvrtiseSpcificNticeStrtDt(Date advrtiseSpcificNticeStrtDt) {
        this.advrtiseSpcificNticeStrtDt = advrtiseSpcificNticeStrtDt;
    }

    public Date getAdvrtiseSpcificNticeEndDt() {
        return advrtiseSpcificNticeEndDt;
    }

    public void setAdvrtiseSpcificNticeEndDt(Date advrtiseSpcificNticeEndDt) {
        this.advrtiseSpcificNticeEndDt = advrtiseSpcificNticeEndDt;
    }

    public Date getOpenPreQulifictionStrtDat() {
        return openPreQulifictionStrtDat;
    }

    public void setOpenPreQulifictionStrtDat(Date openPreQulifictionStrtDat) {
        this.openPreQulifictionStrtDat = openPreQulifictionStrtDat;
    }

    public Date getOpenPreQulifictionEndDat() {
        return openPreQulifictionEndDat;
    }

    public void setOpenPreQulifictionEndDat(Date openPreQulifictionEndDat) {
        this.openPreQulifictionEndDat = openPreQulifictionEndDat;
    }

    public Date getPreQlfPrplEvlRprtStrtDt() {
        return preQlfPrplEvlRprtStrtDt;
    }

    public void setPreQlfPrplEvlRprtStrtDt(Date preQlfPrplEvlRprtStrtDt) {
        this.preQlfPrplEvlRprtStrtDt = preQlfPrplEvlRprtStrtDt;
    }

    public Date getPreQlfPrplEvlRprtEndDt() {
        return preQlfPrplEvlRprtEndDt;
    }

    public void setPreQlfPrplEvlRprtEndDt(Date preQlfPrplEvlRprtEndDt) {
        this.preQlfPrplEvlRprtEndDt = preQlfPrplEvlRprtEndDt;
    }

    public Date getSubmisionEvalRecommEndDte() {
        return submisionEvalRecommEndDte;
    }

    public void setSubmisionEvalRecommEndDte(Date submisionEvalRecommEndDte) {
        this.submisionEvalRecommEndDte = submisionEvalRecommEndDte;
    }

    public Date getSubmApprlEvalRprtStrtDt() {
        return submApprlEvalRprtStrtDt;
    }

    public void setSubmApprlEvalRprtStrtDt(Date submApprlEvalRprtStrtDt) {
        this.submApprlEvalRprtStrtDt = submApprlEvalRprtStrtDt;
    }

    public Date getSubmApprvlEvalRprtEndDt() {
        return submApprvlEvalRprtEndDt;
    }

    public void setSubmApprvlEvalRprtEndDt(Date submApprvlEvalRprtEndDt) {
        this.submApprvlEvalRprtEndDt = submApprvlEvalRprtEndDt;
    }

    public Date getPrpreBidDocmentStrtDte() {
        return prpreBidDocmentStrtDte;
    }

    public void setPrpreBidDocmentStrtDte(Date prpreBidDocmentStrtDte) {
        this.prpreBidDocmentStrtDte = prpreBidDocmentStrtDte;
    }

    public Date getPrpreBidDocmentEndDte() {
        return prpreBidDocmentEndDte;
    }

    public void setPrpreBidDocmentEndDte(Date prpreBidDocmentEndDte) {
        this.prpreBidDocmentEndDte = prpreBidDocmentEndDte;
    }

    public Date getInvtionShrtLstBiderStrtDt() {
        return invtionShrtLstBiderStrtDt;
    }

    public void setInvtionShrtLstBiderStrtDt(Date invtionShrtLstBiderStrtDt) {
        this.invtionShrtLstBiderStrtDt = invtionShrtLstBiderStrtDt;
    }

    public Date getInvtionShrtLstBiderEndDt() {
        return invtionShrtLstBiderEndDt;
    }

    public void setInvtionShrtLstBiderEndDt(Date invtionShrtLstBiderEndDt) {
        this.invtionShrtLstBiderEndDt = invtionShrtLstBiderEndDt;
    }

    public String getProjectPlanNo() {
        return projectPlanNo;
    }

    public void setProjectPlanNo(String projectPlanNo) {
        this.projectPlanNo = projectPlanNo;
    }

    public Date getSubmisionEvalRecommStrtDte() {
        return submisionEvalRecommStrtDte;
    }

    public void setSubmisionEvalRecommStrtDte(Date submisionEvalRecommStrtDte) {
        this.submisionEvalRecommStrtDte = submisionEvalRecommStrtDte;
    }

    public Date getBidOpeningDate() {
        return bidOpeningDate;
    }

    public void setBidOpeningDate(Date bidOpeningDate) {
        this.bidOpeningDate = bidOpeningDate;
    }

    public Date getBidClosingDate() {
        return bidClosingDate;
    }

    public void setBidClosingDate(Date bidClosingDate) {
        this.bidClosingDate = bidClosingDate;
    }

    public Date getEvltTchniclFinanStrtDte() {
        return evltTchniclFinanStrtDte;
    }

    public void setEvltTchniclFinanStrtDte(Date evltTchniclFinanStrtDte) {
        this.evltTchniclFinanStrtDte = evltTchniclFinanStrtDte;
    }

    public Date getEvltTchniclFinanEndDte() {
        return evltTchniclFinanEndDte;
    }

    public void setEvltTchniclFinanEndDte(Date evltTchniclFinanEndDte) {
        this.evltTchniclFinanEndDte = evltTchniclFinanEndDte;
    }

    public Date getSbmtTchnclFinanComiteStrt() {
        return sbmtTchnclFinanComiteStrt;
    }

    public void setSbmtTchnclFinanComiteStrt(Date sbmtTchnclFinanComiteStrt) {
        this.sbmtTchnclFinanComiteStrt = sbmtTchnclFinanComiteStrt;
    }

    public Date getSbmtTchnclFinanComiteEnd() {
        return sbmtTchnclFinanComiteEnd;
    }

    public void setSbmtTchnclFinanComiteEnd(Date sbmtTchnclFinanComiteEnd) {
        this.sbmtTchnclFinanComiteEnd = sbmtTchnclFinanComiteEnd;
    }

    public Date getSbmtTchnclFinanMgmtStrtDt() {
        return sbmtTchnclFinanMgmtStrtDt;
    }

    public void setSbmtTchnclFinanMgmtStrtDt(Date sbmtTchnclFinanMgmtStrtDt) {
        this.sbmtTchnclFinanMgmtStrtDt = sbmtTchnclFinanMgmtStrtDt;
    }

    public Date getSbmtTchnclFinanMgmtEndDte() {
        return sbmtTchnclFinanMgmtEndDte;
    }

    public void setSbmtTchnclFinanMgmtEndDte(Date sbmtTchnclFinanMgmtEndDte) {
        this.sbmtTchnclFinanMgmtEndDte = sbmtTchnclFinanMgmtEndDte;
    }

    public Date getSbmtTchnclFinanBrordEndDt() {
        return sbmtTchnclFinanBrordEndDt;
    }

    public void setSbmtTchnclFinanBrordEndDt(Date sbmtTchnclFinanBrordEndDt) {
        this.sbmtTchnclFinanBrordEndDt = sbmtTchnclFinanBrordEndDt;
    }

    public Date getSbmtTchnclFinanBrordStrtD() {
        return sbmtTchnclFinanBrordStrtD;
    }

    public void setSbmtTchnclFinanBrordStrtD(Date sbmtTchnclFinanBrordStrtD) {
        this.sbmtTchnclFinanBrordStrtD = sbmtTchnclFinanBrordStrtD;
    }

    public Date getNotificationAwardStrtDate() {
        return notificationAwardStrtDate;
    }

    public void setNotificationAwardStrtDate(Date notificationAwardStrtDate) {
        this.notificationAwardStrtDate = notificationAwardStrtDate;
    }

    public Date getNotificationAwardEndDate() {
        return notificationAwardEndDate;
    }

    public void setNotificationAwardEndDate(Date notificationAwardEndDate) {
        this.notificationAwardEndDate = notificationAwardEndDate;
    }

    public Date getContrctDocmntPrprtioStrtDt() {
        return contrctDocmntPrprtioStrtDt;
    }

    public void setContrctDocmntPrprtioStrtDt(Date contrctDocmntPrprtioStrtDt) {
        this.contrctDocmntPrprtioStrtDt = contrctDocmntPrprtioStrtDt;
    }

    public Date getContrctDocmntPrprtionEndDt() {
        return contrctDocmntPrprtionEndDt;
    }

    public void setContrctDocmntPrprtionEndDt(Date contrctDocmntPrprtionEndDt) {
        this.contrctDocmntPrprtionEndDt = contrctDocmntPrprtionEndDt;
    }

    public Date getServicePeriodStrtDt() {
        return servicePeriodStrtDt;
    }

    public void setServicePeriodStrtDt(Date servicePeriodStrtDt) {
        this.servicePeriodStrtDt = servicePeriodStrtDt;
    }

    public Date getServicePeriodEndDt() {
        return servicePeriodEndDt;
    }

    public void setServicePeriodEndDt(Date servicePeriodEndDt) {
        this.servicePeriodEndDt = servicePeriodEndDt;
    }

    public Date getContractTerminationStrtDt() {
        return contractTerminationStrtDt;
    }

    public void setContractTerminationStrtDt(Date contractTerminationStrtDt) {
        this.contractTerminationStrtDt = contractTerminationStrtDt;
    }

    public Date getContractTerminationEndDt() {
        return contractTerminationEndDt;
    }

    public void setContractTerminationEndDt(Date contractTerminationEndDt) {
        this.contractTerminationEndDt = contractTerminationEndDt;
    }

    public String getPurchaseType() {
        return purchaseType;
    }

    public void setPurchaseType(String purchaseType) {
        this.purchaseType = purchaseType;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PrmsProjectPlan)) {
            return false;
        }
        PrmsProjectPlan other = (PrmsProjectPlan) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "et.gov.eep.prms.entity.PrmsProjectPlan[ id=" + id + " ]";
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public Date getTermOfRefStrtDt() {
        return termOfRefStrtDt;
    }

    public void setTermOfRefStrtDt(Date termOfRefStrtDt) {
        this.termOfRefStrtDt = termOfRefStrtDt;
    }

    public Date getTermOfRefEndDt() {
        return termOfRefEndDt;
    }

    public void setTermOfRefEndDt(Date termOfRefEndDt) {
        this.termOfRefEndDt = termOfRefEndDt;
    }

    public Date getAdvrExpInterFloatStrtDt() {
        return advrExpInterFloatStrtDt;
    }

    public void setAdvrExpInterFloatStrtDt(Date advrExpInterFloatStrtDt) {
        this.advrExpInterFloatStrtDt = advrExpInterFloatStrtDt;
    }

    public Date getAdvrExpInterFloatEndDt() {
        return advrExpInterFloatEndDt;
    }

    public void setAdvrExpInterFloatEndDt(Date advrExpInterFloatEndDt) {
        this.advrExpInterFloatEndDt = advrExpInterFloatEndDt;
    }

    public Date getEvExpIntShortLisStrtDt() {
        return evExpIntShortLisStrtDt;
    }

    public void setEvExpIntShortLisStrtDt(Date evExpIntShortLisStrtDt) {
        this.evExpIntShortLisStrtDt = evExpIntShortLisStrtDt;
    }

    public Date getEvExpIntShortLisEndDt() {
        return evExpIntShortLisEndDt;
    }

    public void setEvExpIntShortLisEndDt(Date evExpIntShortLisEndDt) {
        this.evExpIntShortLisEndDt = evExpIntShortLisEndDt;
    }

    public Date getSubShrtRepRecStrtDt() {
        return subShrtRepRecStrtDt;
    }

    public void setSubShrtRepRecStrtDt(Date subShrtRepRecStrtDt) {
        this.subShrtRepRecStrtDt = subShrtRepRecStrtDt;
    }

    public Date getSubShrtRepRecEndDt() {
        return subShrtRepRecEndDt;
    }

    public void setSubShrtRepRecEndDt(Date subShrtRepRecEndDt) {
        this.subShrtRepRecEndDt = subShrtRepRecEndDt;
    }

    public Date getSubShrtRepAppMgtStrtDt() {
        return subShrtRepAppMgtStrtDt;
    }

    public void setSubShrtRepAppMgtStrtDt(Date subShrtRepAppMgtStrtDt) {
        this.subShrtRepAppMgtStrtDt = subShrtRepAppMgtStrtDt;
    }

    public Date getSubShrtRepAppMgtEndDt() {
        return subShrtRepAppMgtEndDt;
    }

    public void setSubShrtRepAppMgtEndDt(Date subShrtRepAppMgtEndDt) {
        this.subShrtRepAppMgtEndDt = subShrtRepAppMgtEndDt;
    }

    public Date getIssueRqtRfpFltPrdStrtDt() {
        return issueRqtRfpFltPrdStrtDt;
    }

    public void setIssueRqtRfpFltPrdStrtDt(Date issueRqtRfpFltPrdStrtDt) {
        this.issueRqtRfpFltPrdStrtDt = issueRqtRfpFltPrdStrtDt;
    }

    public Date getIssueRqtRfpFltPrdEndDt() {
        return issueRqtRfpFltPrdEndDt;
    }

    public void setIssueRqtRfpFltPrdEndDt(Date issueRqtRfpFltPrdEndDt) {
        this.issueRqtRfpFltPrdEndDt = issueRqtRfpFltPrdEndDt;
    }

    public Date getOpenTechPropStrtDt() {
        return openTechPropStrtDt;
    }

    public void setOpenTechPropStrtDt(Date openTechPropStrtDt) {
        this.openTechPropStrtDt = openTechPropStrtDt;
    }

    public Date getOpenTechPropEndDt() {
        return openTechPropEndDt;
    }

    public void setOpenTechPropEndDt(Date openTechPropEndDt) {
        this.openTechPropEndDt = openTechPropEndDt;
    }

    public Date getEvalTechStrtDt() {
        return evalTechStrtDt;
    }

    public void setEvalTechStrtDt(Date evalTechStrtDt) {
        this.evalTechStrtDt = evalTechStrtDt;
    }

    public Date getEvalTechEndDt() {
        return evalTechEndDt;
    }

    public void setEvalTechEndDt(Date evalTechEndDt) {
        this.evalTechEndDt = evalTechEndDt;
    }

    public Date getContExecCompStrtDt() {
        return contExecCompStrtDt;
    }

    public void setContExecCompStrtDt(Date contExecCompStrtDt) {
        this.contExecCompStrtDt = contExecCompStrtDt;
    }

    public Date getContExecCompEndDt() {
        return contExecCompEndDt;
    }

    public void setContExecCompEndDt(Date contExecCompEndDt) {
        this.contExecCompEndDt = contExecCompEndDt;
    }

    public Date getSbmtTchncMgmtStrtDt() {
        return sbmtTchncMgmtStrtDt;
    }

    public void setSbmtTchncMgmtStrtDt(Date sbmtTchncMgmtStrtDt) {
        this.sbmtTchncMgmtStrtDt = sbmtTchncMgmtStrtDt;
    }

    public Date getSbmtTchncMgmtEndDt() {
        return sbmtTchncMgmtEndDt;
    }

    public void setSbmtTchncMgmtEndDt(Date sbmtTchncMgmtEndDt) {
        this.sbmtTchncMgmtEndDt = sbmtTchncMgmtEndDt;
    }

    public Date getSbmtTchncBoardStrtDt() {
        return sbmtTchncBoardStrtDt;
    }

    public void setSbmtTchncBoardStrtDt(Date sbmtTchncBoardStrtDt) {
        this.sbmtTchncBoardStrtDt = sbmtTchncBoardStrtDt;
    }

    public Date getSbmtTchncBoardEndDt() {
        return sbmtTchncBoardEndDt;
    }

    public void setSbmtTchncBoardEndDt(Date sbmtTchncBoardEndDt) {
        this.sbmtTchncBoardEndDt = sbmtTchncBoardEndDt;
    }

    public Date getSbmtTchnclRepComiteStrt() {
        return sbmtTchnclRepComiteStrt;
    }

    public void setSbmtTchnclRepComiteStrt(Date sbmtTchnclRepComiteStrt) {
        this.sbmtTchnclRepComiteStrt = sbmtTchnclRepComiteStrt;
    }

    public Date getSbmtTchnclRepComiteEnd() {
        return sbmtTchnclRepComiteEnd;
    }

    public void setSbmtTchnclRepComiteEnd(Date sbmtTchnclRepComiteEnd) {
        this.sbmtTchnclRepComiteEnd = sbmtTchnclRepComiteEnd;
    }

    public Date getConsttrprdsrtdt() {
        return consttrprdsrtdt;
    }

    public void setConsttrprdsrtdt(Date consttrprdsrtdt) {
        this.consttrprdsrtdt = consttrprdsrtdt;
    }

    public Date getConstprdendtdt() {
        return constprdendtdt;
    }

    public void setConstprdendtdt(Date constprdendtdt) {
        this.constprdendtdt = constprdendtdt;
    }

    @XmlTransient

    public List<WfPrmsProcessed> getWfPrmsProcessedCollection() {
        return wfPrmsProcessedLists;
    }

    public void setWfPrmsProcessedCollection(List<WfPrmsProcessed> wfPrmsProcessedLists) {
        this.wfPrmsProcessedLists = wfPrmsProcessedLists;
    }

    @XmlTransient
    public List<WfPrmsProcessed> getPrmsWorkFlowProccedList() {
        if (prmsWorkFlowProccedList == null) {
            prmsWorkFlowProccedList = new ArrayList<>();
        }
        return prmsWorkFlowProccedList;
    }

    public void setHrWorkFlowProccedList(List<WfPrmsProcessed> prmsWorkFlowProccedList) {
        this.prmsWorkFlowProccedList = prmsWorkFlowProccedList;
    }

}
