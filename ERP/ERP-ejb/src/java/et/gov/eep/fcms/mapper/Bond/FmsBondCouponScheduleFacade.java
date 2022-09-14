package et.gov.eep.fcms.mapper.Bond;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.fcms.entity.Bond.FmsBondCouponInterestPaymt;
import et.gov.eep.fcms.entity.Bond.FmsBondCouponSchedule;

/**
 *
 * @author mz
 */
@Stateless
public class FmsBondCouponScheduleFacade extends AbstractFacade<FmsBondCouponSchedule> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public FmsBondCouponScheduleFacade() {
        super(FmsBondCouponSchedule.class);
    }
 
/*named query to select couponSchedule from FmsBondCouponSchedule using couponScheduleId*/
    public FmsBondCouponSchedule getCouponSchedInfo(FmsBondCouponSchedule couponSchedule) {
        Query q = em.createNamedQuery("FmsBondCouponSchedule.findByCouponScheduleId");
        q.setParameter("couponScheduleId", couponSchedule.getCouponScheduleId());
        FmsBondCouponSchedule schedule = new FmsBondCouponSchedule();
        if (q.getResultList().size() > 0) {
            schedule = (FmsBondCouponSchedule) q.getResultList().get(0);
        }
        return schedule;
    }
   
/*named query to select couponScheduleList from FmsBondCouponSchedule using couponId*/
    public List<FmsBondCouponSchedule> getByBondCouponNo(FmsBondCouponSchedule couponSchedule) {
        Query q = em.createNamedQuery("FmsBondCouponSchedule.findByBondCouponNo");
        q.setParameter("couponId", couponSchedule.getBondCouponNo().getCouponId());
        List<FmsBondCouponSchedule> couponScheduleList = new ArrayList<>();
        if (q.getResultList().size() > 0) {
            couponScheduleList = q.getResultList();
        }
        return couponScheduleList;
    }
    
    /*native query to select all couponScheduleList from FmsBondCouponSchedule by passing Bond number and status 
    return all*/
    public List<FmsBondCouponSchedule> getNumberOfNotPaidStatus(String BondNo, int NOT_PAID) {
        Query q = em.createNativeQuery("SELECT * FROM fms_Bond_coupon_schedule where status='" + NOT_PAID + "' and Bond_no='" + BondNo + "'", FmsBondCouponSchedule.class);
        List<FmsBondCouponSchedule> unPaidCouponList = new ArrayList<>();
        if (q.getResultList().size() > 0) {
            unPaidCouponList = q.getResultList();
        }
        return unPaidCouponList;
    }

        /*native query to select all couponScheduleList from FmsBondCouponSchedule by passing PAYMENT_DATE BETWEEN(date1 and date2) and status 
    return all value*/
    public List<FmsBondCouponSchedule> findPrincipalOfStartAndEndDate(String date1, String date2, String BondNo, int PAID) {
        Query query = em.createNativeQuery("SELECT * FROM FMS_BOND_COUPON_SCHEDULE B where B.Bond_No='" + BondNo + "'"
                + "AND B.PAYMENT_DATE BETWEEN '" + date1 + "' AND '" + date2 + "'" + "AND status='" + PAID + "'", FmsBondCouponSchedule.class);
        List<FmsBondCouponSchedule> couponSchedulePaidList = new ArrayList<>();
        if (query.getResultList().size() > 0) {
            couponSchedulePaidList = query.getResultList();
        }
        return couponSchedulePaidList;
    }

     /*native query to select all couponScheduleList from FmsBondCouponSchedule by passing PAYMENT_DATE BETWEEN(date1 and date2), Bond number and status 
    return all value*/
    public List<FmsBondCouponInterestPaymt> getInterestOfStartAndEndDate(String date1, String date2, String BondNo, int PAID) {
        Query query = em.createNativeQuery("SELECT * FROM FMS_BOND_COUPON_INTEREST_PAYMT B where B.BOND_NO='" + BondNo + "'"
                + "AND B.PAYMENT_DATE BETWEEN '" + date1 + "' AND '" + date2 + "'" + "AND STATUS='" + PAID + "'", FmsBondCouponSchedule.class);
        List<FmsBondCouponInterestPaymt> couponInterstPaidList = new ArrayList<>();
        if (query.getResultList().size() > 0) {
            couponInterstPaidList = query.getResultList();
        }
        return couponInterstPaidList;
    }

     /*native query to select all couponScheduleList from FmsBondCouponSchedule by passing INSTALLMENT_DUE_DATE BETWEEN(date1 and date2)and coupon schedule object
    return all value*/
    public List<FmsBondCouponSchedule> getByStartAndEndDate(String date1, String date2, FmsBondCouponSchedule couponSchedule) {
        Query query = em.createNativeQuery("SELECT * FROM FMS_BOND_COUPON_SCHEDULE B where B.BOND_COUPON_NO ='" + couponSchedule.getBondCouponNo().getCouponId() + "'"
                + "AND B.INSTALLMENT_DUE_DATE BETWEEN '" + date1 + "' AND '" + date2 + "'", FmsBondCouponSchedule.class);
        try {
            List<FmsBondCouponSchedule> couponScheduleLists = new ArrayList<>();
            if (query.getResultList().size() > 0) {
                couponScheduleLists = query.getResultList();
            }
            return couponScheduleLists;

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

     /*native query to select all couponScheduleList from FmsBondCouponSchedule by passing PAYMENT_DATE BETWEEN(date1 and date2),
    coupon schedule object and status return all value*/
    public List<FmsBondCouponSchedule> findPaidPrincipalOfStartAndEndDate(String date1, String date2, FmsBondCouponSchedule couponSchedule, int PAID) {

        Query query = em.createNativeQuery("SELECT * FROM FMS_BOND_COUPON_SCHEDULE B where B.BOND_COUPON_NO ='" + couponSchedule.getBondCouponNo().getCouponId() + "'"
                + "AND B.PAYMENT_DATE BETWEEN '" + date1 + "' AND '" + date2 + "'" + "AND status='" + PAID + "' ", FmsBondCouponSchedule.class);
        List<FmsBondCouponSchedule> couponScheduleList = new ArrayList<>();
        if (query.getResultList().size() > 0) {
            couponScheduleList = query.getResultList();
        }
        return couponScheduleList;
    }
}
