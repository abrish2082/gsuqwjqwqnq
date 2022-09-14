/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.controller.succession;
//<editor-fold defaultstate="collapsed" desc="Import">

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import org.primefaces.event.SelectEvent;
import securityBean.Constants;
import securityBean.SessionBean;
import webService.AAA;
import webService.EventEntry;
import webService.IAdministration;
import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.commonApplications.utility.date.StringDateManipulation;
import et.gov.eep.hrms.businessLogic.address.HrAddressesBeanLocal;
import et.gov.eep.hrms.businessLogic.employee.HrEmployeeBeanLocal;
import et.gov.eep.hrms.businessLogic.succession.successionBeanLocal;
import et.gov.eep.hrms.businessLogic.succession.SuccessorBeanLocal;
import et.gov.eep.hrms.entity.employee.HrEmpAddresses;
import et.gov.eep.hrms.entity.employee.HrEmployees;
import et.gov.eep.hrms.entity.lookup.HrLuGrades;
import et.gov.eep.hrms.entity.organization.HrDeptJobs;
import et.gov.eep.hrms.entity.organization.HrJobTypes;
import et.gov.eep.hrms.entity.succession.HrSmKmp;
import et.gov.eep.hrms.entity.succession.HrSmSuccessorEvaluation;
//|</editor-fold>

@Named("SuccessorController")
@ViewScoped
public class SuccessorController implements Serializable {

    //<editor-fold defaultstate="collapsed" desc="Entity, business logic & Variables">
    @Inject
    HrSmKmp hrSmKmp;

    @EJB
    successionBeanLocal successionBeanLocal;

    @EJB
    HrAddressesBeanLocal HrAddressesBeanLocal;

    @Inject
    HrSmSuccessorEvaluation hrSmSuccessorEvaluation;

    @Inject
    HrLuGrades hrLuGrades;

    @Inject
    HrDeptJobs hrDeptJobs;

    @Inject
    HrEmpAddresses hrEmpAddresses;

    @EJB
    SuccessorBeanLocal successorBeanLocal;

    @EJB
    HrEmployeeBeanLocal hrEmployeeBeanLocal;

    @Inject
    HrEmployees hrEmployees;

    @Inject
    HrJobTypes hrJobTypes;

    @Inject
    SessionBean sessionBeanLocal;

    @EJB
    HrEmployeeBeanLocal hrEmployeesBeanLocal;

    private String tabToggle = "";
    int update = 0;
    private boolean renderPnlCreateAdditional = true;
    private String addorUpdate = "Add";
    private String SaveOrUpdateButton = "Save";
    boolean btnaddvisibility = true;
    private String headerTitle = "Search....";
    private String saveorUpdateBundle = "Update";
    private String icone = "ui-icon-plus";
    private String createOrSearchBundle = "New";
    private boolean disableBtnCreate;
    private boolean renderPnlCreateGatePass = false;
    private boolean renderPnlManPage = true;
    private boolean btnNewRender = false;
    private String renderpnlContrat = "false";
    List<HrSmKmp> jobTitelList = new ArrayList<>();
    List<HrSmSuccessorEvaluation> hrSmSuccessorEvaluationList = new ArrayList<>();
    DataModel<HrSmSuccessorEvaluation> successorEvaluationDataModel;
    private String disableEmpID = "false";
    private String disableEmpName = "true";
    String selected = "Employee ID";
    String Age;

    //</editor-fold>
    @PostConstruct
    public void init() {
        jobTitelList = successionBeanLocal.findAll();
    }

    // <editor-fold defaultstate="collapsed" desc="Getters & setters"> 
    public successionBeanLocal getSuccessionBeanLocal() {
        return successionBeanLocal;
    }

    public void setSuccessionBeanLocal(successionBeanLocal successionBeanLocal) {
        this.successionBeanLocal = successionBeanLocal;
    }

    public HrDeptJobs getHrDeptJobs() {
        return hrDeptJobs;
    }

    public void setHrDeptJobs(HrDeptJobs hrDeptJobs) {
        this.hrDeptJobs = hrDeptJobs;
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

    public SuccessorBeanLocal getSuccessorBeanLocal() {
        return successorBeanLocal;
    }

    public void setSuccessorBeanLocal(SuccessorBeanLocal successorBeanLocal) {
        this.successorBeanLocal = successorBeanLocal;
    }

    public HrEmployeeBeanLocal getHrEmployeeBeanLocal() {
        return hrEmployeeBeanLocal;
    }

    public void setHrEmployeeBeanLocal(HrEmployeeBeanLocal hrEmployeeBeanLocal) {
        this.hrEmployeeBeanLocal = hrEmployeeBeanLocal;
    }

    public HrEmployeeBeanLocal getHrEmployeesBeanLocal() {
        return hrEmployeesBeanLocal;
    }

    public void setHrEmployeesBeanLocal(HrEmployeeBeanLocal hrEmployeesBeanLocal) {
        this.hrEmployeesBeanLocal = hrEmployeesBeanLocal;
    }

    public String getSaveorUpdateBundle() {
        return saveorUpdateBundle;
    }

    public void setSaveorUpdateBundle(String saveorUpdateBundle) {
        this.saveorUpdateBundle = saveorUpdateBundle;
    }

    public String getTabToggle() {
        return tabToggle;
    }

    public void setTabToggle(String tabToggle) {
        this.tabToggle = tabToggle;
    }

    public int getUpdate() {
        return update;
    }

    public void setUpdate(int update) {
        this.update = update;
    }

    public String getAddorUpdate() {
        return addorUpdate;
    }

    public void setAddorUpdate(String addorUpdate) {
        this.addorUpdate = addorUpdate;
    }

    public boolean isRenderPnlCreateAdditional() {
        return renderPnlCreateAdditional;
    }

    public void setRenderPnlCreateAdditional(boolean renderPnlCreateAdditional) {
        this.renderPnlCreateAdditional = renderPnlCreateAdditional;
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

    public HrLuGrades getHrLuGrades() {
        return hrLuGrades;
    }

    public void setHrLuGrades(HrLuGrades hrLuGrades) {
        this.hrLuGrades = hrLuGrades;
    }

    public HrSmSuccessorEvaluation getHrSmSuccessorEvaluation() {
        if (hrSmSuccessorEvaluation == null) {
            hrSmSuccessorEvaluation = new HrSmSuccessorEvaluation();
        }
        return hrSmSuccessorEvaluation;
    }

    public void setHrSmSuccessorEvaluation(HrSmSuccessorEvaluation hrSmSuccessorEvaluation) {
        this.hrSmSuccessorEvaluation = hrSmSuccessorEvaluation;
    }

    public String getSaveOrUpdateButton() {
        return SaveOrUpdateButton;
    }

    public void setSaveOrUpdateButton(String SaveOrUpdateButton) {
        this.SaveOrUpdateButton = SaveOrUpdateButton;
    }

    public boolean isBtnaddvisibility() {
        return btnaddvisibility;
    }

    public void setBtnaddvisibility(boolean btnaddvisibility) {
        this.btnaddvisibility = btnaddvisibility;
    }

    public String getHeaderTitle() {
        return headerTitle;
    }

    public void setHeaderTitle(String headerTitle) {
        this.headerTitle = headerTitle;
    }

    public String getIcone() {
        return icone;
    }

    public void setIcone(String icone) {
        this.icone = icone;
    }

    public String getCreateOrSearchBundle() {
        return createOrSearchBundle;
    }

    public void setCreateOrSearchBundle(String createOrSearchBundle) {
        this.createOrSearchBundle = createOrSearchBundle;
    }

    public boolean isRenderPnlCreateGatePass() {
        return renderPnlCreateGatePass;
    }

    public void setRenderPnlCreateGatePass(boolean renderPnlCreateGatePass) {
        this.renderPnlCreateGatePass = renderPnlCreateGatePass;
    }

    public boolean isRenderPnlManPage() {
        return renderPnlManPage;
    }

    public void setRenderPnlManPage(boolean renderPnlManPage) {
        this.renderPnlManPage = renderPnlManPage;
    }

    public boolean isBtnNewRender() {
        return btnNewRender;
    }

    public void setBtnNewRender(boolean btnNewRender) {
        this.btnNewRender = btnNewRender;
    }

    public String getRenderpnlContrat() {
        return renderpnlContrat;
    }

    public void setRenderpnlContrat(String renderpnlContrat) {
        this.renderpnlContrat = renderpnlContrat;
    }

    public void fetchKMPreq() {
        successorBeanLocal.findAll();
    }

    public HrSmKmp getHrSmKmp() {
        if (hrSmKmp == null) {
            hrSmKmp = new HrSmKmp();

        }
        return hrSmKmp;
    }

    public void setHrSmKmp(HrSmKmp hrSmKmp) {
        this.hrSmKmp = hrSmKmp;
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

    public boolean isDisableBtnCreate() {
        return disableBtnCreate;
    }

    public void setDisableBtnCreate(boolean disableBtnCreate) {
        this.disableBtnCreate = disableBtnCreate;
    }

    public List<HrSmKmp> getJobTitelList() {
        return jobTitelList;
    }

    public void setJobTitelList(List<HrSmKmp> jobTitelList) {
        this.jobTitelList = jobTitelList;
    }

    HrSmSuccessorEvaluation selectedRow = null;

    public HrSmSuccessorEvaluation getSelectedRow() {
        return selectedRow;
    }

    public void setSelectedRow(HrSmSuccessorEvaluation selectedRow) {
        this.selectedRow = selectedRow;
    }

    public List<HrSmSuccessorEvaluation> getHrSmSuccessorEvaluationList() {
        return hrSmSuccessorEvaluationList;
    }

    public void setHrSmSuccessorEvaluationList(List<HrSmSuccessorEvaluation> hrSmSuccessorEvaluationList) {
        this.hrSmSuccessorEvaluationList = hrSmSuccessorEvaluationList;
    }

    public DataModel<HrSmSuccessorEvaluation> getSuccessorEvaluationDataModel() {
        return successorEvaluationDataModel;
    }

    public void setSuccessorEvaluationDataModel(DataModel<HrSmSuccessorEvaluation> successorEvaluationDataModel) {
        this.successorEvaluationDataModel = successorEvaluationDataModel;
    }

    public void recreateDataModel(List<HrSmSuccessorEvaluation> recreateDataModel) {
        successorEvaluationDataModel = null;
        successorEvaluationDataModel = new ListDataModel(new ArrayList<>(recreateDataModel));
    }

    public String getDisableEmpID() {
        return disableEmpID;
    }

    public void setDisableEmpID(String disableEmpID) {
        this.disableEmpID = disableEmpID;
    }

    public String getDisableEmpName() {
        return disableEmpName;
    }

    public void setDisableEmpName(String disableEmpName) {
        this.disableEmpName = disableEmpName;
    }

    public String getSelected() {
        return selected;
    }

    public void setSelected(String selected) {
        this.selected = selected;
    }

    public String getAge() {
        return Age;
    }

    public void setAge(String Age) {
        this.Age = Age;
    }

// </editor-fold >
    // <editor-fold defaultstate="collapsed" desc="Search">
    public void newOrSearchPage() {
        switch (createOrSearchBundle) {
            case "New":
                renderPnlCreateGatePass = true;
                renderPnlManPage = false;
                btnNewRender = false;
                headerTitle = "SuccessionPlanning";
                createOrSearchBundle = "Search";
                saveorUpdateBundle = "save";
                setIcone("ui-icon-search");
                break;
            case "Search":
                renderPnlCreateGatePass = false;
                renderPnlManPage = true;
                btnNewRender = false;
                createOrSearchBundle = "New";
                headerTitle = "Search...";
                setIcone("ui-icon-plus");
                break;
        }
    }

    public void newButtonAction() {
        resetSuccessor();
        renderPnlCreateGatePass = true;
        renderPnlManPage = false;
        btnNewRender = false;
        saveorUpdateBundle = "save";
        update = 0;
    }

    public void searchPage() {
        renderPnlCreateGatePass = false;
        renderPnlManPage = true;
        headerTitle = "Search...";
    }

    public void searchBykmpId(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            String jobTitle = String.valueOf(event.getNewValue());
            recreateDataModel(successorBeanLocal.findJobTitle(jobTitle));
        }
    }

    public void populate(SelectEvent events) {
        hrSmSuccessorEvaluation = (HrSmSuccessorEvaluation) events.getObject();
        hrSmSuccessorEvaluation = successorBeanLocal.getSelectedRequest(hrSmSuccessorEvaluation.getId());
        disableBtnCreate = false;
        renderPnlCreateGatePass = true;
        renderPnlManPage = false;
        renderPnlCreateAdditional = true;
        btnNewRender = true;
        headerTitle = "Key managerial Positoin";
        hrEmployees = hrSmSuccessorEvaluation.getEmpId();
        hrJobTypes = hrEmployees.getJobId();
        hrLuGrades = hrEmployees.getGradeId().getGradeId();
        update = 1;
        saveorUpdateBundle = "Update";
        age();
        createOrSearchBundle = "Search";
        setIcone("ui-icon-search");
    }

    public void searchCriteria(ValueChangeEvent event) {
        if (null != event.getNewValue()) {
            selected = event.getNewValue().toString();
            if (selected.equalsIgnoreCase("Employee ID")) {
                disableEmpID = "false";
                disableEmpName = "true";
            } else {
                disableEmpID = "true";
                disableEmpName = "false";
            }
        }
    }

    public ArrayList<HrEmployees> searchEMPByID(String hrEmployee) {
        ArrayList<HrEmployees> employe = null;
        hrEmployees.setEmpId(hrEmployee);
        employe = successorBeanLocal.SearchByEmpId(hrEmployees);
        return employe;
    }

    public void getEMPByID(SelectEvent event) {
        try {
            hrEmployees.setEmpId(event.getObject().toString());
            hrEmployees = successorBeanLocal.getByEmpId(hrEmployees);
            hrJobTypes = hrEmployees.getJobId();
            hrLuGrades = hrEmployees.getGradeId().getGradeId();
            age();
        } catch (Exception ex) {
            System.err.println("Exception");
        }

    }

    public ArrayList<HrEmployees> searchEmployeeByName(String hrEmployee) {
        ArrayList<HrEmployees> employee = null;
        hrEmployees.setFirstName(hrEmployee);
        employee = hrEmployeesBeanLocal.searchEmployeeByName(hrEmployees);
        return employee;
    }

    public void getByFirstName(SelectEvent event) {
        try {
            hrEmployees.setFirstName(event.getObject().toString());
            hrEmployees = hrEmployeesBeanLocal.getByFirstName(hrEmployees);
            hrJobTypes = hrEmployees.getJobId();
            hrLuGrades = hrEmployees.getGradeId().getGradeId();
            age();
        } catch (Exception ex) {
            System.err.println("Exception");
        }
    }
//</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Main Methods">

    public void saveSuccessor() {
        try {
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(sessionBeanLocal.getUserName(), "saveSuccessor", dataset)) {
                hrSmSuccessorEvaluation.setEmpId(hrEmployees);
                boolean result = successorBeanLocal.checkDuplicate(hrSmSuccessorEvaluation);
                if (update == 0 && result == true) {
                    JsfUtil.addFatalMessage("Duplicate Entry");
                } else {
                    try {
                        if (update == 0) {
                            hrSmSuccessorEvaluation.setEmpId(hrEmployees);
                            hrSmSuccessorEvaluation.setStatus(Constants.PREPARE_VALUE);
                            successorBeanLocal.create(hrSmSuccessorEvaluation);
                            JsfUtil.addSuccessMessage("Successfully Saved");
                            resetSuccessor();
                        } else {
                            hrSmSuccessorEvaluation.setEmpId(hrEmployees);
                            hrSmSuccessorEvaluation.setStatus(Constants.PREPARE_VALUE);
                            successorBeanLocal.edit(hrSmSuccessorEvaluation);
                            JsfUtil.addSuccessMessage("Updated Successfully");
                            resetSuccessor();
                        }
                    } catch (Exception e) {
                        JsfUtil.addErrorMessage("Error occure while save update");
                    }
                }
            } else {
                JsfUtil.addFatalMessage("You don't have appropriate permission, please contact the system administrator.");
                EventEntry eventEntry = new EventEntry();
                eventEntry.setSessionId(sessionBeanLocal.getSessionID());
                eventEntry.setUserId(sessionBeanLocal.getUserId());
                QName qualifiedName = new QName("", "project");
                JAXBElement<String> userName = new JAXBElement<String>(qualifiedName, String.class, null, sessionBeanLocal.getUserName());
                eventEntry.setUserLogin(userName);
                JAXBElement<String> module = new JAXBElement<String>(qualifiedName, String.class, null, "HRMS");
                eventEntry.setModule(module);
                JAXBElement<String> program = new JAXBElement<String>(qualifiedName, String.class, null, "saveSuccessor");
                eventEntry.setProgram(program);
                security.addEventLog(eventEntry, dataset);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void resetSuccessor() {
        hrSmSuccessorEvaluation = new HrSmSuccessorEvaluation();
        hrSmKmp = new HrSmKmp();
        hrEmployees = new HrEmployees();
        hrJobTypes = new HrJobTypes();
        hrLuGrades = new HrLuGrades();
        Age = null;
    }

    public void age() {
        String SStmonth[] = hrEmployees.getDob().split("/");
        int ISstmonth = Integer.parseInt(SStmonth[1]);
        String SSstyear[] = hrEmployees.getDob().split("/");
        int ISyear = Integer.parseInt(SSstyear[2]);
        String SSdate[] = hrEmployees.getDob().split("/");
        int ISstdate = Integer.parseInt(SSdate[0]);
        String toDayDay[] = StringDateManipulation.toDayInEc().split("-");
        int day = Integer.parseInt(toDayDay[2]);
        String toDayMonth[] = StringDateManipulation.toDayInEc().split("-");
        int month = Integer.parseInt(toDayMonth[1]);
        String toDayYear[] = StringDateManipulation.toDayInEc().split("-");
        int year = Integer.parseInt(toDayYear[0]);
        int Diff_Year = ((day - ISstdate) + ((month - ISstmonth) * 30) + ((year - ISyear) * 365));
        int AgeInYear = Diff_Year / 365;
        Age = (AgeInYear + " " + "Year");
    }
    //</editor-fold>
}
