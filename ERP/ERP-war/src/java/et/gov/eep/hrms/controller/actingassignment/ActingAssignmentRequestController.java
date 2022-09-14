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
import et.gov.eep.hrms.businessLogic.actingassignment.ActingAssignmentBeanLocal;
import et.gov.eep.hrms.businessLogic.employee.HrEmployeeBeanLocal;
import et.gov.eep.hrms.entity.actingAssignment.HrActingAssignments;
import et.gov.eep.hrms.entity.employee.HrEmployees;
import et.gov.eep.hrms.entity.lookup.HrLuGrades;
import et.gov.eep.hrms.entity.lookup.HrLuJobLevels;
import et.gov.eep.hrms.entity.organization.HrDepartments;
import et.gov.eep.hrms.entity.organization.HrJobTypes;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

/**
 *
 * @author INSA
 */
@Named(value = "actingAssignmentRequestController")
@ViewScoped
public class ActingAssignmentRequestController implements Serializable {

    //<editor-fold defaultstate="collapsed" desc="Entity Injection">
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
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="EJB Declaration">
    @EJB
    private ActingAssignmentBeanLocal actingAssignmentBeanLocal;
    @EJB
    private HrEmployeeBeanLocal hrEmployeeBean;
    @EJB
    private WfHrProcessedBeanLocal wfHrProcessedBeanLocal;
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Variable declaration">
    int updateStatus = 0;
    private String saveorUpdateBundle = "Save";
    private String createOrSearchBundle = "New";
    private String headerTitle = "Search Employee";
    private String icone = "ui-icon-plus";
    private boolean disableBtnCreate;
    private boolean chooseCreatePage = false;
    private boolean chooseMainPage = true;
    private String chooseEmployee = "true";
    private String choosePosition = "false";
    private int dateCalc;
    Date date = new Date();
    List<SelectItem> filterByStatus = new ArrayList<>();
    DataModel<HrActingAssignments> requestsListDataModel;
    DataModel<WfHrProcessed> workflowDataModel = new ListDataModel<>();
    private List<HrActingAssignments> selectedRequest;
    private List<HrActingAssignments> actingAssignmentList = new ArrayList<>();
    private int selectedStatus;
    int approveType = 0;
    String requestDate;
    private String disableBtn = "false";
    WorkFlow workFlow = new WorkFlow();
    Status status = new Status();
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Post construct">
    
    @PostConstruct
    public void init() {
        wfHrProcessed.setProcessedOn(StringDateManipulation.toDayInEc());
    }
//</editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getter and Setter">
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

    public List<SelectItem> getFilterByStatus() {
        filterByStatus = actingAssignmentBeanLocal.filterByStatus();
        return filterByStatus;
    }

    public void setFilterByStatus(List<SelectItem> filterByStatus) {
        this.filterByStatus = filterByStatus;
    }

    public DataModel<HrActingAssignments> getRequestsListDataModel() {
        return requestsListDataModel;
    }

    public void setRequestsListDataModel(DataModel<HrActingAssignments> requestsListDataModel) {
        this.requestsListDataModel = requestsListDataModel;
    }

    public DataModel<WfHrProcessed> getWorkflowDataModel() {
        return workflowDataModel;
    }

    public void setWorkflowDataModel(DataModel<WfHrProcessed> workflowDataModel) {
        this.workflowDataModel = workflowDataModel;
    }

    public List<HrActingAssignments> getSelectedRequest() {
        if (selectedRequest == null) {
            selectedRequest = new ArrayList<>();
        }
        return selectedRequest;
    }

    public void setSelectedRequest(List<HrActingAssignments> selectedRequest) {
        this.selectedRequest = selectedRequest;
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

    public String getChooseEmployee() {
        return chooseEmployee;
    }

    public void setChooseEmployee(String chooseEmployee) {
        this.chooseEmployee = chooseEmployee;
    }

    public String getChoosePosition() {
        return choosePosition;
    }

    public void setChoosePosition(String choosePosition) {
        this.choosePosition = choosePosition;
    }

    public int getDateCalc() {
        return dateCalc;
    }

    public void setDateCalc(int dateCalc) {
        this.dateCalc = dateCalc;
    }

    public String getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(String requestDate) {
        this.requestDate = requestDate;
    }

    public String getDisableBtn() {
        return disableBtn;
    }

    public void setDisableBtn(String disableBtn) {
        this.disableBtn = disableBtn;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
    // </editor-fold> 

    //<editor-fold defaultstate="collapsed" desc="Main Methods">
    public void actingAssignmentInfo() {
        saveorUpdateBundle = "Save";
        disableBtnCreate = false;
        switch (createOrSearchBundle) {
            case "New":
                chooseCreatePage = true;
                chooseMainPage = false;
                createOrSearchBundle = "Search";
                headerTitle = "Acting Assignment Request";
                setIcone("ui-icon-search");
                break;
            case "Search":
                chooseCreatePage = false;
                chooseMainPage = true;
                createOrSearchBundle = "New";
                headerTitle = "Search Employee";
                setIcone("ui-icon-plus");
                break;
        }
    }
    
    public void workflowDataModel() {
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
    
    public SelectItem[] getStatusList() {
        SelectItem[] items = new SelectItem[4];
        items[0] = new SelectItem("null", "--- Select ---");
        items[1] = new SelectItem(Constants.PREPARE_VALUE, "Load request list");
        items[2] = new SelectItem(Constants.APPROVE_VALUE, "Load approved list");
        items[3] = new SelectItem(Constants.APPROVE_REJECT_VALUE, "Load rejected list");
        return items;
    }
    
    String slected = "Select One";
    
    public void handleEmployee(ValueChangeEvent event) {
        if (null != event.getNewValue()) {
            slected = event.getNewValue().toString();
            if (slected.equalsIgnoreCase("Position")) {
                chooseEmployee = "false";
                choosePosition = "true";
            } else {
                chooseEmployee = "true";
                choosePosition = "false";
            }
        }
    }
    
    //<editor-fold defaultstate="collapsed" desc="Save Update">
    public Date date() {
        DateFormat dformat = new SimpleDateFormat("dd/mm/yyyy");
        Date datee = new Date();
        dformat.format(datee);
        return datee;
    }
    
    public Integer changedTo() throws ParseException {
        SimpleDateFormat simpledate = new SimpleDateFormat("dd/mm/yyyy");
        String x = simpledate.format(hrActingAssignment.getStartingDate());
        String y = simpledate.format(hrActingAssignment.getEndingDate());
        int dayDiff = StringDateManipulation.compareDateDifference(x, y);
        return dateCalc = dayDiff;
    }
    
    int dateDifference;
    
    public void dateValidation() {
        String startMonth[] = hrActingAssignment.getStartingDate().split("/");
        int isSartMonth = Integer.parseInt(startMonth[1]);
        String startYear[] = hrActingAssignment.getStartingDate().split("/");
        int isStartYear = Integer.parseInt(startYear[2]);
        String startDate[] = hrActingAssignment.getStartingDate().split("/");
        int isStartDate = Integer.parseInt(startDate[0]);
        String endDay[] = hrActingAssignment.getEndingDate().split("/");
        int isEndDay = Integer.parseInt(endDay[0]);
        String endMonth[] = hrActingAssignment.getEndingDate().split("/");
        int isEndMonth = Integer.parseInt(endMonth[1]);
        String endYear[] = hrActingAssignment.getEndingDate().split("/");
        int isEndYear = Integer.parseInt(endYear[2]);
        dateDifference = ((isEndDay - isStartDate) + ((isEndMonth - isSartMonth) * 30) + ((isEndYear - isStartYear) * 365));
    }
    
    public void saveWorkflow() {
        wfHrProcessed.setDecision(hrActingAssignment.getStatus());
        wfHrProcessed.setActingId(hrActingAssignment);
        wfHrProcessed.setProcessedBy(sessionBeanLocal.getUserId());
        wfHrProcessedBeanLocal.saveOrUpdate(wfHrProcessed);
    }
    
    public void saveActingRequest() {
        boolean checkDuplication = actingAssignmentBeanLocal.isRequstExist(hrActingAssignment);
        dateValidation();
        if (dateDifference < 0) {
            JsfUtil.addFatalMessage("End date should be greater than start date. Try again!");
        } else if ((hrActingAssignment.getActingType().equalsIgnoreCase("Employee"))
                && ((hrActingAssignment.getAssignedPerson().getId()).equals(hrActingAssignment.getAssignedBy().getId()))) {
            JsfUtil.addFatalMessage("Can not act yourself");
        } else {
            try {
                AAA securityService = new AAA();
                IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
                String systemBundle = "et/gov/eep/commonApplications/securityServer";
                String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
                if (security.checkAccess(sessionBeanLocal.getUserName(), "saveActingRequest", dataset)) {
                    
                    if (updateStatus == 0 && checkDuplication == false) {
                        hrActingAssignment.setStatus(Constants.PREPARE_VALUE);
                        hrActingAssignment.setPreparedBy(String.valueOf(sessionBeanLocal.getUserId()));
                        hrActingAssignment.setRequestDate(wfHrProcessed.getProcessedOn());
                        hrActingAssignment.setRemark(wfHrProcessed.getCommentGiven());
                        wfHrProcessed.setDecision(hrActingAssignment.getStatus());
                        wfHrProcessed.setActingId(hrActingAssignment);
                        wfHrProcessed.setProcessedBy(sessionBeanLocal.getUserId());
                        actingAssignmentBeanLocal.save(hrActingAssignment);
                        wfHrProcessedBeanLocal.saveOrUpdate(wfHrProcessed);
                        JsfUtil.addSuccessMessage("Successfully saved");
                        clearPage();
                    } else if (updateStatus == 0 && checkDuplication == true) {
                        JsfUtil.addFatalMessage("Already request. Try again!");
                    } else {
                        actingAssignmentBeanLocal.edit(hrActingAssignment);
                        JsfUtil.addSuccessMessage("Successfully updated");
                        clearPage();
                    }
                    
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
            } catch (Exception ex) {
                JsfUtil.addFatalMessage("Error occurred while saving");
                updateStatus = 0;
                saveorUpdateBundle = "Save";
            }
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
        updateStatus = 0;
        saveorUpdateBundle = "Save";
    }
    
    public void clearActingRequest() {
        hrActingAssignment = new HrActingAssignments();
        hrEmployees = new HrEmployees();
        delegateEmp = new HrEmployees();
        hrDepartments = new HrDepartments();
        departments = new HrDepartments();
        hrJobTypes = new HrJobTypes();
        jobTypes = new HrJobTypes();
        hrLuGrades = new HrLuGrades();
        luGrades = new HrLuGrades();
        jobGrades = new HrLuGrades();
        hrLuJobLevels = new HrLuJobLevels();
        updateStatus = 0;
        saveorUpdateBundle = "Save";
        disableBtn = "false";
    }
    //</editor-fold>
    
    public ArrayList<HrEmployees> SearchByFname(String hrEmployee) {
        ArrayList<HrEmployees> employees = null;
        hrEmployees.setFirstName(hrEmployee);
        employees = hrEmployeeBean.SearchByFname(hrEmployees);
        return employees;
    }
    
    public void getByEmpName(SelectEvent event) {
        try {
            hrEmployees.setFirstName(event.getObject().toString());
            hrEmployees = hrEmployeeBean.getByFirstName(hrEmployees);
            hrDepartments = hrEmployees.getDeptId();
            hrJobTypes = hrEmployees.getJobId();
            hrLuGrades = hrEmployees.getGradeId().getGradeId();
            hrActingAssignment.setAssignedPerson(hrEmployees);
//            hrActingAssignment.setAssignedBy(delegateEmp);
        } catch (Exception ex) {
            System.err.println("Exception");
        }
    }
    
    public ArrayList<HrEmployees> SearchByFnameDelegate(String hrEmployee) {
        ArrayList<HrEmployees> employees = null;
        delegateEmp.setFirstName(hrEmployee);
        employees = hrEmployeeBean.SearchByFname(delegateEmp);
        return employees;
    }
    
    public void getByDelegateName(SelectEvent event) {
        try {
            delegateEmp.setFirstName(event.getObject().toString());
            delegateEmp = hrEmployeeBean.getByFirstName(delegateEmp);
            departments = delegateEmp.getDeptId();
            jobTypes = delegateEmp.getJobId();
            luGrades = delegateEmp.getGradeId().getGradeId();
//            hrActingAssignment.setAssignedPerson(hrEmployees);
            hrActingAssignment.setAssignedBy(delegateEmp);
        } catch (Exception ex) {
            System.err.println("Exception");
        }
    }
    
    public ArrayList<HrJobTypes> SearchByJobTitel(String jobTitel) {
        ArrayList<HrJobTypes> job = null;
        hrJobTypes.setJobTitle(jobTitel);
        job = actingAssignmentBeanLocal.SearchByJobTitel(hrJobTypes);
        return job;
    }
    
    public void getByJobTitel(SelectEvent event) {
        try {
            hrJobTypes.setJobTitle(event.getObject().toString());
            hrJobTypes = actingAssignmentBeanLocal.getByJobTitel(hrJobTypes);
            jobGrades = hrJobTypes.getJobGradeId().getGradeId();
            hrLuJobLevels = hrJobTypes.getJobLevelId();
            hrActingAssignment.setActingPosition(hrJobTypes);
        } catch (Exception ex) {
            System.err.println("Exception");
        }
    }
    
    public void getActingAssignmentInfo(ValueChangeEvent event) {
        String acting[] = event.getNewValue().toString().split("--");
        int id = Integer.parseInt(acting[0]);
        hrActingAssignment = actingAssignmentBeanLocal.getActingAssignmentInfo(id);
        setHrEmployees(hrActingAssignment.getAssignedPerson());
        setHrEmployees(hrActingAssignment.getAssignedBy());
        hrEmployees = hrActingAssignment.getAssignedPerson();
        delegateEmp = hrActingAssignment.getAssignedBy();
        hrDepartments = hrEmployees.getDeptId();
        departments = delegateEmp.getDeptId();
        hrJobTypes = hrEmployees.getJobId();
        jobTypes = delegateEmp.getJobId();
        hrLuGrades = hrEmployees.getGradeId().getGradeId();
        luGrades = delegateEmp.getGradeId().getGradeId();
        updateStatus = 1;
        saveorUpdateBundle = "Update";
        disableBtnCreate = false;
        if (hrActingAssignment.getAssignedPerson().getFirstName().equalsIgnoreCase(hrEmployees.toString())
                && hrActingAssignment.getAssignedBy().getFirstName().equalsIgnoreCase(delegateEmp.toString()) && createOrSearchBundle.equals("New")) {
            chooseCreatePage = true;
            chooseMainPage = false;
            createOrSearchBundle = "Search";
            headerTitle = "Search Employee";
            setIcone("ui-icon-search");
        } else {
            chooseCreatePage = false;
            chooseMainPage = true;
            createOrSearchBundle = "New";
            headerTitle = "Acting Assignment Request";
            setIcone("ui-icon-plus");
        }
    }
    
    public List<String> getAllRequests() {
        return actingAssignmentBeanLocal.searchByStatus(0);
    }
    
    public void searchAllReqeust() {
        if (hrActingAssignment.getActingType().isEmpty()) {
            selectedRequest = actingAssignmentBeanLocal.findAllRequest();
            requestsListDataModel = new ListDataModel(selectedRequest);
        } else if (hrActingAssignment.getActingType() != null) {
            selectedRequest = actingAssignmentBeanLocal.findByActingType(hrActingAssignment);
            requestsListDataModel = new ListDataModel(selectedRequest);
        } else if (hrActingAssignment.getActingType().isEmpty() && hrActingAssignment.getRequestDate() != null) {
            selectedRequest = actingAssignmentBeanLocal.findByRequestDate(hrActingAssignment);
            requestsListDataModel = new ListDataModel(selectedRequest);
        } else if (hrActingAssignment.getActingType() != null && hrActingAssignment.getRequestDate() != null) {
            selectedRequest = actingAssignmentBeanLocal.findByTwo(hrActingAssignment);
            requestsListDataModel = new ListDataModel(selectedRequest);
        }
    }
    
    private void populateRequest(Status _status) {
        try {
            actingAssignmentList = actingAssignmentBeanLocal.loadActingRequestList(_status, sessionBeanLocal.getUserId());
            requestsListDataModel = new ListDataModel<>(actingAssignmentList);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    private void populateTable(Status _status) {
        try {
            actingAssignmentList = actingAssignmentBeanLocal.loadActingList(_status, sessionBeanLocal.getUserId());
            requestsListDataModel = new ListDataModel<>(actingAssignmentList);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public void filiterByStatus_Vcl(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            selectedStatus = Integer.valueOf(String.valueOf(event.getNewValue()));
            if (selectedStatus == Constants.PREPARE_VALUE) {
                status.setStatus1(Constants.PREPARE_VALUE);
                populateRequest(status);
            } else if (selectedStatus == Constants.APPROVE_VALUE) {
                status.setStatus1(Constants.APPROVE_VALUE);
                status.setStatus2(Constants.CHECK_APPROVE_VALUE);
                populateTable(status);
            } else if (selectedStatus == Constants.APPROVE_REJECT_VALUE) {
                status.setStatus1(Constants.APPROVE_REJECT_VALUE);
                status.setStatus2(Constants.CHECK_REJECT_VALUE);
                populateTable(status);
            }
        }
    }
    
    public void populateActingAssignment(SelectEvent event) {
        hrActingAssignment = (HrActingAssignments) event.getObject();
        hrActingAssignment = actingAssignmentBeanLocal.loadActingRequestDetails(hrActingAssignment.getId());
        setDelegateEmp(hrActingAssignment.getAssignedBy());
        requestDate = hrActingAssignment.getRequestDate();
        departments = delegateEmp.getDeptId();
        jobTypes = delegateEmp.getJobId();
        luGrades = delegateEmp.getGradeId().getGradeId();
        workflowDataModel();
        if (hrActingAssignment.getActingType().equalsIgnoreCase("Employee")) {
            chooseEmployee = "true";
            choosePosition = "false";
            setHrEmployees(hrActingAssignment.getAssignedPerson());
            hrDepartments = hrEmployees.getDeptId();
            hrJobTypes = hrEmployees.getJobId();
            hrLuGrades = hrEmployees.getGradeId().getGradeId();
        } else {
            chooseEmployee = "false";
            choosePosition = "true";
            setHrJobTypes(hrActingAssignment.getActingPosition());
            jobGrades = hrJobTypes.getJobGradeId().getGradeId();
            hrLuJobLevels = hrJobTypes.getJobLevelId();
        }
        if (hrActingAssignment.getStatus().equals(0)) {
            disableBtn = "false";
        } else {
            disableBtn = "true";
        }
        chooseCreatePage = true;
        chooseMainPage = false;
        createOrSearchBundle = "Search";
        setIcone("ui-icon-search");
        updateStatus = 1;
        saveorUpdateBundle = "Update";
    }
    
    // <editor-fold defaultstate="collapsed" desc="Acting Assignment Approve">
    public List<String> getAllAppReList() {
        return actingAssignmentBeanLocal.searchAppReList(0);
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
        hrActingAssignment = actingAssignmentBeanLocal.getActingAssiInfo(id);
        if (hrActingAssignment.getActingType().equalsIgnoreCase("Employee")) { //for render
            chooseEmployee = "true";
            choosePosition = "false";
        } else {
            chooseEmployee = "false";
            choosePosition = "true";
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
    
    public boolean insert() {
        if (hrEmployees.getId() == null) {
            JsfUtil.addFatalMessage(" Employee Information can not be null ");
        } else {
            try {
                if (selectedValue.equalsIgnoreCase("Approve")) {
                    hrActingAssignment.setStatus(1);
                    actingAssignmentBeanLocal.edit(hrActingAssignment);
                    JsfUtil.addSuccessMessage("Approved Successful.");
                    clearPage();
                } else if (selectedValue.equalsIgnoreCase("Reject")) {
                    hrActingAssignment.setStatus(2);
                    actingAssignmentBeanLocal.edit(hrActingAssignment);
                    JsfUtil.addSuccessMessage("Reject Successful.");
                    clearPage();
                } else {
                    hrActingAssignment.setStatus(3);
                    actingAssignmentBeanLocal.edit(hrActingAssignment);
                    JsfUtil.addSuccessMessage("Resubmit Successful.");
                    clearPage();
                }
            } catch (Exception e) {
                JsfUtil.addFatalMessage("Unable to Approve data.");
            }
        }
        return true;
    }
    // </editor-fold>
//</editor-fold>
    
}
