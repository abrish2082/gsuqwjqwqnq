package et.gov.eep.mms.controller;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import securityBean.Constants;
import securityBean.SessionBean;
import securityBean.WorkFlow;
import webService.AAA;
import webService.EventEntry;
import webService.IAdministration;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.context.RequestContext;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.TreeNode;
import et.gov.eep.commonApplications.businessLogic.WfMmsProcessedBeanLocal;
import et.gov.eep.commonApplications.entity.WfMmsProcessed;
import et.gov.eep.commonApplications.entity.ColumnNameResolver;
import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.hrms.businessLogic.recruitment.HrRecruitmentRequestsBeanLocal;
import et.gov.eep.hrms.entity.organization.HrDepartments;
import et.gov.eep.hrms.mapper.organization.HrDepartmentsFacade;
import et.gov.eep.mms.businessLogic.MmsBinCardBeanLocal;
import et.gov.eep.mms.businessLogic.MmsGRNBEanLocal;
import et.gov.eep.mms.businessLogic.MmsGrnDetailBeanLocal;
import et.gov.eep.mms.businessLogic.MmsInspectionBeanLocal;
import et.gov.eep.mms.businessLogic.MmsInspectionDetailBeanLocal;
import et.gov.eep.mms.businessLogic.MmsItemRegisrtationBeanLocal;
import et.gov.eep.mms.businessLogic.MmsLuDeliveryOptionBeanLocal;
import et.gov.eep.mms.businessLogic.MmsStoreInformationBeanLocal;
import et.gov.eep.mms.entity.MmsBinCard;
import et.gov.eep.mms.entity.MmsGrn;
import et.gov.eep.mms.entity.MmsGrnDetail;
import et.gov.eep.mms.entity.MmsInspection;
import et.gov.eep.mms.entity.MmsInspectionDetail;
import et.gov.eep.mms.entity.MmsItemRegistration;
import et.gov.eep.mms.entity.MmsStoreInformation;
import et.gov.eep.mms.entity.mms_lu_delivery_option;

/**
 *
 * @author minab
 */
@Named(value = "gRNController")
@ViewScoped
public class GRNController implements Serializable {

    // <editor-fold defaultstate="collapsed" desc=" EJB's ">
    @EJB
    private MmsInspectionDetailBeanLocal inpsDetailBeanLocal;

    @EJB
    private MmsGRNBEanLocal grnbeanlocal;
    @EJB
    MmsBinCardBeanLocal mmsBinCardBeanLocal;
    @EJB
    MmsGrnDetailBeanLocal grndetailbeanlocal;
    @EJB
    MmsStoreInformationBeanLocal storeInfoInterface;
    @EJB
    MmsInspectionBeanLocal inspectionbeanLocal;
    @EJB
    private MmsLuDeliveryOptionBeanLocal deliveryoptionInterface;
    @EJB
    WfMmsProcessedBeanLocal wfMmsProcessedBeanLocal;
    @EJB
    private MmsItemRegisrtationBeanLocal itemRegInterface;
    @EJB
    HrRecruitmentRequestsBeanLocal hrRecruitmentRequestsBeanLocal;
    @EJB
    HrDepartmentsFacade hrDepartmentsFacade;

// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc=" Entity's ">
    @Inject
    private MmsItemRegistration itemRegEntity;
    @Inject
    private MmsInspection inspection;

    @Inject
    MmsGrn mmsgrn;

    @Inject
    MmsInspectionDetail inspectionDetail;

    @Inject
    MmsGrnDetail grndetail;

    @Inject
    private MmsStoreInformation storeInfoEntity;
    @Inject
    private mms_lu_delivery_option deliveryOptions;
    @Inject
    WfMmsProcessed wfMmsProcessed;
    @Inject
    SessionBean SessionBean;
    @Inject
    WorkFlow workflow;
    @Inject
    private MmsBinCard mmsBinCard;
    @Inject
    HrDepartments hrDepartments;
    @Inject
    ColumnNameResolver columnNameResolver;
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc=" Fields ">
    DataModel<MmsInspectionDetail> inspectionDetailDataModel;
    DataModel<MmsGrnDetail> grnDetailDataModel;
    private DataModel<MmsGrn> mmsGRNSearchInfoDataModel;

    String btnSaveUpdate = "Create";
    int updateStatus = 0;
    String panelInvisible = "false";
    private String saveorUpdateBundle = "Save";
    private boolean renderDecision = false;
    private boolean isRenderedIconWorkflow = false;
    private boolean disableBtnCreate;
    private boolean disableButtonUpdate = false;
    private String createOrSearchBundle = "New";
    private boolean renderPnlCreateGRN = false;
    private boolean renderPnlManPage = true;
    private boolean renderDeliveryOption = false;
    private boolean isRenderedIconNitification = true;
    private boolean isRenderAddButton = true;
    private boolean renderpnlToSearchPage;
    private boolean isStockSelected;
    private String icone = "ui-icon-plus";
    private String activeIndex;
    String newPayNo;
    String lastPaymentNo = "0";
    private MmsGrn selectedItem;
    private List<MmsStoreInformation> storeList;
    private List<MmsGrn> allGrnInfoList;
    private List<MmsGrn> mmsGrnsList;
    private List<MmsInspection> inspectionList;
    private List<mms_lu_delivery_option> deliveryOption;
    private List<MmsItemRegistration> materialNameList = new ArrayList<>();
    List<HrDepartments> allDepartmentsList = new ArrayList<>();
    private static List<HrDepartments> deptArrayLists;
    List<WfMmsProcessed> WfList;
    private final int fromSearch = 0;
    private int fromSearchOrFromRequest;// to determine wheather it the request is from workflow or from direct search so 0 is from search and 1 is from workflow request
    private final int fromRequest = 1;
    private DataModel<WfMmsProcessed> WorkflowDataModel;
    private String userName;
    private String selectedValue;
    private TreeNode root;
    private TreeNode root2;
    private TreeNode selectedNode;
    private boolean saved = false;// to check saved status
    ArrayList<MmsBinCard> bincardList = new ArrayList<>();
    private List<MmsGrn> mmsGrnColumnNameList;
    private List<String> mmsgrnColumnNameList;
    ColumnNames columnNames = new ColumnNames();
    List<ColumnNames> ColumnNamesList1 = new ArrayList<>();
    List<ColumnNameResolver> ColumnNamesList = new ArrayList<>();
// </editor-fold>

    ///<editor-fold defaultstate="collapsed" desc="PostConstraction">
    public class ColumnNames implements Serializable {

        String Table_Col_Name;
        String Parsed_Col_name;

        //<editor-fold defaultstate="collapsed" desc="getter and setter">
        public String getTable_Col_Name() {
            return Table_Col_Name;
        }

        public void setTable_Col_Name(String Table_Col_Name) {
            this.Table_Col_Name = Table_Col_Name;
        }

        public String getParsed_Col_name() {
            return Parsed_Col_name;
        }

        public void setParsed_Col_name(String Parsed_Col_name) {
            this.Parsed_Col_name = Parsed_Col_name;
        }
//</editor-fold>

    }

    @PostConstruct
    public void init() {
        allDepartmentsList = hrDepartmentsFacade.findAll();
        root = new DefaultTreeNode("Root", null);
        recursive(allDepartmentsList, 0, root);
        root2 = getRoot();
        if (workflow.isApproveStatus() || workflow.isCheckStatus()) {
            System.out.println("app or check");

            mmsGrnsList = grnbeanlocal.findGRNListByWfStatus(Constants.PREPARE_VALUE);

            renderDecision = true;
            isRenderedIconNitification = true;
        } else if (workflow.isPrepareStatus()) {
            System.out.println("prep");
            mmsGrnsList = grnbeanlocal.findGRNListByWfStatus(Constants.APPROVE_REJECT_VALUE);
            renderDecision = false;
            if (mmsGrnsList != null) {
                isRenderedIconNitification = mmsGrnsList.size() > 0;
            }

        }
        getMmsgrnColumnNameList();
        wfMmsProcessed.setProcessedBy(SessionBean.getUserId());
        setUserName(SessionBean.getUserName());

        System.out.println("is App==" + workflow.isApproveStatus() + "is chech==" + workflow.isCheckStatus() + "is prepa==" + workflow.isPrepareStatus());
    }

    public void recursive(List<HrDepartments> liste, int id, TreeNode node) {
        deptArrayLists = new ArrayList<>();
        if (allDepartmentsList != null) {
            for (HrDepartments k : allDepartmentsList) {
                if (k.getParentId() != null && k.getParentId() == id) {
                    TreeNode childNode = new DefaultTreeNode(k.getDepId() + "--" + k.getDepName(), node);
                    deptArrayLists.add(k);
                    recursive(deptArrayLists, k.getDepId(), childNode);
                }
            }
        }
    }
    ///</editor-fold >

    // <editor-fold defaultstate="collapsed" desc=" Default Constructor ">
    public GRNController() {
        /* This a default constructer created by the IDE*/
    }

   

// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc=" Getters And Setters ">
    /**
     *
     * @return
     */
   

    public MmsInspectionDetail getInspectionDetail() {
        if (inspectionDetail == null) {
            inspectionDetail = new MmsInspectionDetail();
        }
        return inspectionDetail;
    }

    public MmsItemRegistration getItemRegEntity() {
        if (itemRegEntity == null) {
            itemRegEntity = new MmsItemRegistration();
        }
        return itemRegEntity;
    }

    public List<MmsItemRegistration> getMaterialNameList() {
        return materialNameList;
    }

    public void setMaterialNameList(List<MmsItemRegistration> materialNameList) {
        this.materialNameList = materialNameList;
    }

    public ColumnNameResolver getColumnNameResolver() {
        return columnNameResolver;
    }

    public void setColumnNameResolver(ColumnNameResolver columnNameResolver) {
        this.columnNameResolver = columnNameResolver;
    }

    public List<ColumnNameResolver> getColumnNamesList() {
        return ColumnNamesList;
    }

    public void setColumnNamesList(List<ColumnNameResolver> ColumnNamesList) {
        this.ColumnNamesList = ColumnNamesList;
    }

    public void setItemRegEntity(MmsItemRegistration itemRegEntity) {
        this.itemRegEntity = itemRegEntity;
    }

    public String getSelectedValue() {
        return selectedValue;
    }

    public void setSelectedValue(String selectedValue) {
        this.selectedValue = selectedValue;
    }

    public int getFromSearchOrFromRequest() {
        return fromSearchOrFromRequest;
    }

    public void setFromSearchOrFromRequest(int fromSearchOrFromRequest) {
        this.fromSearchOrFromRequest = fromSearchOrFromRequest;
    }

    public boolean isIsRenderedIconWorkflow() {
        return isRenderedIconWorkflow;
    }

    public void setIsRenderedIconWorkflow(boolean isRenderedIconWorkflow) {
        this.isRenderedIconWorkflow = isRenderedIconWorkflow;
    }

    public List<MmsGrn> getMmsGrnsList() {
        return mmsGrnsList;
    }

    public void setMmsGrnsList(List<MmsGrn> mmsGrnsList) {
        this.mmsGrnsList = mmsGrnsList;
    }

    /**
     *
     * @param inspectionDetail
     */
    public void setInspectionDetail(MmsInspectionDetail inspectionDetail) {
        this.inspectionDetail = inspectionDetail;
    }

    public boolean isRenderpnlToSearchPage() {
        return renderpnlToSearchPage;
    }

    public void setRenderpnlToSearchPage(boolean renderpnlToSearchPage) {
        this.renderpnlToSearchPage = renderpnlToSearchPage;
    }

    public boolean isIsRenderedIconNitification() {
        return isRenderedIconNitification;
    }

    public void setIsRenderedIconNitification(boolean isRenderedIconNitification) {
        this.isRenderedIconNitification = isRenderedIconNitification;
    }

    public boolean isIsStockSelected() {
        return isStockSelected;
    }

    public void setIsStockSelected(boolean isStockSelected) {
        this.isStockSelected = isStockSelected;
    }

    public List<MmsGrn> getAllGrnInfoList() {
        if (allGrnInfoList == null) {
            allGrnInfoList = new ArrayList<>();
        }
        return allGrnInfoList;
    }

    public void setAllGrnInfoList(List<MmsGrn> allGrnInfoList) {
        this.allGrnInfoList = allGrnInfoList;
    }

    public boolean isDisableButtonUpdate() {
        return disableButtonUpdate;
    }

    public void setDisableButtonUpdate(boolean disableButtonUpdate) {
        this.disableButtonUpdate = disableButtonUpdate;
    }

    public mms_lu_delivery_option getDeliveryOptions() {
        if (deliveryOptions == null) {
            deliveryOptions = new mms_lu_delivery_option();
        }
        return deliveryOptions;
    }

    public void setDeliveryOptions(mms_lu_delivery_option deliveryOptions) {
        this.deliveryOptions = deliveryOptions;
    }

    public MmsStoreInformation getStoreInfoEntity() {
        if (storeInfoEntity == null) {
            storeInfoEntity = new MmsStoreInformation();
        }
        return storeInfoEntity;
    }

    public void setStoreInfoEntity(MmsStoreInformation storeInfoEntity) {
        this.storeInfoEntity = storeInfoEntity;
    }

    /**
     *
     * @return
     */
    public DataModel<MmsInspectionDetail> getInspectionDetailDataModel() {
        if (inspectionDetailDataModel == null) {
            inspectionDetailDataModel = new ListDataModel<>();
        }
        return inspectionDetailDataModel;
    }

    /**
     *
     * @param inspectionDetailDataModel
     */
    public void setInspectionDetailDataModel(DataModel<MmsInspectionDetail> inspectionDetailDataModel) {
        this.inspectionDetailDataModel = inspectionDetailDataModel;
    }

    public DataModel<MmsGrnDetail> getGrnDetailDataModel() {
        if (grnDetailDataModel == null) {
            grnDetailDataModel = new ListDataModel<>();
        }
        return grnDetailDataModel;
    }

    public void setGrnDetailDataModel(DataModel<MmsGrnDetail> grnDetailDataModel) {
        this.grnDetailDataModel = grnDetailDataModel;
    }

    /**
     *
     * @return
     */
    public MmsInspection getInspection() {
        if (inspection == null) {
            inspection = new MmsInspection();
        }
        return inspection;
    }

    /**
     *
     * @return
     */
    public DataModel<MmsInspectionDetail> getInspectionDetailsModel() {
        if (inspectionDetailDataModel == null) {
            inspectionDetailDataModel = new ListDataModel<>();
        }
        return inspectionDetailDataModel;
    }

    /**
     *
     * @param inspection
     */
    public void setInspection(MmsInspection inspection) {
        this.inspection = inspection;
    }

    /**
     *
     * @return
     */
    public String getBtn_saveUpdate() {
        return btnSaveUpdate;
    }

    /**
     *
     * @param btnSaveUpdate
     */
    public void setBtn_saveUpdate(String btnSaveUpdate) {
        this.btnSaveUpdate = btnSaveUpdate;
    }

    /**
     *
     * @return
     */
    public MmsGrn getMmsgrn() {
        if (mmsgrn == null) {
            mmsgrn = new MmsGrn();
        }
        return mmsgrn;
    }

    /**
     *
     * @param mmsgrn
     */
    public void setMmsgrn(MmsGrn mmsgrn) {
        this.mmsgrn = mmsgrn;
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

    /**
     *
     * @return
     */
    public String getPanelInvisible() {
        return panelInvisible;
    }

    /**
     *
     * @param panelInvisible
     */
    public void setPanelInvisible(String panelInvisible) {
        this.panelInvisible = panelInvisible;
    }

    public String getSaveorUpdateBundle() {
        return saveorUpdateBundle;
    }

    public void setSaveorUpdateBundle(String saveorUpdateBundle) {
        this.saveorUpdateBundle = saveorUpdateBundle;
    }

    public boolean isDisableBtnCreate() {
        return disableBtnCreate;
    }

    public void setDisableBtnCreate(boolean disableBtnCreate) {
        this.disableBtnCreate = disableBtnCreate;
    }

    public String getCreateOrSearchBundle() {
        return createOrSearchBundle;
    }

    public void setCreateOrSearchBundle(String createOrSearchBundle) {
        this.createOrSearchBundle = createOrSearchBundle;
    }

    public boolean isRenderPnlCreateGRN() {
        return renderPnlCreateGRN;
    }

    public void setRenderPnlCreateGRN(boolean renderPnlCreateGRN) {
        this.renderPnlCreateGRN = renderPnlCreateGRN;
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

    public String getActiveIndex() {
        return activeIndex;
    }

    public void setActiveIndex(String activeIndex) {
        this.activeIndex = activeIndex;
    }

    public DataModel<MmsGrn> getMmsGRNSearchInfoDataModel() {
        if (mmsGRNSearchInfoDataModel == null) {
            mmsGRNSearchInfoDataModel = new ListDataModel<>();
        }
        return mmsGRNSearchInfoDataModel;
    }

    public void setMmsGRNSearchInfoDataModel(DataModel<MmsGrn> mmsGRNSearchInfoDataModel) {
        this.mmsGRNSearchInfoDataModel = mmsGRNSearchInfoDataModel;
    }

    public MmsGrn getSelectedItem() {
        return selectedItem;
    }

    public void setSelectedItem(MmsGrn selectedItem) {
        this.selectedItem = selectedItem;
    }

    public List<MmsInspection> getInspectionList() {
        if (inspectionList == null) {
            inspectionList = new ArrayList<>();
        }
//        inspectionList = inspectionbeanLocal.findAllinsNo();
        return inspectionList;
    }

    public void setInspectionList(List<MmsInspection> inspectionList) {
        this.inspectionList = inspectionList;
    }

    /**
     *
     * @return
     */
    public MmsGrnDetail getMmsGrnDetail() {
        if (grndetail == null) {
            grndetail = new MmsGrnDetail();
        }
        return grndetail;
    }

    /**
     *
     * @param grndet
     */
    public void setGrnDetail(MmsGrnDetail grndet) {
        this.grndetail = grndet;
    }

    /**
     *
     * @return
     */
    public int getUpdateStatus() {
        return updateStatus;
    }

    /**
     *
     * @param updateStatus
     */
    public void setUpdateStatus(int updateStatus) {
        this.updateStatus = updateStatus;
    }

    public boolean isRenderDeliveryOption() {
        return renderDeliveryOption;
    }

    public void setRenderDeliveryOption(boolean renderDeliveryOption) {
        this.renderDeliveryOption = renderDeliveryOption;
    }

    public WfMmsProcessed getWfMmsProcessed() {
        if (wfMmsProcessed == null) {
            wfMmsProcessed = new WfMmsProcessed();
        }
        return wfMmsProcessed;
    }

    public void setWfMmsProcessed(WfMmsProcessed wfMmsProcessed) {
        this.wfMmsProcessed = wfMmsProcessed;
    }

    public List<WfMmsProcessed> getWfList() {
        return WfList;
    }

    public void setWfList(List<WfMmsProcessed> WfList) {
        this.WfList = WfList;
    }

    public DataModel<WfMmsProcessed> getWorkflowDataModel() {
        if (WorkflowDataModel == null) {
            WorkflowDataModel = new ListDataModel<>();
        }
        return WorkflowDataModel;
    }

    public void setWorkflowDataModel(DataModel<WfMmsProcessed> WorkflowDataModel) {
        this.WorkflowDataModel = WorkflowDataModel;
    }

    public boolean isRenderDecision() {
        return renderDecision;
    }

    public void setRenderDecision(boolean renderDecision) {
        this.renderDecision = renderDecision;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public List<MmsGrn> getMmsGrnColumnNameList() {
        if (mmsGrnColumnNameList == null) {
            mmsGrnColumnNameList = new ArrayList<>();
//            mmsGrnColumnNameList = grnbeanlocal.getMmsGrnColumnNameList();

        }
        return mmsGrnColumnNameList;
    }

    public void setMmsGrnColumnNameList(List<MmsGrn> mmsGrnColumnNameList) {
        this.mmsGrnColumnNameList = mmsGrnColumnNameList;
    }

// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc=" Save And Search Related ">
    /*This method is used to On Select GRN Request
     */
    public void onSelectGRnRequest(ValueChangeEvent event) {
        try {
            if (null != event.getNewValue()) {
                mmsgrn = (MmsGrn) event.getNewValue();
                setFromSearchOrFromRequest(fromRequest);
                populateUI();
            }
        } catch (Exception e) {
            //e.printStackTrace();
            JsfUtil.addFatalMessage("Faile to process! Try Again Reloading the Page");
        }
    }
    /*This method is used to On Node Select
     */

    public void onNodeSelect(NodeSelectEvent event) {
        selectedNode = event.getTreeNode();
        int key = Integer.parseInt((selectedNode.getData().toString()).split("--")[0]);
        System.out.println(selectedNode.getData().toString());
        hrDepartments = hrRecruitmentRequestsBeanLocal.getSelectDepartement(key);
        System.out.println("===name====" + hrDepartments);
        mmsgrn.setDepId(hrDepartments);
        System.out.println("=======SettedDepId==========" + mmsgrn.getDepId());
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("PF('dlg').hide();");
    }
    /*This method is used to edit Data Table
     */

    public void editDataTable() {
        grndetail = grnDetailDataModel.getRowData();
        if (mmsgrn.getEnteryType() == "Stock") {
            isStockSelected = true;

        } else {
            isStockSelected = false;
        }
        itemRegEntity = grndetail.getItemId();

    }
    /*This method is used to column Name Change Event
     */

    public void columnNameChangeEvent(ValueChangeEvent event) {      
         if (event.getNewValue() != null) {
            System.out.println("event.getNewValue().toString()=="+event.getNewValue().toString());
            columnNameResolver.setCol_Name_FromTable(event.getNewValue().toString());
            columnNameResolver.setParsed_Col_Name(ColumnParser(columnNameResolver.getCol_Name_FromTable()));
            mmsgrn.setColumnName(columnNameResolver.getCol_Name_FromTable());
            mmsgrn.setColumnValue(null);
        }
    }

    public String ColumnParser(String col_name) {
        String col = col_name.replace("_", " ");
        System.out.println("col==" + col);
        return col;
    }

    public List<String> getMmsgrnColumnNameList() {
        mmsgrnColumnNameList = grnbeanlocal.getMmsGrnColumnNameList();
        if (mmsgrnColumnNameList == null) {
            mmsgrnColumnNameList = new ArrayList<>();
        }
        System.out.println("=======mmsRmgColumnNameList==" + mmsgrnColumnNameList);
        if (mmsgrnColumnNameList.size() > 0) {
            ColumnNamesList = new ArrayList<>();
            for (int i = 0; i < mmsgrnColumnNameList.size(); i++) {               
                ColumnNameResolver obj = new ColumnNameResolver();
                obj.setCol_Name_FromTable((mmsgrnColumnNameList.get(i).toLowerCase()));
                obj.setParsed_Col_Name(ColumnParser((mmsgrnColumnNameList.get(i).toLowerCase())));
                ColumnNamesList.add(obj);
            }

        }
        return mmsgrnColumnNameList;
    }

    public void setMmsgrnColumnNameList(List<String> mmsgrnColumnNameList) {
        this.mmsgrnColumnNameList = mmsgrnColumnNameList;
    }

    /*This method is used to Save GRN
     */
    public void saveGrn() {
        try {
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();

            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");

            if (security.checkAccess(SessionBean.getUserName(), "saveGrn", dataset)) {
                if (updateStatus == 0) {

                    try {
                        generateGrnNo();
                        mmsgrn.setGrnNo(newPayNo);
                        mmsgrn.setDepId(hrDepartments);
                        setWorkFlowValues();
                        mmsgrn.addGRNIdToWorkflow(wfMmsProcessed);

                        grnbeanlocal.create(mmsgrn);
                        int binsize = bincardList.size();
                        for (int i = 0; i < binsize; i++) {
                            mmsBinCardBeanLocal.edit(bincardList.get(i));
                        }
                        JsfUtil.addSuccessMessage("GRN Data Saved" + mmsgrn.getGrnNo());
                        clearPage();
                        System.out.println("========after Clearing page====");
                    } catch (Exception e) {
                        JsfUtil.addFatalMessage("Something Error Occured on Data Saved");
                        clearPage();
                    }
                } else if (updateStatus == 1 && workflow.isPrepareStatus()) {
                    try {
                        mmsgrn.setProcessedBy(wfMmsProcessed.getProcessedBy());
                        mmsgrn.setProcessedOn(wfMmsProcessed.getProcessedOn());
                        mmsgrn.setStatus(Constants.PREPARE_VALUE);
                        wfMmsProcessed.setDecision(Constants.PREPARE_VALUE);
                        grnbeanlocal.edit(mmsgrn);
                        JsfUtil.addSuccessMessage("GRN data is Modified");
                        saveorUpdateBundle = "Save";
                        clearPage();
                    } catch (Exception e) {
                        JsfUtil.addErrorMessage("Something Error Occured on Data Modified");
                        clearPage();

                    }
                } else if (updateStatus == 1 && (workflow.isApproveStatus())) {
                    if (selectedValue.equalsIgnoreCase("Approve") && workflow.isApproveStatus()) {
                        try {
                            mmsgrn.setProcessedBy(wfMmsProcessed.getProcessedBy());
                            mmsgrn.setProcessedOn(wfMmsProcessed.getProcessedOn());
                            mmsgrn.setStatus(Constants.PREPARE_VALUE);
                            wfMmsProcessed.setDecision(Constants.PREPARE_VALUE);

                            if (saved) {
                                boolean status = updateBinCardGRN(mmsgrn.getGrnNo());
                                clearPage();
                            } else {
                                JsfUtil.addFatalMessage("Procedure execution failed");
                            }
                        } catch (Exception e) {
                            JsfUtil.addFatalMessage("Exception occured on Procedure Execution");
                        }

                    } else if (selectedValue.equalsIgnoreCase("Reject") && workflow.isApproveStatus()) {
                        workflow.setUserStatus(Constants.APPROVE_REJECT_VALUE);
                        mmsgrn.setStatus(Constants.APPROVE_REJECT_VALUE);
                        wfMmsProcessed.setDecision(Constants.APPROVE_REJECT_VALUE);
                        saveWorkFlowAndClear();
                        clearPage();
                    }

                }
            } else {
                JsfUtil.addFatalMessage("You don't have appropriate permission, please contact the system administrator. ");
                EventEntry eventEntry = new EventEntry();
                eventEntry.setSessionId(SessionBean.getSessionID());
                eventEntry.setUserId(SessionBean.getUserId());
                QName qualifiedName = new QName("", "project");
                JAXBElement<String> test = new JAXBElement<String>(qualifiedName, String.class, null, SessionBean.getUserName());
                eventEntry.setUserLogin(test);

                security.addEventLog(eventEntry, dataset);
            }
        } catch (Exception ex) {

        }
    }

    /*This method is used to save Work Flow And Clear
     */
    public void saveWorkFlowAndClear() {
        mmsgrn.addGRNIdToWorkflow(wfMmsProcessed);
        wfMmsProcessedBeanLocal.create(wfMmsProcessed);
        grnbeanlocal.edit(mmsgrn);
        mmsGrnsList.remove(mmsgrn);
        saved = true;

        JsfUtil.addSuccessMessage("GRN Data Modified");
    }
    /*This method is used to update Bin Card GRN
     */

    public boolean updateBinCardGRN(String grnNo) {
        try {

            int status;

            status = mmsBinCardBeanLocal.incrementBinCardQuantityForGrn(grnNo);

            return status == 0;
        } catch (Exception ex) {

            return false;
        }

    }

    /*This method is used to clearPage
     */
    public String clearPage() {
        mmsgrn = null;
        updateStatus = 0;
        grndetail = null;
        inspection = null;
        storeInfoEntity = null;
        inspectionDetailDataModel = null;
        inspectionDetail = null;
        wfMmsProcessed = null;
        mmsGRNSearchInfoDataModel = null;
        grnDetailDataModel = null;
        setSelectedValue(null);
        hrDepartments = null;
        return null;
    }
    /*This method is used to set WorkFlow Values
     */

    public void setWorkFlowValues() {
        wfMmsProcessed.setProcessedBy(SessionBean.getUserId());
        mmsgrn.setProcessedBy(SessionBean.getUserId());
        mmsgrn.setProcessedOn(wfMmsProcessed.getProcessedOn());
        mmsgrn.setStatus(Constants.PREPARE_VALUE);
    }
    /*This method is used to recreate Grn DataModel
     */

    private void recreateGrnDataModel() {

        inspectionDetailDataModel = null;
        inspectionDetailDataModel = new ListDataModel(inspection.getMmsInspectionDetailList());

    }
    /*This method is used to recreate Grn DataModel New
     */

    private void recreateGrnDataModelNew() {
        grnDetailDataModel = null;
        grnDetailDataModel = new ListDataModel(mmsgrn.getMmsGrnDetailCollection());
    }
    /*This method is used to clear Popup
     */

    private void clearPopup() {
        grndetail = null;
    }
    /*This method is used to add GRN Info Detail
     */

    public String addGRnInfoDetail() {
        mmsgrn.addGRnDetialInfo(grndetail);
        recreateGrnDataModel();
        clearPopup();
        return null;
    }
    /*This method is used to search By GrnNO
     */

    public List<MmsGrn> searchByGrnNO(String grnNo) {
        ArrayList<MmsGrn> grnNumber = null;
        mmsgrn.setGrnNo(grnNo);
        grnNumber = grnbeanlocal.searchByGrn(mmsgrn);
        btnSaveUpdate = "Update";
        return grnNumber;
    }
    /*This method is used to get By Grn Numbers
     */

    public void getByGrnNumbers(SelectEvent event) {

        mmsgrn = (MmsGrn) event.getObject();

        for (int i = 0; i < mmsgrn.getMmsGrnDetailCollection().size(); i++) {
            inspectionDetail = new MmsInspectionDetail();

            inspectionDetail.setQuantity(mmsgrn.getMmsGrnDetailCollection().get(i).getReceivedQuantity());

            inspectionDetail.setUnitPrice(mmsgrn.getMmsGrnDetailCollection().get(i).getUnitPrice().doubleValue());
            inspectionDetail.setItemId(mmsgrn.getMmsGrnDetailCollection().get(i).getItemId());

            inspection.setPurchaseONo(mmsgrn.getPrNo());
            inspection.setSupplier(mmsgrn.getSupplierName());
            inspection.setStoreName(mmsgrn.getStoreId().getStoreName());
            inspection.setInspectionDate(mmsgrn.getRegDate());

            inspection.addInspectionDetialInfo(inspectionDetail);
        }
        recreateGrnDataModel();

        updateStatus = 1;

        btnSaveUpdate = "Update";
    }
    /*This method is used to get Selected Insp Number Info
     */

    public void getSelectedInspNumberInfo(ValueChangeEvent event) {
        inspection = (MmsInspection) event.getNewValue();
        storeInfoEntity = inspection.getStoreId();
        int size = inspection.getMmsInspectionDetailList().size();
        for (int i = 0; i < size; i++) {

            MmsGrnDetail grndetailObj = new MmsGrnDetail();

            grndetailObj.setReceivedQuantity(inspection.getMmsInspectionDetailList().get(i).getQuantity());
            grndetailObj.setRemark(inspection.getMmsInspectionDetailList().get(i).getRemark());
            grndetailObj.setItemId(inspection.getMmsInspectionDetailList().get(i).getItemId());
            grndetailObj.setUnitPrice(BigDecimal.valueOf(inspection.getMmsInspectionDetailList().get(i).getUnitPrice()));
            mmsgrn.setInspectionId(inspection);
            mmsgrn.setStoreId(storeInfoEntity);
            mmsgrn.setInspectionResult(inspection.getInspectionId().toString());
            mmsgrn.setSupplierName(inspection.getSupplier());
            mmsgrn.setPrNo(inspection.getPurchaseONo());

            mmsgrn.addGRnDetialInfo(grndetailObj);

        }

        recreateGrnDataModelNew();

    }
    /*This method is used to generate GrnNo
     */

    public String generateGrnNo() {
        if (updateStatus == 1) {
            newPayNo = mmsgrn.getGrnNo();
        } else {
            MmsGrn lastPaymentNoObj = grnbeanlocal.getLastinspectionNo();
            if (lastPaymentNoObj != null) {
                lastPaymentNo = lastPaymentNoObj.getGrnId().toString();
            }
            DateFormat f = new SimpleDateFormat("yyyy");
            Date now = new Date();
            int newPayment = 0;
            if ("0".equals(lastPaymentNo)) {
                newPayment = 1;
                newPayNo = "GRN-" + newPayment + "/" + f.format(now);
            } else {
                String[] lastInspNos = lastPaymentNo.split("-");
                String lastDatesPatern = lastInspNos[0];
                String[] lastDatesPaterns = lastDatesPatern.split("/");
                newPayment = Integer.parseInt(lastDatesPaterns[0]);
                newPayment = newPayment + 1;
                newPayNo = "GRN-" + newPayment + "/" + f.format(now);
            }
            mmsgrn.setGrnNo(newPayNo);
        }
        return newPayNo;
    }
    /*This method is used to Go Back Search Button Action
     */

    public void goBackSearchButtonAction() {
        renderpnlToSearchPage = false;
        renderPnlCreateGRN = false;
        renderPnlManPage = true;
    }
    /*This method is used to create New GRN Info
     */

    public void createNewGRNInfo() {

        clearPage();
        renderpnlToSearchPage = false;
        inspectionList = inspectionbeanLocal.findAllByStatus(Constants.APPROVE_VALUE);
        renderPnlCreateGRN = true;
        renderPnlManPage = false;

    }
    /*This method is used to btn Search Action
     */

    public void btn_Search_Action() {
        clearPage();
        renderPnlCreateGRN = false;
        renderPnlManPage = true;
    }
    /*This method is used to handle select Options
     */

    public void handleselectOptions(ValueChangeEvent event) {
        String opt = event.getNewValue().toString();
        if ("1".equalsIgnoreCase(opt)) {
            mmsgrn.setGrnType(1);
        } else {
            mmsgrn.setGrnType(2);

        }

    }
    /*This method is used to handle select Options Delivery Type
     */

    public void handleselectOptionsDeliveryType(ValueChangeEvent event) {
        String opt = event.getNewValue().toString();
        System.out.println("===========option" + opt);
        if ("1".equalsIgnoreCase(opt)) {
            mmsgrn.setDeliveryType(1);
            setRenderDeliveryOption(false);
            deliveryOptions = null;
        } else {
            mmsgrn.setDeliveryType(2);
            setRenderDeliveryOption(true);
            System.out.println("====deliverypopt===========" + isRenderDeliveryOption());

        }

    }
    /*This method is used to search Grn Information
     */

    public void searchGrnInformation() {
        System.out.println("in search");
        mmsgrn.setProcessedBy(SessionBean.getUserId());
        System.out.println("processor " + mmsgrn.getProcessedBy());
        allGrnInfoList = grnbeanlocal.getGrnListsByParameter(columnNameResolver, mmsgrn, mmsgrn.getColumnValue());
        recreateGrnSearchDataModel();
    }
    /*This method is used to recreate Grn Search DataModel
     */

    private void recreateGrnSearchDataModel() {
        mmsGRNSearchInfoDataModel = null;
        mmsGRNSearchInfoDataModel = new ListDataModel(new ArrayList<>(allGrnInfoList));

    }
    /*This method is used to on Row Edit Temp
     */

    public void onRowEditTemp(SelectEvent event) {
        mmsgrn = (MmsGrn) event.getObject();
        storeInfoEntity = mmsgrn.getStoreId();
        renderPnlCreateGRN = true;

        renderPnlManPage = false;
        activeIndex = "0";
        saveorUpdateBundle = "Update";

        updateStatus = 1;
        wfMmsProcessed.setProcessedOn(mmsgrn.getProcessedOn());
        recreateGrnDataModelNew();
    }
    /*This method is used to on on Row Edit
     */

    public void onRowEdit(SelectEvent event) {

        mmsgrn = (MmsGrn) event.getObject();
        setFromSearchOrFromRequest(fromSearch);
        int size = mmsgrn.getWfMmsProcessedList().size();

        if (workflow.isApproveStatus() || workflow.isCheckStatus()) {

            System.out.println("=========checker approve=====" + mmsgrn.getWfMmsProcessedList().get(size - 1).getDecision());

            if (mmsgrn.getWfMmsProcessedList().get(size - 1).getDecision() == Constants.APPROVE_VALUE) {
                setSelectedValue("Approve");
            } else if (mmsgrn.getWfMmsProcessedList().get(size - 1).getDecision() == Constants.APPROVE_REJECT_VALUE) {
                setSelectedValue("Reject");
            }
            wfMmsProcessed.setProcessedOn(mmsgrn.getWfMmsProcessedList().get(size - 1).getProcessedOn());

        } else {
            if (size > 1) {
                System.out.println("========inside if ======" + workflow.isReadonly());

                workflow.setReadonly(true);
                System.out.println("=========inside if 2====" + workflow.isReadonly());
                workflow.setButtonStatus(true);
            }
        }

        populateUI();
    }
    /*This method is used to populateUI
     */

    public void populateUI() {
        inspection = mmsgrn.getInspectionId();
        inspectionList = new ArrayList<>();
        inspectionList.add(inspection);
        storeInfoEntity = mmsgrn.getStoreId();
        hrDepartments = mmsgrn.getDepId();

        renderPnlCreateGRN = true;

        renderPnlManPage = false;
        renderpnlToSearchPage = true;
        activeIndex = "0";
        saveorUpdateBundle = "Update";

        updateStatus = 1;
        if (mmsgrn.getDepId() != null) {
            hrDepartments = mmsgrn.getDepId();
        }

        recreateGrnDataModelNew();
        if (fromSearchOrFromRequest == fromSearch) {
            setUserName(mmsgrn.getProcessedBy().toString());// change this 
            wfMmsProcessed.setProcessedOn(mmsgrn.getProcessedOn());
        }
        setIsRenderedIconWorkflow(true);
        recreateWfDataModel();
    }

    /*This method is used to search Store Information
     */
    public List<MmsStoreInformation> searchStoreInformation(String storeName) {
        ArrayList<MmsStoreInformation> storeInformations = new ArrayList<>();

        storeInfoEntity.setStoreName(storeName);
        storeInformations = storeInfoInterface.searchStoreInformation(storeInfoEntity);
        return storeInformations;
    }

    /*This method is used to get Papms Store Info
     */
    public void getPapmsStoreInfo(SelectEvent event) {
        storeInfoEntity.setStoreName(event.getObject().toString());
        storeInfoEntity = storeInfoInterface.getPapmsStoreInformation(storeInfoEntity);
        mmsgrn.setStoreId(storeInfoEntity);

    }
    /*This List is used to get Store List
     */

    public List<MmsStoreInformation> getStoreList() {
        storeList = storeInfoInterface.findAllStoreInfo();
        return storeList;
    }
    /*This Method is used to handle Select Store Name Search
     */

    public void handleSelectStoreNameSearch(ValueChangeEvent event) {
        if (null != event.getNewValue()) {

            storeInfoEntity = (MmsStoreInformation) event.getNewValue();

//         
        }

    }
    /*This Method is used to handle Select Store Name Main
     */

    public void handleSelectStoreNameMain(ValueChangeEvent event) {
        if (null != event.getNewValue()) {

            storeInfoEntity = (MmsStoreInformation) event.getNewValue();
            mmsgrn.setStoreId(storeInfoEntity);
            itemRegEntity.setStoreId(storeInfoEntity);
            materialNameList = itemRegInterface.searchItemByStoreId(itemRegEntity);
            System.out.println("=========Material name size=====" + materialNameList.size());
        }
    }
    /*This Method is used to change On Entry Type
     */

    public void changeOnEntryType(ValueChangeEvent changeEvent) {
        if (changeEvent.getNewValue() != null) {
            if (changeEvent.getNewValue().toString().equals("stock")) {
                System.out.println("stock selected");
                isStockSelected = true;
                mmsgrn.setEnteryType("stock");
            } else {
                isStockSelected = false;
                mmsgrn.setEnteryType("Department");
            }
        }
    }
    /*This Method is used to handle Select Item Name
     */

    public void handleSelectItemName(SelectEvent event) {
        if (event.getObject() != null) {
            itemRegEntity = (MmsItemRegistration) event.getObject();
            grndetail.setItemId(itemRegEntity);
        }
    }
    /*This Method is used to search Mat Information By Prefix
     */

    public ArrayList<MmsItemRegistration> searchMatInformationByPrefix(String matcode) {
        ArrayList<MmsItemRegistration> itemInformations = new ArrayList<>();// to make the previous search  paramaters and results null;

//        itemInformations = itemRegInterface.searchMatInformationByPrefix(matcode);
        return itemInformations;
    }
    /*This Method is used to get List O fDelivery Option
     */

    public List<mms_lu_delivery_option> getListOfDeliveryOption() {
        deliveryOption = deliveryoptionInterface.findAll();
        return deliveryOption;
    }
    /*This Method is used to handle Select Delivery Option
     */

    public void handleSelectDeliveryOption(ValueChangeEvent event) {
        if (null != event.getNewValue()) {
            deliveryOptions = (mms_lu_delivery_option) event.getNewValue();
            mmsgrn.setDeliveryoption(deliveryOptions);

        }
    }
    /*This Method is used to recreate Work flow DataModel
     */

    public void recreateWfDataModel() {
        WorkflowDataModel = null;
        for (int i = 0; i < mmsgrn.getWfMmsProcessedList().size(); i++) {
            if (mmsgrn.getWfMmsProcessedList().get(i).getDecision() != null) {
                if (mmsgrn.getWfMmsProcessedList().get(i).getDecision() == 1 || mmsgrn.getWfMmsProcessedList().get(i).getDecision() == 3) {
                    mmsgrn.getWfMmsProcessedList().get(i).setWfDecison("Approved");
                } else if (mmsgrn.getWfMmsProcessedList().get(i).getDecision() == 2 || mmsgrn.getWfMmsProcessedList().get(i).getDecision() == 4) {
                    mmsgrn.getWfMmsProcessedList().get(i).setWfDecison("Rejected");
                }
            } else {
                mmsgrn.getWfMmsProcessedList().get(i).setWfDecison("Requested");
            }

        }

        WorkflowDataModel = new ListDataModel(mmsgrn.getWfMmsProcessedList());
    }
    /*This Method is used to item Information List
     */

    public List<MmsItemRegistration> itemInformationList(String itemName) {
        itemRegEntity.setMatName(itemName);
        itemRegEntity.setStoreId(storeInfoEntity);
        materialNameList = itemRegInterface.searchMaterialInfoByStoreAndMatName(itemRegEntity);
        return materialNameList;
    }
    /*This Method is used to get Mms Item Information
     */

    public void getMmsItemInformation(SelectEvent event) {
        itemRegEntity.setMatName(event.getObject().toString());
        itemRegEntity = itemRegInterface.getByMaterialName(itemRegEntity);

        grndetail.setItemId(itemRegEntity);
    }

    /*This Method is used to add Grn Detail
     */
    public String addGrnDetail() {

        mmsBinCard = new MmsBinCard();
        mmsBinCard = itemRegEntity.getMmsBinCard();
        BigDecimal currentqunt = mmsBinCard.getCurrentQuantity();
        mmsgrn.addGRnDetialInfo(grndetail);

        recreateGrnDataModelNew();
        clearPopUp();
        return null;
    }
    /*This Method is used to clear PopUp
     */

    private void clearPopUp() {

        itemRegEntity = null;
        grndetail = null;
    }

// </editor-fold>
}
