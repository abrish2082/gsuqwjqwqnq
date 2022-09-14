/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.controller.pettycash;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.PostConstruct;
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
import securityBean.Constants;
import securityBean.SessionBean;
import webService.AAA;
import webService.EventEntry;
import webService.IAdministration;
import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.fcms.businessLogic.pettyCash.FmsCasherAccountBeanLocal;
import et.gov.eep.fcms.businessLogic.pettyCash.FmsDailyCashRegisterBeanLocal;
import et.gov.eep.fcms.businessLogic.admin.FmsLuSystemBeanLocal;
import et.gov.eep.fcms.businessLogic.Voucher.CashPaymentOrderBeanLocal;
import et.gov.eep.fcms.businessLogic.Voucher.FmsVoucherBeanLocal;
import et.gov.eep.fcms.businessLogic.pettyCash.FmsPettyCashReplenishmentBeanLocal;
import et.gov.eep.fcms.entity.Vocher.FmsCashPaymentOrder;
import et.gov.eep.fcms.entity.pettyCash.FmsCasherAccount;
import et.gov.eep.fcms.entity.Vocher.FmsChequePaymentVoucher;
import et.gov.eep.fcms.entity.pettyCash.FmsDailyCashRegister;
import et.gov.eep.fcms.entity.Vocher.FmsVoucher;
import et.gov.eep.fcms.entity.pettyCash.FmsPettyCashReplenishment;
import et.gov.eep.hrms.entity.employee.HrEmployees;

/**
 *
 * @author Mubarek jebel
 */
@Named

@ViewScoped
public class PettyCashRegisterManagedBean implements Serializable {

    //<editor-fold defaultstate="collapsed" desc="@Inject and @EJB">
    //Inject entities
    @Inject
    SessionBean sessionBean;
    @Inject
    private FmsChequePaymentVoucher chequePaymentVoucherEnty;
    DataModel<FmsChequePaymentVoucher> FmsChequePaymentDataModel;
    @Inject
    private FmsCasherAccount fmsCasherAccount;
    @Inject
    private FmsDailyCashRegister dailyCashRegisterEnty;
    @Inject
    private FmsVoucher fmsVoucher;
    @Inject
    private FmsCashPaymentOrder fmsCashPaymentOrder;
    @Inject
    private HrEmployees empEnty;
    @Inject
    private FmsPettyCashReplenishment fmsPettyCashReplenishment;
    @EJB
    private FmsLuSystemBeanLocal fmsLuSystemBeanLocal;
    @EJB
    private FmsVoucherBeanLocal fmsVoucherBeanLocal;
    @EJB
    FmsDailyCashRegisterBeanLocal cashRegisterBeanLocal;
    @EJB
    private FmsPettyCashReplenishmentBeanLocal fmsPettyCashReplenishmentBeanLocal;
    @EJB
    private CashPaymentOrderBeanLocal cashPaymentOrderBeanLocal;
    @EJB
    private FmsCasherAccountBeanLocal accountBeanLocal;
//</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="variable declaration">
    String optionRendered = "false";
    String currentDateofClosed = "";
    private String createOrSearchBundle = "New";
    private String icone = "ui-icon-plus";
    private String activeIndex;
    private String saveorUpdateBundle = "Save";
    private String Enddate = "";
    private String startingdate = "";
    private String amountInword = "";
    private String amountInFigure = "";
    private String ActionDebitCredit = "";
    private String insufficientBalancewarning = "";
    private String color = "";
    int updteStatus = 0;
    int dailyCashRegStatus = -1;
    int interCatListSizes = 0;
    int IntermidiateCatStatus = 0;
    int catagorynameStatus = 0;
    int GLListSize = 0;
    int MajorCatagorylistId = 0;
    int dataTableUpdateStatus = 0;
    FmsDailyCashRegister selectPettyCash;
    DataModel<FmsDailyCashRegister> dailyCashRegistersDetailDataModel;
    List<FmsDailyCashRegister> newList = new ArrayList<>();
    List<FmsDailyCashRegister> dailyCashRegisterlist;
    List<FmsVoucher> listVoucher = null;
    List<FmsDailyCashRegister> cashRegisterslist = new ArrayList<>();
    List<FmsVoucher> listCPVVoucher;
    List<FmsDailyCashRegister> listPaymentDate;
    List<FmsDailyCashRegister> dailyPaidList;
    List<FmsCasherAccount> listCasher;
    List<FmsCasherAccount> casherAccountsList;
    List<FmsDailyCashRegister> casherAccountsListModel = new ArrayList<>();
    List<FmsVoucher> listCasherVoucher;
    private List<FmsDailyCashRegister> fmsDailyCashRegistersList;
    Set<String> checkVoucherCode = new HashSet<>();
    Set<String> checkCasherId = new HashSet<>();
    private boolean disableBtnCreate;
    private boolean renderPnlCreatePettyCash = false;
    private boolean renderPnlManPage = true;
    boolean isDisabledTxtVoucherCode = true;
    boolean disablePaymentDate = true;
    boolean disableCasherName = false;
    boolean isRenderedTxtCashOnHand = false;
    boolean btnaddvisibility = true;
    boolean isDisabledBtnAdd = true;
    boolean isDisabledBtnSave = true;
    boolean isSticky = false;
    boolean btnClickVisibility = false;
    private BigDecimal totalPaidBefore = new BigDecimal(0.00);
    private BigDecimal totalToBePaidNow = new BigDecimal(0.00);
    private BigDecimal totalPaid = new BigDecimal(0.00);
    private BigDecimal totalEstablished = new BigDecimal(0.00);
    private BigDecimal cashOnHand = new BigDecimal(0.00);
    private BigDecimal cashOnHandOneThird = new BigDecimal(0.00);
    private BigDecimal amountToPay = new BigDecimal(0.00);
    BigDecimal oneThirdValue = new BigDecimal(0.3333333333333333);
    private double ValueDebitCredit = 0.0;
    private NumberConverter numberConverter = new NumberConverter();

    //</editor-fold>
    public PettyCashRegisterManagedBean() {
        numberConverter.setPattern("#,##0.00##");
        numberConverter.setMinIntegerDigits(1);
        numberConverter.setMaxIntegerDigits(40);
        numberConverter.setMaxFractionDigits(3);
    }

    @PostConstruct
    public void init() {
        try {
            empEnty.setEmpId("empone1");//set Emp Id from login Session Ex. EmpId= empone1 is for tola lala from Finance and Control Dep't
            fmsCasherAccount = accountBeanLocal.getCashierAccByEmpId(empEnty);
            getBalance();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
//<editor-fold defaultstate="collapsed" desc="Getter and Setter Methods">

    public NumberConverter getNumberConverter() {
        return numberConverter;
    }

    public void setNumberConverter(NumberConverter numberConverter) {
        this.numberConverter = numberConverter;
    }

    public FmsDailyCashRegister getDailyCashRegisterEnty() {
        if (dailyCashRegisterEnty == null) {
            dailyCashRegisterEnty = new FmsDailyCashRegister();
        }
        return dailyCashRegisterEnty;
    }

    public void setDailyCashRegisterEnty(FmsDailyCashRegister dailyCashRegisterEnty) {
        this.dailyCashRegisterEnty = dailyCashRegisterEnty;
    }

    public FmsCasherAccount getFmsCasherAccount() {
        if (fmsCasherAccount == null) {
            fmsCasherAccount = new FmsCasherAccount();
        }
        return fmsCasherAccount;
    }

    public void setFmsCasherAccount(FmsCasherAccount fmsCasherAccount) {
        this.fmsCasherAccount = fmsCasherAccount;
    }

    public BigDecimal getTotalPaidBefore() {
        return totalPaidBefore;
    }

    public void setTotalPaidBefore(BigDecimal totalPaidBefore) {
        this.totalPaidBefore = totalPaidBefore;
    }

    public BigDecimal getTotalToBePaidNow() {
        return totalToBePaidNow;
    }

    public void setTotalToBePaidNow(BigDecimal totalToBePaidNow) {
        this.totalToBePaidNow = totalToBePaidNow;
    }

    public BigDecimal getTotalPaid() {
        return totalPaid;
    }

    public void setTotalPaid(BigDecimal totalPaid) {
        this.totalPaid = totalPaid;
    }

    public BigDecimal getTotalEstabliseh() {
        return totalEstablished;
    }

    public void setTotalEstabliseh(BigDecimal totalEstablished) {
        this.totalEstablished = totalEstablished;
    }

    /**
     * @return the totalEstabliseh
     */
    /**
     * @return the cashOnHand
     */
    public BigDecimal getCashOnHand() {
        return cashOnHand;
    }

    /**
     * @param cashOnHand the cashOnHand to set
     */
    public void setCashOnHand(BigDecimal cashOnHand) {
        this.cashOnHand = cashOnHand;
    }

    public BigDecimal getCashOnHandOneThird() {
        return cashOnHandOneThird;
    }

    public void setCashOnHandOneThird(BigDecimal cashOnHandOneThird) {
        this.cashOnHandOneThird = cashOnHandOneThird;
    }

    /**
     * @return the cashOnHandPercent
     */
    /**
     * @return the fmsDailyCashRegistersList
     */
    public List<FmsDailyCashRegister> getFmsDailyCashRegistersList() {
        if (fmsDailyCashRegistersList == null) {
            fmsDailyCashRegistersList = new ArrayList<>();
        }
        return fmsDailyCashRegistersList;
    }

    /**
     * @param fmsDailyCashRegistersList the fmsDailyCashRegistersList to set
     */
    public void setFmsDailyCashRegistersList(List<FmsDailyCashRegister> fmsDailyCashRegistersList) {
        this.fmsDailyCashRegistersList = fmsDailyCashRegistersList;
    }

    public boolean isIsDisabledTxtVoucherCode() {
        return isDisabledTxtVoucherCode;
    }

    public void setIsDisabledTxtVoucherCode(boolean isDisabledTxtVoucherCode) {
        this.isDisabledTxtVoucherCode = isDisabledTxtVoucherCode;
    }

    public boolean isDisableCasherName() {
        return disableCasherName;
    }

    public void setDisableCasherName(boolean disableCasherName) {
        this.disableCasherName = disableCasherName;
    }

    public boolean isIsRenderedTxtCashOnHand() {
        return isRenderedTxtCashOnHand;
    }

    public void setIsRenderedTxtCashOnHand(boolean isRenderedTxtCashOnHand) {
        this.isRenderedTxtCashOnHand = isRenderedTxtCashOnHand;
    }

    public boolean isDisablePaymentDate() {
        return disablePaymentDate;
    }

    public void setDisablePaymentDate(boolean disablePaymentDate) {
        this.disablePaymentDate = disablePaymentDate;
    }

    public boolean isBtnClickVisibility() {
        return btnClickVisibility;
    }

    public void setBtnClickVisibility(boolean btnClickVisibility) {
        this.btnClickVisibility = btnClickVisibility;
    }

    public boolean isBtnaddvisibility() {
        return btnaddvisibility;
    }

    public void setBtnaddvisibility(boolean btnaddvisibility) {
        this.btnaddvisibility = btnaddvisibility;
    }

    public boolean isIsDisabledBtnAdd() {
        return isDisabledBtnAdd;
    }

    public void setIsDisabledBtnAdd(boolean isDisabledBtnAdd) {
        this.isDisabledBtnAdd = isDisabledBtnAdd;
    }

    public boolean isIsDisabledBtnSave() {
        return isDisabledBtnSave;
    }

    public void setIsDisabledBtnSave(boolean isDisabledBtnSave) {
        this.isDisabledBtnSave = isDisabledBtnSave;
    }

    public boolean isIsSticky() {
        return isSticky;
    }

    public void setIsSticky(boolean isSticky) {
        this.isSticky = isSticky;
    }

    public String getCreateOrSearchBundle() {
        return createOrSearchBundle;
    }

    public void setCreateOrSearchBundle(String createOrSearchBundle) {
        this.createOrSearchBundle = createOrSearchBundle;
    }

    public int getUpdteStatus() {
        return updteStatus;
    }

    public void setUpdteStatus(int updteStatus) {
        this.updteStatus = updteStatus;
    }

    public boolean isDisableBtnCreate() {
        return disableBtnCreate;
    }

    public void setDisableBtnCreate(boolean disableBtnCreate) {
        this.disableBtnCreate = disableBtnCreate;
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

    public String getActiveIndex() {
        return activeIndex;
    }

    public void setActiveIndex(String activeIndex) {
        this.activeIndex = activeIndex;
    }

    public FmsDailyCashRegister getSelectPettyCash() {
        return selectPettyCash;
    }

    public void setSelectPettyCash(FmsDailyCashRegister selectPettyCash) {
        this.selectPettyCash = selectPettyCash;
    }

    public FmsChequePaymentVoucher getChequePaymentVoucherEnty() {
        return chequePaymentVoucherEnty;
    }

    public void setChequePaymentVoucherEnty(FmsChequePaymentVoucher chequePaymentVoucherEnty) {
        this.chequePaymentVoucherEnty = chequePaymentVoucherEnty;
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

    public String getInsufficientBalancewarning() {
        return insufficientBalancewarning;
    }

    public void setInsufficientBalancewarning(String insufficientBalancewarning) {
        this.insufficientBalancewarning = insufficientBalancewarning;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
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

    public HrEmployees getEmpEnty() {
        if (empEnty == null) {
            empEnty = new HrEmployees();
        }
        return empEnty;
    }

    public void setEmpEnty(HrEmployees empEnty) {
        this.empEnty = empEnty;
    }

    public FmsCashPaymentOrder getFmsCashPaymentOrder() {
        if (fmsCashPaymentOrder == null) {
            fmsCashPaymentOrder = new FmsCashPaymentOrder();
        }
        return fmsCashPaymentOrder;
    }

    public void setFmsCashPaymentOrder(FmsCashPaymentOrder fmsCashPaymentOrder) {
        this.fmsCashPaymentOrder = fmsCashPaymentOrder;
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

    public List<FmsCasherAccount> getListCasher() {
        if (listCasher == null) {
            listCasher = new ArrayList<>();
        }
        return listCasher;
    }

    public void setListCasher(List<FmsCasherAccount> listCasher) {
        this.listCasher = listCasher;
    }

    public List<FmsCasherAccount> getCasherAccountsList() {
        if (casherAccountsList == null) {
            casherAccountsList = new ArrayList<>();

        }
        casherAccountsList = accountBeanLocal.findAllCasher();
        return casherAccountsList;
    }

    public void setCasherAccountsList(List<FmsCasherAccount> casherAccountsList) {
        this.casherAccountsList = casherAccountsList;
    }

    public List<FmsDailyCashRegister> getDailyPaidList() {
        if (dailyPaidList == null) {
            dailyPaidList = new ArrayList<>();
        }
        return dailyPaidList;
    }

    public void setDailyPaidList(List<FmsDailyCashRegister> dailyPaidList) {
        this.dailyPaidList = dailyPaidList;
    }

    public DataModel<FmsDailyCashRegister> getDailyCashRegistersDetailDataModel() {

        return dailyCashRegistersDetailDataModel;
    }

    public void setDailyCashRegistersDetailDataModel(DataModel<FmsDailyCashRegister> dailyCashRegistersDetailDataModel) {
        if (this.dailyCashRegistersDetailDataModel == null) {
            this.dailyCashRegistersDetailDataModel = new ListDataModel<>();
        }
        this.dailyCashRegistersDetailDataModel = dailyCashRegistersDetailDataModel;
    }

    //</editor-fold> 
    // <editor-fold defaultstate="collapsed" desc="save - Update">

    public void saveDailyPettyCashRegister() {
        AAA securityService = new AAA();
        IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
        String systemBundle = "et/gov/eep/commonApplications/securityServer";
        String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
        if (security.checkAccess(sessionBean.getUserName(), "saveDailyPettyCashRegister", dataset)) {
            dailyCashRegisterEnty.setAmount(fmsVoucher.getFmsCashPaymentOrder().getAmountInFigure());
            dailyCashRegisterEnty.setPurpose(fmsVoucher.getPurpose());
            dailyCashRegisterEnty.setChasherId(fmsCasherAccount);
            dailyCashRegisterEnty.setVoucherCode(fmsVoucher.getVoucherId());
            dailyCashRegisterEnty.setPettyCashId(fmsVoucher.getFmsCashPaymentOrder());
            if (updteStatus == 0) {
                try {
                    dailyCashRegisterEnty.setStatus(Constants.FMS_DAILY_CASH_REGISTERED_STATUS);
                    cashRegisterBeanLocal.create(dailyCashRegisterEnty);
                    JsfUtil.addSuccessMessage("Saved Successfuly");
                    clearPage();
                } catch (Exception e) {
                    e.printStackTrace();
                    JsfUtil.addFatalMessage("Unable to save. Try again reloading the page");

                }
            } else if (updteStatus == 1) {
                cashRegisterBeanLocal.edit(dailyCashRegisterEnty);
                JsfUtil.addSuccessMessage("Updated Successfully!");
                clearPage();
            }
            getBalance();
        } else {
            JsfUtil.addFatalMessage("You don't have appropriate permission, please contact the system administrator. ");
            EventEntry eventEntry = new EventEntry();
            eventEntry.setSessionId(sessionBean.getSessionID());
            eventEntry.setUserId(sessionBean.getUserId());
            QName qualifiedName = new QName("", "project");
            JAXBElement<String> test = new JAXBElement<String>(qualifiedName, String.class, null, sessionBean.getUserName());
            eventEntry.setUserLogin(test);
            security.addEventLog(eventEntry, dataset);

        }
    }

    public void searchFmsDailyCashRegister() {
        cashRegisterslist = new ArrayList<>();
        //search by Cashier Name only
        if (!(empEnty.getFirstName().isEmpty())) {
            cashRegisterslist = cashRegisterBeanLocal.getListByName(empEnty);
            //search all
        } else {
            cashRegisterslist = cashRegisterBeanLocal.getAllList();
        }
        dailyCashRegistersDetailDataModel = new ListDataModel(cashRegisterslist);
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Search">
    public List<FmsVoucher> ChartOfAccountSearchlist(String voucheid) {
        listVoucher = null;
        fmsVoucher.setVoucherId(voucheid);
        listVoucher = fmsVoucherBeanLocal.searchVoucheIdListCRVWithHold(fmsVoucher);
        return listVoucher;
    }

    public void getGLCodeInformation(SelectEvent event) {

        String selectedVoucherId = event.getObject().toString();
        fmsVoucher.setVoucherId(selectedVoucherId);
        fmsVoucher = fmsVoucherBeanLocal.getVoucherIDInfo(fmsVoucher);
        recreatePettyCashModelDetail();
    }

    public List<FmsDailyCashRegister> searchByDate(ValueChangeEvent date) {
        Date dt = (Date) date.getNewValue();
        dailyCashRegisterEnty.setRegistrationDate(dt);

        dailyCashRegisterlist = cashRegisterBeanLocal.getdate(dailyCashRegisterEnty);
        return dailyCashRegisterlist;
    }

    public List<FmsCasherAccount> CasherAccountSearchlist(String EmpCode) {
        FmsCasherAccount casherAccount = new FmsCasherAccount();
        listCasher = accountBeanLocal.searchCasherName(casherAccount);
        return listCasher;
    }

    public List<FmsDailyCashRegister> paymentDateList(String registrationDate) {

        listPaymentDate = null;
        FmsDailyCashRegister dailyCashRegister = new FmsDailyCashRegister();
        dailyCashRegister.setRegistrationDate(dailyCashRegisterEnty.getRegistrationDate());
        listPaymentDate = cashRegisterBeanLocal.searchByDate(dailyCashRegister);
        return listPaymentDate;
    }

    public void ChashRegVoucherSearch_(SelectEvent event) {
        casherAccountsListModel = new ArrayList<>();
        String selectedChash = event.getObject().toString();
        listCasherVoucher = fmsVoucherBeanLocal.getChashRegVoucherInfo(selectedChash);
        for (int i = 0; i < listCasherVoucher.size(); i++) {
            dailyCashRegisterEnty = listCasherVoucher.get(i).getFmsCashPaymentOrder().getFmsDailyCashRegisterList();
            fmsCasherAccount = dailyCashRegisterEnty.getChasherId();
            dailyCashRegisterEnty.setAmount(listCasherVoucher.get(i).getFmsCashPaymentOrder().getAmountInFigure());
            dailyCashRegisterEnty.setPurpose(listCasherVoucher.get(i).getPurpose());
            dailyCashRegisterEnty.setVoucherDate(listCasherVoucher.get(i).getFmsPettyCashPaymentVoucher().getPreparedDate());
            dailyCashRegisterEnty.setVoucherCode(listCasherVoucher.get(i).getVoucherId());
            casherAccountsListModel.add(dailyCashRegisterEnty);

        }
        if (listCasherVoucher.size() > 0) {
        }
        recreatePettyCashModelDetail();
        updteStatus = 1;
        saveorUpdateBundle = "Update";
    }

    public void ChashRegVoucherSearch(SelectEvent event) {
        casherAccountsListModel = new ArrayList<>();
        String selectedVoucherId = event.getObject().toString();
        fmsVoucher.setVoucherId(selectedVoucherId);
        fmsVoucher = fmsVoucherBeanLocal.getVoucherIDInfo(fmsVoucher);
        dailyCashRegisterEnty = fmsVoucher.getFmsCashPaymentOrder().getFmsDailyCashRegisterList();
        fmsCasherAccount = dailyCashRegisterEnty.getChasherId();
        dailyCashRegisterEnty.setAmount(fmsVoucher.getFmsCashPaymentOrder().getAmountInFigure());
        dailyCashRegisterEnty.setPurpose(fmsVoucher.getPurpose());
        dailyCashRegisterEnty.setVoucherDate(fmsVoucher.getFmsCashPaymentOrder().getPreparedDate());
        dailyCashRegisterEnty.setVoucherCode(fmsVoucher.getVoucherId());
        casherAccountsListModel.add(dailyCashRegisterEnty);
        recreatePettyCashModelDetail();
        updteStatus = 1;
        saveorUpdateBundle = "Update";

    }

    public void getGLCodeInformation1(SelectEvent event) {
        String selectedVoucherId = event.getObject().toString();
        fmsVoucher.setVoucherId(selectedVoucherId);
        fmsVoucher = fmsVoucherBeanLocal.getVoucherIDInfo(fmsVoucher);
        recreatePettyCashModelDetail();
        updteStatus = 1;
        saveorUpdateBundle = "Update";

    }
    // </editor-fold>

    public SelectItem[] getPettyCashVoucherList() {
        casherAccountsList = accountBeanLocal.findAllCasher();
        return JsfUtil.getSelectItems(casherAccountsList, true);
    }

    public List<FmsVoucher> CPOVoucherCodelist(String voucheid) {
        listVoucher = null;
        fmsVoucher.setVoucherId(voucheid);
        listVoucher = fmsVoucherBeanLocal.searchVoucheIdListCPODailyCash(fmsVoucher);
        return listVoucher;
    }

    public void getBalance() {
        try {
            calculateCashOnHand();
            warnCashier();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //calculate cash on hand
    public void calculateCashOnHand() {
        try {
            dailyPaidList = new ArrayList<>();
            totalEstablished = BigDecimal.valueOf(fmsCasherAccount.getAmount());
            fmsPettyCashReplenishment = fmsPettyCashReplenishmentBeanLocal.getNonReplenishedByCashierId(fmsCasherAccount);
            if (fmsPettyCashReplenishment != null && fmsPettyCashReplenishment.getWfStatus() != Constants.AUTHORIZED) {//IF NOT_AUTHORIZED OR NOTT_REPLENISHED AND IF THE REQUEST IS BEING PROCESSED
                dailyCashRegStatus = fmsPettyCashReplenishment.getWfStatus();
            } else {
                dailyCashRegStatus = Constants.FMS_DAILY_CASH_REGISTERED_STATUS;//45
            }
            dailyPaidList = cashRegisterBeanLocal.findByCashierIdAndStatus(fmsCasherAccount, dailyCashRegStatus);
            for (int i = 0; i < dailyPaidList.size(); i++) {
                totalPaidBefore = totalPaidBefore.add(dailyPaidList.get(i).getAmount());//calculate total paid amount before the current transaction
                if (dailyPaidList.get(i).getStatus() == Constants.AUTHORIZED) {
                    totalPaidBefore = totalPaidBefore.subtract(dailyPaidList.get(i).getAmount());//calculate total paid amount before the current transaction
                }
            }
            totalPaid = totalPaidBefore;
            cashOnHand = totalEstablished.subtract(totalPaid);
            cashOnHandOneThird = totalEstablished.multiply(oneThirdValue);//cashOnHandOneThird = totalEstablished *  1/3;
        } catch (Exception e) {
            e.printStackTrace();
            JsfUtil.addFatalMessage("Unable to Process. Try again reloding the page.");
        }
    }

    //notify cashier
    public void warnCashier() {
        try {
            if (cashOnHand.compareTo(BigDecimal.ZERO) == 0) {
                JsfUtil.addWarningMessage("Your Cash on Hand: " + cashOnHand + " is Insufficient. Please Submit Your Replanishment Request.");
                isDisabledBtnSave = true;
                disablePaymentDate = true;
                isDisabledTxtVoucherCode = true;
            } else if ((cashOnHand.compareTo(BigDecimal.ZERO) == 1) && (cashOnHand.compareTo(cashOnHandOneThird) <= 0)) {//if cashOnHand <= cashOnHandOneThird && cashOnHand>0
                JsfUtil.addWarningMessage("Your Cash on Hand: " + cashOnHand + "  is Less or equals to 1/3rd of the Total Established : " + totalEstablished + "  Please Submit Your Replanishment Request.");
                isDisabledBtnSave = false;
                disablePaymentDate = false;
                isDisabledTxtVoucherCode = false;
            } else {//if cashOnHand > cashOnHandOneThird
                isDisabledBtnAdd = false;
                disablePaymentDate = false;
                isDisabledTxtVoucherCode = false;
            }
        } catch (Exception e) {
            JsfUtil.addFatalMessage("Unable to Process. Try again reloding the page.");
            e.printStackTrace();
        }
    }

    //add subsidery detail
    public String addSubsidiaryLedgerDetail() {
        try {
            dailyCashRegisterEnty.setAmount(fmsVoucher.getFmsCashPaymentOrder().getAmountInFigure());
            dailyCashRegisterEnty.setPurpose(fmsVoucher.getPurpose());
            dailyCashRegisterEnty.setVoucherDate(fmsVoucher.getFmsCashPaymentOrder().getPreparedDate());
            accountBeanLocal.getById(fmsCasherAccount);
            dailyCashRegisterEnty.setChasherId(fmsCasherAccount);
            dailyCashRegisterEnty.setVoucherCode(fmsVoucher.getVoucherId());
            dailyCashRegisterEnty.setPettyCashId(fmsVoucher.getFmsCashPaymentOrder());
            if (checkVoucherCode.contains(dailyCashRegisterEnty.getPettyCashId().getFmsVOUCHERID().getVoucherId())) {
                JsfUtil.addFatalMessage("Voucher  " + dailyCashRegisterEnty.getPettyCashId().getFmsVOUCHERID() + "  is Duplicated");
                return null;
            } else {
                newList.add(dailyCashRegisterEnty);
                checkVoucherCode.add(dailyCashRegisterEnty.getPettyCashId().getFmsVOUCHERID().getVoucherId());
                setFmsDailyCashRegistersList(newList);
                recreateModelDetail();
                clearPopup();
            }
        } catch (Exception e) {
            e.printStackTrace();
            JsfUtil.addFatalMessage("Unable to Add. Try Again Reloading the Page");
        }
        return null;
    }

    //recreate to assign getFmsDailyCashRegistersList value to dailyCashRegistersDetailDataModel
    public void recreateModelDetail() {
        dailyCashRegistersDetailDataModel = null;
        dailyCashRegistersDetailDataModel = new ListDataModel(getFmsDailyCashRegistersList());
    }

    //recreate to assign casherAccountsListModel value to dailyCashRegistersDetailDataModel
    public void recreatePettyCashModelDetail() {
        dailyCashRegistersDetailDataModel = null;
        dailyCashRegistersDetailDataModel = new ListDataModel(casherAccountsListModel);
    }

    //update info
    public void updateAccountUseDetail() {
        dataTableUpdateStatus = 1;
        dailyCashRegisterEnty = getDailyCashRegistersDetailDataModel().getRowData();

    }

    //handle employee change
    public void handleEmplyeChange(ValueChangeEvent event) {
        if (null != event.getNewValue()) {
            FmsCasherAccount casherAccount = new FmsCasherAccount();
            fmsCasherAccount = accountBeanLocal.getCasherAccountInfo(casherAccount);
        }
    }

    //select cashier value change
    public void onSelectCashier(ValueChangeEvent event) {
        fmsCasherAccount = (FmsCasherAccount) event.getNewValue();
        getBalance();
    }

    //dialy cash info
    public String getPCPVDailyCashInfo(SelectEvent event) {
        try {
            fmsVoucher = (FmsVoucher) event.getObject();
            amountToPay = fmsVoucher.getFmsCashPaymentOrder().getAmountInFigure();
            if (amountToPay.compareTo(cashOnHand) == 1) {//if (amountToPay.doubleValue() > cashOnHand.doubleValue()) 
                JsfUtil.addFatalMessage("Your Cash on Hand: " + cashOnHand + "  is Insufficient to Register the selected Voucher. Please Submit Your Replanishment Request.");
                isDisabledBtnSave = true;
                disablePaymentDate = true;
                return null;
            } else if (cashOnHand.compareTo(BigDecimal.ZERO) == 0) {
                isDisabledBtnSave = true;
                disablePaymentDate = true;
                isSticky = true;
                JsfUtil.addWarningMessage("Your Cash on Hand: " + cashOnHand + "  is Insufficient. Please Submit Your Replanishment Request.");
            } else {
                isDisabledBtnSave = false;
                disablePaymentDate = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            JsfUtil.addFatalMessage("Unable to process. Try again reloading the page");
        }
        return null;
    }

    //select petty cahsh
    public void selectPettyCashRow(SelectEvent event) {
        isDisabledTxtVoucherCode = true;
        disablePaymentDate = false;
        isDisabledBtnSave = false;
        dailyCashRegisterEnty = (FmsDailyCashRegister) event.getObject();
        dailyCashRegisterEnty = cashRegisterBeanLocal.getById(dailyCashRegisterEnty);
        fmsCasherAccount = dailyCashRegisterEnty.getChasherId();
        totalEstablished = BigDecimal.valueOf(fmsCasherAccount.getAmount());
        dailyPaidList = cashRegisterBeanLocal.findDailyPaidListByChaserIdAndStatus(fmsCasherAccount);
        for (int i = 0; i < dailyPaidList.size(); i++) {
            totalPaidBefore = totalPaidBefore.add(dailyPaidList.get(i).getAmount());//calculate total paid before current transaction
        }
        cashOnHand = totalEstablished.subtract(totalPaidBefore);
        fmsVoucher.getFmsCashPaymentOrder().setAmountInFigure(dailyCashRegisterEnty.getPettyCashId().getAmountInFigure());
        fmsVoucher.getFmsCashPaymentOrder().setFmsVOUCHERID(dailyCashRegisterEnty.getPettyCashId().getFmsVOUCHERID());
        fmsVoucher.getFmsCashPaymentOrder().setPreparedDate(dailyCashRegisterEnty.getPettyCashId().getPreparedDate());
        fmsVoucher.setVoucherId(dailyCashRegisterEnty.getPettyCashId().getFmsVOUCHERID().getVoucherId());
        renderPnlCreatePettyCash = true;
        renderPnlManPage = false;
        activeIndex = "0";
        createOrSearchBundle = "Search";
        setIcone("ui-icon-search");
        disableBtnCreate = true;
        updteStatus = 1;
        saveorUpdateBundle = "Update";
    }

    //payment mode listener value change
    public void paymentModeChangeListener(ValueChangeEvent event) {
        System.err.println("hellw change listener");
        setOptionRendered("true");
        optionRendered = "true";
    }

    //clear
    public String clearPage() {
        setFmsDailyCashRegistersList(null);
        fmsCashPaymentOrder = new FmsCashPaymentOrder();
        fmsVoucher = new FmsVoucher();
        dailyCashRegisterEnty = new FmsDailyCashRegister();
        dailyCashRegistersDetailDataModel = new ListDataModel<>();
        cashOnHand = new BigDecimal(0.00);
        totalPaid = new BigDecimal(0.00);
        totalPaidBefore = new BigDecimal(0.00);
        totalEstablished = new BigDecimal(0.00);
        updteStatus = 0;
        saveorUpdateBundle = "Save";

        return null;
    }

    //clear
    public String clearPopup() {
        fmsCashPaymentOrder = new FmsCashPaymentOrder();
        newList = new ArrayList<>();
        listVoucher = new ArrayList<>();
        fmsVoucher = new FmsVoucher();
        amountInword = "";
        updteStatus = 0;
        return null;
    }

    //create and search render
    public void createNewPettyCash() {
        clearPage();
        getBalance();
        saveorUpdateBundle = "Save";
        disableBtnCreate = false;
        if (createOrSearchBundle.equals("New")) {
            renderPnlCreatePettyCash = true;
            renderPnlManPage = false;
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
