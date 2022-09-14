/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.controller.perdiem;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import org.primefaces.event.SelectEvent;
import securityBean.SessionBean;
import webService.AAA;
import webService.EventEntry;
import webService.IAdministration;
import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.fcms.businessLogic.perDiem.fmsLuAdditionalAmountBeanLocal;
import et.gov.eep.fcms.entity.perDiem.FmsGoodWillingPayment;

/**
 *
 * @author Mubarek jebel
 */
@Named(value = "fmsLuGoodWillingController")
@ViewScoped
public class FmsLuGoodWillingController implements Serializable {
//<editor-fold defaultstate="collapsed" desc=" Inject and @EJB">
    @Inject
    SessionBean SessionBean;
    @Inject
    FmsGoodWillingPayment fmsGoodWillingPayment;
    @EJB
    fmsLuAdditionalAmountBeanLocal additionalAmountBeanLocal;
//</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="variable declaration">
    FmsGoodWillingPayment goodWillingPayment;
    List<FmsGoodWillingPayment> goodWillingPaymentList;
    List<FmsGoodWillingPayment> fmsGoodWillingPaymentList = new ArrayList<>();
    DataModel<FmsGoodWillingPayment> goodWillingPaymentDModel;
    private boolean disableBtnCreate = false;
    private String saveorUpdateBundle = "Create";
    private String createOrSearchBundle = "New";
    private boolean renderPnlCreateGoodWilling = false;
    private boolean renderPnlManPage = true;
    private String icone = "ui-icon-document";
    private String activeIndex;
    int updateStatus = 0;
    String id;
//</editor-fold>
    public FmsLuGoodWillingController() {
    }

    //<editor-fold defaultstate="collapsed" desc="Getter and Setter Methods">
    public FmsGoodWillingPayment getGoodWillingPayment() {
        return goodWillingPayment;
    }

    public void setGoodWillingPayment(FmsGoodWillingPayment goodWillingPayment) {
        this.goodWillingPayment = goodWillingPayment;
    }

    public FmsGoodWillingPayment getFmsGoodWillingPayment() {
        if (fmsGoodWillingPayment == null) {
            fmsGoodWillingPayment = new FmsGoodWillingPayment();
        }
        return fmsGoodWillingPayment;
    }

    public void setFmsGoodWillingPayment(FmsGoodWillingPayment fmsGoodWillingPayment) {
        this.fmsGoodWillingPayment = fmsGoodWillingPayment;
    }

    public DataModel<FmsGoodWillingPayment> getGoodWillingPaymentDModel() {
        if (goodWillingPaymentDModel == null) {
            goodWillingPaymentDModel = new ListDataModel<>();
        }
        return goodWillingPaymentDModel;
    }

    public void setGoodWillingPaymentDModel(DataModel<FmsGoodWillingPayment> goodWillingPaymentDModel) {
        this.goodWillingPaymentDModel = goodWillingPaymentDModel;
    }

    public boolean isDisableBtnCreate() {
        return disableBtnCreate;
    }

    public void setDisableBtnCreate(boolean disableBtnCreate) {
        this.disableBtnCreate = disableBtnCreate;
    }

    public String getSaveorUpdateBundle() {
        return saveorUpdateBundle;
    }

    public void setSaveorUpdateBundle(String saveorUpdateBundle) {
        this.saveorUpdateBundle = saveorUpdateBundle;
    }

    public String getCreateOrSearchBundle() {
        return createOrSearchBundle;
    }

    public void setCreateOrSearchBundle(String createOrSearchBundle) {
        this.createOrSearchBundle = createOrSearchBundle;
    }

    public boolean isRenderPnlCreateGoodWilling() {
        return renderPnlCreateGoodWilling;
    }

    public void setRenderPnlCreateGoodWilling(boolean renderPnlCreateGoodWilling) {
        this.renderPnlCreateGoodWilling = renderPnlCreateGoodWilling;
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
//</editor-fold>

    //recreate to assign goodWillingPaymentList value to goodWillingPaymentDModel
    public void recreateDataModelDetail() {
        goodWillingPaymentDModel = null;
        goodWillingPaymentDModel = new ListDataModel(new ArrayList(goodWillingPaymentList));
    }

//search
    public void search() {
        fmsGoodWillingPaymentList = additionalAmountBeanLocal.searchAllData(goodWillingPayment);
        goodWillingPaymentDModel = new ListDataModel(fmsGoodWillingPaymentList);
    }

    //search all
    public List<FmsGoodWillingPayment> findAll() {
        return additionalAmountBeanLocal.searchAll();
    }

    //select event fof good willing
    public void selectGoodWillingRow(SelectEvent event) {
        fmsGoodWillingPayment = (FmsGoodWillingPayment) event.getObject();
        fmsGoodWillingPayment.setId(fmsGoodWillingPayment.getId());
        fmsGoodWillingPayment = additionalAmountBeanLocal.getByGWId(fmsGoodWillingPayment);
        renderPnlCreateGoodWilling = true;
        renderPnlManPage = false;
        activeIndex = "0";
        createOrSearchBundle = "Search";
        setIcone("ui-icon-search");
        disableBtnCreate = true;
        updateStatus = 1;
        saveorUpdateBundle = "Update";
    }

    //edit render
    public void onRowEdit() {
        renderPnlCreateGoodWilling = true;
        renderPnlManPage = false;
        activeIndex = "0";
        saveorUpdateBundle = "Update";
        createOrSearchBundle = "Search";
        setIcone("ui-icon-search");
        disableBtnCreate = true;
        updateStatus = 1;
        int rowIndex = goodWillingPaymentDModel.getRowIndex();
        fmsGoodWillingPayment = goodWillingPaymentList.get(rowIndex);
    }

    //save
    public String saveGoodWillingPayment() {
        AAA securityService = new AAA();
        IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
        String systemBundle = "et/gov/eep/commonApplications/securityServer";
        String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
        if (security.checkAccess(SessionBean.getUserName(), "saveGoodWillingPayment", dataset)) {
            if (updateStatus == 0) {
                if (additionalAmountBeanLocal.getAddtionalData(fmsGoodWillingPayment) == null) {
                    additionalAmountBeanLocal.create(fmsGoodWillingPayment);
                    JsfUtil.addSuccessMessage("Saved Successfuly!");
                } else {
                    JsfUtil.addFatalMessage("You Can't Register Another Record, Rather you can Update the registered Data Only!");
                }
            } else {
                additionalAmountBeanLocal.edit(fmsGoodWillingPayment);
                JsfUtil.addSuccessMessage("Updated Successfully!");
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
            security.addEventLog(eventEntry, dataset);

        }
        return null;
    }

    //clear
    private void clearPage() {
        goodWillingPaymentDModel = null;
        fmsGoodWillingPayment = null;
        disableBtnCreate = false;
        updateStatus = 0;
        saveorUpdateBundle = "Create";
    }

    //create and search render
    public void createNewGoodWilling() {
        clearPage();
        saveorUpdateBundle = "Create";
        disableBtnCreate = false;
        if (createOrSearchBundle.equals("New")) {
            renderPnlCreateGoodWilling = true;
            renderPnlManPage = false;
            createOrSearchBundle = "Search";
            setIcone("ui-icon-search");
        } else if (createOrSearchBundle.equals("Search")) {
            renderPnlCreateGoodWilling = false;
            renderPnlManPage = true;
            createOrSearchBundle = "New";
            setIcone("ui-icon-plus");
        }
    }
}
