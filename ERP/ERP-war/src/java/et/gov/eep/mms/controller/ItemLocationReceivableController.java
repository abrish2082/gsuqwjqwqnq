/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.mms.controller;

import java.math.BigDecimal;
import java.util.Objects;
import javax.annotation.PostConstruct;
import javax.faces.model.SelectItem;
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.ejb.EJB;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import securityBean.Constants;
import securityBean.SessionBean;
import securityBean.WorkFlow;
import webService.AAA;
import webService.EventEntry;
import webService.IAdministration;
import org.primefaces.event.SelectEvent;
import et.gov.eep.commonApplications.entity.WfMmsProcessed;
import et.gov.eep.mms.businessLogic.MmsGRNBEanLocal;
import et.gov.eep.mms.businessLogic.MmsGrnDetailBeanLocal;
import et.gov.eep.mms.businessLogic.MmsIsivBeanLocal;
import et.gov.eep.mms.businessLogic.MmsItemRegisrtationBeanLocal;
import et.gov.eep.mms.businessLogic.MmsLocationInfoBeanLocal;
import et.gov.eep.mms.businessLogic.MmsManageLocationBeanLocal;
import et.gov.eep.mms.businessLogic.MmsRmgBeanLocal;
import et.gov.eep.mms.businessLogic.MmsShelfInfoBeanLocal;
import et.gov.eep.mms.businessLogic.MmsStoreInformationBeanLocal;
import et.gov.eep.mms.entity.MmsGrn;
import et.gov.eep.mms.entity.MmsGrnDetail;
import et.gov.eep.mms.entity.MmsIsiv;
import et.gov.eep.mms.entity.MmsIsivDetail;
import et.gov.eep.mms.entity.MmsItemRegistration;
import et.gov.eep.mms.entity.MmsLocationInfo;
import et.gov.eep.mms.entity.MmsLocationInfoDtl;
import et.gov.eep.mms.entity.MmsManageLocation;
import et.gov.eep.mms.entity.MmsManageLocationDtl;
import et.gov.eep.mms.entity.MmsRmg;
import et.gov.eep.mms.entity.MmsShelfInfo;
import et.gov.eep.mms.entity.MmsStoreInformation;
import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.mms.businessLogic.ManageLocationDtlBeanLocal;
import et.gov.eep.mms.businessLogic.MmsIsivReceivedBeanLocal;
import et.gov.eep.mms.businessLogic.MmsLuOutdoorTypeBeanLocal;
import et.gov.eep.mms.entity.MmsIsivReceived;
import et.gov.eep.mms.entity.MmsIsivReceivedDtl;
import et.gov.eep.mms.entity.MmsRmgDetail;

/**
 *
 * @author Minab
 */
@Named(value = "itemLocationReceivableController")
@ViewScoped
public class ItemLocationReceivableController implements Serializable {

    //<editor-fold defaultstate="collapsed" desc="EJB's">
    @EJB
    private MmsGRNBEanLocal mmsGrnBeanLocal;
    @EJB
    private MmsGrnDetailBeanLocal mmsGrnDetailInterface;
    @EJB
    private MmsIsivBeanLocal isivInterface;
    @EJB
    private MmsIsivReceivedBeanLocal isivReceivedInterface;
    @EJB
    private MmsRmgBeanLocal rmgInterface;
    @EJB
    private MmsItemRegisrtationBeanLocal itemRegistrationInterface;
    @EJB
    private MmsStoreInformationBeanLocal storeInfoInterface;
    @EJB
    MmsShelfInfoBeanLocal mmsShelfInfoInterface;

    @EJB
    MmsLocationInfoBeanLocal mmsCellInfoInterface;
    @EJB
    MmsManageLocationBeanLocal mmsManageLocationInterface;
    @EJB
    ManageLocationDtlBeanLocal mmsmanageLocationDtlInterface;
    @EJB
    MmsLuOutdoorTypeBeanLocal outdoorTypeBeanLocal;
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Entity's">
    @Inject
    WfMmsProcessed wfMmsProcessed;
    @Inject
    private MmsManageLocation mmsManageLocationEntity;
    @Inject
    private MmsManageLocationDtl mmsManageLocationDtlEntity;
    @Inject
    private MmsManageLocationDtl managelocdtl;
    @Inject
    private MmsIsivReceived isivReceivedEntity;
    @Inject
    private MmsGrn grnEntity;
    @Inject
    private MmsIsiv isivEntity;
    @Inject
    private MmsIsivDetail isivDetailEntity;
    @Inject
    private MmsIsivReceivedDtl isivReceivedetailEntity;
    @Inject
    private MmsRmg rmgEntity;
    @Inject
    private MmsRmgDetail rmgDtlEntity;
    @Inject
    private MmsItemRegistration itemRegEntity;
    @Inject
    private MmsGrnDetail grnDetailEntity;
    @Inject
    private MmsStoreInformation storeInfoEntity;
    @Inject
    private MmsShelfInfo mmsShelfInfoEntity;

    @Inject
    private MmsLocationInfo mmsCellInfoEntity;
    @Inject
    private MmsLocationInfo mmsTempCellInfoEntity;
    @Inject
    private MmsLocationInfoDtl mmsCellInfoDtlEntity;
    @Inject
    SessionBean SessionBean;
    @Inject
    WorkFlow workflow;

    // @EJB
    //private Mmsgr grnDetailBeanLocal;
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Fields">
    private DataModel<MmsManageLocationDtl> manageLocationDetailDataModel;
    private DataModel<MmsManageLocationDtl> currentLocationInfoDataModel;
    private DataModel<MmsManageLocation> mmsLocationSearchInfoDataModel;
    private DataModel<MmsManageLocationDtl> mmsLocSearchInfoDataModel;
    private BigDecimal unallocatedQuantity = BigDecimal.ZERO;
    private String saveorUpdateBundle = "Save";
    private String checkType = "GRN";
    private String CheckWarehouse = "0";
    private String selectOption2;
    private String storeName;
    private boolean renderWareHouseOneMenu = false;
    private boolean disableBtnCreate;
    private String createOrSearchBundle = "New";
    private boolean renderPnlCreateLocationRecievable = false;
    private boolean renderPnlManPage = true;
    private boolean renderGrn = true;
    private boolean renderRMG = false;
    private boolean renderISIV = false;
    private boolean renderCellCode = false;
    private boolean renderOutdoorInfo = false;
    private boolean renderRackCode = false;
    private boolean disableMatCodeList = false;
    private boolean disableISIVMatCodeList = false;
    private boolean disableRMGMatCodeList = false;
    private boolean disableGrnList = false;
    private boolean disableIsivNoList = false;
    private boolean disableRmgNoList = false;
    private boolean renderForClosedShade = false;
    private boolean renderpnlToSearchPage;
    private String icone = "ui-icon-plus";
    private String activeIndex;
    Set<MmsItemRegistration> checkMaterialId = new HashSet<>();
    Set<MmsLocationInfo> checkcellId = new HashSet<>();
    Set<MmsShelfInfo> checkshelfId = new HashSet<>();
    int updateStatus = 0;
    private int rowIndex;
    private String outdoorNameBundle = "Code:";
    private final String nameBundleForField = "Field Code:";
    private final String nameBundleForOpenShade = "Open Shade Code:";
    private final String nameBundleForRack = "Rack Code:";
    private final String nameBundleForClosedShade = "Closed Shade Code:";
    private final int valueForRack = 0;
    private final int valueForOutdoor = 1;
    private final String defaultOutdoorBundleName = "Code:";
    private final int OutdoorValue = 1;
    private final int hasAclosedShadeArackValue = 0;
    private final int storerackValue = 0;
    private static final String expiryItem = "Expiry";
    private boolean renderForExpiry = false;

    private List<MmsShelfInfo> outdoorCodeList;
    private List<MmsLocationInfo> cellList;
    private List<MmsLocationInfo> cellRowList;
    private String userName;
    private String AddorModify = "Add";
    //    int result = 0;
    int index = 0;
    private List<MmsManageLocationDtl> recreateList;
    private MmsManageLocation selectedLocation;
    boolean result;
    List<MmsManageLocationDtl> locationList = new ArrayList<>();
    List<MmsItemRegistration> detailLists = new ArrayList<>();
    //Add dato to data Table
    int addedQuantity = 0;
    int flag = 0;
    List<MmsManageLocationDtl> listdtl = new ArrayList<>();
    int deductedQuantity = 0;
    int PreviousQuantity = 0;
    Integer cellId;
    List<MmsManageLocationDtl> locationInfo = new ArrayList<>();
    Integer shelfId;
//Get Shelf Lists based on store
    private List<MmsShelfInfo> ShelfList;
    List<MmsStoreInformation> StoreList;
    //get warehouse List
    List<MmsShelfInfo> closedShadeList = new ArrayList<>();
    ArrayList<MmsManageLocation> masterISIV = new ArrayList();
    Integer materialId;
    Integer GrnmaterialId;
    Integer RmgmaterialId;
    private List<MmsRmgDetail> rmgMaterialsList = new ArrayList<>();
    private List<MmsIsivReceivedDtl> IsivMaterialsList;
    private List<MmsGrnDetail> GrnMaterialsList;
    private List<MmsItemRegistration> itemInfoList;
    //Approved Rmg no list
    private List<MmsRmg> approvedRmgList;
    //approved Isiv no list
    private List<MmsIsivReceived> approvedIsivList;
    private List<MmsGrn> approvedGrnList;
    private List<MmsManageLocationDtl> allLocationInfoList;
    List<MmsManageLocation> allLocationList;

//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Constructor">
    /**
     * Creates a new instance of ItemLocationReceivableController
     */
    public ItemLocationReceivableController() {
    }

    @PostConstruct
    public void init() {
        setUserName(SessionBean.getUserName());
        wfMmsProcessed.setProcessedBy(SessionBean.getUserId());
    }
//</editor-fold>

    // <editor-fold defaultstate="collapsed" desc=" Getter And Setter ">
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

    public MmsManageLocation getMmsManageLocationEntity() {
        if (mmsManageLocationEntity == null) {
            mmsManageLocationEntity = new MmsManageLocation();
        }
        return mmsManageLocationEntity;
    }

    public void setMmsManageLocationEntity(MmsManageLocation mmsManageLocationEntity) {
        this.mmsManageLocationEntity = mmsManageLocationEntity;
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

    public MmsGrn getGrnEntity() {
        if (grnEntity == null) {
            grnEntity = new MmsGrn();
        }
        return grnEntity;
    }

    public void setGrnEntity(MmsGrn grnEntity) {
        this.grnEntity = grnEntity;
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

    public MmsIsivReceived getIsivReceivedEntity() {
        if (isivReceivedEntity == null) {
            isivReceivedEntity = new MmsIsivReceived();
        }
        return isivReceivedEntity;
    }

    public void setIsivReceivedEntity(MmsIsivReceived isivReceivedEntity) {
        this.isivReceivedEntity = isivReceivedEntity;
    }

    public MmsIsivReceivedDtl getIsivReceivedetailEntity() {
        if (isivReceivedetailEntity == null) {
            isivReceivedetailEntity = new MmsIsivReceivedDtl();
        }
        return isivReceivedetailEntity;
    }

    public void setIsivReceivedetailEntity(MmsIsivReceivedDtl isivReceivedetailEntity) {
        this.isivReceivedetailEntity = isivReceivedetailEntity;
    }

    public MmsRmg getRmgEntity() {
        if (rmgEntity == null) {
            rmgEntity = new MmsRmg();
        }
        return rmgEntity;
    }

    public void setRmgEntity(MmsRmg rmgEntity) {
        this.rmgEntity = rmgEntity;
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

    public MmsGrnDetail getGrnDetailEntity() {
        if (grnDetailEntity == null) {
            grnDetailEntity = new MmsGrnDetail();
        }
        return grnDetailEntity;
    }

    public void setGrnDetailEntity(MmsGrnDetail grnDetailEntity) {
        this.grnDetailEntity = grnDetailEntity;
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

    public MmsLocationInfo getMmsTempCellInfoEntity() {
        if (mmsTempCellInfoEntity == null) {
            mmsTempCellInfoEntity = new MmsLocationInfo();
        }
        return mmsTempCellInfoEntity;
    }

    public void setMmsTempCellInfoEntity(MmsLocationInfo mmsTempCellInfoEntity) {
        this.mmsTempCellInfoEntity = mmsTempCellInfoEntity;
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

    public boolean isRenderPnlCreateLocationRecievable() {
        return renderPnlCreateLocationRecievable;
    }

    public void setRenderPnlCreateLocationRecievable(boolean renderPnlCreateLocationRecievable) {
        this.renderPnlCreateLocationRecievable = renderPnlCreateLocationRecievable;
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

    public boolean isRenderForClosedShade() {
        return renderForClosedShade;
    }

    public void setRenderForClosedShade(boolean renderForClosedShade) {
        this.renderForClosedShade = renderForClosedShade;
    }

    public boolean isRenderpnlToSearchPage() {
        return renderpnlToSearchPage;
    }

    public void setRenderpnlToSearchPage(boolean renderpnlToSearchPage) {
        this.renderpnlToSearchPage = renderpnlToSearchPage;
    }

    public List<MmsShelfInfo> getOutdoorCodeList() {
        return outdoorCodeList;
    }

    public void setOutdoorCodeList(List<MmsShelfInfo> outdoorCodeList) {
        this.outdoorCodeList = outdoorCodeList;
    }

    public boolean isRenderCellCode() {
        return renderCellCode;
    }

    public void setRenderCellCode(boolean renderCellCode) {
        this.renderCellCode = renderCellCode;
    }

    public boolean isRenderForExpiry() {
        return renderForExpiry;
    }

    public void setRenderForExpiry(boolean renderForExpiry) {
        this.renderForExpiry = renderForExpiry;
    }

    public String getAddorModify() {
        return AddorModify;
    }

    public void setAddorModify(String AddorModify) {
        this.AddorModify = AddorModify;
    }

    public List<MmsGrn> getApprovedGrnList() {
        if (approvedGrnList == null) {
            approvedGrnList = new ArrayList<>();
        }
        return approvedGrnList;
    }

    public void setApprovedGrnList(List<MmsGrn> approvedGrnList) {
        this.approvedGrnList = approvedGrnList;
    }

    public List<MmsIsivReceived> getApprovedIsivList() {
        if (approvedIsivList == null) {
            approvedIsivList = new ArrayList<>();
        }
        return approvedIsivList;
    }

    public void setApprovedIsivList(List<MmsIsivReceived> approvedIsivList) {
        this.approvedIsivList = approvedIsivList;
    }

    public List<MmsRmg> getApprovedRmgList() {
        if (approvedRmgList == null) {
            approvedRmgList = new ArrayList<>();
        }
        return approvedRmgList;
    }

    public void setApprovedRmgList(List<MmsRmg> approvedRmgList) {
        this.approvedRmgList = approvedRmgList;
    }

    /**
     *
     * @return
     */
    public List<MmsGrn> getApproveGRNList() {
        int newgrnlist = 0;

        //  newgrnlist = papmsGrns.size();
        return approvedGrnList;
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

    public List<MmsShelfInfo> getWarehouseLists() {

        ArrayList<MmsShelfInfo> warehouseNames = mmsShelfInfoInterface.getWarhouseListByStoreId(mmsShelfInfoEntity);
        System.out.println("size of the Warehouselist=================" + warehouseNames.isEmpty());

        return warehouseNames;
    }

    public List<MmsStoreInformation> getStoreList() {
        StoreList = storeInfoInterface.findAllStoreInfo();
        return StoreList;
    }

    public boolean isRenderGrn() {
        return renderGrn;
    }

    public void setRenderGrn(boolean renderGrn) {
        this.renderGrn = renderGrn;
    }

    public boolean isRenderRMG() {
        return renderRMG;
    }

    public void setRenderRMG(boolean renderRMG) {
        this.renderRMG = renderRMG;
    }

    public boolean isRenderISIV() {
        return renderISIV;
    }

    public void setRenderISIV(boolean renderISIV) {
        this.renderISIV = renderISIV;
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

    public String getCheckType() {
        return checkType;
    }

    public void setCheckType(String checkType) {
        this.checkType = checkType;
    }

    public List<MmsGrnDetail> getGrnMaterialsList() {
        if (GrnMaterialsList == null) {
            GrnMaterialsList = new ArrayList<>();
        }
        return GrnMaterialsList;
    }

    public void setGrnMaterialsList(List<MmsGrnDetail> GrnMaterialsList) {
        this.GrnMaterialsList = GrnMaterialsList;
    }

    public String getCheckWarehouse() {
        return CheckWarehouse;
    }

    public void setCheckWarehouse(String CheckWarehouse) {
        this.CheckWarehouse = CheckWarehouse;
    }

    public String getSelectOption2() {
        return selectOption2;
    }

    public void setSelectOption2(String selectOption2) {
        this.selectOption2 = selectOption2;
    }

    public boolean isRenderWareHouseOneMenu() {
        return renderWareHouseOneMenu;
    }

    public void setRenderWareHouseOneMenu(boolean renderWareHouseOneMenu) {
        this.renderWareHouseOneMenu = renderWareHouseOneMenu;
    }

    public List<MmsShelfInfo> getShelfList() {
        if (ShelfList == null) {
            ShelfList = new ArrayList<>();
        }
        return ShelfList;
    }

    public void setShelfList(List<MmsShelfInfo> ShelfList) {
        this.ShelfList = ShelfList;
    }

    public List<MmsLocationInfo> getCellList() {
        if (cellList == null) {
            cellList = new ArrayList<>();
        }
        return cellList;
    }

    public void setCellList(List<MmsLocationInfo> cellList) {
        this.cellList = cellList;
    }

    public List<MmsLocationInfo> getCellRowList() {
        if (cellRowList == null) {
            cellRowList = new ArrayList<>();
        }
        return cellRowList;
    }

    public void setCellRowList(List<MmsLocationInfo> cellRowList) {
        this.cellRowList = cellRowList;
    }

    public BigDecimal getUnallocatedQuantity() {
        return unallocatedQuantity;
    }

    public void setUnallocatedQuantity(BigDecimal unallocatedQuantity) {
        this.unallocatedQuantity = unallocatedQuantity;
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

    public DataModel<MmsManageLocationDtl> getCurrentLocationInfoDataModel() {
        if (currentLocationInfoDataModel == null) {
            currentLocationInfoDataModel = new ListDataModel<>();
        }
        return currentLocationInfoDataModel;
    }

    public void setCurrentLocationInfoDataModel(DataModel<MmsManageLocationDtl> currentLocationInfoDataModel) {
        this.currentLocationInfoDataModel = currentLocationInfoDataModel;
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

    public boolean isDisableMatCodeList() {
        return disableMatCodeList;
    }

    public void setDisableMatCodeList(boolean disableMatCodeList) {
        this.disableMatCodeList = disableMatCodeList;
    }

    public boolean isDisableGrnList() {
        return disableGrnList;
    }

    public void setDisableGrnList(boolean disableGrnList) {
        this.disableGrnList = disableGrnList;
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

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public boolean isDisableISIVMatCodeList() {
        return disableISIVMatCodeList;
    }

    public void setDisableISIVMatCodeList(boolean disableISIVMatCodeList) {
        this.disableISIVMatCodeList = disableISIVMatCodeList;
    }

    public boolean isDisableRMGMatCodeList() {
        return disableRMGMatCodeList;
    }

    public void setDisableRMGMatCodeList(boolean disableRMGMatCodeList) {
        this.disableRMGMatCodeList = disableRMGMatCodeList;
    }

    public boolean isDisableIsivNoList() {
        return disableIsivNoList;
    }

    public void setDisableIsivNoList(boolean disableIsivNoList) {
        this.disableIsivNoList = disableIsivNoList;
    }

    public boolean isDisableRmgNoList() {
        return disableRmgNoList;
    }

    public void setDisableRmgNoList(boolean disableRmgNoList) {
        this.disableRmgNoList = disableRmgNoList;
    }

    public MmsRmgDetail getRmgDtlEntity() {
        if (rmgDtlEntity == null) {
            rmgDtlEntity = new MmsRmgDetail();
        }
        return rmgDtlEntity;
    }

    public void setRmgDtlEntity(MmsRmgDetail rmgDtlEntity) {
        this.rmgDtlEntity = rmgDtlEntity;
    }

    public MmsManageLocation getSelectedLocation() {
        return selectedLocation;
    }

    public void setSelectedLocation(MmsManageLocation selectedLocation) {
        this.selectedLocation = selectedLocation;
    }

    public String getOutdoorNameBundle() {
        return outdoorNameBundle;
    }

    public void setOutdoorNameBundle(String outdoorNameBundle) {
        this.outdoorNameBundle = outdoorNameBundle;
    }

    public DataModel<MmsManageLocationDtl> getMmsLocSearchInfoDataModel() {
        if (mmsLocSearchInfoDataModel == null) {
            mmsLocSearchInfoDataModel = new ListDataModel<>();
        }
        return mmsLocSearchInfoDataModel;
    }

    public void setMmsLocSearchInfoDataModel(DataModel<MmsManageLocationDtl> mmsLocSearchInfoDataModel) {
        this.mmsLocSearchInfoDataModel = mmsLocSearchInfoDataModel;
    }

    public MmsManageLocationDtl getManagelocdtl() {
        if (managelocdtl == null) {
            managelocdtl = new MmsManageLocationDtl();
        }
        return managelocdtl;
    }

    public void setManagelocdtl(MmsManageLocationDtl managelocdtl) {
        this.managelocdtl = managelocdtl;
    }

    /**
     * @return the allLocationInfoList
     */
    public List<MmsManageLocationDtl> getAllLocationInfoList() {
        if (allLocationInfoList == null) {
            allLocationInfoList = new ArrayList<>();
        }
        return allLocationInfoList;
    }

    /**
     * @param allLocationInfoList the allLocationInfoList to set
     */
    public void setAllLocationInfoList(List<MmsManageLocationDtl> allLocationInfoList) {
        this.allLocationInfoList = allLocationInfoList;
    }

    public List<MmsManageLocationDtl> getRecreateList() {
        if (recreateList == null) {
            recreateList = new ArrayList<>();
        }
        return recreateList;
    }

    public void setRecreateList(List<MmsManageLocationDtl> recreateList) {
        this.recreateList = recreateList;
    }

    public int getRowIndex() {
        return rowIndex;
    }

    public void setRowIndex(int rowIndex) {
        this.rowIndex = rowIndex;
    }

    /**
     * @return the itemInfoList
     */
    public List<MmsItemRegistration> getItemInfoList() {
        if (itemInfoList == null) {
            itemInfoList = new ArrayList<>();
        }
        return itemInfoList;
    }

    /**
     * @param itemInfoList the itemInfoList to set
     */
    public void setItemInfoList(List<MmsItemRegistration> itemInfoList) {
        this.itemInfoList = itemInfoList;
    }

// </editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc=" Page Rendering Code">
    public void goBackSearchButtonAction() {
        renderpnlToSearchPage = false;
        renderPnlCreateLocationRecievable = false;
        renderPnlManPage = true;
    }

    public void createNewLocationRecievable() {

        clearAllPage();
        saveorUpdateBundle = "Save";
        disableBtnCreate = false;
        renderpnlToSearchPage = false;
//            renderPnlCreateLocationRecievable = true;
//
//            renderPnlManPage = false;
//            createOrSearchBundle = "Search";
        //  approvedGrnList = mmsGrnBeanLocal.approvedGrnList(storeInfoEntity);
        if (createOrSearchBundle.equals("New")) {
            renderPnlCreateLocationRecievable = true;
            renderPnlManPage = false;
            createOrSearchBundle = "Search";
            setIcone("ui-icon-search");
            //generateGatePassNo();
        } else if (createOrSearchBundle.equals("Search")) {
            renderPnlCreateLocationRecievable = false;
            renderPnlManPage = true;
            createOrSearchBundle = "New";
            setIcone("ui-icon-plus");
        }

    }

    public void btn_Search_Action() {
        clearAllPage();
        renderPnlCreateLocationRecievable = false;
        renderPnlManPage = true;
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Event Handlers and other Methods">
    public void handleSelectGRNNo(ValueChangeEvent event) {
        if (null != event.getNewValue()) {
//          
            String GrnNum = event.getNewValue().toString();

            manageLocationDetailDataModel = null;
            grnEntity.setGrnId(Integer.parseInt(GrnNum));
            grnEntity = mmsGrnBeanLocal.getbyGrnID(grnEntity);
            storeInfoEntity.setStoreId(grnEntity.getStoreId().getStoreId());
            mmsManageLocationEntity.setStoreId(storeInfoEntity);
            mmsManageLocationEntity.setGrnId(grnEntity);
            grnEntity.setMmsGrnDetailCollection(grnEntity.getMmsGrnDetailCollection());
            storeInfoEntity.setStoreId(grnEntity.getStoreId().getStoreId());
            setStoreName(grnEntity.getStoreId().getStoreName());
            mmsManageLocationEntity.setGrnId(grnEntity);
            getGRNMaterialList();

        }

    }

    public void handleSelectIsivNo(ValueChangeEvent event) {
        if (null != event.getNewValue()) {
//        
            String ISIVNum = String.valueOf(event.getNewValue());

            manageLocationDetailDataModel = null;
            isivReceivedEntity.setRecievingId(Integer.parseInt(ISIVNum));
            isivReceivedEntity = isivReceivedInterface.getByReceivingId(isivReceivedEntity);
            System.out.println("=====isiv entity=======" + isivReceivedEntity.getMmsIsivReceivedDtlList().size());
            mmsManageLocationEntity.setIsivReceivedId(isivReceivedEntity);
            getIsivMaterialsList();

//           
//            
        }

    }

    public void handleSelectRmgNo(ValueChangeEvent event) {
        if (null != event.getNewValue()) {
            rmgEntity = (MmsRmg) event.getNewValue();

            manageLocationDetailDataModel = null;
            mmsManageLocationEntity.setRmgId(rmgEntity);
            getRmgMaterialsList();
//            
        }

    }

    public void changeCheckRecievableType(ValueChangeEvent e) {
        clearAllPage();
        mmsManageLocationEntity.setSelectOption(checkType);
        if (null != e.getNewValue()) {
            if (e.getNewValue().toString().equals("GRN")) {

                setRenderGrn(true);
                setRenderRMG(false);
                setRenderISIV(false);
                mmsManageLocationEntity.setSelectOption(checkType);
                System.out.println("select option====" + mmsManageLocationEntity.getSelectOption());
            } else if (e.getNewValue().toString().equals("RMG")) {
                System.out.println("============WHen option equals RMG ===");
                setRenderGrn(false);
                setRenderRMG(true);
                setRenderISIV(false);
                mmsManageLocationEntity.setSelectOption(checkType);
                System.out.println("select option====" + mmsManageLocationEntity.getSelectOption());
            } else if (e.getNewValue().toString().equals("RecievedISIV")) {
                System.out.println("============WHen option equals RecievedISIV store id ===" + storeInfoEntity.getStoreId());
                setRenderGrn(false);
                setRenderRMG(false);
                setRenderISIV(true);

            }

        }
    }

    public List<MmsItemRegistration> getGRNMaterialList() {
        itemInfoList.clear();
        GrnMaterialsList = grnEntity.getMmsGrnDetailCollection();
        for (int i = 0; i < GrnMaterialsList.size(); i++) {
            itemInfoList.add(GrnMaterialsList.get(i).getItemId());
        }
        return itemInfoList;
    }

    public List<MmsItemRegistration> getIsivMaterialsList() {
        itemInfoList.clear();
        System.out.println("=======isiv.getisiv detial list========" + isivReceivedEntity.getMmsIsivReceivedDtlList().size());
        IsivMaterialsList = isivReceivedEntity.getMmsIsivReceivedDtlList();
        for (int i = 0; i < IsivMaterialsList.size(); i++) {
            itemInfoList.add(IsivMaterialsList.get(i).getMaterialId());
        }

        return itemInfoList;
    }

    public List<MmsItemRegistration> getRmgMaterialsList() {
        itemInfoList.clear();
        System.out.println("=========Lists======" + rmgEntity.getMmsRmgDetailList().size());
        rmgMaterialsList = rmgEntity.getMmsRmgDetailList();

        for (int i = 0; i < rmgMaterialsList.size(); i++) {
            itemInfoList.add(rmgMaterialsList.get(i).getItemId());
        }
        return itemInfoList;
    }

    public void handleSelectMaterial(ValueChangeEvent event) {
        if (null != event.getNewValue()) {
            String code = event.getNewValue().toString();
            System.out.println("======value=========" + code);

            if (checkType.equals("GRN")) {
                itemRegEntity = (MmsItemRegistration) event.getNewValue();
                mmsManageLocationDtlEntity.setTotalQuantity(itemRegEntity.getMmsGrnDetail().get(0).getReceivedQuantity());
                setUnallocatedQuantity(grnDetailEntity.getReceivedQuantity());
                setDisableGrnList(true);
            } else if (checkType.equals("RecievedISIV")) {
                System.out.println("==========Inside isiv=============");
                itemRegEntity = (MmsItemRegistration) event.getNewValue();
                mmsManageLocationDtlEntity.setTotalQuantity(itemRegEntity.getMmsIsivReceivedDetailList().get(0).getQuantity());

                setDisableIsivNoList(true);

                setUnallocatedQuantity(isivReceivedetailEntity.getQuantity());
            } else if (checkType.equals("RMG")) {
                itemRegEntity = (MmsItemRegistration) event.getNewValue();

                mmsManageLocationDtlEntity.setTotalQuantity(itemRegEntity.getMmsRmgDetailList().get(0).getQuantity());
                setUnallocatedQuantity(rmgDtlEntity.getQuantity());
                setDisableRmgNoList(true);
            }
            System.out.println("===========item REgistration========" + itemRegEntity.getMatName());
            mmsManageLocationDtlEntity.setMaterialId(itemRegEntity);
            checkExpiryItem(itemRegEntity);
            unallocatedQuantity = BigDecimal.ZERO;

            setDisableMatCodeList(true);

            addedQuantity = 0;

        }
    }

    public void MaterialCodeListIsiv(ValueChangeEvent event) {
        if (null != event.getNewValue()) {
            String code = event.getNewValue().toString();
            System.out.println("======value=========" + code);

            itemRegEntity.setMatCode(code);
            itemRegEntity = itemRegistrationInterface.SearchIsivRecievedInfoByMatcodeJoined(itemRegEntity, isivReceivedEntity);
            mmsManageLocationDtlEntity.setMaterialId(itemRegEntity);
            setDisableISIVMatCodeList(true);
            addedQuantity = 0;
        }
    }

    public void handleSelectrmgMatcode(ValueChangeEvent event) {
        if (null != event.getNewValue()) {
            String code = event.getNewValue().toString();
            System.out.println("======value=========" + code);

            itemRegEntity.setMatCode(code);
            itemRegEntity = itemRegistrationInterface.SearchRmgRecievedInfoByMatcodeJoined(itemRegEntity, rmgEntity);
            rmgDtlEntity.setQuantity(itemRegEntity.getMmsRmgDetailList().get(0).getQuantity());
            setUnallocatedQuantity(rmgDtlEntity.getQuantity());
            setDisableRMGMatCodeList(true);
            addedQuantity = 0;
        }
    }

    public void enabledisableWarehouse(ValueChangeEvent ev) {
        if (null != ev.getNewValue()) {
            System.out.println("event inside enable" + ev.getNewValue().toString());
            if (ev.getNewValue().toString().equalsIgnoreCase("0")) {
                setRenderWareHouseOneMenu(false);
                mmsManageLocationDtlEntity.setSelectOption1(0);
            } else if (ev.getNewValue().toString().equalsIgnoreCase("1")) {
                setRenderWareHouseOneMenu(true);
                closedShadeList = mmsShelfInfoInterface.getClosedShadeListByStoreId(storeInfoEntity);
                mmsManageLocationDtlEntity.setSelectOption1(1);

            }

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
            } else {
                //option 1 is for outdoor
                setRenderRackCode(false);
                setRenderOutdoorInfo(true);
                mmsManageLocationDtlEntity.setSelectOption2(1);
                System.out.println("===========inside else====");
            }
        }
    }

    //Handle selecttion of A store
    public void handleSelectStoreName(ValueChangeEvent event) {
        if (null != event.getNewValue()) {
            System.out.println("===========event==" + event.getNewValue());
            storeInfoEntity = (MmsStoreInformation) event.getNewValue();
            switch (checkType) {
                case "GRN":
                    grnEntity.setStoreId(storeInfoEntity);
                    approvedGrnList = mmsGrnBeanLocal.approvedGrnListByStoreAndStatus(grnEntity, Constants.APPROVE_VALUE);
                    break;
                case "RMG":
                    rmgEntity.setRecevingStore(storeInfoEntity);
                    approvedRmgList = rmgInterface.approvedRmgListByStoreAndStatus(rmgEntity, Constants.APPROVE_VALUE);
                    break;
                default:
                    approvedIsivList = isivReceivedInterface.approvedIsivList(storeInfoEntity, Constants.APPROVE_VALUE);
                    break;
            }
            System.out.println("after setting store id=======" + storeInfoEntity.getStoreId());
            mmsManageLocationEntity.setStoreId(storeInfoEntity);
            mmsManageLocationDtlEntity.setSelectOption1(0);
//         
        }

    }

    public List<MmsShelfInfo> getShelfLists() {
        if ("0".equals(CheckWarehouse)) {
            if (selectOption2.equals("0")) {
                System.out.println("=========store@controller========" + storeInfoEntity.getStoreId());
                System.out.println("=========shelf list=====" + ShelfList.size());
            } else {
                System.out.println("=======Insdie outdoor code search=======");
                ShelfList = mmsShelfInfoInterface.searchStoreOutdoor(storeInfoEntity);
            }
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

    //handle selection of warehouse
    public void handleSelectClosedShadeName(ValueChangeEvent event) {
        if (null != event.getNewValue()) {

            mmsShelfInfoEntity.setShadeName(String.valueOf(event.getNewValue()));

        }

    }

    //Handle Selection of ShelfCode
    public void handleSelectShelfCode(ValueChangeEvent event) {
        if (null != event.getNewValue()) {
            mmsShelfInfoEntity = (MmsShelfInfo) event.getNewValue();
            System.out.println("===========inside shelf-------------" + mmsShelfInfoEntity.getShelfId());
            mmsCellInfoEntity.setShelfId(mmsShelfInfoEntity);
            shelfId = mmsCellInfoEntity.getShelfId().getShelfId();
            mmsManageLocationDtlEntity.setShelfId(mmsShelfInfoEntity);
            if (mmsShelfInfoEntity.getRackOrOutdoor() == 1 && mmsShelfInfoEntity.getOutdoorType().equals("Closed Shade") && mmsShelfInfoEntity.getHasArack() == 0) {
                setRenderCellCode(true);
                cellRowList = mmsCellInfoInterface.searchCellByShelfId(mmsShelfInfoEntity);
                cellList = new ArrayList<>();
            } else if (mmsShelfInfoEntity.getRackOrOutdoor() == 0) {
                setRenderCellCode(true);
                cellRowList = mmsCellInfoInterface.searchCellByShelfId(mmsShelfInfoEntity);
                cellList = new ArrayList<>();

            }

        }

    }

    public void handleSelectShelf(ValueChangeEvent event) {
        if (null != event.getNewValue()) {
            mmsTempCellInfoEntity = (MmsLocationInfo) event.getNewValue();
            System.out.println("===========inside shelfrow id-------------" + mmsCellInfoEntity.getLocationId());

            cellList = mmsCellInfoInterface.searchCellByRackAndShelfId(mmsShelfInfoEntity, mmsTempCellInfoEntity);
        }
    }

    //Get Cell codes Based on Shelf
    public List<MmsLocationInfo> getShelfCells() {

        if ("0".equals(CheckWarehouse)) {
            cellList = mmsCellInfoInterface.searchCellByShelfId(mmsShelfInfoEntity);
            return cellList;// JsfUtil.getSelectItems(mmsShelfInfoDtlInterface.getShelf(storeInfoEntity), true);
        } else {
            cellList = mmsCellInfoInterface.searchCellByShelfId(mmsShelfInfoEntity);
        }
        return cellList;
    }

    //Handle Selection of CellCode
    public void handleSelectCellCode(ValueChangeEvent event) {
        if (null != event.getNewValue()) {
            mmsCellInfoEntity = (MmsLocationInfo) event.getNewValue();
            cellId = mmsCellInfoEntity.getLocationId();
            mmsManageLocationDtlEntity.setCellId(mmsCellInfoEntity);
            locationInfo = mmsmanageLocationDtlInterface.searchLocationInfoByCellId(mmsManageLocationDtlEntity);
            recreatecurrentLocModel();
            mmsManageLocationDtlEntity.setCellId(mmsCellInfoEntity);
        }

    }

    public void addDetail() {

        if (checkMaterialId.contains(mmsManageLocationDtlEntity.getMaterialId()) && checkcellId.contains(mmsManageLocationDtlEntity.getCellId()) && checkshelfId.contains(mmsManageLocationDtlEntity.getShelfId()) && mmsManageLocationDtlEntity.getLocationQuantity() == PreviousQuantity) {
            JsfUtil.addFatalMessage("Material already exists");

        } else {
            mmsManageLocationEntity.addGrnDetail(mmsManageLocationDtlEntity);
            checkMaterialId.add(mmsManageLocationDtlEntity.getMaterialId());
            checkcellId.add(mmsManageLocationDtlEntity.getCellId());
            checkshelfId.add(mmsManageLocationDtlEntity.getShelfId());

            recreateDetail();
            clearPage();
        }
    }

    private void recreateModelDetail() {
        manageLocationDetailDataModel = null;
        manageLocationDetailDataModel = new ListDataModel(new ArrayList<>(listdtl));

    }

    public void recreateDetail() {
        manageLocationDetailDataModel = null;
        manageLocationDetailDataModel = new ListDataModel(mmsManageLocationEntity.getMmsManageLocationDtlList());
    }

    private void clearPage() {
        System.out.println("====insdie ClearPopup=====");

        mmsShelfInfoEntity = new MmsShelfInfo();

        mmsCellInfoEntity = new MmsLocationInfo();
        mmsCellInfoDtlEntity = new MmsLocationInfoDtl();
        itemRegEntity = new MmsItemRegistration();
        mmsManageLocationDtlEntity = new MmsManageLocationDtl();

    }

    public void handleSelectRackOrOutdoor(ValueChangeEvent event) {
        if (null != event.getNewValue()) {
            outdoorCodeList = new ArrayList<>();
            setOutdoorNameBundle(defaultOutdoorBundleName);
            System.out.println("event value===========" + event.getNewValue().toString());
            if ("0".equals(event.getNewValue().toString())) {
                setRenderRackCode(true);
                setRenderOutdoorInfo(false);
                setOutdoorNameBundle(nameBundleForRack);
                mmsShelfInfoEntity.setRackOrOutdoor(valueForRack);
                outdoorCodeList = mmsShelfInfoInterface.searchStoreRacks(storeInfoEntity, mmsShelfInfoEntity);
                System.out.println("===========inside if====");

            } else {
                setRenderRackCode(false);
                setRenderOutdoorInfo(true);
                System.out.println("===========inside else====");
                mmsShelfInfoEntity.setRackOrOutdoor(valueForOutdoor);
            }
        }
    }

    public SelectItem[] getListOfOutdoorType() {
        return JsfUtil.getSelectItems(outdoorTypeBeanLocal.findAll(), true);
    }

    public void handleSelectOutdoorType(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            //setDisableRackCode(true);
            System.out.println("=============event======" + event.getNewValue().toString());
            if (event.getNewValue().toString().equals("Field")) {
                setOutdoorNameBundle(nameBundleForField);
                mmsShelfInfoEntity.setOutdoorType(event.getNewValue().toString());
                outdoorCodeList = mmsShelfInfoInterface.searchOutdoorCodes(storeInfoEntity, mmsShelfInfoEntity);
            } else if (event.getNewValue().toString().equals("Closed Shade")) {
                setRenderForClosedShade(true);

            } else if (event.getNewValue().toString().equals("Open Shade")) {
                setOutdoorNameBundle(nameBundleForOpenShade);
                mmsShelfInfoEntity.setOutdoorType(event.getNewValue().toString());
                outdoorCodeList = mmsShelfInfoInterface.searchOutdoorCodes(storeInfoEntity, mmsShelfInfoEntity);
            }

        }
    }

    public void handleSelectHasArack(ValueChangeEvent event) {

        if (null != event.getNewValue()) {
            System.out.println("========event value");
            if ("0".equals(event.getNewValue().toString())) {
                setRenderRackCode(true);
                setRenderOutdoorInfo(false);
                setOutdoorNameBundle(nameBundleForRack);
                mmsShelfInfoEntity.setHasArack(valueForRack);
                outdoorCodeList = mmsShelfInfoInterface.searchClosedShadeRackCodes(storeInfoEntity, mmsShelfInfoEntity);
                System.out.println("=============Size@controller==" + outdoorCodeList.size());

            } else {
                setRenderRackCode(false);
                setRenderOutdoorInfo(true);

                setOutdoorNameBundle(defaultOutdoorBundleName);
                mmsShelfInfoEntity.setHasArack(valueForOutdoor);
                outdoorCodeList = mmsShelfInfoInterface.searchClosedShadeRackCodes(storeInfoEntity, mmsShelfInfoEntity);

            }
        }
    }

    public void checkExpiryItem(MmsItemRegistration item) {
        if (item.getItemType().equals(expiryItem)) {
            System.out.println("=======Has Expiry is true=======");
            setRenderForExpiry(true);
            System.out.println("===value=====" + renderForExpiry);
        }

    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Save Methods and Search">
    public void saveItemLocationReceivable() {
        System.out.println("=======inside save=========");
        try {
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(SessionBean.getUserName(), "saveItemLocationReceivable", dataset)) {
                System.out.println("======after security======");
                if (manageLocationDetailDataModel.getRowCount() <= 0) {
                    JsfUtil.addFatalMessage("Data Table Shoud be filled");
                } else {

                    if (updateStatus == 0) {
                        CheckListForEquality();
                        if (result) {
                            System.out.println("======Inside if of save result========" + result);
                            locationList = mmsmanageLocationDtlInterface.searchLocationInfoByStore(storeInfoEntity);
                            checkInfoType(checkType);
                            wfMmsProcessed.setProcessedBy(SessionBean.getUserId());
                            mmsManageLocationEntity.setProcessedOn(wfMmsProcessed.getProcessedOn());
                            mmsManageLocationEntity.setProcessedBy(wfMmsProcessed.getProcessedBy());
                            setWorkFlowValues();
                            mmsManageLocationInterface.create(mmsManageLocationEntity);
                            updateStatus(checkType);
                            wfMmsProcessed.setProcessedOn(mmsManageLocationEntity.getProcessedOn());

//                       
                            JsfUtil.addSuccessMessage("Location Information Data Saved ");
                            clearAllPage();
                        } else {
                            JsfUtil.addFatalMessage("All Items Should Be processed");
                        }

                    } else {
                        checkInfoType(checkType);
                        setWorkFlowValues();
                        mmsManageLocationInterface.edit(mmsManageLocationEntity);
                        JsfUtil.addSuccessMessage("Location Information Data updated");
                        clearAllPage();
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

    public void setWorkFlowValues() {
        mmsManageLocationEntity.setProcessedBy(SessionBean.getUserId());
        mmsManageLocationEntity.setProcessedOn(wfMmsProcessed.getProcessedOn());
    }

    public void checkInfoType(String Checktype) {
        switch (Checktype) {
            case "GRN":
                mmsManageLocationEntity.setSelectOption("GRN");

                break;
            case "RMG":
                mmsManageLocationEntity.setSelectOption("RMG");

                break;
            case "RecievedISIV":
                mmsManageLocationEntity.setSelectOption("RecievedISIV");
                break;
        }
    }

    public void updateStatus(String Checktype) {
        switch (Checktype) {
            case "GRN":
                mmsManageLocationEntity.setSelectOption("GRN");
                grnEntity.setStatus(Constants.GRN_PROCESSED_BY_RECIVABLE_ITEM_LOCATION);
                mmsGrnBeanLocal.edit(grnEntity);
                break;
            case "RMG":
                mmsManageLocationEntity.setSelectOption("RMG");
                rmgEntity.setStatus(Constants.RMG_PROCESSED_BY_RECIVABLE_ITEM_LOCATION);
                rmgInterface.edit(rmgEntity);
                break;
            case "RecievedISIV":
                mmsManageLocationEntity.setSelectOption("RecievedISIV");
                isivReceivedEntity.setStatus(Constants.ISIV_RECIEVED_PROCESSED_BY_RECIVABLE_ITEM_LOCATION);
                isivReceivedInterface.edit(isivReceivedEntity);
                break;
        }
    }

    public boolean CheckListForEquality() {
        for (int i = 0; i < mmsManageLocationEntity.getMmsManageLocationDtlList().size(); i++) {
            detailLists.add(mmsManageLocationEntity.getMmsManageLocationDtlList().get(i).getMaterialId());
        }
        result = detailLists.containsAll(itemInfoList);
        return result;
    }

    //Save all the information
    public String save() {

        if ((!(getManageLocationDetailDataModel().getRowCount() > 0))) {
            JsfUtil.addFatalMessage("Data Table Shoud be filled");
        } else {
            try {
                if (updateStatus == 0) {
                    System.out.println("==========store info entity======" + storeInfoEntity.getStoreId());
                    locationList = mmsmanageLocationDtlInterface.searchLocationInfoByStore(storeInfoEntity);
                    System.out.println("============location List.size========" + locationList.size());
                    System.out.println("------save------");
                    if ("GRN".equals(checkType)) {
                        if (locationList.size() > 0) {
                            for (int j = 0; j < locationList.size(); j++) {
                                MmsManageLocation manangeLoc = new MmsManageLocation();
                                MmsManageLocationDtl managelocdtlobj = new MmsManageLocationDtl();
                                if (Objects.equals(listdtl.get(j).getMaterialId().getMaterialId(), locationList.get(j).getMaterialId().getMaterialId())
                                        && Objects.equals(listdtl.get(j).getShelfId().getShelfId(), locationList.get(j).getShelfId().getShelfId())
                                        && Objects.equals(listdtl.get(j).getCellId().getLocationId(), locationList.get(j).getCellId().getLocationId())) {
                                    System.out.println("============passed the if condition=============");
                                    System.out.println("===========loc quantity before adding====" + locationList.get(j).getLocationQuantity());

                                    locationList.get(j).setLocationQuantity(locationList.get(j).getLocationQuantity() + listdtl.get(j).getLocationQuantity());
                                    System.out.println("===========loc quantity after adding====" + locationList.get(j).getLocationQuantity());

                                    managelocdtlobj = locationList.get(j);
                                    mmsmanageLocationDtlInterface.edit(managelocdtlobj);

                                } else {
                                    System.out.println("============inside else  condition=============");
                                    int size = masterISIV.size();
                                    System.out.println("============master siv size======" + masterISIV.size());
                                    for (int i = 0; i < size; i++) {
                                        System.out.println("==========inside for loop of else condition=========");
                                        masterISIV.get(i).setGrnId(grnEntity);
                                        masterISIV.get(i).setSelectOption("GRN");
                                        masterISIV.get(i).setStoreId(storeInfoEntity);
                                        mmsManageLocationInterface.create(masterISIV.get(i));
                                    }
                                }

                            }
                        } else {

                        }

                        JsfUtil.addSuccessMessage("Location Information Data Created");
                        clearAllPage();
                    } else if ("RMG".equals(checkType)) {
                        mmsManageLocationEntity.setRmgId(rmgEntity);
                        mmsManageLocationEntity.setSelectOption("RMG");
                        mmsManageLocationInterface.create(mmsManageLocationEntity);
                        JsfUtil.addSuccessMessage("Location Information Data Created");
                        clearAllPage();
                    } else if ("RecievedISIV".equals(checkType)) {

                        int size = masterISIV.size();
                        for (int i = 0; i < size; i++) {
                            masterISIV.get(i).setIsivReceivedId(isivReceivedEntity);
                            masterISIV.get(i).setSelectOption("RecievedISIV");
                            masterISIV.get(i).setStoreId(storeInfoEntity);
                            mmsManageLocationInterface.create(masterISIV.get(i));
                            setDisableIsivNoList(false);
                        }

                        JsfUtil.addSuccessMessage("Location Information Data Created");
                        clearAllPage();

                    }
                } else if (updateStatus == 1) {
                    mmsManageLocationInterface.edit(mmsManageLocationEntity);
                    JsfUtil.addSuccessMessage("Location Information Modified");
                    clearAllPage();
                }
            } catch (Exception ex) {
                JsfUtil.addFatalMessage("Some error occured on data");
                ex.getMessage();
            }

        }
        return null;
    }

    public String clearAllPage() {
        mmsManageLocationEntity = new MmsManageLocation();
        mmsManageLocationDtlEntity = new MmsManageLocationDtl();
        grnEntity = new MmsGrn();
        grnEntity = null;
//        rmgEntity = new MmsRmg();
        rmgEntity = null;
        isivEntity = null;
        mmsShelfInfoEntity = new MmsShelfInfo();
        wfMmsProcessed = null;
        mmsCellInfoDtlEntity = new MmsLocationInfoDtl();
        mmsCellInfoEntity = new MmsLocationInfo();
        manageLocationDetailDataModel = new ListDataModel<>();
        itemRegEntity = new MmsItemRegistration();
        managelocdtl = new MmsManageLocationDtl();
        storeInfoEntity = new MmsStoreInformation();
        mmsLocSearchInfoDataModel = new ListDataModel<>();
        updateStatus = 0;
        setDisableGrnList(false);
        return null;
    }
    //search Location Info

    public void searchLocationInformation() {

        if (storeInfoEntity.getStoreId() != null && itemRegEntity.getMatCode().isEmpty() && itemRegEntity.getMatName().isEmpty()) {
            System.out.println("==========inside By store===" + storeInfoEntity.getStoreId());
            allLocationInfoList = mmsmanageLocationDtlInterface.searchLocationInfoByStore(storeInfoEntity);
            recerateSearchModel();

        } else if (storeInfoEntity.getStoreId() != null && itemRegEntity.getMatName().isEmpty() && !itemRegEntity.getMatCode().isEmpty()) {
            System.out.println("search By Code=====");
            setAllLocationInfoList(mmsmanageLocationDtlInterface.searchByStoreAndItemCode(itemRegEntity, storeInfoEntity));
            System.out.println("==============llist=======" + getAllLocationInfoList().get(0).getMaterialId().getMatCode());

            recerateSearchModel();
        } else if (storeInfoEntity.getStoreId() != null && itemRegEntity.getMatCode().isEmpty() && !itemRegEntity.getMatName().isEmpty()) {
            System.out.println("search By name=====");
            itemRegEntity.setStoreId(storeInfoEntity);
            setAllLocationInfoList(mmsmanageLocationDtlInterface.searchByStoreAndItemName(itemRegEntity));

            recerateSearchModel();
        }

    }

    private void recerateSearchModel() {
        mmsLocSearchInfoDataModel = null;
        mmsLocSearchInfoDataModel = new ListDataModel(new ArrayList<>(allLocationInfoList));

        System.out.println("=============Model==============" + mmsLocSearchInfoDataModel.getRowCount());
    }

    private void recerateSearchModel1() {
        mmsLocationSearchInfoDataModel = null;
        mmsLocationSearchInfoDataModel = new ListDataModel(new ArrayList<>(allLocationList));

        System.out.println("=============Model==============" + mmsLocSearchInfoDataModel.getRowCount());
    }

    public void onRowEdit(SelectEvent event) {
        managelocdtl = (MmsManageLocationDtl) event.getObject();

        System.out.println("=============Master id =====" + managelocdtl.getId());
        System.out.println("=============Detail id =====" + managelocdtl.getManageLocationId().getId());

        mmsManageLocationEntity = managelocdtl.getManageLocationId();

        switch (mmsManageLocationEntity.getSelectOption()) {
            case "RecievedISIV":
                setCheckType("RecievedISIV");
                setRenderGrn(false);
                setRenderRMG(false);
                setRenderISIV(true);
                isivReceivedEntity = mmsManageLocationEntity.getIsivReceivedId();
                isivDetailEntity.setQuantity(isivReceivedEntity.getMmsIsivReceivedDtlList().get(0).getQuantity());
                approvedIsivList = new ArrayList<>();
                approvedIsivList.add(isivReceivedEntity);
                break;
            case "RMG":
                setCheckType("RMG");
                setRenderGrn(false);
                setRenderRMG(true);
                setRenderISIV(false);
                rmgEntity = mmsManageLocationEntity.getRmgId();
                approvedRmgList = new ArrayList<>();
                approvedRmgList.add(rmgEntity);
                break;
            case "GRN":
                System.out.println("==========inside Grn=========");
                setCheckType("GRN");
                setRenderGrn(true);
                setRenderRMG(false);
                setRenderISIV(false);
                approvedGrnList = new ArrayList<>();
                approvedGrnList.add(mmsManageLocationEntity.getGrnId());
                grnEntity = mmsManageLocationEntity.getGrnId();
                System.out.println("==========inside Grn id=========" + mmsManageLocationEntity.getGrnId());
                break;
        }
        recreateList = new ArrayList<>();
        recreateList.add(managelocdtl);
        if (workflow.isPrepareStatus()) {
            wfMmsProcessed.setProcessedOn(mmsManageLocationEntity.getProcessedOn());
            System.out.println("==========================wfMmsProcessed==" + mmsManageLocationEntity.getProcessedOn());
        }
        renderPnlCreateLocationRecievable = true;
        renderPnlManPage = false;
        renderpnlToSearchPage = true;
        activeIndex = "0";
        saveorUpdateBundle = "Update";
        createOrSearchBundle = "New";
        setCreateOrSearchBundle(createOrSearchBundle);
        setIcone("ui-icon-plus");
        disableBtnCreate = true;
        updateStatus = 1;
        recreateDtlList();

    }

    public void recreateSearchModeldtl() {
        manageLocationDetailDataModel = null;
        manageLocationDetailDataModel = new ListDataModel(new ArrayList<>(mmsManageLocationEntity.getMmsManageLocationDtlList()));
    }

    public void recreatecurrentLocModel() {
        currentLocationInfoDataModel = null;
        currentLocationInfoDataModel = new ListDataModel(new ArrayList<>(locationInfo));
    }

    public void recreateDtlList() {
        manageLocationDetailDataModel = null;
        manageLocationDetailDataModel = new ListDataModel(recreateList);
    }

    //Check for duplication
    public void checkRowDuplication(List<MmsManageLocationDtl> listdtl) {
        int size = listdtl.size();
        System.out.println("size of list11111111111111=======" + size);

        for (int i = 0; i < size; i++) {
            if (Objects.equals(materialId, listdtl.get(i).getMaterialId().getMaterialId())
                    && Objects.equals(shelfId, listdtl.get(i).getShelfId().getShelfId())
                    && Objects.equals(cellId, listdtl.get(i).getCellId().getLocationId())) {
                index = i;
                System.out.println("===============INSIDE OF IF CONDITION IN THE FOR LOOP===============");

                System.out.println("size of list ISIV=======" + size);
                System.out.println("=============new material id=========" + materialId);
                System.out.println("=============Old material id=========" + listdtl.get(i).getMaterialId().getMaterialId());

                System.out.println("=============new Shelf id=========" + shelfId);
                System.out.println("=============Old Shelf id=========" + listdtl.get(i).getShelfId().getShelfId());

                System.out.println("=============new Cell id=========" + cellId);
                System.out.println("=============Old Cell id=========" + listdtl.get(i).getCellId().getLocationId());

                System.out.println("===========unallocated qunatity=====" + unallocatedQuantity);
                System.out.println("==========current quantity from dtl===" + mmsManageLocationDtlEntity.getLocationQuantity());

                break;

            }

        }

    }

    public void edit(SelectEvent event) {
        setRowIndex(manageLocationDetailDataModel.getRowIndex());
        System.out.println("==========rowselection===");
        AddorModify = "Modify";
        mmsManageLocationDtlEntity = manageLocationDetailDataModel.getRowData();
        mmsShelfInfoEntity = mmsManageLocationDtlEntity.getShelfId();
        mmsCellInfoEntity = mmsManageLocationDtlEntity.getCellId();
        itemRegEntity = mmsManageLocationDtlEntity.getMaterialId();
        PreviousQuantity = mmsManageLocationDtlEntity.getLocationQuantity();
        itemInfoList = new ArrayList<>();
        itemInfoList.add(itemRegEntity);
        outdoorCodeList = new ArrayList<>();
        outdoorCodeList.add(mmsShelfInfoEntity);
        if (mmsShelfInfoEntity.getRackOrOutdoor() == OutdoorValue) {
            System.out.println("===inside the first if==========");
            if (mmsShelfInfoEntity.getOutdoorType().equals("Closed Shade") && mmsShelfInfoEntity.getHasArack() == hasAclosedShadeArackValue) {
                System.out.println("=========Inside the second if=========");
                setRenderOutdoorInfo(true);

            } else {
                System.out.println("==========Inside Else of has a rack ============");
                setRenderOutdoorInfo(true);
                setRenderForClosedShade(false);

            }
        } else {
            System.out.println("=============test===========");

            setRenderOutdoorInfo(false);
            setRenderForClosedShade(false);
            setOutdoorNameBundle(nameBundleForRack);

        }

    }

//</editor-fold>
}
