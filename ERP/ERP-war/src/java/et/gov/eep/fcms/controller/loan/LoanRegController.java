/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.controller.loan;
//<editor-fold defaultstate="collapsed" desc="Inject">
//</editor-fold>
import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.fcms.businessLogic.LuCurrencyBeanLocal;
import et.gov.eep.fcms.businessLogic.loan.FmsLoanBeanLocal;
import et.gov.eep.fcms.businessLogic.loan.FmsLoanDetailBeanLocal;
import et.gov.eep.fcms.businessLogic.loan.FmsPrincipalPayScheduleBeanLocal;
import et.gov.eep.fcms.entity.FmsLuCurrency;
import et.gov.eep.fcms.entity.loan.FmsLoan;
import et.gov.eep.fcms.entity.loan.FmsLoanDetail;
import et.gov.eep.fcms.entity.loan.FmsPrincipalPayment;
import et.gov.eep.pms.businessLogic.PmsCreateProjectBeanLocal;
import et.gov.eep.pms.entity.PmsCreateProjects;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.event.AjaxBehaviorEvent;
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
import org.primefaces.convert.NumberConverter;
import org.primefaces.event.SelectEvent;
import securityBean.SessionBean;
import webService.AAA;
import webService.EventEntry;
import webService.IAdministration;

/**
 *
 * @author Binyam
 */
@Named(value = "loanRegController")
@ViewScoped
public class LoanRegController implements Serializable {
//<editor-fold defaultstate="collapsed" desc="Inject">
    @Inject
    SessionBean SessionBean;

    @Inject
    FmsLoan fmsLoan;
    @Inject
    FmsPrincipalPayment fmsPrincipalPayment;
    @Inject
    FmsLuCurrency fmsLuCurrency;
    @Inject
    FmsLoanDetail fmsLoanDetail;
    @Inject
    PmsCreateProjects pmsCreateProjects;
//</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="EJB">
     @EJB
    PmsCreateProjectBeanLocal pmsCreateProjectBeanLocal;
    @EJB
    FmsLoanDetailBeanLocal fmsLoanDetailBeanLocal;
    @EJB
    LuCurrencyBeanLocal luCurrencyBeanLocal;
    @EJB
    FmsPrincipalPayScheduleBeanLocal fmsPrincipalPaymentBeanLocal;
    @EJB
    FmsLoanBeanLocal fmsLoanBeanLocal;
//</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="variable declaration">
    List<FmsLoan> loanList;
    List<FmsPrincipalPayment> pricipalpayList;
    DataModel<FmsLoan> loanDatamodel;
    FmsLoan selectedLoan;
    DataModel<FmsLoanDetail> fmsLoanDetailsDataModel;
    FmsLoanDetail selectedfmsLoanDetail;

    int update = 0;
    int duplicated = 0;
    int detailPopulate = 0;

    private String buttval = "Save";
    private String createOrSearchBundle = "New";
    private String headerTitle = "Loan Agreement";
    private String smallheaderTitle = "Loan Agreement";
    private String icone = "ui-icon-plus";
    private boolean disableBtnCreate;
    private boolean renderPnlCreateGatePass = false;
    private boolean renderPnlManPage = true;
    private NumberConverter numberConverter = new NumberConverter();
//</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="LoanRegController">
     public LoanRegController() {
        numberConverter.setPattern("#,##0.00##");
        numberConverter.setMinIntegerDigits(1);
        numberConverter.setMaxIntegerDigits(50);
        numberConverter.setMaxFractionDigits(2);
    }
//</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="getter and setters">
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

    public int getDuplicated() {
        return duplicated;
    }

    public void setDuplicated(int duplicated) {
        this.duplicated = duplicated;
    }

    public int getDetailPopulate() {
        return detailPopulate;
    }

    public void setDetailPopulate(int detailPopulate) {
        this.detailPopulate = detailPopulate;
    }

    public PmsCreateProjects getPmsCreateProjects() {
        if (pmsCreateProjects == null) {
            pmsCreateProjects = new PmsCreateProjects();
        }
        return pmsCreateProjects;
    }

    public void setPmsCreateProjects(PmsCreateProjects pmsCreateProjects) {
        this.pmsCreateProjects = pmsCreateProjects;
    }

    public FmsLoanDetail getSelectedfmsLoanDetail() {
        return selectedfmsLoanDetail;
    }

    public void setSelectedfmsLoanDetail(FmsLoanDetail selectedfmsLoanDetail) {
        this.selectedfmsLoanDetail = selectedfmsLoanDetail;
    }

    public FmsLoanDetail getFmsLoanDetail() {
        if (fmsLoanDetail == null) {
            fmsLoanDetail = new FmsLoanDetail();
        }
        return fmsLoanDetail;
    }

    public void setFmsLoanDetail(FmsLoanDetail fmsLoanDetail) {
        this.fmsLoanDetail = fmsLoanDetail;
    }

    public DataModel<FmsLoanDetail> getFmsLoanDetailsDataModel() {
        return fmsLoanDetailsDataModel;
    }

    public void setFmsLoanDetailsDataModel(DataModel<FmsLoanDetail> fmsLoanDetailsDataModel) {
        this.fmsLoanDetailsDataModel = fmsLoanDetailsDataModel;
    }

    public FmsLuCurrency getFmsLuCurrency() {
        return fmsLuCurrency;
    }

    public void setFmsLuCurrency(FmsLuCurrency fmsLuCurrency) {
        this.fmsLuCurrency = fmsLuCurrency;
    }

    public List<FmsLoan> getLoanList() {
        if (loanList == null) {
            loanList = new ArrayList<>();
        }
        return loanList;
    }

    public void setLoanList(List<FmsLoan> loanList) {
        this.loanList = loanList;
    }

    public DataModel<FmsLoan> getLoanDatamodel() {
        if (loanDatamodel == null) {
            loanDatamodel = new ListDataModel<>();
        }
        return loanDatamodel;
    }

    public void setLoanDatamodel(DataModel<FmsLoan> loanDatamodel) {
        this.loanDatamodel = loanDatamodel;
    }

    public FmsLoan getSelectedLoan() {
        if (selectedLoan == null) {
            selectedLoan = new FmsLoan();
        }
        return selectedLoan;
    }

    public void setSelectedLoan(FmsLoan selectedLoan) {
        this.selectedLoan = selectedLoan;
    }

    public List<FmsPrincipalPayment> getPricipalpayList() {
        return pricipalpayList;
    }

    public void setPricipalpayList(List<FmsPrincipalPayment> pricipalpayList) {
        this.pricipalpayList = pricipalpayList;
    }

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

    public int getUpdate() {
        return update;
    }

    public void setUpdate(int update) {
        this.update = update;
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

    public String getSmallheaderTitle() {
        return smallheaderTitle;
    }

    public void setSmallheaderTitle(String smallheaderTitle) {
        this.smallheaderTitle = smallheaderTitle;
    }

    public String getIcone() {
        return icone;
    }

    public void setIcone(String icone) {
        this.icone = icone;
    }

    public String getButtval() {
        return buttval;
    }

    public void setButtval(String buttval) {
        this.buttval = buttval;
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

    public FmsPrincipalPayment getFmsPrincipalPayment() {
        return fmsPrincipalPayment;
    }

    public void setFmsPrincipalPayment(FmsPrincipalPayment fmsPrincipalPayment) {
        this.fmsPrincipalPayment = fmsPrincipalPayment;
    }
//</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="other methods">
    
    public void createNewLoanView() {
        disableBtnCreate = false;
        if (createOrSearchBundle.equals("New")) {
            renderPnlCreateGatePass = true;
            renderPnlManPage = false;
            createOrSearchBundle = "Search";
            headerTitle = "Loan Agreement";
            smallheaderTitle = "Entry form";
            setIcone("ui-icon-search");
        } else if (createOrSearchBundle.equals("Search")) {
            renderPnlCreateGatePass = false;
            renderPnlManPage = true;
            createOrSearchBundle = "New";
            headerTitle = "Loan Agreement";
            smallheaderTitle = "Loan Agreement";
            setIcone("ui-icon-plus");
        }
        reset();
    }

    public ArrayList<FmsLoan> SearchLoan(String loanNo) {
        ArrayList<FmsLoan> loans = null;
        loans = new ArrayList<>();
        fmsLoan.setLoanNo(loanNo);
        loans = fmsLoanBeanLocal.searchFmsLoanByLoanNo(fmsLoan);
        return loans;
    }

    public void loanSearch() {
        loanList = fmsLoanBeanLocal.searchFmsLoanByLoanNo(fmsLoan);
        loanDatamodel = new ListDataModel<>(loanList);

    }

    public void populate(SelectEvent event) {
        fmsLoan = (FmsLoan) event.getObject();
        fmsLuCurrency = fmsLoan.getCurrencyId();
        fmsLuCurrency = luCurrencyBeanLocal.getCurrencyinfo(fmsLuCurrency);
        recreateDataModel();

        renderPnlCreateGatePass = true;
        renderPnlManPage = false;
        createOrSearchBundle = "Search";
        headerTitle = "Loan Agreement ";
        smallheaderTitle = "Entry form";
        setIcone("ui-icon-search");
        buttval = "Update";
        update = 1;
    }

    public void getByLoanNO(SelectEvent event) {
        fmsLoan.setLoanNo(event.getObject().toString());
        fmsLoan = fmsLoanBeanLocal.getFmsLoanInfo(fmsLoan);
        pricipalpayList = fmsPrincipalPaymentBeanLocal.fetchPricipalPayment(fmsLoan);
        update = 1;
    }

    public SelectItem[] getLoanGroupList() {
        return JsfUtil.getSelectItems(groupList(), true);
    }

    /**
     *
     * @return
     */
    public ArrayList<String> groupList() {
        ArrayList<String> freq = new ArrayList<>();
        freq.add("Local");
        freq.add("Foriegn");
        return freq;
    }

    public SelectItem[] getFrequency() {
        return JsfUtil.getSelectItems(frequency(), true);
    }

    /**
     *
     * @return
     */
    public ArrayList<String> frequency() {
        ArrayList<String> freq = new ArrayList<>();
        freq.add("Annual");
        freq.add("Semi Annual");
        return freq;
    }

    /**
     *
     * @return
     */
    public SelectItem[] getInterestRule() {
        return JsfUtil.getSelectItems(interestRule(), true);
    }

    /**
     *
     * @return
     */
    public ArrayList<BigDecimal> interestRule() {
        ArrayList<BigDecimal> intRule = new ArrayList<>();
        intRule.add(BigDecimal.valueOf(360));
        intRule.add(BigDecimal.valueOf(365));
        return intRule;
    }

    /**
     *
     * @return
     */
    public SelectItem[] getPaymentMethod() {
        return JsfUtil.getSelectItems(PaymentMethod(), true);
    }

    /**
     *
     * @return
     */
    public ArrayList<String> PaymentMethod() {

        ArrayList<String> method = new ArrayList<>();
        method.add("Bank Transfer");
        method.add("Cheque");
        return method;
    }

    /**
     *
     * @return
     */
    public SelectItem[] getCurrency() {
        return JsfUtil.getSelectItems(luCurrencyBeanLocal.findAll(), true);
    }

    public void exchangeRateCalc(AjaxBehaviorEvent event) {
        fmsLoan.setLoanAmountInBirr(fmsLoan.getLoanAmount().multiply(fmsLoan.getExchangeRate()));
    }

    public void currencyCalc(ValueChangeEvent event) {
        try {
            fmsLuCurrency.setCurrency(event.getNewValue().toString());
            fmsLuCurrency = luCurrencyBeanLocal.getCurrencyinfo(fmsLuCurrency);
            fmsLoan.setCurrencyId(fmsLuCurrency);
        } catch (NullPointerException nlex) {
            throw nlex;
        }

    }

    /**
     *
     * @return
     */
    public ArrayList<String> Currency() {

        ArrayList<String> currency = new ArrayList<>();
        currency.add("ETB");
        currency.add("USD");
        currency.add("GBP");
        currency.add("Euro");
        currency.add("Yuan");
        return currency;
    }

    /**
     *
     * @return
     */
    public SelectItem[] getPayStatus() {
        return JsfUtil.getSelectItems(PayStatus(), true);
    }

    /**
     *
     * @return
     */
    public ArrayList<String> PayStatus() {

        ArrayList<String> paystat = new ArrayList<>();
        paystat.add("Unpaid");
        paystat.add("On Payment");
        paystat.add("Paid");
        return paystat;
    }

    /**
     *
     * @param event
     */
    public void dateSpliter(SelectEvent event) {
        Date loandate = (Date) event.getObject();
        String date = loandate.toString();

        String[] ldate = date.split("/");
        int year = Integer.parseInt(ldate[2]);

    }
    int frequency;
    int dueyear;
    int comyear;

    /**
     *
     * @param event
     */
    public void commencementlistener(SelectEvent event) {
        Calendar commenceDate = Calendar.getInstance();
        commenceDate.setTime(fmsLoan.getPrincipalRepaymentDate());
        commenceDate.add(Calendar.DATE, -1);
        comyear = commenceDate.get(Calendar.YEAR);
    }

    /**
     *
     * @param event
     */
    public void duedatelistener(SelectEvent event) {
        Calendar dueDate = Calendar.getInstance();
        dueDate.setTime(fmsLoan.getPrincipalDueDate());
        dueDate.add(Calendar.DATE, -1);
        dueyear = dueDate.get(Calendar.YEAR);
    }

    /**
     *
     * @param event
     */
    public void installmetcal(AjaxBehaviorEvent event) {
        if ((dueyear > 2000) && (comyear > 2000)) {
            if ("Annual".equals(fmsLoan.getFrequency())) {
                frequency = 1;
            } else if ("Semi Annual".equals(fmsLoan.getFrequency())) {
                frequency = 2;
            }
            fmsLoan.setNoInstallment((dueyear - comyear + 1) * frequency);
        }
    }

    /**
     *
     */
    boolean paymentStarted = false;

    public SelectItem[] getProjectList() {
        return JsfUtil.getSelectItems(pmsCreateProjectBeanLocal.findAll(), true);
    }

    public void ProjectListener(ValueChangeEvent event) {
        if (!event.getNewValue().toString().isEmpty()) {
            pmsCreateProjects.setProjectName(event.getNewValue().toString());
            pmsCreateProjects = pmsCreateProjectBeanLocal.findPojectID(pmsCreateProjects);
            fmsLoanDetail.setProjectIdFk(pmsCreateProjects);
        }
    }

    public void addProjectsOnLoan() {
        if (fmsLoanDetail.getProjectIdFk() != null) {
            for (int i = 0; i < fmsLoan.getFmsLoanDetailList().size(); i++) {
                if (fmsLoan.getFmsLoanDetailList().get(i).getProjectIdFk().equals(fmsLoanDetail.getProjectIdFk())) {
                    duplicated = 1;
                }
            }
            if (duplicated == 0 || detailPopulate == 1) {
                fmsLoan.addProjectList(fmsLoanDetail);
                recreateDataModel();
                detailPopulate = 0;
            } else {
                duplicateMsg();
                duplicated = 0;
            }
        } else {
            JsfUtil.addFatalMessage("You must add a project first.");
        }
    }

    public void duplicateMsg() {
        JsfUtil.addFatalMessage("Can not duplicate a project on your request.");
    }

    public void recreateDataModel() {
        fmsLoanDetailsDataModel = new ArrayDataModel<>();
        fmsLoanDetailsDataModel = new ListDataModel(new ArrayList(fmsLoan.getFmsLoanDetailList()));
        fmsLoanDetail = new FmsLoanDetail();
        pmsCreateProjects = new PmsCreateProjects();
    }

    public void detailTablePopulate(SelectEvent event) {
        fmsLoanDetail = (FmsLoanDetail) event.getObject();
        detailPopulate = 1;
    }

    public void CreateLoan() {

        try {
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(SessionBean.getUserName(), "CreateLoan", dataset)) {
                if (fmsLoanDetailsDataModel.getRowCount() <= 0) {
                    JsfUtil.addFatalMessage("No record added to the Table!");
                } else {
                    if (update == 0) {
                        fmsLoanBeanLocal.create(fmsLoan);
                        JsfUtil.addSuccessMessage("Loan Agreement Created Succesfully");
                    } else {
                        System.out.println("fmsLoan  " + fmsLoan.getDescription());
                        fmsLoanBeanLocal.edit(fmsLoan);
                        if (!(pricipalpayList == null)) {

                            for (int p = 0; p < pricipalpayList.size(); p++) {
                                if (pricipalpayList.get(p).getStatus().matches("Paid")) {
                                    paymentStarted = true;
                                    if (paymentStarted == true) {
                                        break;
                                    }
                                }
                            }
                            if (paymentStarted == false) {
                                for (int p = 0; p < pricipalpayList.size(); p++) {
                                    fmsPrincipalPaymentBeanLocal.delete(pricipalpayList.get(p));
                                }
                            }
                        }
                        JsfUtil.addSuccessMessage("Loan Agreement Updated Succesfully");
                    }
                    reset();
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

        } catch (Exception ex) {
        }
    }

    /**
     *
     */
    public void reset() {
        fmsLoan = new FmsLoan();
        loanDatamodel = new ArrayDataModel<>();
        fmsLoanDetailsDataModel = new ArrayDataModel<>();
        fmsLoanDetail = new FmsLoanDetail();
        pmsCreateProjects = new PmsCreateProjects();
    }

//    @PostConstruct
//    public void renderWithPreselectedValue() {
//        
//        fmsLoan = (FmsLoan) getFlash().get("loanInfo");
//        
//        if (fmsLoan == null || fmsLoan.getLoanId() == null) {
//            System.out.println("inside if");
//            createOrSearchBundle = "New";
//            renderPnlCreateGatePass = true;
//            renderPnlManPage = false;
//            createOrSearchBundle = "Search";
//            headerTitle = "Loan Agreement";
//            smallheaderTitle = "Entry form";
//            setIcone("ui-icon-search");
//        } else {
//            System.out.println("inside else");
//            renderPnlCreateGatePass = true;
//            renderPnlManPage = false;
//            createOrSearchBundle = "Search";
//            headerTitle = "Loan  ";
//            smallheaderTitle = "Entry form";
//            setIcone("ui-icon-search");
//            buttval = "Update";
//            update = 1;
//        }
//    }
//</editor-fold>
    
   
}
