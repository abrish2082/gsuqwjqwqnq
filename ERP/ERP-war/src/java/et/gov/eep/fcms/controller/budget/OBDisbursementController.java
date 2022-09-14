/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.controller.budget;
//<editor-fold defaultstate="collapsed" desc="import">
import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.fcms.businessLogic.FmsLuBudgetYearBeanLocal;
import et.gov.eep.fcms.businessLogic.admin.FmsAccountingPeriodBeanLocal;
import et.gov.eep.fcms.businessLogic.admin.FmsCostCSystemJunctionBeanLocal;
import et.gov.eep.fcms.businessLogic.admin.FmsCostCenterBeanLocal;
import et.gov.eep.fcms.businessLogic.admin.FmsGeneralLedgerBeanLocal;
import et.gov.eep.fcms.businessLogic.admin.FmsLuSystemBeanLocal;
import et.gov.eep.fcms.businessLogic.budget.FmsOBDisbursementBeanLocal;
import et.gov.eep.fcms.businessLogic.budget.FmsOperatingBudgetBeanLocal;
import et.gov.eep.fcms.businessLogic.budget.FmsOperatingBudgetDetailBeanLocal;
import et.gov.eep.fcms.businessLogic.budgetYearLookUpBeanLocal;
import et.gov.eep.fcms.entity.FmsLuBudgetYear;
import et.gov.eep.fcms.entity.admin.FmsAccountingPeriod;
import et.gov.eep.fcms.entity.admin.FmsCostCenter;
import et.gov.eep.fcms.entity.admin.FmsCostcSystemJunction;
import et.gov.eep.fcms.entity.admin.FmsGeneralLedger;
import et.gov.eep.fcms.entity.admin.FmsLuSystem;
import et.gov.eep.fcms.entity.budget.FmsObDisbursement;
import et.gov.eep.fcms.entity.budget.FmsOperatingBudget1;
import et.gov.eep.fcms.entity.budget.FmsOperatingBudgetDetail;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.ejb.EJB;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.ArrayDataModel;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import org.primefaces.convert.NumberConverter;
import org.primefaces.event.SelectEvent;
import securityBean.SessionBean;
import webService.AAA;
import webService.EventEntry;
import webService.IAdministration;
    //</editor-fold>



/**
 *
 * @author Me
 */
@Named(value = "oBDisbursementController")
@ViewScoped
public class OBDisbursementController implements Serializable {
//<editor-fold defaultstate="collapsed" desc="Inject">
    @Inject
    SessionBean SessionBean;

    @Inject
    FmsOperatingBudget1 fmsOperatingBudget1;
    @Inject
    FmsOperatingBudgetDetail fmsOperatingBudgetDetail;
    @Inject
    FmsCostCenter fmsCostCenter;
    @Inject
    FmsLuSystem fmsLuSystem;
    @Inject
    FmsLuBudgetYear fmsLuBudgetYear;
    @Inject
    FmsGeneralLedger fmsGeneralLedger;
    @Inject
    FmsObDisbursement fmsObDisbursement;
    @Inject
    FmsCostcSystemJunction fmsCostcSystemJunction;
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="EJB">
    
    @EJB
    FmsCostCSystemJunctionBeanLocal fmsCostCSystemJunctionBeanLocal;
    @EJB
    FmsLuSystemBeanLocal fmsLuSystemBeanLocal;
    @EJB
    FmsCostCenterBeanLocal fmsCostCenterBeanLocal;
    @EJB
    FmsLuBudgetYearBeanLocal fmsLuBudgetYearBeanLocal;
    @EJB
    FmsOperatingBudgetBeanLocal fmsOperatingBudgetBeanLocal;
    @EJB
    FmsOperatingBudgetDetailBeanLocal fmsOperatingBudgetDetailBeanLocal;
    @EJB
    FmsAccountingPeriodBeanLocal fmsAccountingPeriodBeanLocal;
    @EJB
    budgetYearLookUpBeanLocal budgetYearLookUpBeanLocal;
    @EJB
    FmsGeneralLedgerBeanLocal fmsGeneralLedgerBeanLocal;
    @EJB
    FmsOBDisbursementBeanLocal fmsOBDisbursementBeanLocal;
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="variable declarations">
      FmsAccountingPeriod currPeriod = new FmsAccountingPeriod();
    FmsLuBudgetYear nextPeriod = new FmsLuBudgetYear();
    List<FmsLuBudgetYear> budgetyrList = new ArrayList<>();
    List<FmsLuBudgetYear> listLuBudgetYearList = new ArrayList<>();
    List<FmsOperatingBudget1> fmsOperatingBudget1List = new ArrayList<>();
    List<FmsOperatingBudgetDetail> fmsOperatingBudgetDtlList = new ArrayList<>();
    List<FmsObDisbursement> fmsObDisbursementList = new ArrayList<>();
    List<FmsCostCenter> costCenterList;
    private NumberConverter numberConverter = new NumberConverter();

    DataModel<FmsObDisbursement> fmsObDisbursementmodel;
    DataModel<FmsOperatingBudgetDetail> fmsOperatingBudgetDetailmodel;
    DataModel<FmsOperatingBudget1> fmsOperatingBudgetDetailSrcmodel;
    FmsOperatingBudget1 operatingBudgetSelection;
    FmsObDisbursement obDisbursementSelection;
    FmsOperatingBudgetDetail operatingBudgetDetailSelection;

    BigDecimal unAllocatedAmt = BigDecimal.ZERO;
    BigDecimal disbursedAmt = BigDecimal.ZERO;

    private String createOrSearchBundle = "New";
    private boolean renderPnlCreateAdditional = true;
    private boolean renderPnlManPage = false;
    private String icone = "ui-icon-plus";
    private boolean buttonRenderd = false;
    private boolean approveRenderd = false;
    private String reqheaderTitle = "Operating Budget Disbursement Search";
    private String saveOrUpdate = "Create";
    private boolean renderNewSrcBtn = false;
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="getter and setter">
    
    public SelectItem[] getSystemSearchList() {
        return JsfUtil.getSelectItems(fmsLuSystemBeanLocal.findAll(), true);
    }

    public SelectItem[] getCostCenterSearch() {

        fmsLuSystem = fmsCostCenter.getSystemId();
        if (fmsLuSystem != null) {
            return JsfUtil.getSelectItems(fmsCostCenterBeanLocal.findAll(), true);
        } else {
            SelectItem[] items = new SelectItem[1];
            return items;
        }
    }
     public BigDecimal getUnAllocatedAmt() {
        return unAllocatedAmt;
    }

    public void setUnAllocatedAmt(BigDecimal unAllocatedAmt) {
        this.unAllocatedAmt = unAllocatedAmt;
    }

    public List<FmsCostCenter> getCostCenterList() {
        return costCenterList;
    }

    public void setCostCenterList(List<FmsCostCenter> costCenterList) {
        this.costCenterList = costCenterList;
    }

    public BigDecimal getDisbursedAmt() {
        return disbursedAmt;
    }

    public void setDisbursedAmt(BigDecimal disbursedAmt) {
        this.disbursedAmt = disbursedAmt;
    }

    public FmsOperatingBudget1 getFmsOperatingBudget1() {
        return fmsOperatingBudget1;
    }

    public void setFmsOperatingBudget1(FmsOperatingBudget1 fmsOperatingBudget1) {
        this.fmsOperatingBudget1 = fmsOperatingBudget1;
    }

    public FmsOperatingBudgetDetail getFmsOperatingBudgetDetail() {
        return fmsOperatingBudgetDetail;
    }

    public void setFmsOperatingBudgetDetail(FmsOperatingBudgetDetail fmsOperatingBudgetDetail) {
        this.fmsOperatingBudgetDetail = fmsOperatingBudgetDetail;
    }

    public FmsCostCenter getFmsCostCenter() {
        if (fmsCostCenter == null) {
            fmsCostCenter = new FmsCostCenter();
        }
        return fmsCostCenter;
    }

    public void setFmsCostCenter(FmsCostCenter fmsCostCenter) {
        this.fmsCostCenter = fmsCostCenter;
    }

    public FmsLuSystem getFmsLuSystem() {
        return fmsLuSystem;
    }

    public void setFmsLuSystem(FmsLuSystem fmsLuSystem) {
        this.fmsLuSystem = fmsLuSystem;
    }

    public FmsLuBudgetYear getFmsLuBudgetYear() {
        return fmsLuBudgetYear;
    }

    public void setFmsLuBudgetYear(FmsLuBudgetYear fmsLuBudgetYear) {
        this.fmsLuBudgetYear = fmsLuBudgetYear;
    }

    public FmsGeneralLedger getFmsGeneralLedger() {
        return fmsGeneralLedger;
    }

    public void setFmsGeneralLedger(FmsGeneralLedger fmsGeneralLedger) {
        this.fmsGeneralLedger = fmsGeneralLedger;
    }

    public FmsObDisbursement getFmsObDisbursement() {
        return fmsObDisbursement;
    }

    public void setFmsObDisbursement(FmsObDisbursement fmsObDisbursement) {
        this.fmsObDisbursement = fmsObDisbursement;
    }

    public budgetYearLookUpBeanLocal getBudgetYearLookUpBeanLocal() {
        return budgetYearLookUpBeanLocal;
    }

    public void setBudgetYearLookUpBeanLocal(budgetYearLookUpBeanLocal budgetYearLookUpBeanLocal) {
        this.budgetYearLookUpBeanLocal = budgetYearLookUpBeanLocal;
    }

    public FmsAccountingPeriod getCurrPeriod() {
        return currPeriod;
    }

    public void setCurrPeriod(FmsAccountingPeriod currPeriod) {
        this.currPeriod = currPeriod;
    }

    public FmsLuBudgetYear getNextPeriod() {
        return nextPeriod;
    }

    public void setNextPeriod(FmsLuBudgetYear nextPeriod) {
        this.nextPeriod = nextPeriod;
    }

    public List<FmsLuBudgetYear> getBudgetyrList() {
        return budgetyrList;
    }

    public void setBudgetyrList(List<FmsLuBudgetYear> budgetyrList) {
        this.budgetyrList = budgetyrList;
    }

    public List<FmsOperatingBudget1> getFmsOperatingBudget1List() {
        return fmsOperatingBudget1List;
    }

    public void setFmsOperatingBudget1List(List<FmsOperatingBudget1> fmsOperatingBudget1List) {
        this.fmsOperatingBudget1List = fmsOperatingBudget1List;
    }

    public List<FmsOperatingBudgetDetail> getFmsOperatingBudgetDtlList() {
        if (fmsOperatingBudgetDtlList == null) {
            fmsOperatingBudgetDtlList = new ArrayList<>();
        }
        return fmsOperatingBudgetDtlList;
    }

    public void setFmsOperatingBudgetDtlList(List<FmsOperatingBudgetDetail> fmsOperatingBudgetDtlList) {
        this.fmsOperatingBudgetDtlList = fmsOperatingBudgetDtlList;
    }

    public DataModel<FmsObDisbursement> getFmsObDisbursementmodel() {
        return fmsObDisbursementmodel;
    }

    public void setFmsObDisbursementmodel(DataModel<FmsObDisbursement> fmsObDisbursementmodel) {
        this.fmsObDisbursementmodel = fmsObDisbursementmodel;
    }

    public DataModel<FmsOperatingBudgetDetail> getFmsOperatingBudgetDetailmodel() {
        return fmsOperatingBudgetDetailmodel;
    }

    public void setFmsOperatingBudgetDetailmodel(DataModel<FmsOperatingBudgetDetail> fmsOperatingBudgetDetailmodel) {
        this.fmsOperatingBudgetDetailmodel = fmsOperatingBudgetDetailmodel;
    }

    public DataModel<FmsOperatingBudget1> getFmsOperatingBudgetDetailSrcmodel() {
        return fmsOperatingBudgetDetailSrcmodel;
    }

    public void setFmsOperatingBudgetDetailSrcmodel(DataModel<FmsOperatingBudget1> fmsOperatingBudgetDetailSrcmodel) {
        this.fmsOperatingBudgetDetailSrcmodel = fmsOperatingBudgetDetailSrcmodel;
    }

    public FmsOperatingBudget1 getOperatingBudgetSelection() {
        return operatingBudgetSelection;
    }

    public void setOperatingBudgetSelection(FmsOperatingBudget1 operatingBudgetSelection) {
        this.operatingBudgetSelection = operatingBudgetSelection;
    }

    public FmsObDisbursement getObDisbursementSelection() {
        return obDisbursementSelection;
    }

    public void setObDisbursementSelection(FmsObDisbursement obDisbursementSelection) {
        this.obDisbursementSelection = obDisbursementSelection;
    }

    public FmsOperatingBudgetDetail getOperatingBudgetDetailSelection() {
        return operatingBudgetDetailSelection;
    }

    public void setOperatingBudgetDetailSelection(FmsOperatingBudgetDetail operatingBudgetDetailSelection) {
        this.operatingBudgetDetailSelection = operatingBudgetDetailSelection;
    }

    public boolean isRenderNewSrcBtn() {
        return renderNewSrcBtn;
    }

    public void setRenderNewSrcBtn(boolean renderNewSrcBtn) {
        this.renderNewSrcBtn = renderNewSrcBtn;
    }

    public String getCreateOrSearchBundle() {
        return createOrSearchBundle;
    }

    public void setCreateOrSearchBundle(String createOrSearchBundle) {
        this.createOrSearchBundle = createOrSearchBundle;
    }

    public boolean isRenderPnlCreateAdditional() {
        return renderPnlCreateAdditional;
    }

    public void setRenderPnlCreateAdditional(boolean renderPnlCreateAdditional) {
        this.renderPnlCreateAdditional = renderPnlCreateAdditional;
    }

    public boolean isRenderPnlManPage() {
        return renderPnlManPage;
    }

    public void setRenderPnlManPage(boolean renderPnlManPage) {
        this.renderPnlManPage = renderPnlManPage;
    }

    public String getIcone() {
        return icone;
    }

    public void setIcone(String icone) {
        this.icone = icone;
    }

    public boolean isButtonRenderd() {
        return buttonRenderd;
    }

    public void setButtonRenderd(boolean buttonRenderd) {
        this.buttonRenderd = buttonRenderd;
    }

    public boolean isApproveRenderd() {
        return approveRenderd;
    }

    public void setApproveRenderd(boolean approveRenderd) {
        this.approveRenderd = approveRenderd;
    }

    public String getReqheaderTitle() {
        return reqheaderTitle;
    }

    public void setReqheaderTitle(String reqheaderTitle) {
        this.reqheaderTitle = reqheaderTitle;
    }

    public String getSaveOrUpdate() {
        return saveOrUpdate;
    }

    public NumberConverter getNumberConverter() {
        return numberConverter;
    }

    public void setNumberConverter(NumberConverter numberConverter) {
        this.numberConverter = numberConverter;
    }
      public void setSaveOrUpdate(String saveOrUpdate) {
        this.saveOrUpdate = saveOrUpdate;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="OBDisbursementController">
        public OBDisbursementController() {
        numberConverter.setPattern("#,##0.00##");
        numberConverter.setMinIntegerDigits(1);
        numberConverter.setMaxIntegerDigits(50);
        numberConverter.setMaxFractionDigits(2);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="createNewOBDisbView">
        public void createNewOBDisbView() {
        if (createOrSearchBundle.equals("New")) {
            renderPnlCreateAdditional = false;
            renderPnlManPage = true;
            renderNewSrcBtn = false;
            createOrSearchBundle = "Search";
            reqheaderTitle = "Operating Budget Disbursement";
            approveRenderd = false;
            setIcone("ui-icon-search");
            buttonRenderd = false;
            reset();
        } else if (createOrSearchBundle.equals("Search")) {
            renderPnlCreateAdditional = true;
            renderPnlManPage = false;
            renderNewSrcBtn = false;
            createOrSearchBundle = "New";
            reqheaderTitle = "Operating Budget Disbursement Search";
            approveRenderd = true;
            setIcone("ui-icon-plus");
            buttonRenderd = false;
            reset();
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="reset">
         public void reset() {
        fmsOperatingBudget1 = new FmsOperatingBudget1();
        fmsOperatingBudgetDetail = new FmsOperatingBudgetDetail();
        fmsCostCenter = new FmsCostCenter();
        fmsLuSystem = new FmsLuSystem();
        fmsLuBudgetYear = new FmsLuBudgetYear();
        fmsGeneralLedger = new FmsGeneralLedger();
        fmsOperatingBudgetDetailmodel = new ArrayDataModel<>();
        fmsOperatingBudgetDetailSrcmodel = new ArrayDataModel<>();
        fmsObDisbursementmodel = new ArrayDataModel<>();
        fmsObDisbursement = new FmsObDisbursement();
        disbursedAmt = new BigDecimal(0);
        unAllocatedAmt = new BigDecimal(0);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="getLuBudgetYearSearchList">
           public SelectItem[] getLuBudgetYearSearchList() {
        listLuBudgetYearList = fmsLuBudgetYearBeanLocal.getLuBudgetYear();
        listLuBudgetYearList.size();

        currPeriod = fmsAccountingPeriodBeanLocal.getCurretActivePeriod();
        nextPeriod = new FmsLuBudgetYear();
        budgetyrList = new ArrayList<>();
        for (int i = 0; i < listLuBudgetYearList.size(); i++) {
            if (Objects.equals(currPeriod.getLuBudgetYearId().getLuBudgetYearId(), listLuBudgetYearList.get(i).getLuBudgetYearId())) {
                nextPeriod = listLuBudgetYearList.get(i);
            }
        }
        budgetyrList.add(currPeriod.getLuBudgetYearId());
        budgetyrList.add(nextPeriod);
        return JsfUtil.getSelectItems(budgetyrList, true);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="searchYearValueChange">
            public void searchYearValueChange(ValueChangeEvent event) {
        if (!event.getNewValue().toString().isEmpty()) {
            fmsLuBudgetYear.setBudgetYear(event.getNewValue().toString());
            fmsLuBudgetYear = budgetYearLookUpBeanLocal.getYearInfo(fmsLuBudgetYear);
            fmsOperatingBudget1.setBudgetYear(fmsLuBudgetYear);
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="SystemSearchChange">
              public void SystemSearchChange(ValueChangeEvent event) {
        if (!event.getNewValue().toString().isEmpty()) {
            fmsLuSystem.setSystemCode(event.getNewValue().toString());
            fmsLuSystem = fmsLuSystemBeanLocal.getSysDetail(fmsLuSystem);
            fmsCostCenter.setSystemId(fmsLuSystem);
            costCenterList = fmsCostCenterBeanLocal.findMappedCostCenter(fmsLuSystem);
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="CostCenterChange">
                public void CostCenterChange(ValueChangeEvent event) {
        if (!event.getNewValue().toString().isEmpty()) {
            fmsCostCenter.setSystemName(event.getNewValue().toString());
            fmsCostCenter = fmsCostCenterBeanLocal.getCostDetail(fmsCostCenter);
            fmsCostcSystemJunction = fmsCostCSystemJunctionBeanLocal.findByCCandSS(fmsLuSystem, fmsCostCenter);
            fmsOperatingBudget1List = fmsOperatingBudgetBeanLocal.findBySystemAndCostCenterAuthorized(fmsCostcSystemJunction);
        }
    }
    //</editor-fold>
//<editor-fold defaultstate="collapsed" desc="OBReqcodes">
                   public SelectItem[] OBReqcodes() {
        if (fmsCostCenter != null) {
            return JsfUtil.getSelectItems(fmsOperatingBudgetBeanLocal.findBySystemAndCostCenterAuthorized(fmsCostcSystemJunction), true);
        } else {
            SelectItem[] items = new SelectItem[1];
            return items;
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="ObGlCodeChange">
                      public void ObGlCodeChange(ValueChangeEvent event) {
        if (!event.getNewValue().toString().isEmpty()) {
            fmsGeneralLedger.setGeneralLedgerCode(event.getNewValue().toString());
            fmsOperatingBudgetDetail = fmsOperatingBudgetDetailBeanLocal.fetchOBDetailByGLOB1(fmsOperatingBudget1, fmsGeneralLedger);
            fmsObDisbursementList = fmsOBDisbursementBeanLocal.fetchDisbursedOB(fmsOperatingBudgetDetail);
            recreateDisbursementModel();
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="RequestCodeChange">
                       public void RequestCodeChange(ValueChangeEvent event) {
        if (!event.getNewValue().toString().isEmpty()) {
            fmsOperatingBudget1.setRequestCode(event.getNewValue().toString());
            fmsOperatingBudget1 = fmsOperatingBudgetBeanLocal.fetchByRequestCode(fmsOperatingBudget1);
            fmsOperatingBudgetDtlList = fmsOperatingBudgetDetailBeanLocal.fetchOBDetail(fmsOperatingBudget1);
        }
    }

    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="recreateDataModel">
                        public void recreateDataModel() {
        fmsOperatingBudgetDetailmodel = null;
        fmsOperatingBudgetDetailmodel = new ListDataModel(new ArrayList(fmsOperatingBudget1.getFmsOperatingBudgetDetailList()));
        fmsOperatingBudgetDetail = new FmsOperatingBudgetDetail();
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="recreateDisbursementModel">
                           public void recreateDisbursementModel() {
        fmsObDisbursementmodel = null;
        fmsObDisbursementmodel = new ListDataModel(new ArrayList(fmsObDisbursementList));

    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="searchByReqCode">
                             public void searchByReqCode() {
        fmsOperatingBudgetDetailBeanLocal.fetchSelectedOBRequest(fmsOperatingBudget1);
        recreateDisbursementModel();
    }
    //</editor-fold>
                        //<editor-fold defaultstate="collapsed" desc="calOBDisbursement">
                              public void calOBDisbursement() {
        disbursedAmt = fmsObDisbursement.getHamle().add(
                fmsObDisbursement.getNehasie()).add(
                        fmsObDisbursement.getMeskerem().add(
                                fmsObDisbursement.getTikemt()).add(
                                fmsObDisbursement.getHidar()).add(
                                fmsObDisbursement.getTahsas()).add(
                                fmsObDisbursement.getTir()).add(
                                fmsObDisbursement.getYekatit()).add(
                                fmsObDisbursement.getMegabit()).add(
                                fmsObDisbursement.getMiyaziya().add(
                                        fmsObDisbursement.getGinbot()).add(
                                        fmsObDisbursement.getSene())));
        unAllocatedAmt = fmsObDisbursement.getObTaskId().getAllotedAmount().subtract(disbursedAmt);

    }
    //</editor-fold>
                        //<editor-fold defaultstate="collapsed" desc="searchPopulate">
                               public void searchPopulate(SelectEvent event) {
        fmsObDisbursement = (FmsObDisbursement) event.getObject();
        calOBDisbursement();
        renderPnlCreateAdditional = false;
        renderPnlManPage = true;
        createOrSearchBundle = "Search";
        reqheaderTitle = "Operating Budget Disbursement";
        buttonRenderd = true;
        renderNewSrcBtn = true;
        approveRenderd = false;
        saveOrUpdate = "Edit";
        setIcone("ui-icon-search");
    }
    //</editor-fold>
                        //<editor-fold defaultstate="collapsed" desc="saveDisbursement">
                                public void saveDisbursement() {
        calOBDisbursement();
        if (unAllocatedAmt.compareTo(BigDecimal.ZERO) == 0) {
            try {
                AAA securityService = new AAA();
                IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
                String systemBundle = "et/gov/eep/commonApplications/securityServer";
                String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
                if (security.checkAccess(SessionBean.getUserName(), "saveDisbursement", dataset)) {

                    double x = 0;
                    x = fmsObDisbursement.getHamle().doubleValue()
                            + fmsObDisbursement.getNehasie().doubleValue()
                            + fmsObDisbursement.getMeskerem().doubleValue()
                            + fmsObDisbursement.getTikemt().doubleValue()
                            + fmsObDisbursement.getHidar().doubleValue()
                            + fmsObDisbursement.getTahsas().doubleValue()
                            + fmsObDisbursement.getTir().doubleValue()
                            + fmsObDisbursement.getYekatit().doubleValue()
                            + fmsObDisbursement.getMegabit().doubleValue()
                            + fmsObDisbursement.getMiyaziya().doubleValue()
                            + fmsObDisbursement.getGinbot().doubleValue()
                            + fmsObDisbursement.getSene().doubleValue();
                    if (x > fmsObDisbursement.getObTaskId().getAllotedAmount().doubleValue()) {
                        JsfUtil.addFatalMessage("Operating budget disbursment has exceeded the approved amount.");
                    } else {
                        fmsOBDisbursementBeanLocal.edit(fmsObDisbursement);
                        JsfUtil.addSuccessMessage("Operating Budget Disbursed Succesfully.");
                        reset();
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
            } catch (Exception ex) {
            }
        } else {
            JsfUtil.addFatalMessage("There is unallocated amount of birr " + unAllocatedAmt.toString() + ".");
        }
    }
    //</editor-fold>
                        //<editor-fold defaultstate="collapsed" desc="sampl">
    //</editor-fold>
                        //<editor-fold defaultstate="collapsed" desc="sampl">
    //</editor-fold>
                        //<editor-fold defaultstate="collapsed" desc="sampl">
    //</editor-fold>
                        //<editor-fold defaultstate="collapsed" desc="sampl">
    //</editor-fold>
                        //<editor-fold defaultstate="collapsed" desc="sampl">
    //</editor-fold>
                        //<editor-fold defaultstate="collapsed" desc="sampl">
    //</editor-fold>
}
