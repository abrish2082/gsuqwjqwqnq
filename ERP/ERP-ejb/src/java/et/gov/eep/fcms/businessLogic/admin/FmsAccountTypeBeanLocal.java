/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.businessLogic.admin;

import java.util.List;
import javax.ejb.Local;
import et.gov.eep.fcms.entity.admin.FmsAccountType;

/**
 *
 * @author memube
 */
@Local
public interface FmsAccountTypeBeanLocal {

    public void create(FmsAccountType fmsAccountType);

    public void edit(FmsAccountType fmsAccountType);

    public List<FmsAccountType> findAll();

    public Boolean findDupByAcctType(FmsAccountType fmsAccountType);

}
