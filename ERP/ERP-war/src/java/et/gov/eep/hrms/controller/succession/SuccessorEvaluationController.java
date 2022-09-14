/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.controller.succession;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import org.primefaces.event.SelectEvent;
import securityBean.Constants;
import securityBean.SessionBean;
import securityBean.WorkFlow;
import webService.AAA;
import webService.EventEntry;
import webService.IAdministration;
import webService.UserStatus;
import et.gov.eep.commonApplications.businessLogic.WfHrProcessedBeanLocal;
import et.gov.eep.commonApplications.entity.WfHrProcessed;
import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.hrms.businessLogic.employee.HrEmployeeBeanLocal;
import et.gov.eep.hrms.businessLogic.organization.HrDepartmentsBeanLocal;
import et.gov.eep.hrms.businessLogic.recruitment.HrRecruitmentRequestsBeanLocal;
import et.gov.eep.hrms.businessLogic.succession.SuccessorEvaluationBeanLocal;
import et.gov.eep.hrms.businessLogic.succession.successionBeanLocal;
import et.gov.eep.hrms.entity.employee.HrEmployees;
import et.gov.eep.hrms.entity.lookup.HrLuGrades;
import et.gov.eep.hrms.entity.organization.HrDepartments;
import et.gov.eep.hrms.entity.organization.HrDeptJobs;
import et.gov.eep.hrms.entity.organization.HrJobTypes;
import et.gov.eep.hrms.entity.succession.HrSmKmp;
import et.gov.eep.hrms.entity.succession.HrSmSuccessorEvaluation;

/**
 *
 * @author Behailu
 */
@Named(value = "SuccessorEvaluationControllers")
@ViewScoped
public class SuccessorEvaluationController implements Serializable {

    //<editor-fold defaultstate="collapsed" desc="Entity, business logic & Variables">
    @EJB
    SuccessorEvaluationBeanLocal successorEvaluationBeanLocal;
    @EJB
    HrDepartmentsBeanLocal hrDepartmentsBeanLocal;
    @EJB
    successionBeanLocal successionBeanLocal;
    @EJB
    HrEmployeeBeanLocal hrEmployeeBean;
    @EJB
    HrRecruitmentRequestsBeanLocal hrRecruitmentRequestsBeanLocal;
    @EJB
    successionBeanLocal successionBean;
    @EJB
    WfHrProcessedBeanLocal wfHrProcessedBeanLocal;

    @Inject
    private HrSmKmp hrSmKmp;
    @Inject
    HrDepartments hrDepartments;
    @Inject
    WfHrProcessed wfHrProcessed;
    @Inject
    HrEmployees hrEmployees;
    @Inject
    HrJobTypes hrJobTypes;
    @Inject
    HrDeptJobs hrDeptJobs;
    @Inject
    HrSmSuccessorEvaluation hrSmSuccessorEvaluation;
    @Inject
    HrLuGrades hrLuGrades;
    @Inject
    SessionBean SessionBean;
    @Inject
    WorkFlow workFlow;

    List<HrDepartments> allDepartmentsList = new ArrayList<>();
    List<HrEmployees> allEmployeesList = new ArrayList<>();
    List<HrSmKmp> allKMPsList = new ArrayList<>();
    List<HrSmSuccessorEvaluation> allsuccessor = new ArrayList<>();
    DataModel<HrSmSuccessorEvaluation> SuccessorListDataModel = new ListDataModel<>();
    DataModel<HrSmSuccessorEvaluation> SuccessorListinfoDataModel = new ListDataModel<>();
    DataModel<WfHrProcessed> workFlowDataModel = new ListDataModel<>();
    List<SelectItem> filterByStatus = new ArrayList<>();
    List<HrJobTypes> allJobTypes;
    List<HrSmSuccessorEvaluation> allsuccessors;
    List<HrSmSuccessorEvaluation> allsuccessorsTobeEvaluated;
    List<HrSmSuccessorEvaluation> allEvaluatedsuccessors;
    private HrSmSuccessorEvaluation rowselect;
    UserStatus userStatus = new UserStatus();

    private String createOrSearchBundle = "New";
    private String headerTitle = "Search...";
    private String smallheaderTitle = "";
    private String icone = "ui-icon-plus";
    private boolean disableBtnCreate;
    private boolean renderPnlCreateGatePass = false;
    private boolean evaluate = false;
    private boolean renderPnlManPage = true;
    private boolean btnNewRender = false;
    private String saveorUpdateBundle = "Save";
    private boolean enabnleUpdatebtn = true;
    private boolean pnlrWfIcon = false;
    int updateStatus = 0;
    private int requestNotificationCounter = 0;
    private int requestNotificationCounter1 = 0;
    int status;
    boolean btnaddvisibility = true;
    private Integer evaluatorId;
    private String username;
    //</editor-fold>

    @PostConstruct
    public void init() {
        hrSmSuccessorEvaluation = new HrSmSuccessorEvaluation();
        allKMPsList = successionBean.findAll();
        allEvaluatedsuccessors = successorEvaluationBeanLocal.findallEvaluatedsuccessors(hrSmSuccessorEvaluation);
        allsuccessorsTobeEvaluated = successorEvaluationBeanLocal.findallToBeEvaluated(hrSmSuccessorEvaluation);
        SuccessorListDataModel = new ListDataModel(new ArrayList(allsuccessorsTobeEvaluated));
        setUsername(SessionBean.getUserName());
        setEvaluator(SessionBean.getUserId());

    }

    //<editor-fold defaultstate="collapsed" desc="Getters and setters">
    public boolean isEvaluate() {
        return evaluate;
    }

    public void setEvaluate(boolean evaluate) {
        this.evaluate = evaluate;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public WfHrProcessed getWfHrProcessed() {
        return wfHrProcessed;
    }

    public void setWfHrProcessed(WfHrProcessed wfHrProcessed) {
        this.wfHrProcessed = wfHrProcessed;
    }

    public DataModel<WfHrProcessed> getWorkFlowDataModel() {
        return workFlowDataModel;
    }

    public void setWorkFlowDataModel(DataModel<WfHrProcessed> workFlowDataModel) {
        this.workFlowDataModel = workFlowDataModel;
    }

    public List<SelectItem> getFilterByStatus() {
        filterByStatus = successorEvaluationBeanLocal.filterByStatus();
        return filterByStatus;
    }

    public void setFilterByStatus(List<SelectItem> filterByStatus) {
        this.filterByStatus = filterByStatus;
    }

    public boolean isEnabnleUpdatebtn() {
        return enabnleUpdatebtn;
    }

    public void setEnabnleUpdatebtn(boolean enabnleUpdatebtn) {
        this.enabnleUpdatebtn = enabnleUpdatebtn;
    }

    public Integer getEvaluator() {
        return evaluatorId;
    }

    public void setEvaluator(Integer evaluator) {
        this.evaluatorId = evaluator;
    }

    public String getSaveorUpdateBundle() {
        return saveorUpdateBundle;
    }

    public void setSaveorUpdateBundle(String saveorUpdateBundle) {
        this.saveorUpdateBundle = saveorUpdateBundle;
    }

    public boolean isBtnaddvisibility() {
        return btnaddvisibility;
    }

    public void setBtnaddvisibility(boolean btnaddvisibility) {
        this.btnaddvisibility = btnaddvisibility;
    }

    public String getCreateOrSearchBundle() {
        return createOrSearchBundle;
    }

    public void setCreateOrSearchBundle(String createOrSearchBundle) {
        this.createOrSearchBundle = createOrSearchBundle;
    }

    public String getHeaderTitle() {
        return headerTitle;
    }

    public void setHeaderTitle(String headerTitle) {
        this.headerTitle = headerTitle;
    }

    public String getSmallheaderTitle() {
        return smallheaderTitle;
    }

    public void setSmallheaderTitle(String smallheaderTitle) {
        this.smallheaderTitle = smallheaderTitle;
    }

    public String getIcone() {
        return icone;
    }

    public void setIcone(String icone) {
        this.icone = icone;
    }

    public boolean isDisableBtnCreate() {
        return disableBtnCreate;
    }

    public void setDisableBtnCreate(boolean disableBtnCreate) {
        this.disableBtnCreate = disableBtnCreate;
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

    public int getUpdateStatus() {
        return updateStatus;
    }

    public SuccessorEvaluationBeanLocal getSuccessorEvaluationBeanLocal() {
        return successorEvaluationBeanLocal;
    }

    public void setSuccessorEvaluationBeanLocal(SuccessorEvaluationBeanLocal successorEvaluationBeanLocal) {
        this.successorEvaluationBeanLocal = successorEvaluationBeanLocal;
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

    public successionBeanLocal getSuccessionBean() {
        return successionBean;
    }

    public void setSuccessionBean(successionBeanLocal successionBean) {
        this.successionBean = successionBean;
    }

    public HrDepartmentsBeanLocal getHrDepartmentsBeanLocal() {
        return hrDepartmentsBeanLocal;
    }

    public void setHrDepartmentsBeanLocal(HrDepartmentsBeanLocal hrDepartmentsBeanLocal) {
        this.hrDepartmentsBeanLocal = hrDepartmentsBeanLocal;
    }

    public successionBeanLocal getSuccessionBeanLocal() {
        return successionBeanLocal;
    }

    public void setSuccessionBeanLocal(successionBeanLocal successionBeanLocal) {
        this.successionBeanLocal = successionBeanLocal;
    }

    public HrEmployeeBeanLocal getHrEmployeeBean() {
        return hrEmployeeBean;
    }

    public void setHrEmployeeBean(HrEmployeeBeanLocal hrEmployeeBean) {
        this.hrEmployeeBean = hrEmployeeBean;
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

    public HrDeptJobs getHrDeptJobs() {
        if (hrJobTypes == null) {
            hrJobTypes = new HrJobTypes();
        }
        return hrDeptJobs;
    }

    public void setHrDeptJobs(HrDeptJobs hrDeptJobs) {
        this.hrDeptJobs = hrDeptJobs;
    }

    public HrSmKmp getHrSmKmp() {
        return hrSmKmp;
    }

    public void setHrSmKmp(HrSmKmp hrSmKmp) {
        this.hrSmKmp = hrSmKmp;
    }

    public HrDepartments getHrDepartments() {
        return hrDepartments;
    }

    public void setHrDepartments(HrDepartments hrDepartments) {
        this.hrDepartments = hrDepartments;
    }

    public HrLuGrades getHrLuGrades() {
        return hrLuGrades;
    }

    public void setHrLuGrades(HrLuGrades hrLuGrades) {
        this.hrLuGrades = hrLuGrades;
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

    public void setUpdateStatus(int updateStatus) {
        this.updateStatus = updateStatus;
    }

    public HrSmSuccessorEvaluation getRowselect() {
        return rowselect;
    }

    public void setRowselect(HrSmSuccessorEvaluation rowselect) {
        this.rowselect = rowselect;
    }

    public DataModel<HrSmSuccessorEvaluation> getSuccessorListDataModel() {

        if (SuccessorListDataModel == null) {
            SuccessorListDataModel = new ListDataModel<>();
        }

        return SuccessorListDataModel;
    }

    public void setSuccessorListDataModel(DataModel<HrSmSuccessorEvaluation> SuccessorListDataModel) {
        this.SuccessorListDataModel = SuccessorListDataModel;
    }

    public DataModel<HrSmSuccessorEvaluation> getSuccessorListinfoDataModel() {

        if (SuccessorListinfoDataModel == null) {
            SuccessorListinfoDataModel = new ListDataModel<>();
        }
        return SuccessorListinfoDataModel;
    }

    public void setSuccessorListinfoDataModel(DataModel<HrSmSuccessorEvaluation> SuccessorListinfoDataModel) {
        this.SuccessorListinfoDataModel = SuccessorListinfoDataModel;
    }

    public Integer getEvaluatorId() {
        return evaluatorId;
    }

    public void setEvaluatorId(Integer evaluatorId) {
        this.evaluatorId = evaluatorId;
    }

    public List<HrSmKmp> getAllKMPsList() {
        return allKMPsList;
    }

    public void setAllKMPsList(List<HrSmKmp> allKMPsList) {
        this.allKMPsList = allKMPsList;
    }

    public List<HrSmSuccessorEvaluation> getAllsuccessor() {
        return allsuccessor;
    }

    public void setAllsuccessor(List<HrSmSuccessorEvaluation> allsuccessor) {
        this.allsuccessor = allsuccessor;
    }

    public List<HrJobTypes> getAllJobTypes() {
        return allJobTypes;
    }

    public void setAllJobTypes(List<HrJobTypes> allJobTypes) {
        this.allJobTypes = allJobTypes;
    }

    public List<HrEmployees> getAllEmployeesList() {
        return allEmployeesList;
    }

    public void setAllEmployeesList(List<HrEmployees> allEmployeesList) {
        this.allEmployeesList = allEmployeesList;
    }

    public List<HrSmSuccessorEvaluation> getAllsuccessors() {
        return allsuccessors;
    }

    public void setAllsuccessors(List<HrSmSuccessorEvaluation> allsuccessors) {
        this.allsuccessors = allsuccessors;
    }

    public List<HrSmSuccessorEvaluation> getAllEvaluatedsuccessors() {
        return allEvaluatedsuccessors;
    }

    public void setAllEvaluatedsuccessors(List<HrSmSuccessorEvaluation> allEvaluatedsuccessors) {
        this.allEvaluatedsuccessors = allEvaluatedsuccessors;
    }

    public List<HrSmSuccessorEvaluation> getAllsuccessorsTobeEvaluated() {
        return allsuccessorsTobeEvaluated;
    }

    public void setAllsuccessorsTobeEvaluated(List<HrSmSuccessorEvaluation> allsuccessorsTobeEvaluated) {
        this.allsuccessorsTobeEvaluated = allsuccessorsTobeEvaluated;
    }

    public int getRequestNotificationCounter() {
        int status = 0;
        List<HrSmSuccessorEvaluation> readFilteredRetirement = successorEvaluationBeanLocal.loadEvaluationList(status);
        SuccessorListDataModel = new ListDataModel(readFilteredRetirement);
        requestNotificationCounter = readFilteredRetirement.size();
        return requestNotificationCounter;
    }

    public void setRequestNotificationCounter(int requestNotificationCounter) {
        this.requestNotificationCounter = requestNotificationCounter;
    }

    public int getRequestNotificationCounter1() {
        int status = 1;
        List<HrSmSuccessorEvaluation> readFilteredRetirement = successorEvaluationBeanLocal.loadEvaluationList(status);
        SuccessorListDataModel = new ListDataModel(readFilteredRetirement);
        requestNotificationCounter1 = readFilteredRetirement.size();
        return requestNotificationCounter1;
    }

    public void setRequestNotificationCounter1(int requestNotificationCounter1) {
        this.requestNotificationCounter1 = requestNotificationCounter1;
    }

    public boolean isPnlrWfIcon() {
        return pnlrWfIcon;
    }

    public void setPnlrWfIcon(boolean pnlrWfIcon) {
        this.pnlrWfIcon = pnlrWfIcon;
    }

//</editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Search">
    public void createNewView() {
        disableBtnCreate = false;
        if (createOrSearchBundle.equals("New")) {
            renderPnlCreateGatePass = true;
            renderPnlManPage = false;
            evaluate = false;
            btnNewRender = false;
            createOrSearchBundle = "Search";
            headerTitle = "Add  ";
            smallheaderTitle = "";
            setIcone("ui-icon-search");
        } else if (createOrSearchBundle.equals("Search")) {
            renderPnlCreateGatePass = false;
            renderPnlManPage = true;
            evaluate = false;
            btnNewRender = false;
            createOrSearchBundle = "New";
            headerTitle = "Search";
            smallheaderTitle = "";
            setIcone("ui-icon-plus");
        }
    }

    public void newButtonAction() {
        renderPnlCreateGatePass = true;
        renderPnlManPage = false;
        btnNewRender = false;
        updateStatus = 0;
        saveorUpdateBundle = "Save";
    }

    public void Successor_vcl(SelectEvent event) {
        hrSmSuccessorEvaluation = (HrSmSuccessorEvaluation) event.getObject();
        hrSmSuccessorEvaluation = successorEvaluationBeanLocal.readkmpDetail(hrSmSuccessorEvaluation.getId());
        disableBtnCreate = false;
        renderPnlCreateGatePass = true;
        renderPnlManPage = false;
        btnNewRender = true;
        createOrSearchBundle = "Search";
        headerTitle = "successor evaluation ";
        setIcone("ui-icon-search");
        saveorUpdateBundle = "Update";
        if (hrSmSuccessorEvaluation.getStatus() <= 1) {
            enabnleUpdatebtn = true;
        } else {
            enabnleUpdatebtn = false;
        }
        pnlrWfIcon = true;
        recreateDataModelwf();

    }

    public void Successor_evaluation_approval(SelectEvent event) {
        hrSmSuccessorEvaluation = (HrSmSuccessorEvaluation) event.getObject();
        hrSmSuccessorEvaluation = successorEvaluationBeanLocal.readkmpDetail(hrSmSuccessorEvaluation.getId());
        disableBtnCreate = false;
        renderPnlCreateGatePass = false;
        renderPnlManPage = true;
        createOrSearchBundle = "Search";
        headerTitle = "successor evaluation ";
        setIcone("ui-icon-search");
        if (hrSmSuccessorEvaluation.getApprovedDate() == null || hrSmSuccessorEvaluation.getRemark() == null) {
            saveorUpdateBundle = "Save";
        } else {
            saveorUpdateBundle = "Update";
        }
        pnlrWfIcon = true;
        recreateDataModelwf();
    }

    public void SearchByPositionName(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            hrSmKmp = (HrSmKmp) event.getNewValue();
            allsuccessors = successorEvaluationBeanLocal.findbyposition(hrSmKmp);
            SuccessorListDataModel = new ListDataModel(new ArrayList(allsuccessors));
            evaluate = false;
        }
    }

    public void SearchByPositionNameToAprove(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            hrSmKmp = (HrSmKmp) event.getNewValue();
            evaluate = true;
            allEvaluatedsuccessors = successorEvaluationBeanLocal.findbypositionToEvaluate(hrSmKmp);
        }
    }

    public ArrayList<HrSmSuccessorEvaluation> findbyfirstname() {
        allsuccessors = successorEvaluationBeanLocal.findEmployeename(hrEmployees);
        return null;
    }

    public ArrayList<HrSmSuccessorEvaluation> findListByKMPname_EmployeenameForAproval() {
        if (!hrEmployees.getFirstName().isEmpty() && !hrJobTypes.getJobTitle().isEmpty()) {
            JsfUtil.addSuccessMessage("Please Insert only one of Position Name or successor Name");
        } else if (!hrJobTypes.getJobTitle().isEmpty() && hrEmployees.getFirstName().isEmpty()) {
            allsuccessors = successorEvaluationBeanLocal.findJobTitleforApproval(hrJobTypes);
            SuccessorListDataModel = new ListDataModel(new ArrayList(allsuccessors));

        } else if (!hrEmployees.getFirstName().isEmpty() && hrJobTypes.getJobTitle().isEmpty()) {
            allsuccessors = successorEvaluationBeanLocal.findEmployeenameforApproval(hrEmployees);
            SuccessorListDataModel = new ListDataModel(new ArrayList(allsuccessors));
        } else {
            JsfUtil.addSuccessMessage("Please Insert Position  Name or  department");
        }
        return null;
    }

    private void populateTable() {
        try {
            List<HrSmSuccessorEvaluation> readFilteredRetirement = successorEvaluationBeanLocal.loadEvaluationList(status);
            SuccessorListDataModel = new ListDataModel(readFilteredRetirement);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void filiterByStatus_Vcl(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            status = Integer.valueOf(event.getNewValue().toString());
            populateTable();
        }
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Main method">

    public void saveSuccessorEvaluation() {
        try {
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(SessionBean.getUserName(), "saveSuccessorEvaluation", dataset)) {
                try {
                    workFlow.setUserStatus(Constants.CHECK_APPROVE_VALUE);
                    hrSmSuccessorEvaluation.setStatus(workFlow.getUserStatus());
                    if (hrSmSuccessorEvaluation.getKmpId() == null) {
                        JsfUtil.addErrorMessage("select position to Populate successors on dataTable");
                    } else if (hrSmSuccessorEvaluation.getEmpId() == null) {
                        JsfUtil.addErrorMessage("Select successor From the dataTable");
                    } else if (hrSmSuccessorEvaluation.getStatus() == 1) {
                        successorEvaluationBeanLocal.edit(hrSmSuccessorEvaluation);
                        wfSave();
                        JsfUtil.addSuccessMessage("Updated Successfully");
                        hrSmSuccessorEvaluation = new HrSmSuccessorEvaluation();
                    } else {
                        successorEvaluationBeanLocal.edit(hrSmSuccessorEvaluation);
                        wfSave();
                        JsfUtil.addSuccessMessage("Saved Successfully");
                        hrSmSuccessorEvaluation = null;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    JsfUtil.addErrorMessage("Error occure while save update");
                }
            } else {
                JsfUtil.addFatalMessage("You don't have appropriate permission, please contact the system administrator. ");
                EventEntry eventEntry = new EventEntry();
                eventEntry.setSessionId(SessionBean.getSessionID());
                eventEntry.setUserId(SessionBean.getUserId());
                QName qualifiedName = new QName("", "project");
                JAXBElement<String> userName = new JAXBElement<String>(qualifiedName, String.class, null, SessionBean.getUserName());
                eventEntry.setUserLogin(userName);
                JAXBElement<String> module = new JAXBElement<String>(qualifiedName, String.class, null, "HRMS");
                eventEntry.setModule(module);
                JAXBElement<String> program = new JAXBElement<String>(qualifiedName, String.class, null, "saveSuccessorEvaluation");
                eventEntry.setProgram(program);
                security.addEventLog(eventEntry, dataset);

            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void saveSuccessorEvaluationApprove() {
        if (hrSmSuccessorEvaluation.getKmpId() == null) {
            JsfUtil.addFatalMessage("please Select Key Managerial postion To populate evaluated successor list");
        } else if (hrSmSuccessorEvaluation.getEmpId() == null) {
            JsfUtil.addFatalMessage("please select successor form the tabel");
        } else {
            String Srequestmonth[] = hrSmSuccessorEvaluation.getEvaluationDate().split("/");
            int Irequestmonth = Integer.parseInt(Srequestmonth[1]);
            String Srequestyear[] = hrSmSuccessorEvaluation.getEvaluationDate().split("/");
            int Irequestyear = Integer.parseInt(Srequestyear[2]);
            String Srequestdate[] = hrSmSuccessorEvaluation.getEvaluationDate().split("/");
            int Irequestdate = Integer.parseInt(Srequestdate[0]);

            String SApproveday[] = hrSmSuccessorEvaluation.getApprovedDate().split("/");
            int IApproveday = Integer.parseInt(SApproveday[0]);
            String SapprinveMonth[] = hrSmSuccessorEvaluation.getApprovedDate().split("/");
            int IapproveMonth = Integer.parseInt(SapprinveMonth[1]);
            String SapprinveYear[] = hrSmSuccessorEvaluation.getApprovedDate().split("/");
            int IapproveYear = Integer.parseInt(SapprinveYear[2]);
            int RequestAproveDateGap = ((IApproveday - Irequestdate) + ((IapproveMonth - Irequestmonth) * 30) + ((IapproveYear - Irequestyear) * 365));
            if (RequestAproveDateGap < 0) {
                JsfUtil.addErrorMessage(" Approve Date should't Exceed Request date ");
            } else {
                try {
                    AAA securityService = new AAA();
                    IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
                    String systemBundle = "et/gov/eep/commonApplications/securityServer";
                    String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
                    if (security.checkAccess(SessionBean.getUserName(), "saveSuccessorEvaluationApprove", dataset)) {
                        workFlow.setUserStatus(Constants.APPROVE_VALUE);
                        hrSmSuccessorEvaluation.setStatus(workFlow.getUserStatus());
                        successorEvaluationBeanLocal.edit(hrSmSuccessorEvaluation);
                        wfSave();
                        JsfUtil.addSuccessMessage("Approved Successfully");
                        hrSmSuccessorEvaluation = null;
                    } else {
                        JsfUtil.addFatalMessage("You don't have appropriate permission, please contact the system administrator. ");
                        EventEntry eventEntry = new EventEntry();
                        eventEntry.setSessionId(SessionBean.getSessionID());
                        eventEntry.setUserId(SessionBean.getUserId());
                        QName qualifiedName = new QName("", "project");
                        JAXBElement<String> userName = new JAXBElement<String>(qualifiedName, String.class, null, SessionBean.getUserName());
                        eventEntry.setUserLogin(userName);
                        JAXBElement<String> module = new JAXBElement<String>(qualifiedName, String.class, null, "HRMS");
                        eventEntry.setModule(module);
                        JAXBElement<String> program = new JAXBElement<String>(qualifiedName, String.class, null, "saveSuccessorEvaluationApprove");
                        eventEntry.setProgram(program);
                        security.addEventLog(eventEntry, dataset);
                    }
                } catch (Exception ex) {
                    JsfUtil.addErrorMessage("error while saving");
                }
            }
        }
    }

    public void wfSave() {
        wfHrProcessed.setProcessedBy(SessionBean.getUserId());
        wfHrProcessed.setCommentGiven(hrSmSuccessorEvaluation.getRemark());
        wfHrProcessed.setDecision(hrSmSuccessorEvaluation.getStatus());
        wfHrProcessed.setProcessedOn(hrSmSuccessorEvaluation.getEvaluationDate());
        wfHrProcessed.setSuccessionEvaluationId(hrSmSuccessorEvaluation);
        wfHrProcessedBeanLocal.create(wfHrProcessed);
    }

    public void clean() {
        hrSmSuccessorEvaluation = new HrSmSuccessorEvaluation();
        updateStatus = 0;
        saveorUpdateBundle = "Save";
    }

    public void Positionchangelistener(ValueChangeEvent event) {

        if (event.getNewValue() != null) {
            hrSmKmp = (HrSmKmp) event.getNewValue();
            allsuccessor = successorEvaluationBeanLocal.findbyposition(hrSmKmp);
            evaluate = true;
        }
    }

    public void recreateDataModelwf() {
        workFlowDataModel = null;
        workFlowDataModel = new ListDataModel(new ArrayList(hrSmSuccessorEvaluation.getWfHrProcessedList()));
    }

//</editor-fold>
}
