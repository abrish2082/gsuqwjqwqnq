package et.gov.eep.hrms.controller.leave;

import com.lowagie.text.pdf.codec.Base64.InputStream;
import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.commonApplications.utility.date.StringDateManipulation;
import et.gov.eep.fcms.businessLogic.admin.FmsAccountingPeriodBeanLocal;
import et.gov.eep.fcms.entity.admin.FmsAccountingPeriod;
import et.gov.eep.hrms.businessLogic.employee.HrEmployeeBeanLocal;
import et.gov.eep.hrms.businessLogic.leave.HrLeaveBalanceBeanLocal;
import et.gov.eep.hrms.businessLogic.leave.LeaveRequestBeanLocal;
import et.gov.eep.hrms.businessLogic.leave.LeaveSettingBeanLocal;
import et.gov.eep.hrms.entity.employee.HrEmployees;
import et.gov.eep.hrms.entity.leave.HrLeaveBalance;
import et.gov.eep.hrms.entity.leave.HrLeaveRequest;
import et.gov.eep.hrms.entity.leave.HrLeaveSetting;
import et.gov.eep.hrms.entity.leave.HrLuLeaveTypes;
import et.gov.eep.hrms.entity.leave.HrLuLeaveYear;
import et.gov.eep.hrms.mapper.leave.HrLeaveBalanceFacade;
import et.gov.eep.hrms.mapper.leave.HrLuLeaveTypesFacade;
import et.gov.eep.hrms.mapper.leave.HrLuLeaveYearFacade;
import javax.inject.Named;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.faces.context.ExternalContext;
import javax.faces.view.ViewScoped;
import org.insa.client.DmsHandler;
import org.insa.model.DocList;
import org.insa.model.DocumentModel;
import org.insa.util.Utility;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultStreamedContent;
import java.io.File;
import java.io.FileInputStream;
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import org.apache.commons.io.FileUtils;
import org.primefaces.model.StreamedContent;
import securityBean.SessionBean;
import webService.AAA;
import webService.EventEntry;
import webService.IAdministration;

/**
 *
 * @author prg
 */
@Named(value = "leaveReturn")
@ViewScoped
public class LeaveReturn implements Serializable {

    //<editor-fold defaultstate="collapsed" desc="Entity Injection">
    @Inject
            SessionBean sessionBean;
    @Inject
    private HrEmployees employee;
    @Inject
    private HrLeaveRequest leaveRequest;
    @Inject
    private HrLeaveSetting hrLeaveSetting;
    @Inject
    private HrLuLeaveTypes hrLuLeaveTypes;
    @Inject
    private HrLuLeaveYear luLeaveYear;
    @Inject
    private HrLeaveBalance leaveBalance;
    @Inject
    private FmsAccountingPeriod accountingPeriod;
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="EJB Declaration">
    @EJB
            HrLuLeaveYearFacade luLeaveYearFacade;
    @EJB
            HrEmployeeBeanLocal hrEmployeeBean;
    @EJB
            HrLeaveBalanceBeanLocal balanceBeanLocal;
    @EJB
            HrLuLeaveTypesFacade luLeaveTypesFacade;
    @EJB
            LeaveSettingBeanLocal settingBeanLocal;
    @EJB
            LeaveRequestBeanLocal requestBeanLocal;
    @EJB
            HrLeaveBalanceFacade hrLeaveBalanceFacade;
    @EJB
    private FmsAccountingPeriodBeanLocal accountingPeriodBeanLocal;
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="variable initialization">
    HrLeaveBalance lvBalance = new HrLeaveBalance();
    boolean renderBalance = false;
    boolean balanceBtn = true;
    int update = 0;
    int reqSize = 0;
    double totalLeaveDays;
    double proUnusedDays;
    private String createOrSearchBundle = "New";
    private boolean renderPnlCreateAdditional = true;
    private boolean renderPnlManPage = true;
    private String icone = "ui-icon-document";
    private String SaveOrUpdateButton = "Save";
//</editor-fold>
            
    //<editor-fold defaultstate="collapsed" desc="List and datamodel Initialization">
    List<HrLeaveRequest> requestsList = new ArrayList<>();
    List<HrLuLeaveTypes> lvList = new ArrayList<>();
    List<HrLeaveBalance> balanceList = new ArrayList<>();
    DataModel<HrLeaveRequest> leaveRequestDataModel;
    DataModel<HrLeaveBalance> balanceDataModel;
    
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="post construct">
    @PostConstruct
    public void init() {
        requestsList = requestBeanLocal.populateEmployeesOnLeave();
        System.out.println("--requestsList---" + requestsList);
        recreateLeaveRequestModel();
    }
//</editor-fold>

    //    //<editor-fold defaultstate="collapsed" desc="Getter And Setter">
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

    public String getSaveOrUpdateButton() {
        return SaveOrUpdateButton;
    }

    public void setSaveOrUpdateButton(String SaveOrUpdateButton) {
        this.SaveOrUpdateButton = SaveOrUpdateButton;
    }

    public void createNewAdditionalAmount() {
        if (createOrSearchBundle.equals("New")) {
            renderPnlCreateAdditional = true;
            renderPnlManPage = false;
            //createOrSearchBundle = "Search";
            setIcone("ui-icon-search");
        }
//        
    }

    public int getReqSize() {
        return reqSize;
    }

    public void setReqSize(int reqSize) {
        this.reqSize = reqSize;
    }

    public boolean isBalanceBtn() {
        return balanceBtn;
    }

    public void setBalanceBtn(boolean balanceBtn) {
        this.balanceBtn = balanceBtn;
    }

    public double getTotalLeaveDays() {
        return totalLeaveDays;
    }

    public void setTotalLeaveDays(double totalLeaveDays) {
        this.totalLeaveDays = totalLeaveDays;
    }

    public HrLeaveBalance getLeaveBalance() {
        if (leaveBalance == null) {
            leaveBalance = new HrLeaveBalance();
        }
        return leaveBalance;
    }

    public void setLeaveBalance(HrLeaveBalance leaveBalance) {
        this.leaveBalance = leaveBalance;
    }

    public DataModel<HrLeaveRequest> getLeaveRequestDataModel() {
        if (leaveRequestDataModel == null) {
            leaveRequestDataModel = new ListDataModel<>();
        }
        return leaveRequestDataModel;
    }

    public DataModel<HrLeaveBalance> getBalanceDataModel() {
        if (balanceDataModel == null) {
            balanceDataModel = new ListDataModel<>();
        }
        return balanceDataModel;
    }

    public void setBalanceDataModel(DataModel<HrLeaveBalance> balanceDataModel) {
        this.balanceDataModel = balanceDataModel;
    }

    public void setLeaveRequestDataModel(DataModel<HrLeaveRequest> leaveRequestDataModel) {
        this.leaveRequestDataModel = leaveRequestDataModel;
    }

    public HrLuLeaveYear getLuLeaveYear() {
        if (luLeaveYear == null) {
            luLeaveYear = new HrLuLeaveYear();
        }
        return luLeaveYear;
    }

    public void setLuLeaveYear(HrLuLeaveYear luLeaveYear) {
        this.luLeaveYear = luLeaveYear;
    }

    public List<HrLeaveBalance> getBalanceList() {
        if (balanceList == null) {
            balanceList = new ArrayList<>();
        }
        return balanceList;
    }

    public void setBalanceList(List<HrLeaveBalance> balanceList) {
        this.balanceList = balanceList;
    }

    public boolean isRenderBalance() {
        return renderBalance;
    }

    public void setRenderBalance(boolean renderBalance) {
        this.renderBalance = renderBalance;
    }

    public HrLeaveRequest getLeaveRequest() {
        if (leaveRequest == null) {
            leaveRequest = new HrLeaveRequest();
        }
        return leaveRequest;
    }

    public void setLeaveRequest(HrLeaveRequest leaveRequest) {
        this.leaveRequest = leaveRequest;
    }

    public HrEmployees getEmployee() {
        if (employee == null) {
            employee = new HrEmployees();
        }
        return employee;
    }

    public void setEmployee(HrEmployees employee) {
        this.employee = employee;
    }

    public HrLeaveSetting getHrLeaveSetting() {
        if (hrLeaveSetting == null) {
            hrLeaveSetting = new HrLeaveSetting();
        }
        return hrLeaveSetting;
    }

    public void setHrLeaveSetting(HrLeaveSetting hrLeaveSetting) {
        this.hrLeaveSetting = hrLeaveSetting;
    }

    public HrLuLeaveTypes getHrLuLeaveTypes() {
        if (hrLuLeaveTypes == null) {
            hrLuLeaveTypes = new HrLuLeaveTypes();
        }
        return hrLuLeaveTypes;
    }

    public void setHrLuLeaveTypes(HrLuLeaveTypes hrLuLeaveTypes) {
        this.hrLuLeaveTypes = hrLuLeaveTypes;
    }

    public SelectItem[] getLeavetypeList() {
        return JsfUtil.getSelectItems(lvList, true);
    }

    public void recreateLeaveRequestModel() {
        leaveRequestDataModel = null;
        leaveRequestDataModel = new ListDataModel(new ArrayList<>(requestsList));
    }

    public void recreateBalanceDataModel() {
        balanceDataModel = null;
        balanceDataModel = new ListDataModel(new ArrayList<>(balanceList));
        int x = balanceList.size();
        if (x > 0) {
            totalLeaveDays = 0;
            for (int i = 0; i < x; i++) {
                totalLeaveDays = totalLeaveDays + balanceList.get(i).getRemainingDays();
            }
        }
    }

////</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Main Methods ">
    public void fill_HR_Requests(HrEmployees employee) {
        requestsList = null;
        requestsList = new ArrayList<>();
        requestsList = requestBeanLocal.populateEmployeesOnLeave();
        System.out.println("--requestsList---" + requestsList);
//        requestsList = requestBeanLocal.findOrderedEmployeeLeave(employee);
        reqSize = requestsList.size();
        recreateLeaveRequestModel();
        
    }
    
    public void populateLeaveReturn() {
        
        leaveRequest = leaveRequestDataModel.getRowData();
        hrLuLeaveTypes = leaveRequest.getLeaveTypesId();
        employee = leaveRequest.getEmpId();
        
        lvList = null;
        lvList = new ArrayList<>();
        lvList.add(hrLuLeaveTypes);
        populateLeaveList();
        renderBalance = checkToBalance();
        loadBalance();
    }
    
    public void loadBalance() {
        if (employee != null && hrLuLeaveTypes != null) {
            renderBalance = checkToBalance();
            if (true == renderBalance) {
                balanceList = hrLeaveBalanceFacade.populateLeaveBalance(employee, hrLuLeaveTypes);
                
            }
            
        }
    }
    
    public boolean checkToBalance() {
        boolean tobalance = false;
        if (hrLuLeaveTypes != null) {
            hrLeaveSetting = settingBeanLocal.findLeaveSettingByLeaveType(hrLuLeaveTypes);
            if (hrLeaveSetting != null) {
                if (hrLeaveSetting.getToBalance() == true) {
                    tobalance = true;
                    
                }
            }
        }
        
        return tobalance;
    }
    
    public void leaveTyp(ValueChangeEvent event) {
        if (!event.getNewValue().toString().isEmpty()) {
            hrLuLeaveTypes.setLeaveName(event.getNewValue().toString());
            hrLuLeaveTypes = luLeaveTypesFacade.findLeaveTypeByName(hrLuLeaveTypes);
            leaveRequest.setLeaveTypesId(hrLuLeaveTypes);
            
        }
    }
    
    public void populateLeaveList() {
        if (employee != null) {
            List<HrLeaveSetting> l = new ArrayList<>();
            lvList = null;
            lvList = new ArrayList<>();
            String gender;
            gender = employee.getSex();
            l = settingBeanLocal.filterByGender(gender);
            int lSize = l.size();
            for (int i = 0; i < lSize; i++) {
                HrLuLeaveTypes obj = new HrLuLeaveTypes();
                obj = l.get(i).getLeaveTypeId();
                lvList.add(obj);
                obj = null;
            }
        }
    }
    
    //<editor-fold defaultstate="collapsed" desc="Add Remaing Leave Days">
    public void balanceLeaveAdd() {
        
        int size = balanceList.size();
        //  for (int i = 0; i < balanceList.size(); i++) {
        
        lvBalance = balanceList.get(0);
//            if (lvBalance.getRemainingDays() > 0) {
        Integer deposit = balanceList.get(0).getRemainingDays() + leaveRequest.getUnUsedDays();
        
        lvBalance.setRemainingDays(deposit);
        balanceBeanLocal.saveOrUpdate(lvBalance);
    }
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Save Leave Return Method">
    public void saveLeaveReturn() {
        try {
            
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(sessionBean.getUserName(), "saveLeaveReturn", dataset)) {
//                 put ur code here...!
                leaveRequest.setReturnStatus(0);
                employee = leaveRequest.getEmpId();
                employee.setEmpStatus(1);
                hrEmployeeBean.saveOrUpdate(employee);
                System.out.println("--unused save-0" + leaveRequest.getUnUsedDays());
//                if (requestBeanLocal.saveOrUpdate(leaveRequest)) {
                if (true == renderBalance && leaveRequest.getUnUsedDays() > 0) {
                    
                    balanceLeaveAdd();
                }
                requestBeanLocal.saveOrUpdate(leaveRequest);
                requestsList = null;
                requestsList = new ArrayList<>();
                requestsList = requestBeanLocal.populateEmployeesOnLeave();
                reqSize = requestsList.size();
                recreateLeaveRequestModel();
                clearLeaveReturn();
                JsfUtil.addSuccessMessage("Update Successful.");
                
//                }
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
            JsfUtil.addErrorMessage("Somthing Occured on Save");
            
        }
        
    }
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="clear Leave Return Method">
    public String clearLeaveReturn() {
        lvBalance = null;
        hrLeaveSetting = null;
        hrLuLeaveTypes = null;
        leaveRequest = null;
        employee = null;
        docValueModel = null;
        return null;
    }
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="DMS service my work">
    StreamedContent file;
    DataModel<DocumentModel> docValueModel;
    DocumentModel documentModel = new DocumentModel();
    List<DocumentModel> documentModelList = new ArrayList<DocumentModel>();
    List<String> uploadList = new ArrayList<>();
    
    public DocumentModel getDocumentModel() {
        return documentModel;
    }
    
    public void setDocumentModel(DocumentModel documentModel) {
        this.documentModel = documentModel;
    }
    
    public void recreateDataModel() {
        DmsHandler dmsHandler = new DmsHandler();
        DocList docList = new DocList();
        List<String> docId = new ArrayList<>();
//        for (int i = 0; i < leaveRequest.getAttachment(); i++) {
//            System.out.println("===========dociD============== "
//                    + hrInsuranceDiagnosisResult.getHrInsuranceDiagnosisUploadList().get(i).getDocId().toString());
//            docId.add(hrInsuranceDiagnosisResult.getHrInsuranceDiagnosisUploadList().get(i).getDocId().toString());
//        }
//        uploadList = uploadBeanLocal.findByDocId(hrInsuranceDiagnosisResult);
        System.out.println("----uploadList findBYdocId----" + uploadList);
        docId.add(uploadList.toString());
//        System.out.println("----uploadList----" + uploadList);
        docList.setDocIds(docId);
//        docList.setDocIds(docId);
        System.out.println(" ============dociD after============= " + docList.getDocIds().size());
        DocList docListNew = dmsHandler.getDocumentsById(docList);
        if (docListNew != null) {
            System.out.println(" ============dociD from DB============= " + docListNew.getDocList());
            docValueModel = new ListDataModel(docListNew.getDocList());
        }
    }
    
    public DataModel<DocumentModel> getDocValueModel() {
        return docValueModel;
    }
    
    public void setDocValueModel(DataModel<DocumentModel> docValueModel) {
        this.docValueModel = docValueModel;
    }
    
    public void onRowSelect(SelectEvent event) {
        System.out.println("===on row select==============  + " + docValueModel);
        documentModel = (DocumentModel) event.getObject();
        System.out.println("====name on row ==============  + " + documentModel.getName());
        System.out.println("====doc format on row==============  + " + documentModel.getDocFormat());
    }
    
    public void uploadListener(FileUploadEvent event) {
        try {
            DmsHandler dmsHandler = new DmsHandler();
            documentModel = new DocumentModel();
            documentModel.setDocFormat(event.getFile().getFileName().split("\\.")[1]);
            documentModel.setName(event.getFile().getFileName().split("\\.")[0]);
            documentModel.setUserId(Long.valueOf("2"));
            documentModel.setCatagoryId(Utility.CATAGORY_ID);
            documentModel.setCreater("Hrms clianct BG");
            File fileDoc = new File(event.getFile().getFileName());
            
            FileUtils.writeByteArrayToFile(fileDoc, dmsHandler.extractByteArray1(event.getFile().getInputstream()));
            documentModel.setDate(StringDateManipulation.toDayInEc());
            documentModel.setFile(fileDoc);
            documentModel.setByteFile(dmsHandler.extractByteArray1(event.getFile().getInputstream()));
            int x = dmsHandler.saveDocument(documentModel);
            System.out.println("====doc id==============  + " + x);
            if (x != -1) {
                leaveRequest.setAttachment(x);
//                hrDiagnosisUpload.setDocId(x);
//                hrInsuranceDiagnosisResult.Add(hrDiagnosisUpload);
                JsfUtil.addSuccessMessage("Upload Seccessfully");
                documentModelList.add(documentModel);
                setDocValueModel(new ListDataModel(documentModelList));
            } else {
                JsfUtil.addFatalMessage("Not Upload");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public StreamedContent getFile() throws Exception {
        if (documentModel != null) {
            System.out.println("====name ==============  + " + documentModel.getName());
            System.out.println("======doc format==============  + " + documentModel.getDocFormat());
            File fileDoc = new File(""
                    + documentModel.getName() + "."
                    + documentModel.getDocFormat());
            System.out.println("======getFile==============  + " + fileDoc);
            FileUtils.writeByteArrayToFile(fileDoc, documentModel.getByteFile());
            java.io.InputStream input = new FileInputStream(fileDoc);
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            file = new DefaultStreamedContent(input, externalContext.getMimeType(fileDoc.getName()), documentModel.getDocFormat());
            System.out.println("=====Download=============  + " + file);
            System.out.println("=====Download name=============  + " + file.getName());
            System.out.println("=====Download format=============  + " + file.getContentType());
            return file;
        }
        return null;
    }
    
    public void remove(DocumentModel document) {
        System.out.println("=====remove====" + documentModel.getName());
        System.out.println("=====remove2 ====" + documentModel.getDocFormat());
        document = documentModel;
        DmsHandler dmsHandler = new DmsHandler();
        dmsHandler.getRemove(document);
        recreateDataModel();
    }
    //</editor-fold>
//</editor-fold>
}
