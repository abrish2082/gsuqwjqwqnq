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
import et.gov.eep.fcms.entity.fixedasset.FmsDprSubstation;
import et.gov.eep.mms.businessLogic.MmsFaSubstationBeanLocal;
import et.gov.eep.mms.businessLogic.MmsFixedAssetTypeBeanLocal;
import et.gov.eep.mms.entity.MmsFaAssetType;
import et.gov.eep.mms.entity.MmsFaSubstation;
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
@Named(value = "fA_SubstationController")
@ViewScoped
public class FA_SubstationController implements Serializable {

    @EJB
    private MmsFaSubstationBeanLocal substationInterface;
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
    private MmsFaSubstation subStationEntity;
    @Inject
    private FmsDprSubstation subDprEntity;

    @Inject
    private FmsGeneralLedger generalLedgerEntity;
    @Inject
    private FmsLuSystem luSystemEntity;
    @Inject
    private FmsCostCenter costCenterEntity;
    @Inject
    private MmsFaAssetType assetTypeEntity;
    @Inject
    WfMmsProcessed workFlow;
    @Inject
    SessionBean SessionBean;
    @Inject
    WorkFlow workflow;
    private String saveorUpdateBundle = "Save";
    private DataModel<MmsFaSubstation> mmsSubSearchInfoDataModel;
    private boolean renderPnlCreateSub = false;
    private boolean renderPnlManPage = true;
    private String icone = "ui-icon-plus";
    private String createOrSearchBundle = "New";
    private MmsFaSubstation hpSelect;
    int updateStatus = 0;
    private boolean disablereevaluation = true;
    private boolean renderDecision = false;
    private String UserName;

    public FA_SubstationController() {
    }

    public MmsFaSubstation getHpSelect() {
        return hpSelect;
    }

    public void setHpSelect(MmsFaSubstation hpSelect) {
        this.hpSelect = hpSelect;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String UserName) {
        this.UserName = UserName;
    }

    public DataModel<MmsFaSubstation> getMmsSubSearchInfoDataModel() {
        if (mmsSubSearchInfoDataModel == null) {
            mmsSubSearchInfoDataModel = new ListDataModel<>();
        }
        return mmsSubSearchInfoDataModel;
    }

    public void setMmsSubSearchInfoDataModel(DataModel<MmsFaSubstation> mmsSubSearchInfoDataModel) {
        this.mmsSubSearchInfoDataModel = mmsSubSearchInfoDataModel;
    }

    public FmsDprSubstation getSubDprEntity() {
        if (subDprEntity == null) {
            subDprEntity = new FmsDprSubstation();
        }
        return subDprEntity;
    }

    public void setSubDprEntity(FmsDprSubstation subDprEntity) {
        this.subDprEntity = subDprEntity;
    }

    public boolean isRenderDecision() {
        return renderDecision;
    }

    public void setRenderDecision(boolean renderDecision) {
        this.renderDecision = renderDecision;
    }

    public boolean isRenderPnlCreateSub() {
        return renderPnlCreateSub;
    }

    public void setRenderPnlCreateSub(boolean renderPnlCreateSub) {
        this.renderPnlCreateSub = renderPnlCreateSub;
    }

    public boolean isRenderPnlManPage() {
        return renderPnlManPage;
    }

    public void setRenderPnlManPage(boolean renderPnlManPage) {
        this.renderPnlManPage = renderPnlManPage;
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

    public boolean isDisablereevaluation() {
        return disablereevaluation;
    }

    public void setDisablereevaluation(boolean disablereevaluation) {
        this.disablereevaluation = disablereevaluation;
    }

    public MmsFaSubstation getSubStationEntity() {
        if (subStationEntity == null) {
            subStationEntity = new MmsFaSubstation();
        }
        return subStationEntity;
    }

    public void setSubStationEntity(MmsFaSubstation subStationEntity) {
        this.subStationEntity = subStationEntity;
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

//   public SelectItem[] getCostCenterBySystemLU() {
//
//        fmsLuSystem = fmsCostCenter.getSystemId();
//        if (fmsLuSystem != null && fmsSubsid1aryLedger1 != null) {
//            return JsfUtil.getSelectItems(fmsCostCenterBeanLocal.findCostCenter(fmsLuSystem), true);
//        } else {
//            SelectItem[] items = new SelectItem[1];
//            items[0] = new SelectItem("", "--Select Sytem Code--");
//            return items;
//        }
//    }
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
            generalLedgerEntity.setGeneralLedgerId(GlId);

        }
    }

    public void SystemChange(ValueChangeEvent event) {

        if (!event.getNewValue().toString().isEmpty()) {
            luSystemEntity.setSystemCode(event.getNewValue().toString());
            luSystemEntity = luSystemInterface.getSysDetail(luSystemEntity);
            costCenterEntity.setSystemId(luSystemEntity);
            subStationEntity.setSsSysNo(luSystemEntity);
        }
    }

    //System No value change listner
    public void getCostCenterChange(ValueChangeEvent event) {

        if (!event.getNewValue().toString().isEmpty()) {

            String sysno = event.getNewValue().toString();
            Integer sysid = Integer.valueOf(sysno);
            subStationEntity.setSsCostCenter(costCenterEntity);

        }
    }

    //Asset Type value change listner
    public void AssetTypeChange(ValueChangeEvent event) {

        if (!event.getNewValue().toString().isEmpty()) {

            String sysno = event.getNewValue().toString();
            Integer assetId = Integer.valueOf(sysno);

            assetTypeEntity.setAssetId(assetId);
            subStationEntity.setSsAssetType(assetTypeEntity);

            for (MmsFaAssetType Assets : AssetTypeList) {

                boolean a = Assets.getAssetId().equals(Integer.valueOf(event.getNewValue().toString()));
                if (Assets.getAssetId().equals(Integer.valueOf(event.getNewValue().toString()))) {

                    assetTypeEntity.setAssetNo(Assets.getAssetNo());

                    break;
                }
            }

        }
    }

    public void Wfsave() {
        workFlow.setSubstationId(subStationEntity);
        workFlowInterface.create(workFlow);
    }

    public void saveSubstation() {

        try {
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(SessionBean.getUserName(), "saveSubstation", dataset)) {
                // put ur code here...!
                if (updateStatus == 0) {
                    try {

                        Integer serviceLife = subStationEntity.getSsServiceLife();
                        if (serviceLife > 100) {
                            JsfUtil.addFatalMessage(" Service Life Should be Less Than 100 ");

                        }
                        subStationEntity.setSsNo(newSubId);
                        subStationEntity.setSsPrepared(SessionBean.getUserId());
                        subStationEntity.setSsStatus(Constants.PREPARE_VALUE);
                        workFlow.setDecision(Constants.PREPARE_VALUE);
                        workFlow.setProcessedBy(SessionBean.getUserId());
                        subStationEntity.getSubstationList().add(workFlow);
                        substationInterface.create(subStationEntity);
                        //Wfsave();

                        JsfUtil.addSuccessMessage("Substation information is Saved");

                    } catch (Exception ex) {
                        JsfUtil.addFatalMessage(" Something Error Occured on Creating the Data ");

                    }

                } else {

                    try {

                        subStationEntity.setSsStatus(Constants.PREPARE_VALUE);
                        workFlow.setDecision(Constants.PREPARE_VALUE);
                        workFlow.setProcessedBy(SessionBean.getUserId());
                        substationInterface.edit(subStationEntity);
                        Wfsave();
                        JsfUtil.addSuccessMessage("Substation information is Updated");

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
        subStationEntity = null;
        luSystemEntity = null;
        generalLedgerEntity = null;
        assetTypeEntity = null;
        //costCenterEntity=null;
        costCenterEntity.setCostCenterId(null);

        updateStatus = 0;
        saveorUpdateBundle = "Save";
    }

    String newSubId;
    String lastSubId = "0";

    public String generateSubNo() {

        MmsFaSubstation lastInsuranceID = substationInterface.getLastSubId();
        if (lastInsuranceID != null) {
            lastSubId = lastInsuranceID.getSubstationId().toString();
        }

        DateFormat f = new SimpleDateFormat("yyyy");
        Date now = new Date();

        int id = 0;
        if (lastSubId.equals("0")) {
            id = 1;
            newSubId = "SubstaionNo-" + id + "/" + f.format(now);
        } else {

            String[] lastInspNos = lastSubId.split("-");
            String lastDatesPatern = lastInspNos[0];

            String[] lastDatesPaterns = lastDatesPatern.split("/");
            id = Integer.parseInt(lastDatesPaterns[0]);
            id = id + 1;
            newSubId = "SubstaionNo-" + id + "/" + f.format(now);
        }

        return newSubId;
    }

    public void btn_Search_Action() {
        clearPage();
        renderPnlCreateSub = false;
        renderPnlManPage = true;
    }

    public void createNewSubInfo() {
        clearPage();
        saveorUpdateBundle = "Save";
        // disableBtnCreate = false;

        if (createOrSearchBundle.equals("New")) {
            renderPnlCreateSub = true;
            renderPnlManPage = false;
            createOrSearchBundle = "Search";
            setCreateOrSearchBundle(createOrSearchBundle);
            setIcone("ui-icon-search");

        } else if (createOrSearchBundle.equals("Search")) {
            renderPnlCreateSub = false;
            renderPnlManPage = true;
            createOrSearchBundle = "New";
            setCreateOrSearchBundle(createOrSearchBundle);
            setIcone("ui-icon-plus");
        }

    }

    List<MmsFaSubstation> checkerList = new ArrayList<>();
    List<MmsFaSubstation> costList = new ArrayList<>();

    BigDecimal depYr1;
    BigDecimal BookV2;
    BigDecimal AccDep3;

    public void searchSubstationNo() {

        List<MmsFaSubstation> SubsNo = null; //if (!subStationEntity.getSsNo().equals("")) 
        if ((!subStationEntity.getSsNo().isEmpty() && (subStationEntity.getSsLocation().isEmpty()))) {

            String str = subStationEntity.getSsNo();
            subStationEntity.setSsNo(str);
            subStationEntity.setSsPrepared(SessionBean.getUserId());

            SubsNo = substationInterface.searchBySubsNoAndSubPrep(subStationEntity);
            if (SubsNo.size() == 0) {
                JsfUtil.addFatalMessage("There is No Data ...");
            } else {

                costList.clear();
                costList = SubsNo;
                int size = costList.size();
                MmsFaSubstation fa1 = new MmsFaSubstation();
                if (size != 0) {
                    fa1 = costList.get(0);
                }
                SubsNo.clear();
                SubsNo = substationInterface.searchBySubAndSubPrep(subStationEntity);
                if (SubsNo.size() == 0) {

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
//              

                    checkerList.clear();

                    for (int i = 0; i < SubsNo.size(); i++) {
                        MmsFaSubstation fa = new MmsFaSubstation();
                        fa = SubsNo.get(i);
                        int sizeDepr = SubsNo.get(i).getFmsDprSubstationList().size();
                        for (int j = 0; j < sizeDepr; j++) {
                            int stat = SubsNo.get(i).getFmsDprSubstationList().get(j).getStatus();
                            if (stat == 1) {
                                depYr1 = SubsNo.get(i).getFmsDprSubstationList().get(j).getDprYear();
                                BookV2 = SubsNo.get(i).getFmsDprSubstationList().get(j).getNetBookValue();
                                AccDep3 = SubsNo.get(i).getFmsDprSubstationList().get(j).getAccumulatedDpr();
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
        } else if ((subStationEntity.getSsNo().isEmpty() && (!subStationEntity.getSsLocation().isEmpty()))) {

            String str2 = subStationEntity.getSsLocation();
            subStationEntity.setSsLocation(str2);

            SubsNo = substationInterface.searchBySubLocAndSubPrep(subStationEntity);
            if (SubsNo.size() == 0) {
                JsfUtil.addFatalMessage("There is No Data ...");
            } else {

                costList.clear();
                costList = SubsNo;
                int size = costList.size();
                MmsFaSubstation fa1 = new MmsFaSubstation();
                if (size != 0) {
                    fa1 = costList.get(0);
                }
                SubsNo.clear();
                SubsNo = substationInterface.searchBySub2(subStationEntity);
                if (SubsNo.size() == 0) {

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
//              

                    checkerList.clear();

                    for (int i = 0; i < SubsNo.size(); i++) {
                        MmsFaSubstation fa = new MmsFaSubstation();
                        fa = SubsNo.get(i);
                        int sizeDepr = SubsNo.get(i).getFmsDprSubstationList().size();
                        for (int j = 0; j < sizeDepr; j++) {
                            int stat = SubsNo.get(i).getFmsDprSubstationList().get(j).getStatus();
                            if (stat == 1) {
                                depYr1 = SubsNo.get(i).getFmsDprSubstationList().get(j).getDprYear();
                                BookV2 = SubsNo.get(i).getFmsDprSubstationList().get(j).getNetBookValue();
                                AccDep3 = SubsNo.get(i).getFmsDprSubstationList().get(j).getAccumulatedDpr();
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
//            SubsNo = substationInterface.findAll1();
            subStationEntity.setSsPrepared(SessionBean.getUserId());
            checkerList = substationInterface.searchAllSubInfoByPreparerId(subStationEntity.getSsPrepared());
            System.out.println("===allfind=====" + checkerList);
            recerateTransSerachModel();
        }
    }

    private void recerateTransSerachModel() {

        mmsSubSearchInfoDataModel = null;
        mmsSubSearchInfoDataModel = new ListDataModel(new ArrayList<>(checkerList));
    }

    public void onRowEdit(RowEditEvent event) {

        renderPnlCreateSub = true;
        renderPnlManPage = false;
        //activeIndex = "0";
        saveorUpdateBundle = "Update";
        //saveorUpdateBundle = "Search";
        setIcone("ui-icon-search");
        //disableBtnCreate = true;
        updateStatus = 1;
        subStationEntity.getSsRegion();
        //assetTypeEntity.ssubStationEntity.getSsAssetType();

        int rowIndex = mmsSubSearchInfoDataModel.getRowIndex();

        subStationEntity = checkerList.get(rowIndex);

    }

    @PostConstruct
    public void renderWithPreselectedValue() {

        workFlow.setProcessedBy(SessionBean.getUserId());
        setUserName(SessionBean.getUserName());
        if (!(getFlash().get("substaionInfo") == null)) {
            subStationEntity = (MmsFaSubstation) getFlash().get("substaionInfo");
            //Type Name
            assetTypeEntity = subStationEntity.getSsAssetType();
            //Sys No
            luSystemEntity.setSystemCode(subStationEntity.getSsSysNo().getSystemCode());

            luSystemEntity = subStationEntity.getSsSysNo();

            // Cost Center
            CostCenterList = luSystemEntity.getFmsCostCenterList();
            costCenterEntity.setCostCenterId(subStationEntity.getSsCostCenter().getCostCenterId());
            costCenterEntity = luSystemEntity.getFmsCostCenterList().get(0);
            renderPnlCreateSub = true;
            renderPnlManPage = false;
            createOrSearchBundle = "Search";
            setCreateOrSearchBundle(createOrSearchBundle);
            setIcone("ui-icon-search");
        } else {
            renderPnlCreateSub = false;
            renderPnlManPage = true;
            createOrSearchBundle = "New";
            setCreateOrSearchBundle(createOrSearchBundle);
            setIcone("ui-icon-plus");
        }

    }

    public void rowSelect(SelectEvent event) {
        subStationEntity = (MmsFaSubstation) event.getObject();
        subStationEntity.setSubstationId(subStationEntity.getSubstationId());
        subStationEntity = substationInterface.getSelectedRequest(subStationEntity.getSubstationId());
        setDisablereevaluation(false);
        //Type Name
        assetTypeEntity = subStationEntity.getSsAssetType();
        //Sys No
        luSystemEntity.setSystemCode(subStationEntity.getSsSysNo().getSystemCode());

        luSystemEntity = subStationEntity.getSsSysNo();

        // Cost Center
        CostCenterList = luSystemEntity.getFmsCostCenterList();
        costCenterEntity.setCostCenterId(subStationEntity.getSsCostCenter().getCostCenterId());
        costCenterEntity = luSystemEntity.getFmsCostCenterList().get(0);

        subStationEntity.setAccumulatedDpr(AccDep3);
        subStationEntity.setDprYear(depYr1);
        subStationEntity.setNetBookValue(BookV2);

        renderPnlManPage = false;
        renderPnlCreateSub = true;
        saveorUpdateBundle = "Update";
        setIcone("ui-icon-search");
        createOrSearchBundle = "Search";
        updateStatus = 1;

    }

}
