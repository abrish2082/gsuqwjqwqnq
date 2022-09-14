/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.mms.controller;

import et.gov.eep.mms.businessLogic.MmsLocationInformationBeanLocal;
import et.gov.eep.mms.businessLogic.MmsStoreInformationBeanLocal;
import et.gov.eep.mms.entity.MmsLocationInfoDetail;
import et.gov.eep.mms.entity.MmsLocationInformation;
import et.gov.eep.mms.entity.MmsStoreInformation;

import et.gov.eep.commonApplications.utility.JsfUtil;
import java.io.Serializable;
import java.util.ArrayList;
import javax.ejb.EJB;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author kimmyo
 */

@Named(value = "storeLocationInfoManagedBean")
@ViewScoped
public class storeLocationInfoManagedBean implements Serializable {

    /**
     * Creates a new instance of storeLocationInfoManagedBean
     */
  
    @EJB
    private MmsLocationInformationBeanLocal locationInfoInterface;
    @EJB
    private MmsStoreInformationBeanLocal storeInfoInterface;
   @Inject
    MmsLocationInformation locationInfoEntity;
    @Inject
    MmsLocationInfoDetail locInfoDetailEntity;
    @Inject
    MmsStoreInformation storeInfoEntity;
    
    /**
     *
     */
    public storeLocationInfoManagedBean() {
    }
      
      DataModel<MmsLocationInfoDetail>LocInfoAddDetailDataModel;
       private String saveorUpdateBundle ="Create";
       int updateStatus = 0;

    /**
     *
     * @return
     */
    public MmsLocationInformation getLocationInfoEntity() {
             if (this.locationInfoEntity == null) {
            this.locationInfoEntity = new MmsLocationInformation();
        }
        
        return locationInfoEntity;
    }

    /**
     *
     * @param locationInfoEntity
     */
    public void setLocationInfoEntity(MmsLocationInformation locationInfoEntity) {
        this.locationInfoEntity = locationInfoEntity;
    }

    /**
     *
     * @return
     */
    public MmsLocationInfoDetail getLocInfoDetailEntity() {
        if(this.locInfoDetailEntity==null){
            this.locInfoDetailEntity=new MmsLocationInfoDetail();
        }
        return locInfoDetailEntity;
    }

    /**
     *
     * @param locInfoDetailEntity
     */
    public void setLocInfoDetailEntity(MmsLocationInfoDetail locInfoDetailEntity) {
        this.locInfoDetailEntity = locInfoDetailEntity;
    }

    /**
     *
     * @return
     */
    public MmsStoreInformation getStoreInfoEntity() {
        if(this.storeInfoEntity==null){
            this.storeInfoEntity=new MmsStoreInformation();  
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
    public DataModel<MmsLocationInfoDetail> getLocInfoAddDetailDataModel() {
        if(LocInfoAddDetailDataModel==null){
            LocInfoAddDetailDataModel=new ListDataModel<>();
        }
        return LocInfoAddDetailDataModel;
    }

    /**
     *
     * @param LocInfoAddDetailDataModel
     */
    public void setLocInfoAddDetailDataModel(DataModel<MmsLocationInfoDetail> LocInfoAddDetailDataModel) {
        this.LocInfoAddDetailDataModel = LocInfoAddDetailDataModel;
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
       
    
     /**
     * Add Location Detail Information to DataTable Add PopUp data to Data Table
     */
    private void clearPopUp() {
       locInfoDetailEntity = null;
    }

    /**
     *
     * @return
     */
    public String addLocationInfoDetail() { 
        locationInfoEntity.addToLocationInfoDetail(locInfoDetailEntity);
        recreateModelDetail();
        clearPopUp();
        return null;

    } 
    
//    Add to DataModel
    
    /**
     *
     */
        
     public void recreateModelDetail() {
        LocInfoAddDetailDataModel = null;
        LocInfoAddDetailDataModel = new ListDataModel(new ArrayList<>(locationInfoEntity.getPapmsLocationInfoDetailList()));
    }

    /**
     *
     */
    public void updateLocationInfoDetail() {

        locInfoDetailEntity = getLocInfoAddDetailDataModel().getRowData();
    }

    /**
     *
     * @return
     */
    public String clearPage() {
        locationInfoEntity = null;
        storeInfoEntity = null;
        locInfoDetailEntity = null;
        LocInfoAddDetailDataModel = null;
        updateStatus= 0;
        saveorUpdateBundle = "Create";
       
        return null;
    }

    /**
     *
     * @return
     */
    public String btnSave_Action_Listner() {
   // boolean result=locationInfoInterface.getMmsLocationInformationDup(locationInfoEntity);
     if (updateStatus ==0  ){
             //&& result==false) {
         try{
         locationInfoEntity.setStoreId(storeInfoEntity);
            locationInfoInterface.create(locationInfoEntity);
            JsfUtil.addSuccessMessage("Location info is saved");
            return clearPage();
         }
     catch(Exception ex){
         JsfUtil.addErrorMessage("Something Error Occured on Data created");
                return null;
        } }
     else if (updateStatus == 0 ){
             //&& result == true){
          JsfUtil.addSuccessMessage("Duplicate Information is not allowed");
     }
     else {
         try{
            locationInfoInterface.edit(locationInfoEntity);
            
            JsfUtil.addSuccessMessage("Location info is Updated");
           return clearPage();
        }
         catch(Exception ex){
             JsfUtil.addSuccessMessage("Duplicate data exists");
                return null;
         }
     }
     
        return null;

    }

    /**
     *
     * @param event
     */
    public void getLocationInformation(SelectEvent event) {
        locationInfoEntity.setShelfNo(event.getObject().toString());
        locationInfoEntity = locationInfoInterface.getMmsLocationInformation(locationInfoEntity);
        updateStatus = 1;
      
        saveorUpdateBundle = "Update";
        recreateModelDetail();
    }
 
    /**
     *
     * @param shelfNo
     * @return
     */
    public ArrayList<MmsLocationInformation> searchShelfInformation(String shelfNo) {
        ArrayList<MmsLocationInformation> locationInformations = null;// to make the previous search  paramaters and results null;
        locationInfoEntity.setShelfNo(shelfNo);
        storeInfoEntity.getStoreId();
        
        try {

            locationInfoEntity.setStoreId(storeInfoEntity);
            locationInformations = locationInfoInterface.searchStoreAndShelfInfo(locationInfoEntity);
           // searchedShelfSize = locationInfos.size();
        } catch (Exception e) {
            return null;
        }
      
        return locationInformations; 
    }
 //@PostConstruct

    /**
     *
     * @return
     */
     public SelectItem[] getStoreNameList() {
     return JsfUtil.getSelectItems(storeInfoInterface.findAllStoreInfo(), true);
    }

    /**
     *
     * @param event
     */
    public void handleSelectStoreName(ValueChangeEvent event) {
        if (null != event.getNewValue()) {
            
           storeInfoEntity.setStoreName(event.getNewValue().toString());
           storeInfoEntity=storeInfoInterface.getPapmsStoreInformation(storeInfoEntity);
            
        }

    }

    /**
     *
     * @param event
     */
    public void searchSelectStoreListener(ValueChangeEvent event) {
        if (null != event.getNewValue()) {
            String store = event.getNewValue().toString();
           storeInfoEntity.setStoreName(store);
            
        }
    }
 }

