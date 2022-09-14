/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.controller.adminstrator;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import org.primefaces.event.TabChangeEvent;
import securityBean.SessionBean;
import webService.AAA;
import webService.EventEntry;
import webService.IAdministration;
import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.fcms.businessLogic.FmsLuBudgetYearBeanLocal;
import et.gov.eep.fcms.businessLogic.Voucher.FmsVoucherBeanLocal;
import et.gov.eep.fcms.businessLogic.admin.FmsAccountingPeriodBeanLocal;
import et.gov.eep.fcms.controller.Constants;
import et.gov.eep.fcms.entity.FmsLuBudgetYear;
import et.gov.eep.fcms.entity.admin.FmsAccountingPeriod;

/**
 *
 * @author Abiy
 */
@Named

@ViewScoped
public class AccountClosingPeriodManagedBean implements Serializable {

    //<editor-fold defaultstate="collapsed" desc="@EJB and @Inject">
    //@EJB
    @EJB
    private FmsAccountingPeriodBeanLocal fmsAccountingPeriodBeanLocal;
    @EJB
    private FmsLuBudgetYearBeanLocal fmsLuBudgetYearBeanLocal;
    @EJB
    private FmsVoucherBeanLocal fmsVoucherBeanLocal;
    //@Inject
    @Inject
    SessionBean SessionBean;
    @Inject
    FmsAccountingPeriod fmsAccountingPeriodEnty;
    @Inject
    FmsLuBudgetYear fmsLuBudgetYearEnty;
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Object and variable declaration">
    private String budgetyearInformation = "";
    private String saveorUpdateBundle = Constants.getComponentBundle("StartFiscalYear");
    private String saveCloseBundle = Constants.getComponentBundle("ClosePeriod");
    private String Enddate = "";
    private String startingdate = "";
    String selectedmonth = "";
    String selectedmonthMsg = "";
    String NextYearInformation = "";
    String activeTab = "Fiscal Year";
    int updteStatus = 0;
    int MajorCatagorylistId = 0;
    int interCatListSizes = 0;
    int IntermidiateCatStatus = 0;
    int catagorynameStatus = 0;
    int LuBudgetYearListSize = 0;
    List<FmsLuBudgetYear> budgetyrList = new ArrayList<>();
    List<FmsLuBudgetYear> listLuBudgetYearList = null;
    FmsAccountingPeriod currPeriod = new FmsAccountingPeriod();
    FmsLuBudgetYear nextPeriod = new FmsLuBudgetYear();
//</editor-fold>

    public AccountClosingPeriodManagedBean() {

    }

    //<editor-fold defaultstate="collapsed" desc="Getter and Setter Methods">
    /**
     * @return the budgetyearInformation
     */
    public String getBudgetyearInformation() {
        return budgetyearInformation;
    }

    /*
     * @param budgetyearInformation the budgetyearInformation to set
     */
    public void setBudgetyearInformation(String budgetyearInformation) {
        this.budgetyearInformation = budgetyearInformation;
    }

    /*
     * @return
     */
    public FmsAccountingPeriodBeanLocal getFmsAccountingPeriodBeanLocal() {
        return fmsAccountingPeriodBeanLocal;
    }

    /*
     * @param fmsAccountingPeriodBeanLocal
     */
    public void setFmsAccountingPeriodBeanLocal(FmsAccountingPeriodBeanLocal fmsAccountingPeriodBeanLocal) {
        this.fmsAccountingPeriodBeanLocal = fmsAccountingPeriodBeanLocal;
    }

    /*
     * @return
     */
    public FmsLuBudgetYearBeanLocal getFmsLuBudgetYearBeanLocal() {
        return fmsLuBudgetYearBeanLocal;
    }

    /*
     * @param fmsLuBudgetYearBeanLocal
     */
    public void setFmsLuBudgetYearBeanLocal(FmsLuBudgetYearBeanLocal fmsLuBudgetYearBeanLocal) {
        this.fmsLuBudgetYearBeanLocal = fmsLuBudgetYearBeanLocal;
    }

    /*
     * @return
     */
    public FmsLuBudgetYear getFmsLuBudgetYearEnty() {
        if (fmsLuBudgetYearEnty == null) {
            fmsLuBudgetYearEnty = new FmsLuBudgetYear();
        }
        return fmsLuBudgetYearEnty;
    }

    /*
     * @param fmsLuBudgetYearEnty
     */
    public void setFmsLuBudgetYearEnty(FmsLuBudgetYear fmsLuBudgetYearEnty) {
        this.fmsLuBudgetYearEnty = fmsLuBudgetYearEnty;
    }

    /*
     * @return
     */
    public FmsAccountingPeriod getFmsAccountingPeriodEnty() {
        if (fmsAccountingPeriodEnty == null) {
            fmsAccountingPeriodEnty = new FmsAccountingPeriod();
        }
        return fmsAccountingPeriodEnty;
    }

    /*
     * @param fmsAccountingPeriodEnty
     */
    public void setFmsAccountingPeriodEnty(FmsAccountingPeriod fmsAccountingPeriodEnty) {
        this.fmsAccountingPeriodEnty = fmsAccountingPeriodEnty;
    }

    /*
     * @return
     */
    public String getNextYearInformation() {
        return NextYearInformation;
    }

    /*
     * @param NextYearInformation
     */
    public void setNextYearInformation(String NextYearInformation) {
        this.NextYearInformation = NextYearInformation;
    }

    /*
     * @return
     */
    public String getSaveCloseBundle() {
        return saveCloseBundle;
    }

    /*
     * @param saveCloseBundle
     */
    public void setSaveCloseBundle(String saveCloseBundle) {
        this.saveCloseBundle = saveCloseBundle;
    }

    /*
     * @return
     */
    public String getSaveorUpdateBundle() {
        return saveorUpdateBundle;
    }

    /*
     * @param saveorUpdateBundle
     */
    public void setSaveorUpdateBundle(String saveorUpdateBundle) {
        this.saveorUpdateBundle = saveorUpdateBundle;
    }

    /*
     * @return
     */
    public String getEnddate() {
        return Enddate;
    }

    /*
     * @param Enddate
     */
    public void setEnddate(String Enddate) {
        this.Enddate = Enddate;
    }

    /*
     * @return
     */
    public String getStartingdate() {
        return startingdate;
    }

    /*
     * @param startingdate
     */
    public void setStartingdate(String startingdate) {
        this.startingdate = startingdate;
    }

    /*
     * @return
     */
    public String getSelectedmonth() {
        return selectedmonth;
    }

    /*
     * @param selectedmonth
     */
    public void setSelectedmonth(String selectedmonth) {
        this.selectedmonth = selectedmonth;
    }

    /*
     * @return
     */
    public String getSelectedmonthMsg() {
        return selectedmonthMsg;
    }

    /*
     * @param selectedmonthMsg
     */
    public void setSelectedmonthMsg(String selectedmonthMsg) {
        this.selectedmonthMsg = selectedmonthMsg;
    }

    public List<FmsLuBudgetYear> getBudgetyrList() {
        if (budgetyrList == null) {
            budgetyrList = new ArrayList<>();
        }
        return budgetyrList;
    }

    public void setBudgetyrList(List<FmsLuBudgetYear> budgetyrList) {
        this.budgetyrList = budgetyrList;
    }

    public FmsAccountingPeriod getCurrPeriod() {
        if (currPeriod == null) {
            currPeriod = new FmsAccountingPeriod();
        }
        return currPeriod;
    }

    public void setCurrPeriod(FmsAccountingPeriod currPeriod) {
        this.currPeriod = currPeriod;
    }

    public FmsLuBudgetYear getNextPeriod() {
        if (nextPeriod == null) {
            nextPeriod = new FmsLuBudgetYear();
        }
        return nextPeriod;
    }

    public void setNextPeriod(FmsLuBudgetYear nextPeriod) {
        this.nextPeriod = nextPeriod;
    }

    //</editor-fold>    
    
    //PostConstruct for getting extra invoice
    @PostConstruct
    public void getExtraInvoice() {
        FmsAccountingPeriod AccountingPeriod = fmsAccountingPeriodBeanLocal.getCurretActivePeriod();
        if (AccountingPeriod != null) {
            NextYearInformation = "The Current Active Fiscal Year to [" + AccountingPeriod.getStartDate() + "] To [" + AccountingPeriod.getEndDate() + "]";
        } else {
            NextYearInformation = "No Current Active Fiscal Year,Open new Fiscal Year";
        }
    }
    /*
     * @tab change event
     */

    public void onTabChange(TabChangeEvent event) {
        FacesMessage msg = new FacesMessage("Tab Changed", "Active Tab: " + event.getTab().getTitle());
        FmsAccountingPeriod AccountingPeriod = fmsAccountingPeriodBeanLocal.getCurretActivePeriod();
        if (AccountingPeriod != null) {
            String month = AccountingPeriod.getActivePeriod();
            selectedmonth = getMonthString(month);
        } else {
            selectedmonth = "Open new Fiscal Year";
        }

        activeTab = event.getTab().getTitle();
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    //select month string
    private String getMonthString(String month) {
        selectedmonth = "";
        if (month.equals("1")) {
            selectedmonth = "July";
        } else if (month.equals("2")) {
            selectedmonth = "August";
        } else if (month.equals("3")) {
            selectedmonth = "September";
        } else if (month.equals("4")) {
            selectedmonth = "October";
        } else if (month.equals("5")) {
            selectedmonth = "November";
        } else if (month.equals("6")) {
            selectedmonth = "December";
        } else if (month.equals("7")) {
            selectedmonth = "January";
        } else if (month.equals("8")) {
            selectedmonth = "February";
        } else if (month.equals("9")) {
            selectedmonth = "March";
        } else if (month.equals("10")) {
            selectedmonth = "April";
        } else if (month.equals("11")) {
            selectedmonth = "May";
        } else if (month.equals("12")) {
            selectedmonth = "June";
        }
        return selectedmonth;
    }

    // save - Update
    public void btnSaveClos_Action() {

        try {
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(SessionBean.getUserName(), "btnSaveClos_Action", dataset)) {
                if (activeTab.equalsIgnoreCase("Fiscal Year")) {
                    Integer x = 1;
                    String startPeriod = "1";
                    int xd = 2;
                    fmsAccountingPeriodEnty.setStartDate(startingdate);
                    fmsAccountingPeriodEnty.setEndDate(Enddate);
                    fmsAccountingPeriodEnty.setStatus(x);
                    fmsAccountingPeriodEnty.setActivePeriod(startPeriod);
                    fmsAccountingPeriodEnty.setLuBudgetYearId(fmsLuBudgetYearEnty);
                    fmsAccountingPeriodBeanLocal.create(fmsAccountingPeriodEnty);
                    JsfUtil.addSuccessMessage("Fiscal Year Started");
                    getExtraInvoice();
                    getLuBudgetYearList();
                    clearPage();
                }
                if (activeTab.equalsIgnoreCase("Account Period")) {
                    fmsAccountingPeriodEnty = fmsAccountingPeriodBeanLocal.getCurretActivePeriod();
                    int nextPeriod;
                    if (fmsAccountingPeriodEnty != null) {
                        String month = fmsAccountingPeriodEnty.getActivePeriod();
                        if (month.equals("12")) {
                            Integer x = 0;
                            selectedmonthMsg = "Period for the Current Active Fiscal Year is Ended, Please Enter New Fiscal Year";
                            fmsAccountingPeriodEnty.setStatus(x);
                            fmsAccountingPeriodBeanLocal.edit(fmsAccountingPeriodEnty);
                            JsfUtil.addSuccessMessage(selectedmonthMsg);
                        } else {
                            nextPeriod = Integer.parseInt(month) + 1;
                            selectedmonth = getMonthString(month);
                            fmsAccountingPeriodEnty.setActivePeriod(Integer.toString(nextPeriod));
                            fmsAccountingPeriodBeanLocal.edit(fmsAccountingPeriodEnty);
                            fmsVoucherBeanLocal.searchVoucherPostStatus();
                            JsfUtil.addSuccessMessage("Account Period " + selectedmonth + " closed");
                        }
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
        } catch (Exception ex) {
        }
    }

    //save action
    public String btnSave_Action() {
        Integer x = 1;
        int xd = 2;
        fmsAccountingPeriodEnty.setStartDate(startingdate);
        fmsAccountingPeriodEnty.setEndDate(Enddate);
        fmsAccountingPeriodEnty.setStatus(x);
        fmsAccountingPeriodEnty.setActivePeriod("active");
        fmsAccountingPeriodEnty.setLuBudgetYearId(fmsLuBudgetYearEnty);
        fmsAccountingPeriodBeanLocal.create(fmsAccountingPeriodEnty);
        JsfUtil.addSuccessMessage("Fiscal Year Started");
        return clearPage();
    }

    // search
    public List<String> ChartOfAccountSearchlist(Integer status) {
        List<String> listAcctPeriod = null;
        fmsAccountingPeriodEnty.setStatus(status);
        fmsAccountingPeriodBeanLocal.searchAccountingPeriodStatus(fmsAccountingPeriodEnty);
        return listAcctPeriod;
    }

    //select items
    public SelectItem[] getLuBudgetYearList() {
        listLuBudgetYearList = fmsLuBudgetYearBeanLocal.getLuBudgetYear();
        LuBudgetYearListSize = listLuBudgetYearList.size();

        currPeriod = fmsAccountingPeriodBeanLocal.getCurretActivePeriod();
        nextPeriod = new FmsLuBudgetYear();
        budgetyrList = new ArrayList<>();
        for (int i = 0; i < listLuBudgetYearList.size(); i++) {
            if (Objects.equals(currPeriod.getLuBudgetYearId().getLuBudgetYearId(), listLuBudgetYearList.get(i).getLuBudgetYearId())) {
                nextPeriod = listLuBudgetYearList.get(i + 1);
            }
        }
        budgetyrList.add(nextPeriod);
        return JsfUtil.getSelectItems(budgetyrList, true);
    }

    //value change event
    public void handleSelectMajorName(ValueChangeEvent event) {
        if (null != event.getNewValue()) {
            String selectedData;
            String splitedStartDate[] = null, splitedBudgetYear[], selectedStartingYear, selectedEndingYear, splitedEndDate[] = null, nextStartDate, nextEndDate, prevStartDate, prevEndDate;
            MajorCatagorylistId = 0;
            String majorNames = event.getNewValue().toString();
            for (int i = 0; i < LuBudgetYearListSize; i++) {
                if (listLuBudgetYearList.get(i).getBudgetYear().equalsIgnoreCase(majorNames)) {
                    MajorCatagorylistId = listLuBudgetYearList.get(i).getLuBudgetYearId();
                    fmsLuBudgetYearEnty = listLuBudgetYearList.get(i);
                    fmsAccountingPeriodEnty.setLuBudgetYearId(fmsLuBudgetYearEnty);
                    fmsLuBudgetYearEnty.setLuBudgetYearId(MajorCatagorylistId);
                    i = LuBudgetYearListSize;
                }
            }
            selectedData = listLuBudgetYearList.get(MajorCatagorylistId - 1).getBudgetYear();
            splitedBudgetYear = selectedData.toString().split("-");
            selectedStartingYear = splitedBudgetYear[0] + "-07-01";
            selectedEndingYear = splitedBudgetYear[1] + "-06-30";
            Enddate = selectedEndingYear;
            startingdate = selectedStartingYear;

            DateFormat dateFormat = new SimpleDateFormat("yyyy");
            //get current date time with Date()
            Date date = new Date();
            budgetyearInformation = dateFormat.format(date);
            String infoNextyearStarting, infoNextyearEnd;
            int castNextYear = 0;
            castNextYear = Integer.parseInt(budgetyearInformation);
            castNextYear += 1;
            infoNextyearStarting = budgetyearInformation + "-07-01";
            infoNextyearEnd = Integer.toString(castNextYear) + "-06-30";
            budgetyearInformation = "The Next year must be equal to [" + infoNextyearStarting + "] To [" + infoNextyearEnd + "]";
            catagorynameStatus = 1;

        }

    }

    //clear

    public String clearPage() {
        fmsAccountingPeriodEnty = new FmsAccountingPeriod();
        fmsLuBudgetYearEnty = new FmsLuBudgetYear();
        Enddate = "";
        startingdate = "";
        updteStatus = 0;
        catagorynameStatus = 0;
        IntermidiateCatStatus = 0;
        return null;
    }

}
