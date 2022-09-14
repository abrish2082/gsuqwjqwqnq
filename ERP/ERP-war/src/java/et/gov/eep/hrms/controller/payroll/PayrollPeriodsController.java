/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.controller.payroll;

//<editor-fold defaultstate="collapsed" desc="Imports">
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.commonApplications.utility.date.StringDateManipulation;
import et.gov.eep.hrms.businessLogic.payroll.HrPayrollFamiliesInfoBeanLocal;
import et.gov.eep.hrms.businessLogic.payroll.HrPayrollPeriodsBeanLocal;
import et.gov.eep.hrms.entity.payroll.HrPayrollFamiliesInfo;
import et.gov.eep.hrms.entity.payroll.HrPayrollPeriods;
import securityBean.SessionBean;
import webService.AAA;
import webService.EventEntry;
import webService.IAdministration;
//</editor-fold>

/**
 *
 * @author user
 */
@Named(value = "payrollPeriodsController")
@ViewScoped
public class PayrollPeriodsController implements Serializable {

    /**
     * Creates a new instance of PayrollPeriodsController
     */
    public PayrollPeriodsController() {
    }
//<editor-fold defaultstate="collapsed" desc="Objects & Variables">
    @Inject
    HrPayrollFamiliesInfo hrPayrollFamiliesInfo;
    @Inject
    SessionBean sessionBeanLocal;
    @Inject
    HrPayrollPeriods hrPayrollPeriods;

    @EJB
    HrPayrollFamiliesInfoBeanLocal hrPayrollFamiliesInfoFacade;
    @EJB
    HrPayrollPeriodsBeanLocal hrPayrollPeriodsLocal;

    private String saveOrUpdate = "Save";
    private String dateForUpdate;
    private String maxDate;
    private boolean disabled = false;
    private boolean makeActive;

    private List<HrPayrollPeriods> listOfPayrollPeriods;
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    private List<HrPayrollFamiliesInfo> listOfFamilyInfo;
//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Getters & Setters">

    public String getMaxDate() {
        return maxDate;
    }

    public void setMaxDate(String maxDate) {
        this.maxDate = maxDate;
    }

    public boolean isMakeActive() {
        return makeActive;
    }

    public void setMakeActive(boolean makeActive) {
        this.makeActive = makeActive;
    }

    public HrPayrollFamiliesInfo getHrPayrollFamiliesInfo() {
        if (hrPayrollFamiliesInfo == null) {
            hrPayrollFamiliesInfo = new HrPayrollFamiliesInfo();
        }
        return hrPayrollFamiliesInfo;
    }

    public void setHrPayrollFamiliesInfo(HrPayrollFamiliesInfo hrPayrollFamiliesInfo) {
        this.hrPayrollFamiliesInfo = hrPayrollFamiliesInfo;
    }

    public List<HrPayrollFamiliesInfo> getListOfFamilyInfo() {
        listOfFamilyInfo = null;
        listOfFamilyInfo = hrPayrollFamiliesInfoFacade.findAll();
        return listOfFamilyInfo;
    }

    public void setListOfFamilyInfo(List<HrPayrollFamiliesInfo> listOfFamilyInfo) {
        this.listOfFamilyInfo = listOfFamilyInfo;
    }

    public String getDateForUpdate() {
        return dateForUpdate;
    }

    public void setDateForUpdate(String dateForUpdate) {
        this.dateForUpdate = dateForUpdate;
    }

    public HrPayrollPeriods getHrPayrollPeriods() {
        if (hrPayrollPeriods == null) {
            hrPayrollPeriods = new HrPayrollPeriods();
        }
        return hrPayrollPeriods;
    }

    public void setHrPayrollPeriods(HrPayrollPeriods hrPayrollPeriods) {
        this.hrPayrollPeriods = hrPayrollPeriods;
    }

    public List<HrPayrollPeriods> getListOfPayrollPeriods() {
        listOfPayrollPeriods = hrPayrollPeriodsLocal.loadPayrollDates();
        return listOfPayrollPeriods;
    }

    public void setListOfPayrollPeriods(List<HrPayrollPeriods> listOfPayrollPeriods) {
        this.listOfPayrollPeriods = listOfPayrollPeriods;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public String getSaveOrUpdate() {
        return saveOrUpdate;
    }

    public void setSaveOrUpdate(String saveOrUpdate) {
        this.saveOrUpdate = saveOrUpdate;
    }
//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Main Methods">

    public void edit() {
        saveOrUpdate = "Update";
    }

    public void newPayrollPeriod() {
        saveOrUpdate = "Save";
        makeActive = false;
        hrPayrollPeriods = null;
        setDisabled(false);
    }

    public void activeNext() {
        //finalized paryroll date +0
    }

    public String returnYearAndMonth(String dateToSplit) {
        try {
            String dates[] = dateToSplit.split("/");
            return dates[1] + "/" + dates[2];

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }

    }

    public String returnYear(String dateToSplit) {
        try {
            String dates[] = dateToSplit.split("/");
            return dates[2];

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }

    }

    public String returnMonth(String dateToSplit) {
        try {
            String dates[] = dateToSplit.split("/");
            return dates[1];

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }

    }

    public double calctTotalDate(String date) {
        return (Double.valueOf(returnYear(date)) * 360d) + (Double.valueOf(returnMonth(date)) * 30d);
    }

    public void saveOrUpdatePayrollPeriod() {
        try {
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(sessionBeanLocal.getUserName(), "SAVEPAYROLLPERIOD", dataset)) {
                String _nextMonth;
                if (makeActive) {
                    hrPayrollPeriods.setStatus("1");
                } else {
                    hrPayrollPeriods.setStatus("0");
                }
                //dateForUpdate  this date will be displayed when the edit is clicked
                //allready previously maintened date
                if (saveOrUpdate.equalsIgnoreCase("Update")) {
                    if (returnYearAndMonth(dateForUpdate).equalsIgnoreCase(returnYearAndMonth(hrPayrollPeriods.getPaymentMadeForTheMonthOf()))) {
                        if (makeActive) {
                            hrPayrollPeriodsLocal.activateOnlyOneMonth();
                        }
                        hrPayrollPeriodsLocal.edit(hrPayrollPeriods);
                        JsfUtil.addSuccessMessage("Successfully Updated!");
                    } else {
                        if (hrPayrollPeriodsLocal.checkDateRepetition(returnYearAndMonth(hrPayrollPeriods.getPaymentMadeForTheMonthOf())) != null) {
                            JsfUtil.addFatalMessage("You just add a repeated data!");
                        }
                    }
                } else {
                    if (hrPayrollPeriodsLocal.loadMaxDateForPayroll() == null) {
                        //if no date is saved
                        if (Integer.valueOf(returnMonth(hrPayrollPeriods.getPaymentMadeForTheMonthOf())) == 13) {
                            //if month is puagume then force the user to enter the next month
                            _nextMonth = StringDateManipulation.nextMonthInECPuagume(hrPayrollPeriods.getPaymentMadeForTheMonthOf(), "/");
                            JsfUtil.addFatalMessage("Make sure that the next month should be: " + returnYearAndMonth(_nextMonth));
                        } else {
                            if (saveOrUpdate.equalsIgnoreCase("Save")) {
                                if (makeActive) {
                                    hrPayrollPeriodsLocal.activateOnlyOneMonth();
                                }
                                if (hrPayrollPeriodsLocal.checkDateRepetition(returnYearAndMonth(hrPayrollPeriods.getPaymentMadeForTheMonthOf())) != null) {
                                    JsfUtil.addFatalMessage("You just add a repeated data!");
                                } else {
                                    hrPayrollPeriodsLocal.create(hrPayrollPeriods);
                                    JsfUtil.addSuccessMessage("Successfully Saved!");
                                }
                            } else if (saveOrUpdate.equalsIgnoreCase("Update")) {
                                if (returnYearAndMonth(dateForUpdate).equalsIgnoreCase(returnYearAndMonth(hrPayrollPeriods.getPaymentMadeForTheMonthOf()))) {
                                    if (makeActive) {
                                        hrPayrollPeriodsLocal.activateOnlyOneMonth();
                                    }
                                    hrPayrollPeriodsLocal.edit(hrPayrollPeriods);
                                    JsfUtil.addSuccessMessage("Successfully Updated!");
                                } else {
                                    if (hrPayrollPeriodsLocal.checkDateRepetition(returnYearAndMonth(hrPayrollPeriods.getPaymentMadeForTheMonthOf())) != null) {
                                        JsfUtil.addFatalMessage("You just add a repeated data!");
                                    }
                                }
                            }
                        }
                    } else {
                        maxDate = hrPayrollPeriodsLocal.loadMaxDateForPayroll().getPaymentMadeForTheMonthOf();//assign the maximum date through searching from the db
                        _nextMonth = StringDateManipulation.nextMonthInECPuagume(maxDate, "/");//calculating and assigning the next month
                        if (Integer.valueOf(returnMonth(hrPayrollPeriods.getPaymentMadeForTheMonthOf())) == 13) {//check if the selectd date is puagume
                            _nextMonth = StringDateManipulation.nextMonthInECPuagume(hrPayrollPeriods.getPaymentMadeForTheMonthOf(), "/"); //if the selected month is puagume force to select next month i.e meskerem
                            JsfUtil.addFatalMessage("Make sure that the next month should be: " + returnYearAndMonth(_nextMonth));
                        } else if (calctTotalDate(_nextMonth) != calctTotalDate(hrPayrollPeriods.getPaymentMadeForTheMonthOf())) {//if the user doesent select month next to puagume it forces to select ht enext month i.e puagume
                            JsfUtil.addFatalMessage("Make sure that the next month should be: " + returnYearAndMonth(_nextMonth));
                        } else {//if the user select
                            //this code will be implemented if and only the next month n the selected months are the same
                            if (saveOrUpdate.equalsIgnoreCase("Save")) {
                                if (hrPayrollPeriodsLocal.checkDateRepetition(returnYearAndMonth(hrPayrollPeriods.getPaymentMadeForTheMonthOf())) != null) {
                                    if (makeActive) {
                                        hrPayrollPeriodsLocal.activateOnlyOneMonth();
                                    }
                                    JsfUtil.addFatalMessage("You just add a repeated data!");
                                } else {
                                    hrPayrollPeriodsLocal.create(hrPayrollPeriods);
                                    JsfUtil.addSuccessMessage("Successfully Saved!");
                                }
                            } else if (saveOrUpdate.equalsIgnoreCase("Update")) {
                                if (returnYearAndMonth(dateForUpdate).equalsIgnoreCase(returnYearAndMonth(hrPayrollPeriods.getPaymentMadeForTheMonthOf()))) {
                                    if (makeActive) {
                                        hrPayrollPeriodsLocal.activateOnlyOneMonth();
                                    }
                                    hrPayrollPeriodsLocal.edit(hrPayrollPeriods);
                                    JsfUtil.addSuccessMessage("Successfully Updated!");
                                } else {
                                    if (hrPayrollPeriodsLocal.checkDateRepetition(returnYearAndMonth(hrPayrollPeriods.getPaymentMadeForTheMonthOf())) != null) {
                                        JsfUtil.addFatalMessage("You just add a repeated data!");
                                    }
                                }
                            }
                        }
                    }
                }
                hrPayrollPeriods = null;
            } else {
                JsfUtil.addFatalMessage("You don't have appropriate permission, please contact the system administrator. ");
                EventEntry eventEntry = new EventEntry();
                eventEntry.setSessionId(sessionBeanLocal.getSessionID());
                eventEntry.setUserId(sessionBeanLocal.getUserId());
                QName qualifiedName = new QName("", "project");
                JAXBElement<String> test = new JAXBElement<String>(qualifiedName, String.class, null, sessionBeanLocal.getUserName());
                eventEntry.setUserLogin(test);
                security.addEventLog(eventEntry, dataset);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JsfUtil.addFatalMessage("Error");
        }
    }

    public void editPayrollPeriod() {
        try {
            dateForUpdate = hrPayrollPeriods.getPaymentMadeForTheMonthOf();
            saveOrUpdate = "Update";
            if (hrPayrollPeriodsLocal.findPayrollPeriod(hrPayrollPeriods).getStatus().equalsIgnoreCase("[Active]")) {
                makeActive = true;
            } else {
                makeActive = false;
            }
            if (hrPayrollPeriods.getStatus().equalsIgnoreCase("[Closed]")) {
                setDisabled(true);
            } else {
                setDisabled(false);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
//</editor-fold>
}
