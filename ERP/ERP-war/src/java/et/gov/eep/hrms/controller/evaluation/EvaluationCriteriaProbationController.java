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
import et.gov.eep.hrms.businessLogic.evaluation.EvaluationProbationBeanLocal;
import et.gov.eep.hrms.entity.evaluation.HrEvalCriteriaProbations;

/**
 *
 * @author Ob
 */
@Named(value = "evaluationCriteriaProbationController")
@ViewScoped
public class EvaluationCriteriaProbationController implements Serializable {

    public EvaluationCriteriaProbationController() {
    }

    @Inject
    HrEvalCriteriaProbations hrEvalCriteriaProbations;
    @Inject
    SessionBean sessionBeanLocal;

    @EJB
    EvaluationProbationBeanLocal evaluationProbationBeanLocal;

    int updateStatus = 0;
    private String saveorUpdateBundle = "Save";
    private String createorSaveBundle = "New";
    private String icone = "ui-icon-plus";
    private boolean renderSearchOrNew = false;
    private String addorUpdate = "Add";
    private boolean disableBtnCreate;
    private List<HrEvalCriteriaProbations> hrEvalCriteriaProbationsList;
    private DataModel<HrEvalCriteriaProbations> hrEvalCriteriaProbationsDataModel;
    Set<String> checkEvaluation = new HashSet<>();
    List<HrEvalCriteriaProbations> criterias;
    private List<HrEvalCriteriaProbations> criteriaList;
    private boolean renderOne = true;
    private boolean renderTwo = false;
    private boolean btnNewRender = false;
    List<SelectItem> filterByStatus = new ArrayList<>();
    private HrEvalCriteriaProbations selectedCriteria;
    private List<HrEvalCriteriaProbations> criteriaNameList;

    @PostConstruct
    public void init() {
        clearPage();
        setFilterByStatus(evaluationProbationBeanLocal.filterByStatus());
    }

    //<editor-fold defaultstate="collapsed" desc="getter setter">
    public HrEvalCriteriaProbations getSelectedCriteria() {
        if (selectedCriteria == null) {
            selectedCriteria = new HrEvalCriteriaProbations();
        }
        return selectedCriteria;
    }

    public void setSelectedCriteria(HrEvalCriteriaProbations selectedCriteria) {
        this.selectedCriteria = selectedCriteria;
    }

    public boolean isRenderOne() {
        return renderOne;
    }

    public void setRenderOne(boolean renderOne) {
        this.renderOne = renderOne;
    }

    public boolean isBtnNewRender() {
        return btnNewRender;
    }

    public void setBtnNewRender(boolean btnNewRender) {
        this.btnNewRender = btnNewRender;
    }

    public boolean isRenderTwo() {
        return renderTwo;
    }

    public void setRenderTwo(boolean renderTwo) {
        this.renderTwo = renderTwo;
    }

    public String getIcone() {
        return icone;
    }

    public void setIcone(String icone) {
        this.icone = icone;
    }

    public String getCreateorSaveBundle() {
        return createorSaveBundle;
    }

    public void setCreateorSaveBundle(String createorSaveBundle) {
        this.createorSaveBundle = createorSaveBundle;
    }

    public boolean isRenderSearchOrNew() {
        return renderSearchOrNew;
    }

    public void setRenderSearchOrNew(boolean renderSearchOrNew) {
        this.renderSearchOrNew = renderSearchOrNew;
    }

    public String getAddorUpdate() {
        return addorUpdate;
    }

    public int getUpdateStatus() {
        return updateStatus;
    }

    public void setUpdateStatus(int updateStatus) {
        this.updateStatus = updateStatus;
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

    public void setAddorUpdate(String addorUpdate) {
        this.addorUpdate = addorUpdate;
    }

    public Set<String> getCheckevaluation() {
        return checkEvaluation;
    }

    public void setCheckevaluation(Set<String> checkevaluation) {
        this.checkEvaluation = checkevaluation;
    }

    public DataModel<HrEvalCriteriaProbations> getHrEvalCriteriaProbationsDataModel() {
        if (hrEvalCriteriaProbationsDataModel == null) {
            hrEvalCriteriaProbationsDataModel = new ListDataModel<>();
        }
        return hrEvalCriteriaProbationsDataModel;
    }

    public void setHrEvalCriteriaProbationsDataModel(DataModel<HrEvalCriteriaProbations> hrEvalCriteriaProbationsDataModel) {
        this.hrEvalCriteriaProbationsDataModel = hrEvalCriteriaProbationsDataModel;
    }

    public List<HrEvalCriteriaProbations> getHrEvalCriteriaProbationsList() {
        if (hrEvalCriteriaProbationsList == null) {
            hrEvalCriteriaProbationsList = new ArrayList<>();
        }
        return hrEvalCriteriaProbationsList;
    }

    public void setHrEvalCriteriaProbationsList(List<HrEvalCriteriaProbations> hrEvalCriteriaProbationsList) {
        this.hrEvalCriteriaProbationsList = hrEvalCriteriaProbationsList;
    }

    public HrEvalCriteriaProbations getHrEvalCriteriaProbations() {
        if (hrEvalCriteriaProbations == null) {
            hrEvalCriteriaProbations = new HrEvalCriteriaProbations();

        }
        return hrEvalCriteriaProbations;

    }

    public void setHrEvalCriteriaProbations(HrEvalCriteriaProbations hrEvalCriteriaProbations) {
        this.hrEvalCriteriaProbations = hrEvalCriteriaProbations;
    }

    public List<HrEvalCriteriaProbations> getCriteriaList() {
        if (criteriaList == null) {
            criteriaList = new ArrayList<>();
        }
        return criteriaList;
    }

    public void setCriteriaList(List<HrEvalCriteriaProbations> criteriaList) {
        this.criteriaList = criteriaList;
    }

    public List<SelectItem> getFilterByStatus() {
        return filterByStatus;
    }

    public void setFilterByStatus(List<SelectItem> filterByStatus) {
        this.filterByStatus = filterByStatus;
    }

    public List<HrEvalCriteriaProbations> getCriteriaNameList() {
        criteriaNameList = evaluationProbationBeanLocal.findAll();
        return criteriaNameList;
    }

    public void setCriteriaNameList(List<HrEvalCriteriaProbations> criteriaNameList) {
        this.criteriaNameList = criteriaNameList;
    }
    //</editor-fold>

    public void saveProbationCriteria() {
        try {
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(sessionBeanLocal.getUserName(), "saveProbationCriteria", dataset)) {
                try {
                    boolean checkDuplication = evaluationProbationBeanLocal.isCriteriaExist(hrEvalCriteriaProbations);
                    if (updateStatus == 0 && checkDuplication == false) {
                        evaluationProbationBeanLocal.saveOrUpdate(hrEvalCriteriaProbations);
                        JsfUtil.addSuccessMessage("Successfully saved");
                        hrEvalCriteriaProbations = new HrEvalCriteriaProbations();
                    } else if (updateStatus == 0 && checkDuplication == true) {
                        JsfUtil.addFatalMessage("Criteria name  " + hrEvalCriteriaProbations.getCriteriaName() + "  is already registered in the database");
                    } else {
                        evaluationProbationBeanLocal.saveOrUpdate(hrEvalCriteriaProbations);
                        JsfUtil.addSuccessMessage("Successfully Updated");
                        hrEvalCriteriaProbations = new HrEvalCriteriaProbations();
                        updateStatus = 0;
                        saveorUpdateBundle = "Save";
                    }
                } catch (Exception e) {
                    JsfUtil.addFatalMessage("Error occure while save update");
                }

            } else {
                EventEntry eventEntry = new EventEntry();
                eventEntry.setSessionId(sessionBeanLocal.getSessionID());
                eventEntry.setUserId(sessionBeanLocal.getUserId());
                QName qualifiedName = new QName("", "project");
                JAXBElement<String> userName = new JAXBElement<String>(qualifiedName, String.class, null, sessionBeanLocal.getUserName());
                eventEntry.setUserLogin(userName);
                JAXBElement<String> module = new JAXBElement<String>(qualifiedName, String.class, null, "HRMS");
                eventEntry.setModule(module);
                JAXBElement<String> program = new JAXBElement<String>(qualifiedName, String.class, null, "saveProbationCriteria");
                eventEntry.setProgram(program);
                security.addEventLog(eventEntry, dataset);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void clearPage() {
        hrEvalCriteriaProbations = null;
    }

    public void populateCriteria(SelectEvent event) {
        hrEvalCriteriaProbations = (HrEvalCriteriaProbations) event.getObject();
        renderSearchOrNew = true;
        updateStatus = 1;
        createorSaveBundle = "Search";
        saveorUpdateBundle = "Update";
        setIcone("ui-icon-search");
        setIcone("ui-icon-search");
        renderOne = false;
        renderTwo = true;
        btnNewRender = true;
    }

    public void resetProbationCriteria() {
        hrEvalCriteriaProbations = new HrEvalCriteriaProbations();
        updateStatus = 0;
        saveorUpdateBundle = "Save";
    }

    int status = 1;
    int category = 0;

    private void populateTable(int status) {
        try {
            List<HrEvalCriteriaProbations> filteredEvalCriteriaProbation = new ArrayList<>();
            List<HrEvalCriteriaProbations> readFilteredEvalCriteriaProbation = evaluationProbationBeanLocal.filteredEvaluationCriteriaProbation(status);
            for (HrEvalCriteriaProbations evalCriteriaProbation : readFilteredEvalCriteriaProbation) {
                if (Integer.valueOf(evalCriteriaProbation.getStatus()) == 1) {
                    evalCriteriaProbation.setStatus(String.valueOf("Active"));
                } else {
                    evalCriteriaProbation.setStatus("Inactive");
                }
                filteredEvalCriteriaProbation.add(evalCriteriaProbation);
            }
            hrEvalCriteriaProbationsDataModel = new ListDataModel(filteredEvalCriteriaProbation);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void vcl_filiterByStatus(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            status = Integer.valueOf(event.getNewValue().toString());
            populateTable(status);
        }
    }

    public void searchByCriteriaName() {
        if (hrEvalCriteriaProbations.getCriteriaName().isEmpty()) {
            hrEvalCriteriaProbationsList = evaluationProbationBeanLocal.findAll();
            hrEvalCriteriaProbationsDataModel = new ListDataModel(hrEvalCriteriaProbationsList);
        } else if (hrEvalCriteriaProbations.getCriteriaName() != null) {
            hrEvalCriteriaProbationsList = evaluationProbationBeanLocal.findByCriteriaName(hrEvalCriteriaProbations);
            hrEvalCriteriaProbationsDataModel = new ListDataModel(hrEvalCriteriaProbationsList);
        }
    }

    public void probationCriteriaInfo() {
        saveorUpdateBundle = "Save";
        disableBtnCreate = false;
        switch (createorSaveBundle) {
            case "New":
                renderOne = false;
                renderTwo = true;
                createorSaveBundle = "Search";
                setIcone("ui-icon-search");
                break;
            case "Search":
                renderOne = true;
                renderTwo = false;
                createorSaveBundle = "New";
                setIcone("ui-icon-plus");
                break;
        }
    }

    public void newButtonAction() {
        hrEvalCriteriaProbations = new HrEvalCriteriaProbations();
        renderTwo = true;
        renderOne = false;
        btnNewRender = false;
    }

    public void recreateCriteriaTbl() {
        hrEvalCriteriaProbationsDataModel = null;
        hrEvalCriteriaProbationsDataModel = new ListDataModel<>(new ArrayList(hrEvalCriteriaProbationsList));
    }

    public void recreateDataModel(List<HrEvalCriteriaProbations> recreateDataModel) {
        hrEvalCriteriaProbationsDataModel = null;
        hrEvalCriteriaProbationsDataModel = new ListDataModel(new ArrayList<>(recreateDataModel));
    }

    public void findByCriteriaName() {
        try {
            if (hrEvalCriteriaProbations.getCriteriaName() != null) {
                criteriaList = evaluationProbationBeanLocal.searchCriteriaName(hrEvalCriteriaProbations);
                hrEvalCriteriaProbationsDataModel = new ListDataModel(criteriaList);
            } else {
                criteriaList = evaluationProbationBeanLocal.findAll();
                hrEvalCriteriaProbationsDataModel = new ListDataModel(criteriaList);
            }
        } catch (Exception ex) {
            JsfUtil.addFatalMessage("No result found");
        }
    }

    public ArrayList<HrEvalCriteriaProbations> findByCriteria(ValueChangeEvent event) {
        if (event.getNewValue().toString() != null) {
            String crtName = event.getNewValue().toString();
            if (crtName.equalsIgnoreCase("Load all")) {
                criteriaList = evaluationProbationBeanLocal.findAll();
                hrEvalCriteriaProbationsDataModel = new ListDataModel(criteriaList);
            } else {
                recreateDataModel(evaluationProbationBeanLocal.findByCriteria(crtName));
            }
        }
        return null;
    }

}
