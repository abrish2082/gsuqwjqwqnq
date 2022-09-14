package et.gov.eep.prms.controller;

//<editor-fold defaultstate="collapsed" desc="Imports">
import et.gov.eep.commonApplications.entity.WfPrmsProcessed;
import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.commonApplications.utility.StringDateManipulation;
import et.gov.eep.prms.businessLogic.PortEntryBeanLocal;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import et.gov.eep.prms.entity.PrmsPortEntry;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import org.primefaces.event.SelectEvent;
import securityBean.Constants;
import securityBean.SessionBean;
import securityBean.WorkFlow;
import webService.AAA;
import webService.EventEntry;
import webService.IAdministration;
//</editor-fold>

//Port Entry Registeration page view scoped CDI Named Bean class
@Named(value = "portEntryController")
@ViewScoped
public class PortEntryController implements Serializable {

//<editor-fold defaultstate="collapsed" desc="Inject EJBs">
    @EJB
    private PortEntryBeanLocal portEntryBeanLocal;
    //</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Inject Entities">
    @Inject
    private PrmsPortEntry papmsPortEntry;
    @Inject
    WfPrmsProcessed wfPrmsProcessed;
    @Inject
    SessionBean sessionBean;
    @Inject
    WorkFlow workFlow;
    //</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Declare instance Variables">
    int saveStatus = 0;
    int updateStatus = 0;
    int dateWithTodayComparisonStatus;

    private String saveorUpdateBundle = "Save";
    private String logerName;
    private String activeIndex;
    private String userName;
    private String duplicattion = null;
    private String icone = "ui-icon-plus";
    private String addressInfoButton = "Add";
    private String createOrSearchBundle = "New";
    String newPayNo;
    String lastPaymentNo = "0";
    String actorNames;

    private boolean disableBtnCreate = false;
    private boolean disableServiceAndWorkRdbtnWhenSearch = false;
    private boolean renderPnlCreateServiceRegistration = false;
    private boolean renderPnlManPage = true;
    private boolean renderpnlToSearchPage;
    private boolean isRenderCreate = false;

    private PrmsPortEntry selectPort;
    //</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Declare Lists and models">
    List<PrmsPortEntry> portSearchParameterLst;
    private DataModel<PrmsPortEntry> PrmsPortEntryModel;
    Set<String> addressCheck = new HashSet<>();
    List<PrmsPortEntry> prmsPortEntrys;
    List<PrmsPortEntry> requisitionList;
    ArrayList<PrmsPortEntry> listOfApprovedAwardList;
    private List<PrmsPortEntry> prmsBidderRegistrationsForstatus;
    //</editor-fold>

    //default constructor
    public PortEntryController() {
    }

//initial Method using PostConstruct callback lifecycle  
    @PostConstruct
    public void init() {
        prmsBidderRegistrationsForstatus = portEntryBeanLocal.searchBidderRegistration();
        setLogerName(sessionBean.getUserName());
//        wfPrmsProcessed.setProcessedBy(SessionBean.getUserId());
        setUserName(workFlow.getUserName());

    }

    //<editor-fold defaultstate="collapsed" desc="getters and Setters of objects">
    public PrmsPortEntry getPapmsPortEntry() {
        if (papmsPortEntry == null) {
            papmsPortEntry = new PrmsPortEntry();
        }
        return papmsPortEntry;
    }

    public void setPrmsPortEntry(PrmsPortEntry papmsPortEntry) {
        this.papmsPortEntry = papmsPortEntry;
    }

    public WfPrmsProcessed getWfPrmsProcessed() {
        if (wfPrmsProcessed == null) {
            wfPrmsProcessed = new WfPrmsProcessed();
        }
        return wfPrmsProcessed;
    }

    public void setWfPrmsProcessed(WfPrmsProcessed wfPrmsProcessed) {
        this.wfPrmsProcessed = wfPrmsProcessed;
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

    public PrmsPortEntry getSelectPort() {
        return selectPort;
    }

    public void setSelectPort(PrmsPortEntry selectPort) {
        this.selectPort = selectPort;
    }
//</editor-fold>

    // <editor-fold defaultstate="collapsed" desc="getter and setter of Lists and models">
    public List<PrmsPortEntry> getPrmsPortEntrys() {
        if (prmsPortEntrys == null) {
            prmsPortEntrys = new ArrayList<>();
        }
        return prmsPortEntrys;
    }

    public void setPrmsPortEntrys(List<PrmsPortEntry> prmsPortEntrys) {
        this.prmsPortEntrys = prmsPortEntrys;
    }

    public DataModel<PrmsPortEntry> getPrmsPortEntryModel() {
        if (PrmsPortEntryModel == null) {
            PrmsPortEntryModel = new ListDataModel(new ArrayList<>(getPrmsPortEntrys()));
        }
        return PrmsPortEntryModel;
    }

    public void setPrmsPortEntryModel(DataModel<PrmsPortEntry> PrmsPortEntryModel) {
        this.PrmsPortEntryModel = PrmsPortEntryModel;
    }

    public Set<String> getAddressCheck() {
        return addressCheck;
    }

    public void setAddressCheck(Set<String> addressCheck) {
        this.addressCheck = addressCheck;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="getter and setter of instance variables">
    public int getSaveStatus() {
        return saveStatus;
    }

    public void setSaveStatus(int saveStatus) {
        this.saveStatus = saveStatus;
    }

    public int getUpdateStatus() {
        return updateStatus;
    }

    public void setUpdateStatus(int updateStatus) {
        this.updateStatus = updateStatus;
    }

    public String getNewPayNo() {
        return newPayNo;
    }

    public void setNewPayNo(String newPayNo) {
        this.newPayNo = newPayNo;
    }

    public String getActorNames() {
        return actorNames;
    }

    public void setActorNames(String actorNames) {
        this.actorNames = actorNames;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getLastPaymentNo() {
        return lastPaymentNo;
    }

    public void setLastPaymentNo(String lastPaymentNo) {
        this.lastPaymentNo = lastPaymentNo;
    }

    public String getSaveorUpdateBundle() {
        return saveorUpdateBundle;
    }

    public void setSaveorUpdateBundle(String saveorUpdateBundle) {
        this.saveorUpdateBundle = saveorUpdateBundle;
    }

    public String getLogerName() {
        return logerName;
    }

    public void setLogerName(String logerName) {
        this.logerName = logerName;
    }

    public String getIcone() {
        return icone;
    }

    public void setIcone(String icone) {
        this.icone = icone;
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

    public String getDuplicattion() {
        return duplicattion;
    }

    public void setDuplicattion(String duplicattion) {
        this.duplicattion = duplicattion;
    }

    public String getActiveIndex() {
        return activeIndex;
    }

    public void setActiveIndex(String activeIndex) {
        this.activeIndex = activeIndex;
    }

    public boolean isRenderPnlManPage() {
        return renderPnlManPage;
    }

    public void setRenderPnlManPage(boolean renderPnlManPage) {
        this.renderPnlManPage = renderPnlManPage;
    }

    public boolean isRenderpnlToSearchPage() {
        return renderpnlToSearchPage;
    }

    public void setRenderpnlToSearchPage(boolean renderpnlToSearchPage) {
        this.renderpnlToSearchPage = renderpnlToSearchPage;
    }

    public boolean isDisableBtnCreate() {
        return disableBtnCreate;
    }

    public void setDisableBtnCreate(boolean disableBtnCreate) {
        this.disableBtnCreate = disableBtnCreate;
    }

    public boolean isIsRenderCreate() {
        return isRenderCreate;
    }

    public void setIsRenderCreate(boolean isRenderCreate) {
        this.isRenderCreate = isRenderCreate;
    }

    public boolean isDisableServiceAndWorkRdbtnWhenSearch() {
        return disableServiceAndWorkRdbtnWhenSearch;
    }

    public void setDisableServiceAndWorkRdbtnWhenSearch(boolean disableServiceAndWorkRdbtnWhenSearch) {
        this.disableServiceAndWorkRdbtnWhenSearch = disableServiceAndWorkRdbtnWhenSearch;
    }

    public boolean isRenderPnlCreateServiceRegistration() {
        return renderPnlCreateServiceRegistration;
    }

    public void setRenderPnlCreateServiceRegistration(boolean renderPnlCreateServiceRegistration) {
        this.renderPnlCreateServiceRegistration = renderPnlCreateServiceRegistration;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Event changes">
    public void selectedParamChangeEvent(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            papmsPortEntry.setColumnName(event.getNewValue().toString());
            papmsPortEntry.setColumnValue(null);
        }
    }

    public List<PrmsPortEntry> getPortSearchParameterLst() {
        if (portSearchParameterLst == null) {
            portSearchParameterLst = new ArrayList<>();
            portSearchParameterLst = portEntryBeanLocal.getParamNameList();
        }
        return portSearchParameterLst;
    }

    public void setPortSearchParameterLst(List<PrmsPortEntry> portSearchParameterLst) {
        this.portSearchParameterLst = portSearchParameterLst;
    }

    public void searchPortName(SelectEvent event) {

        String PortName = event.getObject().toString();
        papmsPortEntry.setPortName(PortName);
        papmsPortEntry = portEntryBeanLocal.getPortName(papmsPortEntry);

        saveStatus = 1;
        saveorUpdateBundle = "Update";

    }

    public void rowSelect(SelectEvent event) {
        papmsPortEntry = (PrmsPortEntry) event.getObject();
        papmsPortEntry.setPortId(papmsPortEntry.getPortId());
        papmsPortEntry = portEntryBeanLocal.getSelectedRequest(papmsPortEntry.getPortId());
        renderPnlManPage = false;
        renderpnlToSearchPage = true;
        saveorUpdateBundle = "Update";
        renderPnlCreateServiceRegistration = true;

        disableServiceAndWorkRdbtnWhenSearch = true;

        updateStatus = 1;
        recreatePrmsPortEntryModel();

    }

    public void handleRegistrationDate(ValueChangeEvent event) throws ParseException {
        if (event.getNewValue() != null) {
            papmsPortEntry.setDateRigistered(event.getNewValue().toString());

            DateFormat from = new SimpleDateFormat("dd/MM/yyyy"); // current format
            DateFormat to = new SimpleDateFormat("yyyy-MM-dd"); // wanted format

            papmsPortEntry.setDateRigistered(to.format(from.parse(papmsPortEntry.getDateRigistered())));
            dateWithTodayComparisonStatus = StringDateManipulation.compareWithToday(papmsPortEntry.getDateRigistered());
            if (dateWithTodayComparisonStatus != 0) {
                JsfUtil.addFatalMessage("Registration Date Cannot be before or After Todays Date");
            }
        }
    }
    // </editor-fold>

    //<editor-fold defaultstate="collapsed" desc="other Methods">
    public void recreatePrmsPortEntryModel() {
        PrmsPortEntryModel = null;
        PrmsPortEntryModel = new ListDataModel(new ArrayList<>(getPrmsPortEntrys()));
    }

    public void searchPort() {
        papmsPortEntry.setPreparedBy(workFlow.getUserAccount());
        prmsPortEntrys = portEntryBeanLocal.searchPort(papmsPortEntry);
        recreatePrmsPortEntryModel();
        getPreparesName();
    }

    public void getPreparesName() {
        AAA securityService = new AAA();
        IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
        String systemBundle = "et/gov/eep/commonApplications/securityServer";
        String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
        for (int k = 0; k < prmsPortEntrys.size(); k++) {
            if (prmsPortEntrys.get(k).getPreparedBy() != null) {
                int actorsId = prmsPortEntrys.get(k).getPreparedBy();
                actorNames = security.getUserName(actorsId, dataset);
                prmsPortEntrys.get(k).setActorName(actorNames);
            }
        }
    }

    private String ClearPort() {
        papmsPortEntry = null;
        PrmsPortEntryModel = null;
        prmsPortEntrys = null;
        renderpnlToSearchPage = false;
        saveorUpdateBundle = "Save";
        createOrSearchBundle = "Search";
        setIcone("ui-icon-search");
        saveStatus = 0;
        return null;

    }

    public ArrayList<PrmsPortEntry> searchPortByPortName(String PortName) {
        ArrayList<PrmsPortEntry> portName = null;
        papmsPortEntry.setPortName(PortName);
        portName = portEntryBeanLocal.searchPortByPortName(papmsPortEntry);
        return portName;
    }

    public String generatePortNo() {
        if (saveorUpdateBundle.equals("Update")) {
            newPayNo = papmsPortEntry.getPortNo();

        } else {

            newPayNo = getNextPortNo();
        }

        return newPayNo;
    }

    public String getNextPortNo() {
        newPayNo = portEntryBeanLocal.getNextPortNo();
        return newPayNo;
    }

    public SelectItem[] getCostCenterListForRequest() {
        {
            return JsfUtil.getSelectItems(portEntryBeanLocal.getCapitalBudgetDisbursment(papmsPortEntry), true);
        }
    }

    public SelectItem[] getProformaNoLists() {
        listOfApprovedAwardList = new ArrayList<>();
        papmsPortEntry.setPortType("SeaPort");
        listOfApprovedAwardList = portEntryBeanLocal.getCapitalBudgetDisbursment(papmsPortEntry);
        return JsfUtil.getSelectItems(listOfApprovedAwardList, true);
    }

    public SelectItem[] getApprovedMRList() {

        requisitionList = portEntryBeanLocal.approveMaintReqList();
        return JsfUtil.getSelectItemsListBox(requisitionList, true);
    }

    public void createNewServiceRegInfo() {

        saveorUpdateBundle = "Save";
        renderpnlToSearchPage = false;
        if (createOrSearchBundle.equals("New")) {
            ClearPort();
            renderPnlCreateServiceRegistration = true;
            renderPnlManPage = false;
            disableServiceAndWorkRdbtnWhenSearch = false;
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
    //</editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Save or Update methods">
    public String saveForPortRigstration() {
        try {
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(sessionBean.getUserName(), "saveForPortRigstration", dataset)) {

                if (dateWithTodayComparisonStatus == 0) {
                    if (updateStatus == 0) {
                        try {
                            generatePortNo();
                            papmsPortEntry.setPortNo(newPayNo);
                            setPrmsPortEntry(papmsPortEntry);
                            wfPrmsProcessed.setPortId(papmsPortEntry);
//                        papmsPortEntry.setPreparedBy(wfPrmsProcessed.getProcessedBy());
                            papmsPortEntry.setStatus(Constants.PREPARE_VALUE);
                            papmsPortEntry.setPreparedBy(workFlow.getUserAccount());
                            portEntryBeanLocal.create(papmsPortEntry);

                            JsfUtil.addSuccessMessage("Port Information is Saved Successfully");
                            return ClearPort();

                        } catch (Exception e) {
                            e.printStackTrace();
                            JsfUtil.addFatalMessage("Something Error Occured on Data Saved" + e);
                            return null;
                        }
                    } else {
                        try {
                            portEntryBeanLocal.update(papmsPortEntry);
                            JsfUtil.addSuccessMessage("Port Information is Updated Successfully");
                            saveorUpdateBundle = "Save";
                            return ClearPort();
                        } catch (Exception e) {
                            JsfUtil.addFatalMessage("Something Error Occured on Data Modified");
                        }

                    }
                } else {
                    JsfUtil.addFatalMessage("Registration Date Cannot be before or After Todays Date");
                }
            } else {
                JsfUtil.addFatalMessage("You don't have appropriate permission, please contact the system administrator. ");
                EventEntry eventEntry = new EventEntry();
                eventEntry.setSessionId(sessionBean.getSessionID());
                eventEntry.setUserId(sessionBean.getUserId());
                QName qualifiedName = new QName("", "project");
                JAXBElement<String> test = new JAXBElement<String>(qualifiedName, String.class, null, sessionBean.getUserName());
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
