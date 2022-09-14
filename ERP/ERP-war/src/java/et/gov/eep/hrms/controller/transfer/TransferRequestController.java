/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.controller.transfer;

import et.gov.eep.commonApplications.businessLogic.WfHrProcessedBeanLocal;
import et.gov.eep.commonApplications.entity.Status;
import et.gov.eep.commonApplications.entity.WfHrProcessed;
import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.commonApplications.utility.date.StringDateManipulation;
import et.gov.eep.fcms.businessLogic.admin.FmsCostCSystemJunctionBeanLocal;
import et.gov.eep.fcms.businessLogic.admin.FmsCostCenterBeanLocal;
import et.gov.eep.fcms.businessLogic.admin.FmsLuSystemBeanLocal;
import et.gov.eep.fcms.entity.admin.FmsCostCenter;
import et.gov.eep.fcms.entity.admin.FmsCostcSystemJunction;
import et.gov.eep.fcms.entity.admin.FmsLuSystem;
import et.gov.eep.hrms.businessLogic.employee.HrEmployeeBeanLocal;
import et.gov.eep.hrms.businessLogic.payroll.HrPayrollPgPlDeptBeanLocal;
import et.gov.eep.hrms.businessLogic.payroll.HrPayrollPlPgBeanLocal;
import et.gov.eep.hrms.businessLogic.transfer.InternalMovementBeanLocal;
import et.gov.eep.hrms.businessLogic.transfer.TransferReqBeanLocal;
import et.gov.eep.hrms.entity.employee.HrEmpEducations;
import et.gov.eep.hrms.entity.employee.HrEmployees;
import et.gov.eep.hrms.entity.lookup.HrLuGrades;
import et.gov.eep.hrms.entity.lookup.HrLuTransferTypes;
import et.gov.eep.hrms.entity.organization.HrDepartments;
import et.gov.eep.hrms.entity.organization.HrJobTypes;
import et.gov.eep.hrms.entity.organization.HrSalaryScaleRanges;
import et.gov.eep.hrms.entity.organization.HrSalaryScales;
import et.gov.eep.hrms.entity.payroll.HrPayrollPlPg;
import et.gov.eep.hrms.entity.payroll.HrPayrollPlPgDept;
import et.gov.eep.hrms.entity.transfer.HrEmpInternalHistory;
import et.gov.eep.hrms.entity.transfer.HrTransferRequests;
import et.gov.eep.hrms.mapper.organization.HrDepartmentsFacade;
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
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import securityBean.Constants;
import securityBean.SessionBean;
import webService.AAA;
import webService.EventEntry;
import webService.IAdministration;
import webService.UserStatus;

/**
 *
 * @author INSA
 */
@Named(value = "transferRequestController")
@ViewScoped
public class TransferRequestController implements Serializable {

    //<editor-fold defaultstate="collapsed" desc="Entity Declaration">
    @Inject
            HrTransferRequests hrTransferRequests;
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
            HrEmpInternalHistory transferHistory;
    @Inject
            WfHrProcessed wfHrProcessed;
    @Inject
            SessionBean sessionBeanLocal;
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="EJB Declaration">
    @EJB
            TransferReqBeanLocal transferReqBeanLocal;
    @EJB
    private HrEmployeeBeanLocal hrEmployeeBean;
    @EJB
            HrDepartmentsFacade hrDepartmentsFacade;
    @EJB
            FmsCostCSystemJunctionBeanLocal fmsCostCSystemJunctionBeanLocal;
    @Inject
            HrPayrollPgPlDeptBeanLocal hrPayrollPgPlDeptBeanLocal;
    @EJB
            InternalMovementBeanLocal historyBeanLocal;
    @EJB
            WfHrProcessedBeanLocal wfHrProcessedBeanLocal;
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Variable declaration">
    int updateStatus = 0;
    private String saveorUpdateBundle = "Save";
    private String createOrSearchBundle = "New";
    private String headerTitle = "Search Request";
    private String icone = "ui-icon-plus";
    private String disableBtn = "false";
    private boolean chooseCreatePage = false;
    private boolean chooseMainPage = true;
    private TreeNode root;
    private TreeNode root2;
    private TreeNode selectedNode;
    TreeNode DepartmentList;
    String emp;
    String system;
    String costCenter;
    String payLocation;
    String payGroup;
    int approveType = 0;
    private int dateCal;
    DateFormat dateFormat = new SimpleDateFormat("dd/mm/yyyy");
    String disDelete = "true";
    private static List<HrDepartments> araListe;
    List<HrDepartments> allDepartmentsList = new ArrayList<>();
    List<SelectItem> filterByStatus = new ArrayList<>();
    DataModel<HrTransferRequests> transferRequestsDataModel;
    DataModel<WfHrProcessed> workflowDataModel = new ListDataModel<>();
    private List<HrTransferRequests> selectedRequest;
    private List<HrTransferRequests> transferList;
    SelectItem[] listOfJob;
    private String chooseRelocation;
    private String chooseTransfer;
    private String choosePermanent = chooseTransfer = chooseRelocation = "true";
    private String chooseTemporary = "false";
    private int selectedStatus;
    UserStatus userStatus = new UserStatus();
    Status status = new Status();
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="post Construct">
    public TransferRequestController() {
    }
    
    @PostConstruct
    public void init() {
        allDepartmentsList = hrDepartmentsFacade.findAll();
        root = new DefaultTreeNode("Root", null);
        recursive(allDepartmentsList, 0, root);
        root2 = getRoot();
        hrTransferRequests.setRequestDate(StringDateManipulation.toDayInEc());
        wfHrProcessed.setProcessedOn(StringDateManipulation.toDayInEc());
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Main Methods">
    public void workflowDataModel() {
        workflowDataModel = null;
        workflowDataModel = new ListDataModel(new ArrayList(hrTransferRequests.getWfHrProcessedList()));
    }
    
    public void recursive(List<HrDepartments> liste, int id, TreeNode node) {
        araListe = new ArrayList<>();
        if (allDepartmentsList != null) {
            for (HrDepartments k : allDepartmentsList) {
                if (k.getParentId() != null && k.getParentId() == id) {
                    TreeNode childNode = new DefaultTreeNode(k.getDepId() + "--" + k.getDepName(), node);
                    araListe.add(k);
                    recursive(araListe, k.getDepId(), childNode);
                }
            }
        }
    }
    
    // <editor-fold defaultstate="collapsed" desc="Getter and Setter">
    public HrTransferRequests getHrTransferRequests() {
        if (hrTransferRequests == null) {
            hrTransferRequests = new HrTransferRequests();
        }
        return hrTransferRequests;
    }
    
    public void setHrTransferRequests(HrTransferRequests hrTransferRequests) {
        if (hrTransferRequests == null) {
            hrTransferRequests = new HrTransferRequests();
        }
        this.hrTransferRequests = hrTransferRequests;
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
    
    public HrDepartments getHrDepartments() {
        if (hrDepartments == null) {
            hrDepartments = new HrDepartments();
        }
        return hrDepartments;
    }
    
    public void setHrDepartments(HrDepartments hrDepartments) {
        this.hrDepartments = hrDepartments;
    }
    
    public HrLuTransferTypes getHrLuTransferTypes() {
        if (hrLuTransferTypes == null) {
            hrLuTransferTypes = new HrLuTransferTypes();
        }
        return hrLuTransferTypes;
    }
    
    public void setHrLuTransferTypes(HrLuTransferTypes hrLuTransferTypes) {
        this.hrLuTransferTypes = hrLuTransferTypes;
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
    
    public HrDepartments getTransferTo() {
        if (transferTo == null) {
            transferTo = new HrDepartments();
        }
        return transferTo;
    }
    
    public void setTransferTo(HrDepartments transferTo) {
        this.transferTo = transferTo;
    }
    
    public HrSalaryScaleRanges getRenderScaleRanges() {
        if (renderScaleRanges == null) {
            renderScaleRanges = new HrSalaryScaleRanges();
        }
        return renderScaleRanges;
    }
    
    public void setRenderScaleRanges(HrSalaryScaleRanges renderScaleRanges) {
        this.renderScaleRanges = renderScaleRanges;
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
    
    public WfHrProcessed getWfHrProcessed() {
        if (wfHrProcessed == null) {
            wfHrProcessed = new WfHrProcessed();
        }
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
    
    public DataModel<HrTransferRequests> getTransferRequestsDataModel() {
        return transferRequestsDataModel;
    }
    
    public void setTransferRequestsDataModel(DataModel<HrTransferRequests> transferRequestsDataModel) {
        this.transferRequestsDataModel = transferRequestsDataModel;
    }
    
    public List<SelectItem> getFilterByStatus() {
        return transferReqBeanLocal.filterByStatus();
    }
    
    public void setFilterByStatus(List<SelectItem> filterByStatus) {
        this.filterByStatus = filterByStatus;
    }
    
    public List<HrTransferRequests> getSelectedRequest() {
        if (selectedRequest == null) {
            selectedRequest = new ArrayList<>();
        }
        return selectedRequest;
    }
    
    public void setSelectedRequest(List<HrTransferRequests> selectedRequest) {
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
    
    public boolean isChooseMainPage() {
        return chooseMainPage;
    }
    
    public void setChooseMainPage(boolean chooseMainPage) {
        this.chooseMainPage = chooseMainPage;
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
    
    public TreeNode getRoot() {
        return root;
    }
    
    public void setRoot(TreeNode root) {
        this.root = root;
    }
    
    public TreeNode getRoot2() {
        return root2;
    }
    
    public void setRoot2(TreeNode root2) {
        this.root2 = root2;
    }
    
    public TreeNode getSelectedNode() {
        return selectedNode;
    }
    
    public void setSelectedNode(TreeNode selectedNode) {
        this.selectedNode = selectedNode;
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
    
    public String getDisDelete() {
        return disDelete;
    }
    
    public void setDisDelete(String disDelete) {
        this.disDelete = disDelete;
    }
    
    public int getDateCal() {
        return dateCal;
    }
    
    public void setDateCal(int dateCal) {
        this.dateCal = dateCal;
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
    
    public SelectItem[] getListOfJob() {
        return listOfJob;
    }
    
    public void setListOfJob(SelectItem[] listOfJob) {
        this.listOfJob = listOfJob;
    }
    
    public int getApproveType() {
        return approveType;
    }
    
    public void setApproveType(int approveType) {
        this.approveType = approveType;
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
    // </editor-fold>
    
    public Date date() {
        DateFormat dformat = new SimpleDateFormat("dd/mm/yyyy");
        Date datee = new Date();
        dformat.format(datee);
        return datee;
    }
    
    public Integer changedTo() throws ParseException {
        SimpleDateFormat simpledate = new SimpleDateFormat("dd/mm/yyyy");
        String x = simpledate.format(hrTransferRequests.getStartDate());
        String y = simpledate.format(hrTransferRequests.getEndDate());
        int dayDiff = StringDateManipulation.compareDateDifference(x, y);
        System.out.println("diff= " + dayDiff);
        return dateCal = dayDiff;
    }
    
    // <editor-fold defaultstate="collapsed" desc="Save Update">
    @SuppressWarnings("IncompatibleEquals")
            
            int dateDifference;
    int experienceInYear;
    
    public void dateValidation() {
        String startMonth[] = hrTransferRequests.getStartDate().split("/");
        int isSartMonth = Integer.parseInt(startMonth[1]);
        String startYear[] = hrTransferRequests.getStartDate().split("/");
        int isStartYear = Integer.parseInt(startYear[2]);
        String startDate[] = hrTransferRequests.getStartDate().split("/");
        int isStartDate = Integer.parseInt(startDate[0]);
        String endDay[] = hrTransferRequests.getEndDate().split("/");
        int isEndDay = Integer.parseInt(endDay[0]);
        String endMonth[] = hrTransferRequests.getEndDate().split("/");
        int isEndMonth = Integer.parseInt(endMonth[1]);
        String endYear[] = hrTransferRequests.getEndDate().split("/");
        int isEndYear = Integer.parseInt(endYear[2]);
        dateDifference = ((isEndDay - isStartDate) + ((isEndMonth - isSartMonth) * 30) + ((isEndYear - isStartYear) * 365));
    }
    
    public void checkYear() {
        String prevApproveDate = transferReqBeanLocal.findApproved(hrTransferRequests);
        if (prevApproveDate != null) {
            String hireDateInDay[] = prevApproveDate.split("/");
            int inDay = Integer.parseInt(hireDateInDay[0]);
            String hireDateInMonth[] = prevApproveDate.split("/");
            int inMonth = Integer.parseInt(hireDateInMonth[1]);
            String hireDateInyear[] = prevApproveDate.split("/");
            int inYear = Integer.parseInt(hireDateInyear[2]);
            String toDayDay[] = StringDateManipulation.toDayInEc().split("-");
            int day = Integer.parseInt(toDayDay[2]);
            String toDayMonth[] = StringDateManipulation.toDayInEc().split("-");
            int month = Integer.parseInt(toDayMonth[1]);
            String toDayYear[] = StringDateManipulation.toDayInEc().split("-");
            int year = Integer.parseInt(toDayYear[0]);
            int experienceDay = ((day - inDay) + ((month - inMonth) * 30) + ((year - inYear) * 365));
            experienceInYear = experienceDay / 365;
        }
    }
    
    public void saveToHistory() {
//        transferHistory = hrTransferRequests.getRequesterId();
        transferHistory.setProcessType("Transfer");
        transferHistory.setEmpId(hrEmployees);
        transferHistory.setOldDepName(hrEmployees.getDeptId().getDepName());
        transferHistory.setOldJobName(hrEmployees.getJobId().getJobTitle());
        transferHistory.setOldSalaryStep(hrEmployees.getSalaryStep().getSalaryStep().toString());
        transferHistory.setOldGrade(hrEmployees.getGradeId().getGradeId().getGrade());
        transferHistory.setOldSalary(hrEmployees.getBasicSalary());
        historyBeanLocal.saveOrUpdate(transferHistory);
    }
    
    public String saveTransferRqst() {
        try {
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(sessionBeanLocal.getUserName(), "saveTransferRqst", dataset)) {
                boolean checkDuplication = transferReqBeanLocal.isRequstExist(hrTransferRequests);
                boolean approveDate = transferReqBeanLocal.isApproved(hrTransferRequests);
                if (hrEmployees.getId() == null) {
                    JsfUtil.addFatalMessage("Employee Information not be null,please search by name and fill it");
                } else if (hrTransferRequests.getTransferTo() == null) {
                    JsfUtil.addFatalMessage("Department can not be empty");
                } else if (hrTransferRequests.getTransferTypeId().getTransferType().equalsIgnoreCase("Transfer")
                        && (hrTransferRequests.getRequesterId().getDeptId().getDepId()).equals(hrTransferRequests.getTransferTo().getDepId())) {
                    JsfUtil.addFatalMessage("Department can not be the same to transfer");
                } else {
                    if (updateStatus == 0 && checkDuplication == false && approveDate == false) {
                        hrTransferRequests.setStatus(Constants.PREPARE_VALUE);
                        hrTransferRequests.setPreparedBy(String.valueOf(sessionBeanLocal.getUserId()));
                        hrTransferRequests.setRemark(wfHrProcessed.getCommentGiven());
                        wfHrProcessed.setTransferId(hrTransferRequests);
                        wfHrProcessed.setDecision(hrTransferRequests.getStatus());
                        wfHrProcessed.setProcessedBy(sessionBeanLocal.getUserId());
                        transferReqBeanLocal.save(hrTransferRequests);
                        wfHrProcessedBeanLocal.saveOrUpdate(wfHrProcessed);
                        JsfUtil.addSuccessMessage("Saved successfully");
                        return clearPage();
                    } else if (updateStatus == 0 && checkDuplication == true) {
                        JsfUtil.addFatalMessage("Already request. Try again!");
                    } else if (updateStatus == 0 && approveDate == true) {
                        checkYear();
                        if ((experienceInYear < 1)) {
                            JsfUtil.addFatalMessage("An employee should work at least one year before making another transfer request");
                        } else {
                            hrTransferRequests.setStatus(Constants.PREPARE_VALUE);
                            hrTransferRequests.setPreparedBy(String.valueOf(sessionBeanLocal.getSessionID()));
                            hrTransferRequests.setRemark(wfHrProcessed.getCommentGiven());
                            wfHrProcessed.setTransferId(hrTransferRequests);
                            wfHrProcessed.setDecision(hrTransferRequests.getStatus());
                            wfHrProcessed.setProcessedBy(sessionBeanLocal.getUserId());
                            transferReqBeanLocal.edit(hrTransferRequests);
                            wfHrProcessedBeanLocal.saveOrUpdate(wfHrProcessed);
                            JsfUtil.addSuccessMessage("Saved successfully");
                            return clearPage();
                        }
                    } else {
                        transferReqBeanLocal.edit(hrTransferRequests);
                        JsfUtil.addSuccessMessage("modified successfully");
                        return clearPage();
                    }
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
            JsfUtil.addFatalMessage("Error occurs while saving");
            disDelete = "true";
            updateStatus = 0;
            saveorUpdateBundle = "Save";
            return null;
        }
        return null;
    }
    
    public void resetTransferRqst() {
        hrTransferRequests = new HrTransferRequests();
        hrEmployees = new HrEmployees();
        hrDepartments = new HrDepartments();
        transferTo = new HrDepartments();
        hrJobTypes = new HrJobTypes();
        hrLuTransferTypes = new HrLuTransferTypes();
        wfHrProcessed = new WfHrProcessed();
        workflowDataModel = null;
        disDelete = "true";
        system = null;
        costCenter = null;
        payLocation = null;
        payGroup = null;
        updateStatus = 0;
        disableBtn = "false";
        saveorUpdateBundle = "Save";
        emp = null;
    }
    
    private String clearPage() {
        hrTransferRequests = null;
        hrEmployees = null;
        hrDepartments = null;
        transferTo = null;
        hrJobTypes = null;
        hrLuTransferTypes = null;
        disDelete = "true";
        system = null;
        costCenter = null;
        payLocation = null;
        payGroup = null;
        emp = null;
        return null;
    }
    // </editor-fold>
    
    public void transferRequestInfo() {
        saveorUpdateBundle = "Save";
        if (createOrSearchBundle.equals("New")) {
            chooseCreatePage = true;
            chooseMainPage = false;
            disableBtn = "false";
            createOrSearchBundle = "Search";
            headerTitle = "Transfer Request";
            setIcone("ui-icon-search");
        } else if (createOrSearchBundle.equals("Search")) {
            chooseCreatePage = false;
            chooseMainPage = true;
            createOrSearchBundle = "New";
            headerTitle = "Search Request";
            setIcone("ui-icon-plus");
        }
    }
    
    String slected = "Select One";
    
    public void handleTransfer(ValueChangeEvent event) {
        if (null != event.getNewValue()) {
            slected = event.getNewValue().toString();
            if (slected.equalsIgnoreCase("Permanent")) {
                choosePermanent = chooseTransfer = chooseRelocation = "true";
                chooseTemporary = "false";
            } else {
                choosePermanent = chooseTransfer = chooseRelocation = "false";
                chooseTemporary = "true";
            }
        }
    }
    
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
            transferTo = hrTransferRequests.getTransferTo();
            hrJobTypes = hrEmployees.getJobId();
            hrTransferRequests.setRequesterId(hrEmployees);
        } catch (Exception ex) {
            System.err.println("Exception");
        }
    }
    
    public void getTransfer(ValueChangeEvent event) {
        if (!event.getNewValue().toString().isEmpty()) {
            hrLuTransferTypes.setTransferType(event.getNewValue().toString());
            hrLuTransferTypes = transferReqBeanLocal.findTransfertype(hrLuTransferTypes);
            hrTransferRequests.setTransferTypeId(hrLuTransferTypes);
            slected = event.getNewValue().toString();
            if (slected.equalsIgnoreCase("Temporary")) {
                chooseTemporary = "true";
                choosePermanent = "false";
            } else {
                chooseTemporary = "false";
                choosePermanent = "true";
            }
        }
    }
    
    public SelectItem[] getListOfTransferinfo() {
        return JsfUtil.getSelectItems(transferReqBeanLocal.findAllHrLuTransferTypes(), true);
    }
    
    public void onNodeSelect(NodeSelectEvent event) {
        selectedNode = event.getTreeNode();
        int key = Integer.parseInt((selectedNode.getData().toString()).split("--")[0]);
        transferTo = transferReqBeanLocal.getSelectDepartement(key);
        hrTransferRequests.setTransferTo(transferTo);
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
        
//        fmsCostCenter = fmsCostCenterBeanLocal.searchbydepidOfcostcent(transferTo);
//        if (fmsCostCenter == null) {
//            system = null;
//            costCenter = null;
//        } else {
//            setCostCenter(fmsCostCenter.getSystemName());
//            int sysId = fmsCostCenter.getSystemId().getSystemId();
//            fmsLuSystem = fmsLuSystemBeanLocal.searchbysystemidofsyscode(sysId);
//            setSystem(fmsLuSystem.getSystemCode());
//        }
//        hrPayrollPlPg = hrPayrollPlPgBeanLocal.findBydepOfPlPg(transferTo);
//        if (hrPayrollPlPg == null) {
//            payLocation = null;
//            payGroup = null;
//        } else {
////            setPayLocation(hrPayrollPlPg.getPayLocation());
////            setPayGroup(hrPayrollPlPg.getPayGroup());
//        }
    }
    
    public void populateTransfer(SelectEvent event) {
        hrTransferRequests = (HrTransferRequests) event.getObject();
        if (hrTransferRequests.getTransferTypeId().getTransferType().equalsIgnoreCase("Temporary")) {
            chooseTemporary = "true";
            choosePermanent = "false";
        } else {
            chooseTemporary = "false";
            choosePermanent = "true";
        }
        if (hrTransferRequests.getStatus().equals(Constants.APPROVE_VALUE) || hrTransferRequests.getStatus().equals(Constants.CHECK_APPROVE_VALUE)) {
            disableBtn = "true";
        } else {
            disableBtn = "false";
        }
        setHrEmployees(hrTransferRequests.getRequesterId());
        setHrLuTransferTypes(hrTransferRequests.getTransferTypeId());
        hrSalaryScaleRanges = hrEmployees.getGradeId();
        renderScaleRanges = hrEmployees.getGradeId();
        hrDepartments = hrEmployees.getDeptId();
        transferTo = hrTransferRequests.getTransferTo();
        hrJobTypes = hrEmployees.getJobId();
        emp = hrTransferRequests.getRequesterId().getFirstName() + " " + hrTransferRequests.getRequesterId().getMiddleName() + " "
                + hrTransferRequests.getRequesterId().getLastName();
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
        workflowDataModel();
        chooseCreatePage = true;
        chooseMainPage = false;
        createOrSearchBundle = "Search";
        setIcone("ui-icon-search");
        updateStatus = 1;
        saveorUpdateBundle = "Update";
    }
    
    public void getTransferInfo(ValueChangeEvent event) {
        String reqest[] = event.getNewValue().toString().split("--");
        int id = Integer.parseInt(reqest[0]);
        hrTransferRequests = transferReqBeanLocal.getTransferInfo(id);
        if (hrTransferRequests.getTransferTypeId().getTransferType().equalsIgnoreCase("Temporary")) {
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
        hrDepartments = hrEmployees.getDeptId();
        transferTo = hrTransferRequests.getTransferTo();
        hrJobTypes = hrEmployees.getJobId();
        emp = hrTransferRequests.getRequesterId().getFirstName() + " " + hrTransferRequests.getRequesterId().getMiddleName() + " "
                + hrTransferRequests.getRequesterId().getLastName();
        disDelete = "false";
        updateStatus = 1;
        saveorUpdateBundle = "Update";
    }
    
    public List<String> getAllRequests() {
        return transferReqBeanLocal.searchByName(0);
    }
    
    public void jobChangedListener(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            String job[] = event.getNewValue().toString().split("--");
            int id = Integer.parseInt(job[0]);
            hrJobTypes = transferReqBeanLocal.findByName(id);
            hrTransferRequests.setRequesterId(hrEmployees);
        }
    }
    
    public void search() {
        if (hrTransferRequests.getRequestDate() == null && hrEmployees.getFirstName().isEmpty()) {
            selectedRequest = transferReqBeanLocal.searchAllRequest();
            transferRequestsDataModel = new ListDataModel(selectedRequest);
        } else if (hrTransferRequests.getRequestDate() != null && hrEmployees.getFirstName() == null
                || hrEmployees.getFirstName() != null && hrTransferRequests.getRequestDate() == null) {
            selectedRequest = transferReqBeanLocal.findByOne(hrEmployees);
            transferRequestsDataModel = new ListDataModel(selectedRequest);
            System.out.println("Search Name  = = " + selectedRequest.size());
        }
    }
    
    public void searchAllReqeust() {
        if (hrEmployees.getEmpId().isEmpty() && hrEmployees.getFirstName().isEmpty()) {
            selectedRequest = transferReqBeanLocal.searchAllRequest();
            transferRequestsDataModel = new ListDataModel(selectedRequest);
        } else if (hrEmployees.getEmpId() != null && hrEmployees.getFirstName().isEmpty()) {
            selectedRequest = transferReqBeanLocal.findByEmpId(hrEmployees);
            transferRequestsDataModel = new ListDataModel(selectedRequest);
        } else if (hrEmployees.getEmpId().isEmpty() && hrEmployees.getFirstName() != null) {
            selectedRequest = transferReqBeanLocal.findByEmpName(hrEmployees);
            transferRequestsDataModel = new ListDataModel(selectedRequest);
        } else if (hrEmployees.getEmpId() != null && hrEmployees.getFirstName() != null) {
            selectedRequest = transferReqBeanLocal.findByAll(hrEmployees);
            transferRequestsDataModel = new ListDataModel(selectedRequest);
        }
    }
    
    int reqStatus = 0;
    
    private void populateTable() {
        try {
            List<HrTransferRequests> readFilteredTransfer = transferReqBeanLocal.loadTransferList(reqStatus);
            transferRequestsDataModel = new ListDataModel(readFilteredTransfer);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public void filiterByStatus_Vcl(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            reqStatus = Integer.valueOf(event.getNewValue().toString());
            populateTable();
        }
    }
    
    public void searchByStatus(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            selectedStatus = Integer.valueOf(String.valueOf(event.getNewValue()));
            if (selectedStatus == Constants.PREPARE_VALUE) {
                status.setStatus1(Constants.PREPARE_VALUE);
                status.setStatus2(Constants.CHECK_REJECT_VALUE);
                status.setStatus3(Constants.APPROVE_REJECT_VALUE);
                transferList = transferReqBeanLocal.loadTransferReqList(status, sessionBeanLocal.getUserId());
                transferRequestsDataModel = new ListDataModel(transferList);
            } else if (selectedStatus == Constants.APPROVE_VALUE) {
                status.setStatus1(Constants.APPROVE_VALUE);
                status.setStatus2(Constants.CHECK_APPROVE_VALUE);
                transferList = transferReqBeanLocal.loadTransferList(status, sessionBeanLocal.getUserId());
                transferRequestsDataModel = new ListDataModel(transferList);
            }
        }
    }
    
    public SelectItem[] getStatusList() {
        SelectItem[] items = new SelectItem[4];
        items[0] = new SelectItem("null", "--- Select ---");
        items[1] = new SelectItem(Constants.PREPARE_VALUE, "Load request list");
        items[2] = new SelectItem(Constants.APPROVE_VALUE, "Load approved list");
        items[3] = new SelectItem(Constants.APPROVE_REJECT_VALUE, "Load rejected list");
        return items;
    }
    
    private void populateRequestList(Status status) {
        try {
            transferList = transferReqBeanLocal.loadReqTranList(status, sessionBeanLocal.getUserId());
            transferRequestsDataModel = new ListDataModel<>(transferList);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    private void populateApproveList(Status status) {
        try {
            transferList = transferReqBeanLocal.loadApproveTranList(status, sessionBeanLocal.getUserId());
            transferRequestsDataModel = new ListDataModel<>(transferList);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public void loadByStatus(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            selectedStatus = Integer.valueOf(String.valueOf(event.getNewValue()));
            if (selectedStatus == Constants.PREPARE_VALUE) {
                status.setStatus1(Constants.PREPARE_VALUE);
                populateRequestList(status);
            } else if (selectedStatus == Constants.APPROVE_VALUE) {
                status.setStatus1(Constants.APPROVE_VALUE);
                status.setStatus2(Constants.CHECK_APPROVE_VALUE);
                populateApproveList(status);
            } else if (selectedStatus == Constants.APPROVE_REJECT_VALUE) {
                status.setStatus1(Constants.APPROVE_REJECT_VALUE);
                status.setStatus2(Constants.CHECK_REJECT_VALUE);
                populateApproveList(status);
            }
        }
    }
    
    // <editor-fold defaultstate="collapsed" desc="Trnasfer Approve">
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
        hrTransferRequests = transferReqBeanLocal.getTransferInfo(id);
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
        return transferReqBeanLocal.searchAppReList(0);
    }
    
    public boolean insert() {
        if (hrEmployees.getId() == null) {
            JsfUtil.addFatalMessage(" Employee Information must not be null ");
        } else if (hrTransferRequests.getTransferTo() == null) {
            JsfUtil.addFatalMessage("Department can not be empty");
        } else {
            try {
                if (selectedValue.equalsIgnoreCase("Approve")) {
                    hrTransferRequests.setStatus(1);
                    transferReqBeanLocal.edit(hrTransferRequests);
                    JsfUtil.addSuccessMessage("Approved Successful.");
                    clearPage();
                } else if (selectedValue.equalsIgnoreCase("Reject")) {
                    hrTransferRequests.setStatus(2);
                    transferReqBeanLocal.edit(hrTransferRequests);
                    JsfUtil.addSuccessMessage("Reject Successful.");
                    clearPage();
                } else if (selectedValue.equalsIgnoreCase("Resubmit")) {
                    hrTransferRequests.setStatus(3);
                    transferReqBeanLocal.edit(hrTransferRequests);
                    JsfUtil.addSuccessMessage("Resubmit Successful.");
                    clearPage();
                } else {
                    hrTransferRequests.setStatus(4);
                    transferReqBeanLocal.edit(hrTransferRequests);
                    JsfUtil.addSuccessMessage("Forwared Successful.");
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
