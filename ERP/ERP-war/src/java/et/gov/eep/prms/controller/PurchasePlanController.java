/**
 * ****************************************************************************
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates and open the template
 * in the editor.
 * ****************************************************************************
 */
package et.gov.eep.prms.controller;

   // <editor-fold defaultstate="collapsed" desc="Imports">
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.ArrayDataModel;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import javax.faces.validator.ValidatorException;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import org.primefaces.event.RowEditEvent;
import org.primefaces.event.SelectEvent;
import et.gov.eep.commonApplications.businessLogic.EthiopianCalendarBeanLocal;
import et.gov.eep.commonApplications.businessLogic.WfPrmsProcessedBeanLocal;
import et.gov.eep.commonApplications.entity.WfPrmsProcessed;
import et.gov.eep.mms.entity.MmsItemRegistration;
import et.gov.eep.prms.businessLogic.MarketAssesmntBeanLocal;
import et.gov.eep.prms.businessLogic.PurchasePlanBeanLocal;
import et.gov.eep.prms.entity.PrmsPurchasePlan;
import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.fcms.businessLogic.admin.FmsAccountingPeriodBeanLocal;
import et.gov.eep.fcms.businessLogic.budgetYearLookUpBeanLocal;
import et.gov.eep.fcms.entity.FmsLuBudgetYear;
import et.gov.eep.fcms.entity.admin.FmsAccountingPeriod;
import et.gov.eep.mms.businessLogic.MmsCategoryBeanLocal;
import et.gov.eep.mms.businessLogic.MmsNeedAssessmentDtlBeanLocal;
import et.gov.eep.mms.entity.MmsCategory;
import et.gov.eep.mms.entity.MmsNeedAssessment;
import et.gov.eep.mms.entity.MmsNeedAssessmentDtl;
import et.gov.eep.mms.entity.MmsNeedAssessmentService;
import et.gov.eep.prms.businessLogic.PurchaseReqBeanLocal;
import et.gov.eep.prms.businessLogic.ServiceRegistrationBeanLocal;
import et.gov.eep.prms.entity.PrmsAnnualPlanService;
import et.gov.eep.prms.entity.PrmsPurchasePlnDetail;
import et.gov.eep.prms.entity.PrmsPurchaseRequest;
import et.gov.eep.prms.entity.PrmsPurchaseRequestDet;
import et.gov.eep.prms.entity.PrmsServiceAndWorkReg;
import securityBean.Constants;
import securityBean.SessionBean;
import securityBean.WorkFlow;
import webService.AAA;
import webService.EventEntry;
import webService.IAdministration;
// </editor-fold>

/**
 * Purchase Plan page view scoped Managed Bean class
 *
 * @author user
 */
//Purchase Plan page view scoped CDI Named Bean class
@Named("purchasePlanController")
@ViewScoped
public class PurchasePlanController implements Serializable {

    // <editor-fold defaultstate="collapsed" desc="Inject EJBs">
    @EJB
    PurchasePlanBeanLocal purchasePlanBeanLocal;
    @EJB
    MarketAssesmntBeanLocal marketAssesmntBeanLocal;
    @EJB
    PurchaseReqBeanLocal purchaseReqBeanLocal;
    @EJB
    ServiceRegistrationBeanLocal serviceBeanlocal;
    @EJB
    WfPrmsProcessedBeanLocal wfPrmsProcessedBeanLocal;
    @EJB
    MmsNeedAssessmentDtlBeanLocal needAssessmentDtlBeanLocal;
    @EJB
    private FmsAccountingPeriodBeanLocal accountingPeriodBean;
    @EJB
    private budgetYearLookUpBeanLocal budgetYearLookUpBean;
    @EJB
    MmsCategoryBeanLocal catBeanLocal;
    @EJB
    EthiopianCalendarBeanLocal ethiopianCalendarBeanLocal;
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Inject Entities">
    @Inject
    PrmsPurchasePlan eepPurchasePlan;
    @Inject
    MmsCategory category;
    @Inject
    PrmsPurchasePlnDetail eepPurchasePlnDetail;
    @Inject
    MmsItemRegistration eepMmsItemRegistration;
    @Inject
    PrmsServiceAndWorkReg eepPrmsServiceAndWorkReg;
    @Inject
    MmsNeedAssessmentDtl mmsNeedAssessmentDtl;
    @Inject
    MmsNeedAssessment mmsNeedAssessment;
    @Inject
    PrmsPurchaseRequest prmsPurchaseRequest;
    @Inject
    PrmsPurchasePlnDetail prmsPurchasePlnDetail;
    @Inject
    PrmsAnnualPlanService prmsAnnualPlanService;
    @Inject
    MmsNeedAssessmentService needAssessmentService;
    @Inject
    WfPrmsProcessed wfPrmsProcessed;
    @Inject
    WorkFlow workFlow;
    @Inject
    SessionBean SessionBean;
    @Inject
    FmsLuBudgetYear fmsLubudgetYear;
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Declare instance variables">
    private boolean disableBtnCreate = false;
    private boolean renderPnlCreateParty = false;
    private boolean renderPnlManPage = true;
    private boolean renderPnlToSearchPage;
    private boolean service;
    private boolean serviceAndWork;
    private boolean item;
    private boolean work;
    private boolean itemTable;
    private boolean serviceTable;
    private boolean workTable;
    private boolean procurmentType = false;
    private boolean matCategoryType = false;
    private boolean isSelect = false;
    boolean renderDecision;
    boolean isRenderCreate;

    private int selectedRowIndex = 0;
    int rowindextemp = 0;
    int updateStatus = 0;
    int requestNotificationCounter = 0;

    String purchaseType = "";
    private String saveorUpdateBundle = "Save";
    private String createOrSearchBundle = "New";
    private String headerTitle = "Search Annual Plan";
    private String icone = "ui-icon-plus";
    private String duplicattion = null;
    private String userName;
    private String selectedValue = "";
    String logerName;
    String newPayNo;
    String lastPaymentNo = "0";
    String newAnnualPlanNo;
    String LastAnnualPlanNo = "0";

    Date temporaryDate = new Date();
    PrmsPurchasePlan prmsPurchasePlanSelected;
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Declare Lists and models">
    DataModel<PrmsPurchaseRequestDet> purchaseRequestDetMdel;
    DataModel<PrmsPurchasePlnDetail> purchasePlnDetailModel;
    DataModel<PrmsPurchasePlan> prmsPurchasePlanMdl;
    DataModel<PrmsAnnualPlanService> prmsAnnualPlanServiceMdl;
    DataModel<PrmsAnnualPlanService> annualPlanServiceFrWork;
    DataModel<WfPrmsProcessed> wfPrmsProcessedDModel;
    DataModel<MmsNeedAssessmentDtl> mmsNeedAssessmentModel;
    List<PrmsPurchasePlan> planSearchParameterLst;
    ArrayList<MmsItemRegistration> itemRegistrations;
    List<MmsNeedAssessment> needAssessmentList;
    List<MmsNeedAssessmentDtl> mmsNeedAssessmentDtlLst;
    List<PrmsPurchasePlan> purchasePlanList;
    List<PrmsPurchasePlnDetail> prmsPurchasePlnDetailLst;
    List<PrmsAnnualPlanService> prmsAnnualPlanServiceList;
    List<MmsNeedAssessmentService> mmsNeedAssessmentServiceList;
    List<MmsNeedAssessment> mmsNeedAssessmentList;
    List<MmsCategory> CategoryList;
    private List<PrmsPurchasePlan> purchasePlanLis1;
// </editor-fold>

    // life cycle call back method    
    @PostConstruct
    public void initialize() {

        setLogerName(SessionBean.getUserName());
        setUserName(workFlow.getUserName());
        setLogerName(SessionBean.getUserName());
        wfPrmsProcessed.setProcessedBy(SessionBean.getUserId());
        purchasePlanLis1 = purchasePlanBeanLocal.searchByPlanNo_();
        fmsLubudgetYear = null;//eepPurchasePlan.getBudgetYearId();

        if (workFlow.isPrepareStatus()) {
            renderDecision = false;
            isRenderCreate = true;
        }
        if (workFlow.isApproveStatus()) {
            renderDecision = true;
            isRenderCreate = false;
        }
        if (workFlow.isCheckStatus()) {
            renderDecision = true;
            isRenderCreate = false;
        }
        if (workFlow.isReadonly()) {
            setProcurmentType(true);
        } else {
            setProcurmentType(false);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="getter and setter of objects">
    public MmsNeedAssessment getMmsNeedAssessment() {
        if (mmsNeedAssessment == null) {
            mmsNeedAssessment = new MmsNeedAssessment();
        }
        return mmsNeedAssessment;
    }

    public void setMmsNeedAssessment(MmsNeedAssessment mmsNeedAssessment) {
        this.mmsNeedAssessment = mmsNeedAssessment;
    }

    public MmsNeedAssessmentDtl getMmsNeedAssessmentDtl() {
        if (mmsNeedAssessmentDtl == null) {
            mmsNeedAssessmentDtl = new MmsNeedAssessmentDtl();
        }
        return mmsNeedAssessmentDtl;
    }

    public void setMmsNeedAssessmentDtl(MmsNeedAssessmentDtl mmsNeedAssessmentDtl) {
        this.mmsNeedAssessmentDtl = mmsNeedAssessmentDtl;
    }

    public WfPrmsProcessed getWfPrmsProcessed() {
        if (wfPrmsProcessed == null) {
            wfPrmsProcessed = new WfPrmsProcessed();
        }
        return wfPrmsProcessed;
    }

    public void setWfPrmsProcessed(WfPrmsProcessed wfPrmsProcessed) {
        this.wfPrmsProcessed = wfPrmsProcessed;
    }

    public FmsLuBudgetYear getFmsLubudgetYear() {
        if (fmsLubudgetYear == null) {
            fmsLubudgetYear = new FmsLuBudgetYear();
        }
        return fmsLubudgetYear;
    }

    public void setFmsLubudgetYear(FmsLuBudgetYear fmsLubudgetYear) {
        this.fmsLubudgetYear = fmsLubudgetYear;
    }

    public PrmsAnnualPlanService getPrmsAnnualPlanService() {
        if (prmsAnnualPlanService == null) {
            prmsAnnualPlanService = new PrmsAnnualPlanService();
        }
        return prmsAnnualPlanService;
    }

    public void setPrmsAnnualPlanService(PrmsAnnualPlanService prmsAnnualPlanService) {
        this.prmsAnnualPlanService = prmsAnnualPlanService;
    }

    public MmsCategory getCategory() {
        if (category == null) {
            category = new MmsCategory();
        }
        return category;
    }

    public void setCategory(MmsCategory category) {
        this.category = category;
    }

    public WorkFlow getWorkFlow() {
        if (workFlow == null) {
            workFlow = new WorkFlow();
        }
        return workFlow;
    }

    public void setWorkFlow(WorkFlow workFlow) {
        this.workFlow = workFlow;
    }

    public MmsNeedAssessmentService getNeedAssessmentService() {
        if (needAssessmentService == null) {
            needAssessmentService = new MmsNeedAssessmentService();
        }
        return needAssessmentService;
    }

    public void setNeedAssessmentService(MmsNeedAssessmentService needAssessmentService) {
        this.needAssessmentService = needAssessmentService;
    }

    public PrmsPurchasePlan getEepPurchasePlan() {
        if (eepPurchasePlan == null) {
            eepPurchasePlan = new PrmsPurchasePlan();
        }
        return eepPurchasePlan;
    }

    public void setEepPurchasePlan(PrmsPurchasePlan eepPurchasePlan) {
        this.eepPurchasePlan = eepPurchasePlan;
    }

    public PrmsPurchasePlnDetail getEepPurchasePlnDetail() {
        if (eepPurchasePlnDetail == null) {
            eepPurchasePlnDetail = new PrmsPurchasePlnDetail();

        }
        return eepPurchasePlnDetail;
    }

    public void setEepPurchasePlnDetail(PrmsPurchasePlnDetail eepPurchasePlnDetail) {
        this.eepPurchasePlnDetail = eepPurchasePlnDetail;
    }

    public MmsItemRegistration getEepMmsItemRegistration() {

        if (eepMmsItemRegistration == null) {
            eepMmsItemRegistration = new MmsItemRegistration();
        }

        return eepMmsItemRegistration;
    }

    public void setEepMmsItemRegistration(
            MmsItemRegistration eepMmsItemRegistration) {
        this.eepMmsItemRegistration = eepMmsItemRegistration;
    }

    public PrmsServiceAndWorkReg getEepPrmsServiceAndWorkReg() {

        if (eepPrmsServiceAndWorkReg == null) {
            eepPrmsServiceAndWorkReg = new PrmsServiceAndWorkReg();
        }

        return eepPrmsServiceAndWorkReg;
    }

    public void setEepPrmsServiceAndWorkReg(
            PrmsServiceAndWorkReg eepPrmsServiceAndWorkReg) {
        this.eepPrmsServiceAndWorkReg = eepPrmsServiceAndWorkReg;
    }

    public PrmsPurchaseRequest getPrmsPurchaseRequest() {
        if (prmsPurchaseRequest == null) {
            prmsPurchaseRequest = new PrmsPurchaseRequest();
        }
        return prmsPurchaseRequest;
    }

    public void setPrmsPurchaseRequest(PrmsPurchaseRequest prmsPurchaseRequest) {
        this.prmsPurchaseRequest = prmsPurchaseRequest;
    }

    public PrmsPurchasePlnDetail getPrmsPurchasePlnDetail() {
        if (prmsPurchasePlnDetail == null) {
            prmsPurchasePlnDetail = new PrmsPurchasePlnDetail();
        }
        return prmsPurchasePlnDetail;
    }

    public void setPrmsPurchasePlnDetail(PrmsPurchasePlnDetail prmsPurchasePlnDetail) {
        this.prmsPurchasePlnDetail = prmsPurchasePlnDetail;
    }

    public PrmsPurchasePlan getPrmsPurchasePlanSelected() {
        return prmsPurchasePlanSelected;
    }

    public void setPrmsPurchasePlanSelected(PrmsPurchasePlan prmsPurchasePlanSelected) {
        this.prmsPurchasePlanSelected = prmsPurchasePlanSelected;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="getter and setter of Variables">
    //instance Variables
    public int getSelectedRowIndex() {
        return selectedRowIndex;
    }

    public void setSelectedRowIndex(int selectedRowIndex) {
        this.selectedRowIndex = selectedRowIndex;
    }

    public String getLogerName() {
        return logerName;
    }

    public void setLogerName(String logerName) {
        this.logerName = logerName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getIcone() {
        return icone;
    }

    public void setIcone(String icone) {
        this.icone = icone;
    }

    public String getHeaderTitle() {
        return headerTitle;
    }

    public void setHeaderTitle(String headerTitle) {
        this.headerTitle = headerTitle;
    }

    public String getDuplicattion() {
        return duplicattion;
    }

    public void setDuplicattion(String duplicattion) {
        this.duplicattion = duplicattion;
    }

    public String getSaveorUpdateBundle() {
        return saveorUpdateBundle;
    }

    public void setSaveorUpdateBundle(String saveorUpdateBundle) {
        this.saveorUpdateBundle = saveorUpdateBundle;
    }

    public String getCreateOrSearchBundle() {
        return createOrSearchBundle;
    }

    public void setCreateOrSearchBundle(String createOrSearchBundle) {
        this.createOrSearchBundle = createOrSearchBundle;
    }

    public boolean isDisableBtnCreate() {
        return disableBtnCreate;
    }

    public void setDisableBtnCreate(boolean disableBtnCreate) {
        this.disableBtnCreate = disableBtnCreate;
    }

    public boolean isRenderPnlCreateParty() {
        return renderPnlCreateParty;
    }

    public void setRenderPnlCreateParty(boolean renderPnlCreateParty) {
        this.renderPnlCreateParty = renderPnlCreateParty;
    }

    public boolean isService() {
        return service;
    }

    public void setService(boolean service) {
        this.service = service;
    }

    public boolean isItem() {
        return item;
    }

    public void setItem(boolean item) {
        this.item = item;
    }

    public boolean isWork() {
        return work;
    }

    public void setWork(boolean work) {
        this.work = work;
    }

    public boolean isRenderDecision() {
        return renderDecision;
    }

    public void setRenderDecision(boolean renderDecision) {
        this.renderDecision = renderDecision;
    }

    public boolean isIsRenderCreate() {
        return isRenderCreate;
    }

    public void setIsRenderCreate(boolean isRenderCreate) {
        this.isRenderCreate = isRenderCreate;
    }

    public boolean isProcurmentType() {
        return procurmentType;
    }

    public void setProcurmentType(boolean procurmentType) {
        this.procurmentType = procurmentType;
    }

    public boolean isMatCategoryType() {
        return matCategoryType;
    }

    public void setMatCategoryType(boolean matCategoryType) {
        this.matCategoryType = matCategoryType;
    }

    public boolean isItemTable() {
        return itemTable;
    }

    public void setItemTable(boolean itemTable) {
        this.itemTable = itemTable;
    }

    public boolean isServiceTable() {
        return serviceTable;
    }

    public void setServiceTable(boolean serviceTable) {
        this.serviceTable = serviceTable;
    }

    public boolean isRenderPnlManPage() {
        return renderPnlManPage;
    }

    public void setRenderPnlManPage(boolean renderPnlManPage) {
        this.renderPnlManPage = renderPnlManPage;
    }

    public boolean isRenderPnlToSearchPage() {
        return renderPnlToSearchPage;
    }

    public void setRenderPnlToSearchPage(boolean renderPnlToSearchPage) {
        this.renderPnlToSearchPage = renderPnlToSearchPage;
    }

    public boolean isServiceAndWork() {
        return serviceAndWork;
    }

    public void setServiceAndWork(boolean serviceAndWork) {
        this.serviceAndWork = serviceAndWork;
    }

    public boolean isWorkTable() {
        return workTable;
    }

    public void setWorkTable(boolean workTable) {
        this.workTable = workTable;
    }

    public int getRequestNotificationCounter() {
        purchasePlanLis1 = purchasePlanBeanLocal.searchByPlanNo_();
        requestNotificationCounter = purchasePlanLis1.size();
        return requestNotificationCounter;
    }

    public void setRequestNotificationCounter(int requestNotificationCounter) {
        this.requestNotificationCounter = requestNotificationCounter;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="getter and setter of lists and models">
//Lists
    public List<MmsNeedAssessmentDtl> getMmsNeedAssessmentDtlLst() {
        if (mmsNeedAssessmentDtlLst == null) {
            mmsNeedAssessmentDtlLst = new ArrayList<>();
        }
        return mmsNeedAssessmentDtlLst;
    }

    public void setMmsNeedAssessmentDtlLst(List<MmsNeedAssessmentDtl> mmsNeedAssessmentDtlLst) {
        this.mmsNeedAssessmentDtlLst = mmsNeedAssessmentDtlLst;
    }

    public List<PrmsPurchasePlan> getPurchasePlanLis1() {
        if (purchasePlanLis1 == null) {
            purchasePlanLis1 = new ArrayList<>();
        }
        return purchasePlanLis1;
    }

    public void setPurchasePlanLis1(List<PrmsPurchasePlan> purchasePlanLis1) {
        this.purchasePlanLis1 = purchasePlanLis1;
    }

    public List<MmsCategory> getListsOfCategory() {
        if (CategoryList == null) {
            CategoryList = new ArrayList<>();
        }
        CategoryList = purchasePlanBeanLocal.findItemsCategoriesList();
        return CategoryList;
    }

    public void setCategoryList(List<MmsCategory> CategoryList) {
        this.CategoryList = CategoryList;
    }

    public List<MmsNeedAssessment> getMmsNeedAssessmentList() {
        if (mmsNeedAssessmentList == null) {
            mmsNeedAssessmentList = new ArrayList<>();
        }
        return mmsNeedAssessmentList;
    }

    public void setMmsNeedAssessmentList(List<MmsNeedAssessment> mmsNeedAssessmentList) {
        this.mmsNeedAssessmentList = mmsNeedAssessmentList;
    }

    public List<MmsNeedAssessmentService> getMmsNeedAssessmentServiceList() {
        if (mmsNeedAssessmentServiceList == null) {
            mmsNeedAssessmentServiceList = new ArrayList<>();
        }
        return mmsNeedAssessmentServiceList;
    }

    public void setMmsNeedAssessmentServiceList(
            List<MmsNeedAssessmentService> mmsNeedAssessmentServiceList) {
        this.mmsNeedAssessmentServiceList = mmsNeedAssessmentServiceList;
    }

    public List<PrmsAnnualPlanService> getPrmsAnnualPlanServiceList() {
        if (prmsAnnualPlanServiceList == null) {
            prmsAnnualPlanServiceList = new ArrayList<>();
        }
        return prmsAnnualPlanServiceList;
    }

    public void setPrmsAnnualPlanServiceList(List<PrmsAnnualPlanService> prmsAnnualPlanServiceList) {
        this.prmsAnnualPlanServiceList = prmsAnnualPlanServiceList;
    }

    public List<PrmsPurchasePlnDetail> getPrmsPurchasePlnDetailLst() {
        if (prmsPurchasePlnDetailLst == null) {
            prmsPurchasePlnDetailLst = new ArrayList<>();
        }
        return prmsPurchasePlnDetailLst;
    }

    public void setPrmsPurchasePlnDetailLst(List<PrmsPurchasePlnDetail> prmsPurchasePlnDetailLst) {
        this.prmsPurchasePlnDetailLst = prmsPurchasePlnDetailLst;
    }

    public List<PrmsPurchasePlan> getPurchasePlanList() {
        if (purchasePlanList == null) {
            purchasePlanList = new ArrayList<>();
        }
        return purchasePlanList;
    }

    public void setPurchasePlanList(List<PrmsPurchasePlan> purchasePlanList) {
        this.purchasePlanList = purchasePlanList;
    }

    public List<MmsNeedAssessment> getNeedAssessmentList() {
        return needAssessmentList;
    }

    public void setNeedAssessmentList(List<MmsNeedAssessment> needAssessmentList) {
        this.needAssessmentList = needAssessmentList;
    }

    public ArrayList<MmsItemRegistration> getItemRegistrations() {
        return itemRegistrations;
    }

    public void setItemRegistrations(ArrayList<MmsItemRegistration> itemRegistrations) {
        this.itemRegistrations = itemRegistrations;
    }

    //DataModels
    public DataModel<WfPrmsProcessed> getWfPrmsProcessedDModel() {
        if (wfPrmsProcessedDModel == null) {
            wfPrmsProcessedDModel = new ArrayDataModel<>();
        }
        return wfPrmsProcessedDModel;
    }

    public void setWfPrmsProcessedDModel(
            DataModel<WfPrmsProcessed> wfPrmsProcessedDModel) {
        this.wfPrmsProcessedDModel = wfPrmsProcessedDModel;
    }

    public DataModel<PrmsPurchasePlan> getPrmsPurchasePlanMdl() {
        if (prmsPurchasePlanMdl == null) {
            prmsPurchasePlanMdl = new ListDataModel<>();
        }
        return prmsPurchasePlanMdl;
    }

    public void setPrmsPurchasePlanMdl(DataModel<PrmsPurchasePlan> prmsPurchasePlanMdl) {
        this.prmsPurchasePlanMdl = prmsPurchasePlanMdl;
    }

    public DataModel<PrmsPurchaseRequestDet> getPurchaseRequestDetMdel() {
        if (purchaseRequestDetMdel == null) {
            purchaseRequestDetMdel = new ListDataModel<>();
        }
        return purchaseRequestDetMdel;
    }

    public void setPurchaseRequestDetMdel(DataModel<PrmsPurchaseRequestDet> purchaseRequestDetMdel) {
        this.purchaseRequestDetMdel = purchaseRequestDetMdel;
    }

    public DataModel<MmsNeedAssessmentDtl> getMmsNeedAssessmentModel() {
        if (mmsNeedAssessmentModel == null) {
            mmsNeedAssessmentModel = new ListDataModel();
        }
        return mmsNeedAssessmentModel;
    }

    public void setMmsNeedAssessmentModel(DataModel<MmsNeedAssessmentDtl> mmsNeedAssessmentModel) {
        this.mmsNeedAssessmentModel = mmsNeedAssessmentModel;
    }

    public DataModel<PrmsAnnualPlanService> getPrmsAnnualPlanServiceMdl() {
        if (prmsAnnualPlanServiceMdl == null) {
            prmsAnnualPlanServiceMdl = new ListDataModel<>();
        }
        return prmsAnnualPlanServiceMdl;
    }

    public void setPrmsAnnualPlanServiceMdl(DataModel<PrmsAnnualPlanService> prmsAnnualPlanServiceMdl) {
        this.prmsAnnualPlanServiceMdl = prmsAnnualPlanServiceMdl;
    }

    public DataModel<PrmsPurchasePlnDetail> getPurchasePlnDetailModel() {
        if (purchasePlnDetailModel == null) {
            purchasePlnDetailModel = new ListDataModel<>();
        }

        return purchasePlnDetailModel;
    }

    public void setPurchasePlnDetailModel(DataModel<PrmsPurchasePlnDetail> purchasePlnDetailModel) {
        this.purchasePlnDetailModel = purchasePlnDetailModel;
    }

    public List<PrmsPurchasePlan> getPlanSearchParameterLst() {
         if (planSearchParameterLst == null) {
            planSearchParameterLst = new ArrayList<>();
            planSearchParameterLst = purchasePlanBeanLocal.getParamNameList();
        }
        return planSearchParameterLst;
    }

    public void setPlanSearchParameterLst(List<PrmsPurchasePlan> planSearchParameterLst) {
        this.planSearchParameterLst = planSearchParameterLst;
    }
    
    
    
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="change Events">
    public void selectedParamChangeEvent(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            eepPurchasePlan.setColumnName(event.getNewValue().toString());
            eepPurchasePlan.setColumnValue(null);
        }
    }

    public void rowSelect(SelectEvent event) {

        isSelect = true;
        eepPurchasePlan = (PrmsPurchasePlan) event.getObject();
        eepPurchasePlan = purchasePlanBeanLocal.searchById(eepPurchasePlan.getId());
        wfPrmsProcessed.setProcessedOn(eepPurchasePlan.getDateReg());
        renderPnlManPage = false;
        renderPnlCreateParty = true;
        renderPnlToSearchPage = true;
        saveorUpdateBundle = "Update";
        fmsLubudgetYear = eepPurchasePlan.getBudgetYearId();

        if ("Goods".equals(eepPurchasePlan.getPurchaseType())) {
            itemTable = true;
            serviceTable = false;
            workTable = false;
            matCategoryType = true;
        } else if ("Service".equals(eepPurchasePlan.getPurchaseType())) {
            serviceTable = true;
            itemTable = false;
            workTable = false;
            matCategoryType = false;
        } else if ("Work".equals(eepPurchasePlan.getPurchaseType())) {
            serviceTable = false;
            itemTable = false;
            workTable = true;
            matCategoryType = false;
        }

        recreatDataModel();
        recreatServiceDataModel();
        recreatworkflow();

        if (eepPurchasePlan.getPrmsPurchasePlnDetailList().size() > 0) {
            if (eepPurchasePlan.getPrmsPurchasePlnDetailList().get(0).
                    getItemCodeId() != null) {
                category = eepPurchasePlan.getPrmsPurchasePlnDetailList().get(0).
                        getItemCodeId().getMatCategory();
            }
        }
    }

    public void edit(SelectEvent event) {

        String select = eepPurchasePlan.getPurchaseType();
        if ("Goods".equals(select)) {
            eepPurchasePlan.getPrmsPurchasePlnDetailList().add(eepPurchasePlnDetail);

        } else if ("Service".equals(select)) {
            prmsAnnualPlanService = prmsAnnualPlanServiceMdl.getRowData();
            eepPrmsServiceAndWorkReg = prmsAnnualPlanService.getServiceId();
            selectedRowIndex = prmsAnnualPlanServiceMdl.getRowIndex();
            prmsAnnualPlanService = prmsAnnualPlanServiceMdl.getRowData();
            serviceAndWork = true;
            service = true;
            work = false;
            item = false;

        } else if ("Work".equals(select)) {
            setSelectedRowIndex(prmsAnnualPlanServiceMdl.getRowIndex());
            prmsAnnualPlanService = annualPlanServiceFrWork.getRowData();
            eepPrmsServiceAndWorkReg = prmsAnnualPlanService.getServiceId();
            rowindextemp = selectedRowIndex;
            service = false;
            work = true;
            item = false;
            serviceAndWork = true;
        }
        this.eepPurchasePlan.setDateReg(temporaryDate);
        Date currentDate = Calendar.getInstance().getTime();
        eepPurchasePlnDetail.setBidOpeningDate(currentDate);
    }

    public void editService(SelectEvent event) {

        String select = eepPurchasePlan.getPurchaseType();
        if ("Service".equals(select)) {
            serviceAndWork = true;
            service = true;
            work = false;
            item = false;

            setSelectedRowIndex(prmsAnnualPlanServiceMdl.getRowIndex());
            selectedRowIndex = 1;
            prmsAnnualPlanService = prmsAnnualPlanServiceMdl.getRowData();
            eepPrmsServiceAndWorkReg = prmsAnnualPlanService.getServiceId();

        } else if ("Work".equals(select)) {
            service = false;
            work = true;
            item = false;
            serviceAndWork = true;
            setSelectedRowIndex(prmsAnnualPlanServiceMdl.getRowIndex());
            selectedRowIndex = 1;
            prmsAnnualPlanService = prmsAnnualPlanServiceMdl.getRowData();
            eepPrmsServiceAndWorkReg = prmsAnnualPlanService.getServiceId();
        }
        this.eepPurchasePlan.setDateReg(temporaryDate);
    }

    public void searchPlanNoEvent(SelectEvent event) {

        eepPurchasePlan.setPlanNo(event.getObject().toString());
        eepPurchasePlan = purchasePlanBeanLocal.getPlan(eepPurchasePlan);
        recreatDataModel();
    }

    public void RequestListChange(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            eepPurchasePlan = (PrmsPurchasePlan) event.getNewValue();
            populateWorkFlow();
        }
    }

    public void handleDecision(ValueChangeEvent eve) {
        if (eve.getNewValue() != null) {
            selectedValue = eve.getNewValue().toString();
        }
    }

    public void valueChange(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            eepPurchasePlan.getPrmsPurchasePlnDetailList().clear();
            eepPurchasePlan.getPrmsAnnualPlanServiceList().clear();
            purchasePlnDetailModel = null;
            prmsAnnualPlanServiceMdl = null;
            if (!event.getNewValue().toString().isEmpty()) {

                String value = event.getNewValue().toString();
                fmsLubudgetYear.setBudgetYear(value);
                fmsLubudgetYear = budgetYearLookUpBean.getYearInfo(fmsLubudgetYear);
                eepPurchasePlan.setBudgetYearId(fmsLubudgetYear);
//                category = null;
                if (eepPurchasePlan.getPurchaseType() != null) {
                    if (eepPurchasePlan.getPurchaseType().equals("Goods")) {
                        eepPurchasePlan.getPrmsPurchasePlnDetailList().clear();
                        itemTable = true;
                        serviceTable = false;
                        workTable = false;
                        matCategoryType = true;
                    } else if (eepPurchasePlan.getPurchaseType().equals("Service")) {
                        eepPurchasePlan.getPrmsAnnualPlanServiceList().clear();
                        serviceTable = true;
                        itemTable = false;
                        workTable = false;
                        matCategoryType = false;
                    } else if (eepPurchasePlan.getPurchaseType().equals("Work")) {
                        eepPurchasePlan.getPrmsAnnualPlanServiceList().clear();
                        serviceTable = false;
                        itemTable = false;
                        workTable = true;
                        matCategoryType = false;
                    }

                    try {
                        eepPurchasePlan.setPurchaseType(purchaseType);
                        if (eepPurchasePlan.getBudgetYearId() != null) {
                            mmsNeedAssessmentServiceList = purchasePlanBeanLocal.getServiceByYear(
                                    eepPurchasePlan, eepPurchasePlan.getPurchaseType());
                            for (int j = 0; j < mmsNeedAssessmentServiceList.size(); j++) {
                                prmsAnnualPlanService = new PrmsAnnualPlanService();
                                prmsAnnualPlanService.setPurchsePlanId(eepPurchasePlan);
                                prmsAnnualPlanService.setServiceId(mmsNeedAssessmentServiceList.get(j).getServiceId());
                                prmsAnnualPlanService.setQuantity(mmsNeedAssessmentServiceList.get(j).getQuantity());
                                prmsAnnualPlanService.setPriceUnit(mmsNeedAssessmentServiceList.get(j).getUnitPrice());
                                eepPrmsServiceAndWorkReg = mmsNeedAssessmentServiceList.get(j).getServiceId();
                                eepPurchasePlan.getPrmsAnnualPlanServiceList().add(prmsAnnualPlanService);
                            }
                        }
                    } catch (Exception ex) {
                        eepPurchasePlan.setPurchaseType(null);
                        mmsNeedAssessmentServiceList = null;
                        mmsNeedAssessmentDtlLst = null;
                        ex.printStackTrace();
                    }
                    recreatServiceDataModel();
                    recreatDataModel();
                }
            }
        } else {
            eepPurchasePlan.getPrmsPurchasePlnDetailList().clear();
            eepPurchasePlan.getPrmsAnnualPlanServiceList().clear();
            purchasePlnDetailModel = null;
            prmsAnnualPlanServiceMdl = null;
        }
    }

    public void changePrRqType(ValueChangeEvent e) {
        if (e.getNewValue() != null) {
            eepPurchasePlan.getPrmsPurchasePlnDetailList().clear();
            purchaseType = e.getNewValue().toString();
            purchasePlnDetailModel = null;
            prmsAnnualPlanServiceMdl = null;
            category = null;

            if ("Goods".equals(e.getNewValue().toString())) {
                eepPurchasePlan.getPrmsPurchasePlnDetailList().clear();
                itemTable = true;
                serviceTable = false;
                workTable = false;
                matCategoryType = true;
            } else if ("Service".equals(e.getNewValue().toString())) {
                eepPurchasePlan.getPrmsAnnualPlanServiceList().clear();
                serviceTable = true;
                itemTable = false;
                workTable = false;
                matCategoryType = false;
            } else if ("Work".equals(e.getNewValue().toString())) {
                eepPurchasePlan.getPrmsAnnualPlanServiceList().clear();
                serviceTable = false;
                itemTable = false;
                workTable = true;
                matCategoryType = false;
            }

            try {
                eepPurchasePlan.setPurchaseType(purchaseType);
                if (eepPurchasePlan.getBudgetYearId() != null) {
                    mmsNeedAssessmentServiceList = purchasePlanBeanLocal.getServiceByYear(
                            eepPurchasePlan, eepPurchasePlan.getPurchaseType());

                    for (int j = 0; j < mmsNeedAssessmentServiceList.size(); j++) {

                        prmsAnnualPlanService = new PrmsAnnualPlanService();
                        prmsAnnualPlanService.setPurchsePlanId(eepPurchasePlan);
                        prmsAnnualPlanService.setServiceId(mmsNeedAssessmentServiceList.get(j).getServiceId());
                        prmsAnnualPlanService.setQuantity(mmsNeedAssessmentServiceList.get(j).getQuantity());
                        prmsAnnualPlanService.setPriceUnit(mmsNeedAssessmentServiceList.get(j).getUnitPrice());
                        eepPrmsServiceAndWorkReg = mmsNeedAssessmentServiceList.get(j).getServiceId();
                        eepPurchasePlan.getPrmsAnnualPlanServiceList().add(prmsAnnualPlanService);
                    }
                }
                recreatServiceDataModel();
                recreatDataModel();

            } catch (Exception ex) {
                eepPurchasePlan.setPurchaseType(null);
                mmsNeedAssessmentServiceList = null;
                mmsNeedAssessmentDtlLst = null;
                ex.printStackTrace();
            }
        }
    }

    public void changeListener(ValueChangeEvent event) {

        if (null != event.getNewValue()) {
            try {
                eepPurchasePlan.getPrmsPurchasePlnDetailList().clear();
                purchasePlnDetailModel = null;
                prmsAnnualPlanServiceMdl = null;

                category.setCatId(Integer.parseInt(event.getNewValue().toString()));
                category = catBeanLocal.searchByCategoryId(category);
                mmsNeedAssessmentDtlLst = purchasePlanBeanLocal.getByYearss(
                        eepPurchasePlan, Constants.PRMS_ANNUALPLAN_ON_MMS_NEED_ASSESSMENT_STATUS);

                if (mmsNeedAssessmentDtlLst != null) {

                    for (int i = 0; i < mmsNeedAssessmentDtlLst.size(); i++) {

                        int needAssCatId = mmsNeedAssessmentDtlLst.get(i).getItemId().getMatCategory().getCatId();
                        int selectedCatagoryId = category.getCatId();

                        if (needAssCatId == selectedCatagoryId) {

                            eepPurchasePlnDetail = new PrmsPurchasePlnDetail();
                            eepPurchasePlnDetail.setPurchsePlanId(eepPurchasePlan);
                            eepPurchasePlnDetail.setPriceUnit(Double.parseDouble(String.valueOf(
                                    mmsNeedAssessmentDtlLst.get(i).getUnitPrice())));
                            eepPurchasePlnDetail.setItemCodeId((mmsNeedAssessmentDtlLst.get(i).getItemId()));
                            eepPurchasePlnDetail.setQuantity(mmsNeedAssessmentDtlLst.get(i).getQuantity());
                            eepMmsItemRegistration = mmsNeedAssessmentDtlLst.get(i).getItemId();
                            eepPurchasePlan.getPrmsPurchasePlnDetailList().add(eepPurchasePlnDetail);

                        } else {
                            continue;
                        }
                    }
                }

                recreatDataModel();

            } catch (Exception ex) {
                eepPurchasePlan.setPurchaseType(null);
                mmsNeedAssessmentDtlLst = null;
                ex.printStackTrace();
            }
        }
    }

    public void changeMaterialCategory(ValueChangeEvent event) {

        try {
            eepPurchasePlan.setPurchaseType(purchaseType);
            mmsNeedAssessmentServiceList = purchasePlanBeanLocal.getServiceByYear(
                    eepPurchasePlan, eepPurchasePlan.getPurchaseType());
            mmsNeedAssessmentDtlLst = purchasePlanBeanLocal.getByYearss(eepPurchasePlan,
                    Constants.PRMS_ANNUALPLAN_ON_MMS_NEED_ASSESSMENT_STATUS);

            for (int i = 0; i < mmsNeedAssessmentDtlLst.size(); i++) {
                eepPurchasePlnDetail = new PrmsPurchasePlnDetail();
                eepPurchasePlnDetail.setPurchsePlanId(eepPurchasePlan);
                eepPurchasePlnDetail.setPriceUnit(Double.parseDouble(String.valueOf(
                        mmsNeedAssessmentDtlLst.get(i).getUnitPrice())));
                eepPurchasePlnDetail.setItemCodeId((mmsNeedAssessmentDtlLst.get(i).getItemId()));
                eepPurchasePlnDetail.setQuantity(mmsNeedAssessmentDtlLst.get(i).getQuantity());
                eepMmsItemRegistration = mmsNeedAssessmentDtlLst.get(i).getItemId();
                eepPurchasePlan.getPrmsPurchasePlnDetailList().add(eepPurchasePlnDetail);
            }

            for (int j = 0; j < mmsNeedAssessmentServiceList.size(); j++) {
                prmsAnnualPlanService = new PrmsAnnualPlanService();
                prmsAnnualPlanService.setPurchsePlanId(eepPurchasePlan);
                prmsAnnualPlanService.setServiceId(mmsNeedAssessmentServiceList.get(j).getServiceId());
                prmsAnnualPlanService.setQuantity(mmsNeedAssessmentServiceList.get(j).getQuantity());
                prmsAnnualPlanService.setPriceUnit(mmsNeedAssessmentServiceList.get(j).getUnitPrice());
                eepPrmsServiceAndWorkReg = mmsNeedAssessmentServiceList.get(j).getServiceId();
                eepPurchasePlan.getPrmsAnnualPlanServiceList().add(prmsAnnualPlanService);
            }

            recreatServiceDataModel();
            recreatDataModel();

        } catch (Exception ex) {
            eepPurchasePlan.setPurchaseType(null);
            mmsNeedAssessmentServiceList = null;
            mmsNeedAssessmentDtlLst = null;
            ex.printStackTrace();
        }
    }

    public void RegistrationDateChange(ValueChangeEvent event) {
        this.eepPurchasePlan.setDateReg((Date) event.getNewValue());
        temporaryDate = this.eepPurchasePlan.getDateReg();
    }

    public void changeItemCode(ValueChangeEvent e) {
        if (null != e.getNewValue()) {
            itemRegistrations = purchasePlanBeanLocal.getListOfCodeName(e.getNewValue().toString());
        }
    }

    public void onPlnInfoRowEdit(RowEditEvent event) {
        recreatNeedAssMdl();
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="other methods">
    public String generatePlnNo() {
        if (saveorUpdateBundle.equals("Update")) {
            newAnnualPlanNo = eepPurchasePlan.getPlanNo();
        } else {
            newAnnualPlanNo = getAnnualPlanNo();
        }
        return newAnnualPlanNo;
    }

    public String getAnnualPlanNo() {
        String annualPlanNo = purchasePlanBeanLocal.getAnnualPlanNo();
        return annualPlanNo;
    }

    public void createNewAnnualPlan() {
        saveorUpdateBundle = "Save";
        disableBtnCreate = false;
        renderPnlToSearchPage = false;

        if (createOrSearchBundle.equals("New")) {
            renderPnlCreateParty = true;
            renderPnlManPage = false;
            createOrSearchBundle = "Search";
            prmsAnnualPlanServiceMdl = null;
            purchasePlnDetailModel = null;
            clear();

            headerTitle = "Annual Plan Registration";
            setIcone("ui-icon-search");

        } else if (createOrSearchBundle.equals("Search")) {
            renderPnlCreateParty = false;
            renderPnlManPage = true;
            createOrSearchBundle = "New";
            headerTitle = "Search Annual Plan";
            setIcone("ui-icon-plus");
        }
    }

    public void goBackSearchButtonAction() {
        renderPnlManPage = true;
        renderPnlCreateParty = false;
        renderPnlToSearchPage = false;
    }

    public void search_AnnualPlan() {

        eepPurchasePlan.setPreparedBy(String.valueOf(workFlow.getUserAccount()));
        purchasePlanList = purchasePlanBeanLocal.getAPlanNo(0, workFlow.getUserAccount(), eepPurchasePlan);
        
        recreat();
    }

    public ArrayList<PrmsPurchasePlan> searchPlanNo(String planNo) {

        ArrayList<PrmsPurchasePlan> purchasePlanLst = null;
        purchasePlanLst = purchasePlanBeanLocal.searchPlnNo(eepPurchasePlan);
        eepPurchasePlan.setPlanNo(planNo);

        return purchasePlanLst;
    }

    public void addTable() {

        if (isSelect == true) {

            int rowIndex = eepPurchasePlan.getPrmsPurchasePlnDetailList().indexOf(prmsPurchasePlnDetail);
            eepPurchasePlan.getPrmsPurchasePlnDetailList().add(prmsPurchasePlnDetail);

            if (!eepPurchasePlan.getPrmsPurchasePlnDetailList().contains(prmsPurchasePlnDetail)) {
                eepPurchasePlan.getPrmsPurchasePlnDetailList().set(rowIndex, prmsPurchasePlnDetail);
            }
        }

        clearSearch();
    }

    public void addToServiceTable() {
        if (isSelect == true) {
            int rowIndex = eepPurchasePlan.getPrmsAnnualPlanServiceList().indexOf(prmsAnnualPlanService);
            eepPurchasePlan.getPrmsAnnualPlanServiceList().add(prmsAnnualPlanService);
            if (!eepPurchasePlan.getPrmsAnnualPlanServiceList().contains(prmsAnnualPlanService)) {
                eepPurchasePlan.getPrmsAnnualPlanServiceList().set(rowIndex, prmsAnnualPlanService);

            }
        }
        clearServiceInfo();
        recreatServiceDataModel();
    }

    //Validate End date greater than start date on Add To table">  
    public void validateEndDateGood(FacesContext f, UIComponent c, Object obj) {
        Date s = (Date) obj;
        if (s.getTime()
                <= eepPurchasePlnDetail.getBidOpeningDate().getTime()) {
            throw new ValidatorException(
                    new FacesMessage("Validation Error!!! ",
                            "End date is greater than start date."));
        }
    }

    public void validateEndDateService(FacesContext f, UIComponent c, Object obj) {
        Date s = (Date) obj;
        if (s.getTime()
                <= prmsAnnualPlanService.getBidOpeningDate().getTime()) {
            throw new ValidatorException(new FacesMessage("Validation Error!!! ",
                    "End date is greater than start date."));
        }
    }

    public Date getminDate1() {
        return this.eepPurchasePlnDetail.getBidOpeningDate();
    }

    public void populateWorkFlow() {

        eepPurchasePlan.setId(eepPurchasePlan.getId());
        eepPurchasePlan = purchasePlanBeanLocal.getSelectedRequest(
                eepPurchasePlan.getId());
        wfPrmsProcessed.setProcessedOn(eepPurchasePlan.getDateReg());
        renderPnlManPage = false;
        renderPnlCreateParty = true;
        saveorUpdateBundle = "Update";
        String select;
        select = eepPurchasePlan.getPurchaseType();

        if (select.equalsIgnoreCase("Goods")) {
            serviceTable = false;
            itemTable = true;
            workTable = false;
        } else if (select.equalsIgnoreCase("Service")) {
            serviceTable = true;
            itemTable = false;
            workTable = false;
        } else if (select.equalsIgnoreCase("Work")) {
            serviceTable = false;
            itemTable = false;
            workTable = true;
        }

        recreatDataModel();
        recreatworkflow();
    }

    public void removePurchasePlnInfo() {
        int rowIndex = mmsNeedAssessmentModel.getRowIndex();
        mmsNeedAssessmentDtl
                = mmsNeedAssessment.getMmsNeedAssessmentDtlList().get(rowIndex);
        mmsNeedAssessment.getMmsNeedAssessmentDtlList().remove(rowIndex);
        recreatNeedAssMdl();
    }

    public void recreat() {
        prmsPurchasePlanMdl = null;
        prmsPurchasePlanMdl = new ListDataModel<>(new ArrayList<>(
                getPurchasePlanList()));
    }

    public void reCreatMdl() {
        purchaseRequestDetMdel = null;
        purchaseRequestDetMdel = new ListDataModel(new ArrayList<>(
                prmsPurchaseRequest.getPrmsPurchaseRequestDetList()));
    }

    public void recreatNeedAssMdl() {
        mmsNeedAssessmentModel = null;
        mmsNeedAssessmentModel = new ListDataModel(new ArrayList<>(
                mmsNeedAssessment.getMmsNeedAssessmentDtlList()));
    }

    public void recreatDataModel() {
        purchasePlnDetailModel = null;
        purchasePlnDetailModel = new ListDataModel(new ArrayList<>(eepPurchasePlan.getPrmsPurchasePlnDetailList()));
    }

    public void recreatServiceDataModel() {
        prmsAnnualPlanServiceMdl = null;
        prmsAnnualPlanServiceMdl = new ListDataModel(new ArrayList<>(eepPurchasePlan.getPrmsAnnualPlanServiceList()));
    }

    public void reCreat() {
        mmsNeedAssessmentModel = null;
        mmsNeedAssessmentModel = new ListDataModel(new ArrayList<>(mmsNeedAssessment.getMmsNeedAssessmentDtlList()));
    }

    public void recreatworkflow() {
        wfPrmsProcessedDModel = new ListDataModel(
                new ArrayList(eepPurchasePlan.getPrmsWorkFlowProccedList()));
    }

    public void clearGoodsInfo() {
        eepPurchasePlnDetail = null;
        eepMmsItemRegistration = null;
    }

    public void clearServiceInfo() {
        prmsAnnualPlanService = null;
        eepPrmsServiceAndWorkReg = null;
    }

    public void clearSearch() {

        eepPurchasePlan = null;
        eepPurchasePlnDetail = null;
        eepMmsItemRegistration = null;
        purchasePlnDetailModel = null;
        wfPrmsProcessed = null;
    }

    public void clear() {
        eepPurchasePlan = null;
        purchasePlnDetailModel = null;
        prmsAnnualPlanServiceMdl = null;
        fmsLubudgetYear = null;
        temporaryDate = null;
        wfPrmsProcessed = null;
        saveorUpdateBundle = "Save";
        createOrSearchBundle = "Search";
        icone = "ui-icon-search";
        renderPnlToSearchPage = false;
        if (category != null) {
            category = null;
        }
    }

    public SelectItem[] getItemCode() {
        return JsfUtil.getSelectItems(marketAssesmntBeanLocal.ItemCodeList(), true);
    }

    public List<FmsLuBudgetYear> getBudgetYear() {
        return getActiveYearList();//JsfUtil.getSelectItems(purchasePlanBeanLocal.getBudgetYear(), true);
    }

    public List<FmsLuBudgetYear> getActiveYearList() {

        try {
            List<FmsLuBudgetYear> activePeriodList = new ArrayList();
            FmsLuBudgetYear fmsLuBudgetYear = new FmsLuBudgetYear();
            FmsLuBudgetYear fmsLuBudgetYear2 = new FmsLuBudgetYear();
            FmsAccountingPeriod activePer = new FmsAccountingPeriod();
            activePer = accountingPeriodBean.getCurretActivePeriod();
            fmsLuBudgetYear = activePer.getLuBudgetYearId();

            activePeriodList.add(fmsLuBudgetYear);// ehen metekem
            int year1 = fmsLuBudgetYear.getLuBudgetYearId() + 1;
            fmsLuBudgetYear2.setLuBudgetYearId(year1);
            fmsLuBudgetYear2 = budgetYearLookUpBean.findBgtYearbyId(fmsLuBudgetYear2);
            activePeriodList.add(fmsLuBudgetYear2);

            return activePeriodList;

        } catch (Exception e) {
            JsfUtil.addErrorMessage("Error retrieving list of Active Budget year" + e.getMessage());

            return null;
        }
    }

    public SelectItem[] getPrNo() {
        return JsfUtil.getSelectItems(
                purchaseReqBeanLocal.getPurchReqNo(), true);
    }

    public List<MmsNeedAssessment> needList() {
        setNeedAssessmentList(purchasePlanBeanLocal.getBudgetYear());
        return needAssessmentList;
    }

    public Date openDate() {
        return this.eepPurchasePlnDetail.getBidOpeningDate();
    }

    public Date openServiceDate() {
        return this.prmsAnnualPlanService.getBidOpeningDate();
    }

    public Date openDateEnd() {
        Date tempDate;

        if (this.eepPurchasePlnDetail.getBidOpeningDate() == null) {
            tempDate = Calendar.getInstance().getTime();
        } else {
            tempDate = this.eepPurchasePlnDetail.getBidOpeningDate();
        }

        LocalDateTime localDateTime = tempDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        Date bidOpeningPlusOneDay = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());

        return bidOpeningPlusOneDay;//plusDays(1)
    }

    public Date openServiceDateEnd() {

        Date tempDate;
        if (this.eepPurchasePlnDetail.getBidOpeningDate() == null) {
            tempDate = Calendar.getInstance().getTime();
        } else {
            tempDate = this.eepPurchasePlnDetail.getBidOpeningDate();
        }
        LocalDateTime localDateTime = tempDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        Date bidOpeningPlusOneDay = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());

        return bidOpeningPlusOneDay;//plusDays(1)
    }
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Save or Update method">
    //save or update action method
    public String save_AnnualPlan() {

        try {
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");

            if (security.checkAccess(SessionBean.getUserName(), "save_AnnualPlan", dataset)) {
                try {
                    if (saveorUpdateBundle.equals("Save")) {
                        if (eepPurchasePlan.getPrmsPurchasePlnDetailList().size() > 0 || eepPurchasePlan.getPrmsAnnualPlanServiceList().size() > 0) {

                            generatePlnNo();
                            eepPurchasePlan.setPlanNo(newAnnualPlanNo);
                            eepPurchasePlan.setPreparedBy(String.valueOf(SessionBean.getUserId()));
                            Date currentDate = Calendar.getInstance().getTime();
                            eepPurchasePlan.setStatus(Constants.PREPARE_VALUE);
                            wfPrmsProcessed.setPurchasePlanId(eepPurchasePlan);
                            eepPurchasePlan.setDateReg(currentDate);
                            wfPrmsProcessed.setDecision(String.valueOf(eepPurchasePlan.getStatus()));//-
                            wfPrmsProcessed.setProcessedBy(SessionBean.getUserId());
                            if (purchaseType.equals("Goods")) {
                                eepPurchasePlnDetail.setPurchsePlanId(eepPurchasePlan);
                            } else if (purchaseType.equals("Service") || purchaseType.equals("Work")) {
                                prmsAnnualPlanService.setPurchsePlanId(eepPurchasePlan);
                            }
                            eepPurchasePlan.getPrmsWorkFlowProccedList().add(wfPrmsProcessed);// wfPrmsProcessedBeanLocal.savewf(wfPrmsProcessed);
                            purchasePlanBeanLocal.save(eepPurchasePlan);

                            if (mmsNeedAssessment != null) {
                                mmsNeedAssessment.setStatus(Constants.PRMS_ANNUALPLAN_ON_MMS_NEED_ASSESSMENT_STATUS);
                            }
                            if (mmsNeedAssessmentDtlLst != null) {

                                for (int i = 0; i < mmsNeedAssessmentDtlLst.size(); i++) {
                                    mmsNeedAssessmentDtl = mmsNeedAssessmentDtlLst.get(i);
                                    mmsNeedAssessmentDtl.setStatus(Constants.PRMS_ANNUALPLAN_ON_MMS_NEED_ASSESSMENT_STATUS);
                                    needAssessmentDtlBeanLocal.edit(mmsNeedAssessmentDtl);
                                }
                            }

                            clear();
                            JsfUtil.addSuccessMessage("Annual Plan information Successfuly Registared");
                        } else {
                            JsfUtil.addFatalMessage("Atleast One Detail Information should filled");
                        }
                    } else if (workFlow.isPrepareStatus() && saveorUpdateBundle.equals("Update")) {

                        try {
                            if (eepPurchasePlan.getPrmsPurchasePlnDetailList().size() > 0 || eepPurchasePlan.getPrmsAnnualPlanServiceList().size() > 0) {
                                if (eepPurchasePlan.getPrmsPurchasePlnDetailList().size() > 0) {
                                    purchasePlnDetailModel = new ListDataModel(new ArrayList<>(eepPurchasePlan.getPrmsPurchasePlnDetailList()));
                                } else if (eepPurchasePlan.getPrmsAnnualPlanServiceList().size() > 0) {
                                    purchasePlnDetailModel = new ListDataModel(new ArrayList<>(eepPurchasePlan.getPrmsAnnualPlanServiceList()));
                                }
                                if (purchaseType.equals("Goods")) {
                                    eepPurchasePlnDetail.setPurchsePlanId(eepPurchasePlan);
                                } else if (purchaseType.equals("Service") || purchaseType.equals("Work")) {
                                    prmsAnnualPlanService.setPurchsePlanId(eepPurchasePlan);
                                }

                                eepPurchasePlan.getPrmsWorkFlowProccedList().add(wfPrmsProcessed);
                                purchasePlanBeanLocal.update(eepPurchasePlan);
                                JsfUtil.addSuccessMessage("Annual Plan information Successfuly Updated");
                                saveorUpdateBundle = "Save";
                                updateStatus = 0;
                                clear();
                            } else {
                                JsfUtil.addFatalMessage("Atleast One Detail Information should filled");
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            JsfUtil.addErrorMessage("Error occured while Modifiing");
                        }

                    } else if ((workFlow.isApproveStatus() || workFlow.isCheckStatus()) && saveorUpdateBundle.equals("Update")) {

                        try {
                            if (selectedValue.equalsIgnoreCase("Approve") && workFlow.isApproveStatus()) {
                                workFlow.setUserStatus(Constants.APPROVE_VALUE);
                                eepPurchasePlan.setStatus(Constants.APPROVE_VALUE);
                                wfPrmsProcessed.setDecision(String.valueOf(Constants.APPROVE_VALUE));
                            } else if (selectedValue.equalsIgnoreCase("Approve") && workFlow.isCheckStatus()) {
                                workFlow.setUserStatus(Constants.CHECK_APPROVE_VALUE);
                                eepPurchasePlan.setStatus(Constants.CHECK_APPROVE_VALUE);
                                wfPrmsProcessed.setDecision(String.valueOf(Constants.APPROVE_VALUE));
                            } else if (selectedValue.equalsIgnoreCase("Reject") && workFlow.isApproveStatus()) {
                                workFlow.setUserStatus(Constants.APPROVE_REJECT_VALUE);
                                eepPurchasePlan.setStatus(Constants.APPROVE_REJECT_VALUE);
                                wfPrmsProcessed.setDecision(String.valueOf(Constants.APPROVE_REJECT_VALUE));
                            } else if (selectedValue.equalsIgnoreCase("Reject") && workFlow.isCheckStatus()) {
                                workFlow.setUserStatus(Constants.CHECK_REJECT_VALUE);
                                eepPurchasePlan.setStatus(Constants.CHECK_REJECT_VALUE);
                                wfPrmsProcessed.setDecision(String.valueOf(Constants.CHECK_REJECT_VALUE));
                            }

                            eepPurchasePlan.setStatus(workFlow.getUserStatus());
                            purchasePlanBeanLocal.update(eepPurchasePlan);
                            wfPrmsProcessedBeanLocal.savewf(wfPrmsProcessed);
                            JsfUtil.addSuccessMessage("Annual Plan Information has been Sucessfuly Approved");
                            clear();
                            saveorUpdateBundle = "Save";
                            updateStatus = 0;

                        } catch (Exception e) {
                            e.printStackTrace();
                            JsfUtil.addErrorMessage("Something Error Occured on Data Updated");
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    JsfUtil.addErrorMessage("Something occured on save");
                }

            } else {
                JsfUtil.addFatalMessage("You don't have appropriate permission, please contact the system administrator. ");
                EventEntry eventEntry = new EventEntry();
                eventEntry.setSessionId(SessionBean.getSessionID());
                eventEntry.setUserId(SessionBean.getUserId());
                QName qualifiedName = new QName("", "project");
                JAXBElement<String> test = new JAXBElement<String>(qualifiedName, String.class, null, SessionBean.getUserName());
                eventEntry.setUserLogin(test);
                //..... add more information by calling fields defined in the object 
                security.addEventLog(eventEntry, dataset);
            }

            clear();
            return null;

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }

// </editor-fold>  
}
