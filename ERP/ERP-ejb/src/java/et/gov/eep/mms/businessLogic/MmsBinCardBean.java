/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.mms.businessLogic;

import et.gov.eep.commonApplications.entity.ColumnNameResolver;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import et.gov.eep.mms.entity.MmsBinCard;
import et.gov.eep.mms.entity.MmsItemRegistration;
import et.gov.eep.mms.entity.MmsStoreInformation;
import et.gov.eep.mms.mapper.MmsBinCardFacade;

/**
 *
 * @author Minab
 */
@Stateless
public class MmsBinCardBean implements MmsBinCardBeanLocal {

    @EJB
    private MmsBinCardFacade bincardInterface;

    @Override
    public void create(MmsBinCard bincard) {
        bincardInterface.create(bincard);
    }

    @Override
    public void edit(MmsBinCard edit) {
        bincardInterface.edit(edit);
    }

    @Override
    public List<MmsBinCard> searchBinCardInfoByStore(MmsStoreInformation storeEntity) {
        return bincardInterface.searchBinCardInfoByStore(storeEntity);
    }

    @Override
    public List<MmsBinCard> searchByStoreAndItemCode(MmsItemRegistration itemRegistrationEntity, MmsBinCard binCard) {
        return bincardInterface.getByItemCodeAndStoreId(itemRegistrationEntity, binCard);
    }

    @Override
    public List<MmsBinCard> searchByStoreAndItemName(MmsItemRegistration itemRegistrationEntity, MmsBinCard binCard) {
        return bincardInterface.getByItemNameAndStoreId(itemRegistrationEntity, binCard);
    }

    @Override
    public List<MmsBinCard> searchByAllParameters(MmsItemRegistration itemRegistrationEntity, MmsBinCard binCard) {
        return bincardInterface.getByAllParameters(itemRegistrationEntity, binCard);
    }

    @Override
    public MmsBinCard SearchInitalQuantityByMatId(MmsItemRegistration papmsItemRegistration) {
        return bincardInterface.SearchInitalQuantityByMatId(papmsItemRegistration);
    }

    @Override
    public List<MmsBinCard> getItemCodeByStoreAndItemGroup(MmsItemRegistration itemRegistrationEntity) {
        return bincardInterface.getItemCodeByStoreAndItemGroup(itemRegistrationEntity);
    }

    @Override
    public List<MmsBinCard> getitemCode(MmsBinCard binCardEntity) {
        return bincardInterface.getitemCode(binCardEntity);
    }

    @Override
    public MmsBinCard getItemInfoByItemId(MmsItemRegistration itemRegistration) {
        return bincardInterface.getItemInfoByItemId(itemRegistration);
    }

    @Override
    public MmsBinCard getItemInfoByStoreIdAndItemId(MmsItemRegistration itemRegistration, MmsStoreInformation storeInformation) {
        return bincardInterface.getItemInfoByStoreIdAndItemId(itemRegistration, storeInformation);
    }

    @Override
    public int deductFromBinCard(int storeId, String srNo) {
        return bincardInterface.deductFromBinCard(storeId, srNo);
    }

    @Override
    public int incrementBinCardQuantityForGrn(String GrnNo) {
        return bincardInterface.incrementBinCardQuantityForGrn(GrnNo);
    }

    @Override
    public int incrementBinCardQuantityForISIV(String ISIVnO) {
        return bincardInterface.incrementBinCardQuantityForISIV(ISIVnO);
    }

    @Override
    public MmsBinCard findItemByMatId(int matId) {
        return bincardInterface.findItemByMatId(matId);
    }

    
    @Override
    public List<String> getMmsBinColumnNameList() {
        return bincardInterface.getMmsBinColumnNameList();
    }

    @Override
    public List<MmsBinCard> searchByCol_NameAndCol_Value(ColumnNameResolver columnNameResolver, MmsBinCard binCardEntity, String columnValue) {
        return bincardInterface.searchByCol_NameAndCol_Value(columnNameResolver, binCardEntity, columnValue);
    }
}
