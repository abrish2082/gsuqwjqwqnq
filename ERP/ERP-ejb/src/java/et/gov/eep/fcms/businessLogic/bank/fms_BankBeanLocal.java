/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.businessLogic.bank;

import et.gov.eep.commonApplications.entity.ColumnNameResolver;
import java.util.List;
import java.util.ArrayList;
import javax.ejb.Local;
import et.gov.eep.fcms.entity.bank.FmsBank;

/**
 *
 * @author mubejbl
 */
@Local
public interface fms_BankBeanLocal {

    public void create(FmsBank fmsBank);

    public void edit(FmsBank fmsBank);

    public FmsBank getBankInfo(FmsBank fmsBank);

    public Boolean findDupByBankName(FmsBank fmsBank);

    public Boolean findDupByBankCode(FmsBank fmsBank);

    public ArrayList<FmsBank> searchBankByName(FmsBank fmsBank);

    public List<FmsBank> getBankName();

    public List<String> getMmsBankColumnNameList();

    public List<FmsBank> getBankListsByParameter(ColumnNameResolver columnNameResolver, FmsBank fmsBank, String columnValue);

}
