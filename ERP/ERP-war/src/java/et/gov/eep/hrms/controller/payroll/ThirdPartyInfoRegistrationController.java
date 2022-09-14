/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.controller.payroll;
//<editor-fold defaultstate="collapsed" desc="Imports">

import et.gov.eep.commonApplications.utility.JsfUtil;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.event.ValueChangeEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.event.SelectEvent;
import et.gov.eep.hrms.businessLogic.employee.HrEmployeeBeanLocal;
import et.gov.eep.hrms.businessLogic.payroll.HrPayrollEarningDeductionsBeanLocal;
import et.gov.eep.hrms.businessLogic.payroll.HrPayrollFamiliesInfoBeanLocal;
import et.gov.eep.hrms.businessLogic.payroll.HrPayrollMorgageInfoBeanLocal;
import et.gov.eep.hrms.entity.employee.HrEmployees;
import et.gov.eep.hrms.entity.payroll.HrPayrollEarningDeductions;
import et.gov.eep.hrms.entity.payroll.HrPayrollFamiliesInfo;
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import securityBean.SessionBean;
import webService.AAA;
import webService.EventEntry;
import webService.IAdministration;
//</editor-fold>

/**
 *
 * @author user
 */
@Named(value = "thirdPartyInfoRegistrationController")
@ViewScoped
public class ThirdPartyInfoRegistrationController implements Serializable {

    public ThirdPartyInfoRegistrationController() {
    }
//<editor-fold defaultstate="collapsed" desc="Objects & Variables">

    @Inject
    HrEmployees hrEmployees;
    @Inject
    SessionBean sessionBeanLocal;
    @Inject
    HrPayrollFamiliesInfo hrPayrollFamiliesInfo;
    @Inject
    HrPayrollEarningDeductions hrPayrollEarningDeductions;
    @EJB
    HrPayrollMorgageInfoBeanLocal hrPayrollMorgageInfoFacade;
    @EJB
    HrPayrollFamiliesInfoBeanLocal hrPayrollFamiliesInfoFacade;
    @EJB
    HrPayrollEarningDeductionsBeanLocal hrPayrollEarningDeductionsLocal;
    @EJB
    HrEmployeeBeanLocal hrEmployeeBean;

    private static String code;
    private List<HrPayrollEarningDeductions> listOfEarningDeductions;
    private List<HrPayrollFamiliesInfo> listOfFamilyInfo;
//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="PostConstruct">

    @PostConstruct
    public void _init() {
        listOfFamilyInfo = null;
        listOfFamilyInfo = hrPayrollFamiliesInfoFacade.findAll();
        listOfEarningDeductions = null;
        listOfEarningDeductions = hrPayrollEarningDeductionsLocal.loadOnlyDeductions();
    }
//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Getters & Setters">

    public HrEmployees getHrEmployees() {
        if (hrEmployees == null) {
            hrEmployees = new HrEmployees();
        }
        return hrEmployees;
    }

    public List<HrPayrollEarningDeductions> getListOfEarningDeductions() {
        return listOfEarningDeductions;
    }

    public void setListOfEarningDeductions(List<HrPayrollEarningDeductions> listOfEarningDeductions) {
        this.listOfEarningDeductions = listOfEarningDeductions;
    }

    public HrPayrollEarningDeductions getHrPayrollEarningDeductions() {
        if (hrPayrollEarningDeductions == null) {
            hrPayrollEarningDeductions = new HrPayrollEarningDeductions();
        }

        return hrPayrollEarningDeductions;
    }

    public void setHrPayrollEarningDeductions(HrPayrollEarningDeductions hrPayrollEarningDeductions) {
        this.hrPayrollEarningDeductions = hrPayrollEarningDeductions;
    }

    public List<HrPayrollFamiliesInfo> getListOfFamilyInfo() {
        return listOfFamilyInfo;
    }

    public void setListOfFamilyInfo(List<HrPayrollFamiliesInfo> listOfFamilyInfo) {
        this.listOfFamilyInfo = listOfFamilyInfo;
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
//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Main Methods">

    public void getByFirstName(SelectEvent event) {
        try {
            hrEmployees.setFirstName(event.getObject().toString());
            hrEmployees = hrEmployeeBean.getByFirstName(hrEmployees);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void handleChange(ValueChangeEvent event) {
        code = event.getNewValue().toString();
    }

    public void edit() {
        try {
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public void handleChanges() {

    }

    public ArrayList<HrEmployees> SearchByFname(String hrEmployee) {
        ArrayList<HrEmployees> employees = null;
        hrEmployees.setFirstName(hrEmployee);
        employees = hrEmployeeBean.SearchByFname(hrEmployees);

        return employees;
    }

    public String returnId(String splitedValue) {
        try {
            String id = null;
            String conc[];
            conc = splitedValue.split("-");
            id = conc[0];
            return id;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public void saveFamily() {
        try {
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(sessionBeanLocal.getUserName(), "saveFamily", dataset)) {
                hrPayrollEarningDeductions.setCode(BigDecimal.valueOf(Double.valueOf(code)));
                hrPayrollFamiliesInfo.setEarningDedCode(hrPayrollEarningDeductions);
                hrPayrollFamiliesInfo.setEmpId(hrEmployees);
                hrPayrollFamiliesInfoFacade.edit(hrPayrollFamiliesInfo);
                JsfUtil.addSuccessMessage("Successfully Saved!");
                listOfFamilyInfo = hrPayrollFamiliesInfoFacade.findAll();
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
        }
    }
    //
    //    public void saveCourt() {
    //    }
    //
    //    public void saveMorgage() {
    //    }

    public void editEarningDed() {
        try {
            hrEmployees = hrPayrollFamiliesInfo.getEmpId();
            hrPayrollEarningDeductions = hrPayrollFamiliesInfo.getEarningDedCode();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
//</editor-fold>
}
