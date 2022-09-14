/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.controller.medical;

import java.io.Serializable;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
import securityBean.Constants;
import securityBean.SessionBean;
import webService.AAA;
import webService.EventEntry;
import webService.IAdministration;
import webService.UserStatus;
import et.gov.eep.commonApplications.businessLogic.WfHrProcessedBeanLocal;
import et.gov.eep.commonApplications.entity.Status;
import et.gov.eep.commonApplications.entity.WfHrProcessed;
import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.commonApplications.utility.date.StringDateManipulation;
import et.gov.eep.hrms.businessLogic.employee.HrEmployeeBeanLocal;
import et.gov.eep.hrms.businessLogic.medical.CashRefundBeanLocal;
import et.gov.eep.hrms.entity.employee.HrEmployees;
import et.gov.eep.hrms.entity.medical.HrLocalMedInstitutions;
import et.gov.eep.hrms.entity.medical.HrLocalMedSettlementDetail;
import et.gov.eep.hrms.entity.medical.HrLocalMedSettlements;
import et.gov.eep.hrms.entity.organization.HrDepartments;
import et.gov.eep.hrms.entity.organization.HrJobTypes;

/**
 *
 * @author INSA
 */
@Named(value = "cashRefundController")
@ViewScoped
public class CashRefundController implements Serializable {

    @Inject
    HrLocalMedSettlements hrLocalMedSettlements;
    @Inject
    HrLocalMedSettlementDetail hrLocalMedSettlementDetail;
    @Inject
    HrLocalMedInstitutions medicalInstitutions;
    @Inject
    HrEmployees hrEmployees;
    @Inject
    HrEmployees preparerEmp;
    @Inject
    HrEmployees srcEmployees;
    @Inject
    HrDepartments hrDepartments;
    @Inject
    HrJobTypes hrJobTypes;
    @Inject
    WfHrProcessed wfHrProcessed;
    @Inject
    SessionBean sessionBeanLocal;

    @EJB
    CashRefundBeanLocal cashRefundBeanLocal;
    @EJB
    HrEmployeeBeanLocal employeeBean;
    @EJB
    WfHrProcessedBeanLocal wfHrProcessedBeanLocal;

    List<HrLocalMedInstitutions> institutionList;
    DataModel<HrLocalMedSettlementDetail> settlementDetailDataModel;
    DataModel<HrLocalMedSettlements> settlementDataModel = new ListDataModel<>();
    DataModel<WfHrProcessed> workflowDataModel = new ListDataModel<>();
    private List<HrLocalMedSettlements> requestList;
    List<SelectItem> filterByStatus = new ArrayList<>();
    HrLocalMedSettlements selectedRow = null;
    Set<String> checkDuplication = new HashSet<>();
    private List<HrLocalMedSettlements> medSettlementList;

    int updateStatus = 0;
    private String saveOrUpdate = "Save";
    private String addOrUpdate = "Add";
    private String newOrSearch = "New";
    private String icone = "ui-icon-plus";
    private boolean searchPage = true;
    private boolean newPage = false;
    private boolean btnNewRender = false;
    double amount;
    private String disableBtn = "false";
    private String requstDate;
    private String disableEmpID = "false";
    private String disableEmpName = "true";
    private String selected = "Employee ID";
    private int selectedStatus;
    private int status1 = 0, prepareBy;
    UserStatus userStatus = new UserStatus();
    Status status = new Status();

    public CashRefundController() {
    }

    @PostConstruct
    public void init() {
        setFilterByStatus(cashRefundBeanLocal.filterByStatus());
        setRequstDate(StringDateManipulation.toDayInEc());
        wfHrProcessed.setProcessedOn(StringDateManipulation.toDayInEc());
    }

    //<editor-fold defaultstate="collapsed" desc="getter setter">
    public HrLocalMedSettlements getHrLocalMedSettlements() {
        if (hrLocalMedSettlements == null) {
            hrLocalMedSettlements = new HrLocalMedSettlements();
        }
        return hrLocalMedSettlements;
    }

    public void setHrLocalMedSettlements(HrLocalMedSettlements hrLocalMedSettlements) {
        this.hrLocalMedSettlements = hrLocalMedSettlements;
    }

    public HrLocalMedSettlementDetail getHrLocalMedSettlementDetail() {
        if (hrLocalMedSettlementDetail == null) {
            hrLocalMedSettlementDetail = new HrLocalMedSettlementDetail();
        }
        return hrLocalMedSettlementDetail;
    }

    public void setHrLocalMedSettlementDetail(HrLocalMedSettlementDetail hrLocalMedSettlementDetail) {
        this.hrLocalMedSettlementDetail = hrLocalMedSettlementDetail;
    }

    public HrLocalMedInstitutions getMedicalInstitutions() {
        if (medicalInstitutions == null) {
            medicalInstitutions = new HrLocalMedInstitutions();
        }
        return medicalInstitutions;
    }

    public void setMedicalInstitutions(HrLocalMedInstitutions medicalInstitutions) {
        this.medicalInstitutions = medicalInstitutions;
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

    public HrEmployees getPreparerEmp() {
        if (preparerEmp == null) {
            preparerEmp = new HrEmployees();
        }
        return preparerEmp;
    }

    public void setPreparerEmp(HrEmployees preparerEmp) {
        this.preparerEmp = preparerEmp;
    }

    public HrEmployees getSrcEmployees() {
        if (srcEmployees == null) {
            srcEmployees = new HrEmployees();
        }
        return srcEmployees;
    }

    public void setSrcEmployees(HrEmployees srcEmployees) {
        this.srcEmployees = srcEmployees;
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

    public HrJobTypes getHrJobTypes() {
        if (hrJobTypes == null) {
            hrJobTypes = new HrJobTypes();
        }
        return hrJobTypes;
    }

    public void setHrJobTypes(HrJobTypes hrJobTypes) {
        this.hrJobTypes = hrJobTypes;
    }

    public List<HrLocalMedInstitutions> getInstitutionList() {
        return cashRefundBeanLocal.findAll();
    }

    public void setInstitutionList(List<HrLocalMedInstitutions> institutionList) {
        this.institutionList = institutionList;
    }

    public DataModel<HrLocalMedSettlementDetail> getSettlementDetailDataModel() {
        if (settlementDetailDataModel == null) {
            settlementDetailDataModel = new ArrayDataModel<>();
        }
        return settlementDetailDataModel;
    }

    public void setSettlementDetailDataModel(DataModel<HrLocalMedSettlementDetail> settlementDetailDataModel) {
        this.settlementDetailDataModel = settlementDetailDataModel;
    }

    public DataModel<HrLocalMedSettlements> getSettlementDataModel() {
        if (settlementDataModel == null) {
            settlementDataModel = new ListDataModel<>();
        }
        return settlementDataModel;
    }

    public void setSettlementDataModel(DataModel<HrLocalMedSettlements> settlementDataModel) {
        this.settlementDataModel = settlementDataModel;
    }

    public List<HrLocalMedSettlements> getRequestList() {
        if (requestList == null) {
            requestList = new ArrayList<>();
        }
        return requestList;
    }

    public void setRequestList(List<HrLocalMedSettlements> requestList) {
        this.requestList = requestList;
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

    public List<SelectItem> getFilterByStatus() {
        return filterByStatus;
    }

    public void setFilterByStatus(List<SelectItem> filterByStatus) {
        this.filterByStatus = filterByStatus;
    }

    public HrLocalMedSettlements getSelectedRow() {
        return selectedRow;
    }

    public void setSelectedRow(HrLocalMedSettlements selectedRow) {
        this.selectedRow = selectedRow;
    }

    public String getSelected() {
        return selected;
    }

    public void setSelected(String selected) {
        this.selected = selected;
    }

    public String getSaveOrUpdate() {
        return saveOrUpdate;
    }

    public void setSaveOrUpdate(String saveOrUpdate) {
        this.saveOrUpdate = saveOrUpdate;
    }

    public String getAddOrUpdate() {
        return addOrUpdate;
    }

    public void setAddOrUpdate(String addOrUpdate) {
        this.addOrUpdate = addOrUpdate;
    }

    public String getNewOrSearch() {
        return newOrSearch;
    }

    public void setNewOrSearch(String newOrSearch) {
        this.newOrSearch = newOrSearch;
    }

    public String getIcone() {
        return icone;
    }

    public void setIcone(String icone) {
        this.icone = icone;
    }

    public boolean isSearchPage() {
        return searchPage;
    }

    public void setSearchPage(boolean searchPage) {
        this.searchPage = searchPage;
    }

    public boolean isNewPage() {
        return newPage;
    }

    public void setNewPage(boolean newPage) {
        this.newPage = newPage;
    }

    public boolean isBtnNewRender() {
        return btnNewRender;
    }

    public void setBtnNewRender(boolean btnNewRender) {
        this.btnNewRender = btnNewRender;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDisableBtn() {
        return disableBtn;
    }

    public void setDisableBtn(String disableBtn) {
        this.disableBtn = disableBtn;
    }

    public String getRequstDate() {
        return requstDate;
    }

    public void setRequstDate(String requstDate) {
        this.requstDate = requstDate;
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
    //</editor-fold>

    String slected = "select";

    public void searchCriteria(ValueChangeEvent event) {
        if (null != event.getNewValue()) {
            slected = event.getNewValue().toString();
            if (slected.equalsIgnoreCase("Employee Name")) {
                disableEmpID = "true";
                disableEmpName = "false";
            } else {
                disableEmpID = "false";
                disableEmpName = "true";
            }
        }
    }

    public void btnNewOrSearch() {
        switch (newOrSearch) {
            case "New":
                searchPage = false;
                newPage = true;
                btnNewRender = false;
                newOrSearch = "Search";
                setIcone("ui-icon-search");
                break;
            case "Search":
                searchPage = true;
                newPage = false;
                btnNewRender = false;
                newOrSearch = "New";
                setIcone("ui-icon-plus");
                break;
        }
    }

    public void newButtonAction() {
        resetCashRefundRqst();
        newPage = true;
        searchPage = false;
        btnNewRender = false;
    }

    public ArrayList<HrEmployees> searchPatientByName(String hrEmployee) {
        ArrayList<HrEmployees> employe = null;
        hrEmployees.setFirstName(hrEmployee);
        employe = employeeBean.SearchByFname(hrEmployees);
        return employe;
    }

    public void getPatientByName(SelectEvent event) {
        try {
            hrEmployees.setFirstName(event.getObject().toString());
            hrEmployees = employeeBean.getByFirstName(hrEmployees);
            hrLocalMedSettlements.setRequesterId(hrEmployees);
            hrDepartments = hrEmployees.getDeptId();
            hrJobTypes = hrEmployees.getJobId();
        } catch (Exception ex) {
            System.err.println("Exception");
        }
    }

    public ArrayList<HrEmployees> searchPatientByID(String hrEmployee) {
        ArrayList<HrEmployees> employe = null;
        hrEmployees.setEmpId(hrEmployee);
        employe = employeeBean.SearchByEmpId(hrEmployees);
        return employe;
    }

    public void getPatientByID(SelectEvent event) {
        try {
            if (event.getObject() != null) {
                hrEmployees = (HrEmployees) event.getObject();
                hrLocalMedSettlements.setRequesterId(hrEmployees);
                hrDepartments = hrEmployees.getDeptId();
                hrJobTypes = hrEmployees.getJobId();
            }
//            hrEmployees.setEmpId(event.getObject().toString());
//            hrEmployees = employeeBean.getByEmpId(hrEmployees);
//            hrLocalMedSettlements.setRequesterId(hrEmployees);
//            hrDepartments = hrEmployees.getDeptId();
//            hrJobTypes = hrEmployees.getJobId();
        } catch (Exception ex) {
            System.err.println("Exception");
        }
    }

    public void handleSelectedName(ValueChangeEvent event) {
        if (null != event.getNewValue()) {
            String name = String.valueOf(event.getNewValue().toString());
            medicalInstitutions.setName(name);
        }
    }

    public void recreateDataModel() {
        settlementDetailDataModel = null;
        settlementDetailDataModel = new ListDataModel(new ArrayList(hrLocalMedSettlements.getHrLocalMedSettlementDetailList()));
    }

    public void workflowDataModel() {
        workflowDataModel = null;
        workflowDataModel = new ListDataModel(new ArrayList(hrLocalMedSettlements.getWfHrProcessedList()));
    }

    public void calculatingAmount() {
        amount = 0.0;
        for (HrLocalMedSettlementDetail settlementDetail : hrLocalMedSettlements.getHrLocalMedSettlementDetailList()) {
            amount += settlementDetail.getAmount();
        }
    }

    public void addSettlementDetail() {
        if ((checkDuplication.contains(hrLocalMedSettlementDetail.getMedicalInstitution())) && (checkDuplication.contains(hrLocalMedSettlementDetail.getReceiptNumber()))) {
            JsfUtil.addFatalMessage("Duplicate entry");
        } else {
            hrLocalMedSettlements.addSettlementDetail(hrLocalMedSettlementDetail);
            checkDuplication.add(hrLocalMedSettlementDetail.getMedicalInstitution());
            checkDuplication.add(hrLocalMedSettlementDetail.getReceiptNumber());
            recreateDataModel();
            calculatingAmount();
            hrLocalMedSettlementDetail = new HrLocalMedSettlementDetail();
        }
    }

    public void editDataTable() {
        hrLocalMedSettlementDetail = settlementDetailDataModel.getRowData();
        addOrUpdate = "Modify";
    }

    public ArrayList<HrEmployees> searchPreparerName(String hrEmp) {
        ArrayList<HrEmployees> preparer = null;
        preparerEmp.setFirstName(hrEmp);
        preparer = employeeBean.SearchByFname(preparerEmp);
        return preparer;
    }

    public void getPreparerName(SelectEvent event) {
        try {
            preparerEmp.setFirstName(event.getObject().toString());
            preparerEmp = employeeBean.getByFirstName(preparerEmp);
        } catch (Exception ex) {
            System.err.println("Exception");
        }
    }

    public void saveMedicalCashRefundRequest() {
        try {
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(sessionBeanLocal.getUserName(), "saveMedicalCashRefundRequest", dataset)) {
                if ((!(getSettlementDetailDataModel().getRowCount() > 0))) {
                    JsfUtil.addFatalMessage("Data table shoud be filled");
                } else {
                    try {
                        if (updateStatus == 0) {
                            SecureRandom randomNum = new SecureRandom();
                            Integer num = randomNum.nextInt(100000);
                            String formate = String.format("%05d", num);
                            hrLocalMedSettlements.setReferenceNo("Med" + formate);
                            hrLocalMedSettlements.setStatus(String.valueOf(Constants.PREPARE_VALUE));
                            hrLocalMedSettlements.setRequestDate(requstDate);
                            hrLocalMedSettlements.setPreparedBy(sessionBeanLocal.getUserId());
                            hrLocalMedSettlements.setRemark(wfHrProcessed.getCommentGiven());
                            wfHrProcessed.setMedicalCashRefundId(hrLocalMedSettlements);
                            wfHrProcessed.setDecision(Integer.valueOf(hrLocalMedSettlements.getStatus()));
                            wfHrProcessed.setProcessedBy(sessionBeanLocal.getUserId());
                            cashRefundBeanLocal.saveOrUpdate(hrLocalMedSettlements);
                            wfHrProcessedBeanLocal.saveOrUpdate(wfHrProcessed);
                            JsfUtil.addSuccessMessage("Saved successfully");
                            resetCashRefundRqst();
                        } else {
                            cashRefundBeanLocal.saveOrUpdate(hrLocalMedSettlements);
                            JsfUtil.addSuccessMessage("Modified successfully");
                            resetCashRefundRqst();
                        }
                    } catch (Exception ex) {
                        JsfUtil.addFatalMessage("Error occured while save update");
                    }
                }
            } else {
                EventEntry eventEntry = new EventEntry();
                eventEntry.setSessionId(sessionBeanLocal.getSessionID());
                eventEntry.setUserId(sessionBeanLocal.getUserId());
                QName qualifiedName = new QName("", "project");
                JAXBElement<String> userName = new JAXBElement<String>(qualifiedName, String.class, null, sessionBeanLocal.getUserName());
                eventEntry.setUserLogin(userName);
                JAXBElement<String> module = new JAXBElement<String>(qualifiedName, String.class, null, "HRMS");
                eventEntry.setModule(module);
                JAXBElement<String> program = new JAXBElement<String>(qualifiedName, String.class, null, "saveMedicalCashRefundRequest");
                eventEntry.setProgram(program);
                security.addEventLog(eventEntry, dataset);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void resetCashRefundRqst() {
        hrLocalMedSettlements = new HrLocalMedSettlements();
        hrEmployees = new HrEmployees();
        preparerEmp = new HrEmployees();
        hrDepartments = new HrDepartments();
        hrJobTypes = new HrJobTypes();
        wfHrProcessed = new WfHrProcessed();
        settlementDetailDataModel = new ArrayDataModel<>();
        saveOrUpdate = "Save";
        amount = 0;
        disableBtn = "false";
    }

    public void medicalSettlementList() {
        if (srcEmployees.getFirstName() != null) {
            requestList = cashRefundBeanLocal.findByName(srcEmployees);
            settlementDataModel = new ListDataModel(requestList);
        } else if (srcEmployees.getFirstName() == null) {
            requestList = cashRefundBeanLocal.findAllRequest();
            settlementDataModel = new ListDataModel(requestList);
        } else {
            JsfUtil.addFatalMessage("Sorry!. Please try again.");
        }
    }

    int reqStatus = 0;

    private void loadMedSettlement() {
        try {
            List<HrLocalMedSettlements> readFilteredMedSettlement = cashRefundBeanLocal.loadMedicaCashRefund(reqStatus);
            settlementDataModel = new ListDataModel(readFilteredMedSettlement);
        } catch (Exception ex) {
            JsfUtil.addFatalMessage("Can't get value");
        }
    }

    public void filiterByStatus(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            reqStatus = Integer.valueOf(event.getNewValue().toString());
            loadMedSettlement();
        }
    }

    private void populateTable() {
        try {
            List<HrLocalMedSettlements> filteredCashRefund = new ArrayList<>();
            List<HrLocalMedSettlements> readFilteredCashRefund = cashRefundBeanLocal.loadMedicaCashRefund(reqStatus);
            for (HrLocalMedSettlements cashRefund : readFilteredCashRefund) {
                if (Integer.valueOf(cashRefund.getStatus()) == 0) {
                    cashRefund.setStatus(String.valueOf("Active"));
                } else {
                    cashRefund.setStatus(String.valueOf("Inactive"));
                }
                filteredCashRefund.add(cashRefund);
            }
            settlementDataModel = new ListDataModel(filteredCashRefund);
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

    public SelectItem[] getStatusList() {
        SelectItem[] items = new SelectItem[4];
        items[0] = new SelectItem("null", "--- Select ---");
        items[1] = new SelectItem(Constants.PREPARE_VALUE, "Load request list");
        items[2] = new SelectItem(Constants.APPROVE_VALUE, "Load approved list");
        items[3] = new SelectItem(Constants.APPROVE_REJECT_VALUE, "Load rejected list");
        return items;
    }

    public void loadByStatus(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            selectedStatus = Integer.valueOf(String.valueOf(event.getNewValue()));
            if (selectedStatus == Constants.PREPARE_VALUE) {
                status.setStatus1(Constants.PREPARE_VALUE);
                medSettlementList = cashRefundBeanLocal.loadReqMedSettList(status, sessionBeanLocal.getUserId());
                settlementDataModel = new ListDataModel<>(medSettlementList);
            } else if (selectedStatus == Constants.APPROVE_VALUE) {
                status.setStatus1(Constants.APPROVE_VALUE);
                status.setStatus2(Constants.CHECK_APPROVE_VALUE);
                medSettlementList = cashRefundBeanLocal.loadApproveMedSettList(status, sessionBeanLocal.getUserId());
                settlementDataModel = new ListDataModel<>(medSettlementList);
            } else if (selectedStatus == Constants.APPROVE_REJECT_VALUE) {
                status.setStatus1(Constants.APPROVE_REJECT_VALUE);
                status.setStatus2(Constants.CHECK_REJECT_VALUE);
                medSettlementList = cashRefundBeanLocal.loadApproveMedSettList(status, sessionBeanLocal.getUserId());
                settlementDataModel = new ListDataModel<>(medSettlementList);
            }
        }
    }

    public void cashRefundDisplayChanged(SelectEvent event) {
        hrLocalMedSettlements = (HrLocalMedSettlements) event.getObject();
        hrEmployees = hrLocalMedSettlements.getRequesterId();
        hrDepartments = hrEmployees.getDeptId();
        hrJobTypes = hrEmployees.getJobId();
        requstDate = hrLocalMedSettlements.getRequestDate();
        if (hrLocalMedSettlements.getStatus().equals(String.valueOf(Constants.APPROVE_VALUE)) || hrLocalMedSettlements.getStatus().equals(String.valueOf(Constants.CHECK_APPROVE_VALUE))) {
            disableBtn = "true";
        } else {
            disableBtn = "false";
        }
        newPage = true;
        searchPage = false;
        btnNewRender = true;
        newOrSearch = "Search";
        saveOrUpdate = "Update";
        setIcone("ui-icon-search");
        recreateDataModel();
        workflowDataModel();
        calculatingAmount();
    }

}
