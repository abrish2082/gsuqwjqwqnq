/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.businessLogic.bank;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import et.gov.eep.fcms.entity.bank.FmsBankBookError;
import et.gov.eep.fcms.mapper.bank.Bank_book_error_Facade;

/**
 *
 * @author sura
 */
@Stateless
public class Bank_book_error_Logic {

    @EJB
    private Bank_book_error_Facade bank_book_error_Facade;

    public void saveOrUpdate(FmsBankBookError bankBookError) {
        bank_book_error_Facade.saveOrUpdate(bankBookError);
    }
}
