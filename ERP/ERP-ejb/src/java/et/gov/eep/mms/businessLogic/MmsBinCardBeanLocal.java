/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.mms.businessLogic;

import et.gov.eep.commonApplications.entity.ColumnNameResolver;
import java.util.List;
import javax.ejb.Local;
import et.gov.eep.mms.entity.MmsBinCard;
import et.gov.eep.mms.entity.MmsItemRegistration;
import et.gov.eep.mms.entity.MmsStoreInformation;

/**
 *
 * @author Minab
 */
@Local
public interface MmsBinCardBeanLocal {

    void create(MmsBinCard bincard);

    void edit(MmsBinCard edit);

    public List<MmsBinCard> searchBinCardInfoByStore(MmsStoreInformation storeEntity);

    public List<MmsBinCard> searchByStoreAndItemCode(MmsItemRegistration itemRegistrationEntity, MmsBinCard binCard);

    public List<MmsBinCard> searchByStoreAndItemName(MmsItemRegistration itemRegistrationEntity, MmsBinCard binCard);

    public List<MmsBinCard> searchByAllParameters(MmsItemRegistration itemRegistrationEntity, MmsBinCard binCard);

    public MmsBinCard SearchInitalQuantityByMatId(MmsItemRegistration papmsItemRegistration);

    public List<MmsBinCard> getItemCodeByStoreAndItemGroup(MmsItemRegistration itemRegistrationEntity);

    public List<MmsBinCard> getitemCode(MmsBinCard binCardEntity);

    public MmsBinCard getItemInfoByItemId(MmsItemRegistration itemRegistration);

    public MmsBinCard getItemInfoByStoreIdAndItemId(MmsItemRegistration itemRegistration, MmsStoreInformation storeInformation);

    public int deductFromBinCard(int storeId, String srNo);

    public int incrementBinCardQuantityForGrn(String GrnNo);

    public int incrementBinCardQuantityForISIV(String isivNo);

    public MmsBinCard findItemByMatId(int matId);

    public List<String> getMmsBinColumnNameList();

    public List<MmsBinCard> searchByCol_NameAndCol_Value(ColumnNameResolver columnNameResolver, MmsBinCard binCardEntity,String columnValue);
}
