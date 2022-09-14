/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.mms.controller;

import et.gov.eep.mms.businessLogic.LostFxAssetDtlBeanLocal;
import et.gov.eep.mms.businessLogic.ManageLocationDtlBeanLocal;
import et.gov.eep.mms.businessLogic.MmsFixedAssetRegistrationDtlBeanLocal;
import et.gov.eep.mms.businessLogic.MmsFixedAssetReturnDtlBeanLocal;
import et.gov.eep.mms.businessLogic.MmsItemRegisrtationBeanLocal;
import et.gov.eep.mms.businessLogic.MmsLocationInfoBeanLocal;
import et.gov.eep.mms.businessLogic.MmsManageLocationBeanLocal;
import et.gov.eep.mms.businessLogic.MmsStoreInformationBeanLocal;
import et.gov.eep.mms.entity.MmsFixedAssetReturnDtl;
import et.gov.eep.mms.entity.MmsFixedassetRegstDetail;
import et.gov.eep.mms.entity.MmsItemRegistration;
import et.gov.eep.mms.entity.MmsLocationInfo;
import et.gov.eep.mms.entity.MmsLostFixedAssetDetail;
import et.gov.eep.mms.entity.MmsManageLocation;
import et.gov.eep.mms.entity.MmsManageLocationDtl;
import et.gov.eep.mms.entity.MmsStoreInformation;
import et.gov.eep.mms.entity.MmsStorereq;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
//import javax.faces.view.ViewScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Inject;

/**
 *
 * @author w_station
 */
@ManagedBean
@ViewScoped
public class ShelfLifeController implements Serializable {

    @EJB
    private MmsFixedAssetReturnDtlBeanLocal fixedAssetRetDtlInterface;
    @EJB
    private MmsItemRegisrtationBeanLocal itemRegInterface;
    @EJB
    private MmsFixedAssetRegistrationDtlBeanLocal mmsFixedAssetRegistrationDtlBeanLocal;
    @EJB
    private LostFxAssetDtlBeanLocal lostFxAssetDtlInterface;
    @EJB
    private MmsManageLocationBeanLocal managedLocationInterface;
    @EJB
    private ManageLocationDtlBeanLocal manLocDtlInterface;
    @EJB
    MmsStoreInformationBeanLocal storebeanLocal;
    @EJB
    private MmsLocationInfoBeanLocal locationInfoBeanLocal;
    @Inject
    private MmsLostFixedAssetDetail lostFxAssetDetailEntity;
    @Inject
    private MmsFixedAssetReturnDtl returnDtlEntity;
    @Inject
    private MmsItemRegistration itemRegEntity;
    @Inject
    private MmsManageLocation managedLocationEntity;
    @Inject
    private MmsManageLocationDtl managedLocationDtl;
    @Inject
    private MmsLocationInfo locationInfoEntity;
    @Inject
    private MmsStoreInformation storeInfoEntity;
    @Inject
    private MmsStorereq storereq;
    @Inject
    private MmsManageLocationDtl manLocDtlEntity;
    @Inject
    private MmsFixedassetRegstDetail mmsFixedassetRegstDetail;
    List<MmsFixedassetRegstDetail> mmsFixedassetRegstDetailList;

    private String StatusLostOrRet = "wait...";
    private String StoreName1 = "wait...";
    private String Opt = new String("2");
    private boolean renderFXAsset = false;
    private boolean disableFXAsset = true;
    private boolean renderStock = false;
    private boolean disableStock = true;
    private String ItemStatus;
    private String createOrSearchBundle = "Search";
    private String icone = "ui-icon-plus";

    public ShelfLifeController() {
    }

    public MmsLostFixedAssetDetail getLostFxAssetDetailEntity() {
        if (lostFxAssetDetailEntity == null) {
            lostFxAssetDetailEntity = new MmsLostFixedAssetDetail();
        }
        return lostFxAssetDetailEntity;
    }

    public void setLostFxAssetDetailEntity(MmsLostFixedAssetDetail lostFxAssetDetailEntity) {
        this.lostFxAssetDetailEntity = lostFxAssetDetailEntity;
    }

    public MmsManageLocationDtl getManLocDtlEntity() {
        if (manLocDtlEntity == null) {
            manLocDtlEntity = new MmsManageLocationDtl();
        }
        return manLocDtlEntity;
    }

    public void setManLocDtlEntity(MmsManageLocationDtl manLocDtlEntity) {
        this.manLocDtlEntity = manLocDtlEntity;
    }

    public String getStoreName1() {
        return StoreName1;
    }

    public void setStoreName1(String StoreName1) {
        this.StoreName1 = StoreName1;
    }

    public MmsStoreInformation getStoreInfoEntity() {
        if (storeInfoEntity == null) {
            storeInfoEntity = new MmsStoreInformation();
        }
        return storeInfoEntity;
    }

    public void setStoreInfoEntity(MmsStoreInformation storeInfoEntity) {
        this.storeInfoEntity = storeInfoEntity;
    }

    public MmsStorereq getStorereq() {
        if (storereq == null) {
            storereq = new MmsStorereq();
        }
        return storereq;
    }

    public void setStorereq(MmsStorereq storereq) {
        this.storereq = storereq;
    }

    public MmsLocationInfo getLocationInfoEntity() {
        if (locationInfoEntity == null) {
            locationInfoEntity = new MmsLocationInfo();
        }
        return locationInfoEntity;
    }

    public void setLocationInfoEntity(MmsLocationInfo locationInfoEntity) {
        this.locationInfoEntity = locationInfoEntity;
    }

    public MmsFixedAssetReturnDtl getReturnDtlEntity() {
        if (returnDtlEntity == null) {
            returnDtlEntity = new MmsFixedAssetReturnDtl();
        }
        return returnDtlEntity;
    }

    public void setReturnDtlEntity(MmsFixedAssetReturnDtl returnDtlEntity) {
        this.returnDtlEntity = returnDtlEntity;
    }

    public MmsItemRegistration getItemRegEntity() {
        if (itemRegEntity == null) {
            itemRegEntity = new MmsItemRegistration();
        }
        return itemRegEntity;
    }

    public void setItemRegEntity(MmsItemRegistration itemRegEntity) {
        this.itemRegEntity = itemRegEntity;
    }

    public MmsManageLocation getManagedLocationEntity() {
        return managedLocationEntity;
    }

    public void setManagedLocationEntity(MmsManageLocation managedLocationEntity) {
        this.managedLocationEntity = managedLocationEntity;
    }

    public MmsManageLocationDtl getManagedLocationDtl() {
        if (manLocDtlEntity == null) {
            managedLocationDtl = new MmsManageLocationDtl();
        }
        return managedLocationDtl;
    }

    public void setManagedLocationDtl(MmsManageLocationDtl managedLocationDtl) {
        this.managedLocationDtl = managedLocationDtl;
    }

    public String getStatusLostOrRet() {
        return StatusLostOrRet;
    }

    public void setStatusLostOrRet(String StatusLostOrRet) {
        this.StatusLostOrRet = StatusLostOrRet;
    }

    public boolean isRenderFXAsset() {
        return renderFXAsset;
    }

    public void setRenderFXAsset(boolean renderFXAsset) {
        this.renderFXAsset = renderFXAsset;
    }

    public boolean isDisableFXAsset() {
        return disableFXAsset;
    }

    public void setDisableFXAsset(boolean disableFXAsset) {
        this.disableFXAsset = disableFXAsset;
    }

    public boolean isRenderStock() {
        return renderStock;
    }

    public void setRenderStock(boolean renderStock) {
        this.renderStock = renderStock;
    }

    public boolean isDisableStock() {
        return disableStock;
    }

    public void setDisableStock(boolean disableStock) {
        this.disableStock = disableStock;
    }

    public String getItemStatus() {
        return ItemStatus;
    }

    public void setItemStatus(String ItemStatus) {
        this.ItemStatus = ItemStatus;
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

    public String getOpt() {
        return Opt;
    }

    public void setOpt(String Opt) {
        this.Opt = Opt;
    }

    public void changeItemType(ValueChangeEvent e) {
        if (null != e.getNewValue()) {
            if (e.getNewValue().toString().equalsIgnoreCase("1")) {

                setRenderFXAsset(true);
                setDisableFXAsset(false);

                setRenderStock(false);
                setDisableStock(true);

            } else {

                setRenderFXAsset(false);
                setDisableFXAsset(true);
                setRenderStock(true);
                setDisableStock(false);
                //  listLocation.clear();

            }
        }

    }

    List<MmsManageLocationDtl> ItemList;

    public List<MmsManageLocationDtl> getItemList() {
        ItemList = manLocDtlInterface.findallInfo();
        return ItemList;
    }

    List<MmsItemRegistration> ItemList2;

    public List<MmsItemRegistration> getItemList2() {
        ItemList2 = itemRegInterface.findAllItemInfo();
        return ItemList2;
    }

    List<MmsLostFixedAssetDetail> LostItems;

    public List<MmsLostFixedAssetDetail> getLostItems() {
        LostItems = lostFxAssetDtlInterface.findAllInfo();
        return LostItems;
    }

    List<MmsFixedAssetReturnDtl> ReturnedItems;

    public List<MmsFixedAssetReturnDtl> getReturnedItems() {
        ReturnedItems = fixedAssetRetDtlInterface.findAllInfo();
        return ReturnedItems;
    }

    public void createNewLocationInfo() {

        if (createOrSearchBundle.equals("Search")) {
            //New then Search  default is New 
            createOrSearchBundle = "Search.";
            setIcone("ui-icon-search");

        } else if (createOrSearchBundle.equals("Search.")) {
            //Search then New 
            createOrSearchBundle = "Search";
            setIcone("ui-icon-plus");
        }
    }

    Integer MatId;
    List<MmsManageLocationDtl> manLocList;

    public List<MmsManageLocationDtl> getManLocList() {
        if (manLocList == null) {
            manLocList = new ArrayList<>();
        }
        return manLocList;
    }

    public void setManLocList(List<MmsManageLocationDtl> manLocList) {
        this.manLocList = manLocList;
    }

    public void handleSelectItemName(ValueChangeEvent event) {
        System.out.println("......methos handleSelectItemName...........");
        if (null != event.getNewValue()) {
            System.out.println("............event.getNewValue().toString()......." + event.getNewValue().toString());

            manLocDtlEntity = (MmsManageLocationDtl) event.getNewValue();
            managedLocationEntity = (MmsManageLocation) event.getNewValue();
            System.out.println("------------Detail Size -- --  " + managedLocationEntity.getMmsManageLocationDtlList().size());
            manLocList = managedLocationEntity.getMmsManageLocationDtlList();
            System.out.println("------------Detail Size -- --  " + manLocList.size());

            int matIds = Integer.valueOf(event.getNewValue().toString());
            itemRegEntity.setMaterialId(matIds);

//            manLocDtlEntity = manLocDtlInterface.findbyMatId(manLocDtlEntity);
//           
            System.out.println("-------------- MatId ------------" + manLocDtlEntity.getMaterialId().getMaterialId());
            MatId = manLocDtlEntity.getMaterialId().getMaterialId();

            itemRegEntity.setMaterialId(MatId); //this will be used in selecting item location

//            itemRegEntity.setItemType(managedLocationEntity.getMaterialId().getItemType());
            storeInfoEntity = manLocDtlEntity.getManageLocationId().getStoreId();

            //Added:
//            managedLocationEntity.getMmsManageLocationDtlList().get(0).getId();
//            manLocDtlEntity.getManageLocationId(); //Added:
//            manLocDtlEntity.setId(managedLocationEntity.getMmsManageLocationDtlList().get(0).getId());
//            manLocDtlEntity = manLocDtlInterface.findByDtlId(manLocDtlEntity);
//            System.out.println("------------Dtl Id ------------  " + manLocDtlEntity.getId());
            manLocList = manLocDtlInterface.findLocListById(manLocDtlEntity);
            managedLocationEntity.setId(manLocDtlEntity.getManageLocationId().getId());
            managedLocationEntity = managedLocationInterface.findById(managedLocationEntity);
            manLocList = new ArrayList<>();
            manLocList = managedLocationEntity.getMmsManageLocationDtlList();

            listLocation.clear();
            System.out.println("...........manLocList.size()......" + manLocList.size());
            for (int i = 0; i < manLocList.size(); i++) {
                listLocation.add(manLocList.get(i).getCellId());
            }

        }

    }

//    public void handleSelectStockItem(ValueChangeEvent event) {
//        if (null != event.getNewValue()) {
//
//            Integer Id = Integer.parseInt(event.getNewValue().toString());
//            System.out.println(".........Integer...." + Id);
//            itemRegEntity.setMaterialId(Id);
//
//            itemRegEntity = itemRegInterface.getMmsItemInfoByMatId(itemRegEntity);
//            System.out.println("....itemRegEntity........." + itemRegEntity);
//
//            managedLocationEntity = new MmsManageLocation();
//            managedLocationEntity.setMaterialId(itemRegEntity);
//            System.out.println(".......managedLocationEntity....." + managedLocationEntity.getMaterialId());
//            managedLocationEntity = managedLocationInterface.findLocById(managedLocationEntity); // Find By MatID
//            //   System.out.println("--------- Detail Size ----------  " + managedLocationEntity.getMmsManageLocationDtlList().size());
//            locationInfoEntity = managedLocationEntity.getMmsManageLocationDtlList().get(0).getCellId();
//            itemRegEntity = managedLocationEntity.getMaterialId();
//            storeInfoEntity = managedLocationEntity.getMaterialId().getStoreId();
//
//        }
//
//    }
    List<MmsItemRegistration> itemList3;

    public List<MmsItemRegistration> getItemList3() {

        if (itemList3 == null) {
            itemList3 = new ArrayList<>();
        }
        return itemList3;
    }

    public void setItemList3(List<MmsItemRegistration> itemList3) {
        this.itemList3 = itemList3;
    }

    List<MmsItemRegistration> itemList4;

    public List<MmsItemRegistration> getItemList4() {
        if (itemList4 == null) {
            itemList4 = new ArrayList<>();
        }
        return itemList4;
    }

    public void setItemList4(List<MmsItemRegistration> itemList4) {
        this.itemList4 = itemList4;
    }

    List<MmsStoreInformation> StoreList;

    public List<MmsStoreInformation> getStoreList() {
        StoreList = storebeanLocal.findAllStoreInfo();
        return StoreList;
    }
    List<MmsFixedassetRegstDetail> itemlist1;

//    public List<MmsFixedassetRegstDetail> getMmsFixedassetRegstDetailList() {
//        if (mmsFixedassetRegstDetailList == null) {
//            mmsFixedassetRegstDetailList = new ArrayList<>();
////            mmsFixedassetRegstDetailList = mmsFixedAssetRegistrationDtlBeanLocal.findAll();
//            mmsFixedassetRegstDetailList = mmsFixedAssetRegistrationDtlBeanLocal.getTagNo();
//        }
//        return mmsFixedassetRegstDetailList;
//    }
    public List<MmsFixedassetRegstDetail> getMmsFixedassetRegstDetailList() {
        mmsFixedassetRegstDetailList = mmsFixedAssetRegistrationDtlBeanLocal.getTagNo();
        return mmsFixedassetRegstDetailList;
    }

    public void handleSelectStoreName(ValueChangeEvent event) {
        if (null != event.getNewValue()) {

            System.out.println("-------------- Inside Select Store Name --------");
            Integer Id = Integer.parseInt(event.getNewValue().toString());
            System.out.println(".........Integer...." + Id);
            storeInfoEntity.setStoreId(Id);
            itemRegEntity.setStoreId(storeInfoEntity);
            itemList3 = itemRegInterface.searchItemByStoreId(itemRegEntity);

            itemName1.clear();
            System.out.println("...........itemList3.size()......" + itemList3.size());
            for (int i = 0; i < itemList3.size(); i++) {
                itemName1.add(itemList3.get(i));
                System.out.println("-------- Item Name --------" + itemName1.get(i).getMatName());
            }

        }

    }

    public void handleSelectStoreName2(ValueChangeEvent event) {
        if (null != event.getNewValue()) {

            System.out.println("-------------- Inside Select Store Name --------");
            String Id = (event.getNewValue().toString());
            System.out.println(".........Integer...." + Id);
            itemRegEntity.setMatName(Id);
            storeInfoEntity.setStoreId(Integer.parseInt(Id));
            itemRegEntity.setStoreId(storeInfoEntity);
            itemList3 = itemRegInterface.searchItemByStoreId(itemRegEntity);

            itemName1.clear();
            System.out.println("...........itemList3.size()......" + itemList3.size());
            for (int i = 0; i < itemList3.size(); i++) {
                itemName1.add(itemList3.get(i));
                System.out.println("-------- Item Name --------" + itemName1.get(i).getMatName());

            }
            manLocList = manLocDtlInterface.findLocListById(manLocDtlEntity);
            managedLocationEntity.setId(manLocDtlEntity.getManageLocationId().getId());
            managedLocationEntity = managedLocationInterface.findById(managedLocationEntity);
            manLocList = new ArrayList<>();
            manLocList = managedLocationEntity.getMmsManageLocationDtlList();
            listLocation.clear();
            System.out.println("...........manLocList.size()......" + manLocList.size());
            for (int i = 0; i < manLocList.size(); i++) {
                listLocation.add(manLocList.get(i).getCellId());
            }
        }

    }
  //  public void handleTagNo(ValueChangeEvent event) {
//        if (null != event.getNewValue()) {
//
////            mmsFixedassetRegstDetail = (MmsFixedassetRegstDetail) event.getNewValue();
//            String name = event.getNewValue().toString();
//            mmsFixedassetRegstDetail.setTagNo(name);
//            System.out.println("=====tagno===" + mmsFixedassetRegstDetail.getTagNo());
//            mmsFixedassetRegstDetail.setItemName(name);
//            System.out.println("=====item name===" + mmsFixedassetRegstDetail.getItemName());
//            mmsFixedassetRegstDetail.setItemStatus(name);
//            System.out.println("=====ItemStatus===" + mmsFixedassetRegstDetail.getItemStatus());
//            mmsFixedassetRegstDetail.setStoreReqId(mmsFixedassetRegstDetail.getStoreReqId());
//            mmsFixedassetRegstDetail.setStoreReqId(storereq);
//            storeInfoEntity.setStoreId(Integer.parseInt(name));
//
//            itemlist1 = mmsFixedAssetRegistrationDtlBeanLocal.findByTagNo2(mmsFixedassetRegstDetail);
//        }
//    }
    public void handleSelectStoreName3(ValueChangeEvent event) {
        if (null != event.getNewValue()) {
//            mmsFixedassetRegstDetail = (MmsFixedassetRegstDetail) event.getNewValue();
            System.out.println("-------------- Inside Select Store Name --------");
            String Id = (event.getNewValue().toString());
            System.out.println(".........Integer...." + Id);
            mmsFixedassetRegstDetail.setTagNo(Id);
            System.out.println("...........tag no......" + mmsFixedassetRegstDetailList.size());
////            mmsFixedassetRegstDetail.setItemName(Id);
//            System.out.println("=====item name===" + mmsFixedassetRegstDetail.getItemName());
//            mmsFixedassetRegstDetail.setItemStatus(Id);
//            System.out.println("=====ItemStatus===" + mmsFixedassetRegstDetail.getItemStatus());
            storeInfoEntity.setStoreId(Integer.parseInt(Id));
            mmsFixedassetRegstDetail.setStoreReqId(storereq);
            System.out.println("...........Store id......" + mmsFixedassetRegstDetail.getStoreReqId().getStoreReqId());
            mmsFixedassetRegstDetailList = mmsFixedAssetRegistrationDtlBeanLocal.findByTagNo2(mmsFixedassetRegstDetail);

            mmsFixedassetRegstDetailList.clear();
        }

    }

    public void handleTagNo(ValueChangeEvent event) {
        System.out.println("====inside handeler==");
        if (null != event.getNewValue()) {
            mmsFixedassetRegstDetail = (MmsFixedassetRegstDetail) event.getNewValue();
            String name = event.getNewValue().toString();
            mmsFixedassetRegstDetail.setTagNo(name);
            System.out.println("====tag no----"+name); 
            mmsFixedassetRegstDetail.setFarDetId(Integer.parseInt(name));
//            mmsFixedassetRegstDetail.setTagNo(name);
//            System.out.println("=====name===" + mmsFixedassetRegstDetail);
//            System.out.println("=====tagno===" + mmsFixedassetRegstDetail.getTagNo());
//            mmsFixedassetRegstDetail.setItemName(mmsFixedassetRegstDetail.getItemName());
//            System.out.println("=====item name===" + mmsFixedassetRegstDetail.getItemName());
//            mmsFixedassetRegstDetail.setItemStatus(name);
//            System.out.println("=====ItemStatus===" + mmsFixedassetRegstDetail.getItemStatus());
//            mmsFixedassetRegstDetail.setStoreReqId(mmsFixedassetRegstDetail.getStoreReqId());
//            mmsFixedassetRegstDetail.setStoreReqId(storereq);
//            storeInfoEntity.setStoreId(Integer.parseInt(name));
//            System.out.println("=====ItemStatus===" + name);
            itemlist1 = mmsFixedAssetRegistrationDtlBeanLocal.findByTagNo2(mmsFixedassetRegstDetail);
        }
    }

    public void handleSelectItemStatus(ValueChangeEvent event) {
        if (null != event.getNewValue()) {
            System.out.println("pls");
//            String itemName = event.getNewValue().toString();
//            int sizeLost = LostItems.size();
//            int sizeReturn = ReturnedItems.size();
//            for (int i = 0; i < sizeLost; i++) {
//                if (LostItems.get(i).getTagNo().equalsIgnoreCase(itemName)) {
//                    setStatusLostOrRet("Lost");
//                    setStoreName1(LostItems.get(i).getLostItemId().getStoreId().getStoreName());
//                }
//            }
//            for (int j = 0; j < sizeReturn; j++) {
//                if (ReturnedItems.get(j).getItemCode().equalsIgnoreCase(itemName)) {
//                    setStatusLostOrRet("Returned as" + " " + ReturnedItems.get(j).getItemStatus());
//                    setStoreName1(ReturnedItems.get(j).getFarId().getStoreId().getStoreName());
//                }
//            }

            mmsFixedassetRegstDetail = (MmsFixedassetRegstDetail) event.getNewValue();
            mmsFixedassetRegstDetail = mmsFixedAssetRegistrationDtlBeanLocal.getTagInfoBtyId(mmsFixedassetRegstDetail);
        }
    }

    public void handleSelectReturned(ValueChangeEvent event) {
        if (null != event.getNewValue()) {
            setStatusLostOrRet("Returned");

        }

    }

    ArrayList<String> displayLostAndReturn;

    public ArrayList<String> getDisplayLostAndReturn() {
        //Value of Tag no from Return and Lost Fixed asset ....
        displayLostAndReturn = new ArrayList<>();
        ReturnedItems = fixedAssetRetDtlInterface.findAllInfo();
        LostItems = lostFxAssetDtlInterface.findAllInfo();
        int sizeLost = LostItems.size();
        int sizeReturn = ReturnedItems.size();

        for (int i = 0; i < sizeReturn; i++) {
            displayLostAndReturn.add(ReturnedItems.get(i).getItemCode());
        }
        for (int j = 0; j < sizeLost; j++) {
            displayLostAndReturn.add(LostItems.get(j).getTagNo());
        }

        return displayLostAndReturn;
    }

    public void setDisplayLostAndReturn(ArrayList<String> displayLostAndReturn) {
        this.displayLostAndReturn = displayLostAndReturn;
    }

    List<MmsLocationInfo> listLocation = new ArrayList<>();
    List<MmsItemRegistration> itemName1 = new ArrayList<>();

    public List<MmsLocationInfo> getListLocation() {
        listLocation = locationInfoBeanLocal.findAllItemInfo();
        return listLocation;
    }

    public void setListLocation(List<MmsLocationInfo> listLocation) {
        this.listLocation = listLocation;
    }

    public List<MmsItemRegistration> getItemName1() {
        return itemName1;
    }

    public void setItemName1(List<MmsItemRegistration> itemName1) {
        this.itemName1 = itemName1;
    }

    List<MmsManageLocationDtl> locationInfo3;

    public List<MmsManageLocationDtl> getLocationInfo3() {
        if (locationInfo3 == null) {
            locationInfo3 = new ArrayList<>();
        }
        return locationInfo3;
    }

    public void setLocationInfo3(List<MmsManageLocationDtl> locationInfo3) {
        this.locationInfo3 = locationInfo3;
    }

    public void handleSelectLoc(ValueChangeEvent event) {

        //Integer locId = Integer.parseInt(event.getNewValue().toString());
        MmsLocationInfo locInfo = new MmsLocationInfo();
        locInfo = (MmsLocationInfo) event.getNewValue();
        MmsManageLocationDtl manLocDtl = new MmsManageLocationDtl();
        MmsManageLocation manLoc = new MmsManageLocation();

        //locInfo.setLocationId(locId);
        manLocDtl.setCellId(locInfo);
        manLocDtl.setMaterialId(itemRegEntity);
        manLocDtl.setManageLocationId(manLoc);

        System.out.println("------ Loc Id  -- --  " + locInfo.getLocationId());

        locationInfo3 = manLocDtlInterface.getManLocationByLocId(manLocDtl, locInfo);
//        managedLocationEntity.setExpirationDate(locationInfo3.get(0).getManageLocationId().getExpirationDate());
        itemRegEntity.setItemType(locationInfo3.get(0).getMaterialId().getItemType());

    }

    public void handleSelectItem(ValueChangeEvent event) {

    }

    public void handleSelectItem2(ValueChangeEvent event) {

    }

    /**
     * @return the MmsFixedassetRegstDetailList
     */
    /**
     * @param MmsFixedassetRegstDetailList the MmsFixedassetRegstDetailList to
     * set
     */
    public void setMmsFixedassetRegstDetailList(List<MmsFixedassetRegstDetail> MmsFixedassetRegstDetailList) {
        this.mmsFixedassetRegstDetailList = MmsFixedassetRegstDetailList;
    }

    /**
     * @return the mmsFixedassetRegstDetail
     */
    public MmsFixedassetRegstDetail getMmsFixedassetRegstDetail() {
        if (mmsFixedassetRegstDetail == null) {
            mmsFixedassetRegstDetail = new MmsFixedassetRegstDetail();
        }
        return mmsFixedassetRegstDetail;
    }

    /**
     * @param mmsFixedassetRegstDetail the mmsFixedassetRegstDetail to set
     */
    public void setMmsFixedassetRegstDetail(MmsFixedassetRegstDetail mmsFixedassetRegstDetail) {
        this.mmsFixedassetRegstDetail = mmsFixedassetRegstDetail;
    }

    /**
     * @return the mmsFixedAssetRegistrationDtlBeanLocal
     */
    public MmsFixedAssetRegistrationDtlBeanLocal getMmsFixedAssetRegistrationDtlBeanLocal() {
        return mmsFixedAssetRegistrationDtlBeanLocal;
    }

    /**
     * @param mmsFixedAssetRegistrationDtlBeanLocal the
     * mmsFixedAssetRegistrationDtlBeanLocal to set
     */
    public void setMmsFixedAssetRegistrationDtlBeanLocal(MmsFixedAssetRegistrationDtlBeanLocal mmsFixedAssetRegistrationDtlBeanLocal) {
        this.mmsFixedAssetRegistrationDtlBeanLocal = mmsFixedAssetRegistrationDtlBeanLocal;
    }

}
