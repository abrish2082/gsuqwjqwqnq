
package et.gov.eep.mms.businessLogic;

import et.gov.eep.commonApplications.entity.ColumnNameResolver;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import et.gov.eep.mms.entity.MmsCategory;
import et.gov.eep.mms.entity.MmsGrn;
import et.gov.eep.mms.entity.MmsIsivReceived;
import et.gov.eep.mms.entity.MmsItemRegistration;
import et.gov.eep.mms.entity.MmsRmg;
import et.gov.eep.mms.entity.MmsStoreInformation;
import et.gov.eep.mms.entity.MmsSubCat;
import et.gov.eep.mms.mapper.MmsItemRegistrationFacade;

/**
 *
 * @author Minab
 */
@Stateless
public class MmsItemRegisrtationBean implements MmsItemRegisrtationBeanLocal {

    @EJB
    MmsItemRegistrationFacade informationFacade;

    @Inject
    MmsItemRegistration papmsItemRegistration;

    /**
     *
     * @param information
     */
    @Override
    public void create(MmsItemRegistration information) {
        informationFacade.create(information);
    }

    /**
     *
     * @param information
     */
    @Override
    public void edit(MmsItemRegistration information) {
        informationFacade.edit(information);
    }

    /**
     *
     * @param papmsItemRegistration
     * @return
     */
    @Override
    public ArrayList<MmsItemRegistration> searchByMaterialName(MmsItemRegistration papmsItemRegistration) {
        return informationFacade.searchByMaterialName(papmsItemRegistration);
    }

    /**
     *
     * @param item
     * @return
     */
    @Override
    public MmsItemRegistration getByMaterialName(MmsItemRegistration item) {
        return informationFacade.getByMaterialName(item);

    }

    /**
     *
     * @param papmsMaterialInformation
     * @return
     */
    @Override
    public List<MmsItemRegistration> searchMaterialInformationsByMatCode(MmsItemRegistration papmsMaterialInformation) {
        return informationFacade.searchByMaterialName(papmsItemRegistration);

    }

    /**
     *
     * @param items
     * @return
     */
    @Override
    public ArrayList<MmsItemRegistration> SearchByMatNameAgain(MmsItemRegistration items) {
        return informationFacade.searchByInspectorName(items);
    }
//for sadik

    /**
     *
     * @return
     */
    @Override
    public List<MmsItemRegistration> findAllMaterialCodeInfo() {
        return informationFacade.findAll();
    }

    /**
     *
     * @return
     */
    @Override
    public List<MmsItemRegistration> getName() {
        return informationFacade.getItemLists();
    }

    /**
     *
     * @param itemName
     * @return
     */
    @Override
    public MmsItemRegistration getPapmsItemInformation(MmsItemRegistration itemName) {
        return informationFacade.getPapmsItemInformation(itemName);
    }

    /**
     *
     * @param itemInformation
     * @return
     */
    @Override
    public MmsItemRegistration SearchMatIDByItemCode(MmsItemRegistration itemInformation) {
        return informationFacade.SearchMatIdByMatCode(itemInformation);
    }

    /**
     *
     * @param MaterialInformation
     * @return
     */
    @Override
    public List<MmsItemRegistration> searchMaterialInfoByMatCode(MmsItemRegistration MaterialInformation) {
        return informationFacade.searchByMaterialCode(MaterialInformation);
    }

    /**
     *
     * @param itemRegistrationEntity
     * @return
     */
    @Override
    public MmsItemRegistration getMmsItemInfoByCode(MmsItemRegistration itemRegistrationEntity) {
        return informationFacade.getMmsItemInformationByCode(itemRegistrationEntity);
    }

    /**
     *
     * @param items
     * @return
     */
    @Override
    public ArrayList<MmsItemRegistration> searchItem(String items) {
        return informationFacade.searchItem(items);
    }

    @Override
    public List<MmsItemRegistration> findAllItemInfo() {
        return informationFacade.findAll();
    }

    @Override
    public List<MmsItemRegistration> findUnitMeasureInfo() {
        return informationFacade.findAll();
    }

    @Override
    public MmsItemRegistration getMmsItemInfoByMatId(MmsItemRegistration itemRegistrationEntity) {
        return informationFacade.getByMaterialId(itemRegistrationEntity);
    }

    @Override
    public String getLastMaterialCode(MmsItemRegistration papmsItemRegistration, MmsCategory category, MmsSubCat subcat) {
        return informationFacade.getLastMatCodeByParameter1(papmsItemRegistration, category, subcat);
    }

    @Override
    public List<MmsItemRegistration> getByMatCodeJoinedWithBinCard(MmsItemRegistration papmsItemRegistration) {
        return informationFacade.getByMatCodeJoinedWithBinCard(papmsItemRegistration);
    }

    @Override
    public List<MmsItemRegistration> findAllJoinedWithBinCard() {
        return informationFacade.findAllJoinedWithBinCard();
    }

    @Override
    public List<MmsItemRegistration> getItemInfo(MmsItemRegistration itemRegistrationEntity) {
        return informationFacade.getItemInfo(itemRegistrationEntity);
    }

    @Override
    public MmsItemRegistration SearchInfoByMatcode(MmsItemRegistration itemRegEntity, MmsGrn grnEntity) {
        return informationFacade.SearchInfoByMatcodeJoined(itemRegEntity, grnEntity);
    }

    public List<MmsItemRegistration> getItemInfo2(MmsItemRegistration itemRegistrationEntity) {
        return informationFacade.getItemInfo2(itemRegistrationEntity);
    }

    @Override
    public MmsItemRegistration SearchIsivRecievedInfoByMatcodeJoined(MmsItemRegistration itemRegEntity, MmsIsivReceived ivEntity) {
        return informationFacade.SearchIsivRecievedInfoByMatcodeJoined(itemRegEntity, ivEntity);
    }

    @Override
    public MmsItemRegistration SearchRmgRecievedInfoByMatcodeJoined(MmsItemRegistration itemRegEntity, MmsRmg rmgEntity) {
        return informationFacade.SearchRmgInfoByMatcodeJoined(itemRegEntity, rmgEntity);
    }

    @Override
    public List<MmsItemRegistration> searchMaterialInfoByStoreAndMatCode(MmsItemRegistration papmsMaterialInformation) {
        return informationFacade.searchByStoreAndMaterialCode(papmsMaterialInformation);
    }

    @Override
    public boolean checkItemInformationDuplication(MmsItemRegistration itemRegistrationEntity) {
        return informationFacade.checkforDuplication(itemRegistrationEntity);
    }

    @Override
    public List<MmsItemRegistration> searchByAllParameters(MmsItemRegistration papmsItemRegistration) {
        return informationFacade.searchByAllParameters(papmsItemRegistration);
    }

    @Override
    public List<MmsItemRegistration> searchByStoreAndItemName(MmsItemRegistration papmsItemRegistration) {
        return informationFacade.searchByStoreAndItemName(papmsItemRegistration);
    }

    @Override
    public List<MmsItemRegistration> searchByStoreAndItemCode(MmsItemRegistration papmsItemRegistration) {
        return informationFacade.searchByStoreAndItemCode(papmsItemRegistration);
    }

    @Override
    public List<MmsItemRegistration> searchItemInfoByStore(MmsStoreInformation storeInfoEntity) {
        return informationFacade.searchItemInfoByStore(storeInfoEntity);
    }

    @Override
    public List<MmsItemRegistration> searchMaterialInfoByStoreAndMatName(MmsItemRegistration papmsMaterialInformation) {
        return informationFacade.searchByStoreAndMaterialName(papmsMaterialInformation);
    }

    @Override
    public List<MmsItemRegistration> searchItemByStoreId(MmsItemRegistration itemRegEntity) {
        return informationFacade.searchItemByStoreId(itemRegEntity);
    }

    @Override
    public List<MmsItemRegistration> searchMaterialInfoByStoreAndMatNameAndItemGroup(MmsItemRegistration papmsMaterialInformation) {
        return informationFacade.searchMaterialInfoByStoreAndMatNameAndItemGroup(papmsMaterialInformation);
    }

    @Override
    public List<MmsItemRegistration> searchMaterialInfoByStoreAndMatNameAndItemGroupAndGLID(MmsItemRegistration papmsMaterialInformation) {
        return informationFacade.searchMaterialInfoByStoreAndMatNameAndItemGroupAndGLID(papmsMaterialInformation);
    }

    @Override
    public List<MmsItemRegistration> searchByStoreAndGlId(MmsItemRegistration papmsItemRegistration) {
        return informationFacade.searchByStoreAndGlId(papmsItemRegistration);
    }

    @Override
    public MmsItemRegistration searchByMaterialId(Integer materialId) {
        return informationFacade.searchByMateialId(materialId);
    }

    @Override
    public List<MmsItemRegistration> searchItemInfoByStoreAndPeparerId(MmsStoreInformation storeInfoEntity, MmsItemRegistration itemRegistration) {
        return informationFacade.searchItemInfoByStoreAndPeparerId(storeInfoEntity, itemRegistration);
    }

    @Override
    public List<MmsItemRegistration> searchByStoreAndItemCodeAndPeparerId(MmsItemRegistration papmsItemRegistration) {
        return informationFacade.searchByStoreAndItemCodeAndPeparerId(papmsItemRegistration);
    }

    @Override
    public List<MmsItemRegistration> searchByStoreAndItemNameAndPreparerId(MmsItemRegistration papmsItemRegistration) {
        return informationFacade.searchByStoreAndItemNameAndPreparerId(papmsItemRegistration);
    }

    @Override
    public List<MmsItemRegistration> searchByAllParametersAndPreparerId(MmsItemRegistration papmsItemRegistration) {
        return informationFacade.searchByAllParametersAndPreparerId(papmsItemRegistration);
    }

    @Override
    public List<MmsItemRegistration> searchAllByPreparerId(MmsItemRegistration papmsItemRegistration) {
        return informationFacade.searchAllByPreparerId(papmsItemRegistration);
    }

    @Override
    public ArrayList<MmsItemRegistration> searchMatInformationByPrefix(String items) {
        return informationFacade.searchMatInformationByPrefix(items);
    }
    @Override
    public ArrayList<MmsItemRegistration> searchMatInformationByPrefixObject(MmsItemRegistration itemRegistration) {
        return informationFacade.searchMatInformationByPrefixObject(itemRegistration);
    }
    

    @Override
    public List<MmsItemRegistration> findItemsNotRegisteredOnBincard(MmsStoreInformation storeInformation) {
        return informationFacade.findItemsNotRegisteredOnBincard(storeInformation);
    }

    @Override
    public MmsItemRegistration searchByMaterialCode(String matCode) {
         return informationFacade.searchByMaterialCode(matCode);
    }

    @Override
    public List<String> getMmsItemRColumnNameList() {
        return informationFacade.getMmsItemRColumnNameList();
    }

    @Override
    public List<MmsItemRegistration> getIRListsByParameter(MmsItemRegistration papmsItemRegistration) {
         return informationFacade.getIRListsByParameter(papmsItemRegistration);
    }

    @Override
    public List<MmsItemRegistration> getItemListsByParameter(ColumnNameResolver columnNameResolver, MmsItemRegistration papmsItemRegistration, String columnValue) {
         return informationFacade.getItemListsByParameter(columnNameResolver, papmsItemRegistration, columnValue); 
    }

    @Override
    public int ConutBYItemType(String get) {
       return informationFacade.ConutBYItemType(get);
    }

    @Override
    public List<String> findAllItemType() {
       return informationFacade.findAllItemType();
    }

   

    

}
