/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.controller.displines;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
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
import securityBean.Constants;
import securityBean.SessionBean;
import webService.AAA;
import webService.EventEntry;
import webService.IAdministration;
import et.gov.eep.commonApplications.businessLogic.WfHrProcessedBeanLocal;
import et.gov.eep.commonApplications.entity.WfHrProcessed;
import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.commonApplications.utility.date.StringDateManipulation;
import et.gov.eep.hrms.businessLogic.compliantHandling.HrAppealRequestsBeanLocal;
import et.gov.eep.hrms.businessLogic.displine.DisplineRequestBeanLocal;
import et.gov.eep.hrms.businessLogic.displine.HrDisciplineOffenceTypesBeanLocal;
import et.gov.eep.hrms.businessLogic.displine.HrDisciplinePenlitysBeansLocal;
import et.gov.eep.hrms.entity.compliantHandling.HrAppeals;
import et.gov.eep.hrms.entity.displine.HrDisciplineOffencePenality;
import et.gov.eep.hrms.entity.displine.HrDisciplineOffenceTypes;
import et.gov.eep.hrms.entity.displine.HrDisciplinePenaltyTypes;
import et.gov.eep.hrms.entity.displine.HrDisciplinePenlitys;
import et.gov.eep.hrms.entity.displine.HrDisciplineRequests;
import et.gov.eep.hrms.entity.displine.HrEmpDisciplines;
import et.gov.eep.hrms.entity.employee.HrEmployees;
import et.gov.eep.hrms.entity.lookup.HrLuGrades;
import et.gov.eep.hrms.entity.organization.HrDepartments;
import et.gov.eep.hrms.entity.organization.HrJobTypes;
import et.gov.eep.hrms.entity.organization.HrSalaryScaleRanges;
import et.gov.eep.hrms.integration.HREmployeesBeanLocal;

/**
 *
 * @author Abdi_Pc
 */
@Named(value = "adjudicateDisciplineAppealController")
@ViewScoped
public class AdjudicateDisciplineAppealController implements Serializable {

    // <editor-fold defaultstate="collapsed" desc="Entity, bussiness logic & variables">
    @EJB
    WfHrProcessedBeanLocal wfHrProcessedBeanLocal;
    @EJB
    HrAppealRequestsBeanLocal hrAppealRequestsBeanLocal;
    @EJB
    private HREmployeesBeanLocal hrEmployeeBean;
    @EJB
    HrDisciplineOffenceTypesBeanLocal offenceTypesBeanLocal;
    @EJB
    HrDisciplinePenlitysBeansLocal disciplinePenlitysBeansLocal;
    @EJB
    DisplineRequestBeanLocal requestBeanLocal;

    @Inject
    WfHrProcessed wfHrProcessed;
    @Inject
    HrDisciplineRequests disciplineRequests;
    @Inject
    HrDisciplinePenlitys disciplinePenlitys;
    @Inject
    HrDisciplineOffenceTypes offenceTypes;
    @Inject
    HrEmployees hrEmployeesAccuser;
    @Inject
    HrEmployees hrEmployeesRequester;
    @Inject
    HrDisciplinePenaltyTypes hrDisciplinePenaltyTypes;
    @Inject
    HrDisciplineOffencePenality hrDisciplineOffencePenality;
    @Inject
    HrDepartments departmentsOfAccuser, departmentsOfAccused;
    @Inject
    HrJobTypes hrJobTypesOfAccuser, hrJobTypesOfAccused;
    @Inject
    HrAppeals hrAppeals;
    @Inject
    HrLuGrades hrLuGradesAccused, hrLuGradesAccuser;
    @Inject
    SessionBean sessionBeanLocal;
    @Inject
    HrEmpDisciplines hrEmpDisciplines;

    @Inject
    HrSalaryScaleRanges hrSalaryScaleRangesAccuser, hrSalaryScaleRangesRequester;

    List<HrAppeals> appealList;
    List<HrDisciplineRequests> appealApprovedList;
    private String fullName;

    private String createOrSearchBundle = "New";
    private String icone = "ui-icon-plus";
    private String saveorUpdateBundle = "Create";
    private String slectedC = "Accept";

    private boolean disableBtnCreate;
    private boolean renderPnlCreateAdditional = false;
    private boolean renderPnlManPage = true;
    private boolean pnlAccept = true;
    private boolean pnlOther = false;
//</editor-fold>

    @PostConstruct
    public void init() {
        setAppealApprovedList(requestBeanLocal.readApprovedDisciplinaryAppeals());
        //disciplineRequests.setApprovedOn(StringDateManipulation.toDayInEc());
        //wfHrProcessed.setProcessedOn(StringDateManipulation.toDayInEc());
    }

    // <editor-fold defaultstate="collapsed" desc="Getters setters"> 
    public HrSalaryScaleRanges getHrSalaryScaleRangesAccuser() {
        if (hrSalaryScaleRangesAccuser == null) {
            hrSalaryScaleRangesAccuser = new HrSalaryScaleRanges();
        }

        return hrSalaryScaleRangesAccuser;
    }

    public void setHrSalaryScaleRangesAccuser(HrSalaryScaleRanges hrSalaryScaleRangesAccuser) {
        this.hrSalaryScaleRangesAccuser = hrSalaryScaleRangesAccuser;
    }

    public HrSalaryScaleRanges getHrSalaryScaleRangesRequester() {
        if (hrSalaryScaleRangesRequester == null) {
            hrSalaryScaleRangesRequester = new HrSalaryScaleRanges();
        }
        return hrSalaryScaleRangesRequester;
    }

    public void setHrSalaryScaleRangesRequester(HrSalaryScaleRanges hrSalaryScaleRangesRequester) {
        this.hrSalaryScaleRangesRequester = hrSalaryScaleRangesRequester;
    }

    public WfHrProcessed getWfHrProcessed() {
        if (wfHrProcessed == null) {
            wfHrProcessed = new WfHrProcessed();
        }
        return wfHrProcessed;
    }

    public void setWfHrProcessed(WfHrProcessed wfHrProcessed) {
        this.wfHrProcessed = wfHrProcessed;
    }

    public List<HrDisciplineRequests> getAppealApprovedList() {
        return appealApprovedList = requestBeanLocal.readApprovedDisciplinaryAppeals();
    }

    public void setAppealApprovedList(List<HrDisciplineRequests> appealApprovedtList) {
        this.appealApprovedList = appealApprovedtList;
    }

    public boolean isPnlAccept() {
        return pnlAccept;
    }

    public void setPnlAccept(boolean pnlAccept) {
        this.pnlAccept = pnlAccept;
    }

    public boolean isPnlOther() {
        return pnlOther;
    }

    public void setPnlOther(boolean pnlOther) {
        this.pnlOther = pnlOther;
    }

    public String getSlectedC() {
        return slectedC;
    }

    public void setSlectedC(String slectedC) {
        this.slectedC = slectedC;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public HrAppeals getHrAppeals() {
        return hrAppeals;
    }

    public void setHrAppeals(HrAppeals hrAppeals) {
        this.hrAppeals = hrAppeals;
    }

    public HrLuGrades getHrLuGradesAccused() {
        if (hrLuGradesAccused == null) {
            hrLuGradesAccused = new HrLuGrades();
        }
        return hrLuGradesAccused;
    }

    public void setHrLuGradesAccused(HrLuGrades hrLuGradesAccused) {
        this.hrLuGradesAccused = hrLuGradesAccused;
    }

    public HrLuGrades getHrLuGradesAccuser() {
        if (hrLuGradesAccuser == null) {
            hrLuGradesAccuser = new HrLuGrades();
        }
        return hrLuGradesAccuser;
    }

    public void setHrLuGradesAccuser(HrLuGrades hrLuGradesAccuser) {
        this.hrLuGradesAccuser = hrLuGradesAccuser;
    }

    public HrDisciplineRequests getDisciplineRequests() {
        if (disciplineRequests == null) {
            disciplineRequests = new HrDisciplineRequests();
        }
        return disciplineRequests;
    }

    public void setDisciplineRequests(HrDisciplineRequests disciplineRequests) {
        this.disciplineRequests = disciplineRequests;
    }

    public HrDisciplinePenlitys getDisciplinePenlitys() {
        return disciplinePenlitys;
    }

    public void setDisciplinePenlitys(HrDisciplinePenlitys disciplinePenlitys) {
        this.disciplinePenlitys = disciplinePenlitys;
    }

    public HrDisciplineOffenceTypes getOffenceTypes() {
        if (offenceTypes == null) {
            offenceTypes = new HrDisciplineOffenceTypes();
        }
        return offenceTypes;
    }

    public void setOffenceTypes(HrDisciplineOffenceTypes offenceTypes) {
        this.offenceTypes = offenceTypes;
    }

    public HrEmployees getHrEmployeesAccuser() {
        if (hrEmployeesAccuser == null) {
            hrEmployeesAccuser = new HrEmployees();
        }
        return hrEmployeesAccuser;
    }

    public void setHrEmployeesAccuser(HrEmployees hrEmployeesAccuser) {
        this.hrEmployeesAccuser = hrEmployeesAccuser;
    }

    public HrEmployees getHrEmployeesRequester() {
        if (hrEmployeesRequester == null) {
            hrEmployeesRequester = new HrEmployees();
        }
        return hrEmployeesRequester;
    }

    public void setHrEmployeesRequester(HrEmployees hrEmployeesRequester) {
        this.hrEmployeesRequester = hrEmployeesRequester;
    }

    public HrDisciplinePenaltyTypes getHrDisciplinePenaltyTypes() {
        return hrDisciplinePenaltyTypes;
    }

    public void setHrDisciplinePenaltyTypes(HrDisciplinePenaltyTypes hrDisciplinePenaltyTypes) {
        this.hrDisciplinePenaltyTypes = hrDisciplinePenaltyTypes;
    }

    public HrDisciplineOffencePenality getHrDisciplineOffencePenality() {
        return hrDisciplineOffencePenality;
    }

    public void setHrDisciplineOffencePenality(HrDisciplineOffencePenality hrDisciplineOffencePenality) {
        this.hrDisciplineOffencePenality = hrDisciplineOffencePenality;
    }

    public HrDepartments getDepartmentsOfAccuser() {
        if (departmentsOfAccuser == null) {
            departmentsOfAccuser = new HrDepartments();
        }
        return departmentsOfAccuser;
    }

    public void setDepartmentsOfAccuser(HrDepartments departmentsOfAccuser) {
        this.departmentsOfAccuser = departmentsOfAccuser;
    }

    public HrDepartments getDepartmentsOfAccused() {
        if (departmentsOfAccused == null) {
            departmentsOfAccused = new HrDepartments();
        }
        return departmentsOfAccused;
    }

    public void setDepartmentsOfAccused(HrDepartments departmentsOfAccused) {
        this.departmentsOfAccused = departmentsOfAccused;
    }

    public HrJobTypes getHrJobTypesOfAccuser() {
        if (hrJobTypesOfAccuser == null) {
            hrJobTypesOfAccuser = new HrJobTypes();
        }
        return hrJobTypesOfAccuser;
    }

    public void setHrJobTypesOfAccuser(HrJobTypes hrJobTypesOfAccuser) {
        this.hrJobTypesOfAccuser = hrJobTypesOfAccuser;
    }

    public HrJobTypes getHrJobTypesOfAccused() {
        if (hrJobTypesOfAccused == null) {
            hrJobTypesOfAccused = new HrJobTypes();
        }
        return hrJobTypesOfAccused;
    }

    public void setHrJobTypesOfAccused(HrJobTypes hrJobTypesOfAccused) {
        this.hrJobTypesOfAccused = hrJobTypesOfAccused;
    }

    public HrDisciplineOffenceTypesBeanLocal getOffenceTypesBeanLocal() {
        return offenceTypesBeanLocal;
    }

    public void setOffenceTypesBeanLocal(HrDisciplineOffenceTypesBeanLocal offenceTypesBeanLocal) {
        this.offenceTypesBeanLocal = offenceTypesBeanLocal;
    }

    public HrDisciplinePenlitysBeansLocal getDisciplinePenlitysBeansLocal() {
        return disciplinePenlitysBeansLocal;
    }

    public void setDisciplinePenlitysBeansLocal(HrDisciplinePenlitysBeansLocal disciplinePenlitysBeansLocal) {
        this.disciplinePenlitysBeansLocal = disciplinePenlitysBeansLocal;
    }

    public HrAppealRequestsBeanLocal getHrAppealRequestsBeanLocal() {
        return hrAppealRequestsBeanLocal;
    }

    public void setHrAppealRequestsBeanLocal(HrAppealRequestsBeanLocal hrAppealRequestsBeanLocal) {
        this.hrAppealRequestsBeanLocal = hrAppealRequestsBeanLocal;
    }

    public HREmployeesBeanLocal getHrEmployeeBean() {
        return hrEmployeeBean;
    }

    public void setHrEmployeeBean(HREmployeesBeanLocal hrEmployeeBean) {
        this.hrEmployeeBean = hrEmployeeBean;
    }

    public List<HrAppeals> getAppealList() {
        return appealList;
    }

    public void setAppealList(List<HrAppeals> appealList) {
        this.appealList = appealList;
    }

    public boolean isRenderPnlCreateAdditional() {
        return renderPnlCreateAdditional;
    }

    public void setRenderPnlCreateAdditional(boolean renderPnlCreateAdditional) {
        this.renderPnlCreateAdditional = renderPnlCreateAdditional;
    }

    public boolean isRenderPnlManPage() {
        return renderPnlManPage;
    }

    public void setRenderPnlManPage(boolean renderPnlManPage) {
        this.renderPnlManPage = renderPnlManPage;
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
//</editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Search">

    public void createNewAdditionalAmount() {
        disableBtnCreate = false;
        if (createOrSearchBundle.equals("New")) {
            renderPnlCreateAdditional = true;
            renderPnlManPage = false;
            createOrSearchBundle = "Search";
            setIcone("ui-icon-search");
        } else if (createOrSearchBundle.equals("Search")) {
            renderPnlCreateAdditional = false;
            renderPnlManPage = true;
            createOrSearchBundle = "New";
            setIcone("ui-icon-plus");
        }
    }

    public void populate(SelectEvent events) {
        disciplineRequests = (HrDisciplineRequests) events.getObject();
        hrEmployeesRequester = disciplineRequests.getRequesterId();
        departmentsOfAccused = hrEmployeesRequester.getDeptId();
        hrJobTypesOfAccused = hrEmployeesRequester.getJobId();
        hrEmployeesAccuser = disciplineRequests.getOffenderId();
        departmentsOfAccuser = hrEmployeesAccuser.getDeptId();
        hrJobTypesOfAccuser = hrEmployeesAccuser.getJobId();
        offenceTypes = disciplineRequests.getOffenceTypeId();
        hrSalaryScaleRangesAccuser = hrEmployeesAccuser.getGradeId();
        hrSalaryScaleRangesRequester = hrEmployeesRequester.getGradeId();
        hrLuGradesAccused = hrSalaryScaleRangesAccuser.getGradeId();
        hrLuGradesAccuser = hrSalaryScaleRangesRequester.getGradeId();
        hrAppeals.setCaseId(disciplineRequests.getId().toString());
        renderPnlCreateAdditional = true;
        renderPnlManPage = false;
        phaseOutTimeCalculation();
        createOrSearchBundle = "Search";
        icone = "ui-icon-search";
    }
//</editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Main">
    int status = 1;

    DataModel<HrAppeals> appealDataModel;

    public DataModel<HrAppeals> getAppealDataModel() {
        return appealDataModel = new ListDataModel();
    }

    public void setAppealDataModel(DataModel<HrAppeals> appealDataModel) {
        this.appealDataModel = appealDataModel;
    }

    public ArrayList<HrEmployees> SearchByFnameAccused(String employee) {
        ArrayList<HrEmployees> employees = null;
        hrEmployeesRequester.setFirstName(employee);
        employees = hrEmployeeBean.searchEmployeeByName(hrEmployeesRequester);
        return employees;
    }

    public void getByFirstNameAccused(SelectEvent event) {
        hrEmployeesRequester.setFirstName((event.getObject().toString()));
        hrEmployeesRequester = hrEmployeeBean.getByFirstName(hrEmployeesRequester);
        departmentsOfAccused = hrEmployeesRequester.getDeptId();
        hrJobTypesOfAccused = hrEmployeesRequester.getJobId();
        disciplineRequests.setRequesterId(hrEmployeesRequester);
    }

    public ArrayList<HrEmployees> SearchByFname(String employee) {
        ArrayList<HrEmployees> employees = null;
        hrEmployeesAccuser.setFirstName(employee);
        employees = hrEmployeeBean.searchEmployeeByName(hrEmployeesAccuser);
        return employees;
    }

    public void getByFirstName(SelectEvent event) {
        hrEmployeesAccuser.setFirstName((event.getObject().toString()));
        hrEmployeesAccuser = hrEmployeeBean.getByFirstName(hrEmployeesAccuser);
        departmentsOfAccuser = hrEmployeesAccuser.getDeptId();
        hrJobTypesOfAccuser = hrEmployeesAccuser.getJobId();
        disciplineRequests.setOffenderId(hrEmployeesAccuser);
    }

    public void handleDecition(ValueChangeEvent event) {
        if (null != event.getNewValue()) {
            slectedC = event.getNewValue().toString();
            if (slectedC.equalsIgnoreCase("Accept")) {
                pnlAccept = true;
                pnlOther = false;
            } else {
                pnlAccept = false;
                pnlOther = true;
            }
        }
    }

    public String dateFormat(String date) {
        String[] dateFromUI;
        String dateInDB;
        if (date != null && date.contains("-")) {
            dateFromUI = date.split("-");
            dateInDB = dateFromUI[2] + "/" + dateFromUI[1] + "/" + dateFromUI[0];
            return dateInDB;
        }
        return null;
    }

    Integer phaseOutTime = 0;

    public Integer getPhaseOutTime() {
        return phaseOutTime;
    }

    public void setPhaseOutTime(Integer phaseOutTime) {
        this.phaseOutTime = phaseOutTime;
    }

    public void phaseOutTimeCalculation() {
        disciplineRequests.setOffenceDate(disciplineRequests.getOffenceDate());
        HrDisciplineRequests disciplineReq = new HrDisciplineRequests();
        disciplineRequests.setOffenceTypeId(disciplineRequests.getOffenceTypeId());
        disciplineRequests.setOffenderId(hrEmployeesAccuser);
        disciplineReq = requestBeanLocal.findDisciplineReqByOffenderIdAndOffenceType(disciplineRequests);
        if (disciplineReq != null) {
            String offenceDate[] = disciplineReq.getOffenceDate().split("/");
            int offenceDay = Integer.parseInt(offenceDate[0]);
            int offenceMonth = Integer.parseInt(offenceDate[1]);
            int offenceYear = Integer.parseInt(offenceDate[2]);
            String currentDate[] = dateFormat(StringDateManipulation.toDayInEc()).split("/");
            int currentDay = Integer.parseInt(currentDate[2]);
            int currentMonth = Integer.parseInt(currentDate[1]);
            int currentYear = Integer.parseInt(currentDate[0]);
            int dayBetween = (currentDay - offenceDay) + ((currentMonth - offenceMonth) * 30) + ((currentYear - offenceYear) * 360);
            int monthBetween = dayBetween / 30;
            int newMonthDifferece = Integer.parseInt(offenceTypes.getPhaseoutPeriod()) - monthBetween;
            if (newMonthDifferece > 0) {
                phaseOutTime = Integer.parseInt(offenceTypes.getPhaseoutPeriod()) + monthBetween;
            } else {
                phaseOutTime = Integer.parseInt(offenceTypes.getPhaseoutPeriod());
            }
        } else {
            phaseOutTime = Integer.parseInt(offenceTypes.getPhaseoutPeriod());
        }
    }

    String penalty;

    public String getPenalty() {
        return penalty;
    }

    public void setPenalty(String penalty) {
        this.penalty = penalty;
    }

    public void saveaAjudicateDisciplineAppeal() {
        try {
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(sessionBeanLocal.getUserName(), "saveaAjudicateDisciplineAppeal", dataset)) {
                HrAppeals appals = new HrAppeals();
                if (disciplineRequests.getId() != null) {
                    hrAppeals.setCaseId(disciplineRequests.getId().toString());
                    appals = requestBeanLocal.findByCaseId(hrAppeals);
                    if (slectedC.equalsIgnoreCase("Accept") && selectedValue.equalsIgnoreCase("1") && (appals != null)) {
                        disciplineRequests.setStatus(String.valueOf(Constants.ADJUDICATE_DISCIPLINE_APPROVED_STATUS));
                        appals.setStatus(Constants.ADJUDICATE_ON_APPEAL_STATUS);
                        appals.setAppealDecisionDate(StringDateManipulation.toDayInEc());
                        requestBeanLocal.edit(disciplineRequests);
                        requestBeanLocal.edit(appals);
                        JsfUtil.addSuccessMessage("Successfully Saved.");
                        reset();
                    } else if (slectedC.equalsIgnoreCase("Accept") && selectedValue.equalsIgnoreCase("2") && (appals != null)) {
                        disciplineRequests.setStatus(String.valueOf(Constants.ADJUDICATE_DISCIPLINE_REJECT_STATUS));
                        appals.setStatus(Constants.ADJUDICATE_ON_APPEAL_STATUS);
                        appals.setAppealDecisionDate(StringDateManipulation.toDayInEc());
                        //disciplineRequests.setDecisionOnPenality(penalty);
                        requestBeanLocal.edit(disciplineRequests);
                        requestBeanLocal.edit(appals);
                        JsfUtil.addSuccessMessage("Successfully Saved.");
                        reset();
                    } else if (slectedC.equalsIgnoreCase("Other") && selectedValue.equalsIgnoreCase("1") && (appals != null)) {
                        disciplineRequests.setStatus(String.valueOf(Constants.ADJUDICATE_DISCIPLINE_APPROVED_STATUS));
                        appals.setStatus(Constants.ADJUDICATE_ON_APPEAL_STATUS);
                        appals.setAppealDecisionDate(StringDateManipulation.toDayInEc());
                        disciplineRequests.setDecisionOnPenality(penalty);
                        requestBeanLocal.edit(disciplineRequests);
                        requestBeanLocal.edit(appals);
                        JsfUtil.addSuccessMessage("Successfully Saved.");
                        reset();
                    } else if (slectedC.equalsIgnoreCase("Other") && selectedValue.equalsIgnoreCase("2") && (appals != null)) {
                        disciplineRequests.setStatus(String.valueOf(Constants.ADJUDICATE_DISCIPLINE_REJECT_STATUS));
                        appals.setStatus(Constants.ADJUDICATE_ON_APPEAL_STATUS);
                        appals.setAppealDecisionDate(StringDateManipulation.toDayInEc());
                        disciplineRequests.setDecisionOnPenality(penalty);
                        requestBeanLocal.edit(disciplineRequests);
                        requestBeanLocal.edit(appals);
                        JsfUtil.addSuccessMessage("Successfully Saved.");
                        reset();
                    } else {
                        JsfUtil.addSuccessMessage("There is no much case id.");
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
                JAXBElement<String> program = new JAXBElement<String>(qualifiedName, String.class, null, "saveaAjudicateDisciplineAppeal");
                eventEntry.setProgram(program);
                security.addEventLog(eventEntry, dataset);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    String selectedValue = "";

    public void handleSelectOptions(ValueChangeEvent event) {
        if (null != event.getNewValue()) {
            selectedValue = event.getNewValue().toString();
        }
    }

    public void reset() {
        disciplineRequests = null;
        hrEmployeesAccuser = null;
        hrEmployeesRequester = null;
        offenceTypes = null;
        hrJobTypesOfAccuser = null;
        hrJobTypesOfAccused = null;
        departmentsOfAccused = null;
        departmentsOfAccuser = null;
        hrDisciplineOffencePenality = null;
        hrDisciplinePenaltyTypes = null;
        hrLuGradesAccused = null;
        hrLuGradesAccuser = null;
        penalty = null;
        wfHrProcessed = new WfHrProcessed();
        setFullName(null);
        wfHrProcessed.setProcessedOn(null);
        setPhaseOutTime(null);

    }

    //</editor-fold>
}
