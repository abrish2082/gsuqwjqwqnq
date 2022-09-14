/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.controller.transfer;

import et.gov.eep.commonApplications.businessLogic.WfHrProcessedBeanLocal;
import et.gov.eep.commonApplications.entity.WfHrProcessed;
import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.commonApplications.utility.date.StringDateManipulation;
import et.gov.eep.fcms.businessLogic.admin.FmsCostCSystemJunctionBeanLocal;
import et.gov.eep.fcms.businessLogic.admin.FmsCostCenterBeanLocal;
import et.gov.eep.fcms.businessLogic.admin.FmsLuSystemBeanLocal;
import et.gov.eep.fcms.entity.admin.FmsCostCenter;
import et.gov.eep.fcms.entity.admin.FmsCostcSystemJunction;
import et.gov.eep.fcms.entity.admin.FmsLuSystem;
import et.gov.eep.hrms.businessLogic.payroll.HrPayrollPgPlDeptBeanLocal;
import et.gov.eep.hrms.businessLogic.payroll.HrPayrollPlPgBeanLocal;
import et.gov.eep.hrms.businessLogic.transfer.InternalMovementBeanLocal;
import et.gov.eep.hrms.businessLogic.transfer.TransferApproveBeanLocal;
import et.gov.eep.hrms.entity.employee.HrEmpEducations;
import et.gov.eep.hrms.entity.employee.HrEmployees;
import et.gov.eep.hrms.entity.lookup.HrLuGrades;
import et.gov.eep.hrms.entity.lookup.HrLuTransferTypes;
import et.gov.eep.hrms.entity.medical.HrLocalMedSettlements;
import et.gov.eep.hrms.entity.organization.HrDepartments;
import et.gov.eep.hrms.entity.organization.HrJobTypes;
import et.gov.eep.hrms.entity.organization.HrSalaryScaleRanges;
import et.gov.eep.hrms.entity.organization.HrSalaryScales;
import et.gov.eep.hrms.entity.payroll.HrPayrollPlPg;
import et.gov.eep.hrms.entity.payroll.HrPayrollPlPgDept;
import et.gov.eep.hrms.entity.transfer.HrEmpInternalHistory;
import et.gov.eep.hrms.entity.transfer.HrTransferRequests;
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
@Named(value = "transferApproveController")
@ViewScoped
public class TransferApproveController implements Serializable {

//<editor-fold defaultstate="collapsed" desc="Entity Injection">
    @Inject
            HrTransferRequests hrTransferRequests;
    @Inject
            HrEmpInternalHistory empInternalHistory;
    @Inject
            HrEmployees hrEmployees;
    @Inject
            HrDepartments hrDepartments;
    @Inject
            HrDepartments transferTo;
    @Inject
            HrLuTransferTypes hrLuTransferTypes;
    @Inject
            HrJobTypes hrJobTypes;
    @Inject
            HrSalaryScaleRanges hrSalaryScaleRanges;
    @Inject
            HrSalaryScaleRanges renderScaleRanges;
    @Inject
            HrSalaryScales hrSalaryScales;
    @Inject
            HrLuGrades hrLuGrades;
    @Inject
            HrEmpEducations hrEmpEducations;
    @Inject
            HrPayrollPlPgDept hrPayrollPlPgDept;
    @Inject
            FmsCostcSystemJunction fmsCostcSystemJunction;
    @Inject
            WfHrProcessed wfHrProcessed;
    @Inject
            SessionBean sessionBeanLocal;
    @Inject
            WorkFlow workFlow;
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="EJB Declaration">
    @EJB
            TransferApproveBeanLocal transferApproveBeanLocal;
    @EJB
            InternalMovementBeanLocal internalMovementBeanLocal;
    @EJB
            FmsCostCSystemJunctionBeanLocal fmsCostCSystemJunctionBeanLocal;
    @Inject
            HrPayrollPgPlDeptBeanLocal hrPayrollPgPlDeptBeanLocal;
    @EJB
            WfHrProcessedBeanLocal wfHrProcessedBeanLocal;
//</editor-fold>
    
//<editor-fold defaultstate="collapsed" desc="Variable declaration">
    private String createOrSearchBundle = "New";
    private String headerTitle = "Search Request";
    private String icone = "ui-icon-document";
    private boolean disableBtnCreate;
    private boolean chooseCreatePage = false;
    private boolean chooseMainPage = true;
    private String disableBtn = "false";
    DataModel<HrTransferRequests> transferDataModel;
    DataModel<WfHrProcessed> workflowDataModel = new ListDataModel<>();
    private List<HrTransferRequests> selectedRequest;
    List<SelectItem> filterByStatus = new ArrayList<>();
    private List<HrTransferRequests> transferList;
    private String chooseRelocation;
    private String chooseTransfer;
    private String choosePermanent = chooseTransfer = chooseRelocation = "true";
    private String chooseTemporary = "false";
    int approveType = 0;
    String emp;
    String system;
    String costCenter;
    String payLocation;
    String payGroup;
    String system1;
    String costCenter1;
    String payLocation1;
    String payGroup1;
    String empId;
    String fromDeptId;
    HrEmployees empHis;
    int deptId;
    String fromDeptName = null;
    String fromJobId = null;
    String fromJobTitle = null;
    String fromGradeId = null;
    String fromGrade = null;
    String fromSalaryStepId = null;
    String fromSalary = null;
    String toDeptId = null;
    String toDeptName = null;
    String toJobId = null;
    String toJobTitle = null;
    String toGradeId = null;
    String toGrade = null;
    String toSalaryStepId = null;
    String toSalaryStep = null;
    String toSalary = null;
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Post construct">
    public TransferApproveController() {
    }
    
    @PostConstruct
    public void init() {
        hrTransferRequests.setApproveDate(StringDateManipulation.toDayInEc());
        wfHrProcessed.setProcessedOn(StringDateManipulation.toDayInEc());
        transferList = transferApproveBeanLocal.findPreparedList();
    }
//</editor-fold>
    
//<editor-fold defaultstate="collapsed" desc="Main Methods">
    public void workflowDataModel() {
        workflowDataModel = null;
        workflowDataModel = new ListDataModel(new ArrayList(hrTransferRequests.getWfHrProcessedList()));
    }
    
    //<editor-fold defaultstate="collapsed" desc="Getter and Setter">
    public HrTransferRequests getHrTransferRequests() {
        return hrTransferRequests;
    }
    
    public void setHrTransferRequests(HrTransferRequests hrTransferRequests) {
        this.hrTransferRequests = hrTransferRequests;
    }
    
    public HrEmployees getHrEmployees() {
        return hrEmployees;
    }
    
    public void setHrEmployees(HrEmployees hrEmployees) {
        this.hrEmployees = hrEmployees;
    }
    
    public HrDepartments getHrDepartments() {
        return hrDepartments;
    }
    
    public void setHrDepartments(HrDepartments hrDepartments) {
        this.hrDepartments = hrDepartments;
    }
    
    public HrDepartments getTransferTo() {
        return transferTo;
    }
    
    public void setTransferTo(HrDepartments transferTo) {
        this.transferTo = transferTo;
    }
    
    public HrLuTransferTypes getHrLuTransferTypes() {
        return hrLuTransferTypes;
    }
    
    public void setHrLuTransferTypes(HrLuTransferTypes hrLuTransferTypes) {
        this.hrLuTransferTypes = hrLuTransferTypes;
    }
    
    public HrJobTypes getHrJobTypes() {
        return hrJobTypes;
    }
    
    public void setHrJobTypes(HrJobTypes hrJobTypes) {
        this.hrJobTypes = hrJobTypes;
    }
    
    public HrSalaryScaleRanges getHrSalaryScaleRanges() {
        return hrSalaryScaleRanges;
    }
    
    public void setHrSalaryScaleRanges(HrSalaryScaleRanges hrSalaryScaleRanges) {
        this.hrSalaryScaleRanges = hrSalaryScaleRanges;
    }
    
    public HrSalaryScaleRanges getRenderScaleRanges() {
        return renderScaleRanges;
    }
    
    public void setRenderScaleRanges(HrSalaryScaleRanges renderScaleRanges) {
        this.renderScaleRanges = renderScaleRanges;
    }
    
    public HrSalaryScales getHrSalaryScales() {
        return hrSalaryScales;
    }
    
    public void setHrSalaryScales(HrSalaryScales hrSalaryScales) {
        this.hrSalaryScales = hrSalaryScales;
    }
    
    public HrLuGrades getHrLuGrades() {
        return hrLuGrades;
    }
    
    public void setHrLuGrades(HrLuGrades hrLuGrades) {
        this.hrLuGrades = hrLuGrades;
    }
    
    public HrEmpEducations getHrEmpEducations() {
        return hrEmpEducations;
    }
    
    public void setHrEmpEducations(HrEmpEducations hrEmpEducations) {
        this.hrEmpEducations = hrEmpEducations;
    }
    
    public WfHrProcessed getWfHrProcessed() {
        return wfHrProcessed;
    }
    
    public void setWfHrProcessed(WfHrProcessed wfHrProcessed) {
        this.wfHrProcessed = wfHrProcessed;
    }
    
    public DataModel<WfHrProcessed> getWorkflowDataModel() {
        return workflowDataModel;
    }
    
    public void setWorkflowDataModel(DataModel<WfHrProcessed> workflowDataModel) {
        this.workflowDataModel = workflowDataModel;
    }
    
    public List<HrTransferRequests> getTransferList() {
        if (transferList == null) {
            transferList = new ArrayList<>();
        }
        return transferList;
    }
    
    public void setTransferList(List<HrTransferRequests> transferList) {
        this.transferList = transferList;
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
    
    public String getDisableBtn() {
        return disableBtn;
    }
    
    public void setDisableBtn(String disableBtn) {
        this.disableBtn = disableBtn;
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
    
    public DataModel<HrTransferRequests> getTransferDataModel() {
        return transferDataModel;
    }
    
    public void setTransferDataModel(DataModel<HrTransferRequests> transferDataModel) {
        this.transferDataModel = transferDataModel;
    }
    
    public List<SelectItem> getFilterByStatus() {
        return transferApproveBeanLocal.filterByStatus();
    }
    
    public void setFilterByStatus(List<SelectItem> filterByStatus) {
        this.filterByStatus = filterByStatus;
    }
    
    public List<HrTransferRequests> getSelectedRequest() {
        return selectedRequest;
    }
    
    public void setSelectedRequest(List<HrTransferRequests> selectedRequest) {
        this.selectedRequest = selectedRequest;
    }
    
    public String getChooseRelocation() {
        return chooseRelocation;
    }
    
    public void setChooseRelocation(String chooseRelocation) {
        this.chooseRelocation = chooseRelocation;
    }
    
    public String getChooseTransfer() {
        return chooseTransfer;
    }
    
    public void setChooseTransfer(String chooseTransfer) {
        this.chooseTransfer = chooseTransfer;
    }
    
    public String getChoosePermanent() {
        return choosePermanent;
    }
    
    public void setChoosePermanent(String choosePermanent) {
        this.choosePermanent = choosePermanent;
    }
    
    public String getChooseTemporary() {
        return chooseTemporary;
    }
    
    public void setChooseTemporary(String chooseTemporary) {
        this.chooseTemporary = chooseTemporary;
    }
    
    public int getApproveType() {
        return approveType;
    }
    
    public void setApproveType(int approveType) {
        this.approveType = approveType;
    }
    
    public String getEmp() {
        return emp;
    }
    
    public void setEmp(String emp) {
        this.emp = emp;
    }
    
    public String getSystem() {
        return system;
    }
    
    public void setSystem(String system) {
        this.system = system;
    }
    
    public String getCostCenter() {
        return costCenter;
    }
    
    public void setCostCenter(String costCenter) {
        this.costCenter = costCenter;
    }
    
    public String getPayLocation() {
        return payLocation;
    }
    
    public void setPayLocation(String payLocation) {
        this.payLocation = payLocation;
    }
    
    public String getPayGroup() {
        return payGroup;
    }
    
    public void setPayGroup(String payGroup) {
        this.payGroup = payGroup;
    }
    
    public String getSystem1() {
        return system1;
    }
    
    public void setSystem1(String system1) {
        this.system1 = system1;
    }
    
    public String getCostCenter1() {
        return costCenter1;
    }
    
    public void setCostCenter1(String costCenter1) {
        this.costCenter1 = costCenter1;
    }
    
    public String getPayLocation1() {
        return payLocation1;
    }
    
    public void setPayLocation1(String payLocation1) {
        this.payLocation1 = payLocation1;
    }
    
    public String getPayGroup1() {
        return payGroup1;
    }
    
    public void setPayGroup1(String payGroup1) {
        this.payGroup1 = payGroup1;
    }
    //</editor-fold>
    
    List<String> StatusAppRej = new ArrayList<>();
    
    public SelectItem[] getAllState() {
        StatusAppRej.add("Approve");
        StatusAppRej.add("Reject");
        StatusAppRej.add("Resubmit");
        StatusAppRej.add("Forwared");
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
        hrTransferRequests = transferApproveBeanLocal.getTransferInfo(id);
        if (hrTransferRequests.getTransferTypeId().getTransferType().equalsIgnoreCase("Temporary")) { //for render
            chooseTemporary = "true";
            choosePermanent = "false";
        } else {
            chooseTemporary = "false";
            choosePermanent = "true";
        }
        setHrEmployees(hrTransferRequests.getRequesterId());
        setHrLuTransferTypes(hrTransferRequests.getTransferTypeId());
        if (hrTransferRequests.getRequesterId().getFirstName().equalsIgnoreCase(hrEmployees.toString()) && (createOrSearchBundle.equals("New"))) {
            chooseCreatePage = true;
            chooseMainPage = false;
            createOrSearchBundle = "Search";
            headerTitle = "Transfer Request";
            setIcone("ui-icon-search");
        } else {
            chooseCreatePage = false;
            chooseMainPage = true;
            createOrSearchBundle = "New";
            headerTitle = "Search Request";
            setIcone("ui-icon-document");
        }
        hrSalaryScaleRanges = hrEmployees.getGradeId();
        renderScaleRanges = hrEmployees.getGradeId();
        hrLuGrades.setGrade(hrSalaryScaleRanges.getGradeId().getGrade());
        hrDepartments = hrEmployees.getDeptId();
        transferTo = hrTransferRequests.getTransferTo();
        hrJobTypes = hrEmployees.getJobId();
        emp = hrTransferRequests.getRequesterId().getFirstName() + " " + hrTransferRequests.getRequesterId().getMiddleName() + " "
                + hrTransferRequests.getRequesterId().getLastName();
        try {
            if (hrTransferRequests.getStatus() != null) {
                if (hrTransferRequests.getStatus().equals(1)) {
                    approveType = 1;
                } else if (hrTransferRequests.getStatus().equals(2)) {
                    approveType = 2;
                } else if (hrTransferRequests.getStatus().equals(3)) {
                    approveType = 3;
                } else {
                    approveType = 4;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public List<String> getAllAppReList() {
        return transferApproveBeanLocal.searchAppReList(0);
    }
    
    public void editEmployee() {
        hrEmployees = hrTransferRequests.getRequesterId();
        hrEmployees.setDeptId(hrTransferRequests.getTransferTo());
        transferApproveBeanLocal.editEmp(hrEmployees);
    }
    
    public void insertToHistory() {
        empInternalHistory = new HrEmpInternalHistory();
        String transferId = String.valueOf(hrTransferRequests.getId());
        String currentDate = StringDateManipulation.toDayInEc();
        empInternalHistory.setProcessType("Transfer");
        empInternalHistory.setEmpId(empHis);
        empInternalHistory.setOldDepId(deptId);
        empInternalHistory.setOldDepName(fromDeptName);
        empInternalHistory.setProcessId(transferId);
        empInternalHistory.setProcessDate(currentDate);
        internalMovementBeanLocal.saveOrUpdate(empInternalHistory);
    }
    
    int dateDifference;
    
    public void dateValidation() {
        String startMonth[] = hrTransferRequests.getApproveDate().split("/");
        int isSartMonth = Integer.parseInt(startMonth[1]);
        String startYear[] = hrTransferRequests.getApproveDate().split("/");
        int isStartYear = Integer.parseInt(startYear[2]);
        String startDate[] = hrTransferRequests.getApproveDate().split("/");
        int isStartDate = Integer.parseInt(startDate[0]);
        String endDay[] = hrTransferRequests.getEffectiveDate().split("/");
        int isEndDay = Integer.parseInt(endDay[0]);
        String endMonth[] = hrTransferRequests.getEffectiveDate().split("/");
        int isEndMonth = Integer.parseInt(endMonth[1]);
        String endYear[] = hrTransferRequests.getEffectiveDate().split("/");
        int isEndYear = Integer.parseInt(endYear[2]);
        dateDifference = ((isEndDay - isStartDate) + ((isEndMonth - isSartMonth) * 30) + ((isEndYear - isStartYear) * 365));
    }
    
    public boolean approve() {
        if (hrEmployees.getId() == null) {
            JsfUtil.addFatalMessage(" Employee Information must not be null ");
        } else if (hrTransferRequests.getTransferTo() == null) {
            JsfUtil.addFatalMessage("Department can not be empty");
        } else {
            try {
                if (selectedValue.equalsIgnoreCase("Approve")) {
                    hrTransferRequests.setStatus(1);
                    transferApproveBeanLocal.edit(hrTransferRequests);
                    editEmployee();
                    JsfUtil.addSuccessMessage("Successfully Approved.");
                    clearPage();
                } else if (selectedValue.equalsIgnoreCase("Reject")) {
                    hrTransferRequests.setStatus(2);
                    transferApproveBeanLocal.edit(hrTransferRequests);
                    JsfUtil.addSuccessMessage("Successfully Reject.");
                    clearPage();
                } else if (selectedValue.equalsIgnoreCase("Resubmit")) {
                    hrTransferRequests.setStatus(3);
                    transferApproveBeanLocal.edit(hrTransferRequests);
                    JsfUtil.addSuccessMessage("Successfully Resubmit.");
                    clearPage();
                } else {
                    hrTransferRequests.setStatus(4);
                    transferApproveBeanLocal.edit(hrTransferRequests);
                    JsfUtil.addSuccessMessage("Successfully Forwared.");
                    clearPage();
                }
            } catch (Exception e) {
                JsfUtil.addFatalMessage("Unable to Approve data.");
            }
        }
        return true;
    }
    
    public void saveTransferApprove() {
        try {
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(sessionBeanLocal.getUserName(), "saveTransferApprove", dataset)) {
                
                if (hrEmployees.getId() == null) {
                    JsfUtil.addFatalMessage("Can't get employee information");
                } else if (hrTransferRequests.getTransferTo() == null) {
                    JsfUtil.addFatalMessage("Department can not be empty");
                } else {
                    if (selectedValue.equalsIgnoreCase("1") && workFlow.isApproveStatus()) {
                        workFlow.setUserStatus(Constants.APPROVE_VALUE);
                        hrTransferRequests.setStatus(workFlow.getUserStatus());
                    } else if (selectedValue.equalsIgnoreCase("1") && workFlow.isCheckStatus()) {
                        workFlow.setUserStatus(Constants.CHECK_APPROVE_VALUE);
                        hrTransferRequests.setStatus(workFlow.getUserStatus());
                    } else if (selectedValue.equalsIgnoreCase("2") && workFlow.isApproveStatus()) {
                        workFlow.setUserStatus(Constants.APPROVE_REJECT_VALUE);
                        hrTransferRequests.setStatus(workFlow.getUserStatus());
                    } else if (selectedValue.equalsIgnoreCase("2") && workFlow.isCheckStatus()) {
                        workFlow.setUserStatus(Constants.CHECK_REJECT_VALUE);
                        hrTransferRequests.setStatus(workFlow.getUserStatus());
                    }
                    wfHrProcessed.setTransferId(hrTransferRequests);
                    wfHrProcessed.setDecision(hrTransferRequests.getStatus());
                    wfHrProcessed.setProcessedBy(sessionBeanLocal.getUserId());
                    transferApproveBeanLocal.edit(hrTransferRequests);
                    wfHrProcessedBeanLocal.saveOrUpdate(wfHrProcessed);
                    transferList = transferApproveBeanLocal.findPreparedList();
                    editEmployee();
                    insertToHistory();
                    clearPage();
                    JsfUtil.addSuccessMessage("Successfully saved for the final state");
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
            ex.printStackTrace();
            JsfUtil.addFatalMessage("Error occurs while saving");
        }
    }
    
    private String clearPage() {
        hrTransferRequests = null;
        hrEmployees = null;
        hrDepartments = null;
        transferTo = null;
        hrJobTypes = null;
        hrLuTransferTypes = null;
        wfHrProcessed = new WfHrProcessed();
        workflowDataModel = null;
        system = null;
        costCenter = null;
        payLocation = null;
        payGroup = null;
        system1 = null;
        costCenter1 = null;
        payLocation1 = null;
        payGroup1 = null;
        emp = null;
        return null;
    }
    
    public void transferRequestInfo() {
        disableBtnCreate = false;
        if (createOrSearchBundle.equals("New")) {
            chooseCreatePage = true;
            chooseMainPage = false;
            createOrSearchBundle = "Search";
            headerTitle = "Transfer Request";
            setIcone("ui-icon-search");
        } else if (createOrSearchBundle.equals("Search")) {
            chooseCreatePage = false;
            chooseMainPage = true;
            createOrSearchBundle = "New";
            headerTitle = "Search Request";
            setIcone("ui-icon-document");
        }
    }
    
    public void searchPage() {
        chooseCreatePage = false;
        chooseMainPage = true;
        transferDataModel = null;
    }
    
    public void searchAllReqeust() {
        if (hrEmployees.getEmpId().isEmpty() && hrEmployees.getFirstName().isEmpty()) {
            selectedRequest = transferApproveBeanLocal.searchAllRequest();
            transferDataModel = new ListDataModel(selectedRequest);
        } else if (hrEmployees.getEmpId() != null && hrEmployees.getFirstName().isEmpty()) {
            selectedRequest = transferApproveBeanLocal.findByEmpId(hrEmployees);
            transferDataModel = new ListDataModel(selectedRequest);
        } else if (hrEmployees.getEmpId().isEmpty() && hrEmployees.getFirstName() != null) {
            selectedRequest = transferApproveBeanLocal.findByEmpName(hrEmployees);
            transferDataModel = new ListDataModel(selectedRequest);
        } else if (hrEmployees.getEmpId() != null && hrEmployees.getFirstName() != null) {
            selectedRequest = transferApproveBeanLocal.findByAll(hrEmployees);
            transferDataModel = new ListDataModel(selectedRequest);
        }
    }
    
    int status = 0;
    
    private void populateTable() {
        try {
            List<HrTransferRequests> readFilteredTransfer = transferApproveBeanLocal.loadTransferList(status);
            transferDataModel = new ListDataModel(readFilteredTransfer);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public void findByStatus(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            status = Integer.valueOf(event.getNewValue().toString());
            List<HrTransferRequests> readTransferList = transferApproveBeanLocal.searchByStatus(status);
            transferDataModel = new ListDataModel(readTransferList);
        }
    }
    
    public void populateTransfer() {
        if (hrTransferRequests.getTransferTypeId().getTransferType().equalsIgnoreCase("Temporary")) {
            chooseTemporary = "true";
            choosePermanent = "false";
        } else {
            chooseTemporary = "false";
            choosePermanent = "true";
        }
        if (hrTransferRequests.getStatus().equals(Constants.PREPARE_VALUE)) {
            disableBtn = "false";
        } else {
            disableBtn = "true";
        }
        hrEmployees = hrTransferRequests.getRequesterId();
        empId = String.valueOf(hrTransferRequests.getRequesterId().getId());
        fromDeptId = String.valueOf(hrEmployees.getDeptId().getDepId());
        empHis = hrTransferRequests.getRequesterId();
        deptId = hrEmployees.getDeptId().getDepId();
        fromDeptName = String.valueOf(hrEmployees.getDeptId().getDepName());
        toDeptId = String.valueOf(hrTransferRequests.getTransferTo().getDepId());
        toDeptName = String.valueOf(hrTransferRequests.getTransferTo().getDepName());
        setHrLuTransferTypes(hrTransferRequests.getTransferTypeId());
        hrSalaryScaleRanges = hrEmployees.getGradeId();
        renderScaleRanges = hrEmployees.getGradeId();
        hrDepartments = hrEmployees.getDeptId();
        transferTo = hrTransferRequests.getTransferTo();
        hrJobTypes = hrEmployees.getJobId();
        emp = hrTransferRequests.getRequesterId().getFirstName() + " " + hrTransferRequests.getRequesterId().getMiddleName() + " "
                + hrTransferRequests.getRequesterId().getLastName();
        hrPayrollPlPgDept = hrPayrollPgPlDeptBeanLocal.findBydepOfPlPg(hrDepartments.getDepId());
        if (hrPayrollPlPgDept != null) {
            setPayLocation(hrPayrollPlPgDept.getPayLocationPayGroupId().getPayLocation().toString());
            setPayGroup(hrPayrollPlPgDept.getPayLocationPayGroupId().getPayGroup().toString());
        } else {
            hrPayrollPlPgDept = new HrPayrollPlPgDept();
        }
        fmsCostcSystemJunction = fmsCostCSystemJunctionBeanLocal.findbyDeptId(hrDepartments.getDepId());
        if (fmsCostcSystemJunction != null) {
            if (fmsCostcSystemJunction.getFmsCostCenter() != null) {
                setCostCenter(fmsCostcSystemJunction.getFmsCostCenter().getSystemName());
            } else {
                setCostCenter("");
            }
            if (fmsCostcSystemJunction.getFmsLuSystem() != null) {
                setSystem(fmsCostcSystemJunction.getFmsLuSystem().getSystemCode());
            } else {
                setSystem("");
            }
        } else {
            fmsCostcSystemJunction = new FmsCostcSystemJunction();
        }
        hrPayrollPlPgDept = hrPayrollPgPlDeptBeanLocal.findBydepOfPlPg(transferTo.getDepId());
        if (hrPayrollPlPgDept != null) {
            setPayLocation(hrPayrollPlPgDept.getPayLocationPayGroupId().getPayLocation().toString());
            setPayGroup(hrPayrollPlPgDept.getPayLocationPayGroupId().getPayGroup().toString());
        } else {
            hrPayrollPlPgDept = new HrPayrollPlPgDept();
        }
        fmsCostcSystemJunction = fmsCostCSystemJunctionBeanLocal.findbyDeptId(transferTo.getDepId());
        if (fmsCostcSystemJunction != null) {
            if (fmsCostcSystemJunction.getFmsCostCenter() != null) {
                setCostCenter(fmsCostcSystemJunction.getFmsCostCenter().getSystemName());
            } else {
                setCostCenter("");
            }
            if (fmsCostcSystemJunction.getFmsLuSystem() != null) {
                setSystem(fmsCostcSystemJunction.getFmsLuSystem().getSystemCode());
            } else {
                setSystem("");
            }
        } else {
            fmsCostcSystemJunction = new FmsCostcSystemJunction();
        }
        
//        fmsCostCenter = fmsCostCenterBeanLocal.searchbydepidOfcostcent(hrDepartments);
//        if (fmsCostCenter == null) {
//            system1 = null;
//            costCenter1 = null;
//        } else {
//            setCostCenter1(fmsCostCenter.getSystemName());
//            int sysId = fmsCostCenter.getSystemId().getSystemId();
//            fmsLuSystem = fmsLuSystemBeanLocal.searchbysystemidofsyscode(sysId);
//            setSystem1(fmsLuSystem.getSystemCode());
//        }
//        hrPayrollPlPg = hrPayrollPlPgBeanLocal.findBydepOfPlPg(hrDepartments);
//        if (hrPayrollPlPg == null) {
//            payLocation1 = null;
//            payGroup1 = null;
//            } else {
////            setPayLocation1(hrPayrollPlPg.getPayLocation());
////            setPayGroup1(hrPayrollPlPg.getPayGroup());
//            }
//        fmsCostCenter = fmsCostCenterBeanLocal.searchbydepidOfcostcent(transferTo);
//        if (fmsCostCenter == null) {
//            system = null;
//            costCenter = null;
//            } else {
//            setCostCenter(fmsCostCenter.getSystemName());
//            int sysId = fmsCostCenter.getSystemId().getSystemId();
//            fmsLuSystem = fmsLuSystemBeanLocal.searchbysystemidofsyscode(sysId);
//            setSystem(fmsLuSystem.getSystemCode());
//            }
//        hrPayrollPlPg = hrPayrollPlPgBeanLocal.findBydepOfPlPg(transferTo);
//        if (hrPayrollPlPg == null) {
//            payLocation = null;
//            payGroup = null;
//        } else {
////            setPayLocation(hrPayrollPlPg.getPayLocation());
////            setPayGroup(hrPayrollPlPg.getPayGroup());
//        }
        workflowDataModel();
        chooseCreatePage = true;
        chooseMainPage = false;
        setIcone("ui-icon-search");
    }
    
    public void populateNotification(ValueChangeEvent event) {
        hrTransferRequests = (HrTransferRequests) event.getNewValue();
        populateTransfer();
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("PF('widNotf').hide();");
    }
    
    public void populateDatatable(SelectEvent event) {
        hrTransferRequests = (HrTransferRequests) event.getObject();
        populateTransfer();
    }
    
    public void save() {
        String transferId = String.valueOf(hrTransferRequests.getId());
        String currentDate = StringDateManipulation.toDayInEc();
        String oldDepName = null;
        try {
            if (fromDeptName != null) {
                oldDepName = fromDeptName;
            }
            if (transferApproveBeanLocal.updateEmployeeDepartment(transferId, empId,
                    HrEmpInternalHistory.TRANSFER, fromDeptId,
                    toDeptId, oldDepName, currentDate, 1) == 1) {
                JsfUtil.addSuccessMessage("The request has been successfully approved for the final state.");
                clearPage();
            } else {
                JsfUtil.addFatalMessage("Error occured while approving the request for the final state.");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
//</editor-fold>

}
