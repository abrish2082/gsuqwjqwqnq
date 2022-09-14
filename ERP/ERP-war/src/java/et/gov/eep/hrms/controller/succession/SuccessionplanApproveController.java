/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.controller.succession;

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
import et.gov.eep.commonApplications.businessLogic.WfHrProcessedBeanLocal;
import et.gov.eep.commonApplications.entity.WfHrProcessed;
import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.commonApplications.utility.date.StringDateManipulation;
import et.gov.eep.hrms.businessLogic.succession.HrSmSuccessionPlanningBeanLocal;
import et.gov.eep.hrms.entity.employee.HrEmployees;
import et.gov.eep.hrms.entity.lookup.HrLuGrades;
import et.gov.eep.hrms.entity.organization.HrDepartments;
import et.gov.eep.hrms.entity.organization.HrJobTypes;
import et.gov.eep.hrms.entity.succession.HrSmSuccessionPlanDetails;
import et.gov.eep.hrms.entity.succession.HrSmSuccessionPlans;
import et.gov.eep.hrms.entity.succession.HrSmSuccessorEvaluation;

/**
 *
 * @author insa
 */
@Named(value = "SuccessionplanApproveController")
@ViewScoped
public class SuccessionplanApproveController implements Serializable {

    //<editor-fold defaultstate="collapsed" desc="Entity business logic & Variables">
    @Inject
    HrSmSuccessionPlans hrSmSuccessionPlans;

    @Inject
    WfHrProcessed wfHrProcessed;

    @Inject
    HrSmSuccessorEvaluation hrSmSuccessorEvaluation;

    @Inject
    HrSmSuccessionPlanDetails hrSmSuccessionPlanDetails;

    @Inject
    HrJobTypes hrJobTypes;

    @Inject
    HrLuGrades hrLuGrades;

    @Inject
    HrDepartments hrDepartments;

    @Inject
    HrEmployees hrEmployees;

    @Inject
    SessionBean sessionBeanLocal;

    @Inject
    WorkFlow workFlow;

    @EJB
    HrSmSuccessionPlanningBeanLocal hrSmSuccessionPlanningBeanLocal;

    @EJB
    WfHrProcessedBeanLocal wfHrProcessedBeanLocal;

    int status = 0, prepareBy;
    int update = 0;
    private boolean renderPnlCreateGatePass = false;
    private boolean renderPnlManPage = true;
    private String addorUpdate = "Add";
    boolean btnaddvisibility = true;
    private String createOrSearchBundle = "New";
    private String headerTitle = "Search....";
    private String headerTitle1 = "Evaluated List";
    private String saveorUpdateBundle = "Save";
    private String icone = "ui-icon-plus";
    private boolean disableBtnCreate;
    private String renderpnlContrat = "false";
    private HrSmSuccessorEvaluation arraylisttype;
    DataModel<HrSmSuccessionPlanDetails> planDetailDataModel;
    DataModel<HrSmSuccessionPlans> hrTdAproveDataModel;
    String selectedValue = "";
    DataModel<WfHrProcessed> workflowDataModel;
    private boolean renderworkflowPage = false;
    List<HrSmSuccessionPlans> requestList = new ArrayList<>();
    Integer requestNotificationCounter = 0;
//</editor-fold>

    @PostConstruct
    public void init() {
        wfHrProcessed.setProcessedOn(StringDateManipulation.toDayInEc());
        hrSmSuccessionPlans.setPreparedOn(wfHrProcessed.getProcessedOn());
    }
    //<editor-fold defaultstate="collapsed" desc="Getters setters">

    public HrSmSuccessionPlans getHrSmSuccessionPlans() {
        if (hrSmSuccessionPlans == null) {
            hrSmSuccessionPlans = new HrSmSuccessionPlans();
        }
        return hrSmSuccessionPlans;
    }

    public void setHrSmSuccessionPlans(HrSmSuccessionPlans hrSmSuccessionPlans) {
        this.hrSmSuccessionPlans = hrSmSuccessionPlans;
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

    public HrSmSuccessorEvaluation getHrSmSuccessorEvaluation() {
        if (hrSmSuccessorEvaluation == null) {
            hrSmSuccessorEvaluation = new HrSmSuccessorEvaluation();
        }
        return hrSmSuccessorEvaluation;
    }

    public void setHrSmSuccessorEvaluation(HrSmSuccessorEvaluation hrSmSuccessorEvaluation) {
        this.hrSmSuccessorEvaluation = hrSmSuccessorEvaluation;
    }

    public HrSmSuccessionPlanDetails getHrSmSuccessionPlanDetails() {
        if (hrSmSuccessionPlanDetails == null) {
            hrSmSuccessionPlanDetails = new HrSmSuccessionPlanDetails();
        }
        return hrSmSuccessionPlanDetails;
    }

    public void setHrSmSuccessionPlanDetails(HrSmSuccessionPlanDetails hrSmSuccessionPlanDetails) {
        this.hrSmSuccessionPlanDetails = hrSmSuccessionPlanDetails;
    }

    public HrDepartments getHrDepartments() {

        return hrDepartments;
    }

    public void setHrDepartments(HrDepartments hrDepartments) {
        this.hrDepartments = hrDepartments;
    }

    public HrJobTypes getHrJobTypes() {
        return hrJobTypes;
    }

    public void setHrJobTypes(HrJobTypes hrJobTypes) {
        this.hrJobTypes = hrJobTypes;
    }

    public HrLuGrades getHrLuGrades() {
        return hrLuGrades;
    }

    public void setHrLuGrades(HrLuGrades hrLuGrades) {
        this.hrLuGrades = hrLuGrades;
    }

    public HrEmployees getHrEmployees() {
        return hrEmployees;
    }

    public void setHrEmployees(HrEmployees hrEmployees) {
        this.hrEmployees = hrEmployees;
    }

    public HrSmSuccessorEvaluation getArraylisttype() {
        return arraylisttype;
    }

    public void setArraylisttype(HrSmSuccessorEvaluation arraylisttype) {
        this.arraylisttype = arraylisttype;
    }

    public DataModel<WfHrProcessed> getWorkflowDataModel() {
        if (workflowDataModel == null) {
            workflowDataModel = new ArrayDataModel<>();
        }
        return workflowDataModel;
    }

    public void setWorkflowDataModel(DataModel<WfHrProcessed> workflowDataModel) {
        this.workflowDataModel = workflowDataModel;
    }

    public boolean isRenderworkflowPage() {
        return renderworkflowPage;
    }

    public void setRenderworkflowPage(boolean renderworkflowPage) {
        this.renderworkflowPage = renderworkflowPage;
    }

    public String getAddorUpdate() {
        return addorUpdate;
    }

    public void setAddorUpdate(String addorUpdate) {
        this.addorUpdate = addorUpdate;
    }

    public boolean isBtnaddvisibility() {
        return btnaddvisibility;
    }

    public void setBtnaddvisibility(boolean btnaddvisibility) {
        this.btnaddvisibility = btnaddvisibility;
    }

    public int getUpdate() {
        return update;
    }

    public void setUpdate(int update) {
        this.update = update;
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

    public String getHeaderTitle1() {
        return headerTitle1;
    }

    public void setHeaderTitle1(String headerTitle1) {
        this.headerTitle1 = headerTitle1;
    }

    public String getSaveorUpdateBundle() {
        return saveorUpdateBundle;
    }

    public void setSaveorUpdateBundle(String saveorUpdateBundle) {
        this.saveorUpdateBundle = saveorUpdateBundle;
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

    public DataModel<HrSmSuccessionPlans> getHrTdAproveDataModel() {
        return hrTdAproveDataModel;
    }

    public void setHrTdAproveDataModel(DataModel<HrSmSuccessionPlans> hrTdAproveDataModel) {
        this.hrTdAproveDataModel = hrTdAproveDataModel;
    }

    public boolean isRenderPnlCreateGatePass() {
        return renderPnlCreateGatePass;
    }

    public void setRenderPnlCreateGatePass(boolean renderPnlCreateGatePass) {
        this.renderPnlCreateGatePass = renderPnlCreateGatePass;
    }

    public boolean isRenderPnlManPage() {
        return renderPnlManPage;
    }

    public void setRenderPnlManPage(boolean renderPnlManPage) {
        this.renderPnlManPage = renderPnlManPage;
    }

    public String getRenderpnlContrat() {
        return renderpnlContrat;
    }

    public void setRenderpnlContrat(String renderpnlContrat) {
        this.renderpnlContrat = renderpnlContrat;
    }

    public String getSelectedValue() {
        return selectedValue;
    }

    public void setSelectedValue(String selectedValue) {
        this.selectedValue = selectedValue;
    }

    public DataModel<HrSmSuccessionPlanDetails> getPlanDetailDataModel() {
        if (planDetailDataModel == null) {
            planDetailDataModel = new ArrayDataModel<>();
        }
        return planDetailDataModel;
    }

    public void setPlanDetailDataModel(DataModel<HrSmSuccessionPlanDetails> planDetailDataModel) {
        this.planDetailDataModel = planDetailDataModel;
    }

    public List<HrSmSuccessionPlans> getRequestList() {
        return requestList;
    }

    public void setRequestList(List<HrSmSuccessionPlans> requestList) {
        this.requestList = requestList;
    }

    public int getPrepareBy() {
        return prepareBy;
    }

    public void setPrepareBy(int prepareBy) {
        this.prepareBy = prepareBy;
    }

    //</editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Search">
    public Integer getRequestNotificationCounter() {
        requestList = hrSmSuccessionPlanningBeanLocal.findRequestForApproval();
        requestNotificationCounter = requestList.size();
        return requestNotificationCounter;
    }

    public List<HrSmSuccessionPlans> getSucessionRequestList() {
        return hrSmSuccessionPlanningBeanLocal.findRequestForApproval();
    }

    public void poplateNotification(SelectEvent event) {
        hrSmSuccessionPlans = (HrSmSuccessionPlans) event.getObject();
        hrSmSuccessionPlans.setId(hrSmSuccessionPlans.getId());
        hrSmSuccessorEvaluation = hrSmSuccessionPlans.getSuccessorEvaluationId();
        hrSmSuccessionPlans = hrSmSuccessionPlanningBeanLocal.getSelectedPlanRequest(hrSmSuccessionPlans.getId());
        hrEmployees = hrSmSuccessorEvaluation.getEmpId();
        hrJobTypes = hrSmSuccessorEvaluation.getKmpId().getJobId();
        hrDepartments = hrSmSuccessorEvaluation.getKmpId().getDeptId();
        hrLuGrades = hrSmSuccessorEvaluation.getEmpId().getGradeId().getGradeId();
        recreateDataModel();
        recreateWorkflowDataModel();
        disableBtnCreate = false;
        renderPnlCreateGatePass = true;
        renderPnlManPage = false;
        createOrSearchBundle = "new";
        headerTitle = "SuccessionPlanning";
        update = 0;
        saveorUpdateBundle = "Save";
    }

    public void searchPage() {
        renderPnlCreateGatePass = false;
        renderPnlManPage = true;
        renderworkflowPage = false;
        headerTitle = "Search...";
        createOrSearchBundle = "New";
    }

    public void createNewAdditionalAmount() {
        saveorUpdateBundle = "Create";
        disableBtnCreate = false;
        if (createOrSearchBundle.equals("New")) {
            renderPnlCreateGatePass = true;
            renderPnlManPage = false;
            createOrSearchBundle = "Search";
            setIcone("ui-icon-search");
        } else if (createOrSearchBundle.equals("Search")) {
            renderPnlCreateGatePass = false;
            renderPnlManPage = true;
            createOrSearchBundle = "New";
            setIcone("ui-icon-plus");
        }
    }

    public void workflowpage() {
        renderPnlCreateGatePass = false;
        renderPnlManPage = false;
        renderworkflowPage = true;
        headerTitle = "Workflow...";
    }

    public void onRowEdit(SelectEvent event) {
        hrSmSuccessionPlans = (HrSmSuccessionPlans) event.getObject();
        int size = hrSmSuccessionPlans.getWfHrProcessedList().size();
        if (workFlow.isApproveStatus() || workFlow.isCheckStatus()) {
            if (hrSmSuccessionPlans.getWfHrProcessedList().get(size - 1).getDecision() == Constants.CHECK_APPROVE_VALUE || hrSmSuccessionPlans.getWfHrProcessedList().get(size - 1).getDecision() == Constants.APPROVE_VALUE) {
                setSelectedValue("Approve");
            } else if (hrSmSuccessionPlans.getWfHrProcessedList().get(size - 1).getDecision() == Constants.CHECK_REJECT_VALUE || hrSmSuccessionPlans.getWfHrProcessedList().get(size - 1).getDecision() == Constants.APPROVE_REJECT_VALUE) {
                setSelectedValue("Reject");
            }
            wfHrProcessed.setProcessedOn(hrSmSuccessionPlans.getWfHrProcessedList().get(size - 1).getProcessedOn());
        }
        populateapprove();
    }

    public void populateapprove() {
        hrSmSuccessionPlans.setId(hrSmSuccessionPlans.getId());
        hrSmSuccessorEvaluation = hrSmSuccessionPlans.getSuccessorEvaluationId();
        hrSmSuccessionPlans = hrSmSuccessionPlanningBeanLocal.getSelectedPlanRequest(hrSmSuccessionPlans.getId());
        hrEmployees = hrSmSuccessorEvaluation.getEmpId();
        hrJobTypes = hrSmSuccessorEvaluation.getKmpId().getJobId();
        hrDepartments = hrSmSuccessorEvaluation.getKmpId().getDeptId();
        hrLuGrades = hrSmSuccessorEvaluation.getEmpId().getGradeId().getGradeId();
        recreateDataModel();
        recreateWorkflowDataModel();
        disableBtnCreate = false;
        renderPnlCreateGatePass = true;
        renderPnlManPage = false;
        update = 1;
        saveorUpdateBundle = "Update";
        createOrSearchBundle = "Search";
        icone = "ui-icon-search";
    }

    public void handleselectOptions(ValueChangeEvent event) {
        if (null != event.getNewValue()) {
            selectedValue = event.getNewValue().toString();
        }
    }

    private void populateAproveTable() {
        try {
            List<HrSmSuccessionPlans> readFiltered = hrSmSuccessionPlanningBeanLocal.loadSuccessorLists(status);
            hrTdAproveDataModel = new ListDataModel(readFiltered);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void filiterByAproveStatus(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            status = Integer.valueOf(event.getNewValue().toString());
            populateAproveTable();
        }
    }

    public List<SelectItem> getFilterByAproveStatus() {
        return hrSmSuccessionPlanningBeanLocal.filterByAproveStatus();
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Main Methods">
    public void saveSuccessionPlanningApprove() {
        try {
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(sessionBeanLocal.getUserName(), "saveSuccessionPlanningApprove", dataset)) {
                if (hrSmSuccessionPlans.getId() == null) {
                    JsfUtil.addFatalMessage(" Empty Information can't Approve");
                } else {
                    try {
                        if (selectedValue.equalsIgnoreCase("1") && workFlow.isApproveStatus()) {
                            workFlow.setUserStatus(Constants.APPROVE_VALUE);
                            hrSmSuccessionPlans.setStatus(String.valueOf(workFlow.getUserStatus()));
                        } else if (selectedValue.equalsIgnoreCase("1") && workFlow.isCheckStatus()) {
                            workFlow.setUserStatus(Constants.CHECK_APPROVE_VALUE);
                            hrSmSuccessionPlans.setStatus(String.valueOf(workFlow.getUserStatus()));
                        } else if (selectedValue.equalsIgnoreCase("2") && workFlow.isApproveStatus()) {
                            workFlow.setUserStatus(Constants.APPROVE_REJECT_VALUE);
                            hrSmSuccessionPlans.setStatus(String.valueOf(workFlow.getUserStatus()));
                        } else if (selectedValue.equalsIgnoreCase("2") && workFlow.isCheckStatus()) {
                            workFlow.setUserStatus(Constants.CHECK_REJECT_VALUE);
                            hrSmSuccessionPlans.setStatus(String.valueOf(workFlow.getUserStatus()));
                        }
                        hrSmSuccessionPlanningBeanLocal.edit(hrSmSuccessionPlans);
                        wfHrProcessed.setSuccessionPlanningId(hrSmSuccessionPlans);
                        wfHrProcessed.setDecision(Integer.valueOf(hrSmSuccessionPlans.getStatus()));
                        wfHrProcessed.setProcessedBy(sessionBeanLocal.getUserId());
                        wfHrProcessedBeanLocal.saveOrUpdate(wfHrProcessed);
                        JsfUtil.addSuccessMessage(" Saved Successfully!!!");
                        clearSuccessionPlaning();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        JsfUtil.addFatalMessage("Error occure while Saving");
                    }
                }
            } else {
                JsfUtil.addFatalMessage("You don't have appropriate permission, please contact the system administrator.");
                EventEntry eventEntry = new EventEntry();
                eventEntry.setSessionId(sessionBeanLocal.getSessionID());
                eventEntry.setUserId(sessionBeanLocal.getUserId());
                QName qualifiedName = new QName("", "project");
                JAXBElement<String> userName = new JAXBElement<String>(qualifiedName, String.class, null, sessionBeanLocal.getUserName());
                eventEntry.setUserLogin(userName);
                JAXBElement<String> module = new JAXBElement<String>(qualifiedName, String.class, null, "HRMS");
                eventEntry.setModule(module);
                JAXBElement<String> program = new JAXBElement<String>(qualifiedName, String.class, null, "saveSuccessionPlanningApprove");
                eventEntry.setProgram(program);
                security.addEventLog(eventEntry, dataset);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void clearSuccessionPlaning() {
        hrSmSuccessionPlans = new HrSmSuccessionPlans();
        wfHrProcessed = new WfHrProcessed();
        planDetailDataModel = null;
        hrEmployees = new HrEmployees();
        hrDepartments = new HrDepartments();
        hrLuGrades = new HrLuGrades();
        hrJobTypes = new HrJobTypes();
        workFlow = new WorkFlow();

    }

    public void resetSuccessionPlaning() {
        hrSmSuccessionPlans = new HrSmSuccessionPlans();
        wfHrProcessed = new WfHrProcessed();
        planDetailDataModel = null;
        hrEmployees = new HrEmployees();
        hrDepartments = new HrDepartments();
        hrLuGrades = new HrLuGrades();
        hrJobTypes = new HrJobTypes();
    }

    public void recreateDataModel() {
        planDetailDataModel = null;
        planDetailDataModel = new ListDataModel(new ArrayList(hrSmSuccessionPlans.getHrSmSuccessionPlanDetailsList()));
    }

    public void recreateWorkflowDataModel() {
        workflowDataModel = null;
        for (int i = 0; i < hrSmSuccessionPlans.getWfHrProcessedList().size(); i++) {
            if (hrSmSuccessionPlans.getWfHrProcessedList().get(i).getDecision() == 0) {
                hrSmSuccessionPlans.getWfHrProcessedList().get(i).setChangedStstus("Prepared");
            } else if (hrSmSuccessionPlans.getWfHrProcessedList().get(i).getDecision() == 1 || hrSmSuccessionPlans.getWfHrProcessedList().get(i).getDecision() == 3) {
                hrSmSuccessionPlans.getWfHrProcessedList().get(i).setChangedStstus("Approved");
            } else if (hrSmSuccessionPlans.getWfHrProcessedList().get(i).getDecision() == 2 || hrSmSuccessionPlans.getWfHrProcessedList().get(i).getDecision() == 4) {
                hrSmSuccessionPlans.getWfHrProcessedList().get(i).setChangedStstus("Rejected");
            }
            workflowDataModel = new ListDataModel(new ArrayList(hrSmSuccessionPlans.getWfHrProcessedList()));
        }
    }
    //</editor-fold>
}
