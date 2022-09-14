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
import et.gov.eep.fcms.businessLogic.admin.FmsLuSystemBeanLocal;
import et.gov.eep.fcms.entity.admin.FmsCostCenter;
import et.gov.eep.fcms.entity.admin.FmsLuSystem;
import et.gov.eep.fcms.entity.fixedasset.FmsDprTransport;
import et.gov.eep.mms.businessLogic.MmsFaTransportBeanLocal;
import et.gov.eep.mms.entity.MmsFaTransport;
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
 * @NEBIYOU Samuel
 */
@Named(value = "transportController")
@ViewScoped
public class TransportController implements Serializable {

    @EJB
    private FmsLuSystemBeanLocal luSystemInterface;
    @EJB
    WfMmsProcessedBeanLocal workFlowInterface;
    @EJB
    private MmsFaTransportBeanLocal transportInterface;
    @Inject
    private FmsLuSystem luSystemEntity;
    @Inject
    private FmsCostCenter costCenterEntity;
    @Inject
    private MmsFaTransport transportEntity;
    @Inject
    private FmsDprTransport TrDprEntity;
    @Inject
    WfMmsProcessed workFlow;
@Inject
    SessionBean SessionBean;
    @Inject
    WorkFlow workflow;
    private String saveorUpdateBundle = "Save";
    int updateStatus = 0;
    private DataModel<MmsFaTransport> mmsTransportSearchInfoDataModel;
    private boolean renderPnlCreateTransport = false;
    private boolean renderPnlManPage = true;
    private String icone = "ui-icon-plus";
    private String createOrSearchBundle = "New";
    private MmsFaTransport hpSelect;
    private boolean disablereevaluation = true;
    private boolean renderDecision = false;
    private String UserName;

    public TransportController() {
    }

    public MmsFaTransport getHpSelect() {
        return hpSelect;
    }

    public void setHpSelect(MmsFaTransport hpSelect) {
        this.hpSelect = hpSelect;
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

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String UserName) {
        this.UserName = UserName;
    }

    public boolean isRenderDecision() {
        return renderDecision;
    }

    public void setRenderDecision(boolean renderDecision) {
        this.renderDecision = renderDecision;
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

    public FmsDprTransport getTrDprEntity() {
        if (TrDprEntity == null) {
            TrDprEntity = new FmsDprTransport();
        }
        return TrDprEntity;
    }

    public void setTrDprEntity(FmsDprTransport TrDprEntity) {
        this.TrDprEntity = TrDprEntity;
    }

    public MmsFaTransport getTransportEntity() {
        if (transportEntity == null) {
            transportEntity = new MmsFaTransport();
        }
        return transportEntity;
    }

    public void setTransportEntity(MmsFaTransport transportEntity) {
        this.transportEntity = transportEntity;
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

    public boolean isRenderPnlCreateTransport() {
        return renderPnlCreateTransport;
    }

    public void setRenderPnlCreateTransport(boolean renderPnlCreateTransport) {
        this.renderPnlCreateTransport = renderPnlCreateTransport;
    }

    public boolean isRenderPnlManPage() {
        return renderPnlManPage;
    }

    public void setRenderPnlManPage(boolean renderPnlManPage) {
        this.renderPnlManPage = renderPnlManPage;
    }

    public boolean isDisablereevaluation() {
        return disablereevaluation;
    }

    public void setDisablereevaluation(boolean disablereevaluation) {
        this.disablereevaluation = disablereevaluation;
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

    public DataModel<MmsFaTransport> getMmsTransportSearchInfoDataModel() {
        if (mmsTransportSearchInfoDataModel == null) {
            mmsTransportSearchInfoDataModel = new ListDataModel<>();
        }

        return mmsTransportSearchInfoDataModel;
    }

    public void setMmsTransportSearchInfoDataModel(DataModel<MmsFaTransport> mmsTransportSearchInfoDataModel) {
        this.mmsTransportSearchInfoDataModel = mmsTransportSearchInfoDataModel;
    }

    public void SystemChange(ValueChangeEvent event) {

        if (!event.getNewValue().toString().isEmpty()) {
            luSystemEntity = new FmsLuSystem();
            luSystemEntity.setSystemCode(event.getNewValue().toString());
            luSystemEntity = luSystemInterface.getSysDetail(luSystemEntity);
            costCenterEntity.setSystemId(luSystemEntity);
            transportEntity.setTpSysNo(luSystemEntity);
        }
    }
    //system code List

    List<FmsLuSystem> subSystemCodeList = new ArrayList<>();

    public List<FmsLuSystem> getSystemCodelist() {
        subSystemCodeList = luSystemInterface.findAll();
        return subSystemCodeList;
    }

    List<MmsFaTransport> allTransportInfoList;

    private void recerateWindSerachModel() {

        mmsTransportSearchInfoDataModel = null;
        mmsTransportSearchInfoDataModel = new ListDataModel(new ArrayList<>(allTransportInfoList));

    }

    public void Wfsave() {
        workFlow.setTransportId(transportEntity);
        workFlowInterface.create(workFlow);
    }

    public void saveTransport() {

        try {
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(SessionBean.getUserName(), "saveTransport", dataset)) {
                //  put ur code here...!
                if (updateStatus == 0) {
                    System.out.println("----save gebtual-----");
                    try {
                        int serviceLife = transportEntity.getTpServiceYear();
                        System.out.println("----service life =" + serviceLife);
                        if (serviceLife > 100) {
                            System.out.println("----in update-----");
                            JsfUtil.addFatalMessage(" Service Life Should be Less Than 100 ");

                        } else {
                            transportEntity.setTpNo(newTrId);
                            transportEntity.setTpPrepared(SessionBean.getUserId());
                            transportEntity.setTpStatus(Constants.PREPARE_VALUE);
                            workFlow.setDecision(Constants.PREPARE_VALUE);
                            workFlow.setProcessedBy(SessionBean.getUserId());
                            transportEntity.getTransportList().add(workFlow);
                            transportInterface.create(transportEntity);
                            // Wfsave();
                            JsfUtil.addSuccessMessage("Transport information is Saved");
                        }
                    } catch (Exception ex) {
                        JsfUtil.addFatalMessage(" Something Error Occured on Creating the Data ");
                        ex.printStackTrace();

                    }

                } else {

                    try {
                        transportEntity.setTpStatus(Constants.PREPARE_VALUE);
                        workFlow.setDecision(Constants.PREPARE_VALUE);
                        workFlow.setProcessedBy(SessionBean.getUserId());
                        transportInterface.edit(transportEntity);
                        Wfsave();
                        JsfUtil.addSuccessMessage("Transport information is Updated");
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

        subSystemCodeList.clear();
        luSystemEntity = null;
        transportEntity = null;
        updateStatus = 0;
        saveorUpdateBundle = "Save";
        mmsTransportSearchInfoDataModel = null;
    }

    List<MmsFaTransport> checkerList = new ArrayList<>();
    List<MmsFaTransport> costList = new ArrayList<>();

    BigDecimal depYr1;
    BigDecimal BookV2;
    BigDecimal AccDep3;

    public void searchWindInformation1() {

        String str = transportEntity.getTpNo();
        transportEntity.setTpNo(str);
        List<MmsFaTransport> TransNo = null;
        transportEntity.setTpPrepared(SessionBean.getUserId());
        if (!transportEntity.getTpNo().isEmpty()) {

            transportEntity.setTpNo(str);

            TransNo = transportInterface.searchByTrNoAndTrPrep(transportEntity);
            if (TransNo.size() == 0) {
                JsfUtil.addFatalMessage("There is No Data ...");
            } else {
                costList.clear();
                costList = TransNo;
                int size = costList.size();
                MmsFaTransport fa1 = new MmsFaTransport();
                if (size != 0) {
                    fa1 = costList.get(0);
                }
                TransNo.clear();
                TransNo = transportInterface.searchBytr(transportEntity);
                if (TransNo.size() == 0) {

                    BigDecimal depYr1 = new BigDecimal(0.0);
                    BigDecimal BookV2 = new BigDecimal(0.0);
                    BigDecimal AccDep3 = new BigDecimal(0.0);
                    fa1.setDprYear(depYr1);
                    fa1.setAccumulatedDpr(AccDep3);
                    fa1.setNetBookValue(BookV2);

                    checkerList.add(fa1);

                    recerateTransSerachModel();

//                    JsfUtil.addFatalMessage("Depreciation is not yet Calculated");
                } else {
                    int id = TransNo.get(0).getTransportId();
                    transportEntity.setTransportId(id);
                    TrDprEntity.setTpAssetId(transportEntity);
                    checkerList.clear();
                    for (int i = 0; i < TransNo.size(); i++) {
                        MmsFaTransport fa = new MmsFaTransport();
                        fa = TransNo.get(i);
                        int sizeDepr = TransNo.get(i).getFmsDprTransportList().size();
                        for (int j = 0; j < sizeDepr; j++) {
                            int stat = TransNo.get(i).getFmsDprTransportList().get(j).getStatus();
                            if (stat == 1) {
                                depYr1 = TransNo.get(i).getFmsDprTransportList().get(j).getDprYear();
                                BookV2 = TransNo.get(i).getFmsDprTransportList().get(j).getNetBookValue();
                                AccDep3 = TransNo.get(i).getFmsDprTransportList().get(j).getAccumulatedDpr();
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
//            transportEntity.setTpPrepared(SessionBean.getUserId());

            checkerList = transportInterface.searchAllTransmissionsInfoByPreparerId(transportEntity.getTpPrepared());
            System.out.println("===allfind=====" + checkerList);
//            checkerList = transportInterface.findAll1();
            recerateTransSerachModel();
        }

    }

    private void recerateTransSerachModel() {

        mmsTransportSearchInfoDataModel = null;
        mmsTransportSearchInfoDataModel = new ListDataModel(new ArrayList<>(checkerList));

    }

    @PostConstruct
    public void renderWithPreselectedValue() {

        workFlow.setProcessedBy(SessionBean.getUserId());
        setUserName(SessionBean.getUserName());
        if (!(getFlash().get("trnasportInfo") == null)) {
            transportEntity = (MmsFaTransport) getFlash().get("trnasportInfo");
            luSystemEntity = transportEntity.getTpSysNo();

            renderPnlCreateTransport = true;
            renderPnlManPage = false;
            createOrSearchBundle = "Search";
            setCreateOrSearchBundle(createOrSearchBundle);
            setIcone("ui-icon-search");
        } else {
            renderPnlCreateTransport = false;
            renderPnlManPage = true;
            createOrSearchBundle = "New";
            setCreateOrSearchBundle(createOrSearchBundle);
            setIcone("ui-icon-plus");
        }

    }

    public void onRowEdit(RowEditEvent event) {

        renderPnlCreateTransport = true;
        renderPnlManPage = false;
        //activeIndex = "0";
        saveorUpdateBundle = "Update";
        //saveorUpdateBundle = "Search";
        setIcone("ui-icon-search");
        //disableBtnCreate = true;
        updateStatus = 1;

        int rowIndex = mmsTransportSearchInfoDataModel.getRowIndex();

        transportEntity = checkerList.get(rowIndex);

    }

    public void btn_Search_Action() {
        clearPage();
        renderPnlCreateTransport = false;
        renderPnlManPage = true;
    }

    public void createNewTransportInfo() {
        clearPage();
        saveorUpdateBundle = "Save";
        // disableBtnCreate = false;

        if (createOrSearchBundle.equals("New")) {
            renderPnlCreateTransport = true;
            renderPnlManPage = false;
            createOrSearchBundle = "Search";
            setCreateOrSearchBundle(createOrSearchBundle);
            setIcone("ui-icon-search");

        } else if (createOrSearchBundle.equals("Search")) {
            renderPnlCreateTransport = false;
            renderPnlManPage = true;
            createOrSearchBundle = "New";
            setCreateOrSearchBundle(createOrSearchBundle);
            setIcone("ui-icon-plus");
        }

    }

    String newTrId;
    String lastTrId = "0";

    public String generateTransportNo() {

        MmsFaTransport lastInsuranceID = transportInterface.getLastTrId();
        if (lastInsuranceID != null) {
            lastTrId = lastInsuranceID.getTransportId().toString();
        }

        DateFormat f = new SimpleDateFormat("yyyy");
        Date now = new Date();

        int id = 0;
        if (lastTrId.equals("0")) {
            id = 1;
            newTrId = "TransportNo-" + id + "/" + f.format(now);
        } else {

            String[] lastInspNos = lastTrId.split("-");
            String lastDatesPatern = lastInspNos[0];

            String[] lastDatesPaterns = lastDatesPatern.split("/");
            id = Integer.parseInt(lastDatesPaterns[0]);
            id = id + 1;
            newTrId = "TransportNo-" + id + "/" + f.format(now);
        }

        return newTrId;
    }

    public void rowSelect(SelectEvent event) {
        transportEntity = (MmsFaTransport) event.getObject();
        transportEntity.setTransportId(transportEntity.getTransportId());
        transportEntity = transportInterface.getSelectedRequest(transportEntity.getTransportId());
        setDisablereevaluation(false);
        //Sys No
        //luSystemEntity.setSystemCode(transportEntity.getTpSysNo().getSystemCode());

        luSystemEntity = transportEntity.getTpSysNo();

        transportEntity.setAccumulatedDpr(AccDep3);
        transportEntity.setDprYear(depYr1);
        transportEntity.setNetBookValue(BookV2);

        renderPnlManPage = false;
        renderPnlCreateTransport = true;
        saveorUpdateBundle = "Update";
        updateStatus = 1;
        setIcone("ui-icon-search");
        createOrSearchBundle = "Search";

    }

}
