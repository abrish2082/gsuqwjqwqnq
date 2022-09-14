
package et.gov.eep.mms.mapper;

import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.mms.entity.MmsInventoryCountDetail;
import et.gov.eep.mms.entity.MmsInventoryCounting;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author kimmyo
 */
@Stateless
public class MmsInventoryCountDetailFacade extends AbstractFacade<MmsInventoryCountDetail> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    /**
     *
     */
    public MmsInventoryCountDetailFacade() {
        super(MmsInventoryCountDetail.class);
    }

    /**
     *
     * @param countInformation
     * @return
     */
    public MmsInventoryCountDetail getMmsInventoryInformation(MmsInventoryCountDetail countInformation) {

        Query query = em.createNamedQuery("MmsInventoryCountDetail.findByItemCode", MmsInventoryCounting.class);
        query.setParameter("item_code", countInformation.getItemCode());
        try {
            MmsInventoryCountDetail importationInfo = (MmsInventoryCountDetail) query.getResultList().get(0);
            return importationInfo;
        } catch (Exception ex) {
            return null;
        }
    }

    public List<MmsInventoryCountDetail> getInventoryInfoJoinedWithBinCard(MmsInventoryCounting inventoryCountEntity) {
        try{
        Query query = em.createNativeQuery("SELECT icd.*,\n"
                + "  bc.material_id,\n"
                + "  bc.store_id,\n"
                + "  bc.current_quantity,\n"
                + "  ic.inventory_count_no,\n"
                + "  ic.work_unit,\n"
                + "  bc.current_quantity - icd.quantity AS differnce\n"
                + "FROM mms_inventory_count_detail icd\n"
                + "INNER JOIN mms_inventory_counting ic\n"
                + "ON ic.inventory_count_id= icd.inventory_count_id\n"
                + "INNER JOIN mms_bin_card bc\n"
                + "ON bc.material_id= icd.item_code\n"
                + "WHERE ic.inventory_count_id Like '" + inventoryCountEntity.getInventoryCountId() + "%'", MmsInventoryCountDetail.class);
         ArrayList<MmsInventoryCountDetail> listOf = new ArrayList<>(query.getResultList());
            return listOf;
        } catch (Exception ex) {
            return null;

        }
        
    }
}
