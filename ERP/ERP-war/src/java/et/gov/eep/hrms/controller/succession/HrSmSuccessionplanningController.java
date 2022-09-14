/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.controller.succession;
//<editor-fold defaultstate="collapsed" desc="Imports">

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import java.math.BigDecimal;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.ArrayDataModel;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
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
import et.gov.eep.hrms.businessLogic.succession.HrSmSuccessionPlanningBeanLocal;
import et.gov.eep.hrms.businessLogic.succession.SuccessorEvaluationBeanLocal;
import et.gov.eep.hrms.businessLogic.training.HrLuTrainingCategoryBeanLocal;
import et.gov.eep.hrms.businessLogic.training.HrTrainingCoursesBeanLocal;
import et.gov.eep.hrms.entity.employee.HrEmployees;
import et.gov.eep.hrms.entity.lookup.HrLuGrades;
import et.gov.eep.hrms.entity.organization.HrDepartments;
import et.gov.eep.hrms.entity.organization.HrJobTypes;
import et.gov.eep.hrms.entity.succession.HrSmSuccessionPlanDetails;
import et.gov.eep.hrms.entity.succession.HrSmSuccessionPlans;
import et.gov.eep.hrms.entity.succession.HrSmSuccessorEvaluation;
import et.gov.eep.hrms.entity.training.HrLuTrainingCategory;
import et.gov.eep.hrms.entity.training.HrTdCourses;
import et.gov.eep.hrms.mapper.training.HrTdCoursesFacade;

//</editor-fold>
@Named(value = "hrSmSuccessionPlanningCotroller")
@ViewScoped
public class HrSmSuccessionplanningController implements Serializable {

    //<editor-fold defaultstate="collapsed" desc="Entity business logic and variables">
    @Inject
    HrSmSuccessionPlans hrSmSuccessionPlans;

    @Inject
    WfHrProcessed wfHrProcessed;

    @EJB
    SuccessorEvaluationBeanLocal SuccessorEvaluationBeanLocal;

    @EJB
    HrSmSuccessionPlanningBeanLocal hrSmSuccessionPlanningBeanLocal;

    @EJB
    HrTrainingCoursesBeanLocal hrTrainingCoursesBeanLocal;

    @EJB
    HrLuTrainingCategoryBeanLocal hrLuTrainingCategoryBeanLocal;

    @EJB
    WfHrProcessedBeanLocal wfHrProcessedBeanLocal;

    @Inject
    HrSmSuccessorEvaluation hrSmSuccessorEvaluation;

    @Inject
    HrSmSuccessionPlanDetails hrSmSuccessionPlanDetails;

    @Inject
    HrTdCourses hrTdCourses;

    @Inject
    HrLuTrainingCategory hrLuTrainingCategory;

    @Inject
    HrEmployees hrEmployees;

    @Inject
    HrJobTypes hrJobTypes;

    @Inject
    HrDepartments hrDepartments;

    @Inject
    HrLuGrades hrLuGrades;

    @Inject
    SessionBean sessionBeanLocal;

    @EJB
    SuccessorEvaluationBeanLocal successorEvaluationBeanLocal;

    @EJB
    HrTdCoursesFacade hrTdCoursesFacade;

    private String tabToggle = "";
    int update = 0;
    private Boolean disableBtn = false;
    private boolean renderPnlCreateAdditional = true;
    private String addorUpdate = "Add";
    boolean btnaddvisibility = true;
    private String createOrSearchBundle = "";
    private String headerTitle = "Search....";
    private String headerTitle1 = "Evaluated List";
    private String saveorUpdateBundle = "Save";
    private String icone = "ui-icon-plus";
    private boolean disableBtnCreate;
    private boolean renderPnlCreateGatePass = false;
    private boolean renderPnlManPage = true;
    private boolean btnNewRender = false;
    private String renderpnlContrat = "false";

    List<HrSmSuccessionPlans> hrSmSuccessionList = new ArrayList<>();
    List<HrSmSuccessionPlans> allApprovesuccessors = new ArrayList<>();
    List<HrSmSuccessionPlans> hrSmSuccessorEvaluationAddList = new ArrayList<>();
    private ArrayList<HrSmSuccessorEvaluation> selectedRows;
    private HrSmSuccessorEvaluation arraylisttype;
    List<HrSmSuccessionPlans> hrApplyList = new ArrayList<>();
    List<HrSmSuccessorEvaluation> hrApplyList1 = new ArrayList<>();
    List<HrSmSuccessionPlans> planApprovvedList = new ArrayList<>();
    List<HrTdCourses> coursetrainningList = new ArrayList<>();
    List<HrLuTrainingCategory> hrLuTrainingCategoryList = new ArrayList<>();
    private List<HrSmSuccessionPlans> successionPlaningtList;
    List<HrSmSuccessionPlanDetails> hrplandetailList = new ArrayList<>();
    DataModel<WfHrProcessed> workflowDataModel;
    DataModel<HrSmSuccessionPlanDetails> planDetailDataModel;
    DataModel<HrSmSuccessionPlans> hrTdCoursesDataModel;
    DataModel<HrSmSuccessionPlans> hrplaningDataModel;
    private HrSmSuccessionPlans rowselect;
    private Integer evaluatatedBy;
    int status1 = 0, prepareBy;
    WorkFlow workFlow = new WorkFlow();
    UserStatus userStatus = new UserStatus();
    int selectedStatus;
    Status status = new Status();
    Integer requestNotificationCounter = 0;

    //</editor-fold>
    @PostConstruct
    public void init() {
        hrApplyList1 = hrSmSuccessionPlanningBeanLocal.findAprovedSuccessor();
        hrLuTrainingCategoryList = hrSmSuccessionPlanningBeanLocal.findAll();
        String Shday = StringDateManipulation.toDayInEc();
        wfHrProcessed.setProcessedOn(Shday);
        hrSmSuccessionPlans.setPreparedOn(wfHrProcessed.getProcessedOn());
        prepareBy = sessionBeanLocal.getUserId();
    }
    //<editor-fold defaultstate="collapsed" desc="getter setter">

    public Boolean getDisableBtn() {
        return disableBtn;
    }

    public void setDisableBtn(Boolean disableBtn) {
        this.disableBtn = disableBtn;
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

    public HrSmSuccessionPlanDetails getHrSmSuccessionPlanDetails() {
        if (hrSmSuccessionPlanDetails == null) {
            hrSmSuccessionPlanDetails = new HrSmSuccessionPlanDetails();
        }
        return hrSmSuccessionPlanDetails;
    }

    public void setHrSmSuccessionPlanDetails(HrSmSuccessionPlanDetails hrSmSuccessionPlanDetails) {
        this.hrSmSuccessionPlanDetails = hrSmSuccessionPlanDetails;
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

    public SuccessorEvaluationBeanLocal getSuccessorEvaluationBeanLocal() {
        return SuccessorEvaluationBeanLocal;
    }

    public void setSuccessorEvaluationBeanLocal(SuccessorEvaluationBeanLocal SuccessorEvaluationBeanLocal) {
        this.SuccessorEvaluationBeanLocal = SuccessorEvaluationBeanLocal;
    }

    public HrSmSuccessorEvaluation getArraylisttype() {
        return arraylisttype;
    }

    public void setArraylisttype(HrSmSuccessorEvaluation arraylisttype) {
        this.arraylisttype = arraylisttype;
    }

    public List<HrLuTrainingCategory> getHrLuTrainingCategoryList() {
        if (hrLuTrainingCategoryList == null) {
            hrLuTrainingCategoryList = new ArrayList<>();
        }
        return hrLuTrainingCategoryList;
    }

    public void setHrLuTrainingCategoryList(List<HrLuTrainingCategory> hrLuTrainingCategoryList) {
        this.hrLuTrainingCategoryList = hrLuTrainingCategoryList;
    }

    public boolean isDisableBtnCreate() {
        return disableBtnCreate;
    }

    public void setDisableBtnCreate(boolean disableBtnCreate) {
        this.disableBtnCreate = disableBtnCreate;
    }

    public List<HrSmSuccessorEvaluation> getHrApplyList1() {
        if (hrApplyList1 == null) {
            hrApplyList1 = new ArrayList<>();
        }
        return hrApplyList1;
    }

    public void setHrApplyList1(List<HrSmSuccessorEvaluation> hrApplyList1) {
        this.hrApplyList1 = hrApplyList1;
    }

    public List<HrSmSuccessionPlans> getPlanApprovvedList() {
        return planApprovvedList;
    }

    public void setPlanApprovvedList(List<HrSmSuccessionPlans> planApprovvedList) {
        this.planApprovvedList = planApprovvedList;
    }

    public String getCreateOrSearchBundle() {
        return createOrSearchBundle;
    }

    public void setCreateOrSearchBundle(String createOrSearchBundle) {
        this.createOrSearchBundle = createOrSearchBundle;
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

    public boolean isBtnNewRender() {
        return btnNewRender;
    }

    public void setBtnNewRender(boolean btnNewRender) {
        this.btnNewRender = btnNewRender;
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

    public int getUpdate() {
        return update;
    }

    public void setUpdate(int update) {
        this.update = update;
    }

    public List<HrSmSuccessionPlans> getHrSmSuccessionList() {
        return hrSmSuccessionList;
    }

    public void setHrSmSuccessionList(List<HrSmSuccessionPlans> hrSmSuccessionList) {
        this.hrSmSuccessionList = hrSmSuccessionList;
    }

    public List<HrSmSuccessionPlans> getAllApprovesuccessors() {
        return allApprovesuccessors;
    }

    public void setAllApprovesuccessors(List<HrSmSuccessionPlans> allApprovesuccessors) {
        this.allApprovesuccessors = allApprovesuccessors;
    }

    public List<HrSmSuccessionPlans> getHrSmSuccessorEvaluationAddList() {
        return hrSmSuccessorEvaluationAddList;
    }

    public void setHrSmSuccessorEvaluationAddList(List<HrSmSuccessionPlans> hrSmSuccessorEvaluationAddList) {
        this.hrSmSuccessorEvaluationAddList = hrSmSuccessorEvaluationAddList;
    }

    public boolean isRenderPnlCreateAdditional() {
        return renderPnlCreateAdditional;
    }

    public void setRenderPnlCreateAdditional(boolean renderPnlCreateAdditional) {
        this.renderPnlCreateAdditional = renderPnlCreateAdditional;
    }

    public String getAddorUpdate() {
        return addorUpdate;
    }

    public void setAddorUpdate(String addorUpdate) {
        this.addorUpdate = addorUpdate;
    }

    public String getTabToggle() {
        return tabToggle;
    }

    public void setTabToggle(String tabToggle) {
        this.tabToggle = tabToggle;
    }

    public String getRenderpnlContrat() {
        return renderpnlContrat;
    }

    public void setRenderpnlContrat(String renderpnlContrat) {
        this.renderpnlContrat = renderpnlContrat;
    }

    public boolean isBtnaddvisibility() {
        return btnaddvisibility;
    }

    public void setBtnaddvisibility(boolean btnaddvisibility) {
        this.btnaddvisibility = btnaddvisibility;
    }

    public ArrayList<HrSmSuccessorEvaluation> getSelectedRows() {
        return selectedRows;
    }

    public void setSelectedRows(ArrayList<HrSmSuccessorEvaluation> selectedRows) {
        this.selectedRows = selectedRows;
    }

    public List<HrSmSuccessionPlans> getHrApplyList() {
        if (hrApplyList == null) {
            hrApplyList = new ArrayList<>();
        }
        return hrApplyList;
    }

    public void setHrApplyList(List<HrSmSuccessionPlans> hrApplyList) {
        this.hrApplyList = hrApplyList;
    }

    public List<HrTdCourses> getCoursetrainningList() {
        if (coursetrainningList == null) {
            coursetrainningList = new ArrayList<>();
        }
        return coursetrainningList;
    }

    public void setCoursetrainningList(List<HrTdCourses> coursetrainningList) {
        this.coursetrainningList = coursetrainningList;
    }

    public HrSmSuccessionPlans getHrSmSuccessionPlans() {
        if (hrSmSuccessionPlans == null) {
            hrSmSuccessionPlans = new HrSmSuccessionPlans();
        }
        return hrSmSuccessionPlans;
    }

    public void setHrSmSuccessionPlans(HrSmSuccessionPlans hrSmSuccessionPlans) {
        this.hrSmSuccessionPlans = hrSmSuccessionPlans;
    }

    public HrTdCourses getHrTdCourses() {
        if (hrTdCourses == null) {
            hrTdCourses = new HrTdCourses();
        }
        return hrTdCourses;
    }

    public void setHrTdCourses(HrTdCourses hrTdCourses) {
        this.hrTdCourses = hrTdCourses;
    }

    public HrLuTrainingCategory getHrLuTrainingCategory() {
        if (hrLuTrainingCategory == null) {
            hrLuTrainingCategory = new HrLuTrainingCategory();
        }
        return hrLuTrainingCategory;
    }

    public void setHrLuTrainingCategory(HrLuTrainingCategory hrLuTrainingCategory) {
        this.hrLuTrainingCategory = hrLuTrainingCategory;
    }

    public List<HrLuTrainingCategory> getCoursecatagorys() {
        return hrSmSuccessionPlanningBeanLocal.findAllCoursecatagory();
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

    public DataModel<HrSmSuccessionPlans> getHrTdCoursesDataModel() {
        return hrTdCoursesDataModel = new ListDataModel();
    }

    public void setHrTdCoursesDataModel(DataModel<HrSmSuccessionPlans> hrTdCoursesDataModel) {
        this.hrTdCoursesDataModel = hrTdCoursesDataModel;
    }

    public DataModel<HrSmSuccessionPlans> getHrplaningDataModel() {
        if (hrplaningDataModel == null) {
            hrplaningDataModel = new ArrayDataModel<>();
        }
        return hrplaningDataModel;
    }

    public void setHrplaningDataModel(DataModel<HrSmSuccessionPlans> hrplaningDataModel) {
        this.hrplaningDataModel = hrplaningDataModel;
    }

    public HrSmSuccessionPlans getRowselect() {
        return rowselect;
    }

    public void setRowselect(HrSmSuccessionPlans rowselect) {
        this.rowselect = rowselect;
    }

    public List<HrSmSuccessionPlanDetails> getHrplandetailList() {
        return hrplandetailList;
    }

    public void setHrplandetailList(List<HrSmSuccessionPlanDetails> hrplandetailList) {
        this.hrplandetailList = hrplandetailList;
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
        return hrJobTypes;
    }

    public void setHrJobTypes(HrJobTypes hrJobTypes) {
        this.hrJobTypes = hrJobTypes;
    }

    public HrDepartments getHrDepartments() {
        return hrDepartments;
    }

    public void setHrDepartments(HrDepartments hrDepartments) {
        this.hrDepartments = hrDepartments;
    }

    public HrLuGrades getHrLuGrades() {
        return hrLuGrades;
    }

    public void setHrLuGrades(HrLuGrades hrLuGrades) {
        this.hrLuGrades = hrLuGrades;
    }

    public Integer getEvaluatatedBy() {
        return evaluatatedBy;
    }

    public void setEvaluatatedBy(Integer evaluatatedBy) {
        this.evaluatatedBy = evaluatatedBy;
    }

    public List<HrSmSuccessionPlans> getSuccessionPlaningtList() {
        if (hrSmSuccessionList == null) {
            hrSmSuccessionList = new ArrayList<>();
        }
        return successionPlaningtList;
    }

    public void setSuccessionPlaningtList(List<HrSmSuccessionPlans> successionPlaningtList) {
        this.successionPlaningtList = successionPlaningtList;
    }

    //</editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Search">
    public void searchPage() {
        renderPnlCreateGatePass = false;
        renderPnlManPage = true;
        headerTitle = "Search...";
        createOrSearchBundle = "";
    }

    public void newButtonAction() {
        resetSuccessionPlaning();
        renderPnlCreateGatePass = true;
        renderPnlManPage = false;
        btnNewRender = false;
    }

    public void requestNotifys(SelectEvent event) {
        hrSmSuccessorEvaluation = (HrSmSuccessorEvaluation) event.getObject();
        hrEmployees = hrSmSuccessorEvaluation.getEmpId();
        hrJobTypes = hrSmSuccessorEvaluation.getKmpId().getJobId();
        hrDepartments = hrSmSuccessorEvaluation.getKmpId().getDeptId();
        hrLuGrades = hrSmSuccessorEvaluation.getEmpId().getGradeId().getGradeId();
        disableBtnCreate = false;
        renderPnlCreateGatePass = true;
        renderPnlManPage = false;
        renderPnlCreateAdditional = true;
        btnNewRender = true;
        createOrSearchBundle = "new";
        headerTitle = "SuccessionPlanning";
        update = 0;
        saveorUpdateBundle = "Save";
    }

    public void populate(SelectEvent events) {
        hrSmSuccessorEvaluation = (HrSmSuccessorEvaluation) events.getObject();
        hrSmSuccessorEvaluation.setId(hrSmSuccessorEvaluation.getId());
        hrSmSuccessorEvaluation = hrSmSuccessionPlanningBeanLocal.getSelectedRequest(hrSmSuccessorEvaluation.getId());
        hrEmployees = hrSmSuccessorEvaluation.getEmpId();
        hrJobTypes = hrSmSuccessorEvaluation.getKmpId().getJobId();
        hrDepartments = hrSmSuccessorEvaluation.getKmpId().getDeptId();
        hrLuGrades = hrSmSuccessorEvaluation.getEmpId().getGradeId().getGradeId();
        recreateDataModel();
        disableBtnCreate = false;
        renderPnlCreateGatePass = true;
        renderPnlManPage = false;
        renderPnlCreateAdditional = true;
        btnNewRender = true;
        createOrSearchBundle = "new";
        headerTitle = "SuccessionPlanning";
        update = 0;
        saveorUpdateBundle = "Save";
        setIcone("ui-icon-search");
    }

    public void populateapprove(SelectEvent events) {
        hrSmSuccessionPlans = (HrSmSuccessionPlans) events.getObject();
        if (hrSmSuccessionPlans.getStatus().equals(Constants.APPROVE_VALUE) || hrSmSuccessionPlans.getStatus().equals(Constants.CHECK_APPROVE_VALUE)) {
            disableBtn = true;
        } else {
            disableBtn = false;
        }
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
        renderPnlCreateAdditional = true;
        update = 1;
        saveorUpdateBundle = "Update";
    }

    public SelectItem[] getCourseName() {
        if (hrLuTrainingCategory.getCategoryName() != null) {
            SelectItem[] steps = JsfUtil.getSelectItems(hrSmSuccessionPlanningBeanLocal.getTrainingCategoryInfo(hrLuTrainingCategory), true);
            return steps;
        } else {
            SelectItem[] items = new SelectItem[1];
            items[0] = new SelectItem("", "--- Select ---");
            return items;
        }
    }

    public void courseNameValuechange(ValueChangeEvent event) {
        if (event.getNewValue().toString() != null) {
            hrLuTrainingCategory.setCategoryName(event.getNewValue().toString());
            hrLuTrainingCategory = hrLuTrainingCategoryBeanLocal.fetchCatagory(Integer.parseInt(event.getNewValue().toString()));
            hrTdCourses.setCategoryId(hrLuTrainingCategory);
            coursetrainningList = hrTrainingCoursesBeanLocal.findByCatagory(hrTdCourses);
            hrTdCoursesDataModel = new ListDataModel(new ArrayList(coursetrainningList));
        }
    }

    public void courseValuechange(ValueChangeEvent event) {
        if (event.getNewValue().toString() != null) {
            BigDecimal bid = new BigDecimal(event.getNewValue().toString());
            hrTdCourses.setId(bid);
            hrTdCourses = hrTdCoursesFacade.findCourseId(hrTdCourses);
        }
    }

    public void findbyId() {
        try {
            if (hrEmployees.getFirstName() != null) {
                planApprovvedList = hrSmSuccessionPlanningBeanLocal.findFistName(hrSmSuccessorEvaluation);
                hrTdCoursesDataModel = new ListDataModel(new ArrayList(planApprovvedList));
            } else if (hrEmployees.getFirstName() == null) {
                planApprovvedList = hrSmSuccessionPlanningBeanLocal.findall();
                hrplaningDataModel = new ListDataModel(new ArrayList(planApprovvedList));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void searchByFistName(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            planApprovvedList = hrSmSuccessionPlanningBeanLocal.findFistName(hrSmSuccessorEvaluation);
        }
    }

    private void populateRequestTable(Status status1) {
        try {
            workFlow = new WorkFlow();
            successionPlaningtList = hrSmSuccessionPlanningBeanLocal.loadSuccessionRequestList(status1, sessionBeanLocal.getUserId());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void populateApproveTable(Status status1) {
        try {
            workFlow = new WorkFlow();
            successionPlaningtList = hrSmSuccessionPlanningBeanLocal.loadSuccessionAprovedtList(status1, sessionBeanLocal.getUserId());
        } catch (Exception ex) {
            ex.printStackTrace();
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

    public void findAll() {
        planApprovvedList = hrSmSuccessionPlanningBeanLocal.findall(hrSmSuccessionPlans);
        hrTdCoursesDataModel = new ListDataModel(new ArrayList(planApprovvedList));
    }

    public Integer getRequestNotificationCounter() {
        hrLuTrainingCategoryList = hrSmSuccessionPlanningBeanLocal.findAll();
        requestNotificationCounter = hrLuTrainingCategoryList.size();
        return requestNotificationCounter;
    }

    public List<HrSmSuccessionPlans> getSucessionRequestList() {
        return hrSmSuccessionPlanningBeanLocal.findRequestForApproval();
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Mian Methods">

    public void saveSuccessionPlanning() {
        try {
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(sessionBeanLocal.getUserName(), "saveSuccessionPlanning", dataset)) {
                try {
                    if (hrEmployees.getId() == null) {
                        JsfUtil.addFatalMessage("Empty Information can't Planned!!");
                    } else if ((!(planDetailDataModel.getRowCount() > 0))) {
                        JsfUtil.addFatalMessage("Data Table Should be filled");
                    } else {
                        hrSmSuccessorEvaluation.setStatus(Constants.PREPARE_VALUE);
                        hrSmSuccessionPlans.setStatus(String.valueOf(Constants.PREPARE_VALUE));
                        wfHrProcessed.setProcessedBy(sessionBeanLocal.getUserId());
                        hrSmSuccessionPlans.setPreparedBy(wfHrProcessed.getProcessedBy());
                        hrSmSuccessionPlans.setPreparedOn(StringDateManipulation.toDayInEc());
                        successorEvaluationBeanLocal.saveorupdate(hrSmSuccessorEvaluation);
                        hrSmSuccessionPlans.setSuccessorEvaluationId(hrSmSuccessorEvaluation);
                        hrSmSuccessionPlanningBeanLocal.saveOrUpdate(hrSmSuccessionPlans);
                        wfHrProcessed.setDecision(Integer.valueOf(hrSmSuccessionPlans.getStatus()));
                        wfHrProcessed.setSuccessionPlanningId(hrSmSuccessionPlans);
                        if (update == 0) {
                            wfHrProcessedBeanLocal.saveOrUpdate(wfHrProcessed);
                            JsfUtil.addSuccessMessage("Successfully Saved");
                            resetSuccessionPlaning();
                        } else {
                            JsfUtil.addSuccessMessage("Successfully Updated");
                            resetSuccessionPlaning();
                        }
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JsfUtil.addFatalMessage("Error occured while Saving.");
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
                JAXBElement<String> program = new JAXBElement<String>(qualifiedName, String.class, null, "saveSuccessionPlanning");
                eventEntry.setProgram(program);
                security.addEventLog(eventEntry, dataset);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void resetSuccessionPlaning() {
        hrSmSuccessionPlans = new HrSmSuccessionPlans();
        planDetailDataModel = null;
        hrLuTrainingCategory = new HrLuTrainingCategory();
        hrTdCourses = new HrTdCourses();
        hrSmSuccessionPlanDetails = new HrSmSuccessionPlanDetails();
        hrSmSuccessorEvaluation = new HrSmSuccessorEvaluation();
        hrEmployees = new HrEmployees();
        hrDepartments = new HrDepartments();
        hrJobTypes = new HrJobTypes();
        hrLuGrades = new HrLuGrades();
        wfHrProcessed = new WfHrProcessed();
    }

    public void clearSuccessionPlaning() {
        hrEmployees = new HrEmployees();
        hrDepartments = new HrDepartments();
        hrJobTypes = new HrJobTypes();
        hrLuGrades = new HrLuGrades();
        planDetailDataModel = new ArrayDataModel<>();
        hrSmSuccessionPlans = new HrSmSuccessionPlans();
        hrSmSuccessorEvaluation = new HrSmSuccessorEvaluation();
        hrSmSuccessionPlanDetails = new HrSmSuccessionPlanDetails();
        wfHrProcessed = new WfHrProcessed();
    }

    public void add() {
        try {
            String SStmonth[] = hrSmSuccessionPlanDetails.getStartDate().split("/");
            int ISstmonth = Integer.parseInt(SStmonth[1]);
            String SSstyear[] = hrSmSuccessionPlanDetails.getStartDate().split("/");
            int ISyear = Integer.parseInt(SSstyear[2]);
            String SSdate[] = hrSmSuccessionPlanDetails.getStartDate().split("/");
            int ISstdate = Integer.parseInt(SSdate[0]);
            String SEday[] = hrSmSuccessionPlanDetails.getEndDate().split("/");
            int IEday = Integer.parseInt(SEday[0]);
            String SEMonth[] = hrSmSuccessionPlanDetails.getEndDate().split("/");
            int IEMonth = Integer.parseInt(SEMonth[1]);
            String SEYear[] = hrSmSuccessionPlanDetails.getEndDate().split("/");
            int IEYear = Integer.parseInt(SEYear[2]);
            int Start_end_DateGap = ((IEday - ISstdate) + ((IEMonth - ISstmonth) * 30) + ((IEYear - ISyear) * 365));
            if (Start_end_DateGap < 0) {
                JsfUtil.addFatalMessage("Ende date can't come Before Start date!!!");
            } else {
                hrSmSuccessionPlanDetails.setStatus("0");
                hrSmSuccessionPlanDetails.setTrainingId(hrTdCourses);
                hrSmSuccessionPlans.addDetail(hrSmSuccessionPlanDetails);
                recreateDataModel();
                hrTdCourses = new HrTdCourses();
                hrLuTrainingCategory = new HrLuTrainingCategory();
                hrSmSuccessionPlanDetails = new HrSmSuccessionPlanDetails();
            }
        } catch (Exception e) {
            e.printStackTrace();
            JsfUtil.addFatalMessage("error occured");
        }
    }

    public void onRowUnselect(UnselectEvent event) {
        hrSmSuccessorEvaluation = (HrSmSuccessorEvaluation) event.getObject();
        hrSmSuccessionPlans.setSuccessorEvaluationId(hrSmSuccessorEvaluation);
    }

    public void onRowSelect(SelectEvent event) {
        hrSmSuccessorEvaluation = (HrSmSuccessorEvaluation) event.getObject();
        hrSmSuccessionPlans.setSuccessorEvaluationId(hrSmSuccessorEvaluation);
        FacesMessage msg = new FacesMessage(hrSmSuccessorEvaluation.getId() + " is Selected");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void PlanningAprproval(SelectEvent event) {
        hrSmSuccessionPlans = (HrSmSuccessionPlans) event.getObject();
        hrSmSuccessionPlans.setId(hrSmSuccessionPlans.getId());
        hrSmSuccessionPlans = hrSmSuccessionPlanningBeanLocal.readkmpDetail(hrSmSuccessionPlans.getId());
        hrSmSuccessionPlans.getDevelopmentNeed();
        hrSmSuccessionPlans.getDevelopmentPlan();
        hrSmSuccessionPlans.getRemark();
        hrSmSuccessionPlans.getReadinessTimeFrame();
        hrSmSuccessionPlans.getStatus();
        hrSmSuccessionPlans.getSuccessorStrength();
        hrSmSuccessionPlanDetails.setId(hrSmSuccessionPlans.getId());
        hrSmSuccessionPlanDetails.getTrainingId();
        hrSmSuccessionPlanDetails.getStartDate();
        hrSmSuccessionPlanDetails.getEndDate();
        recreateDataModel();
    }

    public void valueChangefiliterByStatus(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            selectedStatus = Integer.valueOf(String.valueOf(event.getNewValue()));
            if (selectedStatus == Constants.PREPARE_VALUE) {
                status.setStatus1(Constants.PREPARE_VALUE);
                populateRequestTable(status);
            } else if (selectedStatus == Constants.APPROVE_VALUE) {
                status.setStatus1(Constants.APPROVE_VALUE);
                status.setStatus2(Constants.CHECK_APPROVE_VALUE);
                populateApproveTable(status);
            } else if (selectedStatus == Constants.APPROVE_REJECT_VALUE) {
                status.setStatus1(Constants.APPROVE_REJECT_VALUE);
                status.setStatus2(Constants.CHECK_REJECT_VALUE);
                populateApproveTable(status);
            }
        }
    }

    public void selectedSuccession(SelectEvent event) {
        hrSmSuccessionPlanDetails = (HrSmSuccessionPlanDetails) event.getObject();
        hrTdCourses = hrSmSuccessionPlanDetails.getTrainingId();
        hrLuTrainingCategory = hrTdCourses.getCategoryId();
        coursetrainningList = hrTrainingCoursesBeanLocal.findByCatagory(hrTdCourses);
    }

    public void recreateDataModel() {
        planDetailDataModel = null;
        planDetailDataModel = new ListDataModel(new ArrayList(hrSmSuccessionPlans.getHrSmSuccessionPlanDetailsList()));
    }

    public void recreateWorkflowDataModel() {
        workflowDataModel = null;
        workflowDataModel = new ListDataModel(new ArrayList(hrSmSuccessionPlans.getWfHrProcessedList()));
        for (int i = 0; i < hrSmSuccessionPlans.getWfHrProcessedList().size(); i++) {
            if (hrSmSuccessionPlans.getWfHrProcessedList().get(i).getDecision().equals(0)) {
                hrSmSuccessionPlans.getWfHrProcessedList().get(i).setChangedStstus("Prepared");
            } else if (hrSmSuccessionPlans.getWfHrProcessedList().get(i).getDecision().equals(1) || hrSmSuccessionPlans.getWfHrProcessedList().get(i).getDecision().equals(3)) {
                hrSmSuccessionPlans.getWfHrProcessedList().get(i).setChangedStstus("Approved");
            } else if (hrSmSuccessionPlans.getWfHrProcessedList().get(i).getDecision().equals(2) || hrSmSuccessionPlans.getWfHrProcessedList().get(i).getDecision().equals(4)) {
                hrSmSuccessionPlans.getWfHrProcessedList().get(i).setChangedStstus("Rejected");
            }
        }
    }
}
//</editor-fold>
