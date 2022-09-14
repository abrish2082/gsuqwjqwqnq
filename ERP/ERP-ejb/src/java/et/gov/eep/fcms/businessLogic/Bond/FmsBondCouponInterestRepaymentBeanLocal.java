/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.businessLogic.Bond;

import java.util.List;
import javax.ejb.Local;
import et.gov.eep.fcms.entity.Bond.FmsBondCouponInterestPaymt;

/**
 *
 * @author mz
 */
@Local
public interface FmsBondCouponInterestRepaymentBeanLocal {

    public void Create(FmsBondCouponInterestPaymt couponInterestRePayment);

    public void Edit(FmsBondCouponInterestPaymt couponInterestRePayment);

    public List<FmsBondCouponInterestPaymt> getInterestRepaymentList(String BondNo);

    public List<FmsBondCouponInterestPaymt> getInterestOfStartAndEndDate(String date1, String date2, String BondNo, int PAID);

}
