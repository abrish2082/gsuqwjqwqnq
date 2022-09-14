/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.controller.actingassignment;

import et.gov.eep.commonApplications.businessLogic.WfHrProcessedBeanLocal;
import et.gov.eep.commonApplications.entity.Status;
import et.gov.eep.commonApplications.entity.WfHrProcessed;
import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.commonApplications.utility.StringDateManipulation;
import et.gov.eep.hrms.businessLogic.actingassignment.ActingAssignmentApproveBeanLocal;
import et.gov.eep.hrms.entity.actingAssignment.HrActingAssignments;
import et.gov.eep.hrms.entity.employee.HrEmployees;
import et.gov.eep.hrms.entity.lookup.HrLuGrades;
import et.gov.eep.hrms.entity.lookup.HrLuJobLevels;
import et.gov.eep.hrms.entity.organization.HrDepartments;
import et.gov.eep.hrms.entity.organization.HrJobTypes;
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
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import securityBean.Constants;
import securityBean.SessionBean;
import securityBean.WorkFlow;
import webService.AAA;
import webService.EventEntry;
import webService.IAdministration;

/**
 *
 * @author munir
 */
@Named(value = "actingAssignmentApproveController")
@ViewScoped
public class ActingAssignmentApproveController implements Serializable {

//<editor-fold defaultstate="collapsed" desc="Entity Injections">
    @Inject
            HrActingAssignments hrActingAssignment;
    @Inject
            HrEmployees hrEmployees;
    @Inject
            HrEmployees delegateEmp;
    @Inject
            HrDepartments hrDepartments;
    @Inject
            HrDepartments departments;
    @Inject
            HrJobTypes hrJobTypes;
    @Inject
            HrJobTypes jobTypes;
    @Inject
            HrLuGrades hrLuGrades;
    @Inject
            HrLuGrades luGrades;
    @Inject
            HrLuGrades jobGrades;
    @Inject
            HrLuJobLevels hrLuJobLevels;
    @Inject
            WfHrProcessed wfHrProcessed;
    @Inject
            SessionBean sessionBeanLocal;
    @Inject
            WorkFlow workFlow;
//</editor-fold>
    
//<editor-fold defaultstate="collapsed" desc="EJB declaration">
    @EJB
            ActingAssignmentApproveBeanLocal actingAssignmentApproveBeanLocal;
    @EJB
    private WfHrProcessedBeanLocal wfHrProcessedBeanLocal;
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Variable declaration">
    private String createOrSearchBundle = "New";
    private String icone = "ui-icon-plus";
    private boolean disableBtnCreate;
    private boolean chooseCreatePage = false;
    private boolean chooseMainPage = true;
    private String renderEmployee = "true";
    private String renderPosition = "false";
    List<SelectItem> filterByStatus = new ArrayList<>();
    DataModel<HrActingAssignments> requestsListDataModel;
    DataModel<WfHrProcessed> workflowDataModel = new ListDataModel<>();
    private List<HrActingAssignments> selectedRequest;
    private List<HrActingAssignments> actingList;
    int approveType = 0;
    String approveDate;
    Status status = new Status();
    private boolean disableBtn = false;
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="post construct">
    public ActingAssignmentApproveController() {
    }
    
    @PostConstruct
    public void init() {
        wfHrProcessed.setProcessedOn(StringDateManipulation.toDayInEc());
        actingList = actingAssignmentApproveBeanLocal.findActingPreparedList();
    }
//</editor-fold>
    
//<editor-fold defaultstate="collapsed" desc="Getter and Setter">
    public HrActingAssignments getHrActingAssignment() {
        if (hrActingAssignment == null) {
            hrActingAssignment = new HrActingAssignments();
        }
        return hrActingAssignment;
    }

    public void setHrActingAssignment(HrActingAssignments hrActingAssignment) {
        this.hrActingAssignment = hrActingAssignment;
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

    public HrEmployees getDelegateEmp() {
        if (delegateEmp == null) {
            delegateEmp = new HrEmployees();
        }
        return delegateEmp;
    }

    public void setDelegateEmp(HrEmployees delegateEmp) {
        this.delegateEmp = delegateEmp;
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

    public HrDepartments getDepartments() {
        if (departments == null) {
            departments = new HrDepartments();
        }
        return departments;
    }

    public void setDepartments(HrDepartments departments) {
        this.departments = departments;
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

    public HrJobTypes getJobTypes() {
        if (jobTypes == null) {
            jobTypes = new HrJobTypes();
        }
        return jobTypes;
    }

    public void setJobTypes(HrJobTypes jobTypes) {
        this.jobTypes = jobTypes;
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

    public HrLuGrades getLuGrades() {
        if (luGrades == null) {
            luGrades = new HrLuGrades();
        }
        return luGrades;
    }

    public void setLuGrades(HrLuGrades luGrades) {
        this.luGrades = luGrades;
    }

    public HrLuGrades getJobGrades() {
        if (jobGrades == null) {
            jobGrades = new HrLuGrades();
        }
        return jobGrades;
    }

    public void setJobGrades(HrLuGrades jobGrades) {
        this.jobGrades = jobGrades;
    }

    public HrLuJobLevels getHrLuJobLevels() {
        if (hrLuJobLevels == null) {
            hrLuJobLevels = new HrLuJobLevels();
        }
        return hrLuJobLevels;
    }

    public void setHrLuJobLevels(HrLuJobLevels hrLuJobLevels) {
        this.hrLuJobLevels = hrLuJobLevels;
    }

    public WfHrProcessed getWfHrProcessed() {
        return wfHrProcessed;
    }

    public void setWfHrProcessed(WfHrProcessed wfHrProcessed) {
        this.wfHrProcessed = wfHrProcessed;
    }

    public String getCreateOrSearchBundle() {
        return createOrSearchBundle;
    }

    public void setCreateOrSearchBundle(String createOrSearchBundle) {
        this.createOrSearchBundle = createOrSearchBundle;
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

    public boolean isChooseCreatePage() {
        return chooseCreatePage;
    }

    public void setChooseCreatePage(boolean chooseCreatePage) {
        this.chooseCreatePage = chooseCreatePage;
    }

    public boolean isChooseMainPage() {
        return chooseMainPage;
    }

    public void setChooseMainPage(boolean chooseMainPage) {
        this.chooseMainPage = chooseMainPage;
    }

    public String getRenderEmployee() {
        return renderEmployee;
    }

    public void setRenderEmployee(String renderEmployee) {
        this.renderEmployee = renderEmployee;
    }

    public String getRenderPosition() {
        return renderPosition;
    }

    public void setRenderPosition(String renderPosition) {
        this.renderPosition = renderPosition;
    }

    public List<SelectItem> getFilterByStatus() {
        filterByStatus = actingAssignmentApproveBeanLocal.filterByStatus();
        return filterByStatus;
    }

    public void setFilterByStatus(List<SelectItem> filterByStatus) {
        this.filterByStatus = filterByStatus;
    }

    public DataModel<HrActingAssignments> getRequestsListDataModel() {
        requestsListDataModel = new ListDataModel(new ArrayList<>(actingAssignmentApproveBeanLocal.findActingList(reqStatus)));;
        return requestsListDataModel;
    }

    public void setRequestsListDataModel(DataModel<HrActingAssignments> requestsListDataModel) {
        this.requestsListDataModel = requestsListDataModel;
    }

    public List<HrActingAssignments> getSelectedRequest() {
        return selectedRequest;
    }

    public void setSelectedRequest(List<HrActingAssignments> selectedRequest) {
        this.selectedRequest = selectedRequest;
    }

    public DataModel<WfHrProcessed> getWorkflowDataModel() {
        return workflowDataModel;
    }

    public void setWorkflowDataModel(DataModel<WfHrProcessed> workflowDataModel) {
        this.workflowDataModel = workflowDataModel;
    }

    public List<HrActingAssignments> getActingList() {
        if (actingList == null) {
            actingList = new ArrayList<>();
        }
        return actingList;
    }

    public void setActingList(List<HrActingAssignments> actingList) {
        this.actingList = actingList;
    }

    public int getApproveType() {
        return approveType;
    }

    public void setApproveType(int approveType) {
        this.approveType = approveType;
    }

    public String getApproveDate() {
        approveDate = StringDateManipulation.toDayInEc();
        return approveDate;
    }

    public void setApproveDate(String approveDate) {
        this.approveDate = approveDate;
    }

    public boolean isDisableBtn() {
        return disableBtn;
    }

    public void setDisableBtn(boolean disableBtn) {
        this.disableBtn = disableBtn;
    }
//</editor-fold>

 //<editor-fold defaultstate="collapsed" desc="main methods">
    public void searchPage() {
        chooseCreatePage = false;
        chooseMainPage = true;
    }
    
    public void actingAssignmentInfo() {
        disableBtnCreate = false;
        switch (createOrSearchBundle) {
            case "New":
                chooseCreatePage = true;
                chooseMainPage = false;
                createOrSearchBundle = "Search";
                setIcone("ui-icon-search");
                break;
            case "Search":
                chooseCreatePage = false;
                chooseMainPage = true;
                createOrSearchBundle = "New";
                setIcone("ui-icon-plus");
                break;
        }
    }
    
    public void workFlowDataModel() {
        workflowDataModel = null;
        for (int i = 0; i < hrActingAssignment.getWfHrProcessedList().size(); i++) {
            if (hrActingAssignment.getWfHrProcessedList().get(i).getDecision() == 0) {
                hrActingAssignment.getWfHrProcessedList().get(i).setChangedStstus("Request");
            } else if (hrActingAssignment.getWfHrProcessedList().get(i).getDecision() == 1 || hrActingAssignment.getWfHrProcessedList().get(i).getDecision() == 3) {
                hrActingAssignment.getWfHrProcessedList().get(i).setChangedStstus("Approved");
            } else if (hrActingAssignment.getWfHrProcessedList().get(i).getDecision() == 2 || hrActingAssignment.getWfHrProcessedList().get(i).getDecision() == 4) {
                hrActingAssignment.getWfHrProcessedList().get(i).setChangedStstus("Rejected");
            }
        }
        workflowDataModel = new ListDataModel(new ArrayList(hrActingAssignment.getWfHrProcessedList()));
    }
    
    public List<String> getAllAppReList() {
        return actingAssignmentApproveBeanLocal.searchAppReList(0);
    }
    
    List<String> StatusAppRej = new ArrayList<>();
    
    public SelectItem[] getAllState() {
        StatusAppRej.add("Approve");
        StatusAppRej.add("Reject");
        StatusAppRej.add("Resubmit");
        return JsfUtil.getSelectItems(StatusAppRej, false);
    }
    
    String selectedValue = "";
    
    public void handleselectOptions(ValueChangeEvent event) {
        if (null != event.getNewValue()) {
            selectedValue = event.getNewValue().toString();
        }
    }
    
    public void findAppRejList(ValueChangeEvent event) {
        String reqest[] = event.getNewValue().toString().split("--");
        int id = Integer.parseInt(reqest[0]);
        hrActingAssignment = actingAssignmentApproveBeanLocal.getActingAssiInfo(id);
        if (hrActingAssignment.getActingType().equalsIgnoreCase("Employee")) { //for render
            renderEmployee = "true";
            renderPosition = "false";
        } else {
            renderEmployee = "false";
            renderPosition = "true";
        }
        setHrEmployees(hrActingAssignment.getAssignedPerson());
        setDelegateEmp(hrActingAssignment.getAssignedBy());
        hrDepartments = hrEmployees.getDeptId();
        departments = delegateEmp.getDeptId();
        hrJobTypes = hrEmployees.getJobId();
        jobTypes = delegateEmp.getJobId();
        hrLuGrades = hrEmployees.getGradeId().getGradeId();
        luGrades = delegateEmp.getGradeId().getGradeId();
        try {
            if (hrActingAssignment.getStatus() != null) {
                if (hrActingAssignment.getStatus().equals(1)) {
                    approveType = 1;
                } else if (hrActingAssignment.getStatus().equals(2)) {
                    approveType = 2;
                } else if (hrActingAssignment.getStatus().equals(3)) {
                    approveType = 3;
                } else {
                    approveType = 4;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    private void clearPage() {
        hrActingAssignment = null;
        hrEmployees = null;
        delegateEmp = null;
        hrDepartments = null;
        departments = null;
        hrJobTypes = null;
        jobTypes = null;
        hrLuGrades = null;
        luGrades = null;
        jobGrades = null;
        hrLuJobLevels = null;
    }
    
    public boolean save() {
        if (hrEmployees.getId() == null) {
            JsfUtil.addFatalMessage(" Employee Information can not be null ");
        } else {
            try {
                if (selectedValue.equalsIgnoreCase("Approve")) {
                    if (actingAssignmentApproveBeanLocal.approveActingRequest(icone, icone, reqStatus, icone, reqStatus, approveType) == 1) {
                        JsfUtil.addSuccessMessage("The request has been successfully approved for the final state.");
                    } else {
                        JsfUtil.addFatalMessage("Error occured while approving the request for the final state.");
                    }
                    hrActingAssignment.setStatus(1);
                    actingAssignmentApproveBeanLocal.edit(hrActingAssignment);
                    JsfUtil.addSuccessMessage("The request has been successfully approved for the final state.");
                    clearPage();
                } else if (selectedValue.equalsIgnoreCase("Reject")) {
                    hrActingAssignment.setStatus(2);
                    actingAssignmentApproveBeanLocal.edit(hrActingAssignment);
                    JsfUtil.addSuccessMessage("The request has been successfully rejected.");
                    clearPage();
                } else {
                    hrActingAssignment.setStatus(3);
                    actingAssignmentApproveBeanLocal.edit(hrActingAssignment);
                    JsfUtil.addSuccessMessage("The request has been successfully resubmitted.");
                    clearPage();
                }
            } catch (Exception e) {
                JsfUtil.addFatalMessage("Error occured while approving the request.");
            }
        }
        return true;
    }
    
    public void saveActingApprove() {
        if (hrActingAssignment.getId() == null) {
            JsfUtil.addFatalMessage("Can't Approve empty data");
        } else {
            try {
                AAA securityService = new AAA();
                IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
                String systemBundle = "et/gov/eep/commonApplications/securityServer";
                String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
                if (security.checkAccess(sessionBeanLocal.getUserName(), "saveActingApprove", dataset)) {
                    
                    if (selectedValue.equalsIgnoreCase("1") && workFlow.isApproveStatus()) {
                        workFlow.setUserStatus(Constants.APPROVE_VALUE);
                        hrActingAssignment.setStatus(workFlow.getUserStatus());
                    } else if (selectedValue.equalsIgnoreCase("1") && workFlow.isCheckStatus()) {
                        workFlow.setUserStatus(Constants.CHECK_APPROVE_VALUE);
                        hrActingAssignment.setStatus(workFlow.getUserStatus());
                    } else if (selectedValue.equalsIgnoreCase("2") && workFlow.isApproveStatus()) {
                        workFlow.setUserStatus(Constants.APPROVE_REJECT_VALUE);
                        hrActingAssignment.setStatus(workFlow.getUserStatus());
                    } else if (selectedValue.equalsIgnoreCase("2") && workFlow.isCheckStatus()) {
                        workFlow.setUserStatus(Constants.CHECK_REJECT_VALUE);
                        hrActingAssignment.setStatus(workFlow.getUserStatus());
                    }
                    wfHrProcessed.setActingId(hrActingAssignment);
                    wfHrProcessed.setDecision(hrActingAssignment.getStatus());
                    wfHrProcessed.setProcessedBy(sessionBeanLocal.getUserId());
                    actingAssignmentApproveBeanLocal.edit(hrActingAssignment);
                    wfHrProcessedBeanLocal.saveOrUpdate(wfHrProcessed);
                    actingList = actingAssignmentApproveBeanLocal.findActingPreparedList();
                    JsfUtil.addSuccessMessage("Successfully saved");
                    clearPage();
                    
                } else {
                    JsfUtil.addFatalMessage("You don't have appropriate permission, please contact the system administrator.");
                    EventEntry eventEntry = new EventEntry();
                    eventEntry.setSessionId(sessionBeanLocal.getSessionID());
                    eventEntry.setUserId(sessionBeanLocal.getUserId());
                    QName qualifiedName = new QName("", "project");
                    JAXBElement<String> test = new JAXBElement<String>(qualifiedName, String.class, null, sessionBeanLocal.getUserName());
                    eventEntry.setUserLogin(test);
                    security.addEventLog(eventEntry, dataset);
                }
            } catch (Exception e) {
                JsfUtil.addFatalMessage("Unable to Approve data.");
            }
        }
    }
    
    public void searchAllReqeust() {
        if (hrActingAssignment.getActingType().isEmpty()) { //                hrActingAssignment.getRequestDate().isEmpty())
            selectedRequest = actingAssignmentApproveBeanLocal.findAllRequest();
            requestsListDataModel = new ListDataModel(selectedRequest);
        } else if (hrActingAssignment.getActingType() != null) {  //                hrActingAssignment.getRequestDate().isEmpty())
            selectedRequest = actingAssignmentApproveBeanLocal.findByActingType(hrActingAssignment);
            requestsListDataModel = new ListDataModel(selectedRequest);
        } else if (hrActingAssignment.getActingType().isEmpty() && hrActingAssignment.getRequestDate() != null) {
            selectedRequest = actingAssignmentApproveBeanLocal.findByRequestDate(hrActingAssignment);
            requestsListDataModel = new ListDataModel(selectedRequest);
        } else if (hrActingAssignment.getActingType() != null && hrActingAssignment.getRequestDate() != null) {
            selectedRequest = actingAssignmentApproveBeanLocal.findByTwo(hrActingAssignment);
            requestsListDataModel = new ListDataModel(selectedRequest);
        }
    }
    
    int reqStatus = 0;
    
    private void populateTable() {
        try {
            List<HrActingAssignments> readActingRequests = actingAssignmentApproveBeanLocal.findActingList(reqStatus);
            requestsListDataModel = new ListDataModel(readActingRequests);
        } catch (Exception ex) {
            ex.printStackTrace();
            JsfUtil.addFatalMessage("Thier is no record.");
        }
    }
    
    public void filiterByStatus_Vcl(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            reqStatus = Integer.valueOf(event.getNewValue().toString());
            populateTable();
        }
    }
    
    public void populateActingAssignment() {
        hrActingAssignment.setId(hrActingAssignment.getId());
        hrActingAssignment = actingAssignmentApproveBeanLocal.loadActingRequestDetails(hrActingAssignment.getId());
        setDelegateEmp(hrActingAssignment.getAssignedBy());
        departments = delegateEmp.getDeptId();
        jobTypes = delegateEmp.getJobId();
        luGrades = delegateEmp.getGradeId().getGradeId();
        workFlowDataModel();
        if (hrActingAssignment.getActingType().equalsIgnoreCase("Employee")) {
            renderEmployee = "true";
            renderPosition = "false";
            setHrEmployees(hrActingAssignment.getAssignedPerson());
            hrDepartments = hrEmployees.getDeptId();
            hrJobTypes = hrEmployees.getJobId();
            hrLuGrades = hrEmployees.getGradeId().getGradeId();
        } else {
            renderEmployee = "false";
            renderPosition = "true";
            setHrJobTypes(hrActingAssignment.getActingPosition());
            jobGrades = hrJobTypes.getJobGradeId().getGradeId();
            hrLuJobLevels = hrJobTypes.getJobLevelId();
        }
        if (hrActingAssignment.getStatus().equals(Constants.PREPARE_VALUE)) {
            disableBtn = false;
        } else {
            disableBtn = true;
        }
        chooseCreatePage = true;
        chooseMainPage = false;
        createOrSearchBundle = "Search";
        setIcone("ui-icon-search");
    }
    
    public void populateNotifcation(SelectEvent event) {
        try {
            if (null != event.getObject()) {
                hrActingAssignment = (HrActingAssignments) event.getObject();
                populateActingAssignment();
                RequestContext context = RequestContext.getCurrentInstance();
                context.execute("PF('widNotf').hide();");
            }
        } catch (Exception e) {
            e.printStackTrace();
            JsfUtil.addFatalMessage("Faile to process! Try Again");
        }
    }
    
    public void populateDatatable(SelectEvent event) {
        hrActingAssignment = (HrActingAssignments) event.getObject();
        populateActingAssignment();
    }
//</editor-fold>
}
