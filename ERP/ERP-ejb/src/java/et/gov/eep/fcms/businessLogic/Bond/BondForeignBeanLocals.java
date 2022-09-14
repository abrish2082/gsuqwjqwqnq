/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.businessLogic.Bond;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;
import et.gov.eep.commonApplications.entity.ComLuCountry;
import et.gov.eep.fcms.entity.Bond.FmsBondForeign;

/**
 *
 * @author mora
 */
@Local
public interface BondForeignBeanLocals {

    public void Create(FmsBondForeign BondForeign);

    public void Eidt(FmsBondForeign BondForeign);

    public FmsBondForeign BondForeigninfo(FmsBondForeign BondForeign);

    public FmsBondForeign searchStatus(FmsBondForeign BondForeign);

    public FmsBondForeign getByBuyersNames(FmsBondForeign BondForeign);

    public ArrayList<FmsBondForeign> searchBondId(FmsBondForeign foreign);

    public ArrayList<FmsBondForeign> findBySerialNameAmountAndCurency(FmsBondForeign BondForeign);

    public ArrayList<FmsBondForeign> findBySerialAmountAndCurency(FmsBondForeign BondForeign);

    public ArrayList<FmsBondForeign> findByNameAmountAndCurency(FmsBondForeign BondForeign);

    public ArrayList<FmsBondForeign> findByAmountAndCurency(FmsBondForeign BondForeign);

    public ArrayList<FmsBondForeign> findByBondCurrency(FmsBondForeign BondForeign);

    public ArrayList<FmsBondForeign> findByBondAmount(FmsBondForeign BondForeign);

    public ArrayList<FmsBondForeign> findByNameAndCurrency(FmsBondForeign BondForeign);

    public ArrayList<FmsBondForeign> findBySerialAndCurrency(FmsBondForeign BondForeign);

    public ArrayList<FmsBondForeign> findBySerialAndAmount(FmsBondForeign BondForeign);

    public ArrayList<FmsBondForeign> searchName(FmsBondForeign foreign);

    public List<FmsBondForeign> searchAll();

    public List<FmsBondForeign> searchFmsBondgroup();

    public List<FmsBondForeign> searchCountryName();

    public ArrayList<FmsBondForeign> searchCountry(FmsBondForeign BondForeign);

    public List<FmsBondForeign> getByCountryId(Date fromIssueDate, Date toIssueDate);

    public List<FmsBondForeign> getByCountryId1(String fromIssueDate, String toIssueDate, ComLuCountry luCountry);

    public List<FmsBondForeign> findBySerialNo(FmsBondForeign BondForeign);

}
