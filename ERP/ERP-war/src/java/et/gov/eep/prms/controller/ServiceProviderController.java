package et.gov.eep.prms.controller;

    // <editor-fold defaultstate="collapsed" desc="Imports">
import et.gov.eep.commonApplications.entity.ComLuCountry;
import et.gov.eep.commonApplications.entity.WfPrmsProcessed;
import java.io.Serializable;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.hrms.entity.address.HrAddresses;
import et.gov.eep.hrms.integration.HrAddressesBeanIntegrationLocal;
import et.gov.eep.prms.businessLogic.BidOpeningListLocal;
import et.gov.eep.prms.businessLogic.ServiceProviderBeanLocal;
import et.gov.eep.prms.entity.PrmsServiceProvider;
import et.gov.eep.prms.entity.PrmsServiceProviderDetail;
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
import javax.inject.Inject;
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.event.RowEditEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import securityBean.Constants;
import securityBean.SessionBean;
import securityBean.WorkFlow;
import webService.AAA;
import webService.EventEntry;
import webService.IAdministration;
// </editor-fold>

//Service Provider page view scoped CDI Named Bean class
@Named(value = "serviceProviderController")
@ViewScoped
public class ServiceProviderController implements Serializable {

    // <editor-fold defaultstate="collapsed" desc="Inject EJBs">
    @EJB
    private BidOpeningListLocal bidOpeningListLocal;
    @EJB
    HrAddressesBeanIntegrationLocal hrAddressesBeanIntegrationLocal;
    @EJB
    private ServiceProviderBeanLocal serviceProviderBeanLocal;
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Inject Entities">
    @Inject
    SessionBean sessionBean;
    @Inject
    HrAddresses hrAddresses;
    @Inject
    WfPrmsProcessed WfPrmsProcessed;
    @Inject
    private PrmsServiceProvider papmsServiceProvider;
    @Inject
    private ComLuCountry comLuCountry;
    @Inject
    private PrmsServiceProviderDetail papmsServiceProviderDetail;
    @Inject
    WorkFlow workFlow;
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Declare Variables">
    private String saveorUpdateBundle = "Save";
    private String addressInfoButton = "Add";
    private String createOrSearchBundle = "New";
    private String addOrModifyBundle = "Add";
    private String duplicattion = null;
    private String activeIndex;
    private String selectOptPartyName;
    private String icone = "ui-icon-plus";
    private String allAddressUnitAsOne;
    private String selected;
    private String selectedid;
    String logerName;
    private String userName;
    private String countryCode;
    String newPayNo;
    String lastPaymentNo = "0";
    String NewServiceNo;
    String LastServiceNo = "0";
    String phone;

    private boolean disableServiceAndWorkRdbtnWhenSearch = false;
    private boolean renderPnlCreateServiceRegistration = false;
    private boolean renderCountryEthiopia = true;
    private boolean renderOutherCOunty = false;
    private boolean renderpnlToSearchPage;
    private boolean disableBtnCreate = false;
    private boolean renderPnlCreateParty = false;
    private boolean renderPnlManPage = true;

    int updateStatus = 0;
    int saveStatus = 0;
    private int addressId;

    private TreeNode root;
    private TreeNode selectedNode;

    private PrmsServiceProvider selectServiceProvider;
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Declare Lists and models">
    List<PrmsServiceProvider> serviceProviderSearchParameterLst;
    private List<PrmsServiceProvider> prmsServiceProviders;
    List<PrmsServiceProvider> prmsServiceProvidersforsearch;
    private static List<HrAddresses> allAddressData = new ArrayList<>();
    private static List<HrAddresses> addresses;
    List<PrmsServiceProviderDetail> prmsServiceProviderDetails;
    ArrayList<ComLuCountry> comLuCountrysList;
    DataModel<PrmsServiceProviderDetail> PrmsServiceProviderDetailModel;
    DataModel<PrmsServiceProvider> PrmsServiceProviderModel;
    Set<String> addressCheck = new HashSet<>();
    // </editor-fold>

    //default constructor method
    public ServiceProviderController() {
    }

    //life cycle callback method
    @PostConstruct
    public void init() {
        prmsServiceProviders = serviceProviderBeanLocal.getserviceproviders();
        setLogerName(sessionBean.getUserName());
        WfPrmsProcessed.setProcessedBy(sessionBean.getUserId());
        setUserName(workFlow.getUserName());
        loadTree();
    }

    // <editor-fold defaultstate="collapsed" desc="getter and setter of objects">
    public PrmsServiceProvider getPapmsServiceProvider() {
        if (papmsServiceProvider == null) {
            papmsServiceProvider = new PrmsServiceProvider();
        }
        return papmsServiceProvider;
    }

    public void setPapmsServiceProvider(PrmsServiceProvider papmsServiceProvider) {
        this.papmsServiceProvider = papmsServiceProvider;
    }

    public ComLuCountry getComLuCountry() {
        if (comLuCountry == null) {
            comLuCountry = new ComLuCountry();
        }
        return comLuCountry;
    }

    public void setComLuCountry(ComLuCountry comLuCountry) {
        this.comLuCountry = comLuCountry;
    }

    public PrmsServiceProviderDetail getPapmsServiceProviderDetail() {
        if (papmsServiceProviderDetail == null) {
            papmsServiceProviderDetail = new PrmsServiceProviderDetail();
        }
        return papmsServiceProviderDetail;
    }

    public void setPapmsServiceProviderDetail(PrmsServiceProviderDetail papmsServiceProviderDetail) {
        this.papmsServiceProviderDetail = papmsServiceProviderDetail;
    }

    public HrAddresses getHrAddresses() {
        if (hrAddresses == null) {
            hrAddresses = new HrAddresses();
        }
        return hrAddresses;
    }

    public void setHrAddresses(HrAddresses hrAddresses) {
        this.hrAddresses = hrAddresses;
    }

    public WfPrmsProcessed getWfPrmsProcessed() {
        if (WfPrmsProcessed == null) {
            WfPrmsProcessed = new WfPrmsProcessed();
        }
        return WfPrmsProcessed;
    }

    public void setWfPrmsProcessed(WfPrmsProcessed WfPrmsProcessed) {
        this.WfPrmsProcessed = WfPrmsProcessed;
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

    // <editor-fold defaultstate="collapsed" desc="getter and setter of Lists and methods">
    public static List<HrAddresses> getAllAddressData() {
        if (allAddressData == null) {
            allAddressData = new ArrayList<>();
        }
        return allAddressData;
    }

    public static void setAllAddressData(List<HrAddresses> allAddressData) {
        ServiceProviderController.allAddressData = allAddressData;
    }

    public static List<HrAddresses> getAddresses() {
        return addresses;
    }

    public static void setAddresses(List<HrAddresses> addresses) {
        ServiceProviderController.addresses = addresses;
    }

    public List<PrmsServiceProvider> getPrmsServiceProviders() {
        if (prmsServiceProviders == null) {
            prmsServiceProviders = new ArrayList<>();
        }
        return prmsServiceProviders;
    }

    public void setPrmsServiceProviders(List<PrmsServiceProvider> prmsServiceProviders) {
        this.prmsServiceProviders = prmsServiceProviders;
    }

    public List<PrmsServiceProvider> getPrmsServiceProvidersforsearch() {
        if (prmsServiceProvidersforsearch == null) {
            prmsServiceProvidersforsearch = new ArrayList<>();
        }
        return prmsServiceProvidersforsearch;
    }

    public void setPrmsServiceProvidersforsearch(List<PrmsServiceProvider> prmsServiceProvidersforsearch) {
        this.prmsServiceProvidersforsearch = prmsServiceProvidersforsearch;
    }

    public DataModel<PrmsServiceProviderDetail> getPrmsServiceProviderDetailModel() {
        if (PrmsServiceProviderDetailModel == null) {
            PrmsServiceProviderDetailModel = new ArrayDataModel<>();
        }
        return PrmsServiceProviderDetailModel;
    }

    public void setPrmsServiceProviderDetailModel(DataModel<PrmsServiceProviderDetail> PrmsServiceProviderDetailModel) {
        this.PrmsServiceProviderDetailModel = PrmsServiceProviderDetailModel;
    }

    public DataModel<PrmsServiceProvider> getPrmsServiceProviderModel() {
        if (PrmsServiceProviderModel == null) {
            PrmsServiceProviderModel = new ArrayDataModel<>();
        }
        return PrmsServiceProviderModel;
    }

    public void setPrmsServiceProviderModel(DataModel<PrmsServiceProvider> PrmsServiceProviderModel) {
        this.PrmsServiceProviderModel = PrmsServiceProviderModel;
    }

    public Set<String> getAddressCheck() {
        return addressCheck;
    }

    public void setAddressCheck(Set<String> addressCheck) {
        this.addressCheck = addressCheck;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="getter and setter of variables">
    public String getSelected() {
        return selected;
    }

    public void setSelected(String selected) {
        this.selected = selected;
    }

    public String getLogerName() {
        return logerName;
    }

    public void setLogerName(String logerName) {
        this.logerName = logerName;
    }

    public String getAllAddressUnitAsOne() {
        return allAddressUnitAsOne;
    }

    public void setAllAddressUnitAsOne(String allAddressUnitAsOne) {
        this.allAddressUnitAsOne = allAddressUnitAsOne;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getSelectedid() {
        return selectedid;
    }

    public void setSelectedid(String selectedid) {
        this.selectedid = selectedid;
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

    public String getDuplicattion() {
        return duplicattion;
    }

    public void setDuplicattion(String duplicattion) {
        this.duplicattion = duplicattion;
    }

    public String getSelectOptPartyName() {
        return selectOptPartyName;
    }

    public void setSelectOptPartyName(String selectOptPartyName) {
        this.selectOptPartyName = selectOptPartyName;
    }

    public String getIcone() {
        return icone;
    }

    public void setIcone(String icone) {
        this.icone = icone;
    }

    public String getActiveIndex() {
        return activeIndex;
    }

    public void setActiveIndex(String activeIndex) {
        this.activeIndex = activeIndex;
    }

    public String getNewServiceNo() {
        return NewServiceNo;
    }

    public void setNewServiceNo(String NewServiceNo) {
        this.NewServiceNo = NewServiceNo;
    }

    public String getLastServiceNo() {
        return LastServiceNo;
    }

    public void setLastServiceNo(String LastServiceNo) {
        this.LastServiceNo = LastServiceNo;
    }

    public String getNewPayNo() {
        return newPayNo;
    }

    public void setNewPayNo(String newPayNo) {
        this.newPayNo = newPayNo;
    }

    public String getLastPaymentNo() {
        return lastPaymentNo;
    }

    public void setLastPaymentNo(String lastPaymentNo) {
        this.lastPaymentNo = lastPaymentNo;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSaveorUpdateBundle() {
        return saveorUpdateBundle;
    }

    void setSaveorUpdateBundle(String saveorUpdateBundle) {
        this.saveorUpdateBundle = saveorUpdateBundle;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public int getSaveStatus() {
        return saveStatus;
    }

    public void setSaveStatus(int saveStatus) {
        this.saveStatus = saveStatus;
    }

    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
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

    public boolean isDisableBtnCreate() {
        return disableBtnCreate;
    }

    public void setDisableBtnCreate(boolean disableBtnCreate) {
        this.disableBtnCreate = disableBtnCreate;
    }

    public boolean isRenderCountryEthiopia() {
        return renderCountryEthiopia;
    }

    public void setRenderCountryEthiopia(boolean renderCountryEthiopia) {
        this.renderCountryEthiopia = renderCountryEthiopia;
    }

    public boolean isRenderOutherCOunty() {
        return renderOutherCOunty;
    }

    public void setRenderOutherCOunty(boolean renderOutherCOunty) {
        this.renderOutherCOunty = renderOutherCOunty;
    }

    public boolean isRenderpnlToSearchPage() {
        return renderpnlToSearchPage;
    }

    public void setRenderpnlToSearchPage(boolean renderpnlToSearchPage) {
        this.renderpnlToSearchPage = renderpnlToSearchPage;
    }

    public boolean isRenderPnlCreateParty() {
        return renderPnlCreateParty;
    }

    public void setRenderPnlCreateParty(boolean renderPnlCreateParty) {
        this.renderPnlCreateParty = renderPnlCreateParty;
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

    public TreeNode getSelectedNode() {
        return selectedNode;
    }

    public void setSelectedNode(TreeNode selectedNode) {
        this.selectedNode = selectedNode;
    }

    public TreeNode getRoot() {
        return root;
    }

    public void setRoot(TreeNode root) {
        this.root = root;
    }

    public PrmsServiceProvider getSelectServiceProvider() {
        return selectServiceProvider;
    }

    public void setSelectServiceProvider(PrmsServiceProvider selectServiceProvider) {
        this.selectServiceProvider = selectServiceProvider;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="other methods">
    public void loadTree() {
        allAddressData = hrAddressesBeanIntegrationLocal.findAllAddressUnit();
        root = new DefaultTreeNode("Root", null);
        populateNodes(allAddressData, 0, root);
    }

    public void populateNodes(List<HrAddresses> addressData, int id, TreeNode node) {
        addresses = new ArrayList<>();
        if (!allAddressData.isEmpty()) {
            for (HrAddresses k : getAllAddressData()) {
                if (k.getParentId() == id) {
                    TreeNode childNode = new DefaultTreeNode(k.getAddressDescription() + "=>" + k.getAddressId(), node);
                    addresses.add(k);
                    populateNodes(addresses, k.getAddressId(), childNode);
                }
            }
        }
    }

    public void createNewParty() {

        saveorUpdateBundle = "Save";
        disableBtnCreate = false;
        if (createOrSearchBundle.equals("New")) {
            renderPnlCreateParty = true;
            renderPnlManPage = false;
            createOrSearchBundle = "Search";
            setIcone("ui-icon-search");
        } else if (createOrSearchBundle.equals("Search")) {
            renderPnlCreateParty = false;
            renderPnlManPage = true;
            createOrSearchBundle = "New";
            setIcone("-plus");
        }
    }

    public String generateServiceNo() {
        PrmsServiceProvider serviceNo = serviceProviderBeanLocal.getLastServiceNo();
        if (serviceNo != null) {
            LastServiceNo = serviceNo.getServiceProId().toString();
        }
        int newPayment = 0;
        if (LastServiceNo.equals("0")) {
            newPayment = 1;
            NewServiceNo = "SERv-" + newPayment;
        } else {
            String[] lastInspNos = LastServiceNo.split("-");
            String lastDatesPatern = lastInspNos[0];
            String[] lastDatesPaterns = lastDatesPatern.split("/");
            newPayment = Integer.parseInt(lastDatesPaterns[0]);
            newPayment = newPayment + 1;
            NewServiceNo = "SERV-" + newPayment;

        }
        papmsServiceProvider.setServiceProNo(NewServiceNo);
        return NewServiceNo;
    }

    public String generateInspectionNo() {
        PrmsServiceProvider lastPaymentNoObj = serviceProviderBeanLocal.getLastServiceNo();
        if (lastPaymentNoObj != null) {
            lastPaymentNo = lastPaymentNoObj.getServiceProId().toString();
        }

        int newPayment = 0;
        if (lastPaymentNo.equals("0")) {
            newPayment = 1;
            newPayNo = "SERV-" + newPayment;
        } else {
            String[] lastInspNos = lastPaymentNo.split("-");
            String lastDatesPatern = lastInspNos[0];
            String[] lastDatesPaterns = lastDatesPatern.split("/");
            newPayment = Integer.parseInt(lastDatesPaterns[0]);
            newPayment = newPayment + 1;
            newPayNo = "SERV-" + newPayment;
        }
        papmsServiceProvider.setServiceProNo(newPayNo);
        return newPayNo;
    }

    public void newOrSearchServiceRegInfo() {
        saveorUpdateBundle = "Save";
        renderpnlToSearchPage = false;
        if (createOrSearchBundle.equals("New")) {
            renderPnlCreateServiceRegistration = true;
            renderPnlManPage = false;
            disableServiceAndWorkRdbtnWhenSearch = false;
            createOrSearchBundle = "Search";
            setIcone("ui-icon-search");

        } else if (createOrSearchBundle.equals("Search")) {
            renderPnlCreateServiceRegistration = false;
            renderPnlManPage = true;
            disableServiceAndWorkRdbtnWhenSearch = true;
            createOrSearchBundle = "New";
            setIcone("ui-icon-plus");
        }
    }

    public void whenSeachClicked() {
        clearServiceProvidePage();
        renderPnlCreateServiceRegistration = false;
        renderPnlManPage = true;
    }

    public void goBackSearchPageBtnAction() {
        renderPnlCreateServiceRegistration = false;
        renderpnlToSearchPage = false;
        renderPnlManPage = true;
    }

    public void searchServiceProvider() {
        papmsServiceProvider.setPreparedby(workFlow.getUserAccount());
        prmsServiceProvidersforsearch = serviceProviderBeanLocal.searchServiceProvider(papmsServiceProvider);
        recreatePrmsServiceProviderModel();
        papmsServiceProvider = new PrmsServiceProvider();
    }

    public void recreateServiceProviderDtlModel() {
        PrmsServiceProviderDetailModel = null;
        PrmsServiceProviderDetailModel = new ListDataModel((papmsServiceProvider.getPrmsServiceProviderDetailList()));
    }

    public void recreatePrmsServiceProviderModel() {
        PrmsServiceProviderModel = null;
        PrmsServiceProviderModel = new ListDataModel(new ArrayList<>(getPrmsServiceProviders()));
    }

    public String addServiceProvideDetialInfo() {
        papmsServiceProviderDetail.setAddressId(hrAddresses);
        papmsServiceProvider.addServiceProvideDetialInfo(papmsServiceProviderDetail);

        recreateServiceProviderDtlModel();
        clearPopUp();
        return null;
    }

    private void clearPopUp() {
        papmsServiceProviderDetail = null;
        addresses = null;
        hrAddresses = new HrAddresses();
        allAddressUnitAsOne = null;
        addOrModifyBundle = "Add";
    }

    private String clearServiceProvidePage() {
        papmsServiceProvider = null;
        PrmsServiceProviderDetailModel = null;
        papmsServiceProviderDetail = null;
        hrAddresses = null;
        allAddressData = null;
        addresses = null;
        saveorUpdateBundle = "Save";
        createOrSearchBundle = "Search";
        setIcone("ui-icon-search");
        saveStatus = 0;
        comLuCountry = null;
        return null;
    }

    public ArrayList<ComLuCountry> getComLuCountrysList() {
        if (comLuCountrysList == null) {
            comLuCountrysList = new ArrayList<>();
            setComLuCountrysList(bidOpeningListLocal.getCountryName());
        }
        return comLuCountrysList;
    }

    public void setComLuCountrysList(ArrayList<ComLuCountry> comLuCountrysList) {

        this.comLuCountrysList = comLuCountrysList;
    }

    public List<PrmsServiceProvider> searchByServiceName(String Name) {
        List<PrmsServiceProvider> serviceProvide = null;
        papmsServiceProvider.setName(Name);
        serviceProvide = serviceProviderBeanLocal.searchServiceProviderByName(papmsServiceProvider);
        return serviceProvide;
    }

    public void updateServiceDetail() {
        papmsServiceProviderDetail = getPrmsServiceProviderDetailModel().getRowData();
    }

    public void removeContactPersonInfo() {
        int rowIndex = PrmsServiceProviderDetailModel.getRowIndex();
        papmsServiceProviderDetail = papmsServiceProvider.getPrmsServiceProviderDetailList().get(rowIndex);
        papmsServiceProvider.getPrmsServiceProviderDetailList().remove(rowIndex);
        recreateServiceProviderDtlModel();
        if (saveorUpdateBundle.equals("Update")) {
            serviceProviderBeanLocal.deleteServiceProvideDetail(papmsServiceProviderDetail);
        }
    }

    public void editItemDataTbl() {
        papmsServiceProviderDetail = PrmsServiceProviderDetailModel.getRowData();
        addOrModifyBundle = "Modify";
        String com = comLuCountry.getCountry();

        if ("Ethiopia".equals(com)) {
            hrAddresses = papmsServiceProviderDetail.getAddressId();
            hrAddresses = new HrAddresses();
            comLuCountry = papmsServiceProvider.getCountryId();
            renderCountryEthiopia = true;
            renderOutherCOunty = false;
            hrAddresses.setAddressName(papmsServiceProviderDetail.getAddressId().getAddressName());

        } else {
            renderOutherCOunty = true;
            renderCountryEthiopia = false;

        }

    }

    public SelectItem[] getCountry() {
        return JsfUtil.getSelectItems(bidOpeningListLocal.getCountryName(), true);
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Event changes">
    public List<PrmsServiceProvider> getServiceProviderSearchParameterLst() {
        if (serviceProviderSearchParameterLst == null) {
            serviceProviderSearchParameterLst = new ArrayList<>();
            serviceProviderSearchParameterLst = serviceProviderBeanLocal.getParamNameList();
        }
        return serviceProviderSearchParameterLst;
    }

    public void setServiceProviderSearchParameterLst(List<PrmsServiceProvider> serviceProviderSearchParameterLst) {
        this.serviceProviderSearchParameterLst = serviceProviderSearchParameterLst;
    }

    public void selectedParamChangeEvent(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            papmsServiceProvider.setColumnName(event.getNewValue().toString());
            papmsServiceProvider.setColumnValue(null);
        }
    }

    public void onContactRowCancel(RowEditEvent event) {
    }

    public void searchServiceProvideName(SelectEvent event) {
        String name = event.getObject().toString();
        papmsServiceProvider.setName(name);
        papmsServiceProvider = serviceProviderBeanLocal.getServiceProvider(papmsServiceProvider);

        saveStatus = 1;
        saveorUpdateBundle = "Update";

        int sizes = papmsServiceProvider.getPrmsServiceProviderDetailList().size();
        for (int i = 0; i < sizes; i++) {
            recreateServiceProviderDtlModel();
        }

    }

    public void rowSelect(SelectEvent event) {

        papmsServiceProvider = (PrmsServiceProvider) event.getObject();

        papmsServiceProvider = serviceProviderBeanLocal.getSelectedRequest(papmsServiceProvider.getServiceProId());
        renderpnlToSearchPage = true;
        renderPnlCreateParty = true;
        renderPnlManPage = false;
        saveorUpdateBundle = "Update";
        renderPnlCreateServiceRegistration = true;

        disableServiceAndWorkRdbtnWhenSearch = true;
        saveStatus = 1;
        comLuCountry = papmsServiceProvider.getCountryId();
        String com = papmsServiceProvider.getCountryId().getCountry();
        if ("Ethiopia".equals(com)) {
            renderCountryEthiopia = true;
            renderOutherCOunty = false;
            hrAddresses = papmsServiceProviderDetail.getAddressId();

        } else {
            renderOutherCOunty = true;
            renderCountryEthiopia = false;

        }

        recreateServiceProviderDtlModel();

    }

    public void onContactRowEdit(RowEditEvent event) {

        int rowIndex = PrmsServiceProviderDetailModel.getRowIndex();
        PrmsServiceProviderDetail contactPerson = new PrmsServiceProviderDetail();

        PrmsServiceProviderDetail comContPerson = (PrmsServiceProviderDetail) event.getObject();

        boolean found = false;
        for (int i = 0; i < papmsServiceProvider.getPrmsServiceProviderDetailList().size(); i++) {
            if (i != rowIndex) {
                if (papmsServiceProvider.getPrmsServiceProviderDetailList().get(i).getBranchName().equals(comContPerson.getBranchName())
                        && papmsServiceProvider.getPrmsServiceProviderDetailList().get(i).getBranchType().equals(comContPerson.getBranchType())) {
                    found = true;
                    break;
                }
            }
        }
        if (found == true) {
            JsfUtil.addSuccessMessage("Duplicate Entry Is Not Allowed !!");

            comContPerson.setBranchName(null);
            papmsServiceProvider.getPrmsServiceProviderDetailList().set(rowIndex, comContPerson);
            recreateServiceProviderDtlModel();
        } else {
            papmsServiceProvider.getPrmsServiceProviderDetailList().set(rowIndex, comContPerson);
            recreateServiceProviderDtlModel();
        }
    }

    public void changeEventSuppCode(ValueChangeEvent event) {
        if (null != event.getNewValue()) {
            comLuCountry = (ComLuCountry) event.getNewValue();
            if (comLuCountry.getCountry().equalsIgnoreCase("Ethiopia")) {
                renderCountryEthiopia = true;
                renderOutherCOunty = false;

            } else {
                countryCode = comLuCountry.getCallingCode() + "-999-99-99-99";
                renderOutherCOunty = true;
                renderCountryEthiopia = false;

            }

        }
    }

    public void onRowEdit(RowEditEvent event) {
        renderPnlCreateParty = true;
        renderPnlManPage = false;
        activeIndex = "0";
        saveorUpdateBundle = "Update";
        createOrSearchBundle = "Search";
        setIcone("ui-icon-search");
        disableBtnCreate = true;
        updateStatus = 1;
        int rowIndex = PrmsServiceProviderModel.getRowIndex();
        papmsServiceProvider = prmsServiceProviders.get(rowIndex);
        recreatePrmsServiceProviderModel();
        recreateServiceProviderDtlModel();

    }

    public void onNodeSelect(NodeSelectEvent event) {
        selectedNode = event.getTreeNode();
        addressId = Integer.parseInt((selectedNode.getData().toString()).split("=>")[1]);
        hrAddresses.setAddressId(addressId);

        hrAddresses = hrAddressesBeanIntegrationLocal.findAllAddressUnitAsOne(hrAddresses);

        setAllAddressUnitAsOne(hrAddresses.getAddressName());
        papmsServiceProviderDetail.setAddressId(hrAddresses);
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Save or Update Method">
    public String saveForServiceProvider() {
        if (PrmsServiceProviderDetailModel.getRowCount() > 0) {
            try {
                AAA securityService = new AAA();
                IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
                String systemBundle = "et/gov/eep/commonApplications/securityServer";
                String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
                if (security.checkAccess(sessionBean.getUserName(), "saveForServiceProvider", dataset)) {
                    if (saveStatus == 0) {
                        try {

                            setPapmsServiceProvider(papmsServiceProvider);
                            papmsServiceProvider.setCountryId(comLuCountry);
                            papmsServiceProvider.setPreparedby(WfPrmsProcessed.getProcessedBy());
                            papmsServiceProvider.setCurrentStatus(Constants.ACTIVE);
                            papmsServiceProvider.setStatus(Constants.PREPARE_VALUE);
                            papmsServiceProvider.setPreparedby(workFlow.getUserAccount());;
                            serviceProviderBeanLocal.create(papmsServiceProvider);
                            papmsServiceProviderDetail.setTelephoneNo(comLuCountry.getCallingCode());
//                          wfPrmsProcessedBeanLocal.savewf(WfPrmsProcessed);

                            JsfUtil.addSuccessMessage("Service Provide Information is Saved Successfully");
                            return clearServiceProvidePage();

                        } catch (Exception e) {
                            e.printStackTrace();
                            JsfUtil.addErrorMessage("Something Error Occured on Data Saved" + e);
                            return null;
                        }
                    } else {
                        try {
                            papmsServiceProvider.setCountryId(comLuCountry);
                            serviceProviderBeanLocal.update(papmsServiceProvider);
                            JsfUtil.addSuccessMessage("Service Provide Information is Updated Successfully");
                            clearServiceProvidePage();
                            saveorUpdateBundle = "Save";
                            return clearServiceProvidePage();
                        } catch (Exception e) {
                            JsfUtil.addErrorMessage("Something Error Occured on Data Modified");
                        }

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
        } else {
            JsfUtil.addFatalMessage("Service provider  detail can not be empty !!");
        }
        return null;
    }
    // </editor-fold>

}
