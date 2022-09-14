package et.gov.eep.hrms.controller.insure;

//import static com.sun.xml.ws.spi.db.BindingContextFactory.LOGGER;
import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.hrms.businessLogic.insure.HrInsuranceDiagonasticResultBeanLocal;
import et.gov.eep.hrms.businessLogic.insure.InsuranceInjuredEmployeesLocal;
import et.gov.eep.hrms.businessLogic.insure.UploadBeanLocal;
import et.gov.eep.hrms.entity.employee.HrEmployees;
import et.gov.eep.hrms.entity.insurance.HrInsurance;
import et.gov.eep.hrms.entity.insurance.HrInsuranceDiagnosisResult;
import et.gov.eep.hrms.entity.insurance.HrInsuranceDiagnosisUpload;
import et.gov.eep.hrms.entity.insurance.HrInsuranceInjuredEmployee;
import et.gov.eep.hrms.entity.insurance.HrLuInsuranceBranches;
import et.gov.eep.hrms.entity.insurance.HrLuInsurances;
import et.gov.eep.hrms.entity.organization.HrJobTypes;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.ArrayDataModel;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import org.primefaces.event.SelectEvent;
import securityBean.Constants;
import securityBean.SessionBean;
import webService.AAA;
import webService.EventEntry;
import webService.IAdministration;

/**
 *
 * @author Me
 */
@Named(value = "diagnosisResultController")
@ViewScoped
public class DiagnosisResultController implements Serializable {
    //<editor-fold defaultstate="collapsed" desc="Variables & Objects">

    @Inject
    HrInsuranceDiagnosisResult hrInsuranceDiagnosisResult;

    @Inject
    HrInsuranceInjuredEmployee hrInsuranceInjuredEmployee;

    @EJB
    HrInsuranceDiagonasticResultBeanLocal hrInsuranceDiagonasticResultBeanLocal;

    @EJB
    InsuranceInjuredEmployeesLocal insuranceInjuredEmployeesLocal;

    @EJB
    UploadBeanLocal uploadBeanLocal;

    @Inject
    HrLuInsurances hrLuInsurances;

    @Inject
    HrEmployees hrEmployees;

    @Inject
    HrJobTypes hrJobTypes;

    @Inject
    HrLuInsuranceBranches hrLuInsuranceBranches;

    @Inject
    HrInsurance hrInsurance;

    @Inject
    SessionBean sessionBeanLocal;

    private String tabToggle = "";
    int update = 0;
    private boolean renderPnlCreateAdditional = true;
    private String addorUpdate = "Add";
    private String SaveOrUpdateButton = "Save";
    boolean btnaddvisibility = true;
    private String createOrSearchBundle = "";
    private String headerTitle = "Injured Employee List";
    private String headerTitle1 = "Search...";
    private String saveorUpdateBundle = "Update";
    private String icone = "ui-icon-plus";
    private boolean disableBtnCreate;
    private boolean renderPnlCreateGatePass = false;
    private boolean renderPnlManPage = true;
    private boolean renderpnlContrat = false;
    DataModel<HrInsuranceDiagnosisResult> insuranceDataModel;
    List<HrInsuranceInjuredEmployee> InsuranceInjuedList = new ArrayList<>();
    List<HrInsuranceDiagnosisResult> DiagnoasisList = new ArrayList<>();
    List<HrInsurance> insuranceNameList = new ArrayList<>();
    List<HrLuInsuranceBranches> HrLuInsuranceBrancheslist = new ArrayList<>();
    private String choosePermanet = "true";
    private String chooseDailyLabour = "false";
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Postconstruct">

    @PostConstruct
    public void init() {
        searchstatuzero();
        DiagnoasisList = hrInsuranceDiagonasticResultBeanLocal.findall();
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="getter setter">

    public List<HrInsurance> getInsuranceNameList() {
        return insuranceNameList;
    }

    public void setInsuranceNameList(List<HrInsurance> insuranceNameList) {
        this.insuranceNameList = insuranceNameList;
    }

    public List<HrInsuranceDiagnosisResult> getDiagnoasisList() {
        return DiagnoasisList;
    }

    public void setDiagnoasisList(List<HrInsuranceDiagnosisResult> DiagnoasisList) {
        this.DiagnoasisList = DiagnoasisList;
    }

    public String getTabToggle() {
        return tabToggle;
    }

    public void setTabToggle(String tabToggle) {
        this.tabToggle = tabToggle;
    }

    public int getUpdate() {
        return update;
    }

    public void setUpdate(int update) {
        this.update = update;
    }

    public boolean isRenderPnlCreateAdditional() {
        return renderPnlCreateAdditional;
    }

    public void setRenderPnlCreateAdditional(boolean renderPnlCreateAdditional) {
        this.renderPnlCreateAdditional = renderPnlCreateAdditional;
    }

    public String getAddorUpdate() {
        return addorUpdate;
    }

    public void setAddorUpdate(String addorUpdate) {
        this.addorUpdate = addorUpdate;
    }

    public String getSaveOrUpdateButton() {
        return SaveOrUpdateButton;
    }

    public void setSaveOrUpdateButton(String SaveOrUpdateButton) {
        this.SaveOrUpdateButton = SaveOrUpdateButton;
    }

    public String getHeaderTitle() {
        return headerTitle;
    }

    public void setHeaderTitle(String headerTitle) {
        this.headerTitle = headerTitle;
    }

    public String getHeaderTitle1() {
        return headerTitle1;
    }

    public void setHeaderTitle1(String headerTitle1) {
        this.headerTitle1 = headerTitle1;
    }

    public String getSaveorUpdateBundle() {
        return saveorUpdateBundle;
    }

    public void setSaveorUpdateBundle(String saveorUpdateBundle) {
        this.saveorUpdateBundle = saveorUpdateBundle;
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

    public boolean isRenderPnlCreateGatePass() {
        return renderPnlCreateGatePass;
    }

    public void setRenderPnlCreateGatePass(boolean renderPnlCreateGatePass) {
        this.renderPnlCreateGatePass = renderPnlCreateGatePass;
    }

    public boolean isRenderPnlManPage() {
        return renderPnlManPage;
    }

    public void setRenderPnlManPage(boolean renderPnlManPage) {
        this.renderPnlManPage = renderPnlManPage;
    }

    public boolean getRenderpnlContrat() {
        return renderpnlContrat;
    }

    public void setRenderpnlContrat(boolean renderpnlContrat) {
        this.renderpnlContrat = renderpnlContrat;
    }

    public String getCreateOrSearchBundle() {
        return createOrSearchBundle;
    }

    public void setCreateOrSearchBundle(String createOrSearchBundle) {
        this.createOrSearchBundle = createOrSearchBundle;
    }

    public HrInsuranceDiagnosisResult getHrInsuranceDiagnosisResult() {
        if (hrInsuranceDiagnosisResult == null) {
            hrInsuranceDiagnosisResult = new HrInsuranceDiagnosisResult();
        }
        return hrInsuranceDiagnosisResult;
    }

    public void setHrInsuranceDiagnosisResult(HrInsuranceDiagnosisResult hrInsuranceDiagnosisResult) {
        this.hrInsuranceDiagnosisResult = hrInsuranceDiagnosisResult;
    }

    public HrInsuranceInjuredEmployee getHrInsuranceInjuredEmployee() {
        if (hrInsuranceInjuredEmployee == null) {
            hrInsuranceInjuredEmployee = new HrInsuranceInjuredEmployee();
        }
        return hrInsuranceInjuredEmployee;
    }

    public void setHrInsuranceInjuredEmployee(HrInsuranceInjuredEmployee hrInsuranceInjuredEmployee) {
        this.hrInsuranceInjuredEmployee = hrInsuranceInjuredEmployee;
    }

    public HrLuInsurances getHrLuInsurances() {
        if (hrLuInsurances == null) {
            hrLuInsurances = new HrLuInsurances();
        }
        return hrLuInsurances;
    }

    public void setHrLuInsurances(HrLuInsurances hrLuInsurances) {
        this.hrLuInsurances = hrLuInsurances;
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

    public HrJobTypes getHrJobTypes() {
        if (hrJobTypes == null) {
            hrJobTypes = new HrJobTypes();
        }
        return hrJobTypes;
    }

    public void setHrJobTypes(HrJobTypes hrJobTypes) {
        this.hrJobTypes = hrJobTypes;
    }

    public HrLuInsuranceBranches getHrLuInsuranceBranches() {
        if (hrLuInsuranceBranches == null) {
            hrLuInsuranceBranches = new HrLuInsuranceBranches();
        }
        return hrLuInsuranceBranches;
    }

    public void setHrLuInsuranceBranches(HrLuInsuranceBranches hrLuInsuranceBranches) {
        this.hrLuInsuranceBranches = hrLuInsuranceBranches;
    }

    public boolean isBtnaddvisibility() {
        return btnaddvisibility;
    }

    public void setBtnaddvisibility(boolean btnaddvisibility) {
        this.btnaddvisibility = btnaddvisibility;
    }

    public DataModel<HrInsuranceDiagnosisResult> getInsuranceDataModel() {
        if (insuranceDataModel == null) {
            insuranceDataModel = new ArrayDataModel<>();
        }
        return insuranceDataModel;
    }

    public void setInsuranceDataModel(DataModel<HrInsuranceDiagnosisResult> insuranceDataModel) {
        this.insuranceDataModel = insuranceDataModel;
    }

    public List<HrInsuranceInjuredEmployee> getInsuranceInjuedList() {
        return InsuranceInjuedList;
    }

    public void setInsuranceInjuedList(List<HrInsuranceInjuredEmployee> InsuranceInjuedList) {
        this.InsuranceInjuedList = InsuranceInjuedList;
    }

    public HrInsurance getHrInsurance() {
        if (hrInsurance == null) {
            hrInsurance = new HrInsurance();
        }
        return hrInsurance;
    }

    public void setHrInsurance(HrInsurance HrInsurance) {
        this.hrInsurance = HrInsurance;
    }

    //</editor-fold> 
    
    //<editor-fold defaultstate="collapsed" desc="Search & populate Methods">
    public void searchPage() {
        renderPnlCreateGatePass = false;
        renderPnlManPage = true;
        headerTitle = "Injured Employee";
        createOrSearchBundle = "New";
    }

    public void populate(SelectEvent events) {
        hrInsuranceInjuredEmployee = null;
        hrInsuranceInjuredEmployee = (HrInsuranceInjuredEmployee) events.getObject();
        hrInsuranceInjuredEmployee.setId(hrInsuranceInjuredEmployee.getId());
        hrInsuranceInjuredEmployee = insuranceInjuredEmployeesLocal.getSelectedRequest(hrInsuranceInjuredEmployee.getId());
        disableBtnCreate = false;
        renderPnlCreateGatePass = true;
        renderPnlManPage = false;
        renderPnlCreateAdditional = true;
        createOrSearchBundle = "Search";
        headerTitle = "SuccessionPlanning";
        setIcone("ui-icon-search");
        update = 0;
        saveorUpdateBundle = "Save";
        if (hrInsuranceInjuredEmployee.getType().equals("Permanent/contract")) {
            choosePermanet = "true";
            chooseDailyLabour = "false";
        } else {
            choosePermanet = "false";
            chooseDailyLabour = "true";
        }
    }

    public void populateNotify(SelectEvent event) {
        hrInsuranceInjuredEmployee = (HrInsuranceInjuredEmployee) event.getObject();
        hrLuInsurances = hrInsuranceInjuredEmployee.getInsuranceId().getInsuranceId();
        hrLuInsuranceBranches = hrInsuranceInjuredEmployee.getInsuranceId().getBranchId();
        disableBtnCreate = false;
        renderPnlCreateGatePass = true;
        renderPnlManPage = false;
        renderPnlCreateAdditional = true;
        createOrSearchBundle = "Search";
        headerTitle = "SuccessionPlanning";
        setIcone("ui-icon-search");
        update = 0;
        saveorUpdateBundle = "Save";
        if (hrInsuranceInjuredEmployee.getType().equals("Permanent/contract")) {
            choosePermanet = "true";
            chooseDailyLabour = "false";
        } else {
            choosePermanet = "false";
            chooseDailyLabour = "true";
        }
    }

    public void populateAll(SelectEvent events) {
        hrInsuranceDiagnosisResult = (HrInsuranceDiagnosisResult) events.getObject();
        hrInsuranceDiagnosisResult.setId(hrInsuranceDiagnosisResult.getId());
        hrInsuranceInjuredEmployee = hrInsuranceDiagnosisResult.getHrInsuranceInjuredEmployee();
        hrEmployees = hrInsuranceInjuredEmployee.getEmpId();
        hrInsuranceDiagnosisResult.setHrInsuranceInjuredEmployee(hrInsuranceInjuredEmployee);
        hrInsuranceDiagnosisResult = hrInsuranceDiagonasticResultBeanLocal.getSelectedPlanRequest(hrInsuranceDiagnosisResult.getId());
        disableBtnCreate = false;
        renderPnlCreateGatePass = true;
        renderPnlManPage = false;
        renderPnlCreateAdditional = true;
        createOrSearchBundle = "New";
        headerTitle = "SuccessionPlanning";
        update = 1;
        saveorUpdateBundle = "Update";

        if (hrInsuranceInjuredEmployee.getType().equals("Permanent/contract")) {
            choosePermanet = "true";
            chooseDailyLabour = "false";
        } else {
            choosePermanet = "false";
            chooseDailyLabour = "true";
        }
    }

    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Main Methods">
    public void saveDiagnosisResult() {
        try {
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(sessionBeanLocal.getUserName(), "saveDiagnosisResult", dataset)) {
                if (hrInsuranceDiagnosisResult.getIsDisease().intValue() > 100) {
                    JsfUtil.addFatalMessage("Precentage Of Damage Can't Greater than 100");
                } else {
                    hrInsuranceDiagnosisResult.setStatus(Constants.PREPARE_VALUE);
                    hrInsuranceInjuredEmployee.setStatus(String.valueOf(Constants.CHECK_APPROVE_VALUE));
                    insuranceInjuredEmployeesLocal.saveorupdate(hrInsuranceInjuredEmployee);
                    try {
                        if (update == 0) {
                            if (hrInsuranceInjuredEmployee.getEmpId() == null && hrInsuranceInjuredEmployee.getFullName() == null) {
                                JsfUtil.addFatalMessage("please select Employee!!!");
                            } else {
                                hrInsuranceDiagnosisResult.setHrInsuranceInjuredEmployee(hrInsuranceInjuredEmployee);
                                hrInsuranceDiagonasticResultBeanLocal.create(hrInsuranceDiagnosisResult);
                                JsfUtil.addSuccessMessage("Successfully Saved");
                                resetDiagnosisResult();
                            }
                        } else {
                            hrInsuranceDiagonasticResultBeanLocal.edit(hrInsuranceDiagnosisResult);
                            JsfUtil.addSuccessMessage("Successfully Updated");
                            resetDiagnosisResult();
                        }
                    } catch (Exception ex) {
                        JsfUtil.addFatalMessage("Error occured while Saving.");
                    }
                }
            } else {
                JsfUtil.addFatalMessage("You don't have appropriate permission, please contact the system administrator.");
                EventEntry eventEntry = new EventEntry();
                eventEntry.setSessionId(sessionBeanLocal.getSessionID());
                eventEntry.setUserId(sessionBeanLocal.getUserId());
                QName qualifiedName = new QName("", "project");
                JAXBElement<String> userName = new JAXBElement<String>(qualifiedName, String.class, null, sessionBeanLocal.getUserName());
                eventEntry.setUserLogin(userName);
                JAXBElement<String> module = new JAXBElement<String>(qualifiedName, String.class, null, "HRMS");
                eventEntry.setModule(module);
                JAXBElement<String> program = new JAXBElement<String>(qualifiedName, String.class, null, "saveDiagnosisResult");
                eventEntry.setProgram(program);
                security.addEventLog(eventEntry, dataset);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void resetDiagnosisResult() {
        hrInsuranceDiagnosisResult = new HrInsuranceDiagnosisResult();
        hrInsuranceInjuredEmployee = new HrInsuranceInjuredEmployee();
        hrLuInsurances = new HrLuInsurances();
        hrLuInsuranceBranches = new HrLuInsuranceBranches();
    }

    public void findAll() {
        try {
            DiagnoasisList = hrInsuranceDiagonasticResultBeanLocal.findall(hrInsuranceDiagnosisResult);
            insuranceDataModel = new ListDataModel(new ArrayList(DiagnoasisList));
            DiagnoasisList = hrInsuranceDiagonasticResultBeanLocal.findall(hrInsuranceDiagnosisResult);
            insuranceDataModel = new ListDataModel(new ArrayList(DiagnoasisList));
        } catch (Exception ex) {
            throw ex;
        }
    }

    public void injuredNameValuechange(ValueChangeEvent event) {
        if (!event.getNewValue().toString().isEmpty()) {
            hrInsuranceInjuredEmployee.setId(Integer.parseInt(event.getNewValue().toString()));
            hrInsuranceInjuredEmployee = hrInsuranceDiagonasticResultBeanLocal.findById(hrInsuranceInjuredEmployee);
            hrLuInsurances = hrInsuranceInjuredEmployee.getInsuranceId().getInsuranceId();
            hrLuInsuranceBranches = hrInsuranceInjuredEmployee.getInsuranceId().getBranchId();
        }
    }

    public String getChoosePermanet() {
        return choosePermanet;
    }

    public void setChoosePermanet(String choosePermanet) {
        this.choosePermanet = choosePermanet;
    }

    public String getChooseDailyLabour() {
        return chooseDailyLabour;
    }

    public void setChooseDailyLabour(String chooseDailyLabour) {
        this.chooseDailyLabour = chooseDailyLabour;
    }

    public void addDiagnosisResult() {
        try {
            DiagnoasisList.add(hrInsuranceDiagnosisResult);
        } catch (Exception e) {
            throw e;
        }
        JsfUtil.addFatalMessage("error occured");
    }

    public void searchstatuzero() {
        String statusinjury = "0";
        InsuranceInjuedList = hrInsuranceDiagonasticResultBeanLocal.findInjuredEmp(hrInsuranceInjuredEmployee, statusinjury);
    }
    //</editor-fold>
}
