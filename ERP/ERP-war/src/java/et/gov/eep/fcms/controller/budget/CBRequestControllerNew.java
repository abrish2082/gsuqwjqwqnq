package et.gov.eep.fcms.controller.budget;
//<editor-fold defaultstate="collapsed" desc="import">

import et.gov.eep.commonApplications.entity.WfFcmsProcessed;
import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.fcms.businessLogic.FmsLuBudgetYearBeanLocal;
import et.gov.eep.fcms.businessLogic.admin.FmsAccountingPeriodBeanLocal;
import et.gov.eep.fcms.businessLogic.admin.FmsCostCenterBeanLocal;
import et.gov.eep.fcms.businessLogic.admin.FmsGeneralLedgerBeanLocal;
import et.gov.eep.fcms.businessLogic.admin.FmsLuSystemBeanLocal;
import et.gov.eep.fcms.businessLogic.budget.FmsCBTasksBeanLocal;
import et.gov.eep.fcms.businessLogic.budget.FmsCapitalBudgetBeanLocal;
import et.gov.eep.fcms.businessLogic.budget.FmsCapitalBudgetDetailBeanLocal;
import et.gov.eep.fcms.businessLogic.budget.FmsLuBudgetSourceBeanLocal;
import et.gov.eep.fcms.businessLogic.budget.budgetCodeBeanLocal;
import et.gov.eep.fcms.businessLogic.budgetYearLookUpBeanLocal;
import et.gov.eep.commonApplications.businessLogic.WfFcmsProcessedBeanLocal;
import et.gov.eep.fcms.businessLogic.admin.FmsCostCSystemJunctionBeanLocal;
import et.gov.eep.fcms.businessLogic.admin.FmsSystemJobJunctionBeanLocal;
import et.gov.eep.fcms.businessLogic.admin.subsidiaryBeanLocal;
import et.gov.eep.fcms.entity.FmsLuBudgetYear;
import et.gov.eep.fcms.entity.admin.FmsAccountingPeriod;
import et.gov.eep.fcms.entity.admin.FmsCostCenter;
import et.gov.eep.fcms.entity.admin.FmsCostcSystemJunction;
import et.gov.eep.fcms.entity.admin.FmsGeneralLedger;
import et.gov.eep.fcms.entity.admin.FmsLuSystem;
import et.gov.eep.fcms.entity.admin.FmsSubsidiaryLedger;
import et.gov.eep.fcms.entity.admin.FmsSystemJobJunction;
import et.gov.eep.fcms.entity.budget.FmsBudgetCode;
import et.gov.eep.fcms.entity.budget.FmsCapitalBudget1;
import et.gov.eep.fcms.entity.budget.FmsCapitalBudgetDetail;
import et.gov.eep.fcms.entity.budget.FmsCapitalBudgetTasks;
import et.gov.eep.fcms.entity.budget.FmsLuBudgetSource;
import et.gov.eep.pms.businessLogic.PmsWorkAuthorizationBeanLocal;
import et.gov.eep.pms.entity.PmsWorkAuthorization;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.annotation.PostConstruct;
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
import securityBean.Constants;
import securityBean.SessionBean;
import webService.AAA;
import webService.EventEntry;
import webService.IAdministration;
//</editor-fold>

@Named(value = "cBRequestControllernew")
@ViewScoped
public class CBRequestControllerNew implements Serializable {
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
    FmsBudgetCode fmsBudgetCode;

    @Inject
    PmsWorkAuthorization pmsWorkAuthorization;
    @Inject
    FmsCapitalBudgetTasks fmsCapitalBudgetTasks;
    @Inject
    FmsLuBudgetSource fmsLuBudgetSource;
    @Inject
    WfFcmsProcessed wfFcmsProcessed;
    @Inject
    FmsCostcSystemJunction fmsCostcSystemJunction;
    @Inject
    FmsSubsidiaryLedger fmsSubsid1aryLedger;
    @Inject
    FmsSystemJobJunction fmsSystemJobJunction;

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="EJB">
    @EJB
    FmsSystemJobJunctionBeanLocal fmsSystemJobJunctionBeanLocal;
    @EJB
    subsidiaryBeanLocal subLedgerBeanLocal;
    @EJB
    WfFcmsProcessedBeanLocal wfFcmsProcessedBeanLocal;
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
    budgetCodeBeanLocal fmsBudgetCodeBeanLocal;
    @EJB
    PmsWorkAuthorizationBeanLocal pmsWorkAuthorizationBeanLocal;
    @EJB
    FmsCBTasksBeanLocal fmsCBTasksBeanLocal;
    @EJB
    FmsLuBudgetSourceBeanLocal fmsLuBudgetSourceBeanLocal;
    @EJB
    FmsCostCSystemJunctionBeanLocal fmsCostCSystemJunctionBeanLocal;
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="variable declaration">
    Constants constants;

    FmsAccountingPeriod currPeriod = new FmsAccountingPeriod();
    FmsLuBudgetYear nextPeriod = new FmsLuBudgetYear();
    List<FmsLuBudgetYear> budgetyrList = new ArrayList<>();
    List<FmsLuBudgetYear> listLuBudgetYearList = new ArrayList<>();
    List<FmsCapitalBudget1> fmsCapitalBudget1List = new ArrayList<>();
    List<FmsCapitalBudgetTasks> fmsCapitalBudgetTasksList = new ArrayList<>();
    List<FmsCostCenter> costCenterList;
    List<FmsSystemJobJunction> sysJobNOList;
    List<FmsBudgetCode> capitaBudgetCodeLists;
    BigDecimal totalRequestedAmount = new BigDecimal(BigInteger.ZERO);
    BigDecimal totalGLRequestedAmount = new BigDecimal(BigInteger.ZERO);
    BigDecimal totalSLRequestedAmount = new BigDecimal(BigInteger.ZERO);
    private Double allotedTaskAmount = (double) 0;

    DataModel<WfFcmsProcessed> wfFcmsProcessedDataModel;
    DataModel<FmsCapitalBudgetDetail> fmsCapitalBudgetDetailmodel;
    DataModel<FmsCapitalBudget1> fmsCapitalBudgetDetailSrcmodel;
    DataModel<FmsCapitalBudgetTasks> fmsCapitalBudgetTasksDataModel;
    FmsCapitalBudget1 capitalBudgetSelection;
    FmsCapitalBudgetDetail capitalBudgetDetailSelection;
    int detailPopulate = 0;
    String userName = new String();
    private NumberConverter numberConverter = new NumberConverter();

    private String createOrSearchBundle = "New";
    private boolean renderPnlCreateAdditional = true;
    private boolean renderPnlManPage = false;
    private String icone = "ui-icon-plus";
    private boolean buttonRenderd = false;
    private boolean approveRenderd = false;
    private String reqheaderTitle = "Capital Budget Request Search";
    private String saveOrUpdate = "Create";
    private String buttonName = "Save";
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="getter and setters">
    public SelectItem[] getSystemSearchList() {
        return JsfUtil.getSelectItems(fmsLuSystemBeanLocal.findAll(), true);
    }

    public SelectItem[] getBudgetCodeList() {
        return JsfUtil.getSelectItems(fmsBudgetCodeBeanLocal.findAllCapital(), true);
    }

    public List<FmsBudgetCode> getCapitaBudgetCodeLists() {
        if (capitaBudgetCodeLists == null) {
            capitaBudgetCodeLists = new ArrayList<>();
            capitaBudgetCodeLists = fmsBudgetCodeBeanLocal.findAllCapital();
        }
        return capitaBudgetCodeLists;
    }

    public void setCapitaBudgetCodeLists(List<FmsBudgetCode> capitaBudgetCodeLists) {
        this.capitaBudgetCodeLists = capitaBudgetCodeLists;
    }

    public Constants getConstants() {
        return constants;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public FmsCostcSystemJunction getFmsCostcSystemJunction() {
        if (fmsCostcSystemJunction == null) {
            fmsCostcSystemJunction = new FmsCostcSystemJunction();
        }
        return fmsCostcSystemJunction;
    }

    public void setFmsCostcSystemJunction(FmsCostcSystemJunction fmsCostcSystemJunction) {
        this.fmsCostcSystemJunction = fmsCostcSystemJunction;
    }

    public FmsSubsidiaryLedger getFmsSubsid1aryLedger() {
        if (fmsSubsid1aryLedger == null) {
            fmsSubsid1aryLedger = new FmsSubsidiaryLedger();
        }

        return fmsSubsid1aryLedger;
    }

    public void setFmsSubsid1aryLedger(FmsSubsidiaryLedger fmsSubsid1aryLedger) {
        this.fmsSubsid1aryLedger = fmsSubsid1aryLedger;
    }

    public BigDecimal getTotalGLRequestedAmount() {
        return totalGLRequestedAmount;
    }

    public void setTotalGLRequestedAmount(BigDecimal totalGLRequestedAmount) {
        this.totalGLRequestedAmount = totalGLRequestedAmount;
    }

    public BigDecimal getTotalSLRequestedAmount() {
        return totalSLRequestedAmount;
    }

    public void setTotalSLRequestedAmount(BigDecimal totalSLRequestedAmount) {
        this.totalSLRequestedAmount = totalSLRequestedAmount;
    }

    public Double getAllotedTaskAmount() {
        return allotedTaskAmount;
    }

    public void setAllotedTaskAmount(Double allotedTaskAmount) {
        this.allotedTaskAmount = allotedTaskAmount;
    }

    public List<FmsSystemJobJunction> getSysJobNOList() {
        if (sysJobNOList == null) {
            sysJobNOList = new ArrayList<>();
        }
        return sysJobNOList;
    }

    public void setSysJobNOList(List<FmsSystemJobJunction> sysJobNOList) {
        this.sysJobNOList = sysJobNOList;
    }

    public FmsLuBudgetSource getFmsLuBudgetSource() {
        if (fmsLuBudgetSource == null) {
            fmsLuBudgetSource = new FmsLuBudgetSource();
        }
        return fmsLuBudgetSource;
    }

    public void setFmsLuBudgetSource(FmsLuBudgetSource fmsLuBudgetSource) {
        this.fmsLuBudgetSource = fmsLuBudgetSource;
    }

    public List<FmsCostCenter> getCostCenterList() {
        if (costCenterList == null) {
            costCenterList = new ArrayList<>();
        }
        return costCenterList;
    }

    public void setCostCenterList(List<FmsCostCenter> costCenterList) {
        this.costCenterList = costCenterList;
    }

    public FmsCapitalBudgetTasks getFmsCapitalBudgetTasks() {
        return fmsCapitalBudgetTasks;
    }

    public void setFmsCapitalBudgetTasks(FmsCapitalBudgetTasks fmsCapitalBudgetTasks) {
        this.fmsCapitalBudgetTasks = fmsCapitalBudgetTasks;
    }

    public List<FmsCapitalBudgetTasks> getFmsCapitalBudgetTasksList() {
        return fmsCapitalBudgetTasksList;
    }

    public void setFmsCapitalBudgetTasksList(List<FmsCapitalBudgetTasks> fmsCapitalBudgetTasksList) {
        this.fmsCapitalBudgetTasksList = fmsCapitalBudgetTasksList;
    }

    public DataModel<FmsCapitalBudgetTasks> getFmsCapitalBudgetTasksDataModel() {
        fmsCapitalBudgetTasksDataModel = new ListDataModel(fmsCapitalBudgetDetailmodel.getRowData().getFmsCapitalBudgetTasksList());
        return fmsCapitalBudgetTasksDataModel;
    }

    public void setFmsCapitalBudgetTasksDataModel(DataModel<FmsCapitalBudgetTasks> fmsCapitalBudgetTasksDataModel) {
        this.fmsCapitalBudgetTasksDataModel = fmsCapitalBudgetTasksDataModel;
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

    public BigDecimal getTotalRequestedAmount() {
        return totalRequestedAmount;
    }

    public void setTotalRequestedAmount(BigDecimal totalRequestedAmount) {
        this.totalRequestedAmount = totalRequestedAmount;
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

    public List<FmsLuBudgetYear> getListLuBudgetYearList() {
        return listLuBudgetYearList;
    }

    public void setListLuBudgetYearList(List<FmsLuBudgetYear> listLuBudgetYearList) {
        this.listLuBudgetYearList = listLuBudgetYearList;
    }

    public List<FmsCapitalBudget1> getFmsCapitalBudget1List() {
        return fmsCapitalBudget1List;
    }

    public void setFmsCapitalBudget1List(List<FmsCapitalBudget1> fmsCapitalBudget1List) {
        this.fmsCapitalBudget1List = fmsCapitalBudget1List;
    }

    public DataModel<FmsCapitalBudgetDetail> getFmsCapitalBudgetDetailmodel() {
        if (fmsCapitalBudgetDetailmodel == null) {
            fmsCapitalBudgetDetailmodel = new ArrayDataModel<>();
        }
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

    public FmsCapitalBudget1 getCapitalBudgetSelection() {
        return capitalBudgetSelection;
    }

    public void setCapitalBudgetSelection(FmsCapitalBudget1 capitalBudgetSelection) {
        this.capitalBudgetSelection = capitalBudgetSelection;
    }

    public FmsCapitalBudgetDetail getCapitalBudgetDetailSelection() {
        return capitalBudgetDetailSelection;
    }

    public void setCapitalBudgetDetailSelection(FmsCapitalBudgetDetail capitalBudgetDetailSelection) {
        this.capitalBudgetDetailSelection = capitalBudgetDetailSelection;
    }

    public int getDetailPopulate() {
        return detailPopulate;
    }

    public void setDetailPopulate(int detailPopulate) {
        this.detailPopulate = detailPopulate;
    }

    public FmsBudgetCode getFmsBudgetCode() {
        if (fmsBudgetCode == null) {
            fmsBudgetCode = new FmsBudgetCode();
        }
        return fmsBudgetCode;
    }

    public void setFmsBudgetCode(FmsBudgetCode fmsBudgetCode) {
        this.fmsBudgetCode = fmsBudgetCode;
    }

    public PmsWorkAuthorization getPmsWorkAuthorization() {
        if (pmsWorkAuthorization == null) {
            pmsWorkAuthorization = new PmsWorkAuthorization();
        }
        return pmsWorkAuthorization;
    }

    public void setPmsWorkAuthorization(PmsWorkAuthorization pmsWorkAuthorization) {
        this.pmsWorkAuthorization = pmsWorkAuthorization;
    }

    public FmsGeneralLedger getFmsGeneralLedger() {
        if (fmsGeneralLedger == null) {
            fmsGeneralLedger = new FmsGeneralLedger();
        }
        return fmsGeneralLedger;
    }

    public void setFmsGeneralLedger(FmsGeneralLedger fmsGeneralLedger) {
        this.fmsGeneralLedger = fmsGeneralLedger;
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

    public void setSaveOrUpdate(String saveOrUpdate) {
        this.saveOrUpdate = saveOrUpdate;
    }

    public String getButtonName() {
        return buttonName;
    }

    public void setButtonName(String buttonName) {
        this.buttonName = buttonName;
    }

    public NumberConverter getNumberConverter() {
        return numberConverter;
    }

    public void setNumberConverter(NumberConverter numberConverter) {
        this.numberConverter = numberConverter;
    }

    public WfFcmsProcessed getWfFcmsProcessed() {
        if (wfFcmsProcessed == null) {
            wfFcmsProcessed = new WfFcmsProcessed();
        }
        return wfFcmsProcessed;
    }

    public void setWfFcmsProcessed(WfFcmsProcessed wfFcmsProcessed) {
        this.wfFcmsProcessed = wfFcmsProcessed;
    }

    public DataModel<WfFcmsProcessed> getWfFcmsProcessedDataModel() {
        return wfFcmsProcessedDataModel;
    }

    public void setWfFcmsProcessedDataModel(DataModel<WfFcmsProcessed> wfFcmsProcessedDataModel) {
        this.wfFcmsProcessedDataModel = wfFcmsProcessedDataModel;
    }

    public FmsSystemJobJunction getFmsSystemJobJunction() {
        if (fmsSystemJobJunction == null) {
            fmsSystemJobJunction = new FmsSystemJobJunction();
        }
        return fmsSystemJobJunction;
    }

    public void setFmsSystemJobJunction(FmsSystemJobJunction fmsSystemJobJunction) {
        this.fmsSystemJobJunction = fmsSystemJobJunction;
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="createNewCBReqView">
    public void createNewCBReqView() {
        if (createOrSearchBundle.equals("New")) {
            renderPnlCreateAdditional = false;
            renderPnlManPage = true;
            createOrSearchBundle = "Search";
            reqheaderTitle = "Capital Budget Request Registration";
            buttonRenderd = true;
            approveRenderd = false;
            setIcone("ui-icon-search");
            reset();
        } else if (createOrSearchBundle.equals("Search")) {
            renderPnlCreateAdditional = true;
            renderPnlManPage = false;
            createOrSearchBundle = "New";
            reqheaderTitle = "Capital Budget Request Search";
            buttonRenderd = false;
            approveRenderd = true;
            setIcone("ui-icon-plus");
            reset();
        }
//        saveUpdateClear();
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="recreateWFDataModel">
    public void recreateWFDataModel() {
        wfFcmsProcessedDataModel = null;
        wfFcmsProcessedDataModel = new ListDataModel(new ArrayList(fmsCapitalBudget1.getWfFcmsProcessedList()));
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="getLuBudgetYearList">
    public SelectItem[] getLuBudgetYearList() {
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
    //<editor-fold defaultstate="collapsed" desc="yearValueChange">
    public void yearValueChange(ValueChangeEvent event) {
        if (!event.getNewValue().toString().isEmpty()) {
            fmsLuBudgetYear.setBudgetYear(event.getNewValue().toString());
            fmsLuBudgetYear = budgetYearLookUpBeanLocal.getYearInfo(fmsLuBudgetYear);
            fmsCapitalBudget1.setBudgetYear(fmsLuBudgetYear);
        }
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="findListSubL">
    public SelectItem[] findListSubL() {
        ArrayList<FmsSubsidiaryLedger> subsidaryList = new ArrayList<>();
        if (fmsGeneralLedger != null) {
            SelectItem[] listSl = null;
            subsidaryList = subLedgerBeanLocal.findSLbyGLandCCSS(fmsGeneralLedger, fmsCostcSystemJunction);
            // subsidaryList = subLedgerBeanLocal.findSLbyGLandCCSS1(fmsGeneralLedger);
            if (subsidaryList.size() > 0) {
                listSl = new SelectItem[subsidaryList.size() + 1];
                listSl[0] = new SelectItem(null, "--- Select One ---");
                System.out.println("12-- " + listSl.length);
                for (int i = 0; i < subsidaryList.size(); i++) {
                    listSl[i + 1] = new SelectItem(subsidaryList.get(i).getSubsidiaryId(), subsidaryList.get(i).getSubsidiaryCode());
                    System.out.println("13");
                }
            }
            return listSl;
        } else {
            SelectItem[] items = new SelectItem[1];
            items[0] = new SelectItem("", "--- Select One ---");
            return items;
        }
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="getSubsidiaryLChange">
    public void getSubsidiaryLChange(ValueChangeEvent event) {
        if (!event.getNewValue().toString().isEmpty()) {
            System.out.println("hiiiiiiiiii");
            fmsSubsid1aryLedger.setSubsidiaryId(Integer.parseInt(event.getNewValue().toString()));
            fmsSubsid1aryLedger = subLedgerBeanLocal.getSubsidiaryInfo(fmsSubsid1aryLedger);
            System.out.println("show us" + fmsSubsid1aryLedger);
            fmsCapitalBudgetTasks.setSlId(fmsSubsid1aryLedger);
        }
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="getSystemList">
    public SelectItem[] getSystemList() {
        return JsfUtil.getSelectItems(fmsLuSystemBeanLocal.findProjSystem(), true);
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="SystemChange">
    public void SystemChange(ValueChangeEvent event) {
        if (!event.getNewValue().toString().isEmpty()) {
            fmsLuSystem.setSystemCode(event.getNewValue().toString());
            fmsLuSystem = fmsLuSystemBeanLocal.getSysDetail(fmsLuSystem);
            costCenterList = fmsCostCenterBeanLocal.findMappedCostCenter(fmsLuSystem);
            sysJobNOList = fmsLuSystem.getFmsSystemJobJunctionList();
        }
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="onChangeJobNo">
    public void onChangeJobNo(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            fmsSystemJobJunction = (FmsSystemJobJunction) event.getNewValue();
            pmsWorkAuthorization.setWorkAuthoId(fmsSystemJobJunction.getWorkAuthoFk().getWorkAuthoId());
            pmsWorkAuthorization = pmsWorkAuthorizationBeanLocal.findByWorkAutId(pmsWorkAuthorization);
            fmsCapitalBudget1.setJobNo(pmsWorkAuthorization);

            int obReqCount = fmsCapitalBudgetBeanLocal.CostCenterDuplicationChecker(fmsLuBudgetYear, fmsCostcSystemJunction, pmsWorkAuthorization);
            if (obReqCount > 0) {
                JsfUtil.addFatalMessage("A request has already been made with cost center " + fmsCostCenter.getSystemName() + " on " + fmsLuBudgetYear.getBudgetYear() + " budget year.");
                reset();
            } else {
                int count = fmsCapitalBudgetBeanLocal.RowCount() + 1;
                String conc = "Cap-" + fmsLuSystem.getSystemCode() + "-" + fmsCostCenter.getSystemName() + "-" + pmsWorkAuthorization.getJobNo() + "-" + count;
                fmsCapitalBudget1.setRequestCode(conc);
                fmsCostcSystemJunction = fmsCostCSystemJunctionBeanLocal.findByCCandSS(fmsLuSystem, fmsCostCenter);
                fmsCapitalBudget1.setCcSsJunction(fmsCostcSystemJunction);
            }
        }

    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="CostCenterChange">
    public void CostCenterChange(ValueChangeEvent event) {
        fmsCostcSystemJunction = new FmsCostcSystemJunction();
        if (!event.getNewValue().toString().isEmpty()) {
            fmsCostCenter.setSystemName(event.getNewValue().toString());
            fmsCostCenter = fmsCostCenterBeanLocal.getCostDetail(fmsCostCenter);
            fmsCostcSystemJunction = fmsCostCSystemJunctionBeanLocal.findByCCandSS(fmsLuSystem, fmsCostCenter);
            fmsCapitalBudget1.setCcSsJunction(fmsCostcSystemJunction);

        }
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="BudgetSourceChange">
    public void BudgetSourceChange(ValueChangeEvent event) {
        if (!event.getNewValue().toString().isEmpty()) {
            fmsLuBudgetSource.setBudgetSourceName(event.getNewValue().toString());
            fmsLuBudgetSource = fmsLuBudgetSourceBeanLocal.findBudgetSrc(fmsLuBudgetSource);
        }
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="getBudgetSourceList">
    public SelectItem[] getBudgetSourceList() {
        return JsfUtil.getSelectItems(fmsLuBudgetSourceBeanLocal.findAllBudetSource(), true);
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="budgetCodeChange">
    public void budgetCodeChange(ValueChangeEvent event) {

        if (!event.getNewValue().toString().isEmpty()) {
            System.out.println("event value null-- " + event.getNewValue().toString());
            fmsBudgetCode.setBudgetCode(event.getNewValue().toString());
            fmsBudgetCode = fmsBudgetCodeBeanLocal.getBudgetCode(fmsBudgetCode);
            fmsCapitalBudgetDetail.setBudgetCode(fmsBudgetCode);
        } else {
            System.out.println("is null");
        }
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="getJobNoByGL">
    public SelectItem[] getJobNoByGL() {

        fmsGeneralLedger = fmsBudgetCode.getGeneralLedger();
        if (fmsGeneralLedger != null) {
            return JsfUtil.getSelectItems(pmsWorkAuthorizationBeanLocal.findJobNoByGL(fmsGeneralLedger), true);
        } else {
            SelectItem[] items = new SelectItem[1];
            items[0] = new SelectItem("", "---Select One---");
            return items;
        }
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="getCostCenterBySystemLU">

    public SelectItem[] getCostCenterBySystemLU() {

        fmsLuSystem = fmsCostCenter.getSystemId();
        if (fmsLuSystem != null) {
            return JsfUtil.getSelectItems(fmsCostCenterBeanLocal.findCostCenter(fmsLuSystem), true);
        } else {
            SelectItem[] items = new SelectItem[1];
            return items;
        }
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="getCostCenterListForRequest">

    public SelectItem[] getCostCenterListForRequest() {
        if (fmsLuSystem.getSystemId() != null) {
            return JsfUtil.getSelectItems(fmsCostCenterBeanLocal.findCostCenterForRequest(fmsLuSystem), true);
        } else {
            SelectItem[] items = new SelectItem[1];
            items[0] = new SelectItem("", "--Select One--");
            return items;
        }
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="addOBDetail">
    //-------------CHANGE------------------

    public void addOBDetail() {
        int duplicationChecker = 0;
        if (detailPopulate == 0) {
            for (FmsCapitalBudgetDetail fmsCapitalBudgetDetailList : fmsCapitalBudget1.getFmsCapitalBudgetDetailList()) {
                if (fmsCapitalBudgetDetail.getBudgetCode().equals(fmsCapitalBudgetDetailList.getBudgetCode())) {
                    duplicationChecker = 1;
                    JsfUtil.addFatalMessage("You can not duplicate general ledger on your request.");
                    recreateDataModel();
                }
            }
        }
        if (duplicationChecker == 0 && detailPopulate == 1) {
            fmsCapitalBudget1.addCapitalBudgetDetail(fmsCapitalBudgetDetail);
            recreateDataModel();
            detailPopulate = 0;
        }
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="addCBReqDetail">
    //-------------CHANGE------------------

    public void addCBReqDetail() {

        boolean duplicated = false;
        for (int i = 0; i < fmsCapitalBudget1.getFmsCapitalBudgetDetailList().size(); i++) {
            if (detailPopulate == 0 && fmsCapitalBudget1.getFmsCapitalBudgetDetailList().get(i).getBudgetCode().getBudgetId() == fmsBudgetCode.getBudgetId()) {
                duplicated = true;
                JsfUtil.addFatalMessage("Can not duplicate budget code on your request");
            }
        }
        if (duplicated == false) {
            fmsCapitalBudget1.addCapitalBudgetDetail(fmsCapitalBudgetDetail);
            detailPopulate = 0;
            recreateDataModel();
        }
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="addCBTask">

    public void addCBTask() {
        if (fmsSubsid1aryLedger != null && fmsCapitalBudgetTasks.getTaskName() != null && fmsCapitalBudgetTasks.getAllotedAmount() != null) {
            try {
                for (int i = 0; i < fmsCapitalBudgetDetail.getFmsCapitalBudgetTasksList().size(); i++) {
                    allotedTaskAmount += fmsCapitalBudgetDetail.getFmsCapitalBudgetTasksList().get(i).getAllotedAmount().doubleValue();
                }
                fmsCapitalBudgetDetail.addCapitalBudgetTasksList(fmsCapitalBudgetTasks);
                fmsCapitalBudgetTasksDataModel = null;
                fmsCapitalBudgetTasksDataModel = new ListDataModel(new ArrayList(fmsCapitalBudgetDetail.getFmsCapitalBudgetTasksList()));
                //totalGLAmtCalc();
                totalSLAmtCalc();
                resetCBDetail();
                resetCBTask();
            } catch (NullPointerException ex) {
                throw ex;
            }
        }
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="resetCBDetail">

    public void resetCBDetail() {
        fmsCapitalBudgetDetailmodel = null;
        fmsCapitalBudgetDetailmodel = new ListDataModel(new ArrayList(fmsCapitalBudget1.getFmsCapitalBudgetDetailList()));
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="removeTask">

    public void removeTask() {
        fmsCapitalBudgetTasks = new FmsCapitalBudgetTasks();
        fmsCapitalBudgetTasks = getFmsCapitalBudgetTasksDataModel().getRowData();
        fmsCBTasksBeanLocal.delete(fmsCapitalBudgetTasks);
        resetCBDetail();
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="recreateDataModel">

    public void recreateDataModel() {
        fmsCapitalBudgetDetailmodel = null;
        fmsCapitalBudgetDetailmodel = new ListDataModel(new ArrayList(fmsCapitalBudget1.getFmsCapitalBudgetDetailList()));
        fmsCapitalBudgetDetail = new FmsCapitalBudgetDetail();
        fmsBudgetCode = new FmsBudgetCode();
        fmsLuBudgetSource = new FmsLuBudgetSource();
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="resetCBTask">

    public void resetCBTask() {
        fmsSubsid1aryLedger = new FmsSubsidiaryLedger();
        fmsCapitalBudgetTasks = new FmsCapitalBudgetTasks();
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="saveCBReq">

    public void saveCBReq() {

        try {
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");

            if (security.checkAccess(SessionBean.getUserName(), "saveCBReq", dataset)) {
                if ("Create".equals(saveOrUpdate) && !fmsCapitalBudget1.getFmsCapitalBudgetDetailList().isEmpty()) {
                    double x = 0;
                    for (int i = 0; i < fmsCapitalBudget1.getFmsCapitalBudgetDetailList().size(); i++) {
                        x += fmsCapitalBudget1.getFmsCapitalBudgetDetailList().get(i).getRequestedAmount().doubleValue();
                    }
                    fmsCapitalBudget1.setTotalReqAmount(new BigDecimal(x));
                    fmsCapitalBudget1.setStatus(Constants.REQUEST_VALUE);
                    fmsCapitalBudgetBeanLocal.create(fmsCapitalBudget1);

                    wfFcmsProcessed.setCapitalBudgetId(fmsCapitalBudget1);
                    wfFcmsProcessed.setProcessedOn(fmsCapitalBudget1.getRequestDate());
                    wfFcmsProcessed.setCommentGiven(fmsCapitalBudget1.getRequestRemark());
                    wfFcmsProcessed.setProcessedBy(BigInteger.valueOf(SessionBean.getUserId()));
                    wfFcmsProcessed.setDecision(Constants.REQUEST_VALUE);
                    wfFcmsProcessedBeanLocal.create(wfFcmsProcessed);
                    JsfUtil.addSuccessMessage("Capital Budget Request Saved.");
                    reset();
                    x = 0;
                } else if ("Edit".equals(saveOrUpdate) && !fmsCapitalBudget1.getFmsCapitalBudgetDetailList().isEmpty()) {
                    double x = 0;
                    for (int i = 0; i < fmsCapitalBudget1.getFmsCapitalBudgetDetailList().size(); i++) {
                        x += fmsCapitalBudget1.getFmsCapitalBudgetDetailList().get(i).getRequestedAmount().doubleValue();
                    }
                    fmsCapitalBudget1.setTotalReqAmount(new BigDecimal(x));
                    fmsCapitalBudgetBeanLocal.edit(fmsCapitalBudget1);
                    wfFcmsProcessed.setCapitalBudgetId(fmsCapitalBudget1);
                    wfFcmsProcessed.setProcessedOn(fmsCapitalBudget1.getRequestDate());
                    wfFcmsProcessed.setCommentGiven(fmsCapitalBudget1.getRequestRemark());
                    wfFcmsProcessed.setProcessedBy(BigInteger.valueOf(SessionBean.getUserId()));
                    wfFcmsProcessed.setDecision(Constants.REQUEST_VALUE);
                    wfFcmsProcessedBeanLocal.create(wfFcmsProcessed);
                    JsfUtil.addSuccessMessage("Capital Budget Request Updated.");
                    reset();
                    x = 0;
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
        wfFcmsProcessed = new WfFcmsProcessed();
        wfFcmsProcessedDataModel = new ArrayDataModel<>();
        buttonName = "Save";
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="decisionChange">

    public void decisionChange() {
        try {
            if (!fmsCapitalBudget1.getWfFcmsProcessedList().isEmpty()) {
                for (int i = 0; i < fmsCapitalBudget1.getWfFcmsProcessedList().size(); i++) {
                    if (fmsCapitalBudget1.getWfFcmsProcessedList().get(i).getDecision() == 17 ^ fmsCapitalBudget1.getWfFcmsProcessedList().get(i).getDecision() == 18) {
                        fmsCapitalBudget1.getWfFcmsProcessedList().get(i).setChangedDecision("Requested");
                    } else if (fmsCapitalBudget1.getWfFcmsProcessedList().get(i).getDecision() == 0 ^ fmsCapitalBudget1.getWfFcmsProcessedList().get(i).getDecision() == 2) {
                        fmsCapitalBudget1.getWfFcmsProcessedList().get(i).setChangedDecision("Prepared");
                    } else if (fmsCapitalBudget1.getWfFcmsProcessedList().get(i).getDecision() == 1 ^ fmsCapitalBudget1.getWfFcmsProcessedList().get(i).getDecision() == 4) {
                        fmsCapitalBudget1.getWfFcmsProcessedList().get(i).setChangedDecision("Checked");
                    } else if (fmsCapitalBudget1.getWfFcmsProcessedList().get(i).getDecision() == 3 ^ fmsCapitalBudget1.getWfFcmsProcessedList().get(i).getDecision() == 11) {
                        fmsCapitalBudget1.getWfFcmsProcessedList().get(i).setChangedDecision("Approved");
                    } else if (fmsCapitalBudget1.getWfFcmsProcessedList().get(i).getDecision() == 10) {
                        fmsCapitalBudget1.getWfFcmsProcessedList().get(i).setChangedDecision("Authorized");
                    }
                }
            }
        } catch (IndexOutOfBoundsException ex) {
            ex.printStackTrace();
        }
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
            fmsCapitalBudget1.setBudgetYear(fmsLuBudgetYear);
        }
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="SystemSearchChange">
    public void SystemSearchChange(ValueChangeEvent event) {
        if (!event.getNewValue().toString().isEmpty()) {
            fmsLuSystem.setSystemCode(event.getNewValue().toString());
            fmsLuSystem = fmsLuSystemBeanLocal.getSysDetail(fmsLuSystem);
            costCenterList = fmsCostCenterBeanLocal.findMappedCostCenter(fmsLuSystem);
        }
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="CostSearchChange">

    public void CostSearchChange(ValueChangeEvent event) {
        if (!event.getNewValue().toString().isEmpty()) {
            fmsCostCenter.setSystemName(event.getNewValue().toString());
            fmsCostCenter = fmsCostCenterBeanLocal.getCCDetail(fmsCostCenter);
            fmsCostcSystemJunction = fmsCostCSystemJunctionBeanLocal.findByCCandSS(fmsLuSystem, fmsCostCenter);

        }
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="searchCBReq">

    public void searchCBReq() {
        if (fmsLuSystem.getSystemCode() == null && fmsLuBudgetYear.getBudgetYear() == null && fmsCostCenter.getSystemName() == null) {
            fmsCapitalBudget1List = fmsCapitalBudgetBeanLocal.findAllRequest();
            fmsCapitalBudgetDetailSrcmodel = new ListDataModel(fmsCapitalBudget1List);
            totalAmtCalc();
        } else if (fmsLuBudgetYear.getBudgetYear() != null && fmsLuSystem.getSystemCode() == null && fmsCostCenter.getSystemName() == null) {
            fmsCapitalBudget1List = fmsCapitalBudgetBeanLocal.findByBudgetYear(fmsLuBudgetYear);
            fmsCapitalBudgetDetailSrcmodel = new ListDataModel(fmsCapitalBudget1List);
            totalAmtCalc();
        } else if (fmsLuBudgetYear.getBudgetYear() != null && fmsLuSystem.getSystemCode() != null && fmsCostCenter.getSystemName() == null) {
            fmsCapitalBudget1List = fmsCapitalBudgetBeanLocal.findByBudgetYearAndSystem(fmsLuBudgetYear, fmsCostcSystemJunction);
            fmsCapitalBudgetDetailSrcmodel = new ListDataModel(fmsCapitalBudget1List);
            totalAmtCalc();
        } else if (fmsLuBudgetYear.getBudgetYear() != null && fmsCostCenter.getSystemName() != null) {
            fmsCapitalBudget1List = fmsCapitalBudgetBeanLocal.findByBudgetYearAndCostCenter(fmsLuBudgetYear, fmsCostcSystemJunction);
            fmsCapitalBudgetDetailSrcmodel = new ListDataModel(fmsCapitalBudget1List);
            totalAmtCalc();
        } else if (fmsLuSystem.getSystemCode() != null || fmsLuBudgetYear.getBudgetYear() == null || fmsCostCenter.getSystemName() != null) {
            fmsCapitalBudget1List = fmsCapitalBudgetBeanLocal.findBySystem(fmsCostcSystemJunction);
            fmsCapitalBudgetDetailSrcmodel = new ListDataModel(fmsCapitalBudget1List);
            totalAmtCalc();
        } else if (fmsLuSystem.getSystemCode() != null || fmsLuBudgetYear.getBudgetYear() != null || fmsCostCenter.getSystemName() != null) {
            fmsCapitalBudget1List = fmsCapitalBudgetBeanLocal.findBySystemAndCostCenter(fmsCostCenter);
            fmsCapitalBudgetDetailSrcmodel = new ListDataModel(fmsCapitalBudget1List);
            totalAmtCalc();
        }
        if (fmsCapitalBudget1List.isEmpty()) {
            JsfUtil.addFatalMessage("No result found.");
        }
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="getCostCenterSearch">

    public SelectItem[] getCostCenterSearch() {

        fmsLuSystem = fmsCostCenter.getSystemId();
        if (fmsLuSystem != null) {
            return JsfUtil.getSelectItems(fmsCostCenterBeanLocal.findCostCenter(fmsLuSystem), true);
        } else {
            SelectItem[] items = new SelectItem[1];
            return items;
        }
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="totalAmtCalc">

    public void totalAmtCalc() {
        if (!fmsCapitalBudget1List.isEmpty()) {
            double x = 0;
            for (int i = 0; i < fmsCapitalBudget1List.size(); i++) {
                x += fmsCapitalBudget1List.get(i).getTotalReqAmount().doubleValue();
            }
            setTotalRequestedAmount(new BigDecimal(x));
        }
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="gettotalReqAmount">

    public String gettotalReqAmount() {
        if (getTotalRequestedAmount() != null) {
            String number = getTotalRequestedAmount().toString();
            double amount = Double.parseDouble(number);
            DecimalFormat formatter = new DecimalFormat("#,##0.00##");
            return formatter.format(amount);
        } else {
            return null;
        }
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="getTotalGLReqAmount">

    public String getTotalGLReqAmount() {
        if (getTotalGLRequestedAmount() != null) {
            String number = getTotalGLRequestedAmount().toString();
            double amount = Double.parseDouble(number);
            DecimalFormat formatter = new DecimalFormat("#,##0.00##");
            return formatter.format(amount);
        } else {
            return null;
        }
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="totalGLAmtCalc">

    public void totalGLAmtCalc() {
        /* if (!fmsCapitalBudget1.getFmsCapitalBudgetDetailList().isEmpty()) {
         double x = 0;
         for (int i = 0; i < fmsCapitalBudget1.getFmsCapitalBudgetDetailList().size(); i++) {
         x += fmsCapitalBudget1.getFmsCapitalBudgetDetailList().get(i).getRequestedAmount().doubleValue();

         if (!fmsCapitalBudget1.getFmsCapitalBudgetDetailList().get(i).getFmsCapitalBudgetTasksList().isEmpty()) {
         double y = 0;
         for (int j = 0; j < fmsCapitalBudget1.getFmsCapitalBudgetDetailList().get(i).getFmsCapitalBudgetTasksList().size(); j++) {
         y += fmsCapitalBudget1.getFmsCapitalBudgetDetailList().get(i).getFmsCapitalBudgetTasksList().get(j).getAllotedAmount().doubleValue();
         }
         setTotalSLRequestedAmount(new BigDecimal(x));
         fmsCapitalBudgetDetail.setRequestedAmount(new BigDecimal(x));
         }

         }
         setTotalGLRequestedAmount(new BigDecimal(x));
         }*/

        if (!fmsCapitalBudgetDetail.getFmsCapitalBudgetTasksList().isEmpty()) {
            double x = 0;
            for (int i = 0; i < fmsCapitalBudgetDetail.getFmsCapitalBudgetTasksList().size(); i++) {
                x += fmsCapitalBudgetDetail.getFmsCapitalBudgetTasksList().get(i).getAllotedAmount().doubleValue();
            }
            setTotalSLRequestedAmount(new BigDecimal(x));
            fmsCapitalBudgetDetail.setRequestedAmount(new BigDecimal(x));
        }

    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="totalSLAmtCalc">

    public void totalSLAmtCalc() {
        if (!fmsCapitalBudgetDetail.getFmsCapitalBudgetTasksList().isEmpty()) {
            double x = 0;
            for (int i = 0; i < fmsCapitalBudgetDetail.getFmsCapitalBudgetTasksList().size(); i++) {
                x += fmsCapitalBudgetDetail.getFmsCapitalBudgetTasksList().get(i).getAllotedAmount().doubleValue();
            }
            setTotalSLRequestedAmount(new BigDecimal(x));
            fmsCapitalBudgetDetail.setRequestedAmount(new BigDecimal(x));
        }
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="getTotalSLReqAmount">

    public String getTotalSLReqAmount() {
        if (getTotalSLRequestedAmount() != null) {
            String number = getTotalSLRequestedAmount().toString();
            double amount = Double.parseDouble(number);
            DecimalFormat formatter = new DecimalFormat("#,##0.00##");
            return formatter.format(amount);
        } else {
            return null;
        }
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="searchPopulate">

    public void searchPopulate(SelectEvent event) {
        fmsCapitalBudget1 = (FmsCapitalBudget1) event.getObject();
        fmsCapitalBudget1.setCapitalBudgetId(fmsCapitalBudget1.getCapitalBudgetId());
        recreateDataModel();
        recreateWFDataModel();
        decisionChange();
        renderPnlCreateAdditional = false;
        renderPnlManPage = true;
        createOrSearchBundle = "Search";
        buttonRenderd = true;
        fmsLuSystem = fmsCapitalBudget1.getCcSsJunction().getFmsLuSystem();
        sysJobNOList = fmsLuSystem.getFmsSystemJobJunctionList();
        pmsWorkAuthorization.setWorkAuthoId(fmsCapitalBudget1.getJobNo().getWorkAuthoId());
        pmsWorkAuthorization = pmsWorkAuthorizationBeanLocal.findByWorkAutId(pmsWorkAuthorization);
        fmsSystemJobJunction = fmsSystemJobJunctionBeanLocal.findBySSPMSId(fmsLuSystem, pmsWorkAuthorization);
        costCenterList = fmsCostCenterBeanLocal.findMappedCostCenter(fmsLuSystem);
        fmsCostCenter = fmsCostCenterBeanLocal.getCCDetail(fmsCapitalBudget1.getCcSsJunction().getFmsCostCenter());
        fmsCostcSystemJunction = fmsCostCSystemJunctionBeanLocal.findByCCandSS(fmsLuSystem, fmsCostCenter);
        fmsLuBudgetYear = fmsCapitalBudget1.getBudgetYear();
        reqheaderTitle = "Capital Budget Request Registration";
        approveRenderd = false;
        saveOrUpdate = "Edit";
        buttonName = "Update";
        setIcone("ui-icon-search");
        totalGLAmtCalc();
        totalSLAmtCalc();
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="detailPopulater">

    public void detailPopulater(SelectEvent event) {
        System.out.println("this is budget code");
        fmsCapitalBudgetDetail = (FmsCapitalBudgetDetail) event.getObject();
        // fmsSubsid1aryLedger.getSubsidiaryCode();
        fmsBudgetCode = fmsCapitalBudgetDetail.getBudgetCode();
        pmsWorkAuthorization = fmsCapitalBudget1.getJobNo();
        fmsGeneralLedger = fmsCapitalBudgetDetail.getBudgetCode().getGeneralLedger();
        detailPopulate = 1;
        totalSLAmtCalc();
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="CBRequestControllerNew">

    public CBRequestControllerNew() {
        numberConverter.setPattern("#,##0.00##");
        numberConverter.setMinIntegerDigits(1);
        numberConverter.setMaxIntegerDigits(50);
        numberConverter.setMaxFractionDigits(2);
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="addTaskEvent">

    public void addTaskEvent() {
        System.out.println("i evented");
        findListSubL();
        System.out.println("ggg");
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="PostConstruct">

    @PostConstruct
    public void _init() {
        userName = SessionBean.getUserName();
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="sampl">
    //</editor-fold>

}
