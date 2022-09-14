/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.controller.loan;
//<editor-fold defaultstate="collapsed" desc="Inject">

import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.fcms.businessLogic.bank.bankDebitAdviceBeanLocal;
import et.gov.eep.fcms.businessLogic.loan.FmsDisbursementBeanLocal;
import et.gov.eep.fcms.businessLogic.loan.FmsLoanBeanLocal;
import et.gov.eep.fcms.businessLogic.loan.FmsLoanCommitmentBeanLocal;
import et.gov.eep.fcms.businessLogic.loan.FmsLoanPaymentSummaryBeanLocal;
import et.gov.eep.fcms.businessLogic.loan.FmsLoanRepaymentBeanLocal;
import et.gov.eep.fcms.entity.bank.FmsBankDebitAdvice;
import et.gov.eep.fcms.entity.loan.FmsLoan;
import et.gov.eep.fcms.entity.loan.FmsLoanCommitment;
import et.gov.eep.fcms.entity.loan.FmsLoanDisbursement;
import et.gov.eep.fcms.entity.loan.FmsLoanPaymentSummary;
import et.gov.eep.fcms.entity.loan.FmsLoanRepayment;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
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
import org.primefaces.event.RowEditEvent;
import org.primefaces.event.SelectEvent;
import securityBean.SessionBean;
import webService.AAA;
import webService.EventEntry;
import webService.IAdministration;
//</editor-fold>

/**
 *
 * @author Binyam
 */
@Named(value = "disbursementRegController")
@ViewScoped
public class DisbursementRegController implements Serializable {
//<editor-fold defaultstate="collapsed" desc="Inject">

    @Inject
    SessionBean SessionBean;

    @Inject
    FmsLoan fmsLoan;
    @Inject
    FmsLoanRepayment fmsLRpt;
    @Inject
    FmsLoanDisbursement fmsLoanDisbursement;
    @Inject
    FmsLoanPaymentSummary fmsLPS;
    @Inject
    FmsLoanCommitment fmsLoanCommitment;
    @Inject
    FmsBankDebitAdvice fmsBankDebitAdvice;
//</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="EJB">
    @EJB
    FmsLoanBeanLocal fmsLoanBeanLocal;
    @EJB
    FmsDisbursementBeanLocal fmsDisbursementBeanLocal;
    @EJB
    FmsLoanRepaymentBeanLocal fmsLoanRepaymentBeanLocal;

    @EJB
    FmsLoanCommitmentBeanLocal fmsLoanCommitmentBeanLocal;
    @EJB
    FmsLoanPaymentSummaryBeanLocal fmslpsBeanLocal;
    @EJB
    bankDebitAdviceBeanLocal adviceBeanLocal;
//</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="variyable declaration">
 DataModel<FmsLoanDisbursement> disbursedataModel;
    DataModel<FmsLoanRepayment> utilisationdataModel;
    DataModel<FmsLoanRepayment> repaymentdataModel;
    DataModel<FmsLoanPaymentSummary> paymentSummaryModel;
    List<FmsLoanDisbursement> disburseList;
    ArrayList<FmsLoanRepayment> utilisationList;
    ArrayList<FmsLoanRepayment> repaymentList;
    ArrayList<FmsLoanPaymentSummary> paymentsummaryList;
    List<FmsBankDebitAdvice> bankDebitAdviceList;
    private NumberConverter numberConverter = new NumberConverter();
    boolean readwrite = true;
    boolean interestCalendare = true;

    FmsLoan selectedLoan;
    DataModel<FmsLoan> loanDatamodel;
    List<FmsLoan> loanList;

    private String createOrSearchBundle = "New";
    private String headerTitle = "Loan Disbursement and Repayment";
    private String icone = "ui-icon-plus";
    private boolean disableBtnCreate;
    private boolean renderPnlCreateGatePass = false;
    private boolean renderPnlManPage = true;
    private boolean renderNewSrcBtn = false;
//</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="DisbursementRegController">
     public DisbursementRegController() {
        numberConverter.setPattern("#,##0.00##");
        numberConverter.setMinIntegerDigits(1);
        numberConverter.setMaxIntegerDigits(50);
        numberConverter.setMaxFractionDigits(2);
    }
//</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="getter and setters">
     public FmsLoan getSelectedLoan() {
        return selectedLoan;
    }

    public void setSelectedLoan(FmsLoan selectedLoan) {
        this.selectedLoan = selectedLoan;
    }

    public DataModel<FmsLoan> getLoanDatamodel() {
        return loanDatamodel;
    }

    public void setLoanDatamodel(DataModel<FmsLoan> loanDatamodel) {
        this.loanDatamodel = loanDatamodel;
    }

    public List<FmsLoan> getLoanList() {
        return loanList;
    }

    public void setLoanList(List<FmsLoan> loanList) {
        this.loanList = loanList;
    }

    public boolean isRenderNewSrcBtn() {
        return renderNewSrcBtn;
    }

    public void setRenderNewSrcBtn(boolean renderNewSrcBtn) {
        this.renderNewSrcBtn = renderNewSrcBtn;
    }

    public String getCreateOrSearchBundle() {
        return createOrSearchBundle;
    }

    public void setCreateOrSearchBundle(String createOrSearchBundle) {
        this.createOrSearchBundle = createOrSearchBundle;
    }

    public String getHeaderTitle() {
        return headerTitle;
    }

    public void setHeaderTitle(String headerTitle) {
        this.headerTitle = headerTitle;
    }

    public String getIcone() {
        return icone;
    }

    public void setIcone(String icone) {
        this.icone = icone;
    }

    public boolean isDisableBtnCreate() {
        return disableBtnCreate;
    }

    public void setDisableBtnCreate(boolean disableBtnCreate) {
        this.disableBtnCreate = disableBtnCreate;
    }

    public boolean isRenderPnlCreateGatePass() {
        return renderPnlCreateGatePass;
    }

    public void setRenderPnlCreateGatePass(boolean renderPnlCreateGatePass) {
        this.renderPnlCreateGatePass = renderPnlCreateGatePass;
    }

    public boolean isRenderPnlManPage() {
        return renderPnlManPage;
    }

    public void setRenderPnlManPage(boolean renderPnlManPage) {
        this.renderPnlManPage = renderPnlManPage;
    }

    public boolean isReadwrite() {
        return readwrite;
    }

    /**
     *
     * @param readwrite
     */
    public void setReadwrite(boolean readwrite) {
        this.readwrite = readwrite;
    }

    /**
     *
     * @return
     */
    public boolean isInterestCalendare() {
        return interestCalendare;
    }

    /**
     *
     * @param interestCalendare
     */
    public void setInterestCalendare(boolean interestCalendare) {
        this.interestCalendare = interestCalendare;
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

    /**
     *
     * @return
     */
    public FmsLoan getFmsLoan() {
        if (fmsLoan == null) {
            fmsLoan = new FmsLoan();
        }
        return fmsLoan;
    }

    /**
     *
     * @param fmsLoan
     */
    public void setFmsLoan(FmsLoan fmsLoan) {
        this.fmsLoan = fmsLoan;
    }

    /**
     *
     * @return
     */
    public FmsLoanDisbursement getFmsLoanDisbursement() {
        if (fmsLoanDisbursement == null) {
            fmsLoanDisbursement = new FmsLoanDisbursement();
        }
        return fmsLoanDisbursement;
    }

    /**
     *
     * @param fmsLoanDisbursement
     */
    public void setFmsLoanDisbursement(FmsLoanDisbursement fmsLoanDisbursement) {
        this.fmsLoanDisbursement = fmsLoanDisbursement;
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

    public FmsLoanRepayment getFmsLRpt() {
        if (fmsLRpt == null) {
            fmsLRpt = new FmsLoanRepayment();
        }
        return fmsLRpt;
    }

    public void setFmsLRpt(FmsLoanRepayment fmsLRpt) {
        this.fmsLRpt = fmsLRpt;
    }

    public FmsLoanCommitment getFmsLoanCommitment() {
        if (fmsLoanCommitment == null) {
            fmsLoanCommitment = new FmsLoanCommitment();
        }
        return fmsLoanCommitment;
    }

    /**
     *
     * @param fmsLoanCommitment
     */
    public void setFmsLoanCommitment(FmsLoanCommitment fmsLoanCommitment) {
        this.fmsLoanCommitment = fmsLoanCommitment;
    }

    /**
     *
     * @return
     */
    public FmsLoanCommitmentBeanLocal getFmsLoanCommitmentBeanLocal() {
        return fmsLoanCommitmentBeanLocal;
    }

    /**
     *
     * @param fmsLoanCommitmentBeanLocal
     */
    public void setFmsLoanCommitmentBeanLocal(FmsLoanCommitmentBeanLocal fmsLoanCommitmentBeanLocal) {
        this.fmsLoanCommitmentBeanLocal = fmsLoanCommitmentBeanLocal;
    }

    /**
     *
     * @return
     */
    public FmsLoanBeanLocal getFmsLoanBeanLocal() {
        return fmsLoanBeanLocal;
    }

    /**
     *
     * @param fmsLoanBeanLocal
     */
    public void setFmsLoanBeanLocal(FmsLoanBeanLocal fmsLoanBeanLocal) {
        this.fmsLoanBeanLocal = fmsLoanBeanLocal;
    }

    public FmsLoanPaymentSummary getFmsLoanPaymentSummary() {
        if (fmsLPS == null) {
            fmsLPS = new FmsLoanPaymentSummary();
        }
        return fmsLPS;
    }

    public void setFmsLoanPaymentSummary(FmsLoanPaymentSummary fmsLPS) {
        this.fmsLPS = fmsLPS;
    }

    public ArrayList<FmsLoanPaymentSummary> getPaymentsummaryList() {
        if (paymentsummaryList == null) {
            paymentsummaryList = new ArrayList<>();
        }
        return paymentsummaryList;
    }

    public void setPaymentsummaryList(ArrayList<FmsLoanPaymentSummary> paymentsummaryList) {
        this.paymentsummaryList = paymentsummaryList;
    }

    public DataModel<FmsLoanDisbursement> getDisbursedataModel() {
        if (disbursedataModel == null) {
            disbursedataModel = new ListDataModel<>();
        }
        return disbursedataModel;
    }

    /**
     *
     * @param disbursedataModel
     */
    public void setDisbursedataModel(DataModel<FmsLoanDisbursement> disbursedataModel) {
        this.disbursedataModel = disbursedataModel;
    }

    public DataModel<FmsLoanRepayment> getRepaymentdataModel() {
        if (repaymentdataModel == null) {
            repaymentdataModel = new ListDataModel<>();
        }
        return repaymentdataModel;
    }

    public void setRepaymentdataModel(DataModel<FmsLoanRepayment> repaymentdataModel) {
        this.repaymentdataModel = repaymentdataModel;
    }

    public DataModel<FmsLoanPaymentSummary> getPaymentSummaryModel() {
        if (paymentSummaryModel == null) {
            paymentSummaryModel = new ListDataModel<>();
        }
        return paymentSummaryModel;
    }

    public void setPaymentSummaryModel(DataModel<FmsLoanPaymentSummary> paymentSummaryModel) {
        this.paymentSummaryModel = paymentSummaryModel;
    }

    public List<FmsBankDebitAdvice> getBankDebitAdviceList() {
        if (bankDebitAdviceList == null) {
            bankDebitAdviceList = adviceBeanLocal.searchAllDebits(null);
        }
        return bankDebitAdviceList;
    }

    public void setBankDebitAdviceList(List<FmsBankDebitAdvice> bankDebitAdviceList) {
        this.bankDebitAdviceList = bankDebitAdviceList;
    }

    public DataModel<FmsLoanRepayment> getUtilisationdataModel() {
        if (utilisationdataModel == null) {
            utilisationdataModel = new ListDataModel<>();
        }
        return utilisationdataModel;
    }

    public void setUtilisationdataModel(DataModel<FmsLoanRepayment> utilisationdataModel) {
        this.utilisationdataModel = utilisationdataModel;
    }

    /**
     *
     * @return
     */
    public List<FmsLoanDisbursement> getDisburseList() {
        if (disburseList == null) {
            disburseList = new ArrayList<>();
        }
        return disburseList;
    }

    /**
     *
     * @param disburseList
     */
    public void setDisburseList(List<FmsLoanDisbursement> disburseList) {
        this.disburseList = disburseList;
    }
//</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="other methods">
     public ArrayList<FmsLoan> SearchLoan(String loanNo) {
        ArrayList<FmsLoan> loans = null;
        fmsLoan.setLoanNo(loanNo);
        loans = fmsLoanBeanLocal.searchFmsLoanByLoanNo(fmsLoan);
        return loans;
    }

    public void bankAdviceSetter(ValueChangeEvent event) {
        if (!event.getNewValue().toString().isEmpty()) {
            fmsBankDebitAdvice.setDebitAdviceId(Integer.parseInt(event.getNewValue().toString()));
            fmsBankDebitAdvice.setDebitAdviceId(Integer.parseInt(event.getNewValue().toString()));
            fmsBankDebitAdvice = adviceBeanLocal.getBankAdviceData(fmsBankDebitAdvice);
            fmsLPS.setBankAdviceFk(fmsBankDebitAdvice);
        }
    }

    public void DisbursementLister(FmsLoanDisbursement LoanDisbursement) {
        if (fmsLoanDisbursement != null) {
            disburseList = fmsDisbursementBeanLocal.fetchDisbursement(fmsLoanDisbursement);
            disbursedataModel = new ListDataModel(new ArrayList(disburseList));
        }
    }

    /**
     *
     */
    public void recreateDibursementDModel() {
        disbursedataModel = null;
        disbursedataModel = new ListDataModel(getDisburseList());
    }

    static Comparator<FmsLoanRepayment> sortByLoanRepaymentId = (FmsLoanRepayment obj1, FmsLoanRepayment obj2) -> obj1.getLoanRepaymentId().compareTo(obj2.getLoanRepaymentId());

    public void recreaterepaymentDModel() {
        utilisationdataModel = new ListDataModel<>();
        utilisationList = new ArrayList<>();
        for (int i = 0; i < getDisburseList().size(); i++) {
            for (int j = 0; j < getDisburseList().get(i).getFmsLoanRepaymentList().size(); j++) {
                utilisationList.add(getDisburseList().get(i).getFmsLoanRepaymentList().get(j));
            }
        }
        Collections.sort(utilisationList, sortByLoanRepaymentId);
        utilisationdataModel = new ListDataModel(utilisationList);
    }

    public void fetchRepaymentSchedule() {
        repaymentList = new ArrayList<>();
        repaymentdataModel = new ListDataModel<>();
        repaymentList = fmsLoanRepaymentBeanLocal.searchLoanRepayment(fmsLoan);
        repaymentdataModel = new ListDataModel(repaymentList);
    }

    public void fetchPaymentSummary() {
        paymentsummaryList = new ArrayList<>();
        paymentSummaryModel = new ListDataModel<>();
        paymentsummaryList = fmslpsBeanLocal.fetchLoanPaymentSummary(fmsLoan);
        paymentSummaryModel = new ListDataModel(paymentsummaryList);
    }

    public void reset() {
        fmsLoan = null;
        fmsLoanDisbursement = null;
        disburseList = null;
        disbursedataModel = null;
        readwrite = true;
        loanDatamodel = null;
        utilisationdataModel = null;
        utilisationList = null;
        repaymentList = null;
        repaymentdataModel = null;
        paymentsummaryList = null;
        paymentSummaryModel = null;

    }

    public void saveRepaymentSchedule() throws ParseException {
        BigDecimal tempBalance = BigDecimal.ZERO;
        BigDecimal tempClosingBalance = BigDecimal.ZERO;
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        Date jan1 = fmt.parse("2007-01-01");
        Date jul31 = fmt.parse("2007-07-31");
        Date disbDate = fmsLoanDisbursement.getDisbursementDate();
        Date repscheduledate = fmsLoanDisbursement.getDisbursementDate();
        Date prevDisbDate = fmsLoanDisbursement.getDisbursementDate();
        jan1.setYear(disbDate.getYear());
        jul31.setYear(disbDate.getYear());
        int i = 0;
        do {
            if (i == 0) {
                if (disbDate.after(jan1) && disbDate.before(jul31)) {
                    Calendar tempCal1 = Calendar.getInstance();
                    tempCal1.setTime(disbDate);
                    tempCal1.set(Calendar.MONTH, 6);
                    tempCal1.set(Calendar.DATE, 31);
                    disbDate = tempCal1.getTime();
                    repscheduledate = disbDate;
                    prevDisbDate = fmsLoanDisbursement.getDisbursementDate();
                } else {
                    Calendar tempCal2 = Calendar.getInstance();
                    tempCal2.setTime(disbDate);
                    tempCal2.set(Calendar.MONTH, 0);
                    tempCal2.set(Calendar.DATE, 31);
                    disbDate = tempCal2.getTime();
                    repscheduledate = disbDate;
                }
                fmsLRpt.setScheduleDate(repscheduledate);
            } else {
                fmsLRpt.setScheduleDate(repscheduledate);
            }
            i = 1;
            long days = Math.abs((int) TimeUnit.MILLISECONDS.toDays(repscheduledate.getTime() - prevDisbDate.getTime()));
            if (fmsLRpt.getScheduleDate().before(fmsLoan.getPrincipalRepaymentDate())) {
                fmsLRpt.setOpeningBalance(fmsLoanDisbursement.getAmount());
                fmsLRpt.setClosingBalance(fmsLoanDisbursement.getAmount());
            } else {
                fmsLRpt.setOpeningBalance(tempClosingBalance);
                fmsLRpt.setCapitalRepayment(fmsLoanDisbursement.getAmount().divide(BigDecimal.valueOf(fmsLoan.getNoInstallment()), 2, RoundingMode.HALF_EVEN));
                fmsLRpt.setClosingBalance(fmsLRpt.getOpeningBalance().subtract(fmsLRpt.getCapitalRepayment()));
            }
            tempClosingBalance = fmsLRpt.getClosingBalance();
            fmsLRpt.setDisbursementFk(fmsLoanDisbursement);
            prevDisbDate.setTime(repscheduledate.getTime());
            if (fmsLoan.getFrequency().matches("Annual")) {
                Calendar cal = Calendar.getInstance();
                cal.setTime(repscheduledate);
                cal.add(Calendar.MONTH, 12);
                repscheduledate = cal.getTime();
            } else if (fmsLoan.getFrequency().matches("Semi Annual")) {
                Calendar cal = Calendar.getInstance();
                cal.setTime(repscheduledate);
                cal.add(Calendar.MONTH, 6);
                repscheduledate = cal.getTime();
            }
            fmsLRpt.setDays(BigDecimal.valueOf(days));
            fmsLRpt.setLiborRate(Float.valueOf(0));
            fmsLRpt.setMargin(BigDecimal.valueOf(fmsLRpt.getLiborRate() + fmsLoanDisbursement.getInterestRate()).setScale(2, RoundingMode.HALF_EVEN));
            fmsLRpt.setInterestRepayment(fmsLRpt.getOpeningBalance().multiply(fmsLRpt.getMargin()).multiply(fmsLRpt.getDays().divide(fmsLoan.getInterestRule(), 2, RoundingMode.HALF_EVEN)));
            fmsLoanDisbursement.getFmsLoanRepaymentList().add(fmsLRpt);
            tempBalance = tempBalance.add(fmsLRpt.getOpeningBalance());
            fmsLRpt = new FmsLoanRepayment();
        } while (repscheduledate.before(fmsLoan.getPrincipalDueDate()));
    }

    Calendar disbDateX = Calendar.getInstance();

    public void amountChecker() {
        disbDateX.setTime(fmsLoanDisbursement.getDisbursementDate());
        if (fmsLoanDisbursement.getAmount().compareTo(BigDecimal.ZERO) == 1) {
            if (fmsLoanDisbursement.getDisbursementDate().after(fmsLoan.getLoanDate()) && fmsLoanDisbursement.getDisbursementDate().before(fmsLoan.getPrincipalRepaymentDate())) {
                double amountSum = 0;
                if (!disburseList.isEmpty()) {
                    for (FmsLoanDisbursement disburseList1 : disburseList) {
                        amountSum += disburseList1.getAmount().doubleValue();
                    }
                    if (fmsLoanDisbursement.getAmount().doubleValue() <= (fmsLoan.getLoanAmount().doubleValue() - amountSum)) {
                        int lastEpisode = disburseList.get(disburseList.size() - 1).getEpisode();
                        fmsLoanDisbursement.setEpisode(lastEpisode + 1);
                        addDisbursement();
                    } else {
                        JsfUtil.addFatalMessage("The disbursed amount is higher than expected, please correct it.");
                    }
                } else if (fmsLoanDisbursement.getAmount().doubleValue() <= fmsLoan.getLoanAmount().doubleValue()) {
                    fmsLoanDisbursement.setEpisode(1);
                    addDisbursement();
                } else {
                    JsfUtil.addFatalMessage("The disbursed amount is higher than expected, please correct it.");
                }
            } else {
                JsfUtil.addFatalMessage("The disbursement date is out of range.");
            }
        } else {
            JsfUtil.addFatalMessage("The disbursement amount can not be zero.");
        }
    }

    public void addDisbursement() {
        try {
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(SessionBean.getUserName(), "addDisbursement", dataset)) {
                fmsLoanDisbursement.setLoanId(fmsLoan);
                saveRepaymentSchedule();
                fmsLoanDisbursement.setDisbursementDate(disbDateX.getTime());
                fmsDisbursementBeanLocal.create(fmsLoanDisbursement);
                DisbursementLister(fmsLoanDisbursement);
                recreaterepaymentDModel();
                fetchRepaymentSchedule();
                JsfUtil.addSuccessMessage("Loan Disbursement saved successfully. ");

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

    /**
     *
     */
    public void followUpEditor() {
        fmsLoanDisbursement = getDisbursedataModel().getRowData();
    }

    /**
     *
     */
    public void addPayment() {
        if (fmsLoanDisbursement.getInterestPayDate().getTime() >= fmsLoanDisbursement.getDisbursementDate().getTime()) {

            long disburseDate = fmsLoanDisbursement.getDisbursementDate().getTime();
            double disbrDate = disburseDate / (24 * 60 * 60 * 1000);
            long intPayDate = fmsLoanDisbursement.getInterestPayDate().getTime();
            double interertDate = intPayDate / (24 * 60 * 60 * 1000);
            fmsLoanDisbursement.setInterestAmount(((new BigDecimal((interertDate - disbrDate) / 360)).multiply(new BigDecimal(((fmsLoanDisbursement.getInterestRate() * 100) / 100))).multiply(fmsLoanDisbursement.getAmount())).setScale(2, RoundingMode.HALF_EVEN));
            disburseList.stream().forEach((schedule) -> {
                fmsDisbursementBeanLocal.edit(schedule);
            });
            fmsLoanDisbursement = new FmsLoanDisbursement();
            JsfUtil.addSuccessMessage("Interest payment is saved");

        } else {
            fmsLoanDisbursement.setInterestPayDate(null);
            fmsLoanDisbursement.setInterestAmount(null);
            JsfUtil.addFatalMessage("Date is prior to disbursed date");
        }
    }

    public void savePaymentSummary() {
        if (!repaymentList.isEmpty()) {
            for (int i = 0; i <= repaymentList.size(); i++) {
                fmslpsBeanLocal.deleteLoanPaymentSummary(fmsLoan);
            }
        }
        for (FmsLoanRepayment loanRepayments : repaymentList) {
            fmsLPS = new FmsLoanPaymentSummary();
            fmsLPS.setLoanFk(fmsLoan);
            fmsLPS.setPaymentDate(loanRepayments.getScheduleDate());
            fmsLPS.setCapitalPayment(loanRepayments.getCapitalRepayment());
            fmsLPS.setInterestPayment(loanRepayments.getInterestRepayment());
            fmsLPS.setEstimatedTotalPayment(fmsLPS.getCapitalPayment().add(fmsLPS.getInterestPayment()));
            fmsLPS.setDefaultInterestRate(BigDecimal.ZERO);
            fmsLPS.setDefaultInterestAmount(BigDecimal.ZERO);
            fmsLPS.setExchangeRate(BigDecimal.ZERO);
            fmsLPS.setPaymentInBirr(BigDecimal.ZERO);
            fmsLPS.setManagementFee(BigDecimal.ZERO);
            fmsLPS.setOtherPayment(BigDecimal.ZERO);
            fmsLPS.setCommitmentFee(BigDecimal.ZERO);
            fmsLPS.setTotalPayment(BigDecimal.ZERO);
            fmslpsBeanLocal.create(fmsLPS);
        }

    }

    public void loanSearch() {
        if (fmsLoan.getLoanNo().isEmpty()) {
            loanList = fmsLoanBeanLocal.findAll();
            loanDatamodel = new ListDataModel(new ArrayList(loanList));
        } else {
            loanList = fmsLoanBeanLocal.searchFmsLoanByLoanNo(fmsLoan);
            loanDatamodel = new ListDataModel(new ArrayList(loanList));
        }
    }

    public void getByLoanNO(SelectEvent event) {
        fmsLoan = (FmsLoan) event.getObject();
        fmsLoan = fmsLoanBeanLocal.getFmsLoanInfo(fmsLoan);
        fmsLoanDisbursement = new FmsLoanDisbursement();
        fmsLoanDisbursement.setLoanId(fmsLoan);
        DisbursementLister(fmsLoanDisbursement);
        recreaterepaymentDModel();
        fetchRepaymentSchedule();
        fetchPaymentSummary();
        readwrite = false;
        renderPnlCreateGatePass = true;
        renderNewSrcBtn = true;
        renderPnlManPage = false;
        createOrSearchBundle = "Search";
        headerTitle = "Loan Disbursement and Repayment";
        setIcone("ui-icon-search");
    }

    public void onCellEdit(RowEditEvent event) {
        Object newValue = event.getObject();
        fmsLRpt = (FmsLoanRepayment) newValue;
        fmsLRpt.setMargin(new BigDecimal(fmsLRpt.getDisbursementFk().getInterestRate() + fmsLRpt.getLiborRate()).setScale(2, RoundingMode.HALF_EVEN));
        fmsLRpt.setInterestRepayment((fmsLRpt.getOpeningBalance().multiply(fmsLRpt.getMargin())).multiply(fmsLRpt.getDays().divide(fmsLoan.getInterestRule(), 2, RoundingMode.HALF_EVEN)).setScale(2, RoundingMode.HALF_EVEN));
        fmsLoanRepaymentBeanLocal.edit(fmsLRpt);
        JsfUtil.addSuccessMessage("Libor rate changed successfully.");
    }

    public void onPaySummaryCellEdit(RowEditEvent event) {
        Object newValue = event.getObject();
        fmsLPS = (FmsLoanPaymentSummary) newValue;
        fmsLPS.setDefaultInterestAmount(fmsLPS.getDefaultInterestRate().multiply(fmsLPS.getEstimatedTotalPayment()).setScale(2, RoundingMode.HALF_EVEN));
        fmsLPS.setTotalPayment(fmsLPS.getEstimatedTotalPayment().add(fmsLPS.getDefaultInterestAmount()).add(fmsLPS.getManagementFee()).add(fmsLPS.getOtherPayment()).add(fmsLPS.getCommitmentFee()));
        fmsLPS.setPaymentInBirr(fmsLPS.getTotalPayment().multiply(fmsLPS.getExchangeRate()).setScale(2, RoundingMode.HALF_EVEN));
        fmslpsBeanLocal.edit(fmsLPS);
        JsfUtil.addSuccessMessage("Payment information saved successfully.");
    }

    public void createNewView() {
        disableBtnCreate = false;
        if (createOrSearchBundle.equals("New")) {
            renderPnlCreateGatePass = true;
            renderNewSrcBtn = false;
            renderPnlManPage = false;
            createOrSearchBundle = "Search";
            headerTitle = "Loan Disbursement and Repayment";
            setIcone("ui-icon-search");
        } else if (createOrSearchBundle.equals("Search")) {
            renderPnlCreateGatePass = false;
            renderNewSrcBtn = false;
            renderPnlManPage = true;
            createOrSearchBundle = "New";
            headerTitle = "Loan Disbursement and Repayment";
            setIcone("ui-icon-plus");
        }
        reset();
    }
//</editor-fold>



   
}
