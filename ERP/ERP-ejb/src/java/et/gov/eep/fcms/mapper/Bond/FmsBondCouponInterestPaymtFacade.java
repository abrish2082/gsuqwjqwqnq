/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.mapper.Bond;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.fcms.entity.Bond.FmsBondCouponInterestPaymt;

/**
 *
 * @author mz
 */
@Stateless
public class FmsBondCouponInterestPaymtFacade extends AbstractFacade<FmsBondCouponInterestPaymt> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public FmsBondCouponInterestPaymtFacade() {
        super(FmsBondCouponInterestPaymt.class);
    }

    /*searching from coupon interest payment for CouponInterestPaymtList using Bond number */
    public List<FmsBondCouponInterestPaymt> getInterestRepaymentList(String BondNo) {
        Query query = em.createNamedQuery("FmsBondCouponInterestPaymt.findByBondNo", FmsBondCouponInterestPaymt.class);
        query.setParameter("BondNo", BondNo);
        List<FmsBondCouponInterestPaymt> CouponInterestPaymtList = new ArrayList<>();
        if (query.getResultList().size() > 0) {
            CouponInterestPaymtList = query.getResultList();
        }
        return null;

    }

    /*native query to select from coupon interest payment by passing repayment start and end date, Bond number status (paid)*/
    public List<FmsBondCouponInterestPaymt> getInterestOfStartAndEndDate(String date1, String date2, String BondNo, int PAID) {
        Query query = em.createNativeQuery("SELECT * FROM FMS_BOND_COUPON_INTEREST_PAYMT  where Bond_No='" + BondNo + "'"
                + "AND PAYMENT_DATE BETWEEN '" + date1 + "' AND '" + date2 + "'" + "AND STATUS='" + PAID + "'", FmsBondCouponInterestPaymt.class);
        List<FmsBondCouponInterestPaymt> couponInterstPaidList = new ArrayList<>();
        if (query.getResultList().size() > 0) {
            couponInterstPaidList = query.getResultList();
        }
        return couponInterstPaidList;
    }
}
