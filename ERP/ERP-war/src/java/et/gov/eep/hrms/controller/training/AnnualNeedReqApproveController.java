/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.controller.training;

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
import et.gov.eep.commonApplications.entity.WfHrProcessed;
import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.commonApplications.utility.date.StringDateManipulation;
import et.gov.eep.hrms.businessLogic.recruitment.HrRecruitmentRequestsBeanLocal;
import et.gov.eep.hrms.businessLogic.training.HrTdAnnualNeedRequestsBeanLocal;
import et.gov.eep.hrms.businessLogic.training.HrTdAnnualTraParticipantsBeanLocal;
import et.gov.eep.hrms.entity.employee.HrEmployees;
import et.gov.eep.hrms.entity.organization.HrDepartments;
import et.gov.eep.hrms.entity.training.HrLuTrainingCategory;
import et.gov.eep.hrms.entity.training.HrTdAnnualNeedRequests;
import et.gov.eep.hrms.entity.training.HrTdAnnualTraParticipants;
import et.gov.eep.hrms.entity.training.HrTdAnnualTrainingNeeds;
import et.gov.eep.hrms.entity.training.HrTdCourses;
import et.gov.eep.hrms.entity.training.HrTdTrainerProfiles;
import et.gov.eep.hrms.mapper.training.HrTdAnnualTrainingNeedsFacade;

/**
 *
 * @author Benin
 */
@Named(value = "annualNeedReqApproveController")
@ViewScoped
public class AnnualNeedReqApproveController implements Serializable {

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
    @Inject
    WorkFlow workFlow;

    @EJB
    WfHrProcessedBeanLocal wfHrProcessedBeanLocal;
    @EJB
    HrTdAnnualNeedRequestsBeanLocal hrTdAnnualNeedRequestsBeanLocal;
    @EJB
    HrRecruitmentRequestsBeanLocal hrRecruitmentRequestsBeanLocal;
    @EJB
    HrTdAnnualTraParticipantsBeanLocal hrTdAnnualTraParticipantsBeanLocal;
    @EJB
    HrTdAnnualTrainingNeedsFacade hrTdAnnualTrainingNeedsFacade;

    public AnnualNeedReqApproveController() {
    }

    private String createOrSearchBundle = "New";
    private String icone = "ui-icon-document";
    private String disableBtnSave = "false";
    private boolean renderPnlCreateGatePass = true;
    private boolean renderPnlManPage = false;
    private String renderpnlContrat = "false";
    private int updateStatus = 0;
    private String SaveOrUpdateButton = "Save";
    private boolean renderPnlChecker = false;
    private boolean renderPnlAAprover = false;
    private boolean renderPnlNotChecker = true;
    private boolean renderPnlNotApprover = false;

    DataModel<HrTdAnnualTrainingNeeds> hrTdAnnualTrainingNeedsDataModel = new ListDataModel<>();
    DataModel<HrTdAnnualTraParticipants> hrTdAnnualTraParticipantsDataModel = new ListDataModel<>();
    DataModel<HrTdAnnualNeedRequests> hrTdAnnualNeedRequestsDataModel = new ListDataModel<>();
    DataModel<WfHrProcessed> hrWorkFlowDataModel = new ListDataModel<>();

    List<HrTdAnnualNeedRequests> checkerRequestList = new ArrayList<>();
    List<HrTdAnnualNeedRequests> approverRequestList = new ArrayList<>();
    String processedOn = "";
    //</editor-fold>

    @PostConstruct
    public void init() {
        if (workFlow.isCheckStatus()) {
            renderPnlNotChecker = true;
            renderPnlNotApprover = false;
            renderPnlChecker = true;
            renderPnlAAprover = false;
        } else {
            renderPnlNotChecker = false;
            renderPnlNotApprover = true;
            renderPnlAAprover = true;
            renderPnlChecker = false;
        }
        setBudgetYears(readBudgetYears());
        wfHrProcessed.setProcessedOn(StringDateManipulation.toDayInEc());
        checkerRequestList = hrTdAnnualNeedRequestsBeanLocal.findRequestForChecker();
        approverRequestList = hrTdAnnualNeedRequestsBeanLocal.findRequestForApproval();
        processedOn = dateFormat(StringDateManipulation.toDayInEc());
    }

    //<editor-fold defaultstate="collapsed" desc="getter Setter">  
    public String getProcessedOn() {
        return processedOn;
    }

    public void setProcessedOn(String processedOn) {
        this.processedOn = processedOn;
    }

    public boolean isRenderPnlChecker() {
        return renderPnlChecker;
    }

    public void setRenderPnlChecker(boolean renderPnlChecker) {
        this.renderPnlChecker = renderPnlChecker;
    }

    public boolean isRenderPnlAAprover() {
        return renderPnlAAprover;
    }

    public void setRenderPnlAAprover(boolean renderPnlAAprover) {
        this.renderPnlAAprover = renderPnlAAprover;
    }

    public boolean isRenderPnlNotChecker() {
        return renderPnlNotChecker;
    }

    public void setRenderPnlNotChecker(boolean renderPnlNotChecker) {
        this.renderPnlNotChecker = renderPnlNotChecker;
    }

    public boolean isRenderPnlNotApprover() {
        return renderPnlNotApprover;
    }

    public void setRenderPnlNotApprover(boolean renderPnlNotApprover) {
        this.renderPnlNotApprover = renderPnlNotApprover;
    }

    public WfHrProcessed getWfHrProcessed() {
        if (wfHrProcessed == null) {
            wfHrProcessed = new WfHrProcessed();
        }
        return wfHrProcessed;
    }

    public List<HrTdAnnualNeedRequests> getCheckerRequestList() {
        if (checkerRequestList == null) {
            checkerRequestList = new ArrayList<>();
        }
        return checkerRequestList;
    }

    public void setCheckerRequestList(List<HrTdAnnualNeedRequests> checkerRequestList) {
        this.checkerRequestList = checkerRequestList;
    }

    public List<HrTdAnnualNeedRequests> getApproverRequestList() {
        if (approverRequestList == null) {
            approverRequestList = new ArrayList<>();
        }
        return approverRequestList;
    }

    public void setApproverRequestList(List<HrTdAnnualNeedRequests> approverRequestList) {
        this.approverRequestList = approverRequestList;
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

    public HrTdTrainerProfiles getHrTdTrainerProfiles() {
        if (hrTdTrainerProfiles == null) {
            hrTdTrainerProfiles = new HrTdTrainerProfiles();
        }
        return hrTdTrainerProfiles;
    }

    public void setHrTdTrainerProfiles(HrTdTrainerProfiles hrTdTrainerProfiles) {
        this.hrTdTrainerProfiles = hrTdTrainerProfiles;
    }

    public HrTdAnnualTraParticipants getHrTdAnnualTraParticipants() {
        return hrTdAnnualTraParticipants;
    }

    public void setHrTdAnnualTraParticipants(HrTdAnnualTraParticipants hrTdAnnualTraParticipants) {
        this.hrTdAnnualTraParticipants = hrTdAnnualTraParticipants;
    }

    public HrEmployees getHrEmployees() {
        return hrEmployees;
    }

    public void setHrEmployees(HrEmployees hrEmployees) {
        this.hrEmployees = hrEmployees;
    }

    public String getCreateOrSearchBundle() {
        return createOrSearchBundle;
    }

    public void setCreateOrSearchBundle(String createOrSearchBundle) {
        this.createOrSearchBundle = createOrSearchBundle;
    }

    public String getIcone() {
        return icone;
    }

    public void setIcone(String icone) {
        this.icone = icone;
    }

    public String getDisableBtnSave() {
        return disableBtnSave;
    }

    public void setDisableBtnSave(String disableBtnSave) {
        this.disableBtnSave = disableBtnSave;
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

    public int getUpdateStatus() {
        return updateStatus;
    }

    public void setUpdateStatus(int updateStatus) {
        this.updateStatus = updateStatus;
    }

    public String getSaveOrUpdateButton() {
        return SaveOrUpdateButton;
    }

    public void setSaveOrUpdateButton(String SaveOrUpdateButton) {
        this.SaveOrUpdateButton = SaveOrUpdateButton;
    }

    public DataModel<HrTdAnnualTrainingNeeds> getHrTdAnnualTrainingNeedsDataModel() {
        return hrTdAnnualTrainingNeedsDataModel;
    }

    public void setHrTdAnnualTrainingNeedsDataModel(DataModel<HrTdAnnualTrainingNeeds> hrTdAnnualTrainingNeedsDataModel) {
        this.hrTdAnnualTrainingNeedsDataModel = hrTdAnnualTrainingNeedsDataModel;
    }

    public DataModel<HrTdAnnualTraParticipants> getHrTdAnnualTraParticipantsDataModel() {
        return hrTdAnnualTraParticipantsDataModel;
    }

    public void setHrTdAnnualTraParticipantsDataModel(DataModel<HrTdAnnualTraParticipants> hrTdAnnualTraParticipantsDataModel) {
        this.hrTdAnnualTraParticipantsDataModel = hrTdAnnualTraParticipantsDataModel;
    }

    public DataModel<HrTdAnnualNeedRequests> getHrTdAnnualNeedRequestsDataModel() {
        return hrTdAnnualNeedRequestsDataModel;
    }

    public void setHrTdAnnualNeedRequestsDataModel(DataModel<HrTdAnnualNeedRequests> hrTdAnnualNeedRequestsDataModel) {
        this.hrTdAnnualNeedRequestsDataModel = hrTdAnnualNeedRequestsDataModel;
    }
    //  </editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Department Tree"> 
    private TreeNode root;
    private TreeNode root2;
    private TreeNode selectedNode;
    TreeNode DepartmentList;
    private static List<HrDepartments> araListe;
    List<HrDepartments> allDepartmentsList = new ArrayList<>();

    public HrDepartments getHrDepartments() {
        if (hrDepartments == null) {
            hrDepartments = new HrDepartments();
        }
        return hrDepartments;
    }

    public void setHrDepartments(HrDepartments hrDepartments) {
        this.hrDepartments = hrDepartments;
    }

    public TreeNode getRoot() {
        return root;
    }

    public void setRoot(TreeNode root) {
        this.root = root;
    }

    public static List<HrDepartments> getAraListe() {
        return araListe;
    }

    public static void setAraListe(List<HrDepartments> araListe) {
        AnnualNeedReqApproveController.araListe = araListe;
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

    public TreeNode getDepartmentList() {
        return DepartmentList;
    }

    public void setDepartmentList(TreeNode DepartmentList) {
        this.DepartmentList = DepartmentList;
    }

    public List<HrDepartments> getAllDepartmentsList() {
        return allDepartmentsList;
    }

    public void setAllDepartmentsList(List<HrDepartments> allDepartmentsList) {
        this.allDepartmentsList = allDepartmentsList;
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

    public void onNodeSelect(NodeSelectEvent event) {
        selectedNode = event.getTreeNode();
        int key = Integer.parseInt((selectedNode.getData().toString()).split("--")[0]);
        hrDepartments = hrRecruitmentRequestsBeanLocal.getSelectDepartement(key);
        hrTdAnnualNeedRequests.setDeptId(hrDepartments);

    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Main">
    public void createNewGatePassInfo() {
        SaveOrUpdateButton = "Save";
        if (createOrSearchBundle.equals("New")) {
            renderPnlCreateGatePass = false;
            renderPnlManPage = true;
            createOrSearchBundle = "Search";
            setIcone("ui-icon-search");
        } else if (createOrSearchBundle.equals("Search")) {
            renderPnlCreateGatePass = true;
            renderPnlManPage = false;
            createOrSearchBundle = "New";
            setIcone("ui-icon-document");
        }
    }

    public void newPage() {
        renderPnlCreateGatePass = false;
        renderPnlManPage = true;
    }

    public void searchPage() {
        renderPnlCreateGatePass = true;
        renderPnlManPage = false;
        hrTdAnnualNeedRequestsDataModel = null;
        srcAnnualNeedRequests = null;
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

    public void recreateTrainingNeed() {
        hrTdAnnualTrainingNeedsDataModel = null;
        hrTdAnnualTrainingNeedsDataModel = new ListDataModel(new ArrayList<>(hrTdAnnualNeedRequests.getHrTdAnnualTrainingNeedsList()));
    }

    public void recreateParticipant() {
        hrTdAnnualTraParticipantsDataModel = new ListDataModel(new ArrayList<>(hrTdAnnualTrainingNeeds.getHrTdAnulTraPaticptsList()));
    }

    public void selectTrainNeed(SelectEvent event) {
        hrTdAnnualTrainingNeeds = (HrTdAnnualTrainingNeeds) event.getObject();
        hrTdAnnualTraParticipants.setAnnTraNeedId(hrTdAnnualTrainingNeeds);
        hrTdTrainerProfiles = hrTdAnnualTrainingNeeds.getInstitutionId();
        hrTdCourses.setId(hrTdAnnualTrainingNeeds.getTrainingId().getId());
        hrLuTrainingCategory = hrTdCourses.getCategoryId();
        hrTdAnnualTraParticipantsDataModel = new ListDataModel(new ArrayList(hrTdAnnualTraParticipantsBeanLocal.SrchAnnTraNeedIdToBeApproved(hrTdAnnualTraParticipants)));
        SaveOrUpdateButton = "Update";
    }

    public ArrayList<HrTdAnnualNeedRequests> findListByYear(ValueChangeEvent event) {
        srcAnnualNeedRequests.setBudgetYear(Integer.valueOf(event.getNewValue().toString()));
        hrTdAnnualNeedRequestsDataModel = new ListDataModel(new ArrayList<>(hrTdAnnualNeedRequestsBeanLocal.searchByBugdetYear(srcAnnualNeedRequests)));
        return null;
    }

    public void viewDetail() {
        hrTdAnnualTrainingNeeds = hrTdAnnualTrainingNeedsDataModel.getRowData();
        recreateParticipant();
    }

    public void populateTrainNeed(SelectEvent event) {
        hrTdAnnualNeedRequests = (HrTdAnnualNeedRequests) event.getObject();
        populateAnnualTrain();
    }

    public void populateAnnualTrain() {
        hrDepartments = hrTdAnnualNeedRequests.getDeptId();
        renderPnlCreateGatePass = false;
        renderPnlManPage = true;
        createOrSearchBundle = "Search";
        setIcone("ui-icon-search");
        readBudgetYears();
        recreateTrainingNeed();
        recreatWorkFlowDataModel();
    }

    public void populateNotifcation(ValueChangeEvent event) {
        try {
            if (null != event.getNewValue()) {
                hrTdAnnualNeedRequests = (HrTdAnnualNeedRequests) event.getNewValue();
                populateAnnualTrain();
                recreatWorkFlowDataModel();
                RequestContext context = RequestContext.getCurrentInstance();
                context.execute("PF('widNotf').hide();");
            }
        } catch (Exception e) {
            e.printStackTrace();
            JsfUtil.addFatalMessage("Faile to process! Try Again");
        }
    }

    public void saveOrUpdate() {
        hrTdAnnualNeedRequestsBeanLocal.saveOrUpdate(hrTdAnnualNeedRequests);
        JsfUtil.addSuccessMessage("Successfully Saved");
        clear();
        renderPnlCreateGatePass = true;
        renderPnlManPage = false;
        createOrSearchBundle = "New";
        setIcone("ui-icon-document");
        readBudgetYears();
        recreateTrainingNeed();
    }

    public SelectItem[] getstatus() {
        return JsfUtil.getSelectItems(status(), true);
    }

    public ArrayList<String> status() {
        ArrayList<String> statusList = new ArrayList<>();
        statusList.add("Request");
        statusList.add("Approved");
        statusList.add("Rejected");
        statusList.add("Resubmitted");
        return statusList;
    }

    int status1 = 0;
    int statusChecker = 0;
    List<HrTdAnnualNeedRequests> trainingApprovedList = new ArrayList<>();
    List<HrTdAnnualNeedRequests> trainingCheckedList = new ArrayList<>();

    public List<SelectItem> getFilterByStatusForChecker() {
        List<SelectItem> selectItems = new ArrayList<>();
        selectItems.add(new SelectItem("null", "--- Select ---"));
        selectItems.add(new SelectItem(String.valueOf("0"), "Load Request List"));
        selectItems.add(new SelectItem(String.valueOf("1"), "Load Checked List"));
        selectItems.add(new SelectItem(String.valueOf("2"), "Load Checker Reject List"));
        selectItems.add(new SelectItem(String.valueOf("3"), "Load All List"));
        return selectItems;
    }

    private void populateTableChecker() {
        try {
            trainingCheckedList = hrTdAnnualNeedRequestsBeanLocal.loadTrainingRequestForChecker(statusChecker);
            hrTdAnnualNeedRequestsDataModel = new ListDataModel<>(trainingCheckedList);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void valueChangeListnerChecker(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            statusChecker = Integer.valueOf(event.getNewValue().toString());
            populateTableChecker();
        }
    }

    public List<SelectItem> getFilterByStatusForApprove() {
        List<SelectItem> selectItems = new ArrayList<>();
        selectItems.add(new SelectItem("null", "--- Select ---"));
        selectItems.add(new SelectItem(String.valueOf("0"), "Load Checked List"));
        selectItems.add(new SelectItem(String.valueOf("1"), "Load Approved List"));
        selectItems.add(new SelectItem(String.valueOf("2"), "Load Approve Reject List"));
        selectItems.add(new SelectItem(String.valueOf("3"), "Load All List"));
        return selectItems;
    }

    private void populateTable() {
        try {
            trainingApprovedList = hrTdAnnualNeedRequestsBeanLocal.loadTrainingRequest(status1);
            hrTdAnnualNeedRequestsDataModel = new ListDataModel<>(trainingApprovedList);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void valueChangeListnerUnplannedTrainingRequest(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            status1 = Integer.valueOf(event.getNewValue().toString());
            populateTable();
        }
    }

    public void saveAnnualTrainingApprove() {
        try {
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(sessionBeanLocal.getUserName(), "saveAnnualTrainingApprove", dataset)) {
                try {
                    if (hrTdAnnualNeedRequests.getId() == null) {
                        JsfUtil.addFatalMessage("Can't get data. Try again! ");
                    } else if (hrTdAnnualTraParticipantsDataModel.getRowCount() <= 0) {
                        JsfUtil.addFatalMessage("Participant Data table shoud't be empty");
                    } else {
                        saveWorkFlowInformation();
                        clear();
                        JsfUtil.addSuccessMessage("Successfully Saved");
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JsfUtil.addFatalMessage("Error occurs while saving");
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
                JAXBElement<String> program = new JAXBElement<String>(qualifiedName, String.class, null, "saveAnnualTrainingApprove");
                eventEntry.setProgram(program);
                security.addEventLog(eventEntry, dataset);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public void clear() {
        hrTdAnnualNeedRequests = null;
        hrTdAnnualTrainingNeeds = null;
        hrTdAnnualTrainingNeedsDataModel = null;
        hrTdAnnualTraParticipantsDataModel = null;
        workFlow = new WorkFlow();
        wfHrProcessed.setCommentGiven(null);
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
    //</editor-fold>

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
                hrTdAnnualNeedRequests.getHrWorkFlowAnnualRequestList().get(i).setChangedStstus("Prepare");
            } else if (hrTdAnnualNeedRequests.getHrWorkFlowAnnualRequestList().get(i).getDecision() == 1) {
                hrTdAnnualNeedRequests.getHrWorkFlowAnnualRequestList().get(i).setChangedStstus("Checked");
            } else if (hrTdAnnualNeedRequests.getHrWorkFlowAnnualRequestList().get(i).getDecision() == 2) {
                hrTdAnnualNeedRequests.getHrWorkFlowAnnualRequestList().get(i).setChangedStstus("Checker Reject");
            } else if (hrTdAnnualNeedRequests.getHrWorkFlowAnnualRequestList().get(i).getDecision() == 3) {
                hrTdAnnualNeedRequests.getHrWorkFlowAnnualRequestList().get(i).setChangedStstus("Approved");
            } else if (hrTdAnnualNeedRequests.getHrWorkFlowAnnualRequestList().get(i).getDecision() == 4) {
                hrTdAnnualNeedRequests.getHrWorkFlowAnnualRequestList().get(i).setChangedStstus("Approve Reject");
            }
        }
        hrWorkFlowDataModel = new ListDataModel(new ArrayList(hrTdAnnualNeedRequests.getHrWorkFlowAnnualRequestList()));
    }

    public void saveWorkFlowInformation() {
        if (selectedValue.equalsIgnoreCase("a") && workFlow.isApproveStatus()) {
            System.out.println("===========Approver is aaproving==============");
            workFlow.setUserStatus(Constants.APPROVE_VALUE);
            hrTdAnnualNeedRequests.setStatus(String.valueOf(workFlow.getUserStatus()));
            hrTdAnnualTrainingNeeds.setStatus(workFlow.getUserStatus());
            hrTdAnnualTraParticipants.setStatus(String.valueOf(workFlow.getUserStatus()));
        } else if (selectedValue.equalsIgnoreCase("b") && workFlow.isApproveStatus()) {
            workFlow.setUserStatus(Constants.APPROVE_REJECT_VALUE);
            hrTdAnnualNeedRequests.setStatus(String.valueOf(workFlow.getUserStatus()));
            hrTdAnnualTrainingNeeds.setStatus(workFlow.getUserStatus());
            hrTdAnnualTraParticipants.setStatus(String.valueOf(workFlow.getUserStatus()));
        } else if (selectedValue.equalsIgnoreCase("a") && workFlow.isCheckStatus()) {
            System.out.println("======Checker is approving==================");
            workFlow.setUserStatus(Constants.CHECK_APPROVE_VALUE);
            hrTdAnnualNeedRequests.setStatus(String.valueOf(workFlow.getUserStatus()));
            hrTdAnnualTrainingNeeds.setStatus(workFlow.getUserStatus());
            hrTdAnnualTraParticipants.setStatus(String.valueOf(workFlow.getUserStatus()));
        } else if (selectedValue.equalsIgnoreCase("b") && workFlow.isCheckStatus()) {
            workFlow.setUserStatus(Constants.CHECK_REJECT_VALUE);
            hrTdAnnualNeedRequests.setStatus(String.valueOf(workFlow.getUserStatus()));
            hrTdAnnualTrainingNeeds.setStatus(workFlow.getUserStatus());
            hrTdAnnualTraParticipants.setStatus(String.valueOf(workFlow.getUserStatus()));
        }
        hrTdAnnualNeedRequestsBeanLocal.saveOrUpdate(hrTdAnnualNeedRequests);
        wfHrProcessed.setAnnualTrainingId(hrTdAnnualNeedRequests);
        wfHrProcessed.setDecision(workFlow.getUserStatus());
        wfHrProcessed.setProcessedBy(sessionBeanLocal.getUserId());
        wfHrProcessed.setProcessedOn(processedOn);
        wfHrProcessedBeanLocal.saveOrUpdate(wfHrProcessed);
    }
    // </editor-fold>

}
