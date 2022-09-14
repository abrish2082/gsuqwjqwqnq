/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.controller.evaluation;

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
import javax.faces.model.SelectItem;
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
import et.gov.eep.hrms.businessLogic.evaluation.EvaluationLevelBeanLocal;
import et.gov.eep.hrms.entity.evaluation.HrEvaluationLevels;

/**
 *
 * @author INSA
 */
@Named(value = "evaluationLevelController")
@ViewScoped
public class EvaluationLevelController implements Serializable {

    @EJB
    EvaluationLevelBeanLocal evaluationLevelBeanLocal;
    @Inject
    HrEvaluationLevels hrEvaluationLevels;
    @Inject
    SessionBean sessionBeanLocal;

    int updateStatus = 0;
    private String saveorUpdate = "Save";
    private String createOrSearchBundle = "New";
    private String icone = "ui-icon-plus";
    private boolean disableBtnCreate;
    private boolean chooseCreatePage = false;
    private boolean renderMainPage = true;
    private boolean btnNewRender = false;

    DataModel<HrEvaluationLevels> levelListDataModel;
    private List<HrEvaluationLevels> selectedLevel;
    List<HrEvaluationLevels> levelList;
    private HrEvaluationLevels selectedRow;
    List<SelectItem> filterByStatus = new ArrayList<>();
    Set<String> checkEvaluation = new HashSet<>();

    public EvaluationLevelController() {
    }

    @PostConstruct
    public void init() {
//        clearPage();
        setFilterByStatus(evaluationLevelBeanLocal.filterByStatus());
    }

    //<editor-fold defaultstate="collapsed" desc="Getter and Setter"> 
    public HrEvaluationLevels getHrEvaluationLevels() {
        if (hrEvaluationLevels == null) {
            hrEvaluationLevels = new HrEvaluationLevels();
        }
        return hrEvaluationLevels;
    }

    public void setHrEvaluationLevels(HrEvaluationLevels hrEvaluationLevels) {
        this.hrEvaluationLevels = hrEvaluationLevels;
    }

    public DataModel<HrEvaluationLevels> getLevelListDataModel() {
        if (levelListDataModel == null) {
            levelListDataModel = new ListDataModel<>();
        }
        return levelListDataModel;
    }

    public void setLevelListDataModel(DataModel<HrEvaluationLevels> levelListDataModel) {
        this.levelListDataModel = levelListDataModel;
    }

    public List<HrEvaluationLevels> getSelectedLevel() {
        if (selectedLevel == null) {
            selectedLevel = new ArrayList<>();
        }
        return selectedLevel;
    }

    public void setSelectedLevel(List<HrEvaluationLevels> selectedLevel) {
        this.selectedLevel = selectedLevel;
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

    public boolean isDisableBtnCreate() {
        return disableBtnCreate;
    }

    public void setDisableBtnCreate(boolean disableBtnCreate) {
        this.disableBtnCreate = disableBtnCreate;
    }

    public boolean isChooseCreatePage() {
        return chooseCreatePage;
    }

    public void setChooseCreatePage(boolean chooseCreatePage) {
        this.chooseCreatePage = chooseCreatePage;
    }

    public boolean isRenderMainPage() {
        return renderMainPage;
    }

    public void setRenderMainPage(boolean renderMainPage) {
        this.renderMainPage = renderMainPage;
    }

    public boolean isBtnNewRender() {
        return btnNewRender;
    }

    public void setBtnNewRender(boolean btnNewRender) {
        this.btnNewRender = btnNewRender;
    }

    public String getSaveorUpdate() {
        return saveorUpdate;
    }

    public void setSaveorUpdate(String saveorUpdate) {
        this.saveorUpdate = saveorUpdate;
    }

    public List<HrEvaluationLevels> getLevelList() {
        return levelList;
    }

    public void setLevelList(List<HrEvaluationLevels> levelList) {
        this.levelList = levelList;
    }

    public HrEvaluationLevels getSelectedRow() {
        return selectedRow;
    }

    public void setSelectedRow(HrEvaluationLevels selectedRow) {
        this.selectedRow = selectedRow;
    }

    public List<SelectItem> getFilterByStatus() {
        return filterByStatus;
    }

    public void setFilterByStatus(List<SelectItem> filterByStatus) {
        this.filterByStatus = filterByStatus;
    }
//</editor-fold>

    public void levelInfo() {
        saveorUpdate = "Save";
        disableBtnCreate = false;
        if (createOrSearchBundle.equals("New")) {
            chooseCreatePage = true;
            renderMainPage = false;
            createOrSearchBundle = "Search";
            setIcone("ui-icon-search");
        } else if (createOrSearchBundle.equals("Search")) {
            chooseCreatePage = false;
            renderMainPage = true;
            createOrSearchBundle = "New";
            setIcone("ui-icon-plus");
        }
    }

    public void newButtonAction() {
        clearPage();
        chooseCreatePage = true;
        chooseCreatePage = false;
        btnNewRender = false;
    }

    public void requestChanged(SelectEvent event) {
        hrEvaluationLevels = (HrEvaluationLevels) event.getObject();
        hrEvaluationLevels.setId(hrEvaluationLevels.getId());
        hrEvaluationLevels = evaluationLevelBeanLocal.getSelectedLevel(hrEvaluationLevels.getId());
        disableBtnCreate = false;
        chooseCreatePage = true;
        renderMainPage = false;
        btnNewRender = true;
        createOrSearchBundle = "Search";
        setIcone("ui-icon-search");
        updateStatus = 1;
        saveorUpdate = "Update";
    }

    // <editor-fold defaultstate="collapsed" desc="Save Update">    
    private void findAll() {
        try {
            List<HrEvaluationLevels> allEvaluationLevels = new ArrayList<>();
            List<HrEvaluationLevels> readAllEvaluationLevel = evaluationLevelBeanLocal.findAll();
            for (HrEvaluationLevels evaLevel : readAllEvaluationLevel) {
                if (Integer.valueOf(evaLevel.getStatus()) == 1) {
                    evaLevel.setStatus(String.valueOf("Active"));
                } else {
                    evaLevel.setStatus(String.valueOf("Inactive"));
                }
                allEvaluationLevels.add(evaLevel);
            }
            levelListDataModel = new ListDataModel(allEvaluationLevels);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void saveEvaluationLevels() {
        try {
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(sessionBeanLocal.getUserName(), "saveEvaluationLevels", dataset)) {
                if (hrEvaluationLevels.getMaximumPoint() > 100) {
                    JsfUtil.addFatalMessage("Maximum value should't be greater than 100");
                } else if (hrEvaluationLevels.getMinimumPoint() >= (hrEvaluationLevels.getMaximumPoint())) {
                    JsfUtil.addFatalMessage("Maximum value should be greater than minimum value");
                } else {
                    if (updateStatus == 0) {
                        try {
                            evaluationLevelBeanLocal.save(hrEvaluationLevels);
                            JsfUtil.addSuccessMessage("Successfully saved");
                            clearPage();
                            findAll();
                        } catch (Exception ex) {
                            JsfUtil.addFatalMessage("Error occured while saving");
                        }
                    } else {
                        try {
                            evaluationLevelBeanLocal.edit(hrEvaluationLevels);
                            JsfUtil.addSuccessMessage("Successfully updated");
                            clearPage();
                            findAll();
                        } catch (Exception ex) {
                            JsfUtil.addFatalMessage("Error occured while updating");
                        }
                    }
                }
//                    
            } else {
                EventEntry eventEntry = new EventEntry();
                eventEntry.setSessionId(sessionBeanLocal.getSessionID());
                eventEntry.setUserId(sessionBeanLocal.getUserId());
                QName qualifiedName = new QName("", "project");
                JAXBElement<String> userName = new JAXBElement<String>(qualifiedName, String.class, null, sessionBeanLocal.getUserName());
                eventEntry.setUserLogin(userName);
                JAXBElement<String> module = new JAXBElement<String>(qualifiedName, String.class, null, "HRMS");
                eventEntry.setModule(module);
                JAXBElement<String> program = new JAXBElement<String>(qualifiedName, String.class, null, "saveEvaluationLevels");
                eventEntry.setProgram(program);
                security.addEventLog(eventEntry, dataset);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveInAdd() {
        if (levelList == null) {
            JsfUtil.addErrorMessage("Please fill the Datatable");
        } else {
            try {
                for (int i = 0; i < levelList.size(); i++) {
                    evaluationLevelBeanLocal.saveOrUpdate(levelList.get(i));
                }
                if (updateStatus == 0) {
                    JsfUtil.addSuccessMessage("Successfully saved");
                    clearPage();
                } else {
                    JsfUtil.addSuccessMessage("Successfully updated");
                    clearPage();
                }
            } catch (Exception e) {
                JsfUtil.addErrorMessage("Unable to Save data");
                updateStatus = 0;
                saveorUpdate = "Save";
            }
        }
    }

    private void clearPage() {
        hrEvaluationLevels = null;
    }

    public void resetEvaluationLevels() {
        hrEvaluationLevels = new HrEvaluationLevels();
        updateStatus = 0;
        saveorUpdate = "Save";
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Search">
    int status = 1;
    int category = 0;

    private void populateTable() {
        try {
            List<HrEvaluationLevels> filteredEvaLevel = new ArrayList<>();
            List<HrEvaluationLevels> readFilteredEvaLevel = evaluationLevelBeanLocal.filteredEvaluationLevel(status);
            for (HrEvaluationLevels evaLevel : readFilteredEvaLevel) {
                if (Integer.valueOf(evaLevel.getStatus()) == 1) {
                    evaLevel.setStatus(String.valueOf("Active"));
                } else {
                    evaLevel.setStatus(String.valueOf("Inactive"));
                }
                filteredEvaLevel.add(evaLevel);
            }
            levelListDataModel = new ListDataModel(filteredEvaLevel);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void vcl_filiterByStatus(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            status = Integer.valueOf(event.getNewValue().toString());
            populateTable();
        }
    }
    // </editor-fold>

    public void populate(SelectEvent events) {
        hrEvaluationLevels = (HrEvaluationLevels) events.getObject();
        checkEvaluation.remove(hrEvaluationLevels);
        saveorUpdate = "Update";
        updateStatus = 1;
    }

    public void addToDatatable() {
        if (!checkEvaluation.contains(hrEvaluationLevels.getEvaluationLevel())) {
            levelList.remove(hrEvaluationLevels);
            levelList.add(hrEvaluationLevels);
            checkEvaluation.add(hrEvaluationLevels.getEvaluationLevel());
            JsfUtil.addSuccessMessage("Added successfully");
            clearPage();
        } else {
            JsfUtil.addErrorMessage("Already added!");
        }
    }

}
