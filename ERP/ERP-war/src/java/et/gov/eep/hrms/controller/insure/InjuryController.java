/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.controller.insure;

import et.gov.eep.commonApplications.entity.ColumnNameResolver;
import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.commonApplications.utility.date.StringDateManipulation;
import et.gov.eep.hrms.businessLogic.employee.HrEmployeeBeanLocal;
import et.gov.eep.hrms.businessLogic.insure.HrinsurancebeanLocal;
import et.gov.eep.hrms.businessLogic.insure.InsuranceBranchBeanLocal;
import et.gov.eep.hrms.businessLogic.insure.InsuranceInjuredEmployeesLocal;
import et.gov.eep.hrms.businessLogic.insure.InsurancebeanLocal;
import et.gov.eep.hrms.entity.employee.HrEmpAddresses;
import et.gov.eep.hrms.entity.employee.HrEmployees;
import et.gov.eep.hrms.entity.insurance.HrInsurance;
import et.gov.eep.hrms.entity.insurance.HrInsuranceInjuredEmployee;
import et.gov.eep.hrms.entity.insurance.HrLuInsuranceBranches;
import et.gov.eep.hrms.entity.insurance.HrLuInsurances;
import et.gov.eep.hrms.entity.lookup.HrLuGrades;
import et.gov.eep.hrms.entity.organization.HrDepartments;
import et.gov.eep.hrms.entity.organization.HrJobTypes;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
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
import org.primefaces.event.SelectEvent;
import securityBean.SessionBean;
import securityBean.WorkFlow;
import webService.AAA;
import webService.EventEntry;
import webService.IAdministration;

/**
 *
 * @author Me
 */
@Named(value = "injuryController")
@ViewScoped
public class InjuryController implements Serializable {

    //<editor-fold defaultstate="collapsed" desc="Entity Injection">
    @Inject
            HrInsuranceInjuredEmployee hrInsuranceInjuredEmployee;
    @Inject
            HrInsurance hrInsurance;
    @Inject
            HrLuInsuranceBranches hrLuInsuranceBranches;
    @Inject
            HrLuInsurances hrLuInsurances;
    @Inject
            HrEmployees hrEmployees;
    @Inject
            HrJobTypes hrJobTypes;
    @Inject
            HrDepartments hrDepartments;
    @Inject
            HrEmpAddresses hrEmpAddresses;
    @Inject
            HrLuGrades hrLuGrades;
    @Inject
            SessionBean SessionBean;
    @Inject
            WorkFlow workFlow;
    @Inject
            ColumnNameResolver columnNameResolver;
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="EJB Declaration">
    @EJB
            InsurancebeanLocal insurancebeanLocal;
    @EJB
            InsuranceInjuredEmployeesLocal insuranceInjuredEmployeesLocal;
    @EJB
            HrinsurancebeanLocal hrinsurancebeanLocal;
    @EJB
            InsuranceBranchBeanLocal insuranceBranchBeanLocal;
    @EJB
            HrEmployeeBeanLocal hrEmployeesBeanLocal;
//</editor-fold>
    
   //<editor-fold defaultstate="collapsed" desc="List and dataModel ">
    DataModel<HrInsuranceInjuredEmployee> hrinsuranceinjuredmodel = new ListDataModel<>();
    DataModel<HrLuInsuranceBranches> hrsmplandetailmodel = new ListDataModel<>();
    HrLuInsuranceBranches selectedRow = null;
    DataModel<HrInsurance> insuranceDataModel;
    HrInsuranceInjuredEmployee selectedRow2 = null;
    HrInsurance selectrowinsurance = null;
    List<SelectItem> filterByStatus = new ArrayList<>();
    List<ColumnNameResolver> ColumnNameResolverList = new ArrayList<>();
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="variable declaration">
    private boolean renderPnlCreateGatePass = false;
    private boolean disableBtnCreate;
    private String saveorUpdateBundle = "Create";
    private String createOrSearchBundle = "New";
    private boolean renderPnlCreateAdditional = false;
    private boolean renderPnlManPage = true;
    private String icone = "ui-icon-plus";
    boolean lockInput = false;
    private String saveOrUpdateButton = "Save";
    int update = 0;
    int status = 1;
    private String choosePermanet = "true";
    private String chooseDailyLabour = "false";
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="post construct">
    @PostConstruct
    public void init() {
        hrLuInsuranceslist = insurancebeanLocal.findAll();
        SearchActiveProvider();
        hrInsuranceenjuredlist = insuranceInjuredEmployeesLocal.findakkll();
        hrInsuranceInjuredEmployee.setType("Permanent/contract");
        hrInsurance.setStatus(1);
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="getter and setter">
    List<HrInsuranceInjuredEmployee> compList;
    
    public InjuryController() {
    }
    
    public ColumnNameResolver getColumnNameResolver() {
        return columnNameResolver;
    }
    
    public void setColumnNameResolver(ColumnNameResolver columnNameResolver) {
        this.columnNameResolver = columnNameResolver;
    }
    
    public List<ColumnNameResolver> getColumnNameResolverList() {
        ColumnNameResolverList = insuranceInjuredEmployeesLocal.findColumns();
        if (ColumnNameResolverList == null) {
            ColumnNameResolverList = new ArrayList<>();
        }
        return ColumnNameResolverList;
    }
    
    public void setColumnNameResolverList(List<ColumnNameResolver> ColumnNameResolverList) {
        this.ColumnNameResolverList = ColumnNameResolverList;
    }
    
    public boolean isLockInput() {
        return lockInput;
    }
    
    public void setLockInput(boolean lockInput) {
        this.lockInput = lockInput;
    }
    
    public HrDepartments getHrDepartments() {
        if (hrDepartments == null) {
            hrDepartments = new HrDepartments();
        }
        return hrDepartments;
    }
    
    public void setHrDepartments(HrDepartments hrDepartments) {
        this.hrDepartments = hrDepartments;
    }
    
    public List<HrInsuranceInjuredEmployee> getCompList() {
        if (compList == null) {
            compList = new ArrayList<>();
        }
        return compList;
    }
    
    public void setCompList(List<HrInsuranceInjuredEmployee> compList) {
        this.compList = compList;
    }
    
    public void ListInjuredEmp() {
        compList = insuranceInjuredEmployeesLocal.findall();
    }
    
    public HrInsuranceInjuredEmployee getSelectedRow2() {
        if (selectedRow2 == null) {
            selectedRow2 = new HrInsuranceInjuredEmployee();
        }
        return selectedRow2;
    }
    
    public void setSelectedRow2(HrInsuranceInjuredEmployee selectedRow2) {
        this.selectedRow2 = selectedRow2;
    }
    
    public DataModel<HrInsuranceInjuredEmployee> getHrinsuranceinjuredmodel() {
        return hrinsuranceinjuredmodel;
    }
    
    public void setHrinsuranceinjuredmodel(DataModel<HrInsuranceInjuredEmployee> hrinsuranceinjuredmodel) {
        this.hrinsuranceinjuredmodel = hrinsuranceinjuredmodel;
    }
    
    public HrInsurance getHrInsurance() {
        if (hrInsurance == null) {
            hrInsurance = new HrInsurance();
        }
        return hrInsurance;
    }
    
    public void setHrInsurance(HrInsurance HrInsurance) {
        this.hrInsurance = HrInsurance;
    }
    
    public HrInsurance getSelectrowinsurance() {
        if (selectrowinsurance == null) {
            selectrowinsurance = new HrInsurance();
        }
        return selectrowinsurance;
    }
    
    public void setSelectrowinsurance(HrInsurance selectrowinsurance) {
        this.selectrowinsurance = selectrowinsurance;
    }
    
    public HrInsuranceInjuredEmployee getHrInsuranceInjuredEmployee() {
        if (hrInsuranceInjuredEmployee == null) {
            hrInsuranceInjuredEmployee = new HrInsuranceInjuredEmployee();
        }
        return hrInsuranceInjuredEmployee;
    }
    
    public void setHrInsuranceInjuredEmployee(HrInsuranceInjuredEmployee hrInsuranceInjuredEmployee) {
        this.hrInsuranceInjuredEmployee = hrInsuranceInjuredEmployee;
    }
    
    public DataModel<HrLuInsuranceBranches> getHrsmplandetailmodel() {
        return hrsmplandetailmodel;
    }
    
    public void setHrsmplandetailmodel(DataModel<HrLuInsuranceBranches> hrsmplandetailmodel) {
        this.hrsmplandetailmodel = hrsmplandetailmodel;
    }
    
    public HrLuInsuranceBranches getSelectedRow() {
        return selectedRow;
    }
    
    public void setSelectedRow(HrLuInsuranceBranches selectedRow) {
        this.selectedRow = selectedRow;
    }
    
    public boolean isRenderPnlCreateGatePass() {
        return renderPnlCreateGatePass;
    }
    
    public HrEmpAddresses getHrEmpAddresses() {
        if (hrEmpAddresses == null) {
            hrEmpAddresses = new HrEmpAddresses();
        }
        return hrEmpAddresses;
    }
    
    public void setHrEmpAddresses(HrEmpAddresses hrEmpAddresses) {
        this.hrEmpAddresses = hrEmpAddresses;
    }
    
    public void setRenderPnlCreateGatePass(boolean renderPnlCreateGatePass) {
        this.renderPnlCreateGatePass = renderPnlCreateGatePass;
    }
    
    public boolean isDisableBtnCreate() {
        return disableBtnCreate;
    }
    
    public void setDisableBtnCreate(boolean disableBtnCreate) {
        this.disableBtnCreate = disableBtnCreate;
    }
    
    public String getSaveorUpdateBundle() {
        return saveorUpdateBundle;
    }
    
    public void setSaveorUpdateBundle(String saveorUpdateBundle) {
        this.saveorUpdateBundle = saveorUpdateBundle;
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
    
    public String getSaveOrUpdateButton() {
        return saveOrUpdateButton;
    }
    
    public void setSaveOrUpdateButton(String saveOrUpdateButton) {
        this.saveOrUpdateButton = saveOrUpdateButton;
    }
    
    public int getUpdate() {
        return update;
    }
    
    public void setUpdate(int update) {
        this.update = update;
    }
    
    public int getStatus() {
        return status;
    }
    
    public void setStatus(int status) {
        this.status = status;
    }
    
    public HrLuInsuranceBranches getHrLuInsuranceBranches() {
        if (hrLuInsuranceBranches == null) {
            hrLuInsuranceBranches = new HrLuInsuranceBranches();
        }
        return hrLuInsuranceBranches;
    }
    
    public void setHrLuInsuranceBranches(HrLuInsuranceBranches hrLuInsuranceBranches) {
        this.hrLuInsuranceBranches = hrLuInsuranceBranches;
    }
    
    public HrLuInsurances getHrLuInsurances() {
        if (hrLuInsurances == null) {
            hrLuInsurances = new HrLuInsurances();
        }
        return hrLuInsurances;
    }
    
    public void setHrLuInsurances(HrLuInsurances hrLuInsurances) {
        this.hrLuInsurances = hrLuInsurances;
    }
    
    public String getChoosePermanet() {
        return choosePermanet;
    }
    
    public void setChoosePermanet(String choosePermanet) {
        this.choosePermanet = choosePermanet;
    }
    
    public String getChooseDailyLabour() {
        return chooseDailyLabour;
    }
    
    public void setChooseDailyLabour(String chooseDailyLabour) {
        this.chooseDailyLabour = chooseDailyLabour;
    }
    
    public String getSlected() {
        return slected;
    }
    
    public void setSlected(String slected) {
        this.slected = slected;
    }
    
    String slected = "Select One";
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Main methods ">
    public void handleEmployee(ValueChangeEvent event) {
        if (null != event.getNewValue()) {
            slected = event.getNewValue().toString();
            if (slected.equalsIgnoreCase("Permanent/contract")) {
                choosePermanet = "true";
                chooseDailyLabour = "false";
            } else {
                choosePermanet = "false";
                chooseDailyLabour = "true";
            }
        }
    }
    
    public void populate(SelectEvent events) {
        
        hrLuInsurances = null;
        hrLuInsurances = (HrLuInsurances) events.getObject();
        renderPnlManPage = false;
        renderPnlCreateAdditional = true;
        createOrSearchBundle = "Search";
        update = 1;
        saveOrUpdateButton = "update";
    }
    
    public void populate2(SelectEvent events) {
        hrInsuranceInjuredEmployee = null;
        hrInsuranceInjuredEmployee = (HrInsuranceInjuredEmployee) events.getObject();
        hrInsuranceInjuredEmployee.setId(hrInsuranceInjuredEmployee.getId());
        hrInsuranceInjuredEmployee = insuranceInjuredEmployeesLocal.getSelectedRequest(hrInsuranceInjuredEmployee.getId());
        hrInsurance = hrInsuranceInjuredEmployee.getInsuranceId();
        hrInsuranceInjuredEmployee.setInsuranceId(hrInsuranceInjuredEmployee.getInsuranceId());
        if (hrInsuranceInjuredEmployee.getType().equalsIgnoreCase("Permanent/contract")) {
            choosePermanet = "true";
            chooseDailyLabour = "false";
            hrEmployees = hrInsuranceInjuredEmployee.getEmpId();
            hrJobTypes = hrEmployees.getJobId();
            hrDepartments = hrEmployees.getDeptId();
        } else {
            choosePermanet = "false";
            chooseDailyLabour = "true";
        }
        renderPnlManPage = false;
        renderPnlCreateAdditional = true;
        createOrSearchBundle = "Search";
        update = 1;
        saveOrUpdateButton = "update";
        lockInput = true;
    }
    
    public void populateinsuranceprovider(SelectEvent events) {
        hrInsurance = null;
        hrInsurance = (HrInsurance) events.getObject();
        hrInsurance = hrinsurancebeanLocal.getSelectedPayment(hrInsurance.getId());
        hrLuInsurances = hrInsurance.getInsuranceId();
        HrLuInsuranceBrancheslist = insurancebeanLocal.findbyID(hrLuInsurances);
        hrLuInsuranceBranches = hrInsurance.getBranchId();
        renderPnlManPage = false;
        renderPnlCreateAdditional = true;
        createOrSearchBundle = "Search";
        update = 1;
        saveOrUpdateButton = "update";
        
    }
    
    public void createNewAdditionalAmount() {
        
        saveorUpdateBundle = "Create";
        disableBtnCreate = false;
        if (createOrSearchBundle.equals("New")) {
            renderPnlCreateAdditional = true;
            renderPnlManPage = false;
            createOrSearchBundle = "Search";
            setIcone("ui-icon-search");
        } else if (createOrSearchBundle.equals("Search")) {
            renderPnlCreateAdditional = false;
            renderPnlManPage = true;
            createOrSearchBundle = "New";
            setIcone("ui-icon-plus");
        }
    }
    
    public void SAVEINSURANCECOMPANY() {
        try {
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(SessionBean.getUserName(), "SAVEINSURANCECOMPANY", dataset)) {
                boolean result = hrinsurancebeanLocal.searchdup(hrInsurance);
                if (update == 0 && result == false) {
                    try {
                        hrinsurancebeanLocal.saveorupdate(hrInsurance);
                        JsfUtil.addSuccessMessage("Saved Successfuly.");
                        hrInsurance = null;
                    } catch (Exception ex) {
                        JsfUtil.addFatalMessage("Something Error Occured on Data created");
                    }
                } else if (update == 0 && result == true) {
                    JsfUtil.addFatalMessage("data already exist.");
                } else {
                    if (update == 1) {
                        try {
                            hrinsurancebeanLocal.saveorupdate(hrInsurance);
                            JsfUtil.addSuccessMessage("data updated successfully");
                            hrInsurance = null;
                        } catch (Exception e) {
                            JsfUtil.addFatalMessage("Duplicate data exists");
                            
                        }
                    }
                }
                
            } else {
                JsfUtil.addFatalMessage("You don't have appropriate permission, please contact the system administrator.");
                System.out.println("NOT GET IN TO IF================");
                EventEntry eventEntry = new EventEntry();
                eventEntry.setSessionId(SessionBean.getSessionID());
                eventEntry.setUserId(SessionBean.getUserId());
                QName qualifiedName = new QName("", "project");
                JAXBElement<String> test = new JAXBElement<String>(qualifiedName, String.class, null, SessionBean.getUserName());
                eventEntry.setUserLogin(test);
                security.addEventLog(eventEntry, dataset);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public void vcl_filiterByCategory(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            hrLuInsurances = (HrLuInsurances) event.getNewValue();
            HrLuInsuranceBrancheslist = insurancebeanLocal.findbyID(hrLuInsurances);
            
        }
    }
    
    public void vcl_filiterByCategory11(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            hrInsurance = (HrInsurance) event.getNewValue();
            
        }
    }
    
    public void vcl_filiterByCategory1(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            hrLuInsuranceBranches = (HrLuInsuranceBranches) event.getNewValue();
            
        }
    }
    
    List<HrLuInsurances> hrLuInsuranceslist = new ArrayList<>();
    List<HrLuInsuranceBranches> HrLuInsuranceBrancheslist = new ArrayList<>();
    List<HrInsurance> hrInsurancelist = new ArrayList<>();
    List<HrInsuranceInjuredEmployee> hrInsuranceenjuredlist = new ArrayList<>();
    
    public List<HrInsuranceInjuredEmployee> getHrInsuranceenjuredlist() {
        return hrInsuranceenjuredlist;
    }
    
    public void setHrInsuranceenjuredlist(List<HrInsuranceInjuredEmployee> hrInsuranceenjuredlist) {
        this.hrInsuranceenjuredlist = hrInsuranceenjuredlist;
    }
    
    public List<HrInsurance> getHrInsurancelist() {
        return hrInsurancelist;
    }
    
    public void setHrInsurancelist(List<HrInsurance> hrInsurancelist) {
        this.hrInsurancelist = hrInsurancelist;
    }
    
    public List<HrLuInsurances> getHrLuInsuranceslist() {
        return hrLuInsuranceslist;
    }
    
    public void setHrLuInsuranceslist(List<HrLuInsurances> hrLuInsuranceslist) {
        this.hrLuInsuranceslist = hrLuInsuranceslist;
    }
    
    public List<HrLuInsuranceBranches> getHrLuInsuranceBrancheslist() {
        return HrLuInsuranceBrancheslist;
    }
    
    public void setHrLuInsuranceBrancheslist(List<HrLuInsuranceBranches> HrLuInsuranceBrancheslist) {
        this.HrLuInsuranceBrancheslist = HrLuInsuranceBrancheslist;
    }
    
    public void savelookinsurance2() {
        insuranceBranchBeanLocal.saveorupdate(hrLuInsuranceBranches);
        
        JsfUtil.addSuccessMessage("saved successfully");
        hrLuInsuranceBranches = null;
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
    
    public HrEmployeeBeanLocal getHrEmployeesBeanLocal() {
        return hrEmployeesBeanLocal;
    }
    
    public void setHrEmployeesBeanLocal(HrEmployeeBeanLocal hrEmployeesBeanLocal) {
        this.hrEmployeesBeanLocal = hrEmployeesBeanLocal;
    }
    
    public HrJobTypes getHrJobTypes() {
        if (hrJobTypes == null) {
            hrJobTypes = new HrJobTypes();
        }
        return hrJobTypes;
    }
    
    public void setHrJobTypes(HrJobTypes hrJobTypes) {
        this.hrJobTypes = hrJobTypes;
    }
    
    public HrLuGrades getHrLuGrades() {
        if (hrLuGrades == null) {
            hrLuGrades = new HrLuGrades();
        }
        return hrLuGrades;
    }
    
    public void setHrLuGrades(HrLuGrades hrLuGrades) {
        this.hrLuGrades = hrLuGrades;
    }
    
    public void reset1() {
        hrLuInsuranceBranches = null;
        hrLuInsuranceBranches = new HrLuInsuranceBranches();
    }
    
    public void reset2() {
        hrLuInsurances = null;
        hrLuInsurances = new HrLuInsurances();
    }
    
    public ArrayList<HrEmployees> searchEmployeeByName(String hrEmployee) {
        ArrayList<HrEmployees> employee = null;
        hrEmployees.setFirstName(hrEmployee);
        employee = hrEmployeesBeanLocal.searchEmployeeByName(hrEmployees);
        return employee;
    }
    
    public ArrayList<HrEmployees> searchEmployeeById(String EmpId) {
        ArrayList<HrEmployees> employee = null;
        hrEmployees.setEmpId(EmpId);
        employee = hrEmployeesBeanLocal.searchEmployeeById(hrEmployees);
        return employee;
    }
    
    public void getByFirstName(SelectEvent event) {
        try {
            hrEmployees.setFirstName(event.getObject().toString());
            hrEmployees = hrEmployeesBeanLocal.getByFirstName(hrEmployees);
            hrJobTypes = hrEmployees.getJobId();
            hrDepartments = hrEmployees.getDeptId();
            hrLuGrades = hrEmployees.getGradeId().getGradeId();
            hrInsuranceInjuredEmployee.setEmpId(hrEmployees);
        } catch (Exception ex) {
            System.err.println("Exception");
        }
    }
    
    public void getByEmpId(SelectEvent event) {
        try {
            hrEmployees.setEmpId(event.getObject().toString());
            hrEmployees = hrEmployeesBeanLocal.getByEmpId(hrEmployees);
            hrJobTypes = hrEmployees.getJobId();
            hrDepartments = hrEmployees.getDeptId();
            hrLuGrades = hrEmployees.getGradeId().getGradeId();
            hrInsuranceInjuredEmployee.setEmpId(hrEmployees);
        } catch (Exception ex) {
            System.err.println("Exception");
        }
    }
    
    DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
    String MessageText = "";
    
    public boolean isInformedDateAfterIngudyDate(String ingurydate, String Informeddate) {
        try {
            if (df.parse(DateFormatFromConvertor(StringDateManipulation.toDayInEc())).before(df.parse(ingurydate)) || df.parse(DateFormatFromConvertor(StringDateManipulation.toDayInEc())).before(df.parse(Informeddate))) {
                MessageText = "Make Sure Ingury date and date informed are in the past";
                return true;
            } else if ((df.parse(Informeddate).before(df.parse(ingurydate)))) {
                MessageText = "Date Informed Can't be Before Accident date";
                return true;
            } else {
                return false;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }
    
    private String DateFormatFromConvertor(String date) {
        String inputDate[] = date.split("-");
        String Outputdate = inputDate[2] + "/" + inputDate[1] + "/" + inputDate[0];
        return Outputdate;
    }
    
    public void saveInjuredEmployee() {
        try {
            if (isInformedDateAfterIngudyDate(hrInsuranceInjuredEmployee.getAccidentDate(), hrInsuranceInjuredEmployee.getAccidentInformedDate()) == true) {
                JsfUtil.addFatalMessage(MessageText);
            } else {
                AAA securityService = new AAA();
                IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
                String systemBundle = "et/gov/eep/commonApplications/securityServer";
                String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
                if (security.checkAccess(SessionBean.getUserName(), "saveInjuredEmployee", dataset)) {
                    if (update == 0) {
                        try {
                            String Shday[] = hrInsuranceInjuredEmployee.getAccidentDate().split("/");
                            int Ihday = Integer.parseInt(Shday[0]);
                            String Shmonth[] = hrInsuranceInjuredEmployee.getAccidentDate().split("/");
                            int Ihmonth = Integer.parseInt(Shmonth[1]);
                            String Shyear[] = hrInsuranceInjuredEmployee.getAccidentDate().split("/");
                            int Ihyear = Integer.parseInt(Shyear[2]);
                            String Scday[] = hrInsuranceInjuredEmployee.getAccidentInformedDate().split("/");
                            int Icday = Integer.parseInt(Scday[0]);
                            String Scmonth[] = hrInsuranceInjuredEmployee.getAccidentInformedDate().split("/");
                            int Icmonth = Integer.parseInt(Scmonth[1]);
                            String Scyear[] = hrInsuranceInjuredEmployee.getAccidentInformedDate().split("/");
                            int Icyear = Integer.parseInt(Scyear[2]);
                            int expday = (Icday - Ihday) + ((Icmonth - Ihmonth) * 30) + ((Icyear - Ihyear) * 365);
                            if (expday <= 1) {
                                hrInsuranceInjuredEmployee.setStatus("0");
                                insuranceInjuredEmployeesLocal.saveorupdate(hrInsuranceInjuredEmployee);
                                JsfUtil.addSuccessMessage("saved successfully");
                            } else if (expday > 1) {
                                JsfUtil.addFatalMessage("difference of accident date and informed can not be > 24 hour");
                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                        
                    } else {
                        try {
                            String Shday[] = hrInsuranceInjuredEmployee.getAccidentDate().split("/");
                            int Ihday = Integer.parseInt(Shday[0]);
                            String Shmonth[] = hrInsuranceInjuredEmployee.getAccidentDate().split("/");
                            int Ihmonth = Integer.parseInt(Shmonth[1]);
                            String Shyear[] = hrInsuranceInjuredEmployee.getAccidentDate().split("/");
                            int Ihyear = Integer.parseInt(Shyear[2]);
                            String Scday[] = hrInsuranceInjuredEmployee.getAccidentInformedDate().split("/");
                            int Icday = Integer.parseInt(Scday[2]);
                            String Scmonth[] = hrInsuranceInjuredEmployee.getAccidentInformedDate().split("/");
                            int Icmonth = Integer.parseInt(Scmonth[1]);
                            String Scyear[] = hrInsuranceInjuredEmployee.getAccidentInformedDate().split("/");
                            int Icyear = Integer.parseInt(Scyear[0]);
                            int expday = (Icday - Ihday) + ((Icmonth - Ihmonth) * 30) + ((Icyear - Ihyear) * 365);
                            if (expday <= 1) {
                                hrInsuranceInjuredEmployee.setStatus("0");
                                insuranceInjuredEmployeesLocal.saveorupdate(hrInsuranceInjuredEmployee);
                                JsfUtil.addSuccessMessage("updated successfully");
                            } else if (expday > 1) {
                                JsfUtil.addFatalMessage("difference of accident date and informed can not be > 24 hour");
                            }
                            
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                    hrInsuranceInjuredEmployee = null;
                    hrEmployees = null;
                    hrJobTypes = null;
                    hrDepartments = null;
                    
                } else {
                    JsfUtil.addFatalMessage("You don't have appropriate permission, please contact the system administrator.");
                    EventEntry eventEntry = new EventEntry();
                    eventEntry.setSessionId(SessionBean.getSessionID());
                    eventEntry.setUserId(SessionBean.getUserId());
                    QName qualifiedName = new QName("", "project");
                    JAXBElement<String> test = new JAXBElement<String>(qualifiedName, String.class, null, SessionBean.getUserName());
                    eventEntry.setUserLogin(test);
                    security.addEventLog(eventEntry, dataset);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
    }
    
    public void resetInjuredEmployee() {
        hrInsuranceInjuredEmployee = null;
        hrInsuranceInjuredEmployee = new HrInsuranceInjuredEmployee();
        hrEmployees = null;
        hrJobTypes = null;
        hrDepartments = null;
    }
    
    public ArrayList<HrInsurance> searchByTerminationName(String telephone) {
        ArrayList<HrInsurance> insurancepro = null;
        hrInsurance.setTelephone(telephone);
        insurancepro = hrinsurancebeanLocal.telephone(hrInsurance);
        saveOrUpdateButton = "Update";
        update = 1;
        return insurancepro;
    }
    
    public void getByTelName(SelectEvent event) {
        try {
            hrInsurance.setTelephone(event.getObject().toString());
            hrInsurance = hrinsurancebeanLocal.getByTerminationName(hrInsurance);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public void findByall() {
        try {
            hrInsuranceenjuredlist = insuranceInjuredEmployeesLocal.findbyname(hrInsuranceInjuredEmployee);
            hrinsuranceinjuredmodel = new ListDataModel(new ArrayList(hrInsuranceenjuredlist));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
    }
    
    public void searchBytype(ValueChangeEvent event) {
        hrInsuranceenjuredlist = insuranceInjuredEmployeesLocal.findtype(hrInsuranceInjuredEmployee);
    }
    
    public void selectedParamChangeEvent(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            columnNameResolver.setCol_Name_FromTable(event.getNewValue().toString());
            columnNameResolver.setParsed_Col_Name(ColumnParser(event.getNewValue().toString().toLowerCase() + ":"));
            //hrJobTypes.setCol_Value(columnNameResolver.getParsed_Col_Name());
        }
    }
    
    public String ColumnParser(String col_name) {
        String col = col_name.replace("_", " ");
        System.out.println("col==" + col);
        return col;
    }
    
    public void searchAllInjuredEmployee() {
        if (hrInsuranceInjuredEmployee.getCol_Value()==null || columnNameResolver.getCol_Name_FromTable()==null) {
            JsfUtil.addFatalMessage("Please Fill the Search parameters properly");
        } else  {
            hrInsuranceenjuredlist = insuranceInjuredEmployeesLocal.findInjuredEmployee(hrInsuranceInjuredEmployee,columnNameResolver.getCol_Name_FromTable());
            hrinsuranceinjuredmodel = new ListDataModel(new ArrayList(hrInsuranceenjuredlist));
        }
    }
    
    public void resetInsuranceCompany() {
        hrInsurance = null;
        hrInsurance = new HrInsurance();
        hrLuInsurances = null;
        hrLuInsurances = new HrLuInsurances();
        hrLuInsuranceBranches = null;
        hrLuInsuranceBranches = new HrLuInsuranceBranches();
        hrInsurance.setStatus(1);
        
    }
    
    public ArrayList<HrLuInsurances> searchByInsuranceName(String insurancename) {
        ArrayList<HrLuInsurances> insurancepro = null;
        hrLuInsurances.setInsuranceName(insurancename);
        insurancepro = insurancebeanLocal.insurancename(hrLuInsurances);
        return insurancepro;
    }
    
    public ArrayList<HrInsurance> ddchByTerminationName(String telephone) {
        ArrayList<HrInsurance> insurancepro = null;
        hrInsurance.setTelephone(telephone);
        insurancepro = hrinsurancebeanLocal.telephone(hrInsurance);
        saveOrUpdateButton = "Update";
        update = 1;
        return insurancepro;
    }
    
    public List<SelectItem> getFilterByStatus() {
        return hrinsurancebeanLocal.filterByStatus();
    }
    
    public void setFilterByStatus(List<SelectItem> filterByStatus) {
        this.filterByStatus = filterByStatus;
    }
    
    public DataModel<HrInsurance> getInsuranceDataModel() {
        if (insuranceDataModel == null) {
            insuranceDataModel = new ArrayDataModel<>();
        }
        return insuranceDataModel;
    }
    
    public void setInsuranceDataModel(DataModel<HrInsurance> insuranceDataModel) {
        this.insuranceDataModel = insuranceDataModel;
    }
    
    public void filiterByStatus_VclInsuranceCompany(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            status = Integer.valueOf(event.getNewValue().toString());
            populateTable();
        }
    }
    
    private void populateTable() {
        try {
            List<HrInsurance> readFiltereddata = hrinsurancebeanLocal.loadFiltereddata(status);
            insuranceDataModel = new ListDataModel(readFiltereddata);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public void getByInsuranceName(SelectEvent event) {
        try {
            hrInsurance.setInsuranceId(hrLuInsurances);
            hrInsurance = hrinsurancebeanLocal.getByInsuranceName(hrLuInsurances);
            saveOrUpdateButton = "Update";
            update = 1;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public void SearchActiveProvider() {
        Integer Status = 1;
        hrInsurancelist = hrinsurancebeanLocal.ActiveLists(Status);
    }
//</editor-fold>
}
