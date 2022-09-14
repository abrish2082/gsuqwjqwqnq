/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.controller.leave;

import et.gov.eep.commonApplications.utility.GregorianCalendarManipulation;
import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.fcms.businessLogic.budgetYearLookUpBeanLocal;
import et.gov.eep.fcms.entity.FmsLuBudgetYear;
import et.gov.eep.hrms.businessLogic.leave.HrLuHolidayNamesBeanLocal;
import et.gov.eep.hrms.businessLogic.leave.HrleaveHolidaySetUpBeanLocal;
import et.gov.eep.hrms.entity.leave.HrLeaveHolidaySetup;
import et.gov.eep.hrms.entity.leave.HrLuHolidayNames;
import javax.xml.namespace.QName;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.ejb.EJB;
import java.util.List;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.xml.bind.JAXBElement;
import org.primefaces.event.SelectEvent;
import securityBean.SessionBean;
import webService.AAA;
import webService.EventEntry;
import webService.IAdministration;

/**
 *
 * @author user
 */
@Named("holidaySetUp")
@ViewScoped
public class HolidaySetUpController implements Serializable {

    //<editor-fold defaultstate="collapsed" desc="Injected classes">
    @EJB
    budgetYearLookUpBeanLocal fmsLuBudgetBeanLocal;
    @EJB
    HrLuHolidayNamesBeanLocal hrLuHolidayNamesBeanLocal;
    @EJB
    HrleaveHolidaySetUpBeanLocal hrleaveHolidaySetUpBeanLocal;
    @Inject
    HrLeaveHolidaySetup hrLeaveHolidaySetup;
    @Inject
    FmsLuBudgetYear budgetYear;
    @Inject
    SessionBean sessionBean;
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="object and variable declaration">
    List<FmsLuBudgetYear> fmsLuBudgetYear;
    List<HrLuHolidayNames> holidayNames;
    List<HrLeaveHolidaySetup> hrLeaveHolidaySetupsList;
    private HrLeaveHolidaySetup selectedRow;

    SimpleDateFormat dateformatter = new SimpleDateFormat("yyyy-MM-dd");
    private String saveOrUpdateBtn = "Save";
    int update = 0;
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Getter and setter">
    public String getSaveOrUpdateBtn() {
        return saveOrUpdateBtn;
    }

    public void setSaveOrUpdateBtn(String saveOrUpdateBtn) {
        this.saveOrUpdateBtn = saveOrUpdateBtn;
    }

    public HrLeaveHolidaySetup getSelectedRow() {
        return selectedRow;
    }

    public void setSelectedRow(HrLeaveHolidaySetup selectedRow) {
        this.selectedRow = selectedRow;
    }

    public List<HrLeaveHolidaySetup> getHrLeaveHolidaySetupsList() {
        return hrLeaveHolidaySetupsList;
    }

    public void setHrLeaveHolidaySetupsList(List<HrLeaveHolidaySetup> hrLeaveHolidaySetupsList) {
        this.hrLeaveHolidaySetupsList = hrLeaveHolidaySetupsList;
    }

    public List<HrLuHolidayNames> getHolidayNames() {
        return holidayNames;
    }

    public void setHolidayNames(List<HrLuHolidayNames> holidayNames) {
        this.holidayNames = holidayNames;
    }

    public HrLeaveHolidaySetup getHrLeaveHolidaySetup() {
        if (hrLeaveHolidaySetup == null) {
            hrLeaveHolidaySetup = new HrLeaveHolidaySetup();
        }
        return hrLeaveHolidaySetup;
    }

    public void setHrLeaveHolidaySetup(HrLeaveHolidaySetup hrLeaveHolidaySetup) {
        this.hrLeaveHolidaySetup = hrLeaveHolidaySetup;
    }

//</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="populate for update">
    public void populateHoliday(SelectEvent events) {
        hrLeaveHolidaySetup = null;
        hrLeaveHolidaySetup = (HrLeaveHolidaySetup) events.getObject();
        update = 1;
        saveOrUpdateBtn = "Update";

    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Populate budget year">
    public SelectItem[] getBudgetYearList() {
        fmsLuBudgetYear = fmsLuBudgetBeanLocal.getBudgetYear();
        System.out.println("---fmsLuBudgetYear---" + fmsLuBudgetYear);
        return JsfUtil.getSelectItems(fmsLuBudgetYear, true);
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="List Registred Hollidays">
    public SelectItem[] getHolidayList() {
        holidayNames = hrLuHolidayNamesBeanLocal.findAllHolidays();
        return JsfUtil.getSelectItems(holidayNames, true);
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="search by year">
    public void vcl_budgetYear(ValueChangeEvent changeEvent) {
        budgetYear = (FmsLuBudgetYear) changeEvent.getNewValue();
        hrLeaveHolidaySetupsList = hrleaveHolidaySetUpBeanLocal.findByBudgetyYear(budgetYear);

    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="save method">
    public void saveHoliday() {
        try {
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(sessionBean.getUserName(), "saveHoliday", dataset)) {
//                 put ur code here...!

                Calendar holidayCal = Calendar.getInstance();
                String holidString[] = hrLeaveHolidaySetup.getHolidayDateInEc().split("/");
                int holidayindexZero = Integer.parseInt(holidString[0]);
                int holidayindexone = Integer.parseInt(holidString[1]) - 1;
                int holidayindexTwo = Integer.parseInt(holidString[2]);
                holidayCal.set(holidayindexTwo, holidayindexone, holidayindexZero);
                //System.out.println("   holiday Dates  " + dateformatter.format(holidayCal.getTime()));
                String holidayDate = dateformatter.format(holidayCal.getTime());
                String holidayTringDateInGC = GregorianCalendarManipulation.ethiopianToGregorian(holidayDate);
                Date holidayDateInGc = dateformatter.parse(holidayTringDateInGC);
                hrLeaveHolidaySetup.setHolidayDateInGc(holidayDateInGc);
                hrleaveHolidaySetUpBeanLocal.saveOrUpdate(hrLeaveHolidaySetup);
                if (update == 1) {
                    JsfUtil.addSuccessMessage("Updated Successfully");
                } else {
                    JsfUtil.addSuccessMessage("Submitted Successfully");
                }
                clearHoliday();

            } else {
                JsfUtil.addFatalMessage("You don't have appropriate permission, please contact the system administrator.");
                EventEntry eventEntry = new EventEntry();
                eventEntry.setSessionId(sessionBean.getSessionID());
                eventEntry.setUserId(sessionBean.getUserId());
                QName qualifiedName = new QName("", "project");
                JAXBElement<String> test = new JAXBElement<String>(qualifiedName, String.class, null, sessionBean.getUserName());
                eventEntry.setUserLogin(test);
//..... add more information by calling fields defined in the object
                security.addEventLog(eventEntry, dataset);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JsfUtil.addFatalMessage(hrLeaveHolidaySetup.getLuHolidayNameId().getHolidayNames() + " Holiday is registered for this budget year");
        }
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="clear method">
    public void clearHoliday() {
        hrLeaveHolidaySetup = null;
        saveOrUpdateBtn = "Save";
        update = 0;
    }
//</editor-fold> 
}
