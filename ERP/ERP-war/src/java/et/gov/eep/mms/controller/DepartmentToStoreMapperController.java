/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.mms.controller;

import et.gov.eep.commonApplications.entity.ColumnNameResolver;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.context.RequestContext;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.hrms.businessLogic.organization.HrDepartmentsBeanLocal;
import et.gov.eep.hrms.entity.organization.HrDepartments;
import et.gov.eep.mms.businessLogic.MmsLuWarehouseBeanLocal;
import et.gov.eep.mms.businessLogic.MmsStoreToDepartmentMapperBeanLocal;
import et.gov.eep.mms.entity.MmsLuWareHouse;
import et.gov.eep.mms.entity.MmsStoreToHrDepMapper;
import securityBean.SessionBean;

/**
 *
 * @author minab
 */
@Named(value = "departmentToStoreMapperController")
@ViewScoped
public class DepartmentToStoreMapperController implements Serializable {

    //<editor-fold defaultstate="collapsed" desc="Entity">
    @Inject
    HrDepartments departmentsEntity;
    @Inject
    MmsLuWareHouse mmsLuWareHouseEntity;
    @Inject
    MmsStoreToHrDepMapper storeToHrDepMapperEntity;
    @Inject
    ColumnNameResolver columnNameResolver;

//</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="EJB's">
    @EJB
    MmsStoreToDepartmentMapperBeanLocal mmsStoreToDepartmentMapperInterface;
    @EJB
    HrDepartmentsBeanLocal hrDepartmentsInterface;
    @EJB
    MmsLuWarehouseBeanLocal luWarehouseInterface;
//</editor-fold> 

    //<editor-fold defaultstate="collapsed" desc="Fields">
    private TreeNode root;
    private TreeNode root2;
    private TreeNode selectedNode;

    private boolean renderPnlCreateNewPage = false;
    private boolean renderPnlManPage = true;
    private boolean renderpnlToSearchPage;
    private String createOrSearchBundle = "New";
    private String icone = "ui-icon-plus";
    private String saveorUpdateBundle = "Save";
    private int updateStatus = 0;
    private DataModel<MmsStoreToHrDepMapper> mmsStoreToHrDepMappersDataModel;
    private List<MmsLuWareHouse> luWareHouses;
    private List<HrDepartments> allDepartmentsList = new ArrayList<>();
    private static List<HrDepartments> araListe;
    private MmsStoreToHrDepMapper selectedItem;
    private boolean duplicated;
    private List<MmsStoreToHrDepMapper> searchList;
    private List<HrDepartments> mmsHrDepColumnNameList;
    private List<String> mmsDepColumnNameList;
    ColumnNames columnNames = new ColumnNames();
//  List<ColumnNames> ColumnNamesList = new ArrayList<>();
    List<ColumnNameResolver> ColumnNamesList = new ArrayList<>();
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Default Constractor">
    public DepartmentToStoreMapperController() {
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

    @PostConstruct
    public void init() {
        allDepartmentsList = hrDepartmentsInterface.findAll();
        root = new DefaultTreeNode("Root", null);

        recursive(allDepartmentsList, 0, root);
        root2 = getRoot();
        getMmsDepColumnNameList();
//        loadAddressTree();
//        hrEmployees.setEmploymentType("Permanent");
//        hrEmployees.setSex("Male");
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
//</editor-fold>  

    //<editor-fold defaultstate="collapsed" desc="Getters And Setters">
    public HrDepartments getDepartmentsEntity() {
        if (departmentsEntity == null) {
            departmentsEntity = new HrDepartments();
        }
        return departmentsEntity;
    }

    public void setDepartmentsEntity(HrDepartments departmentsEntity) {
        this.departmentsEntity = departmentsEntity;
    }

    public ColumnNames getColumnNames() {
        return columnNames;
    }

    public List<ColumnNameResolver> getColumnNamesList() {
        return ColumnNamesList;
    }

    public void setColumnNamesList(List<ColumnNameResolver> ColumnNamesList) {
        this.ColumnNamesList = ColumnNamesList;
    }

    public void setColumnNames(ColumnNames columnNames) {
        this.columnNames = columnNames;
    }

    public MmsLuWareHouse getMmsLuWareHouseEntity() {
        if (mmsLuWareHouseEntity == null) {
            mmsLuWareHouseEntity = new MmsLuWareHouse();
        }
        return mmsLuWareHouseEntity;
    }

    public ColumnNameResolver getColumnNameResolver() {
        return columnNameResolver;
    }

    public void setColumnNameResolver(ColumnNameResolver columnNameResolver) {
        this.columnNameResolver = columnNameResolver;
    }

    public void setMmsLuWareHouseEntity(MmsLuWareHouse mmsLuWareHouseEntity) {
        this.mmsLuWareHouseEntity = mmsLuWareHouseEntity;
    }

    public MmsStoreToHrDepMapper getStoreToHrDepMapperEntity() {
        if (storeToHrDepMapperEntity == null) {
            storeToHrDepMapperEntity = new MmsStoreToHrDepMapper();
        }
        return storeToHrDepMapperEntity;
    }

    public void setStoreToHrDepMapperEntity(MmsStoreToHrDepMapper storeToHrDepMapperEntity) {
        this.storeToHrDepMapperEntity = storeToHrDepMapperEntity;
    }

    public TreeNode getRoot() {
        return root;
    }

    public void setRoot(TreeNode root) {
        this.root = root;
    }

    public TreeNode getRoot2() {
        return root2;
    }

    public void setRoot2(TreeNode root2) {
        this.root2 = root2;
    }

    public TreeNode getSelectedNode() {
        return selectedNode;
    }

    public void setSelectedNode(TreeNode selectedNode) {
        this.selectedNode = selectedNode;
    }

    public boolean isRenderPnlCreateNewPage() {
        return renderPnlCreateNewPage;
    }

    public void setRenderPnlCreateNewPage(boolean renderPnlCreateNewPage) {
        this.renderPnlCreateNewPage = renderPnlCreateNewPage;
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

    public String getSaveorUpdateBundle() {
        return saveorUpdateBundle;
    }

    public void setSaveorUpdateBundle(String saveorUpdateBundle) {
        this.saveorUpdateBundle = saveorUpdateBundle;
    }

    public int getUpdateStatus() {
        return updateStatus;
    }

    public void setUpdateStatus(int updateStatus) {
        this.updateStatus = updateStatus;
    }

    public DataModel<MmsStoreToHrDepMapper> getMmsStoreToHrDepMappersDataModel() {
        if (mmsStoreToHrDepMappersDataModel == null) {
            mmsStoreToHrDepMappersDataModel = new ListDataModel<>();
        }
        return mmsStoreToHrDepMappersDataModel;
    }

    public void setMmsStoreToHrDepMappersDataModel(DataModel<MmsStoreToHrDepMapper> mmsStoreToHrDepMappersDataModel) {
        this.mmsStoreToHrDepMappersDataModel = mmsStoreToHrDepMappersDataModel;
    }

    public MmsStoreToHrDepMapper getSelectedItem() {
        return selectedItem;
    }

    public void setSelectedItem(MmsStoreToHrDepMapper selectedItem) {
        this.selectedItem = selectedItem;
    }

    public boolean isDuplicated() {
        return duplicated;
    }

    public void setDuplicated(boolean duplicated) {
        this.duplicated = duplicated;
    }

    public List<MmsStoreToHrDepMapper> getSearchList() {
        return searchList;
    }

    public void setSearchList(List<MmsStoreToHrDepMapper> searchList) {
        this.searchList = searchList;
    }

    public List<HrDepartments> getMmsHrDepColumnNameList() {
        if (mmsHrDepColumnNameList == null) {
            mmsHrDepColumnNameList = new ArrayList<>();
//            mmsHrDepColumnNameList = hrDepartmentsInterface.getMmsHrDepColumnNameList();

        }
        return mmsHrDepColumnNameList;
    }

    public void setMmsHrDepColumnNameList(List<HrDepartments> mmsHrDepColumnNameList) {
        this.mmsHrDepColumnNameList = mmsHrDepColumnNameList;
    }

//</editor-fold> 
    //<editor-fold defaultstate="collapsed" desc="Event Handlers,Save,Search Methods">
    /*This method is used to search Mapping Info By Parameter
     */
    public void searchMappingInfoByParameter() {
//        System.out.println("-------- Searching ------- ");
//        if (!mmsLuWareHouseEntity.getName().isEmpty() && departmentsEntity.getDepName().isEmpty()) {
//            searchList = mmsStoreToDepartmentMapperInterface.searchByWarehouseName(mmsLuWareHouseEntity);
//        } else if (mmsLuWareHouseEntity.getName().isEmpty() && !departmentsEntity.getDepName().isEmpty()) {
//            searchList = mmsStoreToDepartmentMapperInterface.searchByDepartmentName(departmentsEntity);
//        } else {
//            System.out.println("-------- Inside If ------- ");
//            searchList = mmsStoreToDepartmentMapperInterface.findAll();
//        }
//        recerateMappingModel();       
        searchList = mmsStoreToDepartmentMapperInterface.getHrDepByParameter(columnNameResolver, storeToHrDepMapperEntity, storeToHrDepMapperEntity.getColumnValue());
        recerateMappingModel();
    }

    public void searchSRInformation() {

        searchList = mmsStoreToDepartmentMapperInterface.getHrDepByParameter(columnNameResolver, storeToHrDepMapperEntity, storeToHrDepMapperEntity.getColumnValue());
        recerateMappingModel();
    }

    /*This method is used to column Name Change Event
     */
    public void columnNameChangeEvent(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            columnNames.setTable_Col_Name(event.getNewValue().toString());
            columnNames.setParsed_Col_name(ColumnParser(columnNames.getTable_Col_Name()));
            storeToHrDepMapperEntity.setColumnName(columnNames.getTable_Col_Name());
            storeToHrDepMapperEntity.setColumnValue(null);
        }

    }

    public String ColumnParser(String col_name) {
        String col = col_name.replace("_", " ");
        System.out.println("col==" + col);
        return col;
    }

    public List<String> getMmsDepColumnNameList() {
        mmsDepColumnNameList = mmsStoreToDepartmentMapperInterface.getMmsHrDepColumnNameList();
        if (mmsDepColumnNameList == null) {
            mmsDepColumnNameList = new ArrayList<>();
        }
        System.out.println("=======mmsDepColumnNameList==" + mmsDepColumnNameList);
        if (mmsDepColumnNameList.size() > 0) {
            ColumnNamesList = new ArrayList<>();
            for (int i = 0; i < mmsDepColumnNameList.size(); i++) {
                ColumnNameResolver obj = new ColumnNameResolver();
                obj.setCol_Name_FromTable((mmsDepColumnNameList.get(i).toLowerCase()));
                obj.setParsed_Col_Name(ColumnParser((mmsDepColumnNameList.get(i).toLowerCase())));
                ColumnNamesList.add(obj);
            }

        }
        return mmsDepColumnNameList;
    }

    public void setMmsDepColumnNameList(List<String> mmsDepColumnNameList) {
        this.mmsDepColumnNameList = mmsDepColumnNameList;
    }

    /*This method is used to recerate Mapping Model
     */
    private void recerateMappingModel() {
        mmsStoreToHrDepMappersDataModel = null;
        mmsStoreToHrDepMappersDataModel = new ListDataModel(new ArrayList<>(searchList));
    }
    /*This method is used to On Node Select
     */

    public void onNodeSelect(NodeSelectEvent event) {
        selectedNode = event.getTreeNode();
        int key = Integer.valueOf((selectedNode.getData().toString()).split("--")[0]);

        departmentsEntity.setDepId(key);
        departmentsEntity = hrDepartmentsInterface.findByDepartmentId(departmentsEntity);

        storeToHrDepMapperEntity.setDepartmentId(departmentsEntity);
        checkDuplication();
        if (duplicated) {
            JsfUtil.addFatalMessage("Duplicate Inforamtion is not allowed");
        }
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("PF('dlgDpt').hide()");

    }
    /*This method is used to get List Of Warehouses Lookup
     */

    public List<MmsLuWareHouse> getListOfWarehouses() {
        luWareHouses = luWarehouseInterface.findAll();
        return luWareHouses;
    }
    /*This method is used to handle Select Warehouses
     */

    public void handleSelectWarehouses(ValueChangeEvent event) {
        if (null != event.getNewValue()) {
            mmsLuWareHouseEntity = (MmsLuWareHouse) event.getNewValue();
            storeToHrDepMapperEntity.setWarehouseId(mmsLuWareHouseEntity);

        }
    }
    /*This method is used to On Row Edit
     */

    public void onRowEdit(SelectEvent event) {
        storeToHrDepMapperEntity = (MmsStoreToHrDepMapper) event.getObject();
        renderPnlCreateNewPage = true;

        renderPnlManPage = false;
        renderpnlToSearchPage = true;
        saveorUpdateBundle = "Update";
        createOrSearchBundle = "New";
        setIcone("ui-icon-plus");

        updateStatus = 1;

        departmentsEntity = storeToHrDepMapperEntity.getDepartmentId();
        mmsLuWareHouseEntity = storeToHrDepMapperEntity.getWarehouseId();

    }
    /*This method is used to Save Mapping Information
     */

    public void SaveMappingInformation() {
        if (duplicated == false) {
            if (updateStatus == 0) {
                mmsStoreToDepartmentMapperInterface.create(storeToHrDepMapperEntity);
                JsfUtil.addSuccessMessage("Mapping Information Saved");
                clearPage();
            } else {
                mmsStoreToDepartmentMapperInterface.edit(storeToHrDepMapperEntity);
                JsfUtil.addSuccessMessage("Mapping Information Updated");
                clearPage();
            }
        } else {
            JsfUtil.addFatalMessage("The data already exists");
        }
    }
    /*This method is used to check Duplication
     */

    public void checkDuplication() {
        duplicated = mmsStoreToDepartmentMapperInterface.checkForDuplication(storeToHrDepMapperEntity);
    }
    /*This method is used to clearPage
     */

    public String clearPage() {
        departmentsEntity = null;
        mmsLuWareHouseEntity = null;
        storeToHrDepMapperEntity = null;
        saveorUpdateBundle = "Save";
        updateStatus = 0;

        return null;
    }
    /*This method is used to go Back Search Button Action
     */

    public void goBackSearchButtonAction() {
        renderpnlToSearchPage = false;
        renderPnlCreateNewPage = false;
        renderPnlManPage = true;
    }
    /*This method is used to create New Info
     */

    public void createNewInfo() {
        //
        clearPage();
        saveorUpdateBundle = "Save";

        if (createOrSearchBundle.equals("New")) {
            renderPnlCreateNewPage = true;
            renderPnlManPage = false;
            createOrSearchBundle = "Search";
            setIcone("ui-icon-search");

        } else if (createOrSearchBundle.equals("Search")) {
            renderPnlCreateNewPage = false;
            renderPnlManPage = true;
            createOrSearchBundle = "New";
            setIcone("ui-icon-plus");
        }
    }
    //</editor-fold>

}
