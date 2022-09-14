/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.controller.training;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.ejb.EJB;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
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
import et.gov.eep.hrms.businessLogic.training.HrLuTrainingCategoryBeanLocal;
import et.gov.eep.hrms.businessLogic.training.HrTrainingCoursesBeanLocal;
import et.gov.eep.hrms.entity.evaluation.HrEvaluationCriteria;
import et.gov.eep.hrms.entity.training.HrLuTrainingCategory;
import et.gov.eep.hrms.entity.training.HrTdCourses;

/**
 *
 * @author Mac
 */
@Named(value = "trainingController")
@ViewScoped
public class trainingcontroller implements Serializable {

    @Inject
    HrTdCourses hrTrainingCourses;
    @Inject
    HrLuTrainingCategory hrLuTrainingCategory;
    @Inject
    SessionBean sessionBeanLocal;

    @EJB
    HrTrainingCoursesBeanLocal hrTrainingCoursesBeanLocal;
    @EJB
    HrLuTrainingCategoryBeanLocal hrLuTrainingCategoryBeanLocal;

    private String createOrSearchBundle = "New";
    private String SaveOrUpdateButton = "Save";
    private String icone = "ui-icon-plus";
    private boolean rndNew = false;
    private boolean rndSearch = true;
    private boolean btnNewRender = false;
    private int updateStatus = 0;
    private HrLuTrainingCategory selectedCatagory;

    List<HrLuTrainingCategory> catagoryList;
    DataModel<HrTdCourses> coursesDataModel = new ListDataModel<>();
    private List<HrTdCourses> categoryList;

    public trainingcontroller() {
    }

    //<editor-fold defaultstate="collapsed" desc="Getters and setters">
    public HrTdCourses getHrTrainingCourses() {
        if (hrTrainingCourses == null) {
            hrTrainingCourses = new HrTdCourses();
        }
        return hrTrainingCourses;
    }

    public void setHrTrainingCourses(HrTdCourses hrTrainingCourses) {
        this.hrTrainingCourses = hrTrainingCourses;
    }

    public HrLuTrainingCategory getHrLuTrainingCategory() {
        return hrLuTrainingCategory;
    }

    public void setHrLuTrainingCategory(HrLuTrainingCategory hrLuTrainingCategory) {
        this.hrLuTrainingCategory = hrLuTrainingCategory;
    }

    public List<HrLuTrainingCategory> getCatagoryList() {
        catagoryList = hrTrainingCoursesBeanLocal.findAllCategory();
        return catagoryList;
    }

    public void setCatagoryList(List<HrLuTrainingCategory> catagoryList) {
        this.catagoryList = catagoryList;
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

    public boolean isRndNew() {
        return rndNew;
    }

    public void setRndNew(boolean rndNew) {
        this.rndNew = rndNew;
    }

    public boolean isRndSearch() {
        return rndSearch;
    }

    public void setRndSearch(boolean rndSearch) {
        this.rndSearch = rndSearch;
    }

    public boolean isBtnNewRender() {
        return btnNewRender;
    }

    public void setBtnNewRender(boolean btnNewRender) {
        this.btnNewRender = btnNewRender;
    }

    public DataModel<HrTdCourses> getCoursesDataModel() {
        return coursesDataModel;
    }

    public void setCoursesDataModel(DataModel<HrTdCourses> coursesDataModel) {
        this.coursesDataModel = coursesDataModel;
    }

    public String getSaveOrUpdateButton() {
        return SaveOrUpdateButton;
    }

    public void setSaveOrUpdateButton(String SaveOrUpdateButton) {
        this.SaveOrUpdateButton = SaveOrUpdateButton;
    }

    public HrLuTrainingCategory getSelectedCatagory() {
        return selectedCatagory;
    }

    public void setSelectedCatagory(HrLuTrainingCategory selectedCatagory) {
        this.selectedCatagory = selectedCatagory;
    }

    public SelectItem[] getListOfCategory() {
        return JsfUtil.getSelectItems(hrTrainingCoursesBeanLocal.findAllCategory(), true);
    }
    //</editor-fold>

    public void recreateDataModel(List<HrTdCourses> recreateDataModel) {
        coursesDataModel = null;
        coursesDataModel = new ListDataModel(new ArrayList<>(recreateDataModel));
    }

    public void btnNewOrSearch() {
        SaveOrUpdateButton = "Save";
        switch (createOrSearchBundle) {
            case "New":
                rndNew = true;
                rndSearch = false;
                btnNewRender = false;
                createOrSearchBundle = "Search";
                setIcone("ui-icon-search");
                break;
            case "Search":
                rndNew = false;
                rndSearch = true;
                btnNewRender = false;
                createOrSearchBundle = "New";
                setIcone("ui-icon-plus");
                break;
        }
    }

    public void newButtonAction() {
        clearTrainingCourse();
        rndNew = true;
        rndSearch = false;
        btnNewRender = false;
    }

    public void setCategoryId(ValueChangeEvent event) {
        if (event.getNewValue().toString() != null) {
            hrLuTrainingCategory.setCategoryName(event.getNewValue().toString());
            hrLuTrainingCategory = hrTrainingCoursesBeanLocal.searchByCategoryName(hrLuTrainingCategory);
            if (hrLuTrainingCategory != null) {
                hrTrainingCourses = new HrTdCourses();
                hrTrainingCourses.setCategoryId(hrLuTrainingCategory);
            }
        }
    }

    public ArrayList<HrEvaluationCriteria> findByCriteria(ValueChangeEvent event) {
        if (event.getNewValue().toString() != null) {
            String crtName = event.getNewValue().toString();
            if (crtName.equalsIgnoreCase("Load all")) {
                categoryList = hrTrainingCoursesBeanLocal.findAll();
                coursesDataModel = new ListDataModel(categoryList);
            } else {
                recreateDataModel(hrTrainingCoursesBeanLocal.searchByCatagory(crtName));
            }
        }
        return null;
    }

    public void findByCatagory(ValueChangeEvent event) {
        if (event.getNewValue().toString() != null) {
            hrLuTrainingCategory.setId(Integer.parseInt(event.getNewValue().toString()));
            hrTrainingCourses.setCategoryId(hrLuTrainingCategory);
            coursesDataModel = new ListDataModel(new ArrayList(hrTrainingCoursesBeanLocal.findByCatagory(hrTrainingCourses)));
        }
    }

    public void saveTrainingCourse() {
        try {
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(sessionBeanLocal.getUserName(), "saveTrainingCourse", dataset)) {
                boolean checkDuplication = hrTrainingCoursesBeanLocal.isExist(hrTrainingCourses);
                if (updateStatus == 0 && checkDuplication == true) {
                    JsfUtil.addFatalMessage(hrTrainingCourses.getCourseName() + " is already registered. Try again!");
                } else {
                    try {
                        if (updateStatus == 0) {
                            hrTrainingCoursesBeanLocal.create(hrTrainingCourses);
                            JsfUtil.addSuccessMessage("Successfully saved");
                            clearTrainingCourse();
                        } else {
                            hrTrainingCoursesBeanLocal.update(hrTrainingCourses);
                            JsfUtil.addSuccessMessage("Successfully updated");
                            clearTrainingCourse();
                        }
                        SaveOrUpdateButton = "Save";
                        updateStatus = 0;
                    } catch (Exception e) {
                        e.printStackTrace();
                        JsfUtil.addErrorMessage("Error occure while save update");
                    }
                }
            } else {
                JsfUtil.addFatalMessage("You don't have appropriate permission, please contact the system administrator. ");
                EventEntry eventEntry = new EventEntry();
                eventEntry.setSessionId(sessionBeanLocal.getSessionID());
                eventEntry.setUserId(sessionBeanLocal.getUserId());
                QName qualifiedName = new QName("", "project");
                JAXBElement<String> userName = new JAXBElement<String>(qualifiedName, String.class, null, sessionBeanLocal.getUserName());
                eventEntry.setUserLogin(userName);
                JAXBElement<String> module = new JAXBElement<String>(qualifiedName, String.class, null, "HRMS");
                eventEntry.setModule(module);
                JAXBElement<String> program = new JAXBElement<String>(qualifiedName, String.class, null, "saveTrainingCourse");
                eventEntry.setProgram(program);
                security.addEventLog(eventEntry, dataset);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JsfUtil.addFatalMessage("Error occurs while saving");
        }
    }

    public void clearTrainingCourse() {
        hrLuTrainingCategory = new HrLuTrainingCategory();
        hrTrainingCourses = null;
        SaveOrUpdateButton = "Save";
        updateStatus = 0;
    }

    public void populateCourse(SelectEvent event) {
        hrTrainingCourses = (HrTdCourses) event.getObject();
        hrLuTrainingCategory = hrTrainingCourses.getCategoryId();
        rndNew = true;
        rndSearch = false;
        btnNewRender = true;
        createOrSearchBundle = "Search";
        setIcone("ui-icon-search");
        updateStatus = 1;
        SaveOrUpdateButton = "Update";
    }
    Set<String> checkCatagoryName = new HashSet<>();

    public void saveCourceCatagory() {
        try {
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(sessionBeanLocal.getUserName(), "saveCourceCatagory", dataset)) {
                boolean iscatagoryNameDuplicate = hrLuTrainingCategoryBeanLocal.checkCatagoryName(hrLuTrainingCategory);
                if (iscatagoryNameDuplicate == false) {
                    hrLuTrainingCategory.getCategoryName();
                    hrLuTrainingCategory.getDescription();
                    if (checkCatagoryName.contains(hrLuTrainingCategory.getCategoryName()) && checkCatagoryName.contains(hrLuTrainingCategory.getDescription())) {
                        JsfUtil.addFatalMessage("Catagory name  " + hrLuTrainingCategory.getCategoryName() + "  is duplicated!. Try with another catagory name.");
                    } else {
                        hrLuTrainingCategoryBeanLocal.save(hrLuTrainingCategory);
                        JsfUtil.addSuccessMessage("Successfully Saved");
                        reset();
                        hrLuTrainingCategoryBeanLocal.findAll();
                    }
                } else {
                    JsfUtil.addFatalMessage("Catagory name is duplicated. Try again!!!");
                }
            } else {
                JsfUtil.addFatalMessage("You don't have appropriate permission, please contact the system administrator. ");
                EventEntry eventEntry = new EventEntry();
                eventEntry.setSessionId(sessionBeanLocal.getSessionID());
                eventEntry.setUserId(sessionBeanLocal.getUserId());
                QName qualifiedName = new QName("", "project");
                JAXBElement<String> userName = new JAXBElement<String>(qualifiedName, String.class, null, sessionBeanLocal.getUserName());
                eventEntry.setUserLogin(userName);
                JAXBElement<String> module = new JAXBElement<String>(qualifiedName, String.class, null, "HRMS");

                eventEntry.setModule(module);
                JAXBElement<String> program = new JAXBElement<String>(qualifiedName, String.class, null, "saveCourceCatagory");

                eventEntry.setProgram(program);

                security.addEventLog(eventEntry, dataset);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void reset() {
        hrLuTrainingCategory = new HrLuTrainingCategory();
    }

}
