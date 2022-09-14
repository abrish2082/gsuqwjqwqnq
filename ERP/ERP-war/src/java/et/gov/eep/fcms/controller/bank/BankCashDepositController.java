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
import et.gov.eep.fcms.businessLogic.bank.FmsCodedTransactionBeanLocal;
import et.gov.eep.fcms.businessLogic.admin.FmsGeneralLedgerBeanLocal;
import et.gov.eep.fcms.businessLogic.admin.FmsLuSystemBeanLocal;
import et.gov.eep.fcms.businessLogic.admin.subsidiaryBeanLocal;
import et.gov.eep.fcms.businessLogic.bank.bankAccountBeanLocal;
import et.gov.eep.fcms.businessLogic.bank.bankBranchAccountsBeanLocal;
import et.gov.eep.fcms.businessLogic.bank.bankCashDepositBeanLocal;
import et.gov.eep.fcms.businessLogic.bank.fms_BankBeanLocal;
import et.gov.eep.fcms.entity.bank.FmsBank;
import et.gov.eep.fcms.entity.bank.FmsBankAccount;
import et.gov.eep.fcms.entity.bank.FmsBankBranchAccounts;
import et.gov.eep.fcms.entity.bank.FmsBankCashDeposit;
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
@Named(value = "bankCashDepositController")
@ViewScoped
public class BankCashDepositController implements Serializable {
//<editor-fold defaultstate="collapsed" desc="Inject Entities and @ EJB">
    //Inject @EJB

    @EJB
    private bankCashDepositBeanLocal cashDepositBeanLocal;
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
    SessionBean SessionBean;
    @Inject
    private FmsCasherAccount casherAccountEnty;
    @Inject
    private FmsCodedTransaction fmsCodedTransaction;
    @Inject
    private FmsBankBranchAccounts fmsBankBranchAccounts;
    @Inject
    private FmsBankCashDeposit fmsBankCashDeposit;
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
//</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Variable declaration">
    FmsBankCashDeposit selectedFmsCashDeposit;
    FmsBankBranchAccounts beginningbalance;
    DataModel<FmsBankCashDeposit> cashDepositDataModel;
    List<FmsCasherAccount> casherAccountsList;
    List<FmsBankAccount> branchNames;
    List<FmsBankBranchAccounts> accountNumbers;
    List<FmsBankCashDeposit> transactionList;
    List<FmsBankCashDeposit> dates;
    List<FmsBankCashDeposit> cashList;
    List<FmsCodedTransaction> codedTransactionList;
    List<FmsCostcSystemJunction> ssCcJunctionList = new ArrayList<>();
    List<FmsSystemJobJunction> sysstemJobNumberList;
    List<FmsGeneralLedger> generalLedgerList;
    List<FmsSubsidiaryLedger> subsidiaryLedgerList;
    List<FmsLuSystem> systemList;
    String saveorUpdateBundle = "Save";
    private String tabIndex = "0";
    private String chooseSearchCredit = "display";
    private String chooseSearchDebit = "hidden";
    private String rdoCreditval = "rdoSearchCash";
    private String rdoDebitVal = "rdoSearchChequee";
    private String activeIndex;
    private String icone = "ui-icon-plus";
    private String createOrSearchBundle = "New";
    private String creditAccCode = "";
    private String debitAccCode = "";
    private String display_conn;
    private boolean disableBtnCreate;
    private boolean renderPnlCreateCash = false;
    private boolean renderPnlManPage = true;
    private boolean isDisabledCashier = true;
    private boolean isDisabledBtnAdd = true;
    private boolean isReadOnlyDeposeitedBy = true;
    private boolean isRequiredRdoOption = true;
    boolean renderJobNo = false;
    final Integer projectType = 2;
    final Integer nonProjectType = 1;
    int updteStatus = 0;
    double prevCashDepAmount = 0.0;
    private NumberConverter numberConverter = new NumberConverter();
//</editor-fold>

    public BankCashDepositController() {
        numberConverter.setPattern("#,##0.00##");
        numberConverter.setMinIntegerDigits(1);
        numberConverter.setMaxIntegerDigits(40);
        numberConverter.setMaxFractionDigits(3);
    }

    //<editor-fold defaultstate="collapsed" desc="Getter and Setter Methods">
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

    public List<FmsSystemJobJunction> getSysstemJobNumberList() {
        if (sysstemJobNumberList == null) {
            sysstemJobNumberList = new ArrayList<>();
        }
        return sysstemJobNumberList;
    }

    public void setSysstemJobNumberList(List<FmsSystemJobJunction> sysstemJobNumberList) {
        this.sysstemJobNumberList = sysstemJobNumberList;
    }

    public List<FmsGeneralLedger> getGeneralLedgerList() {
        if (generalLedgerList == null) {
            generalLedgerList = new ArrayList<>();
        }
        return generalLedgerList;
    }

    public void setGeneralLedgerList(List<FmsGeneralLedger> glList) {
        this.generalLedgerList = glList;
    }

    public List<FmsSubsidiaryLedger> getSubsidiaryLedgerListList() {
        if (subsidiaryLedgerList == null) {
            subsidiaryLedgerList = new ArrayList<>();
        }
        return subsidiaryLedgerList;
    }

    public void setSubsidiaryLedgerList(List<FmsSubsidiaryLedger> subsidiaryLedgerList) {
        this.subsidiaryLedgerList = subsidiaryLedgerList;
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

    public List<FmsBankBranchAccounts> getAccountNumbers() {
        if (accountNumbers == null) {
            accountNumbers = new ArrayList<>();
        }

        return accountNumbers;
    }

    public void setAccountNumbers(List<FmsBankBranchAccounts> accountNumbers) {
        this.accountNumbers = accountNumbers;
    }

    public FmsBankBranchAccounts getBeginningbalance() {
        if (beginningbalance == null) {
            beginningbalance = new FmsBankBranchAccounts();
        }
        return beginningbalance;
    }

    public void setBeginningbalance(FmsBankBranchAccounts beginningbalance) {
        this.beginningbalance = beginningbalance;
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

    public List<FmsBank> getBankNameList() {
        return bankBeanLocal.getBankName();
    }

    public List<FmsCasherAccount> getCasherNames() {
        return cashierAccountBeanLocal.getAllCashierName();
    }

    public fms_BankBeanLocal getBankBeanLocal() {
        return bankBeanLocal;
    }

    public void setBankBeanLocal(fms_BankBeanLocal bankBeanLocal) {
        this.bankBeanLocal = bankBeanLocal;
    }

    public double getPrevCashDepAmount() {
        return prevCashDepAmount;
    }

    public void setPrevCashDepAmount(double prevCashDepAmount) {
        this.prevCashDepAmount = prevCashDepAmount;
    }

    public List<FmsBankBranchAccounts> getBankAccountNo() {
        if (fmsBankAccount == null) {
            fmsBankAccount = new FmsBankAccount();
        }
        return branchAccountsBeanLocal.getBankAccountNo();
    }

    public List<FmsBankCashDeposit> getTransactionList() {

        return transactionList;
    }

    public void setTransactionList(List<FmsBankCashDeposit> transactionList) {
        this.transactionList = transactionList;
    }

    public List<FmsBankCashDeposit> getDates() {
        if (dates == null) {
            dates = new ArrayList<>();
        }
        return dates;
    }

    public void setDates(List<FmsBankCashDeposit> dates) {
        this.dates = dates;
    }

    public List<FmsCodedTransaction> getCodedTransactionList() {
        return codedTransactionList;
    }

    public void setCodedTransactionList(List<FmsCodedTransaction> codedTransactionList) {
        this.codedTransactionList = codedTransactionList;
    }

    public List<FmsBankCashDeposit> getCashList() {
        return cashList;
    }

    public void setCashList(List<FmsBankCashDeposit> cashList) {
        this.cashList = cashList;
    }

    public FmsBankCashDeposit getSelectedFmsCashDeposit() {
        return selectedFmsCashDeposit;
    }

    public void setSelectedFmsCashDeposit(FmsBankCashDeposit selectedFmsCashDeposit) {
        this.selectedFmsCashDeposit = selectedFmsCashDeposit;
    }

    public String getActiveIndex() {
        return activeIndex;
    }

    public void setActiveIndex(String activeIndex) {
        this.activeIndex = activeIndex;
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

    public boolean isRenderPnlCreateCash() {
        return renderPnlCreateCash;
    }

    public void setRenderPnlCreateCash(boolean renderPnlCreateCash) {
        this.renderPnlCreateCash = renderPnlCreateCash;
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

    public DataModel<FmsBankCashDeposit> getCashDepositDataModel() {
        if (cashDepositDataModel == null) {
            cashDepositDataModel = new ListDataModel<>();
        }
        return cashDepositDataModel;
    }

    public void setCashDepositDataModel(DataModel<FmsBankCashDeposit> cashDepositDataModel) {
        this.cashDepositDataModel = cashDepositDataModel;
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

    public String getRdoSearchCredit() {
        return rdoCreditval;
    }

    public void setRdoSearchCredit(String rdoSearchCredit) {
        this.rdoCreditval = rdoSearchCredit;
    }

    public String getRdoSearchDebit() {
        return rdoDebitVal;
    }

    public void setRdoSearchDebit(String rdoSearchDebit) {
        this.rdoDebitVal = rdoSearchDebit;
    }

    public String getChooseSearchCredit() {
        return chooseSearchCredit;
    }

    public void setChooseSearchCredit(String chooseSearchCredit) {
        this.chooseSearchCredit = chooseSearchCredit;
    }

    public String getChooseSearchDebit() {
        return chooseSearchDebit;
    }

    public void setChooseSearchDebit(String chooseSearchDebit) {
        this.chooseSearchDebit = chooseSearchDebit;
    }

    public String getRdoCreditval() {
        return rdoCreditval;
    }

    public void setRdoCreditval(String rdoCreditval) {
        this.rdoCreditval = rdoCreditval;
    }

    public String getRdoDebitVal() {
        return rdoDebitVal;
    }

    public void setRdoDebitVal(String rdoDebitVal) {
        this.rdoDebitVal = rdoDebitVal;
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

    public FmsBankAccount getFmsBankAccount() {

        if (fmsBankAccount == null) {
            fmsBankAccount = new FmsBankAccount();
        }
        return fmsBankAccount;
    }

    public int getUpdteStatus() {
        return updteStatus;
    }

    public void setUpdteStatus(int updteStatus) {
        this.updteStatus = updteStatus;
    }

    public void setFmsBankAccount(FmsBankAccount fmsBankAccount) {
        this.fmsBankAccount = fmsBankAccount;
    }

    public bankCashDepositBeanLocal getCashDepositBeanLocal() {
        return cashDepositBeanLocal;
    }

    public void setCashDepositBeanLocal(bankCashDepositBeanLocal cashDepositBeanLocal) {
        this.cashDepositBeanLocal = cashDepositBeanLocal;
    }

    public bankAccountBeanLocal getBankAccBeanLocal() {
        return bankAccBeanLocal;
    }

    public void setBankAccBeanLocal(bankAccountBeanLocal bankAccBeanLocal) {
        this.bankAccBeanLocal = bankAccBeanLocal;
    }

    public FmsBankCashDeposit getFmsBankCashDeposit() {
        if (fmsBankCashDeposit == null) {
            fmsBankCashDeposit = new FmsBankCashDeposit();
        }
        return fmsBankCashDeposit;
    }

    public FmsBank getFmsBank() {

        if (fmsBank == null) {
            fmsBank = new FmsBank();
        }
        return fmsBank;
    }

    public void setFmsBank(FmsBank fmsBank) {
        this.fmsBank = fmsBank;
    }

    public void setFmsBankCashDeposit(FmsBankCashDeposit fmsBankCashDeposit) {
        this.fmsBankCashDeposit = fmsBankCashDeposit;
    }

    public List<FmsBankAccount> getBankName() {
        return bankAccBeanLocal.getBankName();
    }

    public List<FmsBankAccount> getBranchName() {
        return bankAccBeanLocal.getBranchName();
    }

    public String getSaveorUpdateBundle() {
        return saveorUpdateBundle;
    }

    public void setSaveorUpdateBundle(String saveorUpdateBundle) {
        this.saveorUpdateBundle = saveorUpdateBundle;
    }
//</editor-fold>

    //save cash deposit
    public String saveCashDeposit() {
        AAA securityService = new AAA();
        IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
        String systemBundle = "et/gov/eep/commonApplications/securityServer";
        String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
        if (security.checkAccess(SessionBean.getUserName(), "saveCashDeposit", dataset)) {
            double balance1 = 0.0;
            double amount = 0.0;
            double balance2 = 0.0;
            double prev_cash_deposit_amt = 0.0;
            double balance3 = 0.0;
            double balance4 = 0.0;
            if (updteStatus == 0) {
                if (debitAccCode.equalsIgnoreCase(creditAccCode)) {
                    JsfUtil.addFatalMessage("Debit/Credit is not Allowed To/From the Same Account Code!");
                    return null;
                }
                try {
                    //get bankName, branchName, AccountNumber, beginningBalance from fmsBankAccount and set to fmsBankCashDeposit
                    fmsBankCashDeposit.setBankId(fmsBankAccount.getBankId());
                    fmsBankCashDeposit.setBranchName(fmsBankBranchAccounts.getBankAccountId().getBranchName());
                    fmsBankCashDeposit.setAccountNumber(fmsBankBranchAccounts.getAccountNumber());
                    if (fmsBankCashDeposit.getAccountCode() == null) {
                        JsfUtil.addFatalMessage("Add Debit Account code.");
                        return null;
                    }
                    //setBeginningBallance
                    balance1 = fmsBankBranchAccounts.getBeginningBalance();
                    amount = fmsBankCashDeposit.getAmount();
                    balance2 = balance1 + amount;

                    //add amount to beginning balance
                    fmsBankBranchAccounts.setBeginningBalance(balance2);
                    cashDepositBeanLocal.create(fmsBankCashDeposit);

                    //update fmsBankAccount and add amount to beginning balance
                    branchAccountsBeanLocal.edit(fmsBankBranchAccounts);

                    //create coded-transaction data debit-side
                    fmsCodedTransaction.setStatus(0);
                    fmsCodedTransaction.setRecon_status(0);
                    fmsCodedTransaction.setType("cash");
                    fmsCodedTransaction.setCredit(fmsBankCashDeposit.getAmount());
                    fmsCodedTransaction.setDebit(0.00);
                    fmsCodedTransaction.setTranRef(fmsBankCashDeposit.getTranRef());
                    fmsCodedTransaction.setSubsidiaryId(fmsBankCashDeposit.getAccountCode());
                    codedTransactionBeanLocal.create(fmsCodedTransaction);

                    //create coded-transactiondata credit-side
                    fmsCodedTransaction = new FmsCodedTransaction();
                    fmsCodedTransaction.setStatus(0);
                    fmsCodedTransaction.setRecon_status(0);
                    fmsCodedTransaction.setType("cash");
                    fmsCodedTransaction.setCredit(0.00);
                    fmsCodedTransaction.setDebit(fmsBankCashDeposit.getAmount());
                    fmsCodedTransaction.setTranRef(fmsBankCashDeposit.getTranRef());
                    fmsCodedTransaction.setSubsidiaryId(fmsBankBranchAccounts.getSubsidiaryId().getSubsidiaryCode());
                    codedTransactionBeanLocal.create(fmsCodedTransaction);
                    JsfUtil.addSuccessMessage("Saved Successfully!");
                } catch (Exception e) {
                    JsfUtil.addFatalMessage("Failed to Save! Try Again Using Unique Tran. Ref. Number");
                    return null;
                }
            } else {
                try {
                    if (codedTransactionList.isEmpty()) {
                        JsfUtil.addFatalMessage("Update is not Allowed! JV has Already Done for this Transaction.");
                        return null;
                    } else {
                        fmsBankCashDeposit.setBankId(fmsBankAccount.getBankId());
                        fmsBankCashDeposit.setBranchName(fmsBankBranchAccounts.getBankAccountId().getBranchName());
                        fmsBankCashDeposit.setAccountNumber(fmsBankBranchAccounts.getAccountNumber());

                        //setBeginningBallance
                        balance1 = fmsBankBranchAccounts.getBeginningBalance();
                        prev_cash_deposit_amt = prevCashDepAmount;
                        balance3 = balance1 - prev_cash_deposit_amt;
                        amount = fmsBankCashDeposit.getAmount();
                        balance4 = balance3 + amount;

                        //add amount to beginning balance
                        fmsBankBranchAccounts.setBeginningBalance(balance4);
                        cashDepositBeanLocal.edit(fmsBankCashDeposit);

                        //update fmsBankAccount & add amount to prev. balance
                        branchAccountsBeanLocal.edit(fmsBankBranchAccounts);

                        //update coded-transaction-data debit-side
                        fmsCodedTransaction.setCodedTransactonId(codedTransactionList.get(0).getCodedTransactonId());
                        fmsCodedTransaction.setStatus(0);
                        fmsCodedTransaction.setRecon_status(0);
                        fmsCodedTransaction.setType("cash");
                        fmsCodedTransaction.setCredit(0.00);
                        fmsCodedTransaction.setDebit(fmsBankCashDeposit.getAmount());
                        fmsCodedTransaction.setTranRef(fmsBankCashDeposit.getTranRef());
                        fmsCodedTransaction.setSubsidiaryId(fmsBankCashDeposit.getAccountCode());
                        codedTransactionBeanLocal.edit(fmsCodedTransaction);

                        //update coded-transaction-data credit-side
                        fmsCodedTransaction = new FmsCodedTransaction();
                        fmsCodedTransaction.setCodedTransactonId(codedTransactionList.get(1).getCodedTransactonId());
                        fmsCodedTransaction.setStatus(0);
                        fmsCodedTransaction.setRecon_status(0);
                        fmsCodedTransaction.setType("cash");
                        fmsCodedTransaction.setCredit(fmsBankCashDeposit.getAmount());
                        fmsCodedTransaction.setDebit(0.00);
                        fmsCodedTransaction.setTranRef(fmsBankCashDeposit.getTranRef());
                        fmsCodedTransaction.setSubsidiaryId(fmsBankBranchAccounts.getSubsidiaryId().getSubsidiaryCode());
                        codedTransactionBeanLocal.edit(fmsCodedTransaction);
                        JsfUtil.addSuccessMessage("Updated Successfully!");
                        saveorUpdateBundle = "Save";
                        isRequiredRdoOption = true;
                    }
                } catch (Exception e) {
                    JsfUtil.addFatalMessage("Failed to Update! Please try Again Reloading the Page.");
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

//cocatination of systemcode,general leder code,account code
    public void Concatenate1() {
        try {
            String accountCode[] = fmsBankBranchAccounts.getSubsidiaryId().getSubsidiaryCode().split("-");
            fmsLuSystem.setSystemId(Integer.parseInt(accountCode[0]));
            fmsLuSystem = fmsLuSystemBeanLocal.getSystembyId(fmsLuSystem);
            String SL_display = "SL";
            fmsGeneralLedger.setGeneralLedgerId(Integer.parseInt(accountCode[2]));
            fmsGeneralLedger = fmsGeneralLedgerBeanLocal.getByGlId(fmsGeneralLedger);
            if (!"SL".equals(accountCode[0])) {
                fmsSubsidiaryLedger.setSubsidiaryId(Integer.parseInt(accountCode[3]));
                fmsSubsidiaryLedger = subsidiaryLedgerBeanLocal.getSubsidiaryInfo(fmsSubsidiaryLedger);
                SL_display = fmsSubsidiaryLedger.getSubsidiaryCode();
            }
            debitAccCode = fmsLuSystem.getSystemCode() + "-" + accountCode[1] + "-" + fmsGeneralLedger.getGeneralLedgerCode() + "-" + SL_display;
            fmsBankBranchAccounts.setDisplay_conn(debitAccCode);
        } catch (Exception e) {
        }
    }

    //cocatination of system code,general leder code,cashier account code
    public void Concatenate2() {
        String cashierccountCode[] = fmsBankCashDeposit.getAccountCode().split("-");
        fmsLuSystem.setSystemId(Integer.parseInt(cashierccountCode[0]));
        fmsLuSystem = fmsLuSystemBeanLocal.getSystembyId(fmsLuSystem);
        String SL_display = "SL";
        fmsGeneralLedger.setGeneralLedgerId(Integer.parseInt(cashierccountCode[2]));
        fmsGeneralLedger = fmsGeneralLedgerBeanLocal.getByGlId(fmsGeneralLedger);
        if (!"SL".equals(cashierccountCode[0])) {
            fmsSubsidiaryLedger.setSubsidiaryId(Integer.parseInt(cashierccountCode[3]));
            fmsSubsidiaryLedger = subsidiaryLedgerBeanLocal.getSubsidiaryInfo(fmsSubsidiaryLedger);
            SL_display = fmsSubsidiaryLedger.getSubsidiaryCode();
        }
        creditAccCode = fmsLuSystem.getSystemCode() + "-" + cashierccountCode[1] + "-" + fmsGeneralLedger.getGeneralLedgerCode() + "-" + SL_display;
        fmsBankCashDeposit.setDisplay_conn(creditAccCode);
    }

//concationation 3
    public void Concatenate3() {
        for (int i = 0; i < cashList.size(); i++) {
            String cashierccountCode[] = cashList.get(i).getAccountCode().split("-");
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
            fmsBankCashDeposit.setDisplay_conn(display_conn);
            cashList.get(i).setDisplay_conn(display_conn);
        }
    }

//concatination for desplay-conn. and caredit advice
    public void Concatenate() {
        try {
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
        } catch (Exception e) {
        }
    }

    //recreate method to assign cashList value to cashDepositDataModel
    private void recreateCashDataModel() {
        cashDepositDataModel = null;
        cashDepositDataModel = new ListDataModel(new ArrayList(cashList));
    }

    //recreate method to assign cashList value to cashDepositDataModel
    public void recreateDataModel() {
        cashDepositDataModel = null;
        cashDepositDataModel = new ListDataModel(new ArrayList(cashList));
    }

    //populate
    public void populate(SelectEvent event) {
        isRequiredRdoOption = false;
        fmsBankCashDeposit = (FmsBankCashDeposit) event.getObject();
        fmsBankCashDeposit.setDisplay_conn(fmsBankCashDeposit.getAccountCode());
        fmsCodedTransaction.setTranRef(fmsBankCashDeposit.getTranRef());
        codedTransactionList = codedTransactionBeanLocal.getCodedTransactionsByTranRef(fmsCodedTransaction);
        fmsBank = fmsBankCashDeposit.getBankId();
        branchNames = fmsBank.getFmsBankAccountList();
        for (int i = 0; i < branchNames.size(); i++) {
            if (branchNames.get(i).getBranchName().equals(fmsBankCashDeposit.getBranchName())) {
                fmsBankAccount = branchNames.get(i);
                fmsBankAccount.setBankAccountId(branchNames.get(i).getBankAccountId());
                accountNumbers = fmsBankAccount.getFmsBankBranchAccountsList();
                break;
            }
        }
        for (int j = 0; j < accountNumbers.size(); j++) {
            if (accountNumbers.get(j).getAccountNumber().equals(fmsBankCashDeposit.getAccountNumber())) {
                fmsBankBranchAccounts = accountNumbers.get(j);
                break;
            }
        }
        prevCashDepAmount = fmsBankCashDeposit.getAmount();
        updteStatus = 1;
        saveorUpdateBundle = "Update";
        tabIndex = "0";
        renderPnlCreateCash = true;
        renderPnlManPage = false;
        activeIndex = "0";
        saveorUpdateBundle = "Update";
        createOrSearchBundle = "Search";
        setIcone("ui-icon-search");
        disableBtnCreate = true;
    }

    //add method for fmsBankCashDeposit
    public String addAccountInfoDetail() {
        try {
            display_conn = fmsSubsidiaryLedger.getSubsidiaryCode();
            fmsBankCashDeposit.setAccountCode(display_conn);
            fmsBankCashDeposit.setDisplay_conn(display_conn);
            creditAccCode = fmsSubsidiaryLedger.getSubsidiaryCode();
        } catch (Exception e) {
        }
        return null;
    }

    //search for account type(ProjectType)
    public void searchProjectType() {
        generalLedgerList = fmsGeneralLedgerBeanLocal.getByAccountType(projectType);
    }

    // search for acc type( nonProjectType)
    public void searchNonProjectType() {
        generalLedgerList = fmsGeneralLedgerBeanLocal.getByAccountType(nonProjectType);
    }

    //Search Cash Deposit
    public void searchCashDep() {
        cashList = new ArrayList<>();
        try {
            //find all
            if ((fmsBankCashDeposit.getAccountNumber().isEmpty()) && (fmsBankCashDeposit.getCashDepositDate() == null) && (fmsBankCashDeposit.getTranRef().isEmpty())) {
                cashList = cashDepositBeanLocal.searchAllCashs(fmsBankCashDeposit);
                recreateCashDataModel();
                //find by account number only
            } else if (!(fmsBankCashDeposit.getAccountNumber().isEmpty()) && (fmsBankCashDeposit.getCashDepositDate() == null) && (fmsBankCashDeposit.getTranRef().isEmpty())) {
                fmsBankCashDeposit.setCashDepositId(fmsBankCashDeposit.getCashDepositId());
                fmsBankCashDeposit.setBankId(fmsBankCashDeposit.getBankId());
                fmsBankCashDeposit.setAccountNumber(fmsBankCashDeposit.getAccountNumber());
                cashList = cashDepositBeanLocal.getCashDepositDate(fmsBankCashDeposit);
                recreateCashDataModel();

                //find by account number and date only
            } else if (!(fmsBankCashDeposit.getAccountNumber().isEmpty()) && !(fmsBankCashDeposit.getCashDepositDate() == null) && (fmsBankCashDeposit.getTranRef().isEmpty())) {
                fmsBankCashDeposit.setCashDepositId(fmsBankCashDeposit.getCashDepositId());
                fmsBankCashDeposit.setBankId(fmsBankCashDeposit.getBankId());
                fmsBankCashDeposit.setAccountNumber(fmsBankCashDeposit.getAccountNumber());
                fmsBankCashDeposit.setCashDepositDate(fmsBankCashDeposit.getCashDepositDate());
                cashList = cashDepositBeanLocal.getCashDepositTransactions(fmsBankCashDeposit);
                recreateCashDataModel();

                //find by account number, date and transaction ref-no.
            } else if (!(fmsBankCashDeposit.getAccountNumber().isEmpty()) && !(fmsBankCashDeposit.getCashDepositDate() == null) && !(fmsBankCashDeposit.getTranRef().isEmpty())) {
                fmsBankCashDeposit.setCashDepositId(fmsBankCashDeposit.getCashDepositId());
                fmsBankCashDeposit.setBankId(fmsBankCashDeposit.getBankId());
                fmsBankCashDeposit.setAccountNumber(fmsBankCashDeposit.getAccountNumber());
                fmsBankCashDeposit.setCashDepositDate(fmsBankCashDeposit.getCashDepositDate());
                fmsBankCashDeposit.setTranRef(fmsBankCashDeposit.getTranRef());
                cashList = cashDepositBeanLocal.searchAllByAcountNumberDateAndTranRef(fmsBankCashDeposit);
                recreateCashDataModel();
            } else {
                JsfUtil.addFatalMessage("Failed to Search. Try again reloading the page.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            JsfUtil.addFatalMessage("Reload the page and try again.");
        }
    }

    //aray list to search cashDepositBeanLocal and return CashDepositByDate
    public ArrayList<FmsBankCashDeposit> searchCashDepositByDate(Date cashDepositDate) {
        ArrayList<FmsBankCashDeposit> cashDeposits = null;
        fmsBankCashDeposit.setCashDepositDate(cashDepositDate);
        cashDeposits = cashDepositBeanLocal.searchCashDepositByDate(fmsBankCashDeposit);
        return cashDeposits;
    }

    //select item to find subsidaryList
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

    //select item to find all from fmsGeneralLedgerBeanLocal
    public SelectItem[] getGLdata() {
        return JsfUtil.getSelectItems(fmsGeneralLedgerBeanLocal.findAll(), true);
    }

    //select item to find all from fmsLuSystemBeanLocal
    public SelectItem[] getListSys() {
        return JsfUtil.getSelectItems(fmsLuSystemBeanLocal.findAll(), true);
    }

    //select item to find all from subsidiaryLedgerBeanLocal
    public SelectItem[] getSubList() {
        return JsfUtil.getSelectItems(subsidiaryLedgerBeanLocal.findAll(), true);
    }

    //select event to get branch name from bankAccBeanLocal
    public void getBankCashDepositInfo(SelectEvent event) {
        fmsBankCashDeposit = cashDepositBeanLocal.getBankCashDepositInfo(fmsBankCashDeposit);
        fmsBank = (FmsBank) fmsBankCashDeposit.getBankId();
        fmsBankAccount.setBankId(fmsBank);
        fmsBankAccount.setBranchName(fmsBankCashDeposit.getBranchName());
        branchNames = bankAccBeanLocal.getBranchNameById(fmsBankAccount);
        fmsBankAccount.setBranchName(fmsBankCashDeposit.getBranchName());
        updteStatus = 1;
        saveorUpdateBundle = "Update";
    }

    //value change event to get balanch from branchAccountsBeanLocal

    public void getBalance(ValueChangeEvent event) {
        fmsBankBranchAccounts = branchAccountsBeanLocal.getBalanceByAcctNo(fmsBankBranchAccounts);
        debitAccCode = fmsBankBranchAccounts.getSubsidiaryId().getSubsidiaryCode();
    }

    //value change event to get system detail from fmsLuSystemBeanLocal
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

    //value change event to get GL detail from fmsGeneralLedgerBeanLocal
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

    //value change event to get subsidery info from subsidiaryLedgerBeanLocal
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

    //value change event
    public void handleCahierCode(ValueChangeEvent event) {
        casherAccountEnty = cashierAccountBeanLocal.getSlcode(casherAccountEnty);
        fmsBankCashDeposit.setDisplay_conn(casherAccountEnty.getSubsidiaryId().getSubsidiaryCode());
        fmsBankCashDeposit.setAccountCode(casherAccountEnty.getSubsidiaryId().getSubsidiaryCode());
        fmsBankCashDeposit.setDepositedBy(casherAccountEnty.getEmpCode().getFirstName() + " " + casherAccountEnty.getEmpCode().getMiddleName());
        creditAccCode = casherAccountEnty.getSubsidiaryId().getSubsidiaryCode();
    }

    //value change event
    public FmsBankCashDeposit getAllDataFromCashDeposti(ValueChangeEvent event) {
        fmsBankCashDeposit.setCashDepositDate(fmsBankCashDeposit.getCashDepositDate());
        fmsBankCashDeposit = cashDepositBeanLocal.getData(fmsBankCashDeposit);
        fmsCodedTransaction.setTranRef(fmsBankCashDeposit.getTranRef());
        codedTransactionList = codedTransactionBeanLocal.getCodedTransactionsByTranRef(fmsCodedTransaction);
        fmsBankAccount.setBranchName(fmsBankCashDeposit.getBranchName());
        fmsBank = fmsBankCashDeposit.getBankId();
        casherAccountEnty = cashierAccountBeanLocal.getSlcode(casherAccountEnty);
        branchNames = fmsBank.getFmsBankAccountList();
        for (int i = 0; i < branchNames.size(); i++) {
            if (branchNames.get(i).getBranchName().equals(fmsBankCashDeposit.getBranchName())) {
                fmsBankAccount = branchNames.get(i);
                fmsBankAccount.setBankAccountId(branchNames.get(i).getBankAccountId());
                accountNumbers = fmsBankAccount.getFmsBankBranchAccountsList();
                break;
            }
        }
        for (int j = 0; j < accountNumbers.size(); j++) {
            if (accountNumbers.get(j).getAccountNumber().equals(fmsBankCashDeposit.getAccountNumber())) {
                fmsBankBranchAccounts = accountNumbers.get(j);
                break;
            }
        }
        fmsBankAccount.setBankId(fmsBank);
        fmsBankCashDeposit.setAccountNumber(fmsBankCashDeposit.getAccountNumber());
        prevCashDepAmount = fmsBankCashDeposit.getAmount();
        updteStatus = 1;
        saveorUpdateBundle = "Update";
        tabIndex = "0";
        return fmsBankCashDeposit;
    }

    //value change event
    public void isDisabledCashierEvent(ValueChangeEvent event) {
        fmsBankCashDeposit.setDepositedBy("");
        fmsBankCashDeposit.setDisplay_conn("");
        if (fmsBankCashDeposit.getDepositedByOption().equalsIgnoreCase("cashier")) {
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

    //value change event
    public FmsBankAccount getAccountNumber(ValueChangeEvent event) {
        fmsBankAccount.setBranchName(fmsBankAccount.getBranchName());
        fmsBankAccount.setBankId(fmsBankAccount.getBankId());
        return fmsBankAccount;
    }

    //get Branch name from bank account
    public List<FmsBankAccount> getBankBranchFromBankAccount(ValueChangeEvent event) {
        fmsBankAccount.setBankId(fmsBankAccount.getBankId());
        branchNames = bankAccBeanLocal.getBranchNameById(fmsBankAccount);
        return bankAccBeanLocal.getBranchNameById(fmsBankAccount);
    }

    //value change event
    public void getAccountNumberFromBranchAccounts(ValueChangeEvent event) {
        fmsBankBranchAccounts.setBankAccountId(fmsBankAccount);
        accountNumbers = branchAccountsBeanLocal.getAccountNumberByBankAccountId(fmsBankBranchAccounts);
    }

    //value change event
    public List<FmsBankCashDeposit> getTransactionFromCashDeposit(ValueChangeEvent event) {
        transactionList = cashDepositBeanLocal.getCashDepositTransactions(fmsBankCashDeposit);
        return cashDepositBeanLocal.getCashDepositTransactions(fmsBankCashDeposit);
    }

    //value change event
    public List<FmsBankCashDeposit> getDateFromAccount(ValueChangeEvent event) {
        dates = cashDepositBeanLocal.getCashDepositDate(fmsBankCashDeposit);
        return cashDepositBeanLocal.getCashDepositDate(fmsBankCashDeposit);
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
                    sysstemJobNumberList = fmsLuSystem.getFmsSystemJobJunctionList();
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

    //value change event for CostCenter change
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

    //value change event for GL change
    public void onChangeGeneralLedger(ValueChangeEvent event) {
        try {
            if (!event.getNewValue().toString().isEmpty()) {
                fmsGeneralLedger.setGeneralLedgerCode(event.getNewValue().toString());
                fmsGeneralLedger = fmsGeneralLedgerBeanLocal.getGLDetail(fmsGeneralLedger);
                fmsGeneralLedger = (FmsGeneralLedger) event.getNewValue();
                subsidiaryLedgerList = subsidiaryLedgerBeanLocal.findBySsCcJuncAndGL(fmsCostcSystemJunction, fmsGeneralLedger);
            }
        } catch (Exception e) {
            JsfUtil.addFatalMessage("Unable to process. Try again reloading the page.");
        }
    }

    //value change event for subsideryLedger change
    public void onChangeSubsidiaryLedger(ValueChangeEvent event) {
        try {
            if (!event.getNewValue().toString().isEmpty()) {
                fmsSubsidiaryLedger = (FmsSubsidiaryLedger) event.getNewValue();
            }
        } catch (Exception e) {
            JsfUtil.addFatalMessage("Unable to process. Try again reloading the page.");
        }
    }

    //value change event for job number change
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

    //select event for render value
    public String getradioHandiler(SelectEvent event) {
        String slected = fmsBankCashDeposit.getReason();
        String tabToReturn = "tabCashDeposit";
        if (slected.equalsIgnoreCase("rdoSearchCash")) {
            chooseSearchCredit = "display";
            chooseSearchDebit = "hidden";
            tabToReturn = "tabCashDeposit";
        } else {
            chooseSearchDebit = "display";
            chooseSearchCredit = "hidden";
            tabToReturn = "tabChequeDeposit";
        }
        return tabToReturn;
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

    //clear
    public void clearPage() {
        fmsBankCashDeposit = new FmsBankCashDeposit();
        fmsBankAccount = new FmsBankAccount();
        fmsBankBranchAccounts = new FmsBankBranchAccounts();
        casherAccountEnty = new FmsCasherAccount();
        fmsCodedTransaction = new FmsCodedTransaction();
        cashDepositDataModel = new ListDataModel<>();
        clearAccCodes();

    }

    //create and search render method
    public void createNewDeposit() {
        clearPage();
        saveorUpdateBundle = "Save";
        disableBtnCreate = false;
        if (createOrSearchBundle.equals("New")) {
            renderPnlCreateCash = true;
            renderPnlManPage = false;
            createOrSearchBundle = "Search";
            setIcone("ui-icon-search");
        } else if (createOrSearchBundle.equals("Search")) {
            renderPnlCreateCash = false;
            renderPnlManPage = true;
            createOrSearchBundle = "New";
            setIcone("ui-icon-plus");
        }
    }

    //render value for edit
    public void onRowEditAdd() {
        renderPnlCreateCash = true;
        renderPnlManPage = false;
        activeIndex = "0";
        saveorUpdateBundle = "Update";
        createOrSearchBundle = "Search";
        setIcone("ui-icon-search");
        disableBtnCreate = true;
        updteStatus = 1;
    }

}
