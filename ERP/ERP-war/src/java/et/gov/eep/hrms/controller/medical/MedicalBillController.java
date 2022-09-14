/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.controller.medical;

import java.io.Serializable;
import java.security.SecureRandom;
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
import javax.faces.model.SelectItem;
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
import webService.UserStatus;
import et.gov.eep.commonApplications.businessLogic.WfHrProcessedBeanLocal;
import et.gov.eep.commonApplications.entity.Status;
import et.gov.eep.commonApplications.entity.WfHrProcessed;
import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.commonApplications.utility.date.StringDateManipulation;
import et.gov.eep.hrms.businessLogic.employee.HrEmployeeBeanLocal;
import et.gov.eep.hrms.businessLogic.medical.MedInvoiceBeanLocal;
import et.gov.eep.hrms.businessLogic.medical.TeartmentTypeBeanLocal;
import et.gov.eep.hrms.entity.employee.HrEmployees;
import et.gov.eep.hrms.entity.medical.HrLocalMedInstitutions;
import et.gov.eep.hrms.entity.medical.HrLocalMedInvoiceDetails;
import et.gov.eep.hrms.entity.medical.HrLocalMedInvoices;
import et.gov.eep.hrms.entity.medical.HrLocalMedTreatmentType;

/**
 *
 * @author INSA
 */
@Named(value = "medicalBillController")
@ViewScoped
public class MedicalBillController implements Serializable {

    @Inject
    HrLocalMedInvoices hrLocalMedInvoices;
    @Inject
    HrLocalMedInvoices srcLocalMedInvoices;
    @Inject
    HrLocalMedInvoiceDetails hrLocalMedInvoiceDetail;
    @Inject
    HrLocalMedInstitutions medicalInstitutions;
    @Inject
    HrEmployees hrEmployees;
    @Inject
    HrLocalMedTreatmentType hrLocalMedTreatmentType;
    @Inject
    WfHrProcessed wfHrProcessed;
    @Inject
    SessionBean sessionBeanLocal;

    @EJB
    MedInvoiceBeanLocal medInvoiceBeanLocal;
    @EJB
    HrEmployeeBeanLocal employeeBeanLocal;
    @EJB
    TeartmentTypeBeanLocal teartmentTypeBeanLocal;
    @EJB
    WfHrProcessedBeanLocal wfHrProcessedBeanLocal;

    private List<HrLocalMedInstitutions> hospitalList = new ArrayList();
    private List<HrLocalMedInstitutions> pharmacyList = new ArrayList();
    List<HrLocalMedInvoices> institutionName;
    List<HrLocalMedInvoices> medInvoiceList;
    List<SelectItem> filterByStatus = new ArrayList<>();
    List<HrLocalMedInstitutions> instNameList;
    List<HrLocalMedInstitutions> typeList;
    private List<HrLocalMedTreatmentType> listOfTreatmnetType;
    DataModel<HrLocalMedInvoiceDetails> invoiceDetailDataModel;
    DataModel<HrLocalMedInvoices> MedInvoiceDataModel;
    DataModel<WfHrProcessed> workflowDataModel = new ListDataModel<>();
    Set<String> checkDuplication = new HashSet<>();
    private List<HrLocalMedInvoices> medInvoicesList;

    private int updateStatus = 0;
    private String saveOrUpdate = "Save";
    private String addOrUpdate = "Add";
    private String newOrSearch = "New";
    private String icone = "ui-icon-plus";
    private boolean searchPage = true;
    private boolean newPage = false;
    private boolean btnNewRender = false;
    private String selectedInsType = "Hospital";
    private String disableEmpID = "false";
    private String disableEmpName = "true";
    private String selected = "Employee ID";
    private String disableBtn = "false";
    private boolean dsbCompanShare = false;
    private boolean dsbEmployeeShare = false;
    private String prepDate;
    private int selectedStatus;
    int status1 = 0, prepareBy;
    UserStatus userStatus = new UserStatus();
    Status status = new Status();

    public MedicalBillController() {
    }

    @PostConstruct
    public void init() {
        setPrepDate(StringDateManipulation.toDayInEc());
        wfHrProcessed.setProcessedOn(StringDateManipulation.toDayInEc());
        if (selectedInsType.equalsIgnoreCase("Hospital")) {
            pharmacyList = null;
            hospitalList = medInvoiceBeanLocal.getHospitalList();
        } else {
            hospitalList = null;
            pharmacyList = medInvoiceBeanLocal.getPharmacyList();
        }
    }

    //<editor-fold defaultstate="collapsed" desc="getters & setters">
    public HrLocalMedInvoices getHrLocalMedInvoices() {
        return hrLocalMedInvoices;
    }

    public void setHrLocalMedInvoices(HrLocalMedInvoices hrLocalMedInvoices) {
        this.hrLocalMedInvoices = hrLocalMedInvoices;
    }

    public HrLocalMedInvoices getSrcLocalMedInvoices() {
        return srcLocalMedInvoices;
    }

    public void setSrcLocalMedInvoices(HrLocalMedInvoices srcLocalMedInvoices) {
        this.srcLocalMedInvoices = srcLocalMedInvoices;
    }

    public HrLocalMedInstitutions getMedicalInstitutions() {
        return medicalInstitutions;
    }

    public void setMedicalInstitutions(HrLocalMedInstitutions medicalInstitutions) {
        this.medicalInstitutions = medicalInstitutions;
    }

    public HrLocalMedInvoiceDetails getHrLocalMedInvoiceDetail() {
        return hrLocalMedInvoiceDetail;
    }

    public void setHrLocalMedInvoiceDetail(HrLocalMedInvoiceDetails hrLocalMedInvoiceDetail) {
        this.hrLocalMedInvoiceDetail = hrLocalMedInvoiceDetail;
    }

    public HrEmployees getHrEmployees() {
        return hrEmployees;
    }

    public void setHrEmployees(HrEmployees hrEmployees) {
        this.hrEmployees = hrEmployees;
    }

    public HrLocalMedTreatmentType getHrLocalMedTreatmentType() {
        if (hrLocalMedTreatmentType == null) {
            hrLocalMedTreatmentType = new HrLocalMedTreatmentType();
        }
        return hrLocalMedTreatmentType;
    }

    public void setHrLocalMedTreatmentType(HrLocalMedTreatmentType hrLocalMedTreatmentType) {
        this.hrLocalMedTreatmentType = hrLocalMedTreatmentType;
    }

    public WfHrProcessed getWfHrProcessed() {
        return wfHrProcessed;
    }

    public void setWfHrProcessed(WfHrProcessed wfHrProcessed) {
        this.wfHrProcessed = wfHrProcessed;
    }

    public DataModel<WfHrProcessed> getWorkflowDataModel() {
        return workflowDataModel;
    }

    public void setWorkflowDataModel(DataModel<WfHrProcessed> workflowDataModel) {
        this.workflowDataModel = workflowDataModel;
    }

    public String getSaveOrUpdate() {
        return saveOrUpdate;
    }

    public void setSaveOrUpdate(String saveOrUpdate) {
        this.saveOrUpdate = saveOrUpdate;
    }

    public String getAddOrUpdate() {
        return addOrUpdate;
    }

    public void setAddOrUpdate(String addOrUpdate) {
        this.addOrUpdate = addOrUpdate;
    }

    public String getNewOrSearch() {
        return newOrSearch;
    }

    public void setNewOrSearch(String newOrSearch) {
        this.newOrSearch = newOrSearch;
    }

    public String getIcone() {
        return icone;
    }

    public void setIcone(String icone) {
        this.icone = icone;
    }

    public boolean isSearchPage() {
        return searchPage;
    }

    public void setSearchPage(boolean searchPage) {
        this.searchPage = searchPage;
    }

    public boolean isNewPage() {
        return newPage;
    }

    public void setNewPage(boolean newPage) {
        this.newPage = newPage;
    }

    public boolean isBtnNewRender() {
        return btnNewRender;
    }

    public void setBtnNewRender(boolean btnNewRender) {
        this.btnNewRender = btnNewRender;
    }

    public String getSelectedInsType() {
        return selectedInsType;
    }

    public void setSelectedInsType(String selectedInsType) {
        this.selectedInsType = selectedInsType;
    }

    public List<HrLocalMedInstitutions> getHospitalList() {
        return hospitalList;
    }

    public void setHospitalList(List<HrLocalMedInstitutions> hospitalList) {
        this.hospitalList = hospitalList;
    }

    public List<HrLocalMedInstitutions> getPharmacyList() {
        return pharmacyList;
    }

    public void setPharmacyList(List<HrLocalMedInstitutions> pharmacyList) {
        this.pharmacyList = pharmacyList;
    }

    public DataModel<HrLocalMedInvoiceDetails> getInvoiceDetailDataModel() {
        if (invoiceDetailDataModel == null) {
            invoiceDetailDataModel = new ArrayDataModel<>();
        }
        return invoiceDetailDataModel;
    }

    public void setInvoiceDetailDataModel(DataModel<HrLocalMedInvoiceDetails> invoiceDetailDataModel) {
        this.invoiceDetailDataModel = invoiceDetailDataModel;
    }

    public List<HrLocalMedInvoices> getMedInvoiceList() {
        if (medInvoiceList == null) {
            medInvoiceList = new ArrayList<>();
        }
        return medInvoiceList;
    }

    public void setMedInvoiceList(List<HrLocalMedInvoices> medInvoiceList) {
        this.medInvoiceList = medInvoiceList;
    }

    public List<SelectItem> getFilterByStatus() {
        return medInvoiceBeanLocal.filterByStatus();
    }

    public void setFilterByStatus(List<SelectItem> filterByStatus) {
        this.filterByStatus = filterByStatus;
    }

    public List<HrLocalMedInstitutions> getTypeList() {
        typeList = medInvoiceBeanLocal.findByType();
        return typeList;
    }

    public void setTypeList(List<HrLocalMedInstitutions> typeList) {
        this.typeList = typeList;
    }

    public List<HrLocalMedTreatmentType> getListOfTreatmnetType() {
        listOfTreatmnetType = teartmentTypeBeanLocal.findAll();
        return listOfTreatmnetType;
    }

    public void setListOfTreatmnetType(List<HrLocalMedTreatmentType> listOfTreatmnetType) {
        this.listOfTreatmnetType = listOfTreatmnetType;
    }

    public List<HrLocalMedInstitutions> getInstNameList() {
        return instNameList;
    }

    public void setInstNameList(List<HrLocalMedInstitutions> instNameList) {
        this.instNameList = instNameList;
    }

    public List<HrLocalMedInvoices> getInstitutionName() {
        return medInvoiceBeanLocal.findAll();
    }

    public void setInstitutionName(List<HrLocalMedInvoices> institutionName) {
        this.institutionName = institutionName;
    }

    public DataModel<HrLocalMedInvoices> getMedInvoiceDataModel() {
        return MedInvoiceDataModel;
    }

    public void setMedInvoiceDataModel(DataModel<HrLocalMedInvoices> MedInvoiceDataModel) {
        this.MedInvoiceDataModel = MedInvoiceDataModel;
    }

    public String getPrepDate() {
        return prepDate;
    }

    public void setPrepDate(String prepDate) {
        this.prepDate = prepDate;
    }

    public String getDisableEmpID() {
        return disableEmpID;
    }

    public void setDisableEmpID(String disableEmpID) {
        this.disableEmpID = disableEmpID;
    }

    public String getDisableEmpName() {
        return disableEmpName;
    }

    public void setDisableEmpName(String disableEmpName) {
        this.disableEmpName = disableEmpName;
    }

    public String getSelected() {
        return selected;
    }

    public void setSelected(String selected) {
        this.selected = selected;
    }

    public String getDisableBtn() {
        return disableBtn;
    }

    public void setDisableBtn(String disableBtn) {
        this.disableBtn = disableBtn;
    }

    public boolean isDsbCompanShare() {
        return dsbCompanShare;
    }

    public void setDsbCompanShare(boolean dsbCompanShare) {
        this.dsbCompanShare = dsbCompanShare;
    }

    public boolean isDsbEmployeeShare() {
        return dsbEmployeeShare;
    }

    public void setDsbEmployeeShare(boolean dsbEmployeeShare) {
        this.dsbEmployeeShare = dsbEmployeeShare;
    }
    //</editor-fold>

    public void btnNewOrSearch() {
        switch (newOrSearch) {
            case "New":
                searchPage = false;
                newPage = true;
                btnNewRender = false;
                newOrSearch = "Search";
                setIcone("ui-icon-search");
                break;
            case "Search":
                searchPage = true;
                newPage = false;
                btnNewRender = false;
                newOrSearch = "New";
                setIcone("ui-icon-plus");
                break;
        }
    }

    public void newButtonAction() {
        resetCreditBillRqst();
        newPage = true;
        searchPage = false;
        btnNewRender = false;
    }

    public void searchCriteria(ValueChangeEvent event) {
        if (null != event.getNewValue()) {
            selected = event.getNewValue().toString();
            if (selected.equalsIgnoreCase("Employee Name")) {
                disableEmpID = "true";
                disableEmpName = "false";
            } else {
                disableEmpID = "false";
                disableEmpName = "true";
            }
        }
    }

    //<editor-fold defaultstate="collapsed" desc="search">
    public SelectItem[] getList() {
        if (hospitalList != null) {
            return JsfUtil.getSelectItems(hospitalList, true);
        } else if (pharmacyList != null) {
            return JsfUtil.getSelectItems(pharmacyList, true);
        }
        return null;
    }

    public void institutionTypeVlc(ValueChangeEvent event) {
        if (event.getNewValue() != null && !event.getNewValue().equals("")) {
            selectedInsType = event.getNewValue().toString();
            if (selectedInsType.equalsIgnoreCase("Hospital")) {
                pharmacyList = null;
                hospitalList = medInvoiceBeanLocal.getHospitalList();
            } else if (event.getNewValue().toString().equalsIgnoreCase("Pharmacy")) {
                hospitalList = null;
                pharmacyList = medInvoiceBeanLocal.getPharmacyList();
            }
        }
    }

    public void handleSelectedName(ValueChangeEvent event) {
        if (null != event.getNewValue()) {
            String name = String.valueOf(event.getNewValue().toString());
            medicalInstitutions.setName(name);
            hrLocalMedInvoices.setInstitutionId(medicalInstitutions);
        }
    }

    public ArrayList<HrEmployees> searchPatientByName(String hrEmployee) {
        ArrayList<HrEmployees> employees = null;
        hrEmployees.setFirstName(hrEmployee);
        employees = medInvoiceBeanLocal.searchPatientByName(hrEmployee);
        return employees;
    }

    public void getPatientByName(SelectEvent event) {
        try {
            hrEmployees.setFirstName((event.getObject().toString()));
            hrEmployees = medInvoiceBeanLocal.getPatientByName(hrEmployees);
            hrLocalMedInvoiceDetail.setEmpId(hrEmployees);
        } catch (Exception ex) {
            JsfUtil.addFatalMessage("Can't get value");
        }
    }

    public ArrayList<HrEmployees> searchByPatientId(String hrEmployee) {
        ArrayList<HrEmployees> employeeId = null;
        hrEmployees.setEmpId(hrEmployee);
        employeeId = employeeBeanLocal.SearchByEmpId(hrEmployees);
        return employeeId;
    }

    public void getPatientById(SelectEvent event) {
        try {
            if (event.getObject() != null) {
                hrEmployees = (HrEmployees) event.getObject();
                hrLocalMedInvoiceDetail.setEmpId(hrEmployees);
            }
//            hrEmployees.setEmpId(event.getObject().toString());
//            hrEmployees = employeeBeanLocal.getByEmpId(hrEmployees);
//            hrLocalMedInvoiceDetail.setEmpId(hrEmployees);
        } catch (Exception ex) {
            JsfUtil.addFatalMessage("Error occurs while Searching Patient Id");
        }
    }

    public void institutionType_vlc(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            String instType = event.getNewValue().toString();
            hrLocalMedInvoices.setInstitutionId(medicalInstitutions);
            instNameList = medInvoiceBeanLocal.findByInstType(instType);
        }
    }

    public void treatmentType_vlc(ValueChangeEvent event) {
        if (!event.getNewValue().toString().isEmpty()) {
            hrLocalMedTreatmentType.setId(Integer.parseInt(event.getNewValue().toString()));
            hrLocalMedTreatmentType = teartmentTypeBeanLocal.findById(hrLocalMedTreatmentType);
            if (hrLocalMedTreatmentType.getCoveredBy().equalsIgnoreCase("Company")) {
                dsbCompanShare = false;
                dsbEmployeeShare = true;
            } else if (hrLocalMedTreatmentType.getCoveredBy().equalsIgnoreCase("Employee")) {
                dsbCompanShare = true;
                dsbEmployeeShare = false;
            } else {
                dsbCompanShare = false;
                dsbEmployeeShare = false;
            }
            hrLocalMedInvoiceDetail.setTreatmentTypeId(hrLocalMedTreatmentType);
        }
    }
    //</editor-fold>

    public void recreateDataModel() {
        invoiceDetailDataModel = null;
        invoiceDetailDataModel = new ListDataModel(new ArrayList(hrLocalMedInvoices.getHrLocalMedInvoiceDetailsList()));
    }

    public void workflowDataModel() {
        workflowDataModel = null;
        workflowDataModel = new ListDataModel(new ArrayList(hrLocalMedInvoices.getWfHrProcessedList()));
    }

    public void addInvoiceDetail() {
        if ((checkDuplication.contains(hrEmployees.getEmpId()))) {
            JsfUtil.addFatalMessage("Duplicate patient name in the same treatment type is not allowed");
        } else {
            hrLocalMedInvoices.addInvoiceDetail(hrLocalMedInvoiceDetail);
            checkDuplication.add(hrEmployees.getEmpId());
//            checkDuplication.add(hrLocalMedTreatmentType.getTreatmentType());
            recreateDataModel();
            calculateCompanyShare();
            calculateEmployeeShare();
            calculateSubTotal();
            clearDetail();
        }
    }

    //<editor-fold defaultstate="collapsed" desc="calculatiion">
    double companyShare;
    double employeeShare;
    double subTotal;

    //<editor-fold defaultstate="collapsed" desc="getter setter">
    public double getCompanyShare() {
        return companyShare;
    }

    public void setCompanyShare(double companyShare) {
        this.companyShare = companyShare;
    }

    public double getEmployeeShare() {
        return employeeShare;
    }

    public void setEmployeeShare(double employeeShare) {
        this.employeeShare = employeeShare;
    }

    public double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(double subTotal) {
        this.subTotal = subTotal;
    }
    //</editor-fold>

    public void calculateCompanyShare() {
        companyShare = 0.0;
        for (HrLocalMedInvoiceDetails hrLocalMedInvoiceDetailList : hrLocalMedInvoices.getHrLocalMedInvoiceDetailsList()) {
            companyShare += hrLocalMedInvoiceDetailList.getAmount() * 90 / 100;
        }
    }

    public void calculateEmployeeShare() {
        employeeShare = 0.0;
        for (HrLocalMedInvoiceDetails hrLocalMedInvoiceDetailList : hrLocalMedInvoices.getHrLocalMedInvoiceDetailsList()) {
            employeeShare += hrLocalMedInvoiceDetailList.getAmount() * 10 / 100;
        }
    }

    public void calculateSubTotal() {
        subTotal = 0;
        subTotal = employeeShare + companyShare;
//        for (HrLocalMedInvoiceDetails hrLocalMedInvoiceDetailList : hrLocalMedInvoices.getHrLocalMedInvoiceDetailsList()) {
//            subTotal += hrLocalMedInvoiceDetailList.getAmount();
//        }
    }
    //</editor-fold>

    public void editDataTable() {
        hrLocalMedInvoiceDetail = invoiceDetailDataModel.getRowData();
        hrEmployees = hrLocalMedInvoiceDetail.getEmpId();
        addOrUpdate = "Modify";
    }

    public void clearDetail() {
        hrLocalMedInvoiceDetail = new HrLocalMedInvoiceDetails();
        hrEmployees = new HrEmployees();
    }

    //<editor-fold defaultstate="collapsed" desc="save update">
    public void saveMedicalCreditBillRequest() {
        try {
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(sessionBeanLocal.getUserName(), "saveMedicalCreditBillRequest", dataset)) {
                if ((!(getInvoiceDetailDataModel().getRowCount() > 0))) {
                    JsfUtil.addFatalMessage("Data table shoud be filled");
                } else {
                    try {
                        if (updateStatus == 0) {
                            SecureRandom randomNum = new SecureRandom();
                            Integer num = randomNum.nextInt(100000);
                            String formate = String.format("%05d", num);
                            hrLocalMedInvoices.setReferenceNo("Med" + formate);
                            hrLocalMedInvoices.setStatus(Constants.PREPARE_VALUE);
                            hrLocalMedInvoices.setPreparedOn(prepDate);
                            hrLocalMedInvoices.setPreparedBy(sessionBeanLocal.getUserId());
                            hrLocalMedInvoices.setRemark(wfHrProcessed.getCommentGiven());
                            wfHrProcessed.setMedicalCreditBillId(hrLocalMedInvoices);
                            wfHrProcessed.setDecision(hrLocalMedInvoices.getStatus());
                            wfHrProcessed.setProcessedBy(sessionBeanLocal.getUserId());
                            medInvoiceBeanLocal.save(hrLocalMedInvoices);
                            wfHrProcessedBeanLocal.saveOrUpdate(wfHrProcessed);
                            JsfUtil.addSuccessMessage("Successfully saved");
                            resetCreditBillRqst();
                        } else {
                            medInvoiceBeanLocal.edit(hrLocalMedInvoices);
                            JsfUtil.addSuccessMessage("Successfully updated");
                            updateStatus = 0;
                            saveOrUpdate = "Save";
                            resetCreditBillRqst();
                        }
                    } catch (Exception ex) {
                        JsfUtil.addFatalMessage("Error occured while save update");
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
                JAXBElement<String> program = new JAXBElement<String>(qualifiedName, String.class, null, "saveMedicalCreditBillRequest");
                eventEntry.setProgram(program);
                security.addEventLog(eventEntry, dataset);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void resetCreditBillRqst() {
        hrLocalMedInvoices = new HrLocalMedInvoices();
        hrLocalMedInvoiceDetail = new HrLocalMedInvoiceDetails();
        hrEmployees = new HrEmployees();
        wfHrProcessed = new WfHrProcessed();
        medicalInstitutions = new HrLocalMedInstitutions();
        hrLocalMedTreatmentType = new HrLocalMedTreatmentType();
        invoiceDetailDataModel = new ArrayDataModel<>();
        updateStatus = 0;
        saveOrUpdate = "Save";
        employeeShare = 0;
        companyShare = 0;
        subTotal = 0;
        disableBtn = "false";
    }
    //</editor-fold>

    public void srcRecreateDataModel(List<HrLocalMedInvoices> srcRecreateDataModel) {
        MedInvoiceDataModel = null;
        MedInvoiceDataModel = new ListDataModel(new ArrayList<>(srcRecreateDataModel));
    }

    public void creditBill(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            String instName = String.valueOf(event.getNewValue());
            srcRecreateDataModel(medInvoiceBeanLocal.findByName(instName));
        }
    }

    public ArrayList<HrLocalMedInvoices> findMedicalInvoiceList(ValueChangeEvent event) {
        try {
            medicalInstitutions.setName(event.getNewValue().toString());
            MedInvoiceDataModel = new ListDataModel(new ArrayList(medInvoiceList));
        } catch (Exception ex) {
            JsfUtil.addFatalMessage("Can't get value");
        }
        return null;
    }

    public void medicalInvoiceList() {
        try {
            if (medicalInstitutions.getName() == null) {
                medInvoiceList = medInvoiceBeanLocal.findAll();
                MedInvoiceDataModel = new ListDataModel(medInvoiceList);
            } else if (!medicalInstitutions.getName().isEmpty()) {
                medInvoiceList = medInvoiceBeanLocal.findByInstitutionName(medicalInstitutions);
                MedInvoiceDataModel = new ListDataModel(medInvoiceList);
            } else {
                JsfUtil.addFatalMessage("Sorry!. Please try again.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            JsfUtil.addFatalMessage("Sorry! Reload the page and try again.");
        }
    }

    public SelectItem[] getStatusList() {
        SelectItem[] items = new SelectItem[4];
        items[0] = new SelectItem("null", "--- Select ---");
        items[1] = new SelectItem(Constants.PREPARE_VALUE, "Load request list");
        items[2] = new SelectItem(Constants.APPROVE_VALUE, "Load approved list");
        items[3] = new SelectItem(Constants.APPROVE_REJECT_VALUE, "Load rejected list");
        return items;
    }

    public void loadByStatus(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            selectedStatus = Integer.valueOf(String.valueOf(event.getNewValue()));
            if (selectedStatus == Constants.PREPARE_VALUE) {
                status.setStatus1(Constants.PREPARE_VALUE);
                medInvoicesList = medInvoiceBeanLocal.loadReqMedInvoiceList(status, sessionBeanLocal.getUserId());
                MedInvoiceDataModel = new ListDataModel<>(medInvoicesList);
            } else if (selectedStatus == Constants.APPROVE_VALUE) {
                status.setStatus1(Constants.APPROVE_VALUE);
                status.setStatus2(Constants.CHECK_APPROVE_VALUE);
                medInvoicesList = medInvoiceBeanLocal.loadApproveMedInvoiceList(status, sessionBeanLocal.getUserId());
                MedInvoiceDataModel = new ListDataModel<>(medInvoicesList);
            } else if (selectedStatus == Constants.APPROVE_REJECT_VALUE) {
                status.setStatus1(Constants.APPROVE_REJECT_VALUE);
                status.setStatus2(Constants.CHECK_REJECT_VALUE);
                medInvoicesList = medInvoiceBeanLocal.loadApproveMedInvoiceList(status, sessionBeanLocal.getUserId());
                MedInvoiceDataModel = new ListDataModel<>(medInvoicesList);
            }
        }
    }

    int reqStatus = 0;

    private void populateTable() {
        try {
            List<HrLocalMedInvoices> readFilteredMedInvoce = medInvoiceBeanLocal.loadMedicalInvoice(reqStatus);
            MedInvoiceDataModel = new ListDataModel(readFilteredMedInvoce);
        } catch (Exception ex) {
            JsfUtil.addFatalMessage("Can't get value");
        }
    }

    public void filiterByStatus_Vcl(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            reqStatus = Integer.valueOf(event.getNewValue().toString());
            populateTable();
        }
    }

    public SelectItem[] getinst_Type() {
        return JsfUtil.getSelectItems(inst_Type(), true);
    }

    public ArrayList<String> inst_Type() {
        ArrayList<String> typeList = new ArrayList<>();
        typeList.add("Hospital");
        typeList.add("Pharmacy");
        return typeList;
    }

    public void populateMdicalInvoice(SelectEvent event) {
        hrLocalMedInvoices = (HrLocalMedInvoices) event.getObject();
        if (hrLocalMedInvoices.getInstitutionId().getType().equalsIgnoreCase("Hospital")) {
            pharmacyList = null;
            selectedInsType = "Hospital";
            hospitalList = medInvoiceBeanLocal.getHospitalList();
        } else {
            hospitalList = null;
            selectedInsType = "Pharmacy";
            pharmacyList = medInvoiceBeanLocal.getPharmacyList();
        }
        if (hrLocalMedInvoices.getStatus().equals(Constants.PREPARE_VALUE) || hrLocalMedInvoices.getStatus().equals(Constants.CHECK_REJECT_VALUE)
                || hrLocalMedInvoices.getStatus().equals(Constants.APPROVE_REJECT_VALUE)) {
            disableBtn = "false";
        } else {
            disableBtn = "true";
        }
        prepDate = hrLocalMedInvoices.getPreparedOn();
        newPage = true;
        searchPage = false;
        btnNewRender = true;
        newOrSearch = "Search";
        setIcone("ui-icon-search");
        updateStatus = 1;
        saveOrUpdate = "Update";
        recreateDataModel();
        workflowDataModel();
        calculateCompanyShare();
        calculateEmployeeShare();
        calculateSubTotal();
    }

}
