/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.controller.salarydelegation;

import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.commonApplications.utility.date.StringDateManipulation;
import et.gov.eep.hrms.businessLogic.employee.HrEmployeeBeanLocal;
import et.gov.eep.hrms.businessLogic.salarydelegation.HrSalaryDelegationRequestBeanLocal;
import et.gov.eep.hrms.entity.salarydelegation.HrSalaryDelegationRequest;
import et.gov.eep.hrms.entity.employee.HrEmployees;
import et.gov.eep.hrms.entity.organization.HrDepartments;
import et.gov.eep.hrms.entity.employee.HrEmpFamilies;
import et.gov.eep.hrms.entity.organization.HrJobTypes;
import et.gov.eep.hrms.entity.lookup.HrLuTitles;
import et.gov.eep.hrms.entity.organization.HrSalaryScales;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import org.apache.commons.io.IOUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.TabChangeEvent;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author INSA
 */
@Named(value = "hrSalaryDelegationRequestController")
@ViewScoped
public class HrSalaryDelegationRequestController implements Serializable {

    @EJB
    HrSalaryDelegationRequestBeanLocal hrSalaryDelegationRequestBeanLocal;
    @EJB
    private HrEmployeeBeanLocal hrEmployeeBean;
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

    int updteStatus = 0;
    int approveType = 0;
    private String saveorUpdateBundle = "Save";
    private String createOrSearchBundle = "New";
    private String headerTitle = "Search Request";
    private String icone = "ui-icon-document";
    private boolean disableBtnCreate;
    private boolean chooseCreatePage = false;
    private boolean chooseMainPage = true;
    DataModel<HrSalaryDelegationRequest> requestsListDataModel;
    private List<HrSalaryDelegationRequest> selectedRequest;
    List<SelectItem> filterByStatus = new ArrayList<>();
    String tab1 = "Inbox";
    String tab2 = "Response";
    String activeTab = tab1;
    private int dateCalc;
    private Date dateOfDelegated;
    private Date endOfDelegated;
    String delegator;
    DateFormat dateFormat = new SimpleDateFormat("dd/mm/yyyy");
    String disAbleDelete = "true";

    private String chooseEEPemp = "false";
    private String chooseFamily = "false";
    private String chooseOtheremp = "true";
    private String choosePrivateemp = "false";

    private String chooseAllowance;
    private String chooseMonthlySalaryandAllowance;
    private String chooseMonthlySalary = "true";
    private String chooseOtherAmount = chooseMonthlySalaryandAllowance = chooseAllowance = "false";

    // <editor-fold defaultstate="collapsed" desc="Getter and Setter">
    public HrSalaryDelegationRequest getHrSalaryDelegationRequest() {
        if (hrSalaryDelegationRequest == null) {
            hrSalaryDelegationRequest = new HrSalaryDelegationRequest();
        }
        return hrSalaryDelegationRequest;
    }

    public HrEmployees getEmpdelegator() {
        if (empdelegator == null) {
            empdelegator = new HrEmployees();
        }
        return empdelegator;
    }

    public void setEmpdelegator(HrEmployees empdelegator) {
        this.empdelegator = empdelegator;
    }

    public HrEmpFamilies getHrEmpFamilies() {
        if (hrEmpFamilies == null) {
            hrEmpFamilies = new HrEmpFamilies();
        }
        return hrEmpFamilies;
    }

    public void setHrEmpFamilies(HrEmpFamilies hrEmpFamilies) {
        this.hrEmpFamilies = hrEmpFamilies;
    }

    public HrSalaryScales getHrSalaryScales() {
        if (hrSalaryScales == null) {
            hrSalaryScales = new HrSalaryScales();
        }
        return hrSalaryScales;
    }

    public void setHrSalaryScales(HrSalaryScales hrSalaryScales) {
        this.hrSalaryScales = hrSalaryScales;
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

    private String delegaterId;

    public String getDelegaterId() {
        return delegaterId;
    }

    public void setDelegaterId(String delegaterId) {
        this.delegaterId = delegaterId;
    }

    public void setHrSalaryDelegationRequest(HrSalaryDelegationRequest hrSalaryDelegationRequest) {
        this.hrSalaryDelegationRequest = hrSalaryDelegationRequest;
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

    public DataModel<HrSalaryDelegationRequest> getRequestsListDataModel() {
        return requestsListDataModel = new ListDataModel(hrSalaryDelegationRequestBeanLocal.loadSalaryDelegationList(status));
    }

    public void setRequestsListDataModel(DataModel<HrSalaryDelegationRequest> requestsListDataModel) {
        this.requestsListDataModel = requestsListDataModel;
    }

    public List<HrSalaryDelegationRequest> getSelectedRequest() {
        if (selectedRequest == null) {
            selectedRequest = new ArrayList<>();
        }
        return selectedRequest;
    }

    public void setSelectedRequest(List<HrSalaryDelegationRequest> selectedRequest) {
        this.selectedRequest = selectedRequest;
    }

    public List<SelectItem> getFilterByStatus() {
        return hrSalaryDelegationRequestBeanLocal.filterByStatus();
    }

    public void setFilterByStatus(List<SelectItem> filterByStatus) {
        this.filterByStatus = filterByStatus;
    }

    public byte[] getByteData() {
        return byteData;
    }

    public void setByteData(byte[] byteData) {
        this.byteData = byteData;
    }

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
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

    public HrLuTitles getHrLuTitles() {
        if (hrLuTitles == null) {
            hrLuTitles = new HrLuTitles();
        }
        return hrLuTitles;
    }

    public void setHrLuTitles(HrLuTitles hrLuTitles) {
        this.hrLuTitles = hrLuTitles;
    }

    public HrDepartments getHrDepartments() {
        if (hrDepartments == null) {
            hrDepartments = new HrDepartments();
        }
        return hrDepartments;
    }

    public void setHrDepartments(HrDepartments hrDepartments) {
        this.hrDepartments = hrDepartments;
    }

    public String getDisAbleDelete() {
        return disAbleDelete;
    }

    public void setDisAbleDelete(String disAbleDelete) {
        this.disAbleDelete = disAbleDelete;
    }

    public Date getDateOfDelegated() {
        return dateOfDelegated;
    }

    public void setDateOfDelegated(Date dateOfDelegated) {
        this.dateOfDelegated = dateOfDelegated;
    }

    public Date getEndOfDelegated() {
        return endOfDelegated;
    }

    public void setEndOfDelegated(Date endOfDelegated) {
        this.endOfDelegated = endOfDelegated;
    }

    public int getDateCalc() {
        return dateCalc;
    }

    public void setDateCalc(int dateCalc) {
        this.dateCalc = dateCalc;
    }

    public String getDelegator() {
        return delegator;
    }

    public void setDelegator(String delegator) {
        this.delegator = delegator;
    }

    public int getApproveType() {
        return approveType;
    }

    public void setApproveType(int approveType) {
        this.approveType = approveType;
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

    public String getChoosePrivateemp() {
        return choosePrivateemp;
    }

    public void setChoosePrivateemp(String choosePrivateemp) {
        this.choosePrivateemp = choosePrivateemp;
    }

    public String getChooseOtheremp() {
        return chooseOtheremp;
    }

    public void setChooseOtheremp(String chooseOtheremp) {
        this.chooseOtheremp = chooseOtheremp;
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
    // </editor-fold> 

    String slected = "select one";

    public void handleEmploee(ValueChangeEvent event) {
        if (null != event.getNewValue()) {
            slected = event.getNewValue().toString();
            if (slected.equalsIgnoreCase("EEP Employee")) {
                chooseEEPemp = "true";
                chooseFamily = "false";
                choosePrivateemp = "false";
                chooseOtheremp = "false";
            } else if (slected.equalsIgnoreCase("Family")) {
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
        }
    }

    public void handelDelegateType(ValueChangeEvent event) {
        if (null != event.getNewValue()) {
            slected = event.getNewValue().toString();
            if (slected.equalsIgnoreCase("Monthly Salary")) {
                chooseMonthlySalary = "false";
                chooseOtherAmount = "true";
            } else {
                chooseMonthlySalary = "true";
                chooseOtherAmount = "false";
            }
        }
    }

    public void clear1() {
        hrEmployeeBean = null;
        hrSalaryDelegationRequest = null;
    }

    public Date date() {
        DateFormat dformat = new SimpleDateFormat("dd/mm/yyyy");
        Date datee = new Date();
        dformat.format(datee);
        return datee;
    }

    public Integer changedTo() throws ParseException {
        SimpleDateFormat simpledate = new SimpleDateFormat("dd/mm/yyyy");
        String x = simpledate.format(hrSalaryDelegationRequest.getDateOfDelegated());
        String y = simpledate.format(hrSalaryDelegationRequest.getEndOfDelegated());
        int dayDiff = StringDateManipulation.compareDateDifference(x, y);
        return dateCalc = dayDiff;
    }

    public String save() {
        if (hrEmployees.getId() == null) {
            JsfUtil.addFatalMessage("Delegator ID not be null,please search by name and fill it");
        } else if (hrSalaryDelegationRequest.getDateOfDelegated().getTime() >= hrSalaryDelegationRequest.getEndOfDelegated().getTime()) {
            JsfUtil.addFatalMessage("Start date should not be greater than or Equal to end date");
        } else if (hrSalaryDelegationRequest.getDelegateFrom().equalsIgnoreCase("EEP Employee")
                && (hrSalaryDelegationRequest.getEmpId().getId()).equals(hrSalaryDelegationRequest.getDelegateId().getId())) {
            JsfUtil.addFatalMessage("Can not Delegate Yourself");
        } else {
            if (updteStatus == 0) {
                try {
                    hrSalaryDelegationRequest.setStatus(0);
                    hrSalaryDelegationRequest.setFamilyId(hrEmpFamilies);
                    hrSalaryDelegationRequestBeanLocal.edit(hrSalaryDelegationRequest);
                    JsfUtil.addSuccessMessage("Data Saved Successfully");
                    requestsListDataModel = new ListDataModel(new ArrayList<>(hrSalaryDelegationRequestBeanLocal.findAll()));
                    return clearPage();
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JsfUtil.addFatalMessage("Unbale to Save Data");
//                    requestsListDataModel = new ListDataModel(new ArrayList<>(hrSalaryDelegationRequestBeanLocal.findAll()));
                    return null;
                }
            } else {
                try {
                    hrSalaryDelegationRequestBeanLocal.edit(hrSalaryDelegationRequest);
                    JsfUtil.addSuccessMessage("Data Modified Successfully");
                    return clearPage();
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JsfUtil.addFatalMessage("Unbale to Modify Data");
                    return null;
                }
            }
        }
        return null;
    }

    public void delete() {
        try {
            hrSalaryDelegationRequestBeanLocal.remove(hrSalaryDelegationRequest);
            JsfUtil.addSuccessMessage("Data Deleted Successfully");
            clearPage();
            updteStatus = 0;
        } catch (Exception ex) {
            ex.printStackTrace();
            JsfUtil.addFatalMessage("Unbale to Delete");
            saveorUpdateBundle = "Save";
        }
    }

    private String clearPage() {
        delegator = null;
        hrEmployees = null;
        empdelegator = null;
        hrSalaryDelegationRequest = null;
        hrEmpFamilies = null;
        hrJobTypes = null;
        hrDepartments = null;
        disAbleDelete = "true";
        return null;
    }

    public void clear() {
        delegator = null;
        hrEmployees = new HrEmployees();
        empdelegator = new HrEmployees();
        hrSalaryDelegationRequest = new HrSalaryDelegationRequest();
        hrJobTypes = new HrJobTypes();
        hrDepartments = new HrDepartments();
        hrEmpFamilies = new HrEmpFamilies();
        disAbleDelete = "true";
        updteStatus = 0;
        saveorUpdateBundle = "Save";
    }

    // </editor-fold>    
    @PostConstruct
    public void _init() {
        loadListOfRequests();
    }

    public void listner() {
        System.out.print("The amoutn is1 " + delegaterId);
    }

    public void getSalryDelInformation(ValueChangeEvent event) {
        hrEmployees = hrEmployeeBean.getByFirstName(hrEmployees);
        hrSalaryDelegationRequest = hrSalaryDelegationRequestBeanLocal.findSalaryDelById(hrEmployees);
        System.out.print("The amoutn is1 " + delegaterId);
        System.out.print("The amoutn is " + hrSalaryDelegationRequest.getAmount());
    }

    public void getSalaryDel(SelectEvent event) {
        try {
            System.out.println("==selected==" + event.getObject().toString());
            hrEmployees.setFirstName(event.getObject().toString());
            hrEmployees = hrEmployeeBean.getByFirstName(hrEmployees);
            hrSalaryDelegationRequest = hrSalaryDelegationRequestBeanLocal.findSalaryDelById(hrEmployees);
        } catch (Exception ex) {
            System.err.println("Exception");
        }
    }

    public void handleSelect(SelectEvent event) {
        System.out.print("The selectcted event" + event.getObject().toString());
    }

    private List<HrSalaryDelegationRequest> listOfSalaryDelegationRequests;

    public List<HrSalaryDelegationRequest> getListOfSalaryDelegationRequests() {
        return listOfSalaryDelegationRequests;
    }

    public void setListOfSalaryDelegationRequests(List<HrSalaryDelegationRequest> listOfSalaryDelegationRequests) {
        this.listOfSalaryDelegationRequests = listOfSalaryDelegationRequests;
    }

    public void loadListOfRequests() {
        try {
            HrSalaryDelegationRequest salDelRe = new HrSalaryDelegationRequest();
            salDelRe.setStatus(0);
            listOfSalaryDelegationRequests = hrSalaryDelegationRequestBeanLocal.requestListByStatus(salDelRe);
        } catch (Exception ex) {
        }
    }

    public SelectItem[] getSalrayDelList() {
        hrSalaryDelegationRequest.setStatus(0);
        return JsfUtil.getSelectItems(hrSalaryDelegationRequestBeanLocal.requestListByStatus(hrSalaryDelegationRequest), false);
    }

    public ArrayList<HrEmployees> SearchEmpByFname(String hrEmployee) {
        ArrayList<HrEmployees> employeesee = null;
        hrEmployees.setFirstName(hrEmployee);
        employeesee = hrEmployeeBean.SearchByFname(hrEmployees);
        setHrEmployees(hrSalaryDelegationRequest.getEmpId());
        return employeesee;
    }

    public ArrayList<HrEmployees> SearchByFname(String hrEmp) {
        ArrayList<HrEmployees> employees = null;
        empdelegator.setFirstName(hrEmp);
        employees = hrEmployeeBean.SearchByFname(empdelegator);
        setEmpdelegator(hrSalaryDelegationRequest.getDelegateId());
        return employees;
    }

    public void getByFirstName(SelectEvent event) {
        try {
            hrEmployees.setFirstName(event.getObject().toString());
            hrEmployees = hrEmployeeBean.getByFirstName(hrEmployees);
            hrSalaryDelegationRequest = hrSalaryDelegationRequestBeanLocal.findSalaryDelById(hrEmployees);
        } catch (Exception ex) {
            System.err.println("Exception");
        }
    }

    public ArrayList<HrEmpFamilies> SearchFamillyName(String fam) {
        ArrayList<HrEmpFamilies> famname = null;
        hrEmpFamilies.setFirstName(fam);
        famname = hrSalaryDelegationRequestBeanLocal.searchFamname(hrEmpFamilies);
        System.out.println("fam===" + famname);
        return famname;
    }

    public void getByFamillyName(SelectEvent event) {
        try {
            hrEmpFamilies.setFirstName(event.getObject().toString());
            hrEmpFamilies = hrSalaryDelegationRequestBeanLocal.getFamname(hrEmpFamilies);
        } catch (Exception ex) {
            System.err.println("Exception");
        }
    }

    public List<String> getAllRequests() {
        return hrSalaryDelegationRequestBeanLocal.searchByName(0);
    }

    public void findDelegation(ValueChangeEvent event) {
        String reqest[] = event.getNewValue().toString().split("--");
        updteStatus = 1;
        int id = Integer.parseInt(reqest[0]);
        hrSalaryDelegationRequest = hrSalaryDelegationRequestBeanLocal.getDeleationInfo(id);
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
        disAbleDelete = "false";
        saveorUpdateBundle = "Update";
        if (empdelegator == hrSalaryDelegationRequest.getDelegateId()) {
            String delegeName;
            setEmpdelegator(hrSalaryDelegationRequest.getDelegateId());
        } else {
            setEmpdelegator(hrSalaryDelegationRequest.getDelegateId());
        }
    }

    public void onTabChange(TabChangeEvent event) {
        FacesMessage msg = new FacesMessage("Tab Changed", event.getTab().getTitle() + "Tab is Active");
        activeTab = event.getTab().getTitle();
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    double salary;

    public void SalarychangeListener(ValueChangeEvent event) {
        if (updteStatus == 0) {
            if (null != event.getNewValue()) {
                HrEmployees cat = new HrEmployees();
                int sal = event.getNewValue().hashCode();
                hrSalaryDelegationRequest.setAmount(salary);
            }
        } else {
            System.out.println("Disbled");
        }
    }

    public void delegationRequestInfo() {
        saveorUpdateBundle = "Save";
        disableBtnCreate = false;
        if (createOrSearchBundle.equals("New")) {
            chooseCreatePage = true;
            chooseMainPage = false;
            createOrSearchBundle = "Search";
            headerTitle = "Delegation Request";
            setIcone("ui-icon-search");
        } else if (createOrSearchBundle.equals("Search")) {
            chooseCreatePage = false;
            chooseMainPage = true;
            createOrSearchBundle = "New";
            headerTitle = "Search Request";
            setIcone("ui-icon-document");
        }
    }

    int status = 0;

    private void populateTable() {
        try {
            List<HrSalaryDelegationRequest> readFilteredTransfer = hrSalaryDelegationRequestBeanLocal.loadSalaryDelegationList(status);
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
        hrSalaryDelegationRequest = hrSalaryDelegationRequestBeanLocal.getSelectedRequest(hrSalaryDelegationRequest.getId());
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
        updteStatus = 1;
        saveorUpdateBundle = "Update";
        if (empdelegator == hrSalaryDelegationRequest.getDelegateId()) {
            String delegeName;
            setEmpdelegator(hrSalaryDelegationRequest.getDelegateId());
        } else {
            setEmpdelegator(hrSalaryDelegationRequest.getDelegateId());
        }
    }

    public void searchAllReqeust() {
        if (hrEmployees.getEmpId().isEmpty() && hrEmployees.getFirstName().isEmpty()) {
            selectedRequest = hrSalaryDelegationRequestBeanLocal.findAll();
            requestsListDataModel = new ListDataModel(selectedRequest);
        } else if (hrEmployees.getEmpId() != null && hrEmployees.getFirstName().isEmpty()) {
            selectedRequest = hrSalaryDelegationRequestBeanLocal.findByFirstName(hrEmployees);
            requestsListDataModel = new ListDataModel(selectedRequest);
        } else if (hrEmployees.getEmpId().isEmpty() && hrEmployees.getFirstName() != null) {
            selectedRequest = hrSalaryDelegationRequestBeanLocal.findByEmpName(hrEmployees);
            requestsListDataModel = new ListDataModel(selectedRequest);
        } else if (hrEmployees.getEmpId() != null && hrEmployees.getFirstName() != null) {
            selectedRequest = hrSalaryDelegationRequestBeanLocal.findByAll(hrEmployees, hrEmployees);
            requestsListDataModel = new ListDataModel(selectedRequest);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="File Upload">
    private byte[] byteData;
    private UploadedFile file;
    private String filename;

    public void handleFileUpload(FileUploadEvent event) {
        file = event.getFile();
        try {
            byteData = IOUtils.toByteArray(file.getInputstream());
            hrSalaryDelegationRequest.setDataAttached(byteData);
            hrSalaryDelegationRequestBeanLocal.edit(hrSalaryDelegationRequest);
            FacesMessage msg = new FacesMessage("####Succesful####", event.getFile().getFileName() + " is uploaded.");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    // </editor-fold>

    public void getByEmpName(SelectEvent event) {
        try {
            hrEmployees.setFirstName(event.getObject().toString());
            hrEmployees = hrEmployeeBean.getByFirstName(hrEmployees);
            setHrEmployees(hrEmployees);
            hrSalaryDelegationRequest.setEmpId(hrEmployees);
            hrDepartments = hrEmployees.getDeptId();
            hrJobTypes = hrEmployees.getJobId();
        } catch (Exception ex) {
            System.err.println("Exception");
        }

    }

    public void getEmployeeName(SelectEvent event) {
        try {
            empdelegator.setFirstName(event.getObject().toString());
            empdelegator = hrEmployeeBean.getByFirstName(empdelegator);
            setEmpdelegator(empdelegator);
            hrSalaryDelegationRequest.setDelegateId(empdelegator);
        } catch (Exception ex) {
            System.err.println("Exception");
        }
    }

    public ArrayList<HrEmployees> SearchByEmpname(String hrEmployee) {
        ArrayList<HrEmployees> employees = null;
        hrEmployees.setFirstName(hrEmployee);
        employees = hrEmployeeBean.SearchByFname(hrEmployees);
        System.out.println("obj===" + hrEmployee);
        return employees;
    }

    public void loadSalarDelegInfo() {
        String empId = hrEmployees.getEmpId();
        hrSalaryDelegationRequestBeanLocal.SearchByEmpId(hrSalaryDelegationRequest);
        hrSalaryDelegationRequest.setEmpId(hrEmployees);
    }

    public ArrayList<HrSalaryDelegationRequest> searchByFirstName(String firstName) {
        ArrayList<HrSalaryDelegationRequest> name = null;
        hrSalaryDelegationRequest.setFirstName(firstName);
        name = hrSalaryDelegationRequestBeanLocal.searchByFirstName(hrSalaryDelegationRequest);
        System.out.println("obj===" + firstName);
        loadSalarDelegInfo();
        return name;
    }

}
