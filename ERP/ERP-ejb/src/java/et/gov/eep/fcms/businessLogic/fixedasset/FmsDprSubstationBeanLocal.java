/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.businessLogic.fixedasset;

import et.gov.eep.fcms.entity.fixedasset.FmsDprSubstation;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Binyam
 */
@Local
public interface FmsDprSubstationBeanLocal {
    
    
    public List<FmsDprSubstation> findAll() ;    
    public void create(FmsDprSubstation fmsDprSubstation);
    public void edit(FmsDprSubstation fmsDprSubstation) ;
    public List <FmsDprSubstation> fetchSubstation(FmsDprSubstation fmsDprSubstation);
    public List<FmsDprSubstation> findStatus1();
}
