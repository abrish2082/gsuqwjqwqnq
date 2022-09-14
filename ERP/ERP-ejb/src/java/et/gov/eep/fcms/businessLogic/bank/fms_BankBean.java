/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.businessLogic.bank;

import et.gov.eep.commonApplications.entity.ColumnNameResolver;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import et.gov.eep.fcms.entity.bank.FmsBank;
import et.gov.eep.fcms.mapper.bank.FmsBankFacade;

/**
 *
 * @author mubejbl
 */
@Stateless
public class fms_BankBean implements fms_BankBeanLocal {

    @EJB
    private FmsBankFacade fmsBankFacade;

    @Override
    public void create(FmsBank fmsBank) {
        fmsBankFacade.create(fmsBank);
    }

    @Override
    public ArrayList<FmsBank> searchBankByName(FmsBank fmsBank) {
        return fmsBankFacade.searchBankByName(fmsBank);
    }

    @Override
    public FmsBank getBankInfo(FmsBank fmsBank) {
        return fmsBankFacade.getBankInfo(fmsBank);
    }

    @Override
    public void edit(FmsBank fmsBank) {
        fmsBankFacade.edit(fmsBank);
    }

    @Override
    public List<FmsBank> getBankName() {
        return fmsBankFacade.findAll();
    }

    @Override
    public Boolean findDupByBankName(FmsBank fmsBank) {
        return fmsBankFacade.findDupByBankName(fmsBank);
    }

    @Override
    public Boolean findDupByBankCode(FmsBank fmsBank) {
        return fmsBankFacade.findDupByBankCode(fmsBank);
    }

    @Override
    public List<String> getMmsBankColumnNameList() {
        return fmsBankFacade.getMmsBankColumnNameList();
    }

    @Override
    public List<FmsBank> getBankListsByParameter(ColumnNameResolver columnNameResolver, FmsBank fmsBank, String columnValue) {
        return fmsBankFacade.getBankListsByParameter(columnNameResolver,fmsBank,columnValue);
    }
}
