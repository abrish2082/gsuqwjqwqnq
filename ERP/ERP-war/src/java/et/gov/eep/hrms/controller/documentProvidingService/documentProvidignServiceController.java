package et.gov.eep.hrms.controller.documentProvidingService;

import et.gov.eep.commonApplications.businessLogic.WfHrProcessedBeanLocal;
import et.gov.eep.commonApplications.entity.WfHrProcessed;
import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.commonApplications.utility.date.StringDateManipulation;
import et.gov.eep.hrms.businessLogic.address.HrAddressesBeanLocal;
import et.gov.eep.hrms.businessLogic.documentProvidingService.DocumentProvidingServiceBeanLocal;
import et.gov.eep.hrms.businessLogic.documentProvidingService.DocumentTypeBeanLocal;
import et.gov.eep.hrms.businessLogic.employee.HrEmployeeBeanLocal;
import et.gov.eep.hrms.businessLogic.training.HrInlandAndForeignTrainingBeanLocal;
import et.gov.eep.hrms.entity.address.HrAddresses;
import et.gov.eep.hrms.entity.documentProvidingService.HrDocumentRequests;
import et.gov.eep.hrms.entity.documentProvidingService.HrDocumentTypes;
import et.gov.eep.hrms.entity.employee.HrEmployees;
import et.gov.eep.hrms.entity.organization.HrDepartments;
import et.gov.eep.hrms.entity.organization.HrJobTypes;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
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
import webService.UserStatus;

@Named(value = "documentProvidignServiceControllers")
@ViewScoped
public class documentProvidignServiceController implements Serializable {

    //<editor-fold defaultstate="collapsed" desc="Entity Injection">
    @Inject
    HrDocumentRequests hrDocumentRequests;
    @Inject
    HrDocumentTypes hrDocumentTypes;
    @Inject
    HrEmployees Requester;
    @Inject
    HrJobTypes hrJobTypes;
    @Inject
    HrDepartments hrDepartments;
    @Inject
    HrAddresses hrAddresses;
    @Inject
    WfHrProcessed WfHrProcessed;
    @Inject
    SessionBean sessionBeanLocal;
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="EJB Initialization">
    @EJB
    private DocumentProvidingServiceBeanLocal documentProvidingServiceBeanLocal;
    @EJB
    private HrEmployeeBeanLocal hrEmployeeBean;
    @EJB
    HrAddressesBeanLocal hrAddressesBeanLocal;
    @EJB
    DocumentTypeBeanLocal documentTypeBeanLocal;
    @EJB
    HrInlandAndForeignTrainingBeanLocal InlandAndForeignTrainingBeanLocal;
    @EJB
    WfHrProcessedBeanLocal wfHrProcessedBeanLocal;
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Variable declaration">
    int updateStatus = 0;
    String Experience;
    private String reportFormat;
    private String rdreportName;
    private boolean btnNewRender = false;
    private String saveorUpdateBundle = "Save";
    private String ExperienceorConformationBundle = "Generate";
    private String createOrSearchBundle = "New";
    private String createOrSearchBundle1 = "";
    private String headerTitle = "Search Employee";
    private String icone = "ui-icon-plus";
    private boolean disableBtnCreate;
    private boolean chooseCreatePage = false;
    private boolean chooseMainPage = true;
    private boolean generateexp = false;
    private boolean generateEpmConf = false;
    private boolean guranteepnl = false;
    private boolean pnlapproval = false;
    private String chooseEmployee = "true";
    private String choosePosition = "false";
    private boolean pnldocgenerate = false;
    private boolean saveOrUpdatebtnpnl = true;
    private boolean pnlrWfIcon = false;
    private boolean enableleSaveOrUpdateBtn = true;
    private boolean enableleSaveOrUpdateBtn1 = true;
    private int dateCalc;
    private int decision;
    Date date = new Date();
    private String selected;
    private String selectedid;
    private TreeNode root;
    private TreeNode selectedNode;
    private int addressId = 0;
    private int requestNotificationCounter = 0;
    private int requestNotificationCounter1 = 0;
    UserStatus userStatus = new UserStatus();
    WorkFlow workFlow = new WorkFlow();
    int approveType = 0;
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="List Initialization">
    List<SelectItem> filterByStatus = new ArrayList<>();
    private static List<HrAddresses> addresses;
    List<SelectItem> filterByStatus1 = new ArrayList<>();
    List<SelectItem> filterByStatus2 = new ArrayList<>();
    List<HrDocumentTypes> documentTypeList = new ArrayList<>();
    List<HrDocumentRequests> requesstlist = new ArrayList<>();
    List<HrDocumentRequests> approvelist = new ArrayList<>();
    List<HrDocumentRequests> approvedRequesrtList = new ArrayList<>();
    DataModel<HrDocumentRequests> requestsListDataModel;
    DataModel<HrDocumentRequests> ApproveDataModel;
    DataModel<HrDocumentRequests> approvedRequestsListDataModel;
    DataModel<WfHrProcessed> workFlowDataModel = new ListDataModel<>();
    private List<HrDocumentRequests> selectedRequest;
    private static List<HrAddresses> allAddressData = new ArrayList<>();
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="post construct">
    @PostConstruct
    public void init() {

        hrDocumentRequests.setApproveDate(StringDateManipulation.toDayInEc());
        documentTypeList = documentTypeBeanLocal.findAlldocTyps();
        approvedRequesrtList = documentProvidingServiceBeanLocal.findAllAprovedrequests();
        ApproveDataModel = new ListDataModel(approvelist);
        requestsListDataModel = new ListDataModel(requesstlist);
        approvedRequestsListDataModel = new ListDataModel<>(approvedRequesrtList);
        loadTree();
        setFullName(sessionBeanLocal.getUserName());
        System.out.println("approver=====" + workFlow.isApproveStatus());
        System.out.println("preparer=====" + workFlow.isPrepareStatus());
        System.out.println("username=====" + workFlow.getUserName());
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="authentication code (it should be removed)">
    private String fullName;

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Getter&&Setter">
    public HrAddresses getHrAddresses() {
        if (hrAddresses == null) {
            HrAddresses hrAddresses = new HrAddresses();
        }
        return hrAddresses;
    }

    public void setHrAddresses(HrAddresses hrAddresses) {
        this.hrAddresses = hrAddresses;
    }

    public boolean isBtnNewRender() {
        return btnNewRender;
    }

    public void setBtnNewRender(boolean btnNewRender) {
        this.btnNewRender = btnNewRender;
    }

    public WfHrProcessed getWfHrProcessed() {
        return WfHrProcessed;
    }

    public void setWfHrProcessed(WfHrProcessed WfHrProcessed) {
        this.WfHrProcessed = WfHrProcessed;
    }

    public DataModel<WfHrProcessed> getWorkFlowDataModel() {
        return workFlowDataModel;
    }

    public void setWorkFlowDataModel(DataModel<WfHrProcessed> workFlowDataModel) {
        this.workFlowDataModel = workFlowDataModel;
    }

    public String getCreateOrSearchBundle1() {
        return createOrSearchBundle1;
    }

    public void setCreateOrSearchBundle1(String createOrSearchBundle1) {
        this.createOrSearchBundle1 = createOrSearchBundle1;
    }

    public static List<HrAddresses> getAllAddressData() {
        return allAddressData;
    }

    public static void setAllAddressData(List<HrAddresses> allAddressData) {
        documentProvidignServiceController.allAddressData = allAddressData;
    }

    public String getSelected() {
        return selected;
    }

    public HrInlandAndForeignTrainingBeanLocal getInlandAndForeignTrainingBeanLocal() {
        return InlandAndForeignTrainingBeanLocal;
    }

    public void setInlandAndForeignTrainingBeanLocal(HrInlandAndForeignTrainingBeanLocal InlandAndForeignTrainingBeanLocal) {
        this.InlandAndForeignTrainingBeanLocal = InlandAndForeignTrainingBeanLocal;
    }

    public void setSelected(String selected) {
        this.selected = selected;
    }

    public String getSelectedid() {
        return selectedid;
    }

    public void setSelectedid(String selectedid) {
        this.selectedid = selectedid;
    }

    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    public static List<HrAddresses> getAddresses() {
        return addresses;
    }

    public static void setAddresses(List<HrAddresses> addresses) {
        documentProvidignServiceController.addresses = addresses;
    }

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

    public boolean isPnlapproval() {
        return pnlapproval;
    }

    public void setPnlapproval(boolean pnlapproval) {
        this.pnlapproval = pnlapproval;
    }

    public boolean isPnldocgenerate() {
        return pnldocgenerate;
    }

    public void setPnldocgenerate(boolean pnldocgenerate) {
        this.pnldocgenerate = pnldocgenerate;
    }

    public boolean isGuranteepnl() {
        return guranteepnl;
    }

    public void setGuranteepnl(boolean guranteepnl) {
        this.guranteepnl = guranteepnl;
    }

    public List<HrDocumentRequests> getApprovelist() {
        return approvelist;
    }

    public void setApprovelist(List<HrDocumentRequests> approvelist) {
        this.approvelist = approvelist;
    }

    public int getDecision() {
        return decision;
    }

    public void setDecision(int decision) {
        this.decision = decision;
    }

    public DataModel<HrDocumentRequests> getApproveDataModel() {
        return ApproveDataModel;
    }

    public void setApproveDataModel(DataModel<HrDocumentRequests> ApproveDataModel) {
        this.ApproveDataModel = ApproveDataModel;
    }

    public String getRdreportName() {
        return rdreportName;
    }

    public void setRdreportName(String rdreportName) {
        this.rdreportName = rdreportName;
    }

    public DocumentProvidingServiceBeanLocal getDocumentProvidingServiceLocal() {
        return documentProvidingServiceBeanLocal;
    }

    public void setDocumentProvidingServiceLocal(DocumentProvidingServiceBeanLocal documentProvidingServiceBeanLocal) {
        this.documentProvidingServiceBeanLocal = documentProvidingServiceBeanLocal;
    }

    public HrDocumentRequests getHrDocumentRequests() {
        return hrDocumentRequests;
    }

    public void setHrDocumentRequests(HrDocumentRequests hrDocumentRequests) {
        this.hrDocumentRequests = hrDocumentRequests;
    }

    public HrEmployees getRequester() {
        return Requester;
    }

    public void setRequester(HrEmployees Requester) {
        this.Requester = Requester;
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

    public DocumentTypeBeanLocal getDocumentTypeBeanLocal() {
        return documentTypeBeanLocal;
    }

    public void setDocumentTypeBeanLocal(DocumentTypeBeanLocal documentTypeBeanLocal) {
        this.documentTypeBeanLocal = documentTypeBeanLocal;
    }

    public HrDocumentTypes getHrDocumentTypes() {
        if (hrDocumentTypes == null) {
            hrDocumentTypes = new HrDocumentTypes();
        }

        return hrDocumentTypes;
    }

    public void setHrDocumentTypes(HrDocumentTypes hrDocumentTypes) {
        this.hrDocumentTypes = hrDocumentTypes;
    }

    public DocumentProvidingServiceBeanLocal getDocumentProvidingServiceBeanLocal() {
        return documentProvidingServiceBeanLocal;
    }

    public void setDocumentProvidingServiceBeanLocal(DocumentProvidingServiceBeanLocal documentProvidingServiceBeanLocal) {
        this.documentProvidingServiceBeanLocal = documentProvidingServiceBeanLocal;
    }

    public HrEmployeeBeanLocal getHrEmployeeBean() {
        return hrEmployeeBean;
    }

    public void setHrEmployeeBean(HrEmployeeBeanLocal hrEmployeeBean) {
        this.hrEmployeeBean = hrEmployeeBean;
    }

    public int getUpdateStatus() {
        return updateStatus;
    }

    public void setUpdateStatus(int updateStatus) {
        this.updateStatus = updateStatus;
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

    public String getExperienceorConformationBundle() {
        return ExperienceorConformationBundle;
    }

    public void setExperienceorConformationBundle(String ExperienceorConformationBundle) {
        this.ExperienceorConformationBundle = ExperienceorConformationBundle;
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

    public boolean isGenerateexp() {
        return generateexp;
    }

    public void setGenerateexp(boolean generateexp) {
        this.generateexp = generateexp;
    }

    public boolean isGenerateEpmConf() {
        return generateEpmConf;
    }

    public void setGenerateEpmConf(boolean generateEpmConf) {
        this.generateEpmConf = generateEpmConf;
    }

    public String getChooseEmployee() {
        return chooseEmployee;
    }

    public void setChooseEmployee(String chooseEmployee) {
        this.chooseEmployee = chooseEmployee;
    }

    public String getChoosePosition() {
        return choosePosition;
    }

    public void setChoosePosition(String choosePosition) {
        this.choosePosition = choosePosition;
    }

    public int getDateCalc() {
        return dateCalc;
    }

    public void setDateCalc(int dateCalc) {
        this.dateCalc = dateCalc;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setFilterByStatus(List<SelectItem> filterByStatus) {
        this.filterByStatus = filterByStatus;
    }

    public List<SelectItem> getFilterByStatus() {
        filterByStatus = documentProvidingServiceBeanLocal.filterByStatus();
        return filterByStatus;
    }

    public List<SelectItem> getFilterByStatus1() {
        filterByStatus1 = documentProvidingServiceBeanLocal.filterByStatus1();
        return filterByStatus1;
    }

    public void setFilterByStatus1(List<SelectItem> filterByStatus1) {
        this.filterByStatus1 = filterByStatus1;
    }

    public List<SelectItem> getFilterByStatus2() {
        return documentProvidingServiceBeanLocal.filterByStatus2();
    }

    public void setFilterByStatus2(List<SelectItem> filterByStatus2) {
        this.filterByStatus2 = filterByStatus2;
    }

    public List<HrDocumentRequests> getRequesstlist() {
        return requesstlist;
    }

    public void setRequesstlist(List<HrDocumentRequests> requesstlist) {
        this.requesstlist = requesstlist;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public DataModel<HrDocumentRequests> getRequestsListDataModel() {
        return requestsListDataModel;
    }

    public void setRequestsListDataModel(DataModel<HrDocumentRequests> requestsListDataModel) {
        this.requestsListDataModel = requestsListDataModel;
    }

    public List<HrDocumentRequests> getSelectedRequest() {
        return selectedRequest;
    }

    public List<HrDocumentTypes> getDocumentTypeList() {
        return documentTypeList;
    }

    public void setDocumentTypeList(List<HrDocumentTypes> documentTypeList) {
        this.documentTypeList = documentTypeList;
    }

    public void setSelectedRequest(List<HrDocumentRequests> selectedRequest) {
        this.selectedRequest = selectedRequest;
    }

    public int getApproveType() {
        return approveType;
    }

    public void setApproveType(int approveType) {
        this.approveType = approveType;
    }

    public List<HrDocumentRequests> getApprovedRequesrtList() {
        return approvedRequesrtList;
    }

    public void setApprovedRequesrtList(List<HrDocumentRequests> approvedRequesrtList) {
        this.approvedRequesrtList = approvedRequesrtList;
    }

    public String getSelectedValue() {
        return selectedValue;
    }

    public void setSelectedValue(String selectedValue) {
        this.selectedValue = selectedValue;
    }

    public DataModel<HrDocumentRequests> getApprovedRequestsListDataModel() {
        return approvedRequestsListDataModel;
    }

    public void setApprovedRequestsListDataModel(DataModel<HrDocumentRequests> approvedRequestsListDataModel) {
        this.approvedRequestsListDataModel = approvedRequestsListDataModel;
    }

    public String getExperience() {
        return Experience;
    }

    public void setExperience(String Experience) {
        this.Experience = Experience;
    }

    public String getReportFormat() {
        return reportFormat;
    }

    public void setReportFormat(String reportFormat) {
        this.reportFormat = reportFormat;
    }

    public boolean isSaveOrUpdatebtnpnl() {
        return saveOrUpdatebtnpnl;
    }

    public void setSaveOrUpdatebtnpnl(boolean saveOrUpdatebtnpnl) {
        this.saveOrUpdatebtnpnl = saveOrUpdatebtnpnl;
    }

    public int getRequestNotificationCounter() {
        requesstlist = documentProvidingServiceBeanLocal.findAllrequests();
        requestNotificationCounter = requesstlist.size();
        return requestNotificationCounter;
    }

    public void setRequestNotificationCounter(int requestNotificationCounter) {
        this.requestNotificationCounter = requestNotificationCounter;
    }

    public int getRequestNotificationCounter1() {
        requesstlist = documentProvidingServiceBeanLocal.findAllAprovedrequests();
        requestNotificationCounter1 = requesstlist.size();
        return requestNotificationCounter1;
    }

    public void setRequestNotificationCounter1(int requestNotificationCounter1) {
        this.requestNotificationCounter1 = requestNotificationCounter1;
    }

    public boolean isPnlrWfIcon() {
        return pnlrWfIcon;
    }

    public void setPnlrWfIcon(boolean pnlrWfIcon) {
        this.pnlrWfIcon = pnlrWfIcon;
    }

    public boolean isEnableleSaveOrUpdateBtn() {
        return enableleSaveOrUpdateBtn;
    }

    public void setEnableleSaveOrUpdateBtn(boolean enableleSaveOrUpdateBtn) {
        this.enableleSaveOrUpdateBtn = enableleSaveOrUpdateBtn;
    }

    public boolean isEnableleSaveOrUpdateBtn1() {
        return enableleSaveOrUpdateBtn1;
    }

    public void setEnableleSaveOrUpdateBtn1(boolean enableleSaveOrUpdateBtn1) {
        this.enableleSaveOrUpdateBtn1 = enableleSaveOrUpdateBtn1;
    }

//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="main">
    public void documentProvidingServiceInfo() {
        saveorUpdateBundle = "Save";
        disableBtnCreate = false;
        switch (createOrSearchBundle) {
            case "New":
                chooseCreatePage = true;
                chooseMainPage = false;
                generateexp = false;
                generateEpmConf = false;
                createOrSearchBundle = "Search";
                setIcone("ui-icon-search");
                btnNewRender = false;
                break;
            case "Search":
                chooseCreatePage = false;
                chooseMainPage = true;
                // setHrAddresses(hrDocumentRequests.getAddressesId());
                generateexp = false;
                generateEpmConf = false;
                btnNewRender = false;
                createOrSearchBundle = "New";
                setIcone("ui-icon-plus");
                break;
        }
    }

    public void newButtonAction() {
        ClearDocumentRequests();
        chooseCreatePage = true;
        chooseMainPage = false;
        generateexp = false;
        generateEpmConf = false;
        createOrSearchBundle = "Search";
        setIcone("ui-icon-search");
        btnNewRender = false;
        btnNewRender = false;
        saveorUpdateBundle = "Save";
    }

    public void documentProvidingServiceInfo1() {
        saveorUpdateBundle = "Save";
        disableBtnCreate = false;
        switch (createOrSearchBundle1) {
            case "":
                chooseCreatePage = true;
                chooseMainPage = false;
                generateexp = false;
                generateEpmConf = false;
                btnNewRender = false;
                createOrSearchBundle1 = "Search";
                setIcone("ui-icon-search");
                break;
            case "Search":
                chooseCreatePage = false;
                chooseMainPage = true;
                generateexp = false;
                generateEpmConf = false;
                btnNewRender = false;
                setIcone("ui-icon-plus");

                break;
            case "generateexp":
                chooseCreatePage = false;
                chooseMainPage = false;
                generateexp = true;
                generateEpmConf = false;
                btnNewRender = false;
                break;
            case "generateEpmConf":
                chooseCreatePage = false;
                chooseMainPage = false;
                btnNewRender = false;
                generateexp = false;
                generateEpmConf = true;
                break;
        }
    }

    public void loadTree() {
        allAddressData = documentProvidingServiceBeanLocal.findAllAddress();
        root = new DefaultTreeNode("Root", null);
        populateNodes(allAddressData, 0, root);
    }

    public void populateNodes(List<HrAddresses> addressData, int id, TreeNode node) {
        addresses = new ArrayList<>();
        if (!allAddressData.isEmpty()) {
            for (HrAddresses k : getAllAddressData()) {
                if (k.getParentId() == id) {
                    TreeNode childNode = new DefaultTreeNode(k.getAddressDescription() + "=>" + k.getAddressId(), node);
                    addresses.add(k);
                    populateNodes(addresses, k.getAddressId(), childNode);
                }
            }
        }

    }

    public void onNodeSelect(NodeSelectEvent event) {
        selectedNode = event.getTreeNode();
        addressId = Integer.parseInt((selectedNode.getData().toString()).split("=>")[1]);
        hrAddresses.setAddressId(addressId);
        hrAddresses = documentProvidingServiceBeanLocal.findAllAddressUnitAsOne(hrAddresses);

    }

    private void populateTable() {
        try {
            List<HrDocumentRequests> readFilteredRetirement = documentProvidingServiceBeanLocal.loadDocumentRequests(status, sessionBeanLocal.getUserId());
            requestsListDataModel = new ListDataModel(readFilteredRetirement);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void filiterByStatus_Vcl(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            requestsListDataModel = null;
            status = Integer.valueOf(event.getNewValue().toString());
            populateTable();
            pnldocgenerate = false;
            if (status == 0) {
                enableleSaveOrUpdateBtn = true;
            } else {
                enableleSaveOrUpdateBtn = false;
            }
        }
    }

    public void ApproverPopulate(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            status = Integer.valueOf(event.getNewValue().toString());
            hrDocumentRequests.setId(BigDecimal.valueOf(status));
            hrDocumentRequests = documentProvidingServiceBeanLocal.loadDocumentRequestDetails(hrDocumentRequests.getId());
            setRequester(hrDocumentRequests.getRequesterId());
            hrDepartments = Requester.getDeptId();
            hrJobTypes = Requester.getJobId();
            setHrAddresses(hrDocumentRequests.getAddressesId());
            hrDocumentTypes = hrDocumentRequests.getDocumentType();
            if (hrDocumentRequests.getStatus() == 1) {
                setSelectedValue("1");
            } else if (hrDocumentRequests.getStatus() == 2) {
                setSelectedValue("3");
            } else {
                setSelectedValue("--- select decision---");
            }

            setDecision(1);
            String Shday[] = Requester.getHireDate().split("/");
            int Ihday = Integer.parseInt(Shday[0]);
            String Shmonth[] = Requester.getHireDate().split("/");
            int Ihmonth = Integer.parseInt(Shmonth[1]);
            String Shyear[] = Requester.getHireDate().split("/");
            int Ihyear = Integer.parseInt(Shyear[2]);

            String Scday[] = StringDateManipulation.toDayInEc().split("-");
            int Icday = Integer.parseInt(Scday[2]);
            String Scmonth[] = StringDateManipulation.toDayInEc().split("-");
            int Icmonth = Integer.parseInt(Scmonth[1]);
            String Scyear[] = StringDateManipulation.toDayInEc().split("-");
            int Icyear = Integer.parseInt(Scyear[0]);
            int expday = ((Icday - Ihday) + ((Icmonth - Ihmonth) * 30) + ((Icyear - Ihyear) * 365));
            int expInYear = expday / 365;
            int expInMonth = ((expday % 365) / 30);

            Experience = (expInYear + "-" + "Year And " + expInMonth + "- Month");
            updateStatus = 1;
            pnlapproval = true;
            pnldocgenerate = true;;
            chooseCreatePage = true;
            chooseMainPage = false;
            if (hrDocumentTypes.getId().equals(3)) {
                guranteepnl = true;
            } else {
                guranteepnl = false;
            }

            recreateDataModelwf();

        }
    }

    public ArrayList<HrEmployees> SearchByFnameRequest(String hrEmployee) {
        ArrayList<HrEmployees> employees = null;
        Requester.setFirstName(hrEmployee);
        employees = hrEmployeeBean.SearchByFname(Requester);

        return employees;
    }

    public void getByDelegateName(SelectEvent event) {
        try {
            Requester = (HrEmployees) event.getObject();
            //Requester.setFirstName(event.getObject().toString());
            //Requester=(HrEmployees)(event.getObject());
            System.out.println("RequesterId==" + Requester.getFirstName());
            //Requester = hrEmployeeBean.getByFirstName(Requester);
            // Requester.setId(Requester.getId());
            hrDocumentRequests.setRequesterId(Requester);
            hrDepartments = Requester.getDeptId();
            hrJobTypes = Requester.getJobId();

            String Shday[] = Requester.getHireDate().split("/");
            int Ihday = Integer.parseInt(Shday[0]);
            String Shmonth[] = Requester.getHireDate().split("/");
            int Ihmonth = Integer.parseInt(Shmonth[1]);
            String Shyear[] = Requester.getHireDate().split("/");
            int Ihyear = Integer.parseInt(Shyear[2]);

            String Scday[] = StringDateManipulation.toDayInEc().split("-");
            int Icday = Integer.parseInt(Scday[2]);
            String Scmonth[] = StringDateManipulation.toDayInEc().split("-");
            int Icmonth = Integer.parseInt(Scmonth[1]);
            String Scyear[] = StringDateManipulation.toDayInEc().split("-");
            int Icyear = Integer.parseInt(Scyear[0]);

            int expday = ((Icday - Ihday) + ((Icmonth - Ihmonth) * 30) + ((Icyear - Ihyear) * 365));
            int expInYear = expday / 365;
            int expInMonth = ((expday % 365) / 30);

            Experience = (expInYear + "-" + "Year And " + expInMonth + "- Month");
        } catch (Exception ex) {
            System.err.println("Exception");
        }
    }

    public void ClearDocumentRequests() {
        hrDocumentRequests = new HrDocumentRequests();
        Requester = new HrEmployees();
        hrDocumentTypes = new HrDocumentTypes();
        hrDepartments = new HrDepartments();
        hrJobTypes = new HrJobTypes();
        Experience = null;
        hrAddresses = null;
    }

    public void recreateDataModelwf() {
        workFlowDataModel = null;
        workFlowDataModel = new ListDataModel(new ArrayList(hrDocumentRequests.getWfHrProcessedList()));
    }

    public void wfSave() {
        WfHrProcessed.setProcessedBy(sessionBeanLocal.getUserId());
        WfHrProcessed.setDocumentId(hrDocumentRequests);
        WfHrProcessed.setDecision(hrDocumentRequests.getStatus());
        wfHrProcessedBeanLocal.create(WfHrProcessed);
        WfHrProcessed = new WfHrProcessed();
    }

    public void saveDocumentRequests() {
        hrDocumentRequests.setDocumentType(hrDocumentTypes);
        hrDocumentRequests.setAddressesId(hrAddresses);
        if (hrDocumentRequests.getRequesterId() == null) {
            JsfUtil.addFatalMessage("");
        }
        if (hrDocumentRequests.getRequestDate() == null) {
            JsfUtil.addFatalMessage("");
        }
        if (hrDocumentRequests.getDocumentType() == null) {
            JsfUtil.addFatalMessage("document type can not be empty");
        } else {
            hrDocumentRequests.setRequestDate(hrDocumentRequests.getRequestDate());

            String Smonth[] = hrDocumentRequests.getRequestDate().split("/");
            int Imonth = Integer.parseInt(Smonth[1]);
            String Syear[] = hrDocumentRequests.getRequestDate().split("/");
            int Iyear = Integer.parseInt(Syear[2]);
            String Sdate[] = hrDocumentRequests.getRequestDate().split("/");
            int Idate = Integer.parseInt(Sdate[0]);

            hrDocumentRequests.setDocumentType(hrDocumentTypes);

            String Shdays[] = Requester.getHireDate().split("/");
            int Ihdays = Integer.parseInt(Shdays[2]);
            String Shmonths[] = Requester.getHireDate().split("/");
            int Ihmonths = Integer.parseInt(Shmonths[1]);
            String Shyears[] = Requester.getHireDate().split("/");
            int Ihyears = Integer.parseInt(Shyears[0]);

            int hire_requestDate = ((Idate - Ihdays) + ((Imonth - Ihmonths) * 30) + ((Iyear - Ihyears) * 365));

            if (hire_requestDate < 45 && hrDocumentRequests.getDocumentType().getId() == 1) {
                JsfUtil.addFatalMessage("Experience request for  Less than 45 days is not Allowed ");
                ClearDocumentRequests();
            }

            workFlow.setUserStatus(Constants.PREPARE_VALUE);
            hrDocumentRequests.setStatus(workFlow.getUserStatus());

            try {
                AAA securityService = new AAA();
                IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
                String systemBundle = "et/gov/eep/commonApplications/securityServer";
                String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
                if (security.checkAccess(sessionBeanLocal.getUserName(), "saveDocumentRequests", dataset)) {
                    if (updateStatus == 0) {
                        if (documentProvidingServiceBeanLocal.searchByEmpIdAndDocType(hrDocumentRequests) == false && hrDocumentRequests.getDocumentType().getId() == 1) {

                            String prevReqDate = documentProvidingServiceBeanLocal.searchreqdatebyEmpId(hrDocumentRequests);
                            String SPrevdate[] = prevReqDate.split("/");
                            int IPrevdate = Integer.parseInt(SPrevdate[0]);
                            String SPrevmonth[] = prevReqDate.split("/");
                            int IPrevmonth = Integer.parseInt(SPrevmonth[1]);
                            String SPrevyear[] = prevReqDate.split("/");
                            int IPrevyear = Integer.parseInt(SPrevyear[2]);
                            int requestDateDiffrence = ((Idate - IPrevdate) + ((Imonth - IPrevmonth) * 30) + ((Iyear - IPrevyear) * 365));

                            if (requestDateDiffrence > 365) {
                                documentProvidingServiceBeanLocal.save(hrDocumentRequests);
                                wfSave();
                                JsfUtil.addSuccessMessage("Successfully requested" + hrDocumentRequests.getDocumentType().getDocumentType());
                                ClearDocumentRequests();
                            } else {
                                JsfUtil.addFatalMessage("You Must Wait Six-month To Request This Document");
                                ClearDocumentRequests();
                            }
                        } else if (documentProvidingServiceBeanLocal.searchByEmpIdAndDocType(hrDocumentRequests) == false && hrDocumentRequests.getDocumentType().getId().equals(3)) {
                            hrDocumentRequests.setStatus(documentProvidingServiceBeanLocal.searchByEmpIdAndDocType1(hrDocumentRequests));
                            if (hrDocumentRequests.getStatus().equals(0) || hrDocumentRequests.getStatus().equals(1)) {
                                JsfUtil.addFatalMessage("Request is not allowed Unless your previous Request is Revoked(Void)");
                            } else {
                                hrDocumentRequests.setAddressesId(hrAddresses);
                                documentProvidingServiceBeanLocal.save(hrDocumentRequests);
                                wfSave();
                                JsfUtil.addSuccessMessage("Successfully requested" + hrDocumentRequests.getDocumentType().getDocumentType());
                                ClearDocumentRequests();
                            }

                        } else {
                            if (hrAddresses.getAddressId() == null) {
                                hrAddresses.setAddressId(1);
                            } else {
                                hrAddresses.setAddressId(hrAddresses.getAddressId());
                            }
                            hrDocumentRequests.setStatus(0);
                            documentProvidingServiceBeanLocal.save(hrDocumentRequests);
                            wfSave();
                            JsfUtil.addSuccessMessage("Successfully requested" + hrDocumentRequests.getDocumentType().getDocumentType());
                            ClearDocumentRequests();
                        }
                    } else {

                        HrDocumentTypes em = new HrDocumentTypes();
                        em.setId(hrDocumentRequests.getDocumentType().getId());
                        hrDocumentRequests.setDocumentType(em);
                        documentProvidingServiceBeanLocal.update(hrDocumentRequests);
                        // wfSave();
                        ClearDocumentRequests();
                        JsfUtil.addSuccessMessage("Successfully Updated");

                    }
                } else {
                    EventEntry eventEntry = new EventEntry();
                    eventEntry.setSessionId(sessionBeanLocal.getSessionID());
                    eventEntry.setUserId(sessionBeanLocal.getUserId());
                    QName qualifiedName = new QName("", "project");
                    JAXBElement<String> test = new JAXBElement<String>(qualifiedName, String.class, null, sessionBeanLocal.getUserName());
                    eventEntry.setUserLogin(test);

                    JAXBElement<String> userName = new JAXBElement<String>(qualifiedName, String.class, null, sessionBeanLocal.getUserName());
                    eventEntry.setUserLogin(userName);
                    JAXBElement<String> module = new JAXBElement<String>(qualifiedName, String.class, null, "HRMS");
                    eventEntry.setModule(module);
                    JAXBElement<String> program = new JAXBElement<String>(qualifiedName, String.class, null, "saveDocumentRequests");
                    eventEntry.setProgram(program);
//..... add more information by calling fields defined in the object 
                    security.addEventLog(eventEntry, dataset);

                }
            } catch (Exception ex) {
                ex.printStackTrace();
                JsfUtil.addFatalMessage("Error occurred while saving");
            }
        }
    }

    String selectedValue = "";

    public void handleselectOptions(ValueChangeEvent event) {
        if (null != event.getNewValue()) {
            selectedValue = event.getNewValue().toString();
            setSelectedValue(getSelectedValue());
        }
    }

    public void saveDocumentApprove() {
        String Srequestmonth[] = hrDocumentRequests.getRequestDate().split("/");
        int Irequestmonth = Integer.parseInt(Srequestmonth[1]);
        String Srequestyear[] = hrDocumentRequests.getRequestDate().split("/");
        int Irequestyear = Integer.parseInt(Srequestyear[2]);
        String Srequestdate[] = hrDocumentRequests.getRequestDate().split("/");
        int Irequestdate = Integer.parseInt(Srequestdate[0]);
        String SApproveday[] = hrDocumentRequests.getApproveDate().split("/");

        int IApproveday = Integer.parseInt(SApproveday[0]);
        String SapprinveMonth[] = hrDocumentRequests.getApproveDate().split("/");
        int IapproveMonth = Integer.parseInt(SapprinveMonth[1]);
        String SapprinveYear[] = hrDocumentRequests.getApproveDate().split("/");
        int IapproveYear = Integer.parseInt(SapprinveYear[2]);
        int RequestAproveDateGap = ((IApproveday - Irequestdate) + ((IapproveMonth - Irequestmonth) * 30) + ((IapproveYear - Irequestyear) * 365));

        if (Requester.getId() == null) {
            JsfUtil.addErrorMessage("  ");
        } else if (RequestAproveDateGap < 0) {
            JsfUtil.addFatalMessage(" Approve Date should't Exceed Request date ");
        } else {
            try {
                System.out.println("entered here 1");
                AAA securityService = new AAA();
                IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
                String systemBundle = "et/gov/eep/commonApplications/securityServer";
                String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
                if (security.checkAccess(sessionBeanLocal.getUserName(), "saveDocumentApprove", dataset)) {
                    if (selectedValue.equalsIgnoreCase("3")) {
                        hrDocumentRequests.setStatus(3);
                        documentProvidingServiceBeanLocal.update(hrDocumentRequests);
                        System.out.println("entered here 2");
                        wfSave();
                        JsfUtil.addSuccessMessage("Successfully Approved");
                        selectedValue = null;
                        ClearDocumentRequests();
                        chooseCreatePage = false;
                        chooseMainPage = true;
                    } else if (selectedValue.equalsIgnoreCase("2")) {
                        hrDocumentRequests.setStatus(2);
                        documentProvidingServiceBeanLocal.update(hrDocumentRequests);
                        hrDocumentRequests.setStatus(Constants.CHECK_REJECT_VALUE);
                        JsfUtil.addSuccessMessage("Revoked/voided  suceessfully");
                        selectedValue = null;
                        ClearDocumentRequests();
                        chooseCreatePage = false;
                        chooseMainPage = true;;
                    } else {
                        hrDocumentRequests.setStatus(-1);
                        hrDocumentRequests.setApproveDate(null);
//                    hrDocumentRequests.setRemark(null);
                        documentProvidingServiceBeanLocal.update(hrDocumentRequests);
                        hrDocumentRequests.setStatus(Constants.APPROVE_REJECT_VALUE);
                        wfSave();
                        System.out.println("entered here4");
                        JsfUtil.addSuccessMessage("Successfully Reject");
                        selectedValue = null;
                        ClearDocumentRequests();
                        chooseCreatePage = false;
                        chooseMainPage = true;
                    }
                    pnlapproval = false;
                } else {
                    JsfUtil.addFatalMessage("You don't have appropriate permission, please contact the system administrator.");
                    System.out.println("entered here5 ");
                    EventEntry eventEntry = new EventEntry();
                    eventEntry.setSessionId(sessionBeanLocal.getSessionID());
                    eventEntry.setUserId(sessionBeanLocal.getUserId());
                    QName qualifiedName = new QName("", "project");
                    JAXBElement<String> test = new JAXBElement<String>(qualifiedName, String.class, null, sessionBeanLocal.getUserName());
                    eventEntry.setUserLogin(test);

                    JAXBElement<String> userName = new JAXBElement<String>(qualifiedName, String.class, null, sessionBeanLocal.getUserName());
                    eventEntry.setUserLogin(userName);
                    JAXBElement<String> module = new JAXBElement<String>(qualifiedName, String.class, null, "HRMS");
                    eventEntry.setModule(module);
                    JAXBElement<String> program = new JAXBElement<String>(qualifiedName, String.class, null, "saveDocumentApprove");
                    eventEntry.setProgram(program);
//..... add more information by calling fields defined in the object 
                    security.addEventLog(eventEntry, dataset);

                }

            } catch (Exception e) {
                JsfUtil.addFatalMessage("Unable to Approve data.");
            }
        }
    }

    public void Reset() {
        ClearDocumentRequests();
        chooseCreatePage = false;
        chooseMainPage = true;

    }
    int status = 0;

    public void DocumentApprove_processMyEvent(SelectEvent event) {
        hrDocumentRequests = (HrDocumentRequests) event.getObject();
        hrDocumentRequests.setId(hrDocumentRequests.getId());
        hrDocumentRequests = documentProvidingServiceBeanLocal.loadDocumentRequestDetails(hrDocumentRequests.getId());
        setRequester(hrDocumentRequests.getRequesterId());
        hrDepartments = Requester.getDeptId();
        hrJobTypes = Requester.getJobId();
        setHrAddresses(hrDocumentRequests.getAddressesId());
        hrDocumentTypes = hrDocumentRequests.getDocumentType();
        if (hrDocumentRequests.getStatus() == 1) {
            setSelectedValue("1");
        } else if (hrDocumentRequests.getStatus() == 2) {
            setSelectedValue("3");
        } else if (hrDocumentRequests.getStatus() == -3) {
            enableleSaveOrUpdateBtn1 = false;
        } else {
            setSelectedValue("--- select decision---");
        }

        setDecision(1);
        String Shday[] = Requester.getHireDate().split("/");
        int Ihday = Integer.parseInt(Shday[0]);
        String Shmonth[] = Requester.getHireDate().split("/");
        int Ihmonth = Integer.parseInt(Shmonth[1]);
        String Shyear[] = Requester.getHireDate().split("/");
        int Ihyear = Integer.parseInt(Shyear[2]);

        String Scday[] = StringDateManipulation.toDayInEc().split("-");
        int Icday = Integer.parseInt(Scday[2]);
        String Scmonth[] = StringDateManipulation.toDayInEc().split("-");
        int Icmonth = Integer.parseInt(Scmonth[1]);
        String Scyear[] = StringDateManipulation.toDayInEc().split("-");
        int Icyear = Integer.parseInt(Scyear[0]);
        int expday = ((Icday - Ihday) + ((Icmonth - Ihmonth) * 30) + ((Icyear - Ihyear) * 365));
        int expInYear = expday / 365;
        int expInMonth = ((expday % 365) / 30);

        Experience = (expInYear + "-" + "Year And " + expInMonth + "- Month");
        updateStatus = 1;
        pnlapproval = true;
        pnldocgenerate = true;;
        chooseCreatePage = true;
        chooseMainPage = false;
        if (hrDocumentTypes.getId().equals(3)) {
            guranteepnl = true;
        } else {
            guranteepnl = false;
        }
        pnlrWfIcon = true;
        recreateDataModelwf();
    }

    public void DocumentRequest_processMyEvent(SelectEvent event) {
        hrDocumentRequests = (HrDocumentRequests) event.getObject();
        hrDocumentRequests.setId(hrDocumentRequests.getId());
        hrDocumentRequests = documentProvidingServiceBeanLocal.loadDocumentRequestDetails(hrDocumentRequests.getId());
        setRequester(hrDocumentRequests.getRequesterId());
        hrDepartments = Requester.getDeptId();
        hrJobTypes = Requester.getJobId();
        setHrAddresses(hrDocumentRequests.getAddressesId());
        hrDocumentTypes = hrDocumentRequests.getDocumentType();
        if (hrDocumentRequests.getStatus() == 1) {
            setSelectedValue("1");
        } else if (hrDocumentRequests.getStatus() == 2) {
            setSelectedValue("3");
        } else {
            setSelectedValue("--- select decision---");
        }

        setDecision(1);
        String Shday[] = Requester.getHireDate().split("/");
        int Ihday = Integer.parseInt(Shday[0]);
        String Shmonth[] = Requester.getHireDate().split("/");
        int Ihmonth = Integer.parseInt(Shmonth[1]);
        String Shyear[] = Requester.getHireDate().split("/");
        int Ihyear = Integer.parseInt(Shyear[2]);

        String Scday[] = StringDateManipulation.toDayInEc().split("-");
        int Icday = Integer.parseInt(Scday[2]);
        String Scmonth[] = StringDateManipulation.toDayInEc().split("-");
        int Icmonth = Integer.parseInt(Scmonth[1]);
        String Scyear[] = StringDateManipulation.toDayInEc().split("-");
        int Icyear = Integer.parseInt(Scyear[0]);
        int expday = ((Icday - Ihday) + ((Icmonth - Ihmonth) * 30) + ((Icyear - Ihyear) * 365));
        int expInYear = expday / 365;
        int expInMonth = ((expday % 365) / 30);

        Experience = (expInYear + "-" + "Year And " + expInMonth + "- Month");
        updateStatus = 1;
        pnlapproval = true;
        pnldocgenerate = true;
        if (hrDocumentTypes.getId().equals(3)) {
            guranteepnl = true;
        } else {
            guranteepnl = false;
        }
        pnlrWfIcon = true;
        recreateDataModelwf();
    }

    public void DocumentRequest_processMyEvent1(SelectEvent event) {
        hrDocumentRequests = (HrDocumentRequests) event.getObject();
        hrDocumentRequests.setId(hrDocumentRequests.getId());
        hrDocumentRequests = documentProvidingServiceBeanLocal.loadDocumentRequestDetails(hrDocumentRequests.getId());
        setRequester(hrDocumentRequests.getRequesterId());
        hrDepartments = Requester.getDeptId();
        hrJobTypes = Requester.getJobId();
        setHrAddresses(hrDocumentRequests.getAddressesId());
        loadTree();
        hrDocumentTypes = hrDocumentRequests.getDocumentType();

        setDecision(1);
        String Shday[] = Requester.getHireDate().split("/");
        int Ihday = Integer.parseInt(Shday[0]);
        String Shmonth[] = Requester.getHireDate().split("/");
        int Ihmonth = Integer.parseInt(Shmonth[1]);
        String Shyear[] = Requester.getHireDate().split("/");
        int Ihyear = Integer.parseInt(Shyear[2]);

        String Scday[] = StringDateManipulation.toDayInEc().split("-");
        int Icday = Integer.parseInt(Scday[2]);
        String Scmonth[] = StringDateManipulation.toDayInEc().split("-");
        int Icmonth = Integer.parseInt(Scmonth[1]);
        String Scyear[] = StringDateManipulation.toDayInEc().split("-");
        int Icyear = Integer.parseInt(Scyear[0]);
        int expday = ((Icday - Ihday) + ((Icmonth - Ihmonth) * 30) + ((Icyear - Ihyear) * 365));
        int expInYear = expday / 365;
        int expInMonth = ((expday % 365) / 30);

        Experience = (expInYear + "-" + "Year And " + expInMonth + "- Month");
        updateStatus = 1;
        pnlapproval = true;
        pnldocgenerate = true;
        chooseCreatePage = true;
        chooseMainPage = false;
        if (hrDocumentTypes.getId().equals(3)) {
            guranteepnl = true;
        } else {
            guranteepnl = false;
        }
        createOrSearchBundle = "Search";
        setIcone("ui-icon-search");
        updateStatus = 1;
        saveorUpdateBundle = "Update";
        pnlrWfIcon = true;
        recreateDataModelwf();
        btnNewRender = true;
    }

    public void DocumentGenerate_processMyEvent(SelectEvent event) {
        hrDocumentRequests = (HrDocumentRequests) event.getObject();
        hrDocumentRequests.setId(hrDocumentRequests.getId());
        hrDocumentRequests = documentProvidingServiceBeanLocal.loadDocumentRequestDetails(hrDocumentRequests.getId());
        setRequester(hrDocumentRequests.getRequesterId());
        setHrAddresses(hrDocumentRequests.getAddressesId());
        hrDepartments = Requester.getDeptId();
        hrJobTypes = Requester.getJobId();
        hrDocumentTypes = hrDocumentRequests.getDocumentType();
        String Shday[] = Requester.getHireDate().split("/");
        int Ihday = Integer.parseInt(Shday[0]);
        String Shmonth[] = Requester.getHireDate().split("/");
        int Ihmonth = Integer.parseInt(Shmonth[1]);
        String Shyear[] = Requester.getHireDate().split("/");
        int Ihyear = Integer.parseInt(Shyear[2]);

        String Scday[] = StringDateManipulation.toDayInEc().split("-");
        int Icday = Integer.parseInt(Scday[2]);
        String Scmonth[] = StringDateManipulation.toDayInEc().split("-");
        int Icmonth = Integer.parseInt(Scmonth[1]);
        String Scyear[] = StringDateManipulation.toDayInEc().split("-");
        int Icyear = Integer.parseInt(Scyear[0]);
        int expday = ((Icday - Ihday) + ((Icmonth - Ihmonth) * 30) + ((Icyear - Ihyear) * 365));
        int expInYear = expday / 365;
        int expInMonth = ((expday % 365) / 30);

        Experience = (expInYear + "-" + "Year And " + expInMonth + "- Month");
        chooseCreatePage = true;
        chooseMainPage = false;
        createOrSearchBundle = "Search";
        setIcone("ui-icon-search");
        updateStatus = hrDocumentRequests.getDocumentType().getId();
        if (updateStatus == 1) {
            ExperienceorConformationBundle = "Generate Experience";
        } else {
            ExperienceorConformationBundle = "Generate Employee Conformation";
        }
    }

    public void saveGenerateDocument() {
        try {
            System.out.println("inside 1");
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(sessionBeanLocal.getUserName(), "saveGenerateDocument", dataset)) {
                if (selectedValue.equalsIgnoreCase("-3")) {
                    hrDocumentRequests.setStatus(-3);

                } else {
                    hrDocumentRequests.setStatus(3);
                }

                documentProvidingServiceBeanLocal.update(hrDocumentRequests);
                wfSave();
                pnldocgenerate = false;
                ClearDocumentRequests();
                requestsListDataModel = new ArrayDataModel<>();
                JsfUtil.addSuccessMessage("Data successfully saved");
            } else {
                System.out.println("inside 3");
                EventEntry eventEntry = new EventEntry();
                eventEntry.setSessionId(sessionBeanLocal.getSessionID());
                eventEntry.setUserId(sessionBeanLocal.getUserId());
                QName qualifiedName = new QName("", "project");
                JAXBElement<String> test = new JAXBElement<String>(qualifiedName, String.class, null, sessionBeanLocal.getUserName());
                eventEntry.setUserLogin(test);

                JAXBElement<String> userName = new JAXBElement<String>(qualifiedName, String.class, null, sessionBeanLocal.getUserName());
                eventEntry.setUserLogin(userName);
                JAXBElement<String> module = new JAXBElement<String>(qualifiedName, String.class, null, "HRMS");
                eventEntry.setModule(module);
                JAXBElement<String> program = new JAXBElement<String>(qualifiedName, String.class, null, "saveGenerateDocument");
                eventEntry.setProgram(program);
//..... add more information by calling fields defined in the object 
                security.addEventLog(eventEntry, dataset);

            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public void generate() {

        generateexp = true;

    }
    int doctype;

    public void docTypelistner(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            doctype = Integer.valueOf(event.getNewValue().toString());
            if (doctype == 3) {
                guranteepnl = true;
            } else {
                guranteepnl = false;
            }
        }

    }

//</editor-fold>
}
