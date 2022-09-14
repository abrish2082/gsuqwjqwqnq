/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.mms.controller;

import et.gov.eep.commonApplications.businessLogic.WfMmsProcessedBeanLocal;
import et.gov.eep.commonApplications.entity.ColumnNameResolver;
import et.gov.eep.commonApplications.entity.WfMmsProcessed;
import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.mms.businessLogic.MmsLocationInfoBeanLocal;
import et.gov.eep.mms.businessLogic.MmsShelfInfoBeanLocal;

import et.gov.eep.mms.businessLogic.MmsStoreInformationBeanLocal;
import et.gov.eep.mms.entity.MmsLocationInfo;
import et.gov.eep.mms.entity.MmsLocationInfoDtl;
import et.gov.eep.mms.entity.MmsShelfInfo;

import et.gov.eep.mms.entity.MmsStoreInformation;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
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
import org.primefaces.event.SelectEvent;
import securityBean.Constants;
import securityBean.SessionBean;
import securityBean.WorkFlow;
import webService.AAA;
import webService.EventEntry;
import webService.IAdministration;

/**
 *
 * @author minab
 */
@Named(value = "shelfCellInfoController")
@ViewScoped
public class ShelfCellInfoController implements Serializable {

    //<editor-fold defaultstate="collapsed" desc="EJB's">
    @EJB
    WfMmsProcessedBeanLocal wfMmsProcessedBeanLocal;
    @EJB
    MmsShelfInfoBeanLocal mmsShelfInfoInterface;
    @EJB
    MmsLocationInfoBeanLocal mmsCellInfoInterface;
    @EJB
    private MmsStoreInformationBeanLocal storeInfoInterface;
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Entity's">
    @Inject
    private MmsShelfInfo mmsShelfInfoEntity;
    @Inject
    WfMmsProcessed wfMmsProcessed;
    @Inject
    private MmsLocationInfo mmsCellInfoEntity;
    @Inject
    private MmsStoreInformation StoreEntity;
    @Inject
    private MmsLocationInfoDtl mmsCellInfoDtlEntity;
    @Inject
    private MmsStoreInformation storeInfoEntity;
    @Inject
    private MmsStoreInformation storeInfoEntityTemp;
    @Inject
    SessionBean SessionBean;
    @Inject
    WorkFlow workflow;
    @Inject
    ColumnNameResolver columnNameResolver;
//</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Fields">
    private DataModel<MmsLocationInfo> mmsCellInfoSearchDataModel;
    private DataModel<MmsLocationInfo> mmsCellDataModel;
    private boolean rendersearchByempid4reportid = false;
    private boolean disbsearchByempid4reportid = true;
    private boolean renderWareHouseOneMenu = false;
    private boolean disbleWareHouseOneMenu = true;
    private boolean renderstoreShelfOnemenu = true;
    private boolean disablestoreShelfOnemenu = false;
    private boolean disableBtnCreate;
    private boolean renderPnlCreateCellInfo = false;
    private boolean renderPnlManPage = true;
    private boolean renderpnlToSearchPage;
    private String createOrSearchBundle = "New";
    private String icone = "ui-icon-plus";
    private String activeIndex;
    private String saveorUpdateBundle = "Save";
    int updateStatus = 0;
    private String CkeckPayrollType = "2";
    private String CheckWarehouse = "";
    private boolean getlist = false;
    private String userName;
    private final Integer storeRackValue = 0;
    private final Integer closedShadeRackValue = 1;
    DataModel<WfMmsProcessed> WorkflowDataModel = new ListDataModel<>();
    List<WfMmsProcessed> WfList;
    private List<MmsShelfInfo> WarhouseShelfNames = new ArrayList<>();
    private List<MmsShelfInfo> mmsShelfList = new ArrayList<>();
    private List<MmsStoreInformation> StoreListForCell = new ArrayList<>();
    private List<MmsStoreInformation> StoreListForCellMain = new ArrayList<>();
    String selectedValue = "";
    private String AddorModify = "Add";
    Set<String> checkCellCode = new HashSet<>();
    Set<String> checkRackCode = new HashSet<>();
    Set<String> checkShelfRow = new HashSet<>();
    Set<String> checkShelfCode = new HashSet<>();
    private MmsLocationInfo selectedcellCode;
    List<MmsShelfInfo> searchedShelf;
    List<MmsLocationInfo> allCellinfoList;
    List<MmsShelfInfo> warehouseNames;
    List<MmsShelfInfo> warehouseNametemp;
    List<MmsShelfInfo> getshelfs;
    List<MmsLocationInfo> newList = new ArrayList<>();
    int updatedtlstatus = 0;
    List<MmsLocationInfo> addCellList = new ArrayList<>();
    List<MmsStoreInformation> store;
    private List<String> mmsShelfColumnNameList;
    ColumnNames columnNames = new ColumnNames();
    List<ColumnNameResolver> ColumnNamesList = new ArrayList<>();

//</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Constructor">   
    public ShelfCellInfoController() {
    }

    /**
     *
     * @return
     */
    @PostConstruct
    public void initwfMmsProcessed() {
        wfMmsProcessed.setProcessedBy(SessionBean.getUserId());
        setUserName(SessionBean.getUserName());
        System.out.println("is App==" + workflow.isApproveStatus() + "is chech==" + workflow.isCheckStatus() + "is prepa==" + workflow.isPrepareStatus());
        StoreListForCell = storeInfoInterface.findAllStoreInfo();
        StoreListForCellMain = storeInfoInterface.findAllStoreInfo();
        getMmsShelfColumnNameList();
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
//</editor-fold>
    // <editor-fold defaultstate="collapsed" desc=" Getter And Setter ">

    public List<MmsStoreInformation> getStoreListForCell() {
        return StoreListForCell;
    }

    public void setStoreListForCell(List<MmsStoreInformation> StoreListForCell) {
        this.StoreListForCell = StoreListForCell;
    }

    public List<ColumnNameResolver> getColumnNamesList() {
        return ColumnNamesList;
    }

    public void setColumnNamesList(List<ColumnNameResolver> ColumnNamesList) {
        this.ColumnNamesList = ColumnNamesList;
    }

    public ColumnNameResolver getColumnNameResolver() {
        return columnNameResolver;
    }

    public void setColumnNameResolver(ColumnNameResolver columnNameResolver) {
        this.columnNameResolver = columnNameResolver;
    }

    public List<MmsStoreInformation> getStoreListForCellMain() {
        return StoreListForCellMain;
    }

    public void getStoreListForCellMain(List<MmsStoreInformation> StoreListForCell) {
        this.StoreListForCellMain = StoreListForCell;
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

    public String getSelectedValue() {
        return selectedValue;
    }

    public void setSelectedValue(String selectedValue) {
        this.selectedValue = selectedValue;
    }

    public WorkFlow getWorkflow() {
        return workflow;
    }

    public void setWorkflow(WorkFlow workflow) {
        this.workflow = workflow;
    }

    public MmsShelfInfo getMmsShelfInfoEntity() {
        if (mmsShelfInfoEntity == null) {
            mmsShelfInfoEntity = new MmsShelfInfo();
        }
        return mmsShelfInfoEntity;
    }

    /**
     *
     * @param mmsShelfInfoEntity
     */
    public void setMmsShelfInfoEntity(MmsShelfInfo mmsShelfInfoEntity) {
        this.mmsShelfInfoEntity = mmsShelfInfoEntity;
    }

    /**
     *
     * @return
     */
    /**
     *
     * @return
     */
    public MmsLocationInfo getMmsCellInfoEntity() {
        if (mmsCellInfoEntity == null) {
            mmsCellInfoEntity = new MmsLocationInfo();
        }
        return mmsCellInfoEntity;
    }

    /**
     *
     * @param mmsCellInfoEntity
     */
    public void setMmsCellInfoEntity(MmsLocationInfo mmsCellInfoEntity) {
        this.mmsCellInfoEntity = mmsCellInfoEntity;
    }

    /**
     *
     * @return
     */
    public MmsLocationInfoDtl getMmsCellInfoDtlEntity() {
        if (mmsCellInfoDtlEntity == null) {
            mmsCellInfoDtlEntity = new MmsLocationInfoDtl();
        }
        return mmsCellInfoDtlEntity;
    }

    /**
     *
     * @param mmsCellInfoDtlEntity
     */
    public void setMmsCellInfoDtlEntity(MmsLocationInfoDtl mmsCellInfoDtlEntity) {
        this.mmsCellInfoDtlEntity = mmsCellInfoDtlEntity;
    }

    /**
     *
     * @return
     */
    public DataModel<MmsLocationInfo> getMmsCellInfoSearchDataModel() {
        if (mmsCellInfoSearchDataModel == null) {
            mmsCellInfoSearchDataModel = new ListDataModel<>();
        }
        return mmsCellInfoSearchDataModel;
    }

    public void setMmsCellInfoSearchDataModel(DataModel<MmsLocationInfo> mmsCellInfoSearchDataModel) {
        this.mmsCellInfoSearchDataModel = mmsCellInfoSearchDataModel;
    }

    public MmsStoreInformation getStoreEntity() {
        if (StoreEntity == null) {
            StoreEntity = new MmsStoreInformation();
        }
        return StoreEntity;
    }

    public void setStoreEntity(MmsStoreInformation StoreEntity) {
        this.StoreEntity = StoreEntity;
    }

    /**
     *
     * @return
     */
    public MmsStoreInformation getStoreInfoEntity() {
        if (this.storeInfoEntity == null) {
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

    public MmsStoreInformation getStoreInfoEntityTemp() {
        if (storeInfoEntityTemp == null) {
            storeInfoEntityTemp = new MmsStoreInformation();
        }
        return storeInfoEntityTemp;
    }

    public void setStoreInfoEntityTemp(MmsStoreInformation storeInfoEntityTemp) {
        this.storeInfoEntityTemp = storeInfoEntityTemp;
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

    /**
     *
     * @return
     */
    public boolean isRendersearchByempid4reportid() {
        return rendersearchByempid4reportid;
    }

    /**
     *
     * @return
     */
    public String getCkeckPayrollType() {
        return CkeckPayrollType;
    }

    /**
     *
     * @return
     */
    public boolean getDisbsearchByempid4reportid() {
        return disbsearchByempid4reportid;
    }

    /**
     *
     * @param disbsearchByempid4reportid
     */
    public void setDisbsearchByempid4reportid(boolean disbsearchByempid4reportid) {
        this.disbsearchByempid4reportid = disbsearchByempid4reportid;
    }

    /**
     *
     * @param CkeckPayrollType
     */
    public void setCkeckPayrollType(String CkeckPayrollType) {
        this.CkeckPayrollType = CkeckPayrollType;
    }

    /**
     *
     * @return
     */
    public String getCheckWarehouse() {
        return CheckWarehouse;
    }

    /**
     *
     * @param CheckWarehouse
     */
    public void setCheckWarehouse(String CheckWarehouse) {
        this.CheckWarehouse = CheckWarehouse;
    }

    /**
     *
     * @param rendersearchByempid4reportid
     */
    public void setRendersearchByempid4reportid(boolean rendersearchByempid4reportid) {
        this.rendersearchByempid4reportid = rendersearchByempid4reportid;
    }

    /**
     *
     * @return
     */
    public boolean isRenderWareHouseOneMenu() {
        return renderWareHouseOneMenu;
    }

    /**
     *
     * @param renderWareHouseOneMenu
     */
    public void setRenderWareHouseOneMenu(boolean renderWareHouseOneMenu) {
        this.renderWareHouseOneMenu = renderWareHouseOneMenu;
    }

    /**
     *
     * @return
     */
    public boolean isDisbleWareHouseOneMenu() {
        return disbleWareHouseOneMenu;
    }

    /**
     *
     * @param disbleWareHouseOneMenu
     */
    public void setDisbleWareHouseOneMenu(boolean disbleWareHouseOneMenu) {
        this.disbleWareHouseOneMenu = disbleWareHouseOneMenu;
    }

    /**
     *
     * @return
     */
    public boolean isRenderstoreShelfOnemenu() {
        return renderstoreShelfOnemenu;
    }

    /**
     *
     * @param renderstoreShelfOnemenu
     */
    public void setRenderstoreShelfOnemenu(boolean renderstoreShelfOnemenu) {
        this.renderstoreShelfOnemenu = renderstoreShelfOnemenu;
    }

    /**
     *
     * @return
     */
    public boolean isDisablestoreShelfOnemenu() {
        return disablestoreShelfOnemenu;
    }

    /**
     *
     * @param disablestoreShelfOnemenu
     */
    public void setDisablestoreShelfOnemenu(boolean disablestoreShelfOnemenu) {
        this.disablestoreShelfOnemenu = disablestoreShelfOnemenu;
    }

    public List<MmsShelfInfo> getMmsShelfList() {
        return mmsShelfList;
    }

    public void setMmsShelfList(List<MmsShelfInfo> mmsShelfList) {
        this.mmsShelfList = mmsShelfList;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAddorModify() {
        return AddorModify;
    }

    public void setAddorModify(String AddorModify) {
        this.AddorModify = AddorModify;
    }

    public MmsLocationInfo getSelectedcellCode() {
        return selectedcellCode;
    }

    public void setSelectedcellCode(MmsLocationInfo selectedcellCode) {
        this.selectedcellCode = selectedcellCode;
    }

    public boolean isDisableBtnCreate() {
        return disableBtnCreate;
    }

    public void setDisableBtnCreate(boolean disableBtnCreate) {
        this.disableBtnCreate = disableBtnCreate;
    }

    public boolean isRenderPnlCreateLocationInfo() {
        return renderPnlCreateCellInfo;
    }

    public void setRenderPnlCreateLocationInfo(boolean renderPnlCreateCellInfo) {
        this.renderPnlCreateCellInfo = renderPnlCreateCellInfo;
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

    public String getActiveIndex() {
        return activeIndex;
    }

    public void setActiveIndex(String activeIndex) {
        this.activeIndex = activeIndex;
    }

    public DataModel<MmsLocationInfo> getMmsCellDataModel() {
        if (mmsCellDataModel == null) {
            mmsCellDataModel = new ListDataModel<>();
        }
        return mmsCellDataModel;
    }

    public void setMmsCellDataModel(DataModel<MmsLocationInfo> mmsCellDataModel) {
        this.mmsCellDataModel = mmsCellDataModel;
    }

    //for row selection of cell
    private MmsLocationInfo selectedCellDtl;

    public MmsLocationInfo getSelectedCellDtl() {
        return selectedCellDtl;
    }

    public void setSelectedCellDtl(MmsLocationInfo selectedCellDtl) {
        this.selectedCellDtl = selectedCellDtl;
    }

    public List<MmsLocationInfo> getAddCellList() {
        return addCellList;
    }

    public void setAddCellList(List<MmsLocationInfo> addCellList) {
        this.addCellList = addCellList;
    }

    public List<MmsStoreInformation> getStore() {
        if (store == null) {
            store = new ArrayList<>();
        }
        return store;
    }

    public void setStore(List<MmsStoreInformation> store) {
        this.store = store;
    }

    public List<MmsShelfInfo> getWarehouseLists() {
        if (mmsShelfInfoEntity != null) {
            warehouseNames = mmsShelfInfoInterface.searchShelfInformation(mmsShelfInfoEntity);
        } else {
            warehouseNames = warehouseNametemp;
        }

        return warehouseNames;
    }

    /**
     *
     * @return
     */
    public List<MmsShelfInfo> getWarehouseShelfLists() {
        WarhouseShelfNames = mmsShelfInfoInterface.searchWarehouseShelfInformation(mmsShelfInfoEntity);

        return WarhouseShelfNames;
    }
//</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Event Handlers and other Methods">

    public String addCellInfoDetail() {

        if (checkCellCode.contains(mmsCellInfoDtlEntity.getCellCode())) {
            JsfUtil.addErrorMessage("Cell Code: Value is Duplicated");
            return null;
        } else {
            checkCellCode.add(mmsCellInfoDtlEntity.getCellCode());

        }

        clearPopUp2();
        return null;

    }

    public void addCells() {
        if (checkRackCode.contains(mmsShelfInfoEntity.getRackCode()) && checkCellCode.contains(mmsCellInfoEntity.getCellCode()) && checkShelfRow.contains(mmsCellInfoEntity.getCellRow())) {
            JsfUtil.addFatalMessage("Cell Code: Value is Duplicated");

        } else {

            if (updatedtlstatus == 1) {
                System.out.println("============= inside else if=============");
//              
                checkRackCode.add(mmsShelfInfoEntity.getRackCode());
                checkCellCode.add(mmsCellInfoEntity.getCellCode());
                recreateCellModel();
                clearPopUp();
            } else {
                System.out.println("====inside else =============here=======");

                addCellList.add(mmsCellInfoEntity);

                checkRackCode.add(mmsShelfInfoEntity.getRackCode());
                checkCellCode.add(mmsCellInfoEntity.getCellCode());
                checkShelfRow.add(mmsCellInfoEntity.getCellRow());
                recreateCellModel();
                clearPopUp();
            }
        }
    }

    public void recreateCellModel() {
        mmsCellDataModel = null;
        mmsCellDataModel = new ListDataModel(addCellList);
    }

    /**
     *
     * @return
     */
    public String clearPage() {
        StoreListForCell.clear();
        WarhouseShelfNames.clear();

        mmsShelfInfoEntity = new MmsShelfInfo();
        mmsCellInfoEntity = new MmsLocationInfo();
        storeInfoEntity = new MmsStoreInformation();
        storeInfoEntityTemp = new MmsStoreInformation();
        wfMmsProcessed = null;
        mmsCellDataModel = null;
        updateStatus = 0;
        saveorUpdateBundle = "Save";
        mmsCellInfoSearchDataModel = new ListDataModel<>();

        return null;
    }

    /**
     *
     * @param event
     */
    public void handleSelectStoreName(ValueChangeEvent event) {
        if (null != event.getNewValue()) {
            storeInfoEntity = (MmsStoreInformation) event.getNewValue();
            System.out.println("========Store id inside handler=======" + storeInfoEntity.getStoreName());
            System.out.println("========Store id inside handler=======" + storeInfoEntity.getStoreId());
            mmsShelfInfoEntity.setStoreId(storeInfoEntity);

//         
        }

    }

    public void handleSelectStoreNameSearch(ValueChangeEvent event) {
        if (null != event.getNewValue()) {
            Integer Id = Integer.parseInt(event.getNewValue().toString());
            storeInfoEntityTemp.setStoreId(Id);

//         
//         
        }

    }

    /**
     *
     * @param event
     */
    public void handleSelectStoreNameForWarehouse(ValueChangeEvent event) {
        if (null != event.getNewValue()) {
            if (CheckWarehouse == "0") {
                storeInfoEntity.setStoreName(event.getNewValue().toString());
                storeInfoEntity = storeInfoInterface.getPapmsStoreInformation(storeInfoEntity);
                getShelfLists();
            } else {
                getShelfLists().clear();

                storeInfoEntity.setStoreName(event.getNewValue().toString());
                storeInfoEntity = storeInfoInterface.getPapmsStoreInformation(storeInfoEntity);
                getWarehouseLists();
                //shelfNames=;
//           shelfNames.clear();
            }
        }

    }

    /**
     *
     * @param event
     */
    public void handleSelectWarehouseName(ValueChangeEvent event) {
        if (null != event.getNewValue()) {
            mmsShelfInfoEntity.setShadeName(event.getNewValue().toString());
            getWarehouseShelfLists();

        }

    }

    /**
     *
     * @param event
     */
    public void handleSelectWarehouseShelfName(ValueChangeEvent event) {
        if (null != event.getNewValue()) {
            Integer warehouseshelfid = Integer.parseInt(event.getNewValue().toString());
            mmsShelfInfoEntity.setShelfId(warehouseshelfid);
            mmsCellInfoEntity.setClosedShadeName(mmsShelfInfoEntity.getShadeName());
            mmsShelfInfoEntity = mmsShelfInfoInterface.getClosedShadeRackInformationByShelfId(mmsShelfInfoEntity, storeInfoEntity);
            mmsCellInfoEntity.setShelfId(mmsShelfInfoEntity);

        }

    }

    /**
     *
     * @param event
     */
    public void handleSelectShelfCode(ValueChangeEvent event) {
        if (null != event.getNewValue()) {
            Integer shelfid = Integer.parseInt(event.getNewValue().toString());
            mmsShelfInfoEntity.setShelfId(shelfid);
            mmsCellInfoEntity.setShelfId(mmsShelfInfoEntity);

            mmsCellInfoEntity = mmsCellInfoInterface.getMmsCellInformation(mmsCellInfoEntity);

//               
        }

//       
//            
    }

    public void handleSelectStoreShelfCode(ValueChangeEvent event) {
        if (null != event.getNewValue()) {
            mmsShelfInfoEntity = (MmsShelfInfo) event.getNewValue();
            mmsCellInfoEntity.setShelfId(mmsShelfInfoEntity);
            System.out.println("=====shelf inside hadnle====" + mmsCellInfoEntity.getShelfId().getShelfId());
        }
    }

    /**
     *
     * @param e
     */
    public void changeCkeckPayrollType(ValueChangeEvent e) {
        if (null != e.getNewValue()) {
            if (e.getNewValue().toString().equalsIgnoreCase("1")) {

                setRendersearchByempid4reportid(true);
                setDisbsearchByempid4reportid(false);

            } else if (e.getNewValue().toString().equalsIgnoreCase("2")) {

                setRendersearchByempid4reportid(false);
                setDisbsearchByempid4reportid(true);
                mmsShelfInfoEntity.setShadeName("no warehouse");
            }

        }
    }

    /**
     *
     * @param ev
     */
    private void clearPopUp() {
        mmsCellInfoEntity = new MmsLocationInfo();
        mmsShelfInfoEntity = new MmsShelfInfo();
        storeInfoEntity = new MmsStoreInformation();
        StoreEntity = new MmsStoreInformation();
        mmsShelfList.clear();
        updatedtlstatus = 0;
    }

    private void clearPopUp2() {

        mmsCellInfoDtlEntity = null;
    }

    public void updateCellInfoDetail(SelectEvent event) {
        System.out.println("========rowselction====");
        AddorModify = "Modify";
        mmsCellInfoEntity = (MmsLocationInfo) event.getObject();
        mmsShelfInfoEntity = mmsCellInfoEntity.getShelfId();
        storeInfoEntity = mmsShelfInfoEntity.getStoreId();
        updatedtlstatus = 1;
        mmsShelfList = new ArrayList<>();
        mmsShelfList.add(mmsShelfInfoEntity);
        StoreListForCell = new ArrayList<>();
        StoreListForCell.add(storeInfoEntity);

        if (Objects.equals(mmsCellInfoEntity.getCellType(), storeRackValue)) {
            setCheckWarehouse("0");
        } else {

            setCheckWarehouse("1");
        }
    }

    public void enabledisableWarehouse(ValueChangeEvent ev) {
        clearPage();
        if (null != ev.getNewValue()) {
            if (ev.getNewValue().toString().equalsIgnoreCase("0")) {

                setDisbleWareHouseOneMenu(true);
                setRenderWareHouseOneMenu(false);
                setRenderstoreShelfOnemenu(true);
                setDisablestoreShelfOnemenu(false);
                setCheckWarehouse("0");

            } else if (ev.getNewValue().toString().equalsIgnoreCase("1")) {
                setRenderWareHouseOneMenu(true);
                setDisbleWareHouseOneMenu(false);
                setRenderstoreShelfOnemenu(false);
                setDisablestoreShelfOnemenu(true);
                setCheckWarehouse("1");
            }

        }
    }

    public void handleSelectoptionRadioButton(ValueChangeEvent event) {
        if (null != event.getNewValue()) {
            mmsShelfList = new ArrayList<>();
            if (event.getNewValue().toString().equals("0")) {
                mmsShelfList = mmsShelfInfoInterface.searchRackForStoreByStoreId(storeInfoEntity);
                mmsCellInfoEntity.setCellType(storeRackValue);
            } else if (event.getNewValue().toString().equals("1")) {
                mmsShelfList = mmsShelfInfoInterface.searchRackForClosedShadeByStoreId(storeInfoEntity);
                mmsCellInfoEntity.setCellType(closedShadeRackValue);
            }
        }
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
     * @return
     */
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Save Methods and Search">
     /*This method is used to  Save the page
     */
    public String save() {

        if (updateStatus == 0) { //&& result==false) {
            mmsShelfInfoEntity.setStoreId(storeInfoEntity);
            mmsShelfInfoInterface.create(mmsShelfInfoEntity);

            JsfUtil.addSuccessMessage("Shelf Information is Saved");
        } else if (updateStatus == 2) {//&& result == true){
            JsfUtil.addSuccessMessage("Duplicate Information is not allowed");
        } else {
            mmsShelfInfoInterface.edit(mmsShelfInfoEntity);
            JsfUtil.addSuccessMessage("Shelf Information is upadted");
        }
        clearPage();
        return null;
    }

    public void columnNameChangeEvent(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            System.out.println("event.getNewValue().toString()==" + event.getNewValue().toString());
            columnNameResolver.setCol_Name_FromTable(event.getNewValue().toString());
            columnNameResolver.setParsed_Col_Name(ColumnParser(columnNameResolver.getCol_Name_FromTable()));
            mmsCellInfoEntity.setColumnName(columnNameResolver.getCol_Name_FromTable());
            mmsCellInfoEntity.setColumnValue(null);
        }
    }

    public String ColumnParser(String col_name) {
        String col = col_name.replace("_", " ");
        System.out.println("col==" + col);
        return col;
    }

    public List<String> getMmsShelfColumnNameList() {
        mmsShelfColumnNameList = mmsCellInfoInterface.getMmsShelfColumnNameList();
        if (mmsShelfColumnNameList == null) {
            mmsShelfColumnNameList = new ArrayList<>();
        }
        System.out.println("=======mmsStoreColumnNameList==" + mmsShelfColumnNameList);
        if (mmsShelfColumnNameList.size() > 0) {
            ColumnNamesList = new ArrayList<>();
            for (int i = 0; i < mmsShelfColumnNameList.size(); i++) {
                ColumnNameResolver obj = new ColumnNameResolver();
                obj.setCol_Name_FromTable((mmsShelfColumnNameList.get(i).toLowerCase()));
                obj.setParsed_Col_Name(ColumnParser((mmsShelfColumnNameList.get(i).toLowerCase())));
                ColumnNamesList.add(obj);
            }

        }
        return mmsShelfColumnNameList;
    }

    public void setMmsShelfColumnNameList(List<String> mmsShelfColumnNameList) {
        this.mmsShelfColumnNameList = mmsShelfColumnNameList;
    }

    /**
     *
     * @return
     */
    /*This method is used to  Save the Workflow
     */
    public void WfSave() {
        wfMmsProcessedBeanLocal.create(wfMmsProcessed);
    }
    /*This method is used to Save cell information
     */

    public void save_Cell_Information() {
        try {
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(SessionBean.getUserName(), "save_Cell_Information", dataset)) {
                if (mmsCellDataModel.getRowCount() <= 0) {
                    JsfUtil.addFatalMessage("No record added to the Table!");
                } else {
                    if (updateStatus == 0) {
                        savingObjectLoop();
                        JsfUtil.addSuccessMessage("Cell Information is saved");

                    } else if (updateStatus == 2) {//&& result == true){
                        JsfUtil.addFatalMessage("Duplicate Information is not allowed");
                    } else {
                        savingObjectLoop();
                        JsfUtil.addSuccessMessage("Cell information is updated");
                        saveorUpdateBundle = "Save";
                    }
                    clearPage();
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
    /*This method is used to  SavingObject Loop
     */

    public void savingObjectLoop() {
        for (int i = 0; i < addCellList.size(); i++) {
            MmsLocationInfo savingObj = new MmsLocationInfo();
            savingObj.setShelfId(addCellList.get(i).getShelfId());
            System.out.println("========shelfssssssss======" + addCellList.get(i).getShelfId());
            savingObj.setClosedShadeName(addCellList.get(i).getClosedShadeName());
            System.out.println("====Cell code inside save===" + addCellList.get(i).getCellCode());
            savingObj.setCellCode(addCellList.get(i).getCellCode());
            savingObj.setCellRow(addCellList.get(i).getCellRow());
            savingObj.setStatus(Constants.PREPARE_VALUE);
            savingObj.addCellIdToWorkflow(wfMmsProcessed);
            savingObj.setProcessedOn(wfMmsProcessed.getProcessedOn());

            savingObj.setProcessedBy(SessionBean.getUserId());

            if (updateStatus == 0) {
                mmsCellInfoInterface.create(savingObj);
            } else if (updateStatus == 1) {
                mmsCellInfoInterface.saveOrUpdate(savingObj);
            }

        }
    }
    /*This method is used to  Set work flow value 
     */

    public void setWorkFlowValues() {
        mmsCellInfoEntity.setProcessedOn(wfMmsProcessed.getProcessedOn());

        mmsCellInfoEntity.setProcessedBy(wfMmsProcessed.getProcessedBy());
    }

    /**
     *
     * @return
     */
    public List<MmsShelfInfo> getShelfLists() {

        if (mmsShelfInfoEntity != null) {
            //mmsShelfInfoEntity.setStoreId(storeInfoEntity);
            getshelfs = mmsShelfInfoInterface.searchStoreShelf(mmsShelfInfoEntity);
        }
        return getshelfs;
    }
    /*This method is used to  getshelfData
     */

    public SelectItem[] getShelfData() {
        if (storeInfoEntity.getStoreName() != null) {
            return JsfUtil.getSelectItems(mmsShelfInfoInterface.searchStoreShelf(mmsShelfInfoEntity), true);
        } else {
            SelectItem[] items = new SelectItem[1];
            items[0] = new SelectItem("", "--First Select Store --");
            return items;
        }
    }

    /*This method is used to  go back searching Actions
     */
    public void goBackSearchButtonAction() {
        renderpnlToSearchPage = false;
        renderPnlCreateCellInfo = false;
        renderPnlManPage = true;
    }
    /*This method is used to  create new cell informations 
     */

    public void createNewCellInfo() {
        //
        clearPage();
        saveorUpdateBundle = "Save";
        disableBtnCreate = false;
        if (createOrSearchBundle.equals("New")) {
            renderPnlCreateCellInfo = true;
            store = storeInfoInterface.findAllStoreInfo();

            renderPnlManPage = false;
            createOrSearchBundle = "Search";
            setIcone("ui-icon-search");
        } else if (createOrSearchBundle.equals("Search")) {
            renderPnlCreateCellInfo = false;
            renderPnlManPage = true;
            createOrSearchBundle = "New";
            setIcone("ui-icon-plus");
        }
    }
    /*This method is used to  Search 
     */

    public void searchByParameter() {
        System.out.println("in search");
        storeInfoEntity.setProcessedBy(SessionBean.getUserId());
        System.out.println("processor " + storeInfoEntity.getProcessedBy());
        allCellinfoList = mmsCellInfoInterface.getShelfListsByParameter(columnNameResolver, mmsCellInfoEntity, mmsCellInfoEntity.getColumnValue());
        recerateSearchModel();

    }
    
    private void recerateSearchModel() {
        mmsCellInfoSearchDataModel = null;
        mmsCellInfoSearchDataModel = new ListDataModel(new ArrayList<>(allCellinfoList));

    }
    /*This method is used to  On Row Editing
     */

    public void onRowEdit(SelectEvent event) {
        mmsCellInfoEntity = (MmsLocationInfo) event.getObject();
        addCellList = new ArrayList<>();
        addCellList.add(mmsCellInfoEntity);

        renderPnlCreateCellInfo = true;

        renderPnlManPage = false;
        renderpnlToSearchPage = true;
        activeIndex = "0";
        saveorUpdateBundle = "Update";
        createOrSearchBundle = "New";
        setIcone("ui-icon-plus");
        disableBtnCreate = true;
        updateStatus = 1;
        wfMmsProcessed.setProcessedOn(mmsCellInfoEntity.getProcessedOn());
//       
        recreateCellModel();
        mmsShelfInfoEntity = new MmsShelfInfo();
        mmsCellInfoEntity = new MmsLocationInfo();

//       
    }
//</editor-fold>
}
