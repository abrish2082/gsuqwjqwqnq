/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.controller.InternaExperience;

import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.commonApplications.utility.date.StringDateManipulation;
import et.gov.eep.hrms.businessLogic.InternalExperience.InternalExperienceBeanLocal;
import et.gov.eep.hrms.businessLogic.employee.HrEmployeeBeanLocal;
import et.gov.eep.hrms.businessLogic.organization.HrDepartmentsBeanLocal;
import et.gov.eep.hrms.businessLogic.organization.HrJobTypesBeanLocal;
import et.gov.eep.hrms.entity.employee.HrEmployees;
import et.gov.eep.hrms.entity.lookup.HrLuGrades;
import et.gov.eep.hrms.entity.lookup.HrLuSalarySteps;
import et.gov.eep.hrms.entity.organization.HrDepartments;
import et.gov.eep.hrms.entity.organization.HrJobTypes;
import et.gov.eep.hrms.entity.organization.HrSalaryScaleRanges;
import et.gov.eep.hrms.entity.organization.HrSalaryScales;
import et.gov.eep.hrms.entity.transfer.HrEmpInternalHistory;
//import et.gov.eep.hrms.mapper.InternalExperience.HrEmpInternalHistoryFacade;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.ArrayDataModel;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author meles
 */
@Named(value = "internalExperienceController")
@ViewScoped
public class InternalExperienceController implements Serializable {

    /**
     * Creates a new Injection
     */
    
    //<editor-fold defaultstate="collapsed" desc="EJB Declaration">
    @EJB
    private HrEmployeeBeanLocal hrEmployeeBean;
    @EJB
            InternalExperienceBeanLocal internalExperienceBeanLocal;
    @EJB
            HrDepartmentsBeanLocal hrDepartmentsBeanLocal;
    @EJB
            HrJobTypesBeanLocal hrJobTypesBeanLocal;
    @Inject
            HrEmployees hrEmployees;
    @Inject
            HrJobTypes hrJobTypes;
    @Inject
            HrDepartments hrDepartments;
    @Inject
            HrEmpInternalHistory hrEmpInternalHistory;
    @Inject
            HrLuSalarySteps hrLuSalarySteps;
    @Inject
            HrSalaryScaleRanges hrSalaryScaleRanges;
    @Inject
            HrLuGrades hrLuGrades;
    @Inject
            HrSalaryScales hrSalaryScales;
    HrEmpInternalHistory hrEmpInternalHistoryl = new HrEmpInternalHistory();
//</editor-fold>
  
    //<editor-fold defaultstate="collapsed" desc="Variable declaration">
    String serviceYear;
    boolean renderForGraphics = true;
    boolean renderForGraphicsDb = false;
    private boolean renderPnlCreateGatePass = false;
    private boolean disableBtnCreate;
    private String saveorUpdateBundle = "Create";
    private String createOrSearchBundle = "New";
    private boolean renderPnlCreateAdditional = false;
    private boolean position = false;
    private boolean renderPnlManPage = true;
    private String icone = "ui-icon-plus";
    private String activeIndex;
    private String tab = "disabled";
    private boolean renderPnlApproved = false;
    private boolean checkBoxReqst = false;
    private boolean checkBoxApprove = false;
    private String addorUpdate = "Add";
    private String saveOrUpdateButton = "Save";
    int update = 0;
    int status = 1;
    private ArrayList<SelectItem> listOfmovementtype = new ArrayList<>();
    List<HrEmpInternalHistory> hrEmpInternalHistoryList = new ArrayList<>();
    DataModel<HrEmpInternalHistory> hrEmpInternalHistoryDataModel;
    List<HrEmpInternalHistory> hrEmpInternaltemporaryList = new ArrayList<>();
     Double empSalary;
//</editor-fold>
  
    //<editor-fold defaultstate="collapsed" desc="post construct">
    @PostConstruct
    public void init() {
        setListOfmovementtype(listOfMovement());
    }
//</editor-fold> 
    
    //<editor-fold defaultstate="collapsed" desc="getter and setter">
    
    public Double getEmpSalary() {
        return empSalary;
    }
    
    public void setEmpSalary(Double empSalary) {
        this.empSalary = empSalary;
    }
    
    public HrSalaryScales getHrSalaryScales() {
        if (hrSalaryScales == null) {
            hrSalaryScales = new HrSalaryScales();
        }
        return hrSalaryScales;
    }
    
    public void setHrSalaryScales(HrSalaryScales hrSalaryScales) {
        this.hrSalaryScales = hrSalaryScales;
    }
    public HrLuSalarySteps getHrLuSalarySteps() {
        if (hrLuSalarySteps == null) {
            hrLuSalarySteps = new HrLuSalarySteps();
        }
        return hrLuSalarySteps;
    }
    
    public void setHrLuSalarySteps(HrLuSalarySteps hrLuSalarySteps) {
        this.hrLuSalarySteps = hrLuSalarySteps;
    }
    
    public List<HrEmpInternalHistory> getHrEmpInternalHistoryList() {
        return hrEmpInternalHistoryList;
    }
    
    public void setHrEmpInternalHistoryList(List<HrEmpInternalHistory> hrEmpInternalHistoryList) {
        this.hrEmpInternalHistoryList = hrEmpInternalHistoryList;
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
    
    public HrJobTypes getHrJobTypes() {
        if (hrJobTypes == null) {
            hrJobTypes = new HrJobTypes();
        }
        return hrJobTypes;
    }
    
    public void setHrJobTypes(HrJobTypes hrJobTypes) {
        this.hrJobTypes = hrJobTypes;
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
    
    public HrEmpInternalHistory getHrEmpInternalHistory() {
        if (hrEmpInternalHistory == null) {
            hrEmpInternalHistory = new HrEmpInternalHistory();
        }
        return hrEmpInternalHistory;
    }
    
    public boolean isRenderForGraphics() {
        return renderForGraphics;
    }
    
    public void setRenderForGraphics(boolean renderForGraphics) {
        this.renderForGraphics = renderForGraphics;
    }
    
    public boolean isRenderForGraphicsDb() {
        return renderForGraphicsDb;
    }
    
    public void setRenderForGraphicsDb(boolean renderForGraphicsDb) {
        this.renderForGraphicsDb = renderForGraphicsDb;
    }
    
    public void setHrEmpInternalHistory(HrEmpInternalHistory hrEmpInternalHistory) {
        this.hrEmpInternalHistory = hrEmpInternalHistory;
    }
    
    public boolean isRenderPnlCreateGatePass() {
        return renderPnlCreateGatePass;
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
    
    public boolean isPosition() {
        return position;
    }
    
    public void setPosition(boolean position) {
        this.position = position;
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
    
    public String getTab() {
        return tab;
    }
    
    public void setTab(String tab) {
        this.tab = tab;
    }
    
    public boolean isRenderPnlApproved() {
        return renderPnlApproved;
    }
    
    public void setRenderPnlApproved(boolean renderPnlApproved) {
        this.renderPnlApproved = renderPnlApproved;
    }
    
    public boolean isCheckBoxReqst() {
        return checkBoxReqst;
    }
    
    public void setCheckBoxReqst(boolean checkBoxReqst) {
        this.checkBoxReqst = checkBoxReqst;
    }
    
    public boolean isCheckBoxApprove() {
        return checkBoxApprove;
    }
    
    public void setCheckBoxApprove(boolean checkBoxApprove) {
        this.checkBoxApprove = checkBoxApprove;
    }
    
    public String getAddorUpdate() {
        return addorUpdate;
    }
    
    public void setAddorUpdate(String addorUpdate) {
        this.addorUpdate = addorUpdate;
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
    
    public String getServiceYear() {
        return serviceYear;
    }
    
    public void setServiceYear(String serviceYear) {
        this.serviceYear = serviceYear;
    }
    
    public ArrayList<SelectItem> getListOfmovementtype() {
        return listOfmovementtype;
    }
    
    public void setListOfmovementtype(ArrayList<SelectItem> listOfmovementtype) {
        this.listOfmovementtype = listOfmovementtype;
    }
    
    public DataModel<HrEmpInternalHistory> getHrEmpInternalHistoryDataModel() {
        if (hrEmpInternalHistoryDataModel == null) {
            hrEmpInternalHistoryDataModel = new ArrayDataModel<>();
        }
        return hrEmpInternalHistoryDataModel = new ListDataModel(new ArrayList(hrEmpInternalHistoryList));
    }
    
    public void setHrEmpInternalHistoryDataModel(DataModel<HrEmpInternalHistory> hrEmpInternalHistoryDataModel) {
        this.hrEmpInternalHistoryDataModel = hrEmpInternalHistoryDataModel;
    }
    
    public HrEmpInternalHistory getHrEmpInternalHistoryl() {
        if (hrEmpInternalHistoryl == null) {
            hrEmpInternalHistoryl = new HrEmpInternalHistory();
        }
        return hrEmpInternalHistoryl;
    }
    
    public void setHrEmpInternalHistoryl(HrEmpInternalHistory hrEmpInternalHistoryl) {
        this.hrEmpInternalHistoryl = hrEmpInternalHistoryl;
    }
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Main Methods ">
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
    
    public void saveInternalExperience() {
        System.out.println("inside save");
        internalExperienceBeanLocal.save(hrEmpInternalHistory);
        System.out.println("hrEmpInternalHistory" + hrEmpInternalHistory);
        JsfUtil.addSuccessMessage("saved successfully");
        String empID = hrEmployees.getEmpId();
        hrEmpInternalHistoryList = internalExperienceBeanLocal.findByEmpId(hrEmpInternalHistory, empID);
        hrEmpInternalHistory = null;
    }
    
    public void resetInternalExperience() {
        hrEmpInternalHistory = null;
    }
    
    public ArrayList<HrEmployees> searchByEmpId(String empId) {
        try {
            ArrayList<HrEmployees> employeeId = null;
            hrEmployees.setEmpId(empId);
            employeeId = hrEmployeeBean.SearchByEmpId(hrEmployees);
            return employeeId;
        } catch (Exception ex) {
            ex.printStackTrace();
            System.err.println("Exception");
        }
        return null;
    }
    
    public ArrayList<HrDepartments> searchByDept(String deptId) {
        try {
            ArrayList<HrDepartments> hrDepartmentsearch = null;
            hrDepartments.setDepName(deptId);
            hrDepartmentsearch = hrDepartmentsBeanLocal.SearchByDeptId(hrDepartments);
            return hrDepartmentsearch;
        } catch (Exception ex) {
            ex.printStackTrace();
            System.err.println("Exception");
        }
        return null;
    }
    
    public ArrayList<HrJobTypes> searchByJobCode(String jobCode) {
        try {
            ArrayList<HrJobTypes> hrjobtypessearch = null;
            hrJobTypes.setJobCode(jobCode);
            hrjobtypessearch = hrJobTypesBeanLocal.searchByJobCode(hrJobTypes);
            return hrjobtypessearch;
        } catch (Exception ex) {
            ex.printStackTrace();
            System.err.println("Exception");
        }
        return null;
    }
    
    public void getByJobcode(SelectEvent event) {
        try {
            if (event.getObject() != null) {
                hrJobTypes = (HrJobTypes) event.getObject();
                hrEmpInternalHistory.setOldJobId(hrJobTypes.getId());
                hrEmpInternalHistory.setOldJobName(hrJobTypes.getJobTitle());
                hrSalaryScaleRanges = hrJobTypes.getJobGradeId();
                hrLuGrades = hrSalaryScaleRanges.getGradeId();
                hrEmpInternalHistory.setOldGrade(hrLuGrades.getGrade());
                //hrDepartments = hrEmployees.getDeptId();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            System.err.println("Exception");
        }
    }
    
    public void getByDeptId(SelectEvent event) {
        try {
            if (event.getObject() != null) {
                hrDepartments = (HrDepartments) event.getObject();
                hrEmpInternalHistory.setOldDepId(hrDepartments.getDepId());
                hrEmpInternalHistory.setOldDepName(hrDepartments.getDepName());
                //hrDepartments = hrEmployees.getDeptId();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            System.err.println("Exception");
        }
    }
    
    
    
    public void vcl_salaryStep(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            String salStep = event.getNewValue().toString();
            if (salStep.equalsIgnoreCase("0")) {
                hrEmpInternalHistory.setOldSalary(Double.parseDouble(salStep));
                hrEmpInternalHistory.setOldSalaryStep(null);
            } else if (salStep.equalsIgnoreCase("-1")) {
                hrEmpInternalHistory.setOldSalary(hrSalaryScaleRanges.getMaxSalary());
                hrEmpInternalHistory.setOldSalaryStep(null);
            } else if (salStep.equalsIgnoreCase("-2")) {
                hrEmpInternalHistory.setOldSalary(hrSalaryScaleRanges.getMinSalary());
                hrEmpInternalHistory.setOldSalaryStep(null);
            } else {
                hrLuSalarySteps.setSalaryStep(Integer.parseInt(event.getNewValue().toString()));
                hrLuSalarySteps = hrEmployeeBean.searchHrLuSalaryStepsInfo(hrLuSalarySteps);
                hrSalaryScales.setSalaryRangeId(hrSalaryScaleRanges);
                hrSalaryScales.setSalaryStepId(hrLuSalarySteps);
                hrSalaryScales = hrEmployeeBean.checkStepIdRep(hrSalaryScaleRanges, hrLuSalarySteps);
                hrEmpInternalHistory.setOldSalaryStep(hrLuSalarySteps.getSalaryStep().toString());
                setEmpSalary(hrSalaryScales.getSalary());
                hrEmpInternalHistory.setOldSalary(empSalary);
            }
        } else {
            JsfUtil.addErrorMessage("Please Select Salary Step");
        }
    }
    
    public void getByEmpId(SelectEvent event) {
        try {
            if (event.getObject() != null) {
                hrEmployees = (HrEmployees) event.getObject();
                hrDepartments = hrEmployees.getDeptId();
                hrJobTypes = hrEmployees.getJobId();
                hrEmpInternalHistory.setEmpId(hrEmployees);
                hrSalaryScaleRanges = hrEmployees.getGradeId();
                String empID = hrEmployees.getEmpId();
                System.out.println("EMPLOYEE ID=====" + empID);
                hrEmpInternalHistoryList = internalExperienceBeanLocal.findByEmpId(hrEmpInternalHistory, empID);
                System.out.println("hrEmpInternalHistoryList==================" + hrEmpInternalHistoryList);
                if (hrEmpInternalHistoryList == null) {
                    hrEmpInternalHistoryList = new ArrayList<>();
                }
                // hrLuGrades = hrSalaryScaleRanges.getGradeId();
                hrLuSalarySteps = hrEmployees.getSalaryStep();
                if (hrEmployees.getPhoto() != null) {
                    renderForGraphics = false;
                    renderForGraphicsDb = true;
                } else {
                    renderForGraphics = true;
                    renderForGraphicsDb = false;
                }
                String Shday[] = hrEmployees.getHireDate().split("/");
                int Ihday = Integer.parseInt(Shday[0]);
                String Shmonth[] = hrEmployees.getHireDate().split("/");
                int Ihmonth = Integer.parseInt(Shmonth[1]);
                String Shyear[] = hrEmployees.getHireDate().split("/");
                int Ihyear = Integer.parseInt(Shyear[2]);
                String Scday[] = StringDateManipulation.toDayInEc().split("-");
                int Icday = Integer.parseInt(Scday[2]);
                String Scmonth[] = StringDateManipulation.toDayInEc().split("-");
                int Icmonth = Integer.parseInt(Scmonth[1]);
                String Scyear[] = StringDateManipulation.toDayInEc().split("-");
                int Icyear = Integer.parseInt(Scyear[0]);
                int expday = ((Icday - Ihday) + ((Icmonth - Ihmonth) * 30) + ((Icyear - Ihyear) * 365));
                int expInYear = expday / 365;
                int expInMonth = ((expday % 365) / 30);
                int expddays = (((expday % 365) / 30) % 30);
                serviceYear = (expInYear + "year-" + expInMonth + "-month " + expddays + "days");
                
            }
            System.out.println("hrEmpInternalHistory employee id" + hrEmpInternalHistory.getEmpId());
        } catch (Exception ex) {
            ex.printStackTrace();
            System.err.println("Exception");
        }
    }
    
    public ArrayList<SelectItem> listOfMovement() {
        ArrayList<SelectItem> listOfMovement = new ArrayList<>();
        listOfMovement.add(new SelectItem(null, "-- Select --"));
        listOfMovement.add(new SelectItem("new placement", "new placement"));
        listOfMovement.add(new SelectItem("Promotion", "Promotion"));
        listOfMovement.add(new SelectItem("Transfer", "Transfer"));
        listOfMovement.add(new SelectItem("Acting", "Acting"));
        listOfMovement.add(new SelectItem("Salary scale increament", "Salary scale increament"));
        listOfMovement.add(new SelectItem("Job rotation", "Job rotation"));
        listOfMovement.add(new SelectItem("Assignment", "Assignment"));
        listOfMovement.add(new SelectItem("Change on salary scale", "Change on salary scale"));
        listOfMovement.add(new SelectItem("probation period", "probation period"));
        listOfMovement.add(new SelectItem("Displanery case", "Displanery case"));
        return listOfMovement;
    }
    
    public void handleMovementType(ValueChangeEvent event) {
        
        try {
            if (event.getNewValue().toString() != null) {
                hrEmpInternalHistory.setProcessType(event.getNewValue().toString());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
    }
    
    public ArrayList<HrSalaryScales> getSalarySetps() {
        ArrayList<HrSalaryScales> salarySteps = null;
        if (hrDepartments != null && hrJobTypes != null) {
            salarySteps = new ArrayList<>();
            if (hrJobTypes.getJobGradeId() != null) {
                hrSalaryScaleRanges = hrJobTypes.getJobGradeId();
                salarySteps = hrEmployeeBean.searchSalaryStepsInfo(hrSalaryScaleRanges);
            }
        }
        return salarySteps;
    }
    
    public void selectedhistory(SelectEvent event) {
        hrEmpInternalHistory = (HrEmpInternalHistory) event.getObject();
        hrEmployees = hrEmpInternalHistory.getEmpId();
        Integer jobId = hrEmpInternalHistory.getOldJobId();
        Integer deptId = hrEmpInternalHistory.getOldDepId();
//        Integer salStepId=Integer.valueOf(hrEmpInternalHistory.getOldSalaryStep());
        hrJobTypes = hrJobTypesBeanLocal.findBYJobId(jobId);
        hrDepartments = hrDepartmentsBeanLocal.findByDeptId(deptId);
    }
    public HrSalaryScaleRanges getHrSalaryScaleRanges() {
        if (hrSalaryScaleRanges == null) {
            hrSalaryScaleRanges = new HrSalaryScaleRanges();
        }
        return hrSalaryScaleRanges;
    }
    
    public void setHrSalaryScaleRanges(HrSalaryScaleRanges hrSalaryScaleRanges) {
        this.hrSalaryScaleRanges = hrSalaryScaleRanges;
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
    
    /**
     * Creates a new instance of InternalExperienceController
     */
    public InternalExperienceController() {
    }
//</editor-fold>

}
