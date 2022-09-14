/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.controller.pettycash;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.ejb.EJB;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import org.primefaces.convert.NumberConverter;
import org.primefaces.event.SelectEvent;
import securityBean.SessionBean;
import webService.AAA;
import webService.EventEntry;
import webService.IAdministration;
import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.fcms.businessLogic.pettyCash.FmsCasherAccountBeanLocal;
import et.gov.eep.fcms.businessLogic.Voucher.FmsVoucherBeanLocal;
import et.gov.eep.fcms.businessLogic.admin.FmsCostCSystemJunctionBeanLocal;
import et.gov.eep.fcms.businessLogic.admin.FmsGeneralLedgerBeanLocal;
import et.gov.eep.fcms.businessLogic.admin.FmsLuSystemBeanLocal;
import et.gov.eep.fcms.businessLogic.admin.subsidiaryBeanLocal;
import et.gov.eep.fcms.controller.Constants;
import et.gov.eep.fcms.entity.FmsAccountUse;
import et.gov.eep.fcms.entity.pettyCash.FmsCasherAccount;
import et.gov.eep.fcms.entity.FmsDocumentFollowup;
import et.gov.eep.fcms.entity.FmsPettyCashPaymentVoucher;
import et.gov.eep.fcms.entity.Vocher.FmsVoucher;
import et.gov.eep.fcms.entity.admin.FmsCostCenter;
import et.gov.eep.fcms.entity.admin.FmsCostcSystemJunction;
import et.gov.eep.fcms.entity.admin.FmsGeneralLedger;
import et.gov.eep.fcms.entity.admin.FmsLuSystem;
import et.gov.eep.fcms.entity.admin.FmsSubsidiaryLedger;
import et.gov.eep.fcms.entity.admin.FmsSystemJobJunction;
import et.gov.eep.hrms.businessLogic.employee.HrEmployeeBeanLocal;
import et.gov.eep.hrms.entity.employee.HrEmployees;

/**
 *
 * @author Mubarek jebel
 */
@Named(value = "pettyCashEstablishmentManagedBean")

@ViewScoped
public class PettyCashEstablishmentManagedBean implements Serializable {

    //<editor-fold defaultstate="collapsed" desc="Object and variable declaration">
    //Inject entities
    @Inject
    SessionBean SessionBean;
    @Inject
    private FmsPettyCashPaymentVoucher pettyCashPaymentEnty;
    @Inject
    FmsDocumentFollowup fmsDocumentFollowup;
    @Inject
    FmsAccountUse accountUseEnty;
    @Inject
    FmsCasherAccount casherAccountEnty;
    @Inject
    private HrEmployees hrEmployees;
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
    //Inject @EJB
    @EJB
    private FmsVoucherBeanLocal fmsVoucherBeanLocal;
    @EJB
    FmsCasherAccountBeanLocal casherAccountBeanLocal;
    @EJB
    private HrEmployeeBeanLocal hrEmployeeBean;
    @EJB
    FmsLuSystemBeanLocal fmsLuSystemBeanLocal;
    @EJB
    FmsCostCSystemJunctionBeanLocal fmsCostCSystemJunctionBeanLocal;
    @EJB
    private FmsGeneralLedgerBeanLocal fmsGeneralLedgerBeanLocal;
    @EJB
    private subsidiaryBeanLocal subsidiaryLedgerBeanLocal;

    final Integer projectType = 2;
    final Integer nonProjectType = 1;
    int dataTableUpdateStatus = 0;
    private NumberConverter numberConverter = new NumberConverter();
    Set<String> checkCashierDup = new HashSet<>();
    Set<String> checkSubsidiaryCode = new HashSet<>();
    DataModel<FmsAccountUse> accountUseDetailDataModel;
    DataModel<FmsCasherAccount> casherAccountDetailDataModel;
    DataModel<FmsPettyCashPaymentVoucher> pettyCashPaymentDataModel;
    private List<FmsCasherAccount> CasherAccountsList;
    List<FmsGeneralLedger> listGeneralLedger = null;
    List<FmsCasherAccount> casherAccountList;
    List<FmsSubsidiaryLedger> listSubsidiaryLedger = null;
    List<FmsVoucher> listVoucher = null;
    List<FmsCasherAccount> listCasherAccount = null;
    List<FmsCostcSystemJunction> ssCcJunctionList = new ArrayList<>();
    List<FmsSystemJobJunction> sysJobNOList;
    List<FmsGeneralLedger> glList;
    List<FmsSubsidiaryLedger> slList;
    List<FmsLuSystem> systemList;
    FmsVoucher fmsVoucher;
    FmsCasherAccount selectCasher;
    private Boolean update = false;
    int updteStatus = 0;
    int addUpdateStatus = 0;
    int GeneralLedgerListSize = 0;
    int interCatListSizes = 0;
    int IntermidiateCatStatus = 0;
    int catagorynameStatus = 0;
    int MajorCatagorylistId = 0;
    private boolean disableBtnCreate;
    private boolean renderPnlCreatePettyCash = false;
    private boolean renderPnlManPage = true;
    private double ValueDebitCredit = 0.0;
    boolean btnaddvisibility = true;
    boolean btnSave2Visibility = false;
    boolean renderJobNo = false;

    String currentDateofClosed = "";
    String id;
    String CostCenter = "CC";
    String SubsideryLedger = "SL";
    String CostCenter_display = "CostCenter";
    String SubsideryLedger_display = "SubsideryLedger";
    String optionRendered = "false";
    private String Concatination = "";
    private String display_conn;
    private String Enddate = "";
    private String startingdate = "";
    private String amountInword = "";
    private String amountInFigure = "";
    private String ActionDebitCredit = "";
    private String createOrSearchBundle = "New";
    private String icone = "ui-icon-plus";
    private String saveorUpdateBundle = Constants.getComponentBundle("Create");

    //</editor-fold>
    public PettyCashEstablishmentManagedBean() {
        numberConverter.setPattern("#,##0.00##");
        numberConverter.setMinIntegerDigits(1);
        numberConverter.setMaxIntegerDigits(40);
        numberConverter.setMaxFractionDigits(3);

    }
//<editor-fold defaultstate="collapsed" desc="Getter and Setter Methods">

    /**
     * @param CasherAccountsList the CasherAccountsList to set
     */
    public void setCasherAccountsList(List<FmsCasherAccount> CasherAccountsList) {
        this.CasherAccountsList = CasherAccountsList;
    }

    public FmsLuSystem getFmsLuSystem() {
        if (fmsLuSystem == null) {
            fmsLuSystem = new FmsLuSystem();
        }
        return fmsLuSystem;
    }

    public void setFmsLuSystem(FmsLuSystem fmsLuSystem) {
        this.fmsLuSystem = fmsLuSystem;
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

    public FmsCostcSystemJunction getFmsCostcSystemJunction() {
        if (fmsCostcSystemJunction == null) {
            fmsCostcSystemJunction = new FmsCostcSystemJunction();
        }
        return fmsCostcSystemJunction;
    }

    public void setFmsCostcSystemJunction(FmsCostcSystemJunction fmsCostcSystemJunction) {
        this.fmsCostcSystemJunction = fmsCostcSystemJunction;
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

    public FmsGeneralLedger getFmsGeneralLedger() {
        if (fmsGeneralLedger == null) {
            fmsGeneralLedger = new FmsGeneralLedger();
        }
        return fmsGeneralLedger;
    }

    public void setFmsGeneralLedger(FmsGeneralLedger fmsGeneralLedger) {
        this.fmsGeneralLedger = fmsGeneralLedger;
    }

    public FmsSubsidiaryLedger getFmsSubsidiaryLedger() {
        if (fmsSubsidiaryLedger == null) {
            fmsSubsidiaryLedger = new FmsSubsidiaryLedger();
        }
        return fmsSubsidiaryLedger;
    }

    public void setFmsSubsidiaryLedger(FmsSubsidiaryLedger fmsSubsidiaryLedger) {
        this.fmsSubsidiaryLedger = fmsSubsidiaryLedger;
    }

    public List<FmsCostcSystemJunction> getSsCcJunctionList() {
        if (ssCcJunctionList == null) {
            ssCcJunctionList = new ArrayList<>();
        }
        return ssCcJunctionList;
    }

    public void setSsCcJunctionList(List<FmsCostcSystemJunction> ssCcJunctionList) {
        this.ssCcJunctionList = ssCcJunctionList;
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

    public List<FmsGeneralLedger> getGlList() {
        if (glList == null) {
            glList = new ArrayList<>();
        }
        return glList;
    }

    public void setGlList(List<FmsGeneralLedger> glList) {
        this.glList = glList;
    }

    public List<FmsSubsidiaryLedger> getSlList() {
        if (slList == null) {
            slList = new ArrayList<>();
        }
        return slList;
    }

    public void setSlList(List<FmsSubsidiaryLedger> slList) {
        this.slList = slList;
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

    public String getDisplay_conn() {
        return display_conn;
    }

    public void setDisplay_conn(String display_conn) {
        this.display_conn = display_conn;
    }

    public boolean isRenderJobNo() {
        return renderJobNo;
    }

    public void setRenderJobNo(boolean renderJobNo) {
        this.renderJobNo = renderJobNo;
    }

    public boolean isDisableBtnCreate() {
        return disableBtnCreate;
    }

    public boolean isBtnaddvisibility() {
        return btnaddvisibility;
    }

    public void setBtnaddvisibility(boolean btnaddvisibility) {
        this.btnaddvisibility = btnaddvisibility;
    }

    public boolean isBtnSave2Visibility() {
        return btnSave2Visibility;
    }

    public void setBtnSave2Visibility(boolean btnSave2Visibility) {
        this.btnSave2Visibility = btnSave2Visibility;
    }

    public void setDisableBtnCreate(boolean disableBtnCreate) {
        this.disableBtnCreate = disableBtnCreate;
    }

    public String getCreateOrSearchBundle() {
        return createOrSearchBundle;
    }

    public void setCreateOrSearchBundle(String createOrSearchBundle) {
        this.createOrSearchBundle = createOrSearchBundle;
    }

    public boolean isRenderPnlCreatePettyCash() {
        return renderPnlCreatePettyCash;
    }

    public void setRenderPnlCreatePettyCash(boolean renderPnlCreatePettyCash) {
        this.renderPnlCreatePettyCash = renderPnlCreatePettyCash;
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

    public FmsCasherAccount getSelectCasher() {
        return selectCasher;
    }

    public void setSelectCasher(FmsCasherAccount selectCasher) {
        this.selectCasher = selectCasher;
    }

    public NumberConverter getNumberConverter() {
        return numberConverter;
    }

    public void setNumberConverter(NumberConverter numberConverter) {
        this.numberConverter = numberConverter;
    }

    public DataModel<FmsCasherAccount> getCasherAccountDetailDataModel() {

        if (casherAccountDetailDataModel == null) {
            casherAccountDetailDataModel = new ListDataModel<>();
        }
        return casherAccountDetailDataModel;
    }

    public FmsCasherAccount getCasherAccountEnty() {
        if (casherAccountEnty == null) {
            casherAccountEnty = new FmsCasherAccount();
        }
        return casherAccountEnty;
    }

    public void setCasherAccountEnty(FmsCasherAccount casherAccountEnty) {
        this.casherAccountEnty = casherAccountEnty;
    }

    public void setCasherAccountDetailDataModel(DataModel<FmsCasherAccount> casherAccountDetailDataModel) {
        this.casherAccountDetailDataModel = casherAccountDetailDataModel;
    }

    public HrEmployees getHrEmployees() {
        if (hrEmployees == null) {
            hrEmployees = new HrEmployees();
        }
        return hrEmployees;
    }

    public void setHrEmployees(HrEmployees hrEmployees) {
        this.hrEmployees = hrEmployees;
    }

    public FmsPettyCashPaymentVoucher getPettyCashPaymentEnty() {
        return pettyCashPaymentEnty;
    }

    public void setPettyCashPaymentEnty(FmsPettyCashPaymentVoucher pettyCashPaymentEnty) {
        this.pettyCashPaymentEnty = pettyCashPaymentEnty;
    }

    public String getOptionRendered() {
        return optionRendered;
    }

    public void setOptionRendered(String optionRendered) {
        this.optionRendered = optionRendered;
    }

    public double getValueDebitCredit() {
        return ValueDebitCredit;
    }

    public void setValueDebitCredit(double ValueDebitCredit) {
        this.ValueDebitCredit = ValueDebitCredit;
    }

    public String getActionDebitCredit() {
        return ActionDebitCredit;
    }

    public void setActionDebitCredit(String ActionDebitCredit) {
        this.ActionDebitCredit = ActionDebitCredit;
    }

    public DataModel<FmsAccountUse> getAccountUseDetailDataModel() {
        if (accountUseDetailDataModel == null) {
            accountUseDetailDataModel = new ListDataModel<>();
        }
        return accountUseDetailDataModel;
    }

    public void setAccountUseDetailDataModel(DataModel<FmsAccountUse> accountUseDetailDataModel) {
        this.accountUseDetailDataModel = accountUseDetailDataModel;
    }

    public String getAmountInword() {
        return amountInword;
    }

    public void setAmountInword(String amountInword) {
        this.amountInword = amountInword;
    }

    public String getAmountInFigure() {
        return amountInFigure;
    }

    public void setAmountInFigure(String amountInFigure) {
        this.amountInFigure = amountInFigure;
    }
// enty and buss-bean and local

    public FmsAccountUse getAccountUseEnty() {
        if (accountUseEnty == null) {
            accountUseEnty = new FmsAccountUse();
        }
        return accountUseEnty;
    }

    public void setAccountUseEnty(FmsAccountUse accountUseEnty) {
        this.accountUseEnty = accountUseEnty;
    }

    public FmsVoucherBeanLocal getFmsVoucherBeanLocal() {
        return fmsVoucherBeanLocal;
    }

    public void setFmsVoucherBeanLocal(FmsVoucherBeanLocal fmsVoucherBeanLocal) {
        this.fmsVoucherBeanLocal = fmsVoucherBeanLocal;
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

    public String getSaveorUpdateBundle() {
        return saveorUpdateBundle;
    }

    public void setSaveorUpdateBundle(String saveorUpdateBundle) {
        this.saveorUpdateBundle = saveorUpdateBundle;
    }

    public String getEnddate() {
        return Enddate;
    }

    public void setEnddate(String Enddate) {
        this.Enddate = Enddate;
    }

    public String getStartingdate() {
        return startingdate;
    }

    public void setStartingdate(String startingdate) {
        this.startingdate = startingdate;
    }

    public String getCurrentDateofClosed() {
        return currentDateofClosed;
    }

    public void setCurrentDateofClosed(String currentDateofClosed) {
        this.currentDateofClosed = currentDateofClosed;
    }

    public Set<String> getCheckCashierDup() {
        return checkCashierDup;
    }

    public void setCheckCashierDup(Set<String> checkCashierDup) {
        this.checkCashierDup = checkCashierDup;
    }

    public Set<String> getCheckSubsidiaryCode() {
        return checkSubsidiaryCode;
    }

    public void setCheckSubsidiaryCode(Set<String> checkSubsidiaryCode) {
        this.checkSubsidiaryCode = checkSubsidiaryCode;
    }

    public List<FmsCasherAccount> getCasherAccountsList() {
        if (CasherAccountsList == null) {
            CasherAccountsList = new ArrayList<>();
        }
        return CasherAccountsList;
    }

    public void getEmpId(SelectEvent event) {
        hrEmployees = (HrEmployees) event.getObject();
    }

    //</editor-fold> 
    // <editor-fold defaultstate="collapsed" desc="save - Update">
    public String savePettyCashEstablishment() {
        AAA securityService = new AAA();
        IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
        String systemBundle = "et/gov/eep/commonApplications/securityServer";
        String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
        if (security.checkAccess(SessionBean.getUserName(), "savePettyCashEstablishment", dataset)) {
            if (updteStatus == 0) {
                int sizeChareAccount = getCasherAccountsList().size();
                if ((getCasherAccountDetailDataModel().getRowCount() <= 0)) {
                    JsfUtil.addFatalMessage("Data Table Shoud be filled");
                } else {
                    try {
                        for (int i = 0; i < sizeChareAccount; i++) {
                            casherAccountBeanLocal.create(getCasherAccountsList().get(i));
                        }
                        JsfUtil.addSuccessMessage("Saved Saccessfully!");
                        return clearPage();

                    } catch (Exception e) {
                        JsfUtil.addFatalMessage("Failed to process! Please try agian reloading the page");
                        return null;
                    }

                }
            } else if (updteStatus == 1) {//Account code to save
                casherAccountEnty.setSubsidiaryId(fmsSubsidiaryLedger);//Create a relation with SubsideryLedger Entity and set SubsideryLedger ID here
                casherAccountEnty.setAccountCode(fmsSubsidiaryLedger.getSubsidiaryCode());//Account code to save
                casherAccountEnty.setEmpCode(hrEmployees);
                casherAccountBeanLocal.edit(casherAccountEnty);
                JsfUtil.addSuccessMessage("Updated Successfully");
                return clearPage();
            }
        } else {
            JsfUtil.addFatalMessage("You don't have appropriate permission, please contact the system administrator. ");
            EventEntry eventEntry = new EventEntry();
            eventEntry.setSessionId(SessionBean.getSessionID());
            eventEntry.setUserId(SessionBean.getUserId());
            QName qualifiedName = new QName("", "project");
            JAXBElement<String> test = new JAXBElement<String>(qualifiedName, String.class, null, SessionBean.getUserName());
            eventEntry.setUserLogin(test);
            security.addEventLog(eventEntry, dataset);

        }
        return null;
    }

    // <editor-fold defaultstate="collapsed" desc="Search">
    public List<FmsCasherAccount> CasherAccountSearchlist(String casherName) {
        listCasherAccount = null;
        listCasherAccount = casherAccountBeanLocal.searchCasherName(casherAccountEnty);
        return listCasherAccount;
    }

    //update status
    public void updateAccountUseDetail() {
        dataTableUpdateStatus = 1;

    }

    //casheir account info select event
    public void getCasherAccountInformation(SelectEvent event) {
        getCasherAccountsList().clear();
        String selectedCasherAccount = event.getObject().toString();
        if (selectedCasherAccount.equalsIgnoreCase("All Data")) {
            setCasherAccountsList(casherAccountBeanLocal.getCasherAccountAllInfo());
            recreateModelDetail();
        } else {
            casherAccountEnty = casherAccountBeanLocal.getCasherAccountInfo(casherAccountEnty);
            getCasherAccountsList().add(casherAccountEnty);
            recreateModelDetail();
        }

        updteStatus = 1;
        saveorUpdateBundle = "Update";

    }

    //add account code detail
    public String addAccountCodeDetail() {
        if (hrEmployees.getId() == null) {
            JsfUtil.addFatalMessage("Cashier Name: Value is required");
            return null;
        }
        boolean isDupplicated = casherAccountBeanLocal.findDup(hrEmployees);
        if (addUpdateStatus == 0 && isDupplicated) {
            JsfUtil.addFatalMessage("Employee " + hrEmployees.getFirstName() + " is already registered");
        } else {
            try {//Account code to display
                casherAccountEnty.setSubsidiaryId(fmsSubsidiaryLedger);//Create a relation with SubsideryLedger Entity and set SubsideryLedger ID here
                casherAccountEnty.setAccountCode(fmsSubsidiaryLedger.getSubsidiaryCode());//Account code to save
                casherAccountEnty.setEmpCode(hrEmployees);
                if (checkSubsidiaryCode.contains(casherAccountEnty.getAccountCode()) || checkCashierDup.contains(hrEmployees.getId().toString())) {
                    JsfUtil.addFatalMessage("Employee name or Account Code is duplicated");
                } else {
                    getCasherAccountsList().add(casherAccountEnty);
                    checkSubsidiaryCode.add(casherAccountEnty.getAccountCode());
                    checkCashierDup.add(hrEmployees.getId().toString());
                    recreateModelDetail();
                    clearPopup();
                }
            } catch (Exception e) {
            }
        }
        return null;
    }

    //select event for cashier row
    public void selectCasherRow(SelectEvent event) {
        btnaddvisibility = false;
        btnSave2Visibility = true;
        casherAccountEnty = (FmsCasherAccount) event.getObject();
        hrEmployees = casherAccountEnty.getEmpCode();
        String cashierAccountCode[] = casherAccountEnty.getAccountCode().split("-");
        String ss = cashierAccountCode[0];
        String cc = cashierAccountCode[2];
        String gl = cashierAccountCode[3];
        fmsLuSystem.setSystemCode(ss);
        fmsCostCenter.setSystemName(cc);
        fmsGeneralLedger.setGeneralLedgerCode(gl);
        fmsLuSystem = fmsLuSystemBeanLocal.findBySytemCode2(fmsLuSystem);
        ssCcJunctionList = fmsLuSystem.getFmsCostcSystemJunctionList();
        if (fmsLuSystem.getType().equals(projectType.toString())) {
            searchProjectType();
            renderJobNo = true;
            sysJobNOList = fmsLuSystem.getFmsSystemJobJunctionList();
        } else {
            searchNonProjectType();
        }
        fmsCostcSystemJunction = fmsCostCSystemJunctionBeanLocal.findBySystemCodeAndCostCenterCode(fmsLuSystem, fmsCostCenter);
        fmsCostCenter = fmsCostcSystemJunction.getFmsCostCenter();
        fmsGeneralLedger = fmsGeneralLedgerBeanLocal.getGlCode(fmsGeneralLedger);
        fmsSubsidiaryLedger = casherAccountEnty.getSubsidiaryId();
        slList = new ArrayList<>();
        slList.add(fmsSubsidiaryLedger);
        renderPnlCreatePettyCash = true;
        renderPnlManPage = false;
        createOrSearchBundle = "Search";
        setIcone("ui-icon-search");
        disableBtnCreate = true;
        updteStatus = 1;
        saveorUpdateBundle = "Update";
    }

    //populate
    public void populateAccDetail(SelectEvent event) {
        try {
            addUpdateStatus = 1;
            casherAccountEnty = (FmsCasherAccount) event.getObject();
            Concatination = casherAccountEnty.getAccountCode();//Split AccountCode and assign t0 each lebel
            hrEmployees = casherAccountEnty.getEmpCode();
            String cashierAccountCode[] = casherAccountEnty.getAccountCode().split("-");
            String systemCenter = cashierAccountCode[0];
            String costCenter = cashierAccountCode[1];
            String generalLedger = cashierAccountCode[2];
            String subsideryLedger = cashierAccountCode[3];
            fmsLuSystem.setSystemCode(systemCenter);
            fmsCostCenter.setSystemName(costCenter);
            fmsGeneralLedger.setGeneralLedgerCode(generalLedger);
            fmsLuSystem = fmsLuSystemBeanLocal.findBySytemCode2(fmsLuSystem);
            ssCcJunctionList = fmsLuSystem.getFmsCostcSystemJunctionList();
            if (fmsLuSystem.getType().toString().equals(projectType)) {
                searchProjectType();
                renderJobNo = true;
                sysJobNOList = fmsLuSystem.getFmsSystemJobJunctionList();
            } else {
                searchNonProjectType();
            }
            fmsCostcSystemJunction = fmsCostCSystemJunctionBeanLocal.findBySystemCodeAndCostCenterCode(fmsLuSystem, fmsCostCenter);
            fmsCostCenter = fmsCostcSystemJunction.getFmsCostCenter();
            fmsGeneralLedger.setGeneralLedgerCode(generalLedger);
            fmsGeneralLedger = fmsGeneralLedgerBeanLocal.getGlCode(fmsGeneralLedger);
            fmsSubsidiaryLedger = casherAccountEnty.getSubsidiaryId();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //recreate to assign getCasherAccountsList to casherAccountDetailDataModel
    public void recreateModelDetail() {
        casherAccountDetailDataModel = null;
        casherAccountDetailDataModel = new ListDataModel(getCasherAccountsList());
    }

    //generate GL list
    public SelectItem[] getGeneralLedgerList() {
        listGeneralLedger = fmsGeneralLedgerBeanLocal.getGeneralLederList();
        int sizeGL = 0, n, x;
        sizeGL = listGeneralLedger.size();
        for (int i = 0; i < sizeGL; i++) {
            x = listGeneralLedger.get(i).getGeneralLedgerId();
            x = x % 10;
            if (x == 0) {
                listGeneralLedger.remove(listGeneralLedger.get(i));
                sizeGL = listGeneralLedger.size();
                i -= 1;
            }
        }
        GeneralLedgerListSize = listGeneralLedger.size();
        return JsfUtil.getSelectItems(listGeneralLedger, true);
    }

    //get subsidery list
    public SelectItem[] getSubsidiaryLedgerList() {
        if (catagorynameStatus == 1) {
            fmsGeneralLedger.setGeneralLedgerId(MajorCatagorylistId);
            return JsfUtil.getSelectItems(subsidiaryLedgerBeanLocal.getsubsidiaryLedgerList(fmsGeneralLedger), true);
        }
        return null;
    }

    //find all
    public SelectItem[] getGLdata() {
        return JsfUtil.getSelectItems(fmsGeneralLedgerBeanLocal.findAll(), true);
    }

    //find all
    public SelectItem[] getListSys() {
        return JsfUtil.getSelectItems(fmsLuSystemBeanLocal.findAll(), true);
    }

    //find subsidery list
    public SelectItem[] findListSubL() {
        ArrayList<FmsSubsidiaryLedger> subsidaryList = new ArrayList<>();
        if (fmsGeneralLedger != null) {
            SelectItem[] listSl = null;
            subsidaryList = subsidiaryLedgerBeanLocal.findSubLedger(fmsGeneralLedger);
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

    //search project acc type
    public void searchProjectType() {
        glList = fmsGeneralLedgerBeanLocal.getByAccountType(projectType);
    }

    //search non project acc type
    public void searchNonProjectType() {
        glList = fmsGeneralLedgerBeanLocal.getByAccountType(nonProjectType);
    }

    //search 
    public void searchCasherByParameter() {
        try {
            casherAccountList = new ArrayList<>();
            //search by Emp Name only
            if (!(hrEmployees.getFirstName().isEmpty())) {
                casherAccountList = casherAccountBeanLocal.searchCasherByParameter(hrEmployees);
                //search all
            } else {
                casherAccountList = casherAccountBeanLocal.searchAllCasher();
            }
            casherAccountDetailDataModel = new ListDataModel(casherAccountList);
        } catch (Exception e) {
            JsfUtil.addFatalMessage("Failed to search. Try agian reloading the page");
        }
    }

    /**
     * @return the CasherAccountsList
     */
    public List<HrEmployees> SearchByEmpName(String fName) {
        ArrayList<HrEmployees> employees = null;
        hrEmployees.setFirstName(fName);
        employees = hrEmployeeBean.SearchByFname(hrEmployees);
        return employees;
    }

    //search employee
    public List<HrEmployees> SearchByEmpId(String empId) {
        ArrayList<HrEmployees> employees = null;
        hrEmployees.setEmpId(empId);
        employees = hrEmployeeBean.SearchByEmpId(hrEmployees);
        return employees;
    }

    //handle GL
    public void handleSelectGeneralLeger(ValueChangeEvent event) {
        if (null != event.getNewValue()) {
            MajorCatagorylistId = 0;
            String majorNames = event.getNewValue().toString().split("-")[1];
            for (int i = 0; i < GeneralLedgerListSize; i++) {
                if (listGeneralLedger.get(i).getAccountTitle().equalsIgnoreCase(majorNames)) {
                    fmsGeneralLedger = listGeneralLedger.get(i);
                    i = GeneralLedgerListSize;
                }
            }
            catagorynameStatus = 1;
        }
    }

    //get subsidery list
    public void getSubsidiaryLChange(ValueChangeEvent event) {
        if (!event.getNewValue().toString().isEmpty()) {
            fmsSubsidiaryLedger.setSubsidiaryId(Integer.parseInt(event.getNewValue().toString()));
            fmsSubsidiaryLedger = subsidiaryLedgerBeanLocal.getSubsidiaryInfo(fmsSubsidiaryLedger);
        }
    }

    //value change for change system
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
            }
        } catch (Exception e) {
            e.printStackTrace();
            JsfUtil.addFatalMessage("Unable to process. Try again reloading the page.");
        }
    }

    //value change for change cost center
    public void onChangeCostCenter(ValueChangeEvent event) {
        try {
            if (event.getNewValue() != null) {
                fmsCostcSystemJunction = (FmsCostcSystemJunction) event.getNewValue();
                fmsCostCenter = fmsCostcSystemJunction.getFmsCostCenter();
            }
        } catch (Exception e) {
            e.printStackTrace();
            JsfUtil.addFatalMessage("Unable to process. Try again reloading the page.");
        }
    }

    //value change for change GL
    public void onChangeGeneralLedger(ValueChangeEvent event) {
        try {
            if (!event.getNewValue().toString().isEmpty()) {
                fmsGeneralLedger.setGeneralLedgerCode(event.getNewValue().toString());
                fmsGeneralLedger = fmsGeneralLedgerBeanLocal.getGLDetail(fmsGeneralLedger);
                fmsGeneralLedger = (FmsGeneralLedger) event.getNewValue();
                slList = subsidiaryLedgerBeanLocal.findBySsCcJuncAndGL(fmsCostcSystemJunction, fmsGeneralLedger);
            }
        } catch (Exception e) {
            JsfUtil.addFatalMessage("Unable to procefindBySsCcJuncAndGLss. Try again reloading the page.");
        }
    }

    //value change for change subsidery ledger
    public void onChangeSubsidiaryLedger(ValueChangeEvent event) {
        try {
            if (!event.getNewValue().toString().isEmpty()) {
                fmsSubsidiaryLedger = (FmsSubsidiaryLedger) event.getNewValue();
            }
        } catch (Exception e) {
            JsfUtil.addFatalMessage("Unable to process. Try again reloading the page.");
        }
    }

    //value change for change jon number
    public void onChangeJobNo(ValueChangeEvent event) {
        try {
            if (event.getNewValue() != null) {
                fmsSystemJobJunction = (FmsSystemJobJunction) event.getNewValue();
            }
        } catch (Exception e) {
            e.printStackTrace();
            JsfUtil.addFatalMessage("Unable to process. Try again reloading the page.");
        }
    }

    //clear
    public String clearPopup() {
        hrEmployees = new HrEmployees();
        casherAccountEnty = new FmsCasherAccount();
        fmsLuSystem = new FmsLuSystem();
        fmsCostCenter = new FmsCostCenter();
        fmsCostcSystemJunction = new FmsCostcSystemJunction();
        fmsSubsidiaryLedger = new FmsSubsidiaryLedger();
        fmsGeneralLedger = new FmsGeneralLedger();
        casherAccountEnty = new FmsCasherAccount();
        dataTableUpdateStatus = 0;
        return null;
    }

    //clear
    public String clearPage() {
        casherAccountDetailDataModel = null;
        casherAccountEnty = null;
        hrEmployees = null;
        fmsLuSystem = null;
        fmsCostcSystemJunction = null;
        fmsCostCenter = null;
        fmsGeneralLedger = null;
        fmsSubsidiaryLedger = null;
        updteStatus = 0;
        saveorUpdateBundle = "Create";
        btnaddvisibility = true;
        btnSave2Visibility = false;
        return null;
    }

    //create and search render
    public void createNewPettyCash() {
        clearPage();
        saveorUpdateBundle = "Create";
        disableBtnCreate = false;
        if (createOrSearchBundle.equals("New")) {
            renderPnlCreatePettyCash = true;
            renderPnlManPage = false;
            btnSave2Visibility = false;
            btnaddvisibility = true;
            createOrSearchBundle = "Search";
            setIcone("ui-icon-search");
        } else if (createOrSearchBundle.equals("Search")) {
            renderPnlCreatePettyCash = false;
            renderPnlManPage = true;
            createOrSearchBundle = "New";
            setIcone("ui-icon-plus");
        }
    }
}
