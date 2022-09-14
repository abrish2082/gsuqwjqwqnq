package et.gov.eep.mms.controller;

import java.io.Serializable;
import java.util.ArrayList;
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
import securityBean.Constants;
import securityBean.SessionBean;
import securityBean.WorkFlow;
import webService.AAA;
import webService.EventEntry;
import webService.IAdministration;
import org.primefaces.event.*;
import et.gov.eep.commonApplications.businessLogic.WfMmsProcessedBeanLocal;
import et.gov.eep.commonApplications.entity.ColumnNameResolver;
import et.gov.eep.commonApplications.entity.WfMmsProcessed;
import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.fcms.entity.admin.FmsGeneralLedger;
import et.gov.eep.fcms.integration.GeneralLedgerIntegrationBeanLocal;
import et.gov.eep.ifrs.businessLogic.DepreciationSetupBeanLocal;
import et.gov.eep.ifrs.entity.IfrsDepreciationSetup;
import et.gov.eep.mms.businessLogic.ItemTypeBeanLocal;
import et.gov.eep.mms.businessLogic.MmsBinCardBeanLocal;
import et.gov.eep.mms.businessLogic.MmsCategoryBeanLocal;
import et.gov.eep.mms.businessLogic.MmsSubcatBeanLocal;
import et.gov.eep.mms.businessLogic.MmsItemRegisrtationBeanLocal;
import et.gov.eep.mms.businessLogic.MmsLuUnitMeasureBeanLocal;
import et.gov.eep.prms.businessLogic.ServiceRegistrationBeanLocal;
import et.gov.eep.mms.businessLogic.MmsStoreInformationBeanLocal;
import et.gov.eep.mms.entity.MmsBinCard;
import et.gov.eep.mms.entity.MmsCategory;
import et.gov.eep.mms.entity.MmsItemType;
import et.gov.eep.mms.entity.MmsSubCat;
import et.gov.eep.mms.entity.MmsItemRegistration;
import et.gov.eep.mms.entity.MmsLuUnitMeasure;
import et.gov.eep.prms.entity.PrmsServiceAndWorkReg;
import et.gov.eep.mms.entity.MmsStoreInformation;
import org.primefaces.model.chart.PieChartModel;

/**
 *
 * @author minab
 */
@Named(value = "itemRegistrationController")
@ViewScoped
public class ItemRegistrationController implements Serializable {
//<editor-fold defaultstate="collapsed" desc="EJb">

    @EJB
    WfMmsProcessedBeanLocal wfMmsProcessedBeanLocal;
    @EJB
    MmsStoreInformationBeanLocal storeInfoInterface;
    @Inject
    MmsItemType itemtype;
    @EJB
    ItemTypeBeanLocal itemtypeBeanLocal;
    @EJB
    MmsLuUnitMeasureBeanLocal unitMeasureBeanLocal;
    @EJB
    ServiceRegistrationBeanLocal serviceBeanlocal;
    @EJB
    DepreciationSetupBeanLocal depreciationSetupBeanLocal;
    @EJB
    MmsCategoryBeanLocal catBeanLocal;
    @EJB
    MmsSubcatBeanLocal subcatBeanlocal;
    @EJB
    MmsItemRegisrtationBeanLocal papmsItemRegisrtationBeanLocal;
    @EJB
    MmsBinCardBeanLocal bincardInterface;
    @EJB
    GeneralLedgerIntegrationBeanLocal generalLedgerInterface;
    //</editor-fold> 

//<editor-fold defaultstate="collapsed" desc="Entities">
    @Inject
    MmsCategory category;
    @Inject
    private MmsCategory categoryPopUp;
    @Inject
    PrmsServiceAndWorkReg serviceAndWorkReg;
    @Inject
    private MmsStoreInformation storeInfoEntity;
    @Inject
    WfMmsProcessed wfMmsProcessed;
    @Inject
    MmsSubCat subcat;
    @Inject
    private MmsSubCat subcatPopup;
    @Inject
    MmsItemRegistration papmsItemRegistration;
    @Inject
    private MmsBinCard binCardEntity;
    @Inject
    FmsGeneralLedger fmsGeneralLedgerEntity;
    @Inject
    WorkFlow workFlow;
    @Inject
    SessionBean SessionBean;
    @Inject
    IfrsDepreciationSetup ifrsDepreciationSetup;
    @Inject
    ColumnNameResolver columnNameResolver;
    //</editor-fold> 

//<editor-fold defaultstate="collapsed" desc="Fields">
    String CatCode = "";
    String subCatCode = "";
    ArrayList<MmsCategory> catList;
    private String saveorUpdateBundle = "Save";

    String btn_categories = "Save";
    int updateStatus = 0;
    int categoriesupdateStatus = 0;
    private boolean disableBtnCreate;
    private boolean renderPnlCreateItemRegistration = false;
    private boolean renderPnlManPage = true;
    private boolean renderStoreInfo = false;
    private boolean renderpnlToSearchPage;
    private String icone = "ui-icon-plus";
    private String activeIndex;
    private String categoryCode;
    private String subCategoryCode;
    private String createOrSearchBundle = "New";
    private boolean previousItem = true;
    private String userName;
    DataModel<WfMmsProcessed> WorkflowDataModel = new ListDataModel<>();
    List<WfMmsProcessed> WfList;
    private List<MmsLuUnitMeasure> unitMeasures;
    private List<IfrsDepreciationSetup> stockMoveLists;
    List<MmsCategory> CategoryList;
    private List<FmsGeneralLedger> GeneralLedgerList;
    String newMaterialCode;

    String lastMatCode = "000";
    private MmsItemRegistration selectedItem;
    List<MmsStoreInformation> StoreList;
    int changestaus = 0;
    List<MmsSubCat> subcatList;
    private DataModel<MmsItemRegistration> mmsItemRegSearchInfoDataModel;
    List<MmsItemRegistration> allItemsInfoList;
    private List<MmsItemRegistration> mmsItemRColumnNameList;
    List<ColumnNameResolver> ColumnNamesList = new ArrayList<>();
    private List<String> mmsItemColumnNameList;
    private PieChartModel pieModel;
    List<String> ItemStatsuTypes = new ArrayList<>();
    List<ItemStatus> ItemStatusList = new ArrayList<>();
    ItemStatus itemStatus = new ItemStatus();
    //</editor-fold> 

    public class ItemStatus implements Serializable {

        private String itemStatus;
        private int total;

        //<editor-fold defaultstate="collapsed" desc="getter and setter">
        public String getItemStatus() {
            return itemStatus;
        }

        public void setItemStatus(String itemStatus) {
            this.itemStatus = itemStatus;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        //</editor-fold>
        public ItemStatus ItemByStatus(String status, int totalCount) {
            this.itemStatus = status;
            this.total = totalCount;
            return this;
        }

    }
///<editor-fold defaultstate="collapsed" desc="PostConstraction">

    public ItemRegistrationController() {
    }

    @PostConstruct
    public void init() {

        wfMmsProcessed.setProcessedBy(SessionBean.getUserId());
        setUserName(SessionBean.getUserName());
        getMmsItemColumnNameList();
        createPieModel();

    }

    public IfrsDepreciationSetup getIfrsDepreciationSetup() {
        if (ifrsDepreciationSetup == null) {
            ifrsDepreciationSetup = new IfrsDepreciationSetup();
        }
        return ifrsDepreciationSetup;
    }

    public void handleSelectDepreciationType(ValueChangeEvent event) {
        if (null != event.getNewValue()) {
            ifrsDepreciationSetup.setId(Integer.valueOf(event.getNewValue().toString()));
            ifrsDepreciationSetup = depreciationSetupBeanLocal.findById(ifrsDepreciationSetup.getId());
            papmsItemRegistration.setDepreciationTypeId(ifrsDepreciationSetup);
        }
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
    //</editor-fold>
//<editor-fold defaultstate="collapsed" desc="gettre and setter">

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public PieChartModel getPieModel() {
        return pieModel;
    }

    public void setPieModel(PieChartModel pieModel) {
        this.pieModel = pieModel;
    }

    public WorkFlow getWorkFlow() {
        if (workFlow == null) {
            workFlow = new WorkFlow();
        }
        return workFlow;
    }

    public void setWorkFlow(WorkFlow workFlow) {
        this.workFlow = workFlow;
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

    public List<IfrsDepreciationSetup> getStockMoveLists() {
        if (stockMoveLists == null) {
            stockMoveLists = new ArrayList<>();
            stockMoveLists = depreciationSetupBeanLocal.depreciationListForStock();
        }
        return stockMoveLists;
    }

    public void setStockMoveLists(List<IfrsDepreciationSetup> stockMoveLists) {
        this.stockMoveLists = stockMoveLists;
    }

    public List<MmsLuUnitMeasure> getUnitMeasures() {
        unitMeasures = unitMeasureBeanLocal.findAll();
        return unitMeasures;
    }

    public void setUnitMeasures(List<MmsLuUnitMeasure> unitMeasures) {
        this.unitMeasures = unitMeasures;
    }

    public ArrayList<MmsCategory> getCatList() {
        return catList;
    }

    /**
     *
     * @param catList
     */
    public void setCatList(ArrayList<MmsCategory> catList) {
        this.catList = catList;
    }

    /**
     *
     * @param event
     */
    public void setCatId(ValueChangeEvent event) {

        int catid = category.getCatId();

    }

    public boolean CheckBox() {

        return true;
    }

    public MmsBinCard getBinCardEntity() {
        if (binCardEntity == null) {
            binCardEntity = new MmsBinCard();
        }
        return binCardEntity;
    }

    public void setBinCardEntity(MmsBinCard binCardEntity) {
        this.binCardEntity = binCardEntity;
    }

    public String getBtn_SaveUpdate() {
        return saveorUpdateBundle;
    }

    /**
     *
     * @param saveorUpdateBundle
     */
    public void setBtn_SaveUpdate(String saveorUpdateBundle) {
        this.saveorUpdateBundle = saveorUpdateBundle;
    }

    /**
     *
     * @return
     */
    public MmsItemRegistration getPapmsItemRegistration() {
        if (papmsItemRegistration == null) {
            papmsItemRegistration = new MmsItemRegistration();
        }
        return papmsItemRegistration;
    }

    /**
     *
     * @param papmsItemRegistration
     */
    public void setPapmsItemRegistration(MmsItemRegistration papmsItemRegistration) {
        this.papmsItemRegistration = papmsItemRegistration;
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

    public List<MmsCategory> getListsOfCategory() {
        CategoryList = catBeanLocal.findAll();
        return CategoryList;
    }

    public List<FmsGeneralLedger> getGeneralLedgerList() {
        if (GeneralLedgerList == null) {
            GeneralLedgerList = new ArrayList<>();
        }
        return GeneralLedgerList;
    }

    public void setGeneralLedgerList(List<FmsGeneralLedger> GeneralLedgerList) {
        this.GeneralLedgerList = GeneralLedgerList;
    }

    /**
     *
     * @return
     */
    public SelectItem[] getListOfItemType() {
        return JsfUtil.getSelectItems(itemtypeBeanLocal.findAll(), true);
    }

    public SelectItem[] getListofUnitMeasure() {

        return JsfUtil.getSelectItems(unitMeasureBeanLocal.findAll(), true);
    }

    public String getBtn_categories() {
        return btn_categories;
    }

    /**
     *
     * @param btn_categories
     */
    public void setBtn_categories(String btn_categories) {
        this.btn_categories = btn_categories;
    }

    /**
     *
     * @return
     */
    public String getMaterialCode() {
        return CatCode;
    }

    /**
     *
     * @param MaterialCode
     */
    public void setMaterialCode(String MaterialCode) {
        this.CatCode = MaterialCode;
    }

    public boolean isRenderStoreInfo() {
        return renderStoreInfo;
    }

    public void setRenderStoreInfo(boolean renderStoreInfo) {
        this.renderStoreInfo = renderStoreInfo;
    }

    public boolean isRenderpnlToSearchPage() {
        return renderpnlToSearchPage;
    }

    public void setRenderpnlToSearchPage(boolean renderpnlToSearchPage) {
        this.renderpnlToSearchPage = renderpnlToSearchPage;
    }

    public boolean isPreviousItem() {
        return previousItem;
    }

    public void setPreviousItem(boolean previousItem) {
        this.previousItem = previousItem;
    }

    public void getByMaterialName(SelectEvent event) {
        papmsItemRegistration = (MmsItemRegistration) event.getObject();
        papmsItemRegistration.getMatName();
        updateStatus = 1;
        saveorUpdateBundle = "Update";

    }

    /**
     *
     * @param event
     */
    public void getByMaterialCode(SelectEvent event) {
        category = (MmsCategory) event.getObject();
        category.getCatCode();

    }

    /**
     *
     * @param event
     */
    public void getByCategoryName(SelectEvent event) {

        category = (MmsCategory) event.getObject();
        category.getCatName();
//        
    }

    public void getByCategoryNames(SelectEvent event) {

        categoryPopUp.setCatCode(event.getObject().toString());
        categoryPopUp = catBeanLocal.getCatByCategoryCode(categoryPopUp);
        setBtn_categories("Update");

    }

    /**
     *
     * @return
     */
    public MmsCategory getCategory() {
        if (category == null) {
            category = new MmsCategory();
        }
        return category;
    }

    /**
     *
     * @param category
     */
    public void setCategory(MmsCategory category) {
        this.category = category;
    }

    /**
     *
     * @return
     */
    public MmsSubCat getSubcat() {
        if (subcat == null) {
            subcat = new MmsSubCat();
        }
        return subcat;
    }

    /**
     *
     * @param subcat
     */
    public void setSubcat(MmsSubCat subcat) {
        this.subcat = subcat;
    }

    public FmsGeneralLedger getFmsGeneralLedgerEntity() {
        if (fmsGeneralLedgerEntity == null) {
            fmsGeneralLedgerEntity = new FmsGeneralLedger();
        }
        return fmsGeneralLedgerEntity;
    }

    public void setFmsGeneralLedgerEntity(FmsGeneralLedger fmsGeneralLedgerEntity) {
        this.fmsGeneralLedgerEntity = fmsGeneralLedgerEntity;
    }

    /**
     *
     * @return
     */
    public List<MmsSubCat> getSubcatList() {
        if (subcatList == null) {
            subcatList = new ArrayList<>();
        }
        return subcatList;
    }

    /**
     *
     * @param subcatList
     */
    public void setSubcatList(List<MmsSubCat> subcatList) {
        this.subcatList = subcatList;
    }

    public PrmsServiceAndWorkReg getServiceAndWorkReg() {
        if (serviceAndWorkReg == null) {
            serviceAndWorkReg = new PrmsServiceAndWorkReg();
        }
        return serviceAndWorkReg;
    }

    public void setServiceAndWorkReg(PrmsServiceAndWorkReg serviceAndWorkReg) {
        this.serviceAndWorkReg = serviceAndWorkReg;
    }

    public List<MmsStoreInformation> getStoreList() {
        StoreList = storeInfoInterface.findAllStoreInfo();
        return StoreList;
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

    public boolean isRenderPnlCreateItemRegistration() {
        return renderPnlCreateItemRegistration;
    }

    public void setRenderPnlCreateItemRegistration(boolean renderPnlCreateItemRegistration) {
        this.renderPnlCreateItemRegistration = renderPnlCreateItemRegistration;
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

    public String getCreateOrSearchBundle() {
        return createOrSearchBundle;
    }

    public void setCreateOrSearchBundle(String createOrSearchBundle) {
        this.createOrSearchBundle = createOrSearchBundle;
    }

    //Search
    public DataModel<MmsItemRegistration> getMmsItemRegSearchInfoDataModel() {
        if (mmsItemRegSearchInfoDataModel == null) {
            mmsItemRegSearchInfoDataModel = new ListDataModel<>();
        }
        return mmsItemRegSearchInfoDataModel;
    }

    public void setMmsItemRegSearchInfoDataModel(DataModel<MmsItemRegistration> mmsItemRegSearchInfoDataModel) {
        this.mmsItemRegSearchInfoDataModel = mmsItemRegSearchInfoDataModel;
    }

    private void recerateItemRegSearchModel() {
        mmsItemRegSearchInfoDataModel = null;
        mmsItemRegSearchInfoDataModel = new ListDataModel(new ArrayList<>(allItemsInfoList));
    }

    public MmsItemRegistration getSelectedItem() {
        return selectedItem;
    }

    public void setSelectedItem(MmsItemRegistration selectedItem) {
        this.selectedItem = selectedItem;
    }

    public MmsSubCat getSubcatPopup() {
        if (subcatPopup == null) {
            subcatPopup = new MmsSubCat();
        }
        return subcatPopup;
    }

    public void setSubcatPopup(MmsSubCat subcatPopup) {
        this.subcatPopup = subcatPopup;
    }

    public MmsCategory getCategoryPopUp() {
        if (categoryPopUp == null) {
            categoryPopUp = new MmsCategory();
        }
        return categoryPopUp;
    }

    public void setCategoryPopUp(MmsCategory categoryPopUp) {
        this.categoryPopUp = categoryPopUp;
    }

    public List<MmsItemRegistration> getMmsItemRColumnNameList() {
        if (mmsItemRColumnNameList == null) {
            mmsItemRColumnNameList = new ArrayList<>();
//            mmsItemRColumnNameList = papmsItemRegisrtationBeanLocal.getMmsItemRColumnNameList();

        }
        return mmsItemRColumnNameList;
    }

    public void setMmsItemRColumnNameList(List<MmsItemRegistration> mmsItemRColumnNameList) {
        this.mmsItemRColumnNameList = mmsItemRColumnNameList;
    }

    //</editor-fold>
//<editor-fold defaultstate="collapsed" desc="save,search,handelar">
    private void createPieModel() {
        System.out.println("in side pie");
        pieModel = new PieChartModel();
        int Hazard = 0;
        int Expiry = 0;
        int Scrub = 0;
        ItemStatsuTypes = papmsItemRegisrtationBeanLocal.findAllItemType();
        System.out.println("ItemStatsuTypes===" + ItemStatsuTypes);

        for (int i = 0; i < ItemStatsuTypes.size(); i++) {
            int itmeCount = 0;
            itmeCount = papmsItemRegisrtationBeanLocal.ConutBYItemType(ItemStatsuTypes.get(i));
            ItemStatusList.add(itemStatus.ItemByStatus(ItemStatsuTypes.get(i), itmeCount));
            System.out.println("ItemStatsuTypes.get(i)==" + ItemStatsuTypes.get(i));
            System.out.println("itmeCount==" + itmeCount);
            pieModel.set(ItemStatsuTypes.get(i), itmeCount);
        }
//        for (int i = 0; i < 3; i++) {
//            if (i == 0) {
//                pieModel.set("Hazard ", Hazard);
//            }
//            if (i == 1) {
//                pieModel.set("Expiry ", Expiry);
//            }
//            if (i == 2) {
//                pieModel.set("Scrub ", Scrub);
//            }
//
//        }
        pieModel.setTitle("Current Item Type");
        pieModel.setLegendPosition("w");
        pieModel.setShowDataLabels(true);
        pieModel.setShadow(true);

    }

    /*This method is used to Create Category
     */
    public void createCategory() {
        try {
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(SessionBean.getUserName(), "createCategory", dataset)) {
                if (categoriesupdateStatus == 0) {
                    if (catBeanLocal.isCategoryduplicated(categoryPopUp)) {
                        JsfUtil.addFatalMessage("This Category is already Registered");

                    } else {
                        try {

                            MmsCategory tempcat = new MmsCategory();
                            tempcat.setCatCode(categoryPopUp.getCatCode());
                            tempcat.setCatName(categoryPopUp.getCatName());
                            tempcat.setCatDescription(categoryPopUp.getCatDescription());
                            catBeanLocal.create(categoryPopUp);
                            JsfUtil.addSuccessMessage("Category is created Succesfully");
                            ClearAddCategory();

                        } catch (Exception e) {
                            JsfUtil.addFatalMessage("Something Error Occured on Catagory Creation");
                            ClearAddCategory();

                        }

                    }

                } else {

                    catBeanLocal.edit(categoryPopUp);
                    JsfUtil.addSuccessMessage("Category is Modified Succesfully");
                    ClearAddCategory();

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
    /*This method is used to column Name Change Event
     */

    public void columnNameChangeEvent(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            System.out.println("event.getNewValue().toString()==" + event.getNewValue().toString());
            columnNameResolver.setCol_Name_FromTable(event.getNewValue().toString());
            columnNameResolver.setParsed_Col_Name(ColumnParser(columnNameResolver.getCol_Name_FromTable()));
            papmsItemRegistration.setColumnName(columnNameResolver.getCol_Name_FromTable());
            papmsItemRegistration.setColumnValue(null);
        }

    }

    public String ColumnParser(String col_name) {
        String col = col_name.replace("_", " ");
        System.out.println("col==" + col);
        return col;
    }

    public List<String> getMmsItemColumnNameList() {
        mmsItemColumnNameList = papmsItemRegisrtationBeanLocal.getMmsItemRColumnNameList();
        if (mmsItemColumnNameList == null) {
            mmsItemColumnNameList = new ArrayList<>();
        }
        System.out.println("=======mmsStoreColumnNameList==" + mmsItemColumnNameList);
        if (mmsItemColumnNameList.size() > 0) {
            ColumnNamesList = new ArrayList<>();
            for (int i = 0; i < mmsItemColumnNameList.size(); i++) {
                ColumnNameResolver obj = new ColumnNameResolver();
                obj.setCol_Name_FromTable((mmsItemColumnNameList.get(i).toLowerCase()));
                obj.setParsed_Col_Name(ColumnParser((mmsItemColumnNameList.get(i).toLowerCase())));
                ColumnNamesList.add(obj);
            }

        }
        return mmsItemColumnNameList;
    }

    public void setMmsItemColumnNameList(List<String> mmsItemColumnNameList) {
        this.mmsItemColumnNameList = mmsItemColumnNameList;
    }

    /*This method is used to Create Subcategory
     */
    public void createsubCategory() {
        try {
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(SessionBean.getUserName(), "createsubCategory", dataset)) {
                if (subcatBeanlocal.isSubcategoryDuplicated(subcatPopup)) {
                    JsfUtil.addErrorMessage("Subcategory is already Found");

                } else {

                    try {

                        subcatPopup.setCatId(categoryPopUp);
                        subcatPopup.setGlCode(fmsGeneralLedgerEntity.getGeneralLedgerCode());
                        categoryPopUp.getMmsSubCatList().add(subcatPopup);
                        catBeanLocal.edit(categoryPopUp);
                        JsfUtil.addSuccessMessage("Sub Category is Created ");
                        clearPage();
                    } catch (Exception e) {
                        JsfUtil.addErrorMessage("sub category Might be Duplicated");
                        clearPage();
                    }
                }
            } else {
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
    /*This method is used to Save workflow
     */

    public void WfSave() {
        wfMmsProcessed.setMaterialId(papmsItemRegistration);
        wfMmsProcessedBeanLocal.create(wfMmsProcessed);
    }
    /*This method is used to Save Item Registrations
     */

    public void saveItemRegistration() {
        try {
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(SessionBean.getUserName(), "saveItemRegistration", dataset)) {
                if (workFlow.isPrepareStatus()) {
                    if (updateStatus == 0) {
                        try {
                            wfMmsProcessed.setProcessedBy(SessionBean.getUserId());
                            papmsItemRegistration.setDepreciationTypeId(ifrsDepreciationSetup);
                            papmsItemRegistration.setProcessedOn(wfMmsProcessed.getProcessedOn());
                            papmsItemRegistration.setStatus(Constants.PREPARE_VALUE);
                            papmsItemRegistration.setProcessedBy(SessionBean.getUserId());
                            wfMmsProcessed.setProcessedOn(papmsItemRegistration.getProcessedOn());
                            papmsItemRegistration.addItemRegistrationIdToWorkflow(wfMmsProcessed);
                            System.out.println("wf prepare");
                            papmsItemRegisrtationBeanLocal.create(papmsItemRegistration);
                            System.out.println("===========item reg id====" + papmsItemRegistration.getMaterialId());
                            bincardInterface.create(binCardEntity);

                            JsfUtil.addSuccessMessage("Item Information Data Created");
                            clearPage();
                        } catch (Exception e) {
                            JsfUtil.addFatalMessage("Something Error Occured on Data Created");
                            clearPage();
                        }
                    } else {
                        try {
                            papmsItemRegistration.setMatCategory(category);
                            papmsItemRegistration.setMatSubcategory(subcat);
                            papmsItemRegistration.setProcessedBy(SessionBean.getUserId());
                            papmsItemRegistration.setCommentGiven(wfMmsProcessed.getCommentGiven());
                            papmsItemRegistration.setProcessedOn(wfMmsProcessed.getProcessedOn());
                            papmsItemRegistration.setStatus(Constants.PREPARE_VALUE);
                            System.out.println("=====status====" + papmsItemRegistration.getStatus());
                            papmsItemRegisrtationBeanLocal.edit(papmsItemRegistration);
                            System.out.println("==========statusUpdata =" + papmsItemRegistration.getMatStatus());

                            JsfUtil.addSuccessMessage("Item Information Data Modified");
                            saveorUpdateBundle = "Save";
                            clearPage();
                        } catch (Exception e) {
                            JsfUtil.addFatalMessage("Something Error Occured on Data Modified");
                            clearPage();

                        }

                    }
                } else {
                    JsfUtil.addFatalMessage("You don't have appropriate permission");
                }
            } else {
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
    /*This method is used to Btn save Service
     */

    public String btnSave_service() {

        if (updateStatus == 0) {
            try {
                serviceBeanlocal.create(serviceAndWorkReg);
                JsfUtil.addSuccessMessage("Service Information Data Created");
                return clearPage();
            } catch (Exception e) {
                JsfUtil.addErrorMessage("Something Error Occured on Data Created");
                clearPage();
                return null;
            }
        } else {
            try {
                papmsItemRegisrtationBeanLocal.edit(papmsItemRegistration);
                JsfUtil.addSuccessMessage("Store Information Data Modified");
                saveorUpdateBundle = "Save";
                return clearPage();
            } catch (Exception e) {
                JsfUtil.addErrorMessage("Something Error Occured on Data Modified");
                clearPage();

            }
            return null;
        }

    }

    /**
     *
     * @param matname
     * @return
     */
    public ArrayList<MmsItemRegistration> searchByMaterialName(String matname) {
        ArrayList<MmsItemRegistration> ItemName = null;
        ItemName = papmsItemRegisrtationBeanLocal.searchItem(matname);
        return ItemName;
    }

    /**
     *
     * @param catname
     * @return
     */
    public ArrayList<MmsCategory> searchByCategoryName(String catname) {
        ArrayList<MmsCategory> categorName = null;
        categoryPopUp.setCatName(catname);
        categorName = catBeanLocal.searchByCategoryName(categoryPopUp);

        categoriesupdateStatus = 1;
        return categorName;
    }
    /*This method is used to Clear when the save mathed executed
     */

    public String clearSave() {
        papmsItemRegistration = null;
        category = null;
        serviceAndWorkReg = null;
        return null;
    }
    /*This method is used to clearPage
     */

    public String clearPage() {
        papmsItemRegistration = new MmsItemRegistration();
        category = new MmsCategory();
        categoryPopUp = new MmsCategory();
        fmsGeneralLedgerEntity = new FmsGeneralLedger();
        updateStatus = 0;
        serviceAndWorkReg = new PrmsServiceAndWorkReg();
        subcat = new MmsSubCat();
        storeInfoEntity = new MmsStoreInformation();
        binCardEntity = new MmsBinCard();
        mmsItemRegSearchInfoDataModel = new ListDataModel<>();
        subcatPopup = new MmsSubCat();
        wfMmsProcessed = new WfMmsProcessed();
        ifrsDepreciationSetup = null;
        return null;
    }
    /*This method is used to Clear Add Category
     */

    public void ClearAddCategory() {
        categoryPopUp = new MmsCategory();
        setBtn_categories("Create");
    }
    /*This method is used to change Listener
     */

    public void changeListener(ValueChangeEvent event) {

        if (null != event.getNewValue()) {
            category.setCatId(Integer.parseInt(event.getNewValue().toString()));
            papmsItemRegistration.setMatCategory(category);
            category = catBeanLocal.searchByCategoryId(category);
            categoryCode = category.getCatCode();

            subcatList = category.getMmsSubCatList();
        }

    }
    /*This method is used to Sub catgory Listener
     */

    public void SubcatchangeListener(ValueChangeEvent event) {

        if (null != event.getNewValue()) {
            subcat = (MmsSubCat) event.getNewValue();
            papmsItemRegistration.setMatSubcategory(subcat);
            subCategoryCode = subcat.getSubcatCode();
            changestaus = 1;
            generateMaterialCode();

        }

    }
    /*This method is used to handle selecting StoreName
     */

    public void handleSelectStoreName(ValueChangeEvent event) {
        if (null != event.getNewValue()) {
            Integer Id = Integer.parseInt(event.getNewValue().toString());
            storeInfoEntity.setStoreId(Id);
            papmsItemRegistration.setStoreId(storeInfoEntity);
        }

    }
    /*This method is used to handle selecting CategoryName
     */

    public void handleSelectCategoryName(ValueChangeEvent event) {
        if (null != event.getNewValue()) {

            String catName = event.getNewValue().toString();
            category.setCatName(catName);

            category = catBeanLocal.findCategory(category);

        }

    }
    /*This method is used to handle selecting CategoryNamePopup
     */

    public void handleSelectCategoryNamePopup(ValueChangeEvent event) {
        if (null != event.getNewValue()) {

            String catName = event.getNewValue().toString();
            categoryPopUp.setCatName(catName);

            categoryPopUp = catBeanLocal.findCategory(categoryPopUp);

        }

    }
    /*This method is used to handle selecting AccountTitle
     */

    public void handleSelectAccountTitle(ValueChangeEvent event) {
        if (null != event.getNewValue()) {
            fmsGeneralLedgerEntity = (FmsGeneralLedger) event.getNewValue();
            System.out.println("========GL========" + fmsGeneralLedgerEntity.getAccountTitle());
            System.out.println("========GL Code========" + fmsGeneralLedgerEntity.getGeneralLedgerCode());
            subcatPopup.setSubcatCode(fmsGeneralLedgerEntity.getGeneralLedgerCode().substring(2));
            subcatPopup.setSubCatName(fmsGeneralLedgerEntity.getAccountTitle());

        }
    }
    /*This method is used to createNewItemRegInfo
     */

    public void createNewItemRegInfo() {
        clearPage();
        saveorUpdateBundle = "Save";
        disableBtnCreate = false;

        renderPnlCreateItemRegistration = true;
        renderPnlManPage = false;
        GeneralLedgerList = generalLedgerInterface.findActiveGeneralLedgersList();
    }
    /*This method is used to goBackSearchButtonAction
     */

    public void goBackSearchButtonAction() {
        renderpnlToSearchPage = false;
        renderPnlCreateItemRegistration = false;
        renderPnlManPage = true;
    }
    /*This method is used to btn_Search_Action
     */

    public void btn_Search_Action() {
        clearPage();
        renderpnlToSearchPage = false;
        if (createOrSearchBundle.equals("New")) {
            renderPnlCreateItemRegistration = true;
            renderPnlManPage = false;
            createOrSearchBundle = "Search";
            setIcone("ui-icon-search");
        } else if (createOrSearchBundle.equals("Search")) {
            renderPnlCreateItemRegistration = false;
            renderPnlManPage = true;
            createOrSearchBundle = "New";
            setIcone("ui-icon-plus");
        }
    }
    /*This method is used to searchItemInformation
     */

    public void searchItemInformation() {
        System.out.println("in search");
        papmsItemRegistration.setProcessedBy(SessionBean.getUserId());
        System.out.println("processor " + papmsItemRegistration.getProcessedBy());
        allItemsInfoList = papmsItemRegisrtationBeanLocal.getItemListsByParameter(columnNameResolver, papmsItemRegistration, papmsItemRegistration.getColumnValue());
        recerateItemRegSearchModel();

    }
    /*This method is used to on Row Edit
     */

    public void onRowEdit(SelectEvent event) {

        papmsItemRegistration = (MmsItemRegistration) event.getObject();

        category = papmsItemRegistration.getMatCategory();

        if (category != null) {
            System.out.println("====inside sub==");
            subcatList = new ArrayList<>();

            subcatList = category.getMmsSubCatList();
        }
        subcat = papmsItemRegistration.getMatSubcategory();
        wfMmsProcessed.setProcessedOn(papmsItemRegistration.getProcessedOn());
        ifrsDepreciationSetup = papmsItemRegistration.getDepreciationTypeId();
        renderPnlCreateItemRegistration = true;
        renderPnlManPage = false;
        renderStoreInfo = true;
        renderpnlToSearchPage = true;
        activeIndex = "0";
        saveorUpdateBundle = "Update";
        createOrSearchBundle = "New";
        setCreateOrSearchBundle(createOrSearchBundle);
        setIcone("ui-icon-plus");
        disableBtnCreate = true;
        updateStatus = 1;

    }

    /*This method is used to CheckBoxPrevious
     */
    public void CheckBoxPrevious(ValueChangeEvent event) {
        if ("false".equals(event.getNewValue().toString())) {
            setPreviousItem(false);
            papmsItemRegistration.setPreviousItem(0);
        } else {
            setPreviousItem(true);
            papmsItemRegistration.setPreviousItem(1);
        }
    }
    /*This method is used to generate Material Code
     */

    public void generateMaterialCode() {

        if (updateStatus == 1 && changestaus == 0) {
            System.out.println("==========inside if of generate mat code======");
            newMaterialCode = papmsItemRegistration.getMatCode();
        } else if (updateStatus == 0 && changestaus == 1) {

            String lastMaterialCode = papmsItemRegisrtationBeanLocal.getLastMaterialCode(papmsItemRegistration, category, subcat);
            System.out.println("=======last mat code==" + lastMaterialCode);
            if (lastMaterialCode != null) {

                lastMatCode = lastMaterialCode;
                System.out.println("lastMatcode" + lastMatCode);
            }
            int newMatCode = 000;
            if (lastMatCode.equals("000")) {

                newMaterialCode = categoryCode + " " + subCategoryCode + " " + "00" + Integer.toString(1);
                System.out.println("===========Inside If======== material code==========" + newMaterialCode);
                papmsItemRegistration.setMatCode(newMaterialCode);
            } else {
                String[] lastMatCodePattern = lastMatCode.split(" ");
                int partsSize = lastMatCodePattern.length;
                System.out.println("============" + lastMatCodePattern.length);
                System.out.println("============" + partsSize);
                newMatCode = Integer.parseInt(lastMatCodePattern[2]);
                System.out.println("=======Before incrementing========" + newMatCode);
                int newCode = newMatCode + 1;
                if ((newCode > 0) && (newCode <= 9)) {
                    lastMatCodePattern[2] = "00" + Integer.toString(newCode);
                    newMaterialCode = lastMatCodePattern[0] + " " + lastMatCodePattern[1] + " " + lastMatCodePattern[2];
                } else if ((newCode >= 10) && (newCode <= 99)) {
                    lastMatCodePattern[2] = "0" + Integer.toString(newCode);

                    newMaterialCode = lastMatCodePattern[0] + " " + lastMatCodePattern[1] + " " + lastMatCodePattern[2];
                } else if ((newCode >= 100) && (newCode <= 999)) {
                    System.out.println("========inside elese if=======" + Integer.toString(newCode));
                    lastMatCodePattern[2] = Integer.toString(newCode);
                    newMaterialCode = lastMatCodePattern[0] + " " + lastMatCodePattern[1] + " " + lastMatCodePattern[2];
                }

            }
        } //for update for changing the vlaues of category and subcategory
        else if (updateStatus == 1 && changestaus == 1) {

            System.out.println("===========category =============" + category.getCatId());
            String lastMaterialCode = papmsItemRegisrtationBeanLocal.getLastMaterialCode(papmsItemRegistration, category, subcat);
            if (lastMaterialCode != null) {

                lastMatCode = lastMaterialCode;
            }
            int newMatCode = 000;
            if (lastMatCode.equals("000")) {

                newMaterialCode = categoryCode + " " + subCategoryCode + " " + "00" + Integer.toString(1);
                System.out.println("===========Inside If======== material code==========" + newMaterialCode);
                papmsItemRegistration.setMatCode(newMaterialCode);
            } else {

                String[] lastMatCodePattern = lastMatCode.split(" ");
                int partsSize = lastMatCodePattern.length;

                newMatCode = Integer.parseInt(lastMatCodePattern[2]);
                int newCode = newMatCode + 1;
                if ((newCode > 0) && (newCode <= 9)) {
                    lastMatCodePattern[2] = "00" + Integer.toString(newCode);
                    lastMatCodePattern[0] = category.getCatCode();
                    lastMatCodePattern[1] = subcat.getSubcatCode();
                    newMaterialCode = lastMatCodePattern[0] + " " + lastMatCodePattern[1] + " " + lastMatCodePattern[2];
                } else if ((newCode >= 10) && (newCode <= 99)) {
                    lastMatCodePattern[2] = "0" + Integer.toString(newCode);
                    lastMatCodePattern[0] = category.getCatCode();
                    lastMatCodePattern[1] = subcat.getSubcatCode();
                    newMaterialCode = lastMatCodePattern[0] + " " + lastMatCodePattern[1] + " " + lastMatCodePattern[2];
                } else if ((newCode >= 100) && (newCode <= 999)) {
                    lastMatCodePattern[2] = Integer.toString(newCode);
                    lastMatCodePattern[0] = category.getCatCode();
                    lastMatCodePattern[1] = subcat.getSubcatCode();
                    newMaterialCode = lastMatCodePattern[0] + " " + lastMatCodePattern[1] + " " + lastMatCodePattern[2];
                }

            }

        }

        papmsItemRegistration.setMatCode(newMaterialCode);
    }
  //</editor-fold> 
}
