/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.controller.evaluation;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
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
import et.gov.eep.hrms.businessLogic.evaluation.BscSessionBeanLocal;
import et.gov.eep.hrms.entity.evaluation.HrBscSessions;

/**
 *
 * @author INSA
 */
@Named(value = "bscSessionController")
@ViewScoped
public class BscSessionController implements Serializable {

    @EJB
    BscSessionBeanLocal bscSessionBeanLocal;
    @Inject
    HrBscSessions hrBscSessions;
    @Inject
    SessionBean sessionBeanLocal;

    int updateStatus = 0;
    int bscYear = 0;
    int searchCondition = 0;
    private String saveorUpdateBundle = "Save";
    private String createOrSearchBundle = "New";
    private String icone = "ui-icon-plus";
    private boolean disableBtnCreate;
    private boolean chooseCreatePage = false;
    private boolean chooseMainPage = true;
    private boolean btnNewRender = false;

    List<SelectItem> budgetYears;

    DataModel<HrBscSessions> bscSessionDataModel;

    public BscSessionController() {
    }

    @PostConstruct
    public void init() {
        setBudgetYears(readBudgetYears());
        populateTable();
    }

    //<editor-fold defaultstate="collapsed" desc="Getter and Setter">
    public HrBscSessions getHrBscSessions() {
        if (hrBscSessions == null) {
            hrBscSessions = new HrBscSessions();
        }
        return hrBscSessions;
    }

    public void setHrBscSessions(HrBscSessions hrBscSessions) {
        this.hrBscSessions = hrBscSessions;
    }

    public DataModel<HrBscSessions> getBscSessionDataModel() {
        if (bscSessionDataModel == null) {
            recreateDataModel(bscSessionBeanLocal.findAllBscSession());
        }
        return bscSessionDataModel;
    }

    public void setBscSessionDataModel(DataModel<HrBscSessions> bscSessionDataModel) {
        this.bscSessionDataModel = bscSessionDataModel;
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

    public List<SelectItem> getBudgetYears() {
        return budgetYears;
    }

    public void setBudgetYears(List<SelectItem> budgetYears) {
        this.budgetYears = budgetYears;
    }
    //</editor-fold>

    // <editor-fold defaultstate="collapsed" desc="main">
    public void recreateDataModel(List<HrBscSessions> recreateDataModel) {
        bscSessionDataModel = null;
        bscSessionDataModel = new ListDataModel(new ArrayList<>(recreateDataModel));
    }

    public List<SelectItem> readBudgetYears() {
        List<SelectItem> budgetYear = new ArrayList<>();
        String[] toDay = StringDateManipulation.toDayInEc().split("-");
        int year = Integer.valueOf(toDay[0]) + 1;
        while (year > 2000) {
            budgetYear.add(new SelectItem(String.valueOf(year), String.valueOf(year - 1) + "/" + String.valueOf(year)));
            year--;
        }
        return budgetYear;
    }

    public void vcl_budgetYear(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            int year = Integer.valueOf(event.getNewValue().toString());
            recreateDataModel(bscSessionBeanLocal.findByYear(year));
        }
    }

    private void populateTable() {
        recreateDataModel(bscSessionBeanLocal.filteredBscSession(bscYear, searchCondition));
    }

    public void findAllBscSession() {
        searchCondition = 1;
        populateTable();
    }

    int dateDifference;

    public void dateValidation() {
        String startMonth[] = hrBscSessions.getStartDate().split("/");
        int isSartMonth = Integer.parseInt(startMonth[1]);
        String startYear[] = hrBscSessions.getStartDate().split("/");
        int isStartYear = Integer.parseInt(startYear[2]);
        String startDate[] = hrBscSessions.getStartDate().split("/");
        int isStartDate = Integer.parseInt(startDate[0]);
        String endDay[] = hrBscSessions.getEndDate().split("/");
        int isEndDay = Integer.parseInt(endDay[0]);
        String endMonth[] = hrBscSessions.getEndDate().split("/");
        int isEndMonth = Integer.parseInt(endMonth[1]);
        String endYear[] = hrBscSessions.getEndDate().split("/");
        int isEndYear = Integer.parseInt(endYear[2]);
        dateDifference = ((isEndDay - isStartDate) + ((isEndMonth - isSartMonth) * 30) + ((isEndYear - isStartYear) * 365));
    }

    public void saveBscSession() {
        try {
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(sessionBeanLocal.getUserName(), "saveBscSession", dataset)) {
                int year = Integer.valueOf(hrBscSessions.getBscYear().toString());
                dateValidation();
                if (dateDifference < 0) {
                    JsfUtil.addFatalMessage("End date should be greater than start date. Try again!");
                } else {
                    try {
                        if (updateStatus == 0) {
                            bscSessionBeanLocal.save(hrBscSessions);
                            JsfUtil.addSuccessMessage("Saved Successfully");
                            hrBscSessions = new HrBscSessions();
                            recreateDataModel(bscSessionBeanLocal.findByYear(year));
                        } else {
                            bscSessionBeanLocal.edit(hrBscSessions);
                            JsfUtil.addSuccessMessage("Updated Successfully");
                            hrBscSessions = new HrBscSessions();
                            recreateDataModel(bscSessionBeanLocal.findByYear(year));
                        }
                    } catch (Exception e) {
                        JsfUtil.addFatalMessage("Erroe occure while save update");
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
                JAXBElement<String> program = new JAXBElement<String>(qualifiedName, String.class, null, "saveBscSession");
                eventEntry.setProgram(program);
                security.addEventLog(eventEntry, dataset);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void resetBscSession() {
        hrBscSessions = new HrBscSessions();
        updateStatus = 0;
        saveorUpdateBundle = "Save";
    }
    //</editor-fold>

    public void sessionInfo() {
        saveorUpdateBundle = "Save";
        disableBtnCreate = false;
        if (createOrSearchBundle.equals("New")) {
            chooseCreatePage = true;
            chooseMainPage = false;
            createOrSearchBundle = "Search";
            setIcone("ui-icon-search");
        } else if (createOrSearchBundle.equals("Search")) {
            chooseCreatePage = false;
            chooseMainPage = true;
            createOrSearchBundle = "New";
            setIcone("ui-icon-plus");
        }
    }

    public void newButtonAction() {
        hrBscSessions = new HrBscSessions();
        chooseCreatePage = true;
        chooseMainPage = false;
        btnNewRender = false;
    }

    public void requestIdChanged(SelectEvent event) {
        hrBscSessions = (HrBscSessions) event.getObject();
        hrBscSessions.setId(hrBscSessions.getId());
        hrBscSessions = bscSessionBeanLocal.getSelectedRequest(hrBscSessions.getId());
        disableBtnCreate = false;
        chooseCreatePage = true;
        chooseMainPage = false;
        btnNewRender = true;
        createOrSearchBundle = "Search";
        setIcone("ui-icon-search");
        updateStatus = 1;
        saveorUpdateBundle = "Update";
    }

}
