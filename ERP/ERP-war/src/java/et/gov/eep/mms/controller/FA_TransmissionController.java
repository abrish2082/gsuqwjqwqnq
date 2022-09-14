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
//import et.gov.eep.fcms.businessLogic.FaTransmissionDprViewBeanLocal;
import et.gov.eep.fcms.businessLogic.admin.FmsGeneralLedgerBeanLocal;
import et.gov.eep.fcms.businessLogic.admin.FmsLuSystemBeanLocal;
import et.gov.eep.fcms.businessLogic.fixedasset.FmsDprTransmissionBeanLocal;
import et.gov.eep.fcms.entity.admin.FmsCostCenter;
import et.gov.eep.fcms.entity.admin.FmsGeneralLedger;
import et.gov.eep.fcms.entity.admin.FmsLuSystem;
import et.gov.eep.fcms.entity.fixedasset.FmsDprTransmisson;
import et.gov.eep.mms.businessLogic.MmsFixedAssetTypeBeanLocal;
import et.gov.eep.mms.businessLogic.MmsFaTransmissionBeanLocal;
import et.gov.eep.mms.entity.MmsFaAssetType;
import et.gov.eep.mms.entity.MmsFaTransmission;
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
import javax.faces.model.SelectItem;
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
@Named(value = "fA_TransmissionController")
@ViewScoped
public class FA_TransmissionController implements Serializable {

    @EJB
    private MmsFaTransmissionBeanLocal FaTransmissionInterface;
    @EJB
    WfMmsProcessedBeanLocal workFlowInterface;
    @EJB
    private FmsGeneralLedgerBeanLocal generalLedgerInterface;
    @EJB
    private FmsLuSystemBeanLocal luSystemInterface;
    @EJB
    private MmsFixedAssetTypeBeanLocal fixedAssetTypeInterface;
    @EJB
    private FmsDprTransmissionBeanLocal transDprInterface;
    @Inject
    private MmsFaTransmission FaTransmissionEntity;
    @Inject
    private FmsGeneralLedger generalLedgerEntity;
    @Inject
    private FmsDprTransmisson transDprEntity;
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

    int updateStatus = 0;
    private DataModel<MmsFaTransmission> mmsTransSearchInfoDataModel;
    private DataModel<FmsDprTransmisson> mmsTransDprSearchInfoDataModel;
    private boolean renderPnlCreateTransmission = false;
    private boolean renderPnlManPage = true;
    private String icone = "ui-icon-plus";
    private String createOrSearchBundle = "New";
    private MmsFaTransmission hpSelect;
    private boolean disablereevaluation = true;
    private boolean renderDecision = false;
    private String UserName;

    /**
     *
     */
    public FA_TransmissionController() {
    }

    public int getUpdateStatus() {
        return updateStatus;
    }

    public void setUpdateStatus(int updateStatus) {
        this.updateStatus = updateStatus;
    }

    public MmsFaTransmission getHpSelect() {
        return hpSelect;
    }

    public void setHpSelect(MmsFaTransmission hpSelect) {
        this.hpSelect = hpSelect;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String UserName) {
        this.UserName = UserName;
    }

    public boolean isDisablereevaluation() {
        return disablereevaluation;
    }

    public void setDisablereevaluation(boolean disablereevaluation) {
        this.disablereevaluation = disablereevaluation;
    }

    public boolean isRenderDecision() {
        return renderDecision;
    }

    public void setRenderDecision(boolean renderDecision) {
        this.renderDecision = renderDecision;
    }

    public FmsDprTransmisson getTransDprEntity() {
        if (transDprEntity == null) {
            transDprEntity = new FmsDprTransmisson();
        }
        return transDprEntity;
    }

    public void setTransDprEntity(FmsDprTransmisson transDprEntity) {
        this.transDprEntity = transDprEntity;
    }

    public DataModel<MmsFaTransmission> getMmsTransSearchInfoDataModel() {
        if (mmsTransSearchInfoDataModel == null) {

            mmsTransSearchInfoDataModel = new ListDataModel<>();
        }
        return mmsTransSearchInfoDataModel;
    }

    public void setMmsTransSearchInfoDataModel(DataModel<MmsFaTransmission> mmsTransSearchInfoDataModel) {
        this.mmsTransSearchInfoDataModel = mmsTransSearchInfoDataModel;
    }

    public DataModel<FmsDprTransmisson> getMmsTransDprSearchInfoDataModel() {
        if (mmsTransDprSearchInfoDataModel == null) {
            mmsTransDprSearchInfoDataModel = new ListDataModel<>();
        }
        return mmsTransDprSearchInfoDataModel;
    }

    public void setMmsTransDprSearchInfoDataModel(DataModel<FmsDprTransmisson> mmsTransDprSearchInfoDataModel) {
        this.mmsTransDprSearchInfoDataModel = mmsTransDprSearchInfoDataModel;
    }

    public boolean isRenderPnlCreateTransmission() {
        return renderPnlCreateTransmission;
    }

    public void setRenderPnlCreateTransmission(boolean renderPnlCreateTransmission) {
        this.renderPnlCreateTransmission = renderPnlCreateTransmission;
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

    /**
     *
     * @return
     */
    public FmsGeneralLedger getGeneralLedgerEntity() {
        if (generalLedgerEntity == null) {
            generalLedgerEntity = new FmsGeneralLedger();
        }
        return generalLedgerEntity;
    }

    /**
     *
     * @param generalLedgerEntity
     */
    public void setGeneralLedgerEntity(FmsGeneralLedger generalLedgerEntity) {
        this.generalLedgerEntity = generalLedgerEntity;
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

    /**
     *
     * @return
     */
    public FmsLuSystem getLuSystemEntity() {
        if (luSystemEntity == null) {
            luSystemEntity = new FmsLuSystem();
        }
        return luSystemEntity;
    }

    /**
     *
     * @param luSystemEntity
     */
    public void setLuSystemEntity(FmsLuSystem luSystemEntity) {
        this.luSystemEntity = luSystemEntity;
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
    public MmsFaTransmission getFaTransmissionEntity() {
        if (FaTransmissionEntity == null) {
            FaTransmissionEntity = new MmsFaTransmission();
        }
        return FaTransmissionEntity;
    }

    /**
     *
     * @param FaTransmissionEntity
     */
    public void setFaTransmissionEntity(MmsFaTransmission FaTransmissionEntity) {
        this.FaTransmissionEntity = FaTransmissionEntity;
    }

    /**
     *
     * @return
     */
    public MmsFaAssetType getAssetTypeEntity() {
        if (assetTypeEntity == null) {
            assetTypeEntity = new MmsFaAssetType();
        }
        return assetTypeEntity;
    }

    /**
     *
     * @param assetTypeEntity
     */
    public void setAssetTypeEntity(MmsFaAssetType assetTypeEntity) {
        this.assetTypeEntity = assetTypeEntity;
    }

    //system code List
    List<FmsLuSystem> SystemCodeList = new ArrayList<>();

    /**
     *
     * @return
     */
    public List<FmsLuSystem> getSystemCodelist() {
        SystemCodeList = luSystemInterface.findAll();
        return SystemCodeList;
    }
    //Account Code List
    List<FmsGeneralLedger> AcountCodeList = new ArrayList<>();

    /**
     *
     * @return
     */
    public List<FmsGeneralLedger> getAccountCodelist() {
        AcountCodeList = generalLedgerInterface.findAll();
        return AcountCodeList;
    }

    //Asset Type List
    List<MmsFaAssetType> AssetTypeList = new ArrayList<>();

    /**
     *
     * @return
     */
    public List<MmsFaAssetType> getAssetsTypeist() {
        AssetTypeList = fixedAssetTypeInterface.findAll();
        return AssetTypeList;
    }
    //Account code value change listner

    /**
     *
     * @param event
     */
    @PostConstruct
    public void renderWithPreselectedValue() {

        workFlow.setProcessedBy(SessionBean.getUserId());
        setUserName(SessionBean.getUserName());
        if (!(getFlash().get("transmissionInfo") == null)) {
            FaTransmissionEntity = (MmsFaTransmission) getFlash().get("transmissionInfo");
            //Type Name
            assetTypeEntity.setAssetId(FaTransmissionEntity.getAssetTypeNo().getAssetId());
            //Sys No
            luSystemEntity.setSystemCode(FaTransmissionEntity.getSystemNo().getSystemCode());

            luSystemEntity = FaTransmissionEntity.getSystemNo();
            renderPnlCreateTransmission = true;
            renderPnlManPage = false;
            createOrSearchBundle = "Search";
            setCreateOrSearchBundle(createOrSearchBundle);
            setIcone("ui-icon-search");
        } else {
            renderPnlCreateTransmission = false;
            renderPnlManPage = true;
            createOrSearchBundle = "New";
            setCreateOrSearchBundle(createOrSearchBundle);
            setIcone("ui-icon-plus");
        }

    }

    public void getGeneralLedgerChange(ValueChangeEvent event) {

        if (!event.getNewValue().toString().isEmpty()) {

            String evValue = event.getNewValue().toString();
            Integer GlId = Integer.valueOf(evValue);
            generalLedgerEntity.setGeneralLedgerId(GlId);
            //FaTransmissionEntity.setAccountNo(generalLedgerEntity);

        }
    }
    //System No value change listner

    /**
     *
     * @param event
     */
    public void getSystemNoChange(ValueChangeEvent event) {

        if (!event.getNewValue().toString().isEmpty()) {

            luSystemEntity.setSystemCode(event.getNewValue().toString());
            luSystemEntity = luSystemInterface.getSysDetail(luSystemEntity);
            costCenterEntity.setSystemId(luSystemEntity);
            FaTransmissionEntity.setSystemNo(luSystemEntity);
        }
    }

    //Asset Type value change listner
    /**
     *
     * @param event
     */
    public void AssetTypeChange(ValueChangeEvent event) {

        if (!event.getNewValue().toString().isEmpty()) {

            String sysno = event.getNewValue().toString();
            Integer assetId = Integer.valueOf(sysno);

            assetTypeEntity.setAssetId(assetId);
            FaTransmissionEntity.setAssetTypeNo(assetTypeEntity);
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

    /**
     *
     * @return
     */
    public void Wfsave() {
        workFlow.setTransmissionId(FaTransmissionEntity);
        workFlowInterface.create(workFlow);
    }

    public void saveTransmission() {

        try {
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(SessionBean.getUserName(), "saveTransmission", dataset)) {
                //put ur code here...!
                if (updateStatus == 0) {

                    try {
                        Integer serviceLife = FaTransmissionEntity.getServiceLife();
                        if (serviceLife > 100) {
                            JsfUtil.addFatalMessage(" Service Life Should be Less Than 100 ");

                        }
                        FaTransmissionEntity.setTrNo(newTrId);
                        FaTransmissionEntity.setTrPrepared(workFlow.getProcessedBy());
                        FaTransmissionEntity.setTrStatus(Constants.PREPARE_VALUE);
                        workFlow.setDecision(Constants.PREPARE_VALUE);
                        workFlow.setProcessedBy(SessionBean.getUserId());
                        FaTransmissionEntity.getTransmissionList().add(workFlow);

                        FaTransmissionInterface.create(FaTransmissionEntity);
                        //Wfsave();
                        JsfUtil.addSuccessMessage("Transmission information is Saved");

                    } catch (Exception ex) {
                        JsfUtil.addFatalMessage(" Something Error Occured on Creating the Data ");

                    }

                } else {

                    try {

                        FaTransmissionEntity.setTrStatus(Constants.PREPARE_VALUE);
                        workFlow.setDecision(Constants.PREPARE_VALUE);
                        workFlow.setProcessedBy(SessionBean.getUserId());
                        FaTransmissionInterface.edit(FaTransmissionEntity);
                        Wfsave();
                        JsfUtil.addSuccessMessage("Transmission information is Updated");
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
        SystemCodeList.clear();
        AssetTypeList.clear();

        FaTransmissionEntity = null;
        luSystemEntity = null;
        generalLedgerEntity = null;
        assetTypeEntity = null;

        updateStatus = 0;
        saveorUpdateBundle = "Save";
    }

    /**
     *
     * @return
     */
    public SelectItem[] getListSys() {
        return JsfUtil.getSelectItems(luSystemInterface.findAll(), true);
    }

    /**
     *
     * @return
     */
    public SelectItem[] getGLdata() {
        return JsfUtil.getSelectItems(generalLedgerInterface.findAll(), true);
    }

    String newTrId;
    String lastTrId = "0";

    public String generateTrNo() {

        MmsFaTransmission lastInsuranceID = FaTransmissionInterface.getLastTraId();
        if (lastInsuranceID != null) {
            lastTrId = lastInsuranceID.getTransmissionId().toString();
        }

        DateFormat f = new SimpleDateFormat("yyyy");
        Date now = new Date();

        int id = 0;
        if (lastTrId.equals("0")) {
            id = 1;
            newTrId = "TransmissionNo-" + id + "/" + f.format(now);
        } else {

            String[] lastInspNos = lastTrId.split("-");
            String lastDatesPatern = lastInspNos[0];

            String[] lastDatesPaterns = lastDatesPatern.split("/");
            id = Integer.parseInt(lastDatesPaterns[0]);
            id = id + 1;
            newTrId = "TransmissionNo-" + id + "/" + f.format(now);
        }

        return newTrId;
    }

    public void btn_Search_Action() {
        clearPage();
        renderPnlCreateTransmission = false;
        renderPnlManPage = true;
    }

    public void createNewTrInfo() {
        clearPage();
        saveorUpdateBundle = "Save";
        // disableBtnCreate = false;

        if (createOrSearchBundle.equals("New")) {
            renderPnlCreateTransmission = true;
            renderPnlManPage = false;
            createOrSearchBundle = "Search";
            setCreateOrSearchBundle(createOrSearchBundle);
            setIcone("ui-icon-search");

        } else if (createOrSearchBundle.equals("Search")) {
            renderPnlCreateTransmission = false;
            renderPnlManPage = true;
            createOrSearchBundle = "New";
            setCreateOrSearchBundle(createOrSearchBundle);
            setIcone("ui-icon-plus");
        }

    }

    List<MmsFaTransmission> checkerList = new ArrayList<>();
    List<MmsFaTransmission> costList = new ArrayList<>();

    BigDecimal depYr1;
    BigDecimal BookV2;
    BigDecimal AccDep3;

    public void searchTransmisionNo() {

        String str = FaTransmissionEntity.getTrNo();

        FaTransmissionEntity.setTrNo(str);
        List<MmsFaTransmission> TransNo = null;  //if (!FaTransmissionEntity.getTrNo().equals(""))
        FaTransmissionEntity.setTrPrepared(SessionBean.getUserId());

        if (!(FaTransmissionEntity.getTrNo().isEmpty()) && (FaTransmissionEntity.getFromName().isEmpty())) {

            FaTransmissionEntity.setTrNo(str);

            TransNo = FaTransmissionInterface.searchByTransNoAndTrPrep(FaTransmissionEntity);

            if (TransNo.isEmpty()) {
//              
                JsfUtil.addFatalMessage("There is no Data ...");

            } else {

                //code to be written here .....
                costList.clear();
                costList = TransNo;
                int size = costList.size();
                MmsFaTransmission fa1 = new MmsFaTransmission();
                if (size != 0) {
                    fa1 = costList.get(0);
                }
                TransNo.clear();
                TransNo = FaTransmissionInterface.searchByTrans(FaTransmissionEntity);
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
                        MmsFaTransmission faa = new MmsFaTransmission();
                        faa = TransNo.get(i);
                        int sizeDepr = TransNo.get(i).getFmsDprTransmissonList().size();
                        for (int j = 0; j < sizeDepr; j++) {
                            int stat = TransNo.get(i).getFmsDprTransmissonList().get(j).getStatus();
                            if (stat == 1) {
                                depYr1 = TransNo.get(i).getFmsDprTransmissonList().get(j).getDprYear();
                                BookV2 = TransNo.get(i).getFmsDprTransmissonList().get(j).getNetBookValue();
                                AccDep3 = TransNo.get(i).getFmsDprTransmissonList().get(j).getAccumulatedDpr();
                                faa.setDprYear(depYr1);
                                faa.setAccumulatedDpr(AccDep3);
                                faa.setNetBookValue(BookV2);

                                checkerList.add(faa);

                            }
                        }
                    }

                    recerateTransSerachModel();
                }

            }
        } else if ((FaTransmissionEntity.getTrNo().isEmpty()) && (!FaTransmissionEntity.getFromName().isEmpty())) {

            System.out.println(" -------- inside Loc Name ------------");

            String str2 = FaTransmissionEntity.getFromName();
            FaTransmissionEntity.setFromName(str2);
            System.out.println("--------  Loc Name ----- " + str2);
            TransNo = FaTransmissionInterface.searchByLocNameAndTrPrep(FaTransmissionEntity);
            if (TransNo.isEmpty()) {
//              
                JsfUtil.addFatalMessage("There is no Data ...");

            } else {

                //code to be written here .....
                costList.clear();
                costList = TransNo;
                int size = costList.size();
                MmsFaTransmission fa1 = new MmsFaTransmission();
                if (size != 0) {
                    fa1 = costList.get(0);
                }
                TransNo.clear();
                TransNo = FaTransmissionInterface.searchByTrans2(FaTransmissionEntity);
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
                        MmsFaTransmission faa = new MmsFaTransmission();
                        faa = TransNo.get(i);
                        int sizeDepr = TransNo.get(i).getFmsDprTransmissonList().size();
                        for (int j = 0; j < sizeDepr; j++) {
                            int stat = TransNo.get(i).getFmsDprTransmissonList().get(j).getStatus();
                            if (stat == 1) {
                                depYr1 = TransNo.get(i).getFmsDprTransmissonList().get(j).getDprYear();
                                BookV2 = TransNo.get(i).getFmsDprTransmissonList().get(j).getNetBookValue();
                                AccDep3 = TransNo.get(i).getFmsDprTransmissonList().get(j).getAccumulatedDpr();
                                faa.setDprYear(depYr1);
                                faa.setAccumulatedDpr(AccDep3);
                                faa.setNetBookValue(BookV2);

                                checkerList.add(faa);

                            }
                        }
                    }

                    recerateTransSerachModel();
                }

            }

        } else {
            System.out.println("======allfind===" + checkerList);
            checkerList = FaTransmissionInterface.searchAllTransmissionsInfoByPreparerId(FaTransmissionEntity);
//            checkerList = FaTransmissionInterface.findAll1();
            recerateTransSerachModel();
        }
    }

    private void recerateTransSerachModel() {

        mmsTransSearchInfoDataModel = null;
        mmsTransSearchInfoDataModel = new ListDataModel(new ArrayList<>(checkerList));
    }

    public void onRowEdit(RowEditEvent event) {

        renderPnlCreateTransmission = true;
        renderPnlManPage = false;
        //activeIndex = "0";
        saveorUpdateBundle = "Update";
        //saveorUpdateBundle = "Search";
        setIcone("ui-icon-search");
        //disableBtnCreate = true;
        updateStatus = 1;
        FaTransmissionEntity.getTrRegion();

        int rowIndex = mmsTransSearchInfoDataModel.getRowIndex();

        FaTransmissionEntity = checkerList.get(rowIndex);

    }

    public void rowSelect(SelectEvent event) {
        FaTransmissionEntity = (MmsFaTransmission) event.getObject();
        FaTransmissionEntity.setTransmissionId(FaTransmissionEntity.getTransmissionId());
        FaTransmissionEntity = FaTransmissionInterface.getSelectedRequest(FaTransmissionEntity.getTransmissionId());
        setDisablereevaluation(false);
        //Type Name
        assetTypeEntity.setAssetId(FaTransmissionEntity.getAssetTypeNo().getAssetId());
        //Sys No
        luSystemEntity.setSystemCode(FaTransmissionEntity.getSystemNo().getSystemCode());

        luSystemEntity = FaTransmissionEntity.getSystemNo();

        FaTransmissionEntity.setAccumulatedDpr(AccDep3);
        FaTransmissionEntity.setDprYear(depYr1);
        FaTransmissionEntity.setNetBookValue(BookV2);

        renderPnlManPage = false;
        renderPnlCreateTransmission = true;
        saveorUpdateBundle = "Update";
        setIcone("ui-icon-search");
        createOrSearchBundle = "Search";
        updateStatus = 1;

    }

}
