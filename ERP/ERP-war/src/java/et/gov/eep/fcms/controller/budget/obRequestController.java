package et.gov.eep.fcms.controller.budget;
//<editor-fold defaultstate="collapsed" desc="import">

import et.gov.eep.commonApplications.entity.WfFcmsProcessed;
import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.fcms.businessLogic.FmsLuBudgetYearBeanLocal;
import et.gov.eep.fcms.businessLogic.admin.FmsAccountingPeriodBeanLocal;
import et.gov.eep.fcms.businessLogic.admin.FmsCostCenterBeanLocal;
import et.gov.eep.fcms.businessLogic.admin.FmsGeneralLedgerBeanLocal;
import et.gov.eep.fcms.businessLogic.admin.FmsLuSystemBeanLocal;
import et.gov.eep.fcms.businessLogic.budget.FMSOBTasksBeanLocal;
import et.gov.eep.fcms.businessLogic.budget.FmsLuBudgetSourceBeanLocal;
import et.gov.eep.fcms.businessLogic.budget.FmsOperatingBudgetBeanLocal;
import et.gov.eep.fcms.businessLogic.budget.FmsOperatingBudgetDetailBeanLocal;
import et.gov.eep.fcms.businessLogic.budgetYearLookUpBeanLocal;
import et.gov.eep.commonApplications.businessLogic.WfFcmsProcessedBeanLocal;
import et.gov.eep.fcms.businessLogic.admin.FmsCostCSystemJunctionBeanLocal;
import et.gov.eep.fcms.businessLogic.admin.subsidiaryBeanLocal;
import et.gov.eep.fcms.entity.FmsLuBudgetYear;
import et.gov.eep.fcms.entity.admin.FmsAccountingPeriod;
import et.gov.eep.fcms.entity.admin.FmsCostCenter;
import et.gov.eep.fcms.entity.admin.FmsCostcSystemJunction;
import et.gov.eep.fcms.entity.admin.FmsGeneralLedger;
import et.gov.eep.fcms.entity.admin.FmsLuSystem;
import et.gov.eep.fcms.entity.admin.FmsSubsidiaryLedger;
import et.gov.eep.fcms.entity.budget.FmsLuBudgetSource;
import et.gov.eep.fcms.entity.budget.FmsOperatingBudget1;
import et.gov.eep.fcms.entity.budget.FmsOperatingBudgetDetail;
import et.gov.eep.fcms.entity.budget.FmsOperatingBudgetTasks;
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
import org.primefaces.event.SelectEvent;
import securityBean.Constants;
import securityBean.SessionBean;
import webService.AAA;
import webService.EventEntry;
import webService.IAdministration;
//</editor-fold>
@Named(value = "obRequestController")
@ViewScoped
public class obRequestController implements Serializable {
    //<editor-fold defaultstate="collapsed" desc="Inject Entities">

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
    FmsSubsidiaryLedger fmsSubsid1aryLedger;
    @Inject
    FmsOperatingBudgetTasks fmsOperatingBudgetTasks;
    @Inject
    FmsLuBudgetSource fmsLuBudgetSource;
    @Inject
    FmsCostcSystemJunction fmsCostcSystemJunction;
    @Inject
    WfFcmsProcessed wfFcmsProcessed;
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Inject EJBs">
    @EJB
    WfFcmsProcessedBeanLocal wfFcmsProcessedBeanLocal;
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
    FMSOBTasksBeanLocal fMSOBTasksBeanLocal;
    @EJB
    FmsLuBudgetSourceBeanLocal fmsLuBudgetSourceBeanLocal;
    @EJB
    FmsCostCSystemJunctionBeanLocal fmsCostCSystemJunctionBeanLocal;
    @EJB
    subsidiaryBeanLocal subLedgerBeanLocal;
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="variable declaration">
    Constants constants;
    FmsAccountingPeriod currPeriod = new FmsAccountingPeriod();
    FmsLuBudgetYear nextPeriod = new FmsLuBudgetYear();
    
    FmsOperatingBudget1 operatingBudgetSelection;
    FmsOperatingBudgetDetail operatingBudgetDetailSelection;
    BigDecimal totalRequestedAmount = new BigDecimal(BigInteger.ZERO);
    BigDecimal totalGLRequestedAmount = new BigDecimal(BigInteger.ZERO);
    BigDecimal totalSLRequestedAmount = new BigDecimal(BigInteger.ZERO);
    int duplicated = 0;
    int detailPopulate = 0;
    String userName = new String();

    private String createOrSearchBundle = "New";
    private boolean renderPnlCreateAdditional = true;
    private boolean renderPnlManPage = false;
    private String icone = "ui-icon-plus";
    private boolean buttonRenderd = false;
    private boolean approveRenderd = false;
    private String reqheaderTitle = "Operating Budget Request Search";
    private String saveOrUpdate = "Create";
    private String buttonName = "Save";
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Declaring Lists and models ">
    List<FmsLuBudgetYear> budgetyrList = new ArrayList<>();
    List<FmsLuBudgetYear> listLuBudgetYearList = new ArrayList<>();
    List<FmsOperatingBudget1> fmsOperatingBudget1List = new ArrayList<>();
    List<FmsOperatingBudgetTasks> fmsOperatingBudgetTasksList = new ArrayList<>();
    List<FmsCostCenter> costCenterList;

    DataModel<WfFcmsProcessed> wfFcmsProcessedDataModel;
    DataModel<FmsOperatingBudgetDetail> fmsOperatingBudgetDetailmodel;
    DataModel<FmsOperatingBudget1> fmsOperatingBudgetDetailSrcmodel;
    DataModel<FmsOperatingBudgetTasks> fmsOperatingBudgetTasksDataModel;
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="getter and setter">
    public List<FmsCostCenter> getCostCenterList() {
        if (costCenterList == null) {
            costCenterList = new ArrayList<>();
        }
        return costCenterList;
    }

    public void setCostCenterList(List<FmsCostCenter> costCenterList) {
        this.costCenterList = costCenterList;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getDuplicated() {
        return duplicated;
    }

    public void setDuplicated(int duplicated) {
        this.duplicated = duplicated;
    }

    public BigDecimal getTotalRequestedAmount() {
        return totalRequestedAmount;
    }

    public void setTotalRequestedAmount(BigDecimal totalRequestedAmount) {
        this.totalRequestedAmount = totalRequestedAmount;
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

    public Constants getConstants() {
        return constants;
    }

    public int getDetailPopulate() {
        return detailPopulate;
    }

    public void setDetailPopulate(int detailPopulate) {
        this.detailPopulate = detailPopulate;
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

    public FmsLuBudgetSource getFmsLuBudgetSource() {
        if (fmsLuBudgetSource == null) {
            fmsLuBudgetSource = new FmsLuBudgetSource();
        }
        return fmsLuBudgetSource;
    }

    public void setFmsLuBudgetSource(FmsLuBudgetSource fmsLuBudgetSource) {
        this.fmsLuBudgetSource = fmsLuBudgetSource;
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

    public FmsSubsidiaryLedger getFmsSubsid1aryLedger() {
        if (fmsSubsid1aryLedger == null) {
            fmsSubsid1aryLedger = new FmsSubsidiaryLedger();
        }
        return fmsSubsid1aryLedger;
    }

    public void setFmsSubsid1aryLedger(FmsSubsidiaryLedger fmsSubsid1aryLedger) {
        this.fmsSubsid1aryLedger = fmsSubsid1aryLedger;
    }

    public FmsOperatingBudgetTasks getFmsOperatingBudgetTasks() {
        if (fmsOperatingBudgetTasks == null) {
            fmsOperatingBudgetTasks = new FmsOperatingBudgetTasks();
        }
        return fmsOperatingBudgetTasks;
    }

    public void setFmsOperatingBudgetTasks(FmsOperatingBudgetTasks fmsOperatingBudgetTasks) {
        this.fmsOperatingBudgetTasks = fmsOperatingBudgetTasks;
    }

    public FMSOBTasksBeanLocal getfMSOBTasksBeanLocal() {
        return fMSOBTasksBeanLocal;
    }

    public void setfMSOBTasksBeanLocal(FMSOBTasksBeanLocal fMSOBTasksBeanLocal) {
        this.fMSOBTasksBeanLocal = fMSOBTasksBeanLocal;
    }

    public FmsOperatingBudget1 getOperatingBudgetSelection() {
        return operatingBudgetSelection;
    }

    public void setOperatingBudgetSelection(FmsOperatingBudget1 operatingBudgetSelection) {
        this.operatingBudgetSelection = operatingBudgetSelection;
    }

    public FmsOperatingBudgetDetail getOperatingBudgetDetailSelection() {
        return operatingBudgetDetailSelection;
    }

    public void setOperatingBudgetDetailSelection(FmsOperatingBudgetDetail operatingBudgetDetailSelection) {
        this.operatingBudgetDetailSelection = operatingBudgetDetailSelection;
    }

    public List<FmsOperatingBudgetTasks> getFmsOperatingBudgetTasksList() {
        return fmsOperatingBudgetTasksList;
    }

    public void setFmsOperatingBudgetTasksList(List<FmsOperatingBudgetTasks> fmsOperatingBudgetTasksList) {
        this.fmsOperatingBudgetTasksList = fmsOperatingBudgetTasksList;
    }

    public DataModel<FmsOperatingBudgetTasks> getFmsOperatingBudgetTasksDataModel() {
        fmsOperatingBudgetTasksDataModel = new ListDataModel(fmsOperatingBudgetDetailmodel.getRowData().getFmsOperatingBudgetTasksList());
        return fmsOperatingBudgetTasksDataModel;
    }

    public void setFmsOperatingBudgetTasksDataModel(DataModel<FmsOperatingBudgetTasks> fmsOperatingBudgetTasksDataModel) {
        this.fmsOperatingBudgetTasksDataModel = fmsOperatingBudgetTasksDataModel;
    }

    public DataModel<FmsOperatingBudgetDetail> getFmsOperatingBudgetDetailmodel() {
        if (fmsOperatingBudgetDetailmodel == null) {
            fmsOperatingBudgetDetailmodel = new ArrayDataModel<>();
        }
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

    public FmsOperatingBudgetDetail getFmsOperatingBudgetDetail() {
        if (fmsOperatingBudgetDetail == null) {
            fmsOperatingBudgetDetail = new FmsOperatingBudgetDetail();
        }
        return fmsOperatingBudgetDetail;
    }

    public void setFmsOperatingBudgetDetail(FmsOperatingBudgetDetail fmsOperatingBudgetDetail) {
        this.fmsOperatingBudgetDetail = fmsOperatingBudgetDetail;
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

    public FmsOperatingBudget1 getFmsOperatingBudget1() {
        if (fmsOperatingBudget1 == null) {
            fmsOperatingBudget1 = new FmsOperatingBudget1();
        }
        return fmsOperatingBudget1;
    }

    public void setFmsOperatingBudget1(FmsOperatingBudget1 fmsOperatingBudget1) {
        this.fmsOperatingBudget1 = fmsOperatingBudget1;
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

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="PostConstruct">
    @PostConstruct
    public void _init() {
        userName = SessionBean.getUserName();
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="create New OB Req View">
    public void createNewOBReqView() {
        if (createOrSearchBundle.equals("New")) {
            renderPnlCreateAdditional = false;
            renderPnlManPage = true;
            createOrSearchBundle = "Search";
            reqheaderTitle = "Operating Budget Request Registration";
            buttonRenderd = true;
            approveRenderd = false;
            setIcone("ui-icon-search");
            reset();
        } else if (createOrSearchBundle.equals("Search")) {
            renderPnlCreateAdditional = true;
            renderPnlManPage = false;
            createOrSearchBundle = "New";
            reqheaderTitle = "Operating Budget Request Search";
            buttonRenderd = false;
            approveRenderd = true;
            setIcone("ui-icon-plus");
            reset();
        }
//        saveUpdateClear();
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="decisionChange">
    public void decisionChange() {
        try {
            if (!fmsOperatingBudget1.getWfFcmsProcessedList().isEmpty()) {
                for (int i = 0; i < fmsOperatingBudget1.getWfFcmsProcessedList().size(); i++) {
                    if (fmsOperatingBudget1.getWfFcmsProcessedList().get(i).getDecision() == 17 ^ fmsOperatingBudget1.getWfFcmsProcessedList().get(i).getDecision() == 18) {
                        fmsOperatingBudget1.getWfFcmsProcessedList().get(i).setChangedDecision("Requested");
                    } else if (fmsOperatingBudget1.getWfFcmsProcessedList().get(i).getDecision() == 0 ^ fmsOperatingBudget1.getWfFcmsProcessedList().get(i).getDecision() == 2) {
                        fmsOperatingBudget1.getWfFcmsProcessedList().get(i).setChangedDecision("Prepared");
                    } else if (fmsOperatingBudget1.getWfFcmsProcessedList().get(i).getDecision() == 1 ^ fmsOperatingBudget1.getWfFcmsProcessedList().get(i).getDecision() == 4) {
                        fmsOperatingBudget1.getWfFcmsProcessedList().get(i).setChangedDecision("Checked");
                    } else if (fmsOperatingBudget1.getWfFcmsProcessedList().get(i).getDecision() == 3 ^ fmsOperatingBudget1.getWfFcmsProcessedList().get(i).getDecision() == 11) {
                        fmsOperatingBudget1.getWfFcmsProcessedList().get(i).setChangedDecision("Approved");
                    } else if (fmsOperatingBudget1.getWfFcmsProcessedList().get(i).getDecision() == 10) {
                        fmsOperatingBudget1.getWfFcmsProcessedList().get(i).setChangedDecision("Authorized");
                    }
                }
            }
        } catch (IndexOutOfBoundsException ex) {
            ex.printStackTrace();
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
    //<editor-fold defaultstate="collapsed" desc="get Lu Budget Year List">
    public SelectItem[] getLuBudgetYearList() {
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
    //<editor-fold defaultstate="collapsed" desc="year Value Change">
    public void yearValueChange(ValueChangeEvent event) {
        if (!event.getNewValue().toString().isEmpty()) {
            fmsLuBudgetYear.setBudgetYear(event.getNewValue().toString());
            fmsLuBudgetYear = budgetYearLookUpBeanLocal.getYearInfo(fmsLuBudgetYear);
            fmsOperatingBudget1.setBudgetYear(fmsLuBudgetYear);
        }
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="findListSubL">
    public SelectItem[] findListSubL() {
        ArrayList<FmsSubsidiaryLedger> subsidaryList = new ArrayList<>();
        if (fmsGeneralLedger != null) {
            SelectItem[] listSl = null;
            System.out.println("A  " + fmsCostcSystemJunction.getFmsCostCenter());
            System.out.println("B  " + fmsGeneralLedger.getGeneralLedgerCode());
            subsidaryList = subLedgerBeanLocal.findSLbyGLandCCSS(fmsGeneralLedger, fmsCostcSystemJunction);
            if (subsidaryList.size() > 0) {
                listSl = new SelectItem[subsidaryList.size() + 1];
                listSl[0] = new SelectItem(null, "--- Select One ---");
                for (int i = 0; i < subsidaryList.size(); i++) {
                    listSl[i + 1] = new SelectItem(subsidaryList.get(i).getSubsidiaryId(), subsidaryList.get(i).getSubsidiaryCode());
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
            fmsSubsid1aryLedger.setSubsidiaryId(Integer.parseInt(event.getNewValue().toString()));
            fmsSubsid1aryLedger = subLedgerBeanLocal.getSubsidiaryInfo(fmsSubsid1aryLedger);
            fmsOperatingBudgetTasks.setSlId(fmsSubsid1aryLedger);
        }
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="GLListener">
    public void GLListener(ValueChangeEvent event) {
        if (!event.getNewValue().toString().isEmpty()) {
            fmsGeneralLedger.setGeneralLedgerCode(event.getNewValue().toString());
        }
        for (int i = 0; i < fmsOperatingBudget1.getFmsOperatingBudgetDetailList().size(); i++) {
            if (fmsOperatingBudget1.getFmsOperatingBudgetDetailList().get(i).getGeneralLedger().getGeneralLedgerCode().equals(fmsGeneralLedger.getGeneralLedgerCode())) {
                fmsOperatingBudgetDetail = new FmsOperatingBudgetDetail();
                break;
            }
        }
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="SystemChange">
    public void SystemChange(ValueChangeEvent event) {
        if (!event.getNewValue().toString().isEmpty()) {
            fmsLuSystem.setSystemCode(event.getNewValue().toString());
            fmsLuSystem = fmsLuSystemBeanLocal.getSysDetail(fmsLuSystem);
            fmsCostCenter.setSystemId(fmsLuSystem);
//            fmsCostcSystemJunctionDataModel = new ListDataModel(new ArrayList(fmsCostCSystemJunctionBeanLocal.fetchMappedSystem(fmsLuSystem)));
            costCenterList = fmsCostCenterBeanLocal.findMappedCostCenter(fmsLuSystem);
        }
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="getCostCenterBySystemLU">
    public SelectItem[] getCostCenterBySystemLU() {

        fmsLuSystem = fmsCostCenter.getSystemId();
        if (fmsLuSystem != null) {
            return JsfUtil.getSelectItems(fmsCostCenterBeanLocal.findAll(), true);
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
    //<editor-fold defaultstate="collapsed" desc="CostCenterChange">

    public void CostCenterChange(ValueChangeEvent event) {
        if (!event.getNewValue().toString().isEmpty()) {
            fmsCostCenter.setSystemName(event.getNewValue().toString());
            fmsCostCenter = fmsCostCenterBeanLocal.getCostDetail(fmsCostCenter);
            fmsCostcSystemJunction = fmsCostCSystemJunctionBeanLocal.findByCCandSS(fmsLuSystem, fmsCostCenter);
            int obReqCount = fmsOperatingBudgetBeanLocal.CCSSJuncDuplicationChecker(fmsLuBudgetYear, fmsCostcSystemJunction);
            if (obReqCount > 0) {
                JsfUtil.addFatalMessage("A request has already been made with cost center " + fmsCostCenter.getSystemName() + " on " + fmsLuBudgetYear.getBudgetYear() + " budget year.");
                reset();
            } else {
                int count = fmsOperatingBudgetBeanLocal.RowCount() + 1;
                String conc = "Opr-" + fmsLuSystem.getSystemCode() + "-" + fmsCostCenter.getSystemName() + "-" + count;
                fmsOperatingBudget1.setRequestCode(conc);
                fmsCostcSystemJunction = fmsCostCSystemJunctionBeanLocal.findByCCandSS(fmsLuSystem, fmsCostCenter);
                fmsOperatingBudget1.setCcSsJunction(fmsCostcSystemJunction);
            }
        }
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="addOBReqDetail1">

    public void addOBReqDetail1() {
        for (int i = 0; i < fmsOperatingBudget1.getFmsOperatingBudgetDetailList().size(); i++) {
            if (fmsOperatingBudget1.getFmsOperatingBudgetDetailList().get(i).getGeneralLedger().equals(fmsOperatingBudgetDetail.getGeneralLedger())) {
                duplicated = 1;
            }
        }
        if (duplicated == 0 || detailPopulate == 1) {
            fmsOperatingBudget1.addOperatingBudgetDetail(fmsOperatingBudgetDetail);
            recreateDataModel();
            detailPopulate = 0;
        } else {
            duplicateMsg();
            duplicated = 0;
        }
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="detailPopulater">

    public void detailPopulater(SelectEvent event) {
        fmsOperatingBudgetDetail = (FmsOperatingBudgetDetail) event.getObject();
        fmsGeneralLedger = fmsOperatingBudgetDetail.getGeneralLedger();
        detailPopulate = 1;
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="addOBTask">

    public void addOBTask() {
        if (fmsSubsid1aryLedger != null && fmsOperatingBudgetTasks.getTaskName() != null && fmsOperatingBudgetTasks.getAllotedAmount() != null) {
            try {
                for (int i = 0; i < fmsOperatingBudgetDetail.getFmsOperatingBudgetTasksList().size(); i++) {
                    allotedTaskAmount += fmsOperatingBudgetDetail.getFmsOperatingBudgetTasksList().get(i).getAllotedAmount().doubleValue();
                }
                fmsOperatingBudgetDetail.addOperatingBudgetTasksList(fmsOperatingBudgetTasks);
                fmsOperatingBudgetTasksDataModel = null;
                fmsOperatingBudgetTasksDataModel = new ListDataModel(new ArrayList(fmsOperatingBudgetDetail.getFmsOperatingBudgetTasksList()));
                totalSLAmtCalc();
                fmsOperatingBudgetTasks = new FmsOperatingBudgetTasks();
                resetOBDetail();
                resetOBTask();
            } catch (NullPointerException ex) {
                throw ex;
            }
        }
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="searchPopulate">

    public void searchPopulate(SelectEvent event) {
        fmsOperatingBudget1 = (FmsOperatingBudget1) event.getObject();
        fmsOperatingBudget1.setOperatingBudgetId(fmsOperatingBudget1.getOperatingBudgetId());
        recreateDataModel();
        recreateWFDataModel();
        decisionChange();

        renderPnlCreateAdditional = false;
        renderPnlManPage = true;
        createOrSearchBundle = "Search";
        buttonRenderd = true;
        fmsLuSystem = fmsLuSystemBeanLocal.getSystembyId(fmsOperatingBudget1.getCcSsJunction().getFmsLuSystem());
        fmsCostCenter = fmsCostCenterBeanLocal.getCCDetail(fmsOperatingBudget1.getCcSsJunction().getFmsCostCenter());
        costCenterList = fmsCostCenterBeanLocal.findMappedCostCenter(fmsLuSystem);
        fmsCostcSystemJunction = fmsCostCSystemJunctionBeanLocal.findByCCandSS(fmsLuSystem, fmsCostCenter);
        fmsLuBudgetYear = fmsOperatingBudget1.getBudgetYear();
        totalSLAmtCalc();
        reqheaderTitle = "Operating Budget Request Registration";
        approveRenderd = false;
        saveOrUpdate = "Edit";
        buttonName = "Update";
        setIcone("ui-icon-search");
        totalGLAmtCalc();
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="getTotalSLReqAmount">

    public String getTotalSLReqAmount() {
        if (getTotalSLRequestedAmount() != null) {
            String number = getTotalSLRequestedAmount().toString();
            amount = Double.parseDouble(number);
            DecimalFormat formatter = new DecimalFormat("#,##0.00##");

            return formatter.format(amount);
        } else {
            return null;
        }
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="saveOBReq">.

    public void saveOBReq() {
        try {
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");

            if (security.checkAccess(SessionBean.getUserName(), "saveOBReq", dataset)) {

                if ("Create".equals(saveOrUpdate) && !fmsOperatingBudget1.getFmsOperatingBudgetDetailList().isEmpty()) {
                    double x = 0;
                    for (int i = 0; i < fmsOperatingBudget1.getFmsOperatingBudgetDetailList().size(); i++) {
                        x += fmsOperatingBudget1.getFmsOperatingBudgetDetailList().get(i).getRequestedAmount().doubleValue();
                    }
                    fmsOperatingBudget1.setTotalReqAmount(new BigDecimal(x));
                    fmsOperatingBudget1.setStatus(Constants.REQUEST_VALUE);
                    fmsOperatingBudget1.setRequestedBy(SessionBean.getUserId());
                    fmsOperatingBudgetBeanLocal.create(fmsOperatingBudget1);

                    wfFcmsProcessed.setOperatingBudgetId(fmsOperatingBudget1);
                    wfFcmsProcessed.setProcessedOn(fmsOperatingBudget1.getRequestDate());
                    wfFcmsProcessed.setCommentGiven(fmsOperatingBudget1.getRemark());
                    wfFcmsProcessed.setProcessedBy(BigInteger.valueOf(SessionBean.getUserId()));
                    wfFcmsProcessed.setDecision(Constants.REQUEST_VALUE);
                    wfFcmsProcessedBeanLocal.create(wfFcmsProcessed);

                    JsfUtil.addSuccessMessage("Operating Budget Request Saved.");
                    reset();
                    x = 0;
                } else if ("Edit".equals(saveOrUpdate) && !fmsOperatingBudget1.getFmsOperatingBudgetDetailList().isEmpty()) {
                    double x = 0;
                    for (int i = 0; i < fmsOperatingBudget1.getFmsOperatingBudgetDetailList().size(); i++) {
                        x += fmsOperatingBudget1.getFmsOperatingBudgetDetailList().get(i).getRequestedAmount().doubleValue();
                    }
                    fmsOperatingBudget1.setTotalReqAmount(new BigDecimal(x));
                    fmsOperatingBudgetBeanLocal.edit(fmsOperatingBudget1);
                    JsfUtil.addSuccessMessage("Operating Budget Request Updated.");
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
        fmsOperatingBudget1 = new FmsOperatingBudget1();
        fmsOperatingBudgetDetail = new FmsOperatingBudgetDetail();
        fmsCostCenter = new FmsCostCenter();
        fmsLuSystem = new FmsLuSystem();
        fmsLuBudgetYear = new FmsLuBudgetYear();
        fmsGeneralLedger = new FmsGeneralLedger();
        fmsOperatingBudgetDetailmodel = new ArrayDataModel<>();
        fmsOperatingBudgetDetailSrcmodel = new ArrayDataModel<>();
        wfFcmsProcessed = new WfFcmsProcessed();
        wfFcmsProcessedDataModel = new ArrayDataModel<>();
        buttonName = "Save";
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
    //<editor-fold defaultstate="collapsed" desc="search OB Req By Criteria">

    public void searchOBReqByCriteria() {
        if (fmsLuSystem.getSystemCode() == null && fmsLuBudgetYear.getBudgetYear() == null && fmsCostCenter.getSystemName() == null && fmsOperatingBudget1.getRequestCode() == null) {
            fmsOperatingBudget1List = fmsOperatingBudgetBeanLocal.findAllRequest(SessionBean.getUserId());
            fmsOperatingBudgetDetailSrcmodel = new ListDataModel(fmsOperatingBudget1List);
            totalAmtCalc();
        } else if (fmsLuBudgetYear.getBudgetYear() != null && fmsLuSystem.getSystemCode() == null && fmsCostCenter.getSystemName() == null && fmsOperatingBudget1.getRequestCode() == null) {
            fmsOperatingBudget1List = fmsOperatingBudgetBeanLocal.findByBudgetYear(fmsLuBudgetYear);
            fmsOperatingBudgetDetailSrcmodel = new ListDataModel(fmsOperatingBudget1List);
            totalAmtCalc();
        } else if (fmsLuBudgetYear.getBudgetYear() != null && fmsLuSystem.getSystemCode() != null && fmsCostCenter.getSystemName() == null && fmsOperatingBudget1.getRequestCode() == null) {
            fmsOperatingBudget1List = fmsOperatingBudgetBeanLocal.findByBudgetYearAndSystem(fmsLuBudgetYear, fmsCostCenter);
            fmsOperatingBudgetDetailSrcmodel = new ListDataModel(fmsOperatingBudget1List);
            totalAmtCalc();
        } else if (fmsLuBudgetYear.getBudgetYear() != null && fmsCostCenter.getSystemName() != null && fmsOperatingBudget1.getRequestCode() == null) {
            fmsOperatingBudget1List = fmsOperatingBudgetBeanLocal.findByBudgetYearAndCostCenter(fmsLuBudgetYear, fmsCostCenter);
            fmsOperatingBudgetDetailSrcmodel = new ListDataModel(fmsOperatingBudget1List);
            totalAmtCalc();
        } else if (fmsLuSystem.getSystemCode() != null || fmsLuBudgetYear.getBudgetYear() == null || fmsCostCenter.getSystemName() != null || fmsOperatingBudget1.getRequestCode() != null) {
            fmsOperatingBudget1List = fmsOperatingBudgetBeanLocal.findBySystem(fmsCostCenter);
            fmsOperatingBudgetDetailSrcmodel = new ListDataModel(fmsOperatingBudget1List);
            totalAmtCalc();
        } else if (fmsLuSystem.getSystemCode() != null || fmsLuBudgetYear.getBudgetYear() != null || fmsCostCenter.getSystemName() != null || fmsOperatingBudget1.getRequestCode() != null) {
            fmsOperatingBudget1List = fmsOperatingBudgetBeanLocal.findBySystemAndCostCenter(fmsCostCenter);
            fmsOperatingBudgetDetailSrcmodel = new ListDataModel(fmsOperatingBudget1List);
        } else if (fmsLuSystem.getSystemCode() != null || fmsLuBudgetYear.getBudgetYear() != null || fmsCostCenter.getSystemName() != null || fmsOperatingBudget1.getRequestCode() != null) {
            fmsOperatingBudget1List = fmsOperatingBudgetBeanLocal.findByRequestCode(fmsOperatingBudget1);
            fmsOperatingBudgetDetailSrcmodel = new ListDataModel(fmsOperatingBudget1List);
            totalAmtCalc();
        }
        if (fmsOperatingBudget1List.isEmpty()) {
            JsfUtil.addFatalMessage("No result found.");
        }
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="get Lists">

    public SelectItem[] getBudgetSourceList() {
        return JsfUtil.getSelectItems(fmsLuBudgetSourceBeanLocal.findAllBudetSource(), true);
    }

    public SelectItem[] getSystemList() {
        return JsfUtil.getSelectItems(fmsLuSystemBeanLocal.findOprSystem(), true);
    }

    public SelectItem[] getGLList() {
        return JsfUtil.getSelectItems(fmsGeneralLedgerBeanLocal.getOprGLAccount(), true);
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="totalSLAmtCalc">

    public void totalSLAmtCalc() {
        if (!fmsOperatingBudgetDetail.getFmsOperatingBudgetTasksList().isEmpty()) {
            double x = 0;
            for (int i = 0; i < fmsOperatingBudgetDetail.getFmsOperatingBudgetTasksList().size(); i++) {
                x += fmsOperatingBudgetDetail.getFmsOperatingBudgetTasksList().get(i).getAllotedAmount().doubleValue();
            }
            setTotalSLRequestedAmount(new BigDecimal(x));
            fmsOperatingBudgetDetail.setRequestedAmount(new BigDecimal(x));
        }
        totalGLAmtCalc();
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="resetOBTask">

    public void resetOBTask() {
        fmsSubsid1aryLedger = new FmsSubsidiaryLedger();
        fmsOperatingBudgetTasks = new FmsOperatingBudgetTasks();
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="resetOBDetail">

    public void resetOBDetail() {
        fmsOperatingBudgetDetailmodel = null;
        fmsOperatingBudgetDetailmodel = new ListDataModel(new ArrayList(fmsOperatingBudget1.getFmsOperatingBudgetDetailList()));
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="removeTask">

    public void removeTask() {
        fmsOperatingBudgetTasks = new FmsOperatingBudgetTasks();
        fmsOperatingBudgetTasks = getFmsOperatingBudgetTasksDataModel().getRowData();
        fMSOBTasksBeanLocal.delete(fmsOperatingBudgetTasks);
        resetOBDetail();
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="recreateDataModel">

    public void recreateDataModel() {
        fmsOperatingBudgetDetailmodel = new ArrayDataModel<>();
        fmsOperatingBudgetDetailmodel = new ListDataModel(new ArrayList(fmsOperatingBudget1.getFmsOperatingBudgetDetailList()));
        fmsOperatingBudgetDetail = new FmsOperatingBudgetDetail();
        fmsLuBudgetSource = new FmsLuBudgetSource();
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
    //<editor-fold defaultstate="collapsed" desc="recreateWFDataModel">

    public void recreateWFDataModel() {
        wfFcmsProcessedDataModel = null;
        wfFcmsProcessedDataModel = new ListDataModel(new ArrayList(fmsOperatingBudget1.getWfFcmsProcessedList()));
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="getSystemSearchList">

    public SelectItem[] getSystemSearchList() {
        return JsfUtil.getSelectItems(fmsLuSystemBeanLocal.findOprSystem(), true);
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="duplicateMsg">

    public void duplicateMsg() {
        JsfUtil.addFatalMessage("Can not duplicate general ledger on your request.");
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
    //<editor-fold defaultstate="collapsed" desc="SystemSearchChange">

    public void SystemSearchChange(ValueChangeEvent event) {
        if (!event.getNewValue().toString().isEmpty()) {
            fmsLuSystem.setSystemCode(event.getNewValue().toString());
            fmsLuSystem = fmsLuSystemBeanLocal.getSysDetail(fmsLuSystem);
            fmsCostCenter.setSystemId(fmsLuSystem);
        }
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
    //<editor-fold defaultstate="collapsed" desc="totalAmtCalc">

    public void totalAmtCalc() {
        if (!fmsOperatingBudget1List.isEmpty()) {
            double x = 0;
            for (int i = 0; i < fmsOperatingBudget1List.size(); i++) {
                x += fmsOperatingBudget1List.get(i).getTotalReqAmount().doubleValue();
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
    //<editor-fold defaultstate="collapsed" desc="totalGLAmtCalc">

    public void totalGLAmtCalc() {
        if (!fmsOperatingBudget1.getFmsOperatingBudgetDetailList().isEmpty()) {
            double x = 0;
            for (int i = 0; i < fmsOperatingBudget1.getFmsOperatingBudgetDetailList().size(); i++) {
                x += fmsOperatingBudget1.getFmsOperatingBudgetDetailList().get(i).getRequestedAmount().doubleValue();
            }
            setTotalGLRequestedAmount(new BigDecimal(x));
        }
    }
    //</editor-fold>
        public obRequestController() {
    }
    private Double allotedTaskAmount = (double) 0;
    double amount;
}
