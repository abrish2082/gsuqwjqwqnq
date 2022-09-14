/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.controller.allowance;

import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.hrms.businessLogic.allowance.HrAllowanceInJobTitleBeanLocal;
import et.gov.eep.hrms.businessLogic.allowance.HrAllowanceInLevelsBeanLocal;
import et.gov.eep.hrms.businessLogic.allowance.HrAllowanceInLocationsBeanLocal;
import et.gov.eep.hrms.businessLogic.payroll.HrPayrollPeriodsBeanLocal;
import et.gov.eep.hrms.entity.allowance.HrAllowanceInJobTitle;
import et.gov.eep.hrms.entity.allowance.HrAllowanceInLevels;
import et.gov.eep.hrms.entity.allowance.HrAllowanceInLocations;
import et.gov.eep.hrms.entity.payroll.HrPayrollEarningDeductions;
import et.gov.eep.hrms.entity.payroll.HrPayrollMaintainEds;
import et.gov.eep.hrms.entity.payroll.HrPayrollPeriods;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import securityBean.SessionBean;
import webService.AAA;
import webService.EventEntry;
import webService.IAdministration;

/**
 *
 * @author user
 */
@Named(value = "mantainMonthlyAllowanceController")
@ViewScoped
public class MantainMonthlyAllowanceController implements Serializable {

    @EJB
    HrAllowanceInJobTitleBeanLocal hrAllowanceInJobTitleBeanLocal;
    @EJB
    HrAllowanceInLevelsBeanLocal hrAllowanceInLevelsBeanLocal;
    @EJB
    HrAllowanceInLocationsBeanLocal hrAllowanceInLocationsBeanLocal;

    /**
     * Creates a new instance of MantainMonthlyAllowanceController
     */
    public MantainMonthlyAllowanceController() {
    }
    @Inject
    HrAllowanceInLevels hrAllowanceInLevels;

    /**
     *
     * @return
     */
    public HrAllowanceInLevels getHrAllowanceInLevels() {
        if (hrAllowanceInLevels == null) {
            hrAllowanceInLevels = new HrAllowanceInLevels();
        }
        return hrAllowanceInLevels;
    }

    /**
     *
     * @param hrAllowanceInLevels
     */
    public void setHrAllowanceInLevels(HrAllowanceInLevels hrAllowanceInLevels) {
        this.hrAllowanceInLevels = hrAllowanceInLevels;
    }

    private List<HrAllowanceInLevels> listOfAllownceInLevel;

    /**
     *
     * @return
     */
    public List<HrAllowanceInLevels> getListOfAllownceInLevel() {
        return listOfAllownceInLevel;
    }

    /**
     *
     * @param listOfAllownceInLevel
     */
    public void setListOfAllownceInLevel(List<HrAllowanceInLevels> listOfAllownceInLevel) {
        this.listOfAllownceInLevel = listOfAllownceInLevel;
    }

    private List<HrPayrollEarningDeductions> listOfEarningDeductions;

    /**
     *
     * @return
     */
    public List<HrPayrollEarningDeductions> getListOfEarningDeductions() {
        listOfEarningDeductions = hrAllowanceInLevelsBeanLocal.returnEarningDeductionsInJobLevel();
        return listOfEarningDeductions;
    }

    /**
     *
     * @param listOfEarningDeductions
     */
    public void setListOfEarningDeductions(List<HrPayrollEarningDeductions> listOfEarningDeductions) {
        this.listOfEarningDeductions = listOfEarningDeductions;
    }

    private String activePayrollDate;

    /**
     *
     * @return
     */
    public String getActivePayrollDate() {
        return activePayrollDate;
    }

    /**
     *
     * @param activePayrollDate
     */
    public void setActivePayrollDate(String activePayrollDate) {
        this.activePayrollDate = activePayrollDate;
    }
    @EJB
    HrPayrollPeriodsBeanLocal hrPayrollPeriodsLoacal;
    @Inject
    HrPayrollPeriods hrPayrollPeriods;

    SimpleDateFormat format = new SimpleDateFormat("YYYY-MM-dd");

    /**
     *
     */
    @PostConstruct
    public void _init() {
        try {

            if (hrPayrollPeriodsLoacal.activePayrollDate() != null) {
                hrPayrollPeriods = hrPayrollPeriodsLoacal.activePayrollDate();
                activePayrollDate = hrPayrollPeriodsLoacal.activePayrollDate().getPaymentMadeForTheMonthOf();
            } else {
                activePayrollDate = "[No Active Date is Defined]";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //--start of allowance in job title
    @Inject
    HrAllowanceInJobTitle hrAllowanceInJobTitle;

    /**
     *
     * @return
     */
    public HrAllowanceInJobTitle getHrAllowanceInJobTitle() {

        if (hrAllowanceInJobTitle == null) {
            hrAllowanceInJobTitle = new HrAllowanceInJobTitle();
        }
        return hrAllowanceInJobTitle;
    }

    /**
     *
     * @param hrAllowanceInJobTitle
     */
    public void setHrAllowanceInJobTitle(HrAllowanceInJobTitle hrAllowanceInJobTitle) {
        this.hrAllowanceInJobTitle = hrAllowanceInJobTitle;
    }

    List<HrPayrollEarningDeductions> listOfAllowancesInJobTitle;

    /**
     *
     * @return
     */
    public List<HrPayrollEarningDeductions> getListOfAllowancesInJobTitle() {
        listOfAllowancesInJobTitle = hrAllowanceInJobTitleBeanLocal.listOfEarningDedInTitle();
        return listOfAllowancesInJobTitle;
    }

    /**
     *
     * @param listOfAllowancesInJobTitle
     */
    public void setListOfAllowancesInJobTitle(List<HrPayrollEarningDeductions> listOfAllowancesInJobTitle) {
        this.listOfAllowancesInJobTitle = listOfAllowancesInJobTitle;
    }

    //--start of allowance in location
    @Inject
    HrAllowanceInLocations hrAllowanceInLocations;

    /**
     *
     * @return
     */
    public HrAllowanceInLocations getHrAllowanceInLocations() {
        if (hrAllowanceInLocations == null) {
            hrAllowanceInLocations = new HrAllowanceInLocations();
        }
        return hrAllowanceInLocations;
    }

    /**
     *
     * @param hrAllowanceInLocations
     */
    public void setHrAllowanceInLocations(HrAllowanceInLocations hrAllowanceInLocations) {
        this.hrAllowanceInLocations = hrAllowanceInLocations;
    }

    List<HrPayrollEarningDeductions> listOfAllowncesInLocation;

    /**
     *
     * @return
     */
    public List<HrPayrollEarningDeductions> getListOfAllowncesInLocation() {
        listOfAllowncesInLocation = hrAllowanceInLocationsBeanLocal.listOfAllowncesInLocation();
        return listOfAllowncesInLocation;
    }

    /**
     *
     * @param listOfAllowncesInLocation
     */
    public void setListOfAllowncesInLocation(List<HrPayrollEarningDeductions> listOfAllowncesInLocation) {
        this.listOfAllowncesInLocation = listOfAllowncesInLocation;
    }

    /**
     *
     * @param summary
     */
    public void addMessage(String summary) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, null);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
    @Inject
    SessionBean sessionBeanLocal;

    /**
     *
     */
    public void saveAllowanceInJobTitle() {
        try {

            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(sessionBeanLocal.getUserName(), "saveUpdateOvertimeRate", dataset)) {

                if (activePayrollDate.equalsIgnoreCase("[No Active Date is Defined]")) {
                    addMessage("First Define the active payroll Date!");
                } else {

                    hrAllowanceInLevelsBeanLocal.saveAllowanceInJobTitle(activePayrollDate, hrPayrollPeriods, hrPayrollEarningDeductions);
                    addMessage("Sucessfully Saved");
                }
            } else {

                JsfUtil.addFatalMessage("You don't have appropriate permission, please contact the system administrator. ");

                EventEntry eventEntry = new EventEntry();
                eventEntry.setSessionId(sessionBeanLocal.getSessionID());
                eventEntry.setUserId(sessionBeanLocal.getUserId());
                QName qualifiedName = new QName("", "project");
                JAXBElement<String> test = new JAXBElement<String>(qualifiedName, String.class, null, sessionBeanLocal.getUserName());
                eventEntry.setUserLogin(test);
//..... add more information by calling fields defined in the object 
                security.addEventLog(eventEntry, dataset);
            }
//     

        } catch (EJBException ex) {
            ex.printStackTrace();

        }

    }

    /**
     *
     */
    public void saveAllowanceInJobLevel() {
        try {

            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(sessionBeanLocal.getUserName(), "saveUpdateOvertimeRate", dataset)) {

                if (activePayrollDate.equalsIgnoreCase("[No Active Date is Defined]")) {
                    addMessage("First Define the active payroll Date!");
                } else {
                    hrAllowanceInLevelsBeanLocal.saveAllowanceInJobLevel(activePayrollDate, hrPayrollPeriods, hrPayrollEarningDeductions);
                    addMessage("Sucessfully Saved");
                }

            } else {

                JsfUtil.addFatalMessage("You don't have appropriate permission, please contact the system administrator. ");

                EventEntry eventEntry = new EventEntry();
                eventEntry.setSessionId(sessionBeanLocal.getSessionID());
                eventEntry.setUserId(sessionBeanLocal.getUserId());
                QName qualifiedName = new QName("", "project");
                JAXBElement<String> test = new JAXBElement<String>(qualifiedName, String.class, null, sessionBeanLocal.getUserName());
                eventEntry.setUserLogin(test);
//..... add more information by calling fields defined in the object 
                security.addEventLog(eventEntry, dataset);
            }
//     

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    /**
     *
     */
    public void saveAllowanceInLocation() {
        try {

            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(sessionBeanLocal.getUserName(), "saveUpdateOvertimeRate", dataset)) {

                if (activePayrollDate.equalsIgnoreCase("[No Active Date is Defined]")) {
                    addMessage("First Define the active payroll Date!");
                } else {
                    hrAllowanceInLevelsBeanLocal.saveAllowanceInLocation(activePayrollDate, hrPayrollPeriods, hrPayrollEarningDeductions);
                    addMessage("Sucessfully Saved");
                }
            } else {

                JsfUtil.addFatalMessage("You don't have appropriate permission, please contact the system administrator. ");

                EventEntry eventEntry = new EventEntry();
                eventEntry.setSessionId(sessionBeanLocal.getSessionID());
                eventEntry.setUserId(sessionBeanLocal.getUserId());
                QName qualifiedName = new QName("", "project");
                JAXBElement<String> test = new JAXBElement<String>(qualifiedName, String.class, null, sessionBeanLocal.getUserName());
                eventEntry.setUserLogin(test);
//..... add more information by calling fields defined in the object 
                security.addEventLog(eventEntry, dataset);
            }
//     
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    /**
     *
     */
    public void saveAllwoanceInJobTitle() {

    }

    private List<HrAllowanceInLevels> listOfAllownceInJoblevelPaymentTypes;

    /**
     *
     * @return
     */
    public List<HrAllowanceInLevels> getListOfAllownceInJoblevelPaymentTypes() {
        return listOfAllownceInJoblevelPaymentTypes;
    }

    /**
     *
     * @param listOfAllownceInJoblevelPaymentTypes
     */
    public void setListOfAllownceInJoblevelPaymentTypes(List<HrAllowanceInLevels> listOfAllownceInJoblevelPaymentTypes) {
        this.listOfAllownceInJoblevelPaymentTypes = listOfAllownceInJoblevelPaymentTypes;
    }

    @Inject
    HrPayrollEarningDeductions hrPayrollEarningDeductions;

    /**
     *
     * @return
     */
    public HrPayrollEarningDeductions getHrPayrollEarningDeductions() {
        if (hrPayrollEarningDeductions == null) {
            hrPayrollEarningDeductions = new HrPayrollEarningDeductions();
        }
        return hrPayrollEarningDeductions;
    }

    /**
     *
     * @param hrPayrollEarningDeductions
     */
    public void setHrPayrollEarningDeductions(HrPayrollEarningDeductions hrPayrollEarningDeductions) {
        this.hrPayrollEarningDeductions = hrPayrollEarningDeductions;
    }

    private String code;

    /**
     *
     * @return
     */
    public String getCode() {
        return code;
    }

    /**
     *
     * @param code
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     *
     * @param event
     */
    public void handleChange(ValueChangeEvent event) {
        try {
            code = event.getNewValue().toString();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    /**
     *
     */
    public void handleOnAllowanceChange() {

        try {
            hrAllowanceInLevels.setId(jobLevelId);
            hrAllowanceInLevels = hrAllowanceInLevelsBeanLocal.findAllownaceInJobLevel(hrAllowanceInLevels);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    /**
     *
     */
    public void handleOnAllInJtPaymentTypeChange() {

        try {
            hrAllowanceInJobTitle.setId(jobTitleId);
            hrAllowanceInJobTitle = hrAllowanceInJobTitleBeanLocal.findAllInJobTitle(hrAllowanceInJobTitle);

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    private BigDecimal jobLevelId;

    /**
     *
     * @return
     */
    public BigDecimal getJobLevelId() {
        return jobLevelId;
    }

    /**
     *
     * @param jobLevelId
     */
    public void setJobLevelId(BigDecimal jobLevelId) {
        this.jobLevelId = jobLevelId;
    }
    private String jobTitleId;

    /**
     *
     * @return
     */
    public String getJobTitleId() {
        return jobTitleId;
    }

    /**
     *
     * @param jobTitleId
     */
    public void setJobTitleId(String jobTitleId) {
        this.jobTitleId = jobTitleId;
    }

    private List<HrPayrollMaintainEds> listOfEdJobLevel;
    private List<HrPayrollMaintainEds> listOfEdInJobTitle;
    private List<HrPayrollMaintainEds> listOfEdInLocation;

    /**
     *
     * @return
     */
    public List<HrPayrollMaintainEds> getListOfEdInJobTitle() {
        return listOfEdInJobTitle;
    }

    /**
     *
     * @param listOfEdInJobTitle
     */
    public void setListOfEdInJobTitle(List<HrPayrollMaintainEds> listOfEdInJobTitle) {
        this.listOfEdInJobTitle = listOfEdInJobTitle;
    }

    /**
     *
     * @return
     */
    public List<HrPayrollMaintainEds> getListOfEdInLocation() {
        return listOfEdInLocation;
    }

    /**
     *
     * @param listOfEdInLocation
     */
    public void setListOfEdInLocation(List<HrPayrollMaintainEds> listOfEdInLocation) {
        this.listOfEdInLocation = listOfEdInLocation;
    }

    /**
     *
     * @return
     */
    public List<HrPayrollMaintainEds> getListOfEdJobLevel() {
        return listOfEdJobLevel;
    }

    /**
     *
     * @param listOfEdJobLevel
     */
    public void setListOfEdJobLevel(List<HrPayrollMaintainEds> listOfEdJobLevel) {
        this.listOfEdJobLevel = listOfEdJobLevel;
    }

    /**
     *
     */
    public void handleChange1() {

        try {

            if (activePayrollDate.equalsIgnoreCase("[No Active Date is Defined]")) {
                addMessage("First Define the active payroll Date!");
            } else {
                BigDecimal selectedCode = new BigDecimal(code);
                hrPayrollEarningDeductions.setCode(selectedCode);
            }

        } catch (Exception e) {
        }

    }

    /**
     *
     */
    public void handleChangeInJobtitle() {
        try {
            if (activePayrollDate.equalsIgnoreCase("[No Active Date is Defined]")) {
                addMessage("First Define the active payroll Date!");
            } else {

                BigDecimal selectedCode = new BigDecimal(code);
                hrPayrollEarningDeductions.setCode(selectedCode);
            }
        } catch (Exception e) {
        }

    }

    /**
     *
     */
    public void handleChangeInLocation() {
        try {
            BigDecimal selectedCode = new BigDecimal(code);
            hrPayrollEarningDeductions.setCode(selectedCode);

        } catch (Exception e) {
        }

    }

    private List<HrAllowanceInJobTitle> listOfAllInJtPaymentTypes;

    /**
     *
     * @return
     */
    public List<HrAllowanceInJobTitle> getListOfAllInJtPaymentTypes() {
        return listOfAllInJtPaymentTypes;
    }

    /**
     *
     * @param listOfAllInJtPaymentTypes
     */
    public void setListOfAllInJtPaymentTypes(List<HrAllowanceInJobTitle> listOfAllInJtPaymentTypes) {
        this.listOfAllInJtPaymentTypes = listOfAllInJtPaymentTypes;
    }

    //load the all the paymenent typea if it is payed more than once
    /**
     *
     */
    public void handleChangeAllInJobTitle() {
        try {
            BigDecimal selectedCode = new BigDecimal(code);
            hrPayrollEarningDeductions.setCode(selectedCode);
            listOfAllInJtPaymentTypes = hrAllowanceInJobTitleBeanLocal.findAllJobTypePaymentTypes(hrPayrollEarningDeductions);
        } catch (Exception e) {
        }

    }

    private List<HrAllowanceInLocations> listOfAllInLocationPaymetTypes;

    /**
     *
     * @return
     */
    public List<HrAllowanceInLocations> getListOfAllInLocationPaymetTypes() {
        return listOfAllInLocationPaymetTypes;
    }

    /**
     *
     * @param listOfAllInLocationPaymetTypes
     */
    public void setListOfAllInLocationPaymetTypes(List<HrAllowanceInLocations> listOfAllInLocationPaymetTypes) {
        this.listOfAllInLocationPaymetTypes = listOfAllInLocationPaymetTypes;
    }

    //load the all the paymenent typea if it is payed more than once
    /**
     *
     */
    public void handleChangeAllInLocation() {
        try {

            if (activePayrollDate.equalsIgnoreCase("[No Active Date is Defined]")) {
                addMessage("First Define the active payroll Date!");
            } else {

                BigDecimal selectedCode = new BigDecimal(code);
                hrPayrollEarningDeductions.setCode(selectedCode);
            }
        } catch (Exception e) {
        }
    }

    private String locId;

    /**
     *
     * @return
     */
    public String getLocId() {
        return locId;
    }

    /**
     *
     * @param locId
     */
    public void setLocId(String locId) {
        this.locId = locId;
    }

    /**
     *
     */
    public void handleOnAllInLocPaymentTypeChange() {

        try {
            hrAllowanceInLocations.setId(locId);
            hrAllowanceInLocations = hrAllowanceInLocationsBeanLocal.findAllInLocationById(hrAllowanceInLocations);

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

}
