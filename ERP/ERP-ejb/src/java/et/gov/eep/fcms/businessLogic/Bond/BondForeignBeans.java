/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.businessLogic.Bond;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import et.gov.eep.commonApplications.entity.ComLuCountry;
import et.gov.eep.fcms.entity.Bond.FmsBondForeign;
import et.gov.eep.fcms.mapper.Bond.FmsBondForeignFacade;

/**
 *
 * @author mora
 */
@Stateless
public class BondForeignBeans implements BondForeignBeanLocals {

    @EJB
    FmsBondForeignFacade foreignFacade;

    @Override
    public void Create(FmsBondForeign BondForeign) {
        foreignFacade.saveOrUpdate(BondForeign);
    }

    @Override
    public void Eidt(FmsBondForeign BondForeign) {
        foreignFacade.edit(BondForeign);
    }

    @Override
    public ArrayList<FmsBondForeign> searchBondId(FmsBondForeign foreign) {
        return foreignFacade.searchFmsBondId(foreign);
    }

    @Override
    public FmsBondForeign BondForeigninfo(FmsBondForeign BondForeign) {
        return foreignFacade.FmsBondIdinfo(BondForeign); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<FmsBondForeign> searchName(FmsBondForeign foreign) {
        return foreignFacade.searchName(foreign);
    }

    @Override
    public List<FmsBondForeign> searchAll() {
        return foreignFacade.findAll();
    }

    @Override
    public List<FmsBondForeign> searchFmsBondgroup() {
        return foreignFacade.searchFmsBondgroup();
    }

    @Override
    public FmsBondForeign searchStatus(FmsBondForeign BondForeign) {
        return foreignFacade.SearchStatus(BondForeign);

    }

    @Override
    public ArrayList<FmsBondForeign> searchCountry(FmsBondForeign BondForeign) {
        return foreignFacade.searchCountry(BondForeign);
    }

    @Override
    public List<FmsBondForeign> searchCountryName() {
        return foreignFacade.searchCountryName();
    }

    @Override
    public List<FmsBondForeign> getByCountryId(Date fromIssueDate, Date toIssueDate) {
        return foreignFacade.getByCountryId(fromIssueDate, toIssueDate);
    }

    @Override
    public List<FmsBondForeign> getByCountryId1(String fromIssueDate, String toIssueDate, ComLuCountry luCountry) {
        return foreignFacade.getByCountryId1(fromIssueDate, toIssueDate, luCountry);
    }

    @Override
    public List<FmsBondForeign> findBySerialNo(FmsBondForeign BondForeign) {
        return foreignFacade.findBySerialNo(BondForeign);
    }

    @Override
    public ArrayList<FmsBondForeign> findBySerialNameAmountAndCurency(FmsBondForeign BondForeign) {
        return foreignFacade.findBySerialNameAmountAndCurency(BondForeign);
    }

    @Override
    public ArrayList<FmsBondForeign> findBySerialAmountAndCurency(FmsBondForeign BondForeign) {
        return foreignFacade.findBySerialAmountAndCurency(BondForeign);
    }

    @Override
    public ArrayList<FmsBondForeign> findByNameAmountAndCurency(FmsBondForeign BondForeign) {
        return foreignFacade.findByNameAmountAndCurency(BondForeign);
    }

    @Override
    public ArrayList<FmsBondForeign> findByAmountAndCurency(FmsBondForeign BondForeign) {
        return foreignFacade.findByAmountAndCurency(BondForeign);
    }

    @Override
    public ArrayList<FmsBondForeign> findByBondCurrency(FmsBondForeign BondForeign) {
        return foreignFacade.findByBondCurrency(BondForeign);
    }

    @Override
    public ArrayList<FmsBondForeign> findByBondAmount(FmsBondForeign BondForeign) {
        return foreignFacade.findByAmount(BondForeign);
    }

    @Override
    public ArrayList<FmsBondForeign> findByNameAndCurrency(FmsBondForeign BondForeign) {
        return foreignFacade.findByNameAndCurrency(BondForeign);
    }

    @Override
    public ArrayList<FmsBondForeign> findBySerialAndCurrency(FmsBondForeign BondForeign) {
        return foreignFacade.findBySerialAndCurrency(BondForeign);
    }

    @Override
    public ArrayList<FmsBondForeign> findBySerialAndAmount(FmsBondForeign BondForeign) {
        return foreignFacade.findBySerialAndAmount(BondForeign);

    }

    @Override
    public FmsBondForeign getByBuyersNames(FmsBondForeign BondForeign) {
        return foreignFacade.getByBuyersNames(BondForeign);
    }

}
