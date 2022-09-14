/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.controller.promotion;

import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.hrms.businessLogic.lookup.HrLuEducLevelsBeanLocal;
//import et.gov.eep.hrms.businessLogic.HrLuEducLevelBeanLocal;
import et.gov.eep.hrms.businessLogic.promotion.PromotionEducCriteriaBeanLocal;
import et.gov.eep.hrms.entity.lookup.HrLuEducLevels;
import et.gov.eep.hrms.entity.promotion.HrPromotionEducCriterias;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author user
 */
@Named("promotionEducCriteriaControllers")
@ViewScoped
public class PromotionEducCriteriaController implements Serializable {

    @EJB
    PromotionEducCriteriaBeanLocal promotionEducCriteriaBeanLocal;

    @Inject
    HrPromotionEducCriterias hrPromotionEducCriterias;

    @Inject
    HrLuEducLevels hrLuEducLevels;

    @EJB
    HrLuEducLevelsBeanLocal educLevelBeanLocal;
    List<HrPromotionEducCriterias> hrPromotionEducCriteriases = new ArrayList<>();
    private HrPromotionEducCriterias selectedRow;
    int update = 0;
    private String saveOrUpdateButton = "Save";

    @PostConstruct
    public void init() {
        hrPromotionEducCriteriases = promotionEducCriteriaBeanLocal.findAllEducation();
    }

    public HrPromotionEducCriterias getSelectedRow() {
        return selectedRow;
    }

    public void setSelectedRow(HrPromotionEducCriterias selectedRow) {
        this.selectedRow = selectedRow;
    }

    public String getSaveOrUpdateButton() {
        return saveOrUpdateButton;
    }

    public List<HrPromotionEducCriterias> getHrPromotionEducCriteriases() {
        return hrPromotionEducCriteriases;
    }

    public void setHrPromotionEducCriteriases(List<HrPromotionEducCriterias> hrPromotionEducCriteriases) {
        this.hrPromotionEducCriteriases = hrPromotionEducCriteriases;
    }

    public HrLuEducLevels getHrLuEducLevels() {
        return hrLuEducLevels;
    }

    public void setHrLuEducLevels(HrLuEducLevels hrLuEducLevels) {
        this.hrLuEducLevels = hrLuEducLevels;
    }

    public HrPromotionEducCriterias getHrPromotionEducCriterias() {
        if (hrPromotionEducCriterias == null) {
            hrPromotionEducCriterias = new HrPromotionEducCriterias();
        }
        return hrPromotionEducCriterias;
    }

    public void setHrPromotionEducCriterias(HrPromotionEducCriterias hrPromotionEducCriterias) {
        this.hrPromotionEducCriterias = hrPromotionEducCriterias;
    }

    public SelectItem[] getEducationLevel() {
        return JsfUtil.getSelectItems(promotionEducCriteriaBeanLocal.findAll(), true);
    }

    public void save() {
        try {
            for (int i = 0; i < hrPromotionEducCriteriases.size(); i++) {
                promotionEducCriteriaBeanLocal.saveOrUpdate(hrPromotionEducCriteriases.get(i));
            }
            if (update == 0) {
                JsfUtil.addSuccessMessage("Saved Successfully");
            } else {
                JsfUtil.addSuccessMessage("Updated Successfully");
            }

        } catch (Exception e) {
            e.printStackTrace();
            JsfUtil.addErrorMessage("Updated Successfully");
        }
    }

    public void clear() {
        hrPromotionEducCriterias = null;
    }

    public void addEducation() {
        System.out.println("inside educ add");
        if (hrPromotionEducCriteriases.containsAll(hrPromotionEducCriterias.getEducLevelId().getHrPromotionEducCriteriasList() )) {
            System.out.println("Data Already Exist");
             JsfUtil.addErrorMessage("DaTa Already Exit");
        } else {
            hrPromotionEducCriteriases.add(hrPromotionEducCriterias);
            reset();
        }
    }

    public void reset() {
        hrPromotionEducCriterias = null;
    }

    public void populateEducLevel(SelectEvent events) {
        hrPromotionEducCriterias = null;
        hrPromotionEducCriterias = (HrPromotionEducCriterias) events.getObject();
        saveOrUpdateButton = "Update";
        update = 1;
    }
}
