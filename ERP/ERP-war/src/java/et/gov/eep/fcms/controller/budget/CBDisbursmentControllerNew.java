package et.gov.eep.fcms.controller.budget;
//<editor-fold defaultstate="collapsed" desc="import">

import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.fcms.businessLogic.FmsLuBudgetYearBeanLocal;
import et.gov.eep.fcms.businessLogic.admin.FmsAccountingPeriodBeanLocal;
import et.gov.eep.fcms.businessLogic.admin.FmsCostCSystemJunctionBeanLocal;
import et.gov.eep.fcms.businessLogic.admin.FmsCostCenterBeanLocal;
import et.gov.eep.fcms.businessLogic.admin.FmsGeneralLedgerBeanLocal;
import et.gov.eep.fcms.businessLogic.admin.FmsLuSystemBeanLocal;
import et.gov.eep.fcms.businessLogic.budget.FmsCBDisbursementBeanLocal;
import et.gov.eep.fcms.businessLogic.budget.FmsCapitalBudgetBeanLocal;
import et.gov.eep.fcms.businessLogic.budget.FmsCapitalBudgetDetailBeanLocal;
import et.gov.eep.fcms.businessLogic.budgetYearLookUpBeanLocal;
import et.gov.eep.fcms.entity.FmsLuBudgetYear;
import et.gov.eep.fcms.entity.admin.FmsAccountingPeriod;
import et.gov.eep.fcms.entity.admin.FmsCostCenter;
import et.gov.eep.fcms.entity.admin.FmsCostcSystemJunction;
import et.gov.eep.fcms.entity.admin.FmsGeneralLedger;
import et.gov.eep.fcms.entity.admin.FmsLuSystem;
import et.gov.eep.fcms.entity.budget.FmsCapitalBudget1;
import et.gov.eep.fcms.entity.budget.FmsCapitalBudgetDetail;
import et.gov.eep.fcms.entity.budget.FmsCbDisbursement;
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

@Named(value = "cBDisbursmentControllerNew")
@ViewScoped
public class CBDisbursmentControllerNew implements Serializable {
    //<editor-fold defaultstate="collapsed" desc="Inject">

    @Inject
    SessionBean SessionBean;

    @Inject
    FmsCapitalBudget1 fmsCapitalBudget1;
    @Inject
    FmsCapitalBudgetDetail fmsCapitalBudgetDetail;
    @Inject
    FmsCostCenter fmsCostCenter;
    @Inject
    FmsLuSystem fmsLuSystem;
    @Inject
    FmsLuBudgetYear fmsLuBudgetYear;
    @Inject
    FmsGeneralLedger fmsGeneralLedger;
    @Inject
    FmsCbDisbursement fmsCbDisbursement;
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
    FmsCapitalBudgetBeanLocal fmsCapitalBudgetBeanLocal;
    @EJB
    FmsCapitalBudgetDetailBeanLocal fmsCapitalBudgetDetailBeanLocal;
    @EJB
    FmsAccountingPeriodBeanLocal fmsAccountingPeriodBeanLocal;
    @EJB
    budgetYearLookUpBeanLocal budgetYearLookUpBeanLocal;
    @EJB
    FmsGeneralLedgerBeanLocal fmsGeneralLedgerBeanLocal;
    @EJB
    FmsCBDisbursementBeanLocal fmsCBDisbursementBeanLocal;
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="variable declaration">
    FmsAccountingPeriod currPeriod = new FmsAccountingPeriod();
    FmsLuBudgetYear nextPeriod = new FmsLuBudgetYear();
    List<FmsLuBudgetYear> budgetyrList = new ArrayList<>();
    List<FmsLuBudgetYear> listLuBudgetYearList = new ArrayList<>();
    List<FmsCapitalBudget1> fmsCapitalBudget1List = new ArrayList<>();
    List<FmsCbDisbursement> fmsCbDisbursementList = new ArrayList<>();
    List<FmsCostCenter> costCenterList;
    private NumberConverter numberConverter = new NumberConverter();

    BigDecimal unAllocatedAmt = BigDecimal.ZERO;
    BigDecimal disbursedAmt = BigDecimal.ZERO;

    DataModel<FmsCbDisbursement> fmsCbDisbursementmodel;
    DataModel<FmsCapitalBudgetDetail> fmsCapitalBudgetDetailmodel;
    DataModel<FmsCapitalBudget1> fmsCapitalBudgetDetailSrcmodel;
    FmsCapitalBudget1 cperatingBudgetSelection;
    FmsCbDisbursement cbDisbursementSelection;
    FmsCapitalBudgetDetail capitalBudgetDetailSelection;

    private String createOrSearchBundle = "New";
    private boolean renderPnlCreateAdditional = true;
    private boolean renderPnlManPage = false;
    private String icone = "ui-icon-plus";
    private boolean buttonRenderd = false;
    private boolean approveRenderd = false;
    private String reqheaderTitle = "Capital Budget Disbursement Search";
    private String saveOrUpdate = "Create";
    private boolean renderNewSrcBtn = false;

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="getters and setters">

    public boolean isRenderNewSrcBtn() {
        return renderNewSrcBtn;
    }

    public void setRenderNewSrcBtn(boolean renderNewSrcBtn) {
        this.renderNewSrcBtn = renderNewSrcBtn;
    }

    public FmsCostcSystemJunction getFmsCostcSystemJunction() {
        return fmsCostcSystemJunction;
    }

    public void setFmsCostcSystemJunction(FmsCostcSystemJunction fmsCostcSystemJunction) {
        this.fmsCostcSystemJunction = fmsCostcSystemJunction;
    }

    public List<FmsCostCenter> getCostCenterList() {
        return costCenterList;
    }

    public void setCostCenterList(List<FmsCostCenter> costCenterList) {
        this.costCenterList = costCenterList;
    }

    public BigDecimal getUnAllocatedAmt() {
        return unAllocatedAmt;
    }

    public void setUnAllocatedAmt(BigDecimal unAllocatedAmt) {
        this.unAllocatedAmt = unAllocatedAmt;
    }

    public BigDecimal getDisbursedAmt() {
        return disbursedAmt;
    }

    public void setDisbursedAmt(BigDecimal disbursedAmt) {
        this.disbursedAmt = disbursedAmt;
    }

    public FmsCapitalBudget1 getFmsCapitalBudget1() {
        if (fmsCapitalBudget1 == null) {
            fmsCapitalBudget1 = new FmsCapitalBudget1();
        }
        return fmsCapitalBudget1;
    }

    public void setFmsCapitalBudget1(FmsCapitalBudget1 fmsCapitalBudget1) {
        this.fmsCapitalBudget1 = fmsCapitalBudget1;
    }

    public FmsCapitalBudgetDetail getFmsCapitalBudgetDetail() {
        if (fmsCapitalBudgetDetail == null) {
            fmsCapitalBudgetDetail = new FmsCapitalBudgetDetail();
        }
        return fmsCapitalBudgetDetail;
    }

    public void setFmsCapitalBudgetDetail(FmsCapitalBudgetDetail fmsCapitalBudgetDetail) {
        this.fmsCapitalBudgetDetail = fmsCapitalBudgetDetail;
    }

    public FmsCbDisbursement getFmsCbDisbursement() {
        if (fmsCbDisbursement == null) {
            fmsCbDisbursement = new FmsCbDisbursement();
        }
        return fmsCbDisbursement;
    }

    public void setFmsCbDisbursement(FmsCbDisbursement fmsCbDisbursement) {
        this.fmsCbDisbursement = fmsCbDisbursement;
    }

    public DataModel<FmsCbDisbursement> getFmsCbDisbursementmodel() {
        return fmsCbDisbursementmodel;
    }

    public void setFmsCbDisbursementmodel(DataModel<FmsCbDisbursement> fmsCbDisbursementmodel) {
        this.fmsCbDisbursementmodel = fmsCbDisbursementmodel;
    }

    public DataModel<FmsCapitalBudgetDetail> getFmsCapitalBudgetDetailmodel() {
        return fmsCapitalBudgetDetailmodel;
    }

    public void setFmsCapitalBudgetDetailmodel(DataModel<FmsCapitalBudgetDetail> fmsCapitalBudgetDetailmodel) {
        this.fmsCapitalBudgetDetailmodel = fmsCapitalBudgetDetailmodel;
    }

    public DataModel<FmsCapitalBudget1> getFmsCapitalBudgetDetailSrcmodel() {
        return fmsCapitalBudgetDetailSrcmodel;
    }

    public void setFmsCapitalBudgetDetailSrcmodel(DataModel<FmsCapitalBudget1> fmsCapitalBudgetDetailSrcmodel) {
        this.fmsCapitalBudgetDetailSrcmodel = fmsCapitalBudgetDetailSrcmodel;
    }

    public FmsCapitalBudget1 getCperatingBudgetSelection() {
        return cperatingBudgetSelection;
    }

    public void setCperatingBudgetSelection(FmsCapitalBudget1 cperatingBudgetSelection) {
        this.cperatingBudgetSelection = cperatingBudgetSelection;
    }

    public FmsCbDisbursement getCbDisbursementSelection() {
        return cbDisbursementSelection;
    }

    public void setCbDisbursementSelection(FmsCbDisbursement cbDisbursementSelection) {
        this.cbDisbursementSelection = cbDisbursementSelection;
    }

    public FmsCapitalBudgetDetail getCapitalBudgetDetailSelection() {
        return capitalBudgetDetailSelection;
    }

    public void setCapitalBudgetDetailSelection(FmsCapitalBudgetDetail capitalBudgetDetailSelection) {
        this.capitalBudgetDetailSelection = capitalBudgetDetailSelection;
    }

    public FmsCostCenter getFmsCostCenter() {
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

    public List<FmsCapitalBudget1> getFmsCapitalBudget1List() {
        return fmsCapitalBudget1List;
    }

    public void setFmsCapitalBudget1List(List<FmsCapitalBudget1> fmsCapitalBudget1List) {
        this.fmsCapitalBudget1List = fmsCapitalBudget1List;
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

    public CBDisbursmentControllerNew() {
        numberConverter.setPattern("#,##0.00##");
        numberConverter.setMinIntegerDigits(1);
        numberConverter.setMaxIntegerDigits(50);
        numberConverter.setMaxFractionDigits(2);
    }

    public void setSaveOrUpdate(String saveOrUpdate) {
        this.saveOrUpdate = saveOrUpdate;
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="createNewCBDisbView">
    public void createNewCBDisbView() {
        if (createOrSearchBundle.equals("New")) {
            renderPnlCreateAdditional = false;
            renderPnlManPage = true;
            renderNewSrcBtn = false;
            createOrSearchBundle = "Search";
            reqheaderTitle = "Capital Budget Disbursement";
            buttonRenderd = false;
            approveRenderd = false;
            setIcone("ui-icon-search");
            reset();
        } else if (createOrSearchBundle.equals("Search")) {
            renderPnlCreateAdditional = true;
            renderNewSrcBtn = false;
            renderPnlManPage = false;
            createOrSearchBundle = "New";
            reqheaderTitle = "Capital Budget Disbursement Search";
            buttonRenderd = false;
            approveRenderd = true;
            setIcone("ui-icon-plus");
            reset();
        }
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="reset">

    public void reset() {
        fmsCapitalBudget1 = new FmsCapitalBudget1();
        fmsCapitalBudgetDetail = new FmsCapitalBudgetDetail();
        fmsCostCenter = new FmsCostCenter();
        fmsLuSystem = new FmsLuSystem();
        fmsLuBudgetYear = new FmsLuBudgetYear();
        fmsGeneralLedger = new FmsGeneralLedger();
        fmsCapitalBudgetDetailmodel = new ArrayDataModel<>();
        fmsCapitalBudgetDetailSrcmodel = new ArrayDataModel<>();
        fmsCbDisbursementmodel = new ArrayDataModel<>();
        fmsCbDisbursement = new FmsCbDisbursement();
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
                nextPeriod = listLuBudgetYearList.get(i + 1);
            }
        }
        budgetyrList.add(currPeriod.getLuBudgetYearId());
        budgetyrList.add(nextPeriod);
        return JsfUtil.getSelectItems(budgetyrList, true);
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="getCostCenterSearch">

    public SelectItem[] getCostCenterSearch() {

        fmsLuSystem = fmsCostCenter.getSystemId();
        if (fmsLuSystem != null) {
            return JsfUtil.getSelectItems(fmsCostCenterBeanLocal.findAll(), true);
        } else {
            SelectItem[] items = new SelectItem[1];
            return items;
        }
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="searchYearValueChange">

    public void searchYearValueChange(ValueChangeEvent event) {
        if (!event.getNewValue().toString().isEmpty()) {
            fmsLuBudgetYear.setBudgetYear(event.getNewValue().toString());
            fmsLuBudgetYear = budgetYearLookUpBeanLocal.getYearInfo(fmsLuBudgetYear);
            fmsCapitalBudget1.setBudgetYear(fmsLuBudgetYear);
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
        }
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="searchCBDisbByCriteria">

    public void searchCBDisbByCriteria() {
        fmsCbDisbursementList = new ArrayList<>();
//        fmsCbDisbursementList = fmsCBDisbursementBeanLocal.fetchDisbursedBudget(fmsLuBudgetYear, fmsCostCenter,pm);

        if (!fmsCbDisbursementList.isEmpty()) {
            recreateDisbursementModel();
        } else {
            recreateDisbursementModel();
            JsfUtil.addFatalMessage("No result found.");
        }
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="recreateDisbursementModel">

    public void recreateDisbursementModel() {
        fmsCbDisbursementmodel = null;
        fmsCbDisbursementmodel = new ListDataModel(new ArrayList(fmsCbDisbursementList));

    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="getSystemSearchList">

    public SelectItem[] getSystemSearchList() {
        return JsfUtil.getSelectItems(fmsLuSystemBeanLocal.findAll(), true);
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="OBReqcodes">

    public SelectItem[] OBReqcodes() {
        if (fmsCostCenter != null) {
            return JsfUtil.getSelectItems(fmsCapitalBudgetBeanLocal.findCBSystemAndCostCenterAuthorized(fmsCostCenter), true);
        } else {
            SelectItem[] items = new SelectItem[1];
            return items;
        }
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="recreateDataModel">

    public void recreateDataModel() {

        fmsCapitalBudgetDetailmodel = null;
        fmsCapitalBudgetDetailmodel = new ListDataModel(new ArrayList(fmsCapitalBudget1.getFmsCapitalBudgetDetailList()));
        fmsCapitalBudgetDetail = new FmsCapitalBudgetDetail();
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="searchByReqCode">

    public void searchByReqCode() {
        fmsCapitalBudgetDetailBeanLocal.fetchSelectedCBRequest(fmsCapitalBudget1);
        recreateDisbursementModel();
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="calCBDisbursement">

    public void calCBDisbursement() {
        disbursedAmt = fmsCbDisbursement.getHamle().add(
                fmsCbDisbursement.getNehasie()).add(
                        fmsCbDisbursement.getMeskerem().add(
                                fmsCbDisbursement.getTikemt()).add(
                                fmsCbDisbursement.getHidar()).add(
                                fmsCbDisbursement.getTahsas()).add(
                                fmsCbDisbursement.getTir()).add(
                                fmsCbDisbursement.getYekatit()).add(
                                fmsCbDisbursement.getMegabit()).add(
                                fmsCbDisbursement.getMiyaziya().add(
                                        fmsCbDisbursement.getGinbot()).add(
                                        fmsCbDisbursement.getSene())));
        unAllocatedAmt = fmsCbDisbursement.getCbTaskId().getAllotedAmount().subtract(disbursedAmt);
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="searchPopulate">

    public void searchPopulate(SelectEvent event) {
        fmsCbDisbursement = (FmsCbDisbursement) event.getObject();
        calCBDisbursement();

        renderNewSrcBtn = true;
        renderPnlCreateAdditional = false;
        renderPnlManPage = true;
        createOrSearchBundle = "Search";
        reqheaderTitle = "Capital Budget Disbursement";
        buttonRenderd = true;
        approveRenderd = false;
        saveOrUpdate = "Edit";
        setIcone("ui-icon-search");
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="saveCBDisbursement">

    public void saveCBDisbursement() {
        calCBDisbursement();
        if (unAllocatedAmt.compareTo(BigDecimal.ZERO) == 0) {
            try {
                AAA securityService = new AAA();
                IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
                String systemBundle = "et/gov/eep/commonApplications/securityServer";
                String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
                if (security.checkAccess(SessionBean.getUserName(), "saveCBDisbursement", dataset)) {

                    double x = 0;
                    x = fmsCbDisbursement.getHamle().doubleValue()
                            + fmsCbDisbursement.getNehasie().doubleValue()
                            + fmsCbDisbursement.getMeskerem().doubleValue()
                            + fmsCbDisbursement.getTikemt().doubleValue()
                            + fmsCbDisbursement.getHidar().doubleValue()
                            + fmsCbDisbursement.getTahsas().doubleValue()
                            + fmsCbDisbursement.getTir().doubleValue()
                            + fmsCbDisbursement.getYekatit().doubleValue()
                            + fmsCbDisbursement.getMegabit().doubleValue()
                            + fmsCbDisbursement.getMiyaziya().doubleValue()
                            + fmsCbDisbursement.getGinbot().doubleValue()
                            + fmsCbDisbursement.getSene().doubleValue();
                    if (x > fmsCbDisbursement.getCbTaskId().getAllotedAmount().doubleValue()) {
                        JsfUtil.addFatalMessage("Capital budget disbursment has exceeded the approved amount.");
                    } else {
                        fmsCBDisbursementBeanLocal.edit(fmsCbDisbursement);
                        JsfUtil.addSuccessMessage("Capital Budget Disbursed Succesfully.");
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
    

}
