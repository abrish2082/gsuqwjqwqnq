/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.businessLogic.Bond;

import et.gov.eep.commonApplications.entity.ColumnNameResolver;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import et.gov.eep.fcms.entity.Bond.FmsBondCoupon;
import et.gov.eep.fcms.entity.Bond.FmsBondCouponInterestPaymt;
import et.gov.eep.fcms.entity.Bond.FmsBondCouponSchedule;
import et.gov.eep.fcms.mapper.Bond.FmsBondCouponFacade;

/**
 *
 * @author mz
 */
@Stateless
public class FmsBondCouponBean implements FmsBondCouponBeanLocal {

    @EJB
    FmsBondCouponFacade couponFacade;

    @Override
    public void Create(FmsBondCoupon BondCoupon) {
        couponFacade.create(BondCoupon);
    }

    @Override
    public void Edit(FmsBondCoupon BondCoupon) {
        couponFacade.edit(BondCoupon);
    }

    @Override
    public ArrayList<FmsBondCoupon> searchCouponId(FmsBondCoupon BondCoupon) {
        return couponFacade.searchCouponId(BondCoupon);
    }

    @Override
    public List<FmsBondCoupon> searchAll() {
        return couponFacade.findAll();
    }

    @Override
    public List<FmsBondCoupon> findByStartAndEndDate(String date1, String date2) {
        return couponFacade.findByStartAndEndDate(date1, date2);
    }

    @Override
    public List<FmsBondCouponSchedule> getCouponScheduleList(String BondNo) {

        return couponFacade.getCouponScheduleList(BondNo);
    }

    @Override
    public List<FmsBondCouponInterestPaymt> getInterestRepaymentList(String BondNo) {
        return couponFacade.getInterestRepaymentList(BondNo);
    }

    @Override
    public List<FmsBondCouponInterestPaymt> getInterestOfStartAndEndDate(String date1, String date2, String BondNo, int PAID) {
        return couponFacade.getInterestOfStartAndEndDate(date1, date2, BondNo, PAID);
    }

    @Override
    public List<FmsBondCouponInterestPaymt> getInterestRepaymentBondNo(String date1, String date2, String BondNo) {
        return couponFacade.getInterestRepaymentBondNo(date1, date2, BondNo);
    }

    @Override
    public List<FmsBondCoupon> getBondCouponListsByParameter(ColumnNameResolver columnNameResolver, FmsBondCoupon BondCoupon, String columnValue) {
        return couponFacade.getBondCouponListsByParameter(columnNameResolver, BondCoupon, columnValue);
    }

    @Override
    public List<String> getBondCouponInfColumnNameList() {
        return couponFacade.getBondCouponInfColumnNameList();
    }
}
