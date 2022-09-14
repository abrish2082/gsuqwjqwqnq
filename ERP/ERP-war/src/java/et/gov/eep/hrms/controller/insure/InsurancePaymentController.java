/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.controller.insure;

import et.gov.eep.commonApplications.entity.Status;
import et.gov.eep.commonApplications.entity.WfHrProcessed;
import et.gov.eep.commonApplications.mapper.WfHrProcessedFacade;
import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.hrms.businessLogic.employee.HrEmployeeBeanLocal;
import et.gov.eep.hrms.businessLogic.insure.HrInsuranceDiagonasticResultBeanLocal;
import et.gov.eep.hrms.businessLogic.insure.HrinsurancePaymentLocal;
import et.gov.eep.hrms.businessLogic.insure.HrinsurancebeanLocal;
import et.gov.eep.hrms.businessLogic.insure.InsuranceBranchBeanLocal;
import et.gov.eep.hrms.businessLogic.insure.InsuranceInjuredEmployeesLocal;
import et.gov.eep.hrms.businessLogic.insure.InsurancebeanLocal;
import et.gov.eep.hrms.entity.employee.HrEmpAddresses;
import et.gov.eep.hrms.entity.employee.HrEmpMemberships;
import et.gov.eep.hrms.entity.employee.HrEmployees;
import et.gov.eep.hrms.entity.insurance.HrInsurance;
import et.gov.eep.hrms.entity.insurance.HrInsuranceDiagnosisResult;
import et.gov.eep.hrms.entity.insurance.HrInsuranceInjuredEmployee;
import et.gov.eep.hrms.entity.insurance.HrInsurancePayment;
import et.gov.eep.hrms.entity.insurance.HrInsurancePaymentDetail;
import et.gov.eep.hrms.entity.insurance.HrLuInsuranceBranches;
import et.gov.eep.hrms.entity.insurance.HrLuInsurances;
import et.gov.eep.hrms.entity.lookup.HrLuGrades;
import et.gov.eep.hrms.entity.lookup.HrLuMemberships;
import et.gov.eep.hrms.entity.organization.HrJobTypes;
import et.gov.eep.hrms.mapper.insure.HrInsurancePaymentFacade;
import et.gov.eep.hrms.mapper.lookup.HrEmpMembershipsFacade;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
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
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
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
 * @author meles
 */
@Named(value = "insurancePaymentController")
@ViewScoped
public class InsurancePaymentController implements Serializable {

//<editor-fold defaultstate="collapsed" desc="Entity Injection">
    @Inject
    HrInsuranceDiagnosisResult hrInsuranceDiagnosisResult;
    @Inject
    HrInsurancePaymentDetail hrInsurancePaymentDetail;
    @Inject
    HrInsurancePayment hrInsurancePayment;
    @Inject
    HrInsuranceInjuredEmployee hrInsuranceInjuredEmployee;
    @Inject
    HrInsurance hrInsurance;
    @Inject
    HrLuInsuranceBranches hrLuInsuranceBranches;
    @Inject
    HrLuInsurances hrLuInsurances;
    @Inject
    HrEmployees hrEmployees;
    @Inject
    HrJobTypes hrJobTypes;
    @Inject
    HrEmpAddresses hrEmpAddresses;
    @Inject
    HrLuGrades hrLuGrades;
    @Inject
    HrEmpMemberships hrEmpMemberships;
    @Inject
    HrLuMemberships hrLuMemberships;
    @Inject
    WfHrProcessed wfHrProcessed;
    @Inject
    SessionBean SessionBean;
    @Inject
    WorkFlow workFlow;
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="EJB Declaration">
    @EJB
    HrInsuranceDiagonasticResultBeanLocal hrInsuranceDiagonasticResultBeanLocal;
    @EJB
    InsurancebeanLocal insurancebeanLocal;
    @EJB
    InsuranceInjuredEmployeesLocal insuranceInjuredEmployeesLocal;
    @EJB
    HrinsurancebeanLocal hrinsurancebeanLocal;
    @EJB
    InsuranceBranchBeanLocal insuranceBranchBeanLocal;
    @EJB
    HrEmployeeBeanLocal hrEmployeesBeanLocal;
    @EJB
    HrinsurancePaymentLocal hrinsurancePaymentLocal;
    @EJB
    HrEmpMembershipsFacade hrEmpMembershipsFacade;
    @EJB
    WfHrProcessedFacade wfHrProcessedFacade;
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="DataModel ">
    DataModel<WfHrProcessed> workFlowDataModel = new ListDataModel<>();
    DataModel<HrInsuranceInjuredEmployee> hrinsuranceinjuredmodel = new ListDataModel<>();
    DataModel<HrLuInsuranceBranches> hrsmplandetailmodel = new ListDataModel<>();
    DataModel<HrInsurancePaymentDetail> hrInsurancePaymentDetailmodel;
    DataModel<HrInsurancePayment> inpaymentrequestDataModel;
    HrLuInsuranceBranches selectedRow = null;
    DataModel<HrInsurancePayment> insurancepaymentdatamodel;
    List<HrEmpMemberships> empMembershipList = new ArrayList<>();
    List<HrInsurancePayment> HrInsurancePaymentList = new ArrayList<>();
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Variable declaration">
    private String addorUpdate = "Add";
    List<SelectItem> filterByStatus = new ArrayList<>();
    HrInsuranceInjuredEmployee selectedRow2 = null;
    private boolean renderPnlCreateGatePass = false;
    private boolean disableBtnCreate;
    private String saveorUpdateBundle = "Save";
    private String createOrSearchBundle = "New";
    private boolean renderPnlCreateAdditional = false;
    private boolean renderPnlManPage = true;
    private String icone = "ui-icon-plus";
    boolean lockInput = false;
    private String saveOrUpdateButton = "Save";
    int update = 0;
    int selectedstatus = 1;
    Integer requestNotificationCounter = 0;
    private String choosePermanet = "true";
    private String chooseDailyLabour = "false";
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="post construct">
    @PostConstruct
    public void listdiagonsis() {
        FindByNamePermanent();
        empMembershipList = hrEmpMembershipsFacade.findAll();
        requestcounter();
    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="getter and setter">
    public List<HrInsurancePayment> getHrInsurancePaymentList() {
        return HrInsurancePaymentList;
    }
    
    public void setHrInsurancePaymentList(List<HrInsurancePayment> HrInsurancePaymentList) {
        this.HrInsurancePaymentList = HrInsurancePaymentList;
    }
    
    public void FindByNamePermanent() {
        Integer status1 = 0;
        String employe = "Permanent/contract";
        String daily = "Daily Labour";
        hrInsurancediagnosisResultList = hrinsurancePaymentLocal.empname(hrInsuranceDiagnosisResult, employe, daily, status1);
    }
    
    public void FindByNameDaily() {
        Integer status2 = 0;
        String daily = "Daily Labour";
        hrInsurancediagnosisResultList = hrinsurancePaymentLocal.empnamed(hrInsuranceDiagnosisResult, daily, status2);
    }
    
    public List<HrEmpMemberships> getEmpMembershipList() {
        return empMembershipList;
    }
    
    public void setEmpMembershipList(List<HrEmpMemberships> empMembershipList) {
        this.empMembershipList = empMembershipList;
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
    
    public Integer getRequestNotificationCounter() {
        return requestNotificationCounter;
    }
    
    public void setRequestNotificationCounter(Integer requestNotificationCounter) {
        this.requestNotificationCounter = requestNotificationCounter;
    }
    
    public DataModel<WfHrProcessed> getWorkFlowDataModel() {
        return workFlowDataModel;
    }
    
    public void setWorkFlowDataModel(DataModel<WfHrProcessed> workFlowDataModel) {
        this.workFlowDataModel = workFlowDataModel;
    }
    
    public boolean isLockInput() {
        return lockInput;
    }
    
    public void setLockInput(boolean lockInput) {
        this.lockInput = lockInput;
    }
    
    public HrEmpMemberships getHrEmpMemberships() {
        if (hrEmpMemberships == null) {
            hrEmpMemberships = new HrEmpMemberships();
        }
        return hrEmpMemberships;
    }
    
    public void setHrEmpMemberships(HrEmpMemberships hrEmpMemberships) {
        this.hrEmpMemberships = hrEmpMemberships;
    }
    
    public HrLuMemberships getHrLuMemberships() {
        if (hrLuMemberships == null) {
            hrLuMemberships = new HrLuMemberships();
        }
        return hrLuMemberships;
    }
    
    public void setHrLuMemberships(HrLuMemberships hrLuMemberships) {
        this.hrLuMemberships = hrLuMemberships;
    }
    
    public HrInsuranceInjuredEmployee getHrInsuranceInjuredEmployee() {
        return hrInsuranceInjuredEmployee;
    }
    
    public HrInsurancePayment getHrInsurancePayment() {
        if (hrInsurancePayment == null) {
            hrInsurancePayment = new HrInsurancePayment();
        }
        return hrInsurancePayment;
    }
    
    public void setHrInsurancePayment(HrInsurancePayment hrInsurancePayment) {
        this.hrInsurancePayment = hrInsurancePayment;
    }
    
    public HrInsuranceDiagnosisResult getHrInsuranceDiagnosisResult() {
        if (hrInsuranceDiagnosisResult == null) {
            hrInsuranceDiagnosisResult = new HrInsuranceDiagnosisResult();
        }
        return hrInsuranceDiagnosisResult;
    }
    
    public DataModel<HrInsurancePaymentDetail> getHrInsurancePaymentDetailmodel() {
        if (hrInsurancePaymentDetailmodel == null) {
            hrInsurancePaymentDetailmodel = new ArrayDataModel<>();
        }
        return hrInsurancePaymentDetailmodel;
    }
    
    public void setHrInsurancePaymentDetailmodel(DataModel<HrInsurancePaymentDetail> hrInsurancePaymentDetailmodel) {
        this.hrInsurancePaymentDetailmodel = hrInsurancePaymentDetailmodel;
    }
    
    public HrInsurancePaymentDetail getHrInsurancePaymentDetail() {
        if (hrInsurancePaymentDetail == null) {
            hrInsurancePaymentDetail = new HrInsurancePaymentDetail();
        }
        return hrInsurancePaymentDetail;
    }
    
    public void setHrInsurancePaymentDetail(HrInsurancePaymentDetail hrInsurancePaymentDetail) {
        this.hrInsurancePaymentDetail = hrInsurancePaymentDetail;
    }
    
    public void setHrInsuranceDiagnosisResult(HrInsuranceDiagnosisResult hrInsuranceDiagnosisResult) {
        this.hrInsuranceDiagnosisResult = hrInsuranceDiagnosisResult;
    }
    
    public HrInsuranceDiagonasticResultBeanLocal getHrInsuranceDiagonasticResultBeanLocal() {
        return hrInsuranceDiagonasticResultBeanLocal;
    }
    
    public void setHrInsuranceDiagonasticResultBeanLocal(HrInsuranceDiagonasticResultBeanLocal hrInsuranceDiagonasticResultBeanLocal) {
        this.hrInsuranceDiagonasticResultBeanLocal = hrInsuranceDiagonasticResultBeanLocal;
    }
    
    public HrinsurancePaymentLocal getHrinsurancePaymentLocal() {
        return hrinsurancePaymentLocal;
    }
    
    public void setHrinsurancePaymentLocal(HrinsurancePaymentLocal hrinsurancePaymentLocal) {
        this.hrinsurancePaymentLocal = hrinsurancePaymentLocal;
    }
    
    public String getSlected() {
        return slected;
    }
    
    public void setSlected(String slected) {
        this.slected = slected;
    }
    
    public void setHrInsuranceInjuredEmployee(HrInsuranceInjuredEmployee hrInsuranceInjuredEmployee) {
        this.hrInsuranceInjuredEmployee = hrInsuranceInjuredEmployee;
    }
    
    public HrInsurance getHrInsurance() {
        if (hrInsurance == null) {
            hrInsurance = new HrInsurance();
        }
        return hrInsurance;
    }
    
    public void setHrInsurance(HrInsurance HrInsurance) {
        this.hrInsurance = HrInsurance;
    }
    
    public HrLuInsuranceBranches getHrLuInsuranceBranches() {
        if (hrLuInsuranceBranches == null) {
            hrLuInsuranceBranches = new HrLuInsuranceBranches();
        }
        return hrLuInsuranceBranches;
    }
    
    public void setHrLuInsuranceBranches(HrLuInsuranceBranches hrLuInsuranceBranches) {
        this.hrLuInsuranceBranches = hrLuInsuranceBranches;
    }
    
    public HrLuInsurances getHrLuInsurances() {
        if (hrLuInsurances == null) {
            hrLuInsurances = new HrLuInsurances();
        }
        return hrLuInsurances;
    }
    
    public void setHrLuInsurances(HrLuInsurances hrLuInsurances) {
        this.hrLuInsurances = hrLuInsurances;
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
    
    public HrEmpAddresses getHrEmpAddresses() {
        if (hrEmpAddresses == null) {
            hrEmpAddresses = new HrEmpAddresses();
        }
        return hrEmpAddresses;
    }
    
    public void setHrEmpAddresses(HrEmpAddresses hrEmpAddresses) {
        this.hrEmpAddresses = hrEmpAddresses;
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
    
    public InsurancebeanLocal getInsurancebeanLocal() {
        return insurancebeanLocal;
    }
    
    public void setInsurancebeanLocal(InsurancebeanLocal InsurancebeanLocal) {
        this.insurancebeanLocal = InsurancebeanLocal;
    }
    
    public InsuranceInjuredEmployeesLocal getInsuranceInjuredEmployeesLocal() {
        return insuranceInjuredEmployeesLocal;
    }
    
    public void setInsuranceInjuredEmployeesLocal(InsuranceInjuredEmployeesLocal InsuranceInjuredEmployeesLocal) {
        this.insuranceInjuredEmployeesLocal = InsuranceInjuredEmployeesLocal;
    }
    
    public HrinsurancebeanLocal getHrinsurancebeanLocal() {
        return hrinsurancebeanLocal;
    }
    
    public void setHrinsurancebeanLocal(HrinsurancebeanLocal hrinsurancebeanLocal) {
        this.hrinsurancebeanLocal = hrinsurancebeanLocal;
    }
    
    public InsuranceBranchBeanLocal getInsuranceBranchBeanLocal() {
        return insuranceBranchBeanLocal;
    }
    
    public void setInsuranceBranchBeanLocal(InsuranceBranchBeanLocal insuranceBranchBeanLocal) {
        this.insuranceBranchBeanLocal = insuranceBranchBeanLocal;
    }
    
    public HrEmployeeBeanLocal getHrEmployeesBeanLocal() {
        return hrEmployeesBeanLocal;
    }
    
    public void setHrEmployeesBeanLocal(HrEmployeeBeanLocal hrEmployeesBeanLocal) {
        this.hrEmployeesBeanLocal = hrEmployeesBeanLocal;
    }
    
    public DataModel<HrInsuranceInjuredEmployee> getHrinsuranceinjuredmodel() {
        return hrinsuranceinjuredmodel;
    }
    
    public void setHrinsuranceinjuredmodel(DataModel<HrInsuranceInjuredEmployee> hrinsuranceinjuredmodel) {
        this.hrinsuranceinjuredmodel = hrinsuranceinjuredmodel;
    }
    
    public DataModel<HrLuInsuranceBranches> getHrsmplandetailmodel() {
        return hrsmplandetailmodel;
    }
    
    public void setHrsmplandetailmodel(DataModel<HrLuInsuranceBranches> hrsmplandetailmodel) {
        this.hrsmplandetailmodel = hrsmplandetailmodel;
    }
    
    public HrLuInsuranceBranches getSelectedRow() {
        return selectedRow;
    }
    
    public void setSelectedRow(HrLuInsuranceBranches selectedRow) {
        this.selectedRow = selectedRow;
    }
    
    public HrInsuranceInjuredEmployee getSelectedRow2() {
        return selectedRow2;
    }
    
    public void setSelectedRow2(HrInsuranceInjuredEmployee selectedRow2) {
        this.selectedRow2 = selectedRow2;
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
    
    public int getSelectedstatus() {
        return selectedstatus;
    }
    
    public void setSelectedstatus(int selectedstatus) {
        this.selectedstatus = selectedstatus;
    }
    
    public String getChoosePermanet() {
        return choosePermanet;
    }
    
    public void setChoosePermanet(String choosePermanet) {
        this.choosePermanet = choosePermanet;
    }
    
    public String getChooseDailyLabour() {
        return chooseDailyLabour;
    }
    
    public void setChooseDailyLabour(String chooseDailyLabour) {
        this.chooseDailyLabour = chooseDailyLabour;
    }
    
    public DataModel<HrInsurancePayment> getInpaymentrequestDataModel() {
        if (inpaymentrequestDataModel == null) {
            inpaymentrequestDataModel = new ArrayDataModel<>();
        }
        return inpaymentrequestDataModel;
    }
    
    public void setInpaymentrequestDataModel(DataModel<HrInsurancePayment> inpaymentrequestDataModel) {
        this.inpaymentrequestDataModel = inpaymentrequestDataModel;
    }
    
    public void recreateDataModel() {
        inpaymentrequestDataModel = null;
        inpaymentrequestDataModel = new ListDataModel(new ArrayList(hrInsuranceDiagnosisResult.getHrInsurancePaymentList()));
    }
    
    private List<HrInsurancePayment> requestList;
    
    public List<HrInsurancePayment> getRequestList() {
        return requestList;
    }
    
    public void setRequestList(List<HrInsurancePayment> requestList) {
        this.requestList = requestList;
    }
    
    public List<SelectItem> getFilterByStatus() {
        return hrinsurancePaymentLocal.filterByStatus();
    }
    
    public void setFilterByStatus(List<SelectItem> filterByStatus) {
        this.filterByStatus = filterByStatus;
    }
//</editor-fold>
    
//<editor-fold defaultstate="collapsed" desc="main methods">
    public void recreateDataModell() {
        inpaymentrequestDataModel = null;
        inpaymentrequestDataModel = new ListDataModel(new ArrayList<>(getRequestList()));
    }
    
    public void resultdisplayChanged(SelectEvent event) {
        inpaymentrequestDataModel = null;
        hrInsurancePayment = (HrInsurancePayment) event.getObject();
        saveorUpdateBundle = "Update";
        setIcone("ui-icon-search");
        recreateDataModel();
    }
    
    String slected = "Select One";
    
    public void handleEmployee(ValueChangeEvent event) {
        if (null != event.getNewValue()) {
            slected = event.getNewValue().toString();
            if (slected.equalsIgnoreCase("Permanent/contract")) {
                choosePermanet = "true";
                chooseDailyLabour = "false";
            } else {
                choosePermanet = "false";
                chooseDailyLabour = "true";
            }
        }
    }
    
    public void populate(SelectEvent events) {
        
        hrLuInsurances = null;
        hrLuInsurances = (HrLuInsurances) events.getObject();
        renderPnlManPage = false;
        renderPnlCreateAdditional = true;
        createOrSearchBundle = "Search";
        update = 1;
        saveOrUpdateButton = "update";
    }
    
    public void populate2(SelectEvent events) {
        hrInsuranceInjuredEmployee = null;
        hrInsuranceInjuredEmployee = (HrInsuranceInjuredEmployee) events.getObject();
        hrInsuranceInjuredEmployee.setId(hrInsuranceInjuredEmployee.getId());
        hrInsuranceInjuredEmployee = insuranceInjuredEmployeesLocal.getSelectedRequest(hrInsuranceInjuredEmployee.getId());
        if (hrInsuranceInjuredEmployee.getType().equalsIgnoreCase("Permanent/contract")) {
            choosePermanet = "true";
            chooseDailyLabour = "false";
            hrEmployees = hrInsuranceInjuredEmployee.getEmpId();
            hrJobTypes = hrEmployees.getJobId();
        } else {
            choosePermanet = "false";
            chooseDailyLabour = "true";
        }
        renderPnlManPage = false;
        renderPnlCreateAdditional = true;
        createOrSearchBundle = "Search";
        update = 1;
        saveOrUpdateButton = "update";
        
    }
    
    public void createNewAdditionalAmount() {
        
        saveorUpdateBundle = "Save";
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
    
    public void getByFirstName(SelectEvent event) {
        try {
            hrEmployees.setFirstName(event.getObject().toString());
            hrEmployees = hrEmployeesBeanLocal.getByFirstName(hrEmployees);
            hrJobTypes = hrEmployees.getJobId();
            hrLuGrades = hrEmployees.getGradeId().getGradeId();
            hrInsuranceInjuredEmployee.setEmpId(hrEmployees);
            hrInsurancePayment.setPrepardby(hrEmployees);
        } catch (Exception ex) {
            System.err.println("Exception");
        }
    }
    
    public ArrayList<HrInsurance> searchByTerminationName(String telephone) {
        ArrayList<HrInsurance> insurancepro = null;
        hrInsurance.setTelephone(telephone);
        insurancepro = hrinsurancebeanLocal.telephone(hrInsurance);
        saveOrUpdateButton = "Update";
        update = 1;
        return insurancepro;
    }
    
    public ArrayList<HrEmployees> searchEmployeeByName(String hrEmployee) {
        ArrayList<HrEmployees> employee = null;
        hrEmployees.setFirstName(hrEmployee);
        employee = hrEmployeesBeanLocal.searchEmployeeByName(hrEmployees);
        return employee;
    }
    
    public void getByTelName(SelectEvent event) {
        try {
            hrInsurance.setTelephone(event.getObject().toString());
            hrInsurance = hrinsurancebeanLocal.getByTerminationName(hrInsurance);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    List<HrInsuranceDiagnosisResult> hrInsurancediagnosisResultList = new ArrayList<>();
    List<HrInsurancePayment> hrInsurpaymentList = new ArrayList<>();
    
    public List<HrInsurancePayment> getHrInsurpaymentList() {
        return hrInsurpaymentList;
    }
    
    public void setHrInsurpaymentList(List<HrInsurancePayment> hrInsurpaymentList) {
        this.hrInsurpaymentList = hrInsurpaymentList;
    }
    
    public List<HrInsuranceDiagnosisResult> getHrInsurancediagnosisResultList() {
        return hrInsurancediagnosisResultList;
    }
    
    public void setHrInsurancediagnosisResultList(List<HrInsuranceDiagnosisResult> hrInsurancediagnosisResultList) {
        this.hrInsurancediagnosisResultList = hrInsurancediagnosisResultList;
    }
    
    public void recreateDataModelwf() {
        workFlowDataModel = null;
        workFlowDataModel = new ListDataModel(new ArrayList(hrInsurancePayment.getWfHrProcessedList()));
        for (int i = 0; i < hrInsurancePayment.getWfHrProcessedList().size(); i++) {
            if (hrInsurancePayment.getWfHrProcessedList().get(i).getDecision() == 0) {
                hrInsurancePayment.getWfHrProcessedList().get(i).setAction("Prepared");
            } else if (hrInsurancePayment.getWfHrProcessedList().get(i).getDecision() == 3) {
                hrInsurancePayment.getWfHrProcessedList().get(i).setAction("Approved");
            } else if (hrInsurancePayment.getWfHrProcessedList().get(i).getDecision() == 2 || hrInsurancePayment.getWfHrProcessedList().get(i).getDecision() == 4) {
                hrInsurancePayment.getWfHrProcessedList().get(i).setAction("Rejected");
            }
            
        }
        
    }
    
    public void vcl_filiterByCategory(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            hrInsuranceDiagnosisResult = (HrInsuranceDiagnosisResult) event.getNewValue();
            hrInsuranceInjuredEmployee = hrInsuranceDiagnosisResult.getHrInsuranceInjuredEmployee();
            hrEmployees = hrInsuranceInjuredEmployee.getEmpId();
            hrJobTypes = hrEmployees.getJobId();
            hrInsurance = hrInsuranceInjuredEmployee.getInsuranceId();
            hrLuInsurances = hrInsurance.getInsuranceId();
            hrLuInsuranceBranches = hrInsurance.getBranchId();
            hrInsurancePayment.setDiagonasisResultId(hrInsuranceDiagnosisResult);
        }
    }
    
    public void display(ValueChangeEvent event) {
        hrInsuranceDiagnosisResult = (HrInsuranceDiagnosisResult) event.getNewValue();
        hrInsuranceInjuredEmployee = hrInsuranceDiagnosisResult.getHrInsuranceInjuredEmployee();
        hrInsurance = hrInsuranceInjuredEmployee.getInsuranceId();
        hrLuInsurances = hrInsurance.getInsuranceId();
        hrLuInsuranceBranches = hrInsurance.getBranchId();
        hrInsurancePayment.setDiagonasisResultId(hrInsuranceDiagnosisResult);
    }
    List<HrInsuranceDiagnosisResult> diagnosisResultList = new ArrayList<>();
    
    public List<HrInsuranceDiagnosisResult> getDiagnosisResultList() {
        return diagnosisResultList;
    }
    
    public void setDiagnosisResultList(List<HrInsuranceDiagnosisResult> diagnosisResultList) {
        this.diagnosisResultList = diagnosisResultList;
    }
    BigDecimal salarypaidd;
    BigDecimal amountpaid;
    BigDecimal compensation;
    BigDecimal corporation;
    
    public void saveInsurancePayment() {
        
        try {
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(SessionBean.getUserName(), "saveInsurancePayment", dataset)) {
                if ((!(getHrInsurancePaymentDetailmodel().getRowCount() > 0))) {
                    JsfUtil.addFatalMessage("Data table shoud be filled");
                } else {
                    try {
                        if (update == 0) {
                            //                        Integer userid = SessionBean.getSessionID();
//                        Object userobj = (Integer) userid;
//                        HrEmployees hrEmployees = new HrEmployees();
//                        hrEmployees = (HrEmployees) userobj;
////                    hrInsurancePayment.setPreparedBy(String.valueOf(SessionBean.getUserId()));
//                        hrInsurancePayment.setPrepardby(hrEmployees);
                            hrInsuranceDiagnosisResult.setStatus(1);
                            hrInsurancePayment.setStatus(Constants.PREPARE_VALUE);
                            //hrInsurancePayment.setPrepardby(hrEmployees);
                            hrInsuranceDiagonasticResultBeanLocal.saveOrUpdate(hrInsuranceDiagnosisResult);
                            hrinsurancePaymentLocal.saveorupdate(hrInsurancePayment);
                            wfHrProcessed.setInsurancePaymentId(hrInsurancePayment);
                            wfHrProcessed.setProcessedOn(hrInsurancePayment.getPreparddate());
                            wfHrProcessed.setProcessedBy(SessionBean.getUserId());
                            wfHrProcessed.setCommentGiven(hrInsurancePayment.getRemark());
                            wfHrProcessed.setDecision(hrInsurancePayment.getStatus());
                            wfHrProcessedFacade.saveOrUpdate(wfHrProcessed);
                            JsfUtil.addSuccessMessage("Successfully Saved");
                            resetInsurancePayment();
                        } else if (update == 1) {
                            if (hrInsurancePayment.getStatus() == 3) {
                                JsfUtil.addFatalMessage("Can't update approved data");
                            } else {
                                //                        Integer userid = SessionBean.getSessionID();
//                        Object userobj = (Integer) userid;
//                        HrEmployees hrEmployees = new HrEmployees();
//                        hrEmployees = (HrEmployees) userobj;
////                    hrInsurancePayment.setPreparedBy(String.valueOf(SessionBean.getUserId()));
//                        hrInsurancePayment.setPrepardby(hrEmployees);
                                hrInsuranceDiagnosisResult.setStatus(1);
                                hrInsurancePayment.setStatus(Constants.PREPARE_VALUE);
                                hrInsuranceDiagonasticResultBeanLocal.saveOrUpdate(hrInsuranceDiagnosisResult);
                                hrinsurancePaymentLocal.saveorupdate(hrInsurancePayment);
                                wfHrProcessed.setInsurancePaymentId(hrInsurancePayment);
                                wfHrProcessed.setProcessedOn(hrInsurancePayment.getPreparddate());
                                wfHrProcessed.setProcessedBy(SessionBean.getUserId());
                                wfHrProcessed.setCommentGiven(hrInsurancePayment.getRemark());
                                wfHrProcessed.setDecision(hrInsurancePayment.getStatus());
                                wfHrProcessedFacade.saveOrUpdate(wfHrProcessed);
                                JsfUtil.addSuccessMessage("Updated updated");
                                resetInsurancePayment();
                            }
                            
                        }
                        
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        JsfUtil.addFatalMessage("Unable to Save data");
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
                security.addEventLog(eventEntry, dataset);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
    }
    
    public BigDecimal getSalarypaidd() {
        return salarypaidd;
    }
    
    public void setSalarypaidd(BigDecimal salarypaidd) {
        this.salarypaidd = salarypaidd;
    }
    
    public BigDecimal getAmountpaid() {
        return amountpaid;
    }
    
    public void setAmountpaid(BigDecimal amountpaid) {
        this.amountpaid = amountpaid;
    }
    
    public BigDecimal getCompensation() {
        return compensation;
    }
    
    public void setCompensation(BigDecimal compensation) {
        this.compensation = compensation;
    }
    
    public BigDecimal getCorporation() {
        return corporation;
    }
    
    public void setCorporation(BigDecimal corporation) {
        this.corporation = corporation;
    }
    
    public String getAddorUpdate() {
        return addorUpdate;
    }
    
    public void setAddorUpdate(String addorUpdate) {
        this.addorUpdate = addorUpdate;
    }
    Status status = new Status();
    
    public void filiterByStatus_VclInsurancePayment(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            selectedstatus = Integer.valueOf(event.getNewValue().toString());
            if (selectedstatus == Constants.PREPARE_VALUE) {
                status.setStatus1(Constants.PREPARE_VALUE);
                populateTable(status);
            } else if (selectedstatus == Constants.APPROVE_VALUE) {
                status.setStatus1(Constants.APPROVE_VALUE);
                status.setStatus2(Constants.CHECK_APPROVE_VALUE);
                populateTableApprove(status);
            } else if (selectedstatus == Constants.APPROVE_REJECT_VALUE) {
                status.setStatus2(Constants.CHECK_REJECT_VALUE);
                status.setStatus3(Constants.APPROVE_REJECT_VALUE);
                populateTableReject(status);
            }
            
        }
    }
    
    public void filiterByStatus_VclInsurancePayment1(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            selectedstatus = Integer.valueOf(event.getNewValue().toString());
            populateTable();
        }
    }
    
    public SelectItem[] getPopulateFilterByStatus() {
        SelectItem[] items = new SelectItem[4];
        items[0] = new SelectItem("null", "--- Select ---");
        items[1] = new SelectItem(Constants.PREPARE_VALUE, "Load Request List");
        items[2] = new SelectItem(Constants.APPROVE_REJECT_VALUE, "Load Rejected List");
        items[3] = new SelectItem(Constants.APPROVE_VALUE, "Load Approved List");
        return items;
    }
    
    private void populateTable(Status status1) {
        try {
            workFlow = new WorkFlow();
            List<HrInsurancePayment> readFiltereddata = hrinsurancePaymentLocal.loadFiltereddata(status1, SessionBean.getUserId());
            inpaymentrequestDataModel = new ListDataModel(readFiltereddata);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    private void populateTableReject(Status status1) {
        try {
            workFlow = new WorkFlow();
            List<HrInsurancePayment> readFiltereddata = hrinsurancePaymentLocal.populateTableReject(status1, SessionBean.getUserId());
            inpaymentrequestDataModel = new ListDataModel(readFiltereddata);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    private void populateTableApprove(Status status1) {
        try {
            workFlow = new WorkFlow();
            List<HrInsurancePayment> readFiltereddata = hrinsurancePaymentLocal.populateTableApprove(status1, SessionBean.getUserId());
            inpaymentrequestDataModel = new ListDataModel(readFiltereddata);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    private void populateTable() {
        try {
            workFlow = new WorkFlow();
            List<HrInsurancePayment> readFiltereddata = hrinsurancePaymentLocal.loadFiltereddata(selectedstatus);
            inpaymentrequestDataModel = new ListDataModel(readFiltereddata);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public void editDataTable() {
        hrInsurancePaymentDetail = hrInsurancePaymentDetailmodel.getRowData();
        hrInsurancePayment = hrInsurancePaymentDetail.getPaymentRequestId();
        addorUpdate = "Add Changes";
    }
    
    public void addMedicalDetail() {
        hrInsurpaymentList.add(hrInsurancePayment);
        hrInsurancePayment = new HrInsurancePayment();
        
    }
    BigDecimal medicalexpence = new BigDecimal(0.00);
    
    public BigDecimal getMedicalexpence() {
        return medicalexpence;
    }
    
    public void setMedicalexpence(BigDecimal medicalexpence) {
        this.medicalexpence = medicalexpence;
    }
    
    public void calculateDailyLabour() {
        medicalexpence = new BigDecimal(0.0);
        for (HrInsurancePaymentDetail hrInsurancePaymentDetail : hrInsurancePayment.getHrInsurancePaymentDetailList()) {
            medicalexpence = medicalexpence.add(hrInsurancePaymentDetail.getMedicalExpence());
            medicalexpence = medicalexpence.setScale(2, BigDecimal.ROUND_HALF_EVEN);
            medicalexpence = medicalexpence;
            BigDecimal a = hrInsuranceInjuredEmployee.getDailyWage();
            BigDecimal b = hrInsuranceDiagnosisResult.getSickLeave();
            if ((hrInsuranceInjuredEmployee.getAccidentHappened().equalsIgnoreCase("OnDuty"))
                    && hrInsuranceInjuredEmployee.getType().equalsIgnoreCase("Daily Labour")
                    && hrInsuranceDiagnosisResult.getIsDisease().equals(BigDecimal.valueOf(100))) {
                amountpaid = BigDecimal.valueOf(30000);
                compensation = BigDecimal.valueOf(30000);
                corporation = BigDecimal.valueOf(0);
                salarypaidd = BigDecimal.valueOf(0);
            } else if ((hrInsuranceInjuredEmployee.getAccidentHappened()
                    .equalsIgnoreCase("OnDuty"))
                    && hrInsuranceInjuredEmployee.getType().equalsIgnoreCase("Daily Labour")
                    && (hrInsuranceDiagnosisResult.getIsDisease() != null || hrInsuranceDiagnosisResult.getIsDisease() != BigDecimal.valueOf(0))
                    && (hrInsuranceDiagnosisResult.getSickLeave().equals(null) || hrInsuranceDiagnosisResult.getSickLeave().equals(BigDecimal.valueOf(0)))) {
                amountpaid = (BigDecimal.valueOf(30000).multiply(hrInsuranceDiagnosisResult.getIsDisease()).divide(BigDecimal.valueOf(100)).add(medicalexpence).add(salarypaidd));
                corporation = BigDecimal.valueOf(0);
                compensation = (BigDecimal.valueOf(30000).multiply(hrInsuranceDiagnosisResult.getIsDisease()).divide(BigDecimal.valueOf(100)));
                salarypaidd = BigDecimal.valueOf(0);
                
            } else if (hrInsuranceInjuredEmployee.getAccidentHappened()
                    .equalsIgnoreCase("OnDuty")
                    && hrInsuranceInjuredEmployee.getType().equalsIgnoreCase("Daily Labour")
                    && (hrInsuranceDiagnosisResult.getIsDisease() == null || hrInsuranceDiagnosisResult.getIsDisease() == BigDecimal.valueOf(0))
                    && (hrInsuranceDiagnosisResult.getSickLeave() != null || hrInsuranceDiagnosisResult.getSickLeave() != BigDecimal.valueOf(0))) {
                salarypaidd = ((a.multiply(b)).divide(new BigDecimal(30), 2, RoundingMode.HALF_EVEN));
                corporation = BigDecimal.valueOf(0);
                compensation = BigDecimal.valueOf(0);
                amountpaid = salarypaidd.add(medicalexpence).add(compensation);
            } else if (hrInsuranceInjuredEmployee.getAccidentHappened()
                    .equalsIgnoreCase("OnDuty")
                    && hrInsuranceInjuredEmployee.getType().equalsIgnoreCase("Daily Labour")
                    && (hrInsuranceDiagnosisResult.getIsDisease() != null || hrInsuranceDiagnosisResult.getIsDisease() != BigDecimal.valueOf(0))
                    && (hrInsuranceDiagnosisResult.getSickLeave() != null || hrInsuranceDiagnosisResult.getSickLeave() != BigDecimal.valueOf(0))) {
                
                BigDecimal TTD = ((a.multiply(b)).divide(new BigDecimal(30), 2, RoundingMode.HALF_EVEN));
                BigDecimal PTD = BigDecimal.valueOf(30000).multiply(hrInsuranceDiagnosisResult.getIsDisease()).divide(BigDecimal.valueOf(100));
                
                if (TTD.compareTo(PTD) > 0) {
                    salarypaidd = ((a.multiply(b)).divide(new BigDecimal(30), 2, RoundingMode.HALF_EVEN));
                    corporation = TTD.subtract(PTD);
                    compensation = PTD;
                    amountpaid = salarypaidd.add(medicalexpence).add(PTD);
                } else {
                    salarypaidd = ((a.multiply(b)).divide(new BigDecimal(30), 2, RoundingMode.HALF_EVEN));
                    corporation = BigDecimal.valueOf(0);
                    compensation = PTD;
                    amountpaid = salarypaidd.add(medicalexpence).add(PTD);
                }
            } else {
                JsfUtil.addFatalMessage("accident must be onduty");
            }
        }
        
    }
    
    public void calculatingMedicalExpence() {
        medicalexpence = new BigDecimal(0.0);
        for (HrInsurancePaymentDetail hrInsurancePaymentDetail : hrInsurancePayment.getHrInsurancePaymentDetailList()) {
            medicalexpence = medicalexpence.add(hrInsurancePaymentDetail.getMedicalExpence());
            medicalexpence = medicalexpence.setScale(2, BigDecimal.ROUND_HALF_EVEN);
            medicalexpence = medicalexpence;
            BigDecimal a = hrInsuranceInjuredEmployee.getDailyWage();
            BigDecimal b = hrInsuranceDiagnosisResult.getSickLeave();
            if ((hrInsuranceInjuredEmployee.getAccidentHappened().equalsIgnoreCase("OnDuty")
                    || hrInsuranceInjuredEmployee.getAccidentHappened().equalsIgnoreCase("OffDuty"))
                    && (hrEmployees.getEmploymentType().equalsIgnoreCase("Permanent")
                    || hrEmployees.getEmploymentType().equalsIgnoreCase("Contract"))
                    && hrInsuranceDiagnosisResult.getIsDisease().equals(BigDecimal.valueOf(100))
                    && hrEmployees.getBasicSalary() >= 2051) {
                if (hrEmployees.getBasicSalary() == null) {
                    JsfUtil.addFatalMessage("Can Not Find Basic salary");
                } else {
                    amountpaid = BigDecimal.valueOf(150000);
                    compensation = BigDecimal.valueOf(150000);
                    corporation = BigDecimal.valueOf(0);
                    salarypaidd = BigDecimal.valueOf(0);
                }
                
            } else if ((hrInsuranceInjuredEmployee.getAccidentHappened().equalsIgnoreCase("OnDuty")
                    || hrInsuranceInjuredEmployee.getAccidentHappened().equalsIgnoreCase("OffDuty"))
                    && (hrEmployees.getEmploymentType().equalsIgnoreCase("Permanent")
                    || hrEmployees.getEmploymentType().equalsIgnoreCase("Contract"))
                    && hrInsuranceDiagnosisResult.getIsDisease().equals(BigDecimal.valueOf(100))
                    && hrEmployees.getBasicSalary() < 2051) {
                if (hrEmployees.getBasicSalary() == null) {
                    JsfUtil.addFatalMessage("Can Not Find Basic salary");
                } else {
                    amountpaid = BigDecimal.valueOf(hrEmployees.getBasicSalary() * 60);
                    compensation = BigDecimal.valueOf(hrEmployees.getBasicSalary() * 60);
                    corporation = BigDecimal.valueOf(0);
                    salarypaidd = BigDecimal.valueOf(0);
                }
            } else if ((hrInsuranceInjuredEmployee.getAccidentHappened().equalsIgnoreCase("OnDuty")
                    || hrInsuranceInjuredEmployee.getAccidentHappened().equalsIgnoreCase("OffDuty"))
                    && hrEmployees.getEmploymentType().equalsIgnoreCase("Permanent")
                    && (hrInsuranceDiagnosisResult.getSickLeave() != BigDecimal.valueOf(0) || hrInsuranceDiagnosisResult.getSickLeave() != null)
                    && hrInsuranceDiagnosisResult.getIsDisease() == null && offduty.equalsIgnoreCase("1")
                    && hrEmployees.getBasicSalary() < 333.33) {
                salarypaidd = (((BigDecimal.valueOf(hrEmployees.getBasicSalary())).multiply(hrInsuranceDiagnosisResult.getSickLeave())).divide(BigDecimal.valueOf(30), 2, RoundingMode.HALF_EVEN));
                corporation = BigDecimal.valueOf(0);
                compensation = BigDecimal.valueOf(0);
                amountpaid = salarypaidd.add(medicalexpence).add(compensation);
            } else if ((hrInsuranceInjuredEmployee.getAccidentHappened().equalsIgnoreCase("OnDuty")
                    || hrInsuranceInjuredEmployee.getAccidentHappened().equalsIgnoreCase("OffDuty"))
                    && hrEmployees.getEmploymentType().equalsIgnoreCase("Permanent")
                    && (hrInsuranceDiagnosisResult.getIsDisease() != BigDecimal.valueOf(0) || hrInsuranceDiagnosisResult.getIsDisease() != null)
                    && hrInsuranceDiagnosisResult.getSickLeave() == null && offduty.equalsIgnoreCase("1")
                    && hrEmployees.getBasicSalary() < 333.33) {
                corporation = BigDecimal.valueOf(0);
                compensation = BigDecimal.valueOf(20000).multiply(hrInsuranceDiagnosisResult.getIsDisease());
                salarypaidd = BigDecimal.valueOf(0);
                corporation = BigDecimal.valueOf(0);
                amountpaid = (BigDecimal.valueOf(20000).multiply(hrInsuranceDiagnosisResult.getIsDisease()).add(medicalexpence).add(salarypaidd));
            } else if ((hrInsuranceInjuredEmployee.getAccidentHappened().equalsIgnoreCase("OnDuty")
                    || hrInsuranceInjuredEmployee.getAccidentHappened().equalsIgnoreCase("OffDuty"))
                    && hrEmployees.getEmploymentType().equalsIgnoreCase("Permanent")
                    && (hrInsuranceDiagnosisResult.getIsDisease() != BigDecimal.valueOf(0) || hrInsuranceDiagnosisResult.getIsDisease() != null)
                    && (hrInsuranceDiagnosisResult.getSickLeave() != BigDecimal.valueOf(0) || hrInsuranceDiagnosisResult.getSickLeave() != null)
                    && offduty.equalsIgnoreCase("1")
                    && hrEmployees.getBasicSalary() < 333.33) {
                BigDecimal PTD = BigDecimal.valueOf(20000).multiply(hrInsuranceDiagnosisResult.getIsDisease());
                BigDecimal TTD = (((BigDecimal.valueOf(hrEmployees.getBasicSalary())).multiply(hrInsuranceDiagnosisResult.getSickLeave())).divide(BigDecimal.valueOf(30), 2, RoundingMode.HALF_EVEN));
                if (TTD.compareTo(PTD) > 0) {
                    salarypaidd = TTD;
                    compensation = PTD;
                    corporation = TTD.subtract(PTD);
                    amountpaid = TTD.add(medicalexpence).add(PTD);
                } else {
                    salarypaidd = TTD;
                    compensation = PTD;
                    corporation = BigDecimal.valueOf(0);
                    amountpaid = TTD.add(medicalexpence).add(PTD);
                }
            } else if ((hrInsuranceInjuredEmployee.getAccidentHappened()
                    .equalsIgnoreCase("OnDuty")
                    || hrInsuranceInjuredEmployee.getAccidentHappened().equalsIgnoreCase("OffDuty"))
                    && hrEmployees.getEmploymentType().equalsIgnoreCase("Permanent")
                    && (hrInsuranceDiagnosisResult.getIsDisease() != BigDecimal.valueOf(0) || hrInsuranceDiagnosisResult.getIsDisease() != null)
                    && (hrInsuranceDiagnosisResult.getSickLeave() == BigDecimal.valueOf(0) || hrInsuranceDiagnosisResult.getSickLeave() == null)
                    && offduty.equalsIgnoreCase("1")
                    && hrEmployees.getBasicSalary() >= 333.33 && hrEmployees.getBasicSalary() <= 2500) {
                corporation = BigDecimal.valueOf(0);
                compensation = BigDecimal.valueOf(hrEmployees.getBasicSalary()).multiply(BigDecimal.valueOf(60)).multiply(hrInsuranceDiagnosisResult.getIsDisease()).divide(BigDecimal.valueOf(100));
                salarypaidd = BigDecimal.valueOf(0);
                amountpaid = (BigDecimal.valueOf(hrEmployees.getBasicSalary()).multiply(BigDecimal.valueOf(60)).multiply(hrInsuranceDiagnosisResult.getIsDisease()).divide(BigDecimal.valueOf(100)).add(medicalexpence).add(salarypaidd));
            } else if ((hrInsuranceInjuredEmployee.getAccidentHappened()
                    .equalsIgnoreCase("OnDuty")
                    || hrInsuranceInjuredEmployee.getAccidentHappened().equalsIgnoreCase("OffDuty"))
                    && hrEmployees.getEmploymentType().equalsIgnoreCase("Permanent")
                    && (hrInsuranceDiagnosisResult.getIsDisease() == BigDecimal.valueOf(0) || hrInsuranceDiagnosisResult.getIsDisease() == null)
                    && (hrInsuranceDiagnosisResult.getSickLeave() != BigDecimal.valueOf(0) || hrInsuranceDiagnosisResult.getSickLeave() != null)
                    && offduty.equalsIgnoreCase("1")
                    && hrEmployees.getBasicSalary() >= 333.33 && hrEmployees.getBasicSalary() <= 2500) {
                salarypaidd = (BigDecimal.valueOf(hrEmployees.getBasicSalary()).multiply(BigDecimal.valueOf(60)).multiply(hrInsuranceDiagnosisResult.getIsDisease()).divide(BigDecimal.valueOf(100)));
                corporation = BigDecimal.valueOf(0);
                compensation = BigDecimal.valueOf(0);
                amountpaid = salarypaidd.add(medicalexpence).add(compensation);
            } else if ((hrInsuranceInjuredEmployee.getAccidentHappened()
                    .equalsIgnoreCase("OnDuty")
                    || hrInsuranceInjuredEmployee.getAccidentHappened().equalsIgnoreCase("OffDuty"))
                    && hrEmployees.getEmploymentType().equalsIgnoreCase("Permanent")
                    && (hrInsuranceDiagnosisResult.getIsDisease() != BigDecimal.valueOf(0) || hrInsuranceDiagnosisResult.getIsDisease() != null)
                    && (hrInsuranceDiagnosisResult.getSickLeave() != BigDecimal.valueOf(0) || hrInsuranceDiagnosisResult.getSickLeave() != null)
                    && offduty.equalsIgnoreCase("1")
                    && hrEmployees.getBasicSalary() >= 333.33 && hrEmployees.getBasicSalary() <= 2500) {
                BigDecimal PTD = BigDecimal.valueOf(hrEmployees.getBasicSalary()).multiply(BigDecimal.valueOf(60)).multiply(hrInsuranceDiagnosisResult.getIsDisease()).divide(BigDecimal.valueOf(100));
                BigDecimal TTD = (BigDecimal.valueOf(hrEmployees.getBasicSalary()).multiply(BigDecimal.valueOf(60)).multiply(hrInsuranceDiagnosisResult.getIsDisease()).divide(BigDecimal.valueOf(100)));
                if (TTD.compareTo(PTD) > 0) {
                    salarypaidd = TTD;
                    compensation = PTD;
                    corporation = TTD.subtract(PTD);
                    amountpaid = PTD.add(medicalexpence).add(TTD);
                } else {
                    salarypaidd = TTD;
                    compensation = PTD;
                    corporation = BigDecimal.valueOf(0);
                    amountpaid = PTD.add(medicalexpence).add(TTD);
                }
            } else if ((hrInsuranceInjuredEmployee.getAccidentHappened()
                    .equalsIgnoreCase("OnDuty")
                    || hrInsuranceInjuredEmployee.getAccidentHappened().equalsIgnoreCase("OffDuty"))
                    && hrEmployees.getEmploymentType().equalsIgnoreCase("Permanent")
                    && (hrInsuranceDiagnosisResult.getIsDisease() == BigDecimal.valueOf(0) || hrInsuranceDiagnosisResult.getIsDisease() == null)
                    && (hrInsuranceDiagnosisResult.getSickLeave() != BigDecimal.valueOf(0) || hrInsuranceDiagnosisResult.getSickLeave() != null)
                    && offduty.equalsIgnoreCase("1")
                    && hrEmployees.getBasicSalary() >= 2501) {
                salarypaidd = BigDecimal.valueOf(hrEmployees.getBasicSalary()).multiply(hrInsuranceDiagnosisResult.getSickLeave()).divide(BigDecimal.valueOf(30), 2, RoundingMode.HALF_EVEN);
                corporation = BigDecimal.valueOf(0);
                compensation = BigDecimal.valueOf(0);
                amountpaid = salarypaidd.add(medicalexpence).add(compensation);
                
            } else if ((hrInsuranceInjuredEmployee.getAccidentHappened()
                    .equalsIgnoreCase("OnDuty")
                    || hrInsuranceInjuredEmployee.getAccidentHappened().equalsIgnoreCase("OffDuty"))
                    && hrEmployees.getEmploymentType().equalsIgnoreCase("Permanent")
                    && (hrInsuranceDiagnosisResult.getIsDisease() != BigDecimal.valueOf(0) || hrInsuranceDiagnosisResult.getIsDisease() != null)
                    && (hrInsuranceDiagnosisResult.getSickLeave() == BigDecimal.valueOf(0) || hrInsuranceDiagnosisResult.getSickLeave() == null)
                    && offduty.equalsIgnoreCase("1")
                    && hrEmployees.getBasicSalary() >= 2501) {
                corporation = BigDecimal.valueOf(0);
                compensation = BigDecimal.valueOf(150000).multiply(hrInsuranceDiagnosisResult.getIsDisease());
                salarypaidd = BigDecimal.valueOf(0);
                amountpaid = (BigDecimal.valueOf(150000).multiply(hrInsuranceDiagnosisResult.getIsDisease()).add(medicalexpence).add(salarypaidd));
            } else if ((hrInsuranceInjuredEmployee.getAccidentHappened()
                    .equalsIgnoreCase("OnDuty")
                    || hrInsuranceInjuredEmployee.getAccidentHappened().equalsIgnoreCase("OffDuty"))
                    && hrEmployees.getEmploymentType().equalsIgnoreCase("Permanent")
                    && (hrInsuranceDiagnosisResult.getIsDisease() != BigDecimal.valueOf(0) || hrInsuranceDiagnosisResult.getIsDisease() != null)
                    && (hrInsuranceDiagnosisResult.getSickLeave() != BigDecimal.valueOf(0) || hrInsuranceDiagnosisResult.getSickLeave() != null)
                    && offduty.equalsIgnoreCase("1")
                    && hrEmployees.getBasicSalary() >= 2501) {
                BigDecimal TTD = BigDecimal.valueOf(hrEmployees.getBasicSalary()).multiply(hrInsuranceDiagnosisResult.getSickLeave()).divide(BigDecimal.valueOf(30), 2, RoundingMode.HALF_EVEN);
                BigDecimal PTD = BigDecimal.valueOf(150000).multiply(hrInsuranceDiagnosisResult.getIsDisease());
                if (TTD.compareTo(PTD) > 0) {
                    salarypaidd = TTD;
                    compensation = PTD;
                    corporation = TTD.subtract(PTD);
                    amountpaid = salarypaidd.add(medicalexpence).add(compensation);
                } else {
                    salarypaidd = TTD;
                    compensation = PTD;
                    corporation = BigDecimal.valueOf(0);
                    amountpaid = salarypaidd.add(medicalexpence).add(compensation);
                }
                
            } else if (hrInsuranceInjuredEmployee.getAccidentHappened().equalsIgnoreCase("OnDuty")
                    && hrEmployees.getEmploymentType().equalsIgnoreCase("Contract")
                    && (hrInsuranceDiagnosisResult.getSickLeave() != BigDecimal.valueOf(0) || hrInsuranceDiagnosisResult.getSickLeave() != null)
                    && hrInsuranceDiagnosisResult.getIsDisease() == null
                    && hrEmployees.getBasicSalary() < 333.33) {
                salarypaidd = (((BigDecimal.valueOf(hrEmployees.getBasicSalary())).multiply(hrInsuranceDiagnosisResult.getSickLeave())).divide(BigDecimal.valueOf(30), 2, RoundingMode.HALF_EVEN));
                corporation = BigDecimal.valueOf(0);
                compensation = BigDecimal.valueOf(0);
                amountpaid = salarypaidd.add(medicalexpence).add(compensation);
            } else if (hrInsuranceInjuredEmployee.getAccidentHappened().equalsIgnoreCase("OnDuty")
                    && hrEmployees.getEmploymentType().equalsIgnoreCase("Contract")
                    && (hrInsuranceDiagnosisResult.getIsDisease() != BigDecimal.valueOf(0) || hrInsuranceDiagnosisResult.getIsDisease() != null)
                    && hrInsuranceDiagnosisResult.getSickLeave() == null
                    && hrEmployees.getBasicSalary() < 333.33) {
                corporation = BigDecimal.valueOf(0);
                compensation = BigDecimal.valueOf(20000).multiply(hrInsuranceDiagnosisResult.getIsDisease());
                salarypaidd = BigDecimal.valueOf(0);
                corporation = BigDecimal.valueOf(0);
                amountpaid = (BigDecimal.valueOf(20000).multiply(hrInsuranceDiagnosisResult.getIsDisease()).add(medicalexpence).add(salarypaidd));
            } else if (hrInsuranceInjuredEmployee.getAccidentHappened().equalsIgnoreCase("OnDuty")
                    && hrEmployees.getEmploymentType().equalsIgnoreCase("Contract")
                    && (hrInsuranceDiagnosisResult.getIsDisease() != BigDecimal.valueOf(0) || hrInsuranceDiagnosisResult.getIsDisease() != null)
                    && (hrInsuranceDiagnosisResult.getSickLeave() != BigDecimal.valueOf(0) || hrInsuranceDiagnosisResult.getSickLeave() != null)
                    && hrEmployees.getBasicSalary() < 333.33) {
                BigDecimal PTD = BigDecimal.valueOf(20000).multiply(hrInsuranceDiagnosisResult.getIsDisease());
                BigDecimal TTD = (((BigDecimal.valueOf(hrEmployees.getBasicSalary())).multiply(hrInsuranceDiagnosisResult.getSickLeave())).divide(BigDecimal.valueOf(30), 2, RoundingMode.HALF_EVEN));
                if (TTD.compareTo(PTD) > 0) {
                    salarypaidd = TTD;
                    compensation = PTD;
                    corporation = TTD.subtract(PTD);
                    amountpaid = TTD.add(medicalexpence).add(PTD);
                } else {
                    salarypaidd = TTD;
                    compensation = PTD;
                    corporation = BigDecimal.valueOf(0);
                    amountpaid = TTD.add(medicalexpence).add(PTD);
                }
            } else if (hrInsuranceInjuredEmployee.getAccidentHappened()
                    .equalsIgnoreCase("OnDuty")
                    && hrEmployees.getEmploymentType().equalsIgnoreCase("Contract")
                    && (hrInsuranceDiagnosisResult.getIsDisease() != BigDecimal.valueOf(0) || hrInsuranceDiagnosisResult.getIsDisease() != null)
                    && (hrInsuranceDiagnosisResult.getSickLeave() == BigDecimal.valueOf(0) || hrInsuranceDiagnosisResult.getSickLeave() == null)
                    && hrEmployees.getBasicSalary() >= 333.33 && hrEmployees.getBasicSalary() <= 2500) {
                corporation = BigDecimal.valueOf(0);
                compensation = BigDecimal.valueOf(hrEmployees.getBasicSalary()).multiply(BigDecimal.valueOf(60)).multiply(hrInsuranceDiagnosisResult.getIsDisease()).divide(BigDecimal.valueOf(100));
                salarypaidd = BigDecimal.valueOf(0);
                amountpaid = (BigDecimal.valueOf(hrEmployees.getBasicSalary()).multiply(BigDecimal.valueOf(60)).multiply(hrInsuranceDiagnosisResult.getIsDisease()).divide(BigDecimal.valueOf(100)).add(medicalexpence).add(salarypaidd));
            } else if (hrInsuranceInjuredEmployee.getAccidentHappened()
                    .equalsIgnoreCase("OnDuty")
                    && hrEmployees.getEmploymentType().equalsIgnoreCase("Contract")
                    && (hrInsuranceDiagnosisResult.getIsDisease() == BigDecimal.valueOf(0) || hrInsuranceDiagnosisResult.getIsDisease() == null)
                    && (hrInsuranceDiagnosisResult.getSickLeave() != BigDecimal.valueOf(0) || hrInsuranceDiagnosisResult.getSickLeave() != null)
                    && hrEmployees.getBasicSalary() >= 333.33 && hrEmployees.getBasicSalary() <= 2500) {
                salarypaidd = (BigDecimal.valueOf(hrEmployees.getBasicSalary()).multiply(BigDecimal.valueOf(60)).multiply(hrInsuranceDiagnosisResult.getIsDisease()).divide(BigDecimal.valueOf(100)));
                corporation = BigDecimal.valueOf(0);
                compensation = BigDecimal.valueOf(0);
                amountpaid = salarypaidd.add(medicalexpence).add(compensation);
            } else if (hrInsuranceInjuredEmployee.getAccidentHappened()
                    .equalsIgnoreCase("OnDuty")
                    && hrEmployees.getEmploymentType().equalsIgnoreCase("Contract")
                    && (hrInsuranceDiagnosisResult.getIsDisease() != BigDecimal.valueOf(0) || hrInsuranceDiagnosisResult.getIsDisease() != null)
                    && (hrInsuranceDiagnosisResult.getSickLeave() != BigDecimal.valueOf(0) || hrInsuranceDiagnosisResult.getSickLeave() != null)
                    && hrEmployees.getBasicSalary() >= 333.33 && hrEmployees.getBasicSalary() <= 2500) {
                BigDecimal PTD = BigDecimal.valueOf(hrEmployees.getBasicSalary()).multiply(BigDecimal.valueOf(60)).multiply(hrInsuranceDiagnosisResult.getIsDisease()).divide(BigDecimal.valueOf(100));
                BigDecimal TTD = (BigDecimal.valueOf(hrEmployees.getBasicSalary()).multiply(BigDecimal.valueOf(60)).multiply(hrInsuranceDiagnosisResult.getIsDisease()).divide(BigDecimal.valueOf(100)));
                if (TTD.compareTo(PTD) > 0) {
                    salarypaidd = TTD;
                    compensation = PTD;
                    corporation = TTD.subtract(PTD);
                    amountpaid = PTD.add(medicalexpence).add(TTD);
                } else {
                    salarypaidd = TTD;
                    compensation = PTD;
                    corporation = BigDecimal.valueOf(0);
                    amountpaid = PTD.add(medicalexpence).add(TTD);
                }
            } else if (hrInsuranceInjuredEmployee.getAccidentHappened()
                    .equalsIgnoreCase("OnDuty")
                    && hrEmployees.getEmploymentType().equalsIgnoreCase("Contract")
                    && (hrInsuranceDiagnosisResult.getIsDisease() == BigDecimal.valueOf(0) || hrInsuranceDiagnosisResult.getIsDisease() == null)
                    && (hrInsuranceDiagnosisResult.getSickLeave() != BigDecimal.valueOf(0) || hrInsuranceDiagnosisResult.getSickLeave() != null)
                    && hrEmployees.getBasicSalary() >= 2501) {
                salarypaidd = BigDecimal.valueOf(hrEmployees.getBasicSalary()).multiply(hrInsuranceDiagnosisResult.getSickLeave()).divide(BigDecimal.valueOf(30), 2, RoundingMode.HALF_EVEN);
                corporation = BigDecimal.valueOf(0);
                compensation = BigDecimal.valueOf(0);
                amountpaid = salarypaidd.add(medicalexpence).add(compensation);
                
            } else if (hrInsuranceInjuredEmployee.getAccidentHappened()
                    .equalsIgnoreCase("OnDuty")
                    && hrEmployees.getEmploymentType().equalsIgnoreCase("Contract")
                    && (hrInsuranceDiagnosisResult.getIsDisease() != BigDecimal.valueOf(0) || hrInsuranceDiagnosisResult.getIsDisease() != null)
                    && (hrInsuranceDiagnosisResult.getSickLeave() == BigDecimal.valueOf(0) || hrInsuranceDiagnosisResult.getSickLeave() == null)
                    && hrEmployees.getBasicSalary() >= 2501) {
                corporation = BigDecimal.valueOf(0);
                compensation = BigDecimal.valueOf(150000).multiply(hrInsuranceDiagnosisResult.getIsDisease());
                salarypaidd = BigDecimal.valueOf(0);
                amountpaid = (BigDecimal.valueOf(150000).multiply(hrInsuranceDiagnosisResult.getIsDisease()).add(medicalexpence).add(salarypaidd));
                
            } else if (hrInsuranceInjuredEmployee.getAccidentHappened()
                    .equalsIgnoreCase("OnDuty")
                    && hrEmployees.getEmploymentType().equalsIgnoreCase("Contract")
                    && (hrInsuranceDiagnosisResult.getIsDisease() != BigDecimal.valueOf(0) || hrInsuranceDiagnosisResult.getIsDisease() != null)
                    && (hrInsuranceDiagnosisResult.getSickLeave() != BigDecimal.valueOf(0) || hrInsuranceDiagnosisResult.getSickLeave() != null)
                    && hrEmployees.getBasicSalary() >= 2501) {
                compensation = BigDecimal.valueOf(150000).multiply(hrInsuranceDiagnosisResult.getIsDisease());
                salarypaidd = BigDecimal.valueOf(hrEmployees.getBasicSalary()).multiply(hrInsuranceDiagnosisResult.getSickLeave()).divide(BigDecimal.valueOf(30), 2, RoundingMode.HALF_EVEN);
                amountpaid = (BigDecimal.valueOf(150000).multiply(hrInsuranceDiagnosisResult.getIsDisease()).add(salarypaidd).add(medicalexpence));
                if (salarypaidd.compareTo(compensation) > 0) {
                    corporation = salarypaidd.subtract(compensation);
                } else {
                    corporation = BigDecimal.valueOf(0);
                }
                
            } else if (hrInsuranceInjuredEmployee.getAccidentHappened().equalsIgnoreCase("OnDuty")
                    && hrEmployees.getEmploymentType().equalsIgnoreCase("Permanent")
                    && (hrInsuranceDiagnosisResult.getSickLeave() != BigDecimal.valueOf(0) || hrInsuranceDiagnosisResult.getSickLeave() != null)
                    && hrInsuranceDiagnosisResult.getIsDisease() == null
                    && hrEmployees.getBasicSalary() < 333.33) {
                salarypaidd = (((BigDecimal.valueOf(hrEmployees.getBasicSalary())).multiply(hrInsuranceDiagnosisResult.getSickLeave())).divide(BigDecimal.valueOf(30), 2, RoundingMode.HALF_EVEN));
                corporation = BigDecimal.valueOf(0);
                compensation = BigDecimal.valueOf(0);
                amountpaid = salarypaidd.add(medicalexpence).add(compensation);
            } else if (hrInsuranceInjuredEmployee.getAccidentHappened().equalsIgnoreCase("OnDuty")
                    && hrEmployees.getEmploymentType().equalsIgnoreCase("Permanent")
                    && (hrInsuranceDiagnosisResult.getIsDisease() != BigDecimal.valueOf(0) || hrInsuranceDiagnosisResult.getIsDisease() != null)
                    && hrInsuranceDiagnosisResult.getSickLeave() == null
                    && hrEmployees.getBasicSalary() < 333.33) {
                corporation = BigDecimal.valueOf(0);
                compensation = BigDecimal.valueOf(20000).multiply(hrInsuranceDiagnosisResult.getIsDisease());
                salarypaidd = BigDecimal.valueOf(0);
                corporation = BigDecimal.valueOf(0);
                amountpaid = (BigDecimal.valueOf(20000).multiply(hrInsuranceDiagnosisResult.getIsDisease()).add(medicalexpence).add(salarypaidd));
            } else if (hrInsuranceInjuredEmployee.getAccidentHappened().equalsIgnoreCase("OnDuty")
                    && hrEmployees.getEmploymentType().equalsIgnoreCase("Permanent")
                    && (hrInsuranceDiagnosisResult.getIsDisease() != BigDecimal.valueOf(0) || hrInsuranceDiagnosisResult.getIsDisease() != null)
                    && (hrInsuranceDiagnosisResult.getSickLeave() != BigDecimal.valueOf(0) || hrInsuranceDiagnosisResult.getSickLeave() != null)
                    && hrEmployees.getBasicSalary() < 333.33) {
                BigDecimal PTD = BigDecimal.valueOf(20000).multiply(hrInsuranceDiagnosisResult.getIsDisease());
                BigDecimal TTD = (((BigDecimal.valueOf(hrEmployees.getBasicSalary())).multiply(hrInsuranceDiagnosisResult.getSickLeave())).divide(BigDecimal.valueOf(30), 2, RoundingMode.HALF_EVEN));
                if (TTD.compareTo(PTD) > 0) {
                    salarypaidd = TTD;
                    compensation = PTD;
                    corporation = TTD.subtract(PTD);
                    amountpaid = TTD.add(medicalexpence).add(PTD);
                } else {
                    salarypaidd = TTD;
                    compensation = PTD;
                    corporation = BigDecimal.valueOf(0);
                    amountpaid = TTD.add(medicalexpence).add(PTD);
                }
            } else if (hrInsuranceInjuredEmployee.getAccidentHappened()
                    .equalsIgnoreCase("OnDuty")
                    && hrEmployees.getEmploymentType().equalsIgnoreCase("Permanent")
                    && (hrInsuranceDiagnosisResult.getIsDisease() != BigDecimal.valueOf(0) || hrInsuranceDiagnosisResult.getIsDisease() != null)
                    && (hrInsuranceDiagnosisResult.getSickLeave() == BigDecimal.valueOf(0) || hrInsuranceDiagnosisResult.getSickLeave() == null)
                    && hrEmployees.getBasicSalary() >= 333.33 && hrEmployees.getBasicSalary() <= 2500) {
                corporation = BigDecimal.valueOf(0);
                compensation = BigDecimal.valueOf(hrEmployees.getBasicSalary()).multiply(BigDecimal.valueOf(60)).multiply(hrInsuranceDiagnosisResult.getIsDisease()).divide(BigDecimal.valueOf(100));
                salarypaidd = BigDecimal.valueOf(0);
                amountpaid = (BigDecimal.valueOf(hrEmployees.getBasicSalary()).multiply(BigDecimal.valueOf(60)).multiply(hrInsuranceDiagnosisResult.getIsDisease()).divide(BigDecimal.valueOf(100)).add(medicalexpence).add(salarypaidd));
            } else if (hrInsuranceInjuredEmployee.getAccidentHappened()
                    .equalsIgnoreCase("OnDuty")
                    && hrEmployees.getEmploymentType().equalsIgnoreCase("Permanent")
                    && (hrInsuranceDiagnosisResult.getIsDisease() == BigDecimal.valueOf(0) || hrInsuranceDiagnosisResult.getIsDisease() == null)
                    && (hrInsuranceDiagnosisResult.getSickLeave() != BigDecimal.valueOf(0) || hrInsuranceDiagnosisResult.getSickLeave() != null)
                    && hrEmployees.getBasicSalary() >= 333.33 && hrEmployees.getBasicSalary() <= 2500) {
                salarypaidd = (BigDecimal.valueOf(hrEmployees.getBasicSalary()).multiply(BigDecimal.valueOf(60)).multiply(hrInsuranceDiagnosisResult.getIsDisease()).divide(BigDecimal.valueOf(100)));
                corporation = BigDecimal.valueOf(0);
                compensation = BigDecimal.valueOf(0);
                amountpaid = salarypaidd.add(medicalexpence).add(compensation);
            } else if (hrInsuranceInjuredEmployee.getAccidentHappened()
                    .equalsIgnoreCase("OnDuty")
                    && hrEmployees.getEmploymentType().equalsIgnoreCase("Permanent")
                    && (hrInsuranceDiagnosisResult.getIsDisease() != BigDecimal.valueOf(0) || hrInsuranceDiagnosisResult.getIsDisease() != null)
                    && (hrInsuranceDiagnosisResult.getSickLeave() != BigDecimal.valueOf(0) || hrInsuranceDiagnosisResult.getSickLeave() != null)
                    && hrEmployees.getBasicSalary() >= 333.33 && hrEmployees.getBasicSalary() <= 2500) {
                BigDecimal PTD = BigDecimal.valueOf(hrEmployees.getBasicSalary()).multiply(BigDecimal.valueOf(60)).multiply(hrInsuranceDiagnosisResult.getIsDisease()).divide(BigDecimal.valueOf(100));
                BigDecimal TTD = (BigDecimal.valueOf(hrEmployees.getBasicSalary()).multiply(BigDecimal.valueOf(60)).multiply(hrInsuranceDiagnosisResult.getIsDisease()).divide(BigDecimal.valueOf(100)));
                if (TTD.compareTo(PTD) > 0) {
                    salarypaidd = TTD;
                    compensation = PTD;
                    corporation = TTD.subtract(PTD);
                    amountpaid = PTD.add(medicalexpence).add(TTD);
                } else {
                    salarypaidd = TTD;
                    compensation = PTD;
                    corporation = BigDecimal.valueOf(0);
                    amountpaid = PTD.add(medicalexpence).add(TTD);
                }
            } else if (hrInsuranceInjuredEmployee.getAccidentHappened()
                    .equalsIgnoreCase("OnDuty")
                    && hrEmployees.getEmploymentType().equalsIgnoreCase("Permanent")
                    && (hrInsuranceDiagnosisResult.getIsDisease() == BigDecimal.valueOf(0) || hrInsuranceDiagnosisResult.getIsDisease() == null)
                    && (hrInsuranceDiagnosisResult.getSickLeave() != BigDecimal.valueOf(0) || hrInsuranceDiagnosisResult.getSickLeave() != null)
                    && hrEmployees.getBasicSalary() >= 2501) {
                salarypaidd = BigDecimal.valueOf(hrEmployees.getBasicSalary()).multiply(hrInsuranceDiagnosisResult.getSickLeave()).divide(BigDecimal.valueOf(30), 2, RoundingMode.HALF_EVEN);
                corporation = BigDecimal.valueOf(0);
                compensation = BigDecimal.valueOf(0);
                amountpaid = salarypaidd.add(medicalexpence).add(compensation);
                
            } else if (hrInsuranceInjuredEmployee.getAccidentHappened()
                    .equalsIgnoreCase("OnDuty")
                    && hrEmployees.getEmploymentType().equalsIgnoreCase("Permanent")
                    && (hrInsuranceDiagnosisResult.getIsDisease() != BigDecimal.valueOf(0) || hrInsuranceDiagnosisResult.getIsDisease() != null)
                    && (hrInsuranceDiagnosisResult.getSickLeave() == BigDecimal.valueOf(0) || hrInsuranceDiagnosisResult.getSickLeave() == null)
                    && hrEmployees.getBasicSalary() >= 2501) {
                corporation = BigDecimal.valueOf(0);
                compensation = BigDecimal.valueOf(150000).multiply(hrInsuranceDiagnosisResult.getIsDisease());
                salarypaidd = BigDecimal.valueOf(0);
                amountpaid = (BigDecimal.valueOf(150000).multiply(hrInsuranceDiagnosisResult.getIsDisease()).add(medicalexpence).add(salarypaidd));
            } else if (hrInsuranceInjuredEmployee.getAccidentHappened()
                    .equalsIgnoreCase("OnDuty")
                    && hrEmployees.getEmploymentType().equalsIgnoreCase("Permanent")
                    && (hrInsuranceDiagnosisResult.getIsDisease() != BigDecimal.valueOf(0) || hrInsuranceDiagnosisResult.getIsDisease() != null)
                    && (hrInsuranceDiagnosisResult.getSickLeave() != BigDecimal.valueOf(0) || hrInsuranceDiagnosisResult.getSickLeave() != null)
                    && hrEmployees.getBasicSalary() >= 2501) {
                BigDecimal PTD = (BigDecimal.valueOf(150000).multiply(hrInsuranceDiagnosisResult.getIsDisease()).add(medicalexpence));
                BigDecimal TTD = BigDecimal.valueOf(hrEmployees.getBasicSalary()).multiply(hrInsuranceDiagnosisResult.getSickLeave()).divide(BigDecimal.valueOf(30), 2, RoundingMode.HALF_EVEN);
                if (TTD.compareTo(PTD) > 0) {
                    corporation = TTD.subtract(PTD);
                    compensation = PTD;
                    salarypaidd = TTD;
                    amountpaid = TTD.add(medicalexpence).add(PTD);
                } else {
                    corporation = BigDecimal.valueOf(0);
                    compensation = PTD;
                    salarypaidd = TTD;
                    amountpaid = TTD.add(medicalexpence).add(PTD);
                    
                }
                
            } else {
                hrInsurancePaymentDetailmodel = null;
                JsfUtil.addFatalMessage("Invalid input");
            }
            
        }
        
    }
    String offduty;
    
    public String getOffduty() {
        return offduty;
    }
    
    public void setOffduty(String offduty) {
        this.offduty = offduty;
    }
    
    public void populatediagonasis(SelectEvent events) {
        hrInsuranceDiagnosisResult = null;
        hrInsuranceDiagnosisResult = (HrInsuranceDiagnosisResult) events.getObject();
        hrInsurancePayment.setDiagonasisResultId(hrInsuranceDiagnosisResult);
        if (hrInsuranceDiagnosisResult.getHrInsuranceInjuredEmployee().getType().equalsIgnoreCase("Permanent/contract")) {
            choosePermanet = "true";
            chooseDailyLabour = "false";
            hrInsuranceInjuredEmployee = hrInsuranceDiagnosisResult.getHrInsuranceInjuredEmployee();
            hrEmployees = hrInsuranceInjuredEmployee.getEmpId();
            hrJobTypes = hrEmployees.getJobId();
            hrInsurance = hrInsuranceInjuredEmployee.getInsuranceId();
            hrLuInsurances = hrInsurance.getInsuranceId();
            hrLuInsuranceBranches = hrInsurance.getBranchId();
            if (hrEmployees.getHrEmpMembershipsesList().size() >= 1) {
                for (int i = 0; i < hrEmployees.getHrEmpMembershipsesList().size(); i++) {
                    if (hrEmployees.getHrEmpMembershipsesList().get(i).getEmpId().getEmpId().equals(hrEmployees.getEmpId())) {
                        if (hrEmployees.getHrEmpMembershipsesList().get(i).getMembershipId().getMembership().equalsIgnoreCase("24 Hours Insurance")) {
                            setOffduty("1");
                            i = hrEmployees.getHrEmpMembershipsesList().size() + 1;
                        } else {
                            setOffduty("0");
                        }
                    } else {
                        setOffduty("0");
                    }
                }
            } else {
                setOffduty("0");
            }
            
        } else {
            choosePermanet = "false";
            chooseDailyLabour = "true";
            hrInsuranceInjuredEmployee = hrInsuranceDiagnosisResult.getHrInsuranceInjuredEmployee();
            hrInsurance = hrInsuranceInjuredEmployee.getInsuranceId();
            hrLuInsurances = hrInsurance.getInsuranceId();
            hrLuInsuranceBranches = hrInsurance.getBranchId();
        }
        renderPnlManPage = false;
        renderPnlCreateAdditional = true;
        createOrSearchBundle = "Search";
        update = 0;
        saveOrUpdateButton = "Save";
        lockInput = true;
    }
    
    public void populat(SelectEvent events) {
        hrInsurancePayment = null;
        hrInsurancePayment = (HrInsurancePayment) events.getObject();
        recreateDataModelwf();
        if (hrInsurancePayment.getDiagonasisResultId().getHrInsuranceInjuredEmployee().getType().equalsIgnoreCase("Permanent/contract")) {
            choosePermanet = "true";
            chooseDailyLabour = "false";
            hrInsuranceDiagnosisResult = hrInsurancePayment.getDiagonasisResultId();
            hrInsuranceInjuredEmployee = hrInsuranceDiagnosisResult.getHrInsuranceInjuredEmployee();
            hrEmployees = hrInsuranceInjuredEmployee.getEmpId();
            hrJobTypes = hrEmployees.getJobId();
            hrInsurance = hrInsuranceInjuredEmployee.getInsuranceId();
            hrLuInsurances = hrInsurance.getInsuranceId();
            hrLuInsuranceBranches = hrInsurance.getBranchId();
            if (hrEmployees.getHrEmpMembershipsesList().size() >= 1) {
                for (int i = 0; i < hrEmployees.getHrEmpMembershipsesList().size(); i++) {
                    if (hrEmployees.getHrEmpMembershipsesList().get(i).getEmpId().getEmpId().equals(hrEmployees.getEmpId())) {
                        if (hrEmployees.getHrEmpMembershipsesList().get(i).getMembershipId().getMembership().equalsIgnoreCase("24 Hours Insurance")) {
                            setOffduty("1");
                            i = hrEmployees.getHrEmpMembershipsesList().size() + 1;
                        } else {
                            setOffduty("0");
                        }
                    } else {
                        setOffduty("0");
                    }
                }
            } else {
                setOffduty("0");
            }
            
            calculatingMedicalExpence();
        } else {
            choosePermanet = "false";
            chooseDailyLabour = "true";
            hrInsuranceDiagnosisResult = hrInsurancePayment.getDiagonasisResultId();
            System.out.println(" daily " + hrInsuranceDiagnosisResult);
            hrInsuranceInjuredEmployee = hrInsuranceDiagnosisResult.getHrInsuranceInjuredEmployee();
            hrInsurance = hrInsuranceInjuredEmployee.getInsuranceId();
            hrLuInsurances = hrInsurance.getInsuranceId();
            hrLuInsuranceBranches = hrInsurance.getBranchId();
            calculateDailyLabour();
        }
        renderPnlManPage = false;
        renderPnlCreateAdditional = true;
        createOrSearchBundle = "Search";
        update = 1;
        saveOrUpdateButton = "update";
        for (HrInsurancePaymentDetail eee : hrInsurancePayment.getHrInsurancePaymentDetailList()) {
            fieldCheck.add(eee.getRecieptNo().toString());
        }
        recreateDataModel12();
        lockInput = true;
    }
    
    public void resetInsurancePayment() {
        hrInsurancePayment = new HrInsurancePayment();
        hrInsuranceDiagnosisResult = new HrInsuranceDiagnosisResult();
        hrInsuranceInjuredEmployee = new HrInsuranceInjuredEmployee();
        hrInsurancePaymentDetailmodel = new ArrayDataModel<>();
        hrEmployees = new HrEmployees();
        medicalexpence = BigDecimal.valueOf(0);
        hrLuInsurances = null;
        hrLuInsuranceBranches = null;
        compensation = null;
        corporation = null;
        amountpaid = null;
        salarypaidd = null;
        medicalexpence = null;
        update = 0;
        saveOrUpdateButton = "Save";
        addorUpdate = "Add";
    }
    @EJB
            HrInsurancePaymentFacade hrInsurancePaymentFacade;
    
    public void requestcounter() {
        hrInsurpaymentList = hrInsurancePaymentFacade.findrequestlist();
        requestNotificationCounter = hrInsurpaymentList.size();
    }
    
    public void recreateDataModel12() {
        hrInsurancePaymentDetailmodel = null;
        hrInsurancePaymentDetailmodel = new ListDataModel(new ArrayList(hrInsurancePayment.getHrInsurancePaymentDetailList()));
    }
    Set<String> fieldCheck = new HashSet<>();
    
    public Set<String> getFieldCheck() {
        return fieldCheck;
    }
    
    public void setFieldCheck(Set<String> fieldCheck) {
        this.fieldCheck = fieldCheck;
    }
    
    public void addInvoiceDetail() {
        if (fieldCheck.contains(String.valueOf(hrInsurancePaymentDetail.getRecieptNo()))) {
            
            JsfUtil.addFatalMessage("Data Table Reciept Number Duplicated.");
        } else {
            SecureRandom random = new SecureRandom();
            Integer num = random.nextInt(100000);
            String formatted = String.format("%05d", num);
            hrInsurancePaymentDetail.setReferenceNo("INS" + formatted);
            hrInsurancePayment.addInvoiceDetail(hrInsurancePaymentDetail);
            fieldCheck.add(String.valueOf(hrInsurancePaymentDetail.getRecieptNo()));
            recreateDataModel12();
            calculatingMedicalExpence();
            clear2();
        }
    }
    
    public void addInvoiceDetailDailyLabour() {
        if (fieldCheck.contains(String.valueOf(hrInsurancePaymentDetail.getRecieptNo()))) {
            
            JsfUtil.addFatalMessage("Data Table Reciept Number Duplicated.");
        } else {
            SecureRandom random = new SecureRandom();
            Integer num = random.nextInt(100000);
            String formatted = String.format("%05d", num);
            hrInsurancePaymentDetail.setReferenceNo("INS" + formatted);
            hrInsurancePayment.addInvoiceDetail(hrInsurancePaymentDetail);
            fieldCheck.add(String.valueOf(hrInsurancePaymentDetail.getRecieptNo()));
            recreateDataModel12();
            calculateDailyLabour();
            clear2();
        }
    }
    
    public void clear2() {
        hrInsurancePaymentDetail = new HrInsurancePaymentDetail();
    }
    
    public void clear() {
        hrInsurancePaymentDetail = new HrInsurancePaymentDetail();
        compensation = null;
        corporation = null;
        salarypaidd = null;
        amountpaid = null;
        medicalexpence = null;
    }
    
    public void saveInsurancePaymentApprove() {
        
        try {
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(SessionBean.getUserName(), "saveInsurancePaymentApprove", dataset)) {
                if (hrInsurancePayment == null) {
                    if (hrInsurancePayment.getStatus().equals(1)) {
                        JsfUtil.addFatalMessage("Can't Approve empty data");
                    } else if (hrInsurancePayment.getStatus().equals(2)) {
                        JsfUtil.addFatalMessage("Can't Reject empty data");
                    }
                } else if ((!(getHrInsurancePaymentDetailmodel().getRowCount() > 0))) {
                    if (hrInsurancePayment.getStatus().equals(1)) {
                        JsfUtil.addFatalMessage("Can't Approve empty dataTable");
                    } else if (hrInsurancePayment.getStatus().equals(2)) {
                        JsfUtil.addFatalMessage("Can't Reject empty dataTable");
                    }
                    
                } else {
                    try {
//                    hrInsurancePayment.getApprovedBy(String.valueOf(SessionBean.getUserId()));
                        if (hrInsurancePayment.getStatus().equals(1) && workFlow.isApproveStatus()) {
                            workFlow.setUserStatus(Constants.APPROVE_VALUE);
                            hrInsurancePayment.setStatus(workFlow.getUserStatus());
                            wfHrProcessed.setInsurancePaymentId(hrInsurancePayment);
                            wfHrProcessed.setProcessedOn(hrInsurancePayment.getPreparddate());
                            wfHrProcessed.setProcessedBy(SessionBean.getUserId());
                            wfHrProcessed.setDecision(hrInsurancePayment.getStatus());
                            hrinsurancePaymentLocal.saveUpdate(hrInsurancePayment);
                            wfHrProcessedFacade.saveOrUpdate(wfHrProcessed);
                            JsfUtil.addSuccessMessage("Approve Successfully");
                        } else if (hrInsurancePayment.getStatus().equals(1) && workFlow.isCheckStatus()) {
                            workFlow.setUserStatus(Constants.CHECK_APPROVE_VALUE);
                            hrInsurancePayment.setStatus(workFlow.getUserStatus());
                            wfHrProcessed.setInsurancePaymentId(hrInsurancePayment);
                            wfHrProcessed.setProcessedOn(hrInsurancePayment.getPreparddate());
                            wfHrProcessed.setProcessedBy(SessionBean.getUserId());
                            wfHrProcessed.setDecision(hrInsurancePayment.getStatus());
                            hrinsurancePaymentLocal.saveUpdate(hrInsurancePayment);
                            wfHrProcessedFacade.saveOrUpdate(wfHrProcessed);
                            JsfUtil.addSuccessMessage("approved Successfully");
                        } else if (hrInsurancePayment.getStatus().equals(2) && workFlow.isApproveStatus()) {
                            workFlow.setUserStatus(Constants.APPROVE_REJECT_VALUE);
                            hrInsurancePayment.setStatus(workFlow.getUserStatus());
                            wfHrProcessed.setInsurancePaymentId(hrInsurancePayment);
                            wfHrProcessed.setProcessedOn(hrInsurancePayment.getPreparddate());
                            wfHrProcessed.setProcessedBy(SessionBean.getUserId());
                            wfHrProcessed.setDecision(hrInsurancePayment.getStatus());
                            hrinsurancePaymentLocal.saveUpdate(hrInsurancePayment);
                            wfHrProcessedFacade.saveOrUpdate(wfHrProcessed);
                            JsfUtil.addSuccessMessage("Rejected Successfully");
                        } else if (hrInsurancePayment.getStatus().equals(2) && workFlow.isCheckStatus()) {
                            workFlow.setUserStatus(Constants.CHECK_REJECT_VALUE);
                            hrInsurancePayment.setStatus(workFlow.getUserStatus());
                            workFlow.setUserStatus(Constants.APPROVE_REJECT_VALUE);
                            hrInsurancePayment.setStatus(workFlow.getUserStatus());
                            wfHrProcessed.setInsurancePaymentId(hrInsurancePayment);
                            wfHrProcessed.setProcessedOn(hrInsurancePayment.getPreparddate());
                            wfHrProcessed.setProcessedBy(SessionBean.getUserId());
                            wfHrProcessed.setDecision(hrInsurancePayment.getStatus());
                            hrinsurancePaymentLocal.saveUpdate(hrInsurancePayment);
                            wfHrProcessedFacade.saveOrUpdate(wfHrProcessed);
                            JsfUtil.addSuccessMessage("Rejected Successfully");
                        }
                        
                        resetInsurancePayment();
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
    
    public void PaymentapproveDisplayChanged(SelectEvent event) {
        hrInsurancePayment = (HrInsurancePayment) event.getObject();
        recreateDataModelwf();
        hrInsurancePayment.setId(hrInsurancePayment.getId());
        hrInsurancePayment = hrinsurancePaymentLocal.getSelectedPayment(hrInsurancePayment.getId());
        hrInsuranceDiagnosisResult = hrInsurancePayment.getDiagonasisResultId();
        hrInsuranceInjuredEmployee = hrInsuranceDiagnosisResult.getHrInsuranceInjuredEmployee();
        hrInsurance = hrInsuranceInjuredEmployee.getInsuranceId();
        hrLuInsurances = hrInsurance.getInsuranceId();
        hrLuInsuranceBranches = hrInsurance.getBranchId();
        hrEmployees = hrInsuranceInjuredEmployee.getEmpId();
        renderPnlManPage = false;
        renderPnlCreateAdditional = true;
        createOrSearchBundle = "Search";
        update = 1;
        saveOrUpdateButton = "Save";
        recreateDataModel12();
        if (hrInsuranceInjuredEmployee.getType().equalsIgnoreCase("Permanent/contract")) {
            choosePermanet = "true";
            chooseDailyLabour = "false";
            if (hrEmployees.getHrEmpMembershipsesList().size() >= 1) {
                for (int i = 0; i < hrEmployees.getHrEmpMembershipsesList().size(); i++) {
                    if (hrEmployees.getHrEmpMembershipsesList().get(i).getEmpId().getEmpId().equals(hrEmployees.getEmpId())) {
                        if (hrEmployees.getHrEmpMembershipsesList().get(i).getMembershipId().getMembership().equalsIgnoreCase("24 Hours Insurance")) {
                            setOffduty("1");
                            i = hrEmployees.getHrEmpMembershipsesList().size() + 1;
                        } else {
                            setOffduty("0");
                        }
                    } else {
                        setOffduty("0");
                    }
                }
                
            } else {
                setOffduty("0");
            }
            
            calculatingMedicalExpence();
        } else {
            choosePermanet = "false";
            chooseDailyLabour = "true";
            calculateDailyLabour();
        }
        
    }
    
//    public DataModel<HrInsurancePayment> getInsurancepaymentdatamodel() {
//        return insurancepaymentdatamodel = new ListDataModel(hrinsurancePaymentLocal.loadpaymentrequest(status));
//    }
    public void setInsurancepaymentdatamodel(DataModel<HrInsurancePayment> insurancepaymentdatamodel) {
        this.insurancepaymentdatamodel = insurancepaymentdatamodel;
    }
    
    public InsurancePaymentController() {
    }
//</editor-fold>

}
