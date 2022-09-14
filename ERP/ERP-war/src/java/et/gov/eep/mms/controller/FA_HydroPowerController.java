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
import et.gov.eep.fcms.entity.fixedasset.FmsDprHydropower;
import et.gov.eep.mms.businessLogic.MmsFaHydroPowerBeanLocal;
import et.gov.eep.mms.businessLogic.MmsFixedAssetTypeBeanLocal;
import et.gov.eep.mms.entity.MmsFaAssetType;
import et.gov.eep.mms.entity.MmsFaHydropower;
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
 * @author Sadik
 */
@Named(value = "fA_HydroPowerController")
@ViewScoped
public class FA_HydroPowerController implements Serializable {

    @EJB
    WfMmsProcessedBeanLocal workFlowInterface;
    @EJB
    private MmsFaHydroPowerBeanLocal hydroPowerInterface;
    @EJB
    private FmsGeneralLedgerBeanLocal generalLedgerInterface;
    @EJB
    private FmsLuSystemBeanLocal luSystemInterface;
    @EJB
    private MmsFixedAssetTypeBeanLocal fixedAssetTypeInterface;
    @EJB
    private FmsCostCenterBeanLocal costCenterInterface;
    @Inject
    WorkFlow wf;
    @Inject
    WfMmsProcessed workFlow;
    @Inject
    SessionBean SessionBean;
    @Inject
    WorkFlow workflow;
    @Inject
    private MmsFaHydropower hydroPowerEntity;
    @Inject
    private FmsGeneralLedger generalLedgerEntity;
    @Inject
    private FmsLuSystem luSystemEntity;
    @Inject
    private FmsCostCenter costCenterEntity;
    @Inject
    private MmsFaAssetType assetTypeEntity;
    @Inject
    private FmsDprHydropower HydroDprEntity;

    private String saveorUpdateBundle = "Save";
    int updateStatus = 0;
    private DataModel<MmsFaHydropower> mmsHydroSearchInfoDataModel;
    private boolean renderPnlCreateHydro = false;
    private boolean renderPnlManPage = true;
    private String icone = "ui-icon-plus";
    private String createOrSearchBundle = "New";
    private boolean disablereevaluation = true;
    private boolean renderDecision = false;
    private String userName;

    private MmsFaHydropower hpSelect;

    public FA_HydroPowerController() {
    }

    public int getUpdateStatus() {
        return updateStatus;
    }

    public void setUpdateStatus(int updateStatus) {
        this.updateStatus = updateStatus;
    }

    public MmsFaHydropower getHpSelect() {
        return hpSelect;
    }

    public void setHpSelect(MmsFaHydropower hpSelect) {
        this.hpSelect = hpSelect;
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

    public WorkFlow getWf() {
        if (wf == null) {
            wf = new WorkFlow();
        }
        return wf;
    }

    public void setWf(WorkFlow wf) {
        this.wf = wf;
    }

    public boolean isRenderDecision() {
        return renderDecision;
    }

    public void setRenderDecision(boolean renderDecision) {
        this.renderDecision = renderDecision;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public boolean isDisablereevaluation() {
        return disablereevaluation;
    }

    public void setDisablereevaluation(boolean disablereevaluation) {
        this.disablereevaluation = disablereevaluation;
    }

    public DataModel<MmsFaHydropower> getMmsHydroSearchInfoDataModel() {
        if (mmsHydroSearchInfoDataModel == null) {
            mmsHydroSearchInfoDataModel = new ListDataModel<>();
        }
        return mmsHydroSearchInfoDataModel;
    }

    public void setMmsHydroSearchInfoDataModel(DataModel<MmsFaHydropower> mmsHydroSearchInfoDataModel) {
        this.mmsHydroSearchInfoDataModel = mmsHydroSearchInfoDataModel;
    }

    public FmsDprHydropower getHydroDprEntity() {
        if (HydroDprEntity == null) {
            HydroDprEntity = new FmsDprHydropower();
        }
        return HydroDprEntity;
    }

    public void setHydroDprEntity(FmsDprHydropower HydroDprEntity) {
        this.HydroDprEntity = HydroDprEntity;
    }

    public boolean isRenderPnlCreateHydro() {
        return renderPnlCreateHydro;
    }

    public void setRenderPnlCreateHydro(boolean renderPnlCreateHydro) {
        this.renderPnlCreateHydro = renderPnlCreateHydro;
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

    public MmsFaHydropower getHydroPowerEntity() {
        if (hydroPowerEntity == null) {
            hydroPowerEntity = new MmsFaHydropower();
        }
        return hydroPowerEntity;
    }

    public void setHydroPowerEntity(MmsFaHydropower hydroPowerEntity) {
        this.hydroPowerEntity = hydroPowerEntity;
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
            generalLedgerEntity.setGeneralLedgerId(GlId);
        }
    }

    public void SystemChange(ValueChangeEvent event) {

        if (!event.getNewValue().toString().isEmpty()) {
            luSystemEntity.setSystemCode(event.getNewValue().toString());
            luSystemEntity = luSystemInterface.getSysDetail(luSystemEntity);
            costCenterEntity.setSystemId(luSystemEntity);
            hydroPowerEntity.setHpSysNo(luSystemEntity);
        }
    }

    //System No value change listner
    public void getCostCenterChange(ValueChangeEvent event) {

        if (!event.getNewValue().toString().isEmpty()) {

            String sysno = event.getNewValue().toString();
            Integer sysid = Integer.valueOf(sysno);
            costCenterEntity.setCostCenterId(sysid);
            hydroPowerEntity.setHpCostCenter(costCenterEntity);

//          
        }
    }

    //Asset Type value change listner
    public void AssetTypeChange(ValueChangeEvent event) {

        if (!event.getNewValue().toString().isEmpty()) {

            String sysno = event.getNewValue().toString();
            Integer assetId = Integer.valueOf(sysno);

            assetTypeEntity.setAssetId(assetId);
            hydroPowerEntity.setHpAssetType(assetTypeEntity);

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
        workFlow.setHydroPowerId(hydroPowerEntity);
        workFlowInterface.create(workFlow);
    }

    public void saveHydroPower() {

        try {
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(SessionBean.getUserName(), "saveHydroPower", dataset)) {

                if (updateStatus == 0) {
                    try {

                        Integer serviceLife = hydroPowerEntity.getHpServiceLife();
                        if (serviceLife > 100) {
                            JsfUtil.addFatalMessage(" Service Life Should be Less Than 100 ");

                        }

                        hydroPowerEntity.setHpNo(newHpId);
                        hydroPowerEntity.setHpPrepared(workFlow.getProcessedBy());
                        hydroPowerEntity.setHpStatus(Constants.PREPARE_VALUE);
                        // workFlow.setDecision(Constants.PREPARE_VALUE);
                        workFlow.setProcessedBy(SessionBean.getUserId());
                        hydroPowerEntity.getHydroList().add(workFlow);
                        hydroPowerInterface.create(hydroPowerEntity);
                        //Wfsave();

                        JsfUtil.addSuccessMessage("HydroPower information is Saved");

                    } catch (Exception ex) {
                        JsfUtil.addFatalMessage(" Something Error Occured on Creating the Data ");

                    }

                } else {
                    try {

                        hydroPowerEntity.setHpStatus(Constants.PREPARE_VALUE);
                        workFlow.setDecision(hydroPowerEntity.getHpStatus());
                        workFlow.setProcessedBy(hydroPowerEntity.getHpPrepared());
                        hydroPowerInterface.edit(hydroPowerEntity);
                        Wfsave();
                        JsfUtil.addSuccessMessage("HydroPower information is Updated");
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
        // costCenterEntity=null;
        hydroPowerEntity = null;
        luSystemEntity = null;
        generalLedgerEntity = null;
        assetTypeEntity = null;

        updateStatus = 0;
        saveorUpdateBundle = "Save";
        costCenterEntity.setCostCenterId(null);
    }

    String newHpId;
    String lastHpId = "0";

    public String generateHpNo() {

        MmsFaHydropower lastInsuranceID = hydroPowerInterface.getLastHpId();
        if (lastInsuranceID != null) {
            lastHpId = lastInsuranceID.getHydroPowerId().toString();
        }

        DateFormat f = new SimpleDateFormat("yyyy");
        Date now = new Date();

        int id = 0;
        if (lastHpId.equals("0")) {
            id = 1;
            newHpId = "HydroPowerNo-" + id + "/" + f.format(now);
        } else {

            String[] lastInspNos = lastHpId.split("-");
            String lastDatesPatern = lastInspNos[0];

            String[] lastDatesPaterns = lastDatesPatern.split("/");
            id = Integer.parseInt(lastDatesPaterns[0]);
            id = id + 1;
            newHpId = "HydroPowerNo-" + id + "/" + f.format(now);
        }

        return newHpId;
    }

    public void btn_Search_Action() {
        clearPage();
        renderPnlCreateHydro = false;
        renderPnlManPage = true;
    }

    public void createNewHydroInfo() {
        clearPage();
        saveorUpdateBundle = "Save";
        // disableBtnCreate = false;

        if (createOrSearchBundle.equals("New")) {
            renderPnlCreateHydro = true;
            renderPnlManPage = false;
            createOrSearchBundle = "Search";
            setCreateOrSearchBundle(createOrSearchBundle);
            setIcone("ui-icon-search");

        } else if (createOrSearchBundle.equals("Search")) {
            renderPnlCreateHydro = false;
            renderPnlManPage = true;
            createOrSearchBundle = "New";
            setCreateOrSearchBundle(createOrSearchBundle);
            setIcone("ui-icon-plus");
        }

    }

    List<MmsFaHydropower> checkerList = new ArrayList<>();
    List<MmsFaHydropower> costList = new ArrayList<>();

    public void searchHydroNo() {

        String str = hydroPowerEntity.getHpNo();

        hydroPowerEntity.setHpNo(str);

        hydroPowerEntity.setHpPrepared(SessionBean.getUserId());
        List<MmsFaHydropower> TransNo = null;  //!hydroPowerEntity.getHpNo().equals("")

        if ((!hydroPowerEntity.getHpNo().isEmpty()) && (hydroPowerEntity.getHpLocation().isEmpty())) {

            hydroPowerEntity.setHpNo(str);

            TransNo = hydroPowerInterface.searchByHpNoANdProcessedby(hydroPowerEntity);
            if (TransNo.size() == 0) {
                JsfUtil.addFatalMessage("There is No Data ...");
            } else {

                costList.clear();
                costList = TransNo;
                int size = costList.size();
                MmsFaHydropower fa1 = new MmsFaHydropower();
                if (size != 0) {
                    fa1 = costList.get(0);
                }
                TransNo.clear();
                TransNo = hydroPowerInterface.searchByHp(hydroPowerEntity);
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
                    int id = TransNo.get(0).getHydroPowerId();
                    hydroPowerEntity.setHydroPowerId(id);
                    HydroDprEntity.setHpAssetId(hydroPowerEntity);
                    checkerList.clear();
                    for (int i = 0; i < TransNo.size(); i++) {
                        MmsFaHydropower fa = new MmsFaHydropower();
                        fa = TransNo.get(i);
                        int sizeDepr = TransNo.get(i).getFmsDprHydropowerList().size();
                        for (int j = 0; j < sizeDepr; j++) {
                            int stat = TransNo.get(i).getFmsDprHydropowerList().get(j).getStatus();
                            if (stat == 1) {
                                BigDecimal depYr1 = TransNo.get(i).getFmsDprHydropowerList().get(j).getDprYear();
                                BigDecimal BookV2 = TransNo.get(i).getFmsDprHydropowerList().get(j).getNetBookValue();
                                BigDecimal AccDep3 = TransNo.get(i).getFmsDprHydropowerList().get(j).getAccumulatedDpr();
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
        } else if ((hydroPowerEntity.getHpNo().isEmpty()) && (!hydroPowerEntity.getHpLocation().isEmpty())) {

            String str2 = hydroPowerEntity.getHpLocation();
            hydroPowerEntity.setHpLocation(str2);

            TransNo = hydroPowerInterface.searchByHpLocation(hydroPowerEntity);
            if (TransNo.size() == 0) {
                JsfUtil.addFatalMessage("There is No Data ...");
            } else {

                costList.clear();
                costList = TransNo;
                int size = costList.size();
                MmsFaHydropower fa1 = new MmsFaHydropower();
                if (size != 0) {
                    fa1 = costList.get(0);
                }
                TransNo.clear();
                TransNo = hydroPowerInterface.searchByHp2(hydroPowerEntity);
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
                    int id = TransNo.get(0).getHydroPowerId();
                    hydroPowerEntity.setHydroPowerId(id);
                    HydroDprEntity.setHpAssetId(hydroPowerEntity);
                    checkerList.clear();
                    for (int i = 0; i < TransNo.size(); i++) {
                        MmsFaHydropower fa = new MmsFaHydropower();
                        fa = TransNo.get(i);
                        int sizeDepr = TransNo.get(i).getFmsDprHydropowerList().size();
                        for (int j = 0; j < sizeDepr; j++) {
                            int stat = TransNo.get(i).getFmsDprHydropowerList().get(j).getStatus();
                            if (stat == 1) {
                                BigDecimal depYr1 = TransNo.get(i).getFmsDprHydropowerList().get(j).getDprYear();
                                BigDecimal BookV2 = TransNo.get(i).getFmsDprHydropowerList().get(j).getNetBookValue();
                                BigDecimal AccDep3 = TransNo.get(i).getFmsDprHydropowerList().get(j).getAccumulatedDpr();
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
            checkerList = hydroPowerInterface.findAll1();
//            checkerList = hydroPowerInterface.findAll1();
            checkerList = hydroPowerInterface.searchAllTransmissionsInfoByPreparerId(hydroPowerEntity);
            recerateTransSerachModel();
        }
    }

    private void recerateTransSerachModel() {

        mmsHydroSearchInfoDataModel = null;
        mmsHydroSearchInfoDataModel = new ListDataModel(new ArrayList<>(checkerList));
    }

    @PostConstruct
    public void renderWithPreselectedValue() {

        workFlow.setProcessedBy(SessionBean.getUserId());
        setUserName(SessionBean.getUserName());
        // System.out.println("is App=="+workFlow.+"is chech=="+workFlow.isCheckStatus()+"is prepa=="+workFlow.isPrepareStatus());
        if (!(getFlash().get("hydroInfo") == null)) {
            hydroPowerEntity = (MmsFaHydropower) getFlash().get("hydroInfo");
            //Type Name
            assetTypeEntity = hydroPowerEntity.getHpAssetType();
            setDisablereevaluation(false);
            //Sys No
            luSystemEntity.setSystemCode(hydroPowerEntity.getHpSysNo().getSystemCode());

            luSystemEntity = hydroPowerEntity.getHpSysNo();

            // Cost Center
            CostCenterList = luSystemEntity.getFmsCostCenterList();
            costCenterEntity.setCostCenterId(hydroPowerEntity.getHpCostCenter().getCostCenterId());
            costCenterEntity = luSystemEntity.getFmsCostCenterList().get(0);

            renderPnlCreateHydro = true;
            renderPnlManPage = false;
            createOrSearchBundle = "Search";
            setCreateOrSearchBundle(createOrSearchBundle);
            setIcone("ui-icon-search");
        } else {
            renderPnlCreateHydro = false;
            renderPnlManPage = true;
            createOrSearchBundle = "New";
            setCreateOrSearchBundle(createOrSearchBundle);
            setIcone("ui-icon-plus");
        }

    }

    public void onRowEdit(RowEditEvent event) {

        renderPnlCreateHydro = true;
        renderPnlManPage = false;
        //activeIndex = "0";
        saveorUpdateBundle = "Update";
        //saveorUpdateBundle = "Search";
        setIcone("ui-icon-search");
        //disableBtnCreate = true;
        updateStatus = 1;
        hydroPowerEntity.getHpRegion();

        int rowIndex = mmsHydroSearchInfoDataModel.getRowIndex();

        hydroPowerEntity = checkerList.get(rowIndex);

        //Type Name
        assetTypeEntity.setAssetType(hydroPowerEntity.getHpAssetType().getAssetType());
        //Sys No
        luSystemEntity.setSystemCode(hydroPowerEntity.getHpSysNo().getSystemCode());
        // Cost Center
        costCenterEntity.setCostCenterId(hydroPowerEntity.getHpCostCenter().getCostCenterId());

    }

    public void rowSelect(SelectEvent event) {
        hydroPowerEntity = (MmsFaHydropower) event.getObject();
        hydroPowerEntity.setHydroPowerId(hydroPowerEntity.getHydroPowerId());
        hydroPowerEntity = hydroPowerInterface.getSelectedRequest(hydroPowerEntity.getHydroPowerId());
        //Type Name
        assetTypeEntity = hydroPowerEntity.getHpAssetType();
        setDisablereevaluation(false);
        //Sys No
//        luSystemEntity.setSystemCode(hydroPowerEntity.getHpSysNo().getSystemCode());

        luSystemEntity = hydroPowerEntity.getHpSysNo();

        // Cost Center
        CostCenterList = luSystemEntity.getFmsCostCenterList();
        costCenterEntity.setCostCenterId(hydroPowerEntity.getHpCostCenter().getCostCenterId());
        costCenterEntity = luSystemEntity.getFmsCostCenterList().get(0);

        renderPnlManPage = false;
        renderPnlCreateHydro = true;
        saveorUpdateBundle = "Update";
        setIcone("ui-icon-search");
        createOrSearchBundle = "Search";
        updateStatus = 1;

    }

}
