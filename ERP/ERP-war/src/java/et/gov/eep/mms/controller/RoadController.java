/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.mms.controller;

import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.mms.businessLogic.MmsRoadBeanLocal;
import et.gov.eep.mms.entity.MmsFaRoad;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.event.RowEditEvent;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author Nebiyou Samuel
 */
@Named(value = "roadController")
@ViewScoped
public class RoadController implements Serializable {

    @EJB
    private MmsRoadBeanLocal roadinterface;
    @Inject
    private MmsFaRoad roadEntity;

    private String saveorUpdateBundle = "Save";
    int updateStatus = 0;
    private DataModel<MmsFaRoad> mmsRoadSearchInfoDataModel;
    private boolean renderPnlCreateRoad = false;
    private boolean renderPnlManPage = true;
    private String icone = "ui-icon-plus";
    private String createOrSearchBundle = "New";
    private MmsFaRoad hpSelect;

    public RoadController() {
    }

    public MmsFaRoad getHpSelect() {
        return hpSelect;
    }

    public void setHpSelect(MmsFaRoad hpSelect) {
        this.hpSelect = hpSelect;
    }

    public MmsFaRoad getRoadEntity() {
        if (roadEntity == null) {
            roadEntity = new MmsFaRoad();
        }
        return roadEntity;
    }

    public void setRoadEntity(MmsFaRoad roadEntity) {
        this.roadEntity = roadEntity;
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

    public DataModel<MmsFaRoad> getMmsRoadSearchInfoDataModel() {

        if (mmsRoadSearchInfoDataModel == null) {
            mmsRoadSearchInfoDataModel = new ListDataModel<>();
        }
        return mmsRoadSearchInfoDataModel;
    }

    public void setMmsRoadSearchInfoDataModel(DataModel<MmsFaRoad> mmsRoadSearchInfoDataModel) {
        this.mmsRoadSearchInfoDataModel = mmsRoadSearchInfoDataModel;
    }

    public boolean isRenderPnlCreateRoad() {
        return renderPnlCreateRoad;
    }

    public void setRenderPnlCreateRoad(boolean renderPnlCreateRoad) {
        this.renderPnlCreateRoad = renderPnlCreateRoad;
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

    public void saveRoad() {

        try {
//            AAA securityService = new AAA();
//            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
//            String systemBundle = "et/gov/eep/commonApplications/securityServer";
//            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");          
//            if (security.checkAccess(SessionBean.getUserName(), "saveRoad", dataset)) {
//                 put ur code here...!
            if (updateStatus == 0) {

                try {
                    
                    
                    roadEntity.setRoadNo(newRoadId);
                    roadinterface.create(roadEntity);
                    JsfUtil.addSuccessMessage("A New Road information Has Been Saved ");
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JsfUtil.addFatalMessage("Something Error Occured on Creating The Data ");

                }

            } else {

                try {
                    roadinterface.edit(roadEntity);
                    JsfUtil.addSuccessMessage("Road information is Updated");
                } catch (Exception ex) {
                      ex.printStackTrace();
                    JsfUtil.addFatalMessage("Something Error Occured on Updating The Data ");

                }

            }

            clearPage();

            //            } else {
//         EventEntry eventEntry = new EventEntry();
//         eventEntry.setSessionId(sessionBean.getSessionID());
//         eventEntry.setUserId(sessionBean.getUserId());
//         QName qualifiedName = new QName("", "project");
//         JAXBElement<String> test = new JAXBElement<String> (qualifiedName,String.class,null,sessionBean.getUserName());
//         eventEntry.setUserLogin(test);
//..... add more information by calling fields defined in the object 
//            security.addEventLog(eventEntry, dataset);
//            }
        } catch (Exception ex) {

        }
    }

    private void clearPage() {
        updateStatus = 0;
        saveorUpdateBundle = "Save";
        roadEntity = null;
        mmsRoadSearchInfoDataModel = null;

    }

    List<MmsFaRoad> checkerList = new ArrayList<>();

    public void onRowEdit(RowEditEvent event) {

        renderPnlCreateRoad = true;
        renderPnlManPage = false;
        //activeIndex = "0";
        saveorUpdateBundle = "Update";
        // saveorUpdateBundle = "Search";
        setIcone("ui-icon-search");
        //disableBtnCreate = true;
        updateStatus = 1;

        int rowIndex = mmsRoadSearchInfoDataModel.getRowIndex();
        roadEntity = checkerList.get(rowIndex);

    }

    public void createNewLandInfo() {
        clearPage();
        saveorUpdateBundle = "Save";
        // disableBtnCreate = false;

        if (createOrSearchBundle.equals("New")) {
            renderPnlCreateRoad = true;
            renderPnlManPage = false;
            createOrSearchBundle = "Search";
            setCreateOrSearchBundle(createOrSearchBundle);
            setIcone("ui-icon-search");

        } else if (createOrSearchBundle.equals("Search")) {
            renderPnlCreateRoad = false;
            renderPnlManPage = true;
            createOrSearchBundle = "New";
            setCreateOrSearchBundle(createOrSearchBundle);
            setIcone("ui-icon-plus");
        }

    }

    String newRoadId;
    String lastRoadId = "0";

    public String generateLandNo() {

        MmsFaRoad lastInsuranceID = roadinterface.getLastRoadId();
        if (lastInsuranceID != null) {
            lastRoadId = lastInsuranceID.getRoadId().toString();
        }

        DateFormat f = new SimpleDateFormat("yyyy");
        Date now = new Date();

        int id = 0;
        if (lastRoadId.equals("0")) {
            id = 1;
            newRoadId = "RoadNo-" + id + "/" + f.format(now);
        } else {

            String[] lastInspNos = lastRoadId.split("-");
            String lastDatesPatern = lastInspNos[0];

            String[] lastDatesPaterns = lastDatesPatern.split("/");
            id = Integer.parseInt(lastDatesPaterns[0]);
            id = id + 1;
            newRoadId = "RoadNo-" + id + "/" + f.format(now);
        }

        return newRoadId;
    }
    List<MmsFaRoad> allRoadInfo;

    public void searchRoadInformation1() {

        String str = roadEntity.getRoadNo();
        roadEntity.setRoadNo(str);
        if (roadEntity.getRoadNo() != null) {

            allRoadInfo = roadinterface.searchRoadByParameterPrefix(roadEntity);

            checkerList.clear();
            checkerList = allRoadInfo;

            recerateSerachModel();

        } else {
        }
    }

    private void recerateSerachModel() {
        mmsRoadSearchInfoDataModel = null;
        mmsRoadSearchInfoDataModel = new ListDataModel(new ArrayList<>(allRoadInfo));
    }

    public void rowSelect(SelectEvent event) {
        roadEntity = (MmsFaRoad) event.getObject();
        roadEntity.setRoadId(roadEntity.getRoadId());
        roadEntity = roadinterface.getSelectedRequest(roadEntity.getRoadId());

        renderPnlManPage = false;
        renderPnlCreateRoad = true;
        saveorUpdateBundle = "Update";
        setIcone("ui-icon-search");
        createOrSearchBundle = "Search";
        updateStatus = 1;

    }

}
