/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.controller.training;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import securityBean.Constants;
import securityBean.SessionBean;
import securityBean.WorkFlow;
import webService.AAA;
import webService.EventEntry;
import webService.IAdministration;
import et.gov.eep.commonApplications.businessLogic.WfHrProcessedBeanLocal;
import et.gov.eep.commonApplications.entity.Status;
import et.gov.eep.commonApplications.entity.WfHrProcessed;
import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.commonApplications.utility.date.StringDateManipulation;
import et.gov.eep.hrms.businessLogic.employee.HrEmployeeBeanLocal;
import et.gov.eep.hrms.businessLogic.organization.HrDepartmentsBeanLocal;
import et.gov.eep.hrms.businessLogic.training.HrLuTrainingCategoryBeanLocal;
import et.gov.eep.hrms.businessLogic.training.HrTdUnplanTraParticipantBeanLocal;
import et.gov.eep.hrms.businessLogic.training.HrTdUnplanTrainingRequestBeanLocal;
import et.gov.eep.hrms.businessLogic.training.HrTrainerProfilesBeanLocal;
import et.gov.eep.hrms.businessLogic.training.HrTrainingCoursesBeanLocal;
import et.gov.eep.hrms.entity.employee.HrEmployees;
import et.gov.eep.hrms.entity.evaluation.HrEvaluationSessions;
import et.gov.eep.hrms.entity.organization.HrDepartments;
import et.gov.eep.hrms.entity.organization.HrJobTypes;
import et.gov.eep.hrms.entity.training.HrLuTrainingCategory;
import et.gov.eep.hrms.entity.training.HrTdCourses;
import et.gov.eep.hrms.entity.training.HrTdTrainerProfiles;
import et.gov.eep.hrms.entity.training.HrTdUnplanTraParticipant;
import et.gov.eep.hrms.entity.training.HrTdUnplanTrainingRequest;
import et.gov.eep.hrms.mapper.training.HrTdCoursesFacade;

/**
 *
 * @author Abdi_Pc
 *///unplannedT
@Named(value = "unplannedTrainingController")
@ViewScoped
public class UnplannedTRequestController implements Serializable {

    //<editor-fold defaultstate="collapsed" desc="Entity, business logigic $ variables">
    @EJB
    HrTdUnplanTrainingRequestBeanLocal hrTdUnplanTrainingRequestBeanLocal;
    @EJB
    HrTdUnplanTraParticipantBeanLocal HrTdUnplanTraParticipantBeanLocal;
    @EJB
    HrDepartmentsBeanLocal hrDepartmentsBeanLocal;
    @EJB
    HrLuTrainingCategoryBeanLocal hrLuTrainingCategoryBeanLocal;
    @EJB
    HrTrainingCoursesBeanLocal hrTrainingCoursesBeanLocal;
    @EJB
    HrTrainerProfilesBeanLocal hrTrainerProfilesBeanLocal;
    @EJB
    HrEmployeeBeanLocal hrEmployeeBeanLocal;
    @EJB
    HrTdCoursesFacade hrTdCoursesFacade;

    @Inject
    WfHrProcessed wfHrProcessed;
    @EJB
    WfHrProcessedBeanLocal wfHrProcessedBeanLocal;
    @Inject
    HrTdUnplanTrainingRequest hrTdUnplanTrainingRequest;
    @Inject
    HrEmployees hrEmployees;
    @Inject
    HrLuTrainingCategory hrLuTrainingCategory;
    @Inject
    HrTdUnplanTraParticipant hrTdUnplanTraParticipant;
    @Inject
    HrDepartments hrDepartments;
    @Inject
    HrJobTypes hrJobTypes;
    @Inject
    HrTdCourses hrTdCourses;
    @Inject
    HrTdTrainerProfiles hrTdTrainerProfiles;
    @Inject
    HrEvaluationSessions hrEvaluationSessions;
    @Inject
    SessionBean sessionBeanLocal;
    @Inject
    WorkFlow workFlow;

    private boolean disableBtnCreate;
    private boolean renderPnlCreateGatePass = false;
    private boolean renderPnlManPage = true;
    private boolean btnNewRender = false;

    private String addorUpdate = "Add";
    private String saveorUpdateBundle = "Save";
    private String createOrSearchBundle = "New";
    private String headerTitle = "Search....";
    private String icone = "ui-icon-plus";
    int updateStatus = 0;

    List<HrTdTrainerProfiles> institutionList;
    List<HrDepartments> allDepartmentsList = new ArrayList<>();
    List<HrLuTrainingCategory> trainingCatagoryList = new ArrayList<>();
    List<HrTdCourses> trainningCourseList = new ArrayList<>();
    private List<SelectItem> budgetYears;
//    List<SelectItem> filterByStatus = new ArrayList<>();
    Set<String> checkEmp = new HashSet<>();

    DataModel<HrTdUnplanTrainingRequest> unplanedDataModel;
    DataModel<HrTdUnplanTrainingRequest> approvedUnplanedDataModel;
    DataModel<HrTdUnplanTraParticipant> unplannedParticipantDataModel = new ListDataModel<>();
    DataModel<WfHrProcessed> hrWorkFlowDataModel = new ListDataModel<>();
    private String fullName;
    private List<HrTdUnplanTrainingRequest> trainingRequestList;
    private List<HrTdUnplanTrainingRequest> trainingApprovedList;

    DataModel<HrTdUnplanTrainingRequest> unplannedReqDataModel = new ListDataModel<>();
    List<HrTdUnplanTrainingRequest> requestList = new ArrayList<>();

    Status status = new Status();
    int selectedStatus;
    Boolean disabledCrud = false;
    String processedOn = "";

    //</editor-fold>
    @PostConstruct
    public void init() {
        trainingCatagoryList = hrTdUnplanTrainingRequestBeanLocal.findAll(hrLuTrainingCategory);
        institutionList = hrTdUnplanTrainingRequestBeanLocal.findAll(hrTdTrainerProfiles);
        setBudgetYears(readBudgetYears());
        hrTdUnplanTrainingRequest.setLocationType(1);
        requestList = hrTdUnplanTrainingRequestBeanLocal.findRequestForApproval();
        processedOn = dateFormat(StringDateManipulation.toDayInEc());
    }

    //<editor-fold defaultstate="collapsed" desc="Getters setters">
    public String getProcessedOn() {
        return processedOn;
    }

    public void setProcessedOn(String processedOn) {
        this.processedOn = processedOn;
    }

    public WfHrProcessed getWfHrProcessed() {
        if (wfHrProcessed == null) {
            wfHrProcessed = new WfHrProcessed();
        }
        return wfHrProcessed;
    }

    public void setWfHrProcessed(WfHrProcessed wfHrProcessed) {
        this.wfHrProcessed = wfHrProcessed;
    }

    public List<HrTdUnplanTrainingRequest> getRequestList() {
        if (requestList == null) {
            requestList = new ArrayList<>();
        }
        return requestList;
    }

    public void setRequestList(List<HrTdUnplanTrainingRequest> requestList) {
        this.requestList = requestList;
    }

    public DataModel<HrTdUnplanTrainingRequest> getUnplannedReqDataModel() {
        return unplannedReqDataModel;
    }

    public void setUnplannedReqDataModel(DataModel<HrTdUnplanTrainingRequest> unplannedReqDataModel) {
        this.unplannedReqDataModel = unplannedReqDataModel;
    }

    public DataModel<WfHrProcessed> getHrWorkFlowDataModel() {
        return hrWorkFlowDataModel;
    }

    public void setHrWorkFlowDataModel(DataModel<WfHrProcessed> hrWorkFlowDataModel) {
        this.hrWorkFlowDataModel = hrWorkFlowDataModel;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
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

    public List<SelectItem> getBudgetYears() {
        return budgetYears;
    }

    public void setBudgetYears(List<SelectItem> budgetYears) {
        this.budgetYears = budgetYears;
    }

    public HrEvaluationSessions getHrEvaluationSessions() {
        if (hrEvaluationSessions == null) {
            hrEvaluationSessions = new HrEvaluationSessions();
        }
        return hrEvaluationSessions;
    }

    public void setHrEvaluationSessions(HrEvaluationSessions hrEvaluationSessions) {
        this.hrEvaluationSessions = hrEvaluationSessions;
    }

    public List<HrLuTrainingCategory> getTrainingCatagoryList() {
        if (trainingCatagoryList == null) {
            trainingCatagoryList = new ArrayList<>();
        }
        return trainingCatagoryList;
    }

    public void setTrainingCatagoryList(List<HrLuTrainingCategory> trainingCatagoryList) {
        this.trainingCatagoryList = trainingCatagoryList;
    }

    public List<HrTdCourses> getTrainningCourseList() {
        return trainningCourseList;
    }

    public void setTrainningCourseList(List<HrTdCourses> trainningCourseList) {
        this.trainningCourseList = trainningCourseList;
    }

    public HrLuTrainingCategory getHrLuTrainingCategory() {
        if (hrLuTrainingCategory == null) {
            hrLuTrainingCategory = new HrLuTrainingCategory();
        }
        return hrLuTrainingCategory;
    }

    public void setHrLuTrainingCategory(HrLuTrainingCategory hrLuTrainingCategory) {
        this.hrLuTrainingCategory = hrLuTrainingCategory;
    }

    public HrTdUnplanTrainingRequest getHrTdUnplanTrainingRequest() {
        if (hrTdUnplanTrainingRequest == null) {
            hrTdUnplanTrainingRequest = new HrTdUnplanTrainingRequest();
        }
        return hrTdUnplanTrainingRequest;
    }

    public void setHrTdUnplanTrainingRequest(HrTdUnplanTrainingRequest hrTdUnplanTrainingRequest) {
        this.hrTdUnplanTrainingRequest = hrTdUnplanTrainingRequest;
    }

    public HrTdUnplanTraParticipant getHrTdUnplanTraParticipant() {
        if (hrTdUnplanTraParticipant == null) {
            hrTdUnplanTraParticipant = new HrTdUnplanTraParticipant();
        }
        return hrTdUnplanTraParticipant;
    }

    public void setHrTdUnplanTraParticipant(HrTdUnplanTraParticipant hrTdUnplanTraParticipant) {
        this.hrTdUnplanTraParticipant = hrTdUnplanTraParticipant;
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

    public HrTdCourses getHrTdCourses() {
        return hrTdCourses;
    }

    public void setHrTdCourses(HrTdCourses hrTdCourses) {
        this.hrTdCourses = hrTdCourses;
    }

    public HrTdTrainerProfiles getHrTdTrainerProfiles() {
        return hrTdTrainerProfiles;
    }

    public void setHrTdTrainerProfiles(HrTdTrainerProfiles hrTdTrainerProfiles) {
        this.hrTdTrainerProfiles = hrTdTrainerProfiles;
    }

    public List<HrDepartments> getAllDepartmentsList() {
        return allDepartmentsList = hrDepartmentsBeanLocal.findAll();
    }

    public void setAllDepartmentsList(List<HrDepartments> allDepartmentsList) {
        this.allDepartmentsList = allDepartmentsList;
    }

    public List<HrTdTrainerProfiles> getInstitutionList() {
        return institutionList;
    }

    public void setInstitutionList(List<HrTdTrainerProfiles> institutionList) {
        this.institutionList = institutionList;
    }

    public boolean isRenderPnlCreateGatePass() {
        return renderPnlCreateGatePass;
    }

    public void setRenderPnlCreateGatePass(boolean renderPnlCreateGatePass) {
        this.renderPnlCreateGatePass = renderPnlCreateGatePass;
    }

    public String getSaveorUpdateBundle() {
        return saveorUpdateBundle;
    }

    public void setSaveorUpdateBundle(String saveorUpdateBundle) {
        this.saveorUpdateBundle = saveorUpdateBundle;
    }

    public boolean isDisableBtnCreate() {
        return disableBtnCreate;
    }

    public void setDisableBtnCreate(boolean disableBtnCreate) {
        this.disableBtnCreate = disableBtnCreate;
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

    public String getHeaderTitle() {
        return headerTitle;
    }

    public void setHeaderTitle(String headerTitle) {
        this.headerTitle = headerTitle;
    }

    public DataModel<HrTdUnplanTraParticipant> getUnplannedParticipantDataModel() {
        return unplannedParticipantDataModel;
    }

    public void setUnplannedParticipantDataModel(DataModel<HrTdUnplanTraParticipant> unplannedParticipantDataModel) {
        this.unplannedParticipantDataModel = unplannedParticipantDataModel;
    }

    public String getAddorUpdate() {
        return addorUpdate;
    }

    public void setAddorUpdate(String addorUpdate) {
        this.addorUpdate = addorUpdate;
    }

    public List<HrTdUnplanTrainingRequest> getTrainingRequestList() {
        if (trainingRequestList == null) {
            trainingRequestList = new ArrayList<>();
        }
        return trainingRequestList;
    }

    public void setTrainingRequestList(List<HrTdUnplanTrainingRequest> trainingRequestList) {
        this.trainingRequestList = trainingRequestList;
    }

    public List<HrTdUnplanTrainingRequest> getTrainingApprovedList() {
        if (trainingApprovedList == null) {
            trainingApprovedList = new ArrayList<>();
        }
        return trainingApprovedList;
    }

    public void setTrainingApprovedList(List<HrTdUnplanTrainingRequest> trainingApprovedList) {
        this.trainingApprovedList = trainingApprovedList;
    }

    public Boolean getDisabledCrud() {
        return disabledCrud;
    }

    public void setDisabledCrud(Boolean disabledCrud) {
        this.disabledCrud = disabledCrud;
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Search">
    public void createNewInfo() {
        disableBtnCreate = false;
        switch (createOrSearchBundle) {
            case "New":
                renderPnlCreateGatePass = true;
                renderPnlManPage = false;
                btnNewRender = false;
                createOrSearchBundle = "Search";
                headerTitle = "SuccessionPlanning";
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
        clearMaster();
        renderPnlCreateGatePass = true;
        renderPnlManPage = false;
        btnNewRender = false;
    }

    public void recreateDataModel() {
        unplannedParticipantDataModel = null;
        unplannedParticipantDataModel = new ListDataModel(new ArrayList(hrTdUnplanTrainingRequest.getHrTdUnplanTraParticipantList()));
    }

    public void populate(SelectEvent event) {
        hrTdUnplanTrainingRequest = (HrTdUnplanTrainingRequest) event.getObject();
        populateUnplanTrain();
    }

    public void populateUnplanTrain() {
        hrTdUnplanTrainingRequest.setId(hrTdUnplanTrainingRequest.getId());
//        hrTdUnplanTrainingRequest = hrTdUnplanTrainingRequestBeanLocal.getSelectedRequest(hrTdUnplanTrainingRequest.getId());
        hrDepartments = hrTdUnplanTrainingRequest.getDepartmentId();
        hrLuTrainingCategory = hrTdUnplanTrainingRequest.getTrainingId().getCategoryId();
        hrTdCourses = hrTdUnplanTrainingRequest.getTrainingId();
        trainningCourseList = hrLuTrainingCategoryBeanLocal.findbyID(hrLuTrainingCategory);
        hrEmployees = hrTdUnplanTraParticipant.getEmpId();
        if (hrTdUnplanTrainingRequest.getStatus().equals(Constants.APPROVE_VALUE) || hrTdUnplanTrainingRequest.getStatus().equals(Constants.CHECK_APPROVE_VALUE)) {
            disabledCrud = true;
        } else {
            disabledCrud = false;
        }
        recreateDataModel();
        recreatWorkFlowDataModel();
        renderPnlCreateGatePass = true;
        renderPnlManPage = false;
        btnNewRender = true;
        updateStatus = 1;
        saveorUpdateBundle = "Update";
        createOrSearchBundle = "Search";
        setIcone("ui-icon-search");
    }

    public void populateNotifcation(ValueChangeEvent event) {
        try {
            if (null != event.getNewValue()) {
                hrTdUnplanTrainingRequest = (HrTdUnplanTrainingRequest) event.getNewValue();
                populateUnplanTrain();
                recreatWorkFlowDataModel();
                RequestContext context = RequestContext.getCurrentInstance();
                context.execute("PF('widNotf').hide();");
            }
        } catch (Exception e) {
            e.printStackTrace();
            JsfUtil.addFatalMessage("Faile to process! Try Again");
        }
    }

    public List<SelectItem> readBudgetYears() {
        List<SelectItem> budgetYear = new ArrayList<>();
        String[] toDay = StringDateManipulation.toDayInEc().split("-");
        int year = Integer.valueOf(toDay[0]) + 1;
        while (year > 2000) {
            budgetYear.add(new SelectItem(String.valueOf(year), String.valueOf(year - 1) + "/" + String.valueOf(year)));
            year--;
        }
        return budgetYear;
    }

    public SelectItem[] getFilterByStatusForRequest() {
        SelectItem[] items = new SelectItem[4];
        items[0] = new SelectItem("null", "--- Select ---");
        items[1] = new SelectItem(Constants.PREPARE_VALUE, "Load Request List");
        items[2] = new SelectItem(Constants.APPROVE_VALUE, "Load Approved List");
        items[3] = new SelectItem(Constants.APPROVE_REJECT_VALUE, "Load Rejected List");
        return items;
    }

    private void populateRequestTable(Status _status) {
        try {
            trainingRequestList = hrTdUnplanTrainingRequestBeanLocal.loadTrainingRequestList(_status);
            unplannedReqDataModel = new ListDataModel<>(trainingRequestList);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void populateRequestTableForTwoStatus(Status _status) {
        try {
            trainingRequestList = hrTdUnplanTrainingRequestBeanLocal.loadTrainingRequestListForTwo(_status);
            unplannedReqDataModel = new ListDataModel<>(trainingRequestList);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void valueChangeListnerTrainingRequest(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            selectedStatus = Integer.valueOf(String.valueOf(event.getNewValue()));
            if (selectedStatus == Constants.PREPARE_VALUE) {
                status.setStatus1(Constants.PREPARE_VALUE);
                populateRequestTable(status);
            } else if (selectedStatus == Constants.APPROVE_VALUE) {
                status.setStatus1(Constants.APPROVE_VALUE);
                status.setStatus2(Constants.CHECK_APPROVE_VALUE);
                populateRequestTableForTwoStatus(status);
            } else if (selectedStatus == Constants.APPROVE_REJECT_VALUE) {
                status.setStatus1(Constants.APPROVE_REJECT_VALUE);
                status.setStatus2(Constants.CHECK_REJECT_VALUE);
                populateRequestTableForTwoStatus(status);
            }
        }
    }

    int status1 = 0;

    public List<SelectItem> getFilterByStatusForApprove() {
        List<SelectItem> selectItems = new ArrayList<>();
        selectItems.add(new SelectItem("null", "--- Select ---"));
        selectItems.add(new SelectItem(String.valueOf("0"), "Load Request List"));
        selectItems.add(new SelectItem(String.valueOf("1"), "Load Approved List"));
        selectItems.add(new SelectItem(String.valueOf("2"), "Load Reject List"));
        selectItems.add(new SelectItem(String.valueOf("3"), "Load All List"));
        return selectItems;
    }

    private void populateTable() {
        try {
            trainingApprovedList = hrTdUnplanTrainingRequestBeanLocal.loadTrainingRequest(status1);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void valueChangeListnerUnplannedTrainingRequest(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            status1 = Integer.valueOf(event.getNewValue().toString());
            populateTable();
        }
    }

    public ArrayList<HrEmployees> searchEmployeeByName(String hrEmployee) {
        ArrayList<HrEmployees> employee = null;
        hrEmployees.setFirstName(hrEmployee);
        employee = hrEmployeeBeanLocal.searchEmployeeByName(hrEmployees);
        return employee;
    }

    public void getByFirstName(SelectEvent event) {
        try {
            hrEmployees = (HrEmployees) event.getObject();
            hrDepartments = hrEmployees.getDeptId();
            hrJobTypes = hrEmployees.getJobId();
            hrTdUnplanTraParticipant.setEmpId(hrEmployees);
        } catch (Exception ex) {
            System.err.println("Exception");
        }
    }

    public ArrayList<HrEmployees> searchEmployeeById(String empId) {
        ArrayList<HrEmployees> employee = null;
        hrEmployees.setEmpId(empId);
        employee = hrEmployeeBeanLocal.SearchByEmpId(hrEmployees);
        return employee;
    }

    public void getById(SelectEvent event) {
        try {
            hrEmployees.setEmpId(event.getObject().toString());
            hrEmployees = hrEmployeeBeanLocal.getByEmpId(hrEmployees);
            hrDepartments = hrEmployees.getDeptId();
            hrJobTypes = hrEmployees.getJobId();
            hrTdUnplanTraParticipant.setEmpId(hrEmployees);
        } catch (Exception ex) {
            System.err.println("Exception");
        }
    }
    private String disableEmpID = "false";
    private String disableEmpName = "true";
    String selected = "Employee ID";

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
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Main">
    int editStatus = 0;

    public void addToDataTable() {
        if (updateStatus == 0) {
            if (editStatus == 1) {
                recreateDataModel();
                clearDialoge();
            } else if (unplannedParticipantDataModel.getRowCount() >= hrTdUnplanTrainingRequest.getNoOfParticipant()) {
                JsfUtil.addFatalMessage("The number 0f employee must equal with participant!");
            } else if (checkEmp.contains(hrEmployees.getEmpId())) {
                JsfUtil.addFatalMessage("Duplicate Entry. Try again!");
            } else {
                hrTdUnplanTrainingRequest.addToDataTable(hrTdUnplanTraParticipant);
                hrTdUnplanTraParticipant.setStatus(0);
                checkEmp.add(hrEmployees.getEmpId());
                recreateDataModel();
                clearDialoge();
            }
        } else {
            if (editStatus == 1) {
                recreateDataModel();
                clearDialoge();
            } else if (unplannedParticipantDataModel.getRowCount() >= hrTdUnplanTrainingRequest.getNoOfParticipant()) {
                JsfUtil.addFatalMessage("The number 0f employee must equal with participant!");
            } else if (checkEmp.contains(hrEmployees.getEmpId())) {
                JsfUtil.addFatalMessage("Duplicate Entry. Try again!");
            } else {
                hrTdUnplanTrainingRequest.addToDataTable(hrTdUnplanTraParticipant);
                hrTdUnplanTraParticipant.setStatus(0);
                checkEmp.add(hrEmployees.getEmpId());
                recreateDataModel();
                clearDialoge();
            }
        }
    }
    private HrTdUnplanTraParticipant selectedParticipant;

    public HrTdUnplanTraParticipant getSelectedParticipant() {
        return selectedParticipant;
    }

    public void setSelectedParticipant(HrTdUnplanTraParticipant selectedParticipant) {
        this.selectedParticipant = selectedParticipant;
    }

    public void editDataTable(SelectEvent event) {
        //hrTdUnplanTraParticipant = unplannedParticipantDataModel.getRowData();
        hrTdUnplanTraParticipant = (HrTdUnplanTraParticipant) event.getObject();
        hrEmployees = hrTdUnplanTraParticipant.getEmpId();
        hrDepartments = hrTdUnplanTraParticipant.getEmpId().getDeptId();
        hrJobTypes = hrTdUnplanTraParticipant.getEmpId().getJobId();
        addorUpdate = "Modify";
        editStatus = 1;
        // hrLuGrades = hrHrpRecruitmentRequest.getJobId().getJobGradeId().getGradeId();
    }

    public void saveUnplannedTrainingRequest() {
        try {
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(sessionBeanLocal.getUserName(), "saveUnplannedTrainingRequest", dataset)) {
                if (updateStatus == 0 && unplannedParticipantDataModel.getRowCount() > 0) {
                    try {
                        saveWorkFlowInformation();
                        JsfUtil.addSuccessMessage("Saved Successfully");
                        clearMaster();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        JsfUtil.addFatalMessage("Error occured while saving");
                    }
                } else if (!(unplannedParticipantDataModel.getRowCount() > 0)) {
                    JsfUtil.addFatalMessage("Data table should be filled");
                } else {
                    try {
                        hrTdUnplanTrainingRequestBeanLocal.edit(hrTdUnplanTrainingRequest);
                        JsfUtil.addSuccessMessage("Modified Successfully");
                        updateStatus = 1;
                        clearMaster();
                        unplanedDataModel = null;
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        JsfUtil.addFatalMessage("Error occured while updating");
                    }
                }
            } else {
                JsfUtil.addFatalMessage("You don't have appropriate permission, please contact the system administrator. ");
                EventEntry eventEntry = new EventEntry();
                eventEntry.setSessionId(sessionBeanLocal.getSessionID());
                eventEntry.setUserId(sessionBeanLocal.getUserId());
                QName qualifiedName = new QName("", "project");
                JAXBElement<String> userName = new JAXBElement<String>(qualifiedName, String.class, null, sessionBeanLocal.getUserName());
                eventEntry.setUserLogin(userName);
                JAXBElement<String> module = new JAXBElement<String>(qualifiedName, String.class, null, "HRMS");
                eventEntry.setModule(module);
                JAXBElement<String> program = new JAXBElement<String>(qualifiedName, String.class, null, "saveUnplannedTrainingRequest");
                eventEntry.setProgram(program);
                security.addEventLog(eventEntry, dataset);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void approveUnplannedTrainingReques() {
        try {
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(sessionBeanLocal.getUserName(), "approveUnplannedTrainingReques", dataset)) {
                if (hrTdUnplanTrainingRequest.getId() == null) {
                    JsfUtil.addFatalMessage(" Empty Information can't Approve");
                } else {
                    approveWorkFlowInformation();
                    JsfUtil.addSuccessMessage("Successfully Saved.");
                    resetUnplannedTrainingRequest();
                    clearMasterForApprove();
                }
            } else {
                JsfUtil.addFatalMessage("You don't have appropriate permission, please contact the system administrator. ");
                EventEntry eventEntry = new EventEntry();
                eventEntry.setSessionId(sessionBeanLocal.getSessionID());
                eventEntry.setUserId(sessionBeanLocal.getUserId());
                QName qualifiedName = new QName("", "project");
                JAXBElement<String> userName = new JAXBElement<String>(qualifiedName, String.class, null, sessionBeanLocal.getUserName());
                eventEntry.setUserLogin(userName);
                JAXBElement<String> module = new JAXBElement<String>(qualifiedName, String.class, null, "HRMS");
                eventEntry.setModule(module);
                JAXBElement<String> program = new JAXBElement<String>(qualifiedName, String.class, null, "approveUnplannedTrainingReques");
                eventEntry.setProgram(program);
                security.addEventLog(eventEntry, dataset);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public String dateFormat(String date) {
        String[] dateFromUI;
        String dateInDB;
        if (date != null && date.contains("-")) {
            dateFromUI = date.split("-");
            dateInDB = dateFromUI[2] + "/" + dateFromUI[1] + "/" + dateFromUI[0];
            return dateInDB;
        }
        return null;
    }

    public void resetUnplannedTrainingRequest() {
//        hrTdUnplanTrainingRequest = new HrTdUnplanTrainingRequest();
        hrTdUnplanTrainingRequest = new HrTdUnplanTrainingRequest();
        unplannedParticipantDataModel = new ListDataModel<>();
        hrDepartments = new HrDepartments();
        hrLuTrainingCategory = new HrLuTrainingCategory();
    }

    public void clearMaster() {
        hrTdUnplanTrainingRequest = new HrTdUnplanTrainingRequest();
        hrTdUnplanTraParticipant = new HrTdUnplanTraParticipant();
        unplannedParticipantDataModel = new ListDataModel<>();
        hrDepartments = new HrDepartments();
        hrLuTrainingCategory = new HrLuTrainingCategory();
        wfHrProcessed = new WfHrProcessed();
        saveorUpdateBundle = "Save";
    }

    public void clearMasterForApprove() {
        hrTdUnplanTrainingRequest = new HrTdUnplanTrainingRequest();
        unplannedParticipantDataModel = new ListDataModel<>();
        hrDepartments = new HrDepartments();
        hrLuTrainingCategory = new HrLuTrainingCategory();
        wfHrProcessed = new WfHrProcessed();
        workFlow = new WorkFlow();
    }

    public void clearDialoge() {
        hrTdUnplanTraParticipant = new HrTdUnplanTraParticipant();
        hrEmployees = new HrEmployees();
        hrJobTypes = new HrJobTypes();
        hrDepartments = new HrDepartments();
    }

    public void departmentValuechange(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            hrDepartments = (HrDepartments) event.getNewValue();
            hrTdUnplanTrainingRequest.setDepartmentId(hrDepartments);

        }
    }

    public void trainingCatagoryVCL(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            hrLuTrainingCategory.setId(Integer.parseInt(event.getNewValue().toString()));
            trainningCourseList = hrLuTrainingCategoryBeanLocal.findbyID(hrLuTrainingCategory);
        }
    }

    public void trainingCourseVCL(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            hrTdCourses = (HrTdCourses) event.getNewValue();
            hrTdUnplanTrainingRequest.setTrainingId(hrTdCourses);
        }
    }

    public void institutionVCL(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            hrTdTrainerProfiles = (HrTdTrainerProfiles) event.getNewValue();
            hrTdUnplanTrainingRequest.setInstitutionId(hrTdTrainerProfiles);
        }
    }

    //</editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Work Flow ">
    String selectedValue = "";

    public void handleSelectOptions(ValueChangeEvent event) {
        if (null != event.getNewValue()) {
            selectedValue = event.getNewValue().toString();
        }
    }

    public void recreatWorkFlowDataModel() {
        hrWorkFlowDataModel = null;
        for (int i = 0; i < hrTdUnplanTrainingRequest.getHrWorkFlowUnplannedList().size(); i++) {
            if (hrTdUnplanTrainingRequest.getHrWorkFlowUnplannedList().get(i).getDecision() == 0) {
                hrTdUnplanTrainingRequest.getHrWorkFlowUnplannedList().get(i).setChangedStstus("Request");
            } else if (hrTdUnplanTrainingRequest.getHrWorkFlowUnplannedList().get(i).getDecision() == 1 || hrTdUnplanTrainingRequest.getHrWorkFlowUnplannedList().get(i).getDecision() == 3) {
                hrTdUnplanTrainingRequest.getHrWorkFlowUnplannedList().get(i).setChangedStstus("Approved");
            } else if (hrTdUnplanTrainingRequest.getHrWorkFlowUnplannedList().get(i).getDecision() == 2 || hrTdUnplanTrainingRequest.getHrWorkFlowUnplannedList().get(i).getDecision() == 4) {
                hrTdUnplanTrainingRequest.getHrWorkFlowUnplannedList().get(i).setChangedStstus("Rejected");
            }
        }
        hrWorkFlowDataModel = new ListDataModel(new ArrayList(hrTdUnplanTrainingRequest.getHrWorkFlowUnplannedList()));
    }

    public void saveWorkFlowInformation() {
        workFlow.setUserStatus(Constants.PREPARE_VALUE);
        hrTdUnplanTrainingRequest.setStatus(workFlow.getUserStatus());
        hrTdUnplanTrainingRequest.setDescription(wfHrProcessed.getCommentGiven());
        hrTdUnplanTraParticipant.setStatus(workFlow.getUserStatus());
        hrTdUnplanTraParticipant.setUnpTraReqId(hrTdUnplanTrainingRequest);
        hrTdUnplanTrainingRequest.setPreparedBy(String.valueOf(sessionBeanLocal.getUserId()));
        wfHrProcessed.setDecision(hrTdUnplanTrainingRequest.getStatus());
        wfHrProcessed.setUnplannedTrainingId(hrTdUnplanTrainingRequest);
        wfHrProcessed.setProcessedBy(sessionBeanLocal.getUserId());
        wfHrProcessed.setProcessedOn(processedOn);
        hrTdUnplanTrainingRequestBeanLocal.saveOrUpdate(hrTdUnplanTrainingRequest);
        wfHrProcessedBeanLocal.saveOrUpdate(wfHrProcessed);
    }

    public void approveWorkFlowInformation() {
        if (selectedValue.equalsIgnoreCase("1") && workFlow.isApproveStatus()) {
            workFlow.setUserStatus(Constants.APPROVE_VALUE);
            hrTdUnplanTrainingRequest.setStatus(workFlow.getUserStatus());
        } else if (selectedValue.equalsIgnoreCase("2") && workFlow.isApproveStatus()) {
            workFlow.setUserStatus(Constants.APPROVE_REJECT_VALUE);
            hrTdUnplanTrainingRequest.setStatus(workFlow.getUserStatus());
        } else if (selectedValue.equalsIgnoreCase("1") && workFlow.isCheckStatus()) {
            workFlow.setUserStatus(Constants.CHECK_APPROVE_VALUE);
            hrTdUnplanTrainingRequest.setStatus(workFlow.getUserStatus());
        } else if (selectedValue.equalsIgnoreCase("2") && workFlow.isCheckStatus()) {
            workFlow.setUserStatus(Constants.CHECK_REJECT_VALUE);
            hrTdUnplanTrainingRequest.setStatus(workFlow.getUserStatus());
        }
        hrTdUnplanTrainingRequestBeanLocal.edit(hrTdUnplanTrainingRequest);
        wfHrProcessed.setDecision(hrTdUnplanTrainingRequest.getStatus());
        wfHrProcessed.setUnplannedTrainingId(hrTdUnplanTrainingRequest);
        wfHrProcessed.setProcessedBy(sessionBeanLocal.getUserId());
        wfHrProcessed.setProcessedOn(processedOn);
        wfHrProcessedBeanLocal.saveOrUpdate(wfHrProcessed);
    }
    // </editor-fold>
}
