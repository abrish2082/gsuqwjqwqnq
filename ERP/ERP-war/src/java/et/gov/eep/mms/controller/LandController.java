/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.mms.controller;

import et.gov.eep.commonApplications.businessLogic.WfMmsProcessedBeanLocal;
import et.gov.eep.commonApplications.entity.WfMmsProcessed;
import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.mms.businessLogic.MmsLandBeanLocal;
import et.gov.eep.mms.entity.MmsFaLand;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
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
 * @author Nebiyou Samuel
 */
@Named(value = "landController")
@ViewScoped
public class LandController implements Serializable {

    @EJB
    private MmsLandBeanLocal landinterface;
    @EJB
    WfMmsProcessedBeanLocal workFlowInterface;
    @Inject
    private MmsFaLand landEntity;
    @Inject
    WfMmsProcessed workFlow;
    @Inject
    SessionBean SessionBean;
    @Inject
    WorkFlow workflow;
    private String saveorUpdateBundle = "Save";
    int updateStatus = 0;
    private DataModel<MmsFaLand> mmsLandSearchInfoDataModel;
    private boolean renderPnlCreateLand = false;
    private boolean renderPnlManPage = true;
    private String icone = "ui-icon-plus";
    private String createOrSearchBundle = "New";
    private MmsFaLand hpSelect;
    private boolean renderDecision = false;
    private String UserName;

    public LandController() {
    }

    public MmsFaLand getHpSelect() {
        return hpSelect;
    }

    @PostConstruct
    public void initwfMmsProcessed() {
        workFlow.setProcessedBy(SessionBean.getUserId());
        setUserName(SessionBean.getUserName());
        // System.out.println("is App=="+workflow.isApproveStatus()+"is chech=="+workflow.isCheckStatus()+"is prepa=="+workflow.isPrepareStatus());

    }

    public void setHpSelect(MmsFaLand hpSelect) {
        this.hpSelect = hpSelect;
    }

    public MmsFaLand getLandEntity() {
        if (landEntity == null) {
            landEntity = new MmsFaLand();
        }
        return landEntity;
    }

    public void setLandEntity(MmsFaLand landEntity) {
        this.landEntity = landEntity;
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

    public DataModel<MmsFaLand> getMmsLandSearchInfoDataModel() {
        if (mmsLandSearchInfoDataModel == null) {
            mmsLandSearchInfoDataModel = new ListDataModel<>();
        }
        return mmsLandSearchInfoDataModel;

    }

    public void setMmsLandSearchInfoDataModel(DataModel<MmsFaLand> mmsLandSearchInfoDataModel) {
        this.mmsLandSearchInfoDataModel = mmsLandSearchInfoDataModel;
    }

    public boolean isRenderPnlCreateLand() {
        return renderPnlCreateLand;
    }

    public void setRenderPnlCreateLand(boolean renderPnlCreateLand) {
        this.renderPnlCreateLand = renderPnlCreateLand;
    }

    public boolean isRenderPnlManPage() {
        return renderPnlManPage;
    }

    public void setRenderPnlManPage(boolean renderPnlManPage) {
        this.renderPnlManPage = renderPnlManPage;
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

    public void Wfsave() {
        workFlow.setLandId(landEntity);
        workFlowInterface.create(workFlow);
    }

    public void saveLand() {

        try {
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(SessionBean.getUserName(), "saveLand", dataset)) {
                //  put ur code here...!

                if (updateStatus == 0) {
                    try {
                        landEntity.setLandNo(newLandId);
                        landEntity.setLdStatus(Constants.PREPARE_VALUE);
                        workFlow.setDecision(Constants.PREPARE_VALUE);
                        workFlow.setProcessedBy(SessionBean.getUserId());
                        landEntity.setPreparedBy(workFlow.getProcessedBy());
                        landEntity.getLandList().add(workFlow);
                        landinterface.create(landEntity);
                        //Wfsave();
                        JsfUtil.addSuccessMessage("A New Land information is Saved");
                    } catch (Exception ex) {
                        JsfUtil.addFatalMessage("Something Error Occured on Creating the Data ");

                    }

                } else {
                    try {
                        landEntity.setLdStatus(Constants.PREPARE_VALUE);
                        workFlow.setDecision(Constants.PREPARE_VALUE);
                        workFlow.setProcessedBy(SessionBean.getUserId());
                        landEntity.setPreparedBy(workFlow.getProcessedBy());
                        landinterface.edit(landEntity);
                        Wfsave();
                        JsfUtil.addSuccessMessage("Land information is Updated");
                    } catch (Exception ex) {
                        JsfUtil.addFatalMessage("Something Error Occured on Updating the Data ");

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
        updateStatus = 0;
        saveorUpdateBundle = "Save";
        landEntity = null;
        mmsLandSearchInfoDataModel = null;
    }

    List<MmsFaLand> checkerList = new ArrayList<>();

    public void onRowEdit(RowEditEvent event) {

        renderPnlCreateLand = true;
        renderPnlManPage = false;
        //activeIndex = "0";
        saveorUpdateBundle = "Update";
        // saveorUpdateBundle = "Search";
        setIcone("ui-icon-search");
        //disableBtnCreate = true;
        updateStatus = 1;

        int rowIndex = mmsLandSearchInfoDataModel.getRowIndex();

        landEntity = checkerList.get(rowIndex);

    }

    public void btn_Search_Action() {
        clearPage();
        renderPnlCreateLand = false;
        renderPnlManPage = true;
    }

    public void createNewLandInfo() {
        clearPage();
        saveorUpdateBundle = "Save";
        // disableBtnCreate = false;

        if (createOrSearchBundle.equals("New")) {
            renderPnlCreateLand = true;
            renderPnlManPage = false;
            createOrSearchBundle = "Search";
            setCreateOrSearchBundle(createOrSearchBundle);
            setIcone("ui-icon-search");

        } else if (createOrSearchBundle.equals("Search")) {
            renderPnlCreateLand = false;
            renderPnlManPage = true;
            createOrSearchBundle = "New";
            setCreateOrSearchBundle(createOrSearchBundle);
            setIcone("ui-icon-plus");
        }

    }

    String newLandId;
    String lastLandId = "0";

    public String generateLandNo() {

        MmsFaLand lastInsuranceID = landinterface.getLastLandId();
        if (lastInsuranceID != null) {
            lastLandId = lastInsuranceID.getLandId().toString();
        }

        DateFormat f = new SimpleDateFormat("yyyy");
        Date now = new Date();

        int id = 0;
        if (lastLandId.equals("0")) {
            id = 1;
            newLandId = "LandNo-" + id + "/" + f.format(now);
        } else {

            String[] lastInspNos = lastLandId.split("-");
            String lastDatesPatern = lastInspNos[0];

            String[] lastDatesPaterns = lastDatesPatern.split("/");
            id = Integer.parseInt(lastDatesPaterns[0]);
            id = id + 1;
            newLandId = "LandNo-" + id + "/" + f.format(now);
        }

        return newLandId;
    }
    List<MmsFaLand> allLandInfo;

    public void searchLandInformation1() {
        System.out.println("serd");
        landEntity.setPreparedBy(SessionBean.getUserId());
        System.out.println("get id " + landEntity.getPreparedBy());
        System.out.println("name==" + SessionBean.getUserName());
        if ((!landEntity.getLandNo().isEmpty()) && (landEntity.getName().isEmpty())) {

            String str = landEntity.getLandNo();
            landEntity.setLandNo(str);
            allLandInfo = landinterface.searchLandByParameterPrefixAndLandPrep(landEntity);

            checkerList.clear();
            checkerList = allLandInfo;

            recerateSerachModel();

        } else if ((landEntity.getLandNo().isEmpty()) && (!landEntity.getName().isEmpty())) {

            String str2 = landEntity.getName();
            landEntity.setName(str2);
            allLandInfo = landinterface.searchLandByNameAndLandPrep(landEntity);

            checkerList.clear();
            checkerList = allLandInfo;

            recerateSerachModel();
        } else {
            System.out.println("serach");
            allLandInfo = landinterface.searchAllLandInfoByPreparerId(landEntity.getPreparedBy());
            System.out.println("===allfind=====" + allLandInfo);
//            checkerList = transportInterface.findAll1();
            recerateSerachModel();
        }
    }

    private void recerateSerachModel() {
        mmsLandSearchInfoDataModel = null;
        mmsLandSearchInfoDataModel = new ListDataModel(new ArrayList<>(allLandInfo));
    }

    public void rowSelect(SelectEvent event) {
        landEntity = (MmsFaLand) event.getObject();
        landEntity.setLandId(landEntity.getLandId());
        landEntity = landinterface.getSelectedRequest(landEntity.getLandId());

        renderPnlManPage = false;
        renderPnlCreateLand = true;
        saveorUpdateBundle = "Update";
        setIcone("ui-icon-search");
        createOrSearchBundle = "Search";
        updateStatus = 1;

    }

}
