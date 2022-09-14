/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.controller.bank;

import java.util.List;
import javax.ejb.EJB;
import javax.faces.event.ValueChangeEvent;
import java.io.Serializable;
import java.util.ArrayList;
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
import et.gov.eep.fcms.businessLogic.bank.FmsCodedTransactionBeanLocal;
import et.gov.eep.fcms.businessLogic.admin.FmsCostCenterBeanLocal;
import et.gov.eep.fcms.businessLogic.admin.FmsGeneralLedgerBeanLocal;
import et.gov.eep.fcms.businessLogic.admin.FmsLuSystemBeanLocal;
import et.gov.eep.fcms.businessLogic.admin.subsidiaryBeanLocal;
import et.gov.eep.fcms.businessLogic.bank.bankAccountBeanLocal;
import et.gov.eep.fcms.businessLogic.bank.bankBranchAccountsBeanLocal;
import et.gov.eep.fcms.businessLogic.bank.bankDebitAdviceBeanLocal;
import et.gov.eep.fcms.businessLogic.bank.fms_BankBeanLocal;
import et.gov.eep.fcms.entity.bank.FmsBank;
import et.gov.eep.fcms.entity.bank.FmsBankAccount;
import et.gov.eep.fcms.entity.bank.FmsBankBranchAccounts;
import et.gov.eep.fcms.entity.bank.FmsBankDebitAdvice;
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
@Named(value = "bankDebitAdviceController")
@ViewScoped
public class BankDebitAdviceController implements Serializable {
//<editor-fold defaultstate="collapsed" desc="Inject Entities and @ EJB">
    //Inject @EJB

    @EJB
    FmsCostCenterBeanLocal fmsCostCenterBeanLocal;
    @EJB
    private bankDebitAdviceBeanLocal debitAdviceBeanLocal;
    @EJB
    private bankAccountBeanLocal bankAccBeanLocal;
    @EJB
    private fms_BankBeanLocal bankBeanLocal;
    @EJB
    private bankBranchAccountsBeanLocal branchAccountsBeanLocal;
    @EJB
    private FmsCodedTransactionBeanLocal codedTransactionBeanLocal;
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
    private FmsBank fmsBank;
    @Inject
    private FmsBankAccount fmsBankAccount;
    @Inject
    private FmsBankBranchAccounts fmsBankBranchAccounts;
    @Inject
    FmsBankDebitAdvice fmsBankDebitAdvice;
    @Inject
    FmsCodedTransaction fmsCodedTransaction;
    @Inject
    SessionBean SessionBean;
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
    //<editor-fold defaultstate="collapsed" desc="variables declaration">
    //Declaring instance variables
    DataModel<FmsBankDebitAdvice> debitDataModel;
    List<FmsCostcSystemJunction> ssCcJunctionList = new ArrayList<>();
    List<FmsSystemJobJunction> sysJobNOList;
    List<FmsGeneralLedger> glList;
    List<FmsSubsidiaryLedger> slList;
    List<FmsLuSystem> systemList;
    List<FmsCodedTransaction> codedTranList;
    List<FmsBankDebitAdvice> dates;
    List<FmsBankDebitAdvice> transactionList;
    List<FmsBankBranchAccounts> accountNumbers;
    List<FmsBankAccount> branchNames;
    final Integer projectType = 2;
    final Integer nonProjectType = 1;
    int updteStatus = 0;
    String saveorUpdateBundle = "Save";
    private String activeIndex;
    private String tabIndex = "0";
    private String icone = "ui-icon-plus";
    private String createOrSearchBundle = "New";
    private String Conc = "";
    private String creditAccCode = "";
    private String debitAccCode = "";
    private String display_conn;
    boolean renderJobNo = false;
    private boolean disableBtnCreate;
    private boolean renderPnlCreateDebit = false;
    private boolean renderPnlManPage = true;
    private NumberConverter numberConverter = new NumberConverter();
//</editor-fold>

    public BankDebitAdviceController() {
        numberConverter.setPattern("#,##0.00##");
        numberConverter.setMinIntegerDigits(1);
        numberConverter.setMaxIntegerDigits(40);
        numberConverter.setMaxFractionDigits(3);
    }

    //<editor-fold defaultstate="collapsed" desc="Getter and Setter Methods">
    public List<FmsBankBranchAccounts> getBankAccountNo() {
        if (fmsBankAccount == null) {
            fmsBankAccount = new FmsBankAccount();
        }
        return branchAccountsBeanLocal.getBankAccountNo();
    }

    public List<FmsBankDebitAdvice> getTransactionList() {

        return transactionList;
    }

    public void setTransactionList(List<FmsBankDebitAdvice> transactionList) {
        this.transactionList = transactionList;
    }

    public List<FmsBankBranchAccounts> getAccountNumbers() {
        if (accountNumbers == null) {
            accountNumbers = new ArrayList<>();
        }
        return accountNumbers;
    }

    public List<FmsBankDebitAdvice> getDates() {
        if (dates == null) {
            dates = new ArrayList<>();
        }
        return dates;
    }

    public void setDates(List<FmsBankDebitAdvice> dates) {
        this.dates = dates;
    }

    double prevCreditAmount = 0.0;

    public double getPrevCreditAmount() {
        return prevCreditAmount;
    }

    public void setPrevCreditAmount(double prevCreditAmount) {
        this.prevCreditAmount = prevCreditAmount;
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

    public List<FmsCodedTransaction> getCodedTranList() {
        return codedTranList;
    }

    public void setCodedTranList(List<FmsCodedTransaction> codedTranList) {
        this.codedTranList = codedTranList;
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

    public DataModel<FmsBankDebitAdvice> getDebitDataModel() {
        if (debitDataModel == null) {
            debitDataModel = new ListDataModel<>();
        }
        return debitDataModel;
    }

    public void setDebitDataModel(DataModel<FmsBankDebitAdvice> debitDataModel) {
        this.debitDataModel = debitDataModel;
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

    public boolean isRenderPnlCreateDebit() {
        return renderPnlCreateDebit;
    }

    public void setRenderPnlCreateDebit(boolean renderPnlCreateDebit) {
        this.renderPnlCreateDebit = renderPnlCreateDebit;
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

    public NumberConverter getNumberConverter() {
        return numberConverter;
    }

    public void setNumberConverter(NumberConverter numberConverter) {
        this.numberConverter = numberConverter;
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

    public bankDebitAdviceBeanLocal getDebitAdviceBeanLocal() {
        return debitAdviceBeanLocal;
    }

    public void setDebitAdviceBeanLocal(bankDebitAdviceBeanLocal debitAdviceBeanLocal) {
        this.debitAdviceBeanLocal = debitAdviceBeanLocal;
    }

    public bankAccountBeanLocal getBankAccBeanLocal() {
        return bankAccBeanLocal;
    }

    public void setBankAccBeanLocal(bankAccountBeanLocal bankAccBeanLocal) {
        this.bankAccBeanLocal = bankAccBeanLocal;
    }

    public fms_BankBeanLocal getBankBeanLocal() {
        return bankBeanLocal;
    }

    public void setBankBeanLocal(fms_BankBeanLocal bankBeanLocal) {
        this.bankBeanLocal = bankBeanLocal;
    }

    public bankBranchAccountsBeanLocal getBranchAccountsBeanLocal() {
        return branchAccountsBeanLocal;
    }

    public void setBranchAccountsBeanLocal(bankBranchAccountsBeanLocal branchAccountsBeanLocal) {
        this.branchAccountsBeanLocal = branchAccountsBeanLocal;
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

    public FmsBankAccount getFmsBankAccount() {
        if (fmsBankAccount == null) {
            fmsBankAccount = new FmsBankAccount();
        }
        return fmsBankAccount;
    }

    public void setFmsBankAccount(FmsBankAccount fmsBankAccount) {
        this.fmsBankAccount = fmsBankAccount;
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

    public FmsBankDebitAdvice getFmsBankDebitAdvice() {

        if (fmsBankDebitAdvice == null) {
            fmsBankDebitAdvice = new FmsBankDebitAdvice();
        }
        return fmsBankDebitAdvice;
    }

    public void setFmsBankDebitAdvice(FmsBankDebitAdvice fmsBankDebitAdvice) {
        this.fmsBankDebitAdvice = fmsBankDebitAdvice;
    }

    public String getSaveorUpdateBundle() {
        return saveorUpdateBundle;
    }

    public void setSaveorUpdateBundle(String saveorUpdateBundle) {
        this.saveorUpdateBundle = saveorUpdateBundle;
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

    public List<FmsBankDebitAdvice> getDebitList() {
        return debitList;
    }

    public void setDebitList(List<FmsBankDebitAdvice> debitList) {
        this.debitList = debitList;
    }

    List<FmsBankDebitAdvice> debitList;

    FmsBankDebitAdvice selectedFmsDebitAdvice;

    public FmsBankDebitAdvice getSelectedFmsDebitAdvice() {
        return selectedFmsDebitAdvice;
    }

    public void setSelectedFmsDebitAdvice(FmsBankDebitAdvice selectedFmsDebitAdvice) {
        this.selectedFmsDebitAdvice = selectedFmsDebitAdvice;
    }
//</editor-fold>

    //recreate method for assigning debit list for debitdatamodel
    private void recreateDebitDataModel() {
        debitDataModel = null;
        debitDataModel = new ListDataModel(new ArrayList(debitList));
    }

    //concatinate
    public void Concatenate1() {
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
        creditAccCode = fmsLuSystem.getSystemCode() + "-" + accountCode[1] + "-" + fmsGeneralLedger.getGeneralLedgerCode() + "-" + SL_display;
        fmsBankBranchAccounts.setDisplay_conn(creditAccCode);
    }

    //concatinate
    public void Concatenate2() {
        debitAccCode = fmsBankDebitAdvice.getSubsidiaryId();
        String accountCode[] = fmsBankDebitAdvice.getSubsidiaryId().split("-");
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
        display_conn = fmsLuSystem.getSystemCode() + "-" + accountCode[1] + "-" + fmsGeneralLedger.getGeneralLedgerCode() + "-" + SL_display;
        fmsBankDebitAdvice.setDisplay_conn(display_conn);
    }

    //concatinate
    public void Concatenate3() {
        for (int i = 0; i < debitList.size(); i++) {
            fmsBankDebitAdvice.setSubsidiaryId(debitList.get(i).getSubsidiaryId());
            String accountCode[] = fmsBankDebitAdvice.getSubsidiaryId().split("-");
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
            display_conn = fmsLuSystem.getSystemCode() + "-" + accountCode[1] + "-" + fmsGeneralLedger.getGeneralLedgerCode() + "-" + SL_display;
            fmsBankDebitAdvice.setDisplay_conn(display_conn);
            debitList.get(i).setDisplay_conn(display_conn);
        }

    }

    //concatinate
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
        debitAccCode = fmsLuSystem.getSystemId() + "-" + CC_display + "-" + fmsSubsidiaryLedger.getGeneralLedgerId().getGeneralLedgerId() + "-" + SL_display;
    }

    //get bank name from bankbeanlocal
    public List<FmsBank> getBankNameList() {
        return bankBeanLocal.getBankName();

    }

    //search project acc type from fmsGeneralLedgerBeanLocal
    public void searchProjectType() {
        glList = fmsGeneralLedgerBeanLocal.getByAccountType(projectType);
    }

    //search non project acc type from fmsGeneralLedgerBeanLocal
    public void searchNonProjectType() {
        glList = fmsGeneralLedgerBeanLocal.getByAccountType(nonProjectType);
    }

    //Search Debit
    public void searchDebit() {
        debitList = new ArrayList<>();
        try {
            //find all
            if ((fmsBankDebitAdvice.getAccountNumber().isEmpty()) && (fmsBankDebitAdvice.getDebitedDate() == null) && (fmsBankDebitAdvice.getTranRef().isEmpty())) {
                fmsBankDebitAdvice.setDebitAdviceId(fmsBankDebitAdvice.getDebitAdviceId());
                debitList = debitAdviceBeanLocal.searchAllDebits(fmsBankDebitAdvice);
                recreateDebitDataModel();
                //find by account number only
            } else if (!(fmsBankDebitAdvice.getAccountNumber().isEmpty()) && (fmsBankDebitAdvice.getDebitedDate() == null) && (fmsBankDebitAdvice.getTranRef().isEmpty())) {
                fmsBankDebitAdvice.setDebitAdviceId(fmsBankDebitAdvice.getDebitAdviceId());
                fmsBankDebitAdvice.setBankId(fmsBankDebitAdvice.getBankId());
                fmsBankDebitAdvice.setAccountNumber(fmsBankDebitAdvice.getAccountNumber());
                debitList = debitAdviceBeanLocal.getCreditAdviceDate(fmsBankDebitAdvice);
                recreateDebitDataModel();
                //find by account number and date only
            } else if (!(fmsBankDebitAdvice.getAccountNumber().isEmpty()) && !(fmsBankDebitAdvice.getDebitedDate() == null) && (fmsBankDebitAdvice.getTranRef().isEmpty())) {
                fmsBankDebitAdvice.setDebitAdviceId(fmsBankDebitAdvice.getDebitAdviceId());
                fmsBankDebitAdvice.setBankId(fmsBankDebitAdvice.getBankId());
                fmsBankDebitAdvice.setAccountNumber(fmsBankDebitAdvice.getAccountNumber());
                fmsBankDebitAdvice.setDebitedDate(fmsBankDebitAdvice.getDebitedDate());
                debitList = debitAdviceBeanLocal.getDebitAdviceTransactions(fmsBankDebitAdvice);
                recreateDebitDataModel();

                //find by account number, date and transaction ref-no.
            } else if (!(fmsBankDebitAdvice.getAccountNumber().isEmpty()) && !(fmsBankDebitAdvice.getDebitedDate() == null) && !(fmsBankDebitAdvice.getTranRef().isEmpty())) {
                fmsBankDebitAdvice.setDebitAdviceId(fmsBankDebitAdvice.getDebitAdviceId());
                fmsBankDebitAdvice.setBankId(fmsBankDebitAdvice.getBankId());
                fmsBankDebitAdvice.setAccountNumber(fmsBankDebitAdvice.getAccountNumber());
                fmsBankDebitAdvice.setDebitedDate(fmsBankDebitAdvice.getDebitedDate());
                fmsBankDebitAdvice.setTranRef(fmsBankDebitAdvice.getTranRef());
                debitList = debitAdviceBeanLocal.searchAllByAcountNumberDateAndTranRef(fmsBankDebitAdvice);
                recreateDebitDataModel();
            } else {
                JsfUtil.addFatalMessage("Failed to search. Please try again.");
            }
        } catch (Exception e) {
            JsfUtil.addFatalMessage("Reload the page and try again.");
        }
    }

    //populate
    public void populate(SelectEvent event) {
        fmsBankDebitAdvice = (FmsBankDebitAdvice) event.getObject();
        fmsBankDebitAdvice.setDisplay_conn(fmsBankDebitAdvice.getSubsidiaryId());
        fmsCodedTransaction.setTranRef(fmsBankDebitAdvice.getTranRef());
        codedTranList = codedTransactionBeanLocal.getCodedTransactionsByTranRef(fmsCodedTransaction);
        fmsBankAccount.setBranchName(fmsBankDebitAdvice.getBranchName());
        fmsBank = fmsBankDebitAdvice.getBankId();
        branchNames = fmsBank.getFmsBankAccountList();
        for (int i = 0; i < branchNames.size(); i++) {
            if (branchNames.get(i).getBranchName().equals(fmsBankDebitAdvice.getBranchName())) {
                fmsBankAccount = branchNames.get(i);
                fmsBankAccount.setBankAccountId(branchNames.get(i).getBankAccountId());
                accountNumbers = fmsBankAccount.getFmsBankBranchAccountsList();
                break;
            }
        }

        for (int j = 0; j < accountNumbers.size(); j++) {
            if (accountNumbers.get(j).getAccountNumber().equals(fmsBankDebitAdvice.getAccountNumber())) {
                fmsBankBranchAccounts = accountNumbers.get(j);
                break;
            }
        }
        prevCreditAmount = fmsBankDebitAdvice.getAmount();
        updteStatus = 1;
        saveorUpdateBundle = "Update";
        tabIndex = "1";
        renderPnlCreateDebit = true;
        renderPnlManPage = false;
        saveorUpdateBundle = "Update";
        createOrSearchBundle = "Search";
        setIcone("ui-icon-search");
        disableBtnCreate = true;
    }

    //select item for finding all from fmsLuSystemBeanLocal
    public SelectItem[] getGLdata() {
        return JsfUtil.getSelectItems(fmsGeneralLedgerBeanLocal.findAll(), true);
    }

    //select item for finding all from fmsLuSystemBeanLocal
    public SelectItem[] getListSys() {
        return JsfUtil.getSelectItems(fmsLuSystemBeanLocal.findAll(), true);
    }

    //select item for finding all from subsidiaryLedgerBeanLocal
    public SelectItem[] getSubList() {
        return JsfUtil.getSelectItems(subsidiaryLedgerBeanLocal.findAll(), true);
    }

    //select item to getCostCenterBySystemLU
    public SelectItem[] getCostCenterBySystemLU() {

        fmsLuSystem = fmsCostCenter.getSystemId();
        if (fmsLuSystem != null && fmsSubsidiaryLedger != null) {
            return JsfUtil.getSelectItems(fmsCostCenterBeanLocal.findCostCenter(fmsLuSystem), true);
        } else {
            SelectItem[] items = new SelectItem[1];
            items[0] = new SelectItem("", "--- Select One ---");
            return items;
        }
    }

    //select item to find List Subsidery Ledger
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

    //<editor-fold defaultstate="collapsed" desc="value change event">
 //value chage event for system change
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

     //value chage event for cost cente change
    public void CostCenterChange(ValueChangeEvent event) {

        if (!event.getNewValue().toString().isEmpty()) {
            fmsCostCenter.setSystemName(event.getNewValue().toString());
            fmsCostCenter = fmsCostCenterBeanLocal.getCostDetail(fmsCostCenter);
        }
    }

     //value chage event for GL change
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

     //value chage event subsidery change
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

    //lis account numbers from FmsBankBranchAccounts
    public List<FmsBankDebitAdvice> getTransactionFromCreditAdvice(ValueChangeEvent event) {
        transactionList = debitAdviceBeanLocal.getDebitAdviceTransactions(fmsBankDebitAdvice);
        return debitAdviceBeanLocal.getDebitAdviceTransactions(fmsBankDebitAdvice);
    }

     //value chage event to get credit advice date
    public List<FmsBankDebitAdvice> getDateFromCreditAdvice(ValueChangeEvent event) {
        dates = debitAdviceBeanLocal.getCreditAdviceDate(fmsBankDebitAdvice);
        return debitAdviceBeanLocal.getCreditAdviceDate(fmsBankDebitAdvice);
    }

     //value chage event to get branch name
    public List<FmsBankAccount> getBankBranchFromBankAccount(ValueChangeEvent event) {
        fmsBankAccount.setBankId(fmsBankAccount.getBankId());
        branchNames = bankAccBeanLocal.getBranchNameById(fmsBankAccount);
        return bankAccBeanLocal.getBranchNameById(fmsBankAccount);
    }

     //value chage event to get subsidery code
    public void getBalance(ValueChangeEvent event) {
        fmsBankBranchAccounts = branchAccountsBeanLocal.getBalanceByAcctNo(fmsBankBranchAccounts);
        creditAccCode = fmsBankBranchAccounts.getSubsidiaryId().getSubsidiaryCode();
    }

         //value chage event to get AllDataFromCashDeposti
    public FmsBankDebitAdvice getAllDataFromCashDeposti(ValueChangeEvent event) {
        fmsBankDebitAdvice.setDebitedDate(fmsBankDebitAdvice.getDebitedDate());
        fmsBankDebitAdvice = debitAdviceBeanLocal.getData(fmsBankDebitAdvice);
        fmsCodedTransaction.setTranRef(fmsBankDebitAdvice.getTranRef());
        codedTranList = codedTransactionBeanLocal.getCodedTransactionsByTranRef(fmsCodedTransaction);
        fmsBankAccount.setBranchName(fmsBankDebitAdvice.getBranchName());
        fmsBank = fmsBankDebitAdvice.getBankId();
        branchNames = fmsBank.getFmsBankAccountList();
        for (int i = 0; i < branchNames.size(); i++) {
            if (branchNames.get(i).getBranchName().equals(fmsBankDebitAdvice.getBranchName())) {
                fmsBankAccount = branchNames.get(i);
                fmsBankAccount.setBankAccountId(branchNames.get(i).getBankAccountId());
                accountNumbers = fmsBankAccount.getFmsBankBranchAccountsList();
                break;
            }
        }

        for (int j = 0; j < accountNumbers.size(); j++) {
            if (accountNumbers.get(j).getAccountNumber().equals(fmsBankDebitAdvice.getAccountNumber())) {
                fmsBankBranchAccounts = accountNumbers.get(j);
                break;
            }
        }
        fmsBankAccount.setBankId(fmsBank);
        fmsBankDebitAdvice.setAccountNumber(fmsBankDebitAdvice.getAccountNumber());
        prevCreditAmount = fmsBankDebitAdvice.getAmount();
        updteStatus = 1;
        saveorUpdateBundle = "Update";
        tabIndex = "1";
        return fmsBankDebitAdvice;
    }

     //value chage event for system change
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

     //value chage event for cost center change
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

     //value chage event for GL change
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

     //value chage event for subsidery ledger changer
    public void onChangeSubsidiaryLedger(ValueChangeEvent event) {
        try {
            if (!event.getNewValue().toString().isEmpty()) {
                fmsSubsidiaryLedger = (FmsSubsidiaryLedger) event.getNewValue();
            }
        } catch (Exception e) {
            JsfUtil.addFatalMessage("Unable to process. Try again reloading the page.");
        }
    }

    //value chage event for job number change
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
//</editor-fold>
    
    //save debit advice
    public String saveDebitAdvice() {
        AAA securityService = new AAA();
        IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
        String systemBundle = "et/gov/eep/commonApplications/securityServer";
        String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
        if (security.checkAccess(SessionBean.getUserName(), "saveDebitAdvice", dataset)) {
            double balance1 = 0.0;
            double amount = 0.0;
            double balance2 = 0.0;
            double prev_debit_amt = 0.0;
            double balance3 = 0.0;
            double balance4 = 0.0;
            if (updteStatus == 0) {
                if (creditAccCode.equalsIgnoreCase(debitAccCode)) {
                    JsfUtil.addFatalMessage("Debit/Credit is not Allowed to/from the Same Account Code!");
                    return null;
                }
                try {
                    //get bankName, branchName, AccountNumber, beginningBalance from fmsBankAccount and set to fmsBankDebitAdvice
                    fmsBankDebitAdvice.setBankId(fmsBankAccount.getBankId());
                    fmsBankDebitAdvice.setBranchName(fmsBankBranchAccounts.getBankAccountId().getBranchName());
                    fmsBankDebitAdvice.setAccountNumber(fmsBankBranchAccounts.getAccountNumber());
                    if (fmsBankDebitAdvice.getSubsidiaryId() == null) {
                        JsfUtil.addFatalMessage("Add Debit Account code.");
                        return null;
                    }
                    //setBeginningBallance
                    balance1 = fmsBankBranchAccounts.getBeginningBalance();
                    amount = fmsBankDebitAdvice.getAmount();
                    if (balance1 > amount) {
                        balance2 = balance1 - amount;
                        //add amount to beginning balance
                        fmsBankBranchAccounts.setBeginningBalance(balance2);
                        debitAdviceBeanLocal.create(fmsBankDebitAdvice);
                        //update fmsBankAccount and add amount to beginning balance
                        branchAccountsBeanLocal.edit(fmsBankBranchAccounts);
                        //create coded-transaction-data credit-side
                        fmsCodedTransaction.setStatus(0);
                        fmsCodedTransaction.setRecon_status(0);
                        fmsCodedTransaction.setType("debit");
                        fmsCodedTransaction.setCredit(fmsBankDebitAdvice.getAmount());
                        fmsCodedTransaction.setDebit(0.00);
                        fmsCodedTransaction.setTranRef(fmsBankDebitAdvice.getTranRef());
                        fmsCodedTransaction.setSubsidiaryId(fmsBankBranchAccounts.getSubsidiaryId().getSubsidiaryCode());
                        codedTransactionBeanLocal.create(fmsCodedTransaction);

                        //create coded-transactiondata debit-side
                        fmsCodedTransaction = new FmsCodedTransaction();
                        fmsCodedTransaction.setStatus(0);
                        fmsCodedTransaction.setRecon_status(0);
                        fmsCodedTransaction.setType("debit");
                        fmsCodedTransaction.setCredit(0.00);
                        fmsCodedTransaction.setDebit(fmsBankDebitAdvice.getAmount());
                        fmsCodedTransaction.setTranRef(fmsBankDebitAdvice.getTranRef());
                        fmsCodedTransaction.setSubsidiaryId(fmsBankDebitAdvice.getSubsidiaryId());
                        codedTransactionBeanLocal.create(fmsCodedTransaction);
                        JsfUtil.addSuccessMessage("Saved Successfully!");
                        clearPage();
                        clearAccCodes();
                    } else {
                        JsfUtil.addFatalMessage("Insufficient Balance to Debit!.");
                        return null;
                    }
                } catch (Exception e) {
                    JsfUtil.addFatalMessage("Failed to save! Try with Unique Tran. Ref. Number.");
                    return null;
                }

            } else {
                try {
                    if (codedTranList.isEmpty()) {
                        JsfUtil.addFatalMessage("Update is not Allowed!, JV has Already Done for this Transaction.");
                        return null;
                    } else {
                        //get bankName, branchName, AccountNumber, beginningBalance from fmsBankAccount and set to fmsBankDebitAdvice
                        fmsBankDebitAdvice.setBankId(fmsBankAccount.getBankId());
                        fmsBankDebitAdvice.setBranchName(fmsBankBranchAccounts.getBankAccountId().getBranchName());
                        fmsBankDebitAdvice.setAccountNumber(fmsBankBranchAccounts.getAccountNumber());

                        //setBeginningBallance
                        balance1 = fmsBankBranchAccounts.getBeginningBalance();
                        prev_debit_amt = prevCreditAmount;
                        balance3 = balance1 + prev_debit_amt;
                        amount = fmsBankDebitAdvice.getAmount();
                        if (balance1 > amount) {
                            balance4 = balance3 - amount;
                            fmsBankBranchAccounts.setBeginningBalance(balance4);
                            debitAdviceBeanLocal.edit(fmsBankDebitAdvice);
                            //update fmsBankAccount and add amount to beginning balance
                            branchAccountsBeanLocal.edit(fmsBankBranchAccounts);
                            //update coded-transaction-data credit-side
                            fmsCodedTransaction.setCodedTransactonId(codedTranList.get(0).getCodedTransactonId());
                            fmsCodedTransaction.setStatus(0);
                            fmsCodedTransaction.setRecon_status(0);
                            fmsCodedTransaction.setType("debit");
                            fmsCodedTransaction.setCredit(fmsBankDebitAdvice.getAmount());
                            fmsCodedTransaction.setDebit(0.00);
                            fmsCodedTransaction.setTranRef(fmsBankDebitAdvice.getTranRef());
                            fmsCodedTransaction.setSubsidiaryId(fmsBankBranchAccounts.getSubsidiaryId().getSubsidiaryCode());
                            codedTransactionBeanLocal.edit(fmsCodedTransaction);

                            //update coded-transactiondata debit-side
                            fmsCodedTransaction = new FmsCodedTransaction();
                            fmsCodedTransaction.setCodedTransactonId(codedTranList.get(1).getCodedTransactonId());
                            fmsCodedTransaction.setStatus(0);
                            fmsCodedTransaction.setRecon_status(0);
                            fmsCodedTransaction.setType("debit");
                            fmsCodedTransaction.setCredit(0.00);
                            fmsCodedTransaction.setDebit(fmsBankDebitAdvice.getAmount());
                            fmsCodedTransaction.setTranRef(fmsBankDebitAdvice.getTranRef());
                            fmsCodedTransaction.setSubsidiaryId(fmsBankDebitAdvice.getSubsidiaryId());
                            codedTransactionBeanLocal.edit(fmsCodedTransaction);
                            JsfUtil.addSuccessMessage("Update Successfully!");
                            saveorUpdateBundle = "Save";
                            clearPage();
                            clearAccCodes();
                        } else {
                            JsfUtil.addFatalMessage("Insufficient Balance to debit!. Please try again!.");
                        }
                    }
                } catch (Exception e) {
                    JsfUtil.addFatalMessage("Failed to Update. Please try Again Reloading the Page.");
                }
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

    //clear
    public void clearPage() {
        fmsBankDebitAdvice = new FmsBankDebitAdvice();
        fmsBankAccount = new FmsBankAccount();
        fmsBankBranchAccounts = new FmsBankBranchAccounts();
        fmsCodedTransaction = new FmsCodedTransaction();
        debitDataModel = new ListDataModel<>();
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

    //create and search render
    public void createNewDebit() {
        clearPage();
        saveorUpdateBundle = "Save";
        disableBtnCreate = false;
        if (createOrSearchBundle.equals("New")) {
            renderPnlCreateDebit = true;
            renderPnlManPage = false;
            createOrSearchBundle = "Search";
            setIcone("ui-icon-search");
        } else if (createOrSearchBundle.equals("Search")) {
            renderPnlCreateDebit = false;
            renderPnlManPage = true;
            createOrSearchBundle = "New";
            setIcone("ui-icon-plus");
        }
    }

}
