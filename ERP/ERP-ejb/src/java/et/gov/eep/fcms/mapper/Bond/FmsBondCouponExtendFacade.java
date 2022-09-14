package et.gov.eep.fcms.mapper.Bond;

import java.util.ArrayList;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.fcms.entity.Bond.FmsBondCouponExtend;

/**
 *
 * @author mz
 */
@Stateless
public class FmsBondCouponExtendFacade extends AbstractFacade<FmsBondCouponExtend> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public FmsBondCouponExtendFacade() {
        super(FmsBondCouponExtend.class);
    }
    /*named qurey for search fromcoupon extend using the newly extended serial number */

    public ArrayList<FmsBondCouponExtend> searchCouponExtendId(FmsBondCouponExtend couponExtend) {
        Query query = em.createNamedQuery("FmsBondCouponExtend.findByNewSerialNo");
        query.setParameter("newSerialNo", couponExtend.getNewSerialNo());
        try {
            ArrayList<FmsBondCouponExtend> CouponextendList = new ArrayList(query.getResultList());
            return CouponextendList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;

        }
    }
}
