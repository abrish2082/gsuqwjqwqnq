/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.businessLogic.Bond;

import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import et.gov.eep.fcms.entity.Bond.FmsBondCouponInterestPaymt;
import et.gov.eep.fcms.mapper.Bond.FmsBondCouponInterestPaymtFacade;

/**
 *
 * @author mz
 */
@Stateless
public class FmsBondCouponInterestRepaymentBean implements FmsBondCouponInterestRepaymentBeanLocal {

    @EJB
    FmsBondCouponInterestPaymtFacade couponInterestRePayFacade;

    @Override
    public void Edit(FmsBondCouponInterestPaymt couponInterestRePayment) {
        couponInterestRePayFacade.edit(couponInterestRePayment);
    }

    @Override
    public void Create(FmsBondCouponInterestPaymt couponInterestRePayment) {
        couponInterestRePayFacade.create(couponInterestRePayment);
    }

    @Override
    public List<FmsBondCouponInterestPaymt> getInterestRepaymentList(String BondNo) {
        return couponInterestRePayFacade.getInterestRepaymentList(BondNo);
    }

    @Override
    public List<FmsBondCouponInterestPaymt> getInterestOfStartAndEndDate(String date1, String date2, String BondNo, int PAID) {
        return couponInterestRePayFacade.getInterestOfStartAndEndDate(date1, date2, BondNo, PAID);
    }

}
