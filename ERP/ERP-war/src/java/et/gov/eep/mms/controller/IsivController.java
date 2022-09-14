/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.mms.controller;

import java.io.Serializable;
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
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import securityBean.Constants;
import securityBean.SessionBean;
import securityBean.WorkFlow;
import webService.AAA;
import webService.EventEntry;
import webService.IAdministration;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import et.gov.eep.commonApplications.businessLogic.WfMmsProcessedBeanLocal;
import et.gov.eep.commonApplications.entity.WfMmsProcessed;
import et.gov.eep.commonApplications.entity.ColumnNameResolver;
import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.fcms.businessLogic.stock.FmsTotalStockValueBeanLocal;
import et.gov.eep.fcms.entity.stock.FmsTotalStockValue;
import et.gov.eep.hrms.businessLogic.organization.HrDepartmentsBeanLocal;
import et.gov.eep.hrms.entity.organization.HrDepartments;
import et.gov.eep.mms.businessLogic.MmsBinCardBeanLocal;
import et.gov.eep.mms.businessLogic.MmsIsivBeanLocal;
import et.gov.eep.mms.businessLogic.MmsIsivDetailBeanLocal;
import et.gov.eep.mms.businessLogic.MmsStoreInformationBeanLocal;
import et.gov.eep.mms.businessLogic.MmsStoreReqBeanLocal;
import et.gov.eep.mms.businessLogic.MmsStorereqDetailBeanLocal;
import et.gov.eep.mms.entity.MmsBinCard;
import et.gov.eep.mms.entity.MmsIsiv;
import et.gov.eep.mms.entity.MmsIsivDetail;
import et.gov.eep.mms.entity.MmsItemRegistration;
import et.gov.eep.mms.entity.MmsStoreInformation;
import et.gov.eep.mms.entity.MmsStorereq;
import et.gov.eep.mms.entity.MmsStorereqDetail;
import et.gov.eep.prms.entity.PrmsServiceProvider;
import et.gov.eep.prms.integration.ServiceProviderIntegrationBeanLocal;

/**
 *
 * @author Minab
 */
@Named(value = "isivController")
@ViewScoped
public class IsivController implements Serializable {
//<editor-fold defaultstate="collapsed" desc="Entities">

    @Inject
    FmsTotalStockValue fmsTotalStockValue;
    @Inject
    MmsIsiv isivEntity;
    @Inject
    MmsStorereq srEntity;
    @Inject
    private MmsBinCard mmsBinCard;
    @Inject
    MmsStorereqDetail srDetailentity;
    @Inject
    MmsIsivDetail isivdetail;
    @Inject
    MmsStoreInformation fromStore;
    @Inject
    private MmsStoreInformation storeEntity;
    @Inject
    WfMmsProcessed wfMmsProcessed;
    @Inject
    private PrmsServiceProvider serviceProviderEntity;
    @Inject
    private MmsStoreInformation toStore;
    @Inject
    private HrDepartments hrDepartmentsEntity;
    @Inject
    SessionBean SessionBean;
    @Inject
    WorkFlow workflow;
    @Inject
    MmsItemRegistration ItemRegistration;
    @Inject
    ColumnNameResolver columnNameResolver;
    //</editor-fold>

//<editor-fold defaultstate="collapsed" desc="EJB">
    @EJB
    WfMmsProcessedBeanLocal wfMmsProcessedBeanLocal;
    @EJB
    MmsStoreInformationBeanLocal storebeanLocal;
    @EJB
    MmsIsivBeanLocal transferBeanLocal;
    @EJB
    MmsStoreReqBeanLocal srbeanLocal;
    @EJB
    MmsStorereqDetailBeanLocal srDetailBeanlocal;
    @EJB
    MmsIsivDetailBeanLocal isivDetailbeanLocal;
    @EJB
    private HrDepartmentsBeanLocal hrdepartmentInterface;
    @EJB
    MmsBinCardBeanLocal mmsBinCardBeanLocal;
    @EJB
    ServiceProviderIntegrationBeanLocal serviceProviderInterface;
    @EJB
    FmsTotalStockValueBeanLocal fmsTotalStockValueBeanLocal;
    //</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Fields">
    DataModel<WfMmsProcessed> WorkflowDataModel = new ListDataModel<>();
    List<WfMmsProcessed> WfList;
    private List<MmsIsiv> mmsIsivList;
    DataModel<MmsStorereqDetail> srDetailDataModel;
    DataModel<MmsIsivDetail> isivDetailDataModel;
    List<HrDepartments> allDepartmentsList = new ArrayList<>();
    private static List<HrDepartments> araListe;
    int updateStatus = 0;
    String btn_SaveUpdate = "Save";
    String srNumber = "";
    String disableSRselection = "false";
    String storeLocation = "";
    private String saveorUpdateBundle = "Save";
    private TreeNode root;
    private TreeNode root2;
    private TreeNode selectedNode;
    private String createOrSearchBundle = "New";
    private boolean renderDecision = false;
    private boolean renderPnlCreateISIV = false;
    private boolean isRenderedIconNitification = true;
    private boolean isRenderedIconWorkflow = false;
    private boolean renderPnlManPage = true;
    private boolean disableBtnCreate;
    private boolean disableButtonUpdate = false;
    private boolean renderpnlToSearchPage;
    private String icone = "ui-icon-plus";
    private String activeIndex;
    String newPayNo;
    String lastPaymentNo = "0";
    private String userName;
    private String selectedValue = "";
    private List<MmsStoreInformation> toStoreList;
    List<HrDepartments> DepartmentNames = new ArrayList<>();
    private MmsIsiv selectedItem;
    private DataModel<MmsIsiv> mmsIsivSearchInfoDataModel;
    private List<MmsIsiv> allIsivInfoList;
    List<PrmsServiceProvider> serviceProviderList;
    List<MmsStoreInformation> StoreList;
    private boolean saved = false;
    ArrayList<MmsBinCard> bincardList = new ArrayList<>();
    private List<MmsStorereq> srList = new ArrayList<>();
    private List<MmsIsiv> mmsIsivColumnNameList;
    private List<String> mmsIsveColumnNameList;
    ColumnNames columnNames = new ColumnNames();
    List<ColumnNames> ColumnNamesList1 = new ArrayList<>();
    List<ColumnNameResolver> ColumnNamesList = new ArrayList<>();

    //</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Postconstraction">
    public IsivController() {
    }

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
        if (workflow.isApproveStatus()) {
            System.out.println("app or check");
            renderDecision = true;
            mmsIsivList = transferBeanLocal.findIsivListByWfStatus(Constants.PREPARE_VALUE);
            isRenderedIconNitification = true;
        } else if (workflow.isPrepareStatus()) {
            toStoreList = storebeanLocal.findAllStoreInfo();
            System.out.println("prep");
            renderDecision = false;
            mmsIsivList = transferBeanLocal.findIsivListByWfStatus(Constants.APPROVE_REJECT_VALUE);
            if (mmsIsivList != null) {
                isRenderedIconNitification = mmsIsivList.size() > 0;
            }
        }
        wfMmsProcessed.setProcessedBy(SessionBean.getUserId());

        setUserName(SessionBean.getUserName());
        System.out.println("is App==" + workflow.isApproveStatus() + "is chech==" + workflow.isCheckStatus() + "is prepa==" + workflow.isPrepareStatus());
        allDepartmentsList = hrdepartmentInterface.findAll();
        root = new DefaultTreeNode("Root", null);

        recursive(allDepartmentsList, 0, root);
        root2 = getRoot();
        getMmsIsveColumnNameList();
//        loadAddressTree();
//        hrEmployees.setEmploymentType("Permanent");
//        hrEmployees.setSex("Male");

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

    public void onSelectIsivRequest(ValueChangeEvent event) {
        try {
            if (null != event.getNewValue()) {
                isivEntity = (MmsIsiv) event.getNewValue();
                populateUI();
            }
        } catch (Exception e) {
            //e.printStackTrace();
            JsfUtil.addFatalMessage("Faile to process! Try Again Reloading the Page");
        }
    }

    //</editor-fold>
//<editor-fold defaultstate="collapsed" desc="getter and setter">    
    public boolean isDisableBtnCreate() {
        return disableBtnCreate;
    }

    public void setDisableBtnCreate(boolean disableBtnCreate) {
        this.disableBtnCreate = disableBtnCreate;
    }

    public String getSelectedValue() {
        return selectedValue;
    }

    public void setSelectedValue(String selectedValue) {
        this.selectedValue = selectedValue;
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

    public List<MmsStorereq> getSrList() {
        if (srList == null) {
            srList = new ArrayList<>();
        }
        return srList;
    }

    public void setSrList(List<MmsStorereq> srList) {
        this.srList = srList;
    }

    public List<MmsIsiv> getMmsIsivColumnNameList() {
        if (mmsIsivColumnNameList == null) {
            mmsIsivColumnNameList = new ArrayList<>();
//            mmsIsivColumnNameList = transferBeanLocal.getMmsIsivColumnNameList();

        }
        return mmsIsivColumnNameList;
    }

    public void setMmsIsivColumnNameList(List<MmsIsiv> mmsIsivColumnNameList) {
        this.mmsIsivColumnNameList = mmsIsivColumnNameList;
    }

    public boolean isIsRenderedIconNitification() {
        return isRenderedIconNitification;
    }

    public void setIsRenderedIconNitification(boolean isRenderedIconNitification) {
        this.isRenderedIconNitification = isRenderedIconNitification;
    }

    public FmsTotalStockValue getFmsTotalStockValue() {
        if (fmsTotalStockValue == null) {
            fmsTotalStockValue = new FmsTotalStockValue();
        }
        return fmsTotalStockValue;
    }

    public void setFmsTotalStockValue(FmsTotalStockValue fmsTotalStockValue) {
        this.fmsTotalStockValue = fmsTotalStockValue;
    }

    public MmsItemRegistration getItemRegistration() {
        if (ItemRegistration == null) {
            ItemRegistration = new MmsItemRegistration();
        }
        return ItemRegistration;
    }

    public void setItemRegistration(MmsItemRegistration ItemRegistration) {
        this.ItemRegistration = ItemRegistration;
    }

    public List<MmsIsiv> getMmsIsivList() {
//        if (mmsIsivList == null) {
//            mmsIsivList = new ArrayList<>();
//        }
        return mmsIsivList;
    }

    public void setMmsIsivList(List<MmsIsiv> mmsIsivList) {
        this.mmsIsivList = mmsIsivList;
    }

    public boolean isRenderDecision() {
        return renderDecision;
    }

    public void setRenderDecision(boolean renderDecision) {
        this.renderDecision = renderDecision;
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

    public DataModel<WfMmsProcessed> getWorkflowDataModel() {
        if (WorkflowDataModel == null) {
            WorkflowDataModel = new ListDataModel<>();
        }
        return WorkflowDataModel;
    }

    public void setWorkflowDataModel(DataModel<WfMmsProcessed> WorkflowDataModel) {
        this.WorkflowDataModel = WorkflowDataModel;
    }

    public List<WfMmsProcessed> getWfList() {
        return WfList;
    }

    public void setWfList(List<WfMmsProcessed> WfList) {
        this.WfList = WfList;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     *
     * @return
     */
    public MmsIsiv getMaterialtransferEntity() {

        if (isivEntity == null) {
            isivEntity = new MmsIsiv();
        }
        return isivEntity;
    }

    /**
     *
     * @param isivEntity
     */
    public void setMaterialtransferEntity(MmsIsiv isivEntity) {
        this.isivEntity = isivEntity;
    }

    /**
     *
     * @return
     */
    public MmsStorereq getSrEntity() {
        if (srEntity == null) {
            srEntity = new MmsStorereq();
        }
        return srEntity;
    }

    /**
     *
     * @param srEntity
     */
    public void setSrEntity(MmsStorereq srEntity) {
        this.srEntity = srEntity;
    }

    /**
     *
     * @return
     */
    public DataModel<MmsStorereqDetail> getSrDetailDataModel() {
        if (srDetailDataModel == null) {
            srDetailDataModel = new ListDataModel<>();
        }
        return srDetailDataModel;
    }

    /**
     *
     * @return
     */
    public MmsStorereqDetail getSrDetailentity() {
        if (srDetailentity == null) {
            srDetailentity = new MmsStorereqDetail();
        }
        return srDetailentity;
    }

    public PrmsServiceProvider getServiceProviderEntity() {
        if (serviceProviderEntity == null) {
            serviceProviderEntity = new PrmsServiceProvider();
        }
        return serviceProviderEntity;
    }

    public void setServiceProviderEntity(PrmsServiceProvider serviceProviderEntity) {
        this.serviceProviderEntity = serviceProviderEntity;
    }

    /**
     *
     * @param srDetailentity
     */
    public void setSrDetailentity(MmsStorereqDetail srDetailentity) {
        this.srDetailentity = srDetailentity;
    }

    /**
     *
     * @param srDetailDataModel
     */
    public void setSrDetailDataModel(DataModel<MmsStorereqDetail> srDetailDataModel) {
        this.srDetailDataModel = srDetailDataModel;
    }

    private void recreateIsivDataModel() {
        isivDetailDataModel = null;
        isivDetailDataModel = new ListDataModel(new ArrayList<>(isivEntity.getMmsIsivDetailList()));
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

    /**
     *
     * @return
     */
    public String getBtn_SaveUpdate() {
        return btn_SaveUpdate;
    }

    /**
     *
     * @param btn_SaveUpdate
     */
    public void setBtn_SaveUpdate(String btn_SaveUpdate) {
        this.btn_SaveUpdate = btn_SaveUpdate;
    }

    /**
     *
     * @return
     */
    public MmsStoreInformation getStoreinfoentity() {
        if (fromStore == null) {
            fromStore = new MmsStoreInformation();
        }
        return fromStore;
    }

    /**
     *
     * @param fromStore
     */
    public void setStoreinfoentity(MmsStoreInformation fromStore) {
        this.fromStore = fromStore;
    }

    /**
     *
     * @return
     */
    public DataModel<MmsIsivDetail> getIsivDetailDataModel() {
        if (isivDetailDataModel == null) {
            isivDetailDataModel = new ListDataModel<>();
        }
        return isivDetailDataModel;
    }

    /**
     *
     * @param isivDetailDataModel
     */
    public void setIsivDetailDataModel(DataModel<MmsIsivDetail> isivDetailDataModel) {
        this.isivDetailDataModel = isivDetailDataModel;
    }

    /**
     *
     * @return
     */
    public String getSrNumber() {
        return srNumber;
    }

    /**
     *
     * @param srNumber
     */
    public void setSrNumber(String srNumber) {
        this.srNumber = srNumber;
    }

    /**
     *
     * @return
     */
    public String getDisableSRselection() {
        return disableSRselection;
    }

    /**
     *
     * @param disableSRselection
     */
    public void setDisableSRselection(String disableSRselection) {
        this.disableSRselection = disableSRselection;
    }

    /**
     *
     * @return
     */
    public String getStoreLocation() {
        return storeLocation;
    }

    /**
     *
     * @param storeLocation
     */
    public void setStoreLocation(String storeLocation) {
        this.storeLocation = storeLocation;
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

    public boolean isDisableButtonUpdate() {
        return disableButtonUpdate;
    }

    public void setDisableButtonUpdate(boolean disableButtonUpdate) {
        this.disableButtonUpdate = disableButtonUpdate;
    }

    public boolean isRenderpnlToSearchPage() {
        return renderpnlToSearchPage;
    }

    public void setRenderpnlToSearchPage(boolean renderpnlToSearchPage) {
        this.renderpnlToSearchPage = renderpnlToSearchPage;
    }

    public MmsStoreInformation getStoreEntity() {
        if (storeEntity == null) {
            storeEntity = new MmsStoreInformation();
        }
        return storeEntity;
    }

    public void setStoreEntity(MmsStoreInformation storeEntity) {
        this.storeEntity = storeEntity;
    }

    public MmsBinCard getMmsBinCard() {
        if (mmsBinCard == null) {
            mmsBinCard = new MmsBinCard();
        }
        return mmsBinCard;
    }

    public void setMmsBinCard(MmsBinCard mmsBinCard) {
        this.mmsBinCard = mmsBinCard;
    }

    public boolean isIsRenderedIconWorkflow() {
        return isRenderedIconWorkflow;
    }

    public void setIsRenderedIconWorkflow(boolean isRenderedIconWorkflow) {
        this.isRenderedIconWorkflow = isRenderedIconWorkflow;
    }

    public List<MmsStoreInformation> getToStoreList() {
        return toStoreList;
    }

    public void setTotoreList(List<MmsStoreInformation> TotoreList) {
        this.toStoreList = TotoreList;
    }

    public List<MmsStoreInformation> getStoreList() {
        StoreList = storebeanLocal.findAllStoreInfo();
        return StoreList;
    }

    public List<PrmsServiceProvider> getServiceProviderList() {
        serviceProviderList = serviceProviderInterface.findAllServiceProviders();
        return serviceProviderList;
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

    public boolean isRenderPnlCreateISIV() {
        return renderPnlCreateISIV;
    }

    public void setRenderPnlCreateISIV(boolean renderPnlCreateISIV) {
        this.renderPnlCreateISIV = renderPnlCreateISIV;
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

    public DataModel<MmsIsiv> getMmsIsivSearchInfoDataModel() {
        if (mmsIsivSearchInfoDataModel == null) {
            mmsIsivSearchInfoDataModel = new ListDataModel<>();
        }
        return mmsIsivSearchInfoDataModel;
    }

    public void setMmsIsivSearchInfoDataModel(DataModel<MmsIsiv> mmsIsivSearchInfoDataModel) {
        this.mmsIsivSearchInfoDataModel = mmsIsivSearchInfoDataModel;
    }
    //for row selection

    public MmsIsiv getSelectedItem() {
        return selectedItem;
    }

    public void setSelectedItem(MmsIsiv selectedItem) {
        this.selectedItem = selectedItem;
    }

    public MmsStoreInformation getStoreinfoentity2() {
        if (toStore == null) {
            toStore = new MmsStoreInformation();
        }
        return toStore;
    }

    public void setStoreinfoentity2(MmsStoreInformation toStore) {
        this.toStore = toStore;
    }

    public HrDepartments getHrDepartmentsEntity() {
        if (hrDepartmentsEntity == null) {
            hrDepartmentsEntity = new HrDepartments();
        }
        return hrDepartmentsEntity;
    }

    public void setHrDepartmentsEntity(HrDepartments hrDepartmentsEntity) {
        this.hrDepartmentsEntity = hrDepartmentsEntity;
    }

    public List<HrDepartments> getDepartmentNames() {

        DepartmentNames = hrdepartmentInterface.findAllDepartmentInfo();

        return DepartmentNames;
    }

    /**
     *
     * @return
     */
    //</editor-fold>
//<editor-fold defaultstate="collapsed" desc="handler,save,sreach and clear"> 
    /*This method is used to generate Transfer No
     */
    public String generateTransferNo() {
        if (updateStatus == 1) {
            newPayNo = isivEntity.getTransferNo();
        } else {
            MmsIsiv lastPaymentNoObj = transferBeanLocal.getLastTransferNo();
            if (lastPaymentNoObj != null) {
                lastPaymentNo = lastPaymentNoObj.getTransferId().toString();
            }
            DateFormat f = new SimpleDateFormat("yyyy");
            Date now = new Date();
            int newPayment = 0;
            if (lastPaymentNo.equals("0")) {
                newPayment = 1;
                newPayNo = "ISIV-" + newPayment + "/" + f.format(now);
            } else {
                String[] lastInspNos = lastPaymentNo.split("-");
                String lastDatesPatern = lastInspNos[0];
                String[] lastDatesPaterns = lastDatesPatern.split("/");
                newPayment = Integer.parseInt(lastDatesPaterns[0]);
                newPayment = newPayment + 1;
                newPayNo = "ISIV-" + newPayment + "/" + f.format(now);
            }

        }
        return newPayNo;
    }
    /*This method is used to column Name Change Event
     */

    public void columnNameChangeEvent(ValueChangeEvent event) {

        if (event.getNewValue() != null) {
            System.out.println("event.getNewValue().toString()==" + event.getNewValue().toString());
            columnNameResolver.setCol_Name_FromTable(event.getNewValue().toString());
            columnNameResolver.setParsed_Col_Name(ColumnParser(columnNameResolver.getCol_Name_FromTable()));
            isivEntity.setColumnName(columnNameResolver.getCol_Name_FromTable());
            isivEntity.setColumnValue(null);
        }
    }

    public String ColumnParser(String col_name) {
        String col = col_name.replace("_", " ");
        System.out.println("col==" + col);
        return col;
    }

    public List<String> getMmsIsveColumnNameList() {
        mmsIsveColumnNameList = transferBeanLocal.getMmsIsivColumnNameList();
        if (mmsIsveColumnNameList == null) {
            mmsIsveColumnNameList = new ArrayList<>();
        }
        System.out.println("=======mmsRmgColumnNameList==" + mmsIsveColumnNameList);
        if (mmsIsveColumnNameList.size() > 0) {
            ColumnNamesList = new ArrayList<>();
            for (int i = 0; i < mmsIsveColumnNameList.size(); i++) {
                ColumnNameResolver obj = new ColumnNameResolver();
                obj.setCol_Name_FromTable((mmsIsveColumnNameList.get(i).toLowerCase()));
                obj.setParsed_Col_Name(ColumnParser((mmsIsveColumnNameList.get(i).toLowerCase())));
                ColumnNamesList.add(obj);
            }

        }
        return mmsIsveColumnNameList;
    }

    public void setMmsIsveColumnNameList(List<String> mmsIsveColumnNameList) {
        this.mmsIsveColumnNameList = mmsIsveColumnNameList;
    }

    /*This method is used to get selectedsr Info
     */
    public void getselectedsrInfo(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            srEntity = (MmsStorereq) event.getNewValue();

            isivEntity.setStoreReqId(srEntity);
            isivEntity.setDepartment(srEntity.getDeptId());
            isivEntity.setFromStore(srEntity.getRequestStore());
            toStore = srEntity.getToStore();

            isivEntity.setToStore(toStore);
            for (int i = 0; i < srEntity.getMmsStorereqDetailList().size(); i++) {
                MmsIsivDetail isivIDetail = new MmsIsivDetail();

                isivIDetail.setItemId(srEntity.getMmsStorereqDetailList().get(i).getItemId());
                isivIDetail.setQuantity(srEntity.getMmsStorereqDetailList().get(i).getQuantity());
                fmsTotalStockValue = new FmsTotalStockValue();
                fmsTotalStockValue = fmsTotalStockValueBeanLocal.getWeightedAverageValueByMatCode(srEntity.getMmsStorereqDetailList().get(i).getItemId().getMatCode());
                mmsBinCard = new MmsBinCard();
                ItemRegistration = new MmsItemRegistration();
                ItemRegistration = srEntity.getMmsStorereqDetailList().get(i).getItemId();
                isivEntity.addIsIvInfo(isivIDetail);

//            
            }
            recreateIsivDataModel();

        }

    }
    /*This method is used to update SR Detail
     */

    public void updateSrDetail() {
        srDetailentity = getSrDetailDataModel().getRowData();
    }
    /*This method is used to Workflow Save
     */

    public void WfSave() {
        wfMmsProcessed.setTransferId(isivEntity);
        wfMmsProcessedBeanLocal.create(wfMmsProcessed);
    }
    /*This method is used to Save Isiv Issued
     */

    public void saveIsivIssued() {
        try {
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(SessionBean.getUserName(), "saveIsivIssued", dataset)) {
                if (updateStatus == 0) {
                    if (isivDetailDataModel.getRowCount() <= 0) {
                        JsfUtil.addFatalMessage("Saving Without Data is Not Allowed");
                    } else {
                        try {
                            generateTransferNo();
                            isivEntity.setTransferNo(newPayNo);
                            wfMmsProcessed.setProcessedBy(SessionBean.getUserId());
                            isivEntity.addIsivIdToWorkflow(wfMmsProcessed);
                            isivEntity.setProcessedBy(SessionBean.getUserId());
                            isivEntity.setProcessedOn(wfMmsProcessed.getProcessedOn());
                            isivEntity.setStatusWf(Constants.PREPARE_VALUE);
                            isivEntity.getMmsIsivDetailList().add(isivdetail);
                            transferBeanLocal.create(isivEntity);
                            srEntity.setStatus(Constants.SR_PROCESSED_BY_ISIV);
                            srbeanLocal.edit(srEntity);
                            JsfUtil.addSuccessMessage("ISIV Data is Created" + isivEntity.getTransferNo());
                            clearPage();
                            //end filling
                        } catch (Exception e) {
                            e.printStackTrace();
                            JsfUtil.addFatalMessage("Something Error Occured on Data Saved");
                            clearPage();

                        }
                    }
                } else if (updateStatus == 1 && workflow.isPrepareStatus()) {
                    try {
                        isivEntity.setSrNo(srNumber);
                        isivEntity.setProcessedOn(wfMmsProcessed.getProcessedOn());
                        isivEntity.setCommentGiven(wfMmsProcessed.getCommentGiven());
                        isivEntity.setStatusWf(Constants.PREPARE_VALUE);
                        transferBeanLocal.edit(isivEntity);
                        JsfUtil.addSuccessMessage("ISIV data is Modified");
                        saveorUpdateBundle = "Save";
                        mmsIsivList.remove(isivEntity);
                        clearPage();
                    } catch (Exception e) {
                        JsfUtil.addErrorMessage("Something Error Occured on Data Modified");
                        clearPage();

                    }

                } else if (updateStatus == 1 && (workflow.isApproveStatus() || workflow.isCheckStatus())) {
                    System.out.println("12");
                    if (selectedValue.equalsIgnoreCase("Approve") && workflow.isApproveStatus()) {
                        workflow.setUserStatus(Constants.APPROVE_VALUE);
                        isivEntity.setStatusWf(Constants.APPROVE_VALUE);
                        wfMmsProcessed.setDecision(Constants.APPROVE_VALUE);
                        System.out.println("288");
                        saveWorkflowAndClear();
                        if (saved) {
                            boolean status = updateBinCard(srEntity.getRequestStore().getStoreId(), srEntity.getSrNo());
                            System.out.println("====status controller===" + status);
                            clearPage();
                        } else {
                            JsfUtil.addFatalMessage("Procedure execution failed");
                        }
                    } else if (selectedValue.equalsIgnoreCase("Approve") && workflow.isCheckStatus()) {
                        workflow.setUserStatus(Constants.CHECK_APPROVE_VALUE);
                        isivEntity.setStatusWf(Constants.CHECK_APPROVE_VALUE);
                        wfMmsProcessed.setDecision(Constants.CHECK_APPROVE_VALUE);
                        System.out.println("289");
                    } else if (selectedValue.equalsIgnoreCase("Reject") && workflow.isApproveStatus()) {
                        workflow.setUserStatus(Constants.APPROVE_REJECT_VALUE);
                        isivEntity.setStatusWf(Constants.APPROVE_REJECT_VALUE);
                        wfMmsProcessed.setDecision(Constants.APPROVE_REJECT_VALUE);
                        saveWorkflowAndClear();
                        clearPage();
                    } else if (selectedValue.equalsIgnoreCase("Reject") && workflow.isCheckStatus()) {
                        workflow.setUserStatus(Constants.CHECK_REJECT_VALUE);
                        isivEntity.setStatusWf(Constants.CHECK_REJECT_VALUE);
                        wfMmsProcessed.setDecision(Constants.CHECK_REJECT_VALUE);
                        System.out.println("290");
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
//..... add more information by calling fields defined in the object 
                security.addEventLog(eventEntry, dataset);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    /*This method is used to save Work flow And Clear
     */

    public void saveWorkflowAndClear() {
        isivEntity.addIsivIdToWorkflow(wfMmsProcessed);
        transferBeanLocal.edit(isivEntity);
        wfMmsProcessedBeanLocal.create(wfMmsProcessed);
        mmsIsivList.remove(isivEntity);
        saved = true;
        JsfUtil.addSuccessMessage("ISIV Data Modified");
    }
    /*This method is used to update Bin Card
     */

    public boolean updateBinCard(int storeId, String srNo) {
        try {
            System.out.println("========Inside try=========");
            int status;
            //RequestContext context = RequestContext.getCurrentInstance();
            System.out.println("==========store========" + storeId);
            System.out.println("==========SrNo========" + srNo);
            status = mmsBinCardBeanLocal.deductFromBinCard(storeId, srNo);

            return status == 0;
        } catch (Exception ex) {

            return false;
        }

    }
    /*This method is used to get By ISiv Numbers
     */

    public void getByISivNumbers(SelectEvent event) {
        MmsIsivDetail isivdetailObject;
        isivdetailObject = isivDetailbeanLocal.getDetailbyId(isivEntity);
        for (int i = 0; i < isivEntity.getMmsIsivDetailList().size(); i++) {
            srDetailentity = new MmsStorereqDetail();
            srDetailentity.setItemId(isivEntity.getMmsIsivDetailList().get(i).getItemId());

            srDetailentity.setQuantity(isivEntity.getMmsIsivDetailList().get(i).getQuantity());

            srDetailentity.setUnitPrice(isivEntity.getMmsIsivDetailList().get(i).getUnitPrice());

            srEntity.addRequisitionDetialInfo(srDetailentity);
        }

        recreateIsivDataModel();

        srNumber = isivEntity.getSrNo();
        updateStatus = 1;
        disableSRselection = "true";

    }

    /*This method is used to increament Id
     */
    public int increamentId() {
        int id = isivdetail.getMatTranDetId();
        id = id++;
        return id;
    }

    /*This method is used to clear Page
     */
    public String clearPage() {
        fromStore = new MmsStoreInformation();
        toStore = new MmsStoreInformation();
        hrDepartmentsEntity = new HrDepartments();
        isivEntity = new MmsIsiv();
        serviceProviderEntity = new PrmsServiceProvider();
        updateStatus = 0;
        isivdetail = new MmsIsivDetail();
        srEntity = new MmsStorereq();
        storeEntity = new MmsStoreInformation();
        srDetailDataModel = null;
        isivDetailDataModel = null;
        wfMmsProcessed = new WfMmsProcessed();
        setSelectedValue(null);
        mmsIsivSearchInfoDataModel = new ListDataModel<>();
        return null;
    }
    /*This method is used to search By isiv No
     */

    public ArrayList<MmsIsiv> searchByisivNo(String isivno) {
        ArrayList<MmsIsiv> isivNos = null;
        isivEntity.setTransferNo(isivno);
        isivNos = transferBeanLocal.searchByIsIvNo(isivEntity);
        updateStatus = 1;
        btn_SaveUpdate = "Update";
        return isivNos;

    }
    /*This method is used to handle Select To Store
     */

    public void handleSelectToStore(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            toStore = (MmsStoreInformation) event.getNewValue();
            isivEntity.setToStore(toStore);
        }
    }
    /*This method is used to handle Select From Store
     */

    public void handleSelectFromStore(ValueChangeEvent event) {
        if (null != event.getNewValue()) {
            System.out.println("======event=====" + event.getNewValue());
            fromStore = (MmsStoreInformation) event.getNewValue();
//            toStoreList.remove(fromStore);
            isivEntity.setFromStore(fromStore);
            srList = srbeanLocal.findAllwithApprovedStatusForIsiv(fromStore, Constants.APPROVE_VALUE);

        }
    }
    /*This method is used to get Papms Store Info
     */

    public void getPapmsStoreInfo(SelectEvent event) {
        if (event.getComponent().getId().equalsIgnoreCase("autoStoreName")) {
            fromStore.setStoreName(event.getObject().toString());
            fromStore = storebeanLocal.getPapmsStoreInformation(fromStore);
            isivEntity.setFromStore(fromStore);
        } else if (event.getComponent().getId().equalsIgnoreCase("autoStoreName2")) {
            toStore.setStoreName(event.getObject().toString());
            toStore = storebeanLocal.getPapmsStoreInformation(toStore);
            isivEntity.setToStore(toStore);
        }

    }
    /*This method is used to handleSelectStoreName
     */

    public void handleSelectStoreName(ValueChangeEvent event) {
        if (null != event.getNewValue()) {
            storeEntity = (MmsStoreInformation) event.getNewValue();
        }

    }
    /*This method is used to handle Select Service Provider
     */

    public void handleSelectServiceProvider(ValueChangeEvent event) {
        if (null != event.getNewValue()) {
            isivEntity.setTransporter(serviceProviderEntity);
        }
    }
    /*This method is used to on Node Select
     */

    public void onNodeSelect(NodeSelectEvent event) {
        selectedNode = event.getTreeNode();
        int key = Integer.valueOf((selectedNode.getData().toString()).split("--")[0]);
        System.out.println(selectedNode.getData().toString());
        hrDepartmentsEntity.setDepId(key);
        hrDepartmentsEntity = hrdepartmentInterface.findByDepartmentId(hrDepartmentsEntity);
        isivEntity.setDepartment(hrDepartmentsEntity);
    }

    /*This method is used to search Store Information
     */
    public ArrayList<MmsStoreInformation> searchStoreInformation(String storeName) {
        ArrayList<MmsStoreInformation> storeInformations = null;// to make the previous search  paramaters and results null;
        fromStore.setStoreName(storeName);
        storeInformations = storebeanLocal.searchStoreInformation(fromStore);
        return storeInformations;
    }
    /*This method is used to create New ISIV Info
     */

    public void createNewISIVInfo() {

        clearPage();
        saveorUpdateBundle = "Save";
        if (createOrSearchBundle.equals("New")) {
            renderPnlCreateISIV = true;
            renderPnlManPage = false;
            createOrSearchBundle = "Search";
            setCreateOrSearchBundle(createOrSearchBundle);
            setIcone("ui-icon-search");
        } else if (createOrSearchBundle.equals("Search")) {
            renderPnlCreateISIV = false;
            renderPnlManPage = true;
            createOrSearchBundle = "New";
            setCreateOrSearchBundle(createOrSearchBundle);
            setIcone("ui-icon-plus");
        }
    }
    /*This method is used to go Back Search Button Action
     */

    public void goBackSearchButtonAction() {
        renderpnlToSearchPage = false;
        renderPnlCreateISIV = false;
        renderPnlManPage = true;
    }
    /*This method is used to create New Isiv Info
     */

    public void createNewIsivInfo() {

        clearPage();
        saveorUpdateBundle = "Save";
        disableBtnCreate = false;
        renderpnlToSearchPage = false;
        if (createOrSearchBundle.equals("New")) {
            renderPnlCreateISIV = true;
            renderPnlManPage = false;
            createOrSearchBundle = "Search";
            setIcone("ui-icon-search");
        } else if (createOrSearchBundle.equals("Search")) {
            renderPnlCreateISIV = false;
            renderPnlManPage = true;
            createOrSearchBundle = "New";
            setIcone("ui-icon-plus");
        }
    }
    /*This method is used to btn Search Action
     */

    public void btn_Search_Action() {
        clearPage();
        renderPnlCreateISIV = false;
        renderPnlManPage = true;
    }
    /*This method is used to search Isiv Information
     */

    public void searchIsivInformation() {
        System.out.println("in search");
        isivEntity.setProcessedBy(SessionBean.getUserId());
        System.out.println("processor " + isivEntity.getProcessedBy());
        allIsivInfoList = transferBeanLocal.getIsivListsByParameter(columnNameResolver, isivEntity, isivEntity.getColumnValue());
        recerateSivSerachModel();

    }

    private void recerateSivSerachModel() {

        mmsIsivSearchInfoDataModel = null;
        mmsIsivSearchInfoDataModel = new ListDataModel(new ArrayList<>(allIsivInfoList));
    }
    /*This method is used to on Row Edit
     */

    public void onRowEdit(SelectEvent event) {
        isivEntity = (MmsIsiv) event.getObject();
        int size = isivEntity.getWfMmsProcessedList().size();
        if (workflow.isApproveStatus()) {

            if (isivEntity.getWfMmsProcessedList().get(size - 1).getDecision() == Constants.CHECK_APPROVE_VALUE || isivEntity.getWfMmsProcessedList().get(size - 1).getDecision() == Constants.APPROVE_VALUE) {
                setSelectedValue("Approve");
            } else if (isivEntity.getWfMmsProcessedList().get(size - 1).getDecision() == Constants.CHECK_REJECT_VALUE || isivEntity.getWfMmsProcessedList().get(size - 1).getDecision() == Constants.APPROVE_REJECT_VALUE) {
                setSelectedValue("Reject");
            }
            wfMmsProcessed.setProcessedOn(isivEntity.getWfMmsProcessedList().get(size - 1).getProcessedOn());
            wfMmsProcessed.setCommentGiven(isivEntity.getWfMmsProcessedList().get(size - 1).getCommentGiven());

        } else {
        }

        populateUI();

    }
    /*This method is used to populateUI
     */

    public void populateUI() {

        srEntity = isivEntity.getStoreReqId();
        fromStore = isivEntity.getFromStore();
        toStore = isivEntity.getToStore();
        hrDepartmentsEntity = isivEntity.getDepartment();
        serviceProviderEntity = isivEntity.getTransporter();
        srList = new ArrayList<>();
        srList.add(srEntity);
        renderPnlCreateISIV = true;
        renderpnlToSearchPage = true;
        if (workflow.isPrepareStatus()) {
            System.out.println("yes i'm preparer");
            wfMmsProcessed.setProcessedOn(isivEntity.getProcessedOn());
            System.out.println("date====" + isivEntity.getProcessedOn());
        }
        renderPnlManPage = false;
        activeIndex = "0";
        saveorUpdateBundle = "Update";
        createOrSearchBundle = "New";
        setCreateOrSearchBundle(createOrSearchBundle);
        setIcone("ui-icon-plus");
        setIsRenderedIconWorkflow(true);
        updateStatus = 1;
        saveorUpdateButtonHandler();
        recreateIsivDataModel();
        recreateWfDataModel();
    }

    public void recreateWfDataModel() {
        WorkflowDataModel = null;
        for (int i = 0; i < isivEntity.getWfMmsProcessedList().size(); i++) {
            if (isivEntity.getWfMmsProcessedList().get(i).getDecision() != null) {
                if (isivEntity.getWfMmsProcessedList().get(i).getDecision() == 1 || isivEntity.getWfMmsProcessedList().get(i).getDecision() == 3) {
                    isivEntity.getWfMmsProcessedList().get(i).setWfDecison("Approved");
                } else if (isivEntity.getWfMmsProcessedList().get(i).getDecision() == 2 || isivEntity.getWfMmsProcessedList().get(i).getDecision() == 4) {
                    isivEntity.getWfMmsProcessedList().get(i).setWfDecison("Rejected");
                }
            } else {
                isivEntity.getWfMmsProcessedList().get(i).setWfDecison("Requested");
            }

        }
        WorkflowDataModel = new ListDataModel(isivEntity.getWfMmsProcessedList());
    }
    /*This method is used to saveor Update Button Handler
     */

    public void saveorUpdateButtonHandler() {
        if (workflow.isPrepareStatus()) {
            if (isivEntity.getStatusWf().equals(Constants.APPROVE_VALUE) || isivEntity.getStatusWf().equals(Constants.CHECK_APPROVE_VALUE)) {
                disableButtonUpdate = true;
            }
        } else if (workflow.isCheckStatus() || workflow.isApproveStatus()) {
            if (isivEntity.getStatusWf().equals(Constants.APPROVE_VALUE)) {
                disableButtonUpdate = true;
            }
        }

    }
    /*This method is used to handle Select Department
     */

    public void handleSelectDepartment(ValueChangeEvent event) {

        if (null != event.getNewValue().toString()) {
            int depIds = Integer.valueOf(event.getNewValue().toString());
            hrDepartmentsEntity.setDepId(depIds);
            isivEntity.setDepartment(hrDepartmentsEntity);
        }
    }
    /*This method is used to handle Select Store Name ISIV
     */

    public void handleSelectStoreNameISIV(ValueChangeEvent event) {
        if (null != event.getNewValue()) {

            storeEntity.setStoreId(Integer.parseInt(event.getNewValue().toString()));

        }

    }
     //</editor-fold>
}
