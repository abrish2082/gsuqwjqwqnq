package et.gov.eep.mms.businessLogic;

import et.gov.eep.commonApplications.entity.ColumnNameResolver;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Local;
import et.gov.eep.mms.entity.MmsCategory;
import et.gov.eep.mms.entity.MmsGrn;
import et.gov.eep.mms.entity.MmsIsivReceived;
import et.gov.eep.mms.entity.MmsItemRegistration;
import et.gov.eep.mms.entity.MmsRmg;
import et.gov.eep.mms.entity.MmsStoreInformation;
import et.gov.eep.mms.entity.MmsSubCat;

/**
 *
 * @author Minab
 */
@Local
public interface MmsItemRegisrtationBeanLocal {

    /**
     *
     * @param papmsitemregistration
     */
    void create(MmsItemRegistration papmsitemregistration);

    /**
     *
     * @param papmsitemregistration
     */
    void edit(MmsItemRegistration papmsitemregistration);

    /**
     *
     * @param papmsMaterialInformation
     * @return
     */
    List<MmsItemRegistration> searchMaterialInformationsByMatCode(MmsItemRegistration papmsMaterialInformation);

    //for Sadik
    /**
     *
     * @param papmsMaterialInformation
     * @return
     */
    List<MmsItemRegistration> searchMaterialInfoByMatCode(MmsItemRegistration papmsMaterialInformation);

    List<MmsItemRegistration> searchMaterialInfoByStoreAndMatCode(MmsItemRegistration papmsMaterialInformation);

    /**
     *
     * @param papmsItemRegistration
     * @return
     */
    public ArrayList<MmsItemRegistration> searchByMaterialName(MmsItemRegistration papmsItemRegistration);

    /**
     *
     * @param item
     * @return
     */
    public MmsItemRegistration getByMaterialName(MmsItemRegistration item);

    /**
     *
     * @param items
     * @return
     */
    public ArrayList<MmsItemRegistration> SearchByMatNameAgain(MmsItemRegistration items);

    /**
     *
     * @return
     */
    public List<MmsItemRegistration> findAllMaterialCodeInfo();

    /**
     *
     * @param itemName
     * @return
     */
    MmsItemRegistration getPapmsItemInformation(MmsItemRegistration itemName);

    /**
     *
     * @param itemInformation
     * @return
     */
    MmsItemRegistration SearchMatIDByItemCode(MmsItemRegistration itemInformation);

    /**
     *
     * @return
     */
    public List<MmsItemRegistration> getName();

    /**
     *
     * @param itemRegistrationEntity
     * @return
     */
    public MmsItemRegistration getMmsItemInfoByCode(MmsItemRegistration itemRegistrationEntity);

    //today
    /**
     *
     * @param items
     * @return
     */
    public ArrayList<MmsItemRegistration> searchMatInformationByPrefix(String items);

    public ArrayList<MmsItemRegistration> searchMatInformationByPrefixObject(MmsItemRegistration itemRegistration);

    public ArrayList<MmsItemRegistration> searchItem(String items);

    public List<MmsItemRegistration> findAllItemInfo();

    public List<MmsItemRegistration> findUnitMeasureInfo();

    public MmsItemRegistration getMmsItemInfoByMatId(MmsItemRegistration itemRegistrationEntity);

    public String getLastMaterialCode(MmsItemRegistration papmsItemRegistration, MmsCategory category, MmsSubCat subcat);

    public List<MmsItemRegistration> getByMatCodeJoinedWithBinCard(MmsItemRegistration papmsItemRegistration);

    public List<MmsItemRegistration> findAllJoinedWithBinCard();

    public List<MmsItemRegistration> getItemInfo(MmsItemRegistration itemRegistrationEntity);

    public MmsItemRegistration SearchInfoByMatcode(MmsItemRegistration itemRegEntity, MmsGrn grnEntity);

    public MmsItemRegistration SearchIsivRecievedInfoByMatcodeJoined(MmsItemRegistration itemRegEntity, MmsIsivReceived ivEntity);

    public MmsItemRegistration SearchRmgRecievedInfoByMatcodeJoined(MmsItemRegistration itemRegEntity, MmsRmg rmgEntity);

    public List<MmsItemRegistration> getItemInfo2(MmsItemRegistration itemRegistrationEntity);

    boolean checkItemInformationDuplication(MmsItemRegistration itemRegistrationEntity);

    public List<MmsItemRegistration> searchByAllParameters(MmsItemRegistration papmsItemRegistration);

    public List<MmsItemRegistration> searchByStoreAndItemName(MmsItemRegistration papmsItemRegistration);

    public List<MmsItemRegistration> searchByStoreAndItemCode(MmsItemRegistration papmsItemRegistration);

    public List<MmsItemRegistration> searchItemInfoByStore(MmsStoreInformation storeInfoEntity);

    public List<MmsItemRegistration> searchItemByStoreId(MmsItemRegistration itemRegEntity);

    List<MmsItemRegistration> searchMaterialInfoByStoreAndMatName(MmsItemRegistration papmsMaterialInformation);

    List<MmsItemRegistration> searchMaterialInfoByStoreAndMatNameAndItemGroup(MmsItemRegistration papmsMaterialInformation);

    public List<MmsItemRegistration> searchByStoreAndGlId(MmsItemRegistration papmsItemRegistration);

    public MmsItemRegistration searchByMaterialId(Integer materialId);

    public List<MmsItemRegistration> searchItemInfoByStoreAndPeparerId(MmsStoreInformation storeInfoEntity, MmsItemRegistration itemRegistration);

    public List<MmsItemRegistration> searchByStoreAndItemCodeAndPeparerId(MmsItemRegistration papmsItemRegistration);

    public List<MmsItemRegistration> searchByStoreAndItemNameAndPreparerId(MmsItemRegistration papmsItemRegistration);

    public List<MmsItemRegistration> searchByAllParametersAndPreparerId(MmsItemRegistration papmsItemRegistration);

    public List<MmsItemRegistration> searchAllByPreparerId(MmsItemRegistration papmsItemRegistration);

    public List<MmsItemRegistration> searchMaterialInfoByStoreAndMatNameAndItemGroupAndGLID(MmsItemRegistration papmsMaterialInformation);

    public List<MmsItemRegistration> findItemsNotRegisteredOnBincard(MmsStoreInformation storeInformation);

    public MmsItemRegistration searchByMaterialCode(String matCode);

    public List<String> getMmsItemRColumnNameList();

    public List<MmsItemRegistration> getIRListsByParameter(MmsItemRegistration papmsItemRegistration);

    public List<MmsItemRegistration> getItemListsByParameter(ColumnNameResolver columnNameResolver, MmsItemRegistration papmsItemRegistration, String columnValue);

    public int ConutBYItemType(String get);

    public List<String> findAllItemType();

}
