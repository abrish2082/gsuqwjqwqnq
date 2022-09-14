/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.controller.loan;
//<editor-fold defaultstate="collapsed" desc="Inject">
//</editor-fold>
import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.fcms.businessLogic.admin.FmsGeneralLedgerBeanLocal;
import et.gov.eep.fcms.businessLogic.admin.subsidiaryBeanLocal;
import et.gov.eep.fcms.businessLogic.loan.FmsLoanBeanLocal;
import et.gov.eep.fcms.businessLogic.loan.FmsPrincipalPayScheduleBeanLocal;
import et.gov.eep.fcms.entity.admin.FmsGeneralLedger;
import et.gov.eep.fcms.entity.admin.FmsSubsidiaryLedger;
import et.gov.eep.fcms.entity.loan.FmsLoan;
import et.gov.eep.fcms.entity.loan.FmsPrincipalPayAccounts;
import et.gov.eep.fcms.entity.loan.FmsPrincipalPayment;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.ArrayDataModel;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import org.primefaces.event.SelectEvent;
import org.primefaces.convert.NumberConverter;
import securityBean.SessionBean;
import webService.AAA;
import webService.EventEntry;
import webService.IAdministration;

/**
 *
 * @author Binyam
 */
@Named(value = "principalPayScheduleController")
@ViewScoped
public class PrincipalPayScheduleController implements Serializable {
//<editor-fold defaultstate="collapsed" desc="Inject">
    @Inject
    SessionBean SessionBean;

    @Inject
    FmsPrincipalPayment fmsPrincipalPayment;
    @Inject
    FmsPrincipalPayAccounts fmsPrincipalPayAccounts;
    @Inject
    FmsLoan fmsLoan;
    @Inject
    FmsGeneralLedger fmsGeneralLedger;
    @Inject
    FmsSubsidiaryLedger fmsSubsidiaryLedger;
//</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="EJB">
     @EJB
    FmsPrincipalPayScheduleBeanLocal fmsPrincipalPaymentBeanLocal;
    @EJB
    FmsLoanBeanLocal fmsLoanBeanLocal;
    @EJB
    FmsGeneralLedgerBeanLocal fmsGeneralLedgerBeanLocal;
    @EJB
    subsidiaryBeanLocal subLedgerbeanLocal;

//</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="variable declaration">
     FmsPrincipalPayment fmsPrincipalPaymentSelected;
    FmsLoan selectedLoan;
    DataModel<FmsLoan> loanDatamodel;
    DataModel<FmsPrincipalPayment> principalPaymentdataModel;
    List<FmsLoan> loanList;
    DataModel<FmsPrincipalPayAccounts> fmsPrincipalPayAccountsDataModel;

    boolean sl = true;
    int indx = 0;
    boolean paidStat = false;
    private boolean schedGenRender = false;

    private String createOrSearchBundle = "New";
    private String headerTitle = "Principal Payment Schedule";
    private String icone = "ui-icon-plus";
    private boolean disableBtnCreate;
    private boolean renderPnlCreateGatePass = false;
    private boolean renderPnlManPage = true;
    private boolean renderNewSrcBtn = false;
    private NumberConverter numberConverter = new NumberConverter();
//</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="PrincipalPayScheduleController">
     public PrincipalPayScheduleController() {
        this.paidStat = false;
        numberConverter.setPattern("#,##0.00##");
        numberConverter.setMinIntegerDigits(1);
        numberConverter.setMaxIntegerDigits(50);
        numberConverter.setMaxFractionDigits(2);
    }
//</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="getter and setters">
        public FmsPrincipalPayment getFmsPrincipalPaymentSelected() {
        return fmsPrincipalPaymentSelected;
    }

    public void setFmsPrincipalPaymentSelected(FmsPrincipalPayment fmsPrincipalPaymentSelected) {
        this.fmsPrincipalPaymentSelected = fmsPrincipalPaymentSelected;
    }

    public FmsPrincipalPayAccounts getFmsPrincipalPayAccounts() {
        if (fmsPrincipalPayAccounts == null) {
            fmsPrincipalPayAccounts = new FmsPrincipalPayAccounts();
        }
        return fmsPrincipalPayAccounts;
    }

    public void setFmsPrincipalPayAccounts(FmsPrincipalPayAccounts fmsPrincipalPayAccounts) {
        this.fmsPrincipalPayAccounts = fmsPrincipalPayAccounts;
    }

    public boolean isSl() {
        return sl;
    }

    public void setSl(boolean sl) {
        this.sl = sl;
    }

    public DataModel<FmsPrincipalPayAccounts> getFmsPrincipalPayAccountsDataModel() {
        fmsPrincipalPayAccountsDataModel = new ListDataModel(principalPaymentdataModel.getRowData().getFmsPrincipalPayAccountsList());
        return fmsPrincipalPayAccountsDataModel;
    }

    public void setFmsPrincipalPayAccountsDataModel(DataModel<FmsPrincipalPayAccounts> fmsPrincipalPayAccountsDataModel) {
        this.fmsPrincipalPayAccountsDataModel = fmsPrincipalPayAccountsDataModel;
    }

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
    public boolean isSchedGenRender() {
        return schedGenRender;
    }

    /**
     *
     * @param schedGenRender
     */
    public void setSchedGenRender(boolean schedGenRender) {
        this.schedGenRender = schedGenRender;
    }

    /**
     *
     * @return
     */
    public FmsPrincipalPayment getFmsPrincipalPayment() {
        if (fmsPrincipalPayment == null) {
            fmsPrincipalPayment = new FmsPrincipalPayment();
        }
        return fmsPrincipalPayment;
    }

    /**
     *
     * @param fmsPrincipalPayment
     */
    public void setFmsPrincipalPayment(FmsPrincipalPayment fmsPrincipalPayment) {
        this.fmsPrincipalPayment = fmsPrincipalPayment;
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
    public FmsPrincipalPayScheduleBeanLocal getFmsPrincipalPaymentBeanLocal() {
        return fmsPrincipalPaymentBeanLocal;
    }

    /**
     *
     * @param fmsPrincipalPaymentBeanLocal
     */
    public void setFmsPrincipalPaymentBeanLocal(FmsPrincipalPayScheduleBeanLocal fmsPrincipalPaymentBeanLocal) {
        this.fmsPrincipalPaymentBeanLocal = fmsPrincipalPaymentBeanLocal;
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

    public ArrayList<FmsLoan> SearchLoan(String loanNo) {
        ArrayList<FmsLoan> loans = null;
        fmsLoan.setLoanNo(loanNo);
        loans = fmsLoanBeanLocal.searchFmsLoanByLoanNo(fmsLoan);
        return loans;
    }

    public DataModel<FmsPrincipalPayment> getPrincipalPaymentdataModel() {
        if (principalPaymentdataModel == null) {
            principalPaymentdataModel = new ArrayDataModel<>();
        }
        return principalPaymentdataModel;
    }

    /**
     *
     * @return
     */
    public void setPrincipalPaymentdataModel(DataModel<FmsPrincipalPayment> principalPaymentdataModel) {
        this.principalPaymentdataModel = principalPaymentdataModel;
    }

    List<FmsPrincipalPayment> schedules;

    /**
     *
     * @return
     */
    public List<FmsPrincipalPayment> getSchedules() {
        if (schedules == null) {
            schedules = new ArrayList<>();
        }
        return schedules;
    }

    /**
     *
     * @param schedules
     */
    public void setSchedules(List<FmsPrincipalPayment> schedules) {
        this.schedules = schedules;
    }
//</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="other methods">
    public void liquidationRValidator() {
        if (fmsPrincipalPayment.getLiquidationRate() != null) {
            if (fmsPrincipalPayment.getLiquidationRate() > 1) {
                fmsPrincipalPayment.setLiquidationRate(null);
                JsfUtil.addFatalMessage("The value of liquidation rate is greater than one.");
            } else {
            }
        }
    }

    public void followUpEditor() {
        fmsPrincipalPayment = getPrincipalPaymentdataModel().getRowData();
    }

    private long dateDiff;

    /**
     *
     */
    public void addPayment() {
        if (fmsPrincipalPayment.getPaymentDate().getTime() <= fmsPrincipalPayment.getScheduleDate().getTime()) {
            JsfUtil.addFatalMessage("Date is prior to scheduled date.");
            fmsPrincipalPayment.setPaymentDate(null);
            fmsPrincipalPayment.setLiquidationRate(null);
        } else if (!(fmsPrincipalPayment.getStatus().equalsIgnoreCase("Unpaid"))) {
            JsfUtil.addFatalMessage("Payment has already been made.");
            fmsPrincipalPayment.setPaymentDate(null);
            fmsPrincipalPayment.setLiquidationRate(null);            
        } else {
            fmsPrincipalPayment.setPaymentDate(fmsPrincipalPayment.getPaymentDate());
            dateDiff = fmsPrincipalPayment.getPaymentDate().getTime() - fmsPrincipalPayment.getScheduleDate().getTime();
            float daysDiff = dateDiff / (24 * 60 * 60 * 1000);
            BigDecimal liqDate = new BigDecimal(daysDiff / 360);
            fmsPrincipalPayment.setLiquidatedDamage((liqDate.multiply(new BigDecimal((fmsPrincipalPayment.getLiquidationRate().doubleValue() * 100) / 100).multiply(new BigDecimal(fmsPrincipalPayment.getInstallment().doubleValue())))).setScale(2, RoundingMode.HALF_EVEN));
            fmsPrincipalPayment.setStatus("Paid");
            getSchedules().add(fmsPrincipalPayment);
        }
    }

    /**
     *
     * @param event
     */
    public void getByLoanNO(SelectEvent event) {
        fmsLoan.setLoanNo(event.getObject().toString());
        fmsLoan = fmsLoanBeanLocal.getFmsLoanInfo(fmsLoan);
        fmsPrincipalPayment.setLoanId(fmsLoan);
        scheduler(fmsPrincipalPayment);
    }

    /**
     *
     * @param fmsPrincipalPayment
     */
    public void scheduler(FmsPrincipalPayment fmsPrincipalPayment) {
        schedules = fmsPrincipalPaymentBeanLocal.fetchschedule(fmsPrincipalPayment);
        if (!schedules.isEmpty()) {
            principalPaymentdataModel = new ListDataModel(new ArrayList(schedules));
            schedGenRender = false;
        } else {
            schedGenRender = true;
            principalPaymentdataModel = null;
            generateSchedule();
        }
    }

    /**
     *
     */
    public void paymentEditor() {
        schedules.stream().forEach((schedule) -> {
            fmsPrincipalPaymentBeanLocal.edit(schedule);
        });
        JsfUtil.addSuccessMessage("Payment is saved");
    }

    /**
     *
     */
    public void reset() {
        fmsLoan = null;
        fmsPrincipalPayment = null;
        fmsPrincipalPayAccounts = null;
        schedules = null;
        principalPaymentdataModel = null;
        schedGenRender = false;
        loanDatamodel = null;
        selectedLoan = null;
        loanList = null;
        abortlist = null;
    }

    List<FmsPrincipalPayment> abortlist = new ArrayList<>();

    public List<FmsPrincipalPayment> getAbortlist() {
        return abortlist;
    }

    public void setAbortlist(List<FmsPrincipalPayment> abortlist) {
        this.abortlist = abortlist;
    }

    BigDecimal abortedRemaining = new BigDecimal(BigInteger.ZERO);
    BigDecimal abortedRemainingfinal = new BigDecimal(BigInteger.ZERO);

    public BigDecimal getAbortedRemaining() {
        return abortedRemaining;
    }

    public void setAbortedRemaining(BigDecimal abortedRemaining) {
        this.abortedRemaining = abortedRemaining;
    }

    public BigDecimal getAbortedRemainingfinal() {
        return abortedRemainingfinal;
    }

    public void setAbortedRemainingfinal(BigDecimal abortedRemainingfinal) {
        this.abortedRemainingfinal = abortedRemainingfinal;
    }

    public long getDateDiff() {
        return dateDiff;
    }

    public void setDateDiff(long dateDiff) {
        this.dateDiff = dateDiff;
    }

    public int getIndx() {
        return indx;
    }

    public void setIndx(int indx) {
        this.indx = indx;
    }

    public boolean isPaidStat() {
        return paidStat;
    }

    public void setPaidStat(boolean paidStat) {
        this.paidStat = paidStat;
    }

    public void abortTest() {

        for (FmsPrincipalPayment schedule : schedules) {
            if (schedule.getStatus().matches("Paid")) {
                paidStat = true;
            } else {
            }
        }
        abort();
    }

    public void abort() {

        if (paidStat == true) {
            for (int i = 0; i < schedules.size(); i++) {
                if (schedules.get(i).getStatus().matches("Paid")) {
                    abortedRemaining = schedules.get(i).getRemainingBalance();
                    indx = i;
                }
            }
//************** DELETING PRINCIPAL PAYMENT BEGIN ********************//
            for (FmsPrincipalPayment schedule : schedules) {
                if (schedule.getStatus().matches("Unpaid")) {
                    fmsPrincipalPaymentBeanLocal.delete(schedule);
                }
            }
//************** DELETING PRINCIPAL PAYMENT END ********************//

//************** SAVING AS A NEW LOAN BEGIN ********************//
//            if ((schedules.get(indx).getLoanId().getCommitmentRate() * 100) > 100 || (schedules.get(indx).getLoanId().getLiquidationRate() * 100) > 100) {
//                if ((schedules.get(indx).getLoanId().getCommitmentRate() * 100) > 100 && (schedules.get(indx).getLoanId().getLiquidationRate() * 100) > 100) {
//                    JsfUtil.addFatalMessage("Commitment Rate and Liquidation Rate does not have correct value.");
//                } else if ((schedules.get(indx).getLoanId().getCommitmentRate() * 100) > 100) {
//                    JsfUtil.addFatalMessage("Commitment Rate does not have correct value.");
//                } else {
//                    JsfUtil.addFatalMessage("Liquidation Rate does not have correct value.");
//                }
//            }
//            else {
//                getFmsLoan();
//                fmsLoan.setLoanId(null);
//                fmsLoan.setLoanAmount(new BigDecimal(getAbortedRemaining().intValue()));
//                fmsLoan.setLoanNo(schedules.get(indx).getLoanId().getLoanNo() + "-TR");
//                fmsLoanBeanLocal.create(fmsLoan);
//                JsfUtil.addSuccessMessage("Loan Agreement Created Succesfully");
//            }
//************** SAVING AS A NEW LOAN END ********************//
            scheduler(fmsPrincipalPayment);
        }
    }

    public void detailPopulate(SelectEvent event) {
        fmsPrincipalPayment = (FmsPrincipalPayment) event.getObject();
    }

    public SelectItem[] getGLList() {
        return JsfUtil.getSelectItems(fmsGeneralLedgerBeanLocal.findAll(), true);
    }

    public void getGeneralLedgerChange(ValueChangeEvent event) {
        if (!event.getNewValue().toString().isEmpty()) {
            fmsGeneralLedger.setGeneralLedgerCode(event.getNewValue().toString());
            fmsGeneralLedger = fmsGeneralLedgerBeanLocal.getGLDetail(fmsGeneralLedger);
            fmsSubsidiaryLedger.setGeneralLedgerId(fmsGeneralLedger);
            fmsPrincipalPayAccounts.setGeneralLedgerIdFk(fmsGeneralLedger);
        }
    }

    public SelectItem[] findListSubL() {
        ArrayList<FmsSubsidiaryLedger> subsidaryList = new ArrayList<>();
        if (fmsGeneralLedger != null) {
            SelectItem[] listSl = null;
            subsidaryList = subLedgerbeanLocal.findSubLedger(fmsGeneralLedger);
            if (subsidaryList.size() > 0) {
                sl = false;
                listSl = new SelectItem[subsidaryList.size() + 1];
                listSl[0] = new SelectItem(null, "---Select One---");
                for (int i = 0; i < subsidaryList.size(); i++) {
                    listSl[i + 1] = new SelectItem(subsidaryList.get(i).getSubsidiaryId(), subsidaryList.get(i).getSubsidiaryCode());
                }
            } else {
                sl = true;
            }
            return listSl;
        } else {
            SelectItem[] items = new SelectItem[1];
            items[0] = new SelectItem("", "--Select CC & GL--");
            return items;
        }
    }

    public void getSubsidiaryLChange(ValueChangeEvent event) {
        try {
            if (!event.getNewValue().toString().isEmpty()) {
                fmsSubsidiaryLedger.setSubsidiaryId(Integer.parseInt(event.getNewValue().toString()));
                fmsSubsidiaryLedger = subLedgerbeanLocal.getSubsidiaryInfo(fmsSubsidiaryLedger);
                fmsPrincipalPayAccounts.setSubsidiaryLedgerIdFk(fmsSubsidiaryLedger);
            }
        } catch (NullPointerException ex) {
            throw ex;
        }
    }

    public void addPrincipalAccounts() {
        try {
            fmsPrincipalPayment.addPrincipalPayAccountsList(fmsPrincipalPayAccounts);
            fmsPrincipalPayAccounts = new FmsPrincipalPayAccounts();
            fmsPrincipalPayAccountsDataModel = null;
            fmsPrincipalPayAccountsDataModel = new ListDataModel(new ArrayList(fmsPrincipalPayment.getFmsPrincipalPayAccountsList()));
        } catch (NullPointerException ex) {
            throw ex;
        }
    }

    public void removePPAccountst() {
        fmsPrincipalPayAccounts = new FmsPrincipalPayAccounts();
        fmsPrincipalPayAccounts = getFmsPrincipalPayAccountsDataModel().getRowData();
        fmsPrincipalPayment.getFmsPrincipalPayAccountsList().remove(fmsPrincipalPayAccounts);
        reset();
    }

    public void resetOBDetail() {
        fmsPrincipalPayAccountsDataModel = null;
        fmsPrincipalPayAccountsDataModel = new ListDataModel(new ArrayList(fmsPrincipalPayment.getFmsPrincipalPayAccountsList()));
    }

    public void generateSchedule() {
        try {
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(SessionBean.getUserName(), "generateSchedule", dataset)) {

                Calendar firstduedate = Calendar.getInstance();
                firstduedate.setTime(fmsLoan.getPrincipalRepaymentDate());
                fmsPrincipalPayment.setRemainingBalance(fmsLoan.getLoanAmount());
                if (null != fmsLoan.getFrequency()) {
                    switch (fmsLoan.getFrequency()) {
                        case "Semi Annual":
                            for (int i = 1; i <= fmsLoan.getNoInstallment(); i++) {

                                FmsPrincipalPayment fmsPrincipalPaymentIn = new FmsPrincipalPayment();
                                fmsPrincipalPaymentIn.setScheduleDate(firstduedate.getTime());
                                fmsPrincipalPaymentIn.setInstallment(fmsLoan.getLoanAmount().divide((new BigDecimal(fmsLoan.getNoInstallment())), 2, RoundingMode.HALF_EVEN));

                                if (fmsPrincipalPayment.getRemainingBalance().compareTo(fmsPrincipalPaymentIn.getInstallment()) == 1) {

                                    fmsPrincipalPaymentIn.setRemainingBalance(fmsPrincipalPayment.getRemainingBalance().subtract(fmsPrincipalPaymentIn.getInstallment()).setScale(2, RoundingMode.HALF_EVEN));
                                    fmsPrincipalPayment.setRemainingBalance(fmsPrincipalPayment.getRemainingBalance().subtract(fmsPrincipalPaymentIn.getInstallment()).setScale(2, RoundingMode.HALF_EVEN));
                                    fmsPrincipalPaymentIn.setLoanId(fmsLoan);
                                    fmsPrincipalPaymentIn.setStatus("Unpaid");

                                    fmsPrincipalPaymentBeanLocal.create(fmsPrincipalPaymentIn);

                                } else {
                                    fmsPrincipalPaymentIn.setInstallment(fmsPrincipalPayment.getRemainingBalance().setScale(2, RoundingMode.HALF_EVEN));
                                    fmsPrincipalPaymentIn.setRemainingBalance(fmsPrincipalPayment.getRemainingBalance().subtract(fmsPrincipalPaymentIn.getInstallment()).setScale(2, RoundingMode.HALF_EVEN));
                                    fmsPrincipalPayment.setRemainingBalance(fmsPrincipalPayment.getRemainingBalance().subtract(fmsPrincipalPaymentIn.getInstallment()).setScale(2, RoundingMode.HALF_EVEN));
                                    fmsPrincipalPaymentIn.setLoanId(fmsLoan);
                                    fmsPrincipalPaymentIn.setStatus("Unpaid");

                                    fmsPrincipalPaymentBeanLocal.create(fmsPrincipalPaymentIn);

                                }
                                firstduedate.add(Calendar.MONTH, 6);
                            }
                            scheduler(fmsPrincipalPayment);
                            schedGenRender = false;
                            break;
                        case "Annual":
                            for (int i = 1; i <= fmsLoan.getNoInstallment(); i++) {
                                FmsPrincipalPayment fmsPrincipalPaymentIn = new FmsPrincipalPayment();
                                fmsPrincipalPaymentIn.setScheduleDate(firstduedate.getTime());
                                fmsPrincipalPaymentIn.setInstallment(fmsLoan.getLoanAmount().divide(new BigDecimal(fmsLoan.getNoInstallment()), 2, RoundingMode.HALF_EVEN));

                                if (fmsPrincipalPayment.getRemainingBalance().compareTo(fmsPrincipalPaymentIn.getInstallment()) == 1) {

                                    fmsPrincipalPaymentIn.setRemainingBalance(fmsPrincipalPayment.getRemainingBalance().subtract(fmsPrincipalPaymentIn.getInstallment()));
                                    fmsPrincipalPayment.setRemainingBalance(fmsPrincipalPayment.getRemainingBalance().subtract(fmsPrincipalPaymentIn.getInstallment()));
                                    fmsPrincipalPaymentIn.setLoanId(fmsLoan);
                                    fmsPrincipalPaymentIn.setStatus("Unpaid");
                                    fmsPrincipalPaymentBeanLocal.create(fmsPrincipalPaymentIn);

                                } else {

                                    fmsPrincipalPaymentIn.setRemainingBalance(fmsPrincipalPayment.getRemainingBalance().subtract(fmsPrincipalPaymentIn.getInstallment()));
                                    fmsPrincipalPayment.setRemainingBalance(fmsPrincipalPayment.getRemainingBalance().subtract(fmsPrincipalPaymentIn.getInstallment()));
                                    fmsPrincipalPaymentIn.setLoanId(fmsLoan);
                                    fmsPrincipalPaymentIn.setStatus("Unpaid");
                                    fmsPrincipalPaymentBeanLocal.create(fmsPrincipalPaymentIn);
                                }
                                firstduedate.add(Calendar.YEAR, 1);
                            }
                            scheduler(fmsPrincipalPayment);
                            schedGenRender = false;
                            break;
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
//..... add more information by calling fields defined in the object 
                security.addEventLog(eventEntry, dataset);

            }
        } catch (Exception ex) {
        }
    }

    public void loanSearch() {
        loanList = fmsLoanBeanLocal.searchFmsLoanByLoanNo(fmsLoan);
        loanDatamodel = new ListDataModel<>(loanList);

    }

    public void getBLoanNO(SelectEvent event) {
        fmsLoan = (FmsLoan) event.getObject();
        fmsLoan = fmsLoanBeanLocal.getFmsLoanInfo(fmsLoan);
        fmsPrincipalPayment.setLoanId(fmsLoan);
        scheduler(fmsPrincipalPayment);

        renderPnlCreateGatePass = true;
        renderNewSrcBtn = true;
        renderPnlManPage = false;
        createOrSearchBundle = "Search";
        headerTitle = "Principal Payment Schedule  ";
        setIcone("ui-icon-search");
    }

    public void createNewView() {
        disableBtnCreate = false;
        if (createOrSearchBundle.equals("New")) {
            renderPnlCreateGatePass = true;
            renderPnlManPage = false;
            createOrSearchBundle = "Search";
            renderNewSrcBtn = false;
            headerTitle = "Principal Payment Schedule";
            setIcone("ui-icon-search");
        } else if (createOrSearchBundle.equals("Search")) {
            renderPnlCreateGatePass = false;
            renderPnlManPage = true;
            renderNewSrcBtn = false;
            createOrSearchBundle = "New";
            headerTitle = "Principal Payment Schedule";
            setIcone("ui-icon-plus");
        }
        reset();
    }
    
//////////    public String navigateToLoanPage() {
//////////
//////////        FacesContext.getCurrentInstance().getExternalContext().getFlash().put("loanInfo", fmsLoan);
//////////
//////////        navController = navController.getNavigationController();
//////////        navController.setObj(fmsLoan);
//////////        return "loanagreementreg";
//////////    }

//</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Inject">
//</editor-fold>

}
