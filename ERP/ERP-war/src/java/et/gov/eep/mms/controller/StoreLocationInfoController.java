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
import et.gov.eep.mms.businessLogic.MmsLuOutdoorTypeBeanLocal;
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
import java.util.Set;
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
import org.primefaces.event.SelectEvent;
import securityBean.Constants;
import securityBean.SessionBean;
import securityBean.WorkFlow;
import webService.AAA;
import webService.EventEntry;
import webService.IAdministration;

/**
 *
 * @author Minab
 */
@Named(value = "storeLocationInfoController")
@ViewScoped
public class StoreLocationInfoController implements Serializable {
    //<editor-fold defaultstate="collapsed" desc="EJB's">

    /**
     * Creates a new instance of StoreLocationInfoController
     */
    @EJB
    WfMmsProcessedBeanLocal wfMmsProcessedBeanLocal;
    @EJB
    MmsShelfInfoBeanLocal mmsShelfInfoInterface;
    @EJB
    MmsLuOutdoorTypeBeanLocal outdoorTypeBeanLocal;
    @EJB
    MmsLocationInfoBeanLocal mmsCellInfoInterface;
    @EJB
    private MmsStoreInformationBeanLocal storeInfoInterface;
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Entity's">
    @Inject
    private MmsShelfInfo mmsShelfInfoEntity;
    @Inject
    private MmsShelfInfo mmsShelfInfoEntitytemp;
    @Inject
    WfMmsProcessed wfMmsProcessed;
    @Inject
    private MmsLocationInfo mmsCellInfoEntity;
    @Inject
    private MmsLocationInfoDtl mmsCellInfoDtlEntity;
    @Inject
    private MmsStoreInformation storeInfoEntity;
    @Inject
    private MmsStoreInformation storeInfoEntitytemp;
    @Inject
    SessionBean SessionBean;
    @Inject
    WorkFlow workflow;
    @Inject
    ColumnNameResolver columnNameResolver;
//</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Fields">
    private DataModel<MmsLocationInfoDtl> mmsCellInfoDtlDataModel;
    private DataModel<MmsLocationInfo> mmsCellInfoDataModel;
    private DataModel<MmsShelfInfo> mmsShelfSearchInfoDataModel;
    private DataModel<MmsShelfInfo> mmsShelfDataModel;
    private boolean renderDecision = false;
    private boolean rendersearchByempid4reportid = false;
    private boolean disbsearchByempid4reportid = true;
    private boolean renderWareHouseOneMenu = false;
    private boolean disbleWareHouseOneMenu = true;
    private boolean renderstoreShelfOnemenu = true;
    private boolean renderForStore = true;
    private boolean renderForClosedShade = false;
    private boolean disablestoreShelfOnemenu = false;
    private boolean renderOutdoorInfo = false;
    private boolean renderRackCode = false;
    private boolean disableBtnCreate;
    private boolean renderPnlCreateLocationInfo = false;
    private boolean renderPnlManPage = true;
    private boolean renderpnlToSearchPage;
    private String createOrSearchBundle = "New";
    private String icone = "ui-icon-plus";
    private String activeIndex;
    private String saveorUpdateBundle = "Save";
    int updateStatus = 0;
    private String CkeckPayrollType = "2";
    private String CheckWarehouse = "0";
    private String outdoorNameBundle = "Code:";
    private String userName;
    DataModel<WfMmsProcessed> WorkflowDataModel = new ListDataModel<>();
    List<WfMmsProcessed> WfList;
    private String AddorModify = "Add";
    Set<String> checkCellCode = new HashSet<>();
    Set<String> checkShelfCode = new HashSet<>();
    Set<String> checkRackOption = new HashSet<>();
    Set<String> checkStoreName = new HashSet<>();
    String selectedValue = "";
    private MmsShelfInfo selectedItem;
    List<MmsShelfInfo> searchedShelf;
    List<MmsShelfInfo> allShelfinfoList;
    List<MmsStoreInformation> StoreList;
    List<MmsShelfInfo> addShelfsList = new ArrayList<>();
    List<MmsShelfInfo> newList = new ArrayList<>();
    int updatedtlstatus = 0;
    int updateIndex;
    int selectedRow;
    private MmsShelfInfo selectedShelf;
    private List<MmsShelfInfo> mmsShelfInfoColumnNameList;
    private List<String> mmsStoreColumnNameList;
    ColumnNames columnNames = new ColumnNames();
    List<ColumnNameResolver> ColumnNamesList = new ArrayList<>();
//</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Constructor">

    /**
     *
     */
    public StoreLocationInfoController() {
    }

    @PostConstruct
    public void init() {
        if (workflow.isApproveStatus() || workflow.isCheckStatus()) {
            System.out.println("app or check");
            renderDecision = true;
        } else if (workflow.isPrepareStatus()) {
            System.out.println("prep");
            renderDecision = false;
        }
        wfMmsProcessed.setProcessedBy(SessionBean.getUserId());
        setUserName(SessionBean.getUserName());
        System.out.println("is App==" + workflow.isApproveStatus() + "is chech==" + workflow.isCheckStatus() + "is prepa==" + workflow.isPrepareStatus());
        getMmsStoreColumnNameList();
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

    public ColumnNameResolver getColumnNameResolver() {
        return columnNameResolver;
    }

    public void setColumnNameResolver(ColumnNameResolver columnNameResolver) {
        this.columnNameResolver = columnNameResolver;
    }

    public ColumnNames getColumnNames() {
        return columnNames;
    }

    public void setColumnNames(ColumnNames columnNames) {
        this.columnNames = columnNames;
    }

    public List<ColumnNameResolver> getColumnNamesList() {
        return ColumnNamesList;
    }

    public void setColumnNamesList(List<ColumnNameResolver> ColumnNamesList) {
        this.ColumnNamesList = ColumnNamesList;
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
    /**
     *
     * @return
     */
    public DataModel<MmsLocationInfoDtl> getMmsCellInfoDtlDataModel() {
        if (mmsCellInfoDtlDataModel == null) {
            mmsCellInfoDtlDataModel = new ListDataModel<>();
        }
        return mmsCellInfoDtlDataModel;
    }

    public DataModel<MmsLocationInfo> getMmsCellInfoDataModel() {
        if (mmsCellInfoDataModel == null) {
            mmsCellInfoDataModel = new ListDataModel<>();
        }
        return mmsCellInfoDataModel;
    }

    public void setMmsCellInfoDataModel(DataModel<MmsLocationInfo> mmsCellInfoDataModel) {
        this.mmsCellInfoDataModel = mmsCellInfoDataModel;
    }

    /**
     *
     * @param mmsCellInfoDtlDataModel
     */
    public void setMmsCellInfoDtlDataModel(DataModel<MmsLocationInfoDtl> mmsCellInfoDtlDataModel) {
        this.mmsCellInfoDtlDataModel = mmsCellInfoDtlDataModel;
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

    public String getOutdoorNameBundle() {
        return outdoorNameBundle;
    }

    public void setOutdoorNameBundle(String outdoorNameBundle) {
        this.outdoorNameBundle = outdoorNameBundle;
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

    public boolean isRenderForStore() {
        return renderForStore;
    }

    public void setRenderForStore(boolean renderForStore) {
        this.renderForStore = renderForStore;
    }

    public boolean isRenderForClosedShade() {
        return renderForClosedShade;
    }

    public void setRenderForClosedShade(boolean renderForClosedShade) {
        this.renderForClosedShade = renderForClosedShade;
    }

    public MmsShelfInfo getMmsShelfInfoEntitytemp() {
        if (mmsShelfInfoEntitytemp == null) {
            mmsShelfInfoEntitytemp = new MmsShelfInfo();
        }
        return mmsShelfInfoEntitytemp;
    }

    public void setMmsShelfInfoEntitytemp(MmsShelfInfo mmsShelfInfoEntitytemp) {
        this.mmsShelfInfoEntitytemp = mmsShelfInfoEntitytemp;
    }

    public MmsStoreInformation getStoreInfoEntitytemp() {
        if (storeInfoEntitytemp == null) {
            storeInfoEntitytemp = new MmsStoreInformation();
        }
        return storeInfoEntitytemp;
    }

    public void setStoreInfoEntitytemp(MmsStoreInformation storeInfoEntitytemp) {
        this.storeInfoEntitytemp = storeInfoEntitytemp;
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

    public List<MmsStoreInformation> getStoreList() {
        StoreList = storeInfoInterface.findAllStoreInfo();
        return StoreList;
    }

    public boolean isDisableBtnCreate() {
        return disableBtnCreate;
    }

    public void setDisableBtnCreate(boolean disableBtnCreate) {
        this.disableBtnCreate = disableBtnCreate;
    }

    public boolean isRenderPnlCreateLocationInfo() {
        return renderPnlCreateLocationInfo;
    }

    public void setRenderPnlCreateLocationInfo(boolean renderPnlCreateLocationInfo) {
        this.renderPnlCreateLocationInfo = renderPnlCreateLocationInfo;
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

    public DataModel<MmsShelfInfo> getMmsShelfSearchInfoDataModel() {
        if (mmsShelfSearchInfoDataModel == null) {
            mmsShelfSearchInfoDataModel = new ListDataModel<>();
        }
        return mmsShelfSearchInfoDataModel;
    }

    public void setMmsShelfSearchInfoDataModel(DataModel<MmsShelfInfo> mmsShelfSearchInfoDataModel) {
        this.mmsShelfSearchInfoDataModel = mmsShelfSearchInfoDataModel;
    }

    public DataModel<MmsShelfInfo> getMmsShelfDataModel() {
        if (mmsShelfDataModel == null) {
            mmsShelfDataModel = new ListDataModel<>();
        }
        return mmsShelfDataModel;
    }

    public void setMmsShelfDataModel(DataModel<MmsShelfInfo> mmsShelfDataModel) {
        this.mmsShelfDataModel = mmsShelfDataModel;
    }

    //for row selection of shelf
    public MmsShelfInfo getSelectedItem() {
        return selectedItem;
    }

    public void setSelectedItem(MmsShelfInfo selectedItem) {
        this.selectedItem = selectedItem;
    }

    public MmsShelfInfo getSelectedShelf() {
        return selectedShelf;
    }

    public void setSelectedShelf(MmsShelfInfo selectedShelf) {
        this.selectedShelf = selectedShelf;
    }

    public SelectItem[] getListOfOutdoorType() {
        return JsfUtil.getSelectItems(outdoorTypeBeanLocal.findAll(), true);
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

    public List<MmsShelfInfo> getMmsShelfInfoColumnNameList() {
        if (mmsShelfInfoColumnNameList == null) {
            mmsShelfInfoColumnNameList = new ArrayList<>();
//            mmsShelfInfoColumnNameList = mmsShelfInfoInterface.getMmsShelfInfoColumnNameList();

        }
        return mmsShelfInfoColumnNameList;
    }

    public void setMmsShelfInfoColumnNameList(List<MmsShelfInfo> mmsShelfInfoColumnNameList) {
        this.mmsShelfInfoColumnNameList = mmsShelfInfoColumnNameList;
    }

//</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Event Handlers and other Methods">
    private void clearPopUp() {
        setRenderOutdoorInfo(false);
        setRenderForClosedShade(false);
        mmsShelfInfoEntity = new MmsShelfInfo();
        storeInfoEntity = new MmsStoreInformation();
        updatedtlstatus = 0;

    }

    private void clearPopUp2() {
        mmsCellInfoDtlEntity = null;
    }

    /**
     *
     */
    public void recreateModelDetail() {
        mmsShelfDataModel = null;
        mmsShelfDataModel = new ListDataModel(new ArrayList<>(searchedShelf));

    }

    public void recreateModelDetail2() {

        mmsCellInfoDtlDataModel = null;

        // mmsCellInfoDtlDataModel = new ListDataModel(new ArrayList<>(mmsCellInfoEntity.getMmsLocationInfoDtlList()));
    }

    public void recreateModelDetailCellInfo() {

        mmsCellInfoDataModel = null;

        // mmsCellInfoDataModel = new ListDataModel(new ArrayList<>(cellsInsideShelf));
    }

    public void updateShelfInfoDetail(SelectEvent event) {
        mmsShelfInfoEntity = (MmsShelfInfo) event.getObject();
        AddorModify = "Modify";
        storeInfoEntity = mmsShelfInfoEntity.getStoreId();
//        if(mmsShelfInfoEntity.getSelectOption1()==2){
//            setRenderForStore(true);
//            setRenderForClosedShade(false);
        if (mmsShelfInfoEntity.getRackOrOutdoor() == 0) {
            //outdoor==1 and Rack==0
            setRenderRackCode(true);
            setRenderOutdoorInfo(false);
            setOutdoorNameBundle("Rack Code:");
        } else if (mmsShelfInfoEntity.getRackOrOutdoor() == 1) {
            setRenderRackCode(false);
            setRenderOutdoorInfo(true);
            if (mmsShelfInfoEntity.getHasArack() != null) {
                if (mmsShelfInfoEntity.getHasArack() == 0) {
                    setRenderForClosedShade(true);
                    setOutdoorNameBundle("Rack Code:");
                } else {
                    setRenderForClosedShade(true);
                    setOutdoorNameBundle("Code:");
                }
            }
        }
        // }
//        else if(mmsShelfInfoEntity.getSelectOption1()==1){
//             setRenderForStore(false);
//            setRenderForClosedShade(true);
//            if(mmsShelfInfoEntity.getSelectOption2()==0){
//                //outdoor==1 and Rack==0
//                setRenderRackCode(true);
//                setRenderOutdoorInfo(false);
//            }
//            else{
//                 setRenderRackCode(false);
//                setRenderOutdoorInfo(true);
//            }
//        }
        updatedtlstatus = 1;
        updateIndex = addShelfsList.indexOf(mmsShelfInfoEntity);        //addShelfsList.remove((MmsShelfInfo) event.getObject());
//        System.out.println("row index of the table=======" + mmsShelfDataModel.getRowIndex());
//        selectedRow = mmsShelfDataModel.getRowIndex();
//        mmsShelfDtlEntity = getMmsShelfInfoDtlDataModel().getRowData();
    }

    public void updateCellInfoDetail() {
        mmsCellInfoDtlEntity = getMmsCellInfoDtlDataModel().getRowData();

    }

    /**
     *
     * @return
     */
//    public String addShelfInfoDetail() {
//
//        //checkShelfCode.clear();
//        if (checkShelfCode.contains(mmsShelfDtlEntity.getRackCode())) {
//            JsfUtil.addErrorMessage("Shelf Code: Value is Duplicated");
//            return null;
//        } else {
//
//            // mmsShelfInfoEntity.addToShelfInfoDetail(mmsShelfDtlEntity);
//            checkShelfCode.add(mmsShelfDtlEntity.getRackCode());
//
//        }
//        recreateModelDetail();
//        clearPopUp();
//        return null;
//
//    }
    public void addShelfs() {
        if (checkShelfCode.contains(mmsShelfInfoEntity.getRackCode())) {
            for (int i = 0; i < addShelfsList.size(); i++) {
                if (addShelfsList.get(i).getRackCode().equals(mmsShelfInfoEntity.getRackCode())) {
                    addShelfsList.get(i).setRackCode(mmsShelfInfoEntity.getRackCode());
                    addShelfsList.get(i).setOutdoorCode(mmsShelfInfoEntity.getOutdoorCode());
                    addShelfsList.get(i).setShadeName(mmsShelfInfoEntity.getShadeName());
                    addShelfsList.get(i).setStoreId(storeInfoEntity);
                }
            }

            //  JsfUtil.addSuccessMessage("Shelf Code: Value is Duplicated");
        } else if (!checkShelfCode.contains(mmsShelfInfoEntity.getRackCode()) && updatedtlstatus == 0) {
            mmsShelfInfoEntity.setStoreId(storeInfoEntity);
            System.out.println("===========store id==" + mmsShelfInfoEntity.getStoreId());
            newList.add(mmsShelfInfoEntity);
            addShelfsList = newList;
            checkShelfCode.add(mmsShelfInfoEntity.getRackCode());
            System.out.println("store in the list====" + addShelfsList.get(0).getStoreId().getStoreName());
            recreateShelfModel();
            System.out.println("===========store====" + mmsShelfDataModel.getRowData().getStoreId().getStoreName());
            clearPopUp();
        }
    }

    public void addShelfs2() {
        if (checkStoreName.contains(mmsShelfInfoEntity.getStoreId().getStoreName()) && checkRackOption.contains(mmsShelfInfoEntity.getRackOrOutdoor().toString()) && checkShelfCode.contains(mmsShelfInfoEntity.getRackCode())) {
            JsfUtil.addFatalMessage("Duplicate Data is not allowed");
            clearPopUp();
            System.out.println("=============finished inside if=============");
        } else {
            if (updatedtlstatus == 1) {
                System.out.println("============= inside else if=============");

                checkStoreName.add(mmsShelfInfoEntity.getStoreId().getStoreName());
                checkRackOption.add(mmsShelfInfoEntity.getRackOrOutdoor().toString());
                checkShelfCode.add(mmsShelfInfoEntity.getRackCode());
                recreateShelfModel();
                clearPopUp();
            } else {
                System.out.println("===========inside Else else============");
                mmsShelfInfoEntity.setStoreId(storeInfoEntity);
                newList.add(mmsShelfInfoEntity);
                addShelfsList = newList;
                checkStoreName.add(mmsShelfInfoEntity.getStoreId().getStoreName());
                checkRackOption.add(mmsShelfInfoEntity.getRackOrOutdoor().toString());
                checkShelfCode.add(mmsShelfInfoEntity.getRackCode());

                System.out.println("store in the list====" + addShelfsList.get(0).getStoreId().getStoreName());
                recreateShelfModel();
                System.out.println("===========store====" + mmsShelfDataModel.getRowData().getStoreId().getStoreName());
                clearPopUp();
            }
        }
    }

    public void recreateShelfModel() {
        mmsShelfDataModel = null;
        mmsShelfDataModel = new ListDataModel(addShelfsList);
    }

    /**
     *
     * @return
     */
    public String addCellInfoDetail() {

        //checkShelfCode.clear();
        if (checkCellCode.contains(mmsCellInfoDtlEntity.getCellCode())) {
            JsfUtil.addErrorMessage("Cell Code: Value is Duplicated");
            return null;
        } else {

            //mmsCellInfoEntity.addToCellInfoDetail(mmsCellInfoDtlEntity);
            checkCellCode.add(mmsCellInfoDtlEntity.getCellCode());

        }
        recreateModelDetail2();
        clearPopUp2();
        return null;

    }

    /**
     *
     * @return
     */
    public String clearPage() {

        mmsShelfInfoEntity = new MmsShelfInfo();
        mmsCellInfoEntity = new MmsLocationInfo();
        storeInfoEntity = new MmsStoreInformation();

        mmsCellInfoDtlEntity = new MmsLocationInfoDtl();
        mmsShelfDataModel = null;
        wfMmsProcessed = null;
        mmsCellInfoDtlDataModel = null;
        mmsShelfSearchInfoDataModel = new ListDataModel<>();
        updateStatus = 0;
        saveorUpdateBundle = "Save";

        return null;
    }

    /**
     *
     * @param event
     */
    public void handleSelectStoreName(ValueChangeEvent event) {
        if (null != event.getNewValue()) {

            Integer Id = Integer.parseInt(event.getNewValue().toString());
            storeInfoEntity.setStoreId(Id);
            storeInfoEntity = storeInfoInterface.searchByStoreId(storeInfoEntity);
            //getMmsShelfInfoEntity();
            mmsShelfInfoEntity.setStoreId(storeInfoEntity);
//         

        }

    }

    public void handleSelectStoreNameSearch(ValueChangeEvent event) {
        if (null != event.getNewValue()) {
            Integer Id = Integer.parseInt(event.getNewValue().toString());
            storeInfoEntitytemp.setStoreId(Id);
            storeInfoEntitytemp = storeInfoInterface.searchByStoreId(storeInfoEntitytemp);
            mmsShelfInfoEntity.setStoreId(storeInfoEntitytemp);

//         
        }

    }

    public void handleSelectOption1(ValueChangeEvent event) {
        if (null != event.getNewValue()) {
            System.out.println("event value===========" + event.getNewValue().toString());
            if ("2".equals(event.getNewValue().toString())) {
                //option 2 is for store
                setRenderForStore(true);
                setRenderForClosedShade(false);
                System.out.println("===========inside if====");
                mmsShelfInfoEntity.setSelectOption1(2);
            } else {
                //option 1 is for closed shade
                setRenderForClosedShade(true);
                setRenderForStore(false);
                System.out.println("===========inside else====");
                mmsShelfInfoEntity.setSelectOption1(1);
            }
        }
    }

    public void handleSelectRackOrOutdoor(ValueChangeEvent event) {
        if (null != event.getNewValue()) {
            System.out.println("event value===========" + event.getNewValue().toString());
            if ("0".equals(event.getNewValue().toString())) {
                //option 0 is for Rack
                setRenderRackCode(true);
                setRenderOutdoorInfo(false);
                setOutdoorNameBundle("Rack Code:");

                System.out.println("===========inside if====");
                mmsShelfInfoEntity.setRackOrOutdoor(0);
            } else {
                //option 1 is for outdoor
                setRenderRackCode(false);
                setRenderOutdoorInfo(true);
                System.out.println("===========inside else====");
                setOutdoorNameBundle("Rack Code:");
                mmsShelfInfoEntity.setRackOrOutdoor(1);
            }
        }
    }

    /**
     *
     * @param event
     */
    public void handleSelectHasArack(ValueChangeEvent event) {

        if (null != event.getNewValue()) {
            System.out.println("========event value");
            if ("0".equals(event.getNewValue().toString())) {
                //option 0 is for Yes(HAS RACK)
                setRenderRackCode(true);
                setRenderOutdoorInfo(false);
                setOutdoorNameBundle("Rack Code:");

                System.out.println("===========inside if====" + mmsShelfInfoEntity.getHasArack());
                mmsShelfInfoEntity.setHasArack(0);
                System.out.println("===========inside if After Setting====" + mmsShelfInfoEntity.getHasArack());
            } else {
                //option 1 is for No(NO RACK)
                setRenderRackCode(false);
                setRenderOutdoorInfo(true);
                System.out.println("===========inside else====" + mmsShelfInfoEntity.getHasArack());
                setOutdoorNameBundle("Code:");
                mmsShelfInfoEntity.setHasArack(1);
                System.out.println("===========inside else After Setting====" + mmsShelfInfoEntity.getHasArack());
            }
        }
    }

    public void handleSelectOutdoorType(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            //setDisableRackCode(true);
            System.out.println("=============event======" + event.getNewValue().toString());
            if (event.getNewValue().toString().equals("Field")) {
                setOutdoorNameBundle("Field Code:");
            } else if (event.getNewValue().toString().equals("Closed Shade")) {
                setRenderForClosedShade(true);
            } else if (event.getNewValue().toString().equals("Open Shade")) {
                setOutdoorNameBundle("Open Shade Code:");
            }

        }
    }

    /**
     *
     * @param event
     */
    public void handleSelectShelfCode(ValueChangeEvent event) {
        if (null != event.getNewValue()) {
//           
//                 mmsCellInfoEntity.setStoreId(storeInfoEntity);
            mmsCellInfoEntity.setShelfId(mmsShelfInfoEntity);
            mmsCellInfoEntity = mmsCellInfoInterface.getMmsCellInformation(mmsCellInfoEntity);
//              
        }

//          
//            
    }

    /**
     *
     * @param e
     */
    public void changeCkeckPayrollType(ValueChangeEvent e) {
        clearPage();
        if (null != e.getNewValue()) {
            if (e.getNewValue().toString().equalsIgnoreCase("1")) {

                setRendersearchByempid4reportid(true);
                setDisbsearchByempid4reportid(false);
                setCkeckPayrollType("1");

            } else if (e.getNewValue().toString().equalsIgnoreCase("2")) {

                setRendersearchByempid4reportid(false);
                setDisbsearchByempid4reportid(true);
                setCkeckPayrollType("2");
                mmsShelfInfoEntity.setShadeName("not a Shade");
            }

        }
    }

    /**
     *
     * @param ev
     */
    public void enabledisableWarehouse(ValueChangeEvent ev) {
        if (null != ev.getNewValue()) {

            if (ev.getNewValue().toString().equalsIgnoreCase("0")) {

                setDisbleWareHouseOneMenu(true);
                setRenderWareHouseOneMenu(false);
                setRenderstoreShelfOnemenu(true);
                setDisablestoreShelfOnemenu(false);

            } else if (ev.getNewValue().toString().equalsIgnoreCase("1")) {
                setRenderWareHouseOneMenu(true);
                setDisbleWareHouseOneMenu(false);
                setRenderstoreShelfOnemenu(false);
                setDisablestoreShelfOnemenu(true);
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
    /**
     *
     * @return
     */
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Save Methods and Search">
     /*This method is used to column Name Change Event
     */
    public void columnNameChangeEvent(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            System.out.println("event.getNewValue().toString()==" + event.getNewValue().toString());
            columnNameResolver.setCol_Name_FromTable(event.getNewValue().toString());
            columnNameResolver.setParsed_Col_Name(ColumnParser(columnNameResolver.getCol_Name_FromTable()));
            mmsShelfInfoEntity.setColumnName(columnNameResolver.getCol_Name_FromTable());
            mmsShelfInfoEntity.setColumnValue(null);
        }
       
    }

    public String ColumnParser(String col_name) {
        String col = col_name.replace("_", " ");
        System.out.println("col==" + col);
        return col;
    }

    public List<String> getMmsStoreColumnNameList() {
        mmsStoreColumnNameList = mmsShelfInfoInterface.getMmsShelfInfoColumnNameList();
        if (mmsStoreColumnNameList == null) {
            mmsStoreColumnNameList = new ArrayList<>();
        }
        System.out.println("=======mmsStoreColumnNameList==" + mmsStoreColumnNameList);
        if (mmsStoreColumnNameList.size() > 0) {
            ColumnNamesList = new ArrayList<>();
            for (int i = 0; i < mmsStoreColumnNameList.size(); i++) {
                ColumnNameResolver obj = new ColumnNameResolver();
                obj.setCol_Name_FromTable((mmsStoreColumnNameList.get(i).toLowerCase()));
                obj.setParsed_Col_Name(ColumnParser((mmsStoreColumnNameList.get(i).toLowerCase())));
                ColumnNamesList.add(obj);
            }

        }
        return mmsStoreColumnNameList;
    }

    public void setMmsStoreColumnNameList(List<String> mmsStoreColumnNameList) {
        this.mmsStoreColumnNameList = mmsStoreColumnNameList;
    }

    /*This method is used to  Work flow save
     */
    public void wfSave() {
        System.out.println("inside wf save");
        // wfMmsProcessed.setShelfId(selectedItem);
        wfMmsProcessedBeanLocal.create(wfMmsProcessed);
    }
    /*This method is used to Save Shelf Informations
     */

    public void saveShelfInformation() {
        try {
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(SessionBean.getUserName(), "saveShelfInformation", dataset)) {
                if (mmsShelfDataModel.getRowCount() <= 0) {
                    JsfUtil.addFatalMessage("No record added to the Table!");
                } else {
                    if (updateStatus == 0) { //&& result==false) {

                        for (int i = 0; i < addShelfsList.size(); i++) {
                            MmsShelfInfo savingObj = new MmsShelfInfo();
                            savingObj.setStoreId(addShelfsList.get(i).getStoreId());
                            savingObj.setRackCode(addShelfsList.get(i).getRackCode());
                            savingObj.setSelectOption1(addShelfsList.get(i).getSelectOption1());
                            savingObj.setRackOrOutdoor(addShelfsList.get(i).getRackOrOutdoor());
                            savingObj.setHasArack(addShelfsList.get(i).getHasArack());

                            if (addShelfsList.get(i).getOutdoorType() != null) {
                                savingObj.setOutdoorType(addShelfsList.get(i).getOutdoorType());
                            }
                            wfMmsProcessed.setProcessedBy(SessionBean.getUserId());
                            savingObj.setStatus(Constants.PREPARE_VALUE);
                            savingObj.setProcessedOn(wfMmsProcessed.getProcessedOn());
                            savingObj.setProcessedBy(wfMmsProcessed.getProcessedBy());
                            wfMmsProcessed.setDecision(savingObj.getStatus());
//                            wfSave();
                            savingObj.addShelfIdToWorkflow(wfMmsProcessed);
                            mmsShelfInfoInterface.create(savingObj);

                        }

                        JsfUtil.addSuccessMessage("Shelf Information is Saved");
                    } else if (updateStatus == 2) {//&& result == true){
                        JsfUtil.addFatalMessage("Duplicate Information is not allowed");
                    } else {
                        mmsShelfInfoEntity = addShelfsList.get(0);
                        setWorkFlowValues();
                        mmsShelfInfoInterface.edit(mmsShelfInfoEntity);
                        JsfUtil.addSuccessMessage("Shelf Information is updated");
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
    /*This method is used to  set work flow value
     */

    public void setWorkFlowValues() {
        mmsShelfInfoEntity.setProcessedOn(wfMmsProcessed.getProcessedOn());
        mmsShelfInfoEntity.setProcessedBy(SessionBean.getUserId());
    }

    /*This method is used to  Save cell information
     */
    public String save_Cell_Information() {

        if (updateStatus == 0) { //&& result==false) {

            mmsCellInfoEntity.setClosedShadeName(mmsShelfInfoEntity.getShadeName());
            mmsCellInfoEntity.setShelfId(mmsShelfInfoEntity);

            mmsCellInfoInterface.create(mmsCellInfoEntity);

            JsfUtil.addSuccessMessage("Cell information is Saved");
        } else if (updateStatus == 2) {//&& result == true){
            JsfUtil.addSuccessMessage("Duplicate Information is not allowed");
        } else {
            mmsCellInfoInterface.edit(mmsCellInfoEntity);
            JsfUtil.addSuccessMessage("Cell information is upadted");
        }
        clearPage();
        return null;
    }

    /**
     *
     * @return
     */
    public void goBackSearchButtonAction() {
        renderpnlToSearchPage = false;
        renderPnlCreateLocationInfo = false;
        renderPnlManPage = true;
    }
    /*This method is used to  Creat New Locaton Information
     */

    public void createNewLocationInfo() {
        //
        clearPage();
        saveorUpdateBundle = "Save";
        disableBtnCreate = false;
        if (createOrSearchBundle.equals("New")) {
            renderPnlCreateLocationInfo = true;
            renderPnlManPage = false;
            createOrSearchBundle = "Search";
            setIcone("ui-icon-search");
            //generateGatePassNo();
        } else if (createOrSearchBundle.equals("Search")) {
            renderPnlCreateLocationInfo = false;
            renderPnlManPage = true;
            createOrSearchBundle = "New";
            setIcone("ui-icon-plus");
        }
    }
    /*This method is used to  Search
     */

    public void searchByParameter() {
        System.out.println("in search");
        mmsShelfInfoEntity.setProcessedBy(SessionBean.getUserId());
        System.out.println("processor " + mmsShelfInfoEntity.getProcessedBy());
        allShelfinfoList = mmsShelfInfoInterface.getShelfListsByParameter(columnNameResolver, mmsShelfInfoEntity, mmsShelfInfoEntity.getColumnValue());
        recerateSearchModel();
//        if ((storeInfoEntitytemp.getStoreId() != null) && (mmsShelfInfoEntity.getRackCode().isEmpty())) {
//
//            allShelfinfoList = mmsShelfInfoInterface.searchByParameterStore(mmsShelfInfoEntity);
//            System.out.println("===========inside find by store Shelf List==========" + allShelfinfoList.size());
//            recerateSearchModel();
//        } else if (storeInfoEntitytemp.getStoreId() != null && !mmsShelfInfoEntity.getRackCode().isEmpty()) {
//            System.out.println("===========inside find by both==========");
//            System.out.println("=========store id========" + storeInfoEntitytemp.getStoreId());
//            allShelfinfoList = mmsShelfInfoInterface.searchByParameterShelfAndStore(mmsShelfInfoEntity, storeInfoEntitytemp);
//            recerateSearchModel();
//        }
    }

    private void recerateSearchModel() {
        mmsShelfSearchInfoDataModel = null;
        mmsShelfSearchInfoDataModel = new ListDataModel(new ArrayList<>(allShelfinfoList));
    }
    /*This method is used to  On Row Editing
     */

    public void onRowEdit(SelectEvent event) {
        mmsShelfInfoEntitytemp = (MmsShelfInfo) event.getObject();
        wfMmsProcessed.setProcessedOn(mmsShelfInfoEntitytemp.getProcessedOn());
        //mmsShelfInfoEntity = 
        addShelfsList.add(mmsShelfInfoEntitytemp);

        renderPnlCreateLocationInfo = true;

        renderPnlManPage = false;
        renderpnlToSearchPage = true;

        activeIndex = "0";
        saveorUpdateBundle = "Update";
        createOrSearchBundle = "New";
        setIcone("ui-icon-plus");
        disableBtnCreate = true;
        updateStatus = 1;

        recreateShelfModel();

    }
//</editor-fold>
}
