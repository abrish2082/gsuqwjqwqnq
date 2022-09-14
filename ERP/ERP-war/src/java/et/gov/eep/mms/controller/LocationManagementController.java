/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.mms.controller;

import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.mms.businessLogic.MmsGRNBEanLocal;
import et.gov.eep.mms.businessLogic.MmsGrnDetailBeanLocal;
import et.gov.eep.mms.businessLogic.MmsItemRegisrtationBeanLocal;
import et.gov.eep.mms.businessLogic.MmsLocationInfoBeanLocal;
import et.gov.eep.mms.businessLogic.MmsManageLocationBeanLocal;
import et.gov.eep.mms.businessLogic.MmsShelfInfoBeanLocal;
import et.gov.eep.mms.businessLogic.MmsSivBeanLocal;
import et.gov.eep.mms.businessLogic.MmsSivDtlBeanLocal;
import et.gov.eep.mms.businessLogic.MmsStoreInformationBeanLocal;
import et.gov.eep.mms.entity.MmsGrn;
import et.gov.eep.mms.entity.MmsGrnDetail;
import et.gov.eep.mms.entity.MmsItemRegistration;
import et.gov.eep.mms.entity.MmsLocationInfo;
import et.gov.eep.mms.entity.MmsLocationInfoDtl;
import et.gov.eep.mms.entity.MmsManageLocation;
import et.gov.eep.mms.entity.MmsManageLocationDtl;
import et.gov.eep.mms.entity.MmsShelfInfo;

import et.gov.eep.mms.entity.MmsSiv;
import et.gov.eep.mms.entity.MmsSivDetail;
import et.gov.eep.mms.entity.MmsStoreInformation;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Sadik
 */
@Named(value = "locationManagementController")
@ViewScoped
public class LocationManagementController implements Serializable {

    /**
     * Creates a new instance of LocationManagementController
     */
    @EJB
    private MmsGRNBEanLocal mmsGrnBeanLocal;
    @EJB
    private MmsGrnDetailBeanLocal mmsGrnDetailInterface;
    @EJB
    private MmsItemRegisrtationBeanLocal itemRegistrationInterface;
    @EJB
    private MmsStoreInformationBeanLocal storeInfoInterface;
    @EJB
    MmsShelfInfoBeanLocal mmsShelfInfoInterface;
   
    @EJB
    MmsLocationInfoBeanLocal mmsCellInfoInterface;
    @EJB
    MmsManageLocationBeanLocal mmsManageLocationInterface;
    @EJB
    MmsSivBeanLocal mmsSivInterface;
    @EJB
    private MmsSivDtlBeanLocal mmsSivDetailInterface;
    @Inject
    private MmsManageLocation mmsManageLocationEntity;
    @Inject
    private MmsManageLocationDtl mmsManageLocationDtlEntity;
    @Inject
    private MmsGrn grnEntity;
    @Inject
    private MmsItemRegistration itemRegEntity;
    @Inject
    private MmsGrnDetail grnDetailEntity;
    @Inject
    private MmsStoreInformation storeInfoEntity;
    @Inject
    private MmsShelfInfo mmsShelfInfoEntity;
   
    @Inject 
    private MmsLocationInfo mmsCellInfoEntity;
    @Inject
    private MmsLocationInfoDtl mmsCellInfoDtlEntity;
    @Inject
    private MmsSiv mmsSivEntity;
    @Inject
    private MmsSivDetail mmsSivDtlEntity;
    private DataModel<MmsManageLocationDtl> manageLocationDetailDataModel;
    private DataModel<MmsManageLocation> mmsLocationSearchInfoDataModel;

    /**
     *
     */
    public LocationManagementController() {
    }
    private String CheckWarehouse="0";
    boolean rndPropertyForSIV = true;
    boolean rendPropertyForGRN = false;
    boolean rendPropertyForSRN =false;
    private boolean renderWareHouseOneMenu=false;
    private String saveorUpdateBundle = "Create";
   
    private boolean disableBtnCreate;
    private String createOrSearchBundle = "New";
    private boolean renderPnlCreateLocation = false;
    private boolean renderPnlManPage = true;
    private String icone = "ui-icon-plus";
    private String activeIndex;

    /**
     *
     * @return
     */
    public boolean getRndPropertyForSIV() {
        return rndPropertyForSIV;
    }

    /**
     *
     * @param rndPropertyForSIV
     */
    public void setRndPropertyForSIV(boolean rndPropertyForSIV) {
        this.rndPropertyForSIV = rndPropertyForSIV;
    }

    /**
     *
     * @return
     */
    public boolean getRendPropertyForGRN() {
        return rendPropertyForGRN;
    }

    /**
     *
     * @param rendPropertyForGRN
     */
    public void setRendPropertyForGRN(boolean rendPropertyForGRN) {
        this.rendPropertyForGRN = rendPropertyForGRN;
    }

    /**
     *
     * @return
     */
    public boolean getRendPropertyForSRN() {
        return rendPropertyForSRN;
    }

    /**
     *
     * @param rendPropertyForSRN
     */
    public void setRendPropertyForSRN(boolean rendPropertyForSRN) {
        this.rendPropertyForSRN = rendPropertyForSRN;
    }
    
    /**
     *
     * @return
     */
    public String getCheckWarehouse() {
        return CheckWarehouse;
    }

    /**
     *
     * @param CheckWarehouse
     */
    public void setCheckWarehouse(String CheckWarehouse) {
        this.CheckWarehouse = CheckWarehouse;
    }
    
    /**
     *
     * @return
     */
    public boolean isRenderWareHouseOneMenu() {
        return renderWareHouseOneMenu;
    }

    /**
     *
     * @param renderWareHouseOneMenu
     */
    public void setRenderWareHouseOneMenu(boolean renderWareHouseOneMenu) {
        this.renderWareHouseOneMenu = renderWareHouseOneMenu;
    }
  
    /**
     *
     * @return
     */
    public MmsItemRegistration getItemRegEntity() {
      if(itemRegEntity==null){
          itemRegEntity=new MmsItemRegistration();
      }
        return itemRegEntity;
    }

    /**
     *
     * @param itemRegEntity
     */
    public void setItemRegEntity(MmsItemRegistration itemRegEntity) {
        this.itemRegEntity = itemRegEntity;
    }
    
    /**
     *
     * @return
     */
    public MmsGrn getGrnEntity() {
        if(grnEntity==null){
            grnEntity=new MmsGrn();
        }
        return grnEntity;
    }

    /**
     *
     * @param grnEntity
     */
    public void setGrnEntity(MmsGrn grnEntity) {
        this.grnEntity = grnEntity;
    }
    
    /**
     *
     * @return
     */
    public MmsGrnDetail getGrnDetailEntity() {
        if(grnDetailEntity==null){
            grnDetailEntity=new MmsGrnDetail();
        }
        return grnDetailEntity;
    }

    /**
     *
     * @param grnDetailEntity
     */
    public void setGrnDetailEntity(MmsGrnDetail grnDetailEntity) {
        this.grnDetailEntity = grnDetailEntity;
    }
    
    /**
     *
     * @return
     */
    public MmsSiv getMmsSivEntity() {
        if(mmsSivEntity==null){
            mmsSivEntity=new MmsSiv();
        }
        return mmsSivEntity;
    }

    /**
     *
     * @param mmsSivEntity
     */
    public void setMmsSivEntity(MmsSiv mmsSivEntity) {
        this.mmsSivEntity = mmsSivEntity;
    }

    /**
     *
     * @return
     */
    public MmsSivDetail getMmsSivDtlEntity() {
        if(mmsSivDtlEntity==null){
            mmsSivDtlEntity=new MmsSivDetail();
        }
        return mmsSivDtlEntity;
    }

    /**
     *
     * @param mmsSivDtlEntity
     */
    public void setMmsSivDtlEntity(MmsSivDetail mmsSivDtlEntity) {
        this.mmsSivDtlEntity = mmsSivDtlEntity;
    }
    
    /**
     *
     * @return
     */
    public MmsStoreInformation getStoreInfoEntity() {
        if(storeInfoEntity==null){
            storeInfoEntity=new MmsStoreInformation();
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
    
    
    List<MmsSiv> ListNewReqMat = null;

    /**
     *
     * @return
     */
    public SelectItem[] getApproveSIVList() {
        int newReqMatSize = 0;
        ListNewReqMat = mmsSivInterface.approvedSivListForLocationManagement();
        newReqMatSize = ListNewReqMat.size();
        return JsfUtil.getSelectItemsListBox(ListNewReqMat, true);
    }
    
    List<MmsGrn> mmsGrns;
   

    /**
     *
     * @return
     */
    public SelectItem[] getApproveGRNList() {
        int newgrnlist = 0;
        mmsGrns = mmsGrnBeanLocal.approvedGrnList(storeInfoEntity);
        return JsfUtil.getSelectItemsListBox(mmsGrns, true);
    }
 private List<MmsGrnDetail> GrnMaterialsList;
    /**
     *
     * @return
     */
    public List<MmsGrnDetail>  getGRNMaterialList() {

      GrnMaterialsList=grnEntity.getMmsGrnDetailCollection();
        return GrnMaterialsList;
    }
     
    /**
     *
     * @return
     */
    public SelectItem[] getSIVMaterialList() {

      
        return JsfUtil.getSelectItems(mmsSivEntity.getMmsSivDetailList(), true);
    }
     
    /**
     *
     * @return
     */
    public SelectItem[] getStoreName() {
        return JsfUtil.getSelectItems(storeInfoInterface.findAllStoreInfo(), true);
    }

    /**
     *
     * @param event
     */
    public void handleSelectGRNNo(ValueChangeEvent event) {
        if (null != event.getNewValue()) {
            String GrnNum = event.getNewValue().toString();
            manageLocationDetailDataModel=null;
            grnEntity.setGrnNo(GrnNum);
            grnEntity=mmsGrnBeanLocal.getbyGrnNo(grnEntity);

           grnEntity.setMmsGrnDetailCollection(grnEntity.getMmsGrnDetailCollection());

            rndPropertyForSIV = false;
            setRendPropertyForGRN(true);
            rendPropertyForSRN = false;
        }
        
        
    }

    /**
     *
     * @param event
     */
    public void handleSelectSIVNo(ValueChangeEvent event) {
        if (null != event.getNewValue()) {
            String SivNum = event.getNewValue().toString();
            manageLocationDetailDataModel=null;
            mmsSivEntity.setSivNo(SivNum);
            mmsSivEntity=mmsSivInterface.getSivInfoBySivNo(mmsSivEntity);
            
           mmsSivEntity.setMmsSivDetailList(mmsSivEntity.getMmsSivDetailList());
           

            rndPropertyForSIV = true;
            setRendPropertyForGRN(false);
            //rendPropertyForGRN = true;
            rendPropertyForSRN = false;
        }
        
        
    }

    
   
    
    /**
     *
     * @return
     */
   
       
    /**
     *
     * @return
     */
    
     
     
     //Search list of cells based on storeId and ShelfId
      List<MmsLocationInfoDtl> cellCodes=new ArrayList<>();
      

      
    /**
     *
     * @param event
     */
          
      public void handleSelectShelfCode(ValueChangeEvent event) {
        if (null != event.getNewValue()) {
          
           
            String[] shelfCode = event.getNewValue().toString().split("/");
            int shelfId =Integer.parseInt(String.valueOf(shelfCode[0]));
            
                mmsShelfInfoEntity.setShelfId(shelfId);
                 mmsCellInfoEntity.setShelfId(mmsShelfInfoEntity);
                 String shelfCodes=shelfCode[1];
                 mmsCellInfoEntity=mmsCellInfoInterface.getMmsCellInformation(mmsCellInfoEntity);
//                 mmsManageLocationDtlEntity.setShelfCode(shelfCodes);
        }

    }
      
    /**
     *
     * @param event
     */
    public void handleSelectCellCode(ValueChangeEvent event){
          if (null != event.getNewValue()) {
            String cell = event.getNewValue().toString();
           // mmsManageLocationDtlEntity.setCellCode(cell);
//            
        }
      }
      
    /**
     *
     * @return
     */
    public List<MmsShelfInfo> getWarehouseLists() {
        mmsShelfInfoEntity.setStoreId(storeInfoEntity);
       ArrayList<MmsShelfInfo> warehouseNames= mmsShelfInfoInterface.searchShelfInformation(mmsShelfInfoEntity);
      
        return warehouseNames;
    }
    
    /**
     *
     * @param ev
     */
    public  void enabledisableWarehouse(ValueChangeEvent ev){
       if (null != ev.getNewValue()) {
            if (ev.getNewValue().toString().equalsIgnoreCase("0")) {

                
                setRenderWareHouseOneMenu(false);
                
                
                
            } else if (ev.getNewValue().toString().equalsIgnoreCase("1")) {
                setRenderWareHouseOneMenu(true);
                
            
              
            }

        }
   }
    
    List<MmsGrn> papmsGrnList;
    String materialName = "";
    int remainingQuantiy;

    /**
     *
     * @param event
     */
    public void MaterialCodeListGrn(ValueChangeEvent event) {
        if (null != event.getNewValue()) {
            String code = event.getNewValue().toString();
           
            itemRegEntity.setMatCode(code);
         itemRegEntity=itemRegistrationInterface.getMmsItemInfoByCode(itemRegEntity);
         materialName = itemRegEntity.getMatName();
         MmsManageLocation locationMgmt=new MmsManageLocation();
         locationMgmt=mmsManageLocationInterface.getManageLocationInfo(itemRegEntity);
         remainingQuantiy=locationMgmt.getRemainingQuantity();
            mmsManageLocationEntity.setRemainingQuantity(remainingQuantiy);
           
            //grnDetailEntity.setMaterialCode(code);
           // grnDetailEntity=mmsGrnDetailInterface.getGrnDetailInfosByMatcode(grnDetailEntity);

            papmsGrnList = new ArrayList<>();
            papmsGrnList = (List<MmsGrn>)mmsGrnBeanLocal.getGrnInfoByGrnNo(code);
                  

            

            
        }
    }
    int totalGrnQuantity;
    int currentallocatedQuantity;

    /**
     *
     * @param event
     */
    public void handleQuantityMinus(ValueChangeEvent event) {
        if (null != event.getNewValue()) {
            String srNum = event.getNewValue().toString();
            int q=Integer.valueOf(srNum);
            totalGrnQuantity=grnDetailEntity.getReceivedQuantity().intValue();
            currentallocatedQuantity=mmsManageLocationDtlEntity.getLocationQuantity();
            remainingQuantiy=totalGrnQuantity-currentallocatedQuantity;
            mmsManageLocationEntity.setRemainingQuantity(remainingQuantiy);
           


        }

    }
    
    /**
     *
     * @param event
     */
    public void MaterialCodeListSIV(ValueChangeEvent event) {
        if (null != event.getNewValue()) {
            String code = event.getNewValue().toString();
           
            itemRegEntity.setMatCode(code);
         itemRegEntity=itemRegistrationInterface.getMmsItemInfoByCode(itemRegEntity);
          materialName = itemRegEntity.getMatName();
           // mmsSivDtlEntity.setItemCode(code);
            mmsSivDtlEntity=mmsSivDetailInterface.getSivDetailInfoByMatCode(mmsSivDtlEntity);
           
           
        }
    }

    /**
     *
     * @return
     */
    public MmsShelfInfo getMmsShelfInfoEntity() {
        if(mmsShelfInfoEntity==null){
            mmsShelfInfoEntity=new MmsShelfInfo();
        }
        return mmsShelfInfoEntity;
        
    }

    /**
     *
     * @param mmsShelfInfoEntity
     */
    public void setMmsShelfInfoEntity(MmsShelfInfo mmsShelfInfoEntity) {
        this.mmsShelfInfoEntity = mmsShelfInfoEntity;
    }

    /**
     *
     * @return
     */
   

    /**
     *
     * @return
     */
    public MmsLocationInfo getMmsCellInfoEntity() {
        if(mmsCellInfoEntity==null){
            mmsCellInfoEntity=new MmsLocationInfo();
        }
        return mmsCellInfoEntity;
    }

    /**
     *
     * @param mmsCellInfoEntity
     */
    public void setMmsCellInfoEntity(MmsLocationInfo mmsCellInfoEntity) {
        this.mmsCellInfoEntity = mmsCellInfoEntity;
    }

    /**
     *
     * @return
     */
    public MmsLocationInfoDtl getMmsCellInfoDtlEntity() {
        if(mmsCellInfoDtlEntity==null){
            mmsCellInfoDtlEntity=new MmsLocationInfoDtl();
        }
        return mmsCellInfoDtlEntity;
    }

    /**
     *
     * @param mmsCellInfoDtlEntity
     */
    public void setMmsCellInfoDtlEntity(MmsLocationInfoDtl mmsCellInfoDtlEntity) {
        this.mmsCellInfoDtlEntity = mmsCellInfoDtlEntity;
    }

    /**
     *
     * @return
     */
    public MmsManageLocation getMmsManageLocationEntity() {
        if(mmsManageLocationEntity==null){
            mmsManageLocationEntity=new MmsManageLocation();
        }
        return mmsManageLocationEntity;
    }

    /**
     *
     * @param mmsManageLocationEntity
     */
    public void setMmsManageLocationEntity(MmsManageLocation mmsManageLocationEntity) {
        this.mmsManageLocationEntity = mmsManageLocationEntity;
    }

    /**
     *
     * @return
     */
    public MmsManageLocationDtl getMmsManageLocationDtlEntity() {
        if(mmsManageLocationDtlEntity==null){
            mmsManageLocationDtlEntity=new MmsManageLocationDtl();
        }
        return mmsManageLocationDtlEntity;
    }

    /**
     *
     * @param mmsManageLocationDtlEntity
     */
    public void setMmsManageLocationDtlEntity(MmsManageLocationDtl mmsManageLocationDtlEntity) {
        this.mmsManageLocationDtlEntity = mmsManageLocationDtlEntity;
    }

    /**
     *
     */
    public void addGrnDetaile() {
        mmsManageLocationEntity.addGrnDetail(mmsManageLocationDtlEntity);
        
        recreateModelDetail();
        clearPage();

    }

    private void recreateModelDetail() {
        manageLocationDetailDataModel=null;
        manageLocationDetailDataModel=new ListDataModel(new ArrayList<>(mmsManageLocationEntity.getMmsManageLocationDtlList()));
        
    }

    private void clearPage() {
      storeInfoEntity=null;
               
      // mmsShelfInfoEntity=null;
      
       mmsCellInfoDtlEntity=null;
       mmsManageLocationDtlEntity = null;
       
        
    }

    /**
     *
     * @return
     */
    public DataModel<MmsManageLocationDtl> getManageLocationDetailDataModel() {
        if(manageLocationDetailDataModel==null){
            manageLocationDetailDataModel=new ListDataModel<>();
        }
        return manageLocationDetailDataModel;
    }

    /**
     *
     * @param manageLocationDetailDataModel
     */
    public void setManageLocationDetailDataModel(DataModel<MmsManageLocationDtl> manageLocationDetailDataModel) {
        this.manageLocationDetailDataModel = manageLocationDetailDataModel;
    }
    
    /**
     *
     * @return
     */
    public String save_GrnInfo() {

        if ((!(getManageLocationDetailDataModel().getRowCount() > 0))) {
            JsfUtil.addErrorMessage("Data Table Shoud be filled");
        } else {
            try {
                mmsManageLocationEntity.setMaterialName(itemRegEntity.getMatName());
                mmsManageLocationEntity.setGrnId(grnEntity);
                mmsManageLocationEntity.setStoreId(storeInfoEntity);
             
                mmsManageLocationInterface.create(mmsManageLocationEntity);
                

                JsfUtil.addSuccessMessage("Location Information Data Created");

            } catch (Exception e) {
                JsfUtil.addErrorMessage("Something Occured on Data Created");

                return clearPageSave();

            }
        }
        return null;
    }
    private String clearPageSave() {
     
        mmsManageLocationEntity=null;
        mmsManageLocationDtlEntity=null;
        setStoreInfoEntity(null);
        mmsShelfInfoEntity=null;
     
       mmsCellInfoEntity=null;
       mmsCellInfoDtlEntity=null;
        return null;
    }

     public void createNewLocationInfo() {

        clearPage();
        saveorUpdateBundle = "Create";
        disableBtnCreate = false;
        
        if (createOrSearchBundle.equals("New")) {
            renderPnlCreateLocation = true;
            renderPnlManPage = false;
            createOrSearchBundle = "Search";
            setCreateOrSearchBundle(createOrSearchBundle);         
            setIcone("ui-icon-search");

        } else if (createOrSearchBundle.equals("Search")) {
            renderPnlCreateLocation = false;
            renderPnlManPage = true;
            createOrSearchBundle = "New";
            setCreateOrSearchBundle(createOrSearchBundle);          
            setIcone("ui-icon-plus");
        }

    }

    public String getSaveorUpdateBundle() {
        return saveorUpdateBundle;
    }

    public void setSaveorUpdateBundle(String saveorUpdateBundle) {
        this.saveorUpdateBundle = saveorUpdateBundle;
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

    public boolean isRenderPnlCreateLocation() {
        return renderPnlCreateLocation;
    }

    public void setRenderPnlCreateLocation(boolean renderPnlCreateLocation) {
        this.renderPnlCreateLocation = renderPnlCreateLocation;
    }

    public boolean isRenderPnlManPage() {
        return renderPnlManPage;
    }

    public void setRenderPnlManPage(boolean renderPnlManPage) {
        this.renderPnlManPage = renderPnlManPage;
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

    public DataModel<MmsManageLocation> getMmsLocationSearchInfoDataModel() {
        if(mmsLocationSearchInfoDataModel==null){
            mmsLocationSearchInfoDataModel=new ListDataModel<>();
        }
        return mmsLocationSearchInfoDataModel;
    }

    public void setMmsLocationSearchInfoDataModel(DataModel<MmsManageLocation> mmsLocationSearchInfoDataModel) {
        this.mmsLocationSearchInfoDataModel = mmsLocationSearchInfoDataModel;
    }

    

    
}
