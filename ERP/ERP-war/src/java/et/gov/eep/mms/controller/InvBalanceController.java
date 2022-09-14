
package et.gov.eep.mms.controller;

//<editor-fold defaultstate="collapsed" desc="import">
import et.gov.eep.commonApplications.businessLogic.WfMmsProcessedBeanLocal;
import et.gov.eep.commonApplications.entity.ColumnNameResolver;
import et.gov.eep.commonApplications.entity.WfMmsProcessed;
import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.mms.businessLogic.InventoryCountingDetailBeanLocal;
import et.gov.eep.mms.businessLogic.MmsBinCardBeanLocal;
import et.gov.eep.mms.businessLogic.MmsInventoryBalancingBeanLocal;
import et.gov.eep.mms.businessLogic.MmsInventoryCountingBeanLocal;
import et.gov.eep.mms.businessLogic.MmsStoreInformationBeanLocal;
import et.gov.eep.mms.entity.MmsBinCard;
import et.gov.eep.mms.entity.MmsInventoryBalanceSheet;
import et.gov.eep.mms.entity.MmsInventoryCountDetail;
import et.gov.eep.mms.entity.MmsInventoryCounting;
import et.gov.eep.mms.entity.MmsItemRegistration;
import et.gov.eep.mms.entity.MmsStoreInformation;
import java.io.Serializable;
import java.util.ArrayList;
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
 * @author sadik
 */
@Named(value = "invBalanceController")
@ViewScoped
public class InvBalanceController implements Serializable {
    
    //<editor-fold defaultstate="collapsed" desc="Inject Ejbs">
    @EJB
            WfMmsProcessedBeanLocal wfMmsProcessedBeanLocal;
    @EJB
    private InventoryCountingDetailBeanLocal invCountDetInterface;
    @EJB
    private MmsInventoryBalancingBeanLocal invBalanceInterface;
    @EJB
    private MmsInventoryCountingBeanLocal invCountInterface;
    @EJB
    private MmsStoreInformationBeanLocal storeInterface;
  
    @Inject
    SessionBean SessionBean;
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Inject Entities">
    private MmsBinCardBeanLocal mmsBinCardBeanLocal;
    @Inject
            MmsInventoryBalanceSheet invBalanceEntity;
    @Inject
            MmsInventoryBalanceSheet invBalanceEntityTemp;
    @Inject
            MmsInventoryCounting inventoryCountEntity;
    @Inject
    private MmsStoreInformation storeInfoEntity;
    @Inject
    private MmsBinCard mmsBinCard;
    @Inject
    private MmsItemRegistration itemRegistrationEntity;
    @Inject
            WfMmsProcessed wfMmsProcessed;
    @Inject
            SessionBean sessionBean;
    @Inject
            WorkFlow workflow;
     @Inject
    ColumnNameResolver columnNameResolver;
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Declare Lists and model">
    private List<MmsInventoryBalanceSheet> mmsIbsList;
    DataModel<WfMmsProcessed> WorkflowDataModel = new ListDataModel<>();
    List<WfMmsProcessed> WfList;
    DataModel<MmsInventoryBalanceSheet> InvBalanceSearchDataModel;
    DataModel<MmsInventoryCountDetail> inventryConting;
    private DataModel<MmsInventoryCounting> mmsInventorySearchInfoDataModel;
    private List<MmsInventoryCounting> approvedInvNoList;
    List<ColumnNameResolver> ColumnNameResolverList = new ArrayList<>();
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Declare fields">
    private String saveorUpdateBundle = "Save";
    int updateStatus = 0;
    private String createOrSearchBundle = "New";
    private boolean renderPnlCreateInvBalanceSheet = false;
    private boolean isRenderedIconNitification = true;
    private boolean renderDecision = false;
    private boolean renderPnlManPage = true;
    private boolean renderpnlToSearchPage;
    private String userName;
    private String icone = "ui-icon-plus";
    private String selectedValue;
//</editor-fold>
   
    //<editor-fold defaultstate="collapsed" desc="Getter and setter of List and model">
    public void initialization() {
        inventryConting = new ListDataModel(new ArrayList<>(invCountDetInterface.findAll()));
    }
    private List<MmsInventoryBalanceSheet> BalanceSheetList;
    public List<MmsInventoryBalanceSheet> getMmsIbsList() {
        if (mmsIbsList == null) {
            mmsIbsList = new ArrayList<>();
        }
        return mmsIbsList;
    }
    
    public void setMmsIbsList(List<MmsInventoryBalanceSheet> mmsIbsList) {
        this.mmsIbsList = mmsIbsList;
    }
     public DataModel<WfMmsProcessed> getWorkflowDataModel() {
        return WorkflowDataModel;
    }

    public List<ColumnNameResolver> getColumnNameResolverList() {
        return ColumnNameResolverList;
    }

    public void setColumnNameResolverList(List<ColumnNameResolver> ColumnNameResolverList) {
        this.ColumnNameResolverList = ColumnNameResolverList;
    }
    
    public void setWorkflowDataModel(DataModel<WfMmsProcessed> WorkflowDataModel) {
        this.WorkflowDataModel = WorkflowDataModel;
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
    public DataModel<MmsInventoryBalanceSheet> getInvBalanceSearchDataModel() {
        if (InvBalanceSearchDataModel == null) {
            InvBalanceSearchDataModel = new ListDataModel<>();
        }
        return InvBalanceSearchDataModel;
    }
    
    /**
     *
     * @param InvBalanceSearchDataModel
     */
    public void setInvBalanceSearchDataModel(DataModel<MmsInventoryBalanceSheet> InvBalanceSearchDataModel) {
        this.InvBalanceSearchDataModel = InvBalanceSearchDataModel;
    }

    public DataModel<MmsInventoryCountDetail> getInventryConting() {
        return inventryConting;
    }

    public void setInventryConting(DataModel<MmsInventoryCountDetail> inventryConting) {
        this.inventryConting = inventryConting;
    }
    
    
    
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Getter and setter of variables">
    public String getSelectedValue() {
        return selectedValue;
    }
    
    public void setSelectedValue(String selectedValue) {
        this.selectedValue = selectedValue;
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
      public boolean isRenderDecision() {
        return renderDecision;
    }
    
    public void setRenderDecision(boolean renderDecision) {
        this.renderDecision = renderDecision;
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
    
    public String getUserName() {
        return userName;
    }
    
    public void setUserName(String userName) {
        this.userName = userName;
    }
    
    
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Getter and setter of object">
    public WfMmsProcessed getWfMmsProcessed() {
        if (wfMmsProcessed == null) {
            wfMmsProcessed = new WfMmsProcessed();
        }
        return wfMmsProcessed;
    }
    
    public void setWfMmsProcessed(WfMmsProcessed wfMmsProcessed) {
        this.wfMmsProcessed = wfMmsProcessed;
    }
     /**
     *
     * @return
     */
    public MmsInventoryBalanceSheet getInvBalanceEntity() {
        if (invBalanceEntity == null) {
            invBalanceEntity = new MmsInventoryBalanceSheet();
        }
        return invBalanceEntity;
    }
    
    /**
     *
     * @param invBalanceEntity
     */
    public void setInvBalanceEntity(MmsInventoryBalanceSheet invBalanceEntity) {
        this.invBalanceEntity = invBalanceEntity;
    }
    
    public MmsInventoryBalanceSheet getInvBalanceEntityTemp() {
        if (invBalanceEntityTemp == null) {
            invBalanceEntityTemp = new MmsInventoryBalanceSheet();
        }
        return invBalanceEntityTemp;
    }
    
    public void setInvBalanceEntityTemp(MmsInventoryBalanceSheet invBalanceEntityTemp) {
        this.invBalanceEntityTemp = invBalanceEntityTemp;
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
    
    public MmsBinCard getMmsBinCard() {
        if (mmsBinCard == null) {
            mmsBinCard = new MmsBinCard();
        }
        return mmsBinCard;
    }
    
    public void setMmsBinCard(MmsBinCard mmsBinCard) {
        this.mmsBinCard = mmsBinCard;
    }
    
    public MmsItemRegistration getItemRegistrationEntity() {
        if (itemRegistrationEntity == null) {
            itemRegistrationEntity = new MmsItemRegistration();
        }
        return itemRegistrationEntity;
    }
    
    public void setItemRegistrationEntity(MmsItemRegistration itemRegistrationEntity) {
        this.itemRegistrationEntity = itemRegistrationEntity;
    }
    
    
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Row selection & Update">
    public void onSelectIbsRequest(ValueChangeEvent event) {
        try {
            if (null != event.getNewValue()) {
                invBalanceEntity = (MmsInventoryBalanceSheet) event.getNewValue();
                populateUI();
            }
        } catch (Exception e) {
            //e.printStackTrace();
            JsfUtil.addFatalMessage("Faile to process! Try Again Reloading the Page");
        }
    }
    
    /**
     *
     * @return
     */
    ArrayList<MmsBinCard> bincardList = new ArrayList<>();
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="@Post Constract">
    @PostConstruct
    public void init() {
          ColumnNameResolverList = invBalanceInterface.getColumnNameList();
        if (workflow.isApproveStatus() || workflow.isCheckStatus()) {
            System.out.println("app or check");
            mmsIbsList = invBalanceInterface.findInventoryBalanceSheetListByWfStatus(Constants.PREPARE_VALUE);
            renderDecision = true;
            isRenderedIconNitification = true;
        } else if (workflow.isPrepareStatus()) {
            System.out.println("prep");
            approvedInvNoList = invCountInterface.findInventoryCountingsListByWfStatus(Constants.APPROVE_VALUE);
            mmsIbsList = invBalanceInterface.findInventoryBalanceSheetListByWfStatus(Constants.APPROVE_REJECT_VALUE);
            renderDecision = false;
            if (mmsIbsList != null) {
                isRenderedIconNitification = mmsIbsList.size() > 0;
            }
            
        }
        wfMmsProcessed.setProcessedBy(sessionBean.getUserId());
        setUserName(sessionBean.getUserName());
        System.out.println("is App==" + workflow.isApproveStatus() + "is chech==" + workflow.isCheckStatus() + "is prepa==" + workflow.isPrepareStatus());
       
         wfMmsProcessed.setProcessedBy(SessionBean.getUserId());
        setUserName(SessionBean.getUserName());
        System.out.println("is App==" + workflow.isApproveStatus() + "is chech==" + workflow.isCheckStatus() + "is prepa==" + workflow.isPrepareStatus());
    }
    
    public void WfSave() {
        wfMmsProcessed.setIbsId(invBalanceEntity);
        wfMmsProcessedBeanLocal.create(wfMmsProcessed);
       
    }
    
    
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Save and Update methods">
    public void saveInventoryBalanceSheet() {
        try {
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(sessionBean.getUserName(), "saveInventoryBalanceSheet", dataset)) {
                if (updateStatus == 0) {
                    if (inventoryCountEntity.getMmsInventoryCountDetailList().size() <= 0) {
                        JsfUtil.addFatalMessage("Saving Without Data Not Allowed");
                    } else {
                        try {
                            
                            MmsInventoryBalanceSheet lastId = invBalanceInterface.getLastId();
                            
                            Integer Id = 0;
                            if (lastId != null) {
                                
                                Id = lastId.getIbsId() + 1;
                                
                            }
                            if (Id == 0) {
                                Id = 1;
                            }
                            wfMmsProcessed.setProcessedBy(sessionBean.getUserId());
                            invBalanceEntity.setProcessedOn(wfMmsProcessed.getProcessedOn());
                            invBalanceEntity.setProcessedBy(wfMmsProcessed.getProcessedBy());
                            invBalanceEntity.addInventoryBalanceSheetIdToWorkflow(wfMmsProcessed);
                            for (int i = 0; i < inventoryCountEntity.getMmsInventoryCountDetailList().size(); i++) {
                                invBalanceEntity = new MmsInventoryBalanceSheet();
                                itemRegistrationEntity = new MmsItemRegistration();
                                inventoryCountEntity.setInventoryCountId(inventoryCountEntity.getMmsInventoryCountDetailList().get(i).getInventoryCountId().getInventoryCountId());
                                invBalanceEntity.setInvCountId(inventoryCountEntity);
                                itemRegistrationEntity = (inventoryCountEntity.getMmsInventoryCountDetailList().get(i).getItemCode());
                                invBalanceEntity.setMaterialId(itemRegistrationEntity);
                                invBalanceEntity.setOldValue(inventoryCountEntity.getMmsInventoryCountDetailList().get(i).getItemCode().getMmsBinCard().getCurrentQuantity().longValue());
                                invBalanceEntity.setCountingValue(inventoryCountEntity.getMmsInventoryCountDetailList().get(i).getQuantity());
                                invBalanceEntity.setDiffrence(inventoryCountEntity.getMmsInventoryCountDetailList().get(i).getQuantity().intValue() - inventoryCountEntity.getMmsInventoryCountDetailList().get(i).getItemCode().getMmsBinCard().getCurrentQuantity().intValue());
                                invBalanceEntity.setBudgetYear(inventoryCountEntity.getMmsInventoryCountDetailList().get(i).getInventoryCountId().getBudgetYear());
                                invBalanceEntity.setStoreId(inventoryCountEntity.getMmsInventoryCountDetailList().get(i).getInventoryCountId().getWorkUnit());
                                invBalanceEntity.setRemark(invBalanceEntityTemp.getRemark());
                                invBalanceEntity.setApprovedDate(invBalanceEntityTemp.getApprovedDate());
                                mmsBinCard = new MmsBinCard();
                                mmsBinCard = inventoryCountEntity.getMmsInventoryCountDetailList().get(i).getItemCode().getMmsBinCard();
                                int currentqunt = mmsBinCard.getCurrentQuantity().intValue();
                                bincardList.add(mmsBinCard);
                                invBalanceEntity.setIbsId(Id);
                                invBalanceEntity.addInventoryBalanceSheetIdToWorkflow(wfMmsProcessed);
                                System.out.println("16");
                                invBalanceEntity.setProcessedBy(sessionBean.getUserId());
                                invBalanceEntity.setProcessedOn(wfMmsProcessed.getProcessedOn());
                                invBalanceEntity.setStatus(Constants.PREPARE_VALUE);
                                invBalanceInterface.create(invBalanceEntity);
                                Id++;
                            }
                            inventoryCountEntity.setStatus(Constants.Inv_COUNT_SHEET_PROCESSED_BY_BALANCE_SHEET);
                            invCountInterface.edit(inventoryCountEntity);
                            int binsize = bincardList.size();
                            for (int i = 0; i < binsize; i++) {
                                mmsBinCardBeanLocal.edit(bincardList.get(i));
                            }
                            
                            JsfUtil.addSuccessMessage("Inventory Balance Information Saved successfully");
                            saveUpdateClear();
                        } catch (Exception e) {
                            
                        }
                    }
                } else if (updateStatus == 1 && workflow.isPrepareStatus()) {
                    try {
                        invBalanceEntity.setProcessedOn(wfMmsProcessed.getProcessedOn());
                        invBalanceEntity.setCommentGiven(wfMmsProcessed.getCommentGiven());
                        invBalanceInterface.edit(invBalanceEntity);
                        WfSave();
                        JsfUtil.addSuccessMessage("Inventory Balance Information Updated seccussfully");
                        saveUpdateClear();
                    } catch (Exception ex) {
                        
                    }
                } else if (updateStatus == 1 && (workflow.isApproveStatus() || workflow.isCheckStatus())) {
                    System.out.println("12");
                    if (selectedValue.equalsIgnoreCase("Approve") && workflow.isApproveStatus()) {
                        workflow.setUserStatus(Constants.APPROVE_VALUE);
                        invBalanceEntity.setStatus(Constants.APPROVE_VALUE);
                        wfMmsProcessed.setDecision(Constants.APPROVE_VALUE);
                    } else if (selectedValue.equalsIgnoreCase("Approve") && workflow.isCheckStatus()) {
                        workflow.setUserStatus(Constants.CHECK_APPROVE_VALUE);
                        invBalanceEntity.setStatus(Constants.CHECK_APPROVE_VALUE);
                        wfMmsProcessed.setDecision(Constants.CHECK_APPROVE_VALUE);
                    } else if (selectedValue.equalsIgnoreCase("Reject") && workflow.isApproveStatus()) {
                        workflow.setUserStatus(Constants.APPROVE_REJECT_VALUE);
                        invBalanceEntity.setStatus(Constants.APPROVE_REJECT_VALUE);
                        wfMmsProcessed.setDecision(Constants.APPROVE_REJECT_VALUE);
                    } else if (selectedValue.equalsIgnoreCase("Reject") && workflow.isCheckStatus()) {
                        workflow.setUserStatus(Constants.CHECK_REJECT_VALUE);
                        invBalanceEntity.setStatus(Constants.CHECK_REJECT_VALUE);
                        wfMmsProcessed.setDecision(Constants.CHECK_REJECT_VALUE);
                    }
                    wfMmsProcessedBeanLocal.create(wfMmsProcessed);
                    invBalanceInterface.edit(invBalanceEntity);
                    mmsIbsList.remove(invBalanceEntity);
                    JsfUtil.addSuccessMessage("Inventory Balance Information modified success fully");
                    saveUpdateClear();
                }
            } else {
                EventEntry eventEntry = new EventEntry();
                eventEntry.setSessionId(sessionBean.getSessionID());
                eventEntry.setUserId(sessionBean.getUserId());
                QName qualifiedName = new QName("", "project");
                JAXBElement<String> test = new JAXBElement<String>(qualifiedName, String.class, null, sessionBean.getUserName());
                eventEntry.setUserLogin(test);
                security.addEventLog(eventEntry, dataset);
            }
        } catch (Exception ex) {
            
        }
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="saveUpdate Clear">
    private void saveUpdateClear() {
        invBalanceEntity = new MmsInventoryBalanceSheet();
        wfMmsProcessed = null;
        inventryConting = null;
        inventoryCountEntity = new MmsInventoryCounting();
        storeInfoEntity = new MmsStoreInformation();
        invBalanceEntityTemp = new MmsInventoryBalanceSheet();
    }
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Handler">
    List<MmsInventoryCountDetail> countings = null;
    
    /**
     *
     * @return
     */
    public List<MmsInventoryCountDetail> getCountings() {
        countings = new ArrayList<>();
        return countings;
    }
    
    /**
     *
     * @param countings
     */
    public void setCountings(List<MmsInventoryCountDetail> countings) {
        this.countings = countings;
    }
    List<MmsInventoryBalanceSheet> fmsFixedAssetList;
    
    /**
     *
     * @param event
     */
    public void fixedAssetGroups(ValueChangeEvent event) {
        MmsInventoryBalanceSheet fixedAsset;
        fmsFixedAssetList = new ArrayList();
        String name = (event.getNewValue().toString());
        inventoryCountEntity.setBudgetYear(name);
        countings = invCountInterface.getInventryCounting(name);
        inventryConting = new ListDataModel(new ArrayList<>(countings));
    }
    
    /**
     *
     * @return
     */
    public List<String> getYearList() {
        int currentYear = Integer.parseInt(StringDateManipulation.todayInEC().split("-")[0]);
        List<String> aa = new ArrayList<>();
        aa.add("--- Select One ---");
        for (int i = currentYear + 1; i >= 2000; i--) {
            aa.add(String.valueOf(i));
        }
        return aa;
    }
    List<MmsStoreInformation> storeDetailList = new ArrayList<>();
    
    public List<MmsStoreInformation> getStoreList() {
        
        storeDetailList = storeInterface.findAllStoreInfo();
        
        return storeDetailList;
    }
    
    public void handleSelectStoreNameForSearch(ValueChangeEvent event) {
        if (null != event.getNewValue()) {
            System.out.println("=======inside handdle store======");
            storeInfoEntity.setStoreId(Integer.parseInt(event.getNewValue().toString()));
            
        }
        
    }
    
    List<MmsInventoryBalanceSheet> allInventoryBalancesheetList;
    List<MmsInventoryCounting> allInventorycountinfoList;
    public void searchBalanceSheetByParameter() {
        System.out.println("=============inside============");
        System.out.println("store id=========" + storeInfoEntity.getStoreId());
        System.out.println("inv no=========" + inventoryCountEntity.getInventoryCountNo());
        System.out.println("Budget year=========" + storeInfoEntity.getStoreId());
        inventoryCountEntity.setProcessedBy(sessionBean.getUserId());
//        if (null != storeInfoEntity.getStoreId() && inventoryCountEntity.getInventoryCountNo().isEmpty() && invBalanceEntity.getBudgetYear().isEmpty()) {
            System.out.println("========search By Store=======");
            invBalanceEntity.setProcessedBy(sessionBean.getUserId());
            allInventoryBalancesheetList = invBalanceInterface.getBalanceSheetListsByParameter( invBalanceEntity);
            recerateInventoryBalanceSheetSearchmOdel();
//        } else if (null != storeInfoEntity.getStoreId() && !inventoryCountEntity.getInventoryCountNo().isEmpty() && invBalanceEntity.getBudgetYear().isEmpty()) {
//            System.out.println("========search By Store and invNo=======");
//            System.out.println("store id=========" + storeInfoEntity.getStoreId());
//            System.out.println("inv no=========" + inventoryCountEntity.getInventoryCountNo());
//            allInventoryBalancesheetList = invBalanceInterface.searchByParmeterStoreAndInventoryNo(storeInfoEntity, inventoryCountEntity);
//            recerateInventoryBalanceSheetSearchmOdel();
//        } else if (null != storeInfoEntity.getStoreId() && inventoryCountEntity.getInventoryCountNo().isEmpty() && !invBalanceEntity.getBudgetYear().isEmpty()) {
//            System.out.println("========search By Store and budgetY=======");
//            allInventoryBalancesheetList = invBalanceInterface.searchByParameterStoreAndBudgetYearAndProcessedBy(storeInfoEntity, invBalanceEntity);
//            recerateInventoryBalanceSheetSearchmOdel();
//        } else if (storeInfoEntity.getStoreId() != null && !inventoryCountEntity.getInventoryCountNo().isEmpty() && !invBalanceEntity.getBudgetYear().isEmpty()) {
//            System.out.println("========search By All=======");
//            allInventoryBalancesheetList = invBalanceInterface.searchByAllParameters(storeInfoEntity, inventoryCountEntity, invBalanceEntity);
//            recerateInventoryBalanceSheetSearchmOdel();
//        }
        
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
    
    public DataModel<MmsInventoryCounting> getMmsInventorySearchInfoDataModel() {
        if (mmsInventorySearchInfoDataModel == null) {
            mmsInventorySearchInfoDataModel = new ListDataModel<>();
        }
        return mmsInventorySearchInfoDataModel;
    }
    
    public void setMmsInventorySearchInfoDataModel(DataModel<MmsInventoryCounting> mmsInventorySearchInfoDataModel) {
        this.mmsInventorySearchInfoDataModel = mmsInventorySearchInfoDataModel;
    }
    
    private void recerateInventorySerachModel() {
        mmsInventorySearchInfoDataModel = null;
        mmsInventorySearchInfoDataModel = new ListDataModel(new ArrayList<>(allInventorycountinfoList));
    }
    
    private void recerateInventoryBalanceSheetSearchmOdel() {
        InvBalanceSearchDataModel = null;
        InvBalanceSearchDataModel = new ListDataModel(new ArrayList<>(allInventoryBalancesheetList));
    }
    
    public List<MmsInventoryCounting> getApprovedInvNoList() {
        if (approvedInvNoList == null) {
            approvedInvNoList = new ArrayList<>();
        }
        return approvedInvNoList;
    }
    
    public void setApprovedInvNoList(List<MmsInventoryCounting> approvedInvNoList) {
        this.approvedInvNoList = approvedInvNoList;
    }
    
    List<MmsInventoryCountDetail> inventoryDetailList = new ArrayList<>();
    List<MmsInventoryCountDetail> inventoryDataWithBincardList = new ArrayList<>();
    
     public void changeEventColumnName(ValueChangeEvent changeEvent) {
        if (changeEvent.getNewValue() != null) {
            columnNameResolver.setCol_Name_FromTable(changeEvent.getNewValue().toString());
            columnNameResolver.setParsed_Col_Name(ColumnParser(columnNameResolver.getCol_Name_FromTable()));
            invBalanceEntity.setColumnName(columnNameResolver.getCol_Name_FromTable());

        }

    }

    public String ColumnParser(String col_name) {
        String col = col_name.replace("_", " ");
        System.out.println("col==" + col);
        return col.toLowerCase() + ":";
    }

    public void handleSelectGRNNo(ValueChangeEvent event) {
        if (null != event.getNewValue()) {
            
            inventoryDataWithBincardList.clear();
            
            String GrnNum = event.getNewValue().toString();
            inventoryCountEntity = (MmsInventoryCounting) event.getNewValue();
            inventoryDetailList = invCountDetInterface.getInventoryInfoJoinedWithBinCard(inventoryCountEntity);
            storeInfoEntity = inventoryDetailList.get(0).getItemCode().getStoreId();
            invBalanceEntity.setBudgetYear(inventoryDetailList.get(0).getInventoryCountId().getBudgetYear());
            for (int i = 0; i < inventoryDetailList.size(); i++) {
                inventoryDataWithBincardList.add(inventoryDetailList.get(i));
                inventoryDataWithBincardList.get(i).setBinBalance(inventoryDetailList.get(i).getItemCode().getMmsBinCard().getCurrentQuantity().intValue());
            }
            recerateInventoryDetailSerachModel();
            
        }
        
    }
    
    public void handleSelectInventoryNumber(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            inventoryCountEntity = (MmsInventoryCounting) event.getNewValue();
            storeInfoEntity = inventoryCountEntity.getWorkUnit();
            invBalanceEntity.setBudgetYear(inventoryCountEntity.getBudgetYear());
            recerateInventoryDetail();
        }
    }
    
    public void createNewInventoryBalanceSheet() {
        saveUpdateClear();
        if (createOrSearchBundle.equals("New")) {
            saveorUpdateBundle = "Save";
            renderPnlCreateInvBalanceSheet = true;
            renderPnlManPage = false;
            createOrSearchBundle = "Search";
            setIcone("ui-icon-search");
        } else if (createOrSearchBundle.equals("Search")) {
            renderPnlCreateInvBalanceSheet = false;
            renderPnlManPage = true;
            renderpnlToSearchPage=false;
            createOrSearchBundle = "New";
            setIcone("ui-icon-plus");
        }
    }
    
    public void goBackToSearchpageBtnAction() {
        
        renderPnlCreateInvBalanceSheet = false;
        renderPnlManPage = true;
        renderpnlToSearchPage=false;
    }
    
    public String getCreateOrSearchBundle() {
        return createOrSearchBundle;
    }
    
    public void setCreateOrSearchBundle(String createOrSearchBundle) {
        this.createOrSearchBundle = createOrSearchBundle;
    }
    
    public boolean isRenderPnlCreateInvBalanceSheet() {
        return renderPnlCreateInvBalanceSheet;
    }
    
    public void setRenderPnlCreateInvBalanceSheet(boolean renderPnlCreateInvBalanceSheet) {
        this.renderPnlCreateInvBalanceSheet = renderPnlCreateInvBalanceSheet;
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
    
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Addto detail &recreateDatamodel">
    private void recerateInventoryDetailSerachModel() {
        inventryConting = null;
        inventryConting = new ListDataModel(inventoryDataWithBincardList);
    }
    
    private void recerateInventoryDetail() {
        inventryConting = null;
        inventryConting = new ListDataModel(inventoryCountEntity.getMmsInventoryCountDetailList());
    }
    
    public void onRowEdit(SelectEvent event) {
        invBalanceEntity = (MmsInventoryBalanceSheet) event.getObject();
        invBalanceEntityTemp = (MmsInventoryBalanceSheet) event.getObject();
        populateUI();
        
    }
    
    private void recreateDatamodel() {
        inventryConting = null;
        inventryConting = new ListDataModel(new ArrayList<>(inventoryCountEntity.getMmsInventoryCountDetailList()));
    }
    
    private MmsInventoryBalanceSheet selectedInfo;
    
    public MmsInventoryBalanceSheet getSelectedInfo() {
        return selectedInfo;
    }
    
    public void setSelectedInfo(MmsInventoryBalanceSheet selectedInfo) {
        this.selectedInfo = selectedInfo;
    }
    
    public void populateUI() {
        inventoryCountEntity = invBalanceEntity.getInvCountId();
        approvedInvNoList = new ArrayList<>();
        approvedInvNoList.add(inventoryCountEntity);
        storeInfoEntity = invBalanceEntity.getStoreId();
        renderPnlCreateInvBalanceSheet = true;
        wfMmsProcessed.setProcessedOn(invBalanceEntity.getProcessedOn());
        renderPnlManPage = false;
        renderpnlToSearchPage=true;
        saveorUpdateBundle = "Update";
        updateStatus = 1;
        recreateDatamodel();
    }
    
}
//</editor-fold>
