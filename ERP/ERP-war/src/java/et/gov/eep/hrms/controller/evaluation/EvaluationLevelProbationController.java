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
import org.primefaces.event.SelectEvent;
import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.hrms.businessLogic.evaluation.EvaluationProbationLevelBeanLocal;
import et.gov.eep.hrms.entity.evaluation.HrEvalLevelProbations;

/**
 *
 * @author Ob
 */
@Named(value = "evaluationLevelProbationController")
@ViewScoped
public class EvaluationLevelProbationController implements Serializable {

    public EvaluationLevelProbationController() {
    }

    @Inject
    HrEvalLevelProbations hrEvalLevelProbations;

    @EJB
    EvaluationProbationLevelBeanLocal evaluationProbationLevelBeanLocal;

    int updateStatus = 0;
    private String saveorUpdateBundle = "Save";
    private String createorSaveBundle = "New";
    private String icone = "ui-icon-plus";
    private boolean renderSearchOrNew = false;
    private String addorUpdate = "Add";
    private boolean disableBtnCreate;
    private List<HrEvalLevelProbations> hrEvalLevelProbationsList;
    private DataModel<HrEvalLevelProbations> hrEvalLevelProbationsDataModel;
    Set<String> checkEvaluation = new HashSet<>();
    List<HrEvalLevelProbations> levels;
    private boolean renderOne = true;
    private boolean renderTwo = false;
    private boolean btnNewRender = false;
    List<SelectItem> filterByStatus = new ArrayList<>();
    private HrEvalLevelProbations selectedLevel;

    @PostConstruct
    public void init() {
        clearPage();
        setFilterByStatus(evaluationProbationLevelBeanLocal.filterByStatus());
    }

    public HrEvalLevelProbations getSelectedLevel() {
        if (selectedLevel == null) {
            selectedLevel = new HrEvalLevelProbations();
        }
        return selectedLevel;
    }

    public void setSelectedLevel(HrEvalLevelProbations selectedLevel) {
        this.selectedLevel = selectedLevel;
    }

    public boolean isRenderOne() {
        return renderOne;
    }

    public void setRenderOne(boolean renderOne) {
        this.renderOne = renderOne;
    }

    public boolean isRenderTwo() {
        return renderTwo;
    }

    public void setRenderTwo(boolean renderTwo) {
        this.renderTwo = renderTwo;
    }

    public boolean isBtnNewRender() {
        return btnNewRender;
    }

    public void setBtnNewRender(boolean btnNewRender) {
        this.btnNewRender = btnNewRender;
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

    public DataModel<HrEvalLevelProbations> getHrEvalevelProbationsDataModel() {
        if (hrEvalLevelProbationsDataModel == null) {
            hrEvalLevelProbationsDataModel = new ListDataModel<>();
        }
        return hrEvalLevelProbationsDataModel;
    }

    public void setHrEvalLevelProbationsDataModel(DataModel<HrEvalLevelProbations> hrEvalLevelProbationsDataModel) {
        this.hrEvalLevelProbationsDataModel = hrEvalLevelProbationsDataModel;
    }

    public List<HrEvalLevelProbations> getHrEvalLevelProbationsList() {
        if (hrEvalLevelProbationsList == null) {
            hrEvalLevelProbationsList = new ArrayList<>();
        }
        return hrEvalLevelProbationsList;
    }

    public void setHrEvalLevelProbationsList(List<HrEvalLevelProbations> hrEvalLevelProbationsList) {
        this.hrEvalLevelProbationsList = hrEvalLevelProbationsList;
    }

    public HrEvalLevelProbations getHrEvalLevelProbations() {
        if (hrEvalLevelProbations == null) {
            hrEvalLevelProbations = new HrEvalLevelProbations();

        }
        return hrEvalLevelProbations;

    }

    public void setHrEvalLevelProbations(HrEvalLevelProbations hrEvalLevelProbations) {
        this.hrEvalLevelProbations = hrEvalLevelProbations;
    }

    public List<SelectItem> getFilterByStatus() {
        return filterByStatus;
    }

    public void setFilterByStatus(List<SelectItem> filterByStatus) {
        this.filterByStatus = filterByStatus;
    }

    public void create() {
        evaluationProbationLevelBeanLocal.saveOrUpdate(hrEvalLevelProbations);
        JsfUtil.addSuccessMessage("Successful " + saveorUpdateBundle);
        clearPage();

    }

    public void clearPage() {
        hrEvalLevelProbations = null;
    }

    public void levelList(SelectEvent event) {
        hrEvalLevelProbations = (HrEvalLevelProbations) event.getObject();
        hrEvalLevelProbations.setId(hrEvalLevelProbations.getId());
        hrEvalLevelProbations = evaluationProbationLevelBeanLocal.getSelectedRequest(hrEvalLevelProbations.getId());
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

    public void clear() {
        updateStatus = 0;
        hrEvalLevelProbations = new HrEvalLevelProbations();
    }

    int status = 1;
    int category = 0;

    private void populateTable(int status) {
        try {
            List<HrEvalLevelProbations> filteredEvalLevelProbation = new ArrayList<>();
            List<HrEvalLevelProbations> readFilteredEvalLevelProbation = evaluationProbationLevelBeanLocal.filteredEvaluationLevelProbation(status);
            for (HrEvalLevelProbations evalLevelProbation : readFilteredEvalLevelProbation) {
                if (Integer.valueOf(evalLevelProbation.getStatus()) == 1) {
                    evalLevelProbation.setStatus(String.valueOf("Active"));
                } else {
                    evalLevelProbation.setStatus("Inactive");
                }
                filteredEvalLevelProbation.add(evalLevelProbation);
            }
            hrEvalLevelProbationsDataModel = new ListDataModel(filteredEvalLevelProbation);
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

    public void searchByLevelName() {
        if (hrEvalLevelProbations.getEvaluationLevel().isEmpty()) {
            hrEvalLevelProbationsList = evaluationProbationLevelBeanLocal.findAll();
            hrEvalLevelProbationsDataModel = new ListDataModel(hrEvalLevelProbationsList);
        } else if (hrEvalLevelProbations.getEvaluationLevel() != null) {
            hrEvalLevelProbationsList = evaluationProbationLevelBeanLocal.findByLevelName(hrEvalLevelProbations);
            hrEvalLevelProbationsDataModel = new ListDataModel(hrEvalLevelProbationsList);
        }
    }

    public void probationLevelInfo() {
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
        clearPage();
        renderTwo = true;
        renderOne = false;
        btnNewRender = false;
    }

    public void recreateLevelTbl() {
        hrEvalLevelProbationsDataModel = null;
        hrEvalLevelProbationsDataModel = new ListDataModel<>(new ArrayList(hrEvalLevelProbationsList));
    }
}
