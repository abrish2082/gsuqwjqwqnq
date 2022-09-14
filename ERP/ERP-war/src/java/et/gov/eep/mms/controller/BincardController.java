/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.mms.controller;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import et.gov.eep.commonApplications.businessLogic.WfMmsProcessedBeanLocal;
import et.gov.eep.commonApplications.entity.WfMmsProcessed;
import et.gov.eep.commonApplications.entity.ColumnNameResolver;
import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.mms.businessLogic.MmsBinCardBeanLocal;
import et.gov.eep.mms.businessLogic.MmsItemRegisrtationBeanLocal;
import et.gov.eep.mms.businessLogic.MmsStoreInformationBeanLocal;
import et.gov.eep.mms.entity.MmsBinCard;
import et.gov.eep.mms.entity.MmsItemRegistration;
import et.gov.eep.mms.entity.MmsStoreInformation;
import javax.annotation.PostConstruct;

/**
 *
 * @author minab
 */
@Named(value = "bincardController")
@ViewScoped
public class BincardController implements Serializable {

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

    //<editor-fold defaultstate="collapsed" desc="Entities">
    @Inject
    private MmsBinCard binCardEntity;
    @Inject
    private MmsStoreInformation storeEntity;
    @Inject
    private MmsStoreInformation storeInformation;
    @Inject
    private MmsItemRegistration itemRegistrationEntity;
    @Inject
    WfMmsProcessed wfMmsProcessed;
    @Inject
    ColumnNameResolver columnNameResolver;
     //</editor-fold> 

    //<editor-fold defaultstate="collapsed" desc="EJB">
    @EJB
    WfMmsProcessedBeanLocal wfMmsProcessedBeanLocal;
    @EJB
    private MmsBinCardBeanLocal binCardInterface;
    @EJB
    private MmsStoreInformationBeanLocal storeInfoInterface;
    @EJB
    MmsItemRegisrtationBeanLocal ItemRegisrtationInterface;
    //</editor-fold> 

    //<editor-fold defaultstate="collapsed" desc="Fields">
    private DataModel<MmsBinCard> binCardDataModel;
    private List<MmsBinCard> filteredItems;
    DataModel<WfMmsProcessed> WorkflowDataModel = new ListDataModel<>();
    List<WfMmsProcessed> WfList;
    private List<MmsItemRegistration> unregisteredItemCodes;
    private BigDecimal initialQuantity;
    List<MmsStoreInformation> StoreList;
    List<MmsBinCard> allBincarrdInfoList;
    private List<String> mmsBinColumnNameList;
    List<ColumnNameResolver> ColumnNamesList = new ArrayList<>();
      //</editor-fold> 

    //<editor-fold defaultstate="collapsed" desc="postconstraction">
    public BincardController() {
    }

    @PostConstruct
    public void init() {
        getMmsBinColumnNameList();
    }
     //</editor-fold> 

    //<editor-fold defaultstate="collapsed" desc="gettre and setter">
    public WfMmsProcessed getWfMmsProcessed() {
        return wfMmsProcessed;
    }

    public void setWfMmsProcessed(WfMmsProcessed wfMmsProcessed) {
        this.wfMmsProcessed = wfMmsProcessed;
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

    public ColumnNames getColumnNames() {
        return columnNames;
    }

    public void setColumnNames(ColumnNames columnNames) {
        this.columnNames = columnNames;
    }

    public ColumnNameResolver getColumnNameResolver() {
        return columnNameResolver;
    }

    public void setColumnNameResolver(ColumnNameResolver columnNameResolver) {
        this.columnNameResolver = columnNameResolver;
    }

    public List<ColumnNameResolver> getColumnNamesList() {
        return ColumnNamesList;
    }

    public void setColumnNamesList(List<ColumnNameResolver> ColumnNamesList) {
        this.ColumnNamesList = ColumnNamesList;
    }

   

    public MmsBinCard getBinCardEntity() {
        if (binCardEntity == null) {
            binCardEntity = new MmsBinCard();
        }
        return binCardEntity;
    }

    public void setBinCardEntity(MmsBinCard binCardEntity) {
        this.binCardEntity = binCardEntity;
    }

    public MmsStoreInformation getStoreInformation() {
        if (storeInformation == null) {
            storeInformation = new MmsStoreInformation();
        }
        return storeInformation;
    }

    public void setStoreInformation(MmsStoreInformation storeInformation) {
        this.storeInformation = storeInformation;
    }

    public MmsItemRegistration getItemRegistrationEntity() {
        if (itemRegistrationEntity == null) {
            itemRegistrationEntity = new MmsItemRegistration();
        }
        return itemRegistrationEntity;
    }

    public void setItemRegistrationEntity(MmsItemRegistration itemRegistrationEntity) {
        this.itemRegistrationEntity = itemRegistrationEntity;
    }

    public DataModel<MmsBinCard> getBinCardDataModel() {
        if (binCardDataModel == null) {
            binCardDataModel = new ListDataModel<>();
        }
        return binCardDataModel;
    }

    public void setBinCardDataModel(DataModel<MmsBinCard> binCardDataModel) {
        this.binCardDataModel = binCardDataModel;
    }

    public List<MmsItemRegistration> getUnregisteredItemCodes() {
        return unregisteredItemCodes;
    }

    public void setUnregisteredItemCodes(List<MmsItemRegistration> unregisteredItemCodes) {
        this.unregisteredItemCodes = unregisteredItemCodes;
    }

    public BigDecimal getInitialQuantity() {
        return initialQuantity;
    }

    public void setInitialQuantity(BigDecimal initialQuantity) {
        this.initialQuantity = initialQuantity;
    }

    public List<MmsStoreInformation> getStoreList() {
        StoreList = storeInfoInterface.findAllStoreInfo();
        return StoreList;
    }

    public List<MmsBinCard> getFilteredItems() {
        return filteredItems;
    }

    public void setFilteredItems(List<MmsBinCard> filteredItems) {
        this.filteredItems = filteredItems;
    }

    public MmsStoreInformation getStoreEntity() {
        if (storeEntity == null) {
            storeEntity = new MmsStoreInformation();
        }
        return storeEntity;
    }

    public void setStoreEntity(MmsStoreInformation storeEntity) {
        this.storeEntity = storeEntity;
    }
 //</editor-fold> 

    //<editor-fold defaultstate="collapsed" desc="Save,search,handlre">
    /*This method is used to Search Bin Card Information
     */
    public void searchBinCardInformation() {

        allBincarrdInfoList = binCardInterface.searchByCol_NameAndCol_Value(columnNameResolver, binCardEntity, binCardEntity.getColumnValue());
        recerateBincardSearchModel();

//        if (storeEntity.getStoreId() != null && itemRegistrationEntity.getMatCode().isEmpty() && itemRegistrationEntity.getMatName().isEmpty()) {
//            System.out.println("========store id====" + storeEntity.getStoreId());
//            allBincarrdInfoList = binCardInterface.searchBinCardInfoByStore(storeEntity);
//            System.out.println("=====list size=====" + allBincarrdInfoList.size());
//            System.out.println("=====list size=====" + allBincarrdInfoList);
//            recerateBincardSearchModel();
//        } else if (storeEntity.getStoreId() != null && itemRegistrationEntity.getMatName().isEmpty() && !itemRegistrationEntity.getMatCode().isEmpty()) {
//            binCardEntity.setStoreId(storeEntity);
//
//            allBincarrdInfoList = binCardInterface.searchByStoreAndItemCode(itemRegistrationEntity, binCardEntity);
//            System.out.println("==================allbincard====" + allBincarrdInfoList);
//            recerateBincardSearchModel();
//        } else if (storeEntity.getStoreId() != null && itemRegistrationEntity.getMatCode().isEmpty() && !itemRegistrationEntity.getMatName().isEmpty()) {
//            binCardEntity.setStoreId(storeEntity);
//            allBincarrdInfoList = binCardInterface.searchByStoreAndItemName(itemRegistrationEntity, binCardEntity);
//
//            recerateBincardSearchModel();
//        } else if (storeEntity.getStoreId() != null && !itemRegistrationEntity.getMatCode().isEmpty() && !itemRegistrationEntity.getMatName().isEmpty()) {
//            binCardEntity.setStoreId(storeEntity);
//            allBincarrdInfoList = binCardInterface.searchByAllParameters(itemRegistrationEntity, binCardEntity);
//            System.out.println("==================allbincard====" + allBincarrdInfoList);
//            recerateBincardSearchModel();
//        }
    }

    private void recerateBincardSearchModel() {

        binCardDataModel = null;
        binCardDataModel = new ListDataModel(new ArrayList<>(allBincarrdInfoList));
    }
    /*This method is used to column Name Change Event
     */
    ColumnNames columnNames = new ColumnNames();

    public String ColumnParser(String col_name) {
        String col = col_name.replace("_", " ");
        System.out.println("col==" + col);
        return col;
    }

    public void columnNameChangeEvent(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            System.out.println("event.getNewValue().toString()==" + event.getNewValue().toString());
            columnNameResolver.setCol_Name_FromTable(event.getNewValue().toString());
            columnNameResolver.setParsed_Col_Name(ColumnParser(columnNameResolver.getCol_Name_FromTable()));
            binCardEntity.setColumnName(columnNameResolver.getCol_Name_FromTable());
            binCardEntity.setColumnValue(null);
        }

    }

    public List<String> getMmsBinColumnNameList() {
        mmsBinColumnNameList = binCardInterface.getMmsBinColumnNameList();
        if (mmsBinColumnNameList == null) {
            mmsBinColumnNameList = new ArrayList<>();
        }
        System.out.println("=======getMmsBinColumnNameList==" + mmsBinColumnNameList);
        if (mmsBinColumnNameList.size() > 0) {
            ColumnNamesList = new ArrayList<>();
            for (int i = 0; i < mmsBinColumnNameList.size(); i++) {               
                ColumnNameResolver obj = new ColumnNameResolver();
                obj.setCol_Name_FromTable((mmsBinColumnNameList.get(i).toLowerCase()));
                obj.setParsed_Col_Name(ColumnParser((mmsBinColumnNameList.get(i).toLowerCase())));
                ColumnNamesList.add(obj);
            }

        }
        return mmsBinColumnNameList;
    }

    public void setMmsBinColumnNameList(List<String> mmsBinColumnNameList) {
        this.mmsBinColumnNameList = mmsBinColumnNameList;
    }

    /*This method is used to Handle Select Store Name
     */
    public void handleSelectStoreName(ValueChangeEvent event) {
        if (null != event.getNewValue()) {
            Integer Id = Integer.parseInt(event.getNewValue().toString());
            storeEntity.setStoreId(Id);
        }

    }
    /*This method is used to Handle Select Store Change
     */

    public void handleSelectStoreChange(ValueChangeEvent event) {
        if (null != event.getNewValue()) {

            storeInformation = (MmsStoreInformation) event.getNewValue();
            unregisteredItemCodes = ItemRegisrtationInterface.findItemsNotRegisteredOnBincard(storeInformation);
            System.out.println("==========material list size=======" + unregisteredItemCodes.size());
        }
    }
    /*This method is used to Handle Select Item Code
     */

    public void handleSelectItemCode(ValueChangeEvent event) {
        if (null != event.getNewValue()) {
            itemRegistrationEntity = (MmsItemRegistration) event.getNewValue();
        }
    }
    /*This method is used to Save Unregisterd Materials
     */

    public void saveUnregisteredMaterials() {
        binCardEntity.setStoreId(storeInformation);
        binCardEntity.setMaterialId(itemRegistrationEntity);
        binCardEntity.setInitalQuantity(initialQuantity);
        binCardEntity.setCurrentQuantity(initialQuantity);
        binCardInterface.create(binCardEntity);
        JsfUtil.addSuccessMessage("Item Registered Successfully!");
        clearAdd();

    }
    /*This method is used to Clear when Add meathed Executed
     */

    public void clearAdd() {
        binCardEntity = null;
        storeInformation = null;
        storeEntity = null;
        itemRegistrationEntity = null;
        initialQuantity = null;

    }
  //</editor-fold> 
}
