package et.gov.eep.fcms.mapper.Bond;

import et.gov.eep.commonApplications.entity.ColumnNameResolver;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.fcms.entity.Bond.FmsBondCoupon;
import et.gov.eep.fcms.entity.Bond.FmsBondCouponInterestPaymt;
import et.gov.eep.fcms.entity.Bond.FmsBondCouponSchedule;

/**
 *
 * @author mz
 */
@Stateless
public class FmsBondCouponFacade extends AbstractFacade<FmsBondCoupon> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public FmsBondCouponFacade() {
        super(FmsBondCoupon.class);
    }

    /*searching Bond coupon using coupon id*/
    public ArrayList<FmsBondCoupon> searchCouponId(FmsBondCoupon BondCoupon) {
        Query query = em.createNamedQuery("FmsBondCoupon.findByCouponId");
        query.setParameter("couponId", BondCoupon.getCouponId());
        try {
            ArrayList<FmsBondCoupon> CouponList = new ArrayList(query.getResultList());
            return CouponList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;

        }
    }

    /*searching Bond coupon using Bond number*/
    public List<FmsBondCouponSchedule> getCouponScheduleList(String BondNo) {
        Query query = em.createNamedQuery("FmsBondCouponSchedule.findByBondNo");
        query.setParameter("BondNo", BondNo);
        List<FmsBondCouponSchedule> couponList = new ArrayList<>();
        if (query.getResultList().size() > 0) {
            couponList = query.getResultList();

        }
        return couponList;
    }

    /*searching coupon interest payment table using boond number*/
    public List<FmsBondCouponInterestPaymt> getInterestRepaymentList(String BondNo) {
        Query query = em.createNamedQuery("FmsBondCouponInterestPaymt.findByBondNo", FmsBondCoupon.class);
        query.setParameter("BondNo", BondNo);
        List<FmsBondCouponInterestPaymt> CouponInterestPaymtList = new ArrayList<>();
        if (query.getResultList().size() > 0) {
            CouponInterestPaymtList = query.getResultList();
        }
        return CouponInterestPaymtList;
    }

    /*searching Paid Interest Of Payment Date from coupon interest payment table by passing payment date (from date, to date), and Bond number*/
    public List<FmsBondCouponInterestPaymt> getInterestRepaymentBondNo(String date1, String date2, String BondNo) {
        Query query = em.createNamedQuery("FmsBondCouponInterestPaymt.findByPaidInterestOfPayDate", FmsBondCouponInterestPaymt.class);
        query.setParameter("BondNo", BondNo);
        query.setParameter("date1", date1);
        query.setParameter("date2", date2);
        List<FmsBondCouponInterestPaymt> CouponInterestPaymtList = new ArrayList<>();
        if (query.getResultList().size() > 0) {
            CouponInterestPaymtList = query.getResultList();
        }
        return CouponInterestPaymtList;
    }

    /*natve query for searching coupon interest payment table by passing  payment date (from date, to date), Bond number, and status which is paid*/
    public List<FmsBondCouponInterestPaymt> getInterestOfStartAndEndDate(String date1, String date2, String BondNo, int PAID) {
        Query query = em.createNativeQuery("SELECT * FROM FMS_BOND_COUPON_INTEREST_PAYMT B where B.BOND_NO='" + BondNo + "'"
                + "AND B.PAYMENT_DATE BETWEEN '" + date1 + "' AND '" + date2 + "'" + "AND STATUS='" + PAID + "'", FmsBondCoupon.class);
        List<FmsBondCouponInterestPaymt> couponInterstPaidList = new ArrayList<>();
        if (query.getResultList().size() > 0) {
            couponInterstPaidList = query.getResultList();
        }
        return couponInterstPaidList;
    }

    /*native query for searching Bond coupon by passing interest payment satrt and end date */
    public List<FmsBondCoupon> findByStartAndEndDate(String date1, String date2) {
        Query query = em.createNativeQuery("SELECT * FROM FMS_BOND_COUPON B where B.PRINCIPAL_REPAYMENT_START_DATE BETWEEN '" + date1 + "' AND '" + date2 + "'", FmsBondCoupon.class);
        try {
            List<FmsBondCoupon> BondCouponList = new ArrayList<>();
            if (query.getResultList().size() > 0) {
                BondCouponList = query.getResultList();
            }
            return BondCouponList;

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<String> getBondCouponInfColumnNameList() {
        Query query = em.createNativeQuery("SELECT column_name\n"
                + "   FROM USER_TAB_COLUMNS\n"
                + "   WHERE table_name = UPPER('FMS_BOND_COUPON')\n"
                + "   and COLUMN_NAME NOT IN ('COUPON_ID','INTEREST_RATE')\n"
                + "   ORDER BY column_name ASC");
        try {
            List<String> colNameLists = new ArrayList<>();
            colNameLists = query.getResultList();
            return colNameLists;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }

    public List<FmsBondCoupon> getBondCouponListsByParameter(ColumnNameResolver columnNameResolver, FmsBondCoupon BondCoupon, String columnValue) {
        System.out.println("columnNameResolver.getCol_Name_FromTable()==" + columnNameResolver.getCol_Name_FromTable());
        System.out.println("Col_Value==" + columnValue);
        List<FmsBondCoupon> colValueLists = new ArrayList<>();

        if (BondCoupon.getColumnName() != null && !BondCoupon.getColumnName().equals("")
                && BondCoupon.getColumnValue() != null && !BondCoupon.getColumnValue().equals("")) {
            System.out.println("when if");

            Query query = em.createNativeQuery("SELECT * FROM FMS_BOND_COUPON\n"
                    + "   WHERE " + BondCoupon.getColumnName().toLowerCase() + "='" + BondCoupon.getColumnValue() + "'", FmsBondCoupon.class);
            try {
                if (query.getResultList().size() > 0) {
                    colValueLists = query.getResultList();
                }
                return colValueLists;
            } catch (Exception e) {
                e.getMessage();
                return null;
            }
        } else {
            System.out.println("In else " + colValueLists.size());
            Query query = em.createNamedQuery("FmsBondCoupon.findAll");
            colValueLists = query.getResultList();
            System.out.println("list of size " + colValueLists.size());
            return colValueLists;
        }
    }
}
