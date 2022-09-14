package et.gov.eep.fcms.controller.Voucher;
//<editor-fold defaultstate="collapsed" desc="import">

import et.gov.eep.commonApplications.businessLogic.WfFcmsProcessedBeanLocal;
import et.gov.eep.commonApplications.entity.WfFcmsProcessed;
import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.fcms.businessLogic.FmsVatRecieptVoucherBeanLocal;
import et.gov.eep.fcms.businessLogic.FmsWithHoldingVoucherBeanLocal;
import et.gov.eep.fcms.businessLogic.Voucher.FmsVoucherBeanLocal;
import et.gov.eep.fcms.businessLogic.admin.FmsCostCSystemJunctionBeanLocal;
import et.gov.eep.fcms.businessLogic.admin.FmsGeneralLedgerBeanLocal;
import et.gov.eep.fcms.businessLogic.admin.FmsLuSystemBeanLocal;
import et.gov.eep.fcms.businessLogic.admin.FmsVouchersNoRangeBeanLocal;
import et.gov.eep.fcms.businessLogic.admin.subsidiaryBeanLocal;
import et.gov.eep.fcms.entity.FmsAccountUse;
import et.gov.eep.fcms.entity.FmsAccountUseResult;
import et.gov.eep.fcms.entity.FmsDocumentFollowup;
import et.gov.eep.fcms.entity.Vocher.FmsVoucher;
import et.gov.eep.fcms.entity.FmsVatRecieptVoucher;
import et.gov.eep.fcms.entity.admin.FmsCostCenter;
import et.gov.eep.fcms.entity.admin.FmsCostcSystemJunction;
import et.gov.eep.fcms.entity.admin.FmsGeneralLedger;
import et.gov.eep.fcms.entity.admin.FmsLuSystem;
import et.gov.eep.fcms.entity.admin.FmsLuVouchersType;
import et.gov.eep.fcms.entity.admin.FmsSubsidiaryLedger;
import et.gov.eep.fcms.entity.admin.FmsSystemJobJunction;
import et.gov.eep.fcms.entity.admin.FmsVouchersNoRange;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.ArrayDataModel;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import static oracle.jrockit.jfr.events.Bits.longValue;
import org.primefaces.convert.NumberConverter;
import org.primefaces.event.SelectEvent;
import securityBean.Constants;
import securityBean.SessionBean;
import securityBean.WorkFlow;
import webService.AAA;
import webService.EventEntry;
import webService.IAdministration;
//</editor-fold>

@Named(value = "vatVoucherController")
@ViewScoped
public class VatVoucherController implements Serializable {
    //<editor-fold defaultstate="collapsed" desc="Inject">

    @Inject
    WfFcmsProcessed wfFcmsProcessed;
    @Inject
    WorkFlow workFlow;
    @Inject
    FmsDocumentFollowup fmsDocumentFollowup;
    @Inject
    SessionBean sessionBeanLocal;
    @Inject
    FmsVoucher fmsVoucher;
    @Inject
    FmsVoucher fmsVoucher2;
    @Inject
    FmsVatRecieptVoucher vatVoucher;
    @Inject
    FmsAccountUse accountUseEnty;
    @Inject
    FmsLuSystem fmsLuSystem;
    @Inject
    FmsCostCenter fmsCostCenter;
    @Inject
    FmsSubsidiaryLedger fmsSubsidiaryLedger;
    @Inject
    FmsGeneralLedger fmsGeneralLedger;
    @Inject
    private FmsCostcSystemJunction fmsCostcSystemJunction;
    @Inject
    private FmsSystemJobJunction fmsSystemJobJunction;
    @Inject
    FmsVouchersNoRange fmsVouchersNoRange;
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="EJB">
    @EJB
    FmsVatRecieptVoucherBeanLocal fmsVatRecieptVoucherBeanLocal;
    @EJB
    FmsVouchersNoRangeBeanLocal noRangeBeanLocal;
    @EJB
    WfFcmsProcessedBeanLocal wfFcmsProcessedBeanLocal;
    @EJB
    private FmsVoucherBeanLocal fmsVoucherBeanLocal;
    @EJB
    FmsWithHoldingVoucherBeanLocal fmsWithHoldingVoucherBeanLocal;
    @EJB
    FmsCostCSystemJunctionBeanLocal fmsCostCSystemJunctionBeanLocal;
    @EJB
    private FmsGeneralLedgerBeanLocal fmsGeneralLedgerBeanLocal;
    @EJB
    private subsidiaryBeanLocal subsidiaryLedgerBeanLocal;
    @EJB
    FmsLuSystemBeanLocal fmsLuSystemBeanLocal;
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="variable declaration">
    DataModel<FmsAccountUse> accountUseDetailDataModel;
    String processedBy = "";
    String amountInword;
    Boolean decision = false;
    Boolean onCelectCheqNo = true;
    Boolean onCelectSysNo = true;
    List<FmsVoucher> listVoucher = null;
    int currentNo1;
    String fmsVoucherId = null;
    List<FmsVoucher> jvList;
    DataModel<FmsVoucher> fmsVoucherDataModel;
    private BigDecimal ValueDebitCredit = new BigDecimal(0.0);
    private String ActionDebitCredit = "";
    private String createOrSearchBundle = "New";
    int updteStatus = 0;
    private String saveorUpdateBundle = "Create";
    boolean popvocjNo = false;
    private List<Object> accountCredit;
    private HashMap<String, BigDecimal> accountDebit = new HashMap<>();
    private HashMap<String, BigDecimal> accountcredit = new HashMap<>();
    private HashMap<HashMap, HashMap> account = new HashMap<>();
    private DataModel<HashMap> accounthashmapModel;
    List<FmsAccountUseResult> accountUseResults = new ArrayList<>();
    FmsAccountUseResult accountUseResult;
    private String icone = "ui-icon-plus";
    private List<Map.Entry<String, BigDecimal>> entries;
    private List<Map.Entry<String, BigDecimal>> entries_;
    private List<FmsVoucher> selectedJv;
    private List<FmsVoucher> fmsVouchersList;
    private boolean renderPnlCreateJv = false;
    private boolean renderPnlManPage = true;
    private boolean disableBtnCreate;
    boolean saveVochNo = false;
    List<FmsGeneralLedger> glList;
    final Integer projectType = 2;
    final Integer nonProjectType = 1;
    List<FmsSystemJobJunction> sysJobNOList;
    List<FmsCostcSystemJunction> ssCcJunctionList = new ArrayList<>();
    List<FmsLuSystem> systemList;
    private NumberConverter numberConverter = new NumberConverter();
    DataModel<WfFcmsProcessed> workflowDataModel = new ListDataModel<>();
    boolean renderJobNo = false;

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="getter and setters">
    public void setOnCelectSysNo(Boolean onCelectSysNo) {
        this.onCelectSysNo = onCelectSysNo;
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

    public WfFcmsProcessed getWfFcmsProcessed() {
        if (wfFcmsProcessed == null) {
            wfFcmsProcessed = new WfFcmsProcessed();
        }
        return wfFcmsProcessed;
    }

    public void setWfFcmsProcessed(WfFcmsProcessed wfFcmsProcessed) {
        this.wfFcmsProcessed = wfFcmsProcessed;
    }

    public FmsDocumentFollowup getFmsDocumentFollowup() {
        if (fmsDocumentFollowup == null) {
            fmsDocumentFollowup = new FmsDocumentFollowup();

        }
        return fmsDocumentFollowup;
    }

    /**
     *
     * @param fmsDocumentFollowup
     */
    public void setFmsDocumentFollowup(FmsDocumentFollowup fmsDocumentFollowup) {
        this.fmsDocumentFollowup = fmsDocumentFollowup;
    }

    public FmsVatRecieptVoucherBeanLocal getFmsVaReciepttVoucherBeanLocal() {
        return fmsVatRecieptVoucherBeanLocal;
    }

    public void setFmsVatRecieptVoucherBeanLocal(FmsVatRecieptVoucherBeanLocal fmsVatRecieptVoucherBeanLocal) {
        this.fmsVatRecieptVoucherBeanLocal = fmsVatRecieptVoucherBeanLocal;
    }

    public List<FmsVoucher> getListVoucher() {
        return listVoucher;
    }

    public void setListVoucher(List<FmsVoucher> listVoucher) {
        this.listVoucher = listVoucher;
    }

    public boolean isSaveVochNo() {
        return saveVochNo;
    }

    public void setSaveVochNo(boolean saveVochNo) {
        this.saveVochNo = saveVochNo;
    }

    public FmsVatRecieptVoucher getVatVoucher() {
        if (vatVoucher == null) {
            vatVoucher = new FmsVatRecieptVoucher();
        }
        return vatVoucher;
    }

    public void setVatVoucher(FmsVatRecieptVoucher vatVoucher) {
        this.vatVoucher = vatVoucher;
    }

    public DataModel<FmsAccountUse> getAccountUseDetailDataModel() {
        return accountUseDetailDataModel;
    }

    public void setAccountUseDetailDataModel(DataModel<FmsAccountUse> accountUseDetailDataModel) {
        this.accountUseDetailDataModel = accountUseDetailDataModel;
    }

    public DataModel<WfFcmsProcessed> getWorkflowDataModel() {
        if (workflowDataModel == null) {
            workflowDataModel = new ArrayDataModel<>();
        }
        return workflowDataModel;
    }

    public void setWorkflowDataModel(DataModel<WfFcmsProcessed> workflowDataModel) {
        this.workflowDataModel = workflowDataModel;
    }

    /**
     *
     * @return
     */
    public NumberConverter getNumberConverter() {
        return numberConverter;
    }

    /**
     *
     * @param numberConverter
     */
    public void setNumberConverter(NumberConverter numberConverter) {
        this.numberConverter = numberConverter;
    }

    public List<FmsLuSystem> getSystemList() {
        if (systemList == null) {
            systemList = new ArrayList<>();
        }
        systemList = fmsLuSystemBeanLocal.findAll();
        return systemList;
    }

    public void setSystemList(List<FmsLuSystem> systemList) {
        this.systemList = systemList;
    }

    public FmsCostCSystemJunctionBeanLocal getFmsCostCSystemJunctionBeanLocal() {
        return fmsCostCSystemJunctionBeanLocal;
    }

    public void setFmsCostCSystemJunctionBeanLocal(FmsCostCSystemJunctionBeanLocal fmsCostCSystemJunctionBeanLocal) {
        this.fmsCostCSystemJunctionBeanLocal = fmsCostCSystemJunctionBeanLocal;
    }

    public subsidiaryBeanLocal getSubsidiaryLedgerBeanLocal() {
        return subsidiaryLedgerBeanLocal;
    }

    public void setSubsidiaryLedgerBeanLocal(subsidiaryBeanLocal subsidiaryLedgerBeanLocal) {
        this.subsidiaryLedgerBeanLocal = subsidiaryLedgerBeanLocal;
    }

    public FmsVouchersNoRange getFmsVouchersNoRange() {
        if (fmsVouchersNoRange == null) {
            fmsVouchersNoRange = new FmsVouchersNoRange();

        }
        return fmsVouchersNoRange;
    }

    public void setFmsVouchersNoRange(FmsVouchersNoRange fmsVouchersNoRange) {
        this.fmsVouchersNoRange = fmsVouchersNoRange;
    }

    public boolean isPopvocjNo() {
        return popvocjNo;
    }

    public void setPopvocjNo(boolean popvocjNo) {
        this.popvocjNo = popvocjNo;
    }

    public List<FmsGeneralLedger> getGlList() {
        return glList;
    }

    public void setGlList(List<FmsGeneralLedger> glList) {
        this.glList = glList;
    }

    public List<FmsSystemJobJunction> getSysJobNOList() {
        return sysJobNOList;
    }

    public void setSysJobNOList(List<FmsSystemJobJunction> sysJobNOList) {
        this.sysJobNOList = sysJobNOList;
    }

    public List<FmsVoucher> getFmsVouchersList() {
        if (fmsVouchersList == null) {
            fmsVouchersList = new ArrayList<>();
            fmsVouchersList = fmsVoucherBeanLocal.findAllVATS();
        }

        return fmsVouchersList;
    }

    public void setFmsVouchersList(List<FmsVoucher> fmsVouchersList) {
        this.fmsVouchersList = fmsVouchersList;
    }

    public List<FmsCostcSystemJunction> getSsCcJunctionList() {
        return ssCcJunctionList;
    }

    public void setSsCcJunctionList(List<FmsCostcSystemJunction> ssCcJunctionList) {
        this.ssCcJunctionList = ssCcJunctionList;
    }

    public boolean isRenderJobNo() {
        return renderJobNo;
    }

    public void setRenderJobNo(boolean renderJobNo) {
        this.renderJobNo = renderJobNo;
    }

    public int getUpdteStatus() {
        return updteStatus;
    }

    public void setUpdteStatus(int updteStatus) {
        this.updteStatus = updteStatus;
    }

    public String getSaveorUpdateBundle() {
        return saveorUpdateBundle;
    }

    public void setSaveorUpdateBundle(String saveorUpdateBundle) {
        this.saveorUpdateBundle = saveorUpdateBundle;
    }

    public boolean isRenderPnlCreateJv() {
        return renderPnlCreateJv;
    }

    public void setRenderPnlCreateJv(boolean renderPnlCreateJv) {
        this.renderPnlCreateJv = renderPnlCreateJv;
    }

    public boolean isDisableBtnCreate() {
        return disableBtnCreate;
    }

    public void setDisableBtnCreate(boolean disableBtnCreate) {
        this.disableBtnCreate = disableBtnCreate;
    }

    public String getIcone() {
        return icone;
    }

    public void setIcone(String icone) {
        this.icone = icone;
    }

    public List<FmsVoucher> getSelectedJv() {
        return selectedJv;
    }

    public void setSelectedJv(List<FmsVoucher> selectedJv) {
        this.selectedJv = selectedJv;
    }

    public FmsAccountUse getAccountUseEnty() {
        return accountUseEnty;
    }

    public void setAccountUseEnty(FmsAccountUse accountUseEnty) {
        this.accountUseEnty = accountUseEnty;
    }

    public String getFmsVoucherId() {
        return fmsVoucherId;
    }

    public void setFmsVoucherId(String fmsVoucherId) {
        this.fmsVoucherId = fmsVoucherId;
    }

    public List<Object> getAccountCredit() {
        return accountCredit;
    }

    public void setAccountCredit(List<Object> accountCredit) {
        this.accountCredit = accountCredit;
    }

    public HashMap<String, BigDecimal> getAccountDebit() {
        return accountDebit;
    }

    public void setAccountDebit(HashMap<String, BigDecimal> accountDebit) {
        this.accountDebit = accountDebit;
    }

    public HashMap<String, BigDecimal> getAccountcredit() {
        return accountcredit;
    }

    public void setAccountcredit(HashMap<String, BigDecimal> accountcredit) {
        this.accountcredit = accountcredit;
    }

    public HashMap<HashMap, HashMap> getAccount() {
        return account;
    }

    public void setAccount(HashMap<HashMap, HashMap> account) {
        this.account = account;
    }

    public DataModel<HashMap> getAccounthashmapModel() {
        return accounthashmapModel;
    }

    public void setAccounthashmapModel(DataModel<HashMap> accounthashmapModel) {
        this.accounthashmapModel = accounthashmapModel;
    }

    public List<FmsAccountUseResult> getAccountUseResults() {
        return accountUseResults;
    }

    public void setAccountUseResults(List<FmsAccountUseResult> accountUseResults) {
        this.accountUseResults = accountUseResults;
    }

    public FmsAccountUseResult getAccountUseResult() {
        return accountUseResult;
    }

    public void setAccountUseResult(FmsAccountUseResult accountUseResult) {
        this.accountUseResult = accountUseResult;
    }

    public List<Map.Entry<String, BigDecimal>> getEntries() {
        return entries;
    }

    public void setEntries(List<Map.Entry<String, BigDecimal>> entries) {
        this.entries = entries;
    }

    public List<Map.Entry<String, BigDecimal>> getEntries_() {
        return entries_;
    }

    public void setEntries_(List<Map.Entry<String, BigDecimal>> entries_) {
        this.entries_ = entries_;
    }

    public FmsLuSystem getFmsLuSystem() {
        return fmsLuSystem;
    }

    public void setFmsLuSystem(FmsLuSystem fmsLuSystem) {
        this.fmsLuSystem = fmsLuSystem;
    }

    public FmsCostCenter getFmsCostCenter() {
        return fmsCostCenter;
    }

    public void setFmsCostCenter(FmsCostCenter fmsCostCenter) {
        this.fmsCostCenter = fmsCostCenter;
    }

    public FmsSubsidiaryLedger getFmsSubsidiaryLedger() {
        return fmsSubsidiaryLedger;
    }

    public void setFmsSubsidiaryLedger(FmsSubsidiaryLedger fmsSubsidiaryLedger) {
        this.fmsSubsidiaryLedger = fmsSubsidiaryLedger;
    }

    public FmsGeneralLedger getFmsGeneralLedger() {
        return fmsGeneralLedger;
    }

    public void setFmsGeneralLedger(FmsGeneralLedger fmsGeneralLedger) {
        this.fmsGeneralLedger = fmsGeneralLedger;
    }

    public FmsCostcSystemJunction getFmsCostcSystemJunction() {
        return fmsCostcSystemJunction;
    }

    public void setFmsCostcSystemJunction(FmsCostcSystemJunction fmsCostcSystemJunction) {
        this.fmsCostcSystemJunction = fmsCostcSystemJunction;
    }

    public FmsSystemJobJunction getFmsSystemJobJunction() {
        return fmsSystemJobJunction;
    }

    public void setFmsSystemJobJunction(FmsSystemJobJunction fmsSystemJobJunction) {
        this.fmsSystemJobJunction = fmsSystemJobJunction;
    }

    public FmsVoucherBeanLocal getFmsVoucherBeanLocal() {
        return fmsVoucherBeanLocal;
    }

    public void setFmsVoucherBeanLocal(FmsVoucherBeanLocal fmsVoucherBeanLocal) {
        this.fmsVoucherBeanLocal = fmsVoucherBeanLocal;
    }

    public List<FmsVoucher> getJvList() {
        return jvList;
    }

    public void setJvList(List<FmsVoucher> jvList) {
        this.jvList = jvList;
    }

    public DataModel<FmsVoucher> getFmsVoucherDataModel() {
        return fmsVoucherDataModel;
    }

    public void setFmsVoucherDataModel(DataModel<FmsVoucher> fmsVoucherDataModel) {
        this.fmsVoucherDataModel = fmsVoucherDataModel;
    }

    public BigDecimal getValueDebitCredit() {
        return ValueDebitCredit;
    }

    public void setValueDebitCredit(BigDecimal ValueDebitCredit) {
        this.ValueDebitCredit = ValueDebitCredit;
    }

    public String getActionDebitCredit() {
        return ActionDebitCredit;
    }

    public void setActionDebitCredit(String ActionDebitCredit) {
        this.ActionDebitCredit = ActionDebitCredit;
    }

    public String getCreateOrSearchBundle() {
        return createOrSearchBundle;
    }

    public void setCreateOrSearchBundle(String createOrSearchBundle) {
        this.createOrSearchBundle = createOrSearchBundle;
    }

    public DataModel<FmsVoucher> getVoucherDataModel() {
        if (fmsVoucherDataModel == null) {
            fmsVoucherDataModel = new ListDataModel<>();
        }
        return fmsVoucherDataModel;
    }

    public void setVoucherDataModel(DataModel<FmsVoucher> fmsVoucherDataModel) {
        this.fmsVoucherDataModel = fmsVoucherDataModel;
    }

    public FmsVoucher getFmsVoucher() {
        if (fmsVoucher == null) {
            fmsVoucher = new FmsVoucher();
        }
        return fmsVoucher;
    }

    public void setFmsVoucher(FmsVoucher fmsVoucher) {
        this.fmsVoucher = fmsVoucher;
    }

    public FmsVoucher getFmsVoucher2() {
        if (fmsVoucher2 == null) {
            fmsVoucher2 = new FmsVoucher();
        }
        return fmsVoucher2;
    }

    public void setFmsVoucher2(FmsVoucher fmsVoucher2) {
        this.fmsVoucher2 = fmsVoucher2;
    }

    public String getVoucherId() {
        return fmsVoucherId;
    }

    public void setVoucherId(String fmsVoucherId) {
        this.fmsVoucherId = fmsVoucherId;
    }

    public boolean isRenderPnlManPage() {
        return renderPnlManPage;
    }

    public void setRenderPnlManPage(boolean renderPnlManPage) {
        this.renderPnlManPage = renderPnlManPage;
    }

    public String getProcessedBy() {
        return processedBy;
    }

    public void setProcessedBy(String processedBy) {
        this.processedBy = processedBy;
    }

    public Boolean getDecision() {
        return decision;
    }

    public void setDecision(Boolean decision) {
        this.decision = decision;
    }

    public Boolean getOnCelectCheqNo() {
        return onCelectCheqNo;
    }

    public void setOnCelectCheqNo(Boolean onCelectCheqNo) {
        this.onCelectCheqNo = onCelectCheqNo;
    }

    public Boolean getOnCelectSysNo() {
        return onCelectSysNo;
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="clearPage">
    public String clearPage() {
        fmsVoucher = null;
        fmsVoucher2 = null;
        vatVoucher = null;
        fmsLuSystem = null;
        accountUseEnty = null;
        accountUseDetailDataModel = null;
        updteStatus = 0;

        return null;
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="createNewJv">
    public void createNewJv() {
        clearPage();
        saveorUpdateBundle = "Save";
        disableBtnCreate = false;
        if (createOrSearchBundle.equalsIgnoreCase("New")) {
            renderPnlCreateJv = true;
            renderPnlManPage = false;
            createOrSearchBundle = "Search";
            setIcone("ui-icon-search");
            saveVochNo = true;
        } else if (createOrSearchBundle.equalsIgnoreCase("Search")) {
            renderPnlCreateJv = false;
            renderPnlManPage = true;
            createOrSearchBundle = "New";
            setIcone("ui-icon-plus");
            saveVochNo = false;
        }
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="PostConstruct">
    @PostConstruct
    public void init() {

        String refNo = null;
        Integer seqNo = 0;
        fmsVouchersNoRange.setStatus(Constants.CHECK_APPROVE_VALUE);
        FmsLuVouchersType luVouchersType = new FmsLuVouchersType();
        luVouchersType.setId(2);
        fmsVouchersNoRange.setTypeId(luVouchersType);
        fmsVoucher.setPreparedBy(workFlow.getUserName());
//        fmsVouchersNoRange = noRangeBeanLocal.getCurrentVoucherNumber(fmsVouchersNoRange);
        fmsVoucher.setProcessedBy(workFlow.getUserName());
        if (workFlow.isPrepareStatus()) {
            processedBy = "Prepared By";
            decision = true;
        } else if (workFlow.isApproveStatus()) {
            processedBy = "Approved By";
            decision = false;
        } else if (workFlow.isCheckStatus()) {
            processedBy = "check By";
            decision = true;
        }
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="recreateJvDataModel">
    public void recreateJvDataModel() {
        fmsVoucherDataModel = null;
        fmsVoucherDataModel = new ListDataModel(new ArrayList(jvList));
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="searchVat">
    public void searchVat() {
        try {
            //find all
            if (fmsVoucherId.isEmpty()) {
                fmsVoucher.setType("VV");
                jvList = fmsVoucherBeanLocal.findAllbytype(fmsVoucher);
                recreateJvDataModel();
            } else if (!(fmsVoucherId.isEmpty())) {
                //find by begins with
                fmsVoucher.setVoucherId(fmsVoucherId);
                jvList = fmsVoucherBeanLocal.searchVoucheidTypeVV(fmsVoucher);
                recreateJvDataModel();
            } else {
                JsfUtil.addFatalMessage("Sorry!.there is no fmsVoucher begins with " + fmsVoucher.getVoucherId() + ". Try again.");
            }
        } catch (Exception e) {
            JsfUtil.addFatalMessage("Sorry!. No fmsVoucher begins with " + fmsVoucher.getVoucherId() + ". Try again.");
            throw e;
        }
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="populate">
    public void populate(SelectEvent event) {
        fmsVoucher = (FmsVoucher) event.getObject();
        fmsVoucher = fmsVoucherBeanLocal.getVoucherIDInfo(fmsVoucher);
        clearPopup();
        popvocjNo = true;
        recreateModelDetail();
        recreateJVModelDetail();
        updteStatus = 1;
        renderPnlCreateJv = true;
        renderPnlManPage = false;
        saveorUpdateBundle = "Update";
        createOrSearchBundle = "Search";
        setIcone("ui-icon-search");
        disableBtnCreate = true;
        updteStatus = 1;
        onCelectCheqNo = true;
        onCelectSysNo = true;

    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="clearPopup">
    public String clearPopup() {
        accountUseEnty = null;
        fmsLuSystem = null;
        accountUseEnty = new FmsAccountUse();
        ValueDebitCredit = new BigDecimal(0.00);
        ActionDebitCredit = "";
        clearAccCodes();
        fmsLuSystem = new FmsLuSystem();
        return null;
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="clearAccCodes">
    public void clearAccCodes() {
        fmsLuSystem = new FmsLuSystem();
        fmsGeneralLedger = new FmsGeneralLedger();
        fmsCostCenter = new FmsCostCenter();
        fmsSystemJobJunction = new FmsSystemJobJunction();
        fmsCostcSystemJunction = new FmsCostcSystemJunction();
        fmsSubsidiaryLedger = new FmsSubsidiaryLedger();
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="recreateModelDetail">

    public void recreateModelDetail() {
        accountUseDetailDataModel = null;
        accountUseDetailDataModel = new ListDataModel(fmsVoucher.getFmsAccountUseList());
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="recreateJVModelDetail">

    public void recreateJVModelDetail() {
        vatVoucher = fmsVoucher.getFmsVatRecieptVoucher();
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="search Project Type">

    public void searchProjectType() {
        glList = fmsGeneralLedgerBeanLocal.getByAccountType(projectType);
    }

    public void searchNonProjectType() {
        glList = fmsGeneralLedgerBeanLocal.getByAccountType(nonProjectType);
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="AccountUse,GeneralLedger,Subsidiary - Implimentation">
    public void onChangeCheqID(ValueChangeEvent event) {
        System.out.println("change event");
        try {

            System.out.println("oooooooo");
            if (event.getNewValue() != null) {
                fmsVoucher = (FmsVoucher) event.getNewValue();
                vatVoucher.setReferenceNumber(fmsVoucher.getFmsChequePaymentVoucher().getReferenceNumber());
                vatVoucher.setAmountInFigure(longValue(fmsVoucher.getFmsChequePaymentVoucher().getVats()));
                vatVoucher.setTaxPayer(fmsVoucher.getFmsChequePaymentVoucher().getPaidTo());
            }

        } catch (Exception e) {
            JsfUtil.addFatalMessage("Unable to process. Try again reloading the page.");
        }
    }

    public void onChangeSystem(ValueChangeEvent event) {
        try {
            if (event.getNewValue() != null) {
                fmsLuSystem = (FmsLuSystem) event.getNewValue();
                renderJobNo = false;
                if (fmsLuSystem.getType().toString().equals(projectType)) {
                    searchProjectType();
                    renderJobNo = true;
                    sysJobNOList = fmsLuSystem.getFmsSystemJobJunctionList();
                } else {
                    searchNonProjectType();
                }
                ssCcJunctionList = fmsLuSystem.getFmsCostcSystemJunctionList();

                fmsVouchersNoRange = fmsVoucherBeanLocal.getVoucherNo(fmsLuSystem);
                vatVoucher.setSystemno(fmsLuSystem.toString());

            }
        } catch (Exception e) {
            e.printStackTrace();
            JsfUtil.addFatalMessage("Unable to process. Try again reloading the page.");
        }
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="saveVV">

    public String saveVV() {
        AAA securityService = new AAA();
        IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
        String systemBundle = "et/gov/eep/commonApplications/securityServer";
        String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
        if (security.checkAccess(sessionBeanLocal.getUserName(), "saveVV", dataset)) {
//                 put ur code here...! 
            double amt = vatVoucher.getAmountInFigure().doubleValue();
            if (updteStatus == 0) {
                String fmsVoucherID, preparedate;
                DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
                preparedate = dateFormat.format(vatVoucher.getPreparedDate());
                fmsVoucherID = preparedate + "-" + Integer.parseInt(fmsVouchersNoRange.getCurrentNo()) + "-" + "VV";
                fmsVoucher2.setVoucherId(fmsVoucherID);
                if (fmsVoucherBeanLocal.getfmsVoucherDup(fmsVoucher2)) {
                    System.err.println("Value is Duplicated");
                    JsfUtil.addSuccessMessage("Value is Duplicated");
                } else {
                    try {
                        fmsVoucher2.setVoucherId(fmsVoucherID);
                        vatVoucher.setPreparedBy(String.valueOf(sessionBeanLocal.getUserId()));
                        vatVoucher.setStatus("0");
                        fmsVoucher2.setProcessedBy(String.valueOf(sessionBeanLocal.getUserId()));
                        fmsVoucher2.setStatus(Constants.PREPARE_VALUE);
                        fmsDocumentFollowup.setDocumentReference("23526");
                        fmsDocumentFollowup.setType("VV");
                        fmsVoucher2.setType("VV");
                        fmsVoucher2.setVoucherNo(fmsVouchersNoRange.getCurrentNo());
                        currentNo1 = Integer.parseInt(fmsVouchersNoRange.getCurrentNo()) + 1;
                        fmsVouchersNoRange.setCurrentNo(String.valueOf(currentNo1));
                        noRangeBeanLocal.edit(fmsVouchersNoRange);
                        fmsVoucher2.addVatDetail(vatVoucher);
                        fmsVoucherBeanLocal.create(fmsVoucher2);
                        JsfUtil.addSuccessMessage("Withholding Voucher Data Created");
                        return clearPage();
                    } catch (Exception e) {
                        JsfUtil.addFatalMessage("Something Occured on Data Created");
                        return null;
                    }
                }
            } else if (updteStatus == 1) {
                fmsVoucherBeanLocal.edit(fmsVoucher);
                JsfUtil.addSuccessMessage("Withholding Voucher Data is Updated");
                return clearPage();
            } else if (updteStatus == 3) {
                if (listVoucher.size() > 0) {
                    try {
                        for (int i = 0; i < listVoucher.size(); i++) {
                            fmsVoucher.setStatus(Constants.APPROVE_VALUE);
                            FmsVoucher fmsVoucher = new FmsVoucher();
                            fmsVoucher = listVoucher.get(i);
                            fmsVoucherBeanLocal.edit(listVoucher.get(i));
                            fmsVoucherBeanLocal.edit(fmsVoucher);
                            JsfUtil.addSuccessMessage("Withholding Voucher Data is posting Done");
                        }
                        return clearPage();
                    } catch (Exception e) {
                        JsfUtil.addFatalMessage("Something Occured on Data Posting");
                        return null;
                    }
                } else {
                    JsfUtil.addFatalMessage("Select one to Posting");
                    return null;
                }
            }
            return null;
        } else {
            JsfUtil.addFatalMessage("You don't have appropriate permission, please contact the system administrator.");
            EventEntry eventEntry = new EventEntry();
            eventEntry.setSessionId(sessionBeanLocal.getSessionID());
            eventEntry.setUserId(sessionBeanLocal.getUserId());
            QName qualifiedName = new QName("", "project");
            JAXBElement<String> test = new JAXBElement<String>(qualifiedName, String.class, null, sessionBeanLocal.getUserName());
            eventEntry.setUserLogin(test);
//..... add more information by calling fields defined in the object 
            security.addEventLog(eventEntry, dataset);
        }
        return null;
    }

    //</editor-fold>
    public VatVoucherController() {
    }
}
