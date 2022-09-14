
package et.gov.eep.mms.businessLogic;

import et.gov.eep.commonApplications.entity.ColumnNameResolver;
import et.gov.eep.mms.entity.MmsFixedAssetReturn;
import et.gov.eep.mms.entity.MmsInventoryCountDetail;
import et.gov.eep.mms.entity.MmsInventoryCounting;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author kimmyo
 */
@Local
public interface MmsInventoryCountingBeanLocal {

    /**
     *
     * @param inventoryCount
     */
    void create(MmsInventoryCounting inventoryCount);

    /**
     *
     * @param invInformation
     * @return
     */
    ArrayList<MmsInventoryCounting> searchInventoryInformation(MmsInventoryCounting invInformation);

    /**
     *
     * @param inventoryCount
     */
    void edit(MmsInventoryCounting inventoryCount );
    

    /**
     *
     * @param countInformation
     * @return
     */
    MmsInventoryCounting getMmsinvCountInformation(MmsInventoryCounting countInformation);
    
   public List< MmsInventoryCounting> SearchMmsinvCountInformation(MmsInventoryCounting countInformation);

    /**
     *
     * @param countInformation
     * @return
     */
    public ArrayList<MmsInventoryCounting> getInventoryCountByYear(MmsInventoryCounting countInformation);

    /**
     *
     * @param name
     * @return
     */
    public List<MmsInventoryCountDetail> getInventryCounting(String name);

    /**
     *
     * @param inventoryInformation
     * @return
     */
    List<MmsInventoryCounting> searchByStoreAndBudgetYear(MmsInventoryCounting inventoryInformation);

    /**
     *
     * @param inventoryInformation
     * @return
     */
    List<MmsInventoryCountDetail>SearchItemCodeByYear(String inventoryInformation);
    public MmsInventoryCounting getLastInventoryNo();

    public List<MmsInventoryCounting> searchByParameterInventoryNoAndStoreId(MmsInventoryCounting inventoryCountEntity);

    public List<MmsInventoryCounting> SearchAllInventoryInfo();

    public List<MmsInventoryCounting> searchByStoreId(MmsInventoryCounting inventoryCountEntity);

    public List<MmsInventoryCounting> searchByAllParameters(MmsInventoryCounting inventoryCountEntity);

    public List<MmsInventoryCounting> searchByStoreIdAndProcessedBy(MmsInventoryCounting inventoryCountEntity);

    public List<MmsInventoryCounting> searchByParameterInventoryNoAndStoreIdAndProcessedBy(MmsInventoryCounting inventoryCountEntity);

    public List<MmsInventoryCounting> searchByStoreAndBudgetYearAndProcessedBy(MmsInventoryCounting inventoryInformation);
    
    public List<MmsInventoryCounting> findInventoryCountingsListByWfStatus(int status);
        
    public List<MmsInventoryCounting> getCountingListsByParameter(MmsInventoryCounting inventoryCountEntity);

    public List<ColumnNameResolver> getColumnNameList();

    public List<MmsInventoryCounting> getCountingListsByParameterForCheckOrApprove(MmsInventoryCounting inventoryCountEntity, int APPROVE_VALUE);

}
