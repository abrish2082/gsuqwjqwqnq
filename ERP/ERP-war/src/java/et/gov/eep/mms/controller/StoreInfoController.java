/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.mms.controller;

import et.gov.eep.mms.businessLogic.MmsStoreInformationBeanLocal;
import et.gov.eep.hrms.integration.HREmployeesBeanLocal;
import et.gov.eep.mms.entity.MmsStoreInformation;
import et.gov.eep.hrms.entity.employee.HrEmployees;
import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.mms.businessLogic.MmsLuStoreTypeBeanLocal;
import et.gov.eep.mms.businessLogic.MmsLuWarehouseBeanLocal;
import et.gov.eep.commonApplications.businessLogic.WfMmsProcessedBeanLocal;
import et.gov.eep.commonApplications.entity.ColumnNameResolver;
import et.gov.eep.mms.entity.MmsLuStoreType;
import et.gov.eep.mms.entity.MmsLuWareHouse;
import et.gov.eep.commonApplications.entity.WfMmsProcessed;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
//import javax.xml.bind.JAXBElement;
//import javax.xml.namespace.QName;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.chart.DonutChartModel;
import securityBean.Constants;
import securityBean.SessionBean;
import securityBean.WorkFlow;
import webService.AAA;
import webService.EventEntry;
import webService.IAdministration;

/**
 *
 * @author minab
 */
@Named(value = "storeInfoManagedBean")
@ViewScoped
public class StoreInfoController implements Serializable {

    //<editor-fold defaultstate="collapsed" desc="Entity's">
    @EJB
    WfMmsProcessedBeanLocal wfMmsProcessedBeanLocal;
    @EJB
    MmsLuWarehouseBeanLocal mmsLuwareHouseBeanLocal;
    @EJB
    MmsLuStoreTypeBeanLocal storeTypeBeanLocal;
    @EJB
    MmsStoreInformationBeanLocal storeInfoInterface;
    @EJB
    HREmployeesBeanLocal hrEmployeeInterface;
    @Inject
    ColumnNameResolver columnNameResolver;
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="EJB's">
    @Inject
    HrEmployees hrEmployeesEntity;
    @Inject
    MmsLuWareHouse MmsLuWarehouse;
    @Inject
    MmsLuStoreType mmsLuStoreType;
    @Inject
    MmsStoreInformation storeInfoEntity;
    @Inject
    WfMmsProcessed wfMmsProcessed;
    @Inject
    SessionBean SessionBean;
    @Inject
    WorkFlow workflow;
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Fields">
    int updateStatus = 0;
    private String saveorUpdateBundle = "Save";
    private DataModel<MmsStoreInformation> mmsStoreSearchInfoDataModel;
    List<MmsStoreInformation> allStoreInfoList;
    private List<MmsStoreInformation> mmsStoreInfoColumnNameList;
    DataModel<WfMmsProcessed> WorkflowDataModel = new ListDataModel<>();
    List<WfMmsProcessed> WfList;
    List<MmsLuStoreType> MmsLuStoreTypeList = new ArrayList<>();
    List<MmsLuWareHouse> MmsLuWareHouseList = new ArrayList();
    private boolean disableBtnCreate;
    private boolean renderPnlCreateStoreInformation = false;
    private boolean renderPnlManPage = true;
    private boolean renderStoreInfo = false;
    private boolean renderpnlToSearchPage;
    private String createOrSearchBundle = "New";
    private String icone = "ui-icon-plus";
    private String activeIndex;
    private String selectoptStoreName;
    private String UserName;
    private MmsStoreInformation selectedItem;
    int newStoreNO;
    String lastStoreNumber = "0";
    private List<String> mmsStoreColumnNameList;
    ColumnNames columnNames = new ColumnNames();
    List<ColumnNameResolver> ColumnNamesList = new ArrayList<>();
    private DonutChartModel donutModel;
//</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Constructor">

    public StoreInfoController() {
    }

    /**
     *
     * @return
     */
    @PostConstruct
    public void initwfMmsProcessed() {
        wfMmsProcessed.setProcessedBy(SessionBean.getUserId());
        setUserName(SessionBean.getUserName());
        System.out.println("is App==" + workflow.isApproveStatus() + "is chech==" + workflow.isCheckStatus() + "is prepa==" + workflow.isPrepareStatus());
        MmsLuStoreTypeList = storeTypeBeanLocal.findAll();
        MmsLuWareHouseList = mmsLuwareHouseBeanLocal.findAll();
        getMmsStoreColumnNameList();
        createDonutModels();
    }

    public class ColumnNames implements Serializable {

        String Table_Col_Name;
        String Parsed_Col_name;

        //<editor-fold defaultstate="collapsed" desc="getter and setter">
        public String getTable_Col_Name() {
            return Table_Col_Name;
        }

        public void setTable_Col_Name(String Table_Col_Name) {
            this.Table_Col_Name = Table_Col_Name;
        }

        public String getParsed_Col_name() {
            return Parsed_Col_name;
        }

        public void setParsed_Col_name(String Parsed_Col_name) {
            this.Parsed_Col_name = Parsed_Col_name;
        }

//</editor-fold>
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Getter And Setters and Post Constraction">
    public WfMmsProcessed getWfMmsProcessed() {
        if (wfMmsProcessed == null) {
            wfMmsProcessed = new WfMmsProcessed();
        }
        return wfMmsProcessed;
    }

    public void setWfHrProcessed(WfMmsProcessed wfMmsProcessed) {
        this.wfMmsProcessed = wfMmsProcessed;
    }

    public List<ColumnNameResolver> getColumnNamesList() {
        return ColumnNamesList;
    }

    public void setColumnNamesList(List<ColumnNameResolver> ColumnNamesList) {
        this.ColumnNamesList = ColumnNamesList;
    }

    public ColumnNameResolver getColumnNameResolver() {
        return columnNameResolver;
    }

    public void setColumnNameResolver(ColumnNameResolver columnNameResolver) {
        this.columnNameResolver = columnNameResolver;
    }

    public ColumnNames getColumnNames() {
        return columnNames;
    }

    public void setColumnNames(ColumnNames columnNames) {
        this.columnNames = columnNames;
    }

    public DonutChartModel getDonutModel() {
        return donutModel;
    }

    public void setDonutModel(DonutChartModel donutModel) {
        this.donutModel = donutModel;
    }

    public DataModel<WfMmsProcessed> getWorkflowDataModel() {
        return WorkflowDataModel;
    }

    public void setWorkflowDataModel(DataModel<WfMmsProcessed> WorkflowDataModel) {
        this.WorkflowDataModel = WorkflowDataModel;
    }

    public List<WfMmsProcessed> getWfList() {
        return WfList;
    }

    public void setWfList(List<WfMmsProcessed> WfList) {
        this.WfList = WfList;
    }

    public List<MmsLuStoreType> getMmsLuStoreTypeList() {
        return MmsLuStoreTypeList;
    }

    public void setMmsLuStoreTypeList(List<MmsLuStoreType> MmsLuStoreTypeList) {
        this.MmsLuStoreTypeList = MmsLuStoreTypeList;
    }

    public MmsLuStoreType getMmsLuStoreType() {
        return mmsLuStoreType;
    }

    public void setMmsLuStoreType(MmsLuStoreType mmsLuStoreType) {
        this.mmsLuStoreType = mmsLuStoreType;
    }

    public MmsLuWareHouse getMmsLuWarehouse() {
        return MmsLuWarehouse;
    }

    public void setMmsLuWarehouse(MmsLuWareHouse MmsLuWarehouse) {
        this.MmsLuWarehouse = MmsLuWarehouse;
    }

    public List<MmsLuWareHouse> getMmsLuWareHouseList() {
        return MmsLuWareHouseList;
    }

    public void setMmsLuWareHouseList(List<MmsLuWareHouse> MmsLuWareHouseList) {
        this.MmsLuWareHouseList = MmsLuWareHouseList;
    }

    public MmsStoreInformation getStoreInfoEntity() {
        if (storeInfoEntity == null) {
            storeInfoEntity = new MmsStoreInformation();
        }
        return storeInfoEntity;
    }

    /**
     *
     * @param storeInfoEntity
     */
    public void setStoreInfoEntity(MmsStoreInformation storeInfoEntity) {
        this.storeInfoEntity = storeInfoEntity;
    }

    /**
     *
     * @return
     */
    public HrEmployees getHrEmployeesEntity() {
        if (hrEmployeesEntity == null) {
            hrEmployeesEntity = new HrEmployees();
        }
        return hrEmployeesEntity;
    }

    /**
     *
     * @param hrEmployeesEntity
     */
    public void setHrEmployeesEntity(HrEmployees hrEmployeesEntity) {
        this.hrEmployeesEntity = hrEmployeesEntity;
    }

    /**
     *
     * @return
     */
    public String getSaveorUpdateBundle() {
        return saveorUpdateBundle;
    }

    /**
     *
     * @param saveorUpdateBundle
     */
    public void setSaveorUpdateBundle(String saveorUpdateBundle) {
        this.saveorUpdateBundle = saveorUpdateBundle;
    }

    public boolean isRenderStoreInfo() {
        return renderStoreInfo;
    }

    public void setRenderStoreInfo(boolean renderStoreInfo) {
        this.renderStoreInfo = renderStoreInfo;
    }

    public boolean isRenderpnlToSearchPage() {
        return renderpnlToSearchPage;
    }

    public void setRenderpnlToSearchPage(boolean renderpnlToSearchPage) {
        this.renderpnlToSearchPage = renderpnlToSearchPage;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String UserName) {
        this.UserName = UserName;
    }

    public String getIcone() {
        return icone;
    }

    public void setIcone(String icone) {
        this.icone = icone;
    }

    public String getSelectoptStoreName() {
        return selectoptStoreName;
    }

    public void setSelectoptStoreName(String selectoptStoreName) {
        this.selectoptStoreName = selectoptStoreName;
    }

    public MmsStoreInformation getSelectedItem() {
        return selectedItem;
    }

    public void setSelectedItem(MmsStoreInformation selectedItem) {
        this.selectedItem = selectedItem;
    }

    public boolean isDisableBtnCreate() {
        return disableBtnCreate;
    }

    public void setDisableBtnCreate(boolean disableBtnCreate) {
        this.disableBtnCreate = disableBtnCreate;
    }

    public String getCreateOrSearchBundle() {
        return createOrSearchBundle;
    }

    public void setCreateOrSearchBundle(String createOrSearchBundle) {
        this.createOrSearchBundle = createOrSearchBundle;
    }
//</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Search, Event Handlers and other Methods">

    public void handleSelectStoreType(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            mmsLuStoreType = (MmsLuStoreType) event.getNewValue();
            storeInfoEntity.setStorType(mmsLuStoreType);
        }
    }

    public void handleSelectWarehouse(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            MmsLuWarehouse = (MmsLuWareHouse) event.getNewValue();
            storeInfoEntity.setWarehouseId(MmsLuWarehouse);
        }
    }

    /**
     *
     * @return
     */
    /**
     *
     * @param storeName
     * @return
     */
    public ArrayList<MmsStoreInformation> searchStoreInformation(String storeName) {
        ArrayList<MmsStoreInformation> storeInformations = null;// to make the previous search  paramaters and results null;
        storeInfoEntity.setStoreName(storeName);
        storeInformations = storeInfoInterface.searchStoreInformation(storeInfoEntity);
        return storeInformations;
    }

    /**
     *
     * @param FirstName
     * @return
     */
    public ArrayList<HrEmployees> searchEmployeeInformation(String empid) {
        ArrayList<HrEmployees> employeeInformations = null;// to make the previous search  paramaters and results null;

        hrEmployeesEntity.setEmpId(empid);
        System.out.println("employee name" + hrEmployeesEntity.getFirstName());
        employeeInformations = hrEmployeeInterface.SearchByEmpId(hrEmployeesEntity);
        return employeeInformations;
    }

    /**
     *
     * @param event
     */
    public void getPapmsStoreInfo(SelectEvent event) {
        storeInfoEntity.setStoreName(event.getObject().toString());
        storeInfoEntity = storeInfoInterface.getPapmsStoreInformation(storeInfoEntity);

        hrEmployeesEntity.setFirstName(storeInfoEntity.getStoreAdmin().getFirstName());
        updateStatus = 1;
        saveorUpdateBundle = "Update";
    }

    /**
     *
     * @param event
     */
    public void getEmployeeInfo(SelectEvent event) {
        if (null != event.getObject().toString()) {
            String selectPapmssiv = event.getObject().toString();
            hrEmployeesEntity.setEmpId(selectPapmssiv);
            hrEmployeesEntity = hrEmployeeInterface.getByEmpId(hrEmployeesEntity);

            storeInfoEntity.setStoreAdmin(hrEmployeesEntity);

        }
    }

    public void goBackSearchButtonAction() {
        renderpnlToSearchPage = false;
        renderPnlCreateStoreInformation = false;
        renderPnlManPage = true;
    }

    public void createNewStoreInfo() {
        //
        clearPage();
        saveorUpdateBundle = "Save";
        disableBtnCreate = false;
        renderpnlToSearchPage = false;
        if (createOrSearchBundle.equals("New")) {
            renderPnlCreateStoreInformation = true;
            renderPnlManPage = false;
            createOrSearchBundle = "Search";
            setIcone("ui-icon-search");
            //generateGatePassNo();
        } else if (createOrSearchBundle.equals("Search")) {
            renderPnlCreateStoreInformation = false;
            renderPnlManPage = true;
            createOrSearchBundle = "New";
            setIcone("ui-icon-plus");
        }
    }

    public boolean isRenderPnlCreateStoreInformation() {
        return renderPnlCreateStoreInformation;
    }

    public void setRenderPnlCreateStoreInformation(boolean renderPnlCreateStoreInformation) {
        this.renderPnlCreateStoreInformation = renderPnlCreateStoreInformation;
    }

    public boolean isRenderPnlManPage() {
        return renderPnlManPage;
    }

    public void setRenderPnlManPage(boolean renderPnlManPage) {
        this.renderPnlManPage = renderPnlManPage;
    }

    public DataModel<MmsStoreInformation> getMmsStoreSearchInfoDataModel() {
        if (mmsStoreSearchInfoDataModel == null) {
            mmsStoreSearchInfoDataModel = new ListDataModel<>();
        }
        return mmsStoreSearchInfoDataModel;
    }

    public void setMmsStoreSearchInfoDataModel(DataModel<MmsStoreInformation> mmsStoreSearchInfoDataModel) {
        this.mmsStoreSearchInfoDataModel = mmsStoreSearchInfoDataModel;
    }

    public List<MmsStoreInformation> getMmsStoreInfoColumnNameList() {
        if (mmsStoreInfoColumnNameList == null) {
            mmsStoreInfoColumnNameList = new ArrayList<>();
//            mmsStoreInfoColumnNameList = storeInfoInterface.getMmsStoreInfoColumnNameList();

        }
        return mmsStoreInfoColumnNameList;
    }

    public void setMmsStoreInfoColumnNameList(List<MmsStoreInformation> mmsStoreInfoColumnNameList) {
        this.mmsStoreInfoColumnNameList = mmsStoreInfoColumnNameList;
    }

    public void searchStoreByParameter() {
        System.out.println("in search");
        storeInfoEntity.setProcessedBy(SessionBean.getUserId());
        System.out.println("processor " + storeInfoEntity.getProcessedBy());
        allStoreInfoList = storeInfoInterface.getStoreListsByParameter(columnNameResolver, storeInfoEntity, storeInfoEntity.getColumnValue());

        recerateStoreSearchModel();

    }
    String selectedValue = "";

    public void handleselectOptions(ValueChangeEvent event) {
        if (null != event.getNewValue()) {
            selectedValue = event.getNewValue().toString();
        }
    }

    private void recerateStoreSearchModel() {
        mmsStoreSearchInfoDataModel = null;
        mmsStoreSearchInfoDataModel = new ListDataModel(new ArrayList<>(allStoreInfoList));
    }

    public void onRowEdit(SelectEvent event) {
        storeInfoEntity = (MmsStoreInformation) event.getObject();
        mmsLuStoreType = storeInfoEntity.getStoreType();
        MmsLuWarehouse = storeInfoEntity.getWarehouseId();
        wfMmsProcessed.setProcessedOn(storeInfoEntity.getProcessedOn());

        renderPnlCreateStoreInformation = true;
        renderPnlManPage = false;
        renderStoreInfo = true;
        renderpnlToSearchPage = true;
        activeIndex = "0";
        saveorUpdateBundle = "Update";
        createOrSearchBundle = "New";
        setIcone("ui-icon-plus");
        disableBtnCreate = true;
        updateStatus = 1;
        //  storeInfoEntity.setStoreStatus(updateStatus);
        hrEmployeesEntity = storeInfoEntity.getStoreAdmin();
    }
//</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Save Method and GenerateStoreNo">
 /*This method is used to  Work flow save
     */

    public void wfSave() {
        System.out.println("inside wf save");
        wfMmsProcessed.setStoreId(storeInfoEntity);
        wfMmsProcessedBeanLocal.create(wfMmsProcessed);
    }
    /*This method is used to column Name Change Event
     */

    public void columnNameChangeEvent(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            System.out.println("event.getNewValue().toString()==" + event.getNewValue().toString());
            columnNameResolver.setCol_Name_FromTable(event.getNewValue().toString());
            columnNameResolver.setParsed_Col_Name(ColumnParser(columnNameResolver.getCol_Name_FromTable()));
            storeInfoEntity.setColumnName(columnNameResolver.getCol_Name_FromTable());
            storeInfoEntity.setColumnValue(null);
        }
    }

    public String ColumnParser(String col_name) {
        String col = col_name.replace("_", " ");
        System.out.println("col==" + col);
        return col;
    }

    public List<String> getMmsStoreColumnNameList() {
        mmsStoreColumnNameList = storeInfoInterface.getMmsStoreInfColumnNameList();
        if (mmsStoreColumnNameList == null) {
            mmsStoreColumnNameList = new ArrayList<>();
        }
        System.out.println("=======mmsStoreColumnNameList==" + mmsStoreColumnNameList);
        if (mmsStoreColumnNameList.size() > 0) {
            ColumnNamesList = new ArrayList<>();
            for (int i = 0; i < mmsStoreColumnNameList.size(); i++) {
                ColumnNameResolver obj = new ColumnNameResolver();
                obj.setCol_Name_FromTable((mmsStoreColumnNameList.get(i).toLowerCase()));
                obj.setParsed_Col_Name(ColumnParser((mmsStoreColumnNameList.get(i).toLowerCase())));
                ColumnNamesList.add(obj);
            }

        }
        return mmsStoreColumnNameList;
    }

    public void setMmsStoreColumnNameList(List<String> mmsStoreColumnNameList) {
        this.mmsStoreColumnNameList = mmsStoreColumnNameList;
    }

    private void createDonutModels() {
        donutModel = initDonutModel();
        donutModel.setTitle("Store Type");
        donutModel.setLegendPosition("w");
        donutModel.setShowDataLabels(true);
    }
    private DonutChartModel initDonutModel() {
        DonutChartModel model = new DonutChartModel();

        Map<String, Number> circle1 = new LinkedHashMap<String, Number>();
        circle1.put("Main Store", 150);
        circle1.put("Satelite Store", 400);
        circle1.put("Project Store", 200);      
        model.addCircle(circle1);
        return model;
    }
    /*This method is used to  Save the page
     */

    public void saveStoreInformation() {
        try {
            System.out.println("Wf" + wfMmsProcessed.getProcessedBy());
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(SessionBean.getUserName(), "saveStoreInformation", dataset)) {

                System.out.println("Wf" + wfMmsProcessed.getProcessedBy());
                boolean result = storeInfoInterface.getPapmsStoreInformationDup(storeInfoEntity);

                if (updateStatus == 0 && result == false) {
                    try {
                        System.out.println("store ifo save===" + storeInfoEntity.getStoreName());
                        System.out.println("store ifo save===" + wfMmsProcessed.getCommentGiven());
                        generateStoreNo();
                        storeInfoEntity.setStoreNo(Integer.toString(newStoreNO));
                        System.out.println("========Sore No" + newStoreNO);
                        System.out.println("strore type1=====" + mmsLuStoreType.getId());
                        storeInfoEntity.setStorType(mmsLuStoreType);
                        storeInfoEntity.setWarehouseId(MmsLuWarehouse);
                        storeInfoEntity.setStoreStatus(storeInfoEntity.getStoreStatus());
                        storeInfoEntity.setStatus(Constants.PREPARE_VALUE);
                        storeInfoEntity.addStoreIdToWorkflow(wfMmsProcessed);

                        setWorkFlowValues();
                        storeInfoInterface.create(storeInfoEntity);
                        JsfUtil.addSuccessMessage("Store information created");
                        clearPage();
                    } catch (Exception ex) {
                        JsfUtil.addErrorMessage("Something Error Occured on Data created");
                    }
                } else if (updateStatus == 0 && result == true) {
                    JsfUtil.addSuccessMessage("Duplicate Information is not allowed");
                } else {
                    try {
                        System.out.println("store status update======" + storeInfoEntity.getStoreStatus());

                        wfMmsProcessed.setStoreId(storeInfoEntity);
                        setWorkFlowValues();
                        storeInfoInterface.edit(storeInfoEntity);
                        JsfUtil.addSuccessMessage("Store Information Data Modified");
                        saveorUpdateBundle = "Save";
                        clearPage();
                    } catch (Exception e) {

                        JsfUtil.addSuccessMessage("Duplicate data exists");
                    }
                }
            } else {
                JsfUtil.addFatalMessage("You don't have appropriate permission, please contact the system administrator. ");
                EventEntry eventEntry = new EventEntry();
                eventEntry.setSessionId(SessionBean.getSessionID());
                eventEntry.setUserId(SessionBean.getUserId());
                QName qualifiedName = new QName("", "project");
                JAXBElement<String> test = new JAXBElement<String>(qualifiedName, String.class, null, SessionBean.getUserName());
                eventEntry.setUserLogin(test);
//..... add more information by calling fields defined in the object 
                security.addEventLog(eventEntry, dataset);
            }
        } catch (Exception ex) {
        }
    }
    /*This method is used to  Set work flow value
     */

    public void setWorkFlowValues() {
        storeInfoEntity.setProcessedOn(wfMmsProcessed.getProcessedOn());
        storeInfoEntity.setProcessedBy(SessionBean.getUserId());
    }

    /*This method is used to  Clear the page
     */
    public String clearPage() {
        storeInfoEntity = new MmsStoreInformation();
        hrEmployeesEntity = new HrEmployees();
        saveorUpdateBundle = "Save";
        updateStatus = 0;
        mmsLuStoreType = null;
        MmsLuWarehouse = null;
        wfMmsProcessed = null;
        mmsStoreSearchInfoDataModel = new ListDataModel<>();
        return null;
    }

    /*This method is used to  generate New Store No
     */
    public int generateStoreNo() {
        if (updateStatus == 1) {
            newStoreNO = Integer.parseInt(storeInfoEntity.getStoreNo());
        } else {
            System.out.println("=====Inside else of generate=====");
            MmsStoreInformation lastStoreNO = storeInfoInterface.getLastStoreNo();
            if (lastStoreNO != null) {
                lastStoreNumber = lastStoreNO.getStoreNo();

            }

            int newNumber = 0;
            if (lastStoreNumber.equals("0")) {

                newNumber = 1;
                newStoreNO = newNumber;
            } else {

//            
                newNumber = Integer.parseInt(lastStoreNumber);
                System.out.println("======new number==" + newNumber);
                newStoreNO = newNumber + 1;
            }
            System.out.println("=======new no======" + newStoreNO);
        }
        return newStoreNO;
    }
    //</editor-fold>

}
