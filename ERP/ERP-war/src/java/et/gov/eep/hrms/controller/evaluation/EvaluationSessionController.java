/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.controller.evaluation;

import java.io.Serializable;
import java.text.SimpleDateFormat;
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
import et.gov.eep.commonApplications.utility.date.StringDateManipulation;
import et.gov.eep.hrms.businessLogic.evaluation.EvaluationSessionBeanLocal;
import et.gov.eep.hrms.entity.evaluation.HrEvaluationSessions;

/**
 *
 * @author INSA
 */
@Named(value = "evaluationSessionController")
@ViewScoped
public class EvaluationSessionController implements Serializable {

    @EJB
    EvaluationSessionBeanLocal evaluationSessionBeanLocal;
    @Inject
    HrEvaluationSessions hrEvaluationSessions;
    @Inject
    SessionBean sessionBeanLocal;

    DataModel<HrEvaluationSessions> sessionListDataModel;

    int updateStatus = 0;
    boolean btnaddvisibility = true;
    private String saveorUpdateBundle = "Save";
    private String createOrSearchBundle = "New";
    private String headerTitle = "Search Request";
    private String icone = "ui-icon-plus";
    private boolean disableBtnCreate;
    private boolean chooseCreatePage = false;
    private boolean chooseMainPage = true;
    private boolean btnNewRender = false;

    int sessionYear = 0;
    int searchCondition = 0;

    DataModel<HrEvaluationSessions> evaluationSessionDataModel;
    List<HrEvaluationSessions> sessions = new ArrayList<>();

    private List<SelectItem> budgetYears;
    private HrEvaluationSessions selectedRow;
    private List<HrEvaluationSessions> selectedSession;
    Set<String> checkEvaluation = new HashSet<>();

    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public EvaluationSessionController() {
    }

    @PostConstruct
    public void init() {
        setBudgetYears(readBudgetYears());
        populateTable();
    }

    // <editor-fold defaultstate="collapsed" desc="Getter and Setter"> 
    public HrEvaluationSessions getHrEvaluationSessions() {
        if (hrEvaluationSessions == null) {
            hrEvaluationSessions = new HrEvaluationSessions();
        }
        return hrEvaluationSessions;
    }

    public void setHrEvaluationSessions(HrEvaluationSessions hrEvaluationSessions) {
        this.hrEvaluationSessions = hrEvaluationSessions;
    }

    public DataModel<HrEvaluationSessions> getSessionListDataModel() {
        if (sessionListDataModel == null) {
            sessionListDataModel = new ListDataModel<>();
        }
        return sessionListDataModel;
    }

    public void setSessionListDataModel(DataModel<HrEvaluationSessions> sessionListDataModel) {
        this.sessionListDataModel = sessionListDataModel;
    }

    public List<HrEvaluationSessions> getSelectedSession() {
        if (selectedSession == null) {
            selectedSession = new ArrayList<>();
        }
        return selectedSession;
    }

    public void setSelectedSession(List<HrEvaluationSessions> selectedSession) {
        this.selectedSession = selectedSession;
    }

    public boolean isBtnaddvisibility() {
        return btnaddvisibility;
    }

    public void setBtnaddvisibility(boolean btnaddvisibility) {
        this.btnaddvisibility = btnaddvisibility;
    }

    public String getCreateOrSearchBundle() {
        return createOrSearchBundle;
    }

    public void setCreateOrSearchBundle(String createOrSearchBundle) {
        this.createOrSearchBundle = createOrSearchBundle;
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

    public boolean isChooseMainPage() {
        return chooseMainPage;
    }

    public void setChooseMainPage(boolean chooseMainPage) {
        this.chooseMainPage = chooseMainPage;
    }

    public boolean isBtnNewRender() {
        return btnNewRender;
    }

    public void setBtnNewRender(boolean btnNewRender) {
        this.btnNewRender = btnNewRender;
    }

    public DataModel<HrEvaluationSessions> getEvaluationSessionDataModel() {
        if (evaluationSessionDataModel == null) {
            evaluationSessionDataModel = new ListDataModel<>();
        }
        return evaluationSessionDataModel;
    }

    public void setEvaluationSessionDataModel(DataModel<HrEvaluationSessions> evaluationSessionDataModel) {
        this.evaluationSessionDataModel = evaluationSessionDataModel;
    }

    public String getSaveorUpdateBundle() {
        return saveorUpdateBundle;
    }

    public void setSaveorUpdateBundle(String saveorUpdateBundle) {
        this.saveorUpdateBundle = saveorUpdateBundle;
    }

    public List<HrEvaluationSessions> getSessions() {
        return sessions;
    }

    public void setSessions(List<HrEvaluationSessions> sessions) {
        this.sessions = sessions;
    }

    public HrEvaluationSessions getSelectedRow() {
        return selectedRow;
    }

    public void setSelectedRow(HrEvaluationSessions selectedRow) {
        this.selectedRow = selectedRow;
    }

    public List<SelectItem> getBudgetYears() {
        return budgetYears;
    }

    public void setBudgetYears(List<SelectItem> budgetYears) {
        this.budgetYears = budgetYears;
    }
    // </editor-fold>

    public void sessionInfo() {
        saveorUpdateBundle = "Save";
        disableBtnCreate = false;
        if (createOrSearchBundle.equals("New")) {
            chooseCreatePage = true;
            chooseMainPage = false;
            createOrSearchBundle = "Search";
            headerTitle = "Search Criteria";
            setIcone("ui-icon-search");
        } else if (createOrSearchBundle.equals("Search")) {
            chooseCreatePage = false;
            chooseMainPage = true;
            createOrSearchBundle = "New";
            headerTitle = "Evaluation Criteria";
            setIcone("ui-icon-plus");
        }
    }

    public void newButtonAction() {
        hrEvaluationSessions = new HrEvaluationSessions();
        chooseCreatePage = true;
        chooseMainPage = false;
        btnNewRender = false;
    }

    public void evalSession_vcl(SelectEvent event) {
        hrEvaluationSessions = (HrEvaluationSessions) event.getObject();
        hrEvaluationSessions.setId(hrEvaluationSessions.getId());
        hrEvaluationSessions = evaluationSessionBeanLocal.readEvaluationSessionDetail(hrEvaluationSessions.getId());
        disableBtnCreate = false;
        chooseCreatePage = true;
        chooseMainPage = false;
        btnNewRender = true;
        createOrSearchBundle = "Search";
        headerTitle = "Evaluation Session";
        setIcone("ui-icon-search");
        updateStatus = 1;
        saveorUpdateBundle = "Update";
    }

    // <editor-fold defaultstate="collapsed" desc="main">
    public void recreateDataModel(List<HrEvaluationSessions> recreateDataModel) {
        evaluationSessionDataModel = null;
        evaluationSessionDataModel = new ListDataModel(new ArrayList<>(recreateDataModel));
    }

    public List<SelectItem> readBudgetYears() {
        List<SelectItem> budgetYear = new ArrayList<>();
        String[] toDay = StringDateManipulation.toDayInEc().split("-");
        int year = Integer.valueOf(toDay[0]) + 1;
        while (year > 2000) {
            budgetYear.add(new SelectItem(String.valueOf(year)));
            year--;
        }
        return budgetYear;
    }

    private void populateTable() {
        recreateDataModel(evaluationSessionBeanLocal.filteredEvaluationSession(sessionYear, searchCondition));
    }

    public void vcl_budgetYear(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            int year = Integer.valueOf(event.getNewValue().toString());
            recreateDataModel(evaluationSessionBeanLocal.findByYear(year));
        }
    }

    public void findAllSession() {
        populateTable();
    }

    int dateDifference;

    public void dateValidation() {
        String startMonth[] = hrEvaluationSessions.getStartDate().split("/");
        int isSartMonth = Integer.parseInt(startMonth[1]);
        String startYear[] = hrEvaluationSessions.getStartDate().split("/");
        int isStartYear = Integer.parseInt(startYear[2]);
        String startDate[] = hrEvaluationSessions.getStartDate().split("/");
        int isStartDate = Integer.parseInt(startDate[0]);
        String endDay[] = hrEvaluationSessions.getEndDate().split("/");
        int isEndDay = Integer.parseInt(endDay[0]);
        String endMonth[] = hrEvaluationSessions.getEndDate().split("/");
        int isEndMonth = Integer.parseInt(endMonth[1]);
        String endYear[] = hrEvaluationSessions.getEndDate().split("/");
        int isEndYear = Integer.parseInt(endYear[2]);
        dateDifference = ((isEndDay - isStartDate) + ((isEndMonth - isSartMonth) * 30) + ((isEndYear - isStartYear) * 365));
    }

    public void saveEvaluationSession() {
        try {
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(sessionBeanLocal.getUserName(), "saveEvaluationSession", dataset)) {
                boolean checkDuplication = evaluationSessionBeanLocal.isExist(hrEvaluationSessions);
                int year = Integer.valueOf(hrEvaluationSessions.getSessionYear().toString());
                dateValidation();
                if (dateDifference < 0) {
                    JsfUtil.addFatalMessage("End date should be greater than start date. Try again!");
                } else {
                    if (updateStatus == 0 && checkDuplication == false) {
                        try {
                            evaluationSessionBeanLocal.save(hrEvaluationSessions);
                            JsfUtil.addSuccessMessage("Saved Successfully");
                            hrEvaluationSessions = new HrEvaluationSessions();
                            recreateDataModel(evaluationSessionBeanLocal.findByYear(year));
                        } catch (Exception ex) {
                            JsfUtil.addFatalMessage("Error occured while saving");
                        }
                    } else if (updateStatus == 0 && checkDuplication == true) {
                        JsfUtil.addFatalMessage("Already registered. Try again!");
                    } else {
                        try {
                            evaluationSessionBeanLocal.edit(hrEvaluationSessions);
                            JsfUtil.addSuccessMessage("Updated Successfully");
                            hrEvaluationSessions = new HrEvaluationSessions();
                            recreateDataModel(evaluationSessionBeanLocal.findByYear(year));
                        } catch (Exception ex) {
                            JsfUtil.addFatalMessage("Error occured while updating");
                        }
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
                JAXBElement<String> program = new JAXBElement<String>(qualifiedName, String.class, null, "saveEvaluationSession");
                eventEntry.setProgram(program);
                security.addEventLog(eventEntry, dataset);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void resetEvaluationSession() {
        hrEvaluationSessions = new HrEvaluationSessions();
        sessionYear = 0;
        searchCondition = 0;
        updateStatus = 0;
        saveorUpdateBundle = "Save";
    }
    // </editor-fold>

}
