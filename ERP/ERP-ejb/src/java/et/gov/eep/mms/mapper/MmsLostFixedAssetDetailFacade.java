
package et.gov.eep.mms.mapper;

import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.mms.entity.MmsLostFixedAssetDetail;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author w_station
 */
@Stateless
public class MmsLostFixedAssetDetailFacade extends AbstractFacade<MmsLostFixedAssetDetail> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MmsLostFixedAssetDetailFacade() {
        super(MmsLostFixedAssetDetail.class);
    }

    public List<MmsLostFixedAssetDetail> fetchLostItems() {
        Query query = em.createNativeQuery("select * from MMS_LOST_FIXED_ASSET_DETAIL lost where lost.TAG_NO not in "
                + "( select off.TAG_NO from FMS_DPR_OFFICE_ASSET off where off.STATUS =2)", MmsLostFixedAssetDetail.class);
        try {
            List<MmsLostFixedAssetDetail> lostItemsList = query.getResultList();
            return lostItemsList;
        } catch (Exception ex) {
            throw ex;
        }
    }
    
    public MmsLostFixedAssetDetail getLostItem(MmsLostFixedAssetDetail mmsLostFixedAssetDetail) {
        Query query = em.createNamedQuery("MmsLostFixedAssetDetail.findByTagNo");
        query.setParameter("tagNo", mmsLostFixedAssetDetail.getTagNo());
        try {
            MmsLostFixedAssetDetail lostItem =(MmsLostFixedAssetDetail) query.getResultList().get(0);
            return lostItem;
        } catch (Exception ex) {
            throw ex;
        }
    }
}
