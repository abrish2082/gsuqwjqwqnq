/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.controller.promotion;

import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.commonApplications.utility.date.StringDateManipulation;
import et.gov.eep.hrms.businessLogic.employee.HrEmployeeBeanLocal;
import et.gov.eep.hrms.businessLogic.organization.HrJobEducQualificationsBeanLocal;
import et.gov.eep.hrms.businessLogic.promotion.PromotionApplyBeanLocal;
import et.gov.eep.hrms.businessLogic.recruitment.HrAdvertisementBeanLocal;
import et.gov.eep.hrms.businessLogic.recruitment.HrRecruitmentRequestsBeanLocal;
import et.gov.eep.hrms.entity.employee.HrEmpEducations;
import et.gov.eep.hrms.entity.employee.HrEmployees;
import et.gov.eep.hrms.entity.organization.HrDepartments;
import et.gov.eep.hrms.entity.organization.HrJobEducQualifications;
import et.gov.eep.hrms.entity.organization.HrJobTypes;
import et.gov.eep.hrms.entity.promotion.HrPromotionRequests;
import et.gov.eep.hrms.entity.recruitment.HrAdvertisedJobs;
import et.gov.eep.hrms.entity.recruitment.HrAdvertisements;
import et.gov.eep.hrms.entity.recruitment.HrRecruitmentRequests;
import et.gov.eep.hrms.integration.HREmployeesBeanLocal;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.ArrayList;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.DataModel;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.xml.namespace.QName;
import javax.xml.bind.JAXBElement;
import org.primefaces.event.SelectEvent;
import securityBean.SessionBean;
import webService.AAA;
import webService.EventEntry;
import webService.IAdministration;

/**
 *
 * @author user
 */
@Named("promotionPostControllers")
@ViewScoped
public class PromotionPostController implements Serializable {

    @Inject
    SessionBean sessionBean;
    @EJB
    HrJobEducQualificationsBeanLocal hrJobEducQualificationsBeanLocal;
    @EJB
    PromotionApplyBeanLocal promotionApplyBeanLocal;

    @EJB
    HrAdvertisementBeanLocal hrAdvertisementBeanLocal;

    @Inject
    HrPromotionRequests promotionApply;

    @Inject
    HrJobTypes jobTypes;
    @Inject
    HrRecruitmentRequests recruitmentRequests;
    @Inject
    HrDepartments hrDepartments;
    @Inject
    HrEmployees hrEmployees;

    @Inject
    HrEmpEducations hrEmpEducations;

    @Inject
    HrEmployees hrEmployeesApplied;
        @EJB
    HrEmployeeBeanLocal profileBeanLocal;

    @Inject
    HrAdvertisedJobs advertisedJobesEntity;

    @Inject
    HrJobEducQualifications hrJobEducQualifications;

    @Inject
    HrAdvertisements hrAdvertisements;

    @EJB
    private HREmployeesBeanLocal hrEmployeeBean;

    @EJB
    HrRecruitmentRequestsBeanLocal hrRecruitmentRequestsBeanLocal;

    List<HrAdvertisements> activeVacancies;

    List<HrJobEducQualifications> hrJobEducQualificationsesList;

    List<HrAdvertisedJobs> advertisedJobs = new ArrayList<>();

    List<HrEmpEducations> hrEmpEducationsList;

    private DataModel<HrAdvertisements> hradertesideModel;
    private String saveOrUpdateButton = "Save";
    int update = 0;
    private HrAdvertisedJobs selectedRow;

    private boolean disableBtnCreate;
    private String saveorUpdateBundle = "Create";
    private String createOrSearchBundle = "Search";
    private boolean renderPnlCreateAdditional = false;
    private boolean renderPnlManPage = true;
    private boolean validFromTo = false;
    private String icone = "ui-icon-document";

    @PostConstruct
    public void init() {
        getActiveVacancies();
    }

    public HrDepartments getHrDepartments() {
        return hrDepartments;
    }

    public void setHrDepartments(HrDepartments hrDepartments) {
        this.hrDepartments = hrDepartments;
    }

    public HrJobTypes getJobTypes() {
        return jobTypes;
    }

    public void setJobTypes(HrJobTypes jobTypes) {
        this.jobTypes = jobTypes;
    }

    public List<HrEmpEducations> getHrEmpEducationsList() {
        return hrEmpEducationsList;
    }

    public void setHrEmpEducationsList(List<HrEmpEducations> hrEmpEducationsList) {
        this.hrEmpEducationsList = hrEmpEducationsList;
    }

    public List<HrJobEducQualifications> getHrJobEducQualificationsesList() {
        return hrJobEducQualificationsesList;
    }

    public void setHrJobEducQualificationsesList(List<HrJobEducQualifications> hrJobEducQualificationsesList) {
        this.hrJobEducQualificationsesList = hrJobEducQualificationsesList;
    }

    public boolean isRenderPnlManPage() {
        return renderPnlManPage;
    }

    public void setRenderPnlManPage(boolean renderPnlManPage) {
        this.renderPnlManPage = renderPnlManPage;
    }

    public boolean isRenderPnlCreateAdditional() {
        return renderPnlCreateAdditional;
    }

    public void setRenderPnlCreateAdditional(boolean renderPnlCreateAdditional) {
        this.renderPnlCreateAdditional = renderPnlCreateAdditional;
    }

    public String getIcone() {
        return icone;
    }

    public void setIcone(String icone) {
        this.icone = icone;
    }

    public String getCreateOrSearchBundle() {
        return createOrSearchBundle;
    }

    public void setCreateOrSearchBundle(String createOrSearchBundle) {
        this.createOrSearchBundle = createOrSearchBundle;
    }

    // </editor-fold>
    public HrAdvertisedJobs getSelectedRow() {
        return selectedRow;
    }

    public void setSelectedRow(HrAdvertisedJobs selectedRow) {
        this.selectedRow = selectedRow;
    }

    public HrEmpEducations getHrEmpEducations() {
        if (hrEmpEducations == null) {
            hrEmpEducations = new HrEmpEducations();
        }
        return hrEmpEducations;
    }

    public void setHrEmpEducations(HrEmpEducations hrEmpEducations) {
        this.hrEmpEducations = hrEmpEducations;
    }

    public HrAdvertisedJobs getAdvertisedJobesEntity() {
        return advertisedJobesEntity;
    }

    public void setAdvertisedJobesEntity(HrAdvertisedJobs advertisedJobesEntity) {
        this.advertisedJobesEntity = advertisedJobesEntity;
    }

    public List<HrAdvertisedJobs> getAdvertisedJobs() {
        if (advertisedJobs == null) {
            advertisedJobs = new ArrayList<>();
        }
        return advertisedJobs;
    }

    public void setAdvertisedJobs(List<HrAdvertisedJobs> advertisedJobs) {
        this.advertisedJobs = advertisedJobs;
    }

    public List<HrAdvertisements> getActiveVacancies() {
        return activeVacancies = promotionApplyBeanLocal.activeVacancies();
    }

    public String getSaveOrUpdateButton() {
        return saveOrUpdateButton;
    }

    public void setSaveOrUpdateButton(String saveOrUpdateButton) {
        this.saveOrUpdateButton = saveOrUpdateButton;
    }

    public HrPromotionRequests getPromotionRequests() {
        if (promotionApply == null) {
            promotionApply = new HrPromotionRequests();
        }
        return promotionApply;
    }

    public void setPromotionRequests(HrPromotionRequests promotionApply) {
        this.promotionApply = promotionApply;
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

    public List<HrEmployees> SearchByFname(String hrEmployees) {
        List<HrEmployees> employees = null;
        employees = promotionApplyBeanLocal.searchPermanentEmp(hrEmployees);
        return employees;
    }

    public void getByFirstName(SelectEvent event) {
        hrEmployees = new HrEmployees();
        hrEmployees.setFirstName((event.getObject().toString()));
        hrEmployees = promotionApplyBeanLocal.getByPermanentEmp(hrEmployees);

    }

    public DataModel<HrAdvertisements> getHradertesideModel() {
        return hradertesideModel;
    }

    public void setHradertesideModel(DataModel<HrAdvertisements> hradertesideModel) {
        this.hradertesideModel = hradertesideModel;
    }

    public HrAdvertisements getHrAdvertisements() {
        if (hrAdvertisements == null) {
            hrAdvertisements = new HrAdvertisements();
        }
        return hrAdvertisements;
    }

    public void setHrAdvertisements(HrAdvertisements hrAdvertisements) {
        this.hrAdvertisements = hrAdvertisements;
    }

    public void checkDuplicateEmployee() {
    }

    private int calculateExperiance(String year) {
        String currDate = StringDateManipulation.toDayInEc();
        int dateDiffrence = StringDateManipulation.datesDifferenceInDays(year, currDate);
        System.out.println("dateDiffrence---" + dateDiffrence);
        return dateDiffrence;
    }

    private int requsterId;
    SimpleDateFormat dateformatter = new SimpleDateFormat("yyyy-MM-dd");

      public List<HrEmployees> searchEmployee(String employeename) {
        hrEmployees = new HrEmployees();
        List<HrEmployees> registeredEmployee = null;
        hrEmployees.setEmpId(employeename);
        registeredEmployee = profileBeanLocal.SearchByEmpId(hrEmployees);
        return registeredEmployee;

    }

    public void getEmployeeInfo(SelectEvent event) {
        String emplName = event.getObject().toString();

        HrEmployees employeeFirst = new HrEmployees();
        employeeFirst.setEmpId(emplName);

        hrEmployees = profileBeanLocal.getByEmpId(hrEmployees);
        System.out.println("emp Status  " + hrEmployees.getEmpStatus());
    }
    public void savePromotionApply() {
        try {

            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(sessionBean.getUserName(), "savePromotionApply", dataset)) {
//                 put ur code here...!
                String toDay = StringDateManipulation.toDayInEc();

                System.out.println("toDay date---" + toDay);

                if (hrEmployees != null) {

                    String splt[] = hrEmployees.getHireDate().split("/");

                    int leaveDate = Integer.parseInt(splt[0]);

                    int leaveMonth = Integer.parseInt(splt[1]) - 1;

                    int leaveYear = Integer.parseInt(splt[2]);

                    Calendar leaveStartCal = Calendar.getInstance();
                    leaveStartCal.set(leaveYear, leaveMonth, leaveDate);

                    String hireDate = dateformatter.format(leaveStartCal.getTime());

                    hrEmpEducationsList = promotionApplyBeanLocal.findEmpEducation(hrEmployees);
                    System.out.println("hrEmpEducationsList--" + hrEmpEducationsList);

                    requsterId = hrEmployees.getId();
                    String empType = hrEmployees.getEmploymentType();
                    HrPromotionRequests hpa;
                    hpa = promotionApplyBeanLocal.findPomoApplied(hrEmployees, advertisedJobesEntity);
                    System.out.println("---hpa---" + hpa + "-----" + hrEmployees.getEmploymentType());
                    if (hpa == null && hrEmployees.getEmploymentType().equalsIgnoreCase("permanent")) {
                        System.out.println("toDay date---" + toDay);
                        hrEmpEducationsList = promotionApplyBeanLocal.findEmpEducation(hrEmployees);
                        System.out.println("hrEmpEducationsList--" + hrEmpEducationsList);
                        System.out.println("hrJobEducQualificationsesList--" + hrJobEducQualificationsesList);
                        if (hrEmpEducationsList.size() > 0 && hrJobEducQualificationsesList.size() > 0) {
                            if (validateEducLevel() == true) {
                                if (validateEducType() == true) {
                                    for (int j = 0; j < hrJobEducQualificationsesList.size(); j++) {
                                        HrJobEducQualifications hrJobEducQualifications = hrJobEducQualificationsesList.get(j);

                                        if (StringDateManipulation.datesDifferenceInDays(hireDate, toDay) >= hrJobEducQualifications.getMinExperiance()) {
                                            System.out.println("----inside test--1--");
                                            if (StringDateManipulation.datesDifferenceInDays(hireDate, toDay) >= 360) {
                                                System.out.println("----inside test--2--");
                                                promotionApply.setAdvertJobId(advertisedJobesEntity);
                                                promotionApply.setRequesterId(hrEmployees);
                                                promotionApplyBeanLocal.saveOrUpdate(promotionApply);
                                                JsfUtil.addSuccessMessage(" Successfully Applied");
                                                clear();
                                                j = hrJobEducQualificationsesList.size() + 1;
                                            } else {
                                                JsfUtil.addFatalMessage("Selected employee gives less than one year service after hired in EEP. ");
                                            }

                                        } else {
                                            JsfUtil.addFatalMessage("Sorry, Your Exprience is Not Enough for this vacancy. ");
                                        }
                                    }
                                } else {
                                    JsfUtil.addFatalMessage("Sorry, Your Education Types is Not Enough for this vacancy. ");
                                }
                            } else {
                                JsfUtil.addFatalMessage("Sorry, Your Education Level is Not Enough for this vacancy. ");
                            }
//                            }
//                        }
                        } else {
                            JsfUtil.addFatalMessage("Employee Education is null ");
                        }
                    } else if (hpa != null && empType.equalsIgnoreCase("permanent")) {
                        JsfUtil.addFatalMessage("you Are Alread Applied For This Position");
                    } else {
                        JsfUtil.addFatalMessage("Contract Employement is not allowed for internal vacancy");
                    }
                }

            } else {
                JsfUtil.addFatalMessage("You don't have appropriate permission, please contact the system administrator.");

                EventEntry eventEntry = new EventEntry();
                eventEntry.setSessionId(sessionBean.getSessionID());
                eventEntry.setUserId(sessionBean.getUserId());
                QName qualifiedName = new QName("", "project");
                JAXBElement<String> test = new JAXBElement<String>(qualifiedName, String.class, null, sessionBean.getUserName());
                eventEntry.setUserLogin(test);
//..... add more information by calling fields defined in the object 
                security.addEventLog(eventEntry, dataset);
            }
        } catch (Exception e) {
            JsfUtil.addFatalMessage(" Already Applied For this vacancy");
        }
    }

    public boolean validateEducLevel() {
        boolean educLevel = false;
        for (int i = 0; i < hrEmpEducationsList.size(); i++) {
            HrEmpEducations empEducLevel = hrEmpEducationsList.get(i);
            for (int j = 0; j < hrJobEducQualificationsesList.size(); j++) {
                HrJobEducQualifications vacanceEducLevel = hrJobEducQualificationsesList.get(j);
                if (empEducLevel.getEducLevelId().equals(vacanceEducLevel.getEducLevelId())) {
                    educLevel = true;
                    i = hrEmpEducationsList.size() + 1;
                }

            }

        }
        return educLevel;

    }

    public boolean validateEducType() {
        boolean educType = false;
        for (int i = 0; i < hrEmpEducationsList.size(); i++) {
            HrEmpEducations empEducLevel = hrEmpEducationsList.get(i);
            for (int j = 0; j < hrJobEducQualificationsesList.size(); j++) {
                HrJobEducQualifications hrJobEducQualifications = hrJobEducQualificationsesList.get(j);
                if (empEducLevel.getEducTypeId().equals(hrJobEducQualifications.getEducQualId())) {
                    educType = true;
                    i = hrEmpEducationsList.size() + 1;
                }

            }

        }
        return educType;

    }

    public void clear() {
        // promotionApply = null;
        hrEmployees = null;
//        hrAdvertisements = null;
//        advertisedJobs = null;
    }

    public void activeVacancy_processValueChange(ValueChangeEvent event) {
        System.out.println(" ---event-" + event.getNewValue().toString());
        try {
              hrAdvertisements = null;
        if (null != event.getNewValue()) {
            advertisedJobs = new ArrayList<>();
            int advertId = Integer.parseInt(event.getNewValue().toString());
            hrAdvertisements = hrAdvertisementBeanLocal.findByAdvertimnetId(advertId);
            System.out.println(" ---hrAdvertisements--" + hrAdvertisements);
            advertisedJobs = promotionApplyBeanLocal.readAdverJobsQualification(advertId);
            System.out.println("---advertisedJobs---" + advertisedJobs);
            for (int i = 0; i < advertisedJobs.size(); i++) {
                HrAdvertisedJobs get = advertisedJobs.get(i);
                System.out.println("---advertisedJobs---" + get.getJobId().getJobTitle());
            }

        }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    String recruitmentReqstID = "";

    public void populateVacancy(SelectEvent events) {
        advertisedJobesEntity = (HrAdvertisedJobs) events.getObject();
        recruitmentReqstID = advertisedJobesEntity.getRecruitRequestId();
        jobTypes = advertisedJobesEntity.getJobId();
        hrJobEducQualificationsesList = hrJobEducQualificationsBeanLocal.getByJobID(jobTypes);
        renderPnlManPage = false;
        renderPnlCreateAdditional = true;
        createOrSearchBundle = "Search";
        // advertisedJobesEntity = (HrAdvertisedJobs) events.getObject();
        System.out.println("--+recruitmentReqstID" + recruitmentReqstID);
        if (recruitmentReqstID != null) {

            recruitmentRequests = hrRecruitmentRequestsBeanLocal.getByRecruitmentReqstID(recruitmentReqstID);
            System.out.println("--+recruitmentRequests  " + recruitmentRequests);
            if (recruitmentRequests != null) {
                hrDepartments = recruitmentRequests.getDeptId();
                System.out.println("--+hrDepartments  " + hrDepartments);
            } else {
                JsfUtil.addErrorMessage("Recruitment Request Obj value is null");
            }
        } else {
            JsfUtil.addErrorMessage("Recruitment Request ID value is null");
        }
    }

    public void createNewAdditionalAmount() {
        if (createOrSearchBundle.equals("Search")) {
            renderPnlCreateAdditional = false;
            renderPnlManPage = true;
            setIcone("ui-icon-document");
        }
    }
}
