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
import et.gov.eep.fcms.entity.Bond.FmsBondCouponSchedule;
import et.gov.eep.fcms.mapper.Bond.FmsBondCouponScheduleFacade;

/**
 *
 * @author mz
 */
@Stateless
public class FmsBondCouponScheduleBean implements FmsBondCouponScheduleBeanLocal {

    @EJB
    FmsBondCouponScheduleFacade couponScheduleFacade;

    @Override
    public void Edit(FmsBondCouponSchedule couponSchedule) {
        couponScheduleFacade.edit(couponSchedule);
    }

    @Override
    public List<FmsBondCouponSchedule> getByStartAndEndDate(String date1, String date2, FmsBondCouponSchedule couponSchedule) {
        return couponScheduleFacade.getByStartAndEndDate(date1, date2, couponSchedule);
    }

    @Override
    public List<FmsBondCouponSchedule> getByBondCouponNo(FmsBondCouponSchedule couponSchedule) {
        return couponScheduleFacade.getByBondCouponNo(couponSchedule);
    }

    @Override
    public List<FmsBondCouponSchedule> getNumberOfNotPaidStatus(String BondNo, int NOT_PAID) {
        return couponScheduleFacade.getNumberOfNotPaidStatus(BondNo, NOT_PAID);
    }

    @Override
    public List<FmsBondCouponSchedule> findPaidPrincipalOfStartAndEndDate(String date1, String date2, FmsBondCouponSchedule couponSchedule, int PAID) {
        return couponScheduleFacade.findPaidPrincipalOfStartAndEndDate(date1, date2, couponSchedule, PAID);
    }

    @Override
    public FmsBondCouponSchedule getCouponSchedInfo(FmsBondCouponSchedule couponSchedule) {
        return couponScheduleFacade.getCouponSchedInfo(couponSchedule);
    }

    @Override
    public List<FmsBondCouponSchedule> findPrincipalOfStartAndEndDate(String date1, String date2, String BondNo, int PAID) {
        return couponScheduleFacade.findPrincipalOfStartAndEndDate(date1, date2, BondNo, PAID);
    }

    @Override
    public List<FmsBondCouponInterestPaymt> getInterestOfStartAndEndDate(String date1, String date2, String BondNo, int PAID) {
        return couponScheduleFacade.getInterestOfStartAndEndDate(date1, date2, BondNo, PAID);
    }
}
