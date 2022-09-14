/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.controller.salarydelegation;

import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.hrms.businessLogic.salarydelegation.SalaryDelegationApproveBeanLocal;
import et.gov.eep.hrms.entity.employee.HrEmpFamilies;
import et.gov.eep.hrms.entity.employee.HrEmployees;
import et.gov.eep.hrms.entity.lookup.HrLuTitles;
import et.gov.eep.hrms.entity.organization.HrDepartments;
import et.gov.eep.hrms.entity.organization.HrJobTypes;
import et.gov.eep.hrms.entity.organization.HrSalaryScales;
import et.gov.eep.hrms.entity.salarydelegation.HrSalaryDelegationRequest;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author INSA
 */
@Named(value = "salaryDelegationApproveController")
@ViewScoped
public class SalaryDelegationApproveController implements Serializable {

    @EJB
    SalaryDelegationApproveBeanLocal salaryDelegationApproveBeanLocal;
    @Inject
    HrSalaryDelegationRequest hrSalaryDelegationRequest;
    @Inject
    HrEmployees hrEmployees;
    @Inject
    HrEmployees empdelegator;
    @Inject
    HrEmpFamilies hrEmpFamilies;
    @Inject
    HrLuTitles hrLuTitles;
    @Inject
    HrDepartments hrDepartments;
    @Inject
    HrSalaryScales hrSalaryScales;
    @Inject
    HrJobTypes hrJobTypes;

    int approveType = 0;
    private String createOrSearchBundle = "New";
    private String icone = "ui-icon-document";
    private boolean disableBtnCreate;
    private boolean chooseCreatePage = false;
    private boolean chooseMainPage = true;
    DataModel<HrSalaryDelegationRequest> requestsListDataModel;
    private List<HrSalaryDelegationRequest> selectedRequest;
    List<SelectItem> filterByStatus = new ArrayList<>();
    String delegator;

    private String chooseEEPemp = "false";
    private String chooseFamily = "false";
    private String chooseOtheremp = "true";
    private String choosePrivateemp = "false";

    private String chooseAllowance;
    private String chooseMonthlySalaryandAllowance;
    private String chooseMonthlySalary = "true";
    private String chooseOtherAmount = chooseMonthlySalaryandAllowance = chooseAllowance = "false";

    public SalaryDelegationApproveController() {
    }

    //<editor-fold defaultstate="collapsed" desc="Getter and Setter"> 
    public HrSalaryDelegationRequest getHrSalaryDelegationRequest() {
        return hrSalaryDelegationRequest;
    }

    public void setHrSalaryDelegationRequest(HrSalaryDelegationRequest hrSalaryDelegationRequest) {
        this.hrSalaryDelegationRequest = hrSalaryDelegationRequest;
    }

    public HrEmployees getHrEmployees() {
        return hrEmployees;
    }

    public void setHrEmployees(HrEmployees hrEmployees) {
        this.hrEmployees = hrEmployees;
    }

    public HrEmployees getEmpdelegator() {
        return empdelegator;
    }

    public void setEmpdelegator(HrEmployees empdelegator) {
        this.empdelegator = empdelegator;
    }

    public HrEmpFamilies getHrEmpFamilies() {
        return hrEmpFamilies;
    }

    public void setHrEmpFamilies(HrEmpFamilies hrEmpFamilies) {
        this.hrEmpFamilies = hrEmpFamilies;
    }

    public HrLuTitles getHrLuTitles() {
        return hrLuTitles;
    }

    public void setHrLuTitles(HrLuTitles hrLuTitles) {
        this.hrLuTitles = hrLuTitles;
    }

    public HrDepartments getHrDepartments() {
        return hrDepartments;
    }

    public void setHrDepartments(HrDepartments hrDepartments) {
        this.hrDepartments = hrDepartments;
    }

    public HrSalaryScales getHrSalaryScales() {
        return hrSalaryScales;
    }

    public void setHrSalaryScales(HrSalaryScales hrSalaryScales) {
        this.hrSalaryScales = hrSalaryScales;
    }

    public HrJobTypes getHrJobTypes() {
        return hrJobTypes;
    }

    public void setHrJobTypes(HrJobTypes hrJobTypes) {
        this.hrJobTypes = hrJobTypes;
    }

    public int getApproveType() {
        return approveType;
    }

    public void setApproveType(int approveType) {
        this.approveType = approveType;
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

    public DataModel<HrSalaryDelegationRequest> getRequestsListDataModel() {
        return requestsListDataModel = new ListDataModel(salaryDelegationApproveBeanLocal.loadSalaryDelegationList(status));
    }

    public void setRequestsListDataModel(DataModel<HrSalaryDelegationRequest> requestsListDataModel) {
        this.requestsListDataModel = requestsListDataModel;
    }

    public List<HrSalaryDelegationRequest> getSelectedRequest() {
        return selectedRequest;
    }

    public void setSelectedRequest(List<HrSalaryDelegationRequest> selectedRequest) {
        this.selectedRequest = selectedRequest;
    }

    public List<SelectItem> getFilterByStatus() {
        return salaryDelegationApproveBeanLocal.filterByStatus();
    }

    public void setFilterByStatus(List<SelectItem> filterByStatus) {
        this.filterByStatus = filterByStatus;
    }

    public String getDelegator() {
        return delegator;
    }

    public void setDelegator(String delegator) {
        this.delegator = delegator;
    }

    public String getChooseEEPemp() {
        return chooseEEPemp;
    }

    public void setChooseEEPemp(String chooseEEPemp) {
        this.chooseEEPemp = chooseEEPemp;
    }

    public String getChooseFamily() {
        return chooseFamily;
    }

    public void setChooseFamily(String chooseFamily) {
        this.chooseFamily = chooseFamily;
    }

    public String getChooseOtheremp() {
        return chooseOtheremp;
    }

    public void setChooseOtheremp(String chooseOtheremp) {
        this.chooseOtheremp = chooseOtheremp;
    }

    public String getChoosePrivateemp() {
        return choosePrivateemp;
    }

    public void setChoosePrivateemp(String choosePrivateemp) {
        this.choosePrivateemp = choosePrivateemp;
    }

    public String getChooseAllowance() {
        return chooseAllowance;
    }

    public void setChooseAllowance(String chooseAllowance) {
        this.chooseAllowance = chooseAllowance;
    }

    public String getChooseMonthlySalaryandAllowance() {
        return chooseMonthlySalaryandAllowance;
    }

    public void setChooseMonthlySalaryandAllowance(String chooseMonthlySalaryandAllowance) {
        this.chooseMonthlySalaryandAllowance = chooseMonthlySalaryandAllowance;
    }

    public String getChooseMonthlySalary() {
        return chooseMonthlySalary;
    }

    public void setChooseMonthlySalary(String chooseMonthlySalary) {
        this.chooseMonthlySalary = chooseMonthlySalary;
    }

    public String getChooseOtherAmount() {
        return chooseOtherAmount;
    }

    public void setChooseOtherAmount(String chooseOtherAmount) {
        this.chooseOtherAmount = chooseOtherAmount;
    }
//</editor-fold>

    List<String> StatusAppRej = new ArrayList<>();

    public SelectItem[] getAllState() {
        StatusAppRej.add("Approve");
        StatusAppRej.add("Reject");
        return JsfUtil.getSelectItems(StatusAppRej, false);
    }

    public List<String> getAllAppReList() {
        return salaryDelegationApproveBeanLocal.searchAppReList(0);
    }

    String selectedValue = "";

    public void handleselectOptions(ValueChangeEvent event) {
        if (null != event.getNewValue()) {
            selectedValue = event.getNewValue().toString();
        }
    }

    public void findAppRejList(ValueChangeEvent event) {
        String reqest[] = event.getNewValue().toString().split("--");
        int id = Integer.parseInt(reqest[0]);
        hrSalaryDelegationRequest = salaryDelegationApproveBeanLocal.getDeleationInfo(id);
        if (hrSalaryDelegationRequest.getDelegateFrom().equalsIgnoreCase("EEP Employee")) { //for rendered
            chooseEEPemp = "true";
            chooseFamily = "false";
            choosePrivateemp = "false";
            chooseOtheremp = "false";
        } else if (hrSalaryDelegationRequest.getDelegateFrom().equalsIgnoreCase("Family")) {
            chooseEEPemp = "false";
            chooseFamily = "true";
            choosePrivateemp = "false";
            chooseOtheremp = "false";
        } else {
            chooseEEPemp = "false";
            chooseFamily = "false";
            choosePrivateemp = "true";
            chooseOtheremp = "true";
        }
        setHrEmployees(hrSalaryDelegationRequest.getEmpId());
        setHrEmpFamilies(hrSalaryDelegationRequest.getFamilyId());
        if (hrSalaryDelegationRequest.getDelegateType().equalsIgnoreCase("Monthly Salary")) {
            chooseMonthlySalary = "false";
            chooseOtherAmount = "true";
        } else {
            chooseMonthlySalary = "true";
            chooseOtherAmount = "false";
        }
        hrDepartments = hrEmployees.getDeptId();
        hrJobTypes = hrEmployees.getJobId();
        if (empdelegator == hrSalaryDelegationRequest.getDelegateId()) {
            String delegeName;
            setEmpdelegator(hrSalaryDelegationRequest.getDelegateId());
        } else {
            setEmpdelegator(hrSalaryDelegationRequest.getDelegateId());
        }
        try {
            if (hrSalaryDelegationRequest.getStatus() != null) {
                if (hrSalaryDelegationRequest.getStatus().equals(1)) {
                    approveType = 1;
                } else {
                    approveType = -1;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public boolean insert() {
        if (hrEmployees.getId() == null) {
            JsfUtil.addFatalMessage(" Delegator Information must not be null ");
        } else {
            try {
                if (selectedValue.equalsIgnoreCase("1")) {
                    hrSalaryDelegationRequest.setStatus(1);
                    salaryDelegationApproveBeanLocal.edit(hrSalaryDelegationRequest);
                    JsfUtil.addSuccessMessage("Successfully Approved");
                    clearPage();
                } else if(selectedValue.equalsIgnoreCase("2")){
                    hrSalaryDelegationRequest.setStatus(2);
                    salaryDelegationApproveBeanLocal.edit(hrSalaryDelegationRequest);
                    JsfUtil.addSuccessMessage("Successfully Reject");
                    clearPage();
                }else if(selectedValue.equalsIgnoreCase("3")){
                    hrSalaryDelegationRequest.setStatus(3);
                    salaryDelegationApproveBeanLocal.edit(hrSalaryDelegationRequest);
                    JsfUtil.addSuccessMessage("Successfully Forwared");
                    clearPage();
                }else {
                    hrSalaryDelegationRequest.setStatus(4);
                    salaryDelegationApproveBeanLocal.edit(hrSalaryDelegationRequest);
                    JsfUtil.addSuccessMessage("Successfully Resubmmit");
                    clearPage();
                }
            } catch (Exception e) {
                JsfUtil.addFatalMessage("Unable to Approve data.");
            }
        }
        return true;
    }

    private String clearPage() {
        delegator = null;
        hrEmployees = null;
        empdelegator = null;
        hrSalaryDelegationRequest = null;
        hrEmpFamilies = null;
        hrJobTypes = null;
        hrDepartments = null;
        return null;
    }

    public void delegationRequestInfo() {
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
            setIcone("ui-icon-document");
        }
    }

    int status = 0;

    private void populateTable() {
        try {
            List<HrSalaryDelegationRequest> readFilteredTransfer = salaryDelegationApproveBeanLocal.loadSalaryDelegationList(status);
            requestsListDataModel = new ListDataModel(readFilteredTransfer);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void filiterByStatus_Vcl(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            status = Integer.valueOf(event.getNewValue().toString());
            populateTable();
        }
    }

    public void populate(SelectEvent event) {
        hrSalaryDelegationRequest = (HrSalaryDelegationRequest) event.getObject();
        hrSalaryDelegationRequest.setId(hrSalaryDelegationRequest.getId());
        hrSalaryDelegationRequest = salaryDelegationApproveBeanLocal.getSelectedRequest(hrSalaryDelegationRequest.getId());
        if (hrSalaryDelegationRequest.getDelegateFrom().equalsIgnoreCase("EEP Employee")) { //for rendered
            chooseEEPemp = "true";
            chooseFamily = "false";
            choosePrivateemp = "false";
            chooseOtheremp = "false";
        } else if (hrSalaryDelegationRequest.getDelegateFrom().equalsIgnoreCase("Family")) {
            chooseEEPemp = "false";
            chooseFamily = "true";
            choosePrivateemp = "false";
            chooseOtheremp = "false";
        } else {
            chooseEEPemp = "false";
            chooseFamily = "false";
            choosePrivateemp = "true";
            chooseOtheremp = "true";
        }
        setHrEmployees(hrSalaryDelegationRequest.getEmpId());
        setHrEmpFamilies(hrSalaryDelegationRequest.getFamilyId());
        if (hrSalaryDelegationRequest.getDelegateType().equalsIgnoreCase("Monthly Salary")) {
            chooseMonthlySalary = "false";
            chooseOtherAmount = "true";
        } else {
            chooseMonthlySalary = "true";
            chooseOtherAmount = "false";
        }
        hrDepartments = hrEmployees.getDeptId();
        hrJobTypes = hrEmployees.getJobId();
        chooseCreatePage = true;
        chooseMainPage = false;
        createOrSearchBundle = "Search";
        setIcone("ui-icon-search");
        if (empdelegator == hrSalaryDelegationRequest.getDelegateId()) {
            String delegeName;
            setEmpdelegator(hrSalaryDelegationRequest.getDelegateId());
        } else {
            setEmpdelegator(hrSalaryDelegationRequest.getDelegateId());
        }
    }

    public void searchAllReqeust() {
        if (hrEmployees.getEmpId().isEmpty() && hrEmployees.getFirstName().isEmpty()) {
            selectedRequest = salaryDelegationApproveBeanLocal.findAll();
            requestsListDataModel = new ListDataModel(selectedRequest);
        } else if (hrEmployees.getEmpId() != null && hrEmployees.getFirstName().isEmpty()) {
            selectedRequest = salaryDelegationApproveBeanLocal.findByFirstName(hrEmployees);
            requestsListDataModel = new ListDataModel(selectedRequest);
        } else if (hrEmployees.getEmpId().isEmpty() && hrEmployees.getFirstName() != null) {
            selectedRequest = salaryDelegationApproveBeanLocal.findByEmpName(hrEmployees);
            requestsListDataModel = new ListDataModel(selectedRequest);
        } else if (hrEmployees.getEmpId() != null && hrEmployees.getFirstName() != null) {
            selectedRequest = salaryDelegationApproveBeanLocal.findByAll(hrEmployees, hrEmployees);
            requestsListDataModel = new ListDataModel(selectedRequest);
        }
    }

}
