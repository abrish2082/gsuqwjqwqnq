/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.mms.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
import org.primefaces.event.SelectEvent;
import et.gov.eep.commonApplications.businessLogic.WfMmsProcessedBeanLocal;
import et.gov.eep.commonApplications.entity.WfMmsProcessed;
import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.mms.businessLogic.ManageLocationDtlBeanLocal;
import et.gov.eep.mms.businessLogic.MmsIsivBeanLocal;
import et.gov.eep.mms.businessLogic.MmsItemRegisrtationBeanLocal;
import et.gov.eep.mms.businessLogic.MmsLocationInfoBeanLocal;
import et.gov.eep.mms.businessLogic.MmsManageLocationBeanLocal;
import et.gov.eep.mms.businessLogic.MmsShelfInfoBeanLocal;
import et.gov.eep.mms.businessLogic.MmsSivBeanLocal;
import et.gov.eep.mms.businessLogic.MmsStoreInformationBeanLocal;
import et.gov.eep.mms.entity.MmsIsiv;
import et.gov.eep.mms.entity.MmsIsivDetail;
import et.gov.eep.mms.entity.MmsItemRegistration;
import et.gov.eep.mms.entity.MmsLocationInfo;
import et.gov.eep.mms.entity.MmsLocationInfoDtl;
import et.gov.eep.mms.entity.MmsManageLocation;
import et.gov.eep.mms.entity.MmsManageLocationDtl;
import et.gov.eep.mms.entity.MmsShelfInfo;
import et.gov.eep.mms.entity.MmsSiv;
import et.gov.eep.mms.entity.MmsSivDetail;
import et.gov.eep.mms.entity.MmsStoreInformation;



/**
 *
 * @author minab
 */
@Named(value = "itemLocationIssuanceController")
@ViewScoped
public class ItemLocationIssuanceController implements Serializable {
    //<editor-fold defaultstate="collapsed" desc="EJB">

    @EJB
    private MmsSivBeanLocal sivInterface;
    @EJB
    private MmsIsivBeanLocal isivInterface;
    @EJB
    private MmsItemRegisrtationBeanLocal itemRegistrationInterface;
    @EJB
    private MmsStoreInformationBeanLocal storeInfoInterface;
    @EJB
    MmsShelfInfoBeanLocal mmsShelfInfoInterface;
    @EJB
    WfMmsProcessedBeanLocal wfMmsProcessedBeanLocal;
    @EJB
    MmsLocationInfoBeanLocal mmsCellInfoInterface;
    @EJB
    MmsManageLocationBeanLocal mmsManageLocationInterface;
    @EJB
    ManageLocationDtlBeanLocal mmsmanageLocationDtlInterface;
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Entity's"> 
    @Inject
    WfMmsProcessed wfMmsProcessed;
    @Inject
    private MmsManageLocation mmsManageLocationEntity;
    @Inject
    private MmsManageLocation mmsManageLocationTempEntity;
    @Inject
    private MmsManageLocationDtl mmsManageLocationDtlEntity;

    @Inject
    private MmsSiv sivEntity;

    @Inject
    private MmsSivDetail sivDtlEntity;

    @Inject
    private MmsIsiv isivEntity;
    @Inject
    private MmsIsivDetail isivDetailEntity;

    @Inject
    private MmsItemRegistration itemRegEntity;

    @Inject
    private MmsStoreInformation storeInfoEntity;
    @Inject
    private MmsShelfInfo mmsShelfInfoEntity;

    @Inject
    private MmsLocationInfo mmsCellInfoEntity;
    @Inject
    private MmsLocationInfoDtl mmsCellInfoDtlEntity;
    @Inject
    SessionBean SessionBean;
    @Inject
    WorkFlow workflow;
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Fields">
    private DataModel<MmsManageLocationDtl> manageLocationDetailDataModel;
    private DataModel<MmsManageLocationDtl> LocationDetailDataModel;
    private DataModel<MmsManageLocationDtl> deductionDataModel;
    private DataModel<MmsManageLocation> mmsLocationSearchInfoDataModel;
    private DataModel<MmsManageLocationDtl> mmsLocationDtlSearchInfoDataModel;

    private String saveorUpdateBundle = "Save";
    private String checkType = "SIV";
    private String CheckWarehouse = "0";
    private String selectOption2;
    private boolean renderWareHouseOneMenu = false;
    private boolean disableBtnAdd = true;
    private String createOrSearchBundle = "New";
    private boolean renderPnlCreateLocationissuance = false;
    private boolean renderPnlManPage = true;
    private boolean renderSiv = true;
    private boolean renderPanelSave = true;
    private boolean readOnlyquantiy = false;
    private boolean readOnlyDeductquantiy = false;
    private boolean renderIsivIssuance = false;
    private boolean disableSivNoList = false;
    private boolean disableIsivNoList = false;
    private boolean disableSivMatCodeList = false;
    private boolean disableISIVMatCodeList = false;
    private boolean renderOutdoorInfo = false;
    private boolean renderRackCode = false;
    private boolean renderpnlToSearchPage;
    private String icone = "ui-icon-plus";
    private String activeIndex;
    private String storeName;
    int updateStatus = 0;
    private Integer unallicatedQuantity;
    private int rowIndex;
    private boolean renderPreparerInfo = true;
    Set<MmsItemRegistration> checkMaterialId = new HashSet<>();
    Set<MmsLocationInfo> checkcellId = new HashSet<>();
    Set<MmsShelfInfo> checkshelfId = new HashSet<>();
    DataModel<WfMmsProcessed> WorkflowDataModel = new ListDataModel<>();
    List<WfMmsProcessed> WfList;
    private String userName;
    private String AddorModify = "Add";
    private List<MmsSiv> approvedSivList;
    private List<MmsSivDetail> SivMaterialsList;
    private List<MmsItemRegistration> itemInfoList;
    //approved Isiv no(ISSUED) list
    private List<MmsIsiv> approvedIsivList;
    private List<MmsManageLocationDtl> locationdtlList;
    List<MmsStoreInformation> StoreList;
    //Get Cell codes Based on Shelf
    private List<MmsLocationInfo> cellList;
    private List<MmsIsivDetail> IsivMaterialsList;
    private MmsManageLocationDtl selectedLocation;
    List<MmsManageLocation> allLocationList;
    private List<MmsManageLocationDtl> allLocationInfoList;
    List<MmsItemRegistration> detailLists = new ArrayList<>();
    boolean result;
    List<MmsManageLocationDtl> deductionList = new ArrayList<>();
    List<MmsManageLocationDtl> deductionList1 = new ArrayList<>();
    Integer shelfId;
    Integer cellId;
    List<MmsManageLocationDtl> locationInfo = new ArrayList<>();
    List<MmsShelfInfo> closedShadeList = new ArrayList<>();

    //Get Shelf Lists based on store
    private List<MmsShelfInfo> ShelfList;
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Constructor">

    /**
     * Creates a new instance of ItemLocationIssuanceController
     */
    public ItemLocationIssuanceController() {
    }

    @PostConstruct
    public void init() {
        setUserName(SessionBean.getUserName());
        wfMmsProcessed.setProcessedBy(SessionBean.getUserId());
    }

//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Getter And Setters">
    public boolean isRenderPreparerInfo() {
        return renderPreparerInfo;
    }

    public void setRenderPreparerInfo(boolean renderPreparerInfo) {
        this.renderPreparerInfo = renderPreparerInfo;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public MmsManageLocation getMmsManageLocationEntity() {
        if (mmsManageLocationEntity == null) {
            mmsManageLocationEntity = new MmsManageLocation();
        }
        return mmsManageLocationEntity;
    }

    public void setMmsManageLocationEntity(MmsManageLocation mmsManageLocationEntity) {
        this.mmsManageLocationEntity = mmsManageLocationEntity;
    }

    public MmsManageLocation getMmsManageLocationTempEntity() {
        if (mmsManageLocationTempEntity == null) {
            mmsManageLocationTempEntity = new MmsManageLocation();
        }
        return mmsManageLocationTempEntity;
    }

    public void setMmsManageLocationTempEntity(MmsManageLocation mmsManageLocationTempEntity) {
        this.mmsManageLocationTempEntity = mmsManageLocationTempEntity;
    }

    public MmsManageLocationDtl getMmsManageLocationDtlEntity() {
        if (mmsManageLocationDtlEntity == null) {
            mmsManageLocationDtlEntity = new MmsManageLocationDtl();
        }
        return mmsManageLocationDtlEntity;
    }

    public void setMmsManageLocationDtlEntity(MmsManageLocationDtl mmsManageLocationDtlEntity) {
        this.mmsManageLocationDtlEntity = mmsManageLocationDtlEntity;
    }

    public MmsIsiv getIsivEntity() {
        if (isivEntity == null) {
            isivEntity = new MmsIsiv();
        }
        return isivEntity;
    }

    public void setIsivEntity(MmsIsiv isivEntity) {
        this.isivEntity = isivEntity;
    }

    public MmsIsivDetail getIsivDetailEntity() {
        if (isivDetailEntity == null) {
            isivDetailEntity = new MmsIsivDetail();
        }
        return isivDetailEntity;
    }

    public void setIsivDetailEntity(MmsIsivDetail isivDetailEntity) {
        this.isivDetailEntity = isivDetailEntity;
    }

    public MmsItemRegistration getItemRegEntity() {
        if (itemRegEntity == null) {
            itemRegEntity = new MmsItemRegistration();
        }
        return itemRegEntity;
    }

    public void setItemRegEntity(MmsItemRegistration itemRegEntity) {
        this.itemRegEntity = itemRegEntity;
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

    public MmsShelfInfo getMmsShelfInfoEntity() {
        if (mmsShelfInfoEntity == null) {
            mmsShelfInfoEntity = new MmsShelfInfo();
        }
        return mmsShelfInfoEntity;
    }

    public void setMmsShelfInfoEntity(MmsShelfInfo mmsShelfInfoEntity) {
        this.mmsShelfInfoEntity = mmsShelfInfoEntity;
    }

    public MmsLocationInfo getMmsCellInfoEntity() {
        if (mmsCellInfoEntity == null) {
            mmsCellInfoEntity = new MmsLocationInfo();
        }
        return mmsCellInfoEntity;
    }

    public void setMmsCellInfoEntity(MmsLocationInfo mmsCellInfoEntity) {
        this.mmsCellInfoEntity = mmsCellInfoEntity;
    }

    public MmsLocationInfoDtl getMmsCellInfoDtlEntity() {
        if (mmsCellInfoDtlEntity == null) {
            mmsCellInfoDtlEntity = new MmsLocationInfoDtl();
        }
        return mmsCellInfoDtlEntity;
    }

    public void setMmsCellInfoDtlEntity(MmsLocationInfoDtl mmsCellInfoDtlEntity) {
        this.mmsCellInfoDtlEntity = mmsCellInfoDtlEntity;
    }

    public MmsSiv getSivEntity() {
        if (sivEntity == null) {
            sivEntity = new MmsSiv();
        }
        return sivEntity;
    }

    public void setSivEntity(MmsSiv sivEntity) {
        this.sivEntity = sivEntity;
    }

    public MmsSivDetail getSivDtlEntity() {
        if (sivDtlEntity == null) {
            sivDtlEntity = new MmsSivDetail();
        }
        return sivDtlEntity;
    }

    public void setSivDtlEntity(MmsSivDetail sivDtlEntity) {
        this.sivDtlEntity = sivDtlEntity;
    }

    public DataModel<MmsManageLocationDtl> getManageLocationDetailDataModel() {
        if (manageLocationDetailDataModel == null) {
            manageLocationDetailDataModel = new ListDataModel<>();
        }
        return manageLocationDetailDataModel;
    }

    public void setManageLocationDetailDataModel(DataModel<MmsManageLocationDtl> manageLocationDetailDataModel) {
        this.manageLocationDetailDataModel = manageLocationDetailDataModel;
    }

    public DataModel<MmsManageLocationDtl> getLocationDetailDataModel() {
        if (LocationDetailDataModel == null) {
            LocationDetailDataModel = new ListDataModel<>();
        }
        return LocationDetailDataModel;
    }

    public void setLocationDetailDataModel(DataModel<MmsManageLocationDtl> LocationDetailDataModel) {
        this.LocationDetailDataModel = LocationDetailDataModel;
    }

    public DataModel<MmsManageLocationDtl> getDeductionDataModel() {
        if (deductionDataModel == null) {
            deductionDataModel = new ListDataModel<>();
        }
        return deductionDataModel;
    }

    public void setDeductionDataModel(DataModel<MmsManageLocationDtl> deductionDataModel) {
        this.deductionDataModel = deductionDataModel;
    }

    public DataModel<MmsManageLocation> getMmsLocationSearchInfoDataModel() {
        if (mmsLocationSearchInfoDataModel == null) {
            mmsLocationSearchInfoDataModel = new ListDataModel<>();
        }
        return mmsLocationSearchInfoDataModel;
    }

    public void setMmsLocationSearchInfoDataModel(DataModel<MmsManageLocation> mmsLocationSearchInfoDataModel) {
        this.mmsLocationSearchInfoDataModel = mmsLocationSearchInfoDataModel;
    }

    public String getSaveorUpdateBundle() {
        return saveorUpdateBundle;
    }

    public void setSaveorUpdateBundle(String saveorUpdateBundle) {
        this.saveorUpdateBundle = saveorUpdateBundle;
    }

    public String getCheckType() {
        return checkType;
    }

    public void setCheckType(String checkType) {
        this.checkType = checkType;
    }

    public String getCheckWarehouse() {
        return CheckWarehouse;
    }

    public void setCheckWarehouse(String CheckWarehouse) {
        this.CheckWarehouse = CheckWarehouse;
    }

    public boolean isRenderWareHouseOneMenu() {
        return renderWareHouseOneMenu;
    }

    public void setRenderWareHouseOneMenu(boolean renderWareHouseOneMenu) {
        this.renderWareHouseOneMenu = renderWareHouseOneMenu;
    }

    public String getCreateOrSearchBundle() {
        return createOrSearchBundle;
    }

    public void setCreateOrSearchBundle(String createOrSearchBundle) {
        this.createOrSearchBundle = createOrSearchBundle;
    }

    public boolean isRenderPnlCreateLocationissuance() {
        return renderPnlCreateLocationissuance;
    }

    public void setRenderPnlCreateLocationissuance(boolean renderPnlCreateLocationissuance) {
        this.renderPnlCreateLocationissuance = renderPnlCreateLocationissuance;
    }

    public boolean isRenderPnlManPage() {
        return renderPnlManPage;
    }

    public void setRenderPnlManPage(boolean renderPnlManPage) {
        this.renderPnlManPage = renderPnlManPage;
    }

    public boolean isRenderSiv() {
        return renderSiv;
    }

    public void setRenderSiv(boolean renderSiv) {
        this.renderSiv = renderSiv;
    }

    public boolean isRenderIsivIssuance() {
        return renderIsivIssuance;
    }

    public void setRenderIsivIssuance(boolean renderIsivIssuance) {
        this.renderIsivIssuance = renderIsivIssuance;
    }

    public String getIcone() {
        return icone;
    }

    public void setIcone(String icone) {
        this.icone = icone;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public boolean isDisableSivNoList() {
        return disableSivNoList;
    }

    public void setDisableSivNoList(boolean disableSivNoList) {
        this.disableSivNoList = disableSivNoList;
    }

    public boolean isDisableIsivNoList() {
        return disableIsivNoList;
    }

    public void setDisableIsivNoList(boolean disableIsivNoList) {
        this.disableIsivNoList = disableIsivNoList;
    }

    public boolean isDisableSivMatCodeList() {
        return disableSivMatCodeList;
    }

    public void setDisableSivMatCodeList(boolean disableSivMatCodeList) {
        this.disableSivMatCodeList = disableSivMatCodeList;
    }

    public boolean isDisableISIVMatCodeList() {
        return disableISIVMatCodeList;
    }

    public void setDisableISIVMatCodeList(boolean disableISIVMatCodeList) {
        this.disableISIVMatCodeList = disableISIVMatCodeList;
    }

    public Integer getUnallicatedQuantity() {
        return unallicatedQuantity;
    }

    public void setUnallicatedQuantity(Integer unallicatedQuantity) {
        this.unallicatedQuantity = unallicatedQuantity;
    }

    public String getSelectOption2() {
        return selectOption2;
    }

    public void setSelectOption2(String selectOption2) {
        this.selectOption2 = selectOption2;
    }

    public boolean isRenderOutdoorInfo() {
        return renderOutdoorInfo;
    }

    public void setRenderOutdoorInfo(boolean renderOutdoorInfo) {
        this.renderOutdoorInfo = renderOutdoorInfo;
    }

    public boolean isRenderRackCode() {
        return renderRackCode;
    }

    public void setRenderRackCode(boolean renderRackCode) {
        this.renderRackCode = renderRackCode;
    }

    public boolean isRenderpnlToSearchPage() {
        return renderpnlToSearchPage;
    }

    public void setRenderpnlToSearchPage(boolean renderpnlToSearchPage) {
        this.renderpnlToSearchPage = renderpnlToSearchPage;
    }

    public int getRowIndex() {
        return rowIndex;
    }

    public void setRowIndex(int rowIndex) {
        this.rowIndex = rowIndex;
    }

    public boolean isRenderPanelSave() {
        return renderPanelSave;
    }

    public void setRenderPanelSave(boolean renderPanelSave) {
        this.renderPanelSave = renderPanelSave;
    }

    public boolean isReadOnlyquantiy() {
        return readOnlyquantiy;
    }

    public void setReadOnlyquantiy(boolean readOnlyquantiy) {
        this.readOnlyquantiy = readOnlyquantiy;
    }

    public boolean isReadOnlyDeductquantiy() {
        return readOnlyDeductquantiy;
    }

    public void setReadOnlyDeductquantiy(boolean readOnlyDeductquantiy) {
        this.readOnlyDeductquantiy = readOnlyDeductquantiy;
    }

    public boolean isDisableBtnAdd() {
        return disableBtnAdd;
    }

    public void setDisableBtnAdd(boolean disableBtnAdd) {
        this.disableBtnAdd = disableBtnAdd;
    }

    public String getAddorModify() {
        return AddorModify;
    }

    public void setAddorModify(String AddorModify) {
        this.AddorModify = AddorModify;
    }

    public DataModel<MmsManageLocationDtl> getMmsLocationDtlSearchInfoDataModel() {
        if (mmsLocationDtlSearchInfoDataModel == null) {
            mmsLocationDtlSearchInfoDataModel = new ListDataModel<>();
        }
        return mmsLocationDtlSearchInfoDataModel;
    }

    public void setMmsLocationDtlSearchInfoDataModel(DataModel<MmsManageLocationDtl> mmsLocationDtlSearchInfoDataModel) {
        this.mmsLocationDtlSearchInfoDataModel = mmsLocationDtlSearchInfoDataModel;
    }

    public List<MmsManageLocationDtl> getDeductionList() {
        if (deductionList == null) {
            deductionList = new ArrayList<>();
        }
        return deductionList;
    }

    public void setDeductionList(List<MmsManageLocationDtl> deductionList) {
        this.deductionList = deductionList;
    }

    public List<MmsIsiv> getApprovedIsivList() {
        if (approvedIsivList == null) {
            approvedIsivList = new ArrayList<>();
        }
        return approvedIsivList;
    }

    public void setApprovedIsivList(List<MmsIsiv> approvedIsivList) {
        this.approvedIsivList = approvedIsivList;
    }

    public List<MmsItemRegistration> getItemInfoList() {
        if (itemInfoList == null) {
            itemInfoList = new ArrayList<>();
        }
        return itemInfoList;
    }

    public void setItemInfoList(List<MmsItemRegistration> itemInfoList) {
        this.itemInfoList = itemInfoList;
    }

    public List<MmsItemRegistration> getSIVMaterialList() {
        SivMaterialsList = sivEntity.getMmsSivDetailList();

        for (int i = 0; i < SivMaterialsList.size(); i++) {
            itemInfoList.add(SivMaterialsList.get(i).getItemId());
        }
        return itemInfoList;
    }

    public List<MmsSivDetail> getSivMaterialsList() {
        if (SivMaterialsList == null) {
            SivMaterialsList = new ArrayList<>();
        }
        return SivMaterialsList;
    }

    public void setSivMaterialsList(List<MmsSivDetail> SivMaterialsList) {
        this.SivMaterialsList = SivMaterialsList;
    }

    public List<MmsSiv> getApprovedSivList() {
        if (approvedSivList == null) {
            approvedSivList = new ArrayList<>();
        }
        return approvedSivList;
    }

    public void setApprovedSivList(List<MmsSiv> approvedSivList) {
        this.approvedSivList = approvedSivList;
    }

    public List<MmsItemRegistration> getISIVMaterialList() {
        IsivMaterialsList = isivEntity.getMmsIsivDetailList();

        for (int i = 0; i < IsivMaterialsList.size(); i++) {
            itemInfoList.add(IsivMaterialsList.get(i).getItemId());
        }
        return itemInfoList;
    }

    public List<MmsStoreInformation> getStoreList() {
        StoreList = storeInfoInterface.findAllStoreInfo();
        return StoreList;
    }

    public List<MmsIsivDetail> getIsivMaterialsList() {

        System.out.println("=======isiv.getisiv detial list========" + isivEntity.getMmsIsivDetailList().size());
        IsivMaterialsList = isivEntity.getMmsIsivDetailList();

        return IsivMaterialsList;
    }

    //get warehouse List
    public List<MmsShelfInfo> getWarehouseLists() {

        ArrayList<MmsShelfInfo> warehouseNames = mmsShelfInfoInterface.getWarhouseListByStoreId(mmsShelfInfoEntity);
        System.out.println("size of the Warehouselist=================" + warehouseNames.isEmpty());

        return warehouseNames;
    }

    public List<MmsShelfInfo> getClosedShadeList() {
        if (closedShadeList == null) {
            closedShadeList = new ArrayList<>();
        }
        return closedShadeList;
    }

    public void setClosedShadeList(List<MmsShelfInfo> closedShadeList) {
        this.closedShadeList = closedShadeList;
    }

    public List<MmsManageLocationDtl> getLocationdtlList() {
        if (locationdtlList == null) {
            locationdtlList = new ArrayList<>();
        }
        return locationdtlList;
    }

    public void setLocationdtlList(List<MmsManageLocationDtl> locationdtlList) {
        this.locationdtlList = locationdtlList;
    }

    public MmsManageLocationDtl getSelectedLocation() {
        return selectedLocation;
    }

    public void setSelectedLocation(MmsManageLocationDtl selectedLocation) {
        this.selectedLocation = selectedLocation;
    }

    public List<MmsManageLocationDtl> getAllLocationInfoList() {
        if (allLocationInfoList == null) {
            allLocationInfoList = new ArrayList<>();
        }
        return allLocationInfoList;
    }

    public void setAllLocationInfoList(List<MmsManageLocationDtl> allLocationInfoList) {
        this.allLocationInfoList = allLocationInfoList;
    }
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Search, Event Handlers and other Methods">

    public void goBackSearchButtonAction() {
        renderpnlToSearchPage = false;
        renderPnlCreateLocationissuance = false;
        renderPnlManPage = true;
    }

    public void createNewLocationIssuance() {

        clearPage();
        saveorUpdateBundle = "Save";

//        renderPnlCreateLocationissuance = true;
//
//        renderPnlManPage = false;
//        createOrSearchBundle = "Search";
//        approvedSivList = sivInterface.approvedSivList();
//        setCreateOrSearchBundle(createOrSearchBundle);
//        setIcone("ui-icon-search");
        renderpnlToSearchPage = false;
        if (createOrSearchBundle.equals("New")) {
            renderPnlCreateLocationissuance = true;
            renderPnlManPage = false;
            approvedSivList = sivInterface.approvedSivList();
            createOrSearchBundle = "Search";
            setIcone("ui-icon-search");
            //generateGatePassNo();
        } else if (createOrSearchBundle.equals("Search")) {
            renderPnlCreateLocationissuance = false;
            renderPnlManPage = true;
            createOrSearchBundle = "New";
            setIcone("ui-icon-plus");
        }
    }

    public void btn_Search_Action() {
        renderPnlCreateLocationissuance = false;
        renderPnlManPage = true;
    }

    public void changeCheckIssuedType(ValueChangeEvent e) {
        mmsManageLocationEntity.setSelectOption(checkType);
        if (null != e.getNewValue()) {
            if (e.getNewValue().toString().equals("SIV")) {

                setRenderSiv(true);

                setRenderIsivIssuance(false);
//                unallocatedQuantity=0;
                mmsManageLocationEntity.setSelectOption(checkType);
                System.out.println("select option====" + mmsManageLocationEntity.getSelectOption());
            } else if (e.getNewValue().toString().equals("IssuedISIV")) {
                System.out.println("============WHen option equals IssuedISIV ===");
                //approvedIsivList = isivInterface.approvedIsivIssuedList();

                setRenderIsivIssuance(true);
                setRenderSiv(false);

                //    unallocatedQuantity=0;
                mmsManageLocationEntity.setSelectOption(checkType);
                System.out.println("select option====" + mmsManageLocationEntity.getSelectOption());
            }

        }
    }

    public void handleSelectSivNo(ValueChangeEvent event) {
        if (null != event.getNewValue()) {
//          

            manageLocationDetailDataModel = null;
            sivEntity = (MmsSiv) event.getNewValue();

            getSIVMaterialList();
            System.out.println("======getSIVMaterialList==========" + getSIVMaterialList());

        }

    }

    public void handleMaterialCodeListSiv(ValueChangeEvent event) {
        if (null != event.getNewValue()) {
            String code = event.getNewValue().toString();
            System.out.println("======value=========" + code);

            itemRegEntity.setMatCode(code);
            //  itemRegEntity = itemRegistrationInterface.SearchInfoByMatcode(itemRegEntity,);
            //mmsManageLocationEntity.setMaterialId(itemRegEntity);

//         grnDetailEntity.setReceivedQuantity(itemRegEntity.getMmsGrnDetail().get(0).getReceivedQuantity());
            //setUnallocatedQuantity(grnDetailEntity.getReceivedQuantity());
            setDisableSivMatCodeList(true);
            setDisableSivNoList(true);

        }
    }

    public void handleSelectIsivNo(ValueChangeEvent event) {
        if (null != event.getNewValue()) {
//        

            manageLocationDetailDataModel = null;
            isivEntity = (MmsIsiv) event.getNewValue();

            getISIVMaterialList();

//            
        }

    }

//    public List<MmsShelfInfo> getShelfLists() {
//        // storeInfoEntity=storeInfoInterface.getPapmsStoreInformation(storeInfoEntity);
//
//        if ("0".equals(CheckWarehouse)) {
//            ShelfList = mmsShelfInfoInterface.searchShelfByStoreId(storeInfoEntity);
////                System.out.println("======Listis===="+mmsShelfInfoDtlInterface.getShelf(storeInfoEntity));
//            return ShelfList;// JsfUtil.getSelectItems(mmsShelfInfoDtlInterface.getShelf(storeInfoEntity), true);
//        } else if ("1".equals(CheckWarehouse)) {
//            ShelfList = mmsShelfInfoInterface.searchWarehouseShelfInformation(mmsShelfInfoEntity);
//            return ShelfList;
//        }
//
//        return null;
//
//    }
    public List<MmsShelfInfo> getShelfLists() {
        // storeInfoEntity=storeInfoInterface.getPapmsStoreInformation(storeInfoEntity);
        //store=0; shade=1;
        //rack=0,outdoor=1;
        if ("0".equals(CheckWarehouse)) {
            if (selectOption2.equals("0")) {
                System.out.println("=========store@controller========" + storeInfoEntity.getStoreId());
//                ShelfList = mmsShelfInfoInterface.searchShelfByStoreId(storeInfoEntity);
                System.out.println("=========shelf list=====" + ShelfList.size());
            } else {
                System.out.println("=======Insdie outdoor code search=======");
                ShelfList = mmsShelfInfoInterface.searchStoreOutdoor(storeInfoEntity);
            }

//                System.out.println("======Listis===="+mmsShelfInfoDtlInterface.getShelf(storeInfoEntity));
            return ShelfList;// JsfUtil.getSelectItems(mmsShelfInfoDtlInterface.getShelf(storeInfoEntity), true);
        } else if ("1".equals(CheckWarehouse)) {

            if (selectOption2.equals("0")) {
                System.out.println("=========store@controller========" + storeInfoEntity.getStoreId());
                ShelfList = mmsShelfInfoInterface.searchClosedShadeShelfByStoreIdAndShadeName(storeInfoEntity, mmsShelfInfoEntity, 0);
                System.out.println("=========shelf list=====" + ShelfList.size());
            } else {
                System.out.println("=======Insdie outdoor code search=======");
                ShelfList = mmsShelfInfoInterface.searchStoreOutdoor(storeInfoEntity);
            }

            return ShelfList;
        }

        return null;

    }

    public List<MmsLocationInfo> getShelfCells() {

        if ("0".equals(CheckWarehouse)) {
            cellList = mmsCellInfoInterface.searchCellByShelfId(mmsShelfInfoEntity);

//                System.out.println("======Listis===="+mmsShelfInfoDtlInterface.getShelf(storeInfoEntity));
            return cellList;// JsfUtil.getSelectItems(mmsShelfInfoDtlInterface.getShelf(storeInfoEntity), true);
        } else {
            cellList = mmsCellInfoInterface.searchCellByShelfId(mmsShelfInfoEntity);

        }
        return cellList;
    }

    public void changeCheckRecievableType(ValueChangeEvent e) {
        clearAllPage();
        mmsManageLocationEntity.setSelectOption(checkType);
        if (null != e.getNewValue()) {
            if (e.getNewValue().toString().equals("SIV")) {
                setRenderSiv(true);
                setRenderIsivIssuance(false);
                clearAllPage();
                //mmsManageLocationEntity.setSelectOption(checkType);
            } else if (e.getNewValue().toString().equals("ISSUEDISIV")) {
                System.out.println("============WHen option equals ISSUEDISIV ===");
                setRenderIsivIssuance(true);
                setRenderSiv(false);
                // mmsManageLocationEntity.setSelectOption(checkType);
                clearPage();
            }

        }
    }

    public void handleSelectStoreNameForSearch(ValueChangeEvent event) {
        if (null != event.getNewValue()) {
            storeInfoEntity = (MmsStoreInformation) event.getNewValue();
        }
    }

    public void handleSelectStoreName(ValueChangeEvent event) {
        System.out.println("======Inside a method fffffff========");
        if (null != event.getNewValue()) {
            System.out.println("===========event==" + event.getNewValue());
            storeInfoEntity = (MmsStoreInformation) event.getNewValue();
            if (checkType.equals("SIV")) {
                approvedSivList = sivInterface.approvedStoreSivList(storeInfoEntity, Constants.APPROVE_VALUE);
                System.out.println("=siv list size=====" + approvedSivList);

            } else {
                approvedIsivList = isivInterface.approvedIsivListByWfStatusAndStoreId(storeInfoEntity, Constants.APPROVE_VALUE);
                System.out.println("=ISSiv list size=====" + approvedIsivList);
            }
//            papmsItemRegistration.setStoreId(storeInfoEntity);

//         
        }

    }

    public void handleSelectMaterial(ValueChangeEvent event) {
        if (null != event.getNewValue()) {

            switch (checkType) {
                case "SIV":
                    itemRegEntity = (MmsItemRegistration) event.getNewValue();
                    mmsManageLocationDtlEntity.setTotalQuantity(itemRegEntity.getMmsSivDetailList().get(0).getQuantity());
                    System.out.println("==============quantity2======" + mmsManageLocationDtlEntity.getTotalQuantity());
                    locationdtlList = mmsmanageLocationDtlInterface.getByStoreAndMaterialId(itemRegEntity, storeInfoEntity);
                    mmsManageLocationDtlEntity.setLocationQuantity(locationdtlList.get(0).getLocationQuantity());
                    System.out.println("============location mat======" + locationdtlList.size());
                    mmsManageLocationDtlEntity.setId(locationdtlList.get(0).getId());
                    mmsManageLocationDtlEntity = mmsmanageLocationDtlInterface.findByDtlId(mmsManageLocationDtlEntity);
                    System.out.println("master id1  " + mmsManageLocationDtlEntity.getManageLocationId().getId());
                    mmsManageLocationEntity.setId(mmsManageLocationDtlEntity.getManageLocationId().getId());
                    mmsManageLocationDtlEntity.setManageLocationId(locationdtlList.get(0).getManageLocationId());
                    System.out.println("master id " + mmsManageLocationEntity.getId());

                    if (locationdtlList.size() <= 0) {
                        JsfUtil.addFatalMessage("Item Not Found");
                    } else {
                        recreateLocationModel();
//                        setDisableSivNoList(true);
                        mmsManageLocationDtlEntity.setMaterialId(itemRegEntity);
                    }
                    break;
                case "ISSUEDISIV":
                    System.out.println("==========Inside isiv=============");
                    itemRegEntity = (MmsItemRegistration) event.getNewValue();
                    mmsManageLocationDtlEntity.setTotalQuantity(itemRegEntity.getMmsIsivDetailList().get(0).getQuantity());
                    System.out.println("Qty " + mmsManageLocationDtlEntity.getTotalQuantity());
                    locationdtlList = mmsmanageLocationDtlInterface.getByStoreAndMaterialId(itemRegEntity, storeInfoEntity);
//                    mmsManageLocationDtlEntity = mmsmanageLocationDtlInterface.getItemInfo(itemRegEntity,storeInfoEntity);
//                    System.out.println("when I "+mmsManageLocationDtlEntity.getManageLocationId().getId());
                    mmsManageLocationDtlEntity.setLocationQuantity(locationdtlList.get(0).getLocationQuantity());
                    System.out.println("============location mat======" + locationdtlList.size());
                    mmsManageLocationDtlEntity.setId(locationdtlList.get(0).getId());
                    mmsManageLocationDtlEntity = mmsmanageLocationDtlInterface.findByDtlId(mmsManageLocationDtlEntity);
                    System.out.println(" master id1  " + mmsManageLocationDtlEntity.getManageLocationId().getId());
                    mmsManageLocationEntity.setId(mmsManageLocationDtlEntity.getManageLocationId().getId());
                    mmsManageLocationDtlEntity.setManageLocationId(locationdtlList.get(0).getManageLocationId());
                    System.out.println("master id " + mmsManageLocationEntity.getId());
                    if (locationdtlList.size() <= 0) {
                        JsfUtil.addFatalMessage("Item Not Found");
                    } else {
                        recreateLocationModel();
                        setDisableIsivNoList(true);
                        mmsManageLocationDtlEntity.setMaterialId(itemRegEntity);
                    }
                    break;
            }

        }
    }

    public void recreateLocationModel() {
        LocationDetailDataModel = null;
        LocationDetailDataModel = new ListDataModel(locationdtlList);
    }

    public void enabledisableWarehouse(ValueChangeEvent ev) {
        if (null != ev.getNewValue()) {
            System.out.println("event inside enable" + ev.getNewValue().toString());
            if (ev.getNewValue().toString().equalsIgnoreCase("0")) {

                setRenderWareHouseOneMenu(false);

            } else if (ev.getNewValue().toString().equalsIgnoreCase("1")) {
                setRenderWareHouseOneMenu(true);

            }

        }
    }

    public void handleSelectWarehouseName(ValueChangeEvent event) {
        if (null != event.getNewValue()) {

            System.out.println("==========inside warhouse handler store Id====" + mmsShelfInfoEntity.getStoreId());
            System.out.println("==========inside warhouse handler warhouse Name====" + mmsShelfInfoEntity.getShadeName());

        }

    }

    public void handleSelectOption2(ValueChangeEvent event) {
        if (null != event.getNewValue()) {
            System.out.println("event value===========" + event.getNewValue().toString());
            if ("0".equals(event.getNewValue().toString())) {
                //option 0 is for Rack
                setRenderRackCode(true);
                setRenderOutdoorInfo(false);
                mmsManageLocationDtlEntity.setSelectOption2(0);
                System.out.println("===========inside if====");
                // mmsShelfInfoEntity.setSelectOption2(0);
            } else {
                //option 1 is for outdoor
                setRenderRackCode(false);
                setRenderOutdoorInfo(true);
                mmsManageLocationDtlEntity.setSelectOption2(1);
                System.out.println("===========inside else====");
                // mmsShelfInfoEntity.setSelectOption2(1);
            }
        }
    }

    //Handle Selection of ShelfCode
    public void handleSelectShelfCode(ValueChangeEvent event) {
        if (null != event.getNewValue()) {
            mmsShelfInfoEntity = (MmsShelfInfo) event.getNewValue();
            // mmsShelfInfoEntity.setShelfId(Integer.parseInt(event.getNewValue().toString()));
            System.out.println("===========inside shelf-------------" + mmsShelfInfoEntity.getShelfId());
            mmsCellInfoEntity.setShelfId(mmsShelfInfoEntity);
            shelfId = mmsCellInfoEntity.getShelfId().getShelfId();

        }

    }

    //Handle Selection of CellCode
    public void handleSelectCellCode(ValueChangeEvent event) {
        if (null != event.getNewValue()) {
            mmsCellInfoEntity = (MmsLocationInfo) event.getNewValue();
            cellId = mmsCellInfoEntity.getLocationId();
            mmsManageLocationDtlEntity.setCellId(mmsCellInfoEntity);
//            locationInfo = mmsmanageLocationDtlInterface.searchLocationInfoByCellId(mmsManageLocationDtlEntity);
//            recreatecurrentLocModel();
            //mmsManageLocationDtlEntity.setCellId(mmsCellInfoEntity);
            //mmsCellInfoEntity.setLocationId(Integer.parseInt(event.getNewValue().toString()));

        }

    }

    public String clearAllPage() {
        mmsManageLocationEntity = new MmsManageLocation();
        mmsManageLocationDtlEntity = new MmsManageLocationDtl();

        mmsShelfInfoEntity = new MmsShelfInfo();

        mmsCellInfoDtlEntity = new MmsLocationInfoDtl();
        mmsCellInfoEntity = new MmsLocationInfo();
        manageLocationDetailDataModel = null;
        LocationDetailDataModel = null;
        deductionDataModel = null;
        itemRegEntity = new MmsItemRegistration();
        mmsManageLocationTempEntity = new MmsManageLocation();
        storeInfoEntity = new MmsStoreInformation();
        updateStatus = 0;
        wfMmsProcessed = null;
        sivEntity = null;

        return null;
    }

    public void edit(SelectEvent event) {
        setRowIndex(LocationDetailDataModel.getRowIndex());
        System.out.println("======rowselction edit =====");
        AddorModify = "Modify";
        // editRowData = 1;
        mmsManageLocationDtlEntity = LocationDetailDataModel.getRowData();
        mmsShelfInfoEntity = mmsManageLocationDtlEntity.getShelfId();
        mmsCellInfoEntity = mmsManageLocationDtlEntity.getCellId();
        itemRegEntity = mmsManageLocationDtlEntity.getMaterialId();
        mmsManageLocationTempEntity = mmsManageLocationDtlEntity.getManageLocationId();
        //itemInfoList = new ArrayList<>();
        //itemInfoList.add(itemRegEntity);
//        if (mmsManageLocationDtlEntity.getSelectOption1() == 0) {
//            setCheckWarehouse("0");
//            if (mmsManageLocationDtlEntity.getSelectOption2() == 0) {
//                setSelectOption2("0");
//                setRenderRackCode(true);
//                setRenderOutdoorInfo(false);
//            } else {
//                setSelectOption2("1");
//                setRenderOutdoorInfo(true);
//                setRenderRackCode(false);
//            }
//        } else {
//            setCheckWarehouse("1");
//            if (mmsManageLocationDtlEntity.getSelectOption2() == 0) {
//                setSelectOption2("0");
//                setRenderRackCode(true);
//                setRenderOutdoorInfo(false);
//            } else {
//                setSelectOption2("1");
//                setRenderOutdoorInfo(true);
//                setRenderRackCode(false);
//            }
//        }
        setDisableBtnAdd(false);

    }

    public void edit2(SelectEvent event) {
        setRowIndex(deductionDataModel.getRowIndex());
        System.out.println("========selction row edit2");
        AddorModify = "Modify";
        // editRowData = 1;
        mmsManageLocationDtlEntity = deductionDataModel.getRowData();
        mmsShelfInfoEntity = mmsManageLocationDtlEntity.getShelfId();
        mmsCellInfoEntity = mmsManageLocationDtlEntity.getCellId();
        itemRegEntity = mmsManageLocationDtlEntity.getMaterialId();
        mmsManageLocationTempEntity = mmsManageLocationDtlEntity.getManageLocationId();
        itemInfoList = new ArrayList<>();
        itemInfoList.add(itemRegEntity);
        if (updateStatus == 1) {
            readOnlyquantiy = true;
            readOnlyDeductquantiy = true;
        }

//        if (mmsManageLocationDtlEntity.getSelectOption1() == 0) {
//            setCheckWarehouse("0");
//            if (mmsManageLocationDtlEntity.getSelectOption2() == 0) {
//                setSelectOption2("0");
//                setRenderRackCode(true);
//                setRenderOutdoorInfo(false);
//            } else {
//                setSelectOption2("1");
//                setRenderOutdoorInfo(true);
//                setRenderRackCode(false);
//            }
//        } else {
//            setCheckWarehouse("1");
//
//            if (mmsManageLocationDtlEntity.getSelectOption2() == 0) {
//                setSelectOption2("0");
//                setRenderRackCode(true);
//                setRenderOutdoorInfo(false);
//            } else {
//                setSelectOption2("1");
//                setRenderOutdoorInfo(true);
//                setRenderRackCode(false);
//            }
//        }
        if (updateStatus == 0) {
            update = 1;
            deductionList.get(rowIndex).setLocationQuantity(deductionList.get(rowIndex).getLocationQuantity() + deductionList.get(rowIndex).getDeductedQuantity());
        }

        setDisableBtnAdd(false);
    }

    int update = 0;

    public void addDetail() {
        if (updateStatus == 0) {
            if (update == 0) {
                if (checkMaterialId.contains(mmsManageLocationDtlEntity.getMaterialId()) && checkcellId.contains(mmsManageLocationDtlEntity.getCellId()) && checkshelfId.contains(mmsManageLocationDtlEntity.getShelfId())) {
                    JsfUtil.addFatalMessage("Material Already exists");

                } else {
                    if (mmsManageLocationDtlEntity.getDeductedQuantity() > mmsManageLocationDtlEntity.getLocationQuantity() || mmsManageLocationDtlEntity.getDeductedQuantity() == 0) {
                        JsfUtil.addFatalMessage("Not Enough Quantity On the location");

                    } else {
//                        System.out.println("====ID MASTER=======" + mmsManageLocationTempEntity.getId());
//                        System.out.println("before find");
//                        mmsManageLocationEntity = mmsManageLocationInterface.findId(mmsManageLocationTempEntity);
//////
//                        System.out.println("========after searching=========" + mmsManageLocationEntity.getId());
                        System.out.println("Loc Qty " + mmsManageLocationDtlEntity.getLocationQuantity());
                        System.out.println("master ID " + mmsManageLocationDtlEntity.getManageLocationId().getId());
                        System.out.println("DtL ID " + mmsManageLocationEntity.getId());
                        mmsManageLocationDtlEntity.setLocationQuantity(mmsManageLocationDtlEntity.getLocationQuantity() - mmsManageLocationDtlEntity.getDeductedQuantity());
//                        mmsManageLocationEntity = mmsManageLocationInterface.findId(mmsManageLocationTempEntity);
                        deductionList.add(mmsManageLocationDtlEntity);
                        checkMaterialId.add(mmsManageLocationDtlEntity.getMaterialId());
                        checkcellId.add(mmsManageLocationDtlEntity.getCellId());
                        checkshelfId.add(mmsManageLocationDtlEntity.getShelfId());
                        recreateDeductionModel();

                        clearPage1();
                    }
                }
            } else {
                if (mmsManageLocationDtlEntity.getDeductedQuantity() > mmsManageLocationDtlEntity.getLocationQuantity() || mmsManageLocationDtlEntity.getDeductedQuantity() == 0) {
                    JsfUtil.addFatalMessage("Not Enough Quantity On the location");

                } else {
                    System.out.println("====ID MASTER2=======" + mmsManageLocationTempEntity.getId());
                    mmsManageLocationEntity = mmsManageLocationInterface.findId(mmsManageLocationTempEntity);

                    System.out.println("========after searching2=========" + mmsManageLocationEntity.getId());
                    mmsManageLocationDtlEntity.setLocationQuantity(mmsManageLocationDtlEntity.getLocationQuantity() - mmsManageLocationDtlEntity.getDeductedQuantity());
                    deductionList.get(rowIndex).setLocationQuantity(deductionList.get(rowIndex).getLocationQuantity() - mmsManageLocationDtlEntity.getDeductedQuantity());
                    deductionList.add(mmsManageLocationDtlEntity);

                    recreateDeductionModel();

                    clearPage();
                }
            }
        } else {
            //if update status is 1 and if the store man wants to adjust the location quantity;
            System.out.println("=========inside update status =1 else================");
            mmsManageLocationEntity = mmsManageLocationInterface.findId(mmsManageLocationTempEntity);
            System.out.println("=============Row Index Quantity========" + deductionList.get(rowIndex).getLocationQuantity());
            System.out.println("=============Detail Quantity========" + mmsManageLocationDtlEntity.getQuantityAdjustment());
            deductionList.get(rowIndex).setLocationQuantity(mmsManageLocationDtlEntity.getQuantityAdjustment());
            recreateDeductionModel();

            clearPage();
        }
    }

    public void recreateDeductionModel() {
        System.out.println("deduction list=======" + deductionList);
        deductionDataModel = null;
        deductionDataModel = new ListDataModel(deductionList);
    }

    private void clearPage() {
        System.out.println("====insdie ClearPopup=====");
     // storeInfoEntity=null;

        // mmsShelfInfoEntity=null;
//        ShelfList.clear();
//        cellList.clear();
        mmsShelfInfoEntity = new MmsShelfInfo();

        mmsCellInfoEntity = new MmsLocationInfo();
        mmsCellInfoDtlEntity = new MmsLocationInfoDtl();
        itemRegEntity = new MmsItemRegistration();
        mmsManageLocationDtlEntity = new MmsManageLocationDtl();
//        locationDetailDataModel = new ListDataModel<>();
        deductionDataModel = new ListDataModel<>();

    }

    private void clearPage1() {
        System.out.println("====insdie ClearPopup=====");
     // storeInfoEntity=null;

        // mmsShelfInfoEntity=null;
//        ShelfList.clear();
//        cellList.clear();
        mmsShelfInfoEntity = new MmsShelfInfo();
        mmsManageLocationDtlEntity = new MmsManageLocationDtl();
        mmsCellInfoEntity = new MmsLocationInfo();
        mmsCellInfoDtlEntity = new MmsLocationInfoDtl();
        itemRegEntity = new MmsItemRegistration();

    }

//    public void addGrnDetail(MmsManageLocationDtl mmsManageLocationDtlEntity) {
//        if (mmsManageLocationDtlEntity.getManageLocationId() != mmsManageLocationEntity) {
//            deductionList.add(mmsManageLocationDtlEntity);
//           
//            mmsManageLocationDtlEntity.setManageLocationId(mmsManageLocationEntity);
//        }
//    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Save Method">
    public void WfSave() {
        //wfMmsProcessed.setLocationId(mmsCellInfoEntity);
    }

    public void saveItemLocationIssuance() {
        try {
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(SessionBean.getUserName(), "saveItemLocationIssuance", dataset)) {
                if (deductionList.size() <= 0) {
                    JsfUtil.addFatalMessage("Saving Without data is Not Allowed");
                } else {
                    System.out.println("before save" + mmsManageLocationEntity.getId());
                    mmsManageLocationEntity = mmsManageLocationInterface.findId(mmsManageLocationEntity);
//
                    System.out.println("========after searching=========" + mmsManageLocationEntity.getId());
                    CheckListForEquality();
                    if (result) {
                        System.out.println("=====result===" + result);
                        for (MmsManageLocationDtl deductionList2 : deductionList) {
                            mmsmanageLocationDtlInterface.edit(deductionList2);
                        }
                        switch (checkType) {
                            case "SIV":
                                sivEntity.setStatus(Constants.PREPARE_REJECT_VALUE);
                                sivInterface.edit(sivEntity);
                                break;
                            case "ISSUEDISIV":
                                isivEntity.setStatusWf(Constants.PREPARE_REJECT_VALUE);
                                isivInterface.edit(isivEntity);
                                break;
                        }

                        JsfUtil.addSuccessMessage("Location Quantity Updated");
                        clearAllPage();
                    } else {
                        JsfUtil.addFatalMessage("All Items Should Be processed");
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

        }
    }

    public boolean CheckListForEquality() {
        System.out.println("detail size--- " + mmsManageLocationEntity.getMmsManageLocationDtlList().size());
        for (int i = 0; i < mmsManageLocationEntity.getMmsManageLocationDtlList().size(); i++) {
            System.out.println("mat Ids --  " + mmsManageLocationEntity.getMmsManageLocationDtlList().get(i).getMaterialId());
            detailLists.add(mmsManageLocationEntity.getMmsManageLocationDtlList().get(i).getMaterialId());
        }
        System.out.println("=======detail size=====" + detailLists.size());
        System.out.println("======item info size===" + itemInfoList.size());
        result = detailLists.containsAll(itemInfoList);
        System.out.println("============result===========" + result);
        return result;
    }

    public void searchLocationInformation() {

        if (storeInfoEntity.getStoreId() != null && itemRegEntity.getMatCode().isEmpty() && itemRegEntity.getMatName().isEmpty()) {
            System.out.println("==========inside By store===" + storeInfoEntity.getStoreId());
            allLocationInfoList = mmsmanageLocationDtlInterface.searchLocationInfoByStore(storeInfoEntity);
            //allLocationList=mmsManageLocationInterface.searchBYStoreId(storeInfoEntity);
//            System.out.println("all loc size==============="+allLocationList.size());
//            for(int i=0; i<allLocationList.size();i++){
//                System.out.println("============ det size="+allLocationList.get(i).getMmsManageLocationDtlList().size());                
//                getAllLocationInfoList().addAll(allLocationList.get(i).getMmsManageLocationDtlList());
//                System.out.println("============ all size="+getAllLocationInfoList().size());
//            }
//            
            recerateSearchModel();
//            recerateSearchModel1();

        } else if (storeInfoEntity.getStoreId() != null && itemRegEntity.getMatName().isEmpty() && !itemRegEntity.getMatCode().isEmpty()) {
            System.out.println("search By Code=====");
            // itemRegEntity.setStoreId(storeInfoEntity);
            setAllLocationInfoList(mmsmanageLocationDtlInterface.searchByStoreAndItemCode(itemRegEntity, storeInfoEntity));
            System.out.println("==============llist=======" + getAllLocationInfoList().get(0).getMaterialId().getMatCode());
            // allLocationInfoList = binCardInterface.searchBinCardInfoByStore(storeInfoEntity);

            recerateSearchModel();
        } else if (storeInfoEntity.getStoreId() != null && itemRegEntity.getMatCode().isEmpty() && !itemRegEntity.getMatName().isEmpty()) {
            System.out.println("search By name=====");
            itemRegEntity.setStoreId(storeInfoEntity);
            setAllLocationInfoList(mmsmanageLocationDtlInterface.searchByStoreAndItemName(itemRegEntity));

            recerateSearchModel();
        }

    }

    private void recerateSearchModel() {
        mmsLocationDtlSearchInfoDataModel = null;
        mmsLocationDtlSearchInfoDataModel = new ListDataModel(new ArrayList<>(allLocationInfoList));

    }

    public void onRowEdit(SelectEvent event) {
        //MmsManageLocationDtl managelocdtl;
        mmsManageLocationDtlEntity = (MmsManageLocationDtl) event.getObject();
        setRenderPanelSave(false);

        mmsManageLocationEntity = mmsManageLocationDtlEntity.getManageLocationId();
        renderPnlCreateLocationissuance = true;

        renderPnlManPage = false;
        renderPanelSave = true;
        renderpnlToSearchPage = true;
        activeIndex = "0";
        saveorUpdateBundle = "Update";
        createOrSearchBundle = "New";
        setCreateOrSearchBundle(createOrSearchBundle);
        setIcone("ui-icon-plus");

        updateStatus = 1;
        setRenderPreparerInfo(false);
        deductionList = new ArrayList<>();
//        approvedSivList = new ArrayList<>(); 
//                getSIVMaterialList = new ArrayList<>();
//        System.out.println("================siv No=======" + sivEntity.getSivNo());
//        System.out.println("=================list" + approvedSivList);
        approvedSivList = sivInterface.approvedStoreSivList(storeInfoEntity, Constants.APPROVE_VALUE);
        System.out.println("=================list" + approvedSivList);
        sivEntity.getSivNo();
        System.out.println("================siv No=======" + sivEntity.getSivNo());
        deductionList.add(mmsManageLocationDtlEntity);
        recreateDeductionModel();

        //recreateDtlList();
    }
//</editor-fold>
}
