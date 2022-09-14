/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.controller.Bond;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;
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
import securityBean.Constants;
import securityBean.SessionBean;
import webService.AAA;
import webService.EventEntry;
import webService.IAdministration;
import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.fcms.businessLogic.Bond.BondForeignScheduleBeanLocal;
import et.gov.eep.fcms.businessLogic.Bond.FmsBondLiborBeanLocal;
import et.gov.eep.fcms.businessLogic.Bond.BondForeignBeanLocals;
import et.gov.eep.fcms.entity.Bond.FmsBondForeign;
import et.gov.eep.fcms.entity.Bond.FmsBondForeignSchedule;
import et.gov.eep.fcms.entity.Bond.FmsBondLibor;
import et.gov.eep.fcms.entity.FmsLuCurrency;
import et.gov.eep.fcms.mapper.FmsLuCurrencyFacade;

/**
 *
 * @author mora
 */
@Named(value = "bondForeignScheduleController")
@ViewScoped
public class BondForeignScheduleController implements Serializable {

    //<editor-fold defaultstate="collapsed" desc="Inject Entities and @ EJB">
//Inject entity

    @Inject
    FmsBondForeignSchedule foreignSchedule;
    @Inject
    FmsBondForeign BondForeign;
    @Inject
    FmsLuCurrency fmsLuCurrency;
    @Inject
    FmsBondLibor BondLibor;
    @Inject
    SessionBean sessionBeanLocal;
    //Inject bussiness logic using @EJB 
    @EJB
    BondForeignScheduleBeanLocal foreignBean;
    @EJB
    BondForeignBeanLocals foreignBeanLocal;
    @EJB
    FmsBondLiborBeanLocal liborBean;
    @EJB
    FmsLuCurrencyFacade currencyFacade;
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="variables declaration">
    //Declaring instance variables
    Date fromStartDate;
    Date toEndDate;
    Date applicationdate;
    String Bondcurrency;
    String saveUpdate = "Create";
    String searchiteam = "";
    Integer noofInstallment;
    Integer numberOfDays;
    int updateStatus = 0;
    Double AmountToBePaid = 0.0;
    Double liborrate;
    Double BondInterest;
    Double interestRate;
    double totalAmountDue = 0.0;
    double principal;
    double interest = 0.0;
    DataModel<FmsBondForeignSchedule> repaymentScheduleDatamodel;
    DataModel<FmsBondForeign> BondForeignAddDataModel;
    private List<FmsBondForeignSchedule> BondRepaymentSchedules;
    private List<FmsBondForeign> BondForeigns;
    private List<FmsBondForeign> BondForeignList = new ArrayList<>();
    private NumberConverter numberConverter = new NumberConverter();
    private FmsBondForeign BondForeignselect;
    private boolean disableBtnCreate;
    private boolean renderPnlCreateAdditional = false;
    private boolean renderPnlManPage = true;
    private String createOrSearchBundle = "";
    private String icone = "ui-icon-document";
    private String activeIndex;
    boolean disable = false;
//</editor-fold>

    public BondForeignScheduleController() {

        numberConverter.setPattern("#,##0.00##");
        numberConverter.setMinIntegerDigits(1);
        numberConverter.setMaxIntegerDigits(40);
        numberConverter.setMaxFractionDigits(2);
    }

    //<editor-fold defaultstate="collapsed" desc="Getter and Setter Methods">
    public double getPrincipal() {
        return principal;
    }

    public void setPrincipal(double principal) {
        this.principal = principal;
    }

    public double getTotalAmountDue() {
        return totalAmountDue;
    }

    public void setTotalAmountDue(double totalAmountDue) {
        this.totalAmountDue = totalAmountDue;
    }

    public double getInterest() {
        return interest;
    }

    public void setInterest(double interest) {
        this.interest = interest;
    }

    public List<FmsBondForeign> getBondForeignList() {
        return BondForeignList;
    }

    public void setBondForeignList(List<FmsBondForeign> BondForeignList) {
        this.BondForeignList = BondForeignList;

    }

    public FmsBondLibor getBondLibor() {
        return BondLibor;
    }

    public void setBondLibor(FmsBondLibor BondLibor) {
        this.BondLibor = BondLibor;
    }

    public Date getApplicationdate() {
        return applicationdate;
    }

    public void setApplicationdate(Date applicationdate) {
        this.applicationdate = applicationdate;
    }

    public String getBondcurrency() {
        return Bondcurrency;
    }

    public void setBondcurrency(String Bondcurrency) {
        this.Bondcurrency = Bondcurrency;
    }

    public Double getLiborrate() {
        return liborrate;
    }

    public void setLiborrate(Double liborrate) {
        this.liborrate = liborrate;
    }

    public Double getBondInterest() {
        return BondInterest;
    }

    public void setBondInterest(Double BondInterest) {
        this.BondInterest = BondInterest;
    }

    public Double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(Double interestRate) {
        this.interestRate = interestRate;
    }

    public Integer getNoofInstallment() {
        return noofInstallment;
    }

    public void setNoofInstallment(Integer noofInstallment) {
        this.noofInstallment = noofInstallment;
    }

    public Integer getNumberOfDays() {
        return numberOfDays;
    }

    public void setNumberOfDays(Integer numberOfDays) {
        this.numberOfDays = numberOfDays;
    }

    public NumberConverter getNumberConverter() {
        return numberConverter;
    }

    public void setNumberConverter(NumberConverter numberConverter) {
        this.numberConverter = numberConverter;
    }

    public Double getAmountToBePaid() {
        return AmountToBePaid;
    }

    public void setAmountToBePaid(Double AmountToBePaid) {
        this.AmountToBePaid = AmountToBePaid;
    }

    public Date getFromStartDate() {
        return fromStartDate;
    }

    public void setFromStartDate(Date fromStartDate) {
        this.fromStartDate = fromStartDate;
    }

    public Date getToEndDate() {
        return toEndDate;
    }

    public void setToEndDate(Date toEndDate) {
        this.toEndDate = toEndDate;
    }

    public FmsLuCurrency getFmsLuCurrency() {
        if (fmsLuCurrency == null) {
            fmsLuCurrency = new FmsLuCurrency();
        }
        return fmsLuCurrency;
    }

    public void setFmsLuCurrency(FmsLuCurrency fmsLuCurrency) {
        this.fmsLuCurrency = fmsLuCurrency;
    }

    public FmsBondForeign getBondForeign() {
        if (BondForeign == null) {
            BondForeign = new FmsBondForeign();
        }
        return BondForeign;
    }

    public void setBondForeign(FmsBondForeign BondForeign) {
        this.BondForeign = BondForeign;
    }

    public FmsBondForeignSchedule getForeignSchedule() {
        if (foreignSchedule == null) {
            foreignSchedule = new FmsBondForeignSchedule();
        }
        return foreignSchedule;
    }

    public void setForeignSchedule(FmsBondForeignSchedule foreignSchedule) {
        this.foreignSchedule = foreignSchedule;
    }

    public String getSaveUpdate() {
        return saveUpdate;
    }

    public void setSaveUpdate(String saveUpdate) {
        this.saveUpdate = saveUpdate;
    }

    public int getUpdateStatus() {
        return updateStatus;
    }

    public void setUpdateStatus(int updateStatus) {
        this.updateStatus = updateStatus;
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

    public boolean isRenderPnlCreateAdditional() {
        return renderPnlCreateAdditional;
    }

    public void setRenderPnlCreateAdditional(boolean renderPnlCreateAdditional) {
        this.renderPnlCreateAdditional = renderPnlCreateAdditional;
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

    public boolean isDisable() {
        return disable;
    }

    public void setDisable(boolean disable) {
        this.disable = disable;
    }

    public void setSearchiteam(String searchiteam) {
        this.searchiteam = searchiteam;
    }

    public FmsBondForeign getBondForeignselect() {
        return BondForeignselect;
    }

    public void setBondForeignselect(FmsBondForeign BondForeignselect) {
        this.BondForeignselect = BondForeignselect;
    }

    public DataModel<FmsBondForeign> getBondForeignAddDataModel() {
        return BondForeignAddDataModel;
    }

    public void setBondForeignAddDataModel(DataModel<FmsBondForeign> BondForeignAddDataModel) {
        this.BondForeignAddDataModel = BondForeignAddDataModel;
    }

    public DataModel<FmsBondForeignSchedule> getRepaymentScheduleDatamodel() {

        if (repaymentScheduleDatamodel == null) {
            repaymentScheduleDatamodel = new ListDataModel<>();
        }
        return repaymentScheduleDatamodel;
    }

    public void setRepaymentScheduleDatamodel(DataModel<FmsBondForeignSchedule> repaymentScheduleDatamodel) {
        this.repaymentScheduleDatamodel = repaymentScheduleDatamodel;
    }

    public List<FmsBondForeignSchedule> getBondRepaymentSchedules() {
        if (BondRepaymentSchedules == null) {
            BondRepaymentSchedules = new ArrayList<>();
        }
        return BondRepaymentSchedules;
    }

    public void setBondRepaymentSchedules(List<FmsBondForeignSchedule> BondRepaymentSchedules) {
        this.BondRepaymentSchedules = BondRepaymentSchedules;
    }

    List<FmsBondForeignSchedule> repaymentSchedules() {
        if (BondRepaymentSchedules == null) {
            BondRepaymentSchedules = new ArrayList<>();
        }
        return BondRepaymentSchedules;
    }

    public List<FmsBondForeign> getBondForeigns() {
        return BondForeigns;
    }

    public void setBondForeigns(List<FmsBondForeign> BondForeigns) {
        this.BondForeigns = BondForeigns;
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Array list and select []Items">
    public List<FmsBondForeignSchedule> scheduleList() {
        foreignSchedule.getBondForeignId();
        return this.foreignBean.searchForingns(foreignSchedule);
    }

    public List<FmsBondForeign> searchAll() {
        List<FmsBondForeign> foreigns = null;

        foreigns = foreignBeanLocal.searchAll();
        return foreigns;
    }

    public ArrayList<FmsBondForeign> searchBondId(String BondId) {
        ArrayList<FmsBondForeign> foreigns = null;
        BondForeign.setSerialNo(BondId);
        foreigns = foreignBeanLocal.searchBondId(BondForeign);
        return foreigns;
    }

    public ArrayList<FmsBondForeign> searchName(String BondId) {
        ArrayList<FmsBondForeign> foreigns = null;
        BondForeign.setBuyerFullName(BondId);
        foreigns = foreignBeanLocal.searchName(BondForeign);
        return foreigns;
    }

    public ArrayList<FmsBondForeignSchedule> searchFmsStatus(BigDecimal scheduleId) {
        ArrayList<FmsBondForeignSchedule> schedules = null;
        foreignSchedule.setLRepaymentSchId(scheduleId);
        schedules = foreignBean.searchStatus(foreignSchedule);
        return schedules;
    }

    public SelectItem[] getBondList() {
        return JsfUtil.getSelectItems(foreignBean.searchStatus(foreignSchedule), true);
    }

    public SelectItem[] getCurrencyList() {
        return JsfUtil.getSelectItems(currencyFacade.findAll(), true);
    }

    public SelectItem[] getLiborRateList() {
        return JsfUtil.getSelectItems(liborBean.searchLiborRate(BondLibor), true);

    }
//</editor-fold>

    /*recreate method to assign the value of FmsBondForeignScheduleList to repaymentScheduleDatamodel*/
    public void recreateModelSrnDetailPop() {
        repaymentScheduleDatamodel = null;
        repaymentScheduleDatamodel = new ListDataModel(BondForeign.getFmsBondForeignScheduleList());
    }

    /*update schedule method to assign interst and principal of foreign schedule*/
    public void updateforeignSchedule() {
        foreignSchedule = getRepaymentScheduleDatamodel().getRowData();
        interest = foreignSchedule.getInterest();
        principal = foreignSchedule.getPrincipal();
        foreignSchedule.setInterest(interest);
        foreignSchedule.setPrincipal(principal);
        AmountToBePaid = interest + principal;
        foreignSchedule.setPaidAmount(AmountToBePaid);

    }

    //update method for Bond foreign schedule all
    public void updateScheduleall() {
        try {
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(sessionBeanLocal.getUserName(), "updateScheduleall", dataset)) {
                foreignSchedule.setSerialNo(BondForeign.getSerialNo());
                foreignSchedule.setStatus(String.valueOf(Constants.PAID));
                foreignBean.Eidt(foreignSchedule);
                recreateModelSrnDetailPop();
                repaymentSchedules().remove(foreignSchedule);
                JsfUtil.addSuccessMessage("payment is successfully done");
            } else {
                JsfUtil.addFatalMessage("You don't have appropriate permission, please contact the system administrator.");
                EventEntry eventEntry = new EventEntry();
                eventEntry.setSessionId(sessionBeanLocal.getSessionID());
                eventEntry.setUserId(sessionBeanLocal.getUserId());
                QName qualifiedName = new QName("", "project");
                JAXBElement<String> test = new JAXBElement<String>(qualifiedName, String.class, null, sessionBeanLocal.getUserName());
                eventEntry.setUserLogin(test);
//..... add more information by calling fields defined in the object 
                security.addEventLog(eventEntry, dataset);
            }
        } catch (Exception ex) {
        }
    }

    // update method for Bond foreign schedule
    public void updateBondForeignSchedule() {
        try {
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(sessionBeanLocal.getUserName(), "updateBondForeignSchedule", dataset)) {
                foreignSchedule.setSerialNo(BondForeign.getSerialNo());
                foreignSchedule.setStatus(String.valueOf(Constants.PAID));
                foreignBean.Eidt(foreignSchedule);
                recreateModelSrnDetailPop();
                JsfUtil.addSuccessMessage("payment is successfully done");
            } else {
                JsfUtil.addFatalMessage("You don't have appropriate permission, please contact the system administrator.");
                EventEntry eventEntry = new EventEntry();
                eventEntry.setSessionId(sessionBeanLocal.getSessionID());
                eventEntry.setUserId(sessionBeanLocal.getUserId());
                QName qualifiedName = new QName("", "project");
                JAXBElement<String> test = new JAXBElement<String>(qualifiedName, String.class, null, sessionBeanLocal.getUserName());
                eventEntry.setUserLogin(test);
//..... add more information by calling fields defined in the object 
                security.addEventLog(eventEntry, dataset);
            }
        } catch (Exception ex) {
        }
    }

    /*select event for setting repayment shcedule id of the selected Bond*/
    public void getBondInfo(SelectEvent event) {
        foreignSchedule.setLRepaymentSchId((BigDecimal) event.getObject());
        updateStatus = 2;
        saveUpdate = "Update";
    }

    /*select event for foreign schedule*/
    public void getBySchedule(SelectEvent event) {
        BondForeign.setSerialNo(event.getObject().toString());
        BondForeign = foreignBeanLocal.BondForeigninfo(BondForeign);
        renderPnlCreateAdditional = true;
        renderPnlManPage = false;
        createOrSearchBundle = "Search";
        recreateModelSrnDetailPop();
    }

    /*select event for the selected Bond to set id of foreign schedule*/
    public void getSchedule(SelectEvent event) {
        repaymentScheduleDatamodel = null;
        BondRepaymentSchedules = null;
        BondForeign = (FmsBondForeign) event.getObject();
        foreignSchedule.setBondForeignId(BondForeign);
        recreateModelSrnDetailPop();
        renderPnlCreateAdditional = true;
        renderPnlManPage = false;
        createOrSearchBundle = "Search";
        setIcone("ui-icon-search");
    }

    /*select event for searching name of Bond buyer*/
    public void getBuyersName(SelectEvent event) {

        BondForeign.setBuyerFullName(event.getObject().toString());
        BondForeignList = foreignBeanLocal.searchName(BondForeign);
    }

    /*value change event to add data of Bond foreign to repayment schedule */
    public void addRepaymentScheduleInfoDetail(ValueChangeEvent event) {
        if (!repaymentSchedules().contains(foreignSchedule)) {
            foreignSchedule = foreignBean.getFmsBondForeignSchedule(foreignSchedule);
            repaymentSchedules().add(foreignSchedule);
            recreateModelSrnDetailPop();
        }
    }

    /*value change event to get currency of the given value*/
    public void handleSelectCurrency(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            fmsLuCurrency = (FmsLuCurrency) event.getNewValue();
            BondForeign.setFmsLuCurrency(fmsLuCurrency);
        }
    }

    // search from foreign bonf usign serial number and by default
    public void searchBySerialNo() {
        if (BondForeign.getSerialNo() != null) {
            BondForeignList = foreignBeanLocal.findBySerialNo(BondForeign);

            recreateModelSrnDetailPop();
        } else {
            BondForeignList = foreignBeanLocal.searchAll();
            recreateModelSrnDetailPop();
        }
    }

    // search method for Bond foreign schedule from Bond foreign table using serial number, buyers full name, principal amoung and currency 
    public void searchForeignRepayment() {
        if (BondForeign.getSerialNo() != null && BondForeign.getBuyerFullName() != null && BondForeign.getPrincipalamount() == null && fmsLuCurrency.getCurrencyId() == null) {
            BondForeignList = foreignBeanLocal.searchBondId(BondForeign);
            setBondForeignList(BondForeignList);
            recreateModelSrnDetailPop();
        } else if (BondForeign.getSerialNo() == null && BondForeign.getBuyerFullName() != null && BondForeign.getPrincipalamount() == null && fmsLuCurrency.getCurrencyId() == null) {
            BondForeignList = foreignBeanLocal.searchName(BondForeign);
            setBondForeignList(BondForeignList);
            recreateModelSrnDetailPop();
        } else if (BondForeign.getSerialNo() != null && BondForeign.getBuyerFullName() == null && BondForeign.getPrincipalamount() == null && fmsLuCurrency.getCurrencyId() == null) {
            BondForeignList = foreignBeanLocal.findBySerialNo(BondForeign);
            setBondForeignList(BondForeignList);
            recreateModelSrnDetailPop();
        } else if (BondForeign.getSerialNo() != null && BondForeign.getBuyerFullName() != null && BondForeign.getPrincipalamount() != null && fmsLuCurrency.getCurrencyId() != null) {
            BondForeignList = foreignBeanLocal.findBySerialNameAmountAndCurency(BondForeign);
            setBondForeignList(BondForeignList);
            recreateModelSrnDetailPop();
        } else if (BondForeign.getSerialNo() != null && BondForeign.getBuyerFullName() == null && BondForeign.getPrincipalamount() != null && fmsLuCurrency.getCurrencyId() != null) {
            BondForeignList = foreignBeanLocal.findBySerialAmountAndCurency(BondForeign);
            setBondForeignList(BondForeignList);
            recreateModelSrnDetailPop();
        } else if (BondForeign.getSerialNo() == null && BondForeign.getBuyerFullName() != null && BondForeign.getPrincipalamount() != null && fmsLuCurrency.getCurrencyId() != null) {
            BondForeignList = foreignBeanLocal.findByNameAmountAndCurency(BondForeign);
            setBondForeignList(BondForeignList);
            recreateModelSrnDetailPop();
        } else if (BondForeign.getSerialNo() == null && BondForeign.getBuyerFullName() == null && BondForeign.getPrincipalamount() != null && fmsLuCurrency.getCurrencyId() != null) {
            BondForeignList = foreignBeanLocal.findByAmountAndCurency(BondForeign);
            setBondForeignList(BondForeignList);
            recreateModelSrnDetailPop();
        } else if (BondForeign.getSerialNo() == null && BondForeign.getBuyerFullName() == null && BondForeign.getPrincipalamount() == null && fmsLuCurrency.getCurrencyId() != null) {
            BondForeignList = foreignBeanLocal.findByBondCurrency(BondForeign);
            setBondForeignList(BondForeignList);
            recreateModelSrnDetailPop();
        } else if (BondForeign.getSerialNo() == null && BondForeign.getBuyerFullName() == null && BondForeign.getPrincipalamount() != null && fmsLuCurrency.getCurrencyId() == null) {
            BondForeignList = foreignBeanLocal.findByBondAmount(BondForeign);
            setBondForeignList(BondForeignList);
            recreateModelSrnDetailPop();
        } else if (BondForeign.getSerialNo() == null && BondForeign.getBuyerFullName() != null && BondForeign.getPrincipalamount() == null && fmsLuCurrency.getCurrencyId() != null) {
            BondForeignList = foreignBeanLocal.findByNameAndCurrency(BondForeign);
            setBondForeigns(BondForeignList);
            recreateModelSrnDetailPop();
        } else if (BondForeign.getSerialNo() != null && BondForeign.getBuyerFullName() == null && BondForeign.getPrincipalamount() == null && fmsLuCurrency.getCurrencyId() != null) {
            BondForeignList = foreignBeanLocal.findBySerialAndCurrency(BondForeign);
            setBondForeigns(BondForeignList);
            recreateModelSrnDetailPop();
        } else if (BondForeign.getSerialNo() != null && BondForeign.getBuyerFullName() == null && BondForeign.getPrincipalamount() != null && fmsLuCurrency.getCurrencyId() == null) {
            BondForeignList = foreignBeanLocal.findBySerialAndAmount(BondForeign);
            setBondForeigns(BondForeignList);
            recreateModelSrnDetailPop();
        } else {
            BondForeignList = foreignBeanLocal.searchAll();
            setBondForeigns(BondForeignList);
            recreateModelSrnDetailPop();
        }
        if (BondForeignList != null) {
            for (int i = 0; i < BondForeignList.size(); i++) {
                if (BondForeignList.get(i).getStatus().equalsIgnoreCase(String.valueOf(Constants.PAID))) {
                    BondForeignList.get(i).setChangedStatus("PAID");
                } else if (BondForeignList.get(i).getStatus().equalsIgnoreCase(String.valueOf(Constants.NOT_PAID))) {
                    BondForeignList.get(i).setChangedStatus("NOT PAID");
                } else if (BondForeignList.get(i).getStatus().equalsIgnoreCase(String.valueOf(Constants.TERMINATED))) {
                    BondForeignList.get(i).setChangedStatus("TERMINATED");
                } else if (BondForeignList.get(i).getStatus().equalsIgnoreCase(String.valueOf(Constants.PEND))) {
                    BondForeignList.get(i).setChangedStatus("PEND");
                } else if (BondForeignList.get(i).getStatus().equalsIgnoreCase(String.valueOf(Constants.PAST_DUE))) {
                    BondForeignList.get(i).setChangedStatus("PAST DUE");
                }

            }
        }
        recreateModelSrnDetailPop();
    }

    /*search method from Bond foreign using buyers name and serial number*/
    public void search() {
        String S_B, S_I;
        S_B = BondForeign.getBuyerFullName();
        S_I = BondForeign.getSerialNo();
        if (BondForeign.getSerialNo() != null) {
            BondForeigns = searchBondId(BondForeign.getSerialNo());
            BondForeigns = searchBondId(S_I);
            BondForeignAddDataModel = new ListDataModel(BondForeigns);
        } else if (BondForeign.getBuyerFullName() != null) {
            BondForeigns = searchName(BondForeign.getBuyerFullName());
            BondForeigns = searchName(S_B);
            BondForeignAddDataModel = new ListDataModel(BondForeigns);
        } else {
            BondForeigns = searchAll();
            setBondForeigns(BondForeigns);
            recreateModelSrnDetailPop();
        }

    }

    /*method to calculate interest of foreign Bond by chacking currency and libor rate of the issued Bond*/
    public void calculateInterest(FmsBondForeign BondForeign) {
        Bondcurrency = fmsLuCurrency.getCurrencyId();
        applicationdate = BondForeign.getApplicationDate();

        if (BondForeign.getBondName().equals("Auual")) {
            foreignSchedule.setPrincipal(BondForeign.getPrincipalamount());
            foreignSchedule.setInstallmentDueDate(BondForeign.getDueDate());
            noofInstallment = (2 * (BondForeign.getBondValueYear()));
            foreignSchedule.setNoOfInstallmen(noofInstallment);

            if (Bondcurrency != null) {
                BondLibor = liborBean.searchdays(applicationdate, Bondcurrency);
                if (BondLibor != null && BondForeign.getInterestBearing() != 0.00) {
                    liborrate = BondLibor.getLiborRate();
                    interestRate = BondForeign.getInterestBearing() + liborrate;
                    long diff = toEndDate.getTime() - fromStartDate.getTime();
                    numberOfDays = (int) (diff / 24 * 60 * 60 * 1000);//end date-start date (in integer/number)
                    BondInterest = numberOfDays * BondForeign.getBondAmount() * ((interestRate) / 365);
                    foreignSchedule.setInterest(BondInterest);
                } else if (BondLibor == null) {
                    JsfUtil.addErrorMessage("Please Check Application Date on Bond Libor!");
                } else if (BondForeign.getInterestBearing() == 0.00) {
                    BondInterest = 0.00;
                    foreignSchedule.setInterest(BondInterest);
                }

            } else {
                JsfUtil.addErrorMessage("Bond currency is null!");
            }

        }
        BondForeign.getBondName().equals("Semi Auual");

    }

    /*search method using the start and end date given as an input and assigning status using constant value */
    public void searchStartEndDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
        String date1 = formatter.format(fromStartDate);
        String date2 = formatter.format(toEndDate);
        BondRepaymentSchedules = new ArrayList<>();
        BondRepaymentSchedules = foreignBean.getByStartAndEndDate(date1, date2, foreignSchedule);
        if (BondRepaymentSchedules != null) {
            for (int i = 0; i < BondRepaymentSchedules.size(); i++) {
                if (BondRepaymentSchedules.get(i).getStatus().equalsIgnoreCase(String.valueOf(Constants.PAID))) {
                    BondRepaymentSchedules.get(i).setChangedStatus("PAID");
                } else if (BondRepaymentSchedules.get(i).getStatus().equalsIgnoreCase(String.valueOf(Constants.NOT_PAID))) {
                    BondRepaymentSchedules.get(i).setChangedStatus("NOT PAID");
                } else if (BondRepaymentSchedules.get(i).getStatus().equalsIgnoreCase(String.valueOf(Constants.TERMINATED))) {
                    BondRepaymentSchedules.get(i).setChangedStatus("TERMINATED");
                } else if (BondRepaymentSchedules.get(i).getStatus().equalsIgnoreCase(String.valueOf(Constants.PEND))) {
                    BondRepaymentSchedules.get(i).setChangedStatus("PEND");
                } else if (BondRepaymentSchedules.get(i).getStatus().equalsIgnoreCase(String.valueOf(Constants.PAST_DUE))) {
                    BondRepaymentSchedules.get(i).setChangedStatus("PAST DUE");
                }

            }

            repaymentScheduleDatamodel = new ListDataModel(BondRepaymentSchedules);
            repaymentScheduleDatamodel.getRowIndex();
            AmountToBePaid = 0.0;
            for (int i = 0; i < BondRepaymentSchedules.size(); i++) {
                if (BondRepaymentSchedules.get(i).getStatus().equalsIgnoreCase(String.valueOf(Constants.PAID))) {
                    AmountToBePaid = BondRepaymentSchedules.get(i).getInterest() + BondRepaymentSchedules.get(i).getPrincipal();
                    BondRepaymentSchedules.get(i).setPaidAmount(AmountToBePaid);
                    totalAmountDue = (BondRepaymentSchedules.get(i).getInterest() + BondRepaymentSchedules.get(i).getPrincipal()) - AmountToBePaid;
                    BondRepaymentSchedules.get(i).setTotalAmountDue(totalAmountDue);
                } else if (BondRepaymentSchedules.get(i).getStatus().equalsIgnoreCase(String.valueOf(Constants.NOT_PAID))) {
                    AmountToBePaid = 0.0;
                    BondRepaymentSchedules.get(i).setPaidAmount(AmountToBePaid);
                    totalAmountDue = BondRepaymentSchedules.get(i).getInterest() + BondRepaymentSchedules.get(i).getPrincipal();
                    BondRepaymentSchedules.get(i).setTotalAmountDue(totalAmountDue);
                }
            }
            if (BondRepaymentSchedules != null) {
                for (int i = 0; i < BondRepaymentSchedules.size(); i++) {
                    foreignSchedule = new FmsBondForeignSchedule();
                    foreignSchedule = BondRepaymentSchedules.get(i);
                    principal = principal + foreignSchedule.getPrincipal();
                    interest = interest + foreignSchedule.getInterest();
                }
            }
        }
    }
    /*create and search render method*/

    public void createNewAdditionalAmount() {
        disableBtnCreate = false;
        switch (createOrSearchBundle) {
            case "Forecast":
                renderPnlCreateAdditional = true;
                renderPnlManPage = false;
                createOrSearchBundle = "Search";
                setIcone("ui-icon-search");
                break;
            case "Search":
                renderPnlCreateAdditional = false;
                renderPnlManPage = true;
                createOrSearchBundle = "Forecast";
                setIcone("ui-icon-document");
                break;
        }
    }
}
