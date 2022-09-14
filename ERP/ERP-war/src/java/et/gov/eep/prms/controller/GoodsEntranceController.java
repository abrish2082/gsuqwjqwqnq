
package et.gov.eep.prms.controller;

//<editor-fold defaultstate="collapsed" desc="Imortt">
import et.gov.eep.commonApplications.businessLogic.PrmsLuDmArchiveBeanLocal;
import et.gov.eep.commonApplications.businessLogic.WfPrmsProcessedBeanLocal;
import et.gov.eep.commonApplications.controller.DataUpload;
import et.gov.eep.commonApplications.entity.ColumnNameResolver;
import et.gov.eep.commonApplications.entity.ComLuCountry;
import et.gov.eep.commonApplications.entity.PrmsLuDmArchive;
import et.gov.eep.commonApplications.entity.WfPrmsProcessed;
import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.fcms.entity.FmsLuCurrency;
import et.gov.eep.prms.businessLogic.GoodsEntranceBeanLocal;
import et.gov.eep.prms.entity.PrmsContract;
import et.gov.eep.prms.entity.PrmsGoodsEntrance;
import et.gov.eep.hrms.entity.organization.HrDepartments;
import et.gov.eep.hrms.mapper.organization.HrDepartmentsFacade;
import et.gov.eep.prms.entity.PrmsLcRigistration;
import et.gov.eep.prms.entity.PrmsPortEntry;
import et.gov.eep.prms.entity.PrmsSupplyProfile;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import org.insa.model.DocumentModel;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import et.gov.eep.prms.entity.PrmsFileUpload;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.event.RowEditEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.TreeNode;
import securityBean.Constants;
import securityBean.SessionBean;
import securityBean.WorkFlow;
import webService.AAA;
import webService.EventEntry;
import webService.IAdministration;
import org.insa.client.DmsHandler;
import org.insa.model.DocList;
import org.apache.commons.io.FileUtils;
import insa.org.et.security.Utility;
import java.io.File;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import java.io.FileInputStream;
import org.insa.util.StringDateManipulation;
import org.primefaces.model.DefaultStreamedContent;
import et.gov.eep.commonApplications.entity.WfMmsProcessed;
import et.gov.eep.prms.entity.PrmsContractAmendment;
import et.gov.eep.prms.entity.PrmsLcRigistrationAmend;
import java.math.BigDecimal;
//</editor-fold>
// defualt argument
@Named(value = "goodsEntranceController")
@ViewScoped

   //<editor-fold defaultstate="collapsed" desc="Inject EJBs">
public class GoodsEntranceController implements Serializable {
    
    @EJB
            GoodsEntranceBeanLocal goodsEntranceBeanLocal;
    @EJB
            WfPrmsProcessedBeanLocal wfPrmsProcessedBeanLocal;
    @EJB
            PrmsLuDmArchiveBeanLocal prmsLuDmArchiveBeanLocal;
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Inject Entities">
    @Inject
            PrmsGoodsEntrance prmsGoodsEntrance;
    @Inject
            PrmsPortEntry prmsPortEntry;
    @Inject
            PrmsContract prmsContract;
    @Inject
            PrmsLcRigistration prmsLcRigistration;
    @Inject
            PrmsLcRigistrationAmend prmsLcRigistrationAmend;
    @Inject
            PrmsPortEntry portOfDischarge;
    @Inject
            HrDepartments departments;
    @Inject
            FmsLuCurrency fmsLuCurrency;
    @Inject
    private WfPrmsProcessed wfPrmsProcessed;
    @Inject
    private PrmsFileUpload prmsFileUpload;
    @Inject
    private WorkFlow workFlow;
    @Inject
            SessionBean sessionBean;
    @Inject
    private WfMmsProcessed wfMmsProcessed;
    @Inject
    private PrmsContractAmendment prmsContractAmendment;
    @Inject
            PrmsLuDmArchive prmsLuDmArchive;
    @Inject
            PrmsLuDmArchive prmsLuDmArchiveSelection;
    @EJB
            HrDepartmentsFacade hrDepartmentsFacade;
     @Inject
    ColumnNameResolver columnNameResolver;
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Declare Fields">
    private String saveorUpdateBundle = "Save";
    private String createOrSearchBundle = "New";
    private String icone = "ui-icon-plus";
    int saveStatus = 0;
    private boolean disableBtnCreate = false;
    private String duplicattion = null;
    private boolean renderPnlCreateParty = false;
    private boolean renderPnlManPage = true;
    private boolean renderpnlToSeachPage;
    private String activeIndex;
    private TreeNode root;
    private TreeNode root2;
    private TreeNode selectedNode;
    private boolean isFileSelected = false;
    TreeNode DepartmentList;
    private static List<HrDepartments> araListe;
    @Inject
            DataUpload dataUpload;
    private String selectedValue = "";
    private boolean renderDecision = false;
    private String loggerName;
    private int requestNotificationCounter = 0;
    private boolean isRendercreate;
    private boolean isRenderNotify;
    private boolean renderRecevable;
    private boolean renderPayable;
    private boolean isRowFileSelected = false;
    private String fileName;
    private String fileType;
    private byte[] byteFile;
    int updateStatus = 0;
    PrmsGoodsEntrance selectPrmsGoodsEntrance;
    private String _userName = "UserID";
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="declare lists and models">
    DataModel<PrmsGoodsEntrance> prmsGoodsEntrancesModel;
    DataModel<PrmsLuDmArchive> prmsLuDmArchivesDModel;
    private DataModel<WfPrmsProcessed> wfPrmsProcessedDModel;
    List<PrmsGoodsEntrance> goodsEntrances;
    List<HrDepartments> allDepartmentsList = new ArrayList<>();
    List<PrmsContract> listOfContract = new ArrayList<>();
    private List<PrmsPortEntry> listOfPorts;
    private List<PrmsLcRigistration> listOfLC;
    private List<PrmsLcRigistrationAmend> listOfLCAmended;
    ComLuCountry comLuCountry;
    List<ComLuCountry> countryList;
    List<FmsLuCurrency> currencyLists;
    List<PrmsLuDmArchive> prmsLuDmArchivesList;
    private List<PrmsContractAmendment> prmsContractAmendmentLst;
    Set<String> actionPlnDetlCheck = new HashSet<>();
    List<ColumnNameResolver> ColumnNameResolverList = new ArrayList<>();
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="getter and setter of variables">
    public boolean isRenderpnlToSeachPage() {
        return renderpnlToSeachPage;
    }
    
    public void setRenderpnlToSeachPage(boolean renderpnlToSeachPage) {
        this.renderpnlToSeachPage = renderpnlToSeachPage;
    }
      public Set<String> getActionPlnDetlCheck() {
        return actionPlnDetlCheck;
    }
    
    public void setActionPlnDetlCheck(Set<String> actionPlnDetlCheck) {
        this.actionPlnDetlCheck = actionPlnDetlCheck;
    }
    
    public boolean isDisableBtnCreate() {
        return disableBtnCreate;
    }
    
    public void setDisableBtnCreate(boolean disableBtnCreate) {
        this.disableBtnCreate = disableBtnCreate;
    }
    
    public String getDuplicattion() {
        return duplicattion;
    }
    
    public void setDuplicattion(String duplicattion) {
        this.duplicattion = duplicattion;
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
    
    public String getActiveIndex() {
        return activeIndex;
    }
    
    public void setActiveIndex(String activeIndex) {
        this.activeIndex = activeIndex;
    }
    
    public int getUpdateStatus() {
        return updateStatus;
    }
    
    public void setUpdateStatus(int updateStatus) {
        this.updateStatus = updateStatus;
    }
    
    public SessionBean getSessionBean() {
        if (sessionBean == null) {
            sessionBean = new SessionBean();
        }
        return sessionBean;
    }
    
    public void setSessionBean(SessionBean sessionBean) {
        this.sessionBean = sessionBean;
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
    
    public String getIcone() {
        return icone;
    }
    
    public void setIcone(String icone) {
        this.icone = icone;
    }
    
    public int getSaveStatus() {
        return saveStatus;
    }
    
    public void setSaveStatus(int saveStatus) {
        this.saveStatus = saveStatus;
    }
    
    public TreeNode getRoot() {
        return root;
    }
    
    public void setRoot(TreeNode root) {
        this.root = root;
    }
    
    public TreeNode getSelectedNode() {
        return selectedNode;
    }
    
    public void setSelectedNode(TreeNode selectedNode) {
        this.selectedNode = selectedNode;
    }
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="getter and setter of objects">
    public GoodsEntranceController() {
    }
    
    public PrmsGoodsEntrance getSelectPrmsGoodsEntrance() {
        return selectPrmsGoodsEntrance;
    }
    
    public void setSelectPrmsGoodsEntrance(PrmsGoodsEntrance selectPrmsGoodsEntrance) {
        this.selectPrmsGoodsEntrance = selectPrmsGoodsEntrance;
    }
    public FmsLuCurrency getFmsLuCurrency() {
        if (fmsLuCurrency == null) {
            fmsLuCurrency = new FmsLuCurrency();
        }
        return fmsLuCurrency;
    }

    public String getUserName() {
        return _userName;
    }

    public void setUserName(String _userName) {
        this._userName = _userName;
    }
    
    public void setFmsLuCurrency(FmsLuCurrency fmsLuCurrency) {
        this.fmsLuCurrency = fmsLuCurrency;
    }
    
    public void setSelectedCurrency(ValueChangeEvent e) {
        if (e.getNewValue() != null) {
            fmsLuCurrency = (FmsLuCurrency) e.getNewValue();
            prmsGoodsEntrance.setCurrencyId(fmsLuCurrency);
            System.out.println("===================curee=" + fmsLuCurrency.getCurrency());
        }
        
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
    
    public void onNodeSelect(NodeSelectEvent event) {
        
        selectedNode = event.getTreeNode();
        int key = Integer.parseInt((selectedNode.getData().toString()).split("--")[0]);
        
        departments = goodsEntranceBeanLocal.getSelectDepartement(key);
        
        prmsGoodsEntrance.setDepId(departments);
        RequestContext requestContext = RequestContext.getCurrentInstance();
        requestContext.execute("PF('dlg').hide();");
        
    }
     public PrmsGoodsEntrance getPrmsGoodsEntrance() {
        if (prmsGoodsEntrance == null) {
            prmsGoodsEntrance = new PrmsGoodsEntrance();
        }
        return prmsGoodsEntrance;
    }
    
    public void setPrmsGoodsEntrance(PrmsGoodsEntrance prmsGoodsEntrance) {
        this.prmsGoodsEntrance = prmsGoodsEntrance;
    }
    
    public PrmsPortEntry getPrmsPortEntry() {
        if (prmsPortEntry == null) {
            prmsPortEntry = new PrmsPortEntry();
        }
        return prmsPortEntry;
    }
    
    public void setPrmsPortEntry(PrmsPortEntry prmsPortEntry) {
        this.prmsPortEntry = prmsPortEntry;
    }
    
    public void setSelectPortDisch(ValueChangeEvent e) {
        if (e.getNewValue() != null) {
//            portOfDischarge = (PrmsPortEntry) e.getNewValue();
            System.out.println("e.getNewValue().toString()=="+e.getNewValue().toString());
            int portId = Integer.parseInt(e.getNewValue().toString());
            portOfDischarge.setPortId(BigDecimal.valueOf(portId));
            prmsGoodsEntrance.setPortOfDischarge(portOfDischarge);
        }
        
    }
    
    public HrDepartments getDepartments() {
        if (departments == null) {
            departments = new HrDepartments();
        }
        return departments;
    }
    
    public void setDepartments(HrDepartments departments) {
        this.departments = departments;
    }
    
    public PrmsLcRigistration getPrmsLcRigistration() {
        if (prmsLcRigistration == null) {
            prmsLcRigistration = new PrmsLcRigistration();
        }
        return prmsLcRigistration;
    }
    
    public void setPrmsLcRigistration(PrmsLcRigistration prmsLcRigistration) {
        this.prmsLcRigistration = prmsLcRigistration;
    }
    
    public PrmsLcRigistrationAmend getPrmsLcRigistrationAmend() {
        if (prmsLcRigistrationAmend == null) {
            prmsLcRigistrationAmend = new PrmsLcRigistrationAmend();
        }
        return prmsLcRigistrationAmend;
    }
    
    public void setPrmsLcRigistrationAmend(PrmsLcRigistrationAmend prmsLcRigistrationAmend) {
        this.prmsLcRigistrationAmend = prmsLcRigistrationAmend;
    }
    
    
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="getter and setter of lists and models">
    List<PrmsGoodsEntrance> prmsGoodsEntrances;
    
    public List<PrmsGoodsEntrance> getPrmsGoodsEntrances() {
        if (prmsGoodsEntrances == null) {
            prmsGoodsEntrances = new ArrayList<>();
        }
        return prmsGoodsEntrances;
    }
    
    public void setPrmsGoodsEntrances(List<PrmsGoodsEntrance> prmsGoodsEntrances) {
        this.prmsGoodsEntrances = prmsGoodsEntrances;
    }
    
    public List<PrmsGoodsEntrance> getGoodsEntrances() {
        if (prmsGoodsEntrances == null) {
            prmsGoodsEntrances = new ArrayList<>();
        }
        return goodsEntrances;
    }

    public List<ColumnNameResolver> getColumnNameResolverList() {
        return ColumnNameResolverList;
    }

    public void setColumnNameResolverList(List<ColumnNameResolver> ColumnNameResolverList) {
        this.ColumnNameResolverList = ColumnNameResolverList;
    }
    
    public void setGoodsEntrances(List<PrmsGoodsEntrance> goodsEntrances) {
        this.goodsEntrances = goodsEntrances;
    }
    public void searchGoodsNo() {
        prmsGoodsEntrances = goodsEntranceBeanLocal.getGoodsListsByParameter(prmsGoodsEntrance);
        recreatGoodsEnteranceModel();
    }

    public ColumnNameResolver getColumnNameResolver() {
          if(columnNameResolver==null){
            columnNameResolver= new ColumnNameResolver();
        }
        return columnNameResolver;
    }

    public void setColumnNameResolver(ColumnNameResolver columnNameResolver) {
        this.columnNameResolver = columnNameResolver;
    }
    
    public DataModel<PrmsGoodsEntrance> getPrmsGoodsEntrancesModel() {
        if (prmsGoodsEntrancesModel == null) {
            prmsGoodsEntrancesModel = new ListDataModel<>();
        }
        return prmsGoodsEntrancesModel;
    }
    
    public void setPrmsGoodsEntrancesModel(DataModel<PrmsGoodsEntrance> prmsGoodsEntrancesModel) {
        this.prmsGoodsEntrancesModel = prmsGoodsEntrancesModel;
    }
    public void recursive(List<HrDepartments> liste, int id, TreeNode node) {
        araListe = new ArrayList<>();
        if (allDepartmentsList != null) {
            for (HrDepartments k : allDepartmentsList) {
                if (k.getParentId() != null && k.getParentId() == id) {
                    TreeNode childNode = new DefaultTreeNode(k.getDepId() + "--" + k.getDepName(), node);
                    araListe.add(k);
                    recursive(araListe, k.getDepId(), childNode);
                }
            }
        }
    }
    
    public List<HrDepartments> getAllDepartmentsList() {
        return allDepartmentsList;
    }
    
    public void setAllDepartmentsList(List<HrDepartments> allDepartmentsList) {
        this.allDepartmentsList = allDepartmentsList;
    }
    
    public List<FmsLuCurrency> getCurrencyLists() {
        if (currencyLists == null) {
            currencyLists = new ArrayList<>();
        }
        return currencyLists;
    }
    
    public void setCurrencyLists(List<FmsLuCurrency> currencyLists) {
        this.currencyLists = currencyLists;
    }
    public List<ComLuCountry> getCountryList() {
        if (countryList == null) {
            countryList = new ArrayList<>();
            countryList = goodsEntranceBeanLocal.getCountryList();
        }
        return countryList;
    }
    
    public void setCountryList(List<ComLuCountry> countryList) {
        this.countryList = countryList;
    }
    public void setSelectSuppliers(ValueChangeEvent e) {
        prmsContract = (PrmsContract) e.getNewValue();
        prmsContractAmendmentLst = goodsEntranceBeanLocal.checkAmendOrNot(prmsContract);
        if (prmsContractAmendmentLst.size() > 0) {
            prmsContractAmendment = goodsEntranceBeanLocal.getContractAmendment(prmsContract);
            prmsContract.setContractId(prmsContractAmendment.getContractId().getContractId());
            prmsContract.setSuppId(prmsContractAmendment.getSuppId());
            prmsContract.setContractamount(prmsContractAmendment.getContractamount());
        }
        contractAmount();
    }
    
    public SelectItem[] getListOfAllContractNo() {
        listOfContract = goodsEntranceBeanLocal.listOfContractNO();
        return JsfUtil.getSelectItems(listOfContract, true);
    }
    
    public void getSupplierDet() {
        prmsLcRigistration.setSupplier(prmsContract.getSuppId().getVendorName());
    }
    
    public List<PrmsContract> getListOfContract() {
        if (listOfContract == null) {
            listOfContract = new ArrayList<>();
        }
        listOfContract = goodsEntranceBeanLocal.listOfContractNO();
        return listOfContract;
    }
    
    public void setListOfContract(List<PrmsContract> listOfContract) {
        this.listOfContract = listOfContract;
    }
    public TreeNode getDepartmentList() {
        return DepartmentList;
    }
    
    public void setDepartmentList(TreeNode DepartmentList) {
        this.DepartmentList = DepartmentList;
    }
    
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Event changes">
    public void rowSelect(SelectEvent event) {
        prmsGoodsEntrance = (PrmsGoodsEntrance) event.getObject();
        prmsGoodsEntrance.setId(prmsGoodsEntrance.getId());
        prmsGoodsEntrance = goodsEntranceBeanLocal.getSelectedRequest(prmsGoodsEntrance.getId());
        currencyLists = goodsEntranceBeanLocal.getCurrencyName();
        fmsLuCurrency = prmsGoodsEntrance.getCurrencyId();
        countryList = goodsEntranceBeanLocal.getCountryList();
        comLuCountry = prmsGoodsEntrance.getCountryId();
        prmsContract = prmsGoodsEntrance.getCotractId();
        listOfLC = goodsEntranceBeanLocal.listOfLcNO();
        prmsLcRigistration = prmsGoodsEntrance.getLcId();
        listOfPorts = goodsEntranceBeanLocal.listOfPort();
        portOfDischarge = prmsGoodsEntrance.getPortOfDischarge();
        if (workFlow.isPrepareStatus()) {
            wfPrmsProcessed.setProcessedOn(prmsGoodsEntrance.getProcessedOn());
        }
        
        departments = prmsGoodsEntrance.getDepId();
        renderPnlManPage = false;
        renderPnlCreateParty = true;
        renderpnlToSeachPage = true;
        saveorUpdateBundle = "Update";
        createOrSearchBundle = "New";
        setCreateOrSearchBundle(createOrSearchBundle);
        setIcone("ui-icon-plus");
        
    }
     public void changeEventColumnName(ValueChangeEvent changeEvent) {
        if (changeEvent.getNewValue() != null) {
            columnNameResolver.setCol_Name_FromTable(changeEvent.getNewValue().toString());
            columnNameResolver.setParsed_Col_Name(ColumnParser(columnNameResolver.getCol_Name_FromTable()));
            prmsGoodsEntrance.setColumnName(columnNameResolver.getCol_Name_FromTable());
        }
    }

    public String ColumnParser(String col_name) {
        String col = col_name.replace("_", " ");
        System.out.println("col==" + col);
        return col.toLowerCase()+":";
    }

//</editor-fold>
   
    //<editor-fold defaultstate="collapsed" desc="post Costract">
    @PostConstruct
    public void init() {
        allDepartmentsList = hrDepartmentsFacade.findAll();
        root = new DefaultTreeNode("Root", null);
        recursive(allDepartmentsList, 0, root);
        root2 = getRoot();
        ColumnNameResolverList = goodsEntranceBeanLocal.getColumnNameList();
        wfPrmsProcessed.setProcessedBy(sessionBean.getUserId());
        setUserName(sessionBean.getUserName());
        System.out.println("is App==" + workFlow.isApproveStatus() + "is chech==" + workFlow.isCheckStatus() + "is prepa==" + workFlow.isPrepareStatus());
        wfPrmsProcessed.setProcessedBy(workFlow.getUserAccount());
        setLoggerName(workFlow.getUserName());
        System.out.println("is app---" + workFlow.isApproveStatus() + "is check===" + workFlow.isCheckStatus() + "is prep==" + workFlow.isPrepareStatus());
        if (workFlow.isPrepareStatus()) {
            renderDecision = false;
            isRendercreate = true;
            isRenderNotify = false;
        } else if (workFlow.isApproveStatus()) {
            renderDecision = true;
            isRendercreate = false;
            isRenderNotify = true;
        } else if (workFlow.isCheckStatus()) {
            renderDecision = true;
            isRendercreate = false;
            isRenderNotify = true;
        
        }
    }
    
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Event changes">
    public void onRowEdit(RowEditEvent event) {
        renderPnlCreateParty = true;
        renderPnlManPage = false;
        activeIndex = "0";
        saveorUpdateBundle = "Update";
        disableBtnCreate = true;
        updateStatus = 1;
        
        int rowIndex = prmsGoodsEntrancesModel.getRowIndex();
        prmsGoodsEntrance = goodsEntrances.get(rowIndex);
        
        recreatGoodsEnteranceModel();
        
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Search &Save">
    public String saveGoods() {
        try {
             System.out.println("saveorUpdateBundle==11111="+saveorUpdateBundle);
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(sessionBean.getUserName(), "saveGoods", dataset)) {
                System.out.println("saveorUpdateBundle==="+saveorUpdateBundle);
                if (saveorUpdateBundle.equals("Save")) {
                    if (saveStatus == 0) {
                        try {
                            System.out.println("inside save method");
                            generateRegsNo();
                            prmsGoodsEntrance.setRegistrationNo(newRegNo);
                            prmsGoodsEntrance.setBankPermit(prmsLcRigistration.getForeignId().getFeNumber());
                            prmsGoodsEntrance.setSupplier(prmsContract.getSuppId().getVendorName());
                            prmsGoodsEntrance.setCotractId(prmsContract);
                            prmsGoodsEntrance.setCurrencyId(fmsLuCurrency);
                            prmsGoodsEntrance.setPortOfDischarge(portOfDischarge);
                            prmsGoodsEntrance.setLcId(prmsLcRigistration);
                            prmsGoodsEntrance.setCountryId(comLuCountry);
                            prmsGoodsEntrance.setPrepatedBy(wfPrmsProcessed.getProcessedBy());
                            prmsGoodsEntrance.setStatus(Constants.PREPARE_VALUE);
                            prmsGoodsEntrance.setProcessedOn(wfPrmsProcessed.getProcessedOn());
                            wfPrmsProcessed.setDecision(String.valueOf(prmsGoodsEntrance.getStatus()));
                            prmsLuDmArchive.setFileName(fileName);
                            prmsLuDmArchive.setFileType(fileType);
                            prmsLuDmArchive.setUploadFile(byteFile);
                            prmsLuDmArchiveBeanLocal.saveFileInfo(prmsLuDmArchive);
                            prmsGoodsEntrance.setDocumentId(prmsLuDmArchive);
                            prmsGoodsEntrance.getWfPrmsProcessedLists().add(wfPrmsProcessed);
                            goodsEntranceBeanLocal.create(prmsGoodsEntrance);
                            JsfUtil.addSuccessMessage("Goods Entrance information is Saved successfully !!");
                            return ClearGoodsEnterance();
                        } catch (Exception e) {
                            e.printStackTrace();
                            JsfUtil.addFatalMessage("some thing is going to error" + e);
                            ClearGoodsEnterance();
                            return null;
                        }
                    }
                } else if (workFlow.isPrepareStatus() && saveorUpdateBundle.equalsIgnoreCase("Update")) {
                    try {
                        prmsGoodsEntrance.setCountryId(comLuCountry);
                        prmsGoodsEntrance.setLcId(prmsLcRigistration);
                        prmsGoodsEntrance.setCotractId(prmsContract);
                        prmsGoodsEntrance.setCurrencyId(fmsLuCurrency);
                        prmsGoodsEntrance.setPortOfDischarge(portOfDischarge);
                        prmsGoodsEntrance.setProcessedOn(wfPrmsProcessed.getProcessedOn());
                        goodsEntranceBeanLocal.update(prmsGoodsEntrance);
                        JsfUtil.addSuccessMessage("Goods Entrance information is updated ");
                        saveorUpdateBundle = "Save";
                        return ClearGoodsEnterance();
                    } catch (Exception e) {
                        JsfUtil.addFatalMessage("error== w/n data modification" + e);
                        ClearGoodsEnterance();
                    }
                } else if ((workFlow.isApproveStatus() || workFlow.isCheckStatus()) && saveorUpdateBundle.equalsIgnoreCase("Update")) {
                    try {
                        if (selectedValue.equalsIgnoreCase("Approve") && workFlow.isApproveStatus()) {
                            workFlow.setUserStatus(Constants.APPROVE_VALUE);
                            prmsGoodsEntrance.setStatus(Constants.APPROVE_VALUE);
                            wfPrmsProcessed.setDecision(String.valueOf(Constants.APPROVE_VALUE));
                        } else if (selectedValue.equalsIgnoreCase("Approve") && workFlow.isCheckStatus()) {
                            workFlow.setUserStatus(Constants.CHECK_APPROVE_VALUE);
                            prmsGoodsEntrance.setStatus(Constants.CHECK_APPROVE_VALUE);
                            wfPrmsProcessed.setDecision(String.valueOf(Constants.CHECK_APPROVE_VALUE));
                        } else if (selectedValue.equalsIgnoreCase("Reject") && workFlow.isApproveStatus()) {
                            workFlow.setUserStatus(Constants.APPROVE_REJECT_VALUE);
                            prmsGoodsEntrance.setStatus(Constants.APPROVE_REJECT_VALUE);
                            wfPrmsProcessed.setDecision(String.valueOf(Constants.APPROVE_REJECT_VALUE));
                        } else if (selectedValue.equalsIgnoreCase("Reject") && workFlow.isCheckStatus()) {
                            workFlow.setUserStatus(Constants.CHECK_REJECT_VALUE);
                            prmsGoodsEntrance.setStatus(Constants.CHECK_REJECT_VALUE);
                            wfPrmsProcessed.setDecision(String.valueOf(Constants.CHECK_REJECT_VALUE));
                        }
                        wfPrmsProcessed.setGoodsId(prmsGoodsEntrance);
                        goodsEntranceBeanLocal.update(prmsGoodsEntrance);
                        wfPrmsProcessedBeanLocal.savewf(wfPrmsProcessed);
                        JsfUtil.addSuccessMessage("Goods Entrance Decision is Given");
                        saveorUpdateBundle = "Save";
                        return ClearGoodsEnterance();
                    } catch (Exception e) {
                        JsfUtil.addFatalMessage("error== w/n data modification" + e);
                        ClearGoodsEnterance();
                    }
                }
                
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
            
        }
        return null;
    }
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Clera page">
    private String ClearGoodsEnterance() {
        prmsGoodsEntrance = null;
        prmsLcRigistration = null;
        portOfDischarge = null;
        comLuCountry = null;
        prmsContract = null;
        fmsLuCurrency = null;
        departments = null;
        prmsGoodsEntrancesModel = null;
        newDoclist = new ArrayList<>();
        docValueModel = null;
        wfPrmsProcessed.setProcessedOn(null);
        wfPrmsProcessed.setCommentGiven(null);
        return null;
    }
    
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Handler">
    public void setSelectedLCNo(ValueChangeEvent e) {
        prmsLcRigistration = (PrmsLcRigistration) e.getNewValue();
        System.out.println("Lc Id "+prmsLcRigistration.getLcId());
        listOfLCAmended = goodsEntranceBeanLocal.getCheckLcAmended(prmsLcRigistration);
        if (listOfLCAmended != null) {
            System.out.println("Amend lis will Exist by " + prmsLcRigistration.getLcId());
            prmsLcRigistrationAmend = goodsEntranceBeanLocal.getLcAmendedInfo(prmsLcRigistration);
             System.out.println("LC Amend id "+prmsLcRigistrationAmend.getId());
            prmsLcRigistration.setLcNo(prmsLcRigistrationAmend.getLcNo());
            System.out.println("LC Amend "+prmsLcRigistrationAmend.getForeignId());
           
            prmsLcRigistration.setForeignId(prmsLcRigistrationAmend.getForeignId());;
        } else {
            System.out.println("Not Amended by " + prmsLcRigistration.getLcId());
        }
        
    }
    
    public PrmsPortEntry getPortOfDischarge() {
        if (portOfDischarge == null) {
            portOfDischarge = new PrmsPortEntry();
        }
        return portOfDischarge;
    }
    
    public void setPortOfDischarge(PrmsPortEntry portOfDischarge) {
        this.portOfDischarge = portOfDischarge;
    }
    
    public List<PrmsPortEntry> getListOfPorts() {
        if (listOfPorts == null) {
            listOfPorts = new ArrayList<>();
            
        }
        return listOfPorts;
    }
    
    public void setListOfPorts(List<PrmsPortEntry> listOfPorts) {
        this.listOfPorts = listOfPorts;
    }
    
    public SelectItem[] listOfPort() {
        return JsfUtil.getSelectItems(goodsEntranceBeanLocal.listOfPort(), true);
    }
    
    public PrmsContract getPrmsContract() {
        return prmsContract;
    }
    
    public void setPrmsContract(PrmsContract prmsContract) {
        this.prmsContract = prmsContract;
    }
    
    public List<PrmsGoodsEntrance> getActionPlanList() {
        return goodsEntrances;
    }
    
    public void setActionPlanList(List<PrmsGoodsEntrance> goodsEntrances) {
        this.goodsEntrances = goodsEntrances;
    }
    
    public void recreatGoodsEnteranceModel() {
        prmsGoodsEntrancesModel = null;
        prmsGoodsEntrancesModel = new ListDataModel(new ArrayList(getPrmsGoodsEntrances()));
    }
    
    public String getLastRegNo() {
        return lastRegNo;
    }
    
    public void setLastRegNo(String lastRegNo) {
        this.lastRegNo = lastRegNo;
    }
    
    public String getNewRegNo() {
        return newRegNo;
    }
    
    public void setNewRegNo(String newRegNo) {
        this.newRegNo = newRegNo;
    }
    
    String lastRegNo = "0";
    String newRegNo;
    
    public String generateRegsNo() {
        if (saveorUpdateBundle.equals("Update")) {
            newRegNo = prmsGoodsEntrance.getRegistrationNo();
        } else {
            PrmsGoodsEntrance lastRegNoObj = goodsEntranceBeanLocal.getLastRegNo();
            if (lastRegNoObj != null) {
                lastRegNo = lastRegNoObj.getId();
            }
            DateFormat f = new SimpleDateFormat("yyyy");
            Date now = new Date();
            int newReg;
            if (lastRegNo.equals("0")) {
                newReg = 1;
                newRegNo = "IGE-NO-" + newReg + "/" + f.format(now);
                
            } else {
                String[] lastRegNos = lastRegNo.split("-");
                String lastDatesPatern = lastRegNos[0];
                String[] lastDatesPaterns = lastDatesPatern.split("/");
                newReg = Integer.parseInt(lastDatesPaterns[0]);
                newReg = newReg + 1;
                newRegNo = "IGE-NO-" + newReg + "/" + f.format(now);
            }
        }
        return newRegNo;
        
    }
    
    public void addNewGoodEnterance() {
        currencyLists = goodsEntranceBeanLocal.getCurrencyName();
        listOfLC = goodsEntranceBeanLocal.listOfLcNO();
        listOfPorts = goodsEntranceBeanLocal.listOfPort();
        ClearGoodsEnterance();
        saveorUpdateBundle = "Save";
        disableBtnCreate = false;
        renderpnlToSeachPage = false;
        if (createOrSearchBundle.equals("New")) {
            renderPnlCreateParty = true;
            renderPnlManPage = false;
            createOrSearchBundle = "Search";
            setIcone("ui-icon-search");
        } else if (createOrSearchBundle.equals("Search")) {
            renderPnlCreateParty = false;
            renderPnlManPage = true;
            createOrSearchBundle = "New";
            setIcone("ui-icon-plus");
        }
    }
    
    public void goBackToSearchpageBtnAction() {
        renderpnlToSeachPage = false;
        renderPnlCreateParty = false;
        renderPnlManPage = true;
    }
    
    public void totalSum() {
        if (prmsGoodsEntrance.getMiscellaneousCost() != null && prmsGoodsEntrance.getCustomCost() != null && prmsGoodsEntrance.getServiceCost() != null) {
            prmsGoodsEntrance.setTotalCost(prmsGoodsEntrance.getMiscellaneousCost() + prmsGoodsEntrance.getCustomCost() + prmsGoodsEntrance.getServiceCost());
        }
        
    }
    
    public void payableAmount() {
        if (prmsGoodsEntrance.getDepositeCost() != null && prmsGoodsEntrance.getTotalCost() != null) {
            if (prmsGoodsEntrance.getDepositeCost() < prmsGoodsEntrance.getTotalCost()) {
                prmsGoodsEntrance.setCashPayable(prmsGoodsEntrance.getTotalCost() - prmsGoodsEntrance.getDepositeCost());
                System.out.println("1");
                System.out.println("cash payable");
                renderPayable = false;
                renderRecevable = true;
                
            } else {
                System.out.println("2");
                prmsGoodsEntrance.setCashReceivable(prmsGoodsEntrance.getDepositeCost() - prmsGoodsEntrance.getTotalCost());
                System.out.println("cash receivable");
                renderRecevable = false;
                renderPayable = true;
            }
        }
        
    }
    
    /**
     * @return the listOfLC
     */
    public List<PrmsLcRigistration> getListOfLC() {
        if (listOfLC == null) {
            listOfLC = new ArrayList<>();
            listOfLC = goodsEntranceBeanLocal.listOfLcNO();
        }
        return listOfLC;
    }
    
    /**
     * @param listOfLC the listOfLC to set
     */
    public void setListOfLC(List<PrmsLcRigistration> listOfLC) {
        this.listOfLC = listOfLC;
    }
    
    StreamedContent file;
    
    DocumentModel documentModel = new DocumentModel();
    
    public DocumentModel getDocumentModel() {
        return documentModel;
    }
    
    public void setDocumentModel(DocumentModel documentModel) {
        this.documentModel = documentModel;
    }
    
    DataModel<DocumentModel> docValueModel;
    DataModel<PrmsFileUpload> fileUploadDataModel;
    
    public DataModel<DocumentModel> getDocValueModel() {
        return docValueModel;
    }
    
    public void setDocValueModel(DataModel<DocumentModel> docValueModel) {
        this.docValueModel = docValueModel;
    }
    
    public void onRowSelect(SelectEvent event) {
        prmsLuDmArchive = (PrmsLuDmArchive) event.getObject();
        isFileSelected = true;
    }
    
    public DataModel<PrmsFileUpload> getFileUploadDataModel() {
        return fileUploadDataModel;
    }
    
    public void setFileUploadDataModel(DataModel<PrmsFileUpload> fileUploadDataModel) {
        this.fileUploadDataModel = fileUploadDataModel;
    }
    List<PrmsFileUpload> documentList;
    
    public void recreateDmsDataModel() {
        prmsLuDmArchivesDModel = null;
        prmsLuDmArchivesDModel = new ListDataModel<>(prmsLuDmArchivesList);
    }
    
    List<DocumentModel> newDoclist = new ArrayList<>();
    List<Integer> savedDocIds = new ArrayList<>();
    DmsHandler dmsHandler = new DmsHandler();
    int savedDocId;
    
    public void uploadListener(FileUploadEvent event) {
        InputStream inputStream = null;
        try {
            fileName = event.getFile().getFileName().split("\\.")[0];
            fileType = event.getFile().getFileName().split("\\.")[1];
            inputStream = event.getFile().getInputstream();
            byteFile = dataUpload.extractByteArray(inputStream);
            if (byteFile != null) {
                System.out.println("byte File is  " + byteFile);
                JsfUtil.addSuccessMessage("Upload Successfully!!!");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public void getfile() {
        docValueModel = dataUpload.selectListOfFileByDocId(doLst);
        System.out.println("=========no of documents" + docValueModel.getRowCount());
    }
    
    public StreamedContent getFile() throws Exception {
        if (isFileSelected = true) {
            System.out.println("Selected Dile Name is " + prmsLuDmArchive.getFileName());
            dataUpload.getPrmsFileRefNumber(prmsLuDmArchive);
        } else {
            JsfUtil.addFatalMessage("Pls Select File Name");
        }
        return file;
    }
    
    public void remove() {
        getPrmsFileUpload().setId(getPrmsFileUpload().getId());
        goodsEntranceBeanLocal.remove(getPrmsFileUpload());
        prmsLcRigistration.getPrmsFileUploadList().remove(getPrmsFileUpload());
        recreateDmsDataModel();
        
    }
    
    public void remove(DocumentModel document) {
        document = documentModel;
        DmsHandler dmsHandler = new DmsHandler();
        dmsHandler.getRemove(document);
        recreateDmsDataModel();
    }
    List<Integer> doLst = new ArrayList<>();
    int docId;
    
    /**
     * @return the wfPrmsProcessed
     */
    public WfPrmsProcessed getWfPrmsProcessed() {
        return wfPrmsProcessed;
    }
    
    /**
     * @param wfPrmsProcessed the wfPrmsProcessed to set
     */
    public void setWfPrmsProcessed(WfPrmsProcessed wfPrmsProcessed) {
        this.wfPrmsProcessed = wfPrmsProcessed;
    }
    
    /**
     * @return the wfPrmsProcessedDModel
     */
    public DataModel<WfPrmsProcessed> getWfPrmsProcessedDModel() {
        if (wfPrmsProcessedDModel == null) {
            wfPrmsProcessedDModel = new ArrayDataModel<>();
        }
        return wfPrmsProcessedDModel;
    }
    
    /**
     * @param wfPrmsProcessedDModel the wfPrmsProcessedDModel to set
     */
    public void setWfPrmsProcessedDModel(DataModel<WfPrmsProcessed> wfPrmsProcessedDModel) {
        this.wfPrmsProcessedDModel = wfPrmsProcessedDModel;
    }
    
    public void handleselectOptions(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            selectedValue = event.getNewValue().toString();
            System.out.println(" selecte decision " + selectedValue);
            
        }
    }
    
    /**
     * @return the selectedValue
     */
    public String getSelectedValue() {
        return selectedValue;
    }
    
    /**
     * @param selectedValue the selectedValue to set
     */
    public void setSelectedValue(String selectedValue) {
        this.selectedValue = selectedValue;
    }
    
    /**
     * @return the workFlow
     */
    public WorkFlow getWorkFlow() {
        if (workFlow == null) {
            workFlow = new WorkFlow();
        }
        return workFlow;
    }
    
    /**
     * @param workFlow the workFlow to set
     */
    public void setWorkFlow(WorkFlow workFlow) {
        this.workFlow = workFlow;
    }
    
    /**
     * @return the loggerName
     */
    public String getLoggerName() {
        return loggerName;
    }
    
    /**
     * @param loggerName the loggerName to set
     */
    public void setLoggerName(String loggerName) {
        this.loggerName = loggerName;
    }
    
    /**
     * @return the renderDecision
     */
    public boolean isRenderDecision() {
        return renderDecision;
    }
    
    /**
     * @param renderDecision the renderDecision to set
     */
    public void setRenderDecision(boolean renderDecision) {
        this.renderDecision = renderDecision;
    }
    
    /**
     * @return the requestNotificationCounter
     */
    public int getRequestNotificationCounter() {
        goodsEntrances = goodsEntranceBeanLocal.getGoodsLists();
        requestNotificationCounter = goodsEntrances.size();
        return requestNotificationCounter;
    }
    
    /**
     * @param requestNotificationCounter the requestNotificationCounter to set
     */
    public void setRequestNotificationCounter(int requestNotificationCounter) {
        this.requestNotificationCounter = requestNotificationCounter;
    }
    
    public void RequestListChange(ValueChangeEvent eve) {
        //  if (eve.getNewValue() != null) {
        // goodsEntrances = goodsEntranceBeanLocal.searchByGoodsNo(prmsGoodsEntrance);
        prmsGoodsEntrance = (PrmsGoodsEntrance) eve.getNewValue();
        populateDataForApp();
        // }
    }
    
    public void populateDataForApp() {
        prmsGoodsEntrance.setId(prmsGoodsEntrance.getId());
        prmsGoodsEntrance.setId(prmsGoodsEntrance.getId());
        prmsGoodsEntrance = goodsEntranceBeanLocal.getSelectedRequest(prmsGoodsEntrance.getId());
        currencyLists = goodsEntranceBeanLocal.getCurrencyName();
        fmsLuCurrency = prmsGoodsEntrance.getCurrencyId();
        countryList = goodsEntranceBeanLocal.getCountryList();
        comLuCountry = prmsGoodsEntrance.getCountryId();
        prmsContract = prmsGoodsEntrance.getCotractId();
        prmsLcRigistration = prmsGoodsEntrance.getLcId();
        listOfPorts = goodsEntranceBeanLocal.listOfPort();
        portOfDischarge = prmsGoodsEntrance.getPortOfDischarge();
        prmsGoodsEntrance.setProcessedOn(wfPrmsProcessed.getProcessedOn());
        departments = prmsGoodsEntrance.getDepId();
        if (prmsGoodsEntrance.getDocumentId() != null) {
            prmsLuDmArchive = prmsGoodsEntrance.getDocumentId();
            prmsLuDmArchivesList = prmsLuDmArchiveBeanLocal.getFileLists(prmsLuDmArchive);
        }
        renderPnlManPage = false;
        renderPnlCreateParty = true;
        saveorUpdateBundle = "Update";
        
    }
    
    /**
     * @return the prmsFileUpload
     */
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="FileUpload">
    public PrmsFileUpload getPrmsFileUpload() {
        if (prmsFileUpload == null) {
            prmsFileUpload = new PrmsFileUpload();
        }
        return prmsFileUpload;
    }
    
    /**
     * @param prmsFileUpload the prmsFileUpload to set
     */
    public void setPrmsFileUpload(PrmsFileUpload prmsFileUpload) {
        this.prmsFileUpload = prmsFileUpload;
    }
    
    /**
     * @return the isRendercreate
     */
    public boolean isIsRendercreate() {
        return isRendercreate;
    }
    
    /**
     * @param isRendercreate the isRendercreate to set
     */
    public void setIsRendercreate(boolean isRendercreate) {
        this.isRendercreate = isRendercreate;
    }
    
    public boolean isIsRenderNotify() {
        return isRenderNotify;
    }
    
    public void setIsRenderNotify(boolean isRenderNotify) {
        this.isRenderNotify = isRenderNotify;
    }
    
    public boolean isRenderRecevable() {
        return renderRecevable;
    }
    
    public void setRenderRecevable(boolean renderRecevable) {
        this.renderRecevable = renderRecevable;
    }
    
    public boolean isRenderPayable() {
        return renderPayable;
    }
    
    public void setRenderPayable(boolean renderPayable) {
        this.renderPayable = renderPayable;
    }
    
    /**
     * @return the isRowFileSelected
     */
    public boolean isIsRowFileSelected() {
        return isRowFileSelected;
    }
    
    /**
     * @param isRowFileSelected the isRowFileSelected to set
     */
    public void setIsRowFileSelected(boolean isRowFileSelected) {
        this.isRowFileSelected = isRowFileSelected;
    }
    
    /**
     * @return the wfMmsProcessed
     */
    public WfMmsProcessed getWfMmsProcessed() {
        if (wfMmsProcessed == null) {
            wfMmsProcessed = new WfMmsProcessed();
        }
        return wfMmsProcessed;
    }
    
    /**
     * @param wfMmsProcessed the wfMmsProcessed to set
     */
    public void setWfMmsProcessed(WfMmsProcessed wfMmsProcessed) {
        this.wfMmsProcessed = wfMmsProcessed;
    }
    
    public List<PrmsContractAmendment> getPrmsContractAmendmentLst() {
        if (prmsContractAmendmentLst == null) {
            prmsContractAmendmentLst = new ArrayList<>();
        }
        return prmsContractAmendmentLst;
    }
    
    public void setPrmsContractAmendmentLst(List<PrmsContractAmendment> prmsContractAmendmentLst) {
        this.prmsContractAmendmentLst = prmsContractAmendmentLst;
    }
    
    public PrmsContractAmendment getPrmsContractAmendment() {
        if (prmsContractAmendment == null) {
            prmsContractAmendment = new PrmsContractAmendment();
        }
        return prmsContractAmendment;
    }
    
    public void setPrmsContractAmendment(PrmsContractAmendment prmsContractAmendment) {
        this.prmsContractAmendment = prmsContractAmendment;
    }
    
    /**
     * @return the listOfLCAmended
     */
    public List<PrmsLcRigistrationAmend> getListOfLCAmended() {
        if (listOfLCAmended == null) {
            listOfLCAmended = new ArrayList<>();
        }
        return listOfLCAmended;
    }
    
    /**
     * @param listOfLCAmended the listOfLCAmended to set
     */
    public void setListOfLCAmended(List<PrmsLcRigistrationAmend> listOfLCAmended) {
        this.listOfLCAmended = listOfLCAmended;
    }
    
    public void contractAmount() {
        System.out.println("1");
        String contractAmount = "";
        for (int i = 0; i < prmsContract.getPrmsContractCurrencyDetailList().size(); i++) {
            if (prmsContract.getPrmsContractCurrencyDetailList().size() > 0) {
                contractAmount = contractAmount + prmsContract.getPrmsContractCurrencyDetailList().get(i).getAmount() + " " + prmsContract.getPrmsContractCurrencyDetailList().get(i).getCurrencyId().getCurrency() + ",";
            }
        }
        prmsGoodsEntrance.setContractAmount(contractAmount);
        
    }
    
    public boolean isIsFileSelected() {
        return isFileSelected;
    }
    
    public void setIsFileSelected(boolean isFileSelected) {
        this.isFileSelected = isFileSelected;
    }
    
    public PrmsLuDmArchive getPrmsLuDmArchive() {
        if (prmsLuDmArchive == null) {
            prmsLuDmArchive = new PrmsLuDmArchive();
        }
        return prmsLuDmArchive;
    }
    
    public void setPrmsLuDmArchive(PrmsLuDmArchive prmsLuDmArchive) {
        this.prmsLuDmArchive = prmsLuDmArchive;
    }
    
    public PrmsLuDmArchive getPrmsLuDmArchiveSelection() {
        if (prmsLuDmArchiveSelection == null) {
            prmsLuDmArchiveSelection = new PrmsLuDmArchive();
        }
        return prmsLuDmArchiveSelection;
    }
    
    public void setPrmsLuDmArchiveSelection(PrmsLuDmArchive prmsLuDmArchiveSelection) {
        this.prmsLuDmArchiveSelection = prmsLuDmArchiveSelection;
    }
    
    public DataModel<PrmsLuDmArchive> getPrmsLuDmArchivesDModel() {
        return prmsLuDmArchivesDModel;
    }
    
    public void setPrmsLuDmArchivesDModel(DataModel<PrmsLuDmArchive> prmsLuDmArchivesDModel) {
        this.prmsLuDmArchivesDModel = prmsLuDmArchivesDModel;
    }
    
    public List<PrmsLuDmArchive> getPrmsLuDmArchivesList() {
        if (prmsLuDmArchivesList == null) {
            prmsLuDmArchivesList = new ArrayList<>();
        }
        return prmsLuDmArchivesList;
    }
    
    public void setPrmsLuDmArchivesList(List<PrmsLuDmArchive> prmsLuDmArchivesList) {
        this.prmsLuDmArchivesList = prmsLuDmArchivesList;
    }
}
//</editor-fold>

