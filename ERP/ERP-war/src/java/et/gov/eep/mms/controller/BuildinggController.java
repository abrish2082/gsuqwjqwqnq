/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.mms.controller;

import static com.sun.faces.context.flash.ELFlash.getFlash;
import et.gov.eep.commonApplications.businessLogic.WfMmsProcessedBeanLocal;
import et.gov.eep.commonApplications.entity.WfMmsProcessed;
import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.fcms.businessLogic.admin.FmsCostCenterBeanLocal;
import et.gov.eep.fcms.businessLogic.admin.FmsGeneralLedgerBeanLocal;
import et.gov.eep.fcms.businessLogic.admin.FmsLuSystemBeanLocal;
import et.gov.eep.fcms.entity.admin.FmsCostCenter;
import et.gov.eep.fcms.entity.admin.FmsGeneralLedger;
import et.gov.eep.fcms.entity.admin.FmsLuSystem;
import et.gov.eep.fcms.entity.fixedasset.FmsDprBuilding;
import et.gov.eep.mms.businessLogic.MmsFaBuildingBeanLocal;
import et.gov.eep.mms.businessLogic.MmsFixedAssetTypeBeanLocal;
import et.gov.eep.mms.entity.MmsFaAssetType;
import et.gov.eep.mms.entity.MmsFaBuilding;
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
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import org.primefaces.event.RowEditEvent;
import org.primefaces.event.SelectEvent;
import securityBean.Constants;
import securityBean.SessionBean;
import securityBean.WorkFlow;
import webService.AAA;
import webService.EventEntry;
import webService.IAdministration;

/**
 *
 * @author Nebiyou Samuel.
 */
@Named(value = "buildinggController")
@ViewScoped
public class BuildinggController implements Serializable {

    @EJB
    private MmsFaBuildingBeanLocal buildingInterface;
    @EJB
    WfMmsProcessedBeanLocal workFlowInterface;
    @EJB
    private FmsGeneralLedgerBeanLocal generalLedgerInterface;
    @EJB
    private FmsLuSystemBeanLocal luSystemInterface;
    @EJB
    private MmsFixedAssetTypeBeanLocal fixedAssetTypeInterface;
    @EJB
    private FmsCostCenterBeanLocal costCenterInterface;
    @Inject
    private FmsGeneralLedger generalLedgerEntity;
    @Inject
    private MmsFaBuilding buildingEntity;
    @Inject
    private FmsLuSystem luSystemEntity;
    @Inject
    private FmsCostCenter costCenterEntity;
    @Inject
    private MmsFaAssetType assetTypeEntity;
    @Inject
    private FmsDprBuilding BuDprEntity;
    @Inject
    WfMmsProcessed workFlow;
    @Inject
    SessionBean SessionBean;
    @Inject
    WorkFlow workflow;
    private String saveorUpdateBundle = "Save";
    int updateStatus = 0;
    private DataModel<MmsFaBuilding> mmsBuildingSearchInfoDataModel;
    private boolean renderPnlCreateBuilding = false;
    private boolean renderPnlManPage = true;
    private String icone = "ui-icon-plus";
    private String createOrSearchBundle = "New";
    private MmsFaBuilding hpSelect;
    private boolean disablereevaluation = true;
    private boolean renderDecision = false;
    private String UserName;

    public BuildinggController() {
    }

    public MmsFaBuilding getHpSelect() {
        return hpSelect;
    }

    public void setHpSelect(MmsFaBuilding hpSelect) {
        this.hpSelect = hpSelect;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String UserName) {
        this.UserName = UserName;
    }

    public FmsGeneralLedger getGeneralLedgerEntity() {
        if (generalLedgerEntity == null) {
            generalLedgerEntity = new FmsGeneralLedger();
        }
        return generalLedgerEntity;
    }

    public void setGeneralLedgerEntity(FmsGeneralLedger generalLedgerEntity) {
        this.generalLedgerEntity = generalLedgerEntity;
    }

    public boolean isRenderDecision() {
        return renderDecision;
    }

    public void setRenderDecision(boolean renderDecision) {
        this.renderDecision = renderDecision;
    }

    public FmsDprBuilding getBuDprEntity() {
        if (BuDprEntity == null) {
            BuDprEntity = new FmsDprBuilding();
        }
        return BuDprEntity;
    }

    public void setBuDprEntity(FmsDprBuilding BuDprEntity) {
        this.BuDprEntity = BuDprEntity;
    }

    public MmsFaBuilding getBuildingEntity() {
        if (buildingEntity == null) {
            buildingEntity = new MmsFaBuilding();
        }
        return buildingEntity;
    }

    public void setBuildingEntity(MmsFaBuilding buildingEntity) {
        this.buildingEntity = buildingEntity;
    }

    public WfMmsProcessed getWorkFlow() {
        if (workFlow == null) {
            workFlow = new WfMmsProcessed();
        }
        return workFlow;
    }

    public void setWorkFlow(WfMmsProcessed workFlow) {
        this.workFlow = workFlow;
    }

    public FmsLuSystem getLuSystemEntity() {
        if (luSystemEntity == null) {
            luSystemEntity = new FmsLuSystem();
        }
        return luSystemEntity;
    }

    public void setLuSystemEntity(FmsLuSystem luSystemEntity) {
        this.luSystemEntity = luSystemEntity;
    }

    public FmsCostCenter getCostCenterEntity() {
        if (costCenterEntity == null) {
            costCenterEntity = new FmsCostCenter();
        }
        return costCenterEntity;
    }

    public void setCostCenterEntity(FmsCostCenter costCenterEntity) {
        this.costCenterEntity = costCenterEntity;
    }

    public MmsFaAssetType getAssetTypeEntity() {
        if (assetTypeEntity == null) {
            assetTypeEntity = new MmsFaAssetType();
        }
        return assetTypeEntity;
    }

    public void setAssetTypeEntity(MmsFaAssetType assetTypeEntity) {
        this.assetTypeEntity = assetTypeEntity;
    }

    public String getSaveorUpdateBundle() {
        return saveorUpdateBundle;
    }

    public void setSaveorUpdateBundle(String saveorUpdateBundle) {
        this.saveorUpdateBundle = saveorUpdateBundle;
    }

    public int getUpdateStatus() {
        return updateStatus;
    }

    public void setUpdateStatus(int updateStatus) {
        this.updateStatus = updateStatus;
    }

    public DataModel<MmsFaBuilding> getMmsBuildingSearchInfoDataModel() {
        if (mmsBuildingSearchInfoDataModel == null) {
            mmsBuildingSearchInfoDataModel = new ListDataModel<>();
        }
        return mmsBuildingSearchInfoDataModel;
    }

    public void setMmsBuildingSearchInfoDataModel(DataModel<MmsFaBuilding> mmsBuildingSearchInfoDataModel) {
        this.mmsBuildingSearchInfoDataModel = mmsBuildingSearchInfoDataModel;
    }

    public boolean isRenderPnlCreateBuilding() {
        return renderPnlCreateBuilding;
    }

    public void setRenderPnlCreateBuilding(boolean renderPnlCreateBuilding) {
        this.renderPnlCreateBuilding = renderPnlCreateBuilding;
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

    public boolean isDisablereevaluation() {
        return disablereevaluation;
    }

    public void setDisablereevaluation(boolean disablereevaluation) {
        this.disablereevaluation = disablereevaluation;
    }

    public String getCreateOrSearchBundle() {
        return createOrSearchBundle;
    }

    public void setCreateOrSearchBundle(String createOrSearchBundle) {
        this.createOrSearchBundle = createOrSearchBundle;
    }

    List<MmsFaBuilding> allBuildingInfoList;

    private void recerateBuildingSerachModel() {
        mmsBuildingSearchInfoDataModel = null;
        mmsBuildingSearchInfoDataModel = new ListDataModel(new ArrayList<>(allBuildingInfoList));
    }

    //system code List
    List<FmsLuSystem> subSystemCodeList = new ArrayList<>();

    public List<FmsLuSystem> getSystemCodelist() {
        subSystemCodeList = luSystemInterface.findAll();
        return subSystemCodeList;
    }
    //Account Code List
    List<FmsGeneralLedger> AcountCodeList = new ArrayList<>();

    public List<FmsGeneralLedger> getAccountCodelist() {
        AcountCodeList = generalLedgerInterface.findAll();
        return AcountCodeList;
    }

    //Asset Type List
    List<MmsFaAssetType> AssetTypeList = new ArrayList<>();

    public List<MmsFaAssetType> getAssetsTypeist() {
        AssetTypeList = fixedAssetTypeInterface.findAll();
        return AssetTypeList;
    }
    //Cost Center List
    List<FmsCostCenter> CostCenterList = new ArrayList<>();

    public List<FmsCostCenter> getCostCenterList() {
        luSystemEntity = costCenterEntity.getSystemId();
        if (luSystemEntity != null) {
            CostCenterList = costCenterInterface.findCostCenter(luSystemEntity);
            return CostCenterList;
        } else {
            JsfUtil.addSuccessMessage("Select System number Frist");
            CostCenterList.clear();
        }

        return CostCenterList;

    }

    //Account code value change listner
    public void getGeneralLedgerChange(ValueChangeEvent event) {

        if (!event.getNewValue().toString().isEmpty()) {

            String evValue = event.getNewValue().toString();
            Integer GlId = Integer.parseInt(evValue);
            generalLedgerEntity = new FmsGeneralLedger();
            generalLedgerEntity.setGeneralLedgerId(GlId); // Error NullPointerException.
            // buildingEntity.setBuAccountNo(generalLedgerEntity);

        }
    }

    public void SystemChange(ValueChangeEvent event) {

        if (!event.getNewValue().toString().isEmpty()) {
            luSystemEntity.setSystemCode(event.getNewValue().toString());
            luSystemEntity = luSystemInterface.getSysDetail(luSystemEntity);
            costCenterEntity.setSystemId(luSystemEntity);
            buildingEntity.setBuSysNo(luSystemEntity);
        }
    }

    //System No value change listner
    public void getCostCenterChange(ValueChangeEvent event) {

        if (!event.getNewValue().toString().isEmpty()) {

            String sysno = event.getNewValue().toString();
            Integer sysid = Integer.valueOf(sysno);
            costCenterEntity.setCostCenterId(sysid);
            buildingEntity.setBuCostCenter(costCenterEntity);

            // buildingEntity.setSystemNo(luSystemEntity);
        }
    }

    //Asset Type value change listner
    public void AssetTypeChange(ValueChangeEvent event) {

        if (!event.getNewValue().toString().isEmpty()) {

            String sysno = event.getNewValue().toString();
            Integer assetId = Integer.valueOf(sysno);

            assetTypeEntity.setAssetId(assetId);
            buildingEntity.setBuAssetType(assetTypeEntity);

            for (MmsFaAssetType Assets : AssetTypeList) {
                //("========material code list==="+materialCodeList1.getMatName());

                boolean a = Assets.getAssetId().equals(Integer.valueOf(event.getNewValue().toString()));
                if (Assets.getAssetId().equals(Integer.valueOf(event.getNewValue().toString()))) {

                    assetTypeEntity.setAssetNo(Assets.getAssetNo());

                    break;
                }
            }

        }

    }

    @PostConstruct
    public void renderWithPreselectedValue() {

        workFlow.setProcessedBy(SessionBean.getUserId());
        setUserName(SessionBean.getUserName());
        if (!(getFlash().get("bldgInfo") == null)) {
            buildingEntity = (MmsFaBuilding) getFlash().get("bldgInfo");
            assetTypeEntity = buildingEntity.getBuAssetType();
            //Sys No
            luSystemEntity.setSystemCode(buildingEntity.getBuSysNo().getSystemCode());

            luSystemEntity = buildingEntity.getBuSysNo();

            // Cost Center
            CostCenterList = luSystemEntity.getFmsCostCenterList();
            costCenterEntity.setCostCenterId(buildingEntity.getBuCostCenter().getCostCenterId());
            costCenterEntity = luSystemEntity.getFmsCostCenterList().get(0);
            renderPnlCreateBuilding = true;
            renderPnlManPage = false;
            createOrSearchBundle = "Search";
            setCreateOrSearchBundle(createOrSearchBundle);
            setIcone("ui-icon-search");
        } else {
            renderPnlCreateBuilding = false;
            renderPnlManPage = true;
            createOrSearchBundle = "New";
            setCreateOrSearchBundle(createOrSearchBundle);
            setIcone("ui-icon-plus");
        }

    }

    public void Wfsave() {
        workFlow.setBuildingId(buildingEntity);
        workFlowInterface.create(workFlow);
    }

    public void saveBuilding() {

        try {
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(SessionBean.getUserName(), "saveBuilding", dataset)) {

                if (updateStatus == 0) {
                    try {

                        Integer serviceLife = buildingEntity.getBuServiceLife();
                        if (serviceLife > 100) {
                            JsfUtil.addFatalMessage(" Service Life Should be Less Than 100 ");

                        }
                        buildingEntity.setBuNo(newTrId);

                        buildingEntity.setBuStatus(Constants.PREPARE_VALUE);
                        workFlow.setDecision(Constants.PREPARE_VALUE);
                        workFlow.setProcessedBy(SessionBean.getUserId());
                        buildingEntity.setBuPrepared(SessionBean.getUserId());
                        buildingEntity.getBuildingList().add(workFlow);
                        buildingInterface.create(buildingEntity);
                        //Wfsave();
                        JsfUtil.addSuccessMessage("Building information is Saved");
                    } catch (Exception ex) {
                        JsfUtil.addFatalMessage(" Something Error Occured on Creating the Data ");

                    }

                } else {

                    try {
                        buildingEntity.setBuStatus(Constants.PREPARE_VALUE);
                        workFlow.setDecision(buildingEntity.getBuStatus());
                        workFlow.setProcessedBy(buildingEntity.getBuPrepared());
                        buildingInterface.edit(buildingEntity);
                        Wfsave();
                        JsfUtil.addSuccessMessage("Building information is Updated");
                    } catch (Exception ex) {
                        JsfUtil.addFatalMessage(" Something Error Occured on Updating the Data ");

                    }

                }

                clearPage();

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

    private void clearPage() {
        AcountCodeList.clear();
        subSystemCodeList.clear();
        AssetTypeList.clear();
        CostCenterList.clear();
        buildingEntity = null;
        luSystemEntity = null;
        generalLedgerEntity = null;
        assetTypeEntity = null;
        // costCenterEntity=null;
        costCenterEntity.setCostCenterId(null);

        updateStatus = 0;
        saveorUpdateBundle = "Save";
        mmsBuildingSearchInfoDataModel = null;
    }

    List<MmsFaBuilding> checkerList = new ArrayList<>();

    public void searchBuldingInformation1() {

        String str = buildingEntity.getBuildingName();

        //String str1 = event.getObject().toString();
        //("======================Str1==========" + str1);
        buildingEntity.setBuildingName(str);
        if (buildingEntity.getBuildingName() != null) {

            allBuildingInfoList = buildingInterface.searchBuildingByParameterPrefix(buildingEntity);
            checkerList.clear();
            checkerList = allBuildingInfoList;

            recerateBuildingSerachModel();

        } else {
        }
    }

    public void onRowEdit(RowEditEvent event) {

        renderPnlCreateBuilding = true;
        renderPnlManPage = false;
        //activeIndex = "0";
        saveorUpdateBundle = "Update";
        // saveorUpdateBundle = "Search";
        setIcone("ui-icon-search");
        //disableBtnCreate = true;
        updateStatus = 1;

        int rowIndex = mmsBuildingSearchInfoDataModel.getRowIndex();
        buildingEntity = checkerList.get(rowIndex);
//     
    }

    public void btn_Search_Action() {
        clearPage();
        renderPnlCreateBuilding = false;
        renderPnlManPage = true;
    }

    public void createNewBuildingInfo() {
        clearPage();
        saveorUpdateBundle = "Save";
        // disableBtnCreate = false;

        if (createOrSearchBundle.equals("New")) {
            renderPnlCreateBuilding = true;
            renderPnlManPage = false;
            createOrSearchBundle = "Search";
            setCreateOrSearchBundle(createOrSearchBundle);
            setIcone("ui-icon-search");

        } else if (createOrSearchBundle.equals("Search")) {
            renderPnlCreateBuilding = false;
            renderPnlManPage = true;
            createOrSearchBundle = "New";
            setCreateOrSearchBundle(createOrSearchBundle);
            setIcone("ui-icon-plus");
        }

    }
    String newTrId;
    String lastTrId = "0";

    public String generateBuNo() {
        MmsFaBuilding lastInsuranceID = buildingInterface.getLastBuId();
        if (lastInsuranceID != null) {
            lastTrId = lastInsuranceID.getBuildingId().toString();
        }

        DateFormat f = new SimpleDateFormat("yyyy");
        Date now = new Date();

        int id = 0;
        if (lastTrId.equals("0")) {
            id = 1;
            newTrId = "BuildingNo-" + id + "/" + f.format(now);
        } else {

            String[] lastInspNos = lastTrId.split("-");
            String lastDatesPatern = lastInspNos[0];

            String[] lastDatesPaterns = lastDatesPatern.split("/");
            id = Integer.parseInt(lastDatesPaterns[0]);
            id = id + 1;
            newTrId = "BuildingNo-" + id + "/" + f.format(now);
        }

        return newTrId;
    }

    // List<MmsFaBuilding> checkerList = new ArrayList<>();
    List<MmsFaBuilding> costList = new ArrayList<>();

    BigDecimal depYr1;
    BigDecimal BookV2;
    BigDecimal AccDep3;

    public void searchBldgNo() {

        List<MmsFaBuilding> TransNo = null;
        if ((!buildingEntity.getBuNo().isEmpty()) && (buildingEntity.getBuildingName().isEmpty())) {

            String str = buildingEntity.getBuNo();
            buildingEntity.setBuNo(str);
            buildingEntity.setBuPrepared(SessionBean.getUserId());
            TransNo = buildingInterface.searchByBuNoAndProcessedBy(buildingEntity);
            if (TransNo.size() == 0) {
                JsfUtil.addSuccessMessage("There is No Data ...");
            } else {
                costList.clear();
                costList = TransNo;
                int size = costList.size();
                MmsFaBuilding fa1 = new MmsFaBuilding();
                if (size != 0) {
                    fa1 = costList.get(0);
                }
                TransNo.clear();
                TransNo = buildingInterface.searchByBldg(buildingEntity);
                if (TransNo.size() == 0) {

                    BigDecimal depYr1 = new BigDecimal(0.0);
                    BigDecimal BookV2 = new BigDecimal(0.0);
                    BigDecimal AccDep3 = new BigDecimal(0.0);
                    fa1.setDprYear(depYr1);
                    fa1.setAccumulatedDpr(AccDep3);
                    fa1.setNetBookValue(BookV2);

                    checkerList.add(fa1);

                    recerateTransSerachModel();

                    JsfUtil.addFatalMessage("Depreciation is not yet Calculated");
                } else {

                    checkerList.clear();
                    for (int i = 0; i < TransNo.size(); i++) {
                        MmsFaBuilding fa = new MmsFaBuilding();
                        fa = TransNo.get(i);
                        int sizeDepr = TransNo.get(i).getFmsDprBuildingList().size();
                        for (int j = 0; j < sizeDepr; j++) {
                            int stat = TransNo.get(i).getFmsDprBuildingList().get(j).getStatus();
                            if (stat == 1) {

                                depYr1 = TransNo.get(i).getFmsDprBuildingList().get(j).getDprYear();
                                BookV2 = TransNo.get(i).getFmsDprBuildingList().get(j).getNetBookValue();
                                AccDep3 = TransNo.get(i).getFmsDprBuildingList().get(j).getAccumulatedDpr();
                                fa.setDprYear(depYr1);
                                fa.setAccumulatedDpr(AccDep3);
                                fa.setNetBookValue(BookV2);

                                checkerList.add(fa);

                            }
                        }
                    }
                    recerateTransSerachModel();

                }
            }
        } else if ((buildingEntity.getBuNo().isEmpty()) && (!buildingEntity.getBuildingName().isEmpty())) {

            String str2 = buildingEntity.getBuildingName();
            buildingEntity.setBuildingName(str2);

            TransNo = buildingInterface.searchByBuNameAndBuPrep(buildingEntity);
            if (TransNo.size() == 0) {
                JsfUtil.addFatalMessage("There is No Data ...");
            } else {
                costList.clear();
                costList = TransNo;
                int size = costList.size();
                MmsFaBuilding fa1 = new MmsFaBuilding();
                if (size != 0) {
                    fa1 = costList.get(0);
                }
                TransNo.clear();
                TransNo = buildingInterface.searchByBldg2AndBuPrep(buildingEntity);
                if (TransNo.size() == 0) {

                    BigDecimal depYr1 = new BigDecimal(0.0);
                    BigDecimal BookV2 = new BigDecimal(0.0);
                    BigDecimal AccDep3 = new BigDecimal(0.0);
                    fa1.setDprYear(depYr1);
                    fa1.setAccumulatedDpr(AccDep3);
                    fa1.setNetBookValue(BookV2);

                    checkerList.add(fa1);

                    recerateTransSerachModel();

                    JsfUtil.addFatalMessage("Depreciation is not yet Calculated");
                } else {

                    checkerList.clear();
                    for (int i = 0; i < TransNo.size(); i++) {
                        MmsFaBuilding fa = new MmsFaBuilding();
                        fa = TransNo.get(i);
                        int sizeDepr = TransNo.get(i).getFmsDprBuildingList().size();
                        for (int j = 0; j < sizeDepr; j++) {
                            int stat = TransNo.get(i).getFmsDprBuildingList().get(j).getStatus();
                            if (stat == 1) {

                                depYr1 = TransNo.get(i).getFmsDprBuildingList().get(j).getDprYear();
                                BookV2 = TransNo.get(i).getFmsDprBuildingList().get(j).getNetBookValue();
                                AccDep3 = TransNo.get(i).getFmsDprBuildingList().get(j).getAccumulatedDpr();
                                fa.setDprYear(depYr1);
                                fa.setAccumulatedDpr(AccDep3);
                                fa.setNetBookValue(BookV2);

                                checkerList.add(fa);

                            }
                        }
                    }
                    recerateTransSerachModel();

                }
            }

        } else {
            buildingEntity.setBuPrepared(SessionBean.getUserId());
            checkerList = buildingInterface.searchAllTransmissionsInfoByPreparerId(buildingEntity.getBuPrepared());
            System.out.println("===allfind=====" + checkerList);
//            checkerList = buildingInterface.findAll1();
            recerateTransSerachModel();
        }

    }

    private void recerateTransSerachModel() {

        mmsBuildingSearchInfoDataModel = null;
        mmsBuildingSearchInfoDataModel = new ListDataModel(new ArrayList<>(checkerList));
    }

    public void rowSelect(SelectEvent event) {
        buildingEntity = (MmsFaBuilding) event.getObject();
        buildingEntity.setBuildingId(buildingEntity.getBuildingId());
        buildingEntity = buildingInterface.getSelectedRequest(buildingEntity.getBuildingId());
        setDisablereevaluation(false);
        //Type Name

        assetTypeEntity.setAssetId(buildingEntity.getBuAssetType().getAssetId());
//        assetTypeEntity = buildingEntity.getBuAssetType();
        //Sys No
        luSystemEntity.setSystemCode(buildingEntity.getBuSysNo().getSystemCode());

        luSystemEntity = buildingEntity.getBuSysNo();

        // Cost Center
        CostCenterList = luSystemEntity.getFmsCostCenterList();
        costCenterEntity.setCostCenterId(buildingEntity.getBuCostCenter().getCostCenterId());
        costCenterEntity = luSystemEntity.getFmsCostCenterList().get(0);

        buildingEntity.setAccumulatedDpr(AccDep3);
        buildingEntity.setDprYear(depYr1);
        buildingEntity.setNetBookValue(BookV2);

        renderPnlManPage = false;
        renderPnlCreateBuilding = true;
        saveorUpdateBundle = "Update";
        setIcone("ui-icon-search");
        createOrSearchBundle = "Search";
        updateStatus = 1;

    }

}
