/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.controller.allowance;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.validation.ConstraintViolationException;
import org.primefaces.event.SelectEvent;
import securityBean.SessionBean;
import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.hrms.businessLogic.allowance.HrAllowanceInJobTitleBeanLocal;
import et.gov.eep.hrms.businessLogic.allowance.HrAllowanceInLevelsBeanLocal;
import et.gov.eep.hrms.businessLogic.allowance.HrAllowanceInLocationsBeanLocal;
import et.gov.eep.hrms.businessLogic.allowance.HrAllowanceTypesBeanLocal;
import et.gov.eep.hrms.businessLogic.allowance.HrPayrollFiltEmForAllBeanLocal;
import et.gov.eep.hrms.businessLogic.employee.HrEmployeeBeanLocal;
import et.gov.eep.hrms.businessLogic.payroll.HrPayrollEarningDeductionsBeanLocal;
import et.gov.eep.hrms.businessLogic.payroll.HrPayrollPlPgBeanLocal;
import et.gov.eep.hrms.entity.lookup.HrLuGrades;
import et.gov.eep.hrms.entity.lookup.HrLuJobLevels;
import et.gov.eep.hrms.mapper.address.HrAddressesFacade;
import et.gov.eep.hrms.entity.address.HrAddresses;
import et.gov.eep.hrms.entity.allowance.HrAllowanceInLevels;
import et.gov.eep.hrms.entity.allowance.HrAllowanceInLocations;
import et.gov.eep.hrms.entity.allowance.HrAllowanceInJobTitle;
import et.gov.eep.hrms.entity.allowance.HrAllowanceTypes;
import et.gov.eep.hrms.entity.allowance.HrPayrollFiltEmForAll;
import et.gov.eep.hrms.entity.employee.HrEmployees;
import et.gov.eep.hrms.entity.organization.HrJobTypes;
import et.gov.eep.hrms.entity.payroll.HrPayrollEarningDeductions;
import et.gov.eep.hrms.entity.payroll.HrPayrollPlPg;
import et.gov.eep.hrms.mapper.lookup.HrLuGradesFacade;
import et.gov.eep.hrms.mapper.lookup.HrLuJobLevelsFacade;
import et.gov.eep.hrms.mapper.organization.HrJobTypesFacade;
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import webService.AAA;
import webService.EventEntry;
import webService.IAdministration;

/**
 *
 * @author user
 */
@Named(value = "allownaceAmountRegistrationCont")
@ViewScoped
public class AllownaceAmountRegistrationCont implements Serializable {

    // <editor-fold defaultstate="collapsed" desc="Entity, bussiness logic & variable">
    @EJB
    HrPayrollPlPgBeanLocal hrPayrollPlPgBeanLocal;
    @EJB
    HrAddressesFacade hrAddressesFacade1;
    @EJB
    HrAllowanceInJobTitleBeanLocal hrAllowanceInJobTitleBeanLocal;
    @EJB
    HrAllowanceInLevelsBeanLocal hrAllowanceInLevelsBeanLocal;
    @EJB
    HrAllowanceInLocationsBeanLocal hrAllowanceInLocationsBeanLocal;
    @EJB
    HrAllowanceTypesBeanLocal hrAllowanceTypesBeanLocal;
    @EJB
    HrPayrollFiltEmForAllBeanLocal hrPayrollFiltEmForAllBeanLocal;
    @EJB
    HrPayrollEarningDeductionsBeanLocal hrPayrollEarningDeductionsBeanLocal;
    @EJB
    HrEmployeeBeanLocal hrEmployeeBean;
    @EJB
    HrLuJobLevelsFacade hrLuJobLevelsFacadeLocal;
    @EJB
    HrLuGradesFacade hrLuGradesFacadeLocal;
    @EJB
    HrJobTypesFacade hrJobTypesFacade;

    @Inject
    HrAllowanceTypes hrAllowanceTypes;
    @Inject
    HrAllowanceInLevels hrAllowanceInLevels;
    @Inject
    HrLuJobLevels hrLuJobLevels;
    @Inject
    HrPayrollPlPg hrPayrollPlPg;
    @Inject
    HrPayrollEarningDeductions hrPayrollEarningDeductions;
    @Inject
    HrPayrollEarningDeductions hrPayrollEdForAllowance;
    @Inject
    HrPayrollFiltEmForAll hrPayrollFiltEmForAll;
    @Inject
    HrAllowanceInLocations hrAllowanceInLocations;
    @Inject
    HrEmployees hrEmployees;
    @Inject
    HrLuGrades hrLuGrades;
    @Inject
    HrJobTypes jobTypes;
    @Inject
    HrAllowanceInJobTitle hrAllowanceInJobTitle;
    @Inject
    HrAddresses hrAddresses;
    @Inject
    HrAddresses hrAddressJobLevel;
    @Inject
    HrAddresses hrAddressAllowanceLocation;
    @Inject
    HrAddresses hrAddressJobTitle;
    @Inject
    HrAddresses hrAddressLocation;
    @Inject
    SessionBean sessionBeanLocal;

    private boolean isEarning;
    private boolean isDeduction;
    private boolean listOfAllowances = false;
    private boolean remark = false;
    private boolean employeesId = false;
    private boolean status = false;

    private String saveOrUpdate = "Save";
    private String saveOrUpdateFilterEmp = "Save";
    private String lableValue = "Amount In Birr:";
    private String edForAllInLocation;
    private String allAddressUnitAsOne;
    private String amountLable = "Amount/Birr";
    private String jobLevelDescription;
    private String jlPaymentIn;
    private String levelCode;
    private String code;
    private String plpgId;
    private String earningDedCode;
    private String jobTitleId;
    private BigDecimal jobLevelId;

    private List<HrJobTypes> listOfJobTypes;
    private List<HrLuJobLevels> listOfJobLevels;
    private List<HrPayrollPlPg> listHrPayrollPlPg;
    private List<HrAddresses> listOfAddresses;
    private List<HrAllowanceInLocations> listOfAllowanceInLocation;
    private List<HrPayrollEarningDeductions> listOfEarningDeductions;
    private List<HrPayrollEarningDeductions> listOfEarningDeductionsForJTitle;
    private List<HrPayrollEarningDeductions> listOfEarningDeductionsForJLocation;
    private List<HrAllowanceTypes> listOfAllowanceTypes;
    private List<HrAllowanceInLevels> listOfAllowanceInJobLevels;
    private List<HrPayrollFiltEmForAll> listOfFilteredEmployees;
    private List<HrAllowanceInLevels> listOfAllownceInJoblevelPaymentTypes;
    private List<HrAllowanceInLocations> listOfAllwowanceInLocation;
//</editor-fold>

    public AllownaceAmountRegistrationCont() {
    }

    // <editor-fold defaultstate="collapsed" desc="Getters and Setters">
    public HrPayrollEarningDeductions getHrPayrollEdForAllowance() {
        return hrPayrollEdForAllowance;
    }

    public void setHrPayrollEdForAllowance(HrPayrollEarningDeductions hrPayrollEdForAllowance) {
        this.hrPayrollEdForAllowance = hrPayrollEdForAllowance;
    }

    public String getSaveOrUpdateFilterEmp() {
        return saveOrUpdateFilterEmp;
    }

    public void setSaveOrUpdateFilterEmp(String saveOrUpdateFilterEmp) {
        this.saveOrUpdateFilterEmp = saveOrUpdateFilterEmp;
    }

    private List<HrPayrollEarningDeductions> listOfEarningDeductionsForFilter;

    public List<HrPayrollEarningDeductions> getListOfEarningDeductionsForFilter() {
        listOfEarningDeductionsForFilter = hrPayrollEarningDeductionsBeanLocal.listOfEarnngForAllEmp();
        return listOfEarningDeductionsForFilter;
    }

    public void setListOfEarningDeductionsForFilter(List<HrPayrollEarningDeductions> listOfEarningDeductionsForFilter) {
        this.listOfEarningDeductionsForFilter = listOfEarningDeductionsForFilter;
    }

    public List<HrPayrollEarningDeductions> getListOfEarningDeductionsForJTitle() {
        listOfEarningDeductionsForJTitle = hrPayrollEarningDeductionsBeanLocal.loadEdForJObTitle();
        return listOfEarningDeductionsForJTitle;
    }

    public void setListOfEarningDeductionsForJTitle(List<HrPayrollEarningDeductions> listOfEarningDeductionsForJTitle) {
        this.listOfEarningDeductionsForJTitle = listOfEarningDeductionsForJTitle;
    }

    public List<HrPayrollEarningDeductions> getListOfEarningDeductionsForJLocation() {
        listOfEarningDeductionsForJLocation = hrPayrollEarningDeductionsBeanLocal.loadEdForLocation();
        return listOfEarningDeductionsForJLocation;
    }

    public void setListOfEarningDeductionsForJLocation(List<HrPayrollEarningDeductions> listOfEarningDeductionsForJLocation) {
        this.listOfEarningDeductionsForJLocation = listOfEarningDeductionsForJLocation;
    }

    private List<HrPayrollEarningDeductions> listOfLocationEarningDeductions;

    public List<HrPayrollEarningDeductions> getListOfLocationEarningDeductions() {
        listOfLocationEarningDeductions = hrPayrollEarningDeductionsBeanLocal.loadAllowancesForLocation();
        return listOfLocationEarningDeductions;
    }

    public void setListOfLocationEarningDeductions(List<HrPayrollEarningDeductions> listOfLocationEarningDeductions) {
        this.listOfLocationEarningDeductions = listOfLocationEarningDeductions;
    }
    private static List<HrAddresses> addresses;

    public static List<HrAddresses> getAddresses() {
        return addresses;
    }

    public static void setAddresses(List<HrAddresses> addresses) {
        AllownaceAmountRegistrationCont.addresses = addresses;
    }

    public HrPayrollFiltEmForAll getHrPayrollFiltEmForAll() {
        if (hrPayrollFiltEmForAll == null) {
            hrPayrollFiltEmForAll = new HrPayrollFiltEmForAll();
        }
        return hrPayrollFiltEmForAll;
    }

    public void setHrPayrollFiltEmForAll(HrPayrollFiltEmForAll hrPayrollFiltEmForAll) {
        this.hrPayrollFiltEmForAll = hrPayrollFiltEmForAll;
    }

    public List<HrPayrollPlPg> getListHrPayrollPlPg() {
        listHrPayrollPlPg = hrPayrollPlPgBeanLocal.findAllPLPG();
        return listHrPayrollPlPg;
    }

    public void setListHrPayrollPlPg(List<HrPayrollPlPg> listHrPayrollPlPg) {
        this.listHrPayrollPlPg = listHrPayrollPlPg;
    }

    public List<HrPayrollEarningDeductions> getListOfEarningDeductions() {
        listOfEarningDeductions = hrPayrollEarningDeductionsBeanLocal.loadAllEar();

        return listOfEarningDeductions;
    }

    public void setListOfEarningDeductions(List<HrPayrollEarningDeductions> listOfEarningDeductions) {
        this.listOfEarningDeductions = listOfEarningDeductions;
    }

    public HrPayrollEarningDeductions getHrPayrollEarningDeductions() {
        return hrPayrollEarningDeductions;
    }

    public void setHrPayrollEarningDeductions(HrPayrollEarningDeductions hrPayrollEarningDeductions) {
        this.hrPayrollEarningDeductions = hrPayrollEarningDeductions;
    }

    public HrLuJobLevels getHrLuJobLevels() {
        return hrLuJobLevels;
    }

    public void setHrLuJobLevels(HrLuJobLevels hrLuJobLevels) {
        this.hrLuJobLevels = hrLuJobLevels;
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

    public HrAllowanceInLevels getHrAllowanceInLevels() {

        if (hrAllowanceInLevels == null) {
            hrAllowanceInLevels = new HrAllowanceInLevels();
        }
        return hrAllowanceInLevels;
    }

    public void setHrAllowanceInLevels(HrAllowanceInLevels hrAllowanceInLevels) {
        this.hrAllowanceInLevels = hrAllowanceInLevels;
    }

    public HrAllowanceTypes getHrAllowanceTypes() {
        return hrAllowanceTypes;
    }

    public void setHrAllowanceTypes(HrAllowanceTypes hrAllowanceTypes) {
        this.hrAllowanceTypes = hrAllowanceTypes;
    }

    public List<HrAllowanceTypes> getListOfAllowanceTypes() {
        listOfAllowanceTypes = hrAllowanceTypesBeanLocal.findAll();
        return listOfAllowanceTypes;
    }

    public void setListOfAllowanceTypes(List<HrAllowanceTypes> listOfAllowanceTypes) {
        this.listOfAllowanceTypes = listOfAllowanceTypes;
    }

    public List<HrLuGrades> getListOfGrades() {
        return hrLuGradesFacadeLocal.findAll();

    }

    public List<HrLuJobLevels> getListOfJobLevels() {
        return hrLuJobLevelsFacadeLocal.findAll();
    }

    public void setListOfJobLevels(List<HrLuJobLevels> listOfJobLevels) {
        this.listOfJobLevels = listOfJobLevels;
    }

    public List<HrPayrollFiltEmForAll> getListOfFilteredEmployees() {
        return listOfFilteredEmployees;
    }

    public void setListOfFilteredEmployees(List<HrPayrollFiltEmForAll> listOfFilteredEmployees) {
        this.listOfFilteredEmployees = listOfFilteredEmployees;
    }

    public List<HrAllowanceInLevels> getListOfAllowanceInJobLevels() {
//        listOfAllowanceInJobLevels = hrAllowanceInLevelsFacade.findAll();
        return listOfAllowanceInJobLevels;
    }

    public void setListOfAllowanceInJobLevels(List<HrAllowanceInLevels> listOfAllowanceInJobLevels) {
        this.listOfAllowanceInJobLevels = listOfAllowanceInJobLevels;
    }

    public HrAllowanceInLocations getHrAllowanceInLocations() {
        if (hrAllowanceInLocations == null) {
            hrAllowanceInLocations = new HrAllowanceInLocations();
        }
        return hrAllowanceInLocations;
    }

    public void setHrAllowanceInLocations(HrAllowanceInLocations hrAllowanceInLocations) {
        this.hrAllowanceInLocations = hrAllowanceInLocations;
    }

    public String getEdForAllInLocation() {
        return edForAllInLocation;
    }

    public void setEdForAllInLocation(String edForAllInLocation) {
        this.edForAllInLocation = edForAllInLocation;
    }

    public void handleEdForAllInLocation(ValueChangeEvent event) {
        edForAllInLocation = event.getNewValue().toString();
    }

    public String getLableValue() {
        return lableValue;
    }

    public void setLableValue(String lableValue) {
        this.lableValue = lableValue;
    }

    public List<HrAddresses> getListOfAddresses() {
        listOfAddresses = hrAddressesFacade1.findAll();
        return listOfAddresses;
    }

    public void setListOfAddresses(List<HrAddresses> listOfAddresses) {
        this.listOfAddresses = listOfAddresses;
    }

    public HrAddresses getHrAddresses() {
        return hrAddresses;
    }

    public void setHrAddresses(HrAddresses hrAddresses) {
        this.hrAddresses = hrAddresses;
    }

    public List<HrAllowanceInLocations> getListOfAllowanceInLocation() {
        return listOfAllowanceInLocation;
    }

    public void setListOfAllowanceInLocation(List<HrAllowanceInLocations> listOfAllowanceInLocation) {
        this.listOfAllowanceInLocation = listOfAllowanceInLocation;
    }

    private String edForLocation;

    public String getEdForLocation() {
        return edForLocation;
    }

    public void setEdForLocation(String edForLocation) {
        this.edForLocation = edForLocation;
    }

    private String locationId;

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public boolean isIsEarning() {
        return isEarning;
    }

    public void setIsEarning(boolean isEarning) {
        this.isEarning = isEarning;
    }

    public boolean isIsDeduction() {
        return isDeduction;
    }

    public void setIsDeduction(boolean isDeduction) {
        this.isDeduction = isDeduction;
    }

    public boolean isListOfAllowances() {
        return listOfAllowances;
    }

    public void setListOfAllowances(boolean listOfAllowances) {
        this.listOfAllowances = listOfAllowances;
    }

    public boolean isRemark() {
        return remark;
    }

    public void setRemark(boolean remark) {
        this.remark = remark;
    }

    public boolean isEmployeesId() {
        return employeesId;
    }

    public void setEmployeesId(boolean employeesId) {
        this.employeesId = employeesId;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getAllAddressUnitAsOne() {
        return allAddressUnitAsOne;
    }

    public void setAllAddressUnitAsOne(String allAddressUnitAsOne) {
        this.allAddressUnitAsOne = allAddressUnitAsOne;
    }

    public HrEmployees getHrEmployees() {
        if (hrEmployees == null) {
            hrEmployees = new HrEmployees();
        }
        return hrEmployees;
    }

    public void setHrEmployees(HrEmployees hrEmployees) {
        this.hrEmployees = hrEmployees;
    }

    public String getAmountLable() {
        return amountLable;
    }

    public void setAmountLable(String amountLable) {
        this.amountLable = amountLable;
    }

    public BigDecimal getJobLevelId() {
        return jobLevelId;
    }

    public void setJobLevelId(BigDecimal jobLevelId) {
        this.jobLevelId = jobLevelId;
    }

    public String getJobLevelDescription() {
        return jobLevelDescription;
    }

    public void setJobLevelDescription(String jobLevelDescription) {
        this.jobLevelDescription = jobLevelDescription;
    }

    public String getPlpgId() {
        return plpgId;
    }

    public void setPlpgId(String plpgId) {
        this.plpgId = plpgId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLevelCode() {
        return levelCode;
    }

    public void setLevelCode(String levelCode) {
        this.levelCode = levelCode;
    }

    public String getJlPaymentIn() {
        return jlPaymentIn;
    }

    public void setJlPaymentIn(String jlPaymentIn) {
        this.jlPaymentIn = jlPaymentIn;
    }

    public List<HrAllowanceInLevels> getListOfAllownceInJoblevelPaymentTypes() {
        return listOfAllownceInJoblevelPaymentTypes;
    }

    public void setListOfAllownceInJoblevelPaymentTypes(List<HrAllowanceInLevels> listOfAllownceInJoblevelPaymentTypes) {
        this.listOfAllownceInJoblevelPaymentTypes = listOfAllownceInJoblevelPaymentTypes;
    }

    public List<HrAllowanceInLocations> getListOfAllwowanceInLocation() {
        if (listOfAllwowanceInLocation == null) {
            listOfAllwowanceInLocation = new ArrayList<>();
        }
        return listOfAllwowanceInLocation;
    }

    public void setListOfAllwowanceInLocation(List<HrAllowanceInLocations> listOfAllwowanceInLocation) {
        this.listOfAllwowanceInLocation = listOfAllwowanceInLocation;
    }

    private List<HrAllowanceInJobTitle> listOfAllowanceInJobTitle;

    public List<HrAllowanceInJobTitle> getListOfAllowanceInJobTitle() {

        if (listOfAllowanceInJobTitle == null) {
            listOfAllowanceInJobTitle = new ArrayList<>();
        }
        return listOfAllowanceInJobTitle;
    }

    public void setListOfAllowanceInJobTitle(List<HrAllowanceInJobTitle> listOfAllowanceInJobTitle) {
        this.listOfAllowanceInJobTitle = listOfAllowanceInJobTitle;
    }

    public HrAllowanceInJobTitle getHrAllowanceInJobTitle() {
        if (hrAllowanceInJobTitle == null) {
            hrAllowanceInJobTitle = new HrAllowanceInJobTitle();
        }
        return hrAllowanceInJobTitle;
    }

    public void setHrAllowanceInJobTitle(HrAllowanceInJobTitle hrAllowanceInJobTitle) {
        this.hrAllowanceInJobTitle = hrAllowanceInJobTitle;
    }

    public List<HrJobTypes> getListOfJobTypes() {
        listOfJobTypes = hrJobTypesFacade.findAll();
        return listOfJobTypes;
    }

    public void setListOfJobTypes(List<HrJobTypes> listOfJobTypes) {
        this.listOfJobTypes = listOfJobTypes;
    }

    public HrJobTypes getJobTypes() {
        return jobTypes;
    }

    public void setJobTypes(HrJobTypes jobTypes) {
        this.jobTypes = jobTypes;
    }

    public String getJobTitleId() {
        return jobTitleId;
    }

    public void setJobTitleId(String jobTitleId) {
        this.jobTitleId = jobTitleId;
    }

    public String getEarningDedCode() {
        return earningDedCode;
    }

    public void setEarningDedCode(String earningDedCode) {
        this.earningDedCode = earningDedCode;
    }

    public HrAddresses getHrAddressAllowanceLocation() {
        return hrAddressAllowanceLocation;
    }

    public void setHrAddressAllowanceLocation(HrAddresses hrAddressAllowanceLocation) {
        this.hrAddressAllowanceLocation = hrAddressAllowanceLocation;
    }

    public HrAddresses getHrAddressJobLevel() {
        return hrAddressJobLevel;
    }

    public void setHrAddressJobLevel(HrAddresses hrAddressJobLevel) {
        this.hrAddressJobLevel = hrAddressJobLevel;
    }

    public HrAddresses getHrAddressJobTitle() {
        return hrAddressJobTitle;
    }

    public void setHrAddressJobTitle(HrAddresses hrAddressJobTitle) {
        this.hrAddressJobTitle = hrAddressJobTitle;
    }

    public String getSaveOrUpdate() {
        return saveOrUpdate;
    }

    public void setSaveOrUpdate(String saveOrUpdate) {
        this.saveOrUpdate = saveOrUpdate;
    }
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="main Methods">
    
    // <editor-fold defaultstate="collapsed" desc="Allowance in job levels(save,update,edit,search)">
    
    public void saveAllowanceInJobLevel() {
        try {
            
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(sessionBeanLocal.getUserName(), "saveAllowanceInJobLevel", dataset)) {
                BigDecimal selectedCode = new BigDecimal(code);
                BigDecimal selectedLevelCode = new BigDecimal(levelCode);
                hrPayrollEarningDeductions.setCode(selectedCode);
                hrAllowanceInLevels.setPaymentIn(jlPaymentIn);
                hrAllowanceInLevels.setEarningDeductionId(hrPayrollEarningDeductions);
                hrLuJobLevels.setId(selectedLevelCode);
                hrAllowanceInLevels.setLevelId(hrLuJobLevels);
                hrAllowanceInLevels.setPlPg(hrPayrollPlPg);
                if (saveOrUpdate.equalsIgnoreCase("Save")) {
                    if (hrAllowanceInLevelsBeanLocal.checkAllInJobLevel(hrPayrollEarningDeductions, hrAllowanceInLevels, hrLuJobLevels, hrAddressJobLevel) != null) {
                        JsfUtil.addFatalMessage("Duplicate data");
                        listOfAllowanceInJobLevels = hrAllowanceInLevelsBeanLocal.findAll();
                    } else {
                        hrAllowanceInLevelsBeanLocal.edit(hrAllowanceInLevels);
                        JsfUtil.addSuccessMessage("Sucessfully Saved");
                        listOfAllowanceInJobLevels = hrAllowanceInLevelsBeanLocal.findAll();
                        clearAllowanceInLevels();
                    }
                } else {
                    if (hrAllowanceInLevelsBeanLocal.checkAllInJobLevelForUpdate(hrPayrollEarningDeductions, hrAllowanceInLevels, hrLuJobLevels, hrAddressJobLevel) == null) {
                        hrAllowanceInLevelsBeanLocal.edit(hrAllowanceInLevels);
                        JsfUtil.addSuccessMessage("Sucessfully Updated");
                        listOfAllowanceInJobLevels = hrAllowanceInLevelsBeanLocal.findAll();
                        clearAllowanceInLevels();
                    } else {
                        JsfUtil.addFatalMessage("Make sure that you don't add a duplicated data!");
                        listOfAllowanceInJobLevels = hrAllowanceInLevelsBeanLocal.findAll();
                    }
                }
                saveOrUpdate = "Save";
                
            } else {
                EventEntry eventEntry = new EventEntry();
                eventEntry.setSessionId(sessionBeanLocal.getSessionID());
                eventEntry.setUserId(sessionBeanLocal.getUserId());
                QName qualifiedName = new QName("", "project");
                JAXBElement<String> userName = new JAXBElement<String>(qualifiedName, String.class, null, sessionBeanLocal.getUserName());
                eventEntry.setUserLogin(userName);
                JAXBElement<String> module = new JAXBElement<String>(qualifiedName, String.class, null, "HRMS");
                eventEntry.setModule(module);
                JAXBElement<String> program = new JAXBElement<String>(qualifiedName, String.class, null, "saveAllowanceInJobLevel");
                eventEntry.setProgram(program);
                security.addEventLog(eventEntry, dataset);
            }
        } catch (ConstraintViolationException ex) {
            ex.getConstraintViolations();
            ex.printStackTrace();
        }
    }
    
    public void clearAllowanceInLevels() {
        hrPayrollPlPg = new HrPayrollPlPg();
        hrPayrollEarningDeductions = new HrPayrollEarningDeductions();
        hrAllowanceInLevels = new HrAllowanceInLevels();
        hrLuJobLevels = new HrLuJobLevels();
        code = null;
        levelCode = null;
    }
    
    public void edit() {
        try {
            hrPayrollPlPg = hrAllowanceInLevels.getPlPg();
            levelCode = String.valueOf(hrAllowanceInLevels.getLevelId().getId());
            code = String.valueOf(hrAllowanceInLevels.getEarningDeductionId().getCode());
            plpgId = String.valueOf(hrAllowanceInLevels.getPlPg().getId());
            saveOrUpdate = "Update";
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
    }
    
    public void onLevelChange() {
        try {
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public void getJobLevelsPaymentIn(ValueChangeEvent event) {
        try {
            jlPaymentIn = event.getNewValue().toString();
            if (event.getNewValue().toString().equalsIgnoreCase("Birr")) {
                lableValue = "Amount in Birr:";
            } else if (event.getNewValue().toString().equalsIgnoreCase("Percent")) {
                lableValue = "Amount in Percent:";
            } else if (event.getNewValue().toString().equalsIgnoreCase("kind")) {
                lableValue = "Kind";
            } else if (event.getNewValue().toString().equalsIgnoreCase("kind In Birr/Amount")) {
                lableValue = "Amount:";
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public void handleChangeJobLevel(ValueChangeEvent event) {
        plpgId = event.getNewValue().toString();
    }
    
    public void handleChange(ValueChangeEvent event) {
        code = event.getNewValue().toString();
        
    }
    
    public void handleLevelChnage() {
        try {
            BigDecimal edCode = new BigDecimal(earningDedCode);
            hrPayrollEarningDeductions.setCode(edCode);
            listOfAllowanceInJobLevels = hrAllowanceInLevelsBeanLocal.returnAllowanceInJobLevelAmountTypes(hrPayrollEarningDeductions);
        } catch (Exception e) {
        }
        
    }
    
    public void getJobLevel(ValueChangeEvent event) {
        try {
            levelCode = event.getNewValue().toString();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public void handleChangeJobLevel() {
        
        try {
            BigDecimal edCode = new BigDecimal(code);
            hrPayrollEarningDeductions.setCode(edCode);
            listOfAllowanceInJobLevels = hrAllowanceInLevelsBeanLocal.returnAllowanceInJobLevelAmountTypes(hrPayrollEarningDeductions);
        } catch (Exception e) {
        }
        
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
//</editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Allowance in job title(save,update,edit,search)">
    
    public void saveAllowanceInTitle() {
        try {
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(sessionBeanLocal.getUserName(), "saveAllowanceInTitle", dataset)) {
                BigDecimal edCode = new BigDecimal(earningDedCode);
                hrPayrollEarningDeductions.setCode(edCode);
                BigDecimal jobType = new BigDecimal(jobTitleId);
                jobTypes.setId(Integer.valueOf(jobTitleId));
                hrAllowanceInJobTitle.setJobTitleId(jobTypes);
                hrAllowanceInJobTitle.setEarningDeductionId(hrPayrollEarningDeductions);
                hrAllowanceInJobTitle.setAllwanceIn(jlPaymentIn);
                hrAllowanceInJobTitle.setJpJl(hrPayrollPlPg);
                if (saveOrUpdate.equalsIgnoreCase("Save")) {
                    if (hrAllowanceInJobTitleBeanLocal.checkAllowanceInJobTitle(hrAllowanceInJobTitle, hrPayrollEarningDeductions, hrAddressJobTitle) != null) {
                        JsfUtil.addFatalMessage("Duplicate Data!");
                    } else {
                        hrAllowanceInJobTitleBeanLocal.create(hrAllowanceInJobTitle);
                        JsfUtil.addSuccessMessage("Sucessfully Saved");
                        saveOrUpdate = "Save";
                        listOfAllowanceInJobTitle = hrAllowanceInJobTitleBeanLocal.findAllowncesByEdCode(hrPayrollEarningDeductions);
                        clearAllowanceInTitle();
                    }
                } else {
                    if (hrAllowanceInJobTitleBeanLocal.checkAllowanceInJobTitleForUpdate(hrAllowanceInJobTitle, hrPayrollEarningDeductions, hrAddressJobTitle) == null) {
                        hrAllowanceInJobTitleBeanLocal.edit(hrAllowanceInJobTitle);
                        JsfUtil.addSuccessMessage("Sucessfully Updated");
                        saveOrUpdate = "Save";
                        listOfAllowanceInJobTitle = hrAllowanceInJobTitleBeanLocal.findAllowncesByEdCode(hrPayrollEarningDeductions);
                        clearAllowanceInTitle();
                    } else {
                        JsfUtil.addFatalMessage("Make sure that you don't add a duplicated data!");
                    }
                }
                
            } else {
                EventEntry eventEntry = new EventEntry();
                eventEntry.setSessionId(sessionBeanLocal.getSessionID());
                eventEntry.setUserId(sessionBeanLocal.getUserId());
                QName qualifiedName = new QName("", "project");
                JAXBElement<String> userName = new JAXBElement<String>(qualifiedName, String.class, null, sessionBeanLocal.getUserName());
                eventEntry.setUserLogin(userName);
                JAXBElement<String> module = new JAXBElement<String>(qualifiedName, String.class, null, "HRMS");
                eventEntry.setModule(module);
                JAXBElement<String> program = new JAXBElement<String>(qualifiedName, String.class, null, "saveAllowanceInTitle");
                eventEntry.setProgram(program);
                security.addEventLog(eventEntry, dataset);
            }
        } catch (ConstraintViolationException ex) {
            ex.getConstraintViolations();
            ex.printStackTrace();
        }
    }
    
    public void clearAllowanceInTitle() {
        hrPayrollPlPg = new HrPayrollPlPg();
        hrPayrollEarningDeductions = new HrPayrollEarningDeductions();
        hrAllowanceInJobTitle = new HrAllowanceInJobTitle();
        jobTypes = new HrJobTypes();
        jobTitleId = null;
        earningDedCode = null;
    }
    
    public void editAllInJobTitle() {
        try {
            hrPayrollPlPg = hrAllowanceInJobTitle.getJpJl();
            jobTitleId = String.valueOf(hrAllowanceInJobTitle.getJobTitleId().getId());
            earningDedCode = String.valueOf(hrAllowanceInJobTitle.getEarningDeductionId().getCode());
            saveOrUpdate = "Update";
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public void handleChange1() {
        try {
            BigDecimal edCode = new BigDecimal(earningDedCode);
            hrPayrollEarningDeductions.setCode(edCode);
            listOfAllowanceInJobTitle = hrAllowanceInJobTitleBeanLocal.findAllowncesByEdCode(hrPayrollEarningDeductions);
        } catch (Exception e) {
        }
    }
    
    public void lableChange() {
        try {
            BigDecimal edCode = new BigDecimal(earningDedCode);
            hrPayrollEarningDeductions.setCode(edCode);
            listOfAllowanceInJobTitle = hrAllowanceInJobTitleBeanLocal.findAllowncesByEdCode(hrPayrollEarningDeductions);
        } catch (Exception e) {
        }
    }
    
    public void handleChangesLocation() {
        BigDecimal edCode = new BigDecimal(earningDedCode);
        hrPayrollEarningDeductions.setCode(edCode);
        listOfAllowanceInJobTitle = hrAllowanceInJobTitleBeanLocal.findAllowncesByEdCode(hrPayrollEarningDeductions);
    }
    
    public void handleEarningDedCode(ValueChangeEvent event) {
        try {
            earningDedCode = event.getNewValue().toString();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public void getJobTitle(ValueChangeEvent event) {
        try {
            jobTitleId = event.getNewValue().toString();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public void handleJobTypeChange(ValueChangeEvent event) {
        jobTitleId = event.getNewValue().toString();
        
    }
//</editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Allowance in location(save,update,edit,search)">
    
    public void saveAllowanceInLocation() {
        try {
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(sessionBeanLocal.getUserName(), "saveAllowanceInLocation", dataset)) {
                BigDecimal edForLoc = new BigDecimal(edForAllInLocation);
                hrPayrollEarningDeductions.setCode(edForLoc);
                BigDecimal selectedLevelCode = new BigDecimal(levelCode);
                hrLuJobLevels.setId(selectedLevelCode);
                hrAllowanceInLocations.setLevelId(hrLuJobLevels);
                hrAllowanceInLocations.setEarningDeductionCode(hrPayrollEarningDeductions);
                hrAllowanceInLocations.setJlJl(hrPayrollPlPg);
                if (saveOrUpdate.equalsIgnoreCase("Save")) {
                    if (hrAllowanceInLocationsBeanLocal.checkAllInLocation(hrAllowanceInLocations) != null) {
                        JsfUtil.addFatalMessage("Duplicated Data!");
                        listOfAllowanceInLocation = hrAllowanceInLocationsBeanLocal.findAll();
                    } else {
                        hrAllowanceInLocationsBeanLocal.edit(hrAllowanceInLocations);
                        JsfUtil.addSuccessMessage("Sucessfully Saved");
                        listOfAllowanceInLocation = hrAllowanceInLocationsBeanLocal.findAll();
                        clearAllowanceInLocation();
                    }
                } else {
                    if (hrAllowanceInLocationsBeanLocal.checkAllInLocationForUpdate(hrAllowanceInLocations) == null) {
                        hrAllowanceInLocationsBeanLocal.edit(hrAllowanceInLocations);
                        JsfUtil.addSuccessMessage("Sucessfully Update");
                        listOfAllowanceInLocation = hrAllowanceInLocationsBeanLocal.findAll();
                        clearAllowanceInLocation();
                    } else {
                        JsfUtil.addFatalMessage("Duplicate Data");
                        listOfAllowanceInLocation = hrAllowanceInLocationsBeanLocal.findAll();
                    }
                }
                saveOrUpdate = "Save";
            } else {
                EventEntry eventEntry = new EventEntry();
                eventEntry.setSessionId(sessionBeanLocal.getSessionID());
                eventEntry.setUserId(sessionBeanLocal.getUserId());
                QName qualifiedName = new QName("", "project");
                JAXBElement<String> userName = new JAXBElement<String>(qualifiedName, String.class, null, sessionBeanLocal.getUserName());
                eventEntry.setUserLogin(userName);
                JAXBElement<String> module = new JAXBElement<String>(qualifiedName, String.class, null, "HRMS");
                eventEntry.setModule(module);
                JAXBElement<String> program = new JAXBElement<String>(qualifiedName, String.class, null, "saveAllowanceInLocation");
                eventEntry.setProgram(program);
                security.addEventLog(eventEntry, dataset);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void clearAllowanceInLocation() {
        hrPayrollPlPg = new HrPayrollPlPg();
        hrLuJobLevels = new HrLuJobLevels();
        hrAllowanceInLocations = new HrAllowanceInLocations();
        levelCode = null;
        edForAllInLocation = null;
        
    }
    
    public void handleLablelChange(ValueChangeEvent event) {
        jlPaymentIn = event.getNewValue().toString();
        if (event.getNewValue().toString().equalsIgnoreCase("Birr")) {
            lableValue = "Amount in Birr:";
        } else if (event.getNewValue().toString().equalsIgnoreCase("Percent")) {
            lableValue = "Amount in Percent:";
        } else if (event.getNewValue().toString().equalsIgnoreCase("kind")) {
            lableValue = "Kind";
            
        } else if (event.getNewValue().toString().equalsIgnoreCase("kind In Birr/Amount")) {
            lableValue = "Amount:";
        }
    }
    
    public void editAllInLocation() {
        try {
            hrPayrollPlPg = hrAllowanceInLocations.getJlJl();
            levelCode = String.valueOf(hrAllowanceInLocations.getLevelId().getId());
            edForAllInLocation = String.valueOf(hrAllowanceInLocations.getEarningDeductionCode().getCode());
            saveOrUpdate = "Update";
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public void handleAllownceInLocation() {
        try {
            BigDecimal edCode = new BigDecimal(edForAllInLocation);
            hrPayrollEarningDeductions.setCode(edCode);
            listOfAllowanceInLocation = hrAllowanceInLocationsBeanLocal.findAllowncePaymentTypes(hrPayrollEarningDeductions);
            
        } catch (Exception e) {
        }
        
    }
    
    public void handleEarningDedCodeLocation(ValueChangeEvent event) {
        try {
            edForAllInLocation = event.getNewValue().toString();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
    }
//</editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Filter employee for allowance payment(save,update,edit,search)">
    
    public void filterEmployeeForAllowancePayment() {
        try {
//            AAA securityService = new AAA();
//            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
//            String systemBundle = "et/gov/eep/commonApplications/securityServer";
//            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
//            if (security.checkAccess(sessionBeanLocal.getUserName(), "filterEmployeeForAllowancePayment", dataset)) {
            if (saveOrUpdateFilterEmp.equals("Save")) {
                if (hrPayrollFiltEmForAllBeanLocal.checkFilteredEmployees(hrEmployees, hrPayrollEdForAllowance) == null) {
                    hrPayrollFiltEmForAll.setEarningDedCode(hrPayrollEdForAllowance);
                    hrPayrollFiltEmForAll.setEmpId(hrEmployees);
                    hrPayrollFiltEmForAllBeanLocal.create(hrPayrollFiltEmForAll);
                    hrPayrollEdForAllowance = new HrPayrollEarningDeductions();
                    hrEmployees = new HrEmployees();
                    hrPayrollFiltEmForAll = new HrPayrollFiltEmForAll();
                    JsfUtil.addSuccessMessage("Sucessfully Saved");
                } else {
                    JsfUtil.addFatalMessage("Duplicate Data");
                }
            } else {
                hrPayrollFiltEmForAll.setEarningDedCode(hrPayrollEdForAllowance);
                hrPayrollFiltEmForAll.setEmpId(hrEmployees);
                hrPayrollFiltEmForAllBeanLocal.edit(hrPayrollFiltEmForAll);
                hrPayrollEdForAllowance = new HrPayrollEarningDeductions();
                hrEmployees = new HrEmployees();
                hrPayrollFiltEmForAll = new HrPayrollFiltEmForAll();
                JsfUtil.addSuccessMessage("Sucessfully Updated");
            }
//            } else {
//                EventEntry eventEntry = new EventEntry();
//                eventEntry.setSessionId(sessionBeanLocal.getSessionID());
//                eventEntry.setUserId(sessionBeanLocal.getUserId());
//                QName qualifiedName = new QName("", "project");
//                JAXBElement<String> userName = new JAXBElement<String>(qualifiedName, String.class, null, sessionBeanLocal.getUserName());
//                eventEntry.setUserLogin(userName);
//                JAXBElement<String> module = new JAXBElement<String>(qualifiedName, String.class, null, "HRMS");
//                eventEntry.setModule(module);
//                JAXBElement<String> program = new JAXBElement<String>(qualifiedName, String.class, null, "filterEmployeeForAllowancePayment");
//                eventEntry.setProgram(program);
//                security.addEventLog(eventEntry, dataset);
//            }
        } catch (Exception ex) {
            ex.printStackTrace();
            
        }
    }
    
    public void EditFilteredEmployees() {
        hrEmployees.setId(hrPayrollFiltEmForAll.getEmpId().getId());
        listOfAllowances = true;
        remark = true;
        employeesId = true;
        status = false;
        saveOrUpdateFilterEmp = "Update";
    }
    
    public void loadAllEmpEArnings() {
        try {
            if (isEarning) {
                isDeduction = false;
                listOfEarningDeductions = null;
                listOfEarningDeductions = hrPayrollEarningDeductionsBeanLocal.listOfEarnngForAllEmp();
            }
        } catch (Exception e) {
            e.printStackTrace();
            
        }
    }
    
    public void editAllInLocation1() {
        try {
            hrEmployees.setId(hrPayrollFiltEmForAll.getEmpId().getId());
            hrEmployees = hrEmployeeBean.searchById(hrEmployees);
            saveOrUpdateFilterEmp = "Update";
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public void handleChangeFilterEmployees() {
        try {
            listOfFilteredEmployees = hrPayrollFiltEmForAllBeanLocal.filterEmpByEd(hrPayrollEdForAllowance);
        } catch (Exception e) {
        }
        
    }
    
    public void updateFilteredEmployees() {
        try {
            hrPayrollFiltEmForAll.setEarningDedCode(hrPayrollEdForAllowance);
            hrPayrollFiltEmForAll.setEmpId(hrEmployees);
            hrPayrollFiltEmForAllBeanLocal.edit(hrPayrollFiltEmForAll);
            JsfUtil.addSuccessMessage("Sucessfully Updated");
        } catch (Exception ex) {
            ex.printStackTrace();
            JsfUtil.addFatalMessage("Error occred While Updating the employee");
            
        }
    }
    
    public void getByFirstName(SelectEvent event) {
        try {
            hrEmployees.setEmpId(event.getObject().toString());
            hrEmployees = hrEmployeeBean.getByEmpId(hrEmployees);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    
    public ArrayList<HrEmployees> SearchByEmpId(String hrEmployee) {
        ArrayList<HrEmployees> employees = null;
        hrEmployees.setEmpId(hrEmployee);
        employees = hrEmployeeBean.SearchByEmpId(hrEmployees);
        listOfAllowances = false;
        remark = false;
        employeesId = false;
        status = false;
        return employees;
    }
    
    public void loadAllEmpDeductions() {
        try {
            if (isDeduction) {
                isEarning = false;
                listOfEarningDeductions = null;
                listOfEarningDeductions = hrPayrollEarningDeductionsBeanLocal.listOfDeductionsForAllEmp();
            }
        } catch (Exception e) {
            e.printStackTrace();
            
        }
    }
//</editor-fold>
//</editor-fold>
}
