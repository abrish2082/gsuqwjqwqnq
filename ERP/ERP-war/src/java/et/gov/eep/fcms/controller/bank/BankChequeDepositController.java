/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.controller.bank;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.event.ValueChangeEvent;
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
import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.fcms.businessLogic.admin.FmsCostCSystemJunctionBeanLocal;
import et.gov.eep.fcms.businessLogic.pettyCash.FmsCasherAccountBeanLocal;
import et.gov.eep.fcms.businessLogic.bank.bankAccountBeanLocal;
import et.gov.eep.fcms.businessLogic.bank.bankBranchAccountsBeanLocal;
import et.gov.eep.fcms.businessLogic.bank.bankChequeDepositBeanLocal;
import et.gov.eep.fcms.businessLogic.bank.FmsCodedTransactionBeanLocal;
import et.gov.eep.fcms.businessLogic.admin.FmsGeneralLedgerBeanLocal;
import et.gov.eep.fcms.businessLogic.admin.FmsLuSystemBeanLocal;
import et.gov.eep.fcms.businessLogic.admin.subsidiaryBeanLocal;
import et.gov.eep.fcms.businessLogic.bank.fms_BankBeanLocal;
import et.gov.eep.fcms.entity.bank.FmsBank;
import et.gov.eep.fcms.entity.bank.FmsBankAccount;
import et.gov.eep.fcms.entity.bank.FmsBankBranchAccounts;
import et.gov.eep.fcms.entity.bank.FmsBankChequeDeposit;
import et.gov.eep.fcms.entity.pettyCash.FmsCasherAccount;
import et.gov.eep.fcms.entity.bank.FmsCodedTransaction;
import et.gov.eep.fcms.entity.admin.FmsCostCenter;
import et.gov.eep.fcms.entity.admin.FmsCostcSystemJunction;
import et.gov.eep.fcms.entity.admin.FmsGeneralLedger;
import et.gov.eep.fcms.entity.admin.FmsLuSystem;
import et.gov.eep.fcms.entity.admin.FmsSubsidiaryLedger;
import et.gov.eep.fcms.entity.admin.FmsSystemJobJunction;

/**
 *
 * @author mubejbl
 */
@Named(value = "bankChequeDepositController")
@ViewScoped
public class BankChequeDepositController implements Serializable {

    //<editor-fold defaultstate="collapsed" desc="Inject Entities and @ EJB">
    //Inject @EJB
    @EJB
    private bankChequeDepositBeanLocal chequeDepositBeanLocal;
    @EJB
    private bankAccountBeanLocal bankAccBeanLocal;
    @EJB
    private bankBranchAccountsBeanLocal branchAccountsBeanLocal;
    @EJB
    private fms_BankBeanLocal bankBeanLocal;
    @EJB
    private FmsCodedTransactionBeanLocal codedTransactionBeanLocal;
    @EJB
    FmsCasherAccountBeanLocal cashierAccountBeanLocal;
    @EJB
    FmsLuSystemBeanLocal fmsLuSystemBeanLocal;
    @EJB
    FmsCostCSystemJunctionBeanLocal fmsCostCSystemJunctionBeanLocal;
    @EJB
    private FmsGeneralLedgerBeanLocal fmsGeneralLedgerBeanLocal;
    @EJB
    private subsidiaryBeanLocal subsidiaryLedgerBeanLocal;
    //Inject entities
    @Inject
    FmsCodedTransaction fmsCodedTransaction;
    @Inject
    FmsCasherAccount casherAccountEnty;
    @Inject
    private FmsBankBranchAccounts fmsBankBranchAccounts;
    @Inject
    private FmsBankChequeDeposit fmsBankChequeDeposit;
    @Inject
    private FmsBankAccount fmsBankAccount;
    @Inject
    private FmsBank fmsBank;
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
    SessionBean SessionBean;
//</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Variable declaration">
    private NumberConverter numberConverter = new NumberConverter();
    FmsBankChequeDeposit selectedFmsChequeDeposit;
    DataModel<FmsBankChequeDeposit> chequeDepositDataModel;
    List<FmsCodedTransaction> codedTranList;
    List<FmsBankChequeDeposit> dates;
    List<FmsBankBranchAccounts> accountNumbers;
    List<FmsBankAccount> branchNames;
    List<FmsCasherAccount> casherAccountsList;
    List<FmsBankChequeDeposit> chequeNumbers;
    List<FmsCostcSystemJunction> ssCcJunctionList = new ArrayList<>();
    List<FmsSystemJobJunction> sysJobNOList;
    List<FmsGeneralLedger> glList;
    List<FmsSubsidiaryLedger> slList;
    List<FmsLuSystem> systemList;
    List<FmsBankChequeDeposit> chequeList;
    String saveorUpdateBundle = "Save";
    private String creditAccCode = "";
    private String debitAccCode = "";
    private String icone = "ui-icon-plus";
    private String createOrSearchBundle = "New";
    private String display_conn;
    private String tabIndex = "0";
    final Integer projectType = 2;
    final Integer nonProjectType = 1;
    int updteStatus = 0;
    double balance1 = 0.0;
    double amount = 0.0;
    double balance2 = 0.0;
    double prev_cheque_deposit_amt = 0.0;
    double balance3 = 0.0;
    double balance4 = 0.0;
    double prevChequeDepAmount = 0.0;
    private boolean disableBtnCreate;
    private boolean renderPnlCreateCheque = false;
    private boolean renderPnlManPage = true;
    private boolean isDisabledCashier = true;
    private boolean isDisabledBtnAdd = true;
    private boolean isReadOnlyDeposeitedBy = true;
    private boolean isRequiredRdoOption = true;
    boolean renderJobNo = false;
//</editor-fold>

    public BankChequeDepositController() {
        numberConverter.setPattern("#,##0.00##");
        numberConverter.setMinIntegerDigits(1);
        numberConverter.setMaxIntegerDigits(40);
        numberConverter.setMaxFractionDigits(3);
    }

    //<editor-fold defaultstate="collapsed" desc="Getter and Setter Methods">
    public double getPrevChequeDepAmount() {
        return prevChequeDepAmount;
    }

    public void setPrevChequeDepAmount(double prevChequeDepAmount) {
        this.prevChequeDepAmount = prevChequeDepAmount;
    }

    public int getUpdteStatus() {
        return updteStatus;
    }

    public void setUpdteStatus(int updteStatus) {
        this.updteStatus = updteStatus;
    }

    public String getTabIndex() {
        return tabIndex;
    }

    public void setTabIndex(String tabIndex) {
        this.tabIndex = tabIndex;
    }

    public String getSaveorUpdateBundle() {
        return saveorUpdateBundle;
    }

    public void setSaveorUpdateBundle(String saveorUpdateBundle) {
        this.saveorUpdateBundle = saveorUpdateBundle;
    }

    public bankBranchAccountsBeanLocal getBranchAccountsBeanLocal() {
        return branchAccountsBeanLocal;
    }

    public void setBranchAccountsBeanLocal(bankBranchAccountsBeanLocal branchAccountsBeanLocal) {
        this.branchAccountsBeanLocal = branchAccountsBeanLocal;
    }

    public FmsBankBranchAccounts getFmsBankBranchAccounts() {
        if (fmsBankBranchAccounts == null) {
            fmsBankBranchAccounts = new FmsBankBranchAccounts();
        }
        return fmsBankBranchAccounts;
    }

    public void setFmsBankBranchAccounts(FmsBankBranchAccounts fmsBankBranchAccounts) {
        this.fmsBankBranchAccounts = fmsBankBranchAccounts;
    }

    //List ChequeDepositDates
    public List<FmsBankChequeDeposit> getDates() {
        if (dates == null) {
            dates = new ArrayList<>();
        }
        return dates;
    }

    public void setDates(List<FmsBankChequeDeposit> dates) {
        this.dates = dates;
    }

    public List<FmsBankBranchAccounts> getBankAccountNo() {
        return branchAccountsBeanLocal.getBankAccountNo();
    }

    public List<FmsBankChequeDeposit> getChequeNumbers() {
        return chequeNumbers;
    }

    public void setChequeNumbers(List<FmsBankChequeDeposit> chequeNumbers) {
        this.chequeNumbers = chequeNumbers;
    }

    public List<FmsCodedTransaction> getCodedTranList() {
        return codedTranList;
    }

    public void setCodedTranList(List<FmsCodedTransaction> codedTranList) {
        this.codedTranList = codedTranList;
    }

    public FmsBankChequeDeposit getSelectedFmsChequeDeposit() {
        return selectedFmsChequeDeposit;
    }

    public void setSelectedFmsChequeDeposit(FmsBankChequeDeposit selectedFmsChequeDeposit) {
        this.selectedFmsChequeDeposit = selectedFmsChequeDeposit;
    }

    public List<FmsCasherAccount> getCasherNames() {
        return cashierAccountBeanLocal.getAllCashierName();
    }

    public List<FmsBankAccount> getBranchNames() {
        if (branchNames == null) {
            branchNames = new ArrayList<>();
        }
        return branchNames;
    }

    public void setBranchNames(List<FmsBankAccount> branchNames) {
        this.branchNames = branchNames;
    }

    public List<FmsBankBranchAccounts> getAccountNumbers() {
        if (accountNumbers == null) {
            accountNumbers = new ArrayList<>();
        }
        return accountNumbers;
    }

    public void setAccountNumbers(List<FmsBankBranchAccounts> accountNumbers) {
        this.accountNumbers = accountNumbers;
    }

    //get Branch name from bank account
    public List<FmsBank> getBankNameList() {
        return bankBeanLocal.getBankName();

    }

    public fms_BankBeanLocal getBankBeanLocal() {
        return bankBeanLocal;
    }

    public void setBankBeanLocal(fms_BankBeanLocal bankBeanLocal) {
        this.bankBeanLocal = bankBeanLocal;
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

    public void setDisableBtnCreate(boolean disableBtnCreate) {
        this.disableBtnCreate = disableBtnCreate;
    }

    public String getCreateOrSearchBundle() {
        return createOrSearchBundle;
    }

    public void setCreateOrSearchBundle(String createOrSearchBundle) {
        this.createOrSearchBundle = createOrSearchBundle;
    }

    public boolean isRenderPnlCreateCheque() {
        return renderPnlCreateCheque;
    }

    public void setRenderPnlCreateCheque(boolean renderPnlCreateCheque) {
        this.renderPnlCreateCheque = renderPnlCreateCheque;
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

    public boolean isIsDisabledCashier() {
        return isDisabledCashier;
    }

    public void setIsDisabledCashier(boolean isDisabledCashier) {
        this.isDisabledCashier = isDisabledCashier;
    }

    public boolean isIsDisabledBtnAdd() {
        return isDisabledBtnAdd;
    }

    public void setIsDisabledBtnAdd(boolean isDisabledBtnAdd) {
        this.isDisabledBtnAdd = isDisabledBtnAdd;
    }

    public boolean isIsReadOnlyDeposeitedBy() {
        return isReadOnlyDeposeitedBy;
    }

    public void setIsReadOnlyDeposeitedBy(boolean isReadOnlyDeposeitedBy) {
        this.isReadOnlyDeposeitedBy = isReadOnlyDeposeitedBy;
    }

    public boolean isIsRequiredRdoOption() {
        return isRequiredRdoOption;
    }

    public void setIsRequiredRdoOption(boolean isRequiredRdoOption) {
        this.isRequiredRdoOption = isRequiredRdoOption;
    }

    public NumberConverter getNumberConverter() {
        return numberConverter;
    }

    public void setNumberConverter(NumberConverter numberConverter) {
        this.numberConverter = numberConverter;
    }

    public DataModel<FmsBankChequeDeposit> getChequeDepositDataModel() {
        if (chequeDepositDataModel == null) {
            chequeDepositDataModel = new ListDataModel<>();
        }
        return chequeDepositDataModel;
    }

    public void setChequeDepositDataModel(DataModel<FmsBankChequeDeposit> chequeDepositDataModel) {
        this.chequeDepositDataModel = chequeDepositDataModel;
    }

    public List<FmsBankChequeDeposit> getChequeList() {
        return chequeList;
    }

    public void setChequeList(List<FmsBankChequeDeposit> chequeList) {
        this.chequeList = chequeList;
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

    public FmsCasherAccountBeanLocal getCashierAccountBeanLocal() {
        return cashierAccountBeanLocal;
    }

    public void setCashierAccountBeanLocal(FmsCasherAccountBeanLocal cashierAccountBeanLocal) {
        this.cashierAccountBeanLocal = cashierAccountBeanLocal;
    }

    public List<FmsCasherAccount> getCasherAccountsList() {
        if (casherAccountsList == null) {
            casherAccountsList = new ArrayList<>();
        }
        return casherAccountsList;
    }

    public void setCasherAccountsList(List<FmsCasherAccount> casherAccountsList) {
        this.casherAccountsList = casherAccountsList;
    }

    public FmsCodedTransactionBeanLocal getCodedTransactionBeanLocal() {
        return codedTransactionBeanLocal;
    }

    public void setCodedTransactionBeanLocal(FmsCodedTransactionBeanLocal codedTransactionBeanLocal) {
        this.codedTransactionBeanLocal = codedTransactionBeanLocal;
    }

    public FmsCodedTransaction getFmsCodedTransaction() {
        if (fmsCodedTransaction == null) {
            fmsCodedTransaction = new FmsCodedTransaction();
        }
        return fmsCodedTransaction;
    }

    public void setFmsCodedTransaction(FmsCodedTransaction fmsCodedTransaction) {
        this.fmsCodedTransaction = fmsCodedTransaction;
    }

    public bankChequeDepositBeanLocal getChequeDepositBeanLocal() {
        return chequeDepositBeanLocal;
    }

    public void setChequeDepositBeanLocal(bankChequeDepositBeanLocal chequeDepositBeanLocal) {
        this.chequeDepositBeanLocal = chequeDepositBeanLocal;
    }

    public bankAccountBeanLocal getBankAccBeanLocal() {
        return bankAccBeanLocal;
    }

    public void setBankAccBeanLocal(bankAccountBeanLocal bankAccBeanLocal) {
        this.bankAccBeanLocal = bankAccBeanLocal;
    }

    public FmsBankAccount getFmsBankAccount() {
        if (fmsBankAccount == null) {
            fmsBankAccount = new FmsBankAccount();
        }
        return fmsBankAccount;
    }

    public void setFmsBankAccount(FmsBankAccount fmsBankAccount) {
        this.fmsBankAccount = fmsBankAccount;
    }

    public FmsBank getFmsBank() {
        return fmsBank;
    }

    public void setFmsBank(FmsBank fmsBank) {
        this.fmsBank = fmsBank;
    }

    public FmsBankChequeDeposit getFmsBankChequeDeposit() {
        if (fmsBankChequeDeposit == null) {
            fmsBankChequeDeposit = new FmsBankChequeDeposit();
        }
        return fmsBankChequeDeposit;
    }

    public void setFmsBankChequeDeposit(FmsBankChequeDeposit fmsBankChequeDeposit) {
        this.fmsBankChequeDeposit = fmsBankChequeDeposit;
    }
//</editor-fold>

    //searching for bank name from bankAccBeanLocal
    public List<FmsBankAccount> getBankName() {
        return bankAccBeanLocal.getBankName();
    }

    //searching for branch name from bankAccBeanLocal
    public List<FmsBankAccount> getBranchName() {
        return bankAccBeanLocal.getBranchName();
    }

    //searchChequeDepositByDate
    public ArrayList<FmsBankChequeDeposit> searchChequeDepositByDate(Date chequeDepositDate) {
        ArrayList<FmsBankChequeDeposit> chequeDeposits = null;
        fmsBankChequeDeposit.setChequeDepositDate(chequeDepositDate);

        chequeDeposits = chequeDepositBeanLocal.searchChequeDepositByDate(fmsBankChequeDeposit);
        return chequeDeposits;
    }

    //select item for searching all list from fmsGeneralLedgerBeanLocal
    public SelectItem[] getGLdata() {
        return JsfUtil.getSelectItems(fmsGeneralLedgerBeanLocal.findAll(), true);
    }

    //select item for searching all list from fmsLuSystemBeanLocal
    public SelectItem[] getListSys() {
        return JsfUtil.getSelectItems(fmsLuSystemBeanLocal.findAll(), true);
    }

    //select item for searching all list from subsidiaryLedgerBeanLocal
    public SelectItem[] getSubList() {
        return JsfUtil.getSelectItems(subsidiaryLedgerBeanLocal.findAll(), true);
    }

    //value change event for getting subsidery code from fmsBankBranchAccounts
    public void getBalance(ValueChangeEvent event) {
        fmsBankBranchAccounts = branchAccountsBeanLocal.getBalanceByAcctNo(fmsBankBranchAccounts);
        debitAccCode = fmsBankBranchAccounts.getSubsidiaryId().getSubsidiaryCode();
    }

    //value change event for getting system detail from fmsLuSystemBeanLocal
    public void SystemChange(ValueChangeEvent event) {
        try {
            if (!event.getNewValue().toString().isEmpty()) {
                fmsLuSystem.setSystemCode(event.getNewValue().toString());
                fmsLuSystem = fmsLuSystemBeanLocal.getSysDetail(fmsLuSystem);
            }
        } catch (Exception e) {
            JsfUtil.addFatalMessage("Unable to process. Try again reloading the page.");
        }
    }

    //value change event for getting GL detail from fmsGeneralLedgerBeanLocal
    public void getGeneralLedgerChange(ValueChangeEvent event) {
        try {
            if (!event.getNewValue().toString().isEmpty()) {
                fmsGeneralLedger.setGeneralLedgerCode(event.getNewValue().toString());
                fmsGeneralLedger = fmsGeneralLedgerBeanLocal.getGLDetail(fmsGeneralLedger);
                fmsSubsidiaryLedger.setGeneralLedgerId(fmsGeneralLedger);
            }
        } catch (Exception e) {
            JsfUtil.addFatalMessage("Unable to process. Try again reloading the page.");
        }
    }

    //value change event for getting subsidery info from subsidiaryLedgerBeanLocal
    public void getSubsidiaryLChange(ValueChangeEvent event) {
        try {
            if (!event.getNewValue().toString().isEmpty()) {
                fmsSubsidiaryLedger.setSubsidiaryId(Integer.parseInt(event.getNewValue().toString()));
                fmsSubsidiaryLedger = subsidiaryLedgerBeanLocal.getSubsidiaryInfo(fmsSubsidiaryLedger);

            }
        } catch (Exception e) {
            JsfUtil.addFatalMessage("Unable to process. Try again reloading the page.");
        }
    }

    //value change event for handling cashier code
    public void handleCahierCode(ValueChangeEvent event) {
        casherAccountEnty = cashierAccountBeanLocal.getSlcode(casherAccountEnty);
        fmsBankChequeDeposit.setDisplay_conn(casherAccountEnty.getSubsidiaryId().getSubsidiaryCode());
        fmsBankChequeDeposit.setAccountCode(casherAccountEnty.getSubsidiaryId().getSubsidiaryCode());
        fmsBankChequeDeposit.setDepositedBy(casherAccountEnty.getEmpCode().getFirstName() + " " + casherAccountEnty.getEmpCode().getMiddleName());
        creditAccCode = casherAccountEnty.getSubsidiaryId().getSubsidiaryCode();
    }

    //value change event
    public List<FmsBankChequeDeposit> getDateFromAccount(ValueChangeEvent event) {
        dates = chequeDepositBeanLocal.getChequeDepositDate(fmsBankChequeDeposit);
        return chequeDepositBeanLocal.getChequeDepositDate(fmsBankChequeDeposit);

    }
//value chage event to get BankBranchFromBankAccount from bankAccBeanLocal

    public List<FmsBankAccount> getBankBranchFromBankAccount(ValueChangeEvent event) {

        fmsBankAccount.setBankId(fmsBankAccount.getBankId());
        branchNames = bankAccBeanLocal.getBranchNameById(fmsBankAccount);
        return bankAccBeanLocal.getBranchNameById(fmsBankAccount);
    }

    //value chage event to get BankBranchFromBankAccount from bankAccBeanLocal
    public List<FmsBankChequeDeposit> getChequeNoFromChequeDeposit(ValueChangeEvent event) {
        chequeNumbers = chequeDepositBeanLocal.getChequeDepositTransactionsByChequeNo(fmsBankChequeDeposit);
        return chequeDepositBeanLocal.getChequeDepositTransactionsByChequeNo(fmsBankChequeDeposit);
    }

    //value chage event to get BankBranchFromBankAccount from bankAccBeanLocal
    public void getAccountNumberFromBranchAccounts(ValueChangeEvent event) {
        fmsBankBranchAccounts.setBankAccountId(fmsBankAccount);
        accountNumbers = branchAccountsBeanLocal.getAccountNumberByBankAccountId(fmsBankBranchAccounts);
    }

    //search project type
    public void searchProjectType() {
        glList = fmsGeneralLedgerBeanLocal.getByAccountType(projectType);
    }

    //search non project type
    public void searchNonProjectType() {
        glList = fmsGeneralLedgerBeanLocal.getByAccountType(nonProjectType);
    }

    //value change event for system change
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

    //value change event for cost center
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

    //value change event for GL
    public void onChangeGeneralLedger(ValueChangeEvent event) {
        try {
            if (!event.getNewValue().toString().isEmpty()) {
                fmsGeneralLedger.setGeneralLedgerCode(event.getNewValue().toString());
                fmsGeneralLedger = fmsGeneralLedgerBeanLocal.getGLDetail(fmsGeneralLedger);
                fmsGeneralLedger = (FmsGeneralLedger) event.getNewValue();
                slList = subsidiaryLedgerBeanLocal.findBySsCcJuncAndGL(fmsCostcSystemJunction, fmsGeneralLedger);
            }
        } catch (Exception e) {
            JsfUtil.addFatalMessage("Unable to process. Try again reloading the page.");
        }
    }

    //value change event for subsidery ledger
    public void onChangeSubsidiaryLedger(ValueChangeEvent event) {
        try {
            if (!event.getNewValue().toString().isEmpty()) {
                fmsSubsidiaryLedger = (FmsSubsidiaryLedger) event.getNewValue();
            }
        } catch (Exception e) {
            JsfUtil.addFatalMessage("Unable to process. Try again reloading the page.");
        }
    }

    //value change event for job number
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

    public FmsBankChequeDeposit getAllDataFromChequehDeposti(ValueChangeEvent event) {
        fmsBankChequeDeposit.setChequeDepositDate(fmsBankChequeDeposit.getChequeDepositDate());
        fmsBankChequeDeposit = chequeDepositBeanLocal.getData(fmsBankChequeDeposit);
        fmsCodedTransaction.setTranRef(fmsBankChequeDeposit.getChequeNumber());
        codedTranList = codedTransactionBeanLocal.getCodedTransactionsByTranRef(fmsCodedTransaction);
        fmsBankAccount.setBranchName(fmsBankChequeDeposit.getBranchName());
        fmsBank = fmsBankChequeDeposit.getBankId();
        casherAccountEnty = cashierAccountBeanLocal.getSlcode(casherAccountEnty);
        branchNames = fmsBank.getFmsBankAccountList();
        for (int i = 0; i < branchNames.size(); i++) {
            if (branchNames.get(i).getBranchName().equals(fmsBankChequeDeposit.getBranchName())) {
                fmsBankAccount = branchNames.get(i);
                fmsBankAccount.setBankAccountId(branchNames.get(i).getBankAccountId());
                accountNumbers = fmsBankAccount.getFmsBankBranchAccountsList();
                break;
            }
        }

        for (int j = 0; j < accountNumbers.size(); j++) {
            if (accountNumbers.get(j).getAccountNumber().equals(fmsBankChequeDeposit.getAccountNumber())) {
                fmsBankBranchAccounts = accountNumbers.get(j);
                break;
            }
        }

        fmsBankAccount.setBankId(fmsBank);
        fmsBankChequeDeposit.setAccountNumber(fmsBankChequeDeposit.getAccountNumber());
        prevChequeDepAmount = fmsBankChequeDeposit.getAmount();
        updteStatus = 1;
        saveorUpdateBundle = "Update";
        tabIndex = "1";
        return fmsBankChequeDeposit;
    }

    public void isDisabledCashierEvent(ValueChangeEvent event) {
        fmsBankChequeDeposit.setDisplay_conn("");
        fmsBankChequeDeposit.setDepositedBy("");
        if (fmsBankChequeDeposit.getDepositedByOption().equalsIgnoreCase("cashier")) {
            isDisabledCashier = false;
            isReadOnlyDeposeitedBy = true;
            isDisabledBtnAdd = true;
        } else {
            casherAccountEnty = null;
            display_conn = "";
            isDisabledCashier = true;
            isReadOnlyDeposeitedBy = false;
            isDisabledBtnAdd = false;
        }

    }

    //select item 
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

    //cocatination of system code,general leder code,account code,SL
    public void Concatenate1() {
        try {
            String accountCode[] = fmsBankBranchAccounts.getSubsidiaryId().getSubsidiaryCode().split("-");
            fmsLuSystem.setSystemId(Integer.parseInt(accountCode[0]));
            fmsLuSystem = fmsLuSystemBeanLocal.getSystembyId(fmsLuSystem);
            String SL_display = "SL";
            fmsGeneralLedger.setGeneralLedgerId(Integer.parseInt(accountCode[2]));
            fmsGeneralLedger = fmsGeneralLedgerBeanLocal.getByGlId(fmsGeneralLedger);
            if (!"SL".equals(accountCode[3])) {
                fmsSubsidiaryLedger.setSubsidiaryId(Integer.parseInt(accountCode[3]));
                fmsSubsidiaryLedger = subsidiaryLedgerBeanLocal.getSubsidiaryInfo(fmsSubsidiaryLedger);
                SL_display = fmsSubsidiaryLedger.getSubsidiaryCode();
            }
            debitAccCode = fmsLuSystem.getSystemCode() + "-" + accountCode[1] + "-" + fmsGeneralLedger.getGeneralLedgerCode() + "-" + SL_display;
            fmsBankBranchAccounts.setDisplay_conn(debitAccCode);
        } catch (Exception e) {
        }
    }

    //cocatination of system code,general leder code,cashier account code,SL
    public void Concatenate2() {
        String cashierccountCode[] = fmsBankChequeDeposit.getAccountCode().split("-");
        fmsLuSystem.setSystemId(Integer.parseInt(cashierccountCode[0]));
        fmsLuSystem = fmsLuSystemBeanLocal.getSystembyId(fmsLuSystem);
        String SL_display = "SL";
        fmsGeneralLedger.setGeneralLedgerId(Integer.parseInt(cashierccountCode[2]));
        fmsGeneralLedger = fmsGeneralLedgerBeanLocal.getByGlId(fmsGeneralLedger);
        if (!"SL".equals(cashierccountCode[3])) {
            fmsSubsidiaryLedger.setSubsidiaryId(Integer.parseInt(cashierccountCode[3]));
            fmsSubsidiaryLedger = subsidiaryLedgerBeanLocal.getSubsidiaryInfo(fmsSubsidiaryLedger);
            SL_display = fmsSubsidiaryLedger.getSubsidiaryCode();
        }
        creditAccCode = fmsLuSystem.getSystemCode() + "-" + cashierccountCode[1] + "-" + fmsGeneralLedger.getGeneralLedgerCode() + "-" + SL_display;
        fmsBankChequeDeposit.setDisplay_conn(creditAccCode);
    }

    //    private String display_conn;
    public void Concatenate3() {
        for (int i = 0; i < chequeList.size(); i++) {
            String cashierccountCode[] = chequeList.get(i).getAccountCode().split("-");
            fmsLuSystem.setSystemId(Integer.parseInt(cashierccountCode[0]));
            fmsLuSystem = fmsLuSystemBeanLocal.getSystembyId(fmsLuSystem);
            String SL_display = "SL";
            fmsGeneralLedger.setGeneralLedgerId(Integer.parseInt(cashierccountCode[2]));
            fmsGeneralLedger = fmsGeneralLedgerBeanLocal.getByGlId(fmsGeneralLedger);
            if (!"SL".equals(cashierccountCode[3])) {
                fmsSubsidiaryLedger.setSubsidiaryId(Integer.parseInt(cashierccountCode[3]));
                fmsSubsidiaryLedger = subsidiaryLedgerBeanLocal.getSubsidiaryInfo(fmsSubsidiaryLedger);
                SL_display = fmsSubsidiaryLedger.getSubsidiaryCode();
            }
            display_conn = fmsLuSystem.getSystemCode() + "-" + cashierccountCode[1] + "-" + fmsGeneralLedger.getGeneralLedgerCode() + "-" + SL_display;
            fmsBankChequeDeposit.setDisplay_conn(display_conn);
            chequeList.get(i).setDisplay_conn(display_conn);
        }
    }

    //cocatination of system code,general leder code,SL
    public void Concatenate() {
        String CC = "CC";
        String SL = "SL";
        String CC_display = "CC";
        String SL_display = "SL";
        if (fmsSubsidiaryLedger.getSubsidiaryCode() != null) {
            SL = fmsSubsidiaryLedger.getSubsidiaryCode();
            SL_display = fmsSubsidiaryLedger.getSubsidiaryId().toString();

        }
        display_conn = fmsLuSystem.getSystemCode() + "-" + CC + "-" + fmsSubsidiaryLedger.getGeneralLedgerId().getGeneralLedgerCode() + "-" + SL;
        creditAccCode = fmsLuSystem.getSystemId() + "-" + CC_display + "-" + fmsSubsidiaryLedger.getGeneralLedgerId().getGeneralLedgerId() + "-" + SL_display;
    }

    //Lis chequeNumbers
    //populate
    public void populate(SelectEvent event) {
        isRequiredRdoOption = false;
        fmsBankChequeDeposit = (FmsBankChequeDeposit) event.getObject();
        fmsBankChequeDeposit.setDisplay_conn(fmsBankChequeDeposit.getAccountCode());
        fmsCodedTransaction.setTranRef(fmsBankChequeDeposit.getChequeNumber());
        codedTranList = codedTransactionBeanLocal.getCodedTransactionsByTranRef(fmsCodedTransaction);
        fmsBank = fmsBankChequeDeposit.getBankId();
        branchNames = fmsBank.getFmsBankAccountList();
        for (int i = 0; i < branchNames.size(); i++) {
            if (branchNames.get(i).getBranchName().equals(fmsBankChequeDeposit.getBranchName())) {
                fmsBankAccount = branchNames.get(i);
                fmsBankAccount.setBankAccountId(branchNames.get(i).getBankAccountId());
                accountNumbers = fmsBankAccount.getFmsBankBranchAccountsList();
                break;
            }
        }
        for (int j = 0; j < accountNumbers.size(); j++) {
            if (accountNumbers.get(j).getAccountNumber().equals(fmsBankChequeDeposit.getAccountNumber())) {
                fmsBankBranchAccounts = accountNumbers.get(j);
                break;
            }
        }
        fmsBankAccount.setBankId(fmsBank);
        fmsBankChequeDeposit.setAccountNumber(fmsBankChequeDeposit.getAccountNumber());
        prevChequeDepAmount = fmsBankChequeDeposit.getAmount();
        updteStatus = 1;
        saveorUpdateBundle = "Update";
        tabIndex = "1";
        renderPnlCreateCheque = true;
        renderPnlManPage = false;
        saveorUpdateBundle = "Update";
        createOrSearchBundle = "Search";
        setIcone("ui-icon-search");
        disableBtnCreate = true;
    }

    //recreate method for assigning cheque list to chequeDepositDataModel
    private void recreateChequeDataModel() {
        chequeDepositDataModel = null;
        chequeDepositDataModel = new ListDataModel(new ArrayList(chequeList));
    }

    //add method
    public String addAccountInfoDetail() {
        try {
            display_conn = fmsSubsidiaryLedger.getSubsidiaryCode();
            fmsBankChequeDeposit.setAccountCode(display_conn);
            fmsBankChequeDeposit.setDisplay_conn(display_conn);
            creditAccCode = fmsSubsidiaryLedger.getSubsidiaryCode();
        } catch (Exception e) {
            JsfUtil.addFatalMessage("Unable to Add Account Code. Try again Reloadin the page.");
        }
        return null;
    }

    //Search Cash Deposit
    public void searchChequeDep() {
        chequeList = new ArrayList<>();
        try {
            //find all
            if ((fmsBankChequeDeposit.getAccountNumber().isEmpty()) && (fmsBankChequeDeposit.getChequeDepositDate() == null) && (fmsBankChequeDeposit.getChequeNumber().isEmpty())) {
                chequeList = chequeDepositBeanLocal.searchAllCheques(fmsBankChequeDeposit);
                recreateChequeDataModel();
                //find by account number only
            } else if (!(fmsBankChequeDeposit.getAccountNumber().isEmpty()) && (fmsBankChequeDeposit.getChequeDepositDate() == null) && (fmsBankChequeDeposit.getChequeNumber().isEmpty())) {
                fmsBankChequeDeposit.setChequeDepositId(fmsBankChequeDeposit.getChequeDepositId());
                fmsBankChequeDeposit.setBankId(fmsBankChequeDeposit.getBankId());
                fmsBankChequeDeposit.setAccountNumber(fmsBankChequeDeposit.getAccountNumber());
                chequeList = chequeDepositBeanLocal.getChequeDepositDate(fmsBankChequeDeposit);
                recreateChequeDataModel();
                //find by account number and date only
            } else if (!(fmsBankChequeDeposit.getAccountNumber().isEmpty()) && !(fmsBankChequeDeposit.getChequeDepositDate() == null) && (fmsBankChequeDeposit.getChequeNumber().isEmpty())) {
                fmsBankChequeDeposit.setChequeDepositId(fmsBankChequeDeposit.getChequeDepositId());
                fmsBankChequeDeposit.setBankId(fmsBankChequeDeposit.getBankId());
                fmsBankChequeDeposit.setAccountNumber(fmsBankChequeDeposit.getAccountNumber());
                fmsBankChequeDeposit.setChequeDepositDate(fmsBankChequeDeposit.getChequeDepositDate());
                chequeList = chequeDepositBeanLocal.getChequeDepositTransactions(fmsBankChequeDeposit);
                recreateChequeDataModel();
                //find by account number, date and transaction ref-no.
            } else if (!(fmsBankChequeDeposit.getAccountNumber().isEmpty()) && !(fmsBankChequeDeposit.getChequeDepositDate() == null) && !(fmsBankChequeDeposit.getChequeNumber().isEmpty())) {
                fmsBankChequeDeposit.setChequeDepositId(fmsBankChequeDeposit.getChequeDepositId());
                fmsBankChequeDeposit.setBankId(fmsBankChequeDeposit.getBankId());
                fmsBankChequeDeposit.setAccountNumber(fmsBankChequeDeposit.getAccountNumber());
                fmsBankChequeDeposit.setChequeDepositDate(fmsBankChequeDeposit.getChequeDepositDate());
                fmsBankChequeDeposit.setChequeNumber(fmsBankChequeDeposit.getChequeNumber());
                chequeList = chequeDepositBeanLocal.searchAllByAcountNumberDateAndTranRef(fmsBankChequeDeposit);
                recreateChequeDataModel();
            } else {
                JsfUtil.addFatalMessage("Failed to search. Please try again.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            JsfUtil.addFatalMessage("Reload the page and try again.");
        }
    }

    //create cheque deposit
    public void ChequeDeposit() {
        //getCurrentBalance
        balance1 = fmsBankBranchAccounts.getBeginningBalance();
        amount = fmsBankChequeDeposit.getAmount();
        balance2 = balance1 + amount;
        //add amount to beginning balance
        fmsBankBranchAccounts.setBeginningBalance(balance2);
    }

    //update cheque deposit
    public void ChequeDepositUpdate() {
        //getCurrentBalance
        balance1 = fmsBankBranchAccounts.getBeginningBalance();
        prev_cheque_deposit_amt = prevChequeDepAmount;
        balance3 = balance1 - prev_cheque_deposit_amt;
        amount = fmsBankChequeDeposit.getAmount();
        balance4 = balance3 + amount;
        //add amount to beginning balance
        fmsBankBranchAccounts.setBeginningBalance(balance4);

    }

    //save method
    public String saveChequeDeposit() {
        AAA securityService = new AAA();
        IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
        String systemBundle = "et/gov/eep/commonApplications/securityServer";
        String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
        if (security.checkAccess(SessionBean.getUserName(), "saveChequeDeposit", dataset)) {
            if (updteStatus == 0) {
                if (debitAccCode.equalsIgnoreCase(creditAccCode)) {
                    JsfUtil.addFatalMessage("Debit/Credit is not Allowed To/From the Same Account Code!");
                    return null;
                }
                try {
                    fmsBankChequeDeposit.setBankId(fmsBankAccount.getBankId());
                    fmsBankChequeDeposit.setBranchName(fmsBankBranchAccounts.getBankAccountId().getBranchName());
                    fmsBankChequeDeposit.setAccountNumber(fmsBankBranchAccounts.getAccountNumber());
                    ChequeDeposit();
                    chequeDepositBeanLocal.create(fmsBankChequeDeposit);
                    branchAccountsBeanLocal.edit(fmsBankBranchAccounts);
                    //create coded-transaction data-debit-side
                    fmsCodedTransaction.setStatus(0);
                    fmsCodedTransaction.setRecon_status(0);
                    fmsCodedTransaction.setType("cheque");
                    fmsCodedTransaction.setCredit(fmsBankChequeDeposit.getAmount());
                    fmsCodedTransaction.setDebit(0.0);
                    fmsCodedTransaction.setTranRef(fmsBankChequeDeposit.getChequeNumber());
                    fmsCodedTransaction.setSubsidiaryId(fmsBankChequeDeposit.getAccountCode());
                    codedTransactionBeanLocal.create(fmsCodedTransaction);

                    //create coded-transaction data-credit-side
                    fmsCodedTransaction = new FmsCodedTransaction();
                    fmsCodedTransaction.setStatus(0);
                    fmsCodedTransaction.setRecon_status(0);
                    fmsCodedTransaction.setType("cheque");
                    fmsCodedTransaction.setCredit(0.00);
                    fmsCodedTransaction.setDebit(fmsBankChequeDeposit.getAmount());
                    fmsCodedTransaction.setTranRef(fmsBankChequeDeposit.getChequeNumber());
                    fmsCodedTransaction.setSubsidiaryId(fmsBankBranchAccounts.getSubsidiaryId().getSubsidiaryCode());
                    codedTransactionBeanLocal.create(fmsCodedTransaction);
                    JsfUtil.addSuccessMessage("Saved Successfully!");
                } catch (Exception e) {
                    JsfUtil.addFatalMessage("Failed to save! Try Again With Unique Chequee No.");
                    return null;
                }
            } else {
                try {
                    if (codedTranList.isEmpty()) {
                        JsfUtil.addFatalMessage("Update is not allowed!, JV has Already Done for this Transaction.");
                        return null;
                    } else {
                        fmsBankChequeDeposit.setBranchName(fmsBankBranchAccounts.getBankAccountId().getBranchName());
                        fmsBankChequeDeposit.setAccountNumber(fmsBankBranchAccounts.getAccountNumber());
                        ChequeDepositUpdate();
                        chequeDepositBeanLocal.edit(fmsBankChequeDeposit);

                        //update fmsBankAccount & add amount to prev. balance
                        branchAccountsBeanLocal.edit(fmsBankBranchAccounts);

                        //update coded-transaction-data debit-side
                        fmsCodedTransaction.setCodedTransactonId(codedTranList.get(0).getCodedTransactonId());
                        fmsCodedTransaction.setStatus(0);
                        fmsCodedTransaction.setRecon_status(0);
                        fmsCodedTransaction.setType("cheque");
                        fmsCodedTransaction.setCredit(fmsBankChequeDeposit.getAmount());
                        fmsCodedTransaction.setCredit(fmsBankChequeDeposit.getAmount());
                        fmsCodedTransaction.setDebit(0.0);
                        fmsCodedTransaction.setSubsidiaryId(fmsBankChequeDeposit.getAccountCode());
                        codedTransactionBeanLocal.edit(fmsCodedTransaction);

                        //update coded-transaction-data credit-side
                        fmsCodedTransaction = new FmsCodedTransaction();
                        fmsCodedTransaction.setCodedTransactonId(codedTranList.get(1).getCodedTransactonId());
                        fmsCodedTransaction.setStatus(0);
                        fmsCodedTransaction.setRecon_status(0);
                        fmsCodedTransaction.setType("cheque");
                        fmsCodedTransaction.setCredit(0.00);
                        fmsCodedTransaction.setDebit(fmsBankChequeDeposit.getAmount());
                        fmsCodedTransaction.setTranRef(fmsBankChequeDeposit.getChequeNumber());
                        fmsCodedTransaction.setSubsidiaryId(fmsBankBranchAccounts.getSubsidiaryId().getSubsidiaryCode());
                        codedTransactionBeanLocal.edit(fmsCodedTransaction);
                        JsfUtil.addSuccessMessage("Updated Successfully!");
                        saveorUpdateBundle = "Save";
                        isRequiredRdoOption = true;
                    }
                } catch (Exception e) {
                    JsfUtil.addFatalMessage("Failed to Update! Try again Reloading the Page.");
                }
            }
            clearPage();
            clearAccCodes();
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

    //clear
    public void clearPage() {
        fmsBankChequeDeposit = new FmsBankChequeDeposit();
        fmsBankAccount = new FmsBankAccount();
        fmsBankBranchAccounts = new FmsBankBranchAccounts();
        casherAccountEnty = new FmsCasherAccount();
        fmsCodedTransaction = new FmsCodedTransaction();
        chequeDepositDataModel = new ListDataModel<>();

    }

    //clear
    public void clearAccCodes() {
        fmsLuSystem = new FmsLuSystem();
        fmsGeneralLedger = new FmsGeneralLedger();
        fmsCostCenter = new FmsCostCenter();
        fmsCostcSystemJunction = new FmsCostcSystemJunction();
        fmsSystemJobJunction = new FmsSystemJobJunction();
        fmsSubsidiaryLedger = new FmsSubsidiaryLedger();
        debitAccCode = "";
        creditAccCode = "";
    }

    //create and search render method
    public void createNewDeposit() {
        clearPage();
        saveorUpdateBundle = "Save";
        disableBtnCreate = false;
        if (createOrSearchBundle.equals("New")) {
            renderPnlCreateCheque = true;
            renderPnlManPage = false;
            createOrSearchBundle = "Search";
            setIcone("ui-icon-search");
        } else if (createOrSearchBundle.equals("Search")) {
            renderPnlCreateCheque = false;
            renderPnlManPage = true;
            createOrSearchBundle = "New";
            setIcone("ui-icon-plus");
        }
    }
}
