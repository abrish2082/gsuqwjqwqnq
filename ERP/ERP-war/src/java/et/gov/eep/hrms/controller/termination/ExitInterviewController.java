/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.controller.termination;

import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
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
import et.gov.eep.commonApplications.utility.StringDateManipulation;
import et.gov.eep.hrms.businessLogic.employee.HrEmployeeBeanLocal;
import et.gov.eep.hrms.businessLogic.termination.ClearanceBeanLocal;
import et.gov.eep.hrms.businessLogic.termination.ExitInterviewBeanLocal;
import et.gov.eep.hrms.businessLogic.termination.TerminationRequestBeanLocal;
import et.gov.eep.hrms.entity.employee.HrEmployees;
import et.gov.eep.hrms.entity.lookup.HrLuGrades;
import et.gov.eep.hrms.entity.termination.HrClearance;
import et.gov.eep.hrms.entity.termination.HrExitInterview;
import et.gov.eep.hrms.entity.termination.HrTerminationRequests;

/**
 *
 * @author Ob
 */
@Named(value = "exitInterviewController")
@ViewScoped
public class ExitInterviewController implements Serializable {

    @Inject
    HrExitInterview hrExitInterview;
    @Inject
    HrClearance hrClearance;
    @Inject
    HrEmployees hrEmployees;
    @Inject
    HrEmployees preparerEmp;
    @Inject
    HrEmployees srcEmployees;
    @Inject
    HrTerminationRequests hrTerminationRequest;
    @Inject
    HrLuGrades hrLuGrades;
    @Inject
    SessionBean sessionBeanLocal;

    @EJB
    ExitInterviewBeanLocal exitInterviewBeanLocal;
    @EJB
    HrEmployeeBeanLocal employeeBeanLocal;
    @EJB
    ClearanceBeanLocal clearanceBeanLocal;
    @EJB
    TerminationRequestBeanLocal terminationBeanLocal;

    private List<HrExitInterview> selectedList;
    private List<HrTerminationRequests> terminationRequestList;
    DataModel<HrExitInterview> exitInterviewDataModel;

    private boolean chooseCreatePage = false;
    private boolean chooseMainPage = true;
    private boolean btnNewRender = false;
    int updateStatus = 0;
    String experience;
    String preparedDate;
    private String saveOrUpdate = "Save";
    String slected = "Select One";
    private boolean disableReason = true;
    private String createOrSearchBundle = "New";
    private String icone = "ui-icon-plus";

    public ExitInterviewController() {
    }

    @PostConstruct
    public void init() {
        setPreparedDate(StringDateManipulation.toDayInEc());
    }

    //<editor-fold defaultstate="collapsed" desc="getter setter">
    public HrExitInterview getHrExitInterview() {
        if (hrExitInterview == null) {
            hrExitInterview = new HrExitInterview();
        }
        return hrExitInterview;
    }

    public void setHrExitInterview(HrExitInterview hrExitInterview) {
        this.hrExitInterview = hrExitInterview;
    }

    public HrClearance getHrClearance() {
        if (hrClearance == null) {
            hrClearance = new HrClearance();
        }
        return hrClearance;
    }

    public void setHrClearance(HrClearance hrClearance) {
        this.hrClearance = hrClearance;
    }

    public HrEmployees getHrEmployees() {
        if (hrEmployees == null) {
            hrEmployees = new HrEmployees();
        }
        return hrEmployees;
    }

    public void setHrEmployees(HrEmployees hrEmployees) {
        this.hrEmployees = hrEmployees;
    }

    public HrEmployees getPreparerEmp() {
        if (preparerEmp == null) {
            preparerEmp = new HrEmployees();
        }
        return preparerEmp;
    }

    public void setPreparerEmp(HrEmployees preparerEmp) {
        this.preparerEmp = preparerEmp;
    }

    public HrEmployees getSrcEmployees() {
        if (srcEmployees == null) {
            srcEmployees = new HrEmployees();
        }
        return srcEmployees;
    }

    public void setSrcEmployees(HrEmployees srcEmployees) {
        this.srcEmployees = srcEmployees;
    }

    public HrTerminationRequests getHrTerminationRequest() {
        if (hrTerminationRequest == null) {
            hrTerminationRequest = new HrTerminationRequests();
        }
        return hrTerminationRequest;
    }

    public void setHrTerminationRequest(HrTerminationRequests hrTerminationRequest) {
        this.hrTerminationRequest = hrTerminationRequest;
    }

    public HrLuGrades getHrLuGrades() {
        if (hrLuGrades == null) {
            hrLuGrades = new HrLuGrades();
        }
        return hrLuGrades;
    }

    public void setHrLuGrades(HrLuGrades hrLuGrades) {
        this.hrLuGrades = hrLuGrades;
    }

    public List<HrTerminationRequests> getTerminationRequestList() {
        terminationRequestList = exitInterviewBeanLocal.findApprovedTerminationByTerminationType();
        return terminationRequestList;
    }

    public void setTerminationRequestList(List<HrTerminationRequests> terminationRequestList) {
        this.terminationRequestList = terminationRequestList;
    }

    public DataModel<HrExitInterview> getExitInterviewDataModel() {
        return exitInterviewDataModel;
    }

    public void setExitInterviewDataModel(DataModel<HrExitInterview> exitInterviewDataModel) {
        this.exitInterviewDataModel = exitInterviewDataModel;
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

    public String getPreparedDate() {
        return preparedDate;
    }

    public void setPreparedDate(String preparedDate) {
        this.preparedDate = preparedDate;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getSaveOrUpdate() {
        return saveOrUpdate;
    }

    public void setSaveOrUpdate(String saveOrUpdate) {
        this.saveOrUpdate = saveOrUpdate;
    }

    public boolean isDisableReason() {
        return disableReason;
    }

    public void setDisableReason(boolean disableReason) {
        this.disableReason = disableReason;
    }

    public String getIcone() {
        return icone;
    }

    public void setIcone(String icone) {
        this.icone = icone;
    }

    //</editor-fold>
    public SelectItem[] getListOfApprovedTermination() {
        return JsfUtil.getSelectItems(clearanceBeanLocal.findApprovedTermination(), true);
    }

    public void newOrSearchPage() {
        switch (createOrSearchBundle) {
            case "New":
                createOrSearchBundle = "Search";
                chooseCreatePage = true;
                chooseMainPage = false;
                btnNewRender = false;
                setIcone("ui-icon-search");
                saveOrUpdate = "Save";
                break;
            case "Search":
                createOrSearchBundle = "New";
                chooseCreatePage = false;
                chooseMainPage = true;
                btnNewRender = false;
                setIcone("ui-icon-plus");
                break;
        }
    }

    public void newButtonAction() {
        clearPage();
        chooseCreatePage = true;
        chooseMainPage = false;
        btnNewRender = false;
    }

    public void leaveReason_vlc(ValueChangeEvent event) {
        if (!event.getNewValue().toString().isEmpty()) {
            slected = event.getNewValue().toString();
            if (slected.equalsIgnoreCase("8")) {
                disableReason = false;
            } else {
                disableReason = true;
            }
        }
    }

    public SelectItem[] getListOfClearEmployee() {
        return JsfUtil.getSelectItems(exitInterviewBeanLocal.findAll(), true);
    }

    public void serviceYear() {
        String hireDateInDay[] = hrTerminationRequest.getEmpId().getHireDate().split("/");
        int inDay = Integer.parseInt(hireDateInDay[0]);
        String hireDateInMonth[] = hrTerminationRequest.getEmpId().getHireDate().split("/");
        int inMonth = Integer.parseInt(hireDateInMonth[1]);
        String hireDateInyear[] = hrTerminationRequest.getEmpId().getHireDate().split("/");
        int inYear = Integer.parseInt(hireDateInyear[2]);
        String toDayDay[] = et.gov.eep.commonApplications.utility.date.StringDateManipulation.toDayInEc().split("-");
        int day = Integer.parseInt(toDayDay[2]);
        String toDayMonth[] = et.gov.eep.commonApplications.utility.date.StringDateManipulation.toDayInEc().split("-");
        int month = Integer.parseInt(toDayMonth[1]);
        String toDayYear[] = et.gov.eep.commonApplications.utility.date.StringDateManipulation.toDayInEc().split("-");
        int year = Integer.parseInt(toDayYear[0]);
        int experienceDay = ((day - inDay) + ((month - inMonth) * 30) + ((year - inYear) * 365));
        int experienceInYear = experienceDay / 365;
        int experienceInMonth = ((experienceDay % 365) / 30);
        experience = (experienceInYear + " " + "Year and " + experienceInMonth + " Month");
    }

    public void serviceYearForPopulate() {
        String hireDateInDay[] = hrExitInterview.getTerminationRequestId().getEmpId().getHireDate().split("/");
        int inDay = Integer.parseInt(hireDateInDay[0]);
        String hireDateInMonth[] = hrExitInterview.getTerminationRequestId().getEmpId().getHireDate().split("/");
        int inMonth = Integer.parseInt(hireDateInMonth[1]);
        String hireDateInyear[] = hrExitInterview.getTerminationRequestId().getEmpId().getHireDate().split("/");
        int inYear = Integer.parseInt(hireDateInyear[2]);
        String toDayDay[] = et.gov.eep.commonApplications.utility.date.StringDateManipulation.toDayInEc().split("-");
        int day = Integer.parseInt(toDayDay[2]);
        String toDayMonth[] = et.gov.eep.commonApplications.utility.date.StringDateManipulation.toDayInEc().split("-");
        int month = Integer.parseInt(toDayMonth[1]);
        String toDayYear[] = et.gov.eep.commonApplications.utility.date.StringDateManipulation.toDayInEc().split("-");
        int year = Integer.parseInt(toDayYear[0]);
        int experienceDay = ((day - inDay) + ((month - inMonth) * 30) + ((year - inYear) * 365));
        int experienceInYear = experienceDay / 365;
        int experienceInMonth = ((experienceDay % 365) / 30);
        experience = (experienceInYear + " " + "Year and " + experienceInMonth + " Month");
    }

    public void populateTerminatedEmp(ValueChangeEvent event) {
        try {
            if (null != event.getNewValue()) {
                String selectedReqId[] = event.getNewValue().toString().split(" -- ");
                int id = Integer.parseInt(selectedReqId[0]);
                hrTerminationRequest.setId(id);
                hrTerminationRequest = terminationBeanLocal.getSelectedRequest(id);
                hrTerminationRequest.setId(hrTerminationRequest.getId());
                hrTerminationRequest = terminationBeanLocal.getSelectedRequest(hrTerminationRequest.getId());
                hrEmployees = new HrEmployees();
                hrEmployees = hrTerminationRequest.getEmpId();
                hrLuGrades = hrEmployees.getGradeId().getGradeId();
                hrExitInterview.setTerminationRequestId(hrTerminationRequest);
                serviceYear();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void saveExitInterview() {
        try {
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(sessionBeanLocal.getUserName(), "saveExitInterview", dataset)) {
                try {
                    boolean checkDuplication = exitInterviewBeanLocal.checkDuplicate(hrExitInterview);
                    hrExitInterview.setPreparedOn(preparedDate);
                    if (checkDuplication == true && updateStatus == 0) {
                        JsfUtil.addFatalMessage("Duplicate Entry. Try again!");
                    } else {
                        exitInterviewBeanLocal.saveOrUpdate(hrExitInterview);
                        if (updateStatus == 0) {
                            JsfUtil.addSuccessMessage("Successfully saved");
                        } else {
                            JsfUtil.addSuccessMessage("Successfully Updated");
                        }
                        clearPage();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
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
                JAXBElement<String> program = new JAXBElement<String>(qualifiedName, String.class, null, "saveExitInterview");
                eventEntry.setProgram(program);
                security.addEventLog(eventEntry, dataset);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void clearPage() {
        hrExitInterview = new HrExitInterview();
        hrEmployees = new HrEmployees();
        hrLuGrades = new HrLuGrades();
        preparerEmp = null;
        experience = null;
        preparedDate = null;
        updateStatus = 0;
        saveOrUpdate = "Save";
    }

    public void searchExitInterview() {
        if (srcEmployees.getEmpId().isEmpty() && srcEmployees.getFirstName().isEmpty()) {
            selectedList = exitInterviewBeanLocal.findAll();
            exitInterviewDataModel = new ListDataModel(selectedList);
        } else if (srcEmployees.getEmpId() != null && srcEmployees.getFirstName().isEmpty()) {
            selectedList = exitInterviewBeanLocal.findByEmpId(srcEmployees);
            exitInterviewDataModel = new ListDataModel(selectedList);
        } else if (srcEmployees.getEmpId().isEmpty() && srcEmployees.getFirstName() != null) {
            selectedList = exitInterviewBeanLocal.findByName(srcEmployees);
            exitInterviewDataModel = new ListDataModel(selectedList);
        } else if (srcEmployees.getEmpId() != null && srcEmployees.getFirstName() != null) {
            selectedList = exitInterviewBeanLocal.findByEmpIdAndName(srcEmployees, srcEmployees);
            exitInterviewDataModel = new ListDataModel(selectedList);
        }
    }

    public void populateExitInterview(SelectEvent events) {
        hrExitInterview = (HrExitInterview) events.getObject();
        hrExitInterview.setId(hrExitInterview.getId());
        hrExitInterview = exitInterviewBeanLocal.getSelectedExitInterview(hrExitInterview.getId());
        hrEmployees = hrExitInterview.getTerminationRequestId().getEmpId();
        hrLuGrades = hrEmployees.getGradeId().getGradeId();
        preparedDate = hrExitInterview.getPreparedOn();
        serviceYearForPopulate();
        if (hrExitInterview.getLeaveReason().equalsIgnoreCase("8")) {
            disableReason = false;
        } else {
            disableReason = true;
        }
        chooseCreatePage = true;
        chooseMainPage = false;
        btnNewRender = true;
        updateStatus = 1;
        saveOrUpdate = "Update";
        setIcone("ui-icon-search");
    }
}
