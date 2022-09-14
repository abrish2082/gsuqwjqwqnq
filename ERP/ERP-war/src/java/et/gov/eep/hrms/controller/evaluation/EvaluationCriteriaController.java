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
import et.gov.eep.hrms.businessLogic.evaluation.EvaluationBeanLocal;
import et.gov.eep.hrms.entity.evaluation.HrEvaluationCriteria;
import et.gov.eep.hrms.entity.lookup.HrLuEvaluationCategory;

/**
 *
 * @author INSA
 */
@Named(value = "evaluationCriteriaController")
@ViewScoped
public class EvaluationCriteriaController implements Serializable {

    @EJB
    EvaluationBeanLocal evaluationBeanLocal;

    @Inject
    HrEvaluationCriteria hrEvaluationCriteria;
    @Inject
    HrEvaluationCriteria srcEvaluationCriteria;
    @Inject
    HrLuEvaluationCategory hrLuEvaluationCategory;
    @Inject
    SessionBean sessionBeanLocal;

    int updateStatus = 0;
    int addStatus = 0;
    private String saveorUpdateBundle = "Save";
    private String createOrSearchBundle = "New";
    private String headerTitle = "Search Request";
    private String icone = "ui-icon-plus";
    private boolean disableBtnCreate;
    private boolean chooseCreatePage = false;
    private boolean renderMainPage = true;
    private boolean btnNewRender = false;
    private String addorUpdate = "Add";
    private HrEvaluationCriteria selectedCriteria;
    List<HrEvaluationCriteria> criterias = new ArrayList<>();
    DataModel<HrEvaluationCriteria> criteriaListDataModel;
    private List<HrEvaluationCriteria> selectedcriteria;
    private HrEvaluationCriteria selectedRow;
    Set<String> checkEvaluation = new HashSet<>();
    List<SelectItem> filterByStatus = new ArrayList<>();
    List<HrLuEvaluationCategory> filterByCategories = new ArrayList<>();
    private List<HrEvaluationCriteria> criteriaList;
    List<HrEvaluationCriteria> criteriaNameList;

    public EvaluationCriteriaController() {
    }

    @PostConstruct
    public void init() {
        setFilterByStatus(evaluationBeanLocal.filterByStatus());
        setFilterByCategories(evaluationBeanLocal.findAllEvaluationCategory());
        hrEvaluationCriteria.setStatus("1");
    }

    // <editor-fold defaultstate="collapsed" desc="Getter and Setter"> 
    public HrEvaluationCriteria getHrEvaluationCriteria() {
        if (hrEvaluationCriteria == null) {
            hrEvaluationCriteria = new HrEvaluationCriteria();
        }
        return hrEvaluationCriteria;
    }

    public void setHrEvaluationCriteria(HrEvaluationCriteria hrEvaluationCriteria) {
        this.hrEvaluationCriteria = hrEvaluationCriteria;
    }

    public HrEvaluationCriteria getSrcEvaluationCriteria() {
        if (srcEvaluationCriteria == null) {
            srcEvaluationCriteria = new HrEvaluationCriteria();
        }
        return srcEvaluationCriteria;
    }

    public void setSrcEvaluationCriteria(HrEvaluationCriteria srcEvaluationCriteria) {
        this.srcEvaluationCriteria = srcEvaluationCriteria;
    }

    public HrLuEvaluationCategory getHrLuEvaluationCategory() {
        if (hrLuEvaluationCategory == null) {
            hrLuEvaluationCategory = new HrLuEvaluationCategory();
        }
        return hrLuEvaluationCategory;
    }

    public void setHrLuEvaluationCategory(HrLuEvaluationCategory hrLuEvaluationCategory) {
        this.hrLuEvaluationCategory = hrLuEvaluationCategory;
    }

    public DataModel<HrEvaluationCriteria> getCriteriaListDataModel() {
        if (criteriaListDataModel == null) {
            criteriaListDataModel = new ListDataModel<>();
        }
        return criteriaListDataModel;
    }

    public void setCriteriaListDataModel(DataModel<HrEvaluationCriteria> criteriaListDataModel) {
        this.criteriaListDataModel = criteriaListDataModel;
    }

    public List<HrEvaluationCriteria> getSelectedcriteria() {
        if (selectedcriteria == null) {
            selectedcriteria = new ArrayList<>();
        }
        return selectedcriteria;
    }

    public void setSelectedcriteria(List<HrEvaluationCriteria> selectedcriteria) {
        this.selectedcriteria = selectedcriteria;
    }

    public String getCreateOrSearchBundle() {
        return createOrSearchBundle;
    }

    public void setCreateOrSearchBundle(String createOrSearchBundle) {
        this.createOrSearchBundle = createOrSearchBundle;
    }

    public Set<String> getCheckEvaluation() {
        return checkEvaluation;
    }

    public void setCheckEvaluation(Set<String> checkEvaluation) {
        this.checkEvaluation = checkEvaluation;
    }

    public String getHeaderTitle() {
        return headerTitle;
    }

    public void setHeaderTitle(String headerTitle) {
        this.headerTitle = headerTitle;
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

    public String getSaveorUpdateBundle() {
        return saveorUpdateBundle;
    }

    public void setSaveorUpdateBundle(String saveorUpdateBundle) {
        this.saveorUpdateBundle = saveorUpdateBundle;
    }

    public String getAddorUpdate() {
        return addorUpdate;
    }

    public void setAddorUpdate(String addorUpdate) {
        this.addorUpdate = addorUpdate;
    }

    public HrEvaluationCriteria getSelectedCriteria() {
        return selectedCriteria;
    }

    public void setSelectedCriteria(HrEvaluationCriteria selectedCriteria) {
        this.selectedCriteria = selectedCriteria;
    }

    public List<HrEvaluationCriteria> getCriterias() {
        return criterias;
    }

    public void setCriterias(List<HrEvaluationCriteria> criterias) {
        this.criterias = criterias;
    }

    public HrEvaluationCriteria getSelectedRow() {
        return selectedRow;
    }

    public void setSelectedRow(HrEvaluationCriteria selectedRow) {
        this.selectedRow = selectedRow;
    }

    public List<SelectItem> getFilterByStatus() {
        return filterByStatus;
    }

    public void setFilterByStatus(List<SelectItem> filterByStatus) {
        this.filterByStatus = filterByStatus;
    }

    public List<HrLuEvaluationCategory> getFilterByCategories() {
        return filterByCategories;
    }

    public void setFilterByCategories(List<HrLuEvaluationCategory> filterByCategories) {
        this.filterByCategories = filterByCategories;
    }

    public List<HrEvaluationCriteria> getCriteriaNameList() {
        criteriaNameList = evaluationBeanLocal.findAllCriteria();
        return criteriaNameList;
    }

    public void setCriteriaNameList(List<HrEvaluationCriteria> criteriaNameList) {
        this.criteriaNameList = criteriaNameList;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Save Update">
    int sumofWeight = 0;

    public void checkWeightSum() {
        for (int i = 0; i < criterias.size(); i++) {
            sumofWeight += criterias.get(i).getWeight();
        }
    }

    public void saveEvaluationCriteria() {
        try {
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(sessionBeanLocal.getUserName(), "saveEvaluationCriteria", dataset)) {
                checkWeightSum();
                if (sumofWeight > 100) {
                    JsfUtil.addFatalMessage("Weight sum should't be greater than 100%. Try again");
                } else if ((!(criterias.size() > 0))) {
                    JsfUtil.addFatalMessage("Data table shoud be filled");
                } else {
                    try {
                        for (int i = 0; i < criterias.size(); i++) {
                            evaluationBeanLocal.saveUpdate(criterias.get(i));
                        }
                        if (updateStatus == 0) {
                            criterias = null;
                            JsfUtil.addSuccessMessage("Successfully saved");
                        } else {
                            criterias = null;
                            JsfUtil.addSuccessMessage("Successfully updated");
                        }
                    } catch (Exception e) {
                        JsfUtil.addFatalMessage("Error occured while save update");
                    }
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
                JAXBElement<String> program = new JAXBElement<String>(qualifiedName, String.class, null, "saveEvaluationCriteria");
                eventEntry.setProgram(program);
                security.addEventLog(eventEntry, dataset);
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    private void clearPage() {
        hrEvaluationCriteria = null;
        hrLuEvaluationCategory = null;
    }

    public void clearEvaluationCriteria() {
        hrEvaluationCriteria = new HrEvaluationCriteria();
        hrLuEvaluationCategory = new HrLuEvaluationCategory();
        criterias = null;
        updateStatus = 0;
        saveorUpdateBundle = "Save";
        addStatus = 0;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Search">
    int status = 1;
    int category = 0;

    private void populateTable() {
        try {
            List<HrEvaluationCriteria> filteredEvaCriteria = new ArrayList<>();
            List<HrEvaluationCriteria> readFilteredEvaCriteria = evaluationBeanLocal.filteredEvaluationCriteria(status, category);
            for (HrEvaluationCriteria evaCriteria : readFilteredEvaCriteria) {
                if (Integer.valueOf(evaCriteria.getStatus()) == 1) {
                    evaCriteria.setStatus(String.valueOf("Active"));
                } else {
                    evaCriteria.setStatus(String.valueOf("Inactive"));
                }
                filteredEvaCriteria.add(evaCriteria);
            }
            criteriaListDataModel = new ListDataModel(filteredEvaCriteria);
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

    public void vcl_filiterByCategory(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            category = Integer.valueOf(event.getNewValue().toString());
            populateTable();
        }
    }
// </editor-fold>

    public void add() {
        boolean checkDuplication = evaluationBeanLocal.isCriteriaExist(hrEvaluationCriteria);
        if (addStatus == 0 && checkDuplication == false) {
            if (checkEvaluation.contains(hrEvaluationCriteria.getCriteriaName())) {
                JsfUtil.addFatalMessage("Criteria name  " + hrEvaluationCriteria.getCriteriaName() + "  is duplicated!. Try again.");
            } else {
                criterias.add(hrEvaluationCriteria);
                checkEvaluation.add(hrEvaluationCriteria.getCriteriaName());
                clearPage();
            }
        } else if (addStatus == 0 && checkDuplication == true) {
            JsfUtil.addFatalMessage("Criteria name  " + hrEvaluationCriteria.getCriteriaName() + "  is already registered in the database");
        } else {
            if (checkEvaluation.contains(hrEvaluationCriteria.getCriteriaName())) {
                JsfUtil.addFatalMessage("Criteria name  " + hrEvaluationCriteria.getCriteriaName() + "  is duplicated!. Try again.");
            } else {
                criterias.add(hrEvaluationCriteria);
                checkEvaluation.add(hrEvaluationCriteria.getCriteriaName());
                clearPage();
            }
        }
    }

    public void addCriteria() {
        if (hrEvaluationCriteria.getCategoryId().getCategoryName() == null || hrEvaluationCriteria.getWeight() == null) {
            JsfUtil.addSuccessMessage("Fields Should be Filled!");
        } else {
            if (evaluationBeanLocal.checkCriteriaName(hrEvaluationCriteria) == null && addStatus == 0) {
                hrEvaluationCriteria.getCategoryId().getCategoryName();
                hrEvaluationCriteria.getCriteriaName();
                hrEvaluationCriteria.getWeight();
                hrEvaluationCriteria.getStatus();
                if (checkEvaluation.contains(hrEvaluationCriteria.getCriteriaName())) {
                    JsfUtil.addFatalMessage("Criteria name  " + hrEvaluationCriteria.getCriteriaName() + "  is duplicated!. Try again.");
                } else {
                    criterias.add(hrEvaluationCriteria);
                    checkEvaluation.add(hrEvaluationCriteria.getCriteriaName());
                    clearPage();
                }
            } else {
                JsfUtil.addFatalMessage("Criteria name  " + hrEvaluationCriteria.getCriteriaName() + "  is Already registred in the database");
            }
        }
    }

    public void evaluationCriteriaInfo() {
        saveorUpdateBundle = "Save";
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
        hrEvaluationCriteria = new HrEvaluationCriteria();
        chooseCreatePage = true;
        renderMainPage = false;
        btnNewRender = false;
    }

    public void recreateDataModel(List<HrEvaluationCriteria> recreateDataModel) {
        criteriaListDataModel = null;
        criteriaListDataModel = new ListDataModel(new ArrayList<>(recreateDataModel));
    }

    public void criteriaName_vlc(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            String crtName = String.valueOf(event.getNewValue());
            recreateDataModel(evaluationBeanLocal.findByName(crtName));
        }
    }

    public void evalCriteria_vcl(SelectEvent event) {
        hrEvaluationCriteria = (HrEvaluationCriteria) event.getObject();
        hrEvaluationCriteria.setId(hrEvaluationCriteria.getId());
        hrEvaluationCriteria = evaluationBeanLocal.readEvaluationCriteriaDetail(hrEvaluationCriteria.getId());
        disableBtnCreate = false;
        chooseCreatePage = true;
        renderMainPage = false;
        btnNewRender = true;
        createOrSearchBundle = "Search";
        headerTitle = "Evaluation Criteria";
        setIcone("ui-icon-search");
        addStatus = 1;
        updateStatus = 1;
        saveorUpdateBundle = "Update";
    }

    public void populate(SelectEvent events) {
        hrEvaluationCriteria = null;
        hrEvaluationCriteria = (HrEvaluationCriteria) events.getObject();
        saveorUpdateBundle = "Update";
        addorUpdate = "Update";
        updateStatus = 1;
    }

    public SelectItem[] getListOfCategory() {
        return JsfUtil.getSelectItems(evaluationBeanLocal.findAllEvaluationCategory(), true);
    }

    public void getCategoryName(ValueChangeEvent event) {
        if (!event.getNewValue().toString().isEmpty()) {
            hrLuEvaluationCategory.setCategoryName(event.getNewValue().toString());
            hrLuEvaluationCategory = evaluationBeanLocal.findCategoryName(hrLuEvaluationCategory);
            hrEvaluationCriteria.setCategoryId(hrLuEvaluationCategory);
        }
    }

    public void findByCriteriaName() {
        try {
            if (hrEvaluationCriteria.getCriteriaName() != null) {
                criteriaList = evaluationBeanLocal.searchCriteriaName(srcEvaluationCriteria);
                criteriaListDataModel = new ListDataModel(criteriaList);
            } else {
                criteriaList = evaluationBeanLocal.findAllCriteria();
                criteriaListDataModel = new ListDataModel(criteriaList);
            }
        } catch (Exception ex) {
            JsfUtil.addFatalMessage("No result found");
        }
    }

    public ArrayList<HrEvaluationCriteria> findByCriteria(ValueChangeEvent event) {
        if (event.getNewValue().toString() != null) {
            String crtName = event.getNewValue().toString();
            if (crtName.equalsIgnoreCase("Load all")) {
                criteriaList = evaluationBeanLocal.findAllCriteria();
                criteriaListDataModel = new ListDataModel(criteriaList);
            } else {
                recreateDataModel(evaluationBeanLocal.findByName(crtName));
            }
        }
        return null;
    }

    public SelectItem[] getcategory() {
        return JsfUtil.getSelectItems(category(), true);
    }

    public ArrayList<String> category() {
        ArrayList<String> categoryList = new ArrayList<>();
        categoryList.add("Talent");
        categoryList.add("Behavior");
        return categoryList;
    }

}
