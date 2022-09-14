/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.controller.medical;

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
import webService.UserStatus;
import et.gov.eep.commonApplications.businessLogic.WfHrProcessedBeanLocal;
import et.gov.eep.commonApplications.entity.Status;
import et.gov.eep.commonApplications.entity.WfHrProcessed;
import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.commonApplications.utility.date.StringDateManipulation;
import et.gov.eep.hrms.businessLogic.employee.HrEmployeeBeanLocal;
import et.gov.eep.hrms.businessLogic.medical.CashRefundApproveBeanLocal;
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
@Named(value = "cashRefundApproveController")
@ViewScoped
public class CashRefundApproveController implements Serializable {

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
    @Inject
    WorkFlow workFlow;

    @EJB
    CashRefundApproveBeanLocal cashRefundApproveBeanLocal;
    @EJB
    HrEmployeeBeanLocal employeeBean;
    @EJB
    WfHrProcessedBeanLocal wfHrProcessedBeanLocal;

    DataModel<HrLocalMedSettlementDetail> settlementDetailDataModel;
    DataModel<HrLocalMedSettlements> settlementDataModel = new ListDataModel<>();
    DataModel<WfHrProcessed> workflowDataModel = new ListDataModel<>();
    private List<HrLocalMedSettlements> requestList;
    List<SelectItem> filterByStatus = new ArrayList<>();
    private List<HrLocalMedSettlements> medSettlementList;

    int updateStatus = 0;
    private String saveOrUpdate = "Save";
    private String addOrUpdate = "Add";
    private String newOrSearch = "New";
    private String icone = "ui-icon-document";
    private boolean searchPage = true;
    private boolean newPage = false;
    double amount;
    String selectedValue = "";
    private int selectedStatus;
    private int status1 = 0, prepareBy;
    UserStatus userStatus = new UserStatus();
    Status status = new Status();
    private int reqSize;
    private boolean disableBtn = false;

    public CashRefundApproveController() {
    }

    @PostConstruct
    public void init() {
        setFilterByStatus(cashRefundApproveBeanLocal.filterByStatus());
        settlementDataModel = new ListDataModel(cashRefundApproveBeanLocal.loadMedicaCashRefund(reqStatus));
        wfHrProcessed.setProcessedOn(StringDateManipulation.toDayInEc());
        medSettlementList = cashRefundApproveBeanLocal.findPreparedList();
        if (medSettlementList != null && medSettlementList.size() >= 1) {
            reqSize = cashRefundApproveBeanLocal.findPreparedList().size();
        }
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

    public List<HrLocalMedSettlements> getMedSettlementList() {
        if (medSettlementList == null) {
            medSettlementList = new ArrayList<>();
        }
        return medSettlementList;
    }

    public void setMedSettlementList(List<HrLocalMedSettlements> medSettlementList) {
        this.medSettlementList = medSettlementList;
    }

    public int getReqSize() {
        return reqSize;
    }

    public void setReqSize(int reqSize) {
        this.reqSize = reqSize;
    }

    public boolean isDisableBtn() {
        return disableBtn;
    }

    public void setDisableBtn(boolean disableBtn) {
        this.disableBtn = disableBtn;
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

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
//</editor-fold>

    public void btnSearch() {
        searchPage = true;
        newPage = false;
    }

    public void btnNewOrSearch() {
        switch (newOrSearch) {
            case "New":
                searchPage = false;
                newPage = true;
                newOrSearch = "Search";
                setIcone("ui-icon-search");
                break;
            case "Search":
                searchPage = true;
                newPage = false;
                newOrSearch = "New";
                setIcone("ui-icon-document");
                break;
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

    public void handleDecision(ValueChangeEvent event) {
        if (null != event.getNewValue()) {
            selectedValue = event.getNewValue().toString();
        }
    }

    public void saveMedicalCashRefundApprove() {
        try {
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(sessionBeanLocal.getUserName(), "saveMedicalCashRefundApprove", dataset)) {
                if ((!(getSettlementDetailDataModel().getRowCount() > 0))) {
                    JsfUtil.addFatalMessage("Data table shoud be filled");
                } else {
                    try {
                        if (selectedValue.equalsIgnoreCase("1") && workFlow.isApproveStatus()) {
                            workFlow.setUserStatus(Constants.APPROVE_VALUE);
                            hrLocalMedSettlements.setStatus(String.valueOf(workFlow.getUserStatus()));
                        } else if (selectedValue.equalsIgnoreCase("1") && workFlow.isCheckStatus()) {
                            workFlow.setUserStatus(Constants.CHECK_APPROVE_VALUE);
                            hrLocalMedSettlements.setStatus(String.valueOf(workFlow.getUserStatus()));
                        } else if (selectedValue.equalsIgnoreCase("2") && workFlow.isApproveStatus()) {
                            workFlow.setUserStatus(Constants.APPROVE_REJECT_VALUE);
                            hrLocalMedSettlements.setStatus(String.valueOf(workFlow.getUserStatus()));
                        } else if (selectedValue.equalsIgnoreCase("2") && workFlow.isCheckStatus()) {
                            workFlow.setUserStatus(Constants.CHECK_REJECT_VALUE);
                            hrLocalMedSettlements.setStatus(String.valueOf(workFlow.getUserStatus()));
                        }
                        wfHrProcessed.setMedicalCashRefundId(hrLocalMedSettlements);
                        wfHrProcessed.setDecision(Integer.valueOf(hrLocalMedSettlements.getStatus()));
                        wfHrProcessed.setProcessedBy(sessionBeanLocal.getUserId());
                        cashRefundApproveBeanLocal.saveOrUpdate(hrLocalMedSettlements);
                        wfHrProcessedBeanLocal.saveOrUpdate(wfHrProcessed);
                        medSettlementList = cashRefundApproveBeanLocal.findPreparedList();
                        JsfUtil.addSuccessMessage("Approved successfully");
                        clearPage();
                        settlementDataModel = new ListDataModel(cashRefundApproveBeanLocal.loadMedicaCashRefund(reqStatus));
                    } catch (Exception e) {
                        e.printStackTrace();
                        JsfUtil.addFatalMessage("Error occured while saving");
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
                JAXBElement<String> program = new JAXBElement<String>(qualifiedName, String.class, null, "saveMedicalCashRefundApprove");
                eventEntry.setProgram(program);
                security.addEventLog(eventEntry, dataset);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void clearPage() {
        hrLocalMedSettlements = new HrLocalMedSettlements();
        hrEmployees = new HrEmployees();
        preparerEmp = new HrEmployees();
        hrDepartments = new HrDepartments();
        hrJobTypes = new HrJobTypes();
        wfHrProcessed = new WfHrProcessed();
        settlementDetailDataModel = new ListDataModel<>();
        saveOrUpdate = "Save";
        amount = 0;
    }

    int reqStatus = 0;

    private void populateTable() {
        try {
            List<HrLocalMedSettlements> filteredCashRefund = new ArrayList<>();
            List<HrLocalMedSettlements> readFilteredCashRefund = cashRefundApproveBeanLocal.loadMedicaCashRefund(reqStatus);
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

    public void searchByStatus(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            reqStatus = Integer.valueOf(event.getNewValue().toString());
            populateTable();
        }
    }

    public void populateCashRefund() {
        setHrEmployees(hrLocalMedSettlements.getRequesterId());
        hrDepartments = hrEmployees.getDeptId();
        hrJobTypes = hrEmployees.getJobId();
        if (hrLocalMedSettlements.getStatus().equalsIgnoreCase(String.valueOf(Constants.PREPARE_VALUE))) {
            disableBtn = false;
        } else {
            disableBtn = true;
        }
        newPage = true;
        searchPage = false;
        newOrSearch = "Search";
        saveOrUpdate = "Update";
        setIcone("ui-icon-search");
        recreateDataModel();
        workflowDataModel();
        calculatingAmount();
    }

    public void populateNotification(ValueChangeEvent event) {
        hrLocalMedSettlements = (HrLocalMedSettlements) event.getNewValue();
        populateCashRefund();
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("PF('widNotf').hide();");
    }

    public void populateDatatable(SelectEvent event) {
        hrLocalMedSettlements = (HrLocalMedSettlements) event.getObject();
        populateCashRefund();
    }

}
