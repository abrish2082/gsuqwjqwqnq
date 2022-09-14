/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.businessLogic.Bond;

import et.gov.eep.commonApplications.entity.ColumnNameResolver;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Local;
import et.gov.eep.fcms.entity.Bond.FmsBondCoupon;
import et.gov.eep.fcms.entity.Bond.FmsBondCouponInterestPaymt;
import et.gov.eep.fcms.entity.Bond.FmsBondCouponSchedule;

/**
 *
 * @author mz
 */
@Local
public interface FmsBondCouponBeanLocal {

    public void Create(FmsBondCoupon BondCoupon);

    public void Edit(FmsBondCoupon BondCoupon);

    public ArrayList<FmsBondCoupon> searchCouponId(FmsBondCoupon BondCoupon);

    public List<FmsBondCoupon> searchAll();

    public List<FmsBondCoupon> findByStartAndEndDate(String date1, String date2);

    public List<FmsBondCouponSchedule> getCouponScheduleList(String BondNo);

    public List<FmsBondCouponInterestPaymt> getInterestRepaymentList(String BondNo);

    public List<FmsBondCouponInterestPaymt> getInterestRepaymentBondNo(String date1, String date2, String BondNo);

    public List<FmsBondCouponInterestPaymt> getInterestOfStartAndEndDate(String date1, String date2, String BondNo, int PAID);

    public List<FmsBondCoupon> getBondCouponListsByParameter(ColumnNameResolver columnNameResolver, FmsBondCoupon BondCoupon, String columnValue);

    public List<String> getBondCouponInfColumnNameList();
}
