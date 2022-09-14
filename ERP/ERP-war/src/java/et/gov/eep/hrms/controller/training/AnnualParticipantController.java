/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.controller.training;

import java.io.Serializable;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.ArrayDataModel;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import org.primefaces.event.SelectEvent;
import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.hrms.businessLogic.employee.HrEmployeeBeanLocal;
import et.gov.eep.hrms.businessLogic.training.AnnualParticipantBeanLocal;
import et.gov.eep.hrms.entity.employee.HrEmployees;
import et.gov.eep.hrms.entity.organization.HrDepartments;
import et.gov.eep.hrms.entity.organization.HrJobTypes;
import et.gov.eep.hrms.entity.training.HrTdAnnualTraParticipants;
import et.gov.eep.hrms.entity.training.HrTdAnnualTrainingNeeds;
import et.gov.eep.hrms.entity.training.HrTdCourses;

/**
 *
 * @author Ob
 */
@Named(value = "annualParticipantController")
@ViewScoped
public class AnnualParticipantController implements Serializable {

    @Inject
    HrTdAnnualTraParticipants annualTrainingParticipants;
    @Inject
    HrTdAnnualTrainingNeeds hrTdAnnualTrainingNeeds;
    @Inject
    HrTdCourses hrTdCourses;
    @Inject
    HrEmployees hrEmployee;
    @Inject
    HrDepartments hrDepartment;
    @Inject
    HrJobTypes hrJobType;

    @EJB
    private AnnualParticipantBeanLocal annualParticipantBeanLocal;
    @EJB
    private HrEmployeeBeanLocal employeeBean;

    private List<HrTdAnnualTrainingNeeds> corseList;
    List<HrTdAnnualTrainingNeeds> annualTrainingNeedList = new ArrayList<>();
    DataModel<HrTdAnnualTraParticipants> participantEmpDataModel = new ListDataModel<>();
    private String disableBtn = "false";
    private boolean renderSearch = true;
    private boolean renderNew = false;
    private int updateStatus = 0;
    private String SaveOrUpdateButton = "Save";
    String preparedDate;
    Set<String> checkEmp = new HashSet<>();

    public AnnualParticipantController() {
    }

    @PostConstruct
    public void init() {
        annualTrainingNeedList = annualParticipantBeanLocal.findApproved();
    }

    public void newPage() {
        renderSearch = false;
        renderNew = true;
//        hrTdAnnualNeedRequests = null;
//        hrDepartments = null;
//        hrTdAnnualTrainingNeedsDataModel = null;
        disableBtn = "false";
        updateStatus = 0;
        SaveOrUpdateButton = "Save";
    }

    public void searchPage() {
        renderSearch = true;
        renderNew = false;
//        srcAnnualNeedRequests = null;
//        hrTdAnnualNeedRequestsDataModel = null;
    }

    //<editor-fold defaultstate="collapsed" desc="getter setter">
    public HrTdAnnualTraParticipants getAnnualTrainingParticipants() {
        if (annualTrainingParticipants == null) {
            annualTrainingParticipants = new HrTdAnnualTraParticipants();
        }
        return annualTrainingParticipants;
    }

    public void setAnnualTrainingParticipants(HrTdAnnualTraParticipants annualTrainingParticipants) {
        this.annualTrainingParticipants = annualTrainingParticipants;
    }

    public HrTdAnnualTrainingNeeds getHrTdAnnualTrainingNeeds() {
        if (hrTdAnnualTrainingNeeds == null) {
            hrTdAnnualTrainingNeeds = new HrTdAnnualTrainingNeeds();
        }
        return hrTdAnnualTrainingNeeds;
    }

    public void setHrTdAnnualTrainingNeeds(HrTdAnnualTrainingNeeds hrTdAnnualTrainingNeeds) {
        this.hrTdAnnualTrainingNeeds = hrTdAnnualTrainingNeeds;
    }

    public HrTdCourses getHrTdCourses() {
        if (hrTdCourses == null) {
            hrTdCourses = new HrTdCourses();
        }
        return hrTdCourses;
    }

    public void setHrTdCourses(HrTdCourses hrTdCourses) {
        this.hrTdCourses = hrTdCourses;
    }

    public HrEmployees getHrEmployee() {
        if (hrEmployee == null) {
            hrEmployee = new HrEmployees();
        }
        return hrEmployee;
    }

    public void setHrEmployee(HrEmployees hrEmployee) {
        this.hrEmployee = hrEmployee;
    }

    public HrDepartments getHrDepartment() {
        if (hrDepartment == null) {
            hrDepartment = new HrDepartments();
        }
        return hrDepartment;
    }

    public void setHrDepartment(HrDepartments hrDepartment) {
        this.hrDepartment = hrDepartment;
    }

    public HrJobTypes getHrJobType() {
        if (hrJobType == null) {
            hrJobType = new HrJobTypes();
        }
        return hrJobType;
    }

    public void setHrJobType(HrJobTypes hrJobType) {
        this.hrJobType = hrJobType;
    }

    public List<HrTdAnnualTrainingNeeds> getCorseList() {
        corseList = annualParticipantBeanLocal.findApproved();
        return corseList;
    }

    public void setCorseList(List<HrTdAnnualTrainingNeeds> corseList) {
        this.corseList = corseList;
    }

    public List<HrTdAnnualTrainingNeeds> getAnnualTrainingNeedList() {
        return annualTrainingNeedList;
    }

    public void setAnnualTrainingNeedList(List<HrTdAnnualTrainingNeeds> annualTrainingNeedList) {
        this.annualTrainingNeedList = annualTrainingNeedList;
    }

    public DataModel<HrTdAnnualTraParticipants> getParticipantEmpDataModel() {
        if (participantEmpDataModel == null) {
            participantEmpDataModel = new ArrayDataModel<>();
        }
        return participantEmpDataModel;
    }

    public void setParticipantEmpDataModel(DataModel<HrTdAnnualTraParticipants> participantEmpDataModel) {
        this.participantEmpDataModel = participantEmpDataModel;
    }

    public String getDisableBtn() {
        return disableBtn;
    }

    public void setDisableBtn(String disableBtn) {
        this.disableBtn = disableBtn;
    }

    public boolean isRenderSearch() {
        return renderSearch;
    }

    public void setRenderSearch(boolean renderSearch) {
        this.renderSearch = renderSearch;
    }

    public boolean isRenderNew() {
        return renderNew;
    }

    public void setRenderNew(boolean renderNew) {
        this.renderNew = renderNew;
    }

    public String getSaveOrUpdateButton() {
        return SaveOrUpdateButton;
    }

    public void setSaveOrUpdateButton(String SaveOrUpdateButton) {
        this.SaveOrUpdateButton = SaveOrUpdateButton;
    }

    public String getPreparedDate() {
        return preparedDate;
    }

    public void setPreparedDate(String preparedDate) {
        this.preparedDate = preparedDate;
    }

    public Set<String> getCheckEmp() {
        return checkEmp;
    }

    public void setCheckEmp(Set<String> checkEmp) {
        this.checkEmp = checkEmp;
    }
    //</editor-fold>

    public void findCourse(ValueChangeEvent event) {
        try {
            if (event.getNewValue().toString() != null) {
                hrTdCourses = (HrTdCourses) event.getNewValue();
                hrTdAnnualTrainingNeeds = annualParticipantBeanLocal.findByCourse(hrTdCourses);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void course_vlc(ValueChangeEvent event) {
        try {
            if (null != event.getNewValue().toString()) {
                String selectedCourse[] = event.getNewValue().toString().split(" -- ");
                int id = Integer.parseInt(selectedCourse[0]);
                hrTdAnnualTrainingNeeds.setId(id);
                hrTdAnnualTrainingNeeds = annualParticipantBeanLocal.getSelectedRequest(id);
                hrTdAnnualTrainingNeeds.setId(hrTdAnnualTrainingNeeds.getId());
                hrTdAnnualTrainingNeeds = annualParticipantBeanLocal.getSelectedRequest(hrTdAnnualTrainingNeeds.getId());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void populateCourse(SelectEvent events) {
//        hrTdAnnualTrainingNeeds = null;
        hrTdAnnualTrainingNeeds = (HrTdAnnualTrainingNeeds) events.getObject();
        hrTdAnnualTrainingNeeds.setId(hrTdAnnualTrainingNeeds.getId());
        hrTdAnnualTrainingNeeds = annualParticipantBeanLocal.getSelectedRequest(hrTdAnnualTrainingNeeds.getId());
        annualTrainingParticipants.setAnnTraNeedId(hrTdAnnualTrainingNeeds);
    }

    public static boolean isStringNumeric(String str) {
        DecimalFormatSymbols currentLocaleSymbols = DecimalFormatSymbols.getInstance();
        char localeMinusSign = currentLocaleSymbols.getMinusSign();

        if (!Character.isDigit(str.charAt(0)) && str.charAt(0) != localeMinusSign) {
            return false;
        }

        boolean isDecimalSeparatorFound = false;
        char localeDecimalSeparator = currentLocaleSymbols.getDecimalSeparator();

        for (char c : str.substring(1).toCharArray()) {
            if (!Character.isDigit(c)) {
                if (c == localeDecimalSeparator && !isDecimalSeparatorFound) {
                    isDecimalSeparatorFound = true;
                    continue;
                }
                return false;
            }
        }
        return true;
    }

    public ArrayList<HrEmployees> searchEmployee(String employeeName) {
        hrEmployee = new HrEmployees();
        ArrayList<HrEmployees> registeredEmployee = null;
        if (hrEmployee.getFirstName() != null) {
            hrEmployee.setFirstName(employeeName);
            registeredEmployee = annualParticipantBeanLocal.findByEmpName(hrEmployee);
            return registeredEmployee;
        } else {
            hrEmployee.setEmpId(employeeName);
            registeredEmployee = annualParticipantBeanLocal.findByEmpId(hrEmployee);
        }
        return registeredEmployee;
    }

    public ArrayList<HrEmployees> searchByEmpId(String EmpId) {
        ArrayList<HrEmployees> employees = new ArrayList<>();
        hrEmployee.setEmpId(EmpId);
        employees = employeeBean.SearchByEmpId(hrEmployee);
        return employees;
    }

    public void getByEmpId(SelectEvent event) {
        hrEmployee = (HrEmployees) event.getObject();
        annualTrainingParticipants.setEmpId(hrEmployee);
    }

    public ArrayList<HrEmployees> searchByName(String employee) {
        ArrayList<HrEmployees> emp = null;
        hrEmployee.setFirstName(employee);
        emp = employeeBean.SearchByFname(hrEmployee);
        return emp;
    }

    public void getByEmpName(SelectEvent event) {
        try {
            hrEmployee.setFirstName(event.getObject().toString());
            hrEmployee = employeeBean.getByFirstName(hrEmployee);
            annualTrainingParticipants.setEmpId(hrEmployee);
        } catch (Exception ex) {
            System.err.println("Exception");
        }
    }

    public void recreateParticipant() {
        participantEmpDataModel = null;
        participantEmpDataModel = new ListDataModel(new ArrayList<>(hrTdAnnualTrainingNeeds.getHrTdAnulTraPaticptsList()));
    }

    public void addEmp() {
//        if (checkEmp.contains(annualTrainingParticipants.getEmpId().getId().toString())) {
//            JsfUtil.addFatalMessage("Duplicate Entry. Try again!");
//        }
//        else {
        hrTdAnnualTrainingNeeds.addParticipantEmp(annualTrainingParticipants);
//            checkEmp.add(annualTrainingParticipants.getEmpId().getId().toString());
        recreateParticipant();
        clearEmp();
//        }
    }
    int selectedRow;

    public void populateEmp(SelectEvent event) {
        selectedRow = getParticipantEmpDataModel().getRowIndex();
        annualTrainingParticipants = (HrTdAnnualTraParticipants) event.getObject();
        hrEmployee = annualTrainingParticipants.getEmpId();
    }

    public void clearEmp() {
        hrEmployee = null;
        annualTrainingParticipants = null;
    }

    public void save() {
//        if (participantEmpDataModel.getRowCount() <= 0) {
//            JsfUtil.addFatalMessage("Data table shoud be filled");
//        } 
//        else {
        try {
            hrTdAnnualTrainingNeeds.setStatus(0);
//                    hrTdAnnualNeedRequests.setPreparedOn(preparedDate);
            annualParticipantBeanLocal.edit(hrTdAnnualTrainingNeeds);
            JsfUtil.addSuccessMessage("Successfully Saved");
            clearPage();
        } catch (Exception ex) {
            ex.printStackTrace();
            JsfUtil.addFatalMessage("Error occurs while saving");
        }
//        }
    }

    public void clearPage() {
        hrTdAnnualTrainingNeeds = null;
        participantEmpDataModel = null;
    }

}
