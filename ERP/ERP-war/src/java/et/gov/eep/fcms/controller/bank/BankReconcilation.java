/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.controller.bank;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import org.primefaces.convert.NumberConverter;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import securityBean.SessionBean;
import webService.AAA;
import webService.EventEntry;
import webService.IAdministration;
import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.fcms.businessLogic.FmsAccountUseBeanLocal;
import et.gov.eep.fcms.businessLogic.bank.BankReconcilationLogic;
import et.gov.eep.fcms.businessLogic.bank.Bank_book_error_Logic;
import et.gov.eep.fcms.businessLogic.bank.FmsCodedTransactionBeanLocal;
import et.gov.eep.fcms.controller.ReportViewer;
import et.gov.eep.fcms.entity.FmsAccountUse;
import et.gov.eep.fcms.entity.bank.FmsBankBookError;
import et.gov.eep.fcms.entity.bank.FmsBankBranchAccounts;
import et.gov.eep.fcms.entity.Vocher.FmsCashReceiptVoucher;
import et.gov.eep.fcms.entity.Vocher.FmsChequePaymentVoucher;
import et.gov.eep.fcms.entity.bank.FmsCodedTransaction;

/**
 *
 * @author Mubarek jebel
 */
@Named(value = "bankRecon")
@ViewScoped
public class BankReconcilation implements Serializable {

    //<editor-fold defaultstate="collapsed" desc="Logic and Entity Injection point">
    @EJB
    private BankReconcilationLogic bankReconcilationLogic;
    @EJB
    private Bank_book_error_Logic bank_book_error_logic;
    @EJB
    private FmsAccountUseBeanLocal accountUseLogic;
    @EJB
    private FmsCodedTransactionBeanLocal codedTransactionBeanLocal;
    @Inject
    private FmsBankBookError bankBookError;
    @Inject
    private FmsBankBookError bankBookErroredit;
    @Inject
    private ReportViewer reportViewerbean;
    @Inject
    SessionBean SessionBean;
     //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Entity Object declaration">
    private List<FmsAccountUse> acountUsePaymentModel;
    private List<FmsCodedTransaction> withdrawalsModel = new ArrayList<>();
    private List<FmsCodedTransaction> depositsModel = new ArrayList<>();
    private List<FmsAccountUse> fmsAccountUsesmodel2 = new ArrayList<>();
    private List<FmsChequePaymentVoucher> checqPaymentModel;
    private DataModel<FmsAccountUse> accountUseModel;
    private List<FmsCashReceiptVoucher> cashReceiptUseModel;
    private List<FmsBankBookError> bankBookErrorModel;
    private List<FmsCashReceiptVoucher> selectedFmsCashReceiptVoucher;
    private List<FmsChequePaymentVoucher> selectedFmsCheqPaymentVoucher;
    private List<FmsAccountUse> selectedFmsAcountUse;
    private List<FmsAccountUse> selectedFmsAcountUse2;
    private List<FmsCodedTransaction> selectedWithdrawals;
    private List<FmsCodedTransaction> selectedDeposits;
    SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
    DecimalFormat df = new DecimalFormat("#.##");
      //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc=" Variable declaration "> 
    private Double totalAmounts = 0.00;
    private Double cheqAmounts = 0.00;
    private Double genLedAmount = 0.00;
    private Double chequeOutAmount = 0.00;
    private Double depositOutAmount = 0.00;
    private Double bookErrorAmounts = 0.00;
    private Double bankErrorAmounts = 0.00;
    private Double statementEnding = 0.00;
    private Double bankStatementAmounts = 0.00;
    private double adjustedbankbalance = 0.00;
    private double adjustedBookBalance = 0.00;
    private Double interestIncome = 0.00;
    private Double serviceCharge = 0.00;
    private Double unreconciledBalance = 0.00;
    private int bakStatus = 1;
    int updteStatus = 0;
    private boolean btnSaveRender;
    private boolean ajax;
    private boolean renderPnlCreateBank = false;
    private boolean renderPnlManPage = true;
    private boolean disableBtnCreate;
    private String reportDate;
    private String reportFormat;
    private String BankReconcilationType;
    private String book = "";
    private String date = "";
    private String location = "";
    String saveorUpdateBundle = "Save";
    private String activeIndex;
    private String createOrSearchBundle = "New";
    private String icone = "ui-icon-document";

    private NumberConverter numberConverter = new NumberConverter();

    public BankReconcilation() {
        numberConverter.setPattern("#,##0.00##");
        numberConverter.setMinIntegerDigits(1);
        numberConverter.setMaxIntegerDigits(40);
        numberConverter.setMaxFractionDigits(3);
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc=" PostConstruct declaration "> 
    @PostConstruct
    private void __init__() {
        btnSaveRender = false;
        setBankReconcilationType("1");
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc=" Getter and Setter "> 
    public NumberConverter getNumberConverter() {
        return numberConverter;
    }

    public void setNumberConverter(NumberConverter numberConverter) {
        this.numberConverter = numberConverter;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    /**
     *
     * @return
     */
    public String getLocation() {
        return location;
    }

    /**
     *
     * @param location
     */
    public void setLocation(String location) {
        this.location = location;
    }

    public String getSaveorUpdateBundle() {
        return saveorUpdateBundle;
    }

    public void setSaveorUpdateBundle(String saveorUpdateBundle) {
        this.saveorUpdateBundle = saveorUpdateBundle;
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

    public boolean isRenderPnlCreateBank() {
        return renderPnlCreateBank;
    }

    public void setRenderPnlCreateBank(boolean renderPnlCreateBank) {
        this.renderPnlCreateBank = renderPnlCreateBank;
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

    /**
     *
     * @return
     */
    public Double getBankStatementAmounts() {
        return bankStatementAmounts;
    }

    /**
     *
     * @param bankStatementAmounts
     */
    public void setBankStatementAmounts(Double bankStatementAmounts) {
        this.bankStatementAmounts = bankStatementAmounts;
    }

    /**
     *
     * @return
     */
    public int getBakStatus() {
        return bakStatus;
    }

    /**
     *
     * @param bakStatus
     */
    public void setBakStatus(int bakStatus) {
        this.bakStatus = bakStatus;
    }

    /**
     *
     * @return
     */
    public String getBankReconcilationType() {
        return BankReconcilationType;
    }

    /**
     *
     * @param BankReconcilationType
     */
    public void setBankReconcilationType(String BankReconcilationType) {
        this.BankReconcilationType = BankReconcilationType;
    }

    /**
     *
     * @return
     */
    public String getReportFormat() {
        return reportFormat;
    }

    /**
     *
     * @param reportFormat
     */
    public void setReportFormat(String reportFormat) {
        this.reportFormat = reportFormat;
    }

    /**
     *
     * @return
     */
    public boolean isBtnSaveRender() {
        return btnSaveRender;
    }

    /**
     *
     * @param btnSaveRender
     */
    public void setBtnSaveRender(boolean btnSaveRender) {
        this.btnSaveRender = btnSaveRender;
    }

    /**
     *
     * @return
     */
    public Double getUnreconciledBalance() {
        return unreconciledBalance;
    }

    /**
     *
     * @param unreconciledBalance
     */
    public void setUnreconciledBalance(Double unreconciledBalance) {
        this.unreconciledBalance = unreconciledBalance;
    }

    /**
     *
     * @return
     */
    public Double getServiceCharge() {
        return serviceCharge;
    }

    /**
     *
     * @param serviceCharge
     */
    public void setServiceCharge(Double serviceCharge) {
        this.serviceCharge = serviceCharge;
    }

    /**
     *
     * @return
     */
    public Double getInterestIncome() {
        return interestIncome;
    }

    /**
     *
     * @param interestIncome
     */
    public void setInterestIncome(Double interestIncome) {
        this.interestIncome = interestIncome;
    }

    /**
     *
     * @return
     */
    public Double getAdjustedBookBalance() {
        return adjustedBookBalance;
    }

    /**
     *
     * @param adjustedBookBalance
     */
    public void setAdjustedBookBalance(Double adjustedBookBalance) {
        this.adjustedBookBalance = adjustedBookBalance;
    }

    /**
     *
     * @return
     */
    public double getAdjustedbankbalance() {
        return adjustedbankbalance;
    }

    /**
     *
     * @param adjustedbankbalance
     */
    public void setAdjustedbankbalance(double adjustedbankbalance) {
        this.adjustedbankbalance = adjustedbankbalance;
    }

    /**
     *
     * @return
     */
    public Double getStatementEnding() {
        return statementEnding;
    }

    /**
     *
     * @param statementEnding
     */
    public void setStatementEnding(Double statementEnding) {
        this.statementEnding = statementEnding;
    }

    /**
     *
     * @return
     */
    public Double getTotalAmounts() {
        return totalAmounts;
    }

    /**
     *
     * @param totalAmounts
     */
    public void setTotalAmounts(Double totalAmounts) {
        this.totalAmounts = totalAmounts;
    }

    /**
     *
     * @return
     */
    public Double getCheqAmounts() {
        return cheqAmounts;
    }

    /**
     *
     * @param cheqAmounts
     */
    public void setCheqAmounts(Double cheqAmounts) {
        this.cheqAmounts = cheqAmounts;
    }

    /**
     *
     * @return
     */
    public Double getGenLedAmount() {
        return genLedAmount;
    }

    /**
     *
     * @param genLedAmount
     */
    public void setGenLedAmount(Double genLedAmount) {
        this.genLedAmount = genLedAmount;
    }

    /**
     *
     * @return
     */
    public Double getChequeOutAmount() {
        return chequeOutAmount;
    }

    /**
     *
     * @param chequeOutAmount
     */
    public void setChequeOutAmount(Double chequeOutAmount) {
        this.chequeOutAmount = chequeOutAmount;
    }

    /**
     *
     * @return
     */
    public Double getDepositOutAmount() {
        return depositOutAmount;
    }

    /**
     *
     * @param depositOutAmount
     */
    public void setDepositOutAmount(Double depositOutAmount) {
        this.depositOutAmount = depositOutAmount;
    }

    /**
     *
     * @return
     */
    public DataModel<FmsAccountUse> getAccountUseModel() {
        if (accountUseModel == null) {
            accountUseModel = new ListDataModel();
        }
        return accountUseModel;
    }

    /**
     *
     * @param accountUseModel
     */
    public void setAccountUseModel(DataModel<FmsAccountUse> accountUseModel) {
        this.accountUseModel = accountUseModel;
    }

    /**
     *
     * @return
     */
    public List<FmsCashReceiptVoucher> getCashReceiptUseModel() {
        if (cashReceiptUseModel == null) {
            cashReceiptUseModel = new ArrayList<>();
        }

        return cashReceiptUseModel;
    }

    /**
     *
     * @param cashReceiptUseModel
     */
    public void setCashReceiptUseModel(List<FmsCashReceiptVoucher> cashReceiptUseModel) {
        this.cashReceiptUseModel = cashReceiptUseModel;
    }

    /**
     *
     * @return
     */
    public List<FmsChequePaymentVoucher> getChecqPaymentModel() {
        if (checqPaymentModel == null) {
            checqPaymentModel = new ArrayList<>();
        }
        return checqPaymentModel;
    }

    public FmsCodedTransactionBeanLocal getCodedTransactionBeanLocal() {
        return codedTransactionBeanLocal;
    }

    public void setCodedTransactionBeanLocal(FmsCodedTransactionBeanLocal codedTransactionBeanLocal) {
        this.codedTransactionBeanLocal = codedTransactionBeanLocal;
    }

    /**
     *
     * @param checqPaymentModel
     */
    public void setChecqPaymentModel(List<FmsChequePaymentVoucher> checqPaymentModel) {
        this.checqPaymentModel = checqPaymentModel;
    }

    /**
     *
     * @return
     */
    public List<FmsChequePaymentVoucher> getSelectedFmsCheqPaymentVoucher() {
        return selectedFmsCheqPaymentVoucher;
    }

    /**
     *
     * @param selectedFmsCheqPaymentVoucher
     */
    public void setSelectedFmsCheqPaymentVoucher(List<FmsChequePaymentVoucher> selectedFmsCheqPaymentVoucher) {
        this.selectedFmsCheqPaymentVoucher = selectedFmsCheqPaymentVoucher;
    }

    /**
     *
     * @return
     */
    public List<FmsAccountUse> getSelectedFmsAcountUse() {
        return selectedFmsAcountUse;
    }

    /**
     *
     * @param selectedFmsAcountUse
     */
    public void setSelectedFmsAcountUse(List<FmsAccountUse> selectedFmsAcountUse) {
        this.selectedFmsAcountUse = selectedFmsAcountUse;
    }

    /**
     *
     * @return
     */
    public List<FmsAccountUse> getSelectedFmsAcountUse2() {
        return selectedFmsAcountUse2;
    }

    /**
     *
     * @param selectedFmsAcountUse2
     */
    public void setSelectedFmsAcountUse2(List<FmsAccountUse> selectedFmsAcountUse2) {
        this.selectedFmsAcountUse2 = selectedFmsAcountUse2;
    }

    public List<FmsCodedTransaction> getSelectedDeposits() {
        return selectedDeposits;
    }

    public void setSelectedDeposits(List<FmsCodedTransaction> selectedDeposits) {
        this.selectedDeposits = selectedDeposits;
    }

    public List<FmsCodedTransaction> getSelectedWithdrawals() {
        return selectedWithdrawals;
    }

    public void setSelectedWithdrawals(List<FmsCodedTransaction> selectedWithdrawals) {
        this.selectedWithdrawals = selectedWithdrawals;
    }

    /**
     *
     * @return
     */
    public FmsBankBookError getBankBookError() {
        if (bankBookError == null) {
            bankBookError = new FmsBankBookError();
        }
        return bankBookError;
    }

    /**
     *
     * @param bankBookError
     */
    public void setBankBookError(FmsBankBookError bankBookError) {
        this.bankBookError = bankBookError;
    }

    /**
     *
     * @return
     */
    public List<FmsBankBookError> getBankBookErrorModel() {
        if (bankBookErrorModel == null) {
            bankBookErrorModel = new ArrayList();
        }
        return bankBookErrorModel;
    }

    /**
     *
     * @param bankBookErrorModel
     */
    public void setBankBookErrorModel(List<FmsBankBookError> bankBookErrorModel) {
        this.bankBookErrorModel = bankBookErrorModel;
    }

    /**
     *
     * @return
     */
    public Double getBookErrorAmounts() {
        return bookErrorAmounts;
    }

    /**
     *
     * @param bookErrorAmounts
     */
    public void setBookErrorAmounts(Double bookErrorAmounts) {
        this.bookErrorAmounts = bookErrorAmounts;
    }

    /**
     *
     * @return
     */
    public Double getBankErrorAmounts() {
        return bankErrorAmounts;
    }

    /**
     *
     * @param bankErrorAmounts
     */
    public void setBankErrorAmounts(Double bankErrorAmounts) {
        this.bankErrorAmounts = bankErrorAmounts;
    }

    /**
     *
     * @return
     */
    public List<FmsCashReceiptVoucher> getSelectedFmsCashReceiptVoucher() {
        return selectedFmsCashReceiptVoucher;
    }

    /**
     *
     * @param selectedFmsCashReceiptVoucher
     */
    public void setSelectedFmsCashReceiptVoucher(List<FmsCashReceiptVoucher> selectedFmsCashReceiptVoucher) {
        this.selectedFmsCashReceiptVoucher = selectedFmsCashReceiptVoucher;
    }

    /**
     *
     * @return
     */
    public FmsBankBookError getBankBookErroredit() {
        return bankBookErroredit;
    }

    /**
     *
     * @param bankBookErroredit
     */
    public void setBankBookErroredit(FmsBankBookError bankBookErroredit) {
        this.bankBookErroredit = bankBookErroredit;
    }

    /**
     *
     * @return
     */
    public List<FmsBankBranchAccounts> getBankReconcilationList() {
        return bankReconcilationLogic.findAll();
    }

    /**
     *
     * @return
     */
    public List<FmsAccountUse> getAcountUsePaymentModel() {
        if (acountUsePaymentModel == null) {
            acountUsePaymentModel = new ArrayList();
        }
        return acountUsePaymentModel;
    }

    public List<FmsCodedTransaction> getWithdrawalsModel() {
        if (withdrawalsModel == null) {
            withdrawalsModel = new ArrayList();
        }
        return withdrawalsModel;
    }

    public void setWithdrawalsModel(List<FmsCodedTransaction> withdrawalsModel) {
        this.withdrawalsModel = withdrawalsModel;
    }

    public List<FmsCodedTransaction> getDepositsModel() {
        return depositsModel;
    }

    public void setDepositsModel(List<FmsCodedTransaction> depositsModel) {
        if (depositsModel == null) {
            depositsModel = new ArrayList();
        }
        this.depositsModel = depositsModel;
    }

    /**
     *
     * @param acountUsePaymentModel
     */
    public void setAcountUsePaymentModel(List<FmsAccountUse> acountUsePaymentModel) {
        this.acountUsePaymentModel = acountUsePaymentModel;
    }

    /**
     *
     * @return
     */
    public List<FmsAccountUse> getFmsAccountUsesmodel2() {
        if (fmsAccountUsesmodel2 == null) {
            fmsAccountUsesmodel2 = new ArrayList();
        }
        return fmsAccountUsesmodel2;
    }

    /**
     *
     * @param fmsAccountUsesmodel2
     */
    public void setFmsAccountUsesmodel2(List<FmsAccountUse> fmsAccountUsesmodel2) {
        this.fmsAccountUsesmodel2 = fmsAccountUsesmodel2;
    }

    /**
     *
     * @return
     */
    public boolean isAjax() {
        return ajax;
    }

    /**
     *
     * @param ajax
     */
    public void setAjax(boolean ajax) {
        this.ajax = ajax;
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc=" Change In  InReconcilation "> 
    /**
     *
     * @param e
     */
    public void changeInReconcilationList(ValueChangeEvent e) {
        if (null != e.getNewValue()) {
            book = e.getNewValue().toString();
            System.out.println("------e.getNewValue()----" + book);
        }
    }

    /**
     *
     * @param e
     */
    public void changeInReconcilation(SelectEvent e) {
        if (null != e.getObject()) {
            date = format.format(e.getObject());
            depositsModel = null;
            withdrawalsModel = codedTransactionBeanLocal.getChequePaymentVouchers(book, date);
            depositsModel = codedTransactionBeanLocal.getCashReceiptVouchers(book, date);
            for (FmsCodedTransaction withdrawalsModel1 : withdrawalsModel) {
                chequeOutAmount = Double.parseDouble(df.format(chequeOutAmount + withdrawalsModel1.getDebit()));
            }
            for (FmsCodedTransaction depositsModel1 : depositsModel) {
                depositOutAmount = Double.parseDouble(df.format(depositOutAmount + depositsModel1.getCredit()));
            }
            String generalLedgerVal = codedTransactionBeanLocal.getGLBankReco(date.substring(0, 7), date, book);
            if (generalLedgerVal != null) {
                if (Double.parseDouble(generalLedgerVal) > 0) {
                    genLedAmount = Double.parseDouble((generalLedgerVal));
                } else {
                    genLedAmount = -1 * Double.parseDouble(df.format(generalLedgerVal));
                }
            }
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc=" Other Functionality Including Save and Display "> 
    /**
     *
     * @throws ParseException
     */
    public void saveBookandBankError() throws ParseException {
        AAA securityService = new AAA();
        IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
        String systemBundle = "et/gov/eep/commonApplications/securityServer";
        String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
        if (security.checkAccess(SessionBean.getUserName(), "saveBookandBankError", dataset)) {
            bankBookError.setDates(format.format(bankBookError.getDateval()));
            bankBookError.setBankAccountNo(book);
            if (bankBookError.getType().contains("0")) {
                setBankErrorAmounts(getBankErrorAmounts() + bankBookError.getAmount());
            } else if (bankBookError.getType().contains("1")) {
                setBookErrorAmounts(getBookErrorAmounts() + bankBookError.getAmount());
            } else {
                setStatementEnding(getBankStatementAmounts() + bankBookError.getAmount());
            }

            displayBookBankEroor();
            bank_book_error_logic.saveOrUpdate(bankBookError);
            bankBookError = null;
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
    }

    /**
     *
     */
    public void displayBookBankEroor() {
        bankBookErrorModel.clear();
        bankBookErrorModel.add(bankBookError);
    }

    /**
     *
     * @param e
     */
    public void selectDateReport(SelectEvent e) {
        if (null != e.getObject()) {
            reportDate = format.format(e.getObject());
        }
    }

    /**
     *
     */
    public void adjust() {
        adjustedbankbalance = Double.parseDouble(df.format(getStatementEnding() + getDepositOutAmount() - getChequeOutAmount() + getBankErrorAmounts()));
        adjustedBookBalance = Double.parseDouble(df.format(getGenLedAmount() - getServiceCharge() + getInterestIncome() + getBookErrorAmounts()));
        if (adjustedBookBalance > adjustedbankbalance) {
            unreconciledBalance = Double.parseDouble(df.format(adjustedBookBalance - adjustedbankbalance));
        } else {
            unreconciledBalance = Double.parseDouble(df.format(adjustedbankbalance - adjustedBookBalance));
        }
        if (adjustedBookBalance == adjustedbankbalance) {
            btnSaveRender = true;
        }
    }

    /**
     *
     */
    public void saveBankReconciliationStatus() {
        AAA securityService = new AAA();
        IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
        String systemBundle = "et/gov/eep/commonApplications/securityServer";
        String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
        if (security.checkAccess(SessionBean.getUserName(), "saveBankReconciliationStatus", dataset)) {
            for (int i = 0; i < getSelectedDeposits().size(); i++) {
                FmsCodedTransaction fct = new FmsCodedTransaction();
                fct.setCodedTransactonId(getSelectedDeposits().get(i).getCodedTransactonId());
                fct.setCredit(getSelectedDeposits().get(i).getCredit());
                fct.setDebit(getSelectedDeposits().get(i).getDebit());
                fct.setSubsidiaryId(getSelectedDeposits().get(i).getSubsidiaryId());
                fct.setStatus(1);
                fct.setTranRef(getSelectedDeposits().get(i).getTranRef());
                fct.setType(getSelectedDeposits().get(i).getType());
                fct.setRecon_status(1);//Reconciliaton status from 0 to 1
            }
            for (int i = 0; i < getSelectedWithdrawals().size(); i++) {
                FmsCodedTransaction fct = new FmsCodedTransaction();
                fct.setCodedTransactonId(getSelectedWithdrawals().get(i).getCodedTransactonId());
                fct.setCodedTransactonId(getSelectedWithdrawals().get(i).getCodedTransactonId());
                fct.setCredit(getSelectedWithdrawals().get(i).getCredit());
                fct.setDebit(getSelectedWithdrawals().get(i).getDebit());
                fct.setSubsidiaryId(getSelectedWithdrawals().get(i).getSubsidiaryId());
                fct.setStatus(1);
                fct.setTranRef(getSelectedWithdrawals().get(i).getTranRef());
                fct.setType(getSelectedWithdrawals().get(i).getType());
                fct.setRecon_status(1);//Reconciliaton status from 0 to 1
            }
            JsfUtil.addSuccessMessage("Reconciled Successfully!");
            btnSaveRender = false;
            cleanPage();
        } else {
            EventEntry eventEntry = new EventEntry();
            eventEntry.setSessionId(SessionBean.getSessionID());
            eventEntry.setUserId(SessionBean.getUserId());
            QName qualifiedName = new QName("", "project");
            JAXBElement<String> test = new JAXBElement<String>(qualifiedName, String.class, null, SessionBean.getUserName());
            eventEntry.setUserLogin(test);
            security.addEventLog(eventEntry, dataset);

        }
    }

    /**
     *
     * @param account
     * @return
     */
    public String returnGlWithSubCode(FmsBankBranchAccounts account) {
        String glWithSubCode = "";
        if (null != account) {
            glWithSubCode = account.getAccountNumber();
        }
        return glWithSubCode;
    }
     //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc=" onRowSelect Methods "> 
    /**
     *
     * @param event
     */
    public void onRowSelectCashReceiptVoucher(SelectEvent event) {

        if (event.getObject() != null) {
            if (depositOutAmount == 0) {
                depositOutAmount = Double.parseDouble(df.format(totalAmounts - ((FmsCodedTransaction) event.getObject()).getCredit()));
            } else {
                depositOutAmount = Double.parseDouble(df.format(depositOutAmount - ((FmsCodedTransaction) event.getObject()).getCredit()));
            }

        }

    }

    /**
     *
     * @param event
     */
    public void onRowUnselectCashReceiptVoucher(UnselectEvent event) {
        try {
            if (event.getObject() != null) {
                depositOutAmount = Double.parseDouble(df.format(depositOutAmount + ((FmsCodedTransaction) event.getObject()).getCredit()));
            }
        } catch (Exception ex) {
        }
    }

    /**
     *
     * @param event
     */
    public void onRowSelectChecqPayment(SelectEvent event) {
        if (event.getObject() != null) {
            if (chequeOutAmount == 0) {
                chequeOutAmount = Double.parseDouble(df.format(totalAmounts - ((FmsCodedTransaction) event.getObject()).getDebit()));
            } else {
                chequeOutAmount = Double.parseDouble(df.format(chequeOutAmount - ((FmsCodedTransaction) event.getObject()).getDebit()));
            }
        }

    }

    /**
     *
     * @param event
     */
    public void onRowUnselectChecqPayment(UnselectEvent event) {
        try {
            if (event.getObject() != null) {
                chequeOutAmount = Double.parseDouble(df.format(chequeOutAmount + ((FmsCodedTransaction) event.getObject()).getDebit()));
            }
        } catch (Exception ex) {
        }
    }

    /**
     *
     * @param event
     */
    public void onRowBookErrorTable(SelectEvent event) {
        try {
            if (event.getObject() != null) {
                bankBookError = ((FmsBankBookError) event.getObject());
            }
        } catch (Exception ex) {
        }
    }

    //</editor-fold>    
    //<editor-fold defaultstate="collapsed" desc=" Generate Report Method "> 
    /**
     *
     * @param e
     */
    public void selectReportType(ValueChangeEvent e) {
        if (null != e.getNewValue() && !e.getNewValue().equals("")) {
            ajax = e.getNewValue().toString().equalsIgnoreCase("html");
        }
    }
    //</editor-fold>

    //clear
    private void cleanPage() {
        selectedWithdrawals.clear();
        selectedDeposits.clear();
        withdrawalsModel.clear();
        depositsModel.clear();
        bankBookErrorModel.clear();
        bankBookError = null;
        getBankReconcilationList().clear();
        date = null;

    }

    //create and search render
    public void createNewRecon() {
        saveorUpdateBundle = "Save";
        disableBtnCreate = false;
        if (createOrSearchBundle.equals("New")) {
            renderPnlCreateBank = true;
            renderPnlManPage = false;
            createOrSearchBundle = "Search";
            setIcone("ui-icon-search");
        } else if (createOrSearchBundle.equals("Search")) {
            renderPnlCreateBank = false;
            renderPnlManPage = true;
            createOrSearchBundle = "New";
            setIcone("ui-icon-document");
        }
    }
}
