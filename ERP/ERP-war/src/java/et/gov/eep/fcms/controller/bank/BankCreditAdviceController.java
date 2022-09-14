/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.controller.bank;

import java.io.Serializable;
import java.util.ArrayList;
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
import et.gov.eep.fcms.businessLogic.bank.FmsCodedTransactionBeanLocal;
import et.gov.eep.fcms.businessLogic.admin.FmsCostCenterBeanLocal;
import et.gov.eep.fcms.businessLogic.admin.FmsGeneralLedgerBeanLocal;
import et.gov.eep.fcms.businessLogic.admin.FmsLuSystemBeanLocal;
import et.gov.eep.fcms.businessLogic.admin.subsidiaryBeanLocal;
import et.gov.eep.fcms.businessLogic.bank.bankAccountBeanLocal;
import et.gov.eep.fcms.businessLogic.bank.bankBranchAccountsBeanLocal;
import et.gov.eep.fcms.businessLogic.bank.bankCreditAdviceBeanLocal;
import et.gov.eep.fcms.businessLogic.bank.fms_BankBeanLocal;
import et.gov.eep.fcms.entity.bank.FmsBank;
import et.gov.eep.fcms.entity.bank.FmsBankAccount;
import et.gov.eep.fcms.entity.bank.FmsBankBranchAccounts;
import et.gov.eep.fcms.entity.bank.FmsBankCreditAdvice;
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
@Named(value = "bankCreditAdviceController")
@ViewScoped
public class BankCreditAdviceController implements Serializable {

    //<editor-fold defaultstate="collapsed" desc="Inject Entities and @ EJB">
    //Inject @EJB
    @EJB
    FmsCostCenterBeanLocal fmsCostCenterBeanLocal;
    @EJB
    private bankCreditAdviceBeanLocal creditAdviceBeanLocal;
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
    //Inject entites
    @Inject
    private FmsBank fmsBank;
    @Inject
    private FmsBankAccount fmsBankAccount;
    @Inject
    private FmsBankBranchAccounts fmsBankBranchAccounts;
    @Inject
    private FmsBankCreditAdvice fmsBankCreditAdvice;
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
    FmsBankCreditAdvice selectedFmsCreditAdvice;
    DataModel<FmsBankCreditAdvice> creditDataModel;
    List<FmsBankCreditAdvice> creditList;
    List<FmsCostcSystemJunction> ssCcJunctionList = new ArrayList<>();
    List<FmsSystemJobJunction> sysJobNOList;
    List<FmsGeneralLedger> glList;
    List<FmsSubsidiaryLedger> slList;
    List<FmsLuSystem> systemList;
    List<FmsCodedTransaction> codedTranList;
    List<FmsBankBranchAccounts> accountNumbers;
    List<FmsBankCreditAdvice> transactionList;
    List<FmsBankCreditAdvice> dates;
    List<FmsBankAccount> branchNames;
    String saveorUpdateBundle = "Save";
    private String creditAccCode = "";
    private String debitAccCode = "";
    private String display_conn;
    private String activeIndex;
    private String tabIndex = "0";
    private String chooseSearchCredit = "hidden";
    private String chooseSearchDebit = "hidden";
    private String rdoCreditval = "rdoSearchCredit";
    private String rdoDebitVal = "rdoSearchDebit";
    private String icone = "ui-icon-plus";
    private String createOrSearchBundle = "New";
    int updteStatus = 0;
    double prevCreditAmount = 0.0;
    final Integer projectType = 2;
    final Integer nonProjectType = 1;
    private boolean disableBtnCreate;
    private boolean renderPnlCreateCredit = false;
    private boolean renderPnlManPage = true;
    boolean renderJobNo = false;
    private NumberConverter numberConverter = new NumberConverter();
//</editor-fold>

    public BankCreditAdviceController() {
        numberConverter.setPattern("#,##0.00##");
        numberConverter.setMinIntegerDigits(1);
        numberConverter.setMaxIntegerDigits(40);
        numberConverter.setMaxFractionDigits(3);
    }

    //<editor-fold defaultstate="collapsed" desc="Getter and Setter Methods">
    public FmsBankCreditAdvice getSelectedFmsCreditAdvice() {
        return selectedFmsCreditAdvice;
    }

    public void setSelectedFmsCreditAdvice(FmsBankCreditAdvice selectedFmsCreditAdvice) {
        this.selectedFmsCreditAdvice = selectedFmsCreditAdvice;
    }

    public List<FmsBankBranchAccounts> getBankAccountNo() {
        if (fmsBankAccount == null) {
            fmsBankAccount = new FmsBankAccount();
        }
        return branchAccountsBeanLocal.getBankAccountNo();
    }

    public List<FmsBankCreditAdvice> getTransactionList() {

        return transactionList;
    }

    public List<FmsBankBranchAccounts> getAccountNumbers() {
        if (accountNumbers == null) {
            accountNumbers = new ArrayList<>();
        }
        return accountNumbers;
    }

    public List<FmsBankCreditAdvice> getDates() {
        if (dates == null) {
            dates = new ArrayList<>();
        }
        return dates;
    }

    public void setDates(List<FmsBankCreditAdvice> dates) {
        this.dates = dates;
    }

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

    public List<FmsBankCreditAdvice> getCreditList() {
        return creditList;
    }

    public void setCreditList(List<FmsBankCreditAdvice> creditList) {
        this.creditList = creditList;
    }

    public DataModel<FmsBankCreditAdvice> getCreditDataModel() {
        if (creditDataModel == null) {
            creditDataModel = new ListDataModel<>();
        }
        return creditDataModel;
    }

    public void setCreditDataModel(DataModel<FmsBankCreditAdvice> creditDataModel) {
        this.creditDataModel = creditDataModel;
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

    public boolean isRenderPnlCreateCredit() {
        return renderPnlCreateCredit;
    }

    public void setRenderPnlCreateCredit(boolean renderPnlCreateCredit) {
        this.renderPnlCreateCredit = renderPnlCreateCredit;
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

    public FmsBank getFmsBank() {
        if (fmsBank == null) {
            fmsBank = new FmsBank();
        }
        return fmsBank;
    }

    public void setFmsBank(FmsBank fmsBank) {
        this.fmsBank = fmsBank;
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

    public int getUpdteStatus() {
        return updteStatus;
    }

    public void setUpdteStatus(int updteStatus) {
        this.updteStatus = updteStatus;
    }

    public bankCreditAdviceBeanLocal getCreditAdviceBeanLocal() {
        return creditAdviceBeanLocal;
    }

    public void setCreditAdviceBeanLocal(bankCreditAdviceBeanLocal creditAdviceBeanLocal) {
        this.creditAdviceBeanLocal = creditAdviceBeanLocal;
    }

    public bankAccountBeanLocal getBankAccBeanLocal() {
        return bankAccBeanLocal;
    }

    public void setBankAccBeanLocal(bankAccountBeanLocal bankAccBeanLocal) {
        this.bankAccBeanLocal = bankAccBeanLocal;
    }

    public FmsBankCreditAdvice getFmsBankCreditAdvice() {
        if (fmsBankCreditAdvice == null) {
            fmsBankCreditAdvice = new FmsBankCreditAdvice();
        }
        return fmsBankCreditAdvice;
    }

    public void setFmsBankCreditAdvice(FmsBankCreditAdvice fmsBankCreditAdvice) {
        this.fmsBankCreditAdvice = fmsBankCreditAdvice;
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

    public String getSaveorUpdateBundle() {
        return saveorUpdateBundle;
    }

    public void setSaveorUpdateBundle(String saveorUpdateBundle) {
        this.saveorUpdateBundle = saveorUpdateBundle;
    }
//</editor-fold>

    //recreate method to assign credit lit to creditdatamode
    private void recreateCreditDataModel() {
        creditDataModel = null;
        creditDataModel = new ListDataModel(new ArrayList(creditList));
    }

    //select evnt to handle display chooseSearchCredit and debit
    public void getradioHandiler(SelectEvent event) {
        String slected = fmsBankCreditAdvice.getReason();
        if (slected.equalsIgnoreCase("rdoSearchCredit")) {
            chooseSearchCredit = "display";
            chooseSearchDebit = "hidden";
        } else {
            chooseSearchDebit = "display";
            chooseSearchCredit = "hidden";
        }
    }

    //populate select event
    public void populate(SelectEvent event) {
        fmsBankCreditAdvice = (FmsBankCreditAdvice) event.getObject();
        fmsBankCreditAdvice.setDisplay_conn(fmsBankCreditAdvice.getSubsidaryId());
        fmsCodedTransaction.setTranRef(fmsBankCreditAdvice.getTranRef());
        codedTranList = codedTransactionBeanLocal.getCodedTransactionsByTranRef(fmsCodedTransaction);
        fmsBankAccount.setBranchName(fmsBankCreditAdvice.getBranchName());
        fmsBank = fmsBankCreditAdvice.getBankId();
        branchNames = fmsBank.getFmsBankAccountList();
        for (int i = 0; i < branchNames.size(); i++) {
            if (branchNames.get(i).getBranchName().equals(fmsBankCreditAdvice.getBranchName())) {
                fmsBankAccount = branchNames.get(i);
                fmsBankAccount.setBankAccountId(branchNames.get(i).getBankAccountId());
                accountNumbers = fmsBankAccount.getFmsBankBranchAccountsList();
                break;
            }
        }

        for (int j = 0; j < accountNumbers.size(); j++) {
            if (accountNumbers.get(j).getAccountNumber().equals(fmsBankCreditAdvice.getAccountNumber())) {
                fmsBankBranchAccounts = accountNumbers.get(j);
                break;
            }
        }
        prevCreditAmount = fmsBankCreditAdvice.getAmount();
        updteStatus = 1;
        tabIndex = "0";
        renderPnlCreateCredit = true;
        renderPnlManPage = false;
        activeIndex = "0";
        saveorUpdateBundle = "Update";
        createOrSearchBundle = "Search";
        setIcone("ui-icon-search");
        disableBtnCreate = true;
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
        String accountCode[] = fmsBankCreditAdvice.getSubsidaryId().split("-");
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
        fmsBankCreditAdvice.setDisplay_conn(display_conn);
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

//concatinate
    public void Concatenate3() {
        try {

            for (int i = 0; i < creditList.size(); i++) {
                fmsBankCreditAdvice.setSubsidaryId(creditList.get(i).getSubsidaryId());
                String accountCode[] = fmsBankCreditAdvice.getSubsidaryId().split("-");
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
                fmsBankCreditAdvice.setDisplay_conn(display_conn);
                creditList.get(i).setDisplay_conn(display_conn);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //searching for bank name from bankBeanLocal
    public List<FmsBank> getBankNameList() {
        return bankBeanLocal.getBankName();

    }

    //searching for project acc type from fmsGeneralLedgerBeanLocal
    public void searchProjectType() {
        glList = fmsGeneralLedgerBeanLocal.getByAccountType(projectType);
    }

    //searching for non project acc type from fmsGeneralLedgerBeanLocal
    public void searchNonProjectType() {
        glList = fmsGeneralLedgerBeanLocal.getByAccountType(nonProjectType);
    }

    //Search Credit
    public void searchCredit() {
        creditList = new ArrayList<>();
        try {
            //find all
            if ((fmsBankCreditAdvice.getAccountNumber().isEmpty()) && (fmsBankCreditAdvice.getCreditDate() == null) && (fmsBankCreditAdvice.getTranRef().isEmpty())) {
                fmsBankCreditAdvice.setCreditAdviceId(fmsBankCreditAdvice.getCreditAdviceId());
                creditList = creditAdviceBeanLocal.searchAllCredits(fmsBankCreditAdvice);
                recreateCreditDataModel();
                //find by account number only
            } else if (!(fmsBankCreditAdvice.getAccountNumber().isEmpty()) && (fmsBankCreditAdvice.getCreditDate() == null) && (fmsBankCreditAdvice.getTranRef().isEmpty())) {
                fmsBankCreditAdvice.setCreditAdviceId(fmsBankCreditAdvice.getCreditAdviceId());
                fmsBankCreditAdvice.setBankId(fmsBankCreditAdvice.getBankId());
                fmsBankCreditAdvice.setAccountNumber(fmsBankCreditAdvice.getAccountNumber());
                creditList = creditAdviceBeanLocal.getCreditAdviceDate(fmsBankCreditAdvice);
                recreateCreditDataModel();
                //find by account number and date only
            } else if (!(fmsBankCreditAdvice.getAccountNumber().isEmpty()) && !(fmsBankCreditAdvice.getCreditDate() == null) && (fmsBankCreditAdvice.getTranRef().isEmpty())) {
                fmsBankCreditAdvice.setCreditAdviceId(fmsBankCreditAdvice.getCreditAdviceId());
                fmsBankCreditAdvice.setBankId(fmsBankCreditAdvice.getBankId());
                fmsBankCreditAdvice.setAccountNumber(fmsBankCreditAdvice.getAccountNumber());
                fmsBankCreditAdvice.setCreditDate(fmsBankCreditAdvice.getCreditDate());
                creditList = creditAdviceBeanLocal.getCreditTransactions(fmsBankCreditAdvice);
                recreateCreditDataModel();
                //find by account number, date and transaction ref-no.
            } else if (!(fmsBankCreditAdvice.getAccountNumber().isEmpty()) && !(fmsBankCreditAdvice.getCreditDate() == null) && !(fmsBankCreditAdvice.getTranRef().isEmpty())) {
                fmsBankCreditAdvice.setCreditAdviceId(fmsBankCreditAdvice.getCreditAdviceId());
                fmsBankCreditAdvice.setBankId(fmsBankCreditAdvice.getBankId());
                fmsBankCreditAdvice.setAccountNumber(fmsBankCreditAdvice.getAccountNumber());
                fmsBankCreditAdvice.setCreditDate(fmsBankCreditAdvice.getCreditDate());
                fmsBankCreditAdvice.setTranRef(fmsBankCreditAdvice.getTranRef());
                creditList = creditAdviceBeanLocal.searchAllByAcountNumberDateAndTranRef(fmsBankCreditAdvice);
                recreateCreditDataModel();
            } else {
                JsfUtil.addFatalMessage("Failed to Search! Please try again.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            JsfUtil.addFatalMessage("Failed to Search! Try Again Reloading the Page .");
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

    //select item to find CostCenter from fmsCostCenterBeanLocal
    public SelectItem[] getCostCenterBySystemLU() {

        fmsLuSystem = fmsCostCenter.getSystemId();
        if (fmsLuSystem != null && fmsSubsidiaryLedger != null) {
            return JsfUtil.getSelectItems(fmsCostCenterBeanLocal.findCostCenter(fmsLuSystem), true);
        } else {
            SelectItem[] items = new SelectItem[1];
            items[0] = new SelectItem("", "--Select Cost Center--");
            return items;
        }
    }

    //select item to find Subsidery ledger list
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
    //value change event for system change
    public void SystemChange(ValueChangeEvent event) {
        if (!event.getNewValue().toString().isEmpty()) {
            fmsLuSystem.setSystemCode(event.getNewValue().toString());
            fmsLuSystem = fmsLuSystemBeanLocal.getSysDetail(fmsLuSystem);
        }
    }

    //value change event for cost center change
    public void CostCenterChange(ValueChangeEvent event) {

        if (!event.getNewValue().toString().isEmpty()) {
            fmsCostCenter.setSystemName(event.getNewValue().toString());
            fmsCostCenter = fmsCostCenterBeanLocal.getCostDetail(fmsCostCenter);
        }
    }

    //value change event for GL change
    public void getGeneralLedgerChange(ValueChangeEvent event) {
        if (!event.getNewValue().toString().isEmpty()) {
            fmsGeneralLedger.setGeneralLedgerCode(event.getNewValue().toString());
            fmsGeneralLedger = fmsGeneralLedgerBeanLocal.getGLDetail(fmsGeneralLedger);
            fmsSubsidiaryLedger.setGeneralLedgerId(fmsGeneralLedger);

        }
    }

    //value change event for Subsidery ledger change
    public void getSubsidiaryLChange(ValueChangeEvent event) {
        if (!event.getNewValue().toString().isEmpty()) {
            fmsSubsidiaryLedger.setSubsidiaryId(Integer.parseInt(event.getNewValue().toString()));
            fmsSubsidiaryLedger = subsidiaryLedgerBeanLocal.getSubsidiaryInfo(fmsSubsidiaryLedger);

        }
    }

    //add acc info method
    public String addAccountInfoDetail() {
        try {
            display_conn = fmsSubsidiaryLedger.getSubsidiaryCode();
            fmsBankCreditAdvice.setSubsidaryId(display_conn);//to be saved
            fmsBankCreditAdvice.setDisplay_conn(display_conn);//to display
            debitAccCode = fmsSubsidiaryLedger.getSubsidiaryCode();
        } catch (Exception e) {
            JsfUtil.addFatalMessage("Unable to Add Account Code. Try again Reloadin the page.");
        }
        return null;
    }

    //value change event to search AccountNumberByBankAccountId from branchAccountsBeanLocal
    public void getAccountNumberFromBranchAccounts(ValueChangeEvent event) {
        fmsBankBranchAccounts.setBankAccountId(fmsBankAccount);
        accountNumbers = branchAccountsBeanLocal.getAccountNumberByBankAccountId(fmsBankBranchAccounts);
    }

    //value change event to search CreditTransactions from creditAdviceBeanLocal
    public List<FmsBankCreditAdvice> getTransactionFromCredit(ValueChangeEvent event) {
        transactionList = creditAdviceBeanLocal.getCreditTransactions(fmsBankCreditAdvice);
        return creditAdviceBeanLocal.getCreditTransactions(fmsBankCreditAdvice);
    }

    //value change event to search CreditAdviceDate from creditAdviceBeanLocal
    public List<FmsBankCreditAdvice> getDateFromCreditAdvice(ValueChangeEvent event) {
        dates = creditAdviceBeanLocal.getCreditAdviceDate(fmsBankCreditAdvice);
        return creditAdviceBeanLocal.getCreditAdviceDate(fmsBankCreditAdvice);
    }

    //value change event to search BranchNameById from bankAccBeanLocal
    public List<FmsBankAccount> getBankBranchFromBankAccount(ValueChangeEvent event) {
        fmsBankAccount.setBankId(fmsBankAccount.getBankId());
        branchNames = bankAccBeanLocal.getBranchNameById(fmsBankAccount);
        return bankAccBeanLocal.getBranchNameById(fmsBankAccount);
    }

    //value change event to search SubsidiaryCode from fmsBankBranchAccounts
    public void getBalance(ValueChangeEvent event) {
        fmsBankBranchAccounts = branchAccountsBeanLocal.getBalanceByAcctNo(fmsBankBranchAccounts);
        creditAccCode = fmsBankBranchAccounts.getSubsidiaryId().getSubsidiaryCode();
    }

    //value change event for change system
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

    //value change event for change cost center
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

    //value change event for change GL
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

    //value change event for change subsidery ledger
    public void onChangeSubsidiaryLedger(ValueChangeEvent event) {
        try {
            if (!event.getNewValue().toString().isEmpty()) {
                fmsSubsidiaryLedger = (FmsSubsidiaryLedger) event.getNewValue();
            }
        } catch (Exception e) {
            JsfUtil.addFatalMessage("Unable to process. Try again reloading the page.");
        }
    }

    //value change event for change job number
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

    //value change event to search AllData From Credit
    public FmsBankCreditAdvice getAllDataFromCredit(ValueChangeEvent event) {
        fmsBankCreditAdvice.setCreditDate(fmsBankCreditAdvice.getCreditDate());
        fmsBankCreditAdvice = creditAdviceBeanLocal.getAllData(fmsBankCreditAdvice);
        fmsCodedTransaction.setTranRef(fmsBankCreditAdvice.getTranRef());
        codedTranList = codedTransactionBeanLocal.getCodedTransactionsByTranRef(fmsCodedTransaction);
        fmsBankAccount.setBranchName(fmsBankCreditAdvice.getBranchName());
        fmsBank = fmsBankCreditAdvice.getBankId();
        branchNames = fmsBank.getFmsBankAccountList();
        for (int i = 0; i < branchNames.size(); i++) {
            if (branchNames.get(i).getBranchName().equals(fmsBankCreditAdvice.getBranchName())) {
                fmsBankAccount = branchNames.get(i);
                fmsBankAccount.setBankAccountId(branchNames.get(i).getBankAccountId());
                accountNumbers = fmsBankAccount.getFmsBankBranchAccountsList();
                break;
            }
        }

        for (int j = 0; j < accountNumbers.size(); j++) {
            if (accountNumbers.get(j).getAccountNumber().equals(fmsBankCreditAdvice.getAccountNumber())) {
                fmsBankBranchAccounts = accountNumbers.get(j);
                break;
            }
        }
        fmsBankAccount.setBankId(fmsBank);
        fmsBankCreditAdvice.setAccountNumber(fmsBankCreditAdvice.getAccountNumber());
        prevCreditAmount = fmsBankCreditAdvice.getAmount();
        updteStatus = 1;
        saveorUpdateBundle = "Update";
        tabIndex = "0";
        return fmsBankCreditAdvice;
    }
//</editor-fold>
    
    //save method
    public String saveCreditAdvice() {
        AAA securityService = new AAA();
        IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
        String systemBundle = "et/gov/eep/commonApplications/securityServer";
        String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
        if (security.checkAccess(SessionBean.getUserName(), "saveCreditAdvice", dataset)) {
            double balance1 = 0.0;
            double amount = 0.0;
            double balance2 = 0.0;
            double prev_credit_amt = 0.0;
            double balance3 = 0.0;
            double balance4 = 0.0;
            if (updteStatus == 0) {
                if (creditAccCode.equalsIgnoreCase(debitAccCode)) {
                    JsfUtil.addFatalMessage("Debit/Credit is not Allowed to/from the Same Account Code!");
                    return null;
                }
                try {
                    //get bankName, branchName, AccountNumber, beginningBalance from fmsBankAccount and set to fmsBankCreditAdvice
                    fmsBankCreditAdvice.setBankId(fmsBankAccount.getBankId());
                    fmsBankCreditAdvice.setBranchName(fmsBankBranchAccounts.getBankAccountId().getBranchName());
                    fmsBankCreditAdvice.setAccountNumber(fmsBankBranchAccounts.getAccountNumber());
                    if (fmsBankCreditAdvice.getSubsidaryId() == null) {
                        JsfUtil.addFatalMessage("Add Credit Account Code.");
                        return null;
                    }
                    balance1 = fmsBankBranchAccounts.getBeginningBalance(); //setBeginningBallance
                    amount = fmsBankCreditAdvice.getAmount();
                    balance2 = balance1 + amount; //add amount to beginning balance
                    fmsBankBranchAccounts.setBeginningBalance(balance2);
                    creditAdviceBeanLocal.create(fmsBankCreditAdvice);
                    branchAccountsBeanLocal.edit(fmsBankBranchAccounts);//update fmsBankAccount and add amount to beginning balance
                    //create coded-transaction-data credit-side
                    fmsCodedTransaction.setStatus(0);
                    fmsCodedTransaction.setRecon_status(0);
                    fmsCodedTransaction.setType("credit");
                    fmsCodedTransaction.setCredit(fmsBankCreditAdvice.getAmount());
                    fmsCodedTransaction.setDebit(0.00);
                    fmsCodedTransaction.setTranRef(fmsBankCreditAdvice.getTranRef());
                    fmsCodedTransaction.setSubsidiaryId(fmsBankCreditAdvice.getSubsidaryId());
                    codedTransactionBeanLocal.create(fmsCodedTransaction);

                    //create coded-transactiondata debit-side
                    fmsCodedTransaction = new FmsCodedTransaction();
                    fmsCodedTransaction.setStatus(0);
                    fmsCodedTransaction.setRecon_status(0);
                    fmsCodedTransaction.setType("credit");
                    fmsCodedTransaction.setCredit(0.0);
                    fmsCodedTransaction.setDebit(fmsBankCreditAdvice.getAmount());

                    fmsCodedTransaction.setTranRef(fmsBankCreditAdvice.getTranRef());
                    fmsCodedTransaction.setSubsidiaryId(fmsBankBranchAccounts.getSubsidiaryId().getSubsidiaryCode());
                    codedTransactionBeanLocal.create(fmsCodedTransaction);
                    JsfUtil.addSuccessMessage("Saved Successfully!");
                    clearPage();
                    clearAccCodes();
                } catch (Exception e) {
                    JsfUtil.addFatalMessage("Failed to Save! Try with Unique Tran. Ref. Number.");
                }

                return null;
            } else {
                try {
                    if (codedTranList.isEmpty()) {
                        JsfUtil.addFatalMessage("Update is not Allowed!, JV has Alredy Done for this Transaction.");
                        return null;
                    } else {
                        fmsBankCreditAdvice.setBankId(fmsBankAccount.getBankId());
                        fmsBankCreditAdvice.setBranchName(fmsBankBranchAccounts.getBankAccountId().getBranchName());
                        fmsBankCreditAdvice.setAccountNumber(fmsBankBranchAccounts.getAccountNumber());

                        //setBeginningBallance
                        balance1 = fmsBankBranchAccounts.getBeginningBalance();
                        prev_credit_amt = prevCreditAmount;
                        balance3 = balance1 - prev_credit_amt;
                        amount = fmsBankCreditAdvice.getAmount();
                        balance4 = balance3 + amount;
                        //add amount to beginning balance
                        fmsBankBranchAccounts.setBeginningBalance(balance4);
                        creditAdviceBeanLocal.edit(fmsBankCreditAdvice);
                        //update fmsBankAccount and add amount to beginning balance
                        branchAccountsBeanLocal.edit(fmsBankBranchAccounts);
                        //update coded-transaction-data credit-side
                        fmsCodedTransaction.setCodedTransactonId(codedTranList.get(0).getCodedTransactonId());
                        fmsCodedTransaction.setStatus(0);
                        fmsCodedTransaction.setRecon_status(0);
                        fmsCodedTransaction.setType("credit");
                        fmsCodedTransaction.setDebit(0.00);
                        fmsCodedTransaction.setCredit(fmsBankCreditAdvice.getAmount());
                        fmsCodedTransaction.setTranRef(fmsBankCreditAdvice.getTranRef());
                        fmsCodedTransaction.setSubsidiaryId(fmsBankCreditAdvice.getSubsidaryId());
                        codedTransactionBeanLocal.edit(fmsCodedTransaction);

                        //update coded-transaction-data debit-side
                        fmsCodedTransaction = new FmsCodedTransaction();
                        fmsCodedTransaction.setCodedTransactonId(codedTranList.get(1).getCodedTransactonId());
                        fmsCodedTransaction.setStatus(0);
                        fmsCodedTransaction.setRecon_status(0);
                        fmsCodedTransaction.setType("credit");
                        fmsCodedTransaction.setCredit(0.0);
                        fmsCodedTransaction.setDebit(fmsBankCreditAdvice.getAmount());
                        fmsCodedTransaction.setTranRef(fmsBankCreditAdvice.getTranRef());
                        fmsCodedTransaction.setSubsidiaryId(fmsBankBranchAccounts.getSubsidiaryId().getSubsidiaryCode());
                        codedTransactionBeanLocal.edit(fmsCodedTransaction);
                        JsfUtil.addSuccessMessage("Update Successfully!");
                        saveorUpdateBundle = "Save";
                        clearPage();
                        clearAccCodes();
                    }
                } catch (Exception e) {
                    JsfUtil.addFatalMessage("Failed to Update! Trry again reloading the page.");
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
        fmsBankCreditAdvice = null;
        fmsBankAccount = new FmsBankAccount();
        fmsBankBranchAccounts = new FmsBankBranchAccounts();
        fmsCodedTransaction = new FmsCodedTransaction();
        creditDataModel = new ListDataModel<>();
        clearAccCodes();
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

    //create and serach render
    public void createNewCredit() {
        clearPage();
        saveorUpdateBundle = "Save";
        disableBtnCreate = false;
        if (createOrSearchBundle.equals("New")) {
            renderPnlCreateCredit = true;
            renderPnlManPage = false;
            createOrSearchBundle = "Search";
            setIcone("ui-icon-search");
        } else if (createOrSearchBundle.equals("Search")) {
            renderPnlCreateCredit = false;
            renderPnlManPage = true;
            createOrSearchBundle = "New";
            setIcone("ui-icon-plus");
        }
    }
}
