/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.controller.training;
//<editor-fold defaultstate="collapsed" desc="Imports">

import java.io.Serializable;
import java.math.BigDecimal;
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
import et.gov.eep.commonApplications.utility.date.StringDateManipulation;
import et.gov.eep.hrms.businessLogic.training.HrInternshipPaymentBeanLocal;
import et.gov.eep.hrms.businessLogic.training.HrInternshipStudentDetailsBeanLocal;
import et.gov.eep.hrms.entity.employee.HrEmployees;
import et.gov.eep.hrms.entity.organization.HrDepartments;
import et.gov.eep.hrms.entity.training.HrTdIspPaymentDetails;
import et.gov.eep.hrms.entity.training.HrTdIspPayments;
import et.gov.eep.hrms.entity.training.HrTdIspStudentDetails;
import et.gov.eep.hrms.entity.training.HrTdIspStudentPlacement;
import et.gov.eep.hrms.entity.training.HrTdIspStudents;
import et.gov.eep.hrms.mapper.training.HrTdIspPaymentFacade;
import et.gov.eep.hrms.mapper.training.HrTdIspStudentPlacementFacade;

 //</editor-fold>
@Named(value = "hrInternPaymentController")
@ViewScoped
public class hrInternshipStudentPaymentController implements Serializable {
    //<editor-fold defaultstate="collapsed" desc="Entity, bussiness logic & variables">

    @Inject
    HrTdIspPayments hrTdIspPayment;

    @Inject
    HrTdIspPaymentDetails hrTdIspPaymentDetails;

    @Inject
    HrEmployees hrEmployees;

    @Inject
    HrTdIspStudents hrTdIspStudents;

    @Inject
    HrTdIspStudentDetails hrTdIspStudentDetails;

    @Inject
    HrTdIspStudentPlacement hrTdIspStudentPlacement;

    @Inject
    HrDepartments hrDepartment;

    @Inject
    SessionBean sessionBeanLocal;

    @EJB
    HrInternshipPaymentBeanLocal hrInternshipPaymentBeanLocal;

    @EJB
    HrInternshipStudentDetailsBeanLocal hrInternshipStudentDetailsBeanLocal;

    @EJB
    HrTdIspStudentPlacementFacade hrTdIspStudentPlacementFacade;

    @EJB
    HrTdIspPaymentFacade hrInternshipPaymentfacade;

    private String tabToggle = "";
    int update = 0;
    private boolean renderPnlCreateAdditional = true;
    private String addorUpdate = "Add";
    private String SaveOrUpdateButton = "Save";
    boolean btnaddvisibility = true;
    private String headerTitle = "Search....";
    private String saveorUpdateBundle = "Update";
    private String icone = "ui-icon-plus";
    private String createOrSearchBundle = "New";
    private boolean disableBtnCreate;
    private boolean renderPnlCreateGatePass = false;
    private boolean renderPnlManPage = true;
    private boolean btnNewRender = false;
    private String renderpnlContrat = "false";
    private boolean disabled;
    private int updateStatus = 0;

    DataModel<HrTdIspStudentDetails> StatusPlacedmodel;
    private Integer preparedBy;
    String preparedDate;
    private String disableBtn = "false";
    private List<HrTdIspStudents> budgetYear;
    private List<HrTdIspStudents> semisters;
    private List<HrTdIspStudentDetails> placedStudentList = new ArrayList<>();
    DataModel<HrTdIspPaymentDetails> paymentdetailmodel;
    List<HrTdIspPayments> letterList = new ArrayList<>();
    int selectedRowIndexStudent = -1;
    private ArrayList<SelectItem> listOfmonth = new ArrayList<>();
    List<HrTdIspStudentDetails> detaillist = new ArrayList<>();
    Set<String> checkDuplicate = new HashSet<>();
    DataModel<HrTdIspPayments> paymentDataModel = new ListDataModel<>();
    BigDecimal subTotal = new BigDecimal(0.0);
    List<HrTdIspStudentPlacement> internshipList = new ArrayList<>();
    String dept;

    //</editor-fold>
    @PostConstruct
    public void init() {
        setListOfmonth(ListofMonths());
        budgetYear = hrInternshipPaymentBeanLocal.findYear();
        letterList = hrInternshipPaymentBeanLocal.findByReferenceLitter();
        internshipList = hrTdIspStudentPlacementFacade.findAll();
        hrTdIspPayment.setPreparedOn(StringDateManipulation.toDayInEc());
    }

    //<editor-fold defaultstate="collapsed" desc="Getters setters">
    public List<HrTdIspStudentPlacement> getInternshipList() {
        return internshipList;
    }

    public void setInternshipList(List<HrTdIspStudentPlacement> internshipList) {
        this.internshipList = internshipList;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public BigDecimal getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(BigDecimal subTotal) {
        this.subTotal = subTotal;
    }

    public Integer getPreparedBy() {
        return preparedBy;
    }

    public void setPreparedBy(Integer preparedBy) {
        this.preparedBy = preparedBy;
    }

    public String getDisableBtn() {
        return disableBtn;
    }

    public void setDisableBtn(String disableBtn) {
        this.disableBtn = disableBtn;
    }

    public HrTdIspStudentDetails getHrTdIspStudentDetails() {
        if (hrTdIspStudentDetails == null) {
            hrTdIspStudentDetails = new HrTdIspStudentDetails();
        }
        return hrTdIspStudentDetails;
    }

    public void setHrTdIspStudentDetails(HrTdIspStudentDetails hrTdIspStudentDetails) {
        this.hrTdIspStudentDetails = hrTdIspStudentDetails;
    }

    public List<HrTdIspPayments> getLetterList() {
        return letterList;
    }

    public void setLetterList(List<HrTdIspPayments> letterList) {
        this.letterList = letterList;
    }

    public HrTdIspPayments getHrTdIspPayment() {
        if (hrTdIspPayment == null) {
            hrTdIspPayment = new HrTdIspPayments();
        }
        return hrTdIspPayment;
    }

    public void setHrTdIspPayment(HrTdIspPayments hrTdIspPayment) {
        this.hrTdIspPayment = hrTdIspPayment;
    }

    public HrTdIspPaymentDetails getHrTdIspPaymentDetails() {
        if (hrTdIspPaymentDetails == null) {
            hrTdIspPaymentDetails = new HrTdIspPaymentDetails();
        }
        return hrTdIspPaymentDetails;
    }

    public void setHrTdIspPaymentDetails(HrTdIspPaymentDetails hrTdIspPaymentDetails) {
        this.hrTdIspPaymentDetails = hrTdIspPaymentDetails;
    }

    public DataModel<HrTdIspPaymentDetails> getPaymentdetailmodel() {
        if (paymentdetailmodel == null) {
            paymentdetailmodel = new ArrayDataModel<>();
        }
        return paymentdetailmodel;
    }

    public void setPaymentdetailmodel(DataModel<HrTdIspPaymentDetails> paymentdetailmodel) {
        this.paymentdetailmodel = paymentdetailmodel;
    }

    public int getSelectedRowIndexStudent() {
        return selectedRowIndexStudent;
    }

    public void setSelectedRowIndexStudent(int selectedRowIndexStudent) {
        this.selectedRowIndexStudent = selectedRowIndexStudent;
    }

    public HrTdIspStudentPlacement getHrTdIspStudentPlacement() {
        if (hrTdIspStudentPlacement == null) {
            hrTdIspStudentPlacement = new HrTdIspStudentPlacement();
        }
        return hrTdIspStudentPlacement;
    }

    public void setHrTdIspStudentPlacement(HrTdIspStudentPlacement hrTdIspStudentPlacement) {
        this.hrTdIspStudentPlacement = hrTdIspStudentPlacement;
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

    public boolean isBtnaddvisibility() {
        return btnaddvisibility;
    }

    public void setBtnaddvisibility(boolean btnaddvisibility) {
        this.btnaddvisibility = btnaddvisibility;
    }

    public String getHeaderTitle() {
        return headerTitle;
    }

    public void setHeaderTitle(String headerTitle) {
        this.headerTitle = headerTitle;
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

    public String getCreateOrSearchBundle() {
        return createOrSearchBundle;
    }

    public void setCreateOrSearchBundle(String createOrSearchBundle) {
        this.createOrSearchBundle = createOrSearchBundle;
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

    public boolean isBtnNewRender() {
        return btnNewRender;
    }

    public void setBtnNewRender(boolean btnNewRender) {
        this.btnNewRender = btnNewRender;
    }

    public String getRenderpnlContrat() {
        return renderpnlContrat;
    }

    public void setRenderpnlContrat(String renderpnlContrat) {
        this.renderpnlContrat = renderpnlContrat;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public int getUpdateStatus() {
        return updateStatus;
    }

    public void setUpdateStatus(int updateStatus) {
        this.updateStatus = updateStatus;
    }

    public DataModel<HrTdIspStudentDetails> getStatusPlacedmodel() {
        if (StatusPlacedmodel == null) {
            StatusPlacedmodel = new ArrayDataModel<>();
        }
        return StatusPlacedmodel;
    }

    public void setStatusPlacedmodel(DataModel<HrTdIspStudentDetails> StatusPlacedmodel) {
        this.StatusPlacedmodel = StatusPlacedmodel;
    }

    public String getPreparedDate() {
        return preparedDate;
    }

    public void setPreparedDate(String preparedDate) {
        this.preparedDate = preparedDate;
    }

    public HrEmployees getHrEmployees() {
        return hrEmployees;
    }

    public void setHrEmployees(HrEmployees hrEmployees) {
        this.hrEmployees = hrEmployees;
    }

    public HrTdIspStudents getHrTdIspStudents() {
        return hrTdIspStudents;
    }

    public void setHrTdIspStudents(HrTdIspStudents hrTdIspStudents) {
        this.hrTdIspStudents = hrTdIspStudents;
    }

    public List<HrTdIspStudents> getBudgetYear() {
        return budgetYear;
    }

    public void setBudgetYear(List<HrTdIspStudents> budgetYear) {
        this.budgetYear = budgetYear;
    }

    public List<HrTdIspStudents> getSemisters() {
        if (semisters == null) {
            semisters = new ArrayList<>();
        }
        return semisters;
    }

    public void setSemisters(List<HrTdIspStudents> semisters) {
        this.semisters = semisters;
    }

    public List<HrTdIspStudentDetails> getPlacedStudentList() {
        if (placedStudentList == null) {
            placedStudentList = new ArrayList<>();
        }
        return placedStudentList;
    }

    public void setPlacedStudentList(List<HrTdIspStudentDetails> placedStudentList) {
        this.placedStudentList = placedStudentList;
    }

    public ArrayList<SelectItem> getListOfmonth() {
        return listOfmonth;
    }

    public void setListOfmonth(ArrayList<SelectItem> listOfmonth) {
        this.listOfmonth = listOfmonth;
    }

    public List<HrTdIspStudentDetails> getDetaillist() {
        return detaillist;
    }

    public void setDetaillist(List<HrTdIspStudentDetails> detaillist) {
        this.detaillist = detaillist;
    }

    public Set<String> getCheckDuplicate() {
        return checkDuplicate;
    }

    public void setCheckDuplicate(Set<String> checkDuplicate) {
        this.checkDuplicate = checkDuplicate;
    }

    public DataModel<HrTdIspPayments> getPaymentDataModel() {
        if (paymentDataModel == null) {
            paymentDataModel = new ArrayDataModel<>();
        }
        return paymentDataModel;
    }

    public void setPaymentDataModel(DataModel<HrTdIspPayments> paymentDataModel) {
        this.paymentDataModel = paymentDataModel;
    }

    //</editor-fold>  
    // <editor-fold defaultstate="collapsed" desc="Search">
    public void newOrSearchPage() {
        switch (createOrSearchBundle) {
            case "New":
                renderPnlCreateGatePass = true;
                renderPnlManPage = false;
                btnNewRender = false;
                createOrSearchBundle = "Search";
                headerTitle = "Internship student payment";
                saveorUpdateBundle = "save";
                setIcone("ui-icon-search");
                break;
            case "Search":
                renderPnlCreateGatePass = false;
                renderPnlManPage = true;
                btnNewRender = false;
                createOrSearchBundle = "New";
                headerTitle = "Search...";
                setIcone("ui-icon-plus");
                break;
        }
    }

    public void newButtonAction() {
        resetInternshipPayment();
        renderPnlCreateGatePass = true;
        renderPnlManPage = false;
        btnNewRender = false;
        updateStatus = 0;
        saveorUpdateBundle = "Save";
    }

    public void searchPage() {
        renderPnlCreateGatePass = false;
        renderPnlManPage = true;
        headerTitle = "Search...";
    }

    public void findInternshipPayment(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            String refereletter = event.getNewValue().toString();
            paymentDataModel = new ListDataModel(new ArrayList(hrInternshipPaymentBeanLocal.findByLetter(refereletter)));
        }
    }

    public void populate(SelectEvent events) {
        hrTdIspPayment = (HrTdIspPayments) events.getObject();
        calculatingSubTotal();
        recreatpaymentDetailDatamodel();
        disableBtnCreate = false;
        renderPnlCreateGatePass = true;
        renderPnlManPage = false;
        renderPnlCreateAdditional = true;
        btnNewRender = true;
        updateStatus = 1;
        saveorUpdateBundle = "Update";
        createOrSearchBundle = "Search";
        setIcone("ui-icon-search");
    }

    public void findByYear(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            Integer years = Integer.parseInt(event.getNewValue().toString());
            semisters = hrInternshipPaymentBeanLocal.findByYear(years);
        }
    }

    public ArrayList<SelectItem> listOfmonth() {
        ArrayList<SelectItem> list_Year = new ArrayList<>();
        int year = getmonth(StringDateManipulation.toDayInEc());
        list_Year.add(new SelectItem(null, "-- Select --"));
        for (int i = 0; i < 12; i++) {
            list_Year.add(new SelectItem(Integer.toString(year), Integer.toString(year)));
            year -= 1;

        }
        return list_Year;
    }

    private int getmonth(String _date) {
        String dmy[] = _date.split("-");

        return Integer.parseInt(dmy[1]);
    }

    public ArrayList<SelectItem> ListofMonths() {
        ArrayList<SelectItem> list_Bones = new ArrayList<>();
        list_Bones.add(new SelectItem(null, "-- Select --"));
        list_Bones.add(new SelectItem("1", "September "));
        list_Bones.add(new SelectItem("2", "October "));
        list_Bones.add(new SelectItem("3", "November"));
        list_Bones.add(new SelectItem("4", "December"));
        list_Bones.add(new SelectItem("5", "January"));
        list_Bones.add(new SelectItem("6", "February"));
        list_Bones.add(new SelectItem("7", "March"));
        list_Bones.add(new SelectItem("8", "April"));
        list_Bones.add(new SelectItem("9", "May "));
        list_Bones.add(new SelectItem("10", "June"));
        list_Bones.add(new SelectItem("11", "July"));
        list_Bones.add(new SelectItem("12", "Augest"));
        return list_Bones;
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Main Methods">

    public void semisiter_vcl(ValueChangeEvent event) {
        if (!event.getNewValue().toString().isEmpty()) {
            hrTdIspStudents = (HrTdIspStudents) event.getNewValue();
            hrTdIspStudentDetails.setInternshipStudentId(hrTdIspStudents);
            placedStudentList = hrInternshipPaymentBeanLocal.findAll();
        }
    }

    public void studentListner(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            hrTdIspStudentDetails = (HrTdIspStudentDetails) event.getNewValue();
            hrTdIspStudentDetails.setId(hrTdIspStudentDetails.getId());
            hrTdIspStudentDetails = hrInternshipPaymentBeanLocal.findByidObj(hrTdIspStudentDetails.getId());
            if (hrTdIspStudentDetails.getHrTdIspStudentPlacementList().size() > 0 && hrTdIspStudentPlacement != null) {
                hrTdIspStudentPlacement = hrTdIspStudentDetails.getHrTdIspStudentPlacementList().get(0);
                dept = hrTdIspStudentPlacement.getDeptId().getDepName();
            }
            hrTdIspStudentPlacement = hrInternshipPaymentfacade.searchDepartment(hrTdIspStudentDetails);
            hrTdIspPaymentDetails.setStudentId(hrTdIspStudentDetails);
            if ((hrTdIspStudentDetails.getStatus().equals(3))
                    || (hrTdIspStudentDetails.getStatus().equals(-3))) {
                disableBtn = "true";
            } else {
                disableBtn = "false";
            }
        }
    }

    public void add() {
        try {
            if (checkDuplicate.contains(hrTdIspStudentDetails.getStudentId())) {
                JsfUtil.addFatalMessage("Student Id " + hrTdIspStudentDetails.getStudentId() + "  is Duplicated!!!");
            } else {
                SecureRandom random = new SecureRandom();
                Integer num = random.nextInt(100000);
                String formatted = String.format("%05d", num);
                hrTdIspStudentDetails.setStatus(4);
                detaillist.add(hrTdIspStudentDetails);
                hrTdIspPaymentDetails.setReferenceNo("INP" + formatted);
                hrTdIspPayment.add(hrTdIspPaymentDetails);
                checkDuplicate.add(hrTdIspStudentDetails.getStudentId());
                recreatpaymentDetailDatamodel();
                calculatingSubTotal();
                clearpaid();
            }
        } catch (Exception e) {
            e.printStackTrace();
            JsfUtil.addFatalMessage("error occured");
        }
    }

    public void recreatpaymentDetailDatamodel() {
        paymentdetailmodel = null;
        paymentdetailmodel = new ListDataModel<>(new ArrayList(hrTdIspPayment.getHrTdIspPaymentDetailsList()));
    }

    public void clearpaid() {
        dept = null;
        hrTdIspPaymentDetails = new HrTdIspPaymentDetails();
        hrTdIspStudentDetails = new HrTdIspStudentDetails();
    }

    public void populatesDetial(SelectEvent events) {
        hrTdIspPaymentDetails = (HrTdIspPaymentDetails) events.getObject();
        hrTdIspStudentDetails = hrTdIspPaymentDetails.getStudentId();
        hrTdIspStudents = hrTdIspStudentDetails.getInternshipStudentId();
        semisters = new ArrayList<>();
        semisters.add(hrTdIspStudents);
        placedStudentList = hrInternshipPaymentBeanLocal.findAll();
        if (hrTdIspStudentDetails.getHrTdIspStudentPlacementList().size() > 0 && hrTdIspStudentPlacement != null) {
            hrTdIspStudentPlacement = hrTdIspStudentDetails.getHrTdIspStudentPlacementList().get(0);
            dept = hrTdIspStudentPlacement.getDeptId().getDepName();
        }
        hrTdIspStudentPlacement = hrInternshipPaymentfacade.searchDepartment(hrTdIspStudentDetails);
        hrTdIspPaymentDetails.setStudentId(hrTdIspStudentDetails);
        recreatpaymentDetailDatamodel();
        addorUpdate = "Modify";
    }

    public void saveInternshipPayment() {
        try {
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(sessionBeanLocal.getUserName(), "saveInternshipPayment", dataset)) {

                hrTdIspPayment.setPreparedBy(sessionBeanLocal.getUserId());
                if ((!(paymentdetailmodel.getRowCount() > 0))) {
                    JsfUtil.addFatalMessage("Data Table Should be filled");
                } else {
                    try {
                        for (int i = 0; i < detaillist.size(); i++) {
                            hrInternshipStudentDetailsBeanLocal.update(detaillist.get(i));
                            hrInternshipPaymentBeanLocal.saveOrUpdate(hrTdIspPayment);
                        }
                        if (updateStatus == 0) {
                            JsfUtil.addSuccessMessage("Saved Sucessfully");
                            resetInternshipPayment();
                        } else {
                            JsfUtil.addSuccessMessage("Modified Sucessfully");
                            resetInternshipPayment();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        JsfUtil.addFatalMessage("Somthing occure when data Save");
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
                JAXBElement<String> program = new JAXBElement<String>(qualifiedName, String.class, null, "saveInternshipPayment");
                eventEntry.setProgram(program);
                security.addEventLog(eventEntry, dataset);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void calculatingSubTotal() {
        for (HrTdIspPaymentDetails hrTdIspPaymentDetails : hrTdIspPayment.getHrTdIspPaymentDetailsList()) {
            subTotal = subTotal.add(hrTdIspPaymentDetails.getAmountPaid());
            subTotal = subTotal.setScale(2, BigDecimal.ROUND_HALF_EVEN);
            subTotal = subTotal;
        }
    }

    public void resetInternshipPayment() {
        subTotal = null;
        hrTdIspStudents = new HrTdIspStudents();
        hrTdIspPayment = new HrTdIspPayments();
        paymentdetailmodel = null;
        hrTdIspStudentDetails = new HrTdIspStudentDetails();
    }
    //</editor-fold>
}
