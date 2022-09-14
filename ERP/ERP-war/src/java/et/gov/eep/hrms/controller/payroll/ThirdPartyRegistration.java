 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.controller.payroll;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import org.primefaces.event.SelectEvent;
import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.hrms.businessLogic.employee.HrEmployeeBeanLocal;
import et.gov.eep.hrms.businessLogic.payroll.HrPayrollCourtCaseInfoBeanLocal;
import et.gov.eep.hrms.businessLogic.payroll.HrPayrollEarningDeductionsBeanLocal;
import et.gov.eep.hrms.businessLogic.payroll.HrPayrollFamiliesInfoBeanLocal;
import et.gov.eep.hrms.businessLogic.payroll.HrPayrollMorgageInfoBeanLocal;
import et.gov.eep.hrms.businessLogic.payroll.HrPayrollPlPgBeanLocal;
import et.gov.eep.hrms.entity.employee.HrEmployees;
import et.gov.eep.hrms.entity.lookup.HrLuBankBranches;
import et.gov.eep.hrms.entity.lookup.HrLuBanks;
import et.gov.eep.hrms.entity.payroll.HrPayrollCourtCaseInfo;
import et.gov.eep.hrms.entity.payroll.HrPayrollEarningDeductions;
import et.gov.eep.hrms.entity.payroll.HrPayrollFamiliesInfo;
import et.gov.eep.hrms.entity.payroll.HrPayrollMorgageInfo;
import et.gov.eep.hrms.entity.payroll.HrPayrollPlPg;
import securityBean.SessionBean;
import webService.AAA;
import webService.EventEntry;
import webService.IAdministration;

/**
 *
 * @author user
 */
@Named(value = "thirdPartyRegistration")
@ViewScoped
public class ThirdPartyRegistration implements Serializable {

    /**
     * Creates a new instance of ThirdPartyRegistration
     */
    public ThirdPartyRegistration() {
    }
//<editor-fold defaultstate="collapsed" desc="Objects & Variables">
    @Inject
    HrPayrollEarningDeductions hrPayrollEarningDeductions;
    @Inject
    SessionBean sessionBeanLocal;
    @Inject
    HrLuBanks hrLuBanks;
    @Inject
    HrPayrollFamiliesInfo hrPayrollFamiliesInfo;
    @Inject
    HrPayrollPlPg hrPayrollPlPg;
    @Inject
    HrEmployees hrEmployees;
    @Inject
    HrPayrollMorgageInfo hrPayrollMorgageInfo;
    @Inject
    HrPayrollCourtCaseInfo hrPayrollCourtCaseInfo;
    @Inject
    HrLuBankBranches hrLuBankBranches;
    @EJB
    HrPayrollPlPgBeanLocal hrPayrollPlPgBeanLocal;
    @EJB
    HrPayrollEarningDeductionsBeanLocal hrPayrollEarningDeductionsLocal;
    @EJB
    HrPayrollFamiliesInfoBeanLocal hrPayrollFamiliesInfoFacade;
    @EJB
    HrEmployeeBeanLocal hrEmployeeBean;
    @EJB
    HrPayrollCourtCaseInfoBeanLocal hrPayrollCourtCaseInfoLocal;
    @EJB
    HrPayrollMorgageInfoBeanLocal hrPayrollMorgageInfoFacade;

    private String saveOrUpdateFamily = "Save";
    private String saveOrUpdateMorgate = "Save";
    private String saveOrUpdateCourtCase = "Save";
    private String code;
    private String familycode;
    private String coutCaseCode;
    private String morgage;
    private boolean isActiveFamily = false;
    private boolean isActiveMorgage = false;
    private boolean isActiveCourt = false;
    private List<HrPayrollPlPg> listHrPayrollPlPg;
    private List<HrLuBankBranches> bkBranches;
    private List<HrLuBanks> bkBanks;
    private List<HrPayrollFamiliesInfo> listOfFamilyInfo;
    private List<HrPayrollEarningDeductions> listOfEarningDeductions;
    private List<HrPayrollCourtCaseInfo> listOfCourtCaseInfo;
//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Getters & Setters">

    public String getFamilycode() {
        return familycode;
    }

    public void setFamilycode(String familycode) {
        this.familycode = familycode;
    }

    public String getCoutCaseCode() {
        return coutCaseCode;
    }

    public void setCoutCaseCode(String coutCaseCode) {
        this.coutCaseCode = coutCaseCode;
    }

    public String getMorgage() {
        return morgage;
    }

    public void setMorgage(String morgage) {
        this.morgage = morgage;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void handleValueChangeFamily(ValueChangeEvent event) {
        try {
            familycode = event.getNewValue().toString();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void handleValueChangeCourt(ValueChangeEvent event) {
        try {
            coutCaseCode = event.getNewValue().toString();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void handleValueChangeMorgage(ValueChangeEvent event) {
        try {
            morgage = event.getNewValue().toString();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public List<HrPayrollPlPg> getListHrPayrollPlPg() {
        listHrPayrollPlPg = hrPayrollPlPgBeanLocal.findAllPLPG();
        return listHrPayrollPlPg;
    }

    public void setListHrPayrollPlPg(List<HrPayrollPlPg> listHrPayrollPlPg) {
        this.listHrPayrollPlPg = listHrPayrollPlPg;
    }

    public void handleChanges() {
    }

    public boolean isIsActiveFamily() {
        return isActiveFamily;
    }

    public void setIsActiveFamily(boolean isActiveFamily) {
        this.isActiveFamily = isActiveFamily;
    }

    public boolean isIsActiveMorgage() {
        return isActiveMorgage;
    }

    public void setIsActiveMorgage(boolean isActiveMorgage) {
        this.isActiveMorgage = isActiveMorgage;
    }

    public boolean isIsActiveCourt() {
        return isActiveCourt;
    }

    public void setIsActiveCourt(boolean isActiveCourt) {
        this.isActiveCourt = isActiveCourt;
    }

    public List<HrPayrollEarningDeductions> getListOfEarningDeductions() {
        listOfEarningDeductions = hrPayrollEarningDeductionsLocal.loadOnlyDeductions();
        return listOfEarningDeductions;
    }

    public void setListOfEarningDeductions(List<HrPayrollEarningDeductions> listOfEarningDeductions) {
        this.listOfEarningDeductions = listOfEarningDeductions;
    }

    public HrLuBanks getHrLuBanks() {
        if (hrLuBanks == null) {
            hrLuBanks = new HrLuBanks();
        }
        return hrLuBanks;
    }

    public void setHrLuBanks(HrLuBanks hrLuBanks) {
        this.hrLuBanks = hrLuBanks;
    }

    public List<HrLuBankBranches> getBkBranches() {
        bkBranches = hrEmployeeBean.searchBankBranchInfo(hrLuBanks);
        return bkBranches;
    }

    public void setBkBranches(List<HrLuBankBranches> bkBranches) {
        this.bkBranches = bkBranches;
    }

    public List<HrLuBanks> getBkBanks() {
        bkBanks = hrEmployeeBean.findAllBanks();
        return bkBanks;
    }

    public void setBkBanks(List<HrLuBanks> bkBanks) {
        this.bkBanks = bkBanks;
    }

    public HrLuBankBranches getHrLuBankBranches() {
        if (hrLuBankBranches == null) {
            hrLuBankBranches = new HrLuBankBranches();
        }
        return hrLuBankBranches;
    }

    public void setHrLuBankBranches(HrLuBankBranches hrLuBankBranches) {
        this.hrLuBankBranches = hrLuBankBranches;
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
        return listOfFamilyInfo;
    }

    public void setListOfFamilyInfo(List<HrPayrollFamiliesInfo> listOfFamilyInfo) {
        this.listOfFamilyInfo = listOfFamilyInfo;
    }

    public HrPayrollPlPg getHrPayrollPlPg() {
        if (hrPayrollPlPg == null) {
            hrPayrollPlPg = new HrPayrollPlPg();
        }
        return hrPayrollPlPg;
    }

    public void setHrPayrollPlPg(HrPayrollPlPg hrPayrollPlPg) {
        this.hrPayrollPlPg = hrPayrollPlPg;
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

    public HrPayrollMorgageInfo getHrPayrollMorgageInfo() {
        if (hrPayrollMorgageInfo == null) {
            hrPayrollMorgageInfo = new HrPayrollMorgageInfo();
        }
        return hrPayrollMorgageInfo;
    }

    public void setHrPayrollMorgageInfo(HrPayrollMorgageInfo hrPayrollMorgageInfo) {
        this.hrPayrollMorgageInfo = hrPayrollMorgageInfo;
    }

    private List<HrPayrollMorgageInfo> listOFMorgage;

    public List<HrPayrollMorgageInfo> getListOFMorgage() {
        return listOFMorgage;
    }

    public void setListOFMorgage(List<HrPayrollMorgageInfo> listOFMorgage) {
        this.listOFMorgage = listOFMorgage;
    }

    public HrPayrollCourtCaseInfo getHrPayrollCourtCaseInfo() {
        if (hrPayrollCourtCaseInfo == null) {
            hrPayrollCourtCaseInfo = new HrPayrollCourtCaseInfo();
        }
        return hrPayrollCourtCaseInfo;
    }

    public void setHrPayrollCourtCaseInfo(HrPayrollCourtCaseInfo hrPayrollCourtCaseInfo) {
        this.hrPayrollCourtCaseInfo = hrPayrollCourtCaseInfo;
    }

    public List<HrPayrollCourtCaseInfo> getListOfCourtCaseInfo() {
        return listOfCourtCaseInfo;
    }

    public void setListOfCourtCaseInfo(List<HrPayrollCourtCaseInfo> listOfCourtCaseInfo) {
        this.listOfCourtCaseInfo = listOfCourtCaseInfo;
    }

    public String getSaveOrUpdateFamily() {
        return saveOrUpdateFamily;
    }

    public void setSaveOrUpdateFamily(String saveOrUpdateFamily) {
        this.saveOrUpdateFamily = saveOrUpdateFamily;
    }

    public String getSaveOrUpdateMorgate() {
        return saveOrUpdateMorgate;
    }

    public void setSaveOrUpdateMorgate(String saveOrUpdateMorgate) {
        this.saveOrUpdateMorgate = saveOrUpdateMorgate;
    }

    public String getSaveOrUpdateCourtCase() {
        return saveOrUpdateCourtCase;
    }

    public void setSaveOrUpdateCourtCase(String saveOrUpdateCourtCase) {
        this.saveOrUpdateCourtCase = saveOrUpdateCourtCase;
    }
//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Main Methods">

    public HrEmployees getHrEmployees() {
        if (hrEmployees == null) {
            hrEmployees = new HrEmployees();
        }
        return hrEmployees;
    }

    public void checkFamily() {
        try {
            if (isActiveFamily) {
                hrPayrollFamiliesInfo.setStatus("Active");
            } else {
                hrPayrollFamiliesInfo.setStatus("Inactive");
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    public void checkMorgage() {
        try {
            if (isActiveMorgage) {
                hrPayrollMorgageInfo.setStatus("Active");
            } else {
                hrPayrollMorgageInfo.setStatus("Inactive");
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    public void checkCourtCase() {
        try {
            if (isActiveCourt) {
                hrPayrollCourtCaseInfo.setStatus("Active");
            } else {
                hrPayrollCourtCaseInfo.setStatus("Inactive");
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    public void BankChanged(ValueChangeEvent event) {
        if (!event.getNewValue().toString().isEmpty()) {
            hrLuBanks.setBankName(event.getNewValue().toString());
            hrLuBanks = hrEmployeeBean.findBanks(hrLuBanks);
            hrEmployees.setBankName(hrLuBanks);
        }
    }

    public void BankBranchChanged(ValueChangeEvent event) {
        if (!event.getNewValue().toString().isEmpty()) {
            hrLuBankBranches.setBranchName(event.getNewValue().toString());
            hrLuBankBranches = hrEmployeeBean.findBankBranchs(hrLuBankBranches);
            hrEmployees.setBankBranch(hrLuBankBranches);
        }
    }

    public ArrayList<HrEmployees> SearchByFname(String hrEmployee) {
        ArrayList<HrEmployees> employees = null;
        hrEmployees.setEmpId(hrEmployee);
        employees = hrEmployeeBean.SearchByEmpId(hrEmployees);
        return employees;
    }

    public void getByFirstNameFamily(SelectEvent event) {
        try {
            hrEmployees.setFirstName(event.getObject().toString());
            hrEmployees = hrEmployeeBean.getByEmpId(hrEmployees);
            searchEmpFamilyDetail();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getByFirstNameMorgage(SelectEvent event) {
        try {
            hrEmployees.setFirstName(event.getObject().toString());
            hrEmployees = hrEmployeeBean.getByEmpId(hrEmployees);
            searchEmpMortageDetail();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getByFirstNameCourtCase(SelectEvent event) {
        try {
            hrEmployees.setFirstName(event.getObject().toString());
            hrEmployees = hrEmployeeBean.getByEmpId(hrEmployees);
            searchEmpCourtCaseDetail();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteFamily() {
        try {
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(sessionBeanLocal.getUserName(), "deleteFamily", dataset)) {
                hrPayrollFamiliesInfoFacade.remove(hrPayrollFamiliesInfo);
                JsfUtil.addSuccessMessage("Successfully Deleted!");
                this.resetFamilyInfo();
                listOfFamilyInfo = new ArrayList<HrPayrollFamiliesInfo>();
                listOfFamilyInfo = hrPayrollFamiliesInfoFacade.findAll();
                clearFamily();
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
        } catch (Exception e) {
            e.printStackTrace();
            JsfUtil.addErrorMessage("Not Deleted!");
        }
    }

    public void deleteMortage() {
        try {
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(sessionBeanLocal.getUserName(), "deleteMortage", dataset)) {
                hrPayrollMorgageInfoFacade.remove(hrPayrollMorgageInfo);
                JsfUtil.addSuccessMessage("Successfully Deleted!");
//                this.deleteMortage();
                listOFMorgage = new ArrayList<HrPayrollMorgageInfo>();
                listOFMorgage = hrPayrollMorgageInfoFacade.findAll();
                clearMorgage();
            } else {
                EventEntry eventEntry = new EventEntry();
                eventEntry.setSessionId(sessionBeanLocal.getSessionID());
                eventEntry.setUserId(sessionBeanLocal.getUserId());
                QName qualifiedName = new QName("", "project");
                JAXBElement<String> test = new JAXBElement<String>(qualifiedName, String.class, null, sessionBeanLocal.getUserName());
                eventEntry.setUserLogin(test);
                security.addEventLog(eventEntry, dataset);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JsfUtil.addErrorMessage("Not Deleted!");
        }
    }

    public void deleteCourtCaseInfo() {
        try {

            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(sessionBeanLocal.getUserName(), "deleteCourtCaseInfo", dataset)) {
                hrPayrollCourtCaseInfoLocal.remove(hrPayrollCourtCaseInfo);
                JsfUtil.addSuccessMessage("Successfully Deleted!");
                this.resetCourtCaseInfo();
                listOfCourtCaseInfo = new ArrayList<HrPayrollCourtCaseInfo>();
                listOfCourtCaseInfo = hrPayrollCourtCaseInfoLocal.findAll();
                clearCourtCase();
            } else {
                EventEntry eventEntry = new EventEntry();
                eventEntry.setSessionId(sessionBeanLocal.getSessionID());
                eventEntry.setUserId(sessionBeanLocal.getUserId());
                QName qualifiedName = new QName("", "project");
                JAXBElement<String> test = new JAXBElement<String>(qualifiedName, String.class, null, sessionBeanLocal.getUserName());
                eventEntry.setUserLogin(test);
                security.addEventLog(eventEntry, dataset);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JsfUtil.addErrorMessage("Not Deleted!");
        }
    }

    public void buttonAction(ActionEvent actionEvent) {
        JsfUtil.addSuccessMessage("Welcome to Primefaces!!");
        listOfFamilyInfo = new ArrayList<HrPayrollFamiliesInfo>();
        listOfFamilyInfo = hrPayrollFamiliesInfoFacade.findAll();
    }

    public void viewAllFamilyDetail() {
        try {
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(sessionBeanLocal.getUserName(), "saveUpdateOvertimeRate", dataset)) {
                this.resetFamilyInfo();
                listOfFamilyInfo = new ArrayList<HrPayrollFamiliesInfo>();
                listOfFamilyInfo = hrPayrollFamiliesInfoFacade.findAll();
            } else {
                EventEntry eventEntry = new EventEntry();
                eventEntry.setSessionId(sessionBeanLocal.getSessionID());
                eventEntry.setUserId(sessionBeanLocal.getUserId());
                QName qualifiedName = new QName("", "project");
                JAXBElement<String> test = new JAXBElement<String>(qualifiedName, String.class, null, sessionBeanLocal.getUserName());
                eventEntry.setUserLogin(test);
                security.addEventLog(eventEntry, dataset);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void viewAllCourtCaseInfo() {
        try {
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(sessionBeanLocal.getUserName(), "saveUpdateOvertimeRate", dataset)) {
                this.resetCourtCaseInfo();
                listOfCourtCaseInfo = new ArrayList<HrPayrollCourtCaseInfo>();
                listOfCourtCaseInfo = hrPayrollCourtCaseInfoLocal.findAll();

            } else {
                EventEntry eventEntry = new EventEntry();
                eventEntry.setSessionId(sessionBeanLocal.getSessionID());
                eventEntry.setUserId(sessionBeanLocal.getUserId());
                QName qualifiedName = new QName("", "project");
                JAXBElement<String> test = new JAXBElement<String>(qualifiedName, String.class, null, sessionBeanLocal.getUserName());
                eventEntry.setUserLogin(test);
                security.addEventLog(eventEntry, dataset);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void viewAllMortageDetail() {
        try {
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(sessionBeanLocal.getUserName(), "saveUpdateOvertimeRate", dataset)) {
                this.resetMortage();
                listOFMorgage = new ArrayList<HrPayrollMorgageInfo>();
                listOFMorgage = hrPayrollMorgageInfoFacade.findAll();
            } else {
                EventEntry eventEntry = new EventEntry();
                eventEntry.setSessionId(sessionBeanLocal.getSessionID());
                eventEntry.setUserId(sessionBeanLocal.getUserId());
                QName qualifiedName = new QName("", "project");
                JAXBElement<String> test = new JAXBElement<String>(qualifiedName, String.class, null, sessionBeanLocal.getUserName());
                eventEntry.setUserLogin(test);
                security.addEventLog(eventEntry, dataset);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void resetMortage() {
        try {
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(sessionBeanLocal.getUserName(), "resetMortage", dataset)) {
                hrPayrollMorgageInfo = null;
                hrPayrollMorgageInfo = new HrPayrollMorgageInfo();
                listOFMorgage = new ArrayList<HrPayrollMorgageInfo>();
                morgage = null;
                hrEmployees = null;
                hrEmployees = new HrEmployees();
                saveOrUpdateMorgate = "Save";
                isActiveMorgage = false;
                hrLuBanks = null;
                hrLuBanks = new HrLuBanks();
                hrLuBankBranches = null;
                hrLuBankBranches = new HrLuBankBranches();
            } else {
                EventEntry eventEntry = new EventEntry();
                eventEntry.setSessionId(sessionBeanLocal.getSessionID());
                eventEntry.setUserId(sessionBeanLocal.getUserId());
                QName qualifiedName = new QName("", "project");
                JAXBElement<String> test = new JAXBElement<String>(qualifiedName, String.class, null, sessionBeanLocal.getUserName());
                eventEntry.setUserLogin(test);
                security.addEventLog(eventEntry, dataset);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void resetFamilyInfo() {
        try {
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(sessionBeanLocal.getUserName(), "saveUpdateOvertimeRate", dataset)) {
                hrPayrollFamiliesInfo = null;
                hrPayrollFamiliesInfo = new HrPayrollFamiliesInfo();
                listOfFamilyInfo = new ArrayList<HrPayrollFamiliesInfo>();
                familycode = null;
                hrEmployees = null;
                hrEmployees = new HrEmployees();
                saveOrUpdateFamily = "Save";
                isActiveFamily = false;
            } else {
                EventEntry eventEntry = new EventEntry();
                eventEntry.setSessionId(sessionBeanLocal.getSessionID());
                eventEntry.setUserId(sessionBeanLocal.getUserId());
                QName qualifiedName = new QName("", "project");
                JAXBElement<String> test = new JAXBElement<String>(qualifiedName, String.class, null, sessionBeanLocal.getUserName());
                eventEntry.setUserLogin(test);
                security.addEventLog(eventEntry, dataset);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void resetCourtCaseInfo() {
        try {
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(sessionBeanLocal.getUserName(), "saveUpdateOvertimeRate", dataset)) {
                hrPayrollCourtCaseInfo = null;
                hrPayrollCourtCaseInfo = new HrPayrollCourtCaseInfo();
                listOfCourtCaseInfo = new ArrayList<HrPayrollCourtCaseInfo>();
                coutCaseCode = null;
                hrEmployees = null;
                hrEmployees = new HrEmployees();
                saveOrUpdateCourtCase = "Save";
                isActiveCourt = false;
            } else {
                EventEntry eventEntry = new EventEntry();
                eventEntry.setSessionId(sessionBeanLocal.getSessionID());
                eventEntry.setUserId(sessionBeanLocal.getUserId());
                QName qualifiedName = new QName("", "project");
                JAXBElement<String> test = new JAXBElement<String>(qualifiedName, String.class, null, sessionBeanLocal.getUserName());
                eventEntry.setUserLogin(test);
                security.addEventLog(eventEntry, dataset);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void searchEmpFamilyDetail() {
        try {
            listOfFamilyInfo = hrPayrollFamiliesInfoFacade.findByEmpId(hrEmployees);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void searchEmpMortageDetail() {
        try {

            if (hrPayrollMorgageInfoFacade.findByEmpId(hrEmployees) != null) {
                listOFMorgage = hrPayrollMorgageInfoFacade.findByEmpId(hrEmployees);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void searchEmpCourtCaseDetail() {
        try {
            listOfCourtCaseInfo = hrPayrollCourtCaseInfoLocal.findByEmpId(hrEmployees);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void loadFam() {
        try {
            listOfFamilyInfo = hrPayrollFamiliesInfoFacade.findAll();
//            hrPayrollEarningDeductions=null;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void clearFamily() {
        familycode = null;
        hrPayrollFamiliesInfo = new HrPayrollFamiliesInfo();

        hrEmployees = null;
        hrPayrollFamiliesInfo = null;
    }

    public void saveFamily() {
        try {
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(sessionBeanLocal.getUserName(), "saveFamily", dataset)) {
                hrPayrollEarningDeductions.setCode(BigDecimal.valueOf(Double.valueOf(familycode)));
                hrPayrollFamiliesInfoFacade.updateFamilyStatus(hrEmployees, hrPayrollEarningDeductions);
                if (isActiveFamily) {
                    hrPayrollFamiliesInfo.setStatus("Active");
                } else {
                    hrPayrollFamiliesInfo.setStatus("Inactive");
                }
                hrPayrollFamiliesInfo.setEarningDedCode(hrPayrollEarningDeductions);
                hrPayrollFamiliesInfo.setEmpId(hrEmployees);
                hrPayrollFamiliesInfo.setPlPg(hrPayrollPlPg);
                hrPayrollFamiliesInfoFacade.edit(hrPayrollFamiliesInfo);
                if (saveOrUpdateFamily.equalsIgnoreCase("Save")) {
                    JsfUtil.addSuccessMessage("Successfully Saved!");
                } else {
                    JsfUtil.addSuccessMessage("Successfully Updated!");
                }
                searchEmpFamilyDetail();
                clearFamily();
            } else {
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
            JsfUtil.addErrorMessage("Error!");
        }
    }

    public void clearMorgage() {
        morgage = null;
        hrPayrollMorgageInfo = new HrPayrollMorgageInfo();
        hrEmployees = null;
        hrLuBanks = null;
        hrLuBankBranches = null;
    }

    public void saveMorgate() {
        try {
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(sessionBeanLocal.getUserName(), "saveMorgate", dataset)) {
                hrPayrollEarningDeductions.setCode(BigDecimal.valueOf(Double.valueOf(morgage)));
                hrPayrollMorgageInfoFacade.updateMortageStatus(hrEmployees, hrPayrollEarningDeductions);
                if (isActiveMorgage) {
                    hrPayrollMorgageInfo.setStatus("Active");
                } else {
                    hrPayrollMorgageInfo.setStatus("Inactive");
                }
                hrPayrollMorgageInfo.setEarningDedCode(hrPayrollEarningDeductions);
                hrPayrollMorgageInfo.setBankName(hrLuBanks);
                hrPayrollMorgageInfo.setBankBranch(hrLuBankBranches);
                hrPayrollMorgageInfo.setEmpId(hrEmployees);
                hrPayrollMorgageInfo.setPlPg(hrPayrollPlPg);

                hrPayrollMorgageInfoFacade.edit(hrPayrollMorgageInfo);
                if (saveOrUpdateMorgate.equalsIgnoreCase("Save")) {
                    JsfUtil.addSuccessMessage("Successfully Saved!");
                } else {
                    JsfUtil.addSuccessMessage("Successfully Updated!");
                }
                searchEmpMortageDetail();
                clearMorgage();
            } else {
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
            JsfUtil.addErrorMessage("Error!");
        }
    }

    public void clearCourtCase() {
        coutCaseCode = null;
        hrPayrollCourtCaseInfo = new HrPayrollCourtCaseInfo();
        hrEmployees = null;
        hrPayrollCourtCaseInfo = null;
    }

    public void saveCourtCase() {
        try {
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(sessionBeanLocal.getUserName(), "saveCourtCase", dataset)) {
                hrPayrollEarningDeductions.setCode(BigDecimal.valueOf(Double.valueOf(coutCaseCode)));
                hrPayrollCourtCaseInfoLocal.updateFamilyStatus(hrEmployees, hrPayrollEarningDeductions);
                if (isActiveCourt) {
                    hrPayrollCourtCaseInfo.setStatus("Active");
                } else {
                    hrPayrollCourtCaseInfo.setStatus("Inactive");
                }
                hrPayrollCourtCaseInfo.setEarningDedCode(hrPayrollEarningDeductions);
                hrPayrollCourtCaseInfo.setEmpId(hrEmployees);
                hrPayrollCourtCaseInfo.setPlPg(hrPayrollPlPg);
                hrPayrollCourtCaseInfoLocal.edit(hrPayrollCourtCaseInfo);
                if (saveOrUpdateCourtCase.equalsIgnoreCase("Save")) {
                    JsfUtil.addSuccessMessage("Successfully Saved!");
                } else {
                    JsfUtil.addSuccessMessage("Successfully Updated!");
                }
                searchEmpCourtCaseDetail();
                clearCourtCase();
            } else {
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
            JsfUtil.addErrorMessage("Error!");
        }
    }

    public void editCourtCase() {
        try {
            saveOrUpdateCourtCase = "Update";
            hrEmployees = hrPayrollCourtCaseInfo.getEmpId();
            coutCaseCode = String.valueOf(hrPayrollCourtCaseInfo.getEarningDedCode().getCode());
            if (hrPayrollCourtCaseInfo.getStatus().equalsIgnoreCase("Active")) {
                isActiveCourt = true;
            } else {
                isActiveCourt = false;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void editMorgage() {
        try {
            saveOrUpdateMorgate = "Update";
            hrEmployees = hrPayrollMorgageInfo.getEmpId();
            morgage = String.valueOf(hrPayrollMorgageInfo.getEarningDedCode().getCode());
            hrLuBanks = hrPayrollMorgageInfo.getBankName();
            hrLuBankBranches = hrPayrollMorgageInfo.getBankBranch();
            if (hrPayrollMorgageInfo.getStatus().equalsIgnoreCase("Active")) {
                isActiveMorgage = true;
            } else {
                isActiveMorgage = false;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void editFaminly() {
        try {
            saveOrUpdateFamily = "Update";
            hrEmployees = hrPayrollFamiliesInfo.getEmpId();

            familycode = String.valueOf(hrPayrollFamiliesInfo.getEarningDedCode().getCode());
            if (hrPayrollFamiliesInfo.getStatus().equalsIgnoreCase("Active")) {
                isActiveFamily = true;
            } else {
                isActiveFamily = false;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
//</editor-fold>

}
