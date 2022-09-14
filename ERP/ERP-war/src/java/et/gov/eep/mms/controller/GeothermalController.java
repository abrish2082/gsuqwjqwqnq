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
import et.gov.eep.fcms.entity.fixedasset.FmsDprGeothermal;
import et.gov.eep.mms.businessLogic.MmsFaGeothermalBeanLocal;
import et.gov.eep.mms.businessLogic.MmsFixedAssetTypeBeanLocal;
import et.gov.eep.mms.entity.MmsFaAssetType;
import et.gov.eep.mms.entity.MmsFaGeothermal;
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
 * @Nebiyou Samuel
 */
@Named(value = "geothermalController")
@ViewScoped
public class GeothermalController implements Serializable {

    @EJB
    private MmsFaGeothermalBeanLocal geothermalInterface;
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
    private FmsLuSystem luSystemEntity;
    @Inject
    private FmsCostCenter costCenterEntity;
    @Inject
    private MmsFaAssetType assetTypeEntity;
    @Inject
    private MmsFaGeothermal geothermalEntity;
    @Inject
    private FmsDprGeothermal GeoDprEntity;
    @Inject
    WfMmsProcessed workFlow;
    @Inject
    SessionBean SessionBean;
    @Inject
    WorkFlow workflow;
    private String saveorUpdateBundle = "Save";
    int updateStatus = 0;
    private DataModel<MmsFaGeothermal> mmsGeothermalSearchInfoDataModel;
    private boolean renderPnlCreateGeo = false;
    private boolean renderPnlManPage = true;
    private String icone = "ui-icon-plus";
    private String createOrSearchBundle = "New";
    private MmsFaGeothermal hpSelect;
    private boolean disablereevaluation = true;
    private boolean renderDecision = false;
    private String UserName;

    public GeothermalController() {
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

    public MmsFaGeothermal getHpSelect() {
        return hpSelect;
    }

    public void setHpSelect(MmsFaGeothermal hpSelect) {
        this.hpSelect = hpSelect;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String UserName) {
        this.UserName = UserName;
    }

    public FmsDprGeothermal getGeoDprEntity() {
        if (GeoDprEntity == null) {
            GeoDprEntity = new FmsDprGeothermal();
        }
        return GeoDprEntity;
    }

    public void setGeoDprEntity(FmsDprGeothermal GeoDprEntity) {
        this.GeoDprEntity = GeoDprEntity;
    }

    public boolean isRenderDecision() {
        return renderDecision;
    }

    public void setRenderDecision(boolean renderDecision) {
        this.renderDecision = renderDecision;
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

    public WfMmsProcessed getWorkFlow() {
        if (workFlow == null) {
            workFlow = new WfMmsProcessed();
        }
        return workFlow;
    }

    public void setWorkFlow(WfMmsProcessed workFlow) {
        this.workFlow = workFlow;
    }

    public MmsFaGeothermal getGeothermalEntity() {
        if (geothermalEntity == null) {
            geothermalEntity = new MmsFaGeothermal();
        }
        return geothermalEntity;
    }

    public void setGeothermalEntity(MmsFaGeothermal geothermalEntity) {
        this.geothermalEntity = geothermalEntity;
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

    public DataModel<MmsFaGeothermal> getMmsGeothermalSearchInfoDataModel() {
        if (mmsGeothermalSearchInfoDataModel == null) {
            mmsGeothermalSearchInfoDataModel = new ListDataModel<>();
        }
        return mmsGeothermalSearchInfoDataModel;
    }

    public void setMmsGeothermalSearchInfoDataModel(DataModel<MmsFaGeothermal> mmsGeothermalSearchInfoDataModel) {
        this.mmsGeothermalSearchInfoDataModel = mmsGeothermalSearchInfoDataModel;
    }

    public boolean isRenderPnlCreateGeo() {
        return renderPnlCreateGeo;
    }

    public void setRenderPnlCreateGeo(boolean renderPnlCreateGeo) {
        this.renderPnlCreateGeo = renderPnlCreateGeo;
    }

    public boolean isDisablereevaluation() {
        return disablereevaluation;
    }

    public void setDisablereevaluation(boolean disablereevaluation) {
        this.disablereevaluation = disablereevaluation;
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

    public String getCreateOrSearchBundle() {
        return createOrSearchBundle;
    }

    public void setCreateOrSearchBundle(String createOrSearchBundle) {
        this.createOrSearchBundle = createOrSearchBundle;
    }

    List<MmsFaGeothermal> allGeoInfoList;

    private void recerateGeoSerachModel() {
        mmsGeothermalSearchInfoDataModel = null;
        mmsGeothermalSearchInfoDataModel = new ListDataModel(new ArrayList<>(allGeoInfoList));
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
            Integer GlId = Integer.valueOf(evValue);
            generalLedgerEntity = new FmsGeneralLedger();
            generalLedgerEntity.setGeneralLedgerId(GlId);
            geothermalEntity.setGtAccountNo(generalLedgerEntity);

        }
    }

    public void SystemChange(ValueChangeEvent event) {

        if (!event.getNewValue().toString().isEmpty()) {
            luSystemEntity.setSystemCode(event.getNewValue().toString());
            luSystemEntity = luSystemInterface.getSysDetail(luSystemEntity);
            costCenterEntity.setSystemId(luSystemEntity);
            geothermalEntity.setGtSysNo(luSystemEntity);
        }
    }

    //System No value change listner
    public void getCostCenterChange(ValueChangeEvent event) {

        if (!event.getNewValue().toString().isEmpty()) {

            String sysno = event.getNewValue().toString();
            Integer sysid = Integer.valueOf(sysno);
            costCenterEntity.setCostCenterId(sysid);
            geothermalEntity.setGtCostCenter(costCenterEntity);

            // geothermalEntity.setSystemNo(luSystemEntity);
        }
    }

    //Asset Type value change listner
    public void AssetTypeChange(ValueChangeEvent event) {

        if (!event.getNewValue().toString().isEmpty()) {

            String sysno = event.getNewValue().toString();
            Integer assetId = Integer.valueOf(sysno);

            assetTypeEntity.setAssetId(assetId);
            geothermalEntity.setGtAssetType(assetTypeEntity);

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

    public void Wfsave() {
        workFlow.setGeothermalId(geothermalEntity);
        workFlowInterface.create(workFlow);
    }

    public void saveGeothermal() {

        try {
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(SessionBean.getUserName(), "saveGeothermal", dataset)) {
                // put ur code here...!
                if (updateStatus == 0) {
                    try {
                        Integer serviceLife = geothermalEntity.getGtServiceLife();
                        if (serviceLife > 100) {
                            JsfUtil.addFatalMessage(" Service Life Should be Less Than 100 ");

                        }
                        geothermalEntity.setGtNo(newGeoId);
                        geothermalEntity.setGtPrepared(SessionBean.getUserId());
                        geothermalEntity.setGtStatus(Constants.PREPARE_VALUE);
                        workFlow.setDecision(Constants.PREPARE_VALUE);
                        workFlow.setProcessedBy(SessionBean.getUserId());
                        geothermalEntity.getGeothermalList().add(workFlow);
                        geothermalInterface.create(geothermalEntity);
                        // Wfsave();
                        JsfUtil.addSuccessMessage("Geothermal information is Saved");
                    } catch (Exception ex) {
                        JsfUtil.addFatalMessage(" Something Error Occured on Creating the Data ");

                    }

                } else {

                    try {
                        geothermalEntity.setGtStatus(Constants.PREPARE_VALUE);
                        workFlow.setDecision(Constants.PREPARE_VALUE);
                        workFlow.setProcessedBy(SessionBean.getUserId());
                        geothermalInterface.edit(geothermalEntity);
                        Wfsave();
                        JsfUtil.addSuccessMessage("Geothermal information is Updated");
                    } catch (Exception ex) {
                        JsfUtil.addFatalMessage(" Something Error Occured on Updating the Data ");

                    }

                }

                clearPage();

            } else {
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
        geothermalEntity = null;
        luSystemEntity = null;
        generalLedgerEntity = null;
        assetTypeEntity = null;
        // costCenterEntity=null;
        costCenterEntity.setCostCenterId(null);

        updateStatus = 0;
        saveorUpdateBundle = "Save";
        mmsGeothermalSearchInfoDataModel = null;
    }

    List<MmsFaGeothermal> checkerList = new ArrayList<>();

    public void searchGeoInformation1() {

        String str = geothermalEntity.getStationName();

        //String str1 = event.getObject().toString();
        //("======================Str1==========" + str1);
        geothermalEntity.setStationName(str);
        if (geothermalEntity.getStationName() != null) {

            allGeoInfoList = geothermalInterface.searchGeoByParameterPrefix(geothermalEntity);
            checkerList.clear();
            checkerList = allGeoInfoList;

            recerateGeoSerachModel();

        } else {
        }
    }

    public void onRowEdit(RowEditEvent event) {

        renderPnlCreateGeo = true;
        renderPnlManPage = false;
        //activeIndex = "0";
        saveorUpdateBundle = "Update";
        // saveorUpdateBundle = "Search";
        setIcone("ui-icon-search");
        //disableBtnCreate = true;
        updateStatus = 1;

        int rowIndex = mmsGeothermalSearchInfoDataModel.getRowIndex();

        geothermalEntity = checkerList.get(rowIndex);

    }

    public void btn_Search_Action() {
        clearPage();
        renderPnlCreateGeo = false;
        renderPnlManPage = true;
    }

    public void createNewGeoInfo() {
        clearPage();
        saveorUpdateBundle = "Save";
        // disableBtnCreate = false;

        if (createOrSearchBundle.equals("New")) {
            renderPnlCreateGeo = true;
            renderPnlManPage = false;
            createOrSearchBundle = "Search";
            setCreateOrSearchBundle(createOrSearchBundle);
            setIcone("ui-icon-search");

        } else if (createOrSearchBundle.equals("Search")) {
            renderPnlCreateGeo = false;
            renderPnlManPage = true;
            createOrSearchBundle = "New";
            setCreateOrSearchBundle(createOrSearchBundle);
            setIcone("ui-icon-plus");
        }

    }

    String newGeoId;
    String lastGeoId = "0";

    public String generateGeoNo() {

        MmsFaGeothermal lastInsuranceID = geothermalInterface.getLastGeoId();
        if (lastInsuranceID != null) {
            lastGeoId = lastInsuranceID.getGeothermalId().toString();
        }

        DateFormat f = new SimpleDateFormat("yyyy");
        Date now = new Date();

        int id = 0;
        if (lastGeoId.equals("0")) {
            id = 1;
            newGeoId = "GeothermalNo-" + id + "/" + f.format(now);
        } else {

            String[] lastInspNos = lastGeoId.split("-");
            String lastDatesPatern = lastInspNos[0];

            String[] lastDatesPaterns = lastDatesPatern.split("/");
            id = Integer.parseInt(lastDatesPaterns[0]);
            id = id + 1;
            newGeoId = "GeothermalNo-" + id + "/" + f.format(now);
        }

        return newGeoId;
    }

    //  List<MmsFaGeothermal> checkerList = new ArrayList<>();
    List<MmsFaGeothermal> costList = new ArrayList<>();
    BigDecimal depYr;
    BigDecimal BookV;
    BigDecimal AccDep;

    public void searchGtNo() {

        String str = geothermalEntity.getGtNo();

        geothermalEntity.setGtNo(str);
        List<MmsFaGeothermal> TransNo = null; //if (!geothermalEntity.getGtNo().equals(""))
        geothermalEntity.setGtPrepared(SessionBean.getUserId());
        if (geothermalEntity.getGtNo() != null) {

            geothermalEntity.setGtNo(str);

            TransNo = geothermalInterface.searchByGeoNoAndGtPrep(geothermalEntity);
            if (TransNo.isEmpty()) {
                JsfUtil.addFatalMessage("There is No Data ...");
            } else {
                costList.clear();
                costList = TransNo;
                int size = costList.size();
                MmsFaGeothermal fa1 = new MmsFaGeothermal();
                if (size != 0) {
                    fa1 = costList.get(0);
                }
                TransNo.clear();
                TransNo = geothermalInterface.searchByGeo(geothermalEntity);
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

                        MmsFaGeothermal fa = new MmsFaGeothermal();
                        fa = TransNo.get(i);
                        int sizeDepr = TransNo.get(i).getFmsDprGeothermalList().size();
                        for (int j = 0; j < sizeDepr; j++) {

                            int stat = TransNo.get(i).getFmsDprGeothermalList().get(j).getStatus();
                            if (stat == 1) {
                                depYr = TransNo.get(i).getFmsDprGeothermalList().get(j).getDprYear();
                                BookV = TransNo.get(i).getFmsDprGeothermalList().get(j).getNetBookValue();
                                AccDep = TransNo.get(i).getFmsDprGeothermalList().get(j).getAccumulatedDpr();

                                fa.setDprYear(depYr);
                                fa.setAccumulatedDpr(AccDep);
                                fa.setNetBookValue(BookV);

                                checkerList.add(fa);

                            }
                        }
                    }

                }

            }
        } else {
            TransNo = geothermalInterface.findAll1();
//            checkerList = geothermalInterface.findAll1();
            recerateTransSerachModel();
        }
    }

    private void recerateTransSerachModel() {

        mmsGeothermalSearchInfoDataModel = null;
        mmsGeothermalSearchInfoDataModel = new ListDataModel(new ArrayList<>(checkerList));
    }

    @PostConstruct
    public void renderWithPreselectedValue() {

        workFlow.setProcessedBy(SessionBean.getUserId());
        setUserName(SessionBean.getUserName());
        if (!(getFlash().get("geothermalInfo") == null)) {
            geothermalEntity = (MmsFaGeothermal) getFlash().get("geothermalInfo");
            assetTypeEntity = geothermalEntity.getGtAssetType();
            //Sys No
            luSystemEntity.setSystemCode(geothermalEntity.getGtSysNo().getSystemCode());

            luSystemEntity = geothermalEntity.getGtSysNo();

            // Cost Center
            CostCenterList = luSystemEntity.getFmsCostCenterList();
            costCenterEntity.setCostCenterId(geothermalEntity.getGtCostCenter().getCostCenterId());
            costCenterEntity = luSystemEntity.getFmsCostCenterList().get(0);
            renderPnlCreateGeo = true;
            renderPnlManPage = false;
            createOrSearchBundle = "Search";
            setCreateOrSearchBundle(createOrSearchBundle);
            setIcone("ui-icon-search");
        } else {
            renderPnlCreateGeo = false;
            renderPnlManPage = true;
            createOrSearchBundle = "New";
            setCreateOrSearchBundle(createOrSearchBundle);
            setIcone("ui-icon-plus");
        }

    }

    public void rowSelect(SelectEvent event) {
        geothermalEntity = (MmsFaGeothermal) event.getObject();
        geothermalEntity.setGeothermalId(geothermalEntity.getGeothermalId());
        geothermalEntity = geothermalInterface.getSelectedRequest(geothermalEntity.getGeothermalId());
        setDisablereevaluation(false);
        //Type Name 
        //assetTypeEntity.setAssetId(geothermalEntity.getGtAssetType().getAssetId());
        assetTypeEntity = geothermalEntity.getGtAssetType();
        //Sys No
//        luSystemEntity.setSystemCode(geothermalEntity.getGtSysNo().getSystemCode());

        luSystemEntity = geothermalEntity.getGtSysNo();

        // Cost Center
        CostCenterList = luSystemEntity.getFmsCostCenterList();
        costCenterEntity.setCostCenterId(geothermalEntity.getGtCostCenter().getCostCenterId());
        costCenterEntity = luSystemEntity.getFmsCostCenterList().get(0);

        geothermalEntity.setAccumulatedDpr(AccDep);
        geothermalEntity.setDprYear(depYr);
        geothermalEntity.setNetBookValue(BookV);

        renderPnlManPage = false;
        renderPnlCreateGeo = true;
        saveorUpdateBundle = "Update";
        setIcone("ui-icon-search");
        createOrSearchBundle = "Search";
        updateStatus = 1;

    }

}
