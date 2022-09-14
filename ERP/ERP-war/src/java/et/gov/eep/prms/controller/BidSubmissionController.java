package et.gov.eep.prms.controller;

   // <editor-fold defaultstate="collapsed" desc="Imports">
import et.gov.eep.commonApplications.entity.WfPrmsProcessed;
import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.hrms.entity.employee.HrEmployees;
import et.gov.eep.hrms.integration.HREmployeesBeanLocal;
import et.gov.eep.prms.businessLogic.BIdSubmissionBeanLocal;

import et.gov.eep.prms.businessLogic.VendorRegBeanLocal;
import et.gov.eep.prms.entity.PrmsBid;
import et.gov.eep.prms.entity.PrmsBidSubmissionDetail;
import et.gov.eep.prms.entity.PrmsBidSubmission;
import et.gov.eep.prms.entity.PrmsSupplyProfile;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.ejb.EJB;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.event.RowEditEvent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import javax.annotation.PostConstruct;
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import org.primefaces.event.SelectEvent;
import securityBean.Constants;
import securityBean.SessionBean;
import securityBean.WorkFlow;
import webService.AAA;
import webService.EventEntry;
import webService.IAdministration;
// </editor-fold>

//Bid Submission view scoped CDI Named Bean class
@Named(value = "bidSubmissionController")
@ViewScoped
public class BidSubmissionController implements Serializable {

    // <editor-fold defaultstate="collapsed" desc="Inject EJBs">
    @EJB
    private BIdSubmissionBeanLocal bIdSubmissionBeanLocal;
    @EJB
    HREmployeesBeanLocal hREmployeesBeanLocal;
    @EJB
    VendorRegBeanLocal vendorRegBeanLocal;
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Inject Entities">
    @Inject
    WorkFlow workFlow;
    @Inject
    private PrmsBidSubmission prmsBidSubmission;
    @Inject
    private HrEmployees hrEmployees;
    @Inject
    private PrmsBidSubmissionDetail prmsBidSubmissionDetail;
    @Inject
    private PrmsBid eepBidReg;
    @Inject
    private PrmsSupplyProfile prmsSupplyProfile;
    @Inject
    WfPrmsProcessed wfPrmsProcessed;
    @Inject
    SessionBean SessionBean;
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Declare Variables">
    int saveStatus = 0;
    int updateStatus = 0;
    int selectStatue = 0;

    String logerName;
    private String userName;
    private String icone = "ui-icon-plus";
    private String saveorUpdateBundle = "Save";
    private String addressInfoButton = "Add";
    private String addOrModifyBundle = "Add";
    private String createOrSearchBundle = "New";
    private String duplicattion = null;
    private String activeIndex;
    private String selectOptPartyName;
    String newcheckListNo;
    String LastcheckListNo = "0";

    private boolean disableBtnCreate = false;
    private boolean renderPnlManPage = true;
    private boolean renderPnlCreateServiceRegistration = false;
    private boolean renderpnlToSearchPage;
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Declare Lists and models">
    private List<HrEmployees> employeeList;
    ArrayList<PrmsBid> prmsBids;
    ArrayList<PrmsSupplyProfile> prmsSupplyProfiles;
    private List<PrmsSupplyProfile> prmsSupplyProfiles1;
    private PrmsBidSubmission submissionSelection;
    private List<PrmsBidSubmission> prmsBidSubmissionsforstatus;
    List<PrmsBidSubmission> prmsBidSubmissions;
    List<PrmsBid> prmsBidList;
    List<PrmsBidSubmission> bidSummSearchParameterLst;
    DataModel<PrmsBidSubmissionDetail> prmsBidSubmissionDetailsDataModel;
    DataModel<PrmsBidSubmission> PrmsBidSubmissionDataModel;

    Set<String> addressCheck = new HashSet<>();
    Set<String> actionPlnDetlCheck = new HashSet<>();
    // </editor-fold>

    //default constructor method
    public BidSubmissionController() {

    }

    //life cycle callback method
    @PostConstruct
    public void init() {
        prmsBidSubmissionsforstatus = bIdSubmissionBeanLocal.searchBidSubmission();
        setLogerName(SessionBean.getUserName());
        wfPrmsProcessed.setProcessedBy(SessionBean.getUserId());
        setUserName(workFlow.getUserName());
        employeeList = hREmployeesBeanLocal.GetEmployees();
    }

    // <editor-fold defaultstate="collapsed" desc="getter and setter of objects">
    public WfPrmsProcessed getWfPrmsProcessed() {
        if (wfPrmsProcessed == null) {
            wfPrmsProcessed = new WfPrmsProcessed();
        }
        return wfPrmsProcessed;
    }

    public void setWfPrmsProcessed(WfPrmsProcessed wfPrmsProcessed) {
        this.wfPrmsProcessed = wfPrmsProcessed;
    }

    public PrmsBidSubmission getPrmsBidSubmission() {
        if (prmsBidSubmission == null) {
            prmsBidSubmission = new PrmsBidSubmission();
        }
        return prmsBidSubmission;
    }

    public void setPrmsBidSubmission(PrmsBidSubmission prmsBidSubmission) {
        this.prmsBidSubmission = prmsBidSubmission;
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

    public PrmsBidSubmissionDetail getPrmsBidSubmissionDetail() {
        if (prmsBidSubmissionDetail == null) {
            prmsBidSubmissionDetail = new PrmsBidSubmissionDetail();
        }
        return prmsBidSubmissionDetail;
    }

    public void setPrmsBidSubmissionDetail(PrmsBidSubmissionDetail prmsBidSubmissionDetail) {
        this.prmsBidSubmissionDetail = prmsBidSubmissionDetail;
    }

    public PrmsBid getEepBidReg() {
        if (eepBidReg == null) {
            eepBidReg = new PrmsBid();
        }
        return eepBidReg;
    }

    public void setEepBidReg(PrmsBid eepBidReg) {
        this.eepBidReg = eepBidReg;
    }

    public PrmsSupplyProfile getPrmsSupplyProfile() {
        if (prmsSupplyProfile == null) {
            prmsSupplyProfile = new PrmsSupplyProfile();
        }
        return prmsSupplyProfile;
    }

    public void setPrmsSupplyProfile(PrmsSupplyProfile prmsSupplyProfile) {
        this.prmsSupplyProfile = prmsSupplyProfile;
    }

    public WorkFlow getWorkFlow() {
        if (workFlow == null) {
            workFlow = new WorkFlow();
        }
        return workFlow;
    }

    public void setWorkFlow(WorkFlow workFlow) {
        this.workFlow = workFlow;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="getter and setter of Lists and models">
    public List<PrmsBidSubmission> getPrmsBidSubmissionsforstatus() {
        if (prmsBidSubmissionsforstatus == null) {
            prmsBidSubmissionsforstatus = new ArrayList<>();
        }
        return prmsBidSubmissionsforstatus;
    }

    public void setPrmsBidSubmissionsforstatus(List<PrmsBidSubmission> prmsBidSubmissionsforstatus) {
        this.prmsBidSubmissionsforstatus = prmsBidSubmissionsforstatus;
    }

    public List<HrEmployees> getEmployeeList() {
        if (employeeList == null) {
            employeeList = new ArrayList<>();
        }
        return employeeList;
    }

    public void setEmployeeList(List<HrEmployees> employeeList) {
        this.employeeList = employeeList;
    }

    public List<PrmsSupplyProfile> getPrmsSupplyProfiles1() {
        if (prmsSupplyProfiles1 == null) {
            prmsSupplyProfiles1 = new ArrayList<>();
        }
        return prmsSupplyProfiles1;
    }

    public void setPrmsSupplyProfiles1(List<PrmsSupplyProfile> prmsSupplyProfiles1) {
        this.prmsSupplyProfiles1 = prmsSupplyProfiles1;
    }

    public List<PrmsBid> getPrmsBidList() {
        if (prmsBidList == null) {
            prmsBidList = new ArrayList<>();
        }
        return prmsBidList;
    }

    public void setPrmsBidList(List<PrmsBid> prmsBidList) {
        this.prmsBidList = prmsBidList;
    }

    public List<PrmsBidSubmission> getPrmsBidSubmissions() {
        if (prmsBidSubmissions == null) {
            prmsBidSubmissions = new ArrayList<>();
        }
        return prmsBidSubmissions;
    }

    public void setPrmsBidSubmissions(List<PrmsBidSubmission> prmsBidSubmissions) {
        this.prmsBidSubmissions = prmsBidSubmissions;
    }

    public ArrayList<PrmsBid> getPrmsBids() {
        if (prmsBids == null) {
            prmsBids = new ArrayList<>();
        }
        return prmsBids;
    }

    public void setPrmsBids(ArrayList<PrmsBid> prmsBids) {
        this.prmsBids = prmsBids;
    }

    public ArrayList<PrmsSupplyProfile> getPrmsSupplyProfiles() {
        if (prmsSupplyProfiles == null) {
            prmsSupplyProfiles = new ArrayList<>();
        }
        return prmsSupplyProfiles;
    }

    public void setPrmsSupplyProfiles(ArrayList<PrmsSupplyProfile> prmsSupplyProfiles) {
        this.prmsSupplyProfiles = prmsSupplyProfiles;
    }

    public DataModel<PrmsBidSubmissionDetail> getPrmsBidSubmissionDetailsDataModel() {
        if (prmsBidSubmissionDetailsDataModel == null) {
            prmsBidSubmissionDetailsDataModel = new ListDataModel<>();
        }
        return prmsBidSubmissionDetailsDataModel;
    }

    public void setPrmsBidSubmissionDetailsDataModel(DataModel<PrmsBidSubmissionDetail> prmsBidSubmissionDetailsDataModel) {
        this.prmsBidSubmissionDetailsDataModel = prmsBidSubmissionDetailsDataModel;
    }

    public DataModel<PrmsBidSubmission> getPrmsBidSubmissionDataModel() {
        if (PrmsBidSubmissionDataModel == null) {
            PrmsBidSubmissionDataModel = new ListDataModel<>();
        }

        return PrmsBidSubmissionDataModel;
    }

    public void setPrmsBidSubmissionDataModel(DataModel<PrmsBidSubmission> PrmsBidSubmissionDataModel) {
        this.PrmsBidSubmissionDataModel = PrmsBidSubmissionDataModel;
    }

    public Set<String> getAddressCheck() {
        return addressCheck;
    }

    public void setAddressCheck(Set<String> addressCheck) {
        this.addressCheck = addressCheck;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="getter and setter of variables">
    public String getLogerName() {
        return logerName;
    }

    public void setLogerName(String logerName) {
        this.logerName = logerName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getSelectOptPartyName() {
        return selectOptPartyName;
    }

    public void setSelectOptPartyName(String selectOptPartyName) {
        this.selectOptPartyName = selectOptPartyName;
    }

    public String getSaveorUpdateBundle() {
        return saveorUpdateBundle;
    }

    public void setSaveorUpdateBundle(String saveorUpdateBundle) {
        this.saveorUpdateBundle = saveorUpdateBundle;
    }

    public String getAddressInfoButton() {
        return addressInfoButton;
    }

    public void setAddressInfoButton(String addressInfoButton) {
        this.addressInfoButton = addressInfoButton;
    }

    public String getCreateOrSearchBundle() {
        return createOrSearchBundle;
    }

    public void setCreateOrSearchBundle(String createOrSearchBundle) {
        this.createOrSearchBundle = createOrSearchBundle;
    }

    public String getAddOrModifyBundle() {
        return addOrModifyBundle;
    }

    public void setAddOrModifyBundle(String addOrModifyBundle) {
        this.addOrModifyBundle = addOrModifyBundle;
    }

    public String getActiveIndex() {
        return activeIndex;
    }

    public void setActiveIndex(String activeIndex) {
        this.activeIndex = activeIndex;
    }

    public String getIcone() {
        return icone;
    }

    public void setIcone(String icone) {
        this.icone = icone;
    }

    public String getDuplicattion() {
        return duplicattion;
    }

    public void setDuplicattion(String duplicattion) {
        this.duplicattion = duplicattion;
    }

    public boolean isDisableBtnCreate() {
        return disableBtnCreate;
    }

    public void setDisableBtnCreate(boolean disableBtnCreate) {
        this.disableBtnCreate = disableBtnCreate;
    }

    public boolean isRenderPnlCreateServiceRegistration() {
        return renderPnlCreateServiceRegistration;
    }

    public void setRenderPnlCreateServiceRegistration(boolean renderPnlCreateServiceRegistration) {
        this.renderPnlCreateServiceRegistration = renderPnlCreateServiceRegistration;
    }

    public boolean isRenderpnlToSearchPage() {
        return renderpnlToSearchPage;
    }

    public void setRenderpnlToSearchPage(boolean renderpnlToSearchPage) {
        this.renderpnlToSearchPage = renderpnlToSearchPage;
    }

    public boolean isRenderPnlManPage() {
        return renderPnlManPage;
    }

    public void setRenderPnlManPage(boolean renderPnlManPage) {
        this.renderPnlManPage = renderPnlManPage;
    }

    public int getUpdateStatus() {
        return updateStatus;
    }

    public void setUpdateStatus(int updateStatus) {
        this.updateStatus = updateStatus;
    }

    public int getSelectStatue() {
        return selectStatue;
    }

    public void setSelectStatue(int selectStatue) {
        this.selectStatue = selectStatue;
    }

    public int getSaveStatus() {
        return saveStatus;
    }

    public void setSaveStatus(int saveStatus) {
        this.saveStatus = saveStatus;
    }

    public PrmsBidSubmission getSubmissionSelection() {
        return submissionSelection;
    }

    public void setSubmissionSelection(PrmsBidSubmission submissionSelection) {
        this.submissionSelection = submissionSelection;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="change of Events">

    public List<PrmsBidSubmission> getBidSummSearchParameterLst() {
        if (bidSummSearchParameterLst == null) {
            bidSummSearchParameterLst = new ArrayList<>();
            bidSummSearchParameterLst = bIdSubmissionBeanLocal.getParamNameList();
        }
        return bidSummSearchParameterLst;
    }

    public void setBidSummSearchParameterLst(List<PrmsBidSubmission> bidSummSearchParameterLst) {
        this.bidSummSearchParameterLst = bidSummSearchParameterLst;
    }
    
     public void selectedParamChangeEvent(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            prmsBidSubmission.setColumnName(event.getNewValue().toString());
            prmsBidSubmission.setColumnValue(null);
        }
    }
    
    public void rowSelect(SelectEvent event) {
        prmsBidSubmission = (PrmsBidSubmission) event.getObject();
        eepBidReg = prmsBidSubmission.getBidId();

        prmsBidSubmission.setBidSubId(prmsBidSubmission.getBidSubId());
        prmsBidSubmission = bIdSubmissionBeanLocal.getSelectedRequest(prmsBidSubmission.getBidSubId());
        renderPnlManPage = false;
        renderpnlToSearchPage = true;
        renderPnlCreateServiceRegistration = true;
        saveorUpdateBundle = "Update";

        updateStatus = 1;
        recreateprmsBidSubmissionDetailsDataModel();
    }

    public void BidDateGetter(ValueChangeEvent event) {
        if (null != event.getNewValue()) {
            eepBidReg = (PrmsBid) event.getNewValue();
            prmsBidSubmission.setBidId(eepBidReg);
            prmsSupplyProfiles1 = bIdSubmissionBeanLocal.getSupplierList(eepBidReg);

        }

    }

    public void getVendorName(ValueChangeEvent event) {
        if (null != event.getNewValue()) {
            prmsSupplyProfile = (PrmsSupplyProfile) event.getNewValue();
            prmsBidSubmissionDetail.setSupplierId(prmsSupplyProfile);

        }

    }

    public void biddingCompanyAddress(ValueChangeEvent event) {
        if (null != event.getNewValue()) {
            prmsSupplyProfiles = bIdSubmissionBeanLocal.biddingCompanyAddress(event.getNewValue().toString());
        }
    }

    public void onContactRowCancel(RowEditEvent event) {

    }

    public void onRowEdit(RowEditEvent event) {
        renderPnlManPage = false;
        activeIndex = "0";
        saveorUpdateBundle = "Update";
        createOrSearchBundle = "Search";
        setIcone("ui-icon-search");
        disableBtnCreate = true;
        updateStatus = 1;

        int rowIndex = PrmsBidSubmissionDataModel.getRowIndex();
        prmsBidSubmission = prmsBidSubmissions.get(rowIndex);

        recreatePrmsBidSubmissionDataModel();
        recreateprmsBidSubmissionDetailsDataModel();
    }

    public void onContactRowEdit(RowEditEvent event) {

        int rowIndex = prmsBidSubmissionDetailsDataModel.getRowIndex();

        PrmsBidSubmissionDetail comContPerson = (PrmsBidSubmissionDetail) event.getObject();

        boolean found = false;
        for (int i = 0; i < prmsBidSubmission.getPrmsBidSubmissionDetailList().size(); i++) {
            if (i != rowIndex) {
                if (prmsBidSubmission.getPrmsBidSubmissionDetailList().get(i).getRemark().equals(comContPerson.getRemark())
                        && prmsBidSubmission.getPrmsBidSubmissionDetailList().get(i).getDateSub().equals(comContPerson.getDateSub())) {
                    found = true;
                    break;
                }
            }
        }

        if (found == true) {
            JsfUtil.addSuccessMessage("Duplicate Entry Is Not Allowed !!");

            comContPerson.setDateSub(null);
            prmsBidSubmission.getPrmsBidSubmissionDetailList().set(rowIndex, comContPerson);
            recreateprmsBidSubmissionDetailsDataModel();
        } else {
            prmsBidSubmission.getPrmsBidSubmissionDetailList().set(rowIndex, comContPerson);
            recreateprmsBidSubmissionDetailsDataModel();
        }
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="other methods">
    public void createNewParty() {
        saveorUpdateBundle = "Save";
        disableBtnCreate = false;
        renderpnlToSearchPage = false;

        if (createOrSearchBundle.equals("New")) {
            clearprmsBidSubmission();
            renderPnlCreateServiceRegistration = true;
            renderPnlManPage = false;
            createOrSearchBundle = "Search";
            setIcone("ui-icon-search");
        } else if (createOrSearchBundle.equals("Search")) {
            renderPnlCreateServiceRegistration = false;
            renderPnlManPage = true;
            createOrSearchBundle = "New";
            setIcone("ui-icon-plus");
        }
    }

    public void goBackSearchPageBtnAction() {
        renderPnlCreateServiceRegistration = false;
        renderpnlToSearchPage = false;
        renderPnlManPage = true;
    }

    public void searchBidSubmission() {
        prmsBidSubmission.setPreparedBy(workFlow.getUserAccount());

        prmsBidSubmissions = bIdSubmissionBeanLocal.searchBidSubmission(prmsBidSubmission);
        recreatePrmsBidSubmissionDataModel();
        prmsBidSubmission=new PrmsBidSubmission();

    }

    public String getTelOffice() {
        return prmsSupplyProfile.getTelOffice();
    }

    public String generateBidSubmissionNo() {
        if (saveorUpdateBundle.equals("Update")) {
            newcheckListNo = prmsBidSubmission.getBidSubmNo();

        } else {
            PrmsBidSubmission LastCheckNo = bIdSubmissionBeanLocal.LastCheckListNo();
            if (LastCheckNo != null) {
                LastcheckListNo = LastCheckNo.getBidSubId().toString();
            }
            DateFormat f = new SimpleDateFormat("yyyy");
            Date now = new Date();
            int newBidNoLast = 0;
            if (LastcheckListNo.equals("0")) {
                newBidNoLast = 1;
                newcheckListNo = "BidSub-No-" + newBidNoLast + "/" + f.format(now);
            } else {
                String[] lastInspNos = LastcheckListNo.split("-");
                String lastDatesPatern = lastInspNos[0];
                String[] lastDatesPaterns = lastDatesPatern.split("/");
                newBidNoLast = Integer.parseInt(lastDatesPaterns[0]);
                newBidNoLast = newBidNoLast + 1;
                newcheckListNo = "BidSub-No-" + newBidNoLast + "/" + f.format(now);
            }
        }
        return newcheckListNo;

    }

    public List<PrmsBid> getBidNo() {
        setPrmsBidList(bIdSubmissionBeanLocal.getBidNoList());
        return prmsBidList;
    }

    public void editBidSubmissionDataTbl() {
        addOrModifyBundle = "Modify";
        selectStatue = 1;
        prmsBidSubmissionDetail = prmsBidSubmissionDetailsDataModel.getRowData();
        prmsSupplyProfile = prmsBidSubmissionDetail.getSupplierId();
        prmsSupplyProfiles1 = new ArrayList<>();
        prmsSupplyProfiles1.add(prmsSupplyProfile);

    }

    private void ClearPopUp() {
        addOrModifyBundle = "Add";
        prmsBidSubmissionDetail = null;
        prmsSupplyProfile = null;
        hrEmployees = null;

    }

    private String clearprmsBidSubmission() {
        prmsBidSubmission = null;
        prmsBidSubmissionDetailsDataModel = null;
        eepBidReg = null;
        saveorUpdateBundle = "Save";
        updateStatus = 0;
        return null;

    }

    public String addSubmissionDetail() {
        if (selectStatue == 0) {
            if (actionPlnDetlCheck.contains(prmsBidSubmissionDetail.getSupplierId().getVendorName())) {
                JsfUtil.addFatalMessage("Duplicate Entry Is Not Allowed !!");
                return null;
            } else {

                prmsBidSubmission.addSubmissionDetail(prmsBidSubmissionDetail);
                actionPlnDetlCheck.add(prmsBidSubmissionDetail.getSupplierId().getVendorName());

            }
        } else if (selectStatue == 1) {
            prmsBidSubmission.addSubmissionDetail(prmsBidSubmissionDetail);
        }
        recreateprmsBidSubmissionDetailsDataModel();
        ClearPopUp();

        return null;
    }

    public SelectItem[] getSuppName() {
        return JsfUtil.getSelectItems(vendorRegBeanLocal.getVondorName(), true);
    }

    public SelectItem[] getEmployees() {
        return JsfUtil.getSelectItems(hREmployeesBeanLocal.GetEmployees(), true);

    }

    public SelectItem[] bidNumberList() {
        return JsfUtil.getSelectItems(bIdSubmissionBeanLocal.bidNumberList(), true);

    }

    public void recreatePrmsBidSubmissionDataModel() {
        PrmsBidSubmissionDataModel = null;
        PrmsBidSubmissionDataModel = new ListDataModel(new ArrayList<>(getPrmsBidSubmissions()));
    }

    public void recreateprmsBidSubmissionDetailsDataModel() {
        prmsBidSubmissionDetailsDataModel = null;
        prmsBidSubmissionDetailsDataModel = new ListDataModel(new ArrayList<>(prmsBidSubmission.getPrmsBidSubmissionDetailList()));

    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Save or Update method">
    public String saveForBidSubmission() {

        try {
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(SessionBean.getUserName(), "saveForBidSubmission", dataset)) {
                if (prmsBidSubmissionDetailsDataModel.getRowCount() > 0) {
                    if (updateStatus == 0) {
                        try {
                            generateBidSubmissionNo();
                            prmsBidSubmission.setBidSubmNo(newcheckListNo);
                            setPrmsBidSubmission(prmsBidSubmission);
                            prmsBidSubmissionDetail.setEmployeeId(hrEmployees);
                            prmsBidSubmissionDetail.setSupplierId(prmsSupplyProfile);
                            wfPrmsProcessed.setBidSubId(prmsBidSubmission);
                            prmsBidSubmission.setPreparedBy(wfPrmsProcessed.getProcessedBy());
                            prmsBidSubmission.setStatus(Constants.PREPARE_VALUE);
                            prmsBidSubmission.setPreparedBy(workFlow.getUserAccount());
                            bIdSubmissionBeanLocal.create(prmsBidSubmission);
                            JsfUtil.addSuccessMessage("Bid Submission is rigistered!!");
                            return clearprmsBidSubmission();
                        } catch (Exception e) {
                            e.printStackTrace();
                            JsfUtil.addErrorMessage("Something Error Occured on Data Saved" + e);
                            return null;
                        }
                    } else {
                        try {
                            prmsBidSubmission.setBidId(eepBidReg);
                            prmsBidSubmissionDetail.setEmployeeId(hrEmployees);
                            bIdSubmissionBeanLocal.update(prmsBidSubmission);
                            JsfUtil.addSuccessMessage("Bid Submission is Edited!! !!");
                            clearprmsBidSubmission();
                            saveorUpdateBundle = "Save";
                            return clearprmsBidSubmission();
                        } catch (Exception e) {
                            JsfUtil.addErrorMessage("Something Error Occured on Data Modified");
                        }

                    }
                } else {
                    JsfUtil.addFatalMessage("Bid  Submission detail can not be empty !!");
                }
            } else {
                JsfUtil.addFatalMessage("You don't have appropriate permission, please contact the system administrator. ");
                EventEntry eventEntry = new EventEntry();
                eventEntry.setSessionId(SessionBean.getSessionID());
                eventEntry.setUserId(SessionBean.getUserId());
                QName qualifiedName = new QName("", "project");
                JAXBElement<String> test = new JAXBElement<String>(qualifiedName, String.class, null, SessionBean.getUserName());
                eventEntry.setUserLogin(test);

                security.addEventLog(eventEntry, dataset);
            }
        } catch (Exception ex) {
            ex.printStackTrace();

        }

        return null;
    }
// </editor-fold>

}
