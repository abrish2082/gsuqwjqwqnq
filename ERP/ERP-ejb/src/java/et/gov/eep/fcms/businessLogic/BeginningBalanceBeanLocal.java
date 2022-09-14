/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.businessLogic;

import et.gov.eep.fcms.entity.FmsBeginningBalance;
import javax.ejb.Local;

/**
 *
 * @author Me
 */
@Local
public interface BeginningBalanceBeanLocal {

    public void create(FmsBeginningBalance fmsBeginningBalance);

    public void edit(FmsBeginningBalance fmsBeginningBalance);

    public FmsBeginningBalance Acount_Period(FmsBeginningBalance beginningBalance);

}
