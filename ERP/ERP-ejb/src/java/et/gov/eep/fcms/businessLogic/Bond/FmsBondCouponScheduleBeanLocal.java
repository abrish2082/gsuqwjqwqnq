/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.businessLogic.Bond;

import java.util.List;
import javax.ejb.Local;
import et.gov.eep.fcms.entity.Bond.FmsBondCouponInterestPaymt;
import et.gov.eep.fcms.entity.Bond.FmsBondCouponSchedule;

/**
 *
 * @author mz
 */
@Local
public interface FmsBondCouponScheduleBeanLocal {

    public void Edit(FmsBondCouponSchedule couponSchedule);

    public FmsBondCouponSchedule getCouponSchedInfo(FmsBondCouponSchedule couponSchedule);

    public List<FmsBondCouponSchedule> getByStartAndEndDate(String date1, String date2, FmsBondCouponSchedule couponSchedule);

    public List<FmsBondCouponSchedule> findPaidPrincipalOfStartAndEndDate(String date1, String date2, FmsBondCouponSchedule couponSchedule, int PAID);

    public List<FmsBondCouponSchedule> getByBondCouponNo(FmsBondCouponSchedule couponSchedule);

    public List<FmsBondCouponSchedule> getNumberOfNotPaidStatus(String BondNo, int NOT_PAID);

    public List<FmsBondCouponSchedule> findPrincipalOfStartAndEndDate(String date1, String date2, String BondNo, int PAID);

    public List<FmsBondCouponInterestPaymt> getInterestOfStartAndEndDate(String date1, String date2, String BondNo, int PAID);

}
