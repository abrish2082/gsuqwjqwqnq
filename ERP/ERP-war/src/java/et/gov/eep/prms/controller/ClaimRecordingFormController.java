package et.gov.eep.prms.controller;

   //<editor-fold defaultstate="collapsed" desc="Imports">
import et.gov.eep.commonApplications.businessLogic.WfMmsProcessedBeanLocal;
import et.gov.eep.commonApplications.businessLogic.WfPrmsProcessedBeanLocal;
import et.gov.eep.commonApplications.entity.ColumnNameResolver;
import et.gov.eep.commonApplications.entity.WfMmsProcessed;
import et.gov.eep.commonApplications.entity.WfPrmsProcessed;
import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.fcms.entity.FmsLuCurrency;
import et.gov.eep.hrms.entity.organization.HrDepartments;
import et.gov.eep.hrms.mapper.organization.HrDepartmentsFacade;
import et.gov.eep.mms.businessLogic.MmsItemRegisrtationBeanLocal;
import et.gov.eep.mms.entity.MmsItemRegistration;
import et.gov.eep.prms.businessLogic.ClaimReordingFormBeanLocal;
import et.gov.eep.prms.entity.PrmsClaimRecordingForm;
import et.gov.eep.prms.entity.PrmsClaimDetail;
import et.gov.eep.prms.entity.PrmsContract;
import et.gov.eep.prms.entity.PrmsContractAmendment;
import et.gov.eep.prms.entity.PrmsContractDetail;
import et.gov.eep.prms.entity.PrmsLcRigistration;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import org.primefaces.event.RowEditEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import securityBean.SessionBean;
import webService.AAA;
import webService.EventEntry;
import webService.IAdministration;
import securityBean.WorkFlow;
import securityBean.Constants;
//</editor-fold>

@Named(value = "claimRecordingFormController")
@ViewScoped

    //<editor-fold defaultstate="collapsed" desc="Inject EJBs">
public class ClaimRecordingFormController implements Serializable {

    @EJB
    ClaimReordingFormBeanLocal claimReordingFormBeanLocal;
    @EJB
    WfPrmsProcessedBeanLocal wfPrmsProcessedBeanLocal;

    @EJB
    MmsItemRegisrtationBeanLocal itemRegistrationBeanLocal;
    @EJB
    WfMmsProcessedBeanLocal wfMmsProcessedBeanLocal;
    @Inject
    SessionBean SessionBean;
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Inject Entities">
    @Inject
    WfMmsProcessed wfMmsProcessed;
    @Inject
    PrmsClaimRecordingForm prmsClaimRecordingForm;
    @Inject
    PrmsClaimDetail prmsClaimDetail;
    @Inject
    PrmsContract prmsContract;
    @Inject
    PrmsLcRigistration prmsLcRigistration;
    @Inject
    PrmsContractDetail papmsContractDetail;
    @Inject
    HrDepartments departments;
    @Inject
    FmsLuCurrency fmsLuCurrency;
    @Inject
    private MmsItemRegistration itemRegistration;
    @Inject
    private WfPrmsProcessed wfPrmsProcessed;
    @Inject
    private WorkFlow workFlow;
    @Inject
    private PrmsContractAmendment prmsContractAmendment;
    @Inject
    SessionBean sessionBean;
    @EJB
    HrDepartmentsFacade hrDepartmentsFacade;
     @Inject
    ColumnNameResolver columnNameResolver;
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Declare Lists and models">
    DataModel<PrmsClaimRecordingForm> prmsClaimRecordingFormsModel;
    DataModel<PrmsClaimDetail> prmsClaimDetailsModels;
    private DataModel<WfPrmsProcessed> wfPrmsProcessedDModel;
    List<PrmsClaimRecordingForm> prmsClaimRecordingFormList;
    List<HrDepartments> allDepartmentsList = new ArrayList<>();
    List<PrmsContract> listOfContract;
    List<WfMmsProcessed> WfList;
    List<PrmsLcRigistration> listOfLcNO;
    List<FmsLuCurrency> currencyLists;
    private List<PrmsContractAmendment> prmsContractAmendmentLst;
    private List<PrmsClaimRecordingForm> prmscrfList;
    Set<String> actionPlnDetlCheck = new HashSet<>();
    List<ColumnNameResolver> ColumnNameResolverList = new ArrayList<>();
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Declare Fields">
    private String saveorUpdateBundle = "Save";
    int saveStatus;

    private boolean disableBtnCreate = false;
    private String duplicattion = null;
    private boolean renderPnlCreateParty = false;
    private boolean renderPnlManPage = true;
    private boolean renderDecision = false;
    private boolean renderpnlToSearchPage;
    private boolean isRenderedIconNitification = true;
    private String activeIndex;
    private static List<HrDepartments> araListe;
    private TreeNode root;
    private TreeNode root2;
    private TreeNode selectedNode;
    TreeNode DepartmentList;
    private String selectedValue = "";
    private String createOrSearchBundle = "New";
    private String addOrModifyBundle = "Add";
    private String icon = "ui-icon-plus";
    private String loggerName;
    private int requestNotificationCounter = 0;
    private boolean isRendercreate;
    private boolean isRenderNotify;
    int updateStatus = 0;
    private int selectedRowIndex;
    WorkFlow workflow = new WorkFlow();
    PrmsClaimRecordingForm SelectPrmsClaimRecordingForm;
    private String userName;
    private List<PrmsClaimRecordingForm> mmsClaimRColumnNameList;
//</editor-fold>

    //default non-aurgument method
    public ClaimRecordingFormController() {
    }

    //<editor-fold defaultstate="collapsed" desc="PostConstruct">
    @PostConstruct
    public void init() {
        allDepartmentsList = hrDepartmentsFacade.findAll();
        root = new DefaultTreeNode("Root", null);
        System.out.println("====root===" + root);
        recursive(allDepartmentsList, 0, root);
        System.out.println("---after root--" + root2);
        root2 = getRoot();
        wfPrmsProcessed.setProcessedBy(workFlow.getUserAccount());
          setLoggerName(workFlow.getUserName());
          System.out.println("is app---" + workFlow.isApproveStatus() + "is check===" + workFlow.isCheckStatus() + "is prep==" + workFlow.isPrepareStatus());
          ColumnNameResolverList = claimReordingFormBeanLocal.getColumnNameList();
            wfPrmsProcessed.setProcessedBy(SessionBean.getUserId());
           setUserName(SessionBean.getUserName());
           System.out.println("is App==" + workflow.isApproveStatus() + "is chech==" + workflow.isCheckStatus() + "is prepa==" + workflow.isPrepareStatus());
        if (workFlow.isPrepareStatus()) {
            renderDecision = false;
            isRendercreate = true;
            isRenderNotify = false;
        } else if (workFlow.isApproveStatus()) {
            renderDecision = true;
            isRendercreate = false;
            isRenderNotify = true;
        } else if (workFlow.isCheckStatus()) {
            renderDecision = true;
            isRendercreate = false;
            isRenderNotify = true;
            
        }
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="getter and setter of objects">
    public WfMmsProcessed getWfMmsProcessed() {
        return wfMmsProcessed;
    }

    public void setWfMmsProcessed(WfMmsProcessed wfMmsProcessed) {
        this.wfMmsProcessed = wfMmsProcessed;
    }

    public PrmsClaimRecordingForm getSelectPrmsClaimRecordingForm() {
        return SelectPrmsClaimRecordingForm;
    }

    public void setSelectPrmsClaimRecordingForm(PrmsClaimRecordingForm SelectPrmsClaimRecordingForm) {
        this.SelectPrmsClaimRecordingForm = SelectPrmsClaimRecordingForm;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="getter and setter of Lists and models">
    public List<WfMmsProcessed> getWfList() {
        return WfList;
    }

    public void setWfList(List<WfMmsProcessed> WfList) {
        this.WfList = WfList;
    }

    public ColumnNameResolver getColumnNameResolver() {
        return columnNameResolver;
    }

    public void setColumnNameResolver(ColumnNameResolver columnNameResolver) {
        this.columnNameResolver = columnNameResolver;
    }

    public List<ColumnNameResolver> getColumnNameResolverList() {
        return ColumnNameResolverList;
    }

    public void setColumnNameResolverList(List<ColumnNameResolver> ColumnNameResolverList) {
        this.ColumnNameResolverList = ColumnNameResolverList;
    }
    
    
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="getter and setter of Variables">
    public boolean isRenderpnlToSearchPage() {
        return renderpnlToSearchPage;
    }

    public void setRenderpnlToSearchPage(boolean renderpnlToSearchPage) {
        this.renderpnlToSearchPage = renderpnlToSearchPage;
    }

    public boolean isRenderDecision() {
        return renderDecision;
    }

    public void setRenderDecision(boolean renderDecision) {
        this.renderDecision = renderDecision;
    }

    public boolean isIsRenderedIconNitification() {
        return isRenderedIconNitification;
    }

    public void setIsRenderedIconNitification(boolean isRenderedIconNitification) {
        this.isRenderedIconNitification = isRenderedIconNitification;
    }

    public String getSelectedValue() {
        return selectedValue;
    }

    public void setSelectedValue(String selectedValue) {
        this.selectedValue = selectedValue;
    }

    public String getCreateOrSearchBundle() {
        return createOrSearchBundle;
    }

    public void setCreateOrSearchBundle(String createOrSearchBundle) {
        this.createOrSearchBundle = createOrSearchBundle;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getLastClaimNo() {
        return lastClaimNo;
    }

    public void setLastClaimNo(String lastClaimNo) {
        this.lastClaimNo = lastClaimNo;
    }

    public String getNewClaimNo() {
        return newClaimNo;
    }

    public void setNewClaimNo(String newClaimNo) {
        this.newClaimNo = newClaimNo;
    }

    public int getSelectedRowIndex() {
        return selectedRowIndex;
    }

    public void setSelectedRowIndex(int selectedRowIndex) {
        this.selectedRowIndex = selectedRowIndex;
    }

    public List<PrmsClaimRecordingForm> getMmsClaimRColumnNameList() {
        if (mmsClaimRColumnNameList == null) {
            mmsClaimRColumnNameList = new ArrayList<>();
            mmsClaimRColumnNameList = claimReordingFormBeanLocal.getMmsClaimRColumnNameList();

        }
        return mmsClaimRColumnNameList;
    }

    public void setMmsClaimRColumnNameList(List<PrmsClaimRecordingForm> mmsClaimRColumnNameList) {
        this.mmsClaimRColumnNameList = mmsClaimRColumnNameList;
    }

    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Event changes">
    public void onRowEdit(RowEditEvent event) {
        renderPnlCreateParty = true;
        renderPnlManPage = false;
        activeIndex = "0";
        saveorUpdateBundle = "Update";
        disableBtnCreate = true;
        updateStatus = 1;

        int rowIndex = prmsClaimRecordingFormsModel.getRowIndex();
        prmsClaimRecordingForm = prmsClaimRecordingForms.get(rowIndex);

        recreatClaimRecordingFormModel();

    }
     public void changeEventColumnName(ValueChangeEvent changeEvent) {
        if (changeEvent.getNewValue() != null) {
            columnNameResolver.setCol_Name_FromTable(changeEvent.getNewValue().toString());
            columnNameResolver.setParsed_Col_Name(ColumnParser(columnNameResolver.getCol_Name_FromTable()));
            prmsClaimRecordingForm.setColumnName(columnNameResolver.getCol_Name_FromTable());
        }
    }

    public String ColumnParser(String col_name) {
        String col = col_name.replace("_", " ");
        System.out.println("col==" + col);
        return col.toLowerCase()+":";
    }
    /*This method is used to column Name Change Event
     */

    public void columnNameChangeEvent(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            prmsClaimRecordingForm.setColumnName(event.getNewValue().toString());
            prmsClaimRecordingForm.setColumnValue(null);
        }
        
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="other methods">
    public void goBackToSearchpageBtnAction() {
        renderpnlToSearchPage = false;
        renderPnlCreateParty = false;
        renderPnlManPage = true;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Save or Update method">
    public String saveClaim() {

        try {
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(sessionBean.getUserName(), "saveClaim", dataset)) {
                if (saveorUpdateBundle.equals("Save")) {
                    if (prmsClaimDetailsModels.getRowCount() > 0) {
                        if (saveStatus == 0) {
                            try {

                                prmsClaimRecordingForm.setSupplier(prmsContract.getSuppId().getVendorName());
                                prmsClaimRecordingForm.setContractId(prmsContract);
                                prmsClaimRecordingForm.setClaimNo(newClaimNo);
                                prmsClaimRecordingForm.setCurrencyId(fmsLuCurrency);
                                prmsClaimRecordingForm.getWfPrmsProcessedLists().add(wfPrmsProcessed);
                                wfPrmsProcessed.setClaimId(prmsClaimRecordingForm);
                                prmsClaimRecordingForm.setPreparedBy(wfPrmsProcessed.getProcessedBy());
                                prmsClaimRecordingForm.setStatus(Constants.PREPARE_VALUE);
                                prmsClaimRecordingForm.setProcessedOn(wfPrmsProcessed.getProcessedOn());
                                wfPrmsProcessed.setDecision(String.valueOf(prmsClaimRecordingForm.getStatus()));
                                claimReordingFormBeanLocal.create(prmsClaimRecordingForm);
                                JsfUtil.addSuccessMessage("Claim recording Information is Saved successfully!!");
                                clear();
                            } catch (Exception e) {
                                e.printStackTrace();
                                JsfUtil.addFatalMessage("some thing is going to error" + e);
                                clear();
                            }
                        }
                    } else {
                        JsfUtil.addFatalMessage("Atleast One Detail data is Required");
                    }
                } else if (workFlow.isPrepareStatus() && saveorUpdateBundle.equalsIgnoreCase("Update")) {
                    if (prmsClaimDetailsModels.getRowCount() > 0) {
                        try {
                            prmsClaimRecordingForm.setProcessedOn(wfPrmsProcessed.getProcessedOn());
                            claimReordingFormBeanLocal.update(prmsClaimRecordingForm);
                            JsfUtil.addSuccessMessage("Claim Recording Information is updated Successfully");
                            saveorUpdateBundle = "Save";
                            clear();
                        } catch (Exception e) {
                            JsfUtil.addFatalMessage("error== w/n data modification" + e);
                            clear();
                        }
                    } else {
                        JsfUtil.addFatalMessage("Atleast One Detail data is Required");
                    }
                } else if ((workFlow.isApproveStatus() || workFlow.isCheckStatus()) && saveorUpdateBundle.equalsIgnoreCase("Update")) {
                    try {
                        prmsClaimRecordingForm.setContractId(prmsContract);
                        if (selectedValue.equalsIgnoreCase("Approve") && workFlow.isApproveStatus()) {
                            workFlow.setUserStatus(Constants.APPROVE_VALUE);
                            prmsClaimRecordingForm.setStatus(Constants.APPROVE_VALUE);
                            wfPrmsProcessed.setDecision(String.valueOf(Constants.APPROVE_VALUE));
                        } else if (selectedValue.equalsIgnoreCase("Approve") && workFlow.isCheckStatus()) {
                            workFlow.setUserStatus(Constants.CHECK_APPROVE_VALUE);
                            prmsClaimRecordingForm.setStatus(Constants.CHECK_APPROVE_VALUE);
                            wfPrmsProcessed.setDecision(String.valueOf(Constants.CHECK_APPROVE_VALUE));
                        } else if (selectedValue.equalsIgnoreCase("Reject") && workFlow.isApproveStatus()) {
                            workFlow.setUserStatus(Constants.APPROVE_REJECT_VALUE);
                            prmsClaimRecordingForm.setStatus(Constants.APPROVE_REJECT_VALUE);
                            wfPrmsProcessed.setDecision(String.valueOf(Constants.APPROVE_REJECT_VALUE));
                        } else if (selectedValue.equalsIgnoreCase("Reject") && workFlow.isCheckStatus()) {
                            workFlow.setUserStatus(Constants.CHECK_REJECT_VALUE);
                            prmsClaimRecordingForm.setStatus(Constants.CHECK_REJECT_VALUE);
                            wfPrmsProcessed.setDecision(String.valueOf(Constants.CHECK_REJECT_VALUE));
                        }
                        wfPrmsProcessed.setClaimId(prmsClaimRecordingForm);
                        claimReordingFormBeanLocal.update(prmsClaimRecordingForm);
                        wfPrmsProcessedBeanLocal.savewf(wfPrmsProcessed);
                        JsfUtil.addSuccessMessage("Claim recording information is Decided");
                        saveorUpdateBundle = "Save";
                        clear();
                    } catch (Exception e) {
                        JsfUtil.addFatalMessage("error== w/n data modification" + e);
                        clear();
                    }
                }

            } else {
                JsfUtil.addFatalMessage("You don't have appropriate permission, please contact the system administrator. ");
                EventEntry eventEntry = new EventEntry();
                eventEntry.setSessionId(sessionBean.getSessionID());
                eventEntry.setUserId(sessionBean.getUserId());
                QName qualifiedName = new QName("", "project");
                JAXBElement<String> test = new JAXBElement<String>(qualifiedName, String.class, null, sessionBean.getUserName());
                eventEntry.setUserLogin(test);
                security.addEventLog(eventEntry, dataset);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Getter & Setter">
    public DataModel<PrmsClaimDetail> getPrmsClaimDetailsModels() {
        if (prmsClaimDetailsModels == null) {
            prmsClaimDetailsModels = new ListDataModel();
        }
        return prmsClaimDetailsModels;
    }

    public void setPrmsClaimDetailsModels(DataModel<PrmsClaimDetail> prmsClaimDetailsModels) {
        this.prmsClaimDetailsModels = prmsClaimDetailsModels;
    }

    public void setSelectedItem(ValueChangeEvent e) {
        System.out.println("=================item id=" + e.getNewValue().toString());
        itemRegistration.setMaterialId(Integer.parseInt(e.getNewValue().toString()));
        itemRegistration = itemRegistrationBeanLocal.getMmsItemInfoByMatId(itemRegistration);
        System.out.println("=================item name=" + itemRegistration.getMatName());

    }

    public PrmsClaimDetail getPrmsClaimDetail() {
        if (prmsClaimDetail == null) {
            prmsClaimDetail = new PrmsClaimDetail();
        }
        return prmsClaimDetail;
    }

    public void setPrmsClaimDetail(PrmsClaimDetail prmsClaimDetail) {
        this.prmsClaimDetail = prmsClaimDetail;
    }

    List<PrmsClaimRecordingForm> prmsClaimRecordingForms;

    public List<PrmsClaimRecordingForm> getPrmsClaimRecordingForms() {
        if (prmsClaimRecordingForms == null) {
            prmsClaimRecordingForms = new ArrayList<>();
        }
        return prmsClaimRecordingForms;
    }

    public void setPrmsClaimRecordingForms(List<PrmsClaimRecordingForm> prmsClaimRecordingForms) {
        this.prmsClaimRecordingForms = prmsClaimRecordingForms;
    }

    public PrmsClaimRecordingForm getPrmsClaimRecordingForm() {
        if (prmsClaimRecordingForm == null) {
            prmsClaimRecordingForm = new PrmsClaimRecordingForm();
        }

        return prmsClaimRecordingForm;
    }

    public void setPrmsClaimRecordingForm(PrmsClaimRecordingForm PrmsClaimRecordingForm) {
        this.prmsClaimRecordingForm = PrmsClaimRecordingForm;
    }

    public PrmsLcRigistration getPrmsLcRigistration() {
        if (prmsLcRigistration == null) {
            prmsLcRigistration = new PrmsLcRigistration();
        }
        return prmsLcRigistration;
    }

    public void setPrmsLcRigistration(PrmsLcRigistration prmsLcRigistration) {
        this.prmsLcRigistration = prmsLcRigistration;
    }

    public List<PrmsLcRigistration> getListOfLcNO() {
        if (listOfLcNO == null) {
            listOfLcNO = new ArrayList<>();
        }
        return listOfLcNO;
    }

    public void setListOfLcNO(List<PrmsLcRigistration> listOfLcNO) {
        this.listOfLcNO = listOfLcNO;
    }

    public void setSelectedLCNo(ValueChangeEvent e) {
        prmsLcRigistration = (PrmsLcRigistration) e.getNewValue();
        System.out.println("=================LC no=" + prmsLcRigistration.getLcNo() + "========permit no=" + prmsLcRigistration.getForeignId().getFeNumber());

    }

    public PrmsContractDetail getPrmsContractDetail() {
        if (papmsContractDetail == null) {
            papmsContractDetail = new PrmsContractDetail();
        }
        return papmsContractDetail;
    }

    public void setPrmsContractDetail(PrmsContractDetail prmsContractDetail) {
        this.papmsContractDetail = prmsContractDetail;
    }

    public String getSaveorUpdateBundle() {
        return saveorUpdateBundle;
    }

    public void setSaveorUpdateBundle(String saveorUpdateBundle) {
        this.saveorUpdateBundle = saveorUpdateBundle;
    }

    public int getSaveStatus() {
        return saveStatus;
    }

    public void setSaveStatus(int saveStatus) {
        this.saveStatus = saveStatus;
    }

    private List<MmsItemRegistration> itemList;

    public void getItemList(ValueChangeEvent e) {
        MmsItemRegistration matInfo;
        prmsContract = (PrmsContract) e.getNewValue();
        prmsContractAmendmentLst = claimReordingFormBeanLocal.checkAmendOrNot(prmsContract);
        if (prmsContractAmendmentLst.size() > 0) {
            prmsContractAmendment = claimReordingFormBeanLocal.getContractAmendment(prmsContract);
            prmsContract.setContractId(prmsContractAmendment.getContractId().getContractId());
            prmsContract.setSuppId(prmsContractAmendment.getSuppId());
            prmsContract.setContractamount(prmsContractAmendment.getContractamount());
        }

        for (int i = 0; i < prmsContract.getAwardId().getPrmsAwardDetailList().size(); i++) {
            matInfo = new MmsItemRegistration();
            matInfo = prmsContract.getAwardId().getPrmsAwardDetailList().get(i).getMaterialId();
            itemList.add(matInfo);
        }
        contractAmount();

    }

    public void getSupplierDet() {
        prmsClaimRecordingForm.setSupplier(prmsContract.getSuppId().getVendorName());
    }

    public List<PrmsContract> getListOfContract() {
        if (listOfContract == null) {
            listOfContract = new ArrayList<>();
            listOfContract = claimReordingFormBeanLocal.listOfContractNO();
        }

        return listOfContract;
    }

    public void setListOfContract(List<PrmsContract> listOfContract) {
        this.listOfContract = listOfContract;
    }

    public PrmsContract getPrmsContract() {
        return prmsContract;
    }

    public void setPrmsContract(PrmsContract prmsContract) {
        this.prmsContract = prmsContract;
    }

    public List<FmsLuCurrency> getCurrencyLists() {
        if (currencyLists == null) {
            currencyLists = new ArrayList<>();

        }
        return currencyLists;
    }

    public void setCurrencyLists(List<FmsLuCurrency> currencyLists) {
        this.currencyLists = currencyLists;
    }

    public FmsLuCurrency getFmsLuCurrency() {
        return fmsLuCurrency;
    }

    public void setSelectedCurrency(ValueChangeEvent e) {
        fmsLuCurrency = (FmsLuCurrency) e.getNewValue();
        System.out.println("===================curee name=" + fmsLuCurrency.getCurrency() + " === id=" + fmsLuCurrency.getCurrencyId());
    }

    public void setFmsLuCurrency(FmsLuCurrency fmsLuCurrency) {
        this.fmsLuCurrency = fmsLuCurrency;
    }

    public List<HrDepartments> getAllDepartmentsList() {
        return allDepartmentsList;
    }

    public void setAllDepartmentsList(List<HrDepartments> allDepartmentsList) {
        this.allDepartmentsList = allDepartmentsList;
    }

    public HrDepartments getDepartments() {
        if (departments == null) {
            departments = new HrDepartments();
        }
        return departments;
    }

    public void setDepartments(HrDepartments departments) {
        this.departments = departments;
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

    public void onNodeSelect(NodeSelectEvent event) {

        selectedNode = event.getTreeNode();
        int key = Integer.parseInt((selectedNode.getData().toString()).split("--")[0]);

        departments = claimReordingFormBeanLocal.getSelectDepartement(key);

        prmsClaimRecordingForm.setDepId(departments);
        RequestContext requestContext = RequestContext.getCurrentInstance();
        requestContext.execute("PF('dlg').hide();");

    }

    public DataModel<PrmsClaimRecordingForm> getPrmsClaimRecordingFormsModel() {
        if (prmsClaimRecordingFormsModel == null) {
            prmsClaimRecordingFormsModel = new ArrayDataModel<>();
        }
        return prmsClaimRecordingFormsModel;
    }

    public void setPrmsClaimRecordingFormsModel(DataModel<PrmsClaimRecordingForm> prmsClaimRecordingFormsModel) {
        this.prmsClaimRecordingFormsModel = prmsClaimRecordingFormsModel;
    }

    public List<PrmsClaimRecordingForm> getActionPlanList() {
        if (prmsClaimRecordingFormList == null) {
            prmsClaimRecordingFormList = new ArrayList<>();
        }
        return prmsClaimRecordingFormList;
    }

    public Set<String> getActionPlnDetlCheck() {
        return actionPlnDetlCheck;
    }

    public void setActionPlnDetlCheck(Set<String> actionPlnDetlCheck) {
        this.actionPlnDetlCheck = actionPlnDetlCheck;
    }

    public boolean isDisableBtnCreate() {
        return disableBtnCreate;
    }

    public void setDisableBtnCreate(boolean disableBtnCreate) {
        this.disableBtnCreate = disableBtnCreate;
    }

    public String getDuplicattion() {
        return duplicattion;
    }

    public void setDuplicattion(String duplicattion) {
        this.duplicattion = duplicattion;
    }

    public boolean isRenderPnlCreateParty() {
        return renderPnlCreateParty;
    }

    public void setRenderPnlCreateParty(boolean renderPnlCreateParty) {
        this.renderPnlCreateParty = renderPnlCreateParty;
    }

    public boolean isRenderPnlManPage() {
        return renderPnlManPage;
    }

    public void setRenderPnlManPage(boolean renderPnlManPage) {
        this.renderPnlManPage = renderPnlManPage;
    }

    public String getActiveIndex() {
        return activeIndex;
    }

    public void setActiveIndex(String activeIndex) {
        this.activeIndex = activeIndex;
    }

    public int getUpdateStatus() {
        return updateStatus;
    }

    public void setUpdateStatus(int updateStatus) {
        this.updateStatus = updateStatus;
    }

    public SessionBean getSessionBean() {
        if (sessionBean == null) {
            sessionBean = new SessionBean();
        }
        return sessionBean;
    }

    public void setSessionBean(SessionBean sessionBean) {
        this.sessionBean = sessionBean;
    }

    public WorkFlow getWorkflow() {
        if (workflow == null) {
            workflow = new WorkFlow();
        }
        return workflow;
    }

    public void setWorkflow(WorkFlow workflow) {
        this.workflow = workflow;
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="ClearPages">
    public void clear() {
        prmsClaimRecordingForm = null;
        prmsContract = null;
        fmsLuCurrency = null;
        departments = null;
        prmsClaimDetailsModels = null;
        prmsClaimDetail = null;
        wfPrmsProcessed.setProcessedOn(null);
        wfPrmsProcessed.setCommentGiven(null);
        addOrModifyBundle = "Add";
    }

//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Search & Save ">
    String lastClaimNo = "0";
    String newClaimNo;

    public String generateClaimNo() {
        if (saveorUpdateBundle.equals("Update")) {
            newClaimNo = prmsClaimRecordingForm.getClaimNo();
        } else {
            PrmsClaimRecordingForm lastClaimNoObj = claimReordingFormBeanLocal.getLastClaimNo();
            if (lastClaimNoObj != null) {
                lastClaimNo = lastClaimNoObj.getClaimId().toString();
            }
            DateFormat f = new SimpleDateFormat("yyyy");
            Date now = new Date();

            int newClaim;
            if (lastClaimNo.equals("0")) {
                newClaim = 1;
                newClaimNo = "ClaimNo-" + newClaim + "/" + f.format(now);

            } else {
                String[] lastClaimNos = lastClaimNo.split("-");
                String lastDatesPatern = lastClaimNos[0];
                String[] lastDatesPaterns = lastDatesPatern.split("/");
                newClaim = Integer.parseInt(lastDatesPaterns[0]);
                newClaim = newClaim + 1;
                newClaimNo = "ClaimNo-" + newClaim + "/" + f.format(now);
            }
        }
        return newClaimNo;

    }

//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="RowSelect &Update">
    public void rowSelect(SelectEvent event) {

        prmsClaimRecordingForm = (PrmsClaimRecordingForm) event.getObject();
        prmsClaimRecordingForm.setClaimId(prmsClaimRecordingForm.getClaimId());
        currencyLists = claimReordingFormBeanLocal.getCurrencyName();
        fmsLuCurrency = prmsClaimRecordingForm.getCurrencyId();
        prmsContract = prmsClaimRecordingForm.getContractId();
        prmsLcRigistration = prmsClaimRecordingForm.getLcId();
        departments = prmsClaimRecordingForm.getDepId();
        if (workFlow.isPrepareStatus()) {
            wfPrmsProcessed.setProcessedOn(prmsClaimRecordingForm.getProcessedOn());
        }
        renderPnlManPage = false;
        renderPnlCreateParty = true;
        renderpnlToSearchPage = true;
        saveorUpdateBundle = "Update";
        recreatClaimRecordingFormModel();
        reCreatModel();
    }

    public void recreatClaimRecordingFormModel() {
        prmsClaimRecordingFormsModel = null;
        prmsClaimRecordingFormsModel = new ListDataModel(new ArrayList(getPrmsClaimRecordingForms()));
    }

    public void edit() {
        addOrModifyBundle = "Modify";
        checkStatus = true;
        prmsClaimDetail = prmsClaimDetailsModels.getRowData();
        selectedRowIndex = prmsClaimDetailsModels.getRowIndex();
        itemRegistration = prmsClaimDetail.getMaterialId();
        prmsClaimDetail.setMaterialId(itemRegistration);
        MmsItemRegistration matInfo;
        matInfo = new MmsItemRegistration();
        for (int i = 0; i < prmsContract.getAwardId().getPrmsAwardDetailList().size(); i++) {
            matInfo = prmsContract.getAwardId().getPrmsAwardDetailList().get(i).getMaterialId();
            itemList.add(matInfo);
        }

    }

    
    
    
    public void searchClaimRNo() {
        System.out.println("in search");
        prmsClaimRecordingForm.setPreparedBy(sessionBean.getUserId());   
        prmsClaimRecordingForms = claimReordingFormBeanLocal.getClaimListsByParameter(prmsClaimRecordingForm);
        recreatClaimRecordingFormModel();

    }

    public void clearDetail() {
        prmsClaimDetail = null;
        itemRegistration = null;
    }

    public void Remainedclaimquantity() {
        System.out.println("1");
        if (prmsClaimRecordingForm.getDeliveredAmount() <= prmsContract.getContractamount()) {
            System.out.println("2");
            prmsClaimRecordingForm.setReturnedClaimAmount(prmsContract.getContractamount() - prmsClaimRecordingForm.getDeliveredAmount());
        } else {
            System.out.println("is impossible");
            JsfUtil.addFatalMessage("Please Insert proper Delivetry quantity");
        }

    }

    private boolean checkStatus;

    public boolean isCheckStatus() {
        return checkStatus;
    }

    public void setCheckStatus(boolean checkStatus) {
        this.checkStatus = checkStatus;
    }

    public void addTable() {
        if (prmsClaimRecordingForm.getPrmsClaimDetailList().size() >= 0) {
            if (checkStatus == false) {
                prmsClaimDetail.setMaterialId(itemRegistration);
                prmsClaimRecordingForm.getPrmsClaimDetailList().add(prmsClaimDetail);
                prmsClaimDetail.setClaimId(prmsClaimRecordingForm);
            } else if (checkStatus == true) {
                prmsClaimRecordingForm.getPrmsClaimDetailList().set(saveStatus, prmsClaimDetail);
            }

        }
        addOrModifyBundle = "Add";
        reCreatModel();
        clearDetail();
    }

    public void addNewClaim() {
        currencyLists = claimReordingFormBeanLocal.getCurrencyName();
        listOfContract = claimReordingFormBeanLocal.listOfContractNO();
        saveorUpdateBundle = "Save";
        disableBtnCreate = false;
        renderpnlToSearchPage = false;
        if (createOrSearchBundle.equals("New")) {
            clear();
            clearDetail();
            renderPnlCreateParty = true;
            renderPnlManPage = false;
            createOrSearchBundle = "Search";
            setIcon("ui-icon-search");
        } else if (createOrSearchBundle.equals("Search")) {
            renderPnlCreateParty = false;
            renderPnlManPage = true;
            createOrSearchBundle = "New";
            setIcon("ui-icon-plus");
        }
    }

    private void reCreatModel() {
        prmsClaimDetailsModels = null;
        prmsClaimDetailsModels = new ListDataModel(new ArrayList<>(prmsClaimRecordingForm.getPrmsClaimDetailList()));
    }

    public SelectItem[] itemInfo() {
        if (itemList == null) {
            SelectItem[] abc = new SelectItem[1];
            abc[0] = new SelectItem("", "---Select---");
            return abc;
        } else {
            return JsfUtil.getSelectItems(itemList, true);
        }

    }

    /**
     * @return the itemList
     */
    public List<MmsItemRegistration> getItemList() {
        if (itemList == null) {
            itemList = new ArrayList<>();
        }
        return itemList;
    }

    /**
     * @param itemList the itemList to set
     */
    public void setItemList(List<MmsItemRegistration> itemList) {
        this.itemList = itemList;
    }

    /**
     * @return the itemRegistration
     */
    public MmsItemRegistration getItemRegistration() {
        if (itemRegistration == null) {
            itemRegistration = new MmsItemRegistration();
        }
        return itemRegistration;
    }

    /**
     * @param itemRegistration the itemRegistration to set
     */
    public void setItemRegistration(MmsItemRegistration itemRegistration) {
        this.itemRegistration = itemRegistration;
    }

    /**
     * @return the wfPrmsProcessed
     */
    public WfPrmsProcessed getWfPrmsProcessed() {
        return wfPrmsProcessed;
    }

    /**
     * @param wfPrmsProcessed the wfPrmsProcessed to set
     */
    public void setWfPrmsProcessed(WfPrmsProcessed wfPrmsProcessed) {
        this.wfPrmsProcessed = wfPrmsProcessed;
    }

    /**
     * @return the wfPrmsProcessedDModel
     */
    public DataModel<WfPrmsProcessed> getWfPrmsProcessedDModel() {
        if (wfPrmsProcessedDModel == null) {
            wfPrmsProcessedDModel = new ArrayDataModel<>();
        }
        return wfPrmsProcessedDModel;
    }

    /**
     * @param wfPrmsProcessedDModel the wfPrmsProcessedDModel to set
     */
    public void setWfPrmsProcessedDModel(DataModel<WfPrmsProcessed> wfPrmsProcessedDModel) {
        this.wfPrmsProcessedDModel = wfPrmsProcessedDModel;
    }

    public void handleselectOptions(ValueChangeEvent event) {
        if (null != event.getNewValue()) {
            setSelectedValue(event.getNewValue().toString());

        }
    }

    public WorkFlow getWorkFlow() {
        if (workFlow == null) {
            workFlow = new WorkFlow();
        }
        return workFlow;
    }

    /**
     * @param workFlow the workFlow to set
     */
    public void setWorkFlow(WorkFlow workFlow) {
        this.workFlow = workFlow;
    }

    /**
     * @return the loggerName
     */
    public String getLoggerName() {
        return loggerName;
    }

    /**
     * @param loggerName the loggerName to set
     */
    public void setLoggerName(String loggerName) {
        this.loggerName = loggerName;
    }

    /**
     * @return the requestNotificationCounter
     */
    public int getRequestNotificationCounter() {
        prmscrfList = claimReordingFormBeanLocal.getClaimLists();
        requestNotificationCounter = prmscrfList.size();
        return requestNotificationCounter;
    }

    /**
     * @param requestNotificationCounter the requestNotificationCounter to set
     */
    public void setRequestNotificationCounter(int requestNotificationCounter) {
        this.requestNotificationCounter = requestNotificationCounter;
    }

    public void RequestListChange(ValueChangeEvent eve) {
        prmsClaimRecordingForm = (PrmsClaimRecordingForm) eve.getNewValue();
        populateDataForApp();

    }

    public void populateDataForApp() {
        prmsClaimRecordingForm.setClaimId(prmsClaimRecordingForm.getClaimId());
        currencyLists = claimReordingFormBeanLocal.getCurrencyName();
        fmsLuCurrency = prmsClaimRecordingForm.getCurrencyId();
        prmsContract = prmsClaimRecordingForm.getContractId();
        prmsLcRigistration = prmsClaimRecordingForm.getLcId();
        departments = prmsClaimRecordingForm.getDepId();
        prmsClaimRecordingForm.setProcessedOn(wfPrmsProcessed.getProcessedOn());
        renderPnlManPage = false;
        renderPnlCreateParty = true;
        saveorUpdateBundle = "Update";
        recreatClaimRecordingFormModel();
        reCreatModel();

    }

//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Handler">
    /**
     * @return the isRendercreate
     */
    public boolean isIsRendercreate() {
        return isRendercreate;
    }

    /**
     * @param isRendercreate the isRendercreate to set
     */
    public void setIsRendercreate(boolean isRendercreate) {
        this.isRendercreate = isRendercreate;
    }

    public boolean isIsRenderNotify() {
        return isRenderNotify;
    }

    public void setIsRenderNotify(boolean isRenderNotify) {
        this.isRenderNotify = isRenderNotify;
    }

    /**
     * @return the prmscrfList
     */
    public List<PrmsClaimRecordingForm> getPrmscrfList() {
        if (prmscrfList == null) {
            prmscrfList = new ArrayList<>();
        }
        return prmscrfList;
    }

    /**
     * @param prmscrfList the prmscrfList to set
     */
    public void setPrmscrfList(List<PrmsClaimRecordingForm> prmscrfList) {
        this.prmscrfList = prmscrfList;
    }

    public List<PrmsContractAmendment> getPrmsContractAmendmentLst() {
        if (prmsContractAmendmentLst == null) {
            prmsContractAmendmentLst = new ArrayList<>();
        }
        return prmsContractAmendmentLst;
    }

    public void setPrmsContractAmendmentLst(List<PrmsContractAmendment> prmsContractAmendmentLst) {
        this.prmsContractAmendmentLst = prmsContractAmendmentLst;
    }

    public PrmsContractAmendment getPrmsContractAmendment() {
        if (prmsContractAmendment == null) {
            prmsContractAmendment = new PrmsContractAmendment();
        }
        return prmsContractAmendment;
    }

    public void setPrmsContractAmendment(PrmsContractAmendment prmsContractAmendment) {
        this.prmsContractAmendment = prmsContractAmendment;
    }

    public void contractAmount() {
        System.out.println("1");
        String contractAmount = "";
        for (int i = 0; i < prmsContract.getPrmsContractCurrencyDetailList().size(); i++) {
            if (prmsContract.getPrmsContractCurrencyDetailList().size() > 0) {
                contractAmount = contractAmount + prmsContract.getPrmsContractCurrencyDetailList().get(i).getAmount() + " " + prmsContract.getPrmsContractCurrencyDetailList().get(i).getCurrencyId().getCurrency() + ",";
            }
        }
        prmsClaimRecordingForm.setContractAmount(contractAmount);

    }

    /**
     * @return the addOrModifyBundle
     */
    public String getAddOrModifyBundle() {
        return addOrModifyBundle;
    }

    /**
     * @param addOrModifyBundle the addOrModifyBundle to set
     */
    public void setAddOrModifyBundle(String addOrModifyBundle) {
        this.addOrModifyBundle = addOrModifyBundle;
    }

}
//</editor-fold>

