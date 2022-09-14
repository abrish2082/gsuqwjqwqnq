/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.controller.training;

import java.io.Serializable;
import java.math.BigDecimal;
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
import org.primefaces.context.RequestContext;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import securityBean.Constants;
import securityBean.SessionBean;
import securityBean.WorkFlow;
import webService.AAA;
import webService.EventEntry;
import webService.IAdministration;
import et.gov.eep.commonApplications.businessLogic.WfHrProcessedBeanLocal;
import et.gov.eep.commonApplications.entity.Status;
import et.gov.eep.commonApplications.entity.WfHrProcessed;
import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.commonApplications.utility.date.StringDateManipulation;
import et.gov.eep.hrms.businessLogic.employee.HrEmployeeBeanLocal;
import et.gov.eep.hrms.businessLogic.organization.HrDepartmentsBeanLocal;
import et.gov.eep.hrms.businessLogic.recruitment.HrRecruitmentRequestsBeanLocal;
import et.gov.eep.hrms.businessLogic.training.HrTdAnnualNeedRequestsBeanLocal;
import et.gov.eep.hrms.businessLogic.training.HrTdAnnualTraParticipantsBeanLocal;
import et.gov.eep.hrms.businessLogic.training.HrTrainingCoursesBeanLocal;
import et.gov.eep.hrms.entity.employee.HrEmployees;
import et.gov.eep.hrms.entity.organization.HrDepartments;
import et.gov.eep.hrms.entity.training.HrLuTrainingCategory;
import et.gov.eep.hrms.entity.training.HrTdAnnualNeedRequests;
import et.gov.eep.hrms.entity.training.HrTdAnnualTraParticipants;
import et.gov.eep.hrms.entity.training.HrTdAnnualTrainingNeeds;
import et.gov.eep.hrms.entity.training.HrTdCourses;
import et.gov.eep.hrms.entity.training.HrTdTrainerProfiles;
import et.gov.eep.hrms.integration.HrDepartmentsIntegrationBeanLocal;
import et.gov.eep.hrms.mapper.training.HrTdTrainerProfilesFacade;

/**
 *
 * @author Bek
 */
@Named(value = "annualNeedRequestsController")
@ViewScoped
public class AnnualNeedRequestsController implements Serializable {

    //<editor-fold defaultstate="collapsed" desc="Entity , bussiness logic & variables">
    @Inject
    WfHrProcessed wfHrProcessed;
    @Inject
    HrTdAnnualNeedRequests hrTdAnnualNeedRequests;
    @Inject
    HrTdAnnualNeedRequests srcAnnualNeedRequests;
    @Inject
    HrTdAnnualTrainingNeeds hrTdAnnualTrainingNeeds;
    @Inject
    HrLuTrainingCategory hrLuTrainingCategory;
    @Inject
    HrTdCourses hrTdCourses;
    @Inject
    HrDepartments hrDepartments;
    @Inject
    HrTdTrainerProfiles hrTdTrainerProfiles;
    @Inject
    HrTdAnnualTraParticipants hrTdAnnualTraParticipants;
    @Inject
    HrEmployees hrEmployees;
    @Inject
    SessionBean sessionBeanLocal;

    @EJB
    WfHrProcessedBeanLocal wfHrProcessedBeanLocal;
    @EJB
    HrTdAnnualNeedRequestsBeanLocal hrTdAnulNeedReqstsBnLocal;
    @EJB
    HrTrainingCoursesBeanLocal hrTrainingCoursesBeanLocal;
    @EJB
    HrDepartmentsBeanLocal hrDepartmentsFacade;
    @EJB
    HrRecruitmentRequestsBeanLocal hrRecruitmentRequestsBeanLocal;
    @EJB
    HrTdTrainerProfilesFacade hrTdTrainerProfilesFacade;
    @EJB
    HrEmployeeBeanLocal hrEmployeeBeanLocal;
    @EJB
    HrTdAnnualTraParticipantsBeanLocal hrTdAnulTrPrtpantsBLocal;
    @EJB
    HrDepartmentsIntegrationBeanLocal departmentBeanLocal;

    public AnnualNeedRequestsController() {
    }

    private String createOrSearchBundle = "New";
    private String icone = "ui-icon-plus";
    private String disableBtn = "false";
    private boolean renderPnlCreateGatePass = true;
    private boolean renderPnlManPage = false;
    private boolean btnNewRender = false;
    private String renderpnlContrat = "false";
    private int updateStatus = 0;
    private String SaveOrUpdateButton = "Save";
    private String addorUpdate = "Add";
    private String addorUpdateForParticipant = "Add";
    String preparedDate = "";
    Set<String> checkCourse = new HashSet<>();
    Set<String> checkEmp = new HashSet<>();

    List<HrTdCourses> hrTdCoursesList = new ArrayList<>();
    List<HrLuTrainingCategory> hrLuTrainingCategoryList = new ArrayList<>();
    List<HrTdTrainerProfiles> hrTdTrainerProfilesList = new ArrayList<>();
    List<HrTdAnnualTrainingNeeds> hrTdAnnualTrainingNeedsList = new ArrayList<>();
    List<HrEmployees> hrEmployeesList = new ArrayList<>();
    DataModel<HrTdAnnualTrainingNeeds> hrTdAnnualTrainingNeedsDataModel = new ListDataModel<>();
    DataModel<HrTdAnnualNeedRequests> hrTdAnnualNeedRequestsDataModel = new ListDataModel<>();
    DataModel<HrTdAnnualTraParticipants> hrTdAnnulTraParticipantM = new ListDataModel<>();
    private HrTdAnnualNeedRequests selectedAnnualNeed;
    private HrTdAnnualTraParticipants selecteParticipant;
    List<SelectItem> filterByStatus = new ArrayList<>();
    DataModel<WfHrProcessed> hrWorkFlowDataModel = new ListDataModel<>();

    WorkFlow workFlow = new WorkFlow();
    Status statusWorkFlow = new Status();
    int selectedStatus;
    String processedOn = "";
    //</editor-fold>

    @PostConstruct
    public void init() {
        loadTree();
        setFilterByStatus(hrTdAnulNeedReqstsBnLocal.filterByStatus());
        setBudgetYears(readBudgetYears());
        hrLuTrainingCategoryList = hrTrainingCoursesBeanLocal.findAllCategory();
        hrTdTrainerProfilesList = hrTdAnulNeedReqstsBnLocal.hrTdTrainerProfilesList();
        wfHrProcessed.setProcessedOn(StringDateManipulation.toDayInEc());
        processedOn = dateFormat(StringDateManipulation.toDayInEc());
        preparedDate = dateFormat(StringDateManipulation.toDayInEc());
        //  setPreparedDate(StringDateManipulation.toDayInEc());
    }

    //<editor-fold defaultstate="collapsed" desc="authentication code (it should be removed)">
    private String fullName;

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="getter and setter">
    public String getProcessedOn() {
        return processedOn;
    }

    public void setProcessedOn(String processedOn) {
        this.processedOn = processedOn;
    }

    public HrTdAnnualNeedRequests getSelectedAnnualNeed() {
        return selectedAnnualNeed;
    }

    public void setSelectedAnnualNeed(HrTdAnnualNeedRequests selectedAnnualNeed) {
        this.selectedAnnualNeed = selectedAnnualNeed;
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

    public DataModel<WfHrProcessed> getHrWorkFlowDataModel() {
        return hrWorkFlowDataModel;
    }

    public void setHrWorkFlowDataModel(DataModel<WfHrProcessed> hrWorkFlowDataModel) {
        this.hrWorkFlowDataModel = hrWorkFlowDataModel;
    }

    public String getSaveOrUpdateButton() {
        return SaveOrUpdateButton;
    }

    public void setSaveOrUpdateButton(String SaveOrUpdateButton) {
        this.SaveOrUpdateButton = SaveOrUpdateButton;
    }

    public HrTdAnnualNeedRequests getHrTdAnnualNeedRequests() {
        if (hrTdAnnualNeedRequests == null) {
            hrTdAnnualNeedRequests = new HrTdAnnualNeedRequests();
        }
        return hrTdAnnualNeedRequests;
    }

    public void setHrTdAnnualNeedRequests(HrTdAnnualNeedRequests hrTdAnnualNeedRequests) {
        this.hrTdAnnualNeedRequests = hrTdAnnualNeedRequests;
    }

    public HrTdAnnualNeedRequests getSrcAnnualNeedRequests() {
        if (srcAnnualNeedRequests == null) {
            srcAnnualNeedRequests = new HrTdAnnualNeedRequests();
        }
        return srcAnnualNeedRequests;
    }

    public void setSrcAnnualNeedRequests(HrTdAnnualNeedRequests srcAnnualNeedRequests) {
        this.srcAnnualNeedRequests = srcAnnualNeedRequests;
    }

    public HrTdAnnualTrainingNeeds getHrTdAnnualTrainingNeeds() {
        if (hrTdAnnualTrainingNeeds == null) {
            hrTdAnnualTrainingNeeds = new HrTdAnnualTrainingNeeds();
        }
        return hrTdAnnualTrainingNeeds;
    }

    public void setHrTdAnnualTrainingNeeds(HrTdAnnualTrainingNeeds hrTdAnnualTrainingNeeds) {
        this.hrTdAnnualTrainingNeeds = hrTdAnnualTrainingNeeds;
    }

    public String getIcone() {
        return icone;
    }

    public void setIcone(String icone) {
        this.icone = icone;
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

    public String getDisableBtn() {
        return disableBtn;
    }

    public void setDisableBtn(String disableBtn) {
        this.disableBtn = disableBtn;
    }

    public List<SelectItem> getFilterByStatus() {
        return filterByStatus;
    }

    public void setFilterByStatus(List<SelectItem> filterByStatus) {
        this.filterByStatus = filterByStatus;
    }

    public String getRenderpnlContrat() {
        return renderpnlContrat;
    }

    public void setRenderpnlContrat(String renderpnlContrat) {
        this.renderpnlContrat = renderpnlContrat;
    }

    public int getUpdateStatus() {
        return updateStatus;
    }

    public void setUpdateStatus(int updateStatus) {
        this.updateStatus = updateStatus;
    }

    public String getCreateOrSearchBundle() {
        return createOrSearchBundle;
    }

    public void setCreateOrSearchBundle(String createOrSearchBundle) {
        this.createOrSearchBundle = createOrSearchBundle;
    }

    public List<HrTdCourses> getHrTdCoursesList() {
        return hrTdCoursesList;
    }

    public void setHrTdCoursesList(List<HrTdCourses> hrTdCoursesList) {
        this.hrTdCoursesList = hrTdCoursesList;
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

    public HrTdCourses getHrTdCourses() {
        if (hrTdCourses == null) {
            hrTdCourses = new HrTdCourses();
        }
        return hrTdCourses;
    }

    public void setHrTdCourses(HrTdCourses hrTdCourses) {
        this.hrTdCourses = hrTdCourses;
    }

    public List<HrLuTrainingCategory> getHrLuTrainingCategoryList() {
        return hrLuTrainingCategoryList;
    }

    public void setHrLuTrainingCategoryList(List<HrLuTrainingCategory> hrLuTrainingCategoryList) {
        this.hrLuTrainingCategoryList = hrLuTrainingCategoryList;
    }

    public HrTdTrainerProfiles getHrTdTrainerProfiles() {
        if (hrTdTrainerProfiles == null) {
            hrTdTrainerProfiles = new HrTdTrainerProfiles();
        }
        return hrTdTrainerProfiles;
    }

    public void setHrTdTrainerProfiles(HrTdTrainerProfiles hrTdTrainerProfiles) {
        this.hrTdTrainerProfiles = hrTdTrainerProfiles;
    }

    public List<HrTdTrainerProfiles> getHrTdTrainerProfilesList() {
        return hrTdTrainerProfilesList;
    }

    public void setHrTdTrainerProfilesList(List<HrTdTrainerProfiles> hrTdTrainerProfilesList) {
        this.hrTdTrainerProfilesList = hrTdTrainerProfilesList;
    }

    public List<HrTdAnnualTrainingNeeds> getHrTdAnnualTrainingNeedsList() {
        return hrTdAnnualTrainingNeedsList;
    }

    public void setHrTdAnnualTrainingNeedsList(List<HrTdAnnualTrainingNeeds> hrTdAnnualTrainingNeedsList) {
        this.hrTdAnnualTrainingNeedsList = hrTdAnnualTrainingNeedsList;
    }

    public DataModel<HrTdAnnualTrainingNeeds> getHrTdAnnualTrainingNeedsDataModel() {
        if (hrTdAnnualTrainingNeedsDataModel == null) {
            hrTdAnnualTrainingNeedsDataModel = new ArrayDataModel<>();
        }
        return hrTdAnnualTrainingNeedsDataModel;
    }

    public void setHrTdAnnualTrainingNeedsDataModel(DataModel<HrTdAnnualTrainingNeeds> hrTdAnnualTrainingNeedsDataModel) {
        this.hrTdAnnualTrainingNeedsDataModel = hrTdAnnualTrainingNeedsDataModel;
    }

    public DataModel<HrTdAnnualTraParticipants> getHrTdAnnulTraParticipantM() {
        if (hrTdAnnulTraParticipantM == null) {
            hrTdAnnulTraParticipantM = new ListDataModel<>();
        }
        return hrTdAnnulTraParticipantM;
    }

    public void setHrTdAnnulTraParticipantM(DataModel<HrTdAnnualTraParticipants> hrTdAnnulTraParticipantM) {
        this.hrTdAnnulTraParticipantM = hrTdAnnulTraParticipantM;
    }

    public HrTdAnnualTraParticipants getHrTdAnnualTraParticipants() {
        if (hrTdAnnualTraParticipants == null) {
            hrTdAnnualTraParticipants = new HrTdAnnualTraParticipants();
        }
        return hrTdAnnualTraParticipants;
    }

    public void setHrTdAnnualTraParticipants(HrTdAnnualTraParticipants hrTdAnnualTraParticipants) {
        this.hrTdAnnualTraParticipants = hrTdAnnualTraParticipants;
    }

    public String getPreparedDate() {
        return preparedDate;
    }

    public void setPreparedDate(String preparedDate) {
        this.preparedDate = preparedDate;
    }

    public List<HrEmployees> getHrEmployeesList() {
        return hrEmployeesList;
    }

    public void setHrEmployeesList(List<HrEmployees> hrEmployeesList) {
        this.hrEmployeesList = hrEmployeesList;
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
        return hrDepartments;
    }

    public void setHrDepartments(HrDepartments hrDepartments) {
        this.hrDepartments = hrDepartments;
    }

    public DataModel<HrTdAnnualNeedRequests> getHrTdAnnualNeedRequestsDataModel() {
        return hrTdAnnualNeedRequestsDataModel;
    }

    public void setHrTdAnnualNeedRequestsDataModel(DataModel<HrTdAnnualNeedRequests> hrTdAnnualNeedRequestsDataModel) {
        this.hrTdAnnualNeedRequestsDataModel = hrTdAnnualNeedRequestsDataModel;
    }

    public HrTdAnnualTraParticipants getSelecteParticipant() {
        return selecteParticipant;
    }

    public void setSelecteParticipant(HrTdAnnualTraParticipants selecteParticipant) {
        this.selecteParticipant = selecteParticipant;
    }

    public String getAddorUpdate() {
        return addorUpdate;
    }

    public void setAddorUpdate(String addorUpdate) {
        this.addorUpdate = addorUpdate;
    }

    public String getAddorUpdateForParticipant() {
        return addorUpdateForParticipant;
    }

    public void setAddorUpdateForParticipant(String addorUpdateForParticipant) {
        this.addorUpdateForParticipant = addorUpdateForParticipant;
    }

    //  </editor-fold>
    //<editor-fold defaultstate="collapsed" desc="department dialoge">
    private TreeNode root;
    private TreeNode selectedNode;
    private List<HrDepartments> allDepartments;
    private static List<HrDepartments> departments;

    //<editor-fold defaultstate="collapsed" desc="getters & setters">
    public TreeNode getRoot() {
        return root;
    }

    public void setRoot(TreeNode root) {
        this.root = root;
    }

    public TreeNode getSelectedNode() {
        return selectedNode;
    }

    public void setSelectedNode(TreeNode selectedNode) {
        this.selectedNode = selectedNode;
    }

    public List<HrDepartments> getAllDepartments() {
        return allDepartments;
    }

    public void setAllDepartments(List<HrDepartments> allDepartments) {
        this.allDepartments = allDepartments;
    }
    //</editor-fold>

    public void loadTree() {
        allDepartments = departmentBeanLocal.findAllDepartmentInfo();
        root = new DefaultTreeNode("Root", null);
        populateNodes(allDepartments, 0, root);
    }

    public void populateNodes(List<HrDepartments> depts, int id, TreeNode node) {
        departments = new ArrayList<>();
        if (getAllDepartments() != null) {
            for (HrDepartments k : getAllDepartments()) {
                if (k.getParentId() != null && k.getParentId() == id) {
                    TreeNode childNode = new DefaultTreeNode(k.getDepId() + "--" + k.getDepName(), node);
                    departments.add(k);
                    populateNodes(departments, k.getDepId(), childNode);
                }
            }
        }
    }

    public void onNodeSelect(NodeSelectEvent event) {
        selectedNode = event.getTreeNode();
        int deptId = Integer.parseInt((selectedNode.getData().toString()).split("--")[0]);
        hrDepartments = hrRecruitmentRequestsBeanLocal.getSelectDepartement(deptId);
        hrTdAnnualNeedRequests.setDeptId(hrDepartments);
        RequestContext context2 = RequestContext.getCurrentInstance();
        context2.execute("PF('dlg1').hide();");
    }
//</editor-fold>

    //  <editor-fold defaultstate="collapsed" desc="actions">
    public void createNewGatePassInfo() {
        SaveOrUpdateButton = "Save";
        if (createOrSearchBundle.equals("New")) {
            renderPnlCreateGatePass = false;
            renderPnlManPage = true;
            btnNewRender = false;
            createOrSearchBundle = "Search";
            setIcone("ui-icon-search");
        } else if (createOrSearchBundle.equals("Search")) {
            renderPnlCreateGatePass = true;
            renderPnlManPage = false;
            btnNewRender = false;
            createOrSearchBundle = "New";
            setIcone("ui-icon-plus");
        }
    }

    public void newButtonAction() {
        clearPage();
        renderPnlManPage = true;
        renderPnlCreateGatePass = false;
        btnNewRender = false;
    }

    public void newPage() {
        renderPnlCreateGatePass = false;
        renderPnlManPage = true;
        hrTdAnnualNeedRequests = null;
        hrDepartments = null;
        hrTdAnnualTrainingNeeds = null;
        hrTdAnnualTrainingNeedsDataModel = null;
        hrTdAnnulTraParticipantM = null;
        disableBtn = "false";
        updateStatus = 0;
        SaveOrUpdateButton = "Save";
    }

    public void searchPage() {
        renderPnlCreateGatePass = true;
        renderPnlManPage = false;
        srcAnnualNeedRequests = null;
        hrTdAnnualNeedRequestsDataModel = null;
    }

    public SelectItem[] getstatus() {
        return JsfUtil.getSelectItems(status(), true);
    }

    public SelectItem[] getFilterByStatusForRequest() {
        SelectItem[] items = new SelectItem[4];
        items[0] = new SelectItem("null", "--- Select ---");
        items[1] = new SelectItem(Constants.PREPARE_VALUE, "Load Request List");
        items[2] = new SelectItem(Constants.APPROVE_VALUE, "Load Approved List");
        items[3] = new SelectItem(Constants.APPROVE_REJECT_VALUE, "Load Rejected List");
        return items;
    }

    private List<HrTdAnnualNeedRequests> trainingRequestList = new ArrayList<>();

    public List<HrTdAnnualNeedRequests> getTrainingRequestList() {
        if (trainingRequestList == null) {
            trainingRequestList = new ArrayList();
        }
        return trainingRequestList;
    }

    public void setTrainingRequestList(List<HrTdAnnualNeedRequests> trainingRequestList) {
        this.trainingRequestList = trainingRequestList;
    }

    private void populateRequestTable(Status _status) {
        try {
            trainingRequestList = hrTdAnulNeedReqstsBnLocal.loadTrainingRequestList(_status);
            hrTdAnnualNeedRequestsDataModel = new ListDataModel<>(trainingRequestList);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void populateRequestTableForTwoStatus(Status _status) {
        try {
            trainingRequestList = hrTdAnulNeedReqstsBnLocal.loadTrainingRequestListForTwo(_status);
            hrTdAnnualNeedRequestsDataModel = new ListDataModel<>(trainingRequestList);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void valueChangeListnerTrainingRequest(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            selectedStatus = Integer.valueOf(String.valueOf(event.getNewValue()));
            if (selectedStatus == Constants.PREPARE_VALUE) {
                statusWorkFlow.setStatus1(Constants.PREPARE_VALUE);
                populateRequestTable(statusWorkFlow);
            } else if (selectedStatus == Constants.APPROVE_VALUE) {
                statusWorkFlow.setStatus1(Constants.APPROVE_VALUE);
                statusWorkFlow.setStatus2(Constants.CHECK_APPROVE_VALUE);
                populateRequestTableForTwoStatus(statusWorkFlow);
            } else if (selectedStatus == Constants.APPROVE_REJECT_VALUE) {
                statusWorkFlow.setStatus1(Constants.APPROVE_REJECT_VALUE);
                statusWorkFlow.setStatus2(Constants.CHECK_REJECT_VALUE);
                populateRequestTableForTwoStatus(statusWorkFlow);
            }
        }
    }

    public ArrayList<String> status() {
        ArrayList<String> statusList = new ArrayList<>();
        statusList.add("Request");
        statusList.add("Approved");
        statusList.add("Rejected");
        return statusList;
    }

    private List<SelectItem> budgetYears;

    public List<SelectItem> getBudgetYears() {
        return budgetYears;
    }

    public void setBudgetYears(List<SelectItem> budgetYears) {
        this.budgetYears = budgetYears;
    }

    public List<SelectItem> readBudgetYears() {
        List<SelectItem> budgetYear = new ArrayList<>();
        String[] toDay = StringDateManipulation.toDayInEc().split("-");
        int year = Integer.valueOf(toDay[0]) + 1;
        while (year > 2000) {
            budgetYear.add(new SelectItem(String.valueOf(year), String.valueOf(year - 1) + "/" + String.valueOf(year)));
            year--;
        }
        return budgetYear;
    }

    public void somHrTdCoursesList(ValueChangeEvent event) {
        hrLuTrainingCategory.setId(Integer.parseInt(event.getNewValue().toString()));
        hrTdCourses.setCategoryId(hrLuTrainingCategory);
        hrTdCoursesList = hrTrainingCoursesBeanLocal.findByCatagory(hrTdCourses);
    }

    public void getCourse(ValueChangeEvent event) {
        hrTdAnnualTrainingNeeds.setTrainingId(hrTdCourses);
    }

    public void somGetInstitute(ValueChangeEvent event) {
        BigDecimal Ins = new BigDecimal(event.getNewValue().toString());
        hrTdTrainerProfiles.setId(Ins);
        hrTdTrainerProfiles = hrTdTrainerProfilesFacade.findInstituetId(hrTdTrainerProfiles);
        hrTdAnnualTrainingNeeds.setInstitutionId(hrTdTrainerProfiles);
    }

    public void populateAnnaulTrain(SelectEvent event) {
        hrTdAnnualNeedRequests = (HrTdAnnualNeedRequests) event.getObject();
        hrDepartments = hrTdAnnualNeedRequests.getDeptId();
        if (hrTdAnnualNeedRequests.getStatus().equalsIgnoreCase("0")) {
            disableBtn = "false";
        } else {
            disableBtn = "true";
        }
        renderPnlCreateGatePass = false;
        renderPnlManPage = true;
        btnNewRender = true;
        updateStatus = 1;
        SaveOrUpdateButton = "Update";
        createOrSearchBundle = "Search";
        setIcone("ui-icon-search");
        readBudgetYears();
        recreateTrainingNeed();
        recreateParticipant();
        recreatWorkFlowDataModel();
    }

    int status = 0;
    int bYear = 0;

    private void populateTable() {
        try {
            List<HrTdAnnualNeedRequests> filteredEvaCriteria = new ArrayList<>();
            List<HrTdAnnualNeedRequests> readAnnualNeed = hrTdAnulNeedReqstsBnLocal.searchAnnualNeed(status, bYear);
            for (HrTdAnnualNeedRequests requestStatus : readAnnualNeed) {
                if (Integer.valueOf(requestStatus.getStatus()) == 1 && Integer.valueOf(requestStatus.getStatus()) == 3) {
                    requestStatus.setStatus(String.valueOf("Approved"));
                } else if (Integer.valueOf(requestStatus.getStatus()) == 0) {
                    requestStatus.setStatus(String.valueOf("Request"));
                } else if (Integer.valueOf(requestStatus.getStatus()) == 2 && Integer.valueOf(requestStatus.getStatus()) == 4) {
                    requestStatus.setStatus(String.valueOf("Rejected"));
                }
            }
            hrTdAnnualNeedRequestsDataModel = new ListDataModel(filteredEvaCriteria);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void searchByStatus(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            status = Integer.valueOf(event.getNewValue().toString());
            populateTable();
        }
    }

    public void searchByYear(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            bYear = Integer.valueOf(event.getNewValue().toString());
            populateTable();
        }
    }

    public void editTrainingNeed(SelectEvent event) {
        hrTdAnnualTrainingNeeds = (HrTdAnnualTrainingNeeds) event.getObject();
        hrTdTrainerProfilesList = hrTdAnulNeedReqstsBnLocal.hrTdTrainerProfilesList();
        hrTdTrainerProfiles = hrTdAnnualTrainingNeeds.getInstitutionId();
        hrLuTrainingCategoryList = hrTrainingCoursesBeanLocal.findAllCategory();
        hrTdCourses.setId(hrTdAnnualTrainingNeeds.getTrainingId().getId());
        hrLuTrainingCategory = hrTdCourses.getCategoryId();
    }

    public void selectTrainNeed(SelectEvent event) {
        selectedRowIndexNeed = getHrTdAnnualTrainingNeedsDataModel().getRowIndex();
        hrTdAnnualTrainingNeeds = (HrTdAnnualTrainingNeeds) event.getObject();
        hrTdAnnualTraParticipants.setAnnTraNeedId(hrTdAnnualTrainingNeeds);
        hrTdTrainerProfilesList = hrTdAnulNeedReqstsBnLocal.hrTdTrainerProfilesList();
        hrTdTrainerProfiles = hrTdAnnualTrainingNeeds.getInstitutionId();
        hrLuTrainingCategoryList = hrTrainingCoursesBeanLocal.findAllCategory();
        hrTdCourses.setId(hrTdAnnualTrainingNeeds.getTrainingId().getId());
        hrLuTrainingCategory = hrTdCourses.getCategoryId();
        hrTdAnnulTraParticipantM = new ListDataModel(new ArrayList(hrTdAnulTrPrtpantsBLocal.SearchByAnnTraNeedId(hrTdAnnualTraParticipants)));
        SaveOrUpdateButton = "Update";
    }

    public void recreateTrainingNeed() {
        hrTdAnnualTrainingNeedsDataModel = null;
        hrTdAnnualTrainingNeedsDataModel = new ListDataModel(new ArrayList<>(hrTdAnnualNeedRequests.getHrTdAnnualTrainingNeedsList()));
    }

    private boolean checkTrainingNeedDuplicate() {
        for (HrTdAnnualTrainingNeeds need : hrTdAnnualNeedRequests.getHrTdAnnualTrainingNeedsList()) {
            if (need.getTrainingId().getId() != null && need.getTentativeStartDate() != null && need.getTentativeEndDate() != null) {
                if (need.getTrainingId().getId().toString().equalsIgnoreCase(hrTdAnnualTrainingNeeds.getTrainingId().getId().toString())
                        && need.getTentativeStartDate().equalsIgnoreCase(hrTdAnnualTrainingNeeds.getTentativeStartDate())
                        && need.getTentativeEndDate().equalsIgnoreCase(hrTdAnnualTrainingNeeds.getTentativeEndDate())) {
                    return false;
                }
            }
        }
        return true;
    }
    int selectedRowIndexNeed = -1;

    public void addTrainingNeed() {
        hrTdAnnualTrainingNeeds.setInstitutionId(hrTdTrainerProfiles);
        hrTdAnnualTrainingNeeds.setTrainingId(hrTdCourses);
        if (selectedRowIndexNeed > -1) {
            hrTdAnnualTrainingNeeds.setId(hrTdAnnualNeedRequests.getHrTdAnnualTrainingNeedsList().get(selectedRowIndexNeed).getId());
            hrTdAnnualNeedRequests.getHrTdAnnualTrainingNeedsList().remove(selectedRowIndexNeed);
            if (checkTrainingNeedDuplicate()) {
                hrTdAnnualNeedRequests.getHrTdAnnualTrainingNeedsList().add(selectedRowIndexNeed, hrTdAnnualTrainingNeeds);
            } else {
                JsfUtil.addFatalMessage("Duplicate Entry. Try again!");
            }
        } else {
            if (checkTrainingNeedDuplicate()) {
                hrTdAnnualNeedRequests.addTrainingNeed(hrTdAnnualTrainingNeeds);
                recreateTrainingNeed();
                clearTrainingNeed();
            } else {
                JsfUtil.addFatalMessage("Duplicate Entry. Try again!");
            }
        }
        selectedRowIndexNeed = -1;
    }

    public void clearTrainingNeed() {
        hrLuTrainingCategory = null;
        hrTdAnnualTrainingNeeds = null;
        hrTdCourses = null;
        hrTdTrainerProfiles = null;
    }

    int dateDifference;

    public void dateValidation() {
        String startMonth[] = hrTdAnnualTrainingNeeds.getTentativeStartDate().split("/");
        int isSartMonth = Integer.parseInt(startMonth[1]);
        String startYear[] = hrTdAnnualTrainingNeeds.getTentativeStartDate().split("/");
        int isStartYear = Integer.parseInt(startYear[2]);
        String startDate[] = hrTdAnnualTrainingNeeds.getTentativeStartDate().split("/");
        int isStartDate = Integer.parseInt(startDate[0]);
        String endDay[] = hrTdAnnualTrainingNeeds.getTentativeEndDate().split("/");
        int isEndDay = Integer.parseInt(endDay[0]);
        String endMonth[] = hrTdAnnualTrainingNeeds.getTentativeEndDate().split("/");
        int isEndMonth = Integer.parseInt(endMonth[1]);
        String endYear[] = hrTdAnnualTrainingNeeds.getTentativeEndDate().split("/");
        int isEndYear = Integer.parseInt(endYear[2]);
        dateDifference = ((isEndDay - isStartDate) + ((isEndMonth - isSartMonth) * 30) + ((isEndYear - isStartYear) * 365));
    }

    public void addAnnualNeed() {
        //dateValidation();
        if (dateDifference < 0) {
            JsfUtil.addFatalMessage("End date should be greater than Start date. Try again!");
        } else if (checkCourse.contains(hrTdAnnualTrainingNeeds.getTrainingId().getCourseName())) {
            JsfUtil.addFatalMessage("Duplicate Entry. Try again!");
        } else {
            hrTdAnnualTrainingNeeds.setInstitutionId(hrTdTrainerProfiles);
            hrTdAnnualNeedRequests.addTrainingNeed(hrTdAnnualTrainingNeeds);
            checkCourse.add(hrTdAnnualTrainingNeeds.getTrainingId().getCourseName());
            recreateTrainingNeed();
            clearTrainingNeed();
        }
    }

    public void editAnnaulNeedDataTable() {
        hrTdAnnualTrainingNeeds = hrTdAnnualTrainingNeedsDataModel.getRowData();
        hrTdTrainerProfiles = hrTdAnnualTrainingNeeds.getInstitutionId();
        hrTdCourses = hrTdAnnualTrainingNeeds.getTrainingId();
        hrTdCoursesList = hrTrainingCoursesBeanLocal.findByCatagory(hrTdCourses);
        setHrTdCourses(hrTdAnnualTrainingNeeds.getTrainingId());
        hrLuTrainingCategory = hrTdAnnualTrainingNeeds.getTrainingId().getCategoryId();
        addorUpdate = "Modify";
    }

    private boolean checkParticipantDuplicate() {
        for (HrTdAnnualTraParticipants partc : hrTdAnnualTrainingNeeds.getHrTdAnulTraPaticptsList()) {
            if (partc.getEmpId().getId() != null) {
                if (partc.getEmpId().getId().toString().equalsIgnoreCase(hrTdAnnualTraParticipants.getEmpId().getId().toString())) {
                    return false;
                }
            }
        }
        return true;
    }
    int selectedRowIndexParticipant = -1;

    public int getSelectedRowIndexParticipant() {
        return selectedRowIndexParticipant;
    }

    public void setSelectedRowIndexParticipant(int selectedRowIndexParticipant) {
        this.selectedRowIndexParticipant = selectedRowIndexParticipant;
    }

    public void addParticipant() {
        if (selectedRowIndexParticipant > -1) {
            hrTdAnnualTraParticipants.setId(hrTdAnnualTrainingNeeds.getHrTdAnulTraPaticptsList().get(selectedRowIndexParticipant).getId());
            hrTdAnnualTrainingNeeds.getHrTdAnulTraPaticptsList().remove(selectedRowIndexParticipant);
            if (checkParticipantDuplicate()) {
                hrTdAnnualTrainingNeeds.getHrTdAnulTraPaticptsList().add(selectedRowIndexParticipant, hrTdAnnualTraParticipants);
            } else {
                JsfUtil.addFatalMessage("Duplicate Entry. Try again!");
            }
        } else {
            if (checkParticipantDuplicate()) {
                hrTdAnnualTrainingNeeds.addParticipantEmp(hrTdAnnualTraParticipants);
                recreateParticipant();
                clearParticipant();
            } else {
                JsfUtil.addFatalMessage("Duplicate Entry. Try again!");
            }
        }
        selectedRowIndexParticipant = -1;
    }

    public void addDetail() {
        hrTdAnnualTrainingNeeds = hrTdAnnualTrainingNeedsDataModel.getRowData();
        recreateParticipant();
        hrTdCourses = hrTdAnnualTrainingNeeds.getTrainingId();
        hrTdCoursesList = hrTrainingCoursesBeanLocal.findByCatagory(hrTdCourses);
        setHrTdCourses(hrTdAnnualTrainingNeeds.getTrainingId());
    }

    public void addEmp() {
        if (checkEmp.contains(hrEmployees.getEmpId())) {
            JsfUtil.addFatalMessage("Duplicate Entry. Try again!");
        } else {
            hrTdAnnualTrainingNeeds.addParticipantEmp(hrTdAnnualTraParticipants);
            hrTdAnnualTraParticipants.setStatus("0");
            checkEmp.add(hrEmployees.getEmpId());
            recreateParticipant();
            clearParticipant();
        }
    }

    public void editParticipantDataTable() {
        hrTdAnnualTraParticipants = hrTdAnnulTraParticipantM.getRowData();
        hrEmployees = hrTdAnnualTraParticipants.getEmpId();
        addorUpdateForParticipant = "Modify";
    }

    public void deleteParticipant() {
        hrTdAnnualTraParticipants = getHrTdAnnulTraParticipantM().getRowData();
        hrTdAnnualTrainingNeeds.getHrTdAnulTraPaticptsList().remove(hrTdAnnualTraParticipants);
        hrTdAnulNeedReqstsBnLocal.delete(hrTdAnnualTraParticipants);
        recreateParticipant();
        JsfUtil.addSuccessMessage("Successfully Deleted");
    }

    public void clearParticipant() {
        hrEmployees = null;
        hrTdAnnualTraParticipants = null;
    }

    public void recreateParticipant() {
        hrTdAnnulTraParticipantM = null;
        hrTdAnnulTraParticipantM = new ListDataModel(new ArrayList<>(hrTdAnnualTrainingNeeds.getHrTdAnulTraPaticptsList()));
    }

    public void populateParticipant(SelectEvent event) {
        selectedRowIndexParticipant = getHrTdAnnulTraParticipantM().getRowIndex();
        hrTdAnnualTraParticipants = (HrTdAnnualTraParticipants) event.getObject();
        hrEmployees = hrTdAnnualTraParticipants.getEmpId();
    }

    public ArrayList<HrEmployees> searchByEmpId(String EmpId) {
        ArrayList<HrEmployees> employees = new ArrayList<>();
        hrEmployees.setEmpId(EmpId);
        employees = hrEmployeeBeanLocal.SearchByEmpId(hrEmployees);
        return employees;
    }

    public void getByEmpId(SelectEvent event) {
        hrEmployees.setEmpId(event.getObject().toString());
        hrEmployees = hrEmployeeBeanLocal.getByEmpId(hrEmployees);
        hrTdAnnualTraParticipants.setEmpId(hrEmployees);
    }

    public ArrayList<HrEmployees> searchByName(String employee) {
        ArrayList<HrEmployees> emp = null;
        hrEmployees.setFirstName(employee);
        emp = hrEmployeeBeanLocal.SearchByFname(hrEmployees);
        return emp;
    }

    public void getByEmpName(SelectEvent event) {
        try {
            hrEmployees = (HrEmployees) event.getObject();
//            hrEmployees.setFirstName(event.getObject().toString());
//            hrEmployees = hrEmployeeBeanLocal.getByFirstName(hrEmployees);
            hrTdAnnualTraParticipants.setEmpId(hrEmployees);
        } catch (Exception ex) {
            System.err.println("Exception");
        }
    }

    public ArrayList<HrEmployees> searchEmployeeById(String empId) {
        ArrayList<HrEmployees> emp = null;
        hrEmployees.setEmpId(empId);
        emp = hrEmployeeBeanLocal.SearchByEmpId(hrEmployees);
        return emp;
    }

    public void getById(SelectEvent event) {
        try {
            hrEmployees.setEmpId(event.getObject().toString());
            hrEmployees = hrEmployeeBeanLocal.getByEmpId(hrEmployees);
            hrTdAnnualTraParticipants.setEmpId(hrEmployees);
        } catch (Exception ex) {
            System.err.println("Exception");
        }
    }

    private String disableEmpID = "false";
    private String disableEmpName = "true";
    String selected = "Employee ID";

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

    public String getSelected() {
        return selected;
    }

    public void setSelected(String selected) {
        this.selected = selected;
    }

    public void searchCriteria(ValueChangeEvent event) {
        if (null != event.getNewValue()) {
            selected = event.getNewValue().toString();
            if (selected.equalsIgnoreCase("Employee ID")) {
                disableEmpID = "false";
                disableEmpName = "true";
            } else {
                disableEmpID = "true";
                disableEmpName = "false";
            }
        }
    }

    public void saveOrUpdate() {
        hrTdAnulNeedReqstsBnLocal.saveOrUpdate(hrTdAnnualNeedRequests);
        clearPage();
        JsfUtil.addSuccessMessage("Successfully Saved");
    }

    public ArrayList<HrTdAnnualNeedRequests> findListByYear_Dept(ValueChangeEvent event) {
        srcAnnualNeedRequests.setBudgetYear(Integer.valueOf(event.getNewValue().toString()));
        hrTdAnnualNeedRequestsDataModel = new ListDataModel(new ArrayList<>(hrTdAnulNeedReqstsBnLocal.searchByBugdetYear(srcAnnualNeedRequests)));
        return null;
    }
    //  </editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Main">
    public List<HrTdAnnualNeedRequests> createAnnualNeed(int size) {
        List<HrTdAnnualNeedRequests> list = new ArrayList<HrTdAnnualNeedRequests>();
        for (int i = 0; i < size; i++) {
            list.add(new HrTdAnnualNeedRequests(hrTdAnnualNeedRequests.getId()));
        }
        return list;
    }

    public void saved() {
        if (hrTdAnnualNeedRequests.getDeptId() == null) {
            JsfUtil.addFatalMessage("Department Can't be empty. Try again!");
        } else if (hrTdAnnualTrainingNeedsDataModel.getRowCount() <= 0) {
            JsfUtil.addFatalMessage("Data table shoud be filled");
        } else {
            try {
                hrTdAnnualNeedRequests.setStatus("Request");
                hrTdAnnualNeedRequests.setPreparedOn(preparedDate);
                hrTdAnulNeedReqstsBnLocal.saveOrUpdate(hrTdAnnualNeedRequests);
                JsfUtil.addSuccessMessage("Successfully Saved");
                clearPage();
            } catch (Exception ex) {
                ex.printStackTrace();
                JsfUtil.addFatalMessage("Error occurs while saving");
            }
        }
    }

    public void saveAnnualNeedRqts() {
        try {
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(sessionBeanLocal.getUserName(), "saveAnnualNeedRqts", dataset)) {
                if (hrTdAnnualNeedRequests.getDeptId() != null) {
                    boolean checkDuplication = hrTdAnulNeedReqstsBnLocal.isRequstExist(hrTdAnnualNeedRequests);
                    if (hrTdAnnualTrainingNeedsDataModel.getRowCount() <= 0) {
                        JsfUtil.addFatalMessage("Training Need Data table shoud be filled");
                    } else if (hrTdAnnulTraParticipantM.getRowCount() <= 0) {
                        JsfUtil.addFatalMessage("Participant Data table should be filled");
                    } else if (!hrTdAnnualTrainingNeeds.getNoOfNominee().equals(hrTdAnnulTraParticipantM.getRowCount())) {
                        JsfUtil.addFatalMessage("Number of Nominee must be equal with Participant! Try again!");
                    } else {
                        if (updateStatus == 0 && checkDuplication == false) {
                            try {
                                hrTdAnnualTrainingNeeds.setAnnualNeedRequestId(hrTdAnnualNeedRequests);
                                hrTdAnnualNeedRequests.setStatus(String.valueOf(Constants.PREPARE_VALUE));
                                hrTdAnnualNeedRequests.setPreparedBy(sessionBeanLocal.getUserId());
                                hrTdAnnualNeedRequests.setPreparedOn(dateFormat(StringDateManipulation.toDayInEc()));
                                hrTdAnnualTrainingNeeds.setStatus(Constants.PREPARE_VALUE);
                                hrTdAnnualTraParticipants.setStatus(String.valueOf(Constants.PREPARE_VALUE));
                                hrTdAnulNeedReqstsBnLocal.save(hrTdAnnualNeedRequests);
                                wfHrProcessed.setAnnualTrainingId(hrTdAnnualNeedRequests);
                                wfHrProcessed.setProcessedBy(sessionBeanLocal.getUserId());
                                wfHrProcessed.setDecision(Constants.PREPARE_VALUE);
                                wfHrProcessed.setProcessedOn(dateFormat(StringDateManipulation.toDayInEc()));
                                wfHrProcessedBeanLocal.saveOrUpdate(wfHrProcessed);
                                JsfUtil.addSuccessMessage("Successfully Saved");
                                clearPage();
                            } catch (Exception ex) {
                                JsfUtil.addFatalMessage("Error occurs while saving");
                            }
                        } else if (updateStatus == 0 && checkDuplication == true) {
                            JsfUtil.addFatalMessage("Already request. Try again!");
                        } else {
                            try {
                                hrTdAnulNeedReqstsBnLocal.edit(hrTdAnnualNeedRequests);
                                JsfUtil.addSuccessMessage("Successfully Updated");
                                clearPage();
                                SaveOrUpdateButton = "Save";
                            } catch (Exception ex) {
                                JsfUtil.addFatalMessage("Error occurs while updating");
                            }
                        }
                    }
                } else {
                    JsfUtil.addFatalMessage("Department Can't be empty. Try again!");
                }
            } else {
                JsfUtil.addFatalMessage("You don't have appropriate permission, please contact the system administrator. ");
                EventEntry eventEntry = new EventEntry();
                eventEntry.setSessionId(sessionBeanLocal.getSessionID());
                eventEntry.setUserId(sessionBeanLocal.getUserId());
                QName qualifiedName = new QName("", "project");
                JAXBElement<String> userName = new JAXBElement<String>(qualifiedName, String.class, null, sessionBeanLocal.getUserName());
                eventEntry.setUserLogin(userName);
                JAXBElement<String> module = new JAXBElement<String>(qualifiedName, String.class, null, "HRMS");
                eventEntry.setModule(module);
                JAXBElement<String> program = new JAXBElement<String>(qualifiedName, String.class, null, "saveAnnualNeedRqts");
                eventEntry.setProgram(program);
                security.addEventLog(eventEntry, dataset);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public void clearPage() {
        hrTdAnnualNeedRequests = null;
        hrDepartments = null;
        hrTdAnnualTrainingNeeds = null;
        hrTdAnnualTrainingNeedsDataModel = null;
        hrTdAnnulTraParticipantM = null;
        preparedDate = null;
        wfHrProcessed.setCommentGiven(null);
        wfHrProcessed = new WfHrProcessed();
        SaveOrUpdateButton = "Save";
    }

    public void reset() {
        hrTdAnnualNeedRequests = null;
        hrDepartments = null;
        hrTdAnnualTrainingNeedsDataModel = null;
        updateStatus = 0;
        SaveOrUpdateButton = "Save";
    }

    public String dateFormat(String date) {
        String[] dateFromUI;
        String dateInDB;
        if (date != null && date.contains("-")) {
            dateFromUI = date.split("-");
            dateInDB = dateFromUI[2] + "/" + dateFromUI[1] + "/" + dateFromUI[0];
            return dateInDB;
        }
        return null;
    }

    //  </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Work Flow ">
    String selectedValue = "";

    public void handleselectOptions(ValueChangeEvent event) {
        if (null != event.getNewValue()) {
            selectedValue = event.getNewValue().toString();
        }
    }

    public void recreatWorkFlowDataModel() {
        hrWorkFlowDataModel = null;
        for (int i = 0; i < hrTdAnnualNeedRequests.getHrWorkFlowAnnualRequestList().size(); i++) {
            if (hrTdAnnualNeedRequests.getHrWorkFlowAnnualRequestList().get(i).getDecision() == 0) {
                hrTdAnnualNeedRequests.getHrWorkFlowAnnualRequestList().get(i).setChangedStstus("Request");
            } else if (hrTdAnnualNeedRequests.getHrWorkFlowAnnualRequestList().get(i).getDecision() == 1 || hrTdAnnualNeedRequests.getHrWorkFlowAnnualRequestList().get(i).getDecision() == 3) {
                hrTdAnnualNeedRequests.getHrWorkFlowAnnualRequestList().get(i).setChangedStstus("Approved");
            } else if (hrTdAnnualNeedRequests.getHrWorkFlowAnnualRequestList().get(i).getDecision() == 2 || hrTdAnnualNeedRequests.getHrWorkFlowAnnualRequestList().get(i).getDecision() == 4) {
                hrTdAnnualNeedRequests.getHrWorkFlowAnnualRequestList().get(i).setChangedStstus("Rejected");
            }
        }
        hrWorkFlowDataModel = new ListDataModel(new ArrayList(hrTdAnnualNeedRequests.getHrWorkFlowAnnualRequestList()));
    }

    // </editor-fold>
}
