/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.controller.medical;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.PostConstruct; 
import javax.ejb.EJB;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import org.primefaces.event.SelectEvent;
import securityBean.SessionBean;
import webService.AAA;
import webService.EventEntry;
import webService.IAdministration;
import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.hrms.businessLogic.medical.TeartmentTypeBeanLocal;
import et.gov.eep.hrms.entity.medical.HrLocalMedTreatmentType;

/**
 *
 * @author Ob
 */
@Named(value = "treatmentTypeController")
@ViewScoped
public class TreatmentTypeController implements Serializable {

    @Inject
    HrLocalMedTreatmentType hrLocalMedTreatmentType;
    @Inject
    HrLocalMedTreatmentType srcMedTreatmentType;
    @Inject
    SessionBean sessionBeanLocal;

    @EJB
    TeartmentTypeBeanLocal teartmentTypeBeanLocal;

    List<HrLocalMedTreatmentType> treatmentTypeList;
    private List<HrLocalMedTreatmentType> treatmentType;
    DataModel<HrLocalMedTreatmentType> treatmentTypeDataModel;

    private int updateStatus = 0;
    private String saveOrUpdate = "Save";
    private String newOrSearch = "New";
    private String icone = "ui-icon-plus";
    private boolean rndNewPage = false;
    private boolean rndSearchPage = true;
    Set<String> checkTreatment = new HashSet<>();

    public TreatmentTypeController() {
    }

    @PostConstruct
    public void init() {
        hrLocalMedTreatmentType.setCoveredBy("Company");
    }

    //<editor-fold defaultstate="collapsed" desc="getter setter">
    public HrLocalMedTreatmentType getHrLocalMedTreatmentType() {
        if (hrLocalMedTreatmentType == null) {
            hrLocalMedTreatmentType = new HrLocalMedTreatmentType();
        }
        return hrLocalMedTreatmentType;
    }

    public void setHrLocalMedTreatmentType(HrLocalMedTreatmentType hrLocalMedTreatmentType) {
        this.hrLocalMedTreatmentType = hrLocalMedTreatmentType;
    }

    public HrLocalMedTreatmentType getSrcMedTreatmentType() {
        if (srcMedTreatmentType == null) {
            srcMedTreatmentType = new HrLocalMedTreatmentType();
        }
        return srcMedTreatmentType;
    }

    public void setSrcMedTreatmentType(HrLocalMedTreatmentType srcMedTreatmentType) {
        this.srcMedTreatmentType = srcMedTreatmentType;
    }

    public List<HrLocalMedTreatmentType> getTreatmentTypeList() {
        treatmentTypeList = teartmentTypeBeanLocal.findAll();
        return treatmentTypeList;
    }

    public void setTreatmentTypeList(List<HrLocalMedTreatmentType> treatmentTypeList) {
        this.treatmentTypeList = treatmentTypeList;
    }

    public DataModel<HrLocalMedTreatmentType> getTreatmentTypeDataModel() {
        if (treatmentTypeDataModel == null) {
            treatmentTypeDataModel = new ListDataModel<>();
        }
        return treatmentTypeDataModel;
    }

    public void setTreatmentTypeDataModel(DataModel<HrLocalMedTreatmentType> treatmentTypeDataModel) {
        this.treatmentTypeDataModel = treatmentTypeDataModel;
    }

    public String getSaveOrUpdate() {
        return saveOrUpdate;
    }

    public void setSaveOrUpdate(String saveOrUpdate) {
        this.saveOrUpdate = saveOrUpdate;
    }

    public String getNewOrSearch() {
        return newOrSearch;
    }

    public void setNewOrSearch(String newOrSearch) {
        this.newOrSearch = newOrSearch;
    }

    public String getIcone() {
        return icone;
    }

    public void setIcone(String icone) {
        this.icone = icone;
    }

    public boolean isRndNewPage() {
        return rndNewPage;
    }

    public void setRndNewPage(boolean rndNewPage) {
        this.rndNewPage = rndNewPage;
    }

    public boolean isRndSearchPage() {
        return rndSearchPage;
    }

    public void setRndSearchPage(boolean rndSearchPage) {
        this.rndSearchPage = rndSearchPage;
    }
    //</editor-fold>

    public void btnNewOrSearch() {
        saveOrUpdate = "Save";
        switch (newOrSearch) {
            case "New":
                rndNewPage = true;
                rndSearchPage = false;
                newOrSearch = "Search";
                setIcone("ui-icon-search");
                break;
            case "Search":
                rndNewPage = false;
                rndSearchPage = true;
                newOrSearch = "New";
                setIcone("ui-icon-plus");
                break;
        }
    }

    public void saveTreatmentType() {
        boolean checkDuplication = teartmentTypeBeanLocal.isTreatmentExist(hrLocalMedTreatmentType);
        try {
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(sessionBeanLocal.getUserName(), "saveTreatmentType", dataset)) {

                if (updateStatus == 0 && checkDuplication == false) {
                    try {
                        teartmentTypeBeanLocal.save(hrLocalMedTreatmentType);
                        hrLocalMedTreatmentType = null;
                        JsfUtil.addSuccessMessage("Teartment type saved sccessfully");
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        JsfUtil.addFatalMessage("Error occurs while saving");
                    }
                } else if (updateStatus == 0 && checkDuplication == true) {
                    JsfUtil.addFatalMessage("Treatment type " + hrLocalMedTreatmentType.getTreatmentType() + " is already registred in the database");
                } else {
                    try {
                        teartmentTypeBeanLocal.edit(hrLocalMedTreatmentType);
                        hrLocalMedTreatmentType = null;
                        JsfUtil.addSuccessMessage("Teartment type updated sccessfully");
                        updateStatus = 0;
                        saveOrUpdate = "Save";
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        JsfUtil.addFatalMessage("Error occurs while updating");
                    }
                }

            } else {
                JsfUtil.addFatalMessage("You don't have appropriate permission, please contact the system administrator.");
                EventEntry eventEntry = new EventEntry();
                eventEntry.setSessionId(sessionBeanLocal.getSessionID());
                eventEntry.setUserId(sessionBeanLocal.getUserId());
                QName qualifiedName = new QName("", "project");
                JAXBElement<String> test = new JAXBElement<String>(qualifiedName, String.class, null, sessionBeanLocal.getUserName());
                eventEntry.setUserLogin(test);
                security.addEventLog(eventEntry, dataset);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JsfUtil.addFatalMessage("Error occurs while updating");
        }
    }

    public void resetTreatmentType() {
        hrLocalMedTreatmentType = null;
        updateStatus = 0;
        saveOrUpdate = "Save";
    }

    public void recreateDataModel(List<HrLocalMedTreatmentType> recreateDataModel) {
        treatmentTypeDataModel = null;
        treatmentTypeDataModel = new ListDataModel(new ArrayList<>(recreateDataModel));
    }

    public ArrayList<HrLocalMedTreatmentType> findTreatmentType(ValueChangeEvent event) {
        if (event.getNewValue().toString() != null) {
            String teratment = event.getNewValue().toString();
            if (teratment.equalsIgnoreCase("Load all")) {
                treatmentType = teartmentTypeBeanLocal.findAll();
                treatmentTypeDataModel = new ListDataModel(treatmentType);
            } else {
                recreateDataModel(teartmentTypeBeanLocal.findByTreatmentType(teratment));
            }
        }
        return null;
    }

    public void populateTreatmnet(SelectEvent event) {
        srcMedTreatmentType = null;
        hrLocalMedTreatmentType = (HrLocalMedTreatmentType) event.getObject();
        rndNewPage = true;
        rndSearchPage = false;
        newOrSearch = "Search";
        setIcone("ui-icon-search");
        updateStatus = 1;
        saveOrUpdate = "Update";
    }

}
