package et.gov.eep.mms.controller;

//<editor-fold defaultstate="collapsed" desc="Imports">
import et.gov.eep.commonApplications.businessLogic.WfMmsProcessedBeanLocal;
import et.gov.eep.commonApplications.entity.ColumnNameResolver;
import et.gov.eep.commonApplications.entity.WfMmsProcessed;
import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.fcms.businessLogic.admin.FmsAccountingPeriodBeanLocal;
import et.gov.eep.fcms.entity.admin.FmsAccountingPeriod;
import et.gov.eep.hrms.entity.committee.HrCommitteeMembers;
import et.gov.eep.hrms.entity.committee.HrCommittees;
import et.gov.eep.hrms.entity.employee.HrEmployees;
import et.gov.eep.hrms.integration.HREmployeesBeanLocal;
import et.gov.eep.hrms.integration.HrCommitteeMembersIntegrationBeanLocal;
import et.gov.eep.hrms.integration.HrCommitteesIntegrationBeanLocal;
import et.gov.eep.mms.businessLogic.MmsInventoryCountingBeanLocal;
import et.gov.eep.mms.businessLogic.MmsItemRegisrtationBeanLocal;
import et.gov.eep.mms.businessLogic.MmsLuWarehouseBeanLocal;
import et.gov.eep.mms.businessLogic.MmsStoreInformationBeanLocal;
import et.gov.eep.mms.entity.MmsInventoryCountDetail;
import et.gov.eep.mms.entity.MmsInventoryCounting;
import et.gov.eep.mms.entity.MmsStoreInformation;
import et.gov.eep.mms.entity.MmsItemRegistration;
import et.gov.eep.mms.entity.MmsLuWareHouse;
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
import javax.faces.model.SelectItem;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import javax.xml.soap.Detail;
import org.primefaces.event.SelectEvent;
import securityBean.Constants;
import securityBean.SessionBean;
import securityBean.WorkFlow;
import webService.AAA;
import webService.EventEntry;
import webService.IAdministration;
//</editor-fold>

/**
 *
 * @author kimmyo
 */
@Named(value = "inventoryCountManagedBean")
@ViewScoped
public class InventoryCountController implements Serializable {

    // <editor-fold defaultstate="collapsed" desc="Inject EJB's ">
    @EJB
    WfMmsProcessedBeanLocal wfMmsProcessedBeanLocal;
    @EJB
    private MmsInventoryCountingBeanLocal invCountInterface;
    @EJB
    private MmsStoreInformationBeanLocal storeInfoInterface;
    @EJB
    HREmployeesBeanLocal hrEmployeeInterface;
    @EJB
    private MmsItemRegisrtationBeanLocal itemRegInterface;
    @EJB
    private HrCommitteesIntegrationBeanLocal committesInterface;
    @EJB
    private HrCommitteeMembersIntegrationBeanLocal committeMembersInterface;
    @EJB
    private FmsAccountingPeriodBeanLocal fmsAccountingPeriodBeanLocal;
    @EJB
    MmsLuWarehouseBeanLocal MmsLuWarehouseBeanLocal;
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc=" Inject Entitys ">
    @Inject
    WfMmsProcessed wfMmsProcessed;
    @Inject
    HrCommittees hrcommittesEntity;
    @Inject
    HrCommitteeMembers hrcommitteeMembersEntity;
    @Inject
    HrEmployees hrEmployeesEntity;
    @Inject
    ColumnNameResolver columnNameResolver;
    @Inject
    FmsAccountingPeriod accountingPeriodEntity;
    @Inject
    MmsInventoryCountDetail inventorydetail;
    @Inject
    MmsInventoryCounting inventoryCountEntity;
    @Inject
    MmsStoreInformation storeInfoEntity;
    @Inject
    private MmsItemRegistration itemRegEntity;
    private MmsInventoryCounting selectedInventorycount;
    private MmsInventoryCountDetail selectedInventorycountDetail;
    @Inject
    MmsLuWareHouse mmsLuWareHouse;
    @Inject
    SessionBean SessionBean;
    @Inject
    WorkFlow workflow;
/// </editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Declare lists and Models">
    private List<MmsStoreInformation> storeDetailList = new ArrayList<>();
    private List<MmsItemRegistration> materialCodeLists = new ArrayList<>();
    private List<HrCommittees> committeeList = new ArrayList<>();
    private List<HrCommitteeMembers> committeeMembersList = new ArrayList<>();
    private List<HrCommitteeMembers> selectedMembersList = new ArrayList<>();
    private List<MmsItemRegistration> materialNameList = new ArrayList<>();
    private List<MmsInventoryCounting> lists;
    private List<MmsInventoryCounting> mmsInventoryCountingList;
    private List<MmsLuWareHouse> mmsLuWareHousesList = new ArrayList<>();
    private List<HrEmployees> employeeInformations = new ArrayList<>();
    DataModel<WfMmsProcessed> WorkflowDataModel = new ListDataModel<>();
    List<WfMmsProcessed> WfList;
    private DataModel<MmsInventoryCountDetail> inventoryCountAddDetailDataModel;
    private DataModel<MmsInventoryCounting> mmsInventorySearchInfoDataModel;
    List<ColumnNameResolver> ColumnNameResolverList = new ArrayList<>();
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Declare fields">
    private boolean renderDecision = false;
    private boolean renderPnlCreateInvCountsheet = false;
    private boolean renderPnlManPage = true;
    private boolean disableSearchItem = true;
    private boolean renderpnlToSearchPage;
    private boolean isRenderedIconNitification = true;
    private String createOrSearchBundle = "New";
    private String icone = "ui-icon-plus";
    private String userName;
    private boolean isRenderedIconWorkflow = false;
    private String addOrModifyBundle = "Add";
    private String saveorUpdateBundle = "Save";
    private String[] selectedMembers;
    int updateStatus = 0;
    private String members = "";
    private String activeperiod = "";
    private String newInventoryCountNo;
    private String lastInvCountNum = "0";
    private String selectedValue;
//</editor-fold>

    // <editor-fold defaultstate="collapsed" desc=" Default Constructor ">
    public InventoryCountController() {
        //default constructor
    }

    public String getSelectedValue() {
        return selectedValue;
    }

// </editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Getter and Setter of Variables">
    public void setSelectedValue(String selectedValue) {
        this.selectedValue = selectedValue;
    }

    public boolean isIsRenderedIconNitification() {
        return isRenderedIconNitification;
    }

    public boolean isIsRenderedIconWorkflow() {
        return isRenderedIconWorkflow;
    }

    public void setIsRenderedIconWorkflow(boolean isRenderedIconWorkflow) {
        this.isRenderedIconWorkflow = isRenderedIconWorkflow;
    }

    /**
     *
     * @return
     */
    public String getSaveorUpdateBundle() {
        return saveorUpdateBundle;
    }

    /**
     *
     * @param saveorUpdateBundle
     */
    public void setSaveorUpdateBundle(String saveorUpdateBundle) {
        this.saveorUpdateBundle = saveorUpdateBundle;
    }

    public String[] getSelectedMembers() {
        return selectedMembers;
    }

    public void setSelectedMembers(String[] selectedMembers) {
        this.selectedMembers = selectedMembers;
    }

    public boolean isDisableSearchItem() {
        return disableSearchItem;
    }

    public void setDisableSearchItem(boolean disableSearchItem) {
        this.disableSearchItem = disableSearchItem;
    }

    public String getActiveperiod() {
        return activeperiod;
    }

    public void setActiveperiod(String activeperiod) {
        this.activeperiod = activeperiod;
    }

    public boolean isRenderPnlCreateInvCount() {
        return renderPnlCreateInvCountsheet;
    }

    public void setRenderPnlCreateInvCount(boolean renderPnlCreateInvCountsheet) {
        this.renderPnlCreateInvCountsheet = renderPnlCreateInvCountsheet;
    }

    public boolean isRenderPnlManPage() {
        return renderPnlManPage;
    }

    public void setRenderPnlManPage(boolean renderPnlManPage) {
        this.renderPnlManPage = renderPnlManPage;
    }

    public boolean isRenderpnlToSearchPage() {
        return renderpnlToSearchPage;
    }

    public void setRenderpnlToSearchPage(boolean renderpnlToSearchPage) {
        this.renderpnlToSearchPage = renderpnlToSearchPage;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

//</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Getter and setter of Lists and models">
    public List<MmsInventoryCounting> getMmsInventoryCountingList() {

        return mmsInventoryCountingList;
    }

    public void setMmsInventoryCountingList(List<MmsInventoryCounting> mmsInventoryCountingList) {
        this.mmsInventoryCountingList = mmsInventoryCountingList;
    }

    public void setIsRenderedIconNitification(boolean isRenderedIconNitification) {
        this.isRenderedIconNitification = isRenderedIconNitification;
    }

    public boolean isRenderDecision() {
        return renderDecision;
    }

    public void setRenderDecision(boolean renderDecision) {
        this.renderDecision = renderDecision;
    }

    public DataModel<WfMmsProcessed> getWorkflowDataModel() {
        if (WorkflowDataModel == null) {
            WorkflowDataModel = new ListDataModel<>();
        }
        return WorkflowDataModel;
    }

    public List<ColumnNameResolver> getColumnNameResolverList() {
        return ColumnNameResolverList;
    }

    public void setColumnNameResolverList(List<ColumnNameResolver> ColumnNameResolverList) {
        this.ColumnNameResolverList = ColumnNameResolverList;
    }

    public ColumnNameResolver getColumnNameResolver() {
        if (columnNameResolver == null) {
            columnNameResolver = new ColumnNameResolver();
        }
        return columnNameResolver;
    }

    public void setColumnNameResolver(ColumnNameResolver columnNameResolver) {
        this.columnNameResolver = columnNameResolver;
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

    /**
     *
     * @return
     */
    public List<MmsStoreInformation> getStoreDetailList() {
        return storeDetailList;
    }

    /**
     *
     * @param storeDetailList
     */
    public void setStoreDetailList(List<MmsStoreInformation> storeDetailList) {
        this.storeDetailList = storeDetailList;
    }

    public DataModel<MmsInventoryCountDetail> getInventoryCountAddDetailDataModel() {
        if (inventoryCountAddDetailDataModel == null) {
            this.inventoryCountAddDetailDataModel = new ListDataModel<>();
        }
        return inventoryCountAddDetailDataModel;
    }

    public void setInventoryCountAddDetailDataModel(DataModel<MmsInventoryCountDetail> inventoryCountAddDetailDataModel) {
        this.inventoryCountAddDetailDataModel = inventoryCountAddDetailDataModel;
    }

    public List<HrCommitteeMembers> getSelectedMembersList() {
        if (selectedMembersList == null) {
            selectedMembersList = new ArrayList<>();

        }
        return selectedMembersList;
    }

    public void setSelectedMembersList(List<HrCommitteeMembers> selectedMembersList) {
        this.selectedMembersList = selectedMembersList;
    }

    public DataModel<MmsInventoryCounting> getMmsInventorySearchInfoDataModel() {
        if (mmsInventorySearchInfoDataModel == null) {
            mmsInventorySearchInfoDataModel = new ListDataModel<>();
        }
        return mmsInventorySearchInfoDataModel;
    }

    public void setMmsInventorySearchInfoDataModel(DataModel<MmsInventoryCounting> mmsInventorySearchInfoDataModel) {
        this.mmsInventorySearchInfoDataModel = mmsInventorySearchInfoDataModel;
    }

    public List<MmsInventoryCounting> getLists() {

        if (lists == null) {
            lists = new ArrayList<>();
        }
        return lists;
    }

    /**
     *
     * @param lists
     */
    public void setLists(List<MmsInventoryCounting> lists) {
        this.lists = lists;
    }

    public MmsLuWareHouse getMmsLuWareHouse() {
        return mmsLuWareHouse;
    }

    public void setMmsLuWareHouse(MmsLuWareHouse mmsLuWareHouse) {
        this.mmsLuWareHouse = mmsLuWareHouse;
    }

    public List<MmsLuWareHouse> getMmsLuWareHousesList() {
        return mmsLuWareHousesList;
    }

    public void setMmsLuWareHousesList(List<MmsLuWareHouse> mmsLuWareHousesList) {
        this.mmsLuWareHousesList = mmsLuWareHousesList;
    }

//</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Getter and setter of objects">
    public WfMmsProcessed getWfMmsProcessed() {
        if (wfMmsProcessed == null) {
            wfMmsProcessed = new WfMmsProcessed();
        }
        return wfMmsProcessed;
    }

    public void setWfMmsProcessed(WfMmsProcessed wfMmsProcessed) {
        this.wfMmsProcessed = wfMmsProcessed;
    }

    public HrCommittees getHrcommittesEntity() {
        if (hrcommittesEntity == null) {
            hrcommittesEntity = new HrCommittees();
        }
        return hrcommittesEntity;
    }

    public void setHrcommittesEntity(HrCommittees hrcommittesEntity) {
        this.hrcommittesEntity = hrcommittesEntity;
    }

    public HrCommitteeMembers getHrcommitteeMembersEntity() {
        if (hrcommitteeMembersEntity == null) {
            hrcommitteeMembersEntity = new HrCommitteeMembers();
        }
        return hrcommitteeMembersEntity;
    }

    public void setHrcommitteeMembersEntity(HrCommitteeMembers hrcommitteeMembersEntity) {
        this.hrcommitteeMembersEntity = hrcommitteeMembersEntity;
    }

    public FmsAccountingPeriod getAccountingPeriodEntity() {
        if (accountingPeriodEntity == null) {
            accountingPeriodEntity = new FmsAccountingPeriod();
        }
        return accountingPeriodEntity;
    }

    public void setAccountingPeriodEntity(FmsAccountingPeriod accountingPeriodEntity) {
        this.accountingPeriodEntity = accountingPeriodEntity;
    }

    public MmsInventoryCountDetail getInventorydetail() {
        if (inventorydetail == null) {
            inventorydetail = new MmsInventoryCountDetail();
        }
        return inventorydetail;
    }

    public void setInventorydetail(MmsInventoryCountDetail inventorydetail) {
        this.inventorydetail = inventorydetail;
    }

    /**
     *
     * @return
     */
    public MmsInventoryCounting getInventoryCountEntity() {
        if (inventoryCountEntity == null) {
            inventoryCountEntity = new MmsInventoryCounting();
        }
        return inventoryCountEntity;
    }

    /**
     *
     * @param inventoryCountEntity
     */
    public void setInventoryCountEntity(MmsInventoryCounting inventoryCountEntity) {
        this.inventoryCountEntity = inventoryCountEntity;
    }

    /**
     *
     * @return
     */
    public MmsStoreInformation getStoreInfoEntity() {
        if (storeInfoEntity == null) {
            this.storeInfoEntity = new MmsStoreInformation();
        }
        return storeInfoEntity;
    }

    /**
     *
     * @param storeInfoEntity
     */
    public void setStoreInfoEntity(MmsStoreInformation storeInfoEntity) {
        this.storeInfoEntity = storeInfoEntity;
    }

    /**
     *
     * @return
     */
    public HrEmployees getHrEmployeesEntity() {
        if (hrEmployeesEntity == null) {
            this.hrEmployeesEntity = new HrEmployees();
        }
        return hrEmployeesEntity;
    }

    /**
     *
     * @param hrEmployeesEntity
     */
    public void setHrEmployeesEntity(HrEmployees hrEmployeesEntity) {
        this.hrEmployeesEntity = hrEmployeesEntity;
    }

    public MmsInventoryCounting getSelectedInventorycount() {
        return selectedInventorycount;
    }

    public void setSelectedInventorycount(MmsInventoryCounting selectedInventorycount) {
        this.selectedInventorycount = selectedInventorycount;
    }

    public MmsInventoryCountDetail getSelectedInventorycountDetail() {
        return selectedInventorycountDetail;
    }

    public void setSelectedInventorycountDetail(MmsInventoryCountDetail selectedInventorycountDetail) {
        this.selectedInventorycountDetail = selectedInventorycountDetail;
    }

    /**
     * @return the itemRegEntity
     */
    public MmsItemRegistration getItemRegEntity() {
        if (itemRegEntity == null) {
            this.itemRegEntity = new MmsItemRegistration();
        }
        return itemRegEntity;
    }

    /**
     * @param itemRegEntity the itemRegEntity to set
     */
    public void setItemRegEntity(MmsItemRegistration itemRegEntity) {
        this.itemRegEntity = itemRegEntity;
    }

//</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="RecreateModel and Update">
    public void updateinventoryCountDetail() {

        inventorydetail = getInventoryCountAddDetailDataModel().getRowData();

    }

    private void recreateModelDetail() {
        inventoryCountAddDetailDataModel = null;
        inventoryCountAddDetailDataModel = new ListDataModel(new ArrayList<>(inventoryCountEntity.getMmsInventoryCountDetailList()));
    }

    /**
     *
     */
    public void inventoryCountingDetail() {
        inventorydetail = getInventoryCountAddDetailDataModel().getRowData();

    }

    /**
     *
     * @param firstName
     * @return
     */
    public List<HrEmployees> searchEmployeeInformation(String firstName) {

        hrEmployeesEntity.setFirstName(firstName);

        employeeInformations = hrEmployeeInterface.searchEmployeeInfo(hrEmployeesEntity);
        return employeeInformations;
    }

    public List<HrCommittees> getCommittees() {
        committeeList = committesInterface.findAll();
        return committeeList;
    }

    public void handleSelectCommittee(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            hrcommittesEntity.setCommitteeName(event.getNewValue().toString());
            hrcommittesEntity = committesInterface.getCommittee(hrcommittesEntity);
            inventoryCountEntity.setCommitteeId(hrcommittesEntity);
        }
    }

    public List<HrCommitteeMembers> getCommitteeMembers() {
        committeeMembersList = committeMembersInterface.getCommitteeMembers(hrcommittesEntity);

        return committeeMembersList;
    }

    /**
     *
     * @param invId
     * @return
     */
    public List<MmsInventoryCounting> searchInvIdInformation(String invId) {
        ArrayList<MmsInventoryCounting> invInformations;
        invInformations = new ArrayList<>();
        int id = Integer.parseInt(invId);
        inventoryCountEntity.setInventoryCountId(id);
        invInformations = invCountInterface.searchInventoryInformation(inventoryCountEntity);
        return invInformations;
    }

    public List<MmsItemRegistration> itemInformationList(String itemName) {
        itemRegEntity.setMatName(itemName);
        itemRegEntity.setStoreId(storeInfoEntity);
        materialNameList = itemRegInterface.searchMaterialInfoByStoreAndMatName(itemRegEntity);
        return materialNameList;
    }

    public void getMmsItemInformation(SelectEvent event) {
        itemRegEntity.setMatName(event.getObject().toString());
        itemRegEntity = itemRegInterface.getByMaterialName(itemRegEntity);

        inventorydetail.setItemCode(itemRegEntity);

    }

    /**
     *
     * @param event
     */
    List<MmsInventoryCounting> allInventorycountinfoList;

    public void searchInventoryByParameter() {
        System.out.println("workflow.isApproveStatus()==" + workflow.isApproveStatus());
        System.out.println("workflow.isCheckStatus()==" + workflow.isCheckStatus());
        if (workflow.isApproveStatus() || workflow.isCheckStatus()) {
            System.out.println("inside if");
            if (workflow.isApproveStatus()) {
                allInventorycountinfoList = invCountInterface.getCountingListsByParameterForCheckOrApprove(inventoryCountEntity,Constants.APPROVE_VALUE);
                recerateInventorySerachModel();
            } else if (workflow.isCheckStatus()) {
                allInventorycountinfoList = invCountInterface.getCountingListsByParameterForCheckOrApprove(inventoryCountEntity,Constants.CHECK_APPROVE_VALUE);
                recerateInventorySerachModel();
            }
//            
            
        } else {
            allInventorycountinfoList = invCountInterface.getCountingListsByParameter(inventoryCountEntity);
            recerateInventorySerachModel();
//            inventoryCountEntity.setProcessedBy(SessionBean.getUserId());
//            if (null != storeInfoEntity.getStoreId() && inventoryCountEntity.getInventoryCountNo().isEmpty() && inventoryCountEntity.getBudgetYear().isEmpty()) {
//                
//                inventoryCountEntity.setWorkUnit(storeInfoEntity);
//                allInventorycountinfoList = invCountInterface.getCountingListsByParameter(inventoryCountEntity);
//                recerateInventorySerachModel();
//                
//            } else if (storeInfoEntity.getStoreId() != null && !inventoryCountEntity.getInventoryCountNo().isEmpty() && inventoryCountEntity.getBudgetYear().isEmpty()) {
//                
//                inventoryCountEntity.setWorkUnit(storeInfoEntity);
//                allInventorycountinfoList = invCountInterface.getCountingListsByParameter(inventoryCountEntity);
//                recerateInventorySerachModel();
//            } else if (null != storeInfoEntity.getStoreId() && inventoryCountEntity.getInventoryCountNo().isEmpty() && !inventoryCountEntity.getBudgetYear().isEmpty()) {
//                
//                inventoryCountEntity.setWorkUnit(storeInfoEntity);
//                allInventorycountinfoList = invCountInterface.searchByStoreAndBudgetYearAndProcessedBy(inventoryCountEntity);
//                recerateInventorySerachModel();
//            } else if (storeInfoEntity.getStoreId() != null && !inventoryCountEntity.getInventoryCountNo().isEmpty() && !inventoryCountEntity.getBudgetYear().isEmpty()) {
//                
//                inventoryCountEntity.setWorkUnit(storeInfoEntity);
//                allInventorycountinfoList = invCountInterface.getCountingListsByParameter(inventoryCountEntity);
//                recerateInventorySerachModel();
//            } else {
            JsfUtil.addErrorMessage("Store Must Be Selected");
        }
    

}

private void recerateInventorySerachModel() {
        mmsInventorySearchInfoDataModel = null;
        mmsInventorySearchInfoDataModel = new ListDataModel(new ArrayList<>(allInventorycountinfoList));
    }

    public void getMmsInvCountsheetInfo(ValueChangeEvent event) {

        if (null != event.getNewValue()) {
            String selectInvId = event.getNewValue().toString();
            int id = Integer.parseInt(selectInvId);
            inventoryCountEntity.setInventoryCountId(id);
            inventoryCountEntity = invCountInterface.getMmsinvCountInformation(inventoryCountEntity);
            inventoryCountEntity.setApprovedBy(inventoryCountEntity.getApprovedBy());

            storeInfoEntity.setStoreName(inventoryCountEntity.getWorkUnit().getStoreName());
            //   storeInfoEntity.setStoreLocation(inventoryCountEntity.getLocation());
            storeInfoEntity.setRegion(inventoryCountEntity.getWorkUnit().getRegion());
            updateStatus = 1;
            saveorUpdateBundle = "Update";
            recreateModelDetail();

        }

    }

    /**
     *
     * @param event
     */
    public void handleSelectHrCountedByInfo(SelectEvent event) {
        if (null != event.getObject().toString()) {
            String selectPapmssiv = event.getObject().toString();
            hrEmployeesEntity.setFirstName(selectPapmssiv);
            hrEmployeesEntity = hrEmployeeInterface.searchById(hrEmployeesEntity);

        }
    }

    public void handleselectOptions(ValueChangeEvent event) {
        String opt = event.getNewValue().toString();
        if (opt.equalsIgnoreCase("1")) {
            inventoryCountEntity.setCountType(1);
        } else {
            inventoryCountEntity.setCountType(2);

        }

    }

    /**
     *
     * @return
     */
//</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Clear pages and clearpopup">
    public String clearPage() {

        inventoryCountEntity = new MmsInventoryCounting();
        storeInfoEntity = new MmsStoreInformation();
        inventorydetail = new MmsInventoryCountDetail();
        inventoryCountAddDetailDataModel = null;
        hrEmployeesEntity = new HrEmployees();
        hrcommittesEntity = new HrCommittees();
        hrcommitteeMembersEntity = new HrCommitteeMembers();
        mmsInventorySearchInfoDataModel = new ListDataModel<>();
        wfMmsProcessed = null;
        setActiveperiod(null);
        setSelectedValue(null);

        updateStatus = 0;
        saveorUpdateBundle = "Save";

        return null;
    }

    private void clearPopUp() {

        itemRegEntity = null;
        inventorydetail = null;
    }

    /**
     *
     * @return
     */
    public String addinventoryCountDetail() {
        inventoryCountEntity.addToInventoryCountDetail(inventorydetail);
        recreateModelDetail();
        clearPopUp();
        return null;
    }

//</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Post Constract">
    @PostConstruct
        public void init() {
        ColumnNameResolverList = invCountInterface.getColumnNameList();
        if (workflow.isApproveStatus() || workflow.isCheckStatus()) {
            mmsInventoryCountingList = invCountInterface.getCountingListsByParameter(inventoryCountEntity);
            System.out.println("app or check");
            renderDecision = true;
            isRenderedIconNitification = true;

        } else if (workflow.isPrepareStatus()) {
            System.out.println("prep");
            renderDecision = false;

            mmsInventoryCountingList = invCountInterface.getCountingListsByParameter(inventoryCountEntity);
            if (mmsInventoryCountingList != null) {
                isRenderedIconNitification = mmsInventoryCountingList.size() > 0;
                wfMmsProcessed.setProcessedBy(SessionBean.getUserId());
                setUserName(SessionBean.getUserName());
                System.out.println("is App==" + workflow.isApproveStatus() + "is chech==" + workflow.isCheckStatus() + "is prepa==" + workflow.isPrepareStatus());

                wfMmsProcessed.setProcessedBy(SessionBean.getUserId());
                setUserName(SessionBean.getUserName());
                System.out.println("is App==" + workflow.isApproveStatus() + "is chech==" + workflow.isCheckStatus() + "is prepa==" + workflow.isPrepareStatus());
                wfMmsProcessed.setProcessedBy(SessionBean.getUserId());
            }

        }

    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Save and Update methods">
    public void saveInventoryCountSheet() {
        try {
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(SessionBean.getUserName(), "saveInventoryCountSheet", dataset)) {
                if (updateStatus == 0) {

                    try {
                        wfMmsProcessed.setProcessedBy(SessionBean.getUserId());
                        inventoryCountEntity.setProcessedOn(wfMmsProcessed.getProcessedOn());
                        inventoryCountEntity.setProcessedBy(wfMmsProcessed.getProcessedBy());
                        inventoryCountEntity.setInventoryCountNo(newInventoryCountNo);
                        inventoryCountEntity.setWorkUnit(storeInfoEntity);
                        inventoryCountEntity.setActiveAccountingPeriod(accountingPeriodEntity);
                        inventoryCountEntity.setBudgetYear(activeperiod);
                        commiteeMembers();
                        inventoryCountEntity.addInventoryCountIdToWorkflow(wfMmsProcessed);
                        inventoryCountEntity.setStatus(Constants.PREPARE_VALUE);
                        System.out.println("19");
                        invCountInterface.create(inventoryCountEntity);
                        JsfUtil.addSuccessMessage("inventory A New inventory Information is Saved ");
                        clearPage();
                    } catch (Exception ex) {
                        JsfUtil.addErrorMessage("Something Error Occured on Data created");

                    }
                } else if (updateStatus == 1 && workflow.isPrepareStatus()) {
                    try {

                        commiteeMembers();
                        inventoryCountEntity.setCommentGiven(wfMmsProcessed.getCommentGiven());
                        inventoryCountEntity.setProcessedOn(wfMmsProcessed.getProcessedOn());
                        invCountInterface.edit(inventoryCountEntity);
                        JsfUtil.addSuccessMessage("inventory Information is Updated");
                        clearPage();

                    } catch (Exception ex) {

                    }
                } else if (updateStatus == 1 && (workflow.isApproveStatus() || workflow.isCheckStatus())) {

                    if (selectedValue.equalsIgnoreCase("Approve") && workflow.isApproveStatus()) {
                        workflow.setUserStatus(Constants.APPROVE_VALUE);
                        inventoryCountEntity.setStatus(Constants.APPROVE_VALUE);
                        wfMmsProcessed.setDecision(Constants.APPROVE_VALUE);
                    } else if (selectedValue.equalsIgnoreCase("Approve") && workflow.isCheckStatus()) {
                        workflow.setUserStatus(Constants.CHECK_APPROVE_VALUE);
                        inventoryCountEntity.setStatus(Constants.CHECK_APPROVE_VALUE);
                        wfMmsProcessed.setDecision(Constants.CHECK_APPROVE_VALUE);
                    } else if (selectedValue.equalsIgnoreCase("Reject") && workflow.isApproveStatus()) {
                        workflow.setUserStatus(Constants.APPROVE_REJECT_VALUE);
                        inventoryCountEntity.setStatus(Constants.APPROVE_REJECT_VALUE);
                        wfMmsProcessed.setDecision(Constants.APPROVE_REJECT_VALUE);
                    } else if (selectedValue.equalsIgnoreCase("Reject") && workflow.isCheckStatus()) {
                        workflow.setUserStatus(Constants.CHECK_REJECT_VALUE);
                        inventoryCountEntity.setStatus(Constants.CHECK_REJECT_VALUE);
                        wfMmsProcessed.setDecision(Constants.CHECK_REJECT_VALUE);
                    }
                    wfMmsProcessed.setInventoryCountId(inventoryCountEntity);
                    wfMmsProcessedBeanLocal.create(wfMmsProcessed);
                    invCountInterface.edit(inventoryCountEntity);
                    mmsInventoryCountingList.remove(inventoryCountEntity);
                    JsfUtil.addSuccessMessage("inventory Information is Updated");
                    clearPage();
                }

            } else {
                JsfUtil.addFatalMessage("You don't have appropriate permission, please contact the system administrator. ");
                EventEntry eventEntry = new EventEntry();
                eventEntry.setSessionId(SessionBean.getSessionID());
                eventEntry.setUserId(SessionBean.getUserId());
                QName qualifiedName = new QName("", "project");
                JAXBElement

<String> test = new JAXBElement<String>(qualifiedName, String.class  

    , null, SessionBean.getUserName());
    eventEntry.setUserLogin (test);

    security.addEventLog (eventEntry, dataset);
}
} catch (Exception ex) {

        }

    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Handler">
    public void commiteeMembers() {
        for (String selectedMember : selectedMembers) {
            if (members.equals("")) {
                members = selectedMember;
            } else {
                members = members + "," + selectedMember;
            }
        }
        inventoryCountEntity.setCommitteMembers(members);
    }

    /**
     *
     * @return
     */
    public SelectItem[] getStoreNameList() {
        return JsfUtil.getSelectItems(storeInfoInterface.findAllStoreInfo(), true);
    }

    /**
     *
     * @param event
     */
    public void handleSelectStoreName(ValueChangeEvent event) {
        if (null != event.getNewValue()) {

            storeInfoEntity.setStoreName(event.getNewValue().toString());
            storeInfoEntity = storeInfoInterface.getPapmsStoreInformation(storeInfoEntity);
            setDisableSearchItem(false);

        }

    }

    public void changeEventColumnName(ValueChangeEvent changeEvent) {
        if (changeEvent.getNewValue() != null) {
            columnNameResolver.setCol_Name_FromTable(changeEvent.getNewValue().toString());
            columnNameResolver.setParsed_Col_Name(ColumnParser(columnNameResolver.getCol_Name_FromTable()));
            inventoryCountEntity.setColumnName(columnNameResolver.getCol_Name_FromTable());

        }

    }

    public String ColumnParser(String col_name) {
        String col = col_name.replace("_", " ");
        System.out.println("col==" + col);
        return col.toLowerCase() + ":";
    }

    public void handleSelectStoreNameForSearch(ValueChangeEvent event) {
        if (null != event.getNewValue()) {

            storeInfoEntity.setStoreId(Integer.parseInt(event.getNewValue().toString()));
            inventoryCountEntity.setWorkUnit(storeInfoEntity);
//

//
        }

    }

//to get material code from a list generated using find all;
    /**
     *
     * @param event
     */
    public void handleSelectMaterialName(ValueChangeEvent event) {
        if (null != event.getNewValue()) {
//
            for (MmsItemRegistration materialCodeList1 : materialCodeLists) {

                boolean a = materialCodeList1.getMatName().equals(String.valueOf(event.getNewValue()));
                if (materialCodeList1.getMatName().equals(String.valueOf(event.getNewValue()))) {

                    inventorydetail.setItemCode(materialCodeList1);
                    break;
                }
            }

        }

    }

    /**
     *
     * @return
     */
    /**
     *
     * @return
     */
    public List<MmsStoreInformation> getStoreList() {

        storeDetailList = storeInfoInterface.findAllStoreInfo();

        return storeDetailList;
    }

    /**
     *
     * @param event
     */
    public void handleSelectYearAndBudget(ValueChangeEvent event) {
        if (null != event.getNewValue()) {
            String selectYear = event.getNewValue().toString();
            inventoryCountEntity.setBudgetYear(selectYear);
            getInventoryIdList();

        }
    }

    /**
     *
     * @return
     */
    public List<MmsInventoryCounting> getInventoryIdList() {
        storeInfoEntity.getStoreId();
        inventoryCountEntity.getBudgetYear();
        try {
            inventoryCountEntity.setWorkUnit(storeInfoEntity);
            lists = invCountInterface.searchByStoreAndBudgetYear(inventoryCountEntity);
        } catch (Exception e) {
            return null;
        }
        return lists;
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Recreatemodel and update">
    //<editor-fold defaultstate="collapsed" desc="comment">
    public void createNewInvCountInfo() {
        renderpnlToSearchPage = false;
        if (createOrSearchBundle.equals("New")) {
            clearPage();
            saveorUpdateBundle = "Save";
            renderPnlCreateInvCountsheet = true;
            renderPnlManPage = false;
            createOrSearchBundle = "Search";
            setIcone("ui-icon-search");
            accountingPeriodEntity = fmsAccountingPeriodBeanLocal.getCurretActivePeriod();
            activeperiod = accountingPeriodEntity.getStartDate() + "-" + accountingPeriodEntity.getEndDate();
            //generateGatePassNo();
        } else if (createOrSearchBundle.equals("Search")) {
            renderPnlCreateInvCountsheet = false;
            renderPnlManPage = true;
            createOrSearchBundle = "New";
            setIcone("ui-icon-plus");
        }
    }

    public void goBackToSearchpageBtnAction() {
        renderpnlToSearchPage = false;
        renderPnlCreateInvCountsheet = false;
        renderPnlManPage = true;
    }

    public String generateInventoryNo() {
        if (updateStatus == 1) {
            newInventoryCountNo = inventoryCountEntity.getInventoryCountNo();
        } else {
            MmsInventoryCounting lastInvcountnumber = invCountInterface.getLastInventoryNo();
            if (lastInvcountnumber != null) {
                lastInvCountNum = lastInvcountnumber.getInventoryCountId().toString();
            }

            DateFormat f = new SimpleDateFormat("yyyy");
            Date now = new Date();

            int newPayment = 0;
            if (lastInvCountNum.equals("0")) {
                newPayment = 1;
                newInventoryCountNo = "InventoryNo-" + newPayment + "/" + f.format(now);
            } else {

                String[] lastInspNos = lastInvCountNum.split("-");
                String lastDatesPatern = lastInspNos[0];

                String[] lastDatesPaterns = lastDatesPatern.split("/");
                newPayment = Integer.parseInt(lastDatesPaterns[0]);
                newPayment = newPayment + 1;
                newInventoryCountNo = "InventoryNo-" + newPayment + "/" + f.format(now);
            }
        }
        return newInventoryCountNo;
    }

    public void onRowEdit(SelectEvent event) {
        inventoryCountEntity = (MmsInventoryCounting) event.getObject();
//
        populateUI();

    }

    public void onRowPopulate(SelectEvent event) {
        addOrModifyBundle = "Modify";
        inventorydetail = (MmsInventoryCountDetail) event.getObject();
        itemRegEntity = inventorydetail.getItemCode();
    }

    public void populateUI() {
        hrcommittesEntity = inventoryCountEntity.getCommitteeId();
        accountingPeriodEntity = inventoryCountEntity.getActiveAccountingPeriod();
        activeperiod = accountingPeriodEntity.getStartDate() + "-" + accountingPeriodEntity.getEndDate();
        wfMmsProcessed.setProcessedOn(inventoryCountEntity.getProcessedOn());
        storeInfoEntity = inventoryCountEntity.getWorkUnit();
        selectedMembers = inventoryCountEntity.getCommitteMembers().split(",");
        setIsRenderedIconWorkflow(true);
        renderPnlCreateInvCountsheet = true;
        renderpnlToSearchPage = true;
        renderPnlManPage = false;
        saveorUpdateBundle = "Update";
        updateStatus = 1;
        recreateWfDataModel();
        recreateModelDetail();
    }
//</editor-fold>

    public void recreateWfDataModel() {
        WorkflowDataModel = null;
        for (int i = 0; i < inventoryCountEntity.getWfMmsProcessedList().size(); i++) {
            if (inventoryCountEntity.getWfMmsProcessedList().get(i).getDecision() != null) {
                if (inventoryCountEntity.getWfMmsProcessedList().get(i).getDecision() == 1 || inventoryCountEntity.getWfMmsProcessedList().get(i).getDecision() == 3) {
                    inventoryCountEntity.getWfMmsProcessedList().get(i).setWfDecison("Approved");
                } else if (inventoryCountEntity.getWfMmsProcessedList().get(i).getDecision() == 2 || inventoryCountEntity.getWfMmsProcessedList().get(i).getDecision() == 4) {
                    inventoryCountEntity.getWfMmsProcessedList().get(i).setWfDecison("Rejected");
                }
            } else {
                inventoryCountEntity.getWfMmsProcessedList().get(i).setWfDecison("Requested");
            }

        }

        WorkflowDataModel = new ListDataModel(inventoryCountEntity.getWfMmsProcessedList());
    }
}
//</editor-fold>

