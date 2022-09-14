/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.prms.controller;

import et.gov.eep.prms.entity.PrmsBid;
import java.io.Serializable;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author common for PRMS
 */
//PRMS Dashboard page view scoped CDI Named Bean class
@Named("prmsDashBoardController")
@ViewScoped

public class PrmsDashBoardController implements Serializable{
    
    @Inject
    PrmsBid prmsbid;

    public PrmsBid getPrmsbid() {
        return prmsbid;
    }

    public void setPrmsbid(PrmsBid prmsbid) {
        this.prmsbid = prmsbid;
    }
    
    
}
