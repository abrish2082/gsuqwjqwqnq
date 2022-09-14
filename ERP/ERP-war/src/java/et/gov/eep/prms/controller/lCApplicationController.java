/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.prms.controller;

import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.prms.businessLogic.LCApplicationBeanLocal;
import et.gov.eep.prms.entity.PrmsLcApplication;
import et.gov.eep.prms.entity.PrmsSupplyProfile;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.ejb.EJB;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.event.RowEditEvent;
import org.primefaces.event.SelectEvent;

@Named(value = "lCApplicationController")
@ViewScoped
public class lCApplicationController implements Serializable {

    @EJB
    LCApplicationBeanLocal lCApplicationBeanLocal;

    @Inject
    PrmsLcApplication prmsLcApplication;

    @Inject
    PrmsSupplyProfile eepVendorReg;

    private String saveorUpdateBundle = "Save";
    int saveStatus = 0;
    DataModel<PrmsLcApplication> prmsLcApplicationModel;
    List<PrmsLcApplication> prmsLcApplicationList;
    Set<String> prmsLcApplicationDetlCheck = new HashSet<>();
    private String createOrSearchBundle = "New";
    private boolean disableBtnCreate = false;
    private String duplicattion = null;
    private boolean renderPnlCreateParty = false;
    private boolean renderPnlManPage = true;
    private String activeIndex;
    private String icone = "ui-icon-document";

    int updateStatus = 0;
    private int selectedRowIndex;

    PrmsLcApplication selectPrmsLcApplication;

    public lCApplicationController() {

    }

    public PrmsLcApplication getPrmsLcApplication() {
        if (prmsLcApplication == null) {
            prmsLcApplication = new PrmsLcApplication();
        }
        return prmsLcApplication;
    }

    public void setPrmsLcApplication(PrmsLcApplication prmsLcApplication) {
        this.prmsLcApplication = prmsLcApplication;
    }

    public String getSaveorUpdateBundle() {
        return saveorUpdateBundle;
    }

    public void setSaveorUpdateBundle(String saveorUpdateBundle) {
        this.saveorUpdateBundle = saveorUpdateBundle;
    }

    public int getSaveStatus() {
        return saveStatus;
    }

    public void setSaveStatus(int saveStatus) {
        this.saveStatus = saveStatus;
    }

    public DataModel<PrmsLcApplication> getPrmsLcApplicationModel() {
        if (prmsLcApplicationModel == null) {
            prmsLcApplicationModel = new ListDataModel();
        }
        return prmsLcApplicationModel;
    }

    public void setPrmsLcApplicationModel(DataModel<PrmsLcApplication> prmsLcApplicationModel) {
        this.prmsLcApplicationModel = prmsLcApplicationModel;
    }

    public List<PrmsLcApplication> getPrmsLcApplicationList() {
        if (prmsLcApplicationList == null) {
            prmsLcApplicationList = new ArrayList<>();
        }
        return prmsLcApplicationList;
    }

    public void setPrmsLcApplicationList(List<PrmsLcApplication> prmsLcApplicationList) {
        this.prmsLcApplicationList = prmsLcApplicationList;
    }

    public Set<String> getPrmsLcApplicationDetlCheck() {
        return prmsLcApplicationDetlCheck;
    }

    public void setPrmsLcApplicationDetlCheck(Set<String> prmsLcApplicationDetlCheck) {
        this.prmsLcApplicationDetlCheck = prmsLcApplicationDetlCheck;
    }

    public String getCreateOrSearchBundle() {
        return createOrSearchBundle;
    }

    public void setCreateOrSearchBundle(String createOrSearchBundle) {
        this.createOrSearchBundle = createOrSearchBundle;
    }

    public boolean isDisableBtnCreate() {
        return disableBtnCreate;
    }

    public void setDisableBtnCreate(boolean disableBtnCreate) {
        this.disableBtnCreate = disableBtnCreate;
    }

    public String getDuplicattion() {
        return duplicattion;
    }

    public void setDuplicattion(String duplicattion) {
        this.duplicattion = duplicattion;
    }

    public boolean isRenderPnlCreateParty() {
        return renderPnlCreateParty;
    }

    public void setRenderPnlCreateParty(boolean renderPnlCreateParty) {
        this.renderPnlCreateParty = renderPnlCreateParty;
    }

    public boolean isRenderPnlManPage() {
        return renderPnlManPage;
    }

    public void setRenderPnlManPage(boolean renderPnlManPage) {
        this.renderPnlManPage = renderPnlManPage;
    }

    public String getActiveIndex() {
        return activeIndex;
    }

    public void setActiveIndex(String activeIndex) {
        this.activeIndex = activeIndex;
    }

    public String getIcone() {
        return icone;
    }

    public void setIcone(String icone) {
        this.icone = icone;
    }

    public int getUpdateStatus() {
        return updateStatus;
    }

    public void setUpdateStatus(int updateStatus) {
        this.updateStatus = updateStatus;
    }

    public PrmsLcApplication getSelectPrmsLcApplication() {
        return selectPrmsLcApplication;
    }

    public void setSelectPrmsLcApplication(PrmsLcApplication selectPrmsLcApplication) {
        this.selectPrmsLcApplication = selectPrmsLcApplication;
    }

    public void createNewLCApplication() {
        saveorUpdateBundle = "Save";
        disableBtnCreate = false;
        if (createOrSearchBundle.equals("New")) {
            renderPnlCreateParty = true;
            renderPnlManPage = false;
            createOrSearchBundle = "Search";
            setIcone("ui-icon-search");
        } else if (createOrSearchBundle.equals("Search")) {
            renderPnlCreateParty = false;
            renderPnlManPage = true;
            createOrSearchBundle = "New";
            setIcone("ui-icon-document");
        }
    }

    public void onRowEdit(RowEditEvent event) {
        renderPnlCreateParty = true;
        renderPnlManPage = false;
        activeIndex = "0";
        saveorUpdateBundle = "Update";
        createOrSearchBundle = "Search";
        setIcone("ui-icon-search");
        disableBtnCreate = true;
        updateStatus = 1;

        int rowIndex = prmsLcApplicationModel.getRowIndex();
        prmsLcApplication = prmsLcApplicationList.get(rowIndex);

        recreatLCAppModel();

    }

    public void searchMrktByNo() {
        prmsLcApplicationList = lCApplicationBeanLocal.searchByLCAppNo(prmsLcApplication);
        recreatLCAppModel();

    }

    public void rowSelect(SelectEvent event) {
        prmsLcApplication = (PrmsLcApplication) event.getObject();
        prmsLcApplication.setLcappid(prmsLcApplication.getLcappid());
        prmsLcApplication = lCApplicationBeanLocal.getSelectedLCApp(prmsLcApplication.getLcappid());
        renderPnlManPage = false;
        renderPnlCreateParty = true;
        saveorUpdateBundle = "Update";
        recreatLCAppModel();
    }

    public void recreatLCAppModel() {
        prmsLcApplicationModel = null;
        prmsLcApplicationModel = new ListDataModel(new ArrayList(getPrmsLcApplicationList()));
    }

    public String save() {

        if (saveorUpdateBundle.equals("Save")) {

            try {
                generateLCAppNo();
                setPrmsLcApplication(prmsLcApplication);
                prmsLcApplication.setLcAppNo(newLCAppNo);
                lCApplicationBeanLocal.create(prmsLcApplication);
                JsfUtil.addSuccessMessage("LC application information is registered!!");
                return clear();

            } catch (Exception e) {
                e.printStackTrace();
                JsfUtil.addSuccessMessage("some thing is going to error" + e);
                clear();
                return null;

            }
        } else {
            try {
                System.out.println("----in update-----");
                lCApplicationBeanLocal.update(prmsLcApplication);
                JsfUtil.addSuccessMessage("LC application information is updated");
                saveorUpdateBundle = "Save";
                return clear();
            } catch (Exception e) {
                JsfUtil.addSuccessMessage("error== w/n data modification" + e);
                clear();
            }
        }
        return null;
    }

    public String clear() {
        prmsLcApplication = null;
        return null;
    }

    public int getSelectedRowIndex() {
        return selectedRowIndex;
    }

    public void setSelectedRowIndex(int selectedRowIndex) {
        this.selectedRowIndex = selectedRowIndex;
    }

    String lastLCAppNo = "0";
    String newLCAppNo;

    public String generateLCAppNo() {
        if (saveorUpdateBundle.equals("Update")) {
            newLCAppNo = prmsLcApplication.getLcAppNo();

        } else {
            PrmsLcApplication lastLCNo = lCApplicationBeanLocal.getLastLCAppNo();
            if (lastLCNo != null) {
                lastLCAppNo = lastLCNo.getLcappid().toString();

            }

            int newLCAppNoList = 0;
            if (lastLCAppNo.equals("0")) {
                newLCAppNoList = 1;
                newLCAppNo = "LCApp-NO-" + newLCAppNoList;
            } else {
                String[] lastInspNos = lastLCAppNo.split("-");
                String lastDatesPatern = lastInspNos[0];
                String[] lastDatesPaterns = lastDatesPatern.split("/");
                newLCAppNoList = Integer.parseInt(lastDatesPaterns[0]);
                newLCAppNoList = newLCAppNoList + 1;
                newLCAppNo = "LCApp-NO-" + newLCAppNoList;
            }
        }
        return newLCAppNo;

    }

    public SelectItem[] listOfSuppName() {
        return JsfUtil.getSelectItems(lCApplicationBeanLocal.listOfSuppName(), true);
    }

    public SelectItem[] listOfContractNO() {
        return JsfUtil.getSelectItems(lCApplicationBeanLocal.listOfContractNO(), true);
    }
}
