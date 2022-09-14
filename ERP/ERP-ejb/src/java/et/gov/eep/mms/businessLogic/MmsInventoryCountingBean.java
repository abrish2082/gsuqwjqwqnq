
package et.gov.eep.mms.businessLogic;

import et.gov.eep.commonApplications.entity.ColumnNameResolver;
import et.gov.eep.mms.entity.MmsFixedAssetReturn;
import et.gov.eep.mms.entity.MmsInventoryCountDetail;
import et.gov.eep.mms.entity.MmsInventoryCounting;
import et.gov.eep.mms.mapper.MmsInventoryCountingFacade;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author kimmyo
 */
@Stateless
public class MmsInventoryCountingBean implements MmsInventoryCountingBeanLocal {

    @EJB
    MmsInventoryCountingFacade invCountFacade;

    /**
     *
     * @param inventoryCount
     */
    @Override
    public void create(MmsInventoryCounting inventoryCount) {
        invCountFacade.create(inventoryCount);
    }

    /**
     *
     * @param inventoryCount
     */
    @Override
    public void edit(MmsInventoryCounting inventoryCount) {
        invCountFacade.edit(inventoryCount);
    }

    /**
     *
     * @param invInformation
     * @return
     */
    @Override
    public ArrayList<MmsInventoryCounting> searchInventoryInformation(MmsInventoryCounting invInformation) {
        return invCountFacade.searchInventoryIdInformation(invInformation);
    }

    /**
     *
     * @param countInformation
     * @return
     */
    @Override
    public MmsInventoryCounting getMmsinvCountInformation(MmsInventoryCounting countInformation) {
        return invCountFacade.getMmsInventoryInformation(countInformation);
    }

    /**
     *
     * @param countInformation
     * @return
     */
    @Override
    public ArrayList<MmsInventoryCounting> getInventoryCountByYear(MmsInventoryCounting countInformation) {
        return invCountFacade.getInventoryCountByYear(countInformation);
    }

    /**
     *
     * @param inventoryInformation
     * @return
     */
    @Override
    public List<MmsInventoryCounting> searchByStoreAndBudgetYear(MmsInventoryCounting inventoryInformation) {
        return invCountFacade.searchByStoreAndBudgetYear(inventoryInformation);
    }

    @Override
    public List<MmsInventoryCounting> searchByStoreAndBudgetYearAndProcessedBy(MmsInventoryCounting inventoryInformation) {
        return invCountFacade.searchByStoreAndBudgetYearAndProcessedBy(inventoryInformation);
    }

    /**
     *
     * @param inventoryInformation
     * @return
     */
    @Override
    public List<MmsInventoryCountDetail> SearchItemCodeByYear(String inventoryInformation) {
        return invCountFacade.SearchMatcodeByYear(inventoryInformation);
    }

    /**
     *
     * @param name
     * @return
     */
    @Override
    public List<MmsInventoryCountDetail> getInventryCounting(String name) {
        return invCountFacade.getInventoryListByyear(name);
    }

    @Override
    public List<MmsInventoryCounting> SearchMmsinvCountInformation(MmsInventoryCounting countInformation) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public MmsInventoryCounting getLastInventoryNo() {
        return invCountFacade.getLastInvNO();
    }

    @Override
    public List<MmsInventoryCounting> searchByParameterInventoryNoAndStoreId(MmsInventoryCounting inventoryCountEntity) {
        return invCountFacade.searchByParameterInventoryNoAndStoreId(inventoryCountEntity);
    }

    @Override
    public List<MmsInventoryCounting> searchByParameterInventoryNoAndStoreIdAndProcessedBy(MmsInventoryCounting inventoryCountEntity) {
        return invCountFacade.searchByParameterInventoryNoAndStoreIdAndProcessedBy(inventoryCountEntity);
    }

    @Override
    public List<MmsInventoryCounting> SearchAllInventoryInfo() {
        return invCountFacade.findAll();
    }

    @Override
    public List<MmsInventoryCounting> searchByStoreId(MmsInventoryCounting inventoryCountEntity) {
        return invCountFacade.searchByStoreId(inventoryCountEntity);
    }

    @Override
    public List<MmsInventoryCounting> searchByStoreIdAndProcessedBy(MmsInventoryCounting inventoryCountEntity) {
        return invCountFacade.searchByStoreIdAndProcessedBy(inventoryCountEntity);
    }

    @Override
    public List<MmsInventoryCounting> searchByAllParameters(MmsInventoryCounting inventoryCountEntity) {
        return invCountFacade.searchByAllParameters(inventoryCountEntity);
    }

  

    @Override
    public List<MmsInventoryCounting> findInventoryCountingsListByWfStatus(int status) {
      return invCountFacade.findInventoryNumberListByWfStatus(status);
    }
@Override
    public List<MmsInventoryCounting> getCountingListsByParameter(MmsInventoryCounting inventoryCountEntity) {
        return invCountFacade.getCountingListsByParameter(inventoryCountEntity);
    }

    @Override
    public List<ColumnNameResolver> getColumnNameList() {
        return invCountFacade.getColumnNameList();
    }

    @Override
    public List<MmsInventoryCounting> getCountingListsByParameterForCheckOrApprove(MmsInventoryCounting inventoryCountEntity, int APPROVE_VALUE) {
       return invCountFacade.getCountingListsByParameterChekOrApprove(inventoryCountEntity, APPROVE_VALUE);
    }
}
