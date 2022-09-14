/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.controller.attendance;

//<editor-fold defaultstate="collapsed" desc="import">
import et.gov.eep.commonApplications.entity.WfHrProcessed;
import et.gov.eep.commonApplications.mapper.WfHrProcessedFacade;
import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.commonApplications.utility.date.StringDateManipulation;
import et.gov.eep.hrms.businessLogic.attendance.AttendanceDetailBeanLocal;
import et.gov.eep.hrms.businessLogic.attendance.attendanceBeanLocal;
import et.gov.eep.hrms.businessLogic.employee.HrEmployeeBeanLocal;
import et.gov.eep.hrms.entity.attendance.HrAttendances;
import et.gov.eep.hrms.entity.attendance.HrAttendanceDetails;
import et.gov.eep.hrms.entity.employee.HrEmployees;
import et.gov.eep.hrms.entity.organization.HrDepartments;
import et.gov.eep.hrms.entity.organization.HrJobTypes;
import et.gov.eep.hrms.mapper.attendace.HrAttendancesFacade;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URI;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.component.behavior.AjaxBehavior;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import jxl.Cell;
import jxl.Range;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.read.biff.PasswordException;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.UploadedFile;
import securityBean.Constants;
import securityBean.SessionBean;
import securityBean.WorkFlow;
import webService.AAA;
import webService.EventEntry;
import webService.IAdministration;
//</editor-fold>

/**
 *
 * @author meles
 */
@Named(value = "attendanceController")
@ViewScoped
public class AttendanceController implements Serializable {

    //<editor-fold defaultstate="collapsed" desc="variable declaration">
    @EJB
    HrAttendancesFacade hrAttendancesFacade;
    @EJB
    AttendanceDetailBeanLocal attendanceDetailBeanLocal;
    @EJB
    attendanceBeanLocal attendanceBeanLocal;
    @EJB
    HrEmployeeBeanLocal hrEmployeesBeanLocal;
    @EJB
    WfHrProcessedFacade wfHrProcessedFacade;
    @Inject
    HrAttendanceDetails hrsttendanceDetails;
    @Inject
    HrAttendances hrattendances;
    @Inject
    HrEmployees hrEmployees;
    @Inject
    HrJobTypes hrJobTypes;
    @Inject
    HrDepartments hrDepartments;
    @Inject
    WfHrProcessed wfHrProcessed;
    @Inject
    SessionBean SessionBean;
    @Inject
    WorkFlow workFlow;
    DataModel<WfHrProcessed> workFlowDataModel = new ListDataModel<>();
    DataModel<HrAttendances> hrattendancesDataModel = new ListDataModel<>();
    HrAttendances selectedattendance = null;
    DataModel<HrAttendanceDetails> hrsttendanceDetailsDatamodel = new ListDataModel<>();
    List<SelectItem> filterByStatus = new ArrayList<>();
    HrAttendanceDetails selectedRowregidetail = null;
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
    int empStatus;
    Set<String> fieldCheck = new HashSet<>();
    List<HrAttendances> hrList;
    List<HrAttendanceDetails> hratddetailList = new ArrayList<>();
    List<HrAttendances> attendanceList;
    List<HrAttendanceDetails> attendancedetaillist;
    Integer requestNotificationCounter = 0;
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="postconstruct">
    @PostConstruct
    public void init() {
        setListOfYear(listOfBonesYear());
        setListOfmonth(ListofMonths());
        String Shday = StringDateManipulation.toDayInEc();
        hrattendances.setReportedDate(Shday);
        attendanceList = attendanceBeanLocal.findAll();
        hrList = attendanceBeanLocal.findAll(hrattendances);
        requestcounter();
    }

    public AttendanceController() {
    }

//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="getters & setters">
    public void setRequestNotificationCounter(Integer requestNotificationCounter) {
        this.requestNotificationCounter = requestNotificationCounter;
    }

    public Integer getRequestNotificationCounter() {
        return requestNotificationCounter;
    }

    public List<HrAttendances> getHrList() {
        return hrList;
    }

    public void setHrList(List<HrAttendances> hrList) {
        this.hrList = hrList;
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

    public HrAttendances getSelectedattendance() {
        return selectedattendance;
    }

    public void setSelectedattendance(HrAttendances selectedattendance) {
        this.selectedattendance = selectedattendance;
    }

    public DataModel<WfHrProcessed> getWorkFlowDataModel() {
        return workFlowDataModel;
    }

    public void setWorkFlowDataModel(DataModel<WfHrProcessed> workFlowDataModel) {
        this.workFlowDataModel = workFlowDataModel;
    }

    public HrAttendanceDetails getHrAttendanceDetails() {
        if (hrsttendanceDetails == null) {
            hrsttendanceDetails = new HrAttendanceDetails();
        }
        return hrsttendanceDetails;
    }

    public void setHrAttendanceDetails(HrAttendanceDetails hrsttendanceDetails) {
        this.hrsttendanceDetails = hrsttendanceDetails;
    }

    public HrAttendances getHrAttendances() {
        if (hrattendances == null) {
            hrattendances = new HrAttendances();
        }
        return hrattendances;
    }

    public void setHrAttendances(HrAttendances hrattendances) {
        this.hrattendances = hrattendances;
    }

    public DataModel<HrAttendances> getHrAttendancesDataModel() {
        return hrattendancesDataModel;
    }

    public void setHrAttendancesDataModel(DataModel<HrAttendances> hrattendancesDataModel) {
        this.hrattendancesDataModel = hrattendancesDataModel;
    }

    public DataModel<HrAttendanceDetails> getHrAttendanceDetailsDatamodel() {
        return hrsttendanceDetailsDatamodel;
    }

    public void setHrAttendanceDetailsDatamodel(DataModel<HrAttendanceDetails> hrsttendanceDetailsDatamodel) {
        this.hrsttendanceDetailsDatamodel = hrsttendanceDetailsDatamodel;
    }

    public HrAttendanceDetails getSelectedRowregidetail() {
        return selectedRowregidetail;
    }

    public void setSelectedRowregidetail(HrAttendanceDetails selectedRowregidetail) {
        this.selectedRowregidetail = selectedRowregidetail;
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

    public String getAddorUpdate() {
        return addorUpdate;
    }

    public void setAddorUpdate(String addorUpdate) {
        this.addorUpdate = addorUpdate;
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

    public List<HrAttendances> getAttendanceList() {
        return attendanceList;
    }

    public void setAttendanceList(List<HrAttendances> attendanceList) {
        this.attendanceList = attendanceList;
    }

    public List<HrAttendanceDetails> getAttendancedetaillist() {
        return attendancedetaillist;
    }

    public void setAttendancedetaillist(List<HrAttendanceDetails> attendancedetaillist) {
        this.attendancedetaillist = attendancedetaillist;
    }

    public List<SelectItem> getFilterByStatus() {
        return attendanceBeanLocal.Filterstatus();
    }

    public void setFilterByStatus(List<SelectItem> filterByStatus) {
        this.filterByStatus = filterByStatus;
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="main">
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

    public void populateRegistration(SelectEvent events) {
        hrattendances = null;
        hrattendances = (HrAttendances) events.getObject();
        for (HrAttendanceDetails eee : hrattendances.getHrAttendanceDetailsList()) {
            fieldCheck.add(eee.getEmpId().getEmpId());
            System.out.println("fieldCheck" + fieldCheck);
        }
        recreateDataModel();
        recreateDataModelwf();
        renderPnlManPage = false;
        renderPnlCreateAdditional = true;
        createOrSearchBundle = "Search";
        update = 1;
        saveOrUpdateButton = "update";
    }

    public ArrayList<HrAttendances> findByAbsentName(ValueChangeEvent event) {
        hrattendances.setPreparedOn(event.getNewValue().toString());
        hrattendancesDataModel = new ListDataModel(new ArrayList(attendanceBeanLocal.findByabsentdate(hrattendances)));
        return null;

    }

    public void editDataTable() {
        hrsttendanceDetails = hrsttendanceDetailsDatamodel.getRowData();
        hrattendances = hrsttendanceDetails.getAttendanceId();
        hrEmployees = hrsttendanceDetails.getEmpId();
        hrDepartments = hrEmployees.getDeptId();
        hrJobTypes = hrEmployees.getJobId();
        addorUpdate = "Add Changes";
    }

    public void addDetail() {
        empStatus = hrsttendanceDetails.getEmpId().getEmpStatus();
        if (empStatus == 2) {   //*test for on leave status*/
            JsfUtil.addFatalMessage("he or she on leave");
            clearPopup();
        } else if (fieldCheck.contains(hrEmployees.getEmpId())) {

            JsfUtil.addFatalMessage("Data Table Row Duplicated.");
        } else {
            hrattendances.addDetail(hrsttendanceDetails);
            fieldCheck.add(hrEmployees.getEmpId());
            recreateDataModel();
            clearPopup();
        }
    }

    public void requestcounter() {
        hrList = hrAttendancesFacade.findrequestlist();
        requestNotificationCounter = hrList.size();
    }

    public void clearPopup() {
        hrEmployees = new HrEmployees();
        hrDepartments = new HrDepartments();
        hrJobTypes = new HrJobTypes();
        hrsttendanceDetails = null;
        // hrsttendanceDetailsDatamodel=null;
        filterByStatus = null;
        hrattendancesDataModel = null;

    }

    public void recreateDataModel() {
        hrsttendanceDetailsDatamodel = null;
        hrsttendanceDetailsDatamodel = new ListDataModel(new ArrayList(hrattendances.getHrAttendanceDetailsList()));
    }

    public void recreateDataModelwf() {
        workFlowDataModel = null;
        workFlowDataModel = new ListDataModel(new ArrayList(hrattendances.getWfHrProcessedList()));
        for (int i = 0; i < hrattendances.getWfHrProcessedList().size(); i++) {
            if (hrattendances.getWfHrProcessedList().get(i).getDecision() == 0) {
                hrattendances.getWfHrProcessedList().get(i).setAction("Prepared");
            } else if (hrattendances.getWfHrProcessedList().get(i).getDecision() == 3) {
                hrattendances.getWfHrProcessedList().get(i).setAction("Approved");
            } else if (hrattendances.getWfHrProcessedList().get(i).getDecision() == 2 || hrattendances.getWfHrProcessedList().get(i).getDecision() == 4) {
                hrattendances.getWfHrProcessedList().get(i).setAction("Rejected");
            }

        }
    }

    public void recreateDataModel1() {
        hrattendancesDataModel = null;
        hrattendancesDataModel = new ListDataModel(new ArrayList(attendanceBeanLocal.findByabsentdate(hrattendances)));
    }

    public void recreateDataModel2() {
        hrattendancesDataModel = null;
        hrattendancesDataModel = new ListDataModel(new ArrayList(hrList));
    }

    public void clearAttendance() {
        hrsttendanceDetails = new HrAttendanceDetails();
        clearPage();
        clearPopup();

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
            hrDepartments = hrEmployees.getDeptId();
            hrsttendanceDetails.setEmpId(hrEmployees);
        } catch (Exception ex) {
            System.err.println("Exception");
        }
    }

    public void saveAttendace() {
        try {
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(SessionBean.getUserName(), "saveAttendace", dataset)) {
                if (update == 0) {
                    if ((!(getHrAttendanceDetailsDatamodel().getRowCount() > 0))) {
                        JsfUtil.addFatalMessage("can not save empty data");
                    } else {
                        try {
//                        hrattendances.setPreparedBy(String.valueOf(SessionBean.getUserId()));
                            hrattendances.setStatus(Constants.PREPARE_VALUE);
                            attendanceBeanLocal.SaveOrUpdate(hrattendances);
                            wfHrProcessed.setAttendanceId(hrattendances);
                            wfHrProcessed.setProcessedOn(hrattendances.getPreparedOn());
                            wfHrProcessed.setProcessedBy(SessionBean.getUserId());
                            wfHrProcessed.setDecision(hrattendances.getStatus());
                            wfHrProcessed.setCommentGiven(hrattendances.getRemark());
                            wfHrProcessedFacade.saveOrUpdate(wfHrProcessed);
                            JsfUtil.addSuccessMessage("Saved Successfully.");
                            clearPage();
                            clearPopup();
                        } catch (Exception e) {
                            JsfUtil.addFatalMessage("Something occured,unable to saved");
                        }
                    }
                } else {
                    try {
                        if ((!(getHrAttendanceDetailsDatamodel().getRowCount() > 0))) {
                            JsfUtil.addFatalMessage("can not update empty data");
                        } else {
//                        hrattendances.setPreparedBy(String.valueOf(SessionBean.getUserId()));
                            if (hrattendances.getStatus().equals(0)
                                    || hrattendances.getStatus().equals(2)
                                    || hrattendances.getStatus().equals(3)) {
                                attendanceBeanLocal.SaveOrUpdate(hrattendances);
                                wfHrProcessed.setAttendanceId(hrattendances);
                                wfHrProcessed.setProcessedBy(SessionBean.getUserId());
                                wfHrProcessed.setProcessedOn(hrattendances.getPreparedOn());
                                wfHrProcessed.setDecision(hrattendances.getStatus());
                                wfHrProcessedFacade.saveOrUpdate(wfHrProcessed);
                                JsfUtil.addSuccessMessage("Update Successful.");
                                update = 1;
                                saveOrUpdateButton = "Update";
                                clearPage();
                                clearPopup();
                            } else if (hrattendances.getStatus().equals(1)) {
                                JsfUtil.addFatalMessage("can not update approved data");
                            }
                        }
                    } catch (Exception e) {
                        JsfUtil.addFatalMessage("Something occured,unable to update");
                    }

                }

            } else {
                JsfUtil.addFatalMessage("You don't have appropriate permission, please contact the system administrator.");
                EventEntry eventEntry = new EventEntry();
                eventEntry.setSessionId(SessionBean.getSessionID());
                eventEntry.setUserId(SessionBean.getUserId());
                QName qualifiedName = new QName("", "project");
                JAXBElement<String> test = new JAXBElement<String>(qualifiedName, String.class, null, SessionBean.getUserName());
                eventEntry.setUserLogin(test);
//..... add more information by calling fields defined in the object
                security.addEventLog(eventEntry, dataset);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public void clearPage() {
        hrattendances = null;
        hrEmployees = null;
        hrsttendanceDetails = null;
        hrsttendanceDetailsDatamodel = null;
        saveOrUpdateButton = "Save";
        update = 0;
    }

    public void saveAttendaceApprove() {

        try {
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(SessionBean.getUserName(), "saveAttendaceApprove", dataset)) {
                if (hrsttendanceDetails == null) {
                    if (hrattendances.getStatus().equals(1)) {
                        JsfUtil.addFatalMessage("Can't Approve empty data");
                    } else if (hrattendances.getStatus().equals(2)) {
                        JsfUtil.addFatalMessage("Can't Reject empty data");
                    }
                } else if ((!(getHrAttendanceDetailsDatamodel().getRowCount() > 0))) {
                    if (hrattendances.getStatus().equals(1)) {
                        JsfUtil.addFatalMessage("Can't Approve empty dataTable");
                    } else if (hrattendances.getStatus().equals(2)) {
                        JsfUtil.addFatalMessage("Can't Reject empty dataTable");
                    }

                } else {
                    try {
                        hrattendances.setStatus(hrattendances.getStatus());
                        if (hrattendances.getStatus().equals(1) && workFlow.isApproveStatus()) {
                            workFlow.setUserStatus(Constants.APPROVE_VALUE);
                            hrattendances.setStatus(workFlow.getUserStatus());
                            wfHrProcessed.setCommentGiven(hrattendances.getRemark());
                            wfHrProcessed.setAttendanceId(hrattendances);
                            wfHrProcessed.setProcessedBy(SessionBean.getUserId());
                            wfHrProcessed.setProcessedOn(hrattendances.getPreparedOn());
                            wfHrProcessed.setDecision(hrattendances.getStatus());
                            attendanceBeanLocal.SaveOrUpdate(hrattendances);
                            wfHrProcessedFacade.saveOrUpdate(wfHrProcessed);
                            JsfUtil.addSuccessMessage("Approve Successfully");
                            clearPage();
                            clearPopup();
                        } else if (hrattendances.getStatus().equals(1) && workFlow.isCheckStatus()) {
                            workFlow.setUserStatus(Constants.CHECK_APPROVE_VALUE);
                            hrattendances.setStatus(workFlow.getUserStatus());
                            wfHrProcessed.setCommentGiven(hrattendances.getRemark());
                            wfHrProcessed.setAttendanceId(hrattendances);
                            wfHrProcessed.setProcessedBy(SessionBean.getUserId());
                            wfHrProcessed.setProcessedOn(hrattendances.getPreparedOn());
                            wfHrProcessed.setDecision(hrattendances.getStatus());
                            attendanceBeanLocal.SaveOrUpdate(hrattendances);
                            wfHrProcessedFacade.saveOrUpdate(wfHrProcessed);
                            JsfUtil.addSuccessMessage("approved Successfully");
                            clearPage();
                            clearPopup();
                        } else if (hrattendances.getStatus().equals(2) && workFlow.isApproveStatus()) {
                            workFlow.setUserStatus(Constants.APPROVE_REJECT_VALUE);
                            hrattendances.setStatus(workFlow.getUserStatus());
                            wfHrProcessed.setCommentGiven(hrattendances.getRemark());
                            wfHrProcessed.setAttendanceId(hrattendances);
                            wfHrProcessed.setProcessedBy(SessionBean.getUserId());
                            wfHrProcessed.setProcessedOn(hrattendances.getPreparedOn());
                            wfHrProcessed.setDecision(hrattendances.getStatus());
                            attendanceBeanLocal.SaveOrUpdate(hrattendances);
                            wfHrProcessedFacade.saveOrUpdate(wfHrProcessed);
                            JsfUtil.addSuccessMessage("Rejected Successfully");
                            clearPage();
                            clearPopup();
                        } else if (hrattendances.getStatus().equals(2) && workFlow.isCheckStatus()) {
                            workFlow.setUserStatus(Constants.CHECK_REJECT_VALUE);
                            hrattendances.setStatus(workFlow.getUserStatus());
                            wfHrProcessed.setCommentGiven(hrattendances.getRemark());
                            wfHrProcessed.setAttendanceId(hrattendances);
                            wfHrProcessed.setProcessedBy(SessionBean.getUserId());
                            wfHrProcessed.setProcessedOn(hrattendances.getPreparedOn());
                            wfHrProcessed.setDecision(hrattendances.getStatus());
                            attendanceBeanLocal.SaveOrUpdate(hrattendances);
                            wfHrProcessedFacade.saveOrUpdate(wfHrProcessed);
                            JsfUtil.addSuccessMessage("Rejected Successfully");
                            clearPage();
                            clearPopup();
                        }

                        clearAttendance();
                    } catch (Exception ex) {
                        JsfUtil.addFatalMessage("Unable to Approve Or Reject data");
                    }
                }

            } else {
                JsfUtil.addFatalMessage("You don't have appropriate permission, please contact the system administrator.");
                EventEntry eventEntry = new EventEntry();
                eventEntry.setSessionId(SessionBean.getSessionID());
                eventEntry.setUserId(SessionBean.getUserId());
                QName qualifiedName = new QName("", "project");
                JAXBElement<String> test = new JAXBElement<String>(qualifiedName, String.class, null, SessionBean.getUserName());
                eventEntry.setUserLogin(test);
//..... add more information by calling fields defined in the object
                security.addEventLog(eventEntry, dataset);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void filiterByByAbsent_Date(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            hrattendances = (HrAttendances) event.getNewValue();
        }
    }

    public void filiterByStatus_VclAttendance(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            status = Integer.valueOf(event.getNewValue().toString());
            populateTable();
        }
    }

    private void populateTable() {
        try {
            List<HrAttendances> readFiltereddata = attendanceBeanLocal.loadFiltereddata(status);
            hrattendancesDataModel = new ListDataModel(readFiltereddata);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void attendanceapproveDisplayChanged(SelectEvent event) {
        hrattendances = (HrAttendances) event.getObject();
        hrattendances.setId(hrattendances.getId());
        recreateDataModel();
        recreateDataModelwf();
        renderPnlManPage = false;
        renderPnlCreateAdditional = true;
        createOrSearchBundle = "Search";
        update = 1;
        saveOrUpdateButton = "Save";
    }
    private ArrayList<SelectItem> listOfYear = new ArrayList<>();
    private ArrayList<SelectItem> listOfmonth = new ArrayList<>();

    public ArrayList<SelectItem> getListOfmonth() {
        return listOfmonth;
    }

    public void setListOfmonth(ArrayList<SelectItem> listOfmonth) {
        this.listOfmonth = listOfmonth;
    }

    public ArrayList<SelectItem> getListOfYear() {
        return listOfYear;
    }

    public void setListOfYear(ArrayList<SelectItem> listOfYear) {
        this.listOfYear = listOfYear;
    }

    public ArrayList<SelectItem> listOfBonesYear() {
        ArrayList<SelectItem> list_Year = new ArrayList<>();
        int year = getYear(StringDateManipulation.toDayInEc());
        list_Year.add(new SelectItem(null, "-- Select --"));
        for (int i = 0; i < 1; i++) {
            list_Year.add(new SelectItem(Integer.toString(year), Integer.toString(year)));
            year -= 1;

        }
        return list_Year;
    }

    private int getYear(String _date) {
        String dmy[] = _date.split("-");

        return Integer.parseInt(dmy[0]);
    }

    public ArrayList<SelectItem> listOfmonth() {
        ArrayList<SelectItem> list_Year = new ArrayList<>();
        int year = getmonth(StringDateManipulation.toDayInEc());
        list_Year.add(new SelectItem(null, "-- Select --"));
        for (int i = 0; i < 12; i++) {
            list_Year.add(new SelectItem(Integer.toString(year), Integer.toString(year)));
            year -= 1;

        }
        return list_Year;
    }

    private int getmonth(String _date) {
        String dmy[] = _date.split("-");

        return Integer.parseInt(dmy[1]);
    }
    private ArrayList<SelectItem> ListofMonths = new ArrayList<>();

    public ArrayList<SelectItem> getListofMonths() {
        return ListofMonths;
    }

    public void setListofMonths(ArrayList<SelectItem> ListofMonths) {
        this.ListofMonths = ListofMonths;
    }

    public ArrayList<SelectItem> ListofMonths() {
        ArrayList<SelectItem> list_Bones = new ArrayList<>();
        list_Bones.add(new SelectItem(null, "-- Select --"));
        list_Bones.add(new SelectItem("01", "September "));
        list_Bones.add(new SelectItem("02", "October "));
        list_Bones.add(new SelectItem("03", "November"));
        list_Bones.add(new SelectItem("04", "December"));
        list_Bones.add(new SelectItem("05", "January"));
        list_Bones.add(new SelectItem("06", "February"));
        list_Bones.add(new SelectItem("07", "March"));
        list_Bones.add(new SelectItem("08", "April"));
        list_Bones.add(new SelectItem("09", "May "));
        list_Bones.add(new SelectItem("10", "June"));
        list_Bones.add(new SelectItem("11", "July"));
        list_Bones.add(new SelectItem("12", "Augest"));
        return list_Bones;
    }

    public void VCLINPUTABSENTDATE(AjaxBehavior ajaxBehavior) {
        String Shday[] = hrsttendanceDetails.getDateOfAbsence().split(",");
        hrsttendanceDetails.setNoOfDaysAbsent(BigInteger.valueOf(Shday.length));
        int Ihday = Integer.parseInt(Shday[Shday.length - 1]);
        if (Ihday > 30) {
            JsfUtil.addFatalMessage("date can not be greater than 30!!!!");
        } else if (Ihday == 0) {
            JsfUtil.addFatalMessage("date can not be zero!!!!");
        }
    }

    public void saveOrUpdate() {
        hrAttendancesFacade.saveOrUpdate(hrattendances);
        JsfUtil.addSuccessMessage("  report Insertd! ");
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="excel upload">
    // eclear the constat value for the coloum inorder to recognized ---------
    private static final int ACADEMICYEAR = 0;
    private static final int FIELDOFSTUDY = 2;
    private static final int ZONEID = 3;
    private static final int COMMON_DATA = 7;
    private static final int ID = 13;
    private static final int ADDRESSID = 14;
    private static final int DATA_CHECK = 14;
    private static final int FULLNAME = 1;
    private static final int SEX = 2;
    private static final int AGE = 3;
    private static final int COMPLETED_GRADELEVEL = 4;
    private static final int COMPLETED_ACADEMICYEAR = 5;
    private static final int LAST_EXAM_TAKEN_YEAR = 6;
    private static final int ZONE = 7;
    private static final int WOREDA = 8;
    private static final int WRITTENEXAM = 9;
    private static final int INTERVIEW = 10;

    private UploadedFile uploadedFile;
    String filePath;
    String empid;

    public String getEmpid() {
        return empid;
    }

    public void setEmpid(String empid) {
        this.empid = empid;
    }
    List<HrEmployees> hrEmployeesList;

    public List<HrEmployees> getHrEmployeesList() {
        return hrEmployeesList;
    }

    public void setHrEmployeesList(List<HrEmployees> hrEmployeesList) {
        this.hrEmployeesList = hrEmployeesList;
    }

//AT AU AV AW AX AY AZ
    public void handleFileUpload(FileUploadEvent event) throws FileNotFoundException, IOException, ParseException, BiffException {
        FacesMessage message = new FacesMessage("Succesful", event.getFile().getFileName() + " is uploaded.");
        uploadedFile = event.getFile();
        String filePath = "C:\\Users\\meles\\Desktop";
        boolean success = (new File(filePath)).mkdirs();
        if (success) {
            readExcel2003(filePath + event.getFile().getFileName());

        } else {
            readExcel2003(filePath + event.getFile().getFileName());
        }

    }

    public void readExcel2003(String filePath) throws FileNotFoundException, ParseException, IOException, BiffException {
        this.filePath = filePath;
        String noOfDays;
        String[] str = filePath.split("\\.");
        if (str[1].equals("xls")) {
            try {
                Workbook attendanceworkbook = Workbook.getWorkbook(uploadedFile.getInputstream());
                Sheet attendancesheet = attendanceworkbook.getSheet(0);
                int totalRows = attendancesheet.getRows();
                int totalColumns = attendancesheet.getColumns();
                for (int i = 1; i < totalRows; i++) {
                    HrAttendanceDetails sf = new HrAttendanceDetails();
                    sf.setLateMinute(attendancesheet.getCell(2, i).getContents());
                    noOfDays = attendancesheet.getCell(1, i).getContents();
                    sf.setNoOfDaysAbsent(BigInteger.valueOf(Long.parseLong(noOfDays)));
                    sf.setDateOfAbsence(attendancesheet.getCell(4, i).getContents());
                    empid = attendancesheet.getCell(0, i).getContents();
                    hrEmployeesList = hrEmployeesBeanLocal.SearchByEmpId(empid);
                    for (int j = 0; j < hrEmployeesList.size(); j++) {
                        sf.setEmpId(hrEmployeesList.get(j));
                    }
                    String earlyout;
                    earlyout = attendancesheet.getCell(3, i).getContents();
                    sf.setEarlyOut(BigInteger.valueOf(Long.parseLong(earlyout)));
                    hratddetailList.add(sf);
                    hrattendances.addDetail(sf);
                }
                hrsttendanceDetailsDatamodel = null;
                hrsttendanceDetailsDatamodel = new ListDataModel(new ArrayList<>(hratddetailList));
                attendanceworkbook.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
//</editor-fold>

}
