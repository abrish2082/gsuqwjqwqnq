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
import et.gov.eep.hrms.businessLogic.medical.BillApproveBeanLocal;
import et.gov.eep.hrms.entity.employee.HrEmployees;
import et.gov.eep.hrms.entity.medical.HrLocalMedInstitutions;
import et.gov.eep.hrms.entity.medical.HrLocalMedInvoiceDetails;
import et.gov.eep.hrms.entity.medical.HrLocalMedInvoices;

/**
 *
 * @author INSA
 */
@Named(value = "billApproveController")
@ViewScoped
public class BillApproveController implements Serializable {

    @Inject
    HrLocalMedInvoices hrLocalMedInvoices;
    @Inject
    HrLocalMedInvoices srcLocalMedInvoices;
    @Inject
    HrLocalMedInvoiceDetails hrLocalMedInvoiceDetail;
    @Inject
    HrLocalMedInstitutions medicalInstitutions;
    @Inject
    HrEmployees hrEmployees;
    @Inject
    HrEmployees approverEmp;
    @Inject
    WfHrProcessed wfHrProcessed;
    @Inject
    SessionBean sessionBeanLocal;
    @Inject
    WorkFlow workFlow;

    @EJB
    BillApproveBeanLocal billApproveBeanLocal;
    @EJB
    HrEmployeeBeanLocal hrEmployeeBean;
    @EJB
    WfHrProcessedBeanLocal wfHrProcessedBeanLocal;

    int updateStatus = 0;
    private String saveOrUpdate = "Save";
    private String newOrSearch = "New";
    private String icone = "ui-icon-plus";
    private boolean searchPage = true;
    private boolean newPage = false;
    private String approDate;
    private String selectedValue = "";
    private int selectedStatus;
    private int status1 = 0, prepareBy;
    UserStatus userStatus = new UserStatus();
    Status status = new Status();
    private boolean disableBtn = false;

    List<HrLocalMedInvoices> institutionName;
    List<HrLocalMedInvoices> medInvoiceList;
    List<SelectItem> filterByStatus = new ArrayList<>();
    DataModel<HrLocalMedInvoiceDetails> invoiceDetailDataModel = new ListDataModel<>();
    DataModel<HrLocalMedInvoices> MedInvoiceDataModel;
    DataModel<WfHrProcessed> workflowDataModel = new ListDataModel<>();
    private List<HrLocalMedInvoices> medInvoicesList;
    private List<HrLocalMedInvoices> medBillRequesttList;

    public BillApproveController() {
    }

    @PostConstruct
    public void init() {
        wfHrProcessed.setProcessedOn(StringDateManipulation.toDayInEc());
        medBillRequesttList = billApproveBeanLocal.findPreparedList();
    }

    //<editor-fold defaultstate="collapsed" desc="getter setter">
    public HrLocalMedInvoices getHrLocalMedInvoices() {
        return hrLocalMedInvoices;
    }

    public void setHrLocalMedInvoices(HrLocalMedInvoices hrLocalMedInvoices) {
        this.hrLocalMedInvoices = hrLocalMedInvoices;
    }

    public HrLocalMedInvoices getSrcLocalMedInvoices() {
        return srcLocalMedInvoices;
    }

    public void setSrcLocalMedInvoices(HrLocalMedInvoices srcLocalMedInvoices) {
        this.srcLocalMedInvoices = srcLocalMedInvoices;
    }

    public HrLocalMedInvoiceDetails getHrLocalMedInvoiceDetail() {
        return hrLocalMedInvoiceDetail;
    }

    public void setHrLocalMedInvoiceDetail(HrLocalMedInvoiceDetails hrLocalMedInvoiceDetail) {
        this.hrLocalMedInvoiceDetail = hrLocalMedInvoiceDetail;
    }

    public HrLocalMedInstitutions getMedicalInstitutions() {
        return medicalInstitutions;
    }

    public void setMedicalInstitutions(HrLocalMedInstitutions medicalInstitutions) {
        this.medicalInstitutions = medicalInstitutions;
    }

    public HrEmployees getHrEmployees() {
        return hrEmployees;
    }

    public void setHrEmployees(HrEmployees hrEmployees) {
        this.hrEmployees = hrEmployees;
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

    public List<HrLocalMedInvoices> getMedBillRequesttList() {
        if (medBillRequesttList == null) {
            medBillRequesttList = new ArrayList<>();
        }
        return medBillRequesttList;
    }

    public void setMedBillRequesttList(List<HrLocalMedInvoices> medBillRequesttList) {
        this.medBillRequesttList = medBillRequesttList;
    }

    public HrEmployees getApproverEmp() {
        return approverEmp;
    }

    public void setApproverEmp(HrEmployees approverEmp) {
        this.approverEmp = approverEmp;
    }

    public String getSaveOrUpdate() {
        return saveOrUpdate;
    }

    public void setSaveOrUpdate(String saveOrUpdate) {
        this.saveOrUpdate = saveOrUpdate;
    }

    public String getNewOrSearch() {
        return newOrSearch;
    }

    public void setNewOrSearch(String newOrSearch) {
        this.newOrSearch = newOrSearch;
    }

    public boolean getDisableBtn() {
        return disableBtn;
    }

    public void setDisableBtn(boolean disableBtn) {
        this.disableBtn = disableBtn;
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

    public String getApproDate() {
        approDate = StringDateManipulation.toDayInEc();
        return approDate;
    }

    public void setApproDate(String approDate) {
        this.approDate = approDate;
    }

    public List<HrLocalMedInvoices> getInstitutionName() {
        return billApproveBeanLocal.findAll();
    }

    public void setInstitutionName(List<HrLocalMedInvoices> institutionName) {
        this.institutionName = institutionName;
    }

    public List<SelectItem> getFilterByStatus() {
        return billApproveBeanLocal.filterByStatus();
    }

    public void setFilterByStatus(List<SelectItem> filterByStatus) {
        this.filterByStatus = filterByStatus;
    }

    public List<HrLocalMedInvoices> getMedInvoiceList() {
        if (medInvoiceList == null) {
            medInvoiceList = new ArrayList<>();
        }
        return medInvoiceList;
    }

    public void setMedInvoiceList(List<HrLocalMedInvoices> medInvoiceList) {
        this.medInvoiceList = medInvoiceList;
    }

    public DataModel<HrLocalMedInvoiceDetails> getInvoiceDetailDataModel() {
        if (invoiceDetailDataModel == null) {
            invoiceDetailDataModel = new ArrayDataModel<>();
        }
        return invoiceDetailDataModel;
    }

    public void setInvoiceDetailDataModel(DataModel<HrLocalMedInvoiceDetails> invoiceDetailDataModel) {
        this.invoiceDetailDataModel = invoiceDetailDataModel;
    }

    public DataModel<HrLocalMedInvoices> getMedInvoiceDataModel() {
        return MedInvoiceDataModel = new ListDataModel(billApproveBeanLocal.searchByStatus(reqStatus));
    }

    public void setMedInvoiceDataModel(DataModel<HrLocalMedInvoices> MedInvoiceDataModel) {
        this.MedInvoiceDataModel = MedInvoiceDataModel;
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
                setIcone("ui-icon-plus");
                break;
        }
    }

    public void workflowDataModel() {
        workflowDataModel = null;
        workflowDataModel = new ListDataModel(new ArrayList(hrLocalMedInvoices.getWfHrProcessedList()));
    }

    //<editor-fold defaultstate="collapsed" desc="main">
    public void srcRecreateDataModel(List<HrLocalMedInvoices> srcRecreateDataModel) {
        MedInvoiceDataModel = null;
        MedInvoiceDataModel = new ListDataModel(new ArrayList<>(srcRecreateDataModel));
    }

    public void creditBill(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            String instName = String.valueOf(event.getNewValue());
            srcRecreateDataModel(billApproveBeanLocal.findByName(instName));
        }
    }

    public ArrayList<HrEmployees> searchApproverName(String hrEmp) {
        ArrayList<HrEmployees> evaluatorEmp = null;
        approverEmp.setFirstName(hrEmp);
        evaluatorEmp = hrEmployeeBean.SearchByFname(approverEmp);
        return evaluatorEmp;
    }

    public void getApproverName(SelectEvent event) {
        try {
            approverEmp.setFirstName(event.getObject().toString());
            approverEmp = hrEmployeeBean.getByFirstName(approverEmp);
//            hrLocalMedInvoices.setPreparedBy(approverEmp);
        } catch (Exception ex) {
            System.err.println("Exception");
        }
    }

    public void medicalInvoiceList() {
        try {
            if (medicalInstitutions.getName() == null) {
                medInvoiceList = billApproveBeanLocal.findAll();
                MedInvoiceDataModel = new ListDataModel(medInvoiceList);
            } else if (!medicalInstitutions.getName().isEmpty()) {
                medInvoiceList = billApproveBeanLocal.findByInstitutionName(medicalInstitutions);
                MedInvoiceDataModel = new ListDataModel(medInvoiceList);
            } else {
                JsfUtil.addFatalMessage("Sorry!. Please try again.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            JsfUtil.addFatalMessage("Sorry! Reload the page and try again.");
        }
    }

    public void recreateDataModel() {
        invoiceDetailDataModel = null;
        invoiceDetailDataModel = new ListDataModel(new ArrayList(hrLocalMedInvoices.getHrLocalMedInvoiceDetailsList()));
    }

    //<editor-fold defaultstate="collapsed" desc="calculatiion">
    double employeeShare;
    double companyShare;
    double subTotal;

    public double getEmployeeShare() {
        return employeeShare;
    }

    public void setEmployeeShare(double employeeShare) {
        this.employeeShare = employeeShare;
    }

    public double getCompanyShare() {
        return companyShare;
    }

    public void setCompanyShare(double companyShare) {
        this.companyShare = companyShare;
    }

    public double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(double subTotal) {
        this.subTotal = subTotal;
    }

    public void calculatingEmployeeShare() {
        employeeShare = 0.0;
        for (HrLocalMedInvoiceDetails hrLocalMedInvoiceDetailList : hrLocalMedInvoices.getHrLocalMedInvoiceDetailsList()) {
            employeeShare += hrLocalMedInvoiceDetailList.getAmount() * 10 / 100;
        }
    }

    public void calculatingCompanyShare() {
        companyShare = 0.0;
        for (HrLocalMedInvoiceDetails hrLocalMedInvoiceDetailList : hrLocalMedInvoices.getHrLocalMedInvoiceDetailsList()) {
            companyShare += hrLocalMedInvoiceDetailList.getAmount() * 90 / 100;
        }
    }

    public void calculatingSubTotal() {
        subTotal = 0;
        subTotal = employeeShare + companyShare;
    }
    //</editor-fold>

    public void medicalInvoice() {
        medicalInstitutions = hrLocalMedInvoices.getInstitutionId();
        approDate = hrLocalMedInvoices.getApprovedDate();
        if (hrLocalMedInvoices.getStatus().equals(Constants.PREPARE_VALUE)) {
            disableBtn = false;
        } else {
            disableBtn = true;
        }
        newPage = true;
        searchPage = false;
        saveOrUpdate = "Update";
        recreateDataModel();
        workflowDataModel();
        calculatingCompanyShare();
        calculatingEmployeeShare();
        calculatingSubTotal();
    }

    public void populateDatatable(SelectEvent event) {
        hrLocalMedInvoices = (HrLocalMedInvoices) event.getObject();
        medicalInvoice();
    }

    public void populateNotification(ValueChangeEvent event) {
        hrLocalMedInvoices = (HrLocalMedInvoices) event.getNewValue();
        medicalInvoice();
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("PF('widNotf').hide();");
    }

    public void handleDecision(ValueChangeEvent event) {
        if (null != event.getNewValue()) {
            selectedValue = event.getNewValue().toString();
        }
    }

    //<editor-fold defaultstate="collapsed" desc="save update">
    public void saveMedicalCreditBillApprove() {
        try {
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(sessionBeanLocal.getUserName(), "saveMedicalCreditBillApprove", dataset)) {
                if (hrLocalMedInvoices == null) {
                    JsfUtil.addFatalMessage("Can't approve empty data");
                } else if ((!(getInvoiceDetailDataModel().getRowCount() > 0))) {
                    JsfUtil.addFatalMessage("Can't approve empty dataTable");
                } else {
                    try {
                        if (selectedValue.equalsIgnoreCase("1") && workFlow.isApproveStatus()) {
                            workFlow.setUserStatus(Constants.APPROVE_VALUE);
                            hrLocalMedInvoices.setStatus(workFlow.getUserStatus());
                        } else if (selectedValue.equalsIgnoreCase("1") && workFlow.isCheckStatus()) {
                            workFlow.setUserStatus(Constants.CHECK_APPROVE_VALUE);
                            hrLocalMedInvoices.setStatus(workFlow.getUserStatus());
                        } else if (selectedValue.equalsIgnoreCase("2") && workFlow.isApproveStatus()) {
                            workFlow.setUserStatus(Constants.APPROVE_REJECT_VALUE);
                            hrLocalMedInvoices.setStatus(workFlow.getUserStatus());
                        } else if (selectedValue.equalsIgnoreCase("2") && workFlow.isCheckStatus()) {
                            workFlow.setUserStatus(Constants.CHECK_REJECT_VALUE);
                            hrLocalMedInvoices.setStatus(workFlow.getUserStatus());
                        }
                        hrLocalMedInvoices.setApprovedDate(approDate);
                        wfHrProcessed.setMedicalCreditBillId(hrLocalMedInvoices);
                        wfHrProcessed.setDecision(hrLocalMedInvoices.getStatus());
                        wfHrProcessed.setProcessedBy(sessionBeanLocal.getUserId());
                        billApproveBeanLocal.saveOrUpdate(hrLocalMedInvoices);
                        wfHrProcessedBeanLocal.saveOrUpdate(wfHrProcessed);
                        medBillRequesttList = billApproveBeanLocal.findPreparedList();
                        JsfUtil.addSuccessMessage("Successfully saved");
                        clearPage();
                    } catch (Exception e) {
                        JsfUtil.addFatalMessage("Error occure while saving");
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
                JAXBElement<String> program = new JAXBElement<String>(qualifiedName, String.class, null, "saveMedicalCreditBillApprove");
                eventEntry.setProgram(program);
                security.addEventLog(eventEntry, dataset);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void clearPage() {
        hrLocalMedInvoices = new HrLocalMedInvoices();
        hrEmployees = new HrEmployees();
        approverEmp = new HrEmployees();
        wfHrProcessed = new WfHrProcessed();
        medicalInstitutions = new HrLocalMedInstitutions();
        invoiceDetailDataModel = null;
        saveOrUpdate = "Save";
        employeeShare = 0;
        companyShare = 0;
        subTotal = 0;
    }
    //</editor-fold>

    int reqStatus = 0;

    public void searchByStatus(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            reqStatus = Integer.valueOf(event.getNewValue().toString());
            MedInvoiceDataModel = new ListDataModel(billApproveBeanLocal.searchByStatus(reqStatus));;
        }
    }

    public SelectItem[] getStatusList() {
        SelectItem[] items = new SelectItem[3];
        items[0] = new SelectItem("null", "--- Select ---");
        items[1] = new SelectItem(Constants.PREPARE_VALUE, "Load request and rejected list");
        items[2] = new SelectItem(Constants.APPROVE_VALUE, "Load approved list");
        return items;
    }

    private void populateRequestTable(Status status1) {
        try {
            workFlow = new WorkFlow();
            medInvoicesList = billApproveBeanLocal.loadMedicalInvoiceList(status1, sessionBeanLocal.getUserId());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void filiterByStatus_VLC(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            selectedStatus = Integer.valueOf(String.valueOf(event.getNewValue()));
            if (selectedStatus == Constants.PREPARE_VALUE) {
                status.setStatus1(Constants.PREPARE_VALUE);
                status.setStatus2(Constants.CHECK_REJECT_VALUE);
                status.setStatus3(Constants.APPROVE_REJECT_VALUE);
            } else if (selectedStatus == Constants.APPROVE_VALUE) {
                status.setStatus1(Constants.APPROVE_VALUE);
                status.setStatus2(Constants.CHECK_APPROVE_VALUE);
            }
            populateRequestTable(status);
        }
    }
    //</editor-fold>

}
